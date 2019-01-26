package script.theme_park.dungeon.avatar_platform;

import script.*;

public class terminal_cell_04 extends script.base_script
{
    public terminal_cell_04()
    {
    }
    public static final String STF = "dungeon/avatar_platform";
    public static final string_id UNLOCK_CELL = new string_id(STF, "unlock_cell");
    public static final string_id DESTROY_WKE = new string_id(STF, "destroy_wke");
    public static final string_id CELL_UNLOCKED = new string_id(STF, "cell_unlocked");
    public static final string_id EXECUTE = new string_id(STF, "execute");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int menu = mi.addRootMenu(menu_info_types.ITEM_USE, UNLOCK_CELL);
        mi.addRootMenu(menu_info_types.SERVER_MENU1, DESTROY_WKE);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            obj_id structure = getTopMostContainer(self);
            obj_id target = getObjIdObjVar(structure, "avatar_platform.wke_prisoner_04");
            if (!hasObjVar(structure, "avatar_platform.wke_completed_04"))
            {
                dictionary who = new dictionary();
                who.put("player", player);
                messageTo(target, "handleFreedom", who, 0.0f, false);
                sendSystemMessage(player, CELL_UNLOCKED);
                obj_id cellDoor = getObjIdObjVar(structure, "avatar_platform.cell_door_04");
                destroyObject(cellDoor);
            }
        }
        if (item == menu_info_types.SERVER_MENU1)
        {
            obj_id structure = getTopMostContainer(self);
            obj_id target = getObjIdObjVar(structure, "avatar_platform.wke_prisoner_04");
            if (!hasObjVar(structure, "avatar_platform.wke_completed_04"))
            {
                dictionary who = new dictionary();
                who.put("player", player);
                messageTo(target, "handleGunkDeath", who, 0.0f, false);
                sendSystemMessage(player, EXECUTE);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
