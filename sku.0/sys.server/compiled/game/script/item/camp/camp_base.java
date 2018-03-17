package script.item.camp;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.ai_lib;
import script.library.camping;

public class camp_base extends script.base_script
{
    public camp_base()
    {
    }
    public static final string_id SID_DEPLOY = new string_id("camp", "deploy");
    public static final string_id SID_SYS_ALREADY_CAMPING = new string_id("camp", "sys_already_camping");
    public static final string_id SID_SYS_CANT_CAMP = new string_id("camp", "sys_cant_camp");
    public static final string_id SID_SYS_NOT_IN_INV = new string_id("camp", "sys_not_in_inv");
    public static final string_id SID_SYS_DEPLOY = new string_id("camp", "sys_deploy");
    public static final string_id SID_SYS_NOT_IN_COMBAT = new string_id("camp", "sys_not_in_combat");
    public static final string_id SID_SYS_NSF_SKILL = new string_id("camp", "sys_nsf_skill");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        menu_info_data mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
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
            deployCamp(self, player);
        }
        return SCRIPT_CONTINUE;
    }
    public void deployCamp(obj_id self, obj_id player) throws InterruptedException
    {
        if (isSpaceScene())
        {
            return;
        }
        obj_id inventory = getObjectInSlot(player, "inventory");
        if (!utils.isNestedWithin(self, player))
        {
            sendSystemMessage(player, SID_SYS_NOT_IN_INV);
            return;
        }
        if (ai_lib.isInCombat(player))
        {
            sendSystemMessage(player, SID_SYS_NOT_IN_COMBAT);
            return;
        }
        if (1 == getState(player, STATE_SWIMMING))
        {
            sendSystemMessage(player, SID_SYS_CANT_CAMP);
            return;
        }
        if (hasObjVar(player, camping.VAR_CAMP_BASE))
        {
            obj_id myCamp = getObjIdObjVar(player, camping.VAR_PLAYER_CAMP);
            if ((myCamp == null) || (myCamp == obj_id.NULL_ID))
            {
            }
            else 
            {
                if (exists(myCamp) && myCamp.isLoaded())
                {
                    sendSystemMessage(player, SID_SYS_ALREADY_CAMPING);
                    return;
                }
                else 
                {
                    removeObjVar(player, camping.VAR_CAMP_BASE);
                }
            }
        }
        int campMod = getSkillStatMod(player, camping.MOD_CAMP);
        int modReq = getIntObjVar(self, "skillReq");
        int campPower = getIntObjVar(self, "campPower");
        if (campMod < modReq)
        {
            sendSystemMessage(player, SID_SYS_NSF_SKILL);
            return;
        }
        sendSystemMessage(player, SID_SYS_DEPLOY);
        obj_id master = camping.createCamp(player, campPower);
        if (master == null)
        {
        }
        else 
        {
            setObjVar(player, camping.VAR_PLAYER_CAMP, master);
            if (hasScript(player, "theme_park.new_player.new_player"))
            {
                dictionary webster = new dictionary();
                webster.put("deployedCamp", 1);
                messageTo(player, "handleNewPlayerScoutAction", webster, 1, false);
            }
            destroyObject(self);
        }
    }
}
