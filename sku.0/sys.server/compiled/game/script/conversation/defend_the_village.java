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

public class defend_the_village extends script.base_script
{
    public defend_the_village()
    {
    }
    public static String c_stringFile = "conversation/defend_the_village";
    public boolean defend_the_village_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean defend_the_village_condition_percentComplete50(obj_id player, obj_id npc) throws InterruptedException
    {
        boolean result = false;
        if (defend_the_village_condition_isOnRangedSpeedQuest(player, npc))
        {
            if (getPercentComplete(player, "fs_defend_01") + getPercentComplete(player, "fs_defend_02") >= 0.5f)
            {
                result = true;
            }
        }
        else if (defend_the_village_condition_isOnMeleeDefenseQuest(player, npc))
        {
            if (getPercentComplete(player, "fs_defend_03") + getPercentComplete(player, "fs_defend_04") >= 0.5f)
            {
                result = true;
            }
        }
        return result;
    }
    public boolean defend_the_village_condition_canAcceptEitherQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return (defend_the_village_condition_canAcceptMeleeDefenseQuest(player, npc) || defend_the_village_condition_canAcceptRangedSpeedQuest(player, npc));
    }
    public boolean defend_the_village_condition_isOnRangedSpeedQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        boolean result = false;
        if (quests.isActive("fs_defend_wait_01", player))
        {
            result = true;
        }
        return result;
    }
    public boolean defend_the_village_condition_isOnEitherQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return (defend_the_village_condition_isOnMeleeDefenseQuest(player, npc) || defend_the_village_condition_isOnRangedSpeedQuest(player, npc));
    }
    public boolean defend_the_village_condition_isOnMeleeDefenseQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        boolean result = false;
        if (quests.isActive("fs_defend_wait_02", player))
        {
            result = true;
        }
        return result;
    }
    public boolean defend_the_village_condition_hasCompletedQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        boolean result = false;
        if (quests.isComplete("fs_defend_wait_01", player) && quests.isComplete("fs_defend_wait_02", player))
        {
            result = true;
        }
        if ((quests.isComplete("fs_defend_wait_01", player) || quests.isComplete("fs_defend_wait_02", player)) && !defend_the_village_condition_isOnEitherQuest(player, npc) && !defend_the_village_condition_canAcceptEitherQuest(player, npc))
        {
            result = true;
        }
        return result;
    }
    public boolean defend_the_village_condition_canAcceptMeleeDefenseQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        boolean result = false;
        if (quests.canActivate("fs_defend_set_faction_02", player) && !fs_quests.hasQuestAccepted(player))
        {
            result = true;
        }
        return result;
    }
    public boolean defend_the_village_condition_canAcceptRangedSpeedQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        boolean result = false;
        if (quests.canActivate("fs_defend_set_faction", player) && !fs_quests.hasQuestAccepted(player))
        {
            result = true;
        }
        return result;
    }
    public void defend_the_village_action_assignDefendTheVillage1(obj_id player, obj_id npc) throws InterruptedException
    {
        attachScript(player, "quest.force_sensitive.fs_defend_01_cleanup");
        quests.activate("fs_defend_set_faction", player, npc);
        fs_quests.setQuestAccepted(player);
    }
    public void defend_the_village_action_assignDefendTheVillage2(obj_id player, obj_id npc) throws InterruptedException
    {
        attachScript(player, "quest.force_sensitive.fs_defend_01_cleanup");
        quests.activate("fs_defend_set_faction_02", player, npc);
        fs_quests.setQuestAccepted(player);
    }
    public String defend_the_village_tokenTO_delete_me(obj_id player, obj_id npc) throws InterruptedException
    {
        return new String();
    }
    public int defend_the_village_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6e8bed18"))
        {
            if (defend_the_village_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_23");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (defend_the_village_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_25");
                    }
                    utils.setScriptVar(player, "conversation.defend_the_village.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.defend_the_village.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_95fe56b2"))
        {
            if (defend_the_village_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_cb3652f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (defend_the_village_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c382b2d0");
                    }
                    utils.setScriptVar(player, "conversation.defend_the_village.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.defend_the_village.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_64cad97c"))
        {
            if (defend_the_village_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_918a48b8");
                utils.removeScriptVar(player, "conversation.defend_the_village.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int defend_the_village_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_25"))
        {
            if (defend_the_village_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_27");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (defend_the_village_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (defend_the_village_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_29");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_33");
                    }
                    utils.setScriptVar(player, "conversation.defend_the_village.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.defend_the_village.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int defend_the_village_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_29"))
        {
            if (defend_the_village_condition__defaultCondition(player, npc))
            {
                defend_the_village_action_assignDefendTheVillage1(player, npc);
                string_id message = new string_id(c_stringFile, "s_31");
                utils.removeScriptVar(player, "conversation.defend_the_village.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_33"))
        {
            if (defend_the_village_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_35");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (defend_the_village_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (defend_the_village_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_37");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_41");
                    }
                    utils.setScriptVar(player, "conversation.defend_the_village.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.defend_the_village.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int defend_the_village_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_37"))
        {
            if (defend_the_village_condition__defaultCondition(player, npc))
            {
                defend_the_village_action_assignDefendTheVillage1(player, npc);
                string_id message = new string_id(c_stringFile, "s_39");
                utils.removeScriptVar(player, "conversation.defend_the_village.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_41"))
        {
            if (defend_the_village_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_43");
                utils.removeScriptVar(player, "conversation.defend_the_village.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int defend_the_village_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c382b2d0"))
        {
            if (defend_the_village_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_ee72ef65");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (defend_the_village_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (defend_the_village_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a30c1a53");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ed6ed8a4");
                    }
                    utils.setScriptVar(player, "conversation.defend_the_village.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.defend_the_village.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int defend_the_village_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_a30c1a53"))
        {
            if (defend_the_village_condition__defaultCondition(player, npc))
            {
                defend_the_village_action_assignDefendTheVillage2(player, npc);
                string_id message = new string_id(c_stringFile, "s_db99c818");
                utils.removeScriptVar(player, "conversation.defend_the_village.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_ed6ed8a4"))
        {
            if (defend_the_village_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_2647addd");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (defend_the_village_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (defend_the_village_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f9a0517");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2b2d70cd");
                    }
                    utils.setScriptVar(player, "conversation.defend_the_village.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.defend_the_village.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int defend_the_village_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_f9a0517"))
        {
            if (defend_the_village_condition__defaultCondition(player, npc))
            {
                defend_the_village_action_assignDefendTheVillage2(player, npc);
                string_id message = new string_id(c_stringFile, "s_212d92b3");
                utils.removeScriptVar(player, "conversation.defend_the_village.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_2b2d70cd"))
        {
            if (defend_the_village_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_982a066f");
                utils.removeScriptVar(player, "conversation.defend_the_village.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public float getPercentComplete(obj_id self, String questName) throws InterruptedException
    {
        float result = 0.0f;
        String objvarName = "quest." + questName + ".parameter";
        if (hasObjVar(self, objvarName))
        {
            int killsRemaining = getIntObjVar(self, objvarName);
            if (killsRemaining > 0)
            {
                String totalKillsString = quests.getDataEntry(questName, "PARAMETER");
                int totalKills = utils.stringToInt(totalKillsString);
                if (totalKills > 0)
                {
                    result = 1 - (float)(killsRemaining / totalKills);
                }
            }
            else 
            {
                result = 1.0f;
            }
        }
        return result;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.defend_the_village");
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
        detachScript(self, "conversation.defend_the_village");
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
        if (defend_the_village_condition_hasCompletedQuest(player, npc))
        {
            doAnimationAction(npc, "salute1");
            string_id message = new string_id(c_stringFile, "s_a2665c7f");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (defend_the_village_condition_percentComplete50(player, npc))
        {
            doAnimationAction(npc, "thumb_up");
            string_id message = new string_id(c_stringFile, "s_1a9b1371");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (defend_the_village_condition_isOnEitherQuest(player, npc))
        {
            doAnimationAction(npc, "pound_fist_palm");
            string_id message = new string_id(c_stringFile, "s_c9be571d");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (defend_the_village_condition_canAcceptEitherQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_d2fe23af");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (defend_the_village_condition_canAcceptRangedSpeedQuest(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (defend_the_village_condition_canAcceptMeleeDefenseQuest(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (defend_the_village_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_6e8bed18");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_95fe56b2");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_64cad97c");
                }
                utils.setScriptVar(player, "conversation.defend_the_village.branchId", 4);
                npcStartConversation(player, npc, "defend_the_village", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (defend_the_village_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_41ea5583");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("defend_the_village"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.defend_the_village.branchId");
        if (branchId == 4 && defend_the_village_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && defend_the_village_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && defend_the_village_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && defend_the_village_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && defend_the_village_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && defend_the_village_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && defend_the_village_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.defend_the_village.branchId");
        return SCRIPT_CONTINUE;
    }
}
