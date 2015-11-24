package script.theme_park.dathomir;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class set_level_80 extends script.base_script
{
    public set_level_80()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setLevel(self, 80);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setLevel(self, 80);
        return SCRIPT_CONTINUE;
    }
}
