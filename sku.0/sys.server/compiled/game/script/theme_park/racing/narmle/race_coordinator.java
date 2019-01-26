package script.theme_park.racing.narmle;

import script.*;
import script.library.*;

public class race_coordinator extends script.base_script
{
    public race_coordinator()
    {
    }
    public static final String RACING_STF = "theme_park/racing/racing";
    public static final int[] CHECK_POINT = 
    {
        -4987,
        0,
        -2417,
        -5031,
        0,
        -2425,
        -5081,
        0,
        -2393,
        -5172,
        0,
        -2398,
        -5222,
        0,
        -2317,
        -5149,
        0,
        -2286,
        -5114,
        0,
        -2254,
        -5148,
        0,
        -2274,
        -5186,
        0,
        -2216,
        -5328,
        0,
        -2093,
        -5441,
        0,
        -2098,
        -5529,
        0,
        -2137,
        -5475,
        0,
        -2348,
        -5205,
        0,
        -2614,
        -4995,
        0,
        -2484
    };
    public int OnAttach(obj_id self) throws InterruptedException
    {
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        attachScript(self, "conversation.racing_narmle");
        float topRecord = 350.0f;
        setObjVar(self, "racing.narmle.topRecord", topRecord);
        String topRecordName = "Ben Quadinaros";
        setObjVar(self, "racing.narmle.topRecordName", topRecordName);
        messageTo(self, "resetDailyTime", null, 60 * 60 * 24, false);
        return SCRIPT_CONTINUE;
    }
    public int resetDailyTime(obj_id self, dictionary params) throws InterruptedException
    {
        float topRecord = 350.0f;
        setObjVar(self, "racing.narmle.topRecord", topRecord);
        String topRecordName = "Ben Quadinaros";
        setObjVar(self, "racing.narmle.topRecordName", topRecordName);
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
            location checkPoint = new location(CHECK_POINT[i], CHECK_POINT[i + 1], CHECK_POINT[i + 2], "rori");
            setObjVar(player, "racing.narmle.checkpoint" + ((i / 3) + 1), checkPoint);
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
        setObjVar(player, "racing.narmle.missionNum", missionNum);
        setObjVar(player, "racing.narmle.returnLoc", returnLoc);
        setObjVar(player, "racing.narmle.startTime", startTime);
        setObjVar(player, "racing.narmle.isRacing", isRacing);
        setObjVar(player, "racing.narmle.maxCheckPoints", maxCheckPoints);
        if (!hasObjVar(player, "racing.narmle.currentTime"))
        {
            setObjVar(player, "racing.narmle.currentTime", 7500.0f);
        }
        if (!hasObjVar(player, "racing.bestTime.narmle"))
        {
            setObjVar(player, "racing.bestTime.narmle", 7500.0f);
        }
        if (!hasObjVar(player, "racing.lastTime.narmle"))
        {
            setObjVar(player, "racing.lastTime.narmle", 7500.0f);
        }
        if (hasScript(player, "theme_park.racing.narmle.player_racing"))
        {
            messageTo(player, "handleRestartMission", params, 0, false);
        }
        else 
        {
            attachScript(player, "theme_park.racing.narmle.player_racing");
        }
        String playerName = getName(player);
        CustomerServiceLog("Racing", "Player " + playerName + " [" + player + "] started the Narmle Rally race.");
        return SCRIPT_CONTINUE;
    }
    public int messageRegisterBest(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        obj_id npc = params.getObjId("npc");
        string_id notGoodEnough = new string_id(RACING_STF, "not_good_enough");
        float bestTime = getFloatObjVar(player, "racing.lastTime.narmle");
        float topRecord = getFloatObjVar(npc, "racing.narmle.topRecord");
        string_id beatTheRecord = new string_id(RACING_STF, "beat_the_record");
        string_id beatTheRecordFly = new string_id(RACING_STF, "beat_record_fly");
        string_id tiedTheRecord = new string_id(RACING_STF, "tied_the_record");
        string_id stillHoldRecord = new string_id(RACING_STF, "still_hold_record");
        string_id lastTime = new string_id(RACING_STF, "last_time");
        String playerName = getName(player);
        String topRecordHolder = getStringObjVar(self, "racing.narmle.topRecordName");
        prose_package announcement = new prose_package();
        announcement = prose.getPackage(lastTime, playerName + " " + bestTime);
        removeObjVar(player, "racing.lastTime.narmle");
        if (bestTime <= topRecord)
        {
            if (bestTime > 0.0f)
            {
                if (!badge.hasBadge(player, "bdg_racing_narmle_memorial"))
                {
                    
                }
                
                {
                    badge.grantBadge(player, "bdg_racing_narmle_memorial");
                }
                setObjVar(npc, "racing.narmle.topRecord", bestTime);
                setObjVar(npc, "racing.narmle.topRecordName", playerName);
                sendSystemMessage(player, beatTheRecord);
                showFlyText(player, beatTheRecordFly, 1.5f, colors.GREENYELLOW);
                messageTo(npc, "messageDisplayLeader", params, 0, false);
                playMusic(player, "sound/music_combat_bfield_vict.snd");
                chat.publicChat(self, null, null, null, announcement);
                location loc = getLocation(npc);
                if (loc != null)
                {
                    playClientEffectLoc(player, "clienteffect/droid_effect_confetti.cef", loc, 0.0f);
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
        CustomerServiceLog("Racing", "Player " + playerName + " [" + player + "] finished the Narmle Rally race in " + bestTime + " seconds.");
        return SCRIPT_CONTINUE;
    }
    public int messageDisplayLeader(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        obj_id npc = params.getObjId("npc");
        float topRecord = getFloatObjVar(npc, "racing.narmle.topRecord");
        String playerName = getStringObjVar(npc, "racing.narmle.topRecordName");
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
        float bestTime = getFloatObjVar(player, "racing.bestTime.narmle");
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
        removeObjVar(player, "racing.bestTime.narmle");
        return SCRIPT_CONTINUE;
    }
}
