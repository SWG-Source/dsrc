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
import script.library.factions;
import script.library.groundquests;
import script.library.utils;

public class event_cheerleader_vader extends script.base_script
{
    public event_cheerleader_vader()
    {
    }
    public static String c_stringFile = "conversation/event_cheerleader_vader";
    public boolean event_cheerleader_vader_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean event_cheerleader_vader_condition_isRebel(obj_id player, obj_id npc) throws InterruptedException
    {
        int factionHashCode = factions.pvpGetAlignedFaction(player);
        String whichFaction = factions.getFactionNameByHashCode(factionHashCode);
        if (whichFaction == null)
        {
            return false;
        }
        if (whichFaction.equals("Rebel"))
        {
            return true;
        }
        return false;
    }
    public boolean event_cheerleader_vader_condition_isNeutral(obj_id player, obj_id npc) throws InterruptedException
    {
        int factionHashCode = factions.pvpGetAlignedFaction(player);
        String whichFaction = factions.getFactionNameByHashCode(factionHashCode);
        if (whichFaction == null)
        {
            return true;
        }
        return false;
    }
    public boolean event_cheerleader_vader_condition_hasMaxQuests(obj_id player, obj_id npc) throws InterruptedException
    {
        int killReb5 = questGetQuestId("quest/event_gcwcheerleader_sfreb5");
        int killReb10 = questGetQuestId("quest/event_gcwcheerleader_sfreb10");
        int killReb20 = questGetQuestId("quest/event_gcwcheerleader_sfreb20");
        int killReb50 = questGetQuestId("quest/event_gcwcheerleader_sfreb50");
        int cmImp1 = questGetQuestId("quest/event_gcwcheerleader_cmimp1");
        int cmImp2 = questGetQuestId("quest/event_gcwcheerleader_cmimp2");
        int cmImp3 = questGetQuestId("quest/event_gcwcheerleader_cmimp3");
        int cmImp4 = questGetQuestId("quest/event_gcwcheerleader_cmimp4");
        return (((questIsQuestActive(killReb5, player) || questIsQuestActive(killReb10, player) || questIsQuestActive(killReb20, player) || questIsQuestActive(killReb50, player)) && (questIsQuestActive(cmImp1, player) || questIsQuestActive(cmImp2, player) || questIsQuestActive(cmImp3, player) || questIsQuestActive(cmImp4, player))) || (questIsQuestComplete(killReb50, player) && (questIsQuestActive(cmImp1, player) || questIsQuestActive(cmImp2, player) || questIsQuestActive(cmImp3, player) || questIsQuestActive(cmImp4, player))) || (questIsQuestComplete(cmImp4, player) && (questIsQuestActive(killReb5, player) || questIsQuestActive(killReb10, player) || questIsQuestActive(killReb20, player) || questIsQuestActive(killReb50, player))));
    }
    public boolean event_cheerleader_vader_condition_completedAll(obj_id player, obj_id npc) throws InterruptedException
    {
        int killReb5 = questGetQuestId("quest/event_gcwcheerleader_sfreb5");
        int killReb10 = questGetQuestId("quest/event_gcwcheerleader_sfreb10");
        int killReb20 = questGetQuestId("quest/event_gcwcheerleader_sfreb20");
        int killReb50 = questGetQuestId("quest/event_gcwcheerleader_sfreb50");
        int cmImp1 = questGetQuestId("quest/event_gcwcheerleader_cmimp1");
        int cmImp2 = questGetQuestId("quest/event_gcwcheerleader_cmimp2");
        int cmImp3 = questGetQuestId("quest/event_gcwcheerleader_cmimp3");
        int cmImp4 = questGetQuestId("quest/event_gcwcheerleader_cmimp4");
        return (questIsQuestComplete(killReb5, player) && questIsQuestComplete(killReb10, player) && questIsQuestComplete(killReb20, player) && questIsQuestComplete(killReb50, player) && questIsQuestComplete(cmImp1, player) && questIsQuestComplete(cmImp2, player) && questIsQuestComplete(cmImp3, player) && questIsQuestComplete(cmImp4, player));
    }
    public boolean event_cheerleader_vader_condition_noMissionsComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        int killReb5 = questGetQuestId("quest/event_gcwcheerleader_sfreb5");
        int cmImp1 = questGetQuestId("quest/event_gcwcheerleader_cmimp1");
        return (!questIsQuestComplete(killReb5, player) && !questIsQuestComplete(cmImp1, player) && !questIsQuestActive(killReb5, player) && !questIsQuestActive(cmImp1, player));
    }
    public boolean event_cheerleader_vader_condition_pvp1Ready(obj_id player, obj_id npc) throws InterruptedException
    {
        int killReb20 = questGetQuestId("quest/event_gcwcheerleader_sfreb20");
        int killReb10 = questGetQuestId("quest/event_gcwcheerleader_sfreb10");
        int killReb5 = questGetQuestId("quest/event_gcwcheerleader_sfreb5");
        return (!questIsQuestComplete(killReb20, player) && !questIsQuestComplete(killReb10, player) && !questIsQuestComplete(killReb5, player) && !questIsQuestActive(killReb20, player) && !questIsQuestActive(killReb10, player) && !questIsQuestActive(killReb5, player));
    }
    public boolean event_cheerleader_vader_condition_pve1Ready(obj_id player, obj_id npc) throws InterruptedException
    {
        int cmImp3 = questGetQuestId("quest/event_gcwcheerleader_cmimp3");
        int cmImp2 = questGetQuestId("quest/event_gcwcheerleader_cmimp2");
        int cmImp1 = questGetQuestId("quest/event_gcwcheerleader_cmimp1");
        return (!questIsQuestComplete(cmImp3, player) && !questIsQuestComplete(cmImp2, player) && !questIsQuestActive(cmImp3, player) && !questIsQuestActive(cmImp2, player) && !questIsQuestActive(cmImp1, player) && !questIsQuestComplete(cmImp1, player));
    }
    public boolean event_cheerleader_vader_condition_pve2Ready(obj_id player, obj_id npc) throws InterruptedException
    {
        int cmImp3 = questGetQuestId("quest/event_gcwcheerleader_cmimp3");
        int cmImp2 = questGetQuestId("quest/event_gcwcheerleader_cmimp2");
        int cmImp1 = questGetQuestId("quest/event_gcwcheerleader_cmimp1");
        return (!questIsQuestComplete(cmImp3, player) && !questIsQuestComplete(cmImp2, player) && !questIsQuestActive(cmImp2, player) && !questIsQuestActive(cmImp3, player) && questIsQuestComplete(cmImp1, player));
    }
    public boolean event_cheerleader_vader_condition_pvp2Ready(obj_id player, obj_id npc) throws InterruptedException
    {
        int killReb20 = questGetQuestId("quest/event_gcwcheerleader_sfreb20");
        int killReb10 = questGetQuestId("quest/event_gcwcheerleader_sfreb10");
        int killReb5 = questGetQuestId("quest/event_gcwcheerleader_sfreb5");
        return (!questIsQuestComplete(killReb20, player) && !questIsQuestComplete(killReb10, player) && !questIsQuestActive(killReb20, player) && !questIsQuestActive(killReb10, player) && questIsQuestComplete(killReb5, player));
    }
    public boolean event_cheerleader_vader_condition_pve3Ready(obj_id player, obj_id npc) throws InterruptedException
    {
        int cmImp3 = questGetQuestId("quest/event_gcwcheerleader_cmimp3");
        int cmImp2 = questGetQuestId("quest/event_gcwcheerleader_cmimp2");
        int cmImp1 = questGetQuestId("quest/event_gcwcheerleader_cmimp1");
        return (!questIsQuestComplete(cmImp3, player) && !questIsQuestActive(cmImp3, player) && questIsQuestComplete(cmImp2, player) && questIsQuestComplete(cmImp1, player));
    }
    public boolean event_cheerleader_vader_condition_pvp3Ready(obj_id player, obj_id npc) throws InterruptedException
    {
        int killReb20 = questGetQuestId("quest/event_gcwcheerleader_sfreb20");
        int killReb10 = questGetQuestId("quest/event_gcwcheerleader_sfreb10");
        int killReb5 = questGetQuestId("quest/event_gcwcheerleader_sfreb5");
        return (!questIsQuestComplete(killReb20, player) && !questIsQuestActive(killReb20, player) && questIsQuestComplete(killReb10, player) && questIsQuestComplete(killReb5, player));
    }
    public boolean event_cheerleader_vader_condition_pvp4Ready(obj_id player, obj_id npc) throws InterruptedException
    {
        int killReb50 = questGetQuestId("quest/event_gcwcheerleader_sfreb50");
        int killReb20 = questGetQuestId("quest/event_gcwcheerleader_sfreb20");
        int killReb10 = questGetQuestId("quest/event_gcwcheerleader_sfreb10");
        int killReb5 = questGetQuestId("quest/event_gcwcheerleader_sfreb5");
        return (!questIsQuestActive(killReb50, player) && !questIsQuestComplete(killReb50, player) && questIsQuestComplete(killReb20, player) && questIsQuestComplete(killReb10, player) && questIsQuestComplete(killReb5, player));
    }
    public boolean event_cheerleader_vader_condition_pve4Ready(obj_id player, obj_id npc) throws InterruptedException
    {
        int cmImp4 = questGetQuestId("quest/event_gcwcheerleader_cmimp4");
        int cmImp3 = questGetQuestId("quest/event_gcwcheerleader_cmimp3");
        int cmImp2 = questGetQuestId("quest/event_gcwcheerleader_cmimp2");
        int cmImp1 = questGetQuestId("quest/event_gcwcheerleader_cmimp1");
        return (!questIsQuestComplete(cmImp4, player) && !questIsQuestActive(cmImp4, player) && questIsQuestComplete(cmImp3, player) && questIsQuestComplete(cmImp2, player) && questIsQuestComplete(cmImp1, player));
    }
    public boolean event_cheerleader_vader_condition_hasMinOne(obj_id player, obj_id npc) throws InterruptedException
    {
        int killReb5 = questGetQuestId("quest/event_gcwcheerleader_sfreb5");
        int cmImp1 = questGetQuestId("quest/event_gcwcheerleader_cmimp1");
        return (questIsQuestComplete(killReb5, player) || questIsQuestComplete(cmImp1, player) || questIsQuestActive(killReb5, player) || questIsQuestActive(cmImp1, player));
    }
    public void event_cheerleader_vader_action_pvp1mission(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/event_gcwcheerleader_sfreb5");
        groundquests.grantQuest(questId, player, npc, true);
        attachScript(player, "event.gcwraids.spec_force_killer");
    }
    public void event_cheerleader_vader_action_pve1mission(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/event_gcwcheerleader_cmimp1");
        groundquests.grantQuest(questId, player, npc, true);
    }
    public void event_cheerleader_vader_action_pvp2mission(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/event_gcwcheerleader_sfreb10");
        groundquests.grantQuest(questId, player, npc, true);
        attachScript(player, "event.gcwraids.spec_force_killer");
    }
    public void event_cheerleader_vader_action_pve2mission(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/event_gcwcheerleader_cmimp2");
        groundquests.grantQuest(questId, player, npc, true);
    }
    public void event_cheerleader_vader_action_pvp3mission(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/event_gcwcheerleader_sfreb20");
        groundquests.grantQuest(questId, player, npc, true);
        attachScript(player, "event.gcwraids.spec_force_killer");
    }
    public void event_cheerleader_vader_action_pve3mission(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/event_gcwcheerleader_cmimp3");
        groundquests.grantQuest(questId, player, npc, true);
    }
    public void event_cheerleader_vader_action_pvp4mission(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/event_gcwcheerleader_sfreb50");
        groundquests.grantQuest(questId, player, npc, true);
        attachScript(player, "event.gcwraids.spec_force_killer");
    }
    public void event_cheerleader_vader_action_pve4mission(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/event_gcwcheerleader_cmimp4");
        groundquests.grantQuest(questId, player, npc, true);
        attachScript(player, "event.gcwraids.pve_badge");
    }
    public int event_cheerleader_vader_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_34"))
        {
            if (event_cheerleader_vader_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_36");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_cheerleader_vader_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_38");
                    }
                    utils.setScriptVar(player, "conversation.event_cheerleader_vader.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_cheerleader_vader.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int event_cheerleader_vader_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_38"))
        {
            if (event_cheerleader_vader_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_40");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_cheerleader_vader_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (event_cheerleader_vader_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_42");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_46");
                    }
                    utils.setScriptVar(player, "conversation.event_cheerleader_vader.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_cheerleader_vader.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int event_cheerleader_vader_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_42"))
        {
            if (event_cheerleader_vader_condition__defaultCondition(player, npc))
            {
                event_cheerleader_vader_action_pve1mission(player, npc);
                string_id message = new string_id(c_stringFile, "s_44");
                utils.removeScriptVar(player, "conversation.event_cheerleader_vader.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_46"))
        {
            if (event_cheerleader_vader_condition__defaultCondition(player, npc))
            {
                event_cheerleader_vader_action_pvp1mission(player, npc);
                string_id message = new string_id(c_stringFile, "s_48");
                utils.removeScriptVar(player, "conversation.event_cheerleader_vader.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int event_cheerleader_vader_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_52"))
        {
            if (event_cheerleader_vader_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_54");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_cheerleader_vader_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_56");
                    }
                    utils.setScriptVar(player, "conversation.event_cheerleader_vader.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_cheerleader_vader.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_64"))
        {
            if (event_cheerleader_vader_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_66");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_cheerleader_vader_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_68");
                    }
                    utils.setScriptVar(player, "conversation.event_cheerleader_vader.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_cheerleader_vader.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_76"))
        {
            if (event_cheerleader_vader_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_78");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_cheerleader_vader_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.event_cheerleader_vader.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_cheerleader_vader.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_84"))
        {
            if (event_cheerleader_vader_condition__defaultCondition(player, npc))
            {
                event_cheerleader_vader_action_pvp2mission(player, npc);
                string_id message = new string_id(c_stringFile, "s_86");
                utils.removeScriptVar(player, "conversation.event_cheerleader_vader.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_88"))
        {
            if (event_cheerleader_vader_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_90");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_cheerleader_vader_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_92");
                    }
                    utils.setScriptVar(player, "conversation.event_cheerleader_vader.branchId", 20);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_cheerleader_vader.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_96"))
        {
            if (event_cheerleader_vader_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_98");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_cheerleader_vader_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_100");
                    }
                    utils.setScriptVar(player, "conversation.event_cheerleader_vader.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_cheerleader_vader.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_104"))
        {
            if (event_cheerleader_vader_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_106");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_cheerleader_vader_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_108");
                    }
                    utils.setScriptVar(player, "conversation.event_cheerleader_vader.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_cheerleader_vader.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_116"))
        {
            if (event_cheerleader_vader_condition__defaultCondition(player, npc))
            {
                event_cheerleader_vader_action_pvp4mission(player, npc);
                string_id message = new string_id(c_stringFile, "s_118");
                utils.removeScriptVar(player, "conversation.event_cheerleader_vader.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int event_cheerleader_vader_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_56"))
        {
            if (event_cheerleader_vader_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_58");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_cheerleader_vader_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_60");
                    }
                    utils.setScriptVar(player, "conversation.event_cheerleader_vader.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_cheerleader_vader.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int event_cheerleader_vader_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_60"))
        {
            if (event_cheerleader_vader_condition__defaultCondition(player, npc))
            {
                event_cheerleader_vader_action_pve1mission(player, npc);
                string_id message = new string_id(c_stringFile, "s_62");
                utils.removeScriptVar(player, "conversation.event_cheerleader_vader.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int event_cheerleader_vader_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_68"))
        {
            if (event_cheerleader_vader_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_70");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_cheerleader_vader_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_72");
                    }
                    utils.setScriptVar(player, "conversation.event_cheerleader_vader.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_cheerleader_vader.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int event_cheerleader_vader_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_72"))
        {
            if (event_cheerleader_vader_condition__defaultCondition(player, npc))
            {
                event_cheerleader_vader_action_pvp1mission(player, npc);
                string_id message = new string_id(c_stringFile, "s_74");
                utils.removeScriptVar(player, "conversation.event_cheerleader_vader.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int event_cheerleader_vader_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_80"))
        {
            if (event_cheerleader_vader_condition__defaultCondition(player, npc))
            {
                event_cheerleader_vader_action_pve2mission(player, npc);
                string_id message = new string_id(c_stringFile, "s_82");
                utils.removeScriptVar(player, "conversation.event_cheerleader_vader.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int event_cheerleader_vader_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_92"))
        {
            if (event_cheerleader_vader_condition__defaultCondition(player, npc))
            {
                event_cheerleader_vader_action_pve3mission(player, npc);
                string_id message = new string_id(c_stringFile, "s_94");
                utils.removeScriptVar(player, "conversation.event_cheerleader_vader.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int event_cheerleader_vader_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_100"))
        {
            if (event_cheerleader_vader_condition__defaultCondition(player, npc))
            {
                event_cheerleader_vader_action_pvp3mission(player, npc);
                string_id message = new string_id(c_stringFile, "s_102");
                utils.removeScriptVar(player, "conversation.event_cheerleader_vader.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int event_cheerleader_vader_handleBranch24(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_108"))
        {
            if (event_cheerleader_vader_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_110");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_cheerleader_vader_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_112");
                    }
                    utils.setScriptVar(player, "conversation.event_cheerleader_vader.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_cheerleader_vader.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int event_cheerleader_vader_handleBranch25(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_112"))
        {
            if (event_cheerleader_vader_condition__defaultCondition(player, npc))
            {
                event_cheerleader_vader_action_pve4mission(player, npc);
                string_id message = new string_id(c_stringFile, "s_114");
                utils.removeScriptVar(player, "conversation.event_cheerleader_vader.branchId");
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
            detachScript(self, "conversation.event_cheerleader_vader");
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
        detachScript(self, "conversation.event_cheerleader_vader");
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
        if (event_cheerleader_vader_condition_isRebel(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_24");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (event_cheerleader_vader_condition_isNeutral(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_26");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (event_cheerleader_vader_condition_hasMaxQuests(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_28");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (event_cheerleader_vader_condition_completedAll(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_30");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (event_cheerleader_vader_condition_noMissionsComplete(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_32");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (event_cheerleader_vader_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_34");
                }
                utils.setScriptVar(player, "conversation.event_cheerleader_vader.branchId", 5);
                npcStartConversation(player, npc, "event_cheerleader_vader", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (event_cheerleader_vader_condition_hasMinOne(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_50");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (event_cheerleader_vader_condition_pve1Ready(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (event_cheerleader_vader_condition_pvp1Ready(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (event_cheerleader_vader_condition_pve2Ready(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (event_cheerleader_vader_condition_pvp2Ready(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse3 = true;
            }
            boolean hasResponse4 = false;
            if (event_cheerleader_vader_condition_pve3Ready(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse4 = true;
            }
            boolean hasResponse5 = false;
            if (event_cheerleader_vader_condition_pvp3Ready(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse5 = true;
            }
            boolean hasResponse6 = false;
            if (event_cheerleader_vader_condition_pve4Ready(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse6 = true;
            }
            boolean hasResponse7 = false;
            if (event_cheerleader_vader_condition_pvp4Ready(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse7 = true;
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_64");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_76");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_84");
                }
                if (hasResponse4)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_88");
                }
                if (hasResponse5)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_96");
                }
                if (hasResponse6)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_104");
                }
                if (hasResponse7)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_116");
                }
                utils.setScriptVar(player, "conversation.event_cheerleader_vader.branchId", 10);
                npcStartConversation(player, npc, "event_cheerleader_vader", message, responses);
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
        if (!conversationId.equals("event_cheerleader_vader"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.event_cheerleader_vader.branchId");
        if (branchId == 5 && event_cheerleader_vader_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && event_cheerleader_vader_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && event_cheerleader_vader_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && event_cheerleader_vader_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && event_cheerleader_vader_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && event_cheerleader_vader_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && event_cheerleader_vader_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && event_cheerleader_vader_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && event_cheerleader_vader_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && event_cheerleader_vader_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && event_cheerleader_vader_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && event_cheerleader_vader_handleBranch24(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && event_cheerleader_vader_handleBranch25(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.event_cheerleader_vader.branchId");
        return SCRIPT_CONTINUE;
    }
}
