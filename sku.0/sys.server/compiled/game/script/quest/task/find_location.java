package script.quest.task;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.locations;
import script.library.quests;
import script.library.utils;

public class find_location extends script.base_script
{
    public find_location()
    {
    }
    public int OnLogin(obj_id self) throws InterruptedException
    {
        int rows = dataTableGetNumRows("datatables/player/quests.iff");
        int iter = 0;
        for (iter = 0; iter < rows; ++iter)
        {
            if (isQuestActive(self, iter))
            {
                if (quests.isMyQuest(iter, "quest.task.find_location"))
                {
                    String questName = quests.getDataEntry(iter, "NAME");
                    if (hasObjVar(self, "quest." + questName + ".generate"))
                    {
                        removeObjVar(self, "quest." + questName + ".generate");
                        location loc = quests.getLocationTarget(self, iter);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnQuestActivated(obj_id self, int questRow) throws InterruptedException
    {
        LOG("newquests", "find_location - OnQuestActivated(" + questRow + ")");
        if (quests.isMyQuest(questRow, "quest.task.find_location"))
        {
            LOG("newquests", "OnQuestActivated for find_location");
            quests.getLocationTarget(self, questRow);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnArrivedAtLocation(obj_id self, String locationName) throws InterruptedException
    {
        int questId = quests.getQuestId(locationName);
        if (questId > -1)
        {
            if (quests.isMyQuest(questId, "quest.task.find_location"))
            {
                if (quests.isActive(locationName, self))
                {
                    LOG("newquests", "find_location arrived at " + locationName);
                    quests.complete(locationName, self, true);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
