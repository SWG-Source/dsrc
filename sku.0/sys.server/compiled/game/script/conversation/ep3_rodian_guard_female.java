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
import script.library.utils;

public class ep3_rodian_guard_female extends script.base_script
{
    public ep3_rodian_guard_female()
    {
    }
    public static String c_stringFile = "conversation/ep3_rodian_guard_female";
    public boolean ep3_rodian_guard_female_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean ep3_rodian_guard_female_condition_hasFinishedQuest1(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/ep3_rodian_hunter_1");
        return questIsQuestComplete(questId, player);
    }
    public boolean ep3_rodian_guard_female_condition_hasFinishedQuest2(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/ep3_rodian_hunter_2");
        return questIsQuestComplete(questId, player);
    }
    public boolean ep3_rodian_guard_female_condition_hasFinishedQuest3(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/ep3_rodian_hunter_3");
        return questIsQuestComplete(questId, player);
    }
    public boolean ep3_rodian_guard_female_condition_isOnQuest1(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/ep3_rodian_hunter_1");
        return questIsQuestActive(questId, player);
    }
    public boolean ep3_rodian_guard_female_condition_isOnQuest2(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/ep3_rodian_hunter_2");
        return questIsQuestActive(questId, player);
    }
    public boolean ep3_rodian_guard_female_condition_hasFinished_WOOKIEE_quest2(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/ep3_wookiee_benefactor_2");
        return questIsQuestComplete(questId, player);
    }
    public void ep3_rodian_guard_female_action_giveQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/ep3_rodian_hunter_1");
        questActivateQuest(questId, player, npc);
        playClientEffectLoc(player, "clienteffect/voc_npc_cnv_rodian_short.cef", getLocation(npc), 0f);
    }
    public void ep3_rodian_guard_female_action_giveQuestTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/ep3_rodian_hunter_2");
        questActivateQuest(questId, player, npc);
        playClientEffectLoc(player, "clienteffect/voc_npc_cnv_rodian_medium.cef", getLocation(npc), 0f);
    }
    public void ep3_rodian_guard_female_action_clearQuests(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/ep3_rodian_hunter_1");
        int questId2 = questGetQuestId("quest/ep3_rodian_hunter_2");
        int questId3 = questGetQuestId("quest/ep3_rodian_hunter_3");
        questClearQuest(questId1, player);
        questClearQuest(questId2, player);
        questClearQuest(questId3, player);
        playClientEffectLoc(player, "clienteffect/voc_npc_cnv_rodian_short.cef", getLocation(npc), 0f);
    }
    public void ep3_rodian_guard_female_action_speak_SHORT(obj_id player, obj_id npc) throws InterruptedException
    {
        playClientEffectLoc(player, "clienteffect/voc_npc_cnv_rodian_short.cef", getLocation(npc), 0f);
    }
    public void ep3_rodian_guard_female_action_speak_MEDIUM(obj_id player, obj_id npc) throws InterruptedException
    {
        playClientEffectLoc(player, "clienteffect/voc_npc_cnv_rodian_medium.cef", getLocation(npc), 0f);
    }
    public void ep3_rodian_guard_female_action_speak_LONG(obj_id player, obj_id npc) throws InterruptedException
    {
        playClientEffectLoc(player, "clienteffect/voc_npc_cnv_rodian_long.cef", getLocation(npc), 0f);
    }
    public void ep3_rodian_guard_female_action_giveQuestThree(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/ep3_rodian_hunter_3");
        questActivateQuest(questId, player, npc);
        playClientEffectLoc(player, "clienteffect/voc_npc_cnv_rodian_short.cef", getLocation(npc), 0f);
    }
    public void ep3_rodian_guard_female_action_speak_FEMALE(obj_id player, obj_id npc) throws InterruptedException
    {
        playClientEffectLoc(player, "clienteffect/voc_npc_cnv_rodian_guard_female.cef", getLocation(npc), 0f);
    }
    public int ep3_rodian_guard_female_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_b9b27823"))
        {
            if (ep3_rodian_guard_female_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "goodbye");
                ep3_rodian_guard_female_action_speak_FEMALE(player, npc);
                string_id message = new string_id(c_stringFile, "s_c8f2f3db");
                utils.removeScriptVar(player, "conversation.ep3_rodian_guard_female.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_rodian_guard_female_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c8f39347"))
        {
            if (ep3_rodian_guard_female_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "goodbye");
                ep3_rodian_guard_female_action_speak_FEMALE(player, npc);
                string_id message = new string_id(c_stringFile, "s_4d850454");
                utils.removeScriptVar(player, "conversation.ep3_rodian_guard_female.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_rodian_guard_female_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_77e48d5b"))
        {
            if (ep3_rodian_guard_female_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_left");
                ep3_rodian_guard_female_action_speak_FEMALE(player, npc);
                string_id message = new string_id(c_stringFile, "s_5bbf7644");
                utils.removeScriptVar(player, "conversation.ep3_rodian_guard_female.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_rodian_guard_female_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_b84b366c"))
        {
            if (ep3_rodian_guard_female_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "pound_fist_palm");
                ep3_rodian_guard_female_action_speak_FEMALE(player, npc);
                string_id message = new string_id(c_stringFile, "s_3400e92e");
                utils.removeScriptVar(player, "conversation.ep3_rodian_guard_female.branchId");
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
            detachScript(self, "conversation.ep3_rodian_guard_female");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setName(self, "Kair");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setName(self, "Kair");
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
        detachScript(self, "conversation.ep3_rodian_guard_female");
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
        if (!ep3_rodian_guard_female_condition_hasFinishedQuest1(player, npc))
        {
            doAnimationAction(npc, "shake_head_no");
            ep3_rodian_guard_female_action_speak_FEMALE(player, npc);
            string_id message = new string_id(c_stringFile, "s_3c80890b");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_rodian_guard_female_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_b9b27823");
                }
                utils.setScriptVar(player, "conversation.ep3_rodian_guard_female.branchId", 1);
                npcStartConversation(player, npc, "ep3_rodian_guard_female", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (!ep3_rodian_guard_female_condition_hasFinished_WOOKIEE_quest2(player, npc))
        {
            doAnimationAction(npc, "greet");
            ep3_rodian_guard_female_action_speak_FEMALE(player, npc);
            string_id message = new string_id(c_stringFile, "s_eec83bd7");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_rodian_guard_female_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_c8f39347");
                }
                utils.setScriptVar(player, "conversation.ep3_rodian_guard_female.branchId", 3);
                npcStartConversation(player, npc, "ep3_rodian_guard_female", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (!ep3_rodian_guard_female_condition_hasFinishedQuest2(player, npc))
        {
            doAnimationAction(npc, "smack_self");
            ep3_rodian_guard_female_action_speak_FEMALE(player, npc);
            string_id message = new string_id(c_stringFile, "s_ad7f810c");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_rodian_guard_female_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_77e48d5b");
                }
                utils.setScriptVar(player, "conversation.ep3_rodian_guard_female.branchId", 5);
                npcStartConversation(player, npc, "ep3_rodian_guard_female", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (!ep3_rodian_guard_female_condition_hasFinishedQuest3(player, npc))
        {
            doAnimationAction(npc, "shake_head_disgust");
            ep3_rodian_guard_female_action_speak_FEMALE(player, npc);
            string_id message = new string_id(c_stringFile, "s_ee40364b");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_rodian_guard_female_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_b84b366c");
                }
                utils.setScriptVar(player, "conversation.ep3_rodian_guard_female.branchId", 7);
                npcStartConversation(player, npc, "ep3_rodian_guard_female", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_rodian_guard_female_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "bow");
            ep3_rodian_guard_female_action_speak_FEMALE(player, npc);
            string_id message = new string_id(c_stringFile, "s_9c327f1a");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("ep3_rodian_guard_female"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.ep3_rodian_guard_female.branchId");
        if (branchId == 1 && ep3_rodian_guard_female_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && ep3_rodian_guard_female_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && ep3_rodian_guard_female_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && ep3_rodian_guard_female_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.ep3_rodian_guard_female.branchId");
        return SCRIPT_CONTINUE;
    }
}
