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
import script.library.space_quest;
import script.library.utils;

public class naboo_boss_nass extends script.base_script
{
    public naboo_boss_nass()
    {
    }
    public static String c_stringFile = "conversation/naboo_boss_nass";
    public boolean naboo_boss_nass_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean naboo_boss_nass_condition_onArtifactMauler(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "naboo_boss_nass_artifacts_mauler");
    }
    public boolean naboo_boss_nass_condition_sentToBossNass(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "legacy_naboo_droid_module_4") || groundquests.hasCompletedQuest(player, "legacy_naboo_droid_module_4");
    }
    public boolean naboo_boss_nass_condition_finishedArtifactMauler(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "naboo_boss_nass_artifacts_mauler", "boss_nass_mauler_06") || groundquests.hasCompletedQuest(player, "naboo_boss_nass_artifacts_mauler");
    }
    public boolean naboo_boss_nass_condition_onArtifactSkaak(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "naboo_boss_nass_artifacts_skaak");
    }
    public boolean naboo_boss_nass_condition_finishedArtifactSkaak(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "naboo_boss_nass_artifacts_skaak", "boss_nass_artifacts_skaak_07") || groundquests.hasCompletedQuest(player, "naboo_boss_nass_artifacts_skaak");
    }
    public boolean naboo_boss_nass_condition_onArtifactMuskeg(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "naboo_boss_nass_artifacts_muskeg");
    }
    public boolean naboo_boss_nass_condition_finishedArtifactMuskeg(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "naboo_boss_nass_artifacts_muskeg", "boss_nass_artifacts_muskeg_05") || groundquests.hasCompletedQuest(player, "naboo_boss_nass_artifacts_muskeg");
    }
    public boolean naboo_boss_nass_condition_onArtifactDarkwalker(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "naboo_boss_nass_artifacts_darkwalker");
    }
    public boolean naboo_boss_nass_condition_finishedArtifactDarkwalker(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "naboo_boss_nass_artifacts_darkwalker", "boss_nass_artifacts_darkwalker_04") || groundquests.hasCompletedQuest(player, "naboo_boss_nass_artifacts_darkwalker");
    }
    public boolean naboo_boss_nass_condition_onArtifactScattered(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "naboo_boss_nass_artifacts_scattered");
    }
    public boolean naboo_boss_nass_condition_finishedArtifactScattered(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "naboo_boss_nass_artifacts_scattered", "boss_nass_artifacts_scattered_02") || groundquests.hasCompletedQuest(player, "naboo_boss_nass_artifacts_scattered");
    }
    public boolean naboo_boss_nass_condition_onPasscodeRecon(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "naboo_boss_nass_password_recon");
    }
    public boolean naboo_boss_nass_condition_finishedPasscodeRecon(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "naboo_boss_nass_password_recon", "boss_nass_password_recon_04") || groundquests.hasCompletedQuest(player, "naboo_boss_nass_password_recon");
    }
    public boolean naboo_boss_nass_condition_finishedPasscodeCode(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "naboo_boss_nass_password_code", "boss_nass_password_code_06") || groundquests.hasCompletedQuest(player, "naboo_boss_nass_password_code");
    }
    public boolean naboo_boss_nass_condition_onPasscodeCode(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "naboo_boss_nass_password_code");
    }
    public boolean naboo_boss_nass_condition_gettingDroidPart(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "legacy_naboo_droid_module_4", "findDroidPart4_03");
    }
    public boolean naboo_boss_nass_condition_hasDroidPart(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "legacy_naboo_droid_module_4", "findDroidPart4_04") || groundquests.isTaskActive(player, "legacy_naboo_droid_module_4", "findDroidPart4_05") || groundquests.isTaskActive(player, "legacy_naboo_droid_module_4", "findDroidPart4_06") || groundquests.hasCompletedQuest(player, "legacy_naboo_droid_module_4");
    }
    public void naboo_boss_nass_action_grantArtifactMauler(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "findDroidPart4_01");
        groundquests.requestGrantQuest(player, "naboo_boss_nass_artifacts_mauler");
    }
    public void naboo_boss_nass_action_grantArtifactSkaak(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.requestGrantQuest(player, "naboo_boss_nass_artifacts_skaak");
    }
    public void naboo_boss_nass_action_grantArtifactMuskeg(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.requestGrantQuest(player, "naboo_boss_nass_artifacts_muskeg");
    }
    public void naboo_boss_nass_action_grantArtifactDarkwalker(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.requestGrantQuest(player, "naboo_boss_nass_artifacts_darkwalker");
    }
    public void naboo_boss_nass_action_grantArtifactScattered(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.requestGrantQuest(player, "naboo_boss_nass_artifacts_scattered");
    }
    public void naboo_boss_nass_action_signal_scattered_02(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "boss_nass_artifacts_scattered_02");
        groundquests.sendSignal(player, "findDroidPart4_01");
    }
    public void naboo_boss_nass_action_endArtifactDarkwalker(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "boss_nass_artifacts_darkwalker_04");
        groundquests.sendSignal(player, "findDroidPart4_01");
    }
    public void naboo_boss_nass_action_endArtifactMuskeg(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "boss_nass_artifacts_muskeg_05");
        groundquests.sendSignal(player, "findDroidPart4_01");
    }
    public void naboo_boss_nass_action_endArtifactSkaak(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "boss_nass_artifacts_skaak_07");
        groundquests.sendSignal(player, "findDroidPart4_01");
    }
    public void naboo_boss_nass_action_endArtifactMauler(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "boss_nass_mauler_06");
        groundquests.sendSignal(player, "findDroidPart4_01");
    }
    public void naboo_boss_nass_action_grantPasscodeRecon(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.requestGrantQuest(player, "naboo_boss_nass_password_recon");
    }
    public void naboo_boss_nass_action_endPasscodeRecon(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "boss_nass_password_recon_04");
        groundquests.sendSignal(player, "findDroidPart4_01");
    }
    public void naboo_boss_nass_action_grantPasscodeCode(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.requestGrantQuest(player, "naboo_boss_nass_password_code");
    }
    public void naboo_boss_nass_action_endPasscodeCode(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "boss_nass_password_code_06");
        groundquests.sendSignal(player, "findDroidPart4_01");
    }
    public void naboo_boss_nass_action_codeForDroidPart(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "findDroidPart4_02");
    }
    public int naboo_boss_nass_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_70"))
        {
            if (naboo_boss_nass_condition__defaultCondition(player, npc))
            {
                naboo_boss_nass_action_codeForDroidPart(player, npc);
                string_id message = new string_id(c_stringFile, "s_71");
                utils.removeScriptVar(player, "conversation.naboo_boss_nass.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_boss_nass_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_32"))
        {
            if (naboo_boss_nass_condition__defaultCondition(player, npc))
            {
                naboo_boss_nass_action_grantPasscodeCode(player, npc);
                string_id message = new string_id(c_stringFile, "s_67");
                utils.removeScriptVar(player, "conversation.naboo_boss_nass.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_boss_nass_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_58"))
        {
            if (naboo_boss_nass_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_59");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_boss_nass_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.naboo_boss_nass.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_boss_nass.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_boss_nass_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_33"))
        {
            if (naboo_boss_nass_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_34");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_boss_nass_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_64");
                    }
                    utils.setScriptVar(player, "conversation.naboo_boss_nass.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_boss_nass.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_boss_nass_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_64"))
        {
            if (naboo_boss_nass_condition__defaultCondition(player, npc))
            {
                naboo_boss_nass_action_grantPasscodeRecon(player, npc);
                string_id message = new string_id(c_stringFile, "s_65");
                utils.removeScriptVar(player, "conversation.naboo_boss_nass.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_boss_nass_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_55"))
        {
            if (naboo_boss_nass_condition__defaultCondition(player, npc))
            {
                naboo_boss_nass_action_grantArtifactScattered(player, npc);
                string_id message = new string_id(c_stringFile, "s_57");
                utils.removeScriptVar(player, "conversation.naboo_boss_nass.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_boss_nass_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_53"))
        {
            if (naboo_boss_nass_condition__defaultCondition(player, npc))
            {
                naboo_boss_nass_action_grantArtifactDarkwalker(player, npc);
                string_id message = new string_id(c_stringFile, "s_54");
                utils.removeScriptVar(player, "conversation.naboo_boss_nass.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_boss_nass_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_51"))
        {
            if (naboo_boss_nass_condition__defaultCondition(player, npc))
            {
                naboo_boss_nass_action_grantArtifactMuskeg(player, npc);
                string_id message = new string_id(c_stringFile, "s_52");
                utils.removeScriptVar(player, "conversation.naboo_boss_nass.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_boss_nass_handleBranch23(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_39"))
        {
            if (naboo_boss_nass_condition__defaultCondition(player, npc))
            {
                naboo_boss_nass_action_grantArtifactSkaak(player, npc);
                string_id message = new string_id(c_stringFile, "s_50");
                utils.removeScriptVar(player, "conversation.naboo_boss_nass.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_boss_nass_handleBranch26(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_74"))
        {
            if (naboo_boss_nass_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_76");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_boss_nass_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.naboo_boss_nass.branchId", 27);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_boss_nass.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_82"))
        {
            if (naboo_boss_nass_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_84");
                utils.removeScriptVar(player, "conversation.naboo_boss_nass.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_boss_nass_handleBranch27(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_78"))
        {
            if (naboo_boss_nass_condition__defaultCondition(player, npc))
            {
                naboo_boss_nass_action_grantArtifactMauler(player, npc);
                string_id message = new string_id(c_stringFile, "s_80");
                utils.removeScriptVar(player, "conversation.naboo_boss_nass.branchId");
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
            detachScript(self, "conversation.naboo_boss_nass");
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
        faceTo(self, player);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        detachScript(self, "conversation.naboo_boss_nass");
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
        if (naboo_boss_nass_condition_hasDroidPart(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_72");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (naboo_boss_nass_condition_gettingDroidPart(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_73");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (naboo_boss_nass_condition_finishedPasscodeCode(player, npc))
        {
            naboo_boss_nass_action_endPasscodeCode(player, npc);
            string_id message = new string_id(c_stringFile, "s_69");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_boss_nass_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_70");
                }
                utils.setScriptVar(player, "conversation.naboo_boss_nass.branchId", 3);
                npcStartConversation(player, npc, "naboo_boss_nass", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_boss_nass_condition_onPasscodeCode(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_68");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (naboo_boss_nass_condition_finishedPasscodeRecon(player, npc))
        {
            naboo_boss_nass_action_endPasscodeRecon(player, npc);
            string_id message = new string_id(c_stringFile, "s_41");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_boss_nass_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.naboo_boss_nass.branchId", 6);
                npcStartConversation(player, npc, "naboo_boss_nass", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_boss_nass_condition_onPasscodeRecon(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_66");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (naboo_boss_nass_condition_finishedArtifactScattered(player, npc))
        {
            naboo_boss_nass_action_signal_scattered_02(player, npc);
            string_id message = new string_id(c_stringFile, "s_44");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_boss_nass_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_58");
                }
                utils.setScriptVar(player, "conversation.naboo_boss_nass.branchId", 9);
                npcStartConversation(player, npc, "naboo_boss_nass", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_boss_nass_condition_onArtifactScattered(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_42");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (naboo_boss_nass_condition_finishedArtifactDarkwalker(player, npc))
        {
            naboo_boss_nass_action_endArtifactDarkwalker(player, npc);
            string_id message = new string_id(c_stringFile, "s_45");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_boss_nass_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.naboo_boss_nass.branchId", 14);
                npcStartConversation(player, npc, "naboo_boss_nass", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_boss_nass_condition_onArtifactDarkwalker(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_46");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (naboo_boss_nass_condition_finishedArtifactMuskeg(player, npc))
        {
            naboo_boss_nass_action_endArtifactMuskeg(player, npc);
            string_id message = new string_id(c_stringFile, "s_43");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_boss_nass_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_53");
                }
                utils.setScriptVar(player, "conversation.naboo_boss_nass.branchId", 17);
                npcStartConversation(player, npc, "naboo_boss_nass", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_boss_nass_condition_onArtifactMuskeg(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_47");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (naboo_boss_nass_condition_finishedArtifactSkaak(player, npc))
        {
            naboo_boss_nass_action_endArtifactSkaak(player, npc);
            string_id message = new string_id(c_stringFile, "s_48");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_boss_nass_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_51");
                }
                utils.setScriptVar(player, "conversation.naboo_boss_nass.branchId", 20);
                npcStartConversation(player, npc, "naboo_boss_nass", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_boss_nass_condition_onArtifactSkaak(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_49");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (naboo_boss_nass_condition_finishedArtifactMauler(player, npc))
        {
            naboo_boss_nass_action_endArtifactMauler(player, npc);
            string_id message = new string_id(c_stringFile, "s_37");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_boss_nass_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_39");
                }
                utils.setScriptVar(player, "conversation.naboo_boss_nass.branchId", 23);
                npcStartConversation(player, npc, "naboo_boss_nass", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_boss_nass_condition_onArtifactMauler(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_60");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (naboo_boss_nass_condition_sentToBossNass(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_62");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_boss_nass_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (naboo_boss_nass_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_74");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_82");
                }
                utils.setScriptVar(player, "conversation.naboo_boss_nass.branchId", 26);
                npcStartConversation(player, npc, "naboo_boss_nass", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_boss_nass_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_86");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("naboo_boss_nass"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.naboo_boss_nass.branchId");
        if (branchId == 3 && naboo_boss_nass_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && naboo_boss_nass_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && naboo_boss_nass_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && naboo_boss_nass_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && naboo_boss_nass_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && naboo_boss_nass_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && naboo_boss_nass_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && naboo_boss_nass_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && naboo_boss_nass_handleBranch23(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && naboo_boss_nass_handleBranch26(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 27 && naboo_boss_nass_handleBranch27(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.naboo_boss_nass.branchId");
        return SCRIPT_CONTINUE;
    }
}
