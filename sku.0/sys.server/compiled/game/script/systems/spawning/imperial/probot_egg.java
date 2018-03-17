package script.systems.spawning.imperial;

import script.dictionary;
import script.library.attrib;
import script.library.create;
import script.library.pet_lib;
import script.library.utils;
import script.obj_id;

public class probot_egg extends script.base_script
{
    public probot_egg()
    {
    }
    public static final float VOL_SPAWN_RANGE = 80f;
    public static final String VOL_SPAWN = "volSpawnProbot";
    public static final float VOL_EFFECT_RANGE = 100f;
    public static final String VOL_EFFECT = "volEffect";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        createTriggerVolume(VOL_EFFECT, VOL_EFFECT_RANGE, true);
        createTriggerVolume(VOL_SPAWN, VOL_SPAWN_RANGE, true);
        setAttributeInterested(self, attrib.ALL);
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volName, obj_id who) throws InterruptedException
    {
        if (who == self)
        {
            return SCRIPT_CONTINUE;
        }
        if (pet_lib.isPet(who))
        {
            obj_id master = getMaster(who);
            if (isIdValid(master))
            {
                who = master;
            }
        }
        if (isPlayer(who))
        {
            if (volName.equals(VOL_EFFECT) && !utils.hasScriptVar(self, "playedEffect"))
            {
                utils.setScriptVar(self, "playedEffect", true);
                playClientEffectLoc(who, "clienteffect/probot_delivery.cef", getLocation(self), 0f);
                removeTriggerVolume(VOL_EFFECT);
                messageTo(self, "handleSpawnDebris", null, 3f, false);
            }
            else if (volName.equals(VOL_SPAWN))
            {
                dictionary params = new dictionary();
                params.put("target", who);
                messageTo(self, "handleSpawnProbot", params, 1f, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleSpawnProbot(obj_id self, dictionary params) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, "spawnedDebris"))
        {
            int numCycle = params.getInt("cycle");
            if (numCycle > 5)
            {
                destroyObject(self);
                return SCRIPT_CONTINUE;
            }
            params.put("cycle", ++numCycle);
            messageTo(self, "handleSpawnProbot", params, 1f, false);
            return SCRIPT_CONTINUE;
        }
        obj_id probot = create.object("imperial_probot_drone", getLocation(self));
        if (isIdValid(probot))
		{
			attachScript(probot, "ai.override_behavior.scout");
			messageTo(probot, "handleTargetAssignment", params, 1f, false);
		}
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int handleSpawnDebris(obj_id self, dictionary params) throws InterruptedException
    {
        utils.setScriptVar(self, "spawnedDebris", true);
        return SCRIPT_CONTINUE;
    }
}
