package script.systems.gcw;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.ai_lib;
import script.library.utils;
import script.library.gcw;
import script.library.locations;

public class flip_banner extends script.base_script
{
    public flip_banner()
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
        else if ((reb_r > imp_r))
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
        if (empiredayRunning != null && (empiredayRunning.equals("true") || empiredayRunning.equals("1")))
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
        transform t = getTransform_o2w(self);
        if (faction.equals("rebel"))
        {
            t = t.move_l(new vector(0, 0, 2));
        }
        obj_id banner = createObject("object/tangible/gcw/flip_banner_" + faction + ".iff", t, null);
        if (banner == null || !isIdValid(banner))
        {
            return;
        }
        setObjVar(banner, "spawner", self);
        utils.setScriptVar(self, "banner", banner);
        return;
    }
}
