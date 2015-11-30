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

public class ep3_mining_captain_koh extends script.base_script
{
    public ep3_mining_captain_koh()
    {
    }
    public static String c_stringFile = "conversation/ep3_mining_captain_koh";
    public boolean ep3_mining_captain_koh_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean ep3_mining_captain_koh_condition_hasQuestFour(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "ep3_mining_quest_4");
    }
    public boolean ep3_mining_captain_koh_condition_isQuest5Active(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasQuest(player, "space_mining_destroy", "kashyyyk_mining_quest_5");
    }
    public boolean ep3_mining_captain_koh_condition_hasCompletedQuest5(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasWonQuest(player, "space_mining_destroy", "kashyyyk_mining_quest_5") && !space_quest.hasReceivedReward(player, "space_mining_destroy", "kashyyyk_mining_quest_5"));
    }
    public boolean ep3_mining_captain_koh_condition_isReadyForQuest5(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedQuest(player, "ep3_mining_quest_4") && !space_quest.hasQuest(player, "space_mining_destroy", "kashyyyk_mining_quest_5") && !space_quest.hasWonQuest(player, "space_mining_destroy", "kashyyyk_mining_quest_5"));
    }
    public boolean ep3_mining_captain_koh_condition_isReadyForQuest6(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasReceivedReward(player, "space_mining_destroy", "kashyyyk_mining_quest_5") && !space_quest.hasQuest(player, "space_mining_destroy", "lok_mining_quest_6") && !space_quest.hasWonQuest(player, "space_mining_destroy", "lok_mining_quest_6"));
    }
    public boolean ep3_mining_captain_koh_condition_isQuest6Active(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasQuest(player, "space_mining_destroy", "lok_mining_quest_6");
    }
    public boolean ep3_mining_captain_koh_condition_hasCompletedQuest6(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasWonQuest(player, "space_mining_destroy", "lok_mining_quest_6") && !space_quest.hasReceivedReward(player, "space_mining_destroy", "lok_mining_quest_6"));
    }
    public boolean ep3_mining_captain_koh_condition_isReadyForQuest7(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasReceivedReward(player, "space_mining_destroy", "lok_mining_quest_6") && !space_quest.hasQuest(player, "salvage", "corellia_mining_quest_7") && !space_quest.hasWonQuest(player, "salvage", "corellia_mining_quest_7"));
    }
    public boolean ep3_mining_captain_koh_condition_isQuest7Active(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasQuest(player, "salvage", "corellia_mining_quest_7");
    }
    public boolean ep3_mining_captain_koh_condition_hasCompletedQuest7(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasWonQuest(player, "salvage", "corellia_mining_quest_7") && !space_quest.hasReceivedReward(player, "salvage", "corellia_mining_quest_7"));
    }
    public boolean ep3_mining_captain_koh_condition_isReadyForQuest8(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasReceivedReward(player, "salvage", "corellia_mining_quest_7") && !space_quest.hasQuest(player, "space_mining_destroy", "kashyyyk_mining_quest_8") && !space_quest.hasWonQuest(player, "space_mining_destroy", "kashyyyk_mining_quest_8"));
    }
    public boolean ep3_mining_captain_koh_condition_isQuest8Active(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasQuest(player, "space_mining_destroy", "kashyyyk_mining_quest_8") || space_quest.hasQuest(player, "space_mining_destroy", "endor_mining_quest_9") || space_quest.hasQuest(player, "space_mining_destroy", "dathomir_mining_quest_10"));
    }
    public boolean ep3_mining_captain_koh_condition_hasCompletedQuest8(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasWonQuest(player, "space_mining_destroy", "dathomir_mining_quest_10") && !space_quest.hasReceivedReward(player, "space_mining_destroy", "dathomir_mining_quest_10"));
    }
    public boolean ep3_mining_captain_koh_condition_isReadyForQuest9(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasReceivedReward(player, "space_mining_destroy", "dathomir_mining_quest_10") && !space_quest.hasQuest(player, "convoy", "kashyyyk_mining_quest_11") && !space_quest.hasWonQuest(player, "convoy", "kashyyyk_mining_quest_11"));
    }
    public boolean ep3_mining_captain_koh_condition_isQuest9Active(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasQuest(player, "convoy", "kashyyyk_mining_quest_11");
    }
    public boolean ep3_mining_captain_koh_condition_hasCompletedQuest9(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasWonQuest(player, "convoy", "kashyyyk_mining_quest_11") && !space_quest.hasReceivedReward(player, "convoy", "kashyyyk_mining_quest_11"));
    }
    public boolean ep3_mining_captain_koh_condition_isReadyForQuest10(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasReceivedReward(player, "convoy", "kashyyyk_mining_quest_11") && !space_quest.hasQuest(player, "assassinate", "kashyyyk_mining_quest_12") && !space_quest.hasWonQuest(player, "assassinate", "kashyyyk_mining_quest_12"));
    }
    public boolean ep3_mining_captain_koh_condition_isQuest10Active(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasQuest(player, "assassinate", "kashyyyk_mining_quest_12");
    }
    public boolean ep3_mining_captain_koh_condition_hasCompletedQuest10(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasWonQuest(player, "assassinate", "kashyyyk_mining_quest_12") && !space_quest.hasReceivedReward(player, "assassinate", "kashyyyk_mining_quest_12"));
    }
    public boolean ep3_mining_captain_koh_condition_isReadyForQuest11(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasReceivedReward(player, "assassinate", "kashyyyk_mining_quest_12") && !space_quest.hasQuest(player, "space_mining_destroy", "kessel_mining_quest_13") && !space_quest.hasWonQuest(player, "space_mining_destroy", "kessel_mining_quest_13"));
    }
    public boolean ep3_mining_captain_koh_condition_isQuest11Active(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasQuest(player, "space_mining_destroy", "kessel_mining_quest_13");
    }
    public boolean ep3_mining_captain_koh_condition_hasCompletedQuest11(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasWonQuest(player, "space_mining_destroy", "kessel_mining_quest_13") && !space_quest.hasReceivedReward(player, "space_mining_destroy", "kessel_mining_quest_13"));
    }
    public boolean ep3_mining_captain_koh_condition_hasFailedQuest5(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasAbortedQuest(player, "space_mining_destroy", "kashyyyk_mining_quest_5") || space_quest.hasFailedQuest(player, "space_mining_destroy", "kashyyyk_mining_quest_5"))
        {
            if (!space_quest.hasWonQuest(player, "space_mining_destroy", "kashyyyk_mining_quest_5"))
            {
                return true;
            }
        }
        return false;
    }
    public boolean ep3_mining_captain_koh_condition_hasFailedQuest6(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasAbortedQuest(player, "space_mining_destroy", "lok_mining_quest_6") || space_quest.hasFailedQuest(player, "space_mining_destroy", "lok_mining_quest_6"))
        {
            if (!space_quest.hasWonQuest(player, "space_mining_destroy", "lok_mining_quest_6"))
            {
                return true;
            }
        }
        return false;
    }
    public boolean ep3_mining_captain_koh_condition_hasFailedQuest7(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasAbortedQuest(player, "salvage", "corellia_mining_quest_7") || space_quest.hasFailedQuest(player, "salvage", "corellia_mining_quest_7"))
        {
            if (!space_quest.hasWonQuest(player, "salvage", "corellia_mining_quest_7"))
            {
                return true;
            }
        }
        return false;
    }
    public boolean ep3_mining_captain_koh_condition_hasFailedQuest8(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasAbortedQuest(player, "space_mining_destroy", "kashyyyk_mining_quest_8") || space_quest.hasFailedQuest(player, "space_mining_destroy", "kashyyyk_mining_quest_8"))
        {
            if (!space_quest.hasWonQuest(player, "space_mining_destroy", "kashyyyk_mining_quest_8"))
            {
                return true;
            }
        }
        if (space_quest.hasAbortedQuest(player, "space_mining_destroy", "endor_mining_quest_9") || space_quest.hasFailedQuest(player, "space_mining_destroy", "endor_mining_quest_9"))
        {
            if (!space_quest.hasWonQuest(player, "space_mining_destroy", "endor_mining_quest_9"))
            {
                return true;
            }
        }
        if (space_quest.hasAbortedQuest(player, "space_mining_destroy", "dathomir_mining_quest_10") || space_quest.hasFailedQuest(player, "space_mining_destroy", "dathomir_mining_quest_10"))
        {
            if (!space_quest.hasWonQuest(player, "space_mining_destroy", "dathomir_mining_quest_10"))
            {
                return true;
            }
        }
        return false;
    }
    public boolean ep3_mining_captain_koh_condition_hasFailedQuest9(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasAbortedQuest(player, "convoy", "kashyyyk_mining_quest_11") || space_quest.hasFailedQuest(player, "convoy", "kashyyyk_mining_quest_11"))
        {
            if (!space_quest.hasWonQuest(player, "convoy", "kashyyyk_mining_quest_11"))
            {
                return true;
            }
        }
        return false;
    }
    public boolean ep3_mining_captain_koh_condition_hasFailedQuest10(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasAbortedQuest(player, "assassinate", "kashyyyk_mining_quest_12") || space_quest.hasFailedQuest(player, "assassinate", "kashyyyk_mining_quest_12"))
        {
            if (!space_quest.hasWonQuest(player, "assassinate", "kashyyyk_mining_quest_12"))
            {
                return true;
            }
        }
        return false;
    }
    public boolean ep3_mining_captain_koh_condition_hasFailedQuest11(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasAbortedQuest(player, "space_mining_destroy", "kessel_mining_quest_13") || space_quest.hasFailedQuest(player, "space_mining_destroy", "kessel_mining_quest_13"))
        {
            if (!space_quest.hasWonQuest(player, "space_mining_destroy", "kessel_mining_quest_13"))
            {
                return true;
            }
        }
        return false;
    }
    public void ep3_mining_captain_koh_action_sendSignalToPlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "talkedToKoh");
    }
    public void ep3_mining_captain_koh_action_grantQuest5(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "space_mining_destroy", "kashyyyk_mining_quest_5");
    }
    public void ep3_mining_captain_koh_action_grantReward5(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasReceivedReward(player, "space_mining_destroy", "kashyyyk_mining_quest_5"))
        {
            return;
        }
        space_quest.giveReward(player, "space_mining_destroy", "kashyyyk_mining_quest_5", 5000);
        createObjectInInventoryAllowOverload("object/tangible/ship/components/cargo_hold/crg_starfighter_large.iff", player);
    }
    public void ep3_mining_captain_koh_action_grantQuest6(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "space_mining_destroy", "lok_mining_quest_6");
    }
    public void ep3_mining_captain_koh_action_grantReward6(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasReceivedReward(player, "space_mining_destroy", "lok_mining_quest_6"))
        {
            return;
        }
        space_quest.giveReward(player, "space_mining_destroy", "lok_mining_quest_6", 7500, "object/tangible/ship/components/weapon/wpn_tractor_pulse_gun.iff");
    }
    public void ep3_mining_captain_koh_action_grantQuest7(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "salvage", "corellia_mining_quest_7");
    }
    public void ep3_mining_captain_koh_action_grantReward7(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasReceivedReward(player, "salvage", "corellia_mining_quest_7"))
        {
            return;
        }
        space_quest.giveReward(player, "salvage", "corellia_mining_quest_7", 7500);
    }
    public void ep3_mining_captain_koh_action_grantQuest8(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "space_mining_destroy", "kashyyyk_mining_quest_8");
    }
    public void ep3_mining_captain_koh_action_grantReward8(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasReceivedReward(player, "space_mining_destroy", "dathomir_mining_quest_10"))
        {
            return;
        }
        space_quest.giveReward(player, "space_mining_destroy", "dathomir_mining_quest_10", 10000, "object/tangible/ship/components/weapon/wpn_mining_laser_mk2.iff");
    }
    public void ep3_mining_captain_koh_action_grantQuest9(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "convoy", "kashyyyk_mining_quest_11");
    }
    public void ep3_mining_captain_koh_action_grantReward9(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasReceivedReward(player, "convoy", "kashyyyk_mining_quest_11"))
        {
            return;
        }
        space_quest.giveReward(player, "convoy", "kashyyyk_mining_quest_11", 10000);
    }
    public void ep3_mining_captain_koh_action_grantQuest10(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "assassinate", "kashyyyk_mining_quest_12");
    }
    public void ep3_mining_captain_koh_action_grantReward10(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasReceivedReward(player, "assassinate", "kashyyyk_mining_quest_12"))
        {
            return;
        }
        space_quest.giveReward(player, "assassinate", "kashyyyk_mining_quest_12", 12500);
    }
    public void ep3_mining_captain_koh_action_grantQuest11(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "space_mining_destroy", "kessel_mining_quest_13");
    }
    public void ep3_mining_captain_koh_action_grantReward11(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasReceivedReward(player, "space_mining_destroy", "kessel_mining_quest_13"))
        {
            return;
        }
        space_quest.giveReward(player, "space_mining_destroy", "kessel_mining_quest_13", 15000, "object/tangible/space/special_loot/y8_deed.iff");
        createObjectInInventoryAllowOverload("object/tangible/ship/components/cargo_hold/crg_mining_small.iff", player);
    }
    public int ep3_mining_captain_koh_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_248"))
        {
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
            {
                ep3_mining_captain_koh_action_grantQuest11(player, npc);
                string_id message = new string_id(c_stringFile, "s_249");
                utils.removeScriptVar(player, "conversation.ep3_mining_captain_koh.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_mining_captain_koh_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_204"))
        {
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_205");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_206");
                    }
                    utils.setScriptVar(player, "conversation.ep3_mining_captain_koh.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_mining_captain_koh.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_mining_captain_koh_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_206"))
        {
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
            {
                ep3_mining_captain_koh_action_grantReward11(player, npc);
                string_id message = new string_id(c_stringFile, "s_207");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_208");
                    }
                    utils.setScriptVar(player, "conversation.ep3_mining_captain_koh.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_mining_captain_koh.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_mining_captain_koh_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_208"))
        {
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute1");
                string_id message = new string_id(c_stringFile, "s_209");
                utils.removeScriptVar(player, "conversation.ep3_mining_captain_koh.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_mining_captain_koh_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_194"))
        {
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_195");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_196");
                    }
                    utils.setScriptVar(player, "conversation.ep3_mining_captain_koh.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_mining_captain_koh.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_mining_captain_koh_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_196"))
        {
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_197");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_198");
                    }
                    utils.setScriptVar(player, "conversation.ep3_mining_captain_koh.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_mining_captain_koh.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_mining_captain_koh_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_198"))
        {
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_199");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_200");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_201");
                    }
                    utils.setScriptVar(player, "conversation.ep3_mining_captain_koh.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_mining_captain_koh.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_mining_captain_koh_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_200"))
        {
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute1");
                ep3_mining_captain_koh_action_grantQuest11(player, npc);
                string_id message = new string_id(c_stringFile, "s_202");
                utils.removeScriptVar(player, "conversation.ep3_mining_captain_koh.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_201"))
        {
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_203");
                utils.removeScriptVar(player, "conversation.ep3_mining_captain_koh.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_mining_captain_koh_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_246"))
        {
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
            {
                ep3_mining_captain_koh_action_grantQuest10(player, npc);
                string_id message = new string_id(c_stringFile, "s_247");
                utils.removeScriptVar(player, "conversation.ep3_mining_captain_koh.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_mining_captain_koh_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_170"))
        {
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_171");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_172");
                    }
                    utils.setScriptVar(player, "conversation.ep3_mining_captain_koh.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_mining_captain_koh.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_mining_captain_koh_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_172"))
        {
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
            {
                ep3_mining_captain_koh_action_grantReward10(player, npc);
                string_id message = new string_id(c_stringFile, "s_173");
                utils.removeScriptVar(player, "conversation.ep3_mining_captain_koh.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_mining_captain_koh_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_160"))
        {
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_161");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.ep3_mining_captain_koh.branchId", 21);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_mining_captain_koh.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_mining_captain_koh_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_162"))
        {
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_163");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_164");
                    }
                    utils.setScriptVar(player, "conversation.ep3_mining_captain_koh.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_mining_captain_koh.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_mining_captain_koh_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_164"))
        {
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_165");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_166");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_168");
                    }
                    utils.setScriptVar(player, "conversation.ep3_mining_captain_koh.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_mining_captain_koh.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_mining_captain_koh_handleBranch23(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_166"))
        {
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
            {
                ep3_mining_captain_koh_action_grantQuest10(player, npc);
                string_id message = new string_id(c_stringFile, "s_167");
                utils.removeScriptVar(player, "conversation.ep3_mining_captain_koh.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_168"))
        {
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_169");
                utils.removeScriptVar(player, "conversation.ep3_mining_captain_koh.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_mining_captain_koh_handleBranch26(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_244"))
        {
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
            {
                ep3_mining_captain_koh_action_grantQuest9(player, npc);
                string_id message = new string_id(c_stringFile, "s_245");
                utils.removeScriptVar(player, "conversation.ep3_mining_captain_koh.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_mining_captain_koh_handleBranch28(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_146"))
        {
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
            {
                ep3_mining_captain_koh_action_grantReward9(player, npc);
                string_id message = new string_id(c_stringFile, "s_147");
                utils.removeScriptVar(player, "conversation.ep3_mining_captain_koh.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_mining_captain_koh_handleBranch31(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_142"))
        {
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_143");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_144");
                    }
                    utils.setScriptVar(player, "conversation.ep3_mining_captain_koh.branchId", 32);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_mining_captain_koh.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_mining_captain_koh_handleBranch32(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_144"))
        {
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
            {
                ep3_mining_captain_koh_action_grantQuest9(player, npc);
                string_id message = new string_id(c_stringFile, "s_145");
                utils.removeScriptVar(player, "conversation.ep3_mining_captain_koh.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_mining_captain_koh_handleBranch34(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_242"))
        {
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
            {
                ep3_mining_captain_koh_action_grantQuest8(player, npc);
                string_id message = new string_id(c_stringFile, "s_243");
                utils.removeScriptVar(player, "conversation.ep3_mining_captain_koh.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_mining_captain_koh_handleBranch36(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_119"))
        {
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
            {
                ep3_mining_captain_koh_action_grantReward8(player, npc);
                string_id message = new string_id(c_stringFile, "s_120");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_121");
                    }
                    utils.setScriptVar(player, "conversation.ep3_mining_captain_koh.branchId", 37);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_mining_captain_koh.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_mining_captain_koh_handleBranch37(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_121"))
        {
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_122");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_123");
                    }
                    utils.setScriptVar(player, "conversation.ep3_mining_captain_koh.branchId", 38);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_mining_captain_koh.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_mining_captain_koh_handleBranch38(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_123"))
        {
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_124");
                utils.removeScriptVar(player, "conversation.ep3_mining_captain_koh.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_mining_captain_koh_handleBranch41(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_112"))
        {
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_113");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_114");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_115");
                    }
                    utils.setScriptVar(player, "conversation.ep3_mining_captain_koh.branchId", 42);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_mining_captain_koh.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_mining_captain_koh_handleBranch42(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_114"))
        {
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
            {
                ep3_mining_captain_koh_action_grantQuest8(player, npc);
                string_id message = new string_id(c_stringFile, "s_116");
                utils.removeScriptVar(player, "conversation.ep3_mining_captain_koh.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_115"))
        {
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_118");
                utils.removeScriptVar(player, "conversation.ep3_mining_captain_koh.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_mining_captain_koh_handleBranch45(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_240"))
        {
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
            {
                ep3_mining_captain_koh_action_grantQuest7(player, npc);
                string_id message = new string_id(c_stringFile, "s_241");
                utils.removeScriptVar(player, "conversation.ep3_mining_captain_koh.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_mining_captain_koh_handleBranch47(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_86"))
        {
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
            {
                ep3_mining_captain_koh_action_grantReward7(player, npc);
                string_id message = new string_id(c_stringFile, "s_87");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_88");
                    }
                    utils.setScriptVar(player, "conversation.ep3_mining_captain_koh.branchId", 48);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_mining_captain_koh.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_mining_captain_koh_handleBranch48(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_88"))
        {
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_89");
                utils.removeScriptVar(player, "conversation.ep3_mining_captain_koh.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_mining_captain_koh_handleBranch51(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_92"))
        {
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_94");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_96");
                    }
                    utils.setScriptVar(player, "conversation.ep3_mining_captain_koh.branchId", 52);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_mining_captain_koh.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_mining_captain_koh_handleBranch52(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_96"))
        {
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_98");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_101");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_105");
                    }
                    utils.setScriptVar(player, "conversation.ep3_mining_captain_koh.branchId", 53);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_mining_captain_koh.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_mining_captain_koh_handleBranch53(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_101"))
        {
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
            {
                ep3_mining_captain_koh_action_grantQuest7(player, npc);
                string_id message = new string_id(c_stringFile, "s_103");
                utils.removeScriptVar(player, "conversation.ep3_mining_captain_koh.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_105"))
        {
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_107");
                utils.removeScriptVar(player, "conversation.ep3_mining_captain_koh.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_mining_captain_koh_handleBranch56(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_238"))
        {
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
            {
                ep3_mining_captain_koh_action_grantQuest6(player, npc);
                string_id message = new string_id(c_stringFile, "s_239");
                utils.removeScriptVar(player, "conversation.ep3_mining_captain_koh.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_mining_captain_koh_handleBranch58(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_129"))
        {
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
            {
                ep3_mining_captain_koh_action_grantReward6(player, npc);
                string_id message = new string_id(c_stringFile, "s_131");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_133");
                    }
                    utils.setScriptVar(player, "conversation.ep3_mining_captain_koh.branchId", 59);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_mining_captain_koh.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_mining_captain_koh_handleBranch59(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_133"))
        {
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_135");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_137");
                    }
                    utils.setScriptVar(player, "conversation.ep3_mining_captain_koh.branchId", 60);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_mining_captain_koh.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_mining_captain_koh_handleBranch60(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_137"))
        {
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                string_id message = new string_id(c_stringFile, "s_148");
                utils.removeScriptVar(player, "conversation.ep3_mining_captain_koh.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_mining_captain_koh_handleBranch63(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_154"))
        {
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_156");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_175");
                    }
                    utils.setScriptVar(player, "conversation.ep3_mining_captain_koh.branchId", 64);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_mining_captain_koh.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_mining_captain_koh_handleBranch64(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_175"))
        {
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_177");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_179");
                    }
                    utils.setScriptVar(player, "conversation.ep3_mining_captain_koh.branchId", 65);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_mining_captain_koh.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_mining_captain_koh_handleBranch65(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_179"))
        {
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
            {
                ep3_mining_captain_koh_action_grantQuest6(player, npc);
                string_id message = new string_id(c_stringFile, "s_181");
                utils.removeScriptVar(player, "conversation.ep3_mining_captain_koh.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_mining_captain_koh_handleBranch67(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_236"))
        {
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
            {
                ep3_mining_captain_koh_action_grantQuest5(player, npc);
                string_id message = new string_id(c_stringFile, "s_237");
                utils.removeScriptVar(player, "conversation.ep3_mining_captain_koh.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_mining_captain_koh_handleBranch69(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_188"))
        {
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
            {
                ep3_mining_captain_koh_action_grantReward5(player, npc);
                string_id message = new string_id(c_stringFile, "s_190");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_211");
                    }
                    utils.setScriptVar(player, "conversation.ep3_mining_captain_koh.branchId", 70);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_mining_captain_koh.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_mining_captain_koh_handleBranch70(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_211"))
        {
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_213");
                utils.removeScriptVar(player, "conversation.ep3_mining_captain_koh.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_mining_captain_koh_handleBranch73(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_219"))
        {
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
            {
                ep3_mining_captain_koh_action_grantQuest5(player, npc);
                string_id message = new string_id(c_stringFile, "s_221");
                utils.removeScriptVar(player, "conversation.ep3_mining_captain_koh.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_223"))
        {
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_225");
                utils.removeScriptVar(player, "conversation.ep3_mining_captain_koh.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_mining_captain_koh_handleBranch76(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_250"))
        {
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
            {
                ep3_mining_captain_koh_action_sendSignalToPlayer(player, npc);
                string_id message = new string_id(c_stringFile, "s_252");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_254");
                    }
                    utils.setScriptVar(player, "conversation.ep3_mining_captain_koh.branchId", 77);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_mining_captain_koh.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_mining_captain_koh_handleBranch77(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_254"))
        {
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_256");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_258");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_266");
                    }
                    utils.setScriptVar(player, "conversation.ep3_mining_captain_koh.branchId", 78);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_mining_captain_koh.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_mining_captain_koh_handleBranch78(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_258"))
        {
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
            {
                ep3_mining_captain_koh_action_grantQuest5(player, npc);
                string_id message = new string_id(c_stringFile, "s_260");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_262");
                    }
                    utils.setScriptVar(player, "conversation.ep3_mining_captain_koh.branchId", 79);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_mining_captain_koh.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_266"))
        {
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_268");
                utils.removeScriptVar(player, "conversation.ep3_mining_captain_koh.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_mining_captain_koh_handleBranch79(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_262"))
        {
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_264");
                utils.removeScriptVar(player, "conversation.ep3_mining_captain_koh.branchId");
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
            detachScript(self, "conversation.ep3_mining_captain_koh");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setName(self, "Captain Koh");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setName(self, "Captain Koh");
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
        detachScript(self, "conversation.ep3_mining_captain_koh");
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
        if (ep3_mining_captain_koh_condition_hasFailedQuest11(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_235");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_248");
                }
                utils.setScriptVar(player, "conversation.ep3_mining_captain_koh.branchId", 1);
                npcStartConversation(player, npc, "ep3_mining_captain_koh", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_mining_captain_koh_condition_hasCompletedQuest11(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_193");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_204");
                }
                utils.setScriptVar(player, "conversation.ep3_mining_captain_koh.branchId", 3);
                npcStartConversation(player, npc, "ep3_mining_captain_koh", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_mining_captain_koh_condition_isQuest11Active(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_192");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_mining_captain_koh_condition_isReadyForQuest11(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_191");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_194");
                }
                utils.setScriptVar(player, "conversation.ep3_mining_captain_koh.branchId", 8);
                npcStartConversation(player, npc, "ep3_mining_captain_koh", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_mining_captain_koh_condition_hasFailedQuest10(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_234");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_246");
                }
                utils.setScriptVar(player, "conversation.ep3_mining_captain_koh.branchId", 14);
                npcStartConversation(player, npc, "ep3_mining_captain_koh", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_mining_captain_koh_condition_hasCompletedQuest10(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_159");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_170");
                }
                utils.setScriptVar(player, "conversation.ep3_mining_captain_koh.branchId", 16);
                npcStartConversation(player, npc, "ep3_mining_captain_koh", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_mining_captain_koh_condition_isQuest10Active(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_158");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_mining_captain_koh_condition_isReadyForQuest10(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_157");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_160");
                }
                utils.setScriptVar(player, "conversation.ep3_mining_captain_koh.branchId", 20);
                npcStartConversation(player, npc, "ep3_mining_captain_koh", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_mining_captain_koh_condition_hasFailedQuest9(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_233");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_244");
                }
                utils.setScriptVar(player, "conversation.ep3_mining_captain_koh.branchId", 26);
                npcStartConversation(player, npc, "ep3_mining_captain_koh", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_mining_captain_koh_condition_hasCompletedQuest9(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_141");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_146");
                }
                utils.setScriptVar(player, "conversation.ep3_mining_captain_koh.branchId", 28);
                npcStartConversation(player, npc, "ep3_mining_captain_koh", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_mining_captain_koh_condition_isQuest9Active(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_140");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_mining_captain_koh_condition_isReadyForQuest9(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_139");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_142");
                }
                utils.setScriptVar(player, "conversation.ep3_mining_captain_koh.branchId", 31);
                npcStartConversation(player, npc, "ep3_mining_captain_koh", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_mining_captain_koh_condition_hasFailedQuest8(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_232");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_242");
                }
                utils.setScriptVar(player, "conversation.ep3_mining_captain_koh.branchId", 34);
                npcStartConversation(player, npc, "ep3_mining_captain_koh", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_mining_captain_koh_condition_hasCompletedQuest8(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_111");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_119");
                }
                utils.setScriptVar(player, "conversation.ep3_mining_captain_koh.branchId", 36);
                npcStartConversation(player, npc, "ep3_mining_captain_koh", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_mining_captain_koh_condition_isQuest8Active(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_110");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_mining_captain_koh_condition_isReadyForQuest8(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_109");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_112");
                }
                utils.setScriptVar(player, "conversation.ep3_mining_captain_koh.branchId", 41);
                npcStartConversation(player, npc, "ep3_mining_captain_koh", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_mining_captain_koh_condition_hasFailedQuest7(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_231");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_240");
                }
                utils.setScriptVar(player, "conversation.ep3_mining_captain_koh.branchId", 45);
                npcStartConversation(player, npc, "ep3_mining_captain_koh", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_mining_captain_koh_condition_hasCompletedQuest7(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_84");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_86");
                }
                utils.setScriptVar(player, "conversation.ep3_mining_captain_koh.branchId", 47);
                npcStartConversation(player, npc, "ep3_mining_captain_koh", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_mining_captain_koh_condition_isQuest7Active(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_85");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_mining_captain_koh_condition_isReadyForQuest7(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_90");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_92");
                }
                utils.setScriptVar(player, "conversation.ep3_mining_captain_koh.branchId", 51);
                npcStartConversation(player, npc, "ep3_mining_captain_koh", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_mining_captain_koh_condition_hasFailedQuest6(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_230");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_238");
                }
                utils.setScriptVar(player, "conversation.ep3_mining_captain_koh.branchId", 56);
                npcStartConversation(player, npc, "ep3_mining_captain_koh", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_mining_captain_koh_condition_hasCompletedQuest6(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_127");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_129");
                }
                utils.setScriptVar(player, "conversation.ep3_mining_captain_koh.branchId", 58);
                npcStartConversation(player, npc, "ep3_mining_captain_koh", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_mining_captain_koh_condition_isQuest6Active(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_150");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_mining_captain_koh_condition_isReadyForQuest6(player, npc))
        {
            doAnimationAction(npc, "shake_head_disgust");
            string_id message = new string_id(c_stringFile, "s_152");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_154");
                }
                utils.setScriptVar(player, "conversation.ep3_mining_captain_koh.branchId", 63);
                npcStartConversation(player, npc, "ep3_mining_captain_koh", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_mining_captain_koh_condition_hasFailedQuest5(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_229");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_236");
                }
                utils.setScriptVar(player, "conversation.ep3_mining_captain_koh.branchId", 67);
                npcStartConversation(player, npc, "ep3_mining_captain_koh", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_mining_captain_koh_condition_hasCompletedQuest5(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_186");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_188");
                }
                utils.setScriptVar(player, "conversation.ep3_mining_captain_koh.branchId", 69);
                npcStartConversation(player, npc, "ep3_mining_captain_koh", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_mining_captain_koh_condition_isQuest5Active(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_215");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_mining_captain_koh_condition_isReadyForQuest5(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_217");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_219");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_223");
                }
                utils.setScriptVar(player, "conversation.ep3_mining_captain_koh.branchId", 73);
                npcStartConversation(player, npc, "ep3_mining_captain_koh", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_mining_captain_koh_condition_hasQuestFour(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_227");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_250");
                }
                utils.setScriptVar(player, "conversation.ep3_mining_captain_koh.branchId", 76);
                npcStartConversation(player, npc, "ep3_mining_captain_koh", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_mining_captain_koh_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_270");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("ep3_mining_captain_koh"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.ep3_mining_captain_koh.branchId");
        if (branchId == 1 && ep3_mining_captain_koh_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && ep3_mining_captain_koh_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && ep3_mining_captain_koh_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && ep3_mining_captain_koh_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && ep3_mining_captain_koh_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && ep3_mining_captain_koh_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && ep3_mining_captain_koh_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && ep3_mining_captain_koh_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && ep3_mining_captain_koh_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && ep3_mining_captain_koh_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && ep3_mining_captain_koh_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && ep3_mining_captain_koh_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && ep3_mining_captain_koh_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && ep3_mining_captain_koh_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && ep3_mining_captain_koh_handleBranch23(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && ep3_mining_captain_koh_handleBranch26(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 28 && ep3_mining_captain_koh_handleBranch28(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 31 && ep3_mining_captain_koh_handleBranch31(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 32 && ep3_mining_captain_koh_handleBranch32(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 34 && ep3_mining_captain_koh_handleBranch34(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 36 && ep3_mining_captain_koh_handleBranch36(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 37 && ep3_mining_captain_koh_handleBranch37(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 38 && ep3_mining_captain_koh_handleBranch38(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 41 && ep3_mining_captain_koh_handleBranch41(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 42 && ep3_mining_captain_koh_handleBranch42(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 45 && ep3_mining_captain_koh_handleBranch45(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 47 && ep3_mining_captain_koh_handleBranch47(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 48 && ep3_mining_captain_koh_handleBranch48(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 51 && ep3_mining_captain_koh_handleBranch51(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 52 && ep3_mining_captain_koh_handleBranch52(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 53 && ep3_mining_captain_koh_handleBranch53(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 56 && ep3_mining_captain_koh_handleBranch56(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 58 && ep3_mining_captain_koh_handleBranch58(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 59 && ep3_mining_captain_koh_handleBranch59(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 60 && ep3_mining_captain_koh_handleBranch60(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 63 && ep3_mining_captain_koh_handleBranch63(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 64 && ep3_mining_captain_koh_handleBranch64(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 65 && ep3_mining_captain_koh_handleBranch65(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 67 && ep3_mining_captain_koh_handleBranch67(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 69 && ep3_mining_captain_koh_handleBranch69(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 70 && ep3_mining_captain_koh_handleBranch70(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 73 && ep3_mining_captain_koh_handleBranch73(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 76 && ep3_mining_captain_koh_handleBranch76(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 77 && ep3_mining_captain_koh_handleBranch77(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 78 && ep3_mining_captain_koh_handleBranch78(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 79 && ep3_mining_captain_koh_handleBranch79(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.ep3_mining_captain_koh.branchId");
        return SCRIPT_CONTINUE;
    }
}
