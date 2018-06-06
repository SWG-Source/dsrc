package script.item.slicing;

import script.library.utils;
import script.obj_id;

public class slicing_laser_knife extends script.base_script
{
    public slicing_laser_knife()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCount(self, 10);
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        int count = getCount(self);
        names[idx] = "charges";
        attribs[idx] = Integer.toString(count);
        return SCRIPT_CONTINUE;
    }
}
