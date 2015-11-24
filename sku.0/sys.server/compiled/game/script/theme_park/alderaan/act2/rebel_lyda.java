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

public class rebel_lyda extends script.base_script
{
    public rebel_lyda()
    {
    }
    public static final String[] TEMPLATE_LIST = 
    {
        "object/building/poi/generic_flatten_small.iff",
        "object/static/structure/general/camp_tent_s01.iff",
        "object/static/structure/general/camp_tent_s01.iff",
        "object/static/structure/general/campfire_fresh.iff",
        "coa2_lyda_thugs",
        "coa2_lyda_thugs",
        "coa2_lyda_thugs",
        "coa2_lyda_thugs",
        "coa2_lyda_thugs"
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
            205.0f
        },
        
        {
            1.0f,
            0.0f,
            6.0f,
            230.0f
        },
        
        {
            2.0f,
            0.0f,
            2.0f,
            0.0f
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
        setName(self, "Lyda Skims");
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
            setObjVar(newObject, "coa2.rebel.lyda", self);
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
        setObjVar(self, "coa2.rebel.numThugs", 5);
    }
    public int thugKilled(obj_id self, dictionary params) throws InterruptedException
    {
        int numThugs = getIntObjVar(self, "coa2.rebel.numThugs");
        numThugs--;
        setObjVar(self, "coa2.rebel.numThugs", numThugs);
        if (numThugs == 0)
        {
            obj_id player = getObjIdObjVar(self, "coa2.rebel.playerId");
            if (isIdValid(player))
            {
                setObjVar(player, "coa2.progress", 8);
                messageTo(player, "createReturnMission", null, 1, false);
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
}
