package script.space.command;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.space_utils;
import script.library.ship_ai;
import script.library.space_combat;
import script.library.utils;

public class player_cmd_pirate extends script.base_script
{
    public player_cmd_pirate()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        dictionary outparams = new dictionary();
        messageTo(self, "pirateTimeout", outparams, 1200.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "evacuate"))
        {
            debugServerConsoleMsg(null, "ONdESTROY - evacuating tie bomber unit just destructed. obj_id was: " + self);
            return SCRIPT_CONTINUE;
        }
        debugServerConsoleMsg(null, "ONdESTROY - space pirate");
        dictionary outparams = new dictionary();
        outparams.put("deadPirateId", self);
        if (hasObjVar(self, "commanderPlayer"))
        {
            space_utils.notifyObject(getObjIdObjVar(self, "commanderPlayer"), "pirateUnitDestroyed", outparams);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnSpaceUnitMoveToComplete(obj_id self) throws InterruptedException
    {
        debugServerConsoleMsg(null, "ONsPACEuNITmOVEtOcOMPLETE - space pirate script");
        int squadId = ship_ai.unitGetSquadId(self);
        obj_id[] squaddyList = null;
        if (ship_ai.isSquadIdValid(squadId))
        {
            squaddyList = ship_ai.squadGetUnitList(squadId);
        }
        else 
        {
            return SCRIPT_CONTINUE;
        }
        for (int i = 0; i < squaddyList.length; i++)
        {
            space_combat.destroyObjectHyperspace(squaddyList[i]);
        }
        return SCRIPT_CONTINUE;
    }
    public int pirateTimeout(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isIdValid(self))
        {
            debugServerConsoleMsg(null, "+++ pirateTimeout . obj_id of the ship passed into the bomber strike timeout function doesn't come back as valid. What the hey?!?.");
            return SCRIPT_CONTINUE;
        }
        space_combat.destroyObjectHyperspace(self);
        return SCRIPT_CONTINUE;
    }
}
