package script.conversation;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.buff;
import script.library.chat;
import script.library.conversation;
import script.library.factions;
import script.library.group;
import script.library.instance;
import script.library.township;
import script.library.utils;

public class echo_base_launch extends script.base_script
{
    public echo_base_launch()
    {
    }
    public static String c_stringFile = "conversation/echo_base_launch";
    public boolean echo_base_launch_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean echo_base_launch_condition_bothRebel(obj_id player, obj_id npc) throws InterruptedException
    {
        return (factions.isRebel(npc) && factions.isRebel(player));
    }
    public boolean echo_base_launch_condition_bothImperial(obj_id player, obj_id npc) throws InterruptedException
    {
        return (factions.isImperial(npc) && factions.isImperial(player));
    }
    public boolean echo_base_launch_condition_npcRebelPlayerImperial(obj_id player, obj_id npc) throws InterruptedException
    {
        return (factions.isRebel(npc) && factions.isImperial(player));
    }
    public boolean echo_base_launch_condition_npcImperialPlayerRebel(obj_id player, obj_id npc) throws InterruptedException
    {
        return (factions.isImperial(npc) && factions.isRebel(player));
    }
    public boolean echo_base_launch_condition_disableRebel(obj_id player, obj_id npc) throws InterruptedException
    {
        return false;
    }
    public boolean echo_base_launch_condition_disableImperial(obj_id player, obj_id npc) throws InterruptedException
    {
        return false;
    }
    public boolean echo_base_launch_condition_Rebel_awaitingInstance(obj_id player, obj_id npc) throws InterruptedException
    {
        return buff.hasBuff(player, "instance_launching") && factions.isRebel(npc) && factions.isRebel(player);
    }
    public boolean echo_base_launch_condition_Imperial_awaitingInstance(obj_id player, obj_id npc) throws InterruptedException
    {
        return buff.hasBuff(player, "instance_launching") && factions.isImperial(npc) && factions.isImperial(player);
    }
    public boolean echo_base_launch_condition_tooSmallGroup(obj_id player, obj_id npc) throws InterruptedException
    {
        if (isGod(player) || hasObjVar(player, "testingHoth"))
        {
            return false;
        }
        if (group.isGrouped(player))
        {
            obj_id groupObj = getGroupObject(player);
            if (isIdValid(groupObj))
            {
                int numGroupMembers = getGroupSize(groupObj);
                if (numGroupMembers >= 4)
                {
                    obj_id[] groupies = getGroupMemberIds(groupObj);
                    if (groupies != null && groupies.length > 0)
                    {
                        int playerCount = 0;
                        for (int i = 0; i < groupies.length; i++)
                        {
                            if (isPlayer(groupies[i]))
                            {
                                playerCount = playerCount + 1;
                            }
                        }
                        if (playerCount >= 4)
                        {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
    public boolean echo_base_launch_condition_isNotCorrectLevel(obj_id player, obj_id npc) throws InterruptedException
    {
        return getLevel(player) < township.MIN_LEVEL;
    }
    public void echo_base_launch_action_launchRebel(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!instance.isFlaggedForInstance(player, "echo_base"))
        {
            instance.flagPlayerForInstance(player, "echo_base");
        }
        boolean sentToHoth = instance.requestInstanceMovement(player, "echo_base", 1, "rebel");
        if (sentToHoth)
        {
            buff.applyBuff(player, "instance_launching");
        }
    }
    public void echo_base_launch_action_launchImperial(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!instance.isFlaggedForInstance(player, "echo_base"))
        {
            instance.flagPlayerForInstance(player, "echo_base");
        }
        boolean sentToHoth = instance.requestInstanceMovement(player, "echo_base", 2, "imperial");
        if (sentToHoth)
        {
            buff.applyBuff(player, "instance_launching");
        }
    }
    public void echo_base_launch_action_showEchoBaseVendorSui(obj_id player, obj_id npc) throws InterruptedException
    {
        dictionary d = new dictionary();
        d.put("player", player);
        messageTo(npc, "showInventorySUI", d, 0, false);
        return;
    }
    public int echo_base_launch_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_9"))
        {
            if (echo_base_launch_condition_disableRebel(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_45");
                utils.removeScriptVar(player, "conversation.echo_base_launch.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (echo_base_launch_condition_tooSmallGroup(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_53");
                utils.removeScriptVar(player, "conversation.echo_base_launch.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (!echo_base_launch_condition_disableRebel(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_13");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (echo_base_launch_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (echo_base_launch_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_23");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_25");
                    }
                    utils.setScriptVar(player, "conversation.echo_base_launch.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.echo_base_launch.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_20"))
        {
            if (echo_base_launch_condition__defaultCondition(player, npc))
            {
                echo_base_launch_action_showEchoBaseVendorSui(player, npc);
                string_id message = new string_id(c_stringFile, "s_22");
                utils.removeScriptVar(player, "conversation.echo_base_launch.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int echo_base_launch_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_23"))
        {
            if (echo_base_launch_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute1");
                echo_base_launch_action_launchRebel(player, npc);
                string_id message = new string_id(c_stringFile, "s_24");
                utils.removeScriptVar(player, "conversation.echo_base_launch.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_25"))
        {
            if (echo_base_launch_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_disgust");
                string_id message = new string_id(c_stringFile, "s_26");
                utils.removeScriptVar(player, "conversation.echo_base_launch.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int echo_base_launch_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_32"))
        {
            if (echo_base_launch_condition_disableImperial(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_46");
                utils.removeScriptVar(player, "conversation.echo_base_launch.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (echo_base_launch_condition_tooSmallGroup(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_55");
                utils.removeScriptVar(player, "conversation.echo_base_launch.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (!echo_base_launch_condition_disableImperial(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_36");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (echo_base_launch_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (echo_base_launch_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_38");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_42");
                    }
                    utils.setScriptVar(player, "conversation.echo_base_launch.branchId", 16);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.echo_base_launch.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_51"))
        {
            if (echo_base_launch_condition__defaultCondition(player, npc))
            {
                echo_base_launch_action_showEchoBaseVendorSui(player, npc);
                string_id message = new string_id(c_stringFile, "s_54");
                utils.removeScriptVar(player, "conversation.echo_base_launch.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int echo_base_launch_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_38"))
        {
            if (echo_base_launch_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute2");
                echo_base_launch_action_launchImperial(player, npc);
                string_id message = new string_id(c_stringFile, "s_40");
                utils.removeScriptVar(player, "conversation.echo_base_launch.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_42"))
        {
            if (echo_base_launch_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                string_id message = new string_id(c_stringFile, "s_44");
                utils.removeScriptVar(player, "conversation.echo_base_launch.branchId");
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
            detachScript(self, "conversation.echo_base_launch");
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
        detachScript(self, "conversation.echo_base_launch");
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
        if (echo_base_launch_condition_isNotCorrectLevel(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_57");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (echo_base_launch_condition_Rebel_awaitingInstance(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_50");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (echo_base_launch_condition_npcRebelPlayerImperial(player, npc))
        {
            doAnimationAction(npc, "wave_on_dismissing");
            string_id message = new string_id(c_stringFile, "s_18");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (echo_base_launch_condition_bothRebel(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_7");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (echo_base_launch_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (echo_base_launch_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_9");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_20");
                }
                utils.setScriptVar(player, "conversation.echo_base_launch.branchId", 4);
                npcStartConversation(player, npc, "echo_base_launch", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (echo_base_launch_condition_Imperial_awaitingInstance(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_49");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (echo_base_launch_condition_npcImperialPlayerRebel(player, npc))
        {
            doAnimationAction(npc, "threaten");
            string_id message = new string_id(c_stringFile, "s_48");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (echo_base_launch_condition_bothImperial(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_30");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (echo_base_launch_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (echo_base_launch_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_32");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_51");
                }
                utils.setScriptVar(player, "conversation.echo_base_launch.branchId", 13);
                npcStartConversation(player, npc, "echo_base_launch", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (echo_base_launch_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "wave_finger_warning");
            string_id message = new string_id(c_stringFile, "s_58");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("echo_base_launch"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.echo_base_launch.branchId");
        if (branchId == 4 && echo_base_launch_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && echo_base_launch_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && echo_base_launch_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && echo_base_launch_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.echo_base_launch.branchId");
        return SCRIPT_CONTINUE;
    }
}
