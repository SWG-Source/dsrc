package script.theme_park.dungeon.mustafar_trials.valley_battleground;

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

public class turret_droid_controller extends script.base_script
{
    public turret_droid_controller()
    {
    }
    public static final String controllerObject = "object/tangible/dungeon/mustafar/valley_battlefield/turret_controller_object.iff";
    public static final boolean LOGGING = false;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        stop(self);
        return SCRIPT_CONTINUE;
    }
    public int buildTurret(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id controller = params.getObjId("turretController");
        String turretType = params.getString("turretType");
        location buildLoc = params.getLocation("buildLoc");
        utils.setScriptVar(self, "turretController", controller);
        utils.setScriptVar(self, "turretType", turretType);
        utils.setScriptVar(self, "buildLoc", buildLoc);
        location pathLoc = new location(buildLoc.x - 2, buildLoc.y, buildLoc.z - 2, buildLoc.area, buildLoc.cell);
        ai_lib.aiPathTo(self, pathLoc);
        setHomeLocation(self, pathLoc);
        return SCRIPT_CONTINUE;
    }
    public int OnMovePathComplete(obj_id self) throws InterruptedException
    {
        String turretType = utils.getStringScriptVar(self, "turretType");
        obj_id controller = utils.getObjIdScriptVar(self, "turretController");
        String turretTemplate = "object/tangible/dungeon/mustafar/valley_battlefield/turret_" + turretType + ".iff";
        location buildLoc = utils.getLocationScriptVar(self, "buildLoc");
        obj_id turret = createObject(turretTemplate, buildLoc);
        if (!isIdValid(turret))
        {
            return SCRIPT_CONTINUE;
        }
        dictionary dict = new dictionary();
        dict.put("turretId", turret);
        utils.setScriptVar(turret, "controllerId", controller);
        attachScript(turret, "theme_park.dungeon.mustafar_trials.valley_battleground.turret_operations");
        messageTo(controller, "builtTurret", dict, 0, false);
        return SCRIPT_CONTINUE;
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING)
        {
            LOG("logging/turret_droid_controller/" + section, message);
        }
    }
}
