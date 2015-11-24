package script.event;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.create;
import script.library.locations;
import script.library.groundquests;

public class ep3_trando_herald extends script.base_script
{
    public ep3_trando_herald()
    {
    }
    public static final String STF_FILE = "event/ep3_trando_herald";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "ep3HeraldGiveAnotherWp", null, 1, false);
        float timeStamp = getGameTime();
        setObjVar(self, "event.ep3_trando_herald.timeStamp", timeStamp);
        messageTo(self, "ep3HeraldTimeUp", null, 1800, false);
        return SCRIPT_CONTINUE;
    }
    public int OnLogin(obj_id self) throws InterruptedException
    {
        float rightNow = getGameTime();
        float timeStamp = getFloatObjVar(self, "event.ep3_trando_herald.timeStamp");
        float delta = rightNow - timeStamp;
        if (delta > 1800)
        {
            messageTo(self, "ep3HeraldTimeUp", null, 1, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int ep3HeraldGiveAnotherWp(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "event.ep3_trando_herald.waypoint"))
        {
            obj_id waypoint = getObjIdObjVar(self, "event.ep3_trando_herald.waypoint");
            destroyWaypointInDatapad(waypoint, self);
            removeLocationTarget("ep3HeraldWookieeSpawn");
        }
        location here = getLocation(self);
        location spawnLoc = null;
        int x = 0;
        while (x < 10)
        {
            location loc = utils.getRandomLocationInRing(here, 1000, 2500);
            spawnLoc = locations.getGoodLocationAroundLocation(loc, 5f, 5f, 50f, 50f);
            if (spawnLoc != null)
            {
                break;
            }
            x++;
        }
        obj_id waypoint = createWaypointInDatapad(self, spawnLoc);
        if (isIdValid(waypoint))
        {
            addLocationTarget("ep3HeraldWookieeSpawn", spawnLoc, 64);
            setWaypointActive(waypoint, true);
            setWaypointName(waypoint, utils.packStringId(new string_id(STF_FILE, "wp_wookiee_slave")));
            setObjVar(self, "event.ep3_trando_herald.spawnLoc", spawnLoc);
            setObjVar(self, "event.ep3_trando_herald.waypoint", waypoint);
        }
        return SCRIPT_CONTINUE;
    }
    public int ep3HeraldTimeUp(obj_id self, dictionary params) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/ep3_trando_herald");
        if (!questIsQuestComplete(questId1, self))
        {
            int questCrc = questGetQuestId("quest/ep3_trando_herald");
            questClearQuest(questId1, self);
            sendSystemMessage(self, new string_id(STF_FILE, "time_is_up"));
            obj_id waypoint = getObjIdObjVar(self, "event.ep3_trando_herald.waypoint");
            destroyWaypointInDatapad(waypoint, self);
            removeLocationTarget("ep3HeraldWookieeSpawn");
            removeObjVar(self, "event.ep3_trando_herald");
            detachScript(self, "event.ep3_trando_herald");
        }
        else 
        {
            removeObjVar(self, "event.ep3_trando_herald");
            detachScript(self, "event.ep3_trando_herald");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnArrivedAtLocation(obj_id self, String name) throws InterruptedException
    {
        if (name.equals("ep3HeraldWookieeSpawn"))
        {
            obj_id waypoint = getObjIdObjVar(self, "event.ep3_trando_herald.waypoint");
            destroyWaypointInDatapad(waypoint, self);
            removeLocationTarget("ep3HeraldWookieeSpawn");
            location spawnLoc = getLocationObjVar(self, "event.ep3_trando_herald.spawnLoc");
            obj_id wookieeSlave = create.object("wookiee_brawler", spawnLoc);
            setName(wookieeSlave, utils.packStringId(new string_id(STF_FILE, "wookiee_slave")));
            removeLocationTarget(name);
        }
        return SCRIPT_CONTINUE;
    }
}
