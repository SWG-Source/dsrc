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

public class som_battlefield_miner_leader extends script.base_script
{
    public som_battlefield_miner_leader()
    {
    }
    public static String c_stringFile = "conversation/som_battlefield_miner_leader";
    public boolean som_battlefield_miner_leader_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean som_battlefield_miner_leader_condition_isMinerFollowing(obj_id player, obj_id npc) throws InterruptedException
    {
        return (ai_lib.isFollowing(npc) && !utils.hasScriptVar(npc, "deployed"));
    }
    public boolean som_battlefield_miner_leader_condition_isNotFollowing(obj_id player, obj_id npc) throws InterruptedException
    {
        return (!ai_lib.isFollowing(npc) && !utils.hasScriptVar(npc, "deployed"));
    }
    public boolean som_battlefield_miner_leader_condition_isDeployed(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.hasScriptVar(npc, "deployed");
    }
    public boolean som_battlefield_miner_leader_condition_isNotDeployed(obj_id player, obj_id npc) throws InterruptedException
    {
        return (!utils.hasScriptVar(npc, "deployed"));
    }
    public void som_battlefield_miner_leader_action_deployForces(obj_id player, obj_id npc) throws InterruptedException
    {
        ai_lib.aiStopFollowing(npc);
        location here = getLocation(npc);
        aiSetHomeLocation(npc, here);
        utils.setScriptVar(npc, "player", player);
        messageTo(npc, "deployForces", null, 0, false);
    }
    public void som_battlefield_miner_leader_action_startFollowing(obj_id player, obj_id npc) throws InterruptedException
    {
        ai_lib.aiFollow(npc, player);
        setMovementRun(npc);
    }
    public void som_battlefield_miner_leader_action_stopFollowing(obj_id player, obj_id npc) throws InterruptedException
    {
        ai_lib.aiStopFollowing(npc);
        location here = getLocation(npc);
        aiSetHomeLocation(npc, here);
        ai_lib.setDefaultCalmBehavior(npc, ai_lib.BEHAVIOR_SENTINEL);
    }
    public void som_battlefield_miner_leader_action_undeployAndFollow(obj_id player, obj_id npc) throws InterruptedException
    {
        messageTo(npc, "unDeployForces", null, 0, false);
        ai_lib.aiFollow(npc, player);
    }
    public int som_battlefield_miner_leader_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6"))
        {
            if (som_battlefield_miner_leader_condition__defaultCondition(player, npc))
            {
                som_battlefield_miner_leader_action_startFollowing(player, npc);
                string_id message = new string_id(c_stringFile, "s_8");
                utils.removeScriptVar(player, "conversation.som_battlefield_miner_leader.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_10"))
        {
            if (som_battlefield_miner_leader_condition__defaultCondition(player, npc))
            {
                som_battlefield_miner_leader_action_stopFollowing(player, npc);
                string_id message = new string_id(c_stringFile, "s_12");
                utils.removeScriptVar(player, "conversation.som_battlefield_miner_leader.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_14"))
        {
            if (som_battlefield_miner_leader_condition__defaultCondition(player, npc))
            {
                som_battlefield_miner_leader_action_deployForces(player, npc);
                string_id message = new string_id(c_stringFile, "s_16");
                utils.removeScriptVar(player, "conversation.som_battlefield_miner_leader.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_18"))
        {
            if (som_battlefield_miner_leader_condition__defaultCondition(player, npc))
            {
                som_battlefield_miner_leader_action_undeployAndFollow(player, npc);
                string_id message = new string_id(c_stringFile, "s_20");
                utils.removeScriptVar(player, "conversation.som_battlefield_miner_leader.branchId");
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
            detachScript(self, "conversation.som_battlefield_miner_leader");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        chat.setChatMood(self, chat.MOOD_NONE);
        setCondition(self, CONDITION_INVULNERABLE);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        chat.setChatMood(self, chat.MOOD_NONE);
        setCondition(self, CONDITION_INVULNERABLE);
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
        detachScript(self, "conversation.som_battlefield_miner_leader");
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
        if (som_battlefield_miner_leader_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_4");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (som_battlefield_miner_leader_condition_isNotFollowing(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (som_battlefield_miner_leader_condition_isMinerFollowing(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (som_battlefield_miner_leader_condition_isNotDeployed(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (som_battlefield_miner_leader_condition_isDeployed(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse3 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_6");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_10");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_14");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_18");
                }
                utils.setScriptVar(player, "conversation.som_battlefield_miner_leader.branchId", 1);
                npcStartConversation(player, npc, "som_battlefield_miner_leader", message, responses);
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
        if (!conversationId.equals("som_battlefield_miner_leader"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.som_battlefield_miner_leader.branchId");
        if (branchId == 1 && som_battlefield_miner_leader_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.som_battlefield_miner_leader.branchId");
        return SCRIPT_CONTINUE;
    }
}
