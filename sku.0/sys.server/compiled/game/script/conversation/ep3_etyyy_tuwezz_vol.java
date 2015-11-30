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

public class ep3_etyyy_tuwezz_vol extends script.base_script
{
    public ep3_etyyy_tuwezz_vol()
    {
    }
    public static String c_stringFile = "conversation/ep3_etyyy_tuwezz_vol";
    public boolean ep3_etyyy_tuwezz_vol_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean ep3_etyyy_tuwezz_vol_condition_hasCompletedTuwezzQuests(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedQuest(player, "ep3_hunt_tuwezz_collect_uller_horns"));
    }
    public boolean ep3_etyyy_tuwezz_vol_condition_killedSpiketop(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "ep3_hunt_loot_spiketop_killed");
    }
    public boolean ep3_etyyy_tuwezz_vol_condition_finishedHuntingDiseasedUller(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "ep3_hunt_tuwezz_kill_diseased_ullers", "tuwezz_diseasedUllersDone") || groundquests.hasCompletedQuest(player, "ep3_hunt_tuwezz_kill_diseased_ullers"));
    }
    public boolean ep3_etyyy_tuwezz_vol_condition_isHuntingDiseasedUller(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "ep3_hunt_tuwezz_kill_diseased_ullers", "tuwezz_huntingDiseasedUllers"));
    }
    public boolean ep3_etyyy_tuwezz_vol_condition_isCollectingUllerHorns(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "ep3_hunt_tuwezz_collect_uller_horns", "tuwezz_collectUllerHorns"));
    }
    public boolean ep3_etyyy_tuwezz_vol_condition_hasToSpeakToTuwezz(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "ep3_hunt_tuwezz_kill_diseased_ullers", "tuwezz_talkToTuwezz"));
    }
    public boolean ep3_etyyy_tuwezz_vol_condition_finishedCollectingUllerHorns(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "ep3_hunt_tuwezz_collect_uller_horns", "tuwezz_elderUllerHorns"));
    }
    public boolean ep3_etyyy_tuwezz_vol_condition_killedSpiketopPlusAll(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "ep3_hunt_loot_brightclaw_killed") && groundquests.hasCompletedQuest(player, "ep3_hunt_loot_silkthrower_killed") && groundquests.hasCompletedQuest(player, "ep3_hunt_loot_stoneleg_killed") && groundquests.hasCompletedQuest(player, "ep3_hunt_loot_paleclaw_killed") && groundquests.hasCompletedQuest(player, "ep3_hunt_loot_greyclimber_killed");
    }
    public void ep3_etyyy_tuwezz_vol_action_spiketopReward(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "lootQuest_defeatedSpiketop");
    }
    public void ep3_etyyy_tuwezz_vol_action_doneHuntingDiseasedUller(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "tuwezz_diseasedUllersDone");
    }
    public void ep3_etyyy_tuwezz_vol_action_huntDiseasedUller(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "tuwezz_talkToTuwezz");
    }
    public void ep3_etyyy_tuwezz_vol_action_collectUllerHorns(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "ep3_hunt_tuwezz_collect_uller_horns");
    }
    public void ep3_etyyy_tuwezz_vol_action_speakWithSordaan(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "tuwezz_elderUllerHorns");
        groundquests.grantQuest(player, "ep3_hunt_sordaan_seek_sordaan");
        groundquests.sendSignal(player, "sordaan_tuwezzSendsYou");
    }
    public void ep3_etyyy_tuwezz_vol_action_spiketopRewardPlusAll(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "lootQuest_defeatedSpiketop");
        groundquests.grantQuest(player, "ep3_hunt_loot_completed_all");
    }
    public int ep3_etyyy_tuwezz_vol_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1623"))
        {
            if (ep3_etyyy_tuwezz_vol_condition_killedSpiketopPlusAll(player, npc))
            {
                ep3_etyyy_tuwezz_vol_action_spiketopRewardPlusAll(player, npc);
                string_id message = new string_id(c_stringFile, "s_1689");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_tuwezz_vol_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1691");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_tuwezz_vol.branchId", 18);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_tuwezz_vol.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_tuwezz_vol_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_tuwezz_vol_action_spiketopReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_1695");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_tuwezz_vol.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_tuwezz_vol_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1627"))
        {
            if (ep3_etyyy_tuwezz_vol_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_tuwezz_vol_action_speakWithSordaan(player, npc);
                string_id message = new string_id(c_stringFile, "s_1629");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_tuwezz_vol.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1631"))
        {
            if (ep3_etyyy_tuwezz_vol_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1633");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_tuwezz_vol.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1635"))
        {
            if (ep3_etyyy_tuwezz_vol_condition_killedSpiketopPlusAll(player, npc))
            {
                ep3_etyyy_tuwezz_vol_action_spiketopRewardPlusAll(player, npc);
                string_id message = new string_id(c_stringFile, "s_1689");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_tuwezz_vol_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1691");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_tuwezz_vol.branchId", 18);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_tuwezz_vol.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_tuwezz_vol_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_tuwezz_vol_action_spiketopReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_1695");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_tuwezz_vol.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_tuwezz_vol_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1639"))
        {
            if (ep3_etyyy_tuwezz_vol_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1641");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_tuwezz_vol.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1643"))
        {
            if (ep3_etyyy_tuwezz_vol_condition_killedSpiketopPlusAll(player, npc))
            {
                ep3_etyyy_tuwezz_vol_action_spiketopRewardPlusAll(player, npc);
                string_id message = new string_id(c_stringFile, "s_1689");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_tuwezz_vol_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1691");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_tuwezz_vol.branchId", 18);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_tuwezz_vol.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_tuwezz_vol_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_tuwezz_vol_action_spiketopReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_1695");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_tuwezz_vol.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_tuwezz_vol_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1647"))
        {
            if (ep3_etyyy_tuwezz_vol_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_tuwezz_vol_action_collectUllerHorns(player, npc);
                string_id message = new string_id(c_stringFile, "s_1649");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_tuwezz_vol.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1651"))
        {
            if (ep3_etyyy_tuwezz_vol_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1653");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_tuwezz_vol.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1655"))
        {
            if (ep3_etyyy_tuwezz_vol_condition_killedSpiketopPlusAll(player, npc))
            {
                ep3_etyyy_tuwezz_vol_action_spiketopRewardPlusAll(player, npc);
                string_id message = new string_id(c_stringFile, "s_1689");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_tuwezz_vol_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1691");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_tuwezz_vol.branchId", 18);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_tuwezz_vol.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_tuwezz_vol_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_tuwezz_vol_action_spiketopReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_1695");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_tuwezz_vol.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_tuwezz_vol_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1659"))
        {
            if (ep3_etyyy_tuwezz_vol_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1661");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_tuwezz_vol.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1663"))
        {
            if (ep3_etyyy_tuwezz_vol_condition_killedSpiketopPlusAll(player, npc))
            {
                ep3_etyyy_tuwezz_vol_action_spiketopRewardPlusAll(player, npc);
                string_id message = new string_id(c_stringFile, "s_1689");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_tuwezz_vol_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1691");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_tuwezz_vol.branchId", 18);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_tuwezz_vol.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_tuwezz_vol_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_tuwezz_vol_action_spiketopReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_1695");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_tuwezz_vol.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_tuwezz_vol_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1667"))
        {
            if (ep3_etyyy_tuwezz_vol_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1669");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_tuwezz_vol_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_tuwezz_vol_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1671");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1675");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_tuwezz_vol.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_tuwezz_vol.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1679"))
        {
            if (ep3_etyyy_tuwezz_vol_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1681");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_tuwezz_vol.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1683"))
        {
            if (ep3_etyyy_tuwezz_vol_condition_killedSpiketopPlusAll(player, npc))
            {
                ep3_etyyy_tuwezz_vol_action_spiketopRewardPlusAll(player, npc);
                string_id message = new string_id(c_stringFile, "s_1689");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_tuwezz_vol_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1691");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_tuwezz_vol.branchId", 18);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_tuwezz_vol.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_tuwezz_vol_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_tuwezz_vol_action_spiketopReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_1695");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_tuwezz_vol.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_tuwezz_vol_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1671"))
        {
            if (ep3_etyyy_tuwezz_vol_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_tuwezz_vol_action_huntDiseasedUller(player, npc);
                string_id message = new string_id(c_stringFile, "s_1673");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_tuwezz_vol.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1675"))
        {
            if (ep3_etyyy_tuwezz_vol_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1677");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_tuwezz_vol.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_tuwezz_vol_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1687"))
        {
            if (ep3_etyyy_tuwezz_vol_condition_killedSpiketopPlusAll(player, npc))
            {
                ep3_etyyy_tuwezz_vol_action_spiketopRewardPlusAll(player, npc);
                string_id message = new string_id(c_stringFile, "s_1689");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_tuwezz_vol_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1691");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_tuwezz_vol.branchId", 18);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_tuwezz_vol.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_tuwezz_vol_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_tuwezz_vol_action_spiketopReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_1695");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_tuwezz_vol.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_tuwezz_vol_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1691"))
        {
            if (ep3_etyyy_tuwezz_vol_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1693");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_tuwezz_vol.branchId");
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
            detachScript(self, "conversation.ep3_etyyy_tuwezz_vol");
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
        detachScript(self, "conversation.ep3_etyyy_tuwezz_vol");
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
        if (ep3_etyyy_tuwezz_vol_condition_hasCompletedTuwezzQuests(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1621");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_tuwezz_vol_condition_killedSpiketop(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1623");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_tuwezz_vol.branchId", 1);
                npcStartConversation(player, npc, "ep3_etyyy_tuwezz_vol", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_tuwezz_vol_condition_finishedCollectingUllerHorns(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1625");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_tuwezz_vol_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_etyyy_tuwezz_vol_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (ep3_etyyy_tuwezz_vol_condition_killedSpiketop(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1627");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1631");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1635");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_tuwezz_vol.branchId", 2);
                npcStartConversation(player, npc, "ep3_etyyy_tuwezz_vol", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_tuwezz_vol_condition_isCollectingUllerHorns(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1637");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_tuwezz_vol_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_etyyy_tuwezz_vol_condition_killedSpiketop(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1639");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1643");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_tuwezz_vol.branchId", 5);
                npcStartConversation(player, npc, "ep3_etyyy_tuwezz_vol", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_tuwezz_vol_condition_finishedHuntingDiseasedUller(player, npc))
        {
            ep3_etyyy_tuwezz_vol_action_doneHuntingDiseasedUller(player, npc);
            string_id message = new string_id(c_stringFile, "s_1645");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_tuwezz_vol_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_etyyy_tuwezz_vol_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (ep3_etyyy_tuwezz_vol_condition_killedSpiketop(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1647");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1651");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1655");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_tuwezz_vol.branchId", 7);
                npcStartConversation(player, npc, "ep3_etyyy_tuwezz_vol", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_tuwezz_vol_condition_isHuntingDiseasedUller(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1657");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_tuwezz_vol_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_etyyy_tuwezz_vol_condition_killedSpiketop(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1659");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1663");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_tuwezz_vol.branchId", 10);
                npcStartConversation(player, npc, "ep3_etyyy_tuwezz_vol", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_tuwezz_vol_condition_hasToSpeakToTuwezz(player, npc))
        {
            doAnimationAction(npc, "greet");
            string_id message = new string_id(c_stringFile, "s_1665");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_tuwezz_vol_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_etyyy_tuwezz_vol_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (ep3_etyyy_tuwezz_vol_condition_killedSpiketop(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1667");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1679");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1683");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_tuwezz_vol.branchId", 12);
                npcStartConversation(player, npc, "ep3_etyyy_tuwezz_vol", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_tuwezz_vol_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1685");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_tuwezz_vol_condition_killedSpiketop(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1687");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_tuwezz_vol.branchId", 17);
                npcStartConversation(player, npc, "ep3_etyyy_tuwezz_vol", message, responses);
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
        if (!conversationId.equals("ep3_etyyy_tuwezz_vol"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.ep3_etyyy_tuwezz_vol.branchId");
        if (branchId == 1 && ep3_etyyy_tuwezz_vol_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && ep3_etyyy_tuwezz_vol_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && ep3_etyyy_tuwezz_vol_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && ep3_etyyy_tuwezz_vol_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && ep3_etyyy_tuwezz_vol_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && ep3_etyyy_tuwezz_vol_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && ep3_etyyy_tuwezz_vol_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && ep3_etyyy_tuwezz_vol_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && ep3_etyyy_tuwezz_vol_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.ep3_etyyy_tuwezz_vol.branchId");
        return SCRIPT_CONTINUE;
    }
}
