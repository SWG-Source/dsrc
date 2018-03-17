package script.systems.jedi;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class jedi_player extends script.base_script
{
    public jedi_player()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        detachScript(self, "systems.jedi.jedi_player");
        return SCRIPT_CONTINUE;
    }
}
