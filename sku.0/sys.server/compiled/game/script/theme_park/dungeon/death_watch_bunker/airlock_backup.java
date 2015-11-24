package script.theme_park.dungeon.death_watch_bunker;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.chat;
import script.library.create;

public class airlock_backup extends script.base_script
{
    public airlock_backup()
    {
    }
    public static final String TBL_BACKUP = "datatables/dungeon/death_watch/airlock_backup.iff";
    public static final String STF_FILE = "dungeon/death_watch";
    public int OnCreatureDamaged(obj_id self, obj_id attacker, obj_id wpn, int[] damage) throws InterruptedException
    {
        if (!hasObjVar(self, "death_watch.backup"))
        {
            chat.chat(self, new string_id(STF_FILE, "airlock_backup"));
            setObjVar(self, "death_watch.backup", 1);
            messageTo(self, "Backup", null, 5f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int Backup(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id structure = getTopMostContainer(self);
        int defenderCreatures = dataTableGetNumRows(TBL_BACKUP);
        int x = 0;
        while (x < 3)
        {
            String spawn = dataTableGetString(TBL_BACKUP, x, "spawns");
            float xCoord = dataTableGetFloat(TBL_BACKUP, x, "loc_x");
            float yCoord = dataTableGetFloat(TBL_BACKUP, x, "loc_y");
            float zCoord = dataTableGetFloat(TBL_BACKUP, x, "loc_z");
            location myself = getLocation(self);
            String planet = myself.area;
            obj_id top = getTopMostContainer(self);
            String spawnRoom = dataTableGetString(TBL_BACKUP, x, "room");
            obj_id room = getCellId(structure, spawnRoom);
            location spawnPoint = new location(xCoord, yCoord, zCoord, planet, room);
            obj_id spawnedCreature = create.object(spawn, spawnPoint);
            attachScript(spawnedCreature, "theme_park.dungeon.death_watch_bunker.airlock_clean");
            if (x == 1)
            {
                setObjVar(spawnedCreature, "death_watch.backup", 1);
            }
            x = x + 1;
        }
        return SCRIPT_CONTINUE;
    }
}
