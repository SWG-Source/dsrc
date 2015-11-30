package script.theme_park.singing_mountain;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

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
