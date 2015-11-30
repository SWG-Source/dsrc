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

public class tatooine_24_azure_cabal_tweego_01 extends script.base_script
{
    public tatooine_24_azure_cabal_tweego_01()
    {
    }
    public static String c_stringFile = "conversation/tatooine_24_azure_cabal_tweego_01";
    public boolean tatooine_24_azure_cabal_tweego_01_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean tatooine_24_azure_cabal_tweego_01_condition_hasActiveTaskGetTweegosEndorsement(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "naboo_24_azure_cabal_01", "azure_cabal_loruna_endorsements_01_tweego");
    }
    public boolean tatooine_24_azure_cabal_tweego_01_condition_hasCompletedHuntersPath2(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "tatooine_24_azure_cabal_hunters_path_02");
    }
    public boolean tatooine_24_azure_cabal_tweego_01_condition_hasActiveTaskHuntersPath2Return(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "tatooine_24_azure_cabal_hunters_path_02", "azure_cabal_hunters_path_02_return");
    }
    public boolean tatooine_24_azure_cabal_tweego_01_condition_hasActiveTaskContactTweego(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "naboo_24_azure_cabal_01", "azure_cabal_loruna_endorsements_01_meet_tweego");
    }
    public boolean tatooine_24_azure_cabal_tweego_01_condition_hasReturnInDisgrace(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "tatooine_24_azure_cabal_hunters_path_01");
    }
    public boolean tatooine_24_azure_cabal_tweego_01_condition_needsHuntersPath(obj_id player, obj_id npc) throws InterruptedException
    {
        return !groundquests.isQuestActive(player, "tatooine_24_azure_cabal_hunters_path_02");
    }
    public boolean tatooine_24_azure_cabal_tweego_01_condition_hasHuntersPath(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "tatooine_24_azure_cabal_hunters_path_02");
    }
    public boolean tatooine_24_azure_cabal_tweego_01_condition_hasActiveQuestHuntersPathNotFinished(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "tatooine_24_azure_cabal_hunters_path_02");
    }
    public boolean tatooine_24_azure_cabal_tweego_01_condition_hasActiveTaskHuntersPath2Banthas(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "tatooine_24_azure_cabal_hunters_path_02", "azure_cabal_hunters_path_02_destroy_loot_1");
    }
    public boolean tatooine_24_azure_cabal_tweego_01_condition_hasActiveTaskHuntersPath2Brambles(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isQuestActive(player, "tatooine_24_azure_cabal_hunters_path_02") && !groundquests.isTaskActive(player, "tatooine_24_azure_cabal_hunters_path_02", "azure_cabal_hunters_path_02_destroy_loot_1"));
    }
    public void tatooine_24_azure_cabal_tweego_01_action_signalTweegoEndorsement(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "endorsedByTweego");
    }
    public void tatooine_24_azure_cabal_tweego_01_action_facePlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
    }
    public void tatooine_24_azure_cabal_tweego_01_action_grantHunterPath_2(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        groundquests.grantQuest(player, "quest/tatooine_24_azure_cabal_hunters_path_02");
        groundquests.sendSignal(player, "workingForTweego");
        groundquests.sendSignal(player, "azure_cabal_hunters_path_01_try_again");
    }
    public void tatooine_24_azure_cabal_tweego_01_action_signalHuntersPath2Complete(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "azure_cabal_hunters_path_02_returned_heart");
    }
    public int tatooine_24_azure_cabal_tweego_01_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_58"))
        {
            if (tatooine_24_azure_cabal_tweego_01_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_59");
                utils.removeScriptVar(player, "conversation.tatooine_24_azure_cabal_tweego_01.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_24_azure_cabal_tweego_01_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_45"))
        {
            if (tatooine_24_azure_cabal_tweego_01_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_47");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_24_azure_cabal_tweego_01_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_24_azure_cabal_tweego_01_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_48");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_49");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_24_azure_cabal_tweego_01.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_24_azure_cabal_tweego_01.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_43"))
        {
            if (tatooine_24_azure_cabal_tweego_01_condition__defaultCondition(player, npc))
            {
                tatooine_24_azure_cabal_tweego_01_action_grantHunterPath_2(player, npc);
                string_id message = new string_id(c_stringFile, "s_46");
                utils.removeScriptVar(player, "conversation.tatooine_24_azure_cabal_tweego_01.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_24_azure_cabal_tweego_01_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_48"))
        {
            if (tatooine_24_azure_cabal_tweego_01_condition__defaultCondition(player, npc))
            {
                tatooine_24_azure_cabal_tweego_01_action_signalHuntersPath2Complete(player, npc);
                string_id message = new string_id(c_stringFile, "s_50");
                utils.removeScriptVar(player, "conversation.tatooine_24_azure_cabal_tweego_01.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_49"))
        {
            if (tatooine_24_azure_cabal_tweego_01_condition__defaultCondition(player, npc))
            {
                tatooine_24_azure_cabal_tweego_01_action_grantHunterPath_2(player, npc);
                string_id message = new string_id(c_stringFile, "s_46");
                utils.removeScriptVar(player, "conversation.tatooine_24_azure_cabal_tweego_01.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_24_azure_cabal_tweego_01_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_61"))
        {
            if (tatooine_24_azure_cabal_tweego_01_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_64");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_24_azure_cabal_tweego_01_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_65");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_24_azure_cabal_tweego_01.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_24_azure_cabal_tweego_01.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_62"))
        {
            if (tatooine_24_azure_cabal_tweego_01_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_63");
                utils.removeScriptVar(player, "conversation.tatooine_24_azure_cabal_tweego_01.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_24_azure_cabal_tweego_01_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_65"))
        {
            if (tatooine_24_azure_cabal_tweego_01_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_63");
                utils.removeScriptVar(player, "conversation.tatooine_24_azure_cabal_tweego_01.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_24_azure_cabal_tweego_01_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_67"))
        {
            if (tatooine_24_azure_cabal_tweego_01_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_63");
                utils.removeScriptVar(player, "conversation.tatooine_24_azure_cabal_tweego_01.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_24_azure_cabal_tweego_01_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_18"))
        {
            if (tatooine_24_azure_cabal_tweego_01_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_20");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_24_azure_cabal_tweego_01_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_22");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_24_azure_cabal_tweego_01.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_24_azure_cabal_tweego_01.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_24_azure_cabal_tweego_01_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_22"))
        {
            if (tatooine_24_azure_cabal_tweego_01_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_24");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_24_azure_cabal_tweego_01_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_24_azure_cabal_tweego_01_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_26");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_28");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_24_azure_cabal_tweego_01.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_24_azure_cabal_tweego_01.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_24_azure_cabal_tweego_01_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_26"))
        {
            if (tatooine_24_azure_cabal_tweego_01_condition__defaultCondition(player, npc))
            {
                tatooine_24_azure_cabal_tweego_01_action_grantHunterPath_2(player, npc);
                string_id message = new string_id(c_stringFile, "s_44");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_24_azure_cabal_tweego_01_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_52");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_24_azure_cabal_tweego_01.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_24_azure_cabal_tweego_01.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_28"))
        {
            if (tatooine_24_azure_cabal_tweego_01_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_30");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_24_azure_cabal_tweego_01_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_24_azure_cabal_tweego_01_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_32");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_34");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_24_azure_cabal_tweego_01.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_24_azure_cabal_tweego_01.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_24_azure_cabal_tweego_01_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_32"))
        {
            if (tatooine_24_azure_cabal_tweego_01_condition__defaultCondition(player, npc))
            {
                tatooine_24_azure_cabal_tweego_01_action_grantHunterPath_2(player, npc);
                string_id message = new string_id(c_stringFile, "s_44");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_24_azure_cabal_tweego_01_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_52");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_24_azure_cabal_tweego_01.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_24_azure_cabal_tweego_01.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_34"))
        {
            if (tatooine_24_azure_cabal_tweego_01_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_36");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_24_azure_cabal_tweego_01_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_24_azure_cabal_tweego_01_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_38");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_40");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_24_azure_cabal_tweego_01.branchId", 16);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_24_azure_cabal_tweego_01.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_24_azure_cabal_tweego_01_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_38"))
        {
            if (tatooine_24_azure_cabal_tweego_01_condition__defaultCondition(player, npc))
            {
                tatooine_24_azure_cabal_tweego_01_action_grantHunterPath_2(player, npc);
                string_id message = new string_id(c_stringFile, "s_44");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_24_azure_cabal_tweego_01_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_52");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_24_azure_cabal_tweego_01.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_24_azure_cabal_tweego_01.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_40"))
        {
            if (tatooine_24_azure_cabal_tweego_01_condition__defaultCondition(player, npc))
            {
                tatooine_24_azure_cabal_tweego_01_action_grantHunterPath_2(player, npc);
                string_id message = new string_id(c_stringFile, "s_44");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_24_azure_cabal_tweego_01_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_52");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_24_azure_cabal_tweego_01.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_24_azure_cabal_tweego_01.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_24_azure_cabal_tweego_01_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_52"))
        {
            if (tatooine_24_azure_cabal_tweego_01_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_54");
                utils.removeScriptVar(player, "conversation.tatooine_24_azure_cabal_tweego_01.branchId");
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
            detachScript(self, "conversation.tatooine_24_azure_cabal_tweego_01");
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
        detachScript(self, "conversation.tatooine_24_azure_cabal_tweego_01");
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
        if (tatooine_24_azure_cabal_tweego_01_condition_hasCompletedHuntersPath2(player, npc))
        {
            tatooine_24_azure_cabal_tweego_01_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_9");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (tatooine_24_azure_cabal_tweego_01_condition_hasActiveTaskHuntersPath2Return(player, npc))
        {
            tatooine_24_azure_cabal_tweego_01_action_signalHuntersPath2Complete(player, npc);
            string_id message = new string_id(c_stringFile, "s_5");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_24_azure_cabal_tweego_01_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_58");
                }
                utils.setScriptVar(player, "conversation.tatooine_24_azure_cabal_tweego_01.branchId", 2);
                npcStartConversation(player, npc, "tatooine_24_azure_cabal_tweego_01", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_24_azure_cabal_tweego_01_condition_hasReturnInDisgrace(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_41");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_24_azure_cabal_tweego_01_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (tatooine_24_azure_cabal_tweego_01_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_45");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_43");
                }
                utils.setScriptVar(player, "conversation.tatooine_24_azure_cabal_tweego_01.branchId", 4);
                npcStartConversation(player, npc, "tatooine_24_azure_cabal_tweego_01", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_24_azure_cabal_tweego_01_condition_hasActiveTaskHuntersPath2Banthas(player, npc))
        {
            tatooine_24_azure_cabal_tweego_01_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_57");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_24_azure_cabal_tweego_01_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (tatooine_24_azure_cabal_tweego_01_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_61");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_62");
                }
                utils.setScriptVar(player, "conversation.tatooine_24_azure_cabal_tweego_01.branchId", 8);
                npcStartConversation(player, npc, "tatooine_24_azure_cabal_tweego_01", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_24_azure_cabal_tweego_01_condition_hasActiveQuestHuntersPathNotFinished(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_66");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_24_azure_cabal_tweego_01_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_67");
                }
                utils.setScriptVar(player, "conversation.tatooine_24_azure_cabal_tweego_01.branchId", 11);
                npcStartConversation(player, npc, "tatooine_24_azure_cabal_tweego_01", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_24_azure_cabal_tweego_01_condition_hasActiveTaskGetTweegosEndorsement(player, npc))
        {
            tatooine_24_azure_cabal_tweego_01_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_16");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_24_azure_cabal_tweego_01_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.tatooine_24_azure_cabal_tweego_01.branchId", 12);
                npcStartConversation(player, npc, "tatooine_24_azure_cabal_tweego_01", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_24_azure_cabal_tweego_01_condition__defaultCondition(player, npc))
        {
            tatooine_24_azure_cabal_tweego_01_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_56");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("tatooine_24_azure_cabal_tweego_01"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.tatooine_24_azure_cabal_tweego_01.branchId");
        if (branchId == 2 && tatooine_24_azure_cabal_tweego_01_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && tatooine_24_azure_cabal_tweego_01_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && tatooine_24_azure_cabal_tweego_01_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && tatooine_24_azure_cabal_tweego_01_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && tatooine_24_azure_cabal_tweego_01_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && tatooine_24_azure_cabal_tweego_01_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && tatooine_24_azure_cabal_tweego_01_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && tatooine_24_azure_cabal_tweego_01_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && tatooine_24_azure_cabal_tweego_01_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && tatooine_24_azure_cabal_tweego_01_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && tatooine_24_azure_cabal_tweego_01_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && tatooine_24_azure_cabal_tweego_01_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.tatooine_24_azure_cabal_tweego_01.branchId");
        return SCRIPT_CONTINUE;
    }
}
