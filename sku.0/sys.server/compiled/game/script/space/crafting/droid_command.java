package script.space.crafting;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.sui;
import script.library.space_crafting;
import script.library.space_utils;
import script.library.utils;
import script.library.space_pilot_command;

public class droid_command extends script.base_script
{
    public droid_command()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "programSize"))
        {
            setObjVar(self, "programSize", 1);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id objPlayer, menu_info menuInfo) throws InterruptedException
    {
        menuInfo.addRootMenu(menu_info_types.MEMORY_CHIP_ANALYZE, new string_id("space/space_interaction", "memory_chip_analyze"));
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id objPlayer, int item) throws InterruptedException
    {
        if (item == menu_info_types.MEMORY_CHIP_ANALYZE)
        {
            String strDroidCommand = getStringObjVar(self, "strDroidCommand");
            if (strDroidCommand == null)
            {
                return SCRIPT_CONTINUE;
            }
            string_id strSpam = new string_id("space/droid_commands", strDroidCommand + "_description");
            sui.msgbox(objPlayer, strSpam);
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
        if (hasObjVar(self, "programSize"))
        {
            names[idx] = "droid_command_program_size";
            int programSize = getIntObjVar(self, "programSize");
            attribs[idx] = " " + programSize;
            idx++;
        }
        if (hasObjVar(self, "strDroidCommand"))
        {
            String strDroidCommand = getStringObjVar(self, "strDroidCommand");
            names[idx] = "droid_command_name";
            attribs[idx] = utils.packStringId(new string_id("space/droid_commands", strDroidCommand));
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id dataPad = getContainedBy(self);
        debugServerConsoleMsg(null, "DROID_COMMAND.OnDestroy  ***   ENTERED FUNCTION. obj_id passed in for self was: " + self);
        debugServerConsoleMsg(null, "DROID_COMMAND.OnDestroy  ***   obj_id for datapad via getContainedBy(self) was: " + dataPad);
        space_pilot_command.updateControlDevice(self, dataPad);
        return SCRIPT_CONTINUE;
    }
}
