package script.working.toad;

import script.dictionary;
import script.library.datatable;
import script.location;
import script.obj_id;

public class dungeon_logging extends script.base_script
{
    public dungeon_logging()
    {
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        if (hasObjVar(self, "dungeonOff"))
        {
            return SCRIPT_CONTINUE;
        }
        location here = getLocation(self);
        float yaw = getYaw(self);
        obj_id room = here.cell;
        obj_id top = getTopMostContainer(self);
        String roomName = getRoomName(top, room);
        String spawnLoc = text + " @ " + here + " yaw " + yaw;
        String table = "datatables/spawning/dungeon/naboo/robot_dungeon.tab";
        dictionary dctRow = new dictionary();
        dctRow.put("spawns", text);
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
        for (String cellName : allCells) {
            obj_id thisCell = getCellId(building, cellName);
            if (thisCell == cell) {
                return cellName;
            }
        }
        return "no_match";
    }
}
