package script.theme_park.dungeon.mustafar_trials.old_republic_facility;

import script.library.prose;
import script.library.sui;
import script.*;

public class quest_object_05 extends script.base_script
{
    public quest_object_05()
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
        if (status < 7)
        {
            mi.addRootMenu(menu_info_types.SERVER_MENU1, new string_id("mustafar/old_republic_facility", "quest_object_05_use"));
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.SERVER_MENU1)
        {
            activateObject(self, player);
        }
        sendDirtyObjectMenuNotification(self);
        return SCRIPT_CONTINUE;
    }
    public void activateObject(obj_id self, obj_id player) throws InterruptedException
    {
        obj_id building = getTopMostContainer(self);
        int status = 0;
        if (hasObjVar(building, "status"))
        {
            status = getIntObjVar(building, "status");
        }
        if (status >= 7)
        {
            return;
        }
        setObjVar(building, "status", 7);
        obj_id core_tower8 = getCellId(building, "core_tower8");
        permissionsMakePublic(core_tower8);
        String title = "@mustafar/old_republic_facility:quest_object_05_sui_title";
        String prompt = "@mustafar/old_republic_facility:quest_object_05_sui_prompt";
        int pid = sui.msgbox(player, player, prompt, sui.OK_ONLY, title, "noHandler");
        sendSystemMessage(player, new string_id("mustafar/old_republic_facility", "quest_object_05_msg_self"));
        obj_id group = getGroupObject(player);
        if (isIdValid(group))
        {
            obj_id[] members = getGroupMemberIds(group);
            if (members == null || members.length == 0)
            {
                return;
            }
            for (obj_id member : members) {
                if (member != player) {
                    prose_package pp = new prose_package();
                    pp = prose.setStringId(pp, new string_id("mustafar/old_republic_facility", "quest_object_05_msg_other"));
                    pp = prose.setTU(pp, player);
                    sendSystemMessageProse(member, pp);
                }
            }
        }
        return;
    }
}
