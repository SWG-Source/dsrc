package script.systems.gcw;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.hq;
import script.library.xp;
import script.library.sui;
import script.library.utils;
import script.library.prose;
import script.library.ai_lib;
import script.library.slicing;

public class objective_terminal_security extends script.faction_perk.hq.objective_object
{
    public objective_terminal_security()
    {
    }
    public static final string_id SID_SLICE = new string_id("slicing/slicing", "slice");
    public static final string_id SID_FAIL_SLICE = new string_id("slicing/slicing", "hq_security_fail");
    public static final string_id SID_SUCCESS_SLICE = new string_id("slicing/slicing", "hq_security_success");
    public static final string_id SID_NOT_AGAIN = new string_id("slicing/slicing", "not_again");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int mnu = mi.addRootMenu(menu_info_types.SERVER_MENU1, SID_SLICE);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        removeObjVar(self, "slice_start");
        removeObjVar(self, "sliced_by");
        setObjVar(self, "type", "terminal");
        setObjVar(self, "intTerminal", 1);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (pvpGetType(player) != PVPTYPE_DECLARED)
        {
            sendSystemMessageTestingOnly(player, "Only declared factional personnel may access this terminal!");
            return SCRIPT_CONTINUE;
        }
        obj_id structure = getObjIdObjVar(self, "objParent");
        if (!isIdValid(structure))
        {
            return SCRIPT_CONTINUE;
        }
        int sFac = pvpGetAlignedFaction(structure);
        int pFac = pvpGetAlignedFaction(player);
        if (!pvpAreFactionsOpposed(sFac, pFac))
        {
            sendSystemMessageTestingOnly(player, "You are not an enemy of this structure. Why would you want to tamper?");
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, hq.VAR_IS_DISABLED))
        {
            sendSystemMessageTestingOnly(player, "The security terminal has already been sliced!");
            return SCRIPT_CONTINUE;
        }
        obj_id nextObjective = hq.getNextObjective(structure);
        if (nextObjective != self)
        {
            obj_id priorObjective = hq.getPriorObjective(structure, self);
            if (isIdValid(priorObjective))
            {
                prose_package ppDisableOther = prose.getPackage(hq.PROSE_DISABLE_OTHER, priorObjective, self);
                sendSystemMessageProse(player, ppDisableOther);
            }
            else 
            {
                sendSystemMessageTestingOnly(player, "Other objectives must be disabled prior to gaining access to this one.");
            }
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.SERVER_MENU1)
        {
            if (hasObjVar(self, "slicing.failed"))
            {
                if (utils.hasScriptVar(self, "repairing"))
                {
                    obj_id repairer = utils.getObjIdScriptVar(self, "repairing");
                    if (isIdValid(repairer))
                    {
                        if (player == repairer)
                        {
                            sendSystemMessageTestingOnly(player, "You are already repairing a prior slice attempt's damage...");
                            return SCRIPT_CONTINUE;
                        }
                        else 
                        {
                            if (repairer.isLoaded())
                            {
                                sendSystemMessageTestingOnly(player, getName(repairer) + " is already repairing a prior slice attempt's damage...");
                                return SCRIPT_CONTINUE;
                            }
                        }
                    }
                }
                sendSystemMessageTestingOnly(player, "You begin repairing the damage done by a prior slicing attempt...");
                dictionary d = new dictionary();
                d.put("player", player);
                d.put("cnt", 10);
                messageTo(self, "handleSlicingRepair", d, 5f, false);
                utils.setScriptVar(self, "repairing", player);
                return SCRIPT_CONTINUE;
            }
            slicing.startSlicing(player, self, "finishSlicing", "hq_security");
        }
        return SCRIPT_CONTINUE;
    }
    public int handleObjectiveDisabled(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int finishSlicing(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        int success = params.getInt("success");
        obj_id player = params.getObjId("player");
        if (success == 1)
        {
            sendSystemMessage(player, SID_SUCCESS_SLICE);
            obj_id structure = getObjIdObjVar(self, "objParent");
            if (isIdValid(structure))
            {
                hq.disableObjective(structure, self);
            }
        }
        else 
        {
            sendSystemMessage(player, SID_FAIL_SLICE);
            setObjVar(self, "slicing.failed", player);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleSlicingRepair(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, "slicing.failed"))
        {
            sendSystemMessageTestingOnly(player, "It appears that the terminal has been repaired. You may begin slicing again.");
            return SCRIPT_CONTINUE;
        }
        int cnt = params.getInt("cnt");
        int max = 1;
        cnt -= rand(1, max);
        if (cnt < 0)
        {
            sendSystemMessageTestingOnly(player, "It appears that the terminal has been repaired. You may begin slicing again.");
            removeObjVar(self, "slicing.failed");
            return SCRIPT_CONTINUE;
        }
        else 
        {
            sendSystemMessageTestingOnly(player, "Security terminal repairs continue...");
            params.put("cnt", cnt);
            messageTo(self, "handleSlicingRepair", params, 5f, false);
        }
        return SCRIPT_CONTINUE;
    }
}
