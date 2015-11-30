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
import script.library.skill;
import script.library.utils;

public class ep3_clone_relics_nym_starmap_nym extends script.base_script
{
    public ep3_clone_relics_nym_starmap_nym()
    {
    }
    public static String c_stringFile = "conversation/ep3_clone_relics_nym_starmap_nym";
    public boolean ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean ep3_clone_relics_nym_starmap_nym_condition_onQuest1(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isQuestActive(player, "ep3_clone_relics_nym_starmap_1"));
    }
    public boolean ep3_clone_relics_nym_starmap_nym_condition_onTask1(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "ep3_clone_relics_nym_starmap_1", "talkToNym1"));
    }
    public boolean ep3_clone_relics_nym_starmap_nym_condition_foundFirstMap(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "ep3_clone_relics_nym_starmap_1", "foundFirstMap"));
    }
    public boolean ep3_clone_relics_nym_starmap_nym_condition_completedQuest1(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedQuest(player, "ep3_clone_relics_nym_starmap_1"));
    }
    public boolean ep3_clone_relics_nym_starmap_nym_condition_onQuest2(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isQuestActive(player, "ep3_clone_relics_nym_starmap_2"));
    }
    public boolean ep3_clone_relics_nym_starmap_nym_condition_foundSecondMap(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "ep3_clone_relics_nym_starmap_2", "foundSecondMap"));
    }
    public boolean ep3_clone_relics_nym_starmap_nym_condition_completedQuest2(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedQuest(player, "ep3_clone_relics_nym_starmap_2"));
    }
    public boolean ep3_clone_relics_nym_starmap_nym_condition_onQuest3(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isQuestActive(player, "ep3_clone_relics_nym_starmap_3"));
    }
    public boolean ep3_clone_relics_nym_starmap_nym_condition_foundThirdMap(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "ep3_clone_relics_nym_starmap_3", "foundThirdMap"));
    }
    public boolean ep3_clone_relics_nym_starmap_nym_condition_completedQuest3(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedQuest(player, "ep3_clone_relics_nym_starmap_3"));
    }
    public boolean ep3_clone_relics_nym_starmap_nym_condition_onQuest4(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isQuestActive(player, "ep3_clone_relics_nym_starmap_4"));
    }
    public boolean ep3_clone_relics_nym_starmap_nym_condition_talkedToSolo1(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "ep3_clone_relics_nym_starmap_4", "talkedToSolo1"));
    }
    public boolean ep3_clone_relics_nym_starmap_nym_condition_foundFourthMap(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "ep3_clone_relics_nym_starmap_4", "foundFourthMap"));
    }
    public boolean ep3_clone_relics_nym_starmap_nym_condition_isGm(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasObjVar(player, "gm"));
    }
    public boolean ep3_clone_relics_nym_starmap_nym_condition_completedQuest4(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedQuest(player, "ep3_clone_relics_nym_starmap_4"));
    }
    public boolean ep3_clone_relics_nym_starmap_nym_condition_notHighEnough(obj_id player, obj_id npc) throws InterruptedException
    {
        int phase = skill.getProfessionPhase(player);
        return (phase < skill.PHASE_TWO);
    }
    public void ep3_clone_relics_nym_starmap_nym_action_sendSignalTalked1(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "findMap1");
    }
    public void ep3_clone_relics_nym_starmap_nym_action_removeQuest1(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.clearQuest(player, "ep3_clone_relics_nym_starmap_1");
    }
    public void ep3_clone_relics_nym_starmap_nym_action_sendSignalWantedCash(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "gotCash");
    }
    public void ep3_clone_relics_nym_starmap_nym_action_sendSignalGaveFirstMap(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "foundMap1");
    }
    public void ep3_clone_relics_nym_starmap_nym_action_grantQuest2(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "ep3_clone_relics_nym_starmap_2");
    }
    public void ep3_clone_relics_nym_starmap_nym_action_sendSignalGaveSecondMap(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "foundMap2");
    }
    public void ep3_clone_relics_nym_starmap_nym_action_sendSignalGaveThirdMap(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "foundMap3");
    }
    public void ep3_clone_relics_nym_starmap_nym_action_grantQuest4(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "ep3_clone_relics_nym_starmap_4");
    }
    public void ep3_clone_relics_nym_starmap_nym_action_grantQuest3(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "ep3_clone_relics_nym_starmap_3");
    }
    public void ep3_clone_relics_nym_starmap_nym_action_sendSignalGiveMoneyToSolo(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "giveMoneyToSolo");
    }
    public void ep3_clone_relics_nym_starmap_nym_action_sendSignalGaveFourthMap(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "foundMap4");
        setObjVar(player, "doneWithNym", 1);
    }
    public void ep3_clone_relics_nym_starmap_nym_action_removeQuest2(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.clearQuest(player, "ep3_clone_relics_nym_starmap_2");
    }
    public void ep3_clone_relics_nym_starmap_nym_action_removeQuest3(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.clearQuest(player, "ep3_clone_relics_nym_starmap_3");
    }
    public void ep3_clone_relics_nym_starmap_nym_action_removeQuest4(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.clearQuest(player, "ep3_clone_relics_nym_starmap_4");
    }
    public void ep3_clone_relics_nym_starmap_nym_action_removeQuestAll(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.clearQuest(player, "ep3_clone_relics_nym_starmap_1");
        groundquests.clearQuest(player, "ep3_clone_relics_nym_starmap_2");
        groundquests.clearQuest(player, "ep3_clone_relics_nym_starmap_3");
        groundquests.clearQuest(player, "ep3_clone_relics_nym_starmap_4");
    }
    public int ep3_clone_relics_nym_starmap_nym_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_382"))
        {
            doAnimationAction(player, "nod");
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_384");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_386");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_677"))
        {
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_252");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_253");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_254");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_255");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_256");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_257");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId", 74);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_nym_starmap_nym_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_386"))
        {
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_388");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_390");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_nym_starmap_nym_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_390"))
        {
            doAnimationAction(player, "nod_head_once");
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_392");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_394");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId", 5);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_nym_starmap_nym_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_394"))
        {
            doAnimationAction(player, "apologize");
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                ep3_clone_relics_nym_starmap_nym_action_sendSignalGaveFourthMap(player, npc);
                string_id message = new string_id(c_stringFile, "s_396");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_nym_starmap_nym_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_400"))
        {
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_402");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_404");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_636"))
        {
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_252");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_253");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_254");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_255");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_256");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_257");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId", 74);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_nym_starmap_nym_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_404"))
        {
            doAnimationAction(player, "nod");
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_406");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_408");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_nym_starmap_nym_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_408"))
        {
            doAnimationAction(player, "shake_head_no");
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                ep3_clone_relics_nym_starmap_nym_action_sendSignalGiveMoneyToSolo(player, npc);
                string_id message = new string_id(c_stringFile, "s_410");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_nym_starmap_nym_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_414"))
        {
            doAnimationAction(player, "apologize");
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_416");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_418"))
        {
            doAnimationAction(player, "shrug_hands");
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_420");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_589"))
        {
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_252");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_253");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_254");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_255");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_256");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_257");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId", 74);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_nym_starmap_nym_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_424"))
        {
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_426");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcEndConversationWithMessage(player, pp);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_428"))
        {
            doAnimationAction(player, "nod");
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_430");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_432");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId", 16);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_541"))
        {
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_252");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_253");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_254");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_255");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_256");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_257");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId", 74);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_nym_starmap_nym_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_432"))
        {
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_434");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_436");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_nym_starmap_nym_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_436"))
        {
            doAnimationAction(player, "nod");
            ep3_clone_relics_nym_starmap_nym_action_grantQuest4(player, npc);
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_438");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_nym_starmap_nym_handleBranch19(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_442"))
        {
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_444");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_446");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId", 20);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_493"))
        {
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_252");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_253");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_254");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_255");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_256");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_257");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId", 74);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_nym_starmap_nym_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_446"))
        {
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_448");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_450");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId", 21);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_nym_starmap_nym_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_450"))
        {
            doAnimationAction(player, "rub_chin_thoughtful");
            ep3_clone_relics_nym_starmap_nym_action_sendSignalGaveThirdMap(player, npc);
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_452");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_nym_starmap_nym_handleBranch23(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_456"))
        {
            doAnimationAction(player, "apologize");
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_458");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_460"))
        {
            doAnimationAction(player, "shrug_hands");
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_462");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_464");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_445"))
        {
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_252");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_253");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_254");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_255");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_256");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_257");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId", 74);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_nym_starmap_nym_handleBranch25(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_464"))
        {
            doAnimationAction(player, "tap_foot");
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_466");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcEndConversationWithMessage(player, pp);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_nym_starmap_nym_handleBranch27(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_470"))
        {
            doAnimationAction(player, "pose_proudly");
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_472");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_474");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId", 28);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_482"))
        {
            doAnimationAction(player, "sigh_deeply");
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_484");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_397"))
        {
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_252");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_253");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_254");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_255");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_256");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_257");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId", 74);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_nym_starmap_nym_handleBranch28(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_474"))
        {
            doAnimationAction(player, "shrug_hands");
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_476");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_478");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId", 29);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_nym_starmap_nym_handleBranch29(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_478"))
        {
            doAnimationAction(player, "nod");
            ep3_clone_relics_nym_starmap_nym_action_grantQuest3(player, npc);
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_480");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_nym_starmap_nym_handleBranch32(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_488"))
        {
            doAnimationAction(player, "shrug_shoulders");
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_490");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_492");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId", 33);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_364"))
        {
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_252");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_253");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_254");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_255");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_256");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_257");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId", 74);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_nym_starmap_nym_handleBranch33(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_492"))
        {
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_494");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_496");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId", 34);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_nym_starmap_nym_handleBranch34(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_496"))
        {
            doAnimationAction(player, "shake_head_no");
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_498");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_500");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId", 35);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_nym_starmap_nym_handleBranch35(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_500"))
        {
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_502");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_504");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId", 36);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_nym_starmap_nym_handleBranch36(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_504"))
        {
            doAnimationAction(player, "nod");
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                ep3_clone_relics_nym_starmap_nym_action_sendSignalGaveSecondMap(player, npc);
                string_id message = new string_id(c_stringFile, "s_506");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_nym_starmap_nym_handleBranch38(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_510"))
        {
            doAnimationAction(player, "apologize");
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_512");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_514"))
        {
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_516");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_518");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId", 40);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_340"))
        {
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_252");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_253");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_254");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_255");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_256");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_257");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId", 74);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_nym_starmap_nym_handleBranch40(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_518"))
        {
            doAnimationAction(player, "nod");
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_520");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_nym_starmap_nym_handleBranch42(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_524"))
        {
            doAnimationAction(player, "nod");
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_526");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_528");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId", 43);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_544"))
        {
            doAnimationAction(player, "shake_head_no");
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_546");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_548");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId", 48);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_316"))
        {
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_252");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_253");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_254");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_255");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_256");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_257");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId", 74);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_nym_starmap_nym_handleBranch43(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_528"))
        {
            doAnimationAction(player, "shrug_hands");
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_530");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_532");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId", 44);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_nym_starmap_nym_handleBranch44(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_532"))
        {
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_534");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_536");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_540");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId", 45);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_nym_starmap_nym_handleBranch45(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_536"))
        {
            doAnimationAction(player, "rub_chin_thoughtful");
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_538");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_540"))
        {
            doAnimationAction(player, "nod");
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                ep3_clone_relics_nym_starmap_nym_action_grantQuest2(player, npc);
                string_id message = new string_id(c_stringFile, "s_542");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_nym_starmap_nym_handleBranch48(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_548"))
        {
            doAnimationAction(player, "shrug_shoulders");
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_550");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_nym_starmap_nym_handleBranch50(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_554"))
        {
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_556");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_558");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_566");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId", 51);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_292"))
        {
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_613");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_615");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_635");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId", 61);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_nym_starmap_nym_handleBranch51(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_558"))
        {
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_560");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_562");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId", 52);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_566"))
        {
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_568");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_570");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId", 54);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_nym_starmap_nym_handleBranch52(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_562"))
        {
            ep3_clone_relics_nym_starmap_nym_action_sendSignalGaveFirstMap(player, npc);
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_564");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_nym_starmap_nym_handleBranch54(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_570"))
        {
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_560");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_562");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId", 52);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_nym_starmap_nym_handleBranch56(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_595"))
        {
            doAnimationAction(player, "apologize");
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_597");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_599"))
        {
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_601");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_603");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId", 58);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_230"))
        {
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_252");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_253");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_254");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_255");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_256");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_257");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId", 74);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_nym_starmap_nym_handleBranch58(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_603"))
        {
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_605");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_607");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_665");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId", 59);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_nym_starmap_nym_handleBranch59(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_607"))
        {
            doAnimationAction(player, "explain");
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_609");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_611");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_645");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_655");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId", 60);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_665"))
        {
            doAnimationAction(player, "shake_head_no");
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_667");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_669");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId", 73);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_nym_starmap_nym_handleBranch60(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_611"))
        {
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_613");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_615");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_635");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId", 61);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_645"))
        {
            doAnimationAction(player, "rub_chin_thoughtful");
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_647");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_649");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_651");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId", 69);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_655"))
        {
            doAnimationAction(player, "shake_head_no");
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_657");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_659");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_663");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId", 71);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_nym_starmap_nym_handleBranch61(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_615"))
        {
            doAnimationAction(player, "nod");
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_617");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_619");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_623");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId", 62);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_635"))
        {
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_637");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_639");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_641");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId", 67);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_nym_starmap_nym_handleBranch62(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_619"))
        {
            doAnimationAction(player, "nod");
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                ep3_clone_relics_nym_starmap_nym_action_sendSignalTalked1(player, npc);
                string_id message = new string_id(c_stringFile, "s_621");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_623"))
        {
            doAnimationAction(player, "rub_chin_thoughtful");
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_625");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_627");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_631");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId", 64);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_nym_starmap_nym_handleBranch64(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_627"))
        {
            doAnimationAction(player, "shake_head_no");
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                ep3_clone_relics_nym_starmap_nym_action_sendSignalTalked1(player, npc);
                string_id message = new string_id(c_stringFile, "s_629");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_631"))
        {
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                ep3_clone_relics_nym_starmap_nym_action_removeQuest1(player, npc);
                string_id message = new string_id(c_stringFile, "s_633");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_nym_starmap_nym_handleBranch67(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_639"))
        {
            doAnimationAction(player, "rub_chin_thoughtful");
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_617");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_619");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_623");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId", 62);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_641"))
        {
            doAnimationAction(player, "shake_head_no");
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                ep3_clone_relics_nym_starmap_nym_action_removeQuest1(player, npc);
                string_id message = new string_id(c_stringFile, "s_643");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_nym_starmap_nym_handleBranch69(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_649"))
        {
            doAnimationAction(player, "nod_head_multiple");
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_613");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_615");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_635");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId", 61);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_651"))
        {
            doAnimationAction(player, "shake_head_no");
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                ep3_clone_relics_nym_starmap_nym_action_removeQuest1(player, npc);
                string_id message = new string_id(c_stringFile, "s_653");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_nym_starmap_nym_handleBranch71(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_659"))
        {
            doAnimationAction(player, "shake_head_no");
            ep3_clone_relics_nym_starmap_nym_action_sendSignalTalked1(player, npc);
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                ep3_clone_relics_nym_starmap_nym_action_sendSignalWantedCash(player, npc);
                string_id message = new string_id(c_stringFile, "s_661");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_663"))
        {
            doAnimationAction(player, "rub_chin_thoughtful");
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_613");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_615");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_635");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId", 61);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_nym_starmap_nym_handleBranch73(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_669"))
        {
            doAnimationAction(player, "slow_down");
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_609");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_611");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_645");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_655");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId", 60);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_nym_starmap_nym_handleBranch74(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_253"))
        {
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                ep3_clone_relics_nym_starmap_nym_action_removeQuest1(player, npc);
                string_id message = new string_id(c_stringFile, "s_258");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_254"))
        {
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                ep3_clone_relics_nym_starmap_nym_action_removeQuest2(player, npc);
                string_id message = new string_id(c_stringFile, "s_260");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_255"))
        {
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                ep3_clone_relics_nym_starmap_nym_action_removeQuest3(player, npc);
                string_id message = new string_id(c_stringFile, "s_262");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_256"))
        {
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                ep3_clone_relics_nym_starmap_nym_action_removeQuest4(player, npc);
                string_id message = new string_id(c_stringFile, "s_264");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_257"))
        {
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                ep3_clone_relics_nym_starmap_nym_action_removeQuestAll(player, npc);
                string_id message = new string_id(c_stringFile, "s_266");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_nym_starmap_nym_handleBranch80(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_574"))
        {
            doAnimationAction(player, "apologize");
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_576");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_578");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_582");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId", 81);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_586"))
        {
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_588");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_590");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId", 84);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_268"))
        {
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_252");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_253");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_254");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_255");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_256");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_257");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId", 74);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_nym_starmap_nym_handleBranch81(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_578"))
        {
            doAnimationAction(player, "rub_chin_thoughtful");
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_580");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_582"))
        {
            doAnimationAction(player, "nod");
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                ep3_clone_relics_nym_starmap_nym_action_removeQuest1(player, npc);
                string_id message = new string_id(c_stringFile, "s_584");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_nym_starmap_nym_handleBranch84(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_590"))
        {
            doAnimationAction(player, "shrug_hands");
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_592");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
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
            detachScript(self, "conversation.ep3_clone_relics_nym_starmap_nym");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setName(self, new string_id("ep3/npc_names", "clone_relics_nym"));
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setName(self, new string_id("ep3/npc_names", "clone_relics_nym"));
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
        detachScript(self, "conversation.ep3_clone_relics_nym_starmap_nym");
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
        if (ep3_clone_relics_nym_starmap_nym_condition_completedQuest4(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_265");
            prose_package pp = new prose_package();
            pp.stringId = message;
            pp.actor.set(player);
            pp.target.set(npc);
            chat.chat(npc, player, null, null, pp);
            return SCRIPT_CONTINUE;
        }
        if (ep3_clone_relics_nym_starmap_nym_condition_foundFourthMap(player, npc))
        {
            doAnimationAction(player, "tiphat");
            string_id message = new string_id(c_stringFile, "s_380");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_clone_relics_nym_starmap_nym_condition_isGm(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_382");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_677");
                }
                utils.setScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId", 2);
                npcStartConversation(player, npc, "ep3_clone_relics_nym_starmap_nym", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_clone_relics_nym_starmap_nym_condition_talkedToSolo1(player, npc))
        {
            doAnimationAction(player, "tiphat");
            string_id message = new string_id(c_stringFile, "s_398");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_clone_relics_nym_starmap_nym_condition_isGm(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_400");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_636");
                }
                utils.setScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId", 7);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "ep3_clone_relics_nym_starmap_nym", null, pp, responses);
            }
            else 
            {
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                chat.chat(npc, player, null, null, pp);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_clone_relics_nym_starmap_nym_condition_onQuest4(player, npc))
        {
            doAnimationAction(player, "tiphat");
            string_id message = new string_id(c_stringFile, "s_412");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (ep3_clone_relics_nym_starmap_nym_condition_isGm(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_414");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_418");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_589");
                }
                utils.setScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId", 11);
                npcStartConversation(player, npc, "ep3_clone_relics_nym_starmap_nym", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_clone_relics_nym_starmap_nym_condition_completedQuest3(player, npc))
        {
            doAnimationAction(player, "tiphat");
            string_id message = new string_id(c_stringFile, "s_422");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (ep3_clone_relics_nym_starmap_nym_condition_isGm(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_424");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_428");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_541");
                }
                utils.setScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId", 14);
                npcStartConversation(player, npc, "ep3_clone_relics_nym_starmap_nym", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_clone_relics_nym_starmap_nym_condition_foundThirdMap(player, npc))
        {
            doAnimationAction(player, "catchbreath");
            string_id message = new string_id(c_stringFile, "s_440");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_clone_relics_nym_starmap_nym_condition_isGm(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_442");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_493");
                }
                utils.setScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId", 19);
                npcStartConversation(player, npc, "ep3_clone_relics_nym_starmap_nym", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_clone_relics_nym_starmap_nym_condition_onQuest3(player, npc))
        {
            doAnimationAction(player, "tiphat");
            string_id message = new string_id(c_stringFile, "s_454");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (ep3_clone_relics_nym_starmap_nym_condition_isGm(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_456");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_460");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_445");
                }
                utils.setScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId", 23);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "ep3_clone_relics_nym_starmap_nym", null, pp, responses);
            }
            else 
            {
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                chat.chat(npc, player, null, null, pp);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_clone_relics_nym_starmap_nym_condition_completedQuest2(player, npc))
        {
            doAnimationAction(player, "tiphat");
            string_id message = new string_id(c_stringFile, "s_468");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (ep3_clone_relics_nym_starmap_nym_condition_isGm(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_470");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_482");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_397");
                }
                utils.setScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId", 27);
                npcStartConversation(player, npc, "ep3_clone_relics_nym_starmap_nym", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_clone_relics_nym_starmap_nym_condition_foundSecondMap(player, npc))
        {
            doAnimationAction(player, "tiphat");
            string_id message = new string_id(c_stringFile, "s_486");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_clone_relics_nym_starmap_nym_condition_isGm(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_488");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_364");
                }
                utils.setScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId", 32);
                npcStartConversation(player, npc, "ep3_clone_relics_nym_starmap_nym", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_clone_relics_nym_starmap_nym_condition_onQuest2(player, npc))
        {
            doAnimationAction(player, "tiphat");
            string_id message = new string_id(c_stringFile, "s_508");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (ep3_clone_relics_nym_starmap_nym_condition_isGm(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_510");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_514");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_340");
                }
                utils.setScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId", 38);
                npcStartConversation(player, npc, "ep3_clone_relics_nym_starmap_nym", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_clone_relics_nym_starmap_nym_condition_completedQuest1(player, npc))
        {
            doAnimationAction(player, "tiphat");
            string_id message = new string_id(c_stringFile, "s_522");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (ep3_clone_relics_nym_starmap_nym_condition_isGm(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_524");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_544");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_316");
                }
                utils.setScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId", 42);
                npcStartConversation(player, npc, "ep3_clone_relics_nym_starmap_nym", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_clone_relics_nym_starmap_nym_condition_foundFirstMap(player, npc))
        {
            doAnimationAction(player, "tiphat");
            string_id message = new string_id(c_stringFile, "s_552");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_clone_relics_nym_starmap_nym_condition_isGm(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_554");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_292");
                }
                utils.setScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId", 50);
                npcStartConversation(player, npc, "ep3_clone_relics_nym_starmap_nym", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_clone_relics_nym_starmap_nym_condition_notHighEnough(player, npc))
        {
            doAnimationAction(npc, "dismiss");
            string_id message = new string_id(c_stringFile, "s_175");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_clone_relics_nym_starmap_nym_condition_onTask1(player, npc))
        {
            doAnimationAction(player, "tiphat");
            string_id message = new string_id(c_stringFile, "s_1544");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (ep3_clone_relics_nym_starmap_nym_condition_isGm(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_595");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_599");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_230");
                }
                utils.setScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId", 56);
                npcStartConversation(player, npc, "ep3_clone_relics_nym_starmap_nym", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_clone_relics_nym_starmap_nym_condition_onQuest1(player, npc))
        {
            doAnimationAction(player, "tiphat");
            string_id message = new string_id(c_stringFile, "s_572");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (ep3_clone_relics_nym_starmap_nym_condition_isGm(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_574");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_586");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_268");
                }
                utils.setScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId", 80);
                npcStartConversation(player, npc, "ep3_clone_relics_nym_starmap_nym", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_clone_relics_nym_starmap_nym_condition__defaultCondition(player, npc))
        {
            doAnimationAction(player, "tiphat");
            string_id message = new string_id(c_stringFile, "s_240");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("ep3_clone_relics_nym_starmap_nym"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
        if (branchId == 2 && ep3_clone_relics_nym_starmap_nym_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && ep3_clone_relics_nym_starmap_nym_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && ep3_clone_relics_nym_starmap_nym_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && ep3_clone_relics_nym_starmap_nym_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && ep3_clone_relics_nym_starmap_nym_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && ep3_clone_relics_nym_starmap_nym_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && ep3_clone_relics_nym_starmap_nym_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && ep3_clone_relics_nym_starmap_nym_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && ep3_clone_relics_nym_starmap_nym_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && ep3_clone_relics_nym_starmap_nym_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && ep3_clone_relics_nym_starmap_nym_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && ep3_clone_relics_nym_starmap_nym_handleBranch19(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && ep3_clone_relics_nym_starmap_nym_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && ep3_clone_relics_nym_starmap_nym_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && ep3_clone_relics_nym_starmap_nym_handleBranch23(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && ep3_clone_relics_nym_starmap_nym_handleBranch25(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 27 && ep3_clone_relics_nym_starmap_nym_handleBranch27(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 28 && ep3_clone_relics_nym_starmap_nym_handleBranch28(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && ep3_clone_relics_nym_starmap_nym_handleBranch29(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 32 && ep3_clone_relics_nym_starmap_nym_handleBranch32(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 33 && ep3_clone_relics_nym_starmap_nym_handleBranch33(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 34 && ep3_clone_relics_nym_starmap_nym_handleBranch34(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 35 && ep3_clone_relics_nym_starmap_nym_handleBranch35(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 36 && ep3_clone_relics_nym_starmap_nym_handleBranch36(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 38 && ep3_clone_relics_nym_starmap_nym_handleBranch38(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 40 && ep3_clone_relics_nym_starmap_nym_handleBranch40(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 42 && ep3_clone_relics_nym_starmap_nym_handleBranch42(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 43 && ep3_clone_relics_nym_starmap_nym_handleBranch43(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 44 && ep3_clone_relics_nym_starmap_nym_handleBranch44(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 45 && ep3_clone_relics_nym_starmap_nym_handleBranch45(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 48 && ep3_clone_relics_nym_starmap_nym_handleBranch48(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 50 && ep3_clone_relics_nym_starmap_nym_handleBranch50(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 51 && ep3_clone_relics_nym_starmap_nym_handleBranch51(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 52 && ep3_clone_relics_nym_starmap_nym_handleBranch52(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 54 && ep3_clone_relics_nym_starmap_nym_handleBranch54(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 56 && ep3_clone_relics_nym_starmap_nym_handleBranch56(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 58 && ep3_clone_relics_nym_starmap_nym_handleBranch58(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 59 && ep3_clone_relics_nym_starmap_nym_handleBranch59(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 60 && ep3_clone_relics_nym_starmap_nym_handleBranch60(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 61 && ep3_clone_relics_nym_starmap_nym_handleBranch61(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 62 && ep3_clone_relics_nym_starmap_nym_handleBranch62(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 64 && ep3_clone_relics_nym_starmap_nym_handleBranch64(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 67 && ep3_clone_relics_nym_starmap_nym_handleBranch67(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 69 && ep3_clone_relics_nym_starmap_nym_handleBranch69(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 71 && ep3_clone_relics_nym_starmap_nym_handleBranch71(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 73 && ep3_clone_relics_nym_starmap_nym_handleBranch73(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 74 && ep3_clone_relics_nym_starmap_nym_handleBranch74(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 80 && ep3_clone_relics_nym_starmap_nym_handleBranch80(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 81 && ep3_clone_relics_nym_starmap_nym_handleBranch81(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 84 && ep3_clone_relics_nym_starmap_nym_handleBranch84(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.ep3_clone_relics_nym_starmap_nym.branchId");
        return SCRIPT_CONTINUE;
    }
}
