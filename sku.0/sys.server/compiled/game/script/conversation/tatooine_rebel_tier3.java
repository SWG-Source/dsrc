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

public class tatooine_rebel_tier3 extends script.base_script
{
    public tatooine_rebel_tier3()
    {
    }
    public static String c_stringFile = "conversation/tatooine_rebel_tier3";
    public boolean tatooine_rebel_tier3_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean tatooine_rebel_tier3_condition_isCorrectFaction(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "pilot_rebel_navy_novice"));
    }
    public boolean tatooine_rebel_tier3_condition_isReadyForTier3(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_flags.hasCompletedTierTwo(player) && space_flags.hasSpaceFlag(player, "ekerPilot"))
        {
            return true;
        }
        return false;
    }
    public boolean tatooine_rebel_tier3_condition_isOnQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasQuest(player));
    }
    public boolean tatooine_rebel_tier3_condition_failedQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasFailedQuestRecursive(player, "recovery", "tatooine_rebel_tier3_1") || space_quest.hasAbortedQuestRecursive(player, "recovery", "tatooine_rebel_tier3_1"))
        {
            return true;
        }
        return false;
    }
    public boolean tatooine_rebel_tier3_condition_failedQuestTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasFailedQuestRecursive(player, "inspect", "tatooine_rebel_tier3_2") || space_quest.hasAbortedQuestRecursive(player, "inspect", "tatooine_rebel_tier3_2"))
        {
            return true;
        }
        return false;
    }
    public boolean tatooine_rebel_tier3_condition_failedQuestThree(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasFailedQuestRecursive(player, "delivery", "tatooine_rebel_tier3_3") || space_quest.hasAbortedQuestRecursive(player, "delivery", "tatooine_rebel_tier3_3"))
        {
            return true;
        }
        return false;
    }
    public boolean tatooine_rebel_tier3_condition_failedQuestFour(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasFailedQuestRecursive(player, "assassinate", "tatooine_rebel_tier3_4") || space_quest.hasAbortedQuestRecursive(player, "assassinate", "tatooine_rebel_tier3_4"))
        {
            return true;
        }
        return false;
    }
    public boolean tatooine_rebel_tier3_condition_isReadyForTier4(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_flags.hasCompletedTierThree(player) && space_flags.hasSpaceFlag(player, "ekerPilot") && space_quest.hasReceivedReward(player, "assassinate", "tatooine_rebel_tier3_4"))
        {
            return true;
        }
        return false;
    }
    public boolean tatooine_rebel_tier3_condition_hasAllTier3Skills(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_flags.hasCompletedTierThree(player))
        {
            return true;
        }
        return false;
    }
    public boolean tatooine_rebel_tier3_condition_collectingQuestOneReward(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasCompletedQuestRecursive(player, "recovery", "tatooine_rebel_tier3_1"))
        {
            return false;
        }
        if (hasSkill(player, "pilot_rebel_navy_starships_03") || hasSkill(player, "pilot_rebel_navy_procedures_03") || hasSkill(player, "pilot_rebel_navy_weapons_03") || hasSkill(player, "pilot_rebel_navy_droid_03"))
        {
            return false;
        }
        return true;
    }
    public boolean tatooine_rebel_tier3_condition_collectingQuestTwoReward(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasCompletedQuestRecursive(player, "inspect", "tatooine_rebel_tier3_2"))
        {
            return false;
        }
        int skillCount = 0;
        if (hasSkill(player, "pilot_rebel_navy_starships_03"))
        {
            skillCount++;
        }
        if (hasSkill(player, "pilot_rebel_navy_procedures_03"))
        {
            skillCount++;
        }
        if (hasSkill(player, "pilot_rebel_navy_weapons_03"))
        {
            skillCount++;
        }
        if (hasSkill(player, "pilot_rebel_navy_droid_03"))
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
    public boolean tatooine_rebel_tier3_condition_collectingQuestThreeReward(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasCompletedQuestRecursive(player, "delivery", "tatooine_rebel_tier3_3"))
        {
            return false;
        }
        int skillCount = 0;
        if (hasSkill(player, "pilot_rebel_navy_starships_03"))
        {
            skillCount++;
        }
        if (hasSkill(player, "pilot_rebel_navy_procedures_03"))
        {
            skillCount++;
        }
        if (hasSkill(player, "pilot_rebel_navy_weapons_03"))
        {
            skillCount++;
        }
        if (hasSkill(player, "pilot_rebel_navy_droid_03"))
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
    public boolean tatooine_rebel_tier3_condition_collectingQuestFourReward(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasCompletedQuestRecursive(player, "assassinate", "tatooine_rebel_tier3_4"))
        {
            return false;
        }
        if (!hasSkill(player, "pilot_rebel_navy_starships_03"))
        {
            return true;
        }
        if (!hasSkill(player, "pilot_rebel_navy_procedures_03"))
        {
            return true;
        }
        if (!hasSkill(player, "pilot_rebel_navy_weapons_03"))
        {
            return true;
        }
        if (!hasSkill(player, "pilot_rebel_navy_droid_03"))
        {
            return true;
        }
        return false;
    }
    public boolean tatooine_rebel_tier3_condition_onMyTrack(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_flags.isSpaceTrack(player, space_flags.REBEL_TATOOINE);
    }
    public boolean tatooine_rebel_tier3_condition_isPilot(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_flags.hasAnyPilotSkill(player);
    }
    public boolean tatooine_rebel_tier3_condition_hasCompletedQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasCompletedQuestRecursive(player, "recovery", "tatooine_rebel_tier3_1"));
    }
    public boolean tatooine_rebel_tier3_condition_hasCompletedQuestTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasCompletedQuestRecursive(player, "inspect", "tatooine_rebel_tier3_2"));
    }
    public boolean tatooine_rebel_tier3_condition_hasCompletedQuestThree(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasCompletedQuestRecursive(player, "delivery", "tatooine_rebel_tier3_3"));
    }
    public boolean tatooine_rebel_tier3_condition_hasCompletedQuestFour(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasCompletedQuestRecursive(player, "assassinate", "tatooine_rebel_tier3_4"));
    }
    public boolean tatooine_rebel_tier3_condition_hasNegativeFaction(obj_id player, obj_id npc) throws InterruptedException
    {
        return (factions.getFactionStanding(player, factions.FACTION_REBEL) < 0.0f);
    }
    public boolean tatooine_rebel_tier3_condition_hasStarship03Skill(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "pilot_rebel_navy_starships_03"));
    }
    public boolean tatooine_rebel_tier3_condition_hasProcedure03Skill(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "pilot_rebel_navy_procedures_03"));
    }
    public boolean tatooine_rebel_tier3_condition_hasWeapon03Skill(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "pilot_rebel_navy_weapons_03"));
    }
    public boolean tatooine_rebel_tier3_condition_hasDroid03Skill(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "pilot_rebel_navy_droid_03"));
    }
    public void tatooine_rebel_tier3_action_grantQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "recovery", "tatooine_rebel_tier3_1");
    }
    public void tatooine_rebel_tier3_action_grantQuestTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "inspect", "tatooine_rebel_tier3_2");
    }
    public void tatooine_rebel_tier3_action_grantQuestThree(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "delivery", "tatooine_rebel_tier3_3");
    }
    public void tatooine_rebel_tier3_action_grantQuestFour(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "assassinate", "tatooine_rebel_tier3_4");
    }
    public void tatooine_rebel_tier3_action_rewardForQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasReceivedReward(player, "recovery", "tatooine_rebel_tier3_1"))
        {
            space_quest.giveReward(player, "recovery", "tatooine_rebel_tier3_1", 25000, "object/tangible/ship/components/armor/arm_mission_reward_rebel_corellian_triplate.iff");
            factions.addFactionStanding(player, factions.FACTION_REBEL, 100.0f);
        }
    }
    public void tatooine_rebel_tier3_action_rewardForQuestTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasReceivedReward(player, "inspect", "tatooine_rebel_tier3_2"))
        {
            space_quest.giveReward(player, "inspect", "tatooine_rebel_tier3_2", 25000, "object/tangible/ship/components/weapon_capacitor/cap_mission_reward_rebel_qualdex_battery_array.iff");
            factions.addFactionStanding(player, factions.FACTION_REBEL, 100.0f);
        }
    }
    public void tatooine_rebel_tier3_action_rewardForQuestThree(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasReceivedReward(player, "delivery", "tatooine_rebel_tier3_3"))
        {
            space_quest.giveReward(player, "delivery", "tatooine_rebel_tier3_3", 25000, "object/tangible/ship/components/engine/eng_mission_reward_rebel_incom_military.iff");
            factions.addFactionStanding(player, factions.FACTION_REBEL, 100.0f);
        }
    }
    public void tatooine_rebel_tier3_action_rewardForQuestFour(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasReceivedReward(player, "assassinate", "tatooine_rebel_tier3_4"))
        {
            space_quest.giveReward(player, "assassinate", "tatooine_rebel_tier3_4", 25000, "object/tangible/ship/components/reactor/rct_mission_reward_rebel_slayn_hypervortex.iff");
            factions.addFactionStanding(player, factions.FACTION_REBEL, 100.0f);
        }
    }
    public void tatooine_rebel_tier3_action_grantProceduresSkill(obj_id player, obj_id npc) throws InterruptedException
    {
        skill.noisyGrantSkill(player, "pilot_rebel_navy_procedures_03");
    }
    public void tatooine_rebel_tier3_action_grantDroidSkill(obj_id player, obj_id npc) throws InterruptedException
    {
        skill.noisyGrantSkill(player, "pilot_rebel_navy_droid_03");
    }
    public void tatooine_rebel_tier3_action_grantWeaponSkill(obj_id player, obj_id npc) throws InterruptedException
    {
        skill.noisyGrantSkill(player, "pilot_rebel_navy_weapons_03");
    }
    public void tatooine_rebel_tier3_action_grantStarshipSkill(obj_id player, obj_id npc) throws InterruptedException
    {
        skill.noisyGrantSkill(player, "pilot_rebel_navy_starships_03");
    }
    public int tatooine_rebel_tier3_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_ecb17e60"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "slow_down");
                string_id message = new string_id(c_stringFile, "s_861c21de");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f8b02865");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_f8b02865"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_1d28ae3");
                utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_a68dfaa6"))
        {
            doAnimationAction(player, "shrug_hands");
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "stretch");
                tatooine_rebel_tier3_action_grantQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_652ff698");
                utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_9401fd7b"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                tatooine_rebel_tier3_action_grantQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_a53ea74c");
                utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_d85b3a9c"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "tap_head");
                tatooine_rebel_tier3_action_grantQuestTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_59552474");
                utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_87d7e908"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "check_wrist_device");
                tatooine_rebel_tier3_action_grantQuestTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_3eeaed57");
                utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_d454739a"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_accusingly");
                tatooine_rebel_tier3_action_grantQuestThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_5973a2b6");
                utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_9173548c"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                tatooine_rebel_tier3_action_grantQuestThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_665b67d1");
                utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_b40f340"))
        {
            doAnimationAction(player, "flex_biceps");
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_shoulders");
                tatooine_rebel_tier3_action_grantQuestFour(player, npc);
                string_id message = new string_id(c_stringFile, "s_d6643019");
                utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_9fb0046e"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_forward");
                tatooine_rebel_tier3_action_grantQuestFour(player, npc);
                string_id message = new string_id(c_stringFile, "s_9df42b04");
                utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_661"))
        {
            if (!tatooine_rebel_tier3_condition_hasStarship03Skill(player, npc))
            {
                tatooine_rebel_tier3_action_grantStarshipSkill(player, npc);
                string_id message = new string_id(c_stringFile, "s_662");
                utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_rebel_tier3_condition_hasWeapon03Skill(player, npc))
            {
                tatooine_rebel_tier3_action_grantWeaponSkill(player, npc);
                string_id message = new string_id(c_stringFile, "s_663");
                utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_rebel_tier3_condition_hasProcedure03Skill(player, npc))
            {
                tatooine_rebel_tier3_action_grantProceduresSkill(player, npc);
                string_id message = new string_id(c_stringFile, "s_664");
                utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                tatooine_rebel_tier3_action_grantDroidSkill(player, npc);
                string_id message = new string_id(c_stringFile, "s_665");
                utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch26(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_661"))
        {
            if (!tatooine_rebel_tier3_condition_hasStarship03Skill(player, npc))
            {
                tatooine_rebel_tier3_action_grantStarshipSkill(player, npc);
                string_id message = new string_id(c_stringFile, "s_662");
                utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_rebel_tier3_condition_hasWeapon03Skill(player, npc))
            {
                tatooine_rebel_tier3_action_grantWeaponSkill(player, npc);
                string_id message = new string_id(c_stringFile, "s_663");
                utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_rebel_tier3_condition_hasProcedure03Skill(player, npc))
            {
                tatooine_rebel_tier3_action_grantProceduresSkill(player, npc);
                string_id message = new string_id(c_stringFile, "s_664");
                utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                tatooine_rebel_tier3_action_grantDroidSkill(player, npc);
                string_id message = new string_id(c_stringFile, "s_665");
                utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch27(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_661"))
        {
            if (!tatooine_rebel_tier3_condition_hasStarship03Skill(player, npc))
            {
                tatooine_rebel_tier3_action_grantStarshipSkill(player, npc);
                string_id message = new string_id(c_stringFile, "s_662");
                utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_rebel_tier3_condition_hasWeapon03Skill(player, npc))
            {
                tatooine_rebel_tier3_action_grantWeaponSkill(player, npc);
                string_id message = new string_id(c_stringFile, "s_663");
                utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_rebel_tier3_condition_hasProcedure03Skill(player, npc))
            {
                tatooine_rebel_tier3_action_grantProceduresSkill(player, npc);
                string_id message = new string_id(c_stringFile, "s_664");
                utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                tatooine_rebel_tier3_action_grantDroidSkill(player, npc);
                string_id message = new string_id(c_stringFile, "s_665");
                utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch28(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_661"))
        {
            if (!tatooine_rebel_tier3_condition_hasStarship03Skill(player, npc))
            {
                tatooine_rebel_tier3_action_grantStarshipSkill(player, npc);
                string_id message = new string_id(c_stringFile, "s_662");
                utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_rebel_tier3_condition_hasWeapon03Skill(player, npc))
            {
                tatooine_rebel_tier3_action_grantWeaponSkill(player, npc);
                string_id message = new string_id(c_stringFile, "s_663");
                utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_rebel_tier3_condition_hasProcedure03Skill(player, npc))
            {
                tatooine_rebel_tier3_action_grantProceduresSkill(player, npc);
                string_id message = new string_id(c_stringFile, "s_664");
                utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                tatooine_rebel_tier3_action_grantDroidSkill(player, npc);
                string_id message = new string_id(c_stringFile, "s_665");
                utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch29(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_783af10a"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_be9caae0");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b9f4f973");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_81682525");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 30);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_5b000af8"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "hair_flip");
                string_id message = new string_id(c_stringFile, "s_64170652");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d2d48b62");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 100);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch30(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_b9f4f973"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_shoulders");
                string_id message = new string_id(c_stringFile, "s_e95511fe");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_79209e7");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1106a07b");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 31);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_81682525"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_shoulders");
                string_id message = new string_id(c_stringFile, "s_645464dc");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_130");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4c695dbd");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_11a59fbe");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 59);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch31(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_79209e7"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_no");
                string_id message = new string_id(c_stringFile, "s_c93b6a54");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8e632a96");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 32);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1106a07b"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "applause_polite");
                string_id message = new string_id(c_stringFile, "s_8539c0c6");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_53de9d8f");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 46);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch32(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_8e632a96"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_94ece311");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_75913251");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 33);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch33(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_75913251"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_58159522");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 34);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch34(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_77e48d5b"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_4d74f4db");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7f0ba495");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 35);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch35(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_7f0ba495"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_d1ec36f0");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3aa053cf");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 36);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch36(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3aa053cf"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_189648be");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6a45ad00");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 37);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch37(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6a45ad00"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_459dafe4");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d70dba34");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4103a0b8");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 38);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch38(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_d70dba34"))
        {
            doAnimationAction(player, "nod_head_once");
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_13faa821");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1bd3a505");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 39);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_4103a0b8"))
        {
            doAnimationAction(player, "shake_head_no");
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_374e94fe");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_61657d0f");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 42);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch39(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1bd3a505"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_c4bc453f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_25e98647");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 40);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch40(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_25e98647"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                tatooine_rebel_tier3_action_grantQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_d5bf212f");
                utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch42(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_61657d0f"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_74");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 43);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch43(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_76"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_78");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 44);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch44(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_80"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                tatooine_rebel_tier3_action_grantQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_82");
                utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch46(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_53de9d8f"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "tap_head");
                string_id message = new string_id(c_stringFile, "s_81d92cad");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c6594c52");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 47);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch47(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c6594c52"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "accept_affection");
                string_id message = new string_id(c_stringFile, "s_47da17a9");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5c8eeafa");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 48);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch48(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_5c8eeafa"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_7b907498");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 49);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch49(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_92"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_94");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 50);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch50(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_96"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_98");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_100");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_112");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 51);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch51(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_100"))
        {
            doAnimationAction(player, "nod_head_once");
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_102");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_104");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 52);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_112"))
        {
            doAnimationAction(player, "shake_head_no");
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_114");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_116");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 55);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch52(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_104"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_106");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_108");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 53);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch53(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_108"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                tatooine_rebel_tier3_action_grantQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_110");
                utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch55(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_116"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_118");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_120");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 56);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch56(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_120"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_122");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_124");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 57);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch57(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_124"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                tatooine_rebel_tier3_action_grantQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_126");
                utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch59(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_130"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_b136da95");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4bbc7af6");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 60);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_4c695dbd"))
        {
            doAnimationAction(player, "shake_head_no");
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_d2f7d204");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2787404a");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 72);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_11a59fbe"))
        {
            doAnimationAction(player, "pound_fist_palm");
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                string_id message = new string_id(c_stringFile, "s_4d7b2f02");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_221");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 86);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch60(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_4bbc7af6"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                string_id message = new string_id(c_stringFile, "s_58cd53b6");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3899c4e1");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 61);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch61(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3899c4e1"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_c9ba6bc2");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 62);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch62(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_137"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_139");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_141");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 63);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch63(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_141"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_143");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_145");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_157");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 64);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch64(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_145"))
        {
            doAnimationAction(player, "nod_head_once");
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_147");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_149");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 65);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_157"))
        {
            doAnimationAction(player, "shake_head_no");
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_159");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 68);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch65(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_149"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_multiple");
                string_id message = new string_id(c_stringFile, "s_151");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_153");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 66);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch66(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_153"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                tatooine_rebel_tier3_action_grantQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_155");
                utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch68(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_161"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_163");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 69);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch69(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_165"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_167");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_169");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 70);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch70(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_169"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                tatooine_rebel_tier3_action_grantQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_171");
                utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch72(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_2787404a"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_8fbd3f85");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3059f369");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 73);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch73(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3059f369"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wave_finger_warning");
                string_id message = new string_id(c_stringFile, "s_b3f5831e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_82f0aea5");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 74);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch74(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_82f0aea5"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_shoulders");
                string_id message = new string_id(c_stringFile, "s_12e4e81a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5bd69df6");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 75);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch75(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_5bd69df6"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_86fe6fa2");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_183");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 76);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch76(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_183"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_185");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_187");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 77);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch77(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_187"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_189");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_191");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_203");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 78);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch78(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_191"))
        {
            doAnimationAction(player, "nod_head_once");
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_193");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_195");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 79);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_203"))
        {
            doAnimationAction(player, "shake_head_no");
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_205");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_207");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 82);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch79(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_195"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_197");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_199");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 80);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch80(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_199"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                tatooine_rebel_tier3_action_grantQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_201");
                utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch82(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_207"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_209");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 83);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch83(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_211"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_213");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_215");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 84);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch84(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_215"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                tatooine_rebel_tier3_action_grantQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_217");
                utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch86(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_221"))
        {
            doAnimationAction(player, "shake_head_no");
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_shoulders");
                string_id message = new string_id(c_stringFile, "s_137f3e6f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_59cd5207");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 87);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch87(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_59cd5207"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "laugh");
                string_id message = new string_id(c_stringFile, "s_ca2ba04f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b0da26cf");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 88);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch88(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_b0da26cf"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_ab085367");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_228");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 89);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch89(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_228"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_230");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_232");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 90);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch90(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_232"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_234");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 91);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch91(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_236"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_238");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_240");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_252");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 92);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch92(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_240"))
        {
            doAnimationAction(player, "nod_head_once");
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_242");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 93);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_252"))
        {
            doAnimationAction(player, "shake_head_no");
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_254");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_256");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 96);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch93(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_244"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_246");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 94);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch94(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_248"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                tatooine_rebel_tier3_action_grantQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_250");
                utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch96(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_256"))
        {
            doAnimationAction(player, "nod_head_once");
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_258");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_260");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 97);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch97(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_260"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_262");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_264");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 98);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch98(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_264"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                tatooine_rebel_tier3_action_grantQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_266");
                utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch100(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_d2d48b62"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_shoulders");
                string_id message = new string_id(c_stringFile, "s_1fd170b2");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_90875aed");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 101);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch101(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_90875aed"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_ec44bbf0");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d6963a70");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 102);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch102(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_d6963a70"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_ff8b2372");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3269c6d");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8cfb34cb");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 103);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch103(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3269c6d"))
        {
            doAnimationAction(player, "shake_head_no");
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_8a88463d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e6631bf8");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 104);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_8cfb34cb"))
        {
            doAnimationAction(player, "shrug_shoulders");
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                string_id message = new string_id(c_stringFile, "s_a41a2281");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2700bc9b");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 115);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch104(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_e6631bf8"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_341568b3");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_280");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 105);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch105(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_280"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_282");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_284");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 106);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch106(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_284"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_286");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_288");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_300");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 107);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch107(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_288"))
        {
            doAnimationAction(player, "nod_head_once");
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_290");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_292");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 108);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_300"))
        {
            doAnimationAction(player, "shake_head_no");
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_302");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_304");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 111);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch108(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_292"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_294");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_296");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 109);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch109(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_296"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                tatooine_rebel_tier3_action_grantQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_298");
                utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch111(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_304"))
        {
            doAnimationAction(player, "nod_head_once");
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_306");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_308");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 112);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch112(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_308"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_310");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_312");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 113);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch113(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_312"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                tatooine_rebel_tier3_action_grantQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_314");
                utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch115(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_2700bc9b"))
        {
            doAnimationAction(player, "whisper");
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_55eef619");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_89dfa4de");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 116);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch116(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_89dfa4de"))
        {
            doAnimationAction(player, "flex_biceps");
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_321");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_323");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 117);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch117(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_323"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_325");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_327");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 118);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch118(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_327"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_329");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_331");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_343");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 119);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch119(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_331"))
        {
            doAnimationAction(player, "nod_head_once");
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_333");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_335");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 120);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_343"))
        {
            doAnimationAction(player, "shake_head_no");
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_345");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_347");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 123);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch120(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_335"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_337");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_339");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 121);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch121(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_339"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                tatooine_rebel_tier3_action_grantQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_341");
                utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch123(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_347"))
        {
            doAnimationAction(player, "nod_head_once");
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_349");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_351");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 124);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch124(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_351"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_353");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_355");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 125);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch125(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_355"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                tatooine_rebel_tier3_action_grantQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_357");
                utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch127(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c4dbdf08"))
        {
            doAnimationAction(player, "smack_self");
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_shoulders");
                string_id message = new string_id(c_stringFile, "s_d057d7d3");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bf9961a9");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a9bcadc0");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 128);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_de2df0e3"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_8b898446");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_821fd0ab");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_895be92b");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 153);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch128(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_bf9961a9"))
        {
            doAnimationAction(player, "shake_head_disgust");
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1a40c7ec");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7a9322d");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 129);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_a9bcadc0"))
        {
            doAnimationAction(player, "point_forward");
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "laugh");
                string_id message = new string_id(c_stringFile, "s_aeb6556c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_43647dca");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d3e5c0aa");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 139);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch129(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_7a9322d"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                string_id message = new string_id(c_stringFile, "s_3d2253c6");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_366");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 130);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch130(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_366"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_b7d2e561");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fd8741c0");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 131);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch131(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_fd8741c0"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wave_finger_warning");
                string_id message = new string_id(c_stringFile, "s_fffe6e23");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_15e6c3e4");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 132);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch132(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_15e6c3e4"))
        {
            doAnimationAction(player, "shrug_shoulders");
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_69f60e66");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_373");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 133);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch133(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_373"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_315ecc88");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_26c0af06");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 134);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch134(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_26c0af06"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "tap_head");
                string_id message = new string_id(c_stringFile, "s_c2efe3eb");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b84b366c");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 135);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch135(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_b84b366c"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_forward");
                string_id message = new string_id(c_stringFile, "s_a8e0243a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_658f174d");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 136);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch136(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_658f174d"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_hands");
                string_id message = new string_id(c_stringFile, "s_843eff43");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ca8f5dda");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 137);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch137(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_ca8f5dda"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "check_wrist_device");
                tatooine_rebel_tier3_action_grantQuestTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_b1479cf9");
                utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch139(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_43647dca"))
        {
            doAnimationAction(player, "rub_chin_thoughtful");
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_41a00423");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4ffa3018");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 140);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_d3e5c0aa"))
        {
            doAnimationAction(player, "tap_foot");
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_887fc0e9");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c4f6eef8");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 147);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch140(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_4ffa3018"))
        {
            doAnimationAction(player, "nod_head_multiple");
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_e19c193f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2f6ac50d");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 141);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch141(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_2f6ac50d"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                string_id message = new string_id(c_stringFile, "s_b3ae7d2");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_52d4385d");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 142);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch142(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_52d4385d"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_cd86689a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a30c8590");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 143);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch143(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_a30c8590"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_c65b834a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_396");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 144);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch144(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_396"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_hands");
                string_id message = new string_id(c_stringFile, "s_398");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_400");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 145);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch145(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_400"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "check_wrist_device");
                tatooine_rebel_tier3_action_grantQuestTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_402");
                utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch147(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c4f6eef8"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_db14e2c1");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_408");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 148);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch148(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_408"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_410");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_412");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 149);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch149(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_412"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "tap_head");
                string_id message = new string_id(c_stringFile, "s_414");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_416");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 150);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch150(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_416"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_hands");
                string_id message = new string_id(c_stringFile, "s_418");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_420");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 151);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch151(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_420"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "check_wrist_device");
                tatooine_rebel_tier3_action_grantQuestTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_422");
                utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch153(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_821fd0ab"))
        {
            doAnimationAction(player, "explain");
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_2cc79657");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_527fe7c");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 154);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_895be92b"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3949fe14");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_37402596");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e68c0bb8");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_129bdf42");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 163);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch154(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_527fe7c"))
        {
            doAnimationAction(player, "pound_fist_chest");
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_4df738bb");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_151b3d1");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 155);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch155(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_151b3d1"))
        {
            doAnimationAction(player, "nod_head_once");
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                string_id message = new string_id(c_stringFile, "s_6b4f10db");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5f46c092");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 156);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch156(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_5f46c092"))
        {
            doAnimationAction(player, "shrug_shoulders");
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_d762182e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3dc14ee3");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 157);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch157(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3dc14ee3"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_8a4015bb");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_436");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 158);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch158(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_436"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_438");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_440");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 159);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch159(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_440"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_forward");
                string_id message = new string_id(c_stringFile, "s_442");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_444");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 160);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch160(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_444"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_446");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_448");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 161);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch161(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_448"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "check_wrist_device");
                tatooine_rebel_tier3_action_grantQuestTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_450");
                utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch163(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_37402596"))
        {
            doAnimationAction(player, "pound_fist_palm");
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_9c7613eb");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_51a30f3e");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 164);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_e68c0bb8"))
        {
            doAnimationAction(player, "threaten");
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                string_id message = new string_id(c_stringFile, "s_825313cf");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_be155900");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c2051287");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 171);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_129bdf42"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                string_id message = new string_id(c_stringFile, "s_f9f2fe5c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ec3a4b5b");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_18b63392");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 185);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch164(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_51a30f3e"))
        {
            doAnimationAction(player, "stamp_feet");
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_457");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_459");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 165);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch165(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_459"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_461");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_463");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 166);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch166(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_463"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_465");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_467");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 167);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch167(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_467"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_469");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_471");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 168);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch168(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_471"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_hands");
                string_id message = new string_id(c_stringFile, "s_473");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_475");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 169);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch169(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_475"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "check_wrist_device");
                tatooine_rebel_tier3_action_grantQuestTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_477");
                utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch171(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_be155900"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "tap_head");
                string_id message = new string_id(c_stringFile, "s_d258e826");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a9cddbcc");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 172);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_c2051287"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_shoulders");
                string_id message = new string_id(c_stringFile, "s_35b12d6");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_507");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 179);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch172(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_a9cddbcc"))
        {
            doAnimationAction(player, "pose_proudly");
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_dfcc11ba");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_485");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 173);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch173(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_485"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_487");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_489");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 174);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch174(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_489"))
        {
            doAnimationAction(player, "nod_head_once");
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_491");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_493");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 175);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch175(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_493"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_495");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_497");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 176);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch176(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_497"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_hands");
                string_id message = new string_id(c_stringFile, "s_499");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_501");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 177);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch177(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_501"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "check_wrist_device");
                tatooine_rebel_tier3_action_grantQuestTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_503");
                utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch179(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_507"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_509");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_511");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 180);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch180(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_511"))
        {
            doAnimationAction(player, "nod_head_once");
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_513");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_515");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 181);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch181(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_515"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_517");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_519");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 182);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch182(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_519"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_hands");
                string_id message = new string_id(c_stringFile, "s_e3d61563");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_522");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 183);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch183(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_522"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "check_wrist_device");
                tatooine_rebel_tier3_action_grantQuestTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_524");
                utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch185(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_ec3a4b5b"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_188d0288");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_530");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 186);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_18b63392"))
        {
            doAnimationAction(player, "threaten");
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_d9a23c21");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_552");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 192);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch186(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_530"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_532");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_534");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 187);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch187(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_534"))
        {
            doAnimationAction(player, "nod_head_once");
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_536");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_538");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 188);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch188(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_538"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "tap_head");
                string_id message = new string_id(c_stringFile, "s_540");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_542");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 189);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch189(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_542"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_hands");
                string_id message = new string_id(c_stringFile, "s_544");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_546");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 190);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch190(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_546"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "check_wrist_device");
                tatooine_rebel_tier3_action_grantQuestTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_548");
                utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch192(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_552"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_554");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_556");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 193);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch193(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_556"))
        {
            doAnimationAction(player, "nod_head_once");
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_558");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_560");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 194);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch194(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_560"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_562");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_564");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 195);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch195(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_564"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_hands");
                string_id message = new string_id(c_stringFile, "s_566");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_568");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 196);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch196(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_568"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "check_wrist_device");
                tatooine_rebel_tier3_action_grantQuestTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_570");
                utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch198(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_ed4e5e50"))
        {
            doAnimationAction(player, "flex_biceps");
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_f9e6ef1b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_841d27db");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 199);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_ab1cce5f"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "pound_fist_palm");
                string_id message = new string_id(c_stringFile, "s_83b7fee0");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2f463615");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_613");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 203);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch199(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_841d27db"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_db5f856c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a0e63032");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 200);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch200(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_a0e63032"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "tap_head");
                string_id message = new string_id(c_stringFile, "s_58999327");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_412e0683");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 201);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch201(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_412e0683"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute1");
                doAnimationAction(player, "salute1");
                tatooine_rebel_tier3_action_grantQuestThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_94a2001c");
                utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch203(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_2f463615"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                string_id message = new string_id(c_stringFile, "s_d31b92f9");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_710aa637");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8b436d82");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 204);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_613"))
        {
            doAnimationAction(player, "shake_head_no");
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_27bc6d70");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a8af09fc");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 218);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch204(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_710aa637"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_fab55d44");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c265503e");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 205);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_8b436d82"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "laugh");
                string_id message = new string_id(c_stringFile, "s_c7aa6bdb");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ff50cabc");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 210);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch205(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c265503e"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_9f86f530");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f6cd86be");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 206);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch206(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_f6cd86be"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_b2aeeeda");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_42a9f4a3");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 207);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch207(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_42a9f4a3"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_no");
                string_id message = new string_id(c_stringFile, "s_2914d34b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_593");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 208);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch208(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_593"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_shoulders");
                tatooine_rebel_tier3_action_grantQuestThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_573af66b");
                utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch210(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_ff50cabc"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_b4a1239c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c2850e6");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 211);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch211(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c2850e6"))
        {
            doAnimationAction(player, "shrug_shoulders");
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wave_finger_warning");
                string_id message = new string_id(c_stringFile, "s_966a1e03");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fa38c858");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 212);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch212(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_fa38c858"))
        {
            doAnimationAction(player, "point_to_self");
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_shoulders");
                string_id message = new string_id(c_stringFile, "s_b6da869b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e5235eff");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 213);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch213(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_e5235eff"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_eb3726f6");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_52819991");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 214);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch214(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_52819991"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_24d21bf3");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_129732ea");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 215);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch215(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_129732ea"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3414728a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_610");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 216);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch216(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_610"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                tatooine_rebel_tier3_action_grantQuestThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_89962192");
                utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch218(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_a8af09fc"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_be88a7d6");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_114c29a9");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 219);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch219(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_114c29a9"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_e3c318b4");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b7138aed");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 220);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch220(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_b7138aed"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_e24417b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2bc4bdb7");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 221);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch221(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_2bc4bdb7"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "tap_head");
                string_id message = new string_id(c_stringFile, "s_c599f19a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9e4da02c");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 222);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch222(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_9e4da02c"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                tatooine_rebel_tier3_action_grantQuestThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_404b8a10");
                utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch224(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_a91e3c6b"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_4ffe2e0d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_628181f3");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 225);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_639"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_8bf6086e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4ece8e4c");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 230);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch225(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_628181f3"))
        {
            doAnimationAction(player, "pound_fist_chest");
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_5e8b8f88");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_631");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 226);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch226(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_631"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_23c7ed04");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_76340c2f");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 227);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch227(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_76340c2f"))
        {
            doAnimationAction(player, "point_to_self");
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                string_id message = new string_id(c_stringFile, "s_dc95008b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_636");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 228);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch228(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_636"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                tatooine_rebel_tier3_action_grantQuestFour(player, npc);
                string_id message = new string_id(c_stringFile, "s_7b3fb40b");
                utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch230(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_4ece8e4c"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_ae11747c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_90377ed4");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 231);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch231(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_90377ed4"))
        {
            doAnimationAction(player, "pound_fist_chest");
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_d35a8803");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4291c3cf");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 232);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch232(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_4291c3cf"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_forward");
                string_id message = new string_id(c_stringFile, "s_92bcea");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_648");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 233);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch233(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_648"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                tatooine_rebel_tier3_action_grantQuestFour(player, npc);
                string_id message = new string_id(c_stringFile, "s_2493aca3");
                utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch235(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_a927c891"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_d72cbbfa");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_eba35f17");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 236);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_554b78dd"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_9c91459e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_658");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 238);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch236(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_eba35f17"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute1");
                doAnimationAction(player, "salute1");
                string_id message = new string_id(c_stringFile, "s_db13b2d9");
                utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_tier3_handleBranch238(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_658"))
        {
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute1");
                doAnimationAction(player, "salute1");
                string_id message = new string_id(c_stringFile, "s_660");
                utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
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
            detachScript(self, "conversation.tatooine_rebel_tier3");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_SPACE_INTERESTING);
        factions.setFaction(self, factions.FACTION_REBEL);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_SPACE_INTERESTING);
        factions.setFaction(self, factions.FACTION_REBEL);
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
        detachScript(self, "conversation.tatooine_rebel_tier3");
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
        if (!tatooine_rebel_tier3_condition_isPilot(player, npc))
        {
            doAnimationAction(npc, "wave_on_dismissing");
            string_id message = new string_id(c_stringFile, "s_d9245236");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (!tatooine_rebel_tier3_condition_isCorrectFaction(player, npc))
        {
            doAnimationAction(npc, "wave_on_dismissing");
            string_id message = new string_id(c_stringFile, "s_39d07e54");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (!tatooine_rebel_tier3_condition_onMyTrack(player, npc))
        {
            doAnimationAction(npc, "shake_head_no");
            string_id message = new string_id(c_stringFile, "s_30a2d12d");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (tatooine_rebel_tier3_condition_hasNegativeFaction(player, npc))
        {
            doAnimationAction(npc, "pound_fist_palm");
            string_id message = new string_id(c_stringFile, "s_c3e376a5");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_ecb17e60");
                }
                utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 4);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "tatooine_rebel_tier3", null, pp, responses);
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
        if (!tatooine_rebel_tier3_condition_isReadyForTier3(player, npc))
        {
            doAnimationAction(npc, "wave_finger_warning");
            string_id message = new string_id(c_stringFile, "s_614f7f1c");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (tatooine_rebel_tier3_condition_isOnQuest(player, npc))
        {
            doAnimationAction(npc, "wave_on_dismissing");
            string_id message = new string_id(c_stringFile, "s_329414b2");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (tatooine_rebel_tier3_condition_failedQuestOne(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_5a66cd79");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_a68dfaa6");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_9401fd7b");
                }
                utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 9);
                npcStartConversation(player, npc, "tatooine_rebel_tier3", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_rebel_tier3_condition_failedQuestTwo(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_67c8f113");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_d85b3a9c");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_87d7e908");
                }
                utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 12);
                npcStartConversation(player, npc, "tatooine_rebel_tier3", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_rebel_tier3_condition_failedQuestThree(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_c74a6348");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_d454739a");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_9173548c");
                }
                utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 15);
                npcStartConversation(player, npc, "tatooine_rebel_tier3", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_rebel_tier3_condition_failedQuestFour(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_4748286f");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_b40f340");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_9fb0046e");
                }
                utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 18);
                npcStartConversation(player, npc, "tatooine_rebel_tier3", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_rebel_tier3_condition_collectingQuestOneReward(player, npc))
        {
            doAnimationAction(npc, "nod_head_once");
            tatooine_rebel_tier3_action_rewardForQuestOne(player, npc);
            string_id message = new string_id(c_stringFile, "s_8bd6bf55");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_661");
                }
                utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 21);
                npcStartConversation(player, npc, "tatooine_rebel_tier3", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_rebel_tier3_condition_collectingQuestTwoReward(player, npc))
        {
            tatooine_rebel_tier3_action_rewardForQuestTwo(player, npc);
            string_id message = new string_id(c_stringFile, "s_fa6e2383");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_661");
                }
                utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 21);
                npcStartConversation(player, npc, "tatooine_rebel_tier3", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_rebel_tier3_condition_collectingQuestThreeReward(player, npc))
        {
            tatooine_rebel_tier3_action_rewardForQuestThree(player, npc);
            string_id message = new string_id(c_stringFile, "s_f72b6d44");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_661");
                }
                utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 21);
                npcStartConversation(player, npc, "tatooine_rebel_tier3", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_rebel_tier3_condition_collectingQuestFourReward(player, npc))
        {
            doAnimationAction(npc, "tap_head");
            tatooine_rebel_tier3_action_rewardForQuestFour(player, npc);
            string_id message = new string_id(c_stringFile, "s_f9044504");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_661");
                }
                utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 21);
                npcStartConversation(player, npc, "tatooine_rebel_tier3", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (!tatooine_rebel_tier3_condition_hasCompletedQuestOne(player, npc))
        {
            doAnimationAction(npc, "greet");
            string_id message = new string_id(c_stringFile, "s_6f921f12");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_783af10a");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_5b000af8");
                }
                utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 29);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "tatooine_rebel_tier3", null, pp, responses);
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
        if (!tatooine_rebel_tier3_condition_hasCompletedQuestTwo(player, npc))
        {
            doAnimationAction(npc, "greet");
            string_id message = new string_id(c_stringFile, "s_1061c440");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_c4dbdf08");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_de2df0e3");
                }
                utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 127);
                npcStartConversation(player, npc, "tatooine_rebel_tier3", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (!tatooine_rebel_tier3_condition_hasCompletedQuestThree(player, npc))
        {
            doAnimationAction(npc, "nod_head_once");
            string_id message = new string_id(c_stringFile, "s_5852ee6b");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_ed4e5e50");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_ab1cce5f");
                }
                utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 198);
                npcStartConversation(player, npc, "tatooine_rebel_tier3", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (!tatooine_rebel_tier3_condition_hasCompletedQuestFour(player, npc))
        {
            doAnimationAction(npc, "greet");
            string_id message = new string_id(c_stringFile, "s_5bd53e0a");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_a91e3c6b");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_639");
                }
                utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 224);
                npcStartConversation(player, npc, "tatooine_rebel_tier3", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_fb871ded");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (tatooine_rebel_tier3_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_a927c891");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_554b78dd");
                }
                utils.setScriptVar(player, "conversation.tatooine_rebel_tier3.branchId", 235);
                npcStartConversation(player, npc, "tatooine_rebel_tier3", message, responses);
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
        if (!conversationId.equals("tatooine_rebel_tier3"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
        if (branchId == 4 && tatooine_rebel_tier3_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && tatooine_rebel_tier3_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && tatooine_rebel_tier3_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && tatooine_rebel_tier3_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && tatooine_rebel_tier3_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && tatooine_rebel_tier3_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && tatooine_rebel_tier3_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && tatooine_rebel_tier3_handleBranch26(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 27 && tatooine_rebel_tier3_handleBranch27(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 28 && tatooine_rebel_tier3_handleBranch28(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && tatooine_rebel_tier3_handleBranch29(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 30 && tatooine_rebel_tier3_handleBranch30(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 31 && tatooine_rebel_tier3_handleBranch31(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 32 && tatooine_rebel_tier3_handleBranch32(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 33 && tatooine_rebel_tier3_handleBranch33(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 34 && tatooine_rebel_tier3_handleBranch34(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 35 && tatooine_rebel_tier3_handleBranch35(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 36 && tatooine_rebel_tier3_handleBranch36(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 37 && tatooine_rebel_tier3_handleBranch37(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 38 && tatooine_rebel_tier3_handleBranch38(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 39 && tatooine_rebel_tier3_handleBranch39(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 40 && tatooine_rebel_tier3_handleBranch40(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 42 && tatooine_rebel_tier3_handleBranch42(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 43 && tatooine_rebel_tier3_handleBranch43(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 44 && tatooine_rebel_tier3_handleBranch44(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 46 && tatooine_rebel_tier3_handleBranch46(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 47 && tatooine_rebel_tier3_handleBranch47(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 48 && tatooine_rebel_tier3_handleBranch48(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 49 && tatooine_rebel_tier3_handleBranch49(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 50 && tatooine_rebel_tier3_handleBranch50(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 51 && tatooine_rebel_tier3_handleBranch51(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 52 && tatooine_rebel_tier3_handleBranch52(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 53 && tatooine_rebel_tier3_handleBranch53(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 55 && tatooine_rebel_tier3_handleBranch55(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 56 && tatooine_rebel_tier3_handleBranch56(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 57 && tatooine_rebel_tier3_handleBranch57(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 59 && tatooine_rebel_tier3_handleBranch59(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 60 && tatooine_rebel_tier3_handleBranch60(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 61 && tatooine_rebel_tier3_handleBranch61(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 62 && tatooine_rebel_tier3_handleBranch62(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 63 && tatooine_rebel_tier3_handleBranch63(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 64 && tatooine_rebel_tier3_handleBranch64(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 65 && tatooine_rebel_tier3_handleBranch65(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 66 && tatooine_rebel_tier3_handleBranch66(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 68 && tatooine_rebel_tier3_handleBranch68(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 69 && tatooine_rebel_tier3_handleBranch69(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 70 && tatooine_rebel_tier3_handleBranch70(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 72 && tatooine_rebel_tier3_handleBranch72(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 73 && tatooine_rebel_tier3_handleBranch73(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 74 && tatooine_rebel_tier3_handleBranch74(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 75 && tatooine_rebel_tier3_handleBranch75(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 76 && tatooine_rebel_tier3_handleBranch76(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 77 && tatooine_rebel_tier3_handleBranch77(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 78 && tatooine_rebel_tier3_handleBranch78(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 79 && tatooine_rebel_tier3_handleBranch79(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 80 && tatooine_rebel_tier3_handleBranch80(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 82 && tatooine_rebel_tier3_handleBranch82(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 83 && tatooine_rebel_tier3_handleBranch83(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 84 && tatooine_rebel_tier3_handleBranch84(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 86 && tatooine_rebel_tier3_handleBranch86(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 87 && tatooine_rebel_tier3_handleBranch87(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 88 && tatooine_rebel_tier3_handleBranch88(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 89 && tatooine_rebel_tier3_handleBranch89(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 90 && tatooine_rebel_tier3_handleBranch90(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 91 && tatooine_rebel_tier3_handleBranch91(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 92 && tatooine_rebel_tier3_handleBranch92(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 93 && tatooine_rebel_tier3_handleBranch93(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 94 && tatooine_rebel_tier3_handleBranch94(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 96 && tatooine_rebel_tier3_handleBranch96(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 97 && tatooine_rebel_tier3_handleBranch97(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 98 && tatooine_rebel_tier3_handleBranch98(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 100 && tatooine_rebel_tier3_handleBranch100(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 101 && tatooine_rebel_tier3_handleBranch101(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 102 && tatooine_rebel_tier3_handleBranch102(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 103 && tatooine_rebel_tier3_handleBranch103(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 104 && tatooine_rebel_tier3_handleBranch104(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 105 && tatooine_rebel_tier3_handleBranch105(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 106 && tatooine_rebel_tier3_handleBranch106(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 107 && tatooine_rebel_tier3_handleBranch107(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 108 && tatooine_rebel_tier3_handleBranch108(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 109 && tatooine_rebel_tier3_handleBranch109(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 111 && tatooine_rebel_tier3_handleBranch111(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 112 && tatooine_rebel_tier3_handleBranch112(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 113 && tatooine_rebel_tier3_handleBranch113(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 115 && tatooine_rebel_tier3_handleBranch115(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 116 && tatooine_rebel_tier3_handleBranch116(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 117 && tatooine_rebel_tier3_handleBranch117(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 118 && tatooine_rebel_tier3_handleBranch118(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 119 && tatooine_rebel_tier3_handleBranch119(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 120 && tatooine_rebel_tier3_handleBranch120(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 121 && tatooine_rebel_tier3_handleBranch121(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 123 && tatooine_rebel_tier3_handleBranch123(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 124 && tatooine_rebel_tier3_handleBranch124(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 125 && tatooine_rebel_tier3_handleBranch125(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 127 && tatooine_rebel_tier3_handleBranch127(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 128 && tatooine_rebel_tier3_handleBranch128(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 129 && tatooine_rebel_tier3_handleBranch129(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 130 && tatooine_rebel_tier3_handleBranch130(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 131 && tatooine_rebel_tier3_handleBranch131(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 132 && tatooine_rebel_tier3_handleBranch132(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 133 && tatooine_rebel_tier3_handleBranch133(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 134 && tatooine_rebel_tier3_handleBranch134(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 135 && tatooine_rebel_tier3_handleBranch135(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 136 && tatooine_rebel_tier3_handleBranch136(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 137 && tatooine_rebel_tier3_handleBranch137(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 139 && tatooine_rebel_tier3_handleBranch139(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 140 && tatooine_rebel_tier3_handleBranch140(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 141 && tatooine_rebel_tier3_handleBranch141(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 142 && tatooine_rebel_tier3_handleBranch142(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 143 && tatooine_rebel_tier3_handleBranch143(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 144 && tatooine_rebel_tier3_handleBranch144(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 145 && tatooine_rebel_tier3_handleBranch145(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 147 && tatooine_rebel_tier3_handleBranch147(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 148 && tatooine_rebel_tier3_handleBranch148(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 149 && tatooine_rebel_tier3_handleBranch149(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 150 && tatooine_rebel_tier3_handleBranch150(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 151 && tatooine_rebel_tier3_handleBranch151(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 153 && tatooine_rebel_tier3_handleBranch153(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 154 && tatooine_rebel_tier3_handleBranch154(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 155 && tatooine_rebel_tier3_handleBranch155(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 156 && tatooine_rebel_tier3_handleBranch156(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 157 && tatooine_rebel_tier3_handleBranch157(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 158 && tatooine_rebel_tier3_handleBranch158(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 159 && tatooine_rebel_tier3_handleBranch159(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 160 && tatooine_rebel_tier3_handleBranch160(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 161 && tatooine_rebel_tier3_handleBranch161(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 163 && tatooine_rebel_tier3_handleBranch163(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 164 && tatooine_rebel_tier3_handleBranch164(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 165 && tatooine_rebel_tier3_handleBranch165(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 166 && tatooine_rebel_tier3_handleBranch166(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 167 && tatooine_rebel_tier3_handleBranch167(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 168 && tatooine_rebel_tier3_handleBranch168(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 169 && tatooine_rebel_tier3_handleBranch169(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 171 && tatooine_rebel_tier3_handleBranch171(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 172 && tatooine_rebel_tier3_handleBranch172(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 173 && tatooine_rebel_tier3_handleBranch173(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 174 && tatooine_rebel_tier3_handleBranch174(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 175 && tatooine_rebel_tier3_handleBranch175(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 176 && tatooine_rebel_tier3_handleBranch176(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 177 && tatooine_rebel_tier3_handleBranch177(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 179 && tatooine_rebel_tier3_handleBranch179(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 180 && tatooine_rebel_tier3_handleBranch180(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 181 && tatooine_rebel_tier3_handleBranch181(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 182 && tatooine_rebel_tier3_handleBranch182(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 183 && tatooine_rebel_tier3_handleBranch183(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 185 && tatooine_rebel_tier3_handleBranch185(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 186 && tatooine_rebel_tier3_handleBranch186(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 187 && tatooine_rebel_tier3_handleBranch187(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 188 && tatooine_rebel_tier3_handleBranch188(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 189 && tatooine_rebel_tier3_handleBranch189(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 190 && tatooine_rebel_tier3_handleBranch190(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 192 && tatooine_rebel_tier3_handleBranch192(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 193 && tatooine_rebel_tier3_handleBranch193(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 194 && tatooine_rebel_tier3_handleBranch194(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 195 && tatooine_rebel_tier3_handleBranch195(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 196 && tatooine_rebel_tier3_handleBranch196(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 198 && tatooine_rebel_tier3_handleBranch198(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 199 && tatooine_rebel_tier3_handleBranch199(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 200 && tatooine_rebel_tier3_handleBranch200(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 201 && tatooine_rebel_tier3_handleBranch201(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 203 && tatooine_rebel_tier3_handleBranch203(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 204 && tatooine_rebel_tier3_handleBranch204(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 205 && tatooine_rebel_tier3_handleBranch205(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 206 && tatooine_rebel_tier3_handleBranch206(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 207 && tatooine_rebel_tier3_handleBranch207(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 208 && tatooine_rebel_tier3_handleBranch208(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 210 && tatooine_rebel_tier3_handleBranch210(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 211 && tatooine_rebel_tier3_handleBranch211(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 212 && tatooine_rebel_tier3_handleBranch212(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 213 && tatooine_rebel_tier3_handleBranch213(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 214 && tatooine_rebel_tier3_handleBranch214(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 215 && tatooine_rebel_tier3_handleBranch215(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 216 && tatooine_rebel_tier3_handleBranch216(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 218 && tatooine_rebel_tier3_handleBranch218(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 219 && tatooine_rebel_tier3_handleBranch219(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 220 && tatooine_rebel_tier3_handleBranch220(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 221 && tatooine_rebel_tier3_handleBranch221(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 222 && tatooine_rebel_tier3_handleBranch222(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 224 && tatooine_rebel_tier3_handleBranch224(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 225 && tatooine_rebel_tier3_handleBranch225(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 226 && tatooine_rebel_tier3_handleBranch226(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 227 && tatooine_rebel_tier3_handleBranch227(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 228 && tatooine_rebel_tier3_handleBranch228(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 230 && tatooine_rebel_tier3_handleBranch230(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 231 && tatooine_rebel_tier3_handleBranch231(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 232 && tatooine_rebel_tier3_handleBranch232(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 233 && tatooine_rebel_tier3_handleBranch233(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 235 && tatooine_rebel_tier3_handleBranch235(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 236 && tatooine_rebel_tier3_handleBranch236(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 238 && tatooine_rebel_tier3_handleBranch238(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.tatooine_rebel_tier3.branchId");
        return SCRIPT_CONTINUE;
    }
}
