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
import script.library.skill;
import script.library.space_flags;
import script.library.space_quest;
import script.library.utils;

public class greeter_tyrena_pilot_friend extends script.base_script
{
    public greeter_tyrena_pilot_friend()
    {
    }
    public static String c_stringFile = "conversation/greeter_tyrena_pilot_friend";
    public boolean greeter_tyrena_pilot_friend_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean greeter_tyrena_pilot_friend_condition_remembersPlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        return (utils.hasScriptVar(player, "metNewbiePilot"));
    }
    public boolean greeter_tyrena_pilot_friend_condition_spokeWithUlaire(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.hasScriptVar(player, "spokeWithUlaireRoye");
    }
    public boolean greeter_tyrena_pilot_friend_condition_isAnImperialPilot(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_flags.isImperialPilot(player);
    }
    public boolean greeter_tyrena_pilot_friend_condition_isARebelPilot(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_flags.isRebelPilot(player);
    }
    public boolean greeter_tyrena_pilot_friend_condition_isPrivateerPilot(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_flags.isNeutralPilot(player);
    }
    public boolean greeter_tyrena_pilot_friend_condition_hasSpaceExpansion(obj_id player, obj_id npc) throws InterruptedException
    {
        return (features.isSpaceEdition(player));
    }
    public boolean greeter_tyrena_pilot_friend_condition_hasSpaceShip(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasShip(player));
    }
    public void greeter_tyrena_pilot_friend_action_rememberPlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.setScriptVar(player, "metNewbiePilot", true);
    }
    public void greeter_tyrena_pilot_friend_action_grantQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "delivery", "tatooine_newbie_1");
        space_quest.grantNewbieShip(player, "rebel");
    }
    public int greeter_tyrena_pilot_friend_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_17345b4d"))
        {
            if (greeter_tyrena_pilot_friend_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_bd6f92c3");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (greeter_tyrena_pilot_friend_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_729d1b46");
                    }
                    utils.setScriptVar(player, "conversation.greeter_tyrena_pilot_friend.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.greeter_tyrena_pilot_friend.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_41323ada"))
        {
            if (greeter_tyrena_pilot_friend_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_5b823594");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (greeter_tyrena_pilot_friend_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2b99f43");
                    }
                    utils.setScriptVar(player, "conversation.greeter_tyrena_pilot_friend.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.greeter_tyrena_pilot_friend.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int greeter_tyrena_pilot_friend_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_729d1b46"))
        {
            if (greeter_tyrena_pilot_friend_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_c81c50e7");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (greeter_tyrena_pilot_friend_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8b392030");
                    }
                    utils.setScriptVar(player, "conversation.greeter_tyrena_pilot_friend.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.greeter_tyrena_pilot_friend.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int greeter_tyrena_pilot_friend_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_2b99f43"))
        {
            if (greeter_tyrena_pilot_friend_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_c81c50e7");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (greeter_tyrena_pilot_friend_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8b392030");
                    }
                    utils.setScriptVar(player, "conversation.greeter_tyrena_pilot_friend.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.greeter_tyrena_pilot_friend.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int greeter_tyrena_pilot_friend_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_8b392030"))
        {
            if (greeter_tyrena_pilot_friend_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "pound_fist_palm");
                string_id message = new string_id(c_stringFile, "s_2d397ab5");
                utils.removeScriptVar(player, "conversation.greeter_tyrena_pilot_friend.branchId");
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
            detachScript(self, "conversation.greeter_tyrena_pilot_friend");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_SPACE_INTERESTING);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_SPACE_INTERESTING);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info menuInfo) throws InterruptedException
    {
        int menu = menuInfo.addRootMenu(menu_info_types.CONVERSE_START, null);
        menu_info_data menuInfoData = menuInfo.getMenuItemById(menu);
        menuInfoData.setServerNotify(false);
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        faceTo(self, player);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_SPACE_INTERESTING);
        detachScript(self, "conversation.greeter_tyrena_pilot_friend");
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
        if (greeter_tyrena_pilot_friend_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "shake_head_disgust");
            string_id message = new string_id(c_stringFile, "s_26a9fa7a");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (!greeter_tyrena_pilot_friend_condition_spokeWithUlaire(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (greeter_tyrena_pilot_friend_condition_spokeWithUlaire(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_17345b4d");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_41323ada");
                }
                utils.setScriptVar(player, "conversation.greeter_tyrena_pilot_friend.branchId", 1);
                npcStartConversation(player, npc, "greeter_tyrena_pilot_friend", message, responses);
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
        if (!conversationId.equals("greeter_tyrena_pilot_friend"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.greeter_tyrena_pilot_friend.branchId");
        if (branchId == 1 && greeter_tyrena_pilot_friend_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && greeter_tyrena_pilot_friend_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && greeter_tyrena_pilot_friend_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && greeter_tyrena_pilot_friend_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.greeter_tyrena_pilot_friend.branchId");
        return SCRIPT_CONTINUE;
    }
}
