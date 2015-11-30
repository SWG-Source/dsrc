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

public class water_pressure_light extends script.base_script
{
    public water_pressure_light()
    {
    }
    public static final string_id ACCESS_DENIED = new string_id("dungeon/death_watch", "access_denied");
    public static final string_id MNU_WATER_VALVE = new string_id("dungeon/death_watch", "mnu_water_valve");
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleLightObjVar", null, 1f, false);
        return SCRIPT_CONTINUE;
    }
    public int handleLightObjVar(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id structure = player_structure.getStructure(self);
        if (!isIdValid(structure))
        {
            return SCRIPT_CONTINUE;
        }
        obj_var_list ovl = getObjVarList(structure, "water_pressure.lights");
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
                    setObjVar(structure, "light1", self);
                }
                else if (pos == 1)
                {
                    setObjVar(structure, "light2", self);
                }
                else if (pos == 2)
                {
                    setObjVar(structure, "light3", self);
                }
                else if (pos == 3)
                {
                    setObjVar(structure, "light4", self);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
