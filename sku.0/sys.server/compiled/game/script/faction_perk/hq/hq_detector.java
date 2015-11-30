package script.faction_perk.hq;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.hq;
import script.library.utils;
import script.library.faction_perk;

public class hq_detector extends script.base_script
{
    public hq_detector()
    {
    }
    public int OnReceivedItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (utils.isNestedWithin(srcContainer, self))
        {
            return SCRIPT_CONTINUE;
        }
        if (isPlayer(item))
        {
            faction_perk.decloakCovertFactionMember(self, item);
        }
        return SCRIPT_CONTINUE;
    }
}
