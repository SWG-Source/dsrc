package script.conversation;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.chat;
import script.library.conversation;
import script.library.groundquests;
import script.library.static_item;
import script.library.utils;

public class fence extends script.base_script
{
    public fence()
    {
    }
    public static String c_stringFile = "conversation/fence";
    public boolean fence_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean fence_condition_hasContrabandToGive(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        String questName = getStringObjVar(npc, "quest_name");
        String taskName = getStringObjVar(npc, "task_name");
        String dataTableItemToGive = "ITEM_TO_GIVE";
        int questCrc = groundquests.getQuestIdFromString(questName);
        int taskId = groundquests.getTaskId(questCrc, taskName);
        if (!questIsTaskActive(questCrc, taskId, player))
        {
            if (hasObjVar(npc, "alternate_quest_name"))
            {
                questName = getStringObjVar(npc, "alternate_quest_name");
            }
            else 
            {
                return false;
            }
            questCrc = groundquests.getQuestIdFromString(questName);
            if (!questIsTaskActive(questCrc, taskId, player))
            {
                return false;
            }
        }
        String itemToGive = groundquests.getTaskStringDataEntry(questCrc, taskId, dataTableItemToGive);
        obj_id[] contents = getInventoryAndEquipment(player);
        if (contents != null && contents.length > 0)
        {
            for (int i = 0; i < contents.length; i++)
            {
                if (static_item.isStaticItem(contents[i]))
                {
                    String itemName = getStaticItemName(contents[i]);
                    if (itemName.equals(itemToGive))
                    {
                        if (!ai_lib.isInCombat(player))
                        {
                            dictionary webster = new dictionary();
                            webster.put("questName", questName);
                            webster.put("taskName", taskName);
                            webster.put("itemName", itemName);
                            messageTo(player, "itemGivenToNpc", webster, 0, false);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    public boolean fence_condition_inCombat(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        if (ai_lib.isInCombat(player))
        {
            return true;
        }
        return false;
    }
    public boolean fence_condition_pvpTimer(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        String questName = getStringObjVar(npc, "quest_name");
        String taskName = getStringObjVar(npc, "task_name");
        String dataTableItemToGive = "ITEM_TO_GIVE";
        int questCrc = groundquests.getQuestIdFromString(questName);
        int taskId = groundquests.getTaskId(questCrc, taskName);
        if (!questIsTaskActive(questCrc, taskId, player))
        {
            if (hasObjVar(npc, "alternate_quest_name"))
            {
                questName = getStringObjVar(npc, "alternate_quest_name");
            }
            else 
            {
                return false;
            }
            questCrc = groundquests.getQuestIdFromString(questName);
            if (!questIsTaskActive(questCrc, taskId, player))
            {
                return false;
            }
        }
        String itemToGive = groundquests.getTaskStringDataEntry(questCrc, taskId, dataTableItemToGive);
        obj_id[] contents = getInventoryAndEquipment(player);
        if (contents != null && contents.length > 0)
        {
            for (int i = 0; i < contents.length; i++)
            {
                if (static_item.isStaticItem(contents[i]))
                {
                    String itemName = getStaticItemName(contents[i]);
                    if (itemName.equals(itemToGive))
                    {
                        if (groundquests.isTimeRemainingBeforeCompletion(player, questCrc, taskId))
                        {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    public boolean fence_condition_facePlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        return true;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isTangible(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.fence");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info menuInfo) throws InterruptedException
    {
        int menu = menuInfo.addRootMenu(menu_info_types.CONVERSE_START, null);
        menu_info_data menuInfoData = menuInfo.getMenuItemById(menu);
        menuInfoData.setServerNotify(false);
        setCondition(self, CONDITION_CONVERSABLE);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        detachScript(self, "conversation.fence");
        return SCRIPT_CONTINUE;
    }
    public boolean npcStartConversation(obj_id player, obj_id npc, String convoName, string_id greetingId, prose_package greetingProse, string_id[] responses) throws InterruptedException
    {
        Object[] objects = new Object[responses.length];
        System.arraycopy(responses, 0, objects, 0, responses.length);
        return npcStartConversation(player, npc, convoName, greetingId, greetingProse, objects);
    }
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        obj_id npc = self;
        if (ai_lib.isInCombat(npc) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (fence_condition_pvpTimer(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_9");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (fence_condition_hasContrabandToGive(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_5");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (fence_condition_inCombat(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_7");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (fence_condition_facePlayer(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_10");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("fence"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.fence.branchId");
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.fence.branchId");
        return SCRIPT_CONTINUE;
    }
}
