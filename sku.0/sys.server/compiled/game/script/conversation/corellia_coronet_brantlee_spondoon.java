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

public class corellia_coronet_brantlee_spondoon extends script.base_script
{
    public corellia_coronet_brantlee_spondoon()
    {
    }
    public static String c_stringFile = "conversation/corellia_coronet_brantlee_spondoon";
    public boolean corellia_coronet_brantlee_spondoon_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean corellia_coronet_brantlee_spondoon_condition_summonedToBrantlee(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "corellia_coronet_capitol_problems_the_informant", "the_informant_0");
    }
    public boolean corellia_coronet_brantlee_spondoon_condition_informantActive(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "corellia_coronet_capitol_problems_the_informant") && groundquests.hasCompletedTask(player, "corellia_coronet_capitol_problems_the_informant", "the_informant_0") && !groundquests.hasCompletedTask(player, "corellia_coronet_capitol_problems_the_informant", "the_informant_7b");
    }
    public boolean corellia_coronet_brantlee_spondoon_condition_informantComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "corellia_coronet_capitol_problems_the_informant", "the_informant_8") || groundquests.isTaskActive(player, "corellia_coronet_capitol_problems_the_informant", "the_informant_9") || groundquests.hasCompletedQuest(player, "corellia_coronet_capitol_problems_the_informant");
    }
    public boolean corellia_coronet_brantlee_spondoon_condition_thankfulDiktat(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "corellia_coronet_capitol_problems_thankful_diktat") || groundquests.hasCompletedQuest(player, "corellia_coronet_capitol_problems_thankful_diktat");
    }
    public void corellia_coronet_brantlee_spondoon_action_giveInformantQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "the_informant_0");
        return;
    }
    public void corellia_coronet_brantlee_spondoon_action_endInformantQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "the_informant_8");
        return;
    }
    public int corellia_coronet_brantlee_spondoon_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_12"))
        {
            if (corellia_coronet_brantlee_spondoon_condition__defaultCondition(player, npc))
            {
                corellia_coronet_brantlee_spondoon_action_giveInformantQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_14");
                utils.removeScriptVar(player, "conversation.corellia_coronet_brantlee_spondoon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_16"))
        {
            if (corellia_coronet_brantlee_spondoon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_18");
                utils.removeScriptVar(player, "conversation.corellia_coronet_brantlee_spondoon.branchId");
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
            detachScript(self, "conversation.corellia_coronet_brantlee_spondoon");
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
        detachScript(self, "conversation.corellia_coronet_brantlee_spondoon");
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
        if (corellia_coronet_brantlee_spondoon_condition_thankfulDiktat(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_4");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corellia_coronet_brantlee_spondoon_condition_informantComplete(player, npc))
        {
            corellia_coronet_brantlee_spondoon_action_endInformantQuest(player, npc);
            string_id message = new string_id(c_stringFile, "s_6");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corellia_coronet_brantlee_spondoon_condition_informantActive(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_8");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corellia_coronet_brantlee_spondoon_condition_summonedToBrantlee(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_10");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_coronet_brantlee_spondoon_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (corellia_coronet_brantlee_spondoon_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_12");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_16");
                }
                utils.setScriptVar(player, "conversation.corellia_coronet_brantlee_spondoon.branchId", 4);
                npcStartConversation(player, npc, "corellia_coronet_brantlee_spondoon", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_coronet_brantlee_spondoon_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_20");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("corellia_coronet_brantlee_spondoon"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.corellia_coronet_brantlee_spondoon.branchId");
        if (branchId == 4 && corellia_coronet_brantlee_spondoon_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.corellia_coronet_brantlee_spondoon.branchId");
        return SCRIPT_CONTINUE;
    }
}
