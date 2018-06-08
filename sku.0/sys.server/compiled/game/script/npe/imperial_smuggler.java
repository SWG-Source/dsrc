package script.npe;

import script.dictionary;
import script.library.chat;
import script.library.create;
import script.library.groundquests;
import script.location;
import script.obj_id;

public class imperial_smuggler extends script.base_script
{
    public imperial_smuggler()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        createTriggerVolume("npe_imperial_radius", 3.0f, true);
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String name, obj_id player) throws InterruptedException
    {
        if (!isPlayer(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (groundquests.isTaskActive(player, "npe_smuggler_try", "impTalks"))
        {
            chat.chat(self, "Hey you! Get over here!");
            groundquests.sendSignal(player, "impTalks");
        }
        else 
        {
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int help(obj_id self, dictionary params) throws InterruptedException
    {
        location me = getLocation(self);
        me.x += 1;
        me.y += 1;
        create.object("npe_stormtrooper_quest", me);
        me.x += 2;
        me.y += 2;
        create.object("npe_stormtrooper_quest", me);
        return SCRIPT_CONTINUE;
    }
}
