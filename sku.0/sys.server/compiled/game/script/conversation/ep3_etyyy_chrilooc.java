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
import script.library.money;
import script.library.space_quest;
import script.library.utils;

public class ep3_etyyy_chrilooc extends script.base_script
{
    public ep3_etyyy_chrilooc()
    {
    }
    public static String c_stringFile = "conversation/ep3_etyyy_chrilooc";
    public boolean ep3_etyyy_chrilooc_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean ep3_etyyy_chrilooc_condition_hasCompletedSmithQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedQuest(player, "ep3_hunt_chrilooc_seek_johnson"));
    }
    public boolean ep3_etyyy_chrilooc_condition_hasCompletedMedicalQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasWonQuest(player, "recovery", "ep3_hunting_chrilooc_medical_supplies"));
    }
    public boolean ep3_etyyy_chrilooc_condition_hasCompletedKerssocQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_hunt_chrilooc_seek_rodians", "chrilooc_talkToChrilooc") || groundquests.isTaskActive(player, "ep3_hunt_chrilooc_seek_rodians_02", "chrilooc_talkToChrilooc_02");
    }
    public boolean ep3_etyyy_chrilooc_condition_isGettingMedicalSupplies(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasQuest(player, "recovery", "ep3_hunting_chrilooc_medical_supplies"));
    }
    public boolean ep3_etyyy_chrilooc_condition_isSpeakingWithKerssoc(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_hunt_chrilooc_seek_rodians", "chrilooc_gainEtyyyEntry") || groundquests.isTaskActive(player, "ep3_hunt_chrilooc_seek_rodians_02", "chrilooc_gainEtyyyEntry_02");
    }
    public boolean ep3_etyyy_chrilooc_condition_fromWrelaac(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "ep3_hunt_wrelaac_to_chrilooc");
    }
    public boolean ep3_etyyy_chrilooc_condition_foundBrodyJohnson(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "ep3_hunt_johnson_brody_johnson");
    }
    public boolean ep3_etyyy_chrilooc_condition_alreadyOnKerssocQuests(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "ep3_hunt_kerssoc_bantha_pelts") || groundquests.hasCompletedQuest(player, "ep3_hunt_kerssoc_bantha_pelts");
    }
    public boolean ep3_etyyy_chrilooc_condition_hasEtyyyAccess(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "ep3_hunt_kerssoc_enter_etyyy") || groundquests.hasCompletedQuest(player, "ep3_hunt_kerssoc_enter_etyyy");
    }
    public boolean ep3_etyyy_chrilooc_condition_failedGettingMedSupplies(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasFailedQuest(player, "recovery", "ep3_hunting_chrilooc_medical_supplies") || space_quest.hasAbortedQuest(player, "recovery", "ep3_hunting_chrilooc_medical_supplies");
    }
    public boolean ep3_etyyy_chrilooc_condition_cannotSpeakWookiee(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.canSpeakWookiee(player, npc);
    }
    public boolean ep3_etyyy_chrilooc_condition_alreadyHasSpaceMission(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasQuest(player);
    }
    public void ep3_etyyy_chrilooc_action_speakWithSmith(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "chrilooc_talkToChrilooc");
        groundquests.grantQuest(player, "ep3_hunt_chrilooc_seek_johnson");
    }
    public void ep3_etyyy_chrilooc_action_continueWithKerssoc(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "ep3_hunt_chrilooc_seek_rodians_02");
    }
    public void ep3_etyyy_chrilooc_action_getMedicalSupplies(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isQuestActive(player, "ep3_hunt_wrelaac_to_chrilooc"))
        {
            groundquests.sendSignal(player, "wrelaac_talkToChrilooc");
        }
        space_quest.clearQuestFlags(player, "recovery", "ep3_hunting_chrilooc_medical_supplies");
        space_quest.grantQuest(player, "recovery", "ep3_hunting_chrilooc_medical_supplies");
    }
    public void ep3_etyyy_chrilooc_action_speakWithKerssoc(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "ep3_hunt_chrilooc_seek_rodians");
    }
    public void ep3_etyyy_chrilooc_action_thinkWookieeConfusion(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.emoteWookieeConfusion(player, npc);
    }
    public int ep3_etyyy_chrilooc_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_372"))
        {
            if (ep3_etyyy_chrilooc_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_374");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_chrilooc_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_chrilooc_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_376");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_380");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_chrilooc.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_chrilooc.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_chrilooc_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_376"))
        {
            if (ep3_etyyy_chrilooc_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_chrilooc_action_speakWithSmith(player, npc);
                string_id message = new string_id(c_stringFile, "s_378");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_chrilooc.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_380"))
        {
            if (ep3_etyyy_chrilooc_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_382");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_chrilooc.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_chrilooc_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_386"))
        {
            if (ep3_etyyy_chrilooc_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_388");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_chrilooc.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_chrilooc_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_392"))
        {
            if (ep3_etyyy_chrilooc_condition_alreadyOnKerssocQuests(player, npc))
            {
                ep3_etyyy_chrilooc_action_continueWithKerssoc(player, npc);
                string_id message = new string_id(c_stringFile, "s_394");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_chrilooc.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_chrilooc_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_396");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_chrilooc_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_chrilooc_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_398");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_402");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_chrilooc.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_chrilooc.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_406"))
        {
            if (ep3_etyyy_chrilooc_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_408");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_chrilooc_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_372");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_chrilooc.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_chrilooc.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_410"))
        {
            if (ep3_etyyy_chrilooc_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_412");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_chrilooc.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_chrilooc_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_398"))
        {
            if (ep3_etyyy_chrilooc_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_chrilooc_action_speakWithKerssoc(player, npc);
                string_id message = new string_id(c_stringFile, "s_400");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_chrilooc.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_402"))
        {
            if (ep3_etyyy_chrilooc_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_404");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_chrilooc.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_chrilooc_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_372"))
        {
            if (ep3_etyyy_chrilooc_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_374");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_chrilooc_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_chrilooc_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_376");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_380");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_chrilooc.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_chrilooc.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_chrilooc_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_416"))
        {
            if (ep3_etyyy_chrilooc_condition_alreadyHasSpaceMission(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_418");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_chrilooc.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_chrilooc_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_chrilooc_action_getMedicalSupplies(player, npc);
                string_id message = new string_id(c_stringFile, "s_420");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_chrilooc.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_422"))
        {
            if (ep3_etyyy_chrilooc_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_424");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_chrilooc.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_chrilooc_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_428"))
        {
            if (ep3_etyyy_chrilooc_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_430");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_chrilooc.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_chrilooc_handleBranch23(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_434"))
        {
            if (ep3_etyyy_chrilooc_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_436");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_chrilooc_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_chrilooc_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_438");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_452");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_chrilooc.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_chrilooc.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_456"))
        {
            if (ep3_etyyy_chrilooc_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_458");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_chrilooc.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_chrilooc_handleBranch24(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_438"))
        {
            if (ep3_etyyy_chrilooc_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_440");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_chrilooc_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_chrilooc_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_442");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_448");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_chrilooc.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_chrilooc.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_452"))
        {
            if (ep3_etyyy_chrilooc_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_454");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_chrilooc.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_chrilooc_handleBranch25(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_442"))
        {
            if (ep3_etyyy_chrilooc_condition_alreadyHasSpaceMission(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_444");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_chrilooc.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_chrilooc_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_chrilooc_action_getMedicalSupplies(player, npc);
                string_id message = new string_id(c_stringFile, "s_446");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_chrilooc.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_448"))
        {
            if (ep3_etyyy_chrilooc_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_450");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_chrilooc.branchId");
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
            detachScript(self, "conversation.ep3_etyyy_chrilooc");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
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
        detachScript(self, "conversation.ep3_etyyy_chrilooc");
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
        if (ep3_etyyy_chrilooc_condition_cannotSpeakWookiee(player, npc))
        {
            ep3_etyyy_chrilooc_action_thinkWookieeConfusion(player, npc);
            string_id message = new string_id(c_stringFile, "s_364");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_chrilooc_condition_foundBrodyJohnson(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_366");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_chrilooc_condition_hasCompletedSmithQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_368");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_chrilooc_condition_hasCompletedKerssocQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_370");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_chrilooc_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_372");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_chrilooc.branchId", 4);
                npcStartConversation(player, npc, "ep3_etyyy_chrilooc", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_chrilooc_condition_isSpeakingWithKerssoc(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_384");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_chrilooc_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_386");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_chrilooc.branchId", 8);
                npcStartConversation(player, npc, "ep3_etyyy_chrilooc", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_chrilooc_condition_hasCompletedMedicalQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_390");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (!ep3_etyyy_chrilooc_condition_hasEtyyyAccess(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_etyyy_chrilooc_condition_hasEtyyyAccess(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (ep3_etyyy_chrilooc_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_392");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_406");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_410");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_chrilooc.branchId", 10);
                npcStartConversation(player, npc, "ep3_etyyy_chrilooc", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_chrilooc_condition_failedGettingMedSupplies(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_414");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_chrilooc_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_etyyy_chrilooc_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_416");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_422");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_chrilooc.branchId", 17);
                npcStartConversation(player, npc, "ep3_etyyy_chrilooc", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_chrilooc_condition_isGettingMedicalSupplies(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_426");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_chrilooc_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_428");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_chrilooc.branchId", 21);
                npcStartConversation(player, npc, "ep3_etyyy_chrilooc", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_chrilooc_condition_fromWrelaac(player, npc))
        {
            doAnimationAction(npc, "greet");
            string_id message = new string_id(c_stringFile, "s_432");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_chrilooc_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_etyyy_chrilooc_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_434");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_456");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_chrilooc.branchId", 23);
                npcStartConversation(player, npc, "ep3_etyyy_chrilooc", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_chrilooc_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_460");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("ep3_etyyy_chrilooc"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.ep3_etyyy_chrilooc.branchId");
        if (branchId == 4 && ep3_etyyy_chrilooc_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && ep3_etyyy_chrilooc_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && ep3_etyyy_chrilooc_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && ep3_etyyy_chrilooc_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && ep3_etyyy_chrilooc_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && ep3_etyyy_chrilooc_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && ep3_etyyy_chrilooc_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && ep3_etyyy_chrilooc_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && ep3_etyyy_chrilooc_handleBranch23(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && ep3_etyyy_chrilooc_handleBranch24(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && ep3_etyyy_chrilooc_handleBranch25(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.ep3_etyyy_chrilooc.branchId");
        return SCRIPT_CONTINUE;
    }
}
