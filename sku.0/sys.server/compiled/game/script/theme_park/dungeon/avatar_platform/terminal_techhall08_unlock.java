package script.theme_park.dungeon.avatar_platform;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;

public class terminal_techhall08_unlock extends script.base_script
{
    public terminal_techhall08_unlock()
    {
    }
    public static final String STF = "terminal_ui";
    public static final string_id USE_PASSCARD = new string_id(STF, "swipe_passcard");
    public static final string_id DISABLED = new string_id(STF, "door_locks_disabled");
    public static final string_id NOCARD = new string_id(STF, "no_passcard");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int menu = mi.addRootMenu(menu_info_types.ITEM_USE, USE_PASSCARD);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            if (groundquests.isTaskActive(player, "ep3_avatar_techhall08", "keySecurity03") || groundquests.hasCompletedQuest(player, "ep3_avatar_techhall08"))
            {
                obj_id structure = getTopMostContainer(self);
                obj_id room = getCellId(structure, "room");
                permissionsMakePublic(room);
                groundquests.sendSignal(player, "techhall08_unlocked");
                sendSystemMessage(player, DISABLED);
            }
            else 
            {
                if (!groundquests.isQuestActive(player, "ep3_avatar_techhall08"))
                {
                    groundquests.grantQuest(player, "ep3_avatar_techhall08");
                }
                sendSystemMessage(player, NOCARD);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
