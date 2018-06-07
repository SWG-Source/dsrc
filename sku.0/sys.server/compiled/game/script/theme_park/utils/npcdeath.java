package script.theme_park.utils;

import script.dictionary;
import script.obj_id;

public class npcdeath extends script.base_script
{
    public npcdeath()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "quests.target"))
        {
            detachScript(self, "theme_park.utils.npcdeath");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "quests.target"))
        {
            detachScript(self, "theme_park.utils.npcdeath");
            return SCRIPT_CONTINUE;
        }
        obj_id target = getObjIdObjVar(self, "quests.target");
        String msgHandlerName = getStringObjVar(self, "quests.msgHandlerName");
        messageTo(target, msgHandlerName, null, 0, true);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        setHealth(self, -5000);
        messageTo(self, "killMe", null, 5, true);
        return SCRIPT_CONTINUE;
    }
    public int killMe(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
