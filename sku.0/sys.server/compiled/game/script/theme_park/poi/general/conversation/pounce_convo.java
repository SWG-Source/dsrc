package script.theme_park.poi.general.conversation;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class pounce_convo extends script.base_script
{
    public pounce_convo()
    {
    }
    public static final String CONVO = "pounce_convo";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        detachScript(self, "theme_park.poi.general.behavior.terrified_dialog");
        attachScript(self, "npc.converse.npc_converse_menu");
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        detachScript(self, "theme_park.poi.general.behavior.terrified_dialog");
        attachScript(self, "npc.converse.npc_converse_menu");
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id speaker) throws InterruptedException
    {
        if (hasObjVar(self, "leader"))
        {
            string_id greeting = new string_id(CONVO, "lead_1");
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
        if (((response.getAsciiId()).equals("player_3")))
        {
            string_id message = new string_id(CONVO, "npc_2");
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if (((response.getAsciiId()).equals("player_1")))
        {
            string_id message = new string_id(CONVO, "lead_2");
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if (((response.getAsciiId()).equals("player_2")))
        {
            string_id message = new string_id(CONVO, "lead_3");
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
}
