package script.quest.task.fs_quest_cs;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.quests;
import script.library.fs_counterstrike;

public class quest_failed_escort extends script.base_script
{
    public quest_failed_escort()
    {
    }
    public int OnQuestActivated(obj_id self, int questRow) throws InterruptedException
    {
        if (!quests.isMyQuest(questRow, "quest.task.fs_quest_cs.quest_failed_escort"))
        {
            return SCRIPT_CONTINUE;
        }
        if (quests.isActive("fs_cs_escort_commander_pri", self))
        {
            if (hasObjVar(self, "fs_cs.myCapturedCommander"))
            {
                obj_id commander = getObjIdObjVar(self, "fs_cs.myCapturedCommander");
                if (isIdValid(commander) && exists(commander))
                {
                    messageTo(commander, "msgPrimaryEscortAbandoned", null, 0.0f, false);
                }
            }
        }
        fs_counterstrike.resetPlayerToStart(self, null);
        sendSystemMessage(self, new string_id("fs_quest_village", "arrived_without_commander"));
        detachScript(self, "quest.task.fs_quest_cs.quest_failed_escort");
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        detachScript(self, "quest.task.fs_quest_cs.quest_failed_escort");
        return SCRIPT_CONTINUE;
    }
}
