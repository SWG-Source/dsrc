package script.poi.factoryliberation;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.npc;
import script.library.ai_lib;
import script.library.poi;
import script.library.scenario;
import script.library.utils;
import script.library.combat;
import script.library.chat;
import script.ai.ai_combat;

public class antagonist extends script.poi.base.scenario_actor
{
    public antagonist()
    {
    }
    public static final String SCRIPT_CONVERSE = "npc.converse.npc_converse_menu";
    public static final String LOG_NAME = "poiFactoryLiberation Antagonist";
    public static final int CONV_GREET = 0;
    public static final int CONV_INSULT = 1;
    public static final int CONV_MAYBEHELP = 2;
    public static final int CONV_CONSULTMEDIATOR = 3;
    public static final int CONV_NOHELP = 4;
    public static final int CONV_YESHELP = 5;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        attachScript(self, SCRIPT_CONVERSE);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        attachScript(self, SCRIPT_CONVERSE);
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id speaker) throws InterruptedException
    {
        if (ai_lib.isInCombat(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id poiMaster = poi.getBaseObject(self);
        if ((poiMaster == null) || (poiMaster == obj_id.NULL_ID))
        {
            npcEndConversation(speaker);
            return SCRIPT_CONTINUE;
        }
        String convo = scenario.getConvo();
        if (convo.equals(""))
        {
            npcEndConversation(speaker);
            return SCRIPT_CONTINUE;
        }
        int progress = scenario.getPlayerProgress(speaker);
        boolean isLeader = poi.isNpcTagged(self, scenario.ANTAGONIST + "_0");
        if (!isLeader)
        {
            minionChatter(self, convo, speaker, poiMaster);
            return SCRIPT_CONTINUE;
        }
        string_id msg = new string_id(convo, "a_greet");
        Vector responses = new Vector();
        responses.setSize(0);
        switch (progress)
        {
            case CONV_GREET:
            msg = new string_id(convo, "a_greet");
            responses = utils.addElement(responses, new string_id(convo, "r_greet_maybehelp"));
            responses = utils.addElement(responses, new string_id(convo, "r_greet_insult"));
            npcStartConversation(speaker, self, convo, msg, responses);
            scenario.setPlayerProgress(speaker, 1);
            break;
            case CONV_INSULT:
            scenario.say(self, convo, "a_attackafterinsult", false);
            npcEndConversation(speaker);
            startCombat(self, speaker);
            return SCRIPT_CONTINUE;
            case CONV_MAYBEHELP:
            scenario.say(self, convo, "a_angryaftermaybehelp", false);
            npcEndConversation(speaker);
            scenario.setPlayerProgress(speaker, CONV_INSULT);
            break;
            case CONV_NOHELP:
            scenario.say(self, convo, "a_nohelp", false);
            npcEndConversation(speaker);
            scenario.setPlayerProgress(speaker, CONV_INSULT);
            break;
            case CONV_YESHELP:
            scenario.say(self, convo, "a_yeshelp", false);
            npcEndConversation(speaker);
            scenario.setPlayerProgress(speaker, CONV_INSULT);
            break;
        }
        return SCRIPT_CONTINUE;
    }
    public void minionChatter(obj_id self, String convo, obj_id speaker, obj_id poiMaster) throws InterruptedException
    {
        int idx = rand(1, 4);
        poi.quickSay(self, "a_minion_" + idx);
        npcEndConversation(speaker);
    }
    public int OnNpcConversationResponse(obj_id self, String convoName, obj_id speaker, string_id response) throws InterruptedException
    {
        if (ai_lib.isInCombat(self))
        {
            return SCRIPT_CONTINUE;
        }
        String convo = scenario.getConvo();
        if (convo.equals(""))
        {
            npcEndConversation(speaker);
            return SCRIPT_CONTINUE;
        }
        if (!convoName.equals(convo))
        {
            return SCRIPT_CONTINUE;
        }
        int progress = scenario.getPlayerProgress(speaker);
        boolean isLeader = poi.isNpcTagged(self, scenario.ANTAGONIST + "_0");
        if (!isLeader)
        {
            return SCRIPT_CONTINUE;
        }
        String aId = response.getAsciiId();
        string_id[] responses = new string_id[0];
        npcSetConversationResponses(speaker, responses);
        if ((aId.equals("r_greet_maybehelp")))
        {
            npcSpeak(speaker, new string_id(convo, "a_rejecthelp"));
            scenario.setPlayerProgress(speaker, CONV_MAYBEHELP);
        }
        else if ((aId.equals("r_greet_insult")))
        {
            npcSpeak(speaker, new string_id(convo, "a_insult"));
            scenario.setPlayerProgress(speaker, CONV_INSULT);
        }
        return SCRIPT_CONTINUE;
    }
    public int defendWall(obj_id self, dictionary params) throws InterruptedException
    {
        poi.quickSay(self, chat.CHAT_SHOUT, chat.MOOD_ANGRY, "a_defendwall");
        return SCRIPT_CONTINUE;
    }
}
