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
import script.library.utils;

public class ep3_trandoshan_mssikss extends script.base_script
{
    public ep3_trandoshan_mssikss()
    {
    }
    public static String c_stringFile = "conversation/ep3_trandoshan_mssikss";
    public boolean ep3_trandoshan_mssikss_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean ep3_trandoshan_mssikss_condition_canTakeSpaceMission(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasSkill(player, "pilot_rebel_navy_novice") || (hasSkill(player, "pilot_imperial_navy_novice") || (hasSkill(player, "pilot_neutral_novice"))))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean ep3_trandoshan_mssikss_condition_hasFailedMission(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasFailedQuestRecursive(player, "recovery", "ep3_trando_mssikss") || space_quest.hasAbortedQuestRecursive(player, "recovery", "ep3_trando_mssikss"))
        {
            return true;
        }
        return false;
    }
    public boolean ep3_trandoshan_mssikss_condition_receivedReward(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasReceivedReward(player, "recovery", "ep3_trando_mssikss"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean ep3_trandoshan_mssikss_condition_hasWonMission(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasWonQuestRecursive(player, "recovery", "ep3_trando_mssikss"));
    }
    public boolean ep3_trandoshan_mssikss_condition_isOnMission(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasQuest(player));
    }
    public void ep3_trandoshan_mssikss_action_grantMission(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "recovery", "ep3_trando_mssikss");
    }
    public void ep3_trandoshan_mssikss_action_grantReward(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.giveReward(player, "recovery", "ep3_trando_mssikss", 10000, "object/tangible/ship/components/weapon/wpn_trandoshan_fg1a.iff");
    }
    public int ep3_trandoshan_mssikss_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1121"))
        {
            if (ep3_trandoshan_mssikss_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "laugh_titter");
                ep3_trandoshan_mssikss_action_grantReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_1123");
                utils.removeScriptVar(player, "conversation.ep3_trandoshan_mssikss.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_trandoshan_mssikss_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1129"))
        {
            if (ep3_trandoshan_mssikss_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                ep3_trandoshan_mssikss_action_grantMission(player, npc);
                string_id message = new string_id(c_stringFile, "s_1131");
                utils.removeScriptVar(player, "conversation.ep3_trandoshan_mssikss.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1133"))
        {
            if (ep3_trandoshan_mssikss_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wave_on_dismissing");
                string_id message = new string_id(c_stringFile, "s_1135");
                utils.removeScriptVar(player, "conversation.ep3_trandoshan_mssikss.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_trandoshan_mssikss_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1139"))
        {
            if (ep3_trandoshan_mssikss_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_1141");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_trandoshan_mssikss_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1143");
                    }
                    utils.setScriptVar(player, "conversation.ep3_trandoshan_mssikss.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_trandoshan_mssikss.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1159"))
        {
            if (ep3_trandoshan_mssikss_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wave_on_dismissing");
                string_id message = new string_id(c_stringFile, "s_1161");
                utils.removeScriptVar(player, "conversation.ep3_trandoshan_mssikss.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_trandoshan_mssikss_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1143"))
        {
            if (ep3_trandoshan_mssikss_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "gesticulate_wildly");
                string_id message = new string_id(c_stringFile, "s_1145");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_trandoshan_mssikss_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_trandoshan_mssikss_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1147");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1155");
                    }
                    utils.setScriptVar(player, "conversation.ep3_trandoshan_mssikss.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_trandoshan_mssikss.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_trandoshan_mssikss_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1147"))
        {
            if (ep3_trandoshan_mssikss_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_1149");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_trandoshan_mssikss_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1151");
                    }
                    utils.setScriptVar(player, "conversation.ep3_trandoshan_mssikss.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_trandoshan_mssikss.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1155"))
        {
            if (ep3_trandoshan_mssikss_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wave_on_dismissing");
                string_id message = new string_id(c_stringFile, "s_1157");
                utils.removeScriptVar(player, "conversation.ep3_trandoshan_mssikss.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_trandoshan_mssikss_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1151"))
        {
            if (ep3_trandoshan_mssikss_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "goodbye");
                ep3_trandoshan_mssikss_action_grantMission(player, npc);
                string_id message = new string_id(c_stringFile, "s_1153");
                utils.removeScriptVar(player, "conversation.ep3_trandoshan_mssikss.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.ep3_trandoshan_mssikss");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_SPACE_INTERESTING);
        setName(self, "Mssikss");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_SPACE_INTERESTING);
        setName(self, "Mssikss");
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info menuInfo) throws InterruptedException
    {
        int menu = menuInfo.addRootMenu(menu_info_types.CONVERSE_START, null);
        menu_info_data menuInfoData = menuInfo.getMenuItemById(menu);
        menuInfoData.setServerNotify(false);
        setCondition(self, CONDITION_CONVERSABLE);
        faceTo(self, player);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        detachScript(self, "conversation.ep3_trandoshan_mssikss");
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
        if (ep3_trandoshan_mssikss_condition_receivedReward(player, npc))
        {
            doAnimationAction(npc, "sigh_deeply");
            string_id message = new string_id(c_stringFile, "s_1117");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_trandoshan_mssikss_condition_hasWonMission(player, npc))
        {
            doAnimationAction(npc, "celebrate1");
            string_id message = new string_id(c_stringFile, "s_1119");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_trandoshan_mssikss_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1121");
                }
                utils.setScriptVar(player, "conversation.ep3_trandoshan_mssikss.branchId", 2);
                npcStartConversation(player, npc, "ep3_trandoshan_mssikss", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_trandoshan_mssikss_condition_isOnMission(player, npc))
        {
            doAnimationAction(npc, "explain");
            string_id message = new string_id(c_stringFile, "s_1125");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_trandoshan_mssikss_condition_hasFailedMission(player, npc))
        {
            doAnimationAction(npc, "point_accusingly");
            string_id message = new string_id(c_stringFile, "s_1127");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_trandoshan_mssikss_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_trandoshan_mssikss_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1129");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1133");
                }
                utils.setScriptVar(player, "conversation.ep3_trandoshan_mssikss.branchId", 5);
                npcStartConversation(player, npc, "ep3_trandoshan_mssikss", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_trandoshan_mssikss_condition_canTakeSpaceMission(player, npc))
        {
            doAnimationAction(npc, "point_away");
            string_id message = new string_id(c_stringFile, "s_1137");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_trandoshan_mssikss_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_trandoshan_mssikss_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1139");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1159");
                }
                utils.setScriptVar(player, "conversation.ep3_trandoshan_mssikss.branchId", 8);
                npcStartConversation(player, npc, "ep3_trandoshan_mssikss", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_trandoshan_mssikss_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "wave_on_dismissing");
            string_id message = new string_id(c_stringFile, "s_1163");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("ep3_trandoshan_mssikss"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.ep3_trandoshan_mssikss.branchId");
        if (branchId == 2 && ep3_trandoshan_mssikss_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && ep3_trandoshan_mssikss_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && ep3_trandoshan_mssikss_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && ep3_trandoshan_mssikss_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && ep3_trandoshan_mssikss_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && ep3_trandoshan_mssikss_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.ep3_trandoshan_mssikss.branchId");
        return SCRIPT_CONTINUE;
    }
}
