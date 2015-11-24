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

public class quest_object_01 extends script.base_script
{
    public quest_object_01()
    {
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int menu = mi.addRootMenu(menu_info_types.ITEM_USE, new string_id("mustafar/old_republic_facility", "quest_object_01_use"));
        menu_info_data menuInfoData = mi.getMenuItemById(menu);
        if (menuInfoData != null)
        {
            menuInfoData.setServerNotify(true);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            obj_id building = getTopMostContainer(self);
            if (!hasObjVar(building, "status"))
            {
                activateObject(self, player);
            }
            else 
            {
                sendSystemMessage(player, new string_id("mustafar/old_republic_facility", "quest_object_01_msg_already_active"));
            }
        }
        sendDirtyObjectMenuNotification(self);
        return SCRIPT_CONTINUE;
    }
    public void activateObject(obj_id self, obj_id player) throws InterruptedException
    {
        obj_id building = getTopMostContainer(self);
        if (hasObjVar(building, "status"))
        {
            return;
        }
        setObjVar(building, "status", 1);
        obj_id hall7 = getCellId(building, "hall7");
        permissionsMakePublic(hall7);
        String title = "@mustafar/old_republic_facility:quest_object_01_sui_title";
        String prompt = "@mustafar/old_republic_facility:quest_object_01_sui_prompt";
        int pid = sui.msgbox(player, player, prompt, sui.OK_ONLY, title, "noHandler");
        if (!groundquests.isQuestActive(player, "som_story_arc_chapter_one_03"))
        {
            sendSystemMessage(player, new string_id("mustafar/old_republic_facility", "quest_object_01_msg_self"));
        }
        obj_id group = getGroupObject(player);
        obj_id[] members = new obj_id[1];
        members[0] = player;
        if (isIdValid(group))
        {
            members = getGroupMemberIds(group);
            if (members == null || members.length == 0)
            {
                return;
            }
            for (int i = 0; i < members.length; i++)
            {
                if (members[i] != player)
                {
                    prose_package pp = new prose_package();
                    pp = prose.setStringId(pp, new string_id("mustafar/old_republic_facility", "quest_object_01_msg_other"));
                    pp = prose.setTU(pp, player);
                    if (!groundquests.isQuestActive(members[i], "som_story_arc_chapter_one_03"))
                    {
                        sendSystemMessageProse(members[i], pp);
                    }
                }
            }
        }
        dictionary d = new dictionary();
        d.put("members", members);
        messageTo(self, "advanceQuest", d, 3.0f, false);
        return;
    }
    public int advanceQuest(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] members = params.getObjIdArray("members");
        if (members == null || members.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        for (int i = 0; i < members.length; i++)
        {
            if (isIdValid(members[i]))
            {
                groundquests.sendSignal(members[i], "mustafar_uplink_power");
            }
        }
        return SCRIPT_CONTINUE;
    }
}
