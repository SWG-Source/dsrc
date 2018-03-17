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

public class sean_questn_university extends script.base_script
{
    public sean_questn_university()
    {
    }
    public static String c_stringFile = "conversation/sean_questn_university";
    public boolean sean_questn_university_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean sean_questn_university_condition_NegQObj(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.electionStarted"))
        {
            int electionNum = getIntObjVar(npc, "bestine.electionStarted");
            if (hasObjVar(player, "bestine.negquests"))
            {
                int electionPlayerIsIn = getIntObjVar(player, "bestine.negquests");
                if (electionPlayerIsIn >= electionNum)
                {
                    if (!hasObjVar(player, "bestine.sean_university_noroom"))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean sean_questn_university_condition_alreadyHasEvidence(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.playerHasItemByTemplate(player, "object/tangible/loot/quest/sean_questn_gpapers.iff");
    }
    public boolean sean_questn_university_condition_NoRoom1Obj(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.electionStarted"))
        {
            int electionNum = getIntObjVar(npc, "bestine.electionStarted");
            if (hasObjVar(player, "bestine.negquests"))
            {
                int electionPlayerIsIn = getIntObjVar(player, "bestine.negquests");
                if (electionPlayerIsIn >= electionNum)
                {
                    if (hasObjVar(player, "bestine.sean_university_noroom"))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean sean_questn_university_condition_nonoffice(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasObjVar(npc, "bestine.electionEnded");
    }
    public boolean sean_questn_university_condition_noInventorySpace(obj_id player, obj_id npc) throws InterruptedException
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
    public void sean_questn_university_action__defaultAction(obj_id player, obj_id npc) throws InterruptedException
    {
    }
    public void sean_questn_university_action_NoRoom1(obj_id player, obj_id npc) throws InterruptedException
    {
        int electionNum = getIntObjVar(npc, "bestine.electionStarted");
        setObjVar(player, "bestine.sean_university_noroom", electionNum);
    }
    public void sean_questn_university_action_givePapers(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "bestine.sean_university_noroom"))
        {
            removeObjVar(player, "bestine.sean_university_noroom");
        }
        String GPAPERS = "object/tangible/loot/quest/sean_questn_gpapers.iff";
        if (isIdValid(player))
        {
            obj_id playerInv = getObjectInSlot(player, "inventory");
            if (isIdValid(playerInv))
            {
                obj_id item = createObject(GPAPERS, playerInv, "");
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
            detachScript(self, "npc.conversation.sean_questn_university");
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
        detachScript(self, "npc.conversation.sean_questn_university");
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (sean_questn_university_condition_nonoffice(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_9c880546");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (sean_questn_university_condition_alreadyHasEvidence(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_70145cf1");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (sean_questn_university_condition_NoRoom1Obj(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_656e8f97");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (sean_questn_university_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (sean_questn_university_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_7e78bf8f");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_7291fe2e");
                }
                setObjVar(player, "conversation.sean_questn_university.branchId", 3);
                npcStartConversation(player, self, "sean_questn_university", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (sean_questn_university_condition_NegQObj(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_5788afea");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (sean_questn_university_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_36904010");
                }
                setObjVar(player, "conversation.sean_questn_university.branchId", 7);
                npcStartConversation(player, self, "sean_questn_university", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (sean_questn_university_condition__defaultCondition(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_3c4a2f4");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("sean_questn_university"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.sean_questn_university.branchId");
        if (branchId == 3 && response.equals("s_7e78bf8f"))
        {
            if (!sean_questn_university_condition_noInventorySpace(player, self))
            {
                sean_questn_university_action_givePapers(player, self);
                string_id message = new string_id(c_stringFile, "s_dcf7aa7a");
                removeObjVar(player, "conversation.sean_questn_university.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (sean_questn_university_condition_noInventorySpace(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_99711fbf");
                removeObjVar(player, "conversation.sean_questn_university.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I see that you've returned. Do you have enough room now?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && response.equals("s_7291fe2e"))
        {
            if (sean_questn_university_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_b8a92f09");
                removeObjVar(player, "conversation.sean_questn_university.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I see that you've returned. Do you have enough room now?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && response.equals("s_36904010"))
        {
            if (sean_questn_university_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_7c259594");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (sean_questn_university_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5ec23736");
                    }
                    setObjVar(player, "conversation.sean_questn_university.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.sean_questn_university.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I'm in the middle of grading papers. Did you need help with something?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && response.equals("s_5ec23736"))
        {
            if (sean_questn_university_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_799ad811");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (sean_questn_university_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b9b27823");
                    }
                    setObjVar(player, "conversation.sean_questn_university.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.sean_questn_university.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Sean Trenwell... oh yes! I remember him. Unfortunately, he was a particularly bad student. I would always catch him trying to cheat during exams. He barely graduated from the University. In fact, he could even be considered a fake. I really don't want him representing our people in this election.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && response.equals("s_b9b27823"))
        {
            if (!sean_questn_university_condition_noInventorySpace(player, self))
            {
                sean_questn_university_action_givePapers(player, self);
                string_id message = new string_id(c_stringFile, "s_e656ba0c");
                removeObjVar(player, "conversation.sean_questn_university.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (sean_questn_university_condition_noInventorySpace(player, self))
            {
                sean_questn_university_action_NoRoom1(player, self);
                string_id message = new string_id(c_stringFile, "s_dac635d2");
                removeObjVar(player, "conversation.sean_questn_university.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Mmhmm--Oh really? I actually have something that might help you. I think you could use it as evidence. Yes! Here it is. I always keep the grades of my students. Here you are.' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.sean_questn_university.branchId");
        return SCRIPT_CONTINUE;
    }
}
