package script.theme_park.nym;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.money;

public class pirate_engineer extends script.base_script
{
    public pirate_engineer()
    {
    }
    public static final String CONVO = "celebrity/lok_engineer";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setName(self, "Choster (Retired Engineer)");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        attachScript(self, "npc.converse.npc_converse_menu");
        setInvulnerable(self, true);
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id speaker) throws InterruptedException
    {
        if (!hasObjVar(speaker, "nym.jinkins"))
        {
            string_id greeting = new string_id(CONVO, "hello");
            string_id response[] = new string_id[1];
            response[0] = new string_id(CONVO, "who_are_you_2");
            npcStartConversation(speaker, self, "celebConvo", greeting, response);
        }
        if (hasObjVar(speaker, "nym.engineer.who"))
        {
            string_id greeting = new string_id(CONVO, "more_questions");
            string_id response[] = new string_id[1];
            response[0] = new string_id(CONVO, "ask_about_defenses");
            npcStartConversation(speaker, self, "celebConvo", greeting, response);
        }
        else 
        {
            string_id greeting = new string_id(CONVO, "hello");
            string_id response[] = new string_id[2];
            response[0] = new string_id(CONVO, "jinkins_sent_me");
            response[1] = new string_id(CONVO, "who_are_you");
            npcStartConversation(speaker, self, "celebConvo", greeting, response);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String convo, obj_id speaker, string_id response) throws InterruptedException
    {
        if ((response.getAsciiId()).equals("who_are_you"))
        {
            string_id message = new string_id(CONVO, "just_a_guy");
            npcSpeak(speaker, message);
            npcRemoveConversationResponse(speaker, new string_id(CONVO, "who_are_you"));
            setObjVar(speaker, "nym.engineer.who", 1);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("who_are_you_2"))
        {
            string_id message = new string_id(CONVO, "go_away_dude");
            npcSpeak(speaker, message);
            npcEndConversation(speaker);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("jinkins_sent_me"))
        {
            string_id message = new string_id(CONVO, "i_know_jinkins");
            npcSpeak(speaker, message);
            npcRemoveConversationResponse(speaker, new string_id(CONVO, "jinkins_sent_me"));
            npcRemoveConversationResponse(speaker, new string_id(CONVO, "who_are_you"));
            npcAddConversationResponse(speaker, new string_id(CONVO, "talk_pirates"));
            npcAddConversationResponse(speaker, new string_id(CONVO, "talk_engineer"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("talk_pirates"))
        {
            string_id message = new string_id(CONVO, "sulfur_lake_pirates");
            npcSpeak(speaker, message);
            npcRemoveConversationResponse(speaker, new string_id(CONVO, "talk_pirates"));
            npcAddConversationResponse(speaker, new string_id(CONVO, "hate_pirates"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("talk_engineer"))
        {
            string_id message = new string_id(CONVO, "security_system");
            npcSpeak(speaker, message);
            npcRemoveConversationResponse(speaker, new string_id(CONVO, "talk_engineer"));
            npcAddConversationResponse(speaker, new string_id(CONVO, "defenses"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("hate_pirates"))
        {
            string_id message = new string_id(CONVO, "pirates_fired");
            npcSpeak(speaker, message);
            npcRemoveConversationResponse(speaker, new string_id(CONVO, "hate_pirates"));
            npcAddConversationResponse(speaker, new string_id(CONVO, "i_will_help"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("defenses"))
        {
            string_id message = new string_id(CONVO, "which_defense");
            npcSpeak(speaker, message);
            npcRemoveConversationResponse(speaker, new string_id(CONVO, "defenses"));
            npcAddConversationResponse(speaker, new string_id(CONVO, "color_defense"));
            npcAddConversationResponse(speaker, new string_id(CONVO, "math_defense"));
            npcAddConversationResponse(speaker, new string_id(CONVO, "other_defense"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("ask_about_defenses"))
        {
            string_id message = new string_id(CONVO, "which_defense_2");
            npcSpeak(speaker, message);
            npcRemoveConversationResponse(speaker, new string_id(CONVO, "ask_about_defenses"));
            npcAddConversationResponse(speaker, new string_id(CONVO, "color_defense"));
            npcAddConversationResponse(speaker, new string_id(CONVO, "math_defense"));
            npcAddConversationResponse(speaker, new string_id(CONVO, "other_defense"));
            npcAddConversationResponse(speaker, new string_id(CONVO, "i_will_help"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("color_defense"))
        {
            string_id message = new string_id(CONVO, "pirate_artist");
            npcSpeak(speaker, message);
            npcRemoveConversationResponse(speaker, new string_id(CONVO, "color_defense"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("math_defense"))
        {
            string_id message = new string_id(CONVO, "sequential_numbers");
            npcSpeak(speaker, message);
            npcRemoveConversationResponse(speaker, new string_id(CONVO, "math_defense"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("other_defense"))
        {
            string_id message = new string_id(CONVO, "pirate_joker");
            npcSpeak(speaker, message);
            npcRemoveConversationResponse(speaker, new string_id(CONVO, "other_defense"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("i_will_help"))
        {
            string_id message = new string_id(CONVO, "good_luck");
            npcSpeak(speaker, message);
            npcEndConversation(speaker);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
}
