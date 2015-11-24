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
import script.library.static_item;
import script.library.utils;
import script.library.weapons;

public class event_bossk_informant extends script.base_script
{
    public event_bossk_informant()
    {
    }
    public static String c_stringFile = "conversation/event_bossk_informant";
    public boolean event_bossk_informant_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean event_bossk_informant_condition_readyQ4(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "quest/event_cantina_bossk_4", "talkToInformant"));
    }
    public boolean event_bossk_informant_condition_readyQ3P1(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "quest/event_cantina_bossk_3", "talkToCourier"));
    }
    public boolean event_bossk_informant_condition_readyQ3P2(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "quest/event_cantina_bossk_3", "talkToCourierAgain"));
    }
    public boolean event_bossk_informant_condition_completedAll(obj_id player, obj_id npc) throws InterruptedException
    {
        int quest1 = questGetQuestId("quest/event_cantina_bossk_1");
        int quest2 = questGetQuestId("quest/event_cantina_bossk_2");
        int quest3 = questGetQuestId("quest/event_cantina_bossk_3");
        int quest4 = questGetQuestId("quest/event_cantina_bossk_4");
        return (questIsQuestComplete(quest1, player) && questIsQuestComplete(quest2, player) && questIsQuestComplete(quest3, player) && questIsQuestComplete(quest4, player));
    }
    public boolean event_bossk_informant_condition_neverGotCarbine(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.hasCompletedQuest(player, "quest/event_cantina_bossk_3") && !hasObjVar(player, "event.bossk.gotCarbine"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean event_bossk_informant_condition_hasBrokenGloves(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id inv[] = utils.getAllItemsInBankAndInventory(player);
        for (int i = 0; i < inv.length; i++)
        {
            String thisItem = getTemplateName(inv[i]);
            if (thisItem.equals("object/tangible/wearables/gloves/gloves_bossk_reward.iff"))
            {
                if (hasObjVar(inv[i], "skillmod.bonus.ranged_accuracy"))
                {
                    return true;
                }
            }
        }
        return false;
    }
    public void event_bossk_informant_action_signalQ3P2(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "talkToCourierAgain");
        obj_id playerInv = utils.getInventoryContainer(player);
        obj_id reward = static_item.createNewItemFunction("weapon_event_hairtrigger_carbine_03_01", playerInv);
        setObjVar(player, "event.bossk.gotCarbine", 1);
    }
    public void event_bossk_informant_action_signalQ3P1(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "talkToCourier");
    }
    public void event_bossk_informant_action_signalQ4(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "talkToInformant");
        obj_id playerInv = utils.getInventoryContainer(player);
        obj_id reward = static_item.createNewItemFunction("item_event_microservo_gloves_03_01", playerInv);
    }
    public void event_bossk_informant_action_giveNewCarbine(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id playerInv = utils.getInventoryContainer(player);
        obj_id reward = static_item.createNewItemFunction("weapon_event_hairtrigger_carbine_03_01", playerInv);
        setObjVar(player, "event.bossk.gotCarbine", 1);
    }
    public void event_bossk_informant_action_fixGloves(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id inv[] = utils.getAllItemsInBankAndInventory(player);
        for (int i = 0; i < inv.length; i++)
        {
            String thisItem = getTemplateName(inv[i]);
            if (thisItem.equals("object/tangible/wearables/gloves/gloves_bossk_reward.iff"))
            {
                if (hasObjVar(inv[i], "skillmod.bonus.ranged_accuracy"))
                {
                    destroyObject(inv[i]);
                    obj_id playerInv = utils.getInventoryContainer(player);
                    obj_id reward = static_item.createNewItemFunction("item_event_microservo_gloves_03_01", playerInv);
                }
            }
        }
    }
    public int event_bossk_informant_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_486"))
        {
            if (event_bossk_informant_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_488");
                utils.removeScriptVar(player, "conversation.event_bossk_informant.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_26"))
        {
            if (event_bossk_informant_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "apologize");
                event_bossk_informant_action_giveNewCarbine(player, npc);
                string_id message = new string_id(c_stringFile, "s_27");
                utils.removeScriptVar(player, "conversation.event_bossk_informant.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_28"))
        {
            if (event_bossk_informant_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_29");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_bossk_informant_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (event_bossk_informant_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_30");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_32");
                    }
                    utils.setScriptVar(player, "conversation.event_bossk_informant.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_bossk_informant.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int event_bossk_informant_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_30"))
        {
            if (event_bossk_informant_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "apologize");
                event_bossk_informant_action_fixGloves(player, npc);
                string_id message = new string_id(c_stringFile, "s_31");
                utils.removeScriptVar(player, "conversation.event_bossk_informant.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_32"))
        {
            if (event_bossk_informant_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "apologize");
                event_bossk_informant_action_fixGloves(player, npc);
                string_id message = new string_id(c_stringFile, "s_33");
                utils.removeScriptVar(player, "conversation.event_bossk_informant.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int event_bossk_informant_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_492"))
        {
            if (event_bossk_informant_condition__defaultCondition(player, npc))
            {
                event_bossk_informant_action_signalQ4(player, npc);
                string_id message = new string_id(c_stringFile, "s_494");
                utils.removeScriptVar(player, "conversation.event_bossk_informant.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int event_bossk_informant_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_498"))
        {
            if (event_bossk_informant_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "gesticulate_wildly");
                string_id message = new string_id(c_stringFile, "s_500");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_bossk_informant_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_502");
                    }
                    utils.setScriptVar(player, "conversation.event_bossk_informant.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_bossk_informant.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_510"))
        {
            if (event_bossk_informant_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_512");
                utils.removeScriptVar(player, "conversation.event_bossk_informant.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int event_bossk_informant_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_502"))
        {
            if (event_bossk_informant_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_504");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_bossk_informant_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_506");
                    }
                    utils.setScriptVar(player, "conversation.event_bossk_informant.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_bossk_informant.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int event_bossk_informant_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_506"))
        {
            if (event_bossk_informant_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                event_bossk_informant_action_signalQ3P1(player, npc);
                string_id message = new string_id(c_stringFile, "s_508");
                utils.removeScriptVar(player, "conversation.event_bossk_informant.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int event_bossk_informant_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_516"))
        {
            if (event_bossk_informant_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_518");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_bossk_informant_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_520");
                    }
                    utils.setScriptVar(player, "conversation.event_bossk_informant.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_bossk_informant.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int event_bossk_informant_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_520"))
        {
            if (event_bossk_informant_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "adjust");
                event_bossk_informant_action_signalQ3P2(player, npc);
                string_id message = new string_id(c_stringFile, "s_522");
                utils.removeScriptVar(player, "conversation.event_bossk_informant.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int event_bossk_informant_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_24"))
        {
            if (event_bossk_informant_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "apologize");
                event_bossk_informant_action_giveNewCarbine(player, npc);
                string_id message = new string_id(c_stringFile, "s_25");
                utils.removeScriptVar(player, "conversation.event_bossk_informant.branchId");
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
            detachScript(self, "conversation.event_bossk_informant");
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
        detachScript(self, "conversation.event_bossk_informant");
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
        if (event_bossk_informant_condition_completedAll(player, npc))
        {
            doAnimationAction(npc, "beckon");
            string_id message = new string_id(c_stringFile, "s_484");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (event_bossk_informant_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (event_bossk_informant_condition_neverGotCarbine(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (event_bossk_informant_condition_hasBrokenGloves(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_486");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_26");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_28");
                }
                utils.setScriptVar(player, "conversation.event_bossk_informant.branchId", 1);
                npcStartConversation(player, npc, "event_bossk_informant", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (event_bossk_informant_condition_readyQ4(player, npc))
        {
            doAnimationAction(npc, "implore");
            string_id message = new string_id(c_stringFile, "s_490");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (event_bossk_informant_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_492");
                }
                utils.setScriptVar(player, "conversation.event_bossk_informant.branchId", 7);
                npcStartConversation(player, npc, "event_bossk_informant", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (event_bossk_informant_condition_readyQ3P1(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_496");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (event_bossk_informant_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (event_bossk_informant_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_510");
                }
                utils.setScriptVar(player, "conversation.event_bossk_informant.branchId", 9);
                npcStartConversation(player, npc, "event_bossk_informant", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (event_bossk_informant_condition_readyQ3P2(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_514");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (event_bossk_informant_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_516");
                }
                utils.setScriptVar(player, "conversation.event_bossk_informant.branchId", 14);
                npcStartConversation(player, npc, "event_bossk_informant", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (event_bossk_informant_condition_neverGotCarbine(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_524");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (event_bossk_informant_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.event_bossk_informant.branchId", 17);
                npcStartConversation(player, npc, "event_bossk_informant", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (event_bossk_informant_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_23");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("event_bossk_informant"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.event_bossk_informant.branchId");
        if (branchId == 1 && event_bossk_informant_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && event_bossk_informant_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && event_bossk_informant_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && event_bossk_informant_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && event_bossk_informant_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && event_bossk_informant_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && event_bossk_informant_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && event_bossk_informant_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && event_bossk_informant_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.event_bossk_informant.branchId");
        return SCRIPT_CONTINUE;
    }
}
