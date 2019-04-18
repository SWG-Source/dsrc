package script.working.jfreeman;

import script.obj_id;

public class runfast extends script.base_script
{
    public runfast()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        debugSpeakMsg(self, "attached:  say 'runfast' to increase your speed");
        return SCRIPT_CONTINUE;
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        if (text.equals("runfast"))
        {
            float movePercent = getMovementPercent(self);
            debugSpeakMsg(self, "old speed: " + movePercent);
            movePercent = movePercent * 2.0f;
            setMovementPercent(self, movePercent);
            debugSpeakMsg(self, "new speed: " + movePercent);
        }
        else if (text.equals("runslow"))
        {
            float movePercent = getMovementPercent(self);
            debugSpeakMsg(self, "old speed: " + movePercent);
            movePercent = movePercent / 2.0f;
            setMovementPercent(self, movePercent);
            debugSpeakMsg(self, "new speed: " + movePercent);
        }
        return SCRIPT_CONTINUE;
    }
}
