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

public class stormtrooper_leader_quarantine_zone extends script.base_script
{
    public stormtrooper_leader_quarantine_zone()
    {
    }
    public static String c_stringFile = "conversation/stormtrooper_leader_quarantine_zone";
    public boolean stormtrooper_leader_quarantine_zone_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean stormtrooper_leader_quarantine_zone_condition_hasQuestTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        return (groundquests.isQuestActive(player, "quest/outbreak_quest_02_imperial") || groundquests.isQuestActive(player, "quest/outbreak_quest_02_rebel") || groundquests.isQuestActive(player, "quest/outbreak_quest_02_neutral"));
    }
    public boolean stormtrooper_leader_quarantine_zone_condition_hasCompletedQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        return (groundquests.hasCompletedQuest(player, "quest/outbreak_quest_01_imperial") || groundquests.hasCompletedQuest(player, "quest/outbreak_quest_01_rebel") || groundquests.hasCompletedQuest(player, "quest/outbreak_quest_01_neutral"));
    }
    public boolean stormtrooper_leader_quarantine_zone_condition_hasQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        return (groundquests.isQuestActive(player, "quest/outbreak_quest_01_imperial") || groundquests.isQuestActive(player, "quest/outbreak_quest_01_neutral") || groundquests.isQuestActive(player, "quest/outbreak_quest_01_rebel"));
    }
    public boolean stormtrooper_leader_quarantine_zone_condition_hasFoundContingent(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        return (groundquests.hasCompletedQuest(player, "outbreak_quest_02_imeprial") || groundquests.hasCompletedQuest(player, "outbreak_quest_02_neutral") || groundquests.hasCompletedQuest(player, "outbreak_quest_02_rebel"));
    }
    public void stormtrooper_leader_quarantine_zone_action_moveAlongSound(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        play2dNonLoopingMusic(player, "sound/voice_stormtrooper_move_along.snd");
    }
    public void stormtrooper_leader_quarantine_zone_action_warpPlayerOut(obj_id player, obj_id npc) throws InterruptedException
    {
        removeObjVar(player, "outbreak.usedGate");
        buff.removeBuff(player, "death_troopers_no_vehicle");
        warpPlayer(player, "dathomir", -5777, 510, -6546, null, -5777, 510, -6546, null, true);
    }
    public int stormtrooper_leader_quarantine_zone_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_32"))
        {
            if (stormtrooper_leader_quarantine_zone_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_33");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (stormtrooper_leader_quarantine_zone_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_35");
                    }
                    utils.setScriptVar(player, "conversation.stormtrooper_leader_quarantine_zone.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.stormtrooper_leader_quarantine_zone.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_34"))
        {
            if (stormtrooper_leader_quarantine_zone_condition__defaultCondition(player, npc))
            {
                stormtrooper_leader_quarantine_zone_action_warpPlayerOut(player, npc);
                string_id message = new string_id(c_stringFile, "s_29");
                utils.removeScriptVar(player, "conversation.stormtrooper_leader_quarantine_zone.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int stormtrooper_leader_quarantine_zone_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_35"))
        {
            if (stormtrooper_leader_quarantine_zone_condition__defaultCondition(player, npc))
            {
                stormtrooper_leader_quarantine_zone_action_warpPlayerOut(player, npc);
                string_id message = new string_id(c_stringFile, "s_29");
                utils.removeScriptVar(player, "conversation.stormtrooper_leader_quarantine_zone.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int stormtrooper_leader_quarantine_zone_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_24"))
        {
            if (stormtrooper_leader_quarantine_zone_condition__defaultCondition(player, npc))
            {
                stormtrooper_leader_quarantine_zone_action_warpPlayerOut(player, npc);
                string_id message = new string_id(c_stringFile, "s_29");
                utils.removeScriptVar(player, "conversation.stormtrooper_leader_quarantine_zone.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int stormtrooper_leader_quarantine_zone_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_20"))
        {
            if (stormtrooper_leader_quarantine_zone_condition__defaultCondition(player, npc))
            {
                stormtrooper_leader_quarantine_zone_action_warpPlayerOut(player, npc);
                string_id message = new string_id(c_stringFile, "s_29");
                utils.removeScriptVar(player, "conversation.stormtrooper_leader_quarantine_zone.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int stormtrooper_leader_quarantine_zone_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_15"))
        {
            if (stormtrooper_leader_quarantine_zone_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_17");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (stormtrooper_leader_quarantine_zone_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_22");
                    }
                    utils.setScriptVar(player, "conversation.stormtrooper_leader_quarantine_zone.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.stormtrooper_leader_quarantine_zone.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int stormtrooper_leader_quarantine_zone_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_22"))
        {
            if (stormtrooper_leader_quarantine_zone_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_25");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (stormtrooper_leader_quarantine_zone_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_27");
                    }
                    utils.setScriptVar(player, "conversation.stormtrooper_leader_quarantine_zone.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.stormtrooper_leader_quarantine_zone.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int stormtrooper_leader_quarantine_zone_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_27"))
        {
            if (stormtrooper_leader_quarantine_zone_condition__defaultCondition(player, npc))
            {
                stormtrooper_leader_quarantine_zone_action_warpPlayerOut(player, npc);
                string_id message = new string_id(c_stringFile, "s_29");
                utils.removeScriptVar(player, "conversation.stormtrooper_leader_quarantine_zone.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int stormtrooper_leader_quarantine_zone_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_38"))
        {
            if (stormtrooper_leader_quarantine_zone_condition__defaultCondition(player, npc))
            {
                stormtrooper_leader_quarantine_zone_action_warpPlayerOut(player, npc);
                string_id message = new string_id(c_stringFile, "s_29");
                utils.removeScriptVar(player, "conversation.stormtrooper_leader_quarantine_zone.branchId");
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
            detachScript(self, "conversation.stormtrooper_leader_quarantine_zone");
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
        detachScript(self, "conversation.stormtrooper_leader_quarantine_zone");
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
        if (stormtrooper_leader_quarantine_zone_condition_hasFoundContingent(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_31");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (stormtrooper_leader_quarantine_zone_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (stormtrooper_leader_quarantine_zone_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_32");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_34");
                }
                utils.setScriptVar(player, "conversation.stormtrooper_leader_quarantine_zone.branchId", 1);
                npcStartConversation(player, npc, "stormtrooper_leader_quarantine_zone", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (stormtrooper_leader_quarantine_zone_condition_hasQuestTwo(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_21");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (stormtrooper_leader_quarantine_zone_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_24");
                }
                utils.setScriptVar(player, "conversation.stormtrooper_leader_quarantine_zone.branchId", 3);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "stormtrooper_leader_quarantine_zone", null, pp, responses);
            }
            else 
            {
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                chat.chat(npc, player, null, null, pp);
            }
            return SCRIPT_CONTINUE;
        }
        if (stormtrooper_leader_quarantine_zone_condition_hasCompletedQuestOne(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_18");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (stormtrooper_leader_quarantine_zone_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_20");
                }
                utils.setScriptVar(player, "conversation.stormtrooper_leader_quarantine_zone.branchId", 4);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "stormtrooper_leader_quarantine_zone", null, pp, responses);
            }
            else 
            {
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                chat.chat(npc, player, null, null, pp);
            }
            return SCRIPT_CONTINUE;
        }
        if (stormtrooper_leader_quarantine_zone_condition_hasQuestOne(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_13");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (stormtrooper_leader_quarantine_zone_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_15");
                }
                utils.setScriptVar(player, "conversation.stormtrooper_leader_quarantine_zone.branchId", 5);
                npcStartConversation(player, npc, "stormtrooper_leader_quarantine_zone", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (stormtrooper_leader_quarantine_zone_condition__defaultCondition(player, npc))
        {
            stormtrooper_leader_quarantine_zone_action_moveAlongSound(player, npc);
            string_id message = new string_id(c_stringFile, "s_36");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (stormtrooper_leader_quarantine_zone_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_38");
                }
                utils.setScriptVar(player, "conversation.stormtrooper_leader_quarantine_zone.branchId", 9);
                npcStartConversation(player, npc, "stormtrooper_leader_quarantine_zone", message, responses);
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
        if (!conversationId.equals("stormtrooper_leader_quarantine_zone"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.stormtrooper_leader_quarantine_zone.branchId");
        if (branchId == 1 && stormtrooper_leader_quarantine_zone_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && stormtrooper_leader_quarantine_zone_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && stormtrooper_leader_quarantine_zone_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && stormtrooper_leader_quarantine_zone_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && stormtrooper_leader_quarantine_zone_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && stormtrooper_leader_quarantine_zone_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && stormtrooper_leader_quarantine_zone_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && stormtrooper_leader_quarantine_zone_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.stormtrooper_leader_quarantine_zone.branchId");
        return SCRIPT_CONTINUE;
    }
}
