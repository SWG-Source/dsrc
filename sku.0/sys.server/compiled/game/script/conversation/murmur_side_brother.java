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
import script.library.groundquests;
import script.library.utils;

public class murmur_side_brother extends script.base_script
{
    public murmur_side_brother()
    {
    }
    public static String c_stringFile = "conversation/murmur_side_brother";
    public boolean murmur_side_brother_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean murmur_side_brother_condition_notFoundRemains(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "coronet_murmurs_side_4", "murmur_side_missing_brother");
    }
    public boolean murmur_side_brother_condition_avengedJenson(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "coronet_murmurs_side_4", "murmur_side_return_jeridel");
    }
    public boolean murmur_side_brother_condition_finishedQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "coronet_murmurs_side_4");
    }
    public boolean murmur_side_brother_condition_foundRemains(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedTask(player, "coronet_murmurs_side_4", "murmur_side_missing_brother");
    }
    public void murmur_side_brother_action_grantSideQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "coronet_murmurs_side_4");
    }
    public void murmur_side_brother_action_completeSideQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "murmur_side_return");
    }
    public void murmur_side_brother_action_facePlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
    }
    public int murmur_side_brother_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_14"))
        {
            if (murmur_side_brother_condition__defaultCondition(player, npc))
            {
                murmur_side_brother_action_completeSideQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_15");
                utils.removeScriptVar(player, "conversation.murmur_side_brother.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int murmur_side_brother_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_11"))
        {
            if (murmur_side_brother_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_12");
                utils.removeScriptVar(player, "conversation.murmur_side_brother.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int murmur_side_brother_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_20"))
        {
            if (murmur_side_brother_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_22");
                utils.removeScriptVar(player, "conversation.murmur_side_brother.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int murmur_side_brother_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_26"))
        {
            if (murmur_side_brother_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_28");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (murmur_side_brother_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_30");
                    }
                    utils.setScriptVar(player, "conversation.murmur_side_brother.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.murmur_side_brother.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int murmur_side_brother_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_30"))
        {
            if (murmur_side_brother_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_32");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (murmur_side_brother_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_34");
                    }
                    utils.setScriptVar(player, "conversation.murmur_side_brother.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.murmur_side_brother.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int murmur_side_brother_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_34"))
        {
            if (murmur_side_brother_condition__defaultCondition(player, npc))
            {
                murmur_side_brother_action_grantSideQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_36");
                utils.removeScriptVar(player, "conversation.murmur_side_brother.branchId");
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
            detachScript(self, "conversation.murmur_side_brother");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
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
        detachScript(self, "conversation.murmur_side_brother");
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
        if (murmur_side_brother_condition_finishedQuest(player, npc))
        {
            murmur_side_brother_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_16");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (murmur_side_brother_condition_avengedJenson(player, npc))
        {
            murmur_side_brother_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_13");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (murmur_side_brother_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_14");
                }
                utils.setScriptVar(player, "conversation.murmur_side_brother.branchId", 2);
                npcStartConversation(player, npc, "murmur_side_brother", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (murmur_side_brother_condition_foundRemains(player, npc))
        {
            murmur_side_brother_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_10");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (murmur_side_brother_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_11");
                }
                utils.setScriptVar(player, "conversation.murmur_side_brother.branchId", 4);
                npcStartConversation(player, npc, "murmur_side_brother", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (murmur_side_brother_condition_notFoundRemains(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_18");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (murmur_side_brother_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_20");
                }
                utils.setScriptVar(player, "conversation.murmur_side_brother.branchId", 6);
                npcStartConversation(player, npc, "murmur_side_brother", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (murmur_side_brother_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "implore");
            murmur_side_brother_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_24");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (murmur_side_brother_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_26");
                }
                utils.setScriptVar(player, "conversation.murmur_side_brother.branchId", 8);
                npcStartConversation(player, npc, "murmur_side_brother", message, responses);
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
        if (!conversationId.equals("murmur_side_brother"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.murmur_side_brother.branchId");
        if (branchId == 2 && murmur_side_brother_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && murmur_side_brother_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && murmur_side_brother_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && murmur_side_brother_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && murmur_side_brother_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && murmur_side_brother_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.murmur_side_brother.branchId");
        return SCRIPT_CONTINUE;
    }
}
