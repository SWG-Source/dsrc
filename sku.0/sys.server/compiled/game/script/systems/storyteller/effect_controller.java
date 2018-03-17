package script.systems.storyteller;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.static_item;
import script.library.storyteller;
import script.library.create;
import script.library.trial;

public class effect_controller extends script.base_script
{
    public effect_controller()
    {
    }
    public static final String invisibleObject = "object/tangible/theme_park/invisible_object.iff";
    public static final String effectControlleScript = "systems.storyteller.effect_controller";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "handlePlayEffect", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        trial.cleanupObject(self);
        return SCRIPT_CONTINUE;
    }
    public int handlePlayEffect(obj_id self, dictionary params) throws InterruptedException
    {
        playEffect(self);
        return SCRIPT_CONTINUE;
    }
    public void playEffect(obj_id self) throws InterruptedException
    {
        String storytellerTokenName = getStringObjVar(self, storyteller.EFFECT_TOKEN_NAME);
        location here = getLocation(self);
        String effectName = storyteller.getEffectName(storytellerTokenName);
        obj_id[] playersInStory = getPlayersInRangeInStory(self, here);
        if (playersInStory != null || playersInStory.length > 0)
        {
            if (storyteller.isImmediateEffect(storytellerTokenName))
            {
                if ((getTemplateName(self)).equals(invisibleObject))
                {
                    playClientEffectLoc(playersInStory, effectName, here, 0.0f);
                    float cleanup_time = getStandardCleanupTime(self);
                    messageTo(self, "cleanupEffectController", null, cleanup_time, false);
                }
                else 
                {
                    playClientEffectObj(playersInStory, effectName, self, "");
                }
            }
            else 
            {
                playClientEffectObj(playersInStory, effectName, self, "", null, "storyteller_persisted_effect");
                setObjVar(self, storyteller.EFFECT_ACTIVE_OBJVAR, effectName);
                createTriggerVolume("storytellerPersistedEffect_far", 100f, true);
            }
        }
        return;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        if (!isIdValid(breacher) || !isPlayer(breacher))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, storyteller.EFFECT_ACTIVE_OBJVAR))
        {
            return SCRIPT_CONTINUE;
        }
        if (!volumeName.startsWith("storytellerPersistedEffect"))
        {
            return SCRIPT_CONTINUE;
        }
        String effectName = getStringObjVar(self, storyteller.EFFECT_ACTIVE_OBJVAR);
        if (effectName != null || effectName.length() > 0)
        {
            if (storyteller.allowedToSeeStorytellerObject(self, breacher))
            {
                obj_id[] playerInStory = 
                {
                    breacher
                };
                stopClientEffectObjByLabel(playerInStory, self, "storyteller_persisted_effect", false);
                playClientEffectObj(playerInStory, effectName, self, "", null, "storyteller_persisted_effect");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handlePlayNewStorytellerEffect(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] playersInStory = getPlayersInRangeInStory(self, getLocation(self));
        stopClientEffectObjByLabel(playersInStory, self, "storyteller_persisted_effect", false);
        removeObjVar(self, storyteller.EFFECT_ACTIVE_OBJVAR);
        removeTriggerVolume("storytellerPersistedEffect");
        playEffect(self);
        return SCRIPT_CONTINUE;
    }
    public float getStandardCleanupTime(obj_id effect_controller) throws InterruptedException
    {
        return 30.0f;
    }
    public int cleanupEffectController(obj_id self, dictionary params) throws InterruptedException
    {
        trial.cleanupObject(self);
        return SCRIPT_CONTINUE;
    }
    public obj_id[] getPlayersInRangeInStory(obj_id self, location here) throws InterruptedException
    {
        Vector playerInStory = new Vector();
        playerInStory.setSize(0);
        obj_id[] players = getPlayerCreaturesInRange(here, 165.0f);
        if (players == null || players.length == 0)
        {
            return null;
        }
        for (int i = 0; i < players.length; i++)
        {
            obj_id player = players[i];
            if (storyteller.allowedToSeeStorytellerObject(self, player))
            {
                utils.addElement(playerInStory, player);
            }
        }
        obj_id[] _playerInStory = new obj_id[0];
        if (playerInStory != null)
        {
            _playerInStory = new obj_id[playerInStory.size()];
            playerInStory.toArray(_playerInStory);
        }
        return _playerInStory;
    }
}
