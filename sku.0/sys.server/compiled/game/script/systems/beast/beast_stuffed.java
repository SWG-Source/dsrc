package script.systems.beast;

import script.dictionary;
import script.obj_id;

public class beast_stuffed extends script.base_script
{
    public beast_stuffed()
    {
    }
    public int OnPack(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isIdValid(self))
        {
            return SCRIPT_CONTINUE;
        }
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
