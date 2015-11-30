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
import script.library.fs_dyn_village;
import script.library.fs_quests;
import script.library.quests;
import script.library.utils;

public class village_whip extends script.base_script
{
    public village_whip()
    {
    }
    public static String c_stringFile = "conversation/village_whip";
    public boolean village_whip_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean village_whip_condition_phase1_inprogress(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id master = getObjIdObjVar(npc, "village_master");
        if (!isIdValid(master))
        {
            return false;
        }
        int phase = fs_dyn_village.getCurrentPhaseAuth(master);
        if (phase != 1)
        {
            return false;
        }
        if (hasObjVar(player, "quest.fs_reflex1.in_progress"))
        {
            if (!quests.isActive("fs_reflex_rescue_quest_01", player))
            {
                return true;
            }
            if (hasObjVar(player, "quest.fs_reflex_rescue_quest_01.waypoint"))
            {
                obj_id waypoint = getObjIdObjVar(player, "quest.fs_reflex_rescue_quest_01.waypoint");
                obj_id[] wps = getWaypointsInDatapad(player);
                boolean match = false;
                if (wps != null && wps.length > 0)
                {
                    for (int i = 0; i < wps.length; i++)
                    {
                        if (waypoint == wps[i])
                        {
                            match = true;
                        }
                    }
                }
                if (!match)
                {
                    if (hasObjVar(player, "quest.fs_reflex_rescue_quest_01.selected_location"))
                    {
                        location loc = getLocationObjVar(player, "quest.fs_reflex_rescue_quest_01.selected_location");
                        String questName = "fs_reflex_rescue_quest_01";
                        int x_rand = rand(100, 250);
                        int z_rand = rand(100, 250);
                        int x_mod = rand(0, 1);
                        int z_mod = rand(0, 1);
                        if (x_mod == 0)
                        {
                            x_rand *= -1;
                        }
                        if (z_mod == 0)
                        {
                            z_rand *= -1;
                        }
                        loc.x += (float)x_rand;
                        loc.z += (float)z_rand;
                        obj_id wp = createWaypointInDatapad(player, loc);
                        String summary = quests.getDataEntry(questName, "JOURNAL_ENTRY_SUMMARY");
                        if (summary != null && summary.length() > 0)
                        {
                            setWaypointName(wp, summary);
                        }
                        else 
                        {
                            setWaypointName(wp, "missing task summary for " + questName);
                        }
                        setWaypointColor(wp, "yellow");
                        setWaypointActive(wp, true);
                        addLocationTarget(questName + "_waypoint", loc, 32.0f);
                        setObjVar(player, "quest." + questName + ".waypoint", wp);
                    }
                }
            }
            return true;
        }
        return false;
    }
    public boolean village_whip_condition_phase1_givequest(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id master = getObjIdObjVar(npc, "village_master");
        if (!isIdValid(master))
        {
            return false;
        }
        if (!fs_quests.isVillageEligible(player))
        {
            return false;
        }
        if (fs_quests.hasQuestAccepted(player))
        {
            return false;
        }
        int phase = fs_dyn_village.getCurrentPhaseAuth(master);
        if (phase != 1)
        {
            return false;
        }
        return true;
    }
    public boolean village_whip_condition_phase1_continue(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id master = getObjIdObjVar(npc, "village_master");
        if (!isIdValid(master))
        {
            return false;
        }
        int phase = fs_dyn_village.getCurrentPhaseAuth(master);
        if (phase != 1)
        {
            return false;
        }
        if (hasObjVar(player, "quest.fs_reflex1.continue"))
        {
            return true;
        }
        return false;
    }
    public boolean village_whip_condition_phase1_failed(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id master = getObjIdObjVar(npc, "village_master");
        if (!isIdValid(master))
        {
            return false;
        }
        int phase = fs_dyn_village.getCurrentPhaseAuth(master);
        if (phase != 1)
        {
            return false;
        }
        if (hasObjVar(player, "quest.fs_reflex1.failed"))
        {
            return true;
        }
        return false;
    }
    public boolean village_whip_condition_phase1_complete(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id master = getObjIdObjVar(npc, "village_master");
        if (!isIdValid(master))
        {
            return false;
        }
        int phase = fs_dyn_village.getCurrentPhaseAuth(master);
        if (phase != 1)
        {
            return false;
        }
        if (quests.isComplete("fs_reflex_rescue_quest_05", player))
        {
            return true;
        }
        return false;
    }
    public boolean village_whip_condition_phase2_complete(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id master = getObjIdObjVar(npc, "village_master");
        if (!isIdValid(master))
        {
            return false;
        }
        int phase = fs_dyn_village.getCurrentPhaseAuth(master);
        if (phase != 2)
        {
            return false;
        }
        if (quests.isComplete("fs_reflex_fetch_quest_04", player))
        {
            return true;
        }
        return false;
    }
    public boolean village_whip_condition_phase2_continue(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id master = getObjIdObjVar(npc, "village_master");
        if (!isIdValid(master))
        {
            return false;
        }
        int phase = fs_dyn_village.getCurrentPhaseAuth(master);
        if (phase != 2)
        {
            return false;
        }
        if (hasObjVar(player, "quest.fs_reflex2.continue"))
        {
            return true;
        }
        return false;
    }
    public boolean village_whip_condition_phase2_failed(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id master = getObjIdObjVar(npc, "village_master");
        if (!isIdValid(master))
        {
            return false;
        }
        int phase = fs_dyn_village.getCurrentPhaseAuth(master);
        if (phase != 2)
        {
            return false;
        }
        if (hasObjVar(player, "quest.fs_reflex2.failed"))
        {
            return true;
        }
        return false;
    }
    public boolean village_whip_condition_phase2_givequest(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id master = getObjIdObjVar(npc, "village_master");
        if (!isIdValid(master))
        {
            return false;
        }
        if (!fs_quests.isVillageEligible(player))
        {
            return false;
        }
        if (fs_quests.hasQuestAccepted(player))
        {
            return false;
        }
        int phase = fs_dyn_village.getCurrentPhaseAuth(master);
        if (phase != 2)
        {
            return false;
        }
        return true;
    }
    public boolean village_whip_condition_phase2_inprogress(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id master = getObjIdObjVar(npc, "village_master");
        if (!isIdValid(master))
        {
            return false;
        }
        int phase = fs_dyn_village.getCurrentPhaseAuth(master);
        if (phase != 2)
        {
            return false;
        }
        if (hasObjVar(player, "quest.fs_reflex2.in_progress"))
        {
            return true;
        }
        return false;
    }
    public boolean village_whip_condition_phase2_aborted(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id master = getObjIdObjVar(npc, "village_master");
        if (!isIdValid(master))
        {
            return false;
        }
        int phase = fs_dyn_village.getCurrentPhaseAuth(master);
        if (phase != 2)
        {
            return false;
        }
        if (hasObjVar(player, "quest.fs_reflex2.aborted"))
        {
            return true;
        }
        return false;
    }
    public boolean village_whip_condition_not_eligible(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!fs_quests.isVillageEligible(player))
        {
            return true;
        }
        return false;
    }
    public boolean village_whip_condition_phase3(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id master = getObjIdObjVar(npc, "village_master");
        if (!isIdValid(master))
        {
            return false;
        }
        int phase = fs_dyn_village.getCurrentPhaseAuth(master);
        if (phase == 3)
        {
            return true;
        }
        return false;
    }
    public boolean village_whip_condition_phase1_aborted(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id master = getObjIdObjVar(npc, "village_master");
        if (!isIdValid(master))
        {
            return false;
        }
        int phase = fs_dyn_village.getCurrentPhaseAuth(master);
        if (phase != 1)
        {
            return false;
        }
        if (hasObjVar(player, "quest.fs_reflex1.aborted"))
        {
            return true;
        }
        return false;
    }
    public boolean village_whip_condition_quest_accepted(obj_id player, obj_id npc) throws InterruptedException
    {
        if (fs_quests.hasQuestAccepted(player))
        {
            return true;
        }
        return false;
    }
    public boolean village_whip_condition_quest_completed(obj_id player, obj_id npc) throws InterruptedException
    {
        if (fs_quests.hasQuestCompleted(player))
        {
            return true;
        }
        return false;
    }
    public boolean village_whip_condition_phase4(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id master = getObjIdObjVar(npc, "village_master");
        if (!isIdValid(master))
        {
            return false;
        }
        int phase = fs_dyn_village.getCurrentPhaseAuth(master);
        if (phase == 4)
        {
            return true;
        }
        return false;
    }
    public void village_whip_action_phase1_activate_quest(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id master = getObjIdObjVar(npc, "village_master");
        if (isIdValid(master))
        {
            setObjVar(player, "quest.fs_reflex1.master", master);
        }
        attachScript(player, "quest.force_sensitive.fs_reflex1_player");
        quests.activate("fs_reflex_rescue_quest_00", player, null);
        fs_quests.setQuestAccepted(player);
    }
    public void village_whip_action_phase1_continue_quest(obj_id player, obj_id npc) throws InterruptedException
    {
        removeObjVar(player, "quest.fs_reflex1.continue");
        removeObjVar(player, "quest.fs_reflex1.failed");
        removeObjVar(player, "quest.fs_reflex1.aborted");
        quests.activate("fs_reflex_rescue_quest_00", player, null);
    }
    public void village_whip_action_phase2_activate_quest(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id master = getObjIdObjVar(npc, "village_master");
        if (isIdValid(master))
        {
            setObjVar(player, "quest.fs_reflex2.master", master);
        }
        attachScript(player, "quest.force_sensitive.fs_reflex2_player");
        setObjVar(player, "quest.fs_reflex_fetch_quest_03.target", npc);
        quests.activate("fs_reflex_fetch_quest_00", player, null);
        fs_quests.setQuestAccepted(player);
    }
    public void village_whip_action_phase2_continue_quest(obj_id player, obj_id npc) throws InterruptedException
    {
        removeObjVar(player, "quest.fs_reflex2.continue");
        removeObjVar(player, "quest.fs_reflex2.failed");
        removeObjVar(player, "quest.fs_reflex2.aborted");
        if (utils.playerHasItemByTemplate(player, "object/tangible/item/quest/force_sensitive/fs_reflex_supply_crate.iff"))
        {
            obj_id crate = utils.getItemPlayerHasByTemplate(player, "object/tangible/item/quest/force_sensitive/fs_reflex_supply_crate.iff");
            if (isIdValid(crate))
            {
                removeObjVar(crate, "player");
                destroyObject(crate);
            }
        }
        quests.activate("fs_reflex_fetch_quest_00", player, null);
    }
    public void village_whip_action_phase2_abort_in_convo(obj_id player, obj_id npc) throws InterruptedException
    {
        removeObjVar(player, "quest.fs_reflex2.in_progress");
        setObjVar(player, "quest.fs_reflex2.failed", 1);
        for (int i = 0; i <= 6; i++)
        {
            String name = "fs_reflex_fetch_quest_0" + i;
            int questId = quests.getQuestId(name);
            if (quests.isComplete(name, player))
            {
                clearCompletedQuest(player, questId);
            }
            else if (quests.isActive(name, player))
            {
                quests.deactivate(name, player);
            }
            if (hasObjVar(player, "quest." + name + ".waypoint"))
            {
                obj_id waypoint = getObjIdObjVar(player, "quest." + name + ".waypoint");
                if (isIdValid(waypoint))
                {
                    destroyWaypointInDatapad(waypoint, player);
                }
            }
        }
        obj_id theater = getLastSpawnedTheater(player);
        if (isIdValid(theater))
        {
            destroyObject(theater);
        }
    }
    public int village_whip_tokenDI_villagers_left(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasObjVar(player, "quest.fs_reflex1.rescued"))
        {
            return -1;
        }
        int rescued = getIntObjVar(player, "quest.fs_reflex1.rescued");
        return (5 - rescued);
    }
    public int village_whip_tokenDI_supplies_left(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasObjVar(player, "quest.fs_reflex2.rescued"))
        {
            return -1;
        }
        int rescued = getIntObjVar(player, "quest.fs_reflex2.rescued");
        return (6 - rescued);
    }
    public int village_whip_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_dcf4498e"))
        {
            village_whip_action_phase2_abort_in_convo(player, npc);
            if (village_whip_condition__defaultCondition(player, npc))
            {
                village_whip_action_phase2_continue_quest(player, npc);
                string_id message = new string_id(c_stringFile, "s_e780d201");
                utils.removeScriptVar(player, "conversation.village_whip.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_24aab8ee"))
        {
            if (village_whip_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_a80d9308");
                utils.removeScriptVar(player, "conversation.village_whip.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int village_whip_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_77e48d5b"))
        {
            if (village_whip_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_5d4ac8af");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (village_whip_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_dc446bf8");
                    }
                    utils.setScriptVar(player, "conversation.village_whip.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.village_whip.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int village_whip_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_dc446bf8"))
        {
            if (village_whip_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_550396aa");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (village_whip_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a767cb3c");
                    }
                    utils.setScriptVar(player, "conversation.village_whip.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.village_whip.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int village_whip_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_a767cb3c"))
        {
            if (village_whip_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_6fb14c74");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (village_whip_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (village_whip_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_22d4c864");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d29a29d0");
                    }
                    utils.setScriptVar(player, "conversation.village_whip.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.village_whip.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int village_whip_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_22d4c864"))
        {
            if (village_whip_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_e591b8a3");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (village_whip_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (village_whip_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d9ea288b");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2fdb8fbd");
                    }
                    utils.setScriptVar(player, "conversation.village_whip.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.village_whip.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_d29a29d0"))
        {
            if (village_whip_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_32");
                utils.removeScriptVar(player, "conversation.village_whip.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int village_whip_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_d9ea288b"))
        {
            if (village_whip_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_eefb2830");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (village_whip_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (village_whip_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_34ea67b6");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d7ce4a0d");
                    }
                    utils.setScriptVar(player, "conversation.village_whip.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.village_whip.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_2fdb8fbd"))
        {
            if (village_whip_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_29");
                utils.removeScriptVar(player, "conversation.village_whip.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int village_whip_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_34ea67b6"))
        {
            if (village_whip_condition__defaultCondition(player, npc))
            {
                village_whip_action_phase2_activate_quest(player, npc);
                string_id message = new string_id(c_stringFile, "s_edfab808");
                utils.removeScriptVar(player, "conversation.village_whip.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_d7ce4a0d"))
        {
            if (village_whip_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_106cd909");
                utils.removeScriptVar(player, "conversation.village_whip.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int village_whip_handleBranch24(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_22a5c907"))
        {
            if (village_whip_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_74140308");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (village_whip_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (village_whip_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7b35cfbc");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_89");
                    }
                    utils.setScriptVar(player, "conversation.village_whip.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.village_whip.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_a1ba7ed2"))
        {
            if (village_whip_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_39f2cf64");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (village_whip_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (village_whip_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_edb238bc");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5d0073c6");
                    }
                    utils.setScriptVar(player, "conversation.village_whip.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.village_whip.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_455e077f"))
        {
            if (village_whip_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_a32ebe9e");
                utils.removeScriptVar(player, "conversation.village_whip.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int village_whip_handleBranch25(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_7b35cfbc"))
        {
            if (village_whip_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_39f2cf64");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (village_whip_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (village_whip_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_edb238bc");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5d0073c6");
                    }
                    utils.setScriptVar(player, "conversation.village_whip.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.village_whip.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_89"))
        {
            if (village_whip_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_91");
                utils.removeScriptVar(player, "conversation.village_whip.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int village_whip_handleBranch26(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_edb238bc"))
        {
            if (village_whip_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_40681425");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (village_whip_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (village_whip_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_46");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8dace66");
                    }
                    utils.setScriptVar(player, "conversation.village_whip.branchId", 27);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.village_whip.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_5d0073c6"))
        {
            if (village_whip_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_c2db6878");
                utils.removeScriptVar(player, "conversation.village_whip.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int village_whip_handleBranch27(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_46"))
        {
            if (village_whip_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_e9a9130f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (village_whip_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (village_whip_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (village_whip_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_49");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_551d5a8f");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_80");
                    }
                    utils.setScriptVar(player, "conversation.village_whip.branchId", 28);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.village_whip.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_8dace66"))
        {
            if (village_whip_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_85");
                utils.removeScriptVar(player, "conversation.village_whip.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int village_whip_handleBranch28(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_49"))
        {
            if (village_whip_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_dc8490a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (village_whip_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (village_whip_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (village_whip_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d18b8c00");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bf15feab");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9129fd1b");
                    }
                    utils.setScriptVar(player, "conversation.village_whip.branchId", 29);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.village_whip.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_551d5a8f"))
        {
            if (village_whip_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_9359f893");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (village_whip_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (village_whip_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_76");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1dac49d2");
                    }
                    utils.setScriptVar(player, "conversation.village_whip.branchId", 38);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.village_whip.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_80"))
        {
            if (village_whip_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_82");
                utils.removeScriptVar(player, "conversation.village_whip.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int village_whip_handleBranch29(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_d18b8c00"))
        {
            if (village_whip_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_8ccc4e47");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (village_whip_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (village_whip_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d38b8e62");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_63");
                    }
                    utils.setScriptVar(player, "conversation.village_whip.branchId", 30);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.village_whip.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_bf15feab"))
        {
            if (village_whip_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_7e76f08e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (village_whip_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (village_whip_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_91bf5d37");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_54d57caf");
                    }
                    utils.setScriptVar(player, "conversation.village_whip.branchId", 35);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.village_whip.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_9129fd1b"))
        {
            if (village_whip_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_72");
                utils.removeScriptVar(player, "conversation.village_whip.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int village_whip_handleBranch30(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_d38b8e62"))
        {
            if (village_whip_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_55");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (village_whip_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (village_whip_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_57");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_60");
                    }
                    utils.setScriptVar(player, "conversation.village_whip.branchId", 31);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.village_whip.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_63"))
        {
            if (village_whip_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_6bd93e78");
                utils.removeScriptVar(player, "conversation.village_whip.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int village_whip_handleBranch31(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_57"))
        {
            if (village_whip_condition__defaultCondition(player, npc))
            {
                village_whip_action_phase1_activate_quest(player, npc);
                string_id message = new string_id(c_stringFile, "s_3e543459");
                utils.removeScriptVar(player, "conversation.village_whip.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_60"))
        {
            if (village_whip_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_321d7941");
                utils.removeScriptVar(player, "conversation.village_whip.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int village_whip_handleBranch35(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_91bf5d37"))
        {
            if (village_whip_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_8ccc4e47");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (village_whip_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (village_whip_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d38b8e62");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_63");
                    }
                    utils.setScriptVar(player, "conversation.village_whip.branchId", 30);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.village_whip.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_54d57caf"))
        {
            if (village_whip_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_981b299f");
                utils.removeScriptVar(player, "conversation.village_whip.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int village_whip_handleBranch38(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_76"))
        {
            if (village_whip_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_dc8490a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (village_whip_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (village_whip_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (village_whip_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d18b8c00");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bf15feab");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9129fd1b");
                    }
                    utils.setScriptVar(player, "conversation.village_whip.branchId", 29);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.village_whip.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1dac49d2"))
        {
            if (village_whip_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_33b9135e");
                utils.removeScriptVar(player, "conversation.village_whip.branchId");
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
            detachScript(self, "conversation.village_whip");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        if (!fs_dyn_village.getRegisteredObjIdFromClusterWideData(fs_dyn_village.CLUSTER_OBJID_KEY_MASTER, "handleMasterIdResponse", self))
        {
            messageTo(self, "handleMasterIdRequestRetry", null, 120.0f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        if (!fs_dyn_village.getRegisteredObjIdFromClusterWideData(fs_dyn_village.CLUSTER_OBJID_KEY_MASTER, "handleMasterIdResponse", self))
        {
            messageTo(self, "handleMasterIdRequestRetry", null, 120.0f, false);
        }
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
        detachScript(self, "conversation.village_whip");
        return SCRIPT_CONTINUE;
    }
    public int handleMasterIdResponse(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id master = params.getObjId(fs_dyn_village.CLUSTER_OBJID_KEY_MASTER);
        if (isIdValid(master))
        {
            setObjVar(self, "village_master", master);
        }
        else 
        {
            messageTo(self, "handleMasterIdRequestRetry", null, 120.0f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleMasterIdRequestRetry(obj_id self, dictionary params) throws InterruptedException
    {
        if (!fs_dyn_village.getRegisteredObjIdFromClusterWideData(fs_dyn_village.CLUSTER_OBJID_KEY_MASTER, "handleMasterIdResponse", self))
        {
            messageTo(self, "handleMasterIdRequestRetry", null, 120.0f, false);
        }
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
        if (village_whip_condition_not_eligible(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_b8360516");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (village_whip_condition_phase2_complete(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_a984d976");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (village_whip_condition_phase2_aborted(player, npc))
        {
            village_whip_action_phase2_continue_quest(player, npc);
            string_id message = new string_id(c_stringFile, "s_bfc5a85d");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (village_whip_condition_phase2_failed(player, npc))
        {
            village_whip_action_phase2_continue_quest(player, npc);
            string_id message = new string_id(c_stringFile, "s_af518049");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (village_whip_condition_phase2_continue(player, npc))
        {
            village_whip_action_phase2_continue_quest(player, npc);
            string_id message = new string_id(c_stringFile, "s_cc5225b7");
            prose_package pp = new prose_package();
            pp.stringId = message;
            pp.actor.set(player);
            pp.target.set(npc);
            pp.digitInteger = village_whip_tokenDI_supplies_left(player, npc);
            chat.chat(npc, player, null, null, pp);
            return SCRIPT_CONTINUE;
        }
        if (village_whip_condition_phase2_inprogress(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_ed25aceb");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (village_whip_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (village_whip_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_dcf4498e");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_24aab8ee");
                }
                utils.setScriptVar(player, "conversation.village_whip.branchId", 6);
                npcStartConversation(player, npc, "village_whip", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (village_whip_condition_phase2_givequest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_4d59f240");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (village_whip_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_77e48d5b");
                }
                utils.setScriptVar(player, "conversation.village_whip.branchId", 9);
                npcStartConversation(player, npc, "village_whip", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (village_whip_condition_phase1_complete(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_ab0312ab");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (village_whip_condition_phase1_aborted(player, npc))
        {
            village_whip_action_phase1_continue_quest(player, npc);
            string_id message = new string_id(c_stringFile, "s_ccc7b5db");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (village_whip_condition_phase1_failed(player, npc))
        {
            village_whip_action_phase1_continue_quest(player, npc);
            string_id message = new string_id(c_stringFile, "s_4d0a11ef");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (village_whip_condition_phase1_continue(player, npc))
        {
            village_whip_action_phase1_continue_quest(player, npc);
            string_id message = new string_id(c_stringFile, "s_6862142d");
            prose_package pp = new prose_package();
            pp.stringId = message;
            pp.actor.set(player);
            pp.target.set(npc);
            pp.digitInteger = village_whip_tokenDI_villagers_left(player, npc);
            chat.chat(npc, player, null, null, pp);
            return SCRIPT_CONTINUE;
        }
        if (village_whip_condition_phase1_inprogress(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_ea9470bd");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (village_whip_condition_phase1_givequest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_35ee8c91");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (village_whip_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (village_whip_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (village_whip_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_22a5c907");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_a1ba7ed2");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_455e077f");
                }
                utils.setScriptVar(player, "conversation.village_whip.branchId", 24);
                npcStartConversation(player, npc, "village_whip", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (village_whip_condition_quest_completed(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_de007d6");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (village_whip_condition_quest_accepted(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_bfdaca25");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (village_whip_condition_phase3(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_e46c2ff7");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (village_whip_condition_phase4(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_32360540");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (village_whip_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_3c9eddca");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("village_whip"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.village_whip.branchId");
        if (branchId == 6 && village_whip_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && village_whip_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && village_whip_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && village_whip_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && village_whip_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && village_whip_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && village_whip_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && village_whip_handleBranch24(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && village_whip_handleBranch25(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && village_whip_handleBranch26(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 27 && village_whip_handleBranch27(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 28 && village_whip_handleBranch28(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && village_whip_handleBranch29(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 30 && village_whip_handleBranch30(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 31 && village_whip_handleBranch31(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 35 && village_whip_handleBranch35(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 38 && village_whip_handleBranch38(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.village_whip.branchId");
        return SCRIPT_CONTINUE;
    }
}
