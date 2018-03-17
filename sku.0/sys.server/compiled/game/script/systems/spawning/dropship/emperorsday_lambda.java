package script.systems.spawning.dropship;

import script.dictionary;
import script.location;
import script.obj_id;

public class emperorsday_lambda extends script.systems.spawning.dropship.base
{
    public emperorsday_lambda()
    {
    }
    public static final boolean LOGGING_ON = true;
    public static final String LOGGING_CATEGORY = "empireday";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        blog("emperorsday_lambda.OnAttach: Init");
        messageTo(self, "handleAttachDelay", null, 2f, false);
        setHibernationDelay(self, 600.0f);
        return super.OnAttach(self);
    }
    public int handleAttachDelay(obj_id self, dictionary params) throws InterruptedException
    {
        blog("emperorsday_lambda.handleAttachDelay: Init");
        stop(self);
        setPosture(self, POSTURE_PRONE);
        messageTo(self, "takeOff", null, 600f, true);
        queueCommand(self, (-1465754503), self, "", COMMAND_PRIORITY_FRONT);
        return SCRIPT_CONTINUE;
    }
    public int changePosture(obj_id self, dictionary params) throws InterruptedException
    {
        blog("emperorsday_lambda.changePosture: Init");
        setPosture(self, POSTURE_UPRIGHT);
        location ownLocation = getLocation(self);
        ownLocation.z = ownLocation.z - 20.0f;
        faceTo(self, ownLocation);
        messageTo(self, "selfCleanUp", null, 60f, false);
        return SCRIPT_CONTINUE;
    }
    public int takeOff(obj_id self, dictionary params) throws InterruptedException
    {
        blog("emperorsday_lambda.takeOff: Init");
        obj_id exitPoint = getObjIdObjVar(self, "exitpoint");
        if (isValidId(exitPoint))
        {
            blog("emperorsday_lambda.changePosture: Facing to exit location");
            faceTo(self, getLocation(exitPoint));
            messageTo(self, "moveToExitPoint", null, 2, false);
            return SCRIPT_CONTINUE;
        }
        messageTo(self, "changePosture", null, 10, false);
        return SCRIPT_CONTINUE;
    }
    public int moveToExitPoint(obj_id self, dictionary params) throws InterruptedException
    {
        blog("emperorsday_lambda.moveToExitPoint init");
        obj_id exitPoint = getObjIdObjVar(self, "exitpoint");
        if (!isValidId(exitPoint))
        {
            messageTo(self, "changePosture", null, 1, false);
            return SCRIPT_CONTINUE;
        }
        setMovementPercent(self, 16);
        blog("emperorsday_lambda.moveToExitPoint exit point valid");
        pathTo(self, getLocation(exitPoint));
        messageTo(self, "correctTakeOffThenCleanUp", null, 10, false);
        return SCRIPT_CONTINUE;
    }
    public int correctTakeOffThenCleanUp(obj_id self, dictionary params) throws InterruptedException
    {
        blog("emperorsday_lambda.correctTakeOffThenCleanUp init");
        setPosture(self, POSTURE_UPRIGHT);
        blog("emperorsday_lambda.correctTakeOffThenCleanUp changePosture");
        messageTo(self, "selfCleanUp", null, 60f, false);
        return SCRIPT_CONTINUE;
    }
    public int selfCleanUp(obj_id self, dictionary params) throws InterruptedException
    {
        if (isIdValid(self))
        {
            destroyObject(self);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public boolean blog(String msg) throws InterruptedException
    {
        if (LOGGING_ON && msg != null && !msg.equals(""))
        {
            LOG(LOGGING_CATEGORY, msg);
        }
        return true;
    }
}
