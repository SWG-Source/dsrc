package script.theme_park.dungeon.geonosian_madbio_bunker;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.permissions;

public class loot_locker extends script.base_script
{
    public loot_locker()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        detachScript(self, "item.container.loot_crate");
        attachScript(self, "item.container.base.base_container");
        String newLoot = pickNewLoot();
        obj_id cargo = createObject(newLoot, self, "");
        if (hasObjVar(self, "room"))
        {
            createObject("object/tangible/dungeon/poison_stabilizer.iff", self, "");
        }
        return SCRIPT_CONTINUE;
    }
    public int makeMoreLoot(obj_id self, dictionary params) throws InterruptedException
    {
        removeObjVar(self, "gaveLoot");
        String newLoot = pickNewLoot();
        obj_id cargo = createObject(newLoot, self, "");
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_OPEN)
        {
            if (!hasObjVar(self, permissions.VAR_PERMISSION_BASE))
            {
                switch (getContainerType(self))
                {
                    case 0:
                    detachScript(self, "item.container.base.base_container");
                    break;
                    case 1:
                    utils.requestContainerOpen(player, self);
                    break;
                    default:
                    break;
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToLoseItem(obj_id self, obj_id destContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        setObjVar(self, "gaveLoot", 1);
        messageTo(self, "makeMoreLoot", null, 600, true);
        return SCRIPT_CONTINUE;
    }
    public String pickNewLoot() throws InterruptedException
    {
        String newLoot = "object/tangible/loot/dungeon/geonosian_mad_bunker/passkey.iff";
        if (hasObjVar(getSelf(), "room"))
        {
            newLoot = "object/tangible/dungeon/poison_stabilizer.iff";
            return newLoot;
        }
        int pickNewLoot = rand(1, 5);
        switch (pickNewLoot)
        {
            case 1:
            newLoot = "object/tangible/loot/dungeon/geonosian_mad_bunker/passkey.iff";
            break;
            case 2:
            newLoot = "object/tangible/wearables/goggles/rebreather.iff";
            break;
            case 3:
            newLoot = "object/tangible/loot/dungeon/geonosian_mad_bunker/relic_gbb_honey_carafe.iff";
            break;
            case 4:
            newLoot = "object/tangible/loot/dungeon/geonosian_mad_bunker/relic_gbb_honey_carafe.iff";
            break;
            case 5:
            newLoot = "object/tangible/loot/dungeon/geonosian_mad_bunker/relic_gbb_small_ball.iff";
            break;
            default:
            break;
        }
        return newLoot;
    }
}
