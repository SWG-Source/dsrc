package script.poi.rabidbeast;

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
import script.library.group;
import script.library.badge;
import script.library.utils;
import script.library.chat;
import script.ai.ai_combat;

public class mediator extends script.poi.base.scenario_actor
{
    public mediator()
    {
    }
    public static final String SCRIPT_CONVERSE = "npc.converse.npc_converse_menu";
    public static final String LOG_NAME = "poiRabidBeast Mediator";
    public static final int CONV_GREET = 0;
    public static final int CONV_SAVED = 1;
    public static final int CONV_HELP = 2;
    public static final int CONV_NOHELP = 3;
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
        string_id msg = new string_id(convo, "a_greet");
        Vector responses = new Vector();
        responses.setSize(0);
        if (scenario.isComplete())
        {
            int idx = rand(1, 4);
            poi.quickSay(self, "m_chatter_" + idx);
            npcEndConversation(speaker);
            return SCRIPT_CONTINUE;
        }
        else if (hasObjVar(self, "attacked"))
        {
            int idx = rand(1, 2);
            poi.quickSay(self, "m_help_" + idx);
            npcEndConversation(speaker);
            return SCRIPT_CONTINUE;
        }
        else if (hasObjVar(self, "rescued"))
        {
            if (scenario.hasKillCredit(poiMaster, "antagonist", speaker))
            {
                removeObjVar(self, "rescued");
                msg = new string_id(convo, "m_thanks");
                npcStartConversation(speaker, self, convo, msg, responses);
                scenario.complete();
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
                            if (scenario.hasKillCredit(poiMaster, "antagonist", members[n]))
                            {
                                if (!badge.hasBadge(members[n], "poi_rabidbeast"))
                                {
                                    badge.grantBadge(members[n], "poi_rabidbeast");
                                }
                                else 
                                {
                                    badge.notifyHasBadge(members[n], "poi_rabidbeast");
                                }
                            }
                        }
                    }
                }
                else 
                {
                    if (!badge.hasBadge(speaker, "poi_rabidbeast"))
                    {
                        badge.grantBadge(speaker, "poi_rabidbeast");
                    }
                    else 
                    {
                        badge.notifyHasBadge(speaker, "poi_rabidbeast");
                    }
                }
                return SCRIPT_CONTINUE;
            }
            else if (scenario.hasKillCredit(poiMaster, "antagonists", self))
            {
                removeObjVar(self, "rescued");
                msg = new string_id(convo, "m_ididallthework");
                npcStartConversation(speaker, self, convo, msg, responses);
                scenario.complete(poi.getBaseObject(self));
                return SCRIPT_CONTINUE;
            }
            int idx = rand(1, 4);
            poi.quickSay(self, "m_chatter_" + idx);
            npcEndConversation(speaker);
            return SCRIPT_CONTINUE;
        }
        msg = new string_id(convo, "m_greet");
        responses = utils.addElement(responses, new string_id(convo, "r_m_greet"));
        npcStartConversation(speaker, self, convo, msg, responses);
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
        String aId = response.getAsciiId();
        Vector responses = new Vector();
        responses.setSize(0);
        npcSetConversationResponses(speaker, responses);
        if ((aId.equals("r_m_greet")))
        {
            npcSpeak(speaker, new string_id(convo, "m_studying"));
            responses = utils.addElement(responses, new string_id(convo, "r_m_studying"));
            npcSetConversationResponses(speaker, responses);
        }
        else if ((aId.equals("r_m_studying")))
        {
            npcSpeak(speaker, new string_id(convo, "m_thebeast"));
            responses = utils.addElement(responses, new string_id(convo, "r_m_thebeast_help"));
            responses = utils.addElement(responses, new string_id(convo, "r_m_thebeast_nohelp"));
            npcSetConversationResponses(speaker, responses);
        }
        else if ((aId.equals("r_m_thebeast_help")))
        {
            npcEndConversation(speaker);
            poi.quickSay(self, "m_hereitcomes_help");
            setObjVar(self, "attacked", true);
            dictionary params = new dictionary();
            params.put("target", speaker);
            messageTo(poiMaster, "startAttack", params, 2, true);
        }
        else if ((aId.equals("r_m_thebeast_nohelp")))
        {
            npcEndConversation(speaker);
            poi.quickSay(self, "m_hereitcomes_nohelp");
            setObjVar(self, "attacked", true);
            dictionary params = new dictionary();
            params.put("target", speaker);
            messageTo(poiMaster, "startAttack", params, 2, true);
        }
        return SCRIPT_CONTINUE;
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
    public int allDead(obj_id self, dictionary params) throws InterruptedException
    {
        stop(self);
        queueCommand(self, (1780871594), null, "peptalk", COMMAND_PRIORITY_DEFAULT);
        messageTo(self, "resumeDefaultCalmBehavior", null, 3, false);
        poi.quickSay(self, chat.CHAT_EXCLAIM, chat.MOOD_EXUBERANT, "m_wesurvived");
        removeObjVar(self, "attacked");
        setObjVar(self, "rescued", true);
        return SCRIPT_CONTINUE;
    }
}
