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
import script.library.gcw;
import script.library.static_item;
import script.library.utils;

public class static_gcw_base_talus_imperial_reward_npc extends script.base_script
{
    public static_gcw_base_talus_imperial_reward_npc()
    {
    }
    public static String c_stringFile = "conversation/static_gcw_base_talus_imperial_reward_npc";
    public boolean static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean static_gcw_base_talus_imperial_reward_npc_condition_playerImperial(obj_id player, obj_id npc) throws InterruptedException
    {
        return factions.isImperial(player);
    }
    public boolean static_gcw_base_talus_imperial_reward_npc_condition_inControlOfBase(obj_id player, obj_id npc) throws InterruptedException
    {
        return gcw.getPub30StaticBaseControllingFaction(npc) == gcw.IMPERIAL_CONTROL;
    }
    public boolean static_gcw_base_talus_imperial_reward_npc_condition_reward_phase_3(obj_id player, obj_id npc) throws InterruptedException
    {
        return gcw.getPub30StaticBaseCapturePhase(npc) >= 3;
    }
    public boolean static_gcw_base_talus_imperial_reward_npc_condition_reward_phase_4(obj_id player, obj_id npc) throws InterruptedException
    {
        return gcw.getPub30StaticBaseCapturePhase(npc) >= 4;
    }
    public boolean static_gcw_base_talus_imperial_reward_npc_condition_isGodPlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        return isGod(player);
    }
    public boolean static_gcw_base_talus_imperial_reward_npc_condition_reward_phase_5(obj_id player, obj_id npc) throws InterruptedException
    {
        return gcw.getPub30StaticBaseCapturePhase(npc) == 5;
    }
    public boolean static_gcw_base_talus_imperial_reward_npc_condition_reward_phase_2(obj_id player, obj_id npc) throws InterruptedException
    {
        return gcw.getPub30StaticBaseCapturePhase(npc) >= 2;
    }
    public void static_gcw_base_talus_imperial_reward_npc_action_grantHealthPack1(obj_id player, obj_id npc) throws InterruptedException
    {
        static_item.createNewItemFunction("item_gcw_base_health_a_03_01", player);
    }
    public void static_gcw_base_talus_imperial_reward_npc_action_grantHealthPack2(obj_id player, obj_id npc) throws InterruptedException
    {
        static_item.createNewItemFunction("item_gcw_base_health_b_03_01", player);
    }
    public void static_gcw_base_talus_imperial_reward_npc_action_grantHealthPack3(obj_id player, obj_id npc) throws InterruptedException
    {
        static_item.createNewItemFunction("item_gcw_base_health_c_03_01", player);
    }
    public void static_gcw_base_talus_imperial_reward_npc_action_grantHealthPack4(obj_id player, obj_id npc) throws InterruptedException
    {
        static_item.createNewItemFunction("item_gcw_base_health_d_03_01", player);
    }
    public void static_gcw_base_talus_imperial_reward_npc_action_grantHealthPack5(obj_id player, obj_id npc) throws InterruptedException
    {
        static_item.createNewItemFunction("item_gcw_base_health_e_04_01", player);
    }
    public void static_gcw_base_talus_imperial_reward_npc_action_grantActionPack1(obj_id player, obj_id npc) throws InterruptedException
    {
        static_item.createNewItemFunction("item_gcw_base_action_a_03_01", player);
    }
    public void static_gcw_base_talus_imperial_reward_npc_action_grantActionPack2(obj_id player, obj_id npc) throws InterruptedException
    {
        static_item.createNewItemFunction("item_gcw_base_action_b_03_01", player);
    }
    public void static_gcw_base_talus_imperial_reward_npc_action_grantActionPack3(obj_id player, obj_id npc) throws InterruptedException
    {
        static_item.createNewItemFunction("item_gcw_base_action_c_03_01", player);
    }
    public void static_gcw_base_talus_imperial_reward_npc_action_grantActionPack4(obj_id player, obj_id npc) throws InterruptedException
    {
        static_item.createNewItemFunction("item_gcw_base_action_d_03_01", player);
    }
    public void static_gcw_base_talus_imperial_reward_npc_action_grantActionPack5(obj_id player, obj_id npc) throws InterruptedException
    {
        static_item.createNewItemFunction("item_gcw_base_action_e_04_01", player);
    }
    public void static_gcw_base_talus_imperial_reward_npc_action_grantDamageAbsorb1(obj_id player, obj_id npc) throws InterruptedException
    {
        static_item.createNewItemFunction("item_gcw_base_damage_absorb_a_03_01", player);
    }
    public void static_gcw_base_talus_imperial_reward_npc_action_grantDamageAbsorb2(obj_id player, obj_id npc) throws InterruptedException
    {
        static_item.createNewItemFunction("item_gcw_base_damage_absorb_b_03_01", player);
    }
    public void static_gcw_base_talus_imperial_reward_npc_action_grantDamageAbsorb3(obj_id player, obj_id npc) throws InterruptedException
    {
        static_item.createNewItemFunction("item_gcw_base_damage_absorb_c_03_01", player);
    }
    public void static_gcw_base_talus_imperial_reward_npc_action_grantDamageAbsorb4(obj_id player, obj_id npc) throws InterruptedException
    {
        static_item.createNewItemFunction("item_gcw_base_damage_absorb_d_03_01", player);
    }
    public void static_gcw_base_talus_imperial_reward_npc_action_grantDamageAbsorb5(obj_id player, obj_id npc) throws InterruptedException
    {
        static_item.createNewItemFunction("item_gcw_base_damage_absorb_e_04_01", player);
    }
    public void static_gcw_base_talus_imperial_reward_npc_action_grantDamageBoost1(obj_id player, obj_id npc) throws InterruptedException
    {
        static_item.createNewItemFunction("item_gcw_base_damage_booster_a_03_01", player);
    }
    public void static_gcw_base_talus_imperial_reward_npc_action_grantDamageBoost2(obj_id player, obj_id npc) throws InterruptedException
    {
        static_item.createNewItemFunction("item_gcw_base_damage_booster_b_03_01", player);
    }
    public void static_gcw_base_talus_imperial_reward_npc_action_grantDamageBoost3(obj_id player, obj_id npc) throws InterruptedException
    {
        static_item.createNewItemFunction("item_gcw_base_damage_booster_c_03_01", player);
    }
    public void static_gcw_base_talus_imperial_reward_npc_action_grantDamageShield1(obj_id player, obj_id npc) throws InterruptedException
    {
        static_item.createNewItemFunction("item_gcw_base_damage_shield_a_03_01", player);
    }
    public void static_gcw_base_talus_imperial_reward_npc_action_grantDamageShield2(obj_id player, obj_id npc) throws InterruptedException
    {
        static_item.createNewItemFunction("item_gcw_base_damage_shield_b_03_01", player);
    }
    public void static_gcw_base_talus_imperial_reward_npc_action_grantDamageShield3(obj_id player, obj_id npc) throws InterruptedException
    {
        static_item.createNewItemFunction("item_gcw_base_damage_shield_c_03_01", player);
    }
    public void static_gcw_base_talus_imperial_reward_npc_action_grantReactiveHeal1(obj_id player, obj_id npc) throws InterruptedException
    {
        static_item.createNewItemFunction("item_gcw_base_reactive_heal_a_03_01", player);
    }
    public void static_gcw_base_talus_imperial_reward_npc_action_grantReactiveHeal2(obj_id player, obj_id npc) throws InterruptedException
    {
        static_item.createNewItemFunction("item_gcw_base_reactive_heal_b_03_01", player);
    }
    public void static_gcw_base_talus_imperial_reward_npc_action_grantReactiveHeal3(obj_id player, obj_id npc) throws InterruptedException
    {
        static_item.createNewItemFunction("item_gcw_base_reactive_heal_c_03_01", player);
    }
    public void static_gcw_base_talus_imperial_reward_npc_action_grantReactiveAction1(obj_id player, obj_id npc) throws InterruptedException
    {
        static_item.createNewItemFunction("item_gcw_base_reactive_action_a_03_01", player);
    }
    public void static_gcw_base_talus_imperial_reward_npc_action_grantReactiveAction2(obj_id player, obj_id npc) throws InterruptedException
    {
        static_item.createNewItemFunction("item_gcw_base_reactive_action_b_03_01", player);
    }
    public void static_gcw_base_talus_imperial_reward_npc_action_grantReactiveAction3(obj_id player, obj_id npc) throws InterruptedException
    {
        static_item.createNewItemFunction("item_gcw_base_reactive_action_c_03_01", player);
    }
    public void static_gcw_base_talus_imperial_reward_npc_action_grantLastChance1(obj_id player, obj_id npc) throws InterruptedException
    {
        static_item.createNewItemFunction("item_gcw_base_reactive_critical_heal_a_03_01", player);
    }
    public void static_gcw_base_talus_imperial_reward_npc_action_grantLastChance2(obj_id player, obj_id npc) throws InterruptedException
    {
        static_item.createNewItemFunction("item_gcw_base_reactive_critical_heal_b_03_01", player);
    }
    public void static_gcw_base_talus_imperial_reward_npc_action_grantLastChance3(obj_id player, obj_id npc) throws InterruptedException
    {
        static_item.createNewItemFunction("item_gcw_base_reactive_critical_heal_c_03_01", player);
    }
    public void static_gcw_base_talus_imperial_reward_npc_action_grantRallyBanner(obj_id player, obj_id npc) throws InterruptedException
    {
        static_item.createNewItemFunction("item_gcw_base_rally_banner_04_01", player);
    }
    public void static_gcw_base_talus_imperial_reward_npc_action_advanceRewardPhase(obj_id player, obj_id npc) throws InterruptedException
    {
        int newPhase = gcw.advancePub30StaticBaseCapturePhase(npc);
        sendSystemMessageTestingOnly(player, "Reward phase advanced to " + newPhase);
    }
    public void static_gcw_base_talus_imperial_reward_npc_action_regressRewardPhase(obj_id player, obj_id npc) throws InterruptedException
    {
        int newPhase = gcw.regressPub30StaticBaseCapturePhase(npc);
        sendSystemMessageTestingOnly(player, "Reward phase regressed to " + newPhase);
    }
    public void static_gcw_base_talus_imperial_reward_npc_action_reportRewardPhase(obj_id player, obj_id npc) throws InterruptedException
    {
        int phase = gcw.getPub30StaticBaseCapturePhase(npc);
        String next = gcw.getPub30TimeToNextPhaseString(npc);
        if (phase < 5)
        {
            sendSystemMessageTestingOnly(player, "Current reward phase is: " + phase + ". Next reward phase in " + next);
        }
        else 
        {
            sendSystemMessageTestingOnly(player, "Current reward phase is: " + phase + ". This is the final phase");
        }
    }
    public String static_gcw_base_talus_imperial_reward_npc_tokenTO_timeToNext(obj_id player, obj_id npc) throws InterruptedException
    {
        return gcw.getPub30TimeToNextPhaseString(npc);
    }
    public int static_gcw_base_talus_imperial_reward_npc_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_16"))
        {
            if (static_gcw_base_talus_imperial_reward_npc_condition_reward_phase_5(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_31");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_50");
                    }
                    utils.setScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (static_gcw_base_talus_imperial_reward_npc_condition_reward_phase_4(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_46");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_52");
                    }
                    utils.setScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (static_gcw_base_talus_imperial_reward_npc_condition_reward_phase_3(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_47");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_54");
                    }
                    utils.setScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (static_gcw_base_talus_imperial_reward_npc_condition_reward_phase_2(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_48");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_56");
                    }
                    utils.setScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_49");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_58");
                    }
                    utils.setScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_25"))
        {
            if (static_gcw_base_talus_imperial_reward_npc_condition_reward_phase_5(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_32");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_64");
                    }
                    utils.setScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (static_gcw_base_talus_imperial_reward_npc_condition_reward_phase_4(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_60");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_66");
                    }
                    utils.setScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (static_gcw_base_talus_imperial_reward_npc_condition_reward_phase_3(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_61");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_68");
                    }
                    utils.setScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (static_gcw_base_talus_imperial_reward_npc_condition_reward_phase_2(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_62");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_70");
                    }
                    utils.setScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId", 19);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_63");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_277");
                    }
                    utils.setScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId", 21);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_44"))
        {
            if (static_gcw_base_talus_imperial_reward_npc_condition_reward_phase_5(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_72");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_76");
                    }
                    utils.setScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (static_gcw_base_talus_imperial_reward_npc_condition_reward_phase_4(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_78");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_80");
                    }
                    utils.setScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (static_gcw_base_talus_imperial_reward_npc_condition_reward_phase_3(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_84");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId", 27);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (static_gcw_base_talus_imperial_reward_npc_condition_reward_phase_2(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_90");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId", 29);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_96");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_98");
                    }
                    utils.setScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId", 31);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_102"))
        {
            if (static_gcw_base_talus_imperial_reward_npc_condition_reward_phase_5(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_104");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId", 33);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (static_gcw_base_talus_imperial_reward_npc_condition_reward_phase_4(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_110");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId", 35);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_116");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId", 37);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_124"))
        {
            if (static_gcw_base_talus_imperial_reward_npc_condition_reward_phase_5(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_126");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_128");
                    }
                    utils.setScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId", 39);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (static_gcw_base_talus_imperial_reward_npc_condition_reward_phase_4(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_132");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_134");
                    }
                    utils.setScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId", 41);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_138");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_140");
                    }
                    utils.setScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId", 43);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_146"))
        {
            if (static_gcw_base_talus_imperial_reward_npc_condition_reward_phase_5(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_148");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_150");
                    }
                    utils.setScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId", 45);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (static_gcw_base_talus_imperial_reward_npc_condition_reward_phase_4(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_154");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId", 47);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_160");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId", 49);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_168"))
        {
            if (static_gcw_base_talus_imperial_reward_npc_condition_reward_phase_5(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_170");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId", 51);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (static_gcw_base_talus_imperial_reward_npc_condition_reward_phase_4(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_176");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_178");
                    }
                    utils.setScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId", 53);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_182");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_184");
                    }
                    utils.setScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId", 55);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_190"))
        {
            if (static_gcw_base_talus_imperial_reward_npc_condition_reward_phase_5(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_192");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId", 57);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (static_gcw_base_talus_imperial_reward_npc_condition_reward_phase_4(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_198");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_200");
                    }
                    utils.setScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId", 59);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_204");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId", 61);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_212"))
        {
            if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
            {
                static_gcw_base_talus_imperial_reward_npc_action_grantRallyBanner(player, npc);
                string_id message = new string_id(c_stringFile, "s_214");
                utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_222"))
        {
            if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_224");
                utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_226"))
        {
            if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_228");
                utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_230"))
        {
            if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_232");
                utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_234"))
        {
            if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_236");
                utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_238"))
        {
            if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_240");
                utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_242"))
        {
            if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_244");
                utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_247"))
        {
            if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
            {
                static_gcw_base_talus_imperial_reward_npc_action_advanceRewardPhase(player, npc);
                string_id message = new string_id(c_stringFile, "s_250");
                utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_249"))
        {
            if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
            {
                static_gcw_base_talus_imperial_reward_npc_action_regressRewardPhase(player, npc);
                string_id message = new string_id(c_stringFile, "s_252");
                utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_254"))
        {
            if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
            {
                static_gcw_base_talus_imperial_reward_npc_action_reportRewardPhase(player, npc);
                string_id message = new string_id(c_stringFile, "s_256");
                utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_227"))
        {
            if (static_gcw_base_talus_imperial_reward_npc_condition_reward_phase_5(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_231");
                utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_235");
                utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                pp.other.set(static_gcw_base_talus_imperial_reward_npc_tokenTO_timeToNext(player, npc));
                npcEndConversationWithMessage(player, pp);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int static_gcw_base_talus_imperial_reward_npc_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_50"))
        {
            if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
            {
                static_gcw_base_talus_imperial_reward_npc_action_grantHealthPack5(player, npc);
                string_id message = new string_id(c_stringFile, "s_51");
                utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int static_gcw_base_talus_imperial_reward_npc_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_52"))
        {
            if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
            {
                static_gcw_base_talus_imperial_reward_npc_action_grantHealthPack4(player, npc);
                string_id message = new string_id(c_stringFile, "s_53");
                utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int static_gcw_base_talus_imperial_reward_npc_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_54"))
        {
            if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
            {
                static_gcw_base_talus_imperial_reward_npc_action_grantHealthPack3(player, npc);
                string_id message = new string_id(c_stringFile, "s_55");
                utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int static_gcw_base_talus_imperial_reward_npc_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_56"))
        {
            if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
            {
                static_gcw_base_talus_imperial_reward_npc_action_grantHealthPack2(player, npc);
                string_id message = new string_id(c_stringFile, "s_57");
                utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int static_gcw_base_talus_imperial_reward_npc_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_58"))
        {
            if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
            {
                static_gcw_base_talus_imperial_reward_npc_action_grantHealthPack1(player, npc);
                string_id message = new string_id(c_stringFile, "s_59");
                utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int static_gcw_base_talus_imperial_reward_npc_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_64"))
        {
            if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
            {
                static_gcw_base_talus_imperial_reward_npc_action_grantActionPack5(player, npc);
                string_id message = new string_id(c_stringFile, "s_65");
                utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int static_gcw_base_talus_imperial_reward_npc_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_66"))
        {
            if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
            {
                static_gcw_base_talus_imperial_reward_npc_action_grantActionPack4(player, npc);
                string_id message = new string_id(c_stringFile, "s_67");
                utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int static_gcw_base_talus_imperial_reward_npc_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_68"))
        {
            if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
            {
                static_gcw_base_talus_imperial_reward_npc_action_grantActionPack3(player, npc);
                string_id message = new string_id(c_stringFile, "s_69");
                utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int static_gcw_base_talus_imperial_reward_npc_handleBranch19(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_70"))
        {
            if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
            {
                static_gcw_base_talus_imperial_reward_npc_action_grantActionPack2(player, npc);
                string_id message = new string_id(c_stringFile, "s_71");
                utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int static_gcw_base_talus_imperial_reward_npc_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_277"))
        {
            if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
            {
                static_gcw_base_talus_imperial_reward_npc_action_grantActionPack1(player, npc);
                string_id message = new string_id(c_stringFile, "s_278");
                utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int static_gcw_base_talus_imperial_reward_npc_handleBranch23(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_76"))
        {
            if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
            {
                static_gcw_base_talus_imperial_reward_npc_action_grantDamageAbsorb5(player, npc);
                string_id message = new string_id(c_stringFile, "s_77");
                utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int static_gcw_base_talus_imperial_reward_npc_handleBranch25(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_80"))
        {
            if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
            {
                static_gcw_base_talus_imperial_reward_npc_action_grantDamageAbsorb4(player, npc);
                string_id message = new string_id(c_stringFile, "s_82");
                utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int static_gcw_base_talus_imperial_reward_npc_handleBranch27(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_86"))
        {
            if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
            {
                static_gcw_base_talus_imperial_reward_npc_action_grantDamageAbsorb3(player, npc);
                string_id message = new string_id(c_stringFile, "s_88");
                utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int static_gcw_base_talus_imperial_reward_npc_handleBranch29(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_92"))
        {
            if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
            {
                static_gcw_base_talus_imperial_reward_npc_action_grantDamageAbsorb2(player, npc);
                string_id message = new string_id(c_stringFile, "s_94");
                utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int static_gcw_base_talus_imperial_reward_npc_handleBranch31(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_98"))
        {
            if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
            {
                static_gcw_base_talus_imperial_reward_npc_action_grantDamageAbsorb1(player, npc);
                string_id message = new string_id(c_stringFile, "s_100");
                utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int static_gcw_base_talus_imperial_reward_npc_handleBranch33(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_106"))
        {
            if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
            {
                static_gcw_base_talus_imperial_reward_npc_action_grantDamageBoost3(player, npc);
                string_id message = new string_id(c_stringFile, "s_108");
                utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int static_gcw_base_talus_imperial_reward_npc_handleBranch35(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_112"))
        {
            if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
            {
                static_gcw_base_talus_imperial_reward_npc_action_grantDamageBoost2(player, npc);
                string_id message = new string_id(c_stringFile, "s_114");
                utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int static_gcw_base_talus_imperial_reward_npc_handleBranch37(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_118"))
        {
            if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
            {
                static_gcw_base_talus_imperial_reward_npc_action_grantDamageBoost1(player, npc);
                string_id message = new string_id(c_stringFile, "s_120");
                utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int static_gcw_base_talus_imperial_reward_npc_handleBranch39(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_128"))
        {
            if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
            {
                static_gcw_base_talus_imperial_reward_npc_action_grantDamageShield3(player, npc);
                string_id message = new string_id(c_stringFile, "s_130");
                utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int static_gcw_base_talus_imperial_reward_npc_handleBranch41(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_134"))
        {
            if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
            {
                static_gcw_base_talus_imperial_reward_npc_action_grantDamageShield2(player, npc);
                string_id message = new string_id(c_stringFile, "s_136");
                utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int static_gcw_base_talus_imperial_reward_npc_handleBranch43(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_140"))
        {
            if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
            {
                static_gcw_base_talus_imperial_reward_npc_action_grantDamageShield1(player, npc);
                string_id message = new string_id(c_stringFile, "s_142");
                utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int static_gcw_base_talus_imperial_reward_npc_handleBranch45(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_150"))
        {
            if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
            {
                static_gcw_base_talus_imperial_reward_npc_action_grantReactiveHeal3(player, npc);
                string_id message = new string_id(c_stringFile, "s_152");
                utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int static_gcw_base_talus_imperial_reward_npc_handleBranch47(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_156"))
        {
            if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
            {
                static_gcw_base_talus_imperial_reward_npc_action_grantReactiveHeal2(player, npc);
                string_id message = new string_id(c_stringFile, "s_158");
                utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int static_gcw_base_talus_imperial_reward_npc_handleBranch49(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_162"))
        {
            if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
            {
                static_gcw_base_talus_imperial_reward_npc_action_grantReactiveHeal1(player, npc);
                string_id message = new string_id(c_stringFile, "s_164");
                utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int static_gcw_base_talus_imperial_reward_npc_handleBranch51(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_172"))
        {
            if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
            {
                static_gcw_base_talus_imperial_reward_npc_action_grantReactiveAction3(player, npc);
                string_id message = new string_id(c_stringFile, "s_174");
                utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int static_gcw_base_talus_imperial_reward_npc_handleBranch53(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_178"))
        {
            if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
            {
                static_gcw_base_talus_imperial_reward_npc_action_grantReactiveAction2(player, npc);
                string_id message = new string_id(c_stringFile, "s_180");
                utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int static_gcw_base_talus_imperial_reward_npc_handleBranch55(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_184"))
        {
            if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
            {
                static_gcw_base_talus_imperial_reward_npc_action_grantReactiveAction1(player, npc);
                string_id message = new string_id(c_stringFile, "s_186");
                utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int static_gcw_base_talus_imperial_reward_npc_handleBranch57(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_194"))
        {
            if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
            {
                static_gcw_base_talus_imperial_reward_npc_action_grantLastChance3(player, npc);
                string_id message = new string_id(c_stringFile, "s_196");
                utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int static_gcw_base_talus_imperial_reward_npc_handleBranch59(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_200"))
        {
            if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
            {
                static_gcw_base_talus_imperial_reward_npc_action_grantLastChance2(player, npc);
                string_id message = new string_id(c_stringFile, "s_202");
                utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int static_gcw_base_talus_imperial_reward_npc_handleBranch61(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_206"))
        {
            if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
            {
                static_gcw_base_talus_imperial_reward_npc_action_grantLastChance1(player, npc);
                string_id message = new string_id(c_stringFile, "s_208");
                utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
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
            detachScript(self, "conversation.static_gcw_base_talus_imperial_reward_npc");
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
        detachScript(self, "conversation.static_gcw_base_talus_imperial_reward_npc");
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
        if (!static_gcw_base_talus_imperial_reward_npc_condition_inControlOfBase(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_15");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (static_gcw_base_talus_imperial_reward_npc_condition_playerImperial(player, npc))
        {
            doAnimationAction(npc, "salute2");
            doAnimationAction(player, "salute2");
            string_id message = new string_id(c_stringFile, "s_5");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (static_gcw_base_talus_imperial_reward_npc_condition_reward_phase_3(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse3 = true;
            }
            boolean hasResponse4 = false;
            if (static_gcw_base_talus_imperial_reward_npc_condition_reward_phase_3(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse4 = true;
            }
            boolean hasResponse5 = false;
            if (static_gcw_base_talus_imperial_reward_npc_condition_reward_phase_3(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse5 = true;
            }
            boolean hasResponse6 = false;
            if (static_gcw_base_talus_imperial_reward_npc_condition_reward_phase_3(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse6 = true;
            }
            boolean hasResponse7 = false;
            if (static_gcw_base_talus_imperial_reward_npc_condition_reward_phase_3(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse7 = true;
            }
            boolean hasResponse8 = false;
            if (static_gcw_base_talus_imperial_reward_npc_condition_reward_phase_5(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse8 = true;
            }
            boolean hasResponse9 = false;
            if (!static_gcw_base_talus_imperial_reward_npc_condition_reward_phase_3(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse9 = true;
            }
            boolean hasResponse10 = false;
            if (!static_gcw_base_talus_imperial_reward_npc_condition_reward_phase_3(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse10 = true;
            }
            boolean hasResponse11 = false;
            if (!static_gcw_base_talus_imperial_reward_npc_condition_reward_phase_3(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse11 = true;
            }
            boolean hasResponse12 = false;
            if (!static_gcw_base_talus_imperial_reward_npc_condition_reward_phase_3(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse12 = true;
            }
            boolean hasResponse13 = false;
            if (!static_gcw_base_talus_imperial_reward_npc_condition_reward_phase_3(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse13 = true;
            }
            boolean hasResponse14 = false;
            if (!static_gcw_base_talus_imperial_reward_npc_condition_reward_phase_5(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse14 = true;
            }
            boolean hasResponse15 = false;
            if (static_gcw_base_talus_imperial_reward_npc_condition_isGodPlayer(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse15 = true;
            }
            boolean hasResponse16 = false;
            if (static_gcw_base_talus_imperial_reward_npc_condition_isGodPlayer(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse16 = true;
            }
            boolean hasResponse17 = false;
            if (static_gcw_base_talus_imperial_reward_npc_condition_isGodPlayer(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse17 = true;
            }
            boolean hasResponse18 = false;
            if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse18 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_16");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_25");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_44");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_102");
                }
                if (hasResponse4)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_124");
                }
                if (hasResponse5)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_146");
                }
                if (hasResponse6)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_168");
                }
                if (hasResponse7)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_190");
                }
                if (hasResponse8)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_212");
                }
                if (hasResponse9)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_222");
                }
                if (hasResponse10)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_226");
                }
                if (hasResponse11)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_230");
                }
                if (hasResponse12)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_234");
                }
                if (hasResponse13)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_238");
                }
                if (hasResponse14)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_242");
                }
                if (hasResponse15)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_247");
                }
                if (hasResponse16)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_249");
                }
                if (hasResponse17)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_254");
                }
                if (hasResponse18)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_227");
                }
                utils.setScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId", 2);
                npcStartConversation(player, npc, "static_gcw_base_talus_imperial_reward_npc", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (static_gcw_base_talus_imperial_reward_npc_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_258");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("static_gcw_base_talus_imperial_reward_npc"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
        if (branchId == 2 && static_gcw_base_talus_imperial_reward_npc_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && static_gcw_base_talus_imperial_reward_npc_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && static_gcw_base_talus_imperial_reward_npc_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && static_gcw_base_talus_imperial_reward_npc_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && static_gcw_base_talus_imperial_reward_npc_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && static_gcw_base_talus_imperial_reward_npc_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && static_gcw_base_talus_imperial_reward_npc_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && static_gcw_base_talus_imperial_reward_npc_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && static_gcw_base_talus_imperial_reward_npc_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && static_gcw_base_talus_imperial_reward_npc_handleBranch19(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && static_gcw_base_talus_imperial_reward_npc_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && static_gcw_base_talus_imperial_reward_npc_handleBranch23(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && static_gcw_base_talus_imperial_reward_npc_handleBranch25(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 27 && static_gcw_base_talus_imperial_reward_npc_handleBranch27(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && static_gcw_base_talus_imperial_reward_npc_handleBranch29(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 31 && static_gcw_base_talus_imperial_reward_npc_handleBranch31(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 33 && static_gcw_base_talus_imperial_reward_npc_handleBranch33(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 35 && static_gcw_base_talus_imperial_reward_npc_handleBranch35(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 37 && static_gcw_base_talus_imperial_reward_npc_handleBranch37(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 39 && static_gcw_base_talus_imperial_reward_npc_handleBranch39(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 41 && static_gcw_base_talus_imperial_reward_npc_handleBranch41(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 43 && static_gcw_base_talus_imperial_reward_npc_handleBranch43(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 45 && static_gcw_base_talus_imperial_reward_npc_handleBranch45(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 47 && static_gcw_base_talus_imperial_reward_npc_handleBranch47(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 49 && static_gcw_base_talus_imperial_reward_npc_handleBranch49(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 51 && static_gcw_base_talus_imperial_reward_npc_handleBranch51(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 53 && static_gcw_base_talus_imperial_reward_npc_handleBranch53(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 55 && static_gcw_base_talus_imperial_reward_npc_handleBranch55(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 57 && static_gcw_base_talus_imperial_reward_npc_handleBranch57(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 59 && static_gcw_base_talus_imperial_reward_npc_handleBranch59(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 61 && static_gcw_base_talus_imperial_reward_npc_handleBranch61(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.static_gcw_base_talus_imperial_reward_npc.branchId");
        return SCRIPT_CONTINUE;
    }
}
