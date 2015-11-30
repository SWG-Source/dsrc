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

public class maneater_ulon extends script.base_script
{
    public maneater_ulon()
    {
    }
    public static String c_stringFile = "conversation/maneater_ulon";
    public boolean maneater_ulon_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean maneater_ulon_condition_isOnTask(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "som_maneater");
    }
    public boolean maneater_ulon_condition_failedEncounter(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "som_maneater", "mustafar_maneater_four");
    }
    public boolean maneater_ulon_condition_wonTask(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "som_maneater", "mustafar_maneater_five");
    }
    public boolean maneater_ulon_condition_hasWonMission(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "som_maneater");
    }
    public void maneater_ulon_action_startMission(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "som_maneater");
    }
    public void maneater_ulon_action_clearMission(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.clearQuest(player, "som_maneater");
    }
    public void maneater_ulon_action_rewardTime(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "mustafar_maneater_reward");
    }
    public int maneater_ulon_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_14"))
        {
            if (maneater_ulon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "thank");
                string_id message = new string_id(c_stringFile, "s_16");
                utils.removeScriptVar(player, "conversation.maneater_ulon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_15"))
        {
            doAnimationAction(player, "slump_head");
            maneater_ulon_action_clearMission(player, npc);
            if (maneater_ulon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "pound_fist_palm");
                maneater_ulon_action_startMission(player, npc);
                string_id message = new string_id(c_stringFile, "s_17");
                utils.removeScriptVar(player, "conversation.maneater_ulon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int maneater_ulon_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_20"))
        {
            if (maneater_ulon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                string_id message = new string_id(c_stringFile, "s_22");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (maneater_ulon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (maneater_ulon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_24");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_28");
                    }
                    utils.setScriptVar(player, "conversation.maneater_ulon.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.maneater_ulon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int maneater_ulon_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_24"))
        {
            doAnimationAction(player, "pose_proudly");
            if (maneater_ulon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "celebrate");
                maneater_ulon_action_startMission(player, npc);
                string_id message = new string_id(c_stringFile, "s_26");
                utils.removeScriptVar(player, "conversation.maneater_ulon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_28"))
        {
            if (maneater_ulon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "thank");
                string_id message = new string_id(c_stringFile, "s_30");
                utils.removeScriptVar(player, "conversation.maneater_ulon.branchId");
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
            detachScript(self, "conversation.maneater_ulon");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_INTERESTING);
        setName(self, "Chief Ulon Glost");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_INTERESTING);
        setName(self, "Chief Ulon Glost");
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
        detachScript(self, "conversation.maneater_ulon");
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
        if (maneater_ulon_condition_hasWonMission(player, npc))
        {
            doAnimationAction(npc, "wave1");
            string_id message = new string_id(c_stringFile, "s_4");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (maneater_ulon_condition_wonTask(player, npc))
        {
            doAnimationAction(npc, "celebrate1");
            doAnimationAction(player, "expect_tip");
            maneater_ulon_action_rewardTime(player, npc);
            string_id message = new string_id(c_stringFile, "s_6");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (maneater_ulon_condition_isOnTask(player, npc))
        {
            doAnimationAction(npc, "explain");
            string_id message = new string_id(c_stringFile, "s_8");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (maneater_ulon_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (maneater_ulon_condition_failedEncounter(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_15");
                }
                utils.setScriptVar(player, "conversation.maneater_ulon.branchId", 3);
                npcStartConversation(player, npc, "maneater_ulon", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (maneater_ulon_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "check_wrist_device");
            string_id message = new string_id(c_stringFile, "s_18");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (maneater_ulon_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_20");
                }
                utils.setScriptVar(player, "conversation.maneater_ulon.branchId", 6);
                npcStartConversation(player, npc, "maneater_ulon", message, responses);
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
        if (!conversationId.equals("maneater_ulon"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.maneater_ulon.branchId");
        if (branchId == 3 && maneater_ulon_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && maneater_ulon_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && maneater_ulon_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.maneater_ulon.branchId");
        return SCRIPT_CONTINUE;
    }
}
