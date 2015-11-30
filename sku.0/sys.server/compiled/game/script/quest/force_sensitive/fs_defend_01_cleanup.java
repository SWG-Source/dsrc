package script.quest.force_sensitive;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.factions;
import script.library.fs_quests;
import script.library.quests;

public class fs_defend_01_cleanup extends script.base_script
{
    public fs_defend_01_cleanup()
    {
    }
    public int msgQuestAbortPhaseChange(obj_id self, dictionary params) throws InterruptedException
    {
        if (!quests.isComplete("fs_defend_01", self) || !quests.isComplete("fs_defend_02", self))
        {
            CustomerServiceLog("fs_quests", "failing and clearing incomplete Ranged Speed quests for %TU", self, null);
            quests.complete("fs_defend_set_faction", self, false);
            quests.complete("fs_defend_01", self, false);
            quests.complete("fs_defend_02", self, false);
            quests.complete("fs_defend_wait_01", self, false);
            quests.complete("fs_defend_reward_01", self, false);
            quests.complete("fs_defend_reward_02", self, false);
            clearCompletedQuest(self, quests.getQuestId("fs_defend_set_faction"));
            clearCompletedQuest(self, quests.getQuestId("fs_defend_01"));
            clearCompletedQuest(self, quests.getQuestId("fs_defend_02"));
            clearCompletedQuest(self, quests.getQuestId("fs_defend_wait_01"));
            clearCompletedQuest(self, quests.getQuestId("fs_defend_reward_01"));
            clearCompletedQuest(self, quests.getQuestId("fs_defend_reward_02"));
            factions.setFactionStanding(self, "sith_shadow_nonaggro", 0);
        }
        if (!quests.isComplete("fs_defend_03", self) || !quests.isComplete("fs_defend_04", self))
        {
            CustomerServiceLog("fs_quests", "failing and clearing incomplete Melee Defense quests for %TU", self, null);
            quests.complete("fs_defend_set_faction_02", self, false);
            quests.complete("fs_defend_03", self, false);
            quests.complete("fs_defend_04", self, false);
            quests.complete("fs_defend_wait_02", self, false);
            quests.complete("fs_defend_reward_03", self, false);
            quests.complete("fs_defend_reward_04", self, false);
            clearCompletedQuest(self, quests.getQuestId("fs_defend_set_faction_02"));
            clearCompletedQuest(self, quests.getQuestId("fs_defend_03"));
            clearCompletedQuest(self, quests.getQuestId("fs_defend_04"));
            clearCompletedQuest(self, quests.getQuestId("fs_defend_wait_02"));
            clearCompletedQuest(self, quests.getQuestId("fs_defend_reward_03"));
            clearCompletedQuest(self, quests.getQuestId("fs_defend_reward_04"));
            factions.setFactionStanding(self, "sith_shadow_nonaggro", 0);
        }
        if (hasObjVar(self, "quest.destroy_multi"))
        {
            Vector objvarsToRemove = new Vector();
            obj_var_list objvarList = getObjVarList(self, "quest.destroy_multi");
            int numItems = objvarList.getNumItems();
            int iter = 0;
            for (iter = 0; iter < numItems; ++iter)
            {
                obj_var objvar = objvarList.getObjVar(iter);
                String[] questNames = objvar.getStringArrayData();
                Vector newArray = new Vector();
                int questIter = 0;
                for (questIter = 0; questIter < questNames.length; ++questIter)
                {
                    if (!questNames[questIter].equals("fs_defend_01") && !questNames[questIter].equals("fs_defend_02") && !questNames[questIter].equals("fs_defend_03") && !questNames[questIter].equals("fs_defend_04"))
                    {
                        newArray.add(questNames[questIter]);
                    }
                }
                if (newArray.size() > 0)
                {
                    String[] updateNames = new String[newArray.size()];
                    newArray.toArray(updateNames);
                    setObjVar(self, "quest.destroy_multi." + objvar.getName(), updateNames);
                }
                else 
                {
                    objvarsToRemove.add("quest.destroy_multi." + objvar.getName());
                }
            }
            if (objvarsToRemove.size() > 0)
            {
                java.util.Iterator i = objvarsToRemove.iterator();
                while (i.hasNext())
                {
                    String name = (String)i.next();
                    removeObjVar(self, name);
                }
            }
        }
        detachScript(self, "quest.force_sensitive.fs_defend_01_cleanup");
        return SCRIPT_CONTINUE;
    }
    public int OnQuestActivated(obj_id self, int questRow) throws InterruptedException
    {
        String questName = quests.getDataEntry(questRow, "NAME");
        if (questName == null)
        {
            return SCRIPT_CONTINUE;
        }
        if (questName.equals("fs_defend_reward_01") || questName.equals("fs_defend_reward_03"))
        {
            factions.setFactionStanding(self, "sith_shadow_nonaggro", 5000);
            fs_quests.setQuestCompleted(self, questName);
            detachScript(self, "quest.force_sensitive.fs_defend_01_cleanup");
        }
        return SCRIPT_CONTINUE;
    }
}
