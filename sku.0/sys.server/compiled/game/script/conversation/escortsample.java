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
import script.library.money;
import script.library.space_quest;

public class escortsample extends script.base_script
{
    public escortsample()
    {
    }
    public static String c_stringFile = "conversation/escortsample";
    public boolean escortsample_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean escortsample_condition_completedQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasCompletedQuest(player, "escort", "rebel_escort_01") && space_quest.hasWonQuest(player, "escort", "rebel_escort_01"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean escortsample_condition_onQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasQuest(player, "escort", "rebel_escort_01") || space_quest.hasCompletedQuest(player, "escort", "rebel_escort_01"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean escortsample_condition_alreadyRewarded(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasReceivedReward(player, "escort", "rebel_escort_01");
    }
    public boolean escortsample_condition_abortedQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasFailedQuest(player, "escort", "rebel_escort_01") || space_quest.hasAbortedQuest(player, "escort", "rebel_escort_01"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public void escortsample_action__defaultAction(obj_id player, obj_id npc) throws InterruptedException
    {
    }
    public void escortsample_action_anim_greet(obj_id player, obj_id npc) throws InterruptedException
    {
        faceToBehavior(npc, player);
        doAnimationAction(npc, "greet");
    }
    public void escortsample_action_anim_converse(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "conversation_1");
    }
    public void escortsample_action_anim_checkwatch(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "check_wrist_device");
    }
    public void escortsample_action_giveQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "escort", "rebel_escort_01");
        doAnimationAction(npc, "standing_raise_fist");
    }
    public void escortsample_action_rewardPlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "celebrate");
        money.bankTo(money.ACCT_REBEL, player, 3000);
        space_quest.setQuestRewarded(player, "escort", "rebel_escort_01");
    }
    public void escortsample_action_anim_shakeheaddisgust(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "shake_head_disgust");
    }
    public void escortsample_action_anim_wave(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "wave1");
    }
    public void escortsample_action_anim_rubchin(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "rub_chin_thoughtful");
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.escortsample");
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
        detachScript(self, "npc.conversation.escortsample");
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (escortsample_condition_alreadyRewarded(player, self))
        {
            escortsample_action_anim_wave(player, self);
            string_id message = new string_id(c_stringFile, "s_8ff3ca72");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (escortsample_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_3b6feb84");
                }
                setObjVar(player, "conversation.escortsample.branchId", 1);
                npcStartConversation(player, self, "escortsample", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (escortsample_condition_abortedQuest(player, self))
        {
            escortsample_action_anim_shakeheaddisgust(player, self);
            string_id message = new string_id(c_stringFile, "s_440fcf1");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (escortsample_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (escortsample_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_d6b24fb1");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_aa630c8f");
                }
                setObjVar(player, "conversation.escortsample.branchId", 3);
                npcStartConversation(player, self, "escortsample", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (escortsample_condition_onQuest(player, self))
        {
            escortsample_action_anim_greet(player, self);
            string_id message = new string_id(c_stringFile, "s_20355b4c");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (escortsample_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (escortsample_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_8acc2fd5");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_fd6095f2");
                }
                setObjVar(player, "conversation.escortsample.branchId", 8);
                npcStartConversation(player, self, "escortsample", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (escortsample_condition__defaultCondition(player, self))
        {
            escortsample_action_anim_greet(player, self);
            string_id message = new string_id(c_stringFile, "s_fb6bb29c");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (escortsample_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_30b5d96");
                }
                setObjVar(player, "conversation.escortsample.branchId", 12);
                npcStartConversation(player, self, "escortsample", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("escortsample"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.escortsample.branchId");
        if (branchId == 1 && response.equals("s_3b6feb84"))
        {
            if (escortsample_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_f150abd");
                removeObjVar(player, "conversation.escortsample.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Hey escorter! You're a bad-ass escorter!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && response.equals("s_d6b24fb1"))
        {
            if (escortsample_condition__defaultCondition(player, self))
            {
                escortsample_action_anim_converse(player, self);
                string_id message = new string_id(c_stringFile, "s_f82b0fd5");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (escortsample_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (escortsample_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c8f5aa73");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_546aa4f5");
                    }
                    setObjVar(player, "conversation.escortsample.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.escortsample.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You FAILED! Quitter!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && response.equals("s_aa630c8f"))
        {
            if (escortsample_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_fa88eaf6");
                removeObjVar(player, "conversation.escortsample.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You FAILED! Quitter!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && response.equals("s_c8f5aa73"))
        {
            if (escortsample_condition__defaultCondition(player, self))
            {
                escortsample_action_anim_checkwatch(player, self);
                string_id message = new string_id(c_stringFile, "s_a7887ccd");
                removeObjVar(player, "conversation.escortsample.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Okay. Let's try again. I have a critical medical supply transport headed through the asteroid belt. I need someone to make sure it survives its trip. You interested?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && response.equals("s_546aa4f5"))
        {
            if (escortsample_condition__defaultCondition(player, self))
            {
                escortsample_action_giveQuest(player, self);
                string_id message = new string_id(c_stringFile, "s_197dcc5d");
                removeObjVar(player, "conversation.escortsample.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Okay. Let's try again. I have a critical medical supply transport headed through the asteroid belt. I need someone to make sure it survives its trip. You interested?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && response.equals("s_8acc2fd5"))
        {
            if (escortsample_condition_completedQuest(player, self))
            {
                escortsample_action_rewardPlayer(player, self);
                string_id message = new string_id(c_stringFile, "s_2cbca1ae");
                removeObjVar(player, "conversation.escortsample.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (escortsample_condition__defaultCondition(player, self))
            {
                escortsample_action_anim_shakeheaddisgust(player, self);
                string_id message = new string_id(c_stringFile, "s_7133d198");
                removeObjVar(player, "conversation.escortsample.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Hi there. So - did you escort our Rebel supply ship?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && response.equals("s_fd6095f2"))
        {
            if (escortsample_condition__defaultCondition(player, self))
            {
                escortsample_action_anim_converse(player, self);
                string_id message = new string_id(c_stringFile, "s_d1d526e");
                removeObjVar(player, "conversation.escortsample.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Hi there. So - did you escort our Rebel supply ship?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && response.equals("s_30b5d96"))
        {
            if (escortsample_condition__defaultCondition(player, self))
            {
                escortsample_action_anim_converse(player, self);
                string_id message = new string_id(c_stringFile, "s_64815da3");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (escortsample_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (escortsample_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c8f5aa73");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_546aa4f5");
                    }
                    setObjVar(player, "conversation.escortsample.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.escortsample.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Hello, pilot! The Alliance is in need of some talented space fighters. Are you interested?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && response.equals("s_c8f5aa73"))
        {
            if (escortsample_condition__defaultCondition(player, self))
            {
                escortsample_action_anim_checkwatch(player, self);
                string_id message = new string_id(c_stringFile, "s_a7887ccd");
                removeObjVar(player, "conversation.escortsample.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'The Imperials have squeezed off our major supply route through this system. I have a critical medical supply transport headed through the asteroid belt. I need someone to make sure it survives its trip. You interested?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && response.equals("s_546aa4f5"))
        {
            if (escortsample_condition__defaultCondition(player, self))
            {
                escortsample_action_giveQuest(player, self);
                string_id message = new string_id(c_stringFile, "s_197dcc5d");
                removeObjVar(player, "conversation.escortsample.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'The Imperials have squeezed off our major supply route through this system. I have a critical medical supply transport headed through the asteroid belt. I need someone to make sure it survives its trip. You interested?' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.escortsample.branchId");
        return SCRIPT_CONTINUE;
    }
}
