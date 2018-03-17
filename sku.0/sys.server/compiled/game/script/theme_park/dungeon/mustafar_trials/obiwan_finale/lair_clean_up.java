package script.theme_park.dungeon.mustafar_trials.obiwan_finale;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.player_structure;
import script.library.space_dungeon;
import script.library.create;
import script.library.utils;
import script.library.permissions;

public class lair_clean_up extends script.base_script
{
    public lair_clean_up()
    {
    }
    public int msgSpaceDungeonCleanup(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "avatar_platform"))
        {
            removeObjVar(self, "avatar_platform");
        }
        if (self == null)
        {
            return SCRIPT_CONTINUE;
        }
        int x = 0;
        while (x < 200)
        {
            String variable = "spawned" + x;
            if (hasObjVar(self, variable))
            {
                obj_id thing = getObjIdObjVar(self, variable);
                destroyObject(thing);
            }
            x = x + 1;
        }
        obj_id entrance = getCellId(self, "mainroom");
        if (hasObjVar(self, "current_players"))
        {
            removeObjVar(self, "current_players");
        }
        return SCRIPT_CONTINUE;
    }
}
