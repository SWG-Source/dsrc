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
import script.library.groundquests;
import script.library.money;
import script.library.sui;
import script.library.township;
import script.library.transition;
import script.library.utils;

public class nexus_travel_away extends script.base_script
{
    public nexus_travel_away()
    {
    }
    public static String c_stringFile = "conversation/nexus_travel_away";
    public boolean nexus_travel_away_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean nexus_travel_away_condition_hasFoundAurilia(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "travel_found");
    }
    public boolean nexus_travel_away_condition_canAffordPay(obj_id player, obj_id npc) throws InterruptedException
    {
        return money.hasFunds(player, money.MT_TOTAL, township.TRAVEL_COST);
    }
    public void nexus_travel_away_action_travelFromAurilia(obj_id player, obj_id npc) throws InterruptedException
    {
        township.giveTravelListFromAurilia(player, npc);
    }
    public void nexus_travel_away_action_grantFoundAurilia(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "travel_found");
    }
    public int nexus_travel_away_tokenDI_travelCost(obj_id player, obj_id npc) throws InterruptedException
    {
        return township.TRAVEL_COST;
    }
    public int nexus_travel_away_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_17"))
        {
            if (nexus_travel_away_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_18");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (nexus_travel_away_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_20");
                    }
                    utils.setScriptVar(player, "conversation.nexus_travel_away.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.nexus_travel_away.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int nexus_travel_away_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_20"))
        {
            if (nexus_travel_away_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_26");
                utils.removeScriptVar(player, "conversation.nexus_travel_away.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int nexus_travel_away_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_12"))
        {
            if (nexus_travel_away_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_14");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (nexus_travel_away_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (nexus_travel_away_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_22");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_25");
                    }
                    utils.setScriptVar(player, "conversation.nexus_travel_away.branchId", 5);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = nexus_travel_away_tokenDI_travelCost(player, npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.nexus_travel_away.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = nexus_travel_away_tokenDI_travelCost(player, npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_30"))
        {
            if (nexus_travel_away_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_32");
                utils.removeScriptVar(player, "conversation.nexus_travel_away.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int nexus_travel_away_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_22"))
        {
            if (!nexus_travel_away_condition_canAffordPay(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_19");
                utils.removeScriptVar(player, "conversation.nexus_travel_away.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (nexus_travel_away_condition__defaultCondition(player, npc))
            {
                nexus_travel_away_action_travelFromAurilia(player, npc);
                string_id message = new string_id(c_stringFile, "s_24");
                utils.removeScriptVar(player, "conversation.nexus_travel_away.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_25"))
        {
            if (nexus_travel_away_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_28");
                utils.removeScriptVar(player, "conversation.nexus_travel_away.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int nexusTravelFromAurilliaSub(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        int pageId = params.getInt("pageId");
        if (!sui.hasPid(player, township.PID_VAR))
        {
            utils.removeScriptVarTree(player, "nexus.travel");
            forceCloseSUIPage(pageId);
            return SCRIPT_CONTINUE;
        }
        int pid = sui.getPid(player, township.PID_VAR);
        if (pageId != pid)
        {
            forceCloseSUIPage(pageId);
            forceCloseSUIPage(pid);
            sui.removePid(player, township.PID_VAR);
            utils.removeScriptVarTree(player, "nexus.travel");
            return SCRIPT_CONTINUE;
        }
        int btn = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        if (btn == sui.BP_CANCEL || idx < 0)
        {
            sui.removePid(player, township.PID_VAR);
            utils.removeScriptVarTree(player, "nexus.travel");
            return SCRIPT_CONTINUE;
        }
        String[] subGroups = utils.getStringArrayScriptVar(player, township.GROUPS_SCRIPT_VAR);
        if (btn == sui.BP_OK)
        {
            sui.removePid(player, township.PID_VAR);
            township.displayTravelListBySub(player, self, subGroups[idx]);
        }
        return SCRIPT_CONTINUE;
    }
    public int nexusTravelFromAurillia(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        int btn = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        int pageId = params.getInt("pageId");
        obj_id player = sui.getPlayerId(params);
        if (!sui.hasPid(player, township.PID_VAR))
        {
            utils.removeScriptVarTree(player, "nexus.travel");
            forceCloseSUIPage(pageId);
            return SCRIPT_CONTINUE;
        }
        int pid = sui.getPid(player, township.PID_VAR);
        if (pageId != pid)
        {
            forceCloseSUIPage(pageId);
            forceCloseSUIPage(pid);
            sui.removePid(player, township.PID_VAR);
            utils.removeScriptVarTree(player, "nexus.travel");
            return SCRIPT_CONTINUE;
        }
        if (btn == sui.BP_CANCEL)
        {
            sui.removePid(player, township.PID_VAR);
            utils.removeScriptVarTree(player, "nexus.travel");
            return SCRIPT_CONTINUE;
        }
        if (btn == sui.BP_REVERT)
        {
            township.giveTravelListFromAurilia(player, self);
            return SCRIPT_CONTINUE;
        }
        if (idx < 0)
        {
            sui.removePid(player, township.PID_VAR);
            utils.removeScriptVarTree(player, "nexus.travel");
            return SCRIPT_CONTINUE;
        }
        location npcLoc = utils.getLocationScriptVar(player, "nexus.travel.npc_loc");
        location playerLoc = getLocation(player);
        String[] instances = utils.getStringArrayScriptVar(player, "nexus.travel.instances");
        if (btn == sui.BP_OK)
        {
            if (!npcLoc.area.equals(playerLoc.area))
            {
                sendSystemMessage(self, new string_id("nexus", "travel_too_far"));
                utils.removeScriptVarTree(player, "nexus.travel");
                sui.removePid(player, township.PID_VAR);
                return SCRIPT_CONTINUE;
            }
            if (getDistance(npcLoc, playerLoc) < 10)
            {
                dictionary d = new dictionary();
                d.put("price", township.TRAVEL_COST);
                d.put("player", player);
                d.put("npc", self);
                if (transition.hasPermissionForZone(player, instances[idx], "initialRequiredFlag"))
                {
                    if (money.requestPayment(player, "aurillia_travel", township.TRAVEL_COST, "handleTransaction", d, true))
                    {
                        transition.zonePlayerNoGate(instances[idx], player, true);
                    }
                }
                else 
                {
                    transition.notifyPlayerOfInvalidPermission(player, instances[idx]);
                }
            }
            else 
            {
                sendSystemMessage(self, new string_id("nexus", "travel_too_far"));
            }
            utils.removeScriptVarTree(player, "nexus.travel");
        }
        utils.removeScriptVarTree(player, "nexus.travel");
        sui.removePid(player, township.PID_VAR);
        return SCRIPT_CONTINUE;
    }
    public int handleTransaction(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId("player");
        int price = params.getInt("price");
        if (!isIdValid(player) || price < 1)
        {
            return SCRIPT_CONTINUE;
        }
        if (params.getInt(money.DICT_CODE) == money.RET_FAIL)
        {
            chat.chat(self, player, new string_id("spam", "no_money"));
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isTangible(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.nexus_travel_away");
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
        detachScript(self, "conversation.nexus_travel_away");
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
        if (!nexus_travel_away_condition_hasFoundAurilia(player, npc))
        {
            nexus_travel_away_action_grantFoundAurilia(player, npc);
            string_id message = new string_id(c_stringFile, "s_15");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (nexus_travel_away_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_17");
                }
                utils.setScriptVar(player, "conversation.nexus_travel_away.branchId", 1);
                npcStartConversation(player, npc, "nexus_travel_away", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (nexus_travel_away_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_10");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (nexus_travel_away_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (nexus_travel_away_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_12");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_30");
                }
                utils.setScriptVar(player, "conversation.nexus_travel_away.branchId", 4);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "nexus_travel_away", null, pp, responses);
            }
            else 
            {
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                chat.chat(npc, player, null, null, pp);
            }
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("nexus_travel_away"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.nexus_travel_away.branchId");
        if (branchId == 1 && nexus_travel_away_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && nexus_travel_away_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && nexus_travel_away_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && nexus_travel_away_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.nexus_travel_away.branchId");
        return SCRIPT_CONTINUE;
    }
}
