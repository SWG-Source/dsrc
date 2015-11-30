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

public class ep3_rryatt_vritol extends script.base_script
{
    public ep3_rryatt_vritol()
    {
    }
    public static String c_stringFile = "conversation/ep3_rryatt_vritol";
    public boolean ep3_rryatt_vritol_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean ep3_rryatt_vritol_condition_cannotSpeakWookiee(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.canSpeakWookiee(player, npc);
    }
    public boolean ep3_rryatt_vritol_condition_sentByBrody(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "ep3_hunt_vritol_reward_mount");
    }
    public void ep3_rryatt_vritol_action_thinkWookieeConfusion(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.emoteWookieeConfusion(player, npc);
    }
    public void ep3_rryatt_vritol_action_grantKashBanthaMount(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "vritol_speakToVritol");
    }
    public int ep3_rryatt_vritol_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_221"))
        {
            if (ep3_rryatt_vritol_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_224");
                utils.removeScriptVar(player, "conversation.ep3_rryatt_vritol.branchId");
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
            detachScript(self, "conversation.ep3_rryatt_vritol");
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
        faceTo(self, player);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        detachScript(self, "conversation.ep3_rryatt_vritol");
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
        if (ep3_rryatt_vritol_condition_cannotSpeakWookiee(player, npc))
        {
            ep3_rryatt_vritol_action_thinkWookieeConfusion(player, npc);
            string_id message = new string_id(c_stringFile, "s_226");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_rryatt_vritol_condition_sentByBrody(player, npc))
        {
            ep3_rryatt_vritol_action_grantKashBanthaMount(player, npc);
            string_id message = new string_id(c_stringFile, "s_218");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_rryatt_vritol_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_221");
                }
                utils.setScriptVar(player, "conversation.ep3_rryatt_vritol.branchId", 2);
                npcStartConversation(player, npc, "ep3_rryatt_vritol", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_rryatt_vritol_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_669");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("ep3_rryatt_vritol"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.ep3_rryatt_vritol.branchId");
        if (branchId == 2 && ep3_rryatt_vritol_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.ep3_rryatt_vritol.branchId");
        return SCRIPT_CONTINUE;
    }
}
