package script.planet;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class planet_weather extends script.base_script
{
    public planet_weather()
    {
    }
    public static final String PARAM_WEATHER_DESIRED = "desired";
    public static final String PARAM_WEATHER_CURRENT = "current";
    public static final int WEATHER_GOOD = 0;
    public static final int WEATHER_MILD = 1;
    public static final int WEATHER_SEVERE = 2;
    public static final int WEATHER_EXTREME = 3;
    public static final int[] WEATHER_CHANCE = 
    {
        70,
        15,
        10,
        5
    };
    public static final int[][] WEATHER_DURATION_LIMITS = 
    {
        
        {
            1200,
            2400
        },
        
        {
            240,
            480
        },
        
        {
            120,
            300
        },
        
        {
            120,
            300
        }
    };
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (isInWorld(self))
        {
            debugServerConsoleMsg(null, "starting weather from OnAttach()");
            startWeather(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        debugServerConsoleMsg(null, "starting weather from OnInitialize()");
        startWeather(self);
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        setWeatherData(WEATHER_GOOD, 0.0f, 0.0f);
        return SCRIPT_CONTINUE;
    }
    public void startWeather(obj_id self) throws InterruptedException
    {
        int desiredWeather = WEATHER_GOOD;
        int currentWeather = WEATHER_GOOD;
        float windVectorX = rand(1.0f, 2.0f);
        float windVectorZ = rand(1.0f, 2.0f);
        setWeatherData(currentWeather, windVectorX, windVectorZ);
        int weatherDuration = rand(WEATHER_DURATION_LIMITS[currentWeather][0], WEATHER_DURATION_LIMITS[currentWeather][1]);
        debugServerConsoleMsg(null, "planet_weather script start setting weather to " + currentWeather + "(" + desiredWeather + ") for " + weatherDuration + " seconds");
        dictionary params = new dictionary();
        params.put(PARAM_WEATHER_DESIRED, desiredWeather);
        params.put(PARAM_WEATHER_CURRENT, currentWeather);
        messageTo(self, "updateWeather", params, weatherDuration, false);
    }
    public int updateWeather(obj_id self, dictionary params) throws InterruptedException
    {
        int desiredWeather = params.getInt(PARAM_WEATHER_DESIRED);
        int currentWeather = params.getInt(PARAM_WEATHER_CURRENT);
        debugServerConsoleMsg(null, "planet_weather script got weather " + currentWeather + "(" + desiredWeather + ")");
        if (currentWeather != desiredWeather)
        {
            if (currentWeather > desiredWeather)
            {
                --currentWeather;
            }
            else 
            {
                ++currentWeather;
            }
        }
        else if (desiredWeather != 0)
        {
            desiredWeather = 0;
            --currentWeather;
        }
        else 
        {
            int r = rand(1, 100);
            for (int i = 0; i <= WEATHER_EXTREME; ++i)
            {
                if (r <= WEATHER_CHANCE[i])
                {
                    desiredWeather = i;
                    break;
                }
                r -= WEATHER_CHANCE[i];
            }
            if (desiredWeather != 0)
            {
                ++currentWeather;
            }
        }
        if (currentWeather < 0)
        {
            currentWeather = 0;
        }
        else if (currentWeather > WEATHER_EXTREME)
        {
            currentWeather = WEATHER_EXTREME;
        }
        if (desiredWeather < 0)
        {
            desiredWeather = 0;
        }
        else if (desiredWeather > WEATHER_EXTREME)
        {
            desiredWeather = WEATHER_EXTREME;
        }
        int weatherDuration = rand(WEATHER_DURATION_LIMITS[currentWeather][0], WEATHER_DURATION_LIMITS[currentWeather][1]);
        if (desiredWeather != currentWeather)
        {
            weatherDuration /= 2;
        }
        float windVectorX = rand(1.0f, 2.0f);
        float windVectorZ = rand(1.0f, 2.0f);
        setWeatherData(currentWeather, windVectorX, windVectorZ);
        debugServerConsoleMsg(null, "planet_weather script setting weather to " + currentWeather + "(" + desiredWeather + ") for " + weatherDuration + " seconds");
        dictionary newParams = new dictionary();
        newParams.put(PARAM_WEATHER_DESIRED, desiredWeather);
        newParams.put(PARAM_WEATHER_CURRENT, currentWeather);
        messageTo(self, "updateWeather", newParams, weatherDuration, false);
        return SCRIPT_CONTINUE;
    }
}
