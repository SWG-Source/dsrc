package script.theme_park.poi;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class launch extends script.theme_park.poi.base
{
    public launch()
    {
    }
    public static final String POI_SCRIPT = "poi.script";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, POI_BASE_OBJECT))
        {
            return SCRIPT_CONTINUE;
        }
        else 
        {
            messageTo(self, "handle_Spawn_Setup_Complete", null, 20, isObjectPersisted(self));
            return SCRIPT_CONTINUE;
        }
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, POI_BASE_OBJECT))
        {
            return SCRIPT_CONTINUE;
        }
        else 
        {
            messageTo(self, "handle_Spawn_Setup_Complete", null, 20, isObjectPersisted(self));
            return SCRIPT_CONTINUE;
        }
    }
    public int handle_Spawn_Setup_Complete(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "npc_lair.delayedLaunch"))
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, POI_BASE_OBJECT))
        {
            LOG("spawning", self + " hasobjVar " + POI_BASE_OBJECT);
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, POI_SCRIPT))
        {
            LOG("spawning", self + " !hasobjVar " + POI_SCRIPT);
            return SCRIPT_CONTINUE;
        }
        if (hasScript(self, "systems.spawning.spawn_template"))
        {
            setObjVar(self, "npc_lair.delayedLaunch", true);
            messageTo(self, "handleLairInRangeCheck", null, 2, false);
        }
        else 
        {
            launchPoi(self);
        }
        return SCRIPT_CONTINUE;
    }
    public void launchPoi(obj_id self) throws InterruptedException
    {
        if (!isIdValid(self) || !hasObjVar(self, POI_SCRIPT))
        {
            return;
        }
        String poiScript = getStringObjVar(self, POI_SCRIPT);
        LOG("spawning", self + " script im looking for is " + poiScript);
        if (hasScript(self, poiScript))
        {
            LOG("spawning", self + " already got one script");
            return;
        }
        setObjVar(self, POI_BASE_OBJECT, self);
        LOG("spawning", self + " setObjVar");
        attachScript(self, poiScript);
        LOG("spawning", self + " attachScript poiscript above");
        if (hasScript(self, POI_OBJECT_SCRIPT))
        {
            LOG("spawning", self + " hasScirpt " + POI_OBJECT_SCRIPT);
            debugServerConsoleMsg(self, "WARNING:  " + getName(self) + " already has the POI OBJECT script attached.  Don't.");
        }
        else 
        {
            LOG("spawning", self + " attaching Script " + POI_OBJECT_SCRIPT);
            attachScript(self, POI_OBJECT_SCRIPT);
        }
        LOG("spawning", self + " returning");
    }
    public int handleLairInRangeCheck(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] otherObjects = getAllObjectsWithTemplate(getLocation(self), 80.0f, "object/tangible/lair/npc_lair.iff");
        if (otherObjects == null || otherObjects.length < 2)
        {
            launchPoi(self);
        }
        for (int i = 0; i < otherObjects.length; i++)
        {
            if (otherObjects[i] != self)
            {
                if (hasScript(self, "systems.spawning.spawn_template"))
                {
                    destroyObject(self);
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    if (hasScript(otherObjects[i], "systems.spawning.spawn_template"))
                    {
                        destroyObject(otherObjects[i]);
                    }
                }
            }
        }
        launchPoi(self);
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "objLocationReservation"))
        {
            destroyObject(getObjIdObjVar(self, "objLocationReservation"));
            removeObjVar(self, "objLocationReservation");
        }
        return SCRIPT_CONTINUE;
    }
}
