package script.theme_park.meatlump.hideout;

import script.library.groundquests;
import script.library.utils;
import script.location;
import script.obj_id;

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
