package script.theme_park.restuss_event;

import script.dictionary;
import script.obj_id;

public class restuss_event_watcher extends script.base_script
{
    public restuss_event_watcher()
    {
    }
    public int incrimentPhase(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id controller = null;
        if (hasScript(self, "theme_park.restuss_event.restuss_event_manager"))
        {
            controller = self;
        }
        else 
        {
            controller = getFirstObjectWithScript(getLocation(self), 200.0f, "theme_park.restuss_event.restuss_event_manager");
        }
        String element = getStringObjVar(self, "element");
        if (element.contains("wall") || element.contains("medi"))
        {
            return SCRIPT_CONTINUE;
        }
        messageTo(controller, "stepIncrease", null, 1, false);
        return SCRIPT_CONTINUE;
    }
}
