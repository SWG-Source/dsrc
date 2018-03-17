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
import script.library.utils;

public class victor_questn_cantina extends script.base_script
{
    public victor_questn_cantina()
    {
    }
    public static String c_stringFile = "conversation/victor_questn_cantina";
    public boolean victor_questn_cantina_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean victor_questn_cantina_condition_NegQCheck(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.electionStarted"))
        {
            int electionNum = getIntObjVar(npc, "bestine.electionStarted");
            if (hasObjVar(player, "bestine.negativeq"))
            {
                int electionPlayerIsIn = getIntObjVar(player, "bestine.negativeq");
                if (electionPlayerIsIn >= electionNum)
                {
                    if (!hasObjVar(player, "bestine.victor_cantina_noroom"))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean victor_questn_cantina_condition_hasEnoughMoney(obj_id player, obj_id npc) throws InterruptedException
    {
        return money.hasFunds(player, money.MT_TOTAL, 200);
    }
    public boolean victor_questn_cantina_condition_hasNoroomObjVar(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.electionStarted"))
        {
            int electionNum = getIntObjVar(npc, "bestine.electionStarted");
            if (hasObjVar(player, "bestine.negativeq"))
            {
                int electionPlayerIsIn = getIntObjVar(player, "bestine.negativeq");
                if (electionPlayerIsIn >= electionNum)
                {
                    if (hasObjVar(player, "bestine.victor_cantina_noroom"))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean victor_questn_cantina_condition_nonoffice(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasObjVar(npc, "bestine.electionEnded");
    }
    public boolean victor_questn_cantina_condition_noInventorySpace(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean victor_questn_cantina_condition_alreadyHasEvidence(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.playerHasItemByTemplate(player, "object/tangible/loot/quest/victor_questn_dseal.iff");
    }
    public void victor_questn_cantina_action__defaultAction(obj_id player, obj_id npc) throws InterruptedException
    {
    }
    public void victor_questn_cantina_action_Noroom(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "bestine.victor_cantina_noroom", true);
    }
    public void victor_questn_cantina_action_givePaper(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "bestine.victor_cantina_noroom"))
        {
            removeObjVar(player, "bestine.victor_cantina_noroom");
        }
        money.requestPayment(player, npc, 200, "pass_fail", null, true);
        String DSEAL = "object/tangible/loot/quest/victor_questn_dseal.iff";
        if (isIdValid(player))
        {
            obj_id playerInv = getObjectInSlot(player, "inventory");
            if (isIdValid(playerInv))
            {
                obj_id item = createObject(DSEAL, playerInv, "");
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
            detachScript(self, "npc.conversation.victor_questn_cantina");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        ai_lib.setDefaultCalmMood(self, "npc_sitting_chair");
        int self_yaw = 179;
        setYaw(self, self_yaw);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        ai_lib.setDefaultCalmMood(self, "npc_sitting_chair");
        int self_yaw = 179;
        setYaw(self, self_yaw);
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
        detachScript(self, "npc.conversation.victor_questn_cantina");
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (victor_questn_cantina_condition_nonoffice(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_6307f85c");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (victor_questn_cantina_condition_alreadyHasEvidence(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_c70282a3");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (victor_questn_cantina_condition_hasNoroomObjVar(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_89278f85");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (victor_questn_cantina_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (victor_questn_cantina_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_62bf3631");
                }
                setObjVar(player, "conversation.victor_questn_cantina.branchId", 3);
                npcStartConversation(player, self, "victor_questn_cantina", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (victor_questn_cantina_condition_NegQCheck(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_dcb68f7c");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (victor_questn_cantina_condition_hasEnoughMoney(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (!victor_questn_cantina_condition_hasEnoughMoney(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_51ac4991");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_19d90991");
                }
                setObjVar(player, "conversation.victor_questn_cantina.branchId", 7);
                npcStartConversation(player, self, "victor_questn_cantina", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (victor_questn_cantina_condition__defaultCondition(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_3665cc86");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("victor_questn_cantina"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.victor_questn_cantina.branchId");
        if (branchId == 3 && response.equals("s_90ec63e0"))
        {
            if (!victor_questn_cantina_condition_noInventorySpace(player, self))
            {
                victor_questn_cantina_action_givePaper(player, self);
                string_id message = new string_id(c_stringFile, "s_ac84b134");
                removeObjVar(player, "conversation.victor_questn_cantina.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (victor_questn_cantina_condition_noInventorySpace(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_f7347f40");
                removeObjVar(player, "conversation.victor_questn_cantina.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Bah, yer back. Ye 'ave enough room yet?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && response.equals("s_62bf3631"))
        {
            if (victor_questn_cantina_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_36fef8c1");
                removeObjVar(player, "conversation.victor_questn_cantina.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Bah, yer back. Ye 'ave enough room yet?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && response.equals("s_51ac4991"))
        {
            if (!victor_questn_cantina_condition_noInventorySpace(player, self))
            {
                victor_questn_cantina_action_givePaper(player, self);
                string_id message = new string_id(c_stringFile, "s_ac84b134");
                removeObjVar(player, "conversation.victor_questn_cantina.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (victor_questn_cantina_condition_noInventorySpace(player, self))
            {
                victor_questn_cantina_action_Noroom(player, self);
                string_id message = new string_id(c_stringFile, "s_8a0cf969");
                removeObjVar(player, "conversation.victor_questn_cantina.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Yeh, I 'ave what y'need. Y'lookin' fer some sort o'evidence against that Victor Vis'ilis? Wh'evah his name is. He'a been ignorin' our 'illegal' activites fer some time now. He even made'a agreement wit' us, wrote it on'a paper even! Daft, I says. I'll give it to ya iffin' ye pay me... two hunnerd credits.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && response.equals("s_19d90991"))
        {
            if (victor_questn_cantina_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_ff29a83c");
                removeObjVar(player, "conversation.victor_questn_cantina.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Yeh, I 'ave what y'need. Y'lookin' fer some sort o'evidence against that Victor Vis'ilis? Wh'evah his name is. He'a been ignorin' our 'illegal' activites fer some time now. He even made'a agreement wit' us, wrote it on'a paper even! Daft, I says. I'll give it to ya iffin' ye pay me... two hunnerd credits.' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.victor_questn_cantina.branchId");
        return SCRIPT_CONTINUE;
    }
}
