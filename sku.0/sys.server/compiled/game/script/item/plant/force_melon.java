package script.item.plant;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.healing;
import script.library.consumable;
import script.library.player_stomach;

public class force_melon extends script.base_script
{
    public force_melon()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (hasScript(self, "item.comestible.crafted"))
        {
            detachScript(self, "item.comestible.crafted");
        }
        if (!hasScript(self, "item.medicine.stimpack"))
        {
            attachScript(self, "item.medicine.stimpack");
        }
        if (!hasObjVar(self, "healing.power"))
        {
            setObjVar(self, "healing.power", 1000);
        }
        if (!hasObjVar(self, "healing.combat_level_required"))
        {
            setObjVar(self, "healing.combat_level_required", 50);
        }
        return SCRIPT_CONTINUE;
    }
}
