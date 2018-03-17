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

public class event_cheerleader_luke extends script.base_script
{
    public event_cheerleader_luke()
    {
    }
    public static String c_stringFile = "conversation/event_cheerleader_luke";
    public boolean event_cheerleader_luke_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean event_cheerleader_luke_condition_isImperial(obj_id player, obj_id npc) throws InterruptedException
    {
        int factionHashCode = factions.pvpGetAlignedFaction(player);
        String whichFaction = factions.getFactionNameByHashCode(factionHashCode);
        if (whichFaction == null)
        {
            return false;
        }
        if (whichFaction.equals("Imperial"))
        {
            return true;
        }
        return false;
    }
    public boolean event_cheerleader_luke_condition_isNeutral(obj_id player, obj_id npc) throws InterruptedException
    {
        int factionHashCode = factions.pvpGetAlignedFaction(player);
        String whichFaction = factions.getFactionNameByHashCode(factionHashCode);
        if (whichFaction == null)
        {
            return true;
        }
        return false;
    }
    public boolean event_cheerleader_luke_condition_hasMaxQuests(obj_id player, obj_id npc) throws InterruptedException
    {
        int killImp5 = questGetQuestId("quest/event_gcwcheerleader_sfimp5");
        int killImp10 = questGetQuestId("quest/event_gcwcheerleader_sfimp10");
        int killImp20 = questGetQuestId("quest/event_gcwcheerleader_sfimp20");
        int killImp50 = questGetQuestId("quest/event_gcwcheerleader_sfimp50");
        int cmReb1 = questGetQuestId("quest/event_gcwcheerleader_cmreb1");
        int cmReb2 = questGetQuestId("quest/event_gcwcheerleader_cmreb2");
        int cmReb3 = questGetQuestId("quest/event_gcwcheerleader_cmreb3");
        int cmReb4 = questGetQuestId("quest/event_gcwcheerleader_cmreb4");
        return (((questIsQuestActive(killImp5, player) || questIsQuestActive(killImp10, player) || questIsQuestActive(killImp20, player) || questIsQuestActive(killImp50, player)) && (questIsQuestActive(cmReb1, player) || questIsQuestActive(cmReb2, player) || questIsQuestActive(cmReb3, player) || questIsQuestActive(cmReb4, player))) || (questIsQuestComplete(killImp50, player) && (questIsQuestActive(cmReb1, player) || questIsQuestActive(cmReb2, player) || questIsQuestActive(cmReb3, player) || questIsQuestActive(cmReb4, player))) || (questIsQuestComplete(cmReb4, player) && (questIsQuestActive(killImp5, player) || questIsQuestActive(killImp10, player) || questIsQuestActive(killImp20, player) || questIsQuestActive(killImp50, player))));
    }
    public boolean event_cheerleader_luke_condition_completedAll(obj_id player, obj_id npc) throws InterruptedException
    {
        int killImp5 = questGetQuestId("quest/event_gcwcheerleader_sfimp5");
        int killImp10 = questGetQuestId("quest/event_gcwcheerleader_sfimp10");
        int killImp20 = questGetQuestId("quest/event_gcwcheerleader_sfimp20");
        int killImp50 = questGetQuestId("quest/event_gcwcheerleader_sfimp50");
        int cmReb1 = questGetQuestId("quest/event_gcwcheerleader_cmreb1");
        int cmReb2 = questGetQuestId("quest/event_gcwcheerleader_cmreb2");
        int cmReb3 = questGetQuestId("quest/event_gcwcheerleader_cmreb3");
        int cmReb4 = questGetQuestId("quest/event_gcwcheerleader_cmreb4");
        return (questIsQuestComplete(killImp5, player) && questIsQuestComplete(killImp10, player) && questIsQuestComplete(killImp20, player) && questIsQuestComplete(killImp50, player) && questIsQuestComplete(cmReb1, player) && questIsQuestComplete(cmReb2, player) && questIsQuestComplete(cmReb3, player) && questIsQuestComplete(cmReb4, player));
    }
    public boolean event_cheerleader_luke_condition_noMissionsComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        int killImp5 = questGetQuestId("quest/event_gcwcheerleader_sfimp5");
        int cmReb1 = questGetQuestId("quest/event_gcwcheerleader_cmreb1");
        return (!questIsQuestComplete(killImp5, player) && !questIsQuestComplete(cmReb1, player) && !questIsQuestActive(killImp5, player) && !questIsQuestActive(cmReb1, player));
    }
    public boolean event_cheerleader_luke_condition_pvp1Ready(obj_id player, obj_id npc) throws InterruptedException
    {
        int killImp20 = questGetQuestId("quest/event_gcwcheerleader_sfimp20");
        int killImp10 = questGetQuestId("quest/event_gcwcheerleader_sfimp10");
        int killImp5 = questGetQuestId("quest/event_gcwcheerleader_sfimp5");
        return (!questIsQuestActive(killImp20, player) && !questIsQuestActive(killImp10, player) && !questIsQuestActive(killImp5, player) && !questIsQuestComplete(killImp20, player) && !questIsQuestComplete(killImp10, player) && !questIsQuestComplete(killImp5, player));
    }
    public boolean event_cheerleader_luke_condition_pve1Ready(obj_id player, obj_id npc) throws InterruptedException
    {
        int cmReb3 = questGetQuestId("quest/event_gcwcheerleader_cmreb3");
        int cmReb2 = questGetQuestId("quest/event_gcwcheerleader_cmreb2");
        int cmReb1 = questGetQuestId("quest/event_gcwcheerleader_cmreb1");
        return (!questIsQuestComplete(cmReb3, player) && !questIsQuestComplete(cmReb2, player) && !questIsQuestComplete(cmReb1, player) && !questIsQuestActive(cmReb3, player) && !questIsQuestActive(cmReb2, player) && !questIsQuestActive(cmReb1, player));
    }
    public boolean event_cheerleader_luke_condition_pve2Ready(obj_id player, obj_id npc) throws InterruptedException
    {
        int cmReb3 = questGetQuestId("quest/event_gcwcheerleader_cmreb3");
        int cmReb2 = questGetQuestId("quest/event_gcwcheerleader_cmreb2");
        int cmReb1 = questGetQuestId("quest/event_gcwcheerleader_cmreb1");
        return (!questIsQuestComplete(cmReb3, player) && !questIsQuestComplete(cmReb2, player) && !questIsQuestActive(cmReb3, player) && !questIsQuestActive(cmReb2, player) && questIsQuestComplete(cmReb1, player));
    }
    public boolean event_cheerleader_luke_condition_pvp2Ready(obj_id player, obj_id npc) throws InterruptedException
    {
        int killImp20 = questGetQuestId("quest/event_gcwcheerleader_sfimp20");
        int killImp10 = questGetQuestId("quest/event_gcwcheerleader_sfimp10");
        int killImp5 = questGetQuestId("quest/event_gcwcheerleader_sfimp5");
        return (!questIsQuestActive(killImp20, player) && !questIsQuestActive(killImp10, player) && !questIsQuestComplete(killImp20, player) && !questIsQuestComplete(killImp10, player) && questIsQuestComplete(killImp5, player));
    }
    public boolean event_cheerleader_luke_condition_pve3Ready(obj_id player, obj_id npc) throws InterruptedException
    {
        int cmReb3 = questGetQuestId("quest/event_gcwcheerleader_cmreb3");
        int cmReb2 = questGetQuestId("quest/event_gcwcheerleader_cmreb2");
        int cmReb1 = questGetQuestId("quest/event_gcwcheerleader_cmreb1");
        return (!questIsQuestComplete(cmReb3, player) && !questIsQuestActive(cmReb3, player) && questIsQuestComplete(cmReb2, player) && questIsQuestComplete(cmReb1, player));
    }
    public boolean event_cheerleader_luke_condition_pvp3Ready(obj_id player, obj_id npc) throws InterruptedException
    {
        int killImp20 = questGetQuestId("quest/event_gcwcheerleader_sfimp20");
        int killImp10 = questGetQuestId("quest/event_gcwcheerleader_sfimp10");
        int killImp5 = questGetQuestId("quest/event_gcwcheerleader_sfimp5");
        return (!questIsQuestActive(killImp20, player) && !questIsQuestComplete(killImp20, player) && questIsQuestComplete(killImp10, player) && questIsQuestComplete(killImp5, player));
    }
    public boolean event_cheerleader_luke_condition_pvp4Ready(obj_id player, obj_id npc) throws InterruptedException
    {
        int killImp50 = questGetQuestId("quest/event_gcwcheerleader_sfimp50");
        int killImp20 = questGetQuestId("quest/event_gcwcheerleader_sfimp20");
        int killImp10 = questGetQuestId("quest/event_gcwcheerleader_sfimp10");
        int killImp5 = questGetQuestId("quest/event_gcwcheerleader_sfimp5");
        return (!questIsQuestActive(killImp50, player) && !questIsQuestComplete(killImp50, player) && questIsQuestComplete(killImp20, player) && questIsQuestComplete(killImp10, player) && questIsQuestComplete(killImp5, player));
    }
    public boolean event_cheerleader_luke_condition_pve4Ready(obj_id player, obj_id npc) throws InterruptedException
    {
        int cmReb4 = questGetQuestId("quest/event_gcwcheerleader_cmreb4");
        int cmReb3 = questGetQuestId("quest/event_gcwcheerleader_cmreb3");
        int cmReb2 = questGetQuestId("quest/event_gcwcheerleader_cmreb2");
        int cmReb1 = questGetQuestId("quest/event_gcwcheerleader_cmreb1");
        return (!questIsQuestComplete(cmReb4, player) && !questIsQuestActive(cmReb4, player) && questIsQuestComplete(cmReb3, player) && questIsQuestComplete(cmReb2, player) && questIsQuestComplete(cmReb1, player));
    }
    public boolean event_cheerleader_luke_condition_hasMinOne(obj_id player, obj_id npc) throws InterruptedException
    {
        int killImp5 = questGetQuestId("quest/event_gcwcheerleader_sfimp5");
        int cmReb1 = questGetQuestId("quest/event_gcwcheerleader_cmreb1");
        return (questIsQuestComplete(killImp5, player) || questIsQuestComplete(cmReb1, player) || questIsQuestActive(killImp5, player) || questIsQuestActive(cmReb1, player));
    }
    public void event_cheerleader_luke_action_pvp1mission(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/event_gcwcheerleader_sfimp5");
        groundquests.grantQuest(questId, player, npc, true);
        attachScript(player, "event.gcwraids.spec_force_killer");
    }
    public void event_cheerleader_luke_action_pve1mission(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/event_gcwcheerleader_cmreb1");
        groundquests.grantQuest(questId, player, npc, true);
    }
    public void event_cheerleader_luke_action_pvp2mission(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/event_gcwcheerleader_sfimp10");
        groundquests.grantQuest(questId, player, npc, true);
        attachScript(player, "event.gcwraids.spec_force_killer");
    }
    public void event_cheerleader_luke_action_pve2mission(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/event_gcwcheerleader_cmreb2");
        groundquests.grantQuest(questId, player, npc, true);
    }
    public void event_cheerleader_luke_action_pvp3mission(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/event_gcwcheerleader_sfimp20");
        groundquests.grantQuest(questId, player, npc, true);
        attachScript(player, "event.gcwraids.spec_force_killer");
    }
    public void event_cheerleader_luke_action_pve3mission(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/event_gcwcheerleader_cmreb3");
        groundquests.grantQuest(questId, player, npc, true);
    }
    public void event_cheerleader_luke_action_pvp4mission(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/event_gcwcheerleader_sfimp50");
        groundquests.grantQuest(questId, player, npc, true);
        attachScript(player, "event.gcwraids.spec_force_killer");
    }
    public void event_cheerleader_luke_action_pve4mission(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/event_gcwcheerleader_cmreb4");
        groundquests.grantQuest(questId, player, npc, true);
        attachScript(player, "event.gcwraids.pve_badge");
    }
    public int event_cheerleader_luke_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_112"))
        {
            if (event_cheerleader_luke_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_114");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_cheerleader_luke_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_116");
                    }
                    utils.setScriptVar(player, "conversation.event_cheerleader_luke.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_cheerleader_luke.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int event_cheerleader_luke_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_116"))
        {
            if (event_cheerleader_luke_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_118");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_cheerleader_luke_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (event_cheerleader_luke_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_120");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_124");
                    }
                    utils.setScriptVar(player, "conversation.event_cheerleader_luke.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_cheerleader_luke.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int event_cheerleader_luke_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_120"))
        {
            if (event_cheerleader_luke_condition__defaultCondition(player, npc))
            {
                event_cheerleader_luke_action_pve1mission(player, npc);
                string_id message = new string_id(c_stringFile, "s_122");
                utils.removeScriptVar(player, "conversation.event_cheerleader_luke.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_124"))
        {
            if (event_cheerleader_luke_condition__defaultCondition(player, npc))
            {
                event_cheerleader_luke_action_pvp1mission(player, npc);
                string_id message = new string_id(c_stringFile, "s_126");
                utils.removeScriptVar(player, "conversation.event_cheerleader_luke.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int event_cheerleader_luke_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_130"))
        {
            if (event_cheerleader_luke_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_132");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_cheerleader_luke_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_134");
                    }
                    utils.setScriptVar(player, "conversation.event_cheerleader_luke.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_cheerleader_luke.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_142"))
        {
            if (event_cheerleader_luke_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_144");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_cheerleader_luke_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_146");
                    }
                    utils.setScriptVar(player, "conversation.event_cheerleader_luke.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_cheerleader_luke.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_154"))
        {
            if (event_cheerleader_luke_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_156");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_cheerleader_luke_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_158");
                    }
                    utils.setScriptVar(player, "conversation.event_cheerleader_luke.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_cheerleader_luke.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_162"))
        {
            if (event_cheerleader_luke_condition__defaultCondition(player, npc))
            {
                event_cheerleader_luke_action_pvp2mission(player, npc);
                string_id message = new string_id(c_stringFile, "s_164");
                utils.removeScriptVar(player, "conversation.event_cheerleader_luke.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_166"))
        {
            if (event_cheerleader_luke_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_168");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_cheerleader_luke_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_170");
                    }
                    utils.setScriptVar(player, "conversation.event_cheerleader_luke.branchId", 20);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_cheerleader_luke.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_174"))
        {
            if (event_cheerleader_luke_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_176");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_cheerleader_luke_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_178");
                    }
                    utils.setScriptVar(player, "conversation.event_cheerleader_luke.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_cheerleader_luke.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_182"))
        {
            if (event_cheerleader_luke_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_184");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_cheerleader_luke_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_186");
                    }
                    utils.setScriptVar(player, "conversation.event_cheerleader_luke.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_cheerleader_luke.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_194"))
        {
            if (event_cheerleader_luke_condition__defaultCondition(player, npc))
            {
                event_cheerleader_luke_action_pvp4mission(player, npc);
                string_id message = new string_id(c_stringFile, "s_196");
                utils.removeScriptVar(player, "conversation.event_cheerleader_luke.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int event_cheerleader_luke_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_134"))
        {
            if (event_cheerleader_luke_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_136");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_cheerleader_luke_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_138");
                    }
                    utils.setScriptVar(player, "conversation.event_cheerleader_luke.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_cheerleader_luke.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int event_cheerleader_luke_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_138"))
        {
            if (event_cheerleader_luke_condition__defaultCondition(player, npc))
            {
                event_cheerleader_luke_action_pve1mission(player, npc);
                string_id message = new string_id(c_stringFile, "s_140");
                utils.removeScriptVar(player, "conversation.event_cheerleader_luke.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int event_cheerleader_luke_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_146"))
        {
            if (event_cheerleader_luke_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_148");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_cheerleader_luke_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_150");
                    }
                    utils.setScriptVar(player, "conversation.event_cheerleader_luke.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_cheerleader_luke.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int event_cheerleader_luke_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_150"))
        {
            if (event_cheerleader_luke_condition__defaultCondition(player, npc))
            {
                event_cheerleader_luke_action_pvp1mission(player, npc);
                string_id message = new string_id(c_stringFile, "s_152");
                utils.removeScriptVar(player, "conversation.event_cheerleader_luke.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int event_cheerleader_luke_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_158"))
        {
            if (event_cheerleader_luke_condition__defaultCondition(player, npc))
            {
                event_cheerleader_luke_action_pve2mission(player, npc);
                string_id message = new string_id(c_stringFile, "s_160");
                utils.removeScriptVar(player, "conversation.event_cheerleader_luke.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int event_cheerleader_luke_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_170"))
        {
            if (event_cheerleader_luke_condition__defaultCondition(player, npc))
            {
                event_cheerleader_luke_action_pve3mission(player, npc);
                string_id message = new string_id(c_stringFile, "s_172");
                utils.removeScriptVar(player, "conversation.event_cheerleader_luke.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int event_cheerleader_luke_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_178"))
        {
            if (event_cheerleader_luke_condition__defaultCondition(player, npc))
            {
                event_cheerleader_luke_action_pvp3mission(player, npc);
                string_id message = new string_id(c_stringFile, "s_180");
                utils.removeScriptVar(player, "conversation.event_cheerleader_luke.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int event_cheerleader_luke_handleBranch24(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_186"))
        {
            if (event_cheerleader_luke_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_188");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_cheerleader_luke_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_190");
                    }
                    utils.setScriptVar(player, "conversation.event_cheerleader_luke.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_cheerleader_luke.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int event_cheerleader_luke_handleBranch25(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_190"))
        {
            if (event_cheerleader_luke_condition__defaultCondition(player, npc))
            {
                event_cheerleader_luke_action_pve4mission(player, npc);
                string_id message = new string_id(c_stringFile, "s_192");
                utils.removeScriptVar(player, "conversation.event_cheerleader_luke.branchId");
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
            detachScript(self, "conversation.event_cheerleader_luke");
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
        detachScript(self, "conversation.event_cheerleader_luke");
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
        if (event_cheerleader_luke_condition_isImperial(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_102");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (event_cheerleader_luke_condition_isNeutral(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_104");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (event_cheerleader_luke_condition_hasMaxQuests(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_106");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (event_cheerleader_luke_condition_completedAll(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_108");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (event_cheerleader_luke_condition_noMissionsComplete(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_110");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (event_cheerleader_luke_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.event_cheerleader_luke.branchId", 5);
                npcStartConversation(player, npc, "event_cheerleader_luke", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (event_cheerleader_luke_condition_hasMinOne(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_128");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (event_cheerleader_luke_condition_pve1Ready(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (event_cheerleader_luke_condition_pvp1Ready(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (event_cheerleader_luke_condition_pve2Ready(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (event_cheerleader_luke_condition_pvp2Ready(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse3 = true;
            }
            boolean hasResponse4 = false;
            if (event_cheerleader_luke_condition_pve3Ready(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse4 = true;
            }
            boolean hasResponse5 = false;
            if (event_cheerleader_luke_condition_pvp3Ready(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse5 = true;
            }
            boolean hasResponse6 = false;
            if (event_cheerleader_luke_condition_pve4Ready(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse6 = true;
            }
            boolean hasResponse7 = false;
            if (event_cheerleader_luke_condition_pvp4Ready(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_130");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_142");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_154");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_162");
                }
                if (hasResponse4)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_166");
                }
                if (hasResponse5)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_174");
                }
                if (hasResponse6)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_182");
                }
                if (hasResponse7)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_194");
                }
                utils.setScriptVar(player, "conversation.event_cheerleader_luke.branchId", 10);
                npcStartConversation(player, npc, "event_cheerleader_luke", message, responses);
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
        if (!conversationId.equals("event_cheerleader_luke"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.event_cheerleader_luke.branchId");
        if (branchId == 5 && event_cheerleader_luke_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && event_cheerleader_luke_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && event_cheerleader_luke_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && event_cheerleader_luke_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && event_cheerleader_luke_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && event_cheerleader_luke_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && event_cheerleader_luke_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && event_cheerleader_luke_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && event_cheerleader_luke_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && event_cheerleader_luke_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && event_cheerleader_luke_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && event_cheerleader_luke_handleBranch24(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && event_cheerleader_luke_handleBranch25(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.event_cheerleader_luke.branchId");
        return SCRIPT_CONTINUE;
    }
}
