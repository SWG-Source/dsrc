package script.theme_park.dungeon.mustafar_trials.old_republic_facility;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;
import script.library.prose;
import script.library.sui;

public class quest_object_08 extends script.base_script
{
    public quest_object_08()
    {
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        obj_id building = getTopMostContainer(self);
        int status = 0;
        if (hasObjVar(building, "status"))
        {
            status = getIntObjVar(building, "status");
        }
        if (status < 11)
        {
            mi.addRootMenu(menu_info_types.SERVER_MENU1, new string_id("mustafar/old_republic_facility", "quest_object_08_use"));
        }
        if (status == 9)
        {
            mi.addRootMenu(menu_info_types.SERVER_MENU2, new string_id("mustafar/old_republic_facility", "quest_object_08_install"));
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.SERVER_MENU1)
        {
            activateObject(self, player);
        }
        if (item == menu_info_types.SERVER_MENU2)
        {
            installParts(self, player);
        }
        sendDirtyObjectMenuNotification(self);
        return SCRIPT_CONTINUE;
    }
    public void installParts(obj_id self, obj_id player) throws InterruptedException
    {
        obj_id building = getTopMostContainer(self);
        int status = 0;
        if (hasObjVar(building, "status"))
        {
            status = getIntObjVar(building, "status");
        }
        if (status >= 10)
        {
            return;
        }
        setObjVar(building, "status", 10);
        sendSystemMessage(player, new string_id("mustafar/old_republic_facility", "quest_object_08_msg_self_a"));
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
                    pp = prose.setStringId(pp, new string_id("mustafar/old_republic_facility", "quest_object_08_msg_other_a"));
                    pp = prose.setTU(pp, player);
                    sendSystemMessageProse(members[i], pp);
                }
            }
        }
    }
    public void activateObject(obj_id self, obj_id player) throws InterruptedException
    {
        obj_id building = getTopMostContainer(self);
        int status = 0;
        if (hasObjVar(building, "status"))
        {
            status = getIntObjVar(building, "status");
        }
        if (status >= 11)
        {
            return;
        }
        else if (status <= 9)
        {
            String title = "@mustafar/old_republic_facility:quest_object_08_sui_title_a";
            String prompt = "@mustafar/old_republic_facility:quest_object_08_sui_prompt_a";
            int pid = sui.msgbox(player, player, prompt, sui.OK_ONLY, title, "noHandler");
        }
        else 
        {
            setObjVar(building, "status", 11);
            String title = "@mustafar/old_republic_facility:quest_object_08_sui_title_b";
            String prompt = "@mustafar/old_republic_facility:quest_object_08_sui_prompt_b";
            int pid = sui.msgbox(player, player, prompt, sui.OK_ONLY, title, "noHandler");
            sendSystemMessage(player, new string_id("mustafar/old_republic_facility", "quest_object_08_msg_self_b"));
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
                        pp = prose.setStringId(pp, new string_id("mustafar/old_republic_facility", "quest_object_08_msg_other_b"));
                        pp = prose.setTU(pp, player);
                        sendSystemMessageProse(members[i], pp);
                    }
                }
            }
        }
        return;
    }
}
