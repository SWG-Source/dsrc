package script.quest.task.ground.util;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class spawned_item_destroyed_tracker extends script.base_script
{
    public spawned_item_destroyed_tracker()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleSpawnedItemDestroySelf", null, 16, false);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleSpawnedItemDestroySelf", null, 16, false);
        return SCRIPT_CONTINUE;
    }
    public int handleSpawnedItemDestroySelf(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, "intCleanedUp"))
        {
            utils.setScriptVar(self, "intCleanedUp", 1);
            if (hasObjVar(self, "objParent"))
            {
                obj_id objParent = getObjIdObjVar(self, "objParent");
                if (isIdValid(objParent))
                {
                    dictionary webster = new dictionary();
                    webster.put("objectTemplateName", getTemplateName(self));
                    messageTo(objParent, "spawnDestroyed", webster, 1, false);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
