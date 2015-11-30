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
import script.library.chat;
import script.library.skill;

public class binna_jode extends script.base_script
{
    public binna_jode()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        obj_id skirt = createObject("object/tangible/wearables/skirt/skirt_s04.iff", self, "");
        obj_id shirt = createObject("object/tangible/wearables/shirt/shirt_s42.iff", self, "");
        obj_id hair = createObject("object/tangible/hair/human/hair_human_female_s07.iff", self, "");
        obj_id shoes = createObject("object/tangible/wearables/shoes/shoes_s08.iff", self, "");
        setName(self, "Binna Jode");
        hue.setColor(skirt, 1, 57);
        hue.setColor(skirt, 2, 118);
        hue.setColor(shirt, 1, 118);
        hue.setColor(shirt, 2, 57);
        hue.setColor(hair, 1, 7);
        hue.setColor(shoes, 1, 57);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
}
