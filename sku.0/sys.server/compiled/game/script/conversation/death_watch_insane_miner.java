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

public class death_watch_insane_miner extends script.base_script
{
    public death_watch_insane_miner()
    {
    }
    public static String c_stringFile = "conversation/death_watch_insane_miner";
    public boolean death_watch_insane_miner_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean death_watch_insane_miner_condition_startMission(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "death_watch.drill_01") && !hasObjVar(player, "death_watch.medicine"))
        {
            return true;
        }
        return false;
    }
    public boolean death_watch_insane_miner_condition_checkMedicine(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id objInventory = utils.getInventoryContainer(player);
        if (isIdValid(objInventory))
        {
            obj_id[] objContents = utils.getContents(objInventory);
            if (objContents != null)
            {
                for (int intI = 0; intI < objContents.length; intI++)
                {
                    String strItemTemplate = getTemplateName(objContents[intI]);
                    if (strItemTemplate.equals("object/tangible/dungeon/death_watch_bunker/crazed_miner_medicine.iff"))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean death_watch_insane_miner_condition_alreadyAccepted(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "death_watch.medicine"))
        {
            return true;
        }
        return false;
    }
    public boolean death_watch_insane_miner_condition_noMedicine(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id objInventory = utils.getInventoryContainer(player);
        if (isIdValid(objInventory))
        {
            obj_id[] objContents = utils.getContents(objInventory);
            if (objContents != null)
            {
                for (int intI = 0; intI < objContents.length; intI++)
                {
                    String strItemTemplate = getTemplateName(objContents[intI]);
                    if (strItemTemplate.equals("object/tangible/dungeon/death_watch_bunker/crazed_miner_medicine.iff"))
                    {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    public boolean death_watch_insane_miner_condition_noQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasObjVar(player, "death_watch.drill_01") && !hasObjVar(player, "death_watch.medicine"))
        {
            return true;
        }
        return false;
    }
    public void death_watch_insane_miner_action_getMedicine(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "death_watch.medicine", 1);
        messageTo(npc, "handleCrazedCleanup", null, 1800f, false);
        return;
    }
    public void death_watch_insane_miner_action_removeMedicine(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id objInventory = utils.getInventoryContainer(player);
        if (isIdValid(objInventory))
        {
            obj_id[] objContents = utils.getContents(objInventory);
            if (objContents != null)
            {
                for (int intI = 0; intI < objContents.length; intI++)
                {
                    String strItemTemplate = getTemplateName(objContents[intI]);
                    if (strItemTemplate.equals("object/tangible/dungeon/death_watch_bunker/crazed_miner_medicine.iff"))
                    {
                        destroyObject(objContents[intI]);
                        return;
                    }
                }
            }
        }
        return;
    }
    public void death_watch_insane_miner_action_giveBattery(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id objInventory = utils.getInventoryContainer(player);
        if (isIdValid(objInventory))
        {
            obj_id[] objContents = utils.getContents(objInventory);
            if (objContents != null)
            {
                for (int intI = 0; intI < objContents.length; intI++)
                {
                    String strItemTemplate = getTemplateName(objContents[intI]);
                    if (strItemTemplate.equals("object/tangible/dungeon/death_watch_bunker/crazed_miner_medicine.iff"))
                    {
                        destroyObject(objContents[intI]);
                    }
                }
            }
        }
        if (isIdValid(player))
        {
            obj_id playerInv = getObjectInSlot(player, "inventory");
            if (isIdValid(playerInv))
            {
                obj_id item = createObject("object/tangible/dungeon/death_watch_bunker/drill_battery.iff", playerInv, "");
                if (isIdValid(item))
                {
                    setObjVar(player, "death_watch.medicine_success", 1);
                    removeObjVar(player, "death_watch.medicine");
                }
            }
            messageTo(npc, "handleCrazedCleanup", null, 30f, false);
        }
        return;
    }
    public void death_watch_insane_miner_action_attackSpeaker(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(npc, "death_watch.noHelp", player);
        dictionary params = new dictionary();
        params.put("player", player);
        messageTo(npc, "handleCrazedAttack", params, 1f, false);
        return;
    }
    public int death_watch_insane_miner_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3fb7180e"))
        {
            if (death_watch_insane_miner_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_87bb258b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (death_watch_insane_miner_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (death_watch_insane_miner_condition_checkMedicine(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (death_watch_insane_miner_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_16968d33");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_dad50efa");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4fe1bc67");
                    }
                    utils.setScriptVar(player, "conversation.death_watch_insane_miner.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.death_watch_insane_miner.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_7c3e9069"))
        {
            if (death_watch_insane_miner_condition__defaultCondition(player, npc))
            {
                death_watch_insane_miner_action_attackSpeaker(player, npc);
                string_id message = new string_id(c_stringFile, "s_15");
                utils.removeScriptVar(player, "conversation.death_watch_insane_miner.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int death_watch_insane_miner_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_16968d33"))
        {
            if (death_watch_insane_miner_condition__defaultCondition(player, npc))
            {
                death_watch_insane_miner_action_getMedicine(player, npc);
                string_id message = new string_id(c_stringFile, "s_2ff0df5");
                utils.removeScriptVar(player, "conversation.death_watch_insane_miner.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_dad50efa"))
        {
            if (death_watch_insane_miner_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_ea390b92");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (death_watch_insane_miner_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_829888a9");
                    }
                    utils.setScriptVar(player, "conversation.death_watch_insane_miner.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.death_watch_insane_miner.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_4fe1bc67"))
        {
            if (death_watch_insane_miner_condition__defaultCondition(player, npc))
            {
                death_watch_insane_miner_action_attackSpeaker(player, npc);
                string_id message = new string_id(c_stringFile, "s_99f3d3be");
                utils.removeScriptVar(player, "conversation.death_watch_insane_miner.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int death_watch_insane_miner_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_829888a9"))
        {
            death_watch_insane_miner_action_removeMedicine(player, npc);
            if (death_watch_insane_miner_condition__defaultCondition(player, npc))
            {
                death_watch_insane_miner_action_giveBattery(player, npc);
                string_id message = new string_id(c_stringFile, "s_e810685f");
                utils.removeScriptVar(player, "conversation.death_watch_insane_miner.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int death_watch_insane_miner_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_7c83bd45"))
        {
            if (death_watch_insane_miner_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_2efed805");
                utils.removeScriptVar(player, "conversation.death_watch_insane_miner.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_42a2316b"))
        {
            if (death_watch_insane_miner_condition__defaultCondition(player, npc))
            {
                death_watch_insane_miner_action_giveBattery(player, npc);
                string_id message = new string_id(c_stringFile, "s_21");
                utils.removeScriptVar(player, "conversation.death_watch_insane_miner.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_23"))
        {
            if (death_watch_insane_miner_condition__defaultCondition(player, npc))
            {
                death_watch_insane_miner_action_attackSpeaker(player, npc);
                string_id message = new string_id(c_stringFile, "s_25");
                utils.removeScriptVar(player, "conversation.death_watch_insane_miner.branchId");
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
            detachScript(self, "conversation.death_watch_insane_miner");
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
        detachScript(self, "conversation.death_watch_insane_miner");
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
        if (death_watch_insane_miner_condition_startMission(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_21632dd");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (death_watch_insane_miner_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (death_watch_insane_miner_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_3fb7180e");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_7c3e9069");
                }
                utils.setScriptVar(player, "conversation.death_watch_insane_miner.branchId", 1);
                npcStartConversation(player, npc, "death_watch_insane_miner", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (death_watch_insane_miner_condition_alreadyAccepted(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_ce67a12e");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (death_watch_insane_miner_condition_noMedicine(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (death_watch_insane_miner_condition_checkMedicine(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (death_watch_insane_miner_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_7c83bd45");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_42a2316b");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_23");
                }
                utils.setScriptVar(player, "conversation.death_watch_insane_miner.branchId", 8);
                npcStartConversation(player, npc, "death_watch_insane_miner", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (death_watch_insane_miner_condition_noQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_c2b7bf76");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("death_watch_insane_miner"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.death_watch_insane_miner.branchId");
        if (branchId == 1 && death_watch_insane_miner_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && death_watch_insane_miner_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && death_watch_insane_miner_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && death_watch_insane_miner_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.death_watch_insane_miner.branchId");
        return SCRIPT_CONTINUE;
    }
}
