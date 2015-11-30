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
import script.library.content;
import script.library.groundquests;
import script.library.space_quest;
import script.library.utils;

public class naboo_legacy_wilson_tchorshel extends script.base_script
{
    public naboo_legacy_wilson_tchorshel()
    {
    }
    public static String c_stringFile = "conversation/naboo_legacy_wilson_tchorshel";
    public boolean naboo_legacy_wilson_tchorshel_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean naboo_legacy_wilson_tchorshel_condition_kickQuestActive_Imp(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "legacy_naboo_kick_imp") || ((groundquests.hasCompletedQuest(player, "legacy_together") || groundquests.hasCompletedQuest(player, "legacy_together_2")) && hasObjVar(player, content.IMPERIAL_PATH_OBJVAR_NAME));
    }
    public boolean naboo_legacy_wilson_tchorshel_condition_kickQuestActive_Rebel(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "legacy_naboo_kick_reb") || ((groundquests.hasCompletedQuest(player, "legacy_together") || groundquests.hasCompletedQuest(player, "legacy_together_2")) && hasObjVar(player, content.REBEL_PATH_OBJVAR_NAME));
    }
    public boolean naboo_legacy_wilson_tchorshel_condition_seekingDroidParts_Rebel(obj_id player, obj_id npc) throws InterruptedException
    {
        return (!groundquests.hasCompletedQuest(player, "legacy_naboo_droid_module_1") || !groundquests.hasCompletedQuest(player, "legacy_naboo_droid_module_2") || !groundquests.hasCompletedQuest(player, "legacy_naboo_droid_module_3_pt2") || !groundquests.hasCompletedQuest(player, "legacy_naboo_droid_module_4")) && badge.hasBadge(player, "bdg_content_rsf_clearance_7") && hasObjVar(player, content.REBEL_PATH_OBJVAR_NAME);
    }
    public boolean naboo_legacy_wilson_tchorshel_condition_seekingRSFComputerAccess(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "naboo_kadaraa_tipping_the_balance_2") && !badge.hasBadge(player, "bdg_content_rsf_clearance_7");
    }
    public boolean naboo_legacy_wilson_tchorshel_condition_hasAllDroidParts_Rebel(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "legacy_naboo_droid_module_1") && groundquests.hasCompletedQuest(player, "legacy_naboo_droid_module_2") && groundquests.hasCompletedQuest(player, "legacy_naboo_droid_module_3_pt2") && groundquests.hasCompletedQuest(player, "legacy_naboo_droid_module_4") && hasObjVar(player, content.REBEL_PATH_OBJVAR_NAME);
    }
    public boolean naboo_legacy_wilson_tchorshel_condition_onPanakaQuest_Rebel(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isQuestActive(player, "naboo_kadaraa_tipping_the_balance_1") || groundquests.hasCompletedQuest(player, "naboo_kadaraa_tipping_the_balance_1") || groundquests.isQuestActive(player, "naboo_kadaraa_tipping_the_balance_2")) && hasObjVar(player, content.REBEL_PATH_OBJVAR_NAME);
    }
    public boolean naboo_legacy_wilson_tchorshel_condition_onPanakaQuest_Imp(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isQuestActive(player, "naboo_kadaraa_tipping_the_balance_1") || groundquests.hasCompletedQuest(player, "naboo_kadaraa_tipping_the_balance_1") || groundquests.isQuestActive(player, "naboo_kadaraa_tipping_the_balance_2")) && hasObjVar(player, content.IMPERIAL_PATH_OBJVAR_NAME);
    }
    public boolean naboo_legacy_wilson_tchorshel_condition_hasAllDroidParts_Imp(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "legacy_naboo_droid_module_1") && groundquests.hasCompletedQuest(player, "legacy_naboo_droid_module_2") && groundquests.hasCompletedQuest(player, "legacy_naboo_droid_module_3_pt2") && groundquests.hasCompletedQuest(player, "legacy_naboo_droid_module_4") && hasObjVar(player, content.IMPERIAL_PATH_OBJVAR_NAME);
    }
    public boolean naboo_legacy_wilson_tchorshel_condition_seekingDroidParts_Imp(obj_id player, obj_id npc) throws InterruptedException
    {
        return (!groundquests.hasCompletedQuest(player, "legacy_naboo_droid_module_1") || !groundquests.hasCompletedQuest(player, "legacy_naboo_droid_module_2") || !groundquests.hasCompletedQuest(player, "legacy_naboo_droid_module_3_pt2") || !groundquests.hasCompletedQuest(player, "legacy_naboo_droid_module_4")) && badge.hasBadge(player, "bdg_content_rsf_clearance_7") && hasObjVar(player, content.IMPERIAL_PATH_OBJVAR_NAME);
    }
    public boolean naboo_legacy_wilson_tchorshel_condition_completedNaboo_Imperial(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "legacy_naboo_imperial_final_reward");
    }
    public boolean naboo_legacy_wilson_tchorshel_condition_completedNaboo_Rebel(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "legacy_naboo_rebel_final_reward");
    }
    public void naboo_legacy_wilson_tchorshel_action_givePointerToCorellia(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!groundquests.isQuestActive(player, "naboo_send_to_lt_jasper") && !groundquests.isQuestActiveOrComplete(player, "corellia_coronet_capitol_problems_missing_shipment_intro"))
        {
            groundquests.grantQuest(player, "naboo_send_to_lt_jasper");
        }
    }
    public void naboo_legacy_wilson_tchorshel_action_grantKadaaraQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, content.IMPERIAL_PATH_OBJVAR_NAME))
        {
            removeObjVar(player, content.IMPERIAL_PATH_OBJVAR_NAME);
        }
        if (!hasObjVar(player, content.REBEL_PATH_OBJVAR_NAME))
        {
            setObjVar(player, content.REBEL_PATH_OBJVAR_NAME, true);
        }
        groundquests.requestGrantQuest(player, "naboo_kadaraa_tipping_the_balance_1");
        return;
    }
    public void naboo_legacy_wilson_tchorshel_action_grantFinalReward_Rebel(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, content.IMPERIAL_PATH_OBJVAR_NAME))
        {
            removeObjVar(player, content.IMPERIAL_PATH_OBJVAR_NAME);
        }
        if (!hasObjVar(player, content.REBEL_PATH_OBJVAR_NAME))
        {
            setObjVar(player, content.REBEL_PATH_OBJVAR_NAME, true);
        }
        groundquests.sendSignal(player, "returnToLegacyContact");
        groundquests.requestGrantQuest(player, "legacy_naboo_rebel_final_reward");
        return;
    }
    public void naboo_legacy_wilson_tchorshel_action_endFindDroidParts(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "returnToImperilOrRebelContact");
    }
    public void naboo_legacy_wilson_tchorshel_action_endGotoKadaara(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "naboo_from_tatooine_kick");
    }
    public int naboo_legacy_wilson_tchorshel_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_90"))
        {
            if (naboo_legacy_wilson_tchorshel_condition__defaultCondition(player, npc))
            {
                naboo_legacy_wilson_tchorshel_action_endFindDroidParts(player, npc);
                string_id message = new string_id(c_stringFile, "s_92");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_legacy_wilson_tchorshel_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_legacy_wilson_tchorshel_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_83");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_87");
                    }
                    utils.setScriptVar(player, "conversation.naboo_legacy_wilson_tchorshel.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_legacy_wilson_tchorshel.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_91"))
        {
            if (naboo_legacy_wilson_tchorshel_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_93");
                utils.removeScriptVar(player, "conversation.naboo_legacy_wilson_tchorshel.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_legacy_wilson_tchorshel_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_83"))
        {
            if (naboo_legacy_wilson_tchorshel_condition__defaultCondition(player, npc))
            {
                naboo_legacy_wilson_tchorshel_action_grantFinalReward_Rebel(player, npc);
                string_id message = new string_id(c_stringFile, "s_85");
                utils.removeScriptVar(player, "conversation.naboo_legacy_wilson_tchorshel.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_87"))
        {
            if (naboo_legacy_wilson_tchorshel_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_89");
                utils.removeScriptVar(player, "conversation.naboo_legacy_wilson_tchorshel.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_legacy_wilson_tchorshel_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_80"))
        {
            if (naboo_legacy_wilson_tchorshel_condition__defaultCondition(player, npc))
            {
                naboo_legacy_wilson_tchorshel_action_endFindDroidParts(player, npc);
                string_id message = new string_id(c_stringFile, "s_81");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_legacy_wilson_tchorshel_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_legacy_wilson_tchorshel_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_83");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_87");
                    }
                    utils.setScriptVar(player, "conversation.naboo_legacy_wilson_tchorshel.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_legacy_wilson_tchorshel.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_legacy_wilson_tchorshel_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_83"))
        {
            if (naboo_legacy_wilson_tchorshel_condition__defaultCondition(player, npc))
            {
                naboo_legacy_wilson_tchorshel_action_grantFinalReward_Rebel(player, npc);
                string_id message = new string_id(c_stringFile, "s_85");
                utils.removeScriptVar(player, "conversation.naboo_legacy_wilson_tchorshel.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_87"))
        {
            if (naboo_legacy_wilson_tchorshel_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_89");
                utils.removeScriptVar(player, "conversation.naboo_legacy_wilson_tchorshel.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_legacy_wilson_tchorshel_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_53"))
        {
            if (naboo_legacy_wilson_tchorshel_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_55");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_legacy_wilson_tchorshel_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_57");
                    }
                    utils.setScriptVar(player, "conversation.naboo_legacy_wilson_tchorshel.branchId", 16);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_legacy_wilson_tchorshel.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_59"))
        {
            if (naboo_legacy_wilson_tchorshel_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_61");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_legacy_wilson_tchorshel_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_legacy_wilson_tchorshel_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_63");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_69");
                    }
                    utils.setScriptVar(player, "conversation.naboo_legacy_wilson_tchorshel.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_legacy_wilson_tchorshel.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_legacy_wilson_tchorshel_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_57"))
        {
            if (naboo_legacy_wilson_tchorshel_condition__defaultCondition(player, npc))
            {
                naboo_legacy_wilson_tchorshel_action_endGotoKadaara(player, npc);
                string_id message = new string_id(c_stringFile, "s_86");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_legacy_wilson_tchorshel_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
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
                    utils.setScriptVar(player, "conversation.naboo_legacy_wilson_tchorshel.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_legacy_wilson_tchorshel.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_legacy_wilson_tchorshel_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_63"))
        {
            if (naboo_legacy_wilson_tchorshel_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_65");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_legacy_wilson_tchorshel_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_67");
                    }
                    utils.setScriptVar(player, "conversation.naboo_legacy_wilson_tchorshel.branchId", 18);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_legacy_wilson_tchorshel.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_69"))
        {
            if (naboo_legacy_wilson_tchorshel_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_71");
                utils.removeScriptVar(player, "conversation.naboo_legacy_wilson_tchorshel.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_legacy_wilson_tchorshel_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_67"))
        {
            if (naboo_legacy_wilson_tchorshel_condition__defaultCondition(player, npc))
            {
                naboo_legacy_wilson_tchorshel_action_endGotoKadaara(player, npc);
                string_id message = new string_id(c_stringFile, "s_86");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_legacy_wilson_tchorshel_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
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
                    utils.setScriptVar(player, "conversation.naboo_legacy_wilson_tchorshel.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_legacy_wilson_tchorshel.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_legacy_wilson_tchorshel_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_76"))
        {
            if (naboo_legacy_wilson_tchorshel_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_78");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_legacy_wilson_tchorshel_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_82");
                    }
                    utils.setScriptVar(player, "conversation.naboo_legacy_wilson_tchorshel.branchId", 21);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_legacy_wilson_tchorshel.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_legacy_wilson_tchorshel_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_82"))
        {
            if (naboo_legacy_wilson_tchorshel_condition__defaultCondition(player, npc))
            {
                naboo_legacy_wilson_tchorshel_action_endGotoKadaara(player, npc);
                string_id message = new string_id(c_stringFile, "s_86");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_legacy_wilson_tchorshel_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
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
                    utils.setScriptVar(player, "conversation.naboo_legacy_wilson_tchorshel.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_legacy_wilson_tchorshel.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_legacy_wilson_tchorshel_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_94"))
        {
            if (naboo_legacy_wilson_tchorshel_condition__defaultCondition(player, npc))
            {
                naboo_legacy_wilson_tchorshel_action_grantKadaaraQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_96");
                utils.removeScriptVar(player, "conversation.naboo_legacy_wilson_tchorshel.branchId");
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
            detachScript(self, "conversation.naboo_legacy_wilson_tchorshel");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
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
        detachScript(self, "conversation.naboo_legacy_wilson_tchorshel");
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
        if (naboo_legacy_wilson_tchorshel_condition_completedNaboo_Imperial(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_97");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (naboo_legacy_wilson_tchorshel_condition_completedNaboo_Rebel(player, npc))
        {
            naboo_legacy_wilson_tchorshel_action_givePointerToCorellia(player, npc);
            string_id message = new string_id(c_stringFile, "s_98");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (naboo_legacy_wilson_tchorshel_condition_hasAllDroidParts_Imp(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_48");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_legacy_wilson_tchorshel_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (naboo_legacy_wilson_tchorshel_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_90");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_91");
                }
                utils.setScriptVar(player, "conversation.naboo_legacy_wilson_tchorshel.branchId", 3);
                npcStartConversation(player, npc, "naboo_legacy_wilson_tchorshel", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_legacy_wilson_tchorshel_condition_hasAllDroidParts_Rebel(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_12");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_legacy_wilson_tchorshel_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
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
                utils.setScriptVar(player, "conversation.naboo_legacy_wilson_tchorshel.branchId", 6);
                npcStartConversation(player, npc, "naboo_legacy_wilson_tchorshel", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_legacy_wilson_tchorshel_condition_seekingDroidParts_Imp(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_75");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (naboo_legacy_wilson_tchorshel_condition_seekingDroidParts_Rebel(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_20");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (naboo_legacy_wilson_tchorshel_condition_seekingRSFComputerAccess(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_22");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (naboo_legacy_wilson_tchorshel_condition_onPanakaQuest_Imp(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_37");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (naboo_legacy_wilson_tchorshel_condition_onPanakaQuest_Rebel(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_49");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (naboo_legacy_wilson_tchorshel_condition_kickQuestActive_Imp(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_51");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_legacy_wilson_tchorshel_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (naboo_legacy_wilson_tchorshel_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_53");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_59");
                }
                utils.setScriptVar(player, "conversation.naboo_legacy_wilson_tchorshel.branchId", 15);
                npcStartConversation(player, npc, "naboo_legacy_wilson_tchorshel", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_legacy_wilson_tchorshel_condition_kickQuestActive_Rebel(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_73");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_legacy_wilson_tchorshel_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
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
                utils.setScriptVar(player, "conversation.naboo_legacy_wilson_tchorshel.branchId", 20);
                npcStartConversation(player, npc, "naboo_legacy_wilson_tchorshel", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_legacy_wilson_tchorshel_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_100");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("naboo_legacy_wilson_tchorshel"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.naboo_legacy_wilson_tchorshel.branchId");
        if (branchId == 3 && naboo_legacy_wilson_tchorshel_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && naboo_legacy_wilson_tchorshel_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && naboo_legacy_wilson_tchorshel_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && naboo_legacy_wilson_tchorshel_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && naboo_legacy_wilson_tchorshel_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && naboo_legacy_wilson_tchorshel_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && naboo_legacy_wilson_tchorshel_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && naboo_legacy_wilson_tchorshel_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && naboo_legacy_wilson_tchorshel_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && naboo_legacy_wilson_tchorshel_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && naboo_legacy_wilson_tchorshel_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.naboo_legacy_wilson_tchorshel.branchId");
        return SCRIPT_CONTINUE;
    }
}
