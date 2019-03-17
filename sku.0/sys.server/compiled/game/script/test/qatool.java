package script.test;

import script.combat_engine.weapon_data;
import script.*;
import script.library.*;

import java.util.*;

public class qatool extends script.base_script
{
    public qatool()
    {
    }
    public static final String CREATURE_TABLE = "datatables/mob/creatures.iff";
    public static final String SPACE_MOBILE_TABLE = "datatables/space_mobile/space_mobile.iff";
    public static final String MASTER_ITEM_TABLE = "datatables/item/master_item/master_item.iff";
    public static final String ARMOR_STATS_TABLE = "datatables/item/master_item/armor_stats.iff";
    public static final String WEAPON_STATS_TABLE = "datatables/item/master_item/weapon_stats.iff";
    public static final String BUFF_TABLE = "datatables/buff/buff.iff";
    public static final String SKILL_MOD_TABLE = "datatables/expertise/skill_mod_listing.iff";
    public static final String VET_REWARDS_TABLE = "datatables/veteran_rewards/items.iff";
    public static final String DWB_SPAWN_TABLE = "datatables/spawning/dungeon/death_watch_bunker.iff";
    public static final String CUBE_DATATABLE_1 = "datatables/item/loot_cube/republic_assembly_tool.iff";
    public static final int BADGETOOL_MENUOPTION = 0;
    public static final int BUFFTOOL_MENUOPTION = 1;
    public static final int QACUBE_MENUOPTION = 2;
    public static final int CYBERNETIC_MENUOPTION = 3;
    public static final int DMGTOOL_MENUOPTION = 4;
    public static final int DATAPADTOOL_MENUOPTION = 5;
    public static final int DYNAMIC_LOOTOPTION = 6;
    public static final int XPTOOL_MENUOPTION = 7;
    public static final int FACTIONTOOL_MENUOPTION = 8;
    public static final int INVTOOL_MENUOPTION = 9;
    public static final int MSTRITEMTOOL_MENUOPTION = 10;
    public static final int NGETOOL_MENUOPTION = 11;
    public static final int NPCFINDERTOOL_MENUOPTION = 12;
    public static final int PET_OPTION = 13;
    public static final int PROFESSIONTOOL_MENUOPTION = 14;
    public static final int QUESTTOOL_MENUOPTION = 15;
    public static final int RESOURCETOOL_MENUOPTION = 16;
    public static final int RESOURCE_REWARD_TOOL_MENUOPTION = 17;
    public static final int SCRIPTTOOL_MENUOPTION = 18;
    public static final int SPACETOOL_MENUOPTION = 19;
    public static final int FROGTOOL_MENUOPTION = 20;
    public static final int VETERANREWARD_MENUOPTION = 21;
    public static final int WEAPONS_MENUOPTION = 22;
    public static final int WEARABLESTOOL_MENUOPTION = 23;
    public static final String QATOOL_TITLE = "/QATOOL USAGE";
    public static final String SCRIPT_VAR = "qatool";
    public static final String TITLE = "QA Tools";
    public static final String PROMPT = "Choose the tool you want to use";
    public static final String VAR_XP_TYPES_LIST = "xp_types.list";
    public static final String VAR_XP_TYPES_NAMES = "xp_types.names";
    public static final String RESOURCE_TOOL_PROMPT = "This Tool will automatically spawn the space resources selected into the tester inventory.";
    public static final String RESOURCE_TOOL_TITLE = "Resource Reward Tool";
    public static final String RESOURCE_REWARD_TOOL_TITLE = "QA Resource Reward Tool";
    public static final String RESOURCE_REWARD_TOOL_PROMPT = "This tool allows testers to get resources through the Vet Reward Resource script";
    public static final String SCRIPT_TOOL_TITLE = "QA SCRIPT TOOL";
    public static final String SCRIPT_TOOL_PROMPT = "This tool allows the tester to view, attach and detach common QA scripts. \n\n CURRENT ATTACHED SCRIPTS:\n";
    public static final String DATAPAD_TOOL_TITLE = "QA DATAPAD TOOL";
    public static final String DATAPAD_TOOL_PROMPT = "This tool allows the tester to view, warp, export and import into the datapad";
    public static final String ITEM_TOOL_TITLE = "QA MASTER ITEM TOOL";
    public static final String ITEM_TOOL_PROMPT = "This tool allows the tester to find items such as reward armor and weapons.";
    public static final String NGE_TOOL_TITLE = "QA NGE TOOL";
    public static final String NGE_TOOL_PROMPT = "This tool allows the tester to attain a Respec item in their inventory.\n\nPlease select a number that will represent the level that the player character will respec to (Example: 61).";
    public static final String PROFESSION_TOOL_TITLE = "QA PROFESSION ASSISTANT";
    public static final String PROFESSION_TOOL_PROMPT = "This tool allows the tester to research accurate profession data without having to create a new character or force a profession change.";
    public static final String NPCFINDER_TITLE = "NPC/Mob Finder";
    public static final String DAMAGE_TOOL_PROMPT = "Give The amount you want to damage the target for.  This tool will cause damage in the amount you specify.  ARMOR AND OTHER MITIGATION WILL NOT BE CONSIDERED.  Use the Mitigation Tool to test Mitigation.";
    public static final String DAMAGE_TOOL_TITLE = "DAMAGE AMOUNT";
    public static final String MITIGATION_TOOL_PROMPT = "SELECT AN ATTACK LOCATION.\n\r\n\rThe mitigation tool tests armor mitigation based on the attacker weapon.  No actual damage is performed on the target.  Damage and mitigation is simulated using the current mitigation system (without elemental damage).  When the test is conducted a report will be exported to your client directory.";
    public static final String MITIGATION_TOOL_TITLE = "MITIGATION TOOL";
    public static final String QAHELPER_SCRIPTVAR = "qahelper";
    public static final String OBJECT_FINDER_TITLE = "OBJECT FINDER";
    public static final String DAMAGE_PID_SCRIPTVAR = "doDamage.pid";
    public static final String DAMAGE_SCRIPTVAR = "doDamageVar";
    public static final String HEAL_PID_SCRIPTVAR = "healDamage.pid";
    public static final String HEAL_SCRIPTVAR = "healDamageVar";
    public static final String HEAL_TOOL_PROMPT = "Give the amount you want to heal the target for.  This tool will heal in the amount you specify as long as it doesn't exceed the target's maximum health.";
    public static final String HEAL_TOOL_TITLE = "HEAL AMOUNT";
    public static final String SPECMEPOPUP = "Example: /qatool spec smu 22 rebsf bone pis.\n\nProfession:\n\nsmu\tSmuggler\nbou OR bh\tBounty Hunter\noff\t\tOfficer\ncom\tCommando\njed OR for\tForce Sensitive\nmed\tMedic\nspy\tSpy\n\n\nLevel:\n\n<any number between 1 - 90>\tHas to be a number!\n\nFaction (optional argument):\n\n<empty>\tfaction unchanged\nrebsf\tRebel Special Forces\nrebcm\tRebel Combatant\nrebol\tRebel On Leave\nimpsf\tImperial Special Forces\nimpcm\tImperial Combatant\nimpol\tImperial On Leave\nneu\tNeutral\n\n\nArmor (optional argument):\n\n<empty>\tno armor\nbone\tbasicbone armor\nsmuggler\troadmap basic only\nofficer\troadmap basic only\ncommando\troadmap basic only\nbh\troadmap basic only\nmedic\troadmap basic only\nspy\troadmap basic only\nassault_agi\nassault_sta\nassault_con\nassault_pre\nassault_lck\nbattle_agi\nbattle_sta\nbattle_con\nbattle_pre\nbattle_lck\nrecon_agi\nrecon_sta\nrecon_con\nrecon_pre\nrecon_lck\nassault_borvo\nmandalorian_imperial_white\nmandalorian_rebel_white\nmandalorian_imperial_black\nmandalorian_rebel_black\nmandalorian_imperial_red\nmandalorian_rebel_red\nmandalorian_imperial_blue\nmandalorian_rebel_blue\nmandalorian_imperial_green\nmandalorian_rebel_green\nrobe\tall Jedi robes\n\nWeapons (optional argument):\n<empty>\tno weapons\npis\t\tAll Pistols\nrif\t\tAll Rifles\ncar\t\tAll Carbines\nuna\tAll Unarmed\none\tAll One Handed\ntwo\tAll Two Handed\npol\t\tAll Polearms";
    public static final String BUFF_TOOL_PROMPT = "Select Spacial Attack or Buff to be performed on your test character.\n\nTo remove all buffs use the command:\n\n/qatool buff clear";
    public static final String BUFF_TOOL_TITLE = "Special Attack & Buff Tool";
    public static final String QUEST_TOOL_TITLE = "QA Quest Tool";
    public static final String QUEST_TOOL_PROMPT = "Select a Quest or menu item.\n\n(A) Active Quest\n(C) Completed Quest";
    public static final String DYNAMIC_DESCRIPTION = "This tool allows a tester to spawn armor, clothing and weapons based on a selected level.  The items spawned resemble what a player would find on a mob in-game as random loot.";
    public static final String CHU_GON_DAR_TITLE = "Chu-Gon Dar Cube Tool";
    public static final String CHU_GON_DAR_PROMPT = "This tool allows you to quickly obtain items needed to create the items listed below.\n**If you do not have a Cube, one will be created for you.**\n\nSelect an item to create.";
    public static final String DNA_PROMPT = "Pet Options\nChoose a category to continue.";
    public static final String DNA_TITLE = "QA DNA Tool";
    public static final float MINIMUM_FINDER_RADIUS_FLOAT = 10000000.0f;
    public static final string_id NEW_CITY_STRUCTURE_SUBJECT = new string_id("city/city", "new_city_structure_subject");
    public static final string_id NEW_CITY_STRUCTURE_BODY = new string_id("city/city", "new_city_structure_body");
    public static final String[] QATOOLPROMPT = 
    {
        " Format: /qatool <command>",
        " qabadge -- Assigns and revokes badges",
        " qawearables -- spawns clothing into your inventory",
        " buff -- provides a list of in game buffs that can be applied to the tester (also /qatool buff <buffString>)",
        " qainv -- Inventory manipulation functions",
        " qafaction -- Faction manipulation functions ",
        " qaxp - Experience tool.  Allows tester to attain expereince based on current profession.",
        "* (command driven tool) qabag -- Creates the QA bag into your inventory ",
        " qange -- Allows the tester to receive a respec token.",
        " frog -- Allows the tester to spawn character builder terminals directly into the tester inventory.",
        " qaquest -- QA version of a quest tool.  Should provide tester with a list of current quests that are yet to be completed. Allows tester to complete ground and space quests.",
        " qaprofession -- Tester can use this tool to research stats, roadmap and other data. A report can be created based off of the tool data.",
        "* (command driven tool) qalootlogger <Creature Name> <number of iterations> -- Spawns the supplied creature specified number of times and loots it, default is 25",
        " spacetool -- Allows the tester to use tools specific to Space. Also known as the JTL Tool.",
        "* (command driven tool) groundmobsearch -- Allows the tester to search the creature table ",
        "* (command driven tool) groundmoblevel <level number> -- Allows the tester to search the creature table by creature level",
        "* (command driven tool) groundmobplanet <null | planet> -- Allows the tester to search the creature table by planet",
        "* (command driven tool) spacemobsearch -- Allows the tester to search the space mobile table",
        " qaresource -- Allows the tester to spawn different types of resources ",
        " qadatapad - Allows the tester to manipulate datapad data",
        " qascript - Allows the tester to attach or detach common QA scripts without memorizing the script string",
        "* (command driven tool) lootnamesearch - Allows the tester to search for a Static Loot/Reward item by the title of the object (ex. Basic Pistol)",
        "* (command driven tool) lootcodesearch - Allows the tester to search for a Static Loot/Reward item by the staticItem code string (ex. weapon_pistol_02_01)",
        " qaitem - Allows the tester to attain armor and weapons. Also known as the Master Item Tool.",
        "* (command driven tool) kill <target>- Allows the tester to kill another tester (or themself) depending on the current target",
        "* (command driven tool) suicide - Allows the tester to kill themself instantly.",
        "* (command driven tool) spawnitem <itemCodeString> - Allows the tester to spawn a static item into their inventory.",
        " damage <mobTarget or playerTarget> - Allows the tester to damage the health of a target without entering combat with the mobile or player.",
        "* (command driven tool) heal <mobTarget or playerTarget> - Allows the tester to heal the health of a mobile or player target.",
        "* (command driven tool) aistop <mobTarget> - Allows the tester to stop a mobile so it doesn't walk around or wander off.  Once attacked, the mobile will defend itself.",
        "* (command driven tool) aifreeze <mobTarget> - Allows the tester to stop a mobile so it doesn't move even if damaged by combat.  The mobile will not attempt to fight back.",
        " npcFinder (or findNPC) - Allows the tester to look for specific mobs within the server boundaries of their current position.",
        "* (command driven tool) string [code/identifying_the_string]:string_id - Example: /qaTool string [conversation/tatooine_espa_watto]:s_106",
        "* (command driven tool) targetData - Gives tester a popup window with a target's data to include scripts, stats, scriptvars, etc. The popup itself can be edited meaning you can copy or cut from the popup to the chat UI or other applications.",
        "* (command driven tool) helper <creatureName> - Gives tester a creature with additional functionality.  Tester can instruct the test creature to carry out specific instructions in order to assist the tester in attaining a structured combat test case.",
        "* (command driven tool) objFinder <script.string.argument | template/name.iff> - Lists all objects, that have the specified template or script attached, within the server boundary in which the tester currently resides.",
        "* (command driven tool) revokepilot - Removes the test character's Pilot profession completely.",
        "* (command driven tool) spec - Allows tester to instantly change profession, level, faction, armor and weapons. Example: /qatool spec smu 22 rebsf bone pis",
        "* (command driven tool) gmr - Restores health on test character (self only).",
        "* (command driven tool) multi - Allows for multiple spawning of a static item using 2 arguments.  Example: /qatool multi item_restuss_imperial_commendation_02_01 2000",
        "* (command driven tool) entertaineritems - Spawns all entertainer instraments into tester inventory.",
        "* (command driven tool) serialize - Command that allows a crafter to simulate items from a crate.  To use this tool, create two or more of the same item and then /qatool serialize <OID> of the object to be serialized.",
        " vetreward -- Allows tester to spawn veteran rewards rapidly.",
        " qacybernetic -- Allows testers to install/uninstall/repair cybernetics without the need to find the correct NPC.",
        " qaweapon -- Allows testers to spawn weapons that have a set damage amount instead of a damage range.  This is useful in testing various special abilities and armor mitigation.",
        "* (command driven tool) questdump - Exports a text file of all test character completed/active quests to the client root directory.",
        " qa_cube -- Allows testers to obtain components needed to create items using the Chu-Gon Dar Cube.",
        "* (command driven tool) eggspawn - Forces a Yellow Spawner Egg (crown) to spawn its mobile(s) immediately.  Mobile selected is random.",
        " weather -- Allows testers to change weather locally, on their client to test effects of the environment when weather is used.",
        "* (command driven tool) spam - Pass the amount of emails you want to receive to spam your own character with emails from the email server.",
        "* (command driven tool) glow - Issues the test character a blue glowie ability Icon regardless of profession. Allows for testing Blue Glow character model.",
        "* (command driven tool) olditem - Gives tester immediate access to some of the older loot or quest items that may or may not work.",
        " qarewardresource -- Allows testers to quickly access the Resource Reward Deed interface.  From there, testers can spawn resources needed for crafting.",
        " qadna -- Creates valid DNA samples for testers.",
        " fish [n] -- Succesfully catches a fish. Optional parameter [n] = 1-100. Location is the current planet but does not test for valid fishing location.",
        " collectionclickbypass -- Sets scriptvar to allow 1 second clicks on collection items",
        "* (command driven tool) setScriptVar -- Sets a scriptvar on to 0 or a given value. If no current target then scriptvar is set on player.",
        "* (command driven tool) enzyme -- Creates an Enzyme with preset values the mutagen score and the purity score (for example /qatool enzyme 10 15).",
        "* (command driven tool) lyase or qalyase -- Creates an Lyase Enzyme with randomStats set to 11.",
        "* (command driven tool) clearHeroicTimer -- Removes all heroic lockout timers from the character",
        "* (command driven tool) iso or qaiso -- Creates an Isomerase Enzyme with a quality of 90.00.",
        "* (command driven tool) spawnShip <ship spawn string> <number of ships> -- Creates a ship in front of you.(For example: /qat spawnShip awing_tier6 3)",
        " qatcg or tcg - Brings up a menu of options for the SWG Trading Card Game"
    };
    public static final String[] QATOOL_MAIN_MENU = dataTableGetStringColumn("datatables/test/qa_tool_menu.iff", "main_tool");
    public static final String[] FACTION_TOOL_MENU = 
    {
        "Join a faction",
        "Go Covert (Combatant)",
        "Go Overt (Special Forces)",
        "Go On Leave",
        "Go Neutral",
        "GCW Faction Points",
        "Manipulate NPC Factions",
        "Get Factional HQ's and Supplies"
    };
    public static final String[] FS_TOOL_MENU = 
    {
        "FS Village Intro Quests",
        "FS Village Quests",
        "FS Village Exit Quests"
    };
    public static final String[] INVENTORY_TOOL_MENU = 
    {
        "Delete all in inventory",
        "Fill inventory with Junk"
    };
    public static final String[] QUEST_TOOL_MENU = 
    {
        "Add a ground quest manually",
        "Add a space quest manually",
        "Attain test quests",
        "Bulk Grant/Complete Tool",
        "Bulk Grant Tool"
    };
    public static final String[] JTL_TOOL_MENU = 
    {
        "Ship Loot Components",
        "Mining Components",
        "God Ships",
        "Valid Ship Deeds",
        "Valid Ships With Components",
        "RoTW Quest Ship Deeds",
        "Make Master Imperial Pilot",
        "Make Master Rebel Pilot",
        "Make Master Neutral Pilot",
        "Pilot Quest Tools",
        "Revoke pilot skills"
    };
    public static final String[] ENHANCEMENT_TOOL_MENU = 
    {
        "Armor Enhancement",
        "Clothing Enhancement",
        "Random Enhancement"
    };
    public static final String[] XP_TOOL_MENU = 
    {
        "Revoke non-pilot experience",
        "combat_general",
        "quest_combat",
        "quest_crafting",
        "quest_social",
        "quest_general",
        "prestige_imperial",
        "prestige_rebel",
        "prestige_pilot"
    };
    public static final String[] RESOURCE_TOOL_MENU = 
    {
        "Recycled Resources",
        "Space Resources",
        "Common Resources"
    };
    public static final String[] REWARD_RESOURCE_MENU = 
    {
        "Enter Resource Menu"
    };
    public static final String[] ITEM_TOOL_MENU = 
    {
        "Armor",
        "Best Weapons",
        "Get all Certified Weapons",
        "Get Roadmap Items",
        "List Every Item by Category"
    };
    public static final String[] SCRIPT_TOOL_MENU = 
    {
        "Attach Script",
        "Detach Script"
    };
    public static final String[] NGE_TOOL_MENU = 
    {
        "Make CU Profession",
        "Get Respec Token"
    };
    public static final String[] SCRIPT_TOOL_COMMON_SCRIPTS = 
    {
        "csr.get_resource_crate",
        "working.dantest",
        "player.player_gm",
        "gm.cmd",
        "e3demo.e3_demoer",
        "player.yavin_e3",
        "space.content_tools.missiontest",
        "space.content_tools.content_generation",
        "quest.utility.quest_test",
        "test.ttyson_test",
        "test.qatool",
        "test.qa_enhancements",
        "test.qa_jtl_tools",
        "test.qa_quest_skipper",
        "test.qa_resources",
        "test.qabadge",
        "test.qabuff",
        "test.qafaction",
        "test.qainventory",
        "test.qascript",
        "test.qawearables",
        "test.qaxp",
        "test.qadatapad",
        "test.qaitem",
        "test.qange",
        "test.qaprofession",
        "test.qa_damage",
        "test.qa_helper",
        "test.qa_dynamics",
        "test.qaham",
        "test.qa_resource_reward",
        "test.qadna"
    };
    public static final String[] DATAPAD_TOOL_MENU = 
    {
        "Warp to Waypoints",
        "Export Waypoints"
    };
    public static final String[] PROFESSION_TOOL_MENU = 
    {
        "Abilities Alphabetically",
        "Profession Details",
        "Export Profession Details"
    };
    public static final String[] MOB_STRING_FILE_POSSIBILITIES = 
    {
        "mob/creature_names",
        "npc_name",
        "droid_name",
        "monster_name",
        "npc_spawner_n",
        "theme_park_name",
        "ep3/npc_names"
    };
    public static final String[] NPCFINDER_ERR_ARRAY = 
    {
        "No Mob/NPC Found"
    };
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
    public static final String[] PROFESSION_SKILL_NAMES = 
    {
        "_phase1_novice",
        "_phase1_02",
        "_phase1_03",
        "_phase1_04",
        "_phase1_05",
        "_phase1_master",
        "_phase2_novice",
        "_phase2_02",
        "_phase2_03",
        "_phase2_04",
        "_phase2_05",
        "_phase2_master",
        "_phase3_novice",
        "_phase3_02",
        "_phase3_03",
        "_phase3_04",
        "_phase3_05",
        "_phase3_master",
        "_phase4_novice",
        "_phase4_02",
        "_phase4_03",
        "_phase4_04",
        "_phase4_05",
        "_phase4_master"
    };
    public static final String[] PROFESSION_PREFIX = 
    {
        "class_smuggler",
        "class_bountyhunter",
        "class_officer",
        "class_commando",
        "class_forcesensitive",
        "class_medic",
        "class_spy"
    };
    public static final String[] PET_OPTION_MENU = 
    {
        "Access DNA Samples",
        "Future Pet Options"
    };
    public static final String[][] MISC_SEARCH_MULTIARRAY = 
    {
        
        {
            "robe"
        },
        
        {
            "item_jedi_robe_"
        }
    };
    public static final String[][] WEAPON_SEARCH_MULTIARRAY = 
    {
        
        {
            "pis",
            "rif",
            "car",
            "una",
            "one",
            "two",
            "pol"
        },
        
        {
            "weapon_pistol_",
            "weapon_rifle_",
            "weapon_carbine_",
            "weapon_unarmed_",
            "weapon_sword_1h_",
            "weapon_sword_2h_",
            "weapon_polearm_"
        }
    };
    public static final String[][] ARMOR_SEARCH_MULTIARRAY = 
    {
        
        {
            "bone",
            "smuggler",
            "officer",
            "commando",
            "bh",
            "medic",
            "spy",
            "assault_agi",
            "assault_sta",
            "assault_con",
            "assault_pre",
            "assault_lck",
            "battle_agi",
            "battle_sta",
            "battle_con",
            "battle_pre",
            "battle_lck",
            "recon_agi",
            "recon_sta",
            "recon_con",
            "recon_pre",
            "recon_lck",
            "assault_borvo",
            "mandalorian_imperial_white",
            "mandalorian_rebel_white",
            "mandalorian_imperial_black",
            "mandalorian_rebel_black",
            "mandalorian_imperial_red",
            "mandalorian_rebel_red",
            "mandalorian_imperial_blue",
            "mandalorian_rebel_blue",
            "mandalorian_imperial_green",
            "mandalorian_rebel_green"
        },
        
        {
            "armor_bone_",
            "armor_smuggler_roadmap_",
            "armor_officer_roadmap_",
            "armor_commando_roadmap_",
            "armor_bounty_hunter_",
            "armor_medic_roadmap_",
            "armor_spy_roadmap_",
            "armor_assault_agi_lvl80_",
            "armor_assault_sta_lvl80_",
            "armor_assault_con_lvl80_",
            "armor_assault_pre_lvl80_",
            "armor_assault_lck_lvl80_",
            "armor_battle_agi_lvl80_",
            "armor_battle_sta_lvl80_",
            "armor_battle_con_lvl80_",
            "armor_battle_pre_lvl80_",
            "armor_battle_lck_lvl80_",
            "armor_recon_agi_lvl80_",
            "armor_recon_sta_lvl80_",
            "armor_recon_con_lvl80_",
            "armor_recon_pre_lvl80_",
            "armor_recon_lck_lvl80_",
            "armor_assault_borvo_acklay_",
            "armor_mandalorian_imperial_white_",
            "armor_mandalorian_rebel_white_",
            "armor_mandalorian_imperial_black_",
            "armor_mandalorian_rebel_black_",
            "armor_mandalorian_imperial_red_",
            "armor_mandalorian_rebel_red_",
            "armor_mandalorian_imperial_blue_",
            "armor_mandalorian_rebel_blue_",
            "armor_mandalorian_imperial_green_",
            "armor_mandalorian_rebel_green_"
        }
    };
    public static final String[] ENTERTAINER_ITEMS = 
    {
        "object/tangible/component/instrument/dual_wave_synthesizer.iff",
        "object/tangible/instrument/bandfill.iff",
        "object/tangible/instrument/fanfar.iff",
        "object/tangible/instrument/fizz.iff",
        "object/tangible/instrument/flute_droopy.iff",
        "object/tangible/instrument/kloo_horn.iff",
        "object/tangible/instrument/mandoviol.iff",
        "object/tangible/instrument/nalargon.iff",
        "object/tangible/instrument/ommni_box.iff",
        "object/tangible/instrument/slitherhorn.iff",
        "object/tangible/instrument/traz.iff",
        "object/tangible/dance_prop/generic_must_prop_baton_01_l.iff",
        "object/tangible/dance_prop/generic_must_prop_baton_01_r.iff",
        "object/tangible/dance_prop/generic_must_prop_baton_02_l.iff",
        "object/tangible/dance_prop/generic_must_prop_baton_02_r.iff",
        "object/tangible/dance_prop/generic_must_prop_baton_03_r.iff",
        "object/tangible/dance_prop/generic_must_prop_baton_04_l.iff",
        "object/tangible/dance_prop/generic_must_prop_baton_04_r.iff",
        "object/tangible/dance_prop/generic_prop_ribbon_spark_r.iff",
        "object/tangible/dance_prop/generic_prop_sparkler_l.iff",
        "object/tangible/dance_prop/prop_double_ribbon_l.iff",
        "object/tangible/dance_prop/prop_double_ribbon_magic_l.iff",
        "object/tangible/dance_prop/prop_double_ribbon_magic_r.iff",
        "object/tangible/dance_prop/prop_double_ribbon_r.iff",
        "object/tangible/dance_prop/prop_double_ribbon_spark_l.iff",
        "object/tangible/dance_prop/prop_double_ribbon_spark_r.iff",
        "object/tangible/dance_prop/prop_glowstick_l.iff",
        "object/tangible/dance_prop/prop_glowstick_r.iff",
        "object/tangible/dance_prop/prop_ribbon_l.iff",
        "object/tangible/dance_prop/prop_ribbon_magic_l.iff",
        "object/tangible/dance_prop/prop_ribbon_magic_r.iff",
        "object/tangible/dance_prop/prop_ribbon_r.iff",
        "object/tangible/dance_prop/prop_ribbon_spark_l.iff",
        "object/tangible/dance_prop/prop_ribbon_spark_r.iff",
        "object/tangible/dance_prop/prop_sparkler_l.iff",
        "object/tangible/dance_prop/prop_sparkler_r.iff",
        "object/tangible/dance_prop/prop_staff_l.iff",
        "object/tangible/dance_prop/prop_staff_r.iff",
        "object/tangible/dance_prop/prop_sword_l.iff",
        "object/tangible/dance_prop/prop_sword_r.iff",
        "object/tangible/dance_prop/prop_torch_l.iff",
        "object/tangible/dance_prop/prop_torch_r.iff"
    };
    public static final String[] TERMINAL_LIST = 
    {
        "object/tangible/terminal/terminal_mission_artisan.iff",
        "object/tangible/terminal/terminal_mission_entertainer.iff",
        "object/tangible/terminal/terminal_mission_bounty.iff",
        "object/tangible/terminal/terminal_mission.iff",
        "object/tangible/terminal/terminal_mission_scout.iff"
    };
    public static final String[] DATA_SOURCE_MENU_LIST = 
    {
        "spawn dynamic armor",
        "spawn dynamic clothing",
        "spawn dynamic weapons"
    };
    public static final String[] ADD_ON_DATAPAD_MENU = 
    {
        "FILL WAYPOINTS",
        "CLEAR ALL WAYPOINTS"
    };
    public static final String[] CYBER_MENU_LIST = 
    {
        "Install",
        "Uninstall",
        "Repair"
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
    public static final String[] WEATHER_TYPES = 
    {
        "Clear",
        "Mild",
        "Heavy",
        "Severe"
    };
    public static final String[] CONTRABAND_STRINGS = 
    {
        "item_smuggler_contraband_01_01",
        "item_smuggler_contraband_01_02",
        "item_smuggler_contraband_01_03",
        "item_smuggler_contraband_01_04",
        "item_smuggler_contraband_01_05",
        "item_smuggler_contraband_02_01",
        "item_smuggler_contraband_02_02",
        "item_smuggler_contraband_02_03",
        "item_smuggler_contraband_02_04",
        "item_smuggler_contraband_02_05",
        "item_smuggler_contraband_03_01",
        "item_smuggler_contraband_03_02",
        "item_smuggler_contraband_03_03",
        "item_smuggler_contraband_03_04",
        "item_smuggler_contraband_03_05",
        "item_smuggler_contraband_04_01",
        "item_smuggler_contraband_04_02",
        "item_smuggler_contraband_04_03",
        "item_smuggler_contraband_04_04",
        "item_smuggler_contraband_04_05",
        "item_smuggler_contraband_05_01",
        "item_smuggler_contraband_05_02",
        "item_smuggler_contraband_05_03",
        "item_smuggler_contraband_05_04",
        "item_smuggler_contraband_05_05"
    };
    public static final String[] OLD_ITEM_MENU = 
    {
        "Old FS Village Rewards",
        "Hero of Tatooine",
        "Death Watch Bunker Loot",
        "Old Skill Buff Items",
        "Crafting Components",
        "Varactyl Egg and Treasure Map",
        "Loot Schematics"
    };
    public static final String[] OLD_FS_VILLAGE_ITEMS = 
    {
        "object/tangible/item/quest/force_sensitive/bacta_tank.iff",
        "object/tangible/wearables/necklace/necklace_ice_pendant.iff",
        "object/tangible/item/quest/force_sensitive/fs_village_bannerpole_s01.iff",
        "object/tangible/item/quest/force_sensitive/fs_sculpture_2.iff",
        "object/tangible/item/quest/force_sensitive/fs_buff_item.iff",
        "object/tangible/loot/quest/force_sensitive/force_crystal.iff",
        "object/tangible/loot/plant_grow/plant_stage_1.iff",
        "object/tangible/loot/plant_grow/plant_stage_2.iff",
        "object/tangible/loot/plant_grow/plant_stage_3.iff",
        "object/tangible/loot/plant_grow/plant_stage_dead.iff",
        "object/tangible/item/plant/force_melon.iff"
    };
    public static final String[] HERO_OF_TATOOINE_ITEMS = 
    {
        "object/tangible/loot/quest/hero_of_tatooine/squill_skull.iff",
        "object/tangible/loot/quest/hero_of_tatooine/mark_altruism.iff",
        "object/tangible/loot/quest/hero_of_tatooine/squill_skull_pile.iff",
        "object/tangible/loot/quest/hero_of_tatooine/squill_skull.iff",
        "object/tangible/loot/quest/hero_of_tatooine/mark_intellect.iff",
        "object/tangible/loot/quest/hero_of_tatooine/mark_honor.iff",
        "object/tangible/loot/quest/hero_of_tatooine/mark_courage.iff"
    };
    public static final String[] DEATH_WATCH_BUNKER_ITEMS = 
    {
        "object/tangible/loot/dungeon/death_watch_bunker/pistol_de10_barrel.iff",
        "object/tangible/loot/dungeon/death_watch_bunker/mining_drill_reward.iff",
        "object/tangible/loot/dungeon/death_watch_bunker/binary_liquid.iff",
        "object/tangible/loot/dungeon/death_watch_bunker/art_crate.iff",
        "object/tangible/loot/dungeon/death_watch_bunker/blood_vial.iff",
        "object/tangible/loot/dungeon/death_watch_bunker/ducted_fan.iff",
        "object/tangible/loot/dungeon/death_watch_bunker/emulsion_protection.iff",
        "object/tangible/loot/dungeon/death_watch_bunker/fuel_dispersion_unit.iff",
        "object/tangible/loot/dungeon/death_watch_bunker/fuel_injector_tank.iff",
        "object/tangible/loot/dungeon/death_watch_bunker/jetpack_base.iff",
        "object/tangible/loot/dungeon/death_watch_bunker/jetpack_stabilizer.iff",
        "object/tangible/loot/tool/usable_datapad.iff",
        "object/tangible/loot/dungeon/death_watch_bunker/viewscreen_s1.iff",
        "object/tangible/loot/dungeon/death_watch_bunker/viewscreen_s2.iff"
    };
    public static final String[] OLD_SKILL_BUFF_ITEMS = 
    {
        "object/tangible/skill_buff/skill_buff_carbine_accuracy.iff",
        "object/tangible/skill_buff/skill_buff_carbine_speed.iff",
        "object/tangible/skill_buff/skill_buff_heavy_weapon_accuracy.iff",
        "object/tangible/skill_buff/skill_buff_heavy_weapon_speed.iff",
        "object/tangible/skill_buff/skill_buff_mask_scent.iff",
        "object/tangible/skill_buff/skill_buff_melee_accuracy.iff",
        "object/tangible/skill_buff/skill_buff_melee_defense.iff",
        "object/tangible/skill_buff/skill_buff_onehandmelee_accuracy.iff",
        "object/tangible/skill_buff/skill_buff_onehandmelee_speed.iff",
        "object/tangible/skill_buff/skill_buff_pistol_accuracy.iff",
        "object/tangible/skill_buff/skill_buff_pistol_speed.iff",
        "object/tangible/skill_buff/skill_buff_polearm_accuracy.iff",
        "object/tangible/skill_buff/skill_buff_polearm_speed.iff",
        "object/tangible/skill_buff/skill_buff_ranged_accuracy.iff",
        "object/tangible/skill_buff/skill_buff_ranged_defense.iff",
        "object/tangible/skill_buff/skill_buff_rifle_accuracy.iff",
        "object/tangible/skill_buff/skill_buff_rifle_speed.iff",
        "object/tangible/skill_buff/skill_buff_thrown_accuracy.iff",
        "object/tangible/skill_buff/skill_buff_thrown_speed.iff",
        "object/tangible/skill_buff/skill_buff_twohandmelee_accuracy.iff",
        "object/tangible/skill_buff/skill_buff_twohandmelee_speed.iff",
        "object/tangible/skill_buff/skill_buff_unarmed_accuracy.iff",
        "object/tangible/skill_buff/skill_buff_unarmed_speed.iff"
    };
    public static final String[] CRAFTING_COMPONENTS = 
    {
        "object/tangible/component/armor/armor_core_assault_advanced.iff",
        "object/tangible/component/armor/armor_core_assault_basic.iff",
        "object/tangible/component/armor/armor_core_battle_advanced.iff",
        "object/tangible/component/armor/armor_core_battle_standard.iff",
        "object/tangible/component/armor/armor_core_recon_advanced.iff",
        "object/tangible/component/armor/bone_fragment_woolamander_harrower.iff",
        "object/tangible/component/armor/hide_gurk_king.iff",
        "object/tangible/component/armor/scale_giant_dune_kimogila.iff",
        "object/tangible/component/chemistry/biologic_effect_controller.iff",
        "object/tangible/component/chemistry/dispersal_mechanism.iff",
        "object/tangible/component/chemistry/infection_amplifier.iff",
        "object/tangible/component/chemistry/janta_blood.iff",
        "object/tangible/component/chemistry/liquid_delivery_suspension.iff",
        "object/tangible/component/chemistry/rancor_bile.iff",
        "object/tangible/component/chemistry/release_mechanism_duration.iff",
        "object/tangible/component/chemistry/resilience_compound.iff",
        "object/tangible/component/chemistry/solid_delivery_shell.iff",
        "object/tangible/component/clothing/clasp_heavy_duty.iff",
        "object/tangible/component/clothing/clothing_treatment_crystalline.iff",
        "object/tangible/component/clothing/leather_heavy_duty.iff",
        "object/tangible/component/munition/kliknick_gland.iff",
        "object/tangible/component/weapon/blaster_power_handler_enhancement_bounty.iff",
        "object/tangible/component/weapon/blaster_power_handler_enhancement_durability.iff",
        "object/tangible/component/weapon/blaster_power_handler_enhancement_max_damage.iff",
        "object/tangible/component/weapon/blaster_power_handler_enhancement_min_damage.iff",
        "object/tangible/component/weapon/blaster_rifle_barrel.iff",
        "object/tangible/component/weapon/projectile_feed_mechanism_enhancement_bounty.iff",
        "object/tangible/component/weapon/projectile_feed_mechanism_enhancement_durability.iff",
        "object/tangible/component/weapon/projectile_feed_mechanism_enhancement_gorax.iff",
        "object/tangible/component/weapon/projectile_feed_mechanism_enhancement_max_damage.iff",
        "object/tangible/component/weapon/projectile_feed_mechanism_enhancement_min_damage.iff",
        "object/tangible/component/weapon/projectile_rifle_barrel.iff",
        "object/tangible/component/weapon/scope_weapon_advanced.iff",
        "object/tangible/component/weapon/stock_advanced.iff",
        "object/tangible/component/weapon/vibro_unit_enhancement_durability.iff",
        "object/tangible/component/weapon/vibro_unit_enhancement_max_damage.iff",
        "object/tangible/component/weapon/vibro_unit_enhancement_min_damage.iff",
        "object/tangible/component/weapon/vibro_unit_enhancement_wounding.iff",
        "object/tangible/loot/quest/ep3/varactyl_egg.iff",
        "object/tangible/treasure_map/treasure_map_base.iff",
        "object/tangible/component/weapon/vibro_unit_nightsister.iff"
    };
    public static final String[] VARACTYL_TREASURE_ITEMS = 
    {
        "object/tangible/loot/quest/ep3/varactyl_egg.iff",
        "object/tangible/treasure_map/treasure_map_base.iff"
    };
    public static final String[] LOOT_SCHEMATICS = 
    {
        "object/tangible/loot/quest/schematic_republic_blaster_quest.iff",
        "object/tangible/loot/quest/2h_sword_scythe_schematic.iff",
        "object/tangible/loot/loot_schematic/agitator_motor_schematic.iff",
        "object/tangible/loot/loot_schematic/assorted_fruit_schematic.iff",
        "object/tangible/loot/loot_schematic/bacta_tank_schematic.iff",
        "object/tangible/loot/loot_schematic/basket_closed_schematic.iff",
        "object/tangible/loot/loot_schematic/bottle_fat_schematic.iff",
        "object/tangible/loot/loot_schematic/bottle_pear_schematic.iff",
        "object/tangible/loot/loot_schematic/bottle_tall_schematic.iff",
        "object/tangible/loot/loot_schematic/bowl_carved_schematic.iff",
        "object/tangible/loot/loot_schematic/bowl_plain_schematic.iff",
        "object/tangible/loot/loot_schematic/bubble_tank_schematic.iff",
        "object/tangible/loot/loot_schematic/cabinet_elegant_schematic.iff",
        "object/tangible/loot/loot_schematic/campfire_schematic.iff",
        "object/tangible/loot/loot_schematic/cantina_chair_schematic.iff",
        "object/tangible/loot/loot_schematic/chair_cafe_schematic.iff",
        "object/tangible/loot/loot_schematic/chemical_recycler_schematic.iff",
        "object/tangible/loot/loot_schematic/command_console_schematic.iff",
        "object/tangible/loot/loot_schematic/corellian_corvette_landspeeder_av21_schematic.iff",
        "object/tangible/loot/loot_schematic/corellian_corvette_rifle_berserker_schematic.iff",
        "object/tangible/loot/loot_schematic/corellian_flagpole_schematic.iff",
        "object/tangible/loot/loot_schematic/couch_blue_schematic.iff",
        "object/tangible/loot/loot_schematic/creature_recycler_schematic.iff",
        "object/tangible/loot/loot_schematic/death_watch_bounty_hunter_belt_schematic.iff",
        "object/tangible/loot/loot_schematic/death_watch_bounty_hunter_bicep_l_schematic.iff",
        "object/tangible/loot/loot_schematic/death_watch_bounty_hunter_bicep_r_schematic.iff",
        "object/tangible/loot/loot_schematic/death_watch_bounty_hunter_boots_schematic.iff",
        "object/tangible/loot/loot_schematic/death_watch_bounty_hunter_bracer_l_schematic.iff",
        "object/tangible/loot/loot_schematic/death_watch_bounty_hunter_bracer_r_schematic.iff",
        "object/tangible/loot/loot_schematic/death_watch_bounty_hunter_chest_plate_schematic.iff",
        "object/tangible/loot/loot_schematic/death_watch_bounty_hunter_gloves_schematic.iff",
        "object/tangible/loot/loot_schematic/death_watch_bounty_hunter_helmet_schematic.iff",
        "object/tangible/loot/loot_schematic/death_watch_bounty_hunter_leggings_schematic.iff",
        "object/tangible/loot/loot_schematic/death_watch_drink_mandalorian_wine_schematic.iff",
        "object/tangible/loot/loot_schematic/death_watch_executioners_hack_schematic.iff",
        "object/tangible/loot/loot_schematic/death_watch_mandalorian_belt_schematic.iff",
        "object/tangible/loot/loot_schematic/death_watch_mandalorian_bicep_l_schematic.iff",
        "object/tangible/loot/loot_schematic/death_watch_mandalorian_bicep_r_schematic.iff",
        "object/tangible/loot/loot_schematic/death_watch_mandalorian_boots_schematic.iff",
        "object/tangible/loot/loot_schematic/death_watch_mandalorian_bracer_l_schematic.iff",
        "object/tangible/loot/loot_schematic/death_watch_mandalorian_bracer_r_schematic.iff",
        "object/tangible/loot/loot_schematic/death_watch_mandalorian_chest_plate_schematic.iff",
        "object/tangible/loot/loot_schematic/death_watch_mandalorian_gloves_schematic.iff",
        "object/tangible/loot/loot_schematic/death_watch_mandalorian_helmet_schematic.iff",
        "object/tangible/loot/loot_schematic/death_watch_mandalorian_jetpack_schematic.iff",
        "object/tangible/loot/loot_schematic/death_watch_mandalorian_leggings_schematic.iff",
        "object/tangible/loot/loot_schematic/death_watch_pistol_de_10_schematic.iff",
        "object/tangible/loot/loot_schematic/death_watch_razor_knuckler_schematic.iff",
        "object/tangible/loot/loot_schematic/diagnostic_screen_schematic.iff",
        "object/tangible/loot/loot_schematic/droid_body_schematic.iff",
        "object/tangible/loot/loot_schematic/droid_head_schematic.iff",
        "object/tangible/loot/loot_schematic/flora_recycler_schematic.iff",
        "object/tangible/loot/loot_schematic/foodcart_schematic.iff",
        "object/tangible/loot/loot_schematic/gambling_table_schematic.iff",
        "object/tangible/loot/loot_schematic/generic_limited_use.iff",
        "object/tangible/loot/loot_schematic/generic_limited_use_flashy.iff",
        "object/tangible/loot/loot_schematic/generic_vehicle.iff",
        "object/tangible/loot/loot_schematic/geonosian_acklay_bone_armor_schematic.iff",
        "object/tangible/loot/loot_schematic/geonosian_acklay_muscle_armor_schematic.iff",
        "object/tangible/loot/loot_schematic/geonosian_reinforcement_core_schematic.iff",
        "object/tangible/loot/loot_schematic/geonosian_sonic_blaster_schematic.iff",
        "object/tangible/loot/loot_schematic/geonosian_sword_core_schematic.iff",
        "object/tangible/loot/loot_schematic/geonosian_tenloss_dxr6_schematic.iff",
        "object/tangible/loot/loot_schematic/hanging_planter_schematic.iff",
        "object/tangible/loot/loot_schematic/metal_recycler_schematic.iff",
        "object/tangible/loot/loot_schematic/microphone_s01_schematic.iff",
        "object/tangible/loot/loot_schematic/microphone_s02_schematic.iff",
        "object/tangible/loot/loot_schematic/ore_recycler_schematic.iff",
        "object/tangible/loot/loot_schematic/park_bench_schematic.iff",
        "object/tangible/loot/loot_schematic/pitcher_full_schematic.iff",
        "object/tangible/loot/loot_schematic/professors_desk_schematic.iff",
        "object/tangible/loot/loot_schematic/radar_screen_schematic.iff",
        "object/tangible/loot/loot_schematic/radio_schematic.iff",
        "object/tangible/loot/loot_schematic/shisa_schematic.iff",
        "object/tangible/loot/loot_schematic/slave_brazier_schematic.iff",
        "object/tangible/loot/loot_schematic/spear_rack_schematic.iff",
        "object/tangible/loot/loot_schematic/speeder_desert_skiff_schematic.iff",
        "object/tangible/loot/loot_schematic/speeder_usv5_schematic.iff",
        "object/tangible/loot/loot_schematic/stove_schematic.iff",
        "object/tangible/loot/loot_schematic/streetlamp_schematic.iff",
        "object/tangible/loot/loot_schematic/stuffed_fish_schematic.iff",
        "object/tangible/loot/loot_schematic/table_coffee_modern_schematic.iff",
        "object/tangible/loot/loot_schematic/tanning_hide_s01_schematic.iff",
        "object/tangible/loot/loot_schematic/tanning_hide_s02_schematic.iff",
        "object/tangible/loot/loot_schematic/tatooine_tapestry_schematic.iff",
        "object/tangible/loot/loot_schematic/technical_console_s01_schematic.iff",
        "object/tangible/loot/loot_schematic/technical_console_s02_schematic.iff",
        "object/tangible/loot/loot_schematic/throwpillow_schematic.iff",
        "object/tangible/loot/loot_schematic/trandoshan_hunter_rifle_schematic.iff",
        "object/tangible/loot/loot_schematic/tumble_blender_schematic.iff",
        "object/tangible/loot/loot_schematic/utensils_schematic.iff"
    };
    public static final String[] TCG_MENU = 
    {
        "Generate Drop",
        "TCG Info Flag",
        "TCG Always Drop",
        "Card Cluster Information",
        "Promotional Items Cluster Info",
        "Reinitialize TCG Information (warning! this resets everything!)"
    };
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (isGod(self))
        {
            if (getGodLevel(self) < 50)
            {
                detachScript(self, "test.qatool");
                sendSystemMessage(self, "You do not have the appropriate access level to use this script.", null);
            }
            else 
            {
                attachScript(self, "test.qabuff");
                attachScript(self, "test.qawearables");
                attachScript(self, "test.qa_quest_skipper");
                attachScript(self, "test.qabadge");
                attachScript(self, "test.qainventory");
                attachScript(self, "test.qafaction");
                attachScript(self, "test.qaxp");
                attachScript(self, "test.qa_jtl_tools");
                attachScript(self, "test.qa_resources");
                attachScript(self, "test.qascript");
                attachScript(self, "test.qadatapad");
                attachScript(self, "test.qaitem");
                attachScript(self, "test.qange");
                attachScript(self, "test.qaprofession");
                attachScript(self, "test.qa_damage");
                attachScript(self, "test.qa_helper");
                attachScript(self, "test.qa_dynamic");
                attachScript(self, "test.qa_cybernetic");
                attachScript(self, "test.qaweapon");
                attachScript(self, "test.qa_cube");
                attachScript(self, "test.qa_resource_reward");
                attachScript(self, "test.qadna");
                grantSkill(self, "swg_dev");
                sendSystemMessageTestingOnly(self, "You can access the \"SWG Developer\" title by opening your Community options.");
            }
        }
        else 
        {
            detachScript(self, "test.qatool");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        detachScript(self, "test.qabuff");
        detachScript(self, "test.qafstool");
        detachScript(self, "test.qawearables");
        detachScript(self, "test.qa_quest_skipper");
        detachScript(self, "test.qacraftable_loot_spawner");
        detachScript(self, "test.qabadge");
        detachScript(self, "test.qainventory");
        detachScript(self, "test.qafaction");
        detachScript(self, "test.qaxp");
        detachScript(self, "test.qa_jtl_tools");
        detachScript(self, "test.qa_enhancements");
        detachScript(self, "test.qa_resources");
        detachScript(self, "test.qascript");
        detachScript(self, "test.qadatapad");
        detachScript(self, "test.qaitem");
        detachScript(self, "test.qange");
        detachScript(self, "test.mitigation");
        detachScript(self, "test.qa_damage");
        detachScript(self, "test.qaprofession");
        detachScript(self, "test.qa_helper");
        detachScript(self, "test.qa_dynamic");
        detachScript(self, "test.qa_cybernetic");
        detachScript(self, "test.qaweapon");
        detachScript(self, "test.qa_cube");
        detachScript(self, "test.qa_resource_reward");
        detachScript(self, "test.qadna");
        revokeSkillSilent(self, "swg_dev");
        return SCRIPT_CONTINUE;
    }
    public int vetRewardOptionHandler(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isGod(self) || getGodLevel(self) < 10){
            detachScript(self, "test.qatool");
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, "vetreward.pid"))
        {
            qa.checkParams(params, "vetreward");
            int idx = sui.getListboxSelectedRow(params);
            int btn = sui.getIntButtonPressed(params);
            String previousMainMenuArray[] = utils.getStringArrayScriptVar(self, "vetreward.allTemplates");
            if (btn == sui.BP_CANCEL)
            {
                cleanAllScriptVars(self);
                return SCRIPT_CONTINUE;
            }
            else
            {
                String previousSelection = previousMainMenuArray[idx];
                qa.templateObjectSpawner(self, previousSelection);
                String[] allRewardTemplates = dataTableGetStringColumn(VET_REWARDS_TABLE, "Object Template");
                Arrays.sort(allRewardTemplates);
                qa.refreshMenu(self, "The list below shows veteran rewards available", "Vet Reward Tool", allRewardTemplates, "vetRewardOptionHandler", true, "vetreward.pid", "vetreward.allTemplates");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdQaTool(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!isGod(self) || getGodLevel(self) < 10){
            detachScript(self, "test.qatool");
            return SCRIPT_CONTINUE;
        }
        StringTokenizer st = new java.util.StringTokenizer(params);
        int tokens = st.countTokens();
        String command = "";
        if (st.hasMoreTokens())
        {
            command = st.nextToken();
        }
        utils.setScriptVar(self, SCRIPT_VAR + ".toolMainMenu", QATOOL_MAIN_MENU);
        utils.setScriptVar(self, SCRIPT_VAR + ".title", TITLE);
        utils.setScriptVar(self, SCRIPT_VAR + ".prompt", PROMPT);
        if (command.equals(""))
        {
            qa.refreshMenu(self, PROMPT, TITLE, QATOOL_MAIN_MENU, "toolMainMenu", true, SCRIPT_VAR + ".pid");
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(command)).equals("setscriptvar"))
        {
            qaSetScriptVar(self, st);
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(command)).equals("setcount"))
        {
            if (st.hasMoreTokens())
            {
                obj_id oid = getIntendedTarget(self);
                int count = utils.stringToInt(st.nextToken());
                setCount(oid, count);
                sendSystemMessageTestingOnly(self, "Object: " + target + " has added " + count + " to it's item count.");
                return SCRIPT_CONTINUE;
            }
        }
        else if ((toLower(command)).equals("collectionclickbypass"))
        {
            cleanAllScriptVars(self);
            qaEnableCollectionClickBypass(self, st, "none");
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(command)).equals("fish") || (toLower(command)).equals("fishing") || (toLower(command)).equals("qafish"))
        {
            cleanAllScriptVars(self);
            qaFishing(self, st, "none");
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(command)).equals("testtreasure") || (toLower(command)).equals("treasuretest"))
        {
            if (!st.hasMoreTokens())
            {
                sendSystemMessageTestingOnly(self, "Usage: /qatool testtreasure treasure/treasure_1_10 or /qatool testtreasure treasure/treasure_11_20, etc.");
                return SCRIPT_CONTINUE;
            }
            else
            {
                String lootTable = st.nextToken();
                if (lootTable == null || lootTable.equals(""))
                {
                    sendSystemMessageTestingOnly(self, "Usage: /qatool testtreasure treasure/treasure_1_10 or /qatool testtreasure treasure/treasure_11_20, etc.");
                    return SCRIPT_CONTINUE;
                }
                int tableMaxLevel = lootTable.lastIndexOf("_") + 1;
                if (tableMaxLevel < 0)
                {
                    sendSystemMessageTestingOnly(self, "Usage: /qatool testtreasure treasure/treasure_1_10 or /qatool testtreasure treasure/treasure_11_20, etc.");
                    return SCRIPT_CONTINUE;
                }
                String treasureLevel = lootTable.substring(tableMaxLevel);
                int intTreasureLevel = utils.stringToInt(treasureLevel);
                if (intTreasureLevel < 10 || intTreasureLevel > 90)
                {
                    sendSystemMessageTestingOnly(self, "Usage: /qatool testtreasure treasure/treasure_1_10 or /qatool testtreasure treasure/treasure_11_20, etc.");
                    return SCRIPT_CONTINUE;
                }
                location treasureLoc = getLocation(self);
                if (treasureLoc == null)
                {
                    return SCRIPT_CONTINUE;
                }
                obj_id treasureChest = createObject("object/tangible/container/drum/treasure_drum.iff", treasureLoc);
                if (!isValidId(treasureChest) && !exists(treasureChest))
                {
                    return SCRIPT_CONTINUE;
                }
                boolean success = loot.makeLootInContainer(treasureChest, lootTable, 6, intTreasureLevel);
                if (!success)
                {
                    sendSystemMessageTestingOnly(self, "Text export failed.");
                    return SCRIPT_CONTINUE;
                }
                obj_id contents[] = getContents(treasureChest);
                String listcontents = "";
                if (contents.length > 0)
                {
                    for (obj_id content : contents) {
                        listcontents += "(" + content + ")" + getTemplateName(content) + "\r";
                    }
                }
                if (!listcontents.equals(""))
                {
                    saveTextOnClient(self, "treasure" + treasureChest + ".txt", listcontents);
                }
                dictionary treasureDictionary = new dictionary();
                treasureDictionary.put("object", treasureChest);
                messageTo(self, "qaDestroyObject", treasureDictionary, 600, false);
            }
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(command)).equals("olditems") || (toLower(command)).equals("olditem"))
        {
            if (OLD_ITEM_MENU.length > 0)
            {
                qa.refreshMenu(self, "Select a old item category.", "Old Item Spawner. DO NOT BUG ITEMS SPAWNED WITH THIS TOOL!!! DO NOT BUG ITEMS SPAWNED WITH THIS TOOL!!! DO NOT BUG ITEMS SPAWNED WITH THIS TOOL!!! ", OLD_ITEM_MENU, "oldItemMenuHandler", true, "oldItem.pid", "oldItem.menu");
            }
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(command)).equals("glow"))
        {
            grantCommand(self, "blueGlowie");
            return SCRIPT_OVERRIDE;
        }
        else if ((toLower(command)).equals("emailspam") || (toLower(command)).equals("spamemail") || (toLower(command)).equals("spam"))
        {
            emailSpamFunction(self, st);
            return SCRIPT_OVERRIDE;
        }
        else if ((toLower(command)).equals("terminal"))
        {
            for (String s : TERMINAL_LIST) {
                obj_id terminalId = qa.templateObjectSpawner(self, s, true);
            }
            sendSystemMessageTestingOnly(self, "Terminals Spawned.");
            return SCRIPT_OVERRIDE;
        }
        else if ((toLower(command)).equals("eggspawn") || (toLower(command)).equals("eggspawner") || (toLower(command)).equals("spawnegg") || (toLower(command)).equals("spawn"))
        {
            qa.forceEggSpawn(self);
            return SCRIPT_OVERRIDE;
        }
        else if ((toLower(command)).equals("weather"))
        {
            qa.refreshMenu(self, "Select weather type.", "Weather Tool", WEATHER_TYPES, "msgHandleWeatherSelection", true, "weather.pid", "weather.list");
            return SCRIPT_OVERRIDE;
        }
        else if ((toLower(command)).equals("qaquestdump") || (toLower(command)).equals("questdump"))
        {
            saveAllCurrentQuestData(self);
            return SCRIPT_OVERRIDE;
        }
        else if ((toLower(command)).equals("vetrewards") || (toLower(command)).equals("vetreward"))
        {
            String[] allRewardTemplates = dataTableGetStringColumn(VET_REWARDS_TABLE, "Object Template");
            Arrays.sort(allRewardTemplates);
            if (allRewardTemplates.length > 0)
            {
                qa.refreshMenu(self, "The list below shows veteran rewards available", "Vet Reward Tool", allRewardTemplates, "vetRewardOptionHandler", true, "vetreward.pid", "vetreward.allTemplates");
            }
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(command)).equals("serialize"))
        {
            try
            {
                String serialCommand = st.nextToken();
                if (!serialCommand.equals("") || serialCommand != null)
                {
                    obj_id item = utils.stringToObjId(serialCommand);
                    if (!isIdValid(item))
                    {
                        sendSystemMessageTestingOnly(self, "To serialize an item you need to specify a valid OID.");
                        return SCRIPT_CONTINUE;
                    }
                    else
                    {
                        setCraftedId(item, utils.getInventoryContainer(self));
                        setCrafter(item, self);
                        sendSystemMessageTestingOnly(self, "Item " + item + " has been serialized.");
                    }
                }
            }
            catch(Exception e)
            {
                sendSystemMessageTestingOnly(self, "You need to specify the object Id of the object you want to be serialized.");
                sendSystemMessageTestingOnly(self, "/qatool serialize <OID>");
            }
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(command)).equals("entertaineritems"))
        {
            for (String entertainerItem : ENTERTAINER_ITEMS) {
                qa.templateObjectSpawner(self, entertainerItem);
            }
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(command)).equals("skills") || (toLower(command)).equals("skillmods") || (toLower(command)).equals("allskillmods"))
        {
            getAllSkillMods(self);
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(command)).equals("resetexpertise") || (toLower(command)).equals("resetexpertises"))
        {
            utils.fullExpertiseReset(self, true);
            grantSkill(self, "expertise");
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(command)).equals("buff") || (toLower(command)).equals("qabuff"))
        {
            int stringCount = st.countTokens();
            String[] allBuffs = dataTableGetStringColumn(BUFF_TABLE, "NAME");
            Arrays.sort(allBuffs);
            if (stringCount == 1)
            {
                String buffArg = st.nextToken();
                if (buffArg.equals("remove") || buffArg.equals("removebuff") || buffArg.equals("clear"))
                {
                    buff.removeAllBuffs(self);
                    sendSystemMessageTestingOnly(self, "Buffs cleared.");
                }
                else
                {
                    boolean foundBuff = false;
                    for (String allBuff : allBuffs) {
                        if (allBuff.equals(buffArg)) {
                            String buffName = qa.getClientBuffName(self, buffArg);
                            if (!buffName.equals("null")) {
                                qa.applyBuffOption(self, buffArg, buffName);
                                foundBuff = true;
                            }
                            break;
                        }
                    }
                    if (!foundBuff)
                    {
                        sendSystemMessageTestingOnly(self, "Either the Buff or the Buff name was not found.");
                    }
                }
            }
            else
            {
                utils.setScriptVar(self, "qabuff.buffMenu", allBuffs);
                qa.refreshMenu(self, BUFF_TOOL_PROMPT, BUFF_TOOL_TITLE, allBuffs, "buffOptionHandler", "qabuff.pid", sui.OK_CANCEL_REFRESH);
            }
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(command)).equals("qaquest"))
        {
            try
            {
                String[] allQuests = qa.getAllQuests(self);
                String[] combinedMenu = new String[allQuests.length + QUEST_TOOL_MENU.length];
                System.arraycopy(allQuests, 0, combinedMenu, 0, allQuests.length);
                System.arraycopy(QUEST_TOOL_MENU, 0, combinedMenu, allQuests.length, QUEST_TOOL_MENU.length);
                qa.refreshMenu(self, QUEST_TOOL_PROMPT, QUEST_TOOL_TITLE, combinedMenu, "handleMainMenuOptions", true, "qaquest.pid", "qaquest.qaquestMenu");
            }
            catch(Exception e)
            {
                qa.refreshMenu(self, QUEST_TOOL_PROMPT + "\n\nNo quests found on character", QUEST_TOOL_TITLE, QUEST_TOOL_MENU, "handleMainMenuOptions", true, "qaquest.pid", "qaquest.qaquestMenu");
            }
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(command)).equals("smugglingquest"))
        {
            String questLevel = "1";
            int stringCount = st.countTokens();
            if (stringCount == 1)
            {
                questLevel = st.nextToken();
                int num = utils.stringToInt(questLevel);
                if (num < 1 || num > 5)
                {
                    return SCRIPT_CONTINUE;
                }
            }
            groundquests.requestGrantQuest(self, "quest/smuggle_illicit_" + questLevel, true);
            int questCrc = groundquests.getQuestIdFromString("quest/smuggle_illicit_" + questLevel);
            int taskId = 10;
            dictionary qparams = new dictionary();
            qparams.put("questCrc", questCrc);
            qparams.put("taskId", taskId);
            String baseObjVar = groundquests.setBaseObjVar(self, "spawn", questGetQuestName(questCrc), taskId);
            setObjVar(self, baseObjVar + "." + "playedTimeEnd", 0);
            messageTo(self, "messageStartQuestSpawn", qparams, 3, true);
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(command)).equals("smugglermodule"))
        {
            obj_id module = utils.getStaticItemInInventory(self, "item_reward_modify_pistol_01_01");
            if (isIdValid(module))
            {
                incrementCount(module, 100);
            }
            else
            {
                static_item.createNewItemFunction("item_reward_modify_pistol_01_01", self);
                module = utils.getStaticItemInInventory(self, "item_reward_modify_pistol_01_01");
                incrementCount(module, 99);
            }
        }
        else if ((toLower(command)).equals("contraband") || (toLower(command)).equals("contrabandspawner"))
        {
            int stringCount = st.countTokens();
            if (stringCount == 1)
            {
                String stringNumber = st.nextToken();
                int intNumber = utils.stringToInt(stringNumber);
                if (intNumber > 0 && intNumber < 11)
                {
                    for (String contrabandString : CONTRABAND_STRINGS) {
                        mulipleStaticSpawn(self, contrabandString, stringNumber, true);
                    }
                }
                else
                {
                    sendSystemMessageTestingOnly(self, "The tool requires the following usage to function: /qatool contraband <integer between 1 - 10> ");
                }
            }
            else
            {
                sendSystemMessageTestingOnly(self, "The tool requires the following usage to function: /qatool contraband <integer between 1 - 10> ");
            }
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(command)).equals("multi") || (toLower(command)).equals("multispawn") || (toLower(command)).equals("repeat") || (toLower(command)).equals("repeatspawn"))
        {
            int stringCount = st.countTokens();
            if (stringCount == 2)
            {
                String spawnString = st.nextToken();
                String intNumber = st.nextToken();
                boolean spawningComplete = mulipleStaticSpawn(self, spawnString, intNumber, true);
                if (!spawningComplete)
                {
                    sendSystemMessageTestingOnly(self, "The tool requires the following usage to function: /qatool mutli <static_item_spawn_string> <integer> ");
                }
            }
            else
            {
                sendSystemMessageTestingOnly(self, "The tool requires the following usage to function: /qatool mutli <static_item_spawn_string> <integer> ");
            }
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(command)).equals("spec") || (toLower(command)).equals("specme"))
        {
            boolean successMsg = specTester(self, st);
            if (!successMsg)
            {
                sendSystemMessageTestingOnly(self, "***The tool failed in part or in full.  Make sure your arguments are spelled correctly and try again.***");
                return SCRIPT_CONTINUE;
            }
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(command)).equals("enzyme") || (toLower(command)).equals("qaenzyme"))
        {
            boolean successMsg = createEnzyme(self, st);
            if (!successMsg)
            {
                sendSystemMessageTestingOnly(self, "***The tool failed in part or in full.  Make sure your arguments are spelled correctly and try again.***");
                return SCRIPT_CONTINUE;
            }
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(command)).equals("lyase") || (toLower(command)).equals("qalyase"))
        {
            boolean successMsg = createLyase(self);
            if (!successMsg)
            {
                sendSystemMessageTestingOnly(self, "***The tool failed in part or in full.  Make sure your arguments are spelled correctly and try again.***");
                return SCRIPT_CONTINUE;
            }
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(command)).equals("clearheroictimer"))
        {
            boolean successMsg = clearHeroicTimer(self);
            if (!successMsg)
            {
                return SCRIPT_CONTINUE;
            }
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(command)).equals("iso") || (toLower(command)).equals("qaiso"))
        {
            boolean successMsg = createIsomerase(self);
            if (!successMsg)
            {
                sendSystemMessageTestingOnly(self, "***The tool failed in part or in full.  Make sure your arguments are spelled correctly and try again.***");
                return SCRIPT_CONTINUE;
            }
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(command)).equals("revokepilot") || (toLower(command)).equals("pilotrevoke"))
        {
            qa.revokePilotingSkills(self);
            sendSystemMessageTestingOnly(self, "Pilot Skills revoked");
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(command)).equals("qange"))
        {
            qa.createInputBox(self, NGE_TOOL_PROMPT, NGE_TOOL_TITLE, "handleGiveRespecItem", "qange.pid");
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(command)).equals("qa_cube") || (toLower(command)).equals("qacube"))
        {
            qaCubeMenu(self);
        }
        else if ((toLower(command)).equals("objfinder") || (toLower(command)).equals("findobj") || (toLower(command)).equals("objectfinder") || (toLower(command)).equals("findobject"))
        {
            objectFinderFunction(self, st);
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(command)).equals("gmr") || (toLower(command)).equals("gmrevive"))
        {
            healShockWound(self, getShockWound(self));
            setAttrib(self, HEALTH, getMaxAttrib(self, HEALTH));
            sendSystemMessageTestingOnly(self, "gmr used on self");
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(command)).equals("helper") || (toLower(command)).equals("qahelper") || (toLower(command)).equals("aihelper"))
        {
            if (getGodLevel(self) >= 10)
            {
                makeHelper(self, st);
                CustomerServiceLog("qaTool", "User: (" + self + ") " + getName(self) + " has spawned " + st + " as a Helper Mobile.  This creature can be given specific commands like a pet and is used for combat testing.  The creature is spawned using the QA Helper Tool.");
            }
            else
            {
                sendSystemMessage(self, "You do not have the appropriate access level to use this tool.", null);
            }
        }
        else if ((toLower(command)).equals("qalootloggerdwb") || (toLower(command)).equals("dwblootlogger") || (toLower(command)).equals("lootloggerdwb"))
        {
            cleanAllScriptVars(self);
            lootLoggerTool(self, st, "theme_park.dungeon.death_watch_bunker.death_watch_death");
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(command)).equals("logger") || (toLower(command)).equals("lootlogger") || (toLower(command)).equals("qalootlogger"))
        {
            cleanAllScriptVars(self);
            lootLoggerTool(self, st, "none");
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(command)).equals("targetdata") || (toLower(command)).equals("target") || (toLower(command)).equals("dumptarget") || (toLower(command)).equals("dump"))
        {
            targetDataToolFunction(self, st);
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(command)).equals("damagemob") || (toLower(command)).equals("damage") || (toLower(command)).equals("damageplayer") || (toLower(command)).equals("damageself"))
        {
            qa.damageMobTool(self);
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(command)).equals("healmob") || (toLower(command)).equals("heal") || (toLower(command)).equals("healplayer"))
        {
            qa.healMobTool(self);
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(command)).equals("npcname") || (toLower(command)).equals("mobfinder") || (toLower(command)).equals("npcfinder") || (toLower(command)).equals("findnpc"))
        {
            sendSystemMessageTestingOnly(self, "Collecting list of Mobs and NPCs. Please wait.");
            npcFinderFunction(self, st);
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(command)).equals("string") || (toLower(command)).equals("verifystring") || (toLower(command)).equals("stringcode") || (toLower(command)).equals("validatestring"))
        {
            String argString = "";
            if (st.hasMoreTokens())
            {
                int stringCount = st.countTokens();
                argString = st.nextToken();
                if (stringCount == 1)
                {
                    String retrievedString = parseArgumentRetrieveString(self, argString);
                    sui.msgbox(self, self, retrievedString, sui.OK_ONLY, QATOOL_TITLE, "noHandler");
                }
                else if (stringCount > 1)
                {
                    sendSystemMessageTestingOnly(self, "The String ID must not contain any spaces.");
                    sendSystemMessageTestingOnly(self, "Example: /qaTool string [conversation/tatooine_espa_watto]:s_106");
                    return SCRIPT_CONTINUE;
                }
                else
                {
                    sendSystemMessageTestingOnly(self, "The proper format for this command is: /qaTool string [code/identifying_the_string]:string_id ");
                    sendSystemMessageTestingOnly(self, "Example: /qaTool string [conversation/tatooine_espa_watto]:s_106 ");
                    return SCRIPT_CONTINUE;
                }
            }
            else
            {
                sendSystemMessageTestingOnly(self, "The proper format for this command is: /qaTool string [code/identifying_the_string]:string_id ");
                sendSystemMessageTestingOnly(self, "Example: /qaTool string [conversation/tatooine_espa_watto]:s_106 ");
                return SCRIPT_CONTINUE;
            }
        }
        else if ((toLower(command)).equals("qaprofession"))
        {
            utils.setScriptVar(self, "qaprofession.mainMenu", PROFESSION_TOOL_MENU);
            qa.refreshMenu(self, PROFESSION_TOOL_PROMPT, PROFESSION_TOOL_TITLE, PROFESSION_TOOL_MENU, "handleMainMenuOptions", "qaprofession.pid", sui.OK_CANCEL_REFRESH);
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(command)).equals("qaweapon"))
        {
            qa.refreshMenu(self, "-Weapon Type Select Menu-\nChoose the type of weapon you wish to use for testing.", "QA Test Weapon Tool", WEAPON_TYPE, "handleWeaponTypeOptions", "qaweapon.pid", "qaweapon.weaponTypeMenu", sui.OK_CANCEL_REFRESH);
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(command)).equals("qaitem"))
        {
            utils.setScriptVar(self, "qaitem.mainMenu", ITEM_TOOL_MENU);
            qa.refreshMenu(self, ITEM_TOOL_PROMPT, ITEM_TOOL_TITLE, ITEM_TOOL_MENU, "handleMainMenuOptions", "qaitem.pid", sui.OK_CANCEL_REFRESH);
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(command)).equals("qadatapad"))
        {
            toolWarpMenu(self);
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(command)).equals("qascript"))
        {
            utils.setScriptVar(self, "qascript.mainMenu", SCRIPT_TOOL_MENU);
            String toolPrompt = scriptToolPromptMaker(self);
            qa.refreshMenu(self, toolPrompt, SCRIPT_TOOL_TITLE, SCRIPT_TOOL_MENU, "handleMainMenuOptions", "qascript.pid", sui.OK_CANCEL_REFRESH);
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(command)).equals("qabadge"))
        {
            Vector vectorMenuArray = new Vector();
            vectorMenuArray.addElement("*Add All Badges*");
            vectorMenuArray.addElement("*Remove All Badges*");
            String[] badgePages = getAllCollectionPagesInBook("badge_book");
            if ((badgePages != null) && (badgePages.length > 0))
            {
                for (String badgePage : badgePages) {
                    if (!badgePage.equals("bdg_accumulation")) {
                        vectorMenuArray.addElement(badgePage);
                    }
                }
            }
            String[] badgeMenuArray = new String[vectorMenuArray.size()];
            vectorMenuArray.toArray(badgeMenuArray);
            utils.setScriptVar(self, "qabadge.mainMenu", badgeMenuArray);
            if (badgeMenuArray.length < 1)
            {
                sendSystemMessageTestingOnly(self, "Badge UI creation failed.");
            }
            utils.setScriptVar(self, "qabadge.mainMenu", badgeMenuArray);
            qa.refreshMenu(self, "Choose the Badge", "Badge Granter", badgeMenuArray, "mainMenuOptions", "qabadge.pid", sui.OK_CANCEL_REFRESH);
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(command)).equals("qawearables"))
        {
            String[] wearableMenuArray = qa.populateArray(self, "wearable_specie", "datatables/test/qa_wearables.iff");
            utils.setScriptVar(self, "qawearable.mainMenu", wearableMenuArray);
            qa.refreshMenu(self, "Choose the species", "Wearables Spawner", wearableMenuArray, "wearableTypeOptionSelect", "qawearable.pid", sui.OK_CANCEL_REFRESH);
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(command)).equals("qainv") || (toLower(command)).equals("inventory") || (toLower(command)).equals("qainventory"))
        {
            qa.refreshMenu(self, PROMPT, TITLE, INVENTORY_TOOL_MENU, "mainMenuOptions", "qainv.pid", sui.OK_CANCEL_REFRESH);
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(command)).equals("qafaction"))
        {
            qa.refreshMenu(self, PROMPT, TITLE, FACTION_TOOL_MENU, "mainMenuOptions", "qafac.pid", sui.OK_CANCEL_REFRESH);
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(command)).equals("qabag"))
        {
            obj_id inventory = utils.getInventoryContainer(self);
            obj_id[] invAndEquip = getInventoryAndEquipment(self);
            boolean hasBag = false;
            for (obj_id obj_id : invAndEquip) {
                String templateName = getTemplateName(obj_id);
                if (templateName.equals("object/tangible/test/qabag.iff")) {
                    hasBag = true;
                }
            }
            if (hasBag == false)
            {
                createObjectInInventoryAllowOverload("object/tangible/test/qabag.iff", self);
                sendSystemMessageTestingOnly(self, "QA Bag Granted");
                return SCRIPT_CONTINUE;
            }
            else
            {
                sendSystemMessageTestingOnly(self, "You already have the QA bag");
                return SCRIPT_CONTINUE;
            }
        }
        else if ((toLower(command)).equals("qaxp"))
        {
            qa.refreshMenu(self, "Select the xp type...", "Beta XP Dispenser", XP_TOOL_MENU, "handleXpOptions", "qaxp.pid", sui.OK_CANCEL_REFRESH);
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(command)).equals("qadynamic"))
        {
            qa.refreshMenu(self, DYNAMIC_DESCRIPTION, "Dynamic Loot Spawner", DATA_SOURCE_MENU_LIST, "handleMainOptions", "qadynamic.pid", "qadynamic.dynamicMainMenu", sui.OK_CANCEL_REFRESH);
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(command)).equals("qajtl") || (toLower(command)).equals("spacetool"))
        {
            utils.setScriptVar(self, "qajtl.mainMenu", JTL_TOOL_MENU);
            qa.refreshMenu(self, "Tool for JTL specific testing needs.", "QA JTL TOOL", JTL_TOOL_MENU, "mainMenuOptions", false, "qajtl.pid", "qajtl.mainMenu");
        }
        else if ((toLower(command)).equals("qaresource") || (toLower(command)).equals("qaresources"))
        {
            utils.setScriptVar(self, "resource.mainMenu", RESOURCE_TOOL_MENU);
            qa.refreshMenu(self, RESOURCE_TOOL_PROMPT, RESOURCE_TOOL_TITLE, RESOURCE_TOOL_MENU, "startingMenuOptions", "resource.pid", sui.OK_CANCEL_REFRESH);
        }
        else if ((toLower(command)).equals("qarewardresource") || (toLower(command)).equals("qarewardresources"))
        {
            qa.refreshMenu(self, RESOURCE_REWARD_TOOL_PROMPT, RESOURCE_REWARD_TOOL_TITLE, REWARD_RESOURCE_MENU, "handleQATool", "qarewardresource.pid", "qarewardresource.mainMenu", sui.OK_CANCEL);
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(command)).equals("groundmoblevel") || (toLower(command)).equals("groundlevelmob"))
        {
            if (isSpaceScene())
            {
                sendSystemMessageTestingOnly(self, "You have to be on the ground to use this tool.");
            }
            else
            {
                String argumentString = null;
                if (st.hasMoreTokens())
                {
                    argumentString = st.nextToken();
                    HashSet foundCreatures = new HashSet();
                    int mobLevel = utils.stringToInt(argumentString);
                    int[] arrayOfAllCreatures = dataTableGetIntColumn(CREATURE_TABLE, "BaseLevel");
                    int creatureArraySize = arrayOfAllCreatures.length;
                    for (int i = 0; i < creatureArraySize; i++)
                    {
                        if (arrayOfAllCreatures[i] == mobLevel)
                        {
                            foundCreatures.add(dataTableGetString(CREATURE_TABLE, i, "creatureName"));
                        }
                    }
                    if (foundCreatures.size() > 0)
                    {
                        String[] ArrayOfFoundCreatures = new String[foundCreatures.size()];
                        foundCreatures.toArray(ArrayOfFoundCreatures);
                        Arrays.sort(ArrayOfFoundCreatures);
                        qa.refreshMenu(self, "List of creatures that are level " + mobLevel + ".", "QA GROUND MOB SEARCH TOOL", ArrayOfFoundCreatures, "groundMobSearchOptions", true, "groundMobSearch.pid", "groundMobSearch.creaturesFound");
                    }
                    else
                    {
                        sendSystemMessageTestingOnly(self, "The Search String you entered was not found");
                        cleanAllScriptVars(self);
                        return SCRIPT_CONTINUE;
                    }
                }
                else
                {
                    sendSystemMessageTestingOnly(self, "A level number is needed for this tool to work.  Example '/qatool groundmoblevel 2'");
                    cleanAllScriptVars(self);
                    return SCRIPT_CONTINUE;
                }
            }
        }
        else if ((toLower(command)).equals("groundmobplanet") || (toLower(command)).equals("groundplanetmob"))
        {
            if (isSpaceScene())
            {
                sendSystemMessageTestingOnly(self, "You have to be on the ground to use this tool.");
            }
            else
            {
                String argumentString = null;
                HashSet planetHash = new HashSet();
                String[] arrayOfLocations = dataTableGetStringColumn(CREATURE_TABLE, "where");
                if (st.hasMoreTokens())
                {
                    argumentString = toLower(st.nextToken());
                    String[] arrayOfAllCreatures = dataTableGetStringColumn(CREATURE_TABLE, "creatureName");
                    int arrayOfLocationsSize = arrayOfLocations.length;
                    for (int x = 0; x < arrayOfLocationsSize; x++)
                    {
                        if (arrayOfLocations[x].equals(argumentString))
                        {
                            planetHash.add(arrayOfAllCreatures[x]);
                        }
                    }
                    if (planetHash.size() > 0)
                    {
                        String[] ArrayOfFoundCreatures = new String[planetHash.size()];
                        planetHash.toArray(ArrayOfFoundCreatures);
                        Arrays.sort(ArrayOfFoundCreatures);
                        qa.refreshMenu(self, "List of found creatures.", "QA GROUND MOB SEARCH TOOL", ArrayOfFoundCreatures, "groundMobSearchOptions", true, "groundMobSearch.pid", "groundMobSearch.creaturesFound");
                    }
                    else
                    {
                        sendSystemMessageTestingOnly(self, "The planet string you entered was not found");
                        cleanAllScriptVars(self);
                        return SCRIPT_CONTINUE;
                    }
                }
                else
                {
                    int arrayOfLocationsSize = arrayOfLocations.length;
                    for (String arrayOfLocation : arrayOfLocations) {
                        if (arrayOfLocation.equals("")) {
                            planetHash.add("no_location_listed");
                        } else {
                            planetHash.add(toLower(arrayOfLocation));
                        }
                    }
                }
            }
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(command)).equals("groundmobsearch") || (toLower(command)).equals("groundsearchmob"))
        {
            if (isSpaceScene())
            {
                sendSystemMessageTestingOnly(self, "You have to be on the ground to use this tool.");
            }
            else
            {
                String argumentString = null;
                if (st.hasMoreTokens())
                {
                    argumentString = st.nextToken();
                    HashSet foundCreatures = new HashSet();
                    String[] arrayOfAllCreatures = dataTableGetStringColumn(CREATURE_TABLE, "creatureName");
                    int creatureArraySize = arrayOfAllCreatures.length;
                    for (String arrayOfAllCreature : arrayOfAllCreatures) {
                        if (arrayOfAllCreature.contains(argumentString)) {
                            foundCreatures.add(arrayOfAllCreature);
                        }
                    }
                    if (foundCreatures.size() > 0)
                    {
                        String[] ArrayOfFoundCreatures = new String[foundCreatures.size()];
                        foundCreatures.toArray(ArrayOfFoundCreatures);
                        Arrays.sort(ArrayOfFoundCreatures);
                        qa.refreshMenu(self, "List of found creatures.", "QA GROUND MOB SEARCH TOOL", ArrayOfFoundCreatures, "groundMobSearchOptions", true, "groundMobSearch.pid", "groundMobSearch.creaturesFound");
                    }
                    else
                    {
                        sendSystemMessageTestingOnly(self, "The Search String you entered was not found");
                        cleanAllScriptVars(self);
                        return SCRIPT_CONTINUE;
                    }
                }
                else
                {
                    sendSystemMessageTestingOnly(self, "Format: /qatool groundmobsearch <creature name or partial name> ");
                    cleanAllScriptVars(self);
                    return SCRIPT_CONTINUE;
                }
            }
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(command)).equals("spacemobsearch"))
        {
            if (isSpaceScene())
            {
                String argumentString = null;
                if (st.hasMoreTokens())
                {
                    argumentString = st.nextToken();
                    HashSet foundCreatures = new HashSet();
                    String[] arrayOfAllCreatures = dataTableGetStringColumn(SPACE_MOBILE_TABLE, "strIndex");
                    int creatureArraySize = arrayOfAllCreatures.length;
                    for (String arrayOfAllCreature : arrayOfAllCreatures) {
                        if (arrayOfAllCreature.contains(argumentString)) {
                            foundCreatures.add(arrayOfAllCreature);
                        }
                    }
                    if (foundCreatures.size() > 0)
                    {
                        String[] ArrayOfFoundCreatures = new String[foundCreatures.size()];
                        foundCreatures.toArray(ArrayOfFoundCreatures);
                        Arrays.sort(ArrayOfFoundCreatures);
                        qa.refreshMenu(self, "List of space mobiles found.", "QA SPACE MOB SEARCH TOOL", ArrayOfFoundCreatures, "spaceMobSearchOptions", true, "spaceMobSearch.pid", "spaceMobSearch.mobsFound");
                    }
                    else
                    {
                        sendSystemMessageTestingOnly(self, "The Search String you entered was not found");
                        cleanAllScriptVars(self);
                        return SCRIPT_CONTINUE;
                    }
                }
                else
                {
                    sendSystemMessageTestingOnly(self, "Format: /qatool spacemobsearch <possible enemy ship name>");
                    cleanAllScriptVars(self);
                    return SCRIPT_CONTINUE;
                }
            }
            else
            {
                sendSystemMessageTestingOnly(self, "You need to be in space to use this tool.");
            }
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(command)).equals("qafrog") || (toLower(command)).equals("frog"))
        {
            giveTesterAFrog(self);
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(command)).equals("lootsearch") || (toLower(command)).equals("lootnamesearch"))
        {
            String argumentString = null;
            if (st.hasMoreTokens())
            {
                argumentString = st.nextToken();
                searchStaticLoot(self, argumentString, "string");
            }
            else
            {
                sendSystemMessageTestingOnly(self, "You need to type a search string. \nExample: /qaTool lootnamesearch pistol");
            }
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(command)).equals("lootstringsearch") || (toLower(command)).equals("lootcodesearch"))
        {
            String argumentString = null;
            if (st.hasMoreTokens())
            {
                argumentString = st.nextToken();
                searchStaticLoot(self, argumentString, "codestring");
            }
            else
            {
                sendSystemMessageTestingOnly(self, "You need to type a search string. \nExample: /qaTool lootcodesearch weapon_pistol");
            }
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(command)).equals("spawnitem"))
        {
            String argumentString = null;
            if (st.hasMoreTokens())
            {
                argumentString = st.nextToken();
                qa.spawnStaticItemInInventory(self, argumentString, "none");
            }
            else
            {
                sendSystemMessageTestingOnly(self, "You need to type a code string. \nExample: /qaTool item weapon_pistol_02_02.\n\n  If you don't know the code string to spawn something use one of the following tools: /qatool lootnamesearch <searchString> or /qatool lootcodesearch <searchString>");
            }
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(command)).equals("kill") || (toLower(command)).equals("killplayer"))
        {
            String override = null;
            if (st.hasMoreTokens())
            {
                override = st.nextToken();
            }
            obj_id objKillTarget = qaGetTarget(self, override);
            int commandKillResult = commandKill(self, objKillTarget);
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(command)).equals("suicide"))
        {
            if (!isDead(self))
            {
                sendSystemMessageTestingOnly(self, "Killing self.");
                setPosture(self, POSTURE_INCAPACITATED);
                pclib.coupDeGrace(self, self, true, true);
            }
            else
            {
                sendSystemMessageTestingOnly(self, "You're already dead.");
            }
        }
        else if ((toLower(command)).equals("aistop"))
        {
            obj_id stopTarget = qa.findTarget(self);
            if (isValidId(stopTarget) && isMob(stopTarget))
            {
                ai_lib.setDefaultCalmBehavior(stopTarget, ai_lib.BEHAVIOR_SENTINEL);
                sendSystemMessageTestingOnly(self, "target(" + stopTarget + ") STOPPED at location.  The target WILL defend itself.");
                CustomerServiceLog("qaTool", "User: (" + self + ") " + getName(self) + " has stopped (" + stopTarget + ") " + utils.getStringName(stopTarget) + " using the aiStop command.");
            }
            else
            {
                sendSystemMessageTestingOnly(self, "target empty or invalid");
            }
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(command)).equals("aifreeze"))
        {
            obj_id stopTarget = qa.findTarget(self);
            if (isValidId(stopTarget) && isMob(stopTarget))
            {
                ai_lib.setDefaultCalmBehavior(stopTarget, ai_lib.BEHAVIOR_SENTINEL);
                detachScript(stopTarget, "ai.creature_combat");
                sendSystemMessageTestingOnly(self, "target(" + stopTarget + ") STOPPED at location.  The target will NOT defend itself.");
                CustomerServiceLog("qaTool", "User: (" + self + ") " + getName(self) + " has frozen (" + stopTarget + ") " + utils.getStringName(stopTarget) + " using the aiFreeze command.");
            }
            else
            {
                sendSystemMessageTestingOnly(self, "target empty or invalid");
            }
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(command)).equals("qadna"))
        {
            qa.refreshMenu(self, DNA_PROMPT, DNA_TITLE, PET_OPTION_MENU, "handlePetOptionsTool", "qadna.pid", sui.OK_CANCEL_REFRESH);
        }
        else if ((toLower(command)).equals("breakcloning"))
        {
            location playerLoc = getLocation(self);
            String planetName = playerLoc.area;
            if (planetName == null)
            {
                return SCRIPT_CONTINUE;
            }
            obj_id planet = getPlanetByName(planetName);
            if (!isIdValid(planet))
            {
                return SCRIPT_CONTINUE;
            }
            float x = playerLoc.x;
            float z = playerLoc.z;
            obj_id container = getTopMostContainer(self);
            location worldLoc = getWorldLocation(container);
            String area = getBuildoutAreaName(worldLoc.x, worldLoc.z);
            if (area == null)
            {
                area = "";
            }
            Vector idList = utils.getResizeableObjIdArrayScriptVar(planet, cloninglib.VAR_PLANET_CLONE_ID);
            Vector nameList = utils.getResizeableStringArrayScriptVar(planet, cloninglib.VAR_PLANET_CLONE_NAME);
            if (idList.size() == nameList.size())
            {
                idList.add(self);
                utils.setScriptVar(planet, cloninglib.VAR_PLANET_CLONE_ID, idList);
                sendSystemMessageTestingOnly(self, "Cloning planet lists broken for " + planetName);
            }
            else
            {
                sendSystemMessageTestingOnly(self, "Cloning planet lists are already invalid.");
            }
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(command)).equals("qaexportbackpack"))
        {
            cleanAllScriptVars(self);
            qaExportBackpackContents(self);
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(command)).equals("qatcg") || (toLower(command)).equals("tcg"))
        {
            qaTCGMenu(self);
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(command)).equals("spawnship") || (toLower(command)).equals("qaspawnship"))
        {
            if (!isSpaceScene())
            {
                sendSystemMessage(self, "You must be in space to use this tool", null);
                return SCRIPT_CONTINUE;
            }
            transform gloc = getTransform_o2w(space_transition.getContainingShip(self));
            float dist = 200.0f;
            vector n = ((gloc.getLocalFrameK_p()).normalize()).multiply(dist);
            gloc = gloc.move_p(n);
            String targetShipType = "blacksun_bomber_s03_tier5";
            int numShips = 1;
            if (st.hasMoreTokens())
            {
                targetShipType = st.nextToken();
                if (st.hasMoreTokens())
                {
                    String nextToken = st.nextToken();
                    int ns = Integer.parseInt(nextToken);
                    if (ns > 1)
                    {
                        numShips = ns;
                    }
                }
            }
            for (int i = 0; i < numShips; i++)
            {
                obj_id targetShip = space_create.createShipHyperspace(targetShipType, gloc);
                sendSystemMessage(self, "Spawned ship - OID: " + targetShip, null);
            }
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(command)).equals("?"))
        {
            String allHelpData = "";
            Arrays.sort(QATOOLPROMPT);
            for (String s : QATOOLPROMPT) {
                allHelpData = allHelpData + s + "\r\n\t";
            }
            saveTextOnClient(self, "commandUsage.txt", allHelpData);
            sui.msgbox(self, self, allHelpData, sui.OK_ONLY, QATOOL_TITLE, "noHandler");
            return SCRIPT_CONTINUE;
        }
        else
        {
            sendSystemMessageTestingOnly(self, "No such QA Tool Command.  Please check your spelling");
        }
        return SCRIPT_CONTINUE;
    }
    public int oldItemMenuHandler(obj_id self, dictionary params) throws InterruptedException
    {
        if (isGod(self))
        {
            if (utils.hasScriptVar(self, "oldItem.pid"))
            {
                qa.checkParams(params, "oldItem");
                int idx = sui.getListboxSelectedRow(params);
                int btn = sui.getIntButtonPressed(params);
                String previousMainMenuArray[] = utils.getStringArrayScriptVar(self, "oldItem.menu");
                if (btn == sui.BP_CANCEL)
                {
                    cleanAllScriptVars(self);
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    String previousSelection = previousMainMenuArray[idx];
                    if (previousSelection.equals("No Mob/NPC Found"))
                    {
                        cleanAllScriptVars(self);
                        return SCRIPT_CONTINUE;
                    }
                    else 
                    {
                        if (previousSelection.equals(OLD_ITEM_MENU[0]))
                        {
                            for (String oldFsVillageItem : OLD_FS_VILLAGE_ITEMS) {
                                if (!oldFsVillageItem.equals("object/tangible/item/quest/force_sensitive/fs_buff_item.iff")) {
                                    qa.templateObjectSpawner(self, oldFsVillageItem);
                                } else {
                                    obj_id inventoryContainer = utils.getInventoryContainer(self);
                                    obj_id buffItem = createObject(oldFsVillageItem, inventoryContainer, "");
                                    if (isValidId(buffItem)) {
                                        setObjVar(buffItem, "item.time.reuse_time", 259200);
                                        setObjVar(buffItem, "item.buff.type", 0);
                                        setObjVar(buffItem, "item.buff.value", 2000);
                                        setObjVar(buffItem, "item.buff.duration", 7200);
                                        sendSystemMessageTestingOnly(self, "Item Issued.");
                                        CustomerServiceLog("qaTool", "User: (" + self + ") " + getName(self) + " has spawned (" + oldFsVillageItem + ") using a QA Tool or command.");
                                    }
                                }
                            }
                        }
                        if (previousSelection.equals(OLD_ITEM_MENU[1]))
                        {
                            for (String heroOfTatooineItem : HERO_OF_TATOOINE_ITEMS) {
                                qa.templateObjectSpawner(self, heroOfTatooineItem);
                            }
                        }
                        if (previousSelection.equals(OLD_ITEM_MENU[2]))
                        {
                            for (String deathWatchBunkerItem : DEATH_WATCH_BUNKER_ITEMS) {
                                qa.templateObjectSpawner(self, deathWatchBunkerItem);
                            }
                        }
                        if (previousSelection.equals(OLD_ITEM_MENU[3]))
                        {
                            for (String oldSkillBuffItem : OLD_SKILL_BUFF_ITEMS) {
                                qa.templateObjectSpawner(self, oldSkillBuffItem);
                            }
                        }
                        if (previousSelection.equals(OLD_ITEM_MENU[4]))
                        {
                            for (String craftingComponent : CRAFTING_COMPONENTS) {
                                qa.templateObjectSpawner(self, craftingComponent);
                            }
                        }
                        if (previousSelection.equals(OLD_ITEM_MENU[5]))
                        {
                            for (String varactylTreasureItem : VARACTYL_TREASURE_ITEMS) {
                                qa.templateObjectSpawner(self, varactylTreasureItem);
                            }
                        }
                        if (previousSelection.equals(OLD_ITEM_MENU[6]))
                        {
                            obj_id myBag = getObjectInSlot(self, "back");
                            obj_id testerInventoryId = utils.getInventoryContainer(self);
                            if (!isValidId(myBag) && isValidId(testerInventoryId))
                            {
                                qa.findOrCreateAndEquipQABag(self, testerInventoryId, false);
                                myBag = getObjectInSlot(self, "back");
                                for (String lootSchematic : LOOT_SCHEMATICS) {
                                    obj_id itemId = qa.templateObjectSpawner(self, lootSchematic, false);
                                    putInOverloaded(itemId, myBag);
                                }
                            }
                            else 
                            {
                                sendSystemMessageTestingOnly(self, "Delete or drop your Current QA Bag for this tool.");
                            }
                        }
                        if (previousSelection == null)
                        {
                            sendSystemMessageTestingOnly(self, "Menu Selection Failed.");
                            qa.refreshMenu(self, "Select a old item category.", "Old Item Spawner", OLD_ITEM_MENU, "oldItemMenuHandler", true, "oldItem.pid", "oldItem.menu");
                        }
                        qa.refreshMenu(self, "Select a old item category. DO NOT BUG ITEMS SPAWNED WITH THIS TOOL! DO NOT BUG ITEMS SPAWNED WITH THIS TOOL! DO NOT BUG ITEMS SPAWNED WITH THIS TOOL!", "Old Item Spawner", OLD_ITEM_MENU, "oldItemMenuHandler", true, "oldItem.pid", "oldItem.menu");
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int msgHandleWeatherSelection(obj_id self, dictionary params) throws InterruptedException
    {
        if (isGod(self))
        {
            if (utils.hasScriptVar(self, "weather.pid"))
            {
                qa.checkParams(params, "weather");
                int idx = sui.getListboxSelectedRow(params);
                int btn = sui.getIntButtonPressed(params);
                String previousMainMenuArray[] = utils.getStringArrayScriptVar(self, "weather.list");
                if (btn == sui.BP_CANCEL)
                {
                    cleanAllScriptVars(self);
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    String previousSelection = previousMainMenuArray[idx];
                    if (previousSelection.equals("No Mob/NPC Found"))
                    {
                        cleanAllScriptVars(self);
                        return SCRIPT_CONTINUE;
                    }
                    else 
                    {
                        switch (previousSelection) {
                            case "Clear":
                                setWeather(self, 0);
                                break;
                            case "Mild":
                                setWeather(self, 1);
                                break;
                            case "Heavy":
                                setWeather(self, 2);
                                break;
                            case "Severe":
                                setWeather(self, 3);
                                break;
                            default:
                                sendSystemMessageTestingOnly(self, "Selection Failed.");
                                qa.refreshMenu(self, "Select weather type.", "Weather Tool", WEATHER_TYPES, "msgHandleWeatherSelection", true, "weather.pid", "weather.list");
                                break;
                        }
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int npcFinderHandler(obj_id self, dictionary params) throws InterruptedException
    {
        if (isGod(self))
        {
            if (utils.hasScriptVar(self, "npcFinder.pid"))
            {
                qa.checkParams(params, "npcFinder");
                int idx = sui.getListboxSelectedRow(params);
                int btn = sui.getIntButtonPressed(params);
                String previousMainMenuArray[] = utils.getStringArrayScriptVar(self, "npcFinder.allMobStrings");
                if (btn == sui.BP_CANCEL)
                {
                    cleanAllScriptVars(self);
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    String previousSelection = previousMainMenuArray[idx];
                    if (previousSelection.equals("No Mob/NPC Found"))
                    {
                        cleanAllScriptVars(self);
                        return SCRIPT_CONTINUE;
                    }
                    else 
                    {
                        parseThenWarpTester(self, previousSelection);
                        cleanAllScriptVars(self);
                        return SCRIPT_CONTINUE;
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int objectFinderHandler(obj_id self, dictionary params) throws InterruptedException
    {
        if (isGod(self))
        {
            if (utils.hasScriptVar(self, "objFinder.pid"))
            {
                qa.checkParams(params, "npcFinder");
                int idx = sui.getListboxSelectedRow(params);
                int btn = sui.getIntButtonPressed(params);
                String previousMainMenuArray[] = utils.getStringArrayScriptVar(self, "objFinder.objStrings");
                if (btn == sui.BP_CANCEL)
                {
                    cleanAllScriptVars(self);
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    String previousSelection = previousMainMenuArray[idx];
                    parseThenWarpTester(self, previousSelection);
                    cleanAllScriptVars(self);
                    return SCRIPT_CONTINUE;
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int qaLootThem(obj_id self, dictionary params) throws InterruptedException
    {
        boolean isQaBackpackScriptAttached = hasScript(self, "test.qabackpack");
        if (!isQaBackpackScriptAttached)
        {
            attachScript(self, "test.qabackpack");
        }
        obj_id myMob = params.getObjId("myMob");
        obj_id myBag = params.getObjId("myBag");
        obj_id testerInventoryId = params.getObjId("testerInventoryId");
        checkMobContents(self, myMob);
        corpse.lootAICorpse(self, myMob);
        if (!isQaBackpackScriptAttached)
        {
            detachScript(self, "test.qabackpack");
        }
        return SCRIPT_CONTINUE;
    }
    public int qaSaveData(obj_id self, dictionary params) throws InterruptedException
    {
        String exportText = params.getString("exportData");
        try
        {
            obj_id myBag = params.getObjId("myBag");
            if (isValidId(myBag))
            {
                String mobContentsByRow = utils.getStringScriptVar(self, "lootLogger.mobContents");
                boolean contentsExported = exportTextBagContents(self, myBag, exportText, mobContentsByRow);
            }
        }
        catch(Exception e)
        {
        }
        messageTo(self, "cleanAllScriptVars", null, 2.0f, true);
        return SCRIPT_CONTINUE;
    }
    public int qaDestroyMob(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id myMob = params.getObjId("myMob");
        obj_id testerInventoryId = params.getObjId("testerInventoryId");
        destroyObject(myMob);
        qa.findOrCreateAndEquipQABag(self, testerInventoryId, false);
        return SCRIPT_CONTINUE;
    }
    public int qaDestroyObject(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id myMob = params.getObjId("object");
        destroyObject(myMob);
        return SCRIPT_CONTINUE;
    }
    public int cleanAllScriptVars(obj_id self, dictionary params) throws InterruptedException
    {
        cleanAllScriptVars(self);
        return SCRIPT_CONTINUE;
    }
    public int groundMobSearchOptions(obj_id self, dictionary params) throws InterruptedException
    {
        if (isGod(self))
        {
            if (utils.hasScriptVar(self, "groundMobSearch.pid"))
            {
                qa.checkParams(params, "groundMobSearch");
                int idx = sui.getListboxSelectedRow(params);
                int btn = sui.getIntButtonPressed(params);
                String previousMainMenuArray[] = utils.getStringArrayScriptVar(self, "groundMobSearch.creaturesFound");
                if (btn == sui.BP_CANCEL)
                {
                    cleanAllScriptVars(self);
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    String previousSelection = previousMainMenuArray[idx];
                    spawnGroundMob(self, previousSelection);
                    obj_id target = utils.stringToObjId(previousSelection);
                    CustomerServiceLog("qaTool", "User: (" + self + ") " + getName(self) + " has spawned (" + target + ") " + utils.getStringName(target) + " using a QA Tool SUI Menu.");
                    qa.refreshMenu(self, "List of found creatures.", "QA MOB SEARCH TOOL", previousMainMenuArray, "groundMobSearchOptions", true, "groundMobSearch.pid", "groundMobSearch.creaturesFound");
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int spaceMobSearchOptions(obj_id self, dictionary params) throws InterruptedException
    {
        if (isGod(self))
        {
            if (utils.hasScriptVar(self, "spaceMobSearch.pid"))
            {
                qa.checkParams(params, "spaceMobSearch");
                int idx = sui.getListboxSelectedRow(params);
                int btn = sui.getIntButtonPressed(params);
                String previousMainMenuArray[] = utils.getStringArrayScriptVar(self, "spaceMobSearch.mobsFound");
                if (btn == sui.BP_CANCEL)
                {
                    cleanAllScriptVars(self);
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    String previousSelection = previousMainMenuArray[idx];
                    obj_id objTest = space_transition.getContainingShip(self);
                    obj_id objShip = null;
                    if (isIdValid(objTest))
                    {
                        objShip = space_create.createShip(previousSelection, getTransform_o2p(objTest));
                    }
                    else 
                    {
                        objShip = space_create.createShip(previousSelection, getTransform_o2p(self));
                    }
                    if (!isIdValid(objShip))
                    {
                        sendSystemMessageTestingOnly(self, "Tool failed to spawn enemy mobile");
                        cleanAllScriptVars(self);
                        return SCRIPT_CONTINUE;
                    }
                    else 
                    {
                        sendSystemMessageTestingOnly(self, "Made ship of type " + previousSelection + " object id is: " + objShip);
                        qa.refreshMenu(self, "List of space mobiles found.", "QA SPACE MOB SEARCH TOOL", previousMainMenuArray, "spaceMobSearchOptions", true, "spaceMobSearch.pid", "spaceMobSearch.mobsFound");
                        CustomerServiceLog("qaTool", "User: (" + self + ") " + getName(self) + " has spawned (" + objShip + ") " + utils.getStringName(objShip) + " using a QA Tool SUI Menu.");
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int toolMainMenu(obj_id self, dictionary params) throws InterruptedException
    {
        if (isGod(self))
        {
            if (utils.hasScriptVar(self, SCRIPT_VAR + ".pid"))
            {
                String s = "";
                StringTokenizer st = new StringTokenizer(s);
                qa.checkParams(params, SCRIPT_VAR);
                obj_id player = sui.getPlayerId(params);
                int idx = sui.getListboxSelectedRow(params);
                int btn = sui.getIntButtonPressed(params);
                utils.setScriptVar(player, SCRIPT_VAR + ".toolMainMenu", QATOOL_MAIN_MENU);
                utils.setScriptVar(player, SCRIPT_VAR + ".title", TITLE);
                utils.setScriptVar(player, SCRIPT_VAR + ".prompt", PROMPT);
                if (btn == sui.BP_CANCEL)
                {
                    cleanAllScriptVars(self);
                    return SCRIPT_CONTINUE;
                }
                switch (idx)
                {
                    case BADGETOOL_MENUOPTION:
                    Vector vectorMenuArray = new Vector();
                    vectorMenuArray.addElement("*Add All Badges*");
                    vectorMenuArray.addElement("*Remove All Badges*");
                    String[] badgePages = getAllCollectionPagesInBook("badge_book");
                    if ((badgePages != null) && (badgePages.length > 0))
                    {
                        for (String badgePage : badgePages) {
                            if (!badgePage.equals("bdg_accumulation")) {
                                vectorMenuArray.addElement(badgePage);
                            }
                        }
                    }
                    String[] badgeMenuArray = new String[vectorMenuArray.size()];
                    vectorMenuArray.toArray(badgeMenuArray);
                    utils.setScriptVar(self, "qabadge.mainMenu", badgeMenuArray);
                    if (badgeMenuArray.length < 1)
                    {
                        sendSystemMessageTestingOnly(self, "Badge UI creation failed.");
                    }
                    utils.setScriptVar(self, "qabadge.mainMenu", badgeMenuArray);
                    qa.refreshMenu(self, "Choose the Badge", "Badge Granter", badgeMenuArray, "mainMenuOptions", "qabadge.pid", sui.OK_CANCEL_REFRESH);
                    break;
                    case BUFFTOOL_MENUOPTION:
                    String[] allBuffs = dataTableGetStringColumn(BUFF_TABLE, "NAME");
                    Arrays.sort(allBuffs);
                    utils.setScriptVar(self, "qabuff.buffMenu", allBuffs);
                    qa.refreshMenu(self, BUFF_TOOL_PROMPT, BUFF_TOOL_TITLE, allBuffs, "buffOptionHandler", "qabuff.pid", sui.OK_CANCEL_REFRESH);
                    break;
                    case QACUBE_MENUOPTION:
                    qaCubeMenu(player);
                    break;
                    case CYBERNETIC_MENUOPTION:
                    qa.refreshMenu(player, "- Cybernetic Install Menu -\nSelect Install, Uninstall or Repair.", "Cybernetic Tool", CYBER_MENU_LIST, "handleChoice", "qacybernetic.pid", "qacybernetic.CyberMainMenu", sui.OK_CANCEL_REFRESH);
                    break;
                    case DMGTOOL_MENUOPTION:
                    qa.damageMobTool(self);
                    break;
                    case DATAPADTOOL_MENUOPTION:
                    toolWarpMenu(self);
                    break;
                    case DYNAMIC_LOOTOPTION:
                    qa.refreshMenu(self, DYNAMIC_DESCRIPTION, "Dynamic Loot Spawner", DATA_SOURCE_MENU_LIST, "handleMainOptions", "qadynamic.pid", "qadynamic.dynamicMainMenu", sui.OK_CANCEL_REFRESH);
                    break;
                    case XPTOOL_MENUOPTION:
                    qa.refreshMenu(player, "Select the xp type...", "Beta XP Dispenser", XP_TOOL_MENU, "handleXpOptions", "qaxp.pid", sui.OK_CANCEL_REFRESH);
                    break;
                    case FACTIONTOOL_MENUOPTION:
                    qa.refreshMenu(player, PROMPT, TITLE, FACTION_TOOL_MENU, "mainMenuOptions", "qafac.pid", sui.OK_CANCEL_REFRESH);
                    break;
                    case INVTOOL_MENUOPTION:
                    qa.refreshMenu(self, PROMPT, TITLE, INVENTORY_TOOL_MENU, "mainMenuOptions", "qainv.pid", sui.OK_CANCEL_REFRESH);
                    break;
                    case MSTRITEMTOOL_MENUOPTION:
                    utils.setScriptVar(self, "qaitem.mainMenu", ITEM_TOOL_MENU);
                    qa.refreshMenu(self, ITEM_TOOL_PROMPT, ITEM_TOOL_TITLE, ITEM_TOOL_MENU, "handleMainMenuOptions", "qaitem.pid", sui.OK_CANCEL_REFRESH);
                    break;
                    case NGETOOL_MENUOPTION:
                    qa.createInputBox(player, NGE_TOOL_PROMPT, NGE_TOOL_TITLE, "handleGiveRespecItem", "qange.pid");
                    break;
                    case NPCFINDERTOOL_MENUOPTION:
                    npcFinderFunction(self, st);
                    break;
                    case PET_OPTION:
                    qa.refreshMenu(self, DNA_PROMPT, DNA_TITLE, PET_OPTION_MENU, "handlePetOptionsTool", "qadna.pid", sui.OK_CANCEL_REFRESH);
                    break;
                    case PROFESSIONTOOL_MENUOPTION:
                    utils.setScriptVar(self, "qaprofession.mainMenu", PROFESSION_TOOL_MENU);
                    qa.refreshMenu(self, PROFESSION_TOOL_PROMPT, PROFESSION_TOOL_TITLE, PROFESSION_TOOL_MENU, "handleMainMenuOptions", "qaprofession.pid", sui.OK_CANCEL_REFRESH);
                    break;
                    case QUESTTOOL_MENUOPTION:
                    try
                    {
                        String[] allQuests = qa.getAllQuests(self);
                        String[] combinedMenu = new String[allQuests.length + QUEST_TOOL_MENU.length];
                        System.arraycopy(allQuests, 0, combinedMenu, 0, allQuests.length);
                        System.arraycopy(QUEST_TOOL_MENU, 0, combinedMenu, allQuests.length, QUEST_TOOL_MENU.length);
                        qa.refreshMenu(self, QUEST_TOOL_PROMPT, QUEST_TOOL_TITLE, combinedMenu, "handleMainMenuOptions", true, "qaquest.pid", "qaquest.qaquestMenu");
                    }
                    catch(Exception e)
                    {
                        qa.refreshMenu(self, QUEST_TOOL_PROMPT + "\n\nNo quests found on character", QUEST_TOOL_TITLE, QUEST_TOOL_MENU, "handleMainMenuOptions", true, "qaquest.pid", "qaquest.qaquestMenu");
                    }
                    break;
                    case RESOURCETOOL_MENUOPTION:
                    utils.setScriptVar(player, "resource.mainMenu", RESOURCE_TOOL_MENU);
                    qa.refreshMenu(player, RESOURCE_TOOL_PROMPT, "QA Resource Tool", RESOURCE_TOOL_MENU, "startingMenuOptions", "resource.pid", sui.OK_CANCEL_REFRESH);
                    break;
                    case RESOURCE_REWARD_TOOL_MENUOPTION:
                    qa.refreshMenu(self, RESOURCE_REWARD_TOOL_PROMPT, RESOURCE_REWARD_TOOL_TITLE, REWARD_RESOURCE_MENU, "handleQATool", "qarewardresource.pid", "qarewardresource.mainMenu", sui.OK_CANCEL);
                    break;
                    case SCRIPTTOOL_MENUOPTION:
                    utils.setScriptVar(player, "qascript.mainMenu", SCRIPT_TOOL_MENU);
                    String toolPrompt = scriptToolPromptMaker(player);
                    qa.refreshMenu(player, toolPrompt, SCRIPT_TOOL_TITLE, SCRIPT_TOOL_MENU, "handleMainMenuOptions", "qascript.pid", sui.OK_CANCEL_REFRESH);
                    break;
                    case SPACETOOL_MENUOPTION:
                    utils.setScriptVar(self, "qajtl.mainMenu", JTL_TOOL_MENU);
                    qa.refreshMenu(player, "Tool for JTL specific testing needs.", "QA JTL TOOL", JTL_TOOL_MENU, "mainMenuOptions", "qajtl.pid", sui.OK_CANCEL_REFRESH);
                    break;
                    case FROGTOOL_MENUOPTION:
                    giveTesterAFrog(self);
                    break;
                    case VETERANREWARD_MENUOPTION:
                    String[] allRewardTemplates = dataTableGetStringColumn(VET_REWARDS_TABLE, "Object Template");
                    Arrays.sort(allRewardTemplates);
                    if (allRewardTemplates.length > 0)
                    {
                        qa.refreshMenu(self, "The list below shows veteran rewards available", "Vet Reward Tool", allRewardTemplates, "vetRewardOptionHandler", true, "vetreward.pid", "vetreward.allTemplates");
                    }
                    break;
                    case WEAPONS_MENUOPTION:
                    qa.refreshMenu(player, "-Weapon Type Select Menu-\nChoose the type of weapon you wish to use for testing.", "QA Test Weapon Tool", WEAPON_TYPE, "handleWeaponTypeOptions", "qaweapon.pid", "qaweapon.weaponTypeMenu", sui.OK_CANCEL_REFRESH);
                    break;
                    case WEARABLESTOOL_MENUOPTION:
                    String[] wearableMenuArray = qa.populateArray(player, "wearable_specie", "datatables/test/qa_wearables.iff");
                    utils.setScriptVar(player, "qawearable.mainMenu", wearableMenuArray);
                    qa.refreshMenu(player, "Choose the species", "Wearables Spawner", wearableMenuArray, "wearableTypeOptionSelect", "qawearable.pid", sui.OK_CANCEL_REFRESH);
                    break;
                    default:
                    cleanAllScriptVars(self);
                    return SCRIPT_CONTINUE;
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleItemSearchOptions(obj_id self, dictionary params) throws InterruptedException
    {
        if (isGod(self))
        {
            if (utils.hasScriptVar(self, "itemSearch.pid"))
            {
                qa.checkParams(params, "itemSearch");
                int idx = sui.getListboxSelectedRow(params);
                int btn = sui.getIntButtonPressed(params);
                String previousMainMenuArray[] = utils.getStringArrayScriptVar(self, "itemSearch.foundStrings");
                String listOfRowNumbers[] = utils.getStringArrayScriptVar(self, "itemSearch.foundRows");
                String listOfAllItems[] = utils.getStringArrayScriptVar(self, "itemSearch.allItems");
                if (btn == sui.BP_CANCEL)
                {
                    cleanAllScriptVars(self);
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    int selectedRow = utils.stringToInt(listOfRowNumbers[idx]);
                    String selectedItem = listOfAllItems[selectedRow];
                    qa.spawnStaticItemInInventory(self, selectedItem, "none");
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int exportFile(obj_id self, dictionary params) throws InterruptedException
    {
        int btn = sui.getIntButtonPressed(params);
        if (btn == 0)
        {
            obj_id target = utils.stringToObjId(utils.getStringScriptVar(self, "export.lookAtTarget"));
            String fileData = qa.qaTargetDump(self, target, false);
            String objName = getName(target);
            String title = "";
            title = "targetDump" + target;
            saveTextOnClient(self, title + ".txt", fileData);
        }
        cleanAllScriptVars(self);
        return SCRIPT_CONTINUE;
    }
    public int animateForceDestruction(obj_id self, dictionary params) throws InterruptedException
    {
        if (isGod(self))
        {
            doAnimationAction(self, anims.PLAYER_FORCE_DESTRUCTION);
        }
        return SCRIPT_CONTINUE;
    }
    public int runLootLoggerIncrement(obj_id self, dictionary params) throws InterruptedException
    {
        int iterationInt = params.getInt("iterationInt");
        String scriptString = params.getString("scriptString");
        String creatureName = params.getString("myName");
        int successInt = lootLoggerMain(self, iterationInt, creatureName, scriptString);
        return SCRIPT_CONTINUE;
    }
	
    public void cleanAllScriptVars(obj_id player) throws InterruptedException
    {
        utils.removeScriptVarTree(player, "qascript");
        utils.removeScriptVarTree(player, "qacraftableloot");
        utils.removeScriptVarTree(player, "qawearable");
        utils.removeScriptVarTree(player, "qabadge");
        utils.removeScriptVarTree(player, "qaquest");
        utils.removeScriptVarTree(player, "qabuff");
        utils.removeScriptVarTree(player, "qafac");
        utils.removeScriptVarTree(player, "qajtl");
        utils.removeScriptVarTree(player, "qainv");
        utils.removeScriptVarTree(player, "qaxp");
        utils.removeScriptVarTree(player, "qaGem");
        utils.removeScriptVarTree(player, "qascript");
        utils.removeScriptVarTree(player, "qange");
        utils.removeScriptVarTree(player, "qaprofession");
        utils.removeScriptVarTree(player, "npcFinder");
        utils.removeScriptVarTree(player, "doDamage");
        utils.removeScriptVarTree(player, "doDamageVar");
        utils.removeScriptVarTree(player, "export");
        utils.removeScriptVarTree(player, "itemSearch");
        utils.removeScriptVarTree(player, "qatool");
        utils.removeScriptVarTree(player, "lootLogger");
        utils.removeScriptVarTree(player, "mitigation");
        utils.removeScriptVarTree(player, "mitigationPid");
        utils.removeScriptVarTree(player, "qahelper_record");
        utils.removeScriptVarTree(player, "qahelper");
        utils.removeScriptVarTree(player, "qa_helper");
        utils.removeScriptVarTree(player, "objFinder");
        utils.removeScriptVarTree(player, "qaitem");
        utils.removeScriptVarTree(player, "qacybernetic");
        utils.removeScriptVarTree(player, "qadynamic");
        utils.removeScriptVarTree(player, "weather");
        utils.removeScriptVarTree(player, "oldItem");
        utils.removeScriptVarTree(player, SCRIPT_VAR);
    }
    public void parseThenWarpTester(obj_id self, String previousSelection) throws InterruptedException
    {
        if (previousSelection.indexOf(" ") > 0)
        {
            int objOidStart = previousSelection.indexOf("( ") + 2;
            int objOidEnd = previousSelection.length() - 2;
            obj_id objOID = utils.stringToObjId(previousSelection.substring(objOidStart, objOidEnd));
            if (isIdValid(objOID))
            {
                location objLocation = getLocation(objOID);
                warpPlayer(self, objLocation.area, objLocation.x, objLocation.y, objLocation.z, objLocation.cell, objLocation.x, objLocation.y, objLocation.z);
                CustomerServiceLog("qaTool", "User: (" + self + ") " + getName(self) + " has warped to (" + objOID + ") " + utils.getStringName(objOID) + " at location " + objLocation.area + " " + objLocation.x + " " + objLocation.y + " " + objLocation.z + " " + objLocation.cell + " " + objLocation.x + " " + objLocation.y + " " + objLocation.z + " using a QA Tool.");
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "The tool failed to attain the correct location and warp the test character.  Notify the Tool Team!");
            }
        }
        else 
        {
            try
            {
                obj_id objectId = utils.stringToObjId(previousSelection);
                location objLocation = getLocation(objectId);
                warpPlayer(self, objLocation.area, objLocation.x, objLocation.y, objLocation.z, objLocation.cell, objLocation.x, objLocation.y, objLocation.z);
                CustomerServiceLog("qaTool", "User: (" + self + ") " + getName(self) + " has warped to (" + objectId + ") " + utils.getStringName(objectId) + " at location " + objLocation.area + " " + objLocation.x + " " + objLocation.y + " " + objLocation.z + " " + objLocation.cell + " " + objLocation.x + " " + objLocation.y + " " + objLocation.z + " using a QA Tool.");
            }
            catch(Exception e)
            {
                sendSystemMessageTestingOnly(self, "Failed to create an object Id. " + e);
            }
        }
    }
    public void giveTesterAFrog(obj_id self) throws InterruptedException
    {
        obj_id inventory = utils.getInventoryContainer(self);
        if (getVolumeFree(inventory) <= 0)
        {
            sendSystemMessageTestingOnly(self, "Your Inventory is Full, please make room and try again.");
        }
        else 
        {
            createObject("object/tangible/terminal/terminal_character_builder.iff", inventory, "");
            createObject("object/tangible/terminal/terminal_kashyyyk_content.iff", inventory, "");
            sendSystemMessageTestingOnly(self, "Frog Tools Issued.");
            qa.refreshMenu(self, PROMPT, TITLE, QATOOL_MAIN_MENU, "toolMainMenu", true, SCRIPT_VAR + ".pid");
        }
    }
    public void initializeXpTool(obj_id self) throws InterruptedException
    {
        String[] xpTypes = xp.getXpTypes(self);
        if ((xpTypes != null) && (xpTypes.length > 0))
        {
            setXpTypesScriptVar(self, xpTypes);
        }
    }
    public void setXpTypesScriptVar(obj_id self, String[] varData) throws InterruptedException
    {
        if ((varData == null) || (varData.length == 0))
        {
            debugSpeakMsg(self, "setXpTypesVar: passed bad string array!");
            return;
        }
        utils.setBatchScriptVar(self, VAR_XP_TYPES_LIST, varData);
        String[] xpNames = new String[varData.length];
        for (int i = 0; i < varData.length; i++)
        {
            xpNames[i] = "@exp_n:" + varData[i];
        }
        utils.setBatchScriptVar(self, VAR_XP_TYPES_NAMES, xpNames);
    }
    public void spawnGroundMob(obj_id player, String stringIndex) throws InterruptedException
    {
        obj_id target = qa.findTarget(player);
        if (isIdValid(target) == false)
        {
            target = player;
        }
        create.createCreature(stringIndex, getLocation(target), true);
        sendSystemMessageTestingOnly(player, "Mobile spawned at location targeted.");
    }
    public String scriptToolPromptMaker(obj_id player) throws InterruptedException
    {
        String scriptList = getCharacterScriptsPrompt(player);
        return SCRIPT_TOOL_PROMPT + scriptList;
    }
    public void qaCubeMenu(obj_id player) throws InterruptedException
    {
        getNamesArray(player);
        if (utils.hasScriptVar(player, "qa_cube.codeStringArray") && utils.hasScriptVar(player, "qa_cube.showNamesArray"))
        {
            String[] showNamesArray = utils.getStringArrayScriptVar(player, "qa_cube.showNamesArray");
            qa.refreshMenu(player, CHU_GON_DAR_PROMPT, CHU_GON_DAR_TITLE, showNamesArray, "handleChuGonOptions", "qa_cube.pid", "qa_cube.ChuGonMainMenu", sui.OK_CANCEL_REFRESH);
        }
        else 
        {
            sendSystemMessageTestingOnly(player, "An error has occurred, please try again.");
        }
    }
    public String getCharacterScriptsPrompt(obj_id player) throws InterruptedException
    {
        String strScripts = "";
        String[] scriptArray = filterCharacterScripts(player);
        for (String s : scriptArray) {
            if (s.startsWith("test.")) {
                strScripts += s + "\r\n";
            } else {
                for (String scriptToolCommonScript : SCRIPT_TOOL_COMMON_SCRIPTS) {
                    if (s.equals(scriptToolCommonScript)) {
                        strScripts += s + "\r\n";
                    }
                }
            }
        }
        return strScripts;
    }
    public String[] filterCharacterScripts(obj_id player) throws InterruptedException
    {
        String strScripts = "";
        String[] scriptArray = getScriptList(player);
        HashSet theSet = new HashSet();
        for (String s : scriptArray) {
            String script = s;
            if (script.contains("script.")) {
                script = script.substring(7);
                theSet.add(script);
            }
        }
        String[] menuArray = new String[theSet.size()];
        theSet.toArray(menuArray);
        Arrays.sort(menuArray);
        return menuArray;
    }
    public void searchStaticLoot(obj_id self, String argumentString, String searchType) throws InterruptedException
    {
        Vector foundItemRowNumbers = new Vector();
        Vector finalListOfCombinedItems = new Vector();
        String[] arrayOfAllStaticItemsInDatatable = dataTableGetStringColumn(MASTER_ITEM_TABLE, "name");
        String[] arrayOfAllItems = new String[arrayOfAllStaticItemsInDatatable.length];
        for (int i = 0; i < arrayOfAllStaticItemsInDatatable.length; i++)
        {
            arrayOfAllItems[i] = localize(new string_id("static_item_n", arrayOfAllStaticItemsInDatatable[i]));
            if (searchType.equals("string"))
            {
                if (arrayOfAllItems[i] != null && !arrayOfAllItems[i].equals(""))
                {
                    if ((toLower(arrayOfAllItems[i])).contains(toLower(argumentString)))
                    {
                        foundItemRowNumbers.add("" + i);
                        finalListOfCombinedItems.add(arrayOfAllItems[i] + "\t ( " + arrayOfAllStaticItemsInDatatable[i] + " )");
                    }
                }
                else 
                {
                    dictionary troubleRow = dataTableGetRow(MASTER_ITEM_TABLE, i);
                    sendSystemMessageTestingOnly(self, "Code String Found: " + troubleRow.getString("name"));
                    sendSystemMessageTestingOnly(self, "This tool cannot be used until this code string is fixed");
                }
            }
            else 
            {
                if ((toLower(arrayOfAllStaticItemsInDatatable[i])).contains(toLower(argumentString)))
                {
                    foundItemRowNumbers.add("" + i);
                    finalListOfCombinedItems.add(arrayOfAllItems[i] + "\t ( " + arrayOfAllStaticItemsInDatatable[i] + " )");
                }
            }
        }
        String[] arrayOfFoundRowNumbers = new String[foundItemRowNumbers.size()];
        foundItemRowNumbers.toArray(arrayOfFoundRowNumbers);
        String[] arrayOfCombinedStrings = new String[finalListOfCombinedItems.size()];
        finalListOfCombinedItems.toArray(arrayOfCombinedStrings);
        if (arrayOfFoundRowNumbers.length > 0)
        {
            utils.setScriptVar(self, "itemSearch.foundRows", arrayOfFoundRowNumbers);
            utils.setScriptVar(self, "itemSearch.allItems", arrayOfAllStaticItemsInDatatable);
            qa.refreshMenu(self, "List of found items.", "QA STATIC ITEM SEARCH TOOL", arrayOfCombinedStrings, "handleItemSearchOptions", true, "itemSearch.pid", "itemSearch.foundStrings");
        }
        else 
        {
            sendSystemMessageTestingOnly(self, "The Search String you entered was not found");
            cleanAllScriptVars(self);
        }
    }
    public obj_id[] filterOIDsForMob(obj_id self, obj_id[] allOIDs) throws InterruptedException
    {
        if (allOIDs.length > -1)
        {
            Vector allMobObj_IDs = new Vector();
            allMobObj_IDs.setSize(0);
            for (obj_id allOID : allOIDs) {
                if (isMob(allOID) && !isPlayer(allOID)) {
                    utils.addElement(allMobObj_IDs, allOID);
                }
            }
            obj_id[] _allMobObj_IDs = new obj_id[0];
            if (allMobObj_IDs != null)
            {
                _allMobObj_IDs = new obj_id[allMobObj_IDs.size()];
                allMobObj_IDs.toArray(_allMobObj_IDs);
            }
            return _allMobObj_IDs;
        }
        return null;
    }
    public obj_id[] getMobList(obj_id self, location testerLoc, float range) throws InterruptedException
    {
        obj_id[] allMobsCreditKills = getAllObjectsWithScript(testerLoc, range, "systems.combat.credit_for_kills");
        obj_id[] allMobsAi = getAllObjectsWithScript(testerLoc, range, "ai.ai");
        obj_id[] allMobsCombat = getAllObjectsWithScript(testerLoc, range, "systems.combat.combat_actions");
        HashSet combinedMobArrays = new HashSet();
        if (allMobsCreditKills.length > -1)
        {
            for (obj_id allMobsCreditKill : allMobsCreditKills) {
                combinedMobArrays.add("" + allMobsCreditKill);
            }
        }
        if (allMobsAi.length > -1)
        {
            for (obj_id obj_id : allMobsAi) {
                combinedMobArrays.add("" + obj_id);
            }
        }
        if (allMobsCombat.length > -1)
        {
            for (obj_id obj_id : allMobsCombat) {
                combinedMobArrays.add("" + obj_id);
            }
        }
        if (combinedMobArrays.size() > 0)
        {
            String[] allMobOidsAsStrings = new String[combinedMobArrays.size()];
            obj_id[] allMobOids = new obj_id[combinedMobArrays.size()];
            combinedMobArrays.toArray(allMobOidsAsStrings);
            for (int i = 0; i < allMobOidsAsStrings.length; i++)
            {
                allMobOids[i] = utils.stringToObjId(allMobOidsAsStrings[i]);
            }
            if (isValidId(allMobOids[0]))
            {
                return allMobOids;
            }
        }
        return null;
    }
    public String[] getMobMenu(obj_id self, obj_id[] allMobOIDs, String searchString) throws InterruptedException
    {
        if (allMobOIDs.length > -1)
        {
            Vector mobMenu = new Vector();
            mobMenu.setSize(0);
            Vector nullMobCodes = new Vector();
            nullMobCodes.setSize(0);
            for (obj_id allMobOID : allMobOIDs) {
                String codeString = getCreatureName(allMobOID);
                String localizedString = localize(new string_id("mob/creature_names", codeString));
                if (localizedString == null) {
                    localizedString = getEncodedName(allMobOID);
                }
                if (searchString.equals("none")) {
                    utils.addElement(mobMenu, localizedString + "  ( " + allMobOID + " )");
                } else if (!searchString.equals("none") && searchString.length() > 2) {
                    String lowerCaseMobile = toLower(localizedString);
                    String lowerSearchString = toLower(searchString);
                    if (lowerCaseMobile.contains(lowerSearchString) || lowerCaseMobile.equals(lowerSearchString)) {
                        utils.addElement(mobMenu, localizedString + "  ( " + allMobOID + " )");
                    }
                } else if (!searchString.equals("none") && searchString.length() <= 2) {
                    sendSystemMessageTestingOnly(self, "The mobile name must be at least 3 characters long");
                    break;
                } else {
                    sendSystemMessageTestingOnly(self, "There was a problem with your search string.");
                    break;
                }
            }
            if (mobMenu.size() > 0)
            {
                String[] arrayStrings = new String[mobMenu.size()];
                mobMenu.toArray(arrayStrings);
                Arrays.sort(arrayStrings);
                return arrayStrings;
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "No Mobiles found.");
            }
        }
        else 
        {
            sendSystemMessageTestingOnly(self, "No Mobiles found in vicinity!");
        }
        return null;
    }
    public String parseArgumentRetrieveString(obj_id self, String argString) throws InterruptedException
    {
        if (argString.indexOf("[") == 0)
        {
            int dirEnd = argString.indexOf("]");
            String stringDir = argString.substring(1, dirEnd);
            String stringName = argString.substring(dirEnd + 2, argString.length());
            String localizedString = localize(new string_id(stringDir, stringName));
            return Objects.requireNonNullElse(localizedString, "The string ID was either incorrect or was deleted.  Review the string data you are passing to the tool and make sure it is correct.");
        }
        else 
        {
            return "The string ID must contain braces. Example: [conversation/tatooine_espa_watto]:s_106 ";
        }
    }
	

	
    public void mitigationToolFunction(obj_id self, obj_id lookAtTarget) throws InterruptedException
    {
        if (isIdValid(lookAtTarget))
        {
            if (isPlayer(lookAtTarget) || isMob(lookAtTarget))
            {
                obj_id objWeapon = getCurrentWeapon(self);
                if (isIdValid(objWeapon))
                {
                    weapon_data newWpnData = getWeaponData(objWeapon);
                    utils.setScriptVar(self, "mitigation.lookAtTarget", "" + lookAtTarget);
                    utils.setScriptVar(self, "mitigation.objWeapon", "" + objWeapon);
                    utils.setScriptVar(self, "mitigation.minDamage", newWpnData.minDamage);
                    utils.setScriptVar(self, "mitigation.maxDamage", newWpnData.maxDamage);
                    String[] dmgMenu = new String[3];
                    dmgMenu[0] = "Do Minimum Damage ( " + newWpnData.minDamage + " )";
                    dmgMenu[1] = "Do Maximum Damage ( " + newWpnData.maxDamage + " )";
                    dmgMenu[2] = "Do Random Damage";
                    utils.setScriptVar(self, "mitigation.dmgMenu", dmgMenu);
                    qa.refreshMenu(self, MITIGATION_TOOL_PROMPT, MITIGATION_TOOL_TITLE, MITIGATION_HIT_LOCATIONS, "handleAttackLocationOptions", true, "mitigationPid.pid", "mitigation.hitLocationMenu");
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
    public void npcFinderFunction(obj_id self, StringTokenizer st) throws InterruptedException
    {
        if (isSpaceScene())
        {
            sendSystemMessageTestingOnly(self, "You have to be on the ground to use this tool.");
        }
        else 
        {
            if (st.hasMoreTokens())
            {
                String argumentString = st.nextToken();
                obj_id[] allMobOids = getMobList(self, getLocation(self), MINIMUM_FINDER_RADIUS_FLOAT);
                if (allMobOids != null)
                {
                    obj_id[] allMobsWithNames = filterOIDsForMob(self, allMobOids);
                    if (allMobsWithNames.length > -1)
                    {
                        String[] mobMenu = getMobMenu(self, allMobsWithNames, argumentString);
                        if (mobMenu != null && mobMenu.length > -1)
                        {
                            obj_id[] allFoundMobOids = getMobList(self, getLocation(self), MINIMUM_FINDER_RADIUS_FLOAT);
                            if (allMobOids != null)
                            {
                                obj_id[] allFoundMobsWithNames = filterOIDsForMob(self, allFoundMobOids);
                                if (allFoundMobsWithNames.length > -1)
                                {
                                    String[] foundMobMenu = getMobMenu(self, allFoundMobsWithNames, argumentString);
                                    if (foundMobMenu != null && foundMobMenu.length > -1)
                                    {
                                        String promptAppend = "\n\r\n\rTotal Mobs Found within range of " + MINIMUM_FINDER_RADIUS_FLOAT + " = " + allFoundMobsWithNames.length + "\n\rTotal Returned =" + foundMobMenu.length;
                                        qa.refreshMenu(self, "List of Mobs/NPCs: " + promptAppend + "\n\r\n\rThis Tool is NOT 100% accurate.  Do not write defects because you cannot find NPCs using this tool.", NPCFINDER_TITLE, foundMobMenu, "npcFinderHandler", true, "npcFinder.pid", "npcFinder.allMobStrings");
                                    }
                                    else 
                                    {
                                        String promptAppend = "\n\r\n\rTotal Mobs Found within range of " + MINIMUM_FINDER_RADIUS_FLOAT + " = " + allFoundMobsWithNames.length + "\n\rTotal Returned = NOTHING";
                                        qa.refreshMenu(self, "List of Mobs/NPCs: " + promptAppend + "\n\r\n\rThis Tool is NOT 100% accurate.  Do not write defects because you cannot find NPCs using this tool.", NPCFINDER_TITLE, NPCFINDER_ERR_ARRAY, "npcFinderHandler", true, "npcFinder.pid", "npcFinder.allMobStrings");
                                    }
                                }
                            }
                        }
                    }
                }
            }
            else 
            {
                obj_id[] allMobOids = getMobList(self, getLocation(self), MINIMUM_FINDER_RADIUS_FLOAT);
                if (allMobOids != null)
                {
                    obj_id[] allMobsWithNames = filterOIDsForMob(self, allMobOids);
                    if (allMobsWithNames.length > -1)
                    {
                        String[] mobMenu = getMobMenu(self, allMobsWithNames, "none");
                        if (mobMenu != null && mobMenu.length > -1)
                        {
                            String promptAppend = "\n\r\n\rTotal Mobs Found within range of " + MINIMUM_FINDER_RADIUS_FLOAT + " = " + allMobsWithNames.length + "\n\rTotal Returned =" + mobMenu.length;
                            qa.refreshMenu(self, "List of Mobs/NPCs: " + promptAppend + "\n\r\n\rThis Tool is NOT 100% accurate.  Do not write defects because you cannot find NPCs using this tool.", NPCFINDER_TITLE, mobMenu, "npcFinderHandler", true, "npcFinder.pid", "npcFinder.allMobStrings");
                        }
                        else 
                        {
                            String promptAppend = "\n\r\n\rTotal Mobs Found within range of " + MINIMUM_FINDER_RADIUS_FLOAT + " = " + allMobsWithNames.length + "\n\rTotal Returned = NOTHING";
                            qa.refreshMenu(self, "List of Mobs/NPCs: " + promptAppend + "\n\r\n\rThis Tool is NOT 100% accurate.  Do not write defects because you cannot find NPCs using this tool.", NPCFINDER_TITLE, NPCFINDER_ERR_ARRAY, "npcFinderHandler", true, "npcFinder.pid", "npcFinder.allMobStrings");
                        }
                    }
                }
            }
        }
    }
    public void targetDataToolFunction(obj_id self, StringTokenizer st) throws InterruptedException
    {
        obj_id lookAtTarget = qa.findTarget(self);
        if (st.hasMoreTokens())
        {
            String argumentString = st.nextToken();
            lookAtTarget = utils.stringToObjId(argumentString);
            if (isValidId(lookAtTarget))
            {
                String combinedString = qa.qaTargetDump(self, lookAtTarget, true);
                String objIdString = "" + lookAtTarget;
                utils.setScriptVar(self, "export.lookAtTarget", objIdString);
                qa.createCustomUI(self, combinedString);
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "Invalid Object Id passed to tool.  Try again.");
            }
        }
        else 
        {
            if (!isIdValid(lookAtTarget))
            {
                sendSystemMessageTestingOnly(self, "You need to have something targeted to use this command");
            }
            else if (isIdValid(lookAtTarget))
            {
                String combinedString = qa.qaTargetDump(self, lookAtTarget, true);
                String objIdString = "" + lookAtTarget;
                utils.setScriptVar(self, "export.lookAtTarget", objIdString);
                qa.createCustomUI(self, combinedString);
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "you must have a valid target to use this command.");
            }
        }
    }
    public void lootLoggerTool(obj_id self, StringTokenizer st, String scriptString) throws InterruptedException
    {
        String myName = null;
        if (st.hasMoreTokens())
        {
            myName = st.nextToken();
            int dwbSpawnerRow = -1;
            int creatureRowNumber = -1;
            boolean continueSpawnTool = false;
            if (scriptString.equals("none"))
            {
                creatureRowNumber = dataTableSearchColumnForString(myName, "creatureName", CREATURE_TABLE);
                if (creatureRowNumber > -1)
                {
                    continueSpawnTool = true;
                }
            }
            else 
            {
                dwbSpawnerRow = dataTableSearchColumnForString(myName, "spawns", DWB_SPAWN_TABLE);
                creatureRowNumber = dataTableSearchColumnForString(myName, "creatureName", CREATURE_TABLE);
                if (dwbSpawnerRow > -1 && creatureRowNumber > -1)
                {
                    continueSpawnTool = true;
                }
            }
            if (continueSpawnTool)
            {
                obj_id testerInventoryId = utils.getInventoryContainer(self);
                obj_id[] testerInventoryContents = getContents(testerInventoryId);
                obj_id myBag = getObjectInSlot(self, "back");
                boolean contentsEmpty = false;
                boolean qaBagContentsEmpty = false;
                int iteration = 0;
                if (st.hasMoreTokens())
                {
                    iteration = Integer.parseInt(st.nextToken());
                }
                else 
                {
                    iteration = 25;
                }
                if (iteration > 100)
                {
                    iteration = 100;
                    sendSystemMessageTestingOnly(self, "You cannot spawn more than 100!!!");
                }
                if (iteration < 1)
                {
                    iteration = 1;
                    sendSystemMessageTestingOnly(self, "You cannot spawn less than 1!!!");
                }
                if (iteration <= 100)
                {
                    qa.findOrCreateAndEquipQABag(self, testerInventoryId, false);
                    myBag = getObjectInSlot(self, "back");
                    if (isIdValid(myBag))
                    {
                        int loopAmountInt = iteration / 10;
                        int remainderInt = iteration % 10;
                        dictionary incrementDict = new dictionary();
                        incrementDict.put("scriptString", scriptString);
                        incrementDict.put("myName", myName);
                        if (loopAmountInt >= 1)
                        {
                            for (int i = 0; i < loopAmountInt; i++)
                            {
                                try
                                {
                                    incrementDict.put("iterationInt", 10);
                                    messageTo(self, "runLootLoggerIncrement", incrementDict, 5, true);
                                }
                                catch(Exception e)
                                {
                                    sendSystemMessageTestingOnly(self, "Interrupted! " + e);
                                }
                            }
                        }
                        if (remainderInt >= 1)
                        {
                            incrementDict.put("iterationInt", remainderInt);
                            messageTo(self, "runLootLoggerIncrement", incrementDict, 5, true);
                        }
                        dictionary exportDataFile = new dictionary();
                        String tabFileCols = "THIS TOOL IS NOT 100% ACCURATE.\n\n";
                        tabFileCols += "Number of " + myName + "'s spawned was " + iteration + "\n";
                        tabFileCols += "#\tLoot Template String\tStatic Item String\n";
                        exportDataFile.put("exportData", tabFileCols);
                        exportDataFile.put("myBag", myBag);
                        messageTo(self, "qaSaveData", exportDataFile, 15, true);
                    }
                }
            }
            else 
            {
                if (scriptString.equals("none"))
                {
                    sendSystemMessageTestingOnly(self, "Invalid creature name. Please check your spelling and try again.");
                }
                sendSystemMessageTestingOnly(self, "Invalid dungeon creature name. Please make sure the creature you are spawning is spawned in the dungeon and also can be found in the creature table.");
            }
        }
        else 
        {
            sendSystemMessageTestingOnly(self, "Format: /qatool qaspawner <Creature Name> <number of iterations>");
            cleanAllScriptVars(self);
        }
    }
    public int lootLoggerMain(obj_id self, int iteration, String creatureSpawned, String scriptString) throws InterruptedException
    {
        int returnInt = 0;
        if (iteration > 0)
        {
            obj_id testerInventoryId = utils.getInventoryContainer(self);
            qa.findOrCreateAndEquipQABag(self, testerInventoryId, false);
            obj_id myBag = getObjectInSlot(self, "back");
            dictionary loggerDictionary = new dictionary();
            loggerDictionary.put("myBag", myBag);
            loggerDictionary.put("testerInventoryId", testerInventoryId);
            for (int i = 0; i < iteration; i++)
            {
                obj_id myMob = create.createCreature(creatureSpawned, getLocation(self), true);
                if (!scriptString.equals("none"))
                {
                    setObjVar(myMob, "mom", self);
                    setObjVar(myMob, "spawn_number", 1);
                    attachScript(myMob, scriptString);
                }
                int commandKillMobResult = commandKillMob(self, myMob);
                loggerDictionary.put("myMob", myMob);
                messageTo(self, "qaLootThem", loggerDictionary, 1, true);
                messageTo(self, "qaDestroyMob", loggerDictionary, 2, true);
                returnInt++;
            }
        }
        return returnInt;
    }
    public String[] retrieveMobileContents(obj_id self, String mobContentsByRow) throws InterruptedException
    {
        if (mobContentsByRow != null && !mobContentsByRow.equals(""))
        {
            String[] mobileContentsList = split(mobContentsByRow, '-');
            String[] returnedMoblileList = new String[mobileContentsList.length];
            if (mobileContentsList != null && mobileContentsList.length > 0)
            {
                for (int i = 0; i < mobileContentsList.length; i++)
                {
                    String tempString = mobileContentsList[i].replace('-', ' ');
                    returnedMoblileList[i] = tempString.trim();
                }
            }
            return returnedMoblileList;
        }
        return null;
    }
    public boolean exportTextBagContents(obj_id self, obj_id myBag, String exportString, String mobContentsByRow) throws InterruptedException
    {
        if (isIdValid(myBag))
        {
            obj_id[] qaBagInventoryContents = getContents(myBag);
            if (qaBagInventoryContents.length > 0)
            {
                for (int i = 0; i < qaBagInventoryContents.length; i++)
                {
                    if (exportString.equals(""))
                    {
                        exportString = "\tEquipped Backpack Contents.\n\n";
                        exportString += "#\tLoot Template String\tStatic Item String\tObject ID\n";
                    }
                    exportString += " " + (i + 1) + "\t" + getTemplateName(qaBagInventoryContents[i]) + "\t" + getStaticItemName(qaBagInventoryContents[i]) + "\t" + qaBagInventoryContents[i] + "\n";
                }
                if (mobContentsByRow != null && !mobContentsByRow.equals(""))
                {
                    String[] mobileContents = retrieveMobileContents(self, mobContentsByRow);
                    if (mobileContents != null && mobileContents.length > 0)
                    {
                        exportString += "\n\n\nContents of each mobile creature before looting.\n";
                        exportString += "#\t(OID)Item Template\n";
                        int u = 1;
                        String combinedString = "";
                        for (String mobileContent : mobileContents) {
                            if (mobileContent != null && !mobileContent.equals("")) {
                                String eachItemList[] = split(mobileContent, ',');
                                if (eachItemList != null && eachItemList.length > 0) {
                                    for (String s : eachItemList) {
                                        String contentsString = s.replace(',', ' ');
                                        contentsString = contentsString.trim();
                                        if (!contentsString.equals("") && contentsString != null) {
                                            combinedString += contentsString + ",";
                                            contentsString = "";
                                        }
                                    }
                                }
                            }
                            if (!combinedString.equals("")) {
                                exportString += " " + u + "\t" + combinedString + "\n";
                                u++;
                            }
                            combinedString = "";
                        }
                        u = 0;
                    }
                }
                saveTextOnClient(self, "qaDataExport" + getServerFrame() + ".tab", exportString);
                return true;
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "No items were looted!");
            }
        }
        return false;
    }
    public void makeHelper(obj_id self, StringTokenizer st) throws InterruptedException
    {
        if (st.hasMoreTokens())
        {
            String argumentString = st.nextToken();
            if (!argumentString.equals(""))
            {
                int creatureRowNumber = dataTableSearchColumnForString(argumentString, "creatureName", CREATURE_TABLE);
                if (creatureRowNumber > -1)
                {
                    obj_id helperMob = create.createCreature(argumentString, getLocation(self), true);
                    attachScript(helperMob, "test.qa_ai_helper_attach");
                    sendSystemMessageTestingOnly(self, "Helper Created.  Use radial menu.");
                    dictionary creatureRow = dataTableGetRow(CREATURE_TABLE, creatureRowNumber);
                    utils.setScriptVar(self, QAHELPER_SCRIPTVAR + ".creatureDictionary", creatureRow);
                }
                else 
                {
                    sendSystemMessageTestingOnly(self, "Creature name invalid.");
                }
            }
        }
    }
    public void toolWarpMenu(obj_id self) throws InterruptedException
    {
        obj_id[] waypointArray = qa.getAllValidWaypoints(self);
        String[] waypointMenu = qa.getMenuList(self, waypointArray, "waypoint menu");
        if (waypointMenu.length > 0)
        {
            location[] waypointWarpLocations = qa.getLocationList(self, waypointArray);
            utils.setScriptVar(self, "qadatapad.warpPoints", waypointWarpLocations);
            String[] combinedMenu = new String[waypointMenu.length + ADD_ON_DATAPAD_MENU.length];
            System.arraycopy(waypointMenu, 0, combinedMenu, 0, waypointMenu.length);
            System.arraycopy(ADD_ON_DATAPAD_MENU, 0, combinedMenu, waypointMenu.length, ADD_ON_DATAPAD_MENU.length);
            qa.refreshMenu(self, DATAPAD_TOOL_PROMPT, DATAPAD_TOOL_TITLE, combinedMenu, "handleWarpScriptOptions", "qadatapad.pid", "qadatapad.warpMenu", sui.OK_CANCEL_REFRESH);
        }
        else 
        {
            qa.refreshMenu(self, DATAPAD_TOOL_PROMPT, DATAPAD_TOOL_TITLE, ADD_ON_DATAPAD_MENU, "handleWarpScriptOptions", "qadatapad.pid", "qadatapad.warpMenu", sui.OK_CANCEL_REFRESH);
        }
    }
    public void objectFinderFunction(obj_id self, StringTokenizer st) throws InterruptedException
    {
        if (isSpaceScene())
        {
            sendSystemMessageTestingOnly(self, "You have to be on the ground to use this tool.");
        }
        else 
        {
            String scriptStrArg = "";
            if (st.hasMoreTokens())
            {
                int stringCount = st.countTokens();
                if (stringCount == 1)
                {
                    scriptStrArg = st.nextToken();
                    String searchType = "";
                    if (scriptStrArg.contains(".") && !scriptStrArg.contains(".iff"))
                    {
                        searchType = "script";
                    }
                    else if (scriptStrArg.contains("/") && scriptStrArg.contains(".iff"))
                    {
                        searchType = "template";
                    }
                    if (!searchType.equals(""))
                    {
                        listObjectsFound(self, scriptStrArg, searchType);
                    }
                    else 
                    {
                        sendSystemMessageTestingOnly(self, "The tool could not figure out what type of object you were searching for.  Check the string argument");
                        cleanAllScriptVars(self);
                    }
                }
                if (stringCount > 1)
                {
                    sendSystemMessageTestingOnly(self, "The tool only accepts one argument (a script string) without spaces.  Example: /qatool findobj npc.random_quest.quest_setup");
                    cleanAllScriptVars(self);
                }
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "The Tool requires you to specify the template of the object or the script attached to the object.");
                sendSystemMessageTestingOnly(self, "Example: /qatool findobj object/creature/player/human_male.iff");
                sendSystemMessageTestingOnly(self, "Example: /qatool findobj npc.random_quest.quest_setup");
                sendSystemMessageTestingOnly(self, "If successful, the tool will return a list of objects with the pattern specified by the tester.");
                cleanAllScriptVars(self);
            }
        }
    }
    public void listObjectsFound(obj_id self, String attributeString, String searchType) throws InterruptedException
    {
        if (attributeString.length() > 0)
        {
            if (searchType.equals("script"))
            {
                obj_id[] allScriptObjectsRadius = getAllObjectsWithScript(getLocation(self), 10000000.0f, attributeString);
                if (allScriptObjectsRadius.length > 0)
                {
                    String[] scriptObjMenu = getObjMenu(self, allScriptObjectsRadius);
                    String scriptDynamicPrompt = "Script searched for: " + attributeString + "\n";
                    scriptDynamicPrompt += "Found " + scriptObjMenu.length + " objects.\n\nSelect any object below to warp to it's location.";
                    qa.refreshMenu(self, scriptDynamicPrompt, OBJECT_FINDER_TITLE, scriptObjMenu, "objectFinderHandler", true, "objFinder.pid", "objFinder.objStrings");
                }
                else 
                {
                    sendSystemMessageTestingOnly(self, "No objects with that Script were found.");
                    cleanAllScriptVars(self);
                }
            }
            else if (searchType.equals("template"))
            {
                obj_id[] allTemplateObjectsRadius = getAllObjectsWithTemplate(getLocation(self), 10000000.0f, attributeString);
                if (allTemplateObjectsRadius.length > -1)
                {
                    String[] templateObjMenu = getObjMenu(self, allTemplateObjectsRadius);
                    String templateDynamicPrompt = "Template searched for: " + attributeString + "\n";
                    if (templateObjMenu.length >= 1)
                    {
                        templateDynamicPrompt += "Found " + templateObjMenu.length + " objects.\n\nSelect any object below to warp to it's location.";
                        qa.refreshMenu(self, templateDynamicPrompt, OBJECT_FINDER_TITLE, templateObjMenu, "objectFinderHandler", true, "objFinder.pid", "objFinder.objStrings");
                    }
                    else 
                    {
                        Vector convertOidToString = new Vector();
                        for (obj_id allTemplateObjectsRadius1 : allTemplateObjectsRadius) {
                            utils.addElement(convertOidToString, "" + allTemplateObjectsRadius1);
                        }
                        if (convertOidToString.size() >= 1)
                        {
                            String[] oidList = new String[convertOidToString.size()];
                            convertOidToString.toArray(oidList);
                            templateDynamicPrompt += "Found " + oidList.length + " objects.\nTEMPLATE LOOK UP FAILED! Showing OIDs only.\n\nSelect any OID below to warp to it's location.";
                            qa.refreshMenu(self, templateDynamicPrompt, OBJECT_FINDER_TITLE, oidList, "objectFinderHandler", true, "objFinder.pid", "objFinder.objStrings");
                        }
                    }
                }
                else 
                {
                    sendSystemMessageTestingOnly(self, "No objects with that Template were found. Make sure you include the entire object template to include the '.iff'.  Leave out the 'shared'.  If the tool still fails, try searching for the script attached to the object.");
                    cleanAllScriptVars(self);
                }
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "The tool did not know what type of object you were searching for.  Inform the tool team.");
                cleanAllScriptVars(self);
            }
        }
    }
    public String[] getObjMenu(obj_id self, obj_id[] allObjOIDs) throws InterruptedException
    {
        if (allObjOIDs.length > 0)
        {
            Vector objMenu = new Vector();
            objMenu.setSize(0);
            for (obj_id allObjOID : allObjOIDs) {
                String encodeString = getEncodedName(allObjOID);
                if (encodeString.length() > 0) {
                    utils.addElement(objMenu, encodeString + "  ( " + allObjOID + " )");
                }
            }
            if (objMenu.size() > -1)
            {
                String[] arrayStrings = new String[objMenu.size()];
                objMenu.toArray(arrayStrings);
                Arrays.sort(arrayStrings);
                return arrayStrings;
            }
        }
        else 
        {
            sendSystemMessageTestingOnly(self, "The function didn't receive a list of objects.  Make sure the spelling of the template is correct.  Report problems to the tool team.");
            cleanAllScriptVars(self);
        }
        return null;
    }
    public boolean specTester(obj_id self, StringTokenizer st) throws InterruptedException
    {
        if (isSpaceScene())
        {
            sendSystemMessageTestingOnly(self, "You have to be on the ground to use this tool.");
        }
        else 
        {
            String professionStr = "";
            int levelInt = 1;
            int testerLevel = 0;
            String factionStr = "neu";
            String armorStr = "none";
            String weaponStr = "none";
            obj_id testerInventoryId = utils.getInventoryContainer(self);
            if (st.hasMoreTokens())
            {
                professionStr = st.nextToken();
                if (professionStr.equals("?"))
                {
                    sui.msgbox(self, self, SPECMEPOPUP, sui.OK_ONLY, QATOOL_TITLE, "noHandler");
                    return true;
                }
                else 
                {
                    int stringCount = st.countTokens();
                    if (stringCount > 0 && stringCount <= 4)
                    {
                        if (!professionStr.equals(""))
                        {
                            try
                            {
                                levelInt = Integer.parseInt(st.nextToken());
                            }
                            catch(Exception e)
                            {
                                sendSystemMessageTestingOnly(self, "You failed to specify a profession level.");
                                return false;
                            }
                            if (levelInt > 90)
                            {
                                sendSystemMessageTestingOnly(self, "90th Level is max.");
                                return false;
                            }
                            String[] roadmapList = gm.getRoadmapList();
                            String professionCodeString = validateProfession(self, professionStr, roadmapList);
                            if (!professionCodeString.equals("ERROR"))
                            {
                                int currentCombatXp = getExperiencePoints(self, "combat_general");
                                grantExperiencePoints(self, "combat_general", -currentCombatXp);
                                skill.recalcPlayerPools(self, true);
                                qa.revokeAllSkills(self);
                                String[] professionAndNoviceSkill = split(professionCodeString, '-');
                                setSkillTemplate(self, professionAndNoviceSkill[0]);
                                String professionPrefix = professionAndNoviceSkill[1];
                                for (int i = 0; i < 24; i++)
                                {
                                    testerLevel = getLevel(self);
                                    if (levelInt > testerLevel)
                                    {
                                        skill.grantSkillToPlayer(self, professionPrefix + PROFESSION_SKILL_NAMES[i]);
                                    }
                                    else 
                                    {
                                        break;
                                    }
                                }
                                if (st.hasMoreTokens())
                                {
                                    factionStr = st.nextToken();
                                    if (!factionStr.equals("none"))
                                    {
                                        boolean factionSuccess = attainCorrectFaction(self, factionStr);
                                        if (!factionSuccess)
                                        {
                                            return false;
                                        }
                                        boolean factionStatusSuccess = attainCorrectFactionStatus(self, factionStr);
                                        if (!factionStatusSuccess)
                                        {
                                            return false;
                                        }
                                    }
                                    if (st.hasMoreTokens())
                                    {
                                        armorStr = st.nextToken();
                                        if (!armorStr.equals("none"))
                                        {
                                            boolean miscItem = false;
                                            int miscIndex = -1;
                                            for (int i = 0; i < MISC_SEARCH_MULTIARRAY[0].length; i++)
                                            {
                                                if (MISC_SEARCH_MULTIARRAY[0][i].indexOf(armorStr) == 0)
                                                {
                                                    miscItem = true;
                                                    miscIndex = i;
                                                }
                                            }
                                            if (!miscItem)
                                            {
                                                boolean allArmorSpawned = spawnItems(self, armorStr, ARMOR_SEARCH_MULTIARRAY, ARMOR_STATS_TABLE);
                                                if (allArmorSpawned)
                                                {
                                                    sendSystemMessageTestingOnly(self, "Armor spawned without error.");
                                                    qa.findOrCreateAndEquipQABag(self, testerInventoryId, true);
                                                }
                                            }
                                            else 
                                            {
                                                boolean miscItemSpawned = spawnItems(self, armorStr, MISC_SEARCH_MULTIARRAY, MASTER_ITEM_TABLE);
                                                if (miscItemSpawned)
                                                {
                                                    sendSystemMessageTestingOnly(self, "Items spawned without error.");
                                                    qa.findOrCreateAndEquipQABag(self, testerInventoryId, true);
                                                }
                                                else 
                                                {
                                                    return false;
                                                }
                                            }
                                        }
                                    }
                                    if (st.hasMoreTokens())
                                    {
                                        weaponStr = st.nextToken();
                                        if (!weaponStr.equals("none"))
                                        {
                                            boolean allWeaponsSpawned = spawnItems(self, weaponStr, WEAPON_SEARCH_MULTIARRAY, WEAPON_STATS_TABLE);
                                            if (allWeaponsSpawned)
                                            {
                                                sendSystemMessageTestingOnly(self, "Weapons spawned without error.");
                                                qa.findOrCreateAndEquipQABag(self, testerInventoryId, true);
                                                return true;
                                            }
                                        }
                                    }
                                    else 
                                    {
                                        return true;
                                    }
                                    if (armorStr.equals("none") && weaponStr.equals("none"))
                                    {
                                        return true;
                                    }
                                }
                                else 
                                {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        sendSystemMessageTestingOnly(self, "***There was a problem trying to implement all tool arguments***");
        return false;
    }
    public boolean spawnItems(obj_id self, String searchString, String[][] arrayOfArrays, String datatableName) throws InterruptedException
    {
        if (!searchString.equals(""))
        {
            String searchSpawnString = "";
            for (int m = 0; m < arrayOfArrays[0].length; m++)
            {
                if (arrayOfArrays[0][m].indexOf(searchString) == 0)
                {
                    searchSpawnString = arrayOfArrays[1][m];
                }
            }
            if (!searchSpawnString.equals(""))
            {
                Vector itemRowList = new Vector();
                Vector itemSpawnStringList = new Vector();
                String[] itemSpawnStrListArray = dataTableGetStringColumn(datatableName, "name");
                if (itemSpawnStrListArray.length > 0)
                {
                    for (int x = 0; x < itemSpawnStrListArray.length; x++)
                    {
                        if (itemSpawnStrListArray[x].indexOf(searchSpawnString) == 0)
                        {
                            utils.addElement(itemRowList, "" + x);
                        }
                    }
                    if (itemRowList.size() > -1)
                    {
                        String[] allItemRows = new String[itemRowList.size()];
                        itemRowList.toArray(allItemRows);
                        String itemString = "";
                        for (String allItemRow : allItemRows) {
                            itemString = dataTableGetString(datatableName, utils.stringToInt(allItemRow), "name");
                            utils.addElement(itemSpawnStringList, itemString);
                        }
                        if (itemSpawnStringList.size() > -1)
                        {
                            String[] allStrings = new String[itemSpawnStringList.size()];
                            itemSpawnStringList.toArray(allStrings);
                            if (allStrings.length > 0)
                            {
                                for (String allString : allStrings) {
                                    qa.spawnStaticItemInInventory(self, allString, "none");
                                }
                            }
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    public boolean attainCorrectFactionStatus(obj_id self, String factionStr) throws InterruptedException
    {
        if (!factionStr.equals(""))
        {
            if (factionStr.indexOf("sf") == 3)
            {
                if (factions.isRebel(self) || factions.isImperial(self))
                {
                    setObjVar(self, "intChangingFactionStatus", 1);
                    factions.goOvert(self);
                    return true;
                }
            }
            else if (factionStr.indexOf("cm") == 3)
            {
                if (factions.isRebel(self) || factions.isImperial(self))
                {
                    setObjVar(self, "intChangingFactionStatus", 1);
                    factions.goCovert(self);
                    return true;
                }
            }
            else if (factionStr.indexOf("ol") == 3)
            {
                if (factions.isRebel(self) || factions.isImperial(self))
                {
                    setObjVar(self, "intChangingFactionStatus", 1);
                    factions.goOnLeave(self);
                    return true;
                }
            }
            else 
            {
                if (factions.isNeutral(self))
                {
                    sendSystemMessageTestingOnly(self, "Neutral");
                    return true;
                }
                sendSystemMessageTestingOnly(self, "Failure Neutral");
            }
        }
        return false;
    }
    public boolean attainCorrectFaction(obj_id self, String factionStr) throws InterruptedException
    {
        if (!factionStr.equals(""))
        {
            String fullFactionName = "";
            if (factionStr.indexOf("reb") == 0)
            {
                fullFactionName = factions.FACTION_REBEL;
            }
            else if (factionStr.indexOf("imp") == 0)
            {
                fullFactionName = factions.FACTION_IMPERIAL;
            }
            else if (factionStr.indexOf("neu") == 0)
            {
                fullFactionName = factions.FACTION_NEUTRAL;
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "Unknown faction. Setting faction to Neutral.");
                fullFactionName = factions.FACTION_NEUTRAL;
            }
            if (!fullFactionName.equals(""))
            {
                int factionNum = factions.getFactionNumber(fullFactionName);
                int factionHashCode = dataTableGetInt(factions.FACTION_TABLE, factionNum, "pvpFaction");
                pvpSetAlignedFaction(self, factionHashCode);
                return true;
            }
        }
        return false;
    }
    public String validateProfession(obj_id self, String professionStr, String[] roadmapList) throws InterruptedException
    {
        if (!professionStr.equals("") && roadmapList.length > 0)
        {
            String professionCodeStr = "";
            professionStr = toLower(professionStr);
            if (professionStr.contains("smu"))
            {
                professionCodeStr = roadmapList[0] + "-" + PROFESSION_PREFIX[0];
            }
            else if (professionStr.contains("bou") || professionStr.equals("bh"))
            {
                professionCodeStr = roadmapList[1] + "-" + PROFESSION_PREFIX[1];
            }
            else if (professionStr.contains("off"))
            {
                professionCodeStr = roadmapList[2] + "-" + PROFESSION_PREFIX[2];
            }
            else if (professionStr.contains("com"))
            {
                professionCodeStr = roadmapList[3] + "-" + PROFESSION_PREFIX[3];
            }
            else if (professionStr.contains("jed") || professionStr.contains("for"))
            {
                professionCodeStr = roadmapList[4] + "-" + PROFESSION_PREFIX[4];
            }
            else if (professionStr.contains("med"))
            {
                professionCodeStr = roadmapList[5] + "-" + PROFESSION_PREFIX[5];
            }
            else if (professionStr.contains("spy"))
            {
                professionCodeStr = roadmapList[6] + "-" + PROFESSION_PREFIX[6];
            }
            else 
            {
                professionCodeStr = "ERROR";
            }
            if (!professionCodeStr.equals(""))
            {
                return professionCodeStr;
            }
            else 
            {
                professionCodeStr = "ERROR";
            }
        }
        return "ERROR";
    }
    public boolean earnProfessionLevel(obj_id self) throws InterruptedException
    {
        String skillName = getWorkingSkill(self);
        dictionary xpReqs = getSkillPrerequisiteExperience(skillName);
        if (xpReqs == null || xpReqs.isEmpty())
        {
            sendSystemMessageTestingOnly(self, "Current working skill is invalid.");
        }
        java.util.Enumeration e = xpReqs.keys();
        String xpType = (String)(e.nextElement());
        int xpCost = xpReqs.getInt(xpType);
        int curXP = getExperiencePoints(self, xpType);
        if (curXP < xpCost)
        {
            grantExperiencePoints(self, xpType, xpCost - curXP);
        }
        skill_template.earnWorkingSkill(self);
        return true;
    }
    public boolean mulipleStaticSpawn(obj_id self, String spawnString, String intNumber, boolean silent) throws InterruptedException
    {
        if (!spawnString.equals("") && !intNumber.equals(""))
        {
            obj_id inv = utils.getInventoryContainer(self);
            if (isIdValid(inv))
            {
                obj_id[] objContents = utils.getContents(inv, true);
                if (objContents.length >= 80)
                {
                    sendSystemMessageTestingOnly(self, "Empty your inventory before trying to spawn more items");
                }
                else 
                {
                    int loopInt = utils.stringToInt(intNumber);
                    if (loopInt != -1)
                    {
                        obj_id itemSpawned = qa.spawnStaticItemInInventory(self, spawnString, "none", true);
                        if (isIdNull(itemSpawned))
                        {
                            sendSystemMessageTestingOnly(self, "Bad Spawn String.");
                            return false;
                        }
                        else 
                        {
                            for (int x = 0; x < loopInt - 1; x++)
                            {
                                static_item.createNewItemFunction(spawnString, inv);
                            }
                        }
                    }
                    else 
                    {
                        sendSystemMessageTestingOnly(self, "The tool requires the following usage to function: /qatool mutli <static_item_spawn_string> <integer> ");
                        return false;
                    }
                }
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "inventory id failed.");
                return false;
            }
        }
        else 
        {
            sendSystemMessageTestingOnly(self, "The tool requires the following usage to function: /qatool mutli <static_item_spawn_string> <integer> ");
            return false;
        }
        if (!silent)
        {
            sendSystemMessageTestingOnly(self, "Done.");
        }
        return true;
    }
    public void getAllSkillMods(obj_id self) throws InterruptedException
    {
        String[] allSkillModsList = dataTableGetStringColumn(SKILL_MOD_TABLE, "skill_mod");
        if (allSkillModsList.length > -1)
        {
            String combinedString = "This is a current list of all valid combat profession skill mods in SWG:\n\n";
            for (String s : allSkillModsList) {
                combinedString += localize(new string_id("stat_n", s)) + "\t\t\t" + s + "\n";
            }
            if (!combinedString.equals(""))
            {
                sui.msgbox(self, self, combinedString, sui.OK_ONLY, "Skill Mods", "noHandler");
            }
        }
    }
    public void saveAllCurrentQuestData(obj_id self) throws InterruptedException
    {
        try
        {
            String allQuestData = "";
            String[] allQuests = qa.getAllQuests(self);
            if (allQuests.length > 0)
            {
                for (String allQuest : allQuests) {
                    allQuestData += allQuest + "\r";
                }
                saveTextOnClient(self, "questData.txt", allQuestData);
            }
        }
        catch(Exception e)
        {
            sendSystemMessageTestingOnly(self, "Unable to dump quest data due to internal exception or because the test character has no quests.");
        }
    }
    public void setWeather(obj_id self, int weatherSelected) throws InterruptedException
    {
        setWeatherData(weatherSelected, 0.01f, 0.01f);
        messageTo(self, "animateForceDestruction", null, 9.0f, true);
    }
    public void getNamesArray(obj_id player) throws InterruptedException
    {
        String[] codeStringArray = dataTableGetStringColumn(CUBE_DATATABLE_1, "finalTemplate");
        String[] showNamesArray = new String[codeStringArray.length];
        for (int i = 0; i < codeStringArray.length; i++)
        {
            if (codeStringArray[i].endsWith(".iff"))
            {
                int idxSlash = codeStringArray[i].lastIndexOf("/") + 1;
                int idxPeriod = codeStringArray[i].lastIndexOf(".");
                String lookUp = codeStringArray[i].substring(idxSlash, idxPeriod) + "_n";
                showNamesArray[i] = localize(new string_id("som/som_item", lookUp));
            }
            else 
            {
                showNamesArray[i] = localize(new string_id("static_item_n", codeStringArray[i]));
            }
            showNamesArray[i] = showNamesArray[i] + ": (" + codeStringArray[i] + ")";
        }
        utils.setScriptVar(player, "qa_cube.codeStringArray", codeStringArray);
        utils.setScriptVar(player, "qa_cube.showNamesArray", showNamesArray);
    }
    public void emailSpamFunction(obj_id self, StringTokenizer st) throws InterruptedException
    {
        int spamAmount = 0;
        if (st.hasMoreTokens())
        {
            try
            {
                spamAmount = Integer.parseInt(st.nextToken());
            }
            catch(Exception e)
            {
                sendSystemMessageTestingOnly(self, "You failed to specify the amount of spam.");
            }
            if (spamAmount > 0)
            {
                for (int i = 0; i < spamAmount; i++)
                {
                    utils.sendMail(NEW_CITY_STRUCTURE_SUBJECT, NEW_CITY_STRUCTURE_BODY, self, getName(self));
                }
                sendSystemMessageTestingOnly(self, "Spamming completed.");
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "You failed to specify the amount of spam.");
            }
        }
        else 
        {
            sendSystemMessageTestingOnly(self, "You failed to specify the amount of spam.");
        }
    }
    public void checkMobContents(obj_id self, obj_id mobile) throws InterruptedException
    {
        String previousMobileContents = "";
        if (utils.hasScriptVar(self, "lootLogger.mobContents"))
        {
            previousMobileContents = utils.getStringScriptVar(self, "lootLogger.mobContents");
        }
        else 
        {
            utils.setScriptVar(self, "lootLogger.mobContents", "");
        }
        if (isValidId(mobile))
        {
            obj_id mobileInv = utils.getInventoryContainer(mobile);
            obj_id contents[] = getContents(mobileInv);
            String mobileContentsExport = "";
            if (contents.length > 0)
            {
                for (obj_id content : contents) {
                    mobileContentsExport += "(" + content + ")" + getTemplateName(content) + ",";
                }
                String combinedContentsString = previousMobileContents + "-" + mobileContentsExport;
                utils.setScriptVar(self, "lootLogger.mobContents", combinedContentsString);
            }
        }
    }
    public void qaFishing(obj_id self, StringTokenizer st, String scriptString) throws InterruptedException
    {
        String myName = null;
        boolean continueFishing = true;
        location here = getLocation(self);
        if (!minigame.isLocationFishable(here))
        {
            sendSystemMessageTestingOnly(self, "You must be within 2 meters of fishable water and facing it to start fishing.");
            return;
        }
        if (continueFishing)
        {
            int iteration = 0;
            if (st.hasMoreTokens())
            {
                iteration = Integer.parseInt(st.nextToken());
            }
            else 
            {
                iteration = 1;
            }
            if (iteration > 100)
            {
                iteration = 100;
                sendSystemMessageTestingOnly(self, "Catch limit = 1 to 100");
            }
            if (iteration < 1)
            {
                iteration = 1;
                sendSystemMessageTestingOnly(self, "Catch limit = 1 to 100");
            }
            if (iteration <= 100)
            {
                for (int i = 0; i < iteration; i++)
                {
                    obj_id myCatch = minigame.spawnFishingFish(self, here);
                }
            }
        }
    }
    public void qaEnableCollectionClickBypass(obj_id self, StringTokenizer st, String scriptString) throws InterruptedException
    {
        utils.setScriptVar(self, "collection.qa.clickBypass", 0);
        sendSystemMessageTestingOnly(self, "Collection Click Bypass Enabled. Logout to reset to normal collecting.");
    }
    public int qaSetScriptVar(obj_id self, StringTokenizer st) throws InterruptedException
    {
        if (st.hasMoreTokens())
        {
            String scriptVarName = st.nextToken();
            String scriptVarValue = "0";
            if (st.hasMoreTokens())
            {
                scriptVarValue = st.nextToken();
            }
            obj_id target = qaGetTarget(self);
            if (isIdValid(target))
            {
                target = self;
            }
            utils.setScriptVar(target, scriptVarName, utils.stringToInt(scriptVarValue));
            debugSpeakMsg(self, "Scriptvar " + scriptVarName + " = " + scriptVarValue + " set on OID " + target);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            debugSpeakMsg(self, "parameters are updateScriptvar <scriptVarName> <scriptVarValue>");
        }
        return SCRIPT_CONTINUE;
    }
    public void qaExportBackpackContents(obj_id self) throws InterruptedException
    {
        obj_id testerInventoryId = utils.getInventoryContainer(self);
        obj_id[] testerInventoryContents = getContents(testerInventoryId);
        obj_id myBag = getObjectInSlot(self, "back");
        if (isIdValid(myBag))
        {
            dictionary exportDataFile = new dictionary();
            exportDataFile.put("exportData", "");
            exportDataFile.put("myBag", myBag);
            messageTo(self, "qaSaveData", exportDataFile, 15, true);
        }
        else 
        {
            sendSystemMessageTestingOnly(self, "Invalid backpack or no backpack equipped");
        }
    }
    public int commandKill(obj_id self, obj_id target) throws InterruptedException
    {
        if (!isIdValid(self) || !isIdValid(target) || !exists(target))
        {
            sendSystemMessageTestingOnly(self, "Kill command failed.");
            return SCRIPT_CONTINUE;
        }
        obj_id objMyShip = space_transition.getContainingShip(self);
        if (space_utils.isShip(objMyShip))
        {
            int commandKillShipResult = commandKillShip(self, target);
            return SCRIPT_CONTINUE;
        }
        else if (target == self)
        {
            sendSystemMessageTestingOnly(self, "Kill Command Failed. Cannot Kill self.  Use /qatool suicide.");
            return SCRIPT_CONTINUE;
        }
        else if (isPlayer(target))
        {
            setPosture(target, POSTURE_INCAPACITATED);
            pclib.coupDeGrace(self, self, true, true);
            CustomerServiceLog("qaTool", "User: (" + self + ") " + getName(self) + " has killed (" + target + ") " + utils.getStringName(target) + " using the kill command.");
            return SCRIPT_CONTINUE;
        }
        else if (isMob(target))
        {
            if (isIncapacitated(target))
            {
                sendSystemMessageTestingOnly(self, "Kill Command. Target is already dead.");
                return SCRIPT_CONTINUE;
            }
            int commandKillMobResult = commandKillMob(self, target);
            CustomerServiceLog("qaTool", "User: (" + self + ") " + getName(self) + " has killed (" + target + ") " + utils.getStringName(target) + " using the kill command.");
            return SCRIPT_CONTINUE;
        }
        else 
        {
            sendSystemMessageTestingOnly(self, "you must have a valid player or mobile target to use this command");
            return SCRIPT_CONTINUE;
        }
    }
    public int commandKillShip(obj_id self, obj_id target) throws InterruptedException
    {
        if (!isIdValid(self) || !isIdValid(target) || !exists(target))
        {
            sendSystemMessageTestingOnly(self, "Kill ship command failed.");
            return SCRIPT_CONTINUE;
        }
        obj_id objMyShip = space_transition.getContainingShip(self);
        if (!isIdValid(target) || !isIdValid(objMyShip))
        {
            sendSystemMessageTestingOnly(self, "You must be in space and target a ship to destroy it");
            return SCRIPT_CONTINUE;
        }
        if (target == self)
        {
            sendSystemMessageTestingOnly(self, "DO NOT DESTROY YOURSELF");
            return SCRIPT_CONTINUE;
        }
        if (target == objMyShip)
        {
            sendSystemMessageTestingOnly(self, "DO NOT DESTROY YOURSELF");
            return SCRIPT_CONTINUE;
        }
        notifyShipDamage(target, objMyShip, 10.0f);
        space_combat.doChassisDamage(objMyShip, target, 0, 1.0f);
        setShipCurrentChassisHitPoints(target, 0.0f);
        utils.setLocalVar(target, "space.give_rewards", 1);
        space_combat.targetDestroyed(target);
        space_combat.setDeathFlags(target);
        float fltIntensity = rand(0, 1.0f);
        handleShipDestruction(target, fltIntensity);
        space_combat.doDeathCleanup(target);
        return SCRIPT_CONTINUE;
    }
    public int commandKillMob(obj_id self, obj_id target) throws InterruptedException
    {
        if (!isIdValid(self) || !isIdValid(target) || !exists(target))
        {
            sendSystemMessageTestingOnly(self, "Kill command failed.");
            return SCRIPT_CONTINUE;
        }
        final String ATTACK_TYPE = "combat_rangedspecialize_pistol";
        int damage = getMaxHealth(target) + 1;
        Vector attackerList = utils.getResizeableObjIdBatchScriptVar(target, "creditForKills.attackerList.attackers");
        attackerList = utils.addElement(attackerList, self);
        utils.setBatchScriptVar(target, "creditForKills.attackerList.attackers", attackerList);
        utils.setScriptVar(target, "creditForKills.attackerList." + self + ".damage", damage);
        utils.setScriptVar(target, "creditForKills.damageCount", 100);
        utils.setScriptVar(target, "creditForKills.damageTally", damage);
        Vector types = utils.getResizeableObjIdBatchScriptVar(target, "creditForKills.attackerList." + self + ".xp.types");
        types = utils.addElement(types, ATTACK_TYPE);
        utils.setBatchScriptVar(target, "creditForKills.attackerList." + self + ".xp.types", types);
        utils.setScriptVar(target, "creditForKills.attackerList." + self + ".xp." + ATTACK_TYPE, damage);
        setHealth(target, -5000);
        return SCRIPT_CONTINUE;
    }
    public obj_id qaGetTarget(obj_id self, String override) throws InterruptedException
    {
        obj_id lookAtTarget = getLookAtTarget(self);
        obj_id intendedTarget = getIntendedTarget(self);
        obj_id target = null;
        if (isIdValid(lookAtTarget))
        {
            if (isIdValid(intendedTarget))
            {
                if (override == null)
                {
                    target = intendedTarget;
                }
                else 
                {
                    target = lookAtTarget;
                }
            }
            else 
            {
                target = lookAtTarget;
            }
        }
        else 
        {
            target = intendedTarget;
        }
        return target;
    }
    public obj_id qaGetTarget(obj_id self) throws InterruptedException
    {
        obj_id target = qaGetTarget(self, null);
        return target;
    }
    public boolean createEnzyme(obj_id self, StringTokenizer st) throws InterruptedException
    {
        if (isSpaceScene())
        {
            sendSystemMessageTestingOnly(self, "You have to be on the ground to use this tool.");
        }
        else 
        {
            String mutagenStr = "";
            String purityStr = "";
            int mutagen = 1;
            int purity = 1;
            obj_id testerInventoryId = utils.getInventoryContainer(self);
            if (st.hasMoreTokens())
            {
                mutagenStr = st.nextToken();
                if (mutagenStr.equals("?"))
                {
                    return true;
                }
                else 
                {
                    if (!mutagenStr.equals(""))
                    {
                        mutagen = Integer.parseInt(mutagenStr);
                        if (mutagen > 20 || mutagen < 1)
                        {
                            sendSystemMessageTestingOnly(self, "The mutagen value must be between 1 and 20.");
                            return false;
                        }
                    }
                    if (st.hasMoreTokens())
                    {
                        purityStr = st.nextToken();
                        if (purityStr.equals("?"))
                        {
                            return true;
                        }
                        else 
                        {
                            if (!purityStr.equals(""))
                            {
                                purity = Integer.parseInt(purityStr);
                                if (purity > 20 || purity < 1)
                                {
                                    sendSystemMessageTestingOnly(self, "The purity value must be between 1 and 20.");
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
            float mutagenFloat = mutagen;
            float purityFloat = purity;
            obj_id enzyme;
            if (getVolumeFree(testerInventoryId) <= 0)
            {
                sendSystemMessage(self, "Failed to create Enzyme, please make sure that your inventory is not full.", null);
                return false;
            }
            enzyme = createObject("object/tangible/loot/beast/enzyme_3.iff", testerInventoryId, "");
            if (isValidId(enzyme))
            {
                setObjVar(enzyme, "enzyme.enzyme_mutagen", mutagenFloat);
                setObjVar(enzyme, "enzyme.enzyme_purity", purityFloat);
                sendSystemMessageTestingOnly(self, "Created an Enzyme with the values: Mutagen: " + mutagen + " and Purity: " + purity);
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "Failed to create Enzyme, please make sure that your inventory is not full.");
            }
        }
        return true;
    }
    public boolean createLyase(obj_id self) throws InterruptedException
    {
        if (isSpaceScene())
        {
            sendSystemMessageTestingOnly(self, "You have to be on the ground to use this tool.");
        }
        else 
        {
            int randomStat = 11;
            obj_id testerInventoryId = utils.getInventoryContainer(self);
            obj_id lyase;
            if (getVolumeFree(testerInventoryId) <= 0)
            {
                sendSystemMessage(self, "Failed to create Enzyme, please make sure that your inventory is not full.", null);
                return false;
            }
            lyase = createObject("object/tangible/loot/beast/enzyme_2.iff", testerInventoryId, "");
            if (isValidId(lyase))
            {
                dictionary params = new dictionary();
                params.put("lyase", lyase);
                messageTo(self, "makeLyase", params, 2, false);
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "Failed to create Enzyme, please make sure that your inventory is not full.");
            }
        }
        return true;
    }
    public int makeLyase(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id lyaseObject = params.getObjId("lyase");
        setObjVar(lyaseObject, "beast.enzyme.randomStats", 11);
        if (hasObjVar(lyaseObject, "beast.enzyme.freeStatName"))
        {
            removeObjVar(lyaseObject, "beast.enzyme.freeStatName");
        }
        sendSystemMessageTestingOnly(self, "Created a Lyase Enzyme with the values: random Stats: 11");
        return SCRIPT_CONTINUE;
    }
    public boolean clearHeroicTimer(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "instance_player_data"))
        {
            removeObjVar(self, "instance_player_data");
            sendSystemMessageTestingOnly(self, "Removed instance_player_data from: " + self + " - Heroic timers reset");
            return true;
        }
        else 
        {
            sendSystemMessageTestingOnly(self, "QA tool could not find the objvar instance_player_data on the player: " + self);
        }
        return false;
    }
    public boolean createIsomerase(obj_id self) throws InterruptedException
    {
        if (isSpaceScene())
        {
            sendSystemMessageTestingOnly(self, "You have to be on the ground to use this tool.");
        }
        else 
        {
            obj_id testerInventoryId = utils.getInventoryContainer(self);
            obj_id isomerase;
            if (getVolumeFree(testerInventoryId) <= 0)
            {
                sendSystemMessage(self, "Failed to create Enzyme, please make sure that your inventory is not full.", null);
                return false;
            }
            isomerase = createObject("object/tangible/loot/beast/enzyme_1.iff", testerInventoryId, "");
            if (isValidId(isomerase))
            {
                dictionary params = new dictionary();
                params.put("iso", isomerase);
                messageTo(self, "makeIso", params, 2, false);
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "Failed to create Enzyme, please make sure that your inventory is not full.");
            }
        }
        return true;
    }
    public int makeIso(obj_id self, dictionary params) throws InterruptedException
    {
        float quality = 90;
        obj_id isoObject = params.getObjId("iso");
        setObjVar(isoObject, "beast.enzyme.quality", quality);
        sendSystemMessageTestingOnly(self, "Created a Isomerase Enzyme with the values: random Stats: 90%");
        return SCRIPT_CONTINUE;
    }
    public void qaTCGMenu(obj_id self) throws InterruptedException
    {
        if (!isIdValid(self) || !exists(self) || !isGod(self))
        {
            return;
        }
        qa.refreshMenu(self, "Select...", "-SWG TCG Menu-", TCG_MENU, "handleTCGMenu", "tcg_menu", "tcg_menu", sui.OK_CANCEL_REFRESH);
    }
    public int handleTCGMenu(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isGod(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        int idx = sui.getListboxSelectedRow(params);
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            cleanAllScriptVars(self);
            return SCRIPT_CONTINUE;
        }
        switch (idx)
        {
            case 0:
            scheduled_drop.dropCard(scheduled_drop.SYSTEM_COMBAT_NORMAL, self);
            sendSystemMessageTestingOnly(self, "Item placed in inventory.");
            qaTCGMenu(self);
            break;
            case 1:
            if (!hasObjVar(self, "qa_tcg"))
            {
                setObjVar(self, "qa_tcg", 1);
                sendSystemMessageTestingOnly(self, "TCG Info flag has been set on your character.");
                qa.refreshMenu(self, "Select...", "-SWG TCG Menu-", TCG_MENU, "handleTCGMenu", "tcg_menu", "tcg_menu", sui.OK_CANCEL_REFRESH);
                return SCRIPT_CONTINUE;
            }
            else 
            {
                removeObjVar(self, "qa_tcg");
                sendSystemMessageTestingOnly(self, "TCG Info flag has been removed from your character.");
                qa.refreshMenu(self, "Select...", "-SWG TCG Menu-", TCG_MENU, "handleTCGMenu", "tcg_menu", "tcg_menu", sui.OK_CANCEL_REFRESH);
                return SCRIPT_CONTINUE;
            }
            case 2:
            if (!hasObjVar(self, "qa_tcg_always_drop"))
            {
                setObjVar(self, "qa_tcg_always_drop", 1);
                sendSystemMessageTestingOnly(self, "TCG always drop flag has been set on your character.");
                qa.refreshMenu(self, "Select...", "-SWG TCG Menu-", TCG_MENU, "handleTCGMenu", "tcg_menu", "tcg_menu", sui.OK_CANCEL_REFRESH);
                return SCRIPT_CONTINUE;
            }
            else 
            {
                removeObjVar(self, "qa_tcg_always_drop");
                sendSystemMessageTestingOnly(self, "TCG always drop flag has been removed from your character.");
                qa.refreshMenu(self, "Select...", "-SWG TCG Menu-", TCG_MENU, "handleTCGMenu", "tcg_menu", "tcg_menu", sui.OK_CANCEL_REFRESH);
                return SCRIPT_CONTINUE;
            }
            case 3:
            qaTCGCardClusterInfo(self);
            break;
            case 4:
            qaTCGBoosterClusterInfo(self);
            break;
            case 5:
            qaTCGReinit(self);
            break;
            default:
            break;
        }
        return SCRIPT_CONTINUE;
    }
    public String[] tcgClusterInfo(String type) throws InterruptedException
    {
        String[] scheduledPromotions = scheduled_drop.getScheduledPromotions(type);
        if (scheduledPromotions == null || scheduledPromotions.length <= 0)
        {
            return null;
        }
        String[] promotions = scheduled_drop.validatePromotionsVersusCluster(scheduledPromotions);
        if (promotions == null || promotions.length <= 0)
        {
            return null;
        }
        int[] cardsLeft = new int[promotions.length];
        String[] listBoxInfo = new String[promotions.length * 2];
        obj_id tatooine = getPlanetByName("tatooine");
        for (int i = 0, j = promotions.length; i < j; i++)
        {
            cardsLeft[i] = getIntObjVar(tatooine, "tcg." + promotions[i] + ".count");
            listBoxInfo[i * 2] = promotions[i];
            if (cardsLeft[i] == -1)
            {
                listBoxInfo[(i * 2) + 1] = "Items Left: Infinite";
            }
            else 
            {
                int maximumDrops = scheduled_drop.getPromotionMaxDrop(promotions[i]);
                listBoxInfo[(i * 2) + 1] = "Maximum drops: " + maximumDrops + " Items Left: " + cardsLeft[i];
            }
        }
        return listBoxInfo;
    }
    public void qaTCGCardClusterInfo(obj_id self) throws InterruptedException
    {
        String[] listBoxInfo = tcgClusterInfo("card");
        if (listBoxInfo == null || listBoxInfo.length <= 0)
        {
            sendSystemMessageTestingOnly(self, "No promotions running.");
            return;
        }
        qa.refreshMenu(self, "Select...", "-SWG Card Promotions-", listBoxInfo, "handleTCGPromotionMenu", "tcg_menu", "tcg_menu", sui.OK_CANCEL_REFRESH);
    }
    public void qaTCGBoosterClusterInfo(obj_id self) throws InterruptedException
    {
        String[] listBoxInfo = tcgClusterInfo("item");
        if (listBoxInfo == null || listBoxInfo.length <= 0)
        {
            sendSystemMessageTestingOnly(self, "No promotions running.");
            return;
        }
        qa.refreshMenu(self, "Select...", "-SWG Item Promotions-", listBoxInfo, "handleTCGPromotionMenu", "tcg_menu", "tcg_menu", sui.OK_CANCEL_REFRESH);
    }
    public void qaTCGReinit(obj_id self) throws InterruptedException
    {
        sendSystemMessageTestingOnly(self, "Promotions Reinitialized!  Planet tatooine: " + getPlanetByName("tatooine"));
        scheduled_drop.removeClusterPromotions();
        scheduled_drop.removeLastClusterUpdateTime();
        scheduled_drop.instantiatePromotionsOnCluster();
    }
    public int handleTCGPromotionMenu(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isGod(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        int idx = sui.getListboxSelectedRow(params);
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            cleanAllScriptVars(self);
            return SCRIPT_CONTINUE;
        }
        if (idx < 0)
        {
            qaTCGMenu(self);
            return SCRIPT_CONTINUE;
        }
        if (idx % 2 == 1)
        {
            idx--;
        }
        String[] listBoxInfo = utils.getStringArrayScriptVar(self, "tcg_menu");
        String[] promotions = new String[1];
        promotions[0] = listBoxInfo[idx];
        dictionary[] promotionalItems = scheduled_drop.getStaticItemsForAllPromotions(promotions);
        String[] promotionList = new String[promotionalItems.length];
        for (int i = 0, j = promotionalItems.length; i < j; i++)
        {
            promotionList[i] = "[" + i + "]  Promo: " + promotionalItems[i].getString("promotionName") + "  Item: " + promotionalItems[i].getString("promotionItem") + "  Weight: " + promotionalItems[i].getInt("promotionWeight");
        }
        qa.refreshMenu(self, "Items in promotion...", "-SWG TCG Promotional Items-", promotionList, "handleTCGPromotionItems", "tcg_menu", "tcg_menu", sui.OK_CANCEL_REFRESH);
        return SCRIPT_CONTINUE;
    }
    public int handleTCGPromotionItems(obj_id self, dictionary params) throws InterruptedException
    {
        qaTCGMenu(self);
        return SCRIPT_CONTINUE;
    }
}
