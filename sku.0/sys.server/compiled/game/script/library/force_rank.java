package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.skill;
import script.library.xp;
import script.library.utils;
import script.library.prose;
import script.library.pvp;
import script.library.trace;
import script.library.player_structure;
import java.lang.Math;

public class force_rank extends script.base_script
{
    public force_rank()
    {
    }
    public static final String FRS_DATATABLE = "datatables/pvp/force_rank.iff";
    public static final String FRS_DARK_DATATABLE = "datatables/pvp/force_rank_dark.iff";
    public static final String JEDI_TERMINAL_DATATABLE = "datatables/pvp/jedi_enclave_terminals.iff";
    public static final String ENCLAVE_REWARDS_DATATABLE = "datatables/pvp/enclave_rewards.iff";
    public static final String FRS_XP = "force_rank_xp";
    public static final String STF_FILE = "force_rank";
    public static final String CLUSTER_DATA_NAME = "jedi_frs";
    public static final int PVP_BOARD_SIZE = 25;
    public static final int UPDATE_INTERVAL = 900;
    public static final int BACK_UP_INTERVAL = 172800;
    public static final int MAX_RANK_SLOTS = 20;
    public static final int PETITION_INTERVAL = 86400;
    public static final int VOTING_INTERVAL = 86400;
    public static final int ACCEPTANCE_INTERVAL = 86400;
    public static final int MAX_PETITIONERS = 11;
    public static final int MISSED_VOTE_PENALTY = 100;
    public static final int REQUEST_DEMOTION_DURATION = 604800;
    public static final int REQUEST_DEMOTION_COST = 2500;
    public static final int MAINTENANCE_PULSE = 1200;
    public static final int BASE_XP_MAINTENANCE = 100;
    public static final int XP_MAINTENANCE_INTERVAL = 86400;
    public static final int VOTE_CHALLENGE_COST = 2000;
    public static final int VOTE_CHALLENGE_DURATION = 604800;
    public static final int MAX_CHAL_LIST_SIZE = 20;
    public static final int DEBUFF_DURATION = 120;
    public static final String EMPTY_SLOT = "Open Seat";
    public static final String SCRIPT_DATA_HANDLER = "systems.gcw.enclave_data_handler";
    public static final String SCRIPT_FRS_PLAYER = "systems.gcw.player_force_rank";
    public static final String SCRIPT_ENCLAVE_CONTROLLER = "systems.gcw.enclave_controller";
    public static final String JEDI_ROOM_PERMISSIONS_TABLE = "datatables/pvp/jedi_enclave_room_permissions.iff";
    public static final String DATA_COLUMN_COMMUNITY_CELLS = "_community_cells";
    public static final boolean DEBUGGING = false;
    public static final int TEST_COUNCIL = 0;
    public static final int DARK_COUNCIL = 1;
    public static final int LIGHT_COUNCIL = 2;
    public static final String[] COUNCIL_NAMES = 
    {
        "Test Enclave",
        "Dark Enclave",
        "Light Enclave"
    };
    public static final String VAR_RATING = "force_rank.rating";
    public static final String VAR_RANK = "force_rank.rank";
    public static final String VAR_COUNCIL = "force_rank.council";
    public static final String VAR_REQUEST_DEMOTE = "force_rank.request_demote";
    public static final String VAR_VOTE_CHALLENGE = "force_rank.vote_challenge";
    public static final String SCRIPT_VAR_DATA_REQUEST = "force_rank.data_request";
    public static final String SCRIPT_VAR_PARAMS_REQUEST = "force_rank.rank_request";
    public static final String SCRIPT_VAR_HANDLER_REQUEST = "force_rank.handler_request";
    public static final String SCRIPT_VAR_SKILL_RESYNC = "force_rank.script_resync";
    public static final String SCRIPT_VAR_REVALIDATE = "force_rank.revalidate";
    public static final String BATCH_VAR_VOTERS = "force_rank.voters_rank";
    public static final String VAR_RANK11 = "force_rank.roster.rank11";
    public static final String VAR_RANK10 = "force_rank.roster.rank10";
    public static final String VAR_RANK9 = "force_rank.roster.rank9";
    public static final String VAR_RANK8 = "force_rank.roster.rank8";
    public static final String VAR_RANK7 = "force_rank.roster.rank7";
    public static final String VAR_RANK6 = "force_rank.roster.rank6";
    public static final String VAR_RANK5 = "force_rank.roster.rank5";
    public static final String VAR_RANK4 = "force_rank.roster.rank4";
    public static final String VAR_RANK3 = "force_rank.roster.rank3";
    public static final String VAR_RANK2 = "force_rank.roster.rank2";
    public static final String VAR_RANK1 = "force_rank.roster.rank1";
    public static final String VAR_RANK_BASE = "force_rank.roster.rank";
    public static final String VAR_VOTING_BASE = "force_rank.voting.rank";
    public static final String VAR_CHAL_VOTING_BASE = "force_rank.chal_voting";
    public static final String VAR_MEMBER_DATA = "force_rank.data.";
    public static final String VAR_BACKUP_TIME = "force_rank.backup_time";
    public static final String STRING_CLUSTER_ENCLAVE_NAME = "enclave_";
    public static final String BATCH_VAR_PVPBOARD_NAME = "force_rank.stats.pvpboard_name";
    public static final String BATCH_VAR_PVPBOARD_RATING = "force_rank.stats.pvpboard_rating";
    public static final String VAR_ENCLAVE = "force_rank.enclave";
    public static final String VAR_XP_MAINTENANCE = "force_rank.xp_maintenance";
    public static final int COUNCIL_RANK_NUMBER = 10;
    public static final String SCRIPT_VAR_VOTE_TERMINAL = "force_rank.vote_terminal";
    public static final String SCRIPT_VAR_CHAL_VOTE_TERMINAL = "force_rank.chal_vote_terminal";
    public static final String ACTION_VENDETTA = "force_rank.vendettas";
    public static final int ACTION_VENDETTA_DURATION = 604800;
    public static final String ACTION_BANISHMENT = "force_rank.banishments";
    public static final int ACTION_BANISHMENT_DURATION = 604800;
    public static final String ACTION_PURGE_COUNCIL = "force_rank.councilPurges";
    public static final int ACTION_PURGE_COUNCIL_DURATION = 604800;
    public static final String ACTION_CANDIDATE_SUDDEN_DEATH = "force_rank.candidateSuddenDeath";
    public static final int ACTION_CANDIATE_SUDDEN_DEATH_DURATION = 604800;
    public static final String VAR_INITIATORS = ".initiators";
    public static final String VAR_VICTIMS = ".victims";
    public static final String VAR_START_TIMESTAMPS = ".timestamps";
    public static final String VAR_NOTIFY_ENCLAVE_OF_DEATH = "force_rank.notifyEnclaveOfDeath";
    public static final String VAR_MY_ENCLAVE_ID = "force_rank.myEnclaveId";
    public static final float MIN_KILL_CONTRIB_FOR_ACTION_DEATH = .51f;
    public static boolean addToForceRankSystem(obj_id player, int council) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            LOG("force_rank", "force_rank.addToForceRankSystem -- player is invalid.");
            return false;
        }
        if (council != LIGHT_COUNCIL && council != DARK_COUNCIL)
        {
            LOG("force_rank", "force_rank.addToForceRankSystem -- " + player + " has an invalid council value of " + council);
            return false;
        }
        String rank_skill = getForceRankSkill(0, council);
        if (rank_skill == null)
        {
            LOG("force_rank", "force_rank.addToForceRankSystem -- rank_skill is null for rank 0.");
            return false;
        }
        setObjVar(player, VAR_RANK, 0);
        setObjVar(player, VAR_COUNCIL, council);
        if (council == LIGHT_COUNCIL)
        {
            setJediState(player, JEDI_STATE_FORCE_RANKED_LIGHT);
            pvpSetAlignedFaction(player, getFactionId("Rebel"));
        }
        else 
        {
            setJediState(player, JEDI_STATE_FORCE_RANKED_DARK);
            pvpSetAlignedFaction(player, getFactionId("Imperial"));
        }
        pvpMakeDeclared(player);
        grantSkill(player, rank_skill);
        prose_package pp = prose.getPackage(new string_id(STF_FILE, "council_joined"), getCouncilName(council), 0);
        sendSystemMessageProse(player, pp);
        if (!hasScript(player, SCRIPT_FRS_PLAYER))
        {
            attachScript(player, SCRIPT_FRS_PLAYER);
        }
        getEnclaveObjId(player, council, "enclaveIdResponse");
        force_rank.grantRankItems(player);
        return true;
    }
    public static boolean removeFromForceRankSystem(obj_id player, boolean surrendered) throws InterruptedException
    {
        LOG("force_rank", "force_rank.removeFromForceRankSystem");
        if (!isIdValid(player))
        {
            LOG("force_rank", "force_rank.removeFromForceRankSystem -- player is invalid.");
            return false;
        }
        int council = getCouncilAffiliation(player);
        if (council == -1)
        {
            LOG("force_rank", "force_rank.removeFromForceRankSystem -- " + player + " has an invalid council affiliation.");
            return false;
        }
        if (getForceRank(player) > 0)
        {
            LOG("force_rank", "force_rank.removeFromForceRankSystem -- " + player + " has a force rank > 0.");
            return false;
        }
        if (!surrendered)
        {
            force_rank.removeForceRankSkills(player, -1);
        }
        removeObjVar(player, "force_rank");
        if (hasScript(player, SCRIPT_FRS_PLAYER))
        {
            detachScript(player, SCRIPT_FRS_PLAYER);
        }
        prose_package pp = prose.getPackage(new string_id(STF_FILE, "council_left"), getCouncilName(council), 0);
        sendSystemMessageProse(player, pp);
        return true;
    }
    public static int adjustForceRankXP(obj_id player, int amt) throws InterruptedException
    {
        LOG("force_rank", "force_rank.adjustForceRankXP -- player ->" + player + " amt ->" + amt);
        if (!isIdValid(player))
        {
            LOG("force_rank", "force_rank.grantForceRankXP -- player is invalid.");
            return -1;
        }
        if (amt == 0)
        {
            return getExperiencePoints(player, FRS_XP);
        }
        else if (amt > 0)
        {
            grantExperiencePoints(player, FRS_XP, amt);
            prose_package pp = prose.getPackage(new string_id(STF_FILE, "experience_granted"), amt);
            sendSystemMessageProse(player, pp);
        }
        else 
        {
            int current_xp = getExperiencePoints(player, FRS_XP);
            if (amt * -1 > current_xp)
            {
                amt = current_xp * -1;
            }
            LOG("force_rank", "force_rank.adjustForceRankXP -- current_xp ->" + current_xp + " final amt ->" + amt);
            grantExperiencePoints(player, FRS_XP, amt);
            prose_package pp = prose.getPackage(new string_id(STF_FILE, "experience_lost"), amt * -1);
            sendSystemMessageProse(player, pp);
            current_xp += amt;
            int rank = getForceRank(player);
            int min_xp = getForceRankMinXp(rank);
            LOG("force_rank", "rank ->" + rank + " min_xp ->" + min_xp + " current_xp ->" + current_xp);
            if (min_xp > current_xp)
            {
                demoteForceRank(player, rank - 1);
            }
        }
        return getExperiencePoints(player, FRS_XP);
    }
    public static boolean addExperienceDebt(obj_id enclave, String player_name, int xp_debt) throws InterruptedException
    {
        if (!isIdValid(enclave))
        {
            LOG("force_rank", "force_rank.addExperienceDebt -- enclave is invalid.");
            return false;
        }
        if (player_name == null)
        {
            LOG("force_rank", "force_rank.addExperienceDebt -- player_name is null.");
            return false;
        }
        if (xp_debt < 1)
        {
            LOG("force_rank", "force_rank.addExperienceDebt -- illegal value for xp_debt: " + xp_debt);
            return false;
        }
        boolean in_rank = false;
        for (int i = 1; i < 12; i++)
        {
            Vector rank_list = getPlayersInForceRankResizeable(enclave, i);
            if (rank_list != null)
            {
                if (rank_list.indexOf(player_name) != -1)
                {
                    in_rank = true;
                    break;
                }
            }
        }
        if (in_rank)
        {
            String obj_var_name = VAR_MEMBER_DATA + player_name + ".xp_debt";
            int current_debt = 0;
            if (hasObjVar(enclave, obj_var_name))
            {
                current_debt = getIntObjVar(enclave, obj_var_name);
            }
            current_debt += xp_debt;
            setObjVar(enclave, obj_var_name, current_debt);
            return true;
        }
        else 
        {
            LOG("force_rank", "force_rank.addExperienceDebt -- " + player_name + " is not in the force rank list.");
            return false;
        }
    }
    public static int spendForceRankXP(obj_id player, int amt) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            LOG("force_rank", "force_rank.spendForceRankXP -- player is invalid.");
            return -1;
        }
        if (amt < 0)
        {
            LOG("force_rank", "force_rank.spendForceRankXP -- illegal amount value: " + amt);
            return -1;
        }
        int current_xp = getExperiencePoints(player, FRS_XP);
        int min_xp = getForceRankMinXp(getForceRank(player));
        LOG("force_rank", "current_xp ->" + current_xp + " min_xp ->" + min_xp);
        if (current_xp - amt < min_xp)
        {
            return -1;
        }
        else 
        {
            adjustForceRankXP(player, amt * -1);
            return getExperiencePoints(player, FRS_XP);
        }
    }
    public static boolean promoteForceRank(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            LOG("force_rank", "force_rank.promoteForceRank -- player is invalid.");
            return false;
        }
        int council = getCouncilAffiliation(player);
        if (council == -1)
        {
            LOG("force_rank", "force_rank.promoteForceRank -- " + player + " has an invalid council value.");
            return false;
        }
        int current_rank = getForceRank(player);
        if (current_rank < 0 || current_rank > 10)
        {
            LOG("force_rank", "force_rank.promoteForceRank -- attempting to promote " + player + " when his rank is at " + current_rank);
            return false;
        }
        String rank_skill = getForceRankSkill(current_rank + 1, council);
        if (rank_skill == null)
        {
            LOG("force_rank", "force_rank.promoteForceRank -- rank_skill is null for rank " + (current_rank + 1));
            return false;
        }
        _updateForceRankData(player, council, 3, Integer.toString(current_rank), "msgForceRankPromotePlayer", true);
        return true;
    }
    public static boolean demoteForceRank(obj_id player, int rank) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            LOG("force_rank", "force_rank.demoteForceRank -- player is invalid.");
            return false;
        }
        if (rank < 0 || rank > 10)
        {
            LOG("force_rank", "force_rank.demoteForceRank -- illegal rank value: " + rank);
            return false;
        }
        int council = getCouncilAffiliation(player);
        if (council == -1)
        {
            LOG("force_rank", "force_rank.demoteForceRank -- " + player + " has an invalid council value.");
            return false;
        }
        int current_rank = getForceRank(player);
        if (current_rank < 1 || current_rank > 11)
        {
            LOG("force_rank", "force_rank.demoteForceRank -- attempting to demote " + player + " when his rank is at " + current_rank);
            return false;
        }
        if (rank >= current_rank)
        {
            LOG("force_rank", "force_rank.demoteForceRank -- attempting to demote " + player + " to a rank that is greater than or equal to his current rank.");
            return false;
        }
        _updateForceRankData(player, council, 4, Integer.toString(rank), "msgForceRankDemotePlayer", true);
        return true;
    }
    public static boolean demoteForceRank(obj_id enclave, String player_name, int rank) throws InterruptedException
    {
        if (!isIdValid(enclave))
        {
            LOG("force_rank", "force_rank.demoteForceRank -- enclave is invalid.");
            return false;
        }
        if (player_name == null)
        {
            LOG("force_rank", "force_rank.demoteForceRank -- player_name is null.");
            return false;
        }
        int council = getCouncilAffiliation(enclave);
        if (council == -1)
        {
            LOG("force_rank", "force_rank.demoteForceRank -- " + enclave + " has an invalid council value.");
            return false;
        }
        _updateForceRankData(enclave, council, 5, player_name + " " + rank, "msgForceRankDemotePlayer", true);
        return true;
    }
    public static boolean resetClusterData(obj_id enclave) throws InterruptedException
    {
        LOG("force_rank", "force_rank.resetClusterData");
        if (!isIdValid(enclave))
        {
            LOG("force_rank", "force_rank.resetClusterData -- enclave is invalid.");
            return false;
        }
        int council = getCouncilAffiliation(enclave);
        if (council == -1)
        {
            LOG("force_rank", "force_rank.resetClusterData -- " + enclave + " has an invalid council value.");
            return false;
        }
        _updateForceRankData(enclave, council, 8, "", "msgFRSUpdateClusterData", true);
        return true;
    }
    public static boolean renameRankMember(obj_id player, String old_name) throws InterruptedException
    {
        if (old_name == null)
        {
            LOG("force_rank", "force_rank.renameRankMember -- old_name is null.");
            return false;
        }
        int council = getCouncilAffiliation(player);
        if (council == -1)
        {
            LOG("force_rank", "force_rank.renameRankMember -- " + player + " has an invalid council value.");
            return false;
        }
        int current_rank = getForceRank(player);
        if (current_rank < 1 || current_rank > 11)
        {
            LOG("force_rank", "force_rank.renameRankMember -- attempting to demote " + player + " when his rank is at " + current_rank);
            return false;
        }
        _updateForceRankData(player, council, 9, old_name, "msgForceRankRenamePlayer", true);
        return true;
    }
    public static boolean removeForceRankSkills(obj_id player, int rank) throws InterruptedException
    {
        LOG("force_rank", "force_rank.removeForceRankSkills");
        if (!isIdValid(player))
        {
            LOG("force_rank", "force_rank.removeForceRankSkills -- player is invalid.");
            return false;
        }
        if (rank < -1 || rank > 10)
        {
            LOG("force_rank", "force_rank.removeForceRankSkills -- invalid value for rank: " + rank);
            return false;
        }
        int current_rank = getForceRank(player);
        if (current_rank < 0)
        {
            LOG("force_rank", "force_rank.removeForceRankSkills -- can't remove skills from a player with a rank of " + current_rank);
            return false;
        }
        int council = getCouncilAffiliation(player);
        if (council < 1)
        {
            LOG("force_rank", "force_rank.removeForceRankSkills -- " + player + " has an invalid council affiliation.");
            return false;
        }
        utils.setScriptVar(player, SCRIPT_VAR_SKILL_RESYNC, 1);
        for (int i = current_rank; i > rank; i--)
        {
            String rank_skill = getForceRankSkill(i, council);
            LOG("force_rank", "rank_skill[" + i + "]->" + rank_skill);
            if (hasSkill(player, rank_skill))
            {
                revokeSkill(player, rank_skill);
            }
        }
        utils.removeScriptVar(player, SCRIPT_VAR_SKILL_RESYNC);
        return true;
    }
    public static boolean resyncForceRankSkills(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            LOG("force_rank", "force_rank.removeForceRankSkills -- player is invalid.");
            return false;
        }
        int rank = getForceRank(player);
        if (rank < 0 || rank > 11)
        {
            LOG("force_rank", "force_rank.removeForceRankSkills -- invalid value for rank: " + rank);
            return false;
        }
        int council = getCouncilAffiliation(player);
        if (council < 1)
        {
            LOG("force_rank", "force_rank.resyncForceRankSkills -- " + player + " has an invalid council affiliation.");
            return false;
        }
        utils.setScriptVar(player, SCRIPT_VAR_SKILL_RESYNC, 1);
        for (int i = 0; i < 12; i++)
        {
            String skill = getForceRankSkill(i, council);
            if (skill != null)
            {
                if (i <= rank)
                {
                    if (!hasSkill(player, skill))
                    {
                        grantSkill(player, skill);
                        CustomerServiceLog("force_rank", "Adding skill " + skill + " to %TU since it should be granted for rank " + rank, player, null);
                    }
                }
            }
        }
        for (int i = 11; i >= 0; i--)
        {
            String skill = getForceRankSkill(i, council);
            if (skill != null)
            {
                if (i > rank)
                {
                    if (hasSkill(player, skill))
                    {
                        revokeSkill(player, skill);
                        CustomerServiceLog("force_rank", "Removing skill " + skill + " from %TU since it not valid for rank " + rank, player, null);
                    }
                }
            }
        }
        utils.removeScriptVar(player, SCRIPT_VAR_SKILL_RESYNC);
        return true;
    }
    public static boolean performEnclaveMaintenance(obj_id enclave) throws InterruptedException
    {
        if (!isIdValid(enclave))
        {
            LOG("force_rank", "force_rank.performEnclaveMaintenance -- enclave is invalid.");
            return false;
        }
        if (hasObjVar(enclave, VAR_XP_MAINTENANCE))
        {
            int last_maintenance = getIntObjVar(enclave, VAR_XP_MAINTENANCE);
            if (getGameTime() > last_maintenance)
            {
                deductXpMaintenance(enclave);
                setObjVar(enclave, VAR_XP_MAINTENANCE, getGameTime() + getXpInterval());
            }
        }
        else 
        {
            setObjVar(enclave, VAR_XP_MAINTENANCE, getGameTime() + getXpInterval());
        }
        arena.checkArenaMaintenance(enclave);
        checkExperienceDebt(enclave);
        checkVotingResults(enclave);
        int council = force_rank.getCouncilAffiliation(enclave);
        if (council == -1)
        {
            LOG("force_rank", "enclave_controller.performEnclaveMaintenance -- " + enclave + " does not have a valid council value.");
            return false;
        }
        createEnclaveTerminals(enclave);
        int request_id = getClusterWideData(force_rank.CLUSTER_DATA_NAME, force_rank.STRING_CLUSTER_ENCLAVE_NAME + council, false, enclave);
        utils.setScriptVar(enclave, force_rank.SCRIPT_VAR_DATA_REQUEST + request_id, 0);
        return true;
    }
    public static boolean checkVotingResults(obj_id enclave) throws InterruptedException
    {
        LOG("force_rank", "force_rank.checkVotingResults");
        if (!isIdValid(enclave))
        {
            LOG("force_rank", "force_rank.checkVotingResults -- enclave is invalid.");
            return false;
        }
        for (int rank = 1; rank < 12; rank++)
        {
            int status = getVoteStatus(enclave, rank);
            int time = getVoteEndTime(enclave, rank);
            if (status == -1 || time == -1)
            {
                LOG("force_rank", "force_rank.checkVotingResults -- invalid value for status/time :" + status + "/" + time + " for rank " + rank);
                continue;
            }
            String obj_var_base = VAR_VOTING_BASE + rank;
            if (!hasObjVar(enclave, obj_var_base) && status != 4)
            {
                setObjVar(enclave, obj_var_base + ".status", 4);
                setObjVar(enclave, obj_var_base + ".time", getGameTime());
                continue;
            }
            boolean time_expired = time < getGameTime();
            switch (status)
            {
                case 1:
                
                {
                    if (time_expired)
                    {
                        String[] petitioners = getRankPetitioners(enclave, rank);
                        if (petitioners == null)
                        {
                            int avail_slots = getAvailableRankSlots(enclave, rank);
                            if (avail_slots > 0)
                            {
                                setObjVar(enclave, obj_var_base + ".status", 1);
                                setObjVar(enclave, obj_var_base + ".time", getGameTime() + getPetitionInterval());
                            }
                            else 
                            {
                                resetVotingTerminal(enclave, rank);
                            }
                        }
                        else 
                        {
                            int avail_slots = getAvailableRankSlots(enclave, rank);
                            if (petitioners.length <= avail_slots || petitioners.length == 1)
                            {
                                removeObjVar(enclave, obj_var_base + ".petition");
                                removeObjVar(enclave, obj_var_base + ".time");
                                removeObjVar(enclave, obj_var_base + ".votes");
                                setObjVar(enclave, obj_var_base + ".winner", petitioners);
                                setObjVar(enclave, obj_var_base + ".status", 3);
                                setObjVar(enclave, obj_var_base + ".time", getGameTime() + getAcceptanceInterval());
                                string_id sub = new string_id(force_rank.STF_FILE, "vote_win_sub");
                                string_id body = new string_id(force_rank.STF_FILE, "vote_win_body");
                                for (int i = 0; i < petitioners.length; i++)
                                {
                                    utils.sendMail(sub, body, petitioners[i], "Enclave Records");
                                }
                            }
                            else 
                            {
                                setObjVar(enclave, obj_var_base + ".status", 2);
                                setObjVar(enclave, obj_var_base + ".time", getGameTime() + getVoteInterval());
                                int[] votes = new int[petitioners.length];
                                Arrays.fill(votes, 0);
                                setObjVar(enclave, obj_var_base + ".votes", votes);
                                string_id sub = new string_id(force_rank.STF_FILE, "vote_cycle_begun_sub");
                                string_id body = new string_id(force_rank.STF_FILE, "vote_cycle_begun_body");
                                String rank_str = localize(new string_id(force_rank.STF_FILE, "rank" + rank));
                                int vote_interval = getVoteInterval();
                                String time_str = player_structure.assembleTimeRemaining(player_structure.convertSecondsTime(vote_interval));
                                prose_package pp = prose.getPackage(body, time_str, rank_str);
                                sendVoterMail(enclave, rank, sub, pp);
                            }
                        }
                    }
                    break;
                }
                case 2:
                
                {
                    if (time_expired)
                    {
                        int avail_slots = getAvailableRankSlots(enclave, rank);
                        String[] petitioners = getRankPetitioners(enclave, rank);
                        int[] votes = getRankPetitionerVotes(enclave, rank);
                        if (avail_slots > 0)
                        {
                            int[] top_votes = getTopVotes(votes, avail_slots);
                            votes = getRankPetitionerVotes(enclave, rank);
                            if (top_votes != null)
                            {
                                Vector winners = new Vector();
                                winners.setSize(0);
                                for (int i = 0; i < top_votes.length; i++)
                                {
                                    int vote_idx = utils.getElementPositionInArray(votes, top_votes[i]);
                                    winners.add(petitioners[vote_idx]);
                                    votes[vote_idx] = -1;
                                    string_id sub = new string_id(force_rank.STF_FILE, "vote_win_sub");
                                    string_id body = new string_id(force_rank.STF_FILE, "vote_win_body");
                                    utils.sendMail(sub, body, petitioners[vote_idx], "Enclave Records");
                                    CustomerServiceLog("force_rank", petitioners[vote_idx] + " won an open seat in rank " + rank + " with " + top_votes[i] + " votes.");
                                }
                                if (winners.size() > 0)
                                {
                                    removeObjVar(enclave, obj_var_base + ".petition");
                                    removeObjVar(enclave, obj_var_base + ".votes");
                                    setObjVar(enclave, obj_var_base + ".winner", winners);
                                    setObjVar(enclave, obj_var_base + ".status", 3);
                                    setObjVar(enclave, obj_var_base + ".time", getGameTime() + getAcceptanceInterval());
                                }
                            }
                            else 
                            {
                                LOG("force_rank", "force_rank.checkVotingResults -- voting failed for rank " + rank + " due to a null top_votes.");
                                resetVotingTerminal(enclave, rank);
                            }
                        }
                        else 
                        {
                            int[] top_votes = getTopVotes(votes, 1);
                            if (top_votes != null)
                            {
                                int vote_idx = utils.getElementPositionInArray(votes, top_votes[0]);
                                removeObjVar(enclave, obj_var_base + ".petition");
                                removeObjVar(enclave, obj_var_base + ".time");
                                removeObjVar(enclave, obj_var_base + ".votes");
                                String[] winner_list = 
                                {
                                    petitioners[vote_idx]
                                };
                                setObjVar(enclave, obj_var_base + ".winner", winner_list);
                                setObjVar(enclave, obj_var_base + ".status", 4);
                                string_id sub = new string_id(force_rank.STF_FILE, "vote_win_sub");
                                string_id body = new string_id(force_rank.STF_FILE, "vote_win_no_slot_body");
                                utils.sendMail(sub, body, petitioners[vote_idx], "Enclave Records");
                            }
                            else 
                            {
                                LOG("force_rank", "force_rank.checkVotingResults -- voting failed for rank " + rank + " due to a null top_votes.");
                                resetVotingTerminal(enclave, rank);
                            }
                        }
                        checkMissedVotes(enclave, rank);
                    }
                    break;
                }
                case 3:
                
                {
                    if (time_expired)
                    {
                        String[] winners = getVoteWinners(enclave, rank);
                        if (winners != null)
                        {
                            string_id sub = new string_id(force_rank.STF_FILE, "acceptance_expired_sub");
                            string_id body = new string_id(force_rank.STF_FILE, "acceptance_expired_body");
                            String rank_str = localize(new string_id(force_rank.STF_FILE, "rank" + rank));
                            prose_package pp = prose.getPackage(body, rank_str);
                            for (int i = 0; i < winners.length; i++)
                            {
                                utils.sendMail(sub, pp, winners[i], "Enclave Records");
                                CustomerServiceLog("force_rank", winners[i] + "'s chance to accept a promotion to rank " + rank + " has expired.");
                            }
                        }
                        resetVotingTerminal(enclave, rank);
                    }
                    break;
                }
                case 4:
                
                {
                    int avail_slots = getAvailableRankSlots(enclave, rank);
                    if (avail_slots > 0)
                    {
                        String[] winners = getVoteWinners(enclave, rank);
                        if (winners != null)
                        {
                            setObjVar(enclave, obj_var_base + ".status", 3);
                            setObjVar(enclave, obj_var_base + ".time", getGameTime() + getAcceptanceInterval());
                            string_id sub = new string_id(force_rank.STF_FILE, "vote_seat_available_sub");
                            string_id body = new string_id(force_rank.STF_FILE, "vote_seat_available_body");
                            String rank_str = localize(new string_id(force_rank.STF_FILE, "rank" + rank));
                            prose_package pp = prose.getPackage(body, rank_str);
                            for (int i = 0; i < winners.length; i++)
                            {
                                utils.sendMail(sub, pp, winners[i], "Enclave Records");
                            }
                        }
                        else 
                        {
                            setObjVar(enclave, obj_var_base + ".status", 1);
                            setObjVar(enclave, obj_var_base + ".time", getGameTime() + getPetitionInterval());
                        }
                    }
                }
            }
        }
        return true;
    }
    public static boolean checkChallengeVotingResults(obj_id enclave) throws InterruptedException
    {
        LOG("force_rank", "force_rank.checkChallengeVotingResults");
        if (!isIdValid(enclave))
        {
            LOG("force_rank", "force_rank.checkChallengeVotingResults -- enclave is invalid.");
            return false;
        }
        obj_id terminal = getChallengeVotingTerminal(enclave);
        if (!isIdValid(terminal))
        {
            LOG("force_rank", "force_rank.checkChallengeVotingResults -- terminal is invalid.");
            return false;
        }
        obj_var_list vote_list = getObjVarList(terminal, VAR_CHAL_VOTING_BASE);
        if (vote_list == null)
        {
            return true;
        }
        for (int i = 0; i < vote_list.getNumItems(); i++)
        {
            obj_var challenge_objvar = vote_list.getObjVar(i);
            if (challenge_objvar == null)
            {
                continue;
            }
            String name = challenge_objvar.getName();
            if (name == null)
            {
                continue;
            }
            String objvar_name = VAR_CHAL_VOTING_BASE + "." + name;
            int rank = getForceRank(enclave, name);
            int challenge_rank = getChallengeVoteRank(terminal, name);
            int status = getChallengeVoteStatus(terminal, name);
            if (status == -1 || status == 3)
            {
                if (hasObjVar(terminal, objvar_name))
                {
                    removeObjVar(terminal, objvar_name);
                }
                if (hasObjVar(terminal, BATCH_VAR_VOTERS + "." + name))
                {
                    removeObjVar(terminal, BATCH_VAR_VOTERS + "." + name);
                }
                continue;
            }
            int time = getChallengeVoteEndTime(terminal, name);
            boolean time_expired = time < getGameTime();
            switch (status)
            {
                case 1:
                
                {
                    if (challenge_rank != rank)
                    {
                        removeObjVar(terminal, objvar_name);
                        if (hasObjVar(terminal, BATCH_VAR_VOTERS + "." + name))
                        {
                            removeObjVar(terminal, BATCH_VAR_VOTERS + "." + name);
                        }
                        string_id sub = new string_id(force_rank.STF_FILE, "challenge_vote_cancelled_sub");
                        string_id body = new string_id(force_rank.STF_FILE, "challenge_vote_cancelled_body");
                        prose_package pp = prose.getPackage(body, name);
                        sendChallengeVoterMail(enclave, challenge_rank, sub, pp);
                        continue;
                    }
                    if (time_expired)
                    {
                        int[] votes = getChallengeVotes(terminal, name);
                        boolean success = false;
                        if (votes != null)
                        {
                            if (votes[0] >= votes[1] * 2)
                            {
                                success = true;
                            }
                        }
                        if (success)
                        {
                            demoteForceRank(enclave, name, challenge_rank - 1);
                            string_id sub = new string_id(force_rank.STF_FILE, "challenge_vote_success_sub");
                            string_id body = new string_id(force_rank.STF_FILE, "challenge_vote_success_body");
                            prose_package pp = prose.getPackage(body, name, Integer.toString(votes[0]), votes[1]);
                            sendChallengeVoterMail(enclave, challenge_rank, sub, pp);
                        }
                        else 
                        {
                            string_id sub = new string_id(force_rank.STF_FILE, "challenge_vote_fail_sub");
                            string_id body = new string_id(force_rank.STF_FILE, "challenge_vote_fail_body");
                            prose_package pp = prose.getPackage(body, name, Integer.toString(votes[0]), votes[1]);
                            sendChallengeVoterMail(enclave, challenge_rank, sub, pp);
                        }
                        setObjVar(terminal, objvar_name + ".status", 2);
                        setObjVar(terminal, objvar_name + ".time", getGameTime() + 3600);
                    }
                    break;
                }
                case 2:
                
                {
                    if (time_expired)
                    {
                        if (hasObjVar(terminal, objvar_name))
                        {
                            removeObjVar(terminal, objvar_name);
                        }
                        if (hasObjVar(terminal, BATCH_VAR_VOTERS + "." + name))
                        {
                            removeObjVar(terminal, BATCH_VAR_VOTERS + "." + name);
                        }
                    }
                    break;
                }
            }
        }
        return true;
    }
    public static boolean checkMissedVotes(obj_id enclave, int rank) throws InterruptedException
    {
        if (!isIdValid(enclave))
        {
            LOG("force_rank", "force_rank.checkMissedVotes -- enclave is invalid.");
            return false;
        }
        if (rank < 1 || rank > 11)
        {
            LOG("force_rank", "force_rank.checkMissedVotes -- illegal rank value of " + rank);
            return false;
        }
        obj_id terminal = getVotingTerminal(enclave);
        if (!isIdValid(terminal))
        {
            LOG("force_rank", "force_rank.checkMissedVotes -- can't find the voting terminal for enclave " + enclave);
            return false;
        }
        Vector voters = utils.getResizeableStringBatchObjVar(terminal, BATCH_VAR_VOTERS + rank);
        for (int vote_rank = 1; vote_rank < 12; vote_rank++)
        {
            if (getVoteWeight(vote_rank, rank) > 0)
            {
                String[] rank_list = getPlayersInForceRank(enclave, vote_rank);
                if (rank_list != null)
                {
                    int xp_debt = (int)(MISSED_VOTE_PENALTY * Math.pow(vote_rank, 2));
                    for (int i = 0; i < rank_list.length; i++)
                    {
                        if (voters != null)
                        {
                            if (voters.indexOf(rank_list[i]) != -1)
                            {
                                continue;
                            }
                        }
                        CustomerServiceLog("force_rank", "Adding experience debt of " + xp_debt + " to " + rank_list[i] + " for missing a vote on rank " + vote_rank);
                        addExperienceDebt(enclave, rank_list[i], xp_debt);
                        string_id sub = new string_id(force_rank.STF_FILE, "vote_missed_sub");
                        string_id body = new string_id(force_rank.STF_FILE, "vote_missed_body");
                        String rank_str = localize(new string_id(force_rank.STF_FILE, "rank" + rank));
                        prose_package pp = prose.getPackage(body, rank_str, xp_debt);
                        utils.sendMail(sub, pp, rank_list[i], "Enclave Records");
                    }
                }
            }
        }
        return true;
    }
    public static int getVoteStatus(obj_id enclave, int rank) throws InterruptedException
    {
        if (!isIdValid(enclave))
        {
            LOG("force_rank", "force_rank.getRankVoteStatus -- enclave is invalid.");
            return -1;
        }
        if (rank < 1 || rank > 11)
        {
            LOG("force_rank", "force_rank.getRankVoteStatus -- illegal rank value of " + rank);
            return -1;
        }
        String obj_var_name = VAR_VOTING_BASE + rank + ".status";
        if (hasObjVar(enclave, obj_var_name))
        {
            return getIntObjVar(enclave, obj_var_name);
        }
        else 
        {
            return 4;
        }
    }
    public static int getVoteEndTime(obj_id enclave, int rank) throws InterruptedException
    {
        if (!isIdValid(enclave))
        {
            LOG("force_rank", "force_rank.getVoteEndTime -- enclave is invalid.");
            return -1;
        }
        if (rank < 1 || rank > 11)
        {
            LOG("force_rank", "force_rank.getVoteEndTime -- illegal rank value of " + rank);
            return -1;
        }
        String obj_var_name = VAR_VOTING_BASE + rank + ".time";
        if (hasObjVar(enclave, obj_var_name))
        {
            return getIntObjVar(enclave, obj_var_name);
        }
        else 
        {
            return 0;
        }
    }
    public static boolean isVoteTimeExpired(obj_id enclave, int rank) throws InterruptedException
    {
        if (!isIdValid(enclave))
        {
            LOG("force_rank", "force_rank.hasVoteTimeExpired -- enclave is invalid.");
            return false;
        }
        if (rank < 1 || rank > 11)
        {
            LOG("force_rank", "force_rank.hasVoteTimeExpired -- illegal rank value of " + rank);
            return false;
        }
        int time = getVoteEndTime(enclave, rank);
        if (time < getGameTime())
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public static int getChallengeVoteStatus(obj_id terminal, String challenged) throws InterruptedException
    {
        if (!isIdValid(terminal))
        {
            LOG("force_rank", "force_rank.getChallengeVoteStatus -- enclave is invalid.");
            return -1;
        }
        if (challenged == null)
        {
            LOG("force_rank", "force_rank.getChallengeVoteStatus -- challenged is null.");
            return -1;
        }
        String obj_var_name = VAR_CHAL_VOTING_BASE + "." + challenged + ".status";
        if (hasObjVar(terminal, obj_var_name))
        {
            return getIntObjVar(terminal, obj_var_name);
        }
        else 
        {
            return 3;
        }
    }
    public static int getChallengeVoteEndTime(obj_id terminal, String challenged) throws InterruptedException
    {
        if (!isIdValid(terminal))
        {
            LOG("force_rank", "force_rank.getVoteEndTime -- enclave is invalid.");
            return -1;
        }
        if (challenged == null)
        {
            LOG("force_rank", "force_rank.getChallengeVoteEndTime -- challenged is null.");
            return -1;
        }
        String obj_var_name = VAR_CHAL_VOTING_BASE + "." + challenged + ".time";
        if (hasObjVar(terminal, obj_var_name))
        {
            return getIntObjVar(terminal, obj_var_name);
        }
        else 
        {
            return 0;
        }
    }
    public static boolean isChallengeVoteTimeExpired(obj_id terminal, String challenged) throws InterruptedException
    {
        if (!isIdValid(terminal))
        {
            LOG("force_rank", "force_rank.isChallengeVoteTimeExpired -- enclave is invalid.");
            return false;
        }
        if (challenged == null)
        {
            LOG("force_rank", "force_rank.isChallengeVoteTimeExpired -- challenged is null.");
            return false;
        }
        int time = getChallengeVoteEndTime(terminal, challenged);
        if (time < getGameTime())
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public static int getChallengeVoteRank(obj_id terminal, String challenged) throws InterruptedException
    {
        if (!isIdValid(terminal))
        {
            LOG("force_rank", "force_rank.getChallengeVoteRank -- enclave is invalid.");
            return -1;
        }
        if (challenged == null)
        {
            LOG("force_rank", "force_rank.getChallengeVoteRank -- challenged is null.");
            return -1;
        }
        String obj_var_name = VAR_CHAL_VOTING_BASE + "." + challenged + ".rank";
        if (hasObjVar(terminal, obj_var_name))
        {
            return getIntObjVar(terminal, obj_var_name);
        }
        else 
        {
            return 0;
        }
    }
    public static int[] getChallengeVotes(obj_id terminal, String challenged) throws InterruptedException
    {
        if (!isIdValid(terminal))
        {
            LOG("force_rank", "force_rank.getChallengeVotes -- terminal is invalid.");
            return null;
        }
        String obj_var_name = VAR_CHAL_VOTING_BASE + "." + challenged + ".votes";
        if (hasObjVar(terminal, obj_var_name))
        {
            int[] votes = getIntArrayObjVar(terminal, obj_var_name);
            if (votes.length != 2)
            {
                return null;
            }
            else 
            {
                return votes;
            }
        }
        else 
        {
            return null;
        }
    }
    public static String[] getChallengeVoteList(obj_id terminal) throws InterruptedException
    {
        if (!isIdValid(terminal))
        {
            LOG("force_rank", "force_rank.getChallengeVoteList -- terminal is invalid.");
            return null;
        }
        Vector names = new Vector();
        names.setSize(0);
        obj_var_list vote_list = getObjVarList(terminal, VAR_CHAL_VOTING_BASE);
        if (vote_list == null)
        {
            return null;
        }
        for (int i = 0; i < vote_list.getNumItems(); i++)
        {
            obj_var challenge_objvar = vote_list.getObjVar(i);
            if (challenge_objvar != null)
            {
                String name = challenge_objvar.getName();
                if (name != null)
                {
                    names.add(name);
                }
            }
        }
        if (names.size() > 0)
        {
            String[] _names = new String[0];
            if (names != null)
            {
                _names = new String[names.size()];
                names.toArray(_names);
            }
            return _names;
        }
        else 
        {
            return null;
        }
    }
    public static int getNumChallengeVoteList(obj_id terminal) throws InterruptedException
    {
        if (!isIdValid(terminal))
        {
            LOG("force_rank", "force_rank.getNumChallengeVoteList -- terminal is invalid.");
            return -1;
        }
        obj_var_list vote_list = getObjVarList(terminal, VAR_CHAL_VOTING_BASE);
        if (vote_list != null)
        {
            return vote_list.getNumItems();
        }
        else 
        {
            return 0;
        }
    }
    public static String[] getRankPetitioners(obj_id enclave, int rank) throws InterruptedException
    {
        if (!isIdValid(enclave))
        {
            LOG("force_rank", "force_rank.getRankPetitioners -- enclave is invalid.");
            return null;
        }
        if (rank < 1 || rank > 11)
        {
            LOG("force_rank", "force_rank.getRankPetitioners -- illegal rank value of " + rank);
            return null;
        }
        String obj_var_name = VAR_VOTING_BASE + rank + ".petition";
        if (hasObjVar(enclave, obj_var_name))
        {
            return getStringArrayObjVar(enclave, obj_var_name);
        }
        else 
        {
            return null;
        }
    }
    public static Vector getRankPetitionersResizeable(obj_id enclave, int rank) throws InterruptedException
    {
        if (!isIdValid(enclave))
        {
            LOG("force_rank", "force_rank.getRankPetitionersResizeable -- enclave is invalid.");
            return null;
        }
        if (rank < 1 || rank > 11)
        {
            LOG("force_rank", "force_rank.getRankPetitionersResizeable -- illegal rank value of " + rank);
            return null;
        }
        String obj_var_name = VAR_VOTING_BASE + rank + ".petition";
        if (hasObjVar(enclave, obj_var_name))
        {
            return getResizeableStringArrayObjVar(enclave, obj_var_name);
        }
        else 
        {
            return null;
        }
    }
    public static int[] getRankPetitionerVotes(obj_id enclave, int rank) throws InterruptedException
    {
        if (!isIdValid(enclave))
        {
            LOG("force_rank", "force_rank.getRankPetitioners -- enclave is invalid.");
            return null;
        }
        if (rank < 1 || rank > 11)
        {
            LOG("force_rank", "force_rank.getRankPetitioners -- illegal rank value of " + rank);
            return null;
        }
        String obj_var_name = VAR_VOTING_BASE + rank + ".votes";
        if (hasObjVar(enclave, obj_var_name))
        {
            return getIntArrayObjVar(enclave, obj_var_name);
        }
        else 
        {
            return null;
        }
    }
    public static Vector getRankPetitionerVotesResizeable(obj_id enclave, int rank) throws InterruptedException
    {
        if (!isIdValid(enclave))
        {
            LOG("force_rank", "force_rank.getRankPetitionerVotesResizeable -- enclave is invalid.");
            return null;
        }
        if (rank < 1 || rank > 11)
        {
            LOG("force_rank", "force_rank.getRankPetitionerVotesResizeable -- illegal rank value of " + rank);
            return null;
        }
        String obj_var_name = VAR_VOTING_BASE + rank + ".votes";
        if (hasObjVar(enclave, obj_var_name))
        {
            return getResizeableIntArrayObjVar(enclave, obj_var_name);
        }
        else 
        {
            return null;
        }
    }
    public static int[] getTopVotes(int[] votes, int winners) throws InterruptedException
    {
        if (votes == null)
        {
            LOG("force_rank", "force_rank.getTopVotes -- votes is null.");
            return null;
        }
        if (winners < 1)
        {
            LOG("force_rank", "force_rank.getTopVotes -- illegal winners value of " + winners);
            return null;
        }
        if (winners > votes.length)
        {
            return votes;
        }
        int[] top_votes = new int[winners];
        Arrays.sort(votes);
        for (int i = 0; i < top_votes.length; i++)
        {
            top_votes[i] = votes[votes.length - (i + 1)];
        }
        return top_votes;
    }
    public static String[] getVoteWinners(obj_id enclave, int rank) throws InterruptedException
    {
        if (!isIdValid(enclave))
        {
            LOG("force_rank", "force_rank.getVoteWinner -- enclave is invalid.");
            return null;
        }
        if (rank < 1 || rank > 11)
        {
            LOG("force_rank", "force_rank.getVoteWinner -- illegal rank value of " + rank);
            return null;
        }
        String obj_var_name = VAR_VOTING_BASE + rank + ".winner";
        if (hasObjVar(enclave, obj_var_name))
        {
            return getStringArrayObjVar(enclave, obj_var_name);
        }
        else 
        {
            return null;
        }
    }
    public static obj_id getVotingTerminal(obj_id enclave) throws InterruptedException
    {
        if (!isIdValid(enclave))
        {
            LOG("force_rank", "force_rank.getVotingTerminal -- enclave is invalid.");
            return null;
        }
        if (utils.hasScriptVar(enclave, SCRIPT_VAR_VOTE_TERMINAL))
        {
            obj_id terminal = utils.getObjIdScriptVar(enclave, SCRIPT_VAR_VOTE_TERMINAL);
            if (terminal.isAuthoritative())
            {
                return terminal;
            }
            else 
            {
                return null;
            }
        }
        else 
        {
            return null;
        }
    }
    public static obj_id getArenaTerminal(obj_id enclave) throws InterruptedException
    {
        if (!isIdValid(enclave))
        {
            LOG("force_rank", "force_rank.getArenaTerminal -- enclave is invalid.");
            return null;
        }
        if (utils.hasScriptVar(enclave, arena.VAR_CHALLENGE_TERMINAL))
        {
            obj_id terminal = utils.getObjIdScriptVar(enclave, arena.VAR_CHALLENGE_TERMINAL);
            if (terminal.isAuthoritative())
            {
                return terminal;
            }
            else 
            {
                return null;
            }
        }
        else 
        {
            return null;
        }
    }
    public static obj_id getChallengeVotingTerminal(obj_id enclave) throws InterruptedException
    {
        if (!isIdValid(enclave))
        {
            LOG("force_rank", "force_rank.getChallengeVotingTerminal -- enclave is invalid.");
            return null;
        }
        if (utils.hasScriptVar(enclave, SCRIPT_VAR_CHAL_VOTE_TERMINAL))
        {
            obj_id terminal = utils.getObjIdScriptVar(enclave, SCRIPT_VAR_CHAL_VOTE_TERMINAL);
            if (terminal.isAuthoritative())
            {
                return terminal;
            }
            else 
            {
                return null;
            }
        }
        else 
        {
            return null;
        }
    }
    public static boolean resetVotingTerminal(obj_id enclave, int rank) throws InterruptedException
    {
        LOG("force_rank", "force_rank.resetVotingTerminal -- " + VAR_VOTING_BASE + rank);
        if (!isIdValid(enclave))
        {
            LOG("force_rank", "force_rank.resetVotingTerminal -- enclave is invalid.");
            return false;
        }
        if (rank < 1 || rank > 11)
        {
            LOG("force_rank", "force_rank.resetVotingTerminal -- illegal rank value of " + rank);
            return false;
        }
        obj_id terminal = getVotingTerminal(enclave);
        if (!isIdValid(terminal))
        {
            LOG("force_rank", "force_rank.resetVotingTerminal -- can't find a voting terminal for " + enclave);
            return false;
        }
        removeObjVar(terminal, BATCH_VAR_VOTERS + rank);
        removeObjVar(enclave, VAR_VOTING_BASE + rank);
        return true;
    }
    public static boolean deductXpMaintenance(obj_id enclave) throws InterruptedException
    {
        if (!isIdValid(enclave))
        {
            LOG("force_rank", "force_rank.deductXpMaintenance -- enclave is invalid.");
            return false;
        }
        int base_xp_cost = BASE_XP_MAINTENANCE;
        for (int rank = 1; rank < 12; rank++)
        {
            String[] rank_list = getPlayersInForceRank(enclave, rank);
            if (rank_list != null)
            {
                int xp_cost = base_xp_cost * rank;
                for (int i = 0; i < rank_list.length; i++)
                {
                    addExperienceDebt(enclave, rank_list[i], xp_cost);
                    CustomerServiceLog("force_rank", "Adding experience debt of " + xp_cost + " to " + rank_list[i] + " for regular experience maintenance.");
                    string_id sub = new string_id(force_rank.STF_FILE, "xp_maintenace_sub");
                    string_id body = new string_id(force_rank.STF_FILE, "xp_maintenance_body");
                    prose_package pp = prose.getPackage(body, xp_cost);
                    utils.sendMail(sub, pp, rank_list[i], "Enclave Records");
                }
            }
        }
        return true;
    }
    public static boolean checkExperienceDebt(obj_id enclave) throws InterruptedException
    {
        if (!isIdValid(enclave))
        {
            LOG("force_rank", "force_rank.checkExperienceDebt -- enclave is invalid.");
            return false;
        }
        for (int i = 1; i < 12; i++)
        {
            String[] rank_list = getPlayersInForceRank(enclave, i);
            if (rank_list != null)
            {
                int xp_to_demote = 5000 * i;
                for (int j = 0; j < rank_list.length; j++)
                {
                    String obj_var_name = VAR_MEMBER_DATA + rank_list[j] + ".xp_debt";
                    if (hasObjVar(enclave, obj_var_name))
                    {
                        int xp_debt = getIntObjVar(enclave, obj_var_name);
                        if (xp_debt > xp_to_demote)
                        {
                            if (!demoteForceRank(enclave, rank_list[j], i - 1))
                            {
                                LOG("force_rank", "force_rank.checkExperienceDebt -- failed to demote " + rank_list[j] + " due to too much xp debt.");
                                CustomerServiceLog("force_rank", "Failed to demote " + rank_list[j] + " for too much xp debt");
                            }
                            else 
                            {
                                CustomerServiceLog("force_rank", "Demoting " + rank_list[j] + " for too much xp debt");
                                string_id sub = new string_id(force_rank.STF_FILE, "demote_xp_debt_sub");
                                string_id body = new string_id(force_rank.STF_FILE, "demote_xp_debt_body");
                                utils.sendMail(sub, body, rank_list[j], "Enclave Records");
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
    public static boolean requestExperienceDebt(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            LOG("force_rank", "force_rank.requestExperienceDebt -- player is invalid.");
            return false;
        }
        obj_id enclave = getEnclave(player);
        if (isIdValid(enclave))
        {
            dictionary d = new dictionary();
            d.put("player", player);
            d.put("player_name", getFirstName(player));
            messageTo(enclave, "msgRequestExperienceDebt", d, 3.0f, false);
            return true;
        }
        else 
        {
            return false;
        }
    }
    public static int getExperienceDebt(obj_id enclave, String player_name) throws InterruptedException
    {
        if (!isIdValid(enclave))
        {
            LOG("force_rank", "force_rank.getExperienceDebt -- enclave is invalid.");
            return -1;
        }
        if (player_name == null)
        {
            LOG("force_rank", "force_rank.getExperienceDebt -- player_name is null.");
            return -1;
        }
        String obj_var_name = VAR_MEMBER_DATA + player_name + ".xp_debt";
        if (hasObjVar(enclave, obj_var_name))
        {
            return getIntObjVar(enclave, obj_var_name);
        }
        else 
        {
            return 0;
        }
    }
    public static boolean validateFRSPlayerData(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            LOG("force_rank", "force_rank.validateFRSPlayerData -- player is invalid.");
            return false;
        }
        if (hasObjVar(player, "renamePerformed"))
        {
            return false;
        }
        int council = getCouncilAffiliation(player);
        if (council == -1)
        {
            LOG("force_rank", "force_rank.validateFRSPlayerData -- " + player + " does not have council value.");
            return false;
        }
        obj_id enclave = getEnclave(player);
        if (!isIdValid(enclave))
        {
            LOG("force_rank", "force_rank.validateFRSPlayerData -- " + player + " does not have an enclave. Resetting it.");
            getEnclaveObjId(player, getCouncilAffiliation(player), "enclaveIdResponse");
            return false;
        }
        LOG("force_rank", "force_rank.validateFRSPlayerData -- player name " + getFirstName(player));
        dictionary d = new dictionary();
        d.put("player", player);
        d.put("player_name", getFirstName(player));
        d.put("player_rank", getForceRank(player));
        messageTo(enclave, "msgValidateFRSPlayerData", d, 10.0f, false);
        return true;
    }
    public static int getForceRank(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            LOG("force_rank", "force_rank.getForceRank -- player is invalid.");
            return -1;
        }
        if (hasObjVar(player, VAR_RANK))
        {
            return getIntObjVar(player, VAR_RANK);
        }
        else 
        {
            return -1;
        }
    }
    public static int getForceRank(obj_id enclave, String player_name) throws InterruptedException
    {
        if (!isIdValid(enclave))
        {
            LOG("force_rank", "force_rank.getForceRank -- enclave is invalid.");
            return -1;
        }
        if (player_name == null)
        {
            LOG("force_rank", "force_rank.getForceRank -- enclave is invalid.");
            return -1;
        }
        for (int i = 1; i < 12; i++)
        {
            Vector rank_list = getPlayersInForceRankResizeable(enclave, i);
            if (rank_list != null)
            {
                int idx = rank_list.indexOf(player_name);
                if (idx != -1)
                {
                    return i;
                }
            }
        }
        return -1;
    }
    public static int getForceTier(int rank) throws InterruptedException
    {
        if (rank < 0 || rank > 11)
        {
            LOG("force_rank", "force_rank.getForceTier -- illegal rank value: " + rank);
            return -1;
        }
        if (rank == 0)
        {
            return 0;
        }
        if (rank > 0 && rank < 5)
        {
            return 1;
        }
        if (rank > 4 && rank < 8)
        {
            return 2;
        }
        if (rank > 7 && rank < 10)
        {
            return 3;
        }
        if (rank == 10)
        {
            return 4;
        }
        if (rank == 11)
        {
            return 5;
        }
        return -1;
    }
    public static obj_id getEnclave(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            LOG("force_rank", "force_rank.getEnclave -- player is invalid.");
            return null;
        }
        if (hasObjVar(player, VAR_MY_ENCLAVE_ID))
        {
            return getObjIdObjVar(player, VAR_MY_ENCLAVE_ID);
        }
        else 
        {
            return null;
        }
    }
    public static int getCouncilAffiliation(obj_id object) throws InterruptedException
    {
        if (!isIdValid(object))
        {
            LOG("force_rank", "force_rank.getForceRanking -- object is invalid.");
            return -1;
        }
        if (hasObjVar(object, VAR_COUNCIL))
        {
            int council = getIntObjVar(object, VAR_COUNCIL);
            if (council != DARK_COUNCIL && council != LIGHT_COUNCIL)
            {
                return -1;
            }
            else 
            {
                return council;
            }
        }
        else 
        {
            return -1;
        }
    }
    public static String getCouncilName(int council) throws InterruptedException
    {
        if (COUNCIL_NAMES.length < council)
        {
            return null;
        }
        String name = COUNCIL_NAMES[council];
        if (name != null && name.length() > 0)
        {
            return name;
        }
        else 
        {
            return null;
        }
    }
    public static int getAvailableRankSlots(obj_id enclave, int rank) throws InterruptedException
    {
        if (!isIdValid(enclave))
        {
            LOG("force_rank", "force_rank.getAvailableRankSlots -- enclave is invalid.");
            return -1;
        }
        int max_slots = getForceRankNumSlots(rank);
        if (max_slots > 0)
        {
            int slots_taken = 0;
            String[] rank_list = getPlayersInForceRank(enclave, rank);
            if (rank_list != null)
            {
                slots_taken = rank_list.length;
            }
            return max_slots - slots_taken;
        }
        else 
        {
            LOG("force_rank", "force_rank.getAvailableRankSlots -- invalid rank value of " + rank);
            return -1;
        }
    }
    public static int getFactionId(String faction) throws InterruptedException
    {
        if (faction == null)
        {
            LOG("force_rank", "force_rank.getFactionId -- faction is null.");
            return 0;
        }
        int faction_num = factions.getFactionNumber(faction);
        if (faction_num == -1)
        {
            return 0;
        }
        int faction_id = dataTableGetInt("datatables/faction/faction.iff", faction_num, "pvpFaction");
        return faction_id;
    }
    public static String getForceRankSkill(int rank, int council) throws InterruptedException
    {
        if (council != LIGHT_COUNCIL && council != DARK_COUNCIL)
        {
            LOG("force_rank", "force_rank.getForceRankSkill -- invalid council value of " + council);
            return null;
        }
        String datatable;
        if (council == LIGHT_COUNCIL)
        {
            datatable = FRS_DATATABLE;
        }
        else 
        {
            datatable = FRS_DARK_DATATABLE;
        }
        int index = dataTableSearchColumnForInt(rank, "rank", datatable);
        if (index == -1)
        {
            LOG("force_rank", "force_rank.getForceRankSkill -- faction invalid rank value of " + rank);
            return null;
        }
        String skill = dataTableGetString(datatable, index, "skill");
        if (skill.length() < 1)
        {
            return null;
        }
        else 
        {
            return skill;
        }
    }
    public static int getForceSkillRank(String skill, int council) throws InterruptedException
    {
        if (skill == null)
        {
            LOG("force_rank", "force_rank.getForceSkillRank -- skill is null.");
            return -1;
        }
        if (council != LIGHT_COUNCIL && council != DARK_COUNCIL)
        {
            LOG("force_rank", "force_rank.getForceSkillRank -- invalid council value of " + council);
            return -1;
        }
        String datatable;
        if (council == LIGHT_COUNCIL)
        {
            datatable = FRS_DATATABLE;
        }
        else 
        {
            datatable = FRS_DARK_DATATABLE;
        }
        int index = dataTableSearchColumnForString(skill, "skill", datatable);
        return index;
    }
    public static int getForceRankNumSlots(int rank) throws InterruptedException
    {
        int index = dataTableSearchColumnForInt(rank, "rank", FRS_DATATABLE);
        if (index == -1)
        {
            LOG("force_rank", "force_rank.getForceRankNumSlots -- faction invalid rank value of " + rank);
            return -1;
        }
        int num_slots = dataTableGetInt(FRS_DATATABLE, index, "num_slots");
        return num_slots;
    }
    public static int getForceRankMinXp(int rank) throws InterruptedException
    {
        int index = dataTableSearchColumnForInt(rank, "rank", FRS_DATATABLE);
        if (index == -1)
        {
            LOG("force_rank", "force_rank.getForceRankMinXp -- faction invalid rank value of " + rank);
            return -1;
        }
        int min_xp = dataTableGetInt(FRS_DATATABLE, index, "min_xp");
        return min_xp;
    }
    public static int getVotesForPlayer(obj_id player, obj_id enclave) throws InterruptedException
    {
        int playerRank = getForceRank(enclave, utils.getRealPlayerFirstName(player));
        if (playerRank < 0)
        {
            return 0;
        }
        String[] petitioners = getRankPetitioners(enclave, playerRank + 1);
        int[] votes = getRankPetitionerVotes(enclave, playerRank + 1);
        if (votes.length != petitioners.length)
        {
            LOG("force_rank", "force_rank::getVotesForPlayer: -> Votes.length != petitioners.length.  Returning zero votes for player " + utils.getRealPlayerFirstName(player));
            return 0;
        }
        String playerName = utils.getRealPlayerFirstName(player);
        int idx = utils.getElementPositionInArray(petitioners, playerName);
        if (idx < 0)
        {
            return 0;
        }
        return votes[idx];
    }
    public static void setVotesForPlayer(obj_id player, int numVotes, obj_id enclave) throws InterruptedException
    {
        if (!isIdValid(player) || player == null)
        {
            trace.log("force_rank", "force_rank::getVotesForPlayer: -> Can't set votes for null player.", player, trace.TL_ERROR_LOG | trace.TL_DEBUG);
            return;
        }
        int playerRank = getForceRank(enclave, utils.getRealPlayerFirstName(player));
        if (playerRank < 0)
        {
            return;
        }
        Vector petitioners = getRankPetitionersResizeable(enclave, playerRank + 1);
        Vector votes = getRankPetitionerVotesResizeable(enclave, playerRank + 1);
        if (votes.size() != petitioners.size())
        {
            trace.log("force_rank", "force_rank::getVotesForPlayer: -> Votes.length != petitioners.length.  Returning zero votes for player " + utils.getRealPlayerFirstName(player), player, trace.TL_ERROR_LOG | trace.TL_DEBUG);
            return;
        }
        String playerName = utils.getRealPlayerFirstName(player);
        int idx = utils.getElementPositionInArray(petitioners, playerName);
        if (idx > -1)
        {
            if (numVotes < 1)
            {
                utils.removeElementAt(petitioners, idx);
                utils.removeElementAt(votes, idx);
            }
            else 
            {
                votes.set(idx, new Integer(numVotes));
            }
        }
        else if (numVotes > 0)
        {
            utils.addElement(petitioners, playerName);
            utils.addElement(votes, numVotes);
        }
        setObjVar(enclave, VAR_VOTING_BASE + (playerRank + 1) + ".petition", petitioners);
        setObjVar(enclave, VAR_VOTING_BASE + (playerRank + 1) + ".votes", votes);
        return;
    }
    public static Vector getAllPlayerNamesInForceRank(obj_id enclave) throws InterruptedException
    {
        Vector allNames = new Vector();
        for (int h = 1; h < 12; h++)
        {
            String[] players = getPlayersInForceRank(enclave, h);
            if (players == null)
            {
                continue;
            }
            allNames = utils.concatArrays(allNames, players);
        }
        return allNames;
    }
    public static obj_id[] getAllPlayersInForceRank(obj_id enclave, int rank) throws InterruptedException
    {
        Vector playerIds = new Vector();
        playerIds.setSize(0);
        obj_id player = null;
        if (rank >= 0)
        {
            String[] players = getPlayersInForceRank(enclave, rank);
            if (players == null)
            {
                obj_id[] _playerIds = new obj_id[0];
                if (playerIds != null)
                {
                    _playerIds = new obj_id[playerIds.size()];
                    playerIds.toArray(_playerIds);
                }
                return _playerIds;
            }
            for (int i = 0; i < players.length; i++)
            {
                player = getPlayerIdFromFirstName(players[i]);
                if (player != null && isIdValid(player))
                {
                    utils.addElement(playerIds, player);
                }
            }
        }
        else 
        {
            for (int h = 1; h < 12; h++)
            {
                String[] players = getPlayersInForceRank(enclave, h);
                if (players == null)
                {
                    continue;
                }
                for (int s = 0; s < players.length; s++)
                {
                    player = getPlayerIdFromFirstName(players[s]);
                    if (player != null && isIdValid(player))
                    {
                        utils.addElement(playerIds, player);
                    }
                }
            }
        }
        obj_id[] _playerIds = new obj_id[0];
        if (playerIds != null)
        {
            _playerIds = new obj_id[playerIds.size()];
            playerIds.toArray(_playerIds);
        }
        return _playerIds;
    }
    public static int getVoteWeight(int voter_rank, int promotion_rank) throws InterruptedException
    {
        if (voter_rank < 1)
        {
            return -1;
        }
        if (promotion_rank < 1 || promotion_rank > 11)
        {
            LOG("force_rank", "force_rank.isRankVoteEligible -- invalid promotion_rank value of " + promotion_rank);
            return -1;
        }
        if (voter_rank == promotion_rank)
        {
            return 2;
        }
        if (promotion_rank > 0 && promotion_rank < 5)
        {
            if (voter_rank > 0 && voter_rank < 5)
            {
                return 1;
            }
        }
        if (promotion_rank > 4 && promotion_rank < 8)
        {
            if (voter_rank > 4 && voter_rank < 8)
            {
                return 1;
            }
        }
        if (promotion_rank == 8 || promotion_rank == 9)
        {
            if (voter_rank == 8 || voter_rank == 9)
            {
                return 1;
            }
        }
        if (promotion_rank == 10)
        {
            if (voter_rank == 11)
            {
                return 1;
            }
        }
        if (promotion_rank == 11)
        {
            if (voter_rank == 10)
            {
                return 1;
            }
        }
        return -1;
    }
    public static int getChallengeVoteWeight(int voter_rank, int challenge_rank) throws InterruptedException
    {
        if (voter_rank < 1)
        {
            return -1;
        }
        if (challenge_rank < 1 || challenge_rank > 11)
        {
            LOG("force_rank", "force_rank.isRankVoteEligible -- invalid challenge_rank value of " + challenge_rank);
            return -1;
        }
        int voter_tier = getForceTier(voter_rank);
        int challenge_tier = getForceTier(challenge_rank);
        if (voter_tier <= challenge_tier)
        {
            return 1;
        }
        else 
        {
            return 0;
        }
    }
    public static String[] getPlayersInForceRank(obj_id enclave, int rank) throws InterruptedException
    {
        if (!isIdValid(enclave))
        {
            LOG("force_rank", "force_rank.getPlayersInForceRank -- enclave is invalid.");
            return null;
        }
        String objvar_name = VAR_RANK_BASE + rank;
        if (hasObjVar(enclave, objvar_name))
        {
            String[] rank_list = getStringArrayObjVar(enclave, objvar_name);
            Vector final_list = new Vector();
            final_list.setSize(0);
            for (int i = 0; i < rank_list.length; i++)
            {
                if (rank_list[i] != null && rank_list[i].length() > 0 && !rank_list[i].equals(EMPTY_SLOT))
                {
                    final_list = utils.addElement(final_list, rank_list[i]);
                }
            }
            if (final_list.size() > 0)
            {
                String[] _final_list = new String[0];
                if (final_list != null)
                {
                    _final_list = new String[final_list.size()];
                    final_list.toArray(_final_list);
                }
                return _final_list;
            }
            else 
            {
                return null;
            }
        }
        else 
        {
            return null;
        }
    }
    public static Vector getPlayersInForceRankResizeable(obj_id enclave, int rank) throws InterruptedException
    {
        if (!isIdValid(enclave))
        {
            LOG("force_rank", "force_rank.getPlayersInForceRank -- enclave is invalid.");
            return null;
        }
        String objvar_name = VAR_RANK_BASE + rank;
        if (hasObjVar(enclave, objvar_name))
        {
            Vector rank_list = getResizeableStringArrayObjVar(enclave, objvar_name);
            if (rank_list != null)
            {
                int idx = rank_list.indexOf(EMPTY_SLOT);
                while (idx > -1)
                {
                    rank_list.removeElementAt(idx);
                    idx = rank_list.indexOf(EMPTY_SLOT);
                }
            }
            if (rank_list.size() > 0)
            {
                return rank_list;
            }
            else 
            {
                return null;
            }
        }
        return null;
    }
    public static boolean getPlayersInForceRank(obj_id source, int rank, int council, String handler) throws InterruptedException
    {
        if (!isIdValid(source))
        {
            LOG("force_rank", "force_rank.getPlayersInForceRank -- source is invalid.");
            return false;
        }
        if (rank < 0 || rank > 11)
        {
            LOG("force_rank", "force_rank.getPlayersInForceRank -- rank value of " + rank + " is invalid.");
            return false;
        }
        if (handler == null || handler.length() < 1)
        {
            LOG("force_rank", "force_rank.getPlayersInForceRank -- handler value of " + handler + " is invalid.");
            return false;
        }
        _updateForceRankData(source, council, 1, Integer.toString(rank), handler, false);
        return true;
    }
    public static boolean isPlayerEligibleForPromotion(obj_id player, int rank) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            LOG("force_rank", "force_rank.isPlayerEligibleForPromotion -- player is invalid.");
            return false;
        }
        if (rank < 0 || rank > 11)
        {
            LOG("force_rank", "force_rank.isPlayerEligibleForPromotion -- rank value of " + rank + " is invalid.");
            return false;
        }
        int experience = getExperiencePoints(player, FRS_XP);
        String skill_name = getForceRankSkill(rank, getCouncilAffiliation(player));
        if (skill_name == null)
        {
            LOG("force_rank", "force_rank.isPlayerEligibleForPromotion -- no skill found for rank " + rank);
            return false;
        }
        LOG("force_rank", "force_rank.isPlayerEligibleForPromotion -- " + skill_name);
        if (!skill.hasRequiredSkillsForSkillPurchase(player, skill_name))
        {
            return false;
        }
        else 
        {
            return skill.hasRequiredXpForSkillPurchase(player, skill_name);
        }
    }
    public static boolean getEnclaveObjId(obj_id source, int council, String handler) throws InterruptedException
    {
        if (!isIdValid(source))
        {
            LOG("force_rank", "force_rank.getEnclaveObjId -- source is invalid.");
            return false;
        }
        if (handler == null || handler.length() < 1)
        {
            LOG("force_rank", "force_rank.getEnclaveObjId -- handler value of " + handler + " is invalid.");
            return false;
        }
        _updateForceRankData(source, council, 6, "None", handler, false);
        return true;
    }
    public static Vector getRankMembersFromDictionary(dictionary enclave_data, int rank) throws InterruptedException
    {
        if (enclave_data == null)
        {
            LOG("force_rank", "force_rank.getRankMembersFromDictionary -- enclave data is null.");
            return null;
        }
        if (rank < 1 || rank > 11)
        {
            LOG("force_rank", "force_rank.getRankMembersFromDictionary -- rank value of " + rank + " is invalid.");
            return null;
        }
        String key_name = VAR_RANK_BASE + rank + "-";
        int slots = MAX_RANK_SLOTS;
        Vector rank_list = new Vector();
        rank_list.setSize(0);
        for (int i = 0; i < slots; i++)
        {
            if (enclave_data.containsKey(key_name + i))
            {
                String name = enclave_data.getString(key_name + i);
                if (name != null && name.length() > 0)
                {
                    rank_list = utils.addElement(rank_list, name);
                }
            }
        }
        return rank_list;
    }
    public static String[] getPvpBoardNames(obj_id enclave) throws InterruptedException
    {
        if (!isIdValid(enclave))
        {
            LOG("force_rank", "force_rank.getPvpBoardNames -- enclave is invalid.");
            return null;
        }
        if (hasObjVar(enclave, BATCH_VAR_PVPBOARD_NAME))
        {
            String[] names = utils.getStringBatchObjVar(enclave, BATCH_VAR_PVPBOARD_NAME);
            if (names == null)
            {
                return null;
            }
            Vector final_list = new Vector();
            final_list.setSize(0);
            for (int i = 0; i < names.length; i++)
            {
                if (names[i] != null && names[i].length() > 0 && !names[i].equals(EMPTY_SLOT))
                {
                    final_list = utils.addElement(final_list, names[i]);
                }
            }
            if (final_list.size() > 0)
            {
                String[] _final_list = new String[0];
                if (final_list != null)
                {
                    _final_list = new String[final_list.size()];
                    final_list.toArray(_final_list);
                }
                return _final_list;
            }
            else 
            {
                return null;
            }
        }
        else 
        {
            return null;
        }
    }
    public static int[] getPvpBoardRatings(obj_id enclave) throws InterruptedException
    {
        if (!isIdValid(enclave))
        {
            LOG("force_rank", "force_rank.getPvpBoardRatings -- enclave is invalid.");
            return null;
        }
        if (hasObjVar(enclave, BATCH_VAR_PVPBOARD_RATING))
        {
            int[] ratings = utils.getIntBatchObjVar(enclave, BATCH_VAR_PVPBOARD_RATING);
            if (ratings == null)
            {
                return null;
            }
            Vector final_list = new Vector();
            final_list.setSize(0);
            for (int i = 0; i < ratings.length; i++)
            {
                if (ratings[i] > 0)
                {
                    final_list = utils.addElement(final_list, ratings[i]);
                }
            }
            if (final_list.size() > 0)
            {
                int[] _final_list = new int[0];
                if (final_list != null)
                {
                    _final_list = new int[final_list.size()];
                    for (int _i = 0; _i < final_list.size(); ++_i)
                    {
                        _final_list[_i] = ((Integer)final_list.get(_i)).intValue();
                    }
                }
                return _final_list;
            }
            else 
            {
                return null;
            }
        }
        else 
        {
            return null;
        }
    }
    public static int getPvpRankingForPlayer(obj_id enclave, String player_name) throws InterruptedException
    {
        if (!isIdValid(enclave))
        {
            LOG("force_rank", "force_rank.getPvpRatingForPlayer -- enclave is invalid.");
            return -1;
        }
        if (player_name == null)
        {
            LOG("force_rank", "force_rank.getPvpRatingForPlayer -- player_name is null.");
            return -1;
        }
        Vector pvp_board_names = utils.getResizeableStringBatchObjVar(enclave, BATCH_VAR_PVPBOARD_NAME);
        return pvp_board_names.indexOf(player_name);
    }
    public static boolean getPvpBoardRatings(obj_id source, int council, String handler) throws InterruptedException
    {
        if (!isIdValid(source))
        {
            LOG("force_rank", "force_rank.getPlayersInForceRank -- source is invalid.");
            return false;
        }
        if (handler == null || handler.length() < 1)
        {
            LOG("force_rank", "force_rank.getPlayersInForceRank -- handler value of " + handler + " is invalid.");
            return false;
        }
        _updateForceRankData(source, council, 2, Integer.toString(0), handler, false);
        return true;
    }
    public static boolean updatePvpBoardData(obj_id player, int rating, int council, String handler) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            LOG("force_rank", "force_rank.updatePvpBoardData -- player is invalid.");
            return false;
        }
        if (handler == null || handler.length() < 1)
        {
            LOG("force_rank", "force_rank.updatePvpBoardData -- handler value of " + handler + " is invalid.");
            return false;
        }
        _updateForceRankData(player, council, 7, Integer.toString(rating), handler, true);
        return true;
    }
    public static dictionary adjustPvpBoardRankings(obj_id player, int player_rating, dictionary enclave_data) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            LOG("force_rank", "force_rank.adjustPvpBoardRankings -- player is invalid.");
            return null;
        }
        if (enclave_data == null)
        {
            LOG("force_rank", "force_rank.adjustPvpBoardRankings -- enclave_data is null.");
            return null;
        }
        Vector board_names = new Vector();
        board_names.setSize(0);
        Vector board_ratings = new Vector();
        board_ratings.setSize(0);
        for (int i = 0; i < PVP_BOARD_SIZE; i++)
        {
            String key_name = BATCH_VAR_PVPBOARD_NAME + i;
            String key_rating = BATCH_VAR_PVPBOARD_RATING + i;
            if (enclave_data.containsKey(key_name) && enclave_data.containsKey(key_rating))
            {
                String name = enclave_data.getString(key_name);
                int rating = enclave_data.getInt(key_rating);
                if (name != null && name.length() > 0 && !name.equals(EMPTY_SLOT) && rating > 0)
                {
                    board_names = utils.addElement(board_names, name);
                    board_ratings = utils.addElement(board_ratings, rating);
                }
            }
        }
        boolean empty_slots = board_names.size() < PVP_BOARD_SIZE;
        boolean on_board = false;
        int current_board_idx = board_names.indexOf(utils.getRealPlayerFirstName(player));
        if (current_board_idx != -1)
        {
            on_board = true;
        }
        if (!empty_slots && !on_board)
        {
            if (player_rating < ((Integer)board_ratings.get(board_ratings.size())).intValue())
            {
                return null;
            }
        }
        int new_board_idx = -1;
        for (int i = 0; i < board_ratings.size(); i++)
        {
            if (((Integer)board_ratings.get(i)).intValue() < player_rating)
            {
                new_board_idx = i;
                break;
            }
        }
        if (new_board_idx == -1)
        {
            if (!on_board && empty_slots)
            {
                board_names.add(utils.getRealPlayerFirstName(player));
                board_ratings.add(utils.getRealPlayerFirstName(player));
            }
            else 
            {
                return null;
            }
        }
        else 
        {
            if (on_board)
            {
                if (current_board_idx == new_board_idx)
                {
                    return null;
                }
                board_names.remove(current_board_idx);
                board_ratings.remove(current_board_idx);
            }
            board_names.add(new_board_idx, utils.getRealPlayerFirstName(player));
            board_ratings.add(new_board_idx, new Integer(player_rating));
        }
        if (board_names.size() > PVP_BOARD_SIZE)
        {
            board_names.setSize(PVP_BOARD_SIZE);
            board_ratings.setSize(PVP_BOARD_SIZE);
        }
        for (int i = 0; i < board_names.size(); i++)
        {
            String key_name = BATCH_VAR_PVPBOARD_NAME + i;
            String key_rating = BATCH_VAR_PVPBOARD_RATING + i;
            enclave_data.put(key_name, ((String)board_names.get(i)));
            enclave_data.put(key_rating, ((Integer)board_ratings.get(i)).intValue());
        }
        return enclave_data;
    }
    public static boolean sendRankMail(obj_id enclave, int rank, string_id subject, prose_package body) throws InterruptedException
    {
        LOG("force_rank", "force_rank.sendRankMail -- " + rank);
        if (!isIdValid(enclave))
        {
            LOG("force_rank", "force_rank.sendRankMail -- enclave is invalid.");
            return false;
        }
        if (rank < 1 || rank > 11)
        {
            LOG("force_rank", "force_rank.sendRankMail -- illegal value for rank: " + rank);
            return false;
        }
        String[] rank_list = getPlayersInForceRank(enclave, rank);
        if (rank_list != null)
        {
            for (int i = 0; i < rank_list.length; i++)
            {
                LOG("force_rank", "rank_list[" + i + "] ->" + rank_list[i]);
                utils.sendMail(subject, body, rank_list[i], "Enclave Records");
            }
        }
        return true;
    }
    public static boolean sendVoterMail(obj_id enclave, int rank, string_id subject, prose_package body) throws InterruptedException
    {
        if (!isIdValid(enclave))
        {
            LOG("force_rank", "force_rank.sendVoterMail -- enclave is invalid.");
            return false;
        }
        if (rank < 1 || rank > 11)
        {
            LOG("force_rank", "force_rank.sendVoterMail -- illegal value for rank: " + rank);
            return false;
        }
        for (int i = 0; i < 12; i++)
        {
            LOG("force_rank", "vote weight[" + i + "] ->" + getVoteWeight(i, rank));
            if (getVoteWeight(i, rank) > 0)
            {
                sendRankMail(enclave, i, subject, body);
            }
        }
        return true;
    }
    public static boolean sendChallengeVoterMail(obj_id enclave, int rank, string_id subject, prose_package body) throws InterruptedException
    {
        if (!isIdValid(enclave))
        {
            LOG("force_rank", "force_rank.sendChallengeVoterMail -- enclave is invalid.");
            return false;
        }
        if (rank < 1 || rank > 11)
        {
            LOG("force_rank", "force_rank.sendChallengeVoterMail -- illegal value for rank: " + rank);
            return false;
        }
        for (int i = 0; i < 12; i++)
        {
            if (getChallengeVoteWeight(i, rank) > 0)
            {
                sendRankMail(enclave, i, subject, body);
            }
        }
        return true;
    }
    public static boolean resetEnclaveData(obj_id enclave) throws InterruptedException
    {
        LOG("force_rank", "force_rank.resetEnclaveData");
        if (!isIdValid(enclave))
        {
            LOG("force_rank", "force_rank.resetEnclaveData -- enclave is invalid.");
            return false;
        }
        for (int i = 1; i < 12; i++)
        {
            int slots = force_rank.getForceRankNumSlots(i);
            if (slots > 0)
            {
                String[] rank_list = new String[slots];
                Arrays.fill(rank_list, EMPTY_SLOT);
                setObjVar(enclave, force_rank.VAR_RANK_BASE + i, rank_list);
            }
        }
        return true;
    }
    public static boolean replaceEnclaveClusterData(obj_id enclave, int lock_key) throws InterruptedException
    {
        LOG("force_rank", "force_rank.replaceEnclaveClusterData");
        if (!isIdValid(enclave))
        {
            LOG("force_rank", "force_rank.replaceEnclaveClusterData -- enclave is invalid.");
            return false;
        }
        int council = force_rank.getCouncilAffiliation(enclave);
        if (council == -1)
        {
            LOG("force_rank", "enclave_controller.replaceEnclaveData -- " + enclave + " has an invalid council value.");
            return false;
        }
        if (lock_key < 0)
        {
            lock_key = 0;
        }
        dictionary enclave_info = new dictionary();
        LOG("force_rank", "force_rank.replaceEnclaveClusterData -- 1");
        enclave_info.put(VAR_ENCLAVE, enclave);
        for (int i = 1; i < 12; i++)
        {
            String[] rank_members = getStringArrayObjVar(enclave, VAR_RANK_BASE + i);
            if (rank_members != null)
            {
                for (int j = 0; j < rank_members.length; j++)
                {
                    String key = force_rank.VAR_RANK_BASE + i + "-" + j;
                    enclave_info.put(key, rank_members[j]);
                }
            }
        }
        LOG("force_rank", "force_rank.replaceEnclaveClusterData -- 2");
        replaceClusterWideData(CLUSTER_DATA_NAME, STRING_CLUSTER_ENCLAVE_NAME + council, enclave_info, false, lock_key);
        return true;
    }
    public static boolean updateEnclaveClusterData(obj_id enclave, dictionary enclave_data) throws InterruptedException
    {
        LOG("force_rank", "force_rank.updateEnclaveClusterData");
        if (!isIdValid(enclave))
        {
            LOG("force_rank", "force_rank.replaceEnclaveData -- enclave is invalid.");
            return false;
        }
        if (enclave_data == null)
        {
            LOG("force_rank", "force_rank.replaceEnclaveData -- enclave_data is null for " + enclave);
            return false;
        }
        Object[] ranks = new Object[11];
        for (int i = 0; i < ranks.length; i++)
        {
            ranks[i] = new Vector();
        }
        String[] pvp_board_name = new String[PVP_BOARD_SIZE];
        Arrays.fill(pvp_board_name, EMPTY_SLOT);
        int[] pvp_board_rating = new int[PVP_BOARD_SIZE];
        java.util.Enumeration e = enclave_data.keys();
        while (e.hasMoreElements())
        {
            String key_name = (String)(e.nextElement());
            if (key_name.length() > 0)
            {
                if (key_name.startsWith(VAR_RANK_BASE))
                {
                    java.util.StringTokenizer st = new java.util.StringTokenizer(key_name, "-");
                    if (st.countTokens() != 2)
                    {
                        continue;
                    }
                    String prefix = st.nextToken();
                    String slot = st.nextToken();
                    int slot_number = utils.stringToInt(slot);
                    if (slot_number < 0)
                    {
                        continue;
                    }
                    java.util.StringTokenizer st2 = new java.util.StringTokenizer(prefix, VAR_RANK_BASE);
                    if (st2.countTokens() != 1)
                    {
                        continue;
                    }
                    String prefix_rank = st2.nextToken();
                    int rank = utils.stringToInt(prefix_rank);
                    if (rank < 1)
                    {
                        continue;
                    }
                    Vector edit_list = (Vector)(ranks[rank - 1]);
                    edit_list.add(enclave_data.getString(key_name));
                }
                if (key_name.startsWith(BATCH_VAR_PVPBOARD_NAME))
                {
                    java.util.StringTokenizer st = new java.util.StringTokenizer(key_name, BATCH_VAR_PVPBOARD_NAME);
                    if (st.countTokens() != 1)
                    {
                        continue;
                    }
                    String slot = st.nextToken();
                    int slot_number = utils.stringToInt(slot);
                    if (slot_number < 0)
                    {
                        continue;
                    }
                    pvp_board_name[slot_number] = enclave_data.getString(key_name);
                    pvp_board_rating[slot_number] = enclave_data.getInt(BATCH_VAR_PVPBOARD_RATING + slot);
                }
            }
        }
        for (int i = 0; i < ranks.length; i++)
        {
            Object obj_list = ranks[i];
            Vector vec_list = (Vector)obj_list;
            String[] rank_list = new String[vec_list.size()];
            vec_list.toArray(rank_list);
            setObjVar(enclave, VAR_RANK_BASE + (i + 1), rank_list);
        }
        return true;
    }
    public static boolean backupEnclaveData(obj_id enclave) throws InterruptedException
    {
        if (!isIdValid(enclave))
        {
            LOG("force_rank", "force_rank.backupEnclaveData -- enclave is invalid.");
            return false;
        }
        for (int i = 1; i < 12; i++)
        {
            String[] rank_list = getStringArrayObjVar(enclave, VAR_RANK_BASE + i);
            if (rank_list != null && rank_list.length > 0)
            {
                setObjVar(enclave, "bk_" + VAR_RANK_BASE + i, rank_list);
            }
        }
        setObjVar(enclave, VAR_BACKUP_TIME, getGameTime());
        return true;
    }
    public static boolean restoreEnclaveData(obj_id enclave) throws InterruptedException
    {
        if (!isIdValid(enclave))
        {
            LOG("force_rank", "force_rank.restoreEnclaveData -- enclave is invalid.");
            return false;
        }
        for (int i = 1; i < 12; i++)
        {
            String[] rank_list = getStringArrayObjVar(enclave, "bk_" + VAR_RANK_BASE + i);
            if (rank_list != null && rank_list.length > 0)
            {
                setObjVar(enclave, VAR_RANK_BASE + i, rank_list);
            }
        }
        return true;
    }
    public static boolean logEnclaveData(obj_id enclave) throws InterruptedException
    {
        if (!isIdValid(enclave))
        {
            LOG("force_rank", "force_rank.logEnclaveData -- enclave is invalid.");
            return false;
        }
        int council = getCouncilAffiliation(enclave);
        if (council == -1)
        {
            LOG("force_rank", "force_rank.logEnclaveData -- " + enclave + " does not have a council affiliation.");
            return false;
        }
        for (int i = 0; i < 12; i++)
        {
            String[] rank_list = getPlayersInForceRank(enclave, i);
            if (rank_list != null)
            {
                for (int j = 0; j < rank_list.length; j++)
                {
                    if (rank_list[j] != null)
                    {
                        obj_id player = getPlayerIdFromFirstName(rank_list[j]);
                        CustomerServiceLog("force_rank", COUNCIL_NAMES[council] + " Data: " + rank_list[j] + "(" + player + ") is at rank " + i);
                    }
                }
            }
        }
        return true;
    }
    public static dictionary removeExtraSlotFromRankList(dictionary enclave_data, int rank) throws InterruptedException
    {
        if (enclave_data == null)
        {
            LOG("force_rank", "force_rank.removeEntryFromRankList -- enclave is null.");
            return null;
        }
        int max_slots = getForceRankNumSlots(rank);
        if (max_slots < 1)
        {
            LOG("force_rank", "force_rank.removeEntryFromRankList -- invalid max slot data from " + rank);
            return null;
        }
        Vector rank_list = getRankMembersFromDictionary(enclave_data, rank);
        if (rank_list != null)
        {
            int slots_to_remove = rank_list.size() - max_slots;
            int rank_length = rank_list.size();
            if (slots_to_remove < 1)
            {
                LOG("force_rank", "force_rank.removeEntryFromRankList -- there are not slots to remove.");
                return null;
            }
            String key_name = force_rank.VAR_RANK_BASE + rank + "-";
            int data_idx = 0;
            for (int i = 0; i < rank_list.size(); i++)
            {
                if (slots_to_remove > 0)
                {
                    if (((String)rank_list.get(i)) == null || ((String)rank_list.get(i)).length() < 1 || ((String)rank_list.get(i)).equals(EMPTY_SLOT))
                    {
                        rank_length--;
                        slots_to_remove--;
                        continue;
                    }
                }
                enclave_data.put(key_name + data_idx, ((String)rank_list.get(i)));
                data_idx++;
            }
            for (int i = rank_length; i <= MAX_RANK_SLOTS; i++)
            {
                String key_remove = force_rank.VAR_RANK_BASE + rank + "-" + i;
                if (enclave_data.containsKey(key_remove))
                {
                    enclave_data.remove(key_remove);
                }
            }
            return enclave_data;
        }
        else 
        {
            LOG("force_rank", "force_rank.removeEntryFromRankList -- rank_list is null.");
            return null;
        }
    }
    public static boolean grantRankItems(obj_id player, boolean retroactive) throws InterruptedException
    {
        return false;
    }
    public static boolean grantRankItems(obj_id player) throws InterruptedException
    {
        return grantRankItems(player, false);
    }
    public static boolean performFRSAttack(obj_id player, obj_id defender, hit_result results, weapon_data weapondat) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            LOG("force_rank", "force_rank.performFRSAttack -- player is invalid.");
            return false;
        }
        if (!isIdValid(defender))
        {
            LOG("force_rank", "force_rank.performFRSAttack -- defender is invalid.");
            return false;
        }
        if (results == null)
        {
            LOG("force_rank", "force_rank.performFRSAttack -- results is null.");
            return false;
        }
        if (weapondat == null)
        {
            LOG("force_rank", "force_rank.performFRSAttack -- weapondat is null.");
            return false;
        }
        if (!isPlayer(player))
        {
            return false;
        }
        int council = getCouncilAffiliation(player);
        if (council < 1)
        {
            LOG("force_rank", "force_rank.performFRSAttack -- " + player + " has an invalid council affiliation.");
            return false;
        }
        int damage = results.damage;
        obj_id weapon_id = weapondat.id;
        if (council == 1)
        {
            int envy_rating = getEnhancedSkillStatisticModifier(player, "frs_dark_envy");
            if (envy_rating > 0)
            {
                performFRSAttackEnvy(player, defender, weapon_id, damage, envy_rating);
            }
            int suffering_rating = getEnhancedSkillStatisticModifier(player, "frs_dark_suffering");
            if (suffering_rating > 0)
            {
                performFRSAttackSuffering(player, defender, weapon_id, damage, suffering_rating);
            }
        }
        else 
        {
            int vigilance_rating = getEnhancedSkillStatisticModifier(player, "frs_light_vigilance");
            if (vigilance_rating > 0)
            {
                performFRSAttackVigilance(player, defender, weapon_id, damage, vigilance_rating);
            }
            int wisdom_rating = getEnhancedSkillStatisticModifier(player, "frs_light_wisdom");
            if (wisdom_rating > 0)
            {
                performFRSAttackWisdom(player, defender, weapon_id, damage, wisdom_rating);
            }
        }
        return true;
    }
    public static boolean performFRSAttackEnvy(obj_id player, obj_id defender, obj_id weapon, int damage, int rating) throws InterruptedException
    {
        if (!isJedi(player))
        {
            return false;
        }
        if (damage < 1)
        {
            return false;
        }
        if (rating > 100)
        {
            rating = 100;
        }
        int drain = (int)(damage * ((float)rating / 200.0f));
        if (drain < 1)
        {
            drain = 1;
        }
        int gain = (int)(drain * ((float)rating / 100.0f));
        if (gain < 1)
        {
            gain = 1;
        }
        if (!jedi.hasForcePower(defender, drain))
        {
            drain = getForcePower(defender);
            if (drain == 0)
            {
                return false;
            }
        }
        alterForcePower(defender, -drain);
        alterForcePower(player, gain);
        prose_package pp = new prose_package();
        pp.stringId = new string_id("cbt_spam", "frs_dark_envy_drain");
        pp.actor.set(player);
        pp.target.set(defender);
        pp.digitInteger = drain;
        combat.sendCombatSpamMessageProse(player, defender, pp, true, true, false);
        pp.stringId = new string_id("cbt_spam", "frs_dark_envy_gain");
        pp.digitInteger = gain;
        combat.sendCombatSpamMessageProse(player, defender, pp, true, false, false);
        playClientEffectLoc(player, "clienteffect/frs_dark_envy.cef", getLocation(defender), 1.5f);
        return true;
    }
    public static boolean performFRSAttackSuffering(obj_id player, obj_id defender, obj_id weapon, int damage, int rating) throws InterruptedException
    {
        if (isJedi(defender))
        {
            if (getForcePower(defender) > 0)
            {
                return false;
            }
        }
        int drain = (int)(damage * ((float)rating / 300.0f));
        if (drain < 1)
        {
            drain = 1;
        }
        int current_val = 0;
        int[] attribs = 
        {
            1,
            2,
            4,
            5
        };
        for (int i = 0; i < attribs.length; i++)
        {
            int current_attrib = getAttrib(defender, attribs[i]);
            if (current_attrib - 1 <= drain)
            {
                drain = current_attrib - 1;
                if (drain < 1)
                {
                    continue;
                }
            }
            if (hasAttribModifier(defender, "force_rank.suffering_" + attribs[i]))
            {
                attrib_mod[] mods = getAttribModifiers(defender, attribs[i]);
                for (int j = 0; j < mods.length; j++)
                {
                    String name = mods[j].getName();
                    if (name != null && name.equals("force_rank.suffering_" + attribs[i]))
                    {
                        int val = mods[j].getValue();
                        if (val != 0)
                        {
                            removeAttribOrSkillModModifier(defender, "force_rank.suffering_" + attribs[i]);
                            current_val = Math.abs(val);
                        }
                        break;
                    }
                }
            }
            addAttribModifier(defender, "force_rank.suffering_" + attribs[i], attribs[i], -(drain + current_val), DEBUFF_DURATION, 0.0f, 0.0f, false, false, false);
        }
        addBuffIcon(defender, "force_rank.suffering", DEBUFF_DURATION);
        prose_package pp = new prose_package();
        pp.stringId = new string_id("cbt_spam", "frs_dark_suffering");
        pp.actor.set(player);
        pp.target.set(defender);
        pp.digitInteger = drain;
        combat.sendCombatSpamMessageProse(player, defender, pp, true, true, false);
        return true;
    }
    public static boolean performFRSAttackVengeance(obj_id player, obj_id defender, int damage, int rating) throws InterruptedException
    {
        playClientEffectLoc(player, "clienteffect/frs_dark_vengeance.cef", getLocation(defender), 1.5f);
        return true;
    }
    public static boolean performFRSAttackVigilance(obj_id player, obj_id defender, obj_id weapon, int damage, int rating) throws InterruptedException
    {
        if (!isJedi(player))
        {
            return false;
        }
        if (damage < 1)
        {
            return false;
        }
        if (rating > 100)
        {
            rating = 100;
        }
        int drain = (int)(damage * ((float)rating / 100.0f));
        if (drain < 1)
        {
            drain = 1;
        }
        if (!jedi.hasForcePower(defender, drain))
        {
            drain = getForcePower(defender);
            if (drain == 0)
            {
                return false;
            }
        }
        alterForcePower(defender, -drain);
        prose_package pp = new prose_package();
        pp.stringId = new string_id("cbt_spam", "frs_light_vigilance");
        pp.actor.set(player);
        pp.target.set(defender);
        pp.digitInteger = drain;
        combat.sendCombatSpamMessageProse(player, defender, pp, true, true, false);
        playClientEffectLoc(player, "clienteffect/frs_light_vigilance.cef", getLocation(defender), 1.5f);
        return true;
    }
    public static boolean performFRSAttackWisdom(obj_id player, obj_id defender, obj_id weapon, int damage, int rating) throws InterruptedException
    {
        if (isJedi(defender))
        {
            if (getForcePower(defender) > 0)
            {
                return false;
            }
        }
        int drain = (int)(damage * ((float)rating / 200.0f));
        if (drain < 1)
        {
            drain = 1;
        }
        int gain = (int)(drain * ((float)rating / 100.0f));
        if (gain < 1)
        {
            gain = 1;
        }
        for (int i = 0; i < 3; i++)
        {
            addAttribModifier(defender, i * 3, -drain, 0.0f, 0.0f, MOD_POOL);
            addAttribModifier(player, i * 3, gain, 0.0f, 0.0f, MOD_POOL);
        }
        prose_package pp = new prose_package();
        pp.stringId = new string_id("cbt_spam", "frs_light_wisdom_drain");
        pp.actor.set(player);
        pp.target.set(defender);
        pp.digitInteger = drain;
        combat.sendCombatSpamMessageProse(player, defender, pp, true, true, false);
        pp.stringId = new string_id("cbt_spam", "frs_light_wisdom_gain");
        pp.digitInteger = gain;
        combat.sendCombatSpamMessageProse(player, defender, pp, true, false, false);
        playClientEffectLoc(player, "clienteffect/frs_light_wisdom.cef", getLocation(defender), 1.5f);
        return true;
    }
    public static boolean performFRSAttackSerenity(obj_id player, obj_id defender, int damage, int rating) throws InterruptedException
    {
        int drain = (int)(damage * ((float)rating / 400.0f));
        if (drain < 1)
        {
            drain = 1;
        }
        int current_val = 0;
        int[] attribs = 
        {
            1,
            3,
            6
        };
        for (int i = 0; i < attribs.length; i++)
        {
            int current_attrib = getAttrib(defender, attribs[i]);
            if (current_attrib - 1 <= drain)
            {
                drain = current_attrib - 1;
                if (drain < 1)
                {
                    continue;
                }
            }
            if (hasAttribModifier(defender, "force_rank.serenity_" + attribs[i]))
            {
                attrib_mod[] mods = getAttribModifiers(defender, attribs[i]);
                for (int j = 0; j < mods.length; j++)
                {
                    String name = mods[j].getName();
                    if (name != null && name.equals("force_rank.serenity_" + attribs[i]))
                    {
                        int val = mods[j].getValue();
                        if (val != 0)
                        {
                            removeAttribOrSkillModModifier(defender, "force_rank.serenity_" + attribs[i]);
                            current_val = Math.abs(val);
                        }
                        break;
                    }
                }
            }
            addAttribModifier(defender, "force_rank.serenity_" + attribs[i], attribs[i], -(drain + current_val), DEBUFF_DURATION, 0.0f, 0.0f, false, false, false);
        }
        addBuffIcon(defender, "force_rank.serenity", DEBUFF_DURATION);
        prose_package pp = new prose_package();
        pp.stringId = new string_id("cbt_spam", "frs_light_serenity");
        pp.actor.set(player);
        pp.target.set(defender);
        pp.digitInteger = drain;
        combat.sendCombatSpamMessageProse(player, defender, pp, true, true, false);
        playClientEffectLoc(player, "clienteffect/frs_light_serenity.cef", getLocation(defender), 1.5f);
        return true;
    }
    public static void handleCondemnedPlayerDeath(obj_id darkEnclave, dictionary params) throws InterruptedException
    {
        trace.log("force_rank", "force_rank::handledCondemnedPlayerDeath: -> Enter method");
        if (!params.containsKey("victim") || !params.containsKey("totalDamageToVictim"))
        {
            trace.log("force_rank", "force_rank::handleCondemnedPlayerDeath: -> Missing vital params 'victim' and 'totalDamageToVictim'. Not validating Dark Jedi PvP ACTION death.", darkEnclave, trace.TL_ERROR_LOG | trace.TL_DEBUG);
            return;
        }
        obj_id victim = params.getObjId("victim");
        params.remove("victim");
        int totalDamageTally = params.getInt("totalDamageToVictim");
        params.remove("totalDamageToVictim");
        java.util.Enumeration pKeys = params.keys();
        obj_id[] winners = new obj_id[params.size()];
        float[] contributions = new float[winners.length];
        int s = 0;
        float totalDarkJediDeathContrib = 0;
        while (pKeys.hasMoreElements())
        {
            obj_id key = (obj_id)pKeys.nextElement();
            winners[s] = key;
            contributions[s] = params.getFloat(key);
            totalDarkJediDeathContrib += contributions[s];
            s++;
        }
        Vector suddenDeathWinners = new Vector();
        suddenDeathWinners.setSize(0);
        Vector suddenDeathContribs = new Vector();
        suddenDeathContribs.setSize(0);
        boolean suddenDeath = false;
        boolean demoteToZero = false;
        obj_id curPlayer = null;
        float totSDContribs = 0;
        dictionary suddenDeathData = getAndValidatePvPActionData(ACTION_CANDIDATE_SUDDEN_DEATH, darkEnclave);
        dictionary vendettaData = getAndValidatePvPActionData(ACTION_VENDETTA, darkEnclave);
        dictionary banishmentData = getAndValidatePvPActionData(ACTION_BANISHMENT, darkEnclave);
        dictionary purgeData = getAndValidatePvPActionData(ACTION_PURGE_COUNCIL, darkEnclave);
        for (int i = 0; i < winners.length; i++)
        {
            curPlayer = winners[i];
            if (suddenDeathData != null)
            {
                if (bothArePartOfSameSuddenDeathRank(victim, curPlayer, darkEnclave, suddenDeathData))
                {
                    suddenDeath = true;
                    utils.addElement(suddenDeathWinners, curPlayer);
                    utils.addElement(suddenDeathContribs, contributions[i]);
                    totSDContribs += contributions[i];
                }
            }
            if (vendettaData != null && !demoteToZero)
            {
                if (isPartOfSameVendetta(victim, curPlayer, vendettaData))
                {
                    demoteToZero = true;
                }
            }
        }
        if (banishmentData != null && !demoteToZero)
        {
            if (isPvPActionVictim(victim, banishmentData))
            {
                demoteToZero = true;
            }
        }
        if (purgeData != null && !demoteToZero)
        {
            if (isPvPActionVictim(victim, purgeData))
            {
                demoteToZero = true;
            }
        }
        if (suddenDeath)
        {
            float curContrib = 0.0f;
            int victimsVotes = getVotesForPlayer(victim, darkEnclave);
            int curWinnerVotes = 0;
            if (victimsVotes > 0)
            {
                setVotesForPlayer(victim, 0, darkEnclave);
                _clearPvPActionDataForPlayer(victim, new String[]
                {
                    VAR_VICTIMS
                }, ACTION_CANDIDATE_SUDDEN_DEATH, darkEnclave);
                sendSystemMessage(victim, new string_id("pvp_rating", "dark_jedi_kill_lost_votes"));
                trace.log("force_rank", utils.getRealPlayerFirstName(victim) + " has lost " + victimsVotes + " votes for dying during the voting sudden-death period.", victim, trace.TL_CS_LOG | trace.TL_DEBUG);
                int votesGained = 0;
                for (int x = 0; x < suddenDeathWinners.size(); x++)
                {
                    curContrib = ((Float)suddenDeathContribs.get(x)).floatValue() / totSDContribs;
                    curWinnerVotes = getVotesForPlayer(((obj_id)suddenDeathWinners.get(x)), darkEnclave);
                    votesGained = (int)Math.floor((float)victimsVotes * curContrib);
                    trace.log("force_rank", utils.getRealPlayerFirstName(((obj_id)suddenDeathWinners.get(x))) + " has gained " + votesGained + " for killing " + utils.getRealPlayerFirstName(victim), ((obj_id)suddenDeathWinners.get(x)), trace.TL_CS_LOG | trace.TL_DEBUG);
                    setVotesForPlayer(((obj_id)suddenDeathWinners.get(x)), curWinnerVotes + votesGained, darkEnclave);
                    prose_package pp = new prose_package();
                    pp.target.set(utils.getRealPlayerFirstName(victim));
                    pp.digitInteger = votesGained;
                    pp.stringId = new string_id("pvp_rating", "dark_jedi_kill_won_votes");
                    sendSystemMessageProse(((obj_id)suddenDeathWinners.get(x)), pp);
                }
            }
        }
        if (demoteToZero)
        {
            demoteDeadPlayerToRankZero(victim, darkEnclave);
        }
        return;
    }
    public static void moveEnclavedPlayerToNeutralCell(obj_id player) throws InterruptedException
    {
        if (arena.isPlayerInEnclave(player))
        {
            location curLoc = getLocation(player);
            obj_id curCell = curLoc.cell;
            obj_id enclave = getEnclave(player);
            obj_id destCell = null;
            float x = 0.0f;
            float y = 0.0f;
            float z = 0.0f;
            String cellName = "";
            if (curCell != null)
            {
                if (checkCellPermission(player, curCell, true))
                {
                }
            }
            if (force_rank.getCouncilAffiliation(player) == force_rank.DARK_COUNCIL)
            {
                cellName = "antechamber";
                x = 0.0f;
                y = -43.42f;
                z = -31.26f;
            }
            else 
            {
                cellName = "lobby";
                x = -0.02f;
                y = -19.03f;
                z = 35.41f;
            }
            String[] enclaveRooms = getCellNames(enclave);
            if (utils.getElementPositionInArray(enclaveRooms, cellName) > -1)
            {
                destCell = getCellId(enclave, cellName);
                if (isIdValid(destCell))
                {
                    location newloc = new location(x, y, z);
                    newloc.cell = destCell;
                    setLocation(player, newloc);
                }
            }
        }
    }
    public static String getEnclaveAligmentString(obj_id enclave) throws InterruptedException
    {
        if (DEBUGGING)
        {
            return "test";
        }
        int alignmentVal = getCouncilAffiliation(enclave);
        if (alignmentVal == -1)
        {
            trace.log("force_rank", "force_rank.getEnclaveAligmentString -- invalid coucil value on enclave " + enclave, enclave, trace.TL_ERROR_LOG | trace.TL_DEBUG);
            return null;
        }
        String alignment;
        if (alignmentVal == force_rank.DARK_COUNCIL)
        {
            alignment = "dark";
        }
        else 
        {
            alignment = "light";
        }
        return alignment;
    }
    public static void updateAllRoomPermissions(obj_id enclave) throws InterruptedException
    {
        String alignment = getEnclaveAligmentString(enclave);
        if (alignment == null || alignment.length() < 1)
        {
            trace.log("force_rank", "...force_rank::updateRoomPermissions -> Error retrieving enclave alignment.  Room permissions not updated.", enclave, trace.TL_ERROR_LOG | trace.TL_DEBUG);
            return;
        }
        String[] commonsAreaCells = dataTableGetStringColumn(JEDI_ROOM_PERMISSIONS_TABLE, alignment + DATA_COLUMN_COMMUNITY_CELLS);
        String[] enclaveRooms = getCellNames(enclave);
        String columnName = "";
        int[] allowedRanks = new int[0];
        for (int i = 0; i < enclaveRooms.length; i++)
        {
            if (utils.getElementPositionInArray(commonsAreaCells, enclaveRooms[i]) > -1)
            {
                continue;
            }
            columnName = alignment + "_" + enclaveRooms[i];
            allowedRanks = dataTableGetIntColumnNoDefaults(JEDI_ROOM_PERMISSIONS_TABLE, columnName);
            if (allowedRanks == null || allowedRanks.length < 1)
            {
                continue;
            }
            adjustPermissionListForRoom(enclave, enclaveRooms[i], allowedRanks);
        }
        return;
    }
    public static Vector playerNamesToIds(String[] playerNames) throws InterruptedException
    {
        Vector players = new Vector();
        players.setSize(0);
        obj_id player = null;
        for (int i = 0; i < playerNames.length; i++)
        {
            player = getPlayerIdFromFirstName(playerNames[i]);
            if (isIdValid(player) && player != null)
            {
                utils.addElement(players, player);
            }
        }
        return players;
    }
    public static void adjustPermissionListForRoom(obj_id enclave, String cellName, int[] allowedRanks) throws InterruptedException
    {
        obj_id cellId = getCellId(enclave, cellName);
        if (!isIdValid(cellId))
        {
            trace.log("force_rank", "...enclave_gate_keeper::adjustPermissionsListForRoom -> Bunk cellId for cell " + cellName + ". Permissions list not updated.", enclave, trace.TL_ERROR_LOG | trace.TL_DEBUG);
            return;
        }
        clearCellAllowList(cellId);
        String[] allowedPlayers = null;
        for (int i = 0; i < allowedRanks.length; i++)
        {
            allowedPlayers = force_rank.getPlayersInForceRank(enclave, allowedRanks[i]);
            if (allowedPlayers == null || allowedPlayers.length < 1)
            {
                continue;
            }
            for (int x = 0; x < allowedPlayers.length; x++)
            {
                permissionsAddAllowed(cellId, allowedPlayers[x]);
            }
        }
        return;
    }
    public static void setAllCellsPrivate(obj_id enclave) throws InterruptedException
    {
        String[] cellNames = getCellNames(enclave);
        obj_id cellId = null;
        if (cellNames != null)
        {
            for (int i = 0; i < cellNames.length; i++)
            {
                cellId = getCellId(enclave, cellNames[i]);
                permissionsMakePrivate(cellId);
                clearCellBanList(cellId);
                clearCellAllowList(cellId);
            }
        }
        return;
    }
    public static void clearCellBanList(obj_id cell) throws InterruptedException
    {
        String[] banned = permissionsGetBanned(cell);
        if (banned == null || banned.length < 1)
        {
            return;
        }
        for (int i = 0; i < banned.length; i++)
        {
            permissionsRemoveBanned(cell, banned[i]);
        }
        return;
    }
    public static void clearCellAllowList(obj_id cell) throws InterruptedException
    {
        String[] allowed = permissionsGetAllowed(cell);
        if (allowed == null || allowed.length < 1)
        {
            return;
        }
        for (int i = 0; i < allowed.length; i++)
        {
            permissionsRemoveAllowed(cell, allowed[i]);
        }
        return;
    }
    public static void setCommonsRoomsToPublic(obj_id enclave) throws InterruptedException
    {
        String alignment = getEnclaveAligmentString(enclave);
        if (alignment == null || alignment.length() < 1)
        {
            trace.log("force_rank", "force_rank::setCommonRoomsToPublic -> Error retrieving enclave alignment.  Commons Area cells permissions not updated.", enclave, trace.TL_ERROR_LOG | trace.TL_DEBUG);
            return;
        }
        String[] commonsAreaCells = dataTableGetStringColumn(JEDI_ROOM_PERMISSIONS_TABLE, alignment + DATA_COLUMN_COMMUNITY_CELLS);
        if (commonsAreaCells == null || commonsAreaCells.length < 1)
        {
            trace.log("force_rank", "force_rank::setCommonsRoomsToPublic -> No commons area cells found for " + alignment + " enclave. All rooms remain private.", enclave, trace.TL_WARNING | trace.TL_DEBUG);
            return;
        }
        obj_id cellId = null;
        for (int i = 0; i < commonsAreaCells.length; i++)
        {
            cellId = getCellId(enclave, commonsAreaCells[i]);
            if (!isIdValid(cellId))
            {
                trace.log("force_rank", "force_rank::setCommonRoomsToPublic -> Bunk Commons Area Cell ID for cell name " + commonsAreaCells[i] + ". Cell not set to public.", enclave, trace.TL_WARNING | trace.TL_DEBUG);
            }
            permissionsMakePublic(cellId);
        }
        return;
    }
    public static boolean checkCellPermission(obj_id item, obj_id cell, boolean silent) throws InterruptedException
    {
        if (isGod(item))
        {
            sendSystemMessageTestingOnly(item, "god access granted to this area");
            return true;
        }
        if (!isMob(item))
        {
            return true;
        }
        obj_id enclave = getTopMostContainer(cell);
        if (!hasScript(enclave, force_rank.SCRIPT_ENCLAVE_CONTROLLER))
        {
            trace.log("force_rank", "force_rank::checkCellPermission -- " + enclave + " is not an enclave building.", enclave, trace.TL_ERROR_LOG | trace.TL_DEBUG);
            if (!silent)
            {
                sendSystemMessage(item, new string_id("pvp_rating", "enclave_deny_entry"));
            }
            return false;
        }
        String alignment = force_rank.getEnclaveAligmentString(enclave);
        String cellName = getCellName(cell);
        String columnName = alignment + "_" + cellName;
        if (alignment == null || alignment.length() < 1)
        {
            trace.log("force_rank", "force_rank::checkCellPermission-> Error retrieving enclave alignment.", enclave, trace.TL_ERROR_LOG | trace.TL_DEBUG);
            if (!silent)
            {
                sendSystemMessage(item, new string_id("pvp_rating", "enclave_deny_entry"));
            }
            return false;
        }
        if ((isIdValid(item)) && (isPlayer(item)) && (force_rank.isForceRanked(item)) && (force_rank.isPlayersEnclave(enclave, item)))
        {
            String[] commonsAreaCells = dataTableGetStringColumn(force_rank.JEDI_ROOM_PERMISSIONS_TABLE, alignment + force_rank.DATA_COLUMN_COMMUNITY_CELLS);
            if (utils.getElementPositionInArray(commonsAreaCells, cellName) > -1)
            {
                pet_lib.packAllCurrentPets(item, new string_id("pvp_rating", "enclave_deny_pet_entry"));
                return true;
            }
            else 
            {
                int[] allowedRanks = dataTableGetIntColumnNoDefaults(force_rank.JEDI_ROOM_PERMISSIONS_TABLE, columnName);
                int rank = force_rank.getForceRank(item);
                if (utils.getElementPositionInArray(allowedRanks, rank) >= 0)
                {
                    pet_lib.packAllCurrentPets(item, new string_id("pvp_rating", "enclave_deny_pet_entry"));
                    return true;
                }
            }
        }
        else 
        {
            expelFromBuilding(item);
        }
        if (!silent)
        {
            sendSystemMessage(item, new string_id("pvp_rating", "enclave_deny_entry"));
        }
        return false;
    }
    public static void setCellScripts(obj_id enclave) throws InterruptedException
    {
        String[] enclaveRooms = getCellNames(enclave);
        obj_id cellId = null;
        for (int i = 0; i < enclaveRooms.length; i++)
        {
            cellId = getCellId(enclave, enclaveRooms[i]);
            if (!isIdValid(cellId))
            {
                continue;
            }
            attachScript(cellId, "systems.gcw.enclave_cell");
        }
        return;
    }
    public static void makeAllCellsPublic(obj_id enclave) throws InterruptedException
    {
        String[] enclaveRooms = getCellNames(enclave);
        obj_id cellId = null;
        for (int i = 0; i < enclaveRooms.length; i++)
        {
            cellId = getCellId(enclave, enclaveRooms[i]);
            if (!isIdValid(cellId))
            {
                continue;
            }
            permissionsMakePublic(cellId);
        }
    }
    public static void refreshAllRoomPermissions(obj_id enclave) throws InterruptedException
    {
        setAllCellsPrivate(enclave);
        setCommonsRoomsToPublic(enclave);
        updateAllRoomPermissions(enclave);
    }
    public static void makePlayersPermaEnemies(obj_id player1, obj_id player2) throws InterruptedException
    {
        trace.log("force_rank", "Setting PEF for " + player1 + " and " + player2 + " - now enemies.", null, trace.TL_CS_LOG | trace.TL_DEBUG);
        pvpSetPermanentPersonalEnemyFlag(player1, player2);
        pvpSetPermanentPersonalEnemyFlag(player2, player1);
        return;
    }
    public static void makePlayersPermaFriends(obj_id player1, obj_id player2) throws InterruptedException
    {
        trace.log("force_rank", "Making players " + utils.getRealPlayerFirstName(player1) + " and " + utils.getRealPlayerFirstName(player2) + " friends. (not really)", null, trace.TL_DEBUG);
        return;
    }
    public static void makePlayerEnemyOfGroup(obj_id player, obj_id[] enemyGroup) throws InterruptedException
    {
        if (enemyGroup == null)
        {
            return;
        }
        for (int i = 0; i < enemyGroup.length; i++)
        {
            if (player != enemyGroup[i])
            {
                makePlayersPermaEnemies(player, enemyGroup[i]);
            }
        }
        return;
    }
    public static void makePlayerFriendsWithGroup(obj_id player, obj_id[] friendGroup) throws InterruptedException
    {
        if (friendGroup == null)
        {
            return;
        }
        for (int i = 0; i < friendGroup.length; i++)
        {
            if (player != friendGroup[i])
            {
                makePlayersPermaFriends(player, friendGroup[i]);
            }
        }
        return;
    }
    public static void makePlayerGroupsEnemies(obj_id[] group1, obj_id[] group2) throws InterruptedException
    {
        if (group1 == null || group2 == null)
        {
            return;
        }
        for (int i = 0; i < group1.length; i++)
        {
            makePlayerEnemyOfGroup(group1[i], group2);
        }
        return;
    }
    public static dictionary packageActionData(Vector initz, Vector victz, Vector startz) throws InterruptedException
    {
        dictionary d = new dictionary();
        if (initz == null || initz.size() < 1 || victz == null || victz.size() < 1 || startz == null || startz.size() < 1)
        {
            return d;
        }
        d.put("initiators", initz);
        d.put("victims", victz);
        d.put("times", startz);
        return d;
    }
    public static void setPvPActionData(dictionary data, String pvpAction, obj_id darkEnclave) throws InterruptedException
    {
        if (!isIdValid(darkEnclave))
        {
            trace.log("force_rank", "force_rank::setPvPActionData -> darkEnclave ID not valid.  Data not written.", darkEnclave, trace.TL_ERROR_LOG | trace.TL_DEBUG);
            return;
        }
        if (getCouncilAffiliation(darkEnclave) != DARK_COUNCIL)
        {
            trace.log("force_rank", "force_rank::setPvPActionData -> Non-DarkJedi-Enclave caller. Aborting.", darkEnclave, trace.TL_WARNING | trace.TL_DEBUG);
            return;
        }
        if (data == null)
        {
            trace.log("force_rank", "force_rank::setPvPActionData -> @data == null. removing all action data for " + pvpAction, darkEnclave, trace.TL_DEBUG);
            removeObjVar(darkEnclave, pvpAction);
            return;
        }
        if (validatePvPActionData(data) == null)
        {
            if (!data.containsKey("victims"))
            {
                trace.log("force_rank", "force_rank::setPvPActionData -> @data has no victim/starter/time data. removing all action data for " + pvpAction + " from enclave.  this is means all actions of this type are done.", darkEnclave, trace.TL_DEBUG);
                removeObjVar(darkEnclave, pvpAction);
            }
            else 
            {
                trace.log("force_rank", "force_rank::setPvPActionData: -> pvp action data (" + pvpAction + ") invalid.  not set to enclave.", darkEnclave, trace.TL_WARNING | trace.TL_DEBUG);
            }
            return;
        }
        obj_id[] inits = data.getObjIdArray("initiators");
        obj_id[] victs = data.getObjIdArray("victims");
        int[] startTms = data.getIntArray("times");
        setObjVar(darkEnclave, pvpAction + VAR_INITIATORS, inits);
        setObjVar(darkEnclave, pvpAction + VAR_VICTIMS, victs);
        setObjVar(darkEnclave, pvpAction + VAR_START_TIMESTAMPS, startTms);
        return;
    }
    public static dictionary getAndValidatePvPActionData(String pvpAction, obj_id darkEnclave) throws InterruptedException
    {
        if (getCouncilAffiliation(darkEnclave) != DARK_COUNCIL)
        {
            trace.log("force_rank", "force_rank::getAndValidatePvPActionData -> Non-DarkJedi-Enclave. Aborting.", darkEnclave, trace.TL_ERROR_LOG | trace.TL_DEBUG);
            return null;
        }
        if (!isIdValid(darkEnclave))
        {
            trace.log("force_rank", "force_rank::getAndValidatePvPActionData -> darkEnclave ID not valid.", darkEnclave, trace.TL_ERROR_LOG | trace.TL_DEBUG);
            return null;
        }
        Vector initiatorz = new Vector();
        initiatorz.setSize(0);
        if (hasObjVar(darkEnclave, pvpAction + VAR_INITIATORS))
        {
            initiatorz = getResizeableObjIdArrayObjVar(darkEnclave, pvpAction + VAR_INITIATORS);
        }
        Vector victimz = new Vector();
        victimz.setSize(0);
        if (hasObjVar(darkEnclave, pvpAction + VAR_VICTIMS))
        {
            victimz = getResizeableObjIdArrayObjVar(darkEnclave, pvpAction + VAR_VICTIMS);
        }
        Vector timez = new Vector();
        timez.setSize(0);
        if (hasObjVar(darkEnclave, pvpAction + VAR_START_TIMESTAMPS))
        {
            timez = getResizeableIntArrayObjVar(darkEnclave, pvpAction + VAR_START_TIMESTAMPS);
            if (timez == null)
            {
                LOG("force_rank", "Variable " + pvpAction + VAR_START_TIMESTAMPS + " returned a null array!");
            }
        }
        dictionary actionData = packageActionData(initiatorz, victimz, timez);
        return validatePvPActionData(actionData);
    }
    public static dictionary validatePvPActionData(dictionary data) throws InterruptedException
    {
        if (data == null)
        {
            return null;
        }
        if (!data.containsKey("initiators") || !data.containsKey("victims") || !data.containsKey("times"))
        {
            return null;
        }
        obj_id[] inits = data.getObjIdArray("initiators");
        obj_id[] victs = data.getObjIdArray("victims");
        int[] startTms = data.getIntArray("times");
        if (victs == null)
        {
            return null;
        }
        int len = victs.length;
        if (inits == null || startTms == null || inits.length != len || startTms.length != len)
        {
            trace.log("force_rank", "force_rank::validatePvPActionData -> Victim/Initiator/TimeStamp arrays have mismatched length.  Data not written.", null, trace.TL_ERROR_LOG | trace.TL_DEBUG);
            return null;
        }
        return data;
    }
    public static void deleteExpiredPvPActions(obj_id darkEnclave) throws InterruptedException
    {
        Vector initiatorz = new Vector();
        initiatorz.setSize(0);
        Vector victimz = new Vector();
        victimz.setSize(0);
        Vector timez = new Vector();
        timez.setSize(0);
        String[] actions = 
        {
            ACTION_VENDETTA,
            ACTION_PURGE_COUNCIL,
            ACTION_BANISHMENT
        };
        int[] durations = 
        {
            ACTION_VENDETTA_DURATION,
            ACTION_PURGE_COUNCIL_DURATION,
            ACTION_BANISHMENT_DURATION
        };
        int now = getGameTime();
        for (int i = 0; i < actions.length; i++)
        {
            dictionary data = getAndValidatePvPActionData(actions[i], darkEnclave);
            if (data == null)
            {
                return;
            }
            utils.concatArrays(initiatorz, data.getObjIdArray("initiators"));
            utils.concatArrays(victimz, data.getObjIdArray("victims"));
            utils.concatArrays(timez, data.getIntArray("times"));
            int x = 0;
            while (x < timez.size())
            {
                if (now - ((Integer)timez.get(x)).intValue() >= durations[x])
                {
                    utils.removeElementAt(timez, x);
                    utils.removeElementAt(victimz, x);
                    utils.removeElementAt(initiatorz, x);
                }
                else 
                {
                    x++;
                }
            }
            dictionary newData = packageActionData(initiatorz, victimz, timez);
            setPvPActionData(newData, actions[i], darkEnclave);
            timez.clear();
            victimz.clear();
            initiatorz.clear();
        }
        return;
    }
    public static void demoteDeadPlayerToRankZero(obj_id player, obj_id darkEnclave) throws InterruptedException
    {
        Vector peaceTarget = new Vector();
        peaceTarget.setSize(0);
        peaceTarget.add(player);
        Vector recips = new Vector();
        recips.setSize(0);
        utils.concatArrays(recips, getAllPlayersInForceRank(darkEnclave, -1));
        demoteForceRank(darkEnclave, utils.getRealPlayerFirstName(player), 0);
        notifyPlayerOfPvPActionEnd(null, ACTION_BANISHMENT, peaceTarget, recips, false);
        String[] lists = 
        {
            VAR_VICTIMS,
            VAR_INITIATORS
        };
        String[] actions = 
        {
            ACTION_PURGE_COUNCIL,
            ACTION_CANDIDATE_SUDDEN_DEATH,
            ACTION_BANISHMENT,
            ACTION_VENDETTA
        };
        for (int i = 0; i < actions.length; i++)
        {
            _clearPvPActionDataForPlayer(player, lists, actions[i], darkEnclave);
        }
        removeObjVar(player, VAR_NOTIFY_ENCLAVE_OF_DEATH);
        return;
    }
    public static boolean _clearPvPActionDataForPlayer(obj_id player, String[] whichLists, String pvpAction, obj_id darkEnclave) throws InterruptedException
    {
        dictionary data = getAndValidatePvPActionData(pvpAction, darkEnclave);
        if (data == null)
        {
            trace.log("force_rank", "force_rank::_clearPvPActionDataForPlayer: -> Method called but no " + pvpAction + " actions to clear for " + utils.getRealPlayerFirstName(player), player, trace.TL_WARNING | trace.TL_DEBUG);
            return false;
        }
        Vector inits = new Vector();
        inits.setSize(0);
        utils.concatArrays(inits, data.getObjIdArray("initiators"));
        Vector victs = new Vector();
        victs.setSize(0);
        utils.concatArrays(victs, data.getObjIdArray("victims"));
        Vector startTms = new Vector();
        startTms.setSize(0);
        utils.concatArrays(startTms, data.getIntArray("times"));
        for (int i = 0; i < whichLists.length; i++)
        {
            int idx = -1;
            if (whichLists[i].equals(VAR_INITIATORS))
            {
                idx = utils.getElementPositionInArray(inits, player);
            }
            else if (whichLists[i].equals(VAR_VICTIMS))
            {
                idx = utils.getElementPositionInArray(victs, player);
            }
            else 
            {
                trace.log("force_rank", "force_rank::clearAllPvPActionsForPlayer passed bad value for @whichList array .  Bailing without clearing action status.", player, trace.TL_ERROR_LOG | trace.TL_DEBUG);
                continue;
            }
            while (idx > -1)
            {
                inits = utils.removeElementAt(inits, idx);
                victs = utils.removeElementAt(victs, idx);
                startTms = utils.removeElementAt(startTms, idx);
                if (whichLists[i].equals(VAR_INITIATORS))
                {
                    idx = utils.getElementPositionInArray(inits, player);
                }
                else 
                {
                    idx = utils.getElementPositionInArray(victs, player);
                }
            }
        }
        dictionary newData = packageActionData(inits, victs, startTms);
        setPvPActionData(newData, pvpAction, darkEnclave);
        return true;
    }
    public static boolean isPartOfSameVendetta(obj_id player1, obj_id player2, dictionary vendettaData) throws InterruptedException
    {
        Vector initiatorz = new Vector();
        initiatorz.setSize(0);
        utils.concatArrays(initiatorz, vendettaData.getObjIdArray("initiators"));
        Vector victimz = new Vector();
        victimz.setSize(0);
        utils.concatArrays(victimz, vendettaData.getObjIdArray("victims"));
        int idx = initiatorz.indexOf(player1);
        while (idx > -1)
        {
            if (((obj_id)victimz.get(idx)) == player2)
            {
                trace.log("force_rating", "force_rank::isPartOfSameVendetta: -> " + utils.getRealPlayerFirstName(player1) + " is same vendetta as " + utils.getRealPlayerFirstName(player2), player1, trace.TL_DEBUG);
                return true;
            }
            idx = initiatorz.indexOf(player1, idx + 1);
        }
        idx = victimz.indexOf(player1);
        while (idx > -1)
        {
            if (((obj_id)initiatorz.get(idx)) == player2)
            {
                trace.log("force_rating", "force_rank::isPartOfSameVendetta: -> " + utils.getRealPlayerFirstName(player1) + " is same vendetta as " + utils.getRealPlayerFirstName(player2), player1, trace.TL_DEBUG);
                return true;
            }
            idx = victimz.indexOf(player1, idx + 1);
        }
        return false;
    }
    public static boolean isPvPActionInitiator(obj_id player, dictionary actionData1) throws InterruptedException
    {
        obj_id[] initiatorz = actionData1.getObjIdArray("initiators");
        if (utils.getElementPositionInArray(initiatorz, player) > -1)
        {
            return true;
        }
        return false;
    }
    public static boolean isPvPActionVictim(obj_id player, dictionary actionData2) throws InterruptedException
    {
        obj_id[] victimz = actionData2.getObjIdArray("victims");
        int idx = utils.getElementPositionInArray(victimz, player);
        if (idx > -1)
        {
            trace.log("force_rank", "force_rank::isPvPActionVictim: -> " + utils.getRealPlayerFirstName(player) + " IS a victim.", player, trace.TL_DEBUG);
            return true;
        }
        trace.log("force_rank", "force_rank::isPvPActionVictim: -> " + utils.getRealPlayerFirstName(player) + " is NOT a victim.", player, trace.TL_DEBUG);
        return false;
    }
    public static boolean isPartOfPvPAction(obj_id player, obj_id darkEnclave, dictionary actionData3) throws InterruptedException
    {
        return (isPvPActionInitiator(player, actionData3) || isPvPActionVictim(player, actionData3));
    }
    public static boolean bothArePartOfSameSuddenDeathRank(obj_id player1, obj_id player2, obj_id darkEnclave, dictionary suddenDeathData) throws InterruptedException
    {
        if (getForceRank(darkEnclave, utils.getRealPlayerFirstName(player1)) != getForceRank(darkEnclave, utils.getRealPlayerFirstName(player2)))
        {
            return false;
        }
        if (isPvPActionVictim(player1, suddenDeathData) && isPvPActionVictim(player2, suddenDeathData))
        {
            return true;
        }
        return false;
    }
    public static boolean _initiateCouncilPurge(obj_id darkEnclave, obj_id purger) throws InterruptedException
    {
        obj_id[] members = getAllPlayersInForceRank(darkEnclave, COUNCIL_RANK_NUMBER);
        if (members == null)
        {
            return false;
        }
        trace.log("force_rank", "force_rank::_initiateCouncilPurge: -> Council rank (" + COUNCIL_RANK_NUMBER + ") has " + members.length + " valid members.", darkEnclave, trace.TL_DEBUG);
        if (members.length < 1)
        {
            trace.log("force_rank", "force_rank::_initiateCouncilPurge: -> Council purge not done as there are no Council members to purge.", purger, trace.TL_WARNING | trace.TL_DEBUG);
            return true;
        }
        Vector victimz = new Vector();
        victimz.setSize(0);
        Vector initiatorz = new Vector();
        initiatorz.setSize(0);
        Vector timez = new Vector();
        timez.setSize(0);
        int time = getGameTime();
        for (int i = 0; i < members.length; i++)
        {
            if (members[i] != null)
            {
                utils.addElement(victimz, members[i]);
                utils.addElement(initiatorz, purger);
                utils.addElement(timez, time);
            }
        }
        if (victimz.size() > 0)
        {
            dictionary data = packageActionData(initiatorz, victimz, timez);
            setPvPActionData(data, ACTION_PURGE_COUNCIL, darkEnclave);
        }
        Vector recips = new Vector();
        recips.setSize(0);
        utils.concatArrays(recips, getAllPlayersInForceRank(darkEnclave, -1));
        notifyPlayerOfPvPActionStart(purger, ACTION_PURGE_COUNCIL, victimz, recips, darkEnclave);
        return true;
    }
    public static boolean startPvPAction(obj_id initiator, Vector allVictims, String pvpAction, obj_id darkEnclave) throws InterruptedException
    {
        trace.log("force_rank", "force_rank::startPvPAction: -> " + pvpAction + " started by " + initiator, initiator, trace.TL_CS_LOG | trace.TL_DEBUG);
        if (pvpAction.equals(ACTION_PURGE_COUNCIL))
        {
            return _initiateCouncilPurge(darkEnclave, initiator);
        }
        dictionary data = getAndValidatePvPActionData(pvpAction, darkEnclave);
        if (allVictims == null || allVictims.size() < 1)
        {
            return false;
        }
        Vector initiatorz = new Vector();
        initiatorz.setSize(0);
        Vector victimz = new Vector();
        victimz.setSize(0);
        Vector timez = new Vector();
        timez.setSize(0);
        int time = getGameTime();
        if (data != null)
        {
            utils.concatArrays(initiatorz, data.getObjIdArray("initiators"));
            utils.concatArrays(victimz, data.getObjIdArray("victims"));
            utils.concatArrays(timez, data.getIntArray("times"));
        }
        for (int i = 0; i < allVictims.size(); i++)
        {
            utils.addElement(initiatorz, initiator);
            utils.addElement(timez, time);
        }
        utils.concatArrays(victimz, allVictims);
        dictionary newData = packageActionData(initiatorz, victimz, timez);
        setPvPActionData(newData, pvpAction, darkEnclave);
        String victimList = "";
        for (int i = 0; i < allVictims.size(); i++)
        {
            victimList += "" + ((obj_id)allVictims.get(i)) + ",";
        }
        trace.log("force_rank", "Dark Jedi PvP Action Started: " + pvpAction + ".  Initiator: " + initiator + ", Victims: " + victimList, null, trace.TL_CS_LOG | trace.TL_DEBUG);
        Vector recips = new Vector();
        recips.setSize(0);
        if (pvpAction.equals(ACTION_VENDETTA))
        {
            recips.add(initiator);
            recips.add(((obj_id)victimz.get(0)));
        }
        else if (pvpAction.equals(ACTION_BANISHMENT))
        {
            utils.concatArrays(recips, getAllPlayersInForceRank(darkEnclave, -1));
        }
        else if (pvpAction.equals(ACTION_CANDIDATE_SUDDEN_DEATH))
        {
            utils.concatArrays(recips, allVictims);
        }
        notifyPlayerOfPvPActionStart(initiator, pvpAction, allVictims, recips, darkEnclave);
        return true;
    }
    public static void notifyPlayerOfPvPActionStart(obj_id theInitiator, String pvpAction, Vector actionTargets, Vector msgRecipients, obj_id darkEnclave) throws InterruptedException
    {
        if (msgRecipients == null || msgRecipients.size() < 1)
        {
            trace.log("force_rank", "force_rank::notifyPlayerOfPvPActionStart: -> @msgRecipients null or zero length. No one notified.", theInitiator, trace.TL_DEBUG);
            return;
        }
        dictionary parms = new dictionary();
        parms.put("initiator", theInitiator);
        parms.put("pvpAction", pvpAction);
        parms.put("actionTargets", actionTargets);
        parms.put("enclave", darkEnclave);
        for (int i = 0; i < msgRecipients.size(); i++)
        {
            messageTo(((obj_id)msgRecipients.get(i)), "msgPvPActionStart", parms, 0, false);
        }
        return;
    }
    public static void notifyPlayerOfPvPActionEnd(obj_id killer, String pvpAction, Vector peaceTargets, Vector msgRecipients, boolean actionTimedOut) throws InterruptedException
    {
        if (msgRecipients == null || msgRecipients.size() < 1 || peaceTargets == null || peaceTargets.size() < 1)
        {
            trace.log("force_rank", "force_rank::notifyPlayerOfPvPActionEnd: -> @msgRecipients null or zero length. No one notified.", killer, trace.TL_ERROR_LOG | trace.TL_DEBUG);
            return;
        }
        dictionary parms = new dictionary();
        if (killer != null)
        {
            parms.put("killer", killer);
        }
        parms.put("pvpAction", pvpAction);
        parms.put("peaceTargets", peaceTargets);
        parms.put("actionTimedOut", actionTimedOut);
        for (int i = 0; i < msgRecipients.size(); i++)
        {
            messageTo(((obj_id)msgRecipients.get(i)), "msgPvPActionEnd", parms, 0, false);
        }
        return;
    }
    public static obj_id[] getMyVendettaEnemies(dictionary vendettaData, obj_id player) throws InterruptedException
    {
        Vector initiatorz = new Vector();
        initiatorz.setSize(0);
        Vector victimz = new Vector();
        victimz.setSize(0);
        Vector vEnemies = new Vector();
        vEnemies.setSize(0);
        utils.concatArrays(initiatorz, vendettaData.getObjIdArray("initiators"));
        utils.concatArrays(victimz, vendettaData.getObjIdArray("victims"));
        int idx = initiatorz.indexOf(player);
        while (idx > -1)
        {
            utils.addElement(vEnemies, ((obj_id)victimz.get(idx)));
            idx = initiatorz.indexOf(player, idx + 1);
        }
        idx = victimz.indexOf(player);
        while (idx > -1)
        {
            utils.addElement(vEnemies, ((obj_id)initiatorz.get(idx)));
            idx = victimz.indexOf(player, idx + 1);
        }
        trace.log("force_rating", "force_rank::getMyVendettaEnemies: -> " + utils.getRealPlayerFirstName(player) + " has " + vEnemies.size() + " vendettas.", player, trace.TL_DEBUG);
        obj_id[] _vEnemies = new obj_id[0];
        if (vEnemies != null)
        {
            _vEnemies = new obj_id[vEnemies.size()];
            vEnemies.toArray(_vEnemies);
        }
        return _vEnemies;
    }
    public static obj_id[] getMySuddenDeathEnemies(dictionary suddenDeathData, obj_id player, obj_id darkEnclave) throws InterruptedException
    {
        Vector candidateVictims = new Vector();
        candidateVictims.setSize(0);
        Vector enemies = new Vector();
        enemies.setSize(0);
        if (!isPvPActionVictim(player, suddenDeathData))
        {
            obj_id[] _enemies = new obj_id[0];
            if (enemies != null)
            {
                _enemies = new obj_id[enemies.size()];
                enemies.toArray(_enemies);
            }
            return _enemies;
        }
        utils.concatArrays(candidateVictims, suddenDeathData.getObjIdArray("victims"));
        int playerRank = getForceRank(darkEnclave, utils.getRealPlayerFirstName(player));
        if (playerRank < 1)
        {
            return new obj_id[0];
        }
        for (int i = 0; i < candidateVictims.size(); i++)
        {
            if ((((obj_id)candidateVictims.get(i)) != player) && (getForceRank(darkEnclave, utils.getRealPlayerFirstName(((obj_id)candidateVictims.get(i)))) == playerRank))
            {
                utils.addElement(enemies, ((obj_id)candidateVictims.get(i)));
            }
        }
        obj_id[] _enemies = new obj_id[0];
        if (enemies != null)
        {
            _enemies = new obj_id[enemies.size()];
            enemies.toArray(_enemies);
        }
        return _enemies;
    }
    public static obj_id[] getEnemiesForPvPAction(obj_id player, String pvpAction, obj_id darkEnclave) throws InterruptedException
    {
        dictionary data = getAndValidatePvPActionData(pvpAction, darkEnclave);
        if (data == null)
        {
            return new obj_id[0];
        }
        if (pvpAction.equals(ACTION_VENDETTA))
        {
            return getMyVendettaEnemies(data, player);
        }
        if (pvpAction.equals(ACTION_CANDIDATE_SUDDEN_DEATH))
        {
            return getMySuddenDeathEnemies(data, player, darkEnclave);
        }
        return data.getObjIdArray("victims");
    }
    public static void requestPEFs(obj_id player) throws InterruptedException
    {
        if (!hasObjVar(player, VAR_MY_ENCLAVE_ID))
        {
            getEnclaveObjId(player, getCouncilAffiliation(player), "enclaveIdResponse");
            return;
        }
        obj_id myEnclave = getObjIdObjVar(player, VAR_MY_ENCLAVE_ID);
        if (getForceRank(myEnclave, utils.getRealPlayerFirstName(player)) < 1)
        {
            return;
        }
        dictionary parms = new dictionary();
        parms.put("sender", player);
        messageTo(myEnclave, "PEFSynchRequest", parms, 0, false);
        trace.log("force_rank", "force_rank::requestPEFs-> requesting enemy list from enclave", player, trace.TL_DEBUG);
        return;
    }
    public static obj_id[] getCurrentDarkJediEnemies(obj_id player, obj_id enclave) throws InterruptedException
    {
        Vector enemies = new Vector();
        enemies.setSize(0);
        dictionary banishmentData = getAndValidatePvPActionData(ACTION_BANISHMENT, enclave);
        dictionary purgeData = getAndValidatePvPActionData(ACTION_PURGE_COUNCIL, enclave);
        if ((purgeData != null && isPvPActionVictim(player, purgeData)) || (banishmentData != null && isPvPActionVictim(player, banishmentData)))
        {
            trace.log("force_rank", "force_rank::getCurrentDarkJediEnemies: -> " + utils.getRealPlayerFirstName(player) + " is victim of purge/banishment. Returning ALL rank members as enemy.", player, trace.TL_DEBUG);
            obj_id[] enemies2 = getAllPlayersInForceRank(enclave, -1);
            utils.concatArrays(enemies, enemies2);
        }
        else 
        {
            trace.log("force_rank", "force_rank::getCurrentDarkJediEnemies: -> " + utils.getRealPlayerFirstName(player) + " is NOT victim of purge/banishment. Checking for specific enemies...", player, trace.TL_DEBUG);
            utils.concatArrays(enemies, getEnemiesForPvPAction(player, ACTION_VENDETTA, enclave));
            utils.concatArrays(enemies, getEnemiesForPvPAction(player, ACTION_BANISHMENT, enclave));
            utils.concatArrays(enemies, getEnemiesForPvPAction(player, ACTION_PURGE_COUNCIL, enclave));
            utils.concatArrays(enemies, getEnemiesForPvPAction(player, ACTION_CANDIDATE_SUDDEN_DEATH, enclave));
        }
        obj_id[] _enemies = new obj_id[0];
        if (enemies != null)
        {
            _enemies = new obj_id[enemies.size()];
            enemies.toArray(_enemies);
        }
        return _enemies;
    }
    public static boolean isForceRanked(obj_id player) throws InterruptedException
    {
        return (getForceRank(player) >= 0);
    }
    public static boolean canForceRankedPetEnterEnclave(obj_id pet, obj_id enclave) throws InterruptedException
    {
        if (pet_lib.isPet(pet))
        {
            if (!pet_lib.hasMaster(pet))
            {
                return false;
            }
            obj_id master = getMaster(pet);
            return (isForceRanked(master) && (isPlayersEnclave(enclave, master)));
        }
        return false;
    }
    public static boolean isPlayersEnclave(obj_id enclave, obj_id player) throws InterruptedException
    {
        if (getCouncilAffiliation(enclave) == getCouncilAffiliation(player))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public static boolean createEnclaveTerminals(obj_id enclave) throws InterruptedException
    {
        if (!isIdValid(enclave))
        {
            trace.log("force_rank", "force_rank.createEnclaveTerminals -- enclave is invalid", enclave, trace.TL_ERROR_LOG | trace.TL_DEBUG);
            return false;
        }
        if (!hasScript(enclave, force_rank.SCRIPT_ENCLAVE_CONTROLLER))
        {
            trace.log("force_rank", "force_rank.createEnclaveTerminals -- " + enclave + " is not an enclave building.", enclave, trace.TL_ERROR_LOG | trace.TL_DEBUG);
            return false;
        }
        obj_id voting_terminal = getVotingTerminal(enclave);
        obj_id arena_terminal = getArenaTerminal(enclave);
        obj_id chal_voting_terminal = getChallengeVotingTerminal(enclave);
        if (isIdValid(voting_terminal))
        {
            if (getCouncilAffiliation(enclave) == DARK_COUNCIL)
            {
                if (isIdValid(arena_terminal))
                {
                    return false;
                }
            }
            else 
            {
                if (isIdValid(chal_voting_terminal))
                {
                    return false;
                }
            }
        }
        if (isIdValid(voting_terminal))
        {
            destroyObject(voting_terminal);
        }
        if (isIdValid(arena_terminal))
        {
            destroyObject(arena_terminal);
        }
        if (isIdValid(chal_voting_terminal))
        {
            destroyObject(chal_voting_terminal);
        }
        int numRows = dataTableGetNumRows(JEDI_TERMINAL_DATATABLE);
        String area = getCurrentSceneName();
        for (int i = 0; i < numRows; i++)
        {
            dictionary row = dataTableGetRow(JEDI_TERMINAL_DATATABLE, i);
            if (row == null)
            {
                continue;
            }
            if (row.getInt("Council") == getCouncilAffiliation(enclave))
            {
                float x = row.getFloat("x");
                float y = row.getFloat("y");
                float z = row.getFloat("z");
                float rot = row.getFloat("Rotation");
                String cell_name = row.getString("Cell");
                if (cell_name == null || cell_name.length() < 1)
                {
                    LOG("force_rank", "force_rank.createEnclaveTerminals -- cell name is null for " + enclave);
                    continue;
                }
                String template = row.getString("Template");
                if (template == null || cell_name.length() < 1)
                {
                    LOG("force_rank", "force_rank.createEnclaveTerminals -- can't find template " + template + " for " + enclave);
                    continue;
                }
                obj_id cell = getCellId(enclave, cell_name);
                if (cell == null || cell_name.length() < 1)
                {
                    LOG("force_rank", "force_rank.createEnclaveTerminals -- unknown cell name " + row.getString("Cell") + " for " + enclave);
                    continue;
                }
                location loc = new location(x, y, z, area, cell);
                obj_id newTerminal = createObjectInCell(template, enclave, cell_name, loc);
                LOG("force_rank", "force_rank.createEnclaveTerminals -- creating new terminal " + newTerminal + " for " + enclave);
                setYaw(newTerminal, rot);
            }
        }
        return true;
    }
    public static int getPetitionInterval() throws InterruptedException
    {
        String config = getConfigSetting("GameServer", "frsPetitionInterval");
        int value = utils.stringToInt(config);
        if (value > 0)
        {
            return value;
        }
        else 
        {
            return PETITION_INTERVAL;
        }
    }
    public static int getVoteInterval() throws InterruptedException
    {
        String config = getConfigSetting("GameServer", "frsVoteInterval");
        int value = utils.stringToInt(config);
        if (value > 0)
        {
            return value;
        }
        else 
        {
            return VOTING_INTERVAL;
        }
    }
    public static int getAcceptanceInterval() throws InterruptedException
    {
        String config = getConfigSetting("GameServer", "frsAcceptanceInterval");
        int value = utils.stringToInt(config);
        if (value > 0)
        {
            return value;
        }
        else 
        {
            return ACCEPTANCE_INTERVAL;
        }
    }
    public static int getDemotionInterval() throws InterruptedException
    {
        String config = getConfigSetting("GameServer", "frsDemotionInterval");
        int value = utils.stringToInt(config);
        if (value > 0)
        {
            return value;
        }
        else 
        {
            return REQUEST_DEMOTION_DURATION;
        }
    }
    public static int getXpInterval() throws InterruptedException
    {
        String config = getConfigSetting("GameServer", "frsXpInterval");
        int value = utils.stringToInt(config);
        if (value > 0)
        {
            return value;
        }
        else 
        {
            return XP_MAINTENANCE_INTERVAL;
        }
    }
    public static boolean _updateForceRankData(obj_id source, int council, int data_mode, String params, String handler, boolean lock_data) throws InterruptedException
    {
        if (!isIdValid(source))
        {
            LOG("force_rank", "force_rank._updateForceRankData -- source is invalid.");
            return false;
        }
        if (params == null)
        {
            LOG("force_rank", "force_rank._updateForceRankData -- params is null.");
            return false;
        }
        if (handler == null)
        {
            LOG("force_rank", "force_rank._updateForceRankData -- handler is null.");
            return false;
        }
        int request_id = getClusterWideData(force_rank.CLUSTER_DATA_NAME, force_rank.STRING_CLUSTER_ENCLAVE_NAME + council, lock_data, source);
        utils.setScriptVar(source, SCRIPT_VAR_DATA_REQUEST + request_id, data_mode);
        utils.setScriptVar(source, SCRIPT_VAR_PARAMS_REQUEST + request_id, params);
        utils.setScriptVar(source, SCRIPT_VAR_HANDLER_REQUEST + request_id, handler);
        if (!hasScript(source, SCRIPT_DATA_HANDLER))
        {
            attachScript(source, SCRIPT_DATA_HANDLER);
        }
        return true;
    }
}
