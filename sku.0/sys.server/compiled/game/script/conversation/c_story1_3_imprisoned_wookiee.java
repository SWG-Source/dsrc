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

public class c_story1_3_imprisoned_wookiee extends script.base_script
{
    public c_story1_3_imprisoned_wookiee()
    {
    }
    public static String c_stringFile = "conversation/c_story1_3_imprisoned_wookiee";
    public boolean c_story1_3_imprisoned_wookiee_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean c_story1_3_imprisoned_wookiee_condition_onTaskWookiee(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "c_story1_3_imp", "talkWookiee1") || groundquests.isTaskActive(player, "c_story1_3_neu", "talkWookiee1") || groundquests.isTaskActive(player, "c_story1_3_reb", "talkWookiee1"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean c_story1_3_imprisoned_wookiee_condition_onTaskWookiee2(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "c_story1_3_imp", "talkWookiee2") || groundquests.isTaskActive(player, "c_story1_3_neu", "talkWookiee2") || groundquests.isTaskActive(player, "c_story1_3_reb", "talkWookiee2"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean c_story1_3_imprisoned_wookiee_condition_onTaskWookiee3(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "c_story1_3_imp", "talkWookiee3") || groundquests.isTaskActive(player, "c_story1_3_neu", "talkWookiee3") || groundquests.isTaskActive(player, "c_story1_3_reb", "talkWookiee3"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean c_story1_3_imprisoned_wookiee_condition_onTaskEscortWookiee(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "c_story1_3_imp", "escortWookiee") || groundquests.isTaskActive(player, "c_story1_3_neu", "escortWookiee") || groundquests.isTaskActive(player, "c_story1_3_reb", "escortWookiee"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean c_story1_3_imprisoned_wookiee_condition_onTaskFinished(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.hasCompletedTask(player, "c_story1_3_imp", "talkWookiee3") || groundquests.hasCompletedTask(player, "c_story1_3_neu", "talkWookiee3") || groundquests.hasCompletedTask(player, "c_story1_3_reb", "talkWookiee3"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public void c_story1_3_imprisoned_wookiee_action_signalWookiee1(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "talkedWookiee1");
    }
    public void c_story1_3_imprisoned_wookiee_action_signalWookiee2(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "talkedWookiee2");
    }
    public void c_story1_3_imprisoned_wookiee_action_signalWookiee3(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "talkedWookiee3");
    }
    public void c_story1_3_imprisoned_wookiee_action_finishEscort(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.completeTask(player, "c_story1_3_imp", "escortTask");
        groundquests.completeTask(player, "c_story1_3_neu", "escortTask");
        groundquests.completeTask(player, "c_story1_3_reb", "escortTask");
    }
    public int c_story1_3_imprisoned_wookiee_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_102"))
        {
            if (c_story1_3_imprisoned_wookiee_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                string_id message = new string_id(c_stringFile, "s_104");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_3_imprisoned_wookiee_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_106");
                    }
                    utils.setScriptVar(player, "conversation.c_story1_3_imprisoned_wookiee.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_3_imprisoned_wookiee.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_3_imprisoned_wookiee_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_106"))
        {
            doAnimationAction(player, "shake_head_no");
            if (c_story1_3_imprisoned_wookiee_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                c_story1_3_imprisoned_wookiee_action_signalWookiee3(player, npc);
                string_id message = new string_id(c_stringFile, "s_108");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_3_imprisoned_wookiee_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_story1_3_imprisoned_wookiee_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_110");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_122");
                    }
                    utils.setScriptVar(player, "conversation.c_story1_3_imprisoned_wookiee.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_3_imprisoned_wookiee.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_3_imprisoned_wookiee_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_110"))
        {
            doAnimationAction(player, "taken_aback");
            if (c_story1_3_imprisoned_wookiee_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                string_id message = new string_id(c_stringFile, "s_112");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_3_imprisoned_wookiee_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_114");
                    }
                    utils.setScriptVar(player, "conversation.c_story1_3_imprisoned_wookiee.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_3_imprisoned_wookiee.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_122"))
        {
            doAnimationAction(player, "taken_aback");
            if (c_story1_3_imprisoned_wookiee_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_124");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_3_imprisoned_wookiee_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_126");
                    }
                    utils.setScriptVar(player, "conversation.c_story1_3_imprisoned_wookiee.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_3_imprisoned_wookiee.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_3_imprisoned_wookiee_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_114"))
        {
            if (c_story1_3_imprisoned_wookiee_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                string_id message = new string_id(c_stringFile, "s_116");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_3_imprisoned_wookiee_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_118");
                    }
                    utils.setScriptVar(player, "conversation.c_story1_3_imprisoned_wookiee.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_3_imprisoned_wookiee.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_3_imprisoned_wookiee_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_118"))
        {
            if (c_story1_3_imprisoned_wookiee_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "hug_tandem");
                doAnimationAction(player, "hug_tandem");
                string_id message = new string_id(c_stringFile, "s_120");
                utils.removeScriptVar(player, "conversation.c_story1_3_imprisoned_wookiee.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_3_imprisoned_wookiee_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_126"))
        {
            if (c_story1_3_imprisoned_wookiee_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                string_id message = new string_id(c_stringFile, "s_128");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_3_imprisoned_wookiee_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_130");
                    }
                    utils.setScriptVar(player, "conversation.c_story1_3_imprisoned_wookiee.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_3_imprisoned_wookiee.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_3_imprisoned_wookiee_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_130"))
        {
            if (c_story1_3_imprisoned_wookiee_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "hug_tandem");
                string_id message = new string_id(c_stringFile, "s_132");
                utils.removeScriptVar(player, "conversation.c_story1_3_imprisoned_wookiee.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_3_imprisoned_wookiee_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_138"))
        {
            doAnimationAction(player, "shrug_shoulders");
            if (c_story1_3_imprisoned_wookiee_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_140");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_3_imprisoned_wookiee_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_142");
                    }
                    utils.setScriptVar(player, "conversation.c_story1_3_imprisoned_wookiee.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_3_imprisoned_wookiee.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_162"))
        {
            doAnimationAction(player, "nod");
            if (c_story1_3_imprisoned_wookiee_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "thank");
                c_story1_3_imprisoned_wookiee_action_signalWookiee2(player, npc);
                string_id message = new string_id(c_stringFile, "s_164");
                utils.removeScriptVar(player, "conversation.c_story1_3_imprisoned_wookiee.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_3_imprisoned_wookiee_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_142"))
        {
            if (c_story1_3_imprisoned_wookiee_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_multiple");
                string_id message = new string_id(c_stringFile, "s_144");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_3_imprisoned_wookiee_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_story1_3_imprisoned_wookiee_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_146");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_150");
                    }
                    utils.setScriptVar(player, "conversation.c_story1_3_imprisoned_wookiee.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_3_imprisoned_wookiee.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_3_imprisoned_wookiee_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_146"))
        {
            doAnimationAction(player, "shrug_shoulders");
            if (c_story1_3_imprisoned_wookiee_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "thank");
                c_story1_3_imprisoned_wookiee_action_signalWookiee2(player, npc);
                string_id message = new string_id(c_stringFile, "s_148");
                utils.removeScriptVar(player, "conversation.c_story1_3_imprisoned_wookiee.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_150"))
        {
            doAnimationAction(player, "shake_head_no");
            if (c_story1_3_imprisoned_wookiee_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_152");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_3_imprisoned_wookiee_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_story1_3_imprisoned_wookiee_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_154");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_158");
                    }
                    utils.setScriptVar(player, "conversation.c_story1_3_imprisoned_wookiee.branchId", 16);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_3_imprisoned_wookiee.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_3_imprisoned_wookiee_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_154"))
        {
            doAnimationAction(player, "point_accusingly");
            if (c_story1_3_imprisoned_wookiee_condition__defaultCondition(player, npc))
            {
                c_story1_3_imprisoned_wookiee_action_signalWookiee2(player, npc);
                string_id message = new string_id(c_stringFile, "s_156");
                utils.removeScriptVar(player, "conversation.c_story1_3_imprisoned_wookiee.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_158"))
        {
            doAnimationAction(player, "shake_head_no");
            if (c_story1_3_imprisoned_wookiee_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_accusingly");
                string_id message = new string_id(c_stringFile, "s_160");
                utils.removeScriptVar(player, "conversation.c_story1_3_imprisoned_wookiee.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_3_imprisoned_wookiee_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_168"))
        {
            doAnimationAction(player, "dismiss");
            if (c_story1_3_imprisoned_wookiee_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "implore");
                c_story1_3_imprisoned_wookiee_action_signalWookiee1(player, npc);
                string_id message = new string_id(c_stringFile, "s_170");
                utils.removeScriptVar(player, "conversation.c_story1_3_imprisoned_wookiee.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_172"))
        {
            if (c_story1_3_imprisoned_wookiee_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "implore");
                c_story1_3_imprisoned_wookiee_action_signalWookiee1(player, npc);
                string_id message = new string_id(c_stringFile, "s_174");
                utils.removeScriptVar(player, "conversation.c_story1_3_imprisoned_wookiee.branchId");
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
            detachScript(self, "conversation.c_story1_3_imprisoned_wookiee");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setName(self, new string_id("npc_name", "arrworr"));
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setName(self, new string_id("npc_name", "arrworr"));
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
        detachScript(self, "conversation.c_story1_3_imprisoned_wookiee");
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
        if (c_story1_3_imprisoned_wookiee_condition_onTaskFinished(player, npc))
        {
            doAnimationAction(npc, "goodbye");
            string_id message = new string_id(c_stringFile, "s_98");
            prose_package pp = new prose_package();
            pp.stringId = message;
            pp.actor.set(player);
            pp.target.set(npc);
            chat.chat(npc, player, null, null, pp);
            return SCRIPT_CONTINUE;
        }
        if (c_story1_3_imprisoned_wookiee_condition_onTaskWookiee3(player, npc))
        {
            doAnimationAction(npc, "thank");
            c_story1_3_imprisoned_wookiee_action_finishEscort(player, npc);
            string_id message = new string_id(c_stringFile, "s_100");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_story1_3_imprisoned_wookiee_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_102");
                }
                utils.setScriptVar(player, "conversation.c_story1_3_imprisoned_wookiee.branchId", 2);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "c_story1_3_imprisoned_wookiee", null, pp, responses);
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
        if (c_story1_3_imprisoned_wookiee_condition_onTaskEscortWookiee(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_134");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (c_story1_3_imprisoned_wookiee_condition_onTaskWookiee2(player, npc))
        {
            doAnimationAction(npc, "implore");
            string_id message = new string_id(c_stringFile, "s_136");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_story1_3_imprisoned_wookiee_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (c_story1_3_imprisoned_wookiee_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_138");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_162");
                }
                utils.setScriptVar(player, "conversation.c_story1_3_imprisoned_wookiee.branchId", 12);
                npcStartConversation(player, npc, "c_story1_3_imprisoned_wookiee", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_story1_3_imprisoned_wookiee_condition_onTaskWookiee(player, npc))
        {
            doAnimationAction(npc, "scared");
            string_id message = new string_id(c_stringFile, "s_166");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_story1_3_imprisoned_wookiee_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (c_story1_3_imprisoned_wookiee_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_168");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_172");
                }
                utils.setScriptVar(player, "conversation.c_story1_3_imprisoned_wookiee.branchId", 20);
                npcStartConversation(player, npc, "c_story1_3_imprisoned_wookiee", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_story1_3_imprisoned_wookiee_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "implore");
            string_id message = new string_id(c_stringFile, "s_176");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("c_story1_3_imprisoned_wookiee"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.c_story1_3_imprisoned_wookiee.branchId");
        if (branchId == 2 && c_story1_3_imprisoned_wookiee_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && c_story1_3_imprisoned_wookiee_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && c_story1_3_imprisoned_wookiee_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && c_story1_3_imprisoned_wookiee_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && c_story1_3_imprisoned_wookiee_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && c_story1_3_imprisoned_wookiee_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && c_story1_3_imprisoned_wookiee_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && c_story1_3_imprisoned_wookiee_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && c_story1_3_imprisoned_wookiee_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && c_story1_3_imprisoned_wookiee_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && c_story1_3_imprisoned_wookiee_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && c_story1_3_imprisoned_wookiee_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.c_story1_3_imprisoned_wookiee.branchId");
        return SCRIPT_CONTINUE;
    }
}
