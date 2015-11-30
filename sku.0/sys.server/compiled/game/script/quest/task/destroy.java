package script.quest.task;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.quests;
import script.library.utils;

public class destroy extends script.base_script
{
    public destroy()
    {
    }
    public int OnIncapacitateTarget(obj_id self, obj_id target) throws InterruptedException
    {
        LOG("newquests", "destroy - OnIncapacitateTarget()");
        int questId = quests.getQuestIdForTarget(self, target);
        if (questId > -1)
        {
            LOG("newquests", "destroy - Destroyed target is for a player's current active task");
            String questName = quests.getDataEntry(questId, "NAME");
            quests.complete(questName, self, true);
        }
        return SCRIPT_CONTINUE;
    }
}
