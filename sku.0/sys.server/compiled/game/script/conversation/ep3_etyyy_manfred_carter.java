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

public class ep3_etyyy_manfred_carter extends script.base_script
{
    public ep3_etyyy_manfred_carter()
    {
    }
    public static String c_stringFile = "conversation/ep3_etyyy_manfred_carter";
    public boolean ep3_etyyy_manfred_carter_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean ep3_etyyy_manfred_carter_condition_hasCompletedManfredQuests(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "ep3_hunt_manfred_kill_chiss_leader");
    }
    public boolean ep3_etyyy_manfred_carter_condition_finishedCollectChissChemicals(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_hunt_manfred_collect_enhancements", "manfred_chissChemicals") || groundquests.hasCompletedQuest(player, "ep3_hunt_manfred_collect_enhancements");
    }
    public boolean ep3_etyyy_manfred_carter_condition_finishedStealChissGoods(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_hunt_manfred_steal_chiss_goods", "manfred_backToManfred") || groundquests.hasCompletedQuest(player, "ep3_hunt_manfred_steal_chiss_goods");
    }
    public boolean ep3_etyyy_manfred_carter_condition_isCollectingChissChemicals(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_hunt_manfred_collect_enhancements", "manfred_collectChemicals");
    }
    public boolean ep3_etyyy_manfred_carter_condition_isStealingChissGoods(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_hunt_manfred_steal_chiss_goods", "manfred_stealChissGoods");
    }
    public boolean ep3_etyyy_manfred_carter_condition_isKillingChissLeader(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_hunt_manfred_kill_chiss_leader", "manfred_killingChissLeader");
    }
    public boolean ep3_etyyy_manfred_carter_condition_finishedKillChissLeader(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_hunt_manfred_kill_chiss_leader", "manfred_killedChissLeader");
    }
    public boolean ep3_etyyy_manfred_carter_condition_isDeliveringToKerssoc(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_hunt_manfred_steal_chiss_goods", "manfred_deliverToKerssoc");
    }
    public boolean ep3_etyyy_manfred_carter_condition_hasToTalkToManfred(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_hunt_manfred_steal_chiss_goods", "manfred_talkToManfred");
    }
    public void ep3_etyyy_manfred_carter_action_killChissLeader(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "ep3_hunt_manfred_kill_chiss_leader");
    }
    public void ep3_etyyy_manfred_carter_action_speakWithTripp(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "manfred_killedChissLeader");
        groundquests.grantQuest(player, "ep3_hunt_tripp_collect_mouf_pelts");
    }
    public void ep3_etyyy_manfred_carter_action_stealChissGoods(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "manfred_talkToManfred");
    }
    public void ep3_etyyy_manfred_carter_action_collectChissChemicals(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "ep3_hunt_manfred_collect_enhancements");
    }
    public void ep3_etyyy_manfred_carter_action_doneStealChissGoods(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "manfred_backToManfred");
    }
    public void ep3_etyyy_manfred_carter_action_doneCollectChemicals(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "manfred_chissChemicals");
    }
    public int ep3_etyyy_manfred_carter_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_667"))
        {
            if (ep3_etyyy_manfred_carter_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_manfred_carter_action_speakWithTripp(player, npc);
                string_id message = new string_id(c_stringFile, "s_670");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_manfred_carter.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_668"))
        {
            if (ep3_etyyy_manfred_carter_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_671");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_manfred_carter.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_manfred_carter_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_663"))
        {
            if (ep3_etyyy_manfred_carter_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_manfred_carter_action_killChissLeader(player, npc);
                string_id message = new string_id(c_stringFile, "s_665");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_manfred_carter.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_664"))
        {
            if (ep3_etyyy_manfred_carter_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_666");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_manfred_carter.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_manfred_carter_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_570"))
        {
            if (ep3_etyyy_manfred_carter_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_manfred_carter_action_collectChissChemicals(player, npc);
                string_id message = new string_id(c_stringFile, "s_572");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_manfred_carter.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_661"))
        {
            if (ep3_etyyy_manfred_carter_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_662");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_manfred_carter.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_manfred_carter_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_592"))
        {
            if (ep3_etyyy_manfred_carter_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_manfred_carter_action_stealChissGoods(player, npc);
                string_id message = new string_id(c_stringFile, "s_594");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_manfred_carter.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_596"))
        {
            if (ep3_etyyy_manfred_carter_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_598");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_manfred_carter.branchId");
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
            detachScript(self, "conversation.ep3_etyyy_manfred_carter");
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
        detachScript(self, "conversation.ep3_etyyy_manfred_carter");
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
        if (ep3_etyyy_manfred_carter_condition_hasCompletedManfredQuests(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_566");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_manfred_carter_condition_finishedKillChissLeader(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_657");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_manfred_carter_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_etyyy_manfred_carter_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_667");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_668");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_manfred_carter.branchId", 2);
                npcStartConversation(player, npc, "ep3_etyyy_manfred_carter", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_manfred_carter_condition_isKillingChissLeader(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_658");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_manfred_carter_condition_finishedCollectChissChemicals(player, npc))
        {
            ep3_etyyy_manfred_carter_action_doneCollectChemicals(player, npc);
            string_id message = new string_id(c_stringFile, "s_659");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_manfred_carter_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_etyyy_manfred_carter_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_663");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_664");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_manfred_carter.branchId", 6);
                npcStartConversation(player, npc, "ep3_etyyy_manfred_carter", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_manfred_carter_condition_isCollectingChissChemicals(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_660");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_manfred_carter_condition_finishedStealChissGoods(player, npc))
        {
            ep3_etyyy_manfred_carter_action_doneStealChissGoods(player, npc);
            string_id message = new string_id(c_stringFile, "s_568");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_manfred_carter_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_etyyy_manfred_carter_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_570");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_661");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_manfred_carter.branchId", 10);
                npcStartConversation(player, npc, "ep3_etyyy_manfred_carter", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_manfred_carter_condition_isDeliveringToKerssoc(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_574");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_manfred_carter_condition_isStealingChissGoods(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_584");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_manfred_carter_condition_hasToTalkToManfred(player, npc))
        {
            doAnimationAction(npc, "greet");
            string_id message = new string_id(c_stringFile, "s_590");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_manfred_carter_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_etyyy_manfred_carter_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_592");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_596");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_manfred_carter.branchId", 15);
                npcStartConversation(player, npc, "ep3_etyyy_manfred_carter", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_manfred_carter_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_600");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("ep3_etyyy_manfred_carter"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.ep3_etyyy_manfred_carter.branchId");
        if (branchId == 2 && ep3_etyyy_manfred_carter_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && ep3_etyyy_manfred_carter_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && ep3_etyyy_manfred_carter_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && ep3_etyyy_manfred_carter_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.ep3_etyyy_manfred_carter.branchId");
        return SCRIPT_CONTINUE;
    }
}
