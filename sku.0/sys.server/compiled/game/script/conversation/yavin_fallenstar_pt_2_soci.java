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
import script.library.features;
import script.library.groundquests;
import script.library.utils;

public class yavin_fallenstar_pt_2_soci extends script.base_script
{
    public yavin_fallenstar_pt_2_soci()
    {
    }
    public static String c_stringFile = "conversation/yavin_fallenstar_pt_2_soci";
    public boolean yavin_fallenstar_pt_2_soci_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean yavin_fallenstar_pt_2_soci_condition_opcaCrewKilled(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/yavin_fallenstar_pt2");
        int yavin_fallenstar_e12_kill_smugglers = groundquests.getTaskId(questId1, "yavin_fallenstar_e12_kill_smugglers");
        boolean onTask = (questIsTaskComplete(questId1, yavin_fallenstar_e12_kill_smugglers, player));
        return onTask;
    }
    public boolean yavin_fallenstar_pt_2_soci_condition_fallenstar_pt2_complete(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/yavin_fallenstar_pt2");
        boolean OnTask = questIsQuestComplete(questId1, player);
        return OnTask;
    }
    public void yavin_fallenstar_pt_2_soci_action_launchSignal(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "yavin_fallenstar_launch_e14");
    }
    public void yavin_fallenstar_pt_2_soci_action_facePlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
    }
    public int yavin_fallenstar_pt_2_soci_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_142"))
        {
            if (yavin_fallenstar_pt_2_soci_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_144");
                utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_2_soci.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_143"))
        {
            if (yavin_fallenstar_pt_2_soci_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                yavin_fallenstar_pt_2_soci_action_facePlayer(player, npc);
                string_id message = new string_id(c_stringFile, "s_146");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (yavin_fallenstar_pt_2_soci_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_147");
                    }
                    utils.setScriptVar(player, "conversation.yavin_fallenstar_pt_2_soci.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_2_soci.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int yavin_fallenstar_pt_2_soci_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_147"))
        {
            doAnimationAction(player, "explain");
            if (yavin_fallenstar_pt_2_soci_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "taken_aback");
                string_id message = new string_id(c_stringFile, "s_11");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (yavin_fallenstar_pt_2_soci_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.yavin_fallenstar_pt_2_soci.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_2_soci.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int yavin_fallenstar_pt_2_soci_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_13"))
        {
            if (yavin_fallenstar_pt_2_soci_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_disgust");
                yavin_fallenstar_pt_2_soci_action_launchSignal(player, npc);
                string_id message = new string_id(c_stringFile, "s_15");
                utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_2_soci.branchId");
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
            detachScript(self, "conversation.yavin_fallenstar_pt_2_soci");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        setName(self, "Ewrosa Soci");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        setName(self, "Ewrosa Soci");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
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
        detachScript(self, "conversation.yavin_fallenstar_pt_2_soci");
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
        if (yavin_fallenstar_pt_2_soci_condition_fallenstar_pt2_complete(player, npc))
        {
            doAnimationAction(npc, "threaten_combat");
            yavin_fallenstar_pt_2_soci_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_9");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (yavin_fallenstar_pt_2_soci_condition__defaultCondition(player, npc))
        {
            yavin_fallenstar_pt_2_soci_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_141");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (yavin_fallenstar_pt_2_soci_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (yavin_fallenstar_pt_2_soci_condition_opcaCrewKilled(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_142");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_143");
                }
                utils.setScriptVar(player, "conversation.yavin_fallenstar_pt_2_soci.branchId", 2);
                npcStartConversation(player, npc, "yavin_fallenstar_pt_2_soci", message, responses);
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
        if (!conversationId.equals("yavin_fallenstar_pt_2_soci"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.yavin_fallenstar_pt_2_soci.branchId");
        if (branchId == 2 && yavin_fallenstar_pt_2_soci_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && yavin_fallenstar_pt_2_soci_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && yavin_fallenstar_pt_2_soci_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_2_soci.branchId");
        return SCRIPT_CONTINUE;
    }
}
