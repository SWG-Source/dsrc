package script.systems.gcw;

import script.dictionary;
import script.library.gcw;
import script.library.locations;
import script.library.trial;
import script.library.utils;
import script.location;
import script.obj_id;
import script.transform;

public class flip_banner_onpole extends script.systems.gcw.flip_banner
{
    public flip_banner_onpole()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "checkBannerImpulse", null, 1.f, false);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "checkBannerImpulse", null, 1.f, false);
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "banner"))
        {
            obj_id banner = utils.getObjIdScriptVar(self, "banner");
            if (isIdValid(banner))
            {
                destroyObject(banner);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnUnloadedFromMemory(obj_id self) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "banner"))
        {
            obj_id banner = utils.getObjIdScriptVar(self, "banner");
            if (isIdValid(banner))
            {
                destroyObject(banner);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int checkBannerImpulse(obj_id self, dictionary params) throws InterruptedException
    {
        float imp_r = gcw.getImperialPercentileByRegion(self);
        float reb_r = gcw.getRebelPercentileByRegion(self);
        if ((imp_r > reb_r))
        {
            utils.setScriptVar(self, "faction", 1);
            obj_id banner = utils.getObjIdScriptVar(self, "banner");
            if (isIdValid(banner))
            {
                destroyObject(banner);
            }
            spawnBanner(self, "imperial");
        }
        else if ((reb_r >= imp_r))
        {
            utils.setScriptVar(self, "faction", 2);
            obj_id banner = utils.getObjIdScriptVar(self, "banner");
            if (isIdValid(banner))
            {
                destroyObject(banner);
            }
            spawnBanner(self, "rebel");
        }
        messageTo(self, "checkBannerImpulse", null, 3600.f, false);
        return SCRIPT_CONTINUE;
    }
    public void spawnBanner(obj_id self, String faction) throws InterruptedException
    {
        String empiredayRunning = getConfigSetting("GameServer", "empireday_ceremony");
        if (empiredayRunning != null)
        {
            if (empiredayRunning.equals("true") || empiredayRunning.equals("1"))
            {
                location here = getLocation(self);
                String city = locations.getCityName(here);
                if (city == null)
                {
                    city = locations.getGuardSpawnerRegionName(here);
                }
                if (city != null && city.length() > 0)
                {
                    if (city.equals("coronet"))
                    {
                        faction = "rebel";
                    }
                    else if (city.equals("theed"))
                    {
                        faction = "imperial";
                    }
                }
            }
        }
        transform t = getTransform_o2w(self);
        obj_id banner = createObject("object/tangible/gcw/flip_banner_onpole_" + faction + ".iff", t, null);
        if (!isIdValid(banner))
        {
            return;
        }
        obj_id kitParent = trial.getParent(self);
        if (isIdValid(kitParent) && exists(kitParent))
        {
            trial.setParent(kitParent, banner, true);
            trial.markAsTempObject(banner, true);
            setObjVar(banner, "spawn_id", getStringObjVar(self, "spawn_id"));
        }
        setObjVar(banner, "spawner", self);
        utils.setScriptVar(self, "banner", banner);
        return;
    }
}
