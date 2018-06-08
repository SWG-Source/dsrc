package script.quest.ep3;

import script.obj_id;

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
