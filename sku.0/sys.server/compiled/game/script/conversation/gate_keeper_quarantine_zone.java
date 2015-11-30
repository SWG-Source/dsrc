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

public class gate_keeper_quarantine_zone extends script.base_script
{
    public gate_keeper_quarantine_zone()
    {
    }
    public static String c_stringFile = "conversation/gate_keeper_quarantine_zone";
    public boolean gate_keeper_quarantine_zone_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean gate_keeper_quarantine_zone_condition_hasQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        return (groundquests.isQuestActive(player, "outbreak_quest_01_imperial") || groundquests.isQuestActive(player, "outbreak_quest_01_neutral") || groundquests.isQuestActive(player, "outbreak_quest_01_rebel") || isGod(player));
    }
    public boolean gate_keeper_quarantine_zone_condition_hasCompletedQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        return (groundquests.hasCompletedQuest(player, "quest/outbreak_quest_01_imperial") || groundquests.hasCompletedQuest(player, "quest/outbreak_quest_01_rebel") || groundquests.hasCompletedQuest(player, "quest/outbreak_quest_01_neutral"));
    }
    public boolean gate_keeper_quarantine_zone_condition_hasQuestTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        return (groundquests.isQuestActive(player, "quest/outbreak_quest_02_imperial") || groundquests.isQuestActive(player, "quest/outbreak_quest_02_rebel") || groundquests.isQuestActive(player, "quest/outbreak_quest_02_neutral"));
    }
    public boolean gate_keeper_quarantine_zone_condition_hasFoundContingent(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        return (groundquests.hasCompletedQuest(player, "outbreak_quest_02_imeprial") || groundquests.hasCompletedQuest(player, "outbreak_quest_02_neutral") || groundquests.hasCompletedQuest(player, "outbreak_quest_02_rebel"));
    }
    public void gate_keeper_quarantine_zone_action_warpPlayerIn(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "outbreak_quest_01_imperial", "goEntrance") || groundquests.isTaskActive(player, "outbreak_quest_01_rebel", "goEntrance") || groundquests.isTaskActive(player, "outbreak_quest_01_neutral", "goEntrance"))
        {
            groundquests.sendSignal(player, "gainedAccess");
        }
        messageTo(player, "death_troopers_apply_virus", null, 10.0f, false);
        setObjVar(player, "outbreak.usedGate", 1);
        buff.applyBuff(player, "death_troopers_no_vehicle");
        warpPlayer(player, "dathomir", -5786, 510, -6556, null, -5786, 510, -6556, null, true);
    }
    public int gate_keeper_quarantine_zone_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_28"))
        {
            if (gate_keeper_quarantine_zone_condition__defaultCondition(player, npc))
            {
                gate_keeper_quarantine_zone_action_warpPlayerIn(player, npc);
                string_id message = new string_id(c_stringFile, "s_26");
                utils.removeScriptVar(player, "conversation.gate_keeper_quarantine_zone.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int gate_keeper_quarantine_zone_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_22"))
        {
            if (gate_keeper_quarantine_zone_condition__defaultCondition(player, npc))
            {
                gate_keeper_quarantine_zone_action_warpPlayerIn(player, npc);
                string_id message = new string_id(c_stringFile, "s_26");
                utils.removeScriptVar(player, "conversation.gate_keeper_quarantine_zone.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int gate_keeper_quarantine_zone_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_20"))
        {
            if (gate_keeper_quarantine_zone_condition__defaultCondition(player, npc))
            {
                gate_keeper_quarantine_zone_action_warpPlayerIn(player, npc);
                string_id message = new string_id(c_stringFile, "s_26");
                utils.removeScriptVar(player, "conversation.gate_keeper_quarantine_zone.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int gate_keeper_quarantine_zone_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_12"))
        {
            if (gate_keeper_quarantine_zone_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_14");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (gate_keeper_quarantine_zone_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_16");
                    }
                    utils.setScriptVar(player, "conversation.gate_keeper_quarantine_zone.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.gate_keeper_quarantine_zone.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int gate_keeper_quarantine_zone_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_16"))
        {
            if (gate_keeper_quarantine_zone_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_19");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (gate_keeper_quarantine_zone_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.gate_keeper_quarantine_zone.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.gate_keeper_quarantine_zone.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int gate_keeper_quarantine_zone_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_24"))
        {
            if (gate_keeper_quarantine_zone_condition__defaultCondition(player, npc))
            {
                gate_keeper_quarantine_zone_action_warpPlayerIn(player, npc);
                string_id message = new string_id(c_stringFile, "s_26");
                utils.removeScriptVar(player, "conversation.gate_keeper_quarantine_zone.branchId");
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
            detachScript(self, "conversation.gate_keeper_quarantine_zone");
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
        detachScript(self, "conversation.gate_keeper_quarantine_zone");
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
        if (gate_keeper_quarantine_zone_condition_hasFoundContingent(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_27");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (gate_keeper_quarantine_zone_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_28");
                }
                utils.setScriptVar(player, "conversation.gate_keeper_quarantine_zone.branchId", 1);
                npcStartConversation(player, npc, "gate_keeper_quarantine_zone", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (gate_keeper_quarantine_zone_condition_hasQuestTwo(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_21");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (gate_keeper_quarantine_zone_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.gate_keeper_quarantine_zone.branchId", 2);
                npcStartConversation(player, npc, "gate_keeper_quarantine_zone", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (gate_keeper_quarantine_zone_condition_hasCompletedQuestOne(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_18");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (gate_keeper_quarantine_zone_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.gate_keeper_quarantine_zone.branchId", 3);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "gate_keeper_quarantine_zone", null, pp, responses);
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
        if (gate_keeper_quarantine_zone_condition_hasQuestOne(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_10");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (gate_keeper_quarantine_zone_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_12");
                }
                utils.setScriptVar(player, "conversation.gate_keeper_quarantine_zone.branchId", 4);
                npcStartConversation(player, npc, "gate_keeper_quarantine_zone", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (gate_keeper_quarantine_zone_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "shoo");
            string_id message = new string_id(c_stringFile, "s_30");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("gate_keeper_quarantine_zone"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.gate_keeper_quarantine_zone.branchId");
        if (branchId == 1 && gate_keeper_quarantine_zone_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && gate_keeper_quarantine_zone_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && gate_keeper_quarantine_zone_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && gate_keeper_quarantine_zone_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && gate_keeper_quarantine_zone_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && gate_keeper_quarantine_zone_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.gate_keeper_quarantine_zone.branchId");
        return SCRIPT_CONTINUE;
    }
}
