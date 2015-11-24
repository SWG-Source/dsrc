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

public class fs_reflex2_player extends script.base_script
{
    public fs_reflex2_player()
    {
    }
    public static final string_id MSG_FIND_THEATER_WAYPOINT = new string_id("quest/force_sensitive/fs_reflex", "msg_phase_02_find_theater_waypoint");
    public static final string_id MSG_GOT_CRATE = new string_id("quest/force_sensitive/fs_reflex", "msg_phase_02_got_crate");
    public static final string_id MSG_QUEST_FINISHED = new string_id("quest/force_sensitive/fs_reflex", "msg_phase_02_quest_finished");
    public static final string_id MSG_QUEST_CONTINUE = new string_id("quest/force_sensitive/fs_reflex", "msg_phase_02_quest_continue");
    public static final string_id MSG_QUEST_FAILED = new string_id("quest/force_sensitive/fs_reflex", "msg_phase_02_quest_failed");
    public static final string_id MSG_QUEST_FAIL_PHASE_DONE = new string_id("quest/force_sensitive/fs_reflex", "msg_phase_02_quest_fail_phase_done");
    public static final string_id MSG_QUEST_FAIL_INCAP = new string_id("quest/force_sensitive/fs_reflex", "msg_phase_02_quest_fail_incap");
    public static final string_id MSG_QUEST_ERROR_ABORTED = new string_id("quest/force_sensitive/fs_reflex", "msg_phase_02_quest_error_aborted");
    public static final String QUEST_OBJVAR = "quest.fs_reflex2";
    public static final String RESCUED_OBJVAR = QUEST_OBJVAR + ".rescued";
    public static final String IN_PROGRESS_OBJVAR = QUEST_OBJVAR + ".in_progress";
    public static final String CONTINUE_OBJVAR = QUEST_OBJVAR + ".continue";
    public static final String FAILED_OBJVAR = QUEST_OBJVAR + ".failed";
    public static final String MASTER_OBJVAR = QUEST_OBJVAR + ".master";
    public static final String ABORTED_OBJVAR = QUEST_OBJVAR + ".aborted";
    public int msgQuestAbortPhaseChange(obj_id self, dictionary params) throws InterruptedException
    {
        sendSystemMessage(self, MSG_QUEST_FAIL_PHASE_DONE);
        removeObjVar(self, QUEST_OBJVAR);
        resetQuest(self);
        if (hasTheaterAssigned(self))
        {
            unassignTheaterFromPlayer(self);
        }
        detachScript(self, "quest.force_sensitive.fs_reflex2_player");
        return SCRIPT_CONTINUE;
    }
    public int OnQuestActivated(obj_id self, int questRow) throws InterruptedException
    {
        String questName = quests.getDataEntry(questRow, "NAME");
        if (questName == null)
        {
            return SCRIPT_CONTINUE;
        }
        if (questName.equals("fs_reflex_fetch_quest_01"))
        {
            setObjVar(self, IN_PROGRESS_OBJVAR, 1);
        }
        else if (questName.equals("fs_reflex_fetch_quest_02"))
        {
            obj_id crate = createObjectInInventoryAllowOverload("object/tangible/item/quest/force_sensitive/fs_reflex_supply_crate.iff", self);
            if (isIdValid(crate))
            {
                setObjVar(crate, "player", self);
                sendSystemMessage(self, MSG_GOT_CRATE);
                quests.complete("fs_reflex_fetch_quest_02", self, true);
            }
        }
        else if (questName.equals("fs_reflex_fetch_quest_03"))
        {
            location loc = new location(5284.0f, 0, -4222.0f, "dathomir");
            addLocationTarget(questName, loc, 15.0f);
            String wp_objvar = "quest." + questName + ".waypoint";
            obj_id wp = createWaypointInDatapad(self, loc);
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
            setObjVar(self, wp_objvar, wp);
        }
        else if (questName.equals("fs_reflex_fetch_quest_04"))
        {
            int rescued = 0;
            if (hasObjVar(self, RESCUED_OBJVAR))
            {
                rescued = getIntObjVar(self, RESCUED_OBJVAR);
            }
            rescued++;
            if (rescued >= 6)
            {
                finishQuest(self, questName);
            }
            else 
            {
                setObjVar(self, RESCUED_OBJVAR, rescued);
                continueQuest(self, questName, rescued);
            }
        }
        else if (questName.equals("fs_reflex_fetch_quest_05"))
        {
            sendSystemMessage(self, MSG_QUEST_FAILED);
            removeObjVar(self, IN_PROGRESS_OBJVAR);
            setObjVar(self, FAILED_OBJVAR, 1);
            resetQuest(self);
        }
        else if (questName.equals("fs_reflex_fetch_quest_06"))
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
        if (locationName.equals("fs_reflex_fetch_quest_01_waypoint"))
        {
            sendSystemMessage(self, MSG_FIND_THEATER_WAYPOINT);
        }
        else if (locationName.equals("fs_reflex_fetch_quest_03"))
        {
            quests.complete("fs_reflex_fetch_quest_03", self, utils.playerHasItemByTemplate(self, "object/tangible/item/quest/force_sensitive/fs_reflex_supply_crate.iff"));
        }
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        if (quests.isActive("fs_reflex_fetch_quest_03", self))
        {
            sendSystemMessage(self, MSG_QUEST_FAIL_INCAP);
            quests.complete("fs_reflex_fetch_quest_03", self, false);
            if (utils.playerHasItemByTemplate(self, "object/tangible/item/quest/force_sensitive/fs_reflex_supply_crate.iff"))
            {
                obj_id crate = utils.getItemPlayerHasByTemplate(self, "object/tangible/item/quest/force_sensitive/fs_reflex_supply_crate.iff");
                if (isIdValid(crate))
                {
                    destroyObject(crate);
                }
            }
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
        int left = (6 - rescued);
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
        fs_quests.unlockBranch(self, "force_sensitive_enhanced_reflexes_vehicle_control");
        fs_quests.setQuestCompleted(self, "fs_reflex2");
        detachScript(self, "quest.force_sensitive.fs_reflex2_player");
        obj_id createdObject = null;
        createdObject = createObjectInInventoryAllowOverload("object/tangible/item/quest/force_sensitive/fs_buff_item.iff", self);
        if (createdObject != null)
        {
            setObjVar(createdObject, "item.time.reuse_time", 259200);
            setObjVar(createdObject, "item.buff.type", 0);
            setObjVar(createdObject, "item.buff.value", 2000);
            setObjVar(createdObject, "item.buff.duration", 7200);
        }
        obj_id theater = getLastSpawnedTheater(self);
        if (isIdValid(theater))
        {
            destroyObject(theater);
        }
    }
    public void resetQuest(obj_id self) throws InterruptedException
    {
        for (int i = 6; i >= 0; i--)
        {
            String name = "fs_reflex_fetch_quest_0" + i;
            int questId = quests.getQuestId(name);
            quests.complete(name, self, false);
            clearCompletedQuest(self, questId);
            if (hasObjVar(self, "quest." + name + ".waypoint"))
            {
                obj_id waypoint = getObjIdObjVar(self, "quest." + name + ".waypoint");
                if (isIdValid(waypoint))
                {
                    destroyWaypointInDatapad(waypoint, self);
                }
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
        if (datatable.equals("datatables/theater/quest/fs_quest/fs_reflex2.iff"))
        {
            int questId = quests.getQuestId("fs_reflex_fetch_quest_01");
            clearCompletedQuest(self, questId);
            if (quests.isActive("fs_reflex_fetch_quest_01", self))
            {
                quests.deactivate("fs_reflex_fetch_quest_01", self);
            }
            if (hasObjVar(self, "quest.fs_reflex_fetch_quest_01.waypoint"))
            {
                obj_id waypoint = getObjIdObjVar(self, "quest.fs_reflex_fetch_quest_01.waypoint");
                if (isIdValid(waypoint))
                {
                    destroyWaypointInDatapad(waypoint, self);
                }
            }
            messageTo(self, "handleReactivateFailedTask", null, 1.0f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleReactivateFailedTask(obj_id self, dictionary params) throws InterruptedException
    {
        quests.activate("fs_reflex_fetch_quest_01", self, null);
        return SCRIPT_CONTINUE;
    }
}
