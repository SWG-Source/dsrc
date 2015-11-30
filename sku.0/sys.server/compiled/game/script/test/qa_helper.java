package script.test;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import java.util.StringTokenizer;
import script.library.create;
import script.library.combat;
import script.library.qa;
import script.library.sui;
import script.library.utils;

public class qa_helper extends script.base_script
{
    public qa_helper()
    {
    }
    public static final String PID_SCRIPTVAR = "qa_helper";
    public static final String SCRIPTVAR = "qahelper";
    public static final String SCRIPTVAR_MOB = "qahelper_record";
    public static final String CREATURE_TABLE = "datatables/mob/creatures.iff";
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        if (isGod(self))
        {
            if (text.startsWith(SCRIPTVAR))
            {
                java.util.StringTokenizer st = new java.util.StringTokenizer(text);
                String cmd = st.nextToken();
                String arg = "";
                if (st.hasMoreTokens())
                {
                    arg = st.nextToken();
                }
                makeHelper(self, arg);
                return SCRIPT_OVERRIDE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnCreatureDamaged(obj_id self, obj_id attacker, obj_id weapon, int[] damage) throws InterruptedException
    {
        if (hasScript(self, "player.yavin_e3") && utils.getBooleanScriptVar(self, SCRIPTVAR_MOB + ".recordDamage") && utils.getObjIdScriptVar(attacker, "spawnedBy") == self)
        {
            sendSystemMessageTestingOnly(self, "Damage numbers will not be accurate due to player.yavin_e3 script attached to your character");
        }
        else 
        {
            if (utils.getObjIdScriptVar(attacker, "spawnedBy") == self)
            {
                if (utils.getBooleanScriptVar(self, SCRIPTVAR_MOB + ".recordDamage"))
                {
                    String weaponString = "";
                    String unlocalizedWeaponName = "";
                    int testerHealth = utils.getIntScriptVar(self, SCRIPTVAR_MOB + ".healthVar");
                    int currentTime = getGameTime();
                    int currentHealth = getAttrib(self, HEALTH);
                    int damageAmount = testerHealth - currentHealth;
                    utils.setScriptVar(self, SCRIPTVAR_MOB + ".healthVar", currentHealth);
                    obj_id objWeapon = getCurrentWeapon(attacker);
                    String objectWeaponName = getName(objWeapon);
                    int staticItem = objectWeaponName.indexOf("static_item_n:");
                    int nonStaticItem = objectWeaponName.indexOf("weapon_name:");
                    if (nonStaticItem > -1)
                    {
                        unlocalizedWeaponName = objectWeaponName.substring(12);
                        weaponString = localize(new string_id("weapon_name", unlocalizedWeaponName));
                    }
                    else if (staticItem > -1)
                    {
                        unlocalizedWeaponName = objectWeaponName.substring(14);
                        weaponString = localize(new string_id("static_item_n", unlocalizedWeaponName));
                    }
                    else if (!unlocalizedWeaponName.equals(""))
                    {
                        weaponString = unlocalizedWeaponName;
                    }
                    else 
                    {
                        weaponString = "Error Retirieving Weapon Data";
                    }
                    String damageData = attacker + "\t" + currentTime + "\t " + damageAmount + "\t" + weaponString + "\r\n";
                    String appendDamage = "";
                    String damageDone = utils.getStringScriptVar(self, SCRIPTVAR_MOB + ".damageDone");
                    if (!damageDone.equals("No Data"))
                    {
                        appendDamage = damageDone + damageData;
                    }
                    else 
                    {
                        appendDamage = damageData;
                    }
                    utils.setScriptVar(self, SCRIPTVAR_MOB + ".damageDone", appendDamage);
                    currentHealth = 0;
                    damageAmount = 0;
                    return SCRIPT_CONTINUE;
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void makeHelper(obj_id self, String argumentString) throws InterruptedException
    {
        int creatureRowNumber = dataTableSearchColumnForString(argumentString, "creatureName", CREATURE_TABLE);
        if (creatureRowNumber > -1)
        {
            obj_id helperMob = create.createCreature(argumentString, getLocation(self), true);
            attachScript(helperMob, "test.qa_ai_helper_attach");
            sendSystemMessageTestingOnly(self, "Helper Created.  Use radial menu.");
            dictionary creatureRow = dataTableGetRow(CREATURE_TABLE, creatureRowNumber);
            utils.setScriptVar(self, SCRIPTVAR + ".creatureDictionary", creatureRow);
        }
        else 
        {
            sendSystemMessageTestingOnly(self, "Creature name invalid.");
        }
    }
}
