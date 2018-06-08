package script.quest.task.ground;

import script.dictionary;
import script.obj_id;

public class escort_on_target extends script.base_script
{
    public escort_on_target()
    {
    }
    public int messageEscortCleanup(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
