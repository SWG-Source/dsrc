package script.npc.epic_quest.zicx_bug_bomb;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.chat;
import script.library.utils;
import script.library.ai_lib;

public class rori_goru_rainstealer extends script.base_script
{
    public rori_goru_rainstealer()
    {
    }
    public static final String CONVO = "epic_quest/zicx_bug_bomb/rori_goru_rainstealer";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        attachScript(self, "npc.converse.npc_converse_menu");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        setInvulnerable(self, true);
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id speaker) throws InterruptedException
    {
        if (!hasObjVar(speaker, "zicx"))
        {
            String npcGreet = "OMG_Dont_kill_me";
            String response1 = "what_thugs";
            String response2 = "make_me_a_bomb";
            String response3 = "just_passing";
            string_id greeting = new string_id(CONVO, npcGreet);
            string_id response[] = new string_id[3];
            response[0] = new string_id(CONVO, response1);
            response[1] = new string_id(CONVO, response2);
            response[2] = new string_id(CONVO, response3);
            npcStartConversation(speaker, self, "questConvo", greeting, response);
        }
        else 
        {
            String zicx = getStringObjVar(speaker, "zicx");
            if (zicx.equals("reset"))
            {
                String npcGreet = "change_your_mind";
                String response1 = "yes_please";
                String response2 = "no_thanks";
                string_id greeting = new string_id(CONVO, npcGreet);
                string_id response[] = new string_id[2];
                response[0] = new string_id(CONVO, response1);
                response[1] = new string_id(CONVO, response2);
                npcStartConversation(speaker, self, "questConvo", greeting, response);
            }
            if (zicx.equals("start"))
            {
                String npcGreet = "are_you_done";
                String response1 = "need_more_time";
                String response2 = "i_quit";
                String response3 = "need_help";
                string_id greeting = new string_id(CONVO, npcGreet);
                string_id response[] = new string_id[3];
                response[0] = new string_id(CONVO, response1);
                response[1] = new string_id(CONVO, response2);
                response[2] = new string_id(CONVO, response3);
                npcStartConversation(speaker, self, "questConvo", greeting, response);
            }
            if (zicx.equals("bugs"))
            {
                String npcGreet = "get_the_bile";
                String response1 = "ill_get_bile";
                String response2 = "i_quit";
                string_id greeting = new string_id(CONVO, npcGreet);
                string_id response[] = new string_id[2];
                response[0] = new string_id(CONVO, response1);
                response[1] = new string_id(CONVO, response2);
                npcStartConversation(speaker, self, "questConvo", greeting, response);
            }
            if (zicx.equals("bile"))
            {
                String npcGreet = "get_the_bugs";
                String response1 = "ill_get_bugs";
                String response2 = "i_quit";
                string_id greeting = new string_id(CONVO, npcGreet);
                string_id response[] = new string_id[2];
                response[0] = new string_id(CONVO, response1);
                response[1] = new string_id(CONVO, response2);
                npcStartConversation(speaker, self, "questConvo", greeting, response);
            }
            if (zicx.equals("done"))
            {
                String npcGreet = "good_work";
                String response1 = "gimme_the_bomb";
                string_id greeting = new string_id(CONVO, npcGreet);
                string_id response[] = new string_id[1];
                response[0] = new string_id(CONVO, response1);
                npcStartConversation(speaker, self, "questConvo", greeting, response);
            }
            if (zicx.equals("complete"))
            {
                String npcGreet = "please_begone";
                string_id greet = new string_id(CONVO, npcGreet);
                chat.chat(self, greet);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String convo, obj_id player, string_id response) throws InterruptedException
    {
        String response1 = "what_thugs";
        String response2 = "make_me_a_bomb";
        String response3 = "yes_please";
        String response4 = "no_thanks";
        String response5 = "need_more_time";
        String response6 = "i_quit";
        String response7 = "ill_get_bile";
        String response8 = "ill_get_bugs";
        String response9 = "gimme_the_bomb";
        String response10 = "just_passing";
        String response11 = "where_is_jowir";
        String response12 = "sarlacc_bile";
        String response13 = "on_my_way";
        String response14 = "what_weapon";
        String response15 = "still_passing";
        String response16 = "sounds_good";
        String response17 = "need_help";
        if ((response.getAsciiId()).equals(response1))
        {
            String npcAnswer = "all_thugs";
            string_id message = new string_id(CONVO, npcAnswer);
            npcRemoveConversationResponse(player, new string_id(CONVO, "what_thugs"));
            npcSpeak(player, message);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals(response2))
        {
            String npcAnswer = "bombs_are_cool";
            string_id message = new string_id(CONVO, npcAnswer);
            npcRemoveConversationResponse(player, new string_id(CONVO, "what_thugs"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "make_me_a_bomb"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "just_passing"));
            npcAddConversationResponse(player, new string_id(CONVO, "where_is_jowir"));
            npcAddConversationResponse(player, new string_id(CONVO, "sarlacc_bile"));
            npcAddConversationResponse(player, new string_id(CONVO, "on_my_way"));
            npcSpeak(player, message);
            obj_id inventory = utils.getInventoryContainer(player);
            createObject("object/tangible/loot/quest/quest_item_goru_calling_card.iff", inventory, "");
            setObjVar(player, "zicx", "start");
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals(response3))
        {
            String npcAnswer = "bombs_are_cool";
            string_id message = new string_id(CONVO, npcAnswer);
            npcSpeak(player, message);
            obj_id inventory = utils.getInventoryContainer(player);
            createObject("object/tangible/loot/quest/quest_item_goru_calling_card.iff", inventory, "");
            setObjVar(player, "zicx", "start");
            npcAddConversationResponse(player, new string_id(CONVO, "where_is_jowir"));
            npcAddConversationResponse(player, new string_id(CONVO, "sarlacc_bile"));
            npcAddConversationResponse(player, new string_id(CONVO, "on_my_way"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "yes_please"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "no_thanks"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals(response4))
        {
            String npcAnswer = "go_away_then";
            string_id message = new string_id(CONVO, npcAnswer);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals(response5))
        {
            String npcAnswer = "hurry_up";
            string_id message = new string_id(CONVO, npcAnswer);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals(response6))
        {
            String npcAnswer = "fine_quit_then";
            string_id message = new string_id(CONVO, npcAnswer);
            npcSpeak(player, message);
            setObjVar(player, "zicx", "reset");
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals(response7))
        {
            String npcAnswer = "bring_me_the_bile";
            string_id message = new string_id(CONVO, npcAnswer);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals(response8))
        {
            String npcAnswer = "bring_me_the_bugs";
            string_id message = new string_id(CONVO, npcAnswer);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals(response9))
        {
            String npcAnswer = "heres_your_bomb";
            string_id message = new string_id(CONVO, npcAnswer);
            npcSpeak(player, message);
            obj_id inventory = utils.getInventoryContainer(player);
            createObject("object/weapon/ranged/grenade/grenade_bug_bomb.iff", inventory, "");
            setObjVar(player, "zicx", "complete");
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals(response10))
        {
            String npcAnswer = "dont_tell_anyone";
            string_id message = new string_id(CONVO, npcAnswer);
            npcAddConversationResponse(player, new string_id(CONVO, "what_weapon"));
            npcAddConversationResponse(player, new string_id(CONVO, "still_passing"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "just_passing"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "make_me_a_bomb"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "what_thugs"));
            npcSpeak(player, message);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals(response11))
        {
            String npcAnswer = "in_wayfar";
            string_id message = new string_id(CONVO, npcAnswer);
            npcSpeak(player, message);
            npcRemoveConversationResponse(player, new string_id(CONVO, "where_is_jowir"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals(response12))
        {
            String npcAnswer = "no_idea";
            string_id message = new string_id(CONVO, npcAnswer);
            npcSpeak(player, message);
            npcRemoveConversationResponse(player, new string_id(CONVO, "sarlacc_bile"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals(response13))
        {
            String npcAnswer = "good_luck";
            string_id message = new string_id(CONVO, npcAnswer);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals(response14))
        {
            String npcAnswer = "zicx_bomb";
            string_id message = new string_id(CONVO, npcAnswer);
            npcSpeak(player, message);
            npcRemoveConversationResponse(player, new string_id(CONVO, "what_weapon"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "still_passing"));
            npcAddConversationResponse(player, new string_id(CONVO, "sounds_good"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals(response15))
        {
            String npcAnswer = "dont_say_nothing";
            string_id message = new string_id(CONVO, npcAnswer);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals(response16))
        {
            String npcAnswer = "see_jowir";
            string_id message = new string_id(CONVO, npcAnswer);
            setObjVar(player, "zicx", "start");
            npcSpeak(player, message);
            npcRemoveConversationResponse(player, new string_id(CONVO, "sounds_good"));
            npcAddConversationResponse(player, new string_id(CONVO, "where_is_jowir"));
            npcAddConversationResponse(player, new string_id(CONVO, "on_my_way"));
            obj_id inventory = utils.getInventoryContainer(player);
            createObject("object/tangible/loot/quest/quest_item_goru_calling_card.iff", inventory, "");
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals(response17))
        {
            String npcAnswer = "more_info";
            string_id message = new string_id(CONVO, npcAnswer);
            npcSpeak(player, message);
            npcRemoveConversationResponse(player, new string_id(CONVO, "need_more_time"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "i_quit"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "need_help"));
            npcAddConversationResponse(player, new string_id(CONVO, "where_is_jowir"));
            npcAddConversationResponse(player, new string_id(CONVO, "sarlacc_bile"));
            npcAddConversationResponse(player, new string_id(CONVO, "on_my_way"));
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnGiveItem(obj_id self, obj_id item, obj_id player) throws InterruptedException
    {
        if (!hasObjVar(player, "zicx"))
        {
            debugSpeakMsg(self, "I don't know you.");
            return SCRIPT_OVERRIDE;
        }
        String name = getTemplateName(item);
        String zicx = getStringObjVar(player, "zicx");
        if (name.equals("object/tangible/loot/quest/quest_item_zicx_bug_jar.iff"))
        {
            if (zicx.equals("bile"))
            {
                string_id finishMessage = new string_id(CONVO, "give_a_minute");
                chat.chat(self, finishMessage);
                destroyObject(item);
                setObjVar(player, "zicx", "done");
            }
            else 
            {
                string_id finishMessage = new string_id(CONVO, "bring_me_the_bile");
                chat.chat(self, finishMessage);
                destroyObject(item);
                setObjVar(player, "zicx", "bugs");
            }
        }
        else if (name.equals("object/tangible/loot/quest/quest_item_sarlacc_bile_jar.iff"))
        {
            if (zicx.equals("bugs"))
            {
                string_id finishMessage = new string_id(CONVO, "give_a_minute");
                chat.chat(self, finishMessage);
                destroyObject(item);
                setObjVar(player, "zicx", "done");
            }
            else 
            {
                string_id finishMessage = new string_id(CONVO, "bring_me_the_bugs");
                chat.chat(self, finishMessage);
                destroyObject(item);
                setObjVar(player, "zicx", "bile");
            }
        }
        else 
        {
            string_id npcwhat = new string_id(CONVO, "what_is_this");
            chat.chat(self, npcwhat);
        }
        return SCRIPT_CONTINUE;
    }
}
