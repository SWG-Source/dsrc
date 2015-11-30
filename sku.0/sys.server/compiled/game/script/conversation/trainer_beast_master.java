package script.conversation;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.beast_lib;
import script.library.chat;
import script.library.money;
import script.library.utils;

public class trainer_beast_master extends script.base_script
{
    public trainer_beast_master()
    {
    }
    public static String c_stringFile = "conversation/trainer_beast_master";
    public boolean trainer_beast_master_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean trainer_beast_master_condition_canLearnNextProvoke(obj_id player, obj_id npc) throws InterruptedException
    {
        String bestProvoke = beast_lib.getBestBeastMasterSkill(player, "bm_provoke_1");
        if (bestProvoke.equals("bm_provoke_5"))
        {
            return false;
        }
        int playerLevel = getLevel(player);
        if (playerLevel > 72 && !bestProvoke.equals("bm_provoke_5"))
        {
            return true;
        }
        if (playerLevel > 54 && !bestProvoke.equals("bm_provoke_4"))
        {
            return true;
        }
        if (playerLevel > 36 && !bestProvoke.equals("bm_provoke_3"))
        {
            return true;
        }
        if (playerLevel > 18 && !bestProvoke.equals("bm_provoke_2"))
        {
            return true;
        }
        return false;
    }
    public boolean trainer_beast_master_condition_hasActiveBeast(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id activePet = beast_lib.getBeastOnPlayer(player);
        return (beast_lib.isBeastMaster(player) && isIdValid(activePet) && exists(activePet) && beast_lib.isBeast(activePet));
    }
    public boolean trainer_beast_master_condition_canPayFee(obj_id player, obj_id npc) throws InterruptedException
    {
        int balance = getTotalMoney(player);
        return 5000 <= balance;
    }
    public boolean trainer_beast_master_condition_knowsProvoke(obj_id player, obj_id npc) throws InterruptedException
    {
        String[] knownSkills = beast_lib.getKnownSkills(player);
        if (knownSkills == null || knownSkills.length == 0)
        {
            return false;
        }
        for (int i = 0; i < knownSkills.length; i++)
        {
            if (knownSkills[i].startsWith("bm_provoke"))
            {
                return true;
            }
        }
        return false;
    }
    public boolean trainer_beast_master_condition_hasSkillsToUnlearn(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id activePet = beast_lib.getBeastOnPlayer(player);
        if (isIdValid(activePet) && beast_lib.isBeast(activePet))
        {
            String[] trainedSkills = beast_lib.getTrainedSkills(activePet);
            for (int i = 0; i < trainedSkills.length; i++)
            {
                if (!trainedSkills[i].equals("") && !trainedSkills[i].equals("empty"))
                {
                    return true;
                }
            }
        }
        return false;
    }
    public void trainer_beast_master_action_clearPetSkills(obj_id player, obj_id npc) throws InterruptedException
    {
        money.requestPayment(player, money.ACCT_SKILL_TRAINING, 5000, "none", null, false);
        obj_id beast = beast_lib.getBeastOnPlayer(player);
        beast_lib.clearTrainedSkills(beast);
    }
    public void trainer_beast_master_action_teachNextProvoke(obj_id player, obj_id npc) throws InterruptedException
    {
        String current_provoke = beast_lib.getBestBeastMasterSkill(player, "bm_provoke_1");
        String nextProvoke = beast_lib.getNextSkillInLine(current_provoke);
        beast_lib.playerLearnBeastMasterSkill(player, nextProvoke, true);
    }
    public void trainer_beast_master_action_teachFirstProvoke(obj_id player, obj_id npc) throws InterruptedException
    {
        beast_lib.playerLearnBeastMasterSkill(player, "bm_provoke_1");
    }
    public int trainer_beast_master_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6"))
        {
            if (trainer_beast_master_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_8");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (trainer_beast_master_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (trainer_beast_master_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_10");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_16");
                    }
                    utils.setScriptVar(player, "conversation.trainer_beast_master.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.trainer_beast_master.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_20"))
        {
            if (trainer_beast_master_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_22");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (trainer_beast_master_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_24");
                    }
                    utils.setScriptVar(player, "conversation.trainer_beast_master.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.trainer_beast_master.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_28"))
        {
            if (!trainer_beast_master_condition_canLearnNextProvoke(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_30");
                utils.removeScriptVar(player, "conversation.trainer_beast_master.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (trainer_beast_master_condition__defaultCondition(player, npc))
            {
                trainer_beast_master_action_teachNextProvoke(player, npc);
                string_id message = new string_id(c_stringFile, "s_32");
                utils.removeScriptVar(player, "conversation.trainer_beast_master.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_35"))
        {
            if (trainer_beast_master_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_36");
                utils.removeScriptVar(player, "conversation.trainer_beast_master.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int trainer_beast_master_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_10"))
        {
            if (!trainer_beast_master_condition_canPayFee(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_12");
                utils.removeScriptVar(player, "conversation.trainer_beast_master.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (!trainer_beast_master_condition_hasActiveBeast(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_39");
                utils.removeScriptVar(player, "conversation.trainer_beast_master.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (trainer_beast_master_condition__defaultCondition(player, npc))
            {
                trainer_beast_master_action_clearPetSkills(player, npc);
                string_id message = new string_id(c_stringFile, "s_14");
                utils.removeScriptVar(player, "conversation.trainer_beast_master.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_16"))
        {
            if (trainer_beast_master_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_18");
                utils.removeScriptVar(player, "conversation.trainer_beast_master.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int trainer_beast_master_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_24"))
        {
            if (trainer_beast_master_condition__defaultCondition(player, npc))
            {
                trainer_beast_master_action_teachFirstProvoke(player, npc);
                string_id message = new string_id(c_stringFile, "s_26");
                utils.removeScriptVar(player, "conversation.trainer_beast_master.branchId");
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
            detachScript(self, "conversation.trainer_beast_master");
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
        detachScript(self, "conversation.trainer_beast_master");
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
        if (trainer_beast_master_condition_hasActiveBeast(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_4");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (trainer_beast_master_condition_hasSkillsToUnlearn(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (!trainer_beast_master_condition_knowsProvoke(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (trainer_beast_master_condition_knowsProvoke(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (trainer_beast_master_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse3 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_6");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_20");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_28");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_35");
                }
                utils.setScriptVar(player, "conversation.trainer_beast_master.branchId", 1);
                npcStartConversation(player, npc, "trainer_beast_master", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (trainer_beast_master_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_38");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("trainer_beast_master"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.trainer_beast_master.branchId");
        if (branchId == 1 && trainer_beast_master_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && trainer_beast_master_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && trainer_beast_master_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.trainer_beast_master.branchId");
        return SCRIPT_CONTINUE;
    }
}
