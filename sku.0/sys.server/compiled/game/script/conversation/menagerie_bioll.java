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

public class menagerie_bioll extends script.base_script
{
    public menagerie_bioll()
    {
    }
    public static String c_stringFile = "conversation/menagerie_bioll";
    public boolean menagerie_bioll_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean menagerie_bioll_condition_hasVarBiollQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (utils.hasScriptVar(player, "varMenagerieQuestNum"))
        {
            int menagerieQuestNum = utils.getIntScriptVar(player, "varMenagerieQuestNum");
            if (menagerieQuestNum <= 24)
            {
                return true;
            }
        }
        return false;
    }
    public boolean menagerie_bioll_condition_needsNewQuestVar(obj_id player, obj_id npc) throws InterruptedException
    {
        if (utils.hasScriptVar(player, "varMenagerieQuestNum"))
        {
            return false;
        }
        return true;
    }
    public boolean menagerie_bioll_condition_hasVarNonBiollQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (utils.hasScriptVar(player, "varMenagerieQuestNum"))
        {
            int menagerieQuestNum = utils.getIntScriptVar(player, "varMenagerieQuestNum");
            if (menagerieQuestNum > 24)
            {
                return true;
            }
        }
        return false;
    }
    public void menagerie_bioll_action_FaceToAndAssignQuestVar(obj_id player, obj_id npc) throws InterruptedException
    {
        int menagerieQuestNum = rand(1, 56);
        utils.setScriptVar(player, "varMenagerieQuestNum", menagerieQuestNum);
    }
    public void menagerie_bioll_action_faceTo(obj_id player, obj_id npc) throws InterruptedException
    {
    }
    public int menagerie_bioll_tokenDI_varMenagerieQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        int menagerieQuestNum = 0;
        if (utils.hasScriptVar(player, "varMenagerieQuestNum"))
        {
            menagerieQuestNum = utils.getIntScriptVar(player, "varMenagerieQuestNum");
        }
        return menagerieQuestNum;
    }
    public int menagerie_bioll_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_13"))
        {
            menagerie_bioll_action_FaceToAndAssignQuestVar(player, npc);
            if (menagerie_bioll_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_14");
                utils.removeScriptVar(player, "conversation.menagerie_bioll.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int menagerie_bioll_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_12"))
        {
            menagerie_bioll_action_FaceToAndAssignQuestVar(player, npc);
            if (menagerie_bioll_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_15");
                utils.removeScriptVar(player, "conversation.menagerie_bioll.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int menagerie_bioll_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_11"))
        {
            menagerie_bioll_action_FaceToAndAssignQuestVar(player, npc);
            if (menagerie_bioll_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_16");
                utils.removeScriptVar(player, "conversation.menagerie_bioll.branchId");
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
            detachScript(self, "conversation.menagerie_bioll");
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
        detachScript(self, "conversation.menagerie_bioll");
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
        if (menagerie_bioll_condition_needsNewQuestVar(player, npc))
        {
            menagerie_bioll_action_FaceToAndAssignQuestVar(player, npc);
            string_id message = new string_id(c_stringFile, "s_6");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (menagerie_bioll_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.menagerie_bioll.branchId", 1);
                npcStartConversation(player, npc, "menagerie_bioll", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (menagerie_bioll_condition_hasVarBiollQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_5");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (menagerie_bioll_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_12");
                }
                utils.setScriptVar(player, "conversation.menagerie_bioll.branchId", 3);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                pp.digitInteger = menagerie_bioll_tokenDI_varMenagerieQuest(player, npc);
                npcStartConversation(player, npc, "menagerie_bioll", null, pp, responses);
            }
            else 
            {
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                pp.digitInteger = menagerie_bioll_tokenDI_varMenagerieQuest(player, npc);
                chat.chat(npc, player, null, null, pp);
            }
            return SCRIPT_CONTINUE;
        }
        if (menagerie_bioll_condition_hasVarNonBiollQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_8");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (menagerie_bioll_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_11");
                }
                utils.setScriptVar(player, "conversation.menagerie_bioll.branchId", 5);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                pp.digitInteger = menagerie_bioll_tokenDI_varMenagerieQuest(player, npc);
                npcStartConversation(player, npc, "menagerie_bioll", null, pp, responses);
            }
            else 
            {
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                pp.digitInteger = menagerie_bioll_tokenDI_varMenagerieQuest(player, npc);
                chat.chat(npc, player, null, null, pp);
            }
            return SCRIPT_CONTINUE;
        }
        if (menagerie_bioll_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_10");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("menagerie_bioll"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.menagerie_bioll.branchId");
        if (branchId == 1 && menagerie_bioll_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && menagerie_bioll_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && menagerie_bioll_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.menagerie_bioll.branchId");
        return SCRIPT_CONTINUE;
    }
}
