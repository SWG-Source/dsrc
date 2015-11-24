package script.theme_park.racing.nashal;

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
        3787,
        0,
        4764,
        3485,
        0,
        4556,
        3042,
        0,
        4565,
        2248,
        0,
        5169,
        1839,
        0,
        4966,
        1583,
        0,
        5062,
        1499,
        0,
        4802,
        1485,
        0,
        4366,
        2248,
        0,
        3905,
        1886,
        0,
        3211,
        2448,
        0,
        2457,
        2841,
        0,
        2923,
        3670,
        0,
        3475,
        4129,
        0,
        3652,
        4511,
        0,
        4054,
        4529,
        0,
        4831
    };
    public int OnAttach(obj_id self) throws InterruptedException
    {
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        attachScript(self, "conversation.racing_nashal");
        float topRecord = 1285.0f;
        setObjVar(self, "racing.nashal.topRecord", topRecord);
        String topRecordName = "Ben Quadinaros";
        setObjVar(self, "racing.nashal.topRecordName", topRecordName);
        messageTo(self, "resetDailyTime", null, 60 * 60 * 24, false);
        return SCRIPT_CONTINUE;
    }
    public int resetDailyTime(obj_id self, dictionary params) throws InterruptedException
    {
        float topRecord = 1285.0f;
        setObjVar(self, "racing.nashal.topRecord", topRecord);
        String topRecordName = "Ben Quadinaros";
        setObjVar(self, "racing.nashal.topRecordName", topRecordName);
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
            location checkPoint = new location(CHECK_POINT[i], CHECK_POINT[i + 1], CHECK_POINT[i + 2], "talus");
            setObjVar(player, "racing.nashal.checkpoint" + ((i / 3) + 1), checkPoint);
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
        setObjVar(player, "racing.nashal.missionNum", missionNum);
        setObjVar(player, "racing.nashal.returnLoc", returnLoc);
        setObjVar(player, "racing.nashal.startTime", startTime);
        setObjVar(player, "racing.nashal.isRacing", isRacing);
        setObjVar(player, "racing.nashal.maxCheckPoints", maxCheckPoints);
        if (!hasObjVar(player, "racing.nashal.currentTime"))
        {
            setObjVar(player, "racing.nashal.currentTime", 7500.0f);
        }
        if (!hasObjVar(player, "racing.bestTime.nashal"))
        {
            setObjVar(player, "racing.bestTime.nashal", 7500.0f);
        }
        if (!hasObjVar(player, "racing.lastTime.nashal"))
        {
            setObjVar(player, "racing.lastTime.nashal", 7500.0f);
        }
        if (hasScript(player, "theme_park.racing.nashal.player_racing"))
        {
            messageTo(player, "handleRestartMission", params, 0, false);
        }
        else 
        {
            attachScript(player, "theme_park.racing.nashal.player_racing");
        }
        String playerName = getName(player);
        CustomerServiceLog("Racing", "Player " + playerName + " [" + player + "] started the Nashal River race.");
        return SCRIPT_CONTINUE;
    }
    public int messageRegisterBest(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        obj_id npc = params.getObjId("npc");
        string_id notGoodEnough = new string_id(RACING_STF, "not_good_enough");
        float bestTime = getFloatObjVar(player, "racing.lastTime.nashal");
        float topRecord = getFloatObjVar(npc, "racing.nashal.topRecord");
        string_id beatTheRecord = new string_id(RACING_STF, "beat_the_record");
        string_id beatTheRecordFly = new string_id(RACING_STF, "beat_record_fly");
        string_id tiedTheRecord = new string_id(RACING_STF, "tied_the_record");
        string_id stillHoldRecord = new string_id(RACING_STF, "still_hold_record");
        string_id lastTime = new string_id(RACING_STF, "last_time");
        String playerName = getName(player);
        String topRecordHolder = getStringObjVar(self, "racing.nashal.topRecordName");
        prose_package announcement = new prose_package();
        announcement = prose.getPackage(lastTime, playerName + " " + bestTime);
        removeObjVar(player, "racing.lastTime.nashal");
        if (bestTime <= topRecord)
        {
            if (bestTime > 0.0f)
            {
                if (!badge.hasBadge(player, "bdg_racing_nashal_river"))
                {
                    
                }
                
                {
                    badge.grantBadge(player, "bdg_racing_nashal_river");
                }
                setObjVar(npc, "racing.nashal.topRecord", bestTime);
                setObjVar(npc, "racing.nashal.topRecordName", playerName);
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
        CustomerServiceLog("Racing", "Player " + playerName + " [" + player + "] finished the Nashal River race in " + bestTime + " seconds.");
        return SCRIPT_CONTINUE;
    }
    public int messageDisplayLeader(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        obj_id npc = params.getObjId("npc");
        float topRecord = getFloatObjVar(npc, "racing.nashal.topRecord");
        String playerName = getStringObjVar(npc, "racing.nashal.topRecordName");
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
        float bestTime = getFloatObjVar(player, "racing.bestTime.nashal");
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
        removeObjVar(player, "racing.bestTime.nashal");
        return SCRIPT_CONTINUE;
    }
}
