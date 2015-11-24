package script.structure.municipal;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.ai_lib;

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
        behindBar = new location(8.54f, -0.89f, 0.14f, planet, cantina);
        obj_id bartender = create.object("bartender", behindBar);
        setYaw(bartender, 87);
        setCreatureStatic(bartender, true);
        setInvulnerable(bartender, true);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
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
