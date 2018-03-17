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

public class om_aynat extends script.base_script
{
    public om_aynat()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        obj_id pants = createObject("object/tangible/wearables/pants/pants_s31.iff", self, "");
        obj_id shirt = createObject("object/tangible/wearables/shirt/shirt_s30.iff", self, "");
        obj_id hair = createObject("object/tangible/hair/human/hair_human_male_s25.iff", self, "");
        obj_id boots = createObject("object/tangible/wearables/boots/boots_s05.iff", self, "");
        setName(self, "Om Aynat");
        hue.setColor(pants, 1, 68);
        hue.setColor(pants, 2, 59);
        hue.setColor(shirt, 1, 14);
        hue.setColor(shirt, 2, 9);
        hue.setColor(hair, 1, 5);
        hue.setColor(boots, 1, 8);
        hue.setColor(boots, 2, 9);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
}
