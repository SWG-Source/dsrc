package script.systems.crafting;

import script.library.buff;
import script.library.craftinglib;
import script.library.utils;
import script.obj_id;

import java.util.Vector;

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
            for (obj_id cellContent : cellContents) {
                if (!isPlayer(cellContent)) {
                    continue;
                }
                for (String buffName : buffNames) {
                    buff.applyBuff(cellContent, item, buffName);
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
        for (obj_id station : stations) {
            if (!isIdValid(station) || !exists(station)) {
                continue;
            }
            int craftingType = getIntObjVar(station, craftinglib.OBJVAR_CRAFTING_TYPE);
            if (craftingType < 0) {
                return SCRIPT_CONTINUE;
            }
            String[] buffNames = craftinglib.getAllStationBuffNames(craftingType);
            if (buffNames == null || buffNames.length <= 0) {
                return SCRIPT_CONTINUE;
            }
            for (String buffName : buffNames) {
                buff.applyBuff(item, station, buffName);
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
                    for (Object station : stations) {
                        int craftingTypeSearch = getIntObjVar(((obj_id) station), craftinglib.OBJVAR_CRAFTING_TYPE);
                        if (craftingTypeSearch == craftingTypeItem && ((obj_id) station) != item) {
                            secondStation = true;
                            secondStationId = ((obj_id) station);
                            break;
                        }
                    }
                }
                stations = utils.removeElement(stations, item);
                utils.setScriptVar(self, craftinglib.SCRIPTVAR_CELL_STATIONS, stations);
                obj_id[] cellContents = getContents(self);
                if (cellContents != null && cellContents.length > 0)
                {
                    for (obj_id cellContent : cellContents) {
                        if (!isPlayer(cellContent)) {
                            continue;
                        }
                        for (String buffName1 : buffNames) {
                            buff.removeBuff(cellContent, buffName1);
                        }
                        if (secondStation && isIdValid(secondStationId) && exists(secondStationId)) {
                            for (String buffName : buffNames) {
                                buff.applyBuff(cellContent, secondStationId, buffName);
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
        for (int stationBuff : stationBuffs) {
            buff.removeBuff(item, stationBuff);
        }
        return SCRIPT_CONTINUE;
    }
}
