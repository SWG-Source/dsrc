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

public class c_rebdefector_allard extends script.base_script
{
    public c_rebdefector_allard()
    {
    }
    public static String c_stringFile = "conversation/c_rebdefector_allard";
    public boolean c_rebdefector_allard_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean c_rebdefector_allard_condition_playeronquest(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/c_rebdefector");
        int waitsignal = groundquests.getTaskId(questId, "waitsignal");
        boolean onTask = questIsTaskActive(questId, waitsignal, player);
        return onTask;
    }
    public void c_rebdefector_allard_action_forwardquest(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/c_rebdefector");
        int timer20 = groundquests.getTaskId(questId, "timer20");
        groundquests.sendSignal(player, "c_rebdefector_rdefectorarrived");
        questFailTask(questId, timer20, player);
        obj_id waypoint2 = getObjIdObjVar(player, "quest.general.quest/c_rebdefector.hiding");
        if (waypoint2 != null)
        {
            destroyWaypointInDatapad(waypoint2, player);
        }
        removeObjVar(player, "quest.general.quest/c_rebdefector.hiding");
        obj_id waypoint3 = getObjIdObjVar(player, "quest.general.quest/c_rebdefector.ibase");
        if (waypoint3 != null)
        {
            destroyWaypointInDatapad(waypoint3, player);
        }
        removeObjVar(player, "quest.general.quest/c_rebdefector.ibase");
    }
    public void c_rebdefector_allard_action_faceplayer(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
    }
    public int c_rebdefector_allard_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_852"))
        {
            if (c_rebdefector_allard_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_854");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_rebdefector_allard_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_rebdefector_allard_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_856");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_860");
                    }
                    utils.setScriptVar(player, "conversation.c_rebdefector_allard.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_rebdefector_allard.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_864"))
        {
            if (c_rebdefector_allard_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_866");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_rebdefector_allard_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_rebdefector_allard_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_868");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_872");
                    }
                    utils.setScriptVar(player, "conversation.c_rebdefector_allard.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_rebdefector_allard.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_rebdefector_allard_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_856"))
        {
            if (c_rebdefector_allard_condition__defaultCondition(player, npc))
            {
                c_rebdefector_allard_action_forwardquest(player, npc);
                string_id message = new string_id(c_stringFile, "s_858");
                utils.removeScriptVar(player, "conversation.c_rebdefector_allard.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_860"))
        {
            if (c_rebdefector_allard_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_862");
                utils.removeScriptVar(player, "conversation.c_rebdefector_allard.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_rebdefector_allard_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_868"))
        {
            doAnimationAction(player, "salute1");
            if (c_rebdefector_allard_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "pound_fist_palm");
                c_rebdefector_allard_action_forwardquest(player, npc);
                string_id message = new string_id(c_stringFile, "s_870");
                utils.removeScriptVar(player, "conversation.c_rebdefector_allard.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_872"))
        {
            if (c_rebdefector_allard_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_874");
                utils.removeScriptVar(player, "conversation.c_rebdefector_allard.branchId");
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
            detachScript(self, "conversation.c_rebdefector_allard");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        setName(self, "Lt. Allard Lissara");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        setName(self, "Lt. Allard Lissara");
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
        detachScript(self, "conversation.c_rebdefector_allard");
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
        if (c_rebdefector_allard_condition_playeronquest(player, npc))
        {
            c_rebdefector_allard_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_850");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_rebdefector_allard_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (c_rebdefector_allard_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_852");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_864");
                }
                utils.setScriptVar(player, "conversation.c_rebdefector_allard.branchId", 1);
                npcStartConversation(player, npc, "c_rebdefector_allard", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_rebdefector_allard_condition__defaultCondition(player, npc))
        {
            c_rebdefector_allard_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_876");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("c_rebdefector_allard"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.c_rebdefector_allard.branchId");
        if (branchId == 1 && c_rebdefector_allard_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && c_rebdefector_allard_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && c_rebdefector_allard_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.c_rebdefector_allard.branchId");
        return SCRIPT_CONTINUE;
    }
}
