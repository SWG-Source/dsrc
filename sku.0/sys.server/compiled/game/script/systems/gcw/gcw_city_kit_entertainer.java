package script.systems.gcw;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.factions;
import script.library.gcw;
import script.library.trial;
import script.library.utils;
import script.library.ai_lib;

public class gcw_city_kit_entertainer extends script.systems.gcw.gcw_city_kit
{
    public gcw_city_kit_entertainer()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public boolean hasConstructionQuests() throws InterruptedException
    {
        return false;
    }
    public void setupConstructionQuests(obj_id self, obj_id pylon) throws InterruptedException
    {
    }
    public void setupInvasionQuests(obj_id kit) throws InterruptedException
    {
    }
    public int getConstructionQuestsCompleted(obj_id pylon) throws InterruptedException
    {
        return 100;
    }
    public obj_id createFactionKit(int faction, location loc) throws InterruptedException
    {
        dictionary params = new dictionary();
        params.put("faction", faction);
        params.put("location", loc);
        obj_id self = getSelf();
        utils.setObjVar(self, "gcw.troopCounter", 0);
        messageTo(self, "createDemoralizedNpc", params, (float)rand(0, 30) + 60, false);
        return null;
    }
    public int createDemoralizedNpc(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        int troopCounter = 0;
        if (hasObjVar(self, "gcw.troopCounter"))
        {
            troopCounter = getIntObjVar(self, "gcw.troopCounter");
        }
        if (troopCounter >= 3)
        {
            messageTo(self, "createDemoralizedNpc", params, (float)rand(0, 30) + 60, false);
            return SCRIPT_CONTINUE;
        }
        troopCounter++;
        setObjVar(self, "gcw.troopCounter", troopCounter);
        obj_id kit = null;
        int faction = params.getInt("faction");
        location loc = getLocation(self);
        if (factions.FACTION_FLAG_IMPERIAL == faction)
        {
            kit = create.object("gcw_city_demoralized_imperial", loc);
            setName(kit, "Demoralized Imperial");
        }
        else if (factions.FACTION_FLAG_REBEL == faction)
        {
            kit = create.object("gcw_city_demoralized_rebel", loc);
            setName(kit, "Demoralized Rebel");
        }
        ai_lib.setDefaultCalmBehavior(kit, ai_lib.BEHAVIOR_LOITER);
        messageTo(kit, "handleSetDefaultBehavior", null, 1, false);
        if (isIdValid(kit))
        {
            setObjVar(self, "factionFlag", faction);
            utils.setScriptVar(kit, "faction", faction);
            utils.setScriptVar(kit, "creator", self);
            obj_id kitParent = trial.getParent(self);
            if (isIdValid(kitParent) && exists(kitParent))
            {
                trial.setParent(kitParent, kit, true);
                trial.markAsTempObject(kit, true);
                trial.setInterest(kit);
                setObjVar(kit, "spawn_id", getStringObjVar(self, "spawn_id"));
                attachScript(kit, "systems.gcw.gcw_entertainer_faction_quest");
                setHibernationDelay(kit, 3600.0f);
                ai_lib.setLoiterRanges(kit, 1f, 3f);
            }
        }
        messageTo(self, "createDemoralizedNpc", params, (float)rand(0, 30) + 60, false);
        return SCRIPT_CONTINUE;
    }
    public int createFightingNpc(obj_id self, dictionary params) throws InterruptedException
    {
        int faction = utils.getIntObjVar(self, "factionFlag");
        obj_id npc = null;
        obj_id convinced_trooper = params.getObjId("convincedTrooper");
        location loc = getLocation(convinced_trooper);
        if (factions.FACTION_FLAG_IMPERIAL == faction)
        {
            npc = trial.createSchedulerNPC(self, self, "gcw_city_imperial_2", loc);
        }
        else if (factions.FACTION_FLAG_REBEL == faction)
        {
            npc = trial.createSchedulerNPC(self, self, "gcw_city_rebel_commando_assault", loc);
        }
        obj_id trooperParent = utils.getObjIdScriptVar(convinced_trooper, "spawnedBy");
        setObjVar(npc, "ai_controller.behavior", 2);
        setObjVar(npc, "ai.defaultCalmBehavior", 2);
        setObjVar(npc, "gcw.entertainer_rally", 1);
        if (trooperParent == self)
        {
            destroyObject(convinced_trooper);
            if (isIdValid(npc))
            {
                int troopCounter = 0;
                if (hasObjVar(self, "gcw.troopCounter"))
                {
                    troopCounter = getIntObjVar(self, "gcw.troopCounter");
                }
                troopCounter--;
                setObjVar(self, "gcw.troopCounter", troopCounter);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
