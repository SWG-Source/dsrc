package script.space.quest_logic;

import script.dictionary;
import script.obj_id;

public class recovery_escort extends script.base_script
{
    public recovery_escort()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "pendingWarp", null, 1200.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int pendingWarp(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id quest = getObjIdObjVar(self, "quest");
        if (isIdValid(quest) && exists(quest))
        {
            setObjVar(quest, "critical_warped", 1);
        }
        destroyObjectHyperspace(self);
        return SCRIPT_CONTINUE;
    }
    public int missionAbort(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObjectHyperspace(self);
        return SCRIPT_CONTINUE;
    }
}
