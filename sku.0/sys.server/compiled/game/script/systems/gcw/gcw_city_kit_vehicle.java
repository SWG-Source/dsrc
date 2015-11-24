package script.systems.gcw;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.factions;
import script.library.utils;

public class gcw_city_kit_vehicle extends script.systems.gcw.gcw_city_kit
{
    public gcw_city_kit_vehicle()
    {
    }
    public void setupConstructionQuests(obj_id self, obj_id pylon) throws InterruptedException
    {
        setName(pylon, "Vehicle Construction Site");
        utils.setScriptVar(pylon, "gcw.name", "Vehicle Construction Site");
        attachScript(pylon, "systems.gcw.gcw_city_pylon_vehicle");
    }
    public void setupInvasionQuests(obj_id kit) throws InterruptedException
    {
    }
    public obj_id createFactionKit(int faction, location loc) throws InterruptedException
    {
        if (loc == null)
        {
            return null;
        }
        obj_id kit = null;
        if (factions.FACTION_FLAG_IMPERIAL == faction)
        {
            kit = createObject("object/tangible/destructible/gcw_imperial_vehicle.iff", loc);
            setName(kit, "Imperial Vehicle Point");
        }
        else if (factions.FACTION_FLAG_REBEL == faction)
        {
            kit = createObject("object/tangible/destructible/gcw_rebel_vehicle.iff", loc);
            setName(kit, "Rebel Vehicle Point");
        }
        return kit;
    }
}
