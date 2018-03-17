package script.systems.gcw;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.force_rank;
import script.library.player_structure;
import script.library.prose;
import script.library.utils;
import script.library.sui;
import script.library.arena;
import script.library.trace;

public class player_force_rank extends script.base_script
{
    public player_force_rank()
    {
    }
    public static final String SCRIPT_VAR_SUI_PID = "force_rank.vote_sui";
    public static final String SCRIPT_VAR_TERMINAL = "force_rank.vote_terminal";
    public static final String SCRIPT_VAR_PETITIONERS = "force_rank.vote_petitioners";
    public static final String SCRIPT_VAR_VOTE_RANK = "force_rank.vote_rank";
    public static final String SCRIPT_VAR_RANK_LIST = "force_rank.rank_list";
    public static final String SCRIPT_VAR_NAMES = "force_rank.challenge_vote_name";
    public static final String SCRIPT_VAR_CHALLENGE_SELECTED = "force_rank.challenge_vote_selected";
    public static final String SCRIPT_VAR_CHAL_TERMINAL = "force_rank.challenge_vote_terminal";
    public static final String JEDI_GUARDIAN_TITLE_SKILL = "force_title_jedi_rank_04";
    public static final String JEDI_MASTER_TITLE_SKILL = "force_title_jedi_master";
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        if (utils.hasScriptVar(self, arena.VAR_I_AM_DUELING))
        {
            obj_id opponent = utils.getObjIdScriptVar(self, arena.VAR_I_AM_DUELING);
            if (isIdValid(opponent))
            {
                sendSystemMessage(opponent, new string_id("pvp_rating", "arena_incap_opponent"));
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        detachScript(self, "systems.gcw.player_force_rank");
        return SCRIPT_OVERRIDE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        force_rank.getEnclaveObjId(self, force_rank.getCouncilAffiliation(self), "enclaveIdResponse");
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        updateJediScriptData(self, "bountyTrackingData.forceRank", -1);
        return SCRIPT_CONTINUE;
    }
    public int OnLogin(obj_id self) throws InterruptedException
    {
        force_rank.requestExperienceDebt(self);
        return SCRIPT_CONTINUE;
    }
    public int OnSkillRevoked(obj_id self, String skill) throws InterruptedException
    {
        if (utils.hasScriptVar(self, force_rank.SCRIPT_VAR_SKILL_RESYNC))
        {
            return SCRIPT_CONTINUE;
        }
        if (force_rank.getForceRank(self) < 0)
        {
            return SCRIPT_CONTINUE;
        }
        int council = force_rank.getCouncilAffiliation(self);
        if (council == -1)
        {
            LOG("force_rank", "player_force_rank.OnSkillRevoked -- " + self + " does not have a valid council affiliation.");
            return SCRIPT_CONTINUE;
        }
        int skill_rank = force_rank.getForceSkillRank(skill, council);
        if (skill_rank != -1)
        {
            if (skill_rank > 0)
            {
                CustomerServiceLog("force_rank", "Demoting %TU to rank " + (skill_rank - 1) + " since skill " + skill + " was voluntarily surrendered.", self, null);
                force_rank.demoteForceRank(self, skill_rank - 1);
            }
            else 
            {
                force_rank.removeFromForceRankSystem(self, true);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnSkillGranted(obj_id self, String skill) throws InterruptedException
    {
        LOG("force_rank", "player_force_rank::OnSkillGranted: -> Skill Granted: " + skill);
        if (skill.equals("force_rank_dark_rank_09") || skill.equals("force_rank_light_rank_09"))
        {
            CustomerServiceLog("force_rank", "Adding skill " + JEDI_MASTER_TITLE_SKILL + " to %TU since it should be granted for rank 9", self, null);
            grantSkill(self, JEDI_MASTER_TITLE_SKILL);
        }
        if (skill.equals("force_rank_dark_rank_05") || skill.equals("force_rank_light_rank_05"))
        {
            CustomerServiceLog("force_rank", "Adding skill " + JEDI_GUARDIAN_TITLE_SKILL + " to %TU since it should be granted for rank 0", self, null);
            grantSkill(self, JEDI_GUARDIAN_TITLE_SKILL);
        }
        return SCRIPT_CONTINUE;
    }
    public int msgResynchFRSData(obj_id self, dictionary params) throws InterruptedException
    {
        force_rank.validateFRSPlayerData(self);
        return SCRIPT_CONTINUE;
    }
    public int msgMoveToCommonSpot(obj_id self, dictionary params) throws InterruptedException
    {
        force_rank.moveEnclavedPlayerToNeutralCell(self);
        return SCRIPT_CONTINUE;
    }
    public int PEFSynchResponse(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("force_rank", "player_force_rank::PEFSynchResponse: -> (" + utils.getRealPlayerFirstName(self) + ") Got PEF synch response.");
        if (!params.containsKey("enemies"))
        {
            LOG("force_rank", "player_force_rank::PEFSynchResponse: -> (" + utils.getRealPlayerFirstName(self) + ") Missing @sender or @enemies params. Not updating PEF.");
        }
        obj_id[] enemies = params.getObjIdArray("enemies");
        if (enemies != null)
        {
            force_rank.makePlayerEnemyOfGroup(self, enemies);
            LOG("force_rank", "player_force_rank::PEFSynchResponse: -> (" + utils.getRealPlayerFirstName(self) + ") PEFd enemies from enclave.");
            for (int i = 0; i < enemies.length; i++)
            {
                LOG("force_rank", "player_force_rank::PEFSynchResponse: -> [" + enemies[i] + "]");
            }
        }
        else 
        {
            LOG("force_rank", "player_force_rank::PEFSynchResponse: -> (" + utils.getRealPlayerFirstName(self) + ") PEFd enemies received as NULL from enclave.");
        }
        return SCRIPT_CONTINUE;
    }
    public int msgPvPActionStart(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id initiator = params.getObjId("initiator");
        String pvpAction = params.getString("pvpAction");
        obj_id[] actionTargets = params.getObjIdArray("actionTargets");
        obj_id enclave = params.getObjId("enclave");
        if (actionTargets == null || actionTargets.length < 1)
        {
            return SCRIPT_CONTINUE;
        }
        force_rank.makePlayerEnemyOfGroup(self, actionTargets);
        if (pvpAction.equals(force_rank.ACTION_VENDETTA))
        {
            setObjVar(self, force_rank.VAR_NOTIFY_ENCLAVE_OF_DEATH, enclave);
            string_id id = new string_id("pvp_rating", "vendetta_target_start");
            obj_id target = initiator;
            if (initiator == self)
            {
                target = actionTargets[0];
                id = new string_id("pvp_rating", "vendetta_initiator_start");
            }
            prose_package pp = prose.getPackage(id, target);
            if (pp != null)
            {
                sendSystemMessageProse(self, pp);
            }
        }
        else 
        {
            if (utils.getElementPositionInArray(actionTargets, self) > -1)
            {
                setObjVar(self, force_rank.VAR_NOTIFY_ENCLAVE_OF_DEATH, enclave);
            }
            string_id sid = null;
            obj_id target = actionTargets[0];
            if (pvpAction.equals(force_rank.ACTION_BANISHMENT))
            {
                sid = new string_id("pvp_rating", "banishment_start");
            }
            else if (pvpAction.equals(force_rank.ACTION_PURGE_COUNCIL))
            {
                sid = new string_id("pvp_rating", "purge_start");
            }
            else if (pvpAction.equals(force_rank.ACTION_CANDIDATE_SUDDEN_DEATH))
            {
                sid = new string_id("pvp_rating", "sudden_death_start");
            }
            prose_package pp2 = prose.getPackage(sid, target);
            if (pp2 != null)
            {
                sendSystemMessageProse(self, pp2);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int msgPvPActionEnd(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id killer = null;
        if (params.containsKey("killer"))
        {
            killer = params.getObjId("killer");
        }
        String pvpAction = params.getString("pvpAction");
        obj_id[] peaceTargets = params.getObjIdArray("peaceTargets");
        boolean actionTimedOut = params.getBoolean("actionTimedOut");
        force_rank.makePlayerFriendsWithGroup(self, peaceTargets);
        if (pvpAction.equals(force_rank.ACTION_CANDIDATE_SUDDEN_DEATH))
        {
            string_id id = null;
            obj_id target = null;
            if (!actionTimedOut)
            {
                id = new string_id("pvp_rating", "sudden_death_death");
                target = peaceTargets[0];
                if (killer != null && killer == self)
                {
                    target = peaceTargets[0];
                }
                prose_package pp = prose.getPackage(id, target);
                sendSystemMessageProse(self, pp);
            }
        }
        else 
        {
            string_id id = null;
            obj_id target = null;
            if (!actionTimedOut)
            {
                id = new string_id("pvp_rating", "pvp_action_end_death");
                target = peaceTargets[0];
                prose_package ppto = prose.getPackage(id, target);
                sendSystemMessageProse(self, ppto);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int XPDeathValidateResponse(obj_id self, dictionary params) throws InterruptedException
    {
        java.util.Enumeration pKeys = params.keys();
        obj_id player = null;
        int curDelta = 0;
        while (pKeys.hasMoreElements())
        {
            player = (obj_id)pKeys.nextElement();
            curDelta = params.getInt(player);
            if (curDelta > 0)
            {
                trace.log("force_rank", "Dark Jedi PvP Council XP Adjustment for %TU: " + curDelta, player, trace.TL_CS_LOG);
                force_rank.adjustForceRankXP(player, curDelta);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int msgForceRankPromotePlayer(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("force_rank", "player_force_rank.msgForceRankPromotePlayer -- " + params);
        if (params.containsKey("success"))
        {
            boolean success = params.getBoolean("success");
            if (success)
            {
                obj_id enclave = force_rank.getEnclave(self);
                if (isIdValid(enclave))
                {
                    messageTo(enclave, "msgForceEnclaveUpdate", null, 0.0f, false);
                }
                force_rank.grantRankItems(self);
                String rank_skill = force_rank.getForceRankSkill(force_rank.getForceRank(self), force_rank.getCouncilAffiliation(self));
                int new_rank = force_rank.getForceRank(self);
                updateJediScriptData(self, "bountyTrackingData.forceRank", new_rank);
                string_id rank_name = new string_id(force_rank.STF_FILE, "rank" + new_rank);
                prose_package pp = prose.getPackage(new string_id(force_rank.STF_FILE, "rank_gained"), rank_name, 0);
                sendSystemMessageProse(self, pp);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int msgForceRankDemotePlayer(obj_id self, dictionary params) throws InterruptedException
    {
        if (params.containsKey("success"))
        {
            boolean success = params.getBoolean("success");
            if (success)
            {
                int new_rank = params.getInt("rank");
                int current_rank = force_rank.getForceRank(self);
                if (new_rank >= current_rank)
                {
                    LOG("force_rank", "player_force_rank.msgForceRankDemotePlayer -- can't demote " + self + " since the demotion rank is >= current rank.");
                    return SCRIPT_CONTINUE;
                }
                force_rank.removeForceRankSkills(self, new_rank);
                setObjVar(self, force_rank.VAR_RANK, new_rank);
                updateJediScriptData(self, "bountyTrackingData.forceRank", new_rank);
                obj_id enclave = force_rank.getEnclave(self);
                if (isIdValid(enclave))
                {
                    messageTo(enclave, "msgForceEnclaveUpdate", null, 0.0f, false);
                }
                string_id rank_name = new string_id(force_rank.STF_FILE, "rank" + new_rank);
                prose_package pp = prose.getPackage(new string_id(force_rank.STF_FILE, "rank_lost"), rank_name, 0);
                sendSystemMessageProse(self, pp);
            }
            if (utils.hasScriptVar(self, "jedi.revokeSkill"))
            {
                force_rank.removeFromForceRankSystem(self, false);
                String skill = utils.getStringScriptVar(self, "jedi.revokeSkill");
                revokeSkill(self, skill);
                utils.removeScriptVar(self, "jedi.revokeSkill");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int msgFRSCHallengeViewScores(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("force_rank", "player_force_rank.msgFRSCHallengeViewScores");
        utils.removeScriptVar(self, arena.SCRIPT_VAR_SUI_CH_PID);
        utils.removeScriptVar(self, arena.SCRIPT_VAR_CH_TERMINAL);
        return SCRIPT_CONTINUE;
    }
    public int msgFRSChallengeViewChallenges(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id terminal = utils.getObjIdScriptVar(self, arena.SCRIPT_VAR_CH_TERMINAL);
        int[] challengesToView = utils.getIntArrayScriptVar(self, arena.SCRIPT_VAR_CH_VIEW_CHALLENGES);
        utils.removeScriptVar(self, arena.SCRIPT_VAR_SUI_CH_PID);
        utils.removeScriptVar(self, arena.SCRIPT_VAR_CH_TERMINAL);
        utils.removeScriptVar(self, arena.SCRIPT_VAR_CH_VIEW_CHALLENGES);
        if (!isIdValid(terminal))
        {
            LOG("force_rank", "player_force_rank.msgFRSVoteRecordSelect -- terminal is invalid");
            return SCRIPT_CONTINUE;
        }
        else if (!terminal.isLoaded())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id enclave = getTopMostContainer(self);
        if (!isIdValid(enclave))
        {
            LOG("force_rank", "player_force_rank.msgFRSVoteRecordSelect -- enclave is invalid");
            return SCRIPT_CONTINUE;
        }
        if (!hasScript(enclave, force_rank.SCRIPT_ENCLAVE_CONTROLLER))
        {
            LOG("force_rank", "player_force_rank.msgFRSVoteRecordSelect -- " + enclave + " is not an enclave building.");
            return SCRIPT_CONTINUE;
        }
        int row_selected = sui.getListboxSelectedRow(params);
        if (row_selected == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (row_selected + 1 > challengesToView.length)
        {
            LOG("force_rank", "player_force_rank::msgFRSChallengeViewChallenges: -> Selected Idx out of bounds.");
            return SCRIPT_CONTINUE;
        }
        String[] rankChallenges = arena.getChallengerNamesWithTimeRemaining(terminal, challengesToView[row_selected]);
        int pid = sui.listbox(self, self, "@pvp_rating:ch_terminal_pending", rankChallenges, "msgFRSChallengeDoneLooking");
        utils.setScriptVar(self, arena.SCRIPT_VAR_SUI_CH_PID, pid);
        utils.setScriptVar(self, arena.SCRIPT_VAR_CH_TERMINAL, self);
        return SCRIPT_CONTINUE;
    }
    public int msgFRSChallengeDoneLooking(obj_id self, dictionary params) throws InterruptedException
    {
        utils.removeScriptVar(self, arena.SCRIPT_VAR_SUI_CH_PID);
        utils.removeScriptVar(self, arena.SCRIPT_VAR_CH_TERMINAL);
        return SCRIPT_CONTINUE;
    }
    public int msgFRSChallengeConfirmAcceptChallenge(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id terminal = utils.getObjIdScriptVar(self, arena.SCRIPT_VAR_CH_TERMINAL);
        utils.removeScriptVar(self, arena.SCRIPT_VAR_SUI_CH_PID);
        utils.removeScriptVar(self, arena.SCRIPT_VAR_CH_TERMINAL);
        if (!isIdValid(terminal))
        {
            LOG("force_rank", "player_force_rank.msgFRSChallengeConfirmIssueChallenge -- terminal is invalid");
            return SCRIPT_CONTINUE;
        }
        else if (!terminal.isLoaded())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id enclave = getTopMostContainer(self);
        if (!isIdValid(enclave))
        {
            LOG("force_rank", "player_force_rank.msgFRSChallengeConfirmIssueChallenge -- enclave is invalid");
            return SCRIPT_CONTINUE;
        }
        if (!hasScript(enclave, force_rank.SCRIPT_ENCLAVE_CONTROLLER))
        {
            LOG("force_rank", "player_force_rank.msgFRSChallengeConfirmIssueChallenge -- " + enclave + " is not an enclave building.");
            return SCRIPT_CONTINUE;
        }
        if (getDistance(terminal, self) > 15)
        {
            sendSystemMessage(self, new string_id("pvp_rating", "ch_terminal_too_far"));
            return SCRIPT_CONTINUE;
        }
        String button = params.getString("buttonPressed");
        if (button.equals("Cancel") || button.equals("No"))
        {
            return SCRIPT_CONTINUE;
        }
        int rank = force_rank.getForceRank(enclave, getFirstName(self));
        LOG("force_rank", "player_force_rank::msgFRSChallengeConfirmAcceptChallenge: -> Rank for self is " + rank);
        if (rank > 1)
        {
            if ((arena.getChallengerIdsForRank(terminal, rank)).size() > 0)
            {
                String[] challengers = arena.getChallengerNamesWithTimeRemaining(terminal, rank);
                Vector challengerIds = arena.getChallengerIdsForRank(terminal, rank);
                int pid = sui.listbox(self, self, "@pvp_rating:ch_terminal_choose_challenge", challengers, "msgFRSChallengeConfirmAcceptChallengeFinal");
                utils.setScriptVar(self, arena.SCRIPT_VAR_SUI_CH_PID, pid);
                utils.setScriptVar(self, arena.SCRIPT_VAR_CH_TERMINAL, terminal);
                utils.setScriptVar(self, arena.SCRIPT_VAR_CH_SELECT_ACCEPT, challengerIds);
            }
            else 
            {
                sendSystemMessage(self, new string_id("pvp_rating", "ch_terminal_no_challenges_for_rank"));
            }
        }
        else 
        {
            sendSystemMessage(self, new string_id("pvp_rating", "ch_terminal_no_challenges_for_rank"));
        }
        sendDirtyObjectMenuNotification(terminal);
        return SCRIPT_CONTINUE;
    }
    public int msgFRSChallengeConfirmAcceptChallengeFinal(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id terminal = utils.getObjIdScriptVar(self, arena.SCRIPT_VAR_CH_TERMINAL);
        obj_id[] challengers = utils.getObjIdArrayScriptVar(self, arena.SCRIPT_VAR_CH_SELECT_ACCEPT);
        utils.removeScriptVar(self, arena.SCRIPT_VAR_SUI_CH_PID);
        utils.removeScriptVar(self, arena.SCRIPT_VAR_CH_TERMINAL);
        utils.removeScriptVar(self, arena.SCRIPT_VAR_CH_SELECT_ACCEPT);
        if (!isIdValid(terminal))
        {
            LOG("force_rank", "player_force_rank.msgFRSChallengeConfirmIssueChallenge -- terminal is invalid");
            return SCRIPT_CONTINUE;
        }
        else if (!terminal.isLoaded())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id enclave = getTopMostContainer(self);
        if (!isIdValid(enclave))
        {
            LOG("force_rank", "player_force_rank.msgFRSChallengeConfirmIssueChallenge -- enclave is invalid");
            return SCRIPT_CONTINUE;
        }
        if (!hasScript(enclave, force_rank.SCRIPT_ENCLAVE_CONTROLLER))
        {
            LOG("force_rank", "player_force_rank.msgFRSChallengeConfirmIssueChallenge -- " + enclave + " is not an enclave building.");
            return SCRIPT_CONTINUE;
        }
        if (getDistance(terminal, self) > 15)
        {
            sendSystemMessage(self, new string_id("pvp_rating", "ch_terminal_too_far"));
            return SCRIPT_CONTINUE;
        }
        int row_selected = sui.getListboxSelectedRow(params);
        if (row_selected == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (row_selected + 1 > challengers.length)
        {
            LOG("force_rank", "player_force_rank::msgFRSChallengeConfirmAcceptChallengeFinal: -> Selected Idx out of bounds.");
            return SCRIPT_CONTINUE;
        }
        obj_id challenger = challengers[row_selected];
        if (arena.validateAndRemoveChallenger(terminal, challenger, self, enclave))
        {
            if (!arena.acceptChallenge(terminal, self, challenger))
            {
                trace.log("force_rank", "Failed to accept challenge issued by %TU.", challenger, trace.TL_CS_LOG | trace.TL_DEBUG);
                arena.addChallengeIssueData(terminal, challenger, force_rank.getForceRank(enclave, getFirstName(self)));
            }
        }
        sendDirtyObjectMenuNotification(terminal);
        return SCRIPT_CONTINUE;
    }
    public int msgFRSChallengeConfirmIssueChallenge(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id terminal = utils.getObjIdScriptVar(self, arena.SCRIPT_VAR_CH_TERMINAL);
        utils.removeScriptVar(self, arena.SCRIPT_VAR_SUI_CH_PID);
        utils.removeScriptVar(self, arena.SCRIPT_VAR_CH_TERMINAL);
        if (!isIdValid(terminal))
        {
            LOG("force_rank", "player_force_rank.msgFRSChallengeConfirmIssueChallenge -- terminal is invalid");
            return SCRIPT_CONTINUE;
        }
        else if (!terminal.isLoaded())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id enclave = getTopMostContainer(self);
        if (!isIdValid(enclave))
        {
            LOG("force_rank", "player_force_rank.msgFRSChallengeConfirmIssueChallenge -- enclave is invalid");
            return SCRIPT_CONTINUE;
        }
        if (!hasScript(enclave, force_rank.SCRIPT_ENCLAVE_CONTROLLER))
        {
            LOG("force_rank", "player_force_rank.msgFRSChallengeConfirmIssueChallenge -- " + enclave + " is not an enclave building.");
            return SCRIPT_CONTINUE;
        }
        String button = params.getString("buttonPressed");
        if (button.equals("Cancel") || button.equals("No"))
        {
            return SCRIPT_CONTINUE;
        }
        int rank = force_rank.getForceRank(enclave, getFirstName(self));
        if (rank > 0 && rank < 11)
        {
            if (arena.isArenaOpenForChallenges(terminal))
            {
                int avail_slots = force_rank.getAvailableRankSlots(enclave, rank + 1);
                if (avail_slots >= 3)
                {
                    if (hasObjVar(self, "force_rank.qa.overrideArenaOpenSlots"))
                    {
                        sendSystemMessageTestingOnly(self, "QA open slot Vote-Force override.");
                    }
                    else 
                    {
                        sendSystemMessage(self, new string_id("pvp_rating", "ch_terminal_no_need_challenge"));
                        return SCRIPT_CONTINUE;
                    }
                }
                if (arena.canPlayerIssueChallenge(self, terminal))
                {
                    if (!arena.issueChallengeAgainstRank(terminal, self, rank + 1))
                    {
                        LOG("force_rank", "player_force_rank.msgFRSChallengeConfirmIssueChallenge -- failed to issue challenge");
                    }
                }
            }
            else 
            {
                sendSystemMessage(self, new string_id("pvp_rating", "ch_terminal_arena_closed"));
            }
        }
        else 
        {
            prose_package rankBoundsPP = prose.getPackage(new string_id("pvp_rating", "ch_terminal_cant_challenge_rank_bounds"), rank);
            sendSystemMessageProse(self, rankBoundsPP);
        }
        sendDirtyObjectMenuNotification(terminal);
        return SCRIPT_CONTINUE;
    }
    public int handlePlayerDeath(obj_id self, dictionary params) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, arena.VAR_I_AM_DUELING))
        {
            return SCRIPT_CONTINUE;
        }
        arena.duelistDied(self, utils.getObjIdScriptVar(self, arena.VAR_I_AM_DUELING), force_rank.getEnclave(self), false);
        return SCRIPT_CONTINUE;
    }
    public int msgFRSVoteStatusSelect(obj_id self, dictionary params) throws InterruptedException
    {
        PROFILER_START("vote_select");
        obj_id terminal = utils.getObjIdScriptVar(self, SCRIPT_VAR_TERMINAL);
        utils.removeScriptVar(self, SCRIPT_VAR_SUI_PID);
        utils.removeScriptVar(self, SCRIPT_VAR_TERMINAL);
        if (!isIdValid(terminal))
        {
            LOG("force_rank", "terminal_frs_voting.OnObjectMenuSelect -- terminal is invalid");
            PROFILER_STOP("vote_select");
            return SCRIPT_CONTINUE;
        }
        else if (!terminal.isLoaded())
        {
            PROFILER_STOP("vote_select");
            return SCRIPT_CONTINUE;
        }
        obj_id enclave = getTopMostContainer(self);
        if (!isIdValid(enclave))
        {
            LOG("force_rank", "terminal_frs_voting.OnObjectMenuSelect -- enclave is invalid");
            PROFILER_STOP("vote_select");
            return SCRIPT_CONTINUE;
        }
        if (!hasScript(enclave, force_rank.SCRIPT_ENCLAVE_CONTROLLER))
        {
            LOG("force_rank", "terminal_frs_voting.OnObjectMenuRequest -- " + enclave + " is not an enclave building.");
            PROFILER_STOP("vote_select");
            return SCRIPT_CONTINUE;
        }
        String button = params.getString("buttonPressed");
        if (button.equals("Cancel") || button.equals("No"))
        {
            PROFILER_STOP("vote_select");
            return SCRIPT_CONTINUE;
        }
        int row_selected = sui.getListboxSelectedRow(params);
        if (row_selected == -1)
        {
            PROFILER_STOP("vote_select");
            return SCRIPT_CONTINUE;
        }
        int status = force_rank.getVoteStatus(enclave, row_selected + 1);
        if (status == -1)
        {
            sendSystemMessage(self, new string_id(force_rank.STF_FILE, "invalid_rank_selected"));
            PROFILER_STOP("vote_select");
            return SCRIPT_CONTINUE;
        }
        int end_time = force_rank.getVoteEndTime(enclave, row_selected + 1);
        int time_remaining = end_time - getGameTime();
        String time_str = null;
        if (time_remaining > 0)
        {
            time_str = player_structure.assembleTimeRemaining(player_structure.convertSecondsTime(time_remaining));
        }
        else 
        {
            time_str = "closed.";
        }
        Vector dsrc = new Vector();
        dsrc.setSize(0);
        String[] status_names = 
        {
            "Petitioning",
            "Voting",
            "Acceptance",
            "Inactive"
        };
        dsrc.add("Current Stage: " + status_names[status - 1]);
        PROFILER_START("vote_select.slots");
        int slots_available = force_rank.getAvailableRankSlots(enclave, row_selected + 1);
        PROFILER_STOP("vote_select.slots");
        if (slots_available < 0)
        {
            slots_available = 0;
        }
        dsrc.add("Seats Available: " + slots_available);
        if (time_str != null)
        {
            dsrc.add("Time Remaining: " + time_str);
        }
        dsrc.add("");
        if (status == 1 || status == 2)
        {
            String[] petitioners = force_rank.getRankPetitioners(enclave, row_selected + 1);
            int[] votes = force_rank.getRankPetitionerVotes(enclave, row_selected + 1);
            PROFILER_START("vote_select.petitioners");
            if (petitioners != null)
            {
                if (petitioners.length > 20)
                {
                    CustomerServiceLog("force_rank", "WARNING: msgFRSVoteStatusSelect petitioners array too large, size = " + petitioners.length);
                }
                dsrc.add("Petitioners: ");
                for (int i = 0; i < petitioners.length; i++)
                {
                    if (status == 2 && votes != null && votes.length > i)
                    {
                        dsrc.add("   " + petitioners[i] + "    " + votes[i]);
                    }
                    else 
                    {
                        dsrc.add("   " + petitioners[i]);
                    }
                }
            }
            PROFILER_STOP("vote_select.petitioners");
        }
        else if (status == 3 || status == 4)
        {
            PROFILER_START("vote_select.winners");
            String[] winners = force_rank.getVoteWinners(enclave, row_selected + 1);
            if (winners != null)
            {
                if (winners.length > 20)
                {
                    CustomerServiceLog("force_rank", "WARNING: msgFRSVoteStatusSelect winners array too large, size = " + winners.length);
                }
                dsrc.add("Awaiting Acceptance:");
                for (int i = 0; i < winners.length; i++)
                {
                    dsrc.add("   " + winners[i]);
                }
            }
            PROFILER_STOP("vote_select.winners");
        }
        String rank_str = localize(new string_id(force_rank.STF_FILE, "rank" + (row_selected + 1)));
        PROFILER_START("vote_select.sui");
        int pid = sui.listbox(self, self, "Vote stats for " + rank_str, sui.OK_CANCEL, "@force_rank:vote_status", dsrc, "msgFRSVoteStatusClosed");
        PROFILER_STOP("vote_select.sui");
        utils.setScriptVar(self, SCRIPT_VAR_SUI_PID, pid);
        PROFILER_STOP("vote_select");
        return SCRIPT_CONTINUE;
    }
    public int msgFRSVoteStatusClosed(obj_id self, dictionary params) throws InterruptedException
    {
        utils.removeScriptVar(self, SCRIPT_VAR_SUI_PID);
        return SCRIPT_CONTINUE;
    }
    public int msgFRSVoteRecordSelect(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id terminal = utils.getObjIdScriptVar(self, SCRIPT_VAR_TERMINAL);
        utils.removeScriptVar(self, SCRIPT_VAR_SUI_PID);
        if (!isIdValid(terminal))
        {
            LOG("force_rank", "player_force_rank.msgFRSVoteRecordSelect -- terminal is invalid");
            return SCRIPT_CONTINUE;
        }
        else if (!terminal.isLoaded())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id enclave = getTopMostContainer(self);
        if (!isIdValid(enclave))
        {
            LOG("force_rank", "player_force_rank.msgFRSVoteRecordSelect -- enclave is invalid");
            return SCRIPT_CONTINUE;
        }
        if (!hasScript(enclave, force_rank.SCRIPT_ENCLAVE_CONTROLLER))
        {
            LOG("force_rank", "player_force_rank.msgFRSVoteRecordSelect -- " + enclave + " is not an enclave building.");
            return SCRIPT_CONTINUE;
        }
        String button = params.getString("buttonPressed");
        if (button.equals("Cancel") || button.equals("No"))
        {
            return SCRIPT_CONTINUE;
        }
        int row_selected = sui.getListboxSelectedRow(params);
        if (row_selected == -1)
        {
            return SCRIPT_CONTINUE;
        }
        int status = force_rank.getVoteStatus(enclave, row_selected + 1);
        if (status == -1)
        {
            sendSystemMessage(self, new string_id(force_rank.STF_FILE, "invalid_rank_selected"));
            return SCRIPT_CONTINUE;
        }
        if (status != 2)
        {
            sendSystemMessage(self, new string_id(force_rank.STF_FILE, "voting_not_open"));
            return SCRIPT_CONTINUE;
        }
        int player_rank = force_rank.getForceRank(self);
        int vote_weight = force_rank.getVoteWeight(player_rank, row_selected + 1);
        if (vote_weight < 1)
        {
            sendSystemMessage(self, new string_id(force_rank.STF_FILE, "cant_vote_for_rank"));
            return SCRIPT_CONTINUE;
        }
        Vector players_voted = new Vector();
        players_voted.setSize(0);
        if (hasObjVar(terminal, force_rank.BATCH_VAR_VOTERS))
        {
            players_voted = utils.getResizeableStringBatchObjVar(terminal, force_rank.BATCH_VAR_VOTERS);
        }
        int idx = players_voted.indexOf(getFirstName(self));
        if (idx != -1)
        {
            sendSystemMessage(self, new string_id(force_rank.STF_FILE, "already_voted"));
            return SCRIPT_CONTINUE;
        }
        String[] petitioners = force_rank.getRankPetitioners(enclave, row_selected + 1);
        if (petitioners == null)
        {
            sendSystemMessage(self, new string_id(force_rank.STF_FILE, "noone_to_vote_for"));
            return SCRIPT_CONTINUE;
        }
        int pid = sui.listbox(self, self, "@force_rank:vote_record_select_player", sui.OK_CANCEL, "@force_rank:vote_player_selection", petitioners, "msgFRSVoteRecordPlayer");
        utils.setScriptVar(self, SCRIPT_VAR_SUI_PID, pid);
        utils.setScriptVar(self, SCRIPT_VAR_PETITIONERS, petitioners);
        utils.setScriptVar(self, SCRIPT_VAR_VOTE_RANK, row_selected + 1);
        return SCRIPT_CONTINUE;
    }
    public int msgFRSVoteRecordPlayer(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id terminal = utils.getObjIdScriptVar(self, SCRIPT_VAR_TERMINAL);
        String[] petitioners = utils.getStringArrayScriptVar(self, SCRIPT_VAR_PETITIONERS);
        int rank = utils.getIntScriptVar(self, SCRIPT_VAR_VOTE_RANK);
        utils.removeScriptVar(self, SCRIPT_VAR_SUI_PID);
        utils.removeScriptVar(self, SCRIPT_VAR_TERMINAL);
        utils.removeScriptVar(self, SCRIPT_VAR_PETITIONERS);
        utils.removeScriptVar(self, SCRIPT_VAR_VOTE_RANK);
        if (!isIdValid(terminal))
        {
            LOG("force_rank", "player_force_rank.msgFRSVoteRecordPlayer -- terminal is invalid");
            return SCRIPT_CONTINUE;
        }
        else if (!terminal.isLoaded())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id enclave = getTopMostContainer(self);
        if (!isIdValid(enclave))
        {
            LOG("force_rank", "player_force_rank.msgFRSVoteRecordPlayer -- enclave is invalid");
            return SCRIPT_CONTINUE;
        }
        if (!hasScript(enclave, force_rank.SCRIPT_ENCLAVE_CONTROLLER))
        {
            LOG("force_rank", "player_force_rank.msgFRSVoteRecordPlayer -- " + enclave + " is not an enclave building.");
            return SCRIPT_CONTINUE;
        }
        if (petitioners == null)
        {
            LOG("force_rank", "player_force_rank.msgFRSVoteRecordPlayer -- petitioners is null.");
            return SCRIPT_CONTINUE;
        }
        if (rank < 1 || rank > 11)
        {
            sendSystemMessage(self, new string_id(force_rank.STF_FILE, "invalid_rank_selected"));
            return SCRIPT_CONTINUE;
        }
        String button = params.getString("buttonPressed");
        if (button.equals("Cancel") || button.equals("No"))
        {
            return SCRIPT_CONTINUE;
        }
        int row_selected = sui.getListboxSelectedRow(params);
        if (row_selected == -1)
        {
            return SCRIPT_CONTINUE;
        }
        int status = force_rank.getVoteStatus(enclave, rank);
        if (status == -1)
        {
            sendSystemMessage(self, new string_id(force_rank.STF_FILE, "invalid_rank_selected"));
            return SCRIPT_CONTINUE;
        }
        if (status != 2)
        {
            sendSystemMessage(self, new string_id(force_rank.STF_FILE, "voting_not_open"));
            return SCRIPT_CONTINUE;
        }
        if (force_rank.isVoteTimeExpired(enclave, rank))
        {
            sendSystemMessage(self, new string_id(force_rank.STF_FILE, "vote_time_expired"));
            return SCRIPT_CONTINUE;
        }
        int player_rank = force_rank.getForceRank(self);
        int vote_weight = force_rank.getVoteWeight(player_rank, rank);
        if (vote_weight < 1)
        {
            sendSystemMessage(self, new string_id(force_rank.STF_FILE, "cant_vote_for_rank"));
            return SCRIPT_CONTINUE;
        }
        Vector players_voted = new Vector();
        players_voted.setSize(0);
        if (hasObjVar(terminal, force_rank.BATCH_VAR_VOTERS + rank))
        {
            players_voted = utils.getResizeableStringBatchObjVar(terminal, force_rank.BATCH_VAR_VOTERS + rank);
        }
        int idx = players_voted.indexOf(getFirstName(self));
        if (idx != -1)
        {
            sendSystemMessage(self, new string_id(force_rank.STF_FILE, "already_voted"));
            return SCRIPT_CONTINUE;
        }
        String player_selected = petitioners[row_selected];
        players_voted.add(getFirstName(self));
        String[] vote_list = new String[players_voted.size()];
        players_voted.toArray(vote_list);
        utils.setBatchObjVar(terminal, force_rank.BATCH_VAR_VOTERS + rank, vote_list);
        int[] votes = force_rank.getRankPetitionerVotes(enclave, rank);
        votes[row_selected] += vote_weight;
        String obj_var_name = force_rank.VAR_VOTING_BASE + rank + ".votes";
        setObjVar(enclave, obj_var_name, votes);
        prose_package pp = prose.getPackage(new string_id(force_rank.STF_FILE, "vote_cast"), player_selected);
        sendSystemMessageProse(self, pp);
        return SCRIPT_CONTINUE;
    }
    public int msgFRSVotePromotionSelect(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id terminal = utils.getObjIdScriptVar(self, SCRIPT_VAR_TERMINAL);
        utils.removeScriptVar(self, SCRIPT_VAR_SUI_PID);
        utils.removeScriptVar(self, SCRIPT_VAR_TERMINAL);
        if (!isIdValid(terminal))
        {
            LOG("force_rank", "player_force_rank.msgFRSVotePromotionSelect -- terminal is invalid");
            return SCRIPT_CONTINUE;
        }
        else if (!terminal.isLoaded())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id enclave = getTopMostContainer(self);
        if (!isIdValid(enclave))
        {
            LOG("force_rank", "player_force_rank.msgFRSVotePromotionSelect -- enclave is invalid");
            return SCRIPT_CONTINUE;
        }
        if (!hasScript(enclave, force_rank.SCRIPT_ENCLAVE_CONTROLLER))
        {
            LOG("force_rank", "player_force_rank.msgFRSVotePromotionSelect -- " + enclave + " is not an enclave building.");
            return SCRIPT_CONTINUE;
        }
        String button = params.getString("buttonPressed");
        if (button.equals("Cancel") || button.equals("No"))
        {
            return SCRIPT_CONTINUE;
        }
        int row_selected = sui.getListboxSelectedRow(params);
        if (row_selected == -1)
        {
            return SCRIPT_CONTINUE;
        }
        int status = force_rank.getVoteStatus(enclave, row_selected + 1);
        if (status == -1)
        {
            sendSystemMessage(self, new string_id(force_rank.STF_FILE, "invalid_rank_selected"));
            return SCRIPT_CONTINUE;
        }
        if (status != 3)
        {
            sendSystemMessage(self, new string_id(force_rank.STF_FILE, "acceptance_not_open"));
            return SCRIPT_CONTINUE;
        }
        if (force_rank.isVoteTimeExpired(enclave, row_selected + 1))
        {
            sendSystemMessage(self, new string_id(force_rank.STF_FILE, "acceptance_time_expired"));
            return SCRIPT_CONTINUE;
        }
        String obj_var_name = force_rank.VAR_VOTING_BASE + (row_selected + 1) + ".winner";
        Vector winners = getResizeableStringArrayObjVar(enclave, obj_var_name);
        int idx = winners.indexOf(getFirstName(self));
        if (winners == null || idx == -1)
        {
            sendSystemMessage(self, new string_id(force_rank.STF_FILE, "not_a_winner"));
            return SCRIPT_CONTINUE;
        }
        int slots = force_rank.getAvailableRankSlots(enclave, row_selected + 1);
        if (slots < 1)
        {
            setObjVar(enclave, force_rank.VAR_VOTING_BASE + (row_selected + 1) + ".status", 4);
            string_id sub = new string_id(force_rank.STF_FILE, "vote_last_seat_taken_sub");
            string_id body = new string_id(force_rank.STF_FILE, "vote_last_seat_taken_body");
            String rank_str = localize(new string_id(force_rank.STF_FILE, "rank" + (row_selected + 1)));
            prose_package pp = prose.getPackage(body, rank_str);
            for (int i = 0; i < winners.size(); i++)
            {
                utils.sendMail(sub, pp, ((String)winners.get(i)), "Enclave Records");
            }
            sendSystemMessageProse(self, pp);
            return SCRIPT_CONTINUE;
        }
        if (force_rank.isPlayerEligibleForPromotion(self, row_selected + 1))
        {
            if (force_rank.getForceRank(self) >= row_selected + 1)
            {
                sendSystemMessage(self, new string_id(force_rank.STF_FILE, "promotion_already_have_rank"));
                return SCRIPT_CONTINUE;
            }
            sendSystemMessage(self, new string_id(force_rank.STF_FILE, "promotion_accepted"));
            CustomerServiceLog("force_rank", "%TU has accepted a promotion to rank " + (row_selected + 1), self, null);
            force_rank.promoteForceRank(self);
            string_id sub = new string_id(force_rank.STF_FILE, "promotion_accepted_sub");
            string_id body = new string_id(force_rank.STF_FILE, "promotion_accepted_body");
            String rank_str = localize(new string_id(force_rank.STF_FILE, "rank" + (row_selected + 1)));
            prose_package pp = prose.getPackage(body, getName(self), rank_str);
            force_rank.sendRankMail(enclave, row_selected + 1, sub, pp);
            winners.remove(idx);
            if (winners.size() < 1)
            {
                force_rank.resetVotingTerminal(enclave, row_selected + 1);
            }
            else 
            {
                setObjVar(enclave, obj_var_name, winners);
                if (slots - 1 < 1)
                {
                    setObjVar(enclave, force_rank.VAR_VOTING_BASE + (row_selected + 1) + ".status", 4);
                    sub = new string_id(force_rank.STF_FILE, "vote_last_seat_taken_sub");
                    body = new string_id(force_rank.STF_FILE, "vote_last_seat_taken_body");
                    rank_str = localize(new string_id(force_rank.STF_FILE, "rank" + (row_selected + 1)));
                    pp = prose.getPackage(body, rank_str);
                    for (int i = 0; i < winners.size(); i++)
                    {
                        utils.sendMail(sub, pp, ((String)winners.get(i)), "Enclave Records");
                    }
                }
            }
        }
        else 
        {
            sendSystemMessage(self, new string_id(force_rank.STF_FILE, "not_eligible_for_promotion"));
        }
        return SCRIPT_CONTINUE;
    }
    public int msgFRSVotePetitionSelect(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id terminal = utils.getObjIdScriptVar(self, SCRIPT_VAR_TERMINAL);
        utils.removeScriptVar(self, SCRIPT_VAR_SUI_PID);
        utils.removeScriptVar(self, SCRIPT_VAR_TERMINAL);
        if (!isIdValid(terminal))
        {
            LOG("force_rank", "player_force_rank.msgFRSVotePromotionSelect -- terminal is invalid");
            return SCRIPT_CONTINUE;
        }
        else if (!terminal.isLoaded())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id enclave = getTopMostContainer(self);
        if (!isIdValid(enclave))
        {
            LOG("force_rank", "player_force_rank.msgFRSVotePromotionSelect -- enclave is invalid");
            return SCRIPT_CONTINUE;
        }
        if (!hasScript(enclave, force_rank.SCRIPT_ENCLAVE_CONTROLLER))
        {
            LOG("force_rank", "player_force_rank.msgFRSVotePromotionSelect -- " + enclave + " is not an enclave building.");
            return SCRIPT_CONTINUE;
        }
        String button = params.getString("buttonPressed");
        if (button.equals("Cancel") || button.equals("No"))
        {
            return SCRIPT_CONTINUE;
        }
        int row_selected = sui.getListboxSelectedRow(params);
        if (row_selected == -1)
        {
            return SCRIPT_CONTINUE;
        }
        int status = force_rank.getVoteStatus(enclave, row_selected + 1);
        if (status == -1)
        {
            sendSystemMessage(self, new string_id(force_rank.STF_FILE, "invalid_rank_selected"));
            return SCRIPT_CONTINUE;
        }
        if (status != 1)
        {
            sendSystemMessage(self, new string_id(force_rank.STF_FILE, "petition_not_open"));
            return SCRIPT_CONTINUE;
        }
        if (force_rank.isVoteTimeExpired(enclave, row_selected + 1))
        {
            sendSystemMessage(self, new string_id(force_rank.STF_FILE, "petition_time_expired"));
            return SCRIPT_CONTINUE;
        }
        if (force_rank.getForceRank(self) >= row_selected + 1)
        {
            sendSystemMessage(self, new string_id(force_rank.STF_FILE, "petition_already_have_rank"));
            return SCRIPT_CONTINUE;
        }
        String obj_var_name = force_rank.VAR_VOTING_BASE + (row_selected + 1) + ".petition";
        Vector petitioners = getResizeableStringArrayObjVar(enclave, obj_var_name);
        if (petitioners == null)
        {
            petitioners = new Vector();
        }
        if (petitioners.indexOf(getFirstName(self)) != -1)
        {
            sendSystemMessage(self, new string_id(force_rank.STF_FILE, "already_petitioning"));
            return SCRIPT_CONTINUE;
        }
        if (petitioners.size() >= force_rank.MAX_PETITIONERS)
        {
            sendSystemMessage(self, new string_id(force_rank.STF_FILE, "petitioning_no_room"));
            return SCRIPT_CONTINUE;
        }
        if (force_rank.isPlayerEligibleForPromotion(self, row_selected + 1))
        {
            petitioners.add(getFirstName(self));
            setObjVar(enclave, obj_var_name, petitioners);
            sendSystemMessage(self, new string_id(force_rank.STF_FILE, "petitioning_complete"));
            CustomerServiceLog("force_rank", "%TU petitioned for promotion to rank " + row_selected, self, null);
        }
        else 
        {
            sendSystemMessage(self, new string_id(force_rank.STF_FILE, "petitioning_not_eligible"));
        }
        return SCRIPT_CONTINUE;
    }
    public int msgFRSDemoteSelect(obj_id self, dictionary params) throws InterruptedException
    {
        utils.removeScriptVar(self, SCRIPT_VAR_SUI_PID);
        obj_id enclave = getTopMostContainer(self);
        if (!isIdValid(enclave))
        {
            LOG("force_rank", "player_force_rank.msgFRSDemoteSelect -- enclave is invalid");
            return SCRIPT_CONTINUE;
        }
        if (!hasScript(enclave, force_rank.SCRIPT_ENCLAVE_CONTROLLER))
        {
            LOG("force_rank", "player_force_rank.msgFRSDemoteSelect -- " + enclave + " is not an enclave building.");
            return SCRIPT_CONTINUE;
        }
        String button = params.getString("buttonPressed");
        if (button.equals("Cancel") || button.equals("No"))
        {
            return SCRIPT_CONTINUE;
        }
        int row_selected = sui.getListboxSelectedRow(params);
        if (row_selected == -1)
        {
            return SCRIPT_CONTINUE;
        }
        int rank_selected = row_selected + 1;
        int tier_selected = force_rank.getForceTier(rank_selected);
        int player_rank = force_rank.getForceRank(self);
        int player_tier = force_rank.getForceTier(player_rank);
        if (player_tier != 5)
        {
            if (player_tier - 1 <= tier_selected)
            {
                sendSystemMessage(self, new string_id(force_rank.STF_FILE, "demote_too_low_rank"));
                return SCRIPT_CONTINUE;
            }
        }
        int last_demote = 0;
        if (hasObjVar(self, force_rank.VAR_REQUEST_DEMOTE))
        {
            last_demote = getIntObjVar(self, force_rank.VAR_REQUEST_DEMOTE);
        }
        if (last_demote > getGameTime())
        {
            int time_remaining = last_demote - getGameTime();
            String time_str = "1 second.";
            if (time_remaining > 0)
            {
                time_str = player_structure.assembleTimeRemaining(player_structure.convertSecondsTime(time_remaining));
            }
            prose_package pp = prose.getPackage(new string_id(force_rank.STF_FILE, "demote_too_soon"), time_str);
            sendSystemMessageProse(self, pp);
            return SCRIPT_CONTINUE;
        }
        String[] rank_list = force_rank.getPlayersInForceRank(enclave, rank_selected);
        if (rank_list == null)
        {
            sendSystemMessage(self, new string_id(force_rank.STF_FILE, "no_players_in_rank"));
            return SCRIPT_CONTINUE;
        }
        int pid = sui.listbox(self, self, "@force_rank:demote_select_player", sui.OK_CANCEL, "@force_rank:demote_selection", rank_list, "msgFRSDemotePlayerSelected");
        utils.setScriptVar(self, SCRIPT_VAR_SUI_PID, pid);
        utils.setScriptVar(self, SCRIPT_VAR_VOTE_RANK, rank_selected);
        utils.setScriptVar(self, SCRIPT_VAR_RANK_LIST, rank_list);
        return SCRIPT_CONTINUE;
    }
    public int msgFRSDemotePlayerSelected(obj_id self, dictionary params) throws InterruptedException
    {
        String[] rank_list = utils.getStringArrayScriptVar(self, SCRIPT_VAR_RANK_LIST);
        int rank_selected = utils.getIntScriptVar(self, SCRIPT_VAR_VOTE_RANK);
        utils.removeScriptVar(self, SCRIPT_VAR_SUI_PID);
        utils.removeScriptVar(self, SCRIPT_VAR_RANK_LIST);
        utils.removeScriptVar(self, SCRIPT_VAR_VOTE_RANK);
        if (rank_list == null)
        {
            LOG("force_rank", "player_force_rank.msgFRSDemotePlayerSelected -- enclave is invalid");
            return SCRIPT_CONTINUE;
        }
        if (rank_selected < 1 || rank_selected > 11)
        {
            LOG("force_rank", "player_force_rank.msgFRSDemotePlayerSelected -- rank_selected has an illegal value " + rank_selected);
            return SCRIPT_CONTINUE;
        }
        obj_id enclave = getTopMostContainer(self);
        if (!isIdValid(enclave))
        {
            LOG("force_rank", "player_force_rank.msgFRSDemotePlayerSelected -- enclave is invalid");
            return SCRIPT_CONTINUE;
        }
        if (!hasScript(enclave, force_rank.SCRIPT_ENCLAVE_CONTROLLER))
        {
            LOG("force_rank", "player_force_rank.msgFRSDemotePlayerSelected -- " + enclave + " is not an enclave building.");
            return SCRIPT_CONTINUE;
        }
        String button = params.getString("buttonPressed");
        if (button.equals("Cancel") || button.equals("No"))
        {
            return SCRIPT_CONTINUE;
        }
        int row_selected = sui.getListboxSelectedRow(params);
        if (row_selected == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (row_selected >= rank_list.length)
        {
            sendSystemMessage(self, new string_id(force_rank.STF_FILE, "invalid_selection"));
            return SCRIPT_CONTINUE;
        }
        String player_selected = rank_list[row_selected];
        int player_rank = force_rank.getForceRank(enclave, player_selected);
        if (player_rank != rank_selected)
        {
            sendSystemMessage(self, new string_id(force_rank.STF_FILE, "demote_player_changed_rank"));
            return SCRIPT_CONTINUE;
        }
        int tier_selected = force_rank.getForceTier(player_rank);
        int user_tier = force_rank.getForceTier(force_rank.getForceRank(self));
        if (user_tier != 5)
        {
            if (user_tier - 1 <= tier_selected)
            {
                sendSystemMessage(self, new string_id(force_rank.STF_FILE, "demote_too_low_rank"));
                return SCRIPT_CONTINUE;
            }
        }
        int last_demote = 0;
        if (hasObjVar(self, force_rank.VAR_REQUEST_DEMOTE))
        {
            last_demote = getIntObjVar(self, force_rank.VAR_REQUEST_DEMOTE);
        }
        if (last_demote > getGameTime())
        {
            int time_remaining = last_demote - getGameTime();
            String time_str = "1 second.";
            if (time_remaining > 0)
            {
                time_str = player_structure.assembleTimeRemaining(player_structure.convertSecondsTime(time_remaining));
            }
            prose_package pp = prose.getPackage(new string_id(force_rank.STF_FILE, "demote_too_soon"), time_str);
            sendSystemMessageProse(self, pp);
            return SCRIPT_CONTINUE;
        }
        if (force_rank.spendForceRankXP(self, force_rank.REQUEST_DEMOTION_COST * player_rank) < 0)
        {
            int cost = force_rank.REQUEST_DEMOTION_COST * player_rank;
            prose_package pp = prose.getPackage(new string_id(force_rank.STF_FILE, "insufficient_experience"), cost);
            sendSystemMessageProse(self, pp);
            return SCRIPT_CONTINUE;
        }
        if (user_tier == 5)
        {
            setObjVar(self, force_rank.VAR_REQUEST_DEMOTE, getGameTime() + force_rank.getDemotionInterval() / 2);
        }
        else 
        {
            setObjVar(self, force_rank.VAR_REQUEST_DEMOTE, getGameTime() + force_rank.getDemotionInterval());
        }
        CustomerServiceLog("force_rank", "%TU has used a demotion privilege on " + player_selected, self, null);
        force_rank.demoteForceRank(enclave, player_selected, player_rank - 1);
        prose_package pp = prose.getPackage(new string_id(force_rank.STF_FILE, "demote_player_complete"), player_selected);
        sendSystemMessageProse(self, pp);
        string_id sub = new string_id(force_rank.STF_FILE, "demote_request_sub");
        string_id body = new string_id(force_rank.STF_FILE, "demote_request_body");
        prose_package pp_mail = prose.getPackage(body, getFirstName(self));
        utils.sendMail(sub, pp_mail, player_selected, "Enclave Records");
        return SCRIPT_CONTINUE;
    }
    public int msgForceRankRenamePlayer(obj_id self, dictionary params) throws InterruptedException
    {
        if (params.containsKey("success") && params.getBoolean("success") == true)
        {
            CustomerServiceLog("force_rank", "%TU has been successfully renamed in the Enclave data.", self, null);
        }
        else 
        {
            CustomerServiceLog("force_rank", "Failed to rename %TU in the Enclave data.", self, null);
        }
        return SCRIPT_CONTINUE;
    }
    public int msgFRSChalVoteStatusSelect(obj_id self, dictionary params) throws InterruptedException
    {
        String[] names = utils.getStringArrayScriptVar(self, SCRIPT_VAR_NAMES);
        obj_id terminal = utils.getObjIdScriptVar(self, SCRIPT_VAR_CHAL_TERMINAL);
        utils.removeScriptVar(self, SCRIPT_VAR_SUI_PID);
        utils.removeScriptVar(self, SCRIPT_VAR_CHAL_TERMINAL);
        utils.removeScriptVar(self, SCRIPT_VAR_NAMES);
        if (names == null)
        {
            LOG("force_rank", "player_force_rank.msgFRSChalVoteStatusSelect -- names is null.");
            return SCRIPT_CONTINUE;
        }
        if (!isIdValid(terminal))
        {
            LOG("force_rank", "player_force_rank.msgFRSChalVoteStatusSelect -- terminal is invalid.");
            return SCRIPT_CONTINUE;
        }
        String button = params.getString("buttonPressed");
        if (button.equals("Cancel") || button.equals("No"))
        {
            return SCRIPT_CONTINUE;
        }
        int row_selected = sui.getListboxSelectedRow(params);
        if (row_selected == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (row_selected >= names.length)
        {
            sendSystemMessage(self, new string_id(force_rank.STF_FILE, "invalid_selection"));
            return SCRIPT_CONTINUE;
        }
        String challenge_selected = names[row_selected];
        Vector dsrc = new Vector();
        dsrc.setSize(0);
        int status = force_rank.getChallengeVoteStatus(terminal, challenge_selected);
        String status_str = "Voting Complete";
        if (status == 1)
        {
            status_str = "Voting Open";
        }
        int end_time = force_rank.getChallengeVoteEndTime(terminal, challenge_selected);
        int time_remaining = end_time - getGameTime();
        String time_str = null;
        if (time_remaining > 0)
        {
            time_str = player_structure.assembleTimeRemaining(player_structure.convertSecondsTime(time_remaining));
        }
        else 
        {
            time_str = "closed.";
        }
        int[] votes = force_rank.getChallengeVotes(terminal, challenge_selected);
        if (votes == null)
        {
            LOG("force_rank", "player_force_rank.msgFRSChalVoteStatusSelect -- votes is null for challenge " + challenge_selected);
            return SCRIPT_CONTINUE;
        }
        dsrc.add("Current Stage: " + status_str);
        if (status == 1)
        {
            dsrc.add("Time Remaining: " + time_str);
        }
        dsrc.add("Votes For: " + votes[0]);
        dsrc.add("Votes Against: " + votes[1]);
        int pid = sui.listbox(self, self, "No-Confidence Challenge Status for " + challenge_selected, sui.OK_CANCEL, "@force_rank:challenge_vote_status_title", dsrc, "msgFRSVoteStatusClosed");
        utils.setScriptVar(self, SCRIPT_VAR_SUI_PID, pid);
        return SCRIPT_CONTINUE;
    }
    public int msgFRSChalVoteRecordSelect(obj_id self, dictionary params) throws InterruptedException
    {
        String[] names = utils.getStringArrayScriptVar(self, SCRIPT_VAR_NAMES);
        obj_id terminal = utils.getObjIdScriptVar(self, SCRIPT_VAR_CHAL_TERMINAL);
        utils.removeScriptVar(self, SCRIPT_VAR_SUI_PID);
        utils.removeScriptVar(self, SCRIPT_VAR_CHAL_TERMINAL);
        utils.removeScriptVar(self, SCRIPT_VAR_NAMES);
        if (names == null)
        {
            LOG("force_rank", "player_force_rank.msgFRSChalVoteRecordSelect -- names is null.");
            return SCRIPT_CONTINUE;
        }
        if (!isIdValid(terminal))
        {
            LOG("force_rank", "player_force_rank.msgFRSChalVoteRecordSelect -- terminal is invalid.");
            return SCRIPT_CONTINUE;
        }
        String button = params.getString("buttonPressed");
        if (button.equals("Cancel") || button.equals("No"))
        {
            return SCRIPT_CONTINUE;
        }
        int row_selected = sui.getListboxSelectedRow(params);
        if (row_selected == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (row_selected >= names.length)
        {
            sendSystemMessage(self, new string_id(force_rank.STF_FILE, "invalid_selection"));
            return SCRIPT_CONTINUE;
        }
        String challenge_selected = names[row_selected];
        Vector players_voted = new Vector();
        players_voted.setSize(0);
        if (hasObjVar(terminal, force_rank.BATCH_VAR_VOTERS + "." + challenge_selected))
        {
            players_voted = utils.getResizeableStringBatchObjVar(terminal, force_rank.BATCH_VAR_VOTERS + "." + challenge_selected);
        }
        int idx = players_voted.indexOf(getFirstName(self));
        if (idx != -1)
        {
            sendSystemMessage(self, new string_id(force_rank.STF_FILE, "already_voted"));
            return SCRIPT_CONTINUE;
        }
        String[] dsrc = 
        {
            "@force_rank:vote_for",
            "@force_rank:vote_against"
        };
        int pid = sui.listbox(self, self, "Do you vote for or against the removal of " + challenge_selected + "?", sui.OK_CANCEL, "@force_rank:challenge_vote_record_vote_title", dsrc, "msgFRSChalVoteRecordVote");
        utils.setScriptVar(self, SCRIPT_VAR_SUI_PID, pid);
        utils.setScriptVar(self, SCRIPT_VAR_CHAL_TERMINAL, terminal);
        utils.setScriptVar(self, SCRIPT_VAR_CHALLENGE_SELECTED, challenge_selected);
        return SCRIPT_CONTINUE;
    }
    public int msgFRSChalVoteRecordVote(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id terminal = utils.getObjIdScriptVar(self, SCRIPT_VAR_CHAL_TERMINAL);
        String challenge_selected = utils.getStringScriptVar(self, SCRIPT_VAR_CHALLENGE_SELECTED);
        utils.removeScriptVar(self, SCRIPT_VAR_SUI_PID);
        utils.removeScriptVar(self, SCRIPT_VAR_CHAL_TERMINAL);
        utils.removeScriptVar(self, SCRIPT_VAR_CHALLENGE_SELECTED);
        if (!isIdValid(terminal))
        {
            LOG("force_rank", "player_force_rank.msgFRSChalVoteRecordVote -- terminal is invalid.");
            return SCRIPT_CONTINUE;
        }
        if (challenge_selected == null)
        {
            LOG("force_rank", "player_force_rank.msgFRSChalVoteRecordVote -- challenge_selected is null.");
            return SCRIPT_CONTINUE;
        }
        String button = params.getString("buttonPressed");
        if (button.equals("Cancel") || button.equals("No"))
        {
            return SCRIPT_CONTINUE;
        }
        int row_selected = sui.getListboxSelectedRow(params);
        if (row_selected == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (row_selected > 1)
        {
            sendSystemMessage(self, new string_id(force_rank.STF_FILE, "invalid_selection"));
            return SCRIPT_CONTINUE;
        }
        if (force_rank.isChallengeVoteTimeExpired(terminal, challenge_selected))
        {
            sendSystemMessage(self, new string_id(force_rank.STF_FILE, "vote_time_expired"));
            return SCRIPT_CONTINUE;
        }
        int[] votes = force_rank.getChallengeVotes(terminal, challenge_selected);
        if (votes == null)
        {
            sendSystemMessage(self, new string_id(force_rank.STF_FILE, "vote_failed"));
            return SCRIPT_CONTINUE;
        }
        Vector players_voted = new Vector();
        players_voted.setSize(0);
        if (hasObjVar(terminal, force_rank.BATCH_VAR_VOTERS + "." + challenge_selected))
        {
            players_voted = utils.getResizeableStringBatchObjVar(terminal, force_rank.BATCH_VAR_VOTERS + "." + challenge_selected);
        }
        int idx = players_voted.indexOf(getFirstName(self));
        if (idx != -1)
        {
            sendSystemMessage(self, new string_id(force_rank.STF_FILE, "already_voted"));
            return SCRIPT_CONTINUE;
        }
        players_voted.add(getFirstName(self));
        String[] vote_list = new String[players_voted.size()];
        players_voted.toArray(vote_list);
        utils.setBatchObjVar(terminal, force_rank.BATCH_VAR_VOTERS + "." + challenge_selected, vote_list);
        votes[row_selected] += 1;
        String obj_var_name = force_rank.VAR_CHAL_VOTING_BASE + "." + challenge_selected + ".votes";
        setObjVar(terminal, obj_var_name, votes);
        sendSystemMessage(self, new string_id(force_rank.STF_FILE, "challenge_vote_success"));
        return SCRIPT_CONTINUE;
    }
    public int msgFRSVoteChalSelectRank(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id terminal = utils.getObjIdScriptVar(self, SCRIPT_VAR_CHAL_TERMINAL);
        utils.removeScriptVar(self, SCRIPT_VAR_CHAL_TERMINAL);
        if (!isIdValid(terminal))
        {
            LOG("force_rank", "player_force_rank.msgFRSVoteChalSelectRank -- terminal is invalid.");
            return SCRIPT_CONTINUE;
        }
        obj_id enclave = getTopMostContainer(terminal);
        if (!isIdValid(enclave))
        {
            LOG("force_rank", "terminal_frs_voting.msgFRSVoteChalSelectRank -- enclave is invalid");
            return SCRIPT_CONTINUE;
        }
        if (!hasScript(enclave, force_rank.SCRIPT_ENCLAVE_CONTROLLER))
        {
            LOG("force_rank", "terminal_frs_voting.msgFRSVoteChalSelectRank -- " + enclave + " is not an enclave building.");
            return SCRIPT_CONTINUE;
        }
        int rank_selected = params.getInt("challenge_rank");
        if (rank_selected == -1)
        {
            return SCRIPT_CONTINUE;
        }
        LOG("force_rank", "enclave ->" + enclave + "  rank_selected ->" + rank_selected);
        String[] rank_list = force_rank.getPlayersInForceRank(enclave, rank_selected);
        if (rank_list == null)
        {
            LOG("force_rank", "terminal_frs_voting.msgFRSVoteChalSelectRank -- rank_list is null.");
            return SCRIPT_CONTINUE;
        }
        int pid = sui.listbox(self, self, "@force_rank:challenge_vote_select_name", sui.OK_CANCEL, "@force_rank:challenge_vote_select_name_title", rank_list, "msgFRSChalSelectConfirm");
        utils.setScriptVar(self, SCRIPT_VAR_SUI_PID, pid);
        utils.setScriptVar(self, SCRIPT_VAR_CHAL_TERMINAL, terminal);
        utils.setScriptVar(self, SCRIPT_VAR_RANK_LIST, rank_list);
        return SCRIPT_CONTINUE;
    }
    public int msgFRSChalSelectConfirm(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id terminal = utils.getObjIdScriptVar(self, SCRIPT_VAR_CHAL_TERMINAL);
        String[] rank_list = utils.getStringArrayScriptVar(self, SCRIPT_VAR_RANK_LIST);
        utils.removeScriptVar(self, SCRIPT_VAR_SUI_PID);
        utils.removeScriptVar(self, SCRIPT_VAR_CHAL_TERMINAL);
        utils.removeScriptVar(self, SCRIPT_VAR_RANK_LIST);
        if (!isIdValid(terminal))
        {
            LOG("force_rank", "player_force_rank.msgFRSChalSelectConfirm -- terminal is invalid.");
            return SCRIPT_CONTINUE;
        }
        if (rank_list == null)
        {
            LOG("force_rank", "player_force_rank.msgFRSChalSelectConfirm -- rank_list is null.");
            return SCRIPT_CONTINUE;
        }
        obj_id enclave = getTopMostContainer(terminal);
        if (!isIdValid(enclave))
        {
            LOG("force_rank", "terminal_frs_voting.msgFRSChalSelectConfirm -- enclave is invalid");
            return SCRIPT_CONTINUE;
        }
        if (!hasScript(enclave, force_rank.SCRIPT_ENCLAVE_CONTROLLER))
        {
            LOG("force_rank", "terminal_frs_voting.msgFRSChalSelectConfirm -- " + enclave + " is not an enclave building.");
            return SCRIPT_CONTINUE;
        }
        String button = params.getString("buttonPressed");
        if (button.equals("Cancel") || button.equals("No"))
        {
            return SCRIPT_CONTINUE;
        }
        int row_selected = sui.getListboxSelectedRow(params);
        if (row_selected == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (row_selected >= rank_list.length)
        {
            sendSystemMessage(self, new string_id(force_rank.STF_FILE, "invalid_selection"));
            return SCRIPT_CONTINUE;
        }
        String challenge = rank_list[row_selected];
        if (challenge == null)
        {
            sendSystemMessage(self, new string_id(force_rank.STF_FILE, "invalid_selection"));
            return SCRIPT_CONTINUE;
        }
        if (force_rank.getNumChallengeVoteList(terminal) >= force_rank.MAX_CHAL_LIST_SIZE)
        {
            sendSystemMessage(self, new string_id(force_rank.STF_FILE, "vote_challenge_too_many"));
            return SCRIPT_CONTINUE;
        }
        int player_rank = force_rank.getForceRank(self);
        int challenge_rank = force_rank.getForceRank(enclave, challenge);
        if (challenge_rank - player_rank != 1)
        {
            sendSystemMessage(self, new string_id(force_rank.STF_FILE, "vote_challenge_not_correct_rank"));
            return SCRIPT_CONTINUE;
        }
        String objvar_name = force_rank.VAR_CHAL_VOTING_BASE + "." + challenge;
        if (hasObjVar(terminal, objvar_name))
        {
            prose_package pp = prose.getPackage(new string_id(force_rank.STF_FILE, "vote_challenge_already_challenged"), challenge);
            sendSystemMessageProse(self, pp);
            return SCRIPT_CONTINUE;
        }
        int last_demote = 0;
        if (hasObjVar(self, force_rank.VAR_VOTE_CHALLENGE))
        {
            last_demote = getIntObjVar(self, force_rank.VAR_VOTE_CHALLENGE);
        }
        if (last_demote > getGameTime())
        {
            int time_remaining = last_demote - getGameTime();
            String time_str = "1 second.";
            if (time_remaining > 0)
            {
                time_str = player_structure.assembleTimeRemaining(player_structure.convertSecondsTime(time_remaining));
            }
            prose_package pp = prose.getPackage(new string_id(force_rank.STF_FILE, "challenge_too_soon"), time_str);
            sendSystemMessageProse(self, pp);
            return SCRIPT_CONTINUE;
        }
        if (force_rank.spendForceRankXP(self, force_rank.VOTE_CHALLENGE_COST * challenge_rank) == -1)
        {
            sendSystemMessage(self, new string_id(force_rank.STF_FILE, "vote_challenge_not_enough_xp"));
            return SCRIPT_CONTINUE;
        }
        setObjVar(terminal, objvar_name + ".status", 1);
        setObjVar(terminal, objvar_name + ".time", getGameTime() + force_rank.getVoteInterval());
        setObjVar(terminal, objvar_name + ".rank", challenge_rank);
        int[] votes = 
        {
            0,
            0
        };
        setObjVar(terminal, objvar_name + ".votes", votes);
        setObjVar(self, force_rank.VAR_VOTE_CHALLENGE, getGameTime() + force_rank.VOTE_CHALLENGE_DURATION);
        prose_package pp = prose.getPackage(new string_id(force_rank.STF_FILE, "vote_challenge_initiated"), challenge);
        sendSystemMessageProse(self, pp);
        string_id sub = new string_id(force_rank.STF_FILE, "challenge_vote_begun_sub");
        string_id body = new string_id(force_rank.STF_FILE, "challenge_vote_begun_body");
        pp = prose.getPackage(body, getFirstName(self), challenge);
        force_rank.sendChallengeVoterMail(enclave, challenge_rank, sub, pp);
        return SCRIPT_CONTINUE;
    }
    public int handleForceRank(obj_id self, dictionary params) throws InterruptedException
    {
        int row_selected = sui.getListboxSelectedRow(params) + 1;
        int button_pressed = sui.getIntButtonPressed(params);
        int council = force_rank.getCouncilAffiliation(self);
        if (button_pressed == 0)
        {
            utils.setScriptVar(self, "row_selected", row_selected);
            force_rank.getPlayersInForceRank(self, row_selected, council, "handlePlayersInForceRankSelected");
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int handlePlayersInForceRankSelected(obj_id self, dictionary params) throws InterruptedException
    {
        int row_selected = utils.getIntScriptVar(self, "row_selected");
        String force_rank_list[] = params.getStringArray("rank_list");
        String rank_str = localize(new string_id(force_rank.STF_FILE, "rank" + row_selected));
        if (utils.hasScriptVar(self, "force_rank.ShowCouncilRank2"))
        {
            int oldpid = utils.getIntScriptVar(self, "force_rank.ShowCouncilRank2");
            forceCloseSUIPage(oldpid);
            utils.removeScriptVar(self, "force_rank.ShowCouncilRank2");
        }
        int pid = sui.listbox(self, "Members in " + rank_str, rank_str, force_rank_list);
        utils.removeScriptVar(self, "row_selected");
        if (pid > -1)
        {
            utils.setScriptVar(self, "force_rank.ShowCouncilRank2", pid);
        }
        return SCRIPT_CONTINUE;
    }
    public int enclaveIdResponse(obj_id self, dictionary params) throws InterruptedException
    {
        if (!params.containsKey("enclave"))
        {
            LOG("force_rank", "player_force_rank::enclaveIdResponse: -> Missing @enclave parm.  bailing.");
            return SCRIPT_CONTINUE;
        }
        obj_id enclave = params.getObjId("enclave");
        if (enclave == null)
        {
            LOG("force_rank", "player_force_rank::enclaveIdResponse: -> @enclave parm came back from cluster wide data store as null.  bailing.");
        }
        setObjVar(self, force_rank.VAR_MY_ENCLAVE_ID, enclave);
        force_rank.requestPEFs(self);
        return SCRIPT_CONTINUE;
    }
    public int msgApplyExperienceDebt(obj_id self, dictionary params) throws InterruptedException
    {
        int xp_debt = params.getInt("xp_debt");
        if (xp_debt > 0)
        {
            force_rank.adjustForceRankXP(self, -xp_debt);
        }
        return SCRIPT_CONTINUE;
    }
    public int msgValidateFRSPlayerData(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            LOG("force_rank", "enclave_controller.msgValidateFRSPlayerData(player) -- params is null");
            return SCRIPT_CONTINUE;
        }
        int player_rank = params.getInt("player_rank");
        if (player_rank < 0 || player_rank > 11)
        {
            LOG("force_rank", "enclave_controller.msgValidateFRSPlayerData(player) -- player_rank is not a legal value: " + player_rank);
            return SCRIPT_CONTINUE;
        }
        if (player_rank != force_rank.getForceRank(self))
        {
            CustomerServiceLog("force_rank", "%TU has a conflicting rank. Player data is rank " + force_rank.getForceRank(self) + " and enclave data is rank " + player_rank + ". Resetting to enclave rank.", self, null);
            setObjVar(self, force_rank.VAR_RANK, player_rank);
        }
        force_rank.resyncForceRankSkills(self);
        String frsConfig = getConfigSetting("GameServer", "enableFRS");
        if ((frsConfig == null || frsConfig.length() < 1) && force_rank.getForceRank(self) == 0)
        {
            force_rank.removeFromForceRankSystem(self, false);
            return SCRIPT_CONTINUE;
        }
        int council = force_rank.getCouncilAffiliation(self);
        if (council == force_rank.LIGHT_COUNCIL)
        {
            pvpSetAlignedFaction(self, force_rank.getFactionId("Rebel"));
        }
        else 
        {
            pvpSetAlignedFaction(self, force_rank.getFactionId("Imperial"));
        }
        pvpMakeDeclared(self);
        return SCRIPT_CONTINUE;
    }
    public int cmdShowCouncilRank(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
}
