package script.systems.fs_quest;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.battlefield;
import script.library.player_structure;
import script.library.factions;
import script.library.sui;
import script.library.utils;

public class destructible_obj extends script.base_script
{
    public destructible_obj()
    {
    }
    public int OnObjectDisabled(obj_id self, obj_id killer) throws InterruptedException
    {
        location loc = getLocation(self);
        playClientEffectLoc(killer, "clienteffect/combat_explosion_lair_large.cef", loc, 0);
        messageTo(self, "campObjDestroyed", null, 2.5f, false);
        return SCRIPT_CONTINUE;
    }
    public int campObjDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
