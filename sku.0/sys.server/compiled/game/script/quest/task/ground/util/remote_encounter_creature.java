package script.quest.task.ground.util;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.base.remote_object;
import script.library.groundquests;
import script.library.utils;
import script.quest.task.ground.remote_encounter;

public class remote_encounter_creature extends script.base_script
{
    public remote_encounter_creature()
    {
    }
    public static final boolean LOGS_ENABLED = false;
    public static final String REC = "RemoteEncounterCreature";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        log("OnAttach");
        if (utils.hasScriptVar(self, remote_object.REQUESTER) && utils.hasScriptVar(self, remote_object.CREATE_PARAMS))
        {
            obj_id requester = utils.getObjIdScriptVar(self, remote_object.REQUESTER);
            dictionary createParams = utils.getDictionaryScriptVar(self, remote_object.CREATE_PARAMS);
            int questCrc = createParams.getInt(remote_encounter.QUEST_CRC);
            int taskId = createParams.getInt(remote_encounter.TASK_ID);
            int encounterDuration = groundquests.getTaskIntDataEntry(questCrc, taskId, remote_encounter.dataTableColumnEncounterDuration);
            messageTo(self, remote_encounter.REMOTE_ENCOUNTER_ESCAPE, null, encounterDuration, false);
            log("Chief Rock: Ship's current recharge rate is " + getShipShieldRechargeRate(self) + " Setting it to one hojillion");
            setShipShieldRechargeRate(self, 1000000000f);
        }
        else 
        {
            LOG(REC, "Error: Missing required script vars.");
            destroyObject(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int objectDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        log("objectDestroyed");
        if (utils.hasScriptVar(self, remote_object.REQUESTER) && utils.hasScriptVar(self, remote_object.CREATE_PARAMS))
        {
            obj_id requester = utils.getObjIdScriptVar(self, remote_object.REQUESTER);
            dictionary createParams = utils.getDictionaryScriptVar(self, remote_object.CREATE_PARAMS);
            messageTo(requester, remote_encounter.REMOTE_ENCOUNTER_CREATURE_DIED, createParams, 0, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int remoteEncounterCleanup(obj_id self, dictionary params) throws InterruptedException
    {
        log("remoteEncounterCleanup");
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int remoteEncounterEscape(obj_id self, dictionary params) throws InterruptedException
    {
        log("remoteEncounterEscape");
        if (utils.hasScriptVar(self, remote_object.REQUESTER) && utils.hasScriptVar(self, remote_object.CREATE_PARAMS))
        {
            obj_id requester = utils.getObjIdScriptVar(self, remote_object.REQUESTER);
            dictionary createParams = utils.getDictionaryScriptVar(self, remote_object.CREATE_PARAMS);
            messageTo(requester, remote_encounter.REMOTE_ENCOUNTER_CREATURE_ESCAPED, createParams, 0, false);
        }
        destroyObjectHyperspace(self);
        return SCRIPT_CONTINUE;
    }
    public int remoteEncounterSignaled(obj_id self, dictionary params) throws InterruptedException
    {
        log("remoteEncounterSignaled");
        log("Chief Rock: Ship's current recharge rate is " + getShipShieldRechargeRate(self) + " Setting it to much less");
        setShipShieldRechargeRate(self, 1.0f);
        return SCRIPT_CONTINUE;
    }
    public void log(String text) throws InterruptedException
    {
        if (LOGS_ENABLED)
        {
            LOG(REC, text);
        }
    }
    public void dumpScriptVars(obj_id self) throws InterruptedException
    {
        obj_id requester = utils.getObjIdScriptVar(self, remote_object.REQUESTER);
        dictionary createParams = utils.getDictionaryScriptVar(self, remote_object.CREATE_PARAMS);
        log("REQUESTER: " + requester);
        log("CREATE_PARAMS: " + createParams.toString());
    }
}
