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

public class gorr_ebelt extends script.base_script
{
    public gorr_ebelt()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        obj_id shirt = createObject("object/tangible/wearables/shirt/shirt_s30.iff", self, "");
        obj_id pants = createObject("object/tangible/wearables/pants/pants_s07.iff", self, "");
        obj_id boots = createObject("object/tangible/wearables/boots/boots_s12.iff", self, "");
        obj_id jackt = createObject("object/tangible/wearables/jacket/jacket_s35.iff", self, "");
        hue.setColor(pants, 1, 158);
        hue.setColor(pants, 2, 90);
        hue.setColor(jackt, 1, 83);
        hue.setColor(boots, 1, 9);
        setName(self, "Gorr Ebelt");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        debugSpeakMsg(self, "I work for Jabba.  You got a problem with that?");
        return SCRIPT_CONTINUE;
    }
}
