package script.beta;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.cloninglib;

public class clone_debug extends script.base_script
{
    public clone_debug()
    {
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        if (text.equals("info"))
        {
            String planetName = getCurrentSceneName();
            obj_id planet = getPlanetByName(planetName);
            deltadictionary planetVars = planet.getScriptVars();
            location[] loc = planetVars.getLocationArray(cloninglib.VAR_PLANET_CLONE_LOC);
            location[] spawn = planetVars.getLocationArray(cloninglib.VAR_PLANET_CLONE_RESPAWN);
            obj_id[] id = planetVars.getObjIdArray(cloninglib.VAR_PLANET_CLONE_ID);
            for (int i = 0; i < loc.length; ++i)
            {
                debugConsoleMsg(self, "loc: " + loc[i] + spawn[i] + id[i]);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
