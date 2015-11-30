package script.theme_park.alderaan.act2;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.create;
import script.library.utils;
import script.library.chat;

public class imperial_relay_station extends script.base_script
{
    public imperial_relay_station()
    {
    }
    public static final String[] TEMPLATE_LIST = 
    {
        "object/building/poi/generic_flatten_medium.iff",
        "object/static/structure/corellia/corl_power_transformer_s02.iff",
        "object/static/structure/corellia/corl_power_connector.iff",
        "object/static/structure/tatooine/antenna_tatt_style_1.iff",
        "coa2_relay_captain",
        "coa2_relay_guard",
        "coa2_relay_guard",
        "coa2_relay_guard",
        "coa2_relay_guard",
        "coa2_relay_guard",
        "coa2_relay_guard"
    };
    public static final float[][] LOCATION_LIST = 
    {
        
        {
            0.00f,
            0.0f,
            0.0f,
            0.0f
        },
        
        {
            9.87f,
            0.0f,
            0.0f,
            0.0f
        },
        
        {
            5.90f,
            0.0f,
            0.0f,
            0.0f
        },
        
        {
            9.86f,
            0.0f,
            0.0f,
            0.0f
        },
        
        {
            1.77f,
            0.0f,
            -3.70f,
            180.0f
        },
        
        {
            -2.00f,
            0.0f,
            -3.57f,
            180.0f
        },
        
        {
            9.29f,
            0.0f,
            -3.46f,
            180.0f
        },
        
        {
            -14.60f,
            0.0f,
            -8.96f,
            180.0f
        },
        
        {
            -14.57f,
            0.0f,
            6.27f,
            180.0f
        },
        
        {
            -5.04f,
            0.0f,
            -12.26f,
            180.0f
        },
        
        {
            2.26f,
            0.0f,
            7.65f,
            180.0f
        }
    };
    public int OnAttach(obj_id self) throws InterruptedException
    {
        String planet = getCurrentSceneName();
        obj_id cell = getCellId(self, "spawn");
        attachScript(cell, "theme_park.alderaan.act2.relay_station_access");
        location loc = new location(0.0f, 0.0f, 0.0f, planet, cell);
        obj_id terminal = create.staticObject("object/tangible/theme_park/alderaan/act2/relay_station_terminal.iff", loc);
        setYaw(terminal, 180f);
        attachScript(terminal, "theme_park.alderaan.act2.relay_station_terminal");
        permissionsMakePrivate(cell);
        dictionary params = new dictionary();
        params.put("index", 0);
        messageTo(self, "spawnNextObject", params, 2, false);
        return SCRIPT_CONTINUE;
    }
    public int spawnNextObject(obj_id self, dictionary params) throws InterruptedException
    {
        int objectNum = params.getInt("index");
        location loc = getLocation(self);
        loc.x += LOCATION_LIST[objectNum][0];
        loc.y += LOCATION_LIST[objectNum][1];
        loc.z += LOCATION_LIST[objectNum][2];
        obj_id newObject = create.object(TEMPLATE_LIST[objectNum], loc);
        if (isIdValid(newObject))
        {
            setYaw(newObject, LOCATION_LIST[objectNum][3]);
            setObjVar(newObject, "coa2.rebel.relay_station", self);
            Vector objectList = getResizeableObjIdArrayObjVar(self, "coa2.rebel.obj_list");
            if (objectList == null)
            {
                objectList = new Vector();
            }
            utils.addElement(objectList, newObject);
            if (objectList.size() > 0)
            {
                setObjVar(self, "coa2.rebel.obj_list", objectList);
            }
            if (ai_lib.isNpc(newObject))
            {
                int count = utils.getIntScriptVar(self, "coa2.rebel.guards");
                count++;
                utils.setScriptVar(self, "coa2.rebel.guards", count);
                if (count <= 3)
                {
                    obj_id player = getObjIdObjVar(self, "coa2.rebel.playerId");
                    setObjVar(newObject, "coa2.rebel.playerId", player);
                    ai_lib.setDefaultCalmBehavior(newObject, ai_lib.BEHAVIOR_SENTINEL);
                }
            }
        }
        objectNum++;
        if (objectNum < TEMPLATE_LIST.length)
        {
            params.put("index", objectNum);
            messageTo(self, "spawnNextObject", params, 2, false);
        }
        else 
        {
            initCamp();
        }
        return SCRIPT_CONTINUE;
    }
    public void initCamp() throws InterruptedException
    {
        obj_id self = getSelf();
        setObjVar(self, "coa2.rebel.numGuards", 6);
    }
    public int guardKilled(obj_id self, dictionary params) throws InterruptedException
    {
        int numGuards = getIntObjVar(self, "coa2.rebel.numGuards");
        numGuards--;
        setObjVar(self, "coa2.rebel.numGuards", numGuards);
        if (numGuards == 0)
        {
            obj_id player = getObjIdObjVar(self, "coa2.rebel.playerId");
            if (isIdValid(player))
            {
            }
            messageTo(self, "cleanup", null, 15 * 60, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int cleanup(obj_id self, dictionary params) throws InterruptedException
    {
        Vector objectList = getResizeableObjIdArrayObjVar(self, "coa2.rebel.obj_list");
        for (int i = 0; i < objectList.size(); i++)
        {
            if (isIdValid(((obj_id)objectList.get(i))))
            {
                destroyObject(((obj_id)objectList.get(i)));
            }
        }
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int unlockStation(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id cell = getCellId(self, "spawn");
        permissionsMakePublic(cell);
        return SCRIPT_CONTINUE;
    }
}
