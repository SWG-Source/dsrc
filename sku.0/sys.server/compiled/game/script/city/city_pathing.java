package script.city;

import script.dictionary;
import script.library.create;
import script.library.utils;
import script.obj_id;

public class city_pathing extends script.base_script
{
    public city_pathing()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        respawnNpc(self);
        return SCRIPT_CONTINUE;
    }
    public int handleNpcRespawn(obj_id self, dictionary params) throws InterruptedException
    {
        if (params.getObjId("object") != getObjIdObjVar(self, "pathing.lastSpawned"))
        {
            return SCRIPT_CONTINUE;
        }
        respawnNpc(self);
        return SCRIPT_CONTINUE;
    }
    public void respawnNpc(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "npc"))
        {
            return;
        }
        String creatureName = getStringObjVar(self, "npc");
        obj_id npc = create.object(creatureName, getLocation(self));
        if (!isIdValid(npc))
        {
            debugServerConsoleMsg(self, "WARNING: city/city_pathing: could not spawn " + creatureName);
            debugServerConsoleMsg(self, "WARNING: city/city_pathing: bad creatureName on " + self + " at " + getLocation(self));
            return;
        }
        setObjVar(self, "pathing.lastSpawned", npc);
        create.addDestroyMessage(npc, "handleNpcRespawn", 60.0f, self);
        utils.setScriptVar(npc, "pathing.currentStop", self);
        utils.setScriptVar(npc, "pathing.homeStop", self);
        attachScript(npc, "city.city_pathing_npc");
    }
}
