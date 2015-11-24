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
import script.library.features;
import script.library.groundquests;
import script.library.space_quest;
import script.library.utils;

public class c_ddroid_valance extends script.base_script
{
    public c_ddroid_valance()
    {
    }
    public static String c_stringFile = "conversation/c_ddroid_valance";
    public boolean c_ddroid_valance_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean c_ddroid_valance_condition_hasSpaceExpansion(obj_id player, obj_id npc) throws InterruptedException
    {
        return (features.isSpaceEdition(player));
    }
    public boolean c_ddroid_valance_condition_playeronquest1(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/c_darndroid1");
        return questIsQuestActive(questId, player);
    }
    public boolean c_ddroid_valance_condition_playeronquest2part1(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/c_darndroid2");
        return questIsTaskActive(questId, 0, player) || questIsTaskActive(questId, 1, player) || questIsTaskActive(questId, 2, player) || questIsTaskActive(questId, 3, player);
    }
    public boolean c_ddroid_valance_condition_completedq2p2(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/c_darndroid2");
        return questIsTaskActive(questId, 4, player) && questIsTaskComplete(questId, 1, player) && questIsTaskComplete(questId, 2, player) && questIsTaskComplete(questId, 3, player);
    }
    public boolean c_ddroid_valance_condition_readyforquest2(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/c_darndroid1");
        int questId2 = questGetQuestId("quest/c_darndroid2");
        return questIsQuestComplete(questId, player) && !questIsQuestActive(questId2, player);
    }
    public boolean c_ddroid_valance_condition_failedSpace(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasAbortedQuest(player, "assassinate", "c_darndroidspace") || space_quest.hasFailedQuest(player, "assassinate", "c_darndroidspace"))
        {
            return true;
        }
        return false;
    }
    public boolean c_ddroid_valance_condition_completedspace(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasWonQuest(player, "assassinate", "c_darndroidspace"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean c_ddroid_valance_condition_completedAll(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/c_darndroid1");
        int questId2 = questGetQuestId("quest/c_darndroid2");
        return questIsQuestComplete(questId, player) && questIsQuestComplete(questId2, player);
    }
    public void c_ddroid_valance_action_giveddroidquest2(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/c_darndroid2");
        groundquests.grantQuest(questId, player, npc, true);
        obj_id top = getTopMostContainer(player);
        location loc = getLocation(top);
        loc.x = 5353;
        loc.y = 131;
        loc.z = 5632;
        obj_id waypoint = createWaypointInDatapad(player, loc);
        setObjVar(player, "quest.general.quest/c_darndroid2.staging", waypoint);
        setWaypointName(waypoint, "Black Sun Staging Camp");
        setWaypointActive(waypoint, true);
        location loc1 = getLocation(top);
        loc1.x = -2380;
        loc1.y = 0;
        loc1.z = 2031;
        obj_id waypoint1 = createWaypointInDatapad(player, loc1);
        setObjVar(player, "quest.general.quest/c_darndroid2.valance", waypoint1);
        setWaypointName(waypoint1, "Valance Serth");
    }
    public void c_ddroid_valance_action_nojtlCompleteQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "c_darndroid2_campdead");
        groundquests.sendSignal(player, "c_darndroid2_spacedone");
        obj_id waypoint1 = getObjIdObjVar(player, "quest.general.quest/c_darndroid1.taluscrash");
        if (waypoint1 != null)
        {
            destroyWaypointInDatapad(waypoint1, player);
        }
        removeObjVar(player, "quest.general.quest/c_darndroid1.taluscrash");
    }
    public void c_ddroid_valance_action_giveSpaceQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "assassinate", "c_darndroidspace");
        groundquests.sendSignal(player, "c_darndroid2_campdead");
        obj_id waypoint1 = getObjIdObjVar(player, "quest.general.quest/c_darndroid1.taluscrash");
        if (waypoint1 != null)
        {
            destroyWaypointInDatapad(waypoint1, player);
        }
        removeObjVar(player, "quest.general.quest/c_darndroid1.taluscrash");
    }
    public void c_ddroid_valance_action_completespace(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "c_darndroid2_spacedone");
        obj_id top = getTopMostContainer(player);
        location loc = getLocation(top);
        loc.x = 4723;
        loc.y = 3;
        loc.z = -4935;
        loc.area = "naboo";
        obj_id waypoint = createWaypointInDatapad(player, loc);
        setObjVar(player, "quest.general.quest/c_darndroid2.droid", waypoint);
        setWaypointName(waypoint, "C-3TC");
        setWaypointActive(waypoint, true);
    }
    public void c_ddroid_valance_action_completequest1(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "c_darndroid1_valance");
        obj_id waypoint1 = getObjIdObjVar(player, "quest.general.quest/c_darndroid1.taluscrash");
        if (waypoint1 != null)
        {
            destroyWaypointInDatapad(waypoint1, player);
        }
        removeObjVar(player, "quest.general.quest/c_darndroid1.taluscrash");
    }
    public void c_ddroid_valance_action_faceplayer(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
    }
    public int c_ddroid_valance_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1172"))
        {
            if (c_ddroid_valance_condition__defaultCondition(player, npc))
            {
                c_ddroid_valance_action_completequest1(player, npc);
                string_id message = new string_id(c_stringFile, "s_1174");
                utils.removeScriptVar(player, "conversation.c_ddroid_valance.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1176"))
        {
            if (c_ddroid_valance_condition__defaultCondition(player, npc))
            {
                c_ddroid_valance_action_completequest1(player, npc);
                string_id message = new string_id(c_stringFile, "s_1178");
                utils.removeScriptVar(player, "conversation.c_ddroid_valance.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_ddroid_valance_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1182"))
        {
            if (c_ddroid_valance_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1184");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_ddroid_valance_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1186");
                    }
                    utils.setScriptVar(player, "conversation.c_ddroid_valance.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_ddroid_valance.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_ddroid_valance_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1186"))
        {
            if (c_ddroid_valance_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1188");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_ddroid_valance_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1190");
                    }
                    utils.setScriptVar(player, "conversation.c_ddroid_valance.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_ddroid_valance.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_ddroid_valance_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1190"))
        {
            if (c_ddroid_valance_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1192");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_ddroid_valance_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_ddroid_valance_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_ddroid_valance_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1194");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1206");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1210");
                    }
                    utils.setScriptVar(player, "conversation.c_ddroid_valance.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_ddroid_valance.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_ddroid_valance_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1194"))
        {
            if (c_ddroid_valance_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1196");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_ddroid_valance_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_ddroid_valance_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1198");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1202");
                    }
                    utils.setScriptVar(player, "conversation.c_ddroid_valance.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_ddroid_valance.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1206"))
        {
            if (c_ddroid_valance_condition__defaultCondition(player, npc))
            {
                c_ddroid_valance_action_giveddroidquest2(player, npc);
                string_id message = new string_id(c_stringFile, "s_1208");
                utils.removeScriptVar(player, "conversation.c_ddroid_valance.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1210"))
        {
            if (c_ddroid_valance_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1212");
                utils.removeScriptVar(player, "conversation.c_ddroid_valance.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_ddroid_valance_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1198"))
        {
            if (c_ddroid_valance_condition__defaultCondition(player, npc))
            {
                c_ddroid_valance_action_giveddroidquest2(player, npc);
                string_id message = new string_id(c_stringFile, "s_1200");
                utils.removeScriptVar(player, "conversation.c_ddroid_valance.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1202"))
        {
            if (c_ddroid_valance_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1204");
                utils.removeScriptVar(player, "conversation.c_ddroid_valance.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_ddroid_valance_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1216"))
        {
            if (!c_ddroid_valance_condition_hasSpaceExpansion(player, npc))
            {
                c_ddroid_valance_action_nojtlCompleteQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_1218");
                utils.removeScriptVar(player, "conversation.c_ddroid_valance.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (c_ddroid_valance_condition_hasSpaceExpansion(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1220");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_ddroid_valance_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_ddroid_valance_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1222");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1226");
                    }
                    utils.setScriptVar(player, "conversation.c_ddroid_valance.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_ddroid_valance.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1230"))
        {
            if (c_ddroid_valance_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1232");
                utils.removeScriptVar(player, "conversation.c_ddroid_valance.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_ddroid_valance_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1222"))
        {
            if (c_ddroid_valance_condition__defaultCondition(player, npc))
            {
                c_ddroid_valance_action_giveSpaceQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_1224");
                utils.removeScriptVar(player, "conversation.c_ddroid_valance.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1226"))
        {
            if (c_ddroid_valance_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1228");
                utils.removeScriptVar(player, "conversation.c_ddroid_valance.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_ddroid_valance_handleBranch19(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1236"))
        {
            if (c_ddroid_valance_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1238");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_ddroid_valance_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_ddroid_valance_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1240");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1244");
                    }
                    utils.setScriptVar(player, "conversation.c_ddroid_valance.branchId", 20);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_ddroid_valance.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1248"))
        {
            if (c_ddroid_valance_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1250");
                utils.removeScriptVar(player, "conversation.c_ddroid_valance.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_ddroid_valance_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1240"))
        {
            if (c_ddroid_valance_condition__defaultCondition(player, npc))
            {
                c_ddroid_valance_action_completespace(player, npc);
                string_id message = new string_id(c_stringFile, "s_1242");
                utils.removeScriptVar(player, "conversation.c_ddroid_valance.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1244"))
        {
            if (c_ddroid_valance_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1246");
                utils.removeScriptVar(player, "conversation.c_ddroid_valance.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_ddroid_valance_handleBranch25(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1256"))
        {
            if (c_ddroid_valance_condition__defaultCondition(player, npc))
            {
                c_ddroid_valance_action_giveSpaceQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_1258");
                utils.removeScriptVar(player, "conversation.c_ddroid_valance.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1260"))
        {
            if (c_ddroid_valance_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1262");
                utils.removeScriptVar(player, "conversation.c_ddroid_valance.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_ddroid_valance_handleBranch28(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1266"))
        {
            if (c_ddroid_valance_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1268");
                utils.removeScriptVar(player, "conversation.c_ddroid_valance.branchId");
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
            detachScript(self, "conversation.c_ddroid_valance");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        setName(self, "Valance Serth");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        setName(self, "Valance Serth");
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
        detachScript(self, "conversation.c_ddroid_valance");
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
        if (c_ddroid_valance_condition_playeronquest1(player, npc))
        {
            c_ddroid_valance_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_1170");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_ddroid_valance_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (c_ddroid_valance_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1172");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1176");
                }
                utils.setScriptVar(player, "conversation.c_ddroid_valance.branchId", 1);
                npcStartConversation(player, npc, "c_ddroid_valance", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_ddroid_valance_condition_readyforquest2(player, npc))
        {
            c_ddroid_valance_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_1180");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_ddroid_valance_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1182");
                }
                utils.setScriptVar(player, "conversation.c_ddroid_valance.branchId", 4);
                npcStartConversation(player, npc, "c_ddroid_valance", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_ddroid_valance_condition_completedq2p2(player, npc))
        {
            c_ddroid_valance_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_1214");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_ddroid_valance_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (c_ddroid_valance_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1216");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1230");
                }
                utils.setScriptVar(player, "conversation.c_ddroid_valance.branchId", 13);
                npcStartConversation(player, npc, "c_ddroid_valance", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_ddroid_valance_condition_completedspace(player, npc))
        {
            c_ddroid_valance_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_1234");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_ddroid_valance_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (c_ddroid_valance_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1236");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1248");
                }
                utils.setScriptVar(player, "conversation.c_ddroid_valance.branchId", 19);
                npcStartConversation(player, npc, "c_ddroid_valance", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_ddroid_valance_condition_playeronquest2part1(player, npc))
        {
            c_ddroid_valance_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_1252");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (c_ddroid_valance_condition_failedSpace(player, npc))
        {
            c_ddroid_valance_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_1254");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_ddroid_valance_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (c_ddroid_valance_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1256");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1260");
                }
                utils.setScriptVar(player, "conversation.c_ddroid_valance.branchId", 25);
                npcStartConversation(player, npc, "c_ddroid_valance", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_ddroid_valance_condition_completedAll(player, npc))
        {
            c_ddroid_valance_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_1264");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_ddroid_valance_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1266");
                }
                utils.setScriptVar(player, "conversation.c_ddroid_valance.branchId", 28);
                npcStartConversation(player, npc, "c_ddroid_valance", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_ddroid_valance_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1269");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("c_ddroid_valance"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.c_ddroid_valance.branchId");
        if (branchId == 1 && c_ddroid_valance_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && c_ddroid_valance_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && c_ddroid_valance_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && c_ddroid_valance_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && c_ddroid_valance_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && c_ddroid_valance_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && c_ddroid_valance_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && c_ddroid_valance_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && c_ddroid_valance_handleBranch19(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && c_ddroid_valance_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && c_ddroid_valance_handleBranch25(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 28 && c_ddroid_valance_handleBranch28(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.c_ddroid_valance.branchId");
        return SCRIPT_CONTINUE;
    }
}
