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

public class ep3_cheyerooto extends script.base_script
{
    public ep3_cheyerooto()
    {
    }
    public static String c_stringFile = "conversation/ep3_cheyerooto";
    public boolean ep3_cheyerooto_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean ep3_cheyerooto_condition_isReadyToCompleteWrhisch(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_sera_wrhisch_liver", "wrhischReadyToComplete");
    }
    public boolean ep3_cheyerooto_condition_hasCompletedWrhischQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "ep3_sera_wrhisch_liver");
    }
    public boolean ep3_cheyerooto_condition_isRootQuestActive(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "ep3_cheyerooto_5_rrwii_root");
    }
    public boolean ep3_cheyerooto_condition_readyToCompleteRrwii(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_cheyerooto_5_rrwii_root", "cheyerootoRrwiiComplete");
    }
    public boolean ep3_cheyerooto_condition_hasCompletedRrwiiQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "ep3_cheyerooto_5_rrwii_root");
    }
    public boolean ep3_cheyerooto_condition_isWookieeNub(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.canSpeakWookiee(player, npc);
    }
    public void ep3_cheyerooto_action_grantRootGather(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "ep3_cheyerooto_5_rrwii_root");
    }
    public void ep3_cheyerooto_action_giveWrhischReward(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "giveWrhischReward");
    }
    public void ep3_cheyerooto_action_giveRrwiiReward(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "rrwiiGiveReward");
    }
    public void ep3_cheyerooto_action_schoolWookieeNub(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.emoteWookieeConfusion(player, npc);
    }
    public int ep3_cheyerooto_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_514"))
        {
            if (ep3_cheyerooto_condition__defaultCondition(player, npc))
            {
                ep3_cheyerooto_action_giveRrwiiReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_516");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_cheyerooto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_327");
                    }
                    utils.setScriptVar(player, "conversation.ep3_cheyerooto.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_cheyerooto.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_518"))
        {
            if (ep3_cheyerooto_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_520");
                utils.removeScriptVar(player, "conversation.ep3_cheyerooto.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_cheyerooto_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_327"))
        {
            if (ep3_cheyerooto_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_329");
                utils.removeScriptVar(player, "conversation.ep3_cheyerooto.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_cheyerooto_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_524"))
        {
            if (ep3_cheyerooto_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_526");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_cheyerooto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_528");
                    }
                    utils.setScriptVar(player, "conversation.ep3_cheyerooto.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_cheyerooto.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_cheyerooto_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_528"))
        {
            if (ep3_cheyerooto_condition__defaultCondition(player, npc))
            {
                ep3_cheyerooto_action_grantRootGather(player, npc);
                string_id message = new string_id(c_stringFile, "s_530");
                utils.removeScriptVar(player, "conversation.ep3_cheyerooto.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_cheyerooto_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_534"))
        {
            if (ep3_cheyerooto_condition__defaultCondition(player, npc))
            {
                ep3_cheyerooto_action_giveWrhischReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_536");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_cheyerooto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_538");
                    }
                    utils.setScriptVar(player, "conversation.ep3_cheyerooto.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_cheyerooto.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_cheyerooto_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_538"))
        {
            if (ep3_cheyerooto_condition_isWookieeNub(player, npc))
            {
                ep3_cheyerooto_action_schoolWookieeNub(player, npc);
                string_id message = new string_id(c_stringFile, "s_207");
                utils.removeScriptVar(player, "conversation.ep3_cheyerooto.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (ep3_cheyerooto_condition_hasCompletedRrwiiQuest(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_322");
                utils.removeScriptVar(player, "conversation.ep3_cheyerooto.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (ep3_cheyerooto_condition_isRootQuestActive(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_512");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_cheyerooto_condition_readyToCompleteRrwii(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_cheyerooto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_514");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_518");
                    }
                    utils.setScriptVar(player, "conversation.ep3_cheyerooto.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_cheyerooto.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_cheyerooto_condition_hasCompletedWrhischQuest(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_522");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_cheyerooto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_524");
                    }
                    utils.setScriptVar(player, "conversation.ep3_cheyerooto.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_cheyerooto.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_cheyerooto_condition_isReadyToCompleteWrhisch(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_532");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_cheyerooto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_534");
                    }
                    utils.setScriptVar(player, "conversation.ep3_cheyerooto.branchId", 10);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_cheyerooto.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    chat.chat(npc, player, pp);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_cheyerooto_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_540");
                utils.removeScriptVar(player, "conversation.ep3_cheyerooto.branchId");
                chat.chat(npc, player, message);
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
            detachScript(self, "conversation.ep3_cheyerooto");
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
        faceTo(self, player);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        detachScript(self, "conversation.ep3_cheyerooto");
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
        if (ep3_cheyerooto_condition_isWookieeNub(player, npc))
        {
            ep3_cheyerooto_action_schoolWookieeNub(player, npc);
            string_id message = new string_id(c_stringFile, "s_207");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_cheyerooto_condition_hasCompletedRrwiiQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_322");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_cheyerooto_condition_isRootQuestActive(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_512");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_cheyerooto_condition_readyToCompleteRrwii(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_cheyerooto_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_514");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_518");
                }
                utils.setScriptVar(player, "conversation.ep3_cheyerooto.branchId", 3);
                npcStartConversation(player, npc, "ep3_cheyerooto", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_cheyerooto_condition_hasCompletedWrhischQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_522");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_cheyerooto_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_524");
                }
                utils.setScriptVar(player, "conversation.ep3_cheyerooto.branchId", 7);
                npcStartConversation(player, npc, "ep3_cheyerooto", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_cheyerooto_condition_isReadyToCompleteWrhisch(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_532");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_cheyerooto_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_534");
                }
                utils.setScriptVar(player, "conversation.ep3_cheyerooto.branchId", 10);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "ep3_cheyerooto", null, pp, responses);
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
        if (ep3_cheyerooto_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_540");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("ep3_cheyerooto"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.ep3_cheyerooto.branchId");
        if (branchId == 3 && ep3_cheyerooto_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && ep3_cheyerooto_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && ep3_cheyerooto_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && ep3_cheyerooto_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && ep3_cheyerooto_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && ep3_cheyerooto_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.ep3_cheyerooto.branchId");
        return SCRIPT_CONTINUE;
    }
}
