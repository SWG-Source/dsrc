package script.npc;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class cb_deflarian_courier extends script.base_script
{
    public cb_deflarian_courier()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setBaseWalkSpeed(self, 6.0f);
        setHibernationDelay(self, 5.0f * 24.0f * 60.0f * 60.0f);
        return SCRIPT_CONTINUE;
    }
    public int OnHibernateBegin(obj_id self) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, "intCleanedUp"))
        {
            utils.setScriptVar(self, "intCleanedUp", 1);
            obj_id objParent = utils.getObjIdObjVar(self, "objParent");
            float fltRespawnTime = utils.getFloatScriptVar(self, "fltRespawnTime");
            messageTo(objParent, "spawnDestroyed", null, fltRespawnTime, false);
        }
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
