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

public class aujante_klee extends script.base_script
{
    public aujante_klee()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        obj_id pants = createObject("object/tangible/wearables/pants/nightsister_pants_s01.iff", self, "");
        obj_id boots = createObject("object/tangible/wearables/boots/nightsister_boots.iff", self, "");
        obj_id shirt = createObject("object/tangible/wearables/shirt/nightsister_shirt_s02.iff", self, "");
        obj_id hat = createObject("object/tangible/wearables/hat/nightsister_hat_s01.iff", self, "");
        hue.setColor(pants, 1, 7);
        hue.setColor(pants, 2, 34);
        hue.setColor(pants, 3, 24);
        hue.setColor(pants, 4, 34);
        hue.setColor(boots, 1, 44);
        hue.setColor(hat, 1, 34);
        hue.setColor(shirt, 1, 7);
        hue.setColor(shirt, 2, 64);
        setName(self, "Aujante K'lee");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        debugSpeakMsg(self, "I lead the Singing Mountain Clan.");
        return SCRIPT_CONTINUE;
    }
}
