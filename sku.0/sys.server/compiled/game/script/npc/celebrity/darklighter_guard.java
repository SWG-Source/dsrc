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

public class darklighter_guard extends script.base_script
{
    public darklighter_guard()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        obj_id bicep_l = createObject("object/tangible/wearables/armor/marine/armor_marine_bicep_l.iff", self, "");
        obj_id bicep_r = createObject("object/tangible/wearables/armor/marine/armor_marine_bicep_r.iff", self, "");
        obj_id boots = createObject("object/tangible/wearables/armor/marine/armor_marine_boots.iff", self, "");
        obj_id belt = createObject("object/tangible/wearables/belt/belt_s05.iff", self, "");
        obj_id shirt = createObject("object/tangible/wearables/armor/marine/armor_marine_chest_plate.iff", self, "");
        obj_id pants = createObject("object/tangible/wearables/armor/marine/armor_marine_leggings.iff", self, "");
        setName(self, "Darklight Guardian");
        hue.setColor(bicep_l, 1, 54);
        hue.setColor(bicep_l, 2, 90);
        hue.setColor(bicep_l, 3, 87);
        hue.setColor(bicep_r, 1, 54);
        hue.setColor(bicep_r, 2, 90);
        hue.setColor(bicep_r, 3, 87);
        hue.setColor(belt, 1, 31);
        hue.setColor(belt, 2, 16);
        hue.setColor(boots, 3, 87);
        hue.setColor(pants, 1, 90);
        hue.setColor(pants, 2, 54);
        hue.setColor(shirt, 1, 90);
        hue.setColor(shirt, 2, 54);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        debugSpeakMsg(self, "Todd Bailey is my hero");
        return SCRIPT_CONTINUE;
    }
}
