package script.theme_park.newbie_tutorial;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.chat;
import script.library.colors;
import script.library.ai_lib;
import script.library.factions;
import script.library.create;
import script.library.money;
import script.library.utils;
import script.library.pclib;
import script.library.features;
import script.library.trainerlocs;
import script.library.weapons;
import script.library.skill;

public class tutorial_base extends script.base_script
{
    public tutorial_base()
    {
    }
    public static final String NEWBIE_STRING_FILE = "newbie_tutorial/system_messages";
    public static final String NEWBIE_CONVO = "newbie_tutorial/newbie_convo";
    public static final String NEWBIE_SCRIPT = "theme_park.newbie_tutorial.newbie";
    public static final String NEWBIE_SCRIPT_SKIPPED = "theme_park.newbie_tutorial.newbie_skipped";
    public static final String NEWBIE_SCRIPT_EMAIL = "theme_park.newbie_tutorial.newbie_mail";
    public static final String CONVO = "greeter_convo";
    public static final int NEWBIE_STARTING_MONEY = 250;
    public static final String STARTING_EQUIPMENT_FILE = "datatables/equipment/newbie_equipment.iff";
    public static final String NEWBIE_WEAPON = "object/weapon/ranged/pistol/pistol_cdef.iff";
    public static final String PIRATE_WEAPON = "object/weapon/ranged/pistol/pistol_d18.iff";
    public static final String[] COLLECTOR_EDITION_ITEMS = 
    {
        "object/tangible/wearables/goggles/goggles_s01.iff",
        "object/tangible/wearables/goggles/goggles_s02.iff",
        "object/tangible/wearables/goggles/goggles_s03.iff"
    };
    public static final String[] JP_COLLECTOR_EDITION_ITEMS = 
    {
        "object/tangible/wearables/goggles/goggles_s04.iff",
        "object/tangible/wearables/goggles/goggles_s05.iff",
        "object/tangible/wearables/goggles/goggles_s06.iff"
    };
    public static final String NEWBIE_PLAYER = "newbie.player";
    public static final int SHORT_DELAY = 10;
    public static final int MEDIUM_DELAY = 15;
    public static final int LONG_DELAY = 20;
    public static final int VERY_LONG_DELAY = 45;
    public static final String TROOPER1 = "newbie.trooper1";
    public static final float TROOPER1_LOCATION_X = -9.18f;
    public static final float TROOPER1_LOCATION_Y = 0.02f;
    public static final float TROOPER1_LOCATION_Z = 3.61f;
    public static final String TROOPER1_LOCATION_CELL = "r1";
    public static final String TROOPER_TYPE = "stormtrooper";
    public static final String TROOPER1_SCRIPT = "theme_park.newbie_tutorial.trooper1";
    public static final String TROOPER2 = "newbie.trooper2";
    public static final float TROOPER2_LOCATION_X = -1.89f;
    public static final float TROOPER2_LOCATION_Y = 0.00f;
    public static final float TROOPER2_LOCATION_Z = -16.47f;
    public static final String TROOPER2_LOCATION_CELL = "r1";
    public static final String TROOPER2_SCRIPT = "theme_park.newbie_tutorial.trooper2";
    public static final String TROOPER3 = "newbie.trooper3";
    public static final float TROOPER3_LOCATION_X = 19.26f;
    public static final float TROOPER3_LOCATION_Y = -4.2f;
    public static final float TROOPER3_LOCATION_Z = -160.32f;
    public static final String TROOPER3_LOCATION_CELL = "r11";
    public static final String TROOPER3_SCRIPT = "theme_park.newbie_tutorial.trooper3";
    public static final String TROOPER4 = "newbie.trooper4";
    public static final float TROOPER4_LOCATION_X = 31.96f;
    public static final float TROOPER4_LOCATION_Y = -4.2f;
    public static final float TROOPER4_LOCATION_Z = -163.19f;
    public static final String TROOPER4_LOCATION_CELL = "r11";
    public static final String TROOPER4_SCRIPT = "theme_park.newbie_tutorial.trooper4";
    public static final String GREETER = "newbie.greeter";
    public static final float GREETER_LOCATION_X = 34.94f;
    public static final float GREETER_LOCATION_Y = 0.00f;
    public static final float GREETER_LOCATION_Z = -31.75f;
    public static final String GREETER_LOCATION_CELL = "r2";
    public static final String GREETER_TYPE = "imperial_warrant_officer_i";
    public static final String GREETER_SCRIPT = "theme_park.newbie_tutorial.greeter";
    public static final String ROOM1_GREETER = "newbie.room1greeter";
    public static final float ROOM1_GREETER_LOC_X = -9.49f;
    public static final float ROOM1_GREETER_LOC_Y = -3.87f;
    public static final float ROOM1_GREETER_LOC_Z = -3.87f;
    public static final String ROOM1_GREETER_LOC_CELL = "r1";
    public static final String ROOM1_GREETER_TYPE = "imperial_private";
    public static final String ROOM1_GREETER_SCRIPT = "theme_park.newbie_tutorial.greeter_one";
    public static final float ROOM1_GREETER_MID_X = .91f;
    public static final float ROOM1_GREETER_MID_Y = 0.00f;
    public static final float ROOM1_GREETER_MID_Z = -31.76f;
    public static final String ROOM1_GREETER_MID_CELL = "r2";
    public static final float ROOM1_GREETER_DEST_X = 31.23f;
    public static final float ROOM1_GREETER_DEST_Y = -0.00f;
    public static final float ROOM1_GREETER_DEST_Z = -29.48f;
    public static final String ROOM1_GREETER_DEST_CELL = "r2";
    public static final String COMMONER1 = "newbie.commoner1";
    public static final float COMMONER1_LOCATION_X = 19.72f;
    public static final float COMMONER1_LOCATION_Y = 0.00f;
    public static final float COMMONER1_LOCATION_Z = -23.05f;
    public static final String COMMONER_LOCATION_CELL = "r2";
    public static final String COMMONER_TYPE = "commoner";
    public static final String COMMONER1_SCRIPT = "theme_park.newbie_tutorial.commoner1";
    public static final String COMMONER2 = "newbie.commoner2";
    public static final float COMMONER2_LOCATION_X = 18.48f;
    public static final float COMMONER2_LOCATION_Y = 0.00f;
    public static final float COMMONER2_LOCATION_Z = -24.08f;
    public static final String COMMONER2_SCRIPT = "theme_park.newbie_tutorial.commoner2";
    public static final String REFUGEE = "newbie.refugee";
    public static final float REFUGEE_LOCATION_X = 38.81f;
    public static final float REFUGEE_LOCATION_Y = -4.2f;
    public static final float REFUGEE_LOCATION_Z = -166.12f;
    public static final String CRATE = "newbie.room2crate";
    public static final float CRATE_LOCATION_X = 34.80f;
    public static final float CRATE_LOCATION_Y = 0.00f;
    public static final float CRATE_LOCATION_Z = -33.10f;
    public static final String CRATE_LOCATION_CELL = "r2";
    public static final String CRATE_TEMPLATE = "object/tangible/container/drum/tatt_drum_1.iff";
    public static final String CRATE_SCRIPT = "theme_park.newbie_tutorial.box_of_stuff";
    public static final String[] BOX_CONTENTS = 
    {
        "object/tangible/skill_buff/skill_buff_pistol_accuracy.iff",
        "object/tangible/food/foraged/foraged_fruit_s1.iff"
    };
    public static final String BOX_ITEM_SCRIPT = "theme_park.newbie_tutorial.box_item";
    public static final String BAZAAR = "newbie.bazaar";
    public static final float BAZAAR_LOCATION_X = 54.95f;
    public static final float BAZAAR_LOCATION_Y = -0.50f;
    public static final float BAZAAR_LOCATION_Z = -0.68f;
    public static final String BAZAAR_LOCATION_CELL = "r3";
    public static final float BAZAAR_YAW = -124.0f;
    public static final String BAZAAR_TEMPLATE = "object/tangible/terminal/terminal_newbie_clothing.iff";
    public static final String BAZAAR_SCRIPT = "theme_park.newbie_tutorial.bazaar";
    public static final String BANK = "newbie.bank";
    public static final float BANK_LOCATION_X = 50.83f;
    public static final float BANK_LOCATION_Y = -0.5f;
    public static final float BANK_LOCATION_Z = -3.63f;
    public static final String BANK_LOCATION_CELL = "r3";
    public static final float BANK_YAW = -91.0f;
    public static final String BANK_TEMPLATE = "object/tangible/terminal/terminal_bank.iff";
    public static final String BANK_SCRIPT = "theme_park.newbie_tutorial.bank";
    public static final String BANKER = "newbie.banker";
    public static final float BANKER_LOCATION_X = 48.27f;
    public static final float BANKER_LOCATION_Y = -0.50f;
    public static final float BANKER_LOCATION_Z = 1.12f;
    public static final String BANKER_LOCATION_CELL = "r3";
    public static final String BANKER_TYPE = "imperial_warrant_officer_ii";
    public static final String BANKER_SCRIPT = "theme_park.newbie_tutorial.banker";
    public static final String BANK2 = "newbie.bank2";
    public static final float BANK2_LOCATION_X = 41.6f;
    public static final float BANK2_LOCATION_Y = -0.0f;
    public static final float BANK2_LOCATION_Z = 7.37f;
    public static final String BANK2_LOCATION_CELL = "r3";
    public static final float BANK2_YAW = 179.0f;
    public static final String BANK2_SCRIPT = "theme_park.newbie_tutorial.bank2";
    public static final String BANK3 = "newbie.bank3";
    public static final float BANK3_LOCATION_X = 46.18f;
    public static final float BANK3_LOCATION_Y = -0.0f;
    public static final float BANK3_LOCATION_Z = 6.35f;
    public static final String BANK3_LOCATION_CELL = "r3";
    public static final float BANK3_YAW = -179.0f;
    public static final String BANK_USER1 = "newbie.bankUser1";
    public static final float BANK_USER1_LOCATION_X = 41.65f;
    public static final float BANK_USER1_LOCATION_Y = 0.00f;
    public static final float BANK_USER1_LOCATION_Z = 6.35f;
    public static final String BANK_USER1_SCRIPT = "theme_park.newbie_tutorial.bankuser";
    public static final String BANK_USER2 = "newbie.bankUser2";
    public static final float BANK_USER2_LOCATION_X = 46.07f;
    public static final float BANK_USER2_LOCATION_Y = 0.00f;
    public static final float BANK_USER2_LOCATION_Z = 6.55f;
    public static final String BANK_USER2_SCRIPT = "theme_park.newbie_tutorial.bankuser";
    public static final String CLONE_NPC = "newbie.cloneNpc";
    public static final float CLONE_NPC_LOCATION_X = 24.55f;
    public static final float CLONE_NPC_LOCATION_Y = -7.00f;
    public static final float CLONE_NPC_LOCATION_Z = -55.82f;
    public static final String CLONE_NPC_LOCATION_CELL = "r4";
    public static final String CLONE_NPC_TYPE = "protocol_droid_3po_silver";
    public static final String CLONE_NPC_SCRIPT = "theme_park.newbie_tutorial.clone_npc";
    public static final String CLONE_TERMINAL = "newbie.cloneTerminal";
    public static final float CLONE_TERMINAL_LOCATION_X = 2.91f;
    public static final float CLONE_TERMINAL_LOCATION_Y = -7.00f;
    public static final float CLONE_TERMINAL_LOCATION_Z = -56.54f;
    public static final String CLONE_TERMINAL_LOCATION_CELL = "r5";
    public static final float CLONE_TERMINAL_YAW = 89.0f;
    public static final String CLONE_TERMINAL_TEMPLATE = "object/tangible/terminal/terminal_cloning.iff";
    public static final String CLONE_TERMINAL_SCRIPT = "theme_park.newbie_tutorial.clone_terminal";
    public static final float CLONE_PROMPT_LOCATION_X = 0.81f;
    public static final float CLONE_PROMPT_LOCATION_Y = -7.00f;
    public static final float CLONE_PROMPT_LOCATION_Z = -59.73f;
    public static final String CLONE_PROMPT_LOCATION_CELL = "r5";
    public static final String INSURANCE = "newbie.insurance";
    public static final float INSURANCE_LOCATION_X = -0.47f;
    public static final float INSURANCE_LOCATION_Y = -7.00f;
    public static final float INSURANCE_LOCATION_Z = -66.48f;
    public static final String INSURANCE_LOCATION_CELL = "r5";
    public static final float INSURANCE_YAW = 2.00f;
    public static final String INSURANCE_TEMPLATE = "object/tangible/terminal/terminal_insurance.iff";
    public static final String INSURANCE_SCRIPT = "theme_park.newbie_tutorial.insurance";
    public static final String MOUSE_DROID = "newbie.mouseDroid";
    public static final String MOUSE_DROID_TYPE = "mouse_droid";
    public static final float MOUSE_DROID_LOC_X = 76.78f;
    public static final float MOUSE_DROID_LOC_Y = -4.00f;
    public static final float MOUSE_DROID_LOC_Z = -102.02f;
    public static final String MOUSE_DROID_LOC_CELL = "r14";
    public static final float MOUSE_DROID_DEST_X = 47.13f;
    public static final float MOUSE_DROID_DEST_Y = -7.00f;
    public static final float MOUSE_DROID_DEST_Z = -51.5f;
    public static final String MOUSE_DROID_DEST_CELL = "r4";
    public static final String MOUSE_DROID_SCRIPT = "theme_park.newbie_tutorial.mouse";
    public static final String MOUSE_DROID2 = "newbie.mouseDroid2";
    public static final float MOUSE_DROID2_LOC_X = 46.55f;
    public static final float MOUSE_DROID2_LOC_Y = -0.00f;
    public static final float MOUSE_DROID2_LOC_Z = -31.42f;
    public static final String MOUSE_DROID2_LOC_CELL = "r3";
    public static final float MOUSE_DROID2_DEST_X = 0.17f;
    public static final float MOUSE_DROID2_DEST_Y = -0.01f;
    public static final float MOUSE_DROID2_DEST_Z = -6.61f;
    public static final String MOUSE_DROID2_DEST_CELL = "r1";
    public static final String MOUSE_DROID2_SCRIPT = "theme_park.newbie_tutorial.mouse2";
    public static final String COMBAT_EXPLAINER = "newbie.combatExplainer";
    public static final String COMBAT_EXPLAINER_TYPE = "imperial_master_sergeant";
    public static final float COMBAT_EXPLAINER_X = 43.74f;
    public static final float COMBAT_EXPLAINER_Y = -7.00f;
    public static final float COMBAT_EXPLAINER_Z = -75.20f;
    public static final String COMBAT_EXPLAINER_CELL = "r7";
    public static final String COMBAT_EXPLAINER_SCRIPT = "theme_park.newbie_tutorial.combat_explainer";
    public static final float COMBAT_EXPLAINER_YAW = -88.0f;
    public static final String PIRATE = "newbie.pirate";
    public static final String PIRATE_TYPE = "bandit";
    public static final String PIRATE_TEMPLATE = "dressed_criminal_thug_aqualish_male_01.iff";
    public static final float PIRATE_LOCATION_X = 38.9f;
    public static final float PIRATE_LOCATION_Y = -5.94f;
    public static final float PIRATE_LOCATION_Z = -113.59f;
    public static final String PIRATE_LOCATION_CELL = "r8";
    public static final String PIRATE_SCRIPT = "theme_park.newbie_tutorial.pirate";
    public static final String TRAINER = "newbie.trainer";
    public static final float TRAINER_LOCATION_X = 3.69f;
    public static final float TRAINER_LOCATION_Y = -4.20f;
    public static final float TRAINER_LOCATION_Z = -127.69f;
    public static final String TRAINER_LOCATION_CELL = "r9";
    public static final String TRAINER_SCRIPT = "theme_park.newbie_tutorial.trainer";
    public static final String OFFICER = "newbie.officer";
    public static final String OFFICER_TYPE = "imperial_lance_corporal";
    public static final float OFFICER_LOCATION_X = 0.66f;
    public static final float OFFICER_LOCATION_Y = -4.20f;
    public static final float OFFICER_LOCATION_Z = -113.31f;
    public static final String OFFICER_LOCATION_CELL = "r9";
    public static final String OFFICER_SCRIPT = "theme_park.newbie_tutorial.officer";
    public static final String PILOT = "newbie.pilot";
    public static final String PILOT_TYPE = "imperial_lance_corporal";
    public static final String PILOT_TEMPLATE = "dressed_tie_fighter_m.iff";
    public static final float PILOT_LOCATION_X = 25.3f;
    public static final float PILOT_LOCATION_Y = -4.20f;
    public static final float PILOT_LOCATION_Z = -160.4f;
    public static final String PILOT_LOCATION_CELL = "r11";
    public static final float PILOT_YAW = 55.0f;
    public static final String PILOT_SCRIPT = "conversation.tutorial_imperial_broker";
    public static final String MISSION = "newbie.mission_terminal";
    public static final String MISSION_TEMPLATE = "object/tangible/terminal/terminal_mission_newbie.iff";
    public static final float MISSION_LOCATION_X = 19.61f;
    public static final float MISSION_LOCATION_Y = -4.20f;
    public static final float MISSION_LOCATION_Z = -137.97f;
    public static final String MISSION_LOCATION_CELL = "r10";
    public static final float MISSION_YAW = -144.0f;
    public static final String MISSION_SCRIPT = "theme_park.newbie_tutorial.mission_terminal";
    public static final String MISSION_NPC = "newbie.mission_npc";
    public static final String MISSION_NPC_TYPE = "imperial_corporal";
    public static final float MISSION_NPC_LOC_X = 19.51f;
    public static final float MISSION_NPC_LOC_Y = -4.20f;
    public static final float MISSION_NPC_LOC_Z = -145.35f;
    public static final String MISSION_NPC_LOC_CELL = "r10";
    public static final String MISSION_NPC_SCRIPT = "theme_park.newbie_tutorial.mission_npc";
    public static final String TRAVEL_TERMINAL = "newbie.travelTerminal";
    public static final String TRAVEL_TERMINAL_TEMPLATE = "object/tangible/terminal/terminal_travel.iff";
    public static final float TRAVEL_TERMINAL_LOC_X = 27.55f;
    public static final float TRAVEL_TERMINAL_LOC_Y = -3.50f;
    public static final float TRAVEL_TERMINAL_LOC_Z = -167.79f;
    public static final String TRAVEL_TERMINAL_LOC_CELL = "r11";
    public static final String TRAVEL_TERMINAL_SCRIPT = "theme_park.newbie_tutorial.travel_terminal";
    public static final String TRAVEL_NPC = "newbie.travelNpc";
    public static final String TRAVEL_NPC_TYPE = "imperial_sergeant";
    public static final float TRAVEL_NPC_LOC_X = 25.37f;
    public static final float TRAVEL_NPC_LOC_Y = -4.20f;
    public static final float TRAVEL_NPC_LOC_Z = -158.33f;
    public static final String TRAVEL_NPC_LOC_CELL = "r11";
    public static final String TRAVEL_NPC_SCRIPT = "theme_park.newbie_tutorial.travel_npc";
    public static final String INS_USER = "newbie.insuranceUser";
    public static final String INS_USER_TYPE = "commoner";
    public static final float INS_USER_LOC_X = -0.73f;
    public static final float INS_USER_LOC_Y = -7.00f;
    public static final float INS_USER_LOC_Z = -64.85f;
    public static final float INS_USER_YAW = 177.0f;
    public static final String INS_USER_LOC_CELL = "r5";
    public static final String INS_USER_SCRIPT = "theme_park.newbie_tutorial.insurance_user";
    public static final String PANIC_GUY = "newbie.panicMan";
    public static final String PANIC_GUY_TYPE = "commoner";
    public static final float PANIC_GUY_X = 18.25f;
    public static final float PANIC_GUY_Y = -4.00f;
    public static final float PANIC_GUY_Z = -76.88f;
    public static final String PANIC_GUY_CELL = "r6";
    public static final String PANIC_GUY_SCRIPT = "theme_park.newbie_tutorial.panic_guy";
    public static final String DEBRIS = "newbie.debris";
    public static final String DEBRIS_TEMPLATE = "object/tangible/newbie_tutorial/debris.iff";
    public static final String DEBRIS_CELL = "r14";
    public static final float DEBRIS_X = 77.1f;
    public static final float DEBRIS_Y = -4.0f;
    public static final float DEBRIS_Z = -108.9f;
    public static final float DEBRIS_YAW = 180.0f;
    public static final String STATIC_WAYPOINT = "newbie.staticWaypoint";
    public static final String STATIC_WAYPOINT_TEMPLATE = "object/static/structure/general/tutorial_waypoint.iff";
    public static final string_id IMPERIAL_NAME = new string_id(NEWBIE_STRING_FILE, "imp_name");
    public static final string_id QUARTERMASTER_NAME = new string_id(NEWBIE_STRING_FILE, "quarter_name");
    public static final string_id STARTLOC_NAME = new string_id(NEWBIE_STRING_FILE, "start_loc");
    public static final string_id DROID_NAME = new string_id(NEWBIE_STRING_FILE, "droid_name");
    public static final String NERVOUS_GUY = "newbie.nervousGuys";
    public static final String NERVOUS_GUY_TYPE = "commoner";
    public static final float[] NERVOUS_GUY_X = 
    {
        43.13f,
        37.51f,
        39.01f
    };
    public static final float[] NERVOUS_GUY_Y = 
    {
        -7.0f,
        -7.0f,
        -7f
    };
    public static final float[] NERVOUS_GUY_Z = 
    {
        -70.37f,
        -70.02f,
        -79.65f
    };
    public static final float[] NERVOUS_GUY_YAW = 
    {
        -163f,
        108f,
        15f
    };
    public static final String NERVOUS_GUY_CELL = "r7";
    public static final String NERVOUS_GUY_SCRIPT = "theme_park.newbie_tutorial.nervous_guy";
    public static final String CELEB_GUY = "newbie.celebGuys";
    public static final String CELEB_GUY_TYPE = "commoner";
    public static final float[] CELEB_GUY_X = 
    {
        7.6f,
        1.76f
    };
    public static final float[] CELEB_GUY_Y = 
    {
        -4.2f,
        -4.2f
    };
    public static final float[] CELEB_GUY_Z = 
    {
        -105.5f,
        -108.22f
    };
    public static final float[] CELEB_GUY_YAW = 
    {
        -173f,
        134f
    };
    public static final String CELEB_GUY_CELL = "r9";
    public static final String CELEB_GUY_SCRIPT = "theme_park.newbie_tutorial.celeb_guy";
    public static final String TRAPPED_GUY = "newbie.trappedGuy";
    public static final String TRAPPED_GUY_TYPE = "commoner";
    public static final float TRAPPED_GUY_X = 17.75f;
    public static final float TRAPPED_GUY_Y = -4.2f;
    public static final float TRAPPED_GUY_Z = -144.64f;
    public static final float TRAPPED_GUY_YAW = 110f;
    public static final String TRAPPED_GUY_CELL = "r10";
    public static final String TRAPPED_GUY_SCRIPT = "theme_park.newbie_tutorial.trapped_guy";
    public static final String NEW_PLAYER_QUESTS_SCRIPT = "theme_park.new_player.new_player";
    public void sysMessageToPlayer(obj_id box, String text) throws InterruptedException
    {
    }
    public obj_id getPlayer(obj_id box) throws InterruptedException
    {
        if (hasObjVar(box, "newbie.player"))
        {
            return getObjIdObjVar(box, "newbie.player");
        }
        else 
        {
            obj_id player = getObjIdObjVar(getTopMostContainer(box), "newbie.player");
            setObjVar(box, "newbie.player", player);
            return player;
        }
    }
    public void sysMessage(obj_id player, String text) throws InterruptedException
    {
    }
    public void flyText(obj_id item, String text, float size, color flyTextColor) throws InterruptedException
    {
        showFlyText(item, new string_id(NEWBIE_STRING_FILE, text), size, flyTextColor);
    }
    public obj_id getBuilding(obj_id item) throws InterruptedException
    {
        return getTopMostContainer(item);
    }
    public boolean isInRoom(obj_id player, String roomName) throws InterruptedException
    {
        if (detachNewbieTutorialScript(player))
        {
            return false;
        }
        obj_id currentRoom = getContainedBy(player);
        obj_id bldg = getTopMostContainer(player);
        if (!isIdValid(currentRoom))
        {
            detachScript(player, NEWBIE_SCRIPT);
            return false;
        }
        obj_id roomId = getCellId(bldg, roomName);
        if (isIdValid(roomId) && roomId == currentRoom)
        {
            if (roomName.equals("r4"))
            {
                transferBankToInventory(player);
            }
            if (!hasCompletedRoom(player, roomName))
            {
                return true;
            }
        }
        return false;
    }
    public void flagRoomComplete(obj_id player, String roomName) throws InterruptedException
    {
        utils.setScriptVar(player, "newbie.roomComplete." + roomName, true);
    }
    public boolean hasCompletedRoom(obj_id player, String roomName) throws InterruptedException
    {
        return (utils.hasScriptVar(player, "newbie.roomComplete." + roomName));
    }
    public boolean detachNewbieTutorialScript(obj_id player) throws InterruptedException
    {
        if (hasObjVar(player, "newbie.detachScript"))
        {
            detachScript(player, NEWBIE_SCRIPT);
            return true;
        }
        return false;
    }
    public String getProfessionName(obj_id player) throws InterruptedException
    {
        if (utils.hasScriptVar(player, "newbie.profession"))
        {
            return utils.getStringScriptVar(player, "newbie.profession");
        }
        if (!hasObjVar(player, "newbie.hasSkill"))
        {
            String[] skillList = getSkillListingForPlayer(player);
            if (skillList == null)
            {
                LOG("newbie", "WARNING: SKILL LIST IS NULL");
                return "trainer_pistol";
            }
            if (skillList.length < 1)
            {
                LOG("newbie", "WARNING: SKILL LIST IS 0-LENGTH");
                return "trainer_pistol";
            }
            int startingSkillIndex = skillList.length - 1;
            String skillName = null;
            for (int i = 0; i < skillList.length; i++)
            {
                if (skillList[i].endsWith("_novice"))
                {
                    skillName = skillList[i];
                }
            }
            if (skillName == null || skillName.equals(""))
            {
                skillName = "combat_marksman_novice";
            }
            setObjVar(player, "newbie.hasSkill", skillName);
        }
        String skillName = getStringObjVar(player, "newbie.hasSkill");
        String profession = getSkillProfession(skillName);
        if (profession == null)
        {
            LOG("newbie", "WARNING: NO PROFESSION FOR SKILL " + skillName);
            return "trainer_pistol";
        }
        utils.setScriptVar(player, "newbie.profession", profession);
        return profession;
    }
    public String getTrainerType(obj_id player) throws InterruptedException
    {
        String profession = getProfessionName(player);
        if (profession == null)
        {
            return "trainer_pistol";
        }
        if (profession.equals("social_entertainer"))
        {
            return "trainer_entertainer";
        }
        if (profession.equals("outdoors_scout"))
        {
            return "trainer_scout";
        }
        if (profession.equals("science_medic"))
        {
            return "trainer_medic";
        }
        if (profession.equals("crafting_artisan"))
        {
            return "trainer_artisan";
        }
        if (profession.equals("combat_brawler"))
        {
            return "trainer_brawler";
        }
        if (profession.equals("combat_marksman"))
        {
            return "trainer_marksman";
        }
        if (profession.equals("combat_rifleman"))
        {
            return "trainer_rifleman";
        }
        if (profession.equals("combat_pistol"))
        {
            return "trainer_pistol";
        }
        if (profession.equals("combat_carbine"))
        {
            return "trainer_carbine";
        }
        if (profession.equals("combat_unarmed"))
        {
            return "trainer_unarmed";
        }
        if (profession.equals("combat_1hsword"))
        {
            return "trainer_1hsword";
        }
        if (profession.equals("combat_2hsword"))
        {
            return "trainer_2hsword";
        }
        if (profession.equals("combat_polearm"))
        {
            return "trainer_polearm";
        }
        if (profession.equals("social_dancer"))
        {
            return "trainer_dancer";
        }
        if (profession.equals("social_musician"))
        {
            return "trainer_musician";
        }
        if (profession.equals("science_doctor"))
        {
            return "trainer_doctor";
        }
        if (profession.equals("outdoors_ranger"))
        {
            return "trainer_ranger";
        }
        if (profession.equals("outdoors_creaturehandler"))
        {
            return "trainer_creaturehandler";
        }
        if (profession.equals("outdoors_farmer"))
        {
            return "trainer_farmer";
        }
        if (profession.equals("outdoors_miner"))
        {
            return "trainer_miner";
        }
        if (profession.equals("crafting_armorsmith"))
        {
            return "trainer_armorsmith";
        }
        if (profession.equals("crafting_weaponsmith"))
        {
            return "trainer_weaponsmith";
        }
        if (profession.equals("crafting_chef"))
        {
            return "trainer_chef";
        }
        if (profession.equals("crafting_tailor"))
        {
            return "trainer_tailor";
        }
        if (profession.equals("crafting_architect"))
        {
            return "trainer_architect";
        }
        if (profession.equals("crafting_droidengineer"))
        {
            return "trainer_droidengineer";
        }
        if (profession.equals("crafting_industrialist"))
        {
            return "trainer_industrialist";
        }
        if (profession.equals("combat_bountyhunter"))
        {
            return "trainer_bountyhunter";
        }
        if (profession.equals("combat_commando"))
        {
            return "trainer_commando";
        }
        if (profession.equals("combat_smuggler"))
        {
            return "trainer_smuggler";
        }
        if (profession.equals("science_combatmedic"))
        {
            return "trainer_combatmedic";
        }
        if (profession.equals("social_imagedesigner"))
        {
            return "trainer_imagedesigner";
        }
        if (profession.equals("outdoors_squadleader"))
        {
            return "trainer_squadleader";
        }
        LOG("newbie", "WARNING: UNABLE TO DETERMINE PLAYER STARTING PROFESSION");
        return "trainer_pistol";
    }
    public void fillbank(obj_id player) throws InterruptedException
    {
        if (hasObjVar(player, "newbie.youGotBank"))
        {
            return;
        }
        else 
        {
            setObjVar(player, "newbie.youGotBank", true);
        }
        String profession = "all_professions";
        obj_id bank = utils.getInventoryContainer(player);
        if (!isIdValid(bank))
        {
            LOG("newbie", "BAD: cannot locate new player's bank");
            return;
        }
        if (!dataTableHasColumn(STARTING_EQUIPMENT_FILE, profession))
        {
            LOG("newbie", "BAD: cannot locate starting equip for profession: " + profession);
            return;
        }
        String[] items = dataTableGetStringColumnNoDefaults(STARTING_EQUIPMENT_FILE, profession);
        if (items == null)
        {
            LOG("newbie", "no items");
        }
        else if (items.length < 1)
        {
            LOG("newbie", "no items");
        }
        for (int i = 0; i < items.length; i++)
        {
            LOG("newbie", "NEWBIE STARTING EQUIP MAKING: " + items[i]);
            obj_id newItem = null;
            if (items[i].startsWith("object/weapon/"))
            {
                newItem = weapons.createWeapon(items[i], bank, 0.75f);
            }
            else if (items[i].startsWith("object/tangible/medicine"))
            {
                newItem = createObject("object/tangible/medicine/instant_stimpack/stimpack_noob.iff", bank, "");
                if (isIdValid(newItem))
                {
                    setCount(newItem, 5);
                    setObjVar(newItem, "healing.power", 250);
                }
            }
            else 
            {
                newItem = createObject(items[i], bank, "");
            }
            if (!isIdValid(newItem))
            {
                LOG("newbie", "BAD: could not create newbie equipment item " + items[i]);
            }
            else 
            {
                pclib.autoInsureItem(newItem);
            }
        }
    }
    public void transferBankToInventory(obj_id player) throws InterruptedException
    {
        obj_id bank = utils.getPlayerBank(player);
        obj_id playerInv = utils.getInventoryContainer(player);
        obj_id[] invContents = getContents(playerInv);
        if (invContents != null && invContents.length > 0)
        {
            for (int i = 0; i < invContents.length; i++)
            {
                if (hasObjVar(invContents[i], "newbie.item"))
                {
                    if (hasScript(invContents[i], BOX_ITEM_SCRIPT))
                    {
                        detachScript(invContents[i], BOX_ITEM_SCRIPT);
                    }
                    destroyObject(invContents[i]);
                }
            }
        }
        obj_id[] contents = getContents(bank);
        if (contents != null && contents.length > 0)
        {
            for (int i = 0; i < contents.length; i++)
            {
                if (!hasObjVar(contents[i], "newbie.item"))
                {
                    putIn(contents[i], playerInv);
                }
                else 
                {
                    if (hasScript(contents[i], BOX_ITEM_SCRIPT))
                    {
                        detachScript(contents[i], BOX_ITEM_SCRIPT);
                    }
                    destroyObject(contents[i]);
                }
            }
        }
    }
    public boolean containsPlayer(obj_id bldg) throws InterruptedException
    {
        obj_id[] contents = getContents(bldg);
        if (contents != null)
        {
            for (int i = 0; i < contents.length; ++i)
            {
                if (isPlayer(contents[i]) || containsPlayer(contents[i]))
                {
                    return true;
                }
            }
        }
        return false;
    }
    public void grantNewbieStartingMoney(obj_id player) throws InterruptedException
    {
        int amt = NEWBIE_STARTING_MONEY;
        String configStartingMoneyString = getConfigSetting("New_Player", "NewbieStartingMoney");
        int configStartingMoneyAmt = utils.stringToInt(configStartingMoneyString);
        if (configStartingMoneyAmt > 0)
        {
            amt = configStartingMoneyAmt;
        }
        if (money.hasFunds(player, money.MT_TOTAL, amt))
        {
            return;
        }
        dictionary d = new dictionary();
        d.put(money.DICT_AMOUNT, amt);
        transferBankCreditsFromNamedAccount(money.ACCT_NEWBIE_TUTORIAL, player, amt, "timeToWithdraw", "xferFailed", d);
        utils.moneyInMetric(player, money.ACCT_NEWBIE_TUTORIAL, amt);
    }
    public void sendStartingMessage(obj_id player) throws InterruptedException
    {
        if (utils.hasScriptVar(player, "newbie.mailSent"))
        {
            return;
        }
        else 
        {
            utils.setScriptVar(player, "newbie.mailSent", true);
        }
        attachScript(player, NEWBIE_SCRIPT_EMAIL);
        messageTo(player, "handleSendStartingMail", null, 10, true);
    }
    public void sendStartingMessageToPlayer(obj_id player) throws InterruptedException
    {
        string_id subject = null;
        string_id body = null;
        if (isJedi(player))
        {
            subject = new string_id("jedi_spam", "welcome_subject");
            body = new string_id("jedi_spam", "welcome_body");
        }
        else 
        {
            subject = new string_id("newbie_tutorial/newbie_mail", "welcome_subject");
            body = new string_id("newbie_tutorial/newbie_mail", "welcome_body");
        }
        utils.sendMail(subject, body, player, "system");
        obj_id playerInv = utils.getInventoryContainer(player);
        if (features.isCollectorEdition(player))
        {
            subject = new string_id("newbie_tutorial/newbie_mail", "collector_subject");
            body = new string_id("newbie_tutorial/newbie_mail", "collector_body");
            utils.sendMail(subject, body, player, "system");
            for (int i = 0; i < COLLECTOR_EDITION_ITEMS.length; i++)
            {
                obj_id newItem = createObject(COLLECTOR_EDITION_ITEMS[i], playerInv, "");
                if (!isIdValid(newItem))
                {
                    LOG("newbie", "WARNING: PLAYER " + player + " did not receive his collector's edition item: " + COLLECTOR_EDITION_ITEMS[i]);
                }
                else 
                {
                    setObjVar(newItem, "notrade", true);
                }
            }
        }
        else if (features.isJPCollectorEdition(player))
        {
            subject = new string_id("newbie_tutorial/newbie_mail", "collector_subject");
            body = new string_id("newbie_tutorial/newbie_mail", "collector_body");
            utils.sendMail(subject, body, player, "system");
            for (int i = 0; i < JP_COLLECTOR_EDITION_ITEMS.length; i++)
            {
                obj_id newItem = createObject(JP_COLLECTOR_EDITION_ITEMS[i], playerInv, "");
                if (!isIdValid(newItem))
                {
                    LOG("newbie", "WARNING: PLAYER " + player + " did not receive his collector's edition item: " + COLLECTOR_EDITION_ITEMS[i]);
                }
                else 
                {
                    setObjVar(newItem, "notrade", true);
                }
            }
        }
    }
    public void deleteInventory(obj_id player) throws InterruptedException
    {
        if (!hasObjVar(player, "newbie.itemsHosed"))
        {
            obj_id playerInv = getObjectInSlot(player, "inventory");
            if (playerInv == null)
            {
                return;
            }
            setObjVar(player, "newbie.itemsHosed", true);
            obj_id[] contents = getContents(playerInv);
            for (int i = 0; i < contents.length; i++)
            {
                destroyObject(contents[i]);
            }
        }
    }
    public void makeStaticWaypoint(obj_id object) throws InterruptedException
    {
        if (!isIdValid(object))
        {
            return;
        }
        removeStaticWaypoint(object);
        obj_id bldg = getTopMostContainer(object);
        obj_id newWaypoint = createObject(STATIC_WAYPOINT_TEMPLATE, getLocation(object));
        setObjVar(bldg, STATIC_WAYPOINT, newWaypoint);
    }
    public void removeStaticWaypoint(obj_id object) throws InterruptedException
    {
        if (!isIdValid(object))
        {
            return;
        }
        obj_id bldg = getTopMostContainer(object);
        if (hasObjVar(bldg, STATIC_WAYPOINT))
        {
            obj_id oldWaypoint = getObjIdObjVar(bldg, STATIC_WAYPOINT);
            destroyObject(oldWaypoint);
        }
    }
    public void givePlayerSpaceTrainerWaypoints(obj_id player) throws InterruptedException
    {
        if (!hasObjVar(player, "chosenStartTrack"))
        {
            return;
        }
        String playerArea = getLocation(player).area;
        String chosenStartTrack = getStringObjVar(player, "chosenStartTrack");
        if (playerArea != null && playerArea.equals("talus"))
        {
            playerArea = "corellia";
        }
        obj_id trainerWayp = trainerlocs.getTrainerLocationWaypoint(player, chosenStartTrack, playerArea);
        removeObjVar(player, "chosenStartTrack");
        location destination = getWaypointLocation(trainerWayp);
        location origin = getLocation(player);
        destroyClientPath(player);
        createClientPath(player, origin, destination);
        utils.setScriptVar(player, "jtlNewbieStartLoc", origin);
        utils.setScriptVar(player, "jtlNewbieTrainerLoc", destination);
    }
    public void sendToStartLocation(obj_id self, String name) throws InterruptedException
    {
        boolean available = true;
        newbieTutorialSendStartingLocationSelectionResult(self, name, available);
        if (!available)
        {
            return;
        }
        float radius = dataTableGetFloat("datatables/creation/starting_locations.iff", name, "radius");
        float heading = dataTableGetFloat("datatables/creation/starting_locations.iff", name, "heading");
        location loc = getStartingLocationInfo(name);
        if (loc != null)
        {
            if (radius != 0.0f)
            {
                loc.x += rand(-radius, radius);
                loc.z += rand(-radius, radius);
            }
            if (heading != 0.0f)
            {
                setYaw(self, heading);
            }
            if (loc.cell != null && loc.cell != obj_id.NULL_ID)
            {
                warpPlayer(self, loc.area, 0.0f, 0.0f, 0.0f, loc.cell, loc.x, loc.y, loc.z);
            }
            else 
            {
                warpPlayer(self, loc.area, loc.x, loc.y, loc.z, null, 0.0f, 0.0f, 0.0f);
            }
        }
    }
    public void sendThoseStartLocs(obj_id player) throws InterruptedException
    {
        String limitStartingLocations = getConfigSetting("New_Player", "LimitStartingLocations");
        if (limitStartingLocations != null && limitStartingLocations.equals("true"))
        {
            String planet = "tatooine";
            float locX = 3528.0f + rand(-2.5f, 2.5f);
            float locY = 5.0f;
            float locZ = -4804.0f + rand(-2.5f, 2.5f);
            setYaw(player, 180.0f);
            newbieTutorialRequest(player, "clientReady");
            warpPlayer(player, planet, locX, locY, locZ, null, 0.0f, 0.0f, 0.0f);
            return;
        }
        String[] startLocs = getStartingLocations();
        newbieTutorialSendStartingLocationsToPlayer(player, startLocs);
    }
    public void grantAllNoviceSkills(obj_id player) throws InterruptedException
    {
        skill.grantSkillToPlayer(player, "combat_marksman_novice");
        skill.grantSkillToPlayer(player, "combat_brawler_novice");
        skill.grantSkillToPlayer(player, "science_medic_novice");
        skill.grantSkillToPlayer(player, "outdoors_scout_novice");
        skill.grantSkillToPlayer(player, "crafting_artisan_novice");
        skill.grantSkillToPlayer(player, "social_entertainer_novice");
    }
}
