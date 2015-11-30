package script.poi.heromark;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.npc;
import script.library.ai_lib;
import script.library.scenario;
import script.library.badge;
import script.library.poi;
import script.library.utils;
import script.ai.ai_combat;

public class mediator extends script.poi.base.scenario_actor
{
    public mediator()
    {
    }
    public static final String SCRIPT_CONVERSE = "npc.converse.npc_converse_menu";
    public static final String LOG_NAME = "poiHeroMark Mediator";
    public static final int CONV_GREET = 0;
    public static final int CONV_SPOKE = 1;
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
        String convo = scenario.getConvo();
        if (convo.equals(""))
        {
            npcEndConversation(speaker);
            return SCRIPT_CONTINUE;
        }
        int progress = scenario.getPlayerProgress(speaker);
        string_id msg = new string_id(convo, "a_greet");
        Vector responses = new Vector();
        responses.setSize(0);
        if (badge.hasBadge(speaker, "poi_heromark"))
        {
            poi.quickSay(self, "m_heroreturned");
            npcEndConversation(speaker);
        }
        else if (progress == CONV_SPOKE)
        {
            int idx = rand(1, 4);
            poi.quickSay(self, "m_chatter_" + idx);
            npcEndConversation(speaker);
        }
        else 
        {
            if (badge.hasBadge(speaker, "poi_prisonbreak") && badge.hasBadge(speaker, "poi_factoryliberation") && badge.hasBadge(speaker, "poi_rabidbeast") && badge.hasBadge(speaker, "poi_twoliars"))
            {
                msg = new string_id(convo, "m_youareahero");
                responses = utils.addElement(responses, new string_id(convo, "r_m_relatestory"));
                npcStartConversation(speaker, self, convo, msg, responses);
            }
            else 
            {
                msg = new string_id(convo, "m_greet");
                responses = utils.addElement(responses, new string_id(convo, "r_m_greet"));
                npcStartConversation(speaker, self, convo, msg, responses);
            }
        }
        return SCRIPT_CONTINUE;
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
        String aId = response.getAsciiId();
        Vector responses = new Vector();
        responses.setSize(0);
        npcSetConversationResponses(speaker, responses);
        if ((aId.equals("r_m_greet")))
        {
            npcSpeak(speaker, new string_id(convo, "m_herotalk1"));
            responses = utils.addElement(responses, new string_id(convo, "r_m_herotalk1"));
            npcSetConversationResponses(speaker, responses);
        }
        else if ((aId.equals("r_m_herotalk1")))
        {
            npcSpeak(speaker, new string_id(convo, "m_herotalk2"));
            responses = utils.addElement(responses, new string_id(convo, "r_m_herotalk2"));
            npcSetConversationResponses(speaker, responses);
        }
        else if ((aId.equals("r_m_herotalk2")))
        {
            npcSpeak(speaker, new string_id(convo, "m_herotalk3"));
            responses = utils.addElement(responses, new string_id(convo, "r_m_herotalk3"));
            npcSetConversationResponses(speaker, responses);
        }
        else if ((aId.equals("r_m_herotalk3")))
        {
            npcSpeak(speaker, new string_id(convo, "m_herotalk4"));
            responses = utils.addElement(responses, new string_id(convo, "r_m_empirebad"));
            responses = utils.addElement(responses, new string_id(convo, "r_m_empiregood"));
            npcSetConversationResponses(speaker, responses);
        }
        else if ((aId.equals("r_m_empiregood")))
        {
            npcSpeak(speaker, new string_id(convo, "m_herotalk5"));
            scenario.setPlayerProgress(speaker, CONV_SPOKE);
        }
        else if ((aId.equals("r_m_empirebad")))
        {
            npcSpeak(speaker, new string_id(convo, "m_herotalk6"));
            responses = utils.addElement(responses, new string_id(convo, "r_m_herotalk6"));
            npcSetConversationResponses(speaker, responses);
        }
        else if ((aId.equals("r_m_herotalk6")))
        {
            npcSpeak(speaker, new string_id(convo, "m_herotalk7"));
            responses = utils.addElement(responses, new string_id(convo, "r_m_herotalk7"));
            npcSetConversationResponses(speaker, responses);
        }
        else if ((aId.equals("r_m_herotalk7")))
        {
            npcSpeak(speaker, new string_id(convo, "m_herotalk8"));
            responses = utils.addElement(responses, new string_id(convo, "r_m_herotalk8"));
            npcSetConversationResponses(speaker, responses);
        }
        else if ((aId.equals("r_m_herotalk8")))
        {
            npcSpeak(speaker, new string_id(convo, "m_herotalk9"));
            responses = utils.addElement(responses, new string_id(convo, "r_m_herotalk9"));
            npcSetConversationResponses(speaker, responses);
        }
        else if ((aId.equals("r_m_herotalk9")))
        {
            npcSpeak(speaker, new string_id(convo, "m_herotalk10"));
            responses = utils.addElement(responses, new string_id(convo, "r_m_herotalk10"));
            npcSetConversationResponses(speaker, responses);
        }
        else if ((aId.equals("r_m_herotalk10")))
        {
            npcSpeak(speaker, new string_id(convo, "m_herotalk11"));
            scenario.setPlayerProgress(speaker, CONV_SPOKE);
        }
        else if ((aId.equals("r_m_relatestory")))
        {
            npcSpeak(speaker, new string_id(convo, "m_youareahero2"));
            scenario.setPlayerProgress(speaker, CONV_SPOKE);
            badge.grantBadge(speaker, "poi_heromark");
            scenario.complete();
        }
        return SCRIPT_CONTINUE;
    }
}
