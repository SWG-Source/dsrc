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
import script.library.space_quest;
import script.library.static_item;
import script.library.utils;

public class npe_medic2 extends script.base_script
{
    public npe_medic2()
    {
    }
    public static String c_stringFile = "conversation/npe_medic2";
    public boolean npe_medic2_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean npe_medic2_condition_needsNewPack(obj_id player, obj_id npc) throws InterruptedException
    {
        boolean needsItem = false;
        boolean hasItem = false;
        obj_id[] playerStuff = getInventoryAndEquipment(player);
        for (int i = 0; i < playerStuff.length; i++)
        {
            String templateName = static_item.getStaticItemName(playerStuff[i]);
            if (templateName != null)
            {
                if (templateName.equals("item_bactapack_01_01"))
                {
                    hasItem = true;
                }
            }
        }
        if ((groundquests.isTaskActive(player, "npe_medic2", "npe_medic2_heal") || groundquests.isTaskActive(player, "npe_medic2", 0)) && hasItem == false)
        {
            needsItem = true;
        }
        return needsItem;
    }
    public boolean npe_medic2_condition_onEntQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "npe_prof_entertainer2", "play");
    }
    public boolean npe_medic2_condition_hasPilot(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasObjVar(player, "npe.hasMetSerissu");
    }
    public boolean npe_medic2_condition_wantsMedic(obj_id player, obj_id npc) throws InterruptedException
    {
        return (npe_medic2_condition_isMedTemplate(player, npc) && !groundquests.isQuestActiveOrComplete(player, "npe_medic2"));
    }
    public boolean npe_medic2_condition_hasPack(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id[] playerStuff = getInventoryAndEquipment(player);
        for (int i = 0; i < playerStuff.length; i++)
        {
            String templateName = static_item.getStaticItemName(playerStuff[i]);
            if (templateName != null)
            {
                if (templateName.equals("item_bactapack_01_01"))
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean npe_medic2_condition_busy(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "npe_medic", "npe_medic_heal_1") || groundquests.isTaskActive(player, "npe_medic", "npe_medic_return") || groundquests.isTaskActive(player, "npe_medic2", 0) || groundquests.isTaskActive(player, "npe_medic2", "npe_medic2_heal") || groundquests.isTaskActive(player, "npe_medic2", "talk_technician") || groundquests.isTaskActive(player, "npe_medic2", "ingredients") || groundquests.isTaskActive(player, "npe_medic2", 9) || groundquests.isTaskActive(player, "npe_medic2", "return_technician"));
    }
    public boolean npe_medic2_condition_finishedGather(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "npe_medic2", "return2");
    }
    public boolean npe_medic2_condition_finishedLastTask(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "npe_medic2", "final_return");
    }
    public boolean npe_medic2_condition_fromMedic(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isQuestActive(player, "npe_pointer_med_template") && groundquests.hasCompletedQuest(player, "npe_medic"));
    }
    public boolean npe_medic2_condition_finishedMedTask(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "npe_medic2", "return1");
    }
    public boolean npe_medic2_condition_isMedTemplate(obj_id player, obj_id npc) throws InterruptedException
    {
        String pTemplate = getSkillTemplate(player);
        if (pTemplate.indexOf("medic") > -1)
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public void npe_medic2_action_giveMedQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "npe_medic2");
        groundquests.sendSignal(player, "found_medic");
        groundquests.sendSignal(player, "npe_medic_done");
    }
    public void npe_medic2_action_giveReward(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "npe.finishedTemplate", 1);
        groundquests.sendSignal(player, "npe_return_billingsly3");
        groundquests.grantQuest(player, "npe_job_pointer_han");
    }
    public void npe_medic2_action_giveAjaPointer(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "npe_pointer_medic");
        groundquests.sendSignal(player, "found_medic");
    }
    public void npe_medic2_action_giveFirstSignal(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "npe_return_billingsly1");
    }
    public void npe_medic2_action_giveNewPack(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id bactaPack = static_item.createNewItemFunction("item_bactapack_01_01", player);
        obj_id[] items = new obj_id[1];
        items[0] = bactaPack;
        showLootBox(player, items);
        newbieTutorialSetToolbarElement(player, 5, bactaPack);
        dictionary dic = new dictionary();
        dic.put("player", player);
        dic.put("location", 5);
        dic.put("time", 5.0f);
        messageTo(player, "makeUiElementAnimate", dic, 0.25f, true);
    }
    public void npe_medic2_action_facePlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
    }
    public void npe_medic2_action_giveComm(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "npe_medic_comm", false);
        npe_medic2_action_facePlayer(player, npc);
    }
    public void npe_medic2_action_GiveMedQuestAndPack(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.requestGrantQuest(player, "npe_medic2");
        groundquests.sendSignal(player, "found_medic");
        obj_id bactaPack = static_item.createNewItemFunction("item_bactapack_01_01", player);
        newbieTutorialSetToolbarElement(player, 5, bactaPack);
        dictionary dic = new dictionary();
        dic.put("player", player);
        dic.put("location", 5);
        dic.put("time", 5.0f);
        messageTo(player, "makeUiElementAnimate", dic, 0.25f, true);
        groundquests.sendSignal(player, "npe_medic_done");
    }
    public void npe_medic2_action_give2ndSignal(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "npe_return_billingsly2");
    }
    public int npe_medic2_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_67"))
        {
            if (npe_medic2_condition__defaultCondition(player, npc))
            {
                npe_medic2_action_giveNewPack(player, npc);
                string_id message = new string_id(c_stringFile, "s_71");
                utils.removeScriptVar(player, "conversation.npe_medic2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_medic2_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_48"))
        {
            if (npe_medic2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_49");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_medic2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_50");
                    }
                    utils.setScriptVar(player, "conversation.npe_medic2.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_medic2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_medic2_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_50"))
        {
            if (npe_medic2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_51");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_medic2_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.npe_medic2.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_medic2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_medic2_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_52"))
        {
            if (npe_medic2_condition__defaultCondition(player, npc))
            {
                npe_medic2_action_giveReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_53");
                utils.removeScriptVar(player, "conversation.npe_medic2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_medic2_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_40"))
        {
            doAnimationAction(player, "feed_creature_medium");
            if (npe_medic2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_41");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_medic2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_43");
                    }
                    utils.setScriptVar(player, "conversation.npe_medic2.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_medic2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_medic2_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_43"))
        {
            if (npe_medic2_condition__defaultCondition(player, npc))
            {
                npe_medic2_action_give2ndSignal(player, npc);
                string_id message = new string_id(c_stringFile, "s_45");
                utils.removeScriptVar(player, "conversation.npe_medic2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_medic2_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_46"))
        {
            if (npe_medic2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_47");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_medic2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_83");
                    }
                    utils.setScriptVar(player, "conversation.npe_medic2.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_medic2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_medic2_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_83"))
        {
            if (npe_medic2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_84");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_medic2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_33");
                    }
                    utils.setScriptVar(player, "conversation.npe_medic2.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_medic2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_medic2_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_33"))
        {
            if (npe_medic2_condition__defaultCondition(player, npc))
            {
                npe_medic2_action_giveFirstSignal(player, npc);
                string_id message = new string_id(c_stringFile, "s_36");
                utils.removeScriptVar(player, "conversation.npe_medic2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_medic2_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_68"))
        {
            if (npe_medic2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_72");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_medic2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_77");
                    }
                    utils.setScriptVar(player, "conversation.npe_medic2.branchId", 18);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_medic2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_medic2_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_77"))
        {
            if (npe_medic2_condition_hasPack(player, npc))
            {
                npe_medic2_action_giveMedQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_81");
                utils.removeScriptVar(player, "conversation.npe_medic2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (!npe_medic2_condition_hasPack(player, npc))
            {
                npe_medic2_action_GiveMedQuestAndPack(player, npc);
                string_id message = new string_id(c_stringFile, "s_90");
                utils.removeScriptVar(player, "conversation.npe_medic2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_medic2_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_57"))
        {
            if (npe_medic2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_59");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_medic2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_62");
                    }
                    utils.setScriptVar(player, "conversation.npe_medic2.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_medic2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_medic2_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_62"))
        {
            if (npe_medic2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_64");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_medic2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_66");
                    }
                    utils.setScriptVar(player, "conversation.npe_medic2.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_medic2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_medic2_handleBranch23(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_66"))
        {
            if (npe_medic2_condition__defaultCondition(player, npc))
            {
                npe_medic2_action_giveAjaPointer(player, npc);
                string_id message = new string_id(c_stringFile, "s_70");
                utils.removeScriptVar(player, "conversation.npe_medic2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_medic2_handleBranch26(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_80"))
        {
            if (npe_medic2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_85");
                utils.removeScriptVar(player, "conversation.npe_medic2.branchId");
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
            detachScript(self, "conversation.npe_medic2");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setName(self, "Billingsly (Medic)");
        ai_lib.setCustomIdleAnimation(self, "calm");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setName(self, "Billingsly (Medic)");
        ai_lib.setCustomIdleAnimation(self, "calm");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
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
        detachScript(self, "conversation.npe_medic2");
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
        if (npe_medic2_condition_onEntQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_79");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (!npe_medic2_condition_isMedTemplate(player, npc))
        {
            npe_medic2_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_69");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (npe_medic2_condition_needsNewPack(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_65");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (npe_medic2_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.npe_medic2.branchId", 3);
                npcStartConversation(player, npc, "npe_medic2", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (npe_medic2_condition_busy(player, npc))
        {
            npe_medic2_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_42");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (npe_medic2_condition_finishedLastTask(player, npc))
        {
            npe_medic2_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_39");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (npe_medic2_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_48");
                }
                utils.setScriptVar(player, "conversation.npe_medic2.branchId", 6);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "npe_medic2", null, pp, responses);
            }
            else 
            {
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                chat.chat(npc, player, null, null, pp);
            }
            return SCRIPT_CONTINUE;
        }
        if (npe_medic2_condition_finishedGather(player, npc))
        {
            npe_medic2_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_38");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (npe_medic2_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_40");
                }
                utils.setScriptVar(player, "conversation.npe_medic2.branchId", 10);
                npcStartConversation(player, npc, "npe_medic2", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (npe_medic2_condition_finishedMedTask(player, npc))
        {
            npe_medic2_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_44");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (npe_medic2_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_46");
                }
                utils.setScriptVar(player, "conversation.npe_medic2.branchId", 13);
                npcStartConversation(player, npc, "npe_medic2", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (npe_medic2_condition_fromMedic(player, npc))
        {
            npe_medic2_action_giveComm(player, npc);
            string_id message = new string_id(c_stringFile, "s_60");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (npe_medic2_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_68");
                }
                utils.setScriptVar(player, "conversation.npe_medic2.branchId", 17);
                npcStartConversation(player, npc, "npe_medic2", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (npe_medic2_condition_wantsMedic(player, npc))
        {
            npe_medic2_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_37");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (npe_medic2_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_57");
                }
                utils.setScriptVar(player, "conversation.npe_medic2.branchId", 21);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "npe_medic2", null, pp, responses);
            }
            else 
            {
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                chat.chat(npc, player, null, null, pp);
            }
            return SCRIPT_CONTINUE;
        }
        if (npe_medic2_condition_isMedTemplate(player, npc))
        {
            npe_medic2_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_76");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (npe_medic2_condition__defaultCondition(player, npc))
        {
            npe_medic2_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_78");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (npe_medic2_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_80");
                }
                utils.setScriptVar(player, "conversation.npe_medic2.branchId", 26);
                npcStartConversation(player, npc, "npe_medic2", message, responses);
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
        if (!conversationId.equals("npe_medic2"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.npe_medic2.branchId");
        if (branchId == 3 && npe_medic2_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && npe_medic2_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && npe_medic2_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && npe_medic2_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && npe_medic2_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && npe_medic2_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && npe_medic2_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && npe_medic2_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && npe_medic2_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && npe_medic2_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && npe_medic2_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && npe_medic2_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && npe_medic2_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && npe_medic2_handleBranch23(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && npe_medic2_handleBranch26(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.npe_medic2.branchId");
        return SCRIPT_CONTINUE;
    }
}
