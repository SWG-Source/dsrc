package script.space.characters;

import script.obj_id;

public class alozen extends script.base_script
{
    public alozen()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setAnimationMood(self, "npc_imperial");
        setInvulnerable(self, true);
        setName(self, "Field Cdr. Alozen");
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setAnimationMood(self, "npc_imperial");
        setInvulnerable(self, true);
        setName(self, "Field Cdr. Alozen");
        return SCRIPT_CONTINUE;
    }
}
