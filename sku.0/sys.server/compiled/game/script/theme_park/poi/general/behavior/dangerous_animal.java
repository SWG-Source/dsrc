package script.theme_park.poi.general.behavior;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class dangerous_animal extends script.base_script
{
    public dangerous_animal()
    {
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        obj_id player = getObjIdObjVar(self, "quester");
        messageTo(player, "IDied", null, 0, true);
        return SCRIPT_CONTINUE;
    }
}
