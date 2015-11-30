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

public class exar_kun_intro_dig_site_spy extends script.base_script
{
    public exar_kun_intro_dig_site_spy()
    {
    }
    public static String c_stringFile = "conversation/exar_kun_intro_dig_site_spy";
    public boolean exar_kun_intro_dig_site_spy_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean exar_kun_intro_dig_site_spy_condition_exar_kun_02_02_active(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "exar_kun_intro_02", "exar_kun_intro_02_02");
    }
    public void exar_kun_intro_dig_site_spy_action_exar_kun_02_02_signal(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "exar_kun_intro_02_02");
    }
    public int exar_kun_intro_dig_site_spy_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6"))
        {
            if (exar_kun_intro_dig_site_spy_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_8");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (exar_kun_intro_dig_site_spy_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_10");
                    }
                    utils.setScriptVar(player, "conversation.exar_kun_intro_dig_site_spy.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.exar_kun_intro_dig_site_spy.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int exar_kun_intro_dig_site_spy_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_10"))
        {
            if (exar_kun_intro_dig_site_spy_condition__defaultCondition(player, npc))
            {
                exar_kun_intro_dig_site_spy_action_exar_kun_02_02_signal(player, npc);
                string_id message = new string_id(c_stringFile, "s_12");
                utils.removeScriptVar(player, "conversation.exar_kun_intro_dig_site_spy.branchId");
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
            detachScript(self, "conversation.exar_kun_intro_dig_site_spy");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setPosture(self, POSTURE_CROUCHED);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setPosture(self, POSTURE_CROUCHED);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info menuInfo) throws InterruptedException
    {
        int menu = menuInfo.addRootMenu(menu_info_types.CONVERSE_START, null);
        menu_info_data menuInfoData = menuInfo.getMenuItemById(menu);
        menuInfoData.setServerNotify(false);
        setCondition(self, CONDITION_CONVERSABLE);
        setPosture(self, POSTURE_CROUCHED);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        detachScript(self, "conversation.exar_kun_intro_dig_site_spy");
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
        if (exar_kun_intro_dig_site_spy_condition_exar_kun_02_02_active(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_5");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (exar_kun_intro_dig_site_spy_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_6");
                }
                utils.setScriptVar(player, "conversation.exar_kun_intro_dig_site_spy.branchId", 1);
                npcStartConversation(player, npc, "exar_kun_intro_dig_site_spy", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (exar_kun_intro_dig_site_spy_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_14");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("exar_kun_intro_dig_site_spy"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.exar_kun_intro_dig_site_spy.branchId");
        if (branchId == 1 && exar_kun_intro_dig_site_spy_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && exar_kun_intro_dig_site_spy_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.exar_kun_intro_dig_site_spy.branchId");
        return SCRIPT_CONTINUE;
    }
}
