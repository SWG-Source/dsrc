package script.quest.ep3;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.permissions;
import script.library.features;

public class loot_ep3_clone_relics_on_destroy extends script.base_script
{
    public loot_ep3_clone_relics_on_destroy()
    {
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id container = getContainedBy(self);
        if (hasScript(container, "quest.ep3.loot_ep3_clone_relics_nym_starmap") || hasScript(container, "quest.ep3.loot_ep3_clone_relics_jedi_starfighter"))
        {
            messageTo(container, "makeMoreLoot", null, 30, false);
        }
        return SCRIPT_CONTINUE;
    }
}
