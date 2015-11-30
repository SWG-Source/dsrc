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

public class corellia_droid_factory_druno_kener extends script.base_script
{
    public corellia_droid_factory_druno_kener()
    {
    }
    public static String c_stringFile = "conversation/corellia_droid_factory_druno_kener";
    public boolean corellia_droid_factory_druno_kener_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean corellia_droid_factory_druno_kener_condition_finishedLastQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "coreallia_droid_factory_bunker_destroy_factory");
    }
    public boolean corellia_droid_factory_druno_kener_condition_finishedTerminalQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "corellia_droid_bunker_activate_terminals");
    }
    public boolean corellia_droid_factory_druno_kener_condition_readyToCompleteTerminalQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "corellia_droid_bunker_activate_terminals", "activateTerminalReturnToKener");
    }
    public boolean corellia_droid_factory_druno_kener_condition_terminalQuestActive(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "corellia_droid_bunker_activate_terminals");
    }
    public boolean corellia_droid_factory_druno_kener_condition_readyForTerminalQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "corellia_droid_bunker_activate_antenna") && !groundquests.isQuestActiveOrComplete(player, "corellia_droid_bunker_activate_terminals");
    }
    public boolean corellia_droid_factory_druno_kener_condition_readyForAntennaQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return !groundquests.isQuestActiveOrComplete(player, "corellia_droid_bunker_activate_antenna");
    }
    public boolean corellia_droid_factory_druno_kener_condition_readyToCompleteAntennaQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "corellia_droid_bunker_activate_antenna", "returnAntenna");
    }
    public boolean corellia_droid_factory_druno_kener_condition_isAntennaActive(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "corellia_droid_bunker_activate_antenna");
    }
    public void corellia_droid_factory_druno_kener_action_sendTerminalFinishedSignal(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "activateTerminalReturnToKener");
    }
    public void corellia_droid_factory_druno_kener_action_grantTerminalQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "corellia_droid_bunker_activate_terminals");
    }
    public void corellia_droid_factory_druno_kener_action_grantAntennaQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "corellia_droid_bunker_activate_antenna");
    }
    public void corellia_droid_factory_druno_kener_action_sendAntennaFinishedSignal(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "returnAntenna");
    }
    public int corellia_droid_factory_druno_kener_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_10"))
        {
            if (corellia_droid_factory_druno_kener_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_12");
                utils.removeScriptVar(player, "conversation.corellia_droid_factory_druno_kener.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_droid_factory_druno_kener_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_18"))
        {
            if (corellia_droid_factory_druno_kener_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_20");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_droid_factory_druno_kener_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_droid_factory_druno_kener_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_22");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_26");
                    }
                    utils.setScriptVar(player, "conversation.corellia_droid_factory_druno_kener.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_droid_factory_druno_kener.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_droid_factory_druno_kener_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_22"))
        {
            if (corellia_droid_factory_druno_kener_condition__defaultCondition(player, npc))
            {
                corellia_droid_factory_druno_kener_action_grantTerminalQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_24");
                utils.removeScriptVar(player, "conversation.corellia_droid_factory_druno_kener.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_26"))
        {
            if (corellia_droid_factory_druno_kener_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_28");
                utils.removeScriptVar(player, "conversation.corellia_droid_factory_druno_kener.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_droid_factory_druno_kener_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_47"))
        {
            if (corellia_droid_factory_druno_kener_condition_finishedLastQuest(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_4");
                utils.removeScriptVar(player, "conversation.corellia_droid_factory_druno_kener.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (corellia_droid_factory_druno_kener_condition_finishedTerminalQuest(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_6");
                utils.removeScriptVar(player, "conversation.corellia_droid_factory_druno_kener.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (corellia_droid_factory_druno_kener_condition_readyToCompleteTerminalQuest(player, npc))
            {
                corellia_droid_factory_druno_kener_action_sendTerminalFinishedSignal(player, npc);
                string_id message = new string_id(c_stringFile, "s_8");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_droid_factory_druno_kener_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_10");
                    }
                    utils.setScriptVar(player, "conversation.corellia_droid_factory_druno_kener.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_droid_factory_druno_kener.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (corellia_droid_factory_druno_kener_condition_terminalQuestActive(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_14");
                utils.removeScriptVar(player, "conversation.corellia_droid_factory_druno_kener.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (corellia_droid_factory_druno_kener_condition_readyForTerminalQuest(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_16");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_droid_factory_druno_kener_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_18");
                    }
                    utils.setScriptVar(player, "conversation.corellia_droid_factory_druno_kener.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_droid_factory_druno_kener.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (corellia_droid_factory_druno_kener_condition_readyToCompleteAntennaQuest(player, npc))
            {
                corellia_droid_factory_druno_kener_action_sendAntennaFinishedSignal(player, npc);
                string_id message = new string_id(c_stringFile, "s_30");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_droid_factory_druno_kener_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_47");
                    }
                    utils.setScriptVar(player, "conversation.corellia_droid_factory_druno_kener.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_droid_factory_druno_kener.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (corellia_droid_factory_druno_kener_condition_isAntennaActive(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_33");
                utils.removeScriptVar(player, "conversation.corellia_droid_factory_druno_kener.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (corellia_droid_factory_druno_kener_condition_readyForAntennaQuest(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_35");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_droid_factory_druno_kener_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_37");
                    }
                    utils.setScriptVar(player, "conversation.corellia_droid_factory_druno_kener.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_droid_factory_druno_kener.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_droid_factory_druno_kener_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_37"))
        {
            if (corellia_droid_factory_druno_kener_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_39");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_droid_factory_druno_kener_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_droid_factory_druno_kener_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_41");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_45");
                    }
                    utils.setScriptVar(player, "conversation.corellia_droid_factory_druno_kener.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_droid_factory_druno_kener.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_droid_factory_druno_kener_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_41"))
        {
            if (corellia_droid_factory_druno_kener_condition__defaultCondition(player, npc))
            {
                corellia_droid_factory_druno_kener_action_grantAntennaQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_43");
                utils.removeScriptVar(player, "conversation.corellia_droid_factory_druno_kener.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_45"))
        {
            if (corellia_droid_factory_druno_kener_condition__defaultCondition(player, npc))
            {
                corellia_droid_factory_druno_kener_action_sendTerminalFinishedSignal(player, npc);
                string_id message = new string_id(c_stringFile, "s_48");
                utils.removeScriptVar(player, "conversation.corellia_droid_factory_druno_kener.branchId");
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
            detachScript(self, "conversation.corellia_droid_factory_druno_kener");
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
        detachScript(self, "conversation.corellia_droid_factory_druno_kener");
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
        if (corellia_droid_factory_druno_kener_condition_finishedLastQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_4");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corellia_droid_factory_druno_kener_condition_finishedTerminalQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_6");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corellia_droid_factory_druno_kener_condition_readyToCompleteTerminalQuest(player, npc))
        {
            corellia_droid_factory_druno_kener_action_sendTerminalFinishedSignal(player, npc);
            string_id message = new string_id(c_stringFile, "s_8");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_droid_factory_druno_kener_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_10");
                }
                utils.setScriptVar(player, "conversation.corellia_droid_factory_druno_kener.branchId", 3);
                npcStartConversation(player, npc, "corellia_droid_factory_druno_kener", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_droid_factory_druno_kener_condition_terminalQuestActive(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_14");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corellia_droid_factory_druno_kener_condition_readyForTerminalQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_16");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_droid_factory_druno_kener_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_18");
                }
                utils.setScriptVar(player, "conversation.corellia_droid_factory_druno_kener.branchId", 6);
                npcStartConversation(player, npc, "corellia_droid_factory_druno_kener", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_droid_factory_druno_kener_condition_readyToCompleteAntennaQuest(player, npc))
        {
            corellia_droid_factory_druno_kener_action_sendAntennaFinishedSignal(player, npc);
            string_id message = new string_id(c_stringFile, "s_30");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_droid_factory_druno_kener_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_47");
                }
                utils.setScriptVar(player, "conversation.corellia_droid_factory_druno_kener.branchId", 10);
                npcStartConversation(player, npc, "corellia_droid_factory_druno_kener", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_droid_factory_druno_kener_condition_isAntennaActive(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_33");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corellia_droid_factory_druno_kener_condition_readyForAntennaQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_35");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_droid_factory_druno_kener_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_37");
                }
                utils.setScriptVar(player, "conversation.corellia_droid_factory_druno_kener.branchId", 12);
                npcStartConversation(player, npc, "corellia_droid_factory_druno_kener", message, responses);
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
        if (!conversationId.equals("corellia_droid_factory_druno_kener"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.corellia_droid_factory_druno_kener.branchId");
        if (branchId == 3 && corellia_droid_factory_druno_kener_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && corellia_droid_factory_druno_kener_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && corellia_droid_factory_druno_kener_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && corellia_droid_factory_druno_kener_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && corellia_droid_factory_druno_kener_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && corellia_droid_factory_druno_kener_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.corellia_droid_factory_druno_kener.branchId");
        return SCRIPT_CONTINUE;
    }
}
