package script.theme_park.heroic.axkva_min;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.trial;
import script.library.utils;

public class hall_fire extends script.base_script
{
    public hall_fire()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        findWarden(self);
        messageTo(self, "burn_hall", null, 2.0f, false);
        return SCRIPT_CONTINUE;
    }
    public void findWarden(obj_id self) throws InterruptedException
    {
        obj_id[] objects = trial.getObjectsInDungeonWithObjVar(trial.getTop(self), "spawn_id");
        if (objects == null || objects.length == 0)
        {
            return;
        }
        obj_id warden = null;
        for (int i = 0; i < objects.length; i++)
        {
            if (!hasObjVar(objects[i], "spawn_id"))
            {
                continue;
            }
            if ((getStringObjVar(objects[i], "spawn_id")).equals("warden"))
            {
                warden = objects[i];
            }
        }
        trial.setParent(warden, self, true);
    }
    public int burn_hall(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] players = trial.getValidTargetsInCell(trial.getTop(self), "r1");
        if (players == null || players.length == 0)
        {
            messageTo(self, "burn_hall", null, 1.0f, false);
            return SCRIPT_CONTINUE;
        }
        for (int i = 0; i < players.length; i++)
        {
            location loc = getLocation(players[i]);
            String locationData = "" + loc.x + " " + loc.y + " " + loc.z + " " + loc.cell + " " + loc.x + " " + loc.y + " " + loc.z;
            queueCommand(trial.getParent(self), (-1450748792), players[i], locationData, COMMAND_PRIORITY_DEFAULT);
        }
        messageTo(self, "burn_hall", null, 1.0f, false);
        return SCRIPT_CONTINUE;
    }
}
