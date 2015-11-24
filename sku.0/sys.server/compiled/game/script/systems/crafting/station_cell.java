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

public class station_cell extends script.base_script
{
    public station_cell()
    {
    }
    public int OnReceivedItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (getGameObjectType(item) == GOT_misc_crafting_station)
        {
            if (utils.hasScriptVar(self, craftinglib.SCRIPTVAR_CELL_STATIONS))
            {
                Vector stations = utils.getResizeableObjIdArrayScriptVar(self, craftinglib.SCRIPTVAR_CELL_STATIONS);
                if (!utils.isElementInArray(stations, item))
                {
                    stations = utils.addElement(stations, item);
                    utils.setScriptVar(self, craftinglib.SCRIPTVAR_CELL_STATIONS, stations);
                }
            }
            else 
            {
                Vector stations = new Vector();
                stations.setSize(0);
                stations = utils.addElement(stations, item);
                utils.setScriptVar(self, craftinglib.SCRIPTVAR_CELL_STATIONS, stations);
            }
            int craftingType = getIntObjVar(item, craftinglib.OBJVAR_CRAFTING_TYPE);
            if (craftingType < 0)
            {
                return SCRIPT_CONTINUE;
            }
            String[] buffNames = craftinglib.getAllStationBuffNames(craftingType);
            if (buffNames == null || buffNames.length <= 0)
            {
                return SCRIPT_CONTINUE;
            }
            obj_id[] cellContents = getContents(self);
            if (cellContents == null || cellContents.length <= 0)
            {
                return SCRIPT_CONTINUE;
            }
            for (int i = 0; i < cellContents.length; ++i)
            {
                if (!isPlayer(cellContents[i]))
                {
                    continue;
                }
                for (int a = 0; a < buffNames.length; a++)
                {
                    buff.applyBuff(cellContents[i], item, buffNames[a]);
                }
            }
        }
        if (!isPlayer(item))
        {
            return SCRIPT_CONTINUE;
        }
        if (!utils.hasScriptVar(self, craftinglib.SCRIPTVAR_CELL_STATIONS))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] stations = utils.getObjIdArrayScriptVar(self, craftinglib.SCRIPTVAR_CELL_STATIONS);
        if (stations == null || stations.length <= 0)
        {
            return SCRIPT_CONTINUE;
        }
        for (int i = 0; i < stations.length; ++i)
        {
            if (!isIdValid(stations[i]) || !exists(stations[i]))
            {
                continue;
            }
            int craftingType = getIntObjVar(stations[i], craftinglib.OBJVAR_CRAFTING_TYPE);
            if (craftingType < 0)
            {
                return SCRIPT_CONTINUE;
            }
            String[] buffNames = craftinglib.getAllStationBuffNames(craftingType);
            if (buffNames == null || buffNames.length <= 0)
            {
                return SCRIPT_CONTINUE;
            }
            for (int a = 0; a < buffNames.length; a++)
            {
                buff.applyBuff(item, stations[i], buffNames[a]);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToLoseItem(obj_id self, obj_id destContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (getGameObjectType(item) == GOT_misc_crafting_station)
        {
            if (utils.hasScriptVar(self, craftinglib.SCRIPTVAR_CELL_STATIONS))
            {
                boolean secondStation = false;
                int craftingTypeItem = getIntObjVar(item, craftinglib.OBJVAR_CRAFTING_TYPE);
                if (craftingTypeItem < 0)
                {
                    return SCRIPT_CONTINUE;
                }
                String[] buffNames = craftinglib.getAllStationBuffNames(craftingTypeItem);
                if (buffNames == null || buffNames.length <= 0)
                {
                    return SCRIPT_CONTINUE;
                }
                Vector stations = utils.getResizeableObjIdArrayScriptVar(self, craftinglib.SCRIPTVAR_CELL_STATIONS);
                obj_id secondStationId = obj_id.NULL_ID;
                if (stations != null && stations.size() > 0)
                {
                    for (int i = 0; i < stations.size(); ++i)
                    {
                        int craftingTypeSearch = getIntObjVar(((obj_id)stations.get(i)), craftinglib.OBJVAR_CRAFTING_TYPE);
                        if (craftingTypeSearch == craftingTypeItem && ((obj_id)stations.get(i)) != item)
                        {
                            secondStation = true;
                            secondStationId = ((obj_id)stations.get(i));
                            break;
                        }
                    }
                }
                stations = utils.removeElement(stations, item);
                utils.setScriptVar(self, craftinglib.SCRIPTVAR_CELL_STATIONS, stations);
                obj_id[] cellContents = getContents(self);
                if (cellContents != null && cellContents.length > 0)
                {
                    for (int i = 0; i < cellContents.length; ++i)
                    {
                        if (!isPlayer(cellContents[i]))
                        {
                            continue;
                        }
                        for (int a = 0; a < buffNames.length; a++)
                        {
                            buff.removeBuff(cellContents[i], buffNames[a]);
                        }
                        if (secondStation && isIdValid(secondStationId) && exists(secondStationId))
                        {
                            for (int a = 0; a < buffNames.length; a++)
                            {
                                buff.applyBuff(cellContents[i], secondStationId, buffNames[a]);
                            }
                        }
                    }
                }
            }
        }
        if (!isPlayer(item))
        {
            return SCRIPT_CONTINUE;
        }
        int[] stationBuffs = buff.getAllBuffsByEffect(item, "station_buff");
        if (stationBuffs == null || stationBuffs.length <= 0)
        {
            return SCRIPT_CONTINUE;
        }
        for (int i = 0; i < stationBuffs.length; ++i)
        {
            buff.removeBuff(item, stationBuffs[i]);
        }
        return SCRIPT_CONTINUE;
    }
}
