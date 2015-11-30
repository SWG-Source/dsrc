package script.theme_park.corellia.content;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.ai_lib;
import script.library.spawning;
import script.library.create;

public class u13_ponda_gungan_statue_spawner extends script.base_script
{
    public u13_ponda_gungan_statue_spawner()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (canSpawnByConfigSetting())
        {
            messageTo(self, "spawnGunganStatueEvent", null, 10, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (canSpawnByConfigSetting())
        {
            messageTo(self, "spawnGunganStatueEvent", null, 20, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int spawnGunganStatueEvent(obj_id self, dictionary params) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, "gunganStatueSpawned"))
        {
            String statueTemplate = "object/tangible/quest/u13_gungan_statue.iff";
            location here = getLocation(self);
            obj_id statue = createObject(statueTemplate, here);
            setObjVar(statue, "objParent", self);
            utils.setScriptVar(self, "gunganStatueSpawned", true);
            float spawnerYaw = getYaw(self);
            setYaw(statue, spawnerYaw);
            setObjVar(statue, "orignalYaw", spawnerYaw);
        }
        return SCRIPT_CONTINUE;
    }
    public boolean canSpawnByConfigSetting() throws InterruptedException
    {
        String disableSpawners = getConfigSetting("GameServer", "disableAreaSpawners");
        if (disableSpawners == null)
        {
            return true;
        }
        if (disableSpawners.equals("true") || disableSpawners.equals("1"))
        {
            return false;
        }
        return true;
    }
}
