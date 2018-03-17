package script.systems.skills.performance;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.performance;
import script.library.utils;

public class instrument extends script.base_script
{
    public instrument()
    {
    }
    public static final string_id SID_PLAY_INSTRUMENT = new string_id("radial_performance", "play_instrument");
    public static final string_id SID_STOP_PLAYING = new string_id("radial_performance", "stop_playing");
    public static final string_id SID_MUSIC_MUST_UNEQUIP = new string_id("performance", "music_must_unequip");
    public static final string_id SID_MUSIC_TOO_FAR_AWAY = new string_id("performance", "music_too_far_away");
    public static final float INSTRUMENT_CHECK_TIME = 5.0f;
    public static final float MAX_INSTRUMENT_DISTANCE = 3.0f;
    public boolean isActiveInstrument(obj_id player) throws InterruptedException
    {
        obj_id self = getSelf();
        if (getInstrumentAudioId(player) == 0)
        {
            return false;
        }
        obj_id obj = getObjectInSlot(player, "hold_r");
        if (isIdValid(obj) && hasScript(obj, "systems.skills.performance.instrument"))
        {
            return (obj == getSelf());
        }
        if (getFirstParentInWorld(self) == player)
        {
            return false;
        }
        return true;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (isActiveInstrument(player) && hasScript(player, performance.MUSIC_HEARTBEAT_SCRIPT))
        {
            mi.addRootMenu(menu_info_types.ITEM_USE, SID_STOP_PLAYING);
        }
        else if (getFirstParentInWorld(self) == player && hasObjVar(self, "instrument.unequippable"))
        {
            mi.addRootMenu(menu_info_types.ITEM_USE, new string_id("", ""));
        }
        else if (isActiveInstrument(player) || hasObjVar(self, "instrument.unequippable"))
        {
            mi.addRootMenu(menu_info_types.ITEM_USE, SID_PLAY_INSTRUMENT);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        boolean isActive = isActiveInstrument(player);
        boolean isInWorld = getFirstParentInWorld(self) != player;
        boolean isPlaying = hasScript(player, performance.MUSIC_HEARTBEAT_SCRIPT);
        boolean isUnequippable = hasObjVar(self, "instrument.unequippable");
        boolean isOwnedByActivatingPlayer = false;
        if (hasObjVar(self, "instrument.owner") && getObjIdObjVar(self, "instrument.owner") == player)
        {
            isOwnedByActivatingPlayer = true;
        }
        else if (getOwner(self) == player)
        {
            isOwnedByActivatingPlayer = true;
        }
        boolean isStatic = self.getValue() < 10000000;
        if (item == menu_info_types.ITEM_USE)
        {
            if (isPlaying)
            {
                queueCommand(player, (1242387165), null, "", COMMAND_PRIORITY_DEFAULT);
            }
            else if (isActive && !isPlaying)
            {
                if (isInWorld && isUnequippable)
                {
                    if (isOwnedByActivatingPlayer)
                    {
                        setLocation(self, player);
                        setYaw(self, getYaw(player));
                    }
                    else if (isStatic)
                    {
                        setLocation(player, self);
                        setYaw(player, getYaw(self));
                    }
                }
                queueCommand(player, (-573445903), null, "", COMMAND_PRIORITY_DEFAULT);
            }
            else if (isUnequippable && !isInWorld)
            {
                if (hasObjVar(self, "instrument.placed"))
                {
                    obj_id oldPlacedObj = getObjIdObjVar(self, "instrument.placed");
                    destroyObject(oldPlacedObj);
                    removeObjVar(self, "instrument.placed");
                }
                obj_id newPlacedObj = createObject(getTemplateName(self), getLocation(player));
                custom_var[] customVars = getAllCustomVars(self);
                if (customVars != null && customVars.length > 0)
                {
                    for (int i = 0; i < customVars.length; i++)
                    {
                        ranged_int_custom_var ricv = (ranged_int_custom_var)customVars[i];
                        String var = ricv.getVarName();
                        int value = ricv.getValue();
                        if (value != 0)
                        {
                            setRangedIntCustomVarValue(newPlacedObj, var, value);
                        }
                    }
                }
                if (isIdValid(newPlacedObj))
                {
                    location where = getLocation(newPlacedObj);
                    setYaw(newPlacedObj, getYaw(player));
                    setOwner(newPlacedObj, player);
                    setObjVar(newPlacedObj, "instrument.owner", player);
                    setObjVar(self, "instrument.placed", newPlacedObj);
                    utils.setScriptVar(newPlacedObj, "instrument.loc", where);
                    messageTo(newPlacedObj, "checkInstrumentCleanup", null, INSTRUMENT_CHECK_TIME, false);
                    messageTo(newPlacedObj, "checkLocation", null, 1, false);
                }
            }
            else if (getDistance(self, player) > 2.0f)
            {
                sendSystemMessage(player, SID_MUSIC_TOO_FAR_AWAY);
            }
            else 
            {
                sendSystemMessage(player, SID_MUSIC_MUST_UNEQUIP);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToBeTransferred(obj_id self, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        if (hasObjVar(self, "instrument.owner"))
        {
            destroyObject(self);
            return SCRIPT_OVERRIDE;
        }
        if (hasObjVar(self, "instrument.placed"))
        {
            obj_id placedObj = getObjIdObjVar(self, "instrument.placed");
            destroyObject(placedObj);
        }
        return SCRIPT_CONTINUE;
    }
    public int checkLocation(obj_id self, dictionary params) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "instrument.loc"))
        {
            location where = getLocationObjVar(self, "instrument.loc");
            if (where == null || !where.equals(getLocation(self)))
            {
                if (hasObjVar(self, "instrument.owner"))
                {
                    obj_id owner = getObjIdObjVar(self, "instrument.owner");
                    if (isIdValid(owner) && exists(owner))
                    {
                        setLocation(self, getLocation(owner));
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int checkInstrumentCleanup(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "instrument.owner"))
        {
            obj_id owner = getObjIdObjVar(self, "instrument.owner");
            if (isIdValid(owner) && exists(owner) && (getLocation(self)).distance(getLocation(owner)) < MAX_INSTRUMENT_DISTANCE && getContainedBy(owner) == getContainedBy(self))
            {
                messageTo(self, "checkInstrumentCleanup", null, INSTRUMENT_CHECK_TIME, false);
                return SCRIPT_CONTINUE;
            }
        }
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
