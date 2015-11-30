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

public class ep3_clone_relics_clone_trooper_major_raev extends script.base_script
{
    public ep3_clone_relics_clone_trooper_major_raev()
    {
    }
    public static String c_stringFile = "conversation/ep3_clone_relics_clone_trooper_major_raev";
    public boolean ep3_clone_relics_clone_trooper_major_raev_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean ep3_clone_relics_clone_trooper_major_raev_condition_onTaskTalkOfficer(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "ep3_clone_relics_clone_trooper_mort_imperial", "talkToOfficer"));
    }
    public boolean ep3_clone_relics_clone_trooper_major_raev_condition_onTaskReturningDatapad(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "ep3_clone_relics_clone_trooper_mort_imperial", "returnDatapad"));
    }
    public boolean ep3_clone_relics_clone_trooper_major_raev_condition_onTaskWait(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "ep3_clone_relics_clone_trooper_mort_imperial", "waitForIt"));
    }
    public boolean ep3_clone_relics_clone_trooper_major_raev_condition_onTaskTalkOfficerAgain(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "ep3_clone_relics_clone_trooper_mort_imperial", "talkToOfficerAgain"));
    }
    public boolean ep3_clone_relics_clone_trooper_major_raev_condition_hasTalkedToEmperor(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedTask(player, "ep3_clone_relics_clone_trooper_mort_imperial", "talkToEmperor"));
    }
    public boolean ep3_clone_relics_clone_trooper_major_raev_condition_doneWithImperialPart(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedTask(player, "ep3_clone_relics_clone_trooper_mort_imperial", "imperialReward"));
    }
    public void ep3_clone_relics_clone_trooper_major_raev_action_officer1(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "talkedToOfficer");
    }
    public void ep3_clone_relics_clone_trooper_major_raev_action_officer2(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "talkedToOfficer2");
    }
    public void ep3_clone_relics_clone_trooper_major_raev_action_datapadTurnedIn(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "datapadReturned");
    }
    public int ep3_clone_relics_clone_trooper_major_raev_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_85"))
        {
            if (ep3_clone_relics_clone_trooper_major_raev_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_87");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_clone_trooper_major_raev_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_89");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_clone_trooper_major_raev.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_clone_trooper_major_raev.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_clone_trooper_major_raev_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_89"))
        {
            if (ep3_clone_relics_clone_trooper_major_raev_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute2");
                doAnimationAction(player, "salute2");
                ep3_clone_relics_clone_trooper_major_raev_action_datapadTurnedIn(player, npc);
                string_id message = new string_id(c_stringFile, "s_92");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_clone_trooper_major_raev.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_clone_trooper_major_raev_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_104"))
        {
            if (ep3_clone_relics_clone_trooper_major_raev_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "pound_fist_palm");
                doAnimationAction(player, "slow_down");
                ep3_clone_relics_clone_trooper_major_raev_action_officer2(player, npc);
                string_id message = new string_id(c_stringFile, "s_127");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_clone_trooper_major_raev.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_clone_trooper_major_raev_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_133"))
        {
            if (ep3_clone_relics_clone_trooper_major_raev_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_hands");
                ep3_clone_relics_clone_trooper_major_raev_action_officer1(player, npc);
                string_id message = new string_id(c_stringFile, "s_135");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_clone_trooper_major_raev.branchId");
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
            detachScript(self, "conversation.ep3_clone_relics_clone_trooper_major_raev");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setName(self, new string_id("ep3/npc_names", "clone_relics_major_raev"));
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setName(self, new string_id("ep3/npc_names", "clone_relics_major_raev"));
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info menuInfo) throws InterruptedException
    {
        int menu = menuInfo.addRootMenu(menu_info_types.CONVERSE_START, null);
        menu_info_data menuInfoData = menuInfo.getMenuItemById(menu);
        menuInfoData.setServerNotify(false);
        setCondition(self, CONDITION_CONVERSABLE);
        faceTo(self, player);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        detachScript(self, "conversation.ep3_clone_relics_clone_trooper_major_raev");
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
        if (ep3_clone_relics_clone_trooper_major_raev_condition_doneWithImperialPart(player, npc))
        {
            doAnimationAction(npc, "salute2");
            doAnimationAction(player, "salute2");
            string_id message = new string_id(c_stringFile, "s_81");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_clone_relics_clone_trooper_major_raev_condition_onTaskReturningDatapad(player, npc))
        {
            doAnimationAction(npc, "salute2");
            doAnimationAction(player, "salute2");
            string_id message = new string_id(c_stringFile, "s_83");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_clone_relics_clone_trooper_major_raev_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_85");
                }
                utils.setScriptVar(player, "conversation.ep3_clone_relics_clone_trooper_major_raev.branchId", 2);
                npcStartConversation(player, npc, "ep3_clone_relics_clone_trooper_major_raev", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_clone_relics_clone_trooper_major_raev_condition_hasTalkedToEmperor(player, npc))
        {
            doAnimationAction(player, "salute2");
            string_id message = new string_id(c_stringFile, "s_96");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_clone_relics_clone_trooper_major_raev_condition_onTaskTalkOfficerAgain(player, npc))
        {
            doAnimationAction(npc, "nervous");
            string_id message = new string_id(c_stringFile, "s_100");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_clone_relics_clone_trooper_major_raev_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_104");
                }
                utils.setScriptVar(player, "conversation.ep3_clone_relics_clone_trooper_major_raev.branchId", 6);
                npcStartConversation(player, npc, "ep3_clone_relics_clone_trooper_major_raev", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_clone_relics_clone_trooper_major_raev_condition_onTaskWait(player, npc))
        {
            doAnimationAction(npc, "slow_down");
            string_id message = new string_id(c_stringFile, "s_129");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_clone_relics_clone_trooper_major_raev_condition_onTaskTalkOfficer(player, npc))
        {
            doAnimationAction(npc, "nod_head_once");
            doAnimationAction(player, "salute2");
            string_id message = new string_id(c_stringFile, "s_131");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_clone_relics_clone_trooper_major_raev_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_133");
                }
                utils.setScriptVar(player, "conversation.ep3_clone_relics_clone_trooper_major_raev.branchId", 9);
                npcStartConversation(player, npc, "ep3_clone_relics_clone_trooper_major_raev", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_clone_relics_clone_trooper_major_raev_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_137");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("ep3_clone_relics_clone_trooper_major_raev"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.ep3_clone_relics_clone_trooper_major_raev.branchId");
        if (branchId == 2 && ep3_clone_relics_clone_trooper_major_raev_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && ep3_clone_relics_clone_trooper_major_raev_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && ep3_clone_relics_clone_trooper_major_raev_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && ep3_clone_relics_clone_trooper_major_raev_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.ep3_clone_relics_clone_trooper_major_raev.branchId");
        return SCRIPT_CONTINUE;
    }
}
