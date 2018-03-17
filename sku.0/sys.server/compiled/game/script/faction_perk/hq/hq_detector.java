package script.faction_perk.hq;

import script.library.faction_perk;
import script.library.utils;
import script.obj_id;

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
