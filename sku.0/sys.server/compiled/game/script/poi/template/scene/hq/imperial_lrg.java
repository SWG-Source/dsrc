package script.poi.template.scene.hq;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.utils;
import script.library.theater;
import script.library.create;

public class imperial_lrg extends script.base_script
{
    public imperial_lrg()
    {
    }
    public static final boolean IS_PERSISTED = true;
    public static final String SENTINEL_GUARD = "stormtrooper";
    public static final String[] SENTINEL = 
    {
        "world:3.0,0,24.5,0",
        "world:-3.0,0,24.5,0",
        "foyer1:12.3,1,22.5,-135",
        "foyer1:-12.3,1,22.5,135",
        "mainhall:17,1,10.25,180",
        "mainhall:-17,1,10.25,180",
        "mainhall:2,1,-15.5,0",
        "mainhall:-2,1,-15.5,0",
        "jailcell1:11,1,-16.75,180",
        "jailcell1:18,1,-16.75,180",
        "jailcell2:-11,1,-16.75,180",
        "jailcell2:-18,1,-16.75,180",
        "foyer2:7,7,13,180",
        "foyer2:13,7,13,180",
        "foyer2:7,7,6.6,0",
        "foyer2:13,7,6.6,0",
        "world:16,0,-27.5,90",
        "world:16,0,-32,90"
    };
    public static final String PATROL_GUARD = "stormtrooper";
    public static final String[] PATROL = 
    {
        "world:21,7,21,45",
        "world:-21,7,21,-45",
        "world:21,7,-21,135",
        "world:-21,7,-21,-135",
        "world:0,0,-26,180"
    };
    public static final String[] REINFORCEMENTS = 
    {
        "imperial_drop_squad"
    };
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (IS_PERSISTED)
        {
            persistObject(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        cleanupTheater(self);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (IS_PERSISTED)
        {
            setupTheater(self);
        }
        else 
        {
            destroyObject(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnUnloadedFromMemory(obj_id self) throws InterruptedException
    {
        cleanupTheater(self);
        return SCRIPT_CONTINUE;
    }
    public boolean setupTheater(obj_id self) throws InterruptedException
    {
        LOG("imperial_lrg", "**************** SETUP: " + getGameTime() + " **************");
        if ((self == null) || (self == obj_id.NULL_ID))
        {
            self = getSelf();
        }
        Vector children = new Vector();
        children.setSize(0);
        location myLoc = getLocation(self);
        for (int i = 0; i < SENTINEL.length; i++)
        {
            obj_id child = spawn(self, SENTINEL_GUARD, SENTINEL[i], ai_lib.BEHAVIOR_SENTINEL);
            if ((child == null) || (child == obj_id.NULL_ID))
            {
            }
            else 
            {
                children = utils.addElement(children, child);
                ai_lib.setDefaultCalmBehavior(child, ai_lib.BEHAVIOR_SENTINEL);
            }
        }
        for (int i = 0; i < PATROL.length; i++)
        {
            obj_id child = spawn(self, PATROL_GUARD, PATROL[i], ai_lib.BEHAVIOR_SENTINEL);
            if ((child == null) || (child == obj_id.NULL_ID))
            {
            }
            else 
            {
                children = utils.addElement(children, child);
            }
        }
        if ((children == null) || (children.size() == 0))
        {
        }
        else 
        {
            setObjVar(self, theater.VAR_CHILDREN, children);
            return true;
        }
        return false;
    }
    public boolean cleanupTheater(obj_id self) throws InterruptedException
    {
        if ((self == null) || (self == obj_id.NULL_ID))
        {
            self = getSelf();
        }
        obj_id[] children = getObjIdArrayObjVar(self, theater.VAR_CHILDREN);
        if ((children == null) || (children.length == 0))
        {
            return false;
        }
        else 
        {
            for (int i = 0; i < children.length; i++)
            {
                obj_id child = children[i];
                if ((child == null) || (child == obj_id.NULL_ID))
                {
                }
                else 
                {
                    destroyObject(child);
                }
            }
        }
        return true;
    }
    public obj_id spawn(obj_id self, String type, String sLoc, int default_behavior) throws InterruptedException
    {
        LOG("imperial_lrg", "******");
        if ((self == null) || (self == obj_id.NULL_ID))
        {
            self = getSelf();
        }
        if (sLoc.equals("") || sLoc.indexOf(":") < 0)
        {
            return null;
        }
        location myLoc = getLocation(self);
        float myYaw = getYaw(self);
        LOG("imperial_lrg", "structure yaw = " + myYaw);
        location spawnLoc = null;
        java.util.StringTokenizer st = new java.util.StringTokenizer(sLoc, ":");
        String cellName = st.nextToken();
        String coors = st.nextToken();
        st = new java.util.StringTokenizer(coors, ",");
        String sx = st.nextToken();
        String sy = st.nextToken();
        String sz = st.nextToken();
        String syaw = st.nextToken();
        float x = utils.stringToFloat(sx);
        float y = utils.stringToFloat(sy);
        float z = utils.stringToFloat(sz);
        float yaw = utils.stringToFloat(syaw);
        if ((x == Float.NEGATIVE_INFINITY) || (y == Float.NEGATIVE_INFINITY) || (z == Float.NEGATIVE_INFINITY))
        {
            LOG("imperial_lrg", "spawn @ " + sLoc + " parsed x/y/z as invalid");
            return null;
        }
        if (yaw == Float.NEGATIVE_INFINITY)
        {
            LOG("imperial_lrg", "spawn @ " + sLoc + " parsed yaw as invalid");
            return null;
        }
        if (cellName.equals("world"))
        {
            spawnLoc = new location(myLoc.x + x, 0, myLoc.z + z, myLoc.area, obj_id.NULL_ID);
            LOG("imperial_lrg", "world spawn: type = " + type);
            LOG("imperial_lrg", "world spawn: offset = " + sLoc);
            LOG("imperial_lrg", "world spawn: pre-spin spawnLoc = " + spawnLoc.toString());
            if (myYaw != 0.0f)
            {
                float dist = utils.getDistance2D(myLoc, spawnLoc);
                LOG("imperial_lrg", "world spawn: distance = " + dist);
                spawnLoc = utils.rotatePointXZ(myLoc, spawnLoc, myYaw);
                spawnLoc.y = myLoc.y + y;
                LOG("imperial_lrg", "world spawn: post-spin spawnLoc = " + spawnLoc.toString());
            }
        }
        else 
        {
            obj_id cellId = getCellId(self, cellName);
            if ((cellId == null) || (cellId == obj_id.NULL_ID))
            {
                return null;
            }
            else 
            {
                spawnLoc = new location(x, y, z, myLoc.area, cellId);
            }
        }
        obj_id npcId = create.object(type, spawnLoc);
        if ((npcId == null) || (npcId == obj_id.NULL_ID))
        {
            LOG("imperial_lrg", "unable to spawn " + type + " @ " + spawnLoc.toString());
            return null;
        }
        else 
        {
            setYaw(npcId, yaw);
            ai_lib.setDefaultCalmBehavior(npcId, default_behavior);
            LOG("imperial_lrg", "spawned (" + npcId + ")" + getName(npcId) + " at " + (getLocation(npcId)).toString());
        }
        return npcId;
    }
}
