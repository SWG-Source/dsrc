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
import script.library.groundquests;
import script.library.utils;

public class ep3_rryatt_krepauk extends script.base_script
{
    public ep3_rryatt_krepauk()
    {
    }
    public static String c_stringFile = "conversation/ep3_rryatt_krepauk";
    public boolean ep3_rryatt_krepauk_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean ep3_rryatt_krepauk_condition_finishedKatarnHunt(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "ep3_rryatt_krepauk_defeat_katarn", "krepauk_katarnHuntCompleted") || groundquests.hasCompletedQuest(player, "ep3_rryatt_krepauk_defeat_katarn"));
    }
    public boolean ep3_rryatt_krepauk_condition_finishedEliteMinstygarHunt(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "ep3_rryatt_krepauk_kill_elite_minstyngar", "krepauk_eliteMinstyngarHuntCompleted") || groundquests.hasCompletedQuest(player, "ep3_rryatt_krepauk_kill_elite_minstyngar"));
    }
    public boolean ep3_rryatt_krepauk_condition_finishedFeralWookieeHunt(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "ep3_rryatt_krepauk_cleanse_feral_wookiees", "krepauk_feralWookieesCleansed") || groundquests.hasCompletedQuest(player, "ep3_rryatt_krepauk_cleanse_feral_wookiees"));
    }
    public boolean ep3_rryatt_krepauk_condition_finishedExJediHunt(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "ep3_rryatt_krepauk_defeat_exjedi", "krepauk_exJediDefeated") || groundquests.hasCompletedQuest(player, "ep3_rryatt_krepauk_defeat_exjedi"));
    }
    public boolean ep3_rryatt_krepauk_condition_finishedWallugaHunt(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "ep3_rryatt_krepauk_kill_walluga_smashers", "krepauk_completedWallugaHunt") || groundquests.hasCompletedQuest(player, "ep3_rryatt_krepauk_kill_walluga_smashers"));
    }
    public boolean ep3_rryatt_krepauk_condition_onKatarnHunt(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_rryatt_krepauk_defeat_katarn", "krepauk_huntKatarn");
    }
    public boolean ep3_rryatt_krepauk_condition_onEliteMinstyngarHunt(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_rryatt_krepauk_kill_elite_minstyngar", "krepauk_huntEliteMinstyngar");
    }
    public boolean ep3_rryatt_krepauk_condition_onFeralWookieeHunt(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_rryatt_krepauk_cleanse_feral_wookiees", "krepauk_killFeralWookiees");
    }
    public boolean ep3_rryatt_krepauk_condition_onExJediHunt(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_rryatt_krepauk_defeat_exjedi", "krepauk_defeatExJedi");
    }
    public boolean ep3_rryatt_krepauk_condition_onWallugaHunt(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_rryatt_krepauk_kill_walluga_smashers", "krepauk_huntWallugaSmashers");
    }
    public boolean ep3_rryatt_krepauk_condition_cannotSpeakWookiee(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.canSpeakWookiee(player, npc);
    }
    public void ep3_rryatt_krepauk_action_cleanseFeralWookiees(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "ep3_rryatt_krepauk_cleanse_feral_wookiees");
    }
    public void ep3_rryatt_krepauk_action_killEliteMinstyngar(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "ep3_rryatt_krepauk_kill_elite_minstyngar");
    }
    public void ep3_rryatt_krepauk_action_killWallugas(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "ep3_rryatt_krepauk_kill_walluga_smashers");
    }
    public void ep3_rryatt_krepauk_action_defeatExJedi(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "ep3_rryatt_krepauk_defeat_exjedi");
    }
    public void ep3_rryatt_krepauk_action_defeatKatarn(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "ep3_rryatt_krepauk_defeat_katarn");
    }
    public void ep3_rryatt_krepauk_action_doneWallugas(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "krepauk_completedWallugaHunt");
    }
    public void ep3_rryatt_krepauk_action_doneExJedi(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "krepauk_exJediDefeated");
    }
    public void ep3_rryatt_krepauk_action_doneFeralWookiees(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "krepauk_feralWookieesCleansed");
    }
    public void ep3_rryatt_krepauk_action_doneEliteMinstyngar(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "krepauk_eliteMinstyngarHuntCompleted");
    }
    public void ep3_rryatt_krepauk_action_doneKatarn(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!badge.hasBadge(player, "bdg_kash_katarn"))
        {
            badge.grantBadge(player, "bdg_kash_katarn");
        }
        groundquests.sendSignal(player, "krepauk_katarnHuntCompleted");
    }
    public void ep3_rryatt_krepauk_action_thinkWookieeConfusion(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.emoteWookieeConfusion(player, npc);
    }
    public int ep3_rryatt_krepauk_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_315"))
        {
            if (ep3_rryatt_krepauk_condition__defaultCondition(player, npc))
            {
                ep3_rryatt_krepauk_action_defeatKatarn(player, npc);
                string_id message = new string_id(c_stringFile, "s_317");
                utils.removeScriptVar(player, "conversation.ep3_rryatt_krepauk.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_319"))
        {
            if (ep3_rryatt_krepauk_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_321");
                utils.removeScriptVar(player, "conversation.ep3_rryatt_krepauk.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_rryatt_krepauk_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_327"))
        {
            if (ep3_rryatt_krepauk_condition__defaultCondition(player, npc))
            {
                ep3_rryatt_krepauk_action_killEliteMinstyngar(player, npc);
                string_id message = new string_id(c_stringFile, "s_329");
                utils.removeScriptVar(player, "conversation.ep3_rryatt_krepauk.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_331"))
        {
            if (ep3_rryatt_krepauk_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_333");
                utils.removeScriptVar(player, "conversation.ep3_rryatt_krepauk.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_rryatt_krepauk_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_339"))
        {
            if (ep3_rryatt_krepauk_condition__defaultCondition(player, npc))
            {
                ep3_rryatt_krepauk_action_cleanseFeralWookiees(player, npc);
                string_id message = new string_id(c_stringFile, "s_341");
                utils.removeScriptVar(player, "conversation.ep3_rryatt_krepauk.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_343"))
        {
            if (ep3_rryatt_krepauk_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_345");
                utils.removeScriptVar(player, "conversation.ep3_rryatt_krepauk.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_rryatt_krepauk_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_351"))
        {
            if (ep3_rryatt_krepauk_condition__defaultCondition(player, npc))
            {
                ep3_rryatt_krepauk_action_defeatExJedi(player, npc);
                string_id message = new string_id(c_stringFile, "s_353");
                utils.removeScriptVar(player, "conversation.ep3_rryatt_krepauk.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_355"))
        {
            if (ep3_rryatt_krepauk_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_357");
                utils.removeScriptVar(player, "conversation.ep3_rryatt_krepauk.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_rryatt_krepauk_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_363"))
        {
            if (ep3_rryatt_krepauk_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_365");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_rryatt_krepauk_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_rryatt_krepauk_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_368");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_372");
                    }
                    utils.setScriptVar(player, "conversation.ep3_rryatt_krepauk.branchId", 21);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_rryatt_krepauk.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_376"))
        {
            if (ep3_rryatt_krepauk_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_378");
                utils.removeScriptVar(player, "conversation.ep3_rryatt_krepauk.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_rryatt_krepauk_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_368"))
        {
            if (ep3_rryatt_krepauk_condition__defaultCondition(player, npc))
            {
                ep3_rryatt_krepauk_action_killWallugas(player, npc);
                string_id message = new string_id(c_stringFile, "s_370");
                utils.removeScriptVar(player, "conversation.ep3_rryatt_krepauk.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_372"))
        {
            if (ep3_rryatt_krepauk_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_374");
                utils.removeScriptVar(player, "conversation.ep3_rryatt_krepauk.branchId");
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
            detachScript(self, "conversation.ep3_rryatt_krepauk");
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
        detachScript(self, "conversation.ep3_rryatt_krepauk");
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
        if (ep3_rryatt_krepauk_condition_cannotSpeakWookiee(player, npc))
        {
            ep3_rryatt_krepauk_action_thinkWookieeConfusion(player, npc);
            string_id message = new string_id(c_stringFile, "s_307");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_rryatt_krepauk_condition_finishedKatarnHunt(player, npc))
        {
            ep3_rryatt_krepauk_action_doneKatarn(player, npc);
            string_id message = new string_id(c_stringFile, "s_309");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_rryatt_krepauk_condition_onKatarnHunt(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_311");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_rryatt_krepauk_condition_finishedEliteMinstygarHunt(player, npc))
        {
            ep3_rryatt_krepauk_action_doneEliteMinstyngar(player, npc);
            string_id message = new string_id(c_stringFile, "s_313");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_rryatt_krepauk_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_rryatt_krepauk_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_315");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_319");
                }
                utils.setScriptVar(player, "conversation.ep3_rryatt_krepauk.branchId", 4);
                npcStartConversation(player, npc, "ep3_rryatt_krepauk", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_rryatt_krepauk_condition_onEliteMinstyngarHunt(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_323");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_rryatt_krepauk_condition_finishedFeralWookieeHunt(player, npc))
        {
            ep3_rryatt_krepauk_action_doneFeralWookiees(player, npc);
            string_id message = new string_id(c_stringFile, "s_325");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_rryatt_krepauk_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_rryatt_krepauk_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_327");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_331");
                }
                utils.setScriptVar(player, "conversation.ep3_rryatt_krepauk.branchId", 8);
                npcStartConversation(player, npc, "ep3_rryatt_krepauk", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_rryatt_krepauk_condition_onFeralWookieeHunt(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_335");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_rryatt_krepauk_condition_finishedExJediHunt(player, npc))
        {
            ep3_rryatt_krepauk_action_doneExJedi(player, npc);
            string_id message = new string_id(c_stringFile, "s_337");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_rryatt_krepauk_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_rryatt_krepauk_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_339");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_343");
                }
                utils.setScriptVar(player, "conversation.ep3_rryatt_krepauk.branchId", 12);
                npcStartConversation(player, npc, "ep3_rryatt_krepauk", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_rryatt_krepauk_condition_onExJediHunt(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_347");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_rryatt_krepauk_condition_finishedWallugaHunt(player, npc))
        {
            ep3_rryatt_krepauk_action_doneWallugas(player, npc);
            string_id message = new string_id(c_stringFile, "s_349");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_rryatt_krepauk_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_rryatt_krepauk_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_351");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_355");
                }
                utils.setScriptVar(player, "conversation.ep3_rryatt_krepauk.branchId", 16);
                npcStartConversation(player, npc, "ep3_rryatt_krepauk", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_rryatt_krepauk_condition_onWallugaHunt(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_359");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_rryatt_krepauk_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_361");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_rryatt_krepauk_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_rryatt_krepauk_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_363");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_376");
                }
                utils.setScriptVar(player, "conversation.ep3_rryatt_krepauk.branchId", 20);
                npcStartConversation(player, npc, "ep3_rryatt_krepauk", message, responses);
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
        if (!conversationId.equals("ep3_rryatt_krepauk"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.ep3_rryatt_krepauk.branchId");
        if (branchId == 4 && ep3_rryatt_krepauk_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && ep3_rryatt_krepauk_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && ep3_rryatt_krepauk_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && ep3_rryatt_krepauk_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && ep3_rryatt_krepauk_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && ep3_rryatt_krepauk_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.ep3_rryatt_krepauk.branchId");
        return SCRIPT_CONTINUE;
    }
}
