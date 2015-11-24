package script.theme_park.naboo;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class naboo_convo extends script.base_script
{
    public naboo_convo()
    {
    }
    public static final String CONVO = "naboo_convo";
    public static final location theed = new location(3355, 6, -5018, "naboo", null);
    public static final location deeja = new location(-2882, 7, 2097, "naboo");
    public static final location keren = new location(-927, 11, -4483, "naboo");
    public static final location moenia = new location(62, 11, -5078, "naboo");
    public static final location bank_theed = new location(3499, 10, -4944, "naboo", null);
    public static final location bank_deeja = new location(-2699, 12, 2585, "naboo");
    public static final location bank_keren = new location(-900, 13, -4403, "naboo");
    public static final location bank_moenia = new location(25, 8, -5550, "naboo");
    public static final location starport_theed = new location(3534, 5, -4803, "naboo", null);
    public static final location starport_deeja = new location(-2681, 7, 2291, "naboo");
    public static final location starport_keren = new location(-769, 11, -4257, "naboo");
    public static final location starport_moenia = new location(178, 9, -5314, "naboo");
    public static final location cantina_theed = new location(3467, 5, -4852, "naboo", null);
    public static final location cantina_deeja = new location(-2569, 6, 2560, "naboo");
    public static final location cantina_keren = new location(-875, 11, -4284, "naboo");
    public static final location cantina_moenia = new location(110, 16, -5422, "naboo");
    public static final location trainer_theed = new location(3467, 5, -4852, "naboo", null);
    public static final location trainer_deeja = new location(-2895, 8, 2457, "naboo");
    public static final location trainer_keren = new location(-849, 8, -4158, "naboo");
    public static final location trainer_moenia = new location(-101, 13, -5420, "naboo");
    public static final location cloning_facility_theed = new location(3467, 5, -4852, "naboo", null);
    public static final location cloning_facility_deeja = new location(-2405, 7, 2369, "naboo");
    public static final location cloning_facility_keren = new location(-741, 12, -3984, "naboo");
    public static final location cloning_facility_moenia = new location(81, 10, -5204, "naboo");
    public static final location nass = new location(-6390, 83, -7180, "naboo");
    public static final location emperor = new location(10, 0, 10, "naboo");
    public static final location kaja = new location(-933, 10, -4295, "naboo");
    public static final location combat_trainer_theed = new location(-1100, 0, -3737, "naboo");
    public static final location combat_trainer_deeja = new location(-1100, 0, -3737, "naboo");
    public static final location combat_trainer_keren = new location(-1100, 0, -3737, "naboo");
    public static final location combat_trainer_moenia = new location(-1100, 0, -3737, "naboo");
    public static final location crafting_trainer_theed = new location(-1100, 0, -3737, "naboo");
    public static final location crafting_trainer_deeja = new location(-1100, 0, -3737, "naboo");
    public static final location crafting_trainer_keren = new location(-1100, 0, -3737, "naboo");
    public static final location crafting_trainer_moenia = new location(-1100, 0, -3737, "naboo");
    public static final location university_trainer_theed = new location(-1100, 0, -3737, "naboo");
    public static final location university_trainer_deeja = new location(-1100, 0, -3737, "naboo");
    public static final location university_trainer_keren = new location(-1100, 0, -3737, "naboo");
    public static final location university_trainer_moenia = new location(-1100, 0, -3737, "naboo");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!hasScript(self, "npc.converse.npc_converse_menu"))
        {
            attachScript(self, "npc.converse.npc_converse_menu");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id speaker) throws InterruptedException
    {
        string_id greeting = new string_id(CONVO, "greeting_1");
        string_id response[] = new string_id[4];
        response[0] = new string_id(CONVO, "player_1");
        response[1] = new string_id(CONVO, "player_3");
        response[2] = new string_id(CONVO, "player_2");
        response[3] = new string_id(CONVO, "player_13");
        npcStartConversation(speaker, self, CONVO, greeting, response);
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String convo, obj_id player, string_id response) throws InterruptedException
    {
        if ((response.getAsciiId()).equals("player_1"))
        {
            string_id message = new string_id(CONVO, "cities_1");
            npcSpeak(player, message);
            npcAddConversationResponse(player, new string_id(CONVO, "player_4"));
            npcAddConversationResponse(player, new string_id(CONVO, "player_5"));
            npcAddConversationResponse(player, new string_id(CONVO, "player_6"));
            npcAddConversationResponse(player, new string_id(CONVO, "player_7"));
            npcAddConversationResponse(player, new string_id(CONVO, "player_12"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "player_2"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "player_3"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "player_13"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "player_1"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("player_3"))
        {
            string_id message = new string_id(CONVO, "people_1");
            npcSpeak(player, message);
            npcAddConversationResponse(player, new string_id(CONVO, "player_8"));
            npcAddConversationResponse(player, new string_id(CONVO, "player_9"));
            npcAddConversationResponse(player, new string_id(CONVO, "player_10"));
            npcAddConversationResponse(player, new string_id(CONVO, "player_11"));
            npcAddConversationResponse(player, new string_id(CONVO, "player_12"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "player_2"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "player_1"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "player_3"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "player_13"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("player_2"))
        {
            string_id message = new string_id(CONVO, "mission_1");
            npcSpeak(player, message);
            npcRemoveConversationResponse(player, new string_id(CONVO, "player_2"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("player_4"))
        {
            string_id message = new string_id(CONVO, "theed_1");
            npcSpeak(player, message);
            npcRemoveConversationResponse(player, new string_id(CONVO, "player_4"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("player_5"))
        {
            string_id message = new string_id(CONVO, "deeja_1");
            npcSpeak(player, message);
            npcRemoveConversationResponse(player, new string_id(CONVO, "player_5"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("player_6"))
        {
            string_id message = new string_id(CONVO, "moenia_1");
            npcSpeak(player, message);
            npcRemoveConversationResponse(player, new string_id(CONVO, "player_6"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("player_7"))
        {
            string_id message = new string_id(CONVO, "keren_1");
            npcSpeak(player, message);
            npcRemoveConversationResponse(player, new string_id(CONVO, "player_7"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("player_8"))
        {
            string_id message = new string_id(CONVO, "nass_1");
            npcSpeak(player, message);
            npcRemoveConversationResponse(player, new string_id(CONVO, "player_8"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("player_9"))
        {
            string_id message = new string_id(CONVO, "kaja_1");
            npcSpeak(player, message);
            npcRemoveConversationResponse(player, new string_id(CONVO, "player_9"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("player_10"))
        {
            string_id message = new string_id(CONVO, "emperor_1");
            npcSpeak(player, message);
            npcRemoveConversationResponse(player, new string_id(CONVO, "player_10"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("player_11"))
        {
            string_id message = new string_id(CONVO, "imperials_1");
            npcSpeak(player, message);
            npcRemoveConversationResponse(player, new string_id(CONVO, "player_11"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("player_12"))
        {
            string_id message = new string_id(CONVO, "sure_1");
            npcSpeak(player, message);
            string_id[] responses = new string_id[4];
            responses[0] = new string_id(CONVO, "player_1");
            responses[1] = new string_id(CONVO, "player_3");
            responses[2] = new string_id(CONVO, "player_2");
            responses[3] = new string_id(CONVO, "player_13");
            npcSetConversationResponses(player, responses);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("player_13"))
        {
            string_id message = new string_id(CONVO, "where_1");
            npcSpeak(player, message);
            npcAddConversationResponse(player, new string_id(CONVO, "player_14"));
            npcAddConversationResponse(player, new string_id(CONVO, "player_15"));
            npcAddConversationResponse(player, new string_id(CONVO, "player_16"));
            npcAddConversationResponse(player, new string_id(CONVO, "player_12"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "player_2"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "player_3"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "player_13"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "player_1"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("player_14"))
        {
            string_id message = new string_id(CONVO, "find_city_1");
            npcSpeak(player, message);
            npcAddConversationResponse(player, new string_id(CONVO, "directions_theed"));
            npcAddConversationResponse(player, new string_id(CONVO, "directions_deeja"));
            npcAddConversationResponse(player, new string_id(CONVO, "directions_keren"));
            npcAddConversationResponse(player, new string_id(CONVO, "directions_moenia"));
            npcAddConversationResponse(player, new string_id(CONVO, "player_12"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "player_15"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "player_14"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "player_16"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("player_15"))
        {
            string_id message = new string_id(CONVO, "find_bldg_1");
            npcSpeak(player, message);
            npcAddConversationResponse(player, new string_id(CONVO, "bank"));
            npcAddConversationResponse(player, new string_id(CONVO, "cantina"));
            npcAddConversationResponse(player, new string_id(CONVO, "cloning_facility"));
            npcAddConversationResponse(player, new string_id(CONVO, "trainer"));
            npcAddConversationResponse(player, new string_id(CONVO, "starport"));
            npcAddConversationResponse(player, new string_id(CONVO, "player_12"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "player_15"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "player_14"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "player_16"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("player_16"))
        {
            string_id message = new string_id(CONVO, "find_person_1");
            npcSpeak(player, message);
            npcAddConversationResponse(player, new string_id(CONVO, "find_nass"));
            npcAddConversationResponse(player, new string_id(CONVO, "find_kaja"));
            npcAddConversationResponse(player, new string_id(CONVO, "find_emperor"));
            npcAddConversationResponse(player, new string_id(CONVO, "find_trainer"));
            npcAddConversationResponse(player, new string_id(CONVO, "player_12"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "player_14"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "player_16"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "player_15"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("directions_theed"))
        {
            string_id message = new string_id(CONVO, "add_to_radar");
            makeWaypoint(theed, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("directions_deeja"))
        {
            string_id message = new string_id(CONVO, "add_to_radar");
            makeWaypoint(deeja, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("directions_moenia"))
        {
            string_id message = new string_id(CONVO, "add_to_radar");
            makeWaypoint(moenia, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("directions_keren"))
        {
            string_id message = new string_id(CONVO, "add_to_radar");
            makeWaypoint(keren, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("bank"))
        {
            string_id message = new string_id(CONVO, "bank_city");
            npcSpeak(player, message);
            npcAddConversationResponse(player, new string_id(CONVO, "bank_theed"));
            npcAddConversationResponse(player, new string_id(CONVO, "bank_deeja"));
            npcAddConversationResponse(player, new string_id(CONVO, "bank_keren"));
            npcAddConversationResponse(player, new string_id(CONVO, "bank_moenia"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "bank"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "cantina"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "cloning_facility"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "trainer"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "starport"));
            npcAddConversationResponse(player, new string_id(CONVO, "player_12"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("cantina"))
        {
            string_id message = new string_id(CONVO, "cantina_city");
            npcSpeak(player, message);
            npcAddConversationResponse(player, new string_id(CONVO, "cantina_theed"));
            npcAddConversationResponse(player, new string_id(CONVO, "cantina_deeja"));
            npcAddConversationResponse(player, new string_id(CONVO, "cantina_keren"));
            npcAddConversationResponse(player, new string_id(CONVO, "cantina_moenia"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "cantina"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "bank"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "cloning_facility"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "trainer"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "starport"));
            npcAddConversationResponse(player, new string_id(CONVO, "player_12"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("trainer"))
        {
            string_id message = new string_id(CONVO, "trainer_city");
            npcSpeak(player, message);
            npcAddConversationResponse(player, new string_id(CONVO, "trainer_theed"));
            npcAddConversationResponse(player, new string_id(CONVO, "trainer_deeja"));
            npcAddConversationResponse(player, new string_id(CONVO, "trainer_keren"));
            npcAddConversationResponse(player, new string_id(CONVO, "trainer_moenia"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "trainer"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "bank"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "cloning_facility"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "cantina"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "starport"));
            npcAddConversationResponse(player, new string_id(CONVO, "player_12"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("starport"))
        {
            string_id message = new string_id(CONVO, "starport_city");
            npcSpeak(player, message);
            npcAddConversationResponse(player, new string_id(CONVO, "starport_theed"));
            npcAddConversationResponse(player, new string_id(CONVO, "starport_deeja"));
            npcAddConversationResponse(player, new string_id(CONVO, "starport_keren"));
            npcAddConversationResponse(player, new string_id(CONVO, "starport_moenia"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "bank"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "cloning_facility"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "trainer"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "starport"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "cantina"));
            npcAddConversationResponse(player, new string_id(CONVO, "player_12"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("cloning_facility"))
        {
            string_id message = new string_id(CONVO, "cloning_facility_city");
            npcSpeak(player, message);
            npcAddConversationResponse(player, new string_id(CONVO, "cloning_facility_theed"));
            npcAddConversationResponse(player, new string_id(CONVO, "cloning_facility_deeja"));
            npcAddConversationResponse(player, new string_id(CONVO, "cloning_facility_keren"));
            npcAddConversationResponse(player, new string_id(CONVO, "cloning_facility_moenia"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "bank"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "cantina"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "trainer"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "starport"));
            npcAddConversationResponse(player, new string_id(CONVO, "player_12"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("bank_theed"))
        {
            string_id message = new string_id(CONVO, "add_to_radar");
            makeWaypoint(bank_theed, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("bank_deeja"))
        {
            string_id message = new string_id(CONVO, "add_to_radar");
            makeWaypoint(bank_deeja, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("bank_moenia"))
        {
            string_id message = new string_id(CONVO, "add_to_radar");
            makeWaypoint(bank_moenia, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("bank_keren"))
        {
            string_id message = new string_id(CONVO, "add_to_radar");
            makeWaypoint(bank_keren, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("cantina_theed"))
        {
            string_id message = new string_id(CONVO, "add_to_radar");
            makeWaypoint(cantina_theed, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("cantina_deeja"))
        {
            string_id message = new string_id(CONVO, "add_to_radar");
            makeWaypoint(cantina_deeja, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("cantina_moenia"))
        {
            string_id message = new string_id(CONVO, "add_to_radar");
            makeWaypoint(cantina_moenia, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("cantina_keren"))
        {
            string_id message = new string_id(CONVO, "add_to_radar");
            makeWaypoint(cantina_keren, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("trainer_theed"))
        {
            string_id message = new string_id(CONVO, "which_trainer");
            makeWaypoint(trainer_theed, player);
            npcSpeak(player, message);
            npcAddConversationResponse(player, new string_id(CONVO, "university_trainer_theed"));
            npcAddConversationResponse(player, new string_id(CONVO, "crafting_trainer_theed"));
            npcAddConversationResponse(player, new string_id(CONVO, "combat_trainer_theed"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "trainer_theed"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "trainer_deeja"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "trainer_keren"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "trainer_moenia"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("trainer_deeja"))
        {
            string_id message = new string_id(CONVO, "which_trainer");
            makeWaypoint(trainer_deeja, player);
            npcSpeak(player, message);
            npcAddConversationResponse(player, new string_id(CONVO, "university_trainer_deeja"));
            npcAddConversationResponse(player, new string_id(CONVO, "crafting_trainer_deeja"));
            npcAddConversationResponse(player, new string_id(CONVO, "combat_trainer_deeja"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "trainer_theed"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "trainer_deeja"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "trainer_keren"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "trainer_moenia"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("trainer_moenia"))
        {
            string_id message = new string_id(CONVO, "which_trainer");
            makeWaypoint(trainer_moenia, player);
            npcSpeak(player, message);
            npcAddConversationResponse(player, new string_id(CONVO, "university_trainer_moenia"));
            npcAddConversationResponse(player, new string_id(CONVO, "crafting_trainer_moenia"));
            npcAddConversationResponse(player, new string_id(CONVO, "combat_trainer_moenia"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "trainer_theed"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "trainer_deeja"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "trainer_keren"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "trainer_moenia"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("trainer_keren"))
        {
            string_id message = new string_id(CONVO, "which_trainer");
            makeWaypoint(trainer_keren, player);
            npcSpeak(player, message);
            npcAddConversationResponse(player, new string_id(CONVO, "university_trainer_keren"));
            npcAddConversationResponse(player, new string_id(CONVO, "crafting_trainer_keren"));
            npcAddConversationResponse(player, new string_id(CONVO, "combat_trainer_keren"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "trainer_theed"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "trainer_deeja"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "trainer_keren"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "trainer_moenia"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("cloning_facility_theed"))
        {
            string_id message = new string_id(CONVO, "add_to_radar");
            makeWaypoint(cloning_facility_theed, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("cloning_facility_deeja"))
        {
            string_id message = new string_id(CONVO, "add_to_radar");
            makeWaypoint(cloning_facility_deeja, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("cloning_facility_moenia"))
        {
            string_id message = new string_id(CONVO, "add_to_radar");
            makeWaypoint(cloning_facility_moenia, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("cloning_facility_keren"))
        {
            string_id message = new string_id(CONVO, "add_to_radar");
            makeWaypoint(cloning_facility_keren, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("starport_theed"))
        {
            string_id message = new string_id(CONVO, "add_to_radar");
            makeWaypoint(starport_theed, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("starport_deeja"))
        {
            string_id message = new string_id(CONVO, "add_to_radar");
            makeWaypoint(starport_deeja, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("starport_moenia"))
        {
            string_id message = new string_id(CONVO, "add_to_radar");
            makeWaypoint(starport_moenia, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("starport_keren"))
        {
            string_id message = new string_id(CONVO, "add_to_radar");
            makeWaypoint(starport_keren, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("find_nass"))
        {
            string_id message = new string_id(CONVO, "add_to_radar");
            makeWaypoint(nass, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("find_kaja"))
        {
            string_id message = new string_id(CONVO, "add_to_radar");
            makeWaypoint(kaja, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("find_emperor"))
        {
            string_id message = new string_id(CONVO, "add_to_radar");
            makeWaypoint(emperor, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("find_trainer"))
        {
            string_id message = new string_id(CONVO, "trainers_loc");
            npcSpeak(player, message);
            npcAddConversationResponse(player, new string_id(CONVO, "trainer_keren"));
            npcAddConversationResponse(player, new string_id(CONVO, ""));
            npcAddConversationResponse(player, new string_id(CONVO, ""));
            npcAddConversationResponse(player, new string_id(CONVO, ""));
            npcRemoveConversationResponse(player, new string_id(CONVO, "find_emperor"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "find_kaja"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "find_nass"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "find_trainer"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("combat_trainer_theed"))
        {
            string_id message = new string_id(CONVO, "add_to_radar");
            makeWaypoint(combat_trainer_theed, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("crafting_trainer_theed"))
        {
            string_id message = new string_id(CONVO, "add_to_radar");
            makeWaypoint(crafting_trainer_theed, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("university_trainer_theed"))
        {
            string_id message = new string_id(CONVO, "add_to_radar");
            makeWaypoint(university_trainer_theed, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("combat_trainer_deeja"))
        {
            string_id message = new string_id(CONVO, "add_to_radar");
            makeWaypoint(combat_trainer_deeja, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("crafting_trainer_deeja"))
        {
            string_id message = new string_id(CONVO, "add_to_radar");
            makeWaypoint(crafting_trainer_deeja, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("university_trainer_deeja"))
        {
            string_id message = new string_id(CONVO, "add_to_radar");
            makeWaypoint(university_trainer_deeja, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("combat_trainer_keren"))
        {
            string_id message = new string_id(CONVO, "add_to_radar");
            makeWaypoint(combat_trainer_keren, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("crafting_trainer_keren"))
        {
            string_id message = new string_id(CONVO, "add_to_radar");
            makeWaypoint(crafting_trainer_keren, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("university_trainer_keren"))
        {
            string_id message = new string_id(CONVO, "add_to_radar");
            makeWaypoint(university_trainer_keren, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("combat_trainer_theed"))
        {
            string_id message = new string_id(CONVO, "add_to_radar");
            makeWaypoint(combat_trainer_theed, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("crafting_trainer_theed"))
        {
            string_id message = new string_id(CONVO, "add_to_radar");
            makeWaypoint(crafting_trainer_theed, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("university_trainer_theed"))
        {
            string_id message = new string_id(CONVO, "add_to_radar");
            makeWaypoint(university_trainer_theed, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public void makeWaypoint(location loc, obj_id player) throws InterruptedException
    {
        obj_id waypoint = createWaypointInDatapad(player, loc);
        if (isIdValid(waypoint))
        {
            setWaypointVisible(waypoint, true);
            setWaypointActive(waypoint, true);
        }
    }
}
