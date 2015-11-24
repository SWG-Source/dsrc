package script.poi.deliverance;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.npc;
import script.library.scenario;
import script.library.poi;
import script.library.ai_lib;
import script.library.utils;
import script.library.factions;
import script.library.combat;
import script.library.group;
import script.ai.ai_combat;

public class antagonist extends script.theme_park.poi.base
{
    public antagonist()
    {
    }
    public static final String SCRIPT_CONVERSE = "npc.converse.npc_converse_menu";
    public static final String VAR_RESET_TIMER = scenario.VAR_SCENARIO_BASE + ".resetTimer";
    public static final String HANDLER_TIMER = "handleTimer";
    public static final String HANDLER_MEDIATOR_DEAD = "handleMediatorDead";
    public static final String HANDLER_FACE_CALL = "handleFaceCall";
    public static final String DICT_LEADER_ID = "leader";
    public static final String DICT_TARGET_ID = "target";
    public static final String DICT_FACTION_ID = "factionId";
    public static final int TIME_WAIT = 120;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        obj_id poiMaster = poi.getBaseObject(self);
        if ((poiMaster == null) || (poiMaster == obj_id.NULL_ID))
        {
            return SCRIPT_CONTINUE;
        }
        messageTo(self, scenario.HANDLER_MY_DEATH, null, 2, false);
        String myName = getStringObjVar(self, scenario.VAR_MY_NAME);
        if (!myName.equals(""))
        {
            dictionary d = new dictionary();
            d.put(scenario.DICT_NAME, myName);
            d.put(scenario.DICT_OBJID, self);
            messageTo(poiMaster, scenario.HANDLER_ACTOR_DEATH, d, 0, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnArrivedAtLocation(obj_id self, String locName) throws InterruptedException
    {
        LOG("poiDeliverance", "OnArrivedAtLocation: I arrived at location = " + locName);
        ai_lib.aiStopFollowing(self);
        messageTo(self, HANDLER_TIMER, null, TIME_WAIT, false);
        obj_id poiMaster = poi.getBaseObject(self);
        if ((poiMaster == null) || (poiMaster == obj_id.NULL_ID))
        {
            LOG("poiDeliverance", "OnArrivedAtLocation: unknown poi base object!");
            return SCRIPT_CONTINUE;
        }
        String convo = getStringObjVar(poiMaster, scenario.VAR_SCENARIO_CONVO);
        if (convo.equals(""))
        {
            LOG("poiDeliverance", "OnArrivedAtLocation: unknown convo file!");
            return SCRIPT_CONTINUE;
        }
        String desiredLocName = poiMaster.toString() + "_" + scenario.ANTAGONIST;
        if (desiredLocName.equals(locName))
        {
            obj_id target = poi.findObject(scenario.MEDIATOR);
            if ((target == null) || (target == obj_id.NULL_ID))
            {
                return SCRIPT_CONTINUE;
            }
            else 
            {
                location myLoc = getLocation(self);
                location tLoc = getLocation(target);
                float dist = utils.getDistance2D(myLoc, tLoc);
                if (dist < 12.5f)
                {
                    faceTo(self, target);
                    scenario.say(self, convo, "a_greet_m");
                    messageTo(target, "handleAGreetM", null, 2, false);
                }
                else 
                {
                    LOG("poiDeliverance", "(" + target + ") mediator not at target location..");
                    LOG("poiDeliverance", "(" + target + ") pathing to mediator current location..");
                    locName = poiMaster.toString() + "_" + scenario.ANTAGONIST;
                    scenario.requestAddTargetLocation(self, locName, tLoc, 7.5f);
                    setObjVar(self, scenario.VAR_TARGET_LOCATION_NAME, locName);
                    LOG("poiDeliverance", "(" + target + ") mediator loc = " + tLoc.toString());
                    LOG("poiDeliverance", "(" + self + ") leader pathing to: " + tLoc.toString());
                    ai_lib.aiPathTo(self, tLoc);
                    return SCRIPT_CONTINUE;
                }
            }
            messageTo(self, HANDLER_TIMER, null, TIME_WAIT, false);
        }
        obj_var_list ovl = getObjVarList(poiMaster, POI_STRING_LIST);
        if ((ovl == null) || (ovl.getNumItems() == 0))
        {
        }
        else 
        {
            for (int i = 0; i < ovl.getNumItems(); i++)
            {
                obj_var ov = ovl.getObjVar(i);
                String ovName = ov.getName();
                LOG("poiDeliverance", "checking script attach on " + ovName);
                if (ovName.startsWith(scenario.ANTAGONIST + "_"))
                {
                    obj_id ant = poi.findObject(ovName);
                    if ((ant == null) || (ant == obj_id.NULL_ID))
                    {
                        LOG("poiDeliverance", "poi unable to locate object named: " + ovName);
                    }
                    else 
                    {
                        attachScript(ant, SCRIPT_CONVERSE);
                    }
                }
            }
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
        boolean isLeader = false;
        obj_id leader = poi.findObject(scenario.ANTAGONIST + "_0");
        if ((leader == null) || (leader == obj_id.NULL_ID))
        {
        }
        else 
        {
            if (leader == self)
            {
                isLeader = true;
            }
        }
        if (isLeader)
        {
            LOG("poiDeliverance", "I am the antagonist leader...");
            String[] dead = getStringArrayObjVar(poiMaster, scenario.VAR_SCENARIO_DEAD);
            if ((dead == null) || (dead.length == 0))
            {
            }
            else 
            {
                int idx = utils.getElementPositionInArray(dead, scenario.MEDIATOR);
                if (idx < 0)
                {
                }
                else 
                {
                    if (poi.isGrantedCredit(poiMaster, speaker))
                    {
                        scenario.say(self, convo, "a_thank_combat");
                    }
                    else 
                    {
                        scenario.say(self, convo, "a_brushoff");
                    }
                    npcEndConversation(speaker);
                    return SCRIPT_CONTINUE;
                }
            }
            int progress = scenario.getPlayerProgress(speaker);
            string_id msg = new string_id(convo, "a_greet");
            Vector responses = new Vector();
            responses.setSize(0);
            LOG("poiDeliverance", "your progress = " + progress);
            switch (progress)
            {
                case 0:
                scenario.say(self, convo, "a_no_progress");
                npcEndConversation(speaker);
                return SCRIPT_CONTINUE;
                case 1:
                case 3:
                LOG("poiDeliverance", "comparing factions...");
                responses = utils.addElement(responses, new string_id(convo, "response_yes"));
                responses = utils.addElement(responses, new string_id(convo, "response_no"));
                responses = utils.addElement(responses, new string_id(convo, "response_maybe"));
                break;
                case 2:
                scenario.say(self, convo, "a_greet_mad");
                npcEndConversation(speaker);
                groupAttack(self, speaker);
                return SCRIPT_CONTINUE;
                case 4:
                npcEndConversation(speaker);
                groupAttack(self, speaker);
                return SCRIPT_CONTINUE;
                case 5:
                case 6:
                scenario.say(self, convo, "a_waiting");
                npcEndConversation(speaker);
                return SCRIPT_CONTINUE;
                case 7:
                scenario.say(self, convo, "a_negotiate_fail");
                npcEndConversation(speaker);
                groupAttack(self, speaker);
                return SCRIPT_CONTINUE;
                case 8:
                scenario.say(self, convo, "a_thank_negotiate");
                npcEndConversation(speaker);
                scenario.setPlayerProgress(speaker, 9);
                scenario.complete(poi.getBaseObject(self));
                messageTo(self, HANDLER_MEDIATOR_DEAD, null, 0, false);
                return SCRIPT_CONTINUE;
                case 9:
                scenario.say(self, convo, "a_thank_negotiate_again");
                npcEndConversation(speaker);
                return SCRIPT_CONTINUE;
            }
            if ((responses == null) || (responses.size() == 0))
            {
                LOG("poiDeliverance", "I got here but have no responses to give the player... hrmm");
            }
            else 
            {
                npcStartConversation(speaker, self, convo, msg, responses);
            }
        }
        else 
        {
            LOG("poiDeliverance", "I am an antagonist minion...");
            int idx = rand(1, 5);
            scenario.say(self, convo, "minion_" + idx);
            npcEndConversation(speaker);
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
            return SCRIPT_CONTINUE;
        }
        int progress = scenario.getPlayerProgress(speaker);
        if (progress > 0 && progress < 4)
        {
            String aId = response.getAsciiId();
            LOG("poiDeliverance", "your response = " + aId);
            string_id msg = new string_id();
            if ((aId.equals("response_yes")))
            {
                scenario.say(self, convo, "a_yes");
                npcEndConversation(speaker);
                scenario.setPlayerProgress(speaker, 5);
                return SCRIPT_CONTINUE;
            }
            else if ((aId.equals("response_no")))
            {
                scenario.say(self, convo, "a_no");
                npcEndConversation(speaker);
                scenario.setPlayerProgress(speaker, 4);
                groupAttack(self, speaker);
                return SCRIPT_CONTINUE;
            }
            else if ((aId.equals("response_maybe")))
            {
                int roll = rand(1, 3);
                switch (roll)
                {
                    case 1:
                    scenario.say(self, convo, "a_no_negotiate");
                    npcEndConversation(speaker);
                    obj_id target = poi.findObject(scenario.MEDIATOR);
                    if ((target == null) || (target == obj_id.NULL_ID))
                    {
                    }
                    else 
                    {
                        groupAttack(self, target);
                    }
                    break;
                    default:
                    scenario.say(self, convo, "a_maybe");
                    npcEndConversation(speaker);
                    setObjVar(self, VAR_RESET_TIMER, true);
                    break;
                }
                scenario.setPlayerProgress(speaker, 6);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void groupAttack(obj_id self, obj_id target) throws InterruptedException
    {
        startCombat(self, target);
        obj_id m = poi.findObject(scenario.MEDIATOR);
        if ((m == null) || (m == obj_id.NULL_ID))
        {
            return;
        }
        else 
        {
            startCombat(m, self);
        }
    }
    public void groupFace(obj_id self, obj_id target) throws InterruptedException
    {
        obj_id poiMaster = poi.getBaseObject(self);
        if ((poiMaster == null) || (poiMaster == obj_id.NULL_ID))
        {
        }
        else 
        {
            obj_var_list stringList = getObjVarList(poiMaster, POI_STRING_LIST);
            if ((stringList == null) || (stringList.getNumItems() < 1))
            {
            }
            else 
            {
                int numItems = stringList.getNumItems();
                for (int i = 0; i < numItems; i++)
                {
                    obj_var tmp = stringList.getObjVar(i);
                    String pathName = tmp.getName();
                    if (pathName.startsWith(POI_STRING_LIST + ".antagonist_"))
                    {
                        int idx = pathName.indexOf("antagonist_");
                        String name = pathName.substring(idx);
                        obj_id npc = poi.findObject(name);
                        faceTo(npc, target);
                    }
                }
            }
        }
        faceTo(self, target);
    }
    public int handleAddTargetLocation(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("poiDeliverance", "handleAddTargetLocation: entered...");
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        String locName = params.getString(scenario.DICT_LOC_NAME);
        location pathLoc = params.getLocation(scenario.DICT_LOCATION);
        float r = params.getFloat(scenario.DICT_RADIUS);
        addLocationTarget(locName, pathLoc, r);
        return SCRIPT_CONTINUE;
    }
    public int handleTimer(obj_id self, dictionary params) throws InterruptedException
    {
        if (ai_lib.isInCombat(self))
        {
            messageTo(self, HANDLER_TIMER, null, TIME_WAIT, false);
            return SCRIPT_CONTINUE;
        }
        LOG("poiDeliverance", "ANTAGONIST: handleTimer: entered...");
        obj_id poiMaster = poi.getBaseObject(self);
        if ((poiMaster == null) || (poiMaster == obj_id.NULL_ID))
        {
            LOG("poiDeliverance", "handleTimer: unknown poi base object!");
            return SCRIPT_CONTINUE;
        }
        if (scenario.isComplete(poiMaster))
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, VAR_RESET_TIMER))
        {
            boolean resetTimer = getBooleanObjVar(self, VAR_RESET_TIMER);
            if (resetTimer)
            {
                messageTo(self, HANDLER_TIMER, null, TIME_WAIT, false);
                removeObjVar(self, VAR_RESET_TIMER);
                return SCRIPT_CONTINUE;
            }
        }
        String convo = getStringObjVar(poiMaster, scenario.VAR_SCENARIO_CONVO);
        if (convo.equals(""))
        {
            LOG("poiDeliverance", "handleTimer: unknown poi base object!");
            return SCRIPT_CONTINUE;
        }
        String[] dead = getStringArrayObjVar(poiMaster, scenario.VAR_SCENARIO_DEAD);
        if ((dead == null) || (dead.length == 0))
        {
        }
        else 
        {
            int idx = utils.getElementPositionInArray(dead, scenario.MEDIATOR);
            if (idx < 0)
            {
            }
            else 
            {
                messageTo(poiMaster, HANDLER_MEDIATOR_DEAD, null, 30, false);
                return SCRIPT_CONTINUE;
            }
        }
        scenario.say(self, convo, "a_tired_waiting");
        obj_id target = poi.findObject(scenario.MEDIATOR);
        if ((target == null) || (target == obj_id.NULL_ID))
        {
        }
        else 
        {
            groupAttack(self, target);
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
    public int handleMediatorDead(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id poiMaster = poi.getBaseObject(self);
        if ((poiMaster == null) || (poiMaster == obj_id.NULL_ID))
        {
            LOG("poiDeliverance", "handleTimer: unknown poi base object!");
            return SCRIPT_CONTINUE;
        }
        else 
        {
            messageTo(poiMaster, scenario.HANDLER_CLEANUP_SCENARIO, null, TIME_WAIT, false);
        }
        location loc = utils.getRandomLocationInRing(getLocation(self), 96f, 128f);
        pathTo(self, loc);
        return SCRIPT_CONTINUE;
    }
    public int handleMGreetA(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("poiDeliverance", "handleMGreetA: entered...");
        obj_id poiMaster = poi.getBaseObject(self);
        if ((poiMaster == null) || (poiMaster == obj_id.NULL_ID))
        {
            LOG("poiDeliverance", "handleMGreetA: unknown poi base object!");
            return SCRIPT_CONTINUE;
        }
        String convo = getStringObjVar(poiMaster, scenario.VAR_SCENARIO_CONVO);
        if (convo.equals(""))
        {
            LOG("poiDeliverance", "handleMGreetA: unknown convo file!");
            return SCRIPT_CONTINUE;
        }
        obj_id target = poi.findObject(scenario.MEDIATOR);
        if ((target == null) || (target == obj_id.NULL_ID))
        {
            return SCRIPT_CONTINUE;
        }
        else 
        {
            scenario.say(self, convo, "a_respond_m_1");
            messageTo(target, "handleARespondM1", null, 2, false);
        }
        if (ai_lib.isInCombat(target))
        {
            messageTo(self, HANDLER_TIMER, null, 1, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleFaceCall(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id target = params.getObjId(DICT_TARGET_ID);
        if ((target == null) || (target == obj_id.NULL_ID))
        {
            return SCRIPT_CONTINUE;
        }
        faceTo(self, target);
        return SCRIPT_CONTINUE;
    }
}
