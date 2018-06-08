package script.theme_park.nightsister;

import script.dictionary;
import script.obj_id;

public class rancor_attacker_rancor_cave extends script.base_script
{
    public rancor_attacker_rancor_cave()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "nightsisterCaveSpawn", null, 3.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int nightsisterCaveSpawn(obj_id self, dictionary params) throws InterruptedException
    {
        String cell = getStringObjVar(self, "roomname");
        obj_id topMostContainer = getTopMostContainer(self);
        obj_id cellId = getCellId(topMostContainer, cell);
        setObjVar(cellId, "rancor", self);
        return SCRIPT_CONTINUE;
    }
}
