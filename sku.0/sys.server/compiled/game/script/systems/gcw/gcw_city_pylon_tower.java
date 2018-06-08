package script.systems.gcw;

import script.library.gcw;
import script.obj_id;

public class gcw_city_pylon_tower extends script.systems.gcw.gcw_city_pylon
{
    public gcw_city_pylon_tower()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setObjVar(self, gcw.GCW_TOOL_TEMPLATE_OBJVAR, "object/tangible/gcw/crafting_quest/gcw_tower_tool.iff");
        return super.OnAttach(self);
    }
    public String getConstructionQuest() throws InterruptedException
    {
        return "gcw_construct_tower";
    }
    public String getImperialIcon() throws InterruptedException
    {
        return "pt_gcw_quest_imperial_tower.prt";
    }
    public String getRebelIcon() throws InterruptedException
    {
        return "pt_gcw_quest_rebel_tower.prt";
    }
}
