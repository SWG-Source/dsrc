package script.creature_spawner;

import script.dictionary;
import script.library.ai_lib;
import script.library.create;
import script.obj_id;

public class jedi_sentinel_dark_spawner extends script.base_script
{
    public jedi_sentinel_dark_spawner()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        spawnCreatures(self);
        return SCRIPT_CONTINUE;
    }
    public void spawnCreatures(obj_id self) throws InterruptedException
    {
        obj_id spawned = create.object("dark_jedi_sentinel", getLocation(self));
        attachScript(spawned, "theme_park.npc_died.npc_died");

        setObjVar(spawned, "spawner", self);
        setObjVar(spawned, "useOnIncapTrigger", true);
        setYaw(spawned, getYaw(self));

        ai_lib.setDefaultCalmBehavior(spawned, ai_lib.BEHAVIOR_SENTINEL);
    }
    public int npcDied(obj_id self, dictionary params) throws InterruptedException
    {
        spawnCreatures(self);
        return SCRIPT_CONTINUE;
    }
}
