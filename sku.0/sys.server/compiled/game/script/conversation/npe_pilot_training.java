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
import script.library.conversation;
import script.library.groundquests;
import script.library.npe;
import script.library.skill;
import script.library.space_quest;
import script.library.space_utils;
import script.library.utils;

public class npe_pilot_training extends script.base_script
{
    public npe_pilot_training()
    {
    }
    public static String c_stringFile = "conversation/npe_pilot_training";
    public boolean npe_pilot_training_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean npe_pilot_training_condition_hasFailedTrainMission1(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "npe_pilot_first_mission_no_ground", "failedTraining_1") || groundquests.isTaskActive(player, "npe_pilot_first_mission_no_ground", "failedTraining_1b"));
    }
    public boolean npe_pilot_training_condition_isOnMission(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "npe_pilot_first_mission_no_ground", "accessSpace") || groundquests.isTaskActive(player, "npe_pilot_training_2", "launch") || groundquests.isTaskActive(player, "npe_pilot_training_3", "launch"));
    }
    public boolean npe_pilot_training_condition_hasWonTrainMission1(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedQuest(player, "npe_pilot_first_mission_no_ground") || groundquests.isTaskActive(player, "npe_pilot_first_mission_no_ground", "returnToSerissu"));
    }
    public boolean npe_pilot_training_condition_startedEasy(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActiveOrComplete(player, "npe_pilot_easy_1");
    }
    public boolean npe_pilot_training_condition_hasFailedTrainMission2(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "npe_pilot_training_2", "failedTraining_2");
    }
    public boolean npe_pilot_training_condition_hasWonTrainMission2(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedQuest(player, "npe_pilot_training_2") || groundquests.isTaskActive(player, "npe_pilot_training_2", "report"));
    }
    public boolean npe_pilot_training_condition_hasFailedTrainMission3(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "npe_pilot_training_3", "failedTraining3");
    }
    public boolean npe_pilot_training_condition_hasWonTrainMission3(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedQuest(player, "npe_pilot_training_3") || groundquests.isTaskActive(player, "npe_pilot_training_3", "report"));
    }
    public boolean npe_pilot_training_condition_noShip(obj_id player, obj_id npc) throws InterruptedException
    {
        return (!space_utils.hasShip(player) && groundquests.isQuestActiveOrComplete(player, "npe_pilot_first_mission_no_ground"));
    }
    public boolean npe_pilot_training_condition_onFirstQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "npe_pilot_first_mission_no_ground", "accessSpace") || groundquests.isTaskActive(player, "npe_pilot_first_mission_no_ground", "training_1") || groundquests.isTaskActive(player, "npe_pilot_first_mission_no_ground", "training_1b"));
    }
    public boolean npe_pilot_training_condition_canTakeQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasObjVar(player, "npe.finishedTemplate");
    }
    public void npe_pilot_training_action_grantTrainMission1(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.clearQuestFlags(player, "patrol", "npe_training_1");
        space_quest.clearQuestFlags(player, "patrol", "npe_training_1b");
        groundquests.clearQuest(player, "npe_pilot_first_mission_no_ground");
        groundquests.grantQuest(player, "npe_pilot_first_mission_no_ground");
        if (!hasObjVar(player, npe.QUEST_REWORK_VAR))
        {
            setObjVar(player, npe.QUEST_REWORK_VAR, npe.QUEST_ENUMERATION);
        }
    }
    public void npe_pilot_training_action_grantTrainMission2(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.clearQuestFlags(player, "patrol", "npe_training_2");
        space_quest.clearQuestFlags(player, "destroy", "npe_training_2a");
        space_quest.clearQuestFlags(player, "patrol", "npe_training_2b");
        groundquests.clearQuest(player, "npe_pilot_training_2");
        groundquests.grantQuest(player, "npe_pilot_training_2");
    }
    public void npe_pilot_training_action_grantTrainMission3(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.clearQuestFlags(player, "escort", "npe_training_3");
        space_quest.clearQuestFlags(player, "patrol", "npe_training_3a");
        groundquests.clearQuest(player, "npe_pilot_training_3");
        groundquests.grantQuest(player, "npe_pilot_training_3");
    }
    public void npe_pilot_training_action_grantTrainMissionNoGround(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "npe_pilot_first_mission_no_ground");
        if (!hasObjVar(player, npe.QUEST_REWORK_VAR))
        {
            setObjVar(player, npe.QUEST_REWORK_VAR, npe.QUEST_ENUMERATION);
        }
    }
    public void npe_pilot_training_action_grantReward1(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "reported");
    }
    public void npe_pilot_training_action_grantReward2(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "reported");
    }
    public void npe_pilot_training_action_grantReward3(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "reported");
    }
    public void npe_pilot_training_action_grantTalktoLaetin(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "npe_pilot_laetin2");
    }
    public void npe_pilot_training_action_giveShip(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_utils.hasShip(player))
        {
            space_quest.grantNewbieShip(player, "neutral");
        }
    }
    public void npe_pilot_training_action_grantNoviceSkill(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "found_serissu");
        groundquests.sendSignal(player, "giveBasicTraining");
        if (!space_utils.hasShip(player))
        {
            space_quest.grantNewbieShip(player, "neutral");
        }
    }
    public void npe_pilot_training_action_facePlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
    }
    public int npe_pilot_training_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_297"))
        {
            if (npe_pilot_training_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute1");
                string_id message = new string_id(c_stringFile, "s_298");
                utils.removeScriptVar(player, "conversation.npe_pilot_training.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_pilot_training_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_132"))
        {
            npe_pilot_training_action_grantReward3(player, npc);
            if (npe_pilot_training_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_134");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_pilot_training_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_78");
                    }
                    utils.setScriptVar(player, "conversation.npe_pilot_training.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_pilot_training.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_pilot_training_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_78"))
        {
            if (npe_pilot_training_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wave1");
                npe_pilot_training_action_grantTalktoLaetin(player, npc);
                string_id message = new string_id(c_stringFile, "s_79");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_pilot_training_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
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
                    utils.setScriptVar(player, "conversation.npe_pilot_training.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_pilot_training.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_pilot_training_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_54"))
        {
            doAnimationAction(player, "bow");
            if (npe_pilot_training_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_55");
                utils.removeScriptVar(player, "conversation.npe_pilot_training.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_pilot_training_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_281"))
        {
            if (npe_pilot_training_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                string_id message = new string_id(c_stringFile, "s_282");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_pilot_training_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_283");
                    }
                    utils.setScriptVar(player, "conversation.npe_pilot_training.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_pilot_training.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_pilot_training_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_283"))
        {
            if (npe_pilot_training_condition__defaultCondition(player, npc))
            {
                npe_pilot_training_action_grantTrainMission3(player, npc);
                string_id message = new string_id(c_stringFile, "s_284");
                utils.removeScriptVar(player, "conversation.npe_pilot_training.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_pilot_training_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_154"))
        {
            npe_pilot_training_action_grantReward2(player, npc);
            if (npe_pilot_training_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_189");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_pilot_training_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_193");
                    }
                    utils.setScriptVar(player, "conversation.npe_pilot_training.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_pilot_training.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_pilot_training_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_193"))
        {
            if (npe_pilot_training_condition__defaultCondition(player, npc))
            {
                npe_pilot_training_action_grantTrainMission3(player, npc);
                string_id message = new string_id(c_stringFile, "s_197");
                utils.removeScriptVar(player, "conversation.npe_pilot_training.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_pilot_training_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_265"))
        {
            if (npe_pilot_training_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_270");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_pilot_training_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_271");
                    }
                    utils.setScriptVar(player, "conversation.npe_pilot_training.branchId", 16);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_pilot_training.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_pilot_training_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_271"))
        {
            if (npe_pilot_training_condition__defaultCondition(player, npc))
            {
                npe_pilot_training_action_grantTrainMission2(player, npc);
                string_id message = new string_id(c_stringFile, "s_272");
                utils.removeScriptVar(player, "conversation.npe_pilot_training.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_pilot_training_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_176"))
        {
            npe_pilot_training_action_grantReward1(player, npc);
            if (npe_pilot_training_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_201");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_pilot_training_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
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
                    utils.setScriptVar(player, "conversation.npe_pilot_training.branchId", 19);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_pilot_training.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_pilot_training_handleBranch19(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_221"))
        {
            if (npe_pilot_training_condition__defaultCondition(player, npc))
            {
                npe_pilot_training_action_grantTrainMission2(player, npc);
                string_id message = new string_id(c_stringFile, "s_223");
                utils.removeScriptVar(player, "conversation.npe_pilot_training.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_pilot_training_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_247"))
        {
            if (npe_pilot_training_condition__defaultCondition(player, npc))
            {
                npe_pilot_training_action_grantTrainMission1(player, npc);
                string_id message = new string_id(c_stringFile, "s_252");
                utils.removeScriptVar(player, "conversation.npe_pilot_training.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_pilot_training_handleBranch24(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_65"))
        {
            if (npe_pilot_training_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_67");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_pilot_training_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_69");
                    }
                    utils.setScriptVar(player, "conversation.npe_pilot_training.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_pilot_training.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_pilot_training_handleBranch25(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_69"))
        {
            if (npe_pilot_training_condition__defaultCondition(player, npc))
            {
                npe_pilot_training_action_grantNoviceSkill(player, npc);
                string_id message = new string_id(c_stringFile, "s_71");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_pilot_training_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_73");
                    }
                    utils.setScriptVar(player, "conversation.npe_pilot_training.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_pilot_training.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_pilot_training_handleBranch26(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_73"))
        {
            if (npe_pilot_training_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_left");
                npe_pilot_training_action_grantTrainMissionNoGround(player, npc);
                string_id message = new string_id(c_stringFile, "s_75");
                utils.removeScriptVar(player, "conversation.npe_pilot_training.branchId");
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
            detachScript(self, "conversation.npe_pilot_training");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setName(self, "Serissu (Pilot Trainer)");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setName(self, "Serissu (Pilot Trainer)");
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
        detachScript(self, "conversation.npe_pilot_training");
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
        if (npe_pilot_training_condition_noShip(player, npc))
        {
            doAnimationAction(npc, "greet");
            doAnimationAction(player, "greet");
            npe_pilot_training_action_giveShip(player, npc);
            string_id message = new string_id(c_stringFile, "s_59");
            prose_package pp = new prose_package();
            pp.stringId = message;
            pp.actor.set(player);
            pp.target.set(npc);
            chat.chat(npc, player, null, null, pp);
            return SCRIPT_CONTINUE;
        }
        if (npe_pilot_training_condition_startedEasy(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_46");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (npe_pilot_training_condition_isOnMission(player, npc))
        {
            npe_pilot_training_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_235");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (npe_pilot_training_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_297");
                }
                utils.setScriptVar(player, "conversation.npe_pilot_training.branchId", 3);
                npcStartConversation(player, npc, "npe_pilot_training", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (npe_pilot_training_condition_hasWonTrainMission3(player, npc))
        {
            npe_pilot_training_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_130");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (npe_pilot_training_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
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
                utils.setScriptVar(player, "conversation.npe_pilot_training.branchId", 5);
                npcStartConversation(player, npc, "npe_pilot_training", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (npe_pilot_training_condition_hasFailedTrainMission3(player, npc))
        {
            npe_pilot_training_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_148");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (npe_pilot_training_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_281");
                }
                utils.setScriptVar(player, "conversation.npe_pilot_training.branchId", 9);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "npe_pilot_training", null, pp, responses);
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
        if (npe_pilot_training_condition_hasWonTrainMission2(player, npc))
        {
            npe_pilot_training_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_152");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (npe_pilot_training_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
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
                utils.setScriptVar(player, "conversation.npe_pilot_training.branchId", 12);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "npe_pilot_training", null, pp, responses);
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
        if (npe_pilot_training_condition_hasFailedTrainMission2(player, npc))
        {
            doAnimationAction(npc, "sigh_deeply");
            npe_pilot_training_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_170");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (npe_pilot_training_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_265");
                }
                utils.setScriptVar(player, "conversation.npe_pilot_training.branchId", 15);
                npcStartConversation(player, npc, "npe_pilot_training", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (npe_pilot_training_condition_hasWonTrainMission1(player, npc))
        {
            npe_pilot_training_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_174");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (npe_pilot_training_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_176");
                }
                utils.setScriptVar(player, "conversation.npe_pilot_training.branchId", 18);
                npcStartConversation(player, npc, "npe_pilot_training", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (npe_pilot_training_condition_hasFailedTrainMission1(player, npc))
        {
            npe_pilot_training_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_188");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (npe_pilot_training_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_247");
                }
                utils.setScriptVar(player, "conversation.npe_pilot_training.branchId", 21);
                npcStartConversation(player, npc, "npe_pilot_training", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (npe_pilot_training_condition_onFirstQuest(player, npc))
        {
            npe_pilot_training_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_56");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (npe_pilot_training_condition_canTakeQuest(player, npc))
        {
            doAnimationAction(npc, "nod_head_once");
            npe_pilot_training_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_63");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (npe_pilot_training_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_65");
                }
                utils.setScriptVar(player, "conversation.npe_pilot_training.branchId", 24);
                npcStartConversation(player, npc, "npe_pilot_training", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (npe_pilot_training_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_77");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("npe_pilot_training"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.npe_pilot_training.branchId");
        if (branchId == 3 && npe_pilot_training_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && npe_pilot_training_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && npe_pilot_training_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && npe_pilot_training_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && npe_pilot_training_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && npe_pilot_training_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && npe_pilot_training_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && npe_pilot_training_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && npe_pilot_training_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && npe_pilot_training_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && npe_pilot_training_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && npe_pilot_training_handleBranch19(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && npe_pilot_training_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && npe_pilot_training_handleBranch24(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && npe_pilot_training_handleBranch25(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && npe_pilot_training_handleBranch26(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.npe_pilot_training.branchId");
        return SCRIPT_CONTINUE;
    }
}
