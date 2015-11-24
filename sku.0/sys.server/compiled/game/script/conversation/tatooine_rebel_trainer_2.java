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
import script.library.space_quest;
import script.library.space_skill;

public class tatooine_rebel_trainer_2 extends script.base_script
{
    public tatooine_rebel_trainer_2()
    {
    }
    public static String c_stringFile = "conversation/tatooine_rebel_trainer_2";
    public boolean tatooine_rebel_trainer_2_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean tatooine_rebel_trainer_2_condition_hasRebelSkill(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "pilot_rebel_navy_novice"));
    }
    public boolean tatooine_rebel_trainer_2_condition_hasImperialSkill(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        return (hasSkill(player, "pilot_imperial_navy_novice"));
    }
    public boolean tatooine_rebel_trainer_2_condition_hasPilotSkill(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        return (hasSkill(player, "pilot_neutral_novice"));
    }
    public boolean tatooine_rebel_trainer_2_condition_hasNoSkill(obj_id player, obj_id npc) throws InterruptedException
    {
        return (!space_skill.hasSpaceSkills(player));
    }
    public boolean tatooine_rebel_trainer_2_condition_hasRebelQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        if (!tatooine_rebel_trainer_2_condition_hasRebelSkill(player, npc))
        {
            return false;
        }
        return (space_quest.hasQuest(player));
    }
    public boolean tatooine_rebel_trainer_2_condition_hasWonFirstQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        return (space_quest.hasWonQuest(player, "destroy", "rebel_destroy_03"));
    }
    public boolean tatooine_rebel_trainer_2_condition_hasWonSecondQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasWonQuest(player, "destroy", "rebel_destroy_01"));
    }
    public boolean tatooine_rebel_trainer_2_condition_hasWonThirdQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasWonQuest(player, "patrol", "rebel_patrol_02"));
    }
    public boolean tatooine_rebel_trainer_2_condition_hasFinishedNewbieSeries(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        return (space_quest.hasWonQuest(player, "destroy", "rebel_destroy_02"));
    }
    public boolean tatooine_rebel_trainer_2_condition_hasStarShips01(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "pilot_rebel_navy_starships_01"));
    }
    public boolean tatooine_rebel_trainer_2_condition_hasNavigation01(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "pilot_rebel_navy_navigation_01"));
    }
    public boolean tatooine_rebel_trainer_2_condition_hasCombat01(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "pilot_rebel_navy_combat_01"));
    }
    public boolean tatooine_rebel_trainer_2_condition_hasDroid01(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "pilot_rebel_navy_droid_01"));
    }
    public boolean tatooine_rebel_trainer_2_condition_hasAllTier1Skills(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasSkill(player, "pilot_rebel_navy_navigation_01") && hasSkill(player, "pilot_rebel_navy_starships_01") && hasSkill(player, "pilot_rebel_navy_combat_01") && hasSkill(player, "pilot_rebel_navy_droid_01"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean tatooine_rebel_trainer_2_condition_hasWonAllQuests(obj_id player, obj_id npc) throws InterruptedException
    {
        if (tatooine_rebel_trainer_2_condition_hasWonFirstQuest(player, npc) && tatooine_rebel_trainer_2_condition_hasWonSecondQuest(player, npc) && tatooine_rebel_trainer_2_condition_hasWonThirdQuest(player, npc) && tatooine_rebel_trainer_2_condition_hasFinishedNewbieSeries(player, npc))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean tatooine_rebel_trainer_2_condition_readyForPromotion(obj_id player, obj_id npc) throws InterruptedException
    {
        if (tatooine_rebel_trainer_2_condition_hasWonAllQuests(player, npc) && (!tatooine_rebel_trainer_2_condition_hasAnyTier1Skills(player, npc)) && tatooine_rebel_trainer_2_condition_hasRebelSkill(player, npc))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean tatooine_rebel_trainer_2_condition_hasAnyTier1Skills(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasSkill(player, "pilot_rebel_navy_navigation_01") || hasSkill(player, "pilot_rebel_navy_starships_01") || hasSkill(player, "pilot_rebel_navy_combat_01") || hasSkill(player, "pilot_rebel_navy_droid_01"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean tatooine_rebel_trainer_2_condition_hasFailedFirstQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasFailedQuest(player, "destroy", "rebel_destroy_03"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean tatooine_rebel_trainer_2_condition_hasAbortedFirstQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        if (space_quest.hasAbortedQuest(player, "destroy", "rebel_destroy_03"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean tatooine_rebel_trainer_2_condition_hasFirstQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasQuest(player, "destroy", "rebel_destroy_03") || space_quest.hasCompletedQuest(player, "destroy", "rebel_destroy_03"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean tatooine_rebel_trainer_2_condition_hasSecondQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasQuest(player, "escort", "rebel_escort_01") || space_quest.hasCompletedQuest(player, "escort", "rebel_escort_01"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public void tatooine_rebel_trainer_2_action_grantRebelSkill(obj_id player, obj_id npc) throws InterruptedException
    {
        grantSkill(player, "pilot_rebel_navy_novice");
    }
    public void tatooine_rebel_trainer_2_action_grantFirstQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "pound_fist_palm");
        space_quest.grantQuest(player, "destroy", "rebel_destroy_03");
    }
    public void tatooine_rebel_trainer_2_action_grantSecondQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "explain");
        space_quest.grantQuest(player, "escort", "rebel_escort_01");
    }
    public void tatooine_rebel_trainer_2_action_grantThirdQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "explain");
        space_quest.grantQuest(player, "patrol", "rebel_patrol_02");
    }
    public void tatooine_rebel_trainer_2_action_grantFourthQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "snap_finger2");
        space_quest.grantQuest(player, "destroy", "rebel_destroy_02");
    }
    public void tatooine_rebel_trainer_2_action_grantStarships01(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "point_to_self");
        grantSkill(player, "pilot_rebel_navy_starships_01");
    }
    public void tatooine_rebel_trainer_2_action_grantNavigation01(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "point_to_self");
        grantSkill(player, "pilot_rebel_navy_navigation_01");
    }
    public void tatooine_rebel_trainer_2_action_grantCombat01(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "point_to_self");
        grantSkill(player, "pilot_rebel_navy_combat_01");
    }
    public void tatooine_rebel_trainer_2_action_grantDroid01(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "point_to_self");
        grantSkill(player, "pilot_rebel_navy_droid_01");
    }
    public void tatooine_rebel_trainer_2_action_animPointAway(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "point_away");
    }
    public void tatooine_rebel_trainer_2_action_animSalute1(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "salute1");
    }
    public void tatooine_rebel_trainer_2_action_animSlowDown(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "slow_down");
    }
    public void tatooine_rebel_trainer_2_action_animPointToSelf(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "point_to_self");
    }
    public void tatooine_rebel_trainer_2_action_animPointForward(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "point_forward");
    }
    public void tatooine_rebel_trainer_2_action_animSaluteEachOther(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "salute2");
        doAnimationAction(player, "salute2");
    }
    public void tatooine_rebel_trainer_2_action_animPoundFist(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        doAnimationAction(npc, "pound_fist_palm");
    }
    public void tatooine_rebel_trainer_2_action_animScratchHead(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "scratch_head");
    }
    public void tatooine_rebel_trainer_2_action_animStop(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "stop");
    }
    public void tatooine_rebel_trainer_2_action_animDismiss(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "dismiss");
    }
    public void tatooine_rebel_trainer_2_action_animAccuse(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "point_accusingly");
    }
    public void tatooine_rebel_trainer_2_action_animBye(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "goodbye");
    }
    public void tatooine_rebel_trainer_2_action_animNo(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "shake_head_no");
    }
    public void tatooine_rebel_trainer_2_action_animDisgust(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "shake_head_disgust");
    }
    public void tatooine_rebel_trainer_2_action_animConverse(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "conversation_1");
    }
    public void tatooine_rebel_trainer_2_action_animSlitThroat(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        doAnimationAction(npc, "slit_throat");
    }
    public void tatooine_rebel_trainer_2_action_animShrugHands(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        doAnimationAction(npc, "shrug_hands");
    }
    public void tatooine_rebel_trainer_2_action_animGestureWild(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "gesticulate_wildly");
    }
    public void tatooine_rebel_trainer_2_action_animWarn(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "wave_finger_warning");
    }
    public void tatooine_rebel_trainer_2_action_animTaunt(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "taunt2");
    }
    public void tatooine_rebel_trainer_2_action_animExplain(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "explain");
    }
    public void tatooine_rebel_trainer_2_action_animSigh(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "sigh_deeply");
    }
    public void tatooine_rebel_trainer_2_action_animNodHead(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "nod_head_once");
    }
    public void tatooine_rebel_trainer_2_action_actionSit(obj_id player, obj_id npc) throws InterruptedException
    {
        ai_lib.setDefaultCalmMood(npc, "npc_sitting_chair");
    }
    public void tatooine_rebel_trainer_2_action_rewardFirstQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasReceivedReward(player, "destroy", "rebel_destroy_03"))
        {
            space_quest.setQuestRewarded(player, "destroy", "rebel_destroy_03");
            factions.addFactionStanding(player, factions.FACTION_REBEL, 75.0f);
        }
    }
    public int tatooine_rebel_trainer_2_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_65c6992e"))
        {
            tatooine_rebel_trainer_2_action_grantFirstQuest(player, npc);
            if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                tatooine_rebel_trainer_2_action_animNodHead(player, npc);
                string_id message = new string_id(c_stringFile, "s_7f5b6f74");
                removeObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_trainer_2_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_65c6992e"))
        {
            tatooine_rebel_trainer_2_action_grantFirstQuest(player, npc);
            if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                tatooine_rebel_trainer_2_action_animNodHead(player, npc);
                string_id message = new string_id(c_stringFile, "s_7f5b6f74");
                removeObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_trainer_2_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_9ac87532"))
        {
            if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                tatooine_rebel_trainer_2_action_animPointAway(player, npc);
                string_id message = new string_id(c_stringFile, "s_14bb212a");
                removeObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_c49dadaf"))
        {
            if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                tatooine_rebel_trainer_2_action_animPoundFist(player, npc);
                string_id message = new string_id(c_stringFile, "s_b65b63e");
                removeObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_trainer_2_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6c251948"))
        {
            if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                tatooine_rebel_trainer_2_action_grantSecondQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_b76bbf5f");
                removeObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_trainer_2_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_9ac87532"))
        {
            if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                tatooine_rebel_trainer_2_action_animPointAway(player, npc);
                string_id message = new string_id(c_stringFile, "s_14bb212a");
                removeObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_fc01f22f"))
        {
            if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                tatooine_rebel_trainer_2_action_rewardFirstQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_9ccfeefa");
                removeObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_32583e8e"))
        {
            if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                tatooine_rebel_trainer_2_action_animWarn(player, npc);
                string_id message = new string_id(c_stringFile, "s_30e82ed");
                removeObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_trainer_2_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_65c6992e"))
        {
            if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                tatooine_rebel_trainer_2_action_animPoundFist(player, npc);
                string_id message = new string_id(c_stringFile, "s_95ca3dcc");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c999ae60");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4021e802");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8d90443c");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ce77bdfc");
                    }
                    setObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_9633bc98"))
        {
            if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                tatooine_rebel_trainer_2_action_animGestureWild(player, npc);
                string_id message = new string_id(c_stringFile, "s_803bce3f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_76c732ab");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4e1e1511");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_704ffa17");
                    }
                    setObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_3a8e238f"))
        {
            if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                tatooine_rebel_trainer_2_action_animTaunt(player, npc);
                string_id message = new string_id(c_stringFile, "s_add38b4");
                removeObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_trainer_2_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c999ae60"))
        {
            if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                tatooine_rebel_trainer_2_action_grantFirstQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_32761c80");
                removeObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_4021e802"))
        {
            if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                tatooine_rebel_trainer_2_action_animShrugHands(player, npc);
                string_id message = new string_id(c_stringFile, "s_e3034e87");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c999ae60");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4021e802");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8d90443c");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ce77bdfc");
                    }
                    setObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_8d90443c"))
        {
            if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                tatooine_rebel_trainer_2_action_animScratchHead(player, npc);
                string_id message = new string_id(c_stringFile, "s_bf46ea6c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c999ae60");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4021e802");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8d90443c");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ce77bdfc");
                    }
                    setObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_ce77bdfc"))
        {
            if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                tatooine_rebel_trainer_2_action_animStop(player, npc);
                string_id message = new string_id(c_stringFile, "s_24fcd0f1");
                removeObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_trainer_2_handleBranch19(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c999ae60"))
        {
            if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                tatooine_rebel_trainer_2_action_grantFirstQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_32761c80");
                removeObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_4021e802"))
        {
            if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                tatooine_rebel_trainer_2_action_animShrugHands(player, npc);
                string_id message = new string_id(c_stringFile, "s_e3034e87");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c999ae60");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4021e802");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8d90443c");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ce77bdfc");
                    }
                    setObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_8d90443c"))
        {
            if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                tatooine_rebel_trainer_2_action_animScratchHead(player, npc);
                string_id message = new string_id(c_stringFile, "s_bf46ea6c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c999ae60");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4021e802");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8d90443c");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ce77bdfc");
                    }
                    setObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_ce77bdfc"))
        {
            if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                tatooine_rebel_trainer_2_action_animStop(player, npc);
                string_id message = new string_id(c_stringFile, "s_24fcd0f1");
                removeObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_trainer_2_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c999ae60"))
        {
            if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                tatooine_rebel_trainer_2_action_grantFirstQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_32761c80");
                removeObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_4021e802"))
        {
            if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                tatooine_rebel_trainer_2_action_animShrugHands(player, npc);
                string_id message = new string_id(c_stringFile, "s_e3034e87");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c999ae60");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4021e802");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8d90443c");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ce77bdfc");
                    }
                    setObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_8d90443c"))
        {
            if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                tatooine_rebel_trainer_2_action_animScratchHead(player, npc);
                string_id message = new string_id(c_stringFile, "s_bf46ea6c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c999ae60");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4021e802");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8d90443c");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ce77bdfc");
                    }
                    setObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_ce77bdfc"))
        {
            if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                tatooine_rebel_trainer_2_action_animStop(player, npc);
                string_id message = new string_id(c_stringFile, "s_24fcd0f1");
                removeObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_trainer_2_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_76c732ab"))
        {
            if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                tatooine_rebel_trainer_2_action_animPoundFist(player, npc);
                string_id message = new string_id(c_stringFile, "s_95ca3dcc");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c999ae60");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4021e802");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8d90443c");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9d7e5a09");
                    }
                    setObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_4e1e1511"))
        {
            if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                tatooine_rebel_trainer_2_action_animSlowDown(player, npc);
                string_id message = new string_id(c_stringFile, "s_b2f0eabb");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c999ae60");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4021e802");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8d90443c");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9d7e5a09");
                    }
                    setObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_704ffa17"))
        {
            if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                tatooine_rebel_trainer_2_action_animExplain(player, npc);
                string_id message = new string_id(c_stringFile, "s_adb01382");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_71fdec41");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_eeed5bcd");
                    }
                    setObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId", 29);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_trainer_2_handleBranch23(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c999ae60"))
        {
            if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                tatooine_rebel_trainer_2_action_grantFirstQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_32761c80");
                removeObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_4021e802"))
        {
            if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                tatooine_rebel_trainer_2_action_animShrugHands(player, npc);
                string_id message = new string_id(c_stringFile, "s_e3034e87");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c999ae60");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4021e802");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8d90443c");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9d7e5a09");
                    }
                    setObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_8d90443c"))
        {
            if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                tatooine_rebel_trainer_2_action_animScratchHead(player, npc);
                string_id message = new string_id(c_stringFile, "s_bf46ea6c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c999ae60");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4021e802");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8d90443c");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9d7e5a09");
                    }
                    setObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_9d7e5a09"))
        {
            if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                tatooine_rebel_trainer_2_action_animStop(player, npc);
                string_id message = new string_id(c_stringFile, "s_c1cb6e44");
                removeObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_trainer_2_handleBranch25(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c999ae60"))
        {
            if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                tatooine_rebel_trainer_2_action_grantFirstQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_32761c80");
                removeObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_4021e802"))
        {
            if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                tatooine_rebel_trainer_2_action_animShrugHands(player, npc);
                string_id message = new string_id(c_stringFile, "s_e3034e87");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c999ae60");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4021e802");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8d90443c");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9d7e5a09");
                    }
                    setObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_8d90443c"))
        {
            if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                tatooine_rebel_trainer_2_action_animScratchHead(player, npc);
                string_id message = new string_id(c_stringFile, "s_bf46ea6c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c999ae60");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4021e802");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8d90443c");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9d7e5a09");
                    }
                    setObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_9d7e5a09"))
        {
            if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                tatooine_rebel_trainer_2_action_animStop(player, npc);
                string_id message = new string_id(c_stringFile, "s_c1cb6e44");
                removeObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_trainer_2_handleBranch26(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c999ae60"))
        {
            if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                tatooine_rebel_trainer_2_action_grantFirstQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_32761c80");
                removeObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_4021e802"))
        {
            if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                tatooine_rebel_trainer_2_action_animShrugHands(player, npc);
                string_id message = new string_id(c_stringFile, "s_e3034e87");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c999ae60");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4021e802");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8d90443c");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9d7e5a09");
                    }
                    setObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_8d90443c"))
        {
            if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                tatooine_rebel_trainer_2_action_animScratchHead(player, npc);
                string_id message = new string_id(c_stringFile, "s_bf46ea6c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c999ae60");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4021e802");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8d90443c");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9d7e5a09");
                    }
                    setObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_9d7e5a09"))
        {
            if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                tatooine_rebel_trainer_2_action_animStop(player, npc);
                string_id message = new string_id(c_stringFile, "s_c1cb6e44");
                removeObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_trainer_2_handleBranch28(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c999ae60"))
        {
            if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                tatooine_rebel_trainer_2_action_grantFirstQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_32761c80");
                removeObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_4021e802"))
        {
            if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                tatooine_rebel_trainer_2_action_animShrugHands(player, npc);
                string_id message = new string_id(c_stringFile, "s_e3034e87");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c999ae60");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4021e802");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8d90443c");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9d7e5a09");
                    }
                    setObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_8d90443c"))
        {
            if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                tatooine_rebel_trainer_2_action_animScratchHead(player, npc);
                string_id message = new string_id(c_stringFile, "s_bf46ea6c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c999ae60");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4021e802");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8d90443c");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9d7e5a09");
                    }
                    setObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_9d7e5a09"))
        {
            if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                tatooine_rebel_trainer_2_action_animStop(player, npc);
                string_id message = new string_id(c_stringFile, "s_c1cb6e44");
                removeObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_trainer_2_handleBranch29(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_71fdec41"))
        {
            if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                tatooine_rebel_trainer_2_action_animScratchHead(player, npc);
                string_id message = new string_id(c_stringFile, "s_49580a34");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c999ae60");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4021e802");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8d90443c");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9d7e5a09");
                    }
                    setObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_eeed5bcd"))
        {
            if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                tatooine_rebel_trainer_2_action_animExplain(player, npc);
                string_id message = new string_id(c_stringFile, "s_9d805e09");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c999ae60");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4021e802");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8d90443c");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9d7e5a09");
                    }
                    setObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_trainer_2_handleBranch30(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c999ae60"))
        {
            if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                tatooine_rebel_trainer_2_action_grantFirstQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_32761c80");
                removeObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_4021e802"))
        {
            if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                tatooine_rebel_trainer_2_action_animShrugHands(player, npc);
                string_id message = new string_id(c_stringFile, "s_e3034e87");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c999ae60");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4021e802");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8d90443c");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9d7e5a09");
                    }
                    setObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_8d90443c"))
        {
            if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                tatooine_rebel_trainer_2_action_animScratchHead(player, npc);
                string_id message = new string_id(c_stringFile, "s_bf46ea6c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c999ae60");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4021e802");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8d90443c");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9d7e5a09");
                    }
                    setObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_9d7e5a09"))
        {
            if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                tatooine_rebel_trainer_2_action_animStop(player, npc);
                string_id message = new string_id(c_stringFile, "s_c1cb6e44");
                removeObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_trainer_2_handleBranch31(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c999ae60"))
        {
            if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                tatooine_rebel_trainer_2_action_grantFirstQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_32761c80");
                removeObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_4021e802"))
        {
            if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                tatooine_rebel_trainer_2_action_animShrugHands(player, npc);
                string_id message = new string_id(c_stringFile, "s_e3034e87");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c999ae60");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4021e802");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8d90443c");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9d7e5a09");
                    }
                    setObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_8d90443c"))
        {
            if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                tatooine_rebel_trainer_2_action_animScratchHead(player, npc);
                string_id message = new string_id(c_stringFile, "s_bf46ea6c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c999ae60");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4021e802");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8d90443c");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9d7e5a09");
                    }
                    setObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_9d7e5a09"))
        {
            if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                tatooine_rebel_trainer_2_action_animStop(player, npc);
                string_id message = new string_id(c_stringFile, "s_c1cb6e44");
                removeObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_rebel_trainer_2_handleBranch33(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1f680e83"))
        {
            if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                tatooine_rebel_trainer_2_action_animDismiss(player, npc);
                string_id message = new string_id(c_stringFile, "s_7aa2ae19");
                removeObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_57c8489e"))
        {
            if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                tatooine_rebel_trainer_2_action_animNodHead(player, npc);
                string_id message = new string_id(c_stringFile, "s_ad45bcbf");
                removeObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_ca776e30"))
        {
            if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                tatooine_rebel_trainer_2_action_animDisgust(player, npc);
                string_id message = new string_id(c_stringFile, "s_56ea3421");
                removeObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId");
                npcSpeak(player, message);
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
            detachScript(self, "conversation.tatooine_rebel_trainer_2");
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
        detachScript(self, "conversation.tatooine_rebel_trainer_2");
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
        if (tatooine_rebel_trainer_2_condition_hasImperialSkill(player, npc))
        {
            tatooine_rebel_trainer_2_action_animDismiss(player, npc);
            string_id message = new string_id(c_stringFile, "s_32c77056");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (tatooine_rebel_trainer_2_condition_hasPilotSkill(player, npc))
        {
            tatooine_rebel_trainer_2_action_animPointAway(player, npc);
            string_id message = new string_id(c_stringFile, "s_4243cd30");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (tatooine_rebel_trainer_2_condition_hasFailedFirstQuest(player, npc))
        {
            tatooine_rebel_trainer_2_action_animScratchHead(player, npc);
            string_id message = new string_id(c_stringFile, "s_8af71059");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_65c6992e");
                }
                setObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId", 3);
                npcStartConversation(player, npc, "tatooine_rebel_trainer_2", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_rebel_trainer_2_condition_hasAbortedFirstQuest(player, npc))
        {
            tatooine_rebel_trainer_2_action_animScratchHead(player, npc);
            string_id message = new string_id(c_stringFile, "s_f3a2a7d2");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_65c6992e");
                }
                setObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId", 5);
                npcStartConversation(player, npc, "tatooine_rebel_trainer_2", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_rebel_trainer_2_condition_hasSecondQuest(player, npc))
        {
            tatooine_rebel_trainer_2_action_animSaluteEachOther(player, npc);
            string_id message = new string_id(c_stringFile, "s_340e3cc");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (tatooine_rebel_trainer_2_condition_hasWonSecondQuest(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_9ac87532");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_c49dadaf");
                }
                setObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId", 7);
                npcStartConversation(player, npc, "tatooine_rebel_trainer_2", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_rebel_trainer_2_condition_hasWonFirstQuest(player, npc))
        {
            tatooine_rebel_trainer_2_action_animSalute1(player, npc);
            string_id message = new string_id(c_stringFile, "s_113f133e");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_6c251948");
                }
                setObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId", 10);
                npcStartConversation(player, npc, "tatooine_rebel_trainer_2", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_rebel_trainer_2_condition_hasFirstQuest(player, npc))
        {
            tatooine_rebel_trainer_2_action_animSaluteEachOther(player, npc);
            string_id message = new string_id(c_stringFile, "s_ef7c4c8");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (tatooine_rebel_trainer_2_condition_hasWonFirstQuest(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_9ac87532");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_fc01f22f");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_32583e8e");
                }
                setObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId", 12);
                npcStartConversation(player, npc, "tatooine_rebel_trainer_2", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_rebel_trainer_2_condition_hasFinishedNewbieSeries(player, npc))
        {
            tatooine_rebel_trainer_2_action_animSlitThroat(player, npc);
            string_id message = new string_id(c_stringFile, "s_3e8d2533");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_65c6992e");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_9633bc98");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_3a8e238f");
                }
                setObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId", 16);
                npcStartConversation(player, npc, "tatooine_rebel_trainer_2", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
        {
            tatooine_rebel_trainer_2_action_animSlowDown(player, npc);
            string_id message = new string_id(c_stringFile, "s_1d2c155d");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (tatooine_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1f680e83");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_57c8489e");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_ca776e30");
                }
                setObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId", 33);
                npcStartConversation(player, npc, "tatooine_rebel_trainer_2", message, responses);
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
        if (!conversationId.equals("tatooine_rebel_trainer_2"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = getIntObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId");
        if (branchId == 3 && tatooine_rebel_trainer_2_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && tatooine_rebel_trainer_2_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && tatooine_rebel_trainer_2_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && tatooine_rebel_trainer_2_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && tatooine_rebel_trainer_2_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && tatooine_rebel_trainer_2_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && tatooine_rebel_trainer_2_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && tatooine_rebel_trainer_2_handleBranch19(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && tatooine_rebel_trainer_2_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && tatooine_rebel_trainer_2_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && tatooine_rebel_trainer_2_handleBranch23(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && tatooine_rebel_trainer_2_handleBranch25(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && tatooine_rebel_trainer_2_handleBranch26(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 28 && tatooine_rebel_trainer_2_handleBranch28(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && tatooine_rebel_trainer_2_handleBranch29(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 30 && tatooine_rebel_trainer_2_handleBranch30(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 31 && tatooine_rebel_trainer_2_handleBranch31(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 33 && tatooine_rebel_trainer_2_handleBranch33(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.tatooine_rebel_trainer_2.branchId");
        return SCRIPT_CONTINUE;
    }
}
