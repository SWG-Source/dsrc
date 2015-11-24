package script.conversation;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.utils;
import script.library.anims;
import script.library.chat;

public class biogenic_random extends script.base_script
{
    public biogenic_random()
    {
    }
    public static final String CONVO = "conversation/biogenic_random";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        attachScript(self, "npc.converse.npc_converse_menu");
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id speaker) throws InterruptedException
    {
        String mood = chat.MOOD_NONE;
        switch (rand(0, 8))
        {
            case 0:
            mood = chat.MOOD_NONE;
            break;
            case 1:
            mood = chat.MOOD_DEPRESSED;
            break;
            case 2:
            mood = chat.MOOD_DISMAYED;
            break;
            case 3:
            mood = chat.MOOD_DISTRACTED;
            break;
            case 4:
            mood = chat.MOOD_NERVOUS;
            break;
            case 5:
            mood = chat.MOOD_SCARED;
            break;
            case 6:
            mood = chat.MOOD_TERRIFIED;
            break;
            case 7:
            mood = chat.MOOD_PANICKED;
            break;
            case 8:
            mood = chat.MOOD_FEARFUL;
            break;
        }
        int phrase_num = rand(0, 20);
        string_id phrase = new string_id(CONVO, "phrase_" + phrase_num);
        chat.setChatMood(self, mood);
        chat.chat(self, chat.CHAT_SAY, mood, phrase);
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String convo, obj_id speaker, string_id response) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
}
