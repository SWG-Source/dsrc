package script.systems.missions.dynamic;

import script.library.create;
import script.library.locations;
import script.location;
import script.obj_id;

public class deliver_npc_spawner extends script.base_script
{
    public deliver_npc_spawner()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        String empiredayRunning = getConfigSetting("GameServer", "empireday_ceremony");
        if (empiredayRunning != null)
        {
            if (empiredayRunning.equals("true") || empiredayRunning.equals("1"))
            {
                location here = getLocation(self);
                String city = locations.getCityName(here);
                if (city == null)
                {
                    city = locations.getGuardSpawnerRegionName(here);
                }
                if (city != null && city.length() > 0)
                {
                    if (city.equals("theed"))
                    {
                        return SCRIPT_CONTINUE;
                    }
                }
            }
        }
        location here = getLocation(self);
        obj_id npc = create.object("commoner", here);
        attachScript(npc, "systems.missions.dynamic.mission_deliver_npc");
        return SCRIPT_CONTINUE;
    }
}
