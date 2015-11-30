package script.theme_park.outbreak_prolog;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.factions;
import script.library.groundquests;
import script.library.holiday;

public class fix_dungeon_comp_systems extends script.base_script
{
    public fix_dungeon_comp_systems()
    {
    }
    public static final String MENU_STRING_FILE = "theme_park/outbreak/outbreak";
    public static final String MENU_OBJ_VAR = "computer_system";
    public static final String NO_MENU_OBJ_VAR = "none";
    public static final String KEY_QUEST_INT_OBJVAR = "quest";
    public static final String IMPERIAL_KEY_QUEST = "outbreak_quest_administrative_building_imperial_0";
    public static final String REBEL_KEY_QUEST = "outbreak_quest_administrative_building_rebel_0";
    public static final String NEUTRAL_KEY_QUEST = "outbreak_quest_administrative_building_neutral_0";
    public static final String FOUND_KEY_SIGNAL = "hasFoundKey";
    public static final String OUTBREAK_KEY_SEARCH = "search_body_outbreak_key";
    public static final string_id SID_YOU_FIND_NOTHING = new string_id(MENU_STRING_FILE, "you_find_nothing");
    public static final string_id SID_THIS_NOT_FOR_YOU = new string_id(MENU_STRING_FILE, "you_need_parts");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (isDead(player) || isIncapacitated(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, MENU_OBJ_VAR))
        {
            return SCRIPT_CONTINUE;
        }
        String menuObjVar = getStringObjVar(self, MENU_OBJ_VAR);
        if (menuObjVar == null || menuObjVar.length() <= 0)
        {
            return SCRIPT_CONTINUE;
        }
        if (menuObjVar.equals(NO_MENU_OBJ_VAR))
        {
            return SCRIPT_CONTINUE;
        }
        menu_info_data data = mi.getMenuItemByType(menu_info_types.SERVER_MENU2);
        mi.addRootMenu(menu_info_types.ITEM_USE, new string_id(MENU_STRING_FILE, menuObjVar));
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (isDead(player) || isIncapacitated(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, MENU_OBJ_VAR))
        {
            return SCRIPT_CONTINUE;
        }
        if (item != menu_info_types.ITEM_USE)
        {
            return SCRIPT_CONTINUE;
        }
        String menuObjVar = getStringObjVar(self, MENU_OBJ_VAR);
        if (menuObjVar == null || menuObjVar.length() <= 0)
        {
            return SCRIPT_CONTINUE;
        }
        CustomerServiceLog("outbreak_themepark", "fix_dungeon_comp_systems.OnObjectMenuSelect() menuObjVar: " + menuObjVar);
        if (menuObjVar.equals("damage_control"))
        {
            if (!groundquests.isTaskActive(player, "outbreak_quest_facility_01", "repairDamageControlModule"))
            {
                sendSystemMessage(player, SID_THIS_NOT_FOR_YOU);
                return SCRIPT_CONTINUE;
            }
            groundquests.sendSignal(player, "damageControlModuleRepaired");
            return SCRIPT_CONTINUE;
        }
        else if (menuObjVar.equals("communications"))
        {
            CustomerServiceLog("outbreak_themepark", "fix_dungeon_comp_systems.OnObjectMenuSelect() Key menu selected by Player: " + player);
            if (!groundquests.isTaskActive(player, "outbreak_quest_facility_01", "repairCommunicationsModule"))
            {
                sendSystemMessage(player, SID_THIS_NOT_FOR_YOU);
                return SCRIPT_CONTINUE;
            }
            groundquests.sendSignal(player, "communicationsModuleRepaired");
            return SCRIPT_CONTINUE;
        }
        sendSystemMessage(player, SID_YOU_FIND_NOTHING);
        return SCRIPT_CONTINUE;
    }
}
