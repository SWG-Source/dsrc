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
import script.library.skill;
import script.library.space_flags;
import script.library.space_quest;
import script.library.utils;
import script.library.xp;

public class tatooine_imperial_trainer_2b extends script.base_script
{
    public tatooine_imperial_trainer_2b()
    {
    }
    public static String c_stringFile = "conversation/tatooine_imperial_trainer_2b";
    public boolean tatooine_imperial_trainer_2b_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean tatooine_imperial_trainer_2b_condition_hasAuthOfTransfer(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id pInv = utils.getInventoryContainer(player);
        if (pInv == null)
        {
            return false;
        }
        obj_id[] items = getContents(pInv);
        if (items == null)
        {
            return false;
        }
        for (int i = 0; i < items.length; i++)
        {
            String name = getTemplateName(items[i]);
            if (name.equals("object/tangible/space/mission_objects/transfer_auth.iff"))
            {
                utils.setScriptVar(player, "auth", items[i]);
                return true;
            }
        }
        return false;
    }
    public boolean tatooine_imperial_trainer_2b_condition_hasUnsignedForm(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id auth = utils.getObjIdScriptVar(player, "auth");
        if (!isIdValid(auth))
        {
            return true;
        }
        String sign = getStringObjVar(auth, "signature");
        if (sign == null)
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean tatooine_imperial_trainer_2b_condition_hasIncorrectForm(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id auth = utils.getObjIdScriptVar(player, "auth");
        if (!isIdValid(auth))
        {
            return true;
        }
        String sign = getStringObjVar(auth, "signature");
        String name = getName(player);
        if (sign.equals(name))
        {
            return false;
        }
        else 
        {
            return true;
        }
    }
    public boolean tatooine_imperial_trainer_2b_condition_isStormSquadron(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_flags.hasSpaceFlag(player, "stormSquadronInitiate");
    }
    public boolean tatooine_imperial_trainer_2b_condition_isPrivateer(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasSkill(player, "pilot_neutral_novice"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean tatooine_imperial_trainer_2b_condition_isRebel(obj_id player, obj_id npc) throws InterruptedException
    {
        if (factions.getFaction(player) == factions.FACTION_REBEL)
        {
            return true;
        }
        if (hasSkill(player, "pilot_rebel_navy_novice"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean tatooine_imperial_trainer_2b_condition_hasFailedQuest1(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasFailedQuest(player, "inspect", "imperial_ss_4") || space_quest.hasAbortedQuest(player, "inspect", "imperial_ss_4"));
    }
    public boolean tatooine_imperial_trainer_2b_condition_hasWonQuest1(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasWonQuest(player, "inspect", "imperial_ss_4");
    }
    public boolean tatooine_imperial_trainer_2b_condition_isReadyForDutyBlock1(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasReceivedReward(player, "escort", "imperial_ss_5");
    }
    public boolean tatooine_imperial_trainer_2b_condition_hasWonQuest2(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasWonQuest(player, "escort", "imperial_ss_5");
    }
    public boolean tatooine_imperial_trainer_2b_condition_hasFailedQuest2(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasFailedQuest(player, "escort", "imperial_ss_5") || space_quest.hasAbortedQuest(player, "escort", "imperial_ss_5"));
    }
    public boolean tatooine_imperial_trainer_2b_condition_isReadyForMission2(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasReceivedReward(player, "inspect", "imperial_ss_4");
    }
    public boolean tatooine_imperial_trainer_2b_condition_canBuySkill1(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasReceivedReward(player, "escort", "imperial_ss_5"))
        {
            return false;
        }
        else if (space_quest.isPlayerQualifiedForSkill(player, "pilot_imperial_navy_starships_02") || space_quest.isPlayerQualifiedForSkill(player, "pilot_imperial_navy_weapons_02") || space_quest.isPlayerQualifiedForSkill(player, "pilot_imperial_navy_procedures_02") || space_quest.isPlayerQualifiedForSkill(player, "pilot_imperial_navy_droid_02"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean tatooine_imperial_trainer_2b_condition_canBuySkill1A(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasSkill(player, "pilot_imperial_navy_starships_02"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean tatooine_imperial_trainer_2b_condition_canBuySkill1B(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasSkill(player, "pilot_imperial_navy_weapons_02"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean tatooine_imperial_trainer_2b_condition_canBuySkill1C(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasSkill(player, "pilot_imperial_navy_procedures_02"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean tatooine_imperial_trainer_2b_condition_canBuySkill1D(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasSkill(player, "pilot_imperial_navy_droid_02"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean tatooine_imperial_trainer_2b_condition_isReadyForMission3(obj_id player, obj_id npc) throws InterruptedException
    {
        String[] skills = 
        {
            "pilot_imperial_navy_starships_02",
            "pilot_imperial_navy_weapons_02",
            "pilot_imperial_navy_procedures_02",
            "pilot_imperial_navy_droid_02"
        };
        int j = 0;
        for (int i = 0; i < skills.length; i++)
        {
            if (hasSkill(player, skills[i]))
            {
                j++;
            }
        }
        if (j == 4)
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean tatooine_imperial_trainer_2b_condition_isOnMission(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasQuest(player, "inspect", "imperial_ss_4") || space_quest.hasQuest(player, "escort", "imperial_ss_5") || space_quest.hasQuest(player, "recovery", "imperial_ss_6"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean tatooine_imperial_trainer_2b_condition_isWorkingForAlozen(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_flags.hasSpaceFlag(player, "workingWithAlozen");
    }
    public boolean tatooine_imperial_trainer_2b_condition_hasFailedQuest3(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasFailedQuest(player, "recovery", "imperial_ss_6") || space_quest.hasAbortedQuest(player, "recovery", "imperial_ss_6"));
    }
    public boolean tatooine_imperial_trainer_2b_condition_hasWonQuest3(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasWonQuest(player, "recovery", "imperial_ss_6");
    }
    public boolean tatooine_imperial_trainer_2b_condition_transferedToDenner(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasReceivedReward(player, "recovery", "imperial_ss_6"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean tatooine_imperial_trainer_2b_condition_onMyTrack(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_flags.isSpaceTrack(player, space_flags.IMPERIAL_TATOOINE);
    }
    public boolean tatooine_imperial_trainer_2b_condition_canFlyNonTrackDuty(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_flags.hasCompletedTierOne(player) && !space_quest.hasQuest(player))
        {
            return true;
        }
        return false;
    }
    public boolean tatooine_imperial_trainer_2b_condition_isPilot(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_flags.hasAnyPilotSkill(player);
    }
    public void tatooine_imperial_trainer_2b_action_makeStormSquadronInitiate(obj_id player, obj_id npc) throws InterruptedException
    {
        space_flags.setSpaceFlag(player, "stormSquadronInitiate", 1);
    }
    public void tatooine_imperial_trainer_2b_action_giveQuest1(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "inspect", "imperial_ss_4");
    }
    public void tatooine_imperial_trainer_2b_action_giveReward1(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasReceivedReward(player, "inspect", "imperial_ss_4"))
        {
            space_quest.giveReward(player, "inspect", "imperial_ss_4", 5000, "object/tangible/ship/components/shield_generator/shd_mission_reward_imperial_rendili_dual_projector.iff");
            factions.addFactionStanding(player, factions.FACTION_IMPERIAL, 75.0f);
        }
    }
    public void tatooine_imperial_trainer_2b_action_giveQuest2(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "escort", "imperial_ss_5");
    }
    public void tatooine_imperial_trainer_2b_action_giveReward2(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasReceivedReward(player, "escort", "imperial_ss_5"))
        {
            space_quest.giveReward(player, "escort", "imperial_ss_5", 5000);
            factions.addFactionStanding(player, factions.FACTION_IMPERIAL, 50.0f);
        }
    }
    public void tatooine_imperial_trainer_2b_action_grantSkill1A(obj_id player, obj_id npc) throws InterruptedException
    {
        skill.purchaseSkill(player, "pilot_imperial_navy_starships_02");
    }
    public void tatooine_imperial_trainer_2b_action_grantSkill1B(obj_id player, obj_id npc) throws InterruptedException
    {
        skill.purchaseSkill(player, "pilot_imperial_navy_weapons_02");
    }
    public void tatooine_imperial_trainer_2b_action_grantSkill1C(obj_id player, obj_id npc) throws InterruptedException
    {
        skill.purchaseSkill(player, "pilot_imperial_navy_procedures_02");
    }
    public void tatooine_imperial_trainer_2b_action_grantSkill1D(obj_id player, obj_id npc) throws InterruptedException
    {
        skill.purchaseSkill(player, "pilot_imperial_navy_droid_02");
    }
    public void tatooine_imperial_trainer_2b_action_transferToAlozen(obj_id player, obj_id npc) throws InterruptedException
    {
        space_flags.setSpaceFlag(player, "workingWithAlozen", 1);
    }
    public void tatooine_imperial_trainer_2b_action_giveQuest3(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "recovery", "imperial_ss_6");
    }
    public void tatooine_imperial_trainer_2b_action_giveDuty1a(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "destroy_duty", "lok_imperial_ss_2a");
    }
    public void tatooine_imperial_trainer_2b_action_giveDuty1b(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "recovery_duty", "lok_imperial_ss_2b");
    }
    public void tatooine_imperial_trainer_2b_action_giveDuty1c(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "escort_duty", "lok_imperial_ss_2c");
    }
    public void tatooine_imperial_trainer_2b_action_giveReward3(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasReceivedReward(player, "recovery", "imperial_ss_6"))
        {
            space_quest.giveReward(player, "recovery", "imperial_ss_6", 5000);
            factions.addFactionStanding(player, factions.FACTION_IMPERIAL, 50.0f);
        }
    }
    public int tatooine_imperial_trainer_2b_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_4854758d"))
        {
            if (tatooine_imperial_trainer_2b_condition_canFlyNonTrackDuty(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_d7641f66");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b84b366c");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_aec16b0a");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_accusingly");
                string_id message = new string_id(c_stringFile, "s_ad3ca2e6");
                utils.removeScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_imperial_trainer_2b_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_b84b366c"))
        {
            if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_3d7cbb44");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (tatooine_imperial_trainer_2b_condition_onMyTrack(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8a202d7b");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d5ee9adc");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5950b85e");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b0d3b474");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId", 34);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_aec16b0a"))
        {
            if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_8dbf276d");
                utils.removeScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_imperial_trainer_2b_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_71bf4315"))
        {
            if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_accusingly");
                string_id message = new string_id(c_stringFile, "s_20009dcb");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_53923b62");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_imperial_trainer_2b_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_53923b62"))
        {
            if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_54c421ec");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_44522227");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_imperial_trainer_2b_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_44522227"))
        {
            if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1af04177");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bf7bc308");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_imperial_trainer_2b_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_bf7bc308"))
        {
            if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute1");
                doAnimationAction(player, "salute1");
                tatooine_imperial_trainer_2b_action_giveReward3(player, npc);
                string_id message = new string_id(c_stringFile, "s_2f3bf1d8");
                utils.removeScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_imperial_trainer_2b_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1da2b1f3"))
        {
            if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_accusingly");
                string_id message = new string_id(c_stringFile, "s_c0203d03");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a30c1a53");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId", 16);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_imperial_trainer_2b_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_a30c1a53"))
        {
            doAnimationAction(player, "salute2");
            if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wave_on_dismissing");
                tatooine_imperial_trainer_2b_action_giveQuest3(player, npc);
                string_id message = new string_id(c_stringFile, "s_4fc85d67");
                utils.removeScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_imperial_trainer_2b_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_b6b25f13"))
        {
            doAnimationAction(player, "salute1");
            if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_395c2680");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f08d82df");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId", 19);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_imperial_trainer_2b_handleBranch19(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_f08d82df"))
        {
            doAnimationAction(player, "nervous");
            if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_62e2ea4c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_916abbd9");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId", 20);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_imperial_trainer_2b_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_916abbd9"))
        {
            if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "pound_fist_palm");
                string_id message = new string_id(c_stringFile, "s_ea3da11c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_16de6424");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId", 21);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_imperial_trainer_2b_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_16de6424"))
        {
            if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_9506a2bd");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8efde2ae");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_imperial_trainer_2b_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_8efde2ae"))
        {
            doAnimationAction(player, "salute2");
            if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_b7cb6b94");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1ff4247a");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_imperial_trainer_2b_handleBranch23(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1ff4247a"))
        {
            if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
            {
                tatooine_imperial_trainer_2b_action_giveQuest3(player, npc);
                string_id message = new string_id(c_stringFile, "s_e6647df0");
                utils.removeScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_imperial_trainer_2b_handleBranch25(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_7eed59f9"))
        {
            if (!tatooine_imperial_trainer_2b_condition_canBuySkill1(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1d253370");
                utils.removeScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_imperial_trainer_2b_condition_canBuySkill1(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_2172d08a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_156");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ae26c22");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId", 39);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_imperial_trainer_2b_handleBranch26(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_5a7ec1e4"))
        {
            if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_3d7cbb44");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (tatooine_imperial_trainer_2b_condition_onMyTrack(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8a202d7b");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d5ee9adc");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5950b85e");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b0d3b474");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId", 34);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_imperial_trainer_2b_handleBranch27(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_5365b13d"))
        {
            if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "check_wrist_device");
                string_id message = new string_id(c_stringFile, "s_7e1ae5f8");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_57c8489e");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId", 28);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_imperial_trainer_2b_handleBranch28(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_57c8489e"))
        {
            if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_52");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3cbda2cf");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_62");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId", 29);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_imperial_trainer_2b_handleBranch29(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3cbda2cf"))
        {
            if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_e70b39dd");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9315388f");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId", 30);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_62"))
        {
            if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_3d7cbb44");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (tatooine_imperial_trainer_2b_condition_onMyTrack(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8a202d7b");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d5ee9adc");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5950b85e");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b0d3b474");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId", 34);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_imperial_trainer_2b_handleBranch30(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_9315388f"))
        {
            doAnimationAction(player, "nod_head_once");
            if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_f4817c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9f6b62d3");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId", 31);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_imperial_trainer_2b_handleBranch31(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_9f6b62d3"))
        {
            if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_4e7a1e3a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b54802f8");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId", 32);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_imperial_trainer_2b_handleBranch32(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_b54802f8"))
        {
            if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_e6315aa");
                utils.removeScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_imperial_trainer_2b_handleBranch34(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_8a202d7b"))
        {
            if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
            {
                tatooine_imperial_trainer_2b_action_giveDuty1a(player, npc);
                string_id message = new string_id(c_stringFile, "s_8e9a6b0f");
                utils.removeScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_d5ee9adc"))
        {
            if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
            {
                tatooine_imperial_trainer_2b_action_giveDuty1c(player, npc);
                string_id message = new string_id(c_stringFile, "s_5d5a0c89");
                utils.removeScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_5950b85e"))
        {
            if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
            {
                tatooine_imperial_trainer_2b_action_giveDuty1b(player, npc);
                string_id message = new string_id(c_stringFile, "s_17ce8fe5");
                utils.removeScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_b0d3b474"))
        {
            if (!tatooine_imperial_trainer_2b_condition_canBuySkill1(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1d253370");
                utils.removeScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_imperial_trainer_2b_condition_canBuySkill1(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_2172d08a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_156");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ae26c22");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId", 39);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_imperial_trainer_2b_handleBranch39(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_156"))
        {
            if (tatooine_imperial_trainer_2b_condition_canBuySkill1A(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_157");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_161");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId", 40);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_imperial_trainer_2b_condition_canBuySkill1B(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_158");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_163");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId", 42);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_imperial_trainer_2b_condition_canBuySkill1C(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_159");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_165");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId", 44);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_160");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_167");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId", 46);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_ae26c22"))
        {
            if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_7b25fbc3");
                utils.removeScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_imperial_trainer_2b_handleBranch40(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_161"))
        {
            if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
            {
                tatooine_imperial_trainer_2b_action_grantSkill1A(player, npc);
                string_id message = new string_id(c_stringFile, "s_168");
                utils.removeScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_imperial_trainer_2b_handleBranch42(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_163"))
        {
            if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
            {
                tatooine_imperial_trainer_2b_action_grantSkill1B(player, npc);
                string_id message = new string_id(c_stringFile, "s_170");
                utils.removeScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_imperial_trainer_2b_handleBranch44(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_165"))
        {
            if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
            {
                tatooine_imperial_trainer_2b_action_grantSkill1C(player, npc);
                string_id message = new string_id(c_stringFile, "s_172");
                utils.removeScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_imperial_trainer_2b_handleBranch46(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_167"))
        {
            if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
            {
                tatooine_imperial_trainer_2b_action_grantSkill1D(player, npc);
                string_id message = new string_id(c_stringFile, "s_174");
                utils.removeScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_imperial_trainer_2b_handleBranch49(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_da3db7b8"))
        {
            doAnimationAction(player, "embarrassed");
            if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_accusingly");
                string_id message = new string_id(c_stringFile, "s_e240955d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_102");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId", 50);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_imperial_trainer_2b_handleBranch50(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_102"))
        {
            doAnimationAction(player, "salute1");
            if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_accusingly");
                string_id message = new string_id(c_stringFile, "s_c27ccdd6");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_10c85b25");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId", 51);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_imperial_trainer_2b_handleBranch51(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_10c85b25"))
        {
            if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "snap_finger2");
                string_id message = new string_id(c_stringFile, "s_3c521b4d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_86b2a029");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId", 52);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_imperial_trainer_2b_handleBranch52(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_86b2a029"))
        {
            if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "pound_fist_palm");
                tatooine_imperial_trainer_2b_action_giveQuest2(player, npc);
                string_id message = new string_id(c_stringFile, "s_dbcc5353");
                utils.removeScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_imperial_trainer_2b_handleBranch55(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_4d81d507"))
        {
            doAnimationAction(player, "pose_proudly");
            if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
            {
                tatooine_imperial_trainer_2b_action_giveReward1(player, npc);
                string_id message = new string_id(c_stringFile, "s_847e12c5");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_65d0e7ef");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId", 56);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_imperial_trainer_2b_handleBranch56(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_65d0e7ef"))
        {
            if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_48a73eb9");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fe624ae0");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId", 57);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_imperial_trainer_2b_handleBranch57(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_fe624ae0"))
        {
            if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                tatooine_imperial_trainer_2b_action_giveQuest2(player, npc);
                string_id message = new string_id(c_stringFile, "s_116");
                utils.removeScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_imperial_trainer_2b_handleBranch59(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_5cab0f15"))
        {
            doAnimationAction(player, "explain");
            if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_hands");
                string_id message = new string_id(c_stringFile, "s_5c8a242");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_647a7426");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId", 60);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_imperial_trainer_2b_handleBranch60(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_647a7426"))
        {
            if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_598456ed");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_24fbbc08");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId", 61);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_imperial_trainer_2b_handleBranch61(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_24fbbc08"))
        {
            if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
            {
                tatooine_imperial_trainer_2b_action_giveQuest1(player, npc);
                string_id message = new string_id(c_stringFile, "s_567022e6");
                utils.removeScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_imperial_trainer_2b_handleBranch63(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_2eaae5bc"))
        {
            doAnimationAction(player, "explain");
            if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_accusingly");
                string_id message = new string_id(c_stringFile, "s_f1639d7c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f14c78f8");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId", 64);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_53b0407b"))
        {
            if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wave_on_dismissing");
                string_id message = new string_id(c_stringFile, "s_ed5d85e3");
                utils.removeScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_a73dd488"))
        {
            doAnimationAction(player, "standing_placate");
            if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wave_on_dismissing");
                string_id message = new string_id(c_stringFile, "s_1eb2fb7a");
                utils.removeScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_imperial_trainer_2b_handleBranch64(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_f14c78f8"))
        {
            if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_34b41ecc");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_130");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId", 65);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_imperial_trainer_2b_handleBranch65(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_130"))
        {
            doAnimationAction(player, "salute1");
            if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_591121d9");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId", 66);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_imperial_trainer_2b_handleBranch66(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_133"))
        {
            if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_957a3e29");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e053d691");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId", 67);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_imperial_trainer_2b_handleBranch67(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_e053d691"))
        {
            doAnimationAction(player, "nod_head_once");
            if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_19717825");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7a761694");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId", 68);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_imperial_trainer_2b_handleBranch68(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_7a761694"))
        {
            doAnimationAction(player, "listen");
            if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_18478398");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6598178f");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId", 69);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_imperial_trainer_2b_handleBranch69(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6598178f"))
        {
            doAnimationAction(player, "rub_chin_thoughtful");
            if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_6d22cae7");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_47f58b41");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId", 70);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_imperial_trainer_2b_handleBranch70(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_47f58b41"))
        {
            if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_multiple");
                string_id message = new string_id(c_stringFile, "s_d1b5b50");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_81e0c16");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId", 71);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_imperial_trainer_2b_handleBranch71(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_81e0c16"))
        {
            if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_12d564a4");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId", 72);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_imperial_trainer_2b_handleBranch72(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_146"))
        {
            if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_bd3101cc");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d040d381");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId", 73);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_imperial_trainer_2b_handleBranch73(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_d040d381"))
        {
            if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "sigh_deeply");
                string_id message = new string_id(c_stringFile, "s_f81f75fc");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_38c83a93");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId", 74);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_imperial_trainer_2b_handleBranch74(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_38c83a93"))
        {
            if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
            {
                tatooine_imperial_trainer_2b_action_giveQuest1(player, npc);
                string_id message = new string_id(c_stringFile, "s_e617e5c8");
                utils.removeScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId");
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
            detachScript(self, "conversation.tatooine_imperial_trainer_2b");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_SPACE_INTERESTING);
        factions.setFaction(self, factions.FACTION_IMPERIAL);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_SPACE_INTERESTING);
        factions.setFaction(self, factions.FACTION_IMPERIAL);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info menuInfo) throws InterruptedException
    {
        int menu = menuInfo.addRootMenu(menu_info_types.CONVERSE_START, null);
        menu_info_data menuInfoData = menuInfo.getMenuItemById(menu);
        menuInfoData.setServerNotify(false);
        faceTo(self, player);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        detachScript(self, "conversation.tatooine_imperial_trainer_2b");
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
        if (!tatooine_imperial_trainer_2b_condition_isPilot(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_21772e5a");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (tatooine_imperial_trainer_2b_condition_isRebel(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_4");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (tatooine_imperial_trainer_2b_condition_isPrivateer(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_6");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (tatooine_imperial_trainer_2b_condition_isOnMission(player, npc))
        {
            doAnimationAction(npc, "dismiss");
            string_id message = new string_id(c_stringFile, "s_4b074298");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (!tatooine_imperial_trainer_2b_condition_onMyTrack(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_42e9d63a");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_4854758d");
                }
                utils.setScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId", 5);
                npcStartConversation(player, npc, "tatooine_imperial_trainer_2b", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_imperial_trainer_2b_condition_transferedToDenner(player, npc))
        {
            doAnimationAction(npc, "tap_head");
            string_id message = new string_id(c_stringFile, "s_10ee338d");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (tatooine_imperial_trainer_2b_condition_hasWonQuest3(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_b2d005b0");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_71bf4315");
                }
                utils.setScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId", 10);
                npcStartConversation(player, npc, "tatooine_imperial_trainer_2b", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_imperial_trainer_2b_condition_hasFailedQuest3(player, npc))
        {
            doAnimationAction(npc, "shake_head_disgust");
            string_id message = new string_id(c_stringFile, "s_dc229c8d");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1da2b1f3");
                }
                utils.setScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId", 15);
                npcStartConversation(player, npc, "tatooine_imperial_trainer_2b", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_imperial_trainer_2b_condition_isReadyForMission3(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_7ec622db");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_b6b25f13");
                }
                utils.setScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId", 18);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "tatooine_imperial_trainer_2b", null, pp, responses);
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
        if (tatooine_imperial_trainer_2b_condition_canBuySkill1(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_9893fb0");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_7eed59f9");
                }
                utils.setScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId", 25);
                npcStartConversation(player, npc, "tatooine_imperial_trainer_2b", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_imperial_trainer_2b_condition_isReadyForDutyBlock1(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_95549379");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_5a7ec1e4");
                }
                utils.setScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId", 26);
                npcStartConversation(player, npc, "tatooine_imperial_trainer_2b", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_imperial_trainer_2b_condition_hasWonQuest2(player, npc))
        {
            tatooine_imperial_trainer_2b_action_giveReward2(player, npc);
            string_id message = new string_id(c_stringFile, "s_7e1391b7");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_5365b13d");
                }
                utils.setScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId", 27);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "tatooine_imperial_trainer_2b", null, pp, responses);
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
        if (tatooine_imperial_trainer_2b_condition_hasFailedQuest2(player, npc))
        {
            doAnimationAction(npc, "gesticulate_wildly");
            string_id message = new string_id(c_stringFile, "s_1a4113b");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_da3db7b8");
                }
                utils.setScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId", 49);
                npcStartConversation(player, npc, "tatooine_imperial_trainer_2b", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_imperial_trainer_2b_condition_isReadyForMission2(player, npc))
        {
            doAnimationAction(npc, "explain");
            tatooine_imperial_trainer_2b_action_giveQuest2(player, npc);
            string_id message = new string_id(c_stringFile, "s_bf7cf7b8");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (tatooine_imperial_trainer_2b_condition_hasWonQuest1(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_80efb61c");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_4d81d507");
                }
                utils.setScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId", 55);
                npcStartConversation(player, npc, "tatooine_imperial_trainer_2b", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_imperial_trainer_2b_condition_hasFailedQuest1(player, npc))
        {
            doAnimationAction(npc, "tap_foot");
            string_id message = new string_id(c_stringFile, "s_55289b72");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_5cab0f15");
                }
                utils.setScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId", 59);
                npcStartConversation(player, npc, "tatooine_imperial_trainer_2b", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "check_wrist_device");
            string_id message = new string_id(c_stringFile, "s_128d62a");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_imperial_trainer_2b_condition_isWorkingForAlozen(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (tatooine_imperial_trainer_2b_condition_isStormSquadron(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (tatooine_imperial_trainer_2b_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_2eaae5bc");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_53b0407b");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_a73dd488");
                }
                utils.setScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId", 63);
                npcStartConversation(player, npc, "tatooine_imperial_trainer_2b", message, responses);
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
        if (!conversationId.equals("tatooine_imperial_trainer_2b"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId");
        if (branchId == 5 && tatooine_imperial_trainer_2b_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && tatooine_imperial_trainer_2b_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && tatooine_imperial_trainer_2b_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && tatooine_imperial_trainer_2b_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && tatooine_imperial_trainer_2b_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && tatooine_imperial_trainer_2b_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && tatooine_imperial_trainer_2b_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && tatooine_imperial_trainer_2b_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && tatooine_imperial_trainer_2b_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && tatooine_imperial_trainer_2b_handleBranch19(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && tatooine_imperial_trainer_2b_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && tatooine_imperial_trainer_2b_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && tatooine_imperial_trainer_2b_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && tatooine_imperial_trainer_2b_handleBranch23(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && tatooine_imperial_trainer_2b_handleBranch25(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && tatooine_imperial_trainer_2b_handleBranch26(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 27 && tatooine_imperial_trainer_2b_handleBranch27(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 28 && tatooine_imperial_trainer_2b_handleBranch28(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && tatooine_imperial_trainer_2b_handleBranch29(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 30 && tatooine_imperial_trainer_2b_handleBranch30(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 31 && tatooine_imperial_trainer_2b_handleBranch31(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 32 && tatooine_imperial_trainer_2b_handleBranch32(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 34 && tatooine_imperial_trainer_2b_handleBranch34(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 39 && tatooine_imperial_trainer_2b_handleBranch39(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 40 && tatooine_imperial_trainer_2b_handleBranch40(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 42 && tatooine_imperial_trainer_2b_handleBranch42(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 44 && tatooine_imperial_trainer_2b_handleBranch44(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 46 && tatooine_imperial_trainer_2b_handleBranch46(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 49 && tatooine_imperial_trainer_2b_handleBranch49(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 50 && tatooine_imperial_trainer_2b_handleBranch50(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 51 && tatooine_imperial_trainer_2b_handleBranch51(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 52 && tatooine_imperial_trainer_2b_handleBranch52(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 55 && tatooine_imperial_trainer_2b_handleBranch55(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 56 && tatooine_imperial_trainer_2b_handleBranch56(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 57 && tatooine_imperial_trainer_2b_handleBranch57(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 59 && tatooine_imperial_trainer_2b_handleBranch59(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 60 && tatooine_imperial_trainer_2b_handleBranch60(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 61 && tatooine_imperial_trainer_2b_handleBranch61(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 63 && tatooine_imperial_trainer_2b_handleBranch63(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 64 && tatooine_imperial_trainer_2b_handleBranch64(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 65 && tatooine_imperial_trainer_2b_handleBranch65(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 66 && tatooine_imperial_trainer_2b_handleBranch66(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 67 && tatooine_imperial_trainer_2b_handleBranch67(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 68 && tatooine_imperial_trainer_2b_handleBranch68(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 69 && tatooine_imperial_trainer_2b_handleBranch69(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 70 && tatooine_imperial_trainer_2b_handleBranch70(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 71 && tatooine_imperial_trainer_2b_handleBranch71(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 72 && tatooine_imperial_trainer_2b_handleBranch72(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 73 && tatooine_imperial_trainer_2b_handleBranch73(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 74 && tatooine_imperial_trainer_2b_handleBranch74(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.tatooine_imperial_trainer_2b.branchId");
        return SCRIPT_CONTINUE;
    }
}
