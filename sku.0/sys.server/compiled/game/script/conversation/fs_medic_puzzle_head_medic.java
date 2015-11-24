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

public class fs_medic_puzzle_head_medic extends script.base_script
{
    public fs_medic_puzzle_head_medic()
    {
    }
    public static String c_stringFile = "conversation/fs_medic_puzzle_head_medic";
    public boolean fs_medic_puzzle_head_medic_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean fs_medic_puzzle_head_medic_condition_ableToDoMedicQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return fs_quests.hasQuestAccepted(player);
    }
    public boolean fs_medic_puzzle_head_medic_condition_CanTalkToVillagers(obj_id player, obj_id npc) throws InterruptedException
    {
        return fs_quests.isVillageEligible(player);
    }
    public boolean fs_medic_puzzle_head_medic_condition_hasCompleted1(obj_id player, obj_id npc) throws InterruptedException
    {
        if (quests.canActivate(MEDIC_QUEST_NAME_TWO, player) && quests.isComplete(MEDIC_QUEST_NAME_ONE, player))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean fs_medic_puzzle_head_medic_condition_hasCompleted2(obj_id player, obj_id npc) throws InterruptedException
    {
        if (quests.canActivate(MEDIC_QUEST_NAME_THREE, player) && quests.isComplete(MEDIC_QUEST_NAME_TWO, player))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean fs_medic_puzzle_head_medic_condition_Finished(obj_id player, obj_id npc) throws InterruptedException
    {
        return quests.isComplete("fs_medic_puzzle_quest_finish", player);
    }
    public boolean fs_medic_puzzle_head_medic_condition_inProgress(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "fs.numberHealed"))
        {
            if (utils.getIntObjVar(player, "fs.numberHealed") < FIRST_SET && !quests.isComplete(MEDIC_QUEST_NAME_ONE, player))
            {
                return true;
            }
            if (utils.getIntObjVar(player, "fs.numberHealed") < SECOND_SET && !quests.isComplete(MEDIC_QUEST_NAME_TWO, player) && quests.isActive(MEDIC_QUEST_NAME_TWO, player))
            {
                return true;
            }
            if (utils.getIntObjVar(player, "fs.numberHealed") < THIRD_SET && !quests.isComplete(MEDIC_QUEST_NAME_THREE, player) && quests.isActive(MEDIC_QUEST_NAME_THREE, player))
            {
                return true;
            }
        }
        return false;
    }
    public boolean fs_medic_puzzle_head_medic_condition_healedFirstSet(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "fs.numberHealed"))
        {
            if (utils.getIntObjVar(player, "fs.numberHealed") >= FIRST_SET && quests.isActive(MEDIC_QUEST_NAME_ONE, player))
            {
                return true;
            }
        }
        return false;
    }
    public boolean fs_medic_puzzle_head_medic_condition_healedSecondSet(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "fs.numberHealed"))
        {
            if (utils.getIntObjVar(player, "fs.numberHealed") >= SECOND_SET && quests.isActive(MEDIC_QUEST_NAME_TWO, player))
            {
                return true;
            }
        }
        return false;
    }
    public boolean fs_medic_puzzle_head_medic_condition_healedThirdSet(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "fs.numberHealed"))
        {
            if (utils.getIntObjVar(player, "fs.numberHealed") >= THIRD_SET && quests.isActive(MEDIC_QUEST_NAME_THREE, player))
            {
                return true;
            }
        }
        return false;
    }
    public boolean fs_medic_puzzle_head_medic_condition_InventoryCheck(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id inv = utils.getInventoryContainer(player);
        int freeSpace = getVolumeFree(inv);
        if (freeSpace < 1)
        {
            if (hasObjVar(player, "fs.numberHealed"))
            {
                if (utils.getIntObjVar(player, "fs.numberHealed") >= FIRST_SET && !quests.isComplete(MEDIC_QUEST_NAME_ONE, player))
                {
                    return true;
                }
                if (utils.getIntObjVar(player, "fs.numberHealed") >= SECOND_SET && !quests.isComplete(MEDIC_QUEST_NAME_TWO, player))
                {
                    return true;
                }
                if (utils.getIntObjVar(player, "fs.numberHealed") >= THIRD_SET && !quests.isComplete(MEDIC_QUEST_NAME_THREE, player))
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean fs_medic_puzzle_head_medic_condition_haveMedicSkill(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasSkill(player, "science_medic_master");
    }
    public void fs_medic_puzzle_head_medic_action_GiveQuest1(obj_id player, obj_id npc) throws InterruptedException
    {
        quests.activate(MEDIC_QUEST_NAME_ONE, player, npc);
        fs_quests.setQuestAccepted(player);
        attachScript(player, "systems.fs_quest.fs_medic_puzzle_cleanup");
        setObjVar(player, "fs.numberHealed", 0);
    }
    public void fs_medic_puzzle_head_medic_action_GiveDraftSchematic(obj_id player, obj_id npc) throws InterruptedException
    {
        grantSchematic(player, "object/draft_schematic/item/quest_item/fs_medic_puzzle_heal_pack.iff");
    }
    public void fs_medic_puzzle_head_medic_action_Complete1(obj_id player, obj_id npc) throws InterruptedException
    {
        fs_quests.unlockBranch(player, "force_sensitive_heightened_senses_persuasion");
        quests.complete(MEDIC_QUEST_NAME_ONE, player, true);
    }
    public void fs_medic_puzzle_head_medic_action_Complete2(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id playerInventory = utils.getInventoryContainer(player);
        obj_id createdObject = createObject(REWARD_PART_TWO, playerInventory, "");
        quests.complete(MEDIC_QUEST_NAME_TWO, player, true);
    }
    public void fs_medic_puzzle_head_medic_action_Complete3(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id playerInventory = utils.getInventoryContainer(player);
        obj_id createdObject = createObject(REWARD_PART_THREE, playerInventory, "");
        quests.complete(MEDIC_QUEST_NAME_THREE, player, true);
        detachScript(player, "systems.fs_quest.fs_medic_puzzle_cleanup");
    }
    public void fs_medic_puzzle_head_medic_action_GiveQuest2(obj_id player, obj_id npc) throws InterruptedException
    {
        quests.activate(MEDIC_QUEST_NAME_TWO, player, npc);
    }
    public void fs_medic_puzzle_head_medic_action_GiveQuest3(obj_id player, obj_id npc) throws InterruptedException
    {
        quests.activate(MEDIC_QUEST_NAME_THREE, player, npc);
    }
    public String fs_medic_puzzle_head_medic_tokenTO_NumberLeftToHeal(obj_id player, obj_id npc) throws InterruptedException
    {
        int peopleHealed = 0;
        if (utils.hasObjVar(player, "fs.numberHealed"))
        {
            peopleHealed = utils.getIntObjVar(player, "fs.numberHealed");
        }
        if (peopleHealed >= 10)
        {
            peopleHealed = peopleHealed - 10;
        }
        if (peopleHealed >= 5)
        {
            peopleHealed = peopleHealed - 5;
        }
        peopleHealed = 5 - peopleHealed;
        return "" + peopleHealed;
    }
    public static final String MEDIC_QUEST_NAME_ONE = "fs_medic_puzzle_quest_01";
    public static final String MEDIC_QUEST_NAME_TWO = "fs_medic_puzzle_quest_02";
    public static final String MEDIC_QUEST_NAME_THREE = "fs_medic_puzzle_quest_03";
    public static final String REWARD_PART_THREE = "object/tangible/loot/plant_grow/plant_stage_1.iff";
    public static final String REWARD_PART_TWO = "object/tangible/wearables/necklace/necklace_ice_pendant.iff";
    public static final int FIRST_SET = 5;
    public static final int SECOND_SET = 10;
    public static final int THIRD_SET = 15;
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.fs_medic_puzzle_head_medic");
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
        detachScript(self, "npc.conversation.fs_medic_puzzle_head_medic");
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
        if (!fs_medic_puzzle_head_medic_condition_CanTalkToVillagers(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_3b984995");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (fs_medic_puzzle_head_medic_condition_Finished(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_3496f642");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (fs_medic_puzzle_head_medic_condition_InventoryCheck(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_36b08dde");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (fs_medic_puzzle_head_medic_condition_healedThirdSet(player, self))
        {
            fs_medic_puzzle_head_medic_action_Complete3(player, self);
            string_id message = new string_id(c_stringFile, "s_cc506105");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (fs_medic_puzzle_head_medic_condition_healedSecondSet(player, self))
        {
            fs_medic_puzzle_head_medic_action_Complete2(player, self);
            string_id message = new string_id(c_stringFile, "s_a7afcfea");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (fs_medic_puzzle_head_medic_condition_healedFirstSet(player, self))
        {
            fs_medic_puzzle_head_medic_action_Complete1(player, self);
            string_id message = new string_id(c_stringFile, "s_fc604056");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (fs_medic_puzzle_head_medic_condition_inProgress(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_2fee0236");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (fs_medic_puzzle_head_medic_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_3c93827f");
                }
                setObjVar(player, "conversation.fs_medic_puzzle_head_medic.branchId", 7);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(self);
                pp.other.set(fs_medic_puzzle_head_medic_tokenTO_NumberLeftToHeal(player, self));
                npcStartConversation(player, self, "fs_medic_puzzle_head_medic", null, pp, responses);
            }
            else 
            {
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(self);
                pp.other.set(fs_medic_puzzle_head_medic_tokenTO_NumberLeftToHeal(player, self));
                chat.chat(self, player, null, null, pp);
            }
            return SCRIPT_CONTINUE;
        }
        if (fs_medic_puzzle_head_medic_condition_hasCompleted2(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_e15eb34d");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (fs_medic_puzzle_head_medic_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (fs_medic_puzzle_head_medic_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_86129116");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_69ab9125");
                }
                setObjVar(player, "conversation.fs_medic_puzzle_head_medic.branchId", 9);
                npcStartConversation(player, self, "fs_medic_puzzle_head_medic", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (fs_medic_puzzle_head_medic_condition_hasCompleted1(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_ddb49891");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (fs_medic_puzzle_head_medic_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (fs_medic_puzzle_head_medic_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_86129116");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_69ab9125");
                }
                setObjVar(player, "conversation.fs_medic_puzzle_head_medic.branchId", 12);
                npcStartConversation(player, self, "fs_medic_puzzle_head_medic", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (!fs_medic_puzzle_head_medic_condition_haveMedicSkill(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_47a32bb0");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (fs_medic_puzzle_head_medic_condition_ableToDoMedicQuest(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_8a54510d");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (fs_medic_puzzle_head_medic_condition__defaultCondition(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_d46bb0c5");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (fs_medic_puzzle_head_medic_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (fs_medic_puzzle_head_medic_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1024d197");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_3228a43d");
                }
                setObjVar(player, "conversation.fs_medic_puzzle_head_medic.branchId", 17);
                npcStartConversation(player, self, "fs_medic_puzzle_head_medic", message, responses);
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
        if (!conversationId.equals("fs_medic_puzzle_head_medic"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.fs_medic_puzzle_head_medic.branchId");
        if (branchId == 7 && response.equals("s_3c93827f"))
        {
            if (fs_medic_puzzle_head_medic_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_c4682ea6");
                removeObjVar(player, "conversation.fs_medic_puzzle_head_medic.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Hmmm? Oh, talk to the medical droids if you are stuck. Medical records show you have %TO more to go.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && response.equals("s_86129116"))
        {
            fs_medic_puzzle_head_medic_action_GiveQuest3(player, self);
            if (fs_medic_puzzle_head_medic_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_464f7bf4");
                removeObjVar(player, "conversation.fs_medic_puzzle_head_medic.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Back again are we? Well I have a special plant you may find useful, but you will need to prove you are worthy. Go heal at least another five patients then come talk to me again.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && response.equals("s_69ab9125"))
        {
            if (fs_medic_puzzle_head_medic_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_bef6939f");
                removeObjVar(player, "conversation.fs_medic_puzzle_head_medic.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Back again are we? Well I have a special plant you may find useful, but you will need to prove you are worthy. Go heal at least another five patients then come talk to me again.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && response.equals("s_86129116"))
        {
            fs_medic_puzzle_head_medic_action_GiveQuest2(player, self);
            if (fs_medic_puzzle_head_medic_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_464f7bf4");
                removeObjVar(player, "conversation.fs_medic_puzzle_head_medic.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Oh, you are still here? well if you like, go ahead and heal at least five more patients then come talk to me again, I have a little trinket you might find useful.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && response.equals("s_69ab9125"))
        {
            if (fs_medic_puzzle_head_medic_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_bef6939f");
                removeObjVar(player, "conversation.fs_medic_puzzle_head_medic.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Oh, you are still here? well if you like, go ahead and heal at least five more patients then come talk to me again, I have a little trinket you might find useful.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && response.equals("s_1024d197"))
        {
            if (fs_medic_puzzle_head_medic_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_1718c6ed");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (fs_medic_puzzle_head_medic_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (fs_medic_puzzle_head_medic_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cc64464a");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2e7eb20b");
                    }
                    setObjVar(player, "conversation.fs_medic_puzzle_head_medic.branchId", 18);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.fs_medic_puzzle_head_medic.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Hello, I'm the head physician here. Are you one of the new Medics sent to help out?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && response.equals("s_3228a43d"))
        {
            if (fs_medic_puzzle_head_medic_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_db216f5");
                removeObjVar(player, "conversation.fs_medic_puzzle_head_medic.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Hello, I'm the head physician here. Are you one of the new Medics sent to help out?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && response.equals("s_cc64464a"))
        {
            fs_medic_puzzle_head_medic_action_GiveQuest1(player, self);
            if (fs_medic_puzzle_head_medic_condition__defaultCondition(player, self))
            {
                fs_medic_puzzle_head_medic_action_GiveDraftSchematic(player, self);
                string_id message = new string_id(c_stringFile, "s_728f65ee");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (fs_medic_puzzle_head_medic_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7857c2a9");
                    }
                    setObjVar(player, "conversation.fs_medic_puzzle_head_medic.branchId", 19);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.fs_medic_puzzle_head_medic.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Good. Before we get started, I should let you know that you will be too busy to help out any of the other villagers. Also, I will talk with Noldan and he can help you out with some persuasion techniques if you help out. Are you sure you would like to help?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && response.equals("s_2e7eb20b"))
        {
            if (fs_medic_puzzle_head_medic_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_41e66cca");
                removeObjVar(player, "conversation.fs_medic_puzzle_head_medic.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Good. Before we get started, I should let you know that you will be too busy to help out any of the other villagers. Also, I will talk with Noldan and he can help you out with some persuasion techniques if you help out. Are you sure you would like to help?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && response.equals("s_7857c2a9"))
        {
            if (fs_medic_puzzle_head_medic_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_94d417d1");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (fs_medic_puzzle_head_medic_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (fs_medic_puzzle_head_medic_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d17142db");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c4682ea6");
                    }
                    setObjVar(player, "conversation.fs_medic_puzzle_head_medic.branchId", 20);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.fs_medic_puzzle_head_medic.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Great! Let me explain what has happened. It seems in the last attack there were multiple uses of some rather potent chemical weapons. Those you see lying around here are still sick. Unfortunately, normal medicines are not helping them.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && response.equals("s_d17142db"))
        {
            if (fs_medic_puzzle_head_medic_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_17db6d3c");
                removeObjVar(player, "conversation.fs_medic_puzzle_head_medic.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Well, I devised a highly potent, yet also highly dangerous counteragent to many of the symptoms. The only problem is that it has some side effects that are almost as bad as what they currently have. However, through multiple treatments, I've been able to cure some. Here, this draft schematic will allow you to work with the medical droids to help.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && response.equals("s_c4682ea6"))
        {
            if (fs_medic_puzzle_head_medic_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_d08f8eb6");
                removeObjVar(player, "conversation.fs_medic_puzzle_head_medic.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Well, I devised a highly potent, yet also highly dangerous counteragent to many of the symptoms. The only problem is that it has some side effects that are almost as bad as what they currently have. However, through multiple treatments, I've been able to cure some. Here, this draft schematic will allow you to work with the medical droids to help.' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.fs_medic_puzzle_head_medic.branchId");
        return SCRIPT_CONTINUE;
    }
}
