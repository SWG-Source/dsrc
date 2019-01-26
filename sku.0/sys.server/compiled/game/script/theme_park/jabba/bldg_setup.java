package script.theme_park.jabba;

import script.dictionary;
import script.location;
import script.obj_id;

public class bldg_setup extends script.base_script
{
    public bldg_setup()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        createTriggerVolume("guards", 20.0f, true);
        messageTo(self, "spawnTerminal", null, 3, true);
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        if (!isPlayer(breacher))
        {
            return SCRIPT_CONTINUE;
        }
        if (volumeName.equals("guards"))
        {
            obj_id g1 = getObjIdObjVar(self, "guard1");
            obj_id g2 = getObjIdObjVar(self, "guard2");
            obj_id g3 = getObjIdObjVar(self, "guard3");
            obj_id g4 = getObjIdObjVar(self, "guard4");
            startCombat(g1, breacher);
            startCombat(g2, breacher);
            startCombat(g3, breacher);
            startCombat(g4, breacher);
        }
        return SCRIPT_CONTINUE;
    }
    public int spawnTerminal(obj_id self, dictionary params) throws InterruptedException
    {
        location here = getLocation(self);
        location termLoc = here;
        termLoc.z = termLoc.z + 8;
        termLoc.x = termLoc.x - 2;
        obj_id destructor = createObject("object/tangible/furniture/imperial/data_terminal_s1.iff", termLoc);
        setObjVar(self, "DESTRUCTOR", destructor);
        setObjVar(destructor, "bldg", self);
        attachScript(destructor, "theme_park.jabba.bldg_destroy");
        return SCRIPT_CONTINUE;
    }
}
