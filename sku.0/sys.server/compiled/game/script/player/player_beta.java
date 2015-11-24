package script.player;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import java.lang.System;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Vector;
import java.lang.Math;
import script.library.sui;
import script.library.skill;
import script.library.pclib;
import script.library.utils;
import script.library.money;

public class player_beta extends script.base_script
{
    public player_beta()
    {
    }
    public static final String SKILL_WEAPON_RANGED_BASIC = "combat_ranged_weapons_basic";
    public static final String SKILL_WEAPON_MELEE_BASIC = "combat_melee_basic";
    public static final String SKILL_NOVICE_SCOUT = "outdoors_scout_novice";
    public static final String SKILL_NOVICE_MARKSMAN = "combat_marksman_novice";
    public static final String SKILL_NOVICE_BRAWLER = "combat_brawler_novice";
    public static final String SKILL_NOVICE_ENTERTAINER = "social_entertainer_novice";
    public static final String SKILL_NOVICE_MEDIC = "science_medic_novice";
    public static final String SKILL_NOVICE_ARTISAN = "crafting_artisan_novice";
    public int sendLoginMessage(obj_id self, dictionary params) throws InterruptedException
    {
        detachScript(self, "player.player_beta");
        return SCRIPT_CONTINUE;
    }
    public int OnAddedToWorld(obj_id self) throws InterruptedException
    {
        detachScript(self, "player.player_beta");
        return SCRIPT_CONTINUE;
    }
    public int OnLogout(obj_id self) throws InterruptedException
    {
        detachScript(self, "player.player_beta");
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        detachScript(self, "player.player_beta");
        return SCRIPT_CONTINUE;
    }
    public void giveItemsToPlayer(obj_id self) throws InterruptedException
    {
        obj_id inventory = getObjectInSlot(self, "inventory");
        if (inventory == null)
        {
            debugSpeakMsg(self, "inventory is null!");
            return;
        }
        String surveyTool = "mineral";
        boolean gaveItem = false;
        if (getSpecies(self) != SPECIES_WOOKIEE)
        {
            gaveItem = givePlayerArmor(self, inventory);
            if (!gaveItem)
            {
                debugSpeakMsg(self, "Armor granting failed!");
            }
        }
        if (!hasSkill(self, SKILL_WEAPON_RANGED_BASIC))
        {
        }
        boolean boolGaveRW = giveRangedWeapon(self, inventory);
        if (!boolGaveRW)
        {
            debugSpeakMsg(self, "Ranged Weapon granting failed!");
        }
        if (!hasSkill(self, SKILL_WEAPON_MELEE_BASIC))
        {
        }
        boolean boolGaveMW = giveMeleeWeapon(self, inventory);
        if (!boolGaveMW)
        {
            debugSpeakMsg(self, "Melee Weapon granting failed!");
        }
        boolean boolGaveMI = giveMusicalInstrument(self, inventory);
        if (!boolGaveMI)
        {
            debugSpeakMsg(self, "Musical Instrument granting failed!");
        }
        if (hasSkill(self, SKILL_NOVICE_SCOUT))
        {
            String toolType = "gas";
            int randType = rand(1, 4);
            switch (randType)
            {
                case 1:
                toolType = "gas";
                break;
                case 2:
                toolType = "liquid";
                break;
                case 3:
                toolType = "mineral";
                break;
                case 4:
                toolType = "moisture";
                break;
                default:
                break;
            }
            boolean boolGaveBlaster = giveRangedWeapon(self, inventory);
        }
        if (hasSkill(self, SKILL_NOVICE_MARKSMAN))
        {
            String rifleType = "dlt20a";
            int randRifle = rand(1, 4);
            switch (randRifle)
            {
                case 1:
                rifleType = "laser";
                break;
                case 2:
                rifleType = "dlt20";
                break;
                case 3:
                rifleType = "dlt20a";
                break;
                case 4:
                rifleType = "sg82";
                break;
                default:
                break;
            }
            boolean boolGaveBlaster = giveRangedWeapon(self, inventory);
            obj_id rifle = createObject("object/weapon/ranged/rifle/rifle_" + rifleType + ".iff", self, "");
        }
        if (hasSkill(self, SKILL_NOVICE_BRAWLER))
        {
            String swordType = "sword_01";
            int randSword = rand(1, 4);
            switch (randSword)
            {
                case 1:
                swordType = "01";
                break;
                case 2:
                swordType = "02";
                break;
                case 3:
                swordType = "blade_ryyk";
                break;
                case 4:
                swordType = "blade_rantok";
                break;
                default:
                break;
            }
            String polearmType = "sword_01";
            int randPole = rand(1, 5);
            switch (randPole)
            {
                case 1:
                polearmType = "controllerfp";
                break;
                case 2:
                polearmType = "staff_metal";
                break;
                case 3:
                polearmType = "staff_wood_s1";
                break;
                case 4:
                polearmType = "staff_wood_s2";
                break;
                case 5:
                polearmType = "vibrolance";
                break;
                default:
                break;
            }
            obj_id sword = createObject("object/weapon/melee/sword/sword_" + swordType + ".iff", self, "");
            obj_id polearm = createObject("object/weapon/melee/polearm/lance_" + polearmType + ".iff", self, "");
            obj_id belt = createObject("object/tangible/wearables/armor/padded/armor_padded_s01_belt.iff", self, "");
            obj_id lArm = createObject("object/tangible/wearables/armor/padded/armor_padded_s01_bracer_l.iff", self, "");
            obj_id rArm = createObject("object/tangible/wearables/armor/padded/armor_padded_s01_bracer_r.iff", self, "");
            obj_id lBicep = createObject("object/tangible/wearables/armor/padded/armor_padded_s01_bicep_l.iff", self, "");
            obj_id rBicep = createObject("object/tangible/wearables/armor/padded/armor_padded_s01_bicep_r.iff", self, "");
            obj_id gloves = createObject("object/tangible/wearables/armor/padded/armor_padded_s01_gloves.iff", self, "");
            obj_id helmet = createObject("object/tangible/wearables/armor/padded/armor_padded_s01_helmet.iff", self, "");
            obj_id chest = createObject("object/tangible/wearables/armor/padded/armor_padded_s01_chest_plate.iff", self, "");
            obj_id legs = createObject("object/tangible/wearables/armor/padded/armor_padded_s01_leggings.iff", self, "");
            obj_id boots = createObject("object/tangible/wearables/armor/padded/armor_padded_s01_boots.iff", self, "");
        }
        if (hasSkill(self, SKILL_NOVICE_ENTERTAINER))
        {
            String instrumentType = "bandfill";
            int randInst = rand(1, 7);
            switch (randInst)
            {
                case 1:
                instrumentType = "bandfill";
                break;
                case 2:
                instrumentType = "fanfar";
                break;
                case 3:
                instrumentType = "fizz";
                break;
                case 4:
                instrumentType = "kloo_horn";
                break;
                case 5:
                instrumentType = "mandoviol";
                break;
                case 6:
                instrumentType = "slitherhorn";
                break;
                case 7:
                instrumentType = "traz";
                break;
                default:
                break;
            }
            boolean boolGaveBlaster = giveRangedWeapon(self, inventory);
            obj_id imageKit = createObject("", self, "");
            obj_id instrument = createObject("object/tangible/instrument/" + instrumentType + ".iff", self, "");
            obj_id clothes = createObject("object/tangible/wearables/bodysuit/bodysuit_s15.iff", self, "");
        }
        if (hasSkill(self, SKILL_NOVICE_MEDIC))
        {
            String medType = "damage";
            int randMed = rand(1, 7);
            switch (randMed)
            {
                case 1:
                medType = "damage";
                break;
                case 2:
                medType = "sm_s1";
                break;
                case 3:
                medType = "wound";
                break;
                case 4:
                medType = "wound_action";
                break;
                case 5:
                medType = "wound_constitution";
                break;
                case 6:
                medType = "wound_health";
                break;
                case 7:
                medType = "wound_stamina";
                break;
                default:
                break;
            }
            boolean boolGaveBlaster = giveRangedWeapon(self, inventory);
            obj_id stimpack1 = createObject("object/tangible/medicine/stimpack_sm_s1.iff", self, "");
            obj_id stimpack2 = createObject("object/tangible/medicine/antidote_sm_s1.iff", self, "");
            obj_id medpack1 = createObject("object/tangible/medicine/medpack_" + medType + ".iff", self, "");
            obj_id medpack2 = createObject("object/tangible/medicine/medpack_" + medType + ".iff", self, "");
        }
        if (hasSkill(self, SKILL_NOVICE_ARTISAN))
        {
            boolean boolGaveBlaster = giveRangedWeapon(self, inventory);
            obj_id craftStation = createObject("object/tangible/crafting/station/generic_tool.iff", self, "");
        }
        return;
    }
    public boolean givePlayerArmor(obj_id self, obj_id inventory) throws InterruptedException
    {
        boolean gaveItem = true;
        return gaveItem;
    }
    public boolean giveRangedWeapon(obj_id self, obj_id inventory) throws InterruptedException
    {
        boolean gaveItem = false;
        obj_id weapon = obj_id.NULL_ID;
        int randType = rand(0, 10);
        switch (randType)
        {
            case 0:
            weapon = createObject("object/weapon/ranged/pistol/pistol_cdef.iff", inventory, "");
            if (weapon != null)
            {
                gaveItem = true;
            }
            break;
            case 1:
            weapon = createObject("object/weapon/ranged/pistol/pistol_d18.iff", inventory, "");
            if (weapon != null)
            {
                gaveItem = true;
            }
            break;
            case 2:
            weapon = createObject("object/weapon/ranged/pistol/pistol_dh17.iff", inventory, "");
            if (weapon != null)
            {
                gaveItem = true;
            }
            break;
            case 3:
            weapon = createObject("object/weapon/ranged/pistol/pistol_dl44.iff", inventory, "");
            if (weapon != null)
            {
                gaveItem = true;
            }
            break;
            case 4:
            weapon = createObject("object/weapon/ranged/pistol/pistol_dl44_metal.iff", inventory, "");
            if (weapon != null)
            {
                gaveItem = true;
            }
            break;
            case 5:
            weapon = createObject("object/weapon/ranged/pistol/pistol_dx2.iff", inventory, "");
            if (weapon != null)
            {
                gaveItem = true;
            }
            break;
            case 6:
            weapon = createObject("object/weapon/ranged/pistol/pistol_fwg5.iff", inventory, "");
            if (weapon != null)
            {
                gaveItem = true;
            }
            break;
            case 7:
            weapon = createObject("object/weapon/ranged/pistol/pistol_power5.iff", inventory, "");
            if (weapon != null)
            {
                gaveItem = true;
            }
            break;
            case 8:
            weapon = createObject("object/weapon/ranged/pistol/pistol_scout_blaster.iff", inventory, "");
            if (weapon != null)
            {
                gaveItem = true;
            }
            break;
            case 9:
            weapon = createObject("object/weapon/ranged/pistol/pistol_srcombat.iff", inventory, "");
            if (weapon != null)
            {
                gaveItem = true;
            }
            break;
            case 10:
            weapon = createObject("object/weapon/ranged/pistol/pistol_striker.iff", inventory, "");
            if (weapon != null)
            {
                gaveItem = true;
            }
            break;
            default:
            break;
        }
        if (weapon != obj_id.NULL_ID)
        {
            setAutoInsured(weapon);
        }
        return (gaveItem);
    }
    public boolean giveMeleeWeapon(obj_id self, obj_id inventory) throws InterruptedException
    {
        boolean gaveItem = false;
        obj_id weapon = obj_id.NULL_ID;
        int randType = rand(1, 4);
        switch (randType)
        {
            case 1:
            weapon = createObject("object/weapon/melee/knife/knife_dagger.iff", inventory, "");
            if (weapon != null)
            {
                gaveItem = true;
            }
            break;
            case 2:
            weapon = createObject("object/weapon/melee/knife/knife_stone.iff", inventory, "");
            if (weapon != null)
            {
                gaveItem = true;
            }
            break;
            case 3:
            weapon = createObject("object/weapon/melee/knife/knife_survival.iff", inventory, "");
            if (weapon != null)
            {
                gaveItem = true;
            }
            break;
            case 4:
            weapon = createObject("object/weapon/melee/knife/knife_vibroblade.iff", inventory, "");
            if (weapon != null)
            {
                gaveItem = true;
            }
            break;
            default:
            break;
        }
        if (weapon != obj_id.NULL_ID)
        {
            setAutoInsured(weapon);
        }
        return (gaveItem);
    }
    public boolean giveMusicalInstrument(obj_id self, obj_id inventory) throws InterruptedException
    {
        boolean gaveItem = false;
        obj_id instrument = obj_id.NULL_ID;
        int randType = rand(1, 3);
        switch (randType)
        {
            case 1:
            instrument = createObject("object/tangible/instrument/kloo_horn.iff", inventory, "");
            if (instrument != null)
            {
                gaveItem = true;
            }
            break;
            case 2:
            instrument = createObject("object/tangible/instrument/traz.iff", inventory, "");
            if (instrument != null)
            {
                gaveItem = true;
            }
            break;
            case 3:
            instrument = createObject("object/tangible/instrument/slitherhorn.iff", inventory, "");
            if (instrument != null)
            {
                gaveItem = true;
            }
            break;
            case 4:
            instrument = createObject("object/tangible/instrument/nalargon.iff", inventory, "");
            if (instrument != null)
            {
                gaveItem = true;
            }
            break;
            case 5:
            instrument = createObject("object/tangible/instrument/traz.iff", inventory, "");
            if (instrument != null)
            {
                gaveItem = true;
            }
            break;
            case 6:
            instrument = createObject("object/tangible/instrument/fanfar.iff", inventory, "");
            if (instrument != null)
            {
                gaveItem = true;
            }
            break;
            case 7:
            instrument = createObject("object/tangible/instrument/flute_droopy.iff", inventory, "");
            if (instrument != null)
            {
                gaveItem = true;
            }
            break;
            case 8:
            instrument = createObject("object/tangible/instrument/fizz.iff", inventory, "");
            if (instrument != null)
            {
                gaveItem = true;
            }
            break;
            case 9:
            instrument = createObject("object/tangible/instrument/bandfill.iff", inventory, "");
            if (instrument != null)
            {
                gaveItem = true;
            }
            break;
            case 10:
            instrument = createObject("object/tangible/instrument/ommni_box.iff", inventory, "");
            if (instrument != null)
            {
                gaveItem = true;
            }
            break;
            default:
            break;
        }
        if (instrument != obj_id.NULL_ID)
        {
            setAutoInsured(instrument);
        }
        return (gaveItem);
    }
    public int handleInventoryCheck(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_OVERRIDE;
        }
        obj_id[] items = params.getObjIdArray(pclib.DICT_ITEM_ID);
        if ((items == null) || (items.length == 0))
        {
            debugSpeakMsg(self, "handleInventoryCheck: no items at time of check");
        }
        else 
        {
            String[] report = new String[items.length];
            for (int i = 0; i < items.length; i++)
            {
                report[i] = "(" + items[i] + ") " + getName(items[i]);
            }
            sui.listbox(self, "Inventory Check Report", report);
        }
        return SCRIPT_CONTINUE;
    }
}
