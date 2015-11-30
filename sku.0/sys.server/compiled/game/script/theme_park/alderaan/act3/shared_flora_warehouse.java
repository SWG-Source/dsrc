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

public class shared_flora_warehouse extends script.base_script
{
    public shared_flora_warehouse()
    {
    }
    public static final String[] TEMPLATE_LIST = 
    {
        "object/building/poi/generic_flatten_medium.iff",
        "coa3_warehouse_captain",
        "coa3_warehouse_guard",
        "coa3_warehouse_guard",
        "coa3_warehouse_guard",
        "coa3_warehouse_thug",
        "coa3_warehouse_thug",
        "coa3_warehouse_thug"
    };
    public static final String[] CELL_LIST = 
    {
        "",
        "room3",
        "room3",
        "room2",
        "room1",
        "room1",
        "",
        ""
    };
    public static final float[][] LOCATION_LIST = 
    {
        
        {
            0.00f,
            0.00f,
            0.0f,
            0.0f
        },
        
        {
            -3.19f,
            0.13f,
            -4.60f,
            20.0f
        },
        
        {
            -4.22f,
            0.13f,
            -1.82f,
            160.0f
        },
        
        {
            3.28f,
            0.13f,
            -2.53f,
            -160.0f
        },
        
        {
            4.08f,
            0.13f,
            3.86f,
            0.0f
        },
        
        {
            4.09f,
            0.13f,
            0.75f,
            -40.0f
        },
        
        {
            -1.50f,
            0.13f,
            9.00f,
            0.0f
        },
        
        {
            1.50f,
            0.00f,
            9.00f,
            0.0f
        }
    };
    public int OnAttach(obj_id self) throws InterruptedException
    {
        createTriggerVolume("guards", 20f, true);
        dictionary params = new dictionary();
        params.put("index", 0);
        messageTo(self, "spawnNextObject", params, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int spawnNextObject(obj_id self, dictionary params) throws InterruptedException
    {
        int objectNum = params.getInt("index");
        location loc = getLocation(self);
        if (CELL_LIST[objectNum].equals(""))
        {
            loc.x += LOCATION_LIST[objectNum][0];
            loc.y += LOCATION_LIST[objectNum][1];
            loc.z += LOCATION_LIST[objectNum][2];
        }
        else 
        {
            obj_id spawnCell = getCellId(self, CELL_LIST[objectNum]);
            loc.x = LOCATION_LIST[objectNum][0];
            loc.y = LOCATION_LIST[objectNum][1];
            loc.z = LOCATION_LIST[objectNum][2];
            loc.cell = spawnCell;
        }
        obj_id newObject = create.object(TEMPLATE_LIST[objectNum], loc);
        if (isIdValid(newObject))
        {
            setYaw(newObject, LOCATION_LIST[objectNum][3]);
            setObjVar(newObject, "coa3.shared.warehouse", self);
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
            if (ai_lib.isNpc(newObject))
            {
                int count = utils.getIntScriptVar(self, "coa3.shared.guards");
                count++;
                utils.setScriptVar(self, "coa3.shared.guards", count);
                obj_id player = getObjIdObjVar(self, "coa3.shared.playerId");
                setObjVar(newObject, "coa3.shared.playerId", player);
                ai_lib.setDefaultCalmBehavior(newObject, ai_lib.BEHAVIOR_SENTINEL);
                if (count == 1)
                {
                    setCondition(self, CONDITION_CONVERSABLE);
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
            initWarehouse();
        }
        return SCRIPT_CONTINUE;
    }
    public void initWarehouse() throws InterruptedException
    {
        obj_id self = getSelf();
        setObjVar(self, "coa3.shared.numGuards", 7);
        String planet = getLocation(self).area;
        obj_id room3 = getCellId(self, "room3");
        location crateLoc = new location(-4.5f, 0.13f, -3.23f, planet, room3);
        obj_id crate = createObject("object/tangible/container/drum/poi_prize_box_off.iff", crateLoc);
        setOwner(crate, self);
        setObjVar(crate, "owner", self);
        setObjVar(self, "coa3.shared.crateId", crate);
        int count = rand(1, 3);
        for (int i = 0; i < count; i++)
        {
            obj_id flora = createObject("object/tangible/theme_park/alderaan/act3/alderaan_flora.iff", crate, "");
        }
        location termLoc = new location(-5.45f, 0.13f, -4.43f, planet, room3);
        obj_id terminal = createObject("object/tangible/furniture/imperial/data_terminal_s1.iff", termLoc);
        setYaw(terminal, 90);
        setObjVar(self, "coa3.shared.terminalId", terminal);
        setObjVar(terminal, "coa3.shared.warehouse", self);
        attachScript(terminal, "theme_park.alderaan.act3.shared_flora_terminal");
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        if (!isPlayer(breacher))
        {
            return SCRIPT_CONTINUE;
        }
        if (volumeName.equals("guards"))
        {
            obj_id player = getObjIdObjVar(self, "coa3.shared.playerId");
            if (breacher == player)
            {
                Vector objectList = getResizeableObjIdArrayObjVar(self, "coa3.shared.obj_list");
                for (int i = 0; i < objectList.size(); i++)
                {
                    if (isIdValid(((obj_id)objectList.get(i))) && ai_lib.isNpc(((obj_id)objectList.get(i))))
                    {
                        startCombat(((obj_id)objectList.get(i)), breacher);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int guardKilled(obj_id self, dictionary params) throws InterruptedException
    {
        int numGuards = getIntObjVar(self, "coa3.shared.numGuards");
        numGuards--;
        setObjVar(self, "coa3.shared.numGuards", numGuards);
        if (numGuards == 0)
        {
            obj_id player = getObjIdObjVar(self, "coa3.shared.playerId");
            obj_id crate = getObjIdObjVar(self, "coa3.shared.crateId");
            setOwner(crate, player);
            setObjVar(crate, "owner", player);
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
        Vector objectList = getResizeableObjIdArrayObjVar(self, "coa3.shared.obj_list");
        for (int i = 0; i < objectList.size(); i++)
        {
            if (isIdValid(((obj_id)objectList.get(i))))
            {
                destroyObject(((obj_id)objectList.get(i)));
            }
        }
        return SCRIPT_CONTINUE;
    }
}
