package script.conversation;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.beast_lib;
import script.library.callable;
import script.library.chat;
import script.library.conversation;
import script.library.player_structure;
import script.library.tcg;
import script.library.utils;

public class barn_ranchhand extends script.base_script
{
    public barn_ranchhand()
    {
    }
    public static String c_stringFile = "conversation/barn_ranchhand";
    public boolean barn_ranchhand_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean barn_ranchhand_condition_isBuildingOwner(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id building = getTopMostContainer(npc);
        if (isIdValid(building))
        {
            obj_id owner = getOwner(building);
            if (isIdValid(owner) && owner == player)
            {
                return true;
            }
        }
        return false;
    }
    public boolean barn_ranchhand_condition_playerHasBeasts(obj_id player, obj_id npc) throws InterruptedException
    {
        if (beast_lib.getTotalBeastControlDevices(player) > 0)
        {
            return true;
        }
        return false;
    }
    public boolean barn_ranchhand_condition_ranchhandHasBeasts(obj_id player, obj_id npc) throws InterruptedException
    {
        if (tcg.getTotalBarnStoredBeastsFromRanchhand(npc) > 0)
        {
            return true;
        }
        return false;
    }
    public boolean barn_ranchhand_condition_noBeasts(obj_id player, obj_id npc) throws InterruptedException
    {
        if (beast_lib.getTotalBeastControlDevices(player) > 0 || tcg.getTotalBarnStoredBeastsFromRanchhand(npc) > 0)
        {
            return false;
        }
        return true;
    }
    public boolean barn_ranchhand_condition_barnIsFull(obj_id player, obj_id npc) throws InterruptedException
    {
        return tcg.getTotalBarnStoredBeastsFromRanchhand(npc) >= tcg.MAX_NUM_BARN_PETS;
    }
    public boolean barn_ranchhand_condition_playerIsFull(obj_id player, obj_id npc) throws InterruptedException
    {
        return callable.hasMaxStoredCombatPets(player);
    }
    public void barn_ranchhand_action_storeBeast(obj_id player, obj_id npc) throws InterruptedException
    {
        tcg.barnStoreBeastPrompt(player, npc);
        return;
    }
    public void barn_ranchhand_action_reclaimBeast(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id barn = getTopMostContainer(npc);
        if (isIdValid(barn))
        {
            tcg.barnReclaimBeastPrompt(player, barn, npc);
        }
        return;
    }
    public void barn_ranchhand_action_displayBeast(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id barn = getTopMostContainer(npc);
        if (isIdValid(barn))
        {
            tcg.barnDisplayBeastPrompt(player, barn, npc);
        }
        return;
    }
    public String barn_ranchhand_tokenTO_barnOwnerNamePossessive(obj_id player, obj_id npc) throws InterruptedException
    {
        String name = "the";
        obj_id building = getTopMostContainer(npc);
        if (isIdValid(building))
        {
            String buildingOwnerName = getStringObjVar(building, player_structure.VAR_OWNER);
            if (buildingOwnerName != null && buildingOwnerName.length() > 0)
            {
                name = "" + toUpper(buildingOwnerName, 0) + "'s";
            }
        }
        return name;
    }
    public String barn_ranchhand_tokenTO_barnOwnerName(obj_id player, obj_id npc) throws InterruptedException
    {
        String name = "Sir";
        if (getGender(player) == GENDER_FEMALE)
        {
            name = "Ma'am";
        }
        obj_id building = getTopMostContainer(npc);
        if (isIdValid(building))
        {
            String buildingOwnerName = getStringObjVar(building, player_structure.VAR_OWNER);
            if (buildingOwnerName != null && buildingOwnerName.length() > 0)
            {
                name = toUpper(buildingOwnerName, 0);
            }
        }
        return name;
    }
    public int barn_ranchhand_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6"))
        {
            if (barn_ranchhand_condition_barnIsFull(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_27");
                utils.removeScriptVar(player, "conversation.barn_ranchhand.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (barn_ranchhand_condition__defaultCondition(player, npc))
            {
                barn_ranchhand_action_storeBeast(player, npc);
                string_id message = new string_id(c_stringFile, "s_9");
                utils.removeScriptVar(player, "conversation.barn_ranchhand.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_11"))
        {
            if (barn_ranchhand_condition_playerIsFull(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_28");
                utils.removeScriptVar(player, "conversation.barn_ranchhand.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (barn_ranchhand_condition__defaultCondition(player, npc))
            {
                barn_ranchhand_action_reclaimBeast(player, npc);
                string_id message = new string_id(c_stringFile, "s_14");
                utils.removeScriptVar(player, "conversation.barn_ranchhand.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_19"))
        {
            if (barn_ranchhand_condition__defaultCondition(player, npc))
            {
                barn_ranchhand_action_displayBeast(player, npc);
                string_id message = new string_id(c_stringFile, "s_20");
                utils.removeScriptVar(player, "conversation.barn_ranchhand.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_23"))
        {
            if (barn_ranchhand_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_24");
                utils.removeScriptVar(player, "conversation.barn_ranchhand.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int barn_ranchhand_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_26"))
        {
            if (barn_ranchhand_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_30");
                utils.removeScriptVar(player, "conversation.barn_ranchhand.branchId");
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
            detachScript(self, "conversation.barn_ranchhand");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        if (!hasScript(self, tcg.BARN_RANCHHAND_SCRIPT))
        {
            attachScript(self, tcg.BARN_RANCHHAND_SCRIPT);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        if (!hasScript(self, tcg.BARN_RANCHHAND_SCRIPT))
        {
            attachScript(self, tcg.BARN_RANCHHAND_SCRIPT);
        }
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
        detachScript(self, "conversation.barn_ranchhand");
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
        if (barn_ranchhand_condition_isBuildingOwner(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_4");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (barn_ranchhand_condition_playerHasBeasts(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (barn_ranchhand_condition_ranchhandHasBeasts(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (barn_ranchhand_condition_ranchhandHasBeasts(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (barn_ranchhand_condition_noBeasts(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_11");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_19");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_23");
                }
                utils.setScriptVar(player, "conversation.barn_ranchhand.branchId", 1);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                pp.other.set(barn_ranchhand_tokenTO_barnOwnerName(player, npc));
                npcStartConversation(player, npc, "barn_ranchhand", null, pp, responses);
            }
            else 
            {
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                pp.other.set(barn_ranchhand_tokenTO_barnOwnerName(player, npc));
                chat.chat(npc, player, null, null, pp);
            }
            return SCRIPT_CONTINUE;
        }
        if (barn_ranchhand_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_22");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (barn_ranchhand_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_26");
                }
                utils.setScriptVar(player, "conversation.barn_ranchhand.branchId", 8);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                pp.other.set(barn_ranchhand_tokenTO_barnOwnerNamePossessive(player, npc));
                npcStartConversation(player, npc, "barn_ranchhand", null, pp, responses);
            }
            else 
            {
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                pp.other.set(barn_ranchhand_tokenTO_barnOwnerNamePossessive(player, npc));
                chat.chat(npc, player, null, null, pp);
            }
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("barn_ranchhand"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.barn_ranchhand.branchId");
        if (branchId == 1 && barn_ranchhand_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && barn_ranchhand_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.barn_ranchhand.branchId");
        return SCRIPT_CONTINUE;
    }
}
