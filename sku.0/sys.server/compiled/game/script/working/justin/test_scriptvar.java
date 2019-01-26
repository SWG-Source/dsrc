package script.working.justin;

import script.deltadictionary;
import script.obj_id;

public class test_scriptvar extends script.base_script
{
    public test_scriptvar()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        deltadictionary vars = self.getScriptVars();
        vars.put("test", "value");
        debugServerConsoleMsg(self, "On attach, added test/value to script vars");
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        deltadictionary vars = self.getScriptVars();
        String s = vars.getString("test");
        debugServerConsoleMsg(self, "OnDetach, getting test " + s);
        return SCRIPT_CONTINUE;
    }
    public String test_console(String params) throws InterruptedException
    {
        debugServerConsoleMsg(obj_id.NULL_ID, "test_console");
        int weather = 0;
        try
        {
            Integer i = Integer.valueOf(params);
            weather = i;
        }
        catch(NumberFormatException err)
        {
            weather = 0;
        }
        setWeatherData(weather, 20, 20);
        return "Weather set to " + params;
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        if (text.equals("makeWaypoint"))
        {
            obj_id waypoint = createWaypointInDatapad(self, getLocation(self));
            setWaypointName(waypoint, "test waypoint");
        }
        return SCRIPT_CONTINUE;
    }
}
