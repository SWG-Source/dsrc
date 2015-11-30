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
import script.library.groundquests;
import script.library.utils;

public class borvos_acklay_dakk extends script.base_script
{
    public borvos_acklay_dakk()
    {
    }
    public static String c_stringFile = "conversation/borvos_acklay_dakk";
    public boolean borvos_acklay_dakk_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean borvos_acklay_dakk_condition_hasArmorQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "borvo_acklay_find_armorer", "find_armorer") || groundquests.isTaskActive(player, "borvo_acklay_find_armorer_again", "find_dakk"));
    }
    public boolean borvos_acklay_dakk_condition_onOrFinishedHelmet(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActiveOrComplete(player, "borvo_acklay_helmet");
    }
    public boolean borvos_acklay_dakk_condition_onOrFinishedChest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActiveOrComplete(player, "borvo_acklay_chest_plate");
    }
    public boolean borvos_acklay_dakk_condition_onOrFinishedLeggings(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActiveOrComplete(player, "borvo_acklay_leggings");
    }
    public boolean borvos_acklay_dakk_condition_onOrFinishedBootsGolves(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActiveOrComplete(player, "borvo_acklay_boots_gloves");
    }
    public boolean borvos_acklay_dakk_condition_onOrFinishedBracers(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActiveOrComplete(player, "borvo_acklay_bracers");
    }
    public boolean borvos_acklay_dakk_condition_onOrFinishedBiceps(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActiveOrComplete(player, "borvo_acklay_biceps");
    }
    public boolean borvos_acklay_dakk_condition_isOnQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isQuestActive(player, "borvo_acklay_bracers") || groundquests.isQuestActive(player, "borvo_acklay_biceps") || groundquests.isQuestActive(player, "borvo_acklay_leggings") || groundquests.isQuestActive(player, "borvo_acklay_helmet") || groundquests.isQuestActive(player, "borvo_acklay_chest_plate") || groundquests.isQuestActive(player, "borvo_acklay_boots_gloves"));
    }
    public boolean borvos_acklay_dakk_condition_backFromHunting(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "borvo_acklay_bracers", "return") || groundquests.isTaskActive(player, "borvo_acklay_biceps", "return") || groundquests.isTaskActive(player, "borvo_acklay_boots_gloves", "return") || groundquests.isTaskActive(player, "borvo_acklay_chest_plate", "return") || groundquests.isTaskActive(player, "borvo_acklay_leggings", "return") || groundquests.isTaskActive(player, "borvo_acklay_helmet", "return"));
    }
    public boolean borvos_acklay_dakk_condition_deletedQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        boolean questDeleted = false;
        if (hasObjVar(player, "borvo_acklay"))
        {
            questDeleted = true;
        }
        return questDeleted;
    }
    public boolean borvos_acklay_dakk_condition_hasAllArmor(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedQuest(player, "borvo_acklay_bracers") && groundquests.hasCompletedQuest(player, "borvo_acklay_biceps") && groundquests.hasCompletedQuest(player, "borvo_acklay_leggings") && groundquests.hasCompletedQuest(player, "borvo_acklay_helmet") && groundquests.hasCompletedQuest(player, "borvo_acklay_chest_plate") && groundquests.hasCompletedQuest(player, "borvo_acklay_boots_gloves"));
    }
    public boolean borvos_acklay_dakk_condition_hasFinishedSomeArmor(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedQuest(player, "borvo_acklay_bracers") || groundquests.hasCompletedQuest(player, "borvo_acklay_biceps") || groundquests.hasCompletedQuest(player, "borvo_acklay_leggings") || groundquests.hasCompletedQuest(player, "borvo_acklay_helmet") || groundquests.hasCompletedQuest(player, "borvo_acklay_chest_plate") || groundquests.hasCompletedQuest(player, "borvo_acklay_boots_gloves"));
    }
    public void borvos_acklay_dakk_action_grantChest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "borvo_acklay_chest_plate");
        setObjVar(player, "borvo_acklay", "chest");
        groundquests.sendSignal(player, "acklay_armorer_found");
    }
    public void borvos_acklay_dakk_action_grantLeggings(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "borvo_acklay_leggings");
        setObjVar(player, "borvo_acklay", "leggings");
        groundquests.sendSignal(player, "acklay_armorer_found");
    }
    public void borvos_acklay_dakk_action_grantBootsGloves(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "borvo_acklay_boots_gloves");
        setObjVar(player, "borvo_acklay", "boots");
        groundquests.sendSignal(player, "acklay_armorer_found");
    }
    public void borvos_acklay_dakk_action_grantBiceps(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "borvo_acklay_biceps");
        setObjVar(player, "borvo_acklay", "biceps");
        groundquests.sendSignal(player, "acklay_armorer_found");
    }
    public void borvos_acklay_dakk_action_grantBracers(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "borvo_acklay_bracers");
        setObjVar(player, "borvo_acklay", "bracers");
        groundquests.sendSignal(player, "acklay_armorer_found");
    }
    public void borvos_acklay_dakk_action_grantHelmet(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "borvo_acklay_helmet");
        setObjVar(player, "borvo_acklay", "helmet");
        groundquests.sendSignal(player, "acklay_armorer_found");
    }
    public void borvos_acklay_dakk_action_sendSignalCraft(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "acklay_return");
        removeObjVar(player, "borvo_acklay");
    }
    public void borvos_acklay_dakk_action_regrantQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        String questName = getStringObjVar(player, "borvo_acklay");
        if (questName.equals("bracers"))
        {
            groundquests.grantQuest(player, "borvo_acklay_bracers");
        }
        if (questName.equals("boots"))
        {
            groundquests.grantQuest(player, "borvo_acklay_boots_gloves");
        }
        if (questName.equals("helmet"))
        {
            groundquests.grantQuest(player, "borvo_acklay_helmet");
        }
        if (questName.equals("leggings"))
        {
            groundquests.grantQuest(player, "borvo_acklay_leggings");
        }
        if (questName.equals("chest"))
        {
            groundquests.grantQuest(player, "borvo_acklay_chest_plate");
        }
        if (questName.equals("biceps"))
        {
            groundquests.grantQuest(player, "borvo_acklay_biceps");
        }
    }
    public void borvos_acklay_dakk_action_signalFoundArmorer(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "acklay_armorer_found");
    }
    public int borvos_acklay_dakk_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_43"))
        {
            doAnimationAction(player, "feed_creature_medium");
            if (borvos_acklay_dakk_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_44");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (borvos_acklay_dakk_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_94");
                    }
                    utils.setScriptVar(player, "conversation.borvos_acklay_dakk.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.borvos_acklay_dakk.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int borvos_acklay_dakk_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_94"))
        {
            if (borvos_acklay_dakk_condition__defaultCondition(player, npc))
            {
                borvos_acklay_dakk_action_sendSignalCraft(player, npc);
                string_id message = new string_id(c_stringFile, "s_96");
                utils.removeScriptVar(player, "conversation.borvos_acklay_dakk.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int borvos_acklay_dakk_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_40"))
        {
            if (borvos_acklay_dakk_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_41");
                utils.removeScriptVar(player, "conversation.borvos_acklay_dakk.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int borvos_acklay_dakk_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_55"))
        {
            if (borvos_acklay_dakk_condition__defaultCondition(player, npc))
            {
                borvos_acklay_dakk_action_regrantQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_57");
                utils.removeScriptVar(player, "conversation.borvos_acklay_dakk.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int borvos_acklay_dakk_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_59"))
        {
            if (borvos_acklay_dakk_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_61");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (borvos_acklay_dakk_condition_hasArmorQuest(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (borvos_acklay_dakk_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_62");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_63");
                    }
                    utils.setScriptVar(player, "conversation.borvos_acklay_dakk.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.borvos_acklay_dakk.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_60"))
        {
            if (borvos_acklay_dakk_condition__defaultCondition(player, npc))
            {
                borvos_acklay_dakk_action_signalFoundArmorer(player, npc);
                string_id message = new string_id(c_stringFile, "s_64");
                utils.removeScriptVar(player, "conversation.borvos_acklay_dakk.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_66"))
        {
            if (borvos_acklay_dakk_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_67");
                utils.removeScriptVar(player, "conversation.borvos_acklay_dakk.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int borvos_acklay_dakk_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_62"))
        {
            if (borvos_acklay_dakk_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_65");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!borvos_acklay_dakk_condition_onOrFinishedBracers(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!borvos_acklay_dakk_condition_onOrFinishedBiceps(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!borvos_acklay_dakk_condition_onOrFinishedChest(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (!borvos_acklay_dakk_condition_onOrFinishedHelmet(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (!borvos_acklay_dakk_condition_onOrFinishedLeggings(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (!borvos_acklay_dakk_condition_onOrFinishedBootsGolves(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_70");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_74");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_78");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_82");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_86");
                    }
                    utils.setScriptVar(player, "conversation.borvos_acklay_dakk.branchId", 16);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.borvos_acklay_dakk.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_63"))
        {
            if (borvos_acklay_dakk_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_68");
                utils.removeScriptVar(player, "conversation.borvos_acklay_dakk.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int borvos_acklay_dakk_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_52"))
        {
            if (borvos_acklay_dakk_condition__defaultCondition(player, npc))
            {
                borvos_acklay_dakk_action_grantBracers(player, npc);
                string_id message = new string_id(c_stringFile, "s_56");
                utils.removeScriptVar(player, "conversation.borvos_acklay_dakk.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_70"))
        {
            if (borvos_acklay_dakk_condition__defaultCondition(player, npc))
            {
                borvos_acklay_dakk_action_grantBiceps(player, npc);
                string_id message = new string_id(c_stringFile, "s_72");
                utils.removeScriptVar(player, "conversation.borvos_acklay_dakk.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_74"))
        {
            if (borvos_acklay_dakk_condition__defaultCondition(player, npc))
            {
                borvos_acklay_dakk_action_grantChest(player, npc);
                string_id message = new string_id(c_stringFile, "s_76");
                utils.removeScriptVar(player, "conversation.borvos_acklay_dakk.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_78"))
        {
            if (borvos_acklay_dakk_condition__defaultCondition(player, npc))
            {
                borvos_acklay_dakk_action_grantHelmet(player, npc);
                string_id message = new string_id(c_stringFile, "s_80");
                utils.removeScriptVar(player, "conversation.borvos_acklay_dakk.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_82"))
        {
            if (borvos_acklay_dakk_condition__defaultCondition(player, npc))
            {
                borvos_acklay_dakk_action_grantLeggings(player, npc);
                string_id message = new string_id(c_stringFile, "s_84");
                utils.removeScriptVar(player, "conversation.borvos_acklay_dakk.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_86"))
        {
            if (borvos_acklay_dakk_condition__defaultCondition(player, npc))
            {
                borvos_acklay_dakk_action_grantBootsGloves(player, npc);
                string_id message = new string_id(c_stringFile, "s_88");
                utils.removeScriptVar(player, "conversation.borvos_acklay_dakk.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int borvos_acklay_dakk_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_32"))
        {
            if (borvos_acklay_dakk_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_34");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (borvos_acklay_dakk_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_36");
                    }
                    utils.setScriptVar(player, "conversation.borvos_acklay_dakk.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.borvos_acklay_dakk.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int borvos_acklay_dakk_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_36"))
        {
            if (borvos_acklay_dakk_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_38");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!borvos_acklay_dakk_condition_onOrFinishedBracers(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!borvos_acklay_dakk_condition_onOrFinishedBiceps(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!borvos_acklay_dakk_condition_onOrFinishedChest(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (!borvos_acklay_dakk_condition_onOrFinishedHelmet(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (!borvos_acklay_dakk_condition_onOrFinishedLeggings(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (!borvos_acklay_dakk_condition_onOrFinishedBootsGolves(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_70");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_74");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_78");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_82");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_86");
                    }
                    utils.setScriptVar(player, "conversation.borvos_acklay_dakk.branchId", 16);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.borvos_acklay_dakk.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int borvos_acklay_dakk_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_52"))
        {
            if (borvos_acklay_dakk_condition__defaultCondition(player, npc))
            {
                borvos_acklay_dakk_action_grantBracers(player, npc);
                string_id message = new string_id(c_stringFile, "s_56");
                utils.removeScriptVar(player, "conversation.borvos_acklay_dakk.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_70"))
        {
            if (borvos_acklay_dakk_condition__defaultCondition(player, npc))
            {
                borvos_acklay_dakk_action_grantBiceps(player, npc);
                string_id message = new string_id(c_stringFile, "s_72");
                utils.removeScriptVar(player, "conversation.borvos_acklay_dakk.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_74"))
        {
            if (borvos_acklay_dakk_condition__defaultCondition(player, npc))
            {
                borvos_acklay_dakk_action_grantChest(player, npc);
                string_id message = new string_id(c_stringFile, "s_76");
                utils.removeScriptVar(player, "conversation.borvos_acklay_dakk.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_78"))
        {
            if (borvos_acklay_dakk_condition__defaultCondition(player, npc))
            {
                borvos_acklay_dakk_action_grantHelmet(player, npc);
                string_id message = new string_id(c_stringFile, "s_80");
                utils.removeScriptVar(player, "conversation.borvos_acklay_dakk.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_82"))
        {
            if (borvos_acklay_dakk_condition__defaultCondition(player, npc))
            {
                borvos_acklay_dakk_action_grantLeggings(player, npc);
                string_id message = new string_id(c_stringFile, "s_84");
                utils.removeScriptVar(player, "conversation.borvos_acklay_dakk.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_86"))
        {
            if (borvos_acklay_dakk_condition__defaultCondition(player, npc))
            {
                borvos_acklay_dakk_action_grantBootsGloves(player, npc);
                string_id message = new string_id(c_stringFile, "s_88");
                utils.removeScriptVar(player, "conversation.borvos_acklay_dakk.branchId");
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
            detachScript(self, "conversation.borvos_acklay_dakk");
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
        detachScript(self, "conversation.borvos_acklay_dakk");
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
        if (borvos_acklay_dakk_condition_backFromHunting(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_42");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (borvos_acklay_dakk_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_43");
                }
                utils.setScriptVar(player, "conversation.borvos_acklay_dakk.branchId", 1);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "borvos_acklay_dakk", null, pp, responses);
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
        if (borvos_acklay_dakk_condition_isOnQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_39");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (borvos_acklay_dakk_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_40");
                }
                utils.setScriptVar(player, "conversation.borvos_acklay_dakk.branchId", 4);
                npcStartConversation(player, npc, "borvos_acklay_dakk", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (borvos_acklay_dakk_condition_deletedQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_53");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (borvos_acklay_dakk_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_55");
                }
                utils.setScriptVar(player, "conversation.borvos_acklay_dakk.branchId", 6);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "borvos_acklay_dakk", null, pp, responses);
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
        if (borvos_acklay_dakk_condition_hasFinishedSomeArmor(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_58");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (!borvos_acklay_dakk_condition_hasAllArmor(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (borvos_acklay_dakk_condition_hasAllArmor(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (borvos_acklay_dakk_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_59");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_60");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_66");
                }
                utils.setScriptVar(player, "conversation.borvos_acklay_dakk.branchId", 8);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "borvos_acklay_dakk", null, pp, responses);
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
        if (borvos_acklay_dakk_condition_hasArmorQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_30");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (borvos_acklay_dakk_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_32");
                }
                utils.setScriptVar(player, "conversation.borvos_acklay_dakk.branchId", 14);
                npcStartConversation(player, npc, "borvos_acklay_dakk", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (borvos_acklay_dakk_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_90");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("borvos_acklay_dakk"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.borvos_acklay_dakk.branchId");
        if (branchId == 1 && borvos_acklay_dakk_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && borvos_acklay_dakk_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && borvos_acklay_dakk_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && borvos_acklay_dakk_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && borvos_acklay_dakk_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && borvos_acklay_dakk_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && borvos_acklay_dakk_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && borvos_acklay_dakk_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && borvos_acklay_dakk_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && borvos_acklay_dakk_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.borvos_acklay_dakk.branchId");
        return SCRIPT_CONTINUE;
    }
}
