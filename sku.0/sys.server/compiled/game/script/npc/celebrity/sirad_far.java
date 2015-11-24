package script.npc.celebrity;

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

public class sirad_far extends script.base_script
{
    public sirad_far()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        obj_id shirt = createObject("object/tangible/wearables/shirt/shirt_s07.iff", self, "");
        obj_id pants = createObject("object/tangible/wearables/pants/pants_s27.iff", self, "");
        obj_id shoes = createObject("object/tangible/wearables/shoes/shoes_s02.iff", self, "");
        obj_id jacket = createObject("object/tangible/wearables/jacket/jacket_s02.iff", self, "");
        obj_id hair = createObject("object/tangible/hair/human/hair_human_male_s19.iff", self, "");
        hue.setColor(shirt, 1, 174);
        hue.setColor(pants, 1, 85);
        hue.setColor(jacket, 1, 112);
        hue.setColor(hair, 1, 12);
        setName(self, "Sirad Far");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        debugSpeakMsg(self, "Jabba the Hutt?  Never heard of him...");
        return SCRIPT_CONTINUE;
    }
}
