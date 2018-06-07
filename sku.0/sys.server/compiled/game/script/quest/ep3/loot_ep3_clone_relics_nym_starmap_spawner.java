package script.quest.ep3;

import script.library.utils;
import script.obj_id;

public class loot_ep3_clone_relics_nym_starmap_spawner extends script.base_script
{
    public loot_ep3_clone_relics_nym_starmap_spawner()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        obj_id oldBox = utils.getObjIdScriptVar(self, "newBox");
        if (isIdValid(oldBox))
        {
            if (exists(oldBox))
            {
                return SCRIPT_CONTINUE;
            }
        }
        obj_id newBox = createObject("object/tangible/quest/quest_start/ep3_clone_relics_nym_starmap_container.iff", getLocation(self));
        utils.setScriptVar(self, "newBox", newBox);
        return SCRIPT_CONTINUE;
    }
}
