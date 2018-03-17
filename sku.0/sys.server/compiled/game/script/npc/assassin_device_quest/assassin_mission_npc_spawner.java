package script.npc.assassin_device_quest;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.ai_lib;

public class assassin_mission_npc_spawner extends script.base_script
{
    public assassin_mission_npc_spawner()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        String strConfigSetting = getConfigSetting("GameServer", "assassinMissionsEnabled");
        if ((strConfigSetting == null) || (strConfigSetting.equals("")))
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "spawns"))
        {
            String spawn = getStringObjVar(self, "spawns");
            obj_id celeb = create.object(spawn, getLocation(self));
            setInvulnerable(celeb, true);
            ai_lib.setDefaultCalmBehavior(celeb, ai_lib.BEHAVIOR_SENTINEL);
        }
        return SCRIPT_CONTINUE;
    }
}
