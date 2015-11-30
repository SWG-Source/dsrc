package script.item.camp;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.camping;

public class camp_advanced extends script.base_script
{
    public camp_advanced()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        createTriggerVolume("campsite", camping.getAdvancedCampRadius(self), true);
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "modules.ids"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] modules = getObjIdArrayObjVar(self, "modules.ids");
        if (modules == null || modules.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        for (int i = 0; i < modules.length; i++)
        {
            destroyObject(modules[i]);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleControlTerminalLocation(obj_id self, dictionary params) throws InterruptedException
    {
        float radius = camping.getAdvancedCampRadius(self);
        obj_id[] objects = getNonCreaturesInRange(self, radius);
        if (objects == null || objects.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        for (int i = 0; i < objects.length; i++)
        {
            if (hasScript(objects[i], "terminal.terminal_camp"))
            {
                setObjVar(objects[i], "camp", self);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleCampDecay(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
