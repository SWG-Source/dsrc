package script.faction_perk.hq;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.hq;
import script.library.utils;
import script.library.ai_lib;
import script.library.create;
import script.library.factions;
import script.library.uberlair;
import script.library.locations;

public class spawn_egg extends script.base_script
{
    public spawn_egg()
    {
    }
    public static final String VAR_SPAWN_PATHPOINTS = hq.VAR_SPAWN_BASE + ".pathPoints";
    public static final String VAR_SPAWN_FORMATION = hq.VAR_SPAWN_BASE + ".formation";
    public static final String VAR_CLEANING_UP = hq.VAR_SPAWN_BASE + ".cleaningUp";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        createPatrolPathPoints(self);
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        setObjVar(self, VAR_CLEANING_UP, 1);
        obj_id[] children = getObjIdArrayObjVar(self, hq.VAR_SPAWN_CHILDREN);
        if (children == null || children.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        utils.destroyObjects(children);
        return SCRIPT_CONTINUE;
    }
    public int handleSpawnRequest(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, hq.VAR_SPAWN_CHILDREN))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] children = loadSpawns(self);
        if (children != null && children.length > 0)
        {
            setObjVar(self, hq.VAR_SPAWN_CHILDREN, children);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleLeaderIncapacitated(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, VAR_CLEANING_UP))
        {
            return SCRIPT_CONTINUE;
        }
        Vector children = getResizeableObjIdArrayObjVar(self, hq.VAR_SPAWN_CHILDREN);
        if (children == null || children.size() == 0)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id oid = params.getObjId("oid");
        if (!isIdValid(oid))
        {
            return SCRIPT_CONTINUE;
        }
        children = utils.removeElement(children, oid);
        if (children != null || children.size() > 0)
        {
            int pos = 0;
            obj_id leader = null;
            int formation = getIntObjVar(self, VAR_SPAWN_FORMATION);
            for (int i = 0; i < children.size(); i++)
            {
                if (!ai_lib.aiIsDead(((obj_id)children.get(i))))
                {
                    if (!isIdValid(leader))
                    {
                        leader = ((obj_id)children.get(i));
                        setObjVar(leader, hq.VAR_SPAWN_LEADER, true);
                        if (hasObjVar(self, VAR_SPAWN_PATHPOINTS))
                        {
                            location[] pathpoints = getLocationArrayObjVar(self, VAR_SPAWN_PATHPOINTS);
                            if (pathpoints != null && pathpoints.length > 0)
                            {
                                ai_lib.setPatrolPath(leader, pathpoints);
                            }
                        }
                        else 
                        {
                            ai_lib.setDefaultCalmBehavior(leader, ai_lib.BEHAVIOR_LOITER);
                        }
                    }
                    else 
                    {
                        ai_lib.followInFormation(((obj_id)children.get(i)), leader, formation, pos);
                    }
                    pos++;
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleChildDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, VAR_CLEANING_UP))
        {
            return SCRIPT_CONTINUE;
        }
        Vector children = getResizeableObjIdArrayObjVar(self, hq.VAR_SPAWN_CHILDREN);
        if (children == null || children.size() == 0)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id oid = params.getObjId("oid");
        if (!isIdValid(oid))
        {
            return SCRIPT_CONTINUE;
        }
        int original_count = getIntObjVar(self, hq.VAR_SPAWN_COUNT);
        children = utils.removeElement(children, oid);
        if (children == null || children.size() == 0 || original_count == 0 || children.size() < original_count / 4)
        {
            removeObjVar(self, hq.VAR_SPAWN_CHILDREN);
            float delay = 3600;
            if (getTopMostContainer(self) != self)
            {
                delay = 3600;
            }
            messageTo(self, "handleSpawnRequest", null, delay, false);
        }
        else 
        {
            setObjVar(self, hq.VAR_SPAWN_CHILDREN, children);
        }
        return SCRIPT_CONTINUE;
    }
    public obj_id[] loadSpawns(obj_id self) throws InterruptedException
    {
        int spawnType = getIntObjVar(self, hq.VAR_SPAWN_TYPE);
        String faction = toLower(factions.getFaction(self));
        if (faction == null || faction.equals(""))
        {
            return null;
        }
        obj_id parent = getObjIdObjVar(self, hq.VAR_SPAWN_PARENT);
        if (!isIdValid(parent))
        {
            return null;
        }
        String template = utils.getTemplateFilenameNoPath(parent);
        if (template == null || template.equals(""))
        {
            return null;
        }
        String tbl = hq.TBL_SPAWN_EGG_PATH + template;
        String[] spawnList = null;
        switch (spawnType)
        {
            case hq.ST_MEDIUM:
            spawnList = dataTableGetStringColumnNoDefaults(tbl, "medium");
            break;
            case hq.ST_LARGE:
            spawnList = dataTableGetStringColumnNoDefaults(tbl, "large");
            break;
            case hq.ST_SMALL:
            case hq.ST_NONE:
            default:
            spawnList = dataTableGetStringColumnNoDefaults(tbl, "small");
            break;
        }
        if (spawnList == null || spawnList.length == 0)
        {
            return null;
        }
        String spawnKey = getStringObjVar(self, hq.VAR_SPAWN_TEMPLATE);
        if (spawnKey == null || spawnKey.equals(""))
        {
            spawnKey = spawnList[rand(0, spawnList.length - 1)];
            if (spawnKey == null || spawnKey.equals(""))
            {
                return null;
            }
        }
        location here = getLocation(self);
        location spawnLoc = locations.getGoodLocationAroundLocation(here, 1f, 1f, 4f, 4f);
        if (spawnLoc == null)
        {
            spawnLoc = here;
        }
        Vector spawns = new Vector();
        spawns.setSize(0);
        if (spawnKey.startsWith("formation:"))
        {
            String[] args = split(spawnKey, ':');
            if (args == null || args.length != 3)
            {
                return null;
            }
            String tbl_formation = hq.TBL_SPAWN_FORMATION_PATH + args[1] + ".iff";
            String col = args[2];
            String[] tmpkeys = dataTableGetStringColumnNoDefaults(tbl_formation, col);
            if (tmpkeys == null || tmpkeys.length == 0)
            {
                return null;
            }
            Vector keys = new Vector();
            if (keys == null || tmpkeys.length == 0)
            {
                return null;
            }
            int formation = utils.stringToInt(((String)keys.get(0)));
            if (formation < ai_lib.FORMATION_COLUMN)
            {
                return null;
            }
            setObjVar(self, VAR_SPAWN_FORMATION, formation);
            keys = utils.removeElementAt(keys, 0);
            obj_id leader = null;
            float dtheta = 30;
            if (keys.size() > 1)
            {
                dtheta = 360f / (keys.size() - 1);
            }
            location baseLoc = null;
            for (int i = 0; i < keys.size(); i++)
            {
                if (i == 0)
                {
                    baseLoc = spawnLoc;
                }
                else 
                {
                    spawnLoc = utils.rotatePointXZ(baseLoc, 0.75f, dtheta * i);
                }
                obj_id spawn = createSpawn(((String)keys.get(i)), spawnLoc);
                if (isIdValid(spawn))
                {
                    spawns = utils.addElement(spawns, spawn);
                    if (isIdValid(leader))
                    {
                        ai_lib.followInFormation(spawn, leader, formation, i);
                    }
                    else 
                    {
                        leader = spawn;
                        setObjVar(spawn, hq.VAR_SPAWN_LEADER, true);
                        if (hasObjVar(self, VAR_SPAWN_PATHPOINTS))
                        {
                            location[] pathpoints = getLocationArrayObjVar(self, VAR_SPAWN_PATHPOINTS);
                            if (pathpoints != null && pathpoints.length > 0)
                            {
                                ai_lib.setPatrolPath(leader, pathpoints);
                            }
                        }
                        else 
                        {
                            ai_lib.setDefaultCalmBehavior(leader, ai_lib.BEHAVIOR_LOITER);
                        }
                    }
                }
                else 
                {
                    LOG("hq", "unable to create formation spawn: " + ((String)keys.get(i)));
                }
                spawnLoc = null;
            }
        }
        else 
        {
            obj_id spawn = createSpawn(spawnKey, spawnLoc);
            if (isIdValid(spawn))
            {
                spawns = utils.addElement(spawns, spawn);
                float yaw = getFloatObjVar(self, hq.VAR_SPAWN_YAW);
                setYaw(spawn, yaw);
                if (getTopMostContainer(self) == self)
                {
                    ai_lib.setDefaultCalmBehavior(spawn, ai_lib.BEHAVIOR_LOITER);
                }
            }
        }
        if (spawns == null || spawns.size() == 0)
        {
            return null;
        }
        setObjVar(self, hq.VAR_SPAWN_COUNT, spawns.size());
        obj_id[] _spawns = new obj_id[0];
        if (spawns != null)
        {
            _spawns = new obj_id[spawns.size()];
            spawns.toArray(_spawns);
        }
        return _spawns;
    }
    public obj_id createSpawn(String type, location there) throws InterruptedException
    {
        if (type == null || type.equals(""))
        {
            return null;
        }
        if (there == null)
        {
            obj_id self = getSelf();
            there = getLocation(self);
        }
        obj_id spawn = create.object(type, there);
        if (isIdValid(spawn))
        {
            obj_id self = getSelf();
            attachScript(spawn, hq.SCRIPT_SPAWN_CHILD);
            setObjVar(spawn, hq.VAR_SPAWN_PARENT, self);
            return spawn;
        }
        return null;
    }
    public void createPatrolPathPoints(obj_id self) throws InterruptedException
    {
        if (getTopMostContainer(self) != self)
        {
            return;
        }
        obj_id parent = getObjIdObjVar(self, hq.VAR_SPAWN_PARENT);
        if (!isIdValid(parent))
        {
            return;
        }
        float maxTheaterSpawn = getFloatObjVar(parent, "poi.fltSize") - 20f;
        location here = getLocation(self);
        location there = getLocation(parent);
        if (getDistance(here, there) > maxTheaterSpawn)
        {
            location[] pathpoints = new location[rand(4, 8)];
            float dTheta = 360f / pathpoints.length;
            float patrol_distance = maxTheaterSpawn + rand(15f, 30f);
            float minDistance = Float.POSITIVE_INFINITY;
            int closeIndex = 0;
            for (int i = 0; i < pathpoints.length; i++)
            {
                pathpoints[i] = utils.rotatePointXZ(there, patrol_distance, (i * dTheta) - 180f);
                float dist = getDistance(here, pathpoints[i]);
                if (dist < minDistance)
                {
                    minDistance = dist;
                    closeIndex = i;
                }
            }
            location[] toUse = new location[pathpoints.length];
            for (int i = 0; i < pathpoints.length; i++)
            {
                int idx = i + closeIndex;
                if (idx < pathpoints.length)
                {
                }
                else 
                {
                    idx -= pathpoints.length;
                }
                toUse[i] = pathpoints[idx];
            }
            setObjVar(self, VAR_SPAWN_PATHPOINTS, toUse);
        }
    }
    public int handleParentCleanup(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, hq.VAR_SPAWN_CHILDREN))
        {
            destroyObject(self);
            return SCRIPT_CONTINUE;
        }
        Vector children = getResizeableObjIdArrayObjVar(self, hq.VAR_SPAWN_CHILDREN);
        if (children == null || children.size() == 0)
        {
            destroyObject(self);
            return SCRIPT_CONTINUE;
        }
        for (int i = 0; i < children.size(); i++)
        {
            if (ai_lib.isInCombat(((obj_id)children.get(i))))
            {
                messageTo(self, "handleParentCleanup", null, 600, false);
                return SCRIPT_CONTINUE;
            }
        }
        destroyObject(self);
        messageTo(self, "handleParentCleanup", null, 600, false);
        return SCRIPT_CONTINUE;
    }
}
