package script.working.jfreeman;

import script.obj_id;

public class followtest extends script.base_script
{
    public followtest()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        debugSpeakMsg(self, "attached");
        return SCRIPT_CONTINUE;
    }
    public int OnHearSpeech(obj_id self, obj_id speaker, String text) throws InterruptedException
    {
        if (isPlayer(speaker) && text.equals("follow me"))
        {
            debugSpeakMsg(self, "following " + getName(speaker));
            follow(self, speaker, 1.0f, 5.0f);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnFollowTargetLost(obj_id self, obj_id target) throws InterruptedException
    {
        debugSpeakMsg(self, "follow target lost");
        return SCRIPT_OVERRIDE;
    }
    public int OnFollowWaiting(obj_id self, obj_id target) throws InterruptedException
    {
        debugSpeakMsg(self, "follow waiting");
        return SCRIPT_CONTINUE;
    }
    public int OnFollowMoving(obj_id self, obj_id target) throws InterruptedException
    {
        debugSpeakMsg(self, "follow moving");
        return SCRIPT_OVERRIDE;
    }
    public int OnFollowPathNotFound(obj_id self, obj_id target) throws InterruptedException
    {
        debugSpeakMsg(self, "follow path not found");
        return SCRIPT_OVERRIDE;
    }
}
