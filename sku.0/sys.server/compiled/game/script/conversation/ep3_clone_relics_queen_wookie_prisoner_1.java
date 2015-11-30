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
import script.library.utils;

public class ep3_clone_relics_queen_wookie_prisoner_1 extends script.base_script
{
    public ep3_clone_relics_queen_wookie_prisoner_1()
    {
    }
    public static String c_stringFile = "conversation/ep3_clone_relics_queen_wookie_prisoner_1";
    public boolean ep3_clone_relics_queen_wookie_prisoner_1_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean ep3_clone_relics_queen_wookie_prisoner_1_condition_isWookie(obj_id player, obj_id npc) throws InterruptedException
    {
        return (getSpecies(player) == SPECIES_WOOKIEE);
    }
    public boolean ep3_clone_relics_queen_wookie_prisoner_1_condition_knowWookiee(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.canSpeakWookiee(player, npc);
    }
    public boolean ep3_clone_relics_queen_wookie_prisoner_1_condition_talkPrisonersActive(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "ep3_clone_relics_queen_2", "talkedPrisoners"));
    }
    public void ep3_clone_relics_queen_wookie_prisoner_1_action_signalTalked(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "talkedToPrisoners");
    }
    public void ep3_clone_relics_queen_wookie_prisoner_1_action_cantUnderstand(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.emoteWookieeConfusion(player, npc);
    }
    public int ep3_clone_relics_queen_wookie_prisoner_1_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_152"))
        {
            if (ep3_clone_relics_queen_wookie_prisoner_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "slump_head");
                string_id message = new string_id(c_stringFile, "s_154");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_queen_wookie_prisoner_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_156");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_queen_wookie_prisoner_1.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_queen_wookie_prisoner_1.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_queen_wookie_prisoner_1_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_156"))
        {
            doAnimationAction(player, "pound_fist_palm");
            ep3_clone_relics_queen_wookie_prisoner_1_action_signalTalked(player, npc);
            if (ep3_clone_relics_queen_wookie_prisoner_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "goodbye");
                string_id message = new string_id(c_stringFile, "s_158");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_queen_wookie_prisoner_1.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_queen_wookie_prisoner_1_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_162"))
        {
            if (ep3_clone_relics_queen_wookie_prisoner_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "slump_head");
                string_id message = new string_id(c_stringFile, "s_164");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_queen_wookie_prisoner_1.branchId");
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
            detachScript(self, "conversation.ep3_clone_relics_queen_wookie_prisoner_1");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setName(self, new string_id("ep3/npc_names", "clone_relics_grrallo"));
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setName(self, new string_id("ep3/npc_names", "clone_relics_grrallo"));
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
        detachScript(self, "conversation.ep3_clone_relics_queen_wookie_prisoner_1");
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
        if (ep3_clone_relics_queen_wookie_prisoner_1_condition_knowWookiee(player, npc))
        {
            doAnimationAction(npc, "nervous");
            ep3_clone_relics_queen_wookie_prisoner_1_action_cantUnderstand(player, npc);
            string_id message = new string_id(c_stringFile, "s_124");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_clone_relics_queen_wookie_prisoner_1_condition_talkPrisonersActive(player, npc))
        {
            doAnimationAction(npc, "nervous");
            string_id message = new string_id(c_stringFile, "s_150");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_clone_relics_queen_wookie_prisoner_1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_152");
                }
                utils.setScriptVar(player, "conversation.ep3_clone_relics_queen_wookie_prisoner_1.branchId", 2);
                npcStartConversation(player, npc, "ep3_clone_relics_queen_wookie_prisoner_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_clone_relics_queen_wookie_prisoner_1_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "helpme");
            string_id message = new string_id(c_stringFile, "s_160");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_clone_relics_queen_wookie_prisoner_1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_162");
                }
                utils.setScriptVar(player, "conversation.ep3_clone_relics_queen_wookie_prisoner_1.branchId", 5);
                npcStartConversation(player, npc, "ep3_clone_relics_queen_wookie_prisoner_1", message, responses);
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
        if (!conversationId.equals("ep3_clone_relics_queen_wookie_prisoner_1"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.ep3_clone_relics_queen_wookie_prisoner_1.branchId");
        if (branchId == 2 && ep3_clone_relics_queen_wookie_prisoner_1_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && ep3_clone_relics_queen_wookie_prisoner_1_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && ep3_clone_relics_queen_wookie_prisoner_1_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.ep3_clone_relics_queen_wookie_prisoner_1.branchId");
        return SCRIPT_CONTINUE;
    }
}
