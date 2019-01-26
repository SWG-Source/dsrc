package script.space.quest_logic;

import script.dictionary;
import script.library.space_utils;
import script.location;
import script.obj_id;

public class recovery_target extends script.base_script
{
    public recovery_target()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "pendingWarp", null, 3600.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int pendingWarp(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id quest = getObjIdObjVar(self, "quest");
        if (isIdValid(quest) && exists(quest))
        {
            setObjVar(quest, "critical_warped_recovery", 1);
        }
        destroyObjectHyperspace(self);
        return SCRIPT_CONTINUE;
    }
    public int OnShipDisabled(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id quest = getObjIdObjVar(self, "quest");
        space_utils.notifyObject(quest, "recoverShipDisabled", null);
        return SCRIPT_CONTINUE;
    }
    public int selfDestruct(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_OVERRIDE;
    }
    public int objectDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id quest = getObjIdObjVar(self, "quest");
        dictionary outparams = new dictionary();
        outparams.put("ship", self);
        outparams.put("reason", 1);
        space_utils.notifyObject(quest, "recoveryFailed", outparams);
        return SCRIPT_CONTINUE;
    }
    public int missionAbort(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObjectHyperspace(self);
        return SCRIPT_CONTINUE;
    }
    public int registerDestination(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id quest = params.getObjId("quest");
        location loc = params.getLocation("loc");
        String dest = params.getString("dest");
        addLocationTarget3d(dest, loc, 300);
        obj_id player = params.getObjId("player");
        setObjVar(self, "player", player);
        setObjVar(self, "quest", quest);
        if (dest.equals("recovery"))
        {
            removeLocationTarget("escape");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnArrivedAtLocation(obj_id self, String name) throws InterruptedException
    {
        obj_id quest = getObjIdObjVar(self, "quest");
        if (name.equals("recovery"))
        {
            dictionary params = new dictionary();
            params.put("ship", self);
            space_utils.notifyObject(quest, "recoveryComplete", params);
        }
        else if (name.equals("escape"))
        {
            dictionary params = new dictionary();
            params.put("ship", self);
            params.put("reason", 0);
            space_utils.notifyObject(quest, "recoveryFailed", params);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id quest = getObjIdObjVar(self, "quest");
        if (isIdValid(quest) && exists(quest) && hasObjVar(quest, "critical_warped_recovery"))
        {
            dictionary outparams = new dictionary();
            outparams.put("ship", self);
            messageTo(quest, "warpoutFailureRecovery", null, 2.0f, false);
        }
        return SCRIPT_CONTINUE;
    }
}
