package script.theme_park.dungeon.geonosian_madbio_bunker;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class rubble_effect extends script.base_script
{
    public rubble_effect()
    {
    }
    public int OnAboutToReceiveItem(obj_id self, obj_id destinationCell, obj_id transferrer, obj_id item) throws InterruptedException
    {
        if (!isPlayer(item))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id boulderRoom = getCellId(self, "hall6");
        if (!hasObjVar(getSelf(), "played"))
        {
            playClientEffectObj(item, "clienteffect/combat_explosion_lair_large.cef", item, "");
            messageTo(getSelf(), "playedAnim", null, 1, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int playedAnim(obj_id self, dictionary params) throws InterruptedException
    {
        setObjVar(getSelf(), "played", 1);
        messageTo(getSelf(), "resetAnim", null, 120, false);
        return SCRIPT_CONTINUE;
    }
    public int resetAnim(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(getSelf(), "played"))
        {
            removeObjVar(getSelf(), "played");
        }
        return SCRIPT_CONTINUE;
    }
}
