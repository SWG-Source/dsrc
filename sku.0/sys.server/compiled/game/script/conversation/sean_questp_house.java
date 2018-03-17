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

public class sean_questp_house extends script.base_script
{
    public sean_questp_house()
    {
    }
    public static String c_stringFile = "conversation/sean_questp_house";
    public boolean sean_questp_house_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean sean_questp_house_condition_CampObj(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.electionStarted"))
        {
            int electionNum = getIntObjVar(npc, "bestine.electionStarted");
            if (hasObjVar(player, "bestine.campaign"))
            {
                int electionPlayerIsIn = getIntObjVar(player, "bestine.campaign");
                if (electionPlayerIsIn >= electionNum)
                {
                    if (!hasObjVar(player, "bestine.sean_house_noroom"))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean sean_questp_house_condition_HouseTesti(obj_id player, obj_id npc) throws InterruptedException
    {
        String HTESTIMONY = "object/tangible/loot/quest/sean_questp_htestimony.iff";
        if (isIdValid(player))
        {
            obj_id playerInv = getObjectInSlot(player, "inventory");
            if (isIdValid(playerInv))
            {
                obj_id item = createObject(HTESTIMONY, playerInv, "");
                if (isIdValid(item))
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean sean_questp_house_condition_noroomObjVar(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.electionStarted"))
        {
            int electionNum = getIntObjVar(npc, "bestine.electionStarted");
            if (hasObjVar(player, "bestine.campaign"))
            {
                int electionPlayerIsIn = getIntObjVar(player, "bestine.campaign");
                if (electionPlayerIsIn >= electionNum)
                {
                    if (hasObjVar(player, "bestine.sean_house_noroom"))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean sean_questp_house_condition_nonoffice(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasObjVar(npc, "bestine.electionEnded");
    }
    public boolean sean_questp_house_condition_noInventorySpace(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean sean_questp_house_condition_alreadyHasEvidence(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.playerHasItemByTemplate(player, "object/tangible/loot/quest/sean_questp_htestimony.iff");
    }
    public void sean_questp_house_action__defaultAction(obj_id player, obj_id npc) throws InterruptedException
    {
    }
    public void sean_questp_house_action_NoRoom(obj_id player, obj_id npc) throws InterruptedException
    {
        int electionNum = getIntObjVar(npc, "bestine.electionStarted");
        setObjVar(player, "bestine.bestine.sean_house_noroom", electionNum);
    }
    public void sean_questp_house_action_giveTestimony(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "bestine.sean_house_noroom"))
        {
            removeObjVar(player, "bestine.sean_house_noroom");
        }
        String HTESTIMONY = "object/tangible/loot/quest/sean_questp_htestimony.iff";
        if (isIdValid(player))
        {
            obj_id playerInv = getObjectInSlot(player, "inventory");
            if (isIdValid(playerInv))
            {
                obj_id item = createObject(HTESTIMONY, playerInv, "");
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
            detachScript(self, "npc.conversation.sean_questp_house");
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
        detachScript(self, "npc.conversation.sean_questp_house");
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (sean_questp_house_condition_nonoffice(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_2267c63c");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (sean_questp_house_condition_alreadyHasEvidence(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_41f6594d");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (sean_questp_house_condition_noroomObjVar(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_20b8d563");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (sean_questp_house_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (sean_questp_house_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_e69937da");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_48b343d3");
                }
                setObjVar(player, "conversation.sean_questp_house.branchId", 3);
                npcStartConversation(player, self, "sean_questp_house", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (sean_questp_house_condition_CampObj(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_f6effab8");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (sean_questp_house_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (sean_questp_house_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_34f710c5");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_51c40792");
                }
                setObjVar(player, "conversation.sean_questp_house.branchId", 7);
                npcStartConversation(player, self, "sean_questp_house", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (sean_questp_house_condition__defaultCondition(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_d066eb10");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("sean_questp_house"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.sean_questp_house.branchId");
        if (branchId == 3 && response.equals("s_e69937da"))
        {
            if (!sean_questp_house_condition_noInventorySpace(player, self))
            {
                sean_questp_house_action_giveTestimony(player, self);
                string_id message = new string_id(c_stringFile, "s_9e4a0794");
                removeObjVar(player, "conversation.sean_questp_house.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (sean_questp_house_condition_noInventorySpace(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_bd542a54");
                removeObjVar(player, "conversation.sean_questp_house.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I'm glad you're back. Do you have enough room now?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && response.equals("s_48b343d3"))
        {
            if (sean_questp_house_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_d6d23166");
                removeObjVar(player, "conversation.sean_questp_house.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I'm glad you're back. Do you have enough room now?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && response.equals("s_34f710c5"))
        {
            if (sean_questp_house_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_8a9c6889");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (sean_questp_house_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ab4c4ff8");
                    }
                    setObjVar(player, "conversation.sean_questp_house.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.sean_questp_house.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Oh my! I didn't expect visitors today! This place is a complete mess! Please forgive me. The last of the tragedy victims left. They all found new homes. I'm just cleaning up. Why are you here?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && response.equals("s_51c40792"))
        {
            if (sean_questp_house_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_d5702a3b");
                removeObjVar(player, "conversation.sean_questp_house.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Oh my! I didn't expect visitors today! This place is a complete mess! Please forgive me. The last of the tragedy victims left. They all found new homes. I'm just cleaning up. Why are you here?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && response.equals("s_ab4c4ff8"))
        {
            if (sean_questp_house_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_43e36f6f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (sean_questp_house_condition__defaultCondition(player, self))
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
                    setObjVar(player, "conversation.sean_questp_house.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.sean_questp_house.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Oh, you didn't hear? The Empire went on a rampage and destroyed one of the newer, smaller cities assumed to harbor Rebel operatives. Those poor people. Sean Trenwell opened his homes for the survivors. He's such a kind man.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && response.equals("s_67e6df55"))
        {
            if (!sean_questp_house_condition_noInventorySpace(player, self))
            {
                sean_questp_house_action_giveTestimony(player, self);
                string_id message = new string_id(c_stringFile, "s_47e61b76");
                removeObjVar(player, "conversation.sean_questp_house.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (sean_questp_house_condition_noInventorySpace(player, self))
            {
                sean_questp_house_action_NoRoom(player, self);
                string_id message = new string_id(c_stringFile, "s_198905a5");
                removeObjVar(player, "conversation.sean_questp_house.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Sure, I can try. What do you need? Write a testimony? Of course, I will! Anything to help him with this election. I really hope he wins. He deserves it. Let me write it up..' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.sean_questp_house.branchId");
        return SCRIPT_CONTINUE;
    }
}
