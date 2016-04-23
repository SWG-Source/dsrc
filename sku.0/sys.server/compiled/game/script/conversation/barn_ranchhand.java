package script.conversation;

import script.library.*;
import script.*;

public class barn_ranchhand extends script.conversation.base.conversation_base
{
    public String conversation = "conversation.barn_ranchhand";
    public String c_stringFile = "conversation/barn_ranchhand";
    public barn_ranchhand()
    {
        super.scriptName = "barn_ranchhand";
        super.conversation = conversation;
        super.c_stringFile = c_stringFile;
    }
    private boolean barn_ranchhand_condition_isBuildingOwner(obj_id player, obj_id npc) throws InterruptedException
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
    private boolean barn_ranchhand_condition_playerHasBeasts(obj_id player) throws InterruptedException
    {
        return beast_lib.getTotalBeastControlDevices(player) > 0;
    }
    private boolean barn_ranchhand_condition_ranchhandHasBeasts(obj_id npc) throws InterruptedException
    {
        return tcg.getTotalBarnStoredBeastsFromRanchhand(npc) > 0;
    }
    private boolean barn_ranchhand_condition_noBeasts(obj_id player, obj_id npc) throws InterruptedException
    {
        return !(beast_lib.getTotalBeastControlDevices(player) > 0 || tcg.getTotalBarnStoredBeastsFromRanchhand(npc) > 0);
    }
    private boolean barn_ranchhand_condition_barnIsFull(obj_id npc) throws InterruptedException
    {
        return tcg.getTotalBarnStoredBeastsFromRanchhand(npc) >= tcg.MAX_NUM_BARN_PETS;
    }
    private boolean barn_ranchhand_condition_playerIsFull(obj_id player) throws InterruptedException
    {
        return callable.hasMaxStoredCombatPets(player);
    }
    private void barn_ranchhand_action_storeBeast(obj_id player, obj_id npc) throws InterruptedException
    {
        tcg.barnStoreBeastPrompt(player, npc);
    }
    private void barn_ranchhand_action_reclaimBeast(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id barn = getTopMostContainer(npc);
        if (isIdValid(barn))
        {
            tcg.barnReclaimBeastPrompt(player, barn, npc);
        }
    }
    private void barn_ranchhand_action_displayBeast(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id barn = getTopMostContainer(npc);
        if (isIdValid(barn))
        {
            tcg.barnDisplayBeastPrompt(player, barn, npc);
        }
    }
    private String barn_ranchhand_tokenTO_barnOwnerName(obj_id player, obj_id npc) throws InterruptedException
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
    private int barn_ranchhand_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6"))
        {
            if (barn_ranchhand_condition_barnIsFull(npc))
            {
                utils.removeScriptVar(player, "conversation.barn_ranchhand.branchId");
                npcEndConversationWithMessage(player, new string_id(c_stringFile, "s_27"));
                return SCRIPT_CONTINUE;
            }
            barn_ranchhand_action_storeBeast(player, npc);
            utils.removeScriptVar(player, "conversation.barn_ranchhand.branchId");
            npcEndConversationWithMessage(player, new string_id(c_stringFile, "s_9"));
            return SCRIPT_CONTINUE;
        }
        if (response.equals("s_11"))
        {
            if (barn_ranchhand_condition_playerIsFull(player))
            {
                utils.removeScriptVar(player, "conversation.barn_ranchhand.branchId");
                npcEndConversationWithMessage(player, new string_id(c_stringFile, "s_28"));
                return SCRIPT_CONTINUE;
            }
            barn_ranchhand_action_reclaimBeast(player, npc);
            utils.removeScriptVar(player, "conversation.barn_ranchhand.branchId");
            npcEndConversationWithMessage(player, new string_id(c_stringFile, "s_14"));
            return SCRIPT_CONTINUE;
        }
        if (response.equals("s_19"))
        {
            barn_ranchhand_action_displayBeast(player, npc);
            utils.removeScriptVar(player, "conversation.barn_ranchhand.branchId");
            npcEndConversationWithMessage(player, new string_id(c_stringFile, "s_20"));
            return SCRIPT_CONTINUE;
        }
        if (response.equals("s_23"))
        {
            utils.removeScriptVar(player, "conversation.barn_ranchhand.branchId");
            npcEndConversationWithMessage(player, new string_id(c_stringFile, "s_24"));
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_DEFAULT;
    }
    private int barn_ranchhand_handleBranch8(obj_id player, string_id response) throws InterruptedException
    {
        if (response.equals("s_26"))
        {
            utils.removeScriptVar(player, "conversation.barn_ranchhand.branchId");
            npcEndConversationWithMessage(player, new string_id(c_stringFile, "s_30"));
            return SCRIPT_CONTINUE;
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
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (barn_ranchhand_condition_isBuildingOwner(player, self))
        {
            if (barn_ranchhand_condition_playerHasBeasts(player))
            {
                return craft_response_prose(new String[] {"s_4", "s_6"}, 1, player, self, barn_ranchhand_tokenTO_barnOwnerName(player, self));
            }
            if (barn_ranchhand_condition_ranchhandHasBeasts(self))
            {
                return craft_response_prose(new String[] {"s_4", "s_11", "s_19"}, 1, player, self, barn_ranchhand_tokenTO_barnOwnerName(player, self));
            }
            if (barn_ranchhand_condition_noBeasts(player, self))
            {
                return craft_response_prose(new String[] {"s_4", "s_23"}, 1, player, self, barn_ranchhand_tokenTO_barnOwnerName(player, self));
            }
        }
        return craft_response_prose(new String[] {"s_22", "s_26"}, 8, player, self, barn_ranchhand_tokenTO_barnOwnerName(player, self));
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("barn_ranchhand"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = utils.getIntScriptVar(player, "conversation.barn_ranchhand.branchId");
        if (branchId == 1 && barn_ranchhand_handleBranch1(player, self, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && barn_ranchhand_handleBranch8(player, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.barn_ranchhand.branchId");
        return SCRIPT_CONTINUE;
    }
}
