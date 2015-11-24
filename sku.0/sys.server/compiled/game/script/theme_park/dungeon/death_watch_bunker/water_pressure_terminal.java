package script.theme_park.dungeon.death_watch_bunker;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.player_structure;
import script.library.utils;

public class water_pressure_terminal extends script.base_script
{
    public water_pressure_terminal()
    {
    }
    public static final string_id ACCESS_DENIED = new string_id("dungeon/death_watch", "access_denied");
    public static final string_id MNU_WATER_VALVE = new string_id("dungeon/death_watch", "mnu_water_valve");
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleTerminalObjVar", null, 1f, false);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (isDead(player) || isIncapacitated(player))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id structure = player_structure.getStructure(player);
        if (!isIdValid(structure))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(player, "death_watch.water_pressure"))
        {
            sendSystemMessage(player, ACCESS_DENIED);
            return SCRIPT_CONTINUE;
        }
        obj_id newPlayer = getObjIdObjVar(structure, "death_watch.water_pressure_mission");
        if (player != newPlayer)
        {
            sendSystemMessage(player, ACCESS_DENIED);
            removeObjVar(player, "death_watch.water_pressure");
            setObjVar(player, "death_watch.water_pressure_failed", 1);
            return SCRIPT_CONTINUE;
        }
        int mnuControl = mi.addRootMenu(menu_info_types.ITEM_USE, MNU_WATER_VALVE);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (isDead(player) || isIncapacitated(player))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id structure = player_structure.getStructure(player);
        if (!isIdValid(structure))
        {
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.ITEM_USE)
        {
            dictionary params = new dictionary();
            params.put("player", player);
            params.put("terminal", self);
            messageTo(structure, "handleTerminalSwitch", params, 1f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleTerminalObjVar(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id structure = player_structure.getStructure(self);
        if (!isIdValid(structure))
        {
            return SCRIPT_CONTINUE;
        }
        obj_var_list ovl = getObjVarList(structure, "water_pressure.terminals");
        if (ovl == null)
        {
            return SCRIPT_CONTINUE;
        }
        int numTypes = ovl.getNumItems();
        for (int i = 0; i < numTypes; i++)
        {
            obj_var ov = ovl.getObjVar(i);
            String ovName = ov.getName();
            obj_id[] data = ov.getObjIdArrayData();
            int pos = utils.getElementPositionInArray(data, self);
            if (pos > -1)
            {
                if (pos == 0)
                {
                    setObjVar(self, "valve1", 1);
                }
                else if (pos == 1)
                {
                    setObjVar(self, "valve2", 1);
                }
                else if (pos == 2)
                {
                    setObjVar(self, "valve3", 1);
                }
                else if (pos == 3)
                {
                    setObjVar(self, "valve4", 1);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
