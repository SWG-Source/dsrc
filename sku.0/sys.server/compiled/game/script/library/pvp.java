package script.library;

import script.*;

import java.util.Vector;

public class pvp extends script.base_script
{
    public pvp()
    {
    }
    public static final int BUFFER_TIME = 180;
    public static final String SCRIPTVAR_PVP_BASE = "pvp";
    public static final String SCRIPTVAR_DAMAGE_BASE = SCRIPTVAR_PVP_BASE + ".damage";
    public static final String SCRIPTVAR_PVP_DAMAGE = SCRIPTVAR_DAMAGE_BASE + ".pvp";
    public static final String SCRIPTVAR_PVE_DAMAGE = SCRIPTVAR_DAMAGE_BASE + ".pve";
    public static final String SCRIPTVAR_PVP_STAMP = SCRIPTVAR_DAMAGE_BASE + ".stamp";
    public static final String VAR_CREDIT_FOR_PVP_KILLS = "creditForPvPKills";
    public static final String VAR_ATTACKER_LIST = VAR_CREDIT_FOR_PVP_KILLS + ".attackerList";
    public static final String VAR_TOTAL_DAMAGE_TALLY = VAR_CREDIT_FOR_PVP_KILLS + ".totalDamageTally";
    public static final String VAR_LAST_PVP_DAMAGE_TIME = "creditForPvPKillsTracking.lastDamageTimeStamp";
    public static final String VAR_LAST_PVP_DAMAGE_MSG_SENT = "creditForPvPKillsTracking.clearContribsMsgPending";
    public static final int MAX_RATING_DELTA = 40;
    public static final boolean ENFORCE_MINIMUM_DELTA = false;
    public static final String VAR_PVP_RATING = "pvp_rating.value";
    public static final float MAX_RATING_AFFECT_ON_XP = 0.50f;
    public static final int MAX_RATING_DISPARITY = 2000;
    public static final boolean COUNT_PET_DAMAGE = false;
    public static final float COUNT_PET_DAMAGE_PERCENT = 0.25f;
    public static final int CONTRIB_CLEAR_INTERVAL = 180;
    public static final int MAX_PLAYERS = 30;
    public static final int MAX_DISTANCE = 80;
    public static final String VAR_PVP_LAST_KILLS = "pvp_tracker.lastkills";
    public static final String VAR_PVP_LAST_UPDATE = "pvp_tracker.lastupdate";
    public static final int VISIBILITY_THRESHOLD = 2000;
    public static final int INITIAL_RANKING_VALUE = 1200;
    public static final int XP_FARMING_TRACKING_SLOTS = 20;
    public static final int PVP_TRACKING_INTERVAL = 259200;
    public static final String FRS_XP_DATATABLE = "datatables/pvp/force_rank_xp.iff";
    public static final String FRS_DT_COLUMN_PREFIX = "rank";
    public static final String FRS_DT_ROW_NONJEDI_PREFIX = "nj";
    public static final String FRS_DT_ROW_BNTYHUNTER_PREFIX = "bh";
    public static final String FRS_DT_ROW_PADAWAN_PREFIX = "pw";
    public static final String FRS_DT_RANKED_JEDI_PREFIX = "r";
    public static final String FRS_DT_ROW_LOSS_SUFFIX = "_xp_loss";
    public static final String FRS_DT_ROW_WIN_SUFFIX = "_xp_gain";
    public static final String PVP_CS_LOG = "pvp";
    public static final int PVP_STATE_NONE = 0;
    public static final int PVP_STATE_INITIALIZE = 1;
    public static final int PVP_STATE_NEW_BATTLE = 2;
    public static final int PVP_STATE_BUILD_QUEUE = 3;
    public static final int PVP_STATE_INVITE_QUEUE = 4;
    public static final int PVP_STATE_INVITE_OVER = 5;
    public static final int PVP_STATE_ABORT_QUEUE = 6;
    public static final int PVP_STATE_BATTLE_SETUP = 7;
    public static final int PVP_STATE_BATTLE_START = 8;
    public static final int PVP_STATE_BATTLE_ENGAGED = 9;
    public static final int PVP_STATE_BATTLE_END = 10;
    public static final int PVP_STATE_BATTLE_CLEANUP = 11;
    public static final String[] BATTLEFIELD_STATUS = 
    {
        "Loading",
        "Initializing",
        "Starting",
        "Reviewing Queue",
        "Inviting Participants",
        "Reviewing Invitations",
        "Resetting",
        "Battle Setting Up",
        "Battle Started",
        "Battle Engaged",
        "Battle Ending",
        "Battle Cleaning Up"
    };
    public static final int BATTLEFIELD_DURATION = 900;
    public static final float BATTLEFIELD_INVITATION_WAIT_TIME = 120.0f;
    public static final float BATTLEFIELD_SETUP_WAIT_TIME = 120.0f;
    public static final float BATTLEFIELD_END_CLEANUP = 30.0f;
    public static final float BATTLEFIELD_RUNNER_DURATION = 95.0f;
    public static final int BATTLEFIELD_TYPE_NONE = 0;
    public static final int BATTLEFIELD_TYPE_REINFORCEMENTS = 1;
    public static final int BATTLEFIELD_TYPE_CAPTURE_THE_FLAG = 2;
    public static final String BATTLEFIELD_ACTIVE_PLAYERS = "battlefield.active_players";
    public static final String BATTLEFIELD_ACTIVE_REBEL_PLAYERS = "battlefield.active_rebel_players";
    public static final String BATTLEFIELD_ACTIVE_IMPERIAL_PLAYERS = "battlefield.active_imperial_players";
    public static final String COLOR_REBELS = "\\" + colors_hex.COLOR_REBELS;
    public static final String COLOR_IMPERIALS = "\\" + colors_hex.COLOR_IMPERIALS;
    public static obj_var[] _packageCombatWinners(obj_id victim) throws InterruptedException
    {
        if (!isIdValid(victim))
        {
            trace.log("pvp_rating", "pvp.scriptlib._packageCombatWinners:-> victim is invalid.  not handing out points.", victim, trace.TL_WARNING);
            return null;
        }
        if (!utils.hasScriptVar(victim, VAR_ATTACKER_LIST))
        {
            trace.log("pvp_rating", "pvp.scriptlib._packageCombatWinners:-> victim missing scriptvars. not handing out points.", victim, trace.TL_WARNING);
            return null;
        }
        obj_id[] attackerList = utils.getObjIdBatchScriptVar(victim, VAR_ATTACKER_LIST);
        if (attackerList == null || attackerList.length == 0)
        {
            trace.log("pvp_rating", "pvp.scriptlib._packageCombatWinners:-> attackerList is bunk.  Bailing.", victim, trace.TL_WARNING);
            return null;
        }
        obj_var[] damArray = new obj_var[attackerList.length];
        for (int i = 0; i < attackerList.length; i++)
        {
            damArray[i] = new obj_var(attackerList[i].toString(), utils.getIntScriptVar(victim, VAR_ATTACKER_LIST + "." + attackerList[i] + ".damage"));
        }
        if (damArray.length == 0)
        {
            trace.log("pvp_rating", "pvp.scriptlib._packageCombatWinners:-> damArray is bunk. Bailing..", victim, trace.TL_WARNING);
            return null;
        }
        obj_var[] killers = list.quickSort(0, attackerList.length - 1, damArray);
        if ((killers == null) || (killers.length == 0))
        {
            trace.log("pvp_rating", "pvp.scriptlib._packageCombatWinners:-> killers obj_var[] is bunk. Bailing..", victim, trace.TL_WARNING);
            return null;
        }
        String logPlayers = "";
        if (killers.length > MAX_PLAYERS)
        {
            obj_var[] tmpArray = new obj_var[MAX_PLAYERS];
            for (int i = 0; i < MAX_PLAYERS; i++)
            {
                tmpArray[i] = killers[i];
                logPlayers += "" + killers[i] + ",";
            }
            killers = tmpArray;
        }
        if (killers.length > 0 && force_rank.isForceRanked(utils.stringToObjId(killers[killers.length - 1].getName())))
        {
            if (pvp.isPvpDeath(victim))
            {
                utils.setScriptVar(victim, "jedi.rankedDeath", 1);
            }
        }
        trace.log(PVP_CS_LOG, "%TU died [in part] to PvP damage. " + killers.length + " players contributed to the death: [" + logPlayers + "]", victim, trace.TL_CS_LOG);
        return killers;
    }
    public static prose_package getPvPRatedProsePackage(obj_id winner, obj_id loser, boolean winnerPackage) throws InterruptedException
    {
        String[] phrases;
        obj_id actor;
        obj_id target;
        if (winnerPackage)
        {
            actor = winner;
            target = loser;
        }
        else 
        {
            actor = loser;
            target = winner;
        }
        if (isSpecies(actor, SPECIES_TRANDOSHAN))
        {
            if (!winnerPackage)
            {
                phrases = new String[3];
                phrases[0] = "trandoshan_killed1";
                phrases[1] = "trandoshan_killed1";
                phrases[2] = "trandoshan_killed1";
            }
            else 
            {
                phrases = new String[3];
                phrases[0] = "trandoshan_win1";
                phrases[1] = "trandoshan_win1";
                phrases[2] = "trandoshan_win1";
            }
        }
        else 
        {
            if (!winnerPackage)
            {
                phrases = new String[3];
                phrases[0] = "killed1";
                phrases[1] = "killed2";
                phrases[2] = "killed3";
            }
            else 
            {
                phrases = new String[3];
                phrases[0] = "win1";
                phrases[1] = "win2";
                phrases[2] = "win3";
            }
        }
        prose_package pp_msg = new prose_package();
        pp_msg.stringId = new string_id("pvp_rating", phrases[rand(0, phrases.length - 1)]);
        pp_msg.actor.set(actor);
        pp_msg.target.set(utils.getRealPlayerFirstName(target));
        pp_msg.digitInteger = getCurrentPvPRating(actor);
        return pp_msg;
    }
    public static prose_package getPvPThrottleProsePackage(obj_id winner, obj_id loser, boolean winnerPackage) throws InterruptedException
    {
        String stringId;
        obj_id actor;
        obj_id target;
        if (winnerPackage)
        {
            actor = winner;
            target = loser;
        }
        else 
        {
            actor = loser;
            target = winner;
        }
        if (isSpecies(actor, SPECIES_TRANDOSHAN))
        {
            if (winnerPackage)
            {
                stringId = "rating_throttle_trandoshan_winner";
            }
            else 
            {
                stringId = "rating_throttle_trandoshan_loser";
            }
        }
        else 
        {
            if (winnerPackage)
            {
                stringId = "rating_throttle_winner";
            }
            else 
            {
                stringId = "rating_throttle_loser";
            }
        }
        prose_package pp_msg = new prose_package();
        pp_msg.stringId = new string_id("pvp_rating", stringId);
        pp_msg.actor.set(actor);
        pp_msg.target.set(utils.getRealPlayerFirstName(target));
        pp_msg.digitInteger = getCurrentPvPRating(actor);
        return pp_msg;
    }
    public static prose_package getPvPRatingFloorProsePackage(obj_id winner, obj_id loser, boolean winnerPackage) throws InterruptedException
    {
        String stringId;
        obj_id actor;
        obj_id target;
        if (winnerPackage)
        {
            actor = winner;
            target = loser;
        }
        else 
        {
            actor = loser;
            target = winner;
        }
        if (isSpecies(actor, SPECIES_TRANDOSHAN))
        {
            if (winnerPackage)
            {
                stringId = "rating_floor_trandoshan_winner";
            }
            else 
            {
                stringId = "rating_floor_trandoshan_loser";
            }
        }
        else 
        {
            if (winnerPackage)
            {
                stringId = "rating_floor_winner";
            }
            else 
            {
                stringId = "rating_floor_loser";
            }
        }
        prose_package pp_msg = new prose_package();
        pp_msg.stringId = new string_id("pvp_rating", stringId);
        pp_msg.actor.set(actor);
        pp_msg.target.set(utils.getRealPlayerFirstName(target));
        pp_msg.digitInteger = getCurrentPvPRating(actor);
        return pp_msg;
    }
    public static void adjustPvPPoints(obj_id victim) throws InterruptedException
    {
        int totalDamageTally = 0;
        if (utils.hasScriptVar(victim, VAR_TOTAL_DAMAGE_TALLY))
        {
            totalDamageTally = utils.getIntScriptVar(victim, VAR_TOTAL_DAMAGE_TALLY);
        }
        if (totalDamageTally < 0)
        {
            return;
        }
        obj_var[] winners = _packageCombatWinners(victim);
        if (winners == null)
        {
            return;
        }
        boolean onlyThrottledAttackers = true;
        obj_id messageWinner = null;
        int victimRatingDelta = 0;
        int victimXPDelta = 0;
        dictionary darkJediXPRequests = new dictionary();
        darkJediXPRequests.put("victim", victim);
        darkJediXPRequests.put("totalDamageToVictim", totalDamageTally);
        dictionary darkJediPvPAction = new dictionary();
        darkJediPvPAction.put("victim", victim);
        darkJediPvPAction.put("totalDamageToVictim", totalDamageTally);
        obj_id winner;

        for (obj_var winnerVar : winners) {
            int dam = winnerVar.getIntData();
            winner = utils.stringToObjId(winnerVar.getName());
            float percentContribution = ((float) dam / totalDamageTally);
            trace.log("pvp_rating", "Calculating point exchange for winner " + winnerVar.getName(), victim, trace.TL_DEBUG);
            if ((!isIdValid(winner)) || (!winner.isLoaded()) || (winner.isBeingDestroyed()) || (winner == victim)) {
                trace.log("pvp_rating", "pvp.scriptlib.adjustPvPRatings: killer is invalid, !isLoaded, or isBeingDestroyed. ignoring...", winner, trace.TL_WARNING);
                return;
            }
            messageWinner = winner;
            if (!hasKilledVictimRecently(winner, victim)) {
                onlyThrottledAttackers = false;
                if (getDistance(winner, victim) > MAX_DISTANCE) {
                    continue;
                }
                if (!pvpCanAttack(victim, winner)) {
                    continue;
                }
                if (canAffectForceRankXPChange(victim, winner)) {
                    if (force_rank.getCouncilAffiliation(victim) == force_rank.DARK_COUNCIL && force_rank.getCouncilAffiliation(winner) == force_rank.DARK_COUNCIL) {
                        darkJediXPRequests.put(winner, percentContribution);
                        darkJediPvPAction.put(winner, percentContribution);
                    } else {
                        int xpAdjustment = getAdjustedForceRankXPDelta(victim, winner, percentContribution, false);
                        if (xpAdjustment != 0) {
                            force_rank.adjustForceRankXP(winner, xpAdjustment);
                        }
                        victimXPDelta += getAdjustedForceRankXPDelta(victim, winner, percentContribution, true);
                        trace.log("force_rank", "Winner " + utils.getRealPlayerFirstName(winner) + "(" + winner + ") was awarded " + xpAdjustment + " Council XP for their " + percentContribution * 100 + "% contribution to the death of %TU", victim, trace.TL_CS_LOG | trace.TL_DEBUG);
                    }
                } else {
                    if (force_rank.getCouncilAffiliation(victim) == force_rank.DARK_COUNCIL && force_rank.getCouncilAffiliation(winner) == force_rank.DARK_COUNCIL) {
                        darkJediPvPAction.put(winner, percentContribution);
                    }
                }
                int winnerDelta = getAdjustedPvPRatingDelta(victim, winner, percentContribution, false);
                trace.log(PVP_CS_LOG, "Winner " + utils.getRealPlayerFirstName(winner) + "(" + winner + ") was awarded " + winnerDelta + " PvP Rating Points for their " + percentContribution * 100 + "% contribution to the death of %TU", victim, trace.TL_CS_LOG | trace.TL_DEBUG);
                setCurrentPvPRating(winner, getCurrentPvPRating(winner) + winnerDelta);
                victimRatingDelta += getAdjustedPvPRatingDelta(victim, winner, percentContribution, true);
                sendSystemMessageProse(winner, getPvPRatedProsePackage(winner, victim, true));
                registerPlayerKill(winner, victim);
            } else {
                if (force_rank.getCouncilAffiliation(victim) == force_rank.DARK_COUNCIL && force_rank.getCouncilAffiliation(winner) == force_rank.DARK_COUNCIL) {
                    darkJediPvPAction.put(winner, percentContribution);
                } else {
                    prose_package pp = getPvPThrottleProsePackage(winner, victim, true);
                    sendSystemMessageProse(winner, pp);
                    trace.log(PVP_CS_LOG, "PvP points not awarded to " + utils.getRealPlayerFirstName(winner) + "(" + winner + ") for participation in death of %TU - recently killed.", victim, trace.TL_CS_LOG | trace.TL_DEBUG);
                }
            }
        }
        if (victimXPDelta != 0)
        {
            trace.log("force_rank", "%TU lost a total of " + victimXPDelta + " Council XP due to normal pvp for this death.", victim, trace.TL_CS_LOG);
            force_rank.adjustForceRankXP(victim, victimXPDelta);
        }
        if (victimRatingDelta != 0)
        {
            trace.log(PVP_CS_LOG, "%TU lost a total of " + victimRatingDelta + " PvP rating points to normal pvp for this death.", victim, trace.TL_CS_LOG);
            setCurrentPvPRating(victim, getCurrentPvPRating(victim) + victimRatingDelta);
            sendSystemMessageProse(victim, getPvPRatedProsePackage(messageWinner, victim, false));
        }
        else if (onlyThrottledAttackers && isIdValid(messageWinner))
        {
            sendSystemMessageProse(victim, getPvPThrottleProsePackage(messageWinner, victim, false));
        }
    }
    public static boolean canAffectForceRankXPChange(obj_id victim, obj_id winner) throws InterruptedException
    {
        if ((!force_rank.isForceRanked(victim)) && (!force_rank.isForceRanked(winner)))
        {
            return false;
        }
        boolean isOpposed = isInOpposingPvPFactions(victim, winner);
        if (isOpposed)
        {
            return true;
        }
        int winnerCouncil = force_rank.getCouncilAffiliation(winner);
        int victimCouncil = force_rank.getCouncilAffiliation(victim);
        if ((victimCouncil == force_rank.LIGHT_COUNCIL && winnerCouncil == force_rank.LIGHT_COUNCIL) || (victimCouncil == force_rank.DARK_COUNCIL && winnerCouncil == force_rank.DARK_COUNCIL))
        {
            return false;
        }
        int winnerFaction = pvpGetAlignedFaction(winner);
        int victimFaction = pvpGetAlignedFaction(victim);
        if (winnerFaction == 0 && (isPadawan(winner) || isBountyHunter(winner)))
        {
            return true;
        }
        if (victimFaction == 0 && (isPadawan(victim) || isBountyHunter(victim)))
        {
            return true;
        }
        return true;
    }
    public static int getRawForceRankXPDelta(obj_id player, boolean playerWon, obj_id opponent) throws InterruptedException
    {
        int playerRank = force_rank.getForceRank(player);
        if (playerRank < 0)
        {
            return 0;
        }
        String rowName = "";
        String rowPrefix = "";
        String rowSuffix = "";
        if (isInOpposingPvPFactions(player, opponent))
        {
            rowPrefix = FRS_DT_ROW_NONJEDI_PREFIX;
        }
        if (isPadawan(opponent))
        {
            rowPrefix = FRS_DT_ROW_PADAWAN_PREFIX;
        }
        int opponentRank = force_rank.getForceRank(opponent);
        if (opponentRank > -1)
        {
            rowPrefix = FRS_DT_RANKED_JEDI_PREFIX + opponentRank;
        }
        if (isBountyHunter(opponent))
        {
            rowPrefix = FRS_DT_ROW_BNTYHUNTER_PREFIX;
        }
        if (playerWon)
        {
            rowSuffix = FRS_DT_ROW_WIN_SUFFIX;
        }
        else 
        {
            rowSuffix = FRS_DT_ROW_LOSS_SUFFIX;
        }
        rowName = rowPrefix + rowSuffix;
        int xpDelta = dataTableGetInt(FRS_XP_DATATABLE, rowName, "rank" + playerRank);
        if (xpDelta == -1)
        {
            return 0;
        }
        if (!playerWon)
        {
            xpDelta *= -1;
        }
        trace.log("pvp_rating", "Force Rank XP Delta for player " + utils.getRealPlayerFirstName(player) + " is " + xpDelta, player, trace.TL_DEBUG);
        return xpDelta;
    }
    public static int getAdjustedForceRankXPDelta(obj_id victim, obj_id killer, double percentContribution, boolean gettingVictimsDelta) throws InterruptedException
    {
        String wonlost = (gettingVictimsDelta ? "losing the battle to" : "winning the battle against");
        obj_id targetPlayer;
        obj_id opponent;
        if (gettingVictimsDelta)
        {
            targetPlayer = victim;
            opponent = killer;
        }
        else 
        {
            targetPlayer = killer;
            opponent = victim;
        }
        int targetRat = getCurrentPvPRating(targetPlayer);
        int opponentRat = getCurrentPvPRating(opponent);
        int disparity = Math.abs(targetRat - opponentRat);
        if (disparity > MAX_RATING_DISPARITY)
        {
            disparity = MAX_RATING_DISPARITY;
        }
        trace.log("pvp_rating", "Target " + utils.getRealPlayerFirstName(targetPlayer) + "'s PvP rating is " + targetRat + "; Opponent " + utils.getRealPlayerFirstName(opponent) + "'s rating is " + opponentRat + " for a final disparity of " + disparity, opponent, trace.TL_DEBUG);
        float pvpRatingXPAdjustment = ((float)disparity / (float)MAX_RATING_DISPARITY) * MAX_RATING_AFFECT_ON_XP;
        trace.log("pvp_rating", "Rating Disparity is " + disparity + ", creating an XP mod of " + (pvpRatingXPAdjustment * 100) + "% (" + pvpRatingXPAdjustment + ")", null, trace.TL_DEBUG);
        int targetDelta = getRawForceRankXPDelta(targetPlayer, !gettingVictimsDelta, opponent);
        trace.log("pvp_rating", utils.getRealPlayerFirstName(targetPlayer) + "'s raw target player XP Delta for " + wonlost + " opponent " + utils.getRealPlayerFirstName(opponent) + " is " + targetDelta, targetPlayer, trace.TL_DEBUG);
        if (targetDelta != 0)
        {
            targetDelta = (int)((float)targetDelta * percentContribution);
            trace.log("pvp_rating", "After modifying the XP delta with the % contribution the adjusted XP delta for " + utils.getRealPlayerFirstName(targetPlayer) + " is " + targetDelta, null, trace.TL_DEBUG);
            if ((targetRat < opponentRat && gettingVictimsDelta) || (targetRat > opponentRat && !gettingVictimsDelta))
            {
                targetDelta -= (int)((float)targetDelta * pvpRatingXPAdjustment);
            }
            else 
            {
                targetDelta += (int)((float)targetDelta * pvpRatingXPAdjustment);
            }
            trace.log("pvp_rating", "After adjusting target " + utils.getRealPlayerFirstName(targetPlayer) + "'s XP delta against the RatingDisparity adjustment of " + pvpRatingXPAdjustment + ", the **FINAL** adjusted XP Delta is " + targetDelta, targetPlayer, trace.TL_DEBUG);
        }
        return targetDelta;
    }
    public static int getAdjustedPvPRatingDelta(obj_id loser, obj_id winner, double percentInvolvement, boolean gettingVictimsDelta) throws InterruptedException
    {
        if (percentInvolvement > 1.0)
        {
            trace.log("pvp_rating", "pvp.scriptlib.getAdjustedPvPRatingDelta:-> percentInvolvement > 1.0 (" + percentInvolvement + "). Bailing.", loser, trace.TL_WARNING | trace.TL_DEBUG);
            return 0;
        }
        obj_id targetPlayer;
        obj_id opponent;
        if (gettingVictimsDelta)
        {
            targetPlayer = loser;
            opponent = winner;
        }
        else 
        {
            targetPlayer = winner;
            opponent = loser;
        }
        int targetDelta = getPvPRatingDelta(getCurrentPvPRating(targetPlayer), getCurrentPvPRating(opponent), (!gettingVictimsDelta));
        trace.log("pvp_rating", utils.getRealPlayerFirstName(targetPlayer) + "'s raw RATING DELTA, based on a battle against " + utils.getRealPlayerFirstName(opponent) + " is " + targetDelta, targetPlayer, trace.TL_DEBUG);
        targetDelta = _massageDeltaWithRules(targetDelta, !gettingVictimsDelta);
        trace.log("pvp_rating", "After adjusting " + utils.getRealPlayerFirstName(targetPlayer) + "'s raw RATING DELTA, based on Min/Max loss/gain rules, the adjusted delta is " + targetDelta, targetPlayer, trace.TL_DEBUG);
        targetDelta *= percentInvolvement;
        trace.log("pvp_rating", "After scaling " + utils.getRealPlayerFirstName(targetPlayer) + "'s delta, based on the % involvement vs. opponent (" + utils.getRealPlayerFirstName(opponent) + "), the **FINAL** adjusted delta is " + targetDelta, targetPlayer, trace.TL_DEBUG);
        return targetDelta;
    }
    public static int getCurrentPvPRating(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            trace.log("pvp_rating", "pvp_rating.scriptlib:-> Can't fetch PvP Rating for Invalid player ID.  Bailing.", player, trace.TL_ERROR_LOG | trace.TL_DEBUG);
            return -1;
        }
        return getIntObjVar(player, VAR_PVP_RATING);
    }
    public static boolean setCurrentPvPRating(obj_id player, int rating) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            trace.log("pvp_rating", "pvp_rating.scriptlib:-> Can't set PvP Rating for Invalid player ID.  Bailing.", player, trace.TL_ERROR_LOG | trace.TL_DEBUG);
            return false;
        }
        setObjVar(player, VAR_PVP_RATING, rating);
        return true;
    }
    public static int _massageDeltaWithRules(int delta, boolean wonFight) throws InterruptedException
    {
        if (Math.abs(delta) > MAX_RATING_DELTA)
        {
            if (wonFight)
            {
                delta = MAX_RATING_DELTA;
            }
            else 
            {
                delta = MAX_RATING_DELTA * -1;
            }
        }
        if (delta < 0 && wonFight)
        {
            delta = 0;
        }
        else if (delta > 0 && (!wonFight))
        {
            delta = 0;
        }
        if (ENFORCE_MINIMUM_DELTA)
        {
            if (delta < 2 && wonFight)
            {
                delta = 2;
            }
            else if (delta > -2 && (!wonFight))
            {
                delta = -2;
            }
        }
        return delta;
    }
    public static int getPvPRatingDelta(int oldRating, int opponentRating, boolean wonFight) throws InterruptedException
    {
        int score = 1;
        if (!wonFight)
        {
            score = -1;
        }
        int newRating = oldRating + (score * 21) + ((opponentRating - oldRating) / 25);
        int ratingDelta = newRating - oldRating;
        trace.log("pvp_rating", "pvp::getPvPRatingDelta -> returned " + ratingDelta);
        return ratingDelta;
    }
    public static void updateDamageContributorList(obj_id target, obj_id attackr, int dam) throws InterruptedException
    {
        if (!isIdValid(target) || (!isIdValid(attackr)))
        {
            trace.log("pvp_rating.scriptlib", "updateDamageContributorList:-> Target or Attacker is invalid. Bailing.", target, trace.TL_WARNING | trace.TL_DEBUG);
            return;
        }
        if (pet_lib.isPet(attackr) || !isPlayer(attackr))
        {
            return;
        }
        if (dam > 0)
        {
            updatePvPDamageTimeStamp(target);
            double tally = utils.getIntScriptVar(target, VAR_TOTAL_DAMAGE_TALLY);
            tally += dam;
            utils.setScriptVar(target, VAR_TOTAL_DAMAGE_TALLY, (int)tally);
            Vector attackerList = utils.getResizeableObjIdBatchScriptVar(target, VAR_ATTACKER_LIST);
            if (attackerList != null && attackerList.size() > 0)
            {
                if (utils.getElementPositionInArray(attackerList, attackr) == -1)
                {
                    attackerList = utils.addElement(attackerList, attackr);
                    utils.setBatchScriptVar(target, VAR_ATTACKER_LIST, attackerList);
                }
            }
            else 
            {
                attackerList = utils.addElement(attackerList, attackr);
                utils.setBatchScriptVar(target, VAR_ATTACKER_LIST, attackerList);
            }
            String damPath = VAR_ATTACKER_LIST + "." + attackr + ".damage";
            int totalDamage = dam + utils.getIntScriptVar(target, damPath);
            utils.setScriptVar(target, damPath, totalDamage);
        }
    }
    public static boolean hasKilledVictimRecently(obj_id attacker, obj_id victim) throws InterruptedException
    {
        boolean rslt = true;
        obj_id[] victimList = utils.getObjIdBatchObjVar(attacker, VAR_PVP_LAST_KILLS);
        if (victimList != null)
        {
            if (utils.getElementPositionInArray(victimList, victim) == -1)
            {
                rslt = false;
            }
        }
        else 
        {
            rslt = false;
        }
        return rslt;
    }
    public static void registerPlayerKill(obj_id attacker, obj_id victim) throws InterruptedException
    {
        Vector victimList = utils.getResizeableObjIdBatchObjVar(attacker, VAR_PVP_LAST_KILLS);
        if (victimList != null && victimList.size() > 0)
        {
            if (utils.getElementPositionInArray(victimList, victim) == -1)
            {
                victimList = utils.addElement(victimList, victim);
                while (victimList.size() > XP_FARMING_TRACKING_SLOTS)
                {
                    victimList = utils.removeElementAt(victimList, 0);
                }
                utils.setResizeableBatchObjVar(attacker, VAR_PVP_LAST_KILLS, victimList);
                trace.log(PVP_CS_LOG, "Adding " + utils.getRealPlayerFirstName(victim) + "(" + victim + ") to last-50-victim-list of %TU", attacker, trace.TL_CS_LOG);
            }
            else 
            {
                return;
            }
        }
        else 
        {
            victimList = utils.addElement(victimList, victim);
            while (victimList.size() > XP_FARMING_TRACKING_SLOTS)
            {
                victimList = utils.removeElementAt(victimList, 0);
            }
            utils.setResizeableBatchObjVar(attacker, VAR_PVP_LAST_KILLS, victimList);
        }
        setObjVar(attacker, VAR_PVP_LAST_UPDATE, getGameTime() + PVP_TRACKING_INTERVAL);
    }
    public static boolean cleanupCreditForPvPKills(obj_id whichPlayer) throws InterruptedException
    {
        utils.removeScriptVarTree(whichPlayer, "creditForPvPKills");
        utils.removeScriptVarTree(whichPlayer, "creditForPvPKillsTracking");
        if (hasObjVar(whichPlayer, VAR_PVP_LAST_UPDATE))
        {
            if (getIntObjVar(whichPlayer, VAR_PVP_LAST_UPDATE) < getGameTime())
            {
                removeObjVar(whichPlayer, VAR_PVP_LAST_KILLS);
                removeObjVar(whichPlayer, VAR_PVP_LAST_UPDATE);
            }
        }
        return true;
    }
    public static void updatePvPDamageTimeStamp(obj_id player) throws InterruptedException
    {
        setupClearPvPDamageCallback(player);
        utils.setScriptVar(player, VAR_LAST_PVP_DAMAGE_TIME, getGameTime());
    }
    public static void setupClearPvPDamageCallback(obj_id player) throws InterruptedException
    {
        if (utils.hasScriptVar(player, VAR_LAST_PVP_DAMAGE_MSG_SENT))
        {
            return;
        }
        messageTo(player, "checkPvPDamageTime", null, CONTRIB_CLEAR_INTERVAL, false);
        utils.setScriptVar(player, VAR_LAST_PVP_DAMAGE_MSG_SENT, true);
    }
    public static boolean isBountyHunter(obj_id player) throws InterruptedException
    {
        return hasSkill(player, "class_bountyhunter_phase2_novice");
    }
    public static boolean isPadawan(obj_id player) throws InterruptedException
    {
        return hasSkill(player, "force_title_jedi_rank_02");
    }
    public static boolean isInOpposingPvPFactions(obj_id player1, obj_id player2) throws InterruptedException
    {
        if (isPlayer(player1) && pvpGetType(player1) == PVPTYPE_NEUTRAL) {
            return false;
        } else
            return !(isPlayer(player2) && pvpGetType(player2) == PVPTYPE_NEUTRAL) && (pvpAreFactionsOpposed(pvpGetAlignedFaction(player1), pvpGetAlignedFaction(player2)));
    }
    public static boolean updatePlayerDamageTracking(obj_id player, obj_id attacker, obj_id wpn, int[] damage) throws InterruptedException
    {
        if (!isIdValid(player) || !isPlayer(player) || !isIdValid(attacker) || !isIdValid(wpn))
        {
            return false;
        }
        if (damage == null || damage.length == 0)
        {
            return false;
        }
        long tmpTotal = 0;
        for (int aDamage : damage) {
            tmpTotal += aDamage;
        }
        if (tmpTotal > Integer.MAX_VALUE)
        {
            tmpTotal = Integer.MAX_VALUE;
        }
        int totalDamage = (int)tmpTotal;
        if (isPvpDamageSource(player, attacker))
        {
            long newTotal = utils.getIntScriptVar(player, SCRIPTVAR_PVP_DAMAGE) + totalDamage;
            if (newTotal > Integer.MAX_VALUE)
            {
                newTotal = Integer.MAX_VALUE;
            }
            utils.setScriptVar(player, SCRIPTVAR_PVP_DAMAGE, (int)newTotal);
            utils.setScriptVar(player, SCRIPTVAR_PVP_STAMP, getGameTime());
        }
        else 
        {
            long newTotal = utils.getIntScriptVar(player, SCRIPTVAR_PVE_DAMAGE) + totalDamage;
            if (newTotal > Integer.MAX_VALUE)
            {
                newTotal = Integer.MAX_VALUE;
            }
            utils.setScriptVar(player, SCRIPTVAR_PVE_DAMAGE, (int)newTotal);
        }
        return true;
    }
    public static boolean isPvpDamageSource(obj_id player, obj_id attacker) throws InterruptedException
    {
        if (!isIdValid(player) || !isIdValid(attacker))
        {
            return false;
        }
        if (pet_lib.isPet(attacker))
        {
            obj_id master = getMaster(attacker);
            if (isIdValid(master) && master.isLoaded())
            {
                attacker = master;
            }
        }
        if (isPlayer(attacker))
        {
            return true;
        }
        if (isPlayer(player) && pvpGetType(player) == PVPTYPE_NEUTRAL)
        {
            return false;
        }
        return pvpAreFactionsOpposed(pvpGetAlignedFaction(player), pvpGetAlignedFaction(attacker));
    }
    public static void validatePlayerDamageTracking(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return;
        }
        int delta = getGameTime() - utils.getIntScriptVar(player, SCRIPTVAR_PVP_STAMP);
        if (delta < BUFFER_TIME)
        {
            return;
        }
        for (int i = 0; i < 3; i++)
        {
            int attrib = i * 3;
            if (getAttrib(player, attrib) < getWoundedMaxAttrib(player, attrib))
            {
                return;
            }
        }
        clearPlayerDamageTracking(player);
    }
    public static void clearPlayerDamageTracking(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return;
        }
        utils.removeScriptVarTree(player, SCRIPTVAR_DAMAGE_BASE);
    }
    public static boolean isPvpDeath(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player) || !isPlayer(player) || !isDead(player))
        {
            return false;
        }
        int delta = getGameTime() - utils.getIntScriptVar(player, SCRIPTVAR_PVP_STAMP);
        if (delta >= BUFFER_TIME)
        {
            return false;
        }
        int pvpDamage = utils.getIntScriptVar(player, SCRIPTVAR_PVP_DAMAGE);
        int pveDamage = utils.getIntScriptVar(player, SCRIPTVAR_PVE_DAMAGE);
        return (pvpDamage > pveDamage);
    }
    public static void incrementPlayerDeathBounty(obj_id killer, obj_id victim) throws InterruptedException
    {
        if (!isIdValid(killer) || !isIdValid(victim))
        {
            return;
        }
        if (!isPlayer(killer) || !isPlayer(victim))
        {
            return;
        }
        if (hasKilledVictimRecently(killer, victim))
        {
            return;
        }
        registerPlayerKill(killer, victim);
        int bounty = 0;
        if (hasObjVar(killer, "bounty.amount"))
        {
            bounty = getIntObjVar(killer, "bounty.amount");
        }
        bounty += 1000;
        setObjVar(killer, "bounty.amount", bounty);
        if (bounty >= 10000)
        {
            setJediBountyValue(killer, bounty);
        }
        CustomerServiceLog("bounty", "A bounty of 1000 credits has been automatically put on %TU for killing %TT", killer, victim);
    }
    public static void battlefieldWarp(obj_id player, location loc) throws InterruptedException
    {
        if (isIncapacitated(player) || isDead(player))
        {
            int closeSui = utils.getIntScriptVar(player, pclib.VAR_SUI_CLONE);
            forceCloseSUIPage(closeSui);
            utils.setScriptVar(player, "no_cloning_sickness", 1);
            pclib.playerRevive(player);
        }
        pvpMakeCovert(player);
        utils.warpPlayer(player, loc);
    }
    public static void bfActivePlayersAnnounce(obj_id controller, string_id announcement) throws InterruptedException
    {
        Vector players = new Vector();
        players.setSize(0);
        if (utils.hasResizeableObjIdBatchScriptVar(controller, BATTLEFIELD_ACTIVE_PLAYERS))
        {
            players = utils.getResizeableObjIdBatchScriptVar(controller, BATTLEFIELD_ACTIVE_PLAYERS);
        }
        if (players != null && players.size() > 0)
        {
            for (Object player : players) {
                if (!isIdValid(((obj_id) player)) || !exists(((obj_id) player))) {
                    continue;
                }
                sendSystemMessage(((obj_id) player), announcement);
            }
        }
    }
    public static void bfMessagePlayers(obj_id controller, String faction, String message, dictionary params) throws InterruptedException
    {
        Vector players = new Vector();
        players.setSize(0);
        if (utils.hasResizeableStringBatchScriptVar(controller, faction))
        {
            players = utils.getResizeableStringBatchScriptVar(controller, faction);
        }
        if (players != null && players.size() > 0)
        {
            String[] playerInfo;
            obj_id player;

            for (Object player1 : players) {
                if (player1 == null || ((String) player1).length() < 1) {
                    continue;
                }
                playerInfo = split(((String) player1), '^');
                if (playerInfo == null || playerInfo.length < 1) {
                    continue;
                }
                player = utils.stringToObjId(playerInfo[0]);
                if (!isIdValid(player)) {
                    continue;
                }
                messageTo(player, message, params, 1.0f, false);
            }
        }
    }
    public static void bfMessagePlayersOnBattlefield(obj_id controller, String faction, String message, dictionary params) throws InterruptedException
    {
        Vector players = new Vector();
        players.setSize(0);
        if (utils.hasResizeableStringBatchScriptVar(controller, faction))
        {
            players = utils.getResizeableStringBatchScriptVar(controller, faction);
        }
        if (players != null && players.size() > 0)
        {
            String[] playerInfo;
            obj_id player;

            for (Object player1 : players) {
                if (player1 == null || ((String) player1).length() < 1) {
                    continue;
                }
                playerInfo = split(((String) player1), '^');
                if (playerInfo == null || playerInfo.length < 1) {
                    continue;
                }
                player = utils.stringToObjId(playerInfo[0]);
                if (!isIdValid(player) || !exists(player)) {
                    continue;
                }
                messageTo(player, message, params, 1.0f, false);
            }
        }
    }
    public static Vector bfActiveAppendStatistics(obj_id controller, String scriptVar, Vector battlefieldTeams) throws InterruptedException
    {
        if (!isIdValid(controller) || !exists(controller))
        {
            return null;
        }
        Vector activelTeam = new Vector();
        activelTeam.setSize(0);
        if (utils.hasResizeableStringBatchScriptVar(controller, scriptVar))
        {
            activelTeam = utils.getResizeableStringBatchScriptVar(controller, scriptVar);
        }
        if (activelTeam != null && activelTeam.size() > 0)
        {
            int count = 0;
            String[] activeMember;
            dictionary member;
            for (Object anActivelTeam : activelTeam) {
                if (anActivelTeam == null || ((String) anActivelTeam).length() < 1) {
                    continue;
                }
                activeMember = split(((String) anActivelTeam), '^');
                if (activeMember.length < 9) {
                    continue;
                }
                member = new dictionary();
                member.put("player", utils.stringToObjId(activeMember[0]));
                member.put("name", activeMember[2]);
                if (scriptVar.equals(BATTLEFIELD_ACTIVE_REBEL_PLAYERS)) {
                    member.put("faction", factions.FACTION_FLAG_REBEL);
                } else if (scriptVar.equals(BATTLEFIELD_ACTIVE_IMPERIAL_PLAYERS)) {
                    member.put("faction", factions.FACTION_FLAG_IMPERIAL);
                }
                member.put("kills", utils.stringToInt(activeMember[3]));
                member.put("deaths", utils.stringToInt(activeMember[4]));
                member.put("damage", utils.stringToInt(activeMember[5]));
                member.put("healing", utils.stringToInt(activeMember[6]));
                member.put("captures", utils.stringToInt(activeMember[7]));
                member.put("assists", utils.stringToInt(activeMember[8]));
                utils.addElement(battlefieldTeams, member);
                count++;
            }
            if (battlefieldTeams.size() > 0)
            {
                ((dictionary)battlefieldTeams.get(0)).put(scriptVar, count);
            }
        }
        return battlefieldTeams;
    }
    public static Vector bfActiveGetStatistics(obj_id controller) throws InterruptedException
    {
        if (!isIdValid(controller) || !exists(controller))
        {
            return null;
        }
        Vector battlefieldTeams = new Vector();
        battlefieldTeams = bfActiveAppendStatistics(controller, BATTLEFIELD_ACTIVE_REBEL_PLAYERS, battlefieldTeams);
        battlefieldTeams = bfActiveAppendStatistics(controller, BATTLEFIELD_ACTIVE_IMPERIAL_PLAYERS, battlefieldTeams);
        return battlefieldTeams;
    }
    public static String[][] bfStatisticsToArray(Vector battlefieldPlayers, boolean colored) throws InterruptedException
    {
        if (battlefieldPlayers == null || battlefieldPlayers.size() < 1)
        {
            return null;
        }
        String[][] scoreData = new String[battlefieldPlayers.size()][8];
        String color;
        String factionName;
        String name;

        for (int i = 0, j = battlefieldPlayers.size(); i < j; i++)
        {
            int faction = ((dictionary)battlefieldPlayers.get(i)).getInt("faction");
            color = COLOR_IMPERIALS;
            factionName = "Imperial";
            if (faction == factions.FACTION_FLAG_REBEL)
            {
                color = COLOR_REBELS;
                factionName = "Rebel";
            }
            if (!colored)
            {
                color = "";
            }
            scoreData[i][1] = color + factionName;
            name = ((dictionary) battlefieldPlayers.get(i)).getString("name");
            if (name == null || name.length() < 1)
            {
                name = "Unknown";
            }
            int kills = ((dictionary)battlefieldPlayers.get(i)).getInt("kills");
            int deaths = ((dictionary)battlefieldPlayers.get(i)).getInt("deaths");
            int damage = ((dictionary)battlefieldPlayers.get(i)).getInt("damage");
            int healing = ((dictionary)battlefieldPlayers.get(i)).getInt("healing");
            int captures = ((dictionary)battlefieldPlayers.get(i)).getInt("captures");
            int assists = ((dictionary)battlefieldPlayers.get(i)).getInt("assists");
            scoreData[i][0] = color + name;
            scoreData[i][2] = "" + kills;
            scoreData[i][3] = "" + assists;
            scoreData[i][4] = "" + deaths;
            scoreData[i][5] = "" + damage;
            scoreData[i][6] = "" + healing;
            scoreData[i][7] = "" + captures;
        }
        return scoreData;
    }
    public static boolean bfIsValidAndEngaged(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player) || !exists(player))
        {
            return false;
        }
        if (!utils.hasScriptVar(player, "battlefield.active"))
        {
            return false;
        }
        obj_id controller = utils.getObjIdScriptVar(player, "battlefield.active");
        if (!isIdValid(controller) || !exists(controller))
        {
            return false;
        }
        int bfState = utils.getIntScriptVar(controller, "battlefield.state");
        if (bfState != pvp.PVP_STATE_BATTLE_ENGAGED)
        {
            return false;
        }
        return true;
    }
    public static void bfCreditForDamage(obj_id attacker, int damage) throws InterruptedException
    {
        if (!bfIsValidAndEngaged(attacker))
        {
            return;
        }
        int totalDamage = utils.getIntScriptVar(attacker, "battlefield.damage");
        if (damage < 0)
        {
            return;
        }
        totalDamage += damage;
        utils.setScriptVar(attacker, "battlefield.damage", totalDamage);
    }
    public static void bfCreditForHealing(obj_id healer, int healed) throws InterruptedException
    {
        if (!bfIsValidAndEngaged(healer))
        {
            return;
        }
        int totalhealed = utils.getIntScriptVar(healer, "battlefield.healing");
        if (healed < 0)
        {
            return;
        }
        totalhealed += healed;
        utils.setScriptVar(healer, "battlefield.healing", totalhealed);
    }
    public static void bfCreditForKill(obj_id who) throws InterruptedException
    {
        if (!bfIsValidAndEngaged(who))
        {
            return;
        }
        int totalKills = utils.getIntScriptVar(who, "battlefield.kills");
        totalKills++;
        utils.setScriptVar(who, "battlefield.kills", totalKills);
    }
    public static void bfCreditForDeath(obj_id who, obj_id killer) throws InterruptedException
    {
        if (!bfIsValidAndEngaged(who))
        {
            return;
        }
        if ((factions.isRebel(who) && factions.isImperial(killer)) || (factions.isImperial(who) && factions.isRebel(killer)))
        {
            int totalDeaths = utils.getIntScriptVar(who, "battlefield.deaths");
            totalDeaths++;
            utils.setScriptVar(who, "battlefield.deaths", totalDeaths);
        }
        if (buff.hasBuff(who, "battlefield_communication_run"))
        {
            obj_id controller = utils.getObjIdScriptVar(who, "battlefield.active");
            if (!isIdValid(controller) || !exists(controller))
            {
                return;
            }
            pvp.bfClearRunner(controller);
            dictionary params = new dictionary();
            params.put("gcwCredits", 100);
            int faction = factions.getFactionFlag(who);
            if (faction == factions.FACTION_FLAG_REBEL)
            {
                pvp.bfMessagePlayersOnBattlefield(controller, pvp.BATTLEFIELD_ACTIVE_IMPERIAL_PLAYERS, "receiveBattlefieldTerminalCapture", params);
                pvp.bfActivePlayersAnnounce(controller, new string_id("spam", "battlefield_runner_killed_rebel"));
            }
            else if (faction == factions.FACTION_FLAG_IMPERIAL)
            {
                pvp.bfMessagePlayersOnBattlefield(controller, pvp.BATTLEFIELD_ACTIVE_REBEL_PLAYERS, "receiveBattlefieldTerminalCapture", params);
                pvp.bfActivePlayersAnnounce(controller, new string_id("spam", "battlefield_runner_killed_imperial"));
            }
        }
    }
    public static void bfCreditForAssist(obj_id who) throws InterruptedException
    {
        if (!bfIsValidAndEngaged(who))
        {
            return;
        }
        int totalAssists = utils.getIntScriptVar(who, "battlefield.assists");
        totalAssists++;
        utils.setScriptVar(who, "battlefield.assists", totalAssists);
    }
    public static void bfCreditForCapture(obj_id who, int captureValue) throws InterruptedException
    {
        if (!bfIsValidAndEngaged(who))
        {
            return;
        }
        int totalCaptures = utils.getIntScriptVar(who, "battlefield.captures");
        if (captureValue < 0)
        {
            return;
        }
        totalCaptures += captureValue;
        utils.setScriptVar(who, "battlefield.captures", totalCaptures);
    }
    public static boolean bfTerminalIsRegistered(obj_id controller, obj_id terminal) throws InterruptedException
    {
        if (!isIdValid(controller) || !exists(controller) || !isIdValid(terminal) || !exists(terminal))
        {
            return false;
        }
        Vector terminals = new Vector();
        terminals.setSize(0);
        if (utils.hasResizeableObjIdBatchScriptVar(controller, "battlefield.terminals"))
        {
            terminals = utils.getResizeableObjIdBatchScriptVar(controller, "battlefield.terminals");
        }
        if (terminals == null || terminals.size() < 1)
        {
            return false;
        }
        for (Object terminal1 : terminals) {
            if (!isIdValid(((obj_id) terminal1)) || !exists(((obj_id) terminal1))) {
                continue;
            }
            if (terminal1 == terminal) {
                return true;
            }
        }
        return false;
    }
    public static void bfTerminalRegister(obj_id controller, obj_id terminal) throws InterruptedException
    {
        if (!isIdValid(controller))
        {
            return;
        }
        if (!exists(controller))
        {
            if (isIdValid(terminal) && exists(terminal))
            {
                messageTo(terminal, "registerTerminal", null, 1.0f, false);
            }
            return;
        }
        if (!isIdValid(terminal) || !exists(terminal))
        {
            return;
        }
        Vector terminals = new Vector();
        terminals.setSize(0);
        if (utils.hasResizeableObjIdBatchScriptVar(controller, "battlefield.terminals"))
        {
            terminals = utils.getResizeableObjIdBatchScriptVar(controller, "battlefield.terminals");
        }
        if (terminals == null || terminals.size() < 1)
        {
            utils.addElement(terminals, terminal);
            utils.setBatchScriptVar(controller, "battlefield.terminals", terminals);
            return;
        }
        for (Object terminal1 : terminals) {
            if (!isIdValid(((obj_id) terminal1)) || !exists(((obj_id) terminal1))) {
                continue;
            }
            if (terminal1 == terminal) {
                return;
            }
        }
        utils.addElement(terminals, terminal);
        utils.setBatchScriptVar(controller, "battlefield.terminals", terminals);
    }
    public static Vector bfTerminalsGetRegistered(obj_id controller) throws InterruptedException
    {
        if (!isIdValid(controller) || !exists(controller))
        {
            return null;
        }
        Vector terminals = new Vector();
        terminals.setSize(0);
        if (utils.hasResizeableObjIdBatchScriptVar(controller, "battlefield.terminals"))
        {
            terminals = utils.getResizeableObjIdBatchScriptVar(controller, "battlefield.terminals");
        }
        return terminals;
    }
    public static boolean bfTerminalsReset(obj_id controller) throws InterruptedException
    {
        if (!isIdValid(controller) || !exists(controller))
        {
            return false;
        }
        Vector terminals = new Vector();
        terminals.setSize(0);
        if (utils.hasResizeableObjIdBatchScriptVar(controller, "battlefield.terminals"))
        {
            terminals = utils.getResizeableObjIdBatchScriptVar(controller, "battlefield.terminals");
        }
        if (terminals == null || terminals.size() < 1)
        {
            return false;
        }
        if (getIntObjVar(controller, "battlefield.terminalCount") != terminals.size())
        {
            return false;
        }
        for (Object terminal : terminals) {
            if (!isIdValid(((obj_id) terminal)) || !exists(((obj_id) terminal))) {
                continue;
            }
            utils.removeScriptVar(((obj_id) terminal), "battlefield.captured");
            messageTo(((obj_id) terminal), "receiveBattlefieldReset", null, 1.0f, false);
        }
        return true;
    }
    public static int bfGetBattlefieldType(obj_id controller) throws InterruptedException
    {
        if (!isIdValid(controller) || !exists(controller))
        {
            return BATTLEFIELD_TYPE_NONE;
        }
        return utils.getIntScriptVar(controller, "battlefieldType");
    }
    public static obj_id bfGetRunner(obj_id controller) throws InterruptedException
    {
        if (!isIdValid(controller) || !exists(controller))
        {
            return null;
        }
        return utils.getObjIdScriptVar(controller, "battlefield.runner");
    }
    public static void bfSetRunner(obj_id controller, obj_id runner) throws InterruptedException
    {
        if (!isIdValid(controller) || !exists(controller) || !isIdValid(runner) || !exists(runner))
        {
            return;
        }
        utils.setScriptVar(controller, "battlefield.runner", runner);
    }
    public static void bfClearRunner(obj_id controller) throws InterruptedException
    {
        if (!isIdValid(controller) || !exists(controller))
        {
            return;
        }
        obj_id runner = utils.getObjIdScriptVar(controller, "battlefield.runner");
        if (isIdValid(runner) && exists(runner))
        {
            buff.removeBuff(runner, "battlefield_communication_run");
        }
        obj_id terminal = utils.getObjIdScriptVar(controller, "battlefield.runnerTerminal");
        if (!isIdValid(terminal) || !exists(terminal))
        {
            return;
        }
        dictionary dict = new dictionary();
        dict.put("terminal_location", getWorldLocation(terminal));
        dict.put("terminal", terminal);
        pvp.bfMessagePlayersOnBattlefield(controller, pvp.BATTLEFIELD_ACTIVE_REBEL_PLAYERS, "destroyBattlefieldWaypoint", dict);
        pvp.bfMessagePlayersOnBattlefield(controller, pvp.BATTLEFIELD_ACTIVE_IMPERIAL_PLAYERS, "destroyBattlefieldWaypoint", dict);
        utils.removeScriptVar(controller, "battlefield.runner");
        utils.removeScriptVar(controller, "battlefield.runnerTerminal");
    }
    public static boolean bfHasRunner(obj_id controller) throws InterruptedException
    {
        if (!isIdValid(controller) || !exists(controller))
        {
            return false;
        }
        obj_id runner = utils.getObjIdScriptVar(controller, "battlefield.runner");
        return isIdValid(runner) && exists(runner);
    }
    public static void bfActiveWarpPlayerToStart(obj_id controller, obj_id player) throws InterruptedException
    {
        if (!isIdValid(controller) || !exists(controller))
        {
            return;
        }
        int battlefieldState = utils.getIntScriptVar(controller, "battlefield.state");
        location loc;
        if (factions.isRebel(player))
        {
            loc = utils.getLocationScriptVar(controller, "battlefieldRebelSpawn");
        }
        else 
        {
            if (factions.isImperial(player))
            {
                loc = utils.getLocationScriptVar(controller, "battlefieldImperialSpawn");
            }
            else 
            {
                return;
            }
        }
        dictionary params = new dictionary();
        params.put("bfState", battlefieldState);
        params.put("controller", controller);
        if (loc != null)
        {
            params.put("warpLocation", loc);
            messageTo(player, "receiveBattlefieldWarpLocation", params, 1.0f, false);
        }
    }
    public static void bfLog(obj_id controller, String text) throws InterruptedException
    {
        if (!isIdValid(controller))
        {
            return;
        }
        String battlefieldName = "error";
        if (exists(controller))
        {
            battlefieldName = getStringObjVar(controller, "battlefieldName");
        }
        if (battlefieldName == null || battlefieldName.length() < 1)
        {
            battlefieldName = "error";
        }
        CustomerServiceLog("battlefield_" + battlefieldName, text);
    }
}
