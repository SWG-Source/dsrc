package script.theme_park.imperial;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.ai.ai_combat;
import script.library.create;

public class bldg_setup extends script.base_script
{
    public bldg_setup()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        debugSpeakMsg(self, "ATTACHING");
        createTriggerVolume("guards", 20f, true);
        messageTo(self, "spawnTerminal", null, 10, true);
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
        debugSpeakMsg(self, "MAKING A TERMINAL");
        obj_id room = getCellId(self, "mainhall");
        location termLoc = new location(0.1f, .13f, -5.46f, "tatooine", room);
        obj_id destructor = createObject("object/tangible/furniture/imperial/data_terminal_s1.iff", termLoc);
        setObjVar(self, "DESTRUCTOR", destructor);
        attachScript(destructor, "theme_park.bldg_destroy");
        return SCRIPT_CONTINUE;
    }
}
