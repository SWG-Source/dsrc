package script.systems.gcw;

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
import script.library.arena;
import script.library.player_structure;

public class terminal_frs_challenge extends script.base_script
{
    public terminal_frs_challenge()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        LOG("force_rank", "terminal_frs_challenge::OnInitialize: -> Initializing.");
        arena.initializeArena(self);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (isDead(player) || isIncapacitated(player))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id enclave = getTopMostContainer(self);
        if (!isIdValid(enclave))
        {
            LOG("force_rank", "terminal_frs_challenge.OnObjectMenuRequest -- enclave is invalid");
            return SCRIPT_CONTINUE;
        }
        if (!hasScript(enclave, force_rank.SCRIPT_ENCLAVE_CONTROLLER))
        {
            LOG("force_rank", "terminal_frs_challenge.OnObjectMenuRequest -- " + enclave + " is not an enclave building.");
            return SCRIPT_CONTINUE;
        }
        int rank = force_rank.getForceRank(enclave, getFirstName(player));
        int council = force_rank.getCouncilAffiliation(player);
        if (council == -1)
        {
            LOG("force_rank", "terminal_frs_voting.OnObjectMenuRequest -- " + player + " has an invalid council value.");
            return SCRIPT_CONTINUE;
        }
        int mnuViewRankScores = mi.addRootMenu(menu_info_types.SERVER_MENU1, new string_id("pvp_rating", "ch_terminal_view_scores"));
        int mnuViewChallenges = -1;
        int mnuAcceptChallenge = -1;
        int mnuIssueChallenge = -1;
        int mnuViewArenaStatus = mi.addRootMenu(menu_info_types.SERVER_MENU5, new string_id("pvp_rating", "ch_terminal_arena_status"));
        if (hasObjVar(self, arena.VAR_CHALLENGERS))
        {
            mnuViewChallenges = mi.addRootMenu(menu_info_types.SERVER_MENU2, new string_id("pvp_rating", "ch_terminal_view_challenges"));
        }
        if (rank > 0 && rank < 11)
        {
            if (arena.isArenaOpenForChallenges(self) && force_rank.isPlayerEligibleForPromotion(player, rank + 1))
            {
                if (arena.canPlayerIssueChallenge(player, self))
                {
                    mnuIssueChallenge = mi.addRootMenu(menu_info_types.SERVER_MENU3, new string_id("pvp_rating", "ch_terminal_issue_challenge"));
                }
            }
        }
        if (rank > 1)
        {
            if ((arena.getChallengerIdsForRank(self, rank)).size() > 0 && !utils.hasScriptVar(player, arena.VAR_I_AM_DUELING))
            {
                mnuAcceptChallenge = mi.addRootMenu(menu_info_types.SERVER_MENU4, new string_id("pvp_rating", "ch_terminal_accept_challenge"));
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        sendDirtyObjectMenuNotification(self);
        if (isDead(player) || isIncapacitated(player))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id enclave = getTopMostContainer(self);
        if (!isIdValid(enclave))
        {
            LOG("force_rank", "terminal_frs_voting.OnObjectMenuSelect -- enclave is invalid");
            return SCRIPT_CONTINUE;
        }
        if (!hasScript(enclave, force_rank.SCRIPT_ENCLAVE_CONTROLLER))
        {
            LOG("force_rank", "terminal_frs_voting.OnObjectMenuRequest -- " + enclave + " is not an enclave building.");
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(player, arena.SCRIPT_VAR_SUI_CH_PID))
        {
            forceCloseSUIPage(utils.getIntScriptVar(player, arena.SCRIPT_VAR_SUI_CH_PID));
        }
        int pid = -1;
        int rank = force_rank.getForceRank(enclave, getFirstName(player));
        if (getDistance(player, self) > 15)
        {
            sendSystemMessage(player, new string_id("pvp_rating", "ch_terminal_too_far"));
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.SERVER_MENU1)
        {
            String[] scores = arena.getChallengeScoreStrings(self);
            pid = sui.listbox(player, player, "@pvp_rating:ch_rank_scores", sui.OK_ONLY, "@pvp_rating:ch_terminal_view_scores", scores, "msgFRSCHallengeViewScores");
            utils.setScriptVar(player, arena.SCRIPT_VAR_SUI_CH_PID, pid);
            utils.setScriptVar(player, arena.SCRIPT_VAR_CH_TERMINAL, self);
        }
        else if (item == menu_info_types.SERVER_MENU2)
        {
            if (!hasObjVar(self, arena.VAR_CHALLENGERS))
            {
                sendSystemMessage(player, new string_id("pvp_rating", "ch_no_challenges_to_view"));
                return SCRIPT_CONTINUE;
            }
            Vector challengedRanks = arena.getRanksWithActiveChallenges(self);
            String[] stringChallenges = arena.getRanksWithActiveChallengesString(self);
            pid = sui.listbox(player, player, "@pvp_rating:ch_select_active_challenges", sui.OK_ONLY, "@pvp_rating:ch_terminal_view_challenges", stringChallenges, "msgFRSChallengeViewChallenges");
            utils.setScriptVar(player, arena.SCRIPT_VAR_SUI_CH_PID, pid);
            utils.setScriptVar(player, arena.SCRIPT_VAR_CH_TERMINAL, self);
            utils.setScriptVar(player, arena.SCRIPT_VAR_CH_VIEW_CHALLENGES, challengedRanks);
        }
        else if (item == menu_info_types.SERVER_MENU3)
        {
            if (rank < 1 || rank > force_rank.COUNCIL_RANK_NUMBER)
            {
                prose_package rankBoundsPP = prose.getPackage(new string_id("pvp_rating", "ch_terminal_cant_challenge_rank_bounds"), rank);
                sendSystemMessageProse(player, rankBoundsPP);
                return SCRIPT_CONTINUE;
            }
            if (!arena.isArenaOpenForChallenges(self))
            {
                sendSystemMessage(player, new string_id("pvp_rating", "ch_terminal_arena_closed"));
                return SCRIPT_CONTINUE;
            }
            if (!force_rank.isPlayerEligibleForPromotion(player, rank + 1))
            {
                sendSystemMessage(player, new string_id("pvp_rating", "ch_terminal_notenoughexp"));
                return SCRIPT_CONTINUE;
            }
            int avail_slots = force_rank.getAvailableRankSlots(enclave, rank + 1);
            if (avail_slots >= 3)
            {
                if (hasObjVar(player, "force_rank.qa.overrideArenaOpenSlots"))
                {
                    sendSystemMessageTestingOnly(player, "QA open slot Vote-Force override.");
                }
                else 
                {
                    sendSystemMessage(player, new string_id("pvp_rating", "ch_terminal_no_need_challenge"));
                    return SCRIPT_CONTINUE;
                }
            }
            if (arena.canPlayerIssueChallenge(player, self))
            {
                pid = sui.msgbox(player, player, "@pvp_rating:ch_terminal_verify_challenge", sui.OK_CANCEL, "msgFRSChallengeConfirmIssueChallenge");
                utils.setScriptVar(player, arena.SCRIPT_VAR_SUI_CH_PID, pid);
                utils.setScriptVar(player, arena.SCRIPT_VAR_CH_TERMINAL, self);
            }
        }
        else if (item == menu_info_types.SERVER_MENU4)
        {
            if (rank > 1 && !utils.hasScriptVar(player, arena.VAR_I_AM_DUELING))
            {
                if ((arena.getChallengerIdsForRank(self, rank)).size() > 0)
                {
                    pid = sui.msgbox(player, player, "@pvp_rating:ch_terminal_verify_accept_challenge", sui.OK_CANCEL, "msgFRSChallengeConfirmAcceptChallenge");
                    utils.setScriptVar(player, arena.SCRIPT_VAR_SUI_CH_PID, pid);
                    utils.setScriptVar(player, arena.SCRIPT_VAR_CH_TERMINAL, self);
                }
                else 
                {
                    sendSystemMessage(player, new string_id("pvp_rating", "ch_terminal_no_challenges_for_rank"));
                }
            }
            else 
            {
                sendSystemMessage(player, new string_id("pvp_rating", "ch_terminal_no_challenges_for_rank"));
            }
        }
        else if (item == menu_info_types.SERVER_MENU5)
        {
            if (!hasObjVar(self, arena.VAR_ARENA_LAST_OPEN_TIME))
            {
                return SCRIPT_CONTINUE;
            }
            int lastOpen = getIntObjVar(self, arena.VAR_ARENA_LAST_OPEN_TIME);
            int now = getGameTime();
            int secsLeft = 0;
            string_id timeLeftId = null;
            if (arena.isArenaOpenForChallenges(self))
            {
                secsLeft = lastOpen + arena.getArenaOpenLength() - now;
                if (secsLeft < 1)
                {
                    timeLeftId = new string_id("pvp_rating", "ch_terminal_arena_close_imminent");
                }
                else 
                {
                    timeLeftId = new string_id("pvp_rating", "ch_terminal_arena_close_in");
                }
            }
            else 
            {
                secsLeft = lastOpen + arena.getArenaOpenLength() + arena.getArenaOpenEvery() - now;
                if (secsLeft < 1)
                {
                    timeLeftId = new string_id("pvp_rating", "ch_terminal_arena_open_imminent");
                }
                else 
                {
                    timeLeftId = new string_id("pvp_rating", "ch_terminal_arena_open_in");
                }
            }
            if (secsLeft < 1)
            {
                sendSystemMessage(player, timeLeftId);
            }
            else 
            {
                String timeLeftString = player_structure.assembleTimeRemaining(player_structure.convertSecondsTime(secsLeft));
                prose_package pp = prose.getPackage(timeLeftId, timeLeftString);
                sendSystemMessageProse(player, pp);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
