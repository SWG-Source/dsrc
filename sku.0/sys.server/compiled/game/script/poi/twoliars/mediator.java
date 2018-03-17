package script.poi.twoliars;

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
import script.library.poi;
import script.library.utils;
import script.library.prose;
import script.ai.ai_combat;

public class mediator extends script.poi.base.scenario_actor
{
    public mediator()
    {
    }
    public static final String SCRIPT_CONVERSE = "npc.converse.npc_converse_menu";
    public static final String LOG_NAME = "poiTwoLiars Mediator";
    public static final int CONV_GREET = 0;
    public static final int CONV_LOOKING = 1;
    public static final int CONV_LOSS = 2;
    public static final int CONV_WIN = 3;
    public static final String agitateEmotes[] = 
    {
        "scratch",
        "yawn",
        "fidget",
        "cough"
    };
    public int OnAttach(obj_id self) throws InterruptedException
    {
        attachScript(self, SCRIPT_CONVERSE);
        messageTo(self, "doAgitation", null, rand(40, 60), true);
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
        int m_index = getIntObjVar(self, "m_index");
        int s_index = getIntObjVar(self, "s_index");
        switch (progress)
        {
            case CONV_GREET:
            case CONV_LOSS:
            poi.quickSay(self, "m_chatter_" + s_index);
            npcEndConversation(speaker);
            break;
            case CONV_WIN:
            poi.quickSay(self, "m_chatter_v_" + rand(0, 2));
            npcEndConversation(speaker);
            break;
            case CONV_LOOKING:
            msg = new string_id(convo, "m_chatter_" + s_index);
            responses = utils.addElement(responses, new string_id(convo, "r_m_chatter_knowabouttheft"));
            npcStartConversation(speaker, self, convo, msg, responses);
            break;
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
        int progress = scenario.getPlayerProgress(speaker);
        String aId = response.getAsciiId();
        string_id[] responses = new string_id[0];
        npcSetConversationResponses(speaker, responses);
        if ((aId.equals("r_m_chatter_knowabouttheft")))
        {
            int m_index = getIntObjVar(self, "m_index");
            string_id lineId = new string_id(convo, "prose_m_line_" + m_index);
            prose_package pp;
            switch (m_index)
            {
                case 0:
                npcSpeak(speaker, lineId);
                break;
                case 1:
                pp = prose.getPackage(lineId, getLiarName(5));
                npcSpeak(speaker, pp);
                break;
                case 2:
                pp = prose.getPackage(lineId, getLiarName(1), getLiarName(3));
                npcSpeak(speaker, pp);
                break;
                case 3:
                pp = prose.getPackage(lineId, getLiarName(1), getLiarName(2));
                npcSpeak(speaker, pp);
                break;
                case 4:
                pp = prose.getPackage(lineId, getLiarName(3), getLiarName(1), getLiarName(2));
                npcSpeak(speaker, pp);
                break;
                case 5:
                pp = prose.getPackage(lineId, getLiarName(4));
                npcSpeak(speaker, pp);
                break;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public String getLiarName(int liarIndex) throws InterruptedException
    {
        obj_id self = getSelf();
        obj_id poiMaster = poi.getBaseObject(self);
        if ((poiMaster == null) || (poiMaster == obj_id.NULL_ID))
        {
            return "";
        }
        return getStringObjVar(poiMaster, "m_" + liarIndex);
    }
    public int doAgitation(obj_id self, dictionary params) throws InterruptedException
    {
        if (rand(1, 5) == 3)
        {
            int whichsocial = rand(0, 3);
            queueCommand(self, (1780871594), null, agitateEmotes[whichsocial], COMMAND_PRIORITY_DEFAULT);
        }
        messageTo(self, "doAgitation", null, rand(40, 60), true);
        return SCRIPT_CONTINUE;
    }
}
