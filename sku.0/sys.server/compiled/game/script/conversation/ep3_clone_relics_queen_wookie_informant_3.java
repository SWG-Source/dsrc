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
import script.library.groundquests;
import script.library.space_flags;
import script.library.space_quest;
import script.library.utils;

public class ep3_clone_relics_queen_wookie_informant_3 extends script.base_script
{
    public ep3_clone_relics_queen_wookie_informant_3()
    {
    }
    public static String c_stringFile = "conversation/ep3_clone_relics_queen_wookie_informant_3";
    public boolean ep3_clone_relics_queen_wookie_informant_3_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean ep3_clone_relics_queen_wookie_informant_3_condition_isWookie(obj_id player, obj_id npc) throws InterruptedException
    {
        return (getSpecies(player) == SPECIES_WOOKIEE);
    }
    public boolean ep3_clone_relics_queen_wookie_informant_3_condition_onQuestTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isQuestActive(player, "ep3_clone_relics_queen_2"));
    }
    public boolean ep3_clone_relics_queen_wookie_informant_3_condition_onSpaceMission(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasQuest(player, "space_battle", "ep3_clone_relics_queen_3"));
    }
    public boolean ep3_clone_relics_queen_wookie_informant_3_condition_knowWookie(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.canSpeakWookiee(player, npc);
    }
    public boolean ep3_clone_relics_queen_wookie_informant_3_condition_foundInformants(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "ep3_clone_relics_queen_2", "foundInformants"));
    }
    public void ep3_clone_relics_queen_wookie_informant_3_action_signalTalked(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "talkedToPrisonWookie");
    }
    public void ep3_clone_relics_queen_wookie_informant_3_action_dontUnderstand(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.emoteWookieeConfusion(player, npc);
    }
    public int ep3_clone_relics_queen_wookie_informant_3_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_82"))
        {
            doAnimationAction(player, "nod");
            if (ep3_clone_relics_queen_wookie_informant_3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_86");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_queen_wookie_informant_3.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_queen_wookie_informant_3_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_94"))
        {
            if (ep3_clone_relics_queen_wookie_informant_3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_no");
                string_id message = new string_id(c_stringFile, "s_98");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_queen_wookie_informant_3.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_queen_wookie_informant_3_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_106"))
        {
            if (ep3_clone_relics_queen_wookie_informant_3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_shoulders");
                string_id message = new string_id(c_stringFile, "s_110");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_queen_wookie_informant_3.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_queen_wookie_informant_3_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_118"))
        {
            doAnimationAction(player, "belly_laugh");
            if (ep3_clone_relics_queen_wookie_informant_3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "cuckoo");
                string_id message = new string_id(c_stringFile, "s_121");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_queen_wookie_informant_3.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.ep3_clone_relics_queen_wookie_informant_3");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setName(self, new string_id("ep3/npc_names", "clone_relics_orrekazzapirr"));
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setName(self, new string_id("ep3/npc_names", "clone_relics_orrekazzapirr"));
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
        detachScript(self, "conversation.ep3_clone_relics_queen_wookie_informant_3");
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
        if (ep3_clone_relics_queen_wookie_informant_3_condition_knowWookie(player, npc))
        {
            doAnimationAction(npc, "nervous");
            string_id message = new string_id(c_stringFile, "s_76");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_clone_relics_queen_wookie_informant_3_condition_onSpaceMission(player, npc))
        {
            doAnimationAction(npc, "nervous");
            string_id message = new string_id(c_stringFile, "s_78");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_clone_relics_queen_wookie_informant_3_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_82");
                }
                utils.setScriptVar(player, "conversation.ep3_clone_relics_queen_wookie_informant_3.branchId", 2);
                npcStartConversation(player, npc, "ep3_clone_relics_queen_wookie_informant_3", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_clone_relics_queen_wookie_informant_3_condition_foundInformants(player, npc))
        {
            doAnimationAction(npc, "nervous");
            string_id message = new string_id(c_stringFile, "s_90");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_clone_relics_queen_wookie_informant_3_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_94");
                }
                utils.setScriptVar(player, "conversation.ep3_clone_relics_queen_wookie_informant_3.branchId", 4);
                npcStartConversation(player, npc, "ep3_clone_relics_queen_wookie_informant_3", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_clone_relics_queen_wookie_informant_3_condition_onQuestTwo(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_102");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_clone_relics_queen_wookie_informant_3_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_106");
                }
                utils.setScriptVar(player, "conversation.ep3_clone_relics_queen_wookie_informant_3.branchId", 6);
                npcStartConversation(player, npc, "ep3_clone_relics_queen_wookie_informant_3", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_clone_relics_queen_wookie_informant_3_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "nervous");
            string_id message = new string_id(c_stringFile, "s_114");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_clone_relics_queen_wookie_informant_3_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_118");
                }
                utils.setScriptVar(player, "conversation.ep3_clone_relics_queen_wookie_informant_3.branchId", 8);
                npcStartConversation(player, npc, "ep3_clone_relics_queen_wookie_informant_3", message, responses);
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
        if (!conversationId.equals("ep3_clone_relics_queen_wookie_informant_3"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.ep3_clone_relics_queen_wookie_informant_3.branchId");
        if (branchId == 2 && ep3_clone_relics_queen_wookie_informant_3_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && ep3_clone_relics_queen_wookie_informant_3_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && ep3_clone_relics_queen_wookie_informant_3_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && ep3_clone_relics_queen_wookie_informant_3_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.ep3_clone_relics_queen_wookie_informant_3.branchId");
        return SCRIPT_CONTINUE;
    }
}
