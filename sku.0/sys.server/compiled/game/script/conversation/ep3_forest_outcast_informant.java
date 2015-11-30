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

public class ep3_forest_outcast_informant extends script.base_script
{
    public ep3_forest_outcast_informant()
    {
    }
    public static String c_stringFile = "conversation/ep3_forest_outcast_informant";
    public boolean ep3_forest_outcast_informant_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean ep3_forest_outcast_informant_condition_isEpicTaskActiveBad(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "ep3_forest_wirartu_epic_2", 0) || groundquests.hasCompletedQuest(player, "ep3_forest_wirartu_epic_2"));
    }
    public boolean ep3_forest_outcast_informant_condition_isEpicTaskActiveGood(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "ep3_forest_wirartu_epic_3", 0) || groundquests.hasCompletedQuest(player, "ep3_forest_wirartu_epic_3") || groundquests.hasCompletedQuest(player, "ep3_forest_kerritamba_epic_7"));
    }
    public boolean ep3_forest_outcast_informant_condition_isEpicTaskActiveContact(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "ep3_forest_outcast_contact", 0) || groundquests.hasCompletedQuest(player, "ep3_forest_outcast_contact"));
    }
    public boolean ep3_forest_outcast_informant_condition_isEpicTaskAssassinActive(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "ep3_forest_outcast_assassin", 0) || groundquests.hasCompletedQuest(player, "ep3_forest_outcast_assassin"));
    }
    public boolean ep3_forest_outcast_informant_condition_ChkActiveAssassin(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_forest_outcast_assassin", 0);
    }
    public boolean ep3_forest_outcast_informant_condition_ChkCompleteAssassin(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "ep3_forest_outcast_assassin");
    }
    public boolean ep3_forest_outcast_informant_condition_ChkContactActive(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_forest_outcast_contact", 0);
    }
    public boolean ep3_forest_outcast_informant_condition_ChkContactFinish(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "ep3_forest_outcast_contact");
    }
    public boolean ep3_forest_outcast_informant_condition_isTaskActiveOne(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_forest_cryl_quest_2", 0);
    }
    public boolean ep3_forest_outcast_informant_condition_hasCompletedQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "ep3_forest_cryl_quest_2");
    }
    public void ep3_forest_outcast_informant_action_giveAssassinQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "ep3_forest_outcast_assassin");
    }
    public void ep3_forest_outcast_informant_action_giveContactQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "ep3_forest_outcast_contact");
    }
    public void ep3_forest_outcast_informant_action_giveSignalOne(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "zhadran");
    }
    public int ep3_forest_outcast_informant_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_586"))
        {
            if (ep3_forest_outcast_informant_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_590");
                utils.removeScriptVar(player, "conversation.ep3_forest_outcast_informant.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_outcast_informant_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_598"))
        {
            if (ep3_forest_outcast_informant_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_602");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_outcast_informant_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_606");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_outcast_informant.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_outcast_informant.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_outcast_informant_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_606"))
        {
            if (ep3_forest_outcast_informant_condition__defaultCondition(player, npc))
            {
                ep3_forest_outcast_informant_action_giveSignalOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_610");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_outcast_informant_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_612");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_outcast_informant.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_outcast_informant.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_outcast_informant_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_612"))
        {
            if (ep3_forest_outcast_informant_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_614");
                utils.removeScriptVar(player, "conversation.ep3_forest_outcast_informant.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_outcast_informant_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_618"))
        {
            if (ep3_forest_outcast_informant_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_620");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_outcast_informant_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_622");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_outcast_informant.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_outcast_informant.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_626"))
        {
            if (ep3_forest_outcast_informant_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_675");
                utils.removeScriptVar(player, "conversation.ep3_forest_outcast_informant.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_outcast_informant_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_622"))
        {
            if (ep3_forest_outcast_informant_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_624");
                utils.removeScriptVar(player, "conversation.ep3_forest_outcast_informant.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_outcast_informant_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_630"))
        {
            if (ep3_forest_outcast_informant_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_632");
                utils.removeScriptVar(player, "conversation.ep3_forest_outcast_informant.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_634"))
        {
            if (ep3_forest_outcast_informant_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_636");
                utils.removeScriptVar(player, "conversation.ep3_forest_outcast_informant.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_outcast_informant_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_640"))
        {
            if (ep3_forest_outcast_informant_condition__defaultCondition(player, npc))
            {
                ep3_forest_outcast_informant_action_giveContactQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_642");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_outcast_informant_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_644");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_outcast_informant.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_outcast_informant.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_outcast_informant_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_644"))
        {
            if (ep3_forest_outcast_informant_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_646");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_outcast_informant_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_648");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_outcast_informant.branchId", 16);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_outcast_informant.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_outcast_informant_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_648"))
        {
            if (ep3_forest_outcast_informant_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_650");
                utils.removeScriptVar(player, "conversation.ep3_forest_outcast_informant.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_outcast_informant_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_654"))
        {
            if (ep3_forest_outcast_informant_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_656");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_outcast_informant_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_658");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_outcast_informant.branchId", 19);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_outcast_informant.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_outcast_informant_handleBranch19(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_658"))
        {
            if (ep3_forest_outcast_informant_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_660");
                utils.removeScriptVar(player, "conversation.ep3_forest_outcast_informant.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_outcast_informant_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_664"))
        {
            if (ep3_forest_outcast_informant_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_666");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_outcast_informant_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_668");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_outcast_informant.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_outcast_informant.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_outcast_informant_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_668"))
        {
            if (ep3_forest_outcast_informant_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_670");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_outcast_informant_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_672");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_outcast_informant.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_outcast_informant.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_outcast_informant_handleBranch23(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_672"))
        {
            if (ep3_forest_outcast_informant_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_674");
                utils.removeScriptVar(player, "conversation.ep3_forest_outcast_informant.branchId");
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
            detachScript(self, "conversation.ep3_forest_outcast_informant");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setName(self, new string_id("ep3/npc_names", "zhadran"));
        setInvulnerable(self, true);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setName(self, new string_id("ep3/npc_names", "zhadran"));
        setInvulnerable(self, true);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info menuInfo) throws InterruptedException
    {
        int menu = menuInfo.addRootMenu(menu_info_types.CONVERSE_START, null);
        menu_info_data menuInfoData = menuInfo.getMenuItemById(menu);
        menuInfoData.setServerNotify(false);
        setCondition(self, CONDITION_CONVERSABLE);
        faceTo(self, player);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        detachScript(self, "conversation.ep3_forest_outcast_informant");
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
        if (ep3_forest_outcast_informant_condition_hasCompletedQuestOne(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_583");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_forest_outcast_informant_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_586");
                }
                utils.setScriptVar(player, "conversation.ep3_forest_outcast_informant.branchId", 1);
                npcStartConversation(player, npc, "ep3_forest_outcast_informant", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_forest_outcast_informant_condition_isTaskActiveOne(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_594");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_forest_outcast_informant_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_598");
                }
                utils.setScriptVar(player, "conversation.ep3_forest_outcast_informant.branchId", 3);
                npcStartConversation(player, npc, "ep3_forest_outcast_informant", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_forest_outcast_informant_condition_isEpicTaskActiveContact(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_616");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_forest_outcast_informant_condition_ChkContactActive(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_forest_outcast_informant_condition_ChkContactFinish(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_618");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_626");
                }
                utils.setScriptVar(player, "conversation.ep3_forest_outcast_informant.branchId", 7);
                npcStartConversation(player, npc, "ep3_forest_outcast_informant", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_forest_outcast_informant_condition_isEpicTaskAssassinActive(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_628");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_forest_outcast_informant_condition_ChkActiveAssassin(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_forest_outcast_informant_condition_ChkCompleteAssassin(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_630");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_634");
                }
                utils.setScriptVar(player, "conversation.ep3_forest_outcast_informant.branchId", 11);
                npcStartConversation(player, npc, "ep3_forest_outcast_informant", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_forest_outcast_informant_condition_isEpicTaskActiveBad(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_638");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_forest_outcast_informant_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_640");
                }
                utils.setScriptVar(player, "conversation.ep3_forest_outcast_informant.branchId", 14);
                npcStartConversation(player, npc, "ep3_forest_outcast_informant", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_forest_outcast_informant_condition_isEpicTaskActiveGood(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_652");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_forest_outcast_informant_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_654");
                }
                utils.setScriptVar(player, "conversation.ep3_forest_outcast_informant.branchId", 18);
                npcStartConversation(player, npc, "ep3_forest_outcast_informant", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_forest_outcast_informant_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_662");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_forest_outcast_informant_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_664");
                }
                utils.setScriptVar(player, "conversation.ep3_forest_outcast_informant.branchId", 21);
                npcStartConversation(player, npc, "ep3_forest_outcast_informant", message, responses);
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
        if (!conversationId.equals("ep3_forest_outcast_informant"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.ep3_forest_outcast_informant.branchId");
        if (branchId == 1 && ep3_forest_outcast_informant_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && ep3_forest_outcast_informant_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && ep3_forest_outcast_informant_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && ep3_forest_outcast_informant_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && ep3_forest_outcast_informant_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && ep3_forest_outcast_informant_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && ep3_forest_outcast_informant_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && ep3_forest_outcast_informant_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && ep3_forest_outcast_informant_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && ep3_forest_outcast_informant_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && ep3_forest_outcast_informant_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && ep3_forest_outcast_informant_handleBranch19(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && ep3_forest_outcast_informant_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && ep3_forest_outcast_informant_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && ep3_forest_outcast_informant_handleBranch23(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.ep3_forest_outcast_informant.branchId");
        return SCRIPT_CONTINUE;
    }
}
