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
import script.library.smuggler;
import script.library.utils;

public class junk_reggi_nym extends script.base_script
{
    public junk_reggi_nym()
    {
    }
    public static String c_stringFile = "conversation/junk_reggi_nym";
    public boolean junk_reggi_nym_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean junk_reggi_nym_condition_check_inv(obj_id player, obj_id npc) throws InterruptedException
    {
        return smuggler.checkInventory(player, npc);
    }
    public void junk_reggi_nym_action_start_dealing(obj_id player, obj_id npc) throws InterruptedException
    {
        dictionary params = new dictionary();
        params.put("player", player);
        messageTo(npc, "startDealing", params, 1.0f, false);
    }
    public void junk_reggi_nym_action_face_to(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
    }
    public int junk_reggi_nym_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_ef8c7236"))
        {
            if (junk_reggi_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_14e5bdc0");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (junk_reggi_nym_condition_check_inv(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (junk_reggi_nym_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b8e27f3c");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2e005077");
                    }
                    utils.setScriptVar(player, "conversation.junk_reggi_nym.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.junk_reggi_nym.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_53d778d8"))
        {
            if (junk_reggi_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_f25d9c2");
                utils.removeScriptVar(player, "conversation.junk_reggi_nym.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int junk_reggi_nym_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_b8e27f3c"))
        {
            if (junk_reggi_nym_condition__defaultCondition(player, npc))
            {
                junk_reggi_nym_action_start_dealing(player, npc);
                string_id message = new string_id(c_stringFile, "s_5c453a2f");
                utils.removeScriptVar(player, "conversation.junk_reggi_nym.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_2e005077"))
        {
            if (junk_reggi_nym_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_233f3214");
                utils.removeScriptVar(player, "conversation.junk_reggi_nym.branchId");
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
            detachScript(self, "conversation.junk_reggi_nym");
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
        detachScript(self, "conversation.junk_reggi_nym");
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
        if (junk_reggi_nym_condition__defaultCondition(player, npc))
        {
            junk_reggi_nym_action_face_to(player, npc);
            string_id message = new string_id(c_stringFile, "s_60d2f507");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (junk_reggi_nym_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (junk_reggi_nym_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_ef8c7236");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_53d778d8");
                }
                utils.setScriptVar(player, "conversation.junk_reggi_nym.branchId", 1);
                npcStartConversation(player, npc, "junk_reggi_nym", message, responses);
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
        if (!conversationId.equals("junk_reggi_nym"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.junk_reggi_nym.branchId");
        if (branchId == 1 && junk_reggi_nym_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && junk_reggi_nym_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.junk_reggi_nym.branchId");
        return SCRIPT_CONTINUE;
    }
}
