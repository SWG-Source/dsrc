package script.quest.force_sensitive;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.quests;

public class fs_reflex2_theater extends script.base_script
{
    public fs_reflex2_theater()
    {
    }
    public int OnTheaterCreated(obj_id self, obj_id[] objects, obj_id player, obj_id creator) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int handleSetupCrate(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("newquests", "REFLEX2: attempting to initialize crate");
        obj_id player = params.getObjId("player");
        int retry = params.getInt("retry");
        obj_id crate = getFirstObjectWithScript(getLocation(self), 32.0f, "quest.force_sensitive.fs_reflex2_crate");
        dictionary d = new dictionary();
        d.put("player", player);
        d.put("retry", (retry + 1));
        if (isIdValid(crate))
        {
            LOG("newquests", "REFLEX2: crate found, sending init message");
            messageTo(crate, "handleSetupCrate", d, 1.0f, false);
        }
        else 
        {
            LOG("newquests", "REFLEX2: crate not found, trying again");
            if (retry < 5)
            {
                messageTo(self, "handleSetupCrate", d, 15.0f, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
