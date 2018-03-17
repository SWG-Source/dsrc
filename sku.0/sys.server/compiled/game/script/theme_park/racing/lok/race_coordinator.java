package script.theme_park.racing.lok;

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
        946,
        0,
        4634,
        1065,
        0,
        3156,
        3825,
        0,
        -540,
        3337,
        0,
        -2425,
        3364,
        0,
        -3819,
        2962,
        0,
        -4546,
        3080,
        0,
        -4671,
        3009,
        0,
        -4798,
        2893,
        0,
        -4782,
        2744,
        0,
        -4458,
        509,
        0,
        -2924,
        -497,
        0,
        -624,
        427,
        0,
        705,
        838,
        0,
        2738,
        -19,
        0,
        4059,
        -26,
        0,
        4558,
        275,
        0,
        5073
    };
    public int OnAttach(obj_id self) throws InterruptedException
    {
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        attachScript(self, "conversation.racing_lok");
        float topRecord = 2885.0f;
        setObjVar(self, "racing.lok.topRecord", topRecord);
        String topRecordName = "Ben Quadinaros";
        setObjVar(self, "racing.lok.topRecordName", topRecordName);
        messageTo(self, "resetDailyTime", null, 60 * 60 * 24, false);
        return SCRIPT_CONTINUE;
    }
    public int resetDailyTime(obj_id self, dictionary params) throws InterruptedException
    {
        float topRecord = 2885.0f;
        setObjVar(self, "racing.lok.topRecord", topRecord);
        String topRecordName = "Ben Quadinaros";
        setObjVar(self, "racing.lok.topRecordName", topRecordName);
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
            location checkPoint = new location(CHECK_POINT[i], CHECK_POINT[i + 1], CHECK_POINT[i + 2], "lok");
            setObjVar(player, "racing.lok.checkpoint" + ((i / 3) + 1), checkPoint);
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
        setObjVar(player, "racing.lok.missionNum", missionNum);
        setObjVar(player, "racing.lok.returnLoc", returnLoc);
        setObjVar(player, "racing.lok.startTime", startTime);
        setObjVar(player, "racing.lok.isRacing", isRacing);
        setObjVar(player, "racing.lok.maxCheckPoints", maxCheckPoints);
        if (!hasObjVar(player, "racing.lok.currentTime"))
        {
            setObjVar(player, "racing.lok.currentTime", 7500.0f);
        }
        if (!hasObjVar(player, "racing.bestTime.lok"))
        {
            setObjVar(player, "racing.bestTime.lok", 7500.0f);
        }
        if (!hasObjVar(player, "racing.lastTime.lok"))
        {
            setObjVar(player, "racing.lastTime.lok", 7500.0f);
        }
        if (hasScript(player, "theme_park.racing.lok.player_racing"))
        {
            messageTo(player, "handleRestartMission", params, 0, false);
        }
        else 
        {
            attachScript(player, "theme_park.racing.lok.player_racing");
        }
        String playerName = getName(player);
        CustomerServiceLog("Racing", "Player " + playerName + " [" + player + "] started the Lok Marathon race.");
        return SCRIPT_CONTINUE;
    }
    public int messageRegisterBest(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        obj_id npc = params.getObjId("npc");
        string_id notGoodEnough = new string_id(RACING_STF, "not_good_enough");
        float bestTime = getFloatObjVar(player, "racing.lastTime.lok");
        float topRecord = getFloatObjVar(npc, "racing.lok.topRecord");
        string_id beatTheRecord = new string_id(RACING_STF, "beat_the_record");
        string_id beatTheRecordFly = new string_id(RACING_STF, "beat_record_fly");
        string_id tiedTheRecord = new string_id(RACING_STF, "tied_the_record");
        string_id stillHoldRecord = new string_id(RACING_STF, "still_hold_record");
        string_id lastTime = new string_id(RACING_STF, "last_time");
        String playerName = getName(player);
        String topRecordHolder = getStringObjVar(self, "racing.lok.topRecordName");
        prose_package announcement = new prose_package();
        announcement = prose.getPackage(lastTime, playerName + " " + bestTime);
        removeObjVar(player, "racing.lastTime.lok");
        if (bestTime <= topRecord)
        {
            if (bestTime > 0.0f)
            {
                if (!badge.hasBadge(player, "bdg_racing_lok_marathon"))
                {
                    
                }
                
                {
                    badge.grantBadge(player, "bdg_racing_lok_marathon");
                }
                setObjVar(npc, "racing.lok.topRecord", bestTime);
                setObjVar(npc, "racing.lok.topRecordName", playerName);
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
        CustomerServiceLog("Racing", "Player " + playerName + " [" + player + "] finished the Lok Marathon race in " + bestTime + " seconds.");
        return SCRIPT_CONTINUE;
    }
    public int messageDisplayLeader(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        obj_id npc = params.getObjId("npc");
        float topRecord = getFloatObjVar(npc, "racing.lok.topRecord");
        String playerName = getStringObjVar(npc, "racing.lok.topRecordName");
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
        float bestTime = getFloatObjVar(player, "racing.bestTime.lok");
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
        removeObjVar(player, "racing.bestTime.lok");
        return SCRIPT_CONTINUE;
    }
}
