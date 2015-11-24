package script.quest.force_sensitive;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.quests;
import script.library.fs_quests;
import script.library.utils;
import script.library.prose;
import script.library.chat;
import script.library.jedi_trials;
import script.library.group;
import script.library.xp;

public class fs_kickoff extends script.base_script
{
    public fs_kickoff()
    {
    }
    public static final String RANGED_SPEED_BRANCH = "force_sensitive_combat_prowess_ranged_speed";
    public static final String MELEE_DEFENSE_BRANCH = "force_sensitive_enhanced_reflexes_melee_defense";
    public static final String RANGED_SPEED_QUEST = "fs_defend_wait_01";
    public static final String MELEE_DEFENSE_QUEST = "fs_defend_wait_02";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        detachScript(self, "quest.force_sensitive.fs_kickoff");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        
        {
            detachScript(self, "quest.force_sensitive.fs_kickoff");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnPlayerTheaterFail(obj_id self, String datatable, String name) throws InterruptedException
    {
        int quest_id = 0;
        if (hasObjVar(self, "theaterRecoveryTarget"))
        {
            removeObjVar(self, "theaterRecoveryTarget");
        }
        if (datatable.equals("datatables/theater/fs_quest_intro/fs_village_quest_intro.iff"))
        {
            obj_id waypoint = getObjIdObjVar(self, "quest.fs_theater_camp.waypoint");
            if (isIdValid(waypoint))
            {
                destroyWaypointInDatapad(waypoint, self);
            }
            if (hasObjVar(self, "quest.fs_theater_camp.selected_location"))
            {
                removeObjVar(self, "quest.fs_theater_camp.selected_location");
            }
            obj_id theater = getLastSpawnedTheater(self);
            if (isIdValid(theater))
            {
                destroyObject(theater);
            }
            quest_id = quests.getQuestId("fs_theater_camp");
            quests.deactivate("fs_theater_camp", self);
            clearCompletedQuest(self, quest_id);
            unassignTheaterFromPlayer(self);
            messageTo(self, "recreateTheaterOne", null, 4, false);
            sendSystemMessage(self, new string_id("quest/force_sensitive/intro", "theater_retry"));
        }
        if (datatable.equals("datatables/theater/quest/fs_quest/final_battle.iff"))
        {
            obj_id waypointFinal = getObjIdObjVar(self, "quest.fs_theater_final.waypoint");
            if (isIdValid(waypointFinal))
            {
                destroyWaypointInDatapad(waypointFinal, self);
            }
            if (hasObjVar(self, "quest.fs_theater_final.selected_location"))
            {
                removeObjVar(self, "quest.fs_theater_final.selected_location");
            }
            obj_id theater = getLastSpawnedTheater(self);
            if (isIdValid(theater))
            {
                destroyObject(theater);
            }
            quest_id = quests.getQuestId("fs_theater_final");
            quests.deactivate("fs_theater_final", self);
            unassignTheaterFromPlayer(self);
            clearCompletedQuest(self, quest_id);
            messageTo(self, "recreateTheaterTwo", null, 4, false);
            sendSystemMessage(self, new string_id("quest/force_sensitive/intro", "theater_retry"));
        }
        return SCRIPT_CONTINUE;
    }
    public int OnRemovingFromWorld(obj_id self) throws InterruptedException
    {
        obj_id waypoint = getObjIdObjVar(self, "quest.fs_theater_camp.waypoint");
        if (isIdValid(waypoint))
        {
            destroyWaypointInDatapad(waypoint, self);
        }
        waypoint = getObjIdObjVar(self, "quest.fs_theater_final.waypoint");
        if (isIdValid(waypoint))
        {
            destroyWaypointInDatapad(waypoint, self);
        }
        obj_id theater = getLastSpawnedTheater(self);
        if (isIdValid(theater))
        {
            destroyObject(theater);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnImmediateLogout(obj_id self) throws InterruptedException
    {
        obj_id waypoint = getObjIdObjVar(self, "quest.fs_theater_camp.waypoint");
        if (isIdValid(waypoint))
        {
            destroyWaypointInDatapad(waypoint, self);
        }
        waypoint = getObjIdObjVar(self, "quest.fs_theater_final.waypoint");
        if (isIdValid(waypoint))
        {
            destroyWaypointInDatapad(waypoint, self);
        }
        obj_id theater = getLastSpawnedTheater(self);
        if (isIdValid(theater))
        {
            destroyObject(theater);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnLogin(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "fs_kickoff_stage"))
        {
            removeObjVar(self, "fs_kickoff_stage");
        }
        detachScript(self, "quest.force_sensitive_fs_kickoff");
        return SCRIPT_CONTINUE;
    }
    public int OnSkillAboutToBeRevoked(obj_id self, String strSkill) throws InterruptedException
    {
        if (strSkill.startsWith("force_title") && !hasObjVar(self, "clickRespec.granting"))
        {
            string_id nonrevokable = new string_id("jedi_spam", "revoke_force_title");
            sendSystemMessage(self, nonrevokable);
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int meetOldMan(obj_id self, dictionary params) throws InterruptedException
    {
        String config = getConfigSetting("GameServer", "forceSensitiveIntroActive");
        if (config != null)
        {
            if (config.equals("false"))
            {
                return SCRIPT_CONTINUE;
            }
        }
        String planet = getCurrentSceneName();
        if (planet.equals("mustafar"))
        {
            return SCRIPT_CONTINUE;
        }
        int stage = getIntObjVar(self, "fs_kickoff_stage");
        if (stage != 2)
        {
            return SCRIPT_CONTINUE;
        }
        if (fs_quests.canBeginEncounter(self))
        {
            quests.activate("old_man_initial", self, null);
        }
        else 
        {
            startMeetOldMan(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int meetOldManExit(obj_id self, dictionary params) throws InterruptedException
    {
        String planet = getCurrentSceneName();
        if (planet.equals("mustafar"))
        {
            return SCRIPT_CONTINUE;
        }
        int stage = getIntObjVar(self, "fs_kickoff_stage");
        if (stage != 9)
        {
            return SCRIPT_CONTINUE;
        }
        if (fs_quests.canBeginEncounter(self))
        {
            int quest_id = quests.getQuestId("old_man_final");
            clearCompletedQuest(self, quest_id);
            quests.activate("old_man_final", self, null);
        }
        else 
        {
            startMeetOldManExit(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int twoMilitaryEncounter(obj_id self, dictionary params) throws InterruptedException
    {
        String planet = getCurrentSceneName();
        if (planet.equals("mustafar"))
        {
            return SCRIPT_CONTINUE;
        }
        if (fs_quests.canBeginEncounter(self))
        {
            quests.activate("two_military", self, null);
        }
        else 
        {
            startTwoMilitary(self);
        }
        return SCRIPT_CONTINUE;
    }
    public void startMeetOldMan(obj_id player) throws InterruptedException
    {
        String planet = getCurrentSceneName();
        if (planet.equals("mustafar"))
        {
            return;
        }
        int randomDelay = rand(30, 300);
        messageTo(player, "meetOldMan", null, randomDelay, false);
        return;
    }
    public void startTwoMilitary(obj_id player) throws InterruptedException
    {
        String planet = getCurrentSceneName();
        if (planet.equals("mustafar"))
        {
            return;
        }
        int randomDelay = rand(30, 300);
        messageTo(player, "twoMilitaryEncounter", null, randomDelay, false);
        return;
    }
    public void startMeetOldManExit(obj_id player) throws InterruptedException
    {
        String planet = getCurrentSceneName();
        if (planet.equals("mustafar"))
        {
            return;
        }
        int randomDelay = rand(30, 300);
        messageTo(player, "meetOldManExit", null, randomDelay, false);
        return;
    }
    public int tryAgain(obj_id self, dictionary params) throws InterruptedException
    {
        startMeetOldMan(self);
        return SCRIPT_CONTINUE;
    }
    public int finalCleanup(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id theater = getLastSpawnedTheater(self);
        if (isIdValid(theater))
        {
            destroyObject(theater);
        }
        return SCRIPT_CONTINUE;
    }
    public int msgForceComplete(obj_id self, dictionary params) throws InterruptedException
    {
        fs_quests.advanceStage(self);
        jedi_trials.initializePadawanTrials(self);
        sendSystemMessage(self, new string_id("quest/force_sensitive/exit", "final_complete"));
        CustomerServiceLog("fs_quests", "%TU has completed the exit encounter", self, null);
        if (hasObjVar(self, "theaterRecoveryTarget"))
        {
            removeObjVar(self, "theaterRecoveryTarget");
        }
        obj_id waypointFinal = getObjIdObjVar(self, "quest.fs_theater_final.waypoint");
        if (isIdValid(waypointFinal))
        {
            destroyWaypointInDatapad(waypointFinal, self);
        }
        messageTo(self, "finalCleanup", null, 60, false);
        return SCRIPT_CONTINUE;
    }
    public int msgForceFail(obj_id self, dictionary params) throws InterruptedException
    {
        fs_quests.setDelay(self, 13);
        fs_quests.setStage(self, 10);
        int quest_id = quests.getQuestId("old_man_final");
        clearCompletedQuest(self, quest_id);
        quest_id = quests.getQuestId("fs_theater_final");
        clearCompletedQuest(self, quest_id);
        sendSystemMessage(self, new string_id("quest/force_sensitive/exit", "final_fail"));
        if (hasObjVar(self, "theaterRecoveryTarget"))
        {
            removeObjVar(self, "theaterRecoveryTarget");
        }
        obj_id waypointFinal = getObjIdObjVar(self, "quest.fs_theater_final.waypoint");
        if (isIdValid(waypointFinal))
        {
            destroyWaypointInDatapad(waypointFinal, self);
        }
        obj_id theater = getLastSpawnedTheater(self);
        if (isIdValid(theater))
        {
            destroyObject(theater);
        }
        return SCRIPT_CONTINUE;
    }
    public int recreateTheaterOne(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id theater = getLastSpawnedTheater(self);
        if (isIdValid(theater))
        {
            destroyObject(theater);
        }
        obj_id waypoint = getObjIdObjVar(self, "quest.fs_theater_camp.waypoint");
        if (isIdValid(waypoint))
        {
            destroyWaypointInDatapad(waypoint, self);
        }
        quests.activate("fs_theater_camp", self, null);
        return SCRIPT_CONTINUE;
    }
    public int recreateTheaterTwo(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id theater = getLastSpawnedTheater(self);
        if (isIdValid(theater))
        {
            destroyObject(theater);
        }
        obj_id waypointFinal = getObjIdObjVar(self, "quest.fs_theater_final.waypoint");
        if (isIdValid(waypointFinal))
        {
            destroyWaypointInDatapad(waypointFinal, self);
        }
        if (hasObjVar(self, "theaterRecoveryTarget"))
        {
            location where = getLocationObjVar(self, "theaterRecoveryTarget");
            if ((toLower(where.area)).startsWith("kashyyyk"))
            {
                removeObjVar(self, "theaterRecoveryTarget");
                fs_quests.setStage(self, 9);
                if (!fs_quests.isEndQuestEligible(self))
                {
                    fs_quests.setStage(self, 8);
                    return SCRIPT_CONTINUE;
                }
                if (fs_quests.checkDelay(self))
                {
                    int randomDelay = rand(300, 900);
                    messageTo(self, "meetOldManExit", null, randomDelay, false);
                }
            }
        }
        quests.activate("fs_theater_final", self, null);
        return SCRIPT_CONTINUE;
    }
    public int addQuestLootToCorpse(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id corpse = params.getObjId("corpse");
        String questName = params.getString("questName");
        if (questName.equals("two_military"))
        {
            obj_id questOwner = utils.getObjIdObjVar(corpse, "quest.owner", obj_id.NULL_ID);
            if (isIdValid(questOwner) && questOwner == self && quests.isActive("loot_datapad_1", self) && !utils.playerHasItemByTemplateInBankOrInventory(self, "object/tangible/loot/quest/force_sensitive/waypoint_datapad.iff"))
            {
                obj_id winner = getObjIdObjVar(corpse, xp.VAR_TOP_GROUP);
                if (isIdValid(winner))
                {
                    obj_id[] permitted = null;
                    if (group.isGroupObject(winner))
                    {
                        permitted = getGroupMemberIds(winner);
                    }
                    else 
                    {
                        permitted = new obj_id[1];
                        permitted[0] = winner;
                    }
                    if (permitted != null)
                    {
                        for (int i = 0; i < permitted.length; i++)
                        {
                            if (permitted[i] == self)
                            {
                                obj_id inv = utils.getInventoryContainer(corpse);
                                if (isIdValid(inv))
                                {
                                    obj_id loot = createObject("object/tangible/loot/quest/force_sensitive/waypoint_datapad.iff", inv, "");
                                    setObjVar(loot, "quest.loot_datapad_1.quest_item_target", self);
                                    setOwner(loot, self);
                                }
                                break;
                            }
                        }
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        if ((hasScript(killer, "quest.force_sensitive.fs_military_initial")) || (hasScript(killer, "quest.force_sensitive.fs_military_initial_silent")))
        {
            if (hasObjVar(killer, "quest.owner"))
            {
                obj_id questOwner = getObjIdObjVar(killer, "quest.owner");
                if (questOwner == self)
                {
                    if (utils.playerHasItemByTemplate(self, "object/tangible/loot/quest/force_sensitive/force_crystal.iff"))
                    {
                        obj_id crystal = utils.getItemPlayerHasByTemplate(self, "object/tangible/loot/quest/force_sensitive/force_crystal.iff");
                        int quest_id = quests.getQuestId("old_man_force_crystal");
                        clearCompletedQuest(self, quest_id);
                        destroyObject(crystal);
                        prose_package pp = prose.getPackage(new string_id("quest/force_sensitive/intro", "military_take_crystal"), self);
                        chat.publicChat(killer, self, null, null, pp);
                    }
                    setObjVar(self, "fs_kickoff_stage", 2);
                    startMeetOldMan(self);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnQuestActivated(obj_id self, int questRow) throws InterruptedException
    {
        if (isJedi(self))
        {
            return SCRIPT_CONTINUE;
        }
        int quest_id = quests.getQuestId("fs_village_elder");
        if (quest_id == questRow)
        {
            fs_quests.makeVillageEligible(self);
            setJediState(self, JEDI_STATE_FORCE_SENSITIVE);
            grantSkill(self, "force_title_jedi_novice");
            sendSystemMessage(self, new string_id("quest/force_sensitive/intro", "force_sensitive"));
            CustomerServiceLog("fs_quests", "%TU is now force sensitive", self, null);
            return SCRIPT_CONTINUE;
        }
        quest_id = quests.getQuestId("got_datapad");
        if (quest_id == questRow)
        {
            if (hasObjVar(self, "fs_kickoff_stage"))
            {
                int stage = getIntObjVar(self, "fs_kickoff_stage");
                if (stage == 4)
                {
                    fs_quests.advanceStage(self);
                }
            }
        }
        quest_id = quests.getQuestId("got_datapad_2");
        if (quest_id == questRow)
        {
            if (hasObjVar(self, "fs_kickoff_stage"))
            {
                int stage = getIntObjVar(self, "fs_kickoff_stage");
                if (stage == 6)
                {
                    fs_quests.advanceStage(self);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void completeStuckWaitForTasks(obj_id self) throws InterruptedException
    {
        if (!quests.safeHasObjVar(self, "quest.wait_for_tasks"))
        {
            if (quests.isActive(RANGED_SPEED_QUEST, self))
            {
                if (quests.isComplete("fs_defend_01", self) && quests.isComplete("fs_defend_02", self))
                {
                    CustomerServiceLog("fs_quests", "completing stuck " + RANGED_SPEED_QUEST + " task for %TU", self, null);
                    quests.complete(RANGED_SPEED_QUEST, self, true);
                }
            }
            if (quests.isActive(MELEE_DEFENSE_QUEST, self))
            {
                if (quests.isComplete("fs_defend_03", self) && quests.isComplete("fs_defend_04", self))
                {
                    CustomerServiceLog("fs_quests", "completing stuck " + MELEE_DEFENSE_QUEST + " task for %TU", self, null);
                    quests.complete(MELEE_DEFENSE_QUEST, self, true);
                }
            }
        }
    }
    public void grantStuckBranchUnlocks(obj_id self) throws InterruptedException
    {
        if (quests.isComplete("fs_defend_01", self) && quests.isComplete("fs_defend_02", self))
        {
            if (quests.isComplete(RANGED_SPEED_QUEST, self))
            {
                if (!fs_quests.hasUnlockedBranch(self, RANGED_SPEED_BRANCH))
                {
                    CustomerServiceLog("fs_quests", "re-completing " + RANGED_SPEED_QUEST + " to grant stuck " + RANGED_SPEED_BRANCH + " branch unlock for %TU", self, null);
                    quests.complete(RANGED_SPEED_QUEST, self, true);
                }
            }
        }
        if (quests.isComplete("fs_defend_03", self) && quests.isComplete("fs_defend_04", self))
        {
            if (quests.isComplete(MELEE_DEFENSE_QUEST, self))
            {
                if (!fs_quests.hasUnlockedBranch(self, MELEE_DEFENSE_BRANCH))
                {
                    CustomerServiceLog("fs_quests", "re-completing " + MELEE_DEFENSE_QUEST + " to grant stuck " + MELEE_DEFENSE_BRANCH + " branch unlock for %TU", self, null);
                    quests.complete(MELEE_DEFENSE_QUEST, self, true);
                }
            }
        }
    }
    public void resetStaleWaitForTasks(obj_id self) throws InterruptedException
    {
        if (!fs_quests.hasQuestAccepted(self))
        {
            if (quests.isActive(RANGED_SPEED_QUEST, self))
            {
                CustomerServiceLog("fs_quests", "resetting stale active " + RANGED_SPEED_QUEST + " task for %TU", self, null);
                quests.deactivate(RANGED_SPEED_QUEST, self);
            }
            if (quests.isActive(MELEE_DEFENSE_QUEST, self))
            {
                CustomerServiceLog("fs_quests", "resetting stale active " + MELEE_DEFENSE_QUEST + " task for %TU", self, null);
                quests.deactivate(MELEE_DEFENSE_QUEST, self);
            }
        }
    }
}
