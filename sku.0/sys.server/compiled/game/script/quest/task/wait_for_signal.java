package script.quest.task;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.fs_quests_sad;
import script.library.locations;
import script.library.quests;
import script.library.utils;

public class wait_for_signal extends script.base_script
{
    public wait_for_signal()
    {
    }
    public int OnFinishQuestSignal(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("newquests", "wait_for_signal - OnFinishQuestSignal()");
        String questName = params.getString("questName");
        if (questName != null)
        {
            if (utils.hasObjVar(self, fs_quests_sad.SAD_OBJVAR_WAYPOINT))
            {
                obj_id wp = utils.getObjIdObjVar(self, fs_quests_sad.SAD_OBJVAR_WAYPOINT);
                destroyWaypointInDatapad(wp, self);
                utils.removeObjVar(self, fs_quests_sad.SAD_OBJVAR_WAYPOINT);
            }
            if (quests.isActive(questName, self))
            {
                quests.complete(questName, self, true);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnQuestActivated(obj_id self, int questRow) throws InterruptedException
    {
        if (quests.isMyQuest(questRow, "quest.task.wait_for_signal"))
        {
            String questName = quests.getDataEntry(questRow, "NAME");
            if (!utils.hasObjVar(self, fs_quests_sad.SAD_OBJVAR_WAYPOINT))
            {
                location loc = new location(5238.26f, 78.5f, -4189.03f);
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
                setWaypointActive(wp, true);
                setWaypointColor(wp, "yellow");
                setObjVar(self, fs_quests_sad.SAD_OBJVAR_WAYPOINT, wp);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
