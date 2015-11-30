package script.theme_park.poi.naboo.herd;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class poi_herd_grasslands_falumpaset extends script.theme_park.poi.base
{
    public poi_herd_grasslands_falumpaset()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        poiCreateObject("object/creature/monster/falumpaset/falumpaset_grasslands_large.iff", 8, 8);
        setObjVar(self, "spawned", 1);
        int x = rand(7, 11);
        setObjVar(self, "max", x);
        messageTo(self, "spawnCreatures", null, 4, true);
        return SCRIPT_CONTINUE;
    }
    public String getFalumpaset(obj_id self) throws InterruptedException
    {
        String falumpasetSpawn = "object/creature/monster/falumpaset/falumpaset.iff";
        int falumpasetType = rand(1, 2);
        switch (falumpasetType)
        {
            case 1:
            falumpasetSpawn = "object/creature/monster/falumpaset/falumpaset_grasslands_medium.iff";
            break;
            case 2:
            falumpasetSpawn = "object/creature/monster/falumpaset/falumpaset_grasslands_small.iff";
            break;
        }
        return falumpasetSpawn;
    }
    public int spawnCreatures(obj_id self, dictionary params) throws InterruptedException
    {
        location here = new location(getLocation(self));
        int i = getIntObjVar(self, "spawned");
        int max = getIntObjVar(self, "max");
        if (i <= max)
        {
            here.x = rand(-10, 10);
            here.y = rand(-10, 10);
            poiCreateObject(getFalumpaset(self), here.x, here.y);
            i = i + 1;
            setObjVar(self, "spawned", i);
            messageTo(self, "spawnCreatures", null, 3, true);
        }
        return SCRIPT_CONTINUE;
    }
}
