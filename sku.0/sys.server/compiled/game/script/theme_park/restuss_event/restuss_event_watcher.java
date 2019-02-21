package script.theme_park.restuss_event;

import script.dictionary;
import script.obj_id;
import script.library.utils;

public class restuss_event_watcher extends script.base_script
{
    public restuss_event_watcher()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException {
        if(automationEnabled()) completeStageOne(self, null);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException {
        if(automationEnabled()) completeStageOne(self, null);
        return SCRIPT_CONTINUE;
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
    private boolean automationEnabled() {
        String restussEvent = getConfigSetting("EventTeam", "restussEvent");
        if (restussEvent == null || (!restussEvent.equals("1") && !restussEvent.equals("true"))) {
            return false;
        }
        return true;
    }
    public int completeStageOne(obj_id self, dictionary params) throws InterruptedException{
        if(utils.hasScriptVar(self, "processed")) return SCRIPT_CONTINUE;
        utils.setScriptVar(self, "processed", 1);

        String element = getStringObjVar(self, "element");
        LOG("events", "Restuss Event - Completing Stage One (factional base) for element \"" + element + "\".");
        if (element.contains("baracks")) {
            messageTo(self, "incrimentPhase", null, 0, false);
            messageTo(self, "incrimentPhase", null, 20, false);
            messageTo(self, "incrimentPhase", null, 40, false);
        }
        else if (element.contains("headq")) {
            messageTo(self, "incrimentPhase", null, 5, false);
            messageTo(self, "incrimentPhase", null, 25, false);
            messageTo(self, "incrimentPhase", null, 45, false);
        }
        else if (element.contains("commu")) {
            messageTo(self, "incrimentPhase", null, 10, false);
            messageTo(self, "incrimentPhase", null, 30, false);
            messageTo(self, "incrimentPhase", null, 50, false);
        }
        else if (element.contains("logis")) {
            messageTo(self, "incrimentPhase", null, 15, false);
            messageTo(self, "incrimentPhase", null, 35, false);
            messageTo(self, "incrimentPhase", null, 55, false);
        }
        return SCRIPT_CONTINUE;
    }
}
