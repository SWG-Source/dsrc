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
import script.library.npe;
import script.library.utils;

public class npe_station_inaldra2 extends script.base_script
{
    public npe_station_inaldra2()
    {
    }
    public static String c_stringFile = "conversation/npe_station_inaldra2";
    public boolean npe_station_inaldra2_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean npe_station_inaldra2_condition_finishedInaldraQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "npe_inaldra_quest") || (groundquests.hasCompletedQuest(player, "npe_pointer_inaldra") && !groundquests.isQuestActive(player, "npe_inaldra_quest"));
    }
    public boolean npe_station_inaldra2_condition_finishedMainQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "npe_new_main_quest");
    }
    public boolean npe_station_inaldra2_condition_onRewardStep(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "npe_inaldra_quest", "inaldrasignal");
    }
    public boolean npe_station_inaldra2_condition_onInaldraQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "npe_inaldra_quest");
    }
    public boolean npe_station_inaldra2_condition_getOffStationNow(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isQuestActive(player, "npe_pointer_inaldra") && !groundquests.hasCompletedQuest(player, "npe_new_main_quest"));
    }
    public boolean npe_station_inaldra2_condition_isBH(obj_id player, obj_id npc) throws InterruptedException
    {
        String pTemplate = getSkillTemplate(player);
        if (pTemplate.indexOf("bounty_hunter") > -1)
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public void npe_station_inaldra2_action_forwardInaldraQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "npe_inaldra_quest_reward");
        groundquests.grantQuest(player, "npe_pointer_secretary");
    }
    public void npe_station_inaldra2_action_giveInaldraQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "npe_new_main_inaldra");
        groundquests.requestGrantQuest(player, "npe_inaldra_quest");
    }
    public void npe_station_inaldra2_action_completePointerQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "npe_new_main_inaldra");
    }
    public void npe_station_inaldra2_action_facePlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
    }
    public int npe_station_inaldra2_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_26"))
        {
            if (npe_station_inaldra2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_29");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_station_inaldra2_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.npe_station_inaldra2.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_station_inaldra2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_27"))
        {
            if (npe_station_inaldra2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_28");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_station_inaldra2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (npe_station_inaldra2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (npe_station_inaldra2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (npe_station_inaldra2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (npe_station_inaldra2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_56");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_60");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_64");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_68");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_74");
                    }
                    utils.setScriptVar(player, "conversation.npe_station_inaldra2.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_station_inaldra2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_station_inaldra2_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_30"))
        {
            if (npe_station_inaldra2_condition__defaultCondition(player, npc))
            {
                npe_station_inaldra2_action_forwardInaldraQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_118");
                utils.removeScriptVar(player, "conversation.npe_station_inaldra2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_station_inaldra2_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_56"))
        {
            if (npe_station_inaldra2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_58");
                utils.removeScriptVar(player, "conversation.npe_station_inaldra2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_60"))
        {
            if (npe_station_inaldra2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_62");
                utils.removeScriptVar(player, "conversation.npe_station_inaldra2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_64"))
        {
            if (npe_station_inaldra2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_66");
                utils.removeScriptVar(player, "conversation.npe_station_inaldra2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_68"))
        {
            if (npe_station_inaldra2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_70");
                utils.removeScriptVar(player, "conversation.npe_station_inaldra2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_74"))
        {
            if (!npe_station_inaldra2_condition_isBH(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_75");
                utils.removeScriptVar(player, "conversation.npe_station_inaldra2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (npe_station_inaldra2_condition_isBH(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_72");
                utils.removeScriptVar(player, "conversation.npe_station_inaldra2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_station_inaldra2_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_16"))
        {
            if (npe_station_inaldra2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_18");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_station_inaldra2_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.npe_station_inaldra2.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_station_inaldra2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_50"))
        {
            if (npe_station_inaldra2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_52");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_station_inaldra2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (npe_station_inaldra2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (npe_station_inaldra2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (npe_station_inaldra2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (npe_station_inaldra2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_56");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_60");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_64");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_68");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_74");
                    }
                    utils.setScriptVar(player, "conversation.npe_station_inaldra2.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_station_inaldra2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_station_inaldra2_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_20"))
        {
            if (npe_station_inaldra2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_22");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_station_inaldra2_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.npe_station_inaldra2.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_station_inaldra2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_station_inaldra2_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_24"))
        {
            doAnimationAction(player, "shrug_shoulders");
            if (npe_station_inaldra2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_104");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_station_inaldra2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_106");
                    }
                    utils.setScriptVar(player, "conversation.npe_station_inaldra2.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_station_inaldra2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_station_inaldra2_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_106"))
        {
            if (npe_station_inaldra2_condition__defaultCondition(player, npc))
            {
                npe_station_inaldra2_action_giveInaldraQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_108");
                utils.removeScriptVar(player, "conversation.npe_station_inaldra2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_station_inaldra2_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_56"))
        {
            if (npe_station_inaldra2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_58");
                utils.removeScriptVar(player, "conversation.npe_station_inaldra2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_60"))
        {
            if (npe_station_inaldra2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_62");
                utils.removeScriptVar(player, "conversation.npe_station_inaldra2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_64"))
        {
            if (npe_station_inaldra2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_66");
                utils.removeScriptVar(player, "conversation.npe_station_inaldra2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_68"))
        {
            if (npe_station_inaldra2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_70");
                utils.removeScriptVar(player, "conversation.npe_station_inaldra2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_74"))
        {
            if (!npe_station_inaldra2_condition_isBH(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_75");
                utils.removeScriptVar(player, "conversation.npe_station_inaldra2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (npe_station_inaldra2_condition_isBH(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_72");
                utils.removeScriptVar(player, "conversation.npe_station_inaldra2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_station_inaldra2_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_56"))
        {
            if (npe_station_inaldra2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_58");
                utils.removeScriptVar(player, "conversation.npe_station_inaldra2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_60"))
        {
            if (npe_station_inaldra2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_62");
                utils.removeScriptVar(player, "conversation.npe_station_inaldra2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_64"))
        {
            if (npe_station_inaldra2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_66");
                utils.removeScriptVar(player, "conversation.npe_station_inaldra2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_68"))
        {
            if (npe_station_inaldra2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_70");
                utils.removeScriptVar(player, "conversation.npe_station_inaldra2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_74"))
        {
            if (!npe_station_inaldra2_condition_isBH(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_75");
                utils.removeScriptVar(player, "conversation.npe_station_inaldra2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (npe_station_inaldra2_condition_isBH(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_72");
                utils.removeScriptVar(player, "conversation.npe_station_inaldra2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.npe_station_inaldra2");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setName(self, "Inaldra");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        setAnimationMood(self, "npc_sitting_chair");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setName(self, "Inaldra");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        setAnimationMood(self, "npc_sitting_chair");
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
        detachScript(self, "conversation.npe_station_inaldra2");
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
        if (npe_station_inaldra2_condition_finishedInaldraQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_34");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (npe_station_inaldra2_condition_onInaldraQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_25");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (npe_station_inaldra2_condition_onRewardStep(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (npe_station_inaldra2_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_27");
                }
                utils.setScriptVar(player, "conversation.npe_station_inaldra2.branchId", 2);
                npcStartConversation(player, npc, "npe_station_inaldra2", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (npe_station_inaldra2_condition_finishedMainQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_14");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (npe_station_inaldra2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (npe_station_inaldra2_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_16");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_50");
                }
                utils.setScriptVar(player, "conversation.npe_station_inaldra2.branchId", 6);
                npcStartConversation(player, npc, "npe_station_inaldra2", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (npe_station_inaldra2_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_54");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (npe_station_inaldra2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (npe_station_inaldra2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (npe_station_inaldra2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (npe_station_inaldra2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse3 = true;
            }
            boolean hasResponse4 = false;
            if (npe_station_inaldra2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse4 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_56");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_60");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_64");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_68");
                }
                if (hasResponse4)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_74");
                }
                utils.setScriptVar(player, "conversation.npe_station_inaldra2.branchId", 12);
                npcStartConversation(player, npc, "npe_station_inaldra2", message, responses);
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
        if (!conversationId.equals("npe_station_inaldra2"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.npe_station_inaldra2.branchId");
        if (branchId == 2 && npe_station_inaldra2_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && npe_station_inaldra2_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && npe_station_inaldra2_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && npe_station_inaldra2_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && npe_station_inaldra2_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && npe_station_inaldra2_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && npe_station_inaldra2_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && npe_station_inaldra2_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && npe_station_inaldra2_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.npe_station_inaldra2.branchId");
        return SCRIPT_CONTINUE;
    }
}
