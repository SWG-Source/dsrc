package script.theme_park.dungeon.corvette;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.permissions;

public class r2_loot extends script.base_script
{
    public r2_loot()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        String newLoot = pickNewLoot();
        obj_id cargo = createObject(newLoot, self, "");
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
        String classTemplate = getSkillTemplate(player);
        if (classTemplate == null || classTemplate.startsWith("entertainer") || classTemplate.startsWith("trader"))
        {
            sendSystemMessage(player, new string_id("dungeon/corvette", "no_trader_farming_allowed"));
            return SCRIPT_CONTINUE;
        }
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
        String newLoot = "object/tangible/dungeon/droid_maint_module.iff";
        return newLoot;
    }
}
