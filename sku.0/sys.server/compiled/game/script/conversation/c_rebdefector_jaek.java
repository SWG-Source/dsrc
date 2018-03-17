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
import script.library.factions;
import script.library.groundquests;
import script.library.utils;

public class c_rebdefector_jaek extends script.base_script
{
    public c_rebdefector_jaek()
    {
    }
    public static String c_stringFile = "conversation/c_rebdefector_jaek";
    public boolean c_rebdefector_jaek_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean c_rebdefector_jaek_condition_isEnemyFaction(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!factions.isRebel(player))
        {
            return false;
        }
        return true;
    }
    public boolean c_rebdefector_jaek_condition_isFriendlyFaction(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/c_rebdefector");
        boolean onQuest = questIsQuestActive(questId1, player) || questIsQuestComplete(questId1, player);
        if (!factions.isImperial(player) && (!onQuest))
        {
            return false;
        }
        return true;
    }
    public boolean c_rebdefector_jaek_condition_isNeutralFaction(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/c_rebdefector");
        boolean onQuest = questIsQuestActive(questId1, player) || questIsQuestComplete(questId1, player);
        if (factions.isImperial(player) || factions.isRebel(player) || (onQuest))
        {
            return false;
        }
        return true;
    }
    public boolean c_rebdefector_jaek_condition_questIsComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/c_rebdefector");
        boolean doneQuest = questIsQuestComplete(questId1, player);
        return doneQuest;
    }
    public boolean c_rebdefector_jaek_condition_questIsActive(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/c_rebdefector");
        boolean onQuest = questIsQuestActive(questId1, player);
        return onQuest;
    }
    public void c_rebdefector_jaek_action_givequest(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/c_rebdefector");
        groundquests.grantQuest(questId, player, npc, true);
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
    public void c_rebdefector_jaek_action_faceplayer(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
    }
    public int c_rebdefector_jaek_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_498"))
        {
            if (c_rebdefector_jaek_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_500");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_rebdefector_jaek_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_rebdefector_jaek_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_502");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_506");
                    }
                    utils.setScriptVar(player, "conversation.c_rebdefector_jaek.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_rebdefector_jaek.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_510"))
        {
            if (c_rebdefector_jaek_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute1");
                c_rebdefector_jaek_action_givequest(player, npc);
                string_id message = new string_id(c_stringFile, "s_512");
                utils.removeScriptVar(player, "conversation.c_rebdefector_jaek.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_514"))
        {
            if (c_rebdefector_jaek_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_516");
                utils.removeScriptVar(player, "conversation.c_rebdefector_jaek.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_rebdefector_jaek_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_502"))
        {
            if (c_rebdefector_jaek_condition__defaultCondition(player, npc))
            {
                c_rebdefector_jaek_action_givequest(player, npc);
                string_id message = new string_id(c_stringFile, "s_504");
                utils.removeScriptVar(player, "conversation.c_rebdefector_jaek.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_506"))
        {
            if (c_rebdefector_jaek_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_508");
                utils.removeScriptVar(player, "conversation.c_rebdefector_jaek.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_rebdefector_jaek_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_522"))
        {
            if (c_rebdefector_jaek_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_524");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_rebdefector_jaek_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_rebdefector_jaek_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_526");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_530");
                    }
                    utils.setScriptVar(player, "conversation.c_rebdefector_jaek.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_rebdefector_jaek.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_rebdefector_jaek_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_526"))
        {
            if (c_rebdefector_jaek_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                c_rebdefector_jaek_action_givequest(player, npc);
                string_id message = new string_id(c_stringFile, "s_528");
                utils.removeScriptVar(player, "conversation.c_rebdefector_jaek.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_530"))
        {
            if (c_rebdefector_jaek_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_532");
                utils.removeScriptVar(player, "conversation.c_rebdefector_jaek.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_rebdefector_jaek_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_538"))
        {
            if (c_rebdefector_jaek_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                c_rebdefector_jaek_action_givequest(player, npc);
                string_id message = new string_id(c_stringFile, "s_540");
                utils.removeScriptVar(player, "conversation.c_rebdefector_jaek.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_542"))
        {
            if (c_rebdefector_jaek_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shoo");
                string_id message = new string_id(c_stringFile, "s_544");
                utils.removeScriptVar(player, "conversation.c_rebdefector_jaek.branchId");
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
            detachScript(self, "conversation.c_rebdefector_jaek");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        setName(self, "Jaek Vercet");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        setName(self, "Jaek Vercet");
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
        detachScript(self, "conversation.c_rebdefector_jaek");
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
        if (c_rebdefector_jaek_condition_questIsActive(player, npc))
        {
            c_rebdefector_jaek_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_534");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (c_rebdefector_jaek_condition_isFriendlyFaction(player, npc))
        {
            c_rebdefector_jaek_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_496");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_rebdefector_jaek_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (c_rebdefector_jaek_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (c_rebdefector_jaek_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_498");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_510");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_514");
                }
                utils.setScriptVar(player, "conversation.c_rebdefector_jaek.branchId", 2);
                npcStartConversation(player, npc, "c_rebdefector_jaek", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_rebdefector_jaek_condition_isEnemyFaction(player, npc))
        {
            doAnimationAction(npc, "shoo");
            c_rebdefector_jaek_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_518");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (c_rebdefector_jaek_condition_isNeutralFaction(player, npc))
        {
            c_rebdefector_jaek_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_520");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_rebdefector_jaek_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_522");
                }
                utils.setScriptVar(player, "conversation.c_rebdefector_jaek.branchId", 9);
                npcStartConversation(player, npc, "c_rebdefector_jaek", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_rebdefector_jaek_condition_questIsComplete(player, npc))
        {
            c_rebdefector_jaek_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_536");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_rebdefector_jaek_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (c_rebdefector_jaek_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_538");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_542");
                }
                utils.setScriptVar(player, "conversation.c_rebdefector_jaek.branchId", 13);
                npcStartConversation(player, npc, "c_rebdefector_jaek", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_rebdefector_jaek_condition__defaultCondition(player, npc))
        {
            c_rebdefector_jaek_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_546");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("c_rebdefector_jaek"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.c_rebdefector_jaek.branchId");
        if (branchId == 2 && c_rebdefector_jaek_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && c_rebdefector_jaek_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && c_rebdefector_jaek_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && c_rebdefector_jaek_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && c_rebdefector_jaek_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.c_rebdefector_jaek.branchId");
        return SCRIPT_CONTINUE;
    }
}
