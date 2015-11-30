package script.theme_park.dathomir.aurilia;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class make_them_sit extends script.base_script
{
    public make_them_sit()
    {
    }
    public int doSpawnEvent(obj_id self, dictionary params) throws InterruptedException
    {
        messageTo(self, "handleAreaSpawnerHaveThemSit", null, 2, false);
        return SCRIPT_CONTINUE;
    }
    public int handleAreaSpawnerHaveThemSit(obj_id self, dictionary params) throws InterruptedException
    {
        Vector debugSpawnList = new Vector();
        debugSpawnList.setSize(0);
        if (utils.hasScriptVar(self, "debugSpawnList"))
        {
            debugSpawnList = utils.getResizeableObjIdArrayScriptVar(self, "debugSpawnList");
        }
        if (debugSpawnList != null && debugSpawnList.size() > 0)
        {
            for (int i = 0; i < debugSpawnList.size(); i++)
            {
                obj_id spawnedNpc = ((obj_id)debugSpawnList.get(i));
                if (isIdValid(spawnedNpc) && exists(spawnedNpc))
                {
                    if (!hasScript(spawnedNpc, "theme_park.dathomir.aurilia.have_a_seat"))
                    {
                        attachScript(spawnedNpc, "theme_park.dathomir.aurilia.have_a_seat");
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
