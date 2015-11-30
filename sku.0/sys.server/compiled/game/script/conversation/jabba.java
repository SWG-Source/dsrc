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

public class jabba extends script.base_script
{
    public jabba()
    {
    }
    public static String c_stringFile = "conversation/jabba";
    public boolean jabba_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean jabba_condition_finishedBibsQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.hasCompletedQuest(player, "quest/jabba_bib_fortuna_v2") || getIntObjVar(player, "theme_park_jabba") > 14)
        {
            return true;
        }
        return false;
    }
    public boolean jabba_condition_killingDelrice(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "quest/jabba_the_hutt_v2", "killDelriceCapreese"))
        {
            return true;
        }
        return false;
    }
    public boolean jabba_condition_killingTyrok(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "quest/jabba_the_hutt_v2", "killTyrok"))
        {
            return true;
        }
        return false;
    }
    public boolean jabba_condition_killingAssaultTeam(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "quest/jabba_the_hutt_v2", "ValarianAssaultTeam"))
        {
            return true;
        }
        return false;
    }
    public boolean jabba_condition_returningToJabba(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "quest/jabba_the_hutt_v2", "returnToJabba"))
        {
            return true;
        }
        return false;
    }
    public boolean jabba_condition_finishedJabbasQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.hasCompletedQuest(player, "quest/jabba_the_hutt_v2"))
        {
            return true;
        }
        return false;
    }
    public boolean jabba_condition_onHeadStartJabba(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/legacy_head_start");
        int ground = groundquests.getTaskId(questId1, "legacy_head_start_e1");
        boolean onTask = (questIsTaskActive(questId1, ground, player));
        return onTask;
    }
    public void jabba_action_grantJabbasQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "quest/jabba_the_hutt_v2");
    }
    public void jabba_action_sendCompletedJabbaSignal(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "finishedKilling");
    }
    public void jabba_action_grantEV9D9(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "legacy_head_start_launch_e11");
    }
    public void jabba_action_clearPointer(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "found_jabba");
    }
    public int jabba_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_37"))
        {
            if (jabba_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_38");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (jabba_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_39");
                    }
                    utils.setScriptVar(player, "conversation.jabba.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.jabba.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int jabba_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_39"))
        {
            if (jabba_condition__defaultCondition(player, npc))
            {
                jabba_action_grantEV9D9(player, npc);
                string_id message = new string_id(c_stringFile, "s_40");
                utils.removeScriptVar(player, "conversation.jabba.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int jabba_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_28"))
        {
            if (jabba_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_30");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (jabba_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_32");
                    }
                    utils.setScriptVar(player, "conversation.jabba.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.jabba.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_36"))
        {
            if (jabba_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_42");
                utils.removeScriptVar(player, "conversation.jabba.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int jabba_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_32"))
        {
            if (jabba_condition__defaultCondition(player, npc))
            {
                jabba_action_grantJabbasQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_34");
                utils.removeScriptVar(player, "conversation.jabba.branchId");
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
            detachScript(self, "conversation.jabba");
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
        detachScript(self, "conversation.jabba");
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
        if (jabba_condition_finishedJabbasQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_4");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (jabba_condition_onHeadStartJabba(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_37");
                }
                utils.setScriptVar(player, "conversation.jabba.branchId", 1);
                npcStartConversation(player, npc, "jabba", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (jabba_condition_returningToJabba(player, npc))
        {
            jabba_action_sendCompletedJabbaSignal(player, npc);
            string_id message = new string_id(c_stringFile, "s_10");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (jabba_condition_killingAssaultTeam(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_45");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (jabba_condition_killingTyrok(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_22");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (jabba_condition_killingDelrice(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_24");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (jabba_condition_finishedBibsQuest(player, npc))
        {
            jabba_action_clearPointer(player, npc);
            string_id message = new string_id(c_stringFile, "s_26");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (jabba_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (jabba_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_28");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_36");
                }
                utils.setScriptVar(player, "conversation.jabba.branchId", 8);
                npcStartConversation(player, npc, "jabba", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (jabba_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_44");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("jabba"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.jabba.branchId");
        if (branchId == 1 && jabba_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && jabba_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && jabba_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && jabba_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.jabba.branchId");
        return SCRIPT_CONTINUE;
    }
}
