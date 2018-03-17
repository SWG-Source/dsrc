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

public class aaph_koden extends script.base_script
{
    public aaph_koden()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        obj_id shirt = createObject("object/tangible/wearables/shirt/shirt_s28.iff", self, "");
        obj_id pants = createObject("object/tangible/wearables/pants/pants_s32.iff", self, "");
        obj_id shoes = createObject("object/tangible/wearables/shoes/shoes_s07.iff", self, "");
        obj_id hair = createObject("object/tangible/hair/zabrak/hair_zabrak_female_s17.iff", self, "");
        hue.setColor(shirt, 1, 43);
        hue.setColor(pants, 1, 174);
        hue.setColor(pants, 1, 43);
        hue.setColor(shoes, 1, 44);
        hue.setColor(shoes, 1, 11);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        setName(self, "Aaph Koden");
        return SCRIPT_CONTINUE;
    }
}
