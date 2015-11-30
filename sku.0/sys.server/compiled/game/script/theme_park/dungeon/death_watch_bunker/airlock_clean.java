package script.theme_park.dungeon.death_watch_bunker;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;

public class airlock_clean extends script.base_script
{
    public airlock_clean()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleAttackerCleanUp", null, 300f, false);
        return SCRIPT_CONTINUE;
    }
    public int handleAttackerCleanUp(obj_id self, dictionary params) throws InterruptedException
    {
        if (ai_lib.isInCombat(self))
        {
            messageTo(self, "handleAttackerCleanUp", null, 300f, false);
            return SCRIPT_CONTINUE;
        }
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
