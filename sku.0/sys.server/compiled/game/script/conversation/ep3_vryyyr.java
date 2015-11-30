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

public class ep3_vryyyr extends script.base_script
{
    public ep3_vryyyr()
    {
    }
    public static String c_stringFile = "conversation/ep3_vryyyr";
    public boolean ep3_vryyyr_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean ep3_vryyyr_condition_knows_Shiryyywook(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.canSpeakWookiee(player, npc);
    }
    public boolean ep3_vryyyr_condition_is_Trandoshan(obj_id player, obj_id npc) throws InterruptedException
    {
        int plrSpecies = getSpecies(player);
        return (plrSpecies == SPECIES_TRANDOSHAN);
    }
    public void ep3_vryyyr_action_growl(obj_id player, obj_id npc) throws InterruptedException
    {
        playClientEffectObj(npc, "clienteffect/voc_wookiee_loud_6sec.cef", player, "");
    }
    public void ep3_vryyyr_action_medium_talk(obj_id player, obj_id npc) throws InterruptedException
    {
        playClientEffectObj(npc, "clienteffect/voc_wookiee_loud_2sec.cef", player, "");
    }
    public int ep3_vryyyr_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_456"))
        {
            if (ep3_vryyyr_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_458");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_vryyyr_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_vryyyr_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (ep3_vryyyr_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_460");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_468");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_480");
                    }
                    utils.setScriptVar(player, "conversation.ep3_vryyyr.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_vryyyr.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_484"))
        {
            if (ep3_vryyyr_condition__defaultCondition(player, npc))
            {
                ep3_vryyyr_action_growl(player, npc);
                string_id message = new string_id(c_stringFile, "s_486");
                utils.removeScriptVar(player, "conversation.ep3_vryyyr.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_488"))
        {
            if (ep3_vryyyr_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_490");
                utils.removeScriptVar(player, "conversation.ep3_vryyyr.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_vryyyr_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_460"))
        {
            if (ep3_vryyyr_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_462");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_vryyyr_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_464");
                    }
                    utils.setScriptVar(player, "conversation.ep3_vryyyr.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_vryyyr.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_468"))
        {
            if (ep3_vryyyr_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_470");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_vryyyr_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_472");
                    }
                    utils.setScriptVar(player, "conversation.ep3_vryyyr.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_vryyyr.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_480"))
        {
            if (ep3_vryyyr_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_482");
                utils.removeScriptVar(player, "conversation.ep3_vryyyr.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_vryyyr_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_464"))
        {
            if (ep3_vryyyr_condition__defaultCondition(player, npc))
            {
                ep3_vryyyr_action_medium_talk(player, npc);
                string_id message = new string_id(c_stringFile, "s_466");
                utils.removeScriptVar(player, "conversation.ep3_vryyyr.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_vryyyr_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_472"))
        {
            if (ep3_vryyyr_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_474");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_vryyyr_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_476");
                    }
                    utils.setScriptVar(player, "conversation.ep3_vryyyr.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_vryyyr.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_vryyyr_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_476"))
        {
            if (ep3_vryyyr_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_478");
                utils.removeScriptVar(player, "conversation.ep3_vryyyr.branchId");
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
            detachScript(self, "conversation.ep3_vryyyr");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
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
        detachScript(self, "conversation.ep3_vryyyr");
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
        if (ep3_vryyyr_condition_is_Trandoshan(player, npc))
        {
            ep3_vryyyr_action_growl(player, npc);
            string_id message = new string_id(c_stringFile, "s_450");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_vryyyr_condition_knows_Shiryyywook(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_452");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_vryyyr_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_454");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_vryyyr_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_vryyyr_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (ep3_vryyyr_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_456");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_484");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_488");
                }
                utils.setScriptVar(player, "conversation.ep3_vryyyr.branchId", 3);
                npcStartConversation(player, npc, "ep3_vryyyr", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("ep3_vryyyr"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.ep3_vryyyr.branchId");
        if (branchId == 3 && ep3_vryyyr_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && ep3_vryyyr_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && ep3_vryyyr_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && ep3_vryyyr_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && ep3_vryyyr_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.ep3_vryyyr.branchId");
        return SCRIPT_CONTINUE;
    }
}
