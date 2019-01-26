package script.theme_park.dungeon.death_watch_bunker;

import script.dictionary;
import script.library.ai_lib;
import script.location;
import script.obj_id;

public class rc_mouse extends script.base_script
{
    public rc_mouse()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setInvulnerable(self, true);
        return SCRIPT_CONTINUE;
    }
    public int moveLeft(obj_id self, dictionary params) throws InterruptedException
    {
        location here = getLocation(self);
        int dist = params.getInt("distance");
        if (dist == 0)
        {
            dist = 1;
        }
        if (dist > 10)
        {
            dist = 10;
        }
        here.z = here.z + dist;
        ai_lib.aiPathTo(self, here);
        return SCRIPT_CONTINUE;
    }
    public int moveRight(obj_id self, dictionary params) throws InterruptedException
    {
        location here = getLocation(self);
        int dist = params.getInt("distance");
        if (dist == 0)
        {
            dist = 1;
        }
        if (dist > 10)
        {
            dist = 10;
        }
        here.z = here.z - dist;
        ai_lib.aiPathTo(self, here);
        return SCRIPT_CONTINUE;
    }
    public int moveForward(obj_id self, dictionary params) throws InterruptedException
    {
        location here = getLocation(self);
        int dist = params.getInt("distance");
        if (dist == 0)
        {
            dist = 1;
        }
        if (dist > 10)
        {
            dist = 10;
        }
        here.x = here.x + dist;
        ai_lib.aiPathTo(self, here);
        return SCRIPT_CONTINUE;
    }
    public int moveBackward(obj_id self, dictionary params) throws InterruptedException
    {
        location here = getLocation(self);
        int dist = params.getInt("distance");
        if (dist == 0)
        {
            dist = 1;
        }
        if (dist > 10)
        {
            dist = 10;
        }
        here.x = here.x - dist;
        ai_lib.aiPathTo(self, here);
        return SCRIPT_CONTINUE;
    }
    public int detonate(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id viewer = params.getObjId("player");
        location loc = getLocation(self);
        playClientEffectLoc(viewer, "clienteffect/combat_explosion_lair_large.cef", loc, 0);
        obj_id[] objects = getObjectsInRange(self, 3.0f);
        if (objects == null)
        {
            destroyObject(self);
            return SCRIPT_CONTINUE;
        }
        int numObjects = objects.length;
        if (numObjects <= 1)
        {
            destroyObject(self);
            return SCRIPT_CONTINUE;
        }
        for (obj_id thing : objects) {
            String name = getTemplateName(thing);
            if (name.equals("object/tangible/dungeon/death_watch_bunker/invulnerable_debris.iff")) {
                int spawnNum = getIntObjVar(thing, "spawn_number");
                obj_id mom = getObjIdObjVar(thing, "mom");
                if (mom != null && spawnNum != 0) {
                    dictionary info = new dictionary();
                    info.put("spawnNumber", spawnNum);
                    info.put("spawnMob", thing);
                    messageTo(mom, "tellingMomIDied", info, 600, false);
                }
                destroyObject(thing);
                destroyObject(self);
                return SCRIPT_CONTINUE;
            }
        }
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
