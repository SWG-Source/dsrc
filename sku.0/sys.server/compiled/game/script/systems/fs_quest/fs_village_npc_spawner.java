package script.systems.fs_quest;

import script.dictionary;
import script.library.fs_dyn_village;
import script.obj_id;

public class fs_village_npc_spawner extends script.base_script
{
    public fs_village_npc_spawner()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        fs_dyn_village.destroySpawnersNpc(self);
        return SCRIPT_CONTINUE;
    }
    public int msgNpcDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int msgSpawnNpc(obj_id self, dictionary params) throws InterruptedException
    {
        boolean createIfExists = true;
        boolean destroyOldIfExists = true;
        if (params.containsKey("createIfExists"))
        {
            createIfExists = params.getBoolean("createIfExists");
        }
        if (params.containsKey("destroyOldIfExists"))
        {
            destroyOldIfExists = params.getBoolean("destroyOldIfExists");
        }
        return SCRIPT_CONTINUE;
    }
}
