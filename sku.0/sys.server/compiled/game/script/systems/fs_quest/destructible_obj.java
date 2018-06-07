package script.systems.fs_quest;

import script.dictionary;
import script.location;
import script.obj_id;

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
