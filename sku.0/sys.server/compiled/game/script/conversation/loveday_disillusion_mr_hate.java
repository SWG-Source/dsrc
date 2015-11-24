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

public class loveday_disillusion_mr_hate extends script.base_script
{
    public loveday_disillusion_mr_hate()
    {
    }
    public static String c_stringFile = "conversation/loveday_disillusion_mr_hate";
    public boolean loveday_disillusion_mr_hate_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean loveday_disillusion_mr_hate_condition_taskActive(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "loveday_disillusion_mr_hate_v2", "loveday_disillusion_mr_hate_03") || groundquests.isTaskActive(player, "loveday_disillusion_mr_hate_v2_noloot", "loveday_disillusion_mr_hate_03");
    }
    public boolean loveday_disillusion_mr_hate_condition_taskComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "loveday_disillusion_mr_hate_v2", "loveday_disillusion_mr_hate_complete") || groundquests.isTaskActive(player, "loveday_disillusion_mr_hate_v2_noloot", "loveday_disillusion_mr_hate_complete") || hasCompletedCollectionSlot(player, "loveday_2010_disillusion_quest");
    }
    public void loveday_disillusion_mr_hate_action_taskSignal(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "loveday_disillusion_mr_hate_03");
        if (groundquests.isTaskActive(player, "loveday_disillusion_mr_hate_v2", "loveday_disillusion_mr_hate_02b"))
        {
            groundquests.completeTask(player, "loveday_disillusion_mr_hate_v2", "loveday_disillusion_mr_hate_02b");
        }
        if (groundquests.isTaskActive(player, "loveday_disillusion_mr_hate_v2_noloot", "loveday_disillusion_mr_hate_02b"))
        {
            groundquests.completeTask(player, "loveday_disillusion_mr_hate_v2_noloot", "loveday_disillusion_mr_hate_02b");
        }
        messageTo(npc, "handleDestroySelf", null, 19, false);
    }
    public String loveday_disillusion_mr_hate_tokenTO_misterOrMiss(obj_id player, obj_id npc) throws InterruptedException
    {
        String title = "Mister";
        if (getGender(player) == GENDER_FEMALE)
        {
            title = "Miss";
        }
        return title;
    }
    public int loveday_disillusion_mr_hate_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_7"))
        {
            if (loveday_disillusion_mr_hate_condition__defaultCondition(player, npc))
            {
                loveday_disillusion_mr_hate_action_taskSignal(player, npc);
                string_id message = new string_id(c_stringFile, "s_9");
                utils.removeScriptVar(player, "conversation.loveday_disillusion_mr_hate.branchId");
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
            detachScript(self, "conversation.loveday_disillusion_mr_hate");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        messageTo(self, "handleDestroySelf", null, 120, false);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        setName(self, "Mister Friendly");
        ai_lib.setMood(self, chat.MOOD_HAPPY);
        chat.setChatMood(self, chat.MOOD_HAPPY);
        messageTo(self, "handleDestroySelf", null, 120, false);
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
        detachScript(self, "conversation.loveday_disillusion_mr_hate");
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
        if (loveday_disillusion_mr_hate_condition_taskComplete(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_22");
            prose_package pp = new prose_package();
            pp.stringId = message;
            pp.actor.set(player);
            pp.target.set(npc);
            pp.other.set(loveday_disillusion_mr_hate_tokenTO_misterOrMiss(player, npc));
            chat.chat(npc, player, null, null, pp);
            return SCRIPT_CONTINUE;
        }
        if (loveday_disillusion_mr_hate_condition_taskActive(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_6");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (loveday_disillusion_mr_hate_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_7");
                }
                utils.setScriptVar(player, "conversation.loveday_disillusion_mr_hate.branchId", 2);
                npcStartConversation(player, npc, "loveday_disillusion_mr_hate", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (loveday_disillusion_mr_hate_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_11");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("loveday_disillusion_mr_hate"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.loveday_disillusion_mr_hate.branchId");
        if (branchId == 2 && loveday_disillusion_mr_hate_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.loveday_disillusion_mr_hate.branchId");
        return SCRIPT_CONTINUE;
    }
}
