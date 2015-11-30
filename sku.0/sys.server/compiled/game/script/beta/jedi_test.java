package script.beta;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class jedi_test extends script.base_script
{
    public jedi_test()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (getJediState(self) < JEDI_STATE_JEDI)
        {
            setJediState(self, JEDI_STATE_JEDI);
        }
        setMaxForcePower(self, 10000);
        setForcePowerRegenRate(self, 1000);
        detachScript(self, "beta.jedi_test");
        return SCRIPT_CONTINUE;
    }
}
