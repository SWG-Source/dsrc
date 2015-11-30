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

public class gcw_city_kit_medic extends script.systems.gcw.gcw_city_kit
{
    public gcw_city_kit_medic()
    {
    }
    public static final String[] imperialNPCs = 
    {
        "gcw_city_imperial_1",
        "gcw_city_imperial_2",
        "gcw_city_imperial_3"
    };
    public static final String[] rebelNPCs = 
    {
        "gcw_city_rebel_grunt_assault",
        "gcw_city_rebel_commando_assault",
        "gcw_city_rebel_specforce_assault"
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
        messageTo(self, "createWoundedNPC", params, 1.0f, false);
        return null;
    }
    public int createWoundedNPC(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id kit = getObjIdObjVar(self, "gcw.kitObj");
        if (isIdValid(kit) && exists(kit))
        {
            messageTo(self, "createWoundedNPC", params, (float)rand(0, 30) + 60, false);
            return SCRIPT_CONTINUE;
        }
        int faction = params.getInt("faction");
        location loc = params.getLocation("location");
        if (factions.FACTION_FLAG_IMPERIAL == faction)
        {
            kit = create.staticObject(imperialNPCs[rand(0, 2)], loc);
            setName(kit, "Wounded Imperial");
        }
        else if (factions.FACTION_FLAG_REBEL == faction)
        {
            kit = create.staticObject(rebelNPCs[rand(0, 2)], loc);
            setName(kit, "Wounded Rebel");
        }
        if (isIdValid(kit))
        {
            attachScript(kit, "systems.gcw.gcw_npc_hurt");
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
            attachScript(kit, "systems.dungeon_sequencer.ai_controller");
        }
        messageTo(self, "createWoundedNPC", params, (float)rand(0, 30) + 60, false);
        return SCRIPT_CONTINUE;
    }
    public int createHealedNPC(obj_id self, dictionary params) throws InterruptedException
    {
        int faction = getIntObjVar(self, "factionFlag");
        location loc = getLocation(self);
        obj_id npc = null;
        if (factions.FACTION_FLAG_IMPERIAL == faction)
        {
            npc = trial.createSchedulerNPC(self, self, imperialNPCs[rand(0, imperialNPCs.length - 1)]);
        }
        else if (factions.FACTION_FLAG_REBEL == faction)
        {
            npc = trial.createSchedulerNPC(self, self, rebelNPCs[rand(0, rebelNPCs.length - 1)]);
        }
        if (isIdValid(npc))
        {
            setObjVar(self, "gcw.kitObj", npc);
        }
        return SCRIPT_CONTINUE;
    }
}
