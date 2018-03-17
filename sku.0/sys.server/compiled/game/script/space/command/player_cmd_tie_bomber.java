package script.space.command;

import script.dictionary;
import script.library.ship_ai;
import script.library.space_combat;
import script.library.space_utils;
import script.obj_id;

public class player_cmd_tie_bomber extends script.base_script
{
    public player_cmd_tie_bomber()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        dictionary outparams = new dictionary();
        messageTo(self, "bomberStrikeTimeout", outparams, 1200.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "evacuate"))
        {
            debugServerConsoleMsg(null, "ONdESTROY - evacuating tie bomber unit just destructed. obj_id was: " + self);
            return SCRIPT_CONTINUE;
        }
        debugServerConsoleMsg(null, "ONdESTROY - tie bomber");
        if (hasObjVar(self, "commanderPlayer"))
        {
            dictionary outparams = new dictionary();
            outparams.put("deadBomberId", self);
            space_utils.notifyObject(getObjIdObjVar(self, "commanderPlayer"), "bomberStrikeUnitDestroyed", outparams);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnSpaceUnitMoveToComplete(obj_id self) throws InterruptedException
    {
        debugServerConsoleMsg(null, "ONsPACEuNITmOVEtOcOMPLETE - tie bomber script");
        int squadId = ship_ai.unitGetSquadId(self);
        obj_id[] squadUnits;
        if (ship_ai.isSquadIdValid(squadId))
        {
            squadUnits = ship_ai.squadGetUnitList(squadId);
        }
        else
        {
            return SCRIPT_CONTINUE;
        }
        for (obj_id squadUnit : squadUnits) {
            space_combat.destroyObjectHyperspace(squadUnit);
        }
        return SCRIPT_CONTINUE;
    }
    public int bomberStrikeTimeout(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isIdValid(self))
        {
            debugServerConsoleMsg(null, "+++ bomberStrikeTimeout . obj_id of the ship passed into the bomber strike timeout function doesn't come back as valid. What the hey?!?.");
            return SCRIPT_CONTINUE;
        }
        space_combat.destroyObjectHyperspace(self);
        return SCRIPT_CONTINUE;
    }
}
