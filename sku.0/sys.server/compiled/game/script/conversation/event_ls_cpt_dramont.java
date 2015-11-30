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
import script.library.colors;
import script.library.money;
import script.library.utils;

public class event_ls_cpt_dramont extends script.base_script
{
    public event_ls_cpt_dramont()
    {
    }
    public static String c_stringFile = "conversation/event_ls_cpt_dramont";
    public boolean event_ls_cpt_dramont_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean event_ls_cpt_dramont_condition_hasSixCores(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id[] playerItems = getInventoryAndEquipment(player);
        for (int i = 0; i < playerItems.length; i++)
        {
            String itemTemplate = getTemplateName(playerItems[i]);
            if (itemTemplate.equals("object/tangible/loot/quest/lost_squadron_core_1.iff"))
            {
                utils.setScriptVar(player, "core_1", playerItems[i]);
            }
            if (itemTemplate.equals("object/tangible/loot/quest/lost_squadron_core_2.iff"))
            {
                utils.setScriptVar(player, "core_2", playerItems[i]);
            }
            if (itemTemplate.equals("object/tangible/loot/quest/lost_squadron_core_3.iff"))
            {
                utils.setScriptVar(player, "core_3", playerItems[i]);
            }
            if (itemTemplate.equals("object/tangible/loot/quest/lost_squadron_core_4.iff"))
            {
                utils.setScriptVar(player, "core_4", playerItems[i]);
            }
            if (itemTemplate.equals("object/tangible/loot/quest/lost_squadron_core_5.iff"))
            {
                utils.setScriptVar(player, "core_5", playerItems[i]);
            }
            if (itemTemplate.equals("object/tangible/loot/quest/lost_squadron_core_6.iff"))
            {
                utils.setScriptVar(player, "core_6", playerItems[i]);
            }
        }
        if (utils.hasScriptVar(player, "core_1") && utils.hasScriptVar(player, "core_2") && utils.hasScriptVar(player, "core_3") && utils.hasScriptVar(player, "core_4") && utils.hasScriptVar(player, "core_5") && utils.hasScriptVar(player, "core_6"))
        {
            utils.removeScriptVar(player, "core_1");
            utils.removeScriptVar(player, "core_2");
            utils.removeScriptVar(player, "core_3");
            utils.removeScriptVar(player, "core_4");
            utils.removeScriptVar(player, "core_5");
            utils.removeScriptVar(player, "core_6");
            return true;
        }
        else 
        {
            utils.removeScriptVar(player, "core_1");
            utils.removeScriptVar(player, "core_2");
            utils.removeScriptVar(player, "core_3");
            utils.removeScriptVar(player, "core_4");
            utils.removeScriptVar(player, "core_5");
            utils.removeScriptVar(player, "core_6");
            return false;
        }
    }
    public boolean event_ls_cpt_dramont_condition_outOfRewards(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id mom = getObjIdObjVar(npc, "mom");
        int numRewards = getIntObjVar(mom, "event.lost_squadron.num_rewards");
        if (numRewards >= 6)
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean event_ls_cpt_dramont_condition_hasMin1core(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id[] playerItems = getInventoryAndEquipment(player);
        for (int i = 0; i < playerItems.length; i++)
        {
            String itemTemplate = getTemplateName(playerItems[i]);
            if (itemTemplate.equals("object/tangible/loot/quest/lost_squadron_core_1.iff"))
            {
                return true;
            }
            if (itemTemplate.equals("object/tangible/loot/quest/lost_squadron_core_2.iff"))
            {
                return true;
            }
            if (itemTemplate.equals("object/tangible/loot/quest/lost_squadron_core_3.iff"))
            {
                return true;
            }
            if (itemTemplate.equals("object/tangible/loot/quest/lost_squadron_core_4.iff"))
            {
                return true;
            }
            if (itemTemplate.equals("object/tangible/loot/quest/lost_squadron_core_5.iff"))
            {
                return true;
            }
            if (itemTemplate.equals("object/tangible/loot/quest/lost_squadron_core_6.iff"))
            {
                return true;
            }
        }
        return false;
    }
    public void event_ls_cpt_dramont_action_giveBoobiePrize(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id[] playerItems = getInventoryAndEquipment(player);
        obj_id mom = getObjIdObjVar(npc, "mom");
        for (int i = 0; i < playerItems.length; i++)
        {
            String itemTemplate = getTemplateName(playerItems[i]);
            if (itemTemplate.equals("object/tangible/loot/quest/lost_squadron_core_1.iff"))
            {
                utils.setScriptVar(player, "core_1", playerItems[i]);
            }
            if (itemTemplate.equals("object/tangible/loot/quest/lost_squadron_core_2.iff"))
            {
                utils.setScriptVar(player, "core_2", playerItems[i]);
            }
            if (itemTemplate.equals("object/tangible/loot/quest/lost_squadron_core_3.iff"))
            {
                utils.setScriptVar(player, "core_3", playerItems[i]);
            }
            if (itemTemplate.equals("object/tangible/loot/quest/lost_squadron_core_4.iff"))
            {
                utils.setScriptVar(player, "core_4", playerItems[i]);
            }
            if (itemTemplate.equals("object/tangible/loot/quest/lost_squadron_core_5.iff"))
            {
                utils.setScriptVar(player, "core_5", playerItems[i]);
            }
            if (itemTemplate.equals("object/tangible/loot/quest/lost_squadron_core_6.iff"))
            {
                utils.setScriptVar(player, "core_6", playerItems[i]);
            }
        }
        if (utils.hasScriptVar(player, "core_1") && utils.hasScriptVar(player, "core_2") && utils.hasScriptVar(player, "core_3") && utils.hasScriptVar(player, "core_4") && utils.hasScriptVar(player, "core_5") && utils.hasScriptVar(player, "core_6"))
        {
            money.systemPayout(money.ACCT_GROUND_QUEST, player, 100000, "", null);
            obj_id core1 = utils.getObjIdScriptVar(player, "core_1");
            obj_id core2 = utils.getObjIdScriptVar(player, "core_2");
            obj_id core3 = utils.getObjIdScriptVar(player, "core_3");
            obj_id core4 = utils.getObjIdScriptVar(player, "core_4");
            obj_id core5 = utils.getObjIdScriptVar(player, "core_5");
            obj_id core6 = utils.getObjIdScriptVar(player, "core_6");
            if (isIdValid(core1) && exists(core1))
            {
                destroyObject(core1);
            }
            if (isIdValid(core2) && exists(core2))
            {
                destroyObject(core2);
            }
            if (isIdValid(core3) && exists(core3))
            {
                destroyObject(core3);
            }
            if (isIdValid(core4) && exists(core4))
            {
                destroyObject(core4);
            }
            if (isIdValid(core5) && exists(core5))
            {
                destroyObject(core5);
            }
            if (isIdValid(core6) && exists(core6))
            {
                destroyObject(core6);
            }
            dictionary params = new dictionary();
            params.put("reward", "money");
            params.put("player", player);
            messageTo(mom, "rewardGiven", params, 1, false);
        }
        else 
        {
            sendSystemMessage(player, new string_id("event/lost_squadron", "reward_verify_fail"));
        }
        utils.removeScriptVar(player, "core_1");
        utils.removeScriptVar(player, "core_2");
        utils.removeScriptVar(player, "core_3");
        utils.removeScriptVar(player, "core_4");
        utils.removeScriptVar(player, "core_5");
        utils.removeScriptVar(player, "core_6");
    }
    public void event_ls_cpt_dramont_action_giveXWingReward(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id[] playerItems = getInventoryAndEquipment(player);
        obj_id mom = getObjIdObjVar(npc, "mom");
        for (int i = 0; i < playerItems.length; i++)
        {
            String itemTemplate = getTemplateName(playerItems[i]);
            if (itemTemplate.equals("object/tangible/loot/quest/lost_squadron_core_1.iff"))
            {
                utils.setScriptVar(player, "core_1", playerItems[i]);
            }
            if (itemTemplate.equals("object/tangible/loot/quest/lost_squadron_core_2.iff"))
            {
                utils.setScriptVar(player, "core_2", playerItems[i]);
            }
            if (itemTemplate.equals("object/tangible/loot/quest/lost_squadron_core_3.iff"))
            {
                utils.setScriptVar(player, "core_3", playerItems[i]);
            }
            if (itemTemplate.equals("object/tangible/loot/quest/lost_squadron_core_4.iff"))
            {
                utils.setScriptVar(player, "core_4", playerItems[i]);
            }
            if (itemTemplate.equals("object/tangible/loot/quest/lost_squadron_core_5.iff"))
            {
                utils.setScriptVar(player, "core_5", playerItems[i]);
            }
            if (itemTemplate.equals("object/tangible/loot/quest/lost_squadron_core_6.iff"))
            {
                utils.setScriptVar(player, "core_6", playerItems[i]);
            }
        }
        if (utils.hasScriptVar(player, "core_1") && utils.hasScriptVar(player, "core_2") && utils.hasScriptVar(player, "core_3") && utils.hasScriptVar(player, "core_4") && utils.hasScriptVar(player, "core_5") && utils.hasScriptVar(player, "core_6"))
        {
            obj_id playerInventory = utils.getInventoryContainer(player);
            obj_id createdObject = createObject("object/tangible/furniture/city/xwing_event_reward.iff", playerInventory, "");
            if (isIdValid(createdObject) && exists(createdObject))
            {
                obj_id core1 = utils.getObjIdScriptVar(player, "core_1");
                obj_id core2 = utils.getObjIdScriptVar(player, "core_2");
                obj_id core3 = utils.getObjIdScriptVar(player, "core_3");
                obj_id core4 = utils.getObjIdScriptVar(player, "core_4");
                obj_id core5 = utils.getObjIdScriptVar(player, "core_5");
                obj_id core6 = utils.getObjIdScriptVar(player, "core_6");
                if (isIdValid(core1) && exists(core1))
                {
                    destroyObject(core1);
                }
                if (isIdValid(core2) && exists(core2))
                {
                    destroyObject(core2);
                }
                if (isIdValid(core3) && exists(core3))
                {
                    destroyObject(core3);
                }
                if (isIdValid(core4) && exists(core4))
                {
                    destroyObject(core4);
                }
                if (isIdValid(core5) && exists(core5))
                {
                    destroyObject(core5);
                }
                if (isIdValid(core6) && exists(core6))
                {
                    destroyObject(core6);
                }
                dictionary params = new dictionary();
                params.put("reward", "xWing");
                params.put("player", player);
                messageTo(mom, "rewardGiven", params, 1, false);
                showFlyText(player, new string_id("event/lost_squadron", "fly_txt_winner"), 1.5f, colors.LIGHTGOLDENRODYELLOW);
                playMusic(player, "sound/mus_fan_fair_fade.snd");
            }
            else 
            {
                sendSystemMessage(player, new string_id("event/lost_squadron", "sys_msg_full_inv"));
            }
        }
        else 
        {
            sendSystemMessage(player, new string_id("event/lost_squadron", "reward_verify_fail"));
        }
        utils.removeScriptVar(player, "core_1");
        utils.removeScriptVar(player, "core_2");
        utils.removeScriptVar(player, "core_3");
        utils.removeScriptVar(player, "core_4");
        utils.removeScriptVar(player, "core_5");
        utils.removeScriptVar(player, "core_6");
    }
    public void event_ls_cpt_dramont_action_giveTBomberReward(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id[] playerItems = getInventoryAndEquipment(player);
        obj_id mom = getObjIdObjVar(npc, "mom");
        for (int i = 0; i < playerItems.length; i++)
        {
            String itemTemplate = getTemplateName(playerItems[i]);
            if (itemTemplate.equals("object/tangible/loot/quest/lost_squadron_core_1.iff"))
            {
                utils.setScriptVar(player, "core_1", playerItems[i]);
            }
            if (itemTemplate.equals("object/tangible/loot/quest/lost_squadron_core_2.iff"))
            {
                utils.setScriptVar(player, "core_2", playerItems[i]);
            }
            if (itemTemplate.equals("object/tangible/loot/quest/lost_squadron_core_3.iff"))
            {
                utils.setScriptVar(player, "core_3", playerItems[i]);
            }
            if (itemTemplate.equals("object/tangible/loot/quest/lost_squadron_core_4.iff"))
            {
                utils.setScriptVar(player, "core_4", playerItems[i]);
            }
            if (itemTemplate.equals("object/tangible/loot/quest/lost_squadron_core_5.iff"))
            {
                utils.setScriptVar(player, "core_5", playerItems[i]);
            }
            if (itemTemplate.equals("object/tangible/loot/quest/lost_squadron_core_6.iff"))
            {
                utils.setScriptVar(player, "core_6", playerItems[i]);
            }
        }
        if (utils.hasScriptVar(player, "core_1") && utils.hasScriptVar(player, "core_2") && utils.hasScriptVar(player, "core_3") && utils.hasScriptVar(player, "core_4") && utils.hasScriptVar(player, "core_5") && utils.hasScriptVar(player, "core_6"))
        {
            obj_id playerInventory = utils.getInventoryContainer(player);
            obj_id createdObject = createObject("object/tangible/furniture/city/tie_bomber_event_reward.iff", playerInventory, "");
            if (isIdValid(createdObject) && exists(createdObject))
            {
                obj_id core1 = utils.getObjIdScriptVar(player, "core_1");
                obj_id core2 = utils.getObjIdScriptVar(player, "core_2");
                obj_id core3 = utils.getObjIdScriptVar(player, "core_3");
                obj_id core4 = utils.getObjIdScriptVar(player, "core_4");
                obj_id core5 = utils.getObjIdScriptVar(player, "core_5");
                obj_id core6 = utils.getObjIdScriptVar(player, "core_6");
                if (isIdValid(core1) && exists(core1))
                {
                    destroyObject(core1);
                }
                if (isIdValid(core2) && exists(core2))
                {
                    destroyObject(core2);
                }
                if (isIdValid(core3) && exists(core3))
                {
                    destroyObject(core3);
                }
                if (isIdValid(core4) && exists(core4))
                {
                    destroyObject(core4);
                }
                if (isIdValid(core5) && exists(core5))
                {
                    destroyObject(core5);
                }
                if (isIdValid(core6) && exists(core6))
                {
                    destroyObject(core6);
                }
                dictionary params = new dictionary();
                params.put("reward", "tieBomber");
                params.put("player", player);
                messageTo(mom, "rewardGiven", params, 1, false);
                showFlyText(player, new string_id("event/lost_squadron", "fly_txt_winner"), 1.5f, colors.LIGHTGOLDENRODYELLOW);
                playMusic(player, "sound/mus_fan_fair_fade.snd");
            }
            else 
            {
                sendSystemMessage(player, new string_id("event/lost_squadron", "sys_msg_full_inv"));
            }
        }
        else 
        {
            sendSystemMessage(player, new string_id("event/lost_squadron", "reward_verify_fail"));
        }
        utils.removeScriptVar(player, "core_1");
        utils.removeScriptVar(player, "core_2");
        utils.removeScriptVar(player, "core_3");
        utils.removeScriptVar(player, "core_4");
        utils.removeScriptVar(player, "core_5");
        utils.removeScriptVar(player, "core_6");
    }
    public int event_ls_cpt_dramont_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6"))
        {
            if (event_ls_cpt_dramont_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_8");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_ls_cpt_dramont_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.event_ls_cpt_dramont.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_ls_cpt_dramont.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_18"))
        {
            if (event_ls_cpt_dramont_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                string_id message = new string_id(c_stringFile, "s_20");
                utils.removeScriptVar(player, "conversation.event_ls_cpt_dramont.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_22"))
        {
            if (!event_ls_cpt_dramont_condition_hasSixCores(player, npc))
            {
                doAnimationAction(npc, "huh");
                string_id message = new string_id(c_stringFile, "s_24");
                utils.removeScriptVar(player, "conversation.event_ls_cpt_dramont.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (event_ls_cpt_dramont_condition_outOfRewards(player, npc))
            {
                doAnimationAction(npc, "shake_head_no");
                string_id message = new string_id(c_stringFile, "s_26");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_ls_cpt_dramont_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.event_ls_cpt_dramont.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_ls_cpt_dramont.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            if (event_ls_cpt_dramont_condition_hasSixCores(player, npc))
            {
                doAnimationAction(npc, "nod");
                string_id message = new string_id(c_stringFile, "s_32");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_ls_cpt_dramont_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (event_ls_cpt_dramont_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_34");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_46");
                    }
                    utils.setScriptVar(player, "conversation.event_ls_cpt_dramont.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_ls_cpt_dramont.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int event_ls_cpt_dramont_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_10"))
        {
            if (event_ls_cpt_dramont_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_12");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_ls_cpt_dramont_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.event_ls_cpt_dramont.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_ls_cpt_dramont.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int event_ls_cpt_dramont_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_14"))
        {
            if (event_ls_cpt_dramont_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_16");
                utils.removeScriptVar(player, "conversation.event_ls_cpt_dramont.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int event_ls_cpt_dramont_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_28"))
        {
            if (event_ls_cpt_dramont_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "feed_creature_medium");
                event_ls_cpt_dramont_action_giveBoobiePrize(player, npc);
                string_id message = new string_id(c_stringFile, "s_30");
                utils.removeScriptVar(player, "conversation.event_ls_cpt_dramont.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int event_ls_cpt_dramont_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_34"))
        {
            if (event_ls_cpt_dramont_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_36");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_ls_cpt_dramont_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (event_ls_cpt_dramont_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_42");
                    }
                    utils.setScriptVar(player, "conversation.event_ls_cpt_dramont.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_ls_cpt_dramont.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_46"))
        {
            if (event_ls_cpt_dramont_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_48");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_ls_cpt_dramont_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (event_ls_cpt_dramont_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_50");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_54");
                    }
                    utils.setScriptVar(player, "conversation.event_ls_cpt_dramont.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_ls_cpt_dramont.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int event_ls_cpt_dramont_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_38"))
        {
            if (event_ls_cpt_dramont_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute2");
                doAnimationAction(player, "salute2");
                event_ls_cpt_dramont_action_giveTBomberReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_40");
                utils.removeScriptVar(player, "conversation.event_ls_cpt_dramont.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_42"))
        {
            if (event_ls_cpt_dramont_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_44");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_ls_cpt_dramont_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (event_ls_cpt_dramont_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_34");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_46");
                    }
                    utils.setScriptVar(player, "conversation.event_ls_cpt_dramont.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_ls_cpt_dramont.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int event_ls_cpt_dramont_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_34"))
        {
            if (event_ls_cpt_dramont_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_36");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_ls_cpt_dramont_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (event_ls_cpt_dramont_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_42");
                    }
                    utils.setScriptVar(player, "conversation.event_ls_cpt_dramont.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_ls_cpt_dramont.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_46"))
        {
            if (event_ls_cpt_dramont_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_48");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_ls_cpt_dramont_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (event_ls_cpt_dramont_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_50");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_54");
                    }
                    utils.setScriptVar(player, "conversation.event_ls_cpt_dramont.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_ls_cpt_dramont.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int event_ls_cpt_dramont_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_50"))
        {
            if (event_ls_cpt_dramont_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute2");
                doAnimationAction(player, "salute2");
                event_ls_cpt_dramont_action_giveXWingReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_52");
                utils.removeScriptVar(player, "conversation.event_ls_cpt_dramont.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_54"))
        {
            if (event_ls_cpt_dramont_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_56");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_ls_cpt_dramont_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (event_ls_cpt_dramont_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_34");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_46");
                    }
                    utils.setScriptVar(player, "conversation.event_ls_cpt_dramont.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_ls_cpt_dramont.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int event_ls_cpt_dramont_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_34"))
        {
            if (event_ls_cpt_dramont_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_36");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_ls_cpt_dramont_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (event_ls_cpt_dramont_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_42");
                    }
                    utils.setScriptVar(player, "conversation.event_ls_cpt_dramont.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_ls_cpt_dramont.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_46"))
        {
            if (event_ls_cpt_dramont_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_48");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_ls_cpt_dramont_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (event_ls_cpt_dramont_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_50");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_54");
                    }
                    utils.setScriptVar(player, "conversation.event_ls_cpt_dramont.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_ls_cpt_dramont.branchId");
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
            detachScript(self, "conversation.event_ls_cpt_dramont");
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
        detachScript(self, "conversation.event_ls_cpt_dramont");
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
        if (event_ls_cpt_dramont_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_4");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (event_ls_cpt_dramont_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (event_ls_cpt_dramont_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (event_ls_cpt_dramont_condition_hasMin1core(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_6");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_18");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_22");
                }
                utils.setScriptVar(player, "conversation.event_ls_cpt_dramont.branchId", 1);
                npcStartConversation(player, npc, "event_ls_cpt_dramont", message, responses);
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
        if (!conversationId.equals("event_ls_cpt_dramont"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.event_ls_cpt_dramont.branchId");
        if (branchId == 1 && event_ls_cpt_dramont_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && event_ls_cpt_dramont_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && event_ls_cpt_dramont_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && event_ls_cpt_dramont_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && event_ls_cpt_dramont_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && event_ls_cpt_dramont_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && event_ls_cpt_dramont_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && event_ls_cpt_dramont_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && event_ls_cpt_dramont_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.event_ls_cpt_dramont.branchId");
        return SCRIPT_CONTINUE;
    }
}
