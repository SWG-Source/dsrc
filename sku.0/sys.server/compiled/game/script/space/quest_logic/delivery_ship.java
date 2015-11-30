package script.space.quest_logic;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.space_quest;
import script.library.space_utils;
import script.library.utils;
import script.library.prose;

public class delivery_ship extends script.base_script
{
    public delivery_ship()
    {
    }
    public int lightspeedJump(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObjectHyperspace(self);
        return SCRIPT_CONTINUE;
    }
    public int objectDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id quest = getObjIdObjVar(self, "quest");
        if (quest == null)
        {
            return SCRIPT_CONTINUE;
        }
        dictionary outparams = new dictionary();
        outparams.put("ship", self);
        space_utils.notifyObject(quest, "deliveryTargetDestroyed", outparams);
        return SCRIPT_CONTINUE;
    }
}
