package script.systems.gcw;

import script.library.gcw;
import script.library.resource;
import script.obj_id;

public class gcw_city_pylon_vehicle_boss extends script.systems.gcw.gcw_city_pylon
{
    public gcw_city_pylon_vehicle_boss()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setObjVar(self, gcw.GCW_TOOL_TEMPLATE_OBJVAR, "object/tangible/gcw/crafting_quest/gcw_vehicle_tool.iff");
        return super.OnAttach(self);
    }
    public String getConstructionQuest() throws InterruptedException
    {
        return "gcw_construct_vehicle_boss";
    }
    public boolean hasConstructionResources(obj_id player) throws InterruptedException
    {
        if (resource.hasResource(player, "metal", 10000))
        {
            return true;
        }
        return false;
    }
    public boolean consumeConstructionResources(obj_id player) throws InterruptedException
    {
        if (resource.consumeResource(player, "metal", 10000))
        {
            return true;
        }
        return false;
    }
    public String getImperialIcon() throws InterruptedException
    {
        return "pt_gcw_quest_imperial_vehicle_boss.prt";
    }
    public String getRebelIcon() throws InterruptedException
    {
        return "pt_gcw_quest_rebel_vehicle_boss.prt";
    }
}
