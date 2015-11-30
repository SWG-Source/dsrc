package script.creature_spawner;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.locations;
import script.library.create;

public class krayt_graveyard extends script.base_script
{
    public krayt_graveyard()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        doKraytSpawn();
        return SCRIPT_CONTINUE;
    }
    public void doKraytSpawn() throws InterruptedException
    {
        obj_id self = getSelf();
        if (hasObjVar(self, "Krayt"))
        {
            return;
        }
        else 
        {
            location here = getLocation(self);
            location dragon = locations.getGoodLocationAroundLocation(here, 100f, 100f, 100f, 100f);
            obj_id kd = create.object("canyon_krayt_dragon", dragon);
            setObjVar(kd, "creater", self);
            setObjVar(self, "Krayt", kd);
            attachScript(kd, "creature_spawner.death_msg");
            return;
        }
    }
    public int creatureDied(obj_id self, dictionary params) throws InterruptedException
    {
        messageTo(self, "spawnNew", null, 30, true);
        return SCRIPT_CONTINUE;
    }
    public int spawnNew(obj_id self, dictionary params) throws InterruptedException
    {
        removeObjVar(self, "Krayt");
        doKraytSpawn();
        return SCRIPT_CONTINUE;
    }
}
