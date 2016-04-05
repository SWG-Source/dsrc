package script.event.bh_event;

import script.dictionary;
import script.library.create;
import script.library.utils;
import script.location;
import script.obj_id;
import script.string_id;

public class cantina_controller extends script.base_script
{
    public cantina_controller()
    {
    }
    public static final float TWENTY_FOUR_HOURS = 60 * 60 * 24;
    public static final String STF_FILE = "event/bhcelebs";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleBosskEventInitiation", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int handleBosskEventInitiation(obj_id self, dictionary params) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, "bhcelebs.bosskEventInitialized"))
        {
            if (!hasObjVar(self, "bhcelebs.next_invasion_time"))
            {
                float minInvasionTime = TWENTY_FOUR_HOURS;
                float timeChunkSize = 900.0f;
                int numTimeChunk = 192;
                float nextInvasionTime = (rand(1, numTimeChunk) * timeChunkSize) + minInvasionTime + getGameTime();
                setObjVar(self, "bhcelebs.next_invasion_time", nextInvasionTime);
            }
            setObjVar(self, "bhcelebs.invasion_active", 0);
            utils.setScriptVar(self, "bhcelebs.bosskEventInitialized", true);
            messageTo(self, "invasionTimerPing", null, 30, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int invasionTimerPing(obj_id self, dictionary params) throws InterruptedException
    {
        int invasionActive = getIntObjVar(self, "bhcelebs.invasion_active");
        if (invasionActive == 1)
        {
            return SCRIPT_CONTINUE;
        }
        float rightNow = getGameTime();
        float nextInvasionTime = getFloatObjVar(self, "bhcelebs.next_invasion_time");
        if (rightNow > nextInvasionTime && invasionActive == 0)
        {
            messageTo(self, "startBosskEvent", null, 1, false);
            setObjVar(self, "bhcelebs.invasion_active", 1);
            announceStatusToPlayers(self, "bossk_zone_emote");
            return SCRIPT_CONTINUE;
        }
        else 
        {
            messageTo(self, "invasionTimerPing", null, 3600, false);
        }
        return SCRIPT_CONTINUE;
    }
    public void announceStatusToPlayers(obj_id self, String messageId) throws InterruptedException
    {
        obj_id[] objPlayers = getPlayerCreaturesInRange(self, 64.0f);
        if (objPlayers != null && objPlayers.length > 0)
        {
            for (obj_id objPlayer : objPlayers) {
                sendSystemMessage(objPlayer, new string_id(STF_FILE, messageId));
            }
        }
    }
    public int startBosskEvent(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "bhcelebs.bossk"))
        {
            obj_id bossk = getObjIdObjVar(self, "bhcelebs.bossk");
            if (isIdValid(bossk))
            {
                destroyObject(bossk);
                removeObjVar(self, "bhcelebs.bossk");
            }
        }
        if (utils.hasScriptVar(self, "bhcelebs.beingDestroyed"))
        {
            utils.removeScriptVar(self, "bhcelebs.beingDestroyed");
        }
        obj_id bossk = create.object("bossk", new location(43.8f, .1f, 0.5f, getLocation(self).area, getCellId(self, "foyer1")));
        if (isIdValid(bossk))
        {
            setInvulnerable(bossk, true);
            setObjVar(bossk, "bhcelebs.actor_role", "bossk");
            attachScript(bossk, "event.bh_event.cantina_actor");
            setObjVar(bossk, "bhcelebs.cantina", self);
            dictionary webster = new dictionary();
            webster.put("locationTargetName", "startBosskLine1");
            messageTo(bossk, "moveToMainActorPosition", webster, 1, false);
            setObjVar(self, "bhcelebs.bossk", bossk);
            messageTo(self, "bosskEventEnd", null, 2700, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int bosskEventEnd(obj_id self, dictionary params) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "bhcelebs.beingDestroyed"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id bossk = getObjIdObjVar(self, "bhcelebs.bossk");
        setObjVar(self, "bhcelebs.invasion_active", 0);
        setObjVar(bossk, "bhcelebs.run_away", 1);
        messageTo(bossk, "runAwayAndPoof", null, 1, false);
        float minInvasionTime = TWENTY_FOUR_HOURS;
        float timeChunkSize = 900.0f;
        int numTimeChunk = 192;
        float rightNow = getGameTime();
        float nextInvasionTime = (rand(1, numTimeChunk) * timeChunkSize) + minInvasionTime + rightNow;
        setObjVar(self, "bhcelebs.next_invasion_time", nextInvasionTime);
        messageTo(self, "invasionTimerPing", null, TWENTY_FOUR_HOURS, false);
        return SCRIPT_CONTINUE;
    }
    public int OnHearSpeech(obj_id self, obj_id objSpeaker, String strText) throws InterruptedException
    {
        if (isGod(objSpeaker))
        {
            if (strText.equals("forcebosskevent"))
            {
                int invasionActive = getIntObjVar(self, "bhcelebs.invasion_active");
                if (invasionActive == 0)
                {
                    messageTo(self, "startBosskEvent", null, 1, false);
                    setObjVar(self, "bhcelebs.invasion_active", 1);
                    announceStatusToPlayers(self, "bossk_zone_emote");
                }
                else 
                {
                    sendSystemMessage(objSpeaker, "The Bossk cantina event is already active. If you need to restart it, try cleanupbosskevent first.", "");
                }
            }
            if (strText.equals("cleanupbosskevent"))
            {
                int invasionActive = getIntObjVar(self, "bhcelebs.invasion_active");
                if (invasionActive == 1)
                {
                    if (hasObjVar(self, "bhcelebs.bossk"))
                    {
                        obj_id bossk = getObjIdObjVar(self, "bhcelebs.bossk");
                        if (isIdValid(bossk))
                        {
                            setObjVar(bossk, "bhcelebs.forcedCleanup", true);
                            messageTo(self, "bosskEventEnd", null, 1, false);
                        }
                    }
                }
                else 
                {
                    sendSystemMessage(objSpeaker, "The Bossk cantina event is already inactive.", "");
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
