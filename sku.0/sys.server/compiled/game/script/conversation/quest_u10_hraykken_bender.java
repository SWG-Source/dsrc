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
import script.library.utils;

public class quest_u10_hraykken_bender extends script.base_script
{
    public quest_u10_hraykken_bender()
    {
    }
    public static String c_stringFile = "conversation/quest_u10_hraykken_bender";
    public boolean quest_u10_hraykken_bender_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean quest_u10_hraykken_bender_condition_questDisabled(obj_id player, obj_id npc) throws InterruptedException
    {
        return !isGod(player) && !hasObjVar(player, "quest_u10_testing");
    }
    public boolean quest_u10_hraykken_bender_condition_quest_u10_01_active(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActiveOrComplete(player, "quest_u10_01");
    }
    public void quest_u10_hraykken_bender_action_quest_u10_01_grant(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.requestGrantQuest(player, "quest_u10_01");
    }
    public int quest_u10_hraykken_bender_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_29"))
        {
            if (quest_u10_hraykken_bender_condition__defaultCondition(player, npc))
            {
                quest_u10_hraykken_bender_action_quest_u10_01_grant(player, npc);
                string_id message = new string_id(c_stringFile, "s_31");
                utils.removeScriptVar(player, "conversation.quest_u10_hraykken_bender.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_30"))
        {
            if (quest_u10_hraykken_bender_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_32");
                utils.removeScriptVar(player, "conversation.quest_u10_hraykken_bender.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isTangible(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.quest_u10_hraykken_bender");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_SPACE_INTERESTING);
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
        setCondition(self, CONDITION_SPACE_INTERESTING);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        detachScript(self, "conversation.quest_u10_hraykken_bender");
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
        if (quest_u10_hraykken_bender_condition_quest_u10_01_active(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_5");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (quest_u10_hraykken_bender_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_28");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (quest_u10_hraykken_bender_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (quest_u10_hraykken_bender_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_29");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_30");
                }
                utils.setScriptVar(player, "conversation.quest_u10_hraykken_bender.branchId", 2);
                npcStartConversation(player, npc, "quest_u10_hraykken_bender", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("quest_u10_hraykken_bender"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.quest_u10_hraykken_bender.branchId");
        if (branchId == 2 && quest_u10_hraykken_bender_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.quest_u10_hraykken_bender.branchId");
        return SCRIPT_CONTINUE;
    }
}
