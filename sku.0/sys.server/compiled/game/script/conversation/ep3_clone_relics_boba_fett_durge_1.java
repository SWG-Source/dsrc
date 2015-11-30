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

public class ep3_clone_relics_boba_fett_durge_1 extends script.base_script
{
    public ep3_clone_relics_boba_fett_durge_1()
    {
    }
    public static String c_stringFile = "conversation/ep3_clone_relics_boba_fett_durge_1";
    public boolean ep3_clone_relics_boba_fett_durge_1_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean ep3_clone_relics_boba_fett_durge_1_condition_hasCompletedTaskTalkDurge(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedTask(player, "ep3_clone_relics_boba_fett_4", "talkedDurge"));
    }
    public boolean ep3_clone_relics_boba_fett_durge_1_condition_onQuestFour(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isQuestActive(player, "ep3_clone_relics_boba_fett_4"));
    }
    public void ep3_clone_relics_boba_fett_durge_1_action_sendSignalDurge(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "talkedToDurge");
    }
    public void ep3_clone_relics_boba_fett_durge_1_action_sendSignalTalkBartender(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "talkedToBartender");
    }
    public int ep3_clone_relics_boba_fett_durge_1_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_183"))
        {
            doAnimationAction(player, "wave_finger_warning");
            if (ep3_clone_relics_boba_fett_durge_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                string_id message = new string_id(c_stringFile, "s_184");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_boba_fett_durge_1.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_boba_fett_durge_1_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1546"))
        {
            doAnimationAction(player, "point_to_self");
            ep3_clone_relics_boba_fett_durge_1_action_sendSignalDurge(player, npc);
            if (ep3_clone_relics_boba_fett_durge_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                string_id message = new string_id(c_stringFile, "s_1548");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_boba_fett_durge_1.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1550"))
        {
            doAnimationAction(player, "nod_head_once");
            ep3_clone_relics_boba_fett_durge_1_action_sendSignalDurge(player, npc);
            if (ep3_clone_relics_boba_fett_durge_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                string_id message = new string_id(c_stringFile, "s_1552");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_boba_fett_durge_1.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_boba_fett_durge_1_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1556"))
        {
            doAnimationAction(player, "wave_finger_warning");
            if (ep3_clone_relics_boba_fett_durge_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "stop");
                string_id message = new string_id(c_stringFile, "s_1558");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_boba_fett_durge_1.branchId");
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
            detachScript(self, "conversation.ep3_clone_relics_boba_fett_durge_1");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setName(self, new string_id("ep3/npc_names", "clone_relics_durge"));
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setName(self, new string_id("ep3/npc_names", "clone_relics_durge"));
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
        detachScript(self, "conversation.ep3_clone_relics_boba_fett_durge_1");
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
        if (ep3_clone_relics_boba_fett_durge_1_condition_hasCompletedTaskTalkDurge(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_182");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_clone_relics_boba_fett_durge_1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_183");
                }
                utils.setScriptVar(player, "conversation.ep3_clone_relics_boba_fett_durge_1.branchId", 1);
                npcStartConversation(player, npc, "ep3_clone_relics_boba_fett_durge_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_clone_relics_boba_fett_durge_1_condition_onQuestFour(player, npc))
        {
            doAnimationAction(npc, "point_forward");
            ep3_clone_relics_boba_fett_durge_1_action_sendSignalTalkBartender(player, npc);
            string_id message = new string_id(c_stringFile, "s_1544");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_clone_relics_boba_fett_durge_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_clone_relics_boba_fett_durge_1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1546");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1550");
                }
                utils.setScriptVar(player, "conversation.ep3_clone_relics_boba_fett_durge_1.branchId", 3);
                npcStartConversation(player, npc, "ep3_clone_relics_boba_fett_durge_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_clone_relics_boba_fett_durge_1_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1554");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_clone_relics_boba_fett_durge_1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1556");
                }
                utils.setScriptVar(player, "conversation.ep3_clone_relics_boba_fett_durge_1.branchId", 6);
                npcStartConversation(player, npc, "ep3_clone_relics_boba_fett_durge_1", message, responses);
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
        if (!conversationId.equals("ep3_clone_relics_boba_fett_durge_1"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.ep3_clone_relics_boba_fett_durge_1.branchId");
        if (branchId == 1 && ep3_clone_relics_boba_fett_durge_1_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && ep3_clone_relics_boba_fett_durge_1_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && ep3_clone_relics_boba_fett_durge_1_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.ep3_clone_relics_boba_fett_durge_1.branchId");
        return SCRIPT_CONTINUE;
    }
}
