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
import script.library.fs_dyn_village;
import script.library.fs_quests;
import script.library.quests;

public class village_quharek extends script.base_script
{
    public village_quharek()
    {
    }
    public static String c_stringFile = "conversation/village_quharek";
    public boolean village_quharek_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean village_quharek_condition_phase1_givequest(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id master = getObjIdObjVar(npc, "village_master");
        if (!isIdValid(master))
        {
            return false;
        }
        if (!fs_quests.isVillageEligible(player))
        {
            return false;
        }
        if (fs_quests.hasQuestAccepted(player))
        {
            return false;
        }
        int phase = fs_dyn_village.getCurrentPhaseAuth(master);
        if (phase != 1)
        {
            return false;
        }
        return true;
    }
    public boolean village_quharek_condition_phase1_inprogress(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id master = getObjIdObjVar(npc, "village_master");
        if (!isIdValid(master))
        {
            return false;
        }
        int phase = fs_dyn_village.getCurrentPhaseAuth(master);
        if (phase != 1)
        {
            return false;
        }
        if (hasObjVar(player, "quest.fs_crafting1.inprogress"))
        {
            return true;
        }
        return false;
    }
    public boolean village_quharek_condition_phase1_complete(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id master = getObjIdObjVar(npc, "village_master");
        if (!isIdValid(master))
        {
            return false;
        }
        int phase = fs_dyn_village.getCurrentPhaseAuth(master);
        if (phase != 1)
        {
            return false;
        }
        if (quests.isComplete("fs_craft_puzzle_quest_03", player))
        {
            return true;
        }
        return false;
    }
    public boolean village_quharek_condition_accepted_quest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (fs_quests.hasQuestAccepted(player))
        {
            return true;
        }
        return false;
    }
    public boolean village_quharek_condition_not_eligible(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!fs_quests.isVillageEligible(player))
        {
            return true;
        }
        return false;
    }
    public boolean village_quharek_condition_wrong_phase(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id master = getObjIdObjVar(npc, "village_master");
        if (!isIdValid(master))
        {
            return false;
        }
        int phase = fs_dyn_village.getCurrentPhaseAuth(master);
        if (phase != 1)
        {
            return true;
        }
        return false;
    }
    public boolean village_quharek_condition_completed_quest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (fs_quests.hasQuestCompleted(player))
        {
            return true;
        }
        return false;
    }
    public void village_quharek_action_phase1_activate(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id master = getObjIdObjVar(npc, "village_master");
        if (isIdValid(master))
        {
            setObjVar(player, "quest.fs_crafting1.master", master);
        }
        setObjVar(player, "quest.fs_crafting1.inprogress", 1);
        setObjVar(player, "quest.fs_craft_puzzle_quest_01.parameter", 4.0f);
        setObjVar(player, "quest.fs_craft_puzzle_quest_02.parameter", 7.0f);
        attachScript(player, "quest.force_sensitive.fs_crafting1_player");
        quests.activate("fs_craft_puzzle_quest_00", player, null);
        fs_quests.setQuestAccepted(player);
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.village_quharek");
        }
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        if (!fs_dyn_village.getRegisteredObjIdFromClusterWideData(fs_dyn_village.CLUSTER_OBJID_KEY_MASTER, "handleMasterIdResponse", self))
        {
            messageTo(self, "handleMasterIdRequestRetry", null, 120.0f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        if (!fs_dyn_village.getRegisteredObjIdFromClusterWideData(fs_dyn_village.CLUSTER_OBJID_KEY_MASTER, "handleMasterIdResponse", self))
        {
            messageTo(self, "handleMasterIdRequestRetry", null, 120.0f, false);
        }
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
        detachScript(self, "npc.conversation.village_quharek");
        return SCRIPT_CONTINUE;
    }
    public int handleMasterIdResponse(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id master = params.getObjId(fs_dyn_village.CLUSTER_OBJID_KEY_MASTER);
        if (isIdValid(master))
        {
            setObjVar(self, "village_master", master);
        }
        else 
        {
            messageTo(self, "handleMasterIdRequestRetry", null, 120.0f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleMasterIdRequestRetry(obj_id self, dictionary params) throws InterruptedException
    {
        if (!fs_dyn_village.getRegisteredObjIdFromClusterWideData(fs_dyn_village.CLUSTER_OBJID_KEY_MASTER, "handleMasterIdResponse", self))
        {
            messageTo(self, "handleMasterIdRequestRetry", null, 120.0f, false);
        }
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
        if (village_quharek_condition_not_eligible(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_fae10418");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (village_quharek_condition_phase1_complete(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_be3bc184");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (village_quharek_condition_phase1_inprogress(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_16cc0ae1");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (village_quharek_condition_phase1_givequest(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_ebabb0f8");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (village_quharek_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (village_quharek_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_4beb938f");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_f474ed78");
                }
                setObjVar(player, "conversation.village_quharek.branchId", 4);
                npcStartConversation(player, self, "village_quharek", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (village_quharek_condition_completed_quest(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_f1a421d");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (village_quharek_condition_accepted_quest(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_faf1fbe4");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (village_quharek_condition_wrong_phase(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_e05cc4d4");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (village_quharek_condition__defaultCondition(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_6a934b47");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("village_quharek"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.village_quharek.branchId");
        if (branchId == 4 && response.equals("s_4beb938f"))
        {
            if (village_quharek_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_b2dd509a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (village_quharek_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8abf86da");
                    }
                    setObjVar(player, "conversation.village_quharek.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.village_quharek.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You there! Are you handy with a set of tools?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && response.equals("s_f474ed78"))
        {
            if (village_quharek_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_5ec4a1e");
                removeObjVar(player, "conversation.village_quharek.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You there! Are you handy with a set of tools?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_8abf86da"))
        {
            if (village_quharek_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_9cdcc110");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (village_quharek_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (village_quharek_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9ae334e4");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c9f1dbdc");
                    }
                    setObjVar(player, "conversation.village_quharek.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.village_quharek.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Great. Our sensor array was damaged in the last attack on this village by the Sith Shadows. It needs to be repaired, but unfortunately, our chief engineer was also killed in the attack.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && response.equals("s_9ae334e4"))
        {
            if (village_quharek_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_27808b0b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (village_quharek_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (village_quharek_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8459377a");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_75cdc0ad");
                    }
                    setObjVar(player, "conversation.village_quharek.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.village_quharek.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'That's it exactly. You catch on quick. If you will, take a look at the damage, see what needs to be repaired, and fix it up for us.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && response.equals("s_c9f1dbdc"))
        {
            if (village_quharek_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_8d878ba7");
                removeObjVar(player, "conversation.village_quharek.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'That's it exactly. You catch on quick. If you will, take a look at the damage, see what needs to be repaired, and fix it up for us.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && response.equals("s_8459377a"))
        {
            if (village_quharek_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_e26f0607");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (village_quharek_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (village_quharek_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ac061a6f");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a4a9c8e0");
                    }
                    setObjVar(player, "conversation.village_quharek.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.village_quharek.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Good show! If you help us out with this, I will help you learn to use the Force to enhance your assembly skills.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && response.equals("s_75cdc0ad"))
        {
            if (village_quharek_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_8d878ba7");
                removeObjVar(player, "conversation.village_quharek.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Good show! If you help us out with this, I will help you learn to use the Force to enhance your assembly skills.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && response.equals("s_ac061a6f"))
        {
            if (village_quharek_condition__defaultCondition(player, self))
            {
                village_quharek_action_phase1_activate(player, self);
                string_id message = new string_id(c_stringFile, "s_1b7f8e6d");
                removeObjVar(player, "conversation.village_quharek.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Of course you understand that this job will take a while, and if you agree to fix this thing, you won't be able to help anyone else with anything for a while, right?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && response.equals("s_a4a9c8e0"))
        {
            if (village_quharek_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_52acc0");
                removeObjVar(player, "conversation.village_quharek.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Of course you understand that this job will take a while, and if you agree to fix this thing, you won't be able to help anyone else with anything for a while, right?' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.village_quharek.branchId");
        return SCRIPT_CONTINUE;
    }
}
