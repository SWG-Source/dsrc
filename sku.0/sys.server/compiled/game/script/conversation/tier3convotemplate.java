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
import script.library.space_quest;

public class tier3convotemplate extends script.base_script
{
    public tier3convotemplate()
    {
    }
    public static String c_stringFile = "conversation/tier3convotemplate";
    public boolean tier3convotemplate_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean tier3convotemplate_condition_isCorrectFaction(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean tier3convotemplate_condition_isReadyForTier3(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean tier3convotemplate_condition_isOnQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasQuest(player));
    }
    public boolean tier3convotemplate_condition_failedQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        return false;
    }
    public boolean tier3convotemplate_condition_failedQuestTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        return false;
    }
    public boolean tier3convotemplate_condition_failedQuestThree(obj_id player, obj_id npc) throws InterruptedException
    {
        return false;
    }
    public boolean tier3convotemplate_condition_FailedQuestFour(obj_id player, obj_id npc) throws InterruptedException
    {
        return false;
    }
    public boolean tier3convotemplate_condition_FailedQuestFive(obj_id player, obj_id npc) throws InterruptedException
    {
        return false;
    }
    public boolean tier3convotemplate_condition_FailedQuestSix(obj_id player, obj_id npc) throws InterruptedException
    {
        return false;
    }
    public boolean tier3convotemplate_condition_collectingQuestOneReward(obj_id player, obj_id npc) throws InterruptedException
    {
        return false;
    }
    public boolean tier3convotemplate_condition_collectingQuestTwoReward(obj_id player, obj_id npc) throws InterruptedException
    {
        return false;
    }
    public boolean tier3convotemplate_condition_collectingQuestThreeReward(obj_id player, obj_id npc) throws InterruptedException
    {
        return false;
    }
    public boolean tier3convotemplate_condition_collectingQuestFourReward(obj_id player, obj_id npc) throws InterruptedException
    {
        return false;
    }
    public boolean tier3convotemplate_condition_collectingQuestFiveReward(obj_id player, obj_id npc) throws InterruptedException
    {
        return false;
    }
    public boolean tier3convotemplate_condition_collectingQuestSixReward(obj_id player, obj_id npc) throws InterruptedException
    {
        return false;
    }
    public boolean tier3convotemplate_condition_hasCompletedQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean tier3convotemplate_condition_hasCompletedQuestTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean tier3convotemplate_condition_hasCompletedQuestThree(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean tier3convotemplate_condition_hasCompletedQuestFour(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean tier3convotemplate_condition_hasCompletedQuestFive(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean tier3convotemplate_condition_hasCompletedQuestSix(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public void tier3convotemplate_action_grantQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
    }
    public void tier3convotemplate_action_grantQuestTwo(obj_id player, obj_id npc) throws InterruptedException
    {
    }
    public void tier3convotemplate_action_grantQuestThree(obj_id player, obj_id npc) throws InterruptedException
    {
    }
    public void tier3convotemplate_action_grantQuestFour(obj_id player, obj_id npc) throws InterruptedException
    {
    }
    public void tier3convotemplate_action_grantQuestFive(obj_id player, obj_id npc) throws InterruptedException
    {
    }
    public void tier3convotemplate_action_grantQuestSix(obj_id player, obj_id npc) throws InterruptedException
    {
    }
    public void tier3convotemplate_action_rewardForQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
    }
    public void tier3convotemplate_action_rewardForQuesTwo(obj_id player, obj_id npc) throws InterruptedException
    {
    }
    public void tier3convotemplate_action_rewardForQuestThree(obj_id player, obj_id npc) throws InterruptedException
    {
    }
    public void tier3convotemplate_action_rewardForQuestFour(obj_id player, obj_id npc) throws InterruptedException
    {
    }
    public void tier3convotemplate_action_rewardForQuestFive(obj_id player, obj_id npc) throws InterruptedException
    {
    }
    public void tier3convotemplate_action_rewardForQuestSix(obj_id player, obj_id npc) throws InterruptedException
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.tier3convotemplate");
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
        detachScript(self, "npc.conversation.tier3convotemplate");
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
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (!tier3convotemplate_condition_isCorrectFaction(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_abaa1510");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (!tier3convotemplate_condition_isReadyForTier3(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_9c278b35");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (tier3convotemplate_condition_isOnQuest(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_6793cafb");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (tier3convotemplate_condition_failedQuestOne(player, self))
        {
            tier3convotemplate_action_grantQuestOne(player, self);
            string_id message = new string_id(c_stringFile, "s_35c72c81");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (tier3convotemplate_condition_failedQuestTwo(player, self))
        {
            tier3convotemplate_action_grantQuestTwo(player, self);
            string_id message = new string_id(c_stringFile, "s_755c8b16");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (tier3convotemplate_condition_failedQuestThree(player, self))
        {
            tier3convotemplate_action_grantQuestThree(player, self);
            string_id message = new string_id(c_stringFile, "s_f07b0dfd");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (tier3convotemplate_condition_FailedQuestFour(player, self))
        {
            tier3convotemplate_action_grantQuestFour(player, self);
            string_id message = new string_id(c_stringFile, "s_e16e4699");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (tier3convotemplate_condition_FailedQuestFive(player, self))
        {
            tier3convotemplate_action_grantQuestFive(player, self);
            string_id message = new string_id(c_stringFile, "s_22632bc1");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (tier3convotemplate_condition_FailedQuestSix(player, self))
        {
            tier3convotemplate_action_grantQuestSix(player, self);
            string_id message = new string_id(c_stringFile, "s_3b915d92");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (tier3convotemplate_condition_collectingQuestOneReward(player, self))
        {
            tier3convotemplate_action_rewardForQuestOne(player, self);
            string_id message = new string_id(c_stringFile, "s_52a877fa");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (tier3convotemplate_condition_collectingQuestTwoReward(player, self))
        {
            tier3convotemplate_action_grantQuestTwo(player, self);
            string_id message = new string_id(c_stringFile, "s_85f759f2");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (tier3convotemplate_condition_collectingQuestThreeReward(player, self))
        {
            tier3convotemplate_action_rewardForQuestThree(player, self);
            string_id message = new string_id(c_stringFile, "s_1350d8f1");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (tier3convotemplate_condition_collectingQuestFourReward(player, self))
        {
            tier3convotemplate_action_rewardForQuestFour(player, self);
            string_id message = new string_id(c_stringFile, "s_7a2b3961");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (tier3convotemplate_condition_collectingQuestFiveReward(player, self))
        {
            tier3convotemplate_action_rewardForQuestFive(player, self);
            string_id message = new string_id(c_stringFile, "s_dfd0f16b");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (tier3convotemplate_condition_collectingQuestSixReward(player, self))
        {
            tier3convotemplate_action_rewardForQuestSix(player, self);
            string_id message = new string_id(c_stringFile, "s_8f22e2f2");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (!tier3convotemplate_condition_hasCompletedQuestOne(player, self))
        {
            tier3convotemplate_action_grantQuestOne(player, self);
            string_id message = new string_id(c_stringFile, "s_77108b2b");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (!tier3convotemplate_condition_hasCompletedQuestTwo(player, self))
        {
            tier3convotemplate_action_grantQuestTwo(player, self);
            string_id message = new string_id(c_stringFile, "s_fd94cc2c");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (!tier3convotemplate_condition_hasCompletedQuestThree(player, self))
        {
            tier3convotemplate_action_grantQuestThree(player, self);
            string_id message = new string_id(c_stringFile, "s_63ef993a");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (!tier3convotemplate_condition_hasCompletedQuestFour(player, self))
        {
            tier3convotemplate_action_rewardForQuestFour(player, self);
            string_id message = new string_id(c_stringFile, "s_3e1e51ce");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (!tier3convotemplate_condition_hasCompletedQuestFive(player, self))
        {
            tier3convotemplate_action_grantQuestFive(player, self);
            string_id message = new string_id(c_stringFile, "s_61f5696d");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (!tier3convotemplate_condition_hasCompletedQuestSix(player, self))
        {
            tier3convotemplate_action_grantQuestSix(player, self);
            string_id message = new string_id(c_stringFile, "s_6a7031f3");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (tier3convotemplate_condition__defaultCondition(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_fc6ea208");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("tier3convotemplate"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.tier3convotemplate.branchId");
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.tier3convotemplate.branchId");
        return SCRIPT_CONTINUE;
    }
}
