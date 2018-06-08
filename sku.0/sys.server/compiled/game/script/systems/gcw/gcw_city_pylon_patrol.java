package script.systems.gcw;

import script.obj_id;

public class gcw_city_pylon_patrol extends script.systems.gcw.gcw_city_pylon
{
    public gcw_city_pylon_patrol()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return super.OnAttach(self);
    }
    public String getConstructionQuest() throws InterruptedException
    {
        return "gcw_construct_patrol";
    }
    public String getImperialIcon() throws InterruptedException
    {
        return "pt_gcw_quest_imperial_patrol_2.prt";
    }
    public String getRebelIcon() throws InterruptedException
    {
        return "pt_gcw_quest_rebel_patrol_2.prt";
    }
}
