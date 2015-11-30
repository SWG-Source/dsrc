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
import script.library.conversation;
import script.library.utils;

public class jedi_robe_collection_giver extends script.base_script
{
    public jedi_robe_collection_giver()
    {
    }
    public static String c_stringFile = "conversation/jedi_robe_collection_giver";
    public boolean jedi_robe_collection_giver_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean jedi_robe_collection_giver_condition_isEligible_JediRobeCollection(obj_id player, obj_id npc) throws InterruptedException
    {
        if (utils.isProfession(player, utils.FORCE_SENSITIVE))
        {
            if (getLevel(player) >= 85)
            {
                if (!hasCompletedCollectionSlot(player, "jedi_robe_01_01"))
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean jedi_robe_collection_giver_condition_isActive_jediRobeCollection(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasCompletedCollectionSlot(player, "jedi_robe_01_01");
    }
    public boolean jedi_robe_collection_giver_condition_ineligibleJedi(obj_id player, obj_id npc) throws InterruptedException
    {
        if (utils.isProfession(player, utils.FORCE_SENSITIVE))
        {
            if (getLevel(player) < 85)
            {
                return true;
            }
        }
        return false;
    }
    public boolean jedi_robe_collection_giver_condition_isComplete_jediRobeCollection(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasCompletedCollection(player, "jedi_robe_01");
    }
    public void jedi_robe_collection_giver_action_activateJediRobeCollection(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasCompletedCollectionSlot(player, "jedi_robe_01_01"))
        {
            modifyCollectionSlotValue(player, "jedi_robe_01_01", 1);
        }
        if (!hasCompletedCollectionSlot(player, "col_lightsaber_1h_01_01"))
        {
            modifyCollectionSlotValue(player, "col_lightsaber_1h_01_01", 1);
        }
        if (!hasCompletedCollectionSlot(player, "col_lightsaber_2h_01_01"))
        {
            modifyCollectionSlotValue(player, "col_lightsaber_2h_01_01", 1);
        }
        if (!hasCompletedCollectionSlot(player, "col_lightsaber_polearm_01_01"))
        {
            modifyCollectionSlotValue(player, "col_lightsaber_polearm_01_01", 1);
        }
        if (hasCompletedCollection(player, "inv_holocron_collection_02"))
        {
            modifyCollectionSlotValue(player, "jedi_robe_01_02", 1);
        }
        if (hasCompletedCollection(player, "inv_holocron_collection_01"))
        {
            modifyCollectionSlotValue(player, "jedi_robe_01_03", 1);
        }
        return;
    }
    public int jedi_robe_collection_giver_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_10"))
        {
            if (jedi_robe_collection_giver_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "worship");
                jedi_robe_collection_giver_action_activateJediRobeCollection(player, npc);
                string_id message = new string_id(c_stringFile, "s_11");
                utils.removeScriptVar(player, "conversation.jedi_robe_collection_giver.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_13"))
        {
            if (jedi_robe_collection_giver_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "taken_aback");
                string_id message = new string_id(c_stringFile, "s_15");
                utils.removeScriptVar(player, "conversation.jedi_robe_collection_giver.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_18"))
        {
            if (jedi_robe_collection_giver_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                string_id message = new string_id(c_stringFile, "s_20");
                utils.removeScriptVar(player, "conversation.jedi_robe_collection_giver.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isTangible(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.jedi_robe_collection_giver");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info menuInfo) throws InterruptedException
    {
        int menu = menuInfo.addRootMenu(menu_info_types.CONVERSE_START, null);
        menu_info_data menuInfoData = menuInfo.getMenuItemById(menu);
        menuInfoData.setServerNotify(false);
        setCondition(self, CONDITION_CONVERSABLE);
        faceTo(self, player);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        detachScript(self, "conversation.jedi_robe_collection_giver");
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
        if (jedi_robe_collection_giver_condition_isComplete_jediRobeCollection(player, npc))
        {
            doAnimationAction(npc, "bow5");
            string_id message = new string_id(c_stringFile, "s_16");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (jedi_robe_collection_giver_condition_isActive_jediRobeCollection(player, npc))
        {
            doAnimationAction(npc, "greet");
            jedi_robe_collection_giver_action_activateJediRobeCollection(player, npc);
            string_id message = new string_id(c_stringFile, "s_6");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (jedi_robe_collection_giver_condition_isEligible_JediRobeCollection(player, npc))
        {
            doAnimationAction(npc, "beckon");
            string_id message = new string_id(c_stringFile, "s_7");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (jedi_robe_collection_giver_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (jedi_robe_collection_giver_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (jedi_robe_collection_giver_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_10");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_13");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_18");
                }
                utils.setScriptVar(player, "conversation.jedi_robe_collection_giver.branchId", 3);
                npcStartConversation(player, npc, "jedi_robe_collection_giver", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (jedi_robe_collection_giver_condition_ineligibleJedi(player, npc))
        {
            doAnimationAction(npc, "greet");
            string_id message = new string_id(c_stringFile, "s_22");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (jedi_robe_collection_giver_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "wave_on_dismissing");
            string_id message = new string_id(c_stringFile, "s_24");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("jedi_robe_collection_giver"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.jedi_robe_collection_giver.branchId");
        if (branchId == 3 && jedi_robe_collection_giver_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.jedi_robe_collection_giver.branchId");
        return SCRIPT_CONTINUE;
    }
}
