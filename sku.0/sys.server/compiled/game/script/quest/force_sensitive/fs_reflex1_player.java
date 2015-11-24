package script.quest.force_sensitive;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.fs_dyn_village;
import script.library.fs_quests;
import script.library.prose;
import script.library.quests;
import script.library.utils;

public class fs_reflex1_player extends script.base_script
{
    public fs_reflex1_player()
    {
    }
    public static final string_id MSG_FIND_THEATER_WAYPOINT = new string_id("quest/force_sensitive/fs_reflex", "msg_phase_01_find_theater_waypoint");
    public static final string_id MSG_QUEST_FINISHED = new string_id("quest/force_sensitive/fs_reflex", "msg_phase_01_quest_finished");
    public static final string_id MSG_QUEST_CONTINUE = new string_id("quest/force_sensitive/fs_reflex", "msg_phase_01_quest_continue");
    public static final string_id MSG_QUEST_FAILED = new string_id("quest/force_sensitive/fs_reflex", "msg_phase_01_quest_failed");
    public static final string_id MSG_QUEST_FAIL_PHASE_DONE = new string_id("quest/force_sensitive/fs_reflex", "msg_phase_01_quest_fail_phase_done");
    public static final string_id MSG_QUEST_FAIL_INCAP = new string_id("quest/force_sensitive/fs_reflex", "msg_phase_01_quest_fail_incap");
    public static final string_id MSG_QUEST_FAIL_LOGOUT = new string_id("quest/force_sensitive/fs_reflex", "msg_phase_01_quest_fail_logout");
    public static final string_id MSG_QUEST_ERROR_ABORTED = new string_id("quest/force_sensitive/fs_reflex", "msg_phase_01_quest_error_aborted");
    public static final string_id MSG_QUEST_ERROR_THEATER_FAIL = new string_id("quest/force_sensitive/fs_reflex", "msg_phase_01_quest_error_theater_fail");
    public static final String QUEST_OBJVAR = "quest.fs_reflex1";
    public static final String RESCUED_OBJVAR = QUEST_OBJVAR + ".rescued";
    public static final String IN_PROGRESS_OBJVAR = QUEST_OBJVAR + ".in_progress";
    public static final String CONTINUE_OBJVAR = QUEST_OBJVAR + ".continue";
    public static final String FAILED_OBJVAR = QUEST_OBJVAR + ".failed";
    public static final String MASTER_OBJVAR = QUEST_OBJVAR + ".master";
    public static final String ABORTED_OBJVAR = QUEST_OBJVAR + ".aborted";
    public int msgQuestAbortPhaseChange(obj_id self, dictionary params) throws InterruptedException
    {
        sendSystemMessage(self, MSG_QUEST_FAIL_PHASE_DONE);
        resetQuest(self);
        removeObjVar(self, QUEST_OBJVAR);
        detachScript(self, "quest.force_sensitive.fs_reflex1_player");
        if (hasTheaterAssigned(self))
        {
            unassignTheaterFromPlayer(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, IN_PROGRESS_OBJVAR))
        {
            if (hasObjVar(self, "quest.fs_reflex_rescue_quest_03.escort_target"))
            {
                obj_id escort = getObjIdObjVar(self, "quest.fs_reflex_rescue_quest_03.escort_target");
                destroyObject(escort);
            }
            removeObjVar(self, IN_PROGRESS_OBJVAR);
            setObjVar(self, FAILED_OBJVAR, 1);
            obj_id theater = getLastSpawnedTheater(self);
            if (isIdValid(theater))
            {
                destroyObject(theater);
            }
            unassignTheaterFromPlayer(self);
            sendSystemMessage(self, MSG_QUEST_FAIL_LOGOUT);
            resetQuest(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        if (hasObjVar(self, IN_PROGRESS_OBJVAR))
        {
            if (hasObjVar(self, "quest.fs_reflex_rescue_quest_03.escort_target"))
            {
                obj_id escort = getObjIdObjVar(self, "quest.fs_reflex_rescue_quest_03.escort_target");
                destroyObject(escort);
            }
            removeObjVar(self, IN_PROGRESS_OBJVAR);
            setObjVar(self, FAILED_OBJVAR, 1);
            obj_id theater = getLastSpawnedTheater(self);
            if (isIdValid(theater))
            {
                destroyObject(theater);
            }
            unassignTheaterFromPlayer(self);
            sendSystemMessage(self, MSG_QUEST_FAIL_INCAP);
            resetQuest(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnQuestActivated(obj_id self, int questRow) throws InterruptedException
    {
        String questName = quests.getDataEntry(questRow, "NAME");
        if (questName == null)
        {
            return SCRIPT_CONTINUE;
        }
        if (questName.equals("fs_reflex_rescue_quest_01"))
        {
            setObjVar(self, IN_PROGRESS_OBJVAR, 1);
        }
        else if (questName.equals("fs_reflex_rescue_quest_03"))
        {
            if (hasObjVar(self, "quest.fs_reflex_rescue_quest_02.waypoint"))
            {
                obj_id waypoint = getObjIdObjVar(self, "quest.fs_reflex_rescue_quest_02.waypoint");
                if (isIdValid(waypoint))
                {
                    destroyWaypointInDatapad(waypoint, self);
                }
            }
        }
        else if (questName.equals("fs_reflex_rescue_quest_05"))
        {
            int rescued = 0;
            if (hasObjVar(self, RESCUED_OBJVAR))
            {
                rescued = getIntObjVar(self, RESCUED_OBJVAR);
            }
            rescued++;
            if (rescued >= 5)
            {
                finishQuest(self, questName);
            }
            else 
            {
                setObjVar(self, RESCUED_OBJVAR, rescued);
                continueQuest(self, questName, rescued);
            }
        }
        else if (questName.equals("fs_reflex_rescue_quest_06"))
        {
            sendSystemMessage(self, MSG_QUEST_FAILED);
            removeObjVar(self, IN_PROGRESS_OBJVAR);
            setObjVar(self, FAILED_OBJVAR, 1);
            resetQuest(self);
        }
        else if (questName.equals("fs_reflex_rescue_quest_07"))
        {
            sendSystemMessage(self, MSG_QUEST_ERROR_ABORTED);
            removeObjVar(self, IN_PROGRESS_OBJVAR);
            setObjVar(self, ABORTED_OBJVAR, 1);
            resetQuest(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnArrivedAtLocation(obj_id self, String locationName) throws InterruptedException
    {
        if (locationName.equals("fs_reflex_rescue_quest_01_waypoint"))
        {
            sendSystemMessage(self, MSG_FIND_THEATER_WAYPOINT);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleMasterIdResponse(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id master = params.getObjId(fs_dyn_village.CLUSTER_OBJID_KEY_MASTER);
        if (isIdValid(master))
        {
            setObjVar(self, MASTER_OBJVAR, master);
        }
        return SCRIPT_CONTINUE;
    }
    public void continueQuest(obj_id self, String questName, int rescued) throws InterruptedException
    {
        int left = (5 - rescued);
        prose_package pp = prose.getPackage(MSG_QUEST_CONTINUE, left);
        sendSystemMessageProse(self, pp);
        removeObjVar(self, IN_PROGRESS_OBJVAR);
        setObjVar(self, CONTINUE_OBJVAR, 1);
        resetQuest(self);
    }
    public void finishQuest(obj_id self, String questName) throws InterruptedException
    {
        sendSystemMessage(self, MSG_QUEST_FINISHED);
        removeObjVar(self, QUEST_OBJVAR);
        quests.complete(questName, self, true);
        fs_quests.unlockBranch(self, "force_sensitive_enhanced_reflexes_survival");
        fs_quests.setQuestCompleted(self, "fs_reflex1");
        detachScript(self, "quest.force_sensitive.fs_reflex1_player");
        obj_id createdObject = null;
        createdObject = createObjectInInventoryAllowOverload("object/tangible/item/quest/force_sensitive/fs_sculpture_3.iff", self);
        obj_id theater = getLastSpawnedTheater(self);
        if (isIdValid(theater))
        {
            destroyObject(theater);
        }
    }
    public void resetQuest(obj_id self) throws InterruptedException
    {
        for (int i = 0; i <= 7; i++)
        {
            String name = "fs_reflex_rescue_quest_0" + i;
            int questId = quests.getQuestId(name);
            if (quests.isComplete(name, self))
            {
                clearCompletedQuest(self, questId);
            }
            else if (quests.isActive(name, self))
            {
                quests.deactivate(name, self);
            }
            if (hasObjVar(self, "quest." + name + ".waypoint"))
            {
                obj_id waypoint = getObjIdObjVar(self, "quest." + name + ".waypoint");
                if (isIdValid(waypoint))
                {
                    destroyWaypointInDatapad(waypoint, self);
                }
            }
            if (hasObjVar(self, "quest." + name))
            {
                removeObjVar(self, "quest." + name);
            }
            if (hasObjVar(self, name))
            {
                removeObjVar(self, name);
            }
        }
        obj_id theater = getLastSpawnedTheater(self);
        if (isIdValid(theater))
        {
            destroyObject(theater);
        }
    }
    public int OnPlayerTheaterFail(obj_id self, String datatable, String name) throws InterruptedException
    {
        if (datatable.equals("datatables/theater/quest/fs_quest/fs_reflex1.iff"))
        {
            LOG("newquests", "REFLEX1: theater creation failed - attempting to recover");
            int questId = quests.getQuestId("fs_reflex_rescue_quest_01");
            clearCompletedQuest(self, questId);
            if (quests.isActive("fs_reflex_rescue_quest_01", self))
            {
                quests.deactivate("fs_reflex_rescue_quest_01", self);
            }
            if (hasObjVar(self, "quest.fs_reflex_rescue_quest_01.waypoint"))
            {
                LOG("newquests", "REFLEX1: theater creation failed - deleting waypoint");
                obj_id waypoint = getObjIdObjVar(self, "quest.fs_reflex_rescue_quest_01.waypoint");
                if (isIdValid(waypoint))
                {
                    destroyWaypointInDatapad(waypoint, self);
                }
            }
            sendSystemMessage(self, MSG_QUEST_ERROR_THEATER_FAIL);
            LOG("newquests", "REFLEX1: theater creation failed - reactivating quest");
            messageTo(self, "handleReactivateFailedTask", null, 1.0f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleReactivateFailedTask(obj_id self, dictionary params) throws InterruptedException
    {
        quests.activate("fs_reflex_rescue_quest_01", self, null);
        return SCRIPT_CONTINUE;
    }
}
