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

public class ep3_etyyy_ehartt_brihnt extends script.base_script
{
    public ep3_etyyy_ehartt_brihnt()
    {
    }
    public static String c_stringFile = "conversation/ep3_etyyy_ehartt_brihnt";
    public boolean ep3_etyyy_ehartt_brihnt_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean ep3_etyyy_ehartt_brihnt_condition_hasCompletedAllQuests(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedQuest(player, "ep3_hunt_ehartt_collect_walluga_claws"));
    }
    public boolean ep3_etyyy_ehartt_brihnt_condition_finishedCollectingClaws(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "ep3_hunt_ehartt_collect_walluga_claws", "ehartt_wallugaClaws"));
    }
    public boolean ep3_etyyy_ehartt_brihnt_condition_speakToEhartt(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "ep3_hunt_ehartt_collect_walluga_claws", "ehartt_talkToEhartt"));
    }
    public boolean ep3_etyyy_ehartt_brihnt_condition_isCollectingWallugaClaws(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "ep3_hunt_ehartt_collect_walluga_claws", "ehartt_collectingWallugaClaws"));
    }
    public boolean ep3_etyyy_ehartt_brihnt_condition_killedStoneleg(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "ep3_hunt_loot_stoneleg_killed");
    }
    public boolean ep3_etyyy_ehartt_brihnt_condition_killedStonelegPlusAll(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "ep3_hunt_loot_brightclaw_killed") && groundquests.hasCompletedQuest(player, "ep3_hunt_loot_silkthrower_killed") && groundquests.hasCompletedQuest(player, "ep3_hunt_loot_paleclaw_killed") && groundquests.hasCompletedQuest(player, "ep3_hunt_loot_spiketop_killed") && groundquests.hasCompletedQuest(player, "ep3_hunt_loot_greyclimber_killed");
    }
    public void ep3_etyyy_ehartt_brihnt_action_speakWithSordaan(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "ehartt_wallugaClaws");
        groundquests.grantQuest(player, "ep3_hunt_sordaan_seek_sordaan");
        groundquests.sendSignal(player, "sordaan_eharttSendsYou");
    }
    public void ep3_etyyy_ehartt_brihnt_action_stonelegReward(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "lootQuest_defeatedStoneleg");
    }
    public void ep3_etyyy_ehartt_brihnt_action_collectWallugaClaws(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "ehartt_speakWithEhartt");
    }
    public void ep3_etyyy_ehartt_brihnt_action_stonelegRewardPlusAll(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "lootQuest_defeatedStoneleg");
        groundquests.grantQuest(player, "ep3_hunt_loot_completed_all");
    }
    public int ep3_etyyy_ehartt_brihnt_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1797"))
        {
            if (ep3_etyyy_ehartt_brihnt_condition_killedStonelegPlusAll(player, npc))
            {
                ep3_etyyy_ehartt_brihnt_action_stonelegRewardPlusAll(player, npc);
                string_id message = new string_id(c_stringFile, "s_1839");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_ehartt_brihnt_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1841");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_ehartt_brihnt.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_ehartt_brihnt.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_ehartt_brihnt_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_ehartt_brihnt_action_stonelegReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_1845");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_ehartt_brihnt.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_ehartt_brihnt_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1801"))
        {
            if (ep3_etyyy_ehartt_brihnt_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_ehartt_brihnt_action_speakWithSordaan(player, npc);
                string_id message = new string_id(c_stringFile, "s_1803");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_ehartt_brihnt.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1805"))
        {
            if (ep3_etyyy_ehartt_brihnt_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1807");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_ehartt_brihnt.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1809"))
        {
            if (ep3_etyyy_ehartt_brihnt_condition_killedStonelegPlusAll(player, npc))
            {
                ep3_etyyy_ehartt_brihnt_action_stonelegRewardPlusAll(player, npc);
                string_id message = new string_id(c_stringFile, "s_1839");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_ehartt_brihnt_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1841");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_ehartt_brihnt.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_ehartt_brihnt.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_ehartt_brihnt_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_ehartt_brihnt_action_stonelegReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_1845");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_ehartt_brihnt.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_ehartt_brihnt_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1813"))
        {
            if (ep3_etyyy_ehartt_brihnt_condition_killedStonelegPlusAll(player, npc))
            {
                ep3_etyyy_ehartt_brihnt_action_stonelegRewardPlusAll(player, npc);
                string_id message = new string_id(c_stringFile, "s_1839");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_ehartt_brihnt_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1841");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_ehartt_brihnt.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_ehartt_brihnt.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_ehartt_brihnt_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_ehartt_brihnt_action_stonelegReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_1845");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_ehartt_brihnt.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_ehartt_brihnt_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1817"))
        {
            if (ep3_etyyy_ehartt_brihnt_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1819");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_ehartt_brihnt_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_ehartt_brihnt_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1821");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1825");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_ehartt_brihnt.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_ehartt_brihnt.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1829"))
        {
            if (ep3_etyyy_ehartt_brihnt_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1831");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_ehartt_brihnt.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1833"))
        {
            if (ep3_etyyy_ehartt_brihnt_condition_killedStonelegPlusAll(player, npc))
            {
                ep3_etyyy_ehartt_brihnt_action_stonelegRewardPlusAll(player, npc);
                string_id message = new string_id(c_stringFile, "s_1839");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_ehartt_brihnt_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1841");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_ehartt_brihnt.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_ehartt_brihnt.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_ehartt_brihnt_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_ehartt_brihnt_action_stonelegReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_1845");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_ehartt_brihnt.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_ehartt_brihnt_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1821"))
        {
            if (ep3_etyyy_ehartt_brihnt_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_ehartt_brihnt_action_collectWallugaClaws(player, npc);
                string_id message = new string_id(c_stringFile, "s_1823");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_ehartt_brihnt.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1825"))
        {
            if (ep3_etyyy_ehartt_brihnt_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1827");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_ehartt_brihnt.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_ehartt_brihnt_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1837"))
        {
            if (ep3_etyyy_ehartt_brihnt_condition_killedStonelegPlusAll(player, npc))
            {
                ep3_etyyy_ehartt_brihnt_action_stonelegRewardPlusAll(player, npc);
                string_id message = new string_id(c_stringFile, "s_1839");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_ehartt_brihnt_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1841");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_ehartt_brihnt.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_ehartt_brihnt.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_ehartt_brihnt_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_ehartt_brihnt_action_stonelegReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_1845");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_ehartt_brihnt.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_ehartt_brihnt_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1841"))
        {
            if (ep3_etyyy_ehartt_brihnt_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1843");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_ehartt_brihnt.branchId");
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
            detachScript(self, "conversation.ep3_etyyy_ehartt_brihnt");
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
        detachScript(self, "conversation.ep3_etyyy_ehartt_brihnt");
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
        if (ep3_etyyy_ehartt_brihnt_condition_hasCompletedAllQuests(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1795");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_ehartt_brihnt_condition_killedStoneleg(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1797");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_ehartt_brihnt.branchId", 1);
                npcStartConversation(player, npc, "ep3_etyyy_ehartt_brihnt", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_ehartt_brihnt_condition_finishedCollectingClaws(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1799");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_ehartt_brihnt_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_etyyy_ehartt_brihnt_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (ep3_etyyy_ehartt_brihnt_condition_killedStoneleg(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1801");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1805");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1809");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_ehartt_brihnt.branchId", 2);
                npcStartConversation(player, npc, "ep3_etyyy_ehartt_brihnt", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_ehartt_brihnt_condition_isCollectingWallugaClaws(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1811");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_ehartt_brihnt_condition_killedStoneleg(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1813");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_ehartt_brihnt.branchId", 5);
                npcStartConversation(player, npc, "ep3_etyyy_ehartt_brihnt", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_ehartt_brihnt_condition_speakToEhartt(player, npc))
        {
            doAnimationAction(npc, "greet");
            string_id message = new string_id(c_stringFile, "s_1815");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_ehartt_brihnt_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_etyyy_ehartt_brihnt_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (ep3_etyyy_ehartt_brihnt_condition_killedStoneleg(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1817");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1829");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1833");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_ehartt_brihnt.branchId", 6);
                npcStartConversation(player, npc, "ep3_etyyy_ehartt_brihnt", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_ehartt_brihnt_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1835");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_ehartt_brihnt_condition_killedStoneleg(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1837");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_ehartt_brihnt.branchId", 11);
                npcStartConversation(player, npc, "ep3_etyyy_ehartt_brihnt", message, responses);
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
        if (!conversationId.equals("ep3_etyyy_ehartt_brihnt"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.ep3_etyyy_ehartt_brihnt.branchId");
        if (branchId == 1 && ep3_etyyy_ehartt_brihnt_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && ep3_etyyy_ehartt_brihnt_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && ep3_etyyy_ehartt_brihnt_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && ep3_etyyy_ehartt_brihnt_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && ep3_etyyy_ehartt_brihnt_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && ep3_etyyy_ehartt_brihnt_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && ep3_etyyy_ehartt_brihnt_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.ep3_etyyy_ehartt_brihnt.branchId");
        return SCRIPT_CONTINUE;
    }
}
