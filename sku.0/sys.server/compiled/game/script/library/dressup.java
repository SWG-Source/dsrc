package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.hue;
import script.ai.ai_combat;

public class dressup extends script.base_script
{
    public dressup()
    {
    }
    public static final String NPC_DATATABLE = "datatables/npc_customization/npc.iff";
    public static final String CREATURE_TABLE = "datatables/mob/creatures.iff";
    public static final String STORMTROOPER = "datatables/npc_customization/clothing/stormtrooper.iff";
    public static void dressNpc(obj_id npc, String clothesTable) throws InterruptedException
    {
        dressNpc(npc, clothesTable, false);
    }
    public static void dressNpc(obj_id npc, String clothesTable, boolean override) throws InterruptedException
    {
        if (!override && !isClothesWearing(npc))
        {
            hue.randomizeObject(npc);
            return;
        }
        clothesTable = "datatables/npc_customization/clothing/" + clothesTable + ".iff";
        int columns = dataTableGetNumColumns(clothesTable);
        if (columns == 0)
        {
            clothesTable = "datatables/npc_customization/townperson_male_clothes.iff";
            columns = dataTableGetNumColumns(clothesTable);
        }
        PROFILER_START("dressup.dressNpc.loop." + clothesTable + "." + columns);
        int intX = 1;
        while (intX <= columns)
        {
            PROFILER_START("dataTableGetStringColumnNoDefaults");
            String[] clothesList = dataTableGetStringColumnNoDefaults(clothesTable, intX - 1);
            PROFILER_STOP("dataTableGetStringColumnNoDefaults");
            PROFILER_START("innerLoop");
            int clothesNum = rand(0, clothesList.length - 1);
            String clothes = clothesList[clothesNum];
            int columnNum = intX - 1;
            if (clothes == null)
            {
                debugServerConsoleMsg(npc, "LIBRARY.CREATE DRESSNPC ERROR: row was " + clothesNum + " and column was " + columnNum);
            }
            else if (clothes.equals(""))
            {
                debugServerConsoleMsg(npc, "LIBRARY.CREATE DRESSNPC ERROR: row was " + clothesNum + " and column was " + columnNum);
            }
            else 
            {
                PROFILER_START("createObject");
                obj_id newClothes = createObject(clothes, npc, "");
                PROFILER_STOP("createObject");
                if (newClothes == null)
                {
                    debugServerConsoleMsg(npc, "LIBRARY.CREATE DRESSNPC ERROR: unable to create " + clothes + " for npc " + getName(npc) + " objid: " + npc);
                }
                else 
                {
                    setSpecificHue(newClothes, intX, clothesNum, clothesTable);
                }
            }
            intX = intX + 3;
            PROFILER_STOP("innerLoop");
        }
        PROFILER_STOP("dressup.dressNpc.loop." + clothesTable + "." + columns);
        PROFILER_START("dressup.dressNpc.conclude" + clothesTable);
        if (!clothesTable.equals(STORMTROOPER))
        {
            PROFILER_START("giveHair");
            giveHair(npc);
            PROFILER_STOP("giveHair");
            PROFILER_START("hue.randomizeObject");
            hue.randomizeObject(npc);
            PROFILER_STOP("hue.randomizeObject");
        }
        else 
        {
            int designation = rand(1, 5);
            String StName = "TK-";
            switch (designation)
            {
                case 1:
                StName = "TK-";
                break;
                case 2:
                StName = "GK-";
                break;
                case 3:
                StName = "RK-";
                break;
                case 4:
                StName = "LK-";
                break;
                case 5:
                StName = "VK-";
                break;
            }
            setName(npc, StName + rand(1, 820));
        }
        PROFILER_STOP("dressup.dressNpc.conclude" + clothesTable);
    }
    public static void equipNpcWeapon(obj_id npc, String weaponTable) throws InterruptedException
    {
        if (weaponTable.equals("none"))
        {
            return;
        }
        obj_id npcInventory = getObjectInSlot(npc, "inventory");
        if (npcInventory == null)
        {
            return;
        }
        weaponTable = "datatables/npc_customization/weapon/" + weaponTable + ".iff";
        PROFILER_START("dressup.equipNpcWeapon." + weaponTable);
        String primaryWeaponTemplateName = chooseRandomWeapon(weaponTable, 0);
        if (primaryWeaponTemplateName != null)
        {
            primaryWeaponTemplateName = "object/weapon/" + primaryWeaponTemplateName;
        }
        String secondaryWeaponTemplateName = chooseRandomWeapon(weaponTable, 1);
        if (secondaryWeaponTemplateName != null)
        {
            secondaryWeaponTemplateName = "object/weapon/" + secondaryWeaponTemplateName;
        }
        String grenadeWeaponTemplateName = chooseRandomWeapon(weaponTable, 2);
        if (grenadeWeaponTemplateName != null)
        {
            grenadeWeaponTemplateName = "object/weapon/" + grenadeWeaponTemplateName;
        }
        if (grenadeWeaponTemplateName != null)
        {
            setObjVar(npc, "ai.grenadeType", grenadeWeaponTemplateName);
        }
        if (secondaryWeaponTemplateName != null)
        {
            obj_id secondaryWeapon = createObject(secondaryWeaponTemplateName, npcInventory, "");
            setWeaponDamage(npc, secondaryWeapon);
        }
        if (primaryWeaponTemplateName != null)
        {
            obj_id primaryWeapon = createObject(primaryWeaponTemplateName, npcInventory, "");
            setWeaponDamage(npc, primaryWeapon);
        }
        PROFILER_STOP("dressup.equipNpcWeapon." + weaponTable);
    }
    public static String chooseRandomWeapon(String weaponTable, int columnNumber) throws InterruptedException
    {
        if ((dataTableGetNumColumns(weaponTable) - 1) < columnNumber)
        {
            return null;
        }
        String[] weaponList = dataTableGetStringColumnNoDefaults(weaponTable, columnNumber);
        if (weaponList != null)
        {
            if (weaponList.length < 1)
            {
                return null;
            }
            else 
            {
                return weaponList[rand(0, (weaponList.length - 1))];
            }
        }
        else 
        {
            return null;
        }
    }
    public static void hueClothes(obj_id newClothes, String clothes) throws InterruptedException
    {
        custom_var[] allVars = getAllCustomVars(newClothes);
        if (allVars == null)
        {
            return;
        }
        PROFILER_START("dressup.hueClothes.loop." + allVars.length);
        for (int i = 0; i < allVars.length; i++)
        {
            custom_var cv = allVars[i];
            if (cv.isPalColor())
            {
                ranged_int_custom_var ri = (ranged_int_custom_var)cv;
                int min = ri.getMinRangeInclusive();
                int max = ri.getMaxRangeInclusive();
                int randVal = rand(min, max);
                if (max > 128)
                {
                    max = max - 128;
                }
                ri.setValue(randVal);
            }
        }
        PROFILER_STOP("dressup.hueClothes.loop." + allVars.length);
    }
    public static void setSpecificHue(obj_id newClothes, int col, int row, String datatable) throws InterruptedException
    {
        PROFILER_START("dressup.setSpecificHue");
        int minColor = dataTableGetInt(datatable, row, col);
        if (minColor == 0)
        {
            hueClothes(newClothes, datatable);
        }
        else 
        {
            int maxColor = dataTableGetInt(datatable, row, col + 1);
            hue.setColor(newClothes, 1, minColor);
            hue.setColor(newClothes, 2, maxColor);
        }
        PROFILER_STOP("dressup.setSpecificHue");
        return;
    }
    public static void setWeaponDamage(obj_id npc, obj_id weapon) throws InterruptedException
    {
        if (npc == null)
        {
            return;
        }
        if (weapon == null)
        {
            return;
        }
        dictionary creatureDict = utils.dataTableGetRow(CREATURE_TABLE, getCreatureName(npc));
        if (creatureDict == null)
        {
            return;
        }
        int intMaxDamage = creatureDict.getInt("maxDamage");
        int intMinDamage = creatureDict.getInt("minDamage");
        float fltWeaponSpeed = (float)creatureDict.getInt("attackSpeed");
        setWeaponAttackSpeed(weapon, fltWeaponSpeed);
        setWeaponMaxDamage(weapon, intMaxDamage);
        setWeaponMinDamage(weapon, intMinDamage);
        weapons.setWeaponData(weapon);
    }
    public static boolean isClothesWearing(obj_id npc) throws InterruptedException
    {
        String tempName = getTemplateName(npc);
        if (tempName.indexOf("wookiee") != -1)
        {
            return false;
        }
        else if (tempName.indexOf("ithorian") != -1)
        {
            return false;
        }
        else if (tempName.indexOf("chadra") != -1)
        {
            return false;
        }
        else if (tempName.indexOf("gungan") != -1)
        {
            return false;
        }
        return true;
    }
    public static void giveHair(obj_id npc) throws InterruptedException
    {
        PROFILER_START("dressup.giveHair");
        if (npc == null)
        {
            PROFILER_STOP("dressup.giveHair");
            return;
        }
        String template = getTemplateName(npc);
        String hair_table = "datatables/npc_customization/all_hair.iff";
        int vendor_idx = template.indexOf("/vendor");
        if (vendor_idx > -1)
        {
            template = template.substring(0, vendor_idx) + template.substring(vendor_idx + 7);
        }
        if (!dataTableHasColumn(hair_table, template))
        {
            PROFILER_STOP("dressup.giveHair");
            return;
        }
        String[] hairList = dataTableGetStringColumnNoDefaults(hair_table, template);
        if (hairList == null)
        {
            PROFILER_STOP("dressup.giveHair");
            return;
        }
        else if (hairList.length < 1)
        {
            PROFILER_STOP("dressup.giveHair");
            return;
        }
        int hairChoice = rand(0, hairList.length - 1);
        String hair = hairList[hairChoice];
        obj_id setHair = createObject(hair, npc, "");
        hueClothes(setHair, hair);
        PROFILER_STOP("dressup.giveHair");
        return;
    }
    public static boolean setFactionRecruiter(obj_id npc, String faction) throws InterruptedException
    {
        if (npc == null || npc == obj_id.NULL_ID)
        {
            return false;
        }
        if (faction == null)
        {
            return false;
        }
        setObjVar(npc, "faction_recruiter.faction", faction);
        attachScript(npc, "npc.faction_recruiter.faction_recruiter");
        return true;
    }
}
