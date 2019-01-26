package script.structure.filler;

import script.dictionary;
import script.library.structure;
import script.obj_id;

public class filler extends script.base_script
{
    public filler()
    {
    }
    public static final String SCRIPT_ME = "structure.filler.filler";
    public static final float DELAY_TIME = 5.0f;
    public static final String HANDLER_INIT_FILLER_SPAWN = "handleInitFillerSpawn";
    public static final String HANDLER_CLEANUP_FILLER_SPAWN = "handleCleanupFillerSpawn";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnUnloadedFromMemory(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int handleInitFillerSpawn(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("FILLER_BUILDING", "**************** TIME = " + getGameTime() + " ******************");
        structure.resetFillerSpawns(self);
        return SCRIPT_CONTINUE;
    }
    public int handleCleanupFillerSpawn(obj_id self, dictionary params) throws InterruptedException
    {
        structure.cleanupFillerSpawns(self);
        return SCRIPT_CONTINUE;
    }
}
