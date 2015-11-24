package script.conversation;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.chat;
import script.library.conversation;
import script.library.utils;

public class tour_aryon extends script.base_script
{
    public tour_aryon()
    {
    }
    public static String c_stringFile = "conversation/tour_aryon";
    public boolean tour_aryon_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean tour_aryon_condition_electionEnded(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasObjVar(npc, "bestine.electionEnded");
    }
    public boolean tour_aryon_condition_alreadyVoted(obj_id player, obj_id npc) throws InterruptedException
    {
        boolean tour_aryon_condition_alreadyVoted = false;
        int electionNum = getIntObjVar(npc, "bestine.electionStarted");
        if (hasObjVar(player, "bestine.votedSean"))
        {
            int votedSeanElectionNum = getIntObjVar(player, "bestine.votedSean");
            if (votedSeanElectionNum >= electionNum)
            {
                tour_aryon_condition_alreadyVoted = true;
            }
        }
        if (hasObjVar(player, "bestine.votedVictor"))
        {
            int votedVictorElectionNum = getIntObjVar(player, "bestine.votedVictor");
            if (votedVictorElectionNum >= electionNum)
            {
                tour_aryon_condition_alreadyVoted = true;
            }
        }
        return tour_aryon_condition_alreadyVoted;
    }
    public boolean tour_aryon_condition_canVoteForVictor(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "bestine.camp"))
        {
            int electionNum = getIntObjVar(npc, "bestine.electionStarted");
            int playerElectionNum = getIntObjVar(player, "bestine.camp");
            if (playerElectionNum >= electionNum)
            {
                if (utils.playerHasItemByTemplate(player, "object/tangible/loot/quest/victor_questp_testimony.iff"))
                {
                    return true;
                }
                if (utils.playerHasItemByTemplate(player, "object/tangible/loot/quest/victor_questp_jregistry.iff"))
                {
                    return true;
                }
                if (utils.playerHasItemByTemplate(player, "object/tangible/loot/quest/victor_questp_receipt.iff"))
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean tour_aryon_condition_canVoteForSean(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "bestine.campaign"))
        {
            int electionNum = getIntObjVar(npc, "bestine.electionStarted");
            int playerElectionNum = getIntObjVar(player, "bestine.campaign");
            if (playerElectionNum >= electionNum)
            {
                if (utils.playerHasItemByTemplate(player, "object/tangible/loot/quest/sean_questp_ctestimony.iff"))
                {
                    return true;
                }
                if (utils.playerHasItemByTemplate(player, "object/tangible/loot/quest/sean_questp_mdisk.iff"))
                {
                    return true;
                }
                if (utils.playerHasItemByTemplate(player, "object/tangible/loot/quest/sean_questp_htestimony.iff"))
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean tour_aryon_condition_canVote(obj_id player, obj_id npc) throws InterruptedException
    {
        return ((!tour_aryon_condition_alreadyVoted(player, npc)) && ((tour_aryon_condition_canVoteForVictor(player, npc)) || (tour_aryon_condition_canVoteForSean(player, npc))));
    }
    public boolean tour_aryon_condition_electionStarted(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasObjVar(npc, "bestine.electionStarted");
    }
    public boolean tour_aryon_condition_seanWon(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.electionWinner"))
        {
            String winner = getStringObjVar(npc, "bestine.electionWinner");
            if (winner.equals("sean") || winner.equals("Sean"))
            {
                return true;
            }
        }
        return false;
    }
    public boolean tour_aryon_condition_victorWon(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.electionWinner"))
        {
            String winner = getStringObjVar(npc, "bestine.electionWinner");
            if (winner.equals("victor") || winner.equals("Victor"))
            {
                return true;
            }
        }
        return false;
    }
    public boolean tour_aryon_condition_allowTimeLeftRequest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.electionEnded"))
        {
            if (hasObjVar(npc, "bestine.timeNextElectionStarts"))
            {
                return true;
            }
        }
        return false;
    }
    public boolean tour_aryon_condition_moreThanAWeek(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.electionEnded"))
        {
            if (hasObjVar(npc, "bestine.timeNextElectionStarts"))
            {
                int timeNextElectionStarts = getIntObjVar(npc, "bestine.timeNextElectionStarts");
                int currentTime = getGameTime();
                int timeUntilElection = timeNextElectionStarts - currentTime;
                if (timeUntilElection >= 604800 && timeUntilElection < 1209600)
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean tour_aryon_condition_lessThanAWeek(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.electionEnded"))
        {
            if (hasObjVar(npc, "bestine.timeNextElectionStarts"))
            {
                int timeNextElectionStarts = getIntObjVar(npc, "bestine.timeNextElectionStarts");
                int currentTime = getGameTime();
                int timeUntilElection = timeNextElectionStarts - currentTime;
                if (timeUntilElection >= 345600 && timeUntilElection < 604800)
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean tour_aryon_condition_justAFewDays(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.electionEnded"))
        {
            if (hasObjVar(npc, "bestine.timeNextElectionStarts"))
            {
                int timeNextElectionStarts = getIntObjVar(npc, "bestine.timeNextElectionStarts");
                int currentTime = getGameTime();
                int timeUntilElection = timeNextElectionStarts - currentTime;
                if (timeUntilElection >= 172800 && timeUntilElection < 345600)
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean tour_aryon_condition_lessThanADay(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.electionEnded"))
        {
            if (hasObjVar(npc, "bestine.timeNextElectionStarts"))
            {
                int timeNextElectionStarts = getIntObjVar(npc, "bestine.timeNextElectionStarts");
                int currentTime = getGameTime();
                int timeUntilElection = timeNextElectionStarts - currentTime;
                if (timeUntilElection >= 14400 && timeUntilElection < 86400)
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean tour_aryon_condition_moreThan2Weeks(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.electionEnded"))
        {
            if (hasObjVar(npc, "bestine.timeNextElectionStarts"))
            {
                int timeNextElectionStarts = getIntObjVar(npc, "bestine.timeNextElectionStarts");
                int currentTime = getGameTime();
                int timeUntilElection = timeNextElectionStarts - currentTime;
                if (timeUntilElection >= 1209600)
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean tour_aryon_condition_justAFewHours(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.electionEnded"))
        {
            if (hasObjVar(npc, "bestine.timeNextElectionStarts"))
            {
                int timeNextElectionStarts = getIntObjVar(npc, "bestine.timeNextElectionStarts");
                int currentTime = getGameTime();
                int timeUntilElection = timeNextElectionStarts - currentTime;
                if (timeUntilElection >= 3600 && timeUntilElection < 14400)
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean tour_aryon_condition_withinTheHour(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.electionEnded"))
        {
            if (hasObjVar(npc, "bestine.timeNextElectionStarts"))
            {
                int timeNextElectionStarts = getIntObjVar(npc, "bestine.timeNextElectionStarts");
                int currentTime = getGameTime();
                int timeUntilElection = timeNextElectionStarts - currentTime;
                if (timeUntilElection < 3600)
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean tour_aryon_condition_moreThanADay(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.electionEnded"))
        {
            if (hasObjVar(npc, "bestine.timeNextElectionStarts"))
            {
                int timeNextElectionStarts = getIntObjVar(npc, "bestine.timeNextElectionStarts");
                int currentTime = getGameTime();
                int timeUntilElection = timeNextElectionStarts - currentTime;
                if (timeUntilElection >= 86400 && timeUntilElection < 172800)
                {
                    return true;
                }
            }
        }
        return false;
    }
    public void tour_aryon_action_removeSeanEvidence(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id evidenceItem01 = utils.getItemPlayerHasByTemplate(player, "object/tangible/loot/quest/sean_questp_ctestimony.iff");
        obj_id evidenceItem02 = utils.getItemPlayerHasByTemplate(player, "object/tangible/loot/quest/sean_questp_mdisk.iff");
        obj_id evidenceItem03 = utils.getItemPlayerHasByTemplate(player, "object/tangible/loot/quest/sean_questp_htestimony.iff");
        if (isIdValid(evidenceItem01))
        {
            destroyObject(evidenceItem01);
        }
        if (isIdValid(evidenceItem02))
        {
            destroyObject(evidenceItem02);
        }
        if (isIdValid(evidenceItem03))
        {
            destroyObject(evidenceItem03);
        }
        return;
    }
    public void tour_aryon_action_votedSean(obj_id player, obj_id npc) throws InterruptedException
    {
        int electionNum = getIntObjVar(npc, "bestine.electionStarted");
        setObjVar(player, "bestine.votedSean", electionNum);
        tour_aryon_action_removeSeanEvidence(player, npc);
        if (hasObjVar(player, "bestine.votedVictor"))
        {
            removeObjVar(player, "bestine.votedVictor");
        }
        tour_aryon_action_removeElectionObjVars(player, npc);
        if (hasObjVar(npc, "bestine.votesForSean"))
        {
            int votesForSean = (getIntObjVar(npc, "bestine.votesForSean")) + 1;
            setObjVar(npc, "bestine.votesForSean", votesForSean);
        }
        else 
        {
            setObjVar(npc, "bestine.votesForSean", 1);
        }
    }
    public void tour_aryon_action_votedVictor(obj_id player, obj_id npc) throws InterruptedException
    {
        int electionNum = getIntObjVar(npc, "bestine.electionStarted");
        setObjVar(player, "bestine.votedVictor", electionNum);
        tour_aryon_action_removeVictorEvidence(player, npc);
        if (hasObjVar(player, "bestine.votedSean"))
        {
            removeObjVar(player, "bestine.votedSean");
        }
        tour_aryon_action_removeElectionObjVars(player, npc);
        if (hasObjVar(npc, "bestine.votesForVictor"))
        {
            int votesForVictor = (getIntObjVar(npc, "bestine.votesForVictor")) + 1;
            setObjVar(npc, "bestine.votesForVictor", votesForVictor);
        }
        else 
        {
            setObjVar(npc, "bestine.votesForVictor", 1);
        }
    }
    public void tour_aryon_action_removeVictorEvidence(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id evidenceItem01 = utils.getItemPlayerHasByTemplate(player, "object/tangible/loot/quest/victor_questp_testimony.iff");
        obj_id evidenceItem02 = utils.getItemPlayerHasByTemplate(player, "object/tangible/loot/quest/victor_questp_jregistry.iff");
        obj_id evidenceItem03 = utils.getItemPlayerHasByTemplate(player, "object/tangible/loot/quest/victor_questp_receipt.iff");
        if (isIdValid(evidenceItem01))
        {
            destroyObject(evidenceItem01);
        }
        if (isIdValid(evidenceItem02))
        {
            destroyObject(evidenceItem02);
        }
        if (isIdValid(evidenceItem03))
        {
            destroyObject(evidenceItem03);
        }
        return;
    }
    public void tour_aryon_action_removeElectionObjVars(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "bestine.campaign"))
        {
            removeObjVar(player, "bestine.campaign");
        }
        if (hasObjVar(player, "bestine.camp"))
        {
            removeObjVar(player, "bestine.camp");
        }
        if (hasObjVar(player, "bestine.negquests"))
        {
            removeObjVar(player, "bestine.negquests");
        }
        if (hasObjVar(player, "bestine.negativeq"))
        {
            removeObjVar(player, "bestine.negativeq");
        }
        if (hasObjVar(player, "bestine.opponent"))
        {
            removeObjVar(player, "bestine.opponent");
        }
        if (hasObjVar(player, "bestine.rival"))
        {
            removeObjVar(player, "bestine.rival");
        }
        if (hasObjVar(player, "bestine.sean_university_noroom"))
        {
            removeObjVar(player, "bestine.sean_university_noroom");
        }
        if (hasObjVar(player, "bestine.sean_house_noroom"))
        {
            removeObjVar(player, "bestine.sean_house_noroom");
        }
        if (hasObjVar(player, "bestine.sean_market_noroom"))
        {
            removeObjVar(player, "bestine.sean_market_noroom");
        }
        if (hasObjVar(player, "bestine.sean_noroom"))
        {
            removeObjVar(player, "bestine.sean_noroom");
        }
        if (hasObjVar(player, "bestine.victor_cantina_noroom"))
        {
            removeObjVar(player, "bestine.victor_cantina_noroom");
        }
        if (hasObjVar(player, "bestine.victor_capitol_noroom"))
        {
            removeObjVar(player, "bestine.victor_capitol_noroom");
        }
        if (hasObjVar(player, "bestine.victor_hospital_noroom"))
        {
            removeObjVar(player, "bestine.victor_hospital_noroom");
        }
        if (hasObjVar(player, "bestine.victor_slums_noroom"))
        {
            removeObjVar(player, "bestine.victor_slums_noroom");
        }
        if (hasObjVar(player, "bestine.victor_museum_noroom"))
        {
            removeObjVar(player, "bestine.victor_museum_noroom");
        }
        if (hasObjVar(player, "bestine.victor_noroom"))
        {
            removeObjVar(player, "bestine.victor_noroom");
        }
    }
    public int tour_aryon_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_5d2e1112"))
        {
            if (tour_aryon_condition_seanWon(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1797f2fb");
                utils.removeScriptVar(player, "conversation.tour_aryon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tour_aryon_condition_victorWon(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_7420991b");
                utils.removeScriptVar(player, "conversation.tour_aryon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tour_aryon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_f9c0d87f");
                utils.removeScriptVar(player, "conversation.tour_aryon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_68d96c4a"))
        {
            if (tour_aryon_condition_withinTheHour(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_a87968a2");
                utils.removeScriptVar(player, "conversation.tour_aryon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tour_aryon_condition_justAFewHours(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_ae946995");
                utils.removeScriptVar(player, "conversation.tour_aryon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tour_aryon_condition_lessThanADay(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_bc2f5e3d");
                utils.removeScriptVar(player, "conversation.tour_aryon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tour_aryon_condition_moreThanADay(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_9fadb4cd");
                utils.removeScriptVar(player, "conversation.tour_aryon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tour_aryon_condition_justAFewDays(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_9e562b00");
                utils.removeScriptVar(player, "conversation.tour_aryon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tour_aryon_condition_lessThanAWeek(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_9b704e1e");
                utils.removeScriptVar(player, "conversation.tour_aryon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tour_aryon_condition_moreThanAWeek(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3dcd5980");
                utils.removeScriptVar(player, "conversation.tour_aryon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tour_aryon_condition_moreThan2Weeks(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_45511d66");
                utils.removeScriptVar(player, "conversation.tour_aryon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tour_aryon_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_fad1aba"))
        {
            if (tour_aryon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_2a9eedba");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tour_aryon_condition_canVote(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tour_aryon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1a74caee");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ccae64dd");
                    }
                    utils.setScriptVar(player, "conversation.tour_aryon.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tour_aryon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_ca478f48"))
        {
            if (tour_aryon_condition_alreadyVoted(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_2230b5b1");
                utils.removeScriptVar(player, "conversation.tour_aryon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (!tour_aryon_condition_alreadyVoted(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_b1c70765");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tour_aryon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tour_aryon_condition_canVote(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tour_aryon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bb225c00");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_65a4282");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fb124268");
                    }
                    utils.setScriptVar(player, "conversation.tour_aryon.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tour_aryon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_3001dad0"))
        {
            if (tour_aryon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_6beb48d3");
                utils.removeScriptVar(player, "conversation.tour_aryon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tour_aryon_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1a74caee"))
        {
            if (tour_aryon_condition_canVoteForVictor(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_bf3641cd");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tour_aryon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tour_aryon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b51737ff");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_91852029");
                    }
                    utils.setScriptVar(player, "conversation.tour_aryon.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tour_aryon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tour_aryon_condition_canVoteForSean(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_f3d3efe5");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tour_aryon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tour_aryon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_42");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_46");
                    }
                    utils.setScriptVar(player, "conversation.tour_aryon.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tour_aryon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tour_aryon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_7603d7cd");
                utils.removeScriptVar(player, "conversation.tour_aryon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_ccae64dd"))
        {
            if (tour_aryon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3e9e3870");
                utils.removeScriptVar(player, "conversation.tour_aryon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tour_aryon_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_bb225c00"))
        {
            if (tour_aryon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_5ff564e3");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tour_aryon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_540ac7e9");
                    }
                    utils.setScriptVar(player, "conversation.tour_aryon.branchId", 18);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tour_aryon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_65a4282"))
        {
            if (tour_aryon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_72");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tour_aryon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tour_aryon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tour_aryon_condition_canVote(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_74");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_90");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_104");
                    }
                    utils.setScriptVar(player, "conversation.tour_aryon.branchId", 37);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tour_aryon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_fb124268"))
        {
            if (tour_aryon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_107");
                utils.removeScriptVar(player, "conversation.tour_aryon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tour_aryon_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_540ac7e9"))
        {
            if (tour_aryon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_fbf7f817");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tour_aryon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e547fd1e");
                    }
                    utils.setScriptVar(player, "conversation.tour_aryon.branchId", 19);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tour_aryon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tour_aryon_handleBranch19(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_e547fd1e"))
        {
            if (tour_aryon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1ec9d0f0");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tour_aryon_condition_canVote(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tour_aryon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_47117f94");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3829680a");
                    }
                    utils.setScriptVar(player, "conversation.tour_aryon.branchId", 20);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tour_aryon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tour_aryon_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_47117f94"))
        {
            if (tour_aryon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_9bf39028");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tour_aryon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tour_aryon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tour_aryon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_34");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_23b3506c");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a44272b4");
                    }
                    utils.setScriptVar(player, "conversation.tour_aryon.branchId", 21);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tour_aryon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_3829680a"))
        {
            if (tour_aryon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_dfebee34");
                utils.removeScriptVar(player, "conversation.tour_aryon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tour_aryon_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_34"))
        {
            if (tour_aryon_condition_canVoteForVictor(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_bf3641cd");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tour_aryon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tour_aryon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b51737ff");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_91852029");
                    }
                    utils.setScriptVar(player, "conversation.tour_aryon.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tour_aryon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tour_aryon_condition_canVoteForSean(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_f3d3efe5");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tour_aryon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tour_aryon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_42");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_46");
                    }
                    utils.setScriptVar(player, "conversation.tour_aryon.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tour_aryon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tour_aryon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_7603d7cd");
                utils.removeScriptVar(player, "conversation.tour_aryon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_23b3506c"))
        {
            if (tour_aryon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_16d541d2");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tour_aryon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_53");
                    }
                    utils.setScriptVar(player, "conversation.tour_aryon.branchId", 29);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tour_aryon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_a44272b4"))
        {
            if (tour_aryon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_9b77406a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tour_aryon_condition_canVote(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tour_aryon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_90ec63e0");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1f2450ea");
                    }
                    utils.setScriptVar(player, "conversation.tour_aryon.branchId", 33);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tour_aryon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tour_aryon_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_b51737ff"))
        {
            if (tour_aryon_condition__defaultCondition(player, npc))
            {
                tour_aryon_action_votedVictor(player, npc);
                string_id message = new string_id(c_stringFile, "s_b1e3db3e");
                utils.removeScriptVar(player, "conversation.tour_aryon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_91852029"))
        {
            if (tour_aryon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_de483def");
                utils.removeScriptVar(player, "conversation.tour_aryon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tour_aryon_handleBranch25(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_42"))
        {
            if (tour_aryon_condition__defaultCondition(player, npc))
            {
                tour_aryon_action_votedSean(player, npc);
                string_id message = new string_id(c_stringFile, "s_44");
                utils.removeScriptVar(player, "conversation.tour_aryon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_46"))
        {
            if (tour_aryon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_48");
                utils.removeScriptVar(player, "conversation.tour_aryon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tour_aryon_handleBranch29(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_53"))
        {
            if (tour_aryon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3c509be9");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tour_aryon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_56");
                    }
                    utils.setScriptVar(player, "conversation.tour_aryon.branchId", 30);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tour_aryon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tour_aryon_handleBranch30(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_56"))
        {
            if (tour_aryon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_6ff89ddc");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tour_aryon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_eea34749");
                    }
                    utils.setScriptVar(player, "conversation.tour_aryon.branchId", 31);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tour_aryon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tour_aryon_handleBranch31(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_eea34749"))
        {
            if (tour_aryon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_ab50056b");
                utils.removeScriptVar(player, "conversation.tour_aryon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tour_aryon_handleBranch33(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_90ec63e0"))
        {
            if (tour_aryon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_23877744");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tour_aryon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_65");
                    }
                    utils.setScriptVar(player, "conversation.tour_aryon.branchId", 34);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tour_aryon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1f2450ea"))
        {
            if (tour_aryon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_567d69e1");
                utils.removeScriptVar(player, "conversation.tour_aryon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tour_aryon_handleBranch34(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_65"))
        {
            if (tour_aryon_condition_canVoteForVictor(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_bf3641cd");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tour_aryon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tour_aryon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b51737ff");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_91852029");
                    }
                    utils.setScriptVar(player, "conversation.tour_aryon.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tour_aryon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tour_aryon_condition_canVoteForSean(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_f3d3efe5");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tour_aryon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tour_aryon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_42");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_46");
                    }
                    utils.setScriptVar(player, "conversation.tour_aryon.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tour_aryon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tour_aryon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_7603d7cd");
                utils.removeScriptVar(player, "conversation.tour_aryon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tour_aryon_handleBranch37(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_74"))
        {
            if (tour_aryon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_76");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tour_aryon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_78");
                    }
                    utils.setScriptVar(player, "conversation.tour_aryon.branchId", 38);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tour_aryon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_90"))
        {
            if (tour_aryon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_92");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tour_aryon_condition_canVote(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tour_aryon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_94");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_100");
                    }
                    utils.setScriptVar(player, "conversation.tour_aryon.branchId", 42);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tour_aryon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_104"))
        {
            if (tour_aryon_condition_canVoteForVictor(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_bf3641cd");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tour_aryon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tour_aryon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b51737ff");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_91852029");
                    }
                    utils.setScriptVar(player, "conversation.tour_aryon.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tour_aryon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tour_aryon_condition_canVoteForSean(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_f3d3efe5");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tour_aryon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tour_aryon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_42");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_46");
                    }
                    utils.setScriptVar(player, "conversation.tour_aryon.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tour_aryon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tour_aryon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_7603d7cd");
                utils.removeScriptVar(player, "conversation.tour_aryon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tour_aryon_handleBranch38(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_78"))
        {
            if (tour_aryon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_80");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tour_aryon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_82");
                    }
                    utils.setScriptVar(player, "conversation.tour_aryon.branchId", 39);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tour_aryon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tour_aryon_handleBranch39(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_82"))
        {
            if (tour_aryon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_84");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tour_aryon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_86");
                    }
                    utils.setScriptVar(player, "conversation.tour_aryon.branchId", 40);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tour_aryon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tour_aryon_handleBranch40(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_86"))
        {
            if (tour_aryon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_88");
                utils.removeScriptVar(player, "conversation.tour_aryon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tour_aryon_handleBranch42(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_94"))
        {
            if (tour_aryon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_96");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tour_aryon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_98");
                    }
                    utils.setScriptVar(player, "conversation.tour_aryon.branchId", 43);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tour_aryon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_100"))
        {
            if (tour_aryon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_102");
                utils.removeScriptVar(player, "conversation.tour_aryon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tour_aryon_handleBranch43(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_98"))
        {
            if (tour_aryon_condition_canVoteForVictor(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_bf3641cd");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tour_aryon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tour_aryon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b51737ff");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_91852029");
                    }
                    utils.setScriptVar(player, "conversation.tour_aryon.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tour_aryon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tour_aryon_condition_canVoteForSean(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_f3d3efe5");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tour_aryon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tour_aryon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_42");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_46");
                    }
                    utils.setScriptVar(player, "conversation.tour_aryon.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tour_aryon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tour_aryon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_7603d7cd");
                utils.removeScriptVar(player, "conversation.tour_aryon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.tour_aryon");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info menuInfo) throws InterruptedException
    {
        int menu = menuInfo.addRootMenu(menu_info_types.CONVERSE_START, null);
        menu_info_data menuInfoData = menuInfo.getMenuItemById(menu);
        menuInfoData.setServerNotify(false);
        setCondition(self, CONDITION_CONVERSABLE);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        detachScript(self, "conversation.tour_aryon");
        return SCRIPT_CONTINUE;
    }
    public boolean npcStartConversation(obj_id player, obj_id npc, String convoName, string_id greetingId, prose_package greetingProse, string_id[] responses) throws InterruptedException
    {
        Object[] objects = new Object[responses.length];
        System.arraycopy(responses, 0, objects, 0, responses.length);
        return npcStartConversation(player, npc, convoName, greetingId, greetingProse, objects);
    }
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        obj_id npc = self;
        if (ai_lib.isInCombat(npc) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (tour_aryon_condition_electionEnded(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_57d5a9c4");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tour_aryon_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (tour_aryon_condition_allowTimeLeftRequest(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_5d2e1112");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_68d96c4a");
                }
                utils.setScriptVar(player, "conversation.tour_aryon.branchId", 1);
                npcStartConversation(player, npc, "tour_aryon", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tour_aryon_condition_electionStarted(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_c1091f5a");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tour_aryon_condition_canVote(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (tour_aryon_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (tour_aryon_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_fad1aba");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_ca478f48");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_3001dad0");
                }
                utils.setScriptVar(player, "conversation.tour_aryon.branchId", 13);
                npcStartConversation(player, npc, "tour_aryon", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tour_aryon_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_3b657772");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("tour_aryon"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.tour_aryon.branchId");
        if (branchId == 1 && tour_aryon_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && tour_aryon_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && tour_aryon_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && tour_aryon_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && tour_aryon_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && tour_aryon_handleBranch19(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && tour_aryon_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && tour_aryon_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && tour_aryon_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && tour_aryon_handleBranch25(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && tour_aryon_handleBranch29(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 30 && tour_aryon_handleBranch30(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 31 && tour_aryon_handleBranch31(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 33 && tour_aryon_handleBranch33(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 34 && tour_aryon_handleBranch34(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 37 && tour_aryon_handleBranch37(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 38 && tour_aryon_handleBranch38(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 39 && tour_aryon_handleBranch39(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 40 && tour_aryon_handleBranch40(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 42 && tour_aryon_handleBranch42(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 43 && tour_aryon_handleBranch43(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.tour_aryon.branchId");
        return SCRIPT_CONTINUE;
    }
}
