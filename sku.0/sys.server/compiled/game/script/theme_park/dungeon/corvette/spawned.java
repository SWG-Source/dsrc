package script.theme_park.dungeon.corvette;

import script.dictionary;
import script.obj_id;

public class spawned extends script.base_script
{
    public spawned()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "cleanUp", null, 300, false);
        return SCRIPT_CONTINUE;
    }
    public int cleanUp(obj_id self, dictionary params) throws InterruptedException
    {
        detachScript(self, "theme_park.dungeon.corvette.spawned");
        return SCRIPT_CONTINUE;
    }
}
