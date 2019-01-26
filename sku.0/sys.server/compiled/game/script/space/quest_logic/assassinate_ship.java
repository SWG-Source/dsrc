package script.space.quest_logic;

import script.dictionary;
import script.library.space_utils;
import script.obj_id;

public class assassinate_ship extends script.base_script
{
    public assassinate_ship()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "warpOut", null, 1200.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int warpOut(obj_id self, dictionary params) throws InterruptedException
    {
        setObjVar(self, "nowin", 1);
        obj_id quest = getObjIdObjVar(self, "quest");
        if (isIdValid(quest))
        {
            dictionary outparams = new dictionary();
            outparams.put("ship", self);
            space_utils.notifyObject(quest, "warpoutFailure", outparams);
        }
        destroyObjectHyperspace(self);
        return SCRIPT_CONTINUE;
    }
    public int objectDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "nowin"))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, "selfDestruct"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id quest = getObjIdObjVar(self, "quest");
        dictionary outparams = new dictionary();
        outparams.put("ship", self);
        space_utils.notifyObject(quest, "handleShipDestroyed", outparams);
        return SCRIPT_CONTINUE;
    }
    public int missionAbort(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObjectHyperspace(self);
        return SCRIPT_CONTINUE;
    }
}
