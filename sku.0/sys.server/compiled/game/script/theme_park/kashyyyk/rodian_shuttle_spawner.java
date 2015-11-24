package script.theme_park.kashyyyk;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.utils;

public class rodian_shuttle_spawner extends script.base_script
{
    public rodian_shuttle_spawner()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "doShuttleSpawnEvent", null, 9, false);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "doShuttleSpawnEvent", null, 19, false);
        return SCRIPT_CONTINUE;
    }
    public int doShuttleSpawnEvent(obj_id self, dictionary params) throws InterruptedException
    {
        int maxPop = 1;
        if (utils.hasScriptVar(self, "myCreation"))
        {
            obj_id shuttle = utils.getObjIdScriptVar(self, "myCreation");
            if (exists(shuttle))
            {
                return SCRIPT_CONTINUE;
            }
        }
        location where = getLocation(self);
        String shuttleTemplate = "object/creature/npc/theme_park/kash_rodian_shuttle.iff";
        obj_id shuttle = createObject(shuttleTemplate, where);
        if (!isIdValid(shuttle))
        {
            setName(self, "Rodian Shuttle did not spawn.");
            return SCRIPT_CONTINUE;
        }
        float spawnerYaw = getYaw(self);
        setYaw(shuttle, spawnerYaw);
        setObjVar(shuttle, "objParent", self);
        attachScript(shuttle, "theme_park.kashyyyk.kash_rodian_shuttle");
        utils.setScriptVar(self, "myCreation", shuttle);
        return SCRIPT_CONTINUE;
    }
    public int shuttleDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id deadNpc = params.getObjId("destroyedShuttle");
        if (!utils.hasScriptVar(self, "myCreation"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id shuttle = utils.getObjIdScriptVar(self, "myCreation");
        if (shuttle == deadNpc)
        {
            utils.removeScriptVar(self, "myCreation");
            messageTo(self, "doShuttleSpawnEvent", null, 9, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "myCreation"))
        {
            destroySpawnedShuttles(self);
        }
        return SCRIPT_CONTINUE;
    }
    public void destroySpawnedShuttles(obj_id self) throws InterruptedException
    {
        obj_id shuttle = utils.getObjIdScriptVar(self, "myCreation");
        if (isIdValid(shuttle))
        {
            destroyObject(shuttle);
        }
        utils.removeScriptVar(self, "myCreation");
        return;
    }
}
