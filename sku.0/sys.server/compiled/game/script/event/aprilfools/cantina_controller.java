package script.event.aprilfools;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.ai_lib;
import script.library.create;
import script.library.chat;

public class cantina_controller extends script.base_script
{
    public cantina_controller()
    {
    }
    public static final float THIRTY_SIX_HOURS = 60 * 60 * 36;
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        String setting = getConfigSetting("EventTeam", "aprilfools");
        if (setting == null || setting.equals("") || !setting.equals("true"))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, "aprilfools.invasion_run_time"))
        {
            float rightNow = getGameTime();
            float invasionRunTime = rightNow + THIRTY_SIX_HOURS;
            setObjVar(self, "aprilfools.invasion_run_time", invasionRunTime);
        }
        float minInvasionTime = 2700.0f;
        float timeChunkSize = 900.0f;
        int numTimeChunk = 9;
        float rightNow = getGameTime();
        float nextInvasionTime = (rand(1, numTimeChunk) * timeChunkSize) + minInvasionTime + rightNow;
        setObjVar(self, "aprilfools.next_invasion_time", nextInvasionTime);
        setObjVar(self, "aprilfools.invasion_active", 0);
        messageTo(self, "invasionTimerPing", null, 30, false);
        return SCRIPT_CONTINUE;
    }
    public int invasionTimerPing(obj_id self, dictionary params) throws InterruptedException
    {
        float rightNow = getGameTime();
        float nextInvasionTime = getFloatObjVar(self, "aprilfools.next_invasion_time");
        int invasionActive = getIntObjVar(self, "aprilfools.invasion_active");
        float invasionRunTime = getFloatObjVar(self, "aprilfools.invasion_run_time");
        if (rightNow > invasionRunTime)
        {
            return SCRIPT_CONTINUE;
        }
        if (invasionActive == 1)
        {
            return SCRIPT_CONTINUE;
        }
        if (rightNow > nextInvasionTime && invasionActive == 0)
        {
            int coinFlip = rand(1, 2);
            if (coinFlip == 1)
            {
                messageTo(self, "startRobberyEvent", null, 1, false);
            }
            if (coinFlip == 2)
            {
                messageTo(self, "startBartenderEvent", null, 1, false);
            }
            setObjVar(self, "aprilfools.invasion_active", 1);
            announceStatusToPlayers(self, "cantina_zone_emote");
            return SCRIPT_CONTINUE;
        }
        else 
        {
            messageTo(self, "invasionTimerPing", null, 900, false);
        }
        return SCRIPT_CONTINUE;
    }
    public void announceStatusToPlayers(obj_id self, String messageId) throws InterruptedException
    {
        obj_id[] objPlayers = getPlayerCreaturesInRange(self, 64.0f);
        if (objPlayers != null && objPlayers.length > 0)
        {
            for (int i = 0; i < objPlayers.length; i++)
            {
                sendSystemMessage(objPlayers[i], new string_id("aprilfools", messageId));
            }
        }
    }
    public int startRobberyEvent(obj_id self, dictionary params) throws InterruptedException
    {
        location ewok1Start = getLocation(self);
        location ewok2Start = getLocation(self);
        location ewok3Start = getLocation(self);
        location ewok4Start = getLocation(self);
        location foil1Start = getLocation(self);
        obj_id cantinaCell = getCellId(self, "cantina");
        obj_id foyerCell = getCellId(self, "foyer1");
        ewok1Start.x = 47.0f;
        ewok2Start.x = 46.0f;
        ewok3Start.x = 45.0f;
        ewok4Start.x = 44.0f;
        ewok1Start.z = 0.0f;
        ewok2Start.z = 0.0f;
        ewok3Start.z = 0.0f;
        ewok4Start.z = 0.0f;
        ewok1Start.cell = foyerCell;
        ewok2Start.cell = foyerCell;
        ewok3Start.cell = foyerCell;
        ewok4Start.cell = foyerCell;
        foil1Start.x = 18.0f;
        foil1Start.z = 0.0f;
        foil1Start.cell = cantinaCell;
        obj_id ewok1 = create.object("panshee_worker", ewok1Start);
        ai_lib.setDefaultCalmBehavior(ewok1, ai_lib.BEHAVIOR_SENTINEL);
        stop(ewok1);
        setInvulnerable(ewok1, true);
        setObjVar(ewok1, "aprilfools.actor_role", "ewok1");
        attachScript(ewok1, "event.aprilfools.cantina_actor");
        setObjVar(ewok1, "aprilfools.cantina", self);
        obj_id ewok2 = create.object("panshee_worker", ewok2Start);
        ai_lib.setDefaultCalmBehavior(ewok2, ai_lib.BEHAVIOR_SENTINEL);
        stop(ewok2);
        setInvulnerable(ewok2, true);
        setObjVar(ewok2, "aprilfools.actor_role", "ewok2");
        attachScript(ewok2, "event.aprilfools.cantina_actor");
        setObjVar(ewok2, "aprilfools.cantina", self);
        obj_id ewok3 = create.object("panshee_worker", ewok3Start);
        ai_lib.setDefaultCalmBehavior(ewok3, ai_lib.BEHAVIOR_SENTINEL);
        stop(ewok3);
        setInvulnerable(ewok3, true);
        setObjVar(ewok3, "aprilfools.actor_role", "ewok3");
        attachScript(ewok3, "event.aprilfools.cantina_actor");
        setObjVar(ewok3, "aprilfools.cantina", self);
        obj_id ewok4 = create.object("panshee_worker", ewok4Start);
        ai_lib.setDefaultCalmBehavior(ewok4, ai_lib.BEHAVIOR_SENTINEL);
        stop(ewok4);
        setInvulnerable(ewok4, true);
        setObjVar(ewok4, "aprilfools.actor_role", "ewok4");
        attachScript(ewok4, "event.aprilfools.cantina_actor");
        setObjVar(ewok4, "aprilfools.cantina", self);
        obj_id foil1 = create.object("noble", foil1Start);
        ai_lib.setDefaultCalmBehavior(foil1, ai_lib.BEHAVIOR_SENTINEL);
        stop(foil1);
        setInvulnerable(foil1, true);
        setObjVar(foil1, "aprilfools.actor_role", "foil1");
        attachScript(foil1, "event.aprilfools.cantina_actor");
        setObjVar(foil1, "aprilfools.cantina", self);
        setObjVar(self, "aprilfools.ewok1", ewok1);
        setObjVar(self, "aprilfools.ewok2", ewok2);
        setObjVar(self, "aprilfools.ewok3", ewok3);
        setObjVar(self, "aprilfools.ewok4", ewok4);
        setObjVar(self, "aprilfools.foil1", foil1);
        int position = 0;
        params.put("position", position);
        messageTo(ewok1, "moveToMainActorPosition", params, 10, false);
        messageTo(ewok2, "moveToMainActorPosition", params, 10, false);
        messageTo(ewok3, "moveToMainActorPosition", params, 10, false);
        messageTo(ewok4, "moveToMainActorPosition", params, 10, false);
        messageTo(foil1, "moveToMainActorPosition", params, 10, false);
        messageTo(self, "robberyEventSegment1", null, 25, false);
        return SCRIPT_CONTINUE;
    }
    public int robberyEventSegment1(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id ewok1 = getObjIdObjVar(self, "aprilfools.ewok1");
        obj_id ewok2 = getObjIdObjVar(self, "aprilfools.ewok2");
        obj_id ewok3 = getObjIdObjVar(self, "aprilfools.ewok3");
        obj_id ewok4 = getObjIdObjVar(self, "aprilfools.ewok4");
        obj_id foil1 = getObjIdObjVar(self, "aprilfools.foil1");
        messageTo(ewok1, "faceSouth", null, 1, false);
        messageTo(ewok2, "faceEast", null, 1, false);
        messageTo(ewok3, "faceWest", null, 1, false);
        messageTo(ewok4, "faceNorth", null, 1, false);
        messageTo(foil1, "faceNorth", null, 1, false);
        string_id myLine = new string_id("aprilfools", "ewok1_1");
        chat.chat(ewok1, chat.CHAT_SAY, chat.MOOD_CURIOUS, myLine);
        messageTo(self, "robberyEventSegment2", null, 8, false);
        return SCRIPT_CONTINUE;
    }
    public int robberyEventSegment2(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id foil1 = getObjIdObjVar(self, "aprilfools.foil1");
        string_id myLine = new string_id("aprilfools", "foil1_2");
        chat.chat(foil1, chat.CHAT_SAY, chat.MOOD_CONDESCENDING, myLine);
        messageTo(self, "robberyEventSegment3", null, 6, false);
        return SCRIPT_CONTINUE;
    }
    public int robberyEventSegment3(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id ewok1 = getObjIdObjVar(self, "aprilfools.ewok1");
        string_id myLine = new string_id("aprilfools", "ewok1_3");
        chat.chat(ewok1, chat.CHAT_SAY, chat.MOOD_HUNGRY, myLine);
        messageTo(self, "robberyEventSegment4", null, 6, false);
        return SCRIPT_CONTINUE;
    }
    public int robberyEventSegment4(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id ewok2 = getObjIdObjVar(self, "aprilfools.ewok2");
        string_id myLine = new string_id("aprilfools", "ewok2_4");
        chat.chat(ewok2, chat.CHAT_SAY, chat.MOOD_GLOOMY, myLine);
        messageTo(self, "robberyEventSegment5", null, 6, false);
        return SCRIPT_CONTINUE;
    }
    public int robberyEventSegment5(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id ewok3 = getObjIdObjVar(self, "aprilfools.ewok3");
        string_id myLine = new string_id("aprilfools", "ewok3_5");
        chat.chat(ewok3, chat.CHAT_SAY, chat.MOOD_FRUSTRATED, myLine);
        messageTo(self, "robberyEventSegment6", null, 6, false);
        return SCRIPT_CONTINUE;
    }
    public int robberyEventSegment6(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id foil1 = getObjIdObjVar(self, "aprilfools.foil1");
        string_id myLine = new string_id("aprilfools", "foil1_6");
        chat.chat(foil1, chat.CHAT_SAY, chat.MOOD_ANGRY, myLine);
        messageTo(self, "robberyEventSegment7", null, 6, false);
        return SCRIPT_CONTINUE;
    }
    public int robberyEventSegment7(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id ewok1 = getObjIdObjVar(self, "aprilfools.ewok1");
        string_id myLine = new string_id("aprilfools", "ewok1_7");
        chat.chat(ewok1, chat.CHAT_SAY, chat.MOOD_HOPEFUL, myLine);
        messageTo(self, "robberyEventSegment8", null, 6, false);
        return SCRIPT_CONTINUE;
    }
    public int robberyEventSegment8(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id foil1 = getObjIdObjVar(self, "aprilfools.foil1");
        string_id myLine = new string_id("aprilfools", "foil1_8");
        chat.chat(foil1, chat.CHAT_SAY, chat.MOOD_PUZZLED, myLine);
        messageTo(self, "robberyEventSegment9", null, 6, false);
        return SCRIPT_CONTINUE;
    }
    public int robberyEventSegment9(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id ewok1 = getObjIdObjVar(self, "aprilfools.ewok1");
        string_id myLine = new string_id("aprilfools", "ewok1_9");
        chat.chat(ewok1, chat.CHAT_SAY, chat.MOOD_HAPPY, myLine);
        messageTo(self, "robberyEventSegment10", null, 6, false);
        return SCRIPT_CONTINUE;
    }
    public int robberyEventSegment10(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id ewok1 = getObjIdObjVar(self, "aprilfools.ewok1");
        obj_id ewok2 = getObjIdObjVar(self, "aprilfools.ewok2");
        obj_id ewok3 = getObjIdObjVar(self, "aprilfools.ewok3");
        obj_id ewok4 = getObjIdObjVar(self, "aprilfools.ewok4");
        obj_id foil1 = getObjIdObjVar(self, "aprilfools.foil1");
        string_id ewok1Line = new string_id("aprilfools", "ewok1_10");
        string_id ewok3Line = new string_id("aprilfools", "ewok3_10");
        string_id foil1Line = new string_id("aprilfools", "foil1_10");
        chat.chat(ewok1, chat.CHAT_SAY, chat.MOOD_PANICKED, ewok1Line);
        chat.chat(ewok3, chat.CHAT_SAY, chat.MOOD_PANICKED, ewok3Line);
        chat.chat(foil1, chat.CHAT_SAY, chat.MOOD_ENRAGED, foil1Line);
        int cycle = 2;
        setObjVar(self, "aprilfools.robbery_cycle", cycle);
        int position = cycle;
        params.put("position", position);
        messageTo(ewok1, "becomeVulnerable", null, 1, false);
        messageTo(ewok1, "moveToMainActorPosition", params, 1, false);
        messageTo(foil1, "moveToMainActorPosition", params, 2, false);
        messageTo(ewok2, "moveToRandomLocation", null, 1, false);
        messageTo(ewok3, "moveToRandomLocation", null, 1, false);
        messageTo(ewok4, "moveToRandomLocation", null, 1, false);
        messageTo(self, "robberyEventCycle", null, 10, false);
        return SCRIPT_CONTINUE;
    }
    public int robberyEventCycle(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id ewok1 = getObjIdObjVar(self, "aprilfools.ewok1");
        obj_id ewok2 = getObjIdObjVar(self, "aprilfools.ewok2");
        obj_id ewok3 = getObjIdObjVar(self, "aprilfools.ewok3");
        obj_id ewok4 = getObjIdObjVar(self, "aprilfools.ewok4");
        obj_id foil1 = getObjIdObjVar(self, "aprilfools.foil1");
        int cycle = getIntObjVar(self, "aprilfools.robbery_cycle");
        if (!isIdValid(ewok1))
        {
            cycle = 999;
        }
        if (cycle == 999)
        {
            messageTo(self, "concludeRobberyEvent", null, 1, false);
            return SCRIPT_CONTINUE;
        }
        if (cycle == 5)
        {
            cycle = 1;
        }
        int position = cycle;
        params.put("position", position);
        messageTo(ewok1, "moveToMainActorPosition", params, 1, false);
        messageTo(foil1, "moveToMainActorPosition", params, 2, false);
        messageTo(ewok2, "moveToRandomLocation", null, 1, false);
        messageTo(ewok3, "moveToRandomLocation", null, 1, false);
        messageTo(ewok4, "moveToRandomLocation", null, 1, false);
        int randEwokPhrase = rand(1, 7);
        int randFoilPhrase = rand(1, 7);
        string_id ewok1Line = new string_id("aprilfools", "ewok1_rand_" + randEwokPhrase);
        string_id foil1Line = new string_id("aprilfools", "foil1_rand_" + randFoilPhrase);
        chat.chat(ewok1, chat.CHAT_SAY, chat.MOOD_PANICKED, ewok1Line);
        chat.chat(foil1, chat.CHAT_SAY, chat.MOOD_ENRAGED, foil1Line);
        cycle++;
        setObjVar(self, "aprilfools.robbery_cycle", cycle);
        messageTo(self, "robberyEventCycle", null, 16, false);
        return SCRIPT_CONTINUE;
    }
    public int concludeRobberyEvent(obj_id self, dictionary params) throws InterruptedException
    {
        setObjVar(self, "aprilfools.invasion_active", 0);
        obj_id ewok2 = getObjIdObjVar(self, "aprilfools.ewok2");
        obj_id ewok3 = getObjIdObjVar(self, "aprilfools.ewok3");
        obj_id ewok4 = getObjIdObjVar(self, "aprilfools.ewok4");
        obj_id foil1 = getObjIdObjVar(self, "aprilfools.foil1");
        setObjVar(self, "aprilfools.robbery_cycle", 999);
        string_id ewokLine = new string_id("aprilfools", "ewok3_10");
        string_id foilLine = new string_id("aprilfools", "foil1_finally");
        messageTo(ewok2, "runAwayAndPoof", null, 1, false);
        chat.chat(ewok2, chat.CHAT_SAY, chat.MOOD_PANICKED, ewokLine);
        messageTo(ewok3, "runAwayAndPoof", null, 1, false);
        chat.chat(ewok3, chat.CHAT_SAY, chat.MOOD_PANICKED, ewokLine);
        messageTo(ewok4, "runAwayAndPoof", null, 1, false);
        chat.chat(ewok4, chat.CHAT_SAY, chat.MOOD_PANICKED, ewokLine);
        messageTo(foil1, "goDie", null, 60, false);
        chat.chat(foil1, chat.CHAT_SAY, chat.MOOD_ANNOYED, foilLine);
        float minInvasionTime = 2700.0f;
        float timeChunkSize = 900.0f;
        int numTimeChunk = 9;
        float rightNow = getGameTime();
        float nextInvasionTime = (rand(1, numTimeChunk) * timeChunkSize) + minInvasionTime + rightNow;
        setObjVar(self, "aprilfools.next_invasion_time", nextInvasionTime);
        messageTo(self, "invasionTimerPing", null, 2700, false);
        return SCRIPT_CONTINUE;
    }
    public int startBartenderEvent(obj_id self, dictionary params) throws InterruptedException
    {
        location ewok1Start = getLocation(self);
        location ewok2Start = getLocation(self);
        location ewok3Start = getLocation(self);
        location ewok4Start = getLocation(self);
        location foil1Start = getLocation(self);
        obj_id cantinaCell = getCellId(self, "cantina");
        obj_id foyerCell = getCellId(self, "foyer1");
        ewok1Start.x = 47.0f;
        ewok2Start.x = 46.0f;
        ewok3Start.x = 45.0f;
        ewok4Start.x = 44.0f;
        ewok1Start.z = 0.0f;
        ewok2Start.z = 0.0f;
        ewok3Start.z = 0.0f;
        ewok4Start.z = 0.0f;
        ewok1Start.cell = foyerCell;
        ewok2Start.cell = foyerCell;
        ewok3Start.cell = foyerCell;
        ewok4Start.cell = foyerCell;
        foil1Start.x = 8.4f;
        foil1Start.z = 0.4f;
        foil1Start.cell = cantinaCell;
        obj_id ewok1 = create.object("panshee_worker", ewok1Start);
        ai_lib.setDefaultCalmBehavior(ewok1, ai_lib.BEHAVIOR_SENTINEL);
        stop(ewok1);
        setInvulnerable(ewok1, true);
        setObjVar(ewok1, "aprilfools.actor_role", "ewok1");
        attachScript(ewok1, "event.aprilfools.cantina_actor");
        setObjVar(ewok1, "aprilfools.cantina", self);
        obj_id ewok2 = create.object("panshee_worker", ewok2Start);
        ai_lib.setDefaultCalmBehavior(ewok2, ai_lib.BEHAVIOR_SENTINEL);
        stop(ewok2);
        setInvulnerable(ewok2, true);
        setObjVar(ewok2, "aprilfools.actor_role", "ewok2");
        attachScript(ewok2, "event.aprilfools.cantina_actor");
        setObjVar(ewok2, "aprilfools.cantina", self);
        obj_id ewok3 = create.object("panshee_worker", ewok3Start);
        ai_lib.setDefaultCalmBehavior(ewok3, ai_lib.BEHAVIOR_SENTINEL);
        stop(ewok3);
        setInvulnerable(ewok3, true);
        setObjVar(ewok3, "aprilfools.actor_role", "ewok3");
        attachScript(ewok3, "event.aprilfools.cantina_actor");
        setObjVar(ewok3, "aprilfools.cantina", self);
        obj_id ewok4 = create.object("panshee_worker", ewok4Start);
        ai_lib.setDefaultCalmBehavior(ewok4, ai_lib.BEHAVIOR_SENTINEL);
        stop(ewok4);
        setInvulnerable(ewok4, true);
        setObjVar(ewok4, "aprilfools.actor_role", "ewok4");
        attachScript(ewok4, "event.aprilfools.cantina_actor");
        setObjVar(ewok4, "aprilfools.cantina", self);
        obj_id foil1 = create.object("noble", foil1Start);
        ai_lib.setDefaultCalmBehavior(foil1, ai_lib.BEHAVIOR_SENTINEL);
        stop(foil1);
        setInvulnerable(foil1, true);
        setObjVar(foil1, "aprilfools.actor_role", "foil1");
        attachScript(foil1, "event.aprilfools.cantina_actor");
        setObjVar(foil1, "aprilfools.cantina", self);
        String bartenderName = utils.packStringId(new string_id("aprilfools", "bartender_name"));
        setName(foil1, bartenderName);
        setObjVar(self, "aprilfools.ewok1", ewok1);
        setObjVar(self, "aprilfools.ewok2", ewok2);
        setObjVar(self, "aprilfools.ewok3", ewok3);
        setObjVar(self, "aprilfools.ewok4", ewok4);
        setObjVar(self, "aprilfools.foil1", foil1);
        int position = 5;
        params.put("position", position);
        messageTo(ewok1, "moveToMainActorPosition", params, 10, false);
        messageTo(foil1, "moveToMainActorPosition", params, 10, false);
        messageTo(self, "moveAllExtrasToRandomLocation", null, 1, false);
        messageTo(self, "bartenderEventSegment1", null, 25, false);
        return SCRIPT_CONTINUE;
    }
    public int moveAllExtrasToRandomLocation(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id ewok2 = getObjIdObjVar(self, "aprilfools.ewok2");
        obj_id ewok3 = getObjIdObjVar(self, "aprilfools.ewok3");
        obj_id ewok4 = getObjIdObjVar(self, "aprilfools.ewok4");
        messageTo(ewok2, "moveToRandomLocation", null, 10, false);
        messageTo(ewok3, "moveToRandomLocation", null, 10, false);
        messageTo(ewok4, "moveToRandomLocation", null, 10, false);
        return SCRIPT_CONTINUE;
    }
    public int bartenderEventSegment1(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id foil1 = getObjIdObjVar(self, "aprilfools.foil1");
        obj_id ewok1 = getObjIdObjVar(self, "aprilfools.ewok1");
        messageTo(foil1, "faceNorth", null, 1, false);
        messageTo(ewok1, "faceSouth", null, 1, false);
        string_id myLine = new string_id("aprilfools", "bar_line_1");
        chat.chat(ewok1, chat.CHAT_SAY, chat.MOOD_CURIOUS, myLine);
        messageTo(self, "bartenderEventSegment2", null, 6, false);
        return SCRIPT_CONTINUE;
    }
    public int bartenderEventSegment2(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id foil1 = getObjIdObjVar(self, "aprilfools.foil1");
        string_id myLine = new string_id("aprilfools", "bar_line_2");
        chat.chat(foil1, chat.CHAT_SAY, chat.MOOD_CONFUSED, myLine);
        messageTo(self, "bartenderEventSegment3", null, 6, false);
        messageTo(self, "moveAllExtrasToRandomLocation", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int bartenderEventSegment3(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id ewok1 = getObjIdObjVar(self, "aprilfools.ewok1");
        string_id myLine = new string_id("aprilfools", "bar_line_3");
        chat.chat(ewok1, chat.CHAT_SAY, chat.MOOD_CONFUSED, myLine);
        messageTo(self, "bartenderEventSegment4", null, 6, false);
        return SCRIPT_CONTINUE;
    }
    public int bartenderEventSegment4(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id foil1 = getObjIdObjVar(self, "aprilfools.foil1");
        string_id myLine = new string_id("aprilfools", "bar_line_4");
        chat.chat(foil1, chat.CHAT_SAY, chat.MOOD_ENCOURAGING, myLine);
        messageTo(self, "bartenderEventSegment5", null, 6, false);
        messageTo(self, "moveAllExtrasToRandomLocation", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int bartenderEventSegment5(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id ewok1 = getObjIdObjVar(self, "aprilfools.ewok1");
        string_id myLine = new string_id("aprilfools", "bar_line_5");
        chat.chat(ewok1, chat.CHAT_SAY, chat.MOOD_CONFIDENT, myLine);
        messageTo(self, "bartenderEventSegment6", null, 6, false);
        return SCRIPT_CONTINUE;
    }
    public int bartenderEventSegment6(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id foil1 = getObjIdObjVar(self, "aprilfools.foil1");
        string_id myLine = new string_id("aprilfools", "bar_line_6");
        chat.chat(foil1, chat.CHAT_SAY, chat.MOOD_RESPECTFUL, myLine);
        messageTo(self, "bartenderEventSegment7", null, 6, false);
        messageTo(self, "moveAllExtrasToRandomLocation", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int bartenderEventSegment7(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id ewok1 = getObjIdObjVar(self, "aprilfools.ewok1");
        string_id myLine = new string_id("aprilfools", "bar_line_7");
        chat.chat(ewok1, chat.CHAT_SAY, chat.MOOD_THANKFUL, myLine);
        messageTo(self, "bartenderEventSegment8", null, 6, false);
        return SCRIPT_CONTINUE;
    }
    public int bartenderEventSegment8(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id ewok1 = getObjIdObjVar(self, "aprilfools.ewok1");
        string_id myLine = new string_id("aprilfools", "bar_line_8");
        chat.chat(ewok1, chat.CHAT_SAY, chat.MOOD_DISGUSTED, myLine);
        messageTo(self, "bartenderEventSegment9", null, 6, false);
        return SCRIPT_CONTINUE;
    }
    public int bartenderEventSegment9(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id foil1 = getObjIdObjVar(self, "aprilfools.foil1");
        string_id myLine = new string_id("aprilfools", "bar_line_9");
        chat.chat(foil1, chat.CHAT_SAY, chat.MOOD_WORRIED, myLine);
        messageTo(self, "bartenderEventSegment10", null, 6, false);
        messageTo(self, "moveAllExtrasToRandomLocation", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int bartenderEventSegment10(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id ewok1 = getObjIdObjVar(self, "aprilfools.ewok1");
        obj_id foil1 = getObjIdObjVar(self, "aprilfools.foil1");
        string_id ewokLine = new string_id("aprilfools", "bar_line_10");
        string_id foilLine = new string_id("aprilfools", "bar_line_11");
        chat.chat(ewok1, chat.CHAT_SAY, chat.MOOD_SCORNFUL, ewokLine);
        chat.chat(foil1, chat.CHAT_SAY, chat.MOOD_ANGRY, foilLine);
        int cycle = 2;
        setObjVar(self, "aprilfools.robbery_cycle", cycle);
        int position = cycle;
        params.put("position", position);
        messageTo(ewok1, "becomeVulnerable", null, 1, false);
        messageTo(ewok1, "moveToMainActorPosition", params, 1, false);
        messageTo(foil1, "moveToMainActorPosition", params, 2, false);
        messageTo(self, "moveAllExtrasToRandomLocation", null, 1, false);
        messageTo(self, "bartenderEventCycle", null, 10, false);
        return SCRIPT_CONTINUE;
    }
    public int bartenderEventCycle(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id ewok1 = getObjIdObjVar(self, "aprilfools.ewok1");
        obj_id foil1 = getObjIdObjVar(self, "aprilfools.foil1");
        int cycle = getIntObjVar(self, "aprilfools.robbery_cycle");
        if (!isIdValid(ewok1))
        {
            cycle = 999;
        }
        if (cycle == 999)
        {
            messageTo(self, "concludeRobberyEvent", null, 1, false);
            return SCRIPT_CONTINUE;
        }
        if (cycle == 5)
        {
            cycle = 1;
        }
        int position = cycle;
        params.put("position", position);
        messageTo(ewok1, "moveToMainActorPosition", params, 1, false);
        messageTo(foil1, "moveToMainActorPosition", params, 2, false);
        messageTo(self, "moveAllExtrasToRandomLocation", null, 1, false);
        int randEwokPhrase = rand(1, 7);
        int randFoilPhrase = rand(1, 7);
        string_id ewok1Line = new string_id("aprilfools", "ewok1_rand_" + randEwokPhrase);
        string_id foil1Line = new string_id("aprilfools", "bar_foil1_rand_" + randFoilPhrase);
        chat.chat(ewok1, chat.CHAT_SAY, chat.MOOD_PANICKED, ewok1Line);
        chat.chat(foil1, chat.CHAT_SAY, chat.MOOD_ENRAGED, foil1Line);
        cycle++;
        setObjVar(self, "aprilfools.robbery_cycle", cycle);
        messageTo(self, "bartenderEventCycle", null, 16, false);
        return SCRIPT_CONTINUE;
    }
    public int OnHearSpeech(obj_id self, obj_id objSpeaker, String strText) throws InterruptedException
    {
        if (isGod(objSpeaker))
        {
            if (strText.equals("forcecantinaevent"))
            {
                int coinFlip = rand(1, 2);
                if (coinFlip == 1)
                {
                    messageTo(self, "startRobberyEvent", null, 1, false);
                }
                if (coinFlip == 2)
                {
                    messageTo(self, "startBartenderEvent", null, 1, false);
                }
                setObjVar(self, "aprilfools.invasion_active", 1);
                announceStatusToPlayers(self, "cantina_zone_emote");
            }
        }
        return SCRIPT_CONTINUE;
    }
}
