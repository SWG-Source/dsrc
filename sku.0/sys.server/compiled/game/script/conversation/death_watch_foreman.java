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
import script.library.player_structure;
import script.library.utils;

public class death_watch_foreman extends script.base_script
{
    public death_watch_foreman()
    {
    }
    public static String c_stringFile = "conversation/death_watch_foreman";
    public boolean death_watch_foreman_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean death_watch_foreman_condition_startMission(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "death_watch.drill_completed") || hasObjVar(player, "death_watch.drill_kill"))
        {
            return false;
        }
        if (hasObjVar(player, "death_watch.drill_02") || hasObjVar(player, "death_watch.drill_03"))
        {
            return false;
        }
        obj_id structure = getTopMostContainer(npc);
        if (!hasObjVar(structure, "death_watch.haldo") && !hasObjVar(player, "death_watch.drill_01"))
        {
            return true;
        }
        return false;
    }
    public boolean death_watch_foreman_condition_notCompleted(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "death_watch.medicine_success") || hasObjVar(player, "death_watch.medicine_kill"))
        {
            return false;
        }
        obj_id structure = getTopMostContainer(npc);
        obj_id oldPlayer = getObjIdObjVar(structure, "death_watch.haldo_player");
        if (hasObjVar(player, "death_watch.drill_01"))
        {
            if (hasObjVar(structure, "death_watch.haldo") && oldPlayer == player)
            {
                return true;
            }
        }
        return false;
    }
    public boolean death_watch_foreman_condition_helpedHaldo(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "death_watch.drill_01") && hasObjVar(player, "death_watch.medicine_success"))
        {
            return true;
        }
        return false;
    }
    public boolean death_watch_foreman_condition_alreadyBeingHelped(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "death_watch.drill_completed") || hasObjVar(player, "death_watch.drill_kill"))
        {
            return false;
        }
        if (hasObjVar(player, "death_watch.drill_02") || hasObjVar(player, "death_watch.drill_03"))
        {
            return false;
        }
        obj_id structure = getTopMostContainer(npc);
        if (hasObjVar(structure, "death_watch.haldo") && !hasObjVar(player, "death_watch.drill_01"))
        {
            return true;
        }
        return false;
    }
    public boolean death_watch_foreman_condition_killedHaldo(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "death_watch.drill_01") && hasObjVar(player, "death_watch.medicine_kill"))
        {
            return true;
        }
        return false;
    }
    public boolean death_watch_foreman_condition_notCompletedBattery(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasObjVar(player, "death_watch.drill_02"))
        {
            return false;
        }
        obj_id[] objContents = utils.getContents(player, true);
        if (objContents != null)
        {
            for (int intI = 0; intI < objContents.length; intI++)
            {
                String strItemTemplate = getTemplateName(objContents[intI]);
                if (strItemTemplate.equals("object/tangible/dungeon/death_watch_bunker/drill_battery_clean.iff"))
                {
                    return false;
                }
            }
        }
        return true;
    }
    public boolean death_watch_foreman_condition_batteryCleaned(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id structure = getTopMostContainer(npc);
        if (hasObjVar(structure, "death_watch.water_pressure_mission"))
        {
            return false;
        }
        if (!hasObjVar(player, "death_watch.drill_02"))
        {
            return false;
        }
        if (hasObjVar(player, "death_watch.medicine_kill"))
        {
            return false;
        }
        obj_id[] objContents = utils.getContents(player, true);
        if (objContents != null)
        {
            for (int intI = 0; intI < objContents.length; intI++)
            {
                String strItemTemplate = getTemplateName(objContents[intI]);
                if (strItemTemplate.equals("object/tangible/dungeon/death_watch_bunker/drill_battery_clean.iff"))
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean death_watch_foreman_condition_waterPressureStartnoKill(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id structure = getTopMostContainer(npc);
        if (hasObjVar(structure, "death_watch.water_pressure_mission"))
        {
            return false;
        }
        if (hasObjVar(player, "death_watch.drill_03") && hasObjVar(player, "death_watch.medicine_success"))
        {
            return true;
        }
        return false;
    }
    public boolean death_watch_foreman_condition_waterPressureStartKill(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id structure = getTopMostContainer(npc);
        if (hasObjVar(structure, "death_watch.water_pressure_mission"))
        {
            return false;
        }
        if (hasObjVar(player, "death_watch.drill_03") && hasObjVar(player, "death_watch.medicine_kill"))
        {
            return true;
        }
        return false;
    }
    public boolean death_watch_foreman_condition_batteryCleanedKill(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasObjVar(player, "death_watch.drill_02"))
        {
            return false;
        }
        if (!hasObjVar(player, "death_watch.medicine_kill"))
        {
            return false;
        }
        obj_id[] objContents = utils.getContents(player, true);
        if (objContents != null)
        {
            for (int intI = 0; intI < objContents.length; intI++)
            {
                String strItemTemplate = getTemplateName(objContents[intI]);
                if (strItemTemplate.equals("object/tangible/dungeon/death_watch_bunker/drill_battery_clean.iff"))
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean death_watch_foreman_condition_notCompletedWater(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id structure = getTopMostContainer(npc);
        if (hasObjVar(player, "death_watch.drill_03"))
        {
            if (hasObjVar(player, "death_watch.water_pressure"))
            {
                obj_id oldPlayer = getObjIdObjVar(structure, "death_watch.water_pressure_mission");
                if (oldPlayer == player)
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean death_watch_foreman_condition_waterPressureSuccess(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasObjVar(player, "death_watch.drill_03"))
        {
            return false;
        }
        if (hasObjVar(player, "death_watch.water_pressure_success") && !hasObjVar(player, "death_watch.medicine_kill"))
        {
            return true;
        }
        return false;
    }
    public boolean death_watch_foreman_condition_waterPressureSuccessKill(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasObjVar(player, "death_watch.drill_03"))
        {
            return false;
        }
        if (hasObjVar(player, "death_watch.water_pressure_success") && hasObjVar(player, "death_watch.medicine_kill"))
        {
            return true;
        }
        return false;
    }
    public boolean death_watch_foreman_condition_getMoreOreNotReady(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "death_watch.drill_completed"))
        {
            int comTime = getIntObjVar(player, "death_watch.drill_completed");
            int curTime = getGameTime();
            if (curTime < comTime + 172800)
            {
                return true;
            }
        }
        if (hasObjVar(player, "death_watch.drill_kill"))
        {
            int killTime = getIntObjVar(player, "death_watch.drill_kill");
            int curTime = getGameTime();
            if (curTime < killTime + 269200)
            {
                return true;
            }
        }
        return false;
    }
    public boolean death_watch_foreman_condition_getMoreOreReady(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "death_watch.drill_completed"))
        {
            int comTime = getIntObjVar(player, "death_watch.drill_completed");
            int curTime = getGameTime();
            if (curTime > comTime + 172800)
            {
                return true;
            }
        }
        if (hasObjVar(player, "death_watch.drill_kill"))
        {
            int killTime = getIntObjVar(player, "death_watch.drill_kill");
            int curTime = getGameTime();
            if (curTime > killTime + 269200)
            {
                return true;
            }
        }
        return false;
    }
    public boolean death_watch_foreman_condition_waterPressureFail(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "death_watch.drill_completed") || hasObjVar(player, "death_watch.drill_kill"))
        {
            return false;
        }
        if (hasObjVar(player, "death_watch.water_pressure_failed") || hasObjVar(player, "death_watch.drill_03"))
        {
            obj_id structure = getTopMostContainer(npc);
            if (hasObjVar(structure, "death_watch.water_pressure_mission"))
            {
                return false;
            }
            return true;
        }
        return false;
    }
    public boolean death_watch_foreman_condition_waterPressureBusy(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id structure = getTopMostContainer(npc);
        if (hasObjVar(structure, "death_watch.water_pressure_mission"))
        {
            obj_id oldPlayer = getObjIdObjVar(structure, "death_watch.water_pressure_mission");
            if (oldPlayer != player)
            {
                return true;
            }
        }
        return false;
    }
    public boolean death_watch_foreman_condition_haldoFailed(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id structure = getTopMostContainer(npc);
        if (hasObjVar(player, "death_watch.drill_01"))
        {
            if (!hasObjVar(structure, "death_watch.haldo"))
            {
                return true;
            }
        }
        return false;
    }
    public boolean death_watch_foreman_condition_noInvRoom(obj_id player, obj_id npc) throws InterruptedException
    {
        boolean hasNoInvRoom = false;
        obj_id playerInv = utils.getInventoryContainer(player);
        if (isIdValid(playerInv))
        {
            int free_space = getVolumeFree(playerInv);
            if (free_space < 1)
            {
                hasNoInvRoom = true;
            }
        }
        return hasNoInvRoom;
    }
    public void death_watch_foreman_action_helpHaldo(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "death_watch.drill_01", 1);
        dictionary params = new dictionary();
        params.put("player", player);
        messageTo(npc, "handleSpawnHaldo", params, 1f, false);
    }
    public void death_watch_foreman_action_cleanBattery(obj_id player, obj_id npc) throws InterruptedException
    {
        removeObjVar(player, "death_watch.drill_01");
        setObjVar(player, "death_watch.drill_02", 1);
        return;
    }
    public void death_watch_foreman_action_takeBattery(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id[] objContents = utils.getContents(player, true);
        if (objContents != null)
        {
            for (int intI = 0; intI < objContents.length; intI++)
            {
                String strItemTemplate = getTemplateName(objContents[intI]);
                if (strItemTemplate.equals("object/tangible/dungeon/death_watch_bunker/drill_battery_clean.iff"))
                {
                    obj_id battery = objContents[intI];
                    destroyObject(battery);
                }
            }
        }
        removeObjVar(player, "death_watch.drill_02");
        setObjVar(player, "death_watch.drill_03", 1);
        return;
    }
    public void death_watch_foreman_action_startWaterPressure(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "death_watch.water_pressure", 1);
        setObjVar(npc, "death_watch.start_water_pressure", 1);
        obj_id structure = player_structure.getStructure(player);
        if (!isIdValid(structure))
        {
            return;
        }
        setObjVar(structure, "death_watch.water_pressure_mission", player);
        messageTo(structure, "handleWaterPumpReset", null, 1f, false);
        return;
    }
    public void death_watch_foreman_action_finishMission(obj_id player, obj_id npc) throws InterruptedException
    {
        int time = getGameTime();
        if (hasObjVar(player, "death_watch.medicine_kill"))
        {
            removeObjVar(player, "death_watch.medicine_kill");
            removeObjVar(player, "death_watch.drill_03");
            removeObjVar(player, "death_watch.water_pressure_success");
            setObjVar(player, "death_watch.drill_kill", time);
        }
        else 
        {
            removeObjVar(player, "death_watch.medicine_success");
            removeObjVar(player, "death_watch.drill_03");
            removeObjVar(player, "death_watch.water_pressure_success");
            setObjVar(player, "death_watch.drill_completed", time);
        }
        obj_id playerInv = getObjectInSlot(player, "inventory");
        if (isIdValid(playerInv))
        {
            obj_id item = createObject("object/tangible/loot/dungeon/death_watch_bunker/mining_drill_reward.iff", playerInv, "");
        }
        return;
    }
    public void death_watch_foreman_action_giveReward(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "death_watch.drill_completed"))
        {
            int curTime = getGameTime();
            setObjVar(player, "death_watch.drill_completed", curTime);
        }
        else if (hasObjVar(player, "death_watch.drill_kill"))
        {
            int curTime = getGameTime();
            setObjVar(player, "death_watch.drill_kill", curTime);
        }
        obj_id playerInv = getObjectInSlot(player, "inventory");
        if (isIdValid(playerInv))
        {
            obj_id item = createObject("object/tangible/loot/dungeon/death_watch_bunker/mining_drill_reward.iff", playerInv, "");
        }
        return;
    }
    public void death_watch_foreman_action_declineHaldo(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id structure = getTopMostContainer(npc);
        obj_id haldo = getObjIdObjVar(structure, "death_watch.haldo");
        messageTo(haldo, "handleCrazedCleanup", null, 1f, false);
        return;
    }
    public void death_watch_foreman_action_declineWater(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id structure = getTopMostContainer(npc);
        removeObjVar(player, "death_watch.water_pressure");
        removeObjVar(structure, "death_watch.water_pressure_mission");
        setObjVar(player, "death_watch.water_pressure_failed", 1);
        return;
    }
    public void death_watch_foreman_action_cleanOldPlayers(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "death_watch.water_pressure"))
        {
            removeObjVar(player, "death_watch.water_pressure");
        }
        return;
    }
    public int death_watch_foreman_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1c4d0bbb"))
        {
            if (death_watch_foreman_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_7a654750");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (death_watch_foreman_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (death_watch_foreman_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7d65b570");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2de77a24");
                    }
                    utils.setScriptVar(player, "conversation.death_watch_foreman.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.death_watch_foreman.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_637e6c80"))
        {
            if (death_watch_foreman_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_81b8b95b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (death_watch_foreman_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (death_watch_foreman_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_57097d43");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7d2eea07");
                    }
                    utils.setScriptVar(player, "conversation.death_watch_foreman.branchId", 19);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.death_watch_foreman.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int death_watch_foreman_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_7d65b570"))
        {
            if (death_watch_foreman_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_dd6f26cc");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (death_watch_foreman_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_82599d96");
                    }
                    utils.setScriptVar(player, "conversation.death_watch_foreman.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.death_watch_foreman.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_2de77a24"))
        {
            if (death_watch_foreman_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_ac0e598b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (death_watch_foreman_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (death_watch_foreman_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_dc1d4717");
                    }
                    utils.setScriptVar(player, "conversation.death_watch_foreman.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.death_watch_foreman.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int death_watch_foreman_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_82599d96"))
        {
            if (death_watch_foreman_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1fe41c46");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (death_watch_foreman_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (death_watch_foreman_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_687ef712");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_968923c1");
                    }
                    utils.setScriptVar(player, "conversation.death_watch_foreman.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.death_watch_foreman.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int death_watch_foreman_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_687ef712"))
        {
            if (death_watch_foreman_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_c2d12633");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (death_watch_foreman_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (death_watch_foreman_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8bbe4b84");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_19ecd0cd");
                    }
                    utils.setScriptVar(player, "conversation.death_watch_foreman.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.death_watch_foreman.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_968923c1"))
        {
            if (death_watch_foreman_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_b75a73ed");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (death_watch_foreman_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_58a87fe");
                    }
                    utils.setScriptVar(player, "conversation.death_watch_foreman.branchId", 16);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.death_watch_foreman.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int death_watch_foreman_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_8bbe4b84"))
        {
            if (death_watch_foreman_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_471cd71d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (death_watch_foreman_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (death_watch_foreman_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2236f9d7");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1265571c");
                    }
                    utils.setScriptVar(player, "conversation.death_watch_foreman.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.death_watch_foreman.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_19ecd0cd"))
        {
            if (death_watch_foreman_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_645da2b5");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (death_watch_foreman_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_35");
                    }
                    utils.setScriptVar(player, "conversation.death_watch_foreman.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.death_watch_foreman.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int death_watch_foreman_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_2236f9d7"))
        {
            if (death_watch_foreman_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_45d9624a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (death_watch_foreman_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6c1f32dd");
                    }
                    utils.setScriptVar(player, "conversation.death_watch_foreman.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.death_watch_foreman.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1265571c"))
        {
            if (death_watch_foreman_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_c7f1e2ee");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (death_watch_foreman_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f73c5ae7");
                    }
                    utils.setScriptVar(player, "conversation.death_watch_foreman.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.death_watch_foreman.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int death_watch_foreman_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6c1f32dd"))
        {
            if (death_watch_foreman_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_d1b18b50");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (death_watch_foreman_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (death_watch_foreman_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ce027a15");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_71ad5afa");
                    }
                    utils.setScriptVar(player, "conversation.death_watch_foreman.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.death_watch_foreman.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int death_watch_foreman_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_ce027a15"))
        {
            if (death_watch_foreman_condition__defaultCondition(player, npc))
            {
                death_watch_foreman_action_helpHaldo(player, npc);
                string_id message = new string_id(c_stringFile, "s_b7c0824");
                utils.removeScriptVar(player, "conversation.death_watch_foreman.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_71ad5afa"))
        {
            if (death_watch_foreman_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_f79fa021");
                utils.removeScriptVar(player, "conversation.death_watch_foreman.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int death_watch_foreman_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_f73c5ae7"))
        {
            if (death_watch_foreman_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_44557f4a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (death_watch_foreman_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (death_watch_foreman_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_26");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_29");
                    }
                    utils.setScriptVar(player, "conversation.death_watch_foreman.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.death_watch_foreman.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int death_watch_foreman_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_26"))
        {
            if (death_watch_foreman_condition__defaultCondition(player, npc))
            {
                death_watch_foreman_action_helpHaldo(player, npc);
                string_id message = new string_id(c_stringFile, "s_f4fd5da3");
                utils.removeScriptVar(player, "conversation.death_watch_foreman.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_29"))
        {
            if (death_watch_foreman_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_31");
                utils.removeScriptVar(player, "conversation.death_watch_foreman.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int death_watch_foreman_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_35"))
        {
            if (death_watch_foreman_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_471cd71d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (death_watch_foreman_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (death_watch_foreman_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2236f9d7");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1265571c");
                    }
                    utils.setScriptVar(player, "conversation.death_watch_foreman.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.death_watch_foreman.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int death_watch_foreman_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_58a87fe"))
        {
            if (death_watch_foreman_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_c2d12633");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (death_watch_foreman_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (death_watch_foreman_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8bbe4b84");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_19ecd0cd");
                    }
                    utils.setScriptVar(player, "conversation.death_watch_foreman.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.death_watch_foreman.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int death_watch_foreman_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_42"))
        {
            if (death_watch_foreman_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_c2d12633");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (death_watch_foreman_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (death_watch_foreman_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8bbe4b84");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_19ecd0cd");
                    }
                    utils.setScriptVar(player, "conversation.death_watch_foreman.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.death_watch_foreman.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_dc1d4717"))
        {
            if (death_watch_foreman_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_e83ca277");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (death_watch_foreman_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4e099c6a");
                    }
                    utils.setScriptVar(player, "conversation.death_watch_foreman.branchId", 18);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.death_watch_foreman.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int death_watch_foreman_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_4e099c6a"))
        {
            if (death_watch_foreman_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_c2d12633");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (death_watch_foreman_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (death_watch_foreman_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8bbe4b84");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_19ecd0cd");
                    }
                    utils.setScriptVar(player, "conversation.death_watch_foreman.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.death_watch_foreman.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int death_watch_foreman_handleBranch19(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_57097d43"))
        {
            if (death_watch_foreman_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_6d069da0");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (death_watch_foreman_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (death_watch_foreman_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f2b532b6");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_baac9005");
                    }
                    utils.setScriptVar(player, "conversation.death_watch_foreman.branchId", 20);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.death_watch_foreman.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_7d2eea07"))
        {
            if (death_watch_foreman_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_dd6f26cc");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (death_watch_foreman_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_82599d96");
                    }
                    utils.setScriptVar(player, "conversation.death_watch_foreman.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.death_watch_foreman.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int death_watch_foreman_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_f2b532b6"))
        {
            if (death_watch_foreman_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_c2d12633");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (death_watch_foreman_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (death_watch_foreman_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8bbe4b84");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_19ecd0cd");
                    }
                    utils.setScriptVar(player, "conversation.death_watch_foreman.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.death_watch_foreman.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_baac9005"))
        {
            if (death_watch_foreman_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_31");
                utils.removeScriptVar(player, "conversation.death_watch_foreman.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int death_watch_foreman_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c825f420"))
        {
            if (death_watch_foreman_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_fb5cf4cd");
                utils.removeScriptVar(player, "conversation.death_watch_foreman.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_54ee97f2"))
        {
            if (death_watch_foreman_condition__defaultCondition(player, npc))
            {
                death_watch_foreman_action_declineHaldo(player, npc);
                string_id message = new string_id(c_stringFile, "s_d8f6c63f");
                utils.removeScriptVar(player, "conversation.death_watch_foreman.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int death_watch_foreman_handleBranch24(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_bdb717fb"))
        {
            if (death_watch_foreman_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_9cf7119e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (death_watch_foreman_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (death_watch_foreman_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_dd657c44");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f7962ef3");
                    }
                    utils.setScriptVar(player, "conversation.death_watch_foreman.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.death_watch_foreman.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int death_watch_foreman_handleBranch25(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_dd657c44"))
        {
            if (death_watch_foreman_condition__defaultCondition(player, npc))
            {
                death_watch_foreman_action_cleanBattery(player, npc);
                string_id message = new string_id(c_stringFile, "s_63fae5b4");
                utils.removeScriptVar(player, "conversation.death_watch_foreman.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_f7962ef3"))
        {
            if (death_watch_foreman_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_51b7892e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (death_watch_foreman_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (death_watch_foreman_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4d8102d5");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a618b93a");
                    }
                    utils.setScriptVar(player, "conversation.death_watch_foreman.branchId", 27);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.death_watch_foreman.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int death_watch_foreman_handleBranch27(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_4d8102d5"))
        {
            if (death_watch_foreman_condition__defaultCondition(player, npc))
            {
                death_watch_foreman_action_cleanBattery(player, npc);
                string_id message = new string_id(c_stringFile, "s_787e689e");
                utils.removeScriptVar(player, "conversation.death_watch_foreman.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_a618b93a"))
        {
            if (death_watch_foreman_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_31e8b478");
                utils.removeScriptVar(player, "conversation.death_watch_foreman.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int death_watch_foreman_handleBranch30(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_145fd34"))
        {
            if (death_watch_foreman_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3b67f1eb");
                utils.removeScriptVar(player, "conversation.death_watch_foreman.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_3055077f"))
        {
            if (death_watch_foreman_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_579c9bc5");
                utils.removeScriptVar(player, "conversation.death_watch_foreman.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int death_watch_foreman_handleBranch33(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_d62e85a9"))
        {
            if (death_watch_foreman_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_9b4dd6c7");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (death_watch_foreman_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5dbddef");
                    }
                    utils.setScriptVar(player, "conversation.death_watch_foreman.branchId", 34);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.death_watch_foreman.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int death_watch_foreman_handleBranch34(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_5dbddef"))
        {
            if (death_watch_foreman_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_d1eff807");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (death_watch_foreman_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (death_watch_foreman_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b5a845f9");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_728cbacc");
                    }
                    utils.setScriptVar(player, "conversation.death_watch_foreman.branchId", 35);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.death_watch_foreman.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int death_watch_foreman_handleBranch35(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_b5a845f9"))
        {
            if (death_watch_foreman_condition__defaultCondition(player, npc))
            {
                death_watch_foreman_action_cleanBattery(player, npc);
                string_id message = new string_id(c_stringFile, "s_bc5213a");
                utils.removeScriptVar(player, "conversation.death_watch_foreman.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_728cbacc"))
        {
            if (death_watch_foreman_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_23237572");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (death_watch_foreman_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (death_watch_foreman_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6204ccac");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_85");
                    }
                    utils.setScriptVar(player, "conversation.death_watch_foreman.branchId", 37);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.death_watch_foreman.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int death_watch_foreman_handleBranch37(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6204ccac"))
        {
            if (death_watch_foreman_condition__defaultCondition(player, npc))
            {
                death_watch_foreman_action_cleanBattery(player, npc);
                string_id message = new string_id(c_stringFile, "s_bc5213a");
                utils.removeScriptVar(player, "conversation.death_watch_foreman.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_85"))
        {
            if (death_watch_foreman_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_87");
                utils.removeScriptVar(player, "conversation.death_watch_foreman.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int death_watch_foreman_handleBranch39(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_7fe1c6d7"))
        {
            if (death_watch_foreman_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3f363bf3");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (death_watch_foreman_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_44bb7cbc");
                    }
                    utils.setScriptVar(player, "conversation.death_watch_foreman.branchId", 40);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.death_watch_foreman.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_93"))
        {
            if (death_watch_foreman_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_286d283f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (death_watch_foreman_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (death_watch_foreman_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_83d1549d");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b491aba5");
                    }
                    utils.setScriptVar(player, "conversation.death_watch_foreman.branchId", 41);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.death_watch_foreman.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int death_watch_foreman_handleBranch40(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_44bb7cbc"))
        {
            if (death_watch_foreman_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_286d283f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (death_watch_foreman_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (death_watch_foreman_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_83d1549d");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b491aba5");
                    }
                    utils.setScriptVar(player, "conversation.death_watch_foreman.branchId", 41);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.death_watch_foreman.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int death_watch_foreman_handleBranch41(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_83d1549d"))
        {
            if (death_watch_foreman_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_e5e9e886");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (death_watch_foreman_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (death_watch_foreman_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d4b1da9f");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_101");
                    }
                    utils.setScriptVar(player, "conversation.death_watch_foreman.branchId", 42);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.death_watch_foreman.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_b491aba5"))
        {
            if (death_watch_foreman_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_8deeb761");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (death_watch_foreman_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (death_watch_foreman_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_107");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_113");
                    }
                    utils.setScriptVar(player, "conversation.death_watch_foreman.branchId", 46);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.death_watch_foreman.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int death_watch_foreman_handleBranch42(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_d4b1da9f"))
        {
            if (death_watch_foreman_condition_waterPressureStartnoKill(player, npc))
            {
                death_watch_foreman_action_startWaterPressure(player, npc);
                string_id message = new string_id(c_stringFile, "s_262e7b86");
                utils.removeScriptVar(player, "conversation.death_watch_foreman.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (death_watch_foreman_condition_waterPressureStartKill(player, npc))
            {
                death_watch_foreman_action_startWaterPressure(player, npc);
                string_id message = new string_id(c_stringFile, "s_8405256c");
                utils.removeScriptVar(player, "conversation.death_watch_foreman.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_101"))
        {
            if (death_watch_foreman_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_103");
                utils.removeScriptVar(player, "conversation.death_watch_foreman.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int death_watch_foreman_handleBranch46(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_107"))
        {
            if (death_watch_foreman_condition_waterPressureStartnoKill(player, npc))
            {
                death_watch_foreman_action_startWaterPressure(player, npc);
                string_id message = new string_id(c_stringFile, "s_109");
                utils.removeScriptVar(player, "conversation.death_watch_foreman.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (death_watch_foreman_condition_waterPressureStartKill(player, npc))
            {
                death_watch_foreman_action_startWaterPressure(player, npc);
                string_id message = new string_id(c_stringFile, "s_111");
                utils.removeScriptVar(player, "conversation.death_watch_foreman.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_113"))
        {
            if (death_watch_foreman_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_115");
                utils.removeScriptVar(player, "conversation.death_watch_foreman.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int death_watch_foreman_handleBranch50(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_a33b6055"))
        {
            if (death_watch_foreman_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_5c4119d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (death_watch_foreman_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_120");
                    }
                    utils.setScriptVar(player, "conversation.death_watch_foreman.branchId", 51);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.death_watch_foreman.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_122"))
        {
            if (death_watch_foreman_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_286d283f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (death_watch_foreman_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (death_watch_foreman_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_83d1549d");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b491aba5");
                    }
                    utils.setScriptVar(player, "conversation.death_watch_foreman.branchId", 41);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.death_watch_foreman.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int death_watch_foreman_handleBranch51(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_120"))
        {
            if (death_watch_foreman_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_286d283f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (death_watch_foreman_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (death_watch_foreman_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_83d1549d");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b491aba5");
                    }
                    utils.setScriptVar(player, "conversation.death_watch_foreman.branchId", 41);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.death_watch_foreman.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int death_watch_foreman_handleBranch52(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_9bea7011"))
        {
            if (death_watch_foreman_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_6864fcd4");
                utils.removeScriptVar(player, "conversation.death_watch_foreman.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_127"))
        {
            if (death_watch_foreman_condition__defaultCondition(player, npc))
            {
                death_watch_foreman_action_declineWater(player, npc);
                string_id message = new string_id(c_stringFile, "s_129");
                utils.removeScriptVar(player, "conversation.death_watch_foreman.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int death_watch_foreman_handleBranch55(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_5597accd"))
        {
            if (death_watch_foreman_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_b4feb773");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (death_watch_foreman_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ed0d4e5a");
                    }
                    utils.setScriptVar(player, "conversation.death_watch_foreman.branchId", 56);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.death_watch_foreman.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int death_watch_foreman_handleBranch56(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_ed0d4e5a"))
        {
            if (!death_watch_foreman_condition_noInvRoom(player, npc))
            {
                death_watch_foreman_action_finishMission(player, npc);
                string_id message = new string_id(c_stringFile, "s_18426338");
                utils.removeScriptVar(player, "conversation.death_watch_foreman.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (death_watch_foreman_condition_noInvRoom(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_52f3e83e");
                utils.removeScriptVar(player, "conversation.death_watch_foreman.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int death_watch_foreman_handleBranch59(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c04f0d0d"))
        {
            if (death_watch_foreman_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_95879f15");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (death_watch_foreman_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_140");
                    }
                    utils.setScriptVar(player, "conversation.death_watch_foreman.branchId", 60);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.death_watch_foreman.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int death_watch_foreman_handleBranch60(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_140"))
        {
            if (!death_watch_foreman_condition_noInvRoom(player, npc))
            {
                death_watch_foreman_action_finishMission(player, npc);
                string_id message = new string_id(c_stringFile, "s_142");
                utils.removeScriptVar(player, "conversation.death_watch_foreman.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (death_watch_foreman_condition_noInvRoom(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_144");
                utils.removeScriptVar(player, "conversation.death_watch_foreman.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int death_watch_foreman_handleBranch64(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_dbf0d0f9"))
        {
            if (!death_watch_foreman_condition_noInvRoom(player, npc))
            {
                death_watch_foreman_action_giveReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_829888a9");
                utils.removeScriptVar(player, "conversation.death_watch_foreman.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (death_watch_foreman_condition_noInvRoom(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_5af847c7");
                utils.removeScriptVar(player, "conversation.death_watch_foreman.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int death_watch_foreman_handleBranch68(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_e55f453d"))
        {
            if (death_watch_foreman_condition__defaultCondition(player, npc))
            {
                death_watch_foreman_action_startWaterPressure(player, npc);
                string_id message = new string_id(c_stringFile, "s_6b8a415c");
                utils.removeScriptVar(player, "conversation.death_watch_foreman.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_b99aaac2"))
        {
            if (death_watch_foreman_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_23cbff5d");
                utils.removeScriptVar(player, "conversation.death_watch_foreman.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int death_watch_foreman_handleBranch72(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_26fa42a7"))
        {
            if (death_watch_foreman_condition__defaultCondition(player, npc))
            {
                death_watch_foreman_action_helpHaldo(player, npc);
                string_id message = new string_id(c_stringFile, "s_de774e37");
                utils.removeScriptVar(player, "conversation.death_watch_foreman.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_f501c596"))
        {
            if (death_watch_foreman_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_8f36c784");
                utils.removeScriptVar(player, "conversation.death_watch_foreman.branchId");
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
            detachScript(self, "conversation.death_watch_foreman");
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
        detachScript(self, "conversation.death_watch_foreman");
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
        if (death_watch_foreman_condition_startMission(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_8dc6968d");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (death_watch_foreman_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (death_watch_foreman_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1c4d0bbb");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_637e6c80");
                }
                utils.setScriptVar(player, "conversation.death_watch_foreman.branchId", 1);
                npcStartConversation(player, npc, "death_watch_foreman", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (death_watch_foreman_condition_notCompleted(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_4f17ac67");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (death_watch_foreman_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (death_watch_foreman_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_c825f420");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_54ee97f2");
                }
                utils.setScriptVar(player, "conversation.death_watch_foreman.branchId", 21);
                npcStartConversation(player, npc, "death_watch_foreman", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (death_watch_foreman_condition_helpedHaldo(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_27b9acbc");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (death_watch_foreman_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_bdb717fb");
                }
                utils.setScriptVar(player, "conversation.death_watch_foreman.branchId", 24);
                npcStartConversation(player, npc, "death_watch_foreman", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (death_watch_foreman_condition_notCompletedBattery(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_7b9dc7c7");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (death_watch_foreman_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (death_watch_foreman_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_145fd34");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_3055077f");
                }
                utils.setScriptVar(player, "conversation.death_watch_foreman.branchId", 30);
                npcStartConversation(player, npc, "death_watch_foreman", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (death_watch_foreman_condition_killedHaldo(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_3310d1b6");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (death_watch_foreman_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_d62e85a9");
                }
                utils.setScriptVar(player, "conversation.death_watch_foreman.branchId", 33);
                npcStartConversation(player, npc, "death_watch_foreman", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (death_watch_foreman_condition_batteryCleaned(player, npc))
        {
            death_watch_foreman_action_takeBattery(player, npc);
            string_id message = new string_id(c_stringFile, "s_dd7c0dd3");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (death_watch_foreman_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (death_watch_foreman_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_7fe1c6d7");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_93");
                }
                utils.setScriptVar(player, "conversation.death_watch_foreman.branchId", 39);
                npcStartConversation(player, npc, "death_watch_foreman", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (death_watch_foreman_condition_batteryCleanedKill(player, npc))
        {
            death_watch_foreman_action_takeBattery(player, npc);
            string_id message = new string_id(c_stringFile, "s_f46d76f6");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (death_watch_foreman_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (death_watch_foreman_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_a33b6055");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_122");
                }
                utils.setScriptVar(player, "conversation.death_watch_foreman.branchId", 50);
                npcStartConversation(player, npc, "death_watch_foreman", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (death_watch_foreman_condition_notCompletedWater(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_de4a182e");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (death_watch_foreman_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (death_watch_foreman_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_9bea7011");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_127");
                }
                utils.setScriptVar(player, "conversation.death_watch_foreman.branchId", 52);
                npcStartConversation(player, npc, "death_watch_foreman", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (death_watch_foreman_condition_waterPressureSuccess(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_c1dc6296");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (death_watch_foreman_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_5597accd");
                }
                utils.setScriptVar(player, "conversation.death_watch_foreman.branchId", 55);
                npcStartConversation(player, npc, "death_watch_foreman", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (death_watch_foreman_condition_waterPressureSuccessKill(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1b208070");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (death_watch_foreman_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_c04f0d0d");
                }
                utils.setScriptVar(player, "conversation.death_watch_foreman.branchId", 59);
                npcStartConversation(player, npc, "death_watch_foreman", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (death_watch_foreman_condition_getMoreOreNotReady(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1d1d45a9");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (death_watch_foreman_condition_getMoreOreReady(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_e1dd8e6e");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (death_watch_foreman_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_dbf0d0f9");
                }
                utils.setScriptVar(player, "conversation.death_watch_foreman.branchId", 64);
                npcStartConversation(player, npc, "death_watch_foreman", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (death_watch_foreman_condition_alreadyBeingHelped(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_9e37f61f");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (death_watch_foreman_condition_waterPressureFail(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_4af1601a");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (death_watch_foreman_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (death_watch_foreman_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_e55f453d");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_b99aaac2");
                }
                utils.setScriptVar(player, "conversation.death_watch_foreman.branchId", 68);
                npcStartConversation(player, npc, "death_watch_foreman", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (death_watch_foreman_condition_waterPressureBusy(player, npc))
        {
            death_watch_foreman_action_cleanOldPlayers(player, npc);
            string_id message = new string_id(c_stringFile, "s_d9db9b00");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (death_watch_foreman_condition_haldoFailed(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1511575f");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (death_watch_foreman_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (death_watch_foreman_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_26fa42a7");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_f501c596");
                }
                utils.setScriptVar(player, "conversation.death_watch_foreman.branchId", 72);
                npcStartConversation(player, npc, "death_watch_foreman", message, responses);
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
        if (!conversationId.equals("death_watch_foreman"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.death_watch_foreman.branchId");
        if (branchId == 1 && death_watch_foreman_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && death_watch_foreman_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && death_watch_foreman_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && death_watch_foreman_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && death_watch_foreman_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && death_watch_foreman_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && death_watch_foreman_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && death_watch_foreman_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && death_watch_foreman_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && death_watch_foreman_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && death_watch_foreman_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && death_watch_foreman_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && death_watch_foreman_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && death_watch_foreman_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && death_watch_foreman_handleBranch19(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && death_watch_foreman_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && death_watch_foreman_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && death_watch_foreman_handleBranch24(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && death_watch_foreman_handleBranch25(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 27 && death_watch_foreman_handleBranch27(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 30 && death_watch_foreman_handleBranch30(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 33 && death_watch_foreman_handleBranch33(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 34 && death_watch_foreman_handleBranch34(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 35 && death_watch_foreman_handleBranch35(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 37 && death_watch_foreman_handleBranch37(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 39 && death_watch_foreman_handleBranch39(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 40 && death_watch_foreman_handleBranch40(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 41 && death_watch_foreman_handleBranch41(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 42 && death_watch_foreman_handleBranch42(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 46 && death_watch_foreman_handleBranch46(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 50 && death_watch_foreman_handleBranch50(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 51 && death_watch_foreman_handleBranch51(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 52 && death_watch_foreman_handleBranch52(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 55 && death_watch_foreman_handleBranch55(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 56 && death_watch_foreman_handleBranch56(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 59 && death_watch_foreman_handleBranch59(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 60 && death_watch_foreman_handleBranch60(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 64 && death_watch_foreman_handleBranch64(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 68 && death_watch_foreman_handleBranch68(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 72 && death_watch_foreman_handleBranch72(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.death_watch_foreman.branchId");
        return SCRIPT_CONTINUE;
    }
}
