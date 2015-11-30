package script.theme_park.corellia.content;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.create;
import script.library.space_utils;
import script.library.space_dungeon;
import script.library.spawning;
import script.library.utils;

public class meatlump_act1_spawner extends script.base_script
{
    public meatlump_act1_spawner()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "tempStartUpMarker"))
        {
            utils.removeScriptVar(self, "tempStartUpMarker");
        }
        messageTo(self, "spawnerStartUp", null, 9, true);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "spawnerStartUp", null, 11, true);
        return SCRIPT_CONTINUE;
    }
    public int spawnerStartUp(obj_id self, dictionary params) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, "tempStartUpMarker"))
        {
            if (utils.hasScriptVar(self, "myCreations"))
            {
                Vector theList = utils.getResizeableObjIdArrayScriptVar(self, "myCreations");
                for (int i = 0; i < theList.size(); ++i)
                {
                    destroyObject(((obj_id)theList.get(i)));
                }
                utils.removeScriptVar(self, "myCreations");
            }
            utils.setScriptVar(self, "tempStartUpMarker", true);
            messageTo(self, "spawnMeatlumpThugs", null, 9, true);
            messageTo(self, "removeTempStartUpObjvar", null, 39, true);
        }
        return SCRIPT_CONTINUE;
    }
    public int removeTempStartUpObjvar(obj_id self, dictionary params) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "tempStartUpMarker"))
        {
            utils.removeScriptVar(self, "tempStartUpMarker");
        }
        return SCRIPT_CONTINUE;
    }
    public int spawnMeatlumpThugs(obj_id self, dictionary params) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "myCreations"))
        {
            Vector theList = utils.getResizeableObjIdArrayScriptVar(self, "myCreations");
            if (theList == null || theList.size() < 1)
            {
                utils.removeScriptVar(self, "myCreations");
            }
            else 
            {
                return SCRIPT_CONTINUE;
            }
        }
        String meatlump = "meatlump_stooge";
        Vector myCreations = new Vector();
        myCreations.setSize(0);
        location spawnerLoc = getLocation(self);
        location meatlumpLoc1 = new location(spawnerLoc.x - 2, spawnerLoc.y, spawnerLoc.z, "corellia", obj_id.NULL_ID);
        obj_id meatlump1 = create.object(meatlump, meatlumpLoc1);
        ai_lib.setDefaultCalmBehavior(meatlump1, ai_lib.BEHAVIOR_SENTINEL);
        int meatlumpYaw1 = -90;
        setYaw(meatlump1, meatlumpYaw1);
        setObjVar(meatlump1, "objParent", self);
        attachScript(meatlump1, "theme_park.corellia.content.meatlump_act1_spawned_tracker");
        utils.addElement(myCreations, meatlump1);
        location meatlumpLoc2 = new location(spawnerLoc.x, spawnerLoc.y, spawnerLoc.z + 2, "corellia", obj_id.NULL_ID);
        obj_id meatlump2 = create.object(meatlump, meatlumpLoc2);
        ai_lib.setDefaultCalmBehavior(meatlump2, ai_lib.BEHAVIOR_SENTINEL);
        int meatlumpYaw2 = 0;
        setYaw(meatlump2, meatlumpYaw2);
        setObjVar(meatlump2, "objParent", self);
        attachScript(meatlump2, "theme_park.corellia.content.meatlump_act1_spawned_tracker");
        utils.addElement(myCreations, meatlump2);
        location meatlumpLoc3 = new location(spawnerLoc.x + 2, spawnerLoc.y, spawnerLoc.z, "corellia", obj_id.NULL_ID);
        obj_id meatlump3 = create.object(meatlump, meatlumpLoc3);
        ai_lib.setDefaultCalmBehavior(meatlump3, ai_lib.BEHAVIOR_SENTINEL);
        int meatlumpYaw3 = -45;
        setYaw(meatlump3, meatlumpYaw3);
        setObjVar(meatlump3, "objParent", self);
        attachScript(meatlump3, "theme_park.corellia.content.meatlump_act1_spawned_tracker");
        utils.addElement(myCreations, meatlump3);
        if (myCreations != null && myCreations.size() > 0)
        {
            utils.setScriptVar(self, "myCreations", myCreations);
        }
        if (utils.hasScriptVar(self, "respawnMeatlumpsDelay"))
        {
            utils.removeScriptVar(self, "respawnMeatlumpsDelay");
        }
        return SCRIPT_CONTINUE;
    }
    public int spawnMerkie(obj_id self, dictionary params) throws InterruptedException
    {
        Vector myCreations = new Vector();
        myCreations.setSize(0);
        location merkieLoc = getLocation(self);
        String merkieTemplate = "corellia_meatlump_merkie_howzat";
        obj_id merkie = create.object(merkieTemplate, merkieLoc);
        ai_lib.setDefaultCalmBehavior(merkie, ai_lib.BEHAVIOR_SENTINEL);
        int merkieYaw = 180;
        setYaw(merkie, merkieYaw);
        setObjVar(merkie, "objParent", self);
        attachScript(merkie, "theme_park.corellia.content.meatlump_act1_spawned_tracker");
        utils.addElement(myCreations, merkie);
        if (myCreations != null && myCreations.size() > 0)
        {
            utils.setScriptVar(self, "myCreations", myCreations);
        }
        return SCRIPT_CONTINUE;
    }
    public int meatlumpDead(obj_id self, dictionary params) throws InterruptedException
    {
        if (!params.containsKey("deadNpc"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id deadNpc = params.getObjId("deadNpc");
        String deadType = params.getString("deadType");
        if (isIdValid(deadNpc))
        {
            Vector meatlumpList = utils.getResizeableObjIdArrayScriptVar(self, "myCreations");
            if (meatlumpList != null && meatlumpList.size() > 0)
            {
                if (meatlumpList.contains(deadNpc))
                {
                    meatlumpList.remove(deadNpc);
                    if (meatlumpList != null && meatlumpList.size() > 0)
                    {
                        utils.setScriptVar(self, "myCreations", meatlumpList);
                    }
                    else 
                    {
                        utils.removeScriptVar(self, "myCreations");
                        if (deadType.equals("meatlump_stooge"))
                        {
                            messageTo(self, "spawnMerkie", null, 2, true);
                        }
                        else 
                        {
                            int delay = rand(199, 249);
                            utils.setScriptVar(self, "respawnMeatlumpsDelay", delay);
                            messageTo(self, "spawnMeatlumpThugs", null, delay, true);
                        }
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
