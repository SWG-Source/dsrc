package script.city;

import script.dictionary;
import script.library.utils;
import script.obj_id;

public class city_pathing_npc extends script.base_script
{
    public city_pathing_npc()
    {
    }
    public static final int NEXT_ACTION_NONE = -1;
    public static final int NEXT_ACTION_FACETO = 0;
    public static final int NEXT_ACTION_DOANIM = 1;
    public static final int NEXT_ACTION_PATHTONEXT = 2;
    public static final int LAST_POSSIBLE_ACTION = 2;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        executeAction(self, NEXT_ACTION_NONE);
        return SCRIPT_CONTINUE;
    }
    public int resumeDefaultCalmBehavior(obj_id self, dictionary params) throws InterruptedException
    {
        executeAction(self, NEXT_ACTION_NONE);
        return SCRIPT_OVERRIDE;
    }
    public int OnMovePathComplete(obj_id self) throws InterruptedException
    {
        executeAction(self, NEXT_ACTION_FACETO);
        return SCRIPT_CONTINUE;
    }
    public int OnMovePathNotFound(obj_id self) throws InterruptedException
    {
        executeAction(self, NEXT_ACTION_PATHTONEXT);
        checkForFeetGluedToFloor(self);
        return SCRIPT_CONTINUE;
    }
    public void executeAction(obj_id npc, int lastAction) throws InterruptedException
    {
        obj_id currentStop = utils.getObjIdScriptVar(npc, "pathing.currentStop");
        if (!isIdValid(currentStop))
        {
            debugServerConsoleMsg(npc, "WARNING: error in city.city_pathing_npc: cannot find stop " + currentStop + " so I don't know what to do");
            stop(npc);
            doAnimationAction(npc, "weeping");
            return;
        }
        switch (lastAction)
        {
            case NEXT_ACTION_NONE:
                pathTo(npc, getHomeLocation(npc));
                obj_id homeStop = utils.getObjIdScriptVar(npc, "pathing.homeStop");
                utils.setScriptVar(npc, "pathing.currentStop", homeStop);
                return;
            case NEXT_ACTION_FACETO:
                if (hasObjVar(currentStop, "faceto"))
                {
                    doFacingAction(npc, currentStop);
                    return;
                }
            case NEXT_ACTION_DOANIM:
                executeAnimation(npc, currentStop);
                return;
            case NEXT_ACTION_PATHTONEXT:
                pathToNext(npc, currentStop);
                return;
        }
        lastAction++;
        if (lastAction > LAST_POSSIBLE_ACTION)
        {
            lastAction = NEXT_ACTION_NONE;
        }
        executeAction(npc, lastAction);
    }
    public int handleNextAction(obj_id self, dictionary params) throws InterruptedException
    {
        int nextAction = params.getInt("nextAction");
        executeAction(self, nextAction);
        return SCRIPT_CONTINUE;
    }
    public void doFacingAction(obj_id npc, obj_id currentStop) throws InterruptedException
    {
        obj_id objThingToFace = utils.stringToObjId("" + getIntObjVar(currentStop, "faceto"));
        if (isIdValid(objThingToFace))
        {
            faceTo(npc, objThingToFace);
        }
        dictionary parms = new dictionary();
        parms.put("nextAction", NEXT_ACTION_DOANIM);
        messageTo(npc, "handleNextAction", parms, 3, false);
    }
    public void executeAnimation(obj_id npc, obj_id currentStop) throws InterruptedException
    {
        String animToDo = getStringObjVar(currentStop, "anim");
        int delay = getIntObjVar(currentStop, "delay");
        if (delay == 0)
        {
            delay = 10;
        }
        if (animToDo != null)
        {
            doAnimationAction(npc, animToDo);
        }
        dictionary parms = new dictionary();
        parms.put("nextAction", NEXT_ACTION_PATHTONEXT);
        messageTo(npc, "handleNextAction", parms, delay, false);
    }
    public void pathToNext(obj_id npc, obj_id currentStop) throws InterruptedException
    {
        if (hasObjVar(currentStop, "destination"))
        {
            obj_id dest = utils.stringToObjId("" + getIntObjVar(currentStop, "destination"));
            utils.setScriptVar(npc, "pathing.currentStop", dest);
            pathTo(npc, getLocation(dest));
        }
        else 
        {
            executeAction(npc, NEXT_ACTION_NONE);
        }
    }
    public void checkForFeetGluedToFloor(obj_id self) throws InterruptedException
    {
        setObjVar(self, "mightBeStuck", getLocation(self));
        messageTo(self, "amIStuck", null, 300, false);
    }
    public int amIStuck(obj_id self, dictionary params) throws InterruptedException
    {
        if (getLocation(self).equals(getLocationObjVar(self, "mightBeStuck")))
        {
            destroyObject(self);
        }
        return SCRIPT_CONTINUE;
    }
}
