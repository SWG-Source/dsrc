package script.conversation;

import script.*;
import script.library.ai_lib;
import script.library.chat;
import script.library.groundquests;
import script.library.utils;

import java.util.Vector;

public class outbreak_defense_kyraa_naa extends script.base_script
{
    public outbreak_defense_kyraa_naa()
    {
    }
    public static String c_stringFile = "conversation/outbreak_defense_kyraa_naa";
    public boolean outbreak_defense_kyraa_naa_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean outbreak_defense_kyraa_naa_condition_hasCompletedDefenseQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedQuest(player, "camp_gamma_defense_01") || groundquests.hasCompletedQuest(player, "camp_gamma_defense_02") || groundquests.hasCompletedQuest(player, "camp_gamma_defense_03") || groundquests.hasCompletedQuest(player, "camp_gamma_defense_04") && outbreak_defense_kyraa_naa_condition_hasAvailableGuardPosts(player, npc));
    }
    public boolean outbreak_defense_kyraa_naa_condition_isWaitingForRewardSignal(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "camp_gamma_defense_01", "collectRewardGamma") || groundquests.isTaskActive(player, "camp_gamma_defense_02", "collectRewardGamma") || groundquests.isTaskActive(player, "camp_gamma_defense_03", "collectRewardGamma") || groundquests.isTaskActive(player, "camp_gamma_defense_04", "collectRewardGamma");
    }
    public boolean outbreak_defense_kyraa_naa_condition_hasNeverSpoken(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasCompletedCollectionSlot(player, "exp_icon_deathtroopers_camp_gamma") && !groundquests.isQuestActiveOrComplete(player, "camp_gamma_defense_01") && !groundquests.isQuestActiveOrComplete(player, "camp_gamma_defense_02") && !groundquests.isQuestActiveOrComplete(player, "camp_gamma_defense_03") && !groundquests.isQuestActiveOrComplete(player, "camp_gamma_defense_04") && outbreak_defense_kyraa_naa_condition_hasAvailableGuardPosts(player, npc);
    }
    public boolean outbreak_defense_kyraa_naa_condition_isGodPlayerTester(obj_id player, obj_id npc) throws InterruptedException
    {
        return isGod(player);
    }
    public boolean outbreak_defense_kyraa_naa_condition_levelTooHigh(obj_id player, obj_id npc) throws InterruptedException
    {
        return getLevel(player) >= 60;
    }
    public boolean outbreak_defense_kyraa_naa_condition_levelTooLow(obj_id player, obj_id npc) throws InterruptedException
    {
        return getLevel(player) < 40;
    }
    public boolean outbreak_defense_kyraa_naa_condition_noAntiVirus(obj_id player, obj_id npc) throws InterruptedException
    {
        return (!groundquests.hasCompletedQuest(player, "outbreak_quest_01_imperial") && !groundquests.hasCompletedQuest(player, "outbreak_quest_01_rebel") && !groundquests.hasCompletedQuest(player, "outbreak_quest_01_neutral"));
    }
    public boolean outbreak_defense_kyraa_naa_condition_hasDefenseQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isQuestActive(player, "camp_gamma_defense_01") || groundquests.isQuestActive(player, "camp_gamma_defense_02") || groundquests.isQuestActive(player, "camp_gamma_defense_03") || groundquests.isQuestActive(player, "camp_gamma_defense_04"));
    }
    public boolean outbreak_defense_kyraa_naa_condition_hasAvailableGuardPosts(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!utils.hasScriptVar(npc, "guardPostList"))
        {
            CustomerServiceLog("outbreak_themepark", "camp_defense.hasAvailableGuardPosts() the npc, " + npc + " has no guardPostList script var.");
            return false;
        }
        obj_id[] guardPostList = utils.getObjIdArrayScriptVar(npc, "guardPostList");
        if (guardPostList == null || guardPostList.length <= 0)
        {
            CustomerServiceLog("outbreak_themepark", "camp_defense.hasAvailableGuardPosts() the npc, " + npc + " has an invalid guardPostList script var.");
            return false;
        }
        for (obj_id obj_id : guardPostList) {
            if (!isValidId(obj_id) || !exists(obj_id)) {
                continue;
            }
            if (!utils.hasScriptVar(obj_id, "occupied")) {
                return true;
            }
        }
        return false;
    }
    public void outbreak_defense_kyraa_naa_action_giveQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.hasCompletedQuest(player, "camp_gamma_defense_01"))
        {
            groundquests.clearQuest(player, "camp_gamma_defense_01");
        }
        if (groundquests.hasCompletedQuest(player, "camp_gamma_defense_02"))
        {
            groundquests.clearQuest(player, "camp_gamma_defense_02");
        }
        if (groundquests.hasCompletedQuest(player, "camp_gamma_defense_03"))
        {
            groundquests.clearQuest(player, "camp_gamma_defense_03");
        }
        if (groundquests.hasCompletedQuest(player, "camp_gamma_defense_04"))
        {
            groundquests.clearQuest(player, "camp_gamma_defense_04");
        }
        if (!utils.hasScriptVar(npc, "guardPostList"))
        {
            CustomerServiceLog("outbreak_themepark", "camp_defense.giveQuest() the npc, " + npc + " has no guardPostList script var.");
        }
        else 
        {
            obj_id[] guardPostList = utils.getObjIdArrayScriptVar(npc, "guardPostList");
            if (guardPostList == null || guardPostList.length <= 0)
            {
                CustomerServiceLog("outbreak_themepark", "camp_defense.giveQuest() the npc, " + npc + " has an invalid guardPostList script var.");
            }
            else 
            {
                for (obj_id obj_id : guardPostList) {
                    if (!isValidId(obj_id) || !exists(obj_id)) {
                        continue;
                    }
                    if (!utils.hasScriptVar(obj_id, "occupied")) {
                        String questName = getStringObjVar(obj_id, "questObject");
                        if (questName == null || questName.length() <= 0) {
                            CustomerServiceLog("outbreak_themepark", "camp_defense.giveQuest() the npc, " + npc + " has an invalid guardPostList script var.");
                        } else {
                            CustomerServiceLog("outbreak_themepark", "camp_defense.giveQuest() the npc, " + npc + " is granting the player: " + player + " has an invalid guardPostList script var.");
                            groundquests.grantQuest(player, questName);
                        }
                    }
                }
            }
        }
    }
    public void outbreak_defense_kyraa_naa_action_sendQuestSignal(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "hasCompletedGamma");
        utils.removeScriptVar(player, "givenMoreDefenseTime");
        if (!hasCompletedCollectionSlot(player, "defend_camp_gamma"))
        {
            modifyCollectionSlotValue(player, "defend_camp_gamma", 1);
        }
    }
    public void outbreak_defense_kyraa_naa_action_action0001(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "camp_gamma_defense_01");
    }
    public void outbreak_defense_kyraa_naa_action_action0002(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "camp_gamma_defense_02");
    }
    public void outbreak_defense_kyraa_naa_action_action0003(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "camp_gamma_defense_03");
    }
    public void outbreak_defense_kyraa_naa_action_action0004(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "camp_gamma_defense_04");
    }
    public int outbreak_defense_kyraa_naa_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_45"))
        {
            if (outbreak_defense_kyraa_naa_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_46");
                utils.removeScriptVar(player, "conversation.outbreak_defense_kyraa_naa.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int outbreak_defense_kyraa_naa_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_22"))
        {
            if (outbreak_defense_kyraa_naa_condition__defaultCondition(player, npc))
            {
                outbreak_defense_kyraa_naa_action_action0001(player, npc);
                string_id message = new string_id(c_stringFile, "s_28");
                utils.removeScriptVar(player, "conversation.outbreak_defense_kyraa_naa.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_23"))
        {
            if (outbreak_defense_kyraa_naa_condition__defaultCondition(player, npc))
            {
                outbreak_defense_kyraa_naa_action_action0002(player, npc);
                string_id message = new string_id(c_stringFile, "s_29");
                utils.removeScriptVar(player, "conversation.outbreak_defense_kyraa_naa.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_26"))
        {
            if (outbreak_defense_kyraa_naa_condition__defaultCondition(player, npc))
            {
                outbreak_defense_kyraa_naa_action_action0003(player, npc);
                string_id message = new string_id(c_stringFile, "s_30");
                utils.removeScriptVar(player, "conversation.outbreak_defense_kyraa_naa.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_27"))
        {
            if (outbreak_defense_kyraa_naa_condition__defaultCondition(player, npc))
            {
                outbreak_defense_kyraa_naa_action_action0004(player, npc);
                string_id message = new string_id(c_stringFile, "s_31");
                utils.removeScriptVar(player, "conversation.outbreak_defense_kyraa_naa.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int outbreak_defense_kyraa_naa_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_62"))
        {
            if (outbreak_defense_kyraa_naa_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_63");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (outbreak_defense_kyraa_naa_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_51");
                    }
                    utils.setScriptVar(player, "conversation.outbreak_defense_kyraa_naa.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.outbreak_defense_kyraa_naa.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int outbreak_defense_kyraa_naa_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_51"))
        {
            if (outbreak_defense_kyraa_naa_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_52");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (outbreak_defense_kyraa_naa_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_54");
                    }
                    utils.setScriptVar(player, "conversation.outbreak_defense_kyraa_naa.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.outbreak_defense_kyraa_naa.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int outbreak_defense_kyraa_naa_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_54"))
        {
            if (outbreak_defense_kyraa_naa_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "laugh_cackle");
                outbreak_defense_kyraa_naa_action_giveQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_57");
                utils.removeScriptVar(player, "conversation.outbreak_defense_kyraa_naa.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int outbreak_defense_kyraa_naa_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_40"))
        {
            if (outbreak_defense_kyraa_naa_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_42");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (outbreak_defense_kyraa_naa_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_53");
                    }
                    utils.setScriptVar(player, "conversation.outbreak_defense_kyraa_naa.branchId", 16);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.outbreak_defense_kyraa_naa.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int outbreak_defense_kyraa_naa_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_53"))
        {
            if (outbreak_defense_kyraa_naa_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_58");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (outbreak_defense_kyraa_naa_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_60");
                    }
                    utils.setScriptVar(player, "conversation.outbreak_defense_kyraa_naa.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.outbreak_defense_kyraa_naa.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int outbreak_defense_kyraa_naa_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_60"))
        {
            if (outbreak_defense_kyraa_naa_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_right");
                outbreak_defense_kyraa_naa_action_giveQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_77");
                utils.removeScriptVar(player, "conversation.outbreak_defense_kyraa_naa.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        messageTo(self, "findGuardPosts", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        messageTo(self, "findGuardPosts", null, 5, false);
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
    public int findGuardPosts(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] guardPostList = getAllObjectsWithObjVar(getLocation(self), 200, "questObject");
        if (guardPostList == null || guardPostList.length <= 0)
        {
            CustomerServiceLog("outbreak_themepark", "camp_defense.findGuardPosts() the npc, " + self + " failed to find any valid guard post objects within 100 of " + getLocation(self));
            return SCRIPT_CONTINUE;
        }
        Vector guardPostsFound = null;
        for (obj_id obj_id : guardPostList) {
            String spawnerQuestNameMatch = getStringObjVar(obj_id, "questObject");
            if (spawnerQuestNameMatch == null || spawnerQuestNameMatch.length() <= 0) {
                CustomerServiceLog("outbreak_themepark", "camp_defense.findGuardPosts() the npc, " + self + " found a guard post object that did not have a quest string. Guard Post: " + obj_id);
                continue;
            }
            if (!spawnerQuestNameMatch.startsWith("camp_gamma_defense")) {
                continue;
            }
            CustomerServiceLog("outbreak_themepark", "camp_defense.findGuardPosts() the npc, " + self + " found a guard post object. Adding the object to the list. Guard Post: " + obj_id);
            utils.addElement(guardPostsFound, obj_id);
        }
        CustomerServiceLog("outbreak_themepark", "camp_defense.findGuardPosts() the npc, " + self + " loop for all the available guard posts has finished. Length of list: " + guardPostList.length);
        if (guardPostList.length <= 0)
        {
            CustomerServiceLog("outbreak_themepark", "camp_defense.findGuardPosts() the npc, " + self + " had a problem finding guard posts.");
            return SCRIPT_CONTINUE;
        }
        if (guardPostList.length != 4)
        {
            CustomerServiceLog("outbreak_themepark", "camp_defense.findGuardPosts() the npc, " + self + " found less or greater than 4 guard posts!");
        }
        utils.setScriptVar(self, "guardPostList", guardPostList);
        CustomerServiceLog("outbreak_themepark", "camp_defense.findGuardPosts() the npc, " + self + " has a list of guard posts as scriptvar.");
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
        if (outbreak_defense_kyraa_naa_condition_noAntiVirus(player, npc))
        {
            doAnimationAction(npc, "wave_on_dismissing");
            string_id message = new string_id(c_stringFile, "s_69");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (outbreak_defense_kyraa_naa_condition_levelTooLow(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_44");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (outbreak_defense_kyraa_naa_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_45");
                }
                utils.setScriptVar(player, "conversation.outbreak_defense_kyraa_naa.branchId", 2);
                npcStartConversation(player, npc, "outbreak_defense_kyraa_naa", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (outbreak_defense_kyraa_naa_condition_isWaitingForRewardSignal(player, npc))
        {
            outbreak_defense_kyraa_naa_action_sendQuestSignal(player, npc);
            string_id message = new string_id(c_stringFile, "s_64");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (outbreak_defense_kyraa_naa_condition_isGodPlayerTester(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_21");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (outbreak_defense_kyraa_naa_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (outbreak_defense_kyraa_naa_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (outbreak_defense_kyraa_naa_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (outbreak_defense_kyraa_naa_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse3 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_22");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_23");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_26");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_27");
                }
                utils.setScriptVar(player, "conversation.outbreak_defense_kyraa_naa.branchId", 5);
                npcStartConversation(player, npc, "outbreak_defense_kyraa_naa", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (outbreak_defense_kyraa_naa_condition_hasCompletedDefenseQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_55");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (outbreak_defense_kyraa_naa_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.outbreak_defense_kyraa_naa.branchId", 10);
                npcStartConversation(player, npc, "outbreak_defense_kyraa_naa", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (outbreak_defense_kyraa_naa_condition_hasDefenseQuest(player, npc))
        {
            doAnimationAction(npc, "backhand_threaten");
            string_id message = new string_id(c_stringFile, "s_50");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (outbreak_defense_kyraa_naa_condition_hasNeverSpoken(player, npc))
        {
            doAnimationAction(npc, "beckon");
            string_id message = new string_id(c_stringFile, "s_38");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (outbreak_defense_kyraa_naa_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.outbreak_defense_kyraa_naa.branchId", 15);
                npcStartConversation(player, npc, "outbreak_defense_kyraa_naa", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (!outbreak_defense_kyraa_naa_condition_hasAvailableGuardPosts(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_48");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (outbreak_defense_kyraa_naa_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_90");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("outbreak_defense_kyraa_naa"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.outbreak_defense_kyraa_naa.branchId");
        if (branchId == 2 && outbreak_defense_kyraa_naa_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && outbreak_defense_kyraa_naa_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && outbreak_defense_kyraa_naa_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && outbreak_defense_kyraa_naa_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && outbreak_defense_kyraa_naa_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && outbreak_defense_kyraa_naa_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && outbreak_defense_kyraa_naa_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && outbreak_defense_kyraa_naa_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.outbreak_defense_kyraa_naa.branchId");
        return SCRIPT_CONTINUE;
    }
}
