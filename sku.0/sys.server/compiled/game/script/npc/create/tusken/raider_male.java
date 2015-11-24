package script.npc.create.tusken;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.npc;
import script.library.weapons;

public class raider_male extends script.base_script
{
    public raider_male()
    {
    }
    public static final String BASE_PATH = "npc.create";
    public static final String SCRIPT_ME = BASE_PATH + ".tusken.raider_male";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        weapons.createWeapon("object/weapon/melee/baton/baton_gaderiffi.iff", self, 1.0f);
        detachScript(self, SCRIPT_ME);
        return SCRIPT_CONTINUE;
    }
}
