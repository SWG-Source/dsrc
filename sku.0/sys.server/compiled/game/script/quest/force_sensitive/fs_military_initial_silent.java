package script.quest.force_sensitive;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.utils;

public class fs_military_initial_silent extends script.base_script
{
    public fs_military_initial_silent()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        createTriggerVolume("fs_two_military_range", 26, true);
        messageTo(self, "approach", null, 3, false);
        return SCRIPT_CONTINUE;
    }
    public int approach(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id owner = null;
        owner = getObjIdObjVar(self, "quest.owner");
        setMovementRun(self);
        if ((isIdValid(owner)) && (exists(owner)))
        {
            ai_lib.aiFollow(self, owner);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id target) throws InterruptedException
    {
        if (volumeName.equals("fs_two_military_range"))
        {
            obj_id owner = getObjIdObjVar(self, "quest.owner");
            if ((isIdValid(owner)) && (exists(owner)))
            {
                if (owner == target)
                {
                    if (!hasObjVar(self, "contact"))
                    {
                        setObjVar(self, "contact", 1);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int aiCorpsePrepared(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = utils.getObjIdObjVar(self, "quest.owner", obj_id.NULL_ID);
        if (isIdValid(player))
        {
            dictionary d = new dictionary();
            d.put("corpse", self);
            d.put("questName", "two_military");
            messageTo(player, "addQuestLootToCorpse", d, 0, false);
        }
        return SCRIPT_CONTINUE;
    }
}
