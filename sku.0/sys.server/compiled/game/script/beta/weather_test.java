package script.beta;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class weather_test extends script.base_script
{
    public weather_test()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        debugSpeakMsg(self, "Weather Test Script Attached");
        debugSpeakMsg(self, "Commands: clear, mild, heavy, severe");
        setObjVar(self, "weatherIndex", 0);
        return SCRIPT_CONTINUE;
    }
    public int OnHearSpeech(obj_id self, obj_id speaker, String text) throws InterruptedException
    {
        if (text.equals("0") || text.equals("clear"))
        {
            debugSpeakMsg(self, "Clear Weather");
            setWeatherData(0, 0.01f, 0.01f);
            return SCRIPT_CONTINUE;
        }
        if (text.equals("1") || text.equals("mild"))
        {
            debugSpeakMsg(self, "Mild Weather");
            setWeatherData(1, 0.02f, 0.02f);
            return SCRIPT_CONTINUE;
        }
        if (text.equals("2") || text.equals("heavy"))
        {
            debugSpeakMsg(self, "Heavy Weather");
            setWeatherData(2, 0.52f, 0.52f);
            return SCRIPT_CONTINUE;
        }
        if (text.equals("3") || text.equals("severe"))
        {
            debugSpeakMsg(self, "Severe Weather");
            setWeatherData(3, 0.95f, 0.95f);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        debugSpeakMsg(self, "Detaching weather script. Resetting weather.");
        setWeatherData(0, 0.0f, 0.0f);
        return SCRIPT_CONTINUE;
    }
}
