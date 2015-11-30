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
import script.library.space_quest;
import script.library.utils;
import script.library.xp;

public class wookiee_sidequest extends script.base_script
{
    public wookiee_sidequest()
    {
    }
    public static String c_stringFile = "conversation/wookiee_sidequest";
    public boolean wookiee_sidequest_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean wookiee_sidequest_condition_WkeCheck(obj_id player, obj_id npc) throws InterruptedException
    {
        int species = getSpecies(player);
        String speciesName = utils.getPlayerSpeciesName(species);
        if (speciesName.equals("wookiee"))
        {
            if (hasSkill(player, "pilot_imperial_navy_novice") || hasSkill(player, "pilot_rebel_navy_novice") || hasSkill(player, "pilot_neutral_novice"))
            {
                return true;
            }
            return false;
        }
        return false;
    }
    public boolean wookiee_sidequest_condition_NotPilotChk(obj_id player, obj_id npc) throws InterruptedException
    {
        int species = getSpecies(player);
        String speciesName = utils.getPlayerSpeciesName(species);
        if (speciesName.equals("wookiee"))
        {
            if (!hasSkill(player, "pilot_imperial_navy_novice") || !hasSkill(player, "pilot_rebel_navy_novice") || !hasSkill(player, "pilot_neutral_novice"))
            {
                return true;
            }
            return false;
        }
        return false;
    }
    public boolean wookiee_sidequest_condition_RememberObj(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "space.wookiee.remember"))
        {
            return true;
        }
        return false;
    }
    public boolean wookiee_sidequest_condition_Chk1stMission(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasQuest(player, "patrol", "naboo_wookiee_1"))
        {
            return true;
        }
        return false;
    }
    public boolean wookiee_sidequest_condition_Chk2ndMission(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasQuest(player, "patrol", "naboo_wookiee_2"))
        {
            return true;
        }
        return false;
    }
    public boolean wookiee_sidequest_condition_refusedQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "space.wookiee.no"))
        {
            return true;
        }
        return false;
    }
    public boolean wookiee_sidequest_condition_NoMissions(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasQuest(player, "patrol", "naboo_wookiee_1") || !space_quest.hasQuest(player, "inspect", "naboo_wookiee_2"))
        {
            return true;
        }
        return false;
    }
    public boolean wookiee_sidequest_condition_Won1st(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasWonQuest(player, "patrol", "naboo_wookiee_1"))
        {
            return true;
        }
        return false;
    }
    public boolean wookiee_sidequest_condition_failed1st(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasFailedQuest(player, "patrol", "naboo_wookiee_1") || space_quest.hasAbortedQuest(player, "patrol", "naboo_wookiee_1"))
        {
            return true;
        }
        return false;
    }
    public boolean wookiee_sidequest_condition_RewardChk1stMission(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasReceivedReward(player, "patrol", "naboo_wookiee_1"))
        {
            return true;
        }
        return false;
    }
    public boolean wookiee_sidequest_condition_failed2nd(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasFailedQuest(player, "patrol", "naboo_wookiee_2") || space_quest.hasAbortedQuest(player, "patrol", "naboo_wookiee_2"))
        {
            return true;
        }
        return false;
    }
    public boolean wookiee_sidequest_condition_Won2nd(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasWonQuest(player, "patrol", "naboo_wookiee_2"))
        {
            return true;
        }
        return false;
    }
    public boolean wookiee_sidequest_condition_RewardChk2ndMission(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasReceivedReward(player, "patrol", "naboo_wookiee_2"))
        {
            return true;
        }
        return false;
    }
    public boolean wookiee_sidequest_condition_CompletedSeries(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "space.wookiee.done"))
        {
            return true;
        }
        return false;
    }
    public boolean wookiee_sidequest_condition_ChkMissions(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasQuest(player, "patrol", "naboo_wookiee_1") || space_quest.hasQuest(player, "patrol", "naboo_wookiee_2"))
        {
            return true;
        }
        return false;
    }
    public void wookiee_sidequest_action_animConverse(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        doAnimationAction(npc, "conversation_1");
    }
    public void wookiee_sidequest_action_animExplain(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        doAnimationAction(npc, "explain");
    }
    public void wookiee_sidequest_action_animDisgust(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        doAnimationAction(npc, "shake_head_disgust");
    }
    public void wookiee_sidequest_action_animPointAway(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "point_away");
    }
    public void wookiee_sidequest_action_animFist(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        doAnimationAction(npc, "pound_fist_palm");
    }
    public void wookiee_sidequest_action_animSalute(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        doAnimationAction(npc, "salute1");
    }
    public void wookiee_sidequest_action_animSigh(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        doAnimationAction(npc, "sigh_deeply");
    }
    public void wookiee_sidequest_action_animBow(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        doAnimationAction(npc, "bow");
    }
    public void wookiee_sidequest_action_animNod(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        doAnimationAction(npc, "nod_head_once");
    }
    public void wookiee_sidequest_action_grant1stMission(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "patrol", "naboo_wookiee_1");
        doAnimationAction(npc, "salute1");
        setObjVar(player, "space.wookiee.remember", true);
        removeObjVar(player, "space.wookiee.no");
    }
    public void wookiee_sidequest_action_npcRemember(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "space.wookiee.remember", true);
    }
    public void wookiee_sidequest_action_grantRewardMission1(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        doAnimationAction(npc, "salute1");
        space_quest.giveReward(player, "patrol", "naboo_wookiee_1", 5000);
        xp.grant(player, "pilot_general", 200, true);
    }
    public void wookiee_sidequest_action_grant2ndMission(obj_id player, obj_id npc) throws InterruptedException
    {
        removeObjVar(player, "space_quest.patrol.naboo_wookiee_1");
        setObjVar(player, "space_quest.patrol.naboo_wookiee_2", 0);
        space_quest.grantQuest(player, "patrol", "naboo_wookiee_2");
        doAnimationAction(npc, "salute1");
    }
    public void wookiee_sidequest_action_grantRewardMission2(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        doAnimationAction(npc, "salute1");
        space_quest.giveReward(player, "patrol", "naboo_wookiee_2", 5000);
        xp.grant(player, "pilot_general", 200, true);
        removeObjVar(player, "space_quest.patrol.naboo_wookiee_2");
        removeObjVar(player, "space_quest.patrol.naboo_wookiee_1");
        removeObjVar(player, "space.wookiee.remember");
        setObjVar(player, "space.wookiee.done", true);
    }
    public void wookiee_sidequest_action_setRememberDisgust(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "space.wookiee.remember", true);
        setObjVar(player, "space.wookiee.no", true);
        faceTo(npc, player);
        doAnimationAction(npc, "shake_head_disgust");
    }
    public void wookiee_sidequest_action_setRememberSigh(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "space.wookiee.remember", true);
        setObjVar(player, "space.wookiee.no", true);
        faceTo(npc, player);
        doAnimationAction(npc, "sigh_deeply");
    }
    public void wookiee_sidequest_action_animCelebrate(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        doAnimationAction(npc, "celebrate");
    }
    public void wookiee_sidequest_action_animAccuse(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        doAnimationAction(npc, "point_accusingly");
    }
    public void wookiee_sidequest_action_animNo(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        doAnimationAction(npc, "shake_head_no");
    }
    public int wookiee_sidequest_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_361ea065"))
        {
            if (wookiee_sidequest_condition_Chk2ndMission(player, npc))
            {
                wookiee_sidequest_action_animExplain(player, npc);
                string_id message = new string_id(c_stringFile, "s_3c52272b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (wookiee_sidequest_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_dff4891");
                    }
                    utils.setScriptVar(player, "conversation.wookiee_sidequest.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.wookiee_sidequest.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (wookiee_sidequest_condition_Chk1stMission(player, npc))
            {
                wookiee_sidequest_action_animExplain(player, npc);
                string_id message = new string_id(c_stringFile, "s_caa1385e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (wookiee_sidequest_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b269d401");
                    }
                    utils.setScriptVar(player, "conversation.wookiee_sidequest.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.wookiee_sidequest.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_7c2446cd"))
        {
            if (wookiee_sidequest_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_b5ee0393");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (wookiee_sidequest_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_98c8d9d0");
                    }
                    utils.setScriptVar(player, "conversation.wookiee_sidequest.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.wookiee_sidequest.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_ce7c2b5d"))
        {
            if (wookiee_sidequest_condition__defaultCondition(player, npc))
            {
                wookiee_sidequest_action_animAccuse(player, npc);
                string_id message = new string_id(c_stringFile, "s_62be250f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (wookiee_sidequest_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (wookiee_sidequest_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b674edb2");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b9bff63b");
                    }
                    utils.setScriptVar(player, "conversation.wookiee_sidequest.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.wookiee_sidequest.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_f24156ae"))
        {
            if (wookiee_sidequest_condition__defaultCondition(player, npc))
            {
                wookiee_sidequest_action_animSigh(player, npc);
                string_id message = new string_id(c_stringFile, "s_1bd5f568");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (wookiee_sidequest_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (wookiee_sidequest_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_de93f602");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_dc3b96b5");
                    }
                    utils.setScriptVar(player, "conversation.wookiee_sidequest.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.wookiee_sidequest.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_f83c0824"))
        {
            if (wookiee_sidequest_condition__defaultCondition(player, npc))
            {
                wookiee_sidequest_action_animSigh(player, npc);
                string_id message = new string_id(c_stringFile, "s_c9c583c7");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (wookiee_sidequest_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (wookiee_sidequest_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b01c946e");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c089c686");
                    }
                    utils.setScriptVar(player, "conversation.wookiee_sidequest.branchId", 18);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.wookiee_sidequest.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_6420b547"))
        {
            if (wookiee_sidequest_condition__defaultCondition(player, npc))
            {
                wookiee_sidequest_action_animCelebrate(player, npc);
                string_id message = new string_id(c_stringFile, "s_19bc1717");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (wookiee_sidequest_condition_RewardChk1stMission(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (wookiee_sidequest_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (wookiee_sidequest_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (wookiee_sidequest_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3a2c3dd4");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_dfbfa15d");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_aedac34b");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a6b8e0dd");
                    }
                    utils.setScriptVar(player, "conversation.wookiee_sidequest.branchId", 21);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.wookiee_sidequest.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_f8a39ed7"))
        {
            if (wookiee_sidequest_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3a2e34");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (wookiee_sidequest_condition_RewardChk2ndMission(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (wookiee_sidequest_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fd24ad6b");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_621bb7c1");
                    }
                    utils.setScriptVar(player, "conversation.wookiee_sidequest.branchId", 38);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.wookiee_sidequest.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int wookiee_sidequest_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_dff4891"))
        {
            if (wookiee_sidequest_condition__defaultCondition(player, npc))
            {
                wookiee_sidequest_action_animSalute(player, npc);
                string_id message = new string_id(c_stringFile, "s_5264aa09");
                utils.removeScriptVar(player, "conversation.wookiee_sidequest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int wookiee_sidequest_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_b269d401"))
        {
            if (wookiee_sidequest_condition__defaultCondition(player, npc))
            {
                wookiee_sidequest_action_animSalute(player, npc);
                string_id message = new string_id(c_stringFile, "s_8dae50e8");
                utils.removeScriptVar(player, "conversation.wookiee_sidequest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int wookiee_sidequest_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_98c8d9d0"))
        {
            if (wookiee_sidequest_condition__defaultCondition(player, npc))
            {
                wookiee_sidequest_action_animNo(player, npc);
                string_id message = new string_id(c_stringFile, "s_d6d7f320");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (wookiee_sidequest_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1e97d877");
                    }
                    utils.setScriptVar(player, "conversation.wookiee_sidequest.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.wookiee_sidequest.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int wookiee_sidequest_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1e97d877"))
        {
            if (wookiee_sidequest_condition__defaultCondition(player, npc))
            {
                wookiee_sidequest_action_animSalute(player, npc);
                string_id message = new string_id(c_stringFile, "s_7f997ce7");
                utils.removeScriptVar(player, "conversation.wookiee_sidequest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int wookiee_sidequest_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_b674edb2"))
        {
            if (wookiee_sidequest_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_220e79bb");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (wookiee_sidequest_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e2942645");
                    }
                    utils.setScriptVar(player, "conversation.wookiee_sidequest.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.wookiee_sidequest.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_b9bff63b"))
        {
            if (wookiee_sidequest_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_2a6fa56e");
                utils.removeScriptVar(player, "conversation.wookiee_sidequest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int wookiee_sidequest_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_e2942645"))
        {
            if (wookiee_sidequest_condition__defaultCondition(player, npc))
            {
                wookiee_sidequest_action_grant1stMission(player, npc);
                string_id message = new string_id(c_stringFile, "s_5c3cf25c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (wookiee_sidequest_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e6017d65");
                    }
                    utils.setScriptVar(player, "conversation.wookiee_sidequest.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.wookiee_sidequest.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int wookiee_sidequest_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_e6017d65"))
        {
            if (wookiee_sidequest_condition__defaultCondition(player, npc))
            {
                wookiee_sidequest_action_animSalute(player, npc);
                string_id message = new string_id(c_stringFile, "s_418bd43b");
                utils.removeScriptVar(player, "conversation.wookiee_sidequest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int wookiee_sidequest_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_de93f602"))
        {
            if (wookiee_sidequest_condition__defaultCondition(player, npc))
            {
                wookiee_sidequest_action_grant1stMission(player, npc);
                string_id message = new string_id(c_stringFile, "s_66c52109");
                utils.removeScriptVar(player, "conversation.wookiee_sidequest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_dc3b96b5"))
        {
            if (wookiee_sidequest_condition__defaultCondition(player, npc))
            {
                wookiee_sidequest_action_animNod(player, npc);
                string_id message = new string_id(c_stringFile, "s_37ce251c");
                utils.removeScriptVar(player, "conversation.wookiee_sidequest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int wookiee_sidequest_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_b01c946e"))
        {
            if (wookiee_sidequest_condition__defaultCondition(player, npc))
            {
                wookiee_sidequest_action_grant2ndMission(player, npc);
                string_id message = new string_id(c_stringFile, "s_e5e0f00d");
                utils.removeScriptVar(player, "conversation.wookiee_sidequest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_c089c686"))
        {
            if (wookiee_sidequest_condition__defaultCondition(player, npc))
            {
                wookiee_sidequest_action_animSigh(player, npc);
                string_id message = new string_id(c_stringFile, "s_5a73125d");
                utils.removeScriptVar(player, "conversation.wookiee_sidequest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int wookiee_sidequest_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3a2c3dd4"))
        {
            if (wookiee_sidequest_condition__defaultCondition(player, npc))
            {
                wookiee_sidequest_action_grantRewardMission1(player, npc);
                string_id message = new string_id(c_stringFile, "s_e5d57a18");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (wookiee_sidequest_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (wookiee_sidequest_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_dd1be097");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_915d2cbf");
                    }
                    utils.setScriptVar(player, "conversation.wookiee_sidequest.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.wookiee_sidequest.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_dfbfa15d"))
        {
            if (wookiee_sidequest_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_e88fac47");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (wookiee_sidequest_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (wookiee_sidequest_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_54");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_58");
                    }
                    utils.setScriptVar(player, "conversation.wookiee_sidequest.branchId", 27);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.wookiee_sidequest.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_aedac34b"))
        {
            if (wookiee_sidequest_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1fefaaf");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (wookiee_sidequest_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f6c0338f");
                    }
                    utils.setScriptVar(player, "conversation.wookiee_sidequest.branchId", 30);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.wookiee_sidequest.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_a6b8e0dd"))
        {
            if (wookiee_sidequest_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_73");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (wookiee_sidequest_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (wookiee_sidequest_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4273039a");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8a54c987");
                    }
                    utils.setScriptVar(player, "conversation.wookiee_sidequest.branchId", 35);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.wookiee_sidequest.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int wookiee_sidequest_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_dd1be097"))
        {
            if (wookiee_sidequest_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_bcd42bbd");
                utils.removeScriptVar(player, "conversation.wookiee_sidequest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_915d2cbf"))
        {
            if (wookiee_sidequest_condition__defaultCondition(player, npc))
            {
                wookiee_sidequest_action_animExplain(player, npc);
                string_id message = new string_id(c_stringFile, "s_26343911");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (wookiee_sidequest_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (wookiee_sidequest_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8dac9978");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b20f0149");
                    }
                    utils.setScriptVar(player, "conversation.wookiee_sidequest.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.wookiee_sidequest.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int wookiee_sidequest_handleBranch24(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_8dac9978"))
        {
            if (wookiee_sidequest_condition__defaultCondition(player, npc))
            {
                wookiee_sidequest_action_grant2ndMission(player, npc);
                string_id message = new string_id(c_stringFile, "s_e34cea4e");
                utils.removeScriptVar(player, "conversation.wookiee_sidequest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_b20f0149"))
        {
            if (wookiee_sidequest_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_a5415008");
                utils.removeScriptVar(player, "conversation.wookiee_sidequest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int wookiee_sidequest_handleBranch27(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_54"))
        {
            if (wookiee_sidequest_condition__defaultCondition(player, npc))
            {
                wookiee_sidequest_action_grant2ndMission(player, npc);
                string_id message = new string_id(c_stringFile, "s_56");
                utils.removeScriptVar(player, "conversation.wookiee_sidequest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_58"))
        {
            if (wookiee_sidequest_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_60");
                utils.removeScriptVar(player, "conversation.wookiee_sidequest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int wookiee_sidequest_handleBranch30(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_f6c0338f"))
        {
            if (wookiee_sidequest_condition__defaultCondition(player, npc))
            {
                wookiee_sidequest_action_grant2ndMission(player, npc);
                string_id message = new string_id(c_stringFile, "s_b6b02065");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (wookiee_sidequest_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ee27bc0d");
                    }
                    utils.setScriptVar(player, "conversation.wookiee_sidequest.branchId", 31);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.wookiee_sidequest.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int wookiee_sidequest_handleBranch31(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_ee27bc0d"))
        {
            if (wookiee_sidequest_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_ac48a861");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (wookiee_sidequest_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (wookiee_sidequest_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fbb5fd3a");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f584a39");
                    }
                    utils.setScriptVar(player, "conversation.wookiee_sidequest.branchId", 32);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.wookiee_sidequest.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int wookiee_sidequest_handleBranch32(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_fbb5fd3a"))
        {
            if (wookiee_sidequest_condition__defaultCondition(player, npc))
            {
                wookiee_sidequest_action_grant2ndMission(player, npc);
                string_id message = new string_id(c_stringFile, "s_2934a8b2");
                utils.removeScriptVar(player, "conversation.wookiee_sidequest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_f584a39"))
        {
            if (wookiee_sidequest_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_a8f54dbe");
                utils.removeScriptVar(player, "conversation.wookiee_sidequest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int wookiee_sidequest_handleBranch35(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_4273039a"))
        {
            if (wookiee_sidequest_condition__defaultCondition(player, npc))
            {
                wookiee_sidequest_action_grant2ndMission(player, npc);
                string_id message = new string_id(c_stringFile, "s_134245b0");
                utils.removeScriptVar(player, "conversation.wookiee_sidequest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_8a54c987"))
        {
            if (wookiee_sidequest_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_5f70e035");
                utils.removeScriptVar(player, "conversation.wookiee_sidequest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int wookiee_sidequest_handleBranch38(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_fd24ad6b"))
        {
            if (wookiee_sidequest_condition__defaultCondition(player, npc))
            {
                wookiee_sidequest_action_grantRewardMission2(player, npc);
                string_id message = new string_id(c_stringFile, "s_474df52d");
                utils.removeScriptVar(player, "conversation.wookiee_sidequest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_621bb7c1"))
        {
            if (wookiee_sidequest_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_226ffd3d");
                utils.removeScriptVar(player, "conversation.wookiee_sidequest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int wookiee_sidequest_handleBranch41(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_a1689abe"))
        {
            if (wookiee_sidequest_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1a7fb36b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (wookiee_sidequest_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ef2d67f1");
                    }
                    utils.setScriptVar(player, "conversation.wookiee_sidequest.branchId", 42);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.wookiee_sidequest.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_8462e1e2"))
        {
            if (wookiee_sidequest_condition__defaultCondition(player, npc))
            {
                wookiee_sidequest_action_animFist(player, npc);
                string_id message = new string_id(c_stringFile, "s_a74d5cf4");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (wookiee_sidequest_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (wookiee_sidequest_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_40a86cc7");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e8d13d8a");
                    }
                    utils.setScriptVar(player, "conversation.wookiee_sidequest.branchId", 46);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.wookiee_sidequest.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_119"))
        {
            if (wookiee_sidequest_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_dd0ad352");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (wookiee_sidequest_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_122");
                    }
                    utils.setScriptVar(player, "conversation.wookiee_sidequest.branchId", 55);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.wookiee_sidequest.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int wookiee_sidequest_handleBranch42(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_ef2d67f1"))
        {
            if (wookiee_sidequest_condition__defaultCondition(player, npc))
            {
                wookiee_sidequest_action_animBow(player, npc);
                string_id message = new string_id(c_stringFile, "s_d4017dcd");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (wookiee_sidequest_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (wookiee_sidequest_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5ca1856a");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9bcffeac");
                    }
                    utils.setScriptVar(player, "conversation.wookiee_sidequest.branchId", 43);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.wookiee_sidequest.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int wookiee_sidequest_handleBranch43(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_5ca1856a"))
        {
            if (wookiee_sidequest_condition__defaultCondition(player, npc))
            {
                wookiee_sidequest_action_grant1stMission(player, npc);
                string_id message = new string_id(c_stringFile, "s_91");
                utils.removeScriptVar(player, "conversation.wookiee_sidequest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_9bcffeac"))
        {
            if (wookiee_sidequest_condition__defaultCondition(player, npc))
            {
                wookiee_sidequest_action_setRememberSigh(player, npc);
                string_id message = new string_id(c_stringFile, "s_24e34e5a");
                utils.removeScriptVar(player, "conversation.wookiee_sidequest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int wookiee_sidequest_handleBranch46(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_40a86cc7"))
        {
            if (wookiee_sidequest_condition__defaultCondition(player, npc))
            {
                wookiee_sidequest_action_setRememberDisgust(player, npc);
                string_id message = new string_id(c_stringFile, "s_e3ae04b5");
                utils.removeScriptVar(player, "conversation.wookiee_sidequest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_e8d13d8a"))
        {
            if (wookiee_sidequest_condition__defaultCondition(player, npc))
            {
                wookiee_sidequest_action_animPointAway(player, npc);
                string_id message = new string_id(c_stringFile, "s_ba31d7f4");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (wookiee_sidequest_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_101");
                    }
                    utils.setScriptVar(player, "conversation.wookiee_sidequest.branchId", 48);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.wookiee_sidequest.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int wookiee_sidequest_handleBranch48(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_101"))
        {
            if (wookiee_sidequest_condition__defaultCondition(player, npc))
            {
                wookiee_sidequest_action_animExplain(player, npc);
                string_id message = new string_id(c_stringFile, "s_103");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (wookiee_sidequest_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (wookiee_sidequest_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (wookiee_sidequest_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d1b602c1");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ac9e5091");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_115");
                    }
                    utils.setScriptVar(player, "conversation.wookiee_sidequest.branchId", 49);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.wookiee_sidequest.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int wookiee_sidequest_handleBranch49(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_d1b602c1"))
        {
            if (wookiee_sidequest_condition__defaultCondition(player, npc))
            {
                wookiee_sidequest_action_grant1stMission(player, npc);
                string_id message = new string_id(c_stringFile, "s_da183c96");
                utils.removeScriptVar(player, "conversation.wookiee_sidequest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_ac9e5091"))
        {
            if (wookiee_sidequest_condition__defaultCondition(player, npc))
            {
                wookiee_sidequest_action_animFist(player, npc);
                string_id message = new string_id(c_stringFile, "s_7164f5e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (wookiee_sidequest_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (wookiee_sidequest_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_109");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e305d81d");
                    }
                    utils.setScriptVar(player, "conversation.wookiee_sidequest.branchId", 51);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.wookiee_sidequest.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_115"))
        {
            if (wookiee_sidequest_condition__defaultCondition(player, npc))
            {
                wookiee_sidequest_action_animDisgust(player, npc);
                string_id message = new string_id(c_stringFile, "s_117");
                utils.removeScriptVar(player, "conversation.wookiee_sidequest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int wookiee_sidequest_handleBranch51(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_109"))
        {
            if (wookiee_sidequest_condition__defaultCondition(player, npc))
            {
                wookiee_sidequest_action_grant1stMission(player, npc);
                string_id message = new string_id(c_stringFile, "s_111");
                utils.removeScriptVar(player, "conversation.wookiee_sidequest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_e305d81d"))
        {
            if (wookiee_sidequest_condition__defaultCondition(player, npc))
            {
                wookiee_sidequest_action_animDisgust(player, npc);
                string_id message = new string_id(c_stringFile, "s_2aa249ad");
                utils.removeScriptVar(player, "conversation.wookiee_sidequest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int wookiee_sidequest_handleBranch55(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_122"))
        {
            if (wookiee_sidequest_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_124");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (wookiee_sidequest_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (wookiee_sidequest_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7c2995a9");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_69e4801e");
                    }
                    utils.setScriptVar(player, "conversation.wookiee_sidequest.branchId", 56);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.wookiee_sidequest.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int wookiee_sidequest_handleBranch56(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_7c2995a9"))
        {
            if (wookiee_sidequest_condition__defaultCondition(player, npc))
            {
                wookiee_sidequest_action_grant1stMission(player, npc);
                string_id message = new string_id(c_stringFile, "s_550c3e6c");
                utils.removeScriptVar(player, "conversation.wookiee_sidequest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_69e4801e"))
        {
            if (wookiee_sidequest_condition__defaultCondition(player, npc))
            {
                wookiee_sidequest_action_setRememberSigh(player, npc);
                string_id message = new string_id(c_stringFile, "s_65c23663");
                utils.removeScriptVar(player, "conversation.wookiee_sidequest.branchId");
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
            detachScript(self, "conversation.wookiee_sidequest");
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
        setCondition(self, CONDITION_CONVERSABLE);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        detachScript(self, "conversation.wookiee_sidequest");
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
        if (wookiee_sidequest_condition_CompletedSeries(player, npc))
        {
            wookiee_sidequest_action_animSalute(player, npc);
            string_id message = new string_id(c_stringFile, "s_c65fb1ba");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (wookiee_sidequest_condition_RememberObj(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_e68cd354");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (wookiee_sidequest_condition_ChkMissions(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (wookiee_sidequest_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (wookiee_sidequest_condition_refusedQuest(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (wookiee_sidequest_condition_failed1st(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse3 = true;
            }
            boolean hasResponse4 = false;
            if (wookiee_sidequest_condition_failed2nd(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse4 = true;
            }
            boolean hasResponse5 = false;
            if (wookiee_sidequest_condition_Won1st(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse5 = true;
            }
            boolean hasResponse6 = false;
            if (wookiee_sidequest_condition_Won2nd(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse6 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_361ea065");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_7c2446cd");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_ce7c2b5d");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_f24156ae");
                }
                if (hasResponse4)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_f83c0824");
                }
                if (hasResponse5)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_6420b547");
                }
                if (hasResponse6)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_f8a39ed7");
                }
                utils.setScriptVar(player, "conversation.wookiee_sidequest.branchId", 2);
                npcStartConversation(player, npc, "wookiee_sidequest", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (wookiee_sidequest_condition_WkeCheck(player, npc))
        {
            wookiee_sidequest_action_animConverse(player, npc);
            string_id message = new string_id(c_stringFile, "s_77603222");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (wookiee_sidequest_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (wookiee_sidequest_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (wookiee_sidequest_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_a1689abe");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_8462e1e2");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_119");
                }
                utils.setScriptVar(player, "conversation.wookiee_sidequest.branchId", 41);
                npcStartConversation(player, npc, "wookiee_sidequest", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (wookiee_sidequest_condition_NotPilotChk(player, npc))
        {
            wookiee_sidequest_action_animBow(player, npc);
            string_id message = new string_id(c_stringFile, "s_6c34509e");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (wookiee_sidequest_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_c8950314");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("wookiee_sidequest"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.wookiee_sidequest.branchId");
        if (branchId == 2 && wookiee_sidequest_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && wookiee_sidequest_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && wookiee_sidequest_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && wookiee_sidequest_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && wookiee_sidequest_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && wookiee_sidequest_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && wookiee_sidequest_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && wookiee_sidequest_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && wookiee_sidequest_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && wookiee_sidequest_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && wookiee_sidequest_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && wookiee_sidequest_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && wookiee_sidequest_handleBranch24(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 27 && wookiee_sidequest_handleBranch27(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 30 && wookiee_sidequest_handleBranch30(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 31 && wookiee_sidequest_handleBranch31(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 32 && wookiee_sidequest_handleBranch32(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 35 && wookiee_sidequest_handleBranch35(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 38 && wookiee_sidequest_handleBranch38(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 41 && wookiee_sidequest_handleBranch41(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 42 && wookiee_sidequest_handleBranch42(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 43 && wookiee_sidequest_handleBranch43(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 46 && wookiee_sidequest_handleBranch46(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 48 && wookiee_sidequest_handleBranch48(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 49 && wookiee_sidequest_handleBranch49(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 51 && wookiee_sidequest_handleBranch51(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 55 && wookiee_sidequest_handleBranch55(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 56 && wookiee_sidequest_handleBranch56(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.wookiee_sidequest.branchId");
        return SCRIPT_CONTINUE;
    }
}
