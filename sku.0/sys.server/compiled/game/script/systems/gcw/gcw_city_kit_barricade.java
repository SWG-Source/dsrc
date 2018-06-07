package script.systems.gcw;

import script.library.factions;
import script.library.utils;
import script.location;
import script.obj_id;

public class gcw_city_kit_barricade extends script.systems.gcw.gcw_city_kit
{
    public gcw_city_kit_barricade()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public void setupConstructionQuests(obj_id self, obj_id pylon) throws InterruptedException
    {
        setName(pylon, "Barricade Construction Site");
        utils.setScriptVar(pylon, "gcw.name", "Barricade Construction Site");
        attachScript(pylon, "systems.gcw.gcw_city_pylon_barricade");
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
            kit = createObject("object/tangible/destructible/gcw_imperial_barricade.iff", loc);
            setName(kit, "Imperial Barricade");
        }
        else if (factions.FACTION_FLAG_REBEL == faction)
        {
            kit = createObject("object/tangible/destructible/gcw_rebel_barricade.iff", loc);
            setName(kit, "Rebel Barricade");
        }
        return kit;
    }
}
