package script.ai;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.callable;
import script.library.create;
import script.library.utils;

public class dancing_droid extends script.base_script
{
    public dancing_droid()
    {
    }
    public static final string_id SID_DANCE = new string_id("pet/pet_menu", "dance");
    public static final String TRIG_VOLUME = "droid_trig_volume";
    public static final int TRIG_RADIUS = 20;
    public static final boolean PROMISCUOUS = true;
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info item) throws InterruptedException
    {
        if (getMaster(self) != player)
        {
            return SCRIPT_CONTINUE;
        }
        if (!utils.hasScriptVar(self, "music.stillPlaying"))
        {
            item.addRootMenu(menu_info_types.SERVER_MENU1, SID_DANCE);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (getMaster(self) != player)
        {
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.SERVER_MENU1 && !utils.hasScriptVar(self, "music.stillPlaying"))
        {
            location startLocation = getLocation(self);
            obj_id pcd = callable.getCallableCD(self);
            obj_id soundObject = createObject("object/soundobject/soundobject_figrin_dan_band_droid.iff", startLocation);
            String template = getTemplateName(self);
            String droidName = getName(self);
            String[] splitName = split(droidName, '/');
            if (splitName.length > 1)
            {
                droidName = "@" + droidName;
            }
            location holoOneLocation = (location)startLocation.clone();
            boolean goodLocation = false;
            for (int i = 0; i < 10; ++i)
            {
                holoOneLocation = utils.getRandomLocationInRing(startLocation, 1.0f, 2.0f);
                if (isIdValid(holoOneLocation.cell))
                {
                    if (isValidInteriorLocation(holoOneLocation))
                    {
                        goodLocation = true;
                        break;
                    }
                }
                else 
                {
                    goodLocation = true;
                    break;
                }
            }
            obj_id holo_01 = create.object(template, holoOneLocation, false);
            attachScript(holo_01, "ai.dancing_droid");
            setInvulnerable(holo_01, true);
            setHologramType(holo_01, HOLOGRAM_TYPE1_QUALITY3);
            setName(holo_01, droidName);
            location holoTwoLocation = (location)startLocation.clone();
            goodLocation = false;
            for (int i = 0; i < 10; ++i)
            {
                holoTwoLocation = utils.getRandomLocationInRing(startLocation, 1.0f, 2.0f);
                if (isIdValid(holoTwoLocation.cell))
                {
                    if (isValidInteriorLocation(holoTwoLocation) && !holoTwoLocation.equals(holoOneLocation))
                    {
                        goodLocation = true;
                        break;
                    }
                }
                else if (!holoTwoLocation.equals(holoOneLocation))
                {
                    goodLocation = true;
                    break;
                }
            }
            obj_id holo_02 = create.object(template, holoTwoLocation, false);
            attachScript(holo_02, "ai.dancing_droid");
            setInvulnerable(holo_02, true);
            setHologramType(holo_02, HOLOGRAM_TYPE1_QUALITY3);
            setName(holo_02, droidName);
            utils.setScriptVar(self, "music.stillPlaying", 1);
            utils.setScriptVar(holo_01, "music.stillPlaying", 1);
            utils.setScriptVar(holo_02, "music.stillPlaying", 1);
            utils.setScriptVar(self, "dancingDroid.startLocation", startLocation);
            utils.setScriptVar(holo_01, "dancingDroid.startLocation", holoOneLocation);
            utils.setScriptVar(holo_02, "dancingDroid.startLocation", holoTwoLocation);
            setObjVar(pcd, "dancingDroid.soundObject", soundObject);
            setObjVar(pcd, "dancingDroid.holo_02", holo_02);
            setObjVar(pcd, "dancingDroid.holo_01", holo_01);
            createTriggerVolume(TRIG_VOLUME, TRIG_RADIUS, PROMISCUOUS);
            messageTo(self, "clearDancingDroidScriptVar", null, 165, false);
            messageTo(self, "moveDancingDroidNewLocation", null, 1, false);
            messageTo(holo_01, "moveDancingDroidNewLocation", null, 2, false);
            messageTo(holo_02, "moveDancingDroidNewLocation", null, 3, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id petControlDevice = callable.getCallableCD(self);
        if (isIdValid(petControlDevice))
        {
            if (hasObjVar(petControlDevice, "dancingDroid.soundObject"))
            {
                obj_id soundObject = getObjIdObjVar(petControlDevice, "dancingDroid.soundObject");
                dictionary dict = new dictionary();
                dict.put("pcd", petControlDevice);
                messageTo(soundObject, "destroyDancingDroidSoundObject", dict, 0, true);
            }
            obj_id pcd = callable.getCallableCD(self);
            obj_id holo_01 = getObjIdObjVar(pcd, "dancingDroid.holo_01");
            obj_id holo_02 = getObjIdObjVar(pcd, "dancingDroid.holo_02");
            utils.removeScriptVar(self, "music.stillPlaying");
            removeObjVar(pcd, "dancingDroid.holo_01");
            removeObjVar(pcd, "dancingDroid.holo_02");
            destroyObject(holo_01);
            destroyObject(holo_02);
            removeTriggerVolume(TRIG_VOLUME);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeExited(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        if (volumeName.equals(TRIG_VOLUME))
        {
            if (isPlayer(breacher) && getMaster(self) == breacher)
            {
                obj_id pcd = callable.getCallableCD(self);
                obj_id soundObject = getObjIdObjVar(pcd, "dancingDroid.soundObject");
                obj_id holo_01 = getObjIdObjVar(pcd, "dancingDroid.holo_01");
                obj_id holo_02 = getObjIdObjVar(pcd, "dancingDroid.holo_02");
                utils.removeScriptVar(self, "music.stillPlaying");
                removeObjVar(pcd, "dancingDroid.soundObject");
                removeObjVar(pcd, "dancingDroid.holo_01");
                removeObjVar(pcd, "dancingDroid.holo_02");
                destroyObject(soundObject);
                destroyObject(holo_01);
                destroyObject(holo_02);
                removeTriggerVolume(TRIG_VOLUME);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int clearDancingDroidScriptVar(obj_id self, dictionary params) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "music.stillPlaying"))
        {
            obj_id pcd = callable.getCallableCD(self);
            obj_id soundObject = getObjIdObjVar(pcd, "dancingDroid.soundObject");
            obj_id holo_01 = getObjIdObjVar(pcd, "dancingDroid.holo_01");
            obj_id holo_02 = getObjIdObjVar(pcd, "dancingDroid.holo_02");
            utils.removeScriptVar(self, "music.stillPlaying");
            removeObjVar(pcd, "dancingDroid.holo_01");
            removeObjVar(pcd, "dancingDroid.holo_02");
            removeObjVar(pcd, "dancingDroid.soundObject");
            destroyObject(soundObject);
            destroyObject(holo_01);
            destroyObject(holo_02);
            removeTriggerVolume(TRIG_VOLUME);
        }
        return SCRIPT_CONTINUE;
    }
    public int moveDancingDroidStartLocation(obj_id self, dictionary params) throws InterruptedException
    {
        location startLocation = getLocation(self);
        if (utils.hasScriptVar(self, "dancingDroid.startLocation"))
        {
            startLocation = utils.getLocationScriptVar(self, "dancingDroid.startLocation");
        }
        pathTo(self, startLocation);
        if (utils.hasScriptVar(self, "music.stillPlaying"))
        {
            messageTo(self, "moveDancingDroidNewLocation", null, 3, false);
        }
        else 
        {
            utils.removeScriptVar(self, "dancingDroid.startLocation");
        }
        return SCRIPT_CONTINUE;
    }
    public int moveDancingDroidNewLocation(obj_id self, dictionary params) throws InterruptedException
    {
        location startLocation = getLocation(self);
        if (utils.hasScriptVar(self, "dancingDroid.startLocation"))
        {
            startLocation = utils.getLocationScriptVar(self, "dancingDroid.startLocation");
        }
        if (utils.hasScriptVar(self, "music.stillPlaying"))
        {
            location moveToLoc = (location)startLocation.clone();
            boolean goodLocation = false;
            for (int i = 0; i < 10; ++i)
            {
                moveToLoc = utils.getRandomLocationInRing(startLocation, 1.0f, 2.0f);
                if (isIdValid(moveToLoc.cell))
                {
                    if (isValidInteriorLocation(moveToLoc))
                    {
                        goodLocation = true;
                        break;
                    }
                }
                else 
                {
                    goodLocation = true;
                    break;
                }
            }
            pathTo(self, moveToLoc);
        }
        messageTo(self, "moveDancingDroidStartLocation", null, 2, false);
        return SCRIPT_CONTINUE;
    }
    public int destroyDancingDroidSoundObject(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id pcd = params.getObjId("pcd");
        if (destroyObject(self))
        {
            messageTo(pcd, "dancingDroidSoundObjectDestroyed", null, 1, false);
        }
        return SCRIPT_CONTINUE;
    }
}
