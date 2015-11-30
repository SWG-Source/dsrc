package script.terminal;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.city;
import script.library.player_structure;

public class terminal_city_mission extends script.base_script
{
    public terminal_city_mission()
    {
    }
    public static final string_id SID_MT_REMOVE = new string_id("city/city", "mt_remove");
    public static final string_id SID_MT_REMOVED = new string_id("city/city", "mt_removed");
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        city.validateSpecialStructure(self);
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        city.removeMissionTerminal(self);
        return SCRIPT_CONTINUE;
    }
    public int requestDestroy(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int city_id = city.checkMayorCity(player, false);
        if (city_id == 0)
        {
            return SCRIPT_CONTINUE;
        }
        mi.addRootMenu(menu_info_types.SERVER_MENU1, SID_MT_REMOVE);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        int city_id = city.checkMayorCity(player, false);
        if (city_id == 0)
        {
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.SERVER_MENU1)
        {
            sendSystemMessage(player, SID_MT_REMOVED);
            destroyObject(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleUnloadedCityStructureReportData(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        obj_id city_terminal = params.getObjId("city_terminal");
        int city_id = params.getInt("city_id");
        if (!isIdValid(player) || !isIdValid(city_terminal))
        {
            return SCRIPT_CONTINUE;
        }
        if (!city.isNormalStructure(city_id, self))
        {
            location structureLocation = getLocation(self);
            String structureName = localize(getNameStringId(self));
            dictionary dict = new dictionary();
            dict.put("player", player);
            dict.put("city_id", city_id);
            dict.put("city_terminal", city_terminal);
            dict.put("unloadedStructureId", self);
            dict.put("unloadedStructureName", structureName);
            dict.put("unloadedStructureLocation", structureLocation);
            messageTo(city_terminal, "RecievedUnloadedStructureResponse", dict, 0.0f, false);
        }
        else if (player_structure.isCivic(self))
        {
            location structureLocation = getLocation(self);
            float cond = ((float)player_structure.getStructureCondition(self)) / ((float)player_structure.getMaxCondition(self));
            int outcond = (int)(cond * 100);
            String structureName = getEncodedName(self) + " (Condition : " + outcond + "%)";
            dictionary dict = new dictionary();
            dict.put("player", player);
            dict.put("city_id", city_id);
            dict.put("city_terminal", city_terminal);
            dict.put("unloadedStructureId", self);
            dict.put("unloadedStructureName", structureName);
            dict.put("unloadedStructureLocation", structureLocation);
            messageTo(city_terminal, "RecievedUnloadedStructureResponse", dict, 0.0f, false);
        }
        else 
        {
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
}
