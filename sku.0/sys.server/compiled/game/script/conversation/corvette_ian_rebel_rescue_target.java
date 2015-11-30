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
import script.library.group;

public class corvette_ian_rebel_rescue_target extends script.base_script
{
    public corvette_ian_rebel_rescue_target()
    {
    }
    public static String c_stringFile = "conversation/corvette_ian_rebel_rescue_target";
    public boolean corvette_ian_rebel_rescue_target_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean corvette_ian_rebel_rescue_target_condition_alreadyRescued(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasObjVar(npc, "corl_corvette.alreadyRescued");
    }
    public void corvette_ian_rebel_rescue_target_action__defaultAction(obj_id player, obj_id npc) throws InterruptedException
    {
    }
    public void corvette_ian_rebel_rescue_target_action_facePlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
    }
    public void corvette_ian_rebel_rescue_target_action_FactionSuccess(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasObjVar(npc, "corl_corvette.alreadyRescued"))
        {
            setObjVar(npc, "corl_corvette.alreadyRescued", 1);
            messageTo(npc, "escapeNow", null, 3, false);
            int factionReward = 20;
            int factionRewardGroup = factionReward - 5;
            boolean isRescueMission = false;
            if (hasObjVar(player, "corl_corvette.rebel_rescue_mission"))
            {
                factionReward = 250;
                factionRewardGroup = factionReward - 20;
                isRescueMission = true;
                messageTo(player, "youWin", null, 1, false);
            }
            factions.addFactionStanding(player, "Rebel", factionReward);
            if (group.isGrouped(player))
            {
                obj_id groupObj = getGroupObject(player);
                obj_id[] groupMembers = getGroupMemberIds(groupObj);
                int numGroupMembers = groupMembers.length;
                for (int f = 0; f < numGroupMembers; f++)
                {
                    obj_id groupie = groupMembers[f];
                    if (isIdValid(groupie))
                    {
                        if (groupie != player)
                        {
                            factions.addFactionStanding(groupie, "Rebel", factionRewardGroup);
                            if (isRescueMission)
                            {
                                messageTo(groupie, "youWin", null, 1, false);
                            }
                        }
                    }
                }
            }
        }
        return;
    }
    public void corvette_ian_rebel_rescue_target_action_FactionSuccessLesser(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasObjVar(npc, "corl_corvette.alreadyRescued"))
        {
            setObjVar(npc, "corl_corvette.alreadyRescued", 1);
            messageTo(npc, "escapeNow", null, 3, false);
            int factionReward = 15;
            int factionRewardGroup = factionReward - 5;
            boolean isRescueMission = false;
            if (hasObjVar(player, "corl_corvette.rebel_rescue_mission"))
            {
                factionReward = 200;
                factionRewardGroup = factionReward - 20;
                isRescueMission = true;
                messageTo(player, "youWin", null, 1, false);
            }
            factions.addFactionStanding(player, "Rebel", factionReward);
            if (group.isGrouped(player))
            {
                obj_id groupObj = getGroupObject(player);
                obj_id[] groupMembers = getGroupMemberIds(groupObj);
                int numGroupMembers = groupMembers.length;
                for (int f = 0; f < numGroupMembers; f++)
                {
                    obj_id groupie = groupMembers[f];
                    if (isIdValid(groupie))
                    {
                        if (groupie != player)
                        {
                            factions.addFactionStanding(groupie, "Rebel", factionRewardGroup);
                            if (isRescueMission)
                            {
                                messageTo(groupie, "youWin", null, 1, false);
                            }
                        }
                    }
                }
            }
        }
        return;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.corvette_ian_rebel_rescue_target");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setName(self, "Gabriel Ian (a Rebel pilot)");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setName(self, "Gabriel Ian (a Rebel pilot)");
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
        detachScript(self, "npc.conversation.corvette_ian_rebel_rescue_target");
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (corvette_ian_rebel_rescue_target_condition_alreadyRescued(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_9447b2d6");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (corvette_ian_rebel_rescue_target_condition__defaultCondition(player, self))
        {
            corvette_ian_rebel_rescue_target_action_facePlayer(player, self);
            string_id message = new string_id(c_stringFile, "s_7ed61042");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corvette_ian_rebel_rescue_target_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (corvette_ian_rebel_rescue_target_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_9b4d81d1");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_2960a6e2");
                }
                setObjVar(player, "conversation.corvette_ian_rebel_rescue_target.branchId", 2);
                npcStartConversation(player, self, "corvette_ian_rebel_rescue_target", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("corvette_ian_rebel_rescue_target"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.corvette_ian_rebel_rescue_target.branchId");
        if (branchId == 2 && response.equals("s_9b4d81d1"))
        {
            if (corvette_ian_rebel_rescue_target_condition__defaultCondition(player, self))
            {
                corvette_ian_rebel_rescue_target_action_FactionSuccessLesser(player, self);
                string_id message = new string_id(c_stringFile, "s_4f0f61e");
                removeObjVar(player, "conversation.corvette_ian_rebel_rescue_target.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I'm glad to see you guys. I've been trying to work out a escape plan ever since they got me on this vessel but they have kept a close eye on me. How did you find me here?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && response.equals("s_2960a6e2"))
        {
            if (corvette_ian_rebel_rescue_target_condition__defaultCondition(player, self))
            {
                corvette_ian_rebel_rescue_target_action_FactionSuccess(player, self);
                string_id message = new string_id(c_stringFile, "s_3764c8de");
                removeObjVar(player, "conversation.corvette_ian_rebel_rescue_target.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I'm glad to see you guys. I've been trying to work out a escape plan ever since they got me on this vessel but they have kept a close eye on me. How did you find me here?' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.corvette_ian_rebel_rescue_target.branchId");
        return SCRIPT_CONTINUE;
    }
}
