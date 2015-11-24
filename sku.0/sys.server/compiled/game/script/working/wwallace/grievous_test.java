package script.working.wwallace;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.combat;
import script.library.sui;
import script.library.utils;
import script.library.money;
import script.library.pet_lib;
import script.library.ai_lib;
import script.library.skill;
import script.library.hue;
import script.library.space_transition;
import script.library.datatable;
import script.library.xp;
import script.library.space_combat;
import script.library.space_create;
import script.library.armor;
import script.library.weapons;

public class grievous_test extends script.base_script
{
    public grievous_test()
    {
    }
    public static final String SKILL_TBL = "datatables/skill/skills.iff";
    public static final float CRAFTED_QUALITY = 0.85f;
    public static final float CONDITION = 1.0f;
    public static final float GENERAL_PROTECTION = 0.94f;
    public static final String[] BRAWLER = 
    {
        "combat_brawler_novice",
        "combat_brawler_unarmed_01",
        "combat_brawler_unarmed_02",
        "combat_brawler_unarmed_03",
        "combat_brawler_unarmed_04",
        "combat_brawler_1handmelee_01",
        "combat_brawler_1handmelee_02",
        "combat_brawler_1handmelee_03",
        "combat_brawler_1handmelee_04",
        "combat_brawler_2handmelee_01",
        "combat_brawler_2handmelee_02",
        "combat_brawler_2handmelee_03",
        "combat_brawler_2handmelee_04",
        "combat_brawler_polearm_01",
        "combat_brawler_polearm_02",
        "combat_brawler_polearm_03",
        "combat_brawler_polearm_04",
        "combat_brawler_master"
    };
    public static final String[] MARKSMAN = 
    {
        "combat_marksman_novice",
        "combat_marksman_rifle_01",
        "combat_marksman_rifle_02",
        "combat_marksman_rifle_03",
        "combat_marksman_rifle_04",
        "combat_marksman_pistol_01",
        "combat_marksman_pistol_02",
        "combat_marksman_pistol_03",
        "combat_marksman_pistol_04",
        "combat_marksman_carbine_01",
        "combat_marksman_carbine_02",
        "combat_marksman_carbine_03",
        "combat_marksman_carbine_04",
        "combat_marksman_support_01",
        "combat_marksman_support_02",
        "combat_marksman_support_03",
        "combat_marksman_support_04",
        "combat_marksman_master"
    };
    public static final String[] COMMANDO = 
    {
        "combat_commando_novice",
        "combat_commando_heavyweapon_accuracy_01",
        "combat_commando_heavyweapon_accuracy_02",
        "combat_commando_heavyweapon_accuracy_03",
        "combat_commando_heavyweapon_accuracy_04",
        "combat_commando_heavyweapon_speed_01",
        "combat_commando_heavyweapon_speed_02",
        "combat_commando_heavyweapon_speed_03",
        "combat_commando_heavyweapon_speed_04",
        "combat_commando_thrownweapon_01",
        "combat_commando_thrownweapon_02",
        "combat_commando_thrownweapon_03",
        "combat_commando_thrownweapon_04",
        "combat_commando_support_01",
        "combat_commando_support_02",
        "combat_commando_support_03",
        "combat_commando_support_04",
        "combat_commando_master"
    };
    public static final String[] SCOUT = 
    {
        "outdoors_scout_novice",
        "outdoors_scout_movement_01",
        "outdoors_scout_movement_02",
        "outdoors_scout_movement_03",
        "outdoors_scout_movement_04",
        "outdoors_scout_tools_01",
        "outdoors_scout_tools_02",
        "outdoors_scout_tools_03",
        "outdoors_scout_tools_04",
        "outdoors_scout_harvest_01",
        "outdoors_scout_harvest_02",
        "outdoors_scout_harvest_03",
        "outdoors_scout_harvest_04",
        "outdoors_scout_camp_01",
        "outdoors_scout_camp_02",
        "outdoors_scout_camp_03",
        "outdoors_scout_camp_04",
        "outdoors_scout_master"
    };
    public static final String[] BOUNTY_HUNTER = 
    {
        "combat_bountyhunter_novice",
        "combat_bountyhunter_investigation_01",
        "combat_bountyhunter_investigation_02",
        "combat_bountyhunter_investigation_03",
        "combat_bountyhunter_investigation_04",
        "combat_bountyhunter_droidcontrol_01",
        "combat_bountyhunter_droidcontrol_02",
        "combat_bountyhunter_droidcontrol_03",
        "combat_bountyhunter_droidcontrol_04",
        "combat_bountyhunter_droidresponse_01",
        "combat_bountyhunter_droidresponse_02",
        "combat_bountyhunter_droidresponse_03",
        "combat_bountyhunter_droidresponse_04",
        "combat_bountyhunter_support_01",
        "combat_bountyhunter_support_02",
        "combat_bountyhunter_support_03",
        "combat_bountyhunter_support_04",
        "combat_bountyhunter_master"
    };
    public static final String[] CARBINEER = 
    {
        "combat_carbine_novice",
        "combat_carbine_accuracy_01",
        "combat_carbine_accuracy_02",
        "combat_carbine_accuracy_03",
        "combat_carbine_accuracy_04",
        "combat_carbine_speed_01",
        "combat_carbine_speed_02",
        "combat_carbine_speed_03",
        "combat_carbine_speed_04",
        "combat_carbine_ability_01",
        "combat_carbine_ability_02",
        "combat_carbine_ability_03",
        "combat_carbine_ability_04",
        "combat_carbine_support_01",
        "combat_carbine_support_02",
        "combat_carbine_support_03",
        "combat_carbine_support_04",
        "combat_carbine_master"
    };
    public static final String[] TKA = 
    {
        "combat_unarmed_novice",
        "combat_unarmed_accuracy_01",
        "combat_unarmed_accuracy_02",
        "combat_unarmed_accuracy_03",
        "combat_unarmed_accuracy_04",
        "combat_unarmed_speed_01",
        "combat_unarmed_speed_02",
        "combat_unarmed_speed_03",
        "combat_unarmed_speed_04",
        "combat_unarmed_ability_01",
        "combat_unarmed_ability_02",
        "combat_unarmed_ability_03",
        "combat_unarmed_ability_04",
        "combat_unarmed_support_01",
        "combat_unarmed_support_02",
        "combat_unarmed_support_03",
        "combat_unarmed_support_04",
        "combat_unarmed_master"
    };
    public static final String[] PIKEMAN = 
    {
        "combat_polearm_novice",
        "combat_polearm_accuracy_01",
        "combat_polearm_accuracy_02",
        "combat_polearm_accuracy_03",
        "combat_polearm_accuracy_04",
        "combat_polearm_speed_01",
        "combat_polearm_speed_02",
        "combat_polearm_speed_03",
        "combat_polearm_speed_04",
        "combat_polearm_ability_01",
        "combat_polearm_ability_02",
        "combat_polearm_ability_03",
        "combat_polearm_ability_04",
        "combat_polearm_support_01",
        "combat_polearm_support_02",
        "combat_polearm_support_03",
        "combat_polearm_support_04",
        "combat_polearm_master"
    };
    public static final String[] MEDIC = 
    {
        "science_medic_novice",
        "science_medic_injury_01",
        "science_medic_injury_02",
        "science_medic_injury_03",
        "science_medic_injury_04",
        "science_medic_injury_speed_01",
        "science_medic_injury_speed_02",
        "science_medic_injury_speed_03",
        "science_medic_injury_speed_04",
        "science_medic_ability_01",
        "science_medic_ability_02",
        "science_medic_ability_03",
        "science_medic_ability_04",
        "science_medic_crafting_01",
        "science_medic_crafting_02",
        "science_medic_crafting_03",
        "science_medic_crafting_04",
        "science_medic_master"
    };
    public static final String[] DOCTOR = 
    {
        "science_doctor_novice",
        "science_doctor_wound_speed_01",
        "science_doctor_wound_speed_02",
        "science_doctor_wound_speed_03",
        "science_doctor_wound_speed_04",
        "science_doctor_wound_01",
        "science_doctor_wound_02",
        "science_doctor_wound_03",
        "science_doctor_wound_04",
        "science_doctor_ability_01",
        "science_doctor_ability_02",
        "science_doctor_ability_03",
        "science_doctor_ability_04",
        "science_doctor_support_01",
        "science_doctor_support_02",
        "science_doctor_support_03",
        "science_doctor_support_04",
        "science_doctor_master"
    };
    public static final String[] ARMOR_SET_RECON = 
    {
        "marauder/armor_marauder_s02_bicep_l.iff",
        "marauder/armor_marauder_s02_bicep_r.iff",
        "marauder/armor_marauder_s02_boots.iff",
        "marauder/armor_marauder_s02_bracer_l.iff",
        "marauder/armor_marauder_s02_bracer_r.iff",
        "marauder/armor_marauder_s02_chest_plate.iff",
        "marauder/armor_marauder_s02_gloves.iff",
        "marauder/armor_marauder_s02_helmet.iff",
        "marauder/armor_marauder_s02_leggings.iff"
    };
    public static final String[] ARMOR_SET_BATTLE = 
    {
        "marauder/armor_marauder_s01_bicep_l.iff",
        "marauder/armor_marauder_s01_bicep_r.iff",
        "marauder/armor_marauder_s01_boots.iff",
        "marauder/armor_marauder_s01_bracer_l.iff",
        "marauder/armor_marauder_s01_bracer_r.iff",
        "marauder/armor_marauder_s01_chest_plate.iff",
        "marauder/armor_marauder_s01_gloves.iff",
        "marauder/armor_marauder_s01_helmet.iff",
        "marauder/armor_marauder_s01_leggings.iff"
    };
    public static final String[] ARMOR_SET_BATTLE_2 = 
    {
        "ris/armor_ris_bicep_l.iff",
        "ris/armor_ris_bracer_l.iff",
        "ris/armor_ris_gloves.iff",
        "ris/armor_ris_bicep_r.iff",
        "ris/armor_ris_bracer_r.iff",
        "ris/armor_ris_helmet.iff",
        "ris/armor_ris_boots.iff",
        "ris/armor_ris_chest_plate.iff",
        "ris/armor_ris_leggings.iff"
    };
    public static final String[] ARMOR_SET_BATTLE_3 = 
    {
        "padded/armor_padded_s01_bicep_l.iff",
        "padded/armor_padded_s01_bicep_r.iff",
        "padded/armor_padded_s01_boots.iff",
        "padded/armor_padded_s01_bracer_l.iff",
        "padded/armor_padded_s01_bracer_r.iff",
        "padded/armor_padded_s01_chest_plate.iff",
        "padded/armor_padded_s01_gloves.iff",
        "padded/armor_padded_s01_helmet.iff",
        "padded/armor_padded_s01_leggings.iff"
    };
    public static final String[] ARMOR_SET_ASSAULT = 
    {
        "bounty_hunter/armor_bounty_hunter_crafted_bicep_l.iff",
        "bounty_hunter/armor_bounty_hunter_crafted_bicep_r.iff",
        "bounty_hunter/armor_bounty_hunter_crafted_boots.iff",
        "bounty_hunter/armor_bounty_hunter_crafted_bracer_l.iff",
        "bounty_hunter/armor_bounty_hunter_crafted_bracer_r.iff",
        "bounty_hunter/armor_bounty_hunter_crafted_chest_plate.iff",
        "bounty_hunter/armor_bounty_hunter_crafted_gloves.iff",
        "bounty_hunter/armor_bounty_hunter_crafted_helmet.iff",
        "bounty_hunter/armor_bounty_hunter_crafted_leggings.iff"
    };
    public static final String[] ARMOR_SET_ASSAULT_ITHORIAN = 
    {
        "ithorian_sentinel/ith_armor_s03_bicep_l.iff",
        "ithorian_sentinel/ith_armor_s03_chest_plate.iff",
        "ithorian_sentinel/ith_armor_s03_bicep_r.iff",
        "ithorian_sentinel/ith_armor_s03_gloves.iff",
        "ithorian_sentinel/ith_armor_s03_boots.iff",
        "ithorian_sentinel/ith_armor_s03_helmet.iff",
        "ithorian_sentinel/ith_armor_s03_bracer_l.iff",
        "ithorian_sentinel/ith_armor_s03_leggings.iff",
        "ithorian_sentinel/ith_armor_s03_bracer_r.iff"
    };
    public static final String ARMOR_SET_PREFIX = "object/tangible/wearables/armor/";
    public int OnSpeaking(obj_id self, String strText) throws InterruptedException
    {
        String[] strCommands = split(strText, ' ');
        obj_id pInv = utils.getInventoryContainer(self);
        if (strCommands[0].equals("teleport"))
        {
            obj_id top = getTopMostContainer(self);
            String whereTo = strCommands[1];
            location randomLoc = getGoodLocation(top, whereTo);
            obj_id cell = getCellId(top, whereTo);
            utils.warpPlayer(self, randomLoc);
            return SCRIPT_CONTINUE;
        }
        if (strCommands[0].equals("spawnEncounter"))
        {
            location selfLoc = getLocation(self);
            obj_id encounter = createObject("object/tangible/theme_park/myyydril/myyydril_grievous_encounter_manager.iff", selfLoc);
            sendSystemMessageTestingOnly(self, "Encounter ID is " + encounter);
        }
        if (strCommands[0].equals("clearInv"))
        {
            obj_id[] contents = getInventoryAndEquipment(self);
            for (int i = 0; i < contents.length; i++)
            {
                destroyObject(contents[i]);
            }
            return SCRIPT_CONTINUE;
        }
        if (strCommands[0].equals("makeCharacterOne"))
        {
            for (int i = 0; i < BRAWLER.length; i++)
            {
                skill.grantSkill(self, BRAWLER[i]);
            }
            for (int i = 0; i < TKA.length; i++)
            {
                skill.grantSkill(self, TKA[i]);
            }
        }
        if (strCommands[0].equals("makeCharacterTwo"))
        {
            for (int i = 0; i < BRAWLER.length; i++)
            {
                skill.grantSkill(self, BRAWLER[i]);
            }
            for (int i = 0; i < PIKEMAN.length; i++)
            {
                skill.grantSkill(self, PIKEMAN[i]);
            }
            issueBattleArmorSet(self, ARMOR_SET_BATTLE);
            weapons.createWeapon("object/weapon/melee/polearm/lance_massassi.iff", pInv, weapons.VIA_TEMPLATE, CRAFTED_QUALITY);
        }
        if (strCommands[0].equals("makeCharacterThree"))
        {
            String[] allTemplates = new String[]
            {
                "bactainfusion",
                "bactajab",
                "bactashot",
                "bactaspray",
                "bactatoss",
                "deuteriumtoss",
                "disinfect",
                "endorphineinjection",
                "neurotoxin",
                "nutrientinjection",
                "stabilizer",
                "thyroidrupture",
                "traumatize"
            };
            for (int i = 0; i < MEDIC.length; i++)
            {
                skill.grantSkill(self, MEDIC[i]);
            }
            for (int i = 0; i < DOCTOR.length; i++)
            {
                skill.grantSkill(self, DOCTOR[i]);
            }
            issueBattleArmorSet(self, ARMOR_SET_BATTLE_2);
            obj_id stimd = createObject("object/tangible/medicine/instant_stimpack/stimpack_d.iff", pInv, "");
            if (isIdValid(stimd))
            {
                setCount(stimd, 350);
                setObjVar(stimd, "healing.power", 1200);
            }
            for (int i = 0; i < allTemplates.length; i++)
            {
                obj_id enh = createObject("object/tangible/medicine/enhancer/enhancer_" + allTemplates[i] + ".iff", pInv, "");
                if (isIdValid(enh))
                {
                    setCount(enh, 150);
                    setObjVar(enh, "healing.enhancement", 700);
                }
            }
        }
        if (strCommands[0].equals("makeCharacterFour"))
        {
            for (int i = 0; i < BRAWLER.length; i++)
            {
                skill.grantSkill(self, BRAWLER[i]);
            }
            for (int i = 0; i < MARKSMAN.length; i++)
            {
                skill.grantSkill(self, MARKSMAN[i]);
            }
            for (int i = 0; i < COMMANDO.length; i++)
            {
                skill.grantSkill(self, COMMANDO[i]);
            }
            issueAssaultArmorSet(self, ARMOR_SET_ASSAULT);
            weapons.createWeapon("object/weapon/ranged/heavy/heavy_lightning_beam.iff", pInv, weapons.VIA_TEMPLATE, CRAFTED_QUALITY);
        }
        if (strCommands[0].equals("makeCharacterFive"))
        {
            for (int i = 0; i < MARKSMAN.length; i++)
            {
                skill.grantSkill(self, MARKSMAN[i]);
            }
            for (int i = 0; i < CARBINEER.length; i++)
            {
                skill.grantSkill(self, CARBINEER[i]);
            }
            issueBattleArmorSet(self, ARMOR_SET_BATTLE_3);
            weapons.createWeapon("object/weapon/ranged/carbine/carbine_elite.iff", pInv, weapons.VIA_TEMPLATE, CRAFTED_QUALITY);
        }
        if (strCommands[0].equals("makeCharacterSix"))
        {
            for (int i = 0; i < MARKSMAN.length; i++)
            {
                skill.grantSkill(self, MARKSMAN[i]);
            }
            for (int i = 0; i < SCOUT.length; i++)
            {
                skill.grantSkill(self, SCOUT[i]);
            }
            for (int i = 0; i < BOUNTY_HUNTER.length; i++)
            {
                skill.grantSkill(self, BOUNTY_HUNTER[i]);
            }
            issueAssaultArmorSet(self, ARMOR_SET_ASSAULT_ITHORIAN);
            weapons.createWeapon("object/weapon/ranged/carbine/carbine_czerka_dart.iff", pInv, weapons.VIA_TEMPLATE, CRAFTED_QUALITY);
        }
        return SCRIPT_CONTINUE;
    }
    public void issueReconArmorSet(obj_id player, String[] armorPieces) throws InterruptedException
    {
        obj_id pInv = utils.getInventoryContainer(player);
        for (int j = 0; j < armorPieces.length; ++j)
        {
            String armorTemplate = ARMOR_SET_PREFIX + armorPieces[j];
            obj_id armorItem = createObject(armorTemplate, pInv, "");
            if (isIdValid(armorItem))
            {
                if (!isGameObjectTypeOf(armorItem, GOT_armor_foot) && !isGameObjectTypeOf(armorItem, GOT_armor_hand))
                {
                    armor.setArmorDataPercent(armorItem, 2, 0, GENERAL_PROTECTION, CONDITION);
                    armor.setArmorSpecialProtectionPercent(armorItem, armor.DATATABLE_RECON_LAYER, 1.0f);
                }
            }
        }
    }
    public void issueBattleArmorSet(obj_id player, String[] armorPieces) throws InterruptedException
    {
        obj_id pInv = utils.getInventoryContainer(player);
        for (int j = 0; j < armorPieces.length; ++j)
        {
            String armorTemplate = ARMOR_SET_PREFIX + armorPieces[j];
            obj_id armorItem = createObject(armorTemplate, pInv, "");
            if (isIdValid(armorItem))
            {
                if (!isGameObjectTypeOf(armorItem, GOT_armor_foot) && !isGameObjectTypeOf(armorItem, GOT_armor_hand))
                {
                    armor.setArmorDataPercent(armorItem, 2, 1, GENERAL_PROTECTION, CONDITION);
                }
            }
        }
    }
    public void issueAssaultArmorSet(obj_id player, String[] armorPieces) throws InterruptedException
    {
        obj_id pInv = utils.getInventoryContainer(player);
        for (int j = 0; j < armorPieces.length; ++j)
        {
            String armorTemplate = ARMOR_SET_PREFIX + armorPieces[j];
            obj_id armorItem = createObject(armorTemplate, pInv, "");
            if (isIdValid(armorItem))
            {
                if (!isGameObjectTypeOf(armorItem, GOT_armor_foot) && !isGameObjectTypeOf(armorItem, GOT_armor_hand))
                {
                    armor.setArmorDataPercent(armorItem, 2, 2, GENERAL_PROTECTION, CONDITION);
                    armor.setArmorSpecialProtectionPercent(armorItem, armor.DATATABLE_ASSAULT_LAYER, 1.0f);
                }
            }
        }
    }
}
