package script.systems.gcw;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.force_rank;
import script.library.utils;

public class enclave_data_handler extends script.base_script
{
    public enclave_data_handler()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        detachScript(self, force_rank.SCRIPT_DATA_HANDLER);
        return SCRIPT_CONTINUE;
    }
    public int OnClusterWideDataResponse(obj_id self, String manage_name, String enclave_name, int request_id, String[] element_name_list, dictionary[] enclave_data, int lock_key) throws InterruptedException
    {
        LOG("force_rank", "enclave_data_handler.OnClusterWideDataResponse -- " + request_id);
        if (!manage_name.equals(force_rank.CLUSTER_DATA_NAME))
        {
            LOG("force_rank", "enclave_data_handler.OnClusterWideDataResponse -- " + self + " Ignoring cluster manager name " + manage_name);
            return SCRIPT_CONTINUE;
        }
        int data_mode = -1;
        if (utils.hasScriptVar(self, force_rank.SCRIPT_VAR_DATA_REQUEST + request_id))
        {
            data_mode = utils.getIntScriptVar(self, force_rank.SCRIPT_VAR_DATA_REQUEST + request_id);
            if (data_mode == 0)
            {
                LOG("force_rank", "enclave_data_handler.OnClusterWideDataResponse -- " + self + " Ignoring request since it is an enclave data save.");
                return SCRIPT_CONTINUE;
            }
            utils.removeScriptVar(self, force_rank.SCRIPT_VAR_DATA_REQUEST + request_id);
        }
        else 
        {
            LOG("force_rank", "enclave_data_handler.OnClusterWideDataResponse -- cannot find a data_mode for request_id " + request_id + " on " + self);
            releaseClusterWideDataLock(manage_name, lock_key);
            return SCRIPT_OVERRIDE;
        }
        LOG("force_rank", "enclave_data_handler.OnClusterWideDataResponse -- data_mode ->" + data_mode);
        String str_params = null;
        if (utils.hasScriptVar(self, force_rank.SCRIPT_VAR_PARAMS_REQUEST + request_id))
        {
            str_params = utils.getStringScriptVar(self, force_rank.SCRIPT_VAR_PARAMS_REQUEST + request_id);
            utils.removeScriptVar(self, force_rank.SCRIPT_VAR_PARAMS_REQUEST + request_id);
        }
        String handler = null;
        if (utils.hasScriptVar(self, force_rank.SCRIPT_VAR_HANDLER_REQUEST + request_id))
        {
            handler = utils.getStringScriptVar(self, force_rank.SCRIPT_VAR_HANDLER_REQUEST + request_id);
            utils.removeScriptVar(self, force_rank.SCRIPT_VAR_HANDLER_REQUEST + request_id);
        }
        if (data_mode == -1 || str_params == null || handler == null)
        {
            LOG("force_rank", "enclave_data_handler.OnClusterWideDataResponse -- invalid request data found " + data_mode + "/" + str_params + "/" + handler);
            releaseClusterWideDataLock(manage_name, lock_key);
            return SCRIPT_OVERRIDE;
        }
        if (element_name_list == null || element_name_list.length < 1)
        {
            sendSystemMessage(self, new string_id(force_rank.STF_FILE, "no_cluster_data_returned"));
            releaseClusterWideDataLock(manage_name, lock_key);
            return SCRIPT_OVERRIDE;
        }
        switch (data_mode)
        {
            case 1:
            
            {
                int params = utils.stringToInt(str_params);
                dictionary d = new dictionary();
                if (params == 0)
                {
                    for (int i = 1; i < 12; i++)
                    {
                        Vector rank_list = force_rank.getRankMembersFromDictionary(enclave_data[0], i);
                        LOG("force_rank", "rank_list (" + i + ") ->" + rank_list.size());
                        if (rank_list != null)
                        {
                            d.put("rank_list" + i, rank_list);
                        }
                    }
                }
                else 
                {
                    Vector rank_list = force_rank.getRankMembersFromDictionary(enclave_data[0], params);
                    if (rank_list != null)
                    {
                        d.put("rank_list", rank_list);
                    }
                }
                d.put("rank_requested", params);
                messageTo(self, handler, d, 0.0f, false);
                break;
            }
            case 2:
            
            {
                Vector board_names = new Vector();
                board_names.setSize(0);
                Vector board_ratings = new Vector();
                board_ratings.setSize(0);
                for (int i = 0; i < force_rank.PVP_BOARD_SIZE; i++)
                {
                    String key_name = force_rank.BATCH_VAR_PVPBOARD_NAME + i;
                    String key_rating = force_rank.BATCH_VAR_PVPBOARD_RATING + i;
                    if (enclave_data[0].containsKey(key_name) && enclave_data[0].containsKey(key_rating))
                    {
                        String name = enclave_data[0].getString(key_name);
                        int rating = enclave_data[0].getInt(key_rating);
                        if (name != null && name.length() > 0 && !name.equals(force_rank.EMPTY_SLOT) && rating > 0)
                        {
                            board_names = utils.addElement(board_names, name);
                            board_ratings = utils.addElement(board_ratings, rating);
                        }
                    }
                }
                dictionary d_board = new dictionary();
                d_board.put("board_names", board_names);
                d_board.put("board_ratings", board_ratings);
                messageTo(self, handler, d_board, 0.0f, false);
                break;
            }
            case 3:
            
            {
                if (!isPlayer(self))
                {
                    LOG("force_rank", "enclave_data_handler.OnClusterWideDataResponse -- " + self + " is not a player.");
                    releaseClusterWideDataLock(manage_name, lock_key);
                    return SCRIPT_OVERRIDE;
                }
                int params = utils.stringToInt(str_params);
                if (force_rank.getForceRank(self) != params)
                {
                    LOG("force_rank", "enclave_data_handler.OnClusterWideDataResponse -- not promoting " + self + " since his rank has changed.");
                    releaseClusterWideDataLock(manage_name, lock_key);
                    return SCRIPT_OVERRIDE;
                }
                String player_name = getFirstName(self);
                dictionary update_data = new dictionary();
                Vector current_rank = new Vector();
                current_rank.setSize(0);
                if (params > 0)
                {
                    current_rank = force_rank.getRankMembersFromDictionary(enclave_data[0], params);
                }
                Vector next_rank = force_rank.getRankMembersFromDictionary(enclave_data[0], params + 1);
                if (current_rank == null || next_rank == null)
                {
                    LOG("force_rank", "enclave_data_handler.OnClusterWideDataResponse -- promotion failed due to bad rank lists (" + current_rank + "/" + next_rank + ")");
                    CustomerServiceLog("force_rank", "Unable to promote %TU due to due bad rank list data.", self, null);
                    update_data.put("success", false);
                    messageTo(self, handler, update_data, 0.0f, false);
                    releaseClusterWideDataLock(manage_name, lock_key);
                    return SCRIPT_OVERRIDE;
                }
                int current_idx = current_rank.indexOf(player_name);
                if (params != 0)
                {
                    if (current_idx == -1)
                    {
                        LOG("force_rank", "enclave_data_handler.OnClusterWideDataResponse -- can't find player " + self + " in the rank list for " + params);
                        CustomerServiceLog("force_rank", "Unable to promote %TU since he is not in the specified rank.", self, null);
                        update_data.put("success", false);
                        messageTo(self, handler, update_data, 0.0f, false);
                        releaseClusterWideDataLock(manage_name, lock_key);
                        return SCRIPT_OVERRIDE;
                    }
                }
                int empty_idx = next_rank.indexOf(force_rank.EMPTY_SLOT);
                if (empty_idx == -1)
                {
                    if (next_rank.size() >= force_rank.MAX_RANK_SLOTS)
                    {
                        LOG("force_rank", "enclave_data_handler.OnClusterWideDataResponse -- can't find or create an empty slot for " + self + " in rank " + (params + 1));
                        CustomerServiceLog("force_rank", "Unable to promote %TU due to the inability to find or create an open slot.", self, null);
                        update_data.put("success", false);
                        messageTo(self, handler, update_data, 0.0f, false);
                        releaseClusterWideDataLock(manage_name, lock_key);
                        return SCRIPT_OVERRIDE;
                    }
                    empty_idx = next_rank.size();
                }
                if (current_idx > -1)
                {
                    String rank_key = force_rank.VAR_RANK_BASE + params + "-" + current_idx;
                    enclave_data[0].put(rank_key, force_rank.EMPTY_SLOT);
                    int max_slot = force_rank.getForceRankNumSlots(params);
                    if (current_rank.size() > max_slot)
                    {
                        dictionary new_data = force_rank.removeExtraSlotFromRankList(enclave_data[0], params);
                        if (new_data != null)
                        {
                            enclave_data[0] = new_data;
                        }
                    }
                }
                String rank_key = force_rank.VAR_RANK_BASE + (params + 1) + "-" + empty_idx;
                enclave_data[0].put(rank_key, player_name);
                String rank_skill = force_rank.getForceRankSkill(params + 1, force_rank.getCouncilAffiliation(self));
                grantSkill(self, rank_skill);
                setObjVar(self, force_rank.VAR_RANK, params + 1);
                if (params + 1 == 4)
                {
                    grantSkill(self, "force_title_jedi_rank_04");
                }
                else if (params + 1 == 10)
                {
                    grantSkill(self, "force_title_jedi_master");
                }
                replaceClusterWideData(force_rank.CLUSTER_DATA_NAME, element_name_list[0], enclave_data[0], false, lock_key);
                releaseClusterWideDataLock(manage_name, lock_key);
                CustomerServiceLog("force_rank", "%TU has been promoted to rank " + (params + 1), self, null);
                update_data.put("success", true);
                update_data.put("rank", params + 1);
                messageTo(self, handler, update_data, 0.0f, true);
                break;
            }
            case 4:
            
            {
                if (!isPlayer(self))
                {
                    LOG("force_rank", "enclave_data_handler.OnClusterWideDataResponse -- " + self + " is not a player.");
                    releaseClusterWideDataLock(manage_name, lock_key);
                    return SCRIPT_OVERRIDE;
                }
                int params = utils.stringToInt(str_params);
                int player_rank = force_rank.getForceRank(self);
                LOG("force_rank", "player_rank ->" + player_rank + "  params ->" + params);
                if (params >= player_rank)
                {
                    LOG("force_rank", "enclave_data_handler.OnClusterWideDataResponse -- not demoting " + self + " params is >= current rank: " + params + "/" + player_rank);
                    releaseClusterWideDataLock(manage_name, lock_key);
                    return SCRIPT_OVERRIDE;
                }
                if (params < 0 || params > 10)
                {
                    LOG("force_rank", "enclave_data_handler.OnClusterWideDataResponse -- not demoting " + self + " since params value is illegal:" + params);
                    releaseClusterWideDataLock(manage_name, lock_key);
                    return SCRIPT_OVERRIDE;
                }
                String player_name = getFirstName(self);
                dictionary update_data = new dictionary();
                Vector current_rank = force_rank.getRankMembersFromDictionary(enclave_data[0], player_rank);
                Vector next_rank;
                if (params > 0)
                {
                    next_rank = force_rank.getRankMembersFromDictionary(enclave_data[0], params);
                }
                else 
                {
                    next_rank = new Vector();
                }
                if (current_rank == null || next_rank == null)
                {
                    LOG("force_rank", "enclave_data_handler.OnClusterWideDataResponse -- demotion failed due to bad rank lists (" + current_rank + "/" + next_rank + ")");
                    CustomerServiceLog("force_rank", "Unable to demote %TU due to due bad rank list data.", self, null);
                    update_data.put("success", false);
                    messageTo(self, handler, update_data, 0.0f, false);
                    releaseClusterWideDataLock(manage_name, lock_key);
                    return SCRIPT_OVERRIDE;
                }
                int current_idx = current_rank.indexOf(player_name);
                if (current_idx == -1)
                {
                    LOG("force_rank", "enclave_data_handler.OnClusterWideDataResponse -- can't find player " + self + " in the rank list for " + player_rank);
                    CustomerServiceLog("force_rank", "Unable to demote %TU since he is not in the specified rank.", self, null);
                    update_data.put("success", false);
                    messageTo(self, handler, update_data, 0.0f, false);
                    releaseClusterWideDataLock(manage_name, lock_key);
                    return SCRIPT_OVERRIDE;
                }
                else 
                {
                    String rank_key = force_rank.VAR_RANK_BASE + player_rank + "-" + current_idx;
                    enclave_data[0].put(rank_key, force_rank.EMPTY_SLOT);
                    int max_slot = force_rank.getForceRankNumSlots(player_rank);
                    LOG("force_rank", "max_slot ->" + max_slot + "  current_rank->" + current_rank.size());
                    if (current_rank.size() > max_slot)
                    {
                        dictionary new_data = force_rank.removeExtraSlotFromRankList(enclave_data[0], player_rank);
                        if (new_data != null)
                        {
                            enclave_data[0] = new_data;
                        }
                    }
                }
                if (params > 0)
                {
                    int empty_idx = next_rank.indexOf(force_rank.EMPTY_SLOT);
                    if (empty_idx == -1)
                    {
                        if (next_rank.size() >= force_rank.MAX_RANK_SLOTS)
                        {
                            LOG("force_rank", "enclave_data_handler.OnClusterWideDataResponse -- can't demote %TU.  The maximum amount of slots has been reached for rank " + params);
                            CustomerServiceLog("force_rank", "Unable to demote %TU since he is not in the specified rank.", self, null);
                            releaseClusterWideDataLock(manage_name, lock_key);
                            return SCRIPT_OVERRIDE;
                        }
                        String rank_key = force_rank.VAR_RANK_BASE + params + "-" + next_rank.size();
                        enclave_data[0].put(rank_key, player_name);
                    }
                    else 
                    {
                        String rank_key = force_rank.VAR_RANK_BASE + params + "-" + empty_idx;
                        enclave_data[0].put(rank_key, player_name);
                    }
                }
                replaceClusterWideData(force_rank.CLUSTER_DATA_NAME, element_name_list[0], enclave_data[0], false, lock_key);
                releaseClusterWideDataLock(manage_name, lock_key);
                CustomerServiceLog("force_rank", "%TU has been demoted to rank " + params, self, null);
                update_data.put("success", true);
                update_data.put("rank", params);
                messageTo(self, handler, update_data, 0.0f, true);
                break;
            }
            case 5:
            
            {
                java.util.StringTokenizer st = new java.util.StringTokenizer(str_params, " ");
                if (st.countTokens() < 2)
                {
                    LOG("force_rank", "enclave_data_handler.OnClusterWideDataResponse -- str_params is invalid: " + str_params);
                    releaseClusterWideDataLock(manage_name, lock_key);
                    return SCRIPT_OVERRIDE;
                }
                String player_name = st.nextToken();
                str_params = st.nextToken();
                int params = utils.stringToInt(str_params);
                dictionary update_data = new dictionary();
                int player_rank = force_rank.getForceRank(self, player_name);
                if (player_rank < 1)
                {
                    LOG("force_rank", "enclave_data_handler.OnClusterWideDataResponse -- not demoting " + self + " since his rank is at " + player_rank);
                    releaseClusterWideDataLock(manage_name, lock_key);
                    return SCRIPT_OVERRIDE;
                }
                if (params >= player_rank)
                {
                    LOG("force_rank", "enclave_data_handler.OnClusterWideDataResponse -- not demoting " + self + " params is >= current rank: " + params + "/" + player_rank);
                    releaseClusterWideDataLock(manage_name, lock_key);
                    return SCRIPT_OVERRIDE;
                }
                if (params < 0 || params > 10)
                {
                    LOG("force_rank", "enclave_data_handler.OnClusterWideDataResponse -- not demoting " + self + " since params value is illegal:" + params);
                    releaseClusterWideDataLock(manage_name, lock_key);
                    return SCRIPT_OVERRIDE;
                }
                Vector current_rank = force_rank.getRankMembersFromDictionary(enclave_data[0], player_rank);
                Vector next_rank;
                if (params > 0)
                {
                    next_rank = force_rank.getRankMembersFromDictionary(enclave_data[0], params);
                }
                else 
                {
                    next_rank = new Vector();
                }
                if (current_rank == null || next_rank == null)
                {
                    LOG("force_rank", "enclave_data_handler.OnClusterWideDataResponse -- demotion failed due to bad rank lists (" + current_rank + "/" + next_rank + ")");
                    CustomerServiceLog("force_rank", "Unable to demote %TU due to due bad rank list data.", self, null);
                    update_data.put("success", false);
                    messageTo(self, handler, update_data, 0.0f, false);
                    releaseClusterWideDataLock(manage_name, lock_key);
                    return SCRIPT_OVERRIDE;
                }
                int current_idx = current_rank.indexOf(player_name);
                if (current_idx == -1)
                {
                    LOG("force_rank", "enclave_data_handler.OnClusterWideDataResponse -- can't find player " + self + " in the rank list for " + player_rank);
                    CustomerServiceLog("force_rank", "Unable to demote %TU since he is not in the specified rank.", self, null);
                    update_data.put("success", false);
                    messageTo(self, handler, update_data, 0.0f, false);
                    releaseClusterWideDataLock(manage_name, lock_key);
                    return SCRIPT_OVERRIDE;
                }
                else 
                {
                    String rank_key = force_rank.VAR_RANK_BASE + player_rank + "-" + current_idx;
                    enclave_data[0].put(rank_key, force_rank.EMPTY_SLOT);
                    int max_slot = force_rank.getForceRankNumSlots(player_rank);
                    if (current_rank.size() > max_slot)
                    {
                        dictionary new_data = force_rank.removeExtraSlotFromRankList(enclave_data[0], player_rank);
                        if (new_data != null)
                        {
                            enclave_data[0] = new_data;
                        }
                    }
                }
                if (params > 0)
                {
                    int empty_idx = next_rank.indexOf(force_rank.EMPTY_SLOT);
                    if (empty_idx == -1)
                    {
                        if (next_rank.size() >= force_rank.MAX_RANK_SLOTS)
                        {
                            LOG("force_rank", "enclave_data_handler.OnClusterWideDataResponse -- can't demote " + player_name + "  The maximum amount of slots has been reached for rank " + params);
                            CustomerServiceLog("force_rank", "Unable to demote %TU since he is not in the specified rank.", self, null);
                            releaseClusterWideDataLock(manage_name, lock_key);
                            return SCRIPT_OVERRIDE;
                        }
                        String rank_key = force_rank.VAR_RANK_BASE + params + "-" + next_rank.size();
                        enclave_data[0].put(rank_key, player_name);
                    }
                    else 
                    {
                        String rank_key = force_rank.VAR_RANK_BASE + params + "-" + empty_idx;
                        enclave_data[0].put(rank_key, player_name);
                    }
                }
                replaceClusterWideData(force_rank.CLUSTER_DATA_NAME, element_name_list[0], enclave_data[0], false, lock_key);
                releaseClusterWideDataLock(manage_name, lock_key);
                force_rank.updateEnclaveClusterData(self, enclave_data[0]);
                CustomerServiceLog("force_rank", player_name + " has been demoted to rank " + params + " using name instead of obj_id.");
                update_data.put("success", true);
                update_data.put("rank", params);
                obj_id player = getPlayerIdFromFirstName(toLower(player_name));
                if (!isIdValid(player))
                {
                    LOG("force_rank", "enclave_data_handler.OnClusterWideDataResponse -- unable to find an obj_id for " + player_name);
                }
                else 
                {
                    messageTo(player, handler, update_data, 0.0f, false);
                }
                break;
            }
            case 6:
            
            {
                if (enclave_data[0].containsKey(force_rank.VAR_ENCLAVE))
                {
                    dictionary d = new dictionary();
                    d.put("enclave", enclave_data[0].getObjId(force_rank.VAR_ENCLAVE));
                    messageTo(self, handler, d, 0.0f, false);
                }
                else 
                {
                    LOG("force_rank", "enclave_data_handler.OnClusterWideDataResponse -- unable to find obj_id of " + element_name_list[0]);
                }
                break;
            }
            case 7:
            
            {
                int player_rating = utils.stringToInt(str_params);
                if (player_rating < 0)
                {
                    LOG("force_rank", "enclave_data_handler.OnClusterWideDataResponse -- illegal rating value for " + self);
                    releaseClusterWideDataLock(manage_name, lock_key);
                    return SCRIPT_OVERRIDE;
                }
                dictionary pvp_updated_data = force_rank.adjustPvpBoardRankings(self, player_rating, enclave_data[0]);
                dictionary d = new dictionary();
                if (pvp_updated_data != null)
                {
                    replaceClusterWideData(force_rank.CLUSTER_DATA_NAME, element_name_list[0], pvp_updated_data, false, lock_key);
                    releaseClusterWideDataLock(manage_name, lock_key);
                    d.put("success", true);
                }
                else 
                {
                    d.put("success", false);
                }
                messageTo(self, handler, d, 0.0f, false);
                break;
            }
            case 8:
            
            {
                LOG("force_rank", "enclave_data_handler.OnClusterWideDataResponse -- sending message to " + self);
                removeClusterWideData(force_rank.CLUSTER_DATA_NAME, element_name_list[0], lock_key);
                releaseClusterWideDataLock(manage_name, lock_key);
                messageTo(self, handler, null, 0.0f, false);
                break;
            }
            case 9:
            
            {
                String player_name = getFirstName(self);
                int rank = force_rank.getForceRank(self);
                dictionary update_data = new dictionary();
                Vector rank_list = force_rank.getRankMembersFromDictionary(enclave_data[0], rank);
                if (rank_list == null)
                {
                    LOG("force_rank", "enclave_data_handler.OnClusterWideDataResponse -- rename failed due to unable to find list for rank " + rank + ". Player is " + self);
                    CustomerServiceLog("force_rank", "Unable to rename %TU due to due bad rank list data.", self, null);
                    update_data.put("success", false);
                    messageTo(self, handler, update_data, 0.0f, false);
                    releaseClusterWideDataLock(manage_name, lock_key);
                    return SCRIPT_OVERRIDE;
                }
                int idx = rank_list.indexOf(str_params);
                if (idx == -1)
                {
                    LOG("force_rank", "enclave_data_handler.OnClusterWideDataResponse -- can't find the old name of player " + self + " in the rank list " + rank);
                    CustomerServiceLog("force_rank", "Unable to rename %TU since he can't be found in rank " + rank, self, null);
                    update_data.put("success", false);
                    messageTo(self, handler, update_data, 0.0f, false);
                    releaseClusterWideDataLock(manage_name, lock_key);
                    return SCRIPT_OVERRIDE;
                }
                String rank_key = force_rank.VAR_RANK_BASE + rank + "-" + idx;
                enclave_data[0].put(rank_key, player_name);
                replaceClusterWideData(force_rank.CLUSTER_DATA_NAME, element_name_list[0], enclave_data[0], false, lock_key);
                releaseClusterWideDataLock(manage_name, lock_key);
                CustomerServiceLog("force_rank", "Renaming complete for %TU.", self, null);
                break;
            }
            default:
            
            {
                LOG("force_rank", "enclave_data_handler.OnClusterWideDataResponse -- invalid data_mode value of " + data_mode + " on " + self);
                releaseClusterWideDataLock(manage_name, lock_key);
                detachScript(self, force_rank.SCRIPT_DATA_HANDLER);
                break;
            }
        }
        return SCRIPT_OVERRIDE;
    }
}
