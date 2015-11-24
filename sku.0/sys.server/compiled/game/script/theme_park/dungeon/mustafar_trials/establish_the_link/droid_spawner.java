package script.theme_park.dungeon.mustafar_trials.establish_the_link;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.create;
import script.library.trial;

public class droid_spawner extends script.base_script
{
    public droid_spawner()
    {
    }
    public static final boolean LOGGING = false;
    public static final String RELAY_DROID = "som_link_relay_droid";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "spawnRelayDroid", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int spawnRelayDroid(obj_id self, dictionary params) throws InterruptedException
    {
        location spawnLoc = getLocation(self);
        obj_id creature = create.object(RELAY_DROID, spawnLoc);
        attachScript(creature, "theme_park.dungeon.mustafar_trials.establish_the_link.droid_patrol_script");
        utils.setScriptVar(creature, trial.PARENT, self);
        return SCRIPT_CONTINUE;
    }
    public int droidDied(obj_id self, dictionary params) throws InterruptedException
    {
        messageTo(self, "spawnRelayDroid", null, 0, false);
        return SCRIPT_CONTINUE;
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING || trial.UPLINK_LOGGING)
        {
            LOG("logging/droid_spawner/" + section, message);
        }
    }
}
