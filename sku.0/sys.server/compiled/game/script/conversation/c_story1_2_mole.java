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

public class c_story1_2_mole extends script.base_script
{
    public c_story1_2_mole()
    {
    }
    public static String c_stringFile = "conversation/c_story1_2_mole";
    public boolean c_story1_2_mole_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean c_story1_2_mole_condition_atKailaStep(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/c_story1_2_neu");
        int questId2 = questGetQuestId("quest/c_story1_2_imp");
        int questId3 = questGetQuestId("quest/c_story1_2_reb");
        int kaila1 = groundquests.getTaskId(questId1, "kaila");
        int kaila2 = groundquests.getTaskId(questId2, "kaila");
        int kaila3 = groundquests.getTaskId(questId3, "kaila");
        boolean onQuest = questIsTaskActive(questId1, kaila1, player) || questIsTaskActive(questId2, kaila2, player) || questIsTaskActive(questId3, kaila3, player);
        return onQuest;
    }
    public void c_story1_2_mole_action_facePlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
    }
    public void c_story1_2_mole_action_forwardQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "c_story1_2_neu_kaila");
        groundquests.sendSignal(player, "c_story1_2_imp_kaila");
        groundquests.sendSignal(player, "c_story1_2_reb_kaila");
        int questId1 = questGetQuestId("quest/c_story1_2_neu");
        int questId2 = questGetQuestId("quest/c_story1_2_imp");
        int questId3 = questGetQuestId("quest/c_story1_2_reb");
        obj_id remwaypoint = getObjIdObjVar(player, "quest.general.quest/c_story1_2_neu.kaila");
        if (remwaypoint != null)
        {
            destroyWaypointInDatapad(remwaypoint, player);
        }
        removeObjVar(player, "quest.general.quest/c_story1_2_neu.kaila");
        obj_id remwaypoint2 = getObjIdObjVar(player, "quest.general.quest/c_story1_2_imp.kaila");
        if (remwaypoint2 != null)
        {
            destroyWaypointInDatapad(remwaypoint2, player);
        }
        removeObjVar(player, "quest.general.quest/c_story1_2_imp.kaila");
        obj_id remwaypoint3 = getObjIdObjVar(player, "quest.general.quest/c_story1_2_reb.kaila");
        if (remwaypoint3 != null)
        {
            destroyWaypointInDatapad(remwaypoint3, player);
        }
        removeObjVar(player, "quest.general.quest/c_story1_2_reb.kaila");
    }
    public int c_story1_2_mole_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_55"))
        {
            if (c_story1_2_mole_condition__defaultCondition(player, npc))
            {
                c_story1_2_mole_action_forwardQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_57");
                utils.removeScriptVar(player, "conversation.c_story1_2_mole.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_59"))
        {
            if (c_story1_2_mole_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "hair_flip");
                string_id message = new string_id(c_stringFile, "s_61");
                utils.removeScriptVar(player, "conversation.c_story1_2_mole.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_63"))
        {
            doAnimationAction(player, "shake_head_no");
            if (c_story1_2_mole_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "stretch");
                string_id message = new string_id(c_stringFile, "s_65");
                utils.removeScriptVar(player, "conversation.c_story1_2_mole.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.c_story1_2_mole");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        setName(self, "Kaila Min");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        setName(self, "Kaila Min");
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
        detachScript(self, "conversation.c_story1_2_mole");
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
        if (c_story1_2_mole_condition__defaultCondition(player, npc))
        {
            c_story1_2_mole_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_53");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_story1_2_mole_condition_atKailaStep(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (c_story1_2_mole_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (c_story1_2_mole_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_55");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_59");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_63");
                }
                utils.setScriptVar(player, "conversation.c_story1_2_mole.branchId", 1);
                npcStartConversation(player, npc, "c_story1_2_mole", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("c_story1_2_mole"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.c_story1_2_mole.branchId");
        if (branchId == 1 && c_story1_2_mole_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.c_story1_2_mole.branchId");
        return SCRIPT_CONTINUE;
    }
}
