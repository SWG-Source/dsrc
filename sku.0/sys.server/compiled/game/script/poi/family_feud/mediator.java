package script.poi.family_feud;

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
import script.ai.ai_combat;

public class mediator extends script.poi.base.scenario_actor
{
    public mediator()
    {
    }
    public static final String SCRIPT_CONVERSE = "npc.converse.npc_converse_menu";
    public static final String VAR_EN_ROUTE = scenario.VAR_SCENARIO_BASE + ".enRoute";
    public static final String HANDLER_TIMER = "handleTimer";
    public static final String HANDLER_CADENCE = "handleCadence";
    public static final String DICT_LEADER_ID = "leader";
    public static final String DICT_TARGET_ID = "target";
    public static final String DICT_FACTION_ID = "factionId";
    public static final int TIME_WAIT = 120;
    public static final String MY_TARGET_LOCATION = scenario.ANTAGONIST + "_loc";
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
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        messageTo(self, scenario.HANDLER_INCAPACITATION, null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        messageTo(self, scenario.HANDLER_INCAPACITATION, null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id speaker) throws InterruptedException
    {
        obj_id poiMaster = poi.getBaseObject(self);
        if ((poiMaster == null) || (poiMaster == obj_id.NULL_ID))
        {
            npcEndConversation(speaker);
            return SCRIPT_CONTINUE;
        }
        String convo = getStringObjVar(poiMaster, scenario.VAR_SCENARIO_CONVO);
        if (convo.equals(""))
        {
            npcEndConversation(speaker);
            return SCRIPT_CONTINUE;
        }
        if (ai_lib.isInCombat(self))
        {
            scenario.sayNoAct(self, convo, "m_incombat_" + rand(1, 5));
            return SCRIPT_CONTINUE;
        }
        int cnt = getIntObjVar(poiMaster, scenario.VAR_ANTAGONIST_COUNT);
        if (cnt > 0)
        {
        }
        else 
        {
            if (poi.isGrantedCredit(poiMaster, speaker))
            {
                scenario.say(self, convo, "m_thank_combat");
            }
            else 
            {
                scenario.say(self, convo, "m_brushoff");
            }
            npcEndConversation(speaker);
            return SCRIPT_CONTINUE;
        }
        boolean isLeader = false;
        obj_id leader = poi.findObject(scenario.MEDIATOR + "_0");
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
        boolean isLeaderEnRoute = getBooleanObjVar(leader, VAR_EN_ROUTE);
        if (isLeader)
        {
            if (isLeaderEnRoute)
            {
                switch (rand(1, 4))
                {
                    case 1:
                    obj_id[] members = scenario.getActorsWithNamePrefix(poiMaster, scenario.MEDIATOR);
                    if ((members == null) || (members.length == 0))
                    {
                    }
                    else 
                    {
                        scenario.sayNoAct(self, convo, "m_cadence");
                        for (int i = 0; i < members.length; i++)
                        {
                            messageTo(members[i], HANDLER_CADENCE, null, 1, false);
                        }
                    }
                    break;
                    default:
                    scenario.sayNoAct(self, convo, "m_enroute");
                    break;
                }
                npcEndConversation(speaker);
                return SCRIPT_CONTINUE;
            }
            int progress = scenario.getPlayerProgress(speaker);
            string_id msg = new string_id(convo, "m_greet");
            Vector responses = new Vector();
            responses.setSize(0);
            switch (progress)
            {
                case 0:
                case 4:
                responses = utils.addElement(responses, new string_id(convo, "response_yes"));
                responses = utils.addElement(responses, new string_id(convo, "response_no"));
                break;
                case 3:
                case 6:
                scenario.say(self, convo, "m_mad");
                npcEndConversation(speaker);
                scenario.groupAttack(self, speaker);
                return SCRIPT_CONTINUE;
                case 1:
                scenario.say(self, convo, "m_preparing");
                npcEndConversation(speaker);
                return SCRIPT_CONTINUE;
                case 2:
                scenario.say(self, convo, "m_rejected");
                npcEndConversation(speaker);
                return SCRIPT_CONTINUE;
                case 5:
                scenario.say(self, convo, "m_runaround");
                npcEndConversation(speaker);
                scenario.groupAttack(self, speaker);
                break;
            }
            if ((responses == null) || (responses.size() == 0))
            {
            }
            else 
            {
                npcStartConversation(speaker, self, convo, msg, responses);
            }
        }
        else 
        {
            int idx = rand(1, 5);
            scenario.say(self, convo, "m_minion_" + idx);
            npcEndConversation(speaker);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String convoName, obj_id speaker, string_id response) throws InterruptedException
    {
        obj_id poiMaster = poi.getBaseObject(self);
        if ((poiMaster == null) || (poiMaster == obj_id.NULL_ID))
        {
            npcEndConversation(speaker);
            return SCRIPT_CONTINUE;
        }
        String convo = getStringObjVar(poiMaster, scenario.VAR_SCENARIO_CONVO);
        if (convo.equals(""))
        {
            npcEndConversation(speaker);
            return SCRIPT_CONTINUE;
        }
        if (ai_lib.isInCombat(self))
        {
            scenario.sayNoAct(self, convo, "m_incombat_" + rand(1, 5));
            return SCRIPT_CONTINUE;
        }
        if (!convoName.equals(convo))
        {
            return SCRIPT_CONTINUE;
        }
        int progress = scenario.getPlayerProgress(speaker);
        if (progress == 0 || progress == 4)
        {
            String aId = response.getAsciiId();
            string_id msg = new string_id();
            if ((aId.equals("response_yes")))
            {
                scenario.say(self, convo, "m_yes");
                npcEndConversation(speaker);
                scenario.setPlayerProgress(speaker, 1);
                dictionary d = new dictionary();
                d.put(scenario.DICT_OBJID, speaker);
                messageTo(self, scenario.HANDLER_PREP_TIMER, d, rand(10, 25), false);
                return SCRIPT_CONTINUE;
            }
            else if ((aId.equals("response_no")))
            {
                scenario.say(self, convo, "m_no");
                npcEndConversation(speaker);
                scenario.setPlayerProgress(speaker, 2);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnArrivedAtLocation(obj_id self, String name) throws InterruptedException
    {
        if (name.equals(MY_TARGET_LOCATION))
        {
            removeObjVar(self, VAR_EN_ROUTE);
            ai_lib.aiStopFollowing(self);
            obj_id poiMaster = poi.getBaseObject(self);
            if ((poiMaster == null) || (poiMaster == obj_id.NULL_ID))
            {
                return SCRIPT_CONTINUE;
            }
            int enemyCount = getIntObjVar(poiMaster, scenario.VAR_MEDIATOR_COUNT);
            if (enemyCount < 1)
            {
                queueCommand(self, (1780871594), null, "scratch", COMMAND_PRIORITY_DEFAULT);
                return SCRIPT_CONTINUE;
            }
            obj_id target = obj_id.NULL_ID;
            obj_id[] npcsInRange = getNPCsInRange(getLocation(self), 50f);
            if ((npcsInRange == null) || (npcsInRange.length == 0))
            {
            }
            else 
            {
                obj_id enemyLeader = poi.findObject(scenario.ANTAGONIST + "_0");
                if ((enemyLeader == null) || (enemyLeader == obj_id.NULL_ID))
                {
                }
                else 
                {
                    int idx = utils.getElementPositionInArray(npcsInRange, enemyLeader);
                    if (idx > -1)
                    {
                        if (!isIncapacitated(enemyLeader) && !isDead(enemyLeader))
                        {
                            target = enemyLeader;
                        }
                    }
                }
            }
            if (target == obj_id.NULL_ID)
            {
                obj_id[] enemies = scenario.getActorsWithNamePrefix(poiMaster, scenario.ANTAGONIST);
                if ((enemies == null) || (enemies.length == 0))
                {
                }
                else 
                {
                    for (int i = 0; i < enemies.length; i++)
                    {
                        if (target == obj_id.NULL_ID)
                        {
                            if (utils.getElementPositionInArray(npcsInRange, enemies[i]) > -1)
                            {
                                if (!isIncapacitated(enemies[i]) && !isDead(enemies[i]))
                                {
                                    target = enemies[i];
                                }
                            }
                        }
                    }
                }
            }
            if ((target == null) || (target == obj_id.NULL_ID))
            {
            }
            else 
            {
                dictionary d = new dictionary();
                d.put(scenario.DICT_OBJID, target);
                messageTo(self, scenario.HANDLER_CHARGE, d, 0, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleIncapacitation(obj_id self, dictionary params) throws InterruptedException
    {
        scenario.actorIncapacitated(self);
        return SCRIPT_CONTINUE;
    }
    public int handleCadence(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id poiMaster = poi.getBaseObject(self);
        if ((poiMaster == null) || (poiMaster == obj_id.NULL_ID))
        {
            return SCRIPT_CONTINUE;
        }
        String convo = getStringObjVar(poiMaster, scenario.VAR_SCENARIO_CONVO);
        if (convo.equals(""))
        {
            return SCRIPT_CONTINUE;
        }
        scenario.sayNoAct(self, convo, "m_cadence_response");
        return SCRIPT_CONTINUE;
    }
    public int handleFollowTimer(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId(scenario.DICT_OBJID);
        if ((player == null) || (player == obj_id.NULL_ID))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id poiMaster = poi.getBaseObject(self);
        if ((poiMaster == null) || (poiMaster == obj_id.NULL_ID))
        {
            return SCRIPT_CONTINUE;
        }
        String convo = getStringObjVar(poiMaster, scenario.VAR_SCENARIO_CONVO);
        if (convo.equals(""))
        {
            return SCRIPT_CONTINUE;
        }
        location loc = getLocationObjVar(poiMaster, scenario.VAR_ANTAGONIST_LOC);
        pathTo(self, loc);
        return SCRIPT_CONTINUE;
    }
    public int handlePrepTimer(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId(scenario.DICT_OBJID);
        if ((player == null) || (player == obj_id.NULL_ID))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id poiMaster = poi.getBaseObject(self);
        if ((poiMaster == null) || (poiMaster == obj_id.NULL_ID))
        {
            return SCRIPT_CONTINUE;
        }
        String convo = getStringObjVar(poiMaster, scenario.VAR_SCENARIO_CONVO);
        if (convo.equals(""))
        {
            return SCRIPT_CONTINUE;
        }
        scenario.groupFollow(self, player, scenario.MEDIATOR);
        scenario.sayNoAct(self, convo, "m_rtg");
        setObjVar(self, VAR_EN_ROUTE, true);
        scenario.setPlayerProgress(player, 7);
        location loc = getLocationObjVar(poiMaster, scenario.VAR_ANTAGONIST_LOC);
        if (loc == null)
        {
            debugSpeakMsg(self, "target loc = null... aborting target loc!");
        }
        else 
        {
            addLocationTarget(MY_TARGET_LOCATION, loc, 25f);
        }
        messageTo(self, scenario.HANDLER_FOLLOW_TIMER, params, rand(600, 800), true);
        obj_id wp = createWaypointInDatapad(player, loc);
        if (isIdValid(wp))
        {
            setWaypointActive(wp, true);
            setWaypointVisible(wp, true);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleCharge(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id poiMaster = poi.getBaseObject(self);
        if ((poiMaster == null) || (poiMaster == obj_id.NULL_ID))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] members = scenario.getActorsWithNamePrefix(poiMaster, scenario.MEDIATOR);
        if ((members == null) || (members.length == 0))
        {
        }
        else 
        {
            for (int i = 0; i < members.length; i++)
            {
                ai_lib.aiStopFollowing(members[i]);
            }
        }
        String convo = getStringObjVar(poiMaster, scenario.VAR_SCENARIO_CONVO);
        if (convo.equals(""))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id target = params.getObjId(scenario.DICT_OBJID);
        if ((target == null) || (target == obj_id.NULL_ID))
        {
            queueCommand(self, (1780871594), null, "scratch", COMMAND_PRIORITY_DEFAULT);
        }
        else 
        {
            scenario.sayNoAct(self, convo, "m_charge");
            params.put(scenario.DICT_OBJID, self);
            messageTo(target, scenario.HANDLER_ENEMY_ATTACK, params, 2, false);
            scenario.groupAttack(self, target);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleEnemyAttack(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id poiMaster = poi.getBaseObject(self);
        if ((poiMaster == null) || (poiMaster == obj_id.NULL_ID))
        {
            return SCRIPT_CONTINUE;
        }
        String convo = getStringObjVar(poiMaster, scenario.VAR_SCENARIO_CONVO);
        if (convo.equals(""))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id target = params.getObjId(scenario.DICT_OBJID);
        if ((target == null) || (target == obj_id.NULL_ID))
        {
            return SCRIPT_CONTINUE;
        }
        else 
        {
            scenario.sayNoAct(self, convo, "m_enemy_attack");
            scenario.groupAttack(self, target);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleVictory(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id poiMaster = poi.getBaseObject(self);
        if ((poiMaster == null) || (poiMaster == obj_id.NULL_ID))
        {
            return SCRIPT_CONTINUE;
        }
        String convo = getStringObjVar(poiMaster, scenario.VAR_SCENARIO_CONVO);
        if (convo.equals(""))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id leader = poi.findObject(scenario.MEDIATOR + "_0");
        if ((leader == null) || (leader == obj_id.NULL_ID))
        {
        }
        else 
        {
            if (leader == self)
            {
                scenario.say(self, convo, "m_victory");
            }
            else 
            {
                scenario.say(self, convo, "m_minion_victory_" + rand(1, 5));
            }
        }
        return SCRIPT_CONTINUE;
    }
}
