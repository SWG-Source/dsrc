package script.theme_park.tatooine;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class tatooine_convo extends script.base_script
{
    public tatooine_convo()
    {
    }
    public static final String CONVO = "tatooine_convo";
    public static final location mos_eisley = new location(3355, 6, -5018, "tatooine", null);
    public static final location mos_espa = new location(-2882, 7, 2097, "tatooine");
    public static final location bestine = new location(-927, 11, -4483, "tatooine");
    public static final location anchorhead = new location(62, 11, -5078, "tatooine");
    public static final location bank_eisley = new location(3499, 10, -4944, "tatooine", null);
    public static final location bank_espa = new location(-2699, 12, 2585, "tatooine");
    public static final location bank_bestine = new location(-900, 13, -4403, "tatooine");
    public static final location bank_anchorhead = new location(25, 8, -5550, "tatooine");
    public static final location starport_eisley = new location(3534, 5, -4803, "tatooine", null);
    public static final location starport_espa = new location(-2681, 7, 2291, "tatooine");
    public static final location starport_bestine = new location(-769, 11, -4257, "tatooine");
    public static final location starport_anchorhead = new location(178, 9, -5314, "tatooine");
    public static final location cantina_eisley = new location(3467, 5, -4852, "tatooine", null);
    public static final location cantina_espa = new location(-2569, 6, 2560, "tatooine");
    public static final location cantina_bestine = new location(-875, 11, -4284, "tatooine");
    public static final location cantina_anchorhead = new location(110, 16, -5422, "tatooine");
    public static final location trainer_eisley = new location(3467, 5, -4852, "tatooine", null);
    public static final location trainer_espa = new location(-2895, 8, 2457, "tatooine");
    public static final location trainer_bestine = new location(-849, 8, -4158, "tatooine");
    public static final location trainer_anchorhead = new location(-101, 13, -5420, "tatooine");
    public static final location cloning_facility_eisley = new location(3467, 5, -4852, "tatooine", null);
    public static final location cloning_facility_espa = new location(-2405, 7, 2369, "tatooine");
    public static final location cloning_facility_bestine = new location(-741, 12, -3984, "tatooine");
    public static final location cloning_facility_anchorhead = new location(81, 10, -5204, "tatooine");
    public static final location jabba = new location(-6390, 83, -7180, "tatooine");
    public static final location dera = new location(10, 0, 10, "tatooine");
    public static final location talmont = new location(-933, 10, -4295, "tatooine");
    public static final location combat_trainer_eisley = new location(-1100, 0, -3737, "tatooine");
    public static final location combat_trainer_espa = new location(-1100, 0, -3737, "tatooine");
    public static final location combat_trainer_bestine = new location(-1100, 0, -3737, "tatooine");
    public static final location combat_trainer_anchorhead = new location(-1100, 0, -3737, "tatooine");
    public static final location crafting_trainer_eisley = new location(-1100, 0, -3737, "tatooine");
    public static final location crafting_trainer_espa = new location(-1100, 0, -3737, "tatooine");
    public static final location crafting_trainer_bestine = new location(-1100, 0, -3737, "tatooine");
    public static final location crafting_trainer_anchorhead = new location(-1100, 0, -3737, "tatooine");
    public static final location university_trainer_eisley = new location(-1100, 0, -3737, "tatooine");
    public static final location university_trainer_espa = new location(-1100, 0, -3737, "tatooine");
    public static final location university_trainer_bestine = new location(-1100, 0, -3737, "tatooine");
    public static final location university_trainer_anchorhead = new location(-1100, 0, -3737, "tatooine");
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
            string_id message = new string_id(CONVO, "mos_eisley_1");
            npcSpeak(player, message);
            npcRemoveConversationResponse(player, new string_id(CONVO, "player_4"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("player_5"))
        {
            string_id message = new string_id(CONVO, "mos_espa_1");
            npcSpeak(player, message);
            npcRemoveConversationResponse(player, new string_id(CONVO, "player_5"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("player_6"))
        {
            string_id message = new string_id(CONVO, "anchorhead_1");
            npcSpeak(player, message);
            npcRemoveConversationResponse(player, new string_id(CONVO, "player_6"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("player_7"))
        {
            string_id message = new string_id(CONVO, "bestine_1");
            npcSpeak(player, message);
            npcRemoveConversationResponse(player, new string_id(CONVO, "player_7"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("player_8"))
        {
            string_id message = new string_id(CONVO, "jabba_1");
            npcSpeak(player, message);
            npcRemoveConversationResponse(player, new string_id(CONVO, "player_8"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("player_9"))
        {
            string_id message = new string_id(CONVO, "talmont_1");
            npcSpeak(player, message);
            npcRemoveConversationResponse(player, new string_id(CONVO, "player_9"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("player_10"))
        {
            string_id message = new string_id(CONVO, "dera_1");
            npcSpeak(player, message);
            npcRemoveConversationResponse(player, new string_id(CONVO, "player_10"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("player_11"))
        {
            string_id message = new string_id(CONVO, "tuskens_1");
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
            npcAddConversationResponse(player, new string_id(CONVO, "directions_eisley"));
            npcAddConversationResponse(player, new string_id(CONVO, "directions_espa"));
            npcAddConversationResponse(player, new string_id(CONVO, "directions_bestine"));
            npcAddConversationResponse(player, new string_id(CONVO, "directions_anchorhead"));
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
            npcAddConversationResponse(player, new string_id(CONVO, "find_jabba"));
            npcAddConversationResponse(player, new string_id(CONVO, "find_talmont"));
            npcAddConversationResponse(player, new string_id(CONVO, "find_dera"));
            npcAddConversationResponse(player, new string_id(CONVO, "find_trainer"));
            npcAddConversationResponse(player, new string_id(CONVO, "player_12"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "player_14"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "player_16"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "player_15"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("directions_eisley"))
        {
            string_id message = new string_id(CONVO, "add_to_radar");
            makeWaypoint(mos_eisley, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("directions_espa"))
        {
            string_id message = new string_id(CONVO, "add_to_radar");
            makeWaypoint(mos_espa, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("directions_anchorhead"))
        {
            string_id message = new string_id(CONVO, "add_to_radar");
            makeWaypoint(anchorhead, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("directions_bestine"))
        {
            string_id message = new string_id(CONVO, "add_to_radar");
            makeWaypoint(bestine, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("bank"))
        {
            string_id message = new string_id(CONVO, "bank_city");
            npcSpeak(player, message);
            npcAddConversationResponse(player, new string_id(CONVO, "bank_eisley"));
            npcAddConversationResponse(player, new string_id(CONVO, "bank_espa"));
            npcAddConversationResponse(player, new string_id(CONVO, "bank_bestine"));
            npcAddConversationResponse(player, new string_id(CONVO, "bank_anchorhead"));
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
            npcAddConversationResponse(player, new string_id(CONVO, "cantina_eisley"));
            npcAddConversationResponse(player, new string_id(CONVO, "cantina_espa"));
            npcAddConversationResponse(player, new string_id(CONVO, "cantina_bestine"));
            npcAddConversationResponse(player, new string_id(CONVO, "cantina_anchorhead"));
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
            npcAddConversationResponse(player, new string_id(CONVO, "trainer_eisley"));
            npcAddConversationResponse(player, new string_id(CONVO, "trainer_espa"));
            npcAddConversationResponse(player, new string_id(CONVO, "trainer_bestine"));
            npcAddConversationResponse(player, new string_id(CONVO, "trainer_anchorhead"));
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
            npcAddConversationResponse(player, new string_id(CONVO, "starport_eisley"));
            npcAddConversationResponse(player, new string_id(CONVO, "starport_espa"));
            npcAddConversationResponse(player, new string_id(CONVO, "starport_bestine"));
            npcAddConversationResponse(player, new string_id(CONVO, "starport_anchorhead"));
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
            npcAddConversationResponse(player, new string_id(CONVO, "cloning_facility_eisley"));
            npcAddConversationResponse(player, new string_id(CONVO, "cloning_facility_espa"));
            npcAddConversationResponse(player, new string_id(CONVO, "cloning_facility_bestine"));
            npcAddConversationResponse(player, new string_id(CONVO, "cloning_facility_anchorhead"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "bank"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "cantina"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "trainer"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "starport"));
            npcAddConversationResponse(player, new string_id(CONVO, "player_12"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("bank_eisley"))
        {
            string_id message = new string_id(CONVO, "add_to_radar");
            makeWaypoint(bank_eisley, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("bank_espa"))
        {
            string_id message = new string_id(CONVO, "add_to_radar");
            makeWaypoint(bank_espa, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("bank_anchorhead"))
        {
            string_id message = new string_id(CONVO, "add_to_radar");
            makeWaypoint(bank_anchorhead, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("bank_bestine"))
        {
            string_id message = new string_id(CONVO, "add_to_radar");
            makeWaypoint(bank_bestine, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("cantina_eisley"))
        {
            string_id message = new string_id(CONVO, "add_to_radar");
            makeWaypoint(cantina_eisley, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("cantina_espa"))
        {
            string_id message = new string_id(CONVO, "add_to_radar");
            makeWaypoint(cantina_espa, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("cantina_anchorhead"))
        {
            string_id message = new string_id(CONVO, "add_to_radar");
            makeWaypoint(cantina_anchorhead, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("cantina_bestine"))
        {
            string_id message = new string_id(CONVO, "add_to_radar");
            makeWaypoint(cantina_bestine, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("trainer_eisley"))
        {
            string_id message = new string_id(CONVO, "which_trainer");
            makeWaypoint(trainer_eisley, player);
            npcSpeak(player, message);
            npcAddConversationResponse(player, new string_id(CONVO, "university_trainer_eisley"));
            npcAddConversationResponse(player, new string_id(CONVO, "crafting_trainer_eisley"));
            npcAddConversationResponse(player, new string_id(CONVO, "combat_trainer_eisley"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "trainer_eisley"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "trainer_espa"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "trainer_bestine"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "trainer_anchorhead"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("trainer_espa"))
        {
            string_id message = new string_id(CONVO, "which_trainer");
            makeWaypoint(trainer_espa, player);
            npcSpeak(player, message);
            npcAddConversationResponse(player, new string_id(CONVO, "university_trainer_espa"));
            npcAddConversationResponse(player, new string_id(CONVO, "crafting_trainer_espa"));
            npcAddConversationResponse(player, new string_id(CONVO, "combat_trainer_espa"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "trainer_eisley"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "trainer_espa"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "trainer_bestine"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "trainer_anchorhead"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("trainer_anchorhead"))
        {
            string_id message = new string_id(CONVO, "which_trainer");
            makeWaypoint(trainer_anchorhead, player);
            npcSpeak(player, message);
            npcAddConversationResponse(player, new string_id(CONVO, "university_trainer_anchorhead"));
            npcAddConversationResponse(player, new string_id(CONVO, "crafting_trainer_anchorhead"));
            npcAddConversationResponse(player, new string_id(CONVO, "combat_trainer_anchorhead"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "trainer_eisley"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "trainer_espa"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "trainer_bestine"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "trainer_anchorhead"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("trainer_bestine"))
        {
            string_id message = new string_id(CONVO, "which_trainer");
            makeWaypoint(trainer_bestine, player);
            npcSpeak(player, message);
            npcAddConversationResponse(player, new string_id(CONVO, "university_trainer_bestine"));
            npcAddConversationResponse(player, new string_id(CONVO, "crafting_trainer_bestine"));
            npcAddConversationResponse(player, new string_id(CONVO, "combat_trainer_bestine"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "trainer_eisley"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "trainer_espa"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "trainer_bestine"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "trainer_anchorhead"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("cloning_facility_eisley"))
        {
            string_id message = new string_id(CONVO, "add_to_radar");
            makeWaypoint(cloning_facility_eisley, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("cloning_facility_espa"))
        {
            string_id message = new string_id(CONVO, "add_to_radar");
            makeWaypoint(cloning_facility_espa, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("cloning_facility_anchorhead"))
        {
            string_id message = new string_id(CONVO, "add_to_radar");
            makeWaypoint(cloning_facility_anchorhead, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("cloning_facility_bestine"))
        {
            string_id message = new string_id(CONVO, "add_to_radar");
            makeWaypoint(cloning_facility_bestine, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("starport_eisley"))
        {
            string_id message = new string_id(CONVO, "add_to_radar");
            makeWaypoint(starport_eisley, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("starport_espa"))
        {
            string_id message = new string_id(CONVO, "add_to_radar");
            makeWaypoint(starport_espa, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("starport_anchorhead"))
        {
            string_id message = new string_id(CONVO, "add_to_radar");
            makeWaypoint(starport_anchorhead, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("starport_bestine"))
        {
            string_id message = new string_id(CONVO, "add_to_radar");
            makeWaypoint(starport_bestine, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("find_jabba"))
        {
            string_id message = new string_id(CONVO, "add_to_radar");
            makeWaypoint(jabba, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("find_talmont"))
        {
            string_id message = new string_id(CONVO, "add_to_radar");
            makeWaypoint(talmont, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("find_dera"))
        {
            string_id message = new string_id(CONVO, "add_to_radar");
            makeWaypoint(dera, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("find_trainer"))
        {
            string_id message = new string_id(CONVO, "trainers_loc");
            npcSpeak(player, message);
            npcAddConversationResponse(player, new string_id(CONVO, "trainer_bestine"));
            npcAddConversationResponse(player, new string_id(CONVO, ""));
            npcAddConversationResponse(player, new string_id(CONVO, ""));
            npcAddConversationResponse(player, new string_id(CONVO, ""));
            npcRemoveConversationResponse(player, new string_id(CONVO, "find_dera"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "find_talmont"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "find_jabba"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "find_trainer"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("combat_trainer_eisley"))
        {
            string_id message = new string_id(CONVO, "add_to_radar");
            makeWaypoint(combat_trainer_eisley, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("crafting_trainer_eisley"))
        {
            string_id message = new string_id(CONVO, "add_to_radar");
            makeWaypoint(crafting_trainer_eisley, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("university_trainer_eisley"))
        {
            string_id message = new string_id(CONVO, "add_to_radar");
            makeWaypoint(university_trainer_eisley, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("combat_trainer_espa"))
        {
            string_id message = new string_id(CONVO, "add_to_radar");
            makeWaypoint(combat_trainer_espa, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("crafting_trainer_espa"))
        {
            string_id message = new string_id(CONVO, "add_to_radar");
            makeWaypoint(crafting_trainer_espa, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("university_trainer_espa"))
        {
            string_id message = new string_id(CONVO, "add_to_radar");
            makeWaypoint(university_trainer_espa, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("combat_trainer_bestine"))
        {
            string_id message = new string_id(CONVO, "add_to_radar");
            makeWaypoint(combat_trainer_bestine, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("crafting_trainer_bestine"))
        {
            string_id message = new string_id(CONVO, "add_to_radar");
            makeWaypoint(crafting_trainer_bestine, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("university_trainer_bestine"))
        {
            string_id message = new string_id(CONVO, "add_to_radar");
            makeWaypoint(university_trainer_bestine, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("combat_trainer_eisley"))
        {
            string_id message = new string_id(CONVO, "add_to_radar");
            makeWaypoint(combat_trainer_eisley, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("crafting_trainer_eisley"))
        {
            string_id message = new string_id(CONVO, "add_to_radar");
            makeWaypoint(crafting_trainer_eisley, player);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("university_trainer_eisley"))
        {
            string_id message = new string_id(CONVO, "add_to_radar");
            makeWaypoint(university_trainer_eisley, player);
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
