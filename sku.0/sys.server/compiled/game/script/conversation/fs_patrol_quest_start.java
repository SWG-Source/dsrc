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
import script.library.fs_quests;
import script.library.quests;
import script.library.utils;

public class fs_patrol_quest_start extends script.base_script
{
    public fs_patrol_quest_start()
    {
    }
    public static String c_stringFile = "conversation/fs_patrol_quest_start";
    public boolean fs_patrol_quest_start_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean fs_patrol_quest_start_condition_canTalkToVillagers(obj_id player, obj_id npc) throws InterruptedException
    {
        return fs_quests.isVillageEligible(player);
    }
    public boolean fs_patrol_quest_start_condition_ableToDoQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return fs_quests.hasQuestAccepted(player);
    }
    public boolean fs_patrol_quest_start_condition_finished(obj_id player, obj_id npc) throws InterruptedException
    {
        return quests.isComplete("fs_patrol_quest_finish", player);
    }
    public boolean fs_patrol_quest_start_condition_Complete9andQualifiesToComplete10(obj_id player, obj_id npc) throws InterruptedException
    {
        if (quests.isComplete("fs_patrol_quest_9", player) && quests.isActive("fs_patrol_quest_10", player))
        {
            String questName = "fs_patrol_quest_10";
            int waypointCount = 0;
            boolean currentPatrol = false;
            boolean currentPatrolPoint = false;
            if (hasObjVar(player, "quest.fs_patrol.current_patrol"))
            {
                currentPatrol = getBooleanObjVar(player, "quest.fs_patrol.current_patrol");
            }
            if (hasObjVar(player, "quest.fs_patrol.current_patrol_point"))
            {
                currentPatrolPoint = getBooleanObjVar(player, "quest.fs_patrol.current_patrol_point");
            }
            if (hasObjVar(player, "quest." + questName + ".waypointcount"))
            {
                waypointCount = getIntObjVar(player, "quest." + questName + ".waypointcount");
            }
            if (waypointCount >= 8 && currentPatrolPoint && currentPatrol)
            {
                return true;
            }
        }
        return false;
    }
    public boolean fs_patrol_quest_start_condition_Complete19andQualifiesToComplete20(obj_id player, obj_id npc) throws InterruptedException
    {
        if (quests.isComplete("fs_patrol_quest_19", player) && quests.isActive("fs_patrol_quest_20", player))
        {
            String questName = "fs_patrol_quest_20";
            int waypointCount = 0;
            boolean currentPatrol = false;
            boolean currentPatrolPoint = false;
            if (hasObjVar(player, "quest.fs_patrol.current_patrol"))
            {
                currentPatrol = getBooleanObjVar(player, "quest.fs_patrol.current_patrol");
            }
            if (hasObjVar(player, "quest.fs_patrol.current_patrol_point"))
            {
                currentPatrolPoint = getBooleanObjVar(player, "quest.fs_patrol.current_patrol_point");
            }
            if (hasObjVar(player, "quest." + questName + ".waypointcount"))
            {
                waypointCount = getIntObjVar(player, "quest." + questName + ".waypointcount");
            }
            if (waypointCount >= 8 && currentPatrolPoint && currentPatrol)
            {
                return true;
            }
        }
        return false;
    }
    public boolean fs_patrol_quest_start_condition_CheckIfElevenIsGiven(obj_id player, obj_id npc) throws InterruptedException
    {
        if (quests.isComplete("fs_patrol_quest_11", player) || quests.isActive("fs_patrol_quest_11", player))
        {
            return true;
        }
        return false;
    }
    public boolean fs_patrol_quest_start_condition_CheckIfOneIsGiven(obj_id player, obj_id npc) throws InterruptedException
    {
        if (quests.isComplete("fs_patrol_quest_1", player) || quests.isActive("fs_patrol_quest_1", player))
        {
            return true;
        }
        return false;
    }
    public boolean fs_patrol_quest_start_condition_CheckIfTenIsComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        return quests.isComplete("fs_patrol_quest_10", player);
    }
    public boolean fs_patrol_quest_start_condition_FindCurrentCheckIfQualifyToComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        String questName = "fs_patrol_quest_";
        for (int i = 1; i < 21; i++)
        {
            if (DEBUGGING)
            {
                sendSystemMessageTestingOnly(player, "fs_patrol_quest_" + i + " checking if active");
            }
            if (quests.isActive("fs_patrol_quest_" + i, player))
            {
                int waypointCount = 0;
                boolean currentPatrol = false;
                boolean currentPatrolPoint = false;
                if (hasObjVar(player, "quest.fs_patrol.current_patrol"))
                {
                    currentPatrol = getBooleanObjVar(player, "quest.fs_patrol.current_patrol");
                }
                if (hasObjVar(player, "quest.fs_patrol.current_patrol_point"))
                {
                    currentPatrolPoint = getBooleanObjVar(player, "quest.fs_patrol.current_patrol_point");
                }
                if (hasObjVar(player, "quest." + questName + i + ".waypointcount"))
                {
                    waypointCount = getIntObjVar(player, "quest." + questName + i + ".waypointcount");
                }
                if (DEBUGGING)
                {
                    sendSystemMessageTestingOnly(player, "Found for fs_patrol_quest_" + i + " current, currentpoint, count" + currentPatrol + currentPatrolPoint + waypointCount);
                }
                if (waypointCount >= 8 && currentPatrolPoint && currentPatrol)
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean fs_patrol_quest_start_condition_inventoryCheck(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id inv = utils.getInventoryContainer(player);
        int freeSpace = getVolumeFree(inv);
        if (freeSpace < 1)
        {
            if (quests.isActive("fs_patrol_quest_20", player))
            {
                String questName = "fs_patrol_quest_20";
                int waypointCount = 0;
                boolean currentPatrol = false;
                boolean currentPatrolPoint = false;
                if (hasObjVar(player, "quest.fs_patrol.current_patrol"))
                {
                    currentPatrol = getBooleanObjVar(player, "quest.fs_patrol.currnet_patrol");
                }
                if (hasObjVar(player, "quest.fs_patrol.current_patrol_point"))
                {
                    currentPatrolPoint = getBooleanObjVar(player, "quest.fs_patrol.currnet_patrol_point");
                }
                if (hasObjVar(player, "quest." + questName + ".waypointcount"))
                {
                    waypointCount = getIntObjVar(player, "quest." + questName + ".waypointcount");
                }
                if (waypointCount >= 8 && currentPatrolPoint && currentPatrol)
                {
                    return true;
                }
            }
        }
        return false;
    }
    public void fs_patrol_quest_start_action_GiveQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        quests.activate("fs_patrol_quest_start", player, npc);
        fs_quests.setQuestAccepted(player);
        attachScript(player, "quest.force_sensitive.fs_patrol_spawns1");
        setObjVar(player, "quest.fs_patrol.current_patrol", true);
        setObjVar(player, "quest.fs_patrol.current_patrol_point", true);
    }
    public void fs_patrol_quest_start_action_GiveNextQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        String questName = "fs_patrol_quest_";
        boolean gaveNextQuest = false;
        int i = 1;
        while (!gaveNextQuest)
        {
            if (quests.isActive("fs_patrol_quest_" + i, player))
            {
                if (i != 10 || i != 20)
                {
                    quests.complete(questName + i, player, true);
                    int j = i + 1;
                    quests.activate(questName + j, player, npc);
                    setObjVar(player, "quest.fs_patrol.current_patrol", true);
                    setObjVar(player, "quest.fs_patrol.current_patrol_point", true);
                    gaveNextQuest = true;
                }
            }
            i++;
            if (i >= 21)
            {
                gaveNextQuest = true;
            }
        }
    }
    public void fs_patrol_quest_start_action_GiveQuestEleven(obj_id player, obj_id npc) throws InterruptedException
    {
        quests.activate("fs_patrol_quest_11", player, npc);
        setObjVar(player, "quest.fs_patrol.current_patrol", true);
        setObjVar(player, "quest.fs_patrol.current_patrol_point", true);
    }
    public void fs_patrol_quest_start_action_UnlockFSBranch(obj_id player, obj_id npc) throws InterruptedException
    {
        fs_quests.unlockBranch(player, "force_sensitive_combat_prowess_ranged_accuracy");
        quests.complete("fs_patrol_quest_10", player, true);
    }
    public void fs_patrol_quest_start_action_GiveReward(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id createdObject = createObjectInInventoryAllowOverload("object/tangible/item/quest/force_sensitive/fs_village_bannerpole_s01.iff", player);
        quests.complete("fs_patrol_quest_20", player, true);
        detachScript(player, "quest.force_sensitive.fs_patrol_spawns1");
    }
    public void fs_patrol_quest_start_action_resetCurrentPatrol(obj_id player, obj_id npc) throws InterruptedException
    {
        String questName = "fs_patrol_quest_";
        for (int j = 1; j < 21; j++)
        {
            if (hasObjVar(player, "quest." + questName + j))
            {
                removeObjVar(player, "quest." + questName + j + ".waypointcount");
                for (int i = 0; i < 8; i++)
                {
                    if (hasObjVar(player, "quest." + questName + j + ".waypoint" + i))
                    {
                        obj_id waypoint = getObjIdObjVar(player, "quest." + questName + j + ".waypoint" + i);
                        if (isIdValid(waypoint))
                        {
                            destroyWaypointInDatapad(waypoint, player);
                        }
                        removeObjVar(player, "quest." + questName + j + ".waypoint" + i);
                    }
                }
                quests.deactivate(questName + j, player);
                quests.activate(questName + j, player, npc);
                setObjVar(player, "quest.fs_patrol.current_patrol", true);
                setObjVar(player, "quest.fs_patrol.current_patrol_point", true);
            }
        }
    }
    public static final boolean DEBUGGING = false;
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.fs_patrol_quest_start");
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
        detachScript(self, "npc.conversation.fs_patrol_quest_start");
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
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (!fs_patrol_quest_start_condition_canTalkToVillagers(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_ebd8c749");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (fs_patrol_quest_start_condition_finished(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_5f2a0dac");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (fs_patrol_quest_start_condition_Complete9andQualifiesToComplete10(player, self))
        {
            fs_patrol_quest_start_action_UnlockFSBranch(player, self);
            string_id message = new string_id(c_stringFile, "s_7d744b43");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (fs_patrol_quest_start_condition_Complete19andQualifiesToComplete20(player, self))
        {
            fs_patrol_quest_start_action_GiveReward(player, self);
            string_id message = new string_id(c_stringFile, "s_13f339fe");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (fs_patrol_quest_start_condition_CheckIfElevenIsGiven(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_4b5df4f5");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (fs_patrol_quest_start_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (fs_patrol_quest_start_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_d70dba34");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_d33566f3");
                }
                setObjVar(player, "conversation.fs_patrol_quest_start.branchId", 5);
                npcStartConversation(player, self, "fs_patrol_quest_start", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (fs_patrol_quest_start_condition_CheckIfTenIsComplete(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_fb116507");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (fs_patrol_quest_start_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (fs_patrol_quest_start_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_ec2d9d88");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_60a74f25");
                }
                setObjVar(player, "conversation.fs_patrol_quest_start.branchId", 11);
                npcStartConversation(player, self, "fs_patrol_quest_start", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (fs_patrol_quest_start_condition_CheckIfOneIsGiven(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_9e38953b");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (fs_patrol_quest_start_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (fs_patrol_quest_start_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_6d3ed33b");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_d33566f3");
                }
                setObjVar(player, "conversation.fs_patrol_quest_start.branchId", 14);
                npcStartConversation(player, self, "fs_patrol_quest_start", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (fs_patrol_quest_start_condition_ableToDoQuest(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_37871f01");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (fs_patrol_quest_start_condition__defaultCondition(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_b76cc5ed");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (fs_patrol_quest_start_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (fs_patrol_quest_start_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_240c8c9d");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_d6695e83");
                }
                setObjVar(player, "conversation.fs_patrol_quest_start.branchId", 21);
                npcStartConversation(player, self, "fs_patrol_quest_start", message, responses);
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
        if (!conversationId.equals("fs_patrol_quest_start"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.fs_patrol_quest_start.branchId");
        if (branchId == 5 && response.equals("s_d70dba34"))
        {
            if (fs_patrol_quest_start_condition_FindCurrentCheckIfQualifyToComplete(player, self))
            {
                fs_patrol_quest_start_action_GiveNextQuest(player, self);
                string_id message = new string_id(c_stringFile, "s_1f1e059");
                removeObjVar(player, "conversation.fs_patrol_quest_start.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (!fs_patrol_quest_start_condition_FindCurrentCheckIfQualifyToComplete(player, self))
            {
                fs_patrol_quest_start_action_resetCurrentPatrol(player, self);
                string_id message = new string_id(c_stringFile, "s_67427da4");
                removeObjVar(player, "conversation.fs_patrol_quest_start.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Are you back already?  You finish that last patrol?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_d33566f3"))
        {
            if (fs_patrol_quest_start_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_cbbe47bb");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (fs_patrol_quest_start_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (fs_patrol_quest_start_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d70dba34");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4c695dbd");
                    }
                    setObjVar(player, "conversation.fs_patrol_quest_start.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.fs_patrol_quest_start.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Are you back already?  You finish that last patrol?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && response.equals("s_d70dba34"))
        {
            fs_patrol_quest_start_action_resetCurrentPatrol(player, self);
            if (fs_patrol_quest_start_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_61dc9e2d");
                removeObjVar(player, "conversation.fs_patrol_quest_start.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Are you sure you want to start over?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && response.equals("s_4c695dbd"))
        {
            if (fs_patrol_quest_start_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_30c694db");
                removeObjVar(player, "conversation.fs_patrol_quest_start.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Are you sure you want to start over?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && response.equals("s_ec2d9d88"))
        {
            fs_patrol_quest_start_action_GiveQuestEleven(player, self);
            if (fs_patrol_quest_start_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_2872b835");
                removeObjVar(player, "conversation.fs_patrol_quest_start.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I need more help on the patrol routes.  You're proving to be quite valuable to this little operation.  Are you up for some more?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && response.equals("s_60a74f25"))
        {
            if (fs_patrol_quest_start_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_1485495");
                removeObjVar(player, "conversation.fs_patrol_quest_start.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I need more help on the patrol routes.  You're proving to be quite valuable to this little operation.  Are you up for some more?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && response.equals("s_6d3ed33b"))
        {
            if (fs_patrol_quest_start_condition_FindCurrentCheckIfQualifyToComplete(player, self))
            {
                fs_patrol_quest_start_action_GiveNextQuest(player, self);
                string_id message = new string_id(c_stringFile, "s_1b5216d5");
                removeObjVar(player, "conversation.fs_patrol_quest_start.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (!fs_patrol_quest_start_condition_FindCurrentCheckIfQualifyToComplete(player, self))
            {
                fs_patrol_quest_start_action_resetCurrentPatrol(player, self);
                string_id message = new string_id(c_stringFile, "s_491ce1ad");
                removeObjVar(player, "conversation.fs_patrol_quest_start.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Did you finish that patrol?  I see you're back, but I don't think that necessarily means you finished...' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && response.equals("s_d33566f3"))
        {
            if (fs_patrol_quest_start_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_cbbe47bb");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (fs_patrol_quest_start_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (fs_patrol_quest_start_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d70dba34");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4c695dbd");
                    }
                    setObjVar(player, "conversation.fs_patrol_quest_start.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.fs_patrol_quest_start.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Did you finish that patrol?  I see you're back, but I don't think that necessarily means you finished...' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && response.equals("s_d70dba34"))
        {
            fs_patrol_quest_start_action_resetCurrentPatrol(player, self);
            if (fs_patrol_quest_start_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_649f5207");
                removeObjVar(player, "conversation.fs_patrol_quest_start.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Are you sure you want to start over?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && response.equals("s_4c695dbd"))
        {
            if (fs_patrol_quest_start_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_30c694db");
                removeObjVar(player, "conversation.fs_patrol_quest_start.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Are you sure you want to start over?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && response.equals("s_240c8c9d"))
        {
            if (fs_patrol_quest_start_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_2248d7ad");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (fs_patrol_quest_start_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (fs_patrol_quest_start_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_199a175c");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_764478e7");
                    }
                    setObjVar(player, "conversation.fs_patrol_quest_start.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.fs_patrol_quest_start.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'How are you, soldier?  I understand you're looking for something to do?  Great.  I have some patrol routes I need covered.  You interested?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && response.equals("s_d6695e83"))
        {
            if (fs_patrol_quest_start_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_fa12949a");
                removeObjVar(player, "conversation.fs_patrol_quest_start.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'How are you, soldier?  I understand you're looking for something to do?  Great.  I have some patrol routes I need covered.  You interested?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && response.equals("s_199a175c"))
        {
            fs_patrol_quest_start_action_GiveQuestOne(player, self);
            if (fs_patrol_quest_start_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_9ec72704");
                removeObjVar(player, "conversation.fs_patrol_quest_start.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'All right, but here's the drill.  Once you start working for me, I can't have you taking jobs from anyone else.  I demand complete focus from all my soldiers. One other thing.  If you finish this, I'll let Noldan know you will be ready for some ranged accuracy training.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && response.equals("s_764478e7"))
        {
            if (fs_patrol_quest_start_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_e0c9cc07");
                removeObjVar(player, "conversation.fs_patrol_quest_start.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'All right, but here's the drill.  Once you start working for me, I can't have you taking jobs from anyone else.  I demand complete focus from all my soldiers. One other thing.  If you finish this, I'll let Noldan know you will be ready for some ranged accuracy training.' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.fs_patrol_quest_start.branchId");
        return SCRIPT_CONTINUE;
    }
}
