package script.test;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import java.util.HashSet;
import java.util.StringTokenizer;
import java.util.Vector;
import script.library.combat;
import script.library.qa;
import script.library.sui;
import script.library.utils;

public class mitigation extends script.base_script
{
    public mitigation()
    {
    }
    public static final String PID_SCRIPTVAR = "mitigationPid";
    public static final String SCRIPTVAR = "mitigation";
    public static final String MITIGATION_TOOL_PROMPT = "SELECT AN ATTACK LOCATION.\n\r\n\rThe mitigation tool tests armor mitigation based on the attacker weapon.  No actual damage is performed on the target.  Damage and mitigation is simulated using the current mitigation system (without elemental damage).  When the test is conducted a report will be exported to your client directory.";
    public static final String MITIGATION_TOOL_TITLE = "MITIGATION TOOL";
    public static final String[] MITIGATION_HIT_LOCATIONS = 
    {
        "Body",
        "Head",
        "Right Arm",
        "Left Arm",
        "Right Leg",
        "Left Leg"
    };
    public static final String[] WEAPON_DAMAGE_TYPE = 
    {
        "DAMAGE_NONE",
        "DAMAGE_KINETIC",
        "DAMAGE_ENERGY",
        "DAMAGE_BLAST",
        "DAMAGE_STUN",
        "DAMAGE_RESTRAINT",
        "DAMAGE_ELEMENTAL_HEAT",
        "DAMAGE_ELEMENTAL_COLD",
        "DAMAGE_ELEMENTAL_ACID",
        "DAMAGE_ELEMENTAL_ELECTRICAL",
        "DAMAGE_ENVIRONMENTAL_HEAT",
        "DAMAGE_ENVIRONMENTAL_COLD",
        "DAMAGE_ENVIRONMENTAL_ACID",
        "DAMAGE_ENVIRONMENTAL_ELECTRICAL"
    };
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!isGod(self) || getGodLevel(self) < 10) {
            detachScript(self, "test.mitigation");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        if (isGod(self))
        {
            if ((toLower(text)).equals(SCRIPTVAR))
            {
                obj_id lookAtTarget = getLookAtTarget(self);
                if (isIdValid(lookAtTarget))
                {
                    if (isPlayer(lookAtTarget) || isMob(lookAtTarget))
                    {
                        obj_id objWeapon = getCurrentWeapon(self);
                        if (isIdValid(objWeapon))
                        {
                            weapon_data newWpnData = getWeaponData(objWeapon);
                            utils.setScriptVar(self, SCRIPTVAR + ".lookAtTarget", "" + lookAtTarget);
                            utils.setScriptVar(self, SCRIPTVAR + ".objWeapon", "" + objWeapon);
                            utils.setScriptVar(self, SCRIPTVAR + ".minDamage", newWpnData.minDamage);
                            utils.setScriptVar(self, SCRIPTVAR + ".maxDamage", newWpnData.maxDamage);
                            String[] dmgMenu = new String[3];
                            dmgMenu[0] = "Do Minimum Damage ( " + newWpnData.minDamage + " )";
                            dmgMenu[1] = "Do Maximum Damage ( " + newWpnData.maxDamage + " )";
                            dmgMenu[2] = "Do Random Damage";
                            utils.setScriptVar(self, SCRIPTVAR + ".dmgMenu", dmgMenu);
                            qa.refreshMenu(self, MITIGATION_TOOL_PROMPT, MITIGATION_TOOL_TITLE, MITIGATION_HIT_LOCATIONS, "handleAttackLocationOptions", true, PID_SCRIPTVAR + ".pid", SCRIPTVAR + ".hitLocationMenu");
                        }
                        else 
                        {
                            sendSystemMessageTestingOnly(self, "Equip a weapon before attempting to use this tool.");
                        }
                    }
                    else 
                    {
                        sendSystemMessageTestingOnly(self, "You must have a valid mob or player targeted.");
                    }
                }
                else 
                {
                    sendSystemMessageTestingOnly(self, "You must have a valid mob or player targeted.");
                }
            }
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int handleAttackLocationOptions(obj_id self, dictionary params) throws InterruptedException
    {
        if (isGod(self))
        {
            if (utils.hasScriptVar(self, PID_SCRIPTVAR + ".pid"))
            {
                qa.checkParams(params, PID_SCRIPTVAR);
                int idx = sui.getListboxSelectedRow(params);
                int btn = sui.getIntButtonPressed(params);
                if (btn == sui.BP_CANCEL)
                {
                    removePlayer(self, "Tool Exiting.");
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    if (idx > -1)
                    {
                        utils.setScriptVar(self, SCRIPTVAR + ".attackLocation", idx);
                        String[] hitAmountArray = utils.getStringArrayScriptVar(self, SCRIPTVAR + ".dmgMenu");
                        qa.refreshMenu(self, MITIGATION_TOOL_PROMPT, MITIGATION_TOOL_TITLE, hitAmountArray, "handleDamageOptions", PID_SCRIPTVAR + ".pid", sui.OK_CANCEL);
                    }
                    else 
                    {
                        removePlayer(self, "There was an error.  Tool Exiting.");
                        return SCRIPT_CONTINUE;
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleDamageOptions(obj_id self, dictionary params) throws InterruptedException
    {
        if (isGod(self))
        {
            if (utils.hasScriptVar(self, PID_SCRIPTVAR + ".pid"))
            {
                qa.checkParams(params, PID_SCRIPTVAR);
                int idx = sui.getListboxSelectedRow(params);
                int btn = sui.getIntButtonPressed(params);
                if (btn == sui.BP_CANCEL)
                {
                    removePlayer(self, "");
                    return SCRIPT_CONTINUE;
                }
                else if (btn == sui.BP_REVERT)
                {
                    String[] hitAmountArray = utils.getStringArrayScriptVar(self, SCRIPTVAR + ".dmgMenu");
                    qa.refreshMenu(self, MITIGATION_TOOL_PROMPT, MITIGATION_TOOL_TITLE, MITIGATION_HIT_LOCATIONS, "handleAttackLocationOptions", true, PID_SCRIPTVAR + ".pid", SCRIPTVAR + ".hitLocationMenu");
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    if (idx > -1)
                    {
                        obj_id objWeapon = utils.stringToObjId(utils.getStringScriptVar(self, SCRIPTVAR + ".objWeapon"));
                        weapon_data newWpnData = getWeaponData(objWeapon);
                        int testDamage = 0;
                        switch (idx)
                        {
                            case 0:
                            testDamage = utils.getIntScriptVar(self, SCRIPTVAR + ".minDamage");
                            utils.setScriptVar(self, SCRIPTVAR + ".damage", testDamage);
                            break;
                            case 1:
                            testDamage = utils.getIntScriptVar(self, SCRIPTVAR + ".maxDamage");
                            utils.setScriptVar(self, SCRIPTVAR + ".damage", testDamage);
                            break;
                            case 2:
                            utils.setScriptVar(self, SCRIPTVAR + ".damage", testDamage);
                            break;
                            default:
                            removePlayer(self, "");
                            return SCRIPT_CONTINUE;
                        }
                        int pid = sui.transfer(self, self, "Give the amount of tests you would like to conduct on the location specified", "TEST ITERATION", "Maximum", 1000, "Amount", 1, "mitigationReport");
                        sui.showSUIPage(pid);
                        utils.setScriptVar(self, PID_SCRIPTVAR + ".pid", pid);
                    }
                    else 
                    {
                        removePlayer(self, "There was an error.  Tool Exiting.");
                        return SCRIPT_CONTINUE;
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int mitigationReport(obj_id self, dictionary params) throws InterruptedException
    {
        if (isGod(self))
        {
            if (utils.hasScriptVar(self, PID_SCRIPTVAR + ".pid"))
            {
                qa.checkParams(params, PID_SCRIPTVAR);
                int idx = sui.getListboxSelectedRow(params);
                int btn = sui.getIntButtonPressed(params);
                int testIteration = sui.getTransferInputTo(params);
                obj_id lookAtTarget = utils.stringToObjId(utils.getStringScriptVar(self, SCRIPTVAR + ".lookAtTarget"));
                obj_id objWeapon = utils.stringToObjId(utils.getStringScriptVar(self, SCRIPTVAR + ".objWeapon"));
                int attackLocation = utils.getIntScriptVar(self, SCRIPTVAR + ".attackLocation");
                int damage = utils.getIntScriptVar(self, SCRIPTVAR + ".damage");
                int minDmg = utils.getIntScriptVar(self, SCRIPTVAR + ".minDamage");
                int maxDmg = utils.getIntScriptVar(self, SCRIPTVAR + ".maxDamage");
                if (btn == sui.BP_CANCEL)
                {
                    String[] hitAmountArray = utils.getStringArrayScriptVar(self, SCRIPTVAR + ".dmgMenu");
                    qa.refreshMenu(self, MITIGATION_TOOL_PROMPT, MITIGATION_TOOL_TITLE, MITIGATION_HIT_LOCATIONS, "handleAttackLocationOptions", true, PID_SCRIPTVAR + ".pid", SCRIPTVAR + ".hitLocationMenu");
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    String testData = "";
                    int damageTotal = damage;
                    for (int i = 0; i < testIteration; i++)
                    {
                        if (damageTotal == 0)
                        {
                            damageTotal = rand(minDmg, maxDmg);
                        }
                        weapon_data newWpnData = getWeaponData(objWeapon);
                        hit_result hitData = new hit_result();
                        hitData.damage = damageTotal;
                        hitData.success = true;
                        hitData.damageType = newWpnData.attackType;
                        int blockedDamage = combat.applyArmorProtection(self, lookAtTarget, newWpnData, hitData, 0f);
                        testData += (i + 1) + "\t" + damageTotal + "\t" + blockedDamage + "\t" + WEAPON_DAMAGE_TYPE[newWpnData.attackType + 1] + "\n";
                    }
                    String topStrings = "Weapon OID: " + objWeapon + "\tAttack Location: " + MITIGATION_HIT_LOCATIONS[attackLocation] + "\tMinimum Damage: " + minDmg + "\tMaximum Damage: " + maxDmg + "\n\r";
                    topStrings += "Attack #\tDamage Amount\tDamage Blocked\tWeapon Damage Type\n\r";
                    topStrings += testData + "\n\r";
                    saveTextOnClient(self, "weapon" + objWeapon + "MitigationTest.tab", topStrings);
                    removePlayer(self, "");
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void removePlayer(obj_id self, String err) throws InterruptedException
    {
        sendSystemMessageTestingOnly(self, err);
        qa.removeScriptVars(self, SCRIPTVAR);
        qa.removeScriptVars(self, PID_SCRIPTVAR);
        utils.removeScriptVarTree(self, SCRIPTVAR);
        utils.removeScriptVarTree(self, PID_SCRIPTVAR);
    }
}
