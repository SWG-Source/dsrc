package script.quest.hero_of_tatooine;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.chat;
import script.library.prose;
import script.library.utils;

public class intellect_liar extends script.base_script
{
    public intellect_liar()
    {
    }
    public static final String CONVO = "quest/hero_of_tatooine/intellect_liar";
    public static final String HERO_OF_TATOOINE_COMPLETE = "quest.hero_of_tatooine.complete";
    public static final String SPAWNER_OBJVAR = "quest.hero_of_tatooine.intellect.spawner";
    public static final String SPAWNER_CONTROLLER = SPAWNER_OBJVAR + ".controller";
    public static final String SPAWNER_NPC = SPAWNER_OBJVAR + ".npc";
    public static final String SPAWNER_ACTIVE = SPAWNER_OBJVAR + ".active";
    public static final String SPAWNER_LIARS = SPAWNER_ACTIVE + ".liars";
    public static final String INTELLECT_OBJVAR = "quest.hero_of_tatooine.intellect";
    public static final String INTELLECT_COMPLETE = INTELLECT_OBJVAR + ".complete";
    public static final String INTELLECT_IN_PROGRESS = INTELLECT_OBJVAR + ".in_progress";
    public static final String INTELLECT_FAILED = INTELLECT_OBJVAR + ".failed";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        attachScript(self, "npc.converse.npc_converse_menu");
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (!hasScript(self, "npc.converse.npc_converse_menu"))
        {
            attachScript(self, "npc.converse.npc_converse_menu");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id speaker) throws InterruptedException
    {
        int m = rand(0, 11);
        string_id msg = new string_id(CONVO, "greeting_" + m);
        if (hasObjVar(speaker, HERO_OF_TATOOINE_COMPLETE) || hasObjVar(speaker, INTELLECT_COMPLETE) || hasObjVar(speaker, INTELLECT_FAILED) || !utils.hasScriptVar(speaker, INTELLECT_IN_PROGRESS))
        {
            chat.chat(self, speaker, msg, chat.ChatFlag_targetOnly);
            messageTo(self, "handleResetYaw", null, 4.0f, false);
            return SCRIPT_CONTINUE;
        }
        string_id[] responses = new string_id[3];
        responses[0] = new string_id(CONVO, "response_0");
        responses[1] = new string_id(CONVO, "response_1");
        responses[2] = new string_id(CONVO, "response_2");
        npcStartConversation(speaker, self, CONVO, msg, responses);
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversation, obj_id speaker, string_id response) throws InterruptedException
    {
        if ((response.getAsciiId()).equals("response_0"))
        {
            int m = rand(0, 11);
            string_id msg = new string_id(CONVO, "equipment_" + m);
            npcSpeak(speaker, msg);
            string_id[] responses = new string_id[2];
            responses[0] = new string_id(CONVO, "response_1");
            responses[1] = new string_id(CONVO, "response_2");
            npcSetConversationResponses(speaker, responses);
            return SCRIPT_CONTINUE;
        }
        else if ((response.getAsciiId()).equals("response_1"))
        {
            int m = rand(0, 2);
            int n = getIntObjVar(self, SPAWNER_NPC);
            string_id msg = new string_id(CONVO, "others_" + n + "_" + m);
            prose_package pp = null;
            switch (n)
            {
                case 0:
                npcSpeak(speaker, msg);
                break;
                case 1:
                pp = prose.getPackage(msg, getLiarName(self, 5));
                npcSpeak(speaker, pp);
                break;
                case 2:
                pp = prose.getPackage(msg, getLiarName(self, 1), getLiarName(self, 3));
                npcSpeak(speaker, pp);
                break;
                case 3:
                pp = prose.getPackage(msg, getLiarName(self, 1), getLiarName(self, 2));
                npcSpeak(speaker, pp);
                break;
                case 4:
                pp = prose.getPackage(msg, getLiarName(self, 3), getLiarName(self, 1), getLiarName(self, 2));
                npcSpeak(speaker, pp);
                break;
                case 5:
                pp = prose.getPackage(msg, getLiarName(self, 4));
                npcSpeak(speaker, pp);
                break;
            }
            string_id[] responses = new string_id[2];
            responses[0] = new string_id(CONVO, "response_0");
            responses[1] = new string_id(CONVO, "response_2");
            npcSetConversationResponses(speaker, responses);
            return SCRIPT_CONTINUE;
        }
        else if ((response.getAsciiId()).equals("response_2"))
        {
            int m = rand(0, 11);
            string_id msg = new string_id(CONVO, "bye_" + m);
            npcSpeak(speaker, msg);
            npcEndConversation(speaker);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnEndNpcConversation(obj_id self, obj_id speaker) throws InterruptedException
    {
        messageTo(self, "handleResetYaw", null, 4.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int handleSetYaw(obj_id self, dictionary params) throws InterruptedException
    {
        location loc = params.getLocation("loc");
        faceTo(self, loc);
        setObjVar(self, "ai.yaw", getYaw(self));
        return SCRIPT_CONTINUE;
    }
    public int handleResetYaw(obj_id self, dictionary params) throws InterruptedException
    {
        if (isInNpcConversation(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, "ai.yaw"))
        {
            return SCRIPT_CONTINUE;
        }
        int yaw = getIntObjVar(self, "ai.yaw");
        setYaw(self, yaw);
        return SCRIPT_CONTINUE;
    }
    public String getLiarName(obj_id self, int number) throws InterruptedException
    {
        obj_id control = getObjIdObjVar(self, SPAWNER_CONTROLLER);
        if (!isIdValid(control))
        {
            return null;
        }
        obj_id[] liars = getObjIdArrayObjVar(control, SPAWNER_LIARS);
        if (liars == null || liars.length == 0)
        {
            debugSpeakMsg(self, "ARRAY OF LIARS IS NULL OR EMPTY!");
            return null;
        }
        if (!isIdValid(liars[number]))
        {
            debugSpeakMsg(self, "THE LIAR ID IS NOT VALID!");
            return null;
        }
        String name = getEncodedName(liars[number]);
        return name;
    }
}
