package script.systems.event_perk;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class shuttle extends script.base_script
{
    public shuttle()
    {
    }
    public static final String STF_FILE = "event_perk";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        float lifeSpan = getFloatObjVar(self, "event_perk.lifeSpan");
        float timeStamp = getFloatObjVar(self, "event_perk.timeStamp");
        float deathStamp = timeStamp + lifeSpan;
        float rightNow = getGameTime();
        float dieTime = deathStamp - rightNow;
        messageTo(self, "cleanUp", null, dieTime, true);
        return SCRIPT_CONTINUE;
    }
    public int landShuttle(obj_id self, dictionary params) throws InterruptedException
    {
        int shuttleType = getIntObjVar(self, "event_perk.shuttle.shuttleType");
        setObjVar(self, "event_perk.shuttle.readyTakeOff", 0);
        messageTo(self, "readyTakeOff", null, 40, false);
        if (shuttleType == 1)
        {
            queueCommand(self, (-1465754503), self, "", COMMAND_PRIORITY_FRONT);
            setPosture(self, POSTURE_UPRIGHT);
        }
        if (shuttleType == 0 || shuttleType == 2)
        {
            queueCommand(self, (-1114832209), self, "", COMMAND_PRIORITY_FRONT);
            setPosture(self, POSTURE_PRONE);
        }
        if (shuttleType == 3)
        {
            queueCommand(self, (28609318), self, "", COMMAND_PRIORITY_FRONT);
            setPosture(self, POSTURE_CROUCHED);
        }
        obj_id owner = getObjIdObjVar(self, "event_perk.owner");
        sendSystemMessage(owner, new string_id(STF_FILE, "shuttle_is_landing"));
        return SCRIPT_CONTINUE;
    }
    public int readyTakeOff(obj_id self, dictionary params) throws InterruptedException
    {
        setObjVar(self, "event_perk.shuttle.readyTakeOff", 1);
        return SCRIPT_CONTINUE;
    }
    public int takeOff(obj_id self, dictionary params) throws InterruptedException
    {
        int shuttleType = getIntObjVar(self, "event_perk.shuttle.shuttleType");
        int readyTakeOff = getIntObjVar(self, "event_perk.shuttle.readyTakeOff");
        obj_id owner = getObjIdObjVar(self, "event_perk.owner");
        if (readyTakeOff == 0)
        {
            sendSystemMessage(owner, new string_id(STF_FILE, "shuttle_not_take_off"));
            return SCRIPT_CONTINUE;
        }
        if (shuttleType == 1)
        {
            queueCommand(self, (-1114832209), self, "", COMMAND_PRIORITY_FRONT);
            setPosture(self, POSTURE_PRONE);
        }
        if (shuttleType == 0 || shuttleType > 1)
        {
            queueCommand(self, (-1465754503), self, "", COMMAND_PRIORITY_FRONT);
            setPosture(self, POSTURE_UPRIGHT);
        }
        messageTo(self, "cleanUp", null, 20, false);
        sendSystemMessage(owner, new string_id(STF_FILE, "shuttle_is_leaving"));
        return SCRIPT_CONTINUE;
    }
    public int cleanUp(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id owner = getObjIdObjVar(self, "event_perk.owner");
        obj_id beacon = getObjIdObjVar(self, "event_perk.shuttle.owner");
        messageTo(beacon, "getRidOfShuttle", null, 0, true);
        sendSystemMessage(owner, new string_id(STF_FILE, "shuttle_next_is_ready"));
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public void checkTimeLimit(obj_id self) throws InterruptedException
    {
        float lifeSpan = getFloatObjVar(self, "event_perk.lifeSpan");
        float timeStamp = getFloatObjVar(self, "event_perk.timeStamp");
        float rightNow = getGameTime();
        float delta = rightNow - timeStamp;
        if (delta > lifeSpan)
        {
            destroyObject(self);
        }
        return;
    }
    public int OnAddedToWorld(obj_id self) throws InterruptedException
    {
        checkTimeLimit(self);
        return SCRIPT_CONTINUE;
    }
}
