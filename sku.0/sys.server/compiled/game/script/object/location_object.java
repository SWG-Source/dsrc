package script.object;

import script.dictionary;
import script.obj_id;

public class location_object extends script.base_script
{
    public location_object()
    {
    }
    public int handlerDestroyLocationObject(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
