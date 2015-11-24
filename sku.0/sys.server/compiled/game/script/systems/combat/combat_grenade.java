package script.systems.combat;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.sui;
import script.library.combat;
import script.library.static_item;

public class combat_grenade extends script.systems.combat.combat_base
{
    public combat_grenade()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (static_item.isStaticItem(self))
        {
            return SCRIPT_CONTINUE;
        }
        else 
        {
            setCount(self, 10);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (getMinRange(self) != 0)
        {
            range_info rangeData = getWeaponRangeInfo(self);
            if (rangeData != null)
            {
                rangeData.minRange = 0;
                rangeData.maxRange = getMaxRange(self);
                setWeaponRangeInfo(self, rangeData);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (hasObjVar(self, "intUsed"))
        {
            return SCRIPT_CONTINUE;
        }
        menu_info_data mid = mi.getMenuItemByType(menu_info_types.EXAMINE);
        if (mid != null)
        {
            mid.setServerNotify(true);
        }
        mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
        if (mid != null)
        {
            mid.setServerNotify(true);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            String myTemplate = getSharedObjectTemplateName(self);
            String strParams = self.toString();
            obj_id target = getIntendedTarget(player);
            if (isIdValid(target) && pvpCanAttack(player, target))
            {
                if (myTemplate.equals("object/weapon/ranged/grenade/shared_grenade_cryoban.iff"))
                {
                    queueCommand(player, (-1234661205), target, strParams, COMMAND_PRIORITY_FRONT);
                    return SCRIPT_CONTINUE;
                }
                if (myTemplate.equals("object/weapon/ranged/grenade/shared_grenade_fragmentation.iff"))
                {
                    queueCommand(player, (-1463452344), target, strParams, COMMAND_PRIORITY_FRONT);
                    return SCRIPT_CONTINUE;
                }
                if (myTemplate.equals("object/weapon/ranged/grenade/shared_grenade_bug_bomb.iff"))
                {
                    queueCommand(player, (-2089315175), target, strParams, COMMAND_PRIORITY_FRONT);
                    return SCRIPT_CONTINUE;
                }
                if (myTemplate.equals("object/weapon/ranged/grenade/shared_grenade_fragmentation_light.iff"))
                {
                    queueCommand(player, (-1463452344), target, strParams, COMMAND_PRIORITY_FRONT);
                    return SCRIPT_CONTINUE;
                }
                if (myTemplate.equals("object/weapon/ranged/grenade/shared_grenade_glop.iff"))
                {
                    queueCommand(player, (2061473716), target, strParams, COMMAND_PRIORITY_FRONT);
                    return SCRIPT_CONTINUE;
                }
                if (myTemplate.equals("object/weapon/ranged/grenade/shared_grenade_imperial_detonator.iff"))
                {
                    queueCommand(player, (-1231875150), target, strParams, COMMAND_PRIORITY_FRONT);
                    return SCRIPT_CONTINUE;
                }
                if (myTemplate.equals("object/weapon/ranged/grenade/shared_grenade_proton.iff"))
                {
                    queueCommand(player, (-700354563), target, strParams, COMMAND_PRIORITY_FRONT);
                    return SCRIPT_CONTINUE;
                }
                if (myTemplate.equals("object/weapon/ranged/grenade/shared_grenade_thermal_detonator.iff"))
                {
                    queueCommand(player, (-1095287030), target, strParams, COMMAND_PRIORITY_FRONT);
                    return SCRIPT_CONTINUE;
                }
                if (myTemplate.equals("object/weapon/ranged/grenade/shared_grenade_fragmentation_generic.iff"))
                {
                    queueCommand(player, (-1463452344), target, strParams, COMMAND_PRIORITY_FRONT);
                    return SCRIPT_CONTINUE;
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAddedToWorld(obj_id self) throws InterruptedException
    {
        removeObjVar(self, "intUsed");
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
}
