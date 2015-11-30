package script.test;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.qa;
import script.library.sui;
import script.library.utils;

public class qa_damage extends script.base_script
{
    public qa_damage()
    {
    }
    public static final String DAMAGE_PID_SCRIPTVAR = "doDamage.pid";
    public static final String DAMAGE_SCRIPTVAR = "doDamageVar";
    public static final String HEAL_PID_SCRIPTVAR = "healDamage.pid";
    public static final String HEAL_SCRIPTVAR = "healDamageVar";
    public static final String HEAL_TOOL_PROMPT = "Give the amount you want to heal the target for.  This tool will heal in the amount you specify as long as it doesn't exceed the target's maximum health.";
    public static final String HEAL_TOOL_TITLE = "HEAL AMOUNT";
    public static final String DAMAGE_TOOL_PROMPT = "Give the amount you want to damage the target for.  This tool will cause damage in the amount you specify.  ARMOR AND OTHER MITIGATION WILL NOT BE CONSIDERED.  Use the Mitigation Tool to test Mitigation.";
    public static final String DAMAGE_TOOL_TITLE = "DAMAGE AMOUNT";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (isGod(self))
        {
            if (getGodLevel(self) < 10)
            {
                detachScript(self, "test.qatool");
                sendSystemMessage(self, "You do not have the appropriate access level to use this script.", null);
            }
        }
        else if (!isGod(self))
        {
            detachScript(self, "test.qatool");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        if (isGod(self))
        {
            if ((toLower(text)).equals("qadamage") || (toLower(text)).equals("mobdamage") || (toLower(text)).equals("playerdamage"))
            {
                qa.damageMobTool(self);
            }
            else if ((toLower(text)).equals("heal") || (toLower(text)).equals("mobheal") || (toLower(text)).equals("playerheal"))
            {
                qa.healMobTool(self);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int doTheDamage(obj_id self, dictionary params) throws InterruptedException
    {
        if (isGod(self))
        {
            if (utils.hasScriptVar(self, DAMAGE_PID_SCRIPTVAR))
            {
                qa.checkParams(params, "doDamage");
                int idx = sui.getListboxSelectedRow(params);
                int btn = sui.getIntButtonPressed(params);
                int damageAmount = sui.getTransferInputTo(params);
                int healthWhenAttacked = utils.getIntScriptVar(self, DAMAGE_SCRIPTVAR + ".targetCurrentHealth");
                obj_id lookAtTarget = utils.stringToObjId(utils.getStringScriptVar(self, DAMAGE_SCRIPTVAR + ".lookAtTarget"));
                int healthRightNow = getAttrib(lookAtTarget, HEALTH);
                if (btn == sui.BP_CANCEL)
                {
                    removePlayer(self, "Tool Exiting");
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    if (damageAmount > 0 && isIdValid(lookAtTarget))
                    {
                        damage(lookAtTarget, DAMAGE_KINETIC, HIT_LOCATION_BODY, damageAmount);
                        CustomerServiceLog("qaTool", "User: (" + self + ") " + getName(self) + " has healed (" + lookAtTarget + ") using the QA Damage Tool.");
                        sendSystemMessageTestingOnly(self, "Damage to target completed.");
                    }
                    else 
                    {
                        sendSystemMessageTestingOnly(self, "Variables not valid");
                    }
                    removePlayer(self, "");
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int healDamage(obj_id self, dictionary params) throws InterruptedException
    {
        if (isGod(self))
        {
            if (utils.hasScriptVar(self, HEAL_PID_SCRIPTVAR))
            {
                qa.checkParams(params, "healDamage");
                int idx = sui.getListboxSelectedRow(params);
                int btn = sui.getIntButtonPressed(params);
                int healAmount = sui.getTransferInputTo(params);
                int healthWhenAttacked = utils.getIntScriptVar(self, HEAL_SCRIPTVAR + ".targetCurrentHealth");
                obj_id lookAtTarget = utils.stringToObjId(utils.getStringScriptVar(self, HEAL_SCRIPTVAR + ".lookAtTarget"));
                int healthRightNow = getAttrib(lookAtTarget, HEALTH);
                if (btn == sui.BP_CANCEL)
                {
                    removePlayer(self, "Tool Exiting");
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    if (healAmount > 0 && isIdValid(lookAtTarget))
                    {
                        int totalHeal = healAmount + healthRightNow;
                        setAttrib(lookAtTarget, HEALTH, totalHeal);
                        CustomerServiceLog("qaTool", "User: (" + self + ") " + getName(self) + " has healed (" + lookAtTarget + ") using the QA Heal Tool.");
                        sendSystemMessageTestingOnly(self, "Heal target completed.");
                    }
                    else 
                    {
                        sendSystemMessageTestingOnly(self, "Variables not valid");
                    }
                    removePlayer(self, "");
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void removePlayer(obj_id self, String err) throws InterruptedException
    {
        sendSystemMessageTestingOnly(self, err);
        qa.removeScriptVars(self, DAMAGE_SCRIPTVAR);
        qa.removeScriptVars(self, DAMAGE_PID_SCRIPTVAR);
        utils.removeScriptVarTree(self, DAMAGE_SCRIPTVAR);
        utils.removeScriptVarTree(self, DAMAGE_PID_SCRIPTVAR);
        qa.removeScriptVars(self, HEAL_SCRIPTVAR);
        qa.removeScriptVars(self, HEAL_PID_SCRIPTVAR);
        utils.removeScriptVarTree(self, HEAL_SCRIPTVAR);
        utils.removeScriptVarTree(self, HEAL_PID_SCRIPTVAR);
    }
}
