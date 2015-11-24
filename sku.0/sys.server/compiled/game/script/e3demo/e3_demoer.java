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

public class e3_demoer extends script.base_script
{
    public e3_demoer()
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
        setObjVar(self, "intAutoSaveOff", 1);
        queueCommand(self, (1577314655), self, "declared imperial", COMMAND_PRIORITY_FRONT);
        return SCRIPT_CONTINUE;
    }
    public int OnLogout(obj_id self) throws InterruptedException
    {
        location locTest = new location();
        locTest.area = "tatooine";
        setLocation(self, locTest);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        queueCommand(self, (1577314655), self, "declared imperial", COMMAND_PRIORITY_FRONT);
        return SCRIPT_CONTINUE;
    }
    public int OnSpeaking(obj_id self, String strText) throws InterruptedException
    {
        String[] strCommands = split(strText, ' ');
        if (strCommands[0].equalsIgnoreCase("setupDemo"))
        {
            debugServerConsoleMsg(self, "************************* setting Up the Demo");
            location locTest = getLocation(self);
            String strArea = locTest.area;
            if (!strArea.equals("space_light1"))
            {
                sendSystemMessageTestingOnly(self, "You must make a tiebomber pcd and go to space_light1 to use this command");
                return SCRIPT_CONTINUE;
            }
            obj_id[] objTestObjects = getAllObjectsWithTemplate(getLocation(self), 320000, "object/ship/player/player_star_destroyer.iff");
            obj_id objShip = objTestObjects[0];
            obj_id objCell = getCellId(objShip, "elevator_e3_up");
            transform trDestination = getTransformObjVar(objShip, "trBridge");
            sendSystemMessageTestingOnly(self, "Sending you to " + objCell + " in object " + objShip);
            space_transition.enterCapitalShip(self, trDestination, objCell);
            messageTo(self, "beginMusicPlaying", null, 10, false);
        }
        if (strCommands[0].equals("resetDemo"))
        {
            obj_id[] objTestObjects = getAllObjectsWithObjVar(getLocation(self), 320000, "intDeleteOnReset");
            if (objTestObjects != null)
            {
                for (int intI = 0; intI < objTestObjects.length; intI++)
                {
                    destroyObject(objTestObjects[intI]);
                }
            }
            objTestObjects = getAllObjectsWithTemplate(getLocation(self), 320000, "object/ship/player/player_corellian_corvette.iff");
            messageTo(objTestObjects[0], "delayedSpawn", null, 1, false);
            objTestObjects = getAllObjectsWithTemplate(getLocation(self), 320000, "object/ship/player/player_star_destroyer.iff");
            obj_id objShip = objTestObjects[0];
            obj_id objCell = getCellId(objShip, "elevator_e3_up");
            location locTest = new location();
            locTest.cell = objCell;
            setLocation(self, locTest);
            messageTo(self, "beginMusicPlaying", null, 5, false);
        }
        if (strCommands[0].equalsIgnoreCase("makeMandalorian"))
        {
            createInInventory(self, MANDALORIAN_ARMOR);
            sendSystemMessageTestingOnly(self, "Made Mandalorian Armor");
        }
        if (strCommands[0].equalsIgnoreCase("makeWookieeArmor"))
        {
            createInInventory(self, WOOKIEE_ARMOR);
            sendSystemMessageTestingOnly(self, "Made Wookiee Armor");
        }
        if (strCommands[0].equalsIgnoreCase("makeShipComponents"))
        {
            createInInventory(self, SHIP_COMPONENTS);
            sendSystemMessageTestingOnly(self, "Made Ship ComponentsArmor");
        }
        if (strCommands[0].equalsIgnoreCase("nextColor"))
        {
            obj_id objWeapon = getCurrentWeapon(self);
            custom_var myVar = getCustomVarByName(objWeapon, "private/index_color_blade");
            if (myVar.isPalColor())
            {
                palcolor_custom_var pcVar = (palcolor_custom_var)myVar;
                int intColor = pcVar.getValue();
                intColor = intColor + 1;
                if (intColor > 11)
                {
                    intColor = 0;
                }
                pcVar.setValue(intColor);
            }
        }
        if (strCommands[0].equalsIgnoreCase("makeJediClothes"))
        {
            createInInventory(self, JEDI_CLOTHES);
            sendSystemMessageTestingOnly(self, "Made jedi clothes");
        }
        if (strCommands[0].equalsIgnoreCase("combatOn"))
        {
            setCombatTarget(self, getLookAtTarget(self));
            setState(self, STATE_COMBAT, true);
            sendSystemMessageTestingOnly(self, "COMBAT ON!");
        }
        if (strCommands[0].equalsIgnoreCase("combatOff"))
        {
            setCombatTarget(self, null);
            setState(self, STATE_COMBAT, false);
            sendSystemMessageTestingOnly(self, "COMBAT ON!");
        }
        if (strCommands[0].equalsIgnoreCase("makeLightsabers"))
        {
            String[] strItems = LIGHTSABERS;
            obj_id objInventory = utils.getInventoryContainer(self);
            for (int intI = 0; intI < strItems.length; intI++)
            {
                obj_id objTest = createObjectOverloaded(strItems[intI], objInventory);
                if (!isIdValid(objTest))
                {
                    sendSystemMessageTestingOnly(self, "Item of template " + strItems[intI] + " is Incorret!");
                }
                else 
                {
                    setObjVar(objTest, "crafting.source_schematic", objTest);
                    setCrafter(objTest, self);
                }
            }
            sendSystemMessageTestingOnly(self, "Made some lightsabers");
        }
        if (strCommands[0].equalsIgnoreCase("resetNebulon"))
        {
            obj_id[] objTestObjects = getAllObjectsWithObjVar(getLocation(self), 320000, "intNebulon");
            for (int intI = 0; intI < objTestObjects.length; intI++)
            {
                setObjVar(objTestObjects[intI], "intReset", 1);
                destroyObject(objTestObjects[intI]);
            }
            obj_id[] objNebulon = getAllObjectsWithScript(getLocation(self), 320000, "e3demo.spawner_nebulon");
            dictionary dctParams = new dictionary();
            space_utils.notifyObject(objNebulon[0], "spawnNebulon", dctParams);
            sendSystemMessageTestingOnly(self, "Reset nebulon");
        }
        if (strCommands[0].equalsIgnoreCase("destroyNebulon"))
        {
            obj_id[] objTestObjects = getAllObjectsWithObjVar(getLocation(self), 320000, "intNebulon");
            for (int intI = 0; intI < objTestObjects.length; intI++)
            {
                setObjVar(objTestObjects[intI], "intReset", 1);
                destroyObject(objTestObjects[intI]);
            }
        }
        if (strCommands[0].equalsIgnoreCase("openJediSlot"))
        {
            removeObjVar(self, "jedi.timeStamp");
            addJediSlot(self);
            playMusic(self, "sound/music_amb_underwater_b.snd");
            setObjVar(self, "jedi.enabled", 1);
            string_id strSpam = new string_id("jedi_spam", "force_sensitive");
            sendSystemMessage(self, strSpam);
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
        if (strCommands[0].equalsIgnoreCase("colorXwing"))
        {
            obj_id shipId = getPilotedShip(self);
            if (isIdValid(shipId))
            {
                String index = "/shared_owner/index_color_1";
                sui.colorize(self, self, shipId, index, "handleColorize");
            }
            return SCRIPT_CONTINUE;
        }
        if (strCommands[0].equalsIgnoreCase("patternXwing"))
        {
            obj_id shipId = getPilotedShip(self);
            if (isIdValid(shipId))
            {
                String index = "/shared_owner/index_texture_1";
                hue.setRangedIntCustomVar(shipId, index, 0);
            }
            return SCRIPT_CONTINUE;
        }
        if (strCommands[0].equalsIgnoreCase("patternTwoXwing"))
        {
            obj_id shipId = getPilotedShip(self);
            if (isIdValid(shipId))
            {
                String index = "/shared_owner/index_texture_1";
                hue.setRangedIntCustomVar(shipId, index, 1);
            }
            return SCRIPT_CONTINUE;
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
    public int beginMusicPlaying(obj_id self, dictionary params) throws InterruptedException
    {
        String bldgName = getTemplateName(getTopMostContainer(self));
        if (bldgName.equals("object/ship/player/player_star_destroyer.iff"))
        {
            play2dNonLoopingMusic(self, "sound/mus_imperial_march_excerpt.snd");
            messageTo(self, "beginMusicPlaying", null, 55, false);
        }
        return SCRIPT_CONTINUE;
    }
}
