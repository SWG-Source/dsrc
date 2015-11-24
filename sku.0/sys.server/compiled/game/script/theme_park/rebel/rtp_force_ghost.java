package script.theme_park.rebel;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class rtp_force_ghost extends script.base_script
{
    public rtp_force_ghost()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setState(self, STATE_GLOWING_JEDI, true);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setState(self, STATE_GLOWING_JEDI, true);
        return SCRIPT_CONTINUE;
    }
}
