package script.theme_park.meatlump;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.create;
import script.library.groundquests;

public class quest_shuttle_event extends script.base_script
{
    public quest_shuttle_event()
    {
    }
    public static final boolean LOGGING_ON = false;
    public static final String SMUGGLER_MOB = "mtp_hideout_quest_smuggler";
    public static final String MEATLUMP_LOG = "meatlump_weapons_delivery";
    public static final String OWNER_OBJVAR = "meatlump.owner";
    public static final String SMUGGLER_OBJVAR = "meatlump.smuggler";
    public static final String SHUTTLE_OBJVAR = "meatlump.shuttle";
    public static final String QUEST_TASK = "speakSmuggler";
    public static final String QUEST_NAME = "quest/mtp_hideout_retrieve_delivery";
    public int startLandingSequence(obj_id self, dictionary params) throws InterruptedException
    {
        blog("startLandingSequence init ");
        obj_id owner = getObjIdObjVar(self, OWNER_OBJVAR);
        if (!isValidId(owner) || !exists(owner))
        {
            return SCRIPT_CONTINUE;
        }
        queueCommand(self, (-1114832209), self, "", COMMAND_PRIORITY_FRONT);
        setPosture(self, POSTURE_PRONE);
        messageTo(self, "landed", null, 6.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int landed(obj_id self, dictionary params) throws InterruptedException
    {
        blog("landed init");
        obj_id owner = getObjIdObjVar(self, OWNER_OBJVAR);
        if (!isValidId(owner) || !exists(owner))
        {
            return SCRIPT_CONTINUE;
        }
        messageTo(self, "spawnSmuggler", null, 25.0f, false);
        messageTo(self, "startTakeOffSequence", null, 160.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int startTakeOffSequence(obj_id self, dictionary params) throws InterruptedException
    {
        blog("startTakeOffSequence init");
        obj_id owner = getObjIdObjVar(self, OWNER_OBJVAR);
        if (!isValidId(owner) || !exists(owner))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id smuggler = getObjIdObjVar(self, SMUGGLER_OBJVAR);
        if (isValidId(smuggler) && exists(smuggler))
        {
            npcEndConversation(owner);
            blog("startTakeOffSequence FOUND SMUGGLER. DESTROYING SMUGGLER");
            destroyObject(smuggler);
        }
        blog("startTakeOffSequence Sending signal if player has task");
        if (groundquests.isTaskActive(owner, QUEST_NAME, QUEST_TASK))
        {
            groundquests.sendSignal(owner, "smugglerSpoken");
        }
        queueCommand(self, (-1465754503), self, "", COMMAND_PRIORITY_FRONT);
        setPosture(self, POSTURE_UPRIGHT);
        messageTo(self, "cleanUp", null, 20.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int cleanUp(obj_id self, dictionary params) throws InterruptedException
    {
        blog("cleanUp init");
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int spawnSmuggler(obj_id self, dictionary params) throws InterruptedException
    {
        blog("spawnSmuggler init");
        obj_id owner = getObjIdObjVar(self, OWNER_OBJVAR);
        if (!isValidId(owner) || !exists(owner))
        {
            return SCRIPT_CONTINUE;
        }
        location loc = getLocation(self);
        float treasureXdelta = -9f;
        float treasureZdelta = 6f;
        location landingLoc = loc;
        landingLoc.x += treasureXdelta;
        landingLoc.z += treasureZdelta;
        landingLoc.y = getHeightAtLocation(landingLoc.x, landingLoc.z);
        obj_id smuggler = create.object(SMUGGLER_MOB, landingLoc, false);
        if (!isValidId(smuggler) || !exists(smuggler))
        {
            blog("spawnSmuggler failed to spawn smuggler");
            return SCRIPT_CONTINUE;
        }
        ai_lib.setDefaultCalmBehavior(smuggler, ai_lib.BEHAVIOR_SENTINEL);
        setObjVar(smuggler, OWNER_OBJVAR, owner);
        setObjVar(smuggler, SHUTTLE_OBJVAR, self);
        setObjVar(self, SMUGGLER_OBJVAR, smuggler);
        blog("spawnSmuggler smuggler spawned");
        return SCRIPT_CONTINUE;
    }
    public boolean blog(String msg) throws InterruptedException
    {
        if (LOGGING_ON)
        {
            LOG(MEATLUMP_LOG, msg);
        }
        return true;
    }
}
