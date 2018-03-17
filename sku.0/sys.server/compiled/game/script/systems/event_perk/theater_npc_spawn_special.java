package script.systems.event_perk;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.create;
import script.library.ai_lib;
import script.library.locations;

public class theater_npc_spawn_special extends script.base_script
{
    public theater_npc_spawn_special()
    {
    }
    public int OnTheaterCreated(obj_id self, obj_id[] objects, obj_id player, obj_id creator) throws InterruptedException
    {
        location here = getLocation(self);
        float lifeSpan = getFloatObjVar(self, "event_perk.lifeSpan");
        float timeStamp = getFloatObjVar(self, "event_perk.timeStamp");
        float terminalRegistration = getFloatObjVar(self, "event_perk.terminal_registration");
        String spawn = getStringObjVar(self, "event_perk.stringData1");
        float xOffset = getFloatObjVar(self, "event_perk.floatData1");
        float zOffset = getFloatObjVar(self, "event_perk.floatData2");
        obj_id owner = getObjIdObjVar(self, "event_perk.owner");
        here.x += xOffset;
        here.z += zOffset;
        obj_id specialNpc = create.object(spawn, here);
        setObjVar(specialNpc, "event_perk.lifeSpan", lifeSpan);
        setObjVar(specialNpc, "event_perk.timeStamp", timeStamp);
        setObjVar(specialNpc, "event_perk.terminal_registration", terminalRegistration);
        if (spawn.equals("imperial_recruiter") || spawn.equals("rebel_recruiter"))
        {
            ai_lib.setDefaultCalmBehavior(specialNpc, ai_lib.BEHAVIOR_SENTINEL);
            ai_lib.stop(specialNpc);
            setInvulnerable(specialNpc, true);
            attachScript(specialNpc, "npc.faction_recruiter.recruiter_setup");
            attachScript(specialNpc, "npc.faction_recruiter.faction_recruiter");
        }
        return SCRIPT_CONTINUE;
    }
}
