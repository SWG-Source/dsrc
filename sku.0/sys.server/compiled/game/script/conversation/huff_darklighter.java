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

public class huff_darklighter extends script.base_script
{
    public huff_darklighter()
    {
    }
    public static String c_stringFile = "conversation/huff_darklighter";
    public boolean huff_darklighter_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean huff_darklighter_condition_startWithHuff(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "huffs_guard_rifle");
    }
    public boolean huff_darklighter_condition_workingOnHuffDera(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "huff_darklighter_dera");
    }
    public boolean huff_darklighter_condition_returningFromDera(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "huff_darklighter_dera", "finishedDera") || groundquests.hasCompletedQuest(player, "huff_darklighter_dera"));
    }
    public boolean huff_darklighter_condition_workingOnHuffSennex(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "huff_darklighter_sennex");
    }
    public boolean huff_darklighter_condition_returningFromSennex(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "huff_darklighter_sennex", "finishedSennex") || groundquests.hasCompletedQuest(player, "huff_darklighter_sennex"));
    }
    public void huff_darklighter_action_quest_HuffDera(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.requestGrantQuest(player, "huff_darklighter_dera");
    }
    public void huff_darklighter_action_signal_endHuffDera(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "finishedDera");
    }
    public void huff_darklighter_action_grant_HuffSennex(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.requestGrantQuest(player, "huff_darklighter_sennex");
    }
    public void huff_darklighter_action_signal_endHuffSennex(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "finishedSennex");
    }
    public int huff_darklighter_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_15"))
        {
            if (huff_darklighter_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                huff_darklighter_action_grant_HuffSennex(player, npc);
                string_id message = new string_id(c_stringFile, "s_17");
                utils.removeScriptVar(player, "conversation.huff_darklighter.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_16"))
        {
            if (huff_darklighter_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_18");
                utils.removeScriptVar(player, "conversation.huff_darklighter.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int huff_darklighter_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_37"))
        {
            if (huff_darklighter_condition__defaultCondition(player, npc))
            {
                huff_darklighter_action_quest_HuffDera(player, npc);
                string_id message = new string_id(c_stringFile, "s_38");
                utils.removeScriptVar(player, "conversation.huff_darklighter.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_39"))
        {
            if (huff_darklighter_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_40");
                utils.removeScriptVar(player, "conversation.huff_darklighter.branchId");
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
            detachScript(self, "conversation.huff_darklighter");
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
        detachScript(self, "conversation.huff_darklighter");
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
        if (huff_darklighter_condition_returningFromSennex(player, npc))
        {
            doAnimationAction(npc, "udaman");
            huff_darklighter_action_signal_endHuffSennex(player, npc);
            string_id message = new string_id(c_stringFile, "s_20");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (huff_darklighter_condition_workingOnHuffSennex(player, npc))
        {
            doAnimationAction(npc, "stamp_feet");
            string_id message = new string_id(c_stringFile, "s_35");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (huff_darklighter_condition_returningFromDera(player, npc))
        {
            doAnimationAction(npc, "bow");
            huff_darklighter_action_signal_endHuffDera(player, npc);
            string_id message = new string_id(c_stringFile, "s_36");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (huff_darklighter_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (huff_darklighter_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_15");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_16");
                }
                utils.setScriptVar(player, "conversation.huff_darklighter.branchId", 3);
                npcStartConversation(player, npc, "huff_darklighter", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (huff_darklighter_condition_workingOnHuffDera(player, npc))
        {
            doAnimationAction(npc, "implore");
            string_id message = new string_id(c_stringFile, "s_11");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (huff_darklighter_condition_startWithHuff(player, npc))
        {
            doAnimationAction(npc, "offer_affection");
            string_id message = new string_id(c_stringFile, "s_13");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (huff_darklighter_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (huff_darklighter_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_37");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_39");
                }
                utils.setScriptVar(player, "conversation.huff_darklighter.branchId", 7);
                npcStartConversation(player, npc, "huff_darklighter", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (huff_darklighter_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "wave_on_dismissing");
            string_id message = new string_id(c_stringFile, "s_24");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("huff_darklighter"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.huff_darklighter.branchId");
        if (branchId == 3 && huff_darklighter_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && huff_darklighter_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.huff_darklighter.branchId");
        return SCRIPT_CONTINUE;
    }
}
