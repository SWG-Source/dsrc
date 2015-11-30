package script.quest.ep3;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class loot_ep3_clone_relics_jedi_starfighter_spawner extends script.base_script
{
    public loot_ep3_clone_relics_jedi_starfighter_spawner()
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
        obj_id newBox = createObject("object/tangible/quest/quest_start/ep3_clone_relics_jedi_starfighter_container.iff", getLocation(self));
        utils.setScriptVar(self, "newBox", newBox);
        return SCRIPT_CONTINUE;
    }
}
