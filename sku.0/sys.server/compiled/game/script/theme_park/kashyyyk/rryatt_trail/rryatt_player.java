package script.theme_park.kashyyyk.rryatt_trail;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.space_dungeon;

public class rryatt_player extends script.base_script
{
    public rryatt_player()
    {
    }
    public static final String lastTrailGuideTransition = "lastRryattTransition";
    public int OnLogin(obj_id self) throws InterruptedException
    {
        String scene = getCurrentSceneName();
        if (!scene.equals("kashyyyk_rryatt_trail"))
        {
            detachScript(self, "theme_park.kashyyyk.rryatt_trail.rryatt_player");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, lastTrailGuideTransition))
        {
            removeObjVar(self, lastTrailGuideTransition);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnUnsticking(obj_id self) throws InterruptedException
    {
        obj_id movementBase = obj_id.NULL_ID;
        if (hasObjVar(self, lastTrailGuideTransition))
        {
            movementBase = getObjIdObjVar(self, lastTrailGuideTransition);
            if (isIdValid(movementBase) && exists(movementBase))
            {
                String moveTo = getStringObjVar(movementBase, "zoneLine");
                dictionary params = new dictionary();
                params.put("player", self);
                params.put("zoneIn", false);
                messageTo(movementBase, "handleZoneTransitionRequest", params, 0, false);
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, space_dungeon.INSTANCE_ID))
        {
            movementBase = getObjIdObjVar(self, space_dungeon.INSTANCE_ID);
            if (isIdValid(movementBase) && exists(movementBase))
            {
                location baseLoc = getLocation(movementBase);
                warpPlayer(self, baseLoc.area, baseLoc.x, baseLoc.y, baseLoc.z, null, 0, 0, 0);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
}
