package script.npc.create.imperial;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.npc;
import script.library.weapons;

public class stormtrooper extends script.base_script
{
    public stormtrooper()
    {
    }
    public static final String BASE_PATH = "npc.create";
    public static final String SCRIPT_ME = BASE_PATH + ".imperial.stormtrooper";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        weapons.createWeapon("object/weapon/ranged/carbine/carbine_e11.iff", self, 1.0f);
        setName(self, "TK-" + rand(1, 1000));
        detachScript(self, SCRIPT_ME);
        return SCRIPT_CONTINUE;
    }
}
