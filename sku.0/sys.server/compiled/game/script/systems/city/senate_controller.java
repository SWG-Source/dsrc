package script.systems.city;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class senate_controller extends script.base_script
{
    public senate_controller()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        makeAllCellsPublic(self);
        setCellScripts(self);
        return SCRIPT_CONTINUE;
    }
    public void makeAllCellsPublic(obj_id senate) throws InterruptedException
    {
        String[] senateRooms = getCellNames(senate);
        obj_id cellId = null;
        for (int i = 0; i < senateRooms.length; i++)
        {
            cellId = getCellId(senate, senateRooms[i]);
            if (!isIdValid(cellId))
            {
                continue;
            }
            permissionsMakePublic(cellId);
        }
    }
    public void setCellScripts(obj_id enclave) throws InterruptedException
    {
        String[] enclaveRooms = getCellNames(enclave);
        obj_id cellId = null;
        for (int i = 0; i < enclaveRooms.length; i++)
        {
            cellId = getCellId(enclave, enclaveRooms[i]);
            if (!isIdValid(cellId))
            {
                continue;
            }
            attachScript(cellId, "systems.city.senate_cell");
        }
        return;
    }
}
