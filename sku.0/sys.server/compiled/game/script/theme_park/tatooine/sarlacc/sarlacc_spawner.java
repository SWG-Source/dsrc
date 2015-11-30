package script.theme_park.tatooine.sarlacc;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class sarlacc_spawner extends script.base_script
{
    public sarlacc_spawner()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "sarlacc"))
        {
            location sarlaccPit = new location(-6173, 6, -3361, "tatooine", null);
            obj_id sarlacc = createObject("object/mobile/sarlacc.iff", sarlaccPit);
            setObjVar(sarlacc, "spawner", self);
            attachScript(sarlacc, "theme_park.tatooine.sarlacc.sarlacc_death");
            setObjVar(self, "sarlacc", "done");
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "sarlacc"))
        {
            location sarlaccPit = new location(-6173, 6, -3361, "tatooine", null);
            obj_id sarlacc = createObject("object/mobile/sarlacc.iff", sarlaccPit);
            setObjVar(sarlacc, "spawner", self);
            attachScript(sarlacc, "theme_park.tatooine.sarlacc.sarlacc_death");
            setObjVar(self, "sarlacc", "done");
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int sarlaccDied(obj_id self, dictionary params) throws InterruptedException
    {
        location sarlaccPit = new location(-6173, 6, -3361, "tatooine", null);
        obj_id sarlacc = createObject("object/mobile/sarlacc.iff", sarlaccPit);
        setObjVar(sarlacc, "spawner", self);
        attachScript(sarlacc, "theme_park.tatooine.sarlacc.sarlacc_death");
        return SCRIPT_CONTINUE;
    }
}
