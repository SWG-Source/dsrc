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

public class victor_questn_capitol extends script.base_script
{
    public victor_questn_capitol()
    {
    }
    public static String c_stringFile = "conversation/victor_questn_capitol";
    public boolean victor_questn_capitol_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean victor_questn_capitol_condition_NegaObj(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.electionStarted"))
        {
            int electionNum = getIntObjVar(npc, "bestine.electionStarted");
            if (hasObjVar(player, "bestine.negativeq"))
            {
                int electionPlayerIsIn = getIntObjVar(player, "bestine.negativeq");
                if (electionPlayerIsIn >= electionNum)
                {
                    if (!hasObjVar(player, "bestine.victor_capitol_noroom"))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean victor_questn_capitol_condition_alreadyHasEvidence(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.playerHasItemByTemplate(player, "object/tangible/loot/quest/victor_questn_hlist.iff");
    }
    public boolean victor_questn_capitol_condition_hasNoroomObjvar(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.electionStarted"))
        {
            int electionNum = getIntObjVar(npc, "bestine.electionStarted");
            if (hasObjVar(player, "bestine.negativeq"))
            {
                int electionPlayerIsIn = getIntObjVar(player, "bestine.negativeq");
                if (electionPlayerIsIn >= electionNum)
                {
                    if (hasObjVar(player, "bestine.victor_capitol_noroom"))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean victor_questn_capitol_condition_nonoffice(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasObjVar(npc, "bestine.electionEnded");
    }
    public boolean victor_questn_capitol_condition_noInventorySpace(obj_id player, obj_id npc) throws InterruptedException
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
    public void victor_questn_capitol_action_noroom(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "bestine.victor_capitol_noroom", true);
    }
    public void victor_questn_capitol_action_giveList(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "bestine.victor_capitol_noroom"))
        {
            removeObjVar(player, "bestine.victor_capitol_noroom");
        }
        String HLIST = "object/tangible/loot/quest/victor_questn_hlist.iff";
        if (isIdValid(player))
        {
            obj_id playerInv = getObjectInSlot(player, "inventory");
            if (isIdValid(playerInv))
            {
                obj_id item = createObject(HLIST, playerInv, "");
                if (isIdValid(item))
                {
                    return;
                }
            }
        }
        return;
    }
    public int victor_questn_capitol_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_90ec63e0"))
        {
            if (!victor_questn_capitol_condition_noInventorySpace(player, npc))
            {
                victor_questn_capitol_action_giveList(player, npc);
                string_id message = new string_id(c_stringFile, "s_6ddb06b2");
                utils.removeScriptVar(player, "conversation.victor_questn_capitol.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (victor_questn_capitol_condition_noInventorySpace(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_99ef56dc");
                utils.removeScriptVar(player, "conversation.victor_questn_capitol.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_da9a29e9"))
        {
            if (victor_questn_capitol_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_42deb08e");
                utils.removeScriptVar(player, "conversation.victor_questn_capitol.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int victor_questn_capitol_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_496168b6"))
        {
            if (victor_questn_capitol_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_ce8d51fb");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (victor_questn_capitol_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.victor_questn_capitol.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.victor_questn_capitol.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_e50dbfc0"))
        {
            if (victor_questn_capitol_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_11cdd7b1");
                utils.removeScriptVar(player, "conversation.victor_questn_capitol.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int victor_questn_capitol_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_b9b27823"))
        {
            if (!victor_questn_capitol_condition_noInventorySpace(player, npc))
            {
                victor_questn_capitol_action_giveList(player, npc);
                string_id message = new string_id(c_stringFile, "s_d638d2ec");
                utils.removeScriptVar(player, "conversation.victor_questn_capitol.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (victor_questn_capitol_condition_noInventorySpace(player, npc))
            {
                victor_questn_capitol_action_noroom(player, npc);
                string_id message = new string_id(c_stringFile, "s_d2df4599");
                utils.removeScriptVar(player, "conversation.victor_questn_capitol.branchId");
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
            detachScript(self, "conversation.victor_questn_capitol");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setName(self, "TL-017");
        factions.setFaction(self, "Imperial");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setName(self, "TL-017");
        factions.setFaction(self, "Imperial");
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
        detachScript(self, "conversation.victor_questn_capitol");
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
        if (victor_questn_capitol_condition_nonoffice(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_c1bf1629");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (victor_questn_capitol_condition_alreadyHasEvidence(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_f688af52");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (victor_questn_capitol_condition_hasNoroomObjvar(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_f62e5a7d");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (victor_questn_capitol_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (victor_questn_capitol_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.victor_questn_capitol.branchId", 3);
                npcStartConversation(player, npc, "victor_questn_capitol", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (victor_questn_capitol_condition_NegaObj(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_4d313a");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (victor_questn_capitol_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (victor_questn_capitol_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_496168b6");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_e50dbfc0");
                }
                utils.setScriptVar(player, "conversation.victor_questn_capitol.branchId", 7);
                npcStartConversation(player, npc, "victor_questn_capitol", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (victor_questn_capitol_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_7dcdb445");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("victor_questn_capitol"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.victor_questn_capitol.branchId");
        if (branchId == 3 && victor_questn_capitol_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && victor_questn_capitol_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && victor_questn_capitol_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.victor_questn_capitol.branchId");
        return SCRIPT_CONTINUE;
    }
}
