package script.systems.gcw;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.buff;
import script.library.create;
import script.library.factions;
import script.library.gcw;
import script.library.groundquests;
import script.library.resource;
import script.library.restuss_event;
import script.library.static_item;
import script.library.trial;
import script.library.utils;

public class gcw_vehicle_boss_patrol extends script.base_script
{
    public gcw_vehicle_boss_patrol()
    {
    }
    public static final String bossImperials = "gcw_city_atat";
    public static final String bossRebels = "gcw_city_hailfire_droid";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleGCWPatrol", null, 2, false);
        return SCRIPT_CONTINUE;
    }
    
    public int getConstructionQuestsCompleted(obj_id pylon) throws InterruptedException
    {
        int completed = 0;
        if (!isIdValid(pylon) || !exists(pylon))
        {
            return 0;
        }
        if (hasObjVar(pylon, "gcw.constructionQuestsCompleted"))
        {
            completed = getIntObjVar(pylon, "gcw.constructionQuestsCompleted");
        }
        return completed;
    }
    public int handleGCWPatrol(obj_id self, dictionary params) throws InterruptedException
    {
        int faction = -1;
        if (utils.hasScriptVar(self, "faction"))
        {
            faction = utils.getIntScriptVar(self, "faction");
        }
        obj_id kit = utils.getObjIdScriptVar(self, "creator");
        if (!isIdValid(kit) || !exists(kit))
        {
            return SCRIPT_CONTINUE;
        }
        int patrolType = getIntObjVar(kit, "patrolType");
        int construction = getConstructionQuestsCompleted(kit);
        String npcName = "";
        if (construction >= gcw.GCW_CONSTRUCTION_MAXIMUM)
        {
            if (faction == factions.FACTION_FLAG_REBEL)
            {
                npcName = bossRebels;
            }
            else if (faction == factions.FACTION_FLAG_IMPERIAL)
            {
                npcName = bossImperials;
            }
        }
        if (getSchedulerNPCs(kit, "gcwPatrol") < 0)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = null;
        if (patrolType == 0 && getSchedulerNPCs(kit, "gcwPatrol") < 500)
        {
            obj_id currentPatrol = utils.getObjIdScriptVar(kit, "currentPatrol");
            if (!isIdValid(currentPatrol) || !exists(currentPatrol) || isIncapacitated(currentPatrol) || isDead(currentPatrol))
            {
                npc = createSchedulerNPC(kit, npcName);
                utils.setScriptVar(kit, "currentPatrol", npc);
            }
        }
        if (patrolType == 1 && getSchedulerNPCs(kit, "gcwPatrol") < 500)
        {
            npc = createSchedulerNPC(kit, npcName);
        }
        if (isIdValid(npc) && exists(npc))
        {
            detachScript(npc, "systems.vehicle_system.vehicle_base");
            detachScript(npc, "systems.vehicle_system.vehicle_ping");
        }
        messageTo(self, "handleGCWPatrol", null, 900.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int getSchedulerNPCs(obj_id kit, String npcID) throws InterruptedException
    {
        if (!isIdValid(kit) || !exists(kit))
        {
            return -1;
        }
        obj_id kitParent = trial.getParent(kit);
        if (!isIdValid(kitParent) || !exists(kitParent))
        {
            return -1;
        }
        obj_id[] npcs = trial.getObjectsInInstanceBySpawnId(trial.getParent(kit), npcID);
        if (npcs == null)
        {
            return 0;
        }
        return npcs.length;
    }
    public obj_id createSchedulerNPC(obj_id kit, String npcName) throws InterruptedException
    {
        if (!isIdValid(kit) || !exists(kit))
        {
            return null;
        }
        obj_id kitParent = trial.getParent(kit);
        if (!isIdValid(kitParent) || !exists(kitParent))
        {
            return null;
        }
        location loc = getLocation(kit);
        obj_id npc = create.object(npcName, loc);
        if (!isIdValid(npc) || !exists(npc))
        {
            return null;
        }
        trial.markAsTempObject(npc, true);
        trial.setParent(kitParent, npc, true);
        trial.setInterest(npc);
        setObjVar(npc, "spawn_id", "gcwPatrol");
        trial.storeSpawnedChild(kitParent, npc, "gcwPatrol");
        String patrol = getStringObjVar(kit, "patrolPoint");
        if (patrol != null && patrol.length() > 0)
        {
            dictionary path_data = utils.hasScriptVar(kitParent, trial.SEQUENCER_PATH_DATA) ? utils.getDictionaryScriptVar(kitParent, trial.SEQUENCER_PATH_DATA) : null;
            if (path_data != null && !path_data.isEmpty())
            {
                utils.setScriptVar(npc, trial.SEQUENCER_PATH_DATA, utils.getDictionaryScriptVar(kitParent, trial.SEQUENCER_PATH_DATA));
            }
            setObjVar(npc, "patrol_path", patrol);
            setHibernationDelay(npc, 3600.0f);
        }
        attachScript(npc, "systems.dungeon_sequencer.ai_controller");
        return npc;
    }
    public int destroyGCWPatrol(obj_id self, dictionary params) throws InterruptedException
    {
        trial.cleanupObject(self);
        return SCRIPT_CONTINUE;
    }
    public int OnDeath(obj_id self, obj_id killer, obj_id corpseId) throws InterruptedException
    {
        handleDestroyPatrol(self, killer);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectDisabled(obj_id self, obj_id killer) throws InterruptedException
    {
        handleDestroyPatrol(self, killer);
        return SCRIPT_CONTINUE;
    }
    public void handleDestroyPatrol(obj_id self, obj_id killer) throws InterruptedException
    {
        location death = getLocation(self);
        playClientEffectObj(killer, "clienteffect/combat_explosion_lair_large.cef", self, "");
        playClientEffectLoc(killer, "clienteffect/combat_explosion_lair_large.cef", death, 0);
        setInvulnerable(self, true);
        messageTo(self, "destroyGCWPatrol", null, 1f, false);
        return;
    }
}
