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
import script.library.groundquests;
import script.library.utils;

public class corellia_droid_factory_sergeant_mener extends script.base_script
{
    public corellia_droid_factory_sergeant_mener()
    {
    }
    public static String c_stringFile = "conversation/corellia_droid_factory_sergeant_mener";
    public boolean corellia_droid_factory_sergeant_mener_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean corellia_droid_factory_sergeant_mener_condition_isBDNotActiveOrComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        return !groundquests.isQuestActiveOrComplete(player, "corellia_droid_bunker_destroy_battle_droid");
    }
    public boolean corellia_droid_factory_sergeant_mener_condition_isBDFinishTaskActive(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "corellia_droid_bunker_destroy_battle_droid", "returnTenBD");
    }
    public boolean corellia_droid_factory_sergeant_mener_condition_isSBDFinishTaskActive(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "corellia_droid_bunker_destroy_super_battle_droid", "returnTenSBD");
    }
    public boolean corellia_droid_factory_sergeant_mener_condition_isDroidekaFinishTaskActive(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "corellia_droid_bunker_destroy_droideka", "returnFiveDroideka");
    }
    public boolean corellia_droid_factory_sergeant_mener_condition_isBossDroidFinishTaskActive(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "corellia_droid_bunker_destroy_boss_droid", "returnBossDroid");
    }
    public boolean corellia_droid_factory_sergeant_mener_condition_isBossDroidActive(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "corellia_droid_bunker_destroy_boss_droid");
    }
    public boolean corellia_droid_factory_sergeant_mener_condition_isDroidekaFinished(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "corellia_droid_bunker_destroy_droideka");
    }
    public boolean corellia_droid_factory_sergeant_mener_condition_isDroidekaActive(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "corellia_droid_bunker_destroy_droideka");
    }
    public boolean corellia_droid_factory_sergeant_mener_condition_isBossDroidFinished(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "corellia_droid_bunker_destroy_boss_droid");
    }
    public boolean corellia_droid_factory_sergeant_mener_condition_isSBDFinished(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "corellia_droid_bunker_destroy_super_battle_droid");
    }
    public boolean corellia_droid_factory_sergeant_mener_condition_isSBDActive(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "corellia_droid_bunker_destroy_super_battle_droid");
    }
    public boolean corellia_droid_factory_sergeant_mener_condition_isBDFinished(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "corellia_droid_bunker_destroy_battle_droid");
    }
    public boolean corellia_droid_factory_sergeant_mener_condition_isBDActive(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "corellia_droid_bunker_destroy_battle_droid");
    }
    public void corellia_droid_factory_sergeant_mener_action_signalToFinishBD(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "returnTenBD");
    }
    public void corellia_droid_factory_sergeant_mener_action_signalToFinishSBD(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "returnTenSBD");
    }
    public void corellia_droid_factory_sergeant_mener_action_signalToFinishDroideka(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "returnFiveDroideka");
    }
    public void corellia_droid_factory_sergeant_mener_action_signalToFinishBossDroid(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "returnBossDroid");
    }
    public void corellia_droid_factory_sergeant_mener_action_grantBDQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "corellia_droid_bunker_destroy_battle_droid");
    }
    public void corellia_droid_factory_sergeant_mener_action_grantSBDQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "corellia_droid_bunker_destroy_super_battle_droid");
    }
    public void corellia_droid_factory_sergeant_mener_action_grantDroidekaQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "corellia_droid_bunker_destroy_droideka");
    }
    public void corellia_droid_factory_sergeant_mener_action_grantBossDroidQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "corellia_droid_bunker_destroy_boss_droid");
    }
    public int corellia_droid_factory_sergeant_mener_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_12"))
        {
            if (corellia_droid_factory_sergeant_mener_condition__defaultCondition(player, npc))
            {
                corellia_droid_factory_sergeant_mener_action_grantBossDroidQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_14");
                utils.removeScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_16"))
        {
            if (corellia_droid_factory_sergeant_mener_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_18");
                utils.removeScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_droid_factory_sergeant_mener_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_70"))
        {
            if (corellia_droid_factory_sergeant_mener_condition_isBossDroidFinished(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_4");
                utils.removeScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (corellia_droid_factory_sergeant_mener_condition_isBossDroidFinishTaskActive(player, npc))
            {
                corellia_droid_factory_sergeant_mener_action_signalToFinishBossDroid(player, npc);
                string_id message = new string_id(c_stringFile, "s_6");
                utils.removeScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (corellia_droid_factory_sergeant_mener_condition_isBossDroidActive(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_8");
                utils.removeScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (corellia_droid_factory_sergeant_mener_condition_isDroidekaFinished(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_10");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_droid_factory_sergeant_mener_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_droid_factory_sergeant_mener_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_12");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_16");
                    }
                    utils.setScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (corellia_droid_factory_sergeant_mener_condition_isDroidekaFinishTaskActive(player, npc))
            {
                corellia_droid_factory_sergeant_mener_action_signalToFinishDroideka(player, npc);
                string_id message = new string_id(c_stringFile, "s_36");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_droid_factory_sergeant_mener_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_70");
                    }
                    utils.setScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (corellia_droid_factory_sergeant_mener_condition_isDroidekaActive(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_34");
                utils.removeScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (corellia_droid_factory_sergeant_mener_condition_isSBDFinished(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_38");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_droid_factory_sergeant_mener_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_droid_factory_sergeant_mener_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_40");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_44");
                    }
                    utils.setScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (corellia_droid_factory_sergeant_mener_condition_isSBDFinishTaskActive(player, npc))
            {
                corellia_droid_factory_sergeant_mener_action_signalToFinishSBD(player, npc);
                string_id message = new string_id(c_stringFile, "s_48");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_droid_factory_sergeant_mener_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_71");
                    }
                    utils.setScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (corellia_droid_factory_sergeant_mener_condition_isSBDActive(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_49");
                utils.removeScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (corellia_droid_factory_sergeant_mener_condition_isBDFinished(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_50");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_droid_factory_sergeant_mener_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_droid_factory_sergeant_mener_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_58");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_59");
                    }
                    utils.setScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (corellia_droid_factory_sergeant_mener_condition_isBDFinishTaskActive(player, npc))
            {
                corellia_droid_factory_sergeant_mener_action_signalToFinishBD(player, npc);
                string_id message = new string_id(c_stringFile, "s_51");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_droid_factory_sergeant_mener_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_72");
                    }
                    utils.setScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId", 19);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (corellia_droid_factory_sergeant_mener_condition_isBDActive(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_52");
                utils.removeScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (corellia_droid_factory_sergeant_mener_condition_isBDNotActiveOrComplete(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_53");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_droid_factory_sergeant_mener_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_droid_factory_sergeant_mener_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_55");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_67");
                    }
                    utils.setScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId", 21);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_droid_factory_sergeant_mener_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_40"))
        {
            if (corellia_droid_factory_sergeant_mener_condition__defaultCondition(player, npc))
            {
                corellia_droid_factory_sergeant_mener_action_grantDroidekaQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_42");
                utils.removeScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_44"))
        {
            if (corellia_droid_factory_sergeant_mener_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_46");
                utils.removeScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_droid_factory_sergeant_mener_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_71"))
        {
            if (corellia_droid_factory_sergeant_mener_condition_isBossDroidFinished(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_4");
                utils.removeScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (corellia_droid_factory_sergeant_mener_condition_isBossDroidFinishTaskActive(player, npc))
            {
                corellia_droid_factory_sergeant_mener_action_signalToFinishBossDroid(player, npc);
                string_id message = new string_id(c_stringFile, "s_6");
                utils.removeScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (corellia_droid_factory_sergeant_mener_condition_isBossDroidActive(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_8");
                utils.removeScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (corellia_droid_factory_sergeant_mener_condition_isDroidekaFinished(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_10");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_droid_factory_sergeant_mener_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_droid_factory_sergeant_mener_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_12");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_16");
                    }
                    utils.setScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (corellia_droid_factory_sergeant_mener_condition_isDroidekaFinishTaskActive(player, npc))
            {
                corellia_droid_factory_sergeant_mener_action_signalToFinishDroideka(player, npc);
                string_id message = new string_id(c_stringFile, "s_36");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_droid_factory_sergeant_mener_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_70");
                    }
                    utils.setScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (corellia_droid_factory_sergeant_mener_condition_isDroidekaActive(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_34");
                utils.removeScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (corellia_droid_factory_sergeant_mener_condition_isSBDFinished(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_38");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_droid_factory_sergeant_mener_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_droid_factory_sergeant_mener_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_40");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_44");
                    }
                    utils.setScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (corellia_droid_factory_sergeant_mener_condition_isSBDFinishTaskActive(player, npc))
            {
                corellia_droid_factory_sergeant_mener_action_signalToFinishSBD(player, npc);
                string_id message = new string_id(c_stringFile, "s_48");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_droid_factory_sergeant_mener_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_71");
                    }
                    utils.setScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (corellia_droid_factory_sergeant_mener_condition_isSBDActive(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_49");
                utils.removeScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (corellia_droid_factory_sergeant_mener_condition_isBDFinished(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_50");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_droid_factory_sergeant_mener_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_droid_factory_sergeant_mener_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_58");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_59");
                    }
                    utils.setScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (corellia_droid_factory_sergeant_mener_condition_isBDFinishTaskActive(player, npc))
            {
                corellia_droid_factory_sergeant_mener_action_signalToFinishBD(player, npc);
                string_id message = new string_id(c_stringFile, "s_51");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_droid_factory_sergeant_mener_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_72");
                    }
                    utils.setScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId", 19);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (corellia_droid_factory_sergeant_mener_condition_isBDActive(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_52");
                utils.removeScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (corellia_droid_factory_sergeant_mener_condition_isBDNotActiveOrComplete(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_53");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_droid_factory_sergeant_mener_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_droid_factory_sergeant_mener_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_55");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_67");
                    }
                    utils.setScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId", 21);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_droid_factory_sergeant_mener_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_58"))
        {
            if (corellia_droid_factory_sergeant_mener_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_61");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_droid_factory_sergeant_mener_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_droid_factory_sergeant_mener_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_62");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_63");
                    }
                    utils.setScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_59"))
        {
            if (corellia_droid_factory_sergeant_mener_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_60");
                utils.removeScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_droid_factory_sergeant_mener_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_62"))
        {
            if (corellia_droid_factory_sergeant_mener_condition__defaultCondition(player, npc))
            {
                corellia_droid_factory_sergeant_mener_action_grantSBDQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_64");
                utils.removeScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_63"))
        {
            if (corellia_droid_factory_sergeant_mener_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_65");
                utils.removeScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_droid_factory_sergeant_mener_handleBranch19(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_72"))
        {
            if (corellia_droid_factory_sergeant_mener_condition_isBossDroidFinished(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_4");
                utils.removeScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (corellia_droid_factory_sergeant_mener_condition_isBossDroidFinishTaskActive(player, npc))
            {
                corellia_droid_factory_sergeant_mener_action_signalToFinishBossDroid(player, npc);
                string_id message = new string_id(c_stringFile, "s_6");
                utils.removeScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (corellia_droid_factory_sergeant_mener_condition_isBossDroidActive(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_8");
                utils.removeScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (corellia_droid_factory_sergeant_mener_condition_isDroidekaFinished(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_10");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_droid_factory_sergeant_mener_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_droid_factory_sergeant_mener_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_12");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_16");
                    }
                    utils.setScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (corellia_droid_factory_sergeant_mener_condition_isDroidekaFinishTaskActive(player, npc))
            {
                corellia_droid_factory_sergeant_mener_action_signalToFinishDroideka(player, npc);
                string_id message = new string_id(c_stringFile, "s_36");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_droid_factory_sergeant_mener_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_70");
                    }
                    utils.setScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (corellia_droid_factory_sergeant_mener_condition_isDroidekaActive(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_34");
                utils.removeScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (corellia_droid_factory_sergeant_mener_condition_isSBDFinished(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_38");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_droid_factory_sergeant_mener_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_droid_factory_sergeant_mener_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_40");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_44");
                    }
                    utils.setScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (corellia_droid_factory_sergeant_mener_condition_isSBDFinishTaskActive(player, npc))
            {
                corellia_droid_factory_sergeant_mener_action_signalToFinishSBD(player, npc);
                string_id message = new string_id(c_stringFile, "s_48");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_droid_factory_sergeant_mener_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_71");
                    }
                    utils.setScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (corellia_droid_factory_sergeant_mener_condition_isSBDActive(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_49");
                utils.removeScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (corellia_droid_factory_sergeant_mener_condition_isBDFinished(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_50");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_droid_factory_sergeant_mener_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_droid_factory_sergeant_mener_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_58");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_59");
                    }
                    utils.setScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (corellia_droid_factory_sergeant_mener_condition_isBDFinishTaskActive(player, npc))
            {
                corellia_droid_factory_sergeant_mener_action_signalToFinishBD(player, npc);
                string_id message = new string_id(c_stringFile, "s_51");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_droid_factory_sergeant_mener_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_72");
                    }
                    utils.setScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId", 19);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (corellia_droid_factory_sergeant_mener_condition_isBDActive(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_52");
                utils.removeScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (corellia_droid_factory_sergeant_mener_condition_isBDNotActiveOrComplete(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_53");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_droid_factory_sergeant_mener_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_droid_factory_sergeant_mener_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_55");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_67");
                    }
                    utils.setScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId", 21);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_droid_factory_sergeant_mener_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_55"))
        {
            if (corellia_droid_factory_sergeant_mener_condition__defaultCondition(player, npc))
            {
                corellia_droid_factory_sergeant_mener_action_grantBDQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_57");
                utils.removeScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_67"))
        {
            if (corellia_droid_factory_sergeant_mener_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_69");
                utils.removeScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isTangible(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.corellia_droid_factory_sergeant_mener");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
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
        detachScript(self, "conversation.corellia_droid_factory_sergeant_mener");
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
        if (corellia_droid_factory_sergeant_mener_condition_isBossDroidFinished(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_4");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corellia_droid_factory_sergeant_mener_condition_isBossDroidFinishTaskActive(player, npc))
        {
            corellia_droid_factory_sergeant_mener_action_signalToFinishBossDroid(player, npc);
            string_id message = new string_id(c_stringFile, "s_6");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corellia_droid_factory_sergeant_mener_condition_isBossDroidActive(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_8");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corellia_droid_factory_sergeant_mener_condition_isDroidekaFinished(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_10");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_droid_factory_sergeant_mener_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (corellia_droid_factory_sergeant_mener_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_12");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_16");
                }
                utils.setScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId", 4);
                npcStartConversation(player, npc, "corellia_droid_factory_sergeant_mener", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_droid_factory_sergeant_mener_condition_isDroidekaFinishTaskActive(player, npc))
        {
            corellia_droid_factory_sergeant_mener_action_signalToFinishDroideka(player, npc);
            string_id message = new string_id(c_stringFile, "s_36");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_droid_factory_sergeant_mener_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_70");
                }
                utils.setScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId", 7);
                npcStartConversation(player, npc, "corellia_droid_factory_sergeant_mener", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_droid_factory_sergeant_mener_condition_isDroidekaActive(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_34");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corellia_droid_factory_sergeant_mener_condition_isSBDFinished(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_38");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_droid_factory_sergeant_mener_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (corellia_droid_factory_sergeant_mener_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_40");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_44");
                }
                utils.setScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId", 9);
                npcStartConversation(player, npc, "corellia_droid_factory_sergeant_mener", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_droid_factory_sergeant_mener_condition_isSBDFinishTaskActive(player, npc))
        {
            corellia_droid_factory_sergeant_mener_action_signalToFinishSBD(player, npc);
            string_id message = new string_id(c_stringFile, "s_48");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_droid_factory_sergeant_mener_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_71");
                }
                utils.setScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId", 12);
                npcStartConversation(player, npc, "corellia_droid_factory_sergeant_mener", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_droid_factory_sergeant_mener_condition_isSBDActive(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_49");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corellia_droid_factory_sergeant_mener_condition_isBDFinished(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_50");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_droid_factory_sergeant_mener_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (corellia_droid_factory_sergeant_mener_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_58");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_59");
                }
                utils.setScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId", 14);
                npcStartConversation(player, npc, "corellia_droid_factory_sergeant_mener", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_droid_factory_sergeant_mener_condition_isBDFinishTaskActive(player, npc))
        {
            corellia_droid_factory_sergeant_mener_action_signalToFinishBD(player, npc);
            string_id message = new string_id(c_stringFile, "s_51");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_droid_factory_sergeant_mener_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_72");
                }
                utils.setScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId", 19);
                npcStartConversation(player, npc, "corellia_droid_factory_sergeant_mener", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_droid_factory_sergeant_mener_condition_isBDActive(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_52");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corellia_droid_factory_sergeant_mener_condition_isBDNotActiveOrComplete(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_53");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_droid_factory_sergeant_mener_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (corellia_droid_factory_sergeant_mener_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_55");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_67");
                }
                utils.setScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId", 21);
                npcStartConversation(player, npc, "corellia_droid_factory_sergeant_mener", message, responses);
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
        if (!conversationId.equals("corellia_droid_factory_sergeant_mener"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId");
        if (branchId == 4 && corellia_droid_factory_sergeant_mener_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && corellia_droid_factory_sergeant_mener_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && corellia_droid_factory_sergeant_mener_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && corellia_droid_factory_sergeant_mener_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && corellia_droid_factory_sergeant_mener_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && corellia_droid_factory_sergeant_mener_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && corellia_droid_factory_sergeant_mener_handleBranch19(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && corellia_droid_factory_sergeant_mener_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.corellia_droid_factory_sergeant_mener.branchId");
        return SCRIPT_CONTINUE;
    }
}
