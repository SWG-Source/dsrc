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

public class fs_phase4_chief_engineer extends script.base_script
{
    public fs_phase4_chief_engineer()
    {
    }
    public static String c_stringFile = "conversation/fs_phase4_chief_engineer";
    public boolean fs_phase4_chief_engineer_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean fs_phase4_chief_engineer_condition_onQuestStep01(obj_id player, obj_id npc) throws InterruptedException
    {
        return quests.isActive("fs_crafting4_quest_01", player);
    }
    public boolean fs_phase4_chief_engineer_condition_onQuestStep02(obj_id player, obj_id npc) throws InterruptedException
    {
        return quests.isActive("fs_crafting4_quest_02", player);
    }
    public boolean fs_phase4_chief_engineer_condition_onQuestStep03(obj_id player, obj_id npc) throws InterruptedException
    {
        return quests.isActive("fs_crafting4_quest_03", player);
    }
    public boolean fs_phase4_chief_engineer_condition_onQuestStep04(obj_id player, obj_id npc) throws InterruptedException
    {
        return quests.isActive("fs_crafting4_quest_04", player);
    }
    public boolean fs_phase4_chief_engineer_condition_onQuestStep05(obj_id player, obj_id npc) throws InterruptedException
    {
        return quests.isActive("fs_crafting4_quest_05", player);
    }
    public boolean fs_phase4_chief_engineer_condition_onQuestStep06(obj_id player, obj_id npc) throws InterruptedException
    {
        if (fs_quests.hasQuestAccepted(player))
        {
            if (quests.isActive("fs_crafting4_quest_06", player))
            {
                return true;
            }
        }
        return false;
    }
    public boolean fs_phase4_chief_engineer_condition_noInventorySpace(obj_id player, obj_id npc) throws InterruptedException
    {
        boolean hasNoInvRoom = false;
        obj_id playerInv = utils.getInventoryContainer(player);
        if (isIdValid(playerInv))
        {
            int free_space = getVolumeFree(playerInv);
            if (free_space < 1)
            {
                hasNoInvRoom = true;
            }
        }
        return hasNoInvRoom;
    }
    public boolean fs_phase4_chief_engineer_condition_hasAlreadyUnlockedBranch(obj_id player, obj_id npc) throws InterruptedException
    {
        return fs_quests.hasUnlockedBranch(player, "force_sensitive_crafting_mastery_repair");
    }
    public boolean fs_phase4_chief_engineer_condition_alreadyHasOtherQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (fs_quests.hasQuestAccepted(player))
        {
            if (fs_phase4_chief_engineer_condition_isNotOnCrafting4Quest(player, npc))
            {
                return true;
            }
        }
        return false;
    }
    public boolean fs_phase4_chief_engineer_condition_onQuestStep07(obj_id player, obj_id npc) throws InterruptedException
    {
        return quests.isActive("fs_crafting4_quest_07", player);
    }
    public boolean fs_phase4_chief_engineer_condition_isNotOnCrafting4Quest(obj_id player, obj_id npc) throws InterruptedException
    {
        fs_phase4_chief_engineer_action_getCrafting4ActiveQuestNum(player, npc);
        int questNum = utils.getIntScriptVar(player, "crafting4.activeQuest");
        utils.removeScriptVar(player, "crafting4.activeQuest");
        if (questNum > -1)
        {
            return false;
        }
        return true;
    }
    public boolean fs_phase4_chief_engineer_condition_isBeginQuestEligible(obj_id player, obj_id npc) throws InterruptedException
    {
        if (fs_phase4_chief_engineer_condition_isNotOnCrafting4Quest(player, npc))
        {
            return true;
        }
        return false;
    }
    public boolean fs_phase4_chief_engineer_condition_hasCoreObject(obj_id player, obj_id npc) throws InterruptedException
    {
        String coreTemplate = "object/tangible/item/quest/force_sensitive/fs_crafting4_computer_core.iff";
        return utils.playerHasItemByTemplateInBankOrInventory(player, coreTemplate);
    }
    public boolean fs_phase4_chief_engineer_condition_isNotVillageEligible(obj_id player, obj_id npc) throws InterruptedException
    {
        return !fs_quests.isVillageEligible(player);
    }
    public boolean fs_phase4_chief_engineer_condition_isOnQuest01to05(obj_id player, obj_id npc) throws InterruptedException
    {
        if (fs_quests.hasQuestAccepted(player))
        {
            fs_phase4_chief_engineer_action_getCrafting4ActiveQuestNum(player, npc);
            int questNum = utils.getIntScriptVar(player, "crafting4.activeQuest");
            utils.removeScriptVar(player, "crafting4.activeQuest");
            if (questNum > 0 && questNum <= 5)
            {
                return true;
            }
        }
        return false;
    }
    public boolean fs_phase4_chief_engineer_condition_isContinuingQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!fs_quests.hasQuestAccepted(player))
        {
            fs_phase4_chief_engineer_action_getCrafting4ActiveQuestNum(player, npc);
            int questNum = utils.getIntScriptVar(player, "crafting4.activeQuest");
            utils.removeScriptVar(player, "crafting4.activeQuest");
            if (questNum > -1 && questNum <= 5)
            {
                return true;
            }
        }
        return false;
    }
    public void fs_phase4_chief_engineer_action_giveStatueSection(obj_id player, obj_id npc) throws InterruptedException
    {
        String rewardTemplate = "object/tangible/item/quest/force_sensitive/fs_sculpture_2.iff";
        obj_id playerInv = getObjectInSlot(player, "inventory");
        if (isIdValid(playerInv))
        {
            obj_id kit = createObject(rewardTemplate, playerInv, "");
            if (isIdValid(kit))
            {
                if (hasObjVar(player, "fs_crafting4"))
                {
                    removeObjVar(player, "fs_crafting4");
                }
                if (hasScript(player, "quest.force_sensitive.fs_crafting4_player"))
                {
                    detachScript(player, "quest.force_sensitive.fs_crafting4_player");
                }
            }
        }
        if (quests.isActive("fs_crafting4_quest_07", player))
        {
            quests.complete("fs_crafting4_quest_07", player, true);
        }
        quests.activate("fs_crafting4_quest_finish", player, null);
        quests.complete("fs_crafting4_quest_finish", player, true);
        String custLogMsg = "FS Phase 4 Crafting Quest: Player %TU has completed the phase 4 crafting quest.";
        CustomerServiceLog("FS_Phase4_Crafting", custLogMsg, player);
        return;
    }
    public void fs_phase4_chief_engineer_action_setQuestStep07(obj_id player, obj_id npc) throws InterruptedException
    {
        if (quests.isActive("fs_crafting4_quest_06", player))
        {
            quests.complete("fs_crafting4_quest_06", player, true);
        }
        quests.activate("fs_crafting4_quest_07", player, null);
        return;
    }
    public void fs_phase4_chief_engineer_action_giveKerenWaypoint(obj_id player, obj_id npc) throws InterruptedException
    {
        location targetLoc = new location(1215, 0, 2740, "naboo", null);
        obj_id newWaypoint = createWaypointInDatapad(player, targetLoc);
        setWaypointName(newWaypoint, "Keren - Gadget Specialist");
        fs_phase4_chief_engineer_action_activateQuest(player, npc);
        return;
    }
    public void fs_phase4_chief_engineer_action_giveCoronetWaypoint(obj_id player, obj_id npc) throws InterruptedException
    {
        location targetLoc = new location(19, 0, -4775, "corellia", null);
        obj_id newWaypoint = createWaypointInDatapad(player, targetLoc);
        setWaypointName(newWaypoint, "Coronet - Gadget Specialist");
        fs_phase4_chief_engineer_action_activateQuest(player, npc);
        return;
    }
    public void fs_phase4_chief_engineer_action_giveMosEnthaWaypoint(obj_id player, obj_id npc) throws InterruptedException
    {
        location targetLoc = new location(1209, 0, 2923, "tatooine", null);
        obj_id newWaypoint = createWaypointInDatapad(player, targetLoc);
        setWaypointName(newWaypoint, "Mos Entha - Gadget Specialist");
        fs_phase4_chief_engineer_action_activateQuest(player, npc);
        return;
    }
    public void fs_phase4_chief_engineer_action_activateQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        fs_phase4_chief_engineer_action_getCrafting4LastCompleteQuest(player, npc);
        int lastCompleteQuest = utils.getIntScriptVar(player, "crafting4.lastCompleteQuest");
        utils.removeScriptVar(player, "crafting4.lastCompleteQuest");
        if (lastCompleteQuest > -1)
        {
            int nextQuest = lastCompleteQuest + 1;
            String questName = "fs_crafting4_quest_0" + nextQuest;
            quests.activate("fs_crafting4_quest_00", player, null);
        }
        else 
        {
            quests.activate("fs_crafting4_quest_00", player, null);
        }
        fs_quests.setQuestAccepted(player);
        if (!hasScript(player, "quest.force_sensitive.fs_crafting4_player"))
        {
            attachScript(player, "quest.force_sensitive.fs_crafting4_player");
        }
        String custLogMsg = "FS Phase 4 Crafting Quest: Player %TU has begun the phase 4 crafting quest.";
        CustomerServiceLog("FS_Phase4_Crafting", custLogMsg, player);
        return;
    }
    public void fs_phase4_chief_engineer_action_openSkillBranch(obj_id player, obj_id npc) throws InterruptedException
    {
        String coreTemplate = "object/tangible/item/quest/force_sensitive/fs_crafting4_computer_core.iff";
        obj_id coreObj = utils.getItemPlayerHasByTemplateInBankOrInventory(player, coreTemplate);
        if (isIdValid(coreObj))
        {
            messageTo(coreObj, "cleanUpSelf", null, 1, false);
        }
        fs_quests.unlockBranch(player, "force_sensitive_crafting_mastery_repair");
        if (quests.isActive("fs_crafting4_quest_06", player))
        {
            quests.complete("fs_crafting4_quest_06", player, true);
        }
        String custLogMsg = "FS Phase 4 Crafting Quest: Player %TU has unlocked branch name: force_sensitive_crafting_mastery_repair.";
        CustomerServiceLog("FS_Phase4_Crafting", custLogMsg, player);
        return;
    }
    public void fs_phase4_chief_engineer_action_getCrafting4ActiveQuestNum(obj_id player, obj_id npc) throws InterruptedException
    {
        int questNum = -1;
        if (quests.isActive("fs_crafting4_quest_00", player))
        {
            questNum = 0;
        }
        if (quests.isActive("fs_crafting4_quest_01", player))
        {
            questNum = 1;
        }
        if (quests.isActive("fs_crafting4_quest_02", player))
        {
            questNum = 2;
        }
        if (quests.isActive("fs_crafting4_quest_03", player))
        {
            questNum = 3;
        }
        if (quests.isActive("fs_crafting4_quest_04", player))
        {
            questNum = 4;
        }
        if (quests.isActive("fs_crafting4_quest_05", player))
        {
            questNum = 5;
        }
        if (quests.isActive("fs_crafting4_quest_06", player))
        {
            questNum = 6;
        }
        utils.setScriptVar(player, "crafting4.activeQuest", questNum);
        return;
    }
    public void fs_phase4_chief_engineer_action_setFSQuestAccepted(obj_id player, obj_id npc) throws InterruptedException
    {
        fs_quests.setQuestAccepted(player);
        return;
    }
    public void fs_phase4_chief_engineer_action_getCrafting4LastCompleteQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        int questNum = -1;
        if (quests.isComplete("fs_crafting4_quest_01", player))
        {
            questNum = 1;
        }
        if (quests.isComplete("fs_crafting4_quest_02", player))
        {
            questNum = 2;
        }
        if (quests.isComplete("fs_crafting4_quest_03", player))
        {
            questNum = 3;
        }
        if (quests.isComplete("fs_crafting4_quest_04", player))
        {
            questNum = 4;
        }
        if (quests.isComplete("fs_crafting4_quest_05", player))
        {
            questNum = 5;
        }
        if (quests.isComplete("fs_crafting4_quest_06", player))
        {
            questNum = 6;
        }
        utils.setScriptVar(player, "crafting4.lastCompleteQuest", questNum);
        return;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.fs_phase4_chief_engineer");
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
        detachScript(self, "npc.conversation.fs_phase4_chief_engineer");
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
        if (fs_phase4_chief_engineer_condition_isNotVillageEligible(player, self))
        {
            doAnimationAction(self, "wave_on_dismissing");
            string_id message = new string_id(c_stringFile, "s_e77051e7");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (fs_phase4_chief_engineer_condition_onQuestStep07(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_ec479e11");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (fs_phase4_chief_engineer_condition_onQuestStep07(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_6516bd5a");
                }
                setObjVar(player, "conversation.fs_phase4_chief_engineer.branchId", 2);
                npcStartConversation(player, self, "fs_phase4_chief_engineer", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (fs_phase4_chief_engineer_condition_hasAlreadyUnlockedBranch(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_199cece6");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (fs_phase4_chief_engineer_condition_onQuestStep06(player, self))
        {
            doAnimationAction(self, "beckon");
            string_id message = new string_id(c_stringFile, "s_64391455");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (fs_phase4_chief_engineer_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (fs_phase4_chief_engineer_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_121666b3");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_4294034a");
                }
                setObjVar(player, "conversation.fs_phase4_chief_engineer.branchId", 4);
                npcStartConversation(player, self, "fs_phase4_chief_engineer", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (fs_phase4_chief_engineer_condition_isOnQuest01to05(player, self))
        {
            doAnimationAction(self, "stamp_feet");
            string_id message = new string_id(c_stringFile, "s_6c741657");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (fs_phase4_chief_engineer_condition_onQuestStep01(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (fs_phase4_chief_engineer_condition_onQuestStep02(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (fs_phase4_chief_engineer_condition_onQuestStep03(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (fs_phase4_chief_engineer_condition_onQuestStep04(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse3 = true;
            }
            boolean hasResponse4 = false;
            if (fs_phase4_chief_engineer_condition_onQuestStep05(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse4 = true;
            }
            boolean hasResponse5 = false;
            if (fs_phase4_chief_engineer_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse5 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_a2e520b8");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_c7baf80c");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_bab2829b");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_7496bddd");
                }
                if (hasResponse4)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_e3e36f42");
                }
                if (hasResponse5)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_eac5593c");
                }
                setObjVar(player, "conversation.fs_phase4_chief_engineer.branchId", 10);
                npcStartConversation(player, self, "fs_phase4_chief_engineer", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (fs_phase4_chief_engineer_condition_isBeginQuestEligible(player, self))
        {
            doAnimationAction(self, "gesticulate_wildly");
            string_id message = new string_id(c_stringFile, "s_cbc96768");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (fs_phase4_chief_engineer_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (fs_phase4_chief_engineer_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_59f5cee6");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_5542b04b");
                }
                setObjVar(player, "conversation.fs_phase4_chief_engineer.branchId", 17);
                npcStartConversation(player, self, "fs_phase4_chief_engineer", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (fs_phase4_chief_engineer_condition_isContinuingQuest(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_5062e892");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (fs_phase4_chief_engineer_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (fs_phase4_chief_engineer_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_ff872480");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_a69082f1");
                }
                setObjVar(player, "conversation.fs_phase4_chief_engineer.branchId", 31);
                npcStartConversation(player, self, "fs_phase4_chief_engineer", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (fs_phase4_chief_engineer_condition__defaultCondition(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_acbab09c");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("fs_phase4_chief_engineer"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.fs_phase4_chief_engineer.branchId");
        if (branchId == 2 && response.equals("s_6516bd5a"))
        {
            if (fs_phase4_chief_engineer_condition_noInventorySpace(player, self))
            {
                fs_phase4_chief_engineer_action_setQuestStep07(player, self);
                string_id message = new string_id(c_stringFile, "s_bce29099");
                removeObjVar(player, "conversation.fs_phase4_chief_engineer.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (fs_phase4_chief_engineer_condition__defaultCondition(player, self))
            {
                fs_phase4_chief_engineer_action_giveStatueSection(player, self);
                string_id message = new string_id(c_stringFile, "s_8b482724");
                removeObjVar(player, "conversation.fs_phase4_chief_engineer.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Yes, hello. You've returned the rest of your reward?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && response.equals("s_121666b3"))
        {
            doAnimationAction(player, "nod_head_multiple");
            if (fs_phase4_chief_engineer_condition_hasCoreObject(player, self))
            {
                doAnimationAction(self, "celebrate1");
                fs_phase4_chief_engineer_action_openSkillBranch(player, self);
                string_id message = new string_id(c_stringFile, "s_860baf7e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (fs_phase4_chief_engineer_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4136c2df");
                    }
                    setObjVar(player, "conversation.fs_phase4_chief_engineer.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.fs_phase4_chief_engineer.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            if (!fs_phase4_chief_engineer_condition_hasCoreObject(player, self))
            {
                doAnimationAction(self, "rub_chin_thoughtful");
                string_id message = new string_id(c_stringFile, "s_50191de0");
                removeObjVar(player, "conversation.fs_phase4_chief_engineer.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'It's you... good. Did you find a replacement computer core? Have you configured it so that it will be compatible with our computers?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && response.equals("s_4294034a"))
        {
            if (fs_phase4_chief_engineer_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "sigh_deeply");
                string_id message = new string_id(c_stringFile, "s_742ff942");
                removeObjVar(player, "conversation.fs_phase4_chief_engineer.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'It's you... good. Did you find a replacement computer core? Have you configured it so that it will be compatible with our computers?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_4136c2df"))
        {
            if (fs_phase4_chief_engineer_condition_noInventorySpace(player, self))
            {
                fs_phase4_chief_engineer_action_setQuestStep07(player, self);
                string_id message = new string_id(c_stringFile, "s_bce29099");
                removeObjVar(player, "conversation.fs_phase4_chief_engineer.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (fs_phase4_chief_engineer_condition__defaultCondition(player, self))
            {
                fs_phase4_chief_engineer_action_giveStatueSection(player, self);
                string_id message = new string_id(c_stringFile, "s_8b482724");
                removeObjVar(player, "conversation.fs_phase4_chief_engineer.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Yes! Thank you! This will work perfectly. In exchange for your services, I will happily get you started on the road to the Mastery of Crafting Repair.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && response.equals("s_a2e520b8"))
        {
            if (fs_phase4_chief_engineer_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_29622c26");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (fs_phase4_chief_engineer_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (fs_phase4_chief_engineer_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (fs_phase4_chief_engineer_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (fs_phase4_chief_engineer_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (fs_phase4_chief_engineer_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f75c2f80");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_59f634d7");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7f1e8a0c");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_69b44245");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_174ddced");
                    }
                    setObjVar(player, "conversation.fs_phase4_chief_engineer.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.fs_phase4_chief_engineer.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'No, no, no... it'll have to wait. ' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && response.equals("s_c7baf80c"))
        {
            if (fs_phase4_chief_engineer_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_4bcdc402");
                removeObjVar(player, "conversation.fs_phase4_chief_engineer.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'No, no, no... it'll have to wait. ' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && response.equals("s_bab2829b"))
        {
            if (fs_phase4_chief_engineer_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_65f76975");
                removeObjVar(player, "conversation.fs_phase4_chief_engineer.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'No, no, no... it'll have to wait. ' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && response.equals("s_7496bddd"))
        {
            if (fs_phase4_chief_engineer_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_f9732f00");
                removeObjVar(player, "conversation.fs_phase4_chief_engineer.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'No, no, no... it'll have to wait. ' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && response.equals("s_e3e36f42"))
        {
            if (fs_phase4_chief_engineer_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_8ef5f872");
                removeObjVar(player, "conversation.fs_phase4_chief_engineer.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'No, no, no... it'll have to wait. ' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && response.equals("s_eac5593c"))
        {
            if (fs_phase4_chief_engineer_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_29e5661");
                removeObjVar(player, "conversation.fs_phase4_chief_engineer.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'No, no, no... it'll have to wait. ' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && response.equals("s_f75c2f80"))
        {
            if (fs_phase4_chief_engineer_condition__defaultCondition(player, self))
            {
                fs_phase4_chief_engineer_action_giveKerenWaypoint(player, self);
                string_id message = new string_id(c_stringFile, "s_dd8b435");
                removeObjVar(player, "conversation.fs_phase4_chief_engineer.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'There's one in Keren on Naboo, on in Mos Entha on Tatooine, and one in Coronet on Corellia. I can upload a waypoint to their location if you'd like?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && response.equals("s_59f634d7"))
        {
            if (fs_phase4_chief_engineer_condition__defaultCondition(player, self))
            {
                fs_phase4_chief_engineer_action_giveCoronetWaypoint(player, self);
                string_id message = new string_id(c_stringFile, "s_dd8b435");
                removeObjVar(player, "conversation.fs_phase4_chief_engineer.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'There's one in Keren on Naboo, on in Mos Entha on Tatooine, and one in Coronet on Corellia. I can upload a waypoint to their location if you'd like?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && response.equals("s_7f1e8a0c"))
        {
            if (fs_phase4_chief_engineer_condition__defaultCondition(player, self))
            {
                fs_phase4_chief_engineer_action_giveMosEnthaWaypoint(player, self);
                string_id message = new string_id(c_stringFile, "s_dd8b435");
                removeObjVar(player, "conversation.fs_phase4_chief_engineer.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'There's one in Keren on Naboo, on in Mos Entha on Tatooine, and one in Coronet on Corellia. I can upload a waypoint to their location if you'd like?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && response.equals("s_69b44245"))
        {
            if (fs_phase4_chief_engineer_condition__defaultCondition(player, self))
            {
                fs_phase4_chief_engineer_action_activateQuest(player, self);
                string_id message = new string_id(c_stringFile, "s_d38355d4");
                removeObjVar(player, "conversation.fs_phase4_chief_engineer.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'There's one in Keren on Naboo, on in Mos Entha on Tatooine, and one in Coronet on Corellia. I can upload a waypoint to their location if you'd like?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && response.equals("s_174ddced"))
        {
            if (fs_phase4_chief_engineer_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_9c2a9f48");
                removeObjVar(player, "conversation.fs_phase4_chief_engineer.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'There's one in Keren on Naboo, on in Mos Entha on Tatooine, and one in Coronet on Corellia. I can upload a waypoint to their location if you'd like?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && response.equals("s_59f5cee6"))
        {
            if (fs_phase4_chief_engineer_condition_alreadyHasOtherQuest(player, self))
            {
                doAnimationAction(self, "shake_head_no");
                string_id message = new string_id(c_stringFile, "s_9caa26f9");
                removeObjVar(player, "conversation.fs_phase4_chief_engineer.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (fs_phase4_chief_engineer_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_1a960799");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (fs_phase4_chief_engineer_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_61fa9d29");
                    }
                    setObjVar(player, "conversation.fs_phase4_chief_engineer.branchId", 19);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.fs_phase4_chief_engineer.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Arrrgh! It's too much! I'm only one person... and new to this position no less. How can I be expected to keep the village defenses working while we're under attack? It's too much!!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && response.equals("s_5542b04b"))
        {
            if (fs_phase4_chief_engineer_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "wave_on_dismissing");
                string_id message = new string_id(c_stringFile, "s_caa53683");
                removeObjVar(player, "conversation.fs_phase4_chief_engineer.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Arrrgh! It's too much! I'm only one person... and new to this position no less. How can I be expected to keep the village defenses working while we're under attack? It's too much!!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && response.equals("s_61fa9d29"))
        {
            if (fs_phase4_chief_engineer_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "rub_chin_thoughtful");
                string_id message = new string_id(c_stringFile, "s_bf01c6a4");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (fs_phase4_chief_engineer_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (fs_phase4_chief_engineer_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8e1d9e8a");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_94925b50");
                    }
                    setObjVar(player, "conversation.fs_phase4_chief_engineer.branchId", 20);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.fs_phase4_chief_engineer.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Yes. Yes there is. During the first of the recent attacks on the village, our primary computer was damaged, and the computer core has become unstable. A new core must be found as soon as possible. Thanks to all the damage being caused by the attacks, I am unable go find a replacement, but you could go get one in my place.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && response.equals("s_8e1d9e8a"))
        {
            if (fs_phase4_chief_engineer_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "explain");
                string_id message = new string_id(c_stringFile, "s_e0f6c467");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (fs_phase4_chief_engineer_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (fs_phase4_chief_engineer_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7f1492c");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_85cf8e18");
                    }
                    setObjVar(player, "conversation.fs_phase4_chief_engineer.branchId", 21);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.fs_phase4_chief_engineer.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Well, I know some people who might be able to help you find one. You'd need to contact one of them and see what they have to say. From there, both they and I can advise you.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && response.equals("s_94925b50"))
        {
            if (fs_phase4_chief_engineer_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "pose_proudly");
                string_id message = new string_id(c_stringFile, "s_3196d2e0");
                removeObjVar(player, "conversation.fs_phase4_chief_engineer.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Well, I know some people who might be able to help you find one. You'd need to contact one of them and see what they have to say. From there, both they and I can advise you.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && response.equals("s_7f1492c"))
        {
            if (fs_phase4_chief_engineer_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_b69a10cf");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (fs_phase4_chief_engineer_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (fs_phase4_chief_engineer_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (fs_phase4_chief_engineer_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (fs_phase4_chief_engineer_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (fs_phase4_chief_engineer_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f75c2f80");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_59f634d7");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7f1e8a0c");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_69b44245");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_174ddced");
                    }
                    setObjVar(player, "conversation.fs_phase4_chief_engineer.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.fs_phase4_chief_engineer.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Ah enthusiasm... I like that. ' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && response.equals("s_85cf8e18"))
        {
            if (fs_phase4_chief_engineer_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_dc34bd9c");
                removeObjVar(player, "conversation.fs_phase4_chief_engineer.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Ah enthusiasm... I like that. ' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && response.equals("s_f75c2f80"))
        {
            if (fs_phase4_chief_engineer_condition__defaultCondition(player, self))
            {
                fs_phase4_chief_engineer_action_giveKerenWaypoint(player, self);
                string_id message = new string_id(c_stringFile, "s_dd8b435");
                removeObjVar(player, "conversation.fs_phase4_chief_engineer.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I'd recommend speaking to a gadget specialist. I've done business with a gadget specialist in the past and know where you can find one. Actually, I know of three of them. One in Keren on Naboo, on in Mos Entha on Tatooine, and one in Coronet on Corellia. I can upload a waypoint to their location if you'd like?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && response.equals("s_59f634d7"))
        {
            if (fs_phase4_chief_engineer_condition__defaultCondition(player, self))
            {
                fs_phase4_chief_engineer_action_giveCoronetWaypoint(player, self);
                string_id message = new string_id(c_stringFile, "s_dd8b435");
                removeObjVar(player, "conversation.fs_phase4_chief_engineer.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I'd recommend speaking to a gadget specialist. I've done business with a gadget specialist in the past and know where you can find one. Actually, I know of three of them. One in Keren on Naboo, on in Mos Entha on Tatooine, and one in Coronet on Corellia. I can upload a waypoint to their location if you'd like?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && response.equals("s_7f1e8a0c"))
        {
            if (fs_phase4_chief_engineer_condition__defaultCondition(player, self))
            {
                fs_phase4_chief_engineer_action_giveMosEnthaWaypoint(player, self);
                string_id message = new string_id(c_stringFile, "s_dd8b435");
                removeObjVar(player, "conversation.fs_phase4_chief_engineer.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I'd recommend speaking to a gadget specialist. I've done business with a gadget specialist in the past and know where you can find one. Actually, I know of three of them. One in Keren on Naboo, on in Mos Entha on Tatooine, and one in Coronet on Corellia. I can upload a waypoint to their location if you'd like?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && response.equals("s_69b44245"))
        {
            if (fs_phase4_chief_engineer_condition__defaultCondition(player, self))
            {
                fs_phase4_chief_engineer_action_activateQuest(player, self);
                string_id message = new string_id(c_stringFile, "s_d38355d4");
                removeObjVar(player, "conversation.fs_phase4_chief_engineer.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I'd recommend speaking to a gadget specialist. I've done business with a gadget specialist in the past and know where you can find one. Actually, I know of three of them. One in Keren on Naboo, on in Mos Entha on Tatooine, and one in Coronet on Corellia. I can upload a waypoint to their location if you'd like?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && response.equals("s_174ddced"))
        {
            if (fs_phase4_chief_engineer_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_9c2a9f48");
                removeObjVar(player, "conversation.fs_phase4_chief_engineer.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I'd recommend speaking to a gadget specialist. I've done business with a gadget specialist in the past and know where you can find one. Actually, I know of three of them. One in Keren on Naboo, on in Mos Entha on Tatooine, and one in Coronet on Corellia. I can upload a waypoint to their location if you'd like?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 31 && response.equals("s_ff872480"))
        {
            if (fs_phase4_chief_engineer_condition__defaultCondition(player, self))
            {
                fs_phase4_chief_engineer_action_setFSQuestAccepted(player, self);
                string_id message = new string_id(c_stringFile, "s_3031b4c9");
                removeObjVar(player, "conversation.fs_phase4_chief_engineer.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You worked with the last chief engineer, right? But weren't able to find a replacement in time. Would you like to try again? Continue from where you left off?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 31 && response.equals("s_a69082f1"))
        {
            if (fs_phase4_chief_engineer_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_96e10fe0");
                removeObjVar(player, "conversation.fs_phase4_chief_engineer.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You worked with the last chief engineer, right? But weren't able to find a replacement in time. Would you like to try again? Continue from where you left off?' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.fs_phase4_chief_engineer.branchId");
        return SCRIPT_CONTINUE;
    }
}
