package script.theme_park.nightsister;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.combat;
import script.library.utils;
import script.library.create;

public class nsister_rancor_axkva_guards extends script.base_script
{
    public nsister_rancor_axkva_guards()
    {
    }
    public int spawnguards(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id axkva = params.getObjId("axkva");
        String datatable = "datatables/spawning/theme_park/dathomir_nsister_rancor_cave_guards.iff";
        if (datatable == null)
        {
            return SCRIPT_OVERRIDE;
        }
        int numberOfCreaturesToSpawn = dataTableGetNumRows(datatable);
        int x = utils.getIntScriptVar(self, "guardCounter");
        while (x < numberOfCreaturesToSpawn)
        {
            String spawn = dataTableGetString(datatable, x, "spawns");
            float xCoord = dataTableGetFloat(datatable, x, "loc_x");
            float yCoord = dataTableGetFloat(datatable, x, "loc_y");
            float zCoord = dataTableGetFloat(datatable, x, "loc_z");
            location myself = getLocation(self);
            String planet = myself.area;
            String spawnRoom = dataTableGetString(datatable, x, "room");
            obj_id room = getCellId(self, spawnRoom);
            location spawnPoint = new location(xCoord, yCoord, zCoord, planet, room);
            obj_id spawnedCreature = create.object(spawn, spawnPoint);
            setObjVar(spawnedCreature, "cave", self);
            String geoScript = dataTableGetString(datatable, x, "script");
            ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
            if (geoScript != null && !geoScript.equals(""))
            {
                attachScript(spawnedCreature, geoScript);
            }
            String creatureName = dataTableGetString(datatable, x, "name");
            if (creatureName != null && !creatureName.equals(""))
            {
                setName(spawnedCreature, creatureName);
            }
            String mood = dataTableGetString(datatable, x, "mood");
            float yaw = dataTableGetFloat(datatable, x, "yaw");
            setYaw(spawnedCreature, yaw);
            ai_lib.setDefaultCalmMood(spawnedCreature, mood);
            setObjVar(spawnedCreature, "spawn_number", x);
            x = x + 1;
            utils.setScriptVar(self, "guardCounter", x);
            messageTo(self, "beginSpawn", null, 1, false);
        }
        return SCRIPT_CONTINUE;
    }
}
