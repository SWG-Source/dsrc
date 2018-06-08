package script.theme_park.singing_mountain;

import script.obj_id;

public class pet_rancor extends script.base_script
{
    public pet_rancor()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setScale(self, 0.35f);
        return SCRIPT_CONTINUE;
    }
}
