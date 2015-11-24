package script.theme_park.tatooine;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class city_convo extends script.base_script
{
    public city_convo()
    {
    }
    public static final location mos_eisley = new location(3386, 6, -4825, "tatooine");
    public static final location mos_espa = new location(-2882, 7, 2097, "tatooine");
    public static final location bestine = new location(-927, 11, -4483, "tatooine");
    public static final location anchorhead = new location(62, 11, -5078, "tatooine");
    public static final location bank_eisley = new location(3312, 7, -4749, "tatooine");
    public static final location bank_espa = new location(-2699, 12, 2585, "tatooine");
    public static final location bank_bestine = new location(-900, 13, -4403, "tatooine");
    public static final location bank_anchorhead = new location(25, 8, -5550, "tatooine");
    public static final location starport_eisley = new location(3429, 11, -4826, "tatooine");
    public static final location starport_espa = new location(-2681, 7, 2291, "tatooine");
    public static final location starport_bestine = new location(-769, 11, -4257, "tatooine");
    public static final location starport_anchorhead = new location(178, 9, -5314, "tatooine");
    public static final location cantina_eisley = new location(3651, 5, -4903, "tatooine");
    public static final location cantina_espa = new location(-2569, 6, 2560, "tatooine");
    public static final location cantina_bestine = new location(-875, 11, -4284, "tatooine");
    public static final location cantina_anchorhead = new location(110, 16, -5422, "tatooine");
    public static final location hospital_eisley = new location(3477, 7, -4971, "tatooine");
    public static final location hospital_espa = new location(-2895, 8, 2457, "tatooine");
    public static final location hospital_bestine = new location(-849, 8, -4158, "tatooine");
    public static final location hospital_anchorhead = new location(-101, 13, -5420, "tatooine");
    public static final location cloning_facility_eisley = new location(3496, 7, -5165, "tatooine");
    public static final location cloning_facility_espa = new location(-2405, 7, 2369, "tatooine");
    public static final location cloning_facility_bestine = new location(-741, 12, -3984, "tatooine");
    public static final location cloning_facility_anchorhead = new location(81, 10, -5204, "tatooine");
    public static final location jabba = new location(-6390, 83, -7180, "tatooine");
    public static final location dera = new location(10, 0, 10, "tatooine");
    public static final location talmont = new location(-933, 10, -4295, "tatooine");
    public static final location smuggler = new location(20, 0, 20, "tatooine");
    public static final location ranger = new location(10, 0, 10, "tatooine");
    public static final location explorer = new location(20, 0, 20, "tatooine");
    public static final location combat_engineer = new location(20, 0, 20, "tatooine");
    public static final location combat_medic = new location(10, 0, 10, "tatooine");
    public static final location commando = new location(20, 0, 20, "tatooine");
    public static final location creature_handler = new location(20, 0, 20, "tatooine");
    public static final location martial_artist = new location(10, 0, 10, "tatooine");
    public static final location marksman = new location(20, 0, 20, "tatooine");
    public static final location xenobiologist = new location(20, 0, 20, "tatooine");
    public static final location image_designer = new location(10, 0, 10, "tatooine");
    public static final location architect = new location(20, 0, 20, "tatooine");
    public static final location armorer = new location(20, 0, 20, "tatooine");
    public static final location chef = new location(10, 0, 10, "tatooine");
    public static final location droid_engineer = new location(20, 0, 20, "tatooine");
    public static final location gunsmith = new location(20, 0, 20, "tatooine");
    public static final location vehicle_engineer = new location(10, 0, 10, "tatooine");
    public static final location melee_weaponsmith = new location(20, 0, 20, "tatooine");
    public static final location doctor = new location(20, 0, 20, "tatooine");
    public static final location entertainer = new location(10, 0, 10, "tatooine");
    public int OnStartNpcConversation(obj_id self, obj_id speaker) throws InterruptedException
    {
        string_id greeting = new string_id("generic_city_convo", "greeting_1");
        string_id response[] = new string_id[4];
        response[0] = new string_id("generic_city_convo", "player_1");
        response[1] = new string_id("generic_city_convo", "player_3");
        response[2] = new string_id("generic_city_convo", "player_2");
        response[3] = new string_id("generic_city_convo", "player_13");
        npcStartConversation(speaker, self, "generic_city_convo", greeting, response);
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String convo, obj_id player, string_id response) throws InterruptedException
    {
        if ((response.getAsciiId()).equals("player_1"))
        {
            string_id message = new string_id("generic_city_convo", "cities_1");
            npcSpeak(player, message);
            npcAddConversationResponse(player, new string_id("generic_city_convo", "player_4"));
            npcAddConversationResponse(player, new string_id("generic_city_convo", "player_5"));
            npcAddConversationResponse(player, new string_id("generic_city_convo", "player_6"));
            npcAddConversationResponse(player, new string_id("generic_city_convo", "player_7"));
            npcAddConversationResponse(player, new string_id("generic_city_convo", "player_12"));
            npcRemoveConversationResponse(player, new string_id("generic_city_convo", "player_2"));
            npcRemoveConversationResponse(player, new string_id("generic_city_convo", "player_3"));
            npcRemoveConversationResponse(player, new string_id("generic_city_convo", "player_13"));
            npcRemoveConversationResponse(player, new string_id("generic_city_convo", "player_1"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("player_3"))
        {
            string_id message = new string_id("generic_city_convo", "people_1");
            npcSpeak(player, message);
            npcAddConversationResponse(player, new string_id("generic_city_convo", "player_8"));
            npcAddConversationResponse(player, new string_id("generic_city_convo", "player_9"));
            npcAddConversationResponse(player, new string_id("generic_city_convo", "player_10"));
            npcAddConversationResponse(player, new string_id("generic_city_convo", "player_11"));
            npcAddConversationResponse(player, new string_id("generic_city_convo", "player_12"));
            npcRemoveConversationResponse(player, new string_id("generic_city_convo", "player_2"));
            npcRemoveConversationResponse(player, new string_id("generic_city_convo", "player_1"));
            npcRemoveConversationResponse(player, new string_id("generic_city_convo", "player_3"));
            npcRemoveConversationResponse(player, new string_id("generic_city_convo", "player_13"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("player_2"))
        {
            string_id message = new string_id("generic_city_convo", "mission_1");
            npcSpeak(player, message);
            npcRemoveConversationResponse(player, new string_id("generic_city_convo", "player_2"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("player_4"))
        {
            string_id message = new string_id("generic_city_convo", "mos_eisley_1");
            npcSpeak(player, message);
            npcRemoveConversationResponse(player, new string_id("generic_city_convo", "player_4"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("player_5"))
        {
            string_id message = new string_id("generic_city_convo", "mos_espa_1");
            npcSpeak(player, message);
            npcRemoveConversationResponse(player, new string_id("generic_city_convo", "player_5"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("player_6"))
        {
            string_id message = new string_id("generic_city_convo", "anchorhead_1");
            npcSpeak(player, message);
            npcRemoveConversationResponse(player, new string_id("generic_city_convo", "player_6"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("player_7"))
        {
            string_id message = new string_id("generic_city_convo", "bestine_1");
            npcSpeak(player, message);
            npcRemoveConversationResponse(player, new string_id("generic_city_convo", "player_7"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("player_8"))
        {
            string_id message = new string_id("generic_city_convo", "jabba_1");
            npcSpeak(player, message);
            npcRemoveConversationResponse(player, new string_id("generic_city_convo", "player_8"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("player_9"))
        {
            string_id message = new string_id("generic_city_convo", "talmont_1");
            npcSpeak(player, message);
            npcRemoveConversationResponse(player, new string_id("generic_city_convo", "player_9"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("player_10"))
        {
            string_id message = new string_id("generic_city_convo", "dera_1");
            npcSpeak(player, message);
            npcRemoveConversationResponse(player, new string_id("generic_city_convo", "player_10"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("player_11"))
        {
            string_id message = new string_id("generic_city_convo", "tuskens_1");
            npcSpeak(player, message);
            npcRemoveConversationResponse(player, new string_id("generic_city_convo", "player_11"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("player_12"))
        {
            string_id message = new string_id("generic_city_convo", "sure_1");
            npcSpeak(player, message);
            string_id[] responses = new string_id[4];
            responses[0] = new string_id("generic_city_convo", "player_1");
            responses[1] = new string_id("generic_city_convo", "player_3");
            responses[2] = new string_id("generic_city_convo", "player_2");
            responses[3] = new string_id("generic_city_convo", "player_13");
            npcSetConversationResponses(player, responses);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("player_13"))
        {
            string_id message = new string_id("generic_city_convo", "where_1");
            npcSpeak(player, message);
            npcAddConversationResponse(player, new string_id("generic_city_convo", "player_14"));
            npcAddConversationResponse(player, new string_id("generic_city_convo", "player_15"));
            npcAddConversationResponse(player, new string_id("generic_city_convo", "player_16"));
            npcAddConversationResponse(player, new string_id("generic_city_convo", "player_12"));
            npcRemoveConversationResponse(player, new string_id("generic_city_convo", "player_2"));
            npcRemoveConversationResponse(player, new string_id("generic_city_convo", "player_3"));
            npcRemoveConversationResponse(player, new string_id("generic_city_convo", "player_13"));
            npcRemoveConversationResponse(player, new string_id("generic_city_convo", "player_1"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("player_14"))
        {
            string_id message = new string_id("generic_city_convo", "find_city_1");
            npcSpeak(player, message);
            npcAddConversationResponse(player, new string_id("generic_city_convo", "directions_eisley"));
            npcAddConversationResponse(player, new string_id("generic_city_convo", "directions_espa"));
            npcAddConversationResponse(player, new string_id("generic_city_convo", "directions_bestine"));
            npcAddConversationResponse(player, new string_id("generic_city_convo", "directions_anchorhead"));
            npcAddConversationResponse(player, new string_id("generic_city_convo", "player_12"));
            npcRemoveConversationResponse(player, new string_id("generic_city_convo", "player_15"));
            npcRemoveConversationResponse(player, new string_id("generic_city_convo", "player_14"));
            npcRemoveConversationResponse(player, new string_id("generic_city_convo", "player_16"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("player_15"))
        {
            string_id message = new string_id("generic_city_convo", "find_bldg_1");
            npcSpeak(player, message);
            npcAddConversationResponse(player, new string_id("generic_city_convo", "bank"));
            npcAddConversationResponse(player, new string_id("generic_city_convo", "cantina"));
            npcAddConversationResponse(player, new string_id("generic_city_convo", "cloning_facility"));
            npcAddConversationResponse(player, new string_id("generic_city_convo", "hospital"));
            npcAddConversationResponse(player, new string_id("generic_city_convo", "starport"));
            npcAddConversationResponse(player, new string_id("generic_city_convo", "player_12"));
            npcRemoveConversationResponse(player, new string_id("generic_city_convo", "player_15"));
            npcRemoveConversationResponse(player, new string_id("generic_city_convo", "player_14"));
            npcRemoveConversationResponse(player, new string_id("generic_city_convo", "player_16"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("player_16"))
        {
            string_id message = new string_id("generic_city_convo", "find_person_1");
            npcSpeak(player, message);
            npcAddConversationResponse(player, new string_id("generic_city_convo", "find_jabba"));
            npcAddConversationResponse(player, new string_id("generic_city_convo", "find_talmont"));
            npcAddConversationResponse(player, new string_id("generic_city_convo", "find_dera"));
            npcAddConversationResponse(player, new string_id("generic_city_convo", "find_trainer"));
            npcAddConversationResponse(player, new string_id("generic_city_convo", "player_12"));
            npcRemoveConversationResponse(player, new string_id("generic_city_convo", "player_14"));
            npcRemoveConversationResponse(player, new string_id("generic_city_convo", "player_16"));
            npcRemoveConversationResponse(player, new string_id("generic_city_convo", "player_15"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("directions_eisley"))
        {
            string_id message = new string_id("generic_city_convo", "add_to_radar");
            makeWaypoint(mos_eisley, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("directions_espa"))
        {
            string_id message = new string_id("generic_city_convo", "add_to_radar");
            makeWaypoint(mos_espa, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("directions_anchorhead"))
        {
            string_id message = new string_id("generic_city_convo", "add_to_radar");
            makeWaypoint(anchorhead, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("directions_bestine"))
        {
            string_id message = new string_id("generic_city_convo", "add_to_radar");
            makeWaypoint(bestine, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("bank"))
        {
            string_id message = new string_id("generic_city_convo", "bank_city");
            npcSpeak(player, message);
            npcAddConversationResponse(player, new string_id("generic_city_convo", "bank_eisley"));
            npcAddConversationResponse(player, new string_id("generic_city_convo", "bank_espa"));
            npcAddConversationResponse(player, new string_id("generic_city_convo", "bank_bestine"));
            npcAddConversationResponse(player, new string_id("generic_city_convo", "bank_anchorhead"));
            npcRemoveConversationResponse(player, new string_id("generic_city_convo", "bank"));
            npcRemoveConversationResponse(player, new string_id("generic_city_convo", "cantina"));
            npcRemoveConversationResponse(player, new string_id("generic_city_convo", "cloning_facility"));
            npcRemoveConversationResponse(player, new string_id("generic_city_convo", "hospital"));
            npcRemoveConversationResponse(player, new string_id("generic_city_convo", "starport"));
            npcAddConversationResponse(player, new string_id("generic_city_convo", "player_12"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("cantina"))
        {
            string_id message = new string_id("generic_city_convo", "cantina_city");
            npcSpeak(player, message);
            npcAddConversationResponse(player, new string_id("generic_city_convo", "cantina_eisley"));
            npcAddConversationResponse(player, new string_id("generic_city_convo", "cantina_espa"));
            npcAddConversationResponse(player, new string_id("generic_city_convo", "cantina_bestine"));
            npcAddConversationResponse(player, new string_id("generic_city_convo", "cantina_anchorhead"));
            npcRemoveConversationResponse(player, new string_id("generic_city_convo", "cantina"));
            npcRemoveConversationResponse(player, new string_id("generic_city_convo", "bank"));
            npcRemoveConversationResponse(player, new string_id("generic_city_convo", "cloning_facility"));
            npcRemoveConversationResponse(player, new string_id("generic_city_convo", "hospital"));
            npcRemoveConversationResponse(player, new string_id("generic_city_convo", "starport"));
            npcAddConversationResponse(player, new string_id("generic_city_convo", "player_12"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("hospital"))
        {
            string_id message = new string_id("generic_city_convo", "hospital_city");
            npcSpeak(player, message);
            npcAddConversationResponse(player, new string_id("generic_city_convo", "hospital_eisley"));
            npcAddConversationResponse(player, new string_id("generic_city_convo", "hospital_espa"));
            npcAddConversationResponse(player, new string_id("generic_city_convo", "hospital_bestine"));
            npcAddConversationResponse(player, new string_id("generic_city_convo", "hospital_anchorhead"));
            npcRemoveConversationResponse(player, new string_id("generic_city_convo", "hospital"));
            npcRemoveConversationResponse(player, new string_id("generic_city_convo", "bank"));
            npcRemoveConversationResponse(player, new string_id("generic_city_convo", "cloning_facility"));
            npcRemoveConversationResponse(player, new string_id("generic_city_convo", "cantina"));
            npcRemoveConversationResponse(player, new string_id("generic_city_convo", "starport"));
            npcAddConversationResponse(player, new string_id("generic_city_convo", "player_12"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("starport"))
        {
            string_id message = new string_id("generic_city_convo", "starport_city");
            npcSpeak(player, message);
            npcAddConversationResponse(player, new string_id("generic_city_convo", "starport_eisley"));
            npcAddConversationResponse(player, new string_id("generic_city_convo", "starport_espa"));
            npcAddConversationResponse(player, new string_id("generic_city_convo", "starport_bestine"));
            npcAddConversationResponse(player, new string_id("generic_city_convo", "starport_anchorhead"));
            npcRemoveConversationResponse(player, new string_id("generic_city_convo", "bank"));
            npcRemoveConversationResponse(player, new string_id("generic_city_convo", "cloning_facility"));
            npcRemoveConversationResponse(player, new string_id("generic_city_convo", "hospital"));
            npcRemoveConversationResponse(player, new string_id("generic_city_convo", "starport"));
            npcRemoveConversationResponse(player, new string_id("generic_city_convo", "cantina"));
            npcAddConversationResponse(player, new string_id("generic_city_convo", "player_12"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("cloning_facility"))
        {
            string_id message = new string_id("generic_city_convo", "cloning_facility_city");
            npcSpeak(player, message);
            npcAddConversationResponse(player, new string_id("generic_city_convo", "cloning_facility_eisley"));
            npcAddConversationResponse(player, new string_id("generic_city_convo", "cloning_facility_espa"));
            npcAddConversationResponse(player, new string_id("generic_city_convo", "cloning_facility_bestine"));
            npcAddConversationResponse(player, new string_id("generic_city_convo", "cloning_facility_anchorhead"));
            npcRemoveConversationResponse(player, new string_id("generic_city_convo", "bank"));
            npcRemoveConversationResponse(player, new string_id("generic_city_convo", "cantina"));
            npcRemoveConversationResponse(player, new string_id("generic_city_convo", "hospital"));
            npcRemoveConversationResponse(player, new string_id("generic_city_convo", "starport"));
            npcAddConversationResponse(player, new string_id("generic_city_convo", "player_12"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("bank_eisley"))
        {
            string_id message = new string_id("generic_city_convo", "add_to_radar");
            makeWaypoint(bank_eisley, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("bank_espa"))
        {
            string_id message = new string_id("generic_city_convo", "add_to_radar");
            makeWaypoint(bank_espa, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("bank_anchorhead"))
        {
            string_id message = new string_id("generic_city_convo", "add_to_radar");
            makeWaypoint(bank_anchorhead, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("bank_bestine"))
        {
            string_id message = new string_id("generic_city_convo", "add_to_radar");
            makeWaypoint(bank_bestine, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("cantina_eisley"))
        {
            string_id message = new string_id("generic_city_convo", "add_to_radar");
            makeWaypoint(cantina_eisley, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("cantina_espa"))
        {
            string_id message = new string_id("generic_city_convo", "add_to_radar");
            makeWaypoint(cantina_espa, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("cantina_anchorhead"))
        {
            string_id message = new string_id("generic_city_convo", "add_to_radar");
            makeWaypoint(cantina_anchorhead, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("cantina_bestine"))
        {
            string_id message = new string_id("generic_city_convo", "add_to_radar");
            makeWaypoint(cantina_bestine, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("hospital_eisley"))
        {
            string_id message = new string_id("generic_city_convo", "add_to_radar");
            makeWaypoint(hospital_eisley, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("hospital_espa"))
        {
            string_id message = new string_id("generic_city_convo", "add_to_radar");
            makeWaypoint(hospital_espa, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("hospital_anchorhead"))
        {
            string_id message = new string_id("generic_city_convo", "add_to_radar");
            makeWaypoint(hospital_anchorhead, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("hospital_bestine"))
        {
            string_id message = new string_id("generic_city_convo", "add_to_radar");
            makeWaypoint(hospital_bestine, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("cloning_facility_eisley"))
        {
            string_id message = new string_id("generic_city_convo", "add_to_radar");
            makeWaypoint(cloning_facility_eisley, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("cloning_facility_espa"))
        {
            string_id message = new string_id("generic_city_convo", "add_to_radar");
            makeWaypoint(cloning_facility_espa, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("cloning_facility_anchorhead"))
        {
            string_id message = new string_id("generic_city_convo", "add_to_radar");
            makeWaypoint(cloning_facility_anchorhead, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("cloning_facility_bestine"))
        {
            string_id message = new string_id("generic_city_convo", "add_to_radar");
            makeWaypoint(cloning_facility_bestine, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("starport_eisley"))
        {
            string_id message = new string_id("generic_city_convo", "add_to_radar");
            makeWaypoint(starport_eisley, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("starport_espa"))
        {
            string_id message = new string_id("generic_city_convo", "add_to_radar");
            makeWaypoint(starport_espa, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("starport_anchorhead"))
        {
            string_id message = new string_id("generic_city_convo", "add_to_radar");
            makeWaypoint(starport_anchorhead, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("starport_bestine"))
        {
            string_id message = new string_id("generic_city_convo", "add_to_radar");
            makeWaypoint(starport_bestine, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("find_jabba"))
        {
            string_id message = new string_id("generic_city_convo", "add_to_radar");
            makeWaypoint(jabba, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("find_talmont"))
        {
            string_id message = new string_id("generic_city_convo", "add_to_radar");
            makeWaypoint(talmont, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("find_dera"))
        {
            string_id message = new string_id("generic_city_convo", "add_to_radar");
            makeWaypoint(dera, player);
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
