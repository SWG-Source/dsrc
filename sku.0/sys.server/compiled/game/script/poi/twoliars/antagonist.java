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
import script.library.group;
import script.library.badge;
import script.ai.ai_combat;

public class antagonist extends script.poi.base.scenario_actor
{
    public antagonist()
    {
    }
    public static final String SCRIPT_CONVERSE = "npc.converse.npc_converse_menu";
    public static final String LOG_NAME = "poiTwoLiars Antagonist";
    public static final int CONV_GREET = 0;
    public static final int CONV_LOOKING = 1;
    public static final int CONV_LOSS = 2;
    public static final int CONV_WIN = 3;
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
        if (scenario.isComplete())
        {
            int idx = rand(1, 2);
            poi.quickSay(self, "a_chatter_" + idx);
            npcEndConversation(speaker);
            return SCRIPT_CONTINUE;
        }
        switch (progress)
        {
            case CONV_GREET:
            msg = new string_id(convo, "a_greet");
            responses = utils.addElement(responses, new string_id(convo, "r_a_greet"));
            npcStartConversation(speaker, self, convo, msg, responses);
            break;
            case CONV_LOOKING:
            msg = new string_id(convo, "a_whoisit");
            responses = utils.addElement(responses, new string_id(convo, "r_a_ithinkso"));
            responses = utils.addElement(responses, new string_id(convo, "r_a_whoisit"));
            npcStartConversation(speaker, self, convo, msg, responses);
            break;
            case CONV_WIN:
            msg = new string_id(convo, "a_thanks");
            npcStartConversation(speaker, self, convo, msg, responses);
            break;
            case CONV_LOSS:
            msg = new string_id(convo, "a_notrust");
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
        if (!convoName.equals(convo))
        {
            return SCRIPT_CONTINUE;
        }
        int progress = scenario.getPlayerProgress(speaker);
        String aId = response.getAsciiId();
        Vector responses = new Vector();
        responses.setSize(0);
        npcSetConversationResponses(speaker, responses);
        if ((aId.equals("r_a_greet")))
        {
            npcSpeak(speaker, new string_id(convo, "a_lookingformark"));
            responses = utils.addElement(responses, new string_id(convo, "r_a_lookingformark"));
            npcSetConversationResponses(speaker, responses);
        }
        else if ((aId.equals("r_a_lookingformark")))
        {
            npcSpeak(speaker, new string_id(convo, "a_thatstheproblem"));
            responses = utils.addElement(responses, new string_id(convo, "r_a_thatstheproblem"));
            npcSetConversationResponses(speaker, responses);
        }
        else if ((aId.equals("r_a_thatstheproblem")))
        {
            npcSpeak(speaker, new string_id(convo, "a_clues"));
            responses = utils.addElement(responses, new string_id(convo, "r_a_clues"));
            npcSetConversationResponses(speaker, responses);
        }
        else if ((aId.equals("r_a_clues")))
        {
            npcSpeak(speaker, new string_id(convo, "a_imnotdumb"));
            responses = utils.addElement(responses, new string_id(convo, "r_a_imnotdumb"));
            npcSetConversationResponses(speaker, responses);
        }
        else if ((aId.equals("r_a_imnotdumb")))
        {
            npcSpeak(speaker, new string_id(convo, "a_questioned"));
            responses = utils.addElement(responses, new string_id(convo, "r_a_questioned"));
            npcSetConversationResponses(speaker, responses);
        }
        else if ((aId.equals("r_a_questioned")))
        {
            npcSpeak(speaker, new string_id(convo, "a_questioned2"));
            responses = utils.addElement(responses, new string_id(convo, "r_a_questioned2"));
            npcSetConversationResponses(speaker, responses);
        }
        else if ((aId.equals("r_a_questioned2")))
        {
            npcSpeak(speaker, new string_id(convo, "a_goahead"));
            scenario.setPlayerProgress(speaker, CONV_LOOKING);
        }
        else if ((aId.equals("r_a_whoisit")))
        {
            npcSpeak(speaker, new string_id(convo, "a_keeplooking"));
        }
        else if ((aId.equals("r_a_ithinkso")))
        {
            npcSpeak(speaker, new string_id(convo, "a_greatwho"));
            deltadictionary dctScriptVars = speaker.getScriptVars();
            prose_package ppa[] = new prose_package[6];
            int names[] = new int[6];
            for (int i = 0; i < 6; i++)
            {
                names[i] = i;
            }
            int j = 0;
            for (int i = 5; i >= 0; --i)
            {
                int index = rand(0, i);
                int name = names[index];
                names[index] = names[i];
                names[i] = -1;
                String liarName = getLiarName(poiMaster, name);
                dctScriptVars.put("poiTwoLiars.liars." + (j + 1), liarName);
                string_id str = new string_id(convo, "prose_implicate_" + j);
                ppa[j++] = prose.getPackage(str, liarName);
            }
            npcSetConversationResponses(speaker, ppa);
        }
        else 
        {
            deltadictionary dctScriptVars = speaker.getScriptVars();
            for (int i = 0; i < 6; i++)
            {
                String name = getStringObjVar(poiMaster, "m_" + i);
                String res = getString(response);
                String index = res.substring(0, 1);
                String liarName = dctScriptVars.getString("poiTwoLiars.liars." + index);
                if (liarName.equals(name))
                {
                    if (i == 3)
                    {
                        npcSpeak(speaker, new string_id(convo, "a_rightanswer"));
                        scenario.setPlayerProgress(speaker, CONV_WIN);
                        if (group.isGroupObject(speaker))
                        {
                            obj_id[] members = getGroupMemberIds(speaker);
                            if ((members == null) || (members.length == 0))
                            {
                            }
                            else 
                            {
                                for (int n = 0; n < members.length; n++)
                                {
                                    if (!badge.hasBadge(members[n], "poi_twoliars"))
                                    {
                                        badge.grantBadge(members[n], "poi_twoliars");
                                    }
                                    else 
                                    {
                                        badge.notifyHasBadge(members[n], "poi_twoliars");
                                    }
                                }
                            }
                        }
                        else 
                        {
                            if (!badge.hasBadge(speaker, "poi_twoliars"))
                            {
                                badge.grantBadge(speaker, "poi_twoliars");
                            }
                            else 
                            {
                                badge.notifyHasBadge(speaker, "poi_twoliars");
                            }
                        }
                        scenario.complete();
                        return SCRIPT_CONTINUE;
                    }
                    else 
                    {
                        npcSpeak(speaker, new string_id(convo, "a_wronganswer"));
                        scenario.setPlayerProgress(speaker, CONV_LOSS);
                        return SCRIPT_CONTINUE;
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public String getLiarName(obj_id poiMaster, int liarIndex) throws InterruptedException
    {
        return getStringObjVar(poiMaster, "m_" + liarIndex);
    }
}
