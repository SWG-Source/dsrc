package script.npc.static_npc;

import script.library.ai_lib;
import script.library.colors;
import script.library.hue;
import script.obj_id;

public class nitra_vendallan extends script.base_script
{
    public nitra_vendallan()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        obj_id shirt = createObject("object/tangible/wearables/shirt/shirt_s07.iff", self, "");
        obj_id pants = createObject("object/tangible/wearables/pants/pants_s12.iff", self, "");
        obj_id shoes = createObject("object/tangible/wearables/boots/boots_s12.iff", self, "");
        obj_id vest = createObject("object/tangible/wearables/vest/vest_s05.iff", self, "");
        obj_id hair = createObject("object/tangible/hair/human/hair_human_female_s08.iff", self, "");
        hue.setColor(shirt, 1, colors.GREEN);
        hue.setColor(shirt, 1, colors.YELLOW);
        hue.setColor(vest, 1, colors.ORANGE);
        hue.setColor(shoes, 1, colors.BLACK);
        hue.setColor(hair, 1, colors.BEIGE);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        setName(self, "Nitra Vendallan");
        return SCRIPT_CONTINUE;
    }
}
