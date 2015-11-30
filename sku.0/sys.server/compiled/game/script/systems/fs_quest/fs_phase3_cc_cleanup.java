package script.systems.fs_quest;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.community_crafting;
import script.library.fs_dyn_village;
import script.library.fs_quests;
import script.library.quests;
import script.library.utils;

public class fs_phase3_cc_cleanup extends script.base_script
{
    public fs_phase3_cc_cleanup()
    {
    }
    public static final String OBJVAR_LAST_PHASE = community_crafting.OBJVAR_COMMUNITY_CRAFTING_BASE + ".lastPhase";
    public static final String SCRIPTVAR_CRAFTING_TRACKER = fs_dyn_village.OBJVAR_MY_MASTER_OBJECT;
    public static final String SCRIPTVAR_VILLAGE_PHASE = "community_crafting.village_phase";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        startPhase3Cleanup(self);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        startPhase3Cleanup(self);
        return SCRIPT_CONTINUE;
    }
    public int handleGetVillageManager(obj_id self, dictionary params) throws InterruptedException
    {
        boolean rslt = false;
        String key = "";
        if (params.containsKey("key"))
        {
            key = params.getString("key");
        }
        if (params.containsKey("success"))
        {
            rslt = params.getBoolean("success");
        }
        if (rslt && key != null && key.length() > 0)
        {
            obj_id id = null;
            if (params.containsKey(key))
            {
                id = params.getObjId(key);
                if (isIdValid(id))
                {
                    utils.setScriptVar(self, SCRIPTVAR_CRAFTING_TRACKER, id);
                    debugServerConsoleMsg(self, "Object key '" + key + "' was registered as " + id);
                    cleanupCraftingQuest(self);
                    return SCRIPT_CONTINUE;
                }
            }
        }
        debugServerConsoleMsg(self, "Failed to find obj id '" + key + "' cluster wide data. Id might not have been registered yet.");
        messageTo(self, "handleRetryGetVillageManager", null, 30, false);
        return SCRIPT_CONTINUE;
    }
    public int handleRetryGetVillageManager(obj_id self, dictionary params) throws InterruptedException
    {
        requestVillageMaster(self);
        return SCRIPT_CONTINUE;
    }
    public void startPhase3Cleanup(obj_id self) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, SCRIPTVAR_CRAFTING_TRACKER))
        {
            debugServerConsoleMsg(self, "startPhase3Cleanup asking for village manager");
            requestVillageMaster(self);
        }
        else 
        {
            debugServerConsoleMsg(self, "startPhase3Cleanup found village manager");
            cleanupCraftingQuest(self);
        }
    }
    public void requestVillageMaster(obj_id self) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, SCRIPTVAR_CRAFTING_TRACKER))
        {
            debugServerConsoleMsg(self, "requestVillageMaster asking for village manager");
            messageTo(self, "handleRetryGetVillageManager", null, 60.0f, false);
            fs_dyn_village.getRegisteredObjIdFromClusterWideData(fs_dyn_village.CLUSTER_OBJID_KEY_MASTER, "handleGetVillageManager", self);
        }
    }
    public boolean isCommunityCraftingEnabled(obj_id npc) throws InterruptedException
    {
        if (utils.getIntScriptVar(npc, SCRIPTVAR_VILLAGE_PHASE) != 4)
        {
            return false;
        }
        obj_id craftingTracker = utils.getObjIdScriptVar(npc, SCRIPTVAR_CRAFTING_TRACKER);
        if (isIdValid(craftingTracker))
        {
            return community_crafting.isSessionActive(craftingTracker);
        }
        return false;
    }
    public void cleanupCraftingQuest(obj_id self) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, SCRIPTVAR_CRAFTING_TRACKER))
        {
            debugServerConsoleMsg(null, "WARNING: cleanupCraftingQuest no crafting tracker");
            return;
        }
        obj_id villageId = utils.getObjIdScriptVar(self, SCRIPTVAR_CRAFTING_TRACKER);
        if (!isIdValid(villageId))
        {
            debugServerConsoleMsg(null, "WARNING: cleanupCraftingQuest no village");
            return;
        }
        if (!villageId.isInitialized())
        {
            messageTo(self, "handleReinitialize", null, 5, false);
            return;
        }
        int currentPhase = getIntObjVar(villageId, fs_dyn_village.OBJVAR_CURRENT_PHASE);
        int lastPhase = getIntObjVar(villageId, OBJVAR_LAST_PHASE);
        utils.setScriptVar(self, SCRIPTVAR_VILLAGE_PHASE, currentPhase);
        debugServerConsoleMsg(null, "Quharek current phase = " + currentPhase + ", last phase = " + lastPhase);
        if (currentPhase == 4 && (currentPhase != lastPhase || isCommunityCraftingEnabled(self)))
        {
            community_crafting.finalizeItem(villageId, true);
            Vector shieldNames = new Vector();
            Vector shieldValues = new Vector();
            if (community_crafting.getProjectAttributes(villageId, shieldNames, shieldValues))
            {
                for (int i = 0; i < shieldNames.size(); ++i)
                {
                    setObjVar(villageId, fs_dyn_village.OBJVAR_VILLAGE_SHIELDS + "." + (((string_id)(shieldNames.get(i)))).getAsciiId(), (((Float)(shieldValues.get(i)))).floatValue());
                }
            }
            else 
            {
                CustomerServiceLog("fs_village", "WARNING: unable to get final " + "attributes for the phase 3 community crafting quest");
            }
            community_crafting.cleanup(villageId);
            setObjVar(villageId, OBJVAR_LAST_PHASE, currentPhase);
        }
    }
}
