package script.structure.municipal;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.structure;
import script.library.player_structure;
import script.library.utils;

public class cloning_facility extends script.structure.municipal.cloning_base
{
    public cloning_facility()
    {
    }
    public static final String SCRIPT_CLONING_FACILITY = "structure.municipal.cloning_facility";
    public static final String DATATABLE_TERMINAL_LIST = "datatables/structure/municipal/cloning_facility_terminal.iff";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleTerminalSpawning", null, 5f, false);
        return super.OnInitialize(self);
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        obj_id planet = getPlanetByName(getCurrentSceneName());
        if (isIdValid(planet))
        {
            dictionary params = new dictionary();
            params.put("id", self);
            messageTo(planet, "unregisterCloningFacility", params, 1.0f, false);
        }
        return super.OnDetach(self);
    }
    public int handleTerminalSpawning(obj_id self, dictionary params) throws InterruptedException
    {
        createCloningDroid(self);
        if (!player_structure.isCivic(self))
        {
            structure.createStructureTerminals(self, SCRIPT_CLONING_FACILITY, DATATABLE_TERMINAL_LIST);
        }
        return SCRIPT_CONTINUE;
    }
    public void createCloningDroid(obj_id facility) throws InterruptedException
    {
        if (!isIdValid(facility))
        {
            return;
        }
        String facilityTemplate = getTemplateName(facility);
        float fYaw = getYaw(facility);
        String sceneName = getCurrentSceneName();
        int numRows = dataTableGetNumRows(DATATABLE_TERMINAL_LIST);
        Vector terminals = new Vector();
        terminals.setSize(0);
        for (int i = 0; i < numRows; i++)
        {
            String buildingTemplate = dataTableGetString(DATATABLE_TERMINAL_LIST, i, structure.DATATABLE_COL_STRUCTURE);
            if (buildingTemplate.equals(facilityTemplate))
            {
                dictionary item = dataTableGetRow(DATATABLE_TERMINAL_LIST, i);
                String droid_name = item.getString("DROID");
                if (droid_name == null || droid_name.equals(""))
                {
                    continue;
                }
                float x = item.getFloat(structure.DATATABLE_COL_X);
                float y = item.getFloat(structure.DATATABLE_COL_Y);
                float z = item.getFloat(structure.DATATABLE_COL_Z);
                float relativeHeading = item.getFloat(structure.DATATABLE_COL_HEADING);
                String cell_name = item.getString(structure.DATATABLE_COL_CELL);
                obj_id cell_id = obj_id.NULL_ID;
                if (!cell_name.equals(structure.WORLD_DELTA))
                {
                    cell_id = getCellId(facility, cell_name);
                }
                if (cell_id != null)
                {
                    obj_id droid_id = obj_id.NULL_ID;
                    location spot = new location(x, y, z, sceneName, cell_id);
                    float heading = relativeHeading;
                    if (cell_id == obj_id.NULL_ID)
                    {
                        location fLoc = getLocation(facility);
                        spot = new location(fLoc.x + x, fLoc.y + y, fLoc.z + z, sceneName, cell_id);
                        if (fYaw != 0)
                        {
                            spot = utils.rotatePointXZ(fLoc, spot, fYaw);
                            heading -= (90 - fYaw);
                        }
                    }
                    droid_id = create.object(droid_name, spot);
                    if ((droid_id != null) && (droid_id != obj_id.NULL_ID))
                    {
                        setYaw(droid_id, heading);
                    }
                }
            }
        }
    }
}
