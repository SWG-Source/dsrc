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

public class corellia_coronet_times_editor extends script.base_script
{
    public corellia_coronet_times_editor()
    {
    }
    public static String c_stringFile = "conversation/corellia_coronet_times_editor";
    public boolean corellia_coronet_times_editor_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean corellia_coronet_times_editor_condition_hasViewScreenQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "corellia_coronet_meatlump_act1_begin", "meatlumpsAct1_timesEditor");
    }
    public boolean corellia_coronet_times_editor_condition_starportVandalsEnd(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "corellia_coronet_capitol_problems_starport_vandals", "starport_vandals_4");
    }
    public boolean corellia_coronet_times_editor_condition_starportVandalsActive(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "corellia_coronet_capitol_problems_starport_vandals", "starport_vandals_3");
    }
    public boolean corellia_coronet_times_editor_condition_starportVandalsBegin(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "corellia_coronet_capitol_problems_starport_vandals", "starport_vandals_2");
    }
    public boolean corellia_coronet_times_editor_condition_deletedVandals2(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedQuest(player, "corellia_coronet_capitol_problems_starport_vandals") && !(groundquests.isQuestActiveOrComplete(player, "corellia_coronet_capitol_problems_starport_vandals_2")));
    }
    public void corellia_coronet_times_editor_action_sendToVaniKorr(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "meatlumpsAct1_timesEditor");
    }
    public void corellia_coronet_times_editor_action_starportVandalsMeatlumps(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "starport_vandals_2");
    }
    public void corellia_coronet_times_editor_action_starportVandalsDemmiWot(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "starport_vandals_4");
    }
    public void corellia_coronet_times_editor_action_giveVandals2(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "corellia_coronet_capitol_problems_starport_vandals_2");
    }
    public int corellia_coronet_times_editor_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_34"))
        {
            if (corellia_coronet_times_editor_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "tap_foot");
                corellia_coronet_times_editor_action_giveVandals2(player, npc);
                string_id message = new string_id(c_stringFile, "s_35");
                utils.removeScriptVar(player, "conversation.corellia_coronet_times_editor.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_coronet_times_editor_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_29"))
        {
            if (corellia_coronet_times_editor_condition__defaultCondition(player, npc))
            {
                corellia_coronet_times_editor_action_giveVandals2(player, npc);
                string_id message = new string_id(c_stringFile, "s_30");
                utils.removeScriptVar(player, "conversation.corellia_coronet_times_editor.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_coronet_times_editor_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_22"))
        {
            if (corellia_coronet_times_editor_condition__defaultCondition(player, npc))
            {
                corellia_coronet_times_editor_action_starportVandalsMeatlumps(player, npc);
                string_id message = new string_id(c_stringFile, "s_23");
                utils.removeScriptVar(player, "conversation.corellia_coronet_times_editor.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_coronet_times_editor_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_13"))
        {
            if (corellia_coronet_times_editor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_15");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_coronet_times_editor_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_17");
                    }
                    utils.setScriptVar(player, "conversation.corellia_coronet_times_editor.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_coronet_times_editor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_coronet_times_editor_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_17"))
        {
            if (corellia_coronet_times_editor_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_multiple");
                string_id message = new string_id(c_stringFile, "s_24");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_coronet_times_editor_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_26");
                    }
                    utils.setScriptVar(player, "conversation.corellia_coronet_times_editor.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_coronet_times_editor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_coronet_times_editor_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_26"))
        {
            if (corellia_coronet_times_editor_condition__defaultCondition(player, npc))
            {
                corellia_coronet_times_editor_action_sendToVaniKorr(player, npc);
                string_id message = new string_id(c_stringFile, "s_28");
                utils.removeScriptVar(player, "conversation.corellia_coronet_times_editor.branchId");
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
            detachScript(self, "conversation.corellia_coronet_times_editor");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
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
        detachScript(self, "conversation.corellia_coronet_times_editor");
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
        if (corellia_coronet_times_editor_condition_deletedVandals2(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_33");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_coronet_times_editor_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_34");
                }
                utils.setScriptVar(player, "conversation.corellia_coronet_times_editor.branchId", 1);
                npcStartConversation(player, npc, "corellia_coronet_times_editor", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_coronet_times_editor_condition_starportVandalsEnd(player, npc))
        {
            corellia_coronet_times_editor_action_starportVandalsDemmiWot(player, npc);
            string_id message = new string_id(c_stringFile, "s_19");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_coronet_times_editor_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_29");
                }
                utils.setScriptVar(player, "conversation.corellia_coronet_times_editor.branchId", 3);
                npcStartConversation(player, npc, "corellia_coronet_times_editor", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_coronet_times_editor_condition_starportVandalsActive(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_20");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corellia_coronet_times_editor_condition_starportVandalsBegin(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_21");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_coronet_times_editor_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_22");
                }
                utils.setScriptVar(player, "conversation.corellia_coronet_times_editor.branchId", 6);
                npcStartConversation(player, npc, "corellia_coronet_times_editor", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_coronet_times_editor_condition_hasViewScreenQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_11");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_coronet_times_editor_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_13");
                }
                utils.setScriptVar(player, "conversation.corellia_coronet_times_editor.branchId", 8);
                npcStartConversation(player, npc, "corellia_coronet_times_editor", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_coronet_times_editor_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_32");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("corellia_coronet_times_editor"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.corellia_coronet_times_editor.branchId");
        if (branchId == 1 && corellia_coronet_times_editor_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && corellia_coronet_times_editor_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && corellia_coronet_times_editor_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && corellia_coronet_times_editor_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && corellia_coronet_times_editor_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && corellia_coronet_times_editor_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.corellia_coronet_times_editor.branchId");
        return SCRIPT_CONTINUE;
    }
}
