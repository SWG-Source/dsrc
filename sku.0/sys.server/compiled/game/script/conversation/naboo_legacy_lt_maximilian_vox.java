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

public class naboo_legacy_lt_maximilian_vox extends script.base_script
{
    public naboo_legacy_lt_maximilian_vox()
    {
    }
    public static String c_stringFile = "conversation/naboo_legacy_lt_maximilian_vox";
    public boolean naboo_legacy_lt_maximilian_vox_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean naboo_legacy_lt_maximilian_vox_condition_kickQuestActive_Imp(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "legacy_naboo_kick_imp") || ((groundquests.hasCompletedQuest(player, "legacy_together") || groundquests.hasCompletedQuest(player, "legacy_together_2")) && hasObjVar(player, content.IMPERIAL_PATH_OBJVAR_NAME));
    }
    public boolean naboo_legacy_lt_maximilian_vox_condition_kickQuestActive_Rebel(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "legacy_naboo_kick_reb") || ((groundquests.hasCompletedQuest(player, "legacy_together") || groundquests.hasCompletedQuest(player, "legacy_together_2")) && hasObjVar(player, content.REBEL_PATH_OBJVAR_NAME));
    }
    public boolean naboo_legacy_lt_maximilian_vox_condition_seekingDroidParts_Imp(obj_id player, obj_id npc) throws InterruptedException
    {
        return (!groundquests.hasCompletedQuest(player, "legacy_naboo_droid_module_1") || !groundquests.hasCompletedQuest(player, "legacy_naboo_droid_module_2") || !groundquests.hasCompletedQuest(player, "legacy_naboo_droid_module_3_pt2") || !groundquests.hasCompletedQuest(player, "legacy_naboo_droid_module_4")) && badge.hasBadge(player, "bdg_content_rsf_clearance_7") && hasObjVar(player, content.IMPERIAL_PATH_OBJVAR_NAME);
    }
    public boolean naboo_legacy_lt_maximilian_vox_condition_seekingRSFComputerAccess(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "naboo_kadaraa_tipping_the_balance_2") && !badge.hasBadge(player, "bdg_content_rsf_clearance_7");
    }
    public boolean naboo_legacy_lt_maximilian_vox_condition_hasAllDroidParts_Imp(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "legacy_naboo_droid_module_1") && groundquests.hasCompletedQuest(player, "legacy_naboo_droid_module_2") && groundquests.hasCompletedQuest(player, "legacy_naboo_droid_module_3_pt2") && groundquests.hasCompletedQuest(player, "legacy_naboo_droid_module_4") && hasObjVar(player, content.IMPERIAL_PATH_OBJVAR_NAME);
    }
    public boolean naboo_legacy_lt_maximilian_vox_condition_seekingDroidParts_Rebel(obj_id player, obj_id npc) throws InterruptedException
    {
        return (!groundquests.hasCompletedQuest(player, "legacy_naboo_droid_module_1") || !groundquests.hasCompletedQuest(player, "legacy_naboo_droid_module_2") || !groundquests.hasCompletedQuest(player, "legacy_naboo_droid_module_3_pt2") || !groundquests.hasCompletedQuest(player, "legacy_naboo_droid_module_4")) && badge.hasBadge(player, "bdg_content_rsf_clearance_7") && hasObjVar(player, content.REBEL_PATH_OBJVAR_NAME);
    }
    public boolean naboo_legacy_lt_maximilian_vox_condition_hasAllDroidParts_Rebel(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "legacy_naboo_droid_module_1") && groundquests.hasCompletedQuest(player, "legacy_naboo_droid_module_2") && groundquests.hasCompletedQuest(player, "legacy_naboo_droid_module_3_pt2") && groundquests.hasCompletedQuest(player, "legacy_naboo_droid_module_4") && hasObjVar(player, content.REBEL_PATH_OBJVAR_NAME);
    }
    public boolean naboo_legacy_lt_maximilian_vox_condition_onPanakaQuest_Rebel(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isQuestActive(player, "naboo_kadaraa_tipping_the_balance_1") || groundquests.hasCompletedQuest(player, "naboo_kadaraa_tipping_the_balance_1") || groundquests.isQuestActive(player, "naboo_kadaraa_tipping_the_balance_2")) && hasObjVar(player, content.REBEL_PATH_OBJVAR_NAME);
    }
    public boolean naboo_legacy_lt_maximilian_vox_condition_onPanakaQuest_Imp(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isQuestActive(player, "naboo_kadaraa_tipping_the_balance_1") || groundquests.hasCompletedQuest(player, "naboo_kadaraa_tipping_the_balance_1") || groundquests.isQuestActive(player, "naboo_kadaraa_tipping_the_balance_2")) && hasObjVar(player, content.IMPERIAL_PATH_OBJVAR_NAME);
    }
    public boolean naboo_legacy_lt_maximilian_vox_condition_completedNaboo_Imp(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "legacy_naboo_imperial_final_reward");
    }
    public boolean naboo_legacy_lt_maximilian_vox_condition_completedNaboo_Rebel(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "legacy_naboo_rebel_final_reward");
    }
    public void naboo_legacy_lt_maximilian_vox_action_grantKadaaraQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, content.REBEL_PATH_OBJVAR_NAME))
        {
            removeObjVar(player, content.REBEL_PATH_OBJVAR_NAME);
        }
        if (!hasObjVar(player, content.IMPERIAL_PATH_OBJVAR_NAME))
        {
            setObjVar(player, content.IMPERIAL_PATH_OBJVAR_NAME, true);
        }
        groundquests.requestGrantQuest(player, "naboo_kadaraa_tipping_the_balance_1");
        return;
    }
    public void naboo_legacy_lt_maximilian_vox_action_givePointerToCorellia(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!groundquests.isQuestActive(player, "naboo_send_to_lt_jasper") && !groundquests.isQuestActiveOrComplete(player, "corellia_coronet_capitol_problems_missing_shipment_intro"))
        {
            groundquests.grantQuest(player, "naboo_send_to_lt_jasper");
        }
    }
    public void naboo_legacy_lt_maximilian_vox_action_grantFinalReward_Imp(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, content.REBEL_PATH_OBJVAR_NAME))
        {
            removeObjVar(player, content.REBEL_PATH_OBJVAR_NAME);
        }
        if (!hasObjVar(player, content.IMPERIAL_PATH_OBJVAR_NAME))
        {
            setObjVar(player, content.IMPERIAL_PATH_OBJVAR_NAME, true);
        }
        groundquests.sendSignal(player, "returnToLegacyContact");
        groundquests.requestGrantQuest(player, "legacy_naboo_imperial_final_reward");
        return;
    }
    public void naboo_legacy_lt_maximilian_vox_action_endFindDroidParts(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "returnToImperilOrRebelContact");
    }
    public void naboo_legacy_lt_maximilian_vox_action_endGotoKadaara(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "naboo_from_tatooine_kick");
    }
    public int naboo_legacy_lt_maximilian_vox_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_63"))
        {
            if (naboo_legacy_lt_maximilian_vox_condition__defaultCondition(player, npc))
            {
                naboo_legacy_lt_maximilian_vox_action_endFindDroidParts(player, npc);
                string_id message = new string_id(c_stringFile, "s_66");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_legacy_lt_maximilian_vox_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_legacy_lt_maximilian_vox_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_59");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_60");
                    }
                    utils.setScriptVar(player, "conversation.naboo_legacy_lt_maximilian_vox.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_legacy_lt_maximilian_vox.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_64"))
        {
            if (naboo_legacy_lt_maximilian_vox_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_65");
                utils.removeScriptVar(player, "conversation.naboo_legacy_lt_maximilian_vox.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_legacy_lt_maximilian_vox_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_59"))
        {
            if (naboo_legacy_lt_maximilian_vox_condition__defaultCondition(player, npc))
            {
                naboo_legacy_lt_maximilian_vox_action_grantFinalReward_Imp(player, npc);
                string_id message = new string_id(c_stringFile, "s_61");
                utils.removeScriptVar(player, "conversation.naboo_legacy_lt_maximilian_vox.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_60"))
        {
            if (naboo_legacy_lt_maximilian_vox_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_62");
                utils.removeScriptVar(player, "conversation.naboo_legacy_lt_maximilian_vox.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_legacy_lt_maximilian_vox_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_55"))
        {
            if (naboo_legacy_lt_maximilian_vox_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_56");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_legacy_lt_maximilian_vox_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.naboo_legacy_lt_maximilian_vox.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_legacy_lt_maximilian_vox.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_legacy_lt_maximilian_vox_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_57"))
        {
            if (naboo_legacy_lt_maximilian_vox_condition__defaultCondition(player, npc))
            {
                naboo_legacy_lt_maximilian_vox_action_endFindDroidParts(player, npc);
                string_id message = new string_id(c_stringFile, "s_58");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_legacy_lt_maximilian_vox_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_legacy_lt_maximilian_vox_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_59");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_60");
                    }
                    utils.setScriptVar(player, "conversation.naboo_legacy_lt_maximilian_vox.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_legacy_lt_maximilian_vox.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_legacy_lt_maximilian_vox_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_59"))
        {
            if (naboo_legacy_lt_maximilian_vox_condition__defaultCondition(player, npc))
            {
                naboo_legacy_lt_maximilian_vox_action_grantFinalReward_Imp(player, npc);
                string_id message = new string_id(c_stringFile, "s_61");
                utils.removeScriptVar(player, "conversation.naboo_legacy_lt_maximilian_vox.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_60"))
        {
            if (naboo_legacy_lt_maximilian_vox_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_62");
                utils.removeScriptVar(player, "conversation.naboo_legacy_lt_maximilian_vox.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_legacy_lt_maximilian_vox_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_78"))
        {
            if (naboo_legacy_lt_maximilian_vox_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_80");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_legacy_lt_maximilian_vox_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.naboo_legacy_lt_maximilian_vox.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_legacy_lt_maximilian_vox.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_84"))
        {
            if (naboo_legacy_lt_maximilian_vox_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_86");
                utils.removeScriptVar(player, "conversation.naboo_legacy_lt_maximilian_vox.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_legacy_lt_maximilian_vox_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_82"))
        {
            if (naboo_legacy_lt_maximilian_vox_condition__defaultCondition(player, npc))
            {
                naboo_legacy_lt_maximilian_vox_action_endGotoKadaara(player, npc);
                string_id message = new string_id(c_stringFile, "s_96");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_legacy_lt_maximilian_vox_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_98");
                    }
                    utils.setScriptVar(player, "conversation.naboo_legacy_lt_maximilian_vox.branchId", 21);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_legacy_lt_maximilian_vox.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_legacy_lt_maximilian_vox_handleBranch19(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_90"))
        {
            if (naboo_legacy_lt_maximilian_vox_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_92");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_legacy_lt_maximilian_vox_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.naboo_legacy_lt_maximilian_vox.branchId", 20);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_legacy_lt_maximilian_vox.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_102"))
        {
            if (naboo_legacy_lt_maximilian_vox_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_104");
                utils.removeScriptVar(player, "conversation.naboo_legacy_lt_maximilian_vox.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_legacy_lt_maximilian_vox_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_94"))
        {
            if (naboo_legacy_lt_maximilian_vox_condition__defaultCondition(player, npc))
            {
                naboo_legacy_lt_maximilian_vox_action_endGotoKadaara(player, npc);
                string_id message = new string_id(c_stringFile, "s_96");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_legacy_lt_maximilian_vox_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_98");
                    }
                    utils.setScriptVar(player, "conversation.naboo_legacy_lt_maximilian_vox.branchId", 21);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_legacy_lt_maximilian_vox.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_legacy_lt_maximilian_vox_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_98"))
        {
            if (naboo_legacy_lt_maximilian_vox_condition__defaultCondition(player, npc))
            {
                naboo_legacy_lt_maximilian_vox_action_grantKadaaraQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_100");
                utils.removeScriptVar(player, "conversation.naboo_legacy_lt_maximilian_vox.branchId");
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
            detachScript(self, "conversation.naboo_legacy_lt_maximilian_vox");
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
        detachScript(self, "conversation.naboo_legacy_lt_maximilian_vox");
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
        if (naboo_legacy_lt_maximilian_vox_condition_completedNaboo_Rebel(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_27");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (naboo_legacy_lt_maximilian_vox_condition_completedNaboo_Imp(player, npc))
        {
            naboo_legacy_lt_maximilian_vox_action_givePointerToCorellia(player, npc);
            string_id message = new string_id(c_stringFile, "s_28");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (naboo_legacy_lt_maximilian_vox_condition_hasAllDroidParts_Rebel(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_15");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_legacy_lt_maximilian_vox_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (naboo_legacy_lt_maximilian_vox_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_64");
                }
                utils.setScriptVar(player, "conversation.naboo_legacy_lt_maximilian_vox.branchId", 3);
                npcStartConversation(player, npc, "naboo_legacy_lt_maximilian_vox", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_legacy_lt_maximilian_vox_condition_hasAllDroidParts_Imp(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_11");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_legacy_lt_maximilian_vox_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.naboo_legacy_lt_maximilian_vox.branchId", 6);
                npcStartConversation(player, npc, "naboo_legacy_lt_maximilian_vox", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_legacy_lt_maximilian_vox_condition_seekingDroidParts_Rebel(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_22");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (naboo_legacy_lt_maximilian_vox_condition_seekingDroidParts_Imp(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_24");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (naboo_legacy_lt_maximilian_vox_condition_seekingRSFComputerAccess(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_26");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (naboo_legacy_lt_maximilian_vox_condition_onPanakaQuest_Rebel(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_72");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (naboo_legacy_lt_maximilian_vox_condition_onPanakaQuest_Imp(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_74");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (naboo_legacy_lt_maximilian_vox_condition_kickQuestActive_Rebel(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_76");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_legacy_lt_maximilian_vox_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (naboo_legacy_lt_maximilian_vox_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_78");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_84");
                }
                utils.setScriptVar(player, "conversation.naboo_legacy_lt_maximilian_vox.branchId", 16);
                npcStartConversation(player, npc, "naboo_legacy_lt_maximilian_vox", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_legacy_lt_maximilian_vox_condition_kickQuestActive_Imp(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_88");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_legacy_lt_maximilian_vox_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (naboo_legacy_lt_maximilian_vox_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_102");
                }
                utils.setScriptVar(player, "conversation.naboo_legacy_lt_maximilian_vox.branchId", 19);
                npcStartConversation(player, npc, "naboo_legacy_lt_maximilian_vox", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_legacy_lt_maximilian_vox_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_106");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("naboo_legacy_lt_maximilian_vox"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.naboo_legacy_lt_maximilian_vox.branchId");
        if (branchId == 3 && naboo_legacy_lt_maximilian_vox_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && naboo_legacy_lt_maximilian_vox_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && naboo_legacy_lt_maximilian_vox_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && naboo_legacy_lt_maximilian_vox_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && naboo_legacy_lt_maximilian_vox_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && naboo_legacy_lt_maximilian_vox_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && naboo_legacy_lt_maximilian_vox_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && naboo_legacy_lt_maximilian_vox_handleBranch19(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && naboo_legacy_lt_maximilian_vox_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && naboo_legacy_lt_maximilian_vox_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.naboo_legacy_lt_maximilian_vox.branchId");
        return SCRIPT_CONTINUE;
    }
}
