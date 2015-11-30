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

public class gcw_city_kit_damaged_vehicle extends script.systems.gcw.gcw_city_kit
{
    public gcw_city_kit_damaged_vehicle()
    {
    }
    public static final String[] imperialVehicles = 
    {
        "gcw_city_at_st_1",
        "gcw_city_at_st_2",
        "gcw_city_at_st_3"
    };
    public static final String[] rebelVehicles = 
    {
        "gcw_city_at_xt_1",
        "gcw_city_at_xt_2",
        "gcw_city_at_xt_3"
    };
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
        messageTo(self, "createDamagedVehicle", params, 1.0f, false);
        return null;
    }
    public int createDamagedVehicle(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id kit = getObjIdObjVar(self, "gcw.kitObj");
        if (isIdValid(kit) && exists(kit))
        {
            messageTo(self, "createDamagedVehicle", params, (float)rand(0, 30) + 15, false);
            return SCRIPT_CONTINUE;
        }
        int faction = params.getInt("faction");
        location loc = params.getLocation("location");
        if (factions.FACTION_FLAG_IMPERIAL == faction)
        {
            kit = create.staticObject(imperialVehicles[rand(0, 2)], loc);
            setName(kit, "Imperial Damaged Vehicle");
            utils.setScriptVar(kit, "gcw.name", "Imperial Damaged Vehicle");
        }
        else if (factions.FACTION_FLAG_REBEL == faction)
        {
            kit = create.staticObject(rebelVehicles[rand(0, 2)], loc);
            setName(kit, "Rebel Damaged Vehicle");
            utils.setScriptVar(kit, "gcw.name", "Rebel Damaged Vehicle");
        }
        if (isIdValid(kit))
        {
            detachScript(kit, "ai.ai");
            detachScript(kit, "ai.creature_combat");
            attachScript(kit, "systems.gcw.gcw_damaged_vehicle");
            setYaw(kit, getYaw(self));
            setObjVar(self, "gcw.kitObj", kit);
            setObjVar(self, "factionFlag", faction);
            utils.setScriptVar(kit, "faction", faction);
            utils.setScriptVar(kit, "creator", self);
            obj_id kitParent = trial.getParent(self);
            if (isIdValid(kitParent) && exists(kitParent))
            {
                trial.setParent(kitParent, kit, true);
            }
            trial.markAsTempObject(kit, true);
            trial.setInterest(kit);
            setObjVar(kit, "spawn_id", getStringObjVar(self, "spawn_id"));
            setObjVar(kit, gcw.GCW_TOOL_TEMPLATE_OBJVAR, "object/tangible/gcw/crafting_quest/gcw_vehicle_tool.iff");
            attachScript(kit, "systems.dungeon_sequencer.ai_controller");
        }
        messageTo(self, "createDamagedVehicle", params, (float)rand(0, 30) + 60, false);
        return SCRIPT_CONTINUE;
    }
    public int createRepairedVehicle(obj_id self, dictionary params) throws InterruptedException
    {
        int faction = getIntObjVar(self, "factionFlag");
        location loc = getLocation(self);
        obj_id vehicle = null;
        if (factions.FACTION_FLAG_IMPERIAL == faction)
        {
            vehicle = trial.createSchedulerNPC(self, self, imperialVehicles[rand(0, 2)]);
        }
        else if (factions.FACTION_FLAG_REBEL == faction)
        {
            vehicle = trial.createSchedulerNPC(self, self, rebelVehicles[rand(0, 2)]);
        }
        if (isIdValid(vehicle))
        {
            setObjVar(self, "gcw.kitObj", vehicle);
        }
        return SCRIPT_CONTINUE;
    }
}
