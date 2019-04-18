package script.item.camp;

import script.dictionary;
import script.library.camping;
import script.obj_id;

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
        for (obj_id module : modules) {
            destroyObject(module);
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
        for (obj_id object : objects) {
            if (hasScript(object, "terminal.terminal_camp")) {
                setObjVar(object, "camp", self);
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
