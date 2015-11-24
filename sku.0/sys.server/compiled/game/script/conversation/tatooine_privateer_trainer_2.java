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
import script.library.space_flags;
import script.library.space_quest;

public class tatooine_privateer_trainer_2 extends script.base_script
{
    public tatooine_privateer_trainer_2()
    {
    }
    public static String c_stringFile = "conversation/tatooine_privateer_trainer_2";
    public boolean tatooine_privateer_trainer_2_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean tatooine_privateer_trainer_2_condition_hasCompletedTransition(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasCompletedQuestRecursive(player, "patrol", "tat_priv_quest_trans"))
        {
            if (!space_quest.hasWonQuestRecursive(player, "patrol", "tat_priv_quest_trans"))
            {
                return false;
            }
            else 
            {
                return true;
            }
        }
        return false;
    }
    public boolean tatooine_privateer_trainer_2_condition_hasAllTierOneSkills(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_flags.hasCompletedTierOne(player);
    }
    public boolean tatooine_privateer_trainer_2_condition_isSmugglerAllianceMember(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_flags.isSpaceTrack(player, space_flags.PRIVATEER_TATOOINE);
    }
    public boolean tatooine_privateer_trainer_2_condition_isOnTransQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasQuest(player, "patrol", "tat_priv_quest_trans"))
        {
            return true;
        }
        if (space_quest.hasQuest(player, "assassinate", "tat_priv_quest_trans"))
        {
            return true;
        }
        return false;
    }
    public boolean tatooine_privateer_trainer_2_condition_gettingTransQuestReward(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasWonQuestRecursive(player, "patrol", "tat_priv_quest_trans"))
        {
            return false;
        }
        else if (!space_quest.hasReceivedReward(player, "patrol", "tat_priv_quest_trans"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean tatooine_privateer_trainer_2_condition_failedTransQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasFailedQuestRecursive(player, "patrol", "tat_priv_quest_trans") || space_quest.hasAbortedQuestRecursive(player, "patrol", "tat_priv_quest_trans"));
    }
    public boolean tatooine_privateer_trainer_2_condition_hasCompletedSeries(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_flags.hasCompletedTierTwo(player) && space_quest.hasWonQuest(player, "assassinate", "tatooine_privateer_tier2_1a") && space_quest.hasWonQuest(player, "assassinate", "tatooine_privateer_tier2_2a") && space_quest.hasWonQuest(player, "assassinate", "tatooine_privateer_tier2_3a") && space_quest.hasWonQuest(player, "escort", "tatooine_privateer_tier2_4a"))
        {
            return true;
        }
        return false;
    }
    public boolean tatooine_privateer_trainer_2_condition_doneWithTier3(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_flags.hasCompletedTierThree(player);
    }
    public boolean tatooine_privateer_trainer_2_condition_doneWithTier4(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_flags.hasCompletedTierFour(player);
    }
    public void tatooine_privateer_trainer_2_action_grantTransQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "patrol", "tat_priv_quest_trans");
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.tatooine_privateer_trainer_2");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_SPACE_INTERESTING);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_SPACE_INTERESTING);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info menuInfo) throws InterruptedException
    {
        int menu = menuInfo.addRootMenu(menu_info_types.CONVERSE_START, null);
        menu_info_data menuInfoData = menuInfo.getMenuItemById(menu);
        menuInfoData.setServerNotify(false);
        faceTo(self, player);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        detachScript(self, "conversation.tatooine_privateer_trainer_2");
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
        if (!tatooine_privateer_trainer_2_condition_isSmugglerAllianceMember(player, self))
        {
            doAnimationAction(self, "wave_on_dismissing");
            string_id message = new string_id(c_stringFile, "s_b50f6fd1");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_privateer_trainer_2_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (tatooine_privateer_trainer_2_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_dc9e436d");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_ba77b53a");
                }
                setObjVar(player, "conversation.tatooine_privateer_trainer_2.branchId", 1);
                npcStartConversation(player, self, "tatooine_privateer_trainer_2", message, responses);
            }
            else 
            {
                chat.chat(self, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_privateer_trainer_2_condition_doneWithTier3(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_74bf2381");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_privateer_trainer_2_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1c8bddbb");
                }
                setObjVar(player, "conversation.tatooine_privateer_trainer_2.branchId", 4);
                npcStartConversation(player, self, "tatooine_privateer_trainer_2", message, responses);
            }
            else 
            {
                chat.chat(self, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_privateer_trainer_2_condition_hasCompletedSeries(player, self))
        {
            doAnimationAction(self, "tap_foot");
            string_id message = new string_id(c_stringFile, "s_697e04cf");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_privateer_trainer_2_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_98893f74");
                }
                setObjVar(player, "conversation.tatooine_privateer_trainer_2.branchId", 10);
                npcStartConversation(player, self, "tatooine_privateer_trainer_2", message, responses);
            }
            else 
            {
                chat.chat(self, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_privateer_trainer_2_condition_failedTransQuest(player, self))
        {
            doAnimationAction(self, "gesticulate_wildly");
            string_id message = new string_id(c_stringFile, "s_983a902c");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_privateer_trainer_2_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_a60a995b");
                }
                setObjVar(player, "conversation.tatooine_privateer_trainer_2.branchId", 14);
                npcStartConversation(player, self, "tatooine_privateer_trainer_2", message, responses);
            }
            else 
            {
                chat.chat(self, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (!tatooine_privateer_trainer_2_condition_hasAllTierOneSkills(player, self))
        {
            doAnimationAction(self, "beckon");
            string_id message = new string_id(c_stringFile, "s_3ff6efdd");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_privateer_trainer_2_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (tatooine_privateer_trainer_2_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_151b3d1");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_6372b444");
                }
                setObjVar(player, "conversation.tatooine_privateer_trainer_2.branchId", 16);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(self);
                npcStartConversation(player, self, "tatooine_privateer_trainer_2", null, pp, responses);
            }
            else 
            {
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(self);
                chat.chat(self, player, null, null, pp);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_privateer_trainer_2_condition_gettingTransQuestReward(player, self))
        {
            doAnimationAction(self, "applause_polite");
            string_id message = new string_id(c_stringFile, "s_9571c1b4");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        if (tatooine_privateer_trainer_2_condition_isOnTransQuest(player, self))
        {
            doAnimationAction(self, "gesticulate_wildly");
            string_id message = new string_id(c_stringFile, "s_e002e01a");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        if (!tatooine_privateer_trainer_2_condition_hasCompletedTransition(player, self))
        {
            doAnimationAction(self, "check_wrist_device");
            string_id message = new string_id(c_stringFile, "s_89b061e3");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_privateer_trainer_2_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (tatooine_privateer_trainer_2_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_c3c2195b");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_3f10da9d");
                }
                setObjVar(player, "conversation.tatooine_privateer_trainer_2.branchId", 21);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(self);
                npcStartConversation(player, self, "tatooine_privateer_trainer_2", null, pp, responses);
            }
            else 
            {
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(self);
                chat.chat(self, player, null, null, pp);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_privateer_trainer_2_condition_doneWithTier4(player, self))
        {
            doAnimationAction(self, "slow_down");
            string_id message = new string_id(c_stringFile, "s_2d0c7400");
            prose_package pp = new prose_package();
            pp.stringId = message;
            pp.actor.set(player);
            pp.target.set(self);
            chat.chat(self, player, null, null, pp);
            return SCRIPT_CONTINUE;
        }
        if (tatooine_privateer_trainer_2_condition__defaultCondition(player, self))
        {
            doAnimationAction(self, "wave_finger_warning");
            string_id message = new string_id(c_stringFile, "s_1169dea4");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("tatooine_privateer_trainer_2"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.tatooine_privateer_trainer_2.branchId");
        if (branchId == 1 && response.equals("s_dc9e436d"))
        {
            if (tatooine_privateer_trainer_2_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "point_accusingly");
                string_id message = new string_id(c_stringFile, "s_3f2c222d");
                removeObjVar(player, "conversation.tatooine_privateer_trainer_2.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I'm too busy to talk to you. If you're looking for work, then talk to my man Dravis.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 1 && response.equals("s_ba77b53a"))
        {
            if (tatooine_privateer_trainer_2_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "shrug_hands");
                string_id message = new string_id(c_stringFile, "s_c8206f73");
                removeObjVar(player, "conversation.tatooine_privateer_trainer_2.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I'm too busy to talk to you. If you're looking for work, then talk to my man Dravis.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && response.equals("s_1c8bddbb"))
        {
            if (tatooine_privateer_trainer_2_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "point_accusingly");
                string_id message = new string_id(c_stringFile, "s_9fcfc09a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_2_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_800d8ca1");
                    }
                    setObjVar(player, "conversation.tatooine_privateer_trainer_2.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.tatooine_privateer_trainer_2.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'So, Jabba's happy with your performance, I guess, or else he'd have fed you to his rancor, right? Well that's excellent news, and I've got some even better.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_800d8ca1"))
        {
            if (tatooine_privateer_trainer_2_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "snap_finger2");
                string_id message = new string_id(c_stringFile, "s_de0bf763");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_2_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a89cdefd");
                    }
                    setObjVar(player, "conversation.tatooine_privateer_trainer_2.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.tatooine_privateer_trainer_2.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Ever heard of a place called Dathomir? Sure you have. Well it's a nasty little place with lots of opportunity for us to expand our operations. I've got a contact there you should meet.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && response.equals("s_a89cdefd"))
        {
            if (tatooine_privateer_trainer_2_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "pound_fist_palm");
                string_id message = new string_id(c_stringFile, "s_238f162d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_2_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_415dfb0e");
                    }
                    setObjVar(player, "conversation.tatooine_privateer_trainer_2.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.tatooine_privateer_trainer_2.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'He goes by the name of Nirame Sakute, and he's trying to make a name for himself in Nym's organization.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && response.equals("s_415dfb0e"))
        {
            if (tatooine_privateer_trainer_2_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "shrug_shoulders");
                string_id message = new string_id(c_stringFile, "s_a134e447");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_2_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e8ed6f9a");
                    }
                    setObjVar(player, "conversation.tatooine_privateer_trainer_2.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.tatooine_privateer_trainer_2.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Yeah, so? Never heard of hyperspace? Nym's boys go wherever there's an opportunity for profit. Especially at the Empire's expense.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && response.equals("s_e8ed6f9a"))
        {
            if (tatooine_privateer_trainer_2_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "point_to_self");
                string_id message = new string_id(c_stringFile, "s_aa257bf2");
                removeObjVar(player, "conversation.tatooine_privateer_trainer_2.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Like I said, we're branching out. Nirame gives us a cut of his profits. He helps Nym's operation in the Dathomir system, and everyone is happy.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && response.equals("s_98893f74"))
        {
            doAnimationAction(player, "shrug_hands");
            if (tatooine_privateer_trainer_2_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "applause_polite");
                string_id message = new string_id(c_stringFile, "s_fa31c92f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_2_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_de9b68cd");
                    }
                    setObjVar(player, "conversation.tatooine_privateer_trainer_2.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.tatooine_privateer_trainer_2.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'So Shamdon got cold feet and decided to call off the operation. Nothing unusual about that though, you have to have an iron will to mess with Jabba. No matter, we always have work lined up for our pilots.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && response.equals("s_de9b68cd"))
        {
            if (tatooine_privateer_trainer_2_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "point_accusingly");
                string_id message = new string_id(c_stringFile, "s_7642f4a4");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_2_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_805cb6f3");
                    }
                    setObjVar(player, "conversation.tatooine_privateer_trainer_2.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.tatooine_privateer_trainer_2.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Believe it or not, Jabba was so impressed by your work against him that he has asked for your services specifically. You are to speak with Beissa in Jabba's Palace.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && response.equals("s_805cb6f3"))
        {
            doAnimationAction(player, "nod_head_once");
            if (tatooine_privateer_trainer_2_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_9504aca1");
                removeObjVar(player, "conversation.tatooine_privateer_trainer_2.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'What did you do? Kill a bunch of fodder that Jabba really doesn't care about? Jabba respects only one thing and that is strength. You showed backbone and Jabba is more then willing to forgive and forget as long as you do a good job for him.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && response.equals("s_a60a995b"))
        {
            if (tatooine_privateer_trainer_2_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "point_accusingly");
                tatooine_privateer_trainer_2_action_grantTransQuest(player, self);
                string_id message = new string_id(c_stringFile, "s_12f6a89c");
                removeObjVar(player, "conversation.tatooine_privateer_trainer_2.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'What are you doing here? Didn't I tell you to go to Lok?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && response.equals("s_151b3d1"))
        {
            doAnimationAction(player, "nod_head_once");
            if (tatooine_privateer_trainer_2_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "explain");
                string_id message = new string_id(c_stringFile, "s_2a5548e1");
                removeObjVar(player, "conversation.tatooine_privateer_trainer_2.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(self);
                npcSpeak(player, pp);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Ah, %TU! One of Dravis's new 'employees', right?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && response.equals("s_6372b444"))
        {
            doAnimationAction(player, "point_to_self");
            if (tatooine_privateer_trainer_2_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "slow_down");
                string_id message = new string_id(c_stringFile, "s_ff6979b6");
                removeObjVar(player, "conversation.tatooine_privateer_trainer_2.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(self);
                npcSpeak(player, pp);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Ah, %TU! One of Dravis's new 'employees', right?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && response.equals("s_c3c2195b"))
        {
            doAnimationAction(player, "wave_on_dismissing");
            if (tatooine_privateer_trainer_2_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "pound_fist_palm");
                string_id message = new string_id(c_stringFile, "s_71162f37");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_2_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1c8bddbb");
                    }
                    setObjVar(player, "conversation.tatooine_privateer_trainer_2.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.tatooine_privateer_trainer_2.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch '%NU! It's about time you got here. Didn't Dravis tell you I wanted to see you right away?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && response.equals("s_3f10da9d"))
        {
            doAnimationAction(player, "salute2");
            if (tatooine_privateer_trainer_2_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "shake_head_disgust");
                string_id message = new string_id(c_stringFile, "s_7a2ca8fc");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_2_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1c8bddbb");
                    }
                    setObjVar(player, "conversation.tatooine_privateer_trainer_2.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.tatooine_privateer_trainer_2.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch '%NU! It's about time you got here. Didn't Dravis tell you I wanted to see you right away?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && response.equals("s_1c8bddbb"))
        {
            if (tatooine_privateer_trainer_2_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "laugh_titter");
                string_id message = new string_id(c_stringFile, "s_810a7e7");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_2_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_2_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_2_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e70a45c9");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_365a729a");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ba9fbfb1");
                    }
                    setObjVar(player, "conversation.tatooine_privateer_trainer_2.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.tatooine_privateer_trainer_2.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Busy?! You lazy antenna-breaker! Next time Dravis tells you that I need to see you, you'd better haul jets! This is no Lum Run, I've got an important job for you... and this assignment is going to make us RICH.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && response.equals("s_1c8bddbb"))
        {
            if (tatooine_privateer_trainer_2_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "laugh_titter");
                string_id message = new string_id(c_stringFile, "s_810a7e7");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_2_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_2_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_2_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e70a45c9");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_365a729a");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ba9fbfb1");
                    }
                    setObjVar(player, "conversation.tatooine_privateer_trainer_2.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.tatooine_privateer_trainer_2.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'That's what passes as 'fast' for you? Look, this is no Lum Run, I have an important job for you. And this assignment is going to make us RICH!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && response.equals("s_e70a45c9"))
        {
            if (tatooine_privateer_trainer_2_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "explain");
                string_id message = new string_id(c_stringFile, "s_4312ff91");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_2_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_2_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_25e48bc1");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9cf1e4c3");
                    }
                    setObjVar(player, "conversation.tatooine_privateer_trainer_2.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.tatooine_privateer_trainer_2.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Oh NOW you're in a hurry. You greedy bludfly! Alright, here's the deal, I need you to travel to Lok. Ever heard of a guy named Nym?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && response.equals("s_365a729a"))
        {
            if (tatooine_privateer_trainer_2_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "nod_head_multiple");
                string_id message = new string_id(c_stringFile, "s_4963bef6");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_2_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_db4bd0fd");
                    }
                    setObjVar(player, "conversation.tatooine_privateer_trainer_2.branchId", 27);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.tatooine_privateer_trainer_2.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Oh NOW you're in a hurry. You greedy bludfly! Alright, here's the deal, I need you to travel to Lok. Ever heard of a guy named Nym?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && response.equals("s_ba9fbfb1"))
        {
            if (tatooine_privateer_trainer_2_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "point_accusingly");
                string_id message = new string_id(c_stringFile, "s_9a287fb8");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_2_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_db4bd0fd");
                    }
                    setObjVar(player, "conversation.tatooine_privateer_trainer_2.branchId", 27);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.tatooine_privateer_trainer_2.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Oh NOW you're in a hurry. You greedy bludfly! Alright, here's the deal, I need you to travel to Lok. Ever heard of a guy named Nym?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && response.equals("s_25e48bc1"))
        {
            doAnimationAction(player, "shake_head_no");
            if (tatooine_privateer_trainer_2_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "shrug_shoulders");
                string_id message = new string_id(c_stringFile, "s_af906d73");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_2_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_db4bd0fd");
                    }
                    setObjVar(player, "conversation.tatooine_privateer_trainer_2.branchId", 27);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.tatooine_privateer_trainer_2.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'That's ok. He's one nasty customer anyway. He's a Feeorin, if you can believe that. You know how they are!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && response.equals("s_9cf1e4c3"))
        {
            if (tatooine_privateer_trainer_2_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_daf180e6");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_2_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_db4bd0fd");
                    }
                    setObjVar(player, "conversation.tatooine_privateer_trainer_2.branchId", 27);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.tatooine_privateer_trainer_2.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'That's ok. He's one nasty customer anyway. He's a Feeorin, if you can believe that. You know how they are!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && response.equals("s_db4bd0fd"))
        {
            if (tatooine_privateer_trainer_2_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "explain");
                tatooine_privateer_trainer_2_action_grantTransQuest(player, self);
                string_id message = new string_id(c_stringFile, "s_bb6309e6");
                removeObjVar(player, "conversation.tatooine_privateer_trainer_2.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'No? Well, I suppose you wouldn't. Not many of them around any more. Anyway, like I said, it's not really important.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 27 && response.equals("s_db4bd0fd"))
        {
            if (tatooine_privateer_trainer_2_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "explain");
                tatooine_privateer_trainer_2_action_grantTransQuest(player, self);
                string_id message = new string_id(c_stringFile, "s_bb6309e6");
                removeObjVar(player, "conversation.tatooine_privateer_trainer_2.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Right to the point, huh.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && response.equals("s_db4bd0fd"))
        {
            if (tatooine_privateer_trainer_2_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "explain");
                tatooine_privateer_trainer_2_action_grantTransQuest(player, self);
                string_id message = new string_id(c_stringFile, "s_bb6309e6");
                removeObjVar(player, "conversation.tatooine_privateer_trainer_2.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Good, then you know he's not one to mess with. 'Course, he has a lot of enemies, but it's better to be on his good side than not, if you know what I mean.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 30 && response.equals("s_db4bd0fd"))
        {
            if (tatooine_privateer_trainer_2_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "explain");
                tatooine_privateer_trainer_2_action_grantTransQuest(player, self);
                string_id message = new string_id(c_stringFile, "s_bb6309e6");
                removeObjVar(player, "conversation.tatooine_privateer_trainer_2.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'What? Why? What did you do?' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.tatooine_privateer_trainer_2.branchId");
        return SCRIPT_CONTINUE;
    }
}
