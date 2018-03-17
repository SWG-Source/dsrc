package script.library;

import script.obj_id;

public class posture extends script.base_script
{
    public posture()
    {
    }
    public static boolean isStanding(obj_id object) throws InterruptedException
    {
        return (getPosture(object) == POSTURE_UPRIGHT);
    }
    public static void stand(obj_id object) throws InterruptedException
    {
        if (!isStanding(object))
        {
            queueCommand(object, (-1465754503), object, "", COMMAND_PRIORITY_FRONT);
        }
    }
    public static boolean isKnockedDown(obj_id object) throws InterruptedException
    {
        return (getPosture(object) == POSTURE_KNOCKED_DOWN);
    }
    public static boolean isKneeling(obj_id object) throws InterruptedException
    {
        return (getPosture(object) == POSTURE_CROUCHED);
    }
    public static void kneel(obj_id object) throws InterruptedException
    {
        if (!isKneeling(object))
        {
            queueCommand(object, (28609318), object, "", COMMAND_PRIORITY_FRONT);
        }
    }
    public static boolean isProne(obj_id object) throws InterruptedException
    {
        return (getPosture(object) == POSTURE_PRONE);
    }
    public static void prone(obj_id object) throws InterruptedException
    {
        if (!isProne(object))
        {
            queueCommand(object, (-1114832209), object, "", COMMAND_PRIORITY_FRONT);
        }
    }
}
