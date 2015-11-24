package script.systems.fs_quest.patrol;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class fs_dont_kill_npc extends script.base_script
{
    public fs_dont_kill_npc()
    {
    }
    public static final float DESTROY_TIME = 600.0f;
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        if (hasObjVar(self, "quest.fs_patrol.spawned_for"))
        {
            obj_id player = getObjIdObjVar(self, "quest.fs_patrol.spawned_for");
            dictionary params = new dictionary();
            if (killer == player)
            {
                messageTo(player, "handleDontKillPatrolFSNpc", params, 0.0f, true);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        dictionary params = new dictionary();
        messageTo(self, "autoCleanup", params, DESTROY_TIME, false);
        return SCRIPT_CONTINUE;
    }
    public int autoCleanup(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
