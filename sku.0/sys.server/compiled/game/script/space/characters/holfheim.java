package script.space.characters;

import script.obj_id;

public class holfheim extends script.base_script
{
    public holfheim()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setAnimationMood(self, "npc_imperial");
        setInvulnerable(self, true);
        setName(self, "Under Inquisitor Holfheim");
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setAnimationMood(self, "npc_imperial");
        setInvulnerable(self, true);
        setName(self, "Under Inquisitor Holfheim");
        return SCRIPT_CONTINUE;
    }
}
