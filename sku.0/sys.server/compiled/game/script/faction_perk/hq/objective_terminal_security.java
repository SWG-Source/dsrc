package script.faction_perk.hq;

import script.*;
import script.library.*;

public class objective_terminal_security extends script.faction_perk.hq.objective_object
{
    public objective_terminal_security()
    {
    }
    private static final string_id SID_SLICE = new string_id("slicing/slicing", "slice");
    private static final string_id SID_FAIL_SLICE = new string_id("slicing/slicing", "hq_security_fail");
    private static final string_id SID_SUCCESS_SLICE = new string_id("slicing/slicing", "hq_security_success");
    public static final string_id SID_NOT_AGAIN = new string_id("slicing/slicing", "not_again");
    private static final string_id NO_TAMPER = new string_id("faction/faction_hq/faction_hq_response", "no_tamper");
    private static final string_id ALREADY_SLICED = new string_id("faction/faction_hq/faction_hq_response", "already_sliced");
    private static final string_id OTHER_OBJECTIVES = new string_id("faction/faction_hq/faction_hq_response", "other_objectives");
    private static final string_id ONLY_A_SMUGGLER = new string_id("faction/faction_hq/faction_hq_response", "only_a_smuggler");
    private static final string_id ALREADY_REPAIRING_SLICE = new string_id("faction/faction_hq/faction_hq_response", "already_repairing_slice");
    private static final string_id BEGIN_REPAIRING_SLICE = new string_id("faction/faction_hq/faction_hq_response", "begin_repairing_slice");
    private static final string_id NO_REPAIR_CURRENT_STATE = new string_id("faction/faction_hq/faction_hq_response", "no_repair_current_state");
    private static final string_id NO_REPAIR_DISTANCE = new string_id("faction/faction_hq/faction_hq_response", "no_repair_distance");
    private static final string_id TERM_REPAIRED = new string_id("faction/faction_hq/faction_hq_response", "term_repaired");
    private static final string_id TERM_REPAIR_CONT = new string_id("faction/faction_hq/faction_hq_response", "term_repair_cont");
    private static final string_id SID_NO_STEALTH = new string_id("hq", "no_stealth");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int intState = getState(player, STATE_FEIGN_DEATH);
        if (isDead(player) || isIncapacitated(player) || intState > 0)
        {
            return SCRIPT_CONTINUE;
        }
        if (stealth.hasInvisibleBuff(player))
        {
            sendSystemMessage(player, SID_NO_STEALTH);
            return SCRIPT_CONTINUE;
        }
        mi.addRootMenu(menu_info_types.ITEM_USE, SID_SLICE);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (stealth.hasInvisibleBuff(player))
        {
            sendSystemMessage(player, SID_NO_STEALTH);
            return SCRIPT_CONTINUE;
        }
        obj_id structure = getTopMostContainer(self);
        if (!isIdValid(structure))
        {
            return SCRIPT_CONTINUE;
        }
        int sFac = pvpGetAlignedFaction(structure);
        int pFac = pvpGetAlignedFaction(player);
        if (pvpGetType(player) == PVPTYPE_NEUTRAL)
        {
            pFac = 0;
        }
        if (!pvpAreFactionsOpposed(sFac, pFac))
        {
            sendSystemMessage(player, NO_TAMPER);
            return SCRIPT_OVERRIDE;
        }
        if (hasObjVar(self, hq.VAR_IS_DISABLED))
        {
            sendSystemMessage(player, ALREADY_SLICED);
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
                sendSystemMessage(player, OTHER_OBJECTIVES);
            }
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.ITEM_USE)
        {
            if (!hasSkill(player, "class_smuggler_phase1_novice"))
            {
                sendSystemMessage(player, ONLY_A_SMUGGLER);
                return SCRIPT_CONTINUE;
            }
            if (hasObjVar(self, "slicing.failed"))
            {
                if (utils.hasScriptVar(self, "repairing"))
                {
                    obj_id repairer = utils.getObjIdScriptVar(self, "repairing");
                    if (isIdValid(repairer))
                    {
                        if (player == repairer)
                        {
                            sendSystemMessage(player, ALREADY_REPAIRING_SLICE);
                            return SCRIPT_CONTINUE;
                        }
                    }
                }
                sendSystemMessage(player, BEGIN_REPAIRING_SLICE);
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
        obj_id player = params.getObjId("player");
        if (params.getInt("success") == 1)
        {
            sendSystemMessage(player, SID_SUCCESS_SLICE);
            obj_id structure = getTopMostContainer(self);
            if (isIdValid(structure))
            {
                hq.disableObjective(structure, self);
            }
            String playerName = getName(player);
            CustomerServiceLog("faction_hq", playerName + "(" + player + "), " + "sliced Security Terminal at " + getGameTime() + ".");
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
        if (stealth.hasInvisibleBuff(player))
        {
            sendSystemMessage(player, SID_NO_STEALTH);
            return SCRIPT_CONTINUE;
        }
        if (!isIdValid(player) || !exists(player) || !player.isLoaded())
        {
            return SCRIPT_CONTINUE;
        }
        if (isIncapacitated(player) || isDead(player))
        {
            sendSystemMessage(player, NO_REPAIR_CURRENT_STATE);
            return SCRIPT_CONTINUE;
        }
        if (getContainedBy(self) != getContainedBy(player) || getDistance(self, player) > 50f)
        {
            sendSystemMessage(player, NO_REPAIR_DISTANCE);
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, "slicing.failed"))
        {
            sendSystemMessage(player, TERM_REPAIRED);
            return SCRIPT_CONTINUE;
        }
        int cnt = params.getInt("cnt");
        int max = 1;
        if (hasSkill(player, "class_smuggler_phase4_novice"))
        {
            max = 4;
        }
        else if (hasSkill(player, "class_smuggler_phase3_novice"))
        {
            max = 3;
        }
        else if (hasSkill(player, "class_smuggler_phase2_novice"))
        {
            max = 2;
        }
        cnt -= rand(1, max);
        if (cnt < 0)
        {
            sendSystemMessage(player, TERM_REPAIRED);
            removeObjVar(self, "slicing.failed");
            return SCRIPT_CONTINUE;
        }
        else 
        {
            sendSystemMessage(player, TERM_REPAIR_CONT);
            params.put("cnt", cnt);
            messageTo(self, "handleSlicingRepair", params, 5f, false);
        }
        return SCRIPT_CONTINUE;
    }
}
