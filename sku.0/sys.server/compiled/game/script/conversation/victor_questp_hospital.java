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

public class victor_questp_hospital extends script.base_script
{
    public victor_questp_hospital()
    {
    }
    public static String c_stringFile = "conversation/victor_questp_hospital";
    public boolean victor_questp_hospital_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean victor_questp_hospital_condition_CampObjCheck(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.electionStarted"))
        {
            int electionNum = getIntObjVar(npc, "bestine.electionStarted");
            if (hasObjVar(player, "bestine.camp"))
            {
                int electionPlayerIsIn = getIntObjVar(player, "bestine.camp");
                if (electionPlayerIsIn >= electionNum)
                {
                    if (!hasObjVar(player, "bestine.victor_hospital_noroom"))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean victor_questp_hospital_condition_alreadyHasEvidence(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.playerHasItemByTemplate(player, "object/tangible/loot/quest/victor_questp_receipt.iff");
    }
    public boolean victor_questp_hospital_condition_hasNoroomObjvar(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.electionStarted"))
        {
            int electionNum = getIntObjVar(npc, "bestine.electionStarted");
            if (hasObjVar(player, "bestine.camp"))
            {
                int electionPlayerIsIn = getIntObjVar(player, "bestine.camp");
                if (electionPlayerIsIn >= electionNum)
                {
                    if (hasObjVar(player, "bestine.victor_hospital_noroom"))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean victor_questp_hospital_condition_nonoffice(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasObjVar(npc, "bestine.electionEnded");
    }
    public boolean victor_questp_hospital_condition_noInventorySpace(obj_id player, obj_id npc) throws InterruptedException
    {
        boolean hasNoInvRoom = false;
        obj_id playerInv = utils.getInventoryContainer(player);
        if (isIdValid(playerInv))
        {
            int free_space = getVolumeFree(playerInv);
            if (free_space < 1)
            {
                hasNoInvRoom = true;
            }
        }
        return hasNoInvRoom;
    }
    public void victor_questp_hospital_action__defaultAction(obj_id player, obj_id npc) throws InterruptedException
    {
    }
    public void victor_questp_hospital_action_Noroom(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "bestine.victor_hospital_noroom", true);
    }
    public void victor_questp_hospital_action_giveReceipt(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "bestine.victor_hospital_noroom"))
        {
            removeObjVar(player, "bestine.victor_hospital_noroom");
        }
        String RECEIPT = "object/tangible/loot/quest/victor_questp_receipt.iff";
        if (isIdValid(player))
        {
            obj_id playerInv = getObjectInSlot(player, "inventory");
            if (isIdValid(playerInv))
            {
                obj_id item = createObject(RECEIPT, playerInv, "");
                if (isIdValid(item))
                {
                    return;
                }
            }
        }
        return;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.victor_questp_hospital");
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
        detachScript(self, "npc.conversation.victor_questp_hospital");
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (victor_questp_hospital_condition_nonoffice(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_23306eeb");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (victor_questp_hospital_condition_alreadyHasEvidence(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_7651d151");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (victor_questp_hospital_condition_hasNoroomObjvar(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_e4d6a9f4");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (victor_questp_hospital_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (victor_questp_hospital_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_90ec63e0");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_da9a29e9");
                }
                setObjVar(player, "conversation.victor_questp_hospital.branchId", 3);
                npcStartConversation(player, self, "victor_questp_hospital", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (victor_questp_hospital_condition_CampObjCheck(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_f2a042d2");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (victor_questp_hospital_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (victor_questp_hospital_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_67a10ef6");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_58411386");
                }
                setObjVar(player, "conversation.victor_questp_hospital.branchId", 7);
                npcStartConversation(player, self, "victor_questp_hospital", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (victor_questp_hospital_condition__defaultCondition(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_4c7aa64d");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("victor_questp_hospital"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.victor_questp_hospital.branchId");
        if (branchId == 3 && response.equals("s_90ec63e0"))
        {
            if (!victor_questp_hospital_condition_noInventorySpace(player, self))
            {
                victor_questp_hospital_action_giveReceipt(player, self);
                string_id message = new string_id(c_stringFile, "s_90c73952");
                removeObjVar(player, "conversation.victor_questp_hospital.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (victor_questp_hospital_condition_noInventorySpace(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_46462c3d");
                removeObjVar(player, "conversation.victor_questp_hospital.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Oh, you're back! Do you have enough room to receive your evidence now?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && response.equals("s_da9a29e9"))
        {
            if (victor_questp_hospital_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_abdf450b");
                removeObjVar(player, "conversation.victor_questp_hospital.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Oh, you're back! Do you have enough room to receive your evidence now?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && response.equals("s_67a10ef6"))
        {
            if (victor_questp_hospital_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_23ac5f59");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (victor_questp_hospital_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (victor_questp_hospital_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ad2e138");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_dafadce6");
                    }
                    setObjVar(player, "conversation.victor_questp_hospital.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.victor_questp_hospital.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Are you here to make a donation to the orphanage? We received a huge donation just the other week from a respectable politician. It is truly exciting. Our children can finally get the care they need because of him.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && response.equals("s_58411386"))
        {
            if (victor_questp_hospital_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_2dc62c40");
                removeObjVar(player, "conversation.victor_questp_hospital.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Are you here to make a donation to the orphanage? We received a huge donation just the other week from a respectable politician. It is truly exciting. Our children can finally get the care they need because of him.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && response.equals("s_ad2e138"))
        {
            if (victor_questp_hospital_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_e0a4222c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (victor_questp_hospital_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_67e6df55");
                    }
                    setObjVar(player, "conversation.victor_questp_hospital.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.victor_questp_hospital.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Victor Visalis. He's truly a great man. I wish I could help him in some way for being so generous.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && response.equals("s_dafadce6"))
        {
            if (victor_questp_hospital_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_705d8e7c");
                removeObjVar(player, "conversation.victor_questp_hospital.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Victor Visalis. He's truly a great man. I wish I could help him in some way for being so generous.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && response.equals("s_67e6df55"))
        {
            if (!victor_questp_hospital_condition_noInventorySpace(player, self))
            {
                victor_questp_hospital_action_giveReceipt(player, self);
                string_id message = new string_id(c_stringFile, "s_34c66631");
                removeObjVar(player, "conversation.victor_questp_hospital.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (victor_questp_hospital_condition_noInventorySpace(player, self))
            {
                victor_questp_hospital_action_Noroom(player, self);
                string_id message = new string_id(c_stringFile, "s_b06dcd05");
                removeObjVar(player, "conversation.victor_questp_hospital.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You do? I'm all ears. Hmm... really? I have just the perfect thing for your evidence. I always keep a sort of personal receipt system for all the donations I've received just so no one will wonder where I keep getting all this money from. Let me make a copy. Hold on a second, will you?' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.victor_questp_hospital.branchId");
        return SCRIPT_CONTINUE;
    }
}
