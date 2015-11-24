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
import script.library.create;
import script.library.battlefield;
import script.library.locations;
import script.library.craftinglib;
import script.library.space_dungeon;
import script.library.instance;

public class camp_advanced_deed extends script.base_script
{
    public camp_advanced_deed()
    {
    }
    public static final string_id SID_DEPLOY = new string_id("camp", "deploy");
    public static final string_id SID_SYS_ALREADY_CAMPING = new string_id("camp", "sys_already_camping");
    public static final string_id SID_SYS_CANT_CAMP = new string_id("camp", "sys_cant_camp");
    public static final string_id SID_SYS_NOT_IN_INV = new string_id("camp", "sys_not_in_inv");
    public static final string_id SID_SYS_DEPLOY = new string_id("camp", "sys_deploy");
    public static final string_id SID_SYS_NOT_IN_COMBAT = new string_id("camp", "sys_not_in_combat");
    public static final string_id SID_SYS_NSF_SKILL = new string_id("camp", "sys_nsf_skill");
    public static final string_id SID_ABANDONED_CAMP = new string_id("camp", "abandoned_camp");
    public static final string_id SID_STARTING_CAMP = new string_id("camp", "starting_camp");
    public static final string_id SID_CAMP_COMPLETE = new string_id("camp", "camp_complete");
    public static final string_id SID_ASSUMING_OWNERSHIP = new string_id("camp", "assuming_ownership");
    public static final string_id SID_ERROR_CAMP_DISBAND = new string_id("camp", "error_camp_disband");
    public static final string_id SID_ERROR_DEFAULT = new string_id("camp", "error_default");
    public static final string_id SID_ERROR_INSIDE = new string_id("camp", "error_inside");
    public static final string_id SID_ERROR_CAMP_EXISTS = new string_id("camp", "error_camp_exists");
    public static final string_id SID_ERROR_CAMP_TOO_CLOSE = new string_id("camp", "error_camp_too_close");
    public static final string_id SID_ERROR_BUILDING_TOO_CLOSE = new string_id("camp", "error_building_too_close");
    public static final string_id SID_ERROR_LAIR_TOO_CLOSE = new string_id("camp", "error_lair_too_close");
    public static final string_id SID_ERROR_MUNI_TRUE = new string_id("camp", "error_muni_true");
    public static final string_id SID_ERROR_NOBUILD = new string_id("camp", "error_nobuild");
    public static final string_id SID_ERROR_BATTLEFIELD = new string_id("camp", "error_battlefield");
    public static final string_id SID_ERROR_IN_WATER = new string_id("camp", "error_in_water");
    public static final string_id SID_ERROR_TOO_CLOSE_TO_WATER = new string_id("camp", "error_too_close_to_water");
    public static final string_id SID_ERROR_CAMP_TOO_BIG = new string_id("camp", "error_too_big");
    public static final string_id SID_CAMP_MASTER_NAME = new string_id("camp", "camp_master");
    public static final string_id SID_GENERAL_ITEM_ADDED = new string_id("camp", "general_item_added");
    public static final string_id SID_ADVANCED_ITEMS_ADDED = new string_id("camp", "advanced_items_added");
    public static final string_id SID_CAMP_ENTER = new string_id("camp", "camp_enter");
    public static final string_id SID_CAMP_EXIT = new string_id("camp", "camp_exit");
    public static final string_id PROSE_CAMP_ENTER = new string_id("camp", "prose_camp_enter");
    public static final string_id PROSE_CAMP_EXIT = new string_id("camp", "prose_camp_exit");
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
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "modules.shuttle_beacon"))
        {
            float attrib = getFloatObjVar(self, "modules.shuttle_beacon");
            if (attrib != 0.0f)
            {
                names[idx] = "shuttle_beacon";
                attribs[idx] = " " + (int)(attrib);
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
        }
        if (hasObjVar(self, "modules.cloning_tube"))
        {
            float attrib = getFloatObjVar(self, "modules.cloning_tube");
            if (attrib != 0.0f)
            {
                names[idx] = "cloning_tube";
                attribs[idx] = " " + (int)(attrib);
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
        }
        if (hasObjVar(self, "modules.entertainer"))
        {
            float attrib = getFloatObjVar(self, "modules.entertainer");
            if (attrib != 0.0f)
            {
                names[idx] = "entertainer";
                attribs[idx] = " " + (int)(attrib);
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
        }
        if (hasObjVar(self, "modules.junk_dealer"))
        {
            float attrib = getFloatObjVar(self, "modules.junk_dealer");
            if (attrib != 0.0f)
            {
                names[idx] = "junk_dealer";
                attribs[idx] = " " + (int)(attrib);
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
        }
        if (hasObjVar(self, "modules.clothing_station"))
        {
            float attrib = getFloatObjVar(self, "modules.clothing_station");
            if (attrib != 0.0f)
            {
                names[idx] = "clothing_station";
                attribs[idx] = " " + (int)(attrib);
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
        }
        if (hasObjVar(self, "modules.food_station"))
        {
            float attrib = getFloatObjVar(self, "modules.food_station");
            if (attrib != 0.0f)
            {
                names[idx] = "food_station";
                attribs[idx] = " " + (int)(attrib);
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
        }
        if (hasObjVar(self, "modules.ship_station"))
        {
            float attrib = getFloatObjVar(self, "modules.ship_station");
            if (attrib != 0.0f)
            {
                names[idx] = "ship_station";
                attribs[idx] = " " + (int)(attrib);
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
        }
        if (hasObjVar(self, "modules.structure_station"))
        {
            float attrib = getFloatObjVar(self, "modules.structure_station");
            if (attrib != 0.0f)
            {
                names[idx] = "structure_station";
                attribs[idx] = " " + (int)(attrib);
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
        }
        if (hasObjVar(self, "modules.weapon_station"))
        {
            float attrib = getFloatObjVar(self, "modules.weapon_station");
            if (attrib != 0.0f)
            {
                names[idx] = "weapon_station";
                attribs[idx] = " " + (int)(attrib);
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
        }
        if (hasObjVar(self, "modules.imperial"))
        {
            float attrib = getFloatObjVar(self, "modules.imperial");
            if (attrib != 0.0f)
            {
                names[idx] = "imperial";
                attribs[idx] = " " + (int)(attrib);
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
        }
        if (hasObjVar(self, "modules.rebel"))
        {
            float attrib = getFloatObjVar(self, "modules.rebel");
            if (attrib != 0.0f)
            {
                names[idx] = "rebel";
                attribs[idx] = " " + (int)(attrib);
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
        }
        if (hasObjVar(self, "modules.extra_life"))
        {
            float attrib = getFloatObjVar(self, "modules.extra_life");
            if (attrib != 0.0f)
            {
                names[idx] = "extra_life";
                attribs[idx] = " " + (int)(attrib);
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
        }
        if (hasObjVar(self, "modules.lifetime"))
        {
            float attrib = getFloatObjVar(self, "modules.lifetime");
            if (hasObjVar(self, "modules.extra_life"))
            {
                float extraLife = getFloatObjVar(self, "modules.extra_life");
                if (extraLife != 0.0f)
                {
                    attrib += extraLife;
                }
            }
            if (attrib != 0.0f)
            {
                names[idx] = "lifetime";
                attribs[idx] = " " + (int)(attrib);
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void deployCamp(obj_id self, obj_id player) throws InterruptedException
    {
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
        obj_id mount = getMountId(player);
        if (isIdValid(mount))
        {
            sendSystemMessage(player, new string_id("camp", "error_mounted"));
            return;
        }
        location here = getLocation(player);
        float height = getHeightAtLocation(here.x, here.z);
        if (here.y != height)
        {
            sendSystemMessage(player, new string_id("camp", "bad_location"));
            return;
        }
        if (isIdValid(here.cell) || here.area.startsWith("space_"))
        {
            sendSystemMessage(player, SID_ERROR_INSIDE);
            return;
        }
        if (space_dungeon.verifyPlayerSession(player))
        {
            sendSystemMessage(player, new string_id("camp", "camp_instance"));
            return;
        }
        if (here.area.equals("mustafar"))
        {
            if (here.x > 1120 || here.z < -1024)
            {
                return;
            }
        }
        if (instance.isInInstanceArea(player))
        {
            return;
        }
        if (camping.isTooCloseToAnotherCamp(here, player))
        {
            sendSystemMessage(player, new string_id("camp", "camp_too_close"));
            return;
        }
        region bf = battlefield.getBattlefield(here);
        if (bf != null)
        {
            sendSystemMessage(player, SID_ERROR_BATTLEFIELD);
            return;
        }
        if (locations.isInMissionCity(here))
        {
            sendSystemMessage(player, SID_ERROR_MUNI_TRUE);
            return;
        }
        if (isBelowWater(here))
        {
            sendSystemMessage(player, SID_ERROR_IN_WATER);
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
        if (!hasObjVar(self, "template"))
        {
            sendSystemMessage(player, SID_SYS_CANT_CAMP);
            return;
        }
        String template = getStringObjVar(self, "template");
        if (template == null || template.equals(""))
        {
            sendSystemMessage(player, SID_SYS_CANT_CAMP);
            return;
        }
        sendSystemMessage(player, SID_SYS_DEPLOY);
        location loc = getLocation(player);
        obj_id master = create.object(template, loc);
        if (isIdValid(master))
        {
            camping.initializeAdvancedCamp(self, master, player, loc);
            setObjVar(player, camping.VAR_PLAYER_CAMP, master);
            if (hasScript(player, "theme_park.new_player.new_player"))
            {
                dictionary webster = new dictionary();
                webster.put("deployedCamp", 1);
                messageTo(player, "handleNewPlayerScoutAction", webster, 1, false);
            }
            destroyObject(self);
        }
        return;
    }
}
