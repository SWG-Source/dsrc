package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.sui;
import script.library.utils;
import script.library.prose;
import script.library.pet_lib;
import script.library.factions;
import script.library.force_rank;
import script.library.trace;
import java.lang.Math;

public class arena extends script.base_script
{
    public arena()
    {
    }
    public static final String VAR_ARENA_OPEN_FOR_CHALLENGES = "arena.isOpen";
    public static final String VAR_CHALLENGE_DATA = "arena.challengeData";
    public static final String VAR_ARENA_LAST_OPEN_TIME = "arena.lastChallengeStartTime";
    public static final int CHALLENGE_PERIOD_LENGTH = 3600;
    public static final int ARENA_OPEN_LENGTH = 5400;
    public static final int ARENA_OPEN_EVERY = 108000;
    public static final String SCRIPT_VAR_SUI_CH_PID = "force_rank.challenge_sui";
    public static final String SCRIPT_VAR_CH_TERMINAL = "force_rank.challengeTerminal";
    public static final String SCRIPT_VAR_CH_VIEW_CHALLENGES = "force_rank.viewChallenges";
    public static final String SCRIPT_VAR_CH_SELECT_ACCEPT = "force_rank.viewAcceptChallenges";
    public static final String VAR_CH_RANKS_WHO_ANSWERED = "arena.challengeData.ranksThatAnswered";
    public static final String VAR_CH_RANKS_GOT_CHALLENGED = "arena.challengeData.ranksThatGotChallenged";
    public static final String VAR_CHALLENGERS = "arena.challengeData.challengers";
    public static final String VAR_CHALLENGED_RANKS = "arena.challengeData.ranksChallenged";
    public static final String VAR_CHALLENGE_TIMES = "arena.challengeData.challengeTimes";
    public static final String VAR_ALL_RANKS = "arena.allRanks";
    public static final String VAR_RANK_CHALLENGE_SCORES = "arena.rankChallengeScores";
    public static final int MISSED_CHALLENGE_PENALTY = -1;
    public static final int MET_CHALLENGE_REWARD = 0;
    public static final int CHALLENGE_SCORE_CEILING = 0;
    public static final int PENALTY_DEMOTE_THRESHOLD = -8;
    public static final float PERCENT_MET_CHALLENGE = 0.50f;
    public static final boolean COUNT_NO_CHALLENGER_AS_PASSED = true;
    public static final String VAR_ACCEPTED_CHALLENGERS = "arena.challengeData.acceptedChallengers";
    public static final String VAR_ACCEPTED_DEFENDERS = "arena.challengeData.acceptedDefenders";
    public static final String VAR_ACCEPTED_CHALLENGE_RANKS = "arena.challengeData.acceptedChallengeRanks";
    public static final String VAR_MY_ARENA_TERMINAL = "arena.terminalId";
    public static final String VAR_MY_ARENA = "arena.arenaCellId";
    public static final String VAR_I_AM_ARENA = "arena.iAmArena";
    public static final String VAR_LAST_CHALLENGE_TIME = "force_rank.lastRankChallengeTime";
    public static final int CHALLENGE_TIME_INTERVAL = 90000;
    public static final String MAIL_FROM_ENCLAVE = "Hidden Enclave";
    public static final String VAR_I_AM_DUELING = "force_rank.rankChallengeDuelInProgress";
    public static final String VAR_CHALLENGE_TERMINAL = "force_rank.challengeTerminal";
    public static final float ARENA_X_MIN = -13.0f;
    public static final float ARENA_X_MAX = 13.0f;
    public static final float ARENA_Z_MIN = -86.0f;
    public static final float ARENA_Z_MAX = -60.0f;
    public static final float ARENA_Y = -47.42435f;
    public static float getArenaTimeFactorFromConfig() throws InterruptedException
    {
        String strVal = getConfigSetting("GameServer", "darkArenaTimeFactor");
        if (strVal == null || strVal.equals(""))
        {
            return 1.0f;
        }
        float flVal = utils.stringToFloat(strVal);
        if (flVal == Float.NEGATIVE_INFINITY)
        {
            return 1.0f;
        }
        return flVal;
    }
    public static int getChallengePeriodLength() throws InterruptedException
    {
        return (int)Math.ceil(((float)CHALLENGE_PERIOD_LENGTH) * getArenaTimeFactorFromConfig());
    }
    public static int getArenaOpenLength() throws InterruptedException
    {
        return (int)Math.ceil(((float)ARENA_OPEN_LENGTH) * getArenaTimeFactorFromConfig());
    }
    public static int getArenaOpenEvery() throws InterruptedException
    {
        return (int)Math.ceil(((float)ARENA_OPEN_EVERY) * getArenaTimeFactorFromConfig());
    }
    public static int getChallengeTimeInterval() throws InterruptedException
    {
        return (int)Math.ceil(((float)CHALLENGE_TIME_INTERVAL) * getArenaTimeFactorFromConfig());
    }
    public static void addRankAnsweredChallenge(obj_id terminal, int rankA) throws InterruptedException
    {
        Vector ranksA = new Vector();
        ranksA.setSize(0);
        if (hasObjVar(terminal, VAR_CH_RANKS_WHO_ANSWERED))
        {
            utils.concatArrays(ranksA, utils.getIntBatchObjVar(terminal, VAR_CH_RANKS_WHO_ANSWERED));
        }
        utils.addElement(ranksA, rankA);
        utils.setBatchObjVar(terminal, VAR_CH_RANKS_WHO_ANSWERED, ranksA.toArray());
        return;
    }
    public static void addRankGotChallenged(obj_id terminal, int rankC) throws InterruptedException
    {
        Vector ranksC = new Vector();
        ranksC.setSize(0);
        if (hasObjVar(terminal, VAR_CH_RANKS_GOT_CHALLENGED))
        {
            utils.concatArrays(ranksC, utils.getIntBatchObjVar(terminal, VAR_CH_RANKS_GOT_CHALLENGED));
        }
        utils.addElement(ranksC, rankC);
        utils.setBatchObjVar(terminal, VAR_CH_RANKS_GOT_CHALLENGED, ranksC.toArray());
        return;
    }
    public static Vector getRanksWithActiveChallenges(obj_id terminal) throws InterruptedException
    {
        Vector ranks = new Vector();
        ranks.setSize(0);
        if (!hasObjVar(terminal, VAR_CHALLENGED_RANKS) || !hasObjVar(terminal, VAR_ALL_RANKS))
        {
            return ranks;
        }
        int[] allRanks = getIntArrayObjVar(terminal, VAR_ALL_RANKS);
        int[] challengedRanks = utils.getIntBatchObjVar(terminal, VAR_CHALLENGED_RANKS);
        for (int i = 0; i < allRanks.length; i++)
        {
            if (utils.getElementPositionInArray(challengedRanks, allRanks[i]) > -1)
            {
                utils.addElement(ranks, allRanks[i]);
            }
        }
        return ranks;
    }
    public static String[] getRanksWithActiveChallengesString(obj_id terminal) throws InterruptedException
    {
        Vector ranks = getRanksWithActiveChallenges(terminal);
        String[] sranks = new String[ranks.size()];
        for (int i = 0; i < ranks.size(); i++)
        {
            sranks[i] = "Rank " + ((Integer)ranks.get(i)).intValue();
        }
        return sranks;
    }
    public static void resolveRankChallengeResult(obj_id terminal, int rank, int timesChallenged, int timesAnswered) throws InterruptedException
    {
        if (!hasObjVar(terminal, VAR_RANK_CHALLENGE_SCORES) || !hasObjVar(terminal, VAR_ALL_RANKS))
        {
            LOG("force_rank", "arena::resolveRankChallengeResult: -> missing VAR_RANK_CHALLENGE_SCORES.  Rank score not updated.");
            return;
        }
        int[] ranks = getIntArrayObjVar(terminal, VAR_ALL_RANKS);
        int[] score = getIntArrayObjVar(terminal, VAR_RANK_CHALLENGE_SCORES);
        if (ranks.length != score.length)
        {
            trace.log("force_rank", "arena::resolveRankChallengeResult: -> ranks.length=" + ranks.length + " and score.length=" + score.length + ". Unacceptable. Not updating ranks challenge score.", null, trace.TL_ERROR_LOG | trace.TL_DEBUG);
        }
        int idx = utils.getElementPositionInArray(ranks, rank);
        if (idx < 0)
        {
            trace.log("force_rank", "arena::resolveRankChallengeResult: -> Can't update rank " + rank + " challenge score as there is no record for this rank.", null, trace.TL_ERROR_LOG | trace.TL_DEBUG);
            return;
        }
        float rateAnswered = (float)timesAnswered / (float)timesChallenged;
        if (rateAnswered >= PERCENT_MET_CHALLENGE || (COUNT_NO_CHALLENGER_AS_PASSED && timesChallenged == 0))
        {
            trace.log("force_rank", "Rank #" + rank + " met all requirements for this challenge period.", null, trace.TL_CS_LOG);
            if (MET_CHALLENGE_REWARD == 0)
            {
                score[idx] = 0;
            }
            else 
            {
                score[idx] += MET_CHALLENGE_REWARD;
                if (score[idx] > CHALLENGE_SCORE_CEILING)
                {
                    score[idx] = CHALLENGE_SCORE_CEILING;
                }
            }
        }
        else 
        {
            score[idx] += MISSED_CHALLENGE_PENALTY;
            trace.log("force_rank", "Rank #" + rank + " failed to meet the requirements for this challenge period. New rank challenge score is " + score[idx] + ".", null, trace.TL_CS_LOG);
        }
        if (score[idx] <= PENALTY_DEMOTE_THRESHOLD)
        {
            obj_id enclave = getTopMostContainer(terminal);
            if (!hasScript(enclave, force_rank.SCRIPT_ENCLAVE_CONTROLLER))
            {
                trace.log("force_rank", "arena::resolveRankChallengeResult -- voting terminal " + terminal + " is not in an enclave. Not demoting rank " + rank, null, trace.TL_ERROR_LOG | trace.TL_DEBUG);
                return;
            }
            prose_package ppDemote = prose.getPackage(new string_id("pvp_rating", "ch_terminal_demote_rank_penalty"), rank);
            Vector members = force_rank.getAllPlayerNamesInForceRank(enclave);
            string_id subj = new string_id("pvp_rating", "ch_terminal_demote_subject");
            for (int i = 0; i < members.size(); i++)
            {
                utils.sendMail(subj, ppDemote, (String)members.get(i), MAIL_FROM_ENCLAVE);
            }
            String[] demotees = force_rank.getPlayersInForceRank(enclave, rank);
            trace.log("force_rank", "Initiating demotion for rank #" + rank + " (currently has " + demotees.length + " members): Rank challenge score too low.", null, trace.TL_CS_LOG);
            for (int x = 0; x < demotees.length; x++)
            {
                trace.log("force_rank", "Arena initiating low-challenge-score demotion for player " + demotees[x], getPlayerIdFromFirstName(demotees[x]), trace.TL_CS_LOG);
                force_rank.demoteForceRank(enclave, demotees[x], 0);
            }
            score[idx] = 0;
        }
        trace.log("force_rank", "Rank #" + rank + " now has a new challenge score of " + score[idx], null, trace.TL_CS_LOG);
        setObjVar(terminal, VAR_ALL_RANKS, ranks);
        setObjVar(terminal, VAR_RANK_CHALLENGE_SCORES, score);
        return;
    }
    public static String[] getChallengeScoreStrings(obj_id terminal) throws InterruptedException
    {
        if (!hasObjVar(terminal, VAR_ALL_RANKS))
        {
            trace.log("force_rank", "arena::getChallengeScoreStrings: -> Terminal doesnt have the necessary rank data setup!", terminal, trace.TL_ERROR_LOG | trace.TL_DEBUG);
            return new String[0];
        }
        int[] ranks = getIntArrayObjVar(terminal, VAR_ALL_RANKS);
        int[] score = getIntArrayObjVar(terminal, VAR_RANK_CHALLENGE_SCORES);
        String[] strings = new String[ranks.length];
        for (int i = 0; i < ranks.length; i++)
        {
            strings[i] = "Score for rank " + ranks[i] + "  -->  " + score[i];
        }
        return strings;
    }
    public static void setupRankScores(obj_id terminal) throws InterruptedException
    {
        if (!hasObjVar(terminal, VAR_RANK_CHALLENGE_SCORES))
        {
            int[] scores = 
            {
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0
            };
            int[] ranks = 
            {
                2,
                3,
                4,
                5,
                6,
                7,
                8,
                9,
                10,
                11
            };
            setObjVar(terminal, VAR_RANK_CHALLENGE_SCORES, scores);
            setObjVar(terminal, VAR_ALL_RANKS, ranks);
        }
        return;
    }
    public static void initializeArena(obj_id terminal) throws InterruptedException
    {
        cleanupChallengeData(terminal);
        setupRankScores(terminal);
        utils.setScriptVar(terminal, "arenaTimeFactorUsed", getArenaTimeFactorFromConfig());
        if (!hasObjVar(terminal, VAR_ARENA_LAST_OPEN_TIME))
        {
            openArenaForChallenges(terminal);
        }
        else 
        {
            closeArenaForChallenges(terminal);
        }
        obj_id enclave = getTopMostContainer(terminal);
        if (!isIdValid(enclave))
        {
            trace.log("force_rank", "terminal_frs_voting.OnInitialize -- voting terminal " + terminal + " is not in an enclave.", terminal, trace.TL_ERROR_LOG | trace.TL_DEBUG);
            return;
        }
        if (!hasScript(enclave, force_rank.SCRIPT_ENCLAVE_CONTROLLER))
        {
            trace.log("force_rank", "terminal_frs_voting.OnInitialize -- voting terminal " + terminal + " is not in an enclave.", terminal, trace.TL_ERROR_LOG | trace.TL_DEBUG);
            return;
        }
        utils.setScriptVar(enclave, VAR_CHALLENGE_TERMINAL, terminal);
        obj_id arenaCell = getContainedBy(terminal);
        if (arenaCell != null)
        {
            utils.setScriptVar(arenaCell, VAR_MY_ARENA_TERMINAL, terminal);
            utils.setScriptVar(terminal, VAR_MY_ARENA, arenaCell);
            if (!hasScript(arenaCell, "systems.gcw.dark_jedi_arena"))
            {
                attachScript(arenaCell, "systems.gcw.dark_jedi_arena");
            }
        }
        return;
    }
    public static void cleanupChallengeData(obj_id terminal) throws InterruptedException
    {
        removeObjVar(terminal, VAR_CHALLENGE_DATA);
    }
    public static void checkArenaMaintenance(obj_id enclave) throws InterruptedException
    {
        if (!utils.hasScriptVar(enclave, VAR_CHALLENGE_TERMINAL))
        {
            trace.log("force_rank", "arena::checkArenaMaintenance: -> enclave missing challenge terminal scriptvar.", enclave, trace.TL_ERROR_LOG | trace.TL_DEBUG);
            return;
        }
        obj_id terminal = utils.getObjIdScriptVar(enclave, VAR_CHALLENGE_TERMINAL);
        if (!isIdValid(terminal))
        {
            trace.log("force_rank", "arena::checkArenaMaintenance: -> terminal is not valid. maintenance not done.", enclave, trace.TL_ERROR_LOG | trace.TL_DEBUG);
            return;
        }
        if (!hasObjVar(terminal, VAR_ARENA_LAST_OPEN_TIME))
        {
            return;
        }
        int lastOpen = getIntObjVar(terminal, VAR_ARENA_LAST_OPEN_TIME);
        int now = getGameTime();
        boolean closedArenaThisCycle = false;
        if (isArenaOpenForChallenges(terminal))
        {
            if (lastOpen + getArenaOpenLength() <= now)
            {
                closeArenaForChallenges(terminal);
                closedArenaThisCycle = true;
            }
        }
        else 
        {
            if (lastOpen + getArenaOpenLength() + getArenaOpenEvery() <= now)
            {
                openArenaForChallenges(terminal);
            }
        }
        Vector challengers = new Vector();
        challengers.setSize(0);
        Vector ranks = new Vector();
        ranks.setSize(0);
        Vector timeStamps = new Vector();
        timeStamps.setSize(0);
        Vector acceptedChallengers = new Vector();
        acceptedChallengers.setSize(0);
        if (hasObjVar(terminal, VAR_ACCEPTED_CHALLENGERS))
        {
            utils.concatArrays(acceptedChallengers, utils.getObjIdBatchObjVar(terminal, VAR_ACCEPTED_CHALLENGERS));
        }
        if (hasObjVar(terminal, VAR_CHALLENGERS))
        {
            challengers.addAll(utils.getResizeableObjIdBatchObjVar(terminal, VAR_CHALLENGERS));
        }
        if (hasObjVar(terminal, VAR_CHALLENGED_RANKS))
        {
            ranks.addAll(utils.getResizeableIntBatchObjVar(terminal, VAR_CHALLENGED_RANKS));
        }
        if (hasObjVar(terminal, VAR_CHALLENGE_TIMES))
        {
            timeStamps.addAll(utils.getResizeableIntBatchObjVar(terminal, VAR_CHALLENGE_TIMES));
        }
        int len = challengers.size();
        if (ranks.size() != len || timeStamps.size() != len)
        {
            trace.log("force_rank", "arena::checkArenaMaintenance: -> Existing data on challengeTerminal is out of synch: challengers.length=" + challengers.size() + ", ranks.length=" + ranks.size() + ", timeStamps.length=" + timeStamps.size() + ".", terminal, trace.TL_ERROR_LOG | trace.TL_DEBUG);
            return;
        }
        Vector players = new Vector();
        players.setSize(0);
        int timePassed = 0;
        int timeRemaining = 0;
        String timeLeft = "";
        int i = 0;
        boolean removedAChallenger = false;
        while (i < challengers.size())
        {
            timeRemaining = (((Integer)timeStamps.get(i)).intValue() + getChallengePeriodLength()) - now;
            if (timeRemaining < 0)
            {
                trace.log("force_rank", "Dark arena challenge issued by %TU against rank #" + ((Integer)ranks.get(i)).intValue() + " has timed out.", ((obj_id)challengers.get(i)), trace.TL_CS_LOG);
                notifyChallengeTimedOut(terminal, ((obj_id)challengers.get(i)), ((Integer)ranks.get(i)).intValue());
                utils.removeElementAt(challengers, i);
                utils.removeElementAt(ranks, i);
                utils.removeElementAt(timeStamps, i);
                removedAChallenger = true;
            }
            else 
            {
                i++;
            }
        }
        trace.log("force_rank", "arena::checkArenaMaintenange-> challengers.length=" + challengers.size() + ", acceptedChallengers.length=" + acceptedChallengers.size(), null, trace.TL_DEBUG);
        if (challengers.size() > 0)
        {
            utils.setBatchObjVar(terminal, VAR_CHALLENGERS, challengers.toArray());
            utils.setBatchObjVar(terminal, VAR_CHALLENGED_RANKS, ranks.toArray());
            utils.setBatchObjVar(terminal, VAR_CHALLENGE_TIMES, timeStamps.toArray());
        }
        else 
        {
            removeObjVar(terminal, VAR_CHALLENGERS);
            removeObjVar(terminal, VAR_CHALLENGED_RANKS);
            removeObjVar(terminal, VAR_CHALLENGE_TIMES);
            if ((!isArenaOpenForChallenges(terminal) && acceptedChallengers.size() < 1) && (closedArenaThisCycle || removedAChallenger))
            {
                dishOutChallengeAnswerPointsToRanks(terminal);
            }
            else 
            {
            }
        }
        return;
    }
    public static void dishOutChallengeAnswerPointsToRanks(obj_id terminal) throws InterruptedException
    {
        if (!hasObjVar(terminal, VAR_ALL_RANKS))
        {
            trace.log("force_rank", "arena::dishOutChallengeAnswerPointsToRanks: -> missing VAR_ALL_RANKS.  Bailing.", terminal, trace.TL_ERROR_LOG | trace.TL_DEBUG);
            removeObjVar(terminal, VAR_CH_RANKS_GOT_CHALLENGED);
            removeObjVar(terminal, VAR_CH_RANKS_WHO_ANSWERED);
            return;
        }
        int[] challenged = utils.getIntBatchObjVar(terminal, VAR_CH_RANKS_GOT_CHALLENGED);
        int[] answered = utils.getIntBatchObjVar(terminal, VAR_CH_RANKS_WHO_ANSWERED);
        int[] allRanks = getIntArrayObjVar(terminal, VAR_ALL_RANKS);
        int rankChallenges = 0;
        int rankChallengesAccepted = 0;
        for (int i = 0; i < allRanks.length; i++)
        {
            if (challenged != null)
            {
                for (int x = 0; x < challenged.length; x++)
                {
                    if (challenged[x] == allRanks[i])
                    {
                        rankChallenges++;
                    }
                }
            }
            if (answered != null)
            {
                for (int y = 0; y < answered.length; y++)
                {
                    if (answered[y] == allRanks[i])
                    {
                        rankChallengesAccepted++;
                    }
                }
            }
            trace.log("force_rank", "Rank #" + allRanks[i] + " was challenged " + rankChallenges + " times and answered " + rankChallengesAccepted + " times.", null, trace.TL_CS_LOG);
            resolveRankChallengeResult(terminal, allRanks[i], rankChallenges, rankChallengesAccepted);
            rankChallenges = 0;
            rankChallengesAccepted = 0;
        }
        removeObjVar(terminal, VAR_CH_RANKS_GOT_CHALLENGED);
        removeObjVar(terminal, VAR_CH_RANKS_WHO_ANSWERED);
        return;
    }
    public static void openArenaForChallenges(obj_id terminal) throws InterruptedException
    {
        trace.log("force_rank", "Opening arena for challenges (GameTime: " + getGameTime() + ").", null, trace.TL_CS_LOG | trace.TL_DEBUG);
        setObjVar(terminal, VAR_ARENA_OPEN_FOR_CHALLENGES, true);
        setObjVar(terminal, VAR_ARENA_LAST_OPEN_TIME, getGameTime());
        return;
    }
    public static void closeArenaForChallenges(obj_id terminal) throws InterruptedException
    {
        trace.log("force_rank", "Closing arena for challenges (GameTime: " + getGameTime() + ").", null, trace.TL_CS_LOG | trace.TL_DEBUG);
        setObjVar(terminal, VAR_ARENA_OPEN_FOR_CHALLENGES, false);
        return;
    }
    public static boolean isArenaOpenForChallenges(obj_id terminal) throws InterruptedException
    {
        if (!hasObjVar(terminal, VAR_ARENA_OPEN_FOR_CHALLENGES))
        {
            return false;
        }
        return getBooleanObjVar(terminal, VAR_ARENA_OPEN_FOR_CHALLENGES);
    }
    public static obj_id getMyArena(obj_id terminal) throws InterruptedException
    {
        if (!hasObjVar(terminal, VAR_MY_ARENA))
        {
            return null;
        }
        return getObjIdObjVar(terminal, VAR_MY_ARENA);
    }
    public static void notifyChallengeIssued(obj_id terminal, obj_id challenger, int rankChallenged) throws InterruptedException
    {
        obj_id enclave = getTopMostContainer(terminal);
        if (!isIdValid(enclave))
        {
            trace.log("force_rank", "arena::notifyChallengeIssued: -> enclave is not valid.  Not notifying anyone of issued challenge.", terminal, trace.TL_ERROR_LOG | trace.TL_DEBUG);
            return;
        }
        prose_package ppChallenger = prose.getPackage(new string_id("pvp_rating", "challenge_issued_challenger"), rankChallenged);
        sendSystemMessageProse(challenger, ppChallenger);
        obj_id[] rankMembers = force_rank.getAllPlayersInForceRank(enclave, rankChallenged);
        prose_package ppRank = new prose_package();
        ppRank.target.set(utils.getRealPlayerFirstName(challenger));
        ppRank.digitInteger = rankChallenged;
        ppRank.stringId = new string_id("pvp_rating", "challenge_issued_rank");
        string_id msgSubject = new string_id("pvp_rating", "challenge_issued_subject_header");
        for (int i = 0; i < rankMembers.length; i++)
        {
            sendSystemMessageProse(rankMembers[i], ppRank);
            utils.sendMail(msgSubject, ppRank, utils.getRealPlayerFirstName(rankMembers[i]), MAIL_FROM_ENCLAVE);
        }
        return;
    }
    public static void notifyChallengeAccepted(obj_id terminal, obj_id challenger, obj_id defender, int rankFightingFor) throws InterruptedException
    {
        obj_id enclave = getTopMostContainer(terminal);
        if (!isIdValid(enclave))
        {
            trace.log("force_rank", "arena::notifyChallengeAccepted: -> enclave is not valid.  Not notifying anyone of accepted challenge.", terminal, trace.TL_ERROR_LOG | trace.TL_DEBUG);
            return;
        }
        prose_package ppAll = new prose_package();
        ppAll.actor.set(utils.getRealPlayerFirstName(defender));
        ppAll.target.set(utils.getRealPlayerFirstName(challenger));
        ppAll.digitInteger = rankFightingFor;
        ppAll.stringId = new string_id("pvp_rating", "challenge_accepted");
        sendSystemMessageProse(challenger, ppAll);
        obj_id[] rankMembers = force_rank.getAllPlayersInForceRank(enclave, rankFightingFor);
        string_id msgSubject = new string_id("pvp_rating", "challenge_accepted_subject_header");
        for (int i = 0; i < rankMembers.length; i++)
        {
            sendSystemMessageProse(rankMembers[i], ppAll);
            utils.sendMail(msgSubject, ppAll, utils.getRealPlayerFirstName(rankMembers[i]), MAIL_FROM_ENCLAVE);
        }
        return;
    }
    public static void notifyChallengeConcluded(obj_id terminal, obj_id challenger, obj_id defender, boolean challengerWon, int rankFightingFor) throws InterruptedException
    {
        obj_id enclave = getTopMostContainer(terminal);
        if (!isIdValid(enclave))
        {
            trace.log("force_rank", "arena::notifyChallengeConcluded: -> enclave is not valid.  Not notifying anyone of accepted challenge.", terminal, trace.TL_ERROR_LOG | trace.TL_DEBUG);
            return;
        }
        String whichString = "challenge_concluded_challenger_won";
        if (!challengerWon)
        {
            whichString = "challenge_concluded_defender_win";
        }
        prose_package ppAll = new prose_package();
        ppAll.actor.set(utils.getRealPlayerFirstName(defender));
        ppAll.target.set(utils.getRealPlayerFirstName(challenger));
        ppAll.digitInteger = rankFightingFor;
        ppAll.stringId = new string_id("pvp_rating", whichString);
        string_id msgSubject = new string_id("pvp_rating", "challenge_concluded_subject_header");
        sendSystemMessageProse(challenger, ppAll);
        utils.sendMail(msgSubject, ppAll, utils.getRealPlayerFirstName(challenger), MAIL_FROM_ENCLAVE);
        obj_id[] rankMembers = force_rank.getAllPlayersInForceRank(enclave, rankFightingFor);
        for (int i = 0; i < rankMembers.length; i++)
        {
            sendSystemMessageProse(rankMembers[i], ppAll);
            utils.sendMail(msgSubject, ppAll, utils.getRealPlayerFirstName(rankMembers[i]), MAIL_FROM_ENCLAVE);
        }
        return;
    }
    public static void notifyChallengeTimedOut(obj_id terminal, obj_id challenger, int rankChallenged) throws InterruptedException
    {
        obj_id enclave = getTopMostContainer(terminal);
        if (!isIdValid(enclave))
        {
            trace.log("force_rank", "arena::notifyChallengeTimedOut: -> enclave is not valid.  Not notifying anyone of timed out challenge.", terminal, trace.TL_ERROR_LOG | trace.TL_DEBUG);
            return;
        }
        prose_package ppAll = new prose_package();
        ppAll.target.set(utils.getRealPlayerFirstName(challenger));
        ppAll.digitInteger = rankChallenged;
        ppAll.stringId = new string_id("pvp_rating", "challenge_concluded_timeout");
        sendSystemMessageProse(challenger, ppAll);
        obj_id[] rankMembers = force_rank.getAllPlayersInForceRank(enclave, rankChallenged);
        string_id msgSubject = new string_id("pvp_rating", "challenge_timout_subject_header");
        for (int i = 0; i < rankMembers.length; i++)
        {
            sendSystemMessageProse(rankMembers[i], ppAll);
            utils.sendMail(msgSubject, ppAll, utils.getRealPlayerFirstName(rankMembers[i]), MAIL_FROM_ENCLAVE);
        }
        return;
    }
    public static Vector getChallengerIdsForRank(obj_id challengeTerminal, int rank) throws InterruptedException
    {
        obj_id[] challengers = new obj_id[0];
        int[] ranks = new int[0];
        Vector players = new Vector();
        players.setSize(0);
        if (hasObjVar(challengeTerminal, VAR_CHALLENGERS))
        {
            challengers = utils.getObjIdBatchObjVar(challengeTerminal, VAR_CHALLENGERS);
        }
        if (hasObjVar(challengeTerminal, VAR_CHALLENGED_RANKS))
        {
            ranks = utils.getIntBatchObjVar(challengeTerminal, VAR_CHALLENGED_RANKS);
        }
        if (ranks.length != challengers.length)
        {
            trace.log("force_rank", "arena::getChallengerIdsForRank: -> Existing data on challengeTerminal is out of synch: challengers.length=" + challengers.length + ", ranks.length=" + ranks.length + ".", challengeTerminal, trace.TL_ERROR_LOG | trace.TL_DEBUG);
            return players;
        }
        for (int i = 0; i < challengers.length; i++)
        {
            if (ranks[i] == rank)
            {
                utils.addElement(players, challengers[i]);
            }
        }
        return players;
    }
    public static String[] getChallengerNamesForRank(obj_id challengeTerminal, int rank) throws InterruptedException
    {
        Vector players = getChallengerIdsForRank(challengeTerminal, rank);
        return utils.makeNameList(players);
    }
    public static String[] getChallengerNamesWithTimeRemaining(obj_id challengeTerminal, int rank) throws InterruptedException
    {
        obj_id[] challengers = new obj_id[0];
        int[] ranks = new int[0];
        int[] timeStamps = new int[0];
        if (hasObjVar(challengeTerminal, VAR_CHALLENGERS))
        {
            challengers = utils.getObjIdBatchObjVar(challengeTerminal, VAR_CHALLENGERS);
        }
        if (hasObjVar(challengeTerminal, VAR_CHALLENGED_RANKS))
        {
            ranks = utils.getIntBatchObjVar(challengeTerminal, VAR_CHALLENGED_RANKS);
        }
        if (hasObjVar(challengeTerminal, VAR_CHALLENGE_TIMES))
        {
            timeStamps = utils.getIntBatchObjVar(challengeTerminal, VAR_CHALLENGE_TIMES);
        }
        int len = challengers.length;
        if (ranks.length != len || timeStamps.length != len)
        {
            trace.log("force_rank", "arena::getChallengerNamesWithTimeRemaining: -> Existing data on challengeTerminal is out of synch: challengers.length=" + challengers.length + ", ranks.length=" + ranks.length + ", timeStamps.length=" + timeStamps.length + ".", challengeTerminal, trace.TL_ERROR_LOG | trace.TL_DEBUG);
            return new String[0];
        }
        Vector players = new Vector();
        players.setSize(0);
        int now = getGameTime();
        int timePassed = 0;
        int timeRemaining = 0;
        String timeLeft = "";
        for (int i = 0; i < challengers.length; i++)
        {
            if (ranks[i] == rank && isIdValid(challengers[i]))
            {
                timeRemaining = (timeStamps[i] + getChallengePeriodLength()) - now;
                if (timeRemaining < 0)
                {
                    timeLeft = " ( challenge expiring shortly )";
                }
                else if (timeRemaining < 60)
                {
                    timeLeft = " ( less than one minute remaining on challenge )";
                }
                else 
                {
                    timeLeft = " ( " + timeRemaining / 60 + " minutes remaining on this challenge )";
                }
                utils.addElement(players, utils.getRealPlayerFirstName(challengers[i]) + timeLeft);
            }
        }
        String[] arrayNames = utils.toStaticStringArray(players);
        return arrayNames;
    }
    public static boolean addChallengeIssueData(obj_id challengeTerminal, obj_id challenger, int rank) throws InterruptedException
    {
        obj_id enclave = getTopMostContainer(challengeTerminal);
        if (!isIdValid(enclave))
        {
            trace.log("force_rank", "arena::addChallengeIssueData: -> enclave is not valid.  Not adding challenge issued data.", challengeTerminal, trace.TL_ERROR_LOG | trace.TL_DEBUG);
            return false;
        }
        if (hasChallengeIssuedCurrently(challenger, challengeTerminal))
        {
            trace.log("force_rank", "arena::addChallengeIssuData: -> " + utils.getRealPlayerFirstName(challenger) + " already has a challenge issued.  Not adding another one.", challengeTerminal, trace.TL_WARNING | trace.TL_DEBUG);
            return false;
        }
        Vector challengers = new Vector();
        challengers.setSize(0);
        Vector ranks = new Vector();
        ranks.setSize(0);
        Vector timeStamps = new Vector();
        timeStamps.setSize(0);
        if (hasObjVar(challengeTerminal, VAR_CHALLENGERS))
        {
            utils.concatArrays(challengers, utils.getObjIdBatchObjVar(challengeTerminal, VAR_CHALLENGERS));
        }
        if (hasObjVar(challengeTerminal, VAR_CHALLENGED_RANKS))
        {
            utils.concatArrays(ranks, utils.getIntBatchObjVar(challengeTerminal, VAR_CHALLENGED_RANKS));
        }
        if (hasObjVar(challengeTerminal, VAR_CHALLENGE_TIMES))
        {
            utils.concatArrays(timeStamps, utils.getIntBatchObjVar(challengeTerminal, VAR_CHALLENGE_TIMES));
        }
        int len = challengers.size();
        if (ranks.size() != len || timeStamps.size() != len)
        {
            trace.log("force_rank", "arena::addChallengeIssueData: -> Existing data on challengeTerminal is out of synch: challengers.length=" + challengers.size() + ", ranks.length=" + ranks.size() + ", timeStamps.length=" + timeStamps.size() + ". Not adding challenge data for " + utils.getRealPlayerFirstName(challenger), challengeTerminal, trace.TL_ERROR_LOG | trace.TL_DEBUG);
            return false;
        }
        int challengerRank = force_rank.getForceRank(enclave, getFirstName(challenger));
        if (challengerRank + 1 != rank)
        {
            trace.log("force_rank", "arena::addChallengeIssueData: -> cannot allow " + getFirstName(challenger) + "(rank " + challengerRank + ") to challenge rank # " + getFirstName(challenger) + " (rank " + challengerRank + "), as they are attempting to challenge " + rank + ".", challenger, trace.TL_WARNING | trace.TL_DEBUG);
            return false;
        }
        utils.addElement(challengers, challenger);
        utils.addElement(ranks, rank);
        utils.addElement(timeStamps, getGameTime());
        utils.setBatchObjVar(challengeTerminal, VAR_CHALLENGERS, challengers.toArray());
        utils.setBatchObjVar(challengeTerminal, VAR_CHALLENGED_RANKS, ranks.toArray());
        utils.setBatchObjVar(challengeTerminal, VAR_CHALLENGE_TIMES, timeStamps.toArray());
        return true;
    }
    public static boolean addChallengeAcceptedData(obj_id challengeTerminal, obj_id challenger, obj_id defender) throws InterruptedException
    {
        obj_id enclave = getTopMostContainer(challengeTerminal);
        if (!isIdValid(enclave))
        {
            trace.log("force_rank", "arena::addChallengeAcceptedData: -> enclave is not valid.  Not adding challenge accepted data.", challengeTerminal, trace.TL_ERROR_LOG | trace.TL_DEBUG);
            return false;
        }
        Vector acceptedChallengers = new Vector();
        acceptedChallengers.setSize(0);
        Vector acceptedDefenders = new Vector();
        acceptedDefenders.setSize(0);
        Vector acceptedChllngRanks = new Vector();
        acceptedChllngRanks.setSize(0);
        if (hasObjVar(challengeTerminal, VAR_ACCEPTED_CHALLENGERS))
        {
            utils.concatArrays(acceptedChallengers, utils.getObjIdBatchObjVar(challengeTerminal, VAR_ACCEPTED_CHALLENGERS));
        }
        if (hasObjVar(challengeTerminal, VAR_ACCEPTED_DEFENDERS))
        {
            utils.concatArrays(acceptedDefenders, utils.getObjIdBatchObjVar(challengeTerminal, VAR_ACCEPTED_DEFENDERS));
        }
        if (hasObjVar(challengeTerminal, VAR_ACCEPTED_CHALLENGE_RANKS))
        {
            utils.concatArrays(acceptedChllngRanks, utils.getIntBatchObjVar(challengeTerminal, VAR_ACCEPTED_CHALLENGE_RANKS));
        }
        if (acceptedChallengers.size() != acceptedDefenders.size())
        {
            trace.log("force_rank", "acceptedChallenge data out of synch on challengeTerminal: acceptedChallengers.length=" + acceptedChallengers.size() + ", acceptedDefenders.length=" + acceptedDefenders.size(), null, trace.TL_DEBUG | trace.TL_CS_LOG);
            return false;
        }
        int defenderRank = force_rank.getForceRank(enclave, getFirstName(defender));
        if (exists(challenger))
        {
            int challengerRank = force_rank.getForceRank(enclave, getRealPlayerFirstNameAuth(challenger));
            if (challengerRank + 1 != defenderRank)
            {
                trace.log("force_rank", "Cannot allow %TU (rank " + defenderRank + ") to accept challenge from " + utils.getRealPlayerFirstName(challenger) + " (rank " + challengerRank + "), due to a rank number descrepancy.", defender, trace.TL_CS_LOG | trace.TL_DEBUG);
                return false;
            }
        }
        utils.addElement(acceptedChallengers, challenger);
        utils.addElement(acceptedDefenders, defender);
        utils.addElement(acceptedChllngRanks, defenderRank);
        utils.setBatchObjVar(challengeTerminal, VAR_ACCEPTED_CHALLENGERS, acceptedChallengers.toArray());
        utils.setBatchObjVar(challengeTerminal, VAR_ACCEPTED_DEFENDERS, acceptedDefenders.toArray());
        utils.setBatchObjVar(challengeTerminal, VAR_ACCEPTED_CHALLENGE_RANKS, acceptedChllngRanks.toArray());
        return true;
    }
    public static boolean issueChallengeAgainstRank(obj_id challengeTerminal, obj_id player, int rank) throws InterruptedException
    {
        if (addChallengeIssueData(challengeTerminal, player, rank))
        {
            addRankGotChallenged(challengeTerminal, rank);
            notifyChallengeIssued(challengeTerminal, player, rank);
            utils.setObjVar(player, VAR_LAST_CHALLENGE_TIME, getGameTime());
            trace.log("force_rank", "Dark arena challenge issued by %TU against rank #" + rank + ".", player, trace.TL_CS_LOG | trace.TL_DEBUG);
            return true;
        }
        return false;
    }
    public static boolean hasChallengeIssuedCurrently(obj_id player, obj_id challengeTerminal) throws InterruptedException
    {
        Vector challengers = new Vector();
        challengers.setSize(0);
        Vector acceptedChallengers = new Vector();
        acceptedChallengers.setSize(0);
        if (hasObjVar(challengeTerminal, VAR_CHALLENGERS))
        {
            utils.concatArrays(challengers, utils.getObjIdBatchObjVar(challengeTerminal, VAR_CHALLENGERS));
        }
        if (hasObjVar(challengeTerminal, VAR_ACCEPTED_CHALLENGERS))
        {
            utils.concatArrays(acceptedChallengers, utils.getObjIdBatchObjVar(challengeTerminal, VAR_ACCEPTED_CHALLENGERS));
        }
        return ((utils.getElementPositionInArray(challengers, player) > -1) || (utils.getElementPositionInArray(acceptedChallengers, player) > -1));
    }
    public static boolean canPlayerIssueChallenge(obj_id player, obj_id challengeTerminal) throws InterruptedException
    {
        if (utils.hasScriptVar(player, arena.VAR_I_AM_DUELING))
        {
            trace.log("force_rank", "arena::canPlayerIssueChallenge: -> False : Currently dueling.", player, trace.TL_ERROR_LOG | trace.TL_DEBUG);
            return false;
        }
        if (hasChallengeIssuedCurrently(player, challengeTerminal))
        {
            return false;
        }
        if (!hasObjVar(player, VAR_LAST_CHALLENGE_TIME))
        {
            return true;
        }
        int lastChallengeTime = getIntObjVar(player, VAR_LAST_CHALLENGE_TIME);
        int now = getGameTime();
        if ((now - lastChallengeTime) >= getChallengeTimeInterval())
        {
            return true;
        }
        return false;
    }
    public static boolean isPlayerPresentInArena(obj_id arenaCell, obj_id player) throws InterruptedException
    {
        if (!isIdValid(player) || (isDead(player)) || (!player.isAuthoritative()))
        {
            return false;
        }
        location theloc = getLocation(player);
        if (theloc.cell == arenaCell && theloc.x > ARENA_X_MIN && theloc.x < ARENA_X_MAX && theloc.z > ARENA_Z_MIN && theloc.z < ARENA_Z_MAX)
        {
            return true;
        }
        return false;
    }
    public static boolean acceptChallenge(obj_id challengeTerminal, obj_id defender, obj_id challenger) throws InterruptedException
    {
        obj_id enclave = getTopMostContainer(challengeTerminal);
        if (!isIdValid(enclave))
        {
            trace.log("force_rank", "arena::acceptChallenge: -> enclave is not valid.  Not accepting challenge.", challengeTerminal, trace.TL_ERROR_LOG | trace.TL_DEBUG);
            return false;
        }
        if (addChallengeAcceptedData(challengeTerminal, challenger, defender))
        {
            int rank = force_rank.getForceRank(enclave, getFirstName(defender));
            addRankAnsweredChallenge(challengeTerminal, rank);
            notifyChallengeAccepted(challengeTerminal, challenger, defender, rank);
            trace.log("force_rank", "Defender %TU has accepted a challenge for the honor of rank " + rank + ". Starting duel.", defender, trace.TL_CS_LOG | trace.TL_DEBUG);
            beginChallengeDuel(challengeTerminal, challenger, defender);
            return true;
        }
        else 
        {
            trace.log("force_rank", "Error accepting challenge with defender %TU due to a data problem. Challenge not accepted.", defender, trace.TL_CS_LOG | trace.TL_DEBUG);
        }
        return false;
    }
    public static void doDefenderLostPenalties(obj_id defender, obj_id challenger, obj_id enclave, boolean forfeit) throws InterruptedException
    {
        trace.log("force_rank", "Defender %TU lost arena duel to " + utils.getRealPlayerFirstName(challenger) + "(" + challenger + ")", defender, trace.TL_CS_LOG | trace.TL_DEBUG);
        if (!forfeit)
        {
            force_rank.adjustForceRankXP(defender, pvp.getAdjustedForceRankXPDelta(defender, challenger, 1.0f, true));
            force_rank.adjustForceRankXP(challenger, pvp.getAdjustedForceRankXPDelta(defender, challenger, 1.0f, false));
        }
        setObjVar(defender, VAR_LAST_CHALLENGE_TIME, getGameTime());
        int defenderRank = force_rank.getForceRank(enclave, getRealPlayerFirstNameAuth(defender));
        force_rank.demoteForceRank(enclave, getRealPlayerFirstNameAuth(defender), defenderRank - 1);
        force_rank.promoteForceRank(challenger);
        return;
    }
    public static void doChallengerLostPenalties(obj_id defender, obj_id challenger, obj_id enclave, boolean forfeit) throws InterruptedException
    {
        trace.log("force_rank", "Challenger %TU lost arena duel to " + utils.getRealPlayerFirstName(defender) + "(" + defender + ")", challenger, trace.TL_CS_LOG | trace.TL_DEBUG);
        if (!forfeit)
        {
            force_rank.adjustForceRankXP(challenger, pvp.getAdjustedForceRankXPDelta(challenger, defender, 1.0f, true));
            force_rank.adjustForceRankXP(defender, pvp.getAdjustedForceRankXPDelta(challenger, defender, 1.0f, false));
        }
        return;
    }
    public static void duelistDied(obj_id player, obj_id opponent, obj_id enclave, boolean forfeit) throws InterruptedException
    {
        utils.removeScriptVar(player, "noBeneficialJediHelp");
        utils.removeScriptVar(opponent, "noBeneficialJediHelp");
        if (!isIdValid(enclave))
        {
            trace.log("force_rank", "arena::duelistDied: -> enclave is not valid.  Not adding challenge accepted data.", null, trace.TL_DEBUG);
            return;
        }
        if (!hasScript(enclave, force_rank.SCRIPT_ENCLAVE_CONTROLLER))
        {
            trace.log("force_rank", "arena::duelistDied-- " + enclave + " is not an enclave building.", null, trace.TL_DEBUG);
            return;
        }
        obj_id challengeTerminal = null;
        if (utils.hasScriptVar(enclave, VAR_CHALLENGE_TERMINAL))
        {
            challengeTerminal = utils.getObjIdScriptVar(enclave, VAR_CHALLENGE_TERMINAL);
        }
        if (!isIdValid(challengeTerminal))
        {
            trace.log("force_rank", "arena::duelistDied: -> unable to read challenge terminal objvar from enclave.", challengeTerminal, trace.TL_ERROR_LOG | trace.TL_DEBUG);
            return;
        }
        Vector acceptedChallengers = new Vector();
        acceptedChallengers.setSize(0);
        Vector acceptedDefenders = new Vector();
        acceptedDefenders.setSize(0);
        Vector acceptedChllngRanks = new Vector();
        acceptedChllngRanks.setSize(0);
        if (hasObjVar(challengeTerminal, VAR_ACCEPTED_CHALLENGERS))
        {
            utils.concatArrays(acceptedChallengers, utils.getObjIdBatchObjVar(challengeTerminal, VAR_ACCEPTED_CHALLENGERS));
        }
        if (hasObjVar(challengeTerminal, VAR_ACCEPTED_DEFENDERS))
        {
            utils.concatArrays(acceptedDefenders, utils.getObjIdBatchObjVar(challengeTerminal, VAR_ACCEPTED_DEFENDERS));
        }
        if (hasObjVar(challengeTerminal, VAR_ACCEPTED_CHALLENGE_RANKS))
        {
            utils.concatArrays(acceptedChllngRanks, utils.getIntBatchObjVar(challengeTerminal, VAR_ACCEPTED_CHALLENGE_RANKS));
        }
        if (acceptedChallengers.size() != acceptedDefenders.size())
        {
            trace.log("force_rank", "arena::duelistDied: -> acceptedChallenge data out of synch on challengeTerminal: acceptedChallengers.length=" + acceptedChallengers.size() + ", acceptedDefenders.length=" + acceptedDefenders.size(), challengeTerminal, trace.TL_ERROR_LOG | trace.TL_DEBUG);
            return;
        }
        boolean isChallenger = false;
        boolean foundMatch = false;
        int idx = utils.getElementPositionInArray(acceptedChallengers, player);
        if (idx >= 0 && ((obj_id)acceptedDefenders.get(idx)) == opponent)
        {
            foundMatch = true;
            isChallenger = true;
        }
        else 
        {
            idx = utils.getElementPositionInArray(acceptedDefenders, player);
            if (idx >= 0 && ((obj_id)acceptedChallengers.get(idx)) == opponent)
            {
                foundMatch = true;
                isChallenger = false;
            }
        }
        if (!foundMatch)
        {
            trace.log("force_rank", "arena::duelistDied: -> No duelist match found in data for player %TU.  Shouldn't really ever happen. This is a problem.", player, trace.TL_ERROR_LOG | trace.TL_DEBUG | trace.TL_CS_LOG);
            return;
        }
        int rankChallenged = ((Integer)acceptedChllngRanks.get(idx)).intValue();
        trace.log("force_rank", "arena::duelistDied: -> " + utils.getRealPlayerFirstName(player) + " was defeated " + utils.getRealPlayerFirstName(opponent), null, trace.TL_CS_LOG | trace.TL_DEBUG);
        utils.removeScriptVar(player, VAR_I_AM_DUELING);
        utils.removeScriptVar(opponent, VAR_I_AM_DUELING);
        utils.removeElementAt(acceptedChallengers, idx);
        utils.removeElementAt(acceptedDefenders, idx);
        utils.removeElementAt(acceptedChllngRanks, idx);
        if (acceptedChallengers.size() > 0)
        {
            utils.setBatchObjVar(challengeTerminal, VAR_ACCEPTED_CHALLENGERS, acceptedChallengers.toArray());
            utils.setBatchObjVar(challengeTerminal, VAR_ACCEPTED_DEFENDERS, acceptedDefenders.toArray());
            utils.setBatchObjVar(challengeTerminal, VAR_ACCEPTED_CHALLENGE_RANKS, acceptedChllngRanks.toArray());
        }
        else 
        {
            removeObjVar(challengeTerminal, VAR_ACCEPTED_CHALLENGERS);
            removeObjVar(challengeTerminal, VAR_ACCEPTED_DEFENDERS);
            removeObjVar(challengeTerminal, VAR_ACCEPTED_CHALLENGE_RANKS);
            if (!isArenaOpenForChallenges(challengeTerminal))
            {
                Vector challengers = new Vector();
                challengers.setSize(0);
                if (hasObjVar(challengeTerminal, VAR_CHALLENGERS))
                {
                    challengers.addAll(utils.getResizeableObjIdBatchObjVar(challengeTerminal, VAR_CHALLENGERS));
                    if (challengers.size() < 1)
                    {
                        dishOutChallengeAnswerPointsToRanks(challengeTerminal);
                    }
                }
            }
        }
        if (isChallenger)
        {
            doChallengerLostPenalties(opponent, player, enclave, forfeit);
            notifyChallengeConcluded(challengeTerminal, player, opponent, false, rankChallenged);
        }
        else 
        {
            doDefenderLostPenalties(player, opponent, enclave, forfeit);
            notifyChallengeConcluded(challengeTerminal, opponent, player, true, rankChallenged);
        }
        return;
    }
    public static void leftArenaDuringDuel(obj_id player) throws InterruptedException
    {
        if (!utils.hasScriptVar(player, arena.VAR_I_AM_DUELING))
        {
            trace.log("force_rank", "Arena error: player_force_rank::handlePlayerDeath: -> " + player + " is missing arena.VAR_I_AM_DUELING", null, trace.TL_CS_LOG | trace.TL_DEBUG);
            return;
        }
        obj_id enclave = force_rank.getEnclave(player);
        trace.log("force_rank", "Player %TU has somehow left the arena during a duel. Counting abandonment as death.", player, trace.TL_CS_LOG | trace.TL_DEBUG);
        duelistDied(player, utils.getObjIdScriptVar(player, arena.VAR_I_AM_DUELING), enclave, true);
        return;
    }
    public static obj_id isEitherDuelistInADuel(obj_id challengeTerminal, obj_id duelist1, obj_id duelist2) throws InterruptedException
    {
        Vector acceptedChallengers = new Vector();
        acceptedChallengers.setSize(0);
        Vector acceptedDefenders = new Vector();
        acceptedDefenders.setSize(0);
        if (hasObjVar(challengeTerminal, VAR_ACCEPTED_CHALLENGERS))
        {
            utils.concatArrays(acceptedChallengers, utils.getObjIdBatchObjVar(challengeTerminal, VAR_ACCEPTED_CHALLENGERS));
        }
        if (hasObjVar(challengeTerminal, VAR_ACCEPTED_DEFENDERS))
        {
            utils.concatArrays(acceptedDefenders, utils.getObjIdBatchObjVar(challengeTerminal, VAR_ACCEPTED_DEFENDERS));
        }
        if (acceptedChallengers.size() != acceptedDefenders.size())
        {
            trace.log("force_rank", "arena::isEitherDuelistInADuel: -> acceptedChallenge data out of synch on challengeTerminal: acceptedChallengers.length=" + acceptedChallengers.size() + ", acceptedDefenders.length=" + acceptedDefenders.size(), challengeTerminal, trace.TL_ERROR_LOG | trace.TL_DEBUG);
            return null;
        }
        if (utils.getElementPositionInArray(acceptedChallengers, duelist1) > -1 || utils.getElementPositionInArray(acceptedDefenders, duelist1) > -1)
        {
            return duelist1;
        }
        if (utils.getElementPositionInArray(acceptedChallengers, duelist2) > -1 || utils.getElementPositionInArray(acceptedDefenders, duelist2) > -1)
        {
            return duelist2;
        }
        return null;
    }
    public static boolean validateAndRemoveChallenger(obj_id terminal, obj_id challenger, obj_id defender, obj_id enclave) throws InterruptedException
    {
        obj_id bogard = isEitherDuelistInADuel(terminal, challenger, defender);
        if (bogard != null)
        {
            String bogardName = utils.getRealPlayerFirstName(bogard);
            prose_package pp = new prose_package();
            pp.stringId = new string_id("pvp_rating", "arena_duelist_already_engaged");
            pp.target.set(bogardName);
            sendSystemMessageProse(defender, pp);
            return false;
        }
        Vector challengers = new Vector();
        challengers.setSize(0);
        Vector ranksChallenged = new Vector();
        ranksChallenged.setSize(0);
        Vector timeStamps = new Vector();
        timeStamps.setSize(0);
        if (hasObjVar(terminal, VAR_CHALLENGERS))
        {
            utils.concatArrays(challengers, utils.getObjIdBatchObjVar(terminal, VAR_CHALLENGERS));
        }
        if (hasObjVar(terminal, VAR_CHALLENGED_RANKS))
        {
            utils.concatArrays(ranksChallenged, utils.getIntBatchObjVar(terminal, VAR_CHALLENGED_RANKS));
        }
        if (hasObjVar(terminal, VAR_CHALLENGE_TIMES))
        {
            utils.concatArrays(timeStamps, utils.getIntBatchObjVar(terminal, VAR_CHALLENGE_TIMES));
        }
        int idx = utils.getElementPositionInArray(challengers, challenger);
        if (idx < 0)
        {
            sendSystemMessage(defender, new string_id("pvp_rating", "ch_terminal_challenge_no_more"));
            return false;
        }
        int rankChallenged = ((Integer)ranksChallenged.get(idx)).intValue();
        if (force_rank.getForceRank(enclave, getFirstName(defender)) != rankChallenged)
        {
            sendSystemMessage(defender, new string_id("pvp_rating", "ch_terminal_challenge_wrong_rank"));
            return false;
        }
        utils.removeElementAt(challengers, idx);
        utils.removeElementAt(ranksChallenged, idx);
        utils.removeElementAt(timeStamps, idx);
        if (challengers.size() > 0)
        {
            utils.setBatchObjVar(terminal, VAR_CHALLENGERS, challengers.toArray());
            utils.setBatchObjVar(terminal, VAR_CHALLENGED_RANKS, ranksChallenged.toArray());
            utils.setBatchObjVar(terminal, VAR_CHALLENGE_TIMES, timeStamps.toArray());
        }
        else 
        {
            removeObjVar(terminal, VAR_CHALLENGERS);
            removeObjVar(terminal, VAR_CHALLENGED_RANKS);
            removeObjVar(terminal, VAR_CHALLENGE_TIMES);
        }
        return true;
    }
    public static void beginChallengeDuel(obj_id terminal, obj_id challenger, obj_id defender) throws InterruptedException
    {
        obj_id arena_cell = getContainedBy(terminal);
        if (!isIdValid(arena_cell) || !hasScript(arena_cell, "systems.gcw.dark_jedi_arena"))
        {
            trace.log("force_rank", "beginChallengeDuel: -> Couldn't make out the arena cell. Duel aborted.", null, trace.TL_CS_LOG | trace.TL_DEBUG);
            return;
        }
        obj_id enclave = getTopMostContainer(terminal);
        if (!isIdValid(enclave))
        {
            trace.log("force_rank", "beginChallengeDuel -- enclave is invalid. Duel aborted.", null, trace.TL_CS_LOG | trace.TL_DEBUG);
            return;
        }
        if (!isPlayerInEnclave(challenger))
        {
            trace.log("force_rank", "%TU was not in the enclave when challenge was accepted.  Counting as a death-loss.", null, trace.TL_CS_LOG | trace.TL_DEBUG);
            duelistDied(challenger, defender, enclave, true);
            sendSystemMessage(defender, new string_id("pvp_rating", "ch_terminal_challenger_forfeit"));
            sendSystemMessage(challenger, new string_id("pvp_rating", "ch_terminal_challenger_forfeit_ch"));
            utils.sendMail(new string_id("pvp_rating", "challenge_concluded_subject_header"), new string_id("pvp_rating", "ch_terminal_challenger_forfeit_ch"), utils.getRealPlayerFirstName(challenger), "Dark Enclave");
            return;
        }
        if (!isPlayerPresentInArena(arena_cell, challenger))
        {
            teleportPlayerToRandomArenaLoc(challenger, arena_cell);
        }
        if (!isPlayerPresentInArena(arena_cell, defender))
        {
            teleportPlayerToRandomArenaLoc(defender, arena_cell);
        }
        setCombatTarget(challenger, defender);
        setCombatTarget(defender, challenger);
        utils.setScriptVar(challenger, "noBeneficialJediHelp", 1);
        utils.setScriptVar(challenger, VAR_I_AM_DUELING, defender);
        utils.setScriptVar(defender, VAR_I_AM_DUELING, challenger);
        utils.setScriptVar(defender, "noBeneficialJediHelp", 1);
        force_rank.makePlayersPermaEnemies(challenger, defender);
        sendDirtyObjectMenuNotification(terminal);
        return;
    }
    public static void teleportPlayerToRandomArenaLoc(obj_id player, obj_id arenaCell) throws InterruptedException
    {
        float x = rand(ARENA_X_MIN, ARENA_X_MAX);
        float z = rand(ARENA_Z_MIN, ARENA_Z_MAX);
        location newloc = new location(x, ARENA_Y, z);
        newloc.x = x;
        newloc.y = ARENA_Y;
        newloc.z = z;
        newloc.cell = arenaCell;
        setLocation(player, newloc);
    }
    public static boolean isPlayerInEnclave(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return false;
        }
        if (!player.isAuthoritative())
        {
            return false;
        }
        obj_id container = getTopMostContainer(player);
        if (!isIdValid(container) || !hasScript(container, force_rank.SCRIPT_ENCLAVE_CONTROLLER))
        {
            return false;
        }
        return true;
    }
    public static String[] getPlayerNamesForIssuedChallenges(obj_id challengeTerminal, int rank) throws InterruptedException
    {
        obj_id[] ids = getPlayerIdsForIssuedChallenges(challengeTerminal, rank);
        return utils.makeNameList(ids);
    }
    public static obj_id[] getPlayerIdsForIssuedChallenges(obj_id challengeTerminal, int rank) throws InterruptedException
    {
        obj_id enclave = getTopMostContainer(challengeTerminal);
        if (!isIdValid(enclave))
        {
            trace.log("force_rank", "arena::getPlayerIdsForIssuedChallenges: -> enclave is not valid.  Not adding challenge issued data.", challengeTerminal, trace.TL_ERROR_LOG | trace.TL_DEBUG);
            return new obj_id[0];
        }
        Vector challengers = new Vector();
        challengers.setSize(0);
        Vector ranks = new Vector();
        ranks.setSize(0);
        if (hasObjVar(challengeTerminal, VAR_CHALLENGERS))
        {
            utils.concatArrays(challengers, getObjIdArrayObjVar(challengeTerminal, VAR_CHALLENGERS));
        }
        if (hasObjVar(challengeTerminal, VAR_CHALLENGED_RANKS))
        {
            utils.concatArrays(ranks, getIntArrayObjVar(challengeTerminal, VAR_CHALLENGED_RANKS));
        }
        if (ranks.size() != challengers.size())
        {
            trace.log("force_rank", "arena::getPlayerIdsForIssuedChallenges: -> Existing data on challengeTerminal is out of synch: challengers.length=" + challengers.size() + ", ranks.length=" + ranks.size() + ".", challengeTerminal, trace.TL_ERROR_LOG | trace.TL_DEBUG);
            return new obj_id[0];
        }
        return new obj_id[0];
    }
    public static String getRealPlayerFirstNameAuth(obj_id player) throws InterruptedException
    {
        if (!player.isAuthoritative())
        {
            return null;
        }
        String name = getName(player);
        int idx = (name.toLowerCase()).indexOf("corpse of");
        if (idx >= 0)
        {
            name = name.substring(idx + 1);
        }
        java.util.StringTokenizer tok = new java.util.StringTokenizer(name);
        if (tok.hasMoreTokens())
        {
            return tok.nextToken();
        }
        return null;
    }
}
