package script.event;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;

public class shuttle_control extends script.base_script
{
    public shuttle_control()
    {
    }
    public static final float DECAY_TIME = 60 * 60 * 8;
    public static final float[] LANDING_TIME = 
    {
        17,
        22,
        26,
        33
    };
    public int OnAttach(obj_id self) throws InterruptedException
    {
        float timeStamp = getGameTime();
        setObjVar(self, "event.shuttle.timeStamp", timeStamp);
        messageTo(self, "cleanUp", null, DECAY_TIME, false);
        return SCRIPT_CONTINUE;
    }
    public int OnAddedToWorld(obj_id self) throws InterruptedException
    {
        checkTimeLimit(self);
        return SCRIPT_CONTINUE;
    }
    public int landShuttle(obj_id self, dictionary params) throws InterruptedException
    {
        int shuttleType = getIntObjVar(self, "event.shuttle.shuttleType");
        if (shuttleType > 0)
        {
            queueCommand(self, (-1465754503), self, "", COMMAND_PRIORITY_FRONT);
            setPosture(self, POSTURE_UPRIGHT);
        }
        if (shuttleType == 0)
        {
            queueCommand(self, (-1114832209), self, "", COMMAND_PRIORITY_FRONT);
            setPosture(self, POSTURE_PRONE);
        }
        messageTo(self, "dropOffNpcs", null, LANDING_TIME[shuttleType], false);
        obj_id owner = getObjIdObjVar(self, "event.shuttle.owner");
        sendSystemMessage(owner, "SHUTTLE: I am landing.", null);
        return SCRIPT_CONTINUE;
    }
    public int takeOff(obj_id self, dictionary params) throws InterruptedException
    {
        int shuttleType = getIntObjVar(self, "event.shuttle.shuttleType");
        if (shuttleType > 0)
        {
            queueCommand(self, (-1114832209), self, "", COMMAND_PRIORITY_FRONT);
            setPosture(self, POSTURE_PRONE);
        }
        if (shuttleType == 0)
        {
            queueCommand(self, (-1465754503), self, "", COMMAND_PRIORITY_FRONT);
            setPosture(self, POSTURE_UPRIGHT);
        }
        messageTo(self, "cleanUp", null, 20, false);
        obj_id owner = getObjIdObjVar(self, "event.shuttle.owner");
        sendSystemMessage(owner, "SHUTTLE: I am taking off.", null);
        return SCRIPT_CONTINUE;
    }
    public int performFlyBy(obj_id self, dictionary params) throws InterruptedException
    {
        int shuttleType = getIntObjVar(self, "event.shuttle.shuttleType");
        messageTo(self, "landShuttle", null, 0, false);
        messageTo(self, "takeOff", null, LANDING_TIME[shuttleType] + 5, false);
        return SCRIPT_CONTINUE;
    }
    public int dropOffNpcs(obj_id self, dictionary params) throws InterruptedException
    {
        int numSpawns = getIntObjVar(self, "event.shuttle.numSpawns");
        if (numSpawns > 0)
        {
            String template = getStringObjVar(self, "event.shuttle.template");
            for (int i = 0; i < numSpawns; i++)
            {
                location here = getLocation(self);
                obj_id spawn = create.object(template, here);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int cleanUp(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id owner = getObjIdObjVar(self, "event.shuttle.owner");
        messageTo(owner, "getRidOfOwner", null, 0, false);
        sendSystemMessage(owner, "SHUTTLE: I'm deleting myself.", null);
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public void checkTimeLimit(obj_id self) throws InterruptedException
    {
        float timeStamp = getFloatObjVar(self, "event.shuttle.timeStamp");
        float rightNow = getGameTime();
        float delta = rightNow - timeStamp;
        if (delta > DECAY_TIME)
        {
            destroyObject(self);
        }
        return;
    }
}
