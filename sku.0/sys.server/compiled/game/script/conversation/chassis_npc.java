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
import script.library.money;
import script.library.space_crafting;
import script.library.space_transition;
import script.library.sui;
import script.library.travel;
import script.library.utils;

public class chassis_npc extends script.base_script
{
    public chassis_npc()
    {
    }
    public static String c_stringFile = "conversation/chassis_npc";
    public boolean chassis_npc_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean chassis_npc_condition_hasLoot(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_crafting.hasLootToSell(player);
    }
    public void chassis_npc_action_showComponentList(obj_id player, obj_id npc) throws InterruptedException
    {
        space_crafting.purchaseChassisFromBroker(player, npc);
    }
    public int handleCheckShip(obj_id self, dictionary params) throws InterruptedException
    {
        final String TBL = "datatables/space_crafting/chassis_npc.iff";
        final String STF = "chassis_npc";
        obj_id player = sui.getPlayerId(params);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        int idx = sui.getListboxSelectedRow(params);
        int bp = sui.getIntButtonPressed(params);
        utils.setScriptVar(player, "chassis_npc.row", idx);
        utils.setScriptVar(player, "chassis_npc.bp", bp);
        if (idx == -1 || bp == -1)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] deeds = utils.getObjIdArrayScriptVar(player, "chassis_npc.deed");
        if (deeds == null || deeds.length <= 0)
        {
            return SCRIPT_CONTINUE;
        }
        String type = getStringObjVar(deeds[idx], "shiptype");
        String skill = dataTableGetString(TBL, type, 3);
        string_id msg = new string_id(STF, "can_use");
        if (!hasSkill(player, skill))
        {
            msg = new string_id(STF, "cannot_use");
        }
        if (type.equals("firespray"))
        {
            if (hasSkill(player, "pilot_rebel_navy_master") || hasSkill(player, "pilot_imperial_navy_master") || hasSkill(player, "pilot_neutral_master"))
            {
                msg = new string_id(STF, "can_use");
            }
        }
        String message = utils.packStringId(msg);
        string_id headerId = new string_id(STF, "confirm_transaction");
        String header = utils.packStringId(headerId);
        int pid = sui.msgbox(self, player, message, 2, header, 0, "handleBuyShip");
        if (pid <= -1)
        {
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int handleBuyShip(obj_id self, dictionary params) throws InterruptedException
    {
        final String TBL = "datatables/space_crafting/chassis_npc.iff";
        final String STF = "chassis_npc";
        obj_id player = sui.getPlayerId(params);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        int button = sui.getIntButtonPressed(params);
        if (button == 1)
        {
            return SCRIPT_CONTINUE;
        }
        int idx = utils.getIntScriptVar(player, "chassis_npc.row");
        int bp = utils.getIntScriptVar(player, "chassis_npc.bp");
        utils.removeScriptVar(player, "chassis_npc.row");
        utils.removeScriptVar(player, "chassis_npc.bp");
        if (idx == -1 || bp == -1)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id inv = utils.getInventoryContainer(player);
        if (!isIdValid(inv))
        {
            return SCRIPT_CONTINUE;
        }
        int[] prices = utils.getIntArrayScriptVar(player, "chassis_npc.price");
        obj_id[] deeds = utils.getObjIdArrayScriptVar(player, "chassis_npc.deed");
        if (deeds == null || deeds.length <= 0)
        {
            return SCRIPT_CONTINUE;
        }
        String type = getStringObjVar(deeds[idx], "shiptype");
        obj_id datapad = utils.getDatapad(player);
        if (!isIdValid(datapad))
        {
            return SCRIPT_CONTINUE;
        }
        int price = dataTableGetInt(TBL, type, 1);
        obj_id deed = deeds[idx];
        int playerCash = getTotalMoney(player);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        else 
        {
            if (utils.getContainingPlayer(deed) == player)
            {
                float mass = getFloatObjVar(deed, "ship_chassis.mass");
                float hp = getFloatObjVar(deed, "ship_chassis.hp");
                obj_id pcd = space_crafting.createDeedFromBlueprints(player, type, inv, mass, hp);
                if (isIdValid(pcd))
                {
                    dictionary d = new dictionary();
                    d.put("player", player);
                    d.put("deed", pcd);
                    d.put("price", price);
                    d.put("blueprints", deed);
                    money.requestPayment(player, self, price, "handleChassisTransaction", d, false);
                }
                else 
                {
                    string_id msgInvFull = new string_id(STF, "inv_full");
                    chat.chat(self, player, msgInvFull);
                    destroyObject(pcd);
                    return SCRIPT_CONTINUE;
                }
                cleanUpScriptVars(player);
            }
            else 
            {
                string_id msgNotInInv = new string_id(STF, "not_in_inv");
                chat.chat(self, player, msgNotInInv);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleChassisTransaction(obj_id self, dictionary params) throws InterruptedException
    {
        final String STF = "chassis_npc";
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId("player");
        obj_id deed = params.getObjId("deed");
        int price = params.getInt("price");
        obj_id blueprints = params.getObjId("blueprints");
        if (!isIdValid(player) || !isIdValid(deed) || price < 1)
        {
            return SCRIPT_CONTINUE;
        }
        if (params.getInt(money.DICT_CODE) == money.RET_FAIL)
        {
            chat.chat(self, player, new string_id(STF, "no_money"));
            destroyObject(deed);
            return SCRIPT_CONTINUE;
        }
        String strParkingLocation = travel.getTravelPointName(getTopMostContainer(self));
        if (strParkingLocation != null)
        {
            setObjVar(deed, "strParkingLocation", strParkingLocation);
        }
        else 
        {
            setName(deed, "This pcd was made in a starport without a landing location");
        }
        string_id msgBoughtChassis = new string_id(STF, "bought_chassis");
        chat.chat(self, player, msgBoughtChassis);
        CustomerServiceLog("chassis_broker", "%TU CHANGED BLUEPRINT " + blueprints + " (" + getTemplateName(blueprints) + ") " + " INTO DEED " + deed + " (" + getTemplateName(deed) + ") " + " FOR " + price + " CREDITS AT THIS BROKER: " + self, player);
        playMusic(player, "sound/music_acq_academic.snd");
        destroyObject(blueprints);
        return SCRIPT_CONTINUE;
    }
    public void cleanUpScriptVars(obj_id player) throws InterruptedException
    {
        final String SCRIPTVAR_CHASSIS_SUI = "chassis_npc.sui";
        utils.removeScriptVar(player, SCRIPTVAR_CHASSIS_SUI);
        utils.removeScriptVar(player, "chassis_npc.price");
        utils.removeBatchScriptVar(player, "chassis_npc.type");
        utils.removeScriptVar(player, "chassis_npc.deed");
    }
    public void chassis_npc_action_sellComponents(obj_id player, obj_id npc) throws InterruptedException
    {
        space_crafting.sellComponentsToBroker(player, npc);
    }
    public int sellLootComponents(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id objPlayer = sui.getPlayerId(params);
        if (!isIdValid(objPlayer))
        {
            LOG("space", "Invalid player");
            return SCRIPT_CONTINUE;
        }
        int idx = sui.getListboxSelectedRow(params);
        int bp = sui.getIntButtonPressed(params);
        if (idx == -1 || bp == -1)
        {
            LOG("space", "Invalid index");
            return SCRIPT_CONTINUE;
        }
        else if (bp != sui.BP_OK)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] objLootToSell = utils.getObjIdArrayScriptVar(objPlayer, "objLootToSell");
        if (objLootToSell == null || objLootToSell.length <= 0)
        {
            LOG("space", "No loot");
            return SCRIPT_CONTINUE;
        }
        if (idx > objLootToSell.length - 1)
        {
            LOG("space", "really bad index");
            return SCRIPT_CONTINUE;
        }
        obj_id objItem = objLootToSell[idx];
        if (space_crafting.sellLootItem(objPlayer, self, objItem))
        {
            space_crafting.sellComponentsToBroker(objPlayer, self);
        }
        return SCRIPT_CONTINUE;
    }
    public String chassis_npc_tokenTO_tokenTO0001(obj_id player, obj_id npc) throws InterruptedException
    {
        return new String();
    }
    public int chassis_npc_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_94e3013f"))
        {
            if (chassis_npc_condition_hasLoot(player, npc))
            {
                chassis_npc_action_sellComponents(player, npc);
                string_id message = new string_id(c_stringFile, "s_aae81853");
                utils.removeScriptVar(player, "conversation.chassis_npc.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (chassis_npc_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3310c404");
                utils.removeScriptVar(player, "conversation.chassis_npc.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_2f553ea8"))
        {
            if (chassis_npc_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                chassis_npc_action_showComponentList(player, npc);
                string_id message = new string_id(c_stringFile, "s_bcb592f");
                utils.removeScriptVar(player, "conversation.chassis_npc.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_93a92e8"))
        {
            if (chassis_npc_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_37386aa2");
                utils.removeScriptVar(player, "conversation.chassis_npc.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_42d3759c"))
        {
            if (chassis_npc_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_shoulders");
                string_id message = new string_id(c_stringFile, "s_37f48153");
                utils.removeScriptVar(player, "conversation.chassis_npc.branchId");
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
            detachScript(self, "conversation.chassis_npc");
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
        if (!hasObjVar(self, "chassis_broker.alreadyHasName"))
        {
            string_id name = new string_id("chassis_npc", "npc_name");
            setName(self, name);
        }
        setInvulnerable(self, true);
        faceTo(self, player);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        detachScript(self, "conversation.chassis_npc");
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
        if (chassis_npc_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "wave1");
            string_id message = new string_id(c_stringFile, "s_9ed93871");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (chassis_npc_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (chassis_npc_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (chassis_npc_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (chassis_npc_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_94e3013f");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_2f553ea8");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_93a92e8");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_42d3759c");
                }
                utils.setScriptVar(player, "conversation.chassis_npc.branchId", 1);
                npcStartConversation(player, npc, "chassis_npc", message, responses);
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
        if (!conversationId.equals("chassis_npc"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.chassis_npc.branchId");
        if (branchId == 1 && chassis_npc_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.chassis_npc.branchId");
        return SCRIPT_CONTINUE;
    }
}
