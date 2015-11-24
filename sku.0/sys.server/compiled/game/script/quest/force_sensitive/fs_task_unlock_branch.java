package script.quest.force_sensitive;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.fs_quests;
import script.library.quests;

public class fs_task_unlock_branch extends script.base_script
{
    public fs_task_unlock_branch()
    {
    }
    public int OnQuestActivated(obj_id self, int questRow) throws InterruptedException
    {
        boolean success = false;
        if (quests.isMyQuest(questRow, "quest.force_sensitive.fs_task_unlock_branch"))
        {
            String unlockBranch = null;
            String questName = quests.getDataEntry(questRow, "NAME");
            if (questName != null)
            {
                String objvarName = "quest." + questName + ".target";
                if (hasObjVar(self, objvarName))
                {
                    unlockBranch = getStringObjVar(self, objvarName);
                }
                else 
                {
                    unlockBranch = quests.getDataEntry(questRow, "TARGET");
                }
                if (unlockBranch != null && unlockBranch.length() > 0)
                {
                    success = fs_quests.unlockBranch(self, unlockBranch);
                    if (!success)
                    {
                        CustomerServiceLog("fs_quests", "failed to unlock force sensitive branch " + unlockBranch + " to complete quest task " + questName + " for %TU", self, null);
                    }
                }
                else 
                {
                    CustomerServiceLog("fs_quests", "failed to retrieve force sensitive branch name to complete quest task " + questName + " for %TU", self, null);
                }
                quests.complete(questName, self, success);
            }
            else 
            {
                CustomerServiceLog("fs_quests", "failed to retrieve quest name for quest id" + questRow + " for %TU ", self, null);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
