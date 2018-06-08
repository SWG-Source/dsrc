package script.npe;

import script.obj_id;

public class make_invulnerable extends script.base_script
{
    public make_invulnerable()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setInvulnerable(self, true);
        return SCRIPT_CONTINUE;
    }
}
