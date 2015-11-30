package script.working.wwallace;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.datatable;

public class spawner_placer extends script.base_script
{
    public spawner_placer()
    {
    }
    public int OnSpeaking(obj_id self, String strText) throws InterruptedException
    {
        String[] strCommands = split(strText, ' ');
        if (strCommands[0].equals("spawner"))
        {
            location here = getLocation(self);
            float yaw = getYaw(self);
            obj_id room = here.cell;
            obj_id top = getTopMostContainer(self);
            String roomName = getCellName(room);
            String spawnLoc = strCommands[1] + " @ " + here + " yaw " + yaw;
            String table = "datatables/spawning/dungeon/your_dungeon.tab";
            dictionary dctRow = new dictionary();
            dctRow.put("spawns", strCommands[1]);
            dctRow.put("loc_x", here.x);
            dctRow.put("loc_y", here.y);
            dctRow.put("loc_z", here.z);
            dctRow.put("room", roomName);
            dctRow.put("yaw", yaw);
            datatable.serverDataTableAddRow(table, dctRow);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
}
