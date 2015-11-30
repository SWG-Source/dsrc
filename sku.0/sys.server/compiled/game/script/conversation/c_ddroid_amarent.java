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
import script.library.conversation;
import script.library.factions;
import script.library.groundquests;
import script.library.money;
import script.library.utils;

public class c_ddroid_amarent extends script.base_script
{
    public c_ddroid_amarent()
    {
    }
    public static String c_stringFile = "conversation/c_ddroid_amarent";
    public boolean c_ddroid_amarent_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean c_ddroid_amarent_condition_isImperialOfc(obj_id player, obj_id npc) throws InterruptedException
    {
        String whichFaction = factions.getDeclaredFaction(player);
        if (whichFaction == null)
        {
            return false;
        }
        if (whichFaction.equals("Imperial"))
        {
            int rank = pvpGetCurrentGcwRank(player);
            if (rank > 1)
            {
                return true;
            }
        }
        return false;
    }
    public boolean c_ddroid_amarent_condition_playeronquest(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/c_darndroid1");
        return questIsTaskActive(questId, 3, player) || questIsTaskActive(questId, 4, player);
    }
    public boolean c_ddroid_amarent_condition_playerlostwaypoint(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/c_darndroid1");
        return questIsTaskActive(questId, 4, player);
    }
    public boolean c_ddroid_amarent_condition_queststuck(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/c_darndroid1");
        int canintimidate = groundquests.getTaskId(questId, "canintimidate");
        int canbribe = groundquests.getTaskId(questId, "canbribe");
        int reward = groundquests.getTaskId(questId, "reward");
        return (questIsTaskActive(questId, canintimidate, player) || questIsTaskActive(questId, canbribe, player)) && questIsTaskComplete(questId, reward, player);
    }
    public boolean c_ddroid_amarent_condition_hasBribeFunds(obj_id player, obj_id npc) throws InterruptedException
    {
        return money.hasFunds(player, money.MT_TOTAL, 1000);
    }
    public void c_ddroid_amarent_action_intimidateAmarent(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "c_darndroid1_intimidate");
    }
    public void c_ddroid_amarent_action_bribeAmarent(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "c_darndroid1_bribe");
        money.requestPayment(player, npc, 1000, "pass_fail", null, true);
    }
    public void c_ddroid_amarent_action_amarentGivesInfo(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "c_darndroid1_amarent");
        obj_id waypoint1 = getObjIdObjVar(player, "quest.general.quest/c_darndroid1.amarent");
        if (waypoint1 != null)
        {
            destroyWaypointInDatapad(waypoint1, player);
        }
        removeObjVar(player, "quest.general.quest/c_darndroid1.amarent");
    }
    public void c_ddroid_amarent_action_givewaypoint(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id waypoint1 = getObjIdObjVar(player, "quest.general.quest/c_darndroid1.taluscrash");
        if (waypoint1 != null)
        {
            destroyWaypointInDatapad(waypoint1, player);
        }
        removeObjVar(player, "quest.general.quest/c_darndroid1.taluscrash");
        obj_id top = getTopMostContainer(player);
        location loc = getLocation(top);
        loc.x = -2368;
        loc.y = 23;
        loc.z = 2043;
        loc.area = "talus";
        obj_id waypoint = createWaypointInDatapad(player, loc);
        setObjVar(player, "quest.general.quest/c_darndroid1.taluscrash", waypoint);
        setWaypointName(waypoint, "Talus Crash Site");
        setWaypointActive(waypoint, true);
    }
    public void c_ddroid_amarent_action_faceplayer(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
    }
    public void c_ddroid_amarent_action_completequestFix(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/c_darndroid1");
        completeQuest(player, questId);
        obj_id waypoint1 = getObjIdObjVar(player, "quest.general.quest/c_darndroid1.taluscrash");
        if (waypoint1 != null)
        {
            destroyWaypointInDatapad(waypoint1, player);
        }
        removeObjVar(player, "quest.general.quest/c_darndroid1.taluscrash");
        obj_id waypoint2 = getObjIdObjVar(player, "quest.general.quest/c_darndroid1.amarent");
        if (waypoint2 != null)
        {
            destroyWaypointInDatapad(waypoint2, player);
        }
        removeObjVar(player, "quest.general.quest/c_darndroid1.amarent");
    }
    public int c_ddroid_amarent_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_281"))
        {
            if (c_ddroid_amarent_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_283");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_ddroid_amarent_condition_isImperialOfc(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!c_ddroid_amarent_condition_isImperialOfc(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_285");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_301");
                    }
                    utils.setScriptVar(player, "conversation.c_ddroid_amarent.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_ddroid_amarent.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_ddroid_amarent_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_285"))
        {
            if (c_ddroid_amarent_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute1");
                string_id message = new string_id(c_stringFile, "s_287");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_ddroid_amarent_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_ddroid_amarent_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_289");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_293");
                    }
                    utils.setScriptVar(player, "conversation.c_ddroid_amarent.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_ddroid_amarent.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_301"))
        {
            if (c_ddroid_amarent_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_303");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_ddroid_amarent_condition_hasBribeFunds(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!c_ddroid_amarent_condition_hasBribeFunds(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_ddroid_amarent_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_305");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_25");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_309");
                    }
                    utils.setScriptVar(player, "conversation.c_ddroid_amarent.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_ddroid_amarent.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_ddroid_amarent_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_289"))
        {
            if (c_ddroid_amarent_condition__defaultCondition(player, npc))
            {
                c_ddroid_amarent_action_amarentGivesInfo(player, npc);
                string_id message = new string_id(c_stringFile, "s_291");
                utils.removeScriptVar(player, "conversation.c_ddroid_amarent.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_293"))
        {
            if (c_ddroid_amarent_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_295");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_ddroid_amarent_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_297");
                    }
                    utils.setScriptVar(player, "conversation.c_ddroid_amarent.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_ddroid_amarent.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_ddroid_amarent_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_297"))
        {
            if (c_ddroid_amarent_condition__defaultCondition(player, npc))
            {
                c_ddroid_amarent_action_amarentGivesInfo(player, npc);
                string_id message = new string_id(c_stringFile, "s_299");
                utils.removeScriptVar(player, "conversation.c_ddroid_amarent.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_ddroid_amarent_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_305"))
        {
            c_ddroid_amarent_action_bribeAmarent(player, npc);
            if (c_ddroid_amarent_condition__defaultCondition(player, npc))
            {
                c_ddroid_amarent_action_amarentGivesInfo(player, npc);
                string_id message = new string_id(c_stringFile, "s_307");
                utils.removeScriptVar(player, "conversation.c_ddroid_amarent.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_25"))
        {
            if (c_ddroid_amarent_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_26");
                utils.removeScriptVar(player, "conversation.c_ddroid_amarent.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_309"))
        {
            c_ddroid_amarent_action_intimidateAmarent(player, npc);
            if (c_ddroid_amarent_condition__defaultCondition(player, npc))
            {
                c_ddroid_amarent_action_amarentGivesInfo(player, npc);
                string_id message = new string_id(c_stringFile, "s_311");
                utils.removeScriptVar(player, "conversation.c_ddroid_amarent.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_ddroid_amarent_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_316"))
        {
            if (c_ddroid_amarent_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_317");
                utils.removeScriptVar(player, "conversation.c_ddroid_amarent.branchId");
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
            detachScript(self, "conversation.c_ddroid_amarent");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setName(self, "Amarent Loren");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setName(self, "Amarent Loren");
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
        detachScript(self, "conversation.c_ddroid_amarent");
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
        if (c_ddroid_amarent_condition_playeronquest(player, npc))
        {
            c_ddroid_amarent_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_279");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_ddroid_amarent_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_281");
                }
                utils.setScriptVar(player, "conversation.c_ddroid_amarent.branchId", 1);
                npcStartConversation(player, npc, "c_ddroid_amarent", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (!c_ddroid_amarent_condition_playeronquest(player, npc))
        {
            c_ddroid_amarent_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_313");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (c_ddroid_amarent_condition_playerlostwaypoint(player, npc))
        {
            c_ddroid_amarent_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_315");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_ddroid_amarent_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_316");
                }
                utils.setScriptVar(player, "conversation.c_ddroid_amarent.branchId", 12);
                npcStartConversation(player, npc, "c_ddroid_amarent", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_ddroid_amarent_condition_queststuck(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_28");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("c_ddroid_amarent"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.c_ddroid_amarent.branchId");
        if (branchId == 1 && c_ddroid_amarent_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && c_ddroid_amarent_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && c_ddroid_amarent_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && c_ddroid_amarent_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && c_ddroid_amarent_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && c_ddroid_amarent_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.c_ddroid_amarent.branchId");
        return SCRIPT_CONTINUE;
    }
}
