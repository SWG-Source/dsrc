package script.theme_park.poi.tatooine.harrass;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;

public class poi_2_stormtrooper_1_jawa extends script.base_script
{
    public poi_2_stormtrooper_1_jawa()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        location here = new location(getLocation(self));
        here.z = here.z + 5;
        obj_id tr1 = create.object("stormtrooper", here);
        here.x = here.x - 1;
        here.z = here.z + 1;
        obj_id tr2 = create.object("stormtrooper", here);
        here.x = here.x + 2;
        obj_id jawa = createObject("object/creature/npc/base/jawa_base_male.iff", here);
        here.x = here.x + 15;
        obj_id farm = createObject("object/static/test/test_static_sandcrawler.iff", here);
        attachScript(jawa, "theme_park.poi.tatooine.behavior.poi_victim");
        attachScript(tr1, "theme_park.poi.tatooine.behavior.poi_harrass");
        attachScript(tr2, "theme_park.poi.tatooine.behavior.poi_harrass");
        dictionary jawaObj = new dictionary();
        jawaObj.put("target", jawa);
        messageTo(tr1, "doFacing", jawaObj, 3, true);
        messageTo(tr2, "doFacing", jawaObj, 3, true);
        return SCRIPT_CONTINUE;
    }
}
