package script.theme_park.warren;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;

public class imperial_spawner extends script.base_script
{
    public imperial_spawner()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        obj_id imperialOfficer = create.createNpc("imperial_army_captain", "object/mobile/dressed_imperial_captain_m.iff", getLocation(self));
        setInvulnerable(imperialOfficer, true);
        attachScript(imperialOfficer, "theme_park.warren.imperial_captain");
        setObjVar(imperialOfficer, "spawnEgg", self);
        return SCRIPT_CONTINUE;
    }
}
