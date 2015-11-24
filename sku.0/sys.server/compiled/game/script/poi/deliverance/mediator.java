package script.poi.deliverance;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.npc;
import script.library.poi;
import script.library.scenario;
import script.library.ai_lib;
import script.library.utils;
import script.library.combat;
import script.library.group;
import script.ai.ai_combat;

public class mediator extends script.theme_park.poi.base
{
    public mediator()
    {
    }
    public static final String SCRIPT_CONVERSE = "npc.converse.npc_converse_menu";
    public static final String VAR_RUNNING_SCENARIO = "running_scenario";
    public static final String HANDLER_TIMER = "handleTimer";
    public static final int TIMER_LENGTH = 300;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        attachScript(self, SCRIPT_CONVERSE);
        messageTo(self, HANDLER_TIMER, null, TIMER_LENGTH, true);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        attachScript(self, SCRIPT_CONVERSE);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        obj_id poiMaster = poi.getBaseObject(self);
        if ((poiMaster == null) || (poiMaster == obj_id.NULL_ID))
        {
            return SCRIPT_CONTINUE;
        }
        messageTo(self, scenario.HANDLER_MY_DEATH, null, 2, true);
        String myName = getStringObjVar(self, scenario.VAR_MY_NAME);
        if (!myName.equals(""))
        {
            dictionary d = new dictionary();
            d.put(scenario.DICT_NAME, myName);
            d.put(scenario.DICT_OBJID, self);
            messageTo(poiMaster, scenario.HANDLER_ACTOR_DEATH, d, 0, true);
        }
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
            LOG("poiDeliverance", "I have no idea what object I belong to!");
            npcEndConversation(speaker);
            return SCRIPT_CONTINUE;
        }
        String convo = getStringObjVar(poiMaster, scenario.VAR_SCENARIO_CONVO);
        if (convo.equals(""))
        {
            LOG("poiDeliverance", "I have no idea what I am supposed to say");
            npcEndConversation(speaker);
            return SCRIPT_CONTINUE;
        }
        if (scenario.getAliveTeamMemberCount(poiMaster, "antagonist") == 0)
        {
            string_id msg = new string_id();
            if (poi.isGrantedCredit(poiMaster, speaker))
            {
                scenario.say(self, convo, "m_thank_combat");
            }
            else 
            {
                scenario.say(self, convo, "m_saved");
            }
            npcEndConversation(speaker);
            return SCRIPT_CONTINUE;
        }
        int progress = scenario.getPlayerProgress(speaker);
        string_id msg = new string_id(convo, "a_greet");
        Vector responses = new Vector();
        responses.setSize(0);
        LOG("poiDeliverance", "your current progress = " + progress);
        switch (progress)
        {
            case 0:
            msg = new string_id(convo, "m_greet");
            responses = utils.addElement(responses, new string_id(convo, "response_yes"));
            responses = utils.addElement(responses, new string_id(convo, "response_no"));
            responses = utils.addElement(responses, new string_id(convo, "response_maybe"));
            scenario.setPlayerProgress(speaker, 1);
            break;
            case 1:
            scenario.say(self, convo, "m_rejected");
            npcEndConversation(speaker);
            int rollToAttack = rand(1, 5);
            switch (rollToAttack)
            {
                case 1:
                startCombat(self, speaker);
            }
            return SCRIPT_CONTINUE;
            case 2:
            case 3:
            scenario.say(self, convo, "m_waiting");
            npcEndConversation(speaker);
            return SCRIPT_CONTINUE;
            case 4:
            return SCRIPT_CONTINUE;
            case 5:
            scenario.say(self, convo, "m_betrayed");
            npcEndConversation(speaker);
            startCombat(self, speaker);
            return SCRIPT_CONTINUE;
            case 6:
            int rollNegotiate = rand(1, 4);
            switch (rollNegotiate)
            {
                case 1:
                scenario.say(self, convo, "m_no_negotiate");
                scenario.setPlayerProgress(speaker, 7);
                break;
                default:
                scenario.say(self, convo, "m_will_negotiate");
                scenario.setPlayerProgress(speaker, 8);
                break;
            }
            npcEndConversation(speaker);
            return SCRIPT_CONTINUE;
            case 7:
            scenario.say(self, convo, "m_sorry");
            npcEndConversation(speaker);
            return SCRIPT_CONTINUE;
            case 8:
            case 9:
            scenario.say(self, convo, "m_thank_negotiate");
            npcEndConversation(speaker);
            return SCRIPT_CONTINUE;
        }
        npcStartConversation(speaker, self, convo, msg, responses);
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String convoName, obj_id speaker, string_id response) throws InterruptedException
    {
        if (ai_lib.isInCombat(self))
        {
            return SCRIPT_CONTINUE;
        }
        LOG("poiDeliverance", "response = " + response.toString());
        obj_id poiMaster = poi.getBaseObject(self);
        if ((poiMaster == null) || (poiMaster == obj_id.NULL_ID))
        {
            LOG("poiDeliverance", "I have no idea what object I belong to!");
            npcEndConversation(speaker);
            return SCRIPT_CONTINUE;
        }
        String convo = getStringObjVar(poiMaster, scenario.VAR_SCENARIO_CONVO);
        if (convo.equals(""))
        {
            LOG("poiDeliverance", "I have no idea what I am supposed to say");
            npcEndConversation(speaker);
            return SCRIPT_CONTINUE;
        }
        if (!convoName.equals(convo))
        {
            LOG("poiDeliverance", "convo files do not match!!");
            return SCRIPT_CONTINUE;
        }
        int progress = scenario.getPlayerProgress(speaker);
        if (progress == 1)
        {
            String aId = response.getAsciiId();
            string_id msg = new string_id();
            if ((aId.equals("response_yes")))
            {
                scenario.say(self, convo, "m_yes");
                npcEndConversation(speaker);
                scenario.setPlayerProgress(speaker, 2);
            }
            else if ((aId.equals("response_no")))
            {
                scenario.say(self, convo, "m_no");
                npcEndConversation(speaker);
                scenario.setPlayerProgress(speaker, 1);
            }
            else if ((aId.equals("response_maybe")))
            {
                scenario.say(self, convo, "m_maybe");
                npcEndConversation(speaker);
                scenario.setPlayerProgress(speaker, 3);
            }
            int time = rand(0, 15);
            LOG("poiDeliverance", "OnObjectMenuSelect: messaging poiMaster to run scenario in " + time + " seconds!");
            setObjVar(self, VAR_RUNNING_SCENARIO, true);
            messageTo(poiMaster, scenario.HANDLER_RUN_SCENARIO, null, time, true);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleTimer(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("poiDeliverance", "MEDIATOR: handleTimer: entered...");
        if (!hasObjVar(self, VAR_RUNNING_SCENARIO))
        {
            obj_id poiMaster = poi.getBaseObject(self);
            if ((poiMaster == null) || (poiMaster == obj_id.NULL_ID))
            {
                LOG("poiDeliverance", "handleTimer: I have no idea what object I belong to!");
                return SCRIPT_CONTINUE;
            }
            LOG("poiDeliverance", "handleTimer: messaging poiMaster to run scenario NOW!");
            setObjVar(self, VAR_RUNNING_SCENARIO, true);
            messageTo(poiMaster, scenario.HANDLER_RUN_SCENARIO, null, 0, true);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleMyDeath(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("poiDeliverance", "handleMyDeath: entered...");
        obj_id[] killers = getObjIdArrayObjVar(self, scenario.VAR_PRIMARY_KILLERS);
        if ((killers == null) || (killers.length == 0))
        {
            LOG("poiDeliverance", "handleMyDeath: no credit to grant!");
            return SCRIPT_CONTINUE;
        }
        else 
        {
            for (int i = 0; i < killers.length; i++)
            {
                obj_id tmp = killers[i];
                if ((tmp == null) || (tmp == obj_id.NULL_ID))
                {
                }
                else 
                {
                    if (group.isGroupObject(tmp))
                    {
                        obj_id[] members = getGroupMemberIds(tmp);
                        if ((members == null) || (members.length == 0))
                        {
                        }
                        else 
                        {
                            for (int n = 0; n < members.length; n++)
                            {
                                LOG("poiDeliverance", "granting poi credit to (" + members[n] + ") " + getName(members[n]));
                                poiGrantCredit(members[n]);
                            }
                        }
                    }
                    else 
                    {
                        LOG("poiDeliverance", "granting poi credit to (" + tmp + ") " + getName(tmp));
                        poiGrantCredit(tmp);
                    }
                }
            }
        }
        LOG("poiDeliverance", "handleMyDeath: exiting...");
        return SCRIPT_CONTINUE;
    }
    public int handleAGreetM(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("poiDeliverance", "handleAGreetM: entered...");
        obj_id poiMaster = poi.getBaseObject(self);
        if ((poiMaster == null) || (poiMaster == obj_id.NULL_ID))
        {
            LOG("poiDeliverance", "handleAGreetM: unknown poi base object!");
            return SCRIPT_CONTINUE;
        }
        String convo = getStringObjVar(poiMaster, scenario.VAR_SCENARIO_CONVO);
        if (convo.equals(""))
        {
            LOG("poiDeliverance", "handleAGreetM: unknown convo file!");
            return SCRIPT_CONTINUE;
        }
        obj_id target = poiFindObject(scenario.ANTAGONIST + "_0");
        if ((target == null) || (target == obj_id.NULL_ID))
        {
            return SCRIPT_CONTINUE;
        }
        else 
        {
            faceTo(self, target);
            scenario.say(self, convo, "m_greet_a");
            messageTo(target, "handleMGreetA", null, 2, true);
        }
        return SCRIPT_CONTINUE;
    }
}
