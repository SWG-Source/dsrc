package script.systems.gcw;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.force_rank;
import script.library.sui;
import script.library.utils;

public class terminal_frs_chal_vote extends script.base_script
{
    public terminal_frs_chal_vote()
    {
    }
    public static final String SCRIPT_VAR_SUI_PID = "force_rank.vote_sui";
    public static final String SCRIPT_VAR_CHAL_TERMINAL = "force_rank.challenge_vote_terminal";
    public static final String SCRIPT_VAR_NAMES = "force_rank.challenge_vote_name";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        LOG("force_rank", "terminal_frs_chal_vote.OnInitialize -- " + self);
        obj_id enclave = getTopMostContainer(self);
        if (!isIdValid(enclave))
        {
            LOG("force_rank", "terminal_frs_chal_vote -- voting terminal " + self + " is not in an enclave.");
            destroyObject(self);
            return SCRIPT_CONTINUE;
        }
        if (!hasScript(enclave, force_rank.SCRIPT_ENCLAVE_CONTROLLER))
        {
            LOG("force_rank", "terminal_frs_chal_vote -- voting terminal " + self + " is not in an enclave.");
            destroyObject(self);
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(enclave, force_rank.SCRIPT_VAR_CHAL_VOTE_TERMINAL, self);
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
            LOG("force_rank", "terminal_frs_chal_vote.OnObjectMenuRequest -- enclave is invalid");
            return SCRIPT_CONTINUE;
        }
        if (!hasScript(enclave, force_rank.SCRIPT_ENCLAVE_CONTROLLER))
        {
            LOG("force_rank", "terminal_frs_chal_vote.OnObjectMenuRequest -- " + enclave + " is not an enclave building.");
            return SCRIPT_CONTINUE;
        }
        int rank = force_rank.getForceRank(player);
        if (rank == -1)
        {
            sendSystemMessage(player, new string_id(force_rank.STF_FILE, "insufficient_rank_vote"));
            return SCRIPT_CONTINUE;
        }
        int council = force_rank.getCouncilAffiliation(player);
        if (council == -1)
        {
            LOG("force_rank", "terminal_frs_chal_vote.OnObjectMenuRequest -- " + player + " has an invalid council value.");
            return SCRIPT_CONTINUE;
        }
        int mnu = mi.addRootMenu(menu_info_types.SERVER_MENU1, new string_id(force_rank.STF_FILE, "challenge_vote_status"));
        if (rank > 0)
        {
            mi.addSubMenu(mnu, menu_info_types.SERVER_MENU2, new string_id(force_rank.STF_FILE, "record_challenge_vote"));
            mi.addSubMenu(mnu, menu_info_types.SERVER_MENU3, new string_id(force_rank.STF_FILE, "issue_challenge_vote"));
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
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
        if (utils.hasScriptVar(player, SCRIPT_VAR_SUI_PID))
        {
            forceCloseSUIPage(utils.getIntScriptVar(player, SCRIPT_VAR_SUI_PID));
        }
        if (item == menu_info_types.SERVER_MENU1)
        {
            String[] names = force_rank.getChallengeVoteList(self);
            if (names != null)
            {
                int pid = sui.listbox(player, player, "@force_rank:challenge_vote_status_select", sui.OK_CANCEL, "@force_rank:challenge_vote_status_title", names, "msgFRSChalVoteStatusSelect");
                utils.setScriptVar(player, SCRIPT_VAR_SUI_PID, pid);
                utils.setScriptVar(player, SCRIPT_VAR_CHAL_TERMINAL, self);
                utils.setScriptVar(player, SCRIPT_VAR_NAMES, names);
            }
            else 
            {
                sendSystemMessage(player, new string_id("force_rank", "no_challenge_votes"));
            }
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.SERVER_MENU2)
        {
            int player_rank = force_rank.getForceRank(player);
            if (player_rank < 1)
            {
                sendSystemMessage(player, new string_id(force_rank.STF_FILE, "insufficient_rank_vote"));
                return SCRIPT_CONTINUE;
            }
            String[] names = force_rank.getChallengeVoteList(self);
            if (names != null)
            {
                Vector dsrc = new Vector();
                dsrc.setSize(0);
                for (int i = 0; i < names.length; i++)
                {
                    int rank = force_rank.getChallengeVoteRank(self, names[i]);
                    if (rank > 0)
                    {
                        if (force_rank.getChallengeVoteWeight(player_rank, rank) > 0)
                        {
                            dsrc.add(names[i]);
                        }
                    }
                }
                if (dsrc.size() > 0)
                {
                    int pid = sui.listbox(player, player, "@force_rank:challenge_vote_record_vote", sui.OK_CANCEL, "@force_rank:challenge_vote_record_vote_title", dsrc, "msgFRSChalVoteRecordSelect");
                    utils.setScriptVar(player, SCRIPT_VAR_SUI_PID, pid);
                    utils.setScriptVar(player, SCRIPT_VAR_CHAL_TERMINAL, self);
                    utils.setScriptVar(player, SCRIPT_VAR_NAMES, dsrc);
                }
            }
            else 
            {
                sendSystemMessage(player, new string_id("force_rank", "no_challenge_votes"));
            }
        }
        if (item == menu_info_types.SERVER_MENU3)
        {
            int rank = force_rank.getForceRank(player);
            if (rank < 1)
            {
                sendSystemMessage(player, new string_id("force_rank", "no_challenge_vote_low_rank"));
                return SCRIPT_CONTINUE;
            }
            else if (rank == 11)
            {
                sendSystemMessage(player, new string_id("force_rank", "no_challenge_vote_leader"));
                return SCRIPT_CONTINUE;
            }
            utils.setScriptVar(player, SCRIPT_VAR_CHAL_TERMINAL, self);
            dictionary d = new dictionary();
            d.put("challenge_rank", rank + 1);
            messageTo(player, "msgFRSVoteChalSelectRank", d, 0.0f, false);
        }
        return SCRIPT_CONTINUE;
    }
}
