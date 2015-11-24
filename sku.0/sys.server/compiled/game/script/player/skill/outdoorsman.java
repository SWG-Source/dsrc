package script.player.skill;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.permissions;
import script.library.corpse;
import script.library.prose;

public class outdoorsman extends script.base_script
{
    public outdoorsman()
    {
    }
    public static final string_id PROSE_HARVEST_FAILED = new string_id("error_message", "prose_harvest_corpse_failed");
    public static final string_id SID_HARVEST_FAILED = new string_id("error_message", "harvest_corpse_failed");
    public static final string_id SID_NO_RESOURCE = new string_id("error_message", "no_resource");
    public int cmdHarvestCorpse(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!isIdValid(target) || params == null)
        {
            return SCRIPT_CONTINUE;
        }
        boolean canOpen = corpse.hasLootPermissions(target, self);
        if (!canOpen)
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(target, corpse.VAR_HAS_RESOURCE))
        {
            return SCRIPT_CONTINUE;
        }
        boolean found = false;
        int[] hasResource = getIntArrayObjVar(target, corpse.VAR_HAS_RESOURCE);
        if (params.equals("meat"))
        {
            if (hasResource[corpse.CCR_MEAT] > 0)
            {
                found = true;
            }
        }
        else if (params.equals("hide"))
        {
            if (hasResource[corpse.CCR_HIDE] > 0)
            {
                found = true;
            }
        }
        else if (params.equals("bone"))
        {
            if (hasResource[corpse.CCR_BONE] > 0)
            {
                found = true;
            }
        }
        else if (params.equals(""))
        {
            found = true;
        }
        if (!found)
        {
            sendSystemMessage(self, SID_NO_RESOURCE);
            return SCRIPT_CONTINUE;
        }
        dictionary outparams = new dictionary();
        outparams.put("player", self);
        outparams.put("args", params);
        messageTo(target, "harvestCorpse", outparams, 0.f, false);
        return SCRIPT_CONTINUE;
    }
    public int cmdHarvestCorpseFail(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (target == null)
        {
            sendSystemMessage(self, SID_HARVEST_FAILED);
        }
        else 
        {
            prose_package pp = prose.getPackage(PROSE_HARVEST_FAILED, target);
            sendSystemMessageProse(self, pp);
        }
        return SCRIPT_CONTINUE;
    }
}
