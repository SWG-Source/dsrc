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
import script.library.conversation;
import script.library.groundquests;
import script.library.holiday;
import script.library.utils;

public class loveday_matchmaker_droid extends script.base_script
{
    public loveday_matchmaker_droid()
    {
    }
    public static String c_stringFile = "conversation/loveday_matchmaker_droid";
    public boolean loveday_matchmaker_droid_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean loveday_matchmaker_droid_condition_returningMatchIncomplete(obj_id player, obj_id npc) throws InterruptedException
    {
        return holiday.hasLovedayRomanceSeekerCompanion(player) && groundquests.isTaskActive(player, "loveday_playing_cupid", "loveday_playing_cupid_01");
    }
    public boolean loveday_matchmaker_droid_condition_returningMatchComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        return holiday.hasLovedayRomanceSeekerCompanion(player) && groundquests.isTaskActive(player, "loveday_playing_cupid", "loveday_playing_cupid_02");
    }
    public boolean loveday_matchmaker_droid_condition_notYetReadyForAnother(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.hasCompletedQuest(player, "loveday_playing_cupid") && hasObjVar(player, "loveday.eligiblePlayingCupid"))
        {
            int eligibleForNextQuestAt = getIntObjVar(player, "loveday.eligiblePlayingCupid");
            if (getCalendarTime() < eligibleForNextQuestAt)
            {
                return true;
            }
        }
        return false;
    }
    public void loveday_matchmaker_droid_action_grantLovedayQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "loveday.eligiblePlayingCupid"))
        {
            removeObjVar(player, "loveday.eligiblePlayingCupid");
            if (utils.hasScriptVar(player, "loveday.todaysCompanion"))
            {
                utils.removeScriptVar(player, "loveday.todaysCompanion");
            }
            if (utils.hasScriptVar(player, "loveday.todaysCompanionTraits"))
            {
                utils.removeScriptVar(player, "loveday.todaysCompanionTraits");
            }
        }
        if (holiday.hasLovedayRomanceSeekerCompanion(player))
        {
            obj_id romanceSeekerCompanionControlDevice = holiday.getLovedayRomanceSeekerCompanionControlDevice(player);
            if (isIdValid(romanceSeekerCompanionControlDevice))
            {
                destroyObject(romanceSeekerCompanionControlDevice);
            }
        }
        groundquests.clearQuest(player, "loveday_playing_cupid");
        groundquests.grantQuest(player, "loveday_playing_cupid");
        holiday.grantLovedayRomanceSeekerCompanion(player);
        return;
    }
    public void loveday_matchmaker_droid_action_sendCompletionSignal(obj_id player, obj_id npc) throws InterruptedException
    {
        String companionType = "";
        obj_id romanceSeekerCompanionControlDevice = holiday.getLovedayRomanceSeekerCompanionControlDevice(player);
        if (isIdValid(romanceSeekerCompanionControlDevice))
        {
            companionType = getStringObjVar(romanceSeekerCompanionControlDevice, "pet.creatureName");
            destroyObject(romanceSeekerCompanionControlDevice);
        }
        groundquests.sendSignal(player, "loveday_playing_cupid_02");
        if (!hasCompletedCollectionSlot(player, "loveday_2010_playing_cupid"))
        {
            modifyCollectionSlotValue(player, "loveday_2010_playing_cupid", 1);
        }
        if (companionType != null && companionType.length() > 0)
        {
            holiday.checkForAndGrantLovedayMatchmakingSlot(player, companionType);
        }
        int now = getCalendarTime();
        int secondsUntil = secondsUntilNextDailyTime(4, 0, 0);
        int then = now + secondsUntil;
        setObjVar(player, "loveday.eligiblePlayingCupid", then);
    }
    public int loveday_matchmaker_droid_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_21"))
        {
            if (loveday_matchmaker_droid_condition__defaultCondition(player, npc))
            {
                loveday_matchmaker_droid_action_sendCompletionSignal(player, npc);
                string_id message = new string_id(c_stringFile, "s_22");
                utils.removeScriptVar(player, "conversation.loveday_matchmaker_droid.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int loveday_matchmaker_droid_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_14"))
        {
            if (loveday_matchmaker_droid_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "clap_rousing");
                string_id message = new string_id(c_stringFile, "s_16");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (loveday_matchmaker_droid_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (loveday_matchmaker_droid_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_23");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_25");
                    }
                    utils.setScriptVar(player, "conversation.loveday_matchmaker_droid.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.loveday_matchmaker_droid.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_32"))
        {
            if (loveday_matchmaker_droid_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "weeping");
                string_id message = new string_id(c_stringFile, "s_34");
                utils.removeScriptVar(player, "conversation.loveday_matchmaker_droid.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int loveday_matchmaker_droid_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_23"))
        {
            if (loveday_matchmaker_droid_condition__defaultCondition(player, npc))
            {
                loveday_matchmaker_droid_action_grantLovedayQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_24");
                utils.removeScriptVar(player, "conversation.loveday_matchmaker_droid.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_25"))
        {
            if (loveday_matchmaker_droid_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_26");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (loveday_matchmaker_droid_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.loveday_matchmaker_droid.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.loveday_matchmaker_droid.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int loveday_matchmaker_droid_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_28"))
        {
            if (loveday_matchmaker_droid_condition__defaultCondition(player, npc))
            {
                loveday_matchmaker_droid_action_grantLovedayQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_30");
                utils.removeScriptVar(player, "conversation.loveday_matchmaker_droid.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isTangible(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.loveday_matchmaker_droid");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_HOLIDAY_INTERESTING);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_HOLIDAY_INTERESTING);
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
        clearCondition(self, CONDITION_SPACE_INTERESTING);
        detachScript(self, "conversation.loveday_matchmaker_droid");
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
        if (loveday_matchmaker_droid_condition_returningMatchComplete(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_4");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (loveday_matchmaker_droid_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_21");
                }
                utils.setScriptVar(player, "conversation.loveday_matchmaker_droid.branchId", 1);
                npcStartConversation(player, npc, "loveday_matchmaker_droid", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (loveday_matchmaker_droid_condition_returningMatchIncomplete(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_8");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (loveday_matchmaker_droid_condition_notYetReadyForAnother(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_10");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (loveday_matchmaker_droid_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "beckon");
            string_id message = new string_id(c_stringFile, "s_12");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (loveday_matchmaker_droid_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (loveday_matchmaker_droid_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_14");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_32");
                }
                utils.setScriptVar(player, "conversation.loveday_matchmaker_droid.branchId", 5);
                npcStartConversation(player, npc, "loveday_matchmaker_droid", message, responses);
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
        if (!conversationId.equals("loveday_matchmaker_droid"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.loveday_matchmaker_droid.branchId");
        if (branchId == 1 && loveday_matchmaker_droid_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && loveday_matchmaker_droid_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && loveday_matchmaker_droid_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && loveday_matchmaker_droid_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.loveday_matchmaker_droid.branchId");
        return SCRIPT_CONTINUE;
    }
}
