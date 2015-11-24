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
import script.library.skill;
import script.library.space_quest;
import script.library.space_skill;
import script.library.utils;

public class naboo_privateer_trainer_3 extends script.base_script
{
    public naboo_privateer_trainer_3()
    {
    }
    public static String c_stringFile = "conversation/naboo_privateer_trainer_3";
    public boolean naboo_privateer_trainer_3_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean naboo_privateer_trainer_3_condition_remembersPlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        return (utils.hasScriptVar(player, "metRsfTrainer"));
    }
    public boolean naboo_privateer_trainer_3_condition_hasNoviceSkillBox(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasSkill(player, "pilot_neutral_novice"));
    }
    public boolean naboo_privateer_trainer_3_condition_alreadyHasAQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasQuest(player));
    }
    public boolean naboo_privateer_trainer_3_condition_isImperialPilot(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "pilot_imperial_navy_novice"));
    }
    public boolean naboo_privateer_trainer_3_condition_isRebelPilot(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "pilot_rebel_navy_novice"));
    }
    public boolean naboo_privateer_trainer_3_condition_isPilotButNotRsfMember(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "pilot_neutral_novice") && (!hasObjVar(player, "rsfMember")));
    }
    public boolean naboo_privateer_trainer_3_condition_hasCompletedQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasCompletedQuest(player, "destroy_surpriseattack", "naboo_privateer_1"));
    }
    public boolean naboo_privateer_trainer_3_condition_hasCompletedQuestTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasCompletedQuest(player, "destroy", "naboo_privateer_2"));
    }
    public boolean naboo_privateer_trainer_3_condition_hasCompletedQuestThree(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasCompletedQuest(player, "patrol", "naboo_privateer_3"));
    }
    public boolean naboo_privateer_trainer_3_condition_hasCompletedQuestFour(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasCompletedQuest(player, "destroy", "naboo_privateer_4"));
    }
    public boolean naboo_privateer_trainer_3_condition_missionOneFailed(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasFailedQuest(player, "destroy_surpriseattack", "naboo_privateer_1") || space_quest.hasAbortedQuest(player, "destroy_surpriseattack", "naboo_privateer_1"))
        {
            return true;
        }
        return false;
    }
    public boolean naboo_privateer_trainer_3_condition_isReadyForFirstTraining(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasSkill(player, "pilot_neutral_starships_01") || hasSkill(player, "pilot_neutral_procedures_01") || hasSkill(player, "pilot_neutral_weapons_01") || hasSkill(player, "pilot_neutral_droid_01"))
        {
            return false;
        }
        else 
        {
            return true;
        }
    }
    public boolean naboo_privateer_trainer_3_condition_isReadyForMoreTraining(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.isPlayerQualifiedForSkill(player, "pilot_neutral_starships_01"));
    }
    public boolean naboo_privateer_trainer_3_condition_hasAllTierOneSkills(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasSkill(player, "pilot_neutral_starships_01"))
        {
            return false;
        }
        else if (!hasSkill(player, "pilot_neutral_procedures_01"))
        {
            return false;
        }
        else if (!hasSkill(player, "pilot_neutral_weapons_01"))
        {
            return false;
        }
        else if (!hasSkill(player, "pilot_neutral_droid_01"))
        {
            return false;
        }
        else 
        {
            return true;
        }
    }
    public boolean naboo_privateer_trainer_3_condition_missionTwoFailed(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasFailedQuest(player, "destroy", "naboo_privateer_2") || space_quest.hasAbortedQuest(player, "destroy", "naboo_privateer_2"))
        {
            return true;
        }
        return false;
    }
    public boolean naboo_privateer_trainer_3_condition_beenRewardedForQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasCompletedQuest(player, "destroy_surpriseattack", "naboo_privateer_1"))
        {
            return true;
        }
        return ((space_quest.hasCompletedQuest(player, "destroy_surpriseattack", "naboo_privateer_1")) && (!space_quest.hasReceivedReward(player, "patrol", "naboo_privateer_1")));
    }
    public boolean naboo_privateer_trainer_3_condition_beenRewardedForQuestTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasCompletedQuest(player, "destroy", "naboo_privateer_1"))
        {
            return true;
        }
        return ((space_quest.hasCompletedQuest(player, "destroy", "naboo_privateer_1")) && (space_quest.hasReceivedReward(player, "destroy", "naboo_privateer_1")));
    }
    public boolean naboo_privateer_trainer_3_condition_beenRewardedForQuestThree(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasCompletedQuest(player, "patrol", "naboo_privateer_3"))
        {
            return true;
        }
        return ((space_quest.hasCompletedQuest(player, "patrol", "naboo_privateer_3")) && (space_quest.hasReceivedReward(player, "patrol", "naboo_privateer_3")));
    }
    public boolean naboo_privateer_trainer_3_condition_beenRewardedForQuestFour(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasCompletedQuest(player, "destroy", "naboo_privateer_4"))
        {
            return true;
        }
        return ((space_quest.hasCompletedQuest(player, "destroy", "naboo_privateer_4")) && (space_quest.hasReceivedReward(player, "destroy", "naboo_privateer_4")));
    }
    public boolean naboo_privateer_trainer_3_condition_missionThreeFailed(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasFailedQuest(player, "patrol", "naboo_privateer_3") || space_quest.hasAbortedQuest(player, "patrol", "naboo_privateer_3"))
        {
            return true;
        }
        return false;
    }
    public boolean naboo_privateer_trainer_3_condition_missionFourFailed(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasFailedQuest(player, "destroy", "naboo_privateer_4") || space_quest.hasAbortedQuest(player, "destroy", "naboo_privateer_4"))
        {
            return true;
        }
        return false;
    }
    public boolean naboo_privateer_trainer_3_condition_hasStarshipSkill1(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "pilot_neutral_starships_01"));
    }
    public boolean naboo_privateer_trainer_3_condition_hasDroidSkill1(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "pilot_neutral_droid_01"));
    }
    public boolean naboo_privateer_trainer_3_condition_hasProceduresSkill1(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "pilot_neutral_procedures_01"));
    }
    public boolean naboo_privateer_trainer_3_condition_hasWeaponsSkill1(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "pilot_neutral_weapons_01"));
    }
    public boolean naboo_privateer_trainer_3_condition_has_goMeetLordUnset(obj_id player, obj_id npc) throws InterruptedException
    {
        return ((utils.hasScriptVar(player, "goMeetLordUnset")) && !(space_quest.hasQuest(player)));
    }
    public boolean naboo_privateer_trainer_3_condition_FAILED_mission_1(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasFailedQuest(player, "inspect", "privateer_inspection_1") || space_quest.hasAbortedQuest(player, "inspect", "privateer_inspection_1"))
        {
            return true;
        }
        return false;
    }
    public boolean naboo_privateer_trainer_3_condition_WON_mission_1(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasCompletedQuest(player, "inspect", "privateer_inspection_1"));
    }
    public boolean naboo_privateer_trainer_3_condition_isGenderMale(obj_id player, obj_id npc) throws InterruptedException
    {
        return (getGender(player) == GENDER_MALE);
    }
    public boolean naboo_privateer_trainer_3_condition_has_goBackToKyatt(obj_id player, obj_id npc) throws InterruptedException
    {
        return ((utils.hasScriptVar(player, "goBackToKyatt")) && !(space_quest.hasQuest(player)));
    }
    public boolean naboo_privateer_trainer_3_condition_WON_mission_2(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasCompletedQuest(player, "patrol", "naboo_privateer_8"));
    }
    public boolean naboo_privateer_trainer_3_condition_FAILED_mission_2(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasFailedQuest(player, "patrol", "naboo_privateer_8") || space_quest.hasAbortedQuest(player, "patrol", "naboo_privateer_8"))
        {
            return true;
        }
        return false;
    }
    public void naboo_privateer_trainer_3_action_rememberPlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.setScriptVar(player, "metRsfTrainer", true);
    }
    public void naboo_privateer_trainer_3_action_grantNoviceSkillBox(obj_id player, obj_id npc) throws InterruptedException
    {
        grantSkill(player, "pilot_neutral_novice");
        playMusic(player, "sound/music_themequest_acc_general.snd");
        setObjVar(player, "rsfMember", true);
    }
    public void naboo_privateer_trainer_3_action_signUpForRsf(obj_id player, obj_id npc) throws InterruptedException
    {
        playMusic(player, "sound/music_themequest_acc_general.snd");
        setObjVar(player, "rsfMember", true);
    }
    public void naboo_privateer_trainer_3_action_grantMissionOne(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "patrol", "naboo_privateer_1");
    }
    public void naboo_privateer_trainer_3_action_grantMissionTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "destroy", "naboo_privateer_2");
    }
    public void naboo_privateer_trainer_3_action_grantMissionThree(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "patrol", "naboo_privateer_3");
    }
    public void naboo_privateer_trainer_3_action_grantMissionFour(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "destroy", "naboo_privateer_4");
    }
    public void naboo_privateer_trainer_3_action_rewardForQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.giveReward(player, "patrol", "naboo_privateer_1", 500);
    }
    public void naboo_privateer_trainer_3_action_rewardForQuestTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.giveReward(player, "destroy", "naboo_privateer_2", 800);
    }
    public void naboo_privateer_trainer_3_action_rewardForQuestThree(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.giveReward(player, "patrol", "naboo_privateer_3", 1000);
    }
    public void naboo_privateer_trainer_3_action_rewardForQuestFour(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.giveReward(player, "destroy", "naboo_privateer_4", 2000);
    }
    public void naboo_privateer_trainer_3_action_grantDroidSkill(obj_id player, obj_id npc) throws InterruptedException
    {
        grantSkill(player, "pilot_neutral_droid_01");
        playMusic(player, "sound/music_themequest_acc_general.snd");
    }
    public void naboo_privateer_trainer_3_action_grantStarshipSkill(obj_id player, obj_id npc) throws InterruptedException
    {
        grantSkill(player, "pilot_neutral_starships_01");
        playMusic(player, "sound/music_themequest_acc_general.snd");
    }
    public void naboo_privateer_trainer_3_action_grantProceduresSkill(obj_id player, obj_id npc) throws InterruptedException
    {
        grantSkill(player, "pilot_neutral_procedures_01");
        playMusic(player, "sound/music_themequest_acc_general.snd");
    }
    public void naboo_privateer_trainer_3_action_grantWeaponsSkill(obj_id player, obj_id npc) throws InterruptedException
    {
        grantSkill(player, "pilot_neutral_weapons_01");
        playMusic(player, "sound/music_themequest_acc_general.snd");
    }
    public void naboo_privateer_trainer_3_action_buyWeaponsSkill1(obj_id player, obj_id npc) throws InterruptedException
    {
        skill.purchaseSkill(player, "pilot_neutral_weapons_01");
        playMusic(player, "sound/music_themequest_acc_general.snd");
    }
    public void naboo_privateer_trainer_3_action_buyDroidSkill1(obj_id player, obj_id npc) throws InterruptedException
    {
        skill.purchaseSkill(player, "pilot_neutral_droid_01");
        playMusic(player, "sound/music_themequest_acc_general.snd");
    }
    public void naboo_privateer_trainer_3_action_buyProcedureSkill1(obj_id player, obj_id npc) throws InterruptedException
    {
        skill.purchaseSkill(player, "pilot_neutral_procedures_01");
        playMusic(player, "sound/music_themequest_acc_general.snd");
    }
    public void naboo_privateer_trainer_3_action_buyStarshipSkill1(obj_id player, obj_id npc) throws InterruptedException
    {
        skill.purchaseSkill(player, "pilot_neutral_starships_01");
        playMusic(player, "sound/music_themequest_acc_general.snd");
    }
    public void naboo_privateer_trainer_3_action_grantPatrolDutyMission(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "patrol_duty", "naboo_privateer_5");
    }
    public void naboo_privateer_trainer_3_action_grantDestroyDutyMission(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "destroy_duty", "naboo_privateer_6");
    }
    public void naboo_privateer_trainer_3_action_grantEscortDutyMission(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "escort_duty", "naboo_privateer_7");
    }
    public void naboo_privateer_trainer_3_action_grantQuestEight(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "destroy", "naboo_privateer_8");
    }
    public void naboo_privateer_trainer_3_action_set_goMeetLordUnset(obj_id player, obj_id npc) throws InterruptedException
    {
        playMusic(player, "sound/music_themequest_acc_general.snd");
        utils.setScriptVar(player, "goMeetLordUnset", true);
    }
    public void naboo_privateer_trainer_3_action_give_Mission_1(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "inspect", "privateer_inspection_1");
    }
    public void naboo_privateer_trainer_3_action_set_goBackToKyatt(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.setScriptVar(player, "goBackToKyatt", true);
    }
    public void naboo_privateer_trainer_3_action_give_Mission_3(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "escort_duty", "naboo_privateer_9");
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.naboo_privateer_trainer_3");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
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
        setInvulnerable(self, true);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        detachScript(self, "npc.conversation.naboo_privateer_trainer_3");
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
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (naboo_privateer_trainer_3_condition_has_goBackToKyatt(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_62a2f90f");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (naboo_privateer_trainer_3_condition_has_goMeetLordUnset(player, self))
        {
            doAnimationAction(self, "check_wrist_device");
            string_id message = new string_id(c_stringFile, "s_f1acfde0");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_privateer_trainer_3_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_31dd2d63");
                }
                setObjVar(player, "conversation.naboo_privateer_trainer_3.branchId", 2);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(self);
                npcStartConversation(player, self, "naboo_privateer_trainer_3", null, pp, responses);
            }
            else 
            {
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(self);
                chat.chat(self, player, null, null, pp);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_privateer_trainer_3_condition_FAILED_mission_1(player, self))
        {
            naboo_privateer_trainer_3_action_give_Mission_1(player, self);
            string_id message = new string_id(c_stringFile, "s_aeab51da");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (naboo_privateer_trainer_3_condition_WON_mission_1(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_165b7aa9");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_privateer_trainer_3_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_a927c891");
                }
                setObjVar(player, "conversation.naboo_privateer_trainer_3.branchId", 8);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(self);
                npcStartConversation(player, self, "naboo_privateer_trainer_3", null, pp, responses);
            }
            else 
            {
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(self);
                chat.chat(self, player, null, null, pp);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_privateer_trainer_3_condition_FAILED_mission_2(player, self))
        {
            doAnimationAction(self, "rub_chin_thoughtful");
            string_id message = new string_id(c_stringFile, "s_4089fc2d");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (naboo_privateer_trainer_3_condition_WON_mission_2(player, self))
        {
            doAnimationAction(self, "greet");
            string_id message = new string_id(c_stringFile, "s_da5e9261");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_privateer_trainer_3_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_36a4e374");
                }
                setObjVar(player, "conversation.naboo_privateer_trainer_3.branchId", 14);
                npcStartConversation(player, self, "naboo_privateer_trainer_3", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_privateer_trainer_3_condition_alreadyHasAQuest(player, self))
        {
            doAnimationAction(self, "pound_fist_palm");
            string_id message = new string_id(c_stringFile, "s_f25b96ad");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (naboo_privateer_trainer_3_condition__defaultCondition(player, self))
        {
            doAnimationAction(self, "standing_placate");
            string_id message = new string_id(c_stringFile, "s_4529dfcf");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("naboo_privateer_trainer_3"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.naboo_privateer_trainer_3.branchId");
        if (branchId == 2 && response.equals("s_31dd2d63"))
        {
            if (naboo_privateer_trainer_3_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "wave_finger_warning");
                string_id message = new string_id(c_stringFile, "s_984023b3");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_privateer_trainer_3_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_36a4e374");
                    }
                    setObjVar(player, "conversation.naboo_privateer_trainer_3.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.naboo_privateer_trainer_3.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You're late, Captain %TU.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && response.equals("s_36a4e374"))
        {
            if (naboo_privateer_trainer_3_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "explain");
                string_id message = new string_id(c_stringFile, "s_91935719");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_privateer_trainer_3_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7c9c0437");
                    }
                    setObjVar(player, "conversation.naboo_privateer_trainer_3.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.naboo_privateer_trainer_3.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I should say not. You must get underway immediately. Are you prepared for your first mission as the famed 'Captain Ky'at?'' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && response.equals("s_7c9c0437"))
        {
            if (naboo_privateer_trainer_3_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "pound_fist_palm");
                naboo_privateer_trainer_3_action_give_Mission_1(player, self);
                string_id message = new string_id(c_stringFile, "s_76a99d5b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_privateer_trainer_3_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d70dba34");
                    }
                    setObjVar(player, "conversation.naboo_privateer_trainer_3.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.naboo_privateer_trainer_3.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Good. We've had some trouble with militant Gungans fleeing to the Rebel Alliance. They have help from Rebel forgers who provide fake transit documents for them. We think they are shipping these illegal documents in small quantities aboard stunt fighters and cruisers.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_d70dba34"))
        {
            if (naboo_privateer_trainer_3_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "goodbye");
                string_id message = new string_id(c_stringFile, "s_3f06def0");
                removeObjVar(player, "conversation.naboo_privateer_trainer_3.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Hunt down the Rebel smugglers. Disable their starships. Extract the fake documents. Is that simple enough for you?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && response.equals("s_a927c891"))
        {
            if (naboo_privateer_trainer_3_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_c5bfd569");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_privateer_trainer_3_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_privateer_trainer_3_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c699352d");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_31ab4807");
                    }
                    setObjVar(player, "conversation.naboo_privateer_trainer_3.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.naboo_privateer_trainer_3.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Very good performance out there, Captain %TU. The local authorities will be very pleased. Now, the Gungan traitors will be easy to apprehend.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && response.equals("s_c699352d"))
        {
            if (naboo_privateer_trainer_3_condition_isGenderMale(player, self))
            {
                naboo_privateer_trainer_3_action_set_goBackToKyatt(player, self);
                string_id message = new string_id(c_stringFile, "s_4a211440");
                removeObjVar(player, "conversation.naboo_privateer_trainer_3.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (naboo_privateer_trainer_3_condition__defaultCondition(player, self))
            {
                naboo_privateer_trainer_3_action_set_goBackToKyatt(player, self);
                string_id message = new string_id(c_stringFile, "s_4b698d1f");
                removeObjVar(player, "conversation.naboo_privateer_trainer_3.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Nothing to worry about just yet. Why don't you check in with Captain Ky'att? Take a stroll around the palace, maybe?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && response.equals("s_31ab4807"))
        {
            if (naboo_privateer_trainer_3_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "shrug_hands");
                naboo_privateer_trainer_3_action_set_goBackToKyatt(player, self);
                string_id message = new string_id(c_stringFile, "s_8accb5db");
                removeObjVar(player, "conversation.naboo_privateer_trainer_3.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Nothing to worry about just yet. Why don't you check in with Captain Ky'att? Take a stroll around the palace, maybe?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && response.equals("s_36a4e374"))
        {
            if (naboo_privateer_trainer_3_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "bow");
                string_id message = new string_id(c_stringFile, "s_245c99c3");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_privateer_trainer_3_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2a4b4d30");
                    }
                    setObjVar(player, "conversation.naboo_privateer_trainer_3.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.naboo_privateer_trainer_3.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'So, you've done well in Captain Ky'att's name. Congratulations. Are you ready for more serious work?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && response.equals("s_2a4b4d30"))
        {
            if (naboo_privateer_trainer_3_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "explain");
                string_id message = new string_id(c_stringFile, "s_7cce6810");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_privateer_trainer_3_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_574c145e");
                    }
                    setObjVar(player, "conversation.naboo_privateer_trainer_3.branchId", 16);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.naboo_privateer_trainer_3.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'First, let me thank you for breaking up the Gungan's fake identity ring. It has led to scores of arrests here on Naboo and on planet Rori. No doubt the local authorities are eager to see even more from you.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && response.equals("s_574c145e"))
        {
            if (naboo_privateer_trainer_3_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "explain");
                naboo_privateer_trainer_3_action_give_Mission_3(player, self);
                string_id message = new string_id(c_stringFile, "s_523f9f83");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_privateer_trainer_3_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_96b39c0d");
                    }
                    setObjVar(player, "conversation.naboo_privateer_trainer_3.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.naboo_privateer_trainer_3.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Well, we have rounded up many more traitors than we could ever process here on Naboo. Thus, we are sending them to other worlds more equipped to handle these things.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && response.equals("s_96b39c0d"))
        {
            if (naboo_privateer_trainer_3_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "explain");
                string_id message = new string_id(c_stringFile, "s_8050d040");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_privateer_trainer_3_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f2e1c3e5");
                    }
                    setObjVar(player, "conversation.naboo_privateer_trainer_3.branchId", 18);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.naboo_privateer_trainer_3.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You will be assigned as fighter cover for a massive relocation effort! Ensure that our prisoners make it to their destinations... and make sure that the Alliance fails to liberate them.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && response.equals("s_f2e1c3e5"))
        {
            if (naboo_privateer_trainer_3_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "bow");
                string_id message = new string_id(c_stringFile, "s_4434402f");
                removeObjVar(player, "conversation.naboo_privateer_trainer_3.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(self);
                npcSpeak(player, pp);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Perhaps not directly... but the Alliance did fire the first shot by attempting to build fictional identities for these Gungan traitors! Now, I'd bet they're going to intercept the prisoner transports and attempt to free their comrades.' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.naboo_privateer_trainer_3.branchId");
        return SCRIPT_CONTINUE;
    }
}
