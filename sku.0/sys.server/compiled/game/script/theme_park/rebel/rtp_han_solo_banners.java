package script.theme_park.rebel;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.ai_lib;
import script.library.spawning;
import script.library.create;

public class rtp_han_solo_banners extends script.base_script
{
    public rtp_han_solo_banners()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "imperialBannerSpawned"))
        {
            removeObjVar(self, "imperialBannerSpawned");
        }
        if (hasObjVar(self, "rebelBannerSpawned"))
        {
            removeObjVar(self, "rebelBannerSpawned");
        }
        if (canSpawnByConfigSetting())
        {
            messageTo(self, "doImperialBannerEvent", null, 10, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "imperialBannerSpawned"))
        {
            removeObjVar(self, "imperialBannerSpawned");
        }
        if (hasObjVar(self, "rebelBannerSpawned"))
        {
            removeObjVar(self, "rebelBannerSpawned");
        }
        if (canSpawnByConfigSetting())
        {
            messageTo(self, "doImperialBannerEvent", null, 20, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int doImperialBannerEvent(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, "imperialBannerSpawned"))
        {
            String imperialBanner = "object/tangible/quest/rebel/rtp_hansolo_imperial_banner.iff";
            location here = getLocation(self);
            obj_id objTemplate = createObject(imperialBanner, here);
            setObjVar(objTemplate, "objParent", self);
            setObjVar(self, "imperialBannerSpawned", true);
            float spawnerYaw = getYaw(self);
            setYaw(objTemplate, spawnerYaw);
        }
        return SCRIPT_CONTINUE;
    }
    public int doRebelBannerEvent(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, "rebelBannerSpawned"))
        {
            String rebelBanner = "object/tangible/quest/rebel/rtp_hansolo_rebel_banner.iff";
            location here = getLocation(self);
            obj_id objTemplate = createObject(rebelBanner, here);
            setObjVar(objTemplate, "objParent", self);
            setObjVar(self, "rebelBannerSpawned", true);
            float spawnerYaw = getYaw(self);
            setYaw(objTemplate, spawnerYaw);
        }
        return SCRIPT_CONTINUE;
    }
    public int spawnDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "rebelBannerSpawned"))
        {
            removeObjVar(self, "rebelBannerSpawned");
            messageTo(self, "doImperialBannerEvent", null, 0, false);
        }
        else if (hasObjVar(self, "imperialBannerSpawned"))
        {
            removeObjVar(self, "imperialBannerSpawned");
            messageTo(self, "doRebelBannerEvent", null, 0, false);
        }
        return SCRIPT_CONTINUE;
    }
    public boolean canSpawnByConfigSetting() throws InterruptedException
    {
        String disableSpawners = getConfigSetting("GameServer", "disableAreaSpawners");
        if (disableSpawners == null)
        {
            return true;
        }
        if (disableSpawners.equals("true") || disableSpawners.equals("1"))
        {
            return false;
        }
        return true;
    }
}
