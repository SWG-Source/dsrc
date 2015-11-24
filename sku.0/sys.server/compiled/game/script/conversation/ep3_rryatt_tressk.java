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

public class ep3_rryatt_tressk extends script.base_script
{
    public ep3_rryatt_tressk()
    {
    }
    public static String c_stringFile = "conversation/ep3_rryatt_tressk";
    public boolean ep3_rryatt_tressk_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean ep3_rryatt_tressk_condition_finishedLostRodianHunters(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "ep3_rryatt_tressk_kill_lost_rodian_hunters", "tressk_lostRodianHuntersCompleted") || groundquests.hasCompletedQuest(player, "ep3_rryatt_tressk_kill_lost_rodian_hunters"));
    }
    public boolean ep3_rryatt_tressk_condition_finishedDeepWoodsPoachers(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "ep3_rryatt_tressk_kill_deep_woods_poachers", "tressk_deepWoodsPoachersCompleted") || groundquests.hasCompletedQuest(player, "ep3_rryatt_tressk_kill_deep_woods_poachers"));
    }
    public boolean ep3_rryatt_tressk_condition_finishedGotalHunters(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "ep3_rryatt_tressk_kill_gotal_hunters", "tressk_chooseJuntiMace"));
    }
    public boolean ep3_rryatt_tressk_condition_onLostRodianHunters(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_rryatt_tressk_kill_lost_rodian_hunters", "tressk_findLostRodianHunters");
    }
    public boolean ep3_rryatt_tressk_condition_onDeepWoodsPoachers(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_rryatt_tressk_kill_deep_woods_poachers", "tressk_stopDeepWoodsPoachers");
    }
    public boolean ep3_rryatt_tressk_condition_onGotalHunters(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_rryatt_tressk_kill_gotal_hunters", "tressk_defeatGotalHunters");
    }
    public boolean ep3_rryatt_tressk_condition_completelyFinished(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "ep3_rryatt_tressk_kill_gotal_hunters");
    }
    public void ep3_rryatt_tressk_action_doneLostRodianHunters(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "tressk_lostRodianHuntersCompleted");
    }
    public void ep3_rryatt_tressk_action_doneDeepWoodsPoachers(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "tressk_deepWoodsPoachersCompleted");
    }
    public void ep3_rryatt_tressk_action_doneGotalHunters_Mace(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "tressk_chooseJuntiMace");
    }
    public void ep3_rryatt_tressk_action_killLostRodianHunters(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "ep3_rryatt_tressk_kill_lost_rodian_hunters");
    }
    public void ep3_rryatt_tressk_action_killDeepWoodsPoachers(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "ep3_rryatt_tressk_kill_deep_woods_poachers");
    }
    public void ep3_rryatt_tressk_action_killGotalHunters(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "ep3_rryatt_tressk_kill_gotal_hunters");
    }
    public void ep3_rryatt_tressk_action_doneGotalHunters_Pistol(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "tressk_chooseFlechettePistol");
    }
    public int ep3_rryatt_tressk_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_25"))
        {
            if (ep3_rryatt_tressk_condition__defaultCondition(player, npc))
            {
                ep3_rryatt_tressk_action_doneGotalHunters_Mace(player, npc);
                string_id message = new string_id(c_stringFile, "s_27");
                utils.removeScriptVar(player, "conversation.ep3_rryatt_tressk.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_26"))
        {
            if (ep3_rryatt_tressk_condition__defaultCondition(player, npc))
            {
                ep3_rryatt_tressk_action_doneGotalHunters_Pistol(player, npc);
                string_id message = new string_id(c_stringFile, "s_28");
                utils.removeScriptVar(player, "conversation.ep3_rryatt_tressk.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_29"))
        {
            if (ep3_rryatt_tressk_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_30");
                utils.removeScriptVar(player, "conversation.ep3_rryatt_tressk.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_rryatt_tressk_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1385"))
        {
            if (ep3_rryatt_tressk_condition__defaultCondition(player, npc))
            {
                ep3_rryatt_tressk_action_killGotalHunters(player, npc);
                string_id message = new string_id(c_stringFile, "s_1387");
                utils.removeScriptVar(player, "conversation.ep3_rryatt_tressk.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1386"))
        {
            if (ep3_rryatt_tressk_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1388");
                utils.removeScriptVar(player, "conversation.ep3_rryatt_tressk.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_rryatt_tressk_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1381"))
        {
            if (ep3_rryatt_tressk_condition__defaultCondition(player, npc))
            {
                ep3_rryatt_tressk_action_killDeepWoodsPoachers(player, npc);
                string_id message = new string_id(c_stringFile, "s_1383");
                utils.removeScriptVar(player, "conversation.ep3_rryatt_tressk.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1382"))
        {
            if (ep3_rryatt_tressk_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1384");
                utils.removeScriptVar(player, "conversation.ep3_rryatt_tressk.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_rryatt_tressk_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1373"))
        {
            if (ep3_rryatt_tressk_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1375");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_rryatt_tressk_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_rryatt_tressk_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1377");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1378");
                    }
                    utils.setScriptVar(player, "conversation.ep3_rryatt_tressk.branchId", 16);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_rryatt_tressk.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1374"))
        {
            if (ep3_rryatt_tressk_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1376");
                utils.removeScriptVar(player, "conversation.ep3_rryatt_tressk.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_rryatt_tressk_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1377"))
        {
            if (ep3_rryatt_tressk_condition__defaultCondition(player, npc))
            {
                ep3_rryatt_tressk_action_killLostRodianHunters(player, npc);
                string_id message = new string_id(c_stringFile, "s_1379");
                utils.removeScriptVar(player, "conversation.ep3_rryatt_tressk.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1378"))
        {
            if (ep3_rryatt_tressk_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1380");
                utils.removeScriptVar(player, "conversation.ep3_rryatt_tressk.branchId");
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
            detachScript(self, "conversation.ep3_rryatt_tressk");
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
        detachScript(self, "conversation.ep3_rryatt_tressk");
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
        if (ep3_rryatt_tressk_condition_completelyFinished(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_31");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_rryatt_tressk_condition_finishedGotalHunters(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1367");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_rryatt_tressk_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_rryatt_tressk_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (ep3_rryatt_tressk_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_25");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_26");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_29");
                }
                utils.setScriptVar(player, "conversation.ep3_rryatt_tressk.branchId", 2);
                npcStartConversation(player, npc, "ep3_rryatt_tressk", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_rryatt_tressk_condition_onGotalHunters(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1368");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_rryatt_tressk_condition_finishedDeepWoodsPoachers(player, npc))
        {
            ep3_rryatt_tressk_action_doneDeepWoodsPoachers(player, npc);
            string_id message = new string_id(c_stringFile, "s_1369");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_rryatt_tressk_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_rryatt_tressk_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1385");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1386");
                }
                utils.setScriptVar(player, "conversation.ep3_rryatt_tressk.branchId", 7);
                npcStartConversation(player, npc, "ep3_rryatt_tressk", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_rryatt_tressk_condition_onDeepWoodsPoachers(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1370");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_rryatt_tressk_condition_finishedLostRodianHunters(player, npc))
        {
            ep3_rryatt_tressk_action_doneLostRodianHunters(player, npc);
            string_id message = new string_id(c_stringFile, "s_1371");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_rryatt_tressk_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_rryatt_tressk_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1381");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1382");
                }
                utils.setScriptVar(player, "conversation.ep3_rryatt_tressk.branchId", 11);
                npcStartConversation(player, npc, "ep3_rryatt_tressk", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_rryatt_tressk_condition_onLostRodianHunters(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1372");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_rryatt_tressk_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1366");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_rryatt_tressk_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_rryatt_tressk_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1373");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1374");
                }
                utils.setScriptVar(player, "conversation.ep3_rryatt_tressk.branchId", 15);
                npcStartConversation(player, npc, "ep3_rryatt_tressk", message, responses);
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
        if (!conversationId.equals("ep3_rryatt_tressk"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.ep3_rryatt_tressk.branchId");
        if (branchId == 2 && ep3_rryatt_tressk_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && ep3_rryatt_tressk_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && ep3_rryatt_tressk_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && ep3_rryatt_tressk_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && ep3_rryatt_tressk_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.ep3_rryatt_tressk.branchId");
        return SCRIPT_CONTINUE;
    }
}
