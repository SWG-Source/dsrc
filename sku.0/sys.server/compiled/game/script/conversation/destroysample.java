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

public class destroysample extends script.base_script
{
    public destroysample()
    {
    }
    public static String c_stringFile = "conversation/destroysample";
    public boolean destroysample_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean destroysample_condition_completedQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasCompletedQuest(player, "destroy", "sampleDestroy") && space_quest.hasWonQuest(player, "destroy", "sampleDestroy"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean destroysample_condition_onQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasQuest(player, "destroy", "sampleDestroy") || space_quest.hasCompletedQuest(player, "destroy", "sampleDestroy"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean destroysample_condition_alreadyRewarded(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasReceivedReward(player, "destroy", "sampleDestroy");
    }
    public void destroysample_action__defaultAction(obj_id player, obj_id npc) throws InterruptedException
    {
    }
    public void destroysample_action_anim_greet(obj_id player, obj_id npc) throws InterruptedException
    {
        faceToBehavior(npc, player);
        doAnimationAction(npc, "greet");
    }
    public void destroysample_action_anim_converse(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "conversation_1");
    }
    public void destroysample_action_anim_checkwatch(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "check_wrist_device");
    }
    public void destroysample_action_giveQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "destroy", "sampleDestroy");
        doAnimationAction(npc, "standing_raise_fist");
    }
    public void destroysample_action_rewardPlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "celebrate");
        money.bankTo(money.ACCT_REBEL, player, 3000);
        space_quest.setQuestRewarded(player, "destroy", "sampleDestroy");
    }
    public void destroysample_action_anim_shakeheaddisgust(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "shake_head_disgust");
    }
    public void destroysample_action_anim_wave(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "wave1");
    }
    public void destroysample_action_anim_rubchin(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "rub_chin_thoughtful");
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.destroysample");
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
        detachScript(self, "npc.conversation.destroysample");
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (destroysample_condition_alreadyRewarded(player, self))
        {
            destroysample_action_anim_wave(player, self);
            string_id message = new string_id(c_stringFile, "s_bc9b15b3");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (destroysample_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_e0f3deb6");
                }
                setObjVar(player, "conversation.destroysample.branchId", 1);
                npcStartConversation(player, self, "destroysample", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (destroysample_condition_onQuest(player, self))
        {
            destroysample_action_anim_greet(player, self);
            string_id message = new string_id(c_stringFile, "s_cdb71c15");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (destroysample_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (destroysample_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_5786ebef");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_fd6095f2");
                }
                setObjVar(player, "conversation.destroysample.branchId", 3);
                npcStartConversation(player, self, "destroysample", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (destroysample_condition__defaultCondition(player, self))
        {
            destroysample_action_anim_greet(player, self);
            string_id message = new string_id(c_stringFile, "s_f324f7d5");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (destroysample_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_5c09875a");
                }
                setObjVar(player, "conversation.destroysample.branchId", 7);
                npcStartConversation(player, self, "destroysample", message, responses);
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
        if (!conversationId.equals("destroysample"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.destroysample.branchId");
        if (branchId == 1 && response.equals("s_e0f3deb6"))
        {
            if (destroysample_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_f150abd");
                removeObjVar(player, "conversation.destroysample.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Nice to see you, Trent.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && response.equals("s_5786ebef"))
        {
            if (destroysample_condition_completedQuest(player, self))
            {
                destroysample_action_rewardPlayer(player, self);
                string_id message = new string_id(c_stringFile, "s_32900dd8");
                removeObjVar(player, "conversation.destroysample.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (destroysample_condition__defaultCondition(player, self))
            {
                destroysample_action_anim_shakeheaddisgust(player, self);
                string_id message = new string_id(c_stringFile, "s_360ed899");
                removeObjVar(player, "conversation.destroysample.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Hey there Trent.  Do you have the sensor logs confirming your kills?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && response.equals("s_fd6095f2"))
        {
            if (destroysample_condition__defaultCondition(player, self))
            {
                destroysample_action_anim_converse(player, self);
                string_id message = new string_id(c_stringFile, "s_73b20bfc");
                removeObjVar(player, "conversation.destroysample.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Hey there Trent.  Do you have the sensor logs confirming your kills?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && response.equals("s_5c09875a"))
        {
            if (destroysample_condition__defaultCondition(player, self))
            {
                destroysample_action_anim_converse(player, self);
                string_id message = new string_id(c_stringFile, "s_df17f706");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (destroysample_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (destroysample_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_157082a");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cdc3b4c7");
                    }
                    setObjVar(player, "conversation.destroysample.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.destroysample.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I haven't seen you around here before.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && response.equals("s_157082a"))
        {
            if (destroysample_condition__defaultCondition(player, self))
            {
                destroysample_action_anim_checkwatch(player, self);
                string_id message = new string_id(c_stringFile, "s_c1fc410f");
                removeObjVar(player, "conversation.destroysample.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Nice to meet you, Trent.  I've got a job you might be interested in.  My faction wants help in making things difficult for the Empire.  If you can destroy 5 Tie Fighters, I'll pay you 3,000 credits.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && response.equals("s_cdc3b4c7"))
        {
            if (destroysample_condition__defaultCondition(player, self))
            {
                destroysample_action_giveQuest(player, self);
                string_id message = new string_id(c_stringFile, "s_28969bdd");
                removeObjVar(player, "conversation.destroysample.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Nice to meet you, Trent.  I've got a job you might be interested in.  My faction wants help in making things difficult for the Empire.  If you can destroy 5 Tie Fighters, I'll pay you 3,000 credits.' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.destroysample.branchId");
        return SCRIPT_CONTINUE;
    }
}
