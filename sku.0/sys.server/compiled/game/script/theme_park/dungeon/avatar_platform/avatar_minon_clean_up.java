package script.theme_park.dungeon.avatar_platform;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.ai.ai_combat;
import script.library.combat;
import script.library.utils;

public class avatar_minon_clean_up extends script.base_script
{
    public avatar_minon_clean_up()
    {
    }
    public int OnExitedCombat(obj_id self) throws InterruptedException
    {
        if (!ai_lib.isAiDead(self))
        {
            messageTo(self, "checkForReset", null, 60f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int checkForReset(obj_id self, dictionary params) throws InterruptedException
    {
        if (ai_lib.isInCombat(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id currentCell = getContainedBy(self);
        obj_id[] items = getContents(currentCell);
        if (items == null)
        {
            return SCRIPT_CONTINUE;
        }
        int numItems = items.length;
        if (numItems > 0)
        {
            for (int i = 0; i < numItems; i++)
            {
                if (isPlayer(items[i]))
                {
                    messageTo(self, "checkForReset", null, 60f, false);
                    return SCRIPT_CONTINUE;
                }
            }
        }
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
