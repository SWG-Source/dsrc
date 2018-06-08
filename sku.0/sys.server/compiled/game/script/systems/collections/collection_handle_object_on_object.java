package script.systems.collections;

import script.dictionary;
import script.obj_id;

public class collection_handle_object_on_object extends script.base_script
{
    public collection_handle_object_on_object()
    {
    }
    public int incapaciteSelf(obj_id self, dictionary params) throws InterruptedException
    {
        setPosture(self, POSTURE_INCAPACITATED);
        return SCRIPT_CONTINUE;
    }
}
