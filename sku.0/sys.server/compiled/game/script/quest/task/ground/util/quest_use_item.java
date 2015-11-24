package script.quest.task.ground.util;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;
import script.library.utils;

public class quest_use_item extends script.base_script
{
    public quest_use_item()
    {
    }
    public static final String QUI = "QuestUseItem";
    public static final String QUEST_NAME = "questName";
    public static final String QUEST_CRC = "questCrc";
    public static final String TASK_NAME = "taskName";
    public static final String TASK_ID = "taskId";
    public static final String dataTableColumnUseItemMenuString = "USE_ITEM_MENU_STRING";
    public static final String QUEST_ITEM_USED = "questItemUsed";
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info menuInfo) throws InterruptedException
    {
        if (checkRequiredObjVarsAndActive(self, player))
        {
            string_id menuStringId = getQuestStringId(self, dataTableColumnUseItemMenuString);
            int menu = menuInfo.addRootMenu(menu_info_types.ITEM_USE, menuStringId);
            menu_info_data menuInfoData = menuInfo.getMenuItemById(menu);
            menuInfoData.setServerNotify(true);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE && checkRequiredObjVarsAndActive(self, player))
        {
            int questCrc = getQuestCrc(self);
            int taskId = getTaskId(self, questCrc);
            dictionary params = new dictionary();
            params.put(QUEST_CRC, questCrc);
            params.put(TASK_ID, taskId);
            messageTo(player, QUEST_ITEM_USED, params, 0, false);
        }
        return SCRIPT_CONTINUE;
    }
    public boolean checkRequiredObjVarsAndActive(obj_id self, obj_id player) throws InterruptedException
    {
        boolean check = hasObjVar(self, QUEST_NAME) && hasObjVar(self, TASK_NAME);
        if (!check)
        {
            LOG(QUI, "Missing required objvars for script! [" + QUEST_NAME + "][" + TASK_NAME + "]");
            return false;
        }
        int questCrc = getQuestCrc(self);
        int taskId = getTaskId(self, questCrc);
        check = questIsTaskActive(questCrc, taskId, player);
        return check;
    }
    public int getQuestCrc(obj_id self) throws InterruptedException
    {
        return groundquests.getQuestIdFromString(getStringObjVar(self, QUEST_NAME));
    }
    public int getTaskId(obj_id self, int questCrc) throws InterruptedException
    {
        return groundquests.getTaskId(questCrc, getStringObjVar(self, TASK_NAME));
    }
    public String getQuestString(obj_id self, String dataTableColumn) throws InterruptedException
    {
        int questCrc = getQuestCrc(self);
        int taskId = getTaskId(self, questCrc);
        return groundquests.getTaskStringDataEntry(questCrc, taskId, dataTableColumn);
    }
    public string_id getQuestStringId(obj_id self, String dataTableColumn) throws InterruptedException
    {
        String questString = getQuestString(self, dataTableColumn);
        return utils.unpackString(questString);
    }
}
