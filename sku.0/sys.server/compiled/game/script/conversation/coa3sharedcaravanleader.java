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
import script.library.utils;

public class coa3sharedcaravanleader extends script.base_script
{
    public coa3sharedcaravanleader()
    {
    }
    public static String c_stringFile = "conversation/coa3sharedcaravanleader";
    public boolean coa3sharedcaravanleader_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean coa3sharedcaravanleader_condition_hasMissionFromLookout(obj_id player, obj_id npc) throws InterruptedException
    {
        if ((getObjIdObjVar(player, "coa3.imperial.missionNpcId") != npc) && (getObjIdObjVar(player, "coa3.rebel.missionNpcId") != npc))
        {
            return false;
        }
        return (getIntObjVar(player, "coa3.convTracker") == 206);
    }
    public boolean coa3sharedcaravanleader_condition_brokenPartNotRepaired(obj_id player, obj_id npc) throws InterruptedException
    {
        if ((getObjIdObjVar(player, "coa3.imperial.missionNpcId") != npc) && (getObjIdObjVar(player, "coa3.rebel.missionNpcId") != npc))
        {
            return false;
        }
        return (getIntObjVar(player, "coa3.convTracker") == 207);
    }
    public boolean coa3sharedcaravanleader_condition_brokenPartRepaired(obj_id player, obj_id npc) throws InterruptedException
    {
        if ((getObjIdObjVar(player, "coa3.imperial.missionNpcId") != npc) && (getObjIdObjVar(player, "coa3.rebel.missionNpcId") != npc))
        {
            return false;
        }
        final String REPAIRED_GRAV_UNIT = "object/tangible/theme_park/alderaan/act3/repaired_grav_unit.iff";
        if (getIntObjVar(player, "coa3.convTracker") == 207 && utils.playerHasItemByTemplate(player, REPAIRED_GRAV_UNIT))
        {
            return true;
        }
        return false;
    }
    public boolean coa3sharedcaravanleader_condition_hasGottenDisk(obj_id player, obj_id npc) throws InterruptedException
    {
        if ((getObjIdObjVar(player, "coa3.imperial.missionNpcId") != npc) && (getObjIdObjVar(player, "coa3.rebel.missionNpcId") != npc))
        {
            return false;
        }
        return (getIntObjVar(player, "coa3.convTracker") == 208);
    }
    public void coa3sharedcaravanleader_action__defaultAction(obj_id player, obj_id npc) throws InterruptedException
    {
    }
    public void coa3sharedcaravanleader_action_giveBrokenAntiGravUnit(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "coa3.convTracker", 207);
        final String BROKEN_GRAV_UNIT = "object/tangible/theme_park/alderaan/act3/broken_grav_unit.iff";
        final String IMPERIAL_SHARED_STF = "theme_park/alderaan/act3/shared_imperial_missions";
        final string_id BROKEN_UNIT_RECEIVED = new string_id(IMPERIAL_SHARED_STF, "m3_broken_grav_unit_received");
        final string_id BROKEN_UNIT_DENIED = new string_id(IMPERIAL_SHARED_STF, "m3_broken_grav_unit_denied");
        if (!utils.playerHasItemByTemplate(player, BROKEN_GRAV_UNIT))
        {
            obj_id inventory = getObjectInSlot(player, "inventory");
            obj_id unit = createObject(BROKEN_GRAV_UNIT, inventory, "");
            if (!isIdValid(unit))
            {
                sendSystemMessage(player, BROKEN_UNIT_DENIED);
            }
            else 
            {
                sendSystemMessage(player, BROKEN_UNIT_RECEIVED);
            }
        }
    }
    public void coa3sharedcaravanleader_action_getDiskFromCaravanLeader(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "coa3.convTracker", 208);
        final String REPAIRED_GRAV_UNIT = "object/tangible/theme_park/alderaan/act3/repaired_grav_unit.iff";
        final String ENCODED_DATA_DISK = "object/tangible/theme_park/alderaan/act3/encoded_data_disk.iff";
        final String IMPERIAL_SHARED_STF = "theme_park/alderaan/act3/shared_imperial_missions";
        final string_id DATA_DISK_RECEIVED = new string_id(IMPERIAL_SHARED_STF, "m3_data_disk_received");
        final string_id DATA_DISK_DENIED = new string_id(IMPERIAL_SHARED_STF, "m3_data_disk_denied");
        obj_id unit = utils.getItemPlayerHasByTemplate(player, REPAIRED_GRAV_UNIT);
        if (isIdValid(unit))
        {
            destroyObject(unit);
        }
        if (!utils.playerHasItemByTemplate(player, ENCODED_DATA_DISK))
        {
            obj_id inventory = getObjectInSlot(player, "inventory");
            obj_id disk = createObject(ENCODED_DATA_DISK, inventory, "");
            if (!isIdValid(disk))
            {
                sendSystemMessage(player, DATA_DISK_DENIED);
            }
            else 
            {
                sendSystemMessage(player, DATA_DISK_RECEIVED);
            }
        }
        factions.awardFactionStanding(player, "jabba", 200);
        messageTo(player, "createReturnMission", null, 1, false);
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.coa3sharedcaravanleader");
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
        detachScript(self, "npc.conversation.coa3sharedcaravanleader");
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (coa3sharedcaravanleader_condition_hasMissionFromLookout(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_1dde12ed");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (coa3sharedcaravanleader_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (coa3sharedcaravanleader_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (coa3sharedcaravanleader_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_f3da7c8a");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_da681077");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_29e36db9");
                }
                setObjVar(player, "conversation.coa3sharedcaravanleader.branchId", 1);
                npcStartConversation(player, self, "coa3sharedcaravanleader", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (coa3sharedcaravanleader_condition_brokenPartRepaired(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_d3ba3662");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (coa3sharedcaravanleader_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_93eb6764");
                }
                setObjVar(player, "conversation.coa3sharedcaravanleader.branchId", 5);
                npcStartConversation(player, self, "coa3sharedcaravanleader", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (coa3sharedcaravanleader_condition_brokenPartNotRepaired(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_d3ba3662");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (coa3sharedcaravanleader_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (coa3sharedcaravanleader_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_b5f15b19");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_48566ef9");
                }
                setObjVar(player, "conversation.coa3sharedcaravanleader.branchId", 7);
                npcStartConversation(player, self, "coa3sharedcaravanleader", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (coa3sharedcaravanleader_condition_hasGottenDisk(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_869e12b2");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (coa3sharedcaravanleader_condition__defaultCondition(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_e0d207ec");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("coa3sharedcaravanleader"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.coa3sharedcaravanleader.branchId");
        if (branchId == 1 && response.equals("s_f3da7c8a"))
        {
            coa3sharedcaravanleader_action_giveBrokenAntiGravUnit(player, self);
            if (coa3sharedcaravanleader_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_8264ab67");
                removeObjVar(player, "conversation.coa3sharedcaravanleader.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Are you the one I'm waiting on? Transporting this much... umm cargo... over land is dangerous. There are a lot of people that would love to hijack this shipment. With us being broke down; it's only a matter of time before they find us.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 1 && response.equals("s_da681077"))
        {
            if (coa3sharedcaravanleader_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_302369bd");
                removeObjVar(player, "conversation.coa3sharedcaravanleader.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Are you the one I'm waiting on? Transporting this much... umm cargo... over land is dangerous. There are a lot of people that would love to hijack this shipment. With us being broke down; it's only a matter of time before they find us.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 1 && response.equals("s_29e36db9"))
        {
            if (coa3sharedcaravanleader_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_ca41d9fd");
                removeObjVar(player, "conversation.coa3sharedcaravanleader.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Are you the one I'm waiting on? Transporting this much... umm cargo... over land is dangerous. There are a lot of people that would love to hijack this shipment. With us being broke down; it's only a matter of time before they find us.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_93eb6764"))
        {
            coa3sharedcaravanleader_action_getDiskFromCaravanLeader(player, self);
            if (coa3sharedcaravanleader_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_e2c11475");
                removeObjVar(player, "conversation.coa3sharedcaravanleader.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'So is that part repaired yet?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && response.equals("s_b5f15b19"))
        {
            if (coa3sharedcaravanleader_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_b4c62ef2");
                removeObjVar(player, "conversation.coa3sharedcaravanleader.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'So is that part repaired yet?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && response.equals("s_48566ef9"))
        {
            if (coa3sharedcaravanleader_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_b22dadc9");
                removeObjVar(player, "conversation.coa3sharedcaravanleader.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'So is that part repaired yet?' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.coa3sharedcaravanleader.branchId");
        return SCRIPT_CONTINUE;
    }
}
