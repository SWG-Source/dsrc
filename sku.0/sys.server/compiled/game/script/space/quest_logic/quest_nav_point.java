package script.space.quest_logic;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.space_quest;
import script.library.space_utils;

public class quest_nav_point extends script.base_script
{
    public quest_nav_point()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        requestPreloadCompleteTrigger(self);
        return SCRIPT_CONTINUE;
    }
    public int OnPreloadComplete(obj_id self) throws InterruptedException
    {
        obj_id questManager = getNamedObject(space_quest.QUEST_MANAGER);
        if (questManager == null)
        {
            return SCRIPT_CONTINUE;
        }
        dictionary outparams = new dictionary();
        outparams.put("point", self);
        outparams.put("type", "nav");
        space_utils.notifyObject(questManager, "registerQuestLocation", outparams);
        return SCRIPT_CONTINUE;
    }
}
