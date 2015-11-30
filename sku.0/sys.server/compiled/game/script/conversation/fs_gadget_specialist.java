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
import script.library.money;
import script.library.quests;
import script.library.utils;

public class fs_gadget_specialist extends script.base_script
{
    public fs_gadget_specialist()
    {
    }
    public static String c_stringFile = "conversation/fs_gadget_specialist";
    public boolean fs_gadget_specialist_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean fs_gadget_specialist_condition_onQuestStep01(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasScript(player, "quest.force_sensitive.fs_crafting4_player"))
        {
            if (quests.isActive("fs_crafting4_quest_01", player))
            {
                return true;
            }
        }
        return false;
    }
    public boolean fs_gadget_specialist_condition_onQuestStep02(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasScript(player, "quest.force_sensitive.fs_crafting4_player"))
        {
            if (quests.isActive("fs_crafting4_quest_02", player))
            {
                return true;
            }
        }
        return false;
    }
    public boolean fs_gadget_specialist_condition_onQuestStep03(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasScript(player, "quest.force_sensitive.fs_crafting4_player"))
        {
            if (quests.isActive("fs_crafting4_quest_03", player))
            {
                return true;
            }
        }
        return false;
    }
    public boolean fs_gadget_specialist_condition_onQuestStep04(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasScript(player, "quest.force_sensitive.fs_crafting4_player"))
        {
            if (quests.isActive("fs_crafting4_quest_04", player))
            {
                return true;
            }
        }
        return false;
    }
    public boolean fs_gadget_specialist_condition_onQuestStep05(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasScript(player, "quest.force_sensitive.fs_crafting4_player"))
        {
            if (quests.isActive("fs_crafting4_quest_05", player))
            {
                return true;
            }
        }
        return false;
    }
    public boolean fs_gadget_specialist_condition_onQuestStep06(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasScript(player, "quest.force_sensitive.fs_crafting4_player"))
        {
            if (quests.isActive("fs_crafting4_quest_06", player))
            {
                return true;
            }
        }
        return false;
    }
    public boolean fs_gadget_specialist_condition_noInventorySpace(obj_id player, obj_id npc) throws InterruptedException
    {
        boolean hasNoInvRoom = false;
        obj_id playerInv = utils.getInventoryContainer(player);
        if (isIdValid(playerInv))
        {
            int free_space = getVolumeFree(playerInv);
            if (free_space < 1)
            {
                hasNoInvRoom = true;
            }
        }
        return hasNoInvRoom;
    }
    public boolean fs_gadget_specialist_condition_notEnoughCredits(obj_id player, obj_id npc) throws InterruptedException
    {
        return (!money.hasFunds(player, money.MT_TOTAL, 530));
    }
    public boolean fs_gadget_specialist_condition_returningForKit(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasObjVar(player, "fs_crafting4.gadget.returnForKit");
    }
    public boolean fs_gadget_specialist_condition_onGoGoGadgetQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "corellia_coronet_capitol_problems_go_go_gadget", "go_go_gadget_1");
    }
    public boolean fs_gadget_specialist_condition_goGoGadgetFavorDone(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "corellia_coronet_capitol_problems_go_go_gadget", "go_go_gadget_5");
    }
    public void fs_gadget_specialist_action_giveTrackingDeviceKit(obj_id player, obj_id npc) throws InterruptedException
    {
        String kitTemplate = "object/tangible/loot/collectible/kits/fs_tracking_device_kit.iff";
        int kitCost = 530;
        obj_id playerInv = getObjectInSlot(player, "inventory");
        if (isIdValid(playerInv))
        {
            obj_id kit = createObject(kitTemplate, playerInv, "");
            if (isIdValid(kit))
            {
                utils.moneyOutMetric(player, "FS_QUESTS", kitCost);
                money.requestPayment(player, npc, kitCost, "pass_fail", null, true);
                quests.complete("fs_crafting4_quest_01", player, true);
                if (hasObjVar(player, "fs_crafting4.gadget.returnForKit"))
                {
                    removeObjVar(player, "fs_crafting4.gadget.returnForKit");
                }
                String custLogMsg = "FS Phase 4 Crafting Quest: Player %TU has been given the tracking device loot kit.";
                CustomerServiceLog("FS_Phase4_Crafting", custLogMsg, player);
            }
        }
        return;
    }
    public void fs_gadget_specialist_action_setReturningForKit(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "fs_crafting4.gadget.returnForKit", 1);
        return;
    }
    public void fs_gadget_specialist_action_goGoGadgetFavor(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "go_go_gadget_1");
    }
    public void fs_gadget_specialist_action_goGoGadgetEnd(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "go_go_gadget_5");
    }
    public int fs_gadget_specialist_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_a8749106"))
        {
            if (fs_gadget_specialist_condition_notEnoughCredits(player, npc))
            {
                doAnimationAction(npc, "shake_head_no");
                fs_gadget_specialist_action_setReturningForKit(player, npc);
                string_id message = new string_id(c_stringFile, "s_b518079a");
                utils.removeScriptVar(player, "conversation.fs_gadget_specialist.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (fs_gadget_specialist_condition_noInventorySpace(player, npc))
            {
                doAnimationAction(npc, "shake_head_no");
                fs_gadget_specialist_action_setReturningForKit(player, npc);
                string_id message = new string_id(c_stringFile, "s_faaeb65f");
                utils.removeScriptVar(player, "conversation.fs_gadget_specialist.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (fs_gadget_specialist_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                fs_gadget_specialist_action_giveTrackingDeviceKit(player, npc);
                string_id message = new string_id(c_stringFile, "s_bdcd33ef");
                utils.removeScriptVar(player, "conversation.fs_gadget_specialist.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_8fe6a678"))
        {
            if (fs_gadget_specialist_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_accusingly");
                string_id message = new string_id(c_stringFile, "s_6d9d310");
                utils.removeScriptVar(player, "conversation.fs_gadget_specialist.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int fs_gadget_specialist_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_50"))
        {
            if (fs_gadget_specialist_condition__defaultCondition(player, npc))
            {
                fs_gadget_specialist_action_goGoGadgetEnd(player, npc);
                string_id message = new string_id(c_stringFile, "s_51");
                utils.removeScriptVar(player, "conversation.fs_gadget_specialist.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_40"))
        {
            if (fs_gadget_specialist_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_41");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (fs_gadget_specialist_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_42");
                    }
                    utils.setScriptVar(player, "conversation.fs_gadget_specialist.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.fs_gadget_specialist.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_79fe19ed"))
        {
            if (fs_gadget_specialist_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                string_id message = new string_id(c_stringFile, "s_b49f7181");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (fs_gadget_specialist_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (fs_gadget_specialist_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_85bd3ae2");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_55082d0b");
                    }
                    utils.setScriptVar(player, "conversation.fs_gadget_specialist.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.fs_gadget_specialist.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_c7baf80c"))
        {
            if (fs_gadget_specialist_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_4bcdc402");
                utils.removeScriptVar(player, "conversation.fs_gadget_specialist.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_bab2829b"))
        {
            if (fs_gadget_specialist_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_65f76975");
                utils.removeScriptVar(player, "conversation.fs_gadget_specialist.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_7f396fce"))
        {
            if (fs_gadget_specialist_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_f9732f00");
                utils.removeScriptVar(player, "conversation.fs_gadget_specialist.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_b867b2e4"))
        {
            if (fs_gadget_specialist_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_70253663");
                utils.removeScriptVar(player, "conversation.fs_gadget_specialist.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_fe739220"))
        {
            if (fs_gadget_specialist_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_d8a8c77e");
                utils.removeScriptVar(player, "conversation.fs_gadget_specialist.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_5542b04b"))
        {
            if (fs_gadget_specialist_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1283cb1c");
                utils.removeScriptVar(player, "conversation.fs_gadget_specialist.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int fs_gadget_specialist_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_42"))
        {
            if (fs_gadget_specialist_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_43");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (fs_gadget_specialist_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_44");
                    }
                    utils.setScriptVar(player, "conversation.fs_gadget_specialist.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.fs_gadget_specialist.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int fs_gadget_specialist_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_44"))
        {
            if (fs_gadget_specialist_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_45");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (fs_gadget_specialist_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (fs_gadget_specialist_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_46");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_47");
                    }
                    utils.setScriptVar(player, "conversation.fs_gadget_specialist.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.fs_gadget_specialist.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int fs_gadget_specialist_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_46"))
        {
            if (fs_gadget_specialist_condition__defaultCondition(player, npc))
            {
                fs_gadget_specialist_action_goGoGadgetFavor(player, npc);
                string_id message = new string_id(c_stringFile, "s_48");
                utils.removeScriptVar(player, "conversation.fs_gadget_specialist.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_47"))
        {
            if (fs_gadget_specialist_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_49");
                utils.removeScriptVar(player, "conversation.fs_gadget_specialist.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int fs_gadget_specialist_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_85bd3ae2"))
        {
            if (fs_gadget_specialist_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_4389624e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (fs_gadget_specialist_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a1dd9b9e");
                    }
                    utils.setScriptVar(player, "conversation.fs_gadget_specialist.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.fs_gadget_specialist.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_55082d0b"))
        {
            if (fs_gadget_specialist_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3d8991fc");
                utils.removeScriptVar(player, "conversation.fs_gadget_specialist.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int fs_gadget_specialist_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_a1dd9b9e"))
        {
            if (fs_gadget_specialist_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_shoulders");
                string_id message = new string_id(c_stringFile, "s_be68030a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (fs_gadget_specialist_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (fs_gadget_specialist_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_be1b8c5a");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4d7434d2");
                    }
                    utils.setScriptVar(player, "conversation.fs_gadget_specialist.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.fs_gadget_specialist.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int fs_gadget_specialist_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_be1b8c5a"))
        {
            if (fs_gadget_specialist_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                string_id message = new string_id(c_stringFile, "s_e5c058a8");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (fs_gadget_specialist_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (fs_gadget_specialist_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fff0f4f2");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_46bdeee4");
                    }
                    utils.setScriptVar(player, "conversation.fs_gadget_specialist.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.fs_gadget_specialist.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_4d7434d2"))
        {
            if (fs_gadget_specialist_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_hands");
                string_id message = new string_id(c_stringFile, "s_533919fc");
                utils.removeScriptVar(player, "conversation.fs_gadget_specialist.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int fs_gadget_specialist_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_fff0f4f2"))
        {
            if (fs_gadget_specialist_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_multiple");
                string_id message = new string_id(c_stringFile, "s_e79d337");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (fs_gadget_specialist_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b9f237f");
                    }
                    utils.setScriptVar(player, "conversation.fs_gadget_specialist.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.fs_gadget_specialist.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_46bdeee4"))
        {
            if (fs_gadget_specialist_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wave_on_dismissing");
                string_id message = new string_id(c_stringFile, "s_9d19b512");
                utils.removeScriptVar(player, "conversation.fs_gadget_specialist.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int fs_gadget_specialist_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_b9f237f"))
        {
            if (fs_gadget_specialist_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "slow_down");
                string_id message = new string_id(c_stringFile, "s_bdedc5e2");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (fs_gadget_specialist_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (fs_gadget_specialist_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a8749106");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8fe6a678");
                    }
                    utils.setScriptVar(player, "conversation.fs_gadget_specialist.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.fs_gadget_specialist.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int fs_gadget_specialist_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_a8749106"))
        {
            if (fs_gadget_specialist_condition_notEnoughCredits(player, npc))
            {
                doAnimationAction(npc, "shake_head_no");
                fs_gadget_specialist_action_setReturningForKit(player, npc);
                string_id message = new string_id(c_stringFile, "s_b518079a");
                utils.removeScriptVar(player, "conversation.fs_gadget_specialist.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (fs_gadget_specialist_condition_noInventorySpace(player, npc))
            {
                doAnimationAction(npc, "shake_head_no");
                fs_gadget_specialist_action_setReturningForKit(player, npc);
                string_id message = new string_id(c_stringFile, "s_faaeb65f");
                utils.removeScriptVar(player, "conversation.fs_gadget_specialist.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (fs_gadget_specialist_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                fs_gadget_specialist_action_giveTrackingDeviceKit(player, npc);
                string_id message = new string_id(c_stringFile, "s_bdcd33ef");
                utils.removeScriptVar(player, "conversation.fs_gadget_specialist.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_8fe6a678"))
        {
            if (fs_gadget_specialist_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_accusingly");
                string_id message = new string_id(c_stringFile, "s_6d9d310");
                utils.removeScriptVar(player, "conversation.fs_gadget_specialist.branchId");
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
            detachScript(self, "conversation.fs_gadget_specialist");
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
        detachScript(self, "conversation.fs_gadget_specialist");
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
        if (fs_gadget_specialist_condition_returningForKit(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_7045a2d7");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (fs_gadget_specialist_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (fs_gadget_specialist_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_a8749106");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_8fe6a678");
                }
                utils.setScriptVar(player, "conversation.fs_gadget_specialist.branchId", 14);
                npcStartConversation(player, npc, "fs_gadget_specialist", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (fs_gadget_specialist_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "check_wrist_device");
            string_id message = new string_id(c_stringFile, "s_5a562246");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (fs_gadget_specialist_condition_goGoGadgetFavorDone(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (fs_gadget_specialist_condition_onGoGoGadgetQuest(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (fs_gadget_specialist_condition_onQuestStep01(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (fs_gadget_specialist_condition_onQuestStep02(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse3 = true;
            }
            boolean hasResponse4 = false;
            if (fs_gadget_specialist_condition_onQuestStep03(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse4 = true;
            }
            boolean hasResponse5 = false;
            if (fs_gadget_specialist_condition_onQuestStep04(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse5 = true;
            }
            boolean hasResponse6 = false;
            if (fs_gadget_specialist_condition_onQuestStep05(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse6 = true;
            }
            boolean hasResponse7 = false;
            if (fs_gadget_specialist_condition_onQuestStep06(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse7 = true;
            }
            boolean hasResponse8 = false;
            if (fs_gadget_specialist_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse8 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_50");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_40");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_79fe19ed");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_c7baf80c");
                }
                if (hasResponse4)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_bab2829b");
                }
                if (hasResponse5)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_7f396fce");
                }
                if (hasResponse6)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_b867b2e4");
                }
                if (hasResponse7)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_fe739220");
                }
                if (hasResponse8)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_5542b04b");
                }
                utils.setScriptVar(player, "conversation.fs_gadget_specialist.branchId", 2);
                npcStartConversation(player, npc, "fs_gadget_specialist", message, responses);
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
        if (!conversationId.equals("fs_gadget_specialist"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.fs_gadget_specialist.branchId");
        if (branchId == 1 && fs_gadget_specialist_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && fs_gadget_specialist_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && fs_gadget_specialist_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && fs_gadget_specialist_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && fs_gadget_specialist_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && fs_gadget_specialist_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && fs_gadget_specialist_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && fs_gadget_specialist_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && fs_gadget_specialist_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && fs_gadget_specialist_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && fs_gadget_specialist_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.fs_gadget_specialist.branchId");
        return SCRIPT_CONTINUE;
    }
}
