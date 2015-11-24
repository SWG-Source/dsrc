package script.theme_park.alderaan.act3;

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

public class shared_caravan_leader extends script.base_script
{
    public shared_caravan_leader()
    {
    }
    public static final String[] TEMPLATE_LIST = 
    {
        "object/building/poi/generic_flatten_medium.iff",
        "object/static/vehicle/e3/landspeeder.iff",
        "object/static/vehicle/e3/landspeeder.iff",
        "coa3_caravan_thug",
        "coa3_caravan_thug",
        "coa3_caravan_thug",
        "coa3_caravan_thug",
        "coa3_caravan_thug"
    };
    public static final float[][] LOCATION_LIST = 
    {
        
        {
            0.0f,
            0.0f,
            0.0f,
            0.0f
        },
        
        {
            8.0f,
            0.0f,
            2.0f,
            25.0f
        },
        
        {
            1.0f,
            0.0f,
            6.0f,
            50.0f
        },
        
        {
            10.0f,
            0.0f,
            0.0f,
            0.0f
        },
        
        {
            -5.0f,
            0.0f,
            10.0f,
            0.0f
        },
        
        {
            -5.0f,
            0.0f,
            -5.0f,
            0.0f
        },
        
        {
            10.0f,
            0.0f,
            -5.0f,
            0.0f
        },
        
        {
            -8.0f,
            0.0f,
            -2.0f,
            0.0f
        }
    };
    public int OnAttach(obj_id self) throws InterruptedException
    {
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        setCondition(self, CONDITION_CONVERSABLE);
        dictionary params = new dictionary();
        params.put("index", 0);
        messageTo(self, "spawnNextObject", params, 1, false);
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
            setObjVar(newObject, "coa3.shared.caravan_leader", self);
            Vector objectList = getResizeableObjIdArrayObjVar(self, "coa3.shared.obj_list");
            if (objectList == null)
            {
                objectList = new Vector();
            }
            utils.addElement(objectList, newObject);
            if (objectList.size() > 0)
            {
                setObjVar(self, "coa3.shared.obj_list", objectList);
            }
        }
        objectNum++;
        if (objectNum < TEMPLATE_LIST.length)
        {
            params.put("index", objectNum);
            messageTo(self, "spawnNextObject", params, 0, false);
        }
        return SCRIPT_CONTINUE;
    }
}
