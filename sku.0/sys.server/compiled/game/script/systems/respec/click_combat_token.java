package script.systems.respec;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;
import script.library.respec;
import script.library.trace;
import script.library.utils;

public class click_combat_token extends script.base_script
{
    public click_combat_token()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "foundOwner"))
        {
            obj_id player = utils.getContainingPlayer(self);
            if (isIdValid(player) && isPlayer(player))
            {
                setObjVar(self, "foundOwner", player);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTransferred(obj_id self, obj_id sourceContainer, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        if (!hasObjVar(self, "foundOwner"))
        {
            obj_id player = utils.getContainingPlayer(self);
            if (isIdValid(player) && isPlayer(player))
            {
                setObjVar(self, "foundOwner", player);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (canManipulate(player, self, true, true, 15, true))
        {
            if (utils.isNestedWithinAPlayer(self))
            {
                menu_info_data mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
                if (mid != null)
                {
                    mid.setServerNotify(true);
                }
                else 
                {
                    mi.addRootMenu(menu_info_types.ITEM_USE, new string_id("ui_radial", "item_use"));
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (hasObjVar(player, "npcRespec.inProgress"))
        {
            sendSystemMessage(player, new string_id("spam", "cant_use_respec_device"));
            return SCRIPT_CONTINUE;
        }
        if (utils.getContainingPlayer(self) != player)
        {
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.ITEM_USE)
        {
            if (inRestrictedRegion(player))
            {
                sendSystemMessage(player, new string_id("spam", "cant_use_respec_device_here"));
                return SCRIPT_CONTINUE;
            }
            if (canManipulate(player, self, true, true, 15, true))
            {
                obj_id inv = getObjectInSlot(player, "inventory");
                int free = getVolumeFree(inv);
                if (free < 20)
                {
                    sendSystemMessage(player, new string_id("click_respec", "inventory_full"));
                    return SCRIPT_CONTINUE;
                }
                String template = getTemplateName(self);
                if (template.indexOf("ball_of_peace") > -1)
                {
                    obj_id[] me = new obj_id[1];
                    me[0] = player;
                    playClientEffectObj(me, "sound/ball_of_peace_01.snd", player, "root");
                }
                if (getCount(self) >= 0)
                {
                    respec.startNpcRespec(player, self, true);
                }
                else 
                {
                    destroyObject(self);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public boolean inRestrictedRegion(obj_id player) throws InterruptedException
    {
        String[] restrictedRegions = 
        {
            "dathomir_fs_village_unpassable"
        };
        for (int i = 0; i < restrictedRegions.length; i++)
        {
            if (groundquests.isInNamedRegion(player, restrictedRegions[i]))
            {
                return true;
            }
        }
        return false;
    }
}
