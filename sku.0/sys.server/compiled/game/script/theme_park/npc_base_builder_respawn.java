package script.theme_park;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.trial;
import script.library.utils;
import script.library.ai_lib;

public class npc_base_builder_respawn extends script.base_script
{
    public npc_base_builder_respawn()
    {
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        if (ai_lib.isMob(self))
        {
            return SCRIPT_CONTINUE;
        }
        handleRespawn(self);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        handleRespawn(self);
        return SCRIPT_CONTINUE;
    }
    public void handleRespawn(obj_id self) throws InterruptedException
    {
        obj_id parent = trial.getParent(self);
        int spawnNumber = getIntObjVar(self, "base_builder.spawn_position");
        String sRespawn = getStringObjVar(self, "respawn");
        int iRespawn = 0;
        String[] timeHack = split(sRespawn, ':');
        if (timeHack.length > 1)
        {
            int min = utils.stringToInt(timeHack[0]);
            int max = utils.stringToInt(timeHack[1]);
            iRespawn = rand(min, max);
        }
        else 
        {
            iRespawn = utils.stringToInt(sRespawn);
        }
        dictionary respawnData = trial.getSessionDict(parent);
        respawnData.put("spawnNumber", spawnNumber);
        messageTo(parent, "handleEntityRespawn", respawnData, iRespawn, false);
    }
}
