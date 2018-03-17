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
import script.library.factions;
import script.library.groundquests;
import script.library.space_quest;
import script.library.utils;

public class rtp_mon_mothma_main extends script.base_script
{
    public rtp_mon_mothma_main()
    {
    }
    public static String c_stringFile = "conversation/rtp_mon_mothma_main";
    public boolean rtp_mon_mothma_main_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean rtp_mon_mothma_main_condition_rtp_mon_mothma_01_active(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "rtp_mon_mothma_01");
    }
    public boolean rtp_mon_mothma_main_condition_rtp_mon_mothma_01_complete(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "rtp_mon_mothma_01", "rtp_mon_mothma_01_03") || groundquests.hasCompletedQuest(player, "rtp_mon_mothma_01");
    }
    public boolean rtp_mon_mothma_main_condition_completedAckbar(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "rtp_ackbar_02");
    }
    public boolean rtp_mon_mothma_main_condition_rtp_mon_mothma_01_task02_active(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "rtp_mon_mothma_01", "rtp_mon_mothma_01_02");
    }
    public boolean rtp_mon_mothma_main_condition_notRebel(obj_id player, obj_id npc) throws InterruptedException
    {
        String playerFaction = factions.getFaction(player);
        if (playerFaction == null || !playerFaction.equals("Rebel"))
        {
            return true;
        }
        return false;
    }
    public boolean rtp_mon_mothma_main_condition_rebel_isOnLeave(obj_id player, obj_id npc) throws InterruptedException
    {
        return factions.isOnLeave(player);
    }
    public void rtp_mon_mothma_main_action_rtp_mon_mothma_01_granted(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "rtp_mon_mothma_01");
    }
    public void rtp_mon_mothma_main_action_rtp_mon_mothma_01_signal(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "rtp_mon_mothma_01_03");
    }
    public int rtp_mon_mothma_main_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_36"))
        {
            if (rtp_mon_mothma_main_condition__defaultCondition(player, npc))
            {
                rtp_mon_mothma_main_action_rtp_mon_mothma_01_granted(player, npc);
                string_id message = new string_id(c_stringFile, "s_38");
                utils.removeScriptVar(player, "conversation.rtp_mon_mothma_main.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_40"))
        {
            if (rtp_mon_mothma_main_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_42");
                utils.removeScriptVar(player, "conversation.rtp_mon_mothma_main.branchId");
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
            detachScript(self, "conversation.rtp_mon_mothma_main");
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
        faceTo(self, player);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        detachScript(self, "conversation.rtp_mon_mothma_main");
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
        if (rtp_mon_mothma_main_condition_notRebel(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_44");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (!rtp_mon_mothma_main_condition_completedAckbar(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_13");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (rtp_mon_mothma_main_condition_rebel_isOnLeave(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_43");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (rtp_mon_mothma_main_condition_rtp_mon_mothma_01_complete(player, npc))
        {
            rtp_mon_mothma_main_action_rtp_mon_mothma_01_signal(player, npc);
            string_id message = new string_id(c_stringFile, "s_20");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (rtp_mon_mothma_main_condition_rtp_mon_mothma_01_task02_active(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_14");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (rtp_mon_mothma_main_condition_rtp_mon_mothma_01_active(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_32");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (rtp_mon_mothma_main_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_34");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (rtp_mon_mothma_main_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (rtp_mon_mothma_main_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_36");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_40");
                }
                utils.setScriptVar(player, "conversation.rtp_mon_mothma_main.branchId", 7);
                npcStartConversation(player, npc, "rtp_mon_mothma_main", message, responses);
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
        if (!conversationId.equals("rtp_mon_mothma_main"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.rtp_mon_mothma_main.branchId");
        if (branchId == 7 && rtp_mon_mothma_main_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.rtp_mon_mothma_main.branchId");
        return SCRIPT_CONTINUE;
    }
}
