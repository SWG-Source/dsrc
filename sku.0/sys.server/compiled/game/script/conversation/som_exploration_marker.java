package script.conversation;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.badge;
import script.library.chat;
import script.library.create;
import script.library.groundquests;
import script.library.static_item;
import script.library.utils;

public class som_exploration_marker extends script.base_script
{
    public som_exploration_marker()
    {
    }
    public static String c_stringFile = "conversation/som_exploration_marker";
    public boolean som_exploration_marker_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean som_exploration_marker_condition_miningFacility(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActiveOrComplete(player, "som_exploration_mining");
    }
    public boolean som_exploration_marker_condition_crystalFlats(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActiveOrComplete(player, "som_exploration_crystal");
    }
    public boolean som_exploration_marker_condition_smokingForest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActiveOrComplete(player, "som_exploration_smoking");
    }
    public boolean som_exploration_marker_condition_centralVolcano(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActiveOrComplete(player, "som_exploration_volcano");
    }
    public boolean som_exploration_marker_condition_burningPlains(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActiveOrComplete(player, "som_exploration_burning");
    }
    public boolean som_exploration_marker_condition_berkensFlow(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActiveOrComplete(player, "som_exploration_berken");
    }
    public boolean som_exploration_marker_condition_tulrusNesting(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActiveOrComplete(player, "som_exploration_tulrus");
    }
    public boolean som_exploration_marker_condition_completeAll(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "som_exploration_volcano") && groundquests.hasCompletedQuest(player, "som_exploration_burning") && groundquests.hasCompletedQuest(player, "som_exploration_berken") && groundquests.hasCompletedQuest(player, "som_exploration_tulrus") && groundquests.hasCompletedQuest(player, "som_exploration_smoking") && groundquests.hasCompletedQuest(player, "som_exploration_crystal") && groundquests.hasCompletedQuest(player, "som_exploration_mining");
    }
    public boolean som_exploration_marker_condition_completedReward(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasObjVar(player, "mustafar.tanray_heart");
    }
    public boolean som_exploration_marker_condition_hasAcceptedOne(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isQuestActiveOrComplete(player, "som_exploration_volcano") || groundquests.isQuestActiveOrComplete(player, "som_exploration_burning"))
        {
            return true;
        }
        if (groundquests.isQuestActiveOrComplete(player, "som_exploration_berken") || groundquests.isQuestActiveOrComplete(player, "som_exploration_tulrus"))
        {
            return true;
        }
        if (groundquests.isQuestActiveOrComplete(player, "som_exploration_smoking") || groundquests.isQuestActiveOrComplete(player, "som_exploration_crystal"))
        {
            return true;
        }
        if (groundquests.isQuestActiveOrComplete(player, "som_exploration_mining"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public void som_exploration_marker_action_grantCentral(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "som_exploration_volcano");
    }
    public void som_exploration_marker_action_grantBurning(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "som_exploration_burning");
    }
    public void som_exploration_marker_action_grantBerken(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "som_exploration_berken");
    }
    public void som_exploration_marker_action_grantTulrus(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "som_exploration_tulrus");
    }
    public void som_exploration_marker_action_grantSmoking(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "som_exploration_smoking");
    }
    public void som_exploration_marker_action_grantCrystal(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "som_exploration_crystal");
    }
    public void som_exploration_marker_action_grantMining(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "som_exploration_mining");
    }
    public void som_exploration_marker_action_grantReward(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id pInv = utils.getInventoryContainer(player);
        static_item.createNewItemFunction("item_tow_trophey_02_05", pInv);
        setObjVar(player, "mustafar.tanray_heart", 1);
        badge.grantBadge(player, "bdg_must_mustafar_exploration");
    }
    public int som_exploration_marker_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_41"))
        {
            som_exploration_marker_action_grantReward(player, npc);
            if (som_exploration_marker_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow4");
                string_id message = new string_id(c_stringFile, "s_42");
                utils.removeScriptVar(player, "conversation.som_exploration_marker.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_exploration_marker_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_38"))
        {
            doAnimationAction(player, "nod");
            if (som_exploration_marker_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bounce");
                string_id message = new string_id(c_stringFile, "s_40");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!som_exploration_marker_condition_miningFacility(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!som_exploration_marker_condition_crystalFlats(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!som_exploration_marker_condition_smokingForest(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (!som_exploration_marker_condition_centralVolcano(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (!som_exploration_marker_condition_burningPlains(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (!som_exploration_marker_condition_berkensFlow(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                boolean hasResponse6 = false;
                if (!som_exploration_marker_condition_tulrusNesting(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_46");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_50");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_54");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_58");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_62");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_66");
                    }
                    if (hasResponse6)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_70");
                    }
                    utils.setScriptVar(player, "conversation.som_exploration_marker.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_exploration_marker.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_39"))
        {
            doAnimationAction(player, "shake_head_no");
            if (som_exploration_marker_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_78");
                utils.removeScriptVar(player, "conversation.som_exploration_marker.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_exploration_marker_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_46"))
        {
            if (som_exploration_marker_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                som_exploration_marker_action_grantMining(player, npc);
                string_id message = new string_id(c_stringFile, "s_48");
                utils.removeScriptVar(player, "conversation.som_exploration_marker.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_50"))
        {
            if (som_exploration_marker_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                som_exploration_marker_action_grantCrystal(player, npc);
                string_id message = new string_id(c_stringFile, "s_52");
                utils.removeScriptVar(player, "conversation.som_exploration_marker.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_54"))
        {
            if (som_exploration_marker_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                som_exploration_marker_action_grantSmoking(player, npc);
                string_id message = new string_id(c_stringFile, "s_56");
                utils.removeScriptVar(player, "conversation.som_exploration_marker.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_58"))
        {
            if (som_exploration_marker_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                som_exploration_marker_action_grantCentral(player, npc);
                string_id message = new string_id(c_stringFile, "s_60");
                utils.removeScriptVar(player, "conversation.som_exploration_marker.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_62"))
        {
            if (som_exploration_marker_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                som_exploration_marker_action_grantBurning(player, npc);
                string_id message = new string_id(c_stringFile, "s_64");
                utils.removeScriptVar(player, "conversation.som_exploration_marker.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_66"))
        {
            if (som_exploration_marker_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                som_exploration_marker_action_grantBerken(player, npc);
                string_id message = new string_id(c_stringFile, "s_68");
                utils.removeScriptVar(player, "conversation.som_exploration_marker.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_70"))
        {
            if (som_exploration_marker_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                som_exploration_marker_action_grantTulrus(player, npc);
                string_id message = new string_id(c_stringFile, "s_72");
                utils.removeScriptVar(player, "conversation.som_exploration_marker.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_exploration_marker_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_17"))
        {
            if (som_exploration_marker_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_19");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_exploration_marker_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_21");
                    }
                    utils.setScriptVar(player, "conversation.som_exploration_marker.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_exploration_marker.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_76"))
        {
            doAnimationAction(player, "refuse_offer_affection");
            if (som_exploration_marker_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_78");
                utils.removeScriptVar(player, "conversation.som_exploration_marker.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_exploration_marker_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_21"))
        {
            if (som_exploration_marker_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_23");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_exploration_marker_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_25");
                    }
                    utils.setScriptVar(player, "conversation.som_exploration_marker.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_exploration_marker.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_exploration_marker_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_25"))
        {
            if (som_exploration_marker_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_27");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_exploration_marker_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_29");
                    }
                    utils.setScriptVar(player, "conversation.som_exploration_marker.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_exploration_marker.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_exploration_marker_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_29"))
        {
            if (som_exploration_marker_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                string_id message = new string_id(c_stringFile, "s_31");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_exploration_marker_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_33");
                    }
                    utils.setScriptVar(player, "conversation.som_exploration_marker.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_exploration_marker.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_exploration_marker_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_33"))
        {
            if (som_exploration_marker_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_35");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_exploration_marker_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (som_exploration_marker_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_37");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_74");
                    }
                    utils.setScriptVar(player, "conversation.som_exploration_marker.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_exploration_marker.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_exploration_marker_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_37"))
        {
            doAnimationAction(player, "nod");
            if (som_exploration_marker_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_44");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!som_exploration_marker_condition_miningFacility(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!som_exploration_marker_condition_crystalFlats(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!som_exploration_marker_condition_smokingForest(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (!som_exploration_marker_condition_centralVolcano(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (!som_exploration_marker_condition_burningPlains(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (!som_exploration_marker_condition_berkensFlow(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                boolean hasResponse6 = false;
                if (!som_exploration_marker_condition_tulrusNesting(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_46");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_50");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_54");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_58");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_62");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_66");
                    }
                    if (hasResponse6)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_70");
                    }
                    utils.setScriptVar(player, "conversation.som_exploration_marker.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_exploration_marker.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_74"))
        {
            doAnimationAction(player, "shake_head_no");
            if (som_exploration_marker_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_78");
                utils.removeScriptVar(player, "conversation.som_exploration_marker.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_exploration_marker_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_46"))
        {
            if (som_exploration_marker_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                som_exploration_marker_action_grantMining(player, npc);
                string_id message = new string_id(c_stringFile, "s_48");
                utils.removeScriptVar(player, "conversation.som_exploration_marker.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_50"))
        {
            if (som_exploration_marker_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                som_exploration_marker_action_grantCrystal(player, npc);
                string_id message = new string_id(c_stringFile, "s_52");
                utils.removeScriptVar(player, "conversation.som_exploration_marker.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_54"))
        {
            if (som_exploration_marker_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                som_exploration_marker_action_grantSmoking(player, npc);
                string_id message = new string_id(c_stringFile, "s_56");
                utils.removeScriptVar(player, "conversation.som_exploration_marker.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_58"))
        {
            if (som_exploration_marker_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                som_exploration_marker_action_grantCentral(player, npc);
                string_id message = new string_id(c_stringFile, "s_60");
                utils.removeScriptVar(player, "conversation.som_exploration_marker.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_62"))
        {
            if (som_exploration_marker_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                som_exploration_marker_action_grantBurning(player, npc);
                string_id message = new string_id(c_stringFile, "s_64");
                utils.removeScriptVar(player, "conversation.som_exploration_marker.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_66"))
        {
            if (som_exploration_marker_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                som_exploration_marker_action_grantBerken(player, npc);
                string_id message = new string_id(c_stringFile, "s_68");
                utils.removeScriptVar(player, "conversation.som_exploration_marker.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_70"))
        {
            if (som_exploration_marker_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                som_exploration_marker_action_grantTulrus(player, npc);
                string_id message = new string_id(c_stringFile, "s_72");
                utils.removeScriptVar(player, "conversation.som_exploration_marker.branchId");
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
            detachScript(self, "conversation.som_exploration_marker");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_INTERESTING);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_INTERESTING);
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
        detachScript(self, "conversation.som_exploration_marker");
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
        if (som_exploration_marker_condition_completedReward(player, npc))
        {
            doAnimationAction(npc, "bow");
            string_id message = new string_id(c_stringFile, "s_4");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (som_exploration_marker_condition_completeAll(player, npc))
        {
            doAnimationAction(npc, "thank");
            string_id message = new string_id(c_stringFile, "s_6");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (som_exploration_marker_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_41");
                }
                utils.setScriptVar(player, "conversation.som_exploration_marker.branchId", 2);
                npcStartConversation(player, npc, "som_exploration_marker", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (som_exploration_marker_condition_hasAcceptedOne(player, npc))
        {
            doAnimationAction(npc, "wave1");
            string_id message = new string_id(c_stringFile, "s_10");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (som_exploration_marker_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (som_exploration_marker_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_38");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_39");
                }
                utils.setScriptVar(player, "conversation.som_exploration_marker.branchId", 4);
                npcStartConversation(player, npc, "som_exploration_marker", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (som_exploration_marker_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "bow4");
            string_id message = new string_id(c_stringFile, "s_15");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (som_exploration_marker_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (som_exploration_marker_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_17");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_76");
                }
                utils.setScriptVar(player, "conversation.som_exploration_marker.branchId", 6);
                npcStartConversation(player, npc, "som_exploration_marker", message, responses);
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
        if (!conversationId.equals("som_exploration_marker"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.som_exploration_marker.branchId");
        if (branchId == 2 && som_exploration_marker_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && som_exploration_marker_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && som_exploration_marker_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && som_exploration_marker_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && som_exploration_marker_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && som_exploration_marker_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && som_exploration_marker_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && som_exploration_marker_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && som_exploration_marker_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && som_exploration_marker_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.som_exploration_marker.branchId");
        return SCRIPT_CONTINUE;
    }
}
