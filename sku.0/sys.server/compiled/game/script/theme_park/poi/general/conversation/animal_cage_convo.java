package script.theme_park.poi.general.conversation;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.chat;

public class animal_cage_convo extends script.base_script
{
    public animal_cage_convo()
    {
    }
    public static final String CONVO = "npc_reaction/animal_cage";
    public static final String STF_FILE = "npc_reaction/animal_cage";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        attachScript(self, "npc.converse.npc_converse_menu");
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        attachScript(self, "npc.converse.npc_converse_menu");
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id speaker) throws InterruptedException
    {
        if (hasObjVar(speaker, "squillKilled"))
        {
            removeObjVar(speaker, "squillKilled");
            removeObjVar(speaker, "squill");
            chat.chat(self, new string_id(STF_FILE, "animal_cage"));
            obj_id inv = utils.getInventoryContainer(speaker);
            obj_id med = createObject("object/tangible/medicine/medpack_damage.iff", inv, "");
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "scientist"))
        {
            string_id greeting = new string_id(CONVO, "scientist_1");
            string_id response[] = new string_id[2];
            response[0] = new string_id(CONVO, "player_1");
            response[1] = new string_id(CONVO, "player_2");
            npcStartConversation(speaker, self, CONVO, greeting, response);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            string_id greeting = new string_id(CONVO, "npc_1");
            string_id response[] = new string_id[1];
            response[0] = new string_id(CONVO, "player_3");
            npcStartConversation(speaker, self, CONVO, greeting, response);
            return SCRIPT_CONTINUE;
        }
    }
    public int OnNpcConversationResponse(obj_id self, String convoName, obj_id player, string_id response) throws InterruptedException
    {
        if (!convoName.equals(CONVO))
        {
            return SCRIPT_CONTINUE;
        }
        if (((response.getAsciiId()).equals("player_1")))
        {
            string_id message = new string_id(CONVO, "squill");
            npcSpeak(player, message);
            string_id responses[] = new string_id[2];
            responses[0] = new string_id(CONVO, "player_3");
            responses[1] = new string_id(CONVO, "player_4");
            npcSetConversationResponses(player, responses);
            return SCRIPT_CONTINUE;
        }
        if (((response.getAsciiId()).equals("player_2")))
        {
            string_id message = new string_id(CONVO, "scientist_2");
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if (((response.getAsciiId()).equals("player_3")))
        {
            string_id message = new string_id(CONVO, "scientist_3");
            npcSpeak(player, message);
            string_id responses[] = new string_id[2];
            responses[0] = new string_id(CONVO, "player_2");
            responses[1] = new string_id(CONVO, "player_4");
            npcSetConversationResponses(player, responses);
            return SCRIPT_CONTINUE;
        }
        if (((response.getAsciiId()).equals("player_4")))
        {
            string_id message = new string_id(CONVO, "scientist_4");
            npcSpeak(player, message);
            string_id responses[] = new string_id[2];
            responses[0] = new string_id(CONVO, "player_2");
            responses[1] = new string_id(CONVO, "player_5");
            npcSetConversationResponses(player, responses);
            return SCRIPT_CONTINUE;
        }
        if (((response.getAsciiId()).equals("player_5")))
        {
            string_id message = new string_id(CONVO, "scientist_6");
            npcSpeak(player, message);
            npcEndConversation(player);
            attachScript(player, "theme_park.poi.general.special.spawn_squill");
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
}
