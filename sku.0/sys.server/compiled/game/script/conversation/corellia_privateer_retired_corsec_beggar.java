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
import script.library.features;
import script.library.skill;
import script.library.space_quest;
import script.library.space_skill;
import script.library.utils;

public class corellia_privateer_retired_corsec_beggar extends script.base_script
{
    public corellia_privateer_retired_corsec_beggar()
    {
    }
    public static String c_stringFile = "conversation/corellia_privateer_retired_corsec_beggar";
    public boolean corellia_privateer_retired_corsec_beggar_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean corellia_privateer_retired_corsec_beggar_condition_remembersPlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        return (utils.hasScriptVar(player, "metWyron"));
    }
    public boolean corellia_privateer_retired_corsec_beggar_condition_hasNoviceSkillBox(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "pilot_rebel_navy_novice"));
    }
    public boolean corellia_privateer_retired_corsec_beggar_condition_hasRebelQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasQuest(player) && corellia_privateer_retired_corsec_beggar_condition_hasNoviceSkillBox(player, npc));
    }
    public boolean corellia_privateer_retired_corsec_beggar_condition_isImperialPilot(obj_id player, obj_id npc) throws InterruptedException
    {
        int playerFactionID = pvpGetAlignedFaction(player);
        if (playerFactionID == (-615855020))
        {
            return true;
        }
        else 
        {
            return (hasSkill(player, "pilot_imperial_navy_novice"));
        }
    }
    public boolean corellia_privateer_retired_corsec_beggar_condition_isRebelPilot(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "pilot_rebel_navy_novice"));
    }
    public boolean corellia_privateer_retired_corsec_beggar_condition_isGenderMale(obj_id player, obj_id npc) throws InterruptedException
    {
        return (getGender(player) == GENDER_MALE);
    }
    public boolean corellia_privateer_retired_corsec_beggar_condition_hasCompletedQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasCompletedQuest(player, "destroy_surpriseattack", "naboo_rebel_1"));
    }
    public boolean corellia_privateer_retired_corsec_beggar_condition_hasCompletedQuestTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasCompletedQuest(player, "destroy", "naboo_rebel_2"));
    }
    public boolean corellia_privateer_retired_corsec_beggar_condition_hasCompletedQuestThree(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasCompletedQuest(player, "escort", "naboo_rebel_3"));
    }
    public boolean corellia_privateer_retired_corsec_beggar_condition_hasCompletedQuestFour(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasCompletedQuest(player, "destroy", "naboo_rebel_4"));
    }
    public boolean corellia_privateer_retired_corsec_beggar_condition_hasFailedQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasFailedQuest(player, "destroy_surpriseattack", "naboo_rebel_1_surprise") || space_quest.hasAbortedQuest(player, "destroy_surpriseattack", "naboo_rebel_1_surprise") || space_quest.hasFailedQuest(player, "patrol", "naboo_rebel_1") || space_quest.hasAbortedQuest(player, "patrol", "naboo_rebel_1"));
    }
    public boolean corellia_privateer_retired_corsec_beggar_condition_isReadyForFirstTraining(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasSkill(player, "pilot_rebel_navy_starships_01") || hasSkill(player, "pilot_rebel_navy_procedures_01") || hasSkill(player, "pilot_rebel_navy_weapons_01") || hasSkill(player, "pilot_rebel_navy_droid_01"))
        {
            return false;
        }
        else 
        {
            return true;
        }
    }
    public boolean corellia_privateer_retired_corsec_beggar_condition_isReadyForMoreTraining(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.isPlayerQualifiedForSkill(player, "pilot_rebel_navy_starships_01"));
    }
    public boolean corellia_privateer_retired_corsec_beggar_condition_hasAllTierOneSkills(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasSkill(player, "pilot_rebel_navy_starships_01"))
        {
            return false;
        }
        else if (!hasSkill(player, "pilot_rebel_navy_procedures_01"))
        {
            return false;
        }
        else if (!hasSkill(player, "pilot_rebel_navy_weapons_01"))
        {
            return false;
        }
        else if (!hasSkill(player, "pilot_rebel_navy_droid_01"))
        {
            return false;
        }
        else 
        {
            return true;
        }
    }
    public boolean corellia_privateer_retired_corsec_beggar_condition_hasFailedQuestTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasFailedQuest(player, "destroy", "naboo_rebel_2") || space_quest.hasAbortedQuest(player, "destroy", "naboo_rebel_2"));
    }
    public boolean corellia_privateer_retired_corsec_beggar_condition_beenRewardedForQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasCompletedQuest(player, "destroy_surpriseattack", "naboo_rebel_1") && space_quest.hasReceivedReward(player, "destroy_surpriseattack", "naboo_rebel_1");
    }
    public boolean corellia_privateer_retired_corsec_beggar_condition_beenRewardedForQuestTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasCompletedQuest(player, "destroy", "naboo_rebel_2") && space_quest.hasReceivedReward(player, "destroy", "naboo_rebel_2");
    }
    public boolean corellia_privateer_retired_corsec_beggar_condition_beenRewardedForQuestThree(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasCompletedQuest(player, "escort", "naboo_rebel_3") && space_quest.hasReceivedReward(player, "escort", "naboo_rebel_3");
    }
    public boolean corellia_privateer_retired_corsec_beggar_condition_beenRewardedForQuestFour(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasCompletedQuest(player, "destroy", "naboo_rebel_4") && space_quest.hasReceivedReward(player, "destroy", "naboo_rebel_4");
    }
    public boolean corellia_privateer_retired_corsec_beggar_condition_hasFailedQuestThree(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasFailedQuest(player, "escort", "naboo_rebel_3") || space_quest.hasAbortedQuest(player, "escort", "naboo_rebel_3") || space_quest.hasFailedQuest(player, "patrol", "naboo_rebel_3") || space_quest.hasAbortedQuest(player, "patrol", "naboo_rebel_3"));
    }
    public boolean corellia_privateer_retired_corsec_beggar_condition_hasFailedQuestFour(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasFailedQuest(player, "destroy", "naboo_rebel_4") || space_quest.hasAbortedQuest(player, "destroy", "naboo_rebel_4"));
    }
    public boolean corellia_privateer_retired_corsec_beggar_condition_isPrivateerPilot(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasSkill(player, "pilot_neutral_novice");
    }
    public boolean corellia_privateer_retired_corsec_beggar_condition_readyForSecondSkill(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasCompletedQuest(player, "destroy", "naboo_rebel_4") && !hasSkill(player, "pilot_rebel_navy_starships_01") && !hasSkill(player, "pilot_rebel_navy_weapons_01") && !hasSkill(player, "pilot_rebel_navy_procedures_01") && !hasSkill(player, "pilot_rebel_navy_droid_01"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean corellia_privateer_retired_corsec_beggar_condition_hasSTARSHIPS1(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "pilot_rebel_navy_starships_01"));
    }
    public boolean corellia_privateer_retired_corsec_beggar_condition_hasWEAPONS1(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "pilot_rebel_navy_weapons_01"));
    }
    public boolean corellia_privateer_retired_corsec_beggar_condition_hasPROCEDURES1(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "pilot_rebel_navy_procedures_01"));
    }
    public boolean corellia_privateer_retired_corsec_beggar_condition_hasDROIDS1(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "pilot_rebel_navy_droid_01"));
    }
    public boolean corellia_privateer_retired_corsec_beggar_condition_hasSpaceExpansion(obj_id player, obj_id npc) throws InterruptedException
    {
        return (features.isSpaceEdition(player));
    }
    public boolean corellia_privateer_retired_corsec_beggar_condition_hasSpaceShip(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasShip(player));
    }
    public boolean corellia_privateer_retired_corsec_beggar_condition_has_GRADUATED_goto_DANTOOINE(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasObjVar(player, "vortexPilot"));
    }
    public boolean corellia_privateer_retired_corsec_beggar_condition_hasVeryFirstQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!corellia_privateer_retired_corsec_beggar_condition_hasSpaceShip(player, npc))
        {
            return false;
        }
        else 
        {
            return (space_quest.hasQuest(player, "patrol", "naboo_rebel_1") || space_quest.hasQuest(player, "destroy_surpriseattack", "naboo_rebel_1"));
        }
    }
    public boolean corellia_privateer_retired_corsec_beggar_condition_imp_isasking(obj_id player, obj_id npc) throws InterruptedException
    {
        return (utils.hasScriptVar(player, "imp_isasking"));
    }
    public boolean corellia_privateer_retired_corsec_beggar_condition_q1_has_discussed(obj_id player, obj_id npc) throws InterruptedException
    {
        return (utils.hasScriptVar(player, "q1_discussed"));
    }
    public void corellia_privateer_retired_corsec_beggar_action_rememberPlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.setScriptVar(player, "metWyron", true);
    }
    public void corellia_privateer_retired_corsec_beggar_action_grantNoviceSkillBox(obj_id player, obj_id npc) throws InterruptedException
    {
        grantSkill(player, "pilot_rebel_navy_novice");
        playMusic(player, "sound/music_themequest_acc_general.snd");
        setObjVar(player, "rebelAllianceMember", true);
    }
    public void corellia_privateer_retired_corsec_beggar_action_grantQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "patrol", "naboo_rebel_1");
    }
    public void corellia_privateer_retired_corsec_beggar_action_grantQuestTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "destroy", "naboo_rebel_2");
    }
    public void corellia_privateer_retired_corsec_beggar_action_grantQuestThree(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "patrol", "naboo_rebel_3");
    }
    public void corellia_privateer_retired_corsec_beggar_action_grantQuestFour(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "destroy", "naboo_rebel_4");
    }
    public void corellia_privateer_retired_corsec_beggar_action_anim_salute(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "salute");
    }
    public void corellia_privateer_retired_corsec_beggar_action_anim_greet(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "greet");
    }
    public void corellia_privateer_retired_corsec_beggar_action_anim_poundFist(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "pound_fist_palm");
    }
    public void corellia_privateer_retired_corsec_beggar_action_anim_explode(obj_id player, obj_id npc) throws InterruptedException
    {
        playClientEffectLoc(player, "clienteffect/lair_damage_light.cef", getLocation(npc), 1.52f);
    }
    public void corellia_privateer_retired_corsec_beggar_action_grantSkill1(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        doAnimationAction(npc, "salute1");
        if (corellia_privateer_retired_corsec_beggar_condition_readyForSecondSkill(player, npc))
        {
            grantSkill(player, "pilot_rebel_navy_starships_01");
        }
        else 
        {
            skill.purchaseSkill(player, "pilot_rebel_navy_starships_01");
        }
    }
    public void corellia_privateer_retired_corsec_beggar_action_grantSkill2(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        doAnimationAction(npc, "salute1");
        if (corellia_privateer_retired_corsec_beggar_condition_readyForSecondSkill(player, npc))
        {
            grantSkill(player, "pilot_rebel_navy_weapons_01");
        }
        else 
        {
            skill.purchaseSkill(player, "pilot_rebel_navy_weapons_01");
        }
    }
    public void corellia_privateer_retired_corsec_beggar_action_grantSkill3(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        doAnimationAction(npc, "salute1");
        if (corellia_privateer_retired_corsec_beggar_condition_readyForSecondSkill(player, npc))
        {
            grantSkill(player, "pilot_rebel_navy_procedures_01");
        }
        else 
        {
            skill.purchaseSkill(player, "pilot_rebel_navy_procedures_01");
        }
    }
    public void corellia_privateer_retired_corsec_beggar_action_grantSkill4(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        doAnimationAction(npc, "salute1");
        if (corellia_privateer_retired_corsec_beggar_condition_readyForSecondSkill(player, npc))
        {
            grantSkill(player, "pilot_rebel_navy_droid_01");
        }
        else 
        {
            skill.purchaseSkill(player, "pilot_rebel_navy_droid_01");
        }
    }
    public void corellia_privateer_retired_corsec_beggar_action_grantDUTY_Destroy1(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "destroy_duty", "naboo_rebel_6");
    }
    public void corellia_privateer_retired_corsec_beggar_action_grantDUTY_Escort1(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "escort_duty", "naboo_rebel_7");
    }
    public void corellia_privateer_retired_corsec_beggar_action_reward_quest_1(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.giveReward(player, "destroy_surpriseattack", "naboo_rebel_1", 500);
    }
    public void corellia_privateer_retired_corsec_beggar_action_reward_quest_2(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.giveReward(player, "destroy", "naboo_rebel_2", 750);
    }
    public void corellia_privateer_retired_corsec_beggar_action_reward_quest_3(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.giveReward(player, "escort", "naboo_rebel_3", 1000);
    }
    public void corellia_privateer_retired_corsec_beggar_action_reward_quest_4(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.giveReward(player, "destroy", "naboo_rebel_4", 1500);
    }
    public void corellia_privateer_retired_corsec_beggar_action_grantSpaceShip(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantNewbieShip(player, "rebel");
    }
    public void corellia_privateer_retired_corsec_beggar_action_set_GRADUATED_goto_DANTOOINE(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "vortexPilot", true);
    }
    public void corellia_privateer_retired_corsec_beggar_action_an_imp_is_asking(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.setScriptVar(player, "imp_isasking", true);
    }
    public void corellia_privateer_retired_corsec_beggar_action_action0001(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.setScriptVar(player, "q1_premission", true);
    }
    public void corellia_privateer_retired_corsec_beggar_action_q1_set_discussed(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.setScriptVar(player, "q1_discussed", true);
    }
    public int corellia_privateer_retired_corsec_beggar_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_49eaf107"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_82f0aea5");
                removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_8be640b8"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_2812a379");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e28320c1");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_19104f66");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_46663cbe");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8252a913");
                    }
                    setObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId", 36);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_privateer_retired_corsec_beggar_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_e28320c1"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "implore");
                string_id message = new string_id(c_stringFile, "s_cb58d836");
                removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_19104f66"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "implore");
                string_id message = new string_id(c_stringFile, "s_67f5202c");
                removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_46663cbe"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3f5509de");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c4d665ba");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ed52be64");
                    }
                    setObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId", 39);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_8252a913"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_7c3dc1ab");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1a2b4bd6");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_455169a0");
                    }
                    setObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId", 56);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_privateer_retired_corsec_beggar_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3daae2ee"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_no");
                string_id message = new string_id(c_stringFile, "s_db730888");
                removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_428087e9"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_baf2d521");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_55b16c62");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f2dc9195");
                    }
                    setObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_privateer_retired_corsec_beggar_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_55b16c62"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_b93aa42e");
                removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_f2dc9195"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_away");
                string_id message = new string_id(c_stringFile, "s_3541bf7");
                removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_privateer_retired_corsec_beggar_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_f95334"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_4f68d18f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bb54f6cc");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ca776e30");
                    }
                    setObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_ca776e30"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1f7981ec");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_757d93fb");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ff607592");
                    }
                    setObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_privateer_retired_corsec_beggar_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_bb54f6cc"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_9f2e17a3");
                removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_ca776e30"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_b25e0684");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_17b4bc37");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2313ac9e");
                    }
                    setObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_privateer_retired_corsec_beggar_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_17b4bc37"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_f2319d89");
                removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_2313ac9e"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_558fa93c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cdc41c9f");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cf3b2658");
                    }
                    setObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_privateer_retired_corsec_beggar_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_cdc41c9f"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_55b16c5b");
                removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_cf3b2658"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_54fb5f5d");
                removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_privateer_retired_corsec_beggar_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_757d93fb"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_63520a89");
                removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_ff607592"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_4b74e148");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_eb4e939");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9df7e4c2");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_daa001f6");
                    }
                    setObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId", 19);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_privateer_retired_corsec_beggar_handleBranch19(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_eb4e939"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_79ec01f7");
                removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_9df7e4c2"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1c7c9ce0");
                removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_daa001f6"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_a761aa1e");
                removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_privateer_retired_corsec_beggar_handleBranch23(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_2992b933"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_4fbbd27f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2683bb8f");
                    }
                    setObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_2a33c404"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_f52972fb");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
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
                    setObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId", 30);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_8be640b8"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_2812a379");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e28320c1");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_19104f66");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_46663cbe");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8252a913");
                    }
                    setObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId", 36);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_privateer_retired_corsec_beggar_handleBranch24(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_2683bb8f"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_11d6eac4");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_958a863c");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f2841236");
                    }
                    setObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_privateer_retired_corsec_beggar_handleBranch25(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_958a863c"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_b9b4cdf3");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3e8d294c");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1eecbc00");
                    }
                    setObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_f2841236"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "gesticulate_wildly");
                string_id message = new string_id(c_stringFile, "s_9756c395");
                removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_privateer_retired_corsec_beggar_handleBranch26(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3e8d294c"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_cbc9bfc1");
                removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1eecbc00"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_264b3207");
                removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_privateer_retired_corsec_beggar_handleBranch30(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_77e48d5b"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_11d6eac4");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_958a863c");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f2841236");
                    }
                    setObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId", 31);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_privateer_retired_corsec_beggar_handleBranch31(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_958a863c"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_b9b4cdf3");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3e8d294c");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1eecbc00");
                    }
                    setObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId", 32);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_f2841236"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "gesticulate_wildly");
                string_id message = new string_id(c_stringFile, "s_9756c395");
                removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_privateer_retired_corsec_beggar_handleBranch32(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3e8d294c"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_cbc9bfc1");
                removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1eecbc00"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_264b3207");
                removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_privateer_retired_corsec_beggar_handleBranch36(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_e28320c1"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "implore");
                string_id message = new string_id(c_stringFile, "s_cb58d836");
                removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_19104f66"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "implore");
                string_id message = new string_id(c_stringFile, "s_67f5202c");
                removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_46663cbe"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3f5509de");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c4d665ba");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ed52be64");
                    }
                    setObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId", 39);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_8252a913"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_7c3dc1ab");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1a2b4bd6");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_455169a0");
                    }
                    setObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId", 56);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_privateer_retired_corsec_beggar_handleBranch39(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c4d665ba"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_4294df70");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_de0c6e64");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_366389b9");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1aea5dd4");
                    }
                    setObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId", 40);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_ed52be64"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_968710ca");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8f4bce76");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c8291538");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ff739c7e");
                    }
                    setObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId", 48);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_privateer_retired_corsec_beggar_handleBranch40(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_de0c6e64"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_576d00c5");
                removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_366389b9"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_591c9c49");
                removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1aea5dd4"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_6a6e024a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_958a863c");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f2841236");
                    }
                    setObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId", 43);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_privateer_retired_corsec_beggar_handleBranch43(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_958a863c"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_b9b4cdf3");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3e8d294c");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1eecbc00");
                    }
                    setObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId", 44);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_f2841236"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "gesticulate_wildly");
                string_id message = new string_id(c_stringFile, "s_9756c395");
                removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_privateer_retired_corsec_beggar_handleBranch44(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3e8d294c"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_cbc9bfc1");
                removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1eecbc00"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_264b3207");
                removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_privateer_retired_corsec_beggar_handleBranch48(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_8f4bce76"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_7eee531");
                removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_c8291538"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_c3c03121");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e3717de2");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_78b67180");
                    }
                    setObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId", 50);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_ff739c7e"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "pound_fist_palm");
                string_id message = new string_id(c_stringFile, "s_c30cfa8b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_34f6c18f");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5034aab7");
                    }
                    setObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId", 53);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_privateer_retired_corsec_beggar_handleBranch50(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_e3717de2"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "pound_fist_chest");
                string_id message = new string_id(c_stringFile, "s_97aef052");
                removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_78b67180"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_e5ddc8b8");
                removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_privateer_retired_corsec_beggar_handleBranch53(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_34f6c18f"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_77258083");
                removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_5034aab7"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_ec3ffcdb");
                removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_privateer_retired_corsec_beggar_handleBranch56(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1a2b4bd6"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_b9b4cdf3");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3e8d294c");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1eecbc00");
                    }
                    setObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId", 57);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_455169a0"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_78ae72a2");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d79019d0");
                    }
                    setObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId", 60);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_privateer_retired_corsec_beggar_handleBranch57(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3e8d294c"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_cbc9bfc1");
                removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1eecbc00"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_264b3207");
                removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_privateer_retired_corsec_beggar_handleBranch60(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_d79019d0"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_ed8c4da8");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c45af70d");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b21356c8");
                    }
                    setObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId", 61);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_privateer_retired_corsec_beggar_handleBranch61(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c45af70d"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "belly_laugh");
                string_id message = new string_id(c_stringFile, "s_4e213b9f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f06b7dcf");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1ca480f5");
                    }
                    setObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId", 62);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_b21356c8"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_6ce3ead6");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e529f302");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f6046c2a");
                    }
                    setObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId", 69);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_privateer_retired_corsec_beggar_handleBranch62(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_f06b7dcf"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_6ce3ead6");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e529f302");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f6046c2a");
                    }
                    setObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId", 63);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1ca480f5"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "gesticulate_wildly");
                string_id message = new string_id(c_stringFile, "s_bab29e60");
                removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_privateer_retired_corsec_beggar_handleBranch63(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_e529f302"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_2a27a72e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_47744716");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5b204877");
                    }
                    setObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId", 64);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_f6046c2a"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_f8411238");
                removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_privateer_retired_corsec_beggar_handleBranch64(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_47744716"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_34556faa");
                removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_5b204877"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_763e6d5");
                removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_privateer_retired_corsec_beggar_handleBranch69(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_e529f302"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_2a27a72e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_47744716");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5b204877");
                    }
                    setObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId", 70);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_f6046c2a"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_f8411238");
                removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_privateer_retired_corsec_beggar_handleBranch70(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_47744716"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_34556faa");
                removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_5b204877"))
        {
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_763e6d5");
                removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
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
            detachScript(self, "conversation.corellia_privateer_retired_corsec_beggar");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_SPACE_INTERESTING);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_SPACE_INTERESTING);
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
        detachScript(self, "conversation.corellia_privateer_retired_corsec_beggar");
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
        if (corellia_privateer_retired_corsec_beggar_condition_isRebelPilot(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1c18efb1");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_49eaf107");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_8be640b8");
                }
                setObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId", 1);
                npcStartConversation(player, npc, "corellia_privateer_retired_corsec_beggar", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_privateer_retired_corsec_beggar_condition_isPrivateerPilot(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1af6e3a2");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_3daae2ee");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_428087e9");
                }
                setObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId", 4);
                npcStartConversation(player, npc, "corellia_privateer_retired_corsec_beggar", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_privateer_retired_corsec_beggar_condition_isImperialPilot(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_892ffb49");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_f95334");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_ca776e30");
                }
                setObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId", 9);
                npcStartConversation(player, npc, "corellia_privateer_retired_corsec_beggar", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "check_wrist_device");
            string_id message = new string_id(c_stringFile, "s_87f655c5");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (corellia_privateer_retired_corsec_beggar_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_2992b933");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_2a33c404");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_8be640b8");
                }
                setObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId", 23);
                npcStartConversation(player, npc, "corellia_privateer_retired_corsec_beggar", message, responses);
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
        if (!conversationId.equals("corellia_privateer_retired_corsec_beggar"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = getIntObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
        if (branchId == 1 && corellia_privateer_retired_corsec_beggar_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && corellia_privateer_retired_corsec_beggar_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && corellia_privateer_retired_corsec_beggar_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && corellia_privateer_retired_corsec_beggar_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && corellia_privateer_retired_corsec_beggar_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && corellia_privateer_retired_corsec_beggar_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && corellia_privateer_retired_corsec_beggar_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && corellia_privateer_retired_corsec_beggar_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && corellia_privateer_retired_corsec_beggar_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && corellia_privateer_retired_corsec_beggar_handleBranch19(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && corellia_privateer_retired_corsec_beggar_handleBranch23(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && corellia_privateer_retired_corsec_beggar_handleBranch24(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && corellia_privateer_retired_corsec_beggar_handleBranch25(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && corellia_privateer_retired_corsec_beggar_handleBranch26(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 30 && corellia_privateer_retired_corsec_beggar_handleBranch30(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 31 && corellia_privateer_retired_corsec_beggar_handleBranch31(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 32 && corellia_privateer_retired_corsec_beggar_handleBranch32(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 36 && corellia_privateer_retired_corsec_beggar_handleBranch36(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 39 && corellia_privateer_retired_corsec_beggar_handleBranch39(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 40 && corellia_privateer_retired_corsec_beggar_handleBranch40(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 43 && corellia_privateer_retired_corsec_beggar_handleBranch43(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 44 && corellia_privateer_retired_corsec_beggar_handleBranch44(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 48 && corellia_privateer_retired_corsec_beggar_handleBranch48(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 50 && corellia_privateer_retired_corsec_beggar_handleBranch50(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 53 && corellia_privateer_retired_corsec_beggar_handleBranch53(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 56 && corellia_privateer_retired_corsec_beggar_handleBranch56(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 57 && corellia_privateer_retired_corsec_beggar_handleBranch57(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 60 && corellia_privateer_retired_corsec_beggar_handleBranch60(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 61 && corellia_privateer_retired_corsec_beggar_handleBranch61(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 62 && corellia_privateer_retired_corsec_beggar_handleBranch62(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 63 && corellia_privateer_retired_corsec_beggar_handleBranch63(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 64 && corellia_privateer_retired_corsec_beggar_handleBranch64(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 69 && corellia_privateer_retired_corsec_beggar_handleBranch69(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 70 && corellia_privateer_retired_corsec_beggar_handleBranch70(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.corellia_privateer_retired_corsec_beggar.branchId");
        return SCRIPT_CONTINUE;
    }
}
