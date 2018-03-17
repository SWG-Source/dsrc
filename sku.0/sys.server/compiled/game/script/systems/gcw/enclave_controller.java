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
import script.library.pvp;

public class enclave_controller extends script.base_script
{
    public enclave_controller()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        String frsConfig = getConfigSetting("GameServer", "enableFRS");
        if (frsConfig == null || frsConfig.length() < 1)
        {
            removeObjVar(self, "force_rank.roster");
            removeObjVar(self, "force_rank.data");
            removeObjVar(self, "force_rank.voting");
            force_rank.resetEnclaveData(self);
            force_rank.resetClusterData(self);
            force_rank.createEnclaveTerminals(self);
            return SCRIPT_CONTINUE;
        }
        LOG("force_rank", "enclave_controller.OnInitialize -- " + self);
        int council = force_rank.getCouncilAffiliation(self);
        if (council == -1)
        {
            LOG("force_rank", "enclave_controller.OnInitialize -- " + self + " does not have a valid council value.");
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, "force_rank.roster"))
        {
            LOG("force_rank", "enclave_controller.OnInitialize -- can't find enclave data on " + self + ".  Setting it up...");
            CustomerServiceLog("force_rank", "Initializing data for enclave " + self);
            force_rank.resetEnclaveData(self);
        }
        int request_id = getClusterWideData(force_rank.CLUSTER_DATA_NAME, force_rank.STRING_CLUSTER_ENCLAVE_NAME + council, false, self);
        utils.setScriptVar(self, force_rank.SCRIPT_VAR_DATA_REQUEST + request_id, 0);
        force_rank.logEnclaveData(self);
        force_rank.makeAllCellsPublic(self);
        force_rank.setCellScripts(self);
        messageTo(self, "msgEnclavePulse", null, force_rank.MAINTENANCE_PULSE, false);
        return SCRIPT_CONTINUE;
    }
    public int OnClusterWideDataResponse(obj_id self, String manage_name, String enclave_name, int request_id, String[] element_name_list, dictionary[] enclave_data, int lock_key) throws InterruptedException
    {
        LOG("force_rank", "enclave_controller.OnClusterWideDataResponse -- " + self);
        if (!manage_name.equals(force_rank.CLUSTER_DATA_NAME))
        {
            LOG("force_rank", "enclave_controller.OnClusterWideDataResponse -- " + self + " Ignoring cluster manager name " + manage_name);
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, force_rank.SCRIPT_VAR_DATA_REQUEST + request_id))
        {
            int data_mode = utils.getIntScriptVar(self, force_rank.SCRIPT_VAR_DATA_REQUEST + request_id);
            if (data_mode != 0)
            {
                LOG("force_rank", "enclave_controller.OnClusterWideDataResponse -- ignoring request_id " + request_id + " on " + self + " since its data_mode is " + data_mode);
                return SCRIPT_CONTINUE;
            }
            utils.removeScriptVar(self, force_rank.SCRIPT_VAR_DATA_REQUEST + request_id);
        }
        else 
        {
            LOG("force_rank", "enclave_controller.OnClusterWideDataResponse -- cannot find a data_mode for request_id " + request_id + " on " + self);
            releaseClusterWideDataLock(manage_name, lock_key);
            return SCRIPT_OVERRIDE;
        }
        int council = force_rank.getCouncilAffiliation(self);
        if (council == -1)
        {
            LOG("force_rank", "enclave_controller.OnClusterWideDataResponse -- " + self + " does not have a valid council value.");
            releaseClusterWideDataLock(manage_name, lock_key);
            return SCRIPT_OVERRIDE;
        }
        if (element_name_list == null || element_name_list.length < 1)
        {
            LOG("force_rank", "enclave_controller.OnClusterWideDataResponse -- replace cluster data");
            force_rank.replaceEnclaveClusterData(self, lock_key);
        }
        else 
        {
            if (enclave_data == null || enclave_data.length < 1)
            {
                LOG("force_rank", "enclave_controller.OnClusterWideDataResponse -- " + self + " enclave data is null or empty.");
                releaseClusterWideDataLock(manage_name, lock_key);
                return SCRIPT_OVERRIDE;
            }
            if (enclave_data.length > 1)
            {
                LOG("force_rank", "enclave_controller.OnClusterWideDataResponse -- " + self + " There is more than one element in the returned data.  This shouldn't happen.");
                releaseClusterWideDataLock(manage_name, lock_key);
                return SCRIPT_OVERRIDE;
            }
            force_rank.updateEnclaveClusterData(self, enclave_data[0]);
        }
        releaseClusterWideDataLock(manage_name, lock_key);
        return SCRIPT_OVERRIDE;
    }
    public int PEFSynchRequest(obj_id self, dictionary params) throws InterruptedException
    {
        if (!params.containsKey("sender"))
        {
            LOG("force_rank", "enclave_controller::requestPEFSynch: -> missing @sender parm... not responding to PEF sync.");
            return SCRIPT_CONTINUE;
        }
        obj_id sender = params.getObjId("sender");
        dictionary parms = new dictionary();
        parms.put("sender", self);
        parms.put("enemies", force_rank.getCurrentDarkJediEnemies(sender, self));
        messageTo(sender, "PEFSynchResponse", parms, 0, false);
        return SCRIPT_CONTINUE;
    }
    public int msgVictimizedJediDeath(obj_id self, dictionary params) throws InterruptedException
    {
        if (params != null)
        {
            force_rank.handleCondemnedPlayerDeath(self, params);
        }
        return SCRIPT_CONTINUE;
    }
    public int XPDeathValidateRequest(obj_id self, dictionary params) throws InterruptedException
    {
        java.util.Enumeration pKeysd = params.keys();
        while (pKeysd.hasMoreElements())
        {
            LOG("force_rank", "XPDeathValidateRequest params keys: " + pKeysd.nextElement());
        }
        if ((!params.containsKey("victim")) || (!params.containsKey("totalDamageToVictim")))
        {
            LOG("pvp_rating", "enclabe_controller::XPDeathValidateRequest: -> Missing vital params 'victim' and 'totalDamageToVictim'. Not validating Dark Jedi XP death.");
            return SCRIPT_CONTINUE;
        }
        obj_id victim = params.getObjId("victim");
        params.remove("victim");
        int totalDamageTally = params.getInt("totalDamageToVictim");
        params.remove("totalDamageToVictim");
        java.util.Enumeration pKeys = params.keys();
        obj_id[] winners = new obj_id[params.size()];
        float[] contributions = new float[winners.length];
        int i = 0;
        while (pKeys.hasMoreElements())
        {
            obj_id key = (obj_id)pKeys.nextElement();
            winners[i] = key;
            contributions[i] = params.getFloat(key);
            i++;
        }
        dictionary suddenDeathData = force_rank.getAndValidatePvPActionData(force_rank.ACTION_CANDIDATE_SUDDEN_DEATH, self);
        dictionary vendettaData = force_rank.getAndValidatePvPActionData(force_rank.ACTION_VENDETTA, self);
        dictionary banishmentData = force_rank.getAndValidatePvPActionData(force_rank.ACTION_BANISHMENT, self);
        dictionary purgeData = force_rank.getAndValidatePvPActionData(force_rank.ACTION_PURGE_COUNCIL, self);
        dictionary validatedXPDeltas = new dictionary();
        int victimXPDelta = 0;
        for (i = 0; i < winners.length; i++)
        {
            if ((vendettaData != null && force_rank.isPartOfSameVendetta(winners[i], victim, vendettaData)) || (banishmentData != null && force_rank.isPvPActionVictim(victim, banishmentData)) || (purgeData != null && force_rank.isPvPActionVictim(victim, purgeData)) || (suddenDeathData != null && force_rank.bothArePartOfSameSuddenDeathRank(winners[i], victim, self, suddenDeathData)))
            {
                validatedXPDeltas.put(winners[i], pvp.getAdjustedForceRankXPDelta(victim, winners[i], contributions[i], false));
                victimXPDelta += pvp.getAdjustedForceRankXPDelta(victim, winners[i], contributions[i], true);
            }
        }
        if (victimXPDelta > 0)
        {
            validatedXPDeltas.put(victim, victimXPDelta);
        }
        if (validatedXPDeltas.size() > 0)
        {
            messageTo(victim, "XPDeathValidateResponse", validatedXPDeltas, 0, false);
        }
        else 
        {
            LOG("force_rank", "enclave_controller::XPDeathValidateRequest: -> None of the dark Jedi who participated in the death are eligible to receive XP.");
        }
        return SCRIPT_CONTINUE;
    }
    public int msgRequestExperienceDebt(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        String player_name = params.getString("player_name");
        if (!isIdValid(player))
        {
            LOG("force_rank", "enclave_controller.msgRequestExperienceDebt -- player is invalid.");
            return SCRIPT_CONTINUE;
        }
        if (player_name == null)
        {
            LOG("force_rank", "enclave_controller.msgRequestExperienceDebt -- player_name is null.");
            return SCRIPT_CONTINUE;
        }
        int xp_debt = force_rank.getExperienceDebt(self, player_name);
        if (xp_debt > 0)
        {
            if (force_rank.getForceRank(self, player_name) > 0)
            {
                dictionary d = new dictionary();
                d.put("xp_debt", xp_debt);
                messageTo(player, "msgApplyExperienceDebt", d, 3.0f, false);
            }
            String obj_var_name = force_rank.VAR_MEMBER_DATA + player_name + ".xp_debt";
            removeObjVar(self, obj_var_name);
        }
        return SCRIPT_CONTINUE;
    }
    public int msgForceEnclaveUpdate(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("force_rank", "enclave_controller.msgForceEnclaveUpdate -- " + self);
        int council = force_rank.getCouncilAffiliation(self);
        if (council == -1)
        {
            LOG("force_rank", "enclave_controller.forceEnclaveUpdate -- " + self + " does not have a valid council value.");
            return SCRIPT_CONTINUE;
        }
        int request_id = getClusterWideData(force_rank.CLUSTER_DATA_NAME, force_rank.STRING_CLUSTER_ENCLAVE_NAME + council, false, self);
        utils.setScriptVar(self, force_rank.SCRIPT_VAR_DATA_REQUEST + request_id, 0);
        return SCRIPT_CONTINUE;
    }
    public int msgEnclavePulse(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("force_rank", "enclave_controller.msgEnclavePulse -- " + self + "  " + getGameTime());
        messageTo(self, "msgEnclavePulse", null, force_rank.MAINTENANCE_PULSE, false);
        force_rank.performEnclaveMaintenance(self);
        return SCRIPT_CONTINUE;
    }
    public int msgValidateFRSPlayerData(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            LOG("force_rank", "enclave_controller.msgValidateFRSPlayerData(enclave) -- params is null");
            return SCRIPT_CONTINUE;
        }
        String player_name = params.getString("player_name");
        int player_rank = params.getInt("player_rank");
        obj_id player = params.getObjId("player");
        if (player_name == null)
        {
            LOG("force_rank", "enclave_controller.msgValidateFRSPlayerData(enclave) -- player_name is null");
            return SCRIPT_CONTINUE;
        }
        if (player_rank < 0 || player_rank > 11)
        {
            LOG("force_rank", "enclave_controller.msgValidateFRSPlayerData(enclave) -- player_rank is not a legal value: " + player_rank);
            return SCRIPT_CONTINUE;
        }
        if (!isIdValid(player))
        {
            LOG("force_rank", "enclave_controller.msgValidateFRSPlayerData(enclave) -- player is invalid");
            return SCRIPT_CONTINUE;
        }
        int enclave_rank = force_rank.getForceRank(self, player_name);
        if (enclave_rank == -1)
        {
            enclave_rank = 0;
        }
        if (player_rank != enclave_rank)
        {
            params.put("player_rank", enclave_rank);
        }
        messageTo(player, "msgValidateFRSPlayerData", params, 0.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int msgFRSUpdateClusterData(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("force_rank", "enclave_controller.msgFRSUpdateClusterData");
        int council = force_rank.getCouncilAffiliation(self);
        if (council == -1)
        {
            LOG("force_rank", "enclave_controller.msgFRSUpdateClusterData -- " + self + " does not have a valid council value.");
            return SCRIPT_CONTINUE;
        }
        int request_id = getClusterWideData(force_rank.CLUSTER_DATA_NAME, force_rank.STRING_CLUSTER_ENCLAVE_NAME + council, true, self);
        utils.setScriptVar(self, force_rank.SCRIPT_VAR_DATA_REQUEST + request_id, 0);
        return SCRIPT_CONTINUE;
    }
}
