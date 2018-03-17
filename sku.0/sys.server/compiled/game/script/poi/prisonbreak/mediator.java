package script.poi.prisonbreak;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.poi;
import script.library.scenario;
import script.library.utils;
import script.library.chat;
import script.library.group;
import script.library.badge;
import script.ai.ai;
import script.ai.ai_combat;

public class mediator extends script.poi.base.scenario_actor
{
    public mediator()
    {
    }
    public static final String SCRIPT_CONVERSE = "npc.converse.npc_converse_menu";
    public static final String LOG_NAME = "poiPrisonBreak Mediator";
    public static final String ALERT_VOLUME_NAME = "alertTriggerVolume";
    public static final int CONV_GREET = 0;
    public static final int CONV_INSULT = 1;
    public static final int CONV_NOHELP = 2;
    public static final int CONV_YESHELP = 3;
    public static final int CONV_TALKMEDIATOR = 4;
    public static final int CONV_CHECKGUARD = 5;
    public static final int CONV_GUARDPEEING = 6;
    public static final int CONV_GUARDHOLDINGIT = 7;
    public static final int CONV_MAYBEHELP = 8;
    public static final int CONV_SABOTAGEPLAN = 9;
    public static final String joyEmotes[] = 
    {
        "thank",
        "cheer",
        "applaud",
        "laugh",
        "softclap",
        "yes",
        "glow"
    };
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
        messageTo(self, scenario.HANDLER_MY_DEATH, null, 2, false);
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
        boolean isLeader = poi.isNpcTagged(self, "mediator_0");
        boolean isGuard = poi.isNpcTagged(self, "mediator_1");
        boolean antagonistsDead = false;
        boolean someDead = false;
        obj_id[] antagonists = scenario.getTeamMembers("antagonist");
        int deadcount = scenario.getDeadTeamMemberCount(poiMaster, "antagonist");
        if (deadcount == antagonists.length)
        {
            antagonistsDead = true;
        }
        else if (deadcount > 0)
        {
            someDead = true;
        }
        boolean speakerCredit = scenario.hasKillCredit(poiMaster, "antagonist", speaker);
        boolean leaderDead = hasObjVar(poiMaster, "mediatorLeaderDead");
        if (!isLeader)
        {
            if (isGuard)
            {
                guardChatter(self, convo, speaker, poiMaster, antagonistsDead, speakerCredit, leaderDead);
            }
            else 
            {
                minionChatter(self, convo, speaker, poiMaster, antagonistsDead, speakerCredit, leaderDead);
            }
            return SCRIPT_CONTINUE;
        }
        string_id msg = new string_id(convo, "m_greet_0");
        Vector responses = new Vector();
        responses.setSize(0);
        if (antagonistsDead)
        {
            if (!speakerCredit)
            {
                poi.quickSay(self, chat.CHAT_STATE, chat.MOOD_EXUBERANT, "m_minion_wesurvived");
            }
            else if (hasObjVar(poiMaster, "playerSabotage"))
            {
                poi.quickSay(self, chat.CHAT_STATE, chat.MOOD_EXUBERANT, "m_majorvictory");
                dictionary params = new dictionary();
                params.put("victory", true);
                params.put("speaker", speaker);
                messageTo(self, "finishScenario", params, 0, false);
            }
            else 
            {
                poi.quickSay(self, chat.CHAT_STATE, chat.MOOD_EXUBERANT, "m_minorvictory");
                dictionary params = new dictionary();
                params.put("victory", true);
                params.put("speaker", speaker);
                messageTo(self, "finishScenario", params, 0, false);
            }
            npcEndConversation(speaker);
            return SCRIPT_CONTINUE;
        }
        else if (hasObjVar(poiMaster, "sabotage"))
        {
            if (progress == CONV_SABOTAGEPLAN)
            {
                scenario.say(self, convo, "m_distractguard", false);
            }
            else 
            {
                scenario.say(self, convo, "m_soontheypay", false);
            }
            npcEndConversation(speaker);
            return SCRIPT_CONTINUE;
        }
        switch (progress)
        {
            case CONV_GREET:
            case CONV_INSULT:
            case CONV_NOHELP:
            case CONV_MAYBEHELP:
            msg = new string_id(convo, "m_greet");
            responses = utils.addElement(responses, new string_id(convo, "r_m_greet_whatprisoners"));
            npcStartConversation(speaker, self, convo, msg, responses);
            break;
            case CONV_TALKMEDIATOR:
            msg = new string_id(convo, "m_busy");
            responses = utils.addElement(responses, new string_id(convo, "r_anywork"));
            npcStartConversation(speaker, self, convo, msg, responses);
            break;
            case CONV_CHECKGUARD:
            msg = new string_id(convo, "m_didyoucheck");
            responses = utils.addElement(responses, new string_id(convo, "r_noillcheck"));
            npcStartConversation(speaker, self, convo, msg, responses);
            break;
            case CONV_GUARDPEEING:
            case CONV_GUARDHOLDINGIT:
            msg = new string_id(convo, "m_didyoucheck");
            responses = utils.addElement(responses, new string_id(convo, "r_hesdoingfine"));
            if (progress == CONV_GUARDPEEING)
            {
                responses = utils.addElement(responses, new string_id(convo, "r_hesonbreak"));
            }
            else if (progress == CONV_GUARDHOLDINGIT)
            {
                responses = utils.addElement(responses, new string_id(convo, "r_heneedsabreak"));
            }
            npcStartConversation(speaker, self, convo, msg, responses);
            break;
            case CONV_YESHELP:
            msg = new string_id(convo, "m_busy");
            responses = utils.addElement(responses, new string_id(convo, "r_prisonersplanning"));
            npcStartConversation(speaker, self, convo, msg, responses);
            break;
            default:
            scenario.say(self, convo, "m_busy", false);
            npcEndConversation(speaker);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public void guardChatter(obj_id self, String convo, obj_id speaker, obj_id poiMaster, boolean antagonistsDead, boolean speakerCredit, boolean leaderDead) throws InterruptedException
    {
        int progress = scenario.getPlayerProgress(speaker);
        int idx;
        if (leaderDead || antagonistsDead)
        {
            minionChatter(self, convo, speaker, poiMaster, antagonistsDead, speakerCredit, leaderDead);
            return;
        }
        if (hasObjVar(self, "peeing"))
        {
            poi.quickSay(self, "m_minion_guard_peeing");
            npcEndConversation(speaker);
            return;
        }
        Vector responses = new Vector();
        responses.setSize(0);
        string_id msg;
        switch (progress)
        {
            case CONV_CHECKGUARD:
            idx = rand(1, 4);
            msg = new string_id(convo, "m_minion_guard_" + idx);
            responses = utils.addElement(responses, new string_id(convo, "r_toldtocheckyou"));
            npcStartConversation(speaker, self, convo, msg, responses);
            break;
            case CONV_YESHELP:
            case CONV_SABOTAGEPLAN:
            idx = rand(1, 4);
            msg = new string_id(convo, "m_minion_guard_" + idx);
            responses = utils.addElement(responses, new string_id(convo, "r_whatshappening"));
            npcStartConversation(speaker, self, convo, msg, responses);
            break;
            default:
            idx = rand(1, 4);
            poi.quickSay(self, "m_minion_guard_" + idx);
            npcEndConversation(speaker);
            break;
        }
    }
    public void minionChatter(obj_id self, String convo, obj_id speaker, obj_id poiMaster, boolean antagonistsDead, boolean speakerCredit, boolean leaderDead) throws InterruptedException
    {
        if (leaderDead)
        {
            if (!speakerCredit)
            {
                poi.quickSay(self, chat.CHAT_EULOGIZE, chat.MOOD_SAD, "m_minion_leaderdead");
            }
            else if (hasObjVar(poiMaster, "playerSabotage"))
            {
                int idx = rand(1, 2);
                poi.quickSay(self, chat.CHAT_COMPLAIN, chat.MOOD_SAD, "m_minion_minorfailure_" + idx);
                messageTo(self, "finishScenario", null, 0, false);
            }
            else 
            {
                int idx = rand(1, 2);
                poi.quickSay(self, chat.CHAT_SHOUT, chat.MOOD_DISMAYED, "m_minion_majorfailure_" + idx);
                messageTo(self, "finishScenario", null, 0, false);
            }
        }
        else if (antagonistsDead)
        {
            if (!speakerCredit)
            {
                poi.quickSay(self, chat.CHAT_STATE, chat.MOOD_EXUBERANT, "m_minion_wesurvived");
            }
            else if (hasObjVar(poiMaster, "playerSabotage"))
            {
                int idx = rand(1, 2);
                poi.quickSay(self, chat.CHAT_STATE, chat.MOOD_EXUBERANT, "m_minion_majorvictory_" + idx);
            }
            else 
            {
                int idx = rand(1, 2);
                poi.quickSay(self, chat.CHAT_STATE, chat.MOOD_EXUBERANT, "m_minion_minorvictory_" + idx);
            }
        }
        else 
        {
            int idx = rand(1, 4);
            poi.quickSay(self, "m_minion_" + idx);
        }
        npcEndConversation(speaker);
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
            LOG(LOG_NAME, "Convo files do not match!!");
            return SCRIPT_CONTINUE;
        }
        int progress = scenario.getPlayerProgress(speaker);
        boolean isLeader = poi.isNpcTagged(self, scenario.MEDIATOR + "_0");
        boolean isGuard = poi.isNpcTagged(self, scenario.MEDIATOR + "_1");
        if (!isGuard && !isLeader)
        {
            return SCRIPT_CONTINUE;
        }
        String aId = response.getAsciiId();
        string_id msg = new string_id();
        Vector responses = new Vector();
        responses.setSize(0);
        npcSetConversationResponses(speaker, responses);
        if (isGuard)
        {
            if ((aId.equals("r_toldtocheckyou")))
            {
                npcSpeak(speaker, new string_id(convo, "m_guard_allsquiet"));
                responses = utils.addElement(responses, new string_id(convo, "r_gotellmaster"));
                npcSetConversationResponses(speaker, responses);
            }
            else if ((aId.equals("r_gotellmaster")))
            {
                npcSpeak(speaker, new string_id(convo, "m_guard_needtopee"));
                responses = utils.addElement(responses, new string_id(convo, "r_pee_goodidea"));
                responses = utils.addElement(responses, new string_id(convo, "r_pee_badidea"));
                npcSetConversationResponses(speaker, responses);
            }
            else if ((aId.equals("r_pee_goodidea")))
            {
                npcSpeak(speaker, new string_id(convo, "m_guard_pee_goodidea"));
                if (progress == CONV_CHECKGUARD)
                {
                    scenario.setPlayerProgress(speaker, CONV_GUARDPEEING);
                }
                messageTo(self, "goPee", null, 2, false);
                setObjVar(self, "peeing", true);
            }
            else if ((aId.equals("r_pee_badidea")))
            {
                npcSpeak(speaker, new string_id(convo, "m_guard_pee_badidea"));
                if (progress == CONV_CHECKGUARD)
                {
                    scenario.setPlayerProgress(speaker, CONV_GUARDHOLDINGIT);
                }
                messageTo(self, "goPee", null, 20, false);
                setObjVar(self, "peeing", true);
            }
            else if ((aId.equals("r_whatshappening")))
            {
                npcSpeak(speaker, new string_id(convo, "m_guard_needtopee"));
                responses = utils.addElement(responses, new string_id(convo, "r_pee_goodidea"));
                responses = utils.addElement(responses, new string_id(convo, "r_pee_badidea"));
                npcSetConversationResponses(speaker, responses);
            }
        }
        else if (isLeader)
        {
            if ((aId.equals("r_m_greet_whatprisoners")))
            {
                scenario.setPlayerProgress(speaker, CONV_TALKMEDIATOR);
                npcSpeak(speaker, new string_id(convo, "m_tellprisoners"));
                responses = utils.addElement(responses, new string_id(convo, "r_tp_whynotkillthem"));
                responses = utils.addElement(responses, new string_id(convo, "r_tp_bettermovethem"));
                npcSetConversationResponses(speaker, responses);
            }
            else if ((aId.equals("r_tp_whynotkillthem")))
            {
                npcSpeak(speaker, new string_id(convo, "m_nokillthem"));
                scenario.setPlayerProgress(speaker, CONV_TALKMEDIATOR);
                responses = utils.addElement(responses, new string_id(convo, "r_anywork"));
                npcSetConversationResponses(speaker, responses);
            }
            else if ((aId.equals("r_tp_bettermovethem")))
            {
                npcSpeak(speaker, new string_id(convo, "m_cantmovethem"));
                scenario.setPlayerProgress(speaker, CONV_TALKMEDIATOR);
                responses = utils.addElement(responses, new string_id(convo, "r_anywork"));
                npcSetConversationResponses(speaker, responses);
            }
            else if ((aId.equals("r_anywork")))
            {
                npcSpeak(speaker, new string_id(convo, "m_checktheguard"));
                scenario.setPlayerProgress(speaker, CONV_CHECKGUARD);
            }
            else if ((aId.equals("r_noillcheck")))
            {
                npcSpeak(speaker, new string_id(convo, "m_yeahyoudothat"));
            }
            else if ((aId.equals("r_hesdoingfine")))
            {
                npcSpeak(speaker, new string_id(convo, "m_excellent"));
            }
            else if ((aId.equals("r_hesonbreak")))
            {
                npcSpeak(speaker, new string_id(convo, "m_whatbreak"));
            }
            else if ((aId.equals("r_heneedsabreak")))
            {
                npcSpeak(speaker, new string_id(convo, "m_nobreak"));
            }
            else if ((aId.equals("r_prisonersplanning")))
            {
                npcSpeak(speaker, new string_id(convo, "m_whatbreakout"));
                scenario.setPlayerProgress(speaker, CONV_TALKMEDIATOR);
                responses = utils.addElement(responses, new string_id(convo, "r_prisonershavebomb"));
                npcSetConversationResponses(speaker, responses);
            }
            else if ((aId.equals("r_prisonershavebomb")))
            {
                npcSpeak(speaker, new string_id(convo, "m_abomb"));
                scenario.setPlayerProgress(speaker, CONV_TALKMEDIATOR);
                responses = utils.addElement(responses, new string_id(convo, "r_distractanyway"));
                npcSetConversationResponses(speaker, responses);
            }
            else if ((aId.equals("r_distractanyway")))
            {
                npcSpeak(speaker, new string_id(convo, "m_theplan"));
                setObjVar(poiMaster, "sabotage", true);
                scenario.setPlayerProgress(speaker, CONV_SABOTAGEPLAN);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int finishScenario(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id poiMaster = poi.getBaseObject(self);
        scenario.complete(poiMaster);
        if ((params != null) && params.getBoolean("victory"))
        {
            obj_id speaker = params.getObjId("speaker");
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
                        if (!badge.hasBadge(members[n], "poi_prisonbreak"))
                        {
                            badge.grantBadge(members[n], "poi_prisonbreak");
                        }
                        else 
                        {
                            badge.notifyHasBadge(members[n], "poi_prisonbreak");
                        }
                    }
                }
            }
            else 
            {
                if (!badge.hasBadge(speaker, "poi_prisonbreak"))
                {
                    badge.grantBadge(speaker, "poi_prisonbreak");
                }
                else 
                {
                    badge.notifyHasBadge(speaker, "poi_prisonbreak");
                }
            }
            obj_id[] mediators = scenario.getTeamMembers(poiMaster, "mediator");
            if (mediators == null)
            {
                return SCRIPT_CONTINUE;
            }
            for (int i = 0; i < mediators.length; i++)
            {
                obj_id a = mediators[i];
                if ((a == null) || (a == obj_id.NULL_ID))
                {
                    continue;
                }
                messageTo(a, "celebrateVictory", null, 0, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnSawAttack(obj_id self, obj_id defender, obj_id[] attackers) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int celebrateVictory(obj_id self, dictionary params) throws InterruptedException
    {
        stop(self);
        int whichsocial = rand(0, 6);
        queueCommand(self, (1780871594), null, joyEmotes[whichsocial], COMMAND_PRIORITY_DEFAULT);
        messageTo(self, "resumeDefaultCalmBehavior", null, 3, false);
        return SCRIPT_CONTINUE;
    }
    public int goPee(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id poiMaster = poi.getBaseObject(self);
        if ((poiMaster == null) || (poiMaster == obj_id.NULL_ID))
        {
            return SCRIPT_CONTINUE;
        }
        poi.quickSay(self, chat.CHAT_EXCLAIM, chat.MOOD_NONE, "m_minion_gopee");
        location peeLoc = new location(getLocation(self));
        peeLoc.x -= 15;
        peeLoc.z += 15;
        setMovementRun(self);
        ai_lib.aiPathTo(self, peeLoc);
        messageTo(poiMaster, "guardGone", null, 10, false);
        return SCRIPT_CONTINUE;
    }
    public int handleMyDeath(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id poiMaster = poi.getBaseObject(self);
        if ((poiMaster == null) || (poiMaster == obj_id.NULL_ID))
        {
            return SCRIPT_CONTINUE;
        }
        if (poi.isNpcTagged(self, scenario.MEDIATOR + "_0"))
        {
            setObjVar(poiMaster, "mediatorLeaderDead", true);
        }
        if (hasObjVar(self, "guard") && !hasObjVar(poiMaster, "bombPlan"))
        {
            messageTo(poiMaster, "guardGone", null, 3, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        if (hasObjVar(breacher, "gm"))
        {
            return SCRIPT_OVERRIDE;
        }
        if (ai_lib.isInCombat(self))
        {
            return SCRIPT_OVERRIDE;
        }
        if (breacher == self)
        {
            return SCRIPT_OVERRIDE;
        }
        if (isIncapacitated(self))
        {
            return SCRIPT_OVERRIDE;
        }
        if (!isMob(breacher))
        {
            return SCRIPT_OVERRIDE;
        }
        if (ai_lib.isMonster(breacher))
        {
            return SCRIPT_OVERRIDE;
        }
        if (volumeName.equals(ALERT_VOLUME_NAME))
        {
            if (poi.isNpcTagged(self, "mediator_0"))
            {
                return SCRIPT_OVERRIDE;
            }
            obj_id poiMaster = poi.getBaseObject(self);
            if ((poiMaster == null) || (poiMaster == obj_id.NULL_ID))
            {
                return SCRIPT_CONTINUE;
            }
            if (hasObjVar(poiMaster, "recentlySpoke"))
            {
                return SCRIPT_OVERRIDE;
            }
            setObjVar(poiMaster, "recentlySpoke", true);
            messageTo(self, "resetSpeakTimer", null, 60 * 5, false);
            faceTo(self, breacher);
            int i = rand(0, 4);
            if (i == 0)
            {
                queueCommand(self, (1780871594), breacher, "hail", COMMAND_PRIORITY_DEFAULT);
            }
            else 
            {
                poi.quickSay(self, "m_approachchatter_" + i);
            }
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_OVERRIDE;
    }
    public int resetSpeakTimer(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id poiMaster = poi.getBaseObject(self);
        if ((poiMaster == null) || (poiMaster == obj_id.NULL_ID))
        {
            return SCRIPT_CONTINUE;
        }
        removeObjVar(poiMaster, "recentlySpoke");
        return SCRIPT_CONTINUE;
    }
}
