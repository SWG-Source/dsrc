package script.quest.task.fs_quest_cs;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.jedi;
import script.library.utils;
import script.library.trace;
import script.library.quests;
import script.library.fs_counterstrike;

public class quest_done extends script.base_script
{
    public quest_done()
    {
    }
    public int OnQuestActivated(obj_id self, int questRow) throws InterruptedException
    {
        if (!quests.isMyQuest(questRow, "quest.task.fs_quest_cs.quest_done"))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, "fs_cs.myCapturedCommander"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id commander = getObjIdObjVar(self, "fs_cs.myCapturedCommander");
        if (!isIdValid(commander) || !exists(commander))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(commander, "fs_cs.primaryEscort"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id primary = getObjIdObjVar(commander, "fs_cs.primaryEscort");
        if (!isIdValid(primary) || !exists(primary))
        {
            return SCRIPT_CONTINUE;
        }
        boolean teamComplete = false;
        obj_id shieldKiller = null;
        if (hasObjVar(commander, fs_counterstrike.OBJVAR_CAMP_SHIELD_KILLER))
        {
            shieldKiller = getObjIdObjVar(commander, fs_counterstrike.OBJVAR_CAMP_SHIELD_KILLER);
            if (shieldKiller != primary)
            {
                teamComplete = true;
            }
        }
        destroyObject(commander);
        dictionary d = new dictionary();
        d.put("teamComplete", teamComplete);
        messageTo(primary, "msgQuestComplete", d, 0.0f, true);
        if (shieldKiller != primary)
        {
            messageTo(shieldKiller, "msgQuestComplete", d, 0.0f, true);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (quests.isActive("fs_cs_quest_done", self))
        {
            quests.complete("fs_cs_quest_done", self, true);
        }
        detachScript(self, "quest.task.fs_quest_cs.quest_done");
        return SCRIPT_CONTINUE;
    }
}
