package script.item.tool;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.consumable;
import script.library.pet_lib;
import script.library.sui;
import script.library.utils;
import script.library.pclib;
import script.library.prose;
import script.library.healing;
import java.util.Enumeration;
import script.library.ai_lib;

public class medikit extends script.base_script
{
    public medikit()
    {
    }
    public static final String SCRIPTVAR_PID = "tool.pid";
    public static final String SCRIPTVAR_PLAYER = "tool.player";
    public static final String SCRIPTVAR_TARGET = "tool.lookattarget";
    public static final String SCRIPTVAR_LASTCOMMAND = "tool.lastcommand";
    public static final String STF = "tool/med_tool";
    public static final string_id MNU_DIAGNOSE = new string_id(STF, "mnu_diagnose");
    public static final string_id OPT_COLOR_TRIM = new string_id(STF, "opt_color_trim");
    public static final string_id OPT_COLOR_FRAME = new string_id(STF, "opt_color_frame");
    public static final string_id SID_OTHER_PLAYERS_ONLY = new string_id(STF, "other_players_only");
    public static final string_id SID_DO_NOT_HEAL = new string_id(STF, "do_not_heal");
    public static final string_id SID_MUST_BE_PLAYER_OR_PET = new string_id(STF, "must_be_player_or_pet");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        clearTrackingScriptVars(self);
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToReceiveItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        String parentDirectory = getTemplateParentDirectory(item);
        if (parentDirectory.equals("foraged") && (getCount(self) < 99))
        {
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_OVERRIDE;
    }
    public int OnReceivedItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        int countDelta = foragedItemsMod(item);
        incrementCount(self, countDelta);
        destroyObject(item);
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "medikit.quality"))
        {
            names[idx] = "useModifier";
            int attrib = getIntObjVar(self, "medikit.quality");
            attribs[idx] = "" + attrib;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!utils.isNestedWithin(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        int myPosture = getPosture(self);
        if (myPosture == POSTURE_DEAD)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id lookatTarget = getLookAtTarget(player);
        if (canDiagnose(player, lookatTarget))
        {
            int mnuDiagnose = mi.addRootMenu(menu_info_types.SERVER_MED_TOOL_DIAGNOSE, MNU_DIAGNOSE);
        }
        int currentCharges = getCount(self);
        if (!(currentCharges < 1))
        {
            if (healing.canHealDamage(player, false))
            {
                mi.addRootMenu(menu_info_types.SERVER_MED_TOOL_TENDWOUND, new string_id(STF, "menu_tendwound"));
                mi.addRootMenu(menu_info_types.SERVER_MED_TOOL_TENDDAMAGE, new string_id(STF, "menu_tenddamage"));
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (!utils.isNestedWithin(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id lookatTarget = getLookAtTarget(player);
        if (item == menu_info_types.SERVER_MED_TOOL_DIAGNOSE)
        {
            queueCommand(player, (1685325419), self, lookatTarget.toString(), COMMAND_PRIORITY_DEFAULT);
            utils.setScriptVar(self, SCRIPTVAR_PLAYER, player);
            utils.setScriptVar(self, SCRIPTVAR_TARGET, lookatTarget);
            utils.setScriptVar(self, SCRIPTVAR_LASTCOMMAND, "diagnose");
        }
        if (item == menu_info_types.SERVER_MED_TOOL_TENDWOUND)
        {
            sendSystemMessageTestingOnly(player, "Selected TENDWOUND,  line 144");
            if (isIdValid(self) && isIdValid(lookatTarget))
            {
                sendSystemMessageTestingOnly(player, "Check passed isIdValid(self) AND isIdValid(target),  line 147");
                queueCommand(self, (-2132418973), lookatTarget, "no_params", COMMAND_PRIORITY_DEFAULT);
                utils.setScriptVar(self, SCRIPTVAR_TARGET, lookatTarget);
                utils.setScriptVar(self, SCRIPTVAR_LASTCOMMAND, "tendwound");
            }
        }
        if (item == menu_info_types.SERVER_MED_TOOL_TENDDAMAGE)
        {
            sendSystemMessageTestingOnly(player, "Selected TENDDAMAGE, line 155");
            if (isIdValid(self) && isIdValid(lookatTarget))
            {
                sendSystemMessageTestingOnly(player, "Check passed isIdValid(self) AND isIdValid(target),  line 158");
                queueCommand(self, (2132302964), lookatTarget, "no_params", COMMAND_PRIORITY_DEFAULT);
                utils.setScriptVar(self, SCRIPTVAR_TARGET, lookatTarget);
                utils.setScriptVar(self, SCRIPTVAR_LASTCOMMAND, "tenddamage");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdDiagnosePatient(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!isIdValid(self) || !isIdValid(target))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id medic = null;
        if (medic != target)
        {
            if (getDistance(medic, target) > consumable.MAX_AFFECT_DISTANCE)
            {
                sendSystemMessage(medic, consumable.SID_TARGET_OUT_OF_RANGE);
                return SCRIPT_CONTINUE;
            }
        }
        String[] dsrc = new String[NUM_ATTRIBUTES + 1];
        for (int i = 0; i < NUM_ATTRIBUTES; i++)
        {
            String attribute_string = (healing.attributeToString(i)).toLowerCase();
            int attribWound = getAttribWound(target, i);
            dsrc[i] = attribute_string + "  --  " + attribWound;
        }
        dsrc[NUM_ATTRIBUTES] = new string_id(STF, "battle_fatigue") + "  --  " + getShockWound(target);
        String prompt = "Wounds";
        String title = "Patient's Wounds";
        sui.listbox(self, medic, prompt, sui.OK_CANCEL, title, dsrc, "noHandler");
        clearTrackingScriptVars(self);
        return SCRIPT_CONTINUE;
    }
    public boolean canDiagnose(obj_id medic, obj_id target) throws InterruptedException
    {
        if ((medic != target) && (isIdValid(target)))
        {
            if (getDistance(medic, target) > consumable.MAX_AFFECT_DISTANCE)
            {
                sendSystemMessage(medic, consumable.SID_TARGET_OUT_OF_RANGE);
            }
            else 
            {
                if (!pvpCanHelp(medic, target))
                {
                    LOG("LOG_CHANNEL", medic + " ->It would be unwise to help such a patient.");
                    sendSystemMessage(medic, SID_DO_NOT_HEAL);
                }
                else 
                {
                    if (!isPlayer(target))
                    {
                        if (!isCreaturePet(target) && !pet_lib.isNpcPet(target))
                        {
                            LOG("LOG_CHANNEL", "Target must be a player or a creature pet in order to tend wounds.");
                            sendSystemMessage(medic, SID_MUST_BE_PLAYER_OR_PET);
                        }
                        else 
                        {
                            return true;
                        }
                    }
                    else 
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean haveChargedMedTool(obj_id player, obj_id medikitTool) throws InterruptedException
    {
        if ((getCount(medikitTool) > 0) && (utils.isNestedWithin(medikitTool, player)))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public void clearTrackingScriptVars(obj_id self) throws InterruptedException
    {
        utils.removeScriptVar(self, SCRIPTVAR_PID);
        utils.removeScriptVar(self, SCRIPTVAR_PLAYER);
        utils.removeScriptVar(self, SCRIPTVAR_TARGET);
        utils.removeScriptVar(self, SCRIPTVAR_LASTCOMMAND);
    }
    public String getTemplateParentDirectory(obj_id target) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return null;
        }
        String parentDirectory = null;
        String fullPath = getTemplateName(target);
        if (fullPath != null && !fullPath.equals(""))
        {
            String[] tmp = split(fullPath, '/');
            if (tmp != null && tmp.length > 0)
            {
                parentDirectory = tmp[tmp.length - 2];
            }
        }
        return parentDirectory;
    }
    public int foragedItemsMod(obj_id item) throws InterruptedException
    {
        if (!isIdValid(item))
        {
            return 0;
        }
        int health_mod = 0;
        int mind_mod = 0;
        int action_mod = 0;
        int mod_time = 0;
        int forageMod = 0;
        if (hasObjVar(item, "consumable.health_mod"))
        {
            health_mod = getIntObjVar(item, "consumable.health_mod");
        }
        if (hasObjVar(item, "consumable.mind_mod"))
        {
            mind_mod = getIntObjVar(item, "consumable.mind_mod");
        }
        if (hasObjVar(item, "consumable.action_mod"))
        {
            action_mod = getIntObjVar(item, "consumable.mind_mod");
        }
        if (hasObjVar(item, "consumable.mod_time"))
        {
            mod_time = getIntObjVar(item, "consumable.mind_mod");
        }
        int tempItemModSum = health_mod + mind_mod + action_mod + mod_time;
        if (tempItemModSum < 400)
        {
            forageMod = 1;
        }
        else if (tempItemModSum < 700)
        {
            forageMod = 2;
        }
        else 
        {
            forageMod = 3;
        }
        return forageMod;
    }
    public boolean isCreaturePet(obj_id npc) throws InterruptedException
    {
        return (ai_lib.isMonster(npc) || hasObjVar(npc, "ai.pet"));
    }
}
