package script.npc.celebrity;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;

public class porcellus extends script.base_script
{
    public porcellus()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        obj_id pants = createObject("object/tangible/wearables/pants/pants_s01.iff", self, "");
        obj_id shoes = createObject("object/tangible/wearables/shoes/shoes_s03.iff", self, "");
        obj_id shirt = createObject("object/tangible/wearables/shirt/shirt_s05.iff", self, "");
        if (hasScript(self, "theme_park.tatooine.max_quest.porcellus"))
        {
            detachScript(self, "theme_park.tatooine.max_quest.porcellus_convo");
        }
        attachScript(self, "conversation.porcellus");
        return SCRIPT_CONTINUE;
    }
}
