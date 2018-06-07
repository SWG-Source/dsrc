package script.structure.municipal;

import script.library.ai_lib;
import script.library.create;
import script.location;
import script.obj_id;

public class cantina extends script.base_script
{
    public cantina()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        location behindBar = getLocation(self);
        String planet = behindBar.area;
        obj_id cantina = getCellId(self, "cantina");
        if (cantina == null)
        {
            return SCRIPT_OVERRIDE;
        }
        if(getIntObjVar(self,"intSpawnBartender") == 1) {
            behindBar = new location(8.54f, -0.89f, 0.14f, planet, cantina);
            obj_id bartender = create.object("bartender", behindBar);
            setYaw(bartender, 87);
            // removed static setting as it caused bartenders to just stay put.  They move around the bar
            // so this caused a memory leak by storing points it couldn't navigate to.
            // setCreatureStatic(bartender, true);

            setInvulnerable(bartender, true);
            attachScript(bartender, "npc.bartender.base");
            attachScript(bartender, "npc.bartender.listen");
            ai_lib.setDefaultCalmMood(bartender, "npc_imperial");
            //ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        }
        if (hasScript(self, "theme_park.lok.cantina"))
        {
            if (hasScript(self, "city.imperial_crackdown.crackdown_cantina"))
            {
                detachScript(self, "city.imperial_crackdown.crackdown_cantina");
            }
            return SCRIPT_CONTINUE;
        }
        attachScript(self, "city.imperial_crackdown.crackdown_cantina");
        return SCRIPT_CONTINUE;
    }
}
