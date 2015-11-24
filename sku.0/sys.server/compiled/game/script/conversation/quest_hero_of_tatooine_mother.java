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

public class quest_hero_of_tatooine_mother extends script.base_script
{
    public quest_hero_of_tatooine_mother()
    {
    }
    public static String c_stringFile = "conversation/quest_hero_of_tatooine_mother";
    public boolean quest_hero_of_tatooine_mother_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean quest_hero_of_tatooine_mother_condition_notOnQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasObjVar(player, "quest.hero_of_tatooine.altruism"))
        {
            return true;
        }
        if (hasObjVar(player, "quest.hero_of_tatooine.altruism.complete"))
        {
            return true;
        }
        obj_id leader = getObjIdObjVar(npc, "quest.hero_of_tatooine.altruism.leader");
        if (player != leader)
        {
            return true;
        }
        return false;
    }
    public boolean quest_hero_of_tatooine_mother_condition_acceptedFollow(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "quest.hero_of_tatooine.altruism"))
        {
            int step = getIntObjVar(player, "quest.hero_of_tatooine.altruism");
            if (step == 2)
            {
                return true;
            }
        }
        return false;
    }
    public boolean quest_hero_of_tatooine_mother_condition_isFollowing(obj_id player, obj_id npc) throws InterruptedException
    {
        if (aiGetMovementState(npc) == MOVEMENT_FOLLOW)
        {
            return true;
        }
        return false;
    }
    public void quest_hero_of_tatooine_mother_action_setFollowAccept(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "quest.hero_of_tatooine.altruism", 2);
    }
    public void quest_hero_of_tatooine_mother_action_startFollowing(obj_id player, obj_id npc) throws InterruptedException
    {
        clearCondition(npc, CONDITION_CONVERSABLE);
        detachScript(npc, "conversation.quest_hero_of_tatooine_mother");
        attachScript(player, "quest.hero_of_tatooine.altruism_player");
        messageTo(npc, "handleStartFollowing", null, 3.0f, false);
    }
    public int quest_hero_of_tatooine_mother_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_5e328776"))
        {
            if (quest_hero_of_tatooine_mother_condition__defaultCondition(player, npc))
            {
                quest_hero_of_tatooine_mother_action_startFollowing(player, npc);
                string_id message = new string_id(c_stringFile, "s_877ce91a");
                utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_mother.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_da196589"))
        {
            if (quest_hero_of_tatooine_mother_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_cd1765f7");
                utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_mother.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_hero_of_tatooine_mother_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_d28e075c"))
        {
            if (quest_hero_of_tatooine_mother_condition__defaultCondition(player, npc))
            {
                quest_hero_of_tatooine_mother_action_setFollowAccept(player, npc);
                string_id message = new string_id(c_stringFile, "s_8892a744");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_hero_of_tatooine_mother_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quest_hero_of_tatooine_mother_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8beec89f");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7d25e81f");
                    }
                    utils.setScriptVar(player, "conversation.quest_hero_of_tatooine_mother.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_mother.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_6441a2a6"))
        {
            if (quest_hero_of_tatooine_mother_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_ecd319b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_hero_of_tatooine_mother_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quest_hero_of_tatooine_mother_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8de4e85c");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9fe4df0a");
                    }
                    utils.setScriptVar(player, "conversation.quest_hero_of_tatooine_mother.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_mother.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_hero_of_tatooine_mother_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_8beec89f"))
        {
            if (quest_hero_of_tatooine_mother_condition__defaultCondition(player, npc))
            {
                quest_hero_of_tatooine_mother_action_startFollowing(player, npc);
                string_id message = new string_id(c_stringFile, "s_14");
                utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_mother.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_7d25e81f"))
        {
            if (quest_hero_of_tatooine_mother_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_17");
                utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_mother.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_hero_of_tatooine_mother_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_8de4e85c"))
        {
            if (quest_hero_of_tatooine_mother_condition__defaultCondition(player, npc))
            {
                quest_hero_of_tatooine_mother_action_setFollowAccept(player, npc);
                string_id message = new string_id(c_stringFile, "s_8892a744");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_hero_of_tatooine_mother_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quest_hero_of_tatooine_mother_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8beec89f");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7d25e81f");
                    }
                    utils.setScriptVar(player, "conversation.quest_hero_of_tatooine_mother.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_mother.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_9fe4df0a"))
        {
            if (quest_hero_of_tatooine_mother_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_e72f8b74");
                utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_mother.branchId");
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
            detachScript(self, "conversation.quest_hero_of_tatooine_mother");
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
        detachScript(self, "conversation.quest_hero_of_tatooine_mother");
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
        if (quest_hero_of_tatooine_mother_condition_acceptedFollow(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_d0294ce0");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (quest_hero_of_tatooine_mother_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (quest_hero_of_tatooine_mother_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_5e328776");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_da196589");
                }
                utils.setScriptVar(player, "conversation.quest_hero_of_tatooine_mother.branchId", 1);
                npcStartConversation(player, npc, "quest_hero_of_tatooine_mother", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (quest_hero_of_tatooine_mother_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_9");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (quest_hero_of_tatooine_mother_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (quest_hero_of_tatooine_mother_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_d28e075c");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_6441a2a6");
                }
                utils.setScriptVar(player, "conversation.quest_hero_of_tatooine_mother.branchId", 4);
                npcStartConversation(player, npc, "quest_hero_of_tatooine_mother", message, responses);
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
        if (!conversationId.equals("quest_hero_of_tatooine_mother"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.quest_hero_of_tatooine_mother.branchId");
        if (branchId == 1 && quest_hero_of_tatooine_mother_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && quest_hero_of_tatooine_mother_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && quest_hero_of_tatooine_mother_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && quest_hero_of_tatooine_mother_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_mother.branchId");
        return SCRIPT_CONTINUE;
    }
}
