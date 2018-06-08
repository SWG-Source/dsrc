package script.systems.jedi;

import script.obj_id;

public class jedi_weapon_noslice extends script.base_script
{
    public jedi_weapon_noslice()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        detachScript(self, "slicing.slicing_weapon");
        detachScript(self, "systems.jedi.jedi_weapon_noslice");
        return SCRIPT_CONTINUE;
    }
}
