package script.theme_park.dungeon.mustafar_trials.obiwan_finale;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.mustafar;
import script.library.utils;

public class obiwan_dynamic_timeout extends script.base_script
{
    public obiwan_dynamic_timeout()
    {
    }
    public static final String TRIGGER_VOLUME_OBI = "obiwan_interest_volume";
    public static final float OBI_INTEREST_RADIUS = 7f;
    public static final boolean CONST_FLAG_DO_LOGGING = false;
    public static final int OBIWAN_DESPAWN_DELAY = 150;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, "player"))
        {
            detachScript(self, "theme_park.dungeon.mustafar_trials.obiwan_finale.obiwan_dynamic_timeout");
        }
        else 
        {
            obiwanTriggerVolumeInitializer(self);
            messageTo(self, "obiwanTimeoutDelay", null, OBIWAN_DESPAWN_DELAY, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "obiwanTimeoutDelay", null, OBIWAN_DESPAWN_DELAY, false);
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        if (isPlayer(breacher) && !isIncapacitated(breacher))
        {
            if (volumeName.equals(TRIGGER_VOLUME_OBI))
            {
                if (utils.hasScriptVar(self, "player"))
                {
                    obj_id player = utils.getObjIdScriptVar(self, "player");
                    if (breacher == player)
                    {
                        utils.setScriptVar(self, "approachedByPlayer", 1);
                        messageTo(self, "despawnObiwanDelay", null, 120, false);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void debugLogging(String section, String message) throws InterruptedException
    {
        if (CONST_FLAG_DO_LOGGING)
        {
            LOG("debug/obiwan_dynamic_timeout/" + section, message);
        }
    }
    public void obiwanTriggerVolumeInitializer(obj_id self) throws InterruptedException
    {
        if (!hasTriggerVolume(self, TRIGGER_VOLUME_OBI))
        {
            createTriggerVolume(TRIGGER_VOLUME_OBI, OBI_INTEREST_RADIUS, true);
        }
        else 
        {
            obj_id[] triggerVolumeDenizens = getTriggerVolumeContents(self, TRIGGER_VOLUME_OBI);
            for (int i = 0; i < triggerVolumeDenizens.length; i++)
            {
                if (isPlayer(triggerVolumeDenizens[i]) && !isIncapacitated(triggerVolumeDenizens[i]))
                {
                    if (utils.hasScriptVar(self, "player"))
                    {
                        obj_id player = utils.getObjIdScriptVar(self, "player");
                        if (triggerVolumeDenizens[i] == player)
                        {
                            utils.setScriptVar(self, "approachedByPlayer", 1);
                            messageTo(self, "despawnObiwanDelay", null, 120, false);
                        }
                    }
                    return;
                }
            }
        }
        return;
    }
    public int obiwanTimeoutDelay(obj_id self, dictionary params) throws InterruptedException
    {
        debugLogging("obiwanTimeoutDelay: ", " entered.");
        if (!utils.hasScriptVar(self, "approachedByPlayer"))
        {
            destroyObject(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int despawnObiwanDelay(obj_id self, dictionary params) throws InterruptedException
    {
        debugLogging("despawnObiwanDelay: ", " entered.");
        if (isInNpcConversation(self))
        {
            messageTo(self, "despawnObiwanDelay", null, OBIWAN_DESPAWN_DELAY, false);
        }
        else 
        {
            setCreatureCover(self, 125);
            setCreatureCoverVisibility(self, false);
            playClientEffectObj(self, "clienteffect/combat_special_attacker_cover.cef", self, "");
            destroyObject(self);
        }
        return SCRIPT_CONTINUE;
    }
}
