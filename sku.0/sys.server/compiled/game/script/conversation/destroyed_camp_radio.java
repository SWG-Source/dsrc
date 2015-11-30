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

public class destroyed_camp_radio extends script.base_script
{
    public destroyed_camp_radio()
    {
    }
    public static String c_stringFile = "conversation/destroyed_camp_radio";
    public boolean destroyed_camp_radio_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean destroyed_camp_radio_condition_isOnTask01(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_kachirho_destroyed_camp", "reportLisum");
    }
    public boolean destroyed_camp_radio_condition_isOnTask02(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_kachirho_destroyed_camp", "foundItems");
    }
    public boolean destroyed_camp_radio_condition_isOnTask03(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_kachirho_destroyed_camp", "canopyDead");
    }
    public boolean destroyed_camp_radio_condition_isOnMission(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "ep3_kachirho_destroyed_camp");
    }
    public boolean destroyed_camp_radio_condition_hasCompletedSeries(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "ep3_kachirho_destroyed_camp") || groundquests.isTaskActive(player, "ep3_kachirho_destroyed_camp", "unlockContainer");
    }
    public void destroyed_camp_radio_action_doSignal01(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "huntResearchers");
    }
    public void destroyed_camp_radio_action_doSignal02(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "huntCanopy");
    }
    public void destroyed_camp_radio_action_doSignal03(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "codeReceived");
    }
    public void destroyed_camp_radio_action_grantMission(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "ep3_kachirho_destroyed_camp");
    }
    public void destroyed_camp_radio_action_sendStatic(obj_id player, obj_id npc) throws InterruptedException
    {
        playClientEffectLoc(player, "clienteffect/ep3_radio_static.cef.cef", getLocation(npc), 0.0f);
    }
    public int destroyed_camp_radio_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_411"))
        {
            if (destroyed_camp_radio_condition__defaultCondition(player, npc))
            {
                destroyed_camp_radio_action_doSignal03(player, npc);
                string_id message = new string_id(c_stringFile, "s_413");
                utils.removeScriptVar(player, "conversation.destroyed_camp_radio.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int destroyed_camp_radio_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_417"))
        {
            if (destroyed_camp_radio_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_419");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (destroyed_camp_radio_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_421");
                    }
                    utils.setScriptVar(player, "conversation.destroyed_camp_radio.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.destroyed_camp_radio.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int destroyed_camp_radio_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_421"))
        {
            if (destroyed_camp_radio_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_68");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (destroyed_camp_radio_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.destroyed_camp_radio.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.destroyed_camp_radio.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int destroyed_camp_radio_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_70"))
        {
            if (destroyed_camp_radio_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_72");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (destroyed_camp_radio_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (destroyed_camp_radio_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_74");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_78");
                    }
                    utils.setScriptVar(player, "conversation.destroyed_camp_radio.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.destroyed_camp_radio.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int destroyed_camp_radio_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_74"))
        {
            if (destroyed_camp_radio_condition__defaultCondition(player, npc))
            {
                destroyed_camp_radio_action_doSignal02(player, npc);
                string_id message = new string_id(c_stringFile, "s_76");
                utils.removeScriptVar(player, "conversation.destroyed_camp_radio.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_78"))
        {
            if (destroyed_camp_radio_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_80");
                utils.removeScriptVar(player, "conversation.destroyed_camp_radio.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int destroyed_camp_radio_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_443"))
        {
            if (destroyed_camp_radio_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_445");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (destroyed_camp_radio_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_447");
                    }
                    utils.setScriptVar(player, "conversation.destroyed_camp_radio.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.destroyed_camp_radio.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int destroyed_camp_radio_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_447"))
        {
            if (destroyed_camp_radio_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_449");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (destroyed_camp_radio_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_451");
                    }
                    utils.setScriptVar(player, "conversation.destroyed_camp_radio.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.destroyed_camp_radio.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int destroyed_camp_radio_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_451"))
        {
            if (destroyed_camp_radio_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_453");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (destroyed_camp_radio_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_455");
                    }
                    utils.setScriptVar(player, "conversation.destroyed_camp_radio.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.destroyed_camp_radio.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int destroyed_camp_radio_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_455"))
        {
            if (destroyed_camp_radio_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_457");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (destroyed_camp_radio_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (destroyed_camp_radio_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_459");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_463");
                    }
                    utils.setScriptVar(player, "conversation.destroyed_camp_radio.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.destroyed_camp_radio.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int destroyed_camp_radio_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_459"))
        {
            if (destroyed_camp_radio_condition__defaultCondition(player, npc))
            {
                destroyed_camp_radio_action_doSignal01(player, npc);
                string_id message = new string_id(c_stringFile, "s_461");
                utils.removeScriptVar(player, "conversation.destroyed_camp_radio.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_463"))
        {
            if (destroyed_camp_radio_condition__defaultCondition(player, npc))
            {
                destroyed_camp_radio_action_sendStatic(player, npc);
                string_id message = new string_id(c_stringFile, "s_465");
                utils.removeScriptVar(player, "conversation.destroyed_camp_radio.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int destroyed_camp_radio_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_105"))
        {
            if (destroyed_camp_radio_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_106");
                utils.removeScriptVar(player, "conversation.destroyed_camp_radio.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int destroyed_camp_radio_handleBranch19(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_469"))
        {
            if (destroyed_camp_radio_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_471");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (destroyed_camp_radio_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_473");
                    }
                    utils.setScriptVar(player, "conversation.destroyed_camp_radio.branchId", 20);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.destroyed_camp_radio.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int destroyed_camp_radio_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_473"))
        {
            if (destroyed_camp_radio_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_475");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (destroyed_camp_radio_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (destroyed_camp_radio_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_477");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_481");
                    }
                    utils.setScriptVar(player, "conversation.destroyed_camp_radio.branchId", 21);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.destroyed_camp_radio.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int destroyed_camp_radio_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_477"))
        {
            if (destroyed_camp_radio_condition__defaultCondition(player, npc))
            {
                destroyed_camp_radio_action_grantMission(player, npc);
                string_id message = new string_id(c_stringFile, "s_479");
                utils.removeScriptVar(player, "conversation.destroyed_camp_radio.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_481"))
        {
            if (destroyed_camp_radio_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_483");
                utils.removeScriptVar(player, "conversation.destroyed_camp_radio.branchId");
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
            detachScript(self, "conversation.destroyed_camp_radio");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
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
        detachScript(self, "conversation.destroyed_camp_radio");
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
        if (destroyed_camp_radio_condition_hasCompletedSeries(player, npc))
        {
            destroyed_camp_radio_action_sendStatic(player, npc);
            string_id message = new string_id(c_stringFile, "s_407");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (destroyed_camp_radio_condition_isOnTask03(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_409");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (destroyed_camp_radio_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_411");
                }
                utils.setScriptVar(player, "conversation.destroyed_camp_radio.branchId", 2);
                npcStartConversation(player, npc, "destroyed_camp_radio", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (destroyed_camp_radio_condition_isOnTask02(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_415");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (destroyed_camp_radio_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_417");
                }
                utils.setScriptVar(player, "conversation.destroyed_camp_radio.branchId", 4);
                npcStartConversation(player, npc, "destroyed_camp_radio", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (destroyed_camp_radio_condition_isOnTask01(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_441");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (destroyed_camp_radio_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_443");
                }
                utils.setScriptVar(player, "conversation.destroyed_camp_radio.branchId", 10);
                npcStartConversation(player, npc, "destroyed_camp_radio", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (destroyed_camp_radio_condition_isOnMission(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_104");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (destroyed_camp_radio_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_105");
                }
                utils.setScriptVar(player, "conversation.destroyed_camp_radio.branchId", 17);
                npcStartConversation(player, npc, "destroyed_camp_radio", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (destroyed_camp_radio_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_467");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (destroyed_camp_radio_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_469");
                }
                utils.setScriptVar(player, "conversation.destroyed_camp_radio.branchId", 19);
                npcStartConversation(player, npc, "destroyed_camp_radio", message, responses);
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
        if (!conversationId.equals("destroyed_camp_radio"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.destroyed_camp_radio.branchId");
        if (branchId == 2 && destroyed_camp_radio_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && destroyed_camp_radio_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && destroyed_camp_radio_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && destroyed_camp_radio_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && destroyed_camp_radio_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && destroyed_camp_radio_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && destroyed_camp_radio_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && destroyed_camp_radio_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && destroyed_camp_radio_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && destroyed_camp_radio_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && destroyed_camp_radio_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && destroyed_camp_radio_handleBranch19(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && destroyed_camp_radio_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && destroyed_camp_radio_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.destroyed_camp_radio.branchId");
        return SCRIPT_CONTINUE;
    }
}
