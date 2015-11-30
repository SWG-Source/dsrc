package script.conversation;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.buff;
import script.library.chat;
import script.library.conversation;
import script.library.groundquests;
import script.library.utils;

public class medical_droid_outbreak extends script.base_script
{
    public medical_droid_outbreak()
    {
    }
    public static String c_stringFile = "conversation/medical_droid_outbreak";
    public boolean medical_droid_outbreak_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean medical_droid_outbreak_condition_hasSpokenToCody(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        return groundquests.hasCompletedQuest(player, "outbreak_quest_01_imperial") || groundquests.hasCompletedQuest(player, "outbreak_quest_01_rebel") || groundquests.hasCompletedQuest(player, "outbreak_quest_01_neutral") || isGod(player);
    }
    public void medical_droid_outbreak_action_giveAntiVirus(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.setScriptVar(player, "outbreak.innoculated", 1);
        buff.applyBuff(player, "death_troopers_inoculation");
    }
    public int medical_droid_outbreak_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_96"))
        {
            if (medical_droid_outbreak_condition__defaultCondition(player, npc))
            {
                medical_droid_outbreak_action_giveAntiVirus(player, npc);
                string_id message = new string_id(c_stringFile, "s_16");
                utils.removeScriptVar(player, "conversation.medical_droid_outbreak.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_15"))
        {
            if (medical_droid_outbreak_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_17");
                utils.removeScriptVar(player, "conversation.medical_droid_outbreak.branchId");
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
            detachScript(self, "conversation.medical_droid_outbreak");
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
        detachScript(self, "conversation.medical_droid_outbreak");
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
        if (medical_droid_outbreak_condition_hasSpokenToCody(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_73");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (medical_droid_outbreak_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (medical_droid_outbreak_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_96");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_15");
                }
                utils.setScriptVar(player, "conversation.medical_droid_outbreak.branchId", 1);
                npcStartConversation(player, npc, "medical_droid_outbreak", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (medical_droid_outbreak_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_60");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("medical_droid_outbreak"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.medical_droid_outbreak.branchId");
        if (branchId == 1 && medical_droid_outbreak_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.medical_droid_outbreak.branchId");
        return SCRIPT_CONTINUE;
    }
}
