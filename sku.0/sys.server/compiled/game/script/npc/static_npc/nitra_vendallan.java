package script.npc.static_npc;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.hue;
import script.library.colors;

public class nitra_vendallan extends script.base_script
{
    public nitra_vendallan()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        obj_id shirt = createObject("object/tangible/wearables/shirt/shirt_s07.iff", self, "");
        obj_id pants = createObject("object/tangible/wearables/pants/pants_s12.iff", self, "");
        obj_id shoes = createObject("object/tangible/wearables/shoes/shoes_s03.iff", self, "");
        obj_id vest = createObject("object/tangible/wearables/vest/vest_s05.iff", self, "");
        hue.setColor(shirt, 1, colors.GREEN);
        hue.setColor(shirt, 1, colors.YELLOW);
        hue.setColor(vest, 1, colors.ORANGE);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        setName(self, "Nitra Vendallan");
        return SCRIPT_CONTINUE;
    }
}
