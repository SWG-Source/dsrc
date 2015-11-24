package script.theme_park.dungeon.avatar_platform;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;

public class terminal_boss_fight extends script.base_script
{
    public terminal_boss_fight()
    {
    }
    public static final String STF = "dungeon/avatar_platform";
    public static final string_id UNLOCK_DOOR = new string_id(STF, "unlock_door");
    public static final string_id CANNOT_UNLOCK = new string_id(STF, "cannot_unlock");
    public static final string_id OPEN_DOOR = new string_id(STF, "open_door");
    public static final string_id DOOR_UNLOCKED = new string_id(STF, "door_unlocked");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int menu = mi.addRootMenu(menu_info_types.ITEM_USE, UNLOCK_DOOR);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            if (groundquests.isTaskActive(player, "ep3_trando_hssissk_zssik_10", "killHarwakokok"))
            {
                sendSystemMessage(player, CANNOT_UNLOCK);
                return SCRIPT_CONTINUE;
            }
            if (groundquests.hasCompletedTask(player, "ep3_trando_hssissk_zssik_10", "killHarwakokok"))
            {
                obj_id structure = getTopMostContainer(self);
                obj_id commandhall = getCellId(structure, "commandhall08");
                permissionsMakePublic(commandhall);
                sendSystemMessage(player, OPEN_DOOR);
                return SCRIPT_CONTINUE;
            }
            else 
            {
                sendSystemMessage(player, DOOR_UNLOCKED);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
