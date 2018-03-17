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
import script.ai.ai_combat;

public class imperial_research_destroy extends script.base_script
{
    public imperial_research_destroy()
    {
    }
    public static final String[] TEMPLATE_LIST = 
    {
        "object/building/poi/generic_flatten_medium.iff",
        "coa3_imperial_research_captain",
        "coa3_imperial_research_captain",
        "coa3_imperial_research_guard",
        "coa3_imperial_research_guard",
        "coa3_imperial_research_guard",
        "coa3_imperial_research_guard",
        "coa3_imperial_research_guard",
        "coa3_imperial_research_guard"
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
            -10.00f,
            0.0f,
            3.00f,
            -90.0f
        },
        
        {
            -10.00f,
            0.0f,
            -3.00f,
            -90.0f
        },
        
        {
            -15.00f,
            0.0f,
            10.00f,
            -90.0f
        },
        
        {
            -15.00f,
            0.0f,
            -10.00f,
            -90.0f
        },
        
        {
            12.00f,
            0.0f,
            15.00f,
            45.0f
        },
        
        {
            12.50f,
            0.0f,
            -15.00f,
            135.0f
        },
        
        {
            -7.00f,
            0.0f,
            20.00f,
            -45.0f
        },
        
        {
            -7.00f,
            0.0f,
            -20.00f,
            -135.0f
        }
    };
    public int OnAttach(obj_id self) throws InterruptedException
    {
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
            setObjVar(newObject, "coa3.imperial.facility", self);
            Vector objectList = getResizeableObjIdArrayObjVar(self, "coa3.imperial.obj_list");
            if (objectList == null)
            {
                objectList = new Vector();
            }
            utils.addElement(objectList, newObject);
            if (objectList.size() > 0)
            {
                setObjVar(self, "coa3.imperial.obj_list", objectList);
            }
            if (ai_lib.isNpc(newObject))
            {
                int count = utils.getIntScriptVar(self, "coa3.imperial.guards");
                count++;
                utils.setScriptVar(self, "coa3.imperial.guards", count);
                obj_id player = getObjIdObjVar(self, "coa3.imperial.playerId");
                setObjVar(newObject, "coa3.imperial.playerId", player);
                if (count < 5)
                {
                    ai_lib.setDefaultCalmBehavior(newObject, ai_lib.BEHAVIOR_SENTINEL);
                }
            }
        }
        objectNum++;
        if (objectNum < TEMPLATE_LIST.length)
        {
            params.put("index", objectNum);
            messageTo(self, "spawnNextObject", params, 0, false);
        }
        else 
        {
            initFacility();
        }
        return SCRIPT_CONTINUE;
    }
    public void initFacility() throws InterruptedException
    {
        obj_id self = getSelf();
        obj_id player = getObjIdObjVar(self, "coa3.imperial.playerId");
        setObjVar(self, "coa3.imperial.numGuards", 8);
        location termLoc = getLocation(self);
        termLoc.x += -8;
        termLoc.z += 3.4;
        obj_id terminal = createObject("object/tangible/furniture/imperial/data_terminal_s1.iff", termLoc);
        setYaw(terminal, -90);
        setObjVar(self, "coa3.imperial.terminalId", terminal);
        setObjVar(terminal, "coa3.imperial.facility", self);
        setObjVar(terminal, "coa3.imperial.playerId", player);
        attachScript(terminal, "theme_park.alderaan.act3.imperial_research_terminal");
    }
    public int guardKilled(obj_id self, dictionary params) throws InterruptedException
    {
        int numGuards = getIntObjVar(self, "coa3.imperial.numGuards");
        numGuards--;
        setObjVar(self, "coa3.imperial.numGuards", numGuards);
        if (numGuards == 0)
        {
            messageTo(self, "cleanup", params, 60 * 15, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int cleanup(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        Vector objectList = getResizeableObjIdArrayObjVar(self, "coa3.imperial.obj_list");
        for (int i = 0; i < objectList.size(); i++)
        {
            if (isIdValid(((obj_id)objectList.get(i))))
            {
                destroyObject(((obj_id)objectList.get(i)));
            }
        }
        obj_id terminal = getObjIdObjVar(self, "coa3.imperial.terminalId");
        messageTo(terminal, "cleanup", null, 0, false);
        return SCRIPT_CONTINUE;
    }
}
