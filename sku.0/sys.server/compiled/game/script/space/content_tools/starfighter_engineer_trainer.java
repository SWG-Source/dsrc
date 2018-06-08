package script.space.content_tools;

import script.obj_id;

public class starfighter_engineer_trainer extends script.base_script
{
    public starfighter_engineer_trainer()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setObjVar(self, "trainer", "trainer_starfighter_engineer");
        return SCRIPT_CONTINUE;
    }
}
