package script.systems.event_perk;

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
import script.library.prose;
import script.library.chat;
import java.util.StringTokenizer;

public class race_droid extends script.base_script
{
    public race_droid()
    {
    }
    public static final String RACING_STF = "theme_park/racing/racing";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "initializeRaceDroid", null, 3, false);
        return SCRIPT_CONTINUE;
    }
    public int initializeRaceDroid(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, "storytellerid"))
        {
            messageTo(self, "cleanupProp", null, 1, false);
            return SCRIPT_CONTINUE;
        }
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        setInvulnerable(self, true);
        float topRecord = 99000.0f;
        setObjVar(self, "event_perk.racing.topRecord", topRecord);
        setObjVar(self, "event_perk.racing.initialized", 0);
        String topRecordName = "Nobody";
        setObjVar(self, "event_perk.racing.topRecordName", topRecordName);
        setObjVar(self, "event_perk.racing.setupStep", 0);
        setObjVar(self, "event_perk.racing.settingUp", 0);
        return SCRIPT_CONTINUE;
    }
    public boolean allowedToUse(obj_id self, obj_id player) throws InterruptedException
    {
        obj_id storyteller = getObjIdObjVar(self, "storytellerid");
        if (isIdValid(storyteller))
        {
            if (storyteller == player)
            {
                return true;
            }
            else if (utils.hasScriptVar(player, "storytellerAssistant"))
            {
                obj_id whoAmIAssisting = utils.getObjIdScriptVar(player, "storytellerAssistant");
                if (isIdValid(whoAmIAssisting) && storyteller == whoAmIAssisting)
                {
                    return true;
                }
            }
        }
        return false;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int initialized = getIntObjVar(self, "event_perk.racing.initialized");
        if (initialized != 1)
        {
            if (allowedToUse(self, player))
            {
                int setupStep = getIntObjVar(self, "event_perk.racing.setupStep");
                int menu = mi.addRootMenu(menu_info_types.SERVER_MENU3, new string_id("event_perk", "race_set_parameters"));
                if (hasObjVar(self, "event_perk.racing.maxWaypoints"))
                {
                    mi.addSubMenu(menu, menu_info_types.SERVER_MENU4, new string_id("event_perk", "race_view_parameters"));
                    int menu2 = mi.addRootMenu(menu_info_types.SERVER_MENU5, new string_id("event_perk", "race_initialize_droid"));
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.SERVER_MENU3 || item == menu_info_types.SERVER_MENU4 || item == menu_info_types.SERVER_MENU5)
        {
            if (allowedToUse(self, player))
            {
                int initialized = getIntObjVar(self, "event_perk.racing.initialized");
                int settingUp = getIntObjVar(self, "event_perk.racing.settingUp");
                dictionary params = new dictionary();
                params.put("player", player);
                if (item == menu_info_types.SERVER_MENU3)
                {
                    if (initialized == 1)
                    {
                        return SCRIPT_CONTINUE;
                    }
                    if (settingUp != 0)
                    {
                        return SCRIPT_CONTINUE;
                    }
                    setObjVar(self, "event_perk.racing.setupStep", 0);
                    messageTo(self, "continueCollectingRaceData", params, 1, false);
                }
                if (item == menu_info_types.SERVER_MENU4)
                {
                    if (initialized == 1)
                    {
                        return SCRIPT_CONTINUE;
                    }
                    messageTo(self, "showStoredPlayerWaypointRaceData", params, 1, false);
                }
                if (item == menu_info_types.SERVER_MENU5)
                {
                    setObjVar(self, "event_perk.racing.initialized", 1);
                    attachScript(self, "conversation.event_perk_racing");
                    CustomerServiceLog("EventPerk", "(Race Droid - [" + self + "] was initialized for play by player [" + player + "].");
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int messageStartMission(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        int missionNum = 0;
        float startTime = getGameTime();
        int isRacing = 1;
        int maxWaypoints = getIntObjVar(self, "event_perk.racing.maxWaypoints");
        for (int i = 0; i < maxWaypoints; i++)
        {
            location checkPoint = getLocationObjVar(self, "event_perk.racing.checkpoint" + i);
            setObjVar(player, "event_perk.racing.checkpoint" + i, checkPoint);
        }
        location returnLoc = new location();
        obj_id building = getTopMostContainer(self);
        if (building == self)
        {
            returnLoc = getLocation(self);
        }
        else 
        {
            returnLoc = getLocation(building);
        }
        setObjVar(player, "event_perk.racing.missionNum", missionNum);
        setObjVar(player, "event_perk.racing.returnLoc", returnLoc);
        setObjVar(player, "event_perk.racing.startTime", startTime);
        setObjVar(player, "event_perk.racing.isRacing", isRacing);
        setObjVar(player, "event_perk.racing.maxWaypoints", maxWaypoints);
        setObjVar(player, "event_perk.racing.currentCheckpoint", 0);
        setObjVar(player, "event_perk.racing.droid", self);
        if (!hasObjVar(player, "event_perk.racing.currentTime"))
        {
            setObjVar(player, "event_perk.racing.currentTime", 99000.0f);
        }
        if (!hasObjVar(player, "event_perk.racing.bestTime"))
        {
            setObjVar(player, "event_perk.racing.bestTime", 99000.0f);
        }
        if (!hasObjVar(player, "event_perk.racing.lastTime"))
        {
            setObjVar(player, "event_perk.racing.lastTime", 99000.0f);
        }
        if (hasScript(player, "systems.event_perk.race_droid_player"))
        {
            messageTo(player, "handleRestartMission", params, 0, false);
        }
        else 
        {
            attachScript(player, "systems.event_perk.race_droid_player");
        }
        return SCRIPT_CONTINUE;
    }
    public int messageRegisterBest(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        string_id notGoodEnough = new string_id(RACING_STF, "not_good_enough");
        float bestTime = getFloatObjVar(player, "event_perk.racing.currentTime");
        float topRecord = getFloatObjVar(self, "event_perk.racing.topRecord");
        string_id beatTheRecord = new string_id(RACING_STF, "beat_the_record");
        string_id beatTheRecordFly = new string_id(RACING_STF, "beat_record_fly");
        string_id tiedTheRecord = new string_id(RACING_STF, "tied_the_record");
        string_id stillHoldRecord = new string_id(RACING_STF, "still_hold_record");
        string_id lastTime = new string_id(RACING_STF, "last_time");
        String playerName = getName(player);
        String topRecordHolder = getStringObjVar(self, "event_perk.racing.topRecordName");
        prose_package announcement = new prose_package();
        announcement = prose.getPackage(lastTime, playerName + " " + bestTime);
        removeObjVar(player, "event_perk.racing.lastTime");
        if (bestTime <= topRecord)
        {
            if (bestTime > 0.0f)
            {
                setObjVar(self, "event_perk.racing.topRecord", bestTime);
                setObjVar(self, "event_perk.racing.topRecordName", playerName);
                sendSystemMessage(player, beatTheRecord);
                showFlyText(player, beatTheRecordFly, 1.5f, colors.GREENYELLOW);
                messageTo(self, "messageDisplayLeader", params, 0, false);
                playMusic(player, "sound/music_combat_bfield_vict.snd");
                chat.publicChat(self, null, null, null, announcement);
                location loc = getLocation(self);
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
                messageTo(self, "messageDisplayLeader", params, 0, false);
            }
            if (!playerName.equals(topRecordHolder))
            {
                sendSystemMessage(player, notGoodEnough);
                messageTo(self, "messageDisplayLeader", params, 0, false);
                playMusic(player, "sound/music_combat_bfield_death.snd");
            }
        }
        if (bestTime == topRecord)
        {
            chat.publicChat(self, null, null, null, announcement);
            if (playerName.equals(topRecordHolder))
            {
                sendSystemMessage(player, stillHoldRecord);
                messageTo(self, "messageDisplayLeader", params, 0, false);
            }
            if (!playerName.equals(topRecordHolder))
            {
                sendSystemMessage(player, tiedTheRecord);
                messageTo(self, "messageDisplayLeader", params, 0, false);
                playMusic(player, "sound/music_combat_bfield_death.snd");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int messageDisplayLeader(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        float topRecord = getFloatObjVar(self, "event_perk.racing.topRecord");
        String playerName = getStringObjVar(self, "event_perk.racing.topRecordName");
        string_id currentRecordHolder = new string_id(RACING_STF, "current_record_holder");
        sendSystemMessage(player, currentRecordHolder);
        string_id leaderDisplay = new string_id("event_perk", "racing_leader_display");
        prose_package pp = prose.getPackage(leaderDisplay);
        prose.setTO(pp, playerName);
        prose.setDF(pp, topRecord);
        sendSystemMessageProse(player, pp);
        return SCRIPT_CONTINUE;
    }
    public int continueCollectingRaceData(obj_id self, dictionary params) throws InterruptedException
    {
        setObjVar(self, "event_perk.racing.settingUp", 1);
        obj_id player = params.getObjId("player");
        showRaceSetupUI(self, player);
        return SCRIPT_CONTINUE;
    }
    public int showRaceSetupUI(obj_id self, obj_id player) throws InterruptedException
    {
        int current = getIntObjVar(self, "event_perk.racing.setupStep");
        string_id promptSid = new string_id("event_perk", "race_setup_prompt_" + current);
        string_id titleSid = new string_id("event_perk", "race_setup_title_" + current);
        prose_package promptPP = prose.getPackage(promptSid);
        prose_package titlePP = prose.getPackage(titleSid);
        String prompt = packOutOfBandProsePackage(null, promptPP);
        String title = packOutOfBandProsePackage(null, titlePP);
        int pid = sui.inputbox(self, player, "\0" + prompt, "\0" + title, "handleRaceUIdata", 10, false, "");
        return pid;
    }
    public int handleRaceUIdata(obj_id self, dictionary params) throws InterruptedException
    {
        int bp = sui.getIntButtonPressed(params);
        String text = sui.getInputBoxText(params);
        obj_id player = sui.getPlayerId(params);
        params.put("player", player);
        if (bp == sui.BP_CANCEL)
        {
            sendSystemMessage(player, new string_id("event_perk", "race_setup_canceled"));
            setObjVar(self, "event_perk.racing.settingUp", 0);
            if (hasObjVar(self, "event_perk.racing.maxWaypoints"))
            {
                setObjVar(self, "event_perk.racing.setupStep", 9);
            }
            else 
            {
                setObjVar(self, "event_perk.racing.setupStep", 0);
            }
            return SCRIPT_CONTINUE;
        }
        if (text == null || text.equals(""))
        {
            sendSystemMessage(player, new string_id("event_perk", "race_setup_nothing_entered"));
            messageTo(self, "continueCollectingRaceData", params, 1, false);
            return SCRIPT_CONTINUE;
        }
        setObjVar(self, "event_perk.racing.lastData", text);
        messageTo(self, "storeLastRaceDataObjVar", params, 0, false);
        return SCRIPT_CONTINUE;
    }
    public int storeLastRaceDataObjVar(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        int setupStep = getIntObjVar(self, "event_perk.racing.setupStep");
        int setupCurrentSubStep = getIntObjVar(self, "event_perk.racing.setupCurrentSubStep");
        String lastDataStr = getStringObjVar(self, "event_perk.racing.lastData");
        dictionary tempSetupData = new dictionary();
        if (utils.hasScriptVar(self, "event_perk.racing.tempSetupData"))
        {
            tempSetupData = utils.getDictionaryScriptVar(self, "event_perk.racing.tempSetupData");
        }
        if (setupStep == 0)
        {
            int lastDataInt = utils.stringToInt(lastDataStr);
            if (lastDataInt < 1 || lastDataInt > 15)
            {
                sendSystemMessage(self, new string_id("event_perk", "racing_setup_waypoint_error"));
                messageTo(self, "continueCollectingRaceData", params, 1, false);
                return SCRIPT_CONTINUE;
            }
            tempSetupData.put("event_perk.racing.maxWaypoints", lastDataInt);
            utils.setScriptVar(self, "event_perk.racing.tempSetupData", tempSetupData);
            sendSystemMessage(player, "Number of waypoints: " + lastDataInt, null);
            setObjVar(self, "event_perk.racing.setupCurrentSubStep", 0);
            setObjVar(self, "event_perk.racing.setupStep", 1);
            messageTo(self, "continueCollectingRaceData", params, 1, false);
            return SCRIPT_CONTINUE;
        }
        if (setupStep == 1)
        {
            StringTokenizer st = new StringTokenizer(lastDataStr);
            int setupMaxSubStep = tempSetupData.getInt("event_perk.racing.maxWaypoints");
            setupCurrentSubStep = getIntObjVar(self, "event_perk.racing.setupCurrentSubStep");
            String scene = getCurrentSceneName();
            if (st.countTokens() == 2 && setupCurrentSubStep < setupMaxSubStep - 1)
            {
                String xCoordStr = st.nextToken();
                String zCoordStr = st.nextToken();
                int xCoord = utils.stringToInt(xCoordStr);
                int zCoord = utils.stringToInt(zCoordStr);
                if (xCoord > 8000 || xCoord < -8000 || zCoord > 8000 || zCoord < -8000 || xCoord == -1 || zCoord == -1)
                {
                    sendSystemMessage(self, new string_id("event_perk", "racing_setup_coord_error"));
                    messageTo(self, "continueCollectingRaceData", params, 2, false);
                    return SCRIPT_CONTINUE;
                }
                location checkPoint = new location(xCoord, 0, zCoord, scene);
                tempSetupData.put("event_perk.racing.checkpoint" + setupCurrentSubStep, checkPoint);
                utils.setScriptVar(self, "event_perk.racing.tempSetupData", tempSetupData);
                string_id waypointMsgSID = new string_id("event_perk", "race_waypoint_is");
                String waypointMsg = getString(waypointMsgSID);
                waypointMsg += xCoord + " " + zCoord;
                sendSystemMessage(player, waypointMsg, null);
                setupCurrentSubStep++;
                setObjVar(self, "event_perk.racing.setupCurrentSubStep", setupCurrentSubStep);
                messageTo(self, "continueCollectingRaceData", params, 1, false);
                return SCRIPT_CONTINUE;
            }
            if (st.countTokens() == 2 && setupCurrentSubStep == setupMaxSubStep - 1)
            {
                String xCoordStr = st.nextToken();
                String zCoordStr = st.nextToken();
                int xCoord = utils.stringToInt(xCoordStr);
                int zCoord = utils.stringToInt(zCoordStr);
                if (xCoord > 8000 || xCoord < -8000 || zCoord > 8000 || zCoord < -8000 || xCoord == -1 || zCoord == -1)
                {
                    sendSystemMessage(self, new string_id("event_perk", "racing_setup_coord_error"));
                    messageTo(self, "continueCollectingRaceData", params, 1, false);
                    return SCRIPT_CONTINUE;
                }
                location checkPoint = new location(xCoord, 0, zCoord, scene);
                tempSetupData.put("event_perk.racing.checkpoint" + setupCurrentSubStep, checkPoint);
                utils.setScriptVar(self, "event_perk.racing.tempSetupData", tempSetupData);
                setObjVar(self, "event_perk.racing.setupStep", 9);
                int finalMaxWaypoints = tempSetupData.getInt("event_perk.racing.maxWaypoints");
                setObjVar(self, "event_perk.racing.maxWaypoints", finalMaxWaypoints);
                for (int i = 0; i < finalMaxWaypoints; i++)
                {
                    location finalCheckpoints = tempSetupData.getLocation("event_perk.racing.checkpoint" + i);
                    setObjVar(self, "event_perk.racing.checkpoint" + i, finalCheckpoints);
                }
                string_id setupCompleted = new string_id("event_perk", "race_setup_complete");
                string_id waypointMsgSID = new string_id("event_perk", "race_waypoint_is");
                String waypointMsg = getString(waypointMsgSID);
                waypointMsg += xCoord + " " + zCoord;
                sendSystemMessage(player, waypointMsg, null);
                sendSystemMessage(player, setupCompleted);
                utils.removeScriptVar(self, "event_perk.racing.tempSetupData");
                messageTo(self, "showStoredPlayerWaypointRaceData", null, 1, false);
                setObjVar(self, "event_perk.racing.settingUp", 0);
                return SCRIPT_CONTINUE;
            }
            if (st.countTokens() != 2)
            {
                sendSystemMessage(player, new string_id("event_perk", "racing_setup_coord_error"));
                messageTo(self, "continueCollectingRaceData", params, 1, false);
                return SCRIPT_CONTINUE;
            }
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int showStoredPlayerWaypointRaceData(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        int maxWaypoints = getIntObjVar(self, "event_perk.racing.maxWaypoints");
        sendSystemMessage(self, new string_id("event_perk", "race_show_status0"));
        for (int i = 0; i < maxWaypoints; i++)
        {
            location waypoint = getLocationObjVar(self, "event_perk.racing.checkpoint" + i);
            float xCoordFloat = waypoint.x;
            float zCoordFloat = waypoint.z;
            string_id waypointSID = new string_id("event_perk", "race_waypoint");
            String waypointData = getString(waypointSID);
            waypointData += (i + 1) + " " + xCoordFloat + " " + zCoordFloat;
            sendSystemMessage(player, waypointData, null);
        }
        return SCRIPT_CONTINUE;
    }
}
