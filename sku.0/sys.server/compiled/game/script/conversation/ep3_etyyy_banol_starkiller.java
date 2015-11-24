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
import script.library.space_quest;
import script.library.utils;

public class ep3_etyyy_banol_starkiller extends script.base_script
{
    public ep3_etyyy_banol_starkiller()
    {
    }
    public static String c_stringFile = "conversation/ep3_etyyy_banol_starkiller";
    public boolean ep3_etyyy_banol_starkiller_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean ep3_etyyy_banol_starkiller_condition_completedQuestTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasWonQuest(player, "recovery", "ep3_hunting_banol_capture_fordan");
    }
    public boolean ep3_etyyy_banol_starkiller_condition_completedQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasWonQuest(player, "assassinate", "ep3_hunting_banol_destroy_tripps_goods");
    }
    public boolean ep3_etyyy_banol_starkiller_condition_canDoBanolQuests(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasObjVar(player, "ep3Etyyy.canDoBanolQuests");
    }
    public boolean ep3_etyyy_banol_starkiller_condition_isOnQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasQuest(player, "assassinate", "ep3_hunting_banol_destroy_tripps_goods");
    }
    public boolean ep3_etyyy_banol_starkiller_condition_isOnQuestTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasQuest(player, "recovery", "ep3_hunting_banol_capture_fordan");
    }
    public boolean ep3_etyyy_banol_starkiller_condition_completedBothQuests(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasWonQuest(player, "assassinate", "ep3_hunting_banol_destroy_tripps_goods") && (space_quest.hasWonQuest(player, "recovery", "ep3_hunting_banol_capture_fordan") || space_quest.hasFailedQuest(player, "recovery", "ep3_hunting_banol_capture_fordan"));
    }
    public boolean ep3_etyyy_banol_starkiller_condition_failedQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasFailedQuest(player, "assassinate", "ep3_hunting_banol_destroy_tripps_goods") || space_quest.hasAbortedQuest(player, "assassinate", "ep3_hunting_banol_destroy_tripps_goods");
    }
    public boolean ep3_etyyy_banol_starkiller_condition_failedQuestTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasFailedQuest(player, "recovery", "ep3_hunting_banol_capture_fordan") || space_quest.hasAbortedQuest(player, "recovery", "ep3_hunting_banol_capture_fordan");
    }
    public boolean ep3_etyyy_banol_starkiller_condition_alreadyHasSpaceQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasQuest(player);
    }
    public void ep3_etyyy_banol_starkiller_action_grantQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.clearQuestFlags(player, "assassinate", "ep3_hunting_banol_destroy_tripps_goods");
        space_quest.grantQuest(player, "assassinate", "ep3_hunting_banol_destroy_tripps_goods");
    }
    public void ep3_etyyy_banol_starkiller_action_grantQuestTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.clearQuestFlags(player, "assassinate", "ep3_hunting_banol_destroy_tripps_goods");
        space_quest.clearQuestFlags(player, "recovery", "ep3_hunting_banol_capture_fordan");
        space_quest.grantQuest(player, "recovery", "ep3_hunting_banol_capture_fordan");
    }
    public int ep3_etyyy_banol_starkiller_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_488"))
        {
            if (ep3_etyyy_banol_starkiller_condition_alreadyHasSpaceQuest(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_752");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_banol_starkiller.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_banol_starkiller_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_banol_starkiller_action_grantQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_520");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_banol_starkiller.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_492"))
        {
            if (ep3_etyyy_banol_starkiller_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_494");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_banol_starkiller.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_banol_starkiller_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_498"))
        {
            if (ep3_etyyy_banol_starkiller_condition_alreadyHasSpaceQuest(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_752");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_banol_starkiller.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_banol_starkiller_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_banol_starkiller_action_grantQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_520");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_banol_starkiller.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_502"))
        {
            if (ep3_etyyy_banol_starkiller_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_504");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_banol_starkiller.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_banol_starkiller_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_508"))
        {
            if (ep3_etyyy_banol_starkiller_condition_alreadyHasSpaceQuest(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_752");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_banol_starkiller.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_banol_starkiller_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_banol_starkiller_action_grantQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_520");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_banol_starkiller.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_512"))
        {
            if (ep3_etyyy_banol_starkiller_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_514");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_banol_starkiller.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_banol_starkiller_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_518"))
        {
            if (ep3_etyyy_banol_starkiller_condition_alreadyHasSpaceQuest(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_752");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_banol_starkiller.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_banol_starkiller_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_banol_starkiller_action_grantQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_520");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_banol_starkiller.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_522"))
        {
            if (ep3_etyyy_banol_starkiller_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_524");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_banol_starkiller.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_banol_starkiller_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_528"))
        {
            if (ep3_etyyy_banol_starkiller_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_530");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_banol_starkiller_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_banol_starkiller_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_532");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_536");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_banol_starkiller.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_banol_starkiller.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_540"))
        {
            if (ep3_etyyy_banol_starkiller_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_542");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_banol_starkiller_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_banol_starkiller_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_544");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_548");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_banol_starkiller.branchId", 16);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_banol_starkiller.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_552"))
        {
            if (ep3_etyyy_banol_starkiller_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_554");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_banol_starkiller.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_banol_starkiller_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_532"))
        {
            if (ep3_etyyy_banol_starkiller_condition_alreadyHasSpaceQuest(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_753");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_banol_starkiller.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_banol_starkiller_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_banol_starkiller_action_grantQuestTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_534");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_banol_starkiller.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_536"))
        {
            if (ep3_etyyy_banol_starkiller_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_538");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_banol_starkiller.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_banol_starkiller_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_544"))
        {
            if (ep3_etyyy_banol_starkiller_condition_alreadyHasSpaceQuest(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_754");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_banol_starkiller.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_banol_starkiller_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_banol_starkiller_action_grantQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_546");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_banol_starkiller.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_548"))
        {
            if (ep3_etyyy_banol_starkiller_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_550");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_banol_starkiller.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_banol_starkiller_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_558"))
        {
            if (ep3_etyyy_banol_starkiller_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_560");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_banol_starkiller_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_banol_starkiller_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_562");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_566");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_banol_starkiller.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_banol_starkiller.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_banol_starkiller_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_562"))
        {
            if (ep3_etyyy_banol_starkiller_condition_alreadyHasSpaceQuest(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_755");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_banol_starkiller.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_banol_starkiller_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_banol_starkiller_action_grantQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_564");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_banol_starkiller.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_566"))
        {
            if (ep3_etyyy_banol_starkiller_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_568");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_banol_starkiller.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.ep3_etyyy_banol_starkiller");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
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
        detachScript(self, "conversation.ep3_etyyy_banol_starkiller");
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
        if (ep3_etyyy_banol_starkiller_condition_failedQuestOne(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_486");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_banol_starkiller_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_etyyy_banol_starkiller_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_488");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_492");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_banol_starkiller.branchId", 1);
                npcStartConversation(player, npc, "ep3_etyyy_banol_starkiller", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_banol_starkiller_condition_completedBothQuests(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_496");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_banol_starkiller_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_etyyy_banol_starkiller_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_498");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_502");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_banol_starkiller.branchId", 3);
                npcStartConversation(player, npc, "ep3_etyyy_banol_starkiller", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_banol_starkiller_condition_failedQuestTwo(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_506");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_banol_starkiller_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_etyyy_banol_starkiller_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_508");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_512");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_banol_starkiller.branchId", 5);
                npcStartConversation(player, npc, "ep3_etyyy_banol_starkiller", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_banol_starkiller_condition_completedQuestTwo(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_516");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_banol_starkiller_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_etyyy_banol_starkiller_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_518");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_522");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_banol_starkiller.branchId", 7);
                npcStartConversation(player, npc, "ep3_etyyy_banol_starkiller", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_banol_starkiller_condition_completedQuestOne(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_526");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_banol_starkiller_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_etyyy_banol_starkiller_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (ep3_etyyy_banol_starkiller_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_528");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_540");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_552");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_banol_starkiller.branchId", 11);
                npcStartConversation(player, npc, "ep3_etyyy_banol_starkiller", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_banol_starkiller_condition_canDoBanolQuests(player, npc))
        {
            doAnimationAction(npc, "greet");
            string_id message = new string_id(c_stringFile, "s_556");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_banol_starkiller_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_558");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_banol_starkiller.branchId", 21);
                npcStartConversation(player, npc, "ep3_etyyy_banol_starkiller", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_banol_starkiller_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_570");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("ep3_etyyy_banol_starkiller"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.ep3_etyyy_banol_starkiller.branchId");
        if (branchId == 1 && ep3_etyyy_banol_starkiller_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && ep3_etyyy_banol_starkiller_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && ep3_etyyy_banol_starkiller_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && ep3_etyyy_banol_starkiller_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && ep3_etyyy_banol_starkiller_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && ep3_etyyy_banol_starkiller_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && ep3_etyyy_banol_starkiller_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && ep3_etyyy_banol_starkiller_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && ep3_etyyy_banol_starkiller_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.ep3_etyyy_banol_starkiller.branchId");
        return SCRIPT_CONTINUE;
    }
}
