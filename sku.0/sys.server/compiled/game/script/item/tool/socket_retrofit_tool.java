package script.item.tool;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.static_item;

public class socket_retrofit_tool extends script.base_script
{
    public socket_retrofit_tool()
    {
    }
    public int OnAboutToReceiveItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (!utils.isNestedWithin(self, transferer))
        {
            sendSystemMessage(transferer, new string_id("spam", "trap_not_in_inventory"));
            return SCRIPT_OVERRIDE;
        }
        if (!canPutInTool(item, transferer, self))
        {
            sendSystemMessage(transferer, new string_id("spam", "cant_retrofit_that"));
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public boolean canPutInTool(obj_id item, obj_id transferer, obj_id self) throws InterruptedException
    {
        obj_id[] stuff = getContents(self);
        if (stuff.length != 0)
        {
            return false;
        }
        if (static_item.isStaticItem(item))
        {
            return false;
        }
        if (hasScript(item, "item.armor.dynamic_armor"))
        {
            return false;
        }
        int myGot = getGameObjectType(item);
        if (isGameObjectTypeOf(item, GOT_armor) || isGameObjectTypeOf(item, GOT_weapon) || isGameObjectTypeOf(item, GOT_clothing) || isGameObjectTypeOf(item, GOT_cybernetic))
        {
            if (hasObjVar(item, "skillmod.bonus"))
            {
                return true;
            }
        }
        return false;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!utils.isNestedWithin(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        string_id strSpam = new string_id("spam", "retrofit_tool_retrofit");
        mi.addRootMenu(menu_info_types.SERVER_MENU3, strSpam);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (!utils.isNestedWithin(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        if (self.isBeingDestroyed())
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, "retro_tool_processing"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] stuff = getContents(self);
        if (stuff.length == 0)
        {
            sendSystemMessage(player, new string_id("spam", "tool_is_empty"));
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.SERVER_MENU3)
        {
            utils.setScriptVar(self, "retro_tool_processing", 1);
            obj_id thisStuff = stuff[0];
            if (retrofitItUp(thisStuff))
            {
                obj_id inventory = utils.getInventoryContainer(player);
                if (putIn(thisStuff, inventory, player))
                {
                    destroyObject(self);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public boolean retrofitItUp(obj_id item) throws InterruptedException
    {
        boolean success = false;
        if (hasObjVar(item, "skillmod.bonus"))
        {
            removeObjVar(item, "skillmod.bonus");
            if (isGameObjectTypeOf(item, GOT_cybernetic_arm) || isGameObjectTypeOf(item, GOT_cybernetic_legs))
            {
                setSkillModSockets(item, 2);
            }
            else 
            {
                setSkillModSockets(item, 1);
            }
            setObjVar(item, "reverse_engineering.retrofitted", 1);
            setCondition(item, CONDITION_MAGIC_ITEM);
            success = true;
        }
        return success;
    }
}
