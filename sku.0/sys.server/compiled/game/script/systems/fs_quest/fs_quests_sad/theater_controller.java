package script.systems.fs_quest.fs_quests_sad;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.fs_quests_sad;
import script.library.quests;
import script.library.utils;

public class theater_controller extends script.base_script
{
    public theater_controller()
    {
    }
    public int OnTheaterCreated(obj_id self, obj_id[] objects, obj_id player, obj_id creator) throws InterruptedException
    {
        dictionary params = new dictionary();
        params.put("player", player);
        for (int i = 0; i < objects.length; i++)
        {
            messageTo(objects[i], "OnTheaterCreated", params, 0, true);
        }
        utils.setObjVar(player, fs_quests_sad.SAD_OBJVAR_MASTER_THEATER, self);
        return SCRIPT_CONTINUE;
    }
    public int destroySelf(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
