package script.systems.event_perk;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;

public class invulnerable extends script.base_script
{
    public invulnerable()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setInvulnerable(self, true);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        ai_lib.stop(self);
        pickNameByTemplate(self);
        return SCRIPT_CONTINUE;
    }
    public void pickNameByTemplate(obj_id self) throws InterruptedException
    {
        String name = " ";
        String whatAmI = getTemplateName(self);
        if (whatAmI.equals("object/mobile/dressed_stormtrooper_m.iff"))
        {
            int roll = rand(100, 999);
            name = "TK-" + roll;
        }
        if (whatAmI.equals("object/mobile/dressed_tie_fighter_m.iff"))
        {
            int roll = rand(100, 999);
            name = "DS-" + roll;
        }
        if (whatAmI.equals("object/mobile/atat.iff"))
        {
            name = "AT-AT";
        }
        if (whatAmI.equals("object/mobile/atst.iff"))
        {
            name = "AT-ST";
        }
        if (whatAmI.equals("object/mobile/bantha_saddle.iff"))
        {
            name = "a bantha mount";
        }
        setName(self, name);
        return;
    }
}
