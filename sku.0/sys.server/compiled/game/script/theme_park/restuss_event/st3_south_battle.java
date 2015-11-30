package script.theme_park.restuss_event;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.groundquests;

public class st3_south_battle extends script.base_script
{
    public st3_south_battle()
    {
    }
    public static final String VOLUME_NAME = "quest_area";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        createTriggerVolume(VOLUME_NAME, 10.0f, true);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        createTriggerVolume(VOLUME_NAME, 10.0f, true);
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        location spawnerLocation = getLocation(self);
        if (!utils.hasScriptVar(self, "spawnedAlready") && (isPlayer(breacher)))
        {
            obj_id waveSpawner = createObject("object/tangible/theme_park/invisible_object.iff", getLocation(self));
            debugSpeakMsg(self, "I just made " + waveSpawner);
            setObjVar(waveSpawner, "wave_spawner.data_table", "datatables/spawning/restuss_event/st3_southgate_battle.iff");
            attachScript(waveSpawner, "theme_park.wave_spawner");
            utils.setScriptVar(self, "spawnedAlready", 1);
            messageTo(self, "readyToSpawn", null, 1200.0f, false);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int test(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id waveSpawner = createObject("object/tangible/theme_park/invisible_object.iff", getLocation(self));
        debugSpeakMsg(self, "I just made " + waveSpawner);
        setObjVar(waveSpawner, "wave_spawner.data_table", "datatables/spawning/restuss_event/st3_southgate_battle.iff");
        attachScript(waveSpawner, "theme_park.wave_spawner");
        utils.setScriptVar(self, "spawnedAlready", 1);
        messageTo(self, "readyToSpawn", null, 900.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int readyToSpawn(obj_id self, dictionary params) throws InterruptedException
    {
        utils.removeScriptVar(self, "spawnedAlready");
        return SCRIPT_CONTINUE;
    }
}
