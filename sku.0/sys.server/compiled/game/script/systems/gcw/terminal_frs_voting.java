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

public class terminal_frs_voting extends script.base_script
{
    public terminal_frs_voting()
    {
    }
    public static final String SCRIPT_VAR_SUI_PID = "force_rank.vote_sui";
    public static final String SCRIPT_VAR_TERMINAL = "force_rank.vote_terminal";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        LOG("force_rank", "terminal_frs_voting.OnInitialize -- " + self);
        obj_id enclave = getTopMostContainer(self);
        if (!isIdValid(enclave))
        {
            LOG("force_rank", "terminal_frs_voting.OnInitialize -- voting terminal " + self + " is not in an enclave.");
            destroyObject(self);
            return SCRIPT_CONTINUE;
        }
        if (!hasScript(enclave, force_rank.SCRIPT_ENCLAVE_CONTROLLER))
        {
            LOG("force_rank", "terminal_frs_voting.OnInitialize -- voting terminal " + self + " is not in an enclave.");
            destroyObject(self);
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(enclave, force_rank.SCRIPT_VAR_VOTE_TERMINAL, self);
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
            LOG("force_rank", "terminal_frs_voting.OnObjectMenuRequest -- enclave is invalid");
            return SCRIPT_CONTINUE;
        }
        if (!hasScript(enclave, force_rank.SCRIPT_ENCLAVE_CONTROLLER))
        {
            LOG("force_rank", "terminal_frs_voting.OnObjectMenuRequest -- " + enclave + " is not an enclave building.");
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
            LOG("force_rank", "terminal_frs_voting.OnObjectMenuRequest -- " + player + " has an invalid council value.");
            return SCRIPT_CONTINUE;
        }
        int mnu = mi.addRootMenu(menu_info_types.SERVER_MENU1, new string_id(force_rank.STF_FILE, "vote_status"));
        mi.addSubMenu(mnu, menu_info_types.SERVER_MENU2, new string_id(force_rank.STF_FILE, "record_vote"));
        mi.addSubMenu(mnu, menu_info_types.SERVER_MENU3, new string_id(force_rank.STF_FILE, "accept_promotion"));
        mi.addSubMenu(mnu, menu_info_types.SERVER_MENU4, new string_id(force_rank.STF_FILE, "petition"));
        if (rank > 7 && council == force_rank.LIGHT_COUNCIL)
        {
            mi.addRootMenu(menu_info_types.SERVER_MENU5, new string_id(force_rank.STF_FILE, "demote_member"));
        }
        mi.addRootMenu(menu_info_types.SERVER_MENU6, new string_id(force_rank.STF_FILE, "recover_jedi_items"));
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
        String[] dsrc = new String[11];
        for (int i = 0; i < dsrc.length; i++)
        {
            dsrc[i] = "@force_rank:rank" + (i + 1);
        }
        if (item == menu_info_types.SERVER_MENU1)
        {
            int pid = sui.listbox(player, player, "@force_rank:vote_status_select", sui.OK_CANCEL, "@force_rank:rank_selection", dsrc, "msgFRSVoteStatusSelect");
            utils.setScriptVar(player, SCRIPT_VAR_SUI_PID, pid);
            utils.setScriptVar(player, SCRIPT_VAR_TERMINAL, self);
        }
        if (item == menu_info_types.SERVER_MENU2)
        {
            int player_rank = force_rank.getForceRank(player);
            for (int i = 0; i < dsrc.length; i++)
            {
                if (force_rank.getVoteStatus(enclave, i + 1) == 2)
                {
                    if (force_rank.getVoteWeight(player_rank, i + 1) > 0)
                    {
                        Vector players_voted = utils.getResizeableStringBatchObjVar(self, force_rank.BATCH_VAR_VOTERS + (i + 1));
                        if (players_voted != null)
                        {
                            if (players_voted.indexOf(getFirstName(player)) == -1)
                            {
                                dsrc[i] += " *";
                            }
                        }
                        else 
                        {
                            dsrc[i] += " *";
                        }
                    }
                }
            }
            int pid = sui.listbox(player, player, "@force_rank:vote_record_select", sui.OK_CANCEL, "@force_rank:rank_selection", dsrc, "msgFRSVoteRecordSelect");
            utils.setScriptVar(player, SCRIPT_VAR_SUI_PID, pid);
            utils.setScriptVar(player, SCRIPT_VAR_TERMINAL, self);
        }
        if (item == menu_info_types.SERVER_MENU3)
        {
            int pid = sui.listbox(player, player, "@force_rank:vote_promotion_select", sui.OK_CANCEL, "@force_rank:rank_selection", dsrc, "msgFRSVotePromotionSelect");
            utils.setScriptVar(player, SCRIPT_VAR_SUI_PID, pid);
            utils.setScriptVar(player, SCRIPT_VAR_TERMINAL, self);
        }
        if (item == menu_info_types.SERVER_MENU4)
        {
            int pid = sui.listbox(player, player, "@force_rank:vote_petition_select", sui.OK_CANCEL, "@force_rank:rank_selection", dsrc, "msgFRSVotePetitionSelect");
            utils.setScriptVar(player, SCRIPT_VAR_SUI_PID, pid);
            utils.setScriptVar(player, SCRIPT_VAR_TERMINAL, self);
        }
        if (item == menu_info_types.SERVER_MENU5)
        {
            int pid = sui.listbox(player, player, "@force_rank:demote_select_rank", sui.OK_CANCEL, "@force_rank:rank_selection", dsrc, "msgFRSDemoteSelect");
            utils.setScriptVar(player, SCRIPT_VAR_SUI_PID, pid);
        }
        if (item == menu_info_types.SERVER_MENU6)
        {
            if (force_rank.grantRankItems(player, true))
            {
                sendSystemMessage(player, new string_id(force_rank.STF_FILE, "items_recovered"));
            }
        }
        return SCRIPT_CONTINUE;
    }
}
