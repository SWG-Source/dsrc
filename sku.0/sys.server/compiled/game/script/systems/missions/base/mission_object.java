package script.systems.missions.base;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class mission_object extends script.systems.missions.base.mission_dynamic_base
{
    public mission_object()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        LOG("mission_spam", "Mission data is live, obj_id is " + self);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "isPlayerCreated"))
        {
            removeObjVar(self, "isPlayerCreated");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id player = getMissionHolder(self);
        if (player != null)
        {
            dictionary msgData = new dictionary();
            msgData.put("missionId", self);
            messageTo(player, "missionObjectDestroyed", msgData, 0, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnGrantMission(obj_id self, obj_id target) throws InterruptedException
    {
        debugServerConsoleMsg(self, "Assigning mission to " + target.toString());
        assignMission(self, target);
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        if (player == null || names == null || attribs == null || names.length != attribs.length)
        {
            return SCRIPT_CONTINUE;
        }
        int i = getFirstFreeIndex(names);
        String target = getMissionTargetName(self);
        if (target != null && target.length() > 0)
        {
            names[i] = "@" + new string_id("ui_mission", "attribs_target");
            attribs[i] = target;
            ++i;
        }
        names[i] = "@" + new string_id("ui_mission", "attribs_creator");
        attribs[i] = getMissionCreator(self);
        ++i;
        names[i] = "@" + new string_id("ui_mission", "attribs_reward");
        attribs[i] = "" + getMissionReward(self);
        ++i;
        int difficulty = getMissionDifficulty(self);
        if (difficulty > 0)
        {
            names[i] = "@" + new string_id("ui_mission", "attribs_difficulty");
            attribs[i] = "" + difficulty;
            ++i;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnMissionAssigned(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int objectCreated(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("missions", "Got message");
        obj_id objObject = params.getObjId("objObject");
        Vector objObjects = getResizeableObjIdArrayObjVar(self, "objObjects");
        objObjects = utils.addElement(objObjects, objObject);
        if (objObjects != null && objObjects.size() > 0)
        {
            setObjVar(self, "objObjects", objObjects);
        }
        return SCRIPT_CONTINUE;
    }
}
