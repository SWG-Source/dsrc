package script.theme_park.dungeon.geonosian_madbio_bunker;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class assistant_armory_lock extends script.base_script
{
    public assistant_armory_lock()
    {
    }
    public static final String MSGS = "dungeon/geonosian_madbio";
    public int OnAboutToReceiveItem(obj_id self, obj_id destinationCell, obj_id transferrer, obj_id item) throws InterruptedException
    {
        if (!isPlayer(item))
        {
            return SCRIPT_CONTINUE;
        }
        String objvarKey = getStringObjVar(self, "objvar_key");
        if (objvarKey == null || objvarKey.equals(""))
        {
            objvarKey = "nil";
        }
        if (!hasObjVar(item, objvarKey))
        {
            string_id locked = new string_id(MSGS, "door_locked");
            sendSystemMessage(item, locked);
            return SCRIPT_OVERRIDE;
        }
        else 
        {
            return SCRIPT_CONTINUE;
        }
    }
}
