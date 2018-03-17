package script.e3demo;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.space_transition;
import script.library.create;
import script.library.bio_engineer;
import script.library.hue;
import script.library.sui;
import script.library.pet_lib;
import script.library.ship_ai;
import script.library.space_utils;
import script.library.space_combat;
import script.library.space_create;
import script.library.ai_lib;

public class demoer extends script.base_script
{
    public demoer()
    {
    }
    public static final String[] MANDALORIAN_ARMOR = 
    {
        "object/tangible/wearables/armor/mandalorian/armor_mandalorian_bracer_l.iff",
        "object/tangible/wearables/armor/mandalorian/armor_mandalorian_bracer_r.iff",
        "object/tangible/wearables/armor/mandalorian/armor_mandalorian_chest_plate.iff",
        "object/tangible/wearables/armor/mandalorian/armor_mandalorian_belt.iff",
        "object/tangible/wearables/armor/mandalorian/armor_mandalorian_bicep_l.iff",
        "object/tangible/wearables/armor/mandalorian/armor_mandalorian_bicep_r.iff",
        "object/tangible/wearables/armor/mandalorian/armor_mandalorian_gloves.iff",
        "object/tangible/wearables/armor/mandalorian/armor_mandalorian_helmet.iff",
        "object/tangible/wearables/armor/mandalorian/armor_mandalorian_leggings.iff",
        "object/tangible/wearables/armor/mandalorian/armor_mandalorian_shoes.iff"
    };
    public static final String[] WOOKIEE_ARMOR = 
    {
        "object/tangible/wearables/armor/kashyyykian_ceremonial/armor_kashyyykian_ceremonial_bracer_l.iff",
        "object/tangible/wearables/armor/kashyyykian_ceremonial/armor_kashyyykian_ceremonial_bracer_r.iff",
        "object/tangible/wearables/armor/kashyyykian_ceremonial/armor_kashyyykian_ceremonial_chest_plate.iff",
        "object/tangible/wearables/armor/kashyyykian_ceremonial/armor_kashyyykian_ceremonial_leggings.iff"
    };
    public static final String[] SHIP_COMPONENTS = 
    {
        "object/tangible/ship/components/engine/eng_incom_fusialthrust.iff",
        "object/tangible/ship/components/engine/eng_qualdex_kyromaster.iff",
        "object/tangible/ship/components/weapon/wpn_subpro_tripleblaster_mark2.iff",
        "object/tangible/ship/components/weapon/wpn_incom_shredder.iff",
        "object/tangible/ship/components/weapon/wpn_armek_sw4.iff",
        "object/tangible/ship/components/booster/xwing_booster_test.iff"
    };
    public static final String[] JEDI_CLOTHES = 
    {
        "object/tangible/wearables/robe/robe_s05_h1.iff",
        "object/tangible/wearables/robe/robe_s05.iff"
    };
    public static final String[] LIGHTSABERS = 
    {
        "object/weapon/melee/2h_sword/crafted_saber/sword_lightsaber_two_handed_gen5.iff",
        "object/weapon/melee/sword/crafted_saber/sword_lightsaber_one_handed_gen5.iff",
        "object/weapon/melee/polearm/crafted_saber/sword_lightsaber_polearm_gen5.iff"
    };
    public static final String[] CREATUREHANDLER = 
    {
        "outdoors_ranger",
        "outdoors_ranger_novice",
        "outdoors_ranger_master",
        "outdoors_ranger_movement_01",
        "outdoors_ranger_movement_02",
        "outdoors_ranger_movement_03",
        "outdoors_ranger_movement_04",
        "outdoors_ranger_tracking_01",
        "outdoors_ranger_tracking_02",
        "outdoors_ranger_tracking_03",
        "outdoors_ranger_tracking_04",
        "outdoors_ranger_harvest_01",
        "outdoors_ranger_harvest_02",
        "outdoors_ranger_harvest_03",
        "outdoors_ranger_harvest_04",
        "outdoors_ranger_support_01",
        "outdoors_ranger_support_02",
        "outdoors_ranger_support_03",
        "outdoors_ranger_support_04",
        "outdoors_creaturehandler",
        "outdoors_creaturehandler_novice",
        "outdoors_creaturehandler_master",
        "outdoors_creaturehandler_taming_01",
        "outdoors_creaturehandler_taming_02",
        "outdoors_creaturehandler_taming_03",
        "outdoors_creaturehandler_taming_04",
        "outdoors_creaturehandler_training_01",
        "outdoors_creaturehandler_training_02",
        "outdoors_creaturehandler_training_03",
        "outdoors_creaturehandler_training_04",
        "outdoors_creaturehandler_healing_01",
        "outdoors_creaturehandler_healing_02",
        "outdoors_creaturehandler_healing_03",
        "outdoors_creaturehandler_healing_04",
        "outdoors_creaturehandler_support_01",
        "outdoors_creaturehandler_support_02",
        "outdoors_creaturehandler_support_03",
        "outdoors_creaturehandler_support_04"
    };
    public static final String[] POLITICIAN = 
    {
        "social_politician",
        "social_politician_novice",
        "social_politician_master",
        "social_politician_fiscal_01",
        "social_politician_fiscal_02",
        "social_politician_fiscal_03",
        "social_politician_fiscal_04",
        "social_politician_martial_01",
        "social_politician_martial_02",
        "social_politician_martial_03",
        "social_politician_martial_04",
        "social_politician_civic_01",
        "social_politician_civic_02",
        "social_politician_civic_03",
        "social_politician_civic_04",
        "social_politician_urban_01",
        "social_politician_urban_02",
        "social_politician_urban_03",
        "social_politician_urban_04"
    };
    public static final String[] JEDI = 
    {
        "jedi_padawan",
        "jedi_padawan_novice",
        "jedi_padawan_master",
        "jedi_padawan_saber_01",
        "jedi_padawan_saber_02",
        "jedi_padawan_saber_03",
        "jedi_padawan_saber_04",
        "jedi_padawan_healing_01",
        "jedi_padawan_healing_02",
        "jedi_padawan_healing_03",
        "jedi_padawan_healing_04",
        "jedi_padawan_force_power_01",
        "jedi_padawan_force_power_02",
        "jedi_padawan_force_power_03",
        "jedi_padawan_force_power_04",
        "jedi_padawan_force_manipulation_01",
        "jedi_padawan_force_manipulation_02",
        "jedi_padawan_force_manipulation_03",
        "jedi_padawan_force_manipulation_04",
        "jedi_light_side_journeyman",
        "jedi_light_side_journeyman_novice",
        "jedi_light_side_journeyman_master",
        "jedi_light_side_journeyman_saber_01",
        "jedi_light_side_journeyman_saber_02",
        "jedi_light_side_journeyman_saber_03",
        "jedi_light_side_journeyman_saber_04",
        "jedi_light_side_journeyman_healing_01",
        "jedi_light_side_journeyman_healing_02",
        "jedi_light_side_journeyman_healing_03",
        "jedi_light_side_journeyman_healing_04",
        "jedi_light_side_journeyman_force_power_01",
        "jedi_light_side_journeyman_force_power_02",
        "jedi_light_side_journeyman_force_power_03",
        "jedi_light_side_journeyman_force_power_04",
        "jedi_light_side_journeyman_force_manipulation_01",
        "jedi_light_side_journeyman_force_manipulation_02",
        "jedi_light_side_journeyman_force_manipulation_03",
        "jedi_light_side_journeyman_force_manipulation_04",
        "jedi_light_side_master",
        "jedi_light_side_master_novice",
        "jedi_light_side_master_master",
        "jedi_light_side_master_saber_01",
        "jedi_light_side_master_saber_02",
        "jedi_light_side_master_saber_03",
        "jedi_light_side_master_saber_04",
        "jedi_light_side_master_healing_01",
        "jedi_light_side_master_healing_02",
        "jedi_light_side_master_healing_03",
        "jedi_light_side_master_healing_04",
        "jedi_light_side_master_force_power_01",
        "jedi_light_side_master_force_power_02",
        "jedi_light_side_master_force_power_03",
        "jedi_light_side_master_force_power_04",
        "jedi_light_side_master_force_manipulation_01",
        "jedi_light_side_master_force_manipulation_02",
        "jedi_light_side_master_force_manipulation_03",
        "jedi_light_side_master_force_manipulation_04",
        "jedi_dark_side_journeyman",
        "jedi_dark_side_journeyman_novice",
        "jedi_dark_side_journeyman_master",
        "jedi_dark_side_journeyman_saber_01",
        "jedi_dark_side_journeyman_saber_02",
        "jedi_dark_side_journeyman_saber_03",
        "jedi_dark_side_journeyman_saber_04",
        "jedi_dark_side_journeyman_healing_01",
        "jedi_dark_side_journeyman_healing_02",
        "jedi_dark_side_journeyman_healing_03",
        "jedi_dark_side_journeyman_healing_04",
        "jedi_dark_side_journeyman_force_power_01",
        "jedi_dark_side_journeyman_force_power_02",
        "jedi_dark_side_journeyman_force_power_03",
        "jedi_dark_side_journeyman_force_power_04",
        "jedi_dark_side_journeyman_force_manipulation_01",
        "jedi_dark_side_journeyman_force_manipulation_02",
        "jedi_dark_side_journeyman_force_manipulation_03",
        "jedi_dark_side_journeyman_force_manipulation_04",
        "jedi_dark_side_master",
        "jedi_dark_side_master_novice",
        "jedi_dark_side_master_master",
        "jedi_dark_side_master_saber_01",
        "jedi_dark_side_master_saber_02",
        "jedi_dark_side_master_saber_03",
        "jedi_dark_side_master_saber_04",
        "jedi_dark_side_master_healing_01",
        "jedi_dark_side_master_healing_02",
        "jedi_dark_side_master_healing_03",
        "jedi_dark_side_master_healing_04",
        "jedi_dark_side_master_force_power_01",
        "jedi_dark_side_master_force_power_02",
        "jedi_dark_side_master_force_power_03",
        "jedi_dark_side_master_force_power_04",
        "jedi_dark_side_master_force_manipulation_01",
        "jedi_dark_side_master_force_manipulation_02",
        "jedi_dark_side_master_force_manipulation_03",
        "jedi_dark_side_master_force_manipulation_04"
    };
    public static final String[] REBEL_PILOT = 
    {
        "pilot_rebel_navy",
        "pilot_rebel_navy_novice",
        "pilot_rebel_navy_master",
        "pilot_rebel_navy_starships_01",
        "pilot_rebel_navy_starships_02",
        "pilot_rebel_navy_starships_03",
        "pilot_rebel_navy_starships_04",
        "pilot_rebel_navy_weapons_01",
        "pilot_rebel_navy_weapons_02",
        "pilot_rebel_navy_weapons_03",
        "pilot_rebel_navy_weapons_04",
        "pilot_rebel_navy_procedures_01",
        "pilot_rebel_navy_procedures_02",
        "pilot_rebel_navy_procedures_03",
        "pilot_rebel_navy_procedures_04",
        "pilot_rebel_navy_droid_01",
        "pilot_rebel_navy_droid_02",
        "pilot_rebel_navy_droid_03",
        "pilot_rebel_navy_droid_04"
    };
    public static final String[] IMPERIAL_PILOT = 
    {
        "pilot_imperial_navy",
        "pilot_imperial_navy_novice",
        "pilot_imperial_navy_master",
        "pilot_imperial_navy_starships_01",
        "pilot_imperial_navy_starships_02",
        "pilot_imperial_navy_starships_03",
        "pilot_imperial_navy_starships_04",
        "pilot_imperial_navy_weapons_01",
        "pilot_imperial_navy_weapons_02",
        "pilot_imperial_navy_weapons_03",
        "pilot_imperial_navy_weapons_04",
        "pilot_imperial_navy_procedures_01",
        "pilot_imperial_navy_procedures_02",
        "pilot_imperial_navy_procedures_03",
        "pilot_imperial_navy_procedures_04",
        "pilot_imperial_navy_droid_01",
        "pilot_imperial_navy_droid_02",
        "pilot_imperial_navy_droid_03",
        "pilot_imperial_navy_droid_04"
    };
    public static final String[] NEUTRAL_PILOT = 
    {
        "pilot_neutral",
        "pilot_neutral_novice",
        "pilot_neutral_master",
        "pilot_neutral_starships_01",
        "pilot_neutral_starships_02",
        "pilot_neutral_starships_03",
        "pilot_neutral_starships_04",
        "pilot_neutral_weapons_01",
        "pilot_neutral_weapons_02",
        "pilot_neutral_weapons_03",
        "pilot_neutral_weapons_04",
        "pilot_neutral_procedures_01",
        "pilot_neutral_procedures_02",
        "pilot_neutral_procedures_03",
        "pilot_neutral_procedures_04",
        "pilot_neutral_droid_01",
        "pilot_neutral_droid_02",
        "pilot_neutral_droid_03",
        "pilot_neutral_droid_04"
    };
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if(!isGod(self) || getGodLevel(self) < 50 || !isPlayer(self)){
            detachScript(self, "e3demo.demoer");
            return SCRIPT_CONTINUE;
        }
        setObjVar(self, "intAutoSaveOff", 1);
        return SCRIPT_CONTINUE;
    }
    public int OnSpeaking(obj_id self, String strText) throws InterruptedException
    {
        String[] strCommands = split(strText, ' ');
        if (strCommands[0].equalsIgnoreCase("setupGroundDemo1"))
        {
            location jabbaPalace = new location(2591, 2, 4423);
            obj_id palace1 = createObject("object/building/tatooine/palace_tatooine_jabba.iff", jabbaPalace);
            setObjVar(self, "palace1", palace1);
            dictionary dctParams = new dictionary();
            dctParams.put("objBuilding", palace1);
            String[] strScripts = getScriptList(palace1);
            detachScriptList(strScripts, palace1);
            LOG("test", "Messaging self with " + dctParams.toString());
            sendSystemMessageTestingOnly(self, "Setup Ground Demo 1");
            messageTo(self, "cleanoutBuilding", dctParams, 15, false);
            obj_id[] decor = new obj_id[13];
            location crawler1loc = new location(2527, .66f, 4624);
            obj_id crawler1 = createObject("object/static/vehicle/static_sandcrawler.iff", crawler1loc);
            decor[0] = crawler1;
            location crawler2loc = new location(2529, .86f, 4672);
            obj_id crawler2 = createObject("object/static/vehicle/static_sandcrawler.iff", crawler2loc);
            decor[1] = crawler2;
            location jawa1loc = new location(2540, .70f, 4624);
            obj_id jawa1 = createObject("object/static/creature/tatooine_jawa.iff", jawa1loc);
            decor[2] = jawa1;
            location jawa2loc = new location(2543, .71f, 4624);
            obj_id jawa2 = createObject("object/static/creature/tatooine_jawa.iff", jawa2loc);
            decor[3] = jawa2;
            location jawa3loc = new location(2543, .75f, 4627);
            obj_id jawa3 = createObject("object/static/creature/tatooine_jawa.iff", jawa3loc);
            decor[4] = jawa3;
            location jawa4loc = new location(2540, .74f, 4626);
            obj_id jawa4 = createObject("object/static/creature/tatooine_jawa.iff", jawa4loc);
            decor[5] = jawa4;
            location r2loc = new location(2542, .73f, 4625);
            obj_id r2 = createObject("object/static/creature/droids_r2.iff", r2loc);
            decor[6] = r2;
            location poiLoc = new location(2551, 1.94f, 4662);
            obj_id poi = createObject("object/static/poi/scene/camp_jawa_lrg.iff", poiLoc);
            decor[7] = poi;
            location banthaLoc = new location(2572.73f, 1.96f, 4666.73f);
            obj_id bantha = createObject("object/static/creature/tatooine_bantha_saddle.iff", banthaLoc);
            decor[8] = bantha;
            location gamor1loc = new location(2599, 0, 4578);
            obj_id gamor1 = create.object("gamorrean_guard", gamor1loc);
            setInvulnerable(gamor1, true);
            ai_lib.setDefaultCalmBehavior(gamor1, ai_lib.BEHAVIOR_SENTINEL);
            setScale(gamor1, 1.5f);
            setYaw(gamor1, 0);
            decor[9] = gamor1;
            location gamor2loc = new location(2582, 0, 4578);
            obj_id gamor2 = create.object("gamorrean_guard", gamor2loc);
            setInvulnerable(gamor2, true);
            ai_lib.setDefaultCalmBehavior(gamor2, ai_lib.BEHAVIOR_SENTINEL);
            setScale(gamor2, 1.5f);
            setYaw(gamor2, 0);
            decor[10] = gamor2;
            obj_id cell = getCellId(palace1, "foyer");
            location bomarr1loc = new location(-6.4f, .2f, 119.8f, "thm_tato_jabbas_palace", cell);
            obj_id bomarr1 = create.object("bomarr_monk", bomarr1loc);
            setInvulnerable(bomarr1, true);
            ai_lib.setDefaultCalmBehavior(bomarr1, ai_lib.BEHAVIOR_SENTINEL);
            setYaw(bomarr1, 11);
            decor[11] = bomarr1;
            location bomarr2loc = new location(6.0f, .2f, 130.8f, "thm_tato_jabbas_palace", cell);
            obj_id bomarr2 = create.object("bomarr_monk", bomarr2loc);
            setInvulnerable(bomarr2, true);
            ai_lib.setDefaultCalmBehavior(bomarr2, ai_lib.BEHAVIOR_SENTINEL);
            setYaw(bomarr2, -14);
            decor[11] = bomarr2;
            for (int intI = 0; intI < decor.length; intI++)
            {
                setObjVar(decor[intI], "intGroundDemo1", 1);
            }
            setObjVar(self, "decor", decor);
        }
        if (strCommands[0].equalsIgnoreCase("setupGroundDemo2"))
        {
            location jabbaPalace = new location(-2480, .25f, -5109);
            obj_id palace2 = createObject("object/building/tatooine/palace_tatooine_jabba.iff", jabbaPalace);
            setObjVar(self, "palace2", palace2);
            dictionary dctParams = new dictionary();
            dctParams.put("objBuilding", palace2);
            String[] strScripts = getScriptList(palace2);
            detachScriptList(strScripts, palace2);
            LOG("test", "Messaging self with " + dctParams.toString());
            sendSystemMessageTestingOnly(self, "Setup Ground Demo 2");
            messageTo(self, "cleanoutBuilding", dctParams, 15, false);
            obj_id[] decor = new obj_id[13];
            location crawler1loc = new location(-2528.72f, 0, -4915.57f);
            obj_id crawler1 = createObject("object/static/vehicle/static_sandcrawler.iff", crawler1loc);
            decor[0] = crawler1;
            location crawler2loc = new location(-2529.78f, 0, -4868.04f);
            obj_id crawler2 = createObject("object/static/vehicle/static_sandcrawler.iff", crawler2loc);
            decor[1] = crawler2;
            location jawa1loc = new location(-2502.87f, 0, -4913.52f);
            obj_id jawa1 = createObject("object/static/creature/tatooine_jawa.iff", jawa1loc);
            setYaw(jawa1, -2);
            decor[2] = jawa1;
            location jawa2loc = new location(-2505.72f, 0, -4911.22f);
            obj_id jawa2 = createObject("object/static/creature/tatooine_jawa.iff", jawa2loc);
            setYaw(jawa2, 95);
            decor[3] = jawa2;
            location jawa3loc = new location(-2502.09f, 0, -4910.44f);
            obj_id jawa3 = createObject("object/static/creature/tatooine_jawa.iff", jawa3loc);
            setYaw(jawa3, -164);
            decor[4] = jawa3;
            location jawa4loc = new location(-2500.72f, 0, -4911.91f);
            obj_id jawa4 = createObject("object/static/creature/tatooine_jawa.iff", jawa4loc);
            setYaw(jawa4, -87);
            decor[5] = jawa4;
            location r2loc = new location(-2502.95f, 0, -4911.74f);
            obj_id r2 = createObject("object/static/creature/droids_r2.iff", r2loc);
            decor[6] = r2;
            location poiLoc = new location(-2499.79f, 0, -4883.52f);
            obj_id poi = createObject("object/static/poi/scene/camp_jawa_lrg.iff", poiLoc);
            decor[7] = poi;
            location banthaLoc = new location(-2479.56f, 0, -4882.23f);
            obj_id bantha = createObject("object/static/creature/tatooine_bantha_saddle.iff", banthaLoc);
            decor[8] = bantha;
            location gamor1loc = new location(-2471.3f, 0, -4954.6f);
            obj_id gamor1 = create.object("gamorrean_guard", gamor1loc);
            setInvulnerable(gamor1, true);
            ai_lib.setDefaultCalmBehavior(gamor1, ai_lib.BEHAVIOR_SENTINEL);
            setScale(gamor1, 1.5f);
            setYaw(gamor1, 0);
            decor[9] = gamor1;
            location gamor2loc = new location(-2488.7f, 0, -4956.8f);
            obj_id gamor2 = create.object("gamorrean_guard", gamor2loc);
            setInvulnerable(gamor2, true);
            ai_lib.setDefaultCalmBehavior(gamor2, ai_lib.BEHAVIOR_SENTINEL);
            setScale(gamor2, 1.5f);
            setYaw(gamor2, 0);
            decor[10] = gamor2;
            obj_id cell = getCellId(palace2, "foyer");
            location bomarr1loc = new location(-6.4f, .2f, 119.8f, "thm_tato_jabbas_palace", cell);
            obj_id bomarr1 = create.object("bomarr_monk", bomarr1loc);
            setInvulnerable(bomarr1, true);
            ai_lib.setDefaultCalmBehavior(bomarr1, ai_lib.BEHAVIOR_SENTINEL);
            setYaw(bomarr1, 11);
            decor[11] = bomarr1;
            location bomarr2loc = new location(6.0f, .2f, 130.8f, "thm_tato_jabbas_palace", cell);
            obj_id bomarr2 = create.object("bomarr_monk", bomarr2loc);
            setInvulnerable(bomarr2, true);
            ai_lib.setDefaultCalmBehavior(bomarr2, ai_lib.BEHAVIOR_SENTINEL);
            setYaw(bomarr2, -14);
            decor[11] = bomarr2;
            for (int intI = 0; intI < decor.length; intI++)
            {
                setObjVar(decor[intI], "intGroundDemo2", 1);
            }
            setObjVar(self, "decor", decor);
        }
        if (strCommands[0].equalsIgnoreCase("cleanupGroundDemo1"))
        {
            location jabbaPalace = new location(2591, 2, 4423);
            obj_id[] objTestObjects = getObjectsInRange(jabbaPalace, 1000);
            if (objTestObjects != null)
            {
                for (int intI = 0; intI < objTestObjects.length; intI++)
                {
                    destroyObject(objTestObjects[intI]);
                }
            }
            return SCRIPT_CONTINUE;
        }
        if (strCommands[0].equalsIgnoreCase("cleanupGroundDemo2"))
        {
            location jabbaPalace = new location(-2480, .25f, -5109);
            obj_id[] objTestObjects = getObjectsInRange(jabbaPalace, 1000);
            if (objTestObjects != null)
            {
                for (int intI = 0; intI < objTestObjects.length; intI++)
                {
                    destroyObject(objTestObjects[intI]);
                }
            }
            return SCRIPT_CONTINUE;
        }
        if (strCommands[0].equalsIgnoreCase("gotoGroundDemo1"))
        {
            warpPlayer(self, "tatooine", 2569, 2, 4665, null, 0, 0, 0, "", false);
        }
        if (strCommands[0].equalsIgnoreCase("gotoGroundDemo2"))
        {
            warpPlayer(self, "tatooine", -2483.9f, 0, -4879.6f, null, 0, 0, 0, "", false);
        }
        if (strCommands[0].equalsIgnoreCase("startGroundDemo1"))
        {
            sendSystemMessageTestingOnly(self, "Demo is starting now!  Proceed into the palace!");
            obj_id palace1 = getObjIdObjVar(self, "palace1");
            obj_id cell = getCellId(palace1, "foyer");
            location grievousSpawn = new location(-1.2f, 0.3f, 116.9f, "thm_tato_jabbas_palace", cell);
            obj_id grievous = create.object("demo_general_grievous", grievousSpawn);
            attachScript(grievous, "e3demo.grievous");
            setObjVar(grievous, "player", self);
            setObjVar(grievous, "demoNumber", 1);
        }
        if (strCommands[0].equalsIgnoreCase("startGroundDemo2"))
        {
            sendSystemMessageTestingOnly(self, "Demo is starting now!  Proceed into the palace!");
            obj_id palace2 = getObjIdObjVar(self, "palace2");
            obj_id cell = getCellId(palace2, "foyer");
            location grievousSpawn = new location(-1.2f, 0.3f, 116.9f, "thm_tato_jabbas_palace", cell);
            obj_id grievous = create.object("demo_general_grievous", grievousSpawn);
            attachScript(grievous, "e3demo.grievous");
            setObjVar(grievous, "player", self);
            setObjVar(grievous, "demoNumber", 2);
        }
        if (strCommands[0].equalsIgnoreCase("cleanupGroundDemo1"))
        {
            obj_id palace1 = getObjIdObjVar(self, "palace1");
            destroyObject(palace1);
            obj_id[] decor = getObjIdArrayObjVar(self, "decor");
            for (int i = 0; i < decor.length; i++)
            {
                if (isIdValid(decor[i]))
                {
                    destroyObject(decor[i]);
                }
            }
        }
        if (strCommands[0].equalsIgnoreCase("cleanupgroundDemo2"))
        {
            obj_id palace2 = getObjIdObjVar(self, "palace2");
            destroyObject(palace2);
            obj_id[] decor = getObjIdArrayObjVar(self, "decor");
            for (int i = 0; i < decor.length; i++)
            {
                if (isIdValid(decor[i]))
                {
                    destroyObject(decor[i]);
                }
            }
        }
        if (strCommands[0].equalsIgnoreCase("setupSpaceDemo"))
        {
            obj_id objShip = space_transition.getContainingShip(self);
            if (!isIdValid(objShip))
            {
                obj_id objPCD = space_utils.createShipControlDevice(self, "xwing", true);
                objShip = space_transition.getShipFromShipControlDevice(objPCD);
            }
            grantAllSkills(self, REBEL_PILOT);
            setFlightModel(objShip, "demoer");
            setShipCapacitorEnergyMaximum(objShip, 50000);
            setShipCapacitorEnergyCurrent(objShip, 50000);
            setShipCapacitorEnergyRechargeRate(objShip, 1000);
            dictionary dctParams = new dictionary();
            dctParams.put("objShip", objShip);
            messageTo(self, "gotoSpace", dctParams, 1, false);
        }
        if (strCommands[0].equalsIgnoreCase("resetSpaceDemo"))
        {
            obj_id objShip = space_transition.getContainingShip(self);
            int spaceFaction = space_transition.getPlayerSpaceFaction(self);
            shipSetSpaceFaction(space_transition.getContainingShip(self), spaceFaction);
            warpPlayer(objShip, "space_kashyyyk", 2934, 455, -2340, null, 2934, 455, -2340, null, true);
            messageTo(self, "faceAvatar", null, 2, false);
            grantAllSkills(self, REBEL_PILOT);
            sendSystemMessageTestingOnly(self, "FOOZLE");
        }
        if (strCommands[0].equalsIgnoreCase("makeRebelPilot"))
        {
            grantAllSkills(self, REBEL_PILOT);
        }
        if (strCommands[0].equalsIgnoreCase("makeImperialPilot"))
        {
            grantAllSkills(self, IMPERIAL_PILOT);
        }
        if (strCommands[0].equalsIgnoreCase("makePolitician"))
        {
            grantAllSkills(self, POLITICIAN);
        }
        if (strCommands[0].equalsIgnoreCase("makeJedi"))
        {
            setJediState(self, JEDI_STATE_FORCE_SENSITIVE);
            grantAllSkills(self, JEDI);
        }
        if (strCommands[0].equalsIgnoreCase("makeCreatureHandler"))
        {
            grantAllSkills(self, CREATUREHANDLER);
        }
        if (strCommands[0].equalsIgnoreCase("makeNeutralPilot"))
        {
            grantAllSkills(self, NEUTRAL_PILOT);
        }
        if (strCommands[0].equalsIgnoreCase("makeBantha"))
        {
            createBantha(self);
        }
        if (strCommands[0].equalsIgnoreCase("makeDewback"))
        {
            createDewback(self);
        }
        if (strCommands[0].equalsIgnoreCase("makeLandSpeeder"))
        {
            createLandSpeeder(self);
        }
        if (strCommands[0].equalsIgnoreCase("makeAv21"))
        {
            createAv21(self);
        }
        if (strCommands[0].equalsIgnoreCase("makeSwoop"))
        {
            createSwoop(self);
        }
        if (strCommands[0].equalsIgnoreCase("makeJetpack"))
        {
            createJetpack(self);
        }
        if (strCommands[0].equalsIgnoreCase("makeDroid"))
        {
            obj_id inventoryContainer = getObjectInSlot(self, "inventory");
            if (!isIdValid(inventoryContainer))
            {
                debugServerConsoleMsg(self, "working.reecetest - looks like the objid of the player inventory is invalid");
            }
            obj_id object = createObject("object/tangible/deed/pet_deed/deed_r2_basic.iff", inventoryContainer, "SLOT_INVENTORY");
            if (!isIdValid(object))
            {
                debugServerConsoleMsg(self, "working.reecetest - failed to create the deed object");
            }
            int powerLevel = 50;
            final String CREATURE_NAME = "r2_crafted";
            dictionary creatureDict = dataTableGetRow(create.CREATURE_TABLE, "r2_crafted");
            setObjVar(object, "creature_attribs.type", "r2_crafted");
            for (int attribNum = 0; attribNum < NUM_ATTRIBUTES; attribNum++)
            {
                int minAttrib = creatureDict.getInt(create.MINATTRIBNAMES[attribNum]);
                int maxAttrib = creatureDict.getInt(create.MAXATTRIBNAMES[attribNum]);
                int attribRange = maxAttrib - minAttrib;
                int newAttribValue = (int)((attribRange * powerLevel) / 1000) + minAttrib;
                setObjVar(object, "creature_attribs." + create.MAXATTRIBNAMES[attribNum], newAttribValue);
            }
            int toHitChance = creatureDict.getInt("toHitChance");
            float minToHit = toHitChance + (toHitChance * bio_engineer.CREATURE_MIN_TO_HIT_MOD);
            float maxToHit = toHitChance + (toHitChance * bio_engineer.CREATURE_MAX_TO_HIT_MOD);
            setObjVar(object, "creature_attribs.toHitChance", toHitChance);
            obj_id creatureWeapon = getCurrentWeapon(object);
            float wpnSpeed = (float)creatureDict.getInt("attackSpeed");
            float minWpnSpeed = wpnSpeed + (wpnSpeed * bio_engineer.CREATURE_MIN_WEAPON_SPEED_MOD);
            float maxWpnSpeed = wpnSpeed + (wpnSpeed * bio_engineer.CREATURE_MAX_WEAPON_SPEED_MOD);
            setObjVar(object, "creature_attribs.attackSpeed", wpnSpeed);
            int minDamage = creatureDict.getInt("minDamage");
            int maxDamage = creatureDict.getInt("maxDamage");
            float minMinDamage = minDamage + (minDamage * bio_engineer.CREATURE_MIN_DAMAGE_MOD);
            float maxMinDamage = minDamage + (minDamage * bio_engineer.CREATURE_MAX_DAMAGE_MOD);
            float minMaxDamage = maxDamage + (maxDamage * bio_engineer.CREATURE_MIN_DAMAGE_MOD);
            float maxMaxDamage = maxDamage + (maxDamage * bio_engineer.CREATURE_MAX_DAMAGE_MOD);
            minDamage = (int)(minMinDamage + (((maxMinDamage - minMinDamage) * powerLevel) / 1000));
            maxDamage = (int)(minMaxDamage + (((maxMaxDamage - minMaxDamage) * powerLevel) / 1000));
            setObjVar(object, "creature_attribs.minDamage", minDamage);
            setObjVar(object, "creature_attribs.maxDamage", maxDamage);
            setObjVar(object, "crafting_components.cmbt_module", 600.0f);
            setObjVar(object, "dataModuleRating", 12);
            setObjVar(object, "ai.pet.hasContainer", 12);
            setObjVar(object, "ai.pet.isRepairDroid", true);
            sendSystemMessageTestingOnly(self, "Made R2 Unit deed in your inventory");
        }
        return SCRIPT_CONTINUE;
    }
    public void grantAllSkills(obj_id objPlayer, String[] strSkillList) throws InterruptedException
    {
        for (int intI = 0; intI < strSkillList.length; intI++)
        {
            grantSkill(objPlayer, strSkillList[intI]);
        }
    }
    public void createBantha(obj_id self) throws InterruptedException
    {
        String controlTemplate = "object/intangible/pet/bantha_hue.iff";
        obj_id datapad = utils.getPlayerDatapad(self);
        obj_id petControlDevice = createObject(controlTemplate, datapad, "");
        setObjVar(petControlDevice, "pet.creatureName", "bantha_e3");
        attachScript(petControlDevice, "ai.pet_control_device");
        setName(petControlDevice, "(bantha)");
        int petType = pet_lib.PET_TYPE_NON_AGGRO;
        setObjVar(petControlDevice, "ai.pet.type", petType);
        setObjVar(petControlDevice, "ai.pet.trainedMount", 1);
    }
    public void createDewback(obj_id self) throws InterruptedException
    {
        String controlTemplate = "object/intangible/pet/dewback_hue.iff";
        obj_id datapad = utils.getPlayerDatapad(self);
        obj_id petControlDevice = createObject(controlTemplate, datapad, "");
        setObjVar(petControlDevice, "pet.creatureName", "dewback");
        attachScript(petControlDevice, "ai.pet_control_device");
        setName(petControlDevice, "(dewback)");
        int petType = pet_lib.PET_TYPE_NON_AGGRO;
        setObjVar(petControlDevice, "ai.pet.type", petType);
        setObjVar(petControlDevice, "ai.pet.trainedMount", 1);
    }
    public void createLandSpeeder(obj_id self) throws InterruptedException
    {
        obj_id datapad = utils.getPlayerDatapad(self);
        String controlTemplate = "object/intangible/vehicle/landspeeder_x34_pcd.iff";
        obj_id petControlDevice = createObject(controlTemplate, datapad, "");
        setObjVar(petControlDevice, "pet.crafted", true);
        setObjVar(petControlDevice, "vehicle_attribs.object_ref", "x34");
        attachScript(petControlDevice, "systems.vehicle_system.vehicle_control_device");
        setObjVar(petControlDevice, "ai.pet.trainedMount", 1);
        setName(petControlDevice, "(x34)");
    }
    public void createAv21(obj_id self) throws InterruptedException
    {
        obj_id datapad = utils.getPlayerDatapad(self);
        String controlTemplate = "object/intangible/vehicle/landspeeder_av21_pcd.iff";
        obj_id petControlDevice = createObject(controlTemplate, datapad, "");
        setObjVar(petControlDevice, "pet.crafted", true);
        setObjVar(petControlDevice, "vehicle_attribs.object_ref", "av21");
        attachScript(petControlDevice, "systems.vehicle_system.vehicle_control_device");
        setObjVar(petControlDevice, "ai.pet.trainedMount", 1);
        setName(petControlDevice, "(av21)");
    }
    public void createSwoop(obj_id self) throws InterruptedException
    {
        obj_id datapad = utils.getPlayerDatapad(self);
        String controlTemplate = "object/intangible/vehicle/speederbike_swoop_pcd.iff";
        obj_id petControlDevice = createObject(controlTemplate, datapad, "");
        setObjVar(petControlDevice, "pet.crafted", true);
        setObjVar(petControlDevice, "vehicle_attribs.object_ref", "swoop");
        attachScript(petControlDevice, "systems.vehicle_system.vehicle_control_device");
        setObjVar(petControlDevice, "ai.pet.trainedMount", 1);
        setName(petControlDevice, "(swoop)");
    }
    public void createJetpack(obj_id self) throws InterruptedException
    {
        obj_id datapad = utils.getPlayerDatapad(self);
        String controlTemplate = "object/intangible/vehicle/jetpack_pcd.iff";
        obj_id petControlDevice = createObject(controlTemplate, datapad, "");
        setObjVar(petControlDevice, "pet.crafted", true);
        setObjVar(petControlDevice, "vehicle_attribs.object_ref", "jetpack_mandalorian");
        attachScript(petControlDevice, "systems.vehicle_system.vehicle_control_device");
        setObjVar(petControlDevice, "ai.pet.trainedMount", 1);
        setName(petControlDevice, "(jetpack)");
    }
    public void createInInventory(obj_id objPlayer, String[] strItems) throws InterruptedException
    {
        obj_id objInventory = utils.getInventoryContainer(objPlayer);
        for (int intI = 0; intI < strItems.length; intI++)
        {
            obj_id objTest = createObjectOverloaded(strItems[intI], objInventory);
            if (!isIdValid(objTest))
            {
                sendSystemMessageTestingOnly(objPlayer, "Item of template " + strItems[intI] + " is Incorret!");
            }
        }
        return;
    }
    public void setFlightModel(obj_id objShip, String strFlightModel) throws InterruptedException
    {
        dictionary dctShipInfo = dataTableGetRow("datatables/ship/ship_debug.iff", strFlightModel);
        if (dctShipInfo == null)
        {
            return;
        }
        if (!isIdValid(objShip))
        {
            return;
        }
        setShipEngineAccelerationRate(objShip, dctShipInfo.getFloat("engine_accel"));
        setShipEngineDecelerationRate(objShip, dctShipInfo.getFloat("engine_decel"));
        setShipEnginePitchAccelerationRateDegrees(objShip, dctShipInfo.getFloat("engine_pitch_accel"));
        setShipEngineYawAccelerationRateDegrees(objShip, dctShipInfo.getFloat("engine_yaw_accel"));
        setShipEngineRollAccelerationRateDegrees(objShip, dctShipInfo.getFloat("engine_roll_accel"));
        setShipEnginePitchRateMaximumDegrees(objShip, dctShipInfo.getFloat("engine_pitch"));
        setShipEngineYawRateMaximumDegrees(objShip, dctShipInfo.getFloat("engine_yaw"));
        setShipEngineRollRateMaximumDegrees(objShip, dctShipInfo.getFloat("engine_roll"));
        setShipEngineSpeedMaximum(objShip, dctShipInfo.getFloat("engine_speed"));
        setShipEngineSpeedRotationFactorMaximum(objShip, dctShipInfo.getFloat("speed_rotation_factor"));
        setShipSlideDampener(objShip, dctShipInfo.getFloat("slideDamp"));
        setShipBoosterEnergyCurrent(objShip, dctShipInfo.getFloat("booster_energy"));
        setShipBoosterEnergyMaximum(objShip, dctShipInfo.getFloat("booster_energy"));
        setShipBoosterEnergyRechargeRate(objShip, dctShipInfo.getFloat("booster_recharge"));
        setShipBoosterEnergyConsumptionRate(objShip, dctShipInfo.getFloat("booster_consumption"));
        setShipBoosterAcceleration(objShip, dctShipInfo.getFloat("booster_accel"));
        setShipBoosterSpeedMaximum(objShip, dctShipInfo.getFloat("booster_speed"));
        return;
    }
    public int faceAvatar(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id objShip = space_transition.getContainingShip(self);
        obj_id[] objAsteroids = getAllObjectsWithTemplate(getLocation(self), 320000, "object/ship/spacestation_avatar_platform.iff");
        obj_id objAsteroid = objAsteroids[0];
        vector vctTest = space_utils.getVector(objAsteroid);
        transform trTest = getTransform_o2p(objShip);
        transform trFinalTransform = space_utils.faceTransformToVector(trTest, vctTest);
        setTransform_o2p(objShip, trFinalTransform);
        int spaceFaction = space_transition.getPlayerSpaceFaction(self);
        shipSetSpaceFaction(space_transition.getContainingShip(self), spaceFaction);
        return SCRIPT_CONTINUE;
    }
    public int resetDemo1(obj_id self, dictionary params) throws InterruptedException
    {
        warpPlayer(self, "tatooine", 2569, 2, 4665, null, 0, 0, 0, "", false);
        messageTo(self, "setUpDemo1", null, 1.0f, true);
        return SCRIPT_CONTINUE;
    }
    public int resetDemo2(obj_id self, dictionary params) throws InterruptedException
    {
        warpPlayer(self, "tatooine", -2483.9f, 0, -4879.6f, null, 0, 0, 0, "", false);
        messageTo(self, "setUpDemo2", null, 1.0f, true);
        return SCRIPT_CONTINUE;
    }
    public int setUpDemo1(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id palace1 = getObjIdObjVar(self, "palace1");
        obj_id cell = getCellId(palace1, "foyer");
        location grievousSpawn = new location(-1.2f, 0.3f, 116.9f, "thm_tato_jabbas_palace", cell);
        obj_id grievous = create.object("demo_general_grievous", grievousSpawn);
        attachScript(grievous, "e3demo.grievous");
        setObjVar(grievous, "player", self);
        setObjVar(grievous, "demoNumber", 1);
        return SCRIPT_CONTINUE;
    }
    public int setUpDemo2(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id palace2 = getObjIdObjVar(self, "palace2");
        obj_id cell = getCellId(palace2, "foyer");
        location grievousSpawn = new location(-1.2f, 0.3f, 116.9f, "thm_tato_jabbas_palace", cell);
        obj_id grievous = create.object("demo_general_grievous", grievousSpawn);
        attachScript(grievous, "e3demo.grievous");
        setObjVar(grievous, "player", self);
        setObjVar(grievous, "demoNumber", 2);
        return SCRIPT_CONTINUE;
    }
    public int cleanoutBuilding(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("test", "Cleaningout bulidng");
        obj_id objBuilding = params.getObjId("objBuilding");
        obj_id[] objContents = getContents(objBuilding);
        if (objContents != null)
        {
            for (int intI = 0; intI < objContents.length; intI++)
            {
                String strTemplate = getTemplateName(objContents[intI]);
                int intIndex = strTemplate.indexOf("cell.iff");
                LOG("test", "intIndex is " + intIndex);
                if (intIndex == -1)
                {
                    destroyObject(objContents[intI]);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void detachScriptList(String[] strScriptList, obj_id objObject) throws InterruptedException
    {
        for (int intJ = 0; intJ < strScriptList.length; intJ++)
        {
            String script = strScriptList[intJ];
            if (script.indexOf("script.") > -1)
            {
                script = script.substring(7);
            }
            if (!script.equals(""))
            {
                detachScript(objObject, script);
            }
        }
    }
    public int gotoSpace(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id objShip = params.getObjId("objShip");
        location locTest = new location();
        locTest.area = "space_kashyyyk";
        locTest.x = 2934;
        locTest.y = 455;
        locTest.z = -2340;
        launch(self, objShip, null, locTest, getLocation(self));
        messageTo(self, "faceAvatar", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public void launch(obj_id player, obj_id ship, obj_id[] membersApprovedByShipOwner, location warpLocation, location groundLoc) throws InterruptedException
    {
        space_transition.clearOvertStatus(ship);
        Vector groupMembersToWarp = utils.addElement(null, player);
        Vector groupMemberStartIndex = utils.addElement(null, 0);
        utils.setScriptVar(player, "strLaunchPointName", "launching");
        Vector shipStartLocations = space_transition.getShipStartLocations(ship);
        if (shipStartLocations != null && shipStartLocations.size() > 0)
        {
            int startIndex = 0;
            location playerLoc = getLocation(player);
            if (isIdValid(playerLoc.cell))
            {
                obj_id group = getGroupObject(player);
            }
        }
        for (int i = 0; i < groupMembersToWarp.size(); ++i)
        {
            space_transition.setLaunchInfo(((obj_id)groupMembersToWarp.get(i)), ship, ((Integer)groupMemberStartIndex.get(i)).intValue(), groundLoc);
            warpPlayer(((obj_id)groupMembersToWarp.get(i)), warpLocation.area, warpLocation.x, warpLocation.y, warpLocation.z, null, warpLocation.x, warpLocation.y, warpLocation.z);
        }
    }
}
