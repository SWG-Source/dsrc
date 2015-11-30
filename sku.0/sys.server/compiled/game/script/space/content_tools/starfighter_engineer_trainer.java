package script.space.content_tools;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

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
