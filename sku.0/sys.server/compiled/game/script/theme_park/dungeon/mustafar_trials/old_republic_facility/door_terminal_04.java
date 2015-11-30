package script.theme_park.dungeon.mustafar_trials.old_republic_facility;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.prose;
import script.library.sui;

public class door_terminal_04 extends script.base_script
{
    public door_terminal_04()
    {
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        obj_id building = getTopMostContainer(self);
        int id = mi.addRootMenu(menu_info_types.SERVER_MENU1, new string_id("mustafar/old_republic_facility", "door_terminal_use"));
        menu_info_data mid = mi.getMenuItemById(id);
        mid.setServerNotify(true);
        int status = 0;
        if (hasObjVar(building, "status"))
        {
            status = getIntObjVar(building, "status");
        }
        if (status == 4)
        {
            mi.addRootMenu(menu_info_types.SERVER_MENU2, new string_id("mustafar/old_republic_facility", "door_terminal_use_codes"));
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.SERVER_MENU1)
        {
            readTerminal(self, player);
        }
        if (item == menu_info_types.SERVER_MENU2)
        {
            useCard(self, player);
        }
        sendDirtyObjectMenuNotification(self);
        return SCRIPT_CONTINUE;
    }
    public void readTerminal(obj_id self, obj_id player) throws InterruptedException
    {
        obj_id building = getTopMostContainer(self);
        String title = "@mustafar/old_republic_facility:door_terminal_sui_title";
        String prompt = "@mustafar/old_republic_facility:door_terminal_04_sui_prompt";
        int status = 0;
        if (hasObjVar(building, "status"))
        {
            status = getIntObjVar(building, "status");
        }
        if (status < 5)
        {
            prompt += "_a";
        }
        int pid = sui.msgbox(player, player, prompt, sui.OK_ONLY, title, "noHandler");
        return;
    }
    public void useCard(obj_id self, obj_id player) throws InterruptedException
    {
        obj_id building = getTopMostContainer(self);
        int status = 0;
        if (hasObjVar(building, "status"))
        {
            status = getIntObjVar(building, "status");
        }
        if (status >= 5)
        {
            return;
        }
        obj_id smallroom20 = getCellId(building, "smallroom20");
        permissionsMakePublic(smallroom20);
        setObjVar(building, "status", 5);
        sendSystemMessage(player, new string_id("mustafar/old_republic_facility", "door_terminal_04_msg_self"));
        obj_id group = getGroupObject(player);
        if (isIdValid(group))
        {
            obj_id[] members = getGroupMemberIds(group);
            if (members == null || members.length == 0)
            {
                return;
            }
            for (int i = 0; i < members.length; i++)
            {
                if (members[i] != player)
                {
                    prose_package pp = new prose_package();
                    pp = prose.setStringId(pp, new string_id("mustafar/old_republic_facility", "door_terminal_04_msg_other"));
                    pp = prose.setTU(pp, player);
                    sendSystemMessageProse(members[i], pp);
                }
            }
        }
        return;
    }
}
