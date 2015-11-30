package script.item.container;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class player_loot_crate extends script.item.container.locked_slicable
{
    public player_loot_crate()
    {
    }
    public static final String LOOT_CRATE = "object/tangible/container/loot/loot_crate.iff";
    public int handleSlicingSuccess(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id myContainer = getContainedBy(self);
        obj_id lootCrate = null;
        if (isIdValid(myContainer))
        {
            lootCrate = createObject(LOOT_CRATE, myContainer, "");
        }
        else 
        {
            lootCrate = createObject(LOOT_CRATE, getLocation(self));
        }
        removeObjVar(lootCrate, "slicing.locked");
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        names[0] = "lock_mechanism";
        if (hasObjVar(self, "slicing.slicable"))
        {
            attribs[0] = "@obj_attr_n:slicable";
        }
        else 
        {
            attribs[0] = "@obj_attr_n:broken";
        }
        return SCRIPT_CONTINUE;
    }
}
