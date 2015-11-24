package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class geiger extends script.base_script
{
    public geiger()
    {
    }
    public static final String OBJVAR_GEIGER_LOCATION = "geiger.location";
    public static final String OBJVAR_GEIGER_FACTOR = "geiger.factor";
    public static final String OBJVAR_GEIGER_OBJECT = "geiger.object";
    public static final String OBJVAR_GEIGER_PLAYER = "geiger.player";
    public static final String OBJVAR_GEIGER_PID = "geiger.pid";
    public static final String SCRIPTVAR_GEIGER_LAST = "geiger.last";
    public static final String GEIGER_TEMPLATE = "object/intangible/data_item/data_geiger_counter.iff";
    public static final String GEIGER_SCRIPT = "item.geiger.base";
    public static boolean hasGeiger(obj_id player) throws InterruptedException
    {
        return hasObjVar(player, OBJVAR_GEIGER_OBJECT);
    }
    public static void resetGeiger(obj_id player) throws InterruptedException
    {
        LOG("geiger", "before creating object");
        if (!hasObjVar(player, OBJVAR_GEIGER_OBJECT))
        {
            LOG("geiger", "creating object");
            obj_id datapad = utils.getDatapad(player);
            LOG("geiger", "datapad = " + datapad);
            obj_id geiger = createObject(GEIGER_TEMPLATE, datapad, "");
            LOG("geiger", "geiger = " + geiger);
            setObjVar(player, OBJVAR_GEIGER_OBJECT, geiger);
            setObjVar(geiger, OBJVAR_GEIGER_PLAYER, player);
            string_id newDevice = new string_id("system_msg", "new_datapad_device");
            sendSystemMessage(player, newDevice);
        }
    }
    public static void setGeiger(obj_id player, location loc) throws InterruptedException
    {
        LOG("geiger", "setGeiger: " + loc);
        setObjVar(player, OBJVAR_GEIGER_LOCATION, loc);
        setObjVar(player, OBJVAR_GEIGER_FACTOR, rand(3, 7));
        LOG("geiger", "before creating object");
        if (!hasObjVar(player, OBJVAR_GEIGER_OBJECT))
        {
            LOG("geiger", "creating object");
            obj_id datapad = utils.getDatapad(player);
            LOG("geiger", "datapad = " + datapad);
            obj_id geiger = createObject(GEIGER_TEMPLATE, datapad, "");
            LOG("geiger", "geiger = " + geiger);
            setObjVar(player, OBJVAR_GEIGER_OBJECT, geiger);
            setObjVar(geiger, OBJVAR_GEIGER_PLAYER, player);
            string_id newDevice = new string_id("system_msg", "new_datapad_device");
            sendSystemMessage(player, newDevice);
        }
    }
    public static void clearGeiger(obj_id player) throws InterruptedException
    {
        removeObjVar(player, OBJVAR_GEIGER_LOCATION);
        removeObjVar(player, OBJVAR_GEIGER_FACTOR);
    }
    public static void removeGeiger(obj_id player) throws InterruptedException
    {
        obj_id datapad = utils.getPlayerDatapad(player);
        obj_id[] contents = getContents(datapad);
        for (int i = 0; i < contents.length; i++)
        {
            if (hasScript(contents[i], GEIGER_SCRIPT))
            {
                destroyObject(contents[i]);
            }
        }
        clearGeiger(player);
    }
    public static float calculateGeigerNumber(obj_id player) throws InterruptedException
    {
        location here = getLocation(player);
        if (getLocationObjVar(player, OBJVAR_GEIGER_LOCATION) != null)
        {
            location loc = getLocationObjVar(player, OBJVAR_GEIGER_LOCATION);
            int targetX = (int)loc.x;
            int targetZ = (int)loc.z;
            int factor = getIntObjVar(player, OBJVAR_GEIGER_FACTOR);
            float distance = (float)Math.sqrt((here.x - targetX) * (here.x - targetX) + (here.z - targetZ) * (here.z - targetZ));
            distance = 5000 - distance;
            float result;
            if (factor % 2 == 1)
            {
                result = distance / factor;
            }
            else 
            {
                result = (float)Math.sqrt(distance / factor) * 100;
            }
            return result;
        }
        else 
        {
            return 0.0f;
        }
    }
    public static void updateGeiger(obj_id player, obj_id geiger) throws InterruptedException
    {
        if (hasObjVar(player, OBJVAR_GEIGER_PID))
        {
            int pid = getIntObjVar(player, OBJVAR_GEIGER_PID);
            if (!hasObjVar(player, OBJVAR_GEIGER_LOCATION))
            {
                setSUIProperty(pid, "%info%", "Text", "\n\nEnemy Threat Level: \n\\#ff0000 -No tracking device detected-");
                flushSUIPage(pid);
                return;
            }
            location targetLoc = getLocationObjVar(player, OBJVAR_GEIGER_LOCATION);
            location here = getLocation(player);
            if ((targetLoc != null && !here.area.equals(targetLoc.area)) || targetLoc == null)
            {
                setSUIProperty(pid, "%info%", "Text", "\\#ff0000 \n\nNo Signal");
                flushSUIPage(pid);
                return;
            }
            location signalCheck = getLocation(player);
            int targetX = (int)targetLoc.x;
            int targetZ = (int)targetLoc.z;
            float distance = (float)Math.sqrt((here.x - targetX) * (here.x - targetX) + (here.z - targetZ) * (here.z - targetZ));
            float varience = rand(500, 1000);
            if (distance > 4500.0f + varience)
            {
                setSUIProperty(pid, "%info%", "Text", "\\#ff0000 \n\nWeak Signal");
                flushSUIPage(pid);
                return;
            }
            if (distance <= 64.0f)
            {
                setSUIProperty(pid, "%info%", "Text", "\\#0000ff \n\nSignal Peak. Target within 64 meters");
                flushSUIPage(pid);
                return;
            }
            float last = utils.getFloatScriptVar(player, SCRIPTVAR_GEIGER_LAST);
            float val = calculateGeigerNumber(player);
            utils.setScriptVar(player, SCRIPTVAR_GEIGER_LAST, val);
            float value = (val - last);
            if (value == 0.0f)
            {
                setSUIProperty(pid, "%info%", "Text", "\n\nEnemy Threat Level: " + val + "\nDelta: 0.  You are not moving.");
                flushSUIPage(pid);
                return;
            }
            else 
            {
                setSUIProperty(pid, "%info%", "Text", "\n\nEnemy Threat Level: " + val + "\nDelta: " + (value > 0.0f ? "\\#00ff00 Moving closer \n " : "\\#ff0000 Moving away \n ") + (value > 9999.0f ? "-Out of range-" : (value + "")));
                flushSUIPage(pid);
            }
        }
    }
}
