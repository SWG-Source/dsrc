package script.systems.spawning;

import script.dictionary;
import script.obj_id;

public class spawn_npc_lair_tracker extends script.base_script
{
    public spawn_npc_lair_tracker()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int spawn_Cleanup(obj_id self, dictionary params) throws InterruptedException
    {
        setObjVar(self, "intDeleting", 1);
        debugSpeakMsg(self, "I am walking to an interesting place and despawning. Pay no attention to me!");
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int spawn_removeNPC(obj_id self, dictionary params) throws InterruptedException
    {
        debugSpeakMsg(self, "I am walking to an interesting place and despawning. Pay no attention to me!");
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int OnUnloadedFromMemory(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "intDeleting"))
        {
            destroyObject(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "intDeleting"))
        {
            obj_id objMaster = getObjIdObjVar(self, "objMaster");
            dictionary dctParams = new dictionary();
            dctParams.put("objNPC", self);
            messageTo(objMaster, "npc_Delete", dctParams, 0, true);
            setObjVar(self, "intDeleting", 1);
        }
        return SCRIPT_CONTINUE;
    }
}
