package script.quest.util;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;
import script.library.utils;

public class retrieve_item_rotate_north extends script.base_script
{
    public retrieve_item_rotate_north()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleRecordOriginalRotation", null, 3, false);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleRecordOriginalRotation", null, 3, false);
        return SCRIPT_CONTINUE;
    }
    public int handleRecordOriginalRotation(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, "orignalYaw"))
        {
            float originalYaw = getYaw(self);
            setObjVar(self, "orignalYaw", originalYaw);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        location here = getLocation(player);
        location term = getLocation(self);
        float dist = getDistance(here, term);
        if (item == menu_info_types.ITEM_USE)
        {
            if (isDead(player) || isIncapacitated(player) || dist > 5.0)
            {
                return SCRIPT_CONTINUE;
            }
            if (hasScript(player, "quest.task.ground.retrieve_item") || hasScript(player, "quest.task.ground.wave_event_player"))
            {
                if (groundquests.playerNeedsToRetrieveThisItem(player, self, "retrieve_item") || groundquests.playerNeedsToRetrieveThisItem(player, self, "wave_event"))
                {
                    if (utils.hasScriptVar(self, "waveEventCurrentWave"))
                    {
                        int wave = utils.getIntScriptVar(self, "waveEventCurrentWave");
                        if (wave > 0)
                        {
                            return SCRIPT_CONTINUE;
                        }
                    }
                    if (hasObjVar(self, "orignalYaw"))
                    {
                        if (!utils.hasScriptVar(self, "alreadyRotated"))
                        {
                            obj_id[] players = getPlayerCreaturesInRange(getLocation(self), 40.0f);
                            if (players != null && players.length > 0)
                            {
                                playClientEffectObj(players, "sound/item_heavy_wood_door_open.snd", self, "");
                            }
                            setYaw(self, 0.0f);
                            utils.setScriptVar(self, "alreadyRotated", 1);
                            messageTo(self, "handleRestoreOriginalRotation", null, 9, false);
                        }
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleRestoreOriginalRotation(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "orignalYaw"))
        {
            float originalYaw = getFloatObjVar(self, "orignalYaw");
            obj_id[] players = getPlayerCreaturesInRange(getLocation(self), 40.0f);
            if (players != null && players.length > 0)
            {
                playClientEffectObj(players, "sound/item_heavy_wood_door_close.snd", self, "");
            }
            utils.removeScriptVar(self, "alreadyRotated");
            setYaw(self, originalYaw);
        }
        return SCRIPT_CONTINUE;
    }
}
