package script.test;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.qa;
import script.library.sui;

public class qaweapon extends script.base_script
{
    public qaweapon()
    {
    }
    public static final String SCRIPTVAR = "qaweapon";
    public static final String[] WEAPON_DAMAGE = 
    {
        "1",
        "10",
        "50",
        "100",
        "500",
        "1000",
        "5000"
    };
    public static final String[] WEAPON_ELEMENT_TYPE = 
    {
        "None",
        "Heat",
        "Cold",
        "Acid",
        "Electric"
    };
    public static final String[] WEAPON_TYPE = 
    {
        "Pistol",
        "Carbine",
        "Rifle",
        "Unarmed",
        "1-Handed",
        "2-Handed",
        "Polearm",
        "Heavy Weapon",
        "Flame Thrower"
    };
    public static final int PISTOL = 0;
    public static final int CARBINE = 1;
    public static final int RIFLE = 2;
    public static final int UNARMED = 3;
    public static final int ONE_HANDED = 4;
    public static final int TWO_HANDED = 5;
    public static final int POLEARM = 6;
    public static final int HEAVY = 7;
    public static final int FLAMETHROWER = 8;
    public static final int DAMAGE_1 = 0;
    public static final int DAMAGE_10 = 1;
    public static final int DAMAGE_50 = 2;
    public static final int DAMAGE_100 = 3;
    public static final int DAMAGE_500 = 4;
    public static final int DAMAGE_1000 = 5;
    public static final int DAMAGE_5000 = 6;
    public static final int ELEMENT_0 = 0;
    public static final int ELEMENT_32 = 1;
    public static final int ELEMENT_64 = 2;
    public static final int ELEMENT_128 = 3;
    public static final int ELEMENT_256 = 4;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (isGod(self))
        {
            if (getGodLevel(self) < 10)
            {
                detachScript(self, "test.qaweapon");
                sendSystemMessage(self, "You do not have the appropriate access level to use this script.", null);
            }
        }
        else if (!isGod(self))
        {
            detachScript(self, "test.qaweapon");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        if (isGod(self))
        {
            if ((toLower(text)).equals(SCRIPTVAR))
            {
                weaponTypeMenu(self);
                return SCRIPT_OVERRIDE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleWeaponTypeOptions(obj_id self, dictionary params) throws InterruptedException
    {
        if (isGod(self))
        {
            obj_id player = sui.getPlayerId(params);
            int btn = sui.getIntButtonPressed(params);
            if (btn == sui.BP_CANCEL)
            {
                qa.removePlayer(player, SCRIPTVAR, "");
                return SCRIPT_CONTINUE;
            }
            if (btn == sui.BP_REVERT)
            {
                qa.qaToolMainMenu(player);
                utils.removeScriptVarTree(player, SCRIPTVAR);
                return SCRIPT_CONTINUE;
            }
            int idx = sui.getListboxSelectedRow(params);
            if (idx < 0)
            {
                qa.removePlayer(player, SCRIPTVAR, "");
                return SCRIPT_CONTINUE;
            }
            switch (idx)
            {
                case PISTOL:
                utils.setScriptVar(player, SCRIPTVAR + ".weaponTypeChoice", "Pistol");
                weaponDamageMenu(player);
                break;
                case CARBINE:
                utils.setScriptVar(player, SCRIPTVAR + ".weaponTypeChoice", "Carbine");
                weaponDamageMenu(player);
                break;
                case RIFLE:
                utils.setScriptVar(player, SCRIPTVAR + ".weaponTypeChoice", "Rifle");
                weaponDamageMenu(player);
                break;
                case UNARMED:
                utils.setScriptVar(player, SCRIPTVAR + ".weaponTypeChoice", "Unarmed");
                weaponDamageMenu(player);
                break;
                case ONE_HANDED:
                utils.setScriptVar(player, SCRIPTVAR + ".weaponTypeChoice", "1-Handed");
                weaponDamageMenu(player);
                break;
                case TWO_HANDED:
                utils.setScriptVar(player, SCRIPTVAR + ".weaponTypeChoice", "2-Handed");
                weaponDamageMenu(player);
                break;
                case POLEARM:
                utils.setScriptVar(player, SCRIPTVAR + ".weaponTypeChoice", "Polearm");
                weaponDamageMenu(player);
                break;
                case HEAVY:
                utils.setScriptVar(player, SCRIPTVAR + ".weaponTypeChoice", "Heavy Weapon");
                weaponDamageMenu(player);
                break;
                case FLAMETHROWER:
                utils.setScriptVar(player, SCRIPTVAR + ".weaponTypeChoice", "Flame Thrower");
                weaponDamageMenu(player);
                break;
                default:
                qa.removePlayer(player, SCRIPTVAR, "There was an error with your selection, Please try again.");
                weaponTypeMenu(self);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleWeaponLevelOptions(obj_id self, dictionary params) throws InterruptedException
    {
        if (isGod(self))
        {
            obj_id player = sui.getPlayerId(params);
            int btn = sui.getIntButtonPressed(params);
            if (btn == sui.BP_CANCEL)
            {
                qa.removePlayer(player, SCRIPTVAR, "");
                return SCRIPT_CONTINUE;
            }
            if (btn == sui.BP_REVERT)
            {
                weaponTypeMenu(player);
                return SCRIPT_CONTINUE;
            }
            int idx = sui.getListboxSelectedRow(params);
            if (idx < 0)
            {
                qa.removePlayer(player, SCRIPTVAR, "");
                return SCRIPT_CONTINUE;
            }
            switch (idx)
            {
                case DAMAGE_1:
                utils.setScriptVar(player, SCRIPTVAR + ".damageChoice", 1);
                elementalTypeMenu(player);
                break;
                case DAMAGE_10:
                utils.setScriptVar(player, SCRIPTVAR + ".damageChoice", 10);
                elementalTypeMenu(player);
                break;
                case DAMAGE_50:
                utils.setScriptVar(player, SCRIPTVAR + ".damageChoice", 50);
                elementalTypeMenu(player);
                break;
                case DAMAGE_100:
                utils.setScriptVar(player, SCRIPTVAR + ".damageChoice", 100);
                elementalTypeMenu(player);
                break;
                case DAMAGE_500:
                utils.setScriptVar(player, SCRIPTVAR + ".damageChoice", 500);
                elementalTypeMenu(player);
                break;
                case DAMAGE_1000:
                utils.setScriptVar(player, SCRIPTVAR + ".damageChoice", 1000);
                elementalTypeMenu(player);
                break;
                case DAMAGE_5000:
                utils.setScriptVar(player, SCRIPTVAR + ".damageChoice", 5000);
                elementalTypeMenu(player);
                break;
                default:
                qa.removePlayer(player, SCRIPTVAR, "There was an error with your selection, Please try again.");
                weaponTypeMenu(self);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleWeaponElementalOptions(obj_id self, dictionary params) throws InterruptedException
    {
        if (isGod(self))
        {
            obj_id player = sui.getPlayerId(params);
            int btn = sui.getIntButtonPressed(params);
            if (btn == sui.BP_CANCEL)
            {
                qa.removePlayer(player, SCRIPTVAR, "");
                return SCRIPT_CONTINUE;
            }
            if (btn == sui.BP_REVERT)
            {
                weaponDamageMenu(player);
                return SCRIPT_CONTINUE;
            }
            int idx = sui.getListboxSelectedRow(params);
            if (idx < 0)
            {
                qa.removePlayer(player, SCRIPTVAR, "");
                return SCRIPT_CONTINUE;
            }
            switch (idx)
            {
                case ELEMENT_0:
                utils.setScriptVar(player, SCRIPTVAR + ".elementChoice", 0);
                createQAWeapon(player);
                break;
                case ELEMENT_32:
                utils.setScriptVar(player, SCRIPTVAR + ".elementChoice", 32);
                elementalDamageMenu(player);
                break;
                case ELEMENT_64:
                utils.setScriptVar(player, SCRIPTVAR + ".elementChoice", 64);
                elementalDamageMenu(player);
                break;
                case ELEMENT_128:
                utils.setScriptVar(player, SCRIPTVAR + ".elementChoice", 128);
                elementalDamageMenu(player);
                break;
                case ELEMENT_256:
                utils.setScriptVar(player, SCRIPTVAR + ".elementChoice", 256);
                elementalDamageMenu(player);
                break;
                default:
                qa.removePlayer(player, SCRIPTVAR, "There was an error with your selection, Please try again.");
                weaponTypeMenu(self);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleWeaponElementalDamage(obj_id self, dictionary params) throws InterruptedException
    {
        if (isGod(self))
        {
            obj_id player = sui.getPlayerId(params);
            int btn = sui.getIntButtonPressed(params);
            if (btn == sui.BP_CANCEL)
            {
                qa.removePlayer(player, SCRIPTVAR, "");
                return SCRIPT_CONTINUE;
            }
            if (btn == sui.BP_REVERT)
            {
                elementalTypeMenu(player);
                return SCRIPT_CONTINUE;
            }
            int idx = sui.getListboxSelectedRow(params);
            if (idx < 0)
            {
                qa.removePlayer(player, SCRIPTVAR, "");
                return SCRIPT_CONTINUE;
            }
            switch (idx)
            {
                case DAMAGE_1:
                utils.setScriptVar(player, SCRIPTVAR + ".elementalDamage", 1);
                createQAWeapon(player);
                break;
                case DAMAGE_10:
                utils.setScriptVar(player, SCRIPTVAR + ".elementalDamage", 10);
                createQAWeapon(player);
                break;
                case DAMAGE_50:
                utils.setScriptVar(player, SCRIPTVAR + ".elementalDamage", 50);
                createQAWeapon(player);
                break;
                case DAMAGE_100:
                utils.setScriptVar(player, SCRIPTVAR + ".elementalDamage", 100);
                createQAWeapon(player);
                break;
                case DAMAGE_500:
                utils.setScriptVar(player, SCRIPTVAR + ".elementalDamage", 500);
                createQAWeapon(player);
                break;
                case DAMAGE_1000:
                utils.setScriptVar(player, SCRIPTVAR + ".elementalDamage", 1000);
                createQAWeapon(player);
                break;
                case DAMAGE_5000:
                utils.setScriptVar(player, SCRIPTVAR + ".elementalDamage", 5000);
                createQAWeapon(player);
                break;
                default:
                qa.removePlayer(player, SCRIPTVAR, "There was an error with your selection, Please try again.");
                weaponTypeMenu(self);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void weaponTypeMenu(obj_id player) throws InterruptedException
    {
        qa.refreshMenu(player, "-Weapon Type Select Menu-\nChoose the type of weapon you wish to use for testing.", "QA Test Weapon Tool", WEAPON_TYPE, "handleWeaponTypeOptions", SCRIPTVAR + ".pid", SCRIPTVAR + ".weaponTypeMenu", sui.OK_CANCEL_REFRESH);
    }
    public void weaponDamageMenu(obj_id player) throws InterruptedException
    {
        qa.refreshMenu(player, "-Weapon Damage Select Menu-\nSelect the amount of damage you want the weapon to have.\nNOTE: The Min/Max damage of all weapons will be the same.", "QA Test Weapon Tool", WEAPON_DAMAGE, "handleWeaponLevelOptions", SCRIPTVAR + ".pid", SCRIPTVAR + ".damageMenu", sui.OK_CANCEL_REFRESH);
    }
    public void elementalTypeMenu(obj_id player) throws InterruptedException
    {
        qa.refreshMenu(player, "-Weapon Damage Select Menu-\nSelect the elemental damage type you want the weapon to use.", "QA Test Weapon Tool", WEAPON_ELEMENT_TYPE, "handleWeaponElementalOptions", SCRIPTVAR + ".pid", SCRIPTVAR + ".elementalTypeMenu", sui.OK_CANCEL_REFRESH);
    }
    public void elementalDamageMenu(obj_id player) throws InterruptedException
    {
        qa.refreshMenu(player, "-Weapon Damage Select Menu-\nSelect the amount of elemental damage you want add to the weapon.\nNOTE: The Min/Max damage of all weapons will be the same.", "QA Test Weapon Tool", WEAPON_DAMAGE, "handleWeaponElementalDamage", SCRIPTVAR + ".pid", SCRIPTVAR + ".elementalTypeMenu", sui.OK_CANCEL_REFRESH);
    }
    public void checkInventory(obj_id player) throws InterruptedException
    {
        obj_id inventory = utils.getInventoryContainer(player);
        obj_id[] invItems = getContents(inventory);
        if (invItems.length > 79)
        {
            sendSystemMessageTestingOnly(player, "You do not have enough space in your inventory, please delete some items and try again.");
        }
    }
    public void createQAWeapon(obj_id player) throws InterruptedException
    {
        if (utils.hasScriptVar(player, SCRIPTVAR + ".damageChoice") && utils.hasScriptVar(player, SCRIPTVAR + ".weaponTypeChoice"))
        {
            obj_id inventory = utils.getInventoryContainer(player);
            checkInventory(player);
            int intDamage = utils.getIntScriptVar(player, SCRIPTVAR + ".damageChoice");
            int intElementalType = utils.getIntScriptVar(player, SCRIPTVAR + ".elementChoice");
            int intElementalDamage = utils.getIntScriptVar(player, SCRIPTVAR + ".elementalDamage");
            float speed = 0.00f;
            String weaponObject = "";
            String weaponType = utils.getStringScriptVar(player, SCRIPTVAR + ".weaponTypeChoice");
            if (weaponType.equals("Pistol"))
            {
                speed = 0.40f;
                weaponObject = "object/weapon/ranged/pistol/pistol_cdef.iff";
            }
            else if (weaponType.equals("Carbine"))
            {
                speed = 0.60f;
                weaponObject = "object/weapon/ranged/carbine/carbine_cdef.iff";
            }
            else if (weaponType.equals("Rifle"))
            {
                speed = 0.80f;
                weaponObject = "object/weapon/ranged/rifle/rifle_cdef.iff";
            }
            if (weaponType.equals("Flame Thrower"))
            {
                speed = 0.25f;
                weaponObject = "object/weapon/ranged/rifle/rifle_flame_thrower.iff";
            }
            else if (weaponType.equals("Unarmed") || weaponType.equals("1-Handed") || weaponType.equals("2-Handed") || weaponType.equals("Polearm") || weaponType.equals("Heavy Weapon"))
            {
                speed = 1.00f;
                if (weaponType.equals("Heavy Weapon"))
                {
                    weaponObject = "object/weapon/ranged/heavy/heavy_rocket_launcher.iff";
                }
                else if (weaponType.equals("Unarmed"))
                {
                    weaponObject = "object/weapon/melee/special/vibroknuckler.iff";
                }
                else if (weaponType.equals("1-Handed"))
                {
                    weaponObject = "object/weapon/melee/knife/knife_dagger.iff";
                }
                else if (weaponType.equals("2-Handed"))
                {
                    weaponObject = "object/weapon/melee/2h_sword/2h_sword_cleaver.iff";
                }
                else if (weaponType.equals("Polearm"))
                {
                    weaponObject = "object/weapon/melee/polearm/lance_staff_wood_s1.iff";
                }
            }
            obj_id newObj = createObject(weaponObject, inventory, "");
            setObjVar(newObj, "dynamic_item.intLevelRequired", 1);
            setObjVar(newObj, "noTrade", true);
            setObjVar(newObj, "QAWeapon", 1);
            setWeaponAttackSpeed(newObj, speed);
            setWeaponMaxDamage(newObj, intDamage);
            setWeaponMinDamage(newObj, intDamage);
            setName(newObj, "QA Weapon - Testing Use Only");
            if (intElementalType > 0)
            {
                setWeaponElementalDamage(newObj, intElementalType, intElementalDamage);
            }
            sendSystemMessageTestingOnly(player, "The QAWeapon has been placed in your backpack.");
            qa.removePlayer(player, SCRIPTVAR, "");
        }
        else 
        {
            sendSystemMessageTestingOnly(player, "There was an error with the QA Test Weapon Tool - Please Try Again.");
            qa.removePlayer(player, SCRIPTVAR, "");
            weaponTypeMenu(player);
        }
    }
}
