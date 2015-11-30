package script.systems.city;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class senate_cell extends script.base_script
{
    public senate_cell()
    {
    }
    public static final String JEDI_ROOM_PERMISSIONS_TABLE = "datatables/pvp/jedi_enclave_room_permissions.iff";
    public static final String DATA_COLUMN_COMMUNITY_CELLS = "_community_cells";
    public int OnAboutToReceiveItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (checkCellPermission(item, self, false))
        {
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_OVERRIDE;
    }
    public boolean checkCellPermission(obj_id item, obj_id cell, boolean silent) throws InterruptedException
    {
        if (!isIdValid(item))
        {
            return false;
        }
        if (isGod(item))
        {
            sendSystemMessageTestingOnly(item, "god access granted to this area");
            return true;
        }
        if (!isMob(item))
        {
            return true;
        }
        obj_id enclave = getTopMostContainer(cell);
        String alignment = "light";
        String cellName = getCellName(cell);
        String columnName = alignment + "_" + cellName;
        if (isPlayer(item))
        {
            String[] commonsAreaCells = dataTableGetStringColumn(JEDI_ROOM_PERMISSIONS_TABLE, alignment + DATA_COLUMN_COMMUNITY_CELLS);
            if (utils.getElementPositionInArray(commonsAreaCells, cellName) > -1)
            {
                return true;
            }
            else 
            {
                int[] allowedRanks = dataTableGetIntColumnNoDefaults(JEDI_ROOM_PERMISSIONS_TABLE, columnName);
                int rank = getIntObjVar(item, "senator_rank");
                if (utils.getElementPositionInArray(allowedRanks, rank) >= 0)
                {
                    return true;
                }
            }
        }
        return false;
    }
}
