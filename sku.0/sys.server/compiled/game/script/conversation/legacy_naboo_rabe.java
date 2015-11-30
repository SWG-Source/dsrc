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

public class legacy_naboo_rabe extends script.base_script
{
    public legacy_naboo_rabe()
    {
    }
    public static String c_stringFile = "conversation/legacy_naboo_rabe";
    public boolean legacy_naboo_rabe_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean legacy_naboo_rabe_condition_isOnRabeQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "legacy_naboo_helping_the_handmaidens", "onRabesQuest"))
        {
            return true;
        }
        return false;
    }
    public boolean legacy_naboo_rabe_condition_completedRabeQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.hasCompletedTask(player, "legacy_naboo_helping_the_handmaidens", "finishedRabesQuests"))
        {
            return true;
        }
        return false;
    }
    public void legacy_naboo_rabe_action_sendStartSignalForVineyard(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "rabeAskedToCleanVineyard");
    }
    public int legacy_naboo_rabe_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_8"))
        {
            if (legacy_naboo_rabe_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_10");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (legacy_naboo_rabe_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_12");
                    }
                    utils.setScriptVar(player, "conversation.legacy_naboo_rabe.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.legacy_naboo_rabe.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_32"))
        {
            if (legacy_naboo_rabe_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_34");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (legacy_naboo_rabe_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_36");
                    }
                    utils.setScriptVar(player, "conversation.legacy_naboo_rabe.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.legacy_naboo_rabe.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_naboo_rabe_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_12"))
        {
            if (legacy_naboo_rabe_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_14");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (legacy_naboo_rabe_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_16");
                    }
                    utils.setScriptVar(player, "conversation.legacy_naboo_rabe.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.legacy_naboo_rabe.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_naboo_rabe_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_16"))
        {
            if (legacy_naboo_rabe_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_18");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (legacy_naboo_rabe_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.legacy_naboo_rabe.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.legacy_naboo_rabe.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_naboo_rabe_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_20"))
        {
            if (legacy_naboo_rabe_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_22");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (legacy_naboo_rabe_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_24");
                    }
                    utils.setScriptVar(player, "conversation.legacy_naboo_rabe.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.legacy_naboo_rabe.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_naboo_rabe_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_24"))
        {
            if (legacy_naboo_rabe_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_26");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (legacy_naboo_rabe_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_28");
                    }
                    utils.setScriptVar(player, "conversation.legacy_naboo_rabe.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.legacy_naboo_rabe.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_naboo_rabe_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_28"))
        {
            if (legacy_naboo_rabe_condition__defaultCondition(player, npc))
            {
                legacy_naboo_rabe_action_sendStartSignalForVineyard(player, npc);
                string_id message = new string_id(c_stringFile, "s_30");
                utils.removeScriptVar(player, "conversation.legacy_naboo_rabe.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_naboo_rabe_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_36"))
        {
            if (legacy_naboo_rabe_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_10");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (legacy_naboo_rabe_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_12");
                    }
                    utils.setScriptVar(player, "conversation.legacy_naboo_rabe.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.legacy_naboo_rabe.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.legacy_naboo_rabe");
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
        detachScript(self, "conversation.legacy_naboo_rabe");
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
        if (legacy_naboo_rabe_condition_completedRabeQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_4");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (legacy_naboo_rabe_condition_isOnRabeQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_6");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (legacy_naboo_rabe_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (legacy_naboo_rabe_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_8");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_32");
                }
                utils.setScriptVar(player, "conversation.legacy_naboo_rabe.branchId", 2);
                npcStartConversation(player, npc, "legacy_naboo_rabe", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (legacy_naboo_rabe_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_38");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("legacy_naboo_rabe"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.legacy_naboo_rabe.branchId");
        if (branchId == 2 && legacy_naboo_rabe_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && legacy_naboo_rabe_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && legacy_naboo_rabe_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && legacy_naboo_rabe_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && legacy_naboo_rabe_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && legacy_naboo_rabe_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && legacy_naboo_rabe_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.legacy_naboo_rabe.branchId");
        return SCRIPT_CONTINUE;
    }
}
