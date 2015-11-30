package script.item.travel_ticket;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.travel;

public class travel_shuttle extends script.base_script
{
    public travel_shuttle()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        detachScript(self, "ai.ai");
        detachScript(self, "ai.creature_combat");
        detachScript(self, "skeleton.humanoid");
        detachScript(self, "systems.combat.combat_actions");
        detachScript(self, "systems.combat.credit_for_kills");
        stop(self);
        setPosture(self, POSTURE_UPRIGHT);
        attacker_results cbtAnimationResults = new attacker_results();
        cbtAnimationResults.endPosture = POSTURE_UPRIGHT;
        cbtAnimationResults.id = self;
        doCombatResults("change_posture", cbtAnimationResults, null);
        queueCommand(self, (-1465754503), self, "", COMMAND_PRIORITY_FRONT);
        obj_id starport = travel.getStarportFromTerminal(self);
        if (starport == null || starport == obj_id.NULL_ID)
        {
            LOG("LOG_CHANNEL", "travel_shuttle::OnAttach -- starport is null for " + self);
            return SCRIPT_CONTINUE;
        }
        int ground_time = travel.getGroundTime(starport);
        messageTo(self, "msgShuttleTakeOff", null, ground_time, false);
        return SCRIPT_CONTINUE;
    }
    public int OnUnloadedFromMemory(obj_id self) throws InterruptedException
    {
        obj_id starport = travel.getStarportFromTerminal(self);
        LOG("LOG_CHANNEL", "travel_shuttle::OnUnloadedFromMemory -- " + self + " from starport " + starport);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "travel_shuttle::OnInitialize -- " + self);
        obj_id starport = travel.getStarportFromTerminal(self);
        if (starport == null || starport == obj_id.NULL_ID)
        {
            LOG("LOG_CHANNEL", "travel_shuttle::OnInitialize -- starport is null for " + self);
            return SCRIPT_CONTINUE;
        }
        boolean stuff = false;
        if (self.isInitialized())
        {
            setPosture(self, POSTURE_UPRIGHT);
            attacker_results cbtAnimationResults = new attacker_results();
            cbtAnimationResults.endPosture = POSTURE_UPRIGHT;
            cbtAnimationResults.id = self;
            doCombatResults("change_posture", cbtAnimationResults, null);
            queueCommand(self, (-1465754503), self, "", COMMAND_PRIORITY_FRONT);
            setObjVar(starport, travel.VAR_SHUTTLE_AVAILABLE, 1);
            setObjVar(starport, travel.VAR_SHUTTLE_TIMESTAMP, getGameTime());
            int ground_time = travel.getGroundTime(starport);
            LOG("LOG_CHANNEL", "starport ->" + starport + " ground_time ->" + ground_time + " shuttle ->" + self);
            messageTo(self, "msgShuttleTakeOff", null, ground_time, false);
        }
        else 
        {
            LOG("LOG_CHANNEL", "travel_shuttle::OnInitialize -- starport " + starport + " is not yet loaded.");
            dictionary params = new dictionary();
            params.put("shuttle", self);
            messageTo(starport, "msgRestartShuttle", params, 10.0f, false);
            LOG("LOG_CHANNEL", "travel_shuttle::OnInitialize -- send message to " + starport);
        }
        return SCRIPT_CONTINUE;
    }
    public int msgShuttleTakeOff(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "travel_shuttle::msgShuttleTakeOff --" + self);
        if (hasMessageTo(self, "msgShuttleTakeOff"))
        {
            return SCRIPT_CONTINUE;
        }
        setPosture(self, POSTURE_PRONE);
        attacker_results cbtAnimationResults = new attacker_results();
        cbtAnimationResults.endPosture = POSTURE_PRONE;
        cbtAnimationResults.id = self;
        doCombatResults("change_posture", cbtAnimationResults, null);
        queueCommand(self, (-1114832209), self, "", COMMAND_PRIORITY_FRONT);
        obj_id starport = travel.getStarportFromTerminal(self);
        boolean toggle = travel.setShuttleAvailability(starport);
        LOG("LOG_CHANNEL", "travel_shuttle::msgShuttleTakeOff -- starport ->" + starport + " toggle ->" + toggle);
        int air_time = travel.getAirTime(starport);
        messageTo(self, "msgShuttleLand", null, air_time, false);
        return SCRIPT_CONTINUE;
    }
    public int msgShuttleLand(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "travel_shuttle::msgShuttleLand -- " + self);
        setPosture(self, POSTURE_UPRIGHT);
        attacker_results cbtAnimationResults = new attacker_results();
        cbtAnimationResults.endPosture = POSTURE_UPRIGHT;
        cbtAnimationResults.id = self;
        doCombatResults("change_posture", cbtAnimationResults, null);
        queueCommand(self, (-1465754503), self, "", COMMAND_PRIORITY_FRONT);
        obj_id starport = travel.getStarportFromTerminal(self);
        int ground_time = travel.getGroundTime(starport);
        messageTo(self, "msgShuttleTakeOff", null, ground_time, false);
        messageTo(self, "msgBoardTime", null, 25, false);
        return SCRIPT_CONTINUE;
    }
    public int msgBoardTime(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "travel_shuttle::msgBoardTime -- " + self);
        obj_id starport = travel.getStarportFromTerminal(self);
        boolean toggle = travel.setShuttleAvailability(starport);
        LOG("LOG_CHANNEL", "travel_shuttle::msgBoardTime -- starport ->" + starport + " toggle ->" + toggle);
        return SCRIPT_CONTINUE;
    }
    public int msgRestartShuttle(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "travel_shuttle::msgRestartShuttle -- " + self);
        obj_id starport = params.getObjId("starport");
        if (starport == null || starport == obj_id.NULL_ID)
        {
            LOG("LOG_CHANNEL", "starport::msgRestartShuttle -- starport is null for " + self);
            return SCRIPT_CONTINUE;
        }
        setPosture(self, POSTURE_UPRIGHT);
        attacker_results cbtAnimationResults = new attacker_results();
        cbtAnimationResults.endPosture = POSTURE_UPRIGHT;
        cbtAnimationResults.id = self;
        doCombatResults("change_posture", cbtAnimationResults, null);
        queueCommand(self, (-1465754503), self, "", COMMAND_PRIORITY_FRONT);
        setObjVar(starport, travel.VAR_SHUTTLE_AVAILABLE, 1);
        setObjVar(starport, travel.VAR_SHUTTLE_TIMESTAMP, getGameTime());
        int ground_time = travel.getGroundTime(starport);
        messageTo(self, "msgShuttleTakeOff", null, ground_time, false);
        return SCRIPT_CONTINUE;
    }
}
