package script.quest.task;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.factions;
import script.library.quests;
import script.library.utils;

public class set_faction extends script.base_script
{
    public set_faction()
    {
    }
    public int OnQuestActivated(obj_id self, int questRow) throws InterruptedException
    {
        if (quests.isMyQuest(questRow, "quest.task.set_faction"))
        {
            boolean success = false;
            String questName = quests.getDataEntry(questRow, "NAME");
            String targetObjVarName = "quest." + questName + ".target";
            String factionName = null;
            if (hasObjVar(self, targetObjVarName))
            {
                factionName = getStringObjVar(self, targetObjVarName);
            }
            else 
            {
                factionName = quests.getDataEntry(questRow, "TARGET");
            }
            if (factionName != null && factionName.length() > 0)
            {
                String parameterObjVarName = "quest." + questName + ".parameter";
                int factionPoints = 0;
                if (hasObjVar(self, parameterObjVarName))
                {
                    factionPoints = getIntObjVar(self, parameterObjVarName);
                }
                else 
                {
                    String factionPointsString = quests.getDataEntry(questRow, "PARAMETER");
                    if (factionPointsString != null && factionPointsString.length() > 0)
                    {
                        factionPoints = utils.stringToInt(factionPointsString);
                    }
                }
                factions.setFactionStanding(self, factionName, factionPoints);
                success = true;
            }
            else 
            {
                LOG("newquests", "failed to retrieve a faction name from object variable (" + targetObjVarName + ") or from the TARGET column at quest row " + questRow + " in quests.tab");
            }
            quests.complete(questName, self, success);
        }
        return SCRIPT_CONTINUE;
    }
}
