package script.theme_park.poi.general.conversation;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.money;
import script.ai.ai_combat;
import script.library.chat;

public class duelist_convo extends script.base_script
{
    public duelist_convo()
    {
    }
    public static final String CONVO = "npc_reaction/duelist";
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
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        if (hasObjVar(killer, "isDueling"))
        {
            setObjVar(killer, "duelwon", 1);
            removeObjVar(killer, "isDueling");
            return SCRIPT_CONTINUE;
        }
        else 
        {
            return SCRIPT_CONTINUE;
        }
    }
    public int OnStartNpcConversation(obj_id self, obj_id speaker) throws InterruptedException
    {
        if (hasObjVar(speaker, "duelwon"))
        {
            chat.chat(speaker, new string_id(CONVO, "duel_6"));
            npcEndConversation(speaker);
            money.bankTo(money.ACCT_POI, speaker, 300);
            removeObjVar(speaker, "duelwon");
            return SCRIPT_CONTINUE;
        }
        else 
        {
            string_id greeting = new string_id(CONVO, "duel_1");
            string_id response[] = new string_id[2];
            response[0] = new string_id(CONVO, "player_1");
            response[1] = new string_id(CONVO, "player_2");
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
            string_id message = new string_id(CONVO, "duel_2");
            npcSpeak(player, message);
            string_id responses[] = new string_id[2];
            responses[0] = new string_id(CONVO, "player_3");
            responses[1] = new string_id(CONVO, "player_4");
            npcSetConversationResponses(player, responses);
            return SCRIPT_CONTINUE;
        }
        if (((response.getAsciiId()).equals("player_2")))
        {
            string_id message = new string_id(CONVO, "duel_3");
            npcSpeak(player, message);
            messageTo(self, "startDuel", null, 20, true);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if (((response.getAsciiId()).equals("player_3")))
        {
            string_id message = new string_id(CONVO, "duel_4");
            npcSpeak(player, message);
            string_id responses[] = new string_id[2];
            responses[0] = new string_id(CONVO, "player_5");
            responses[1] = new string_id(CONVO, "player_6");
            npcSetConversationResponses(player, responses);
            return SCRIPT_CONTINUE;
        }
        if (((response.getAsciiId()).equals("player_4")))
        {
            string_id message = new string_id(CONVO, "duel_3");
            npcSpeak(player, message);
            messageTo(self, "startDuel", null, 20, true);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if (((response.getAsciiId()).equals("player_5")))
        {
            string_id message = new string_id(CONVO, "duel_3");
            npcSpeak(player, message);
            messageTo(self, "startDuel", null, 20, true);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if (((response.getAsciiId()).equals("player_6")))
        {
            string_id message = new string_id(CONVO, "duel_5");
            npcSpeak(player, message);
            setObjVar(self, "player", player);
            setObjVar(player, "isDueling", 1);
            messageTo(self, "duelPlayer", null, 10, true);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int startDuel(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id opponent = getObjIdObjVar(self, "opponent");
        startCombat(self, opponent);
        startCombat(opponent, self);
        return SCRIPT_CONTINUE;
    }
    public int duelPlayer(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id opponent = getObjIdObjVar(self, "opponent");
        obj_id player = getObjIdObjVar(self, "player");
        startCombat(opponent, player);
        return SCRIPT_CONTINUE;
    }
}
