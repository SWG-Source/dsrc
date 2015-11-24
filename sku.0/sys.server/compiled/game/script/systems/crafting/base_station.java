package script.systems.crafting;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.buff;
import script.library.craftinglib;
import script.library.utils;

public class base_station extends script.base_script
{
    public base_station()
    {
    }
    public int OnAboutToReceiveItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (transferer == obj_id.NULL_ID || transferer == srcContainer)
        {
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_OVERRIDE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (utils.isInHouseCellSpace(self))
        {
            mi.addRootMenu(menu_info_types.CRAFT_HOPPER_INPUT, null);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "crafting.stationMod"))
        {
            names[idx] = "stationmod";
            float attrib = getFloatObjVar(self, "crafting.stationMod");
            attribs[idx] = "" + attrib;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (isInWorldCell(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id cell = getContainedBy(self);
        if (!isIdValid(cell) || !exists(cell))
        {
            return SCRIPT_CONTINUE;
        }
        if (isPlayer(cell))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.isNestedWithinAPlayer(cell))
        {
            return SCRIPT_CONTINUE;
        }
        if (isMob(cell))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasScript(cell, "systems.crafting.station_cell"))
        {
            attachScript(cell, "systems.crafting.station_cell");
        }
        if (utils.hasScriptVar(cell, craftinglib.SCRIPTVAR_CELL_STATIONS))
        {
            Vector stations = utils.getResizeableObjIdArrayScriptVar(cell, craftinglib.SCRIPTVAR_CELL_STATIONS);
            if (!utils.isElementInArray(stations, self))
            {
                stations = utils.addElement(stations, self);
                utils.setScriptVar(cell, craftinglib.SCRIPTVAR_CELL_STATIONS, stations);
            }
        }
        else 
        {
            Vector stations = new Vector();
            stations.setSize(0);
            stations = utils.addElement(stations, self);
            utils.setScriptVar(cell, craftinglib.SCRIPTVAR_CELL_STATIONS, stations);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTransferred(obj_id self, obj_id sourceContainer, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        if (isInWorldCell(self) || !isIdValid(destContainer))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id cell = getContainedBy(self);
        if (!isIdValid(cell) || !exists(cell))
        {
            return SCRIPT_CONTINUE;
        }
        if (isPlayer(cell))
        {
            return SCRIPT_CONTINUE;
        }
        if (isMob(cell))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.isNestedWithinAPlayer(cell))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasScript(cell, "systems.crafting.station_cell"))
        {
            attachScript(cell, "systems.crafting.station_cell");
        }
        if (utils.hasScriptVar(cell, craftinglib.SCRIPTVAR_CELL_STATIONS))
        {
            Vector stations = utils.getResizeableObjIdArrayScriptVar(cell, craftinglib.SCRIPTVAR_CELL_STATIONS);
            if (!utils.isElementInArray(stations, self))
            {
                stations = utils.addElement(stations, self);
                utils.setScriptVar(cell, craftinglib.SCRIPTVAR_CELL_STATIONS, stations);
            }
        }
        else 
        {
            Vector stations = new Vector();
            stations.setSize(0);
            stations = utils.addElement(stations, self);
            utils.setScriptVar(cell, craftinglib.SCRIPTVAR_CELL_STATIONS, stations);
        }
        if (isPlayer(transferer))
        {
            int craftingType = getIntObjVar(self, craftinglib.OBJVAR_CRAFTING_TYPE);
            String[] buffNames = craftinglib.getAllStationBuffNames(craftingType);
            if (buffNames == null || buffNames.length <= 0)
            {
                return SCRIPT_CONTINUE;
            }
            for (int a = 0; a < buffNames.length; a++)
            {
                buff.applyBuff(transferer, self, buffNames[a]);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
