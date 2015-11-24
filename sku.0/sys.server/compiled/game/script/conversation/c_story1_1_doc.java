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
import script.library.groundquests;
import script.library.utils;

public class c_story1_1_doc extends script.base_script
{
    public c_story1_1_doc()
    {
    }
    public static String c_stringFile = "conversation/c_story1_1_doc";
    public boolean c_story1_1_doc_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean c_story1_1_doc_condition_atDocStep(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/c_story1_1_reb");
        int questId2 = questGetQuestId("quest/c_story1_1_neu");
        int questId3 = questGetQuestId("quest/c_story1_1_imp");
        int doctor1 = groundquests.getTaskId(questId1, "doctor");
        int doctor2 = groundquests.getTaskId(questId2, "doctor");
        int doctor3 = groundquests.getTaskId(questId3, "doctor");
        boolean onQuest = false;
        onQuest = questIsTaskActive(questId1, doctor1, player) || questIsTaskActive(questId2, doctor2, player) || questIsTaskActive(questId3, doctor3, player);
        return onQuest;
    }
    public boolean c_story1_1_doc_condition_afterDoctorStep(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/c_story1_1_reb");
        int questId2 = questGetQuestId("quest/c_story1_1_neu");
        int questId3 = questGetQuestId("quest/c_story1_1_imp");
        int doctor1 = groundquests.getTaskId(questId1, "doctor");
        int doctor2 = groundquests.getTaskId(questId2, "doctor");
        int doctor3 = groundquests.getTaskId(questId3, "doctor");
        boolean onQuest = false;
        onQuest = questIsTaskComplete(questId1, doctor1, player) || questIsTaskComplete(questId2, doctor2, player) || questIsTaskComplete(questId3, doctor3, player);
        return onQuest;
    }
    public void c_story1_1_doc_action_forwardquest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "c_story1_1_reb_doctorinfo");
        groundquests.sendSignal(player, "c_story1_1_neu_doctorinfo");
        groundquests.sendSignal(player, "c_story1_1_imp_doctorinfo");
        int questId1 = questGetQuestId("quest/c_story1_1_reb");
        int questId2 = questGetQuestId("quest/c_story1_1_neu");
        int questId3 = questGetQuestId("quest/c_story1_1_imp");
        obj_id remwaypoint = getObjIdObjVar(player, "quest.general.quest/c_story1_1_reb.doctor");
        if (remwaypoint != null)
        {
            destroyWaypointInDatapad(remwaypoint, player);
        }
        removeObjVar(player, "quest.general.quest/c_story1_1_reb.doctor");
        obj_id remwaypoint1 = getObjIdObjVar(player, "quest.general.quest/c_story1_1_neu.doctor");
        if (remwaypoint1 != null)
        {
            destroyWaypointInDatapad(remwaypoint1, player);
        }
        removeObjVar(player, "quest.general.quest/c_story1_1_neu.doctor");
        obj_id remwaypoint2 = getObjIdObjVar(player, "quest.general.quest/c_story1_1_imp.doctor");
        if (remwaypoint2 != null)
        {
            destroyWaypointInDatapad(remwaypoint2, player);
        }
        removeObjVar(player, "quest.general.quest/c_story1_1_imp.doctor");
    }
    public void c_story1_1_doc_action_faceplayer(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
    }
    public int c_story1_1_doc_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_720"))
        {
            if (c_story1_1_doc_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_722");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_1_doc_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_story1_1_doc_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_724");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_732");
                    }
                    utils.setScriptVar(player, "conversation.c_story1_1_doc.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_1_doc.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_1_doc_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_724"))
        {
            if (c_story1_1_doc_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "gesticulate_wildly");
                string_id message = new string_id(c_stringFile, "s_726");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_1_doc_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_728");
                    }
                    utils.setScriptVar(player, "conversation.c_story1_1_doc.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_1_doc.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_732"))
        {
            if (c_story1_1_doc_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_734");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_1_doc_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_736");
                    }
                    utils.setScriptVar(player, "conversation.c_story1_1_doc.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_1_doc.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_1_doc_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_728"))
        {
            if (c_story1_1_doc_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "pound_fist_palm");
                c_story1_1_doc_action_forwardquest(player, npc);
                string_id message = new string_id(c_stringFile, "s_730");
                utils.removeScriptVar(player, "conversation.c_story1_1_doc.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_1_doc_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_736"))
        {
            if (c_story1_1_doc_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "pound_fist_palm");
                c_story1_1_doc_action_forwardquest(player, npc);
                string_id message = new string_id(c_stringFile, "s_738");
                utils.removeScriptVar(player, "conversation.c_story1_1_doc.branchId");
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
            detachScript(self, "conversation.c_story1_1_doc");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        setName(self, "Edvar Vang");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        setName(self, "Edvar Vang");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
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
        detachScript(self, "conversation.c_story1_1_doc");
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
        if (c_story1_1_doc_condition_atDocStep(player, npc))
        {
            c_story1_1_doc_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_718");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_story1_1_doc_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_720");
                }
                utils.setScriptVar(player, "conversation.c_story1_1_doc.branchId", 1);
                npcStartConversation(player, npc, "c_story1_1_doc", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_story1_1_doc_condition_afterDoctorStep(player, npc))
        {
            doAnimationAction(npc, "point_away");
            c_story1_1_doc_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_740");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (c_story1_1_doc_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "greet");
            c_story1_1_doc_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_742");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("c_story1_1_doc"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.c_story1_1_doc.branchId");
        if (branchId == 1 && c_story1_1_doc_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && c_story1_1_doc_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && c_story1_1_doc_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && c_story1_1_doc_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.c_story1_1_doc.branchId");
        return SCRIPT_CONTINUE;
    }
}
