package script.event.emp_day;

import script.dictionary;
import script.library.chat;
import script.library.groundquests;
import script.library.prose;
import script.location;
import script.obj_id;
import script.string_id;

public class goodbye_path_destroy extends script.base_script
{
    public goodbye_path_destroy()
    {
    }
    public static final boolean LOGGING_ON = true;
    public static final String MEATLUMP_LOG = "empire_day_trigger";
    public static final String CLIENT_EFFECT = "appearance/pt_smoke_puff.prt";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        blog("GOODBYE OnAttach");
        messageTo(self, "pathToExit", null, 2, false);
        messageTo(self, "selfDestruct", null, 120, false);
        return SCRIPT_CONTINUE;
    }
    public int OnMovePathComplete(obj_id self) throws InterruptedException
    {
        blog("GOODBYE OnMovePathComplete");
        if (!hasObjVar(self, "exitPoint"))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, "exitLocation"))
        {
            return SCRIPT_CONTINUE;
        }
        if (getLocation(self) == getLocationObjVar(self, "exitLocation"))
        {
            signal(self);
            messageTo(self, "selfDestruct", null, 2, false);
            return SCRIPT_CONTINUE;
        }
        pathTo(self, getLocationObjVar(self, "exitLocation"));
        return SCRIPT_CONTINUE;
    }
    public int OnMoveMoving(obj_id self) throws InterruptedException
    {
        blog("GOODBYE OnMoveMoving");
        if (!hasObjVar(self, "exitPoint"))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, "exitLocation"))
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "saidGoodbye"))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, "rescuer"))
        {
            setObjVar(self, "saidGoodbye", true);
            return SCRIPT_CONTINUE;
        }
        obj_id rescuer = getObjIdObjVar(self, "rescuer");
        if (!isValidId(rescuer) || !exists(rescuer))
        {
            setObjVar(self, "saidGoodbye", true);
            return SCRIPT_CONTINUE;
        }
        chat.publicChat(self, rescuer, null, null, prose.getPackage(new string_id("event/empire_day", "see_you_topside"), rescuer));
        setObjVar(self, "saidGoodbye", true);
        return SCRIPT_CONTINUE;
    }
    public int pathToExit(obj_id self, dictionary params) throws InterruptedException
    {
        blog("GOODBYE pathToExit");
        String mobDisguise = getStringObjVar(self, "disguise_npc");
        if (mobDisguise == null || mobDisguise.length() <= 0)
        {
            blog("GOODBYE pathToExit - Mob had no diguise");
            signal(self);
            messageTo(self, "selfDestruct", null, 2, false);
            return SCRIPT_CONTINUE;
        }
        obj_id[] exitPoints = getAllObjectsWithObjVar(getLocation(self), 40f, "disguise_exit");
        if (exitPoints == null || exitPoints.length <= 0)
        {
            blog("GOODBYE pathToExit - NO POINTS FOUND");
            signal(self);
            messageTo(self, "selfDestruct", null, 2, false);
            return SCRIPT_CONTINUE;
        }
        obj_id point = obj_id.NULL_ID;
        String var;
        for (obj_id exitPoint : exitPoints) {
            var = getStringObjVar(exitPoint, "disguise_exit");
            if (var == null || !var.equals(mobDisguise)) {
                blog("GOODBYE pathToExit - var: " + var + " mobDisguise: " + mobDisguise);
                continue;
            }
            point = exitPoint;
        }
        if (!isValidId(point) || !exists(point))
        {
            blog("GOODBYE pathToExit - NO POINTS FOUND AFTER LOOP");
            signal(self);
            messageTo(self, "selfDestruct", null, 2, false);
            return SCRIPT_CONTINUE;
        }
        location exitLocation = getLocation(point);
        if (exitLocation == null)
        {
            blog("GOODBYE pathToExit - LOCATION OF POINT 0 INVALID");
            signal(self);
            messageTo(self, "selfDestruct", null, 2, false);
            return SCRIPT_CONTINUE;
        }
        blog("GOODBYE pathToExit - NPC should be pathing");
        pathTo(self, exitLocation);
        setObjVar(self, "exitPoint", point);
        setObjVar(self, "exitLocation", exitLocation);
        signal(self);
        messageTo(self, "selfDestruct", null, 9, false);
        return SCRIPT_CONTINUE;
    }
    public int selfDestruct(obj_id self, dictionary params) throws InterruptedException
    {
        blog("GOODBYE selfDestruct");
        if (!hasObjVar(self, "sentSignal"))
        {
            signal(self);
        }
        destroyObject(self);
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
    public boolean signal(obj_id mob) throws InterruptedException
    {
        blog("GOODBYE signal");
        obj_id rescuer = getObjIdObjVar(mob, "rescuer");
        if (!isValidId(rescuer) || !exists(rescuer))
        {
            blog("GOODBYE rescuer invalid");
            return false;
        }
        blog("GOODBYE rescuer valid: " + rescuer);
        groundquests.sendSignal(rescuer, "prisonerHasLeftTheBuilding");
        blog("GOODBYE signal sent");
        playClientEffectObj(mob, CLIENT_EFFECT, mob, "");
        setObjVar(mob, "sentSignal", true);
        return true;
    }
}
