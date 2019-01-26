package script.space.load_test;

import script.dictionary;
import script.library.ship_ai;
import script.library.utils;
import script.obj_id;

public class load_test_ai extends script.base_script
{
    public load_test_ai()
    {
    }
    public static final float PING_TIME = 120.0f;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        obj_id followTarget = utils.getObjIdScriptVar(self, "followTarget");
        if (isIdValid(followTarget))
        {
            ship_ai.spaceFollow(self, followTarget, 15.0f);
        }
        messageTo(self, "ping", null, 0, false);
        return SCRIPT_CONTINUE;
    }
    public int ping(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id followTarget = utils.getObjIdScriptVar(self, "followTarget");
        if (!isIdValid(followTarget) || !followTarget.isLoaded())
        {
            destroyObject(self);
        }
        else 
        {
            messageTo(self, "ping", null, PING_TIME, false);
        }
        return SCRIPT_CONTINUE;
    }
}
