package script.space.characters;

import script.obj_id;

public class oberhaur extends script.base_script
{
    public oberhaur()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setAnimationMood(self, "npc_imperial");
        setInvulnerable(self, true);
        setName(self, "Cdr. Oberhaur");
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setAnimationMood(self, "npc_imperial");
        setInvulnerable(self, true);
        setName(self, "Cdr. Oberhaur");
        return SCRIPT_CONTINUE;
    }
}
