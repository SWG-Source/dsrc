package script.holocron;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class datapad_entry extends script.base_script
{
    public datapad_entry()
    {
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id player = getObjIdObjVar(self, "newbie_handoff.player");
        if (hasObjVar(self, "hasClientPath"))
        {
            destroyClientPath(getObjIdObjVar(self, "hasClientPath"));
            removeObjVar(self, "hasClientPath");
        }
        dictionary params = new dictionary();
        params.put("name", getStringObjVar(self, "newbie_handoff.name"));
        messageTo(player, "removeDatapadEntryData", params, 0, true);
        return SCRIPT_CONTINUE;
    }
}
