package script.theme_park.dungeon.death_watch_bunker;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class debris_reload_check2 extends script.base_script
{
    public debris_reload_check2()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        boolean empty = true;
        obj_id mom = getTopMostContainer(self);
        obj_id room = getCellId(mom, "medroom37");
        obj_id[] cell_contents = getContents(room);
        int numInCell = cell_contents.length;
        if (cell_contents != null && numInCell > 0)
        {
            for (int i = 0; i < numInCell; i++)
            {
                if (isIdValid(cell_contents[i]) && isPlayer(cell_contents[i]))
                {
                    empty = false;
                }
            }
        }
        if (empty != true)
        {
            destroyObject(self);
            int spawnNum = getIntObjVar(self, "spawn_number");
            obj_id dad = getObjIdObjVar(self, "mom");
            if (dad != null && spawnNum != 0)
            {
                dictionary info = new dictionary();
                info.put("spawnNumber", spawnNum);
                info.put("spawnMob", self);
                messageTo(dad, "tellingMomIDied", info, 600, false);
            }
        }
        else 
        {
            createTerminal(self);
        }
        return SCRIPT_CONTINUE;
    }
    public void createTerminal(obj_id self) throws InterruptedException
    {
        location here = getLocation(self);
        String planet = here.area;
        obj_id top = getTopMostContainer(self);
        obj_id cell = getCellId(top, "hall36");
        location terminal = new location(115.0f, -64, -155, planet, cell);
        obj_id term = createObject("object/tangible/dungeon/terminal_free_s1.iff", terminal);
        setYaw(term, 180);
        setObjVar(self, "term", term);
        setObjVar(term, "mom", self);
        attachScript(term, "theme_park.dungeon.death_watch_bunker.debris_terminal");
        return;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "term"))
        {
            obj_id terminal = getObjIdObjVar(self, "term");
            if (isIdValid(terminal))
            {
                messageTo(terminal, "cleanUpTime", null, 30, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
