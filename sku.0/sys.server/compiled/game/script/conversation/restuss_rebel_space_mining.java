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
import script.library.group;
import script.library.resource;
import script.library.restuss_event;
import script.library.space_quest;
import script.library.trial;
import script.library.utils;

public class restuss_rebel_space_mining extends script.base_script
{
    public restuss_rebel_space_mining()
    {
    }
    public static String c_stringFile = "conversation/restuss_rebel_space_mining";
    public boolean restuss_rebel_space_mining_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean restuss_rebel_space_mining_condition_completedSpaceMine1(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id[] boxes = utils.getAllItemsPlayerHasByTemplate(player, "object/resource_container/space/metal.iff");
        LOG("RESTUSS_SPACE_MINING", "Box obj_id is " + boxes);
        if (boxes == null || boxes.length < 1)
        {
            LOG("RESTUSS_SPACE_MINING", "Box is null");
            return false;
        }
        obj_id currentBox = null;
        obj_id currentBoxResource = null;
        String currentBoxResourceName = "";
        int currentBoxCount = 0;
        for (int i = 0; i < boxes.length; i++)
        {
            currentBox = boxes[i];
            currentBoxResource = getResourceContainerResourceType(currentBox);
            LOG("RESTUSS_SPACE_MINING", "Resource Type is " + currentBoxResource);
            currentBoxResourceName = getResourceName(currentBoxResource);
            LOG("RESTUSS_SPACE_MINING", "Resource Name is " + currentBoxResourceName);
            if (currentBoxResourceName.indexOf("space_metal_obsidian") > -1 || currentBoxResourceName.indexOf("Obsidian Asteroid") > -1)
            {
                currentBoxCount = getResourceContainerQuantity(currentBox);
                LOG("RESTUSS_SPACE_MINING", "Current box count is " + currentBoxCount);
                if (currentBoxCount >= 500)
                {
                    if (groundquests.isTaskActive(player, "restuss_rebel_space_mining_1", "returnRomer"))
                    {
                        LOG("RESTUSS_SPACE_MINING", "Everything worked correctly");
                        return true;
                    }
                    LOG("RESTUSS_SPACE_MINING", "Quest task is not active");
                }
                LOG("RESTUSS_SPACE_MINING", "Current box count < 500");
            }
            LOG("RESTUSS_SPACE_MINING", "Resource Name !end with space_metal_obsidian");
        }
        LOG("RESTUSS_SPACE_MINING", "I fell all the way through");
        return false;
    }
    public boolean restuss_rebel_space_mining_condition_spaceMineActive1(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "restuss_rebel_space_mining_1");
    }
    public boolean restuss_rebel_space_mining_condition_completedSpaceMine2(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id[] boxes = utils.getAllItemsPlayerHasByTemplate(player, "object/resource_container/space/gas.iff");
        if (boxes == null || boxes.length < 1)
        {
            return false;
        }
        obj_id currentBox = null;
        obj_id currentBoxResource = null;
        String currentBoxResourceName = "";
        int currentBoxCount = 0;
        for (int i = 0; i < boxes.length; i++)
        {
            currentBox = boxes[i];
            currentBoxResource = getResourceContainerResourceType(currentBox);
            currentBoxResourceName = getResourceName(currentBoxResource);
            LOG("RESTUSS_SPACE_MINING", "Resource Name is " + currentBoxResourceName);
            if (currentBoxResourceName.indexOf("space_gas_organometallic") > -1 || currentBoxResourceName.indexOf("Organometallic Asteroid") > -1)
            {
                currentBoxCount = getResourceContainerQuantity(currentBox);
                if (currentBoxCount >= 250)
                {
                    if (groundquests.isTaskActive(player, "restuss_rebel_space_mining_2", "returnRomer2"))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean restuss_rebel_space_mining_condition_spaceMineActive2(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "restuss_rebel_space_mining_2");
    }
    public boolean restuss_rebel_space_mining_condition_inPhase2(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id object = trial.getParent(npc);
        if (factions.isRebel(player) && (restuss_event.getPhase(object) == 2))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean restuss_rebel_space_mining_condition_inPhase1(obj_id player, obj_id npc) throws InterruptedException
    {
        if (factions.isRebel(player))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean restuss_rebel_space_mining_condition_enoughOre(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id object = trial.getParent(npc);
        if (factions.isRebel(player) && (restuss_event.getPhase(object) == 3))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean restuss_rebel_space_mining_condition_isImperialPlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        return factions.isImperial(player);
    }
    public void restuss_rebel_space_mining_action_giveSpaceMine1(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id mobj = space_quest._getQuest(player, "space_mining_destroy", "restuss_rebel_mining_1");
        if (mobj != null) space_quest.setSilentQuestAborted(player, mobj);
        groundquests.clearQuest(player, "restuss_rebel_space_mining_1");
        groundquests.grantQuest(player, "restuss_rebel_space_mining_1");
    }
    public void restuss_rebel_space_mining_action_signalDone(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id[] boxes = utils.getAllItemsPlayerHasByTemplate(player, "object/resource_container/space/metal.iff");
        obj_id currentBox = null;
        obj_id currentBoxResource = null;
        String currentBoxResourceName = "";
        int currentBoxCount = 0;
        for (int i = 0; i < boxes.length; i++)
        {
            currentBox = boxes[i];
            currentBoxResource = getResourceContainerResourceType(currentBox);
            currentBoxResourceName = getResourceName(currentBoxResource);
            if (currentBoxResourceName.indexOf("space_metal_obsidian") > -1 || currentBoxResourceName.indexOf("Obsidian Asteroid") > -1)
            {
                currentBoxCount = getResourceContainerQuantity(currentBox);
                if (currentBoxCount >= 500)
                {
                    removeResourceFromContainer(currentBox, currentBoxResource, 500);
                    break;
                }
            }
        }
        groundquests.sendSignal(player, "returnedRomer");
        if (group.isGrouped(player))
        {
            Vector members = group.getPCMembersInRange(player, 35f);
            if (members != null && members.size() > 0)
            {
                int numInGroup = members.size();
                for (int i = 0; i < numInGroup; i++)
                {
                    obj_id thisMember = ((obj_id)members.get(i));
                    groundquests.sendSignal(thisMember, "returnedRomer");
                }
            }
        }
        restuss_event.incrimentCompletedQuestCount(npc, "restuss_rebel_space_mining_1");
        int phase = restuss_event.getPhase(npc);
        if (phase == 0)
        {
            float ratio = restuss_event.getCompletedQuestRatio(npc, "restuss_rebel_space_mining_1");
            if (ratio > .5)
            {
                restuss_event.incrimentPhase(npc);
                return;
            }
        }
        if (phase == 1)
        {
            if (restuss_event.isRequiredCountMet(npc, "restuss_rebel_space_mining_1"))
            {
                restuss_event.incrimentPhase(npc);
                return;
            }
        }
    }
    public void restuss_rebel_space_mining_action_giveSpaceMine2(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id mobj = space_quest._getQuest(player, "space_mining_destroy", "restuss_rebel_mining_2");
        if (mobj != null) space_quest.setSilentQuestAborted(player, mobj);
        groundquests.clearQuest(player, "restuss_rebel_space_mining_2");
        groundquests.grantQuest(player, "restuss_rebel_space_mining_2");
    }
    public void restuss_rebel_space_mining_action_signalDone2(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id[] boxes = utils.getAllItemsPlayerHasByTemplate(player, "object/resource_container/space/gas.iff");
        obj_id currentBox = null;
        obj_id currentBoxResource = null;
        String currentBoxResourceName = "";
        int currentBoxCount = 0;
        for (int i = 0; i < boxes.length; i++)
        {
            currentBox = boxes[i];
            currentBoxResource = getResourceContainerResourceType(currentBox);
            currentBoxResourceName = getResourceName(currentBoxResource);
            if (currentBoxResourceName.indexOf("space_gas_organometallic") > -1 || currentBoxResourceName.indexOf("Organometallic Asteroid") > -1)
            {
                currentBoxCount = getResourceContainerQuantity(currentBox);
                if (currentBoxCount >= 250)
                {
                    removeResourceFromContainer(currentBox, currentBoxResource, 250);
                    break;
                }
            }
        }
        groundquests.sendSignal(player, "returnedRomer2");
        if (group.isGrouped(player))
        {
            Vector members = group.getPCMembersInRange(player, 35f);
            if (members != null && members.size() > 0)
            {
                int numInGroup = members.size();
                for (int i = 0; i < numInGroup; i++)
                {
                    obj_id thisMember = ((obj_id)members.get(i));
                    groundquests.sendSignal(thisMember, "returnedRomer2");
                }
            }
        }
        restuss_event.incrimentCompletedQuestCount(npc, "restuss_rebel_space_mining_2");
        int phase = restuss_event.getPhase(npc);
        if (phase == 2)
        {
            if (restuss_event.isRequiredCountMet(npc, "restuss_rebel_space_mining_2"))
            {
                restuss_event.incrimentPhase(npc);
                return;
            }
        }
    }
    public void restuss_rebel_space_mining_action_eject(obj_id player, obj_id npc) throws InterruptedException
    {
        expelFromBuilding(player);
    }
    public int restuss_rebel_space_mining_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_38"))
        {
            doAnimationAction(player, "nod");
            if (restuss_rebel_space_mining_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute1");
                doAnimationAction(player, "salute1");
                restuss_rebel_space_mining_action_giveSpaceMine2(player, npc);
                string_id message = new string_id(c_stringFile, "s_40");
                utils.removeScriptVar(player, "conversation.restuss_rebel_space_mining.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_42"))
        {
            doAnimationAction(player, "shake_head_no");
            if (restuss_rebel_space_mining_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute1");
                doAnimationAction(player, "salute1");
                string_id message = new string_id(c_stringFile, "s_44");
                utils.removeScriptVar(player, "conversation.restuss_rebel_space_mining.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int restuss_rebel_space_mining_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_20"))
        {
            doAnimationAction(player, "nod");
            if (restuss_rebel_space_mining_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute1");
                doAnimationAction(player, "salute1");
                restuss_rebel_space_mining_action_giveSpaceMine2(player, npc);
                string_id message = new string_id(c_stringFile, "s_22");
                utils.removeScriptVar(player, "conversation.restuss_rebel_space_mining.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_24"))
        {
            doAnimationAction(player, "shake_head_no");
            if (restuss_rebel_space_mining_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute1");
                doAnimationAction(player, "salute1");
                string_id message = new string_id(c_stringFile, "s_26");
                utils.removeScriptVar(player, "conversation.restuss_rebel_space_mining.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int restuss_rebel_space_mining_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_29"))
        {
            doAnimationAction(player, "nod");
            restuss_rebel_space_mining_action_giveSpaceMine1(player, npc);
            if (restuss_rebel_space_mining_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute1");
                doAnimationAction(player, "salute1");
                string_id message = new string_id(c_stringFile, "s_31");
                utils.removeScriptVar(player, "conversation.restuss_rebel_space_mining.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_33"))
        {
            doAnimationAction(player, "shake_head_no");
            if (restuss_rebel_space_mining_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute1");
                doAnimationAction(player, "salute1");
                string_id message = new string_id(c_stringFile, "s_35");
                utils.removeScriptVar(player, "conversation.restuss_rebel_space_mining.branchId");
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
            detachScript(self, "conversation.restuss_rebel_space_mining");
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
        detachScript(self, "conversation.restuss_rebel_space_mining");
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
        if (restuss_rebel_space_mining_condition_isImperialPlayer(player, npc))
        {
            restuss_rebel_space_mining_action_eject(player, npc);
            string_id message = new string_id(c_stringFile, "s_41");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (restuss_rebel_space_mining_condition_completedSpaceMine2(player, npc))
        {
            doAnimationAction(npc, "salute1");
            doAnimationAction(player, "salute1");
            restuss_rebel_space_mining_action_signalDone2(player, npc);
            string_id message = new string_id(c_stringFile, "s_13");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (restuss_rebel_space_mining_condition_completedSpaceMine1(player, npc))
        {
            doAnimationAction(npc, "salute1");
            doAnimationAction(player, "salute1");
            restuss_rebel_space_mining_action_signalDone(player, npc);
            string_id message = new string_id(c_stringFile, "s_6");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (restuss_rebel_space_mining_condition_spaceMineActive2(player, npc))
        {
            doAnimationAction(npc, "salute1");
            doAnimationAction(player, "salute1");
            string_id message = new string_id(c_stringFile, "s_11");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (restuss_rebel_space_mining_condition_spaceMineActive1(player, npc))
        {
            doAnimationAction(npc, "salute1");
            doAnimationAction(player, "salute1");
            string_id message = new string_id(c_stringFile, "s_9");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (restuss_rebel_space_mining_condition_enoughOre(player, npc))
        {
            doAnimationAction(npc, "salute1");
            doAnimationAction(player, "salute1");
            string_id message = new string_id(c_stringFile, "s_36");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (restuss_rebel_space_mining_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (restuss_rebel_space_mining_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.restuss_rebel_space_mining.branchId", 6);
                npcStartConversation(player, npc, "restuss_rebel_space_mining", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (restuss_rebel_space_mining_condition_inPhase2(player, npc))
        {
            doAnimationAction(npc, "salute1");
            doAnimationAction(player, "salute1");
            string_id message = new string_id(c_stringFile, "s_17");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (restuss_rebel_space_mining_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (restuss_rebel_space_mining_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_20");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_24");
                }
                utils.setScriptVar(player, "conversation.restuss_rebel_space_mining.branchId", 9);
                npcStartConversation(player, npc, "restuss_rebel_space_mining", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (restuss_rebel_space_mining_condition_inPhase1(player, npc))
        {
            doAnimationAction(npc, "salute1");
            doAnimationAction(player, "salute1");
            string_id message = new string_id(c_stringFile, "s_27");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (restuss_rebel_space_mining_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (restuss_rebel_space_mining_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_29");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_33");
                }
                utils.setScriptVar(player, "conversation.restuss_rebel_space_mining.branchId", 12);
                npcStartConversation(player, npc, "restuss_rebel_space_mining", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (restuss_rebel_space_mining_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_39");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("restuss_rebel_space_mining"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.restuss_rebel_space_mining.branchId");
        if (branchId == 6 && restuss_rebel_space_mining_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && restuss_rebel_space_mining_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && restuss_rebel_space_mining_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.restuss_rebel_space_mining.branchId");
        return SCRIPT_CONTINUE;
    }
}
