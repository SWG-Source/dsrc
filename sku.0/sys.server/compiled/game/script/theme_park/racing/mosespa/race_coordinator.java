package script.theme_park.racing.mosespa;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.ai_lib;
import script.library.locations;
import script.library.colors;
import script.library.sui;
import script.library.badge;
import script.library.prose;
import script.library.chat;

public class race_coordinator extends script.base_script
{
    public race_coordinator()
    {
    }
    public static final String RACING_STF = "theme_park/racing/racing";
    public static final int[] CHECK_POINT = 
    {
        1943,
        0,
        4792,
        1546,
        0,
        4959,
        1316,
        0,
        5434,
        688,
        0,
        5439,
        160,
        0,
        5306,
        -414,
        0,
        5090,
        -664,
        0,
        4832,
        -769,
        0,
        4340,
        -710,
        0,
        3993,
        81,
        0,
        4092,
        594,
        0,
        4284,
        917,
        0,
        3841,
        1333,
        0,
        4457,
        1630,
        0,
        4326,
        2132,
        0,
        4346,
        2371,
        0,
        4546
    };
    public int OnAttach(obj_id self) throws InterruptedException
    {
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        attachScript(self, "conversation.racing_mosespa");
        float topRecord = 925.0f;
        setObjVar(self, "racing.mosespa.topRecord", topRecord);
        String topRecordName = "Ben Quadinaros";
        setObjVar(self, "racing.mosespa.topRecordName", topRecordName);
        messageTo(self, "resetDailyTime", null, 60 * 60 * 24, false);
        return SCRIPT_CONTINUE;
    }
    public int resetDailyTime(obj_id self, dictionary params) throws InterruptedException
    {
        float topRecord = 925.0f;
        setObjVar(self, "racing.mosespa.topRecord", topRecord);
        String topRecordName = "Ben Quadinaros";
        setObjVar(self, "racing.mosespa.topRecordName", topRecordName);
        messageTo(self, "resetDailyTime", null, 60 * 60 * 24, false);
        return SCRIPT_CONTINUE;
    }
    public int messageStartMission(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        obj_id npc = params.getObjId("npc");
        int missionNum = 0;
        float startTime = getGameTime();
        int isRacing = 1;
        int maxCheckPoints = CHECK_POINT.length / 3;
        for (int i = 0; i < CHECK_POINT.length; i += 3)
        {
            location checkPoint = new location(CHECK_POINT[i], CHECK_POINT[i + 1], CHECK_POINT[i + 2], "tatooine");
            setObjVar(player, "racing.mosespa.checkpoint" + ((i / 3) + 1), checkPoint);
        }
        location returnLoc = new location();
        obj_id building = getTopMostContainer(npc);
        if (building == npc)
        {
            returnLoc = getLocation(npc);
        }
        else 
        {
            returnLoc = getLocation(building);
        }
        setObjVar(player, "racing.mosespa.missionNum", missionNum);
        setObjVar(player, "racing.mosespa.returnLoc", returnLoc);
        setObjVar(player, "racing.mosespa.startTime", startTime);
        setObjVar(player, "racing.mosespa.isRacing", isRacing);
        setObjVar(player, "racing.mosespa.maxCheckPoints", maxCheckPoints);
        if (!hasObjVar(player, "racing.mosespa.currentTime"))
        {
            setObjVar(player, "racing.mosespa.currentTime", 7500.0f);
        }
        if (!hasObjVar(player, "racing.bestTime.mosespa"))
        {
            setObjVar(player, "racing.bestTime.mosespa", 7500.0f);
        }
        if (!hasObjVar(player, "racing.lastTime.mosespa"))
        {
            setObjVar(player, "racing.lastTime.mosespa", 7500.0f);
        }
        if (hasScript(player, "theme_park.racing.mosespa.player_racing"))
        {
            messageTo(player, "handleRestartMission", params, 0, false);
        }
        else 
        {
            attachScript(player, "theme_park.racing.mosespa.player_racing");
        }
        return SCRIPT_CONTINUE;
    }
    public int messageRegisterBest(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        obj_id npc = params.getObjId("npc");
        string_id notGoodEnough = new string_id(RACING_STF, "not_good_enough");
        float bestTime = getFloatObjVar(player, "racing.lastTime.mosespa");
        float topRecord = getFloatObjVar(npc, "racing.mosespa.topRecord");
        string_id beatTheRecord = new string_id(RACING_STF, "beat_the_record");
        string_id beatTheRecordFly = new string_id(RACING_STF, "beat_record_fly");
        string_id tiedTheRecord = new string_id(RACING_STF, "tied_the_record");
        string_id stillHoldRecord = new string_id(RACING_STF, "still_hold_record");
        string_id lastTime = new string_id(RACING_STF, "last_time");
        String playerName = getName(player);
        String topRecordHolder = getStringObjVar(self, "racing.mosespa.topRecordName");
        prose_package announcement = new prose_package();
        announcement = prose.getPackage(lastTime, playerName + " " + bestTime);
        removeObjVar(player, "racing.lastTime.mosespa");
        if (bestTime <= topRecord)
        {
            if (bestTime > 0.0f)
            {
                if (!badge.hasBadge(player, "bdg_racing_mos_espa"))
                {
                    
                }
                
                {
                    badge.grantBadge(player, "bdg_racing_mos_espa");
                }
                setObjVar(npc, "racing.mosespa.topRecord", bestTime);
                setObjVar(npc, "racing.mosespa.topRecordName", playerName);
                sendSystemMessage(player, beatTheRecord);
                showFlyText(player, beatTheRecordFly, 1.5f, colors.GREENYELLOW);
                messageTo(npc, "messageDisplayLeader", params, 0, false);
                playMusic(player, "sound/music_combat_bfield_vict.snd");
                chat.publicChat(self, null, null, null, announcement);
                location loc = getLocation(npc);
                if (loc != null)
                {
                    playClientEffectLoc(player, "clienteffect/droid_effect_confetti.cef", loc, 0.f);
                }
            }
        }
        if (bestTime > topRecord)
        {
            chat.publicChat(self, null, null, null, announcement);
            if (playerName.equals(topRecordHolder))
            {
                sendSystemMessage(player, stillHoldRecord);
                messageTo(npc, "messageDisplayLeader", params, 0, false);
            }
            if (!playerName.equals(topRecordHolder))
            {
                sendSystemMessage(player, notGoodEnough);
                messageTo(npc, "messageDisplayLeader", params, 0, false);
                playMusic(player, "sound/music_combat_bfield_death.snd");
            }
        }
        if (bestTime == topRecord)
        {
            chat.publicChat(self, null, null, null, announcement);
            if (playerName.equals(topRecordHolder))
            {
                sendSystemMessage(player, stillHoldRecord);
                messageTo(npc, "messageDisplayLeader", params, 0, false);
            }
            if (!playerName.equals(topRecordHolder))
            {
                sendSystemMessage(player, tiedTheRecord);
                messageTo(npc, "messageDisplayLeader", params, 0, false);
                playMusic(player, "sound/music_combat_bfield_death.snd");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int messageDisplayLeader(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        obj_id npc = params.getObjId("npc");
        float topRecord = getFloatObjVar(npc, "racing.mosespa.topRecord");
        String playerName = getStringObjVar(npc, "racing.mosespa.topRecordName");
        string_id currentRecordHolder = new string_id(RACING_STF, "current_record_holder");
        sendSystemMessage(player, currentRecordHolder);
        sendSystemMessage(player, "" + playerName + " with a time of " + topRecord + " seconds.", null);
        return SCRIPT_CONTINUE;
    }
    public int messageWhatsMyTime(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        string_id whatsMyTime = new string_id(RACING_STF, "whats_my_time");
        string_id whatsMyTimeSpatial = new string_id(RACING_STF, "whats_my_time_spatial");
        String playerName = getName(player);
        float bestTime = getFloatObjVar(player, "racing.bestTime.mosespa");
        prose_package myBestTime = new prose_package();
        myBestTime = prose.getPackage(whatsMyTimeSpatial, playerName + " " + bestTime);
        sendSystemMessage(player, whatsMyTime);
        sendSystemMessage(player, "Time in seconds: " + bestTime, null);
        chat.publicChat(self, null, null, null, myBestTime);
        return SCRIPT_CONTINUE;
    }
    public int messageErasePersonalBest(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        removeObjVar(player, "racing.bestTime.mosespa");
        return SCRIPT_CONTINUE;
    }
}
