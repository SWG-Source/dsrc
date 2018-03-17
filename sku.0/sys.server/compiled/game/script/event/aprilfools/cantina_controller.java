package script.event.aprilfools;

import script.dictionary;
import script.library.ai_lib;
import script.library.chat;
import script.library.create;
import script.library.utils;
import script.location;
import script.obj_id;
import script.string_id;

public class cantina_controller extends script.base_script
{
    public static final float THIRTY_SIX_HOURS = 60 * 60 * 36;

    private String[] types = {"ewok1", "ewok2", "ewok3", "ewok4", "foil1"};

    private String[] robberyEventNpcOrder = {"foil1", "ewok1", "ewok2", "ewok3", "foil1", "ewok1", "foil1", "ewok1"};
    private String[] robberyEventLineOrder = {"foil1_2", "ewok1_3", "ewok2_4", "ewok3_5", "foil1_6", "ewok1_7", "foil1_8", "ewok1_9"};
    private String[] robberyEventMoodOrder = {chat.MOOD_CONDESCENDING, chat.MOOD_HUNGRY, chat.MOOD_GLOOMY, chat.MOOD_FRUSTRATED, chat.MOOD_ANGRY, chat.MOOD_HOPEFUL, chat.MOOD_PUZZLED, chat.MOOD_PUZZLED};

    private String[] bartenderEventNpcOrder = {"foil1", "ewok1", "foil1", "ewok1", "foil1", "ewok1", "ewok1", "foil1"};
    private String[] bartenderLineOrder = {"bar_line_2", "bar_line_3", "bar_line_4", "bar_line_5", "bar_line_6", "bar_line_7", "bar_line_8", "bar_line_9"};
    private String[] bartenderMoodOrder = {chat.MOOD_CONFUSED, chat.MOOD_CONFUSED, chat.MOOD_ENCOURAGING, chat.MOOD_CONFIDENT, chat.MOOD_RESPECTFUL, chat.MOOD_THANKFUL, chat.MOOD_DISGUSTED, chat.MOOD_WORRIED};

