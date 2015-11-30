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

public class c_impdefector_jom extends script.base_script
{
    public c_impdefector_jom()
    {
    }
    public static String c_stringFile = "conversation/c_impdefector_jom";
    public boolean c_impdefector_jom_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean c_impdefector_jom_condition_isEnemyFaction(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!factions.isImperial(player))
        {
            return false;
        }
        return true;
    }
    public boolean c_impdefector_jom_condition_isFriendlyFaction(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/c_impdefector");
        boolean onQuest = questIsQuestActive(questId1, player) || questIsQuestComplete(questId1, player);
        if (!factions.isRebel(player) && (!onQuest))
        {
            return false;
        }
        return true;
    }
    public boolean c_impdefector_jom_condition_isNeutralFaction(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/c_impdefector");
        boolean onQuest = questIsQuestActive(questId1, player) || questIsQuestComplete(questId1, player);
        if (factions.isImperial(player) || factions.isRebel(player) || (onQuest))
        {
            return false;
        }
        return true;
    }
    public boolean c_impdefector_jom_condition_questIsComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/c_impdefector");
        boolean doneQuest = questIsQuestComplete(questId1, player);
        return doneQuest;
    }
    public boolean c_impdefector_jom_condition_questIsActive(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/c_impdefector");
        boolean onQuest = questIsQuestActive(questId1, player);
        return onQuest;
    }
    public void c_impdefector_jom_action_givequest(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/c_impdefector");
        groundquests.grantQuest(questId, player, npc, true);
        obj_id waypoint2 = getObjIdObjVar(player, "quest.general.quest/c_impdefector.hiding");
        if (waypoint2 != null)
        {
            destroyWaypointInDatapad(waypoint2, player);
        }
        removeObjVar(player, "quest.general.quest/c_impdefector.hiding");
        obj_id waypoint3 = getObjIdObjVar(player, "quest.general.quest/c_impdefector.rbase");
        if (waypoint3 != null)
        {
            destroyWaypointInDatapad(waypoint3, player);
        }
        removeObjVar(player, "quest.general.quest/c_impdefector.rbase");
    }
    public void c_impdefector_jom_action_faceplayer(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
    }
    public int c_impdefector_jom_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_552"))
        {
            if (c_impdefector_jom_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_554");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_impdefector_jom_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_impdefector_jom_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_556");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_560");
                    }
                    utils.setScriptVar(player, "conversation.c_impdefector_jom.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_impdefector_jom.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_564"))
        {
            if (c_impdefector_jom_condition__defaultCondition(player, npc))
            {
                c_impdefector_jom_action_givequest(player, npc);
                string_id message = new string_id(c_stringFile, "s_566");
                utils.removeScriptVar(player, "conversation.c_impdefector_jom.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_568"))
        {
            if (c_impdefector_jom_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_570");
                utils.removeScriptVar(player, "conversation.c_impdefector_jom.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_impdefector_jom_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_556"))
        {
            if (c_impdefector_jom_condition__defaultCondition(player, npc))
            {
                c_impdefector_jom_action_givequest(player, npc);
                string_id message = new string_id(c_stringFile, "s_558");
                utils.removeScriptVar(player, "conversation.c_impdefector_jom.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_560"))
        {
            if (c_impdefector_jom_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_562");
                utils.removeScriptVar(player, "conversation.c_impdefector_jom.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_impdefector_jom_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_576"))
        {
            if (c_impdefector_jom_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_578");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_impdefector_jom_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_impdefector_jom_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_580");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_584");
                    }
                    utils.setScriptVar(player, "conversation.c_impdefector_jom.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_impdefector_jom.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_impdefector_jom_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_580"))
        {
            if (c_impdefector_jom_condition__defaultCondition(player, npc))
            {
                c_impdefector_jom_action_givequest(player, npc);
                string_id message = new string_id(c_stringFile, "s_582");
                utils.removeScriptVar(player, "conversation.c_impdefector_jom.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_584"))
        {
            if (c_impdefector_jom_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_586");
                utils.removeScriptVar(player, "conversation.c_impdefector_jom.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_impdefector_jom_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_592"))
        {
            if (c_impdefector_jom_condition__defaultCondition(player, npc))
            {
                c_impdefector_jom_action_givequest(player, npc);
                string_id message = new string_id(c_stringFile, "s_594");
                utils.removeScriptVar(player, "conversation.c_impdefector_jom.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_596"))
        {
            if (c_impdefector_jom_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_598");
                utils.removeScriptVar(player, "conversation.c_impdefector_jom.branchId");
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
            detachScript(self, "conversation.c_impdefector_jom");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        setName(self, "Jom Irimore");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        setName(self, "Jom Irimore");
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
        detachScript(self, "conversation.c_impdefector_jom");
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
        if (c_impdefector_jom_condition_questIsActive(player, npc))
        {
            c_impdefector_jom_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_588");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (c_impdefector_jom_condition_isFriendlyFaction(player, npc))
        {
            c_impdefector_jom_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_550");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_impdefector_jom_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (c_impdefector_jom_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (c_impdefector_jom_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_552");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_564");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_568");
                }
                utils.setScriptVar(player, "conversation.c_impdefector_jom.branchId", 2);
                npcStartConversation(player, npc, "c_impdefector_jom", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_impdefector_jom_condition_isEnemyFaction(player, npc))
        {
            c_impdefector_jom_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_572");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (c_impdefector_jom_condition_isNeutralFaction(player, npc))
        {
            c_impdefector_jom_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_574");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_impdefector_jom_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_576");
                }
                utils.setScriptVar(player, "conversation.c_impdefector_jom.branchId", 9);
                npcStartConversation(player, npc, "c_impdefector_jom", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_impdefector_jom_condition_questIsComplete(player, npc))
        {
            c_impdefector_jom_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_590");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_impdefector_jom_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (c_impdefector_jom_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_592");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_596");
                }
                utils.setScriptVar(player, "conversation.c_impdefector_jom.branchId", 13);
                npcStartConversation(player, npc, "c_impdefector_jom", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_impdefector_jom_condition__defaultCondition(player, npc))
        {
            c_impdefector_jom_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_600");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("c_impdefector_jom"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.c_impdefector_jom.branchId");
        if (branchId == 2 && c_impdefector_jom_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && c_impdefector_jom_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && c_impdefector_jom_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && c_impdefector_jom_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && c_impdefector_jom_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.c_impdefector_jom.branchId");
        return SCRIPT_CONTINUE;
    }
}
