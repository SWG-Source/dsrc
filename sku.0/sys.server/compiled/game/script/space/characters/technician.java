package script.space.characters;

import script.obj_id;

public class technician extends script.base_script
{
    public technician()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setInvulnerable(self, true);
        setName(self, "A Starship Technician");
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setInvulnerable(self, true);
        setName(self, "A Starship Technician");
        return SCRIPT_CONTINUE;
    }
}
