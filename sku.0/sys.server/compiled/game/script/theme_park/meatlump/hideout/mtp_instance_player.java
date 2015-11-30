package script.theme_park.meatlump.hideout;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.create;
import script.library.instance;
import script.library.groundquests;
import script.library.structure;
import script.library.sui;
import script.library.utils;

public class mtp_instance_player extends script.base_script
{
    public mtp_instance_player()
    {
    }
    public int OnLogin(obj_id self) throws InterruptedException
    {
        location here = getLocation(self);
        obj_id containingBuilding = getTopMostContainer(self);
        if (isIdValid(containingBuilding))
        {
            here = getLocation(containingBuilding);
        }
        String area = getBuildoutAreaName(here.x, here.z);
        if (area == null || area.equals(""))
        {
            detachScript(self, "theme_park.meatlump.hideout.mtp_instance_player");
            return SCRIPT_CONTINUE;
        }
        if (!area.equals("mtp_hideout_instance"))
        {
            detachScript(self, "theme_park.meatlump.hideout.mtp_instance_player");
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnNewbieTutorialResponse(obj_id self, String action) throws InterruptedException
    {
        if (action.equals("clientReady"))
        {
            location here = getLocation(self);
            if (here.area.equals("dungeon1"))
            {
                obj_id containingBuilding = getTopMostContainer(self);
                if (isIdValid(containingBuilding))
                {
                    here = getLocation(containingBuilding);
                }
                String buildoutAreaName = getBuildoutAreaName(here.x, here.z);
                if (buildoutAreaName.equals("mtp_hideout_instance"))
                {
                    if (utils.hasScriptVar(self, "mtp_currentInstanceTrigger"))
                    {
                        String instanceTrigger = utils.getStringScriptVar(self, "mtp_currentInstanceTrigger");
                        groundquests.sendSignal(self, instanceTrigger);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnEnteredCombat(obj_id self) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "mtp_rescuedMeatlump"))
        {
            obj_id rescuedMeatlump = utils.getObjIdScriptVar(self, "mtp_rescuedMeatlump");
            if (isIdValid(rescuedMeatlump))
            {
                messageTo(rescuedMeatlump, "mtp_handleRescuerAttacked", null, 0.5f, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "mtp_rescuedMeatlump"))
        {
            utils.removeScriptVar(self, "mtp_rescuedMeatlump");
        }
        if (utils.hasScriptVar(self, "mtp_currentInstanceTrigger"))
        {
            utils.removeScriptVar(self, "mtp_currentInstanceTrigger");
        }
        return SCRIPT_CONTINUE;
    }
}
