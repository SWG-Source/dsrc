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

public class ep3_forest_wirartu_attack extends script.base_script
{
    public ep3_forest_wirartu_attack()
    {
    }
    public static String c_stringFile = "conversation/ep3_forest_wirartu_attack";
    public boolean ep3_forest_wirartu_attack_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean ep3_forest_wirartu_attack_condition_isEpicTaskActiveOne(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_forest_wirartu_epic_3", 0);
    }
    public boolean ep3_forest_wirartu_attack_condition_isOnHold(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_forest_on_hold", 0);
    }
    public void ep3_forest_wirartu_attack_action_giveSignalOne(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.completeQuest(player, "ep3_forest_wirartu_epic_1");
    }
    public void ep3_forest_wirartu_attack_action_IncapMe(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "ep3_forest_wirartu_epic_2");
        kill(npc);
        messageTo(npc, "cleanMeUp", null, 10, false);
    }
    public void ep3_forest_wirartu_attack_action_giveQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "ep3_forest_wirartu_epic_3");
        messageTo(npc, "cleanMeUp", null, 20, false);
    }
    public int ep3_forest_wirartu_attack_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_869"))
        {
            ep3_forest_wirartu_attack_action_giveSignalOne(player, npc);
            if (ep3_forest_wirartu_attack_condition__defaultCondition(player, npc))
            {
                ep3_forest_wirartu_attack_action_giveQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_871");
                utils.removeScriptVar(player, "conversation.ep3_forest_wirartu_attack.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_873"))
        {
            ep3_forest_wirartu_attack_action_giveSignalOne(player, npc);
            if (ep3_forest_wirartu_attack_condition__defaultCondition(player, npc))
            {
                ep3_forest_wirartu_attack_action_IncapMe(player, npc);
                string_id message = new string_id(c_stringFile, "s_875");
                utils.removeScriptVar(player, "conversation.ep3_forest_wirartu_attack.branchId");
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
            detachScript(self, "conversation.ep3_forest_wirartu_attack");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setName(self, new string_id("ep3/npc_names", "wirartu"));
        setInvulnerable(self, true);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setName(self, new string_id("ep3/npc_names", "wirartu"));
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
        detachScript(self, "conversation.ep3_forest_wirartu_attack");
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
        if (ep3_forest_wirartu_attack_condition_isEpicTaskActiveOne(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_865");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_forest_wirartu_attack_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_867");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_forest_wirartu_attack_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_forest_wirartu_attack_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_869");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_873");
                }
                utils.setScriptVar(player, "conversation.ep3_forest_wirartu_attack.branchId", 2);
                npcStartConversation(player, npc, "ep3_forest_wirartu_attack", message, responses);
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
        if (!conversationId.equals("ep3_forest_wirartu_attack"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.ep3_forest_wirartu_attack.branchId");
        if (branchId == 2 && ep3_forest_wirartu_attack_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.ep3_forest_wirartu_attack.branchId");
        return SCRIPT_CONTINUE;
    }
}
