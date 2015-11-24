package script.space.quest_logic;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.space_quest;
import script.library.space_utils;
import script.library.utils;
import script.library.prose;

public class assassinate_target extends script.base_script
{
    public assassinate_target()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "warpOut", null, 1200.f, false);
        return SCRIPT_CONTINUE;
    }
    public int warpOut(obj_id self, dictionary params) throws InterruptedException
    {
        setObjVar(self, "nowin", 1);
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
        return SCRIPT_CONTINUE;
    }
    public int objectDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id quest = getObjIdObjVar(self, "quest");
        if (hasObjVar(self, "nowin"))
        {
            dictionary outparams = new dictionary();
            outparams.put("ship", self);
            outparams.put("reason", 0);
            space_utils.notifyObject(quest, "targetEscaped", outparams);
            return SCRIPT_CONTINUE;
        }
        dictionary outparams = new dictionary();
        outparams.put("ship", self);
        outparams.put("reason", 1);
        space_utils.notifyObject(quest, "assassinateSuccess", outparams);
        return SCRIPT_CONTINUE;
    }
    public int OnArrivedAtLocation(obj_id self, String name) throws InterruptedException
    {
        obj_id quest = getObjIdObjVar(self, "quest");
        if (name.equals("escape"))
        {
            dictionary params = new dictionary();
            params.put("ship", self);
            params.put("reason", 0);
            space_utils.notifyObject(quest, "targetEscaped", params);
        }
        return SCRIPT_CONTINUE;
    }
    public int missionAbort(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObjectHyperspace(self);
        return SCRIPT_CONTINUE;
    }
}
