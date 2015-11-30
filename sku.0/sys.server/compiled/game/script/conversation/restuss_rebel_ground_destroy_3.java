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
import script.library.groundquests;
import script.library.restuss_event;
import script.library.utils;

public class restuss_rebel_ground_destroy_3 extends script.base_script
{
    public restuss_rebel_ground_destroy_3()
    {
    }
    public static String c_stringFile = "conversation/restuss_rebel_ground_destroy_3";
    public boolean restuss_rebel_ground_destroy_3_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean restuss_rebel_ground_destroy_3_condition_stage3ready(obj_id player, obj_id npc) throws InterruptedException
    {
        if (factions.isRebel(player) && restuss_event.isRestussInStageThree(npc))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean restuss_rebel_ground_destroy_3_condition_mission1Active(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isQuestActive(player, "restuss_rebel_st3_destroy_installation_1") || groundquests.isQuestActive(player, "restuss_rebel_st3_destroy_installation_1_commando"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean restuss_rebel_ground_destroy_3_condition_mission1Success(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "restuss_rebel_st3_destroy_installation_1", "returnVoldez1") || groundquests.isTaskActive(player, "restuss_rebel_st3_destroy_installation_1_commando", "returnVoldez1"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean restuss_rebel_ground_destroy_3_condition_completedMission1(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.hasCompletedQuest(player, "restuss_rebel_st3_destroy_installation_1") || groundquests.hasCompletedQuest(player, "restuss_rebel_st3_destroy_installation_1_commando"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean restuss_rebel_ground_destroy_3_condition_mission2Active(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "restuss_rebel_st3_destroy_boss");
    }
    public boolean restuss_rebel_ground_destroy_3_condition_mission2Success(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "restuss_rebel_st3_destroy_boss", "returnVoldez2");
    }
    public boolean restuss_rebel_ground_destroy_3_condition_notCommando(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean restuss_rebel_ground_destroy_3_condition_commando(obj_id player, obj_id npc) throws InterruptedException
    {
        return false;
    }
    public boolean restuss_rebel_ground_destroy_3_condition_completedMission2(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "restuss_rebel_st3_destroy_boss");
    }
    public boolean restuss_rebel_ground_destroy_3_condition_playerRebel(obj_id player, obj_id npc) throws InterruptedException
    {
        if (factions.isRebel(player))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean restuss_rebel_ground_destroy_3_condition_isPlayerImperial(obj_id player, obj_id npc) throws InterruptedException
    {
        return factions.isImperial(player);
    }
    public void restuss_rebel_ground_destroy_3_action_giveAntenna(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!groundquests.isQuestActive(player, "restuss_rebel_st3_destroy_boss"))
        {
            groundquests.grantQuest(player, "restuss_rebel_st3_destroy_installation_1");
        }
    }
    public void restuss_rebel_ground_destroy_3_action_reward1(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "returnedVoldez1");
    }
    public void restuss_rebel_ground_destroy_3_action_giveKillBoss(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!groundquests.isQuestActive(player, "restuss_rebel_st3_destroy_installation_1") || !groundquests.isQuestActive(player, "restuss_rebel_st3_destroy_installation_1_commando"))
        {
            groundquests.grantQuest(player, "restuss_rebel_st3_destroy_boss");
        }
    }
    public void restuss_rebel_ground_destroy_3_action_reward2(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "returnedVoldez2");
    }
    public void restuss_rebel_ground_destroy_3_action_giveAntennaCommando(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!groundquests.isQuestActive(player, "restuss_rebel_st3_destroy_boss"))
        {
            groundquests.grantQuest(player, "restuss_rebel_st3_destroy_installation_1_commando");
        }
    }
    public void restuss_rebel_ground_destroy_3_action_eject(obj_id player, obj_id npc) throws InterruptedException
    {
        expelFromBuilding(player);
    }
    public int restuss_rebel_ground_destroy_3_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_74"))
        {
            doAnimationAction(player, "salute1");
            if (restuss_rebel_ground_destroy_3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute1");
                restuss_rebel_ground_destroy_3_action_giveKillBoss(player, npc);
                string_id message = new string_id(c_stringFile, "s_76");
                utils.removeScriptVar(player, "conversation.restuss_rebel_ground_destroy_3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_78"))
        {
            doAnimationAction(player, "shake_head_no");
            if (restuss_rebel_ground_destroy_3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_80");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (restuss_rebel_ground_destroy_3_condition_notCommando(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (restuss_rebel_ground_destroy_3_condition_commando(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (restuss_rebel_ground_destroy_3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_82");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_86");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_90");
                    }
                    utils.setScriptVar(player, "conversation.restuss_rebel_ground_destroy_3.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.restuss_rebel_ground_destroy_3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_94"))
        {
            doAnimationAction(player, "salute1");
            if (restuss_rebel_ground_destroy_3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute1");
                string_id message = new string_id(c_stringFile, "s_96");
                utils.removeScriptVar(player, "conversation.restuss_rebel_ground_destroy_3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int restuss_rebel_ground_destroy_3_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_82"))
        {
            doAnimationAction(player, "salute1");
            if (restuss_rebel_ground_destroy_3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute1");
                restuss_rebel_ground_destroy_3_action_giveAntenna(player, npc);
                string_id message = new string_id(c_stringFile, "s_84");
                utils.removeScriptVar(player, "conversation.restuss_rebel_ground_destroy_3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_86"))
        {
            doAnimationAction(player, "salute1");
            if (restuss_rebel_ground_destroy_3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute1");
                restuss_rebel_ground_destroy_3_action_giveAntennaCommando(player, npc);
                string_id message = new string_id(c_stringFile, "s_88");
                utils.removeScriptVar(player, "conversation.restuss_rebel_ground_destroy_3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_90"))
        {
            doAnimationAction(player, "salute1");
            if (restuss_rebel_ground_destroy_3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute1");
                string_id message = new string_id(c_stringFile, "s_92");
                utils.removeScriptVar(player, "conversation.restuss_rebel_ground_destroy_3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int restuss_rebel_ground_destroy_3_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_27"))
        {
            doAnimationAction(player, "salute1");
            if (restuss_rebel_ground_destroy_3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute1");
                restuss_rebel_ground_destroy_3_action_giveKillBoss(player, npc);
                string_id message = new string_id(c_stringFile, "s_29");
                utils.removeScriptVar(player, "conversation.restuss_rebel_ground_destroy_3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_31"))
        {
            doAnimationAction(player, "shake_head_no");
            if (restuss_rebel_ground_destroy_3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_33");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (restuss_rebel_ground_destroy_3_condition_notCommando(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (restuss_rebel_ground_destroy_3_condition_commando(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (restuss_rebel_ground_destroy_3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_65");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_37");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_42");
                    }
                    utils.setScriptVar(player, "conversation.restuss_rebel_ground_destroy_3.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.restuss_rebel_ground_destroy_3.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_46"))
        {
            doAnimationAction(player, "salute2");
            if (restuss_rebel_ground_destroy_3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute2");
                string_id message = new string_id(c_stringFile, "s_48");
                utils.removeScriptVar(player, "conversation.restuss_rebel_ground_destroy_3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int restuss_rebel_ground_destroy_3_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_65"))
        {
            doAnimationAction(player, "salute1");
            if (restuss_rebel_ground_destroy_3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute1");
                restuss_rebel_ground_destroy_3_action_giveAntenna(player, npc);
                string_id message = new string_id(c_stringFile, "s_67");
                utils.removeScriptVar(player, "conversation.restuss_rebel_ground_destroy_3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_37"))
        {
            doAnimationAction(player, "salute1");
            if (restuss_rebel_ground_destroy_3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute1");
                restuss_rebel_ground_destroy_3_action_giveAntennaCommando(player, npc);
                string_id message = new string_id(c_stringFile, "s_69");
                utils.removeScriptVar(player, "conversation.restuss_rebel_ground_destroy_3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_42"))
        {
            doAnimationAction(player, "salute1");
            if (restuss_rebel_ground_destroy_3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute1");
                string_id message = new string_id(c_stringFile, "s_44");
                utils.removeScriptVar(player, "conversation.restuss_rebel_ground_destroy_3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int restuss_rebel_ground_destroy_3_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_52"))
        {
            doAnimationAction(player, "salute1");
            if (restuss_rebel_ground_destroy_3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute1");
                restuss_rebel_ground_destroy_3_action_giveAntenna(player, npc);
                string_id message = new string_id(c_stringFile, "s_54");
                utils.removeScriptVar(player, "conversation.restuss_rebel_ground_destroy_3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_61"))
        {
            doAnimationAction(player, "salute1");
            if (restuss_rebel_ground_destroy_3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute1");
                restuss_rebel_ground_destroy_3_action_giveAntennaCommando(player, npc);
                string_id message = new string_id(c_stringFile, "s_63");
                utils.removeScriptVar(player, "conversation.restuss_rebel_ground_destroy_3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_57"))
        {
            doAnimationAction(player, "salute1");
            if (restuss_rebel_ground_destroy_3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute1");
                string_id message = new string_id(c_stringFile, "s_59");
                utils.removeScriptVar(player, "conversation.restuss_rebel_ground_destroy_3.branchId");
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
            detachScript(self, "conversation.restuss_rebel_ground_destroy_3");
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
        detachScript(self, "conversation.restuss_rebel_ground_destroy_3");
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
        if (restuss_rebel_ground_destroy_3_condition_isPlayerImperial(player, npc))
        {
            restuss_rebel_ground_destroy_3_action_eject(player, npc);
            string_id message = new string_id(c_stringFile, "s_66");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (restuss_rebel_ground_destroy_3_condition_mission2Success(player, npc))
        {
            doAnimationAction(npc, "salute1");
            doAnimationAction(player, "salute1");
            restuss_rebel_ground_destroy_3_action_reward2(player, npc);
            string_id message = new string_id(c_stringFile, "s_39");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (restuss_rebel_ground_destroy_3_condition_mission1Success(player, npc))
        {
            doAnimationAction(npc, "salute1");
            doAnimationAction(player, "salute1");
            restuss_rebel_ground_destroy_3_action_reward1(player, npc);
            string_id message = new string_id(c_stringFile, "s_10");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (restuss_rebel_ground_destroy_3_condition_mission1Active(player, npc))
        {
            doAnimationAction(npc, "salute1");
            doAnimationAction(player, "salute1");
            string_id message = new string_id(c_stringFile, "s_9");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (restuss_rebel_ground_destroy_3_condition_mission2Active(player, npc))
        {
            doAnimationAction(npc, "salute1");
            doAnimationAction(player, "salute1");
            string_id message = new string_id(c_stringFile, "s_25");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (restuss_rebel_ground_destroy_3_condition_completedMission2(player, npc))
        {
            doAnimationAction(npc, "salute1");
            doAnimationAction(player, "salute1");
            string_id message = new string_id(c_stringFile, "s_72");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (restuss_rebel_ground_destroy_3_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (restuss_rebel_ground_destroy_3_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (restuss_rebel_ground_destroy_3_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_74");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_78");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_94");
                }
                utils.setScriptVar(player, "conversation.restuss_rebel_ground_destroy_3.branchId", 6);
                npcStartConversation(player, npc, "restuss_rebel_ground_destroy_3", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (restuss_rebel_ground_destroy_3_condition_completedMission1(player, npc))
        {
            doAnimationAction(npc, "salute1");
            doAnimationAction(player, "salute1");
            string_id message = new string_id(c_stringFile, "s_24");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (restuss_rebel_ground_destroy_3_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (restuss_rebel_ground_destroy_3_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (restuss_rebel_ground_destroy_3_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_27");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_31");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_46");
                }
                utils.setScriptVar(player, "conversation.restuss_rebel_ground_destroy_3.branchId", 13);
                npcStartConversation(player, npc, "restuss_rebel_ground_destroy_3", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (restuss_rebel_ground_destroy_3_condition_stage3ready(player, npc))
        {
            doAnimationAction(npc, "salute1");
            doAnimationAction(player, "salute1");
            string_id message = new string_id(c_stringFile, "s_50");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (restuss_rebel_ground_destroy_3_condition_notCommando(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (restuss_rebel_ground_destroy_3_condition_commando(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (restuss_rebel_ground_destroy_3_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_52");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_61");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_57");
                }
                utils.setScriptVar(player, "conversation.restuss_rebel_ground_destroy_3.branchId", 20);
                npcStartConversation(player, npc, "restuss_rebel_ground_destroy_3", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (restuss_rebel_ground_destroy_3_condition_playerRebel(player, npc))
        {
            doAnimationAction(npc, "salute1");
            doAnimationAction(player, "salute1");
            string_id message = new string_id(c_stringFile, "s_62");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (restuss_rebel_ground_destroy_3_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "goodbye");
            string_id message = new string_id(c_stringFile, "s_68");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("restuss_rebel_ground_destroy_3"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.restuss_rebel_ground_destroy_3.branchId");
        if (branchId == 6 && restuss_rebel_ground_destroy_3_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && restuss_rebel_ground_destroy_3_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && restuss_rebel_ground_destroy_3_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && restuss_rebel_ground_destroy_3_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && restuss_rebel_ground_destroy_3_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.restuss_rebel_ground_destroy_3.branchId");
        return SCRIPT_CONTINUE;
    }
}
