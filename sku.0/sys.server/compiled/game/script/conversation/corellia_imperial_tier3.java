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

public class corellia_imperial_tier3 extends script.base_script
{
    public corellia_imperial_tier3()
    {
    }
    public static String c_stringFile = "conversation/corellia_imperial_tier3";
    public boolean corellia_imperial_tier3_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean corellia_imperial_tier3_condition_isCorrectFaction(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "pilot_imperial_navy_novice"));
    }
    public boolean corellia_imperial_tier3_condition_isReadyForTier3(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_flags.hasCompletedTierTwo(player);
    }
    public boolean corellia_imperial_tier3_condition_isOnQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasQuest(player));
    }
    public boolean corellia_imperial_tier3_condition_failedQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasFailedQuestRecursive(player, "patrol", "corellia_imperial_tier3_1") || space_quest.hasAbortedQuestRecursive(player, "patrol", "corellia_imperial_tier3_1"))
        {
            return true;
        }
        return false;
    }
    public boolean corellia_imperial_tier3_condition_failedQuestTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasFailedQuestRecursive(player, "recovery", "corellia_imperial_tier3_2") || space_quest.hasAbortedQuestRecursive(player, "recovery", "corellia_imperial_tier3_2"))
        {
            return true;
        }
        return false;
    }
    public boolean corellia_imperial_tier3_condition_failedQuestThree(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasFailedQuestRecursive(player, "rescue", "corellia_imperial_tier3_3") || space_quest.hasAbortedQuestRecursive(player, "rescue", "corellia_imperial_tier3_3"))
        {
            return true;
        }
        return false;
    }
    public boolean corellia_imperial_tier3_condition_failedQuestFour(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasFailedQuestRecursive(player, "recovery", "corellia_imperial_tier3_4") || space_quest.hasAbortedQuestRecursive(player, "recovery", "corellia_imperial_tier3_4"))
        {
            return true;
        }
        return false;
    }
    public boolean corellia_imperial_tier3_condition_onMyTrack(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_flags.isSpaceTrack(player, space_flags.IMPERIAL_CORELLIA);
    }
    public boolean corellia_imperial_tier3_condition_isPilot(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_flags.hasAnyPilotSkill(player);
    }
    public boolean corellia_imperial_tier3_condition_collectingQuestOneReward(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasCompletedQuestRecursive(player, "patrol", "corellia_imperial_tier3_1"))
        {
            return false;
        }
        if (hasSkill(player, "pilot_imperial_navy_starships_03") || hasSkill(player, "pilot_imperial_navy_procedures_03") || hasSkill(player, "pilot_imperial_navy_weapons_03") || hasSkill(player, "pilot_imperial_navy_droid_03"))
        {
            return false;
        }
        return true;
    }
    public boolean corellia_imperial_tier3_condition_collectingQuestTwoReward(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasCompletedQuestRecursive(player, "recovery", "corellia_imperial_tier3_2"))
        {
            return false;
        }
        int skillCount = 0;
        if (hasSkill(player, "pilot_imperial_navy_starships_03"))
        {
            skillCount++;
        }
        if (hasSkill(player, "pilot_imperial_navy_procedures_03"))
        {
            skillCount++;
        }
        if (hasSkill(player, "pilot_imperial_navy_weapons_03"))
        {
            skillCount++;
        }
        if (hasSkill(player, "pilot_imperial_navy_droid_03"))
        {
            skillCount++;
        }
        if (skillCount < 2)
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean corellia_imperial_tier3_condition_collectingQuestThreeReward(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasCompletedQuestRecursive(player, "rescue", "corellia_imperial_tier3_3"))
        {
            return false;
        }
        int skillCount = 0;
        if (hasSkill(player, "pilot_imperial_navy_starships_03"))
        {
            skillCount++;
        }
        if (hasSkill(player, "pilot_imperial_navy_procedures_03"))
        {
            skillCount++;
        }
        if (hasSkill(player, "pilot_imperial_navy_weapons_03"))
        {
            skillCount++;
        }
        if (hasSkill(player, "pilot_imperial_navy_droid_03"))
        {
            skillCount++;
        }
        if (skillCount < 3)
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean corellia_imperial_tier3_condition_collectingQuestFourReward(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasCompletedQuestRecursive(player, "recovery", "corellia_imperial_tier3_4"))
        {
            return false;
        }
        if (!hasSkill(player, "pilot_imperial_navy_starships_03"))
        {
            return true;
        }
        if (!hasSkill(player, "pilot_imperial_navy_procedures_03"))
        {
            return true;
        }
        if (!hasSkill(player, "pilot_imperial_navy_weapons_03"))
        {
            return true;
        }
        if (!hasSkill(player, "pilot_imperial_navy_droid_03"))
        {
            return true;
        }
        return false;
    }
    public boolean corellia_imperial_tier3_condition_hasNegativeFaction(obj_id player, obj_id npc) throws InterruptedException
    {
        return (factions.getFactionStanding(player, factions.FACTION_IMPERIAL) < 0.0f);
    }
    public boolean corellia_imperial_tier3_condition_hasCompletedQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasCompletedQuestRecursive(player, "patrol", "corellia_imperial_tier3_1"));
    }
    public boolean corellia_imperial_tier3_condition_hasCompletedQuestTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasCompletedQuestRecursive(player, "recovery", "corellia_imperial_tier3_2"));
    }
    public boolean corellia_imperial_tier3_condition_hasCompletedQuestThree(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasCompletedQuestRecursive(player, "rescue", "corellia_imperial_tier3_3"));
    }
    public boolean corellia_imperial_tier3_condition_hasCompletedQuestFour(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasCompletedQuestRecursive(player, "recovery", "corellia_imperial_tier3_4"));
    }
    public boolean corellia_imperial_tier3_condition_hasStarship03Skill(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "pilot_imperial_navy_starships_03"));
    }
    public boolean corellia_imperial_tier3_condition_hasProcedure03Skill(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "pilot_imperial_navy_procedures_03"));
    }
    public boolean corellia_imperial_tier3_condition_hasWeapon03Skill(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "pilot_imperial_navy_weapons_03"));
    }
    public boolean corellia_imperial_tier3_condition_hasDroid03Skill(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "pilot_imperial_navy_droid_03"));
    }
    public void corellia_imperial_tier3_action_grantQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "patrol", "corellia_imperial_tier3_1");
    }
    public void corellia_imperial_tier3_action_grantQuestTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "recovery", "corellia_imperial_tier3_2");
    }
    public void corellia_imperial_tier3_action_grantQuestThree(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "rescue", "corellia_imperial_tier3_3");
    }
    public void corellia_imperial_tier3_action_grantQuestFour(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "recovery", "corellia_imperial_tier3_4");
    }
    public void corellia_imperial_tier3_action_rewardForQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasReceivedReward(player, "patrol", "corellia_imperial_tier3_1"))
        {
            factions.addFactionStanding(player, factions.FACTION_IMPERIAL, 100.0f);
            space_quest.giveReward(player, "patrol", "corellia_imperial_tier3_1", 25000, "object/tangible/ship/components/weapon_capacitor/cap_mission_reward_imperial_rendili_k_class.iff");
        }
    }
    public void corellia_imperial_tier3_action_rewardForQuestTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasReceivedReward(player, "recovery", "corellia_imperial_tier3_2"))
        {
            factions.addFactionStanding(player, factions.FACTION_IMPERIAL, 100.0f);
            space_quest.giveReward(player, "recovery", "corellia_imperial_tier3_2", 25000, "object/tangible/ship/components/droid_interface/ddi_mission_reward_imperial_sfs_military_grade.iff");
        }
    }
    public void corellia_imperial_tier3_action_rewardForQuestThree(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasReceivedReward(player, "rescue", "corellia_imperial_tier3_3"))
        {
            factions.addFactionStanding(player, factions.FACTION_IMPERIAL, 100.0f);
            space_quest.giveReward(player, "rescue", "corellia_imperial_tier3_3", 25000, "object/tangible/ship/components/reactor/rct_mission_reward_imperial_sds_high_output.iff");
        }
    }
    public void corellia_imperial_tier3_action_rewardForQuestFour(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasReceivedReward(player, "recovery", "corellia_imperial_tier3_4"))
        {
            factions.addFactionStanding(player, factions.FACTION_IMPERIAL, 100.0f);
            space_quest.giveReward(player, "recovery", "corellia_imperial_tier3_4", 25000, "object/tangible/ship/components/engine/eng_mission_reward_imperial_cygnus_megadrive.iff");
        }
    }
    public void corellia_imperial_tier3_action_grantProceduresSkill(obj_id player, obj_id npc) throws InterruptedException
    {
        skill.noisyGrantSkill(player, "pilot_imperial_navy_procedures_03");
    }
    public void corellia_imperial_tier3_action_grantDroidSkill(obj_id player, obj_id npc) throws InterruptedException
    {
        skill.noisyGrantSkill(player, "pilot_imperial_navy_droid_03");
    }
    public void corellia_imperial_tier3_action_grantWeaponSkill(obj_id player, obj_id npc) throws InterruptedException
    {
        skill.noisyGrantSkill(player, "pilot_imperial_navy_weapons_03");
    }
    public void corellia_imperial_tier3_action_grantStarshipSkill(obj_id player, obj_id npc) throws InterruptedException
    {
        skill.noisyGrantSkill(player, "pilot_imperial_navy_starships_03");
    }
    public int corellia_imperial_tier3_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_77e48d5b"))
        {
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_accusingly");
                string_id message = new string_id(c_stringFile, "s_28d87347");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_765d3767");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_tier3.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_tier3_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_765d3767"))
        {
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "laugh_titter");
                string_id message = new string_id(c_stringFile, "s_50e8d95e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_900f01d1");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_tier3.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_tier3_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_900f01d1"))
        {
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "smack_self");
                string_id message = new string_id(c_stringFile, "s_c82eaa27");
                utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_tier3_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_a5d90aff"))
        {
            doAnimationAction(player, "shrug_hands");
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_away");
                corellia_imperial_tier3_action_grantQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_1f1da1b8");
                utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_9c5ae9a"))
        {
            doAnimationAction(player, "shrug_shoulders");
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_30b6b17c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_883481e3");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_tier3.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_tier3_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_883481e3"))
        {
            doAnimationAction(player, "point_to_self");
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                corellia_imperial_tier3_action_grantQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_73c4833");
                utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_tier3_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_b713f64f"))
        {
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                corellia_imperial_tier3_action_grantQuestTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_823cc9e1");
                utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_e6e69be4"))
        {
            doAnimationAction(player, "slump_head");
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                corellia_imperial_tier3_action_grantQuestTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_d32315a8");
                utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_tier3_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_dbb3a834"))
        {
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                corellia_imperial_tier3_action_grantQuestThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_4c47ba68");
                utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_d8a7505"))
        {
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_accusingly");
                corellia_imperial_tier3_action_grantQuestThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_cf274903");
                utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_tier3_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_d8db54f6"))
        {
            doAnimationAction(player, "point_to_self");
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
            {
                corellia_imperial_tier3_action_grantQuestFour(player, npc);
                string_id message = new string_id(c_stringFile, "s_c0cca856");
                utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_31dd2d63"))
        {
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wave_on_dismissing");
                corellia_imperial_tier3_action_grantQuestFour(player, npc);
                string_id message = new string_id(c_stringFile, "s_fdbdc3c3");
                utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_tier3_handleBranch23(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_b200234c"))
        {
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_b695af6b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2c7b60e");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_tier3.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_tier3_handleBranch24(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_2c7b60e"))
        {
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_2b261439");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_72418db3");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_tier3.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_tier3_handleBranch25(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_72418db3"))
        {
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_hands");
                string_id message = new string_id(c_stringFile, "s_a1b009ed");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8c751827");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_tier3.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_tier3_handleBranch26(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_8c751827"))
        {
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_accusingly");
                string_id message = new string_id(c_stringFile, "s_a4f70dc9");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.corellia_imperial_tier3.branchId", 27);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_tier3_handleBranch27(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_8efde2ae"))
        {
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_8dcf50f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_132");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_tier3.branchId", 28);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_tier3_handleBranch28(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_132"))
        {
            if (!corellia_imperial_tier3_condition_hasStarship03Skill(player, npc))
            {
                corellia_imperial_tier3_action_grantStarshipSkill(player, npc);
                string_id message = new string_id(c_stringFile, "s_133");
                utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (!corellia_imperial_tier3_condition_hasWeapon03Skill(player, npc))
            {
                corellia_imperial_tier3_action_grantWeaponSkill(player, npc);
                string_id message = new string_id(c_stringFile, "s_134");
                utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (!corellia_imperial_tier3_condition_hasProcedure03Skill(player, npc))
            {
                corellia_imperial_tier3_action_grantProceduresSkill(player, npc);
                string_id message = new string_id(c_stringFile, "s_135");
                utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
            {
                corellia_imperial_tier3_action_grantDroidSkill(player, npc);
                string_id message = new string_id(c_stringFile, "s_136");
                utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_tier3_handleBranch33(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_a6944d2"))
        {
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_bd1d7bf2");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b779a3");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_tier3.branchId", 34);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_ddfa7bdb"))
        {
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_a1314746");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b779a3");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_tier3.branchId", 34);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_tier3_handleBranch34(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_b779a3"))
        {
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "pose_proudly");
                string_id message = new string_id(c_stringFile, "s_62ccc48d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_107391a2");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_tier3.branchId", 35);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_tier3_handleBranch35(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_107391a2"))
        {
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "smack_self");
                string_id message = new string_id(c_stringFile, "s_47132c0f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_132");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_tier3.branchId", 28);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_tier3_handleBranch36(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_132"))
        {
            if (!corellia_imperial_tier3_condition_hasStarship03Skill(player, npc))
            {
                corellia_imperial_tier3_action_grantStarshipSkill(player, npc);
                string_id message = new string_id(c_stringFile, "s_133");
                utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (!corellia_imperial_tier3_condition_hasWeapon03Skill(player, npc))
            {
                corellia_imperial_tier3_action_grantWeaponSkill(player, npc);
                string_id message = new string_id(c_stringFile, "s_134");
                utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (!corellia_imperial_tier3_condition_hasProcedure03Skill(player, npc))
            {
                corellia_imperial_tier3_action_grantProceduresSkill(player, npc);
                string_id message = new string_id(c_stringFile, "s_135");
                utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
            {
                corellia_imperial_tier3_action_grantDroidSkill(player, npc);
                string_id message = new string_id(c_stringFile, "s_136");
                utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_tier3_handleBranch37(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_b779a3"))
        {
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "pose_proudly");
                string_id message = new string_id(c_stringFile, "s_62ccc48d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_107391a2");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_tier3.branchId", 35);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_tier3_handleBranch38(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_657cb9bd"))
        {
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_91e2ca48");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1c74e041");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_tier3.branchId", 39);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_tier3_handleBranch39(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1c74e041"))
        {
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_26b0b5d1");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f7a9cb6c");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_tier3.branchId", 40);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_tier3_handleBranch40(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_f7a9cb6c"))
        {
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "laugh_cackle");
                string_id message = new string_id(c_stringFile, "s_847e468b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_132");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_tier3.branchId", 28);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_tier3_handleBranch41(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_132"))
        {
            if (!corellia_imperial_tier3_condition_hasStarship03Skill(player, npc))
            {
                corellia_imperial_tier3_action_grantStarshipSkill(player, npc);
                string_id message = new string_id(c_stringFile, "s_133");
                utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (!corellia_imperial_tier3_condition_hasWeapon03Skill(player, npc))
            {
                corellia_imperial_tier3_action_grantWeaponSkill(player, npc);
                string_id message = new string_id(c_stringFile, "s_134");
                utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (!corellia_imperial_tier3_condition_hasProcedure03Skill(player, npc))
            {
                corellia_imperial_tier3_action_grantProceduresSkill(player, npc);
                string_id message = new string_id(c_stringFile, "s_135");
                utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
            {
                corellia_imperial_tier3_action_grantDroidSkill(player, npc);
                string_id message = new string_id(c_stringFile, "s_136");
                utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_tier3_handleBranch42(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_84bc2cb6"))
        {
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_726bfa38");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_132");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_tier3.branchId", 28);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1d2c73ce"))
        {
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "pound_fist_chest");
                string_id message = new string_id(c_stringFile, "s_ea45b3c5");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d9391333");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_tier3.branchId", 44);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_tier3_handleBranch43(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_132"))
        {
            if (!corellia_imperial_tier3_condition_hasStarship03Skill(player, npc))
            {
                corellia_imperial_tier3_action_grantStarshipSkill(player, npc);
                string_id message = new string_id(c_stringFile, "s_133");
                utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (!corellia_imperial_tier3_condition_hasWeapon03Skill(player, npc))
            {
                corellia_imperial_tier3_action_grantWeaponSkill(player, npc);
                string_id message = new string_id(c_stringFile, "s_134");
                utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (!corellia_imperial_tier3_condition_hasProcedure03Skill(player, npc))
            {
                corellia_imperial_tier3_action_grantProceduresSkill(player, npc);
                string_id message = new string_id(c_stringFile, "s_135");
                utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
            {
                corellia_imperial_tier3_action_grantDroidSkill(player, npc);
                string_id message = new string_id(c_stringFile, "s_136");
                utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_tier3_handleBranch44(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_d9391333"))
        {
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1dab174a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_132");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_tier3.branchId", 28);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_tier3_handleBranch45(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_132"))
        {
            if (!corellia_imperial_tier3_condition_hasStarship03Skill(player, npc))
            {
                corellia_imperial_tier3_action_grantStarshipSkill(player, npc);
                string_id message = new string_id(c_stringFile, "s_133");
                utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (!corellia_imperial_tier3_condition_hasWeapon03Skill(player, npc))
            {
                corellia_imperial_tier3_action_grantWeaponSkill(player, npc);
                string_id message = new string_id(c_stringFile, "s_134");
                utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (!corellia_imperial_tier3_condition_hasProcedure03Skill(player, npc))
            {
                corellia_imperial_tier3_action_grantProceduresSkill(player, npc);
                string_id message = new string_id(c_stringFile, "s_135");
                utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
            {
                corellia_imperial_tier3_action_grantDroidSkill(player, npc);
                string_id message = new string_id(c_stringFile, "s_136");
                utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_tier3_handleBranch46(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_fd236a42"))
        {
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_disgust");
                string_id message = new string_id(c_stringFile, "s_b622a494");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d547e29b");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_19be2c59");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_tier3.branchId", 47);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_tier3_handleBranch47(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_d547e29b"))
        {
            doAnimationAction(player, "salute1");
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_ecfe85ae");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7af5dab4");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_tier3.branchId", 48);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_19be2c59"))
        {
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_accusingly");
                string_id message = new string_id(c_stringFile, "s_18509eaa");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b93b0c31");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_tier3.branchId", 54);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_tier3_handleBranch48(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_7af5dab4"))
        {
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wave_on_dismissing");
                string_id message = new string_id(c_stringFile, "s_7bef2106");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1969477d");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_tier3.branchId", 49);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_tier3_handleBranch49(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1969477d"))
        {
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_accusingly");
                string_id message = new string_id(c_stringFile, "s_53126084");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f2993921");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_tier3.branchId", 50);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_tier3_handleBranch50(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_f2993921"))
        {
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_multiple");
                string_id message = new string_id(c_stringFile, "s_586a7d64");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_12c769cf");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_tier3.branchId", 51);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_tier3_handleBranch51(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_12c769cf"))
        {
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_daab08d3");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.corellia_imperial_tier3.branchId", 52);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_tier3_handleBranch52(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_b6b25f13"))
        {
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_up");
                corellia_imperial_tier3_action_grantQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_e3bbe83c");
                utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_tier3_handleBranch54(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_b93b0c31"))
        {
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_ecfe85ae");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7af5dab4");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_tier3.branchId", 48);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_tier3_handleBranch55(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_5eb7a1e3"))
        {
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_fe5bcaa2");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2ef3848a");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_tier3.branchId", 56);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_tier3_handleBranch56(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_2ef3848a"))
        {
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_shoulders");
                string_id message = new string_id(c_stringFile, "s_1bf62740");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7c05dd60");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_tier3.branchId", 57);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_tier3_handleBranch57(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_7c05dd60"))
        {
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "snap_finger2");
                string_id message = new string_id(c_stringFile, "s_22f895e3");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_44750833");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_tier3.branchId", 58);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_tier3_handleBranch58(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_44750833"))
        {
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1e26f934");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_24f21f71");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_tier3.branchId", 59);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_tier3_handleBranch59(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_24f21f71"))
        {
            doAnimationAction(player, "salute1");
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                corellia_imperial_tier3_action_grantQuestTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_bc65ef6a");
                utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_tier3_handleBranch61(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_ecd826ee"))
        {
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_3af86b40");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d0f9703");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_tier3.branchId", 62);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_tier3_handleBranch62(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_d0f9703"))
        {
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "pound_fist_palm");
                string_id message = new string_id(c_stringFile, "s_8bbc7303");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_af020de3");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_tier3.branchId", 63);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_tier3_handleBranch63(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_af020de3"))
        {
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_85e02cc1");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d1e44f54");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_tier3.branchId", 64);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_tier3_handleBranch64(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_d1e44f54"))
        {
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_shoulders");
                corellia_imperial_tier3_action_grantQuestThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_66ebf9d3");
                utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_tier3_handleBranch66(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_fc27931b"))
        {
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "slow_down");
                string_id message = new string_id(c_stringFile, "s_27e5eff7");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c9015929");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_tier3.branchId", 67);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_tier3_handleBranch67(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c9015929"))
        {
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_36900273");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cd445db2");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_tier3.branchId", 68);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_tier3_handleBranch68(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_cd445db2"))
        {
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                corellia_imperial_tier3_action_grantQuestFour(player, npc);
                string_id message = new string_id(c_stringFile, "s_271e95b3");
                utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_tier3_handleBranch70(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_d2e23af2"))
        {
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_shoulders");
                string_id message = new string_id(c_stringFile, "s_91cf92a7");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_24a3fa0c");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_tier3.branchId", 71);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_tier3_handleBranch71(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_24a3fa0c"))
        {
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_hands");
                string_id message = new string_id(c_stringFile, "s_c18b617e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1474f13c");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_tier3.branchId", 72);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_tier3_handleBranch72(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1474f13c"))
        {
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_to_self");
                string_id message = new string_id(c_stringFile, "s_6d0b02b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9b5a6b23");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_tier3.branchId", 73);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_tier3_handleBranch73(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_9b5a6b23"))
        {
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_c65e1fa7");
                utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
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
            detachScript(self, "conversation.corellia_imperial_tier3");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_SPACE_INTERESTING);
        factions.setFaction(self, factions.FACTION_IMPERIAL);
        setName(self, "Haymir Rendundi");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_SPACE_INTERESTING);
        factions.setFaction(self, factions.FACTION_IMPERIAL);
        setName(self, "Haymir Rendundi");
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
        detachScript(self, "conversation.corellia_imperial_tier3");
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
        if (!corellia_imperial_tier3_condition_isPilot(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_8ccf2f5f");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (!corellia_imperial_tier3_condition_isCorrectFaction(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_7bcc420b");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (!corellia_imperial_tier3_condition_onMyTrack(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_9be798dc");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (!corellia_imperial_tier3_condition_isReadyForTier3(player, npc))
        {
            doAnimationAction(npc, "point_away");
            string_id message = new string_id(c_stringFile, "s_8eaae252");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corellia_imperial_tier3_condition_hasNegativeFaction(player, npc))
        {
            doAnimationAction(npc, "slit_throat");
            string_id message = new string_id(c_stringFile, "s_ff9d26dd");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_77e48d5b");
                }
                utils.setScriptVar(player, "conversation.corellia_imperial_tier3.branchId", 5);
                npcStartConversation(player, npc, "corellia_imperial_tier3", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_imperial_tier3_condition_isOnQuest(player, npc))
        {
            doAnimationAction(npc, "point_accusingly");
            string_id message = new string_id(c_stringFile, "s_2e69b8ed");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corellia_imperial_tier3_condition_failedQuestOne(player, npc))
        {
            doAnimationAction(npc, "shake_head_disgust");
            string_id message = new string_id(c_stringFile, "s_c6844d2a");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_a5d90aff");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_9c5ae9a");
                }
                utils.setScriptVar(player, "conversation.corellia_imperial_tier3.branchId", 10);
                npcStartConversation(player, npc, "corellia_imperial_tier3", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_imperial_tier3_condition_failedQuestTwo(player, npc))
        {
            doAnimationAction(npc, "hold_nose");
            string_id message = new string_id(c_stringFile, "s_64d5bea6");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_b713f64f");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_e6e69be4");
                }
                utils.setScriptVar(player, "conversation.corellia_imperial_tier3.branchId", 14);
                npcStartConversation(player, npc, "corellia_imperial_tier3", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_imperial_tier3_condition_failedQuestThree(player, npc))
        {
            doAnimationAction(npc, "point_accusingly");
            string_id message = new string_id(c_stringFile, "s_3f9b3561");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_dbb3a834");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_d8a7505");
                }
                utils.setScriptVar(player, "conversation.corellia_imperial_tier3.branchId", 17);
                npcStartConversation(player, npc, "corellia_imperial_tier3", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_imperial_tier3_condition_failedQuestFour(player, npc))
        {
            doAnimationAction(npc, "shake_head_disgust");
            string_id message = new string_id(c_stringFile, "s_38be6beb");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_d8db54f6");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_31dd2d63");
                }
                utils.setScriptVar(player, "conversation.corellia_imperial_tier3.branchId", 20);
                npcStartConversation(player, npc, "corellia_imperial_tier3", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_imperial_tier3_condition_collectingQuestOneReward(player, npc))
        {
            doAnimationAction(npc, "point_accusingly");
            corellia_imperial_tier3_action_rewardForQuestOne(player, npc);
            string_id message = new string_id(c_stringFile, "s_14060a1c");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_b200234c");
                }
                utils.setScriptVar(player, "conversation.corellia_imperial_tier3.branchId", 23);
                npcStartConversation(player, npc, "corellia_imperial_tier3", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_imperial_tier3_condition_collectingQuestTwoReward(player, npc))
        {
            corellia_imperial_tier3_action_rewardForQuestTwo(player, npc);
            string_id message = new string_id(c_stringFile, "s_6cef5164");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_a6944d2");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_ddfa7bdb");
                }
                utils.setScriptVar(player, "conversation.corellia_imperial_tier3.branchId", 33);
                npcStartConversation(player, npc, "corellia_imperial_tier3", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_imperial_tier3_condition_collectingQuestThreeReward(player, npc))
        {
            corellia_imperial_tier3_action_rewardForQuestThree(player, npc);
            string_id message = new string_id(c_stringFile, "s_d3306841");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_657cb9bd");
                }
                utils.setScriptVar(player, "conversation.corellia_imperial_tier3.branchId", 38);
                npcStartConversation(player, npc, "corellia_imperial_tier3", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_imperial_tier3_condition_collectingQuestFourReward(player, npc))
        {
            corellia_imperial_tier3_action_rewardForQuestFour(player, npc);
            string_id message = new string_id(c_stringFile, "s_dd4ea40c");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_84bc2cb6");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1d2c73ce");
                }
                utils.setScriptVar(player, "conversation.corellia_imperial_tier3.branchId", 42);
                npcStartConversation(player, npc, "corellia_imperial_tier3", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (!corellia_imperial_tier3_condition_hasCompletedQuestOne(player, npc))
        {
            doAnimationAction(npc, "laugh_titter");
            string_id message = new string_id(c_stringFile, "s_1c0f4063");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_fd236a42");
                }
                utils.setScriptVar(player, "conversation.corellia_imperial_tier3.branchId", 46);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "corellia_imperial_tier3", null, pp, responses);
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
        if (!corellia_imperial_tier3_condition_hasCompletedQuestTwo(player, npc))
        {
            doAnimationAction(npc, "beckon");
            string_id message = new string_id(c_stringFile, "s_fdda16c7");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_5eb7a1e3");
                }
                utils.setScriptVar(player, "conversation.corellia_imperial_tier3.branchId", 55);
                npcStartConversation(player, npc, "corellia_imperial_tier3", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (!corellia_imperial_tier3_condition_hasCompletedQuestThree(player, npc))
        {
            doAnimationAction(npc, "beckon");
            string_id message = new string_id(c_stringFile, "s_424f249d");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_ecd826ee");
                }
                utils.setScriptVar(player, "conversation.corellia_imperial_tier3.branchId", 61);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "corellia_imperial_tier3", null, pp, responses);
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
        if (!corellia_imperial_tier3_condition_hasCompletedQuestFour(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_799d2952");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_fc27931b");
                }
                utils.setScriptVar(player, "conversation.corellia_imperial_tier3.branchId", 66);
                npcStartConversation(player, npc, "corellia_imperial_tier3", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "salute1");
            doAnimationAction(player, "salute1");
            string_id message = new string_id(c_stringFile, "s_daaff7a");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_imperial_tier3_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_d2e23af2");
                }
                utils.setScriptVar(player, "conversation.corellia_imperial_tier3.branchId", 70);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "corellia_imperial_tier3", null, pp, responses);
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
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("corellia_imperial_tier3"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
        if (branchId == 5 && corellia_imperial_tier3_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && corellia_imperial_tier3_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && corellia_imperial_tier3_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && corellia_imperial_tier3_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && corellia_imperial_tier3_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && corellia_imperial_tier3_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && corellia_imperial_tier3_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && corellia_imperial_tier3_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && corellia_imperial_tier3_handleBranch23(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && corellia_imperial_tier3_handleBranch24(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && corellia_imperial_tier3_handleBranch25(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && corellia_imperial_tier3_handleBranch26(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 27 && corellia_imperial_tier3_handleBranch27(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 28 && corellia_imperial_tier3_handleBranch28(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 33 && corellia_imperial_tier3_handleBranch33(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 34 && corellia_imperial_tier3_handleBranch34(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 35 && corellia_imperial_tier3_handleBranch35(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 36 && corellia_imperial_tier3_handleBranch36(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 37 && corellia_imperial_tier3_handleBranch37(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 38 && corellia_imperial_tier3_handleBranch38(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 39 && corellia_imperial_tier3_handleBranch39(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 40 && corellia_imperial_tier3_handleBranch40(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 41 && corellia_imperial_tier3_handleBranch41(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 42 && corellia_imperial_tier3_handleBranch42(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 43 && corellia_imperial_tier3_handleBranch43(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 44 && corellia_imperial_tier3_handleBranch44(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 45 && corellia_imperial_tier3_handleBranch45(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 46 && corellia_imperial_tier3_handleBranch46(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 47 && corellia_imperial_tier3_handleBranch47(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 48 && corellia_imperial_tier3_handleBranch48(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 49 && corellia_imperial_tier3_handleBranch49(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 50 && corellia_imperial_tier3_handleBranch50(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 51 && corellia_imperial_tier3_handleBranch51(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 52 && corellia_imperial_tier3_handleBranch52(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 54 && corellia_imperial_tier3_handleBranch54(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 55 && corellia_imperial_tier3_handleBranch55(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 56 && corellia_imperial_tier3_handleBranch56(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 57 && corellia_imperial_tier3_handleBranch57(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 58 && corellia_imperial_tier3_handleBranch58(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 59 && corellia_imperial_tier3_handleBranch59(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 61 && corellia_imperial_tier3_handleBranch61(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 62 && corellia_imperial_tier3_handleBranch62(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 63 && corellia_imperial_tier3_handleBranch63(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 64 && corellia_imperial_tier3_handleBranch64(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 66 && corellia_imperial_tier3_handleBranch66(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 67 && corellia_imperial_tier3_handleBranch67(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 68 && corellia_imperial_tier3_handleBranch68(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 70 && corellia_imperial_tier3_handleBranch70(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 71 && corellia_imperial_tier3_handleBranch71(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 72 && corellia_imperial_tier3_handleBranch72(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 73 && corellia_imperial_tier3_handleBranch73(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.corellia_imperial_tier3.branchId");
        return SCRIPT_CONTINUE;
    }
}
