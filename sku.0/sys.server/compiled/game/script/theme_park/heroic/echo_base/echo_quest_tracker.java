package script.theme_park.heroic.echo_base;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;
import script.library.trial;
import script.library.utils;

public class echo_quest_tracker extends script.base_script
{
    public echo_quest_tracker()
    {
    }
    public static final String PHASE = "phase";
    public static final String P1_DEAD_AT = "p1_dead_at";
    public static final String P1_GENERATOR_DESTROYED = "p1_generator_destroyed";
    public static final String P1_IMPERIAL_GRANT = "imperial_phase_1";
    public static final String P1_REBEL_GRANT = "rebel_phase_1";
    public static final String P2_IMPERIAL_GRANT = "heroic_echo_imperial_phase_2";
    public static final String P2_REBEL_GRANT = "heroic_echo_rebel_phase_2_v2";
    public static final String P2_REBEL_QUEST_UNUSED_V1 = "heroic_echo_rebel_phase_2";
    public static final String P3_IMPERIAL_GRANT = "heroic_echo_imperial_evac";
    public static final String P3_REBEL_GRANT = "heroic_echo_rebel_evac";
    public static final String PHASE3_STARTED = "phase3_started";
    public static final String P3_COMPLETE = "phase3_complete";
    public static final String[] P2_IMPERIAL_SIGNALS = 
    {
        "imp_phase2_food",
        "imp_phase2_ion_capacitor",
        "imp_phase2_command",
        "imp_phase2_hangar_bay",
        "imp_phase2_medical",
        "imp_phase2_equipment",
        "imp_food_destroyed",
        "imp_medical_destroyed",
        "imp_equipment_destroyed"
    };
    public static final String P2_IMP_FOOD_DESTROYED = "imp_food_destroyed";
    public static final String P2_IMP_MEDICAL_DESTROYED = "imp_medical_destroyed";
    public static final String P2_IMP_EQUIPMENT_DESTROYED = "imp_equipment_destroyed";
    public static final String[] P2_IMPERIAL_FAIL_TASKS = 
    {
        "imp_phase2_food_crate_task",
        "imp_phase2_medical_crate_task",
        "imp_phase2_equip_crate_task",
        "imp_phase2_ion_capacitor_task",
        "imp_phase2_command_center_task",
        "imp_phase2_hangar_bay_task"
    };
    public static final String[] P2_REBEL_SIGNALS = 
    {
        "p2_rebel_secure_thermal_power",
        "p2_rebel_secure_lower_hangar",
        "p2_rebel_secure_med_bay",
        "p2_rebel_secure_command_center",
        "reb_non_essential_personnel",
        "reb_command_personnel",
        "reb_phase2_command",
        "reb_medical_personnel",
        "reb_thermal_evac",
        "reb_equipment_evac"
    };
    public static final String P2_REBEL_COMMAND_PERSONNEL = "reb_command_personnel";
    public static final String P2_REBEL_MEDICAL_PERSONNEL = "reb_medical_personnel";
    public static final String P2_REBEL_NON_ESSENTIAL_PERSONNEL = "reb_non_essential_personnel";
    public static final String P2_REBEL_THERMAL_EVAC = "reb_thermal_evac";
    public static final String P2_REBEL_EQUIPMENT_EVAC = "reb_equipment_evac";
    public static final String[] P2_REBEL_FAIL_TASKS = 
    {
        "reb_phase2_command_center_task",
        "reb_phase2_thermal_personnel_task",
        "reb_phase2_command_personnel_task",
        "reb_phase2_medical_personnel_task",
        "reb_phase2_equipment_personnel_task",
        "reb_phase2_nonessential_personnel_task"
    };
    public static final String P3_REBEL_SUPPLIES_LOADED_1 = "reb_supplies_loaded_1";
    public static final String P3_REBEL_SUPPLIES_LOADED_2 = "reb_supplies_loaded_2";
    public static final String P3_REBEL_SUPPLIES_LOADED_3 = "reb_supplies_loaded_3";
    public static final String P3_REBEL_SUPPLIES_LOADED_4 = "reb_supplies_loaded_4";
    public static final String P3_REBEL_SUPPLIES_LOADED_5 = "reb_supplies_loaded_5";
    public static final String P3_REB_TRANSPORT_AWAY = "reb_transport_away";
    public static final String[] P3_REBEL = 
    {
        P3_REBEL_SUPPLIES_LOADED_1,
        P3_REBEL_SUPPLIES_LOADED_2,
        P3_REBEL_SUPPLIES_LOADED_3,
        P3_REBEL_SUPPLIES_LOADED_4,
        P3_REBEL_SUPPLIES_LOADED_5,
        P3_REB_TRANSPORT_AWAY
    };
    public static final String[] P3_REBEL_FAIL_TASKS = 
    {
        "load_transport_one",
        "load_transport_two",
        "load_transport_three",
        "load_transport_four",
        "load_transport_five",
        "fake_task_la_la"
    };
    public static final String[] P3_IMPERIAL = 
    {
        "p3_supplies_destroyed",
        "p3_transport_destroyed"
    };
    public static final String[] COUNTED_UPDATES = 
    {
        P1_DEAD_AT,
        P3_IMPERIAL[0],
        P3_IMPERIAL[1],
        P2_REBEL_COMMAND_PERSONNEL,
        P2_REBEL_MEDICAL_PERSONNEL,
        P2_REBEL_NON_ESSENTIAL_PERSONNEL,
        P3_REBEL_SUPPLIES_LOADED_1,
        P3_REBEL_SUPPLIES_LOADED_2,
        P3_REBEL_SUPPLIES_LOADED_3,
        P3_REBEL_SUPPLIES_LOADED_4,
        P3_REBEL_SUPPLIES_LOADED_5,
        P2_IMP_FOOD_DESTROYED,
        P2_IMP_MEDICAL_DESTROYED,
        P2_IMP_EQUIPMENT_DESTROYED,
        P2_REBEL_THERMAL_EVAC,
        P2_REBEL_EQUIPMENT_EVAC,
        P3_REB_TRANSPORT_AWAY
    };
    public static final String[] COUNTED_TASK_NAMES = 
    {
        "agrilat_plumed_rasp",
        "aakuan_anarchist",
        "aakuan_assassin",
        "aakuan_sentinal",
        "aakuan_spice_guard",
        "aakuan_warder",
        "aakuan_assassin",
        "adept_jinda_shaman",
        "adept_panshee_shaman",
        "afarathu_cult_bodyguard",
        "afarathu_cult_follower",
        "zucca_boar_blight",
        "zucca_boar_elder",
        "zucca_boar_porcellus",
        "zucca_boar_blight",
        "zucca_boar_porcellus",
        "zucca_boar"
    };
    public void questReset(obj_id self) throws InterruptedException
    {
        setObjVar(self, PHASE, 1);
        setObjVar(self, P1_DEAD_AT, 0);
        setObjVar(self, P1_GENERATOR_DESTROYED, 0);
        for (int i = 0; i < P2_IMPERIAL_SIGNALS.length; i++)
        {
            setObjVar(self, P2_IMPERIAL_SIGNALS[i], 0);
        }
        for (int i = 0; i < P2_REBEL_SIGNALS.length; i++)
        {
            setObjVar(self, P2_REBEL_SIGNALS[i], 0);
        }
        for (int i = 0; i < P3_IMPERIAL.length; i++)
        {
            setObjVar(self, P3_IMPERIAL[i], 0);
        }
        return;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        questReset(self);
        return SCRIPT_CONTINUE;
    }
    public int questReset(obj_id self, dictionary params) throws InterruptedException
    {
        questReset(self);
        return SCRIPT_CONTINUE;
    }
    public void questRemoval(obj_id player) throws InterruptedException
    {
        groundquests.clearQuest(player, P1_REBEL_GRANT);
        groundquests.clearQuest(player, P1_IMPERIAL_GRANT);
        groundquests.clearQuest(player, P2_REBEL_GRANT);
        groundquests.clearQuest(player, P2_IMPERIAL_GRANT);
        groundquests.clearQuest(player, P3_REBEL_GRANT);
        groundquests.clearQuest(player, P3_IMPERIAL_GRANT);
        groundquests.clearQuest(player, P2_REBEL_QUEST_UNUSED_V1);
    }
    public int questUpdate(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] players = utils.getPlayersInBuildoutRow(self);
        boolean doBroadcast = true;
        if (!isIdValid(self))
        {
            return SCRIPT_CONTINUE;
        }
        int isRebel = getIntObjVar(self, "isRebel");
        if (players == null || players.length == 0)
        {
            doBroadcast = false;
        }
        String update = params.getString("update");
        String systemMessage = "";
        if (params.containsKey("systemMessage"))
        {
            systemMessage = params.getString("systemMessage");
        }
        String[] failArray = new String[P2_IMPERIAL_FAIL_TASKS.length];
        failArray = utils.copyArray(P2_IMPERIAL_FAIL_TASKS, failArray);
        int phase = getPhase();
        String activeQuest = "";
        String nextQuest = "";
        switch (phase)
        {
            case 1:
            activeQuest = P1_IMPERIAL_GRANT;
            nextQuest = P2_IMPERIAL_GRANT;
            break;
            case 2:
            activeQuest = P2_IMPERIAL_GRANT;
            nextQuest = P3_IMPERIAL_GRANT;
            break;
            case 3:
            activeQuest = P3_IMPERIAL_GRANT;
            nextQuest = "";
            break;
        }
        if (isRebel == 1)
        {
            switch (phase)
            {
                case 1:
                activeQuest = P1_REBEL_GRANT;
                nextQuest = P2_REBEL_GRANT;
                break;
                case 2:
                activeQuest = P2_REBEL_GRANT;
                nextQuest = P3_REBEL_GRANT;
                failArray = utils.copyArray(P2_REBEL_FAIL_TASKS, failArray);
                break;
                case 3:
                activeQuest = P3_REBEL_GRANT;
                nextQuest = "";
                failArray = utils.copyArray(P3_REBEL_FAIL_TASKS, failArray);
                break;
            }
        }
        int failIndex = utils.getElementPositionInArray(failArray, update);
        if (failIndex > -1)
        {
            String failTask = failArray[failIndex];
            handleFailCase(self, players, activeQuest, failTask);
        }
        else 
        {
            int updateIndex = -1;
            for (int i = 0, j = COUNTED_UPDATES.length; i < j; i++)
            {
                if (update.equals(COUNTED_UPDATES[i]))
                {
                    updateIndex = i;
                    LOG("echo_quest_tracker", "questUpdate update index: " + i);
                }
            }
            if (updateIndex >= 0)
            {
                handleGenericTaskCompletion(self, players, doBroadcast, COUNTED_UPDATES[updateIndex], COUNTED_TASK_NAMES[updateIndex]);
            }
            else 
            {
                setObjVar(self, update, 1);
                if (doBroadcast)
                {
                    groundquests.sendSignal(players, update);
                    if (systemMessage != null && systemMessage.length() > 0)
                    {
                        if (!utils.hasScriptVar(self, systemMessage))
                        {
                            utils.sendSystemMessage(players, new string_id("theme_park/heroic", systemMessage));
                            utils.setScriptVar(self, systemMessage, 1);
                        }
                    }
                    if (isRebel == 1 && phase == 2)
                    {
                        dictionary webster = new dictionary();
                        webster.put("players", players);
                        webster.put("activeQuest", activeQuest);
                        messageTo(self, "checkForFailedTask", webster, 1, false);
                    }
                }
            }
            if (update.equals(P1_GENERATOR_DESTROYED))
            {
                setObjVar(self, PHASE, 2);
                if (doBroadcast)
                {
                    groundquests.clearQuest(players, activeQuest);
                    groundquests.requestGrantQuest(players, nextQuest);
                    messageTo(self, "phaseOneVoiceOvers", null, 4.0f, false);
                }
            }
            if (update.equals(PHASE3_STARTED))
            {
                setObjVar(self, PHASE, 3);
                if (doBroadcast)
                {
                    groundquests.clearQuest(players, activeQuest);
                    groundquests.requestGrantQuest(players, nextQuest);
                }
            }
            if (update.equals(P3_COMPLETE))
            {
                setObjVar(self, PHASE, -1);
                if (doBroadcast)
                {
                    LOG("echo_quest_tracker", "questUpdate PhaseThree sendSignal");
                    groundquests.clearQuest(players, activeQuest);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int checkForFailedTask(obj_id self, dictionary params) throws InterruptedException
    {
        String questName = params.getString("activeQuest");
        obj_id[] players = params.getObjIdArray("players");
        if (players == null || players.length <= 0 || questName.length() <= 0)
        {
            return SCRIPT_CONTINUE;
        }
        int phase = getPhase();
        int isRebel = getIntObjVar(self, "isRebel");
        if (isRebel == 1)
        {
            if (phase == 2)
            {
                for (int i = 0; i < P2_REBEL_FAIL_TASKS.length; i++)
                {
                    if (hasObjVar(self, P2_REBEL_FAIL_TASKS[i]))
                    {
                        failEchoTask(players, questName, P2_REBEL_FAIL_TASKS[i], true);
                    }
                }
            }
        }
        else 
        {
            if (phase == 2)
            {
                for (int i = 0; i < P2_IMPERIAL_FAIL_TASKS.length; i++)
                {
                    if (hasObjVar(self, P2_IMPERIAL_FAIL_TASKS[i]))
                    {
                        failEchoTask(players, questName, P2_IMPERIAL_FAIL_TASKS[i], true);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void handleFailCase(obj_id self, obj_id[] players, String questName, String failTask) throws InterruptedException
    {
        setObjVar(self, failTask, 1);
        failEchoTask(players, questName, failTask, true);
        return;
    }
    public void failEchoTask(obj_id[] players, String questName, String failTask, boolean showMessage) throws InterruptedException
    {
        if (players != null && players.length > 0)
        {
            for (int i = 0; i < players.length; i++)
            {
                failEchoTask(players[i], questName, failTask, showMessage);
            }
        }
        return;
    }
    public void failEchoTask(obj_id player, String questName, String failTask, boolean showMessage) throws InterruptedException
    {
        obj_id self = getSelf();
        if (isIdValid(player) && exists(player))
        {
            if (groundquests.isTaskActive(player, questName, failTask))
            {
                groundquests.failTask(player, questName, failTask);
                if (showMessage)
                {
                    sendSystemMessage(player, new string_id("theme_park/heroic", failTask));
                }
            }
            else 
            {
                if (getPhase() == 2 && getIntObjVar(self, "isRebel") == 1)
                {
                    String precedingTask = "";
                    if (failTask.equals("reb_phase2_command_center_task"))
                    {
                        precedingTask = "fake_command_center_destruction";
                    }
                    else if (failTask.equals("reb_phase2_command_personnel_task"))
                    {
                        precedingTask = "fake_command_center_personnel";
                    }
                    else if (failTask.equals("reb_phase2_thermal_personnel_task"))
                    {
                        precedingTask = "p2_rebel_secure_thermal_power";
                    }
                    else if (failTask.equals("reb_phase2_medical_personnel_task"))
                    {
                        precedingTask = "p2_rebel_secure_med_bay";
                    }
                    else if (failTask.equals("reb_phase2_equipment_personnel_task"))
                    {
                        precedingTask = "p2_rebel_secure_lower_hangar";
                    }
                    if (precedingTask != null && precedingTask.length() > 0)
                    {
                        if (groundquests.isTaskActive(player, questName, precedingTask))
                        {
                            groundquests.completeTask(player, questName, precedingTask);
                            setObjVar(self, failTask, 1);
                            failEchoTask(player, questName, failTask, showMessage);
                        }
                        if (hasObjVar(self, "reb_phase2_command_center_task") && hasObjVar(self, "reb_phase2_command_personnel_task"))
                        {
                            if (groundquests.isTaskActive(player, questName, "p2_rebel_secure_command_center"))
                            {
                                groundquests.completeTask(player, questName, "p2_rebel_secure_command_center");
                            }
                        }
                    }
                }
            }
        }
    }
    public int getPhase() throws InterruptedException
    {
        return getIntObjVar(getSelf(), PHASE);
    }
    public void updatePlayerQuests(obj_id player) throws InterruptedException
    {
        obj_id self = getSelf();
        int isRebel = getIntObjVar(self, "isRebel");
        int phase = getPhase();
        LOG("echo_quest_tracker", "requestUpdatePlayer phase: " + phase + " self: " + self + " isRebel: " + isRebel);
        questRemoval(player);
        String activeQuest = "";
        switch (phase)
        {
            case 1:
            activeQuest = isRebel == 1 ? P1_REBEL_GRANT : P1_IMPERIAL_GRANT;
            if (!groundquests.isQuestActive(player, activeQuest))
            {
                groundquests.requestGrantQuest(player, activeQuest);
            }
            int deadATATs = getIntObjVar(self, P1_DEAD_AT);
            if (deadATATs > 0)
            {
                dictionary dict = new dictionary();
                for (int i = 0; i < deadATATs; i++)
                {
                    dict.put("creatureName", "agrilat_plumed_rasp");
                    dict.put("location", getLocation(player));
                    dict.put("socialGroup", "atat");
                    messageTo(player, "receiveCreditForKill", dict, 1.0f, false);
                }
            }
            break;
            case 2:
            if (isRebel == 1)
            {
                activeQuest = P2_REBEL_GRANT;
            }
            else 
            {
                activeQuest = P2_IMPERIAL_GRANT;
            }
            if (!groundquests.isQuestActive(player, activeQuest))
            {
                groundquests.requestGrantQuest(player, activeQuest);
            }
            String[] phaseTwoSignals = isRebel == 1 ? P2_REBEL_SIGNALS : P2_IMPERIAL_SIGNALS;
            for (int i = 0; i < phaseTwoSignals.length; i++)
            {
                int count = getIntObjVar(self, phaseTwoSignals[i]);
                if (count == 1)
                {
                    groundquests.sendSignal(player, phaseTwoSignals[i]);
                }
                if (count >= 1)
                {
                    for (int j = 0, k = COUNTED_UPDATES.length; j < k; j++)
                    {
                        if (phaseTwoSignals[i].equals(COUNTED_UPDATES[j]))
                        {
                            dictionary dict = new dictionary();
                            for (int l = 0; l < count; l++)
                            {
                                dict.put("creatureName", COUNTED_TASK_NAMES[j]);
                                dict.put("location", getLocation(player));
                                dict.put("socialGroup", "atat");
                                messageTo(player, "receiveCreditForKill", dict, 1.0f, false);
                            }
                        }
                    }
                }
            }
            obj_id[] players = 
            {
                player
            };
            dictionary webster = new dictionary();
            webster.put("players", players);
            webster.put("activeQuest", activeQuest);
            messageTo(self, "checkForFailedTask", webster, 1, false);
            break;
            case 3:
            if (isRebel == 1)
            {
                activeQuest = P3_REBEL_GRANT;
            }
            else 
            {
                activeQuest = P3_IMPERIAL_GRANT;
            }
            if (!groundquests.isQuestActive(player, activeQuest))
            {
                groundquests.requestGrantQuest(player, activeQuest);
            }
            String[] phaseThreeSignals = isRebel == 1 ? P3_REBEL : P3_IMPERIAL;
            for (int i = 0; i < phaseThreeSignals.length; i++)
            {
                int count = getIntObjVar(self, phaseThreeSignals[i]);
                if (count == 1)
                {
                    groundquests.sendSignal(player, phaseThreeSignals[i]);
                }
                if (count >= 1)
                {
                    for (int j = 0, k = COUNTED_UPDATES.length; j < k; j++)
                    {
                        if (phaseThreeSignals[i].equals(COUNTED_UPDATES[j]))
                        {
                            dictionary dict = new dictionary();
                            for (int l = 0; l < count; l++)
                            {
                                dict.put("creatureName", COUNTED_TASK_NAMES[j]);
                                dict.put("location", getLocation(player));
                                dict.put("socialGroup", "atat");
                                messageTo(player, "receiveCreditForKill", dict, 1.0f, false);
                            }
                        }
                    }
                }
            }
            break;
            case -1:
            break;
        }
    }
    public int phaseOneVoiceOvers(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] players = utils.getPlayersInBuildoutRow(self);
        int isRebel = getIntObjVar(self, "isRebel");
        int deadAtats = getIntObjVar(self, P1_DEAD_AT);
        dictionary dict = trial.getSessionDict(trial.getParent(self));
        if (utils.hasScriptVar(self, "phaseOneAtatVoiceOver"))
        {
            return SCRIPT_CONTINUE;
        }
        if (isRebel == 1)
        {
            if (deadAtats >= 6)
            {
                dict.put("triggerName", "rebel_imperial_walkers_win_1");
            }
            else 
            {
                if (deadAtats >= 1 && deadAtats <= 5)
                {
                    dict.put("triggerName", "rebel_imperial_walkers_win_2");
                }
                else 
                {
                    dict.put("triggerName", "rebel_imperial_walkers_win_3");
                }
            }
        }
        else 
        {
            if (deadAtats <= 0)
            {
                dict.put("triggerName", "imperial_walkers_win_1");
            }
            else 
            {
                if (deadAtats == 1 || deadAtats == 2)
                {
                    dict.put("triggerName", "imperial_walkers_win_2");
                }
                else 
                {
                    dict.put("triggerName", "imperial_walkers_win_3");
                }
            }
        }
        dict.put("triggerType", "triggerId");
        utils.setScriptVar(self, "phaseOneAtatVoiceOver", 1);
        messageTo(trial.getParent(self), "triggerFired", dict, 0.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int requestUpdatePlayer(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        updatePlayerQuests(player);
        return SCRIPT_CONTINUE;
    }
    public void handleGenericTaskCompletion(obj_id self, obj_id[] players, boolean doBroadcast, String task, String creatureName) throws InterruptedException
    {
        int dead = getIntObjVar(self, task);
        dead++;
        LOG("echo_quest_tracker", "handleGenericTaskCompletion task: " + task + " creatureName:" + creatureName + " dead: " + dead);
        setObjVar(self, task, dead);
        dictionary dict = new dictionary();
        if (doBroadcast)
        {
            for (int i = 0; i < players.length; i++)
            {
                dict.put("creatureName", creatureName);
                dict.put("location", getLocation(players[i]));
                dict.put("socialGroup", "atat");
                messageTo(players[i], "receiveCreditForKill", dict, 0.0f, false);
            }
        }
    }
}
