package script.space.combat;

import script.dictionary;
import script.library.utils;
import script.obj_id;

public class combat_space_base extends script.base_script
{
    public combat_space_base()
    {
    }
    public int finishDocking(obj_id self, dictionary params) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, "docking"))
        {
            return SCRIPT_CONTINUE;
        }
        utils.removeScriptVar(self, "docking");
        return SCRIPT_CONTINUE;
    }
}
