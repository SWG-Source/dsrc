package script.theme_park.dungeon.borvos_vault;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.utils;
import script.library.permissions;
import script.library.static_item;

public class loot_chest extends script.base_script
{
    public loot_chest()
    {
    }
    public static final String DATATABLE_LOOT = "datatables/loot/dungeon/borvo_vault.iff";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        detachScript(self, "item.container.loot_crate");
        attachScript(self, "item.container.base.base_container");
        if (!hasObjVar(self, "spawnedLoot"))
        {
            spawnLoot(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
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
        removeObjVar(self, "spawnedLoot");
        messageTo(self, "makeMoreLoot", null, 1200, true);
        return SCRIPT_CONTINUE;
    }
    public int makeMoreLoot(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, "spawnedLoot"))
        {
            spawnLoot(self);
        }
        return SCRIPT_CONTINUE;
    }
    public obj_id spawnLoot(obj_id self) throws InterruptedException
    {
        String[] lootArray = dataTableGetStringColumn(DATATABLE_LOOT, "chest");
        int arrayLength = lootArray.length;
        int index = rand(1, arrayLength - 1);
        String newItem = lootArray[index];
        obj_id cargo = static_item.createNewItemFunction(newItem, self);
        setObjVar(self, "spawnedLoot", cargo);
        return cargo;
    }
}
