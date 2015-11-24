package script.poi.template.scene.escape_pod;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.locations;
import script.library.utils;

public class base extends script.base_script
{
    public base()
    {
    }
    public static final String VAR_THEATER_BASE = "theater";
    public static final String VAR_CHILDREN = VAR_THEATER_BASE + ".children";
    public static final String VAR_BEEN_INITIALIZED = "beenInitialized";
    public static final String POD_DOOR = "object/static/structure/general/escape_pod_door.iff";
    public static final int MAX_DEBRIS = 3;
    public static final String[] DEBRIS = 
    {
        "object/static/structure/tatooine/debris_tatt_crate_1.iff",
        "object/static/structure/tatooine/debris_tatt_crate_metal_1.iff",
        "object/tangible/container/drum/tatt_drum_1.iff"
    };
    public static final int MAX_MISC = 3;
    public static final String[] MISC = 
    {
        "object/tangible/camp/camp_cot.iff",
        "object/tangible/camp/camp_lawn_chair.iff",
        "object/tangible/camp/camp_stool_short.iff",
        "object/tangible/camp/camp_stool_tall.iff",
        "object/tangible/camp/camp_tent_s1.iff"
    };
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnAddedToWorld(obj_id self) throws InterruptedException
    {
        randomizeDebris(self);
        setupTheater(self);
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        cleanupTheater(self);
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        cleanupTheater(self);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, VAR_BEEN_INITIALIZED))
        {
            destroyObject(self);
        }
        else 
        {
            setObjVar(self, VAR_BEEN_INITIALIZED, true);
        }
        return SCRIPT_CONTINUE;
    }
    public boolean setupTheater(obj_id self) throws InterruptedException
    {
        if ((self == null) || (self == obj_id.NULL_ID))
        {
            self = getSelf();
        }
        Vector children = new Vector();
        children.setSize(0);
        location myLoc = getLocation(self);
        location loc = locations.getGoodLocationAroundLocation(myLoc, 2f, 2f, 10f, 10f);
        if (loc != null)
        {
            obj_id door = createObject(POD_DOOR, loc);
            if ((door == null) || (door == obj_id.NULL_ID))
            {
            }
            else 
            {
                randomizeDebris(door);
                children = utils.addElement(children, door);
            }
        }
        int roll = rand(1, MAX_DEBRIS);
        for (int i = 0; i < roll; i++)
        {
            int idx = rand(0, DEBRIS.length - 1);
            loc = locations.getGoodLocationAroundLocation(myLoc, 2f, 2f, 10f, 10f);
            if (loc != null)
            {
                loc.y = getHeightAtLocation(loc.x, loc.z);
                obj_id debrisId = createObject(DEBRIS[idx], loc);
                if ((debrisId == null) || (debrisId == obj_id.NULL_ID))
                {
                }
                else 
                {
                    randomizeDebris(debrisId);
                    children = utils.addElement(children, debrisId);
                }
            }
        }
        roll = rand(1, MAX_MISC);
        for (int i = 0; i < roll; i++)
        {
            int idx = rand(0, MISC.length - 1);
            loc = locations.getGoodLocationAroundLocation(myLoc, 2f, 2f, 10f, 10f);
            if (loc != null)
            {
                obj_id debrisId = createObject(MISC[idx], loc);
                if ((debrisId == null) || (debrisId == obj_id.NULL_ID))
                {
                }
                else 
                {
                    setYaw(debrisId, rand(0, 359));
                    children = utils.addElement(children, debrisId);
                }
            }
        }
        if ((children == null) || (children.size() == 0))
        {
            debugSpeakMsg(self, "no children?!");
        }
        else 
        {
            setObjVar(self, VAR_CHILDREN, children);
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
        obj_id[] children = getObjIdArrayObjVar(self, VAR_CHILDREN);
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
        return false;
    }
    public boolean randomizeDebris(obj_id target) throws InterruptedException
    {
        if ((target == null) || (target == obj_id.NULL_ID))
        {
            return false;
        }
        setYaw(target, rand(0, 359));
        return true;
    }
}
