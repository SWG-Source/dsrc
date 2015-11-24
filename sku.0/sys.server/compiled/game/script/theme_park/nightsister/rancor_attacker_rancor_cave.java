package script.theme_park.nightsister;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.utils;
import script.library.attrib;

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