    public cantina_controller()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        String setting = getConfigSetting("GameServer", "foolsDay");
        if (setting == null || setting.equals("") || !setting.equals("true"))
        {
            return SCRIPT_CONTINUE;
        }
        float rightNow = getGameTime();
        if (!hasObjVar(self, "aprilfools.invasion_run_time"))
        {
            float invasionRunTime = rightNow + THIRTY_SIX_HOURS;
            setObjVar(self, "aprilfools.invasion_run_time", invasionRunTime);
        }
        float minInvasionTime = 2700.0f;
        float timeChunkSize = 900.0f;
        float nextInvasionTime = (rand(1, 9) * timeChunkSize) + minInvasionTime + rightNow;
        setObjVar(self, "aprilfools.next_invasion_time", nextInvasionTime);
        setObjVar(self, "aprilfools.invasion_active", 0);
        messageTo(self, "invasionTimerPing", null, 30, false);
        return SCRIPT_CONTINUE;
    }
    public int invasionTimerPing(obj_id self, dictionary params) throws InterruptedException
    {
        float rightNow = getGameTime();
        float invasionRunTime = getFloatObjVar(self, "aprilfools.invasion_run_time");
        if (rightNow > invasionRunTime)
        {
            return SCRIPT_CONTINUE;
        }
        int invasionActive = getIntObjVar(self, "aprilfools.invasion_active");
        if (invasionActive == 1)
        {
            return SCRIPT_CONTINUE;
        }
        float nextInvasionTime = getFloatObjVar(self, "aprilfools.next_invasion_time");
        if (rightNow > nextInvasionTime && invasionActive == 0)
        {
            if (rand(1, 2) == 1)
            {
                messageTo(self, "startRobberyEvent", null, 1, false);
            }
            else
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
            for (obj_id objPlayer : objPlayers) {
                sendSystemMessage(objPlayer, new string_id("aprilfools", messageId));
            }
        }
    }
    public obj_id spawnNpc(obj_id self, String type, location startLocation, String role, float pointX, float pointZ, obj_id cell, dictionary params, boolean moveTo) throws InterruptedException{
        startLocation.x = pointX;
        startLocation.z = pointZ;
        startLocation.cell = cell;
        obj_id npc = create.object(type, startLocation);
        ai_lib.setDefaultCalmBehavior(npc, ai_lib.BEHAVIOR_SENTINEL);
        stop(npc);
        setInvulnerable(npc, true);
        setObjVar(npc, "aprilfools.actor_role", role);
        attachScript(npc, "event.aprilfools.cantina_actor");
        setObjVar(npc, "aprilfools.cantina", self);
        setObjVar(self, "aprilfools." + role, npc);
        if(moveTo) messageTo(npc, "moveToMainActorPosition", params, 10, false);
        return npc;
    }
    public int startRobberyEvent(obj_id self, dictionary params) throws InterruptedException
    {
        location loc = getLocation(self);
        obj_id cantinaCell = getCellId(self, "cantina");
        obj_id foyerCell = getCellId(self, "foyer1");

        spawnNpc(self, "panshee_worker", loc, types[0], 47.0f, 0.0f, foyerCell, params, true);
        spawnNpc(self, "panshee_worker", loc, types[1], 46.0f, 0.0f, foyerCell, params, true);
        spawnNpc(self, "panshee_worker", loc, types[2], 45.0f, 0.0f, foyerCell, params, true);
        spawnNpc(self, "panshee_worker", loc, types[3], 44.0f, 0.0f, foyerCell, params, true);
        spawnNpc(self, "noble", loc, types[4], 18.0f, 0.0f, cantinaCell, params, true);

        params.put("position", 0);

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

        chat.chat(ewok1, chat.CHAT_SAY, chat.MOOD_CURIOUS, new string_id("aprilfools", "ewok1_1"));
        params.put("segment", 0);
        messageTo(self, "robberyEventSegment", params, 8, false);

        return SCRIPT_CONTINUE;
    }

    public int robberyEventSegment(obj_id self, dictionary params) throws InterruptedException
    {
        int segment = params.getInt("segment");
        obj_id npc = getObjIdObjVar(self, "aprilfools." + robberyEventNpcOrder[segment]);
        string_id myLine = new string_id("aprilfools", robberyEventLineOrder[segment]);
        chat.chat(npc, chat.CHAT_SAY, robberyEventMoodOrder[segment], myLine);
        if(segment < 7) {
            params.put("segment", segment + 1);
            messageTo(self, "robberyEventSegment", params, 6, false);
        }
        else{
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
            params.put("position", cycle);

            messageTo(ewok1, "becomeVulnerable", null, 1, false);
            messageTo(ewok1, "moveToMainActorPosition", params, 1, false);
            messageTo(foil1, "moveToMainActorPosition", params, 2, false);
            messageTo(ewok2, "moveToRandomLocation", null, 1, false);
            messageTo(ewok3, "moveToRandomLocation", null, 1, false);
            messageTo(ewok4, "moveToRandomLocation", null, 1, false);
            messageTo(self, "robberyEventCycle", null, 10, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int robberyEventCycle(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id ewok1 = getObjIdObjVar(self, "aprilfools.ewok1");
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
        obj_id ewok2 = getObjIdObjVar(self, "aprilfools.ewok2");
        obj_id ewok3 = getObjIdObjVar(self, "aprilfools.ewok3");
        obj_id ewok4 = getObjIdObjVar(self, "aprilfools.ewok4");
        obj_id foil1 = getObjIdObjVar(self, "aprilfools.foil1");

        params.put("position", cycle);

        messageTo(ewok1, "moveToMainActorPosition", params, 1, false);
        messageTo(foil1, "moveToMainActorPosition", params, 2, false);
        messageTo(ewok2, "moveToRandomLocation", null, 1, false);
        messageTo(ewok3, "moveToRandomLocation", null, 1, false);
        messageTo(ewok4, "moveToRandomLocation", null, 1, false);

        chat.chat(ewok1, chat.CHAT_SAY, chat.MOOD_PANICKED, new string_id("aprilfools", "ewok1_rand_" + rand(1, 7)));
        chat.chat(foil1, chat.CHAT_SAY, chat.MOOD_ENRAGED, new string_id("aprilfools", "foil1_rand_" + rand(1, 7)));

        setObjVar(self, "aprilfools.robbery_cycle", cycle + 1);

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
        location loc = getLocation(self);

        obj_id cantinaCell = getCellId(self, "cantina");
        obj_id foyerCell = getCellId(self, "foyer1");

        obj_id ewok1 = spawnNpc(self, "panshee_worker", loc, types[0], 47.0f, 0.0f, foyerCell, params, false);
        spawnNpc(self, "panshee_worker", loc, types[1], 46.0f, 0.0f, foyerCell, params, false);
        spawnNpc(self, "panshee_worker", loc, types[2], 45.0f, 0.0f, foyerCell, params, false);
        spawnNpc(self, "panshee_worker", loc, types[3], 44.0f, 0.0f, foyerCell, params, false);
        obj_id foil1 = spawnNpc(self, "noble", loc, types[4], 8.4f, 0.0f, cantinaCell, params, false);

        setName(foil1, utils.packStringId(new string_id("aprilfools", "bartender_name")));

        params.put("position", 5);

        messageTo(ewok1, "moveToMainActorPosition", params, 10, false);
        messageTo(foil1, "moveToMainActorPosition", params, 10, false);
        messageTo(self, "moveAllExtrasToRandomLocation", null, 1, false);
        messageTo(self, "startBartenderEventSegments", null, 25, false);

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
    public int startBartenderEventSegments(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id ewok1 = getObjIdObjVar(self, "aprilfools.ewok1");
        messageTo(getObjIdObjVar(self, "aprilfools.foil1"), "faceNorth", null, 1, false);
        messageTo(ewok1, "faceSouth", null, 1, false);
        chat.chat(ewok1, chat.CHAT_SAY, chat.MOOD_CURIOUS, new string_id("aprilfools", "bar_line_1"));
        params.put("segment", 0);
        messageTo(self, "bartenderEventSegment", params, 6, false);
        return SCRIPT_CONTINUE;
    }

    public int bartenderEventSegment(obj_id self, dictionary params) throws InterruptedException
    {
        int segment = params.getInt("segment");
        obj_id npc = getObjIdObjVar(self, "aprilfools." + bartenderEventNpcOrder[segment]);
        string_id myLine = new string_id("aprilfools", bartenderLineOrder[segment]);
        chat.chat(npc, chat.CHAT_SAY, bartenderMoodOrder[segment], myLine);
        if(segment == 0 || segment == 2 || segment == 4 || segment == 7)
            messageTo(self, "moveAllExtrasToRandomLocation", null, 1, false);
        if(segment < 7) {
            params.put("segment", segment + 1);
            messageTo(self, "bartenderEventSegment", params, 6, false);
        }
        else {
            obj_id ewok1 = getObjIdObjVar(self, "aprilfools.ewok1");
            obj_id foil1 = getObjIdObjVar(self, "aprilfools.foil1");

            chat.chat(ewok1, chat.CHAT_SAY, chat.MOOD_SCORNFUL, new string_id("aprilfools", "bar_line_10"));
            chat.chat(foil1, chat.CHAT_SAY, chat.MOOD_ANGRY, new string_id("aprilfools", "bar_line_11"));

            setObjVar(self, "aprilfools.robbery_cycle", 2);

            params.put("position", 2);

            messageTo(ewok1, "becomeVulnerable", null, 1, false);
            messageTo(ewok1, "moveToMainActorPosition", params, 1, false);
            messageTo(foil1, "moveToMainActorPosition", params, 2, false);
            messageTo(self, "moveAllExtrasToRandomLocation", null, 1, false);
            messageTo(self, "bartenderEventCycle", null, 10, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int bartenderEventCycle(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id ewok1 = getObjIdObjVar(self, "aprilfools.ewok1");
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
        obj_id foil1 = getObjIdObjVar(self, "aprilfools.foil1");

        params.put("position", cycle);

        messageTo(ewok1, "moveToMainActorPosition", params, 1, false);
        messageTo(foil1, "moveToMainActorPosition", params, 2, false);
        messageTo(self, "moveAllExtrasToRandomLocation", null, 1, false);

        chat.chat(ewok1, chat.CHAT_SAY, chat.MOOD_PANICKED, new string_id("aprilfools", "ewok1_rand_" + rand(1, 7)));
        chat.chat(foil1, chat.CHAT_SAY, chat.MOOD_ENRAGED, new string_id("aprilfools", "bar_foil1_rand_" + rand(1, 7)));

        setObjVar(self, "aprilfools.robbery_cycle", cycle + 1);

        messageTo(self, "bartenderEventCycle", null, 16, false);
        return SCRIPT_CONTINUE;
    }
    public int OnHearSpeech(obj_id self, obj_id objSpeaker, String strText) throws InterruptedException
    {
        if (isGod(objSpeaker))
        {
            if (strText.equals("forcecantinaevent"))
            {
                if (rand(1, 2) == 1)
                {
                    messageTo(self, "startRobberyEvent", null, 1, false);
                }
                else
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
