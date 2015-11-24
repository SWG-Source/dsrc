package script.theme_park.dungeon.avatar_platform;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;

public class terminal_security01_unlock extends script.base_script
{
    public terminal_security01_unlock()
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
            if (groundquests.isTaskActive(player, "ep3_avatar_security_01", "keySecurity01") || groundquests.hasCompletedQuest(player, "ep3_avatar_security_01"))
            {
                obj_id structure = getTopMostContainer(self);
                obj_id techhall06 = getCellId(structure, "techhall06");
                obj_id techhall04 = getCellId(structure, "techhall04");
                obj_id techhall01 = getCellId(structure, "techhall01");
                permissionsMakePublic(techhall06);
                permissionsMakePublic(techhall04);
                permissionsMakePublic(techhall01);
                groundquests.sendSignal(player, "techhalls_unlocked");
                sendSystemMessage(player, DISABLED);
            }
            else 
            {
                if (!groundquests.isQuestActive(player, "ep3_avatar_security_01"))
                {
                    groundquests.grantQuest(player, "ep3_avatar_security_01");
                }
                sendSystemMessage(player, NOCARD);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
