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
import script.library.npe;
import script.library.utils;

public class npe_runequest extends script.base_script
{
    public npe_runequest()
    {
    }
    public static String c_stringFile = "conversation/npe_runequest";
    public boolean npe_runequest_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean npe_runequest_condition_isTaskActiveRune(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "npe_runequest", "runes") && !groundquests.hasCompletedQuest(player, "npe_runequest"));
    }
    public boolean npe_runequest_condition_hasCompletedRuneTask(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedTask(player, "npe_runequest", "runes") && !groundquests.hasCompletedQuest(player, "npe_runequest"));
    }
    public boolean npe_runequest_condition_hasCompletedQuestRune(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "npe_runequest");
    }
    public boolean npe_runequest_condition_hasTemplate(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasObjVar(player, "npe.finishedTemplate");
    }
    public void npe_runequest_action_grantRuneQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "npe_runequest");
        if (!hasObjVar(player, npe.QUEST_REWORK_VAR))
        {
            setObjVar(player, npe.QUEST_REWORK_VAR, npe.QUEST_ENUMERATION);
        }
    }
    public void npe_runequest_action_giveRuneSignal(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "runesignal");
    }
    public void npe_runequest_action_groupPopUp(obj_id player, obj_id npc) throws InterruptedException
    {
        messageTo(player, "groupPopUp1", null, 0, false);
        messageTo(player, "groupPopUp2", null, 5, false);
    }
    public int npe_runequest_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_15"))
        {
            if (npe_runequest_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_16");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_runequest_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.npe_runequest.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_runequest.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_runequest_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_17"))
        {
            doAnimationAction(player, "bow");
            if (npe_runequest_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wave1");
                npe_runequest_action_giveRuneSignal(player, npc);
                string_id message = new string_id(c_stringFile, "s_18");
                utils.removeScriptVar(player, "conversation.npe_runequest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_runequest_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_14"))
        {
            if (npe_runequest_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_20");
                utils.removeScriptVar(player, "conversation.npe_runequest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_runequest_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_24"))
        {
            if (npe_runequest_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_26");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_runequest_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_28");
                    }
                    utils.setScriptVar(player, "conversation.npe_runequest.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_runequest.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_runequest_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_28"))
        {
            if (npe_runequest_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_30");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_runequest_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.npe_runequest.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_runequest.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_runequest_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_32"))
        {
            npe_runequest_action_groupPopUp(player, npc);
            if (npe_runequest_condition__defaultCondition(player, npc))
            {
                npe_runequest_action_grantRuneQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_34");
                utils.removeScriptVar(player, "conversation.npe_runequest.branchId");
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
            detachScript(self, "conversation.npe_runequest");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setName(self, "Aquillia (Archaeologist)");
        setInvulnerable(self, true);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setName(self, "Aquillia (Archaeologist)");
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
        detachScript(self, "conversation.npe_runequest");
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
        if (npe_runequest_condition_hasCompletedQuestRune(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_12");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (npe_runequest_condition_hasCompletedRuneTask(player, npc))
        {
            doAnimationAction(npc, "implore");
            string_id message = new string_id(c_stringFile, "s_11");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (npe_runequest_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_15");
                }
                utils.setScriptVar(player, "conversation.npe_runequest.branchId", 2);
                npcStartConversation(player, npc, "npe_runequest", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (npe_runequest_condition_isTaskActiveRune(player, npc))
        {
            doAnimationAction(npc, "implore");
            string_id message = new string_id(c_stringFile, "s_10");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (npe_runequest_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_14");
                }
                utils.setScriptVar(player, "conversation.npe_runequest.branchId", 5);
                npcStartConversation(player, npc, "npe_runequest", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (npe_runequest_condition_hasTemplate(player, npc))
        {
            doAnimationAction(npc, "bow");
            string_id message = new string_id(c_stringFile, "s_22");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (npe_runequest_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_24");
                }
                utils.setScriptVar(player, "conversation.npe_runequest.branchId", 7);
                npcStartConversation(player, npc, "npe_runequest", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (npe_runequest_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_36");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("npe_runequest"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.npe_runequest.branchId");
        if (branchId == 2 && npe_runequest_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && npe_runequest_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && npe_runequest_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && npe_runequest_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && npe_runequest_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && npe_runequest_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.npe_runequest.branchId");
        return SCRIPT_CONTINUE;
    }
}
