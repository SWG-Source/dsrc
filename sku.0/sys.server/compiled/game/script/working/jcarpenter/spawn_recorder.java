package script.working.jcarpenter;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.datatable;
import java.util.StringTokenizer;

public class spawn_recorder extends script.base_script
{
    public spawn_recorder()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "record_table"))
        {
            sendSystemMessageTestingOnly(self, "Attach the objvar 'record_table' to yourself with the name of the table to which you want to record");
        }
        else 
        {
            String table = getStringObjVar(self, "record_table");
            sendSystemMessageTestingOnly(self, "Recording to table: " + table);
            sendSystemMessageTestingOnly(self, "If this is not correct, change the value of the objvar 'record_table'");
        }
        sendSystemMessageTestingOnly(self, "Say 'mark <creature name>' to place a creature or npc at your current location and heading");
        return SCRIPT_CONTINUE;
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        if (!(toLower(text)).startsWith("mark"))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, "record_table"))
        {
            sendSystemMessageTestingOnly(self, "[ERROR] No datatable specified for recording");
            return SCRIPT_CONTINUE;
        }
        String table = getStringObjVar(self, "record_table");
        if (!(toLower(table)).startsWith("datatables") || !(toLower(table)).endsWith(".tab"))
        {
            sendSystemMessageTestingOnly(self, "[ERROR] Invalid datatable specified for recording");
            return SCRIPT_CONTINUE;
        }
        StringTokenizer st = new StringTokenizer(text);
        String command = st.nextToken();
        String creature = "";
        if (st.hasMoreTokens())
        {
            creature = st.nextToken();
        }
        obj_id obj = self;
        if (command.equalsIgnoreCase("markthis"))
        {
            obj = getLookAtTarget(self);
            creature = getTemplateName(obj);
        }
        location here = getLocation(obj);
        float yaw = getYaw(obj);
        obj_id room = here.cell;
        obj_id top = getTopMostContainer(obj);
        String roomName = getRoomName(top, room);
        sendSystemMessageTestingOnly(self, "Spawning: " + creature + "  Location: " + here + "  Heading: " + yaw);
        dictionary dctRow = new dictionary();
        dctRow.put("spawns", creature);
        dctRow.put("loc_x", here.x);
        dctRow.put("loc_y", here.y);
        dctRow.put("loc_z", here.z);
        dctRow.put("room", roomName);
        dctRow.put("yaw", yaw);
        datatable.serverDataTableAddRow(table, dctRow);
        return SCRIPT_CONTINUE;
    }
    public String getRoomName(obj_id building, obj_id cell) throws InterruptedException
    {
        String[] allCells = getCellNames(building);
        int numberOfCells = allCells.length;
        for (int i = 0; i < numberOfCells; i++)
        {
            String cellName = allCells[i];
            obj_id thisCell = getCellId(building, cellName);
            if (thisCell == cell)
            {
                return cellName;
            }
        }
        return "no_match";
    }
}
