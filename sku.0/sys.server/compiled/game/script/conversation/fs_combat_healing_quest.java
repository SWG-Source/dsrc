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
import script.library.fs_quests;
import script.library.quests;
import script.library.utils;

public class fs_combat_healing_quest extends script.base_script
{
    public fs_combat_healing_quest()
    {
    }
    public static String c_stringFile = "conversation/fs_combat_healing_quest";
    public boolean fs_combat_healing_quest_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean fs_combat_healing_quest_condition_canTalkToVillagers(obj_id player, obj_id npc) throws InterruptedException
    {
        return fs_quests.isVillageEligible(player);
    }
    public boolean fs_combat_healing_quest_condition_Finished(obj_id player, obj_id npc) throws InterruptedException
    {
        return quests.isComplete("fs_combat_healing_finish", player);
    }
    public boolean fs_combat_healing_quest_condition_inProgress(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "quest.fs_combat_healing.totalhealed"))
        {
            int totalhealed = getIntObjVar(player, "quest.fs_combat_healing.totalhealed");
            if (totalhealed < 50 && quests.isActive("fs_combat_healing_1", player))
            {
                return true;
            }
            else if (totalhealed < 100 && quests.isActive("fs_combat_healing_2", player))
            {
                return true;
            }
        }
        return false;
    }
    public boolean fs_combat_healing_quest_condition_hasCompletedFirstSet(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "quest.fs_combat_healing.totalhealed"))
        {
            int totalhealed = getIntObjVar(player, "quest.fs_combat_healing.totalhealed");
            if (totalhealed >= 50 && !quests.isComplete("fs_combat_healing_1", player))
            {
                return true;
            }
        }
        return false;
    }
    public boolean fs_combat_healing_quest_condition_intentroyCheck(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id inv = utils.getInventoryContainer(player);
        int freeSpace = getVolumeFree(inv);
        if (freeSpace < 1)
        {
            if (hasObjVar(player, "quest.fs_combat_healing.totalhealed"))
            {
                int totalhealed = getIntObjVar(player, "quest.fs_combat_healing.totalhealed");
                if (totalhealed >= 100 && !quests.isComplete("fs_combat_healing_2", player))
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean fs_combat_healing_quest_condition_hasCompletedSecondSet(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "quest.fs_combat_healing.totalhealed"))
        {
            int totalhealed = getIntObjVar(player, "quest.fs_combat_healing.totalhealed");
            if (totalhealed >= 100 && quests.isComplete("fs_combat_healing_1", player))
            {
                return true;
            }
        }
        return false;
    }
    public boolean fs_combat_healing_quest_condition_ableToDoQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return fs_quests.hasQuestAccepted(player);
    }
    public boolean fs_combat_healing_quest_condition_doneFirstSet(obj_id player, obj_id npc) throws InterruptedException
    {
        if (quests.canActivate("fs_combat_healing_2", player) && quests.isComplete("fs_combat_healing_1", player))
        {
            return true;
        }
        return false;
    }
    public boolean fs_combat_healing_quest_condition_hasMedicSkill(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasSkill(player, "science_medic_novice");
    }
    public void fs_combat_healing_quest_action_giveQuest1(obj_id player, obj_id npc) throws InterruptedException
    {
        quests.activate("fs_combat_healing_1", player, npc);
        fs_quests.setQuestAccepted(player);
        attachScript(player, "quest.force_sensitive.fs_combat_healing");
        setObjVar(player, "quest.fs_combat_healing.totalhealed", 0);
    }
    public void fs_combat_healing_quest_action_giveQuest2(obj_id player, obj_id npc) throws InterruptedException
    {
        quests.activate("fs_combat_healing_2", player, npc);
    }
    public void fs_combat_healing_quest_action_giveReward(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id playerInventory = utils.getInventoryContainer(player);
        obj_id createdObject = createObject("object/tangible/item/quest/force_sensitive/bacta_tank.iff", playerInventory, "");
        quests.complete("fs_combat_healing_2", player, true);
        detachScript(player, "quest.force_sensitive.fs_combat_healing");
    }
    public void fs_combat_healing_quest_action_unlockMedicBranch(obj_id player, obj_id npc) throws InterruptedException
    {
        fs_quests.unlockBranch(player, "force_sensitive_heightened_senses_healing");
        quests.complete("fs_combat_healing_1", player, true);
    }
    public String fs_combat_healing_quest_tokenTO_numberHealed(obj_id player, obj_id npc) throws InterruptedException
    {
        int totalhealed = -1;
        if (hasObjVar(player, "quest.fs_combat_healing.totalhealed"))
        {
            totalhealed = getIntObjVar(player, "quest.fs_combat_healing.totalhealed");
        }
        if (totalhealed <= 1)
        {
            return localize(new string_id("quest/force_sensitive/fs_medic", "combat_healing_just_started"));
        }
        String healingCountPartOne = localize(new string_id("quest/force_sensitive/fs_medic", "combat_healing_count_part1"));
        String healingCountPartTwo = localize(new string_id("quest/force_sensitive/fs_medic", "combat_healing_count_part2"));
        return healingCountPartOne + " " + totalhealed + " " + healingCountPartTwo;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.fs_combat_healing_quest");
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
        detachScript(self, "npc.conversation.fs_combat_healing_quest");
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
        if (!fs_combat_healing_quest_condition_canTalkToVillagers(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_48b1807b");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (fs_combat_healing_quest_condition_Finished(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_7263868e");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (fs_combat_healing_quest_condition_inProgress(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_e6d3592a");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (fs_combat_healing_quest_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_67e6df55");
                }
                setObjVar(player, "conversation.fs_combat_healing_quest.branchId", 3);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(self);
                pp.other.set(fs_combat_healing_quest_tokenTO_numberHealed(player, self));
                npcStartConversation(player, self, "fs_combat_healing_quest", null, pp, responses);
            }
            else 
            {
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(self);
                pp.other.set(fs_combat_healing_quest_tokenTO_numberHealed(player, self));
                chat.chat(self, player, null, null, pp);
            }
            return SCRIPT_CONTINUE;
        }
        if (fs_combat_healing_quest_condition_intentroyCheck(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_a7b82bb9");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (fs_combat_healing_quest_condition_hasCompletedSecondSet(player, self))
        {
            fs_combat_healing_quest_action_giveReward(player, self);
            string_id message = new string_id(c_stringFile, "s_5b948c11");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (fs_combat_healing_quest_condition_hasCompletedFirstSet(player, self))
        {
            fs_combat_healing_quest_action_unlockMedicBranch(player, self);
            string_id message = new string_id(c_stringFile, "s_8d7b6d3");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (fs_combat_healing_quest_condition_doneFirstSet(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_6a7931f2");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (fs_combat_healing_quest_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (fs_combat_healing_quest_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_aa2e6c00");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_2e7fd1c");
                }
                setObjVar(player, "conversation.fs_combat_healing_quest.branchId", 8);
                npcStartConversation(player, self, "fs_combat_healing_quest", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (!fs_combat_healing_quest_condition_hasMedicSkill(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_14e60463");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (fs_combat_healing_quest_condition_ableToDoQuest(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_5b2d3e0f");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (fs_combat_healing_quest_condition__defaultCondition(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_7ed32afc");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (fs_combat_healing_quest_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (fs_combat_healing_quest_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_8fddaea0");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_d4d1a11f");
                }
                setObjVar(player, "conversation.fs_combat_healing_quest.branchId", 13);
                npcStartConversation(player, self, "fs_combat_healing_quest", message, responses);
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
        if (!conversationId.equals("fs_combat_healing_quest"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.fs_combat_healing_quest.branchId");
        if (branchId == 3 && response.equals("s_67e6df55"))
        {
            if (fs_combat_healing_quest_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_e734fdf3");
                removeObjVar(player, "conversation.fs_combat_healing_quest.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I see, I see, yes very good, it looks like %TO' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && response.equals("s_aa2e6c00"))
        {
            fs_combat_healing_quest_action_giveQuest2(player, self);
            if (fs_combat_healing_quest_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_3fd56843");
                removeObjVar(player, "conversation.fs_combat_healing_quest.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Hmm... I can tell you are a great healer, I may have something else for you if you are interested in helping some more.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && response.equals("s_2e7fd1c"))
        {
            if (fs_combat_healing_quest_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_671a071f");
                removeObjVar(player, "conversation.fs_combat_healing_quest.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Hmm... I can tell you are a great healer, I may have something else for you if you are interested in helping some more.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && response.equals("s_8fddaea0"))
        {
            if (fs_combat_healing_quest_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_ad2d5f2b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (fs_combat_healing_quest_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (fs_combat_healing_quest_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_25b83816");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5d4f7fdb");
                    }
                    setObjVar(player, "conversation.fs_combat_healing_quest.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.fs_combat_healing_quest.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Huh? Oh! Great! More medics! The troops are in desperate need of support. Are you here to provide the medical support?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && response.equals("s_d4d1a11f"))
        {
            if (fs_combat_healing_quest_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_df4917e");
                removeObjVar(player, "conversation.fs_combat_healing_quest.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Huh? Oh! Great! More medics! The troops are in desperate need of support. Are you here to provide the medical support?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && response.equals("s_25b83816"))
        {
            fs_combat_healing_quest_action_giveQuest1(player, self);
            if (fs_combat_healing_quest_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_1f0513d3");
                removeObjVar(player, "conversation.fs_combat_healing_quest.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Help? Yes, you can help! What I need you to help with is troop support. This may put you in direct harms way, and you won't be able to help the other villagers with their tasks, but I will make sure Noldan helps you to develop your healing abilities even more. Are you sure?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && response.equals("s_5d4f7fdb"))
        {
            if (fs_combat_healing_quest_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_8c663fab");
                removeObjVar(player, "conversation.fs_combat_healing_quest.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Help? Yes, you can help! What I need you to help with is troop support. This may put you in direct harms way, and you won't be able to help the other villagers with their tasks, but I will make sure Noldan helps you to develop your healing abilities even more. Are you sure?' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.fs_combat_healing_quest.branchId");
        return SCRIPT_CONTINUE;
    }
}
