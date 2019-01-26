package script.theme_park.racing.agrilat;

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
        1496,
        0,
        4725,
        1318,
        0,
        5005,
        1007,
        0,
        5063,
        800,
        0,
        4940,
        560,
        0,
        4350,
        600,
        0,
        4210,
        668,
        0,
        3900,
        953,
        0,
        3741,
        890,
        0,
        4250,
        1043,
        0,
        4618,
        1280,
        0,
        4180,
        1564,
        0,
        4628,
        1748,
        0,
        4524
    };
    public int OnAttach(obj_id self) throws InterruptedException
    {
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        attachScript(self, "conversation.racing_agrilat");
        float topRecord = 510.0f;
        setObjVar(self, "racing.agrilat.topRecord", topRecord);
        String topRecordName = "Ben Quadinaros";
        setObjVar(self, "racing.agrilat.topRecordName", topRecordName);
        messageTo(self, "resetDailyTime", null, 60 * 60 * 24, false);
        return SCRIPT_CONTINUE;
    }
    public int resetDailyTime(obj_id self, dictionary params) throws InterruptedException
    {
        float topRecord = 510.0f;
        setObjVar(self, "racing.agrilat.topRecord", topRecord);
        String topRecordName = "Ben Quadinaros";
        setObjVar(self, "racing.agrilat.topRecordName", topRecordName);
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
            location checkPoint = new location(CHECK_POINT[i], CHECK_POINT[i + 1], CHECK_POINT[i + 2], "corellia");
            setObjVar(player, "racing.agrilat.checkpoint" + ((i / 3) + 1), checkPoint);
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
        setObjVar(player, "racing.agrilat.missionNum", missionNum);
        setObjVar(player, "racing.agrilat.returnLoc", returnLoc);
        setObjVar(player, "racing.agrilat.startTime", startTime);
        setObjVar(player, "racing.agrilat.isRacing", isRacing);
        setObjVar(player, "racing.agrilat.maxCheckPoints", maxCheckPoints);
        if (!hasObjVar(player, "racing.agrilat.currentTime"))
        {
            setObjVar(player, "racing.agrilat.currentTime", 7500.0f);
        }
        if (!hasObjVar(player, "racing.bestTime.agrilat"))
        {
            setObjVar(player, "racing.bestTime.agrilat", 7500.0f);
        }
        if (!hasObjVar(player, "racing.lastTime.agrilat"))
        {
            setObjVar(player, "racing.lastTime.agrilat", 7500.0f);
        }
        if (hasScript(player, "theme_park.racing.agrilat.player_racing"))
        {
            messageTo(player, "handleRestartMission", params, 0, false);
        }
        else 
        {
            attachScript(player, "theme_park.racing.agrilat.player_racing");
        }
        return SCRIPT_CONTINUE;
    }
    public int messageRegisterBest(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        obj_id npc = params.getObjId("npc");
        string_id notGoodEnough = new string_id(RACING_STF, "not_good_enough");
        float bestTime = getFloatObjVar(player, "racing.lastTime.agrilat");
        float topRecord = getFloatObjVar(npc, "racing.agrilat.topRecord");
        string_id beatTheRecord = new string_id(RACING_STF, "beat_the_record");
        string_id beatTheRecordFly = new string_id(RACING_STF, "beat_record_fly");
        string_id tiedTheRecord = new string_id(RACING_STF, "tied_the_record");
        string_id stillHoldRecord = new string_id(RACING_STF, "still_hold_record");
        string_id lastTime = new string_id(RACING_STF, "last_time");
        String playerName = getName(player);
        String topRecordHolder = getStringObjVar(self, "racing.agrilat.topRecordName");
        prose_package announcement = new prose_package();
        announcement = prose.getPackage(lastTime, playerName + " " + bestTime);
        removeObjVar(player, "racing.lastTime.agrilat");
        if (bestTime <= topRecord)
        {
            if (bestTime > 0.0f)
            {
                if (!badge.hasBadge(player, "bdg_racing_agrilat_swamp"))
                {
                    
                }
                
                {
                    badge.grantBadge(player, "bdg_racing_agrilat_swamp");
                }
                setObjVar(npc, "racing.agrilat.topRecord", bestTime);
                setObjVar(npc, "racing.agrilat.topRecordName", playerName);
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
        return SCRIPT_CONTINUE;
    }
    public int messageDisplayLeader(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        obj_id npc = params.getObjId("npc");
        float topRecord = getFloatObjVar(npc, "racing.agrilat.topRecord");
        String playerName = getStringObjVar(npc, "racing.agrilat.topRecordName");
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
        float bestTime = getFloatObjVar(player, "racing.bestTime.agrilat");
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
        removeObjVar(player, "racing.bestTime.agrilat");
        return SCRIPT_CONTINUE;
    }
}
