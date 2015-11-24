package script.systems.gcw;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.buff;
import script.library.factions;
import script.library.gcw;
import script.library.pvp;
import script.library.regions;
import script.library.trial;
import script.library.utils;

public class pvp_battlefield extends script.base_script
{
    public pvp_battlefield()
    {
    }
    public static final float CYCLE_HEARTBEAT = 30.0f;
    public static final String PVP_AREA_RECORD = "gcw_pvp_region.activity_list";
    public static final String CYCLE_STATUS = "gcw_pvp_region.isActive";
    public static final String CYCLE_ITTERATION = "gcw_pvp_region.cycle_itteration";
    public static final String GCW_REGION_DATA = "gcw_pvp_region";
    public static final String LAST_CYCLE = "gcw_pvp_region.lastCycle";
    public static final String[] BATTLEFIELD_STATUS = pvp.BATTLEFIELD_STATUS;
    public static final String BATTLEFIELD_TABLE = "datatables/pvp/pvp_battlefields.iff";
    public static final String BATTLEFIELD_REBEL_INDIVIDUAL_QUEUE = "battlefield.rebel_individual_queue";
    public static final String BATTLEFIELD_IMPERIAL_INDIVIDUAL_QUEUE = "battlefield.imperial_individual_queue";
    public static final String BATTLEFIELD_REBEL_GROUP_QUEUE = "battlefield.rebel_group_queue";
    public static final String BATTLEFIELD_IMPERIAL_GROUP_QUEUE = "battlefield.imperial_group_queue";
    public static final String BATTLEFIELD_POTENTIAL_PLAYERS = "battlefield.potential_players";
    public static final String BATTLEFIELD_POTENTIAL_REBEL_PLAYERS = "battlefield.potential_rebel_players";
    public static final String BATTLEFIELD_POTENTIAL_IMPERIAL_PLAYERS = "battlefield.potential_imperial_players";
    public static final String BATTLEFIELD_INVITED_REBEL_PLAYERS = "battlefield.invited_rebel_players";
    public static final String BATTLEFIELD_INVITED_IMPERIAL_PLAYERS = "battlefield.invited_imperial_players";
    public static final String BATTLEFIELD_ACTIVE_PLAYERS = pvp.BATTLEFIELD_ACTIVE_PLAYERS;
    public static final String BATTLEFIELD_ACTIVE_REBEL_PLAYERS = pvp.BATTLEFIELD_ACTIVE_REBEL_PLAYERS;
    public static final String BATTLEFIELD_ACTIVE_IMPERIAL_PLAYERS = pvp.BATTLEFIELD_ACTIVE_IMPERIAL_PLAYERS;
    public static final float BATTLEFIELD_HEARTBEAT = 5.0f;
    public static final float BATTLEFIELD_INVITATION_RECALCULATE_TIME = 60.0f;
    public static final float BATTLEFIELD_INVITATION_REINVITE_TIME = 65.0f;
    public static final int BATTLEFIELD_MINIMUM_TEAM_SIZE = 4;
    public static final int BATTLEFIELD_MAXIMUM_TEAM_SIZE = 16;
    public static final int BATTLEFIELD_PLAYER_KILL_VALUE = 10;
    public static final int BATTLEFIELD_TERMINAL_CAPTURE_VALUE = 500;
    public void doLogging(String section, String message) throws InterruptedException
    {
    }
    public void blog(obj_id controller, String text) throws InterruptedException
    {
        pvp.bfLog(controller, text);
    }
    public void setupBattlefield(obj_id controller) throws InterruptedException
    {
        if (!isIdValid(controller))
        {
            return;
        }
        String battlefieldName = getStringObjVar(controller, "battlefieldName");
        if (battlefieldName == null || battlefieldName.length() < 1)
        {
            return;
        }
        dictionary battlefieldDict = utils.dataTableGetRow(BATTLEFIELD_TABLE, battlefieldName);
        if (battlefieldDict == null)
        {
            return;
        }
        gcw.makeBattlefieldRegion(controller);
        gcw.makePushbackRegion(controller);
        int battlefieldType = battlefieldDict.getInt("battle_type");
        utils.setScriptVar(controller, "battlefieldType", battlefieldType);
        location loc1 = getLocation(controller);
        loc1.x = battlefieldDict.getFloat("rebel_spawn_x");
        loc1.y = battlefieldDict.getFloat("rebel_spawn_y");
        loc1.z = battlefieldDict.getFloat("rebel_spawn_z");
        utils.setScriptVar(controller, "battlefieldRebelSpawn", loc1);
        location loc2 = getLocation(controller);
        loc2.x = battlefieldDict.getFloat("imperial_spawn_x");
        loc2.y = battlefieldDict.getFloat("imperial_spawn_y");
        loc2.z = battlefieldDict.getFloat("imperial_spawn_z");
        utils.setScriptVar(controller, "battlefieldImperialSpawn", loc2);
        location loc3 = getLocation(controller);
        loc3.x = battlefieldDict.getFloat("kickout_invalid_x");
        loc3.y = battlefieldDict.getFloat("kickout_invalid_y");
        loc3.z = battlefieldDict.getFloat("kickout_invalid_z");
        utils.setScriptVar(controller, "kickOutLocation", loc3);
        location loc4 = getLocation(controller);
        loc4.x = battlefieldDict.getFloat("kickout_rebel_x");
        loc4.y = battlefieldDict.getFloat("kickout_rebel_y");
        loc4.z = battlefieldDict.getFloat("kickout_rebel_z");
        utils.setScriptVar(controller, "rebelKickOutLocation", loc4);
        location loc5 = getLocation(controller);
        loc5.x = battlefieldDict.getFloat("kickout_imperial_x");
        loc5.y = battlefieldDict.getFloat("kickout_imperial_y");
        loc5.z = battlefieldDict.getFloat("kickout_imperial_z");
        utils.setScriptVar(controller, "imperialKickOutLocation", loc5);
        getClusterWideData("pvp", battlefieldName, true, controller);
        blog(controller, "Battlefield Reset");
        utils.setScriptVar(controller, "battlefield.state", pvp.PVP_STATE_NONE);
        dictionary params = new dictionary();
        int gameTime = getGameTime();
        params.put("stateTime", gameTime);
        utils.setScriptVar(controller, "battlefield.stateTime", gameTime);
        messageTo(controller, "checkBattlefieldState", params, BATTLEFIELD_HEARTBEAT, false);
    }
    public int OnClusterWideDataResponse(obj_id self, String manage_name, String name, int request_id, String[] element_name_list, dictionary[] data, int lock_key) throws InterruptedException
    {
        String battlefieldName = getStringObjVar(self, "battlefieldName");
        if (battlefieldName == null || battlefieldName.length() < 1)
        {
            return SCRIPT_CONTINUE;
        }
        if (!manage_name.equals("pvp") || !name.equals(battlefieldName))
        {
            return SCRIPT_CONTINUE;
        }
        if (element_name_list != null && element_name_list.length > 0 && data != null && data.length > 0)
        {
            for (int i = 0, j = data.length; i < j; i++)
            {
                if (data[i].getObjId("battlefield") == self)
                {
                    blog(self, "pvp_battlefield OnClusterWideDataResponse Battlefield: " + battlefieldName + " already exists in data");
                    releaseClusterWideDataLock(manage_name, lock_key);
                    return SCRIPT_CONTINUE;
                }
            }
        }
        dictionary battlefield = new dictionary();
        battlefield.put("battlefield", self);
        replaceClusterWideData(manage_name, battlefieldName, battlefield, true, lock_key);
        releaseClusterWideDataLock(manage_name, lock_key);
        blog(self, "pvp_battlefield OnClusterWideDataResponse Battlefield: " + battlefieldName + " added");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        detachScript(self, "quest.task.ground.retrieve_item_on_item");
        setupBattlefield(self);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        blog(self, "pvp_battlefield OnInitialize");
        detachScript(self, "quest.task.ground.retrieve_item_on_item");
        setupBattlefield(self);
        return SCRIPT_CONTINUE;
    }
    public int checkBattlefieldState(obj_id self, dictionary params) throws InterruptedException
    {
        int bfState = utils.getIntScriptVar(self, "battlefield.state");
        int stateTime = utils.getIntScriptVar(self, "battlefield.stateTime");
        int bfType = utils.getIntScriptVar(self, "battlefieldType");
        int messageTime = params.getInt("stateTime");
        if (stateTime != messageTime)
        {
            blog(self, "checkBattlefieldState Old messageTo received and ignored");
            return SCRIPT_CONTINUE;
        }
        String extra = "";
        int gameTime = getGameTime();
        switch (bfState)
        {
            case pvp.PVP_STATE_NONE:
            bfQueueClear(self);
            bfState = pvp.PVP_STATE_INITIALIZE;
            break;
            case pvp.PVP_STATE_INITIALIZE:
            bfCleanup(self);
            if (pvp.bfTerminalsReset(self))
            {
                bfState = pvp.PVP_STATE_NEW_BATTLE;
                extra = "Terminals all accounted for.";
            }
            else 
            {
                extra = "Terminals not initialized.";
            }
            utils.setScriptVar(self, "battlefield.imperialScore", 5000);
            utils.setScriptVar(self, "battlefield.rebelScore", 5000);
            break;
            case pvp.PVP_STATE_NEW_BATTLE:
            bfState = pvp.PVP_STATE_BUILD_QUEUE;
            break;
            case pvp.PVP_STATE_BUILD_QUEUE:
            if (bfQueueBuildPotentialEnemies(self))
            {
                utils.setScriptVar(self, "battlefield.inviteAnnounce", gameTime);
                bfState = pvp.PVP_STATE_INVITE_QUEUE;
            }
            else 
            {
                utils.setScriptVar(self, "battlefield.hibernateTime", gameTime);
                extra = " hibernating gameTime: " + gameTime;
            }
            break;
            case pvp.PVP_STATE_INVITE_QUEUE:
            if (!utils.hasScriptVar(self, "battlefield.queueTime"))
            {
                utils.setScriptVar(self, "battlefield.queueTime", gameTime);
                bfQueueSendInvitations(self);
            }
            else 
            {
                int inviteTime = utils.getIntScriptVar(self, "battlefield.queueTime");
                if (gameTime - inviteTime > pvp.BATTLEFIELD_INVITATION_WAIT_TIME)
                {
                    utils.removeScriptVar(self, "battlefield.queueTime");
                    utils.removeScriptVar(self, "battlefield.inviteAnnounce");
                    bfState = pvp.PVP_STATE_INVITE_OVER;
                }
                else 
                {
                    if (gameTime - inviteTime < BATTLEFIELD_INVITATION_RECALCULATE_TIME)
                    {
                        bfQueueBuildPotentialEnemies(self);
                        bfQueueSendInvitations(self);
                    }
                }
                int lastAnnounceTime = utils.getIntScriptVar(self, "battlefield.inviteAnnounce");
                if (gameTime - lastAnnounceTime > 60)
                {
                    params = new dictionary();
                    params.put("announceId", new string_id("spam", "battlefield_invitation_ongoing"));
                    pvp.bfMessagePlayers(self, BATTLEFIELD_ACTIVE_REBEL_PLAYERS, "receiveBattlefieldAnnouncement", params);
                    pvp.bfMessagePlayers(self, BATTLEFIELD_ACTIVE_IMPERIAL_PLAYERS, "receiveBattlefieldAnnouncement", params);
                    utils.setScriptVar(self, "battlefield.inviteAnnounce", gameTime);
                }
            }
            extra = " Rebels Accepted: " + bfActiveGetRebelCount(self) + " Imperials Accepted: " + bfActiveGetImperialCount(self);
            break;
            case pvp.PVP_STATE_INVITE_OVER:
            int minimumTeamSize = BATTLEFIELD_MINIMUM_TEAM_SIZE;
            if (getConfigSetting("GameServer", "BfMinimumTeamSize") != null && (getConfigSetting("GameServer", "BfMinimumTeamSize")).length() > 0)
            {
                minimumTeamSize = utils.stringToInt(getConfigSetting("GameServer", "BfMinimumTeamSize"));
            }
            extra = "checkBattlefieldState Invitations over.  minimumTeamSize: " + minimumTeamSize;
            if (bfActiveGetRebelCount(self) < minimumTeamSize || bfActiveGetImperialCount(self) < minimumTeamSize)
            {
                blog(self, "checkBattlefieldState minimumTeamSize: not met");
                bfState = pvp.PVP_STATE_ABORT_QUEUE;
                bfQueueClearPotentialEnemies(self, false);
                bfQueueClearInvitations(self);
                bfActiveRefusePlayers(self);
                clearBattlefieldActivePlayers(self);
                break;
            }
            bfActiveEvenTeams(self);
            bfQueueClearPotentialEnemies(self, true);
            bfQueueClearInvitations(self);
            bfActivePlayParticlePickup(self);
            params = new dictionary();
            params.put("announceId", new string_id("spam", "battlefield_invitation_warp"));
            pvp.bfMessagePlayers(self, BATTLEFIELD_ACTIVE_REBEL_PLAYERS, "receiveBattlefieldAnnouncement", params);
            pvp.bfMessagePlayers(self, BATTLEFIELD_ACTIVE_IMPERIAL_PLAYERS, "receiveBattlefieldAnnouncement", params);
            bfState = pvp.PVP_STATE_BATTLE_SETUP;
            break;
            case pvp.PVP_STATE_ABORT_QUEUE:
            bfState = pvp.PVP_STATE_INITIALIZE;
            break;
            case pvp.PVP_STATE_BATTLE_SETUP:
            if (!utils.hasScriptVar(self, "battlefield.bfSetupTime"))
            {
                utils.setScriptVar(self, "battlefield.bfSetupTime", gameTime);
                bfActiveSetupPlayers(self);
                bfActiveWarpPlayers(self, bfState);
            }
            else 
            {
                int setupTime = utils.getIntScriptVar(self, "battlefield.bfSetupTime");
                if (gameTime - setupTime > pvp.BATTLEFIELD_SETUP_WAIT_TIME)
                {
                    bfState = pvp.PVP_STATE_BATTLE_START;
                    utils.removeScriptVar(self, "battlefield.bfSetupTime");
                    bfActiveWarpPlayers(self, bfState);
                }
            }
            break;
            case pvp.PVP_STATE_BATTLE_START:
            if (!utils.hasScriptVar(self, "battlefield.battleTime"))
            {
                utils.setScriptVar(self, "battlefield.battleTime", gameTime);
            }
            pvp.bfActivePlayersAnnounce(self, new string_id("spam", "battlefield_battle_begun"));
            bfState = pvp.PVP_STATE_BATTLE_ENGAGED;
            break;
            case pvp.PVP_STATE_BATTLE_ENGAGED:
            bfActivePlayersStoreScores(self);
            int battleTime = utils.getIntScriptVar(self, "battlefield.battleTime");
            if (!utils.hasScriptVar(self, "battlefield.battleTimeAnnounce") && (pvp.BATTLEFIELD_DURATION - (gameTime - battleTime)) < 60)
            {
                utils.setScriptVar(self, "battlefield.battleTimeAnnounce", 1);
                pvp.bfActivePlayersAnnounce(self, new string_id("spam", "battlefield_battle_one_minute_left"));
            }
            if (bfType == pvp.BATTLEFIELD_TYPE_CAPTURE_THE_FLAG)
            {
                int runnerTime = utils.getIntScriptVar(self, "battlefield.runnerTime");
                if (!pvp.bfHasRunner(self) && gameTime - runnerTime > pvp.BATTLEFIELD_RUNNER_DURATION / 2 && rand(0, 1) == 0)
                {
                    runnerTime = 0;
                }
                if (gameTime - runnerTime > pvp.BATTLEFIELD_RUNNER_DURATION && (pvp.BATTLEFIELD_DURATION - (gameTime - battleTime) > pvp.BATTLEFIELD_RUNNER_DURATION))
                {
                    if (!pvp.bfHasRunner(self))
                    {
                        bfChooseRunner(self);
                    }
                    else 
                    {
                        pvp.bfClearRunner(self);
                    }
                }
            }
            if (gameTime - battleTime > pvp.BATTLEFIELD_DURATION)
            {
                utils.removeScriptVar(self, "battlefield.battleTime");
                utils.removeScriptVar(self, "battlefield.battleTimeAnnounce");
                utils.setScriptVar(self, "battlefield.endTime", gameTime);
                bfBattleEnd(self);
                bfState = pvp.PVP_STATE_BATTLE_END;
            }
            break;
            case pvp.PVP_STATE_BATTLE_END:
            int endTime = utils.getIntScriptVar(self, "battlefield.endTime");
            if (gameTime - endTime > pvp.BATTLEFIELD_END_CLEANUP)
            {
                bfState = pvp.PVP_STATE_BATTLE_CLEANUP;
            }
            break;
            case pvp.PVP_STATE_BATTLE_CLEANUP:
            bfActiveKickOutPlayers(self);
            clearBattlefieldActivePlayers(self);
            bfState = pvp.PVP_STATE_INITIALIZE;
            break;
            default:
            bfState = pvp.PVP_STATE_NONE;
            break;
        }
        blog(self, "checkBattlefieldState bfState: " + bfState + " - " + BATTLEFIELD_STATUS[bfState] + " " + extra);
        utils.setScriptVar(self, "battlefield.state", bfState);
        params = new dictionary();
        params.put("stateTime", gameTime);
        utils.setScriptVar(self, "battlefield.stateTime", gameTime);
        if (!utils.hasScriptVar(self, "battlefield.hibernateTime"))
        {
            messageTo(self, "checkBattlefieldState", params, BATTLEFIELD_HEARTBEAT, false);
        }
        return SCRIPT_CONTINUE;
    }
    public void battlefieldUnhibernate(obj_id self) throws InterruptedException
    {
        dictionary params = new dictionary();
        int gameTime = getGameTime();
        params.put("stateTime", gameTime);
        utils.setScriptVar(self, "battlefield.stateTime", gameTime);
        utils.removeScriptVar(self, "battlefield.hibernateTime");
        messageTo(self, "checkBattlefieldState", params, BATTLEFIELD_HEARTBEAT, false);
    }
    public int OnHearSpeech(obj_id self, obj_id speaker, String text) throws InterruptedException
    {
        if (!isIdValid(speaker) || !isGod(speaker))
        {
            return SCRIPT_CONTINUE;
        }
        String[] words = split(text, ' ');
        if (words[0].equals("bfreset"))
        {
            setupBattlefield(self);
            sendSystemMessageTestingOnly(speaker, "Battlefield reset.");
        }
        if (words[0].equals("bfcont"))
        {
            dictionary params = new dictionary();
            int gameTime = getGameTime();
            params.put("stateTime", gameTime);
            utils.setScriptVar(self, "battlefield.stateTime", gameTime);
            if (!utils.hasScriptVar(self, "battlefield.hibernateTime"))
            {
                messageTo(self, "checkBattlefieldState", params, BATTLEFIELD_HEARTBEAT, false);
            }
            sendSystemMessageTestingOnly(speaker, "Battlefield continued.");
        }
        if (words[0].equals("bfend"))
        {
            utils.removeScriptVar(self, "battlefield.bfSetup");
            utils.removeScriptVar(self, "battlefield.battleTime");
            sendSystemMessageTestingOnly(speaker, "Battlefield ended.");
        }
        return SCRIPT_CONTINUE;
    }
    public int validateTeamPlayer(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        Vector recordList = new Vector();
        recordList.setSize(0);
        if (utils.hasResizeableObjIdBatchScriptVar(self, BATTLEFIELD_ACTIVE_PLAYERS))
        {
            recordList = utils.getResizeableObjIdBatchScriptVar(self, BATTLEFIELD_ACTIVE_PLAYERS);
        }
        params.put("battlefieldController", self);
        location loc = utils.getLocationScriptVar(self, "kickOutLocation");
        if (loc == null)
        {
            loc = getLocation(self);
            loc.x = loc.x + 550;
        }
        params.put("kickOutLocation", loc);
        if (recordList == null || recordList.size() < 1 || !utils.isElementInArray(recordList, player))
        {
            messageTo(player, "invalidBattlefieldPlayer", params, 1.0f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public void bfBattleEnd(obj_id controller) throws InterruptedException
    {
        if (!isIdValid(controller) || !exists(controller))
        {
            return;
        }
        dictionary dict = new dictionary();
        String battlefieldName = getStringObjVar(controller, "battlefieldName");
        dict.put("battlefieldName", battlefieldName);
        pvp.bfMessagePlayersOnBattlefield(controller, BATTLEFIELD_ACTIVE_REBEL_PLAYERS, "receiveBattlefieldFinale", dict);
        pvp.bfMessagePlayersOnBattlefield(controller, BATTLEFIELD_ACTIVE_IMPERIAL_PLAYERS, "receiveBattlefieldFinale", dict);
        Vector battlefieldPlayers = pvp.bfActiveGetStatistics(controller);
        if (battlefieldPlayers == null || battlefieldPlayers.size() < 1)
        {
            return;
        }
        String[][] scoreData = pvp.bfStatisticsToArray(battlefieldPlayers, false);
        if (scoreData == null || scoreData.length < 1)
        {
            return;
        }
        int imperialScore = utils.getIntScriptVar(controller, "battlefield.imperialScore");
        int rebelScore = utils.getIntScriptVar(controller, "battlefield.rebelScore");
        String victory = "";
        if (rebelScore == imperialScore)
        {
            victory = "rebel tie," + rebelScore + ",imperial tie," + imperialScore;
        }
        else if (rebelScore > imperialScore)
        {
            victory = "rebel win," + rebelScore + ",imperial loss," + imperialScore;
        }
        else 
        {
            victory = "rebel loss," + rebelScore + ",imperial win," + imperialScore;
        }
        CustomerServiceLog("battlefield_scores", ",completed," + battlefieldName + "," + victory + ",");
        for (int i = 0, j = scoreData.length; i < j; i++)
        {
            obj_id player = null;
            if (battlefieldPlayers != null && i < battlefieldPlayers.size())
            {
                player = ((dictionary)battlefieldPlayers.get(i)).getObjId("player");
            }
            CustomerServiceLog("battlefield_scores", ",score," + battlefieldName + "," + player + "," + scoreData[i][0] + "," + scoreData[i][1] + "," + scoreData[i][2] + "," + scoreData[i][7] + "," + scoreData[i][3] + "," + scoreData[i][4] + "," + scoreData[i][5] + "," + scoreData[i][6] + ",");
        }
    }
    public void bfActivePlayersStoreScores(obj_id controller, String scriptVar) throws InterruptedException
    {
        if (!isIdValid(controller) || !exists(controller))
        {
            return;
        }
        Vector activeMembers = new Vector();
        activeMembers.setSize(0);
        if (utils.hasResizeableStringBatchScriptVar(controller, scriptVar))
        {
            activeMembers = utils.getResizeableStringBatchScriptVar(controller, scriptVar);
        }
        if (activeMembers == null && activeMembers.size() < 1)
        {
            return;
        }
        int count = 0;
        int totalNewKills = 0;
        int totalNewCaptures = 0;
        for (int i = 0, j = activeMembers.size(); i < j; i++)
        {
            if (((String)activeMembers.get(i)) == null || ((String)activeMembers.get(i)).length() < 1)
            {
                continue;
            }
            String[] playerInfo = split(((String)activeMembers.get(i)), '^');
            if (playerInfo == null || playerInfo.length < 8)
            {
                continue;
            }
            obj_id player = utils.stringToObjId(playerInfo[0]);
            if (!isIdValid(player) || !exists(player))
            {
                continue;
            }
            String calendarTime = playerInfo[1];
            String name = playerInfo[2];
            int oldKills = utils.stringToInt(playerInfo[3]);
            int kills = utils.getIntScriptVar(player, "battlefield.kills");
            totalNewKills += kills - oldKills;
            int deaths = utils.getIntScriptVar(player, "battlefield.deaths");
            int damage = utils.getIntScriptVar(player, "battlefield.damage");
            int healing = utils.getIntScriptVar(player, "battlefield.healing");
            int captures = utils.getIntScriptVar(player, "battlefield.captures");
            int assists = utils.getIntScriptVar(player, "battlefield.assists");
            int oldCaptures = utils.stringToInt(playerInfo[7]);
            totalNewCaptures += captures - oldCaptures;
            activeMembers.set(i, "" + player + "^" + calendarTime + "^" + name + "^" + kills + "^" + deaths + "^" + damage + "^" + healing + "^" + captures + "^" + assists);
        }
        int imperialScore = utils.getIntScriptVar(controller, "battlefield.imperialScore");
        int rebelScore = utils.getIntScriptVar(controller, "battlefield.rebelScore");
        if (totalNewKills > 0)
        {
            Vector terminals = pvp.bfTerminalsGetRegistered(controller);
            int rebelTerminalMitigation = 0;
            int imperialTerminalMitigation = 0;
            if (terminals != null && terminals.size() > 0)
            {
                blog(controller, "terminals: " + terminals.size());
                for (int i = 0, j = terminals.size(); i < j; i++)
                {
                    if (!isIdValid(((obj_id)terminals.get(i))) || !exists(((obj_id)terminals.get(i))))
                    {
                        continue;
                    }
                    int faction = utils.getIntScriptVar(((obj_id)terminals.get(i)), "battlefield.terminal_faction");
                    if (faction == factions.FACTION_FLAG_REBEL)
                    {
                        rebelTerminalMitigation += utils.getIntScriptVar(((obj_id)terminals.get(i)), "battlefield.terminal_mitigation");
                    }
                    else if (faction == factions.FACTION_FLAG_IMPERIAL)
                    {
                        imperialTerminalMitigation += utils.getIntScriptVar(((obj_id)terminals.get(i)), "battlefield.terminal_mitigation");
                    }
                }
            }
            if (rebelTerminalMitigation > 9)
            {
                rebelTerminalMitigation = 9;
            }
            if (imperialTerminalMitigation > 9)
            {
                imperialTerminalMitigation = 9;
            }
            blog(controller, "rebelTerminalMitigation: " + rebelTerminalMitigation + " imperialTerminalMitigation: " + imperialTerminalMitigation);
            if (scriptVar.equals(BATTLEFIELD_ACTIVE_REBEL_PLAYERS))
            {
                imperialScore -= (totalNewKills * (BATTLEFIELD_PLAYER_KILL_VALUE - imperialTerminalMitigation));
                if (imperialScore < 0)
                {
                    imperialScore = 0;
                }
            }
            else 
            {
                rebelScore -= (totalNewKills * (BATTLEFIELD_PLAYER_KILL_VALUE - rebelTerminalMitigation));
                if (rebelScore < 0)
                {
                    rebelScore = 0;
                }
            }
        }
        if (totalNewCaptures > 0)
        {
            if (scriptVar.equals(BATTLEFIELD_ACTIVE_REBEL_PLAYERS))
            {
                imperialScore -= (totalNewCaptures * BATTLEFIELD_TERMINAL_CAPTURE_VALUE);
                if (imperialScore < 0)
                {
                    imperialScore = 0;
                }
            }
            else 
            {
                rebelScore -= (totalNewCaptures * BATTLEFIELD_TERMINAL_CAPTURE_VALUE);
                if (rebelScore < 0)
                {
                    rebelScore = 0;
                }
            }
        }
        utils.setScriptVar(controller, "battlefield.imperialScore", imperialScore);
        utils.setScriptVar(controller, "battlefield.rebelScore", rebelScore);
        if (imperialScore <= 0 || rebelScore <= 0)
        {
            utils.removeScriptVar(controller, "battlefield.battleTime");
        }
        utils.setBatchScriptVar(controller, scriptVar, activeMembers);
    }
    public void bfActivePlayersStoreScores(obj_id controller) throws InterruptedException
    {
        if (!isIdValid(controller) || !exists(controller))
        {
            return;
        }
        bfActivePlayersStoreScores(controller, BATTLEFIELD_ACTIVE_REBEL_PLAYERS);
        bfActivePlayersStoreScores(controller, BATTLEFIELD_ACTIVE_IMPERIAL_PLAYERS);
    }
    public void bfActiveEvenTeams(obj_id controller) throws InterruptedException
    {
        if (!isIdValid(controller) || !exists(controller))
        {
            return;
        }
        int rebelCount = bfActiveGetRebelCount(controller);
        int imperialCount = bfActiveGetImperialCount(controller);
        int removalCount = 0;
        String activeScriptVar = "";
        String queueScriptVar = "";
        int faction = 0;
        if (rebelCount > imperialCount)
        {
            removalCount = rebelCount - imperialCount;
            activeScriptVar = BATTLEFIELD_ACTIVE_REBEL_PLAYERS;
            queueScriptVar = BATTLEFIELD_REBEL_INDIVIDUAL_QUEUE;
            faction = factions.FACTION_FLAG_REBEL;
            blog(controller, "bfActiveEvenTeams Rebels (" + rebelCount + ") outnumber imperials (" + imperialCount + ")");
        }
        else if (imperialCount > rebelCount)
        {
            removalCount = imperialCount - rebelCount;
            activeScriptVar = BATTLEFIELD_ACTIVE_IMPERIAL_PLAYERS;
            queueScriptVar = BATTLEFIELD_IMPERIAL_INDIVIDUAL_QUEUE;
            faction = factions.FACTION_FLAG_IMPERIAL;
            blog(controller, "bfActiveEvenTeams Imperials (" + imperialCount + ") outnumber Rebels (" + rebelCount + ")");
        }
        else 
        {
            blog(controller, "bfActiveEvenTeams Teams are even.  Imperials (" + imperialCount + ") Rebels (" + rebelCount + ")");
            return;
        }
        Vector activePlayers = new Vector();
        activePlayers.setSize(0);
        if (utils.hasResizeableStringBatchScriptVar(controller, activeScriptVar))
        {
            activePlayers = utils.getResizeableStringBatchScriptVar(controller, activeScriptVar);
        }
        if (removalCount > 0 && activePlayers != null && activePlayers.size() > 0)
        {
            for (int i = activePlayers.size() - removalCount, j = activePlayers.size(); i < j; i++)
            {
                String[] activeData = split(((String)activePlayers.get(i)), '^');
                if (activeData == null || activeData.length < 1)
                {
                    continue;
                }
                obj_id playerToRemove = utils.stringToObjId(activeData[0]);
                if (!isIdValid(playerToRemove))
                {
                    continue;
                }
                bfActiveRemovePlayer(controller, playerToRemove, activeScriptVar);
                bfQueueRemovePotentialEnemy(controller, playerToRemove, faction);
                dictionary params = new dictionary();
                String battlefieldName = getStringObjVar(controller, "battlefieldName");
                params.put("battlefieldName", battlefieldName);
                messageTo(playerToRemove, "receiveBattlefieldRefusePlayers", params, 1.0f, false);
            }
        }
    }
    public void bfActiveSetupPlayers(obj_id controller) throws InterruptedException
    {
        if (!isIdValid(controller) || !exists(controller))
        {
            return;
        }
        pvp.bfMessagePlayers(controller, BATTLEFIELD_ACTIVE_REBEL_PLAYERS, "receiveBattlefieldSetupWarp", null);
        pvp.bfMessagePlayers(controller, BATTLEFIELD_ACTIVE_IMPERIAL_PLAYERS, "receiveBattlefieldSetupWarp", null);
    }
    public void bfActivePlayParticlePickup(obj_id controller) throws InterruptedException
    {
        if (!isIdValid(controller) || !exists(controller))
        {
            return;
        }
        pvp.bfMessagePlayers(controller, BATTLEFIELD_ACTIVE_REBEL_PLAYERS, "receiveBattlefieldPlayParticlePickup", null);
        pvp.bfMessagePlayers(controller, BATTLEFIELD_ACTIVE_IMPERIAL_PLAYERS, "receiveBattlefieldPlayParticlePickup", null);
    }
    public void bfActiveWarpPlayers(obj_id controller, int battlefieldState) throws InterruptedException
    {
        if (!isIdValid(controller) || !exists(controller))
        {
            return;
        }
        location loc = utils.getLocationScriptVar(controller, "battlefieldRebelSpawn");
        dictionary params = new dictionary();
        params.put("bfState", battlefieldState);
        params.put("controller", controller);
        if (loc != null)
        {
            params.put("warpLocation", loc);
            pvp.bfMessagePlayers(controller, BATTLEFIELD_ACTIVE_REBEL_PLAYERS, "receiveBattlefieldWarpLocation", params);
        }
        loc = utils.getLocationScriptVar(controller, "battlefieldImperialSpawn");
        if (loc != null)
        {
            params.put("warpLocation", loc);
            pvp.bfMessagePlayers(controller, BATTLEFIELD_ACTIVE_IMPERIAL_PLAYERS, "receiveBattlefieldWarpLocation", params);
        }
    }
    public Vector bfActiveGetPlayersInPvPRegion(obj_id controller, int faction) throws InterruptedException
    {
        if (!isIdValid(controller) || !exists(controller))
        {
            return null;
        }
        Vector recordList = new Vector();
        recordList.setSize(0);
        String factionScriptVar = BATTLEFIELD_ACTIVE_IMPERIAL_PLAYERS;
        if (faction == factions.FACTION_FLAG_REBEL)
        {
            factionScriptVar = BATTLEFIELD_ACTIVE_REBEL_PLAYERS;
        }
        Vector activeTeam = new Vector();
        activeTeam.setSize(0);
        if (utils.hasResizeableStringBatchScriptVar(controller, factionScriptVar))
        {
            activeTeam = utils.getResizeableStringBatchScriptVar(controller, factionScriptVar);
        }
        if (activeTeam == null || activeTeam.size() < 1)
        {
            return null;
        }
        Vector membersInRegion = new Vector();
        membersInRegion.setSize(0);
        for (int i = 0, j = activeTeam.size(); i < j; i++)
        {
            if (((String)activeTeam.get(i)) == null || ((String)activeTeam.get(i)).length() < 1)
            {
                continue;
            }
            String[] memberInfo = split(((String)activeTeam.get(i)), '^');
            if (memberInfo == null || memberInfo.length < 1)
            {
                continue;
            }
            obj_id player = utils.stringToObjId(memberInfo[0]);
            if (!isIdValid(player) || !exists(player) || isIncapacitated(player) || isDead(player))
            {
                continue;
            }
            region[] regionList = getRegionsAtPoint(getLocation(player));
            if (regionList == null || regionList.length < 1)
            {
                continue;
            }
            boolean regionFound = false;
            for (int k = 0, l = regionList.length; k < l && !regionFound; k++)
            {
                String regionName = regionList[k].getName();
                if (regionName != null && regionName.startsWith("pvp_battlefield"))
                {
                    utils.addElement(membersInRegion, player);
                    regionFound = true;
                }
            }
        }
        return membersInRegion;
    }
    public void bfActiveAddPlayer(obj_id controller, obj_id player, String name, int faction) throws InterruptedException
    {
        if (!isIdValid(controller) || !isIdValid(player))
        {
            return;
        }
        Vector recordList = new Vector();
        recordList.setSize(0);
        if (utils.hasResizeableObjIdBatchScriptVar(controller, BATTLEFIELD_ACTIVE_PLAYERS))
        {
            recordList = utils.getResizeableObjIdBatchScriptVar(controller, BATTLEFIELD_ACTIVE_PLAYERS);
        }
        utils.addElement(recordList, player);
        utils.setBatchScriptVar(controller, BATTLEFIELD_ACTIVE_PLAYERS, recordList);
        String activeScriptVar = BATTLEFIELD_ACTIVE_IMPERIAL_PLAYERS;
        String invitedScriptVar = BATTLEFIELD_INVITED_IMPERIAL_PLAYERS;
        if (faction == factions.FACTION_FLAG_REBEL)
        {
            activeScriptVar = BATTLEFIELD_ACTIVE_REBEL_PLAYERS;
            invitedScriptVar = BATTLEFIELD_INVITED_REBEL_PLAYERS;
        }
        Vector activeTeam = new Vector();
        activeTeam.setSize(0);
        Vector newRecordList = new Vector();
        newRecordList.setSize(0);
        if (utils.hasResizeableStringBatchScriptVar(controller, activeScriptVar))
        {
            activeTeam = utils.getResizeableStringBatchScriptVar(controller, activeScriptVar);
        }
        blog(controller, "bfActiveAddPlayer !!!!!!!! player to add: " + player);
        if (activeTeam == null || activeTeam.size() < 1)
        {
            utils.addElement(newRecordList, "" + player + "^" + getCalendarTime() + "^" + name + "^0^0^0^0^0^0");
        }
        else 
        {
            if (getPositionInArrayByPlayer(player, activeTeam) > -1)
            {
                blog(controller, "bfActiveAddPlayer Player: " + player + " already exists in active team list.");
                return;
            }
            Vector invitedList = new Vector();
            invitedList.setSize(0);
            if (utils.hasResizeableObjIdBatchScriptVar(controller, invitedScriptVar))
            {
                invitedList = utils.getResizeableObjIdBatchScriptVar(controller, invitedScriptVar);
            }
            for (int i = 0, j = invitedList.size(); i < j; i++)
            {
                blog(controller, "bfActiveAddPlayer *** invitedList[" + i + "]: " + ((obj_id)invitedList.get(i)));
            }
            for (int i = 0, j = activeTeam.size(); i < j; i++)
            {
                blog(controller, "bfActiveAddPlayer {{{ activeTeam[" + i + "]: " + ((String)activeTeam.get(i)));
            }
            for (int i = 0; i < invitedList.size(); i++)
            {
                if (!isIdValid(((obj_id)invitedList.get(i))))
                {
                    continue;
                }
                int activeIndex = getPositionInArrayByPlayer(((obj_id)invitedList.get(i)), activeTeam);
                if (activeIndex > -1)
                {
                    blog(controller, "bfActiveAddPlayer activeTeam[" + activeIndex + "]: " + ((String)activeTeam.get(activeIndex)));
                    utils.addElement(newRecordList, ((String)activeTeam.get(activeIndex)));
                }
                else if (((obj_id)invitedList.get(i)) == player)
                {
                    blog(controller, "bfActiveAddPlayer invitedList[" + i + "]: " + ((obj_id)invitedList.get(i)) + " == player: " + player);
                    utils.addElement(newRecordList, "" + player + "^" + getCalendarTime() + "^" + name + "^0^0^0^0^0^0");
                }
            }
        }
        for (int i = 0, j = newRecordList.size(); i < j; i++)
        {
            blog(controller, "bfActiveAddPlayer final list newRecordList[" + i + "]: " + ((String)newRecordList.get(i)));
        }
        utils.setBatchScriptVar(controller, activeScriptVar, newRecordList);
    }
    public void bfActiveRemovePlayer(obj_id controller, obj_id player, String activeScriptVar) throws InterruptedException
    {
        if (!isIdValid(controller) || !isIdValid(player))
        {
            return;
        }
        bfQueueRemovePlayer(controller, player, activeScriptVar);
        Vector recordList = new Vector();
        recordList.setSize(0);
        if (utils.hasResizeableObjIdBatchScriptVar(controller, BATTLEFIELD_ACTIVE_PLAYERS))
        {
            recordList = utils.getResizeableObjIdBatchScriptVar(controller, BATTLEFIELD_ACTIVE_PLAYERS);
        }
        Vector newRecordList = new Vector();
        newRecordList.setSize(0);
        if (recordList == null || recordList.size() < 1)
        {
            return;
        }
        for (int i = 0, j = recordList.size(); i < j; i++)
        {
            if (((obj_id)recordList.get(i)) == player)
            {
                continue;
            }
            utils.addElement(newRecordList, ((obj_id)recordList.get(i)));
        }
        if (newRecordList != null && newRecordList.size() > 0)
        {
            utils.setBatchScriptVar(controller, BATTLEFIELD_ACTIVE_PLAYERS, newRecordList);
        }
        else 
        {
            utils.removeScriptVar(controller, BATTLEFIELD_ACTIVE_PLAYERS);
        }
    }
    public int bfObjIdBatchScriptVarSize(obj_id controller, String scriptVar) throws InterruptedException
    {
        if (!isIdValid(controller))
        {
            return 0;
        }
        Vector recordList = new Vector();
        recordList.setSize(0);
        if (utils.hasResizeableObjIdBatchScriptVar(controller, scriptVar))
        {
            recordList = utils.getResizeableObjIdBatchScriptVar(controller, scriptVar);
            if (recordList != null)
            {
                return recordList.size();
            }
        }
        return 0;
    }
    public int bfStringBatchScriptVarSize(obj_id controller, String scriptVar) throws InterruptedException
    {
        if (!isIdValid(controller))
        {
            return 0;
        }
        Vector recordList = new Vector();
        recordList.setSize(0);
        if (utils.hasResizeableStringBatchScriptVar(controller, scriptVar))
        {
            recordList = utils.getResizeableStringBatchScriptVar(controller, scriptVar);
            if (recordList != null)
            {
                return recordList.size();
            }
        }
        return 0;
    }
    public int bfActiveGetPlayerCount(obj_id controller) throws InterruptedException
    {
        return bfObjIdBatchScriptVarSize(controller, BATTLEFIELD_ACTIVE_PLAYERS);
    }
    public int bfActiveGetRebelCount(obj_id controller) throws InterruptedException
    {
        return bfStringBatchScriptVarSize(controller, BATTLEFIELD_ACTIVE_REBEL_PLAYERS);
    }
    public int bfActiveGetImperialCount(obj_id controller) throws InterruptedException
    {
        return bfStringBatchScriptVarSize(controller, BATTLEFIELD_ACTIVE_IMPERIAL_PLAYERS);
    }
    public void bfActiveKickOutPlayers(obj_id controller) throws InterruptedException
    {
        if (!isIdValid(controller))
        {
            return;
        }
        Vector activePlayers = new Vector();
        activePlayers.setSize(0);
        if (utils.hasResizeableObjIdBatchScriptVar(controller, BATTLEFIELD_ACTIVE_PLAYERS))
        {
            activePlayers = utils.getResizeableObjIdBatchScriptVar(controller, BATTLEFIELD_ACTIVE_PLAYERS);
        }
        location rebelLoc = utils.getLocationScriptVar(controller, "rebelKickOutLocation");
        location imperialLoc = utils.getLocationScriptVar(controller, "imperialKickOutLocation");
        for (int i = 0, j = activePlayers.size(); i < j; i++)
        {
            if (isIdValid(((obj_id)activePlayers.get(i))))
            {
                String battlefieldName = getStringObjVar(controller, "battlefieldName");
                dictionary params = new dictionary();
                params.put("battlefieldName", battlefieldName);
                if (exists(((obj_id)activePlayers.get(i))))
                {
                    params.put("warpLocation", (factions.isRebel(((obj_id)activePlayers.get(i))) ? rebelLoc : imperialLoc));
                }
                Vector terminals = pvp.bfTerminalsGetRegistered(controller);
                if (terminals != null && terminals.size() > 0)
                {
                    params.put("terminals", terminals);
                }
                messageTo(((obj_id)activePlayers.get(i)), "receiveBattlefieldOver", params, 0.0f, false);
            }
        }
    }
    public void bfActiveRefusePlayers(obj_id controller) throws InterruptedException
    {
        if (!isIdValid(controller))
        {
            return;
        }
        Vector activePlayers = new Vector();
        activePlayers.setSize(0);
        if (utils.hasResizeableObjIdBatchScriptVar(controller, BATTLEFIELD_ACTIVE_PLAYERS))
        {
            activePlayers = utils.getResizeableObjIdBatchScriptVar(controller, BATTLEFIELD_ACTIVE_PLAYERS);
        }
        for (int i = 0, j = activePlayers.size(); i < j; i++)
        {
            if (isIdValid(((obj_id)activePlayers.get(i))))
            {
                String battlefieldName = getStringObjVar(controller, "battlefieldName");
                dictionary params = new dictionary();
                params.put("battlefieldName", battlefieldName);
                messageTo(((obj_id)activePlayers.get(i)), "receiveBattlefieldRefusePlayers", params, 0.0f, false);
            }
        }
    }
    public int kickPlayerOutInvalid(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        messageTo(player, "invalidBattlefieldPlayer", null, 1.0f, true);
        return SCRIPT_CONTINUE;
    }
    public boolean bfQueueBuildPotentialEnemies(obj_id controller) throws InterruptedException
    {
        if (!isIdValid(controller))
        {
            return false;
        }
        int rebelPlayersSize = bfQueueGetSize(controller, BATTLEFIELD_REBEL_INDIVIDUAL_QUEUE);
        int imperialPlayersSize = bfQueueGetSize(controller, BATTLEFIELD_IMPERIAL_INDIVIDUAL_QUEUE);
        int minimumTeamSize = BATTLEFIELD_MINIMUM_TEAM_SIZE;
        if (getConfigSetting("GameServer", "BfMinimumTeamSize") != null && (getConfigSetting("GameServer", "BfMinimumTeamSize")).length() > 0)
        {
            minimumTeamSize = utils.stringToInt(getConfigSetting("GameServer", "BfMinimumTeamSize"));
        }
        if (rebelPlayersSize < minimumTeamSize || imperialPlayersSize < minimumTeamSize)
        {
            return false;
        }
        int rebelPlayersActiveSize = bfQueueGetSize(controller, BATTLEFIELD_ACTIVE_REBEL_PLAYERS);
        int imperialPlayersActiveSize = bfQueueGetSize(controller, BATTLEFIELD_ACTIVE_IMPERIAL_PLAYERS);
        blog(controller, "bfQueueBuildPotentialEnemies rebelPlayersSize: " + rebelPlayersSize + " imperialPlayersSize: " + imperialPlayersSize + " minimumTeamSize: " + minimumTeamSize);
        Vector rebels = new Vector();
        rebels.setSize(0);
        Vector imperials = new Vector();
        imperials.setSize(0);
        int potentialRebels = 0;
        int potentialImperials = 0;
        int maxTeamSize = BATTLEFIELD_MAXIMUM_TEAM_SIZE;
        if (getConfigSetting("GameServer", "BfTeamSize") != null && (getConfigSetting("GameServer", "BfTeamSize")).length() > 0)
        {
            maxTeamSize = utils.stringToInt(getConfigSetting("GameServer", "BfTeamSize"));
        }
        blog(controller, "bfQueueBuildPotentialEnemies maxTeamSize: " + maxTeamSize);
        for (int i = 0, j = (rebelPlayersSize < maxTeamSize - potentialRebels ? rebelPlayersSize : maxTeamSize - potentialRebels); i < j; i++)
        {
            utils.addElement(rebels, bfQueueGetPlayerAtIndex(controller, i, BATTLEFIELD_REBEL_INDIVIDUAL_QUEUE));
        }
        if (rebels != null && rebels.size() > 0)
        {
            blog(controller, "bfQueueBuildPotentialEnemies potential rebels: " + rebels.size());
            utils.setBatchScriptVar(controller, BATTLEFIELD_POTENTIAL_REBEL_PLAYERS, rebels);
        }
        for (int i = 0, j = (imperialPlayersSize < maxTeamSize - potentialImperials ? imperialPlayersSize : maxTeamSize - potentialImperials); i < j; i++)
        {
            utils.addElement(imperials, bfQueueGetPlayerAtIndex(controller, i, BATTLEFIELD_IMPERIAL_INDIVIDUAL_QUEUE));
        }
        if (imperials != null && imperials.size() > 0)
        {
            blog(controller, "bfQueueBuildPotentialEnemies potential imperials: " + imperials.size());
            utils.setBatchScriptVar(controller, BATTLEFIELD_POTENTIAL_IMPERIAL_PLAYERS, imperials);
        }
        return true;
    }
    public void bfQueueRemovePotentialEnemy(obj_id controller, obj_id player, int faction) throws InterruptedException
    {
        if (!isIdValid(controller) || !isIdValid(player))
        {
            return;
        }
        int rebelPlayersSize = bfQueueGetSize(controller, BATTLEFIELD_REBEL_INDIVIDUAL_QUEUE);
        int rebelGroupsSize = bfQueueGetSize(controller, BATTLEFIELD_REBEL_GROUP_QUEUE);
        int imperialPlayersSize = bfQueueGetSize(controller, BATTLEFIELD_IMPERIAL_INDIVIDUAL_QUEUE);
        int imperialGroupsSize = bfQueueGetSize(controller, BATTLEFIELD_IMPERIAL_GROUP_QUEUE);
        if (rebelPlayersSize < 1 && rebelGroupsSize < 1 && imperialPlayersSize < 1 && imperialGroupsSize < 1)
        {
            return;
        }
        blog(controller, "rebelPlayersSize: " + rebelPlayersSize + " rebelGroupsSize: " + rebelGroupsSize + " imperialPlayersSize: " + imperialPlayersSize + " imperialGroupsSize: " + imperialGroupsSize);
        Vector rebels = new Vector();
        rebels.setSize(0);
        Vector imperials = new Vector();
        imperials.setSize(0);
        Vector newRebels = new Vector();
        newRebels.setSize(0);
        Vector newImperials = new Vector();
        newImperials.setSize(0);
        if (faction == factions.FACTION_FLAG_REBEL)
        {
            if (!utils.hasResizeableObjIdBatchScriptVar(controller, BATTLEFIELD_POTENTIAL_REBEL_PLAYERS))
            {
                return;
            }
            rebels = utils.getResizeableObjIdBatchScriptVar(controller, BATTLEFIELD_POTENTIAL_REBEL_PLAYERS);
            for (int i = 0, j = rebels.size(); i < j; i++)
            {
                if (((obj_id)rebels.get(i)) != player)
                {
                    utils.addElement(newRebels, ((obj_id)rebels.get(i)));
                }
            }
            utils.setBatchScriptVar(controller, BATTLEFIELD_POTENTIAL_REBEL_PLAYERS, newRebels);
        }
        if (faction == factions.FACTION_FLAG_IMPERIAL)
        {
            if (!utils.hasResizeableObjIdBatchScriptVar(controller, BATTLEFIELD_POTENTIAL_IMPERIAL_PLAYERS))
            {
                return;
            }
            imperials = utils.getResizeableObjIdBatchScriptVar(controller, BATTLEFIELD_POTENTIAL_IMPERIAL_PLAYERS);
            for (int i = 0, j = imperials.size(); i < j; i++)
            {
                if (((obj_id)imperials.get(i)) != player)
                {
                    utils.addElement(newImperials, ((obj_id)imperials.get(i)));
                }
            }
            utils.setBatchScriptVar(controller, BATTLEFIELD_POTENTIAL_IMPERIAL_PLAYERS, newImperials);
        }
    }
    public void bfQueueClearPotentialEnemies(obj_id controller, boolean clearQueue) throws InterruptedException
    {
        if (!isIdValid(controller))
        {
            return;
        }
        Vector rebels = new Vector();
        rebels.setSize(0);
        Vector imperials = new Vector();
        imperials.setSize(0);
        if (utils.hasResizeableObjIdBatchScriptVar(controller, BATTLEFIELD_POTENTIAL_REBEL_PLAYERS))
        {
            rebels = utils.getResizeableObjIdBatchScriptVar(controller, BATTLEFIELD_POTENTIAL_REBEL_PLAYERS);
        }
        if (utils.hasResizeableObjIdBatchScriptVar(controller, BATTLEFIELD_POTENTIAL_IMPERIAL_PLAYERS))
        {
            imperials = utils.getResizeableObjIdBatchScriptVar(controller, BATTLEFIELD_POTENTIAL_IMPERIAL_PLAYERS);
        }
        if (rebels != null && rebels.size() > 0 && clearQueue)
        {
            for (int i = 0, j = rebels.size(); i < j; i++)
            {
                if (!isIdValid(((obj_id)rebels.get(i))))
                {
                    continue;
                }
                bfQueueRemovePlayer(controller, ((obj_id)rebels.get(i)), factions.FACTION_FLAG_REBEL);
            }
        }
        if (imperials != null && imperials.size() > 0 && clearQueue)
        {
            for (int i = 0, j = imperials.size(); i < j; i++)
            {
                if (!isIdValid(((obj_id)imperials.get(i))))
                {
                    continue;
                }
                bfQueueRemovePlayer(controller, ((obj_id)imperials.get(i)), factions.FACTION_FLAG_IMPERIAL);
            }
        }
        utils.removeBatchScriptVar(controller, BATTLEFIELD_POTENTIAL_REBEL_PLAYERS);
        utils.removeBatchScriptVar(controller, BATTLEFIELD_POTENTIAL_IMPERIAL_PLAYERS);
    }
    public void bfQueueSendInvitations(obj_id controller) throws InterruptedException
    {
        if (!isIdValid(controller))
        {
            return;
        }
        Vector rebels = new Vector();
        rebels.setSize(0);
        Vector imperials = new Vector();
        imperials.setSize(0);
        Vector invitedRebels = new Vector();
        invitedRebels.setSize(0);
        Vector invitedImperials = new Vector();
        invitedImperials.setSize(0);
        if (utils.hasResizeableObjIdBatchScriptVar(controller, BATTLEFIELD_POTENTIAL_REBEL_PLAYERS))
        {
            rebels = utils.getResizeableObjIdBatchScriptVar(controller, BATTLEFIELD_POTENTIAL_REBEL_PLAYERS);
        }
        if (utils.hasResizeableObjIdBatchScriptVar(controller, BATTLEFIELD_POTENTIAL_IMPERIAL_PLAYERS))
        {
            imperials = utils.getResizeableObjIdBatchScriptVar(controller, BATTLEFIELD_POTENTIAL_IMPERIAL_PLAYERS);
        }
        if (utils.hasResizeableObjIdBatchScriptVar(controller, BATTLEFIELD_INVITED_REBEL_PLAYERS))
        {
            invitedRebels = utils.getResizeableObjIdBatchScriptVar(controller, BATTLEFIELD_INVITED_REBEL_PLAYERS);
        }
        if (utils.hasResizeableObjIdBatchScriptVar(controller, BATTLEFIELD_INVITED_IMPERIAL_PLAYERS))
        {
            invitedImperials = utils.getResizeableObjIdBatchScriptVar(controller, BATTLEFIELD_INVITED_IMPERIAL_PLAYERS);
        }
        dictionary params = new dictionary();
        String battlefieldName = getStringObjVar(controller, "battlefieldName");
        if (battlefieldName == null || battlefieldName.length() < 1)
        {
            battlefieldName = "none";
        }
        params.put("battlefieldController", controller);
        params.put("battlefieldName", localize(new string_id("spam", battlefieldName)));
        if (rebels != null && rebels.size() > 0)
        {
            location loc = utils.getLocationScriptVar(controller, "battlefieldRebelSpawn");
            params.put("factionToMatch", factions.FACTION_FLAG_REBEL);
            if (loc != null)
            {
                params.put("warpInLocation", loc);
                for (int i = 0, j = rebels.size(); i < j; i++)
                {
                    if (!isIdValid(((obj_id)rebels.get(i))) || utils.isElementInArray(invitedRebels, ((obj_id)rebels.get(i))))
                    {
                        continue;
                    }
                    messageTo(((obj_id)rebels.get(i)), "receiveBattlefieldInvitation", params, 1.0f, false);
                    utils.addElement(invitedRebels, ((obj_id)rebels.get(i)));
                }
            }
        }
        if (imperials != null && imperials.size() > 0)
        {
            location loc = utils.getLocationScriptVar(controller, "battlefieldImperialSpawn");
            params.put("factionToMatch", factions.FACTION_FLAG_IMPERIAL);
            if (loc != null)
            {
                params.put("warpInLocation", loc);
                for (int i = 0, j = imperials.size(); i < j; i++)
                {
                    if (!isIdValid(((obj_id)imperials.get(i))) || utils.isElementInArray(invitedImperials, ((obj_id)imperials.get(i))))
                    {
                        continue;
                    }
                    messageTo(((obj_id)imperials.get(i)), "receiveBattlefieldInvitation", params, 1.0f, false);
                    utils.addElement(invitedImperials, ((obj_id)imperials.get(i)));
                }
            }
        }
        utils.setBatchScriptVar(controller, BATTLEFIELD_INVITED_REBEL_PLAYERS, invitedRebels);
        utils.setBatchScriptVar(controller, BATTLEFIELD_INVITED_IMPERIAL_PLAYERS, invitedImperials);
    }
    public void bfQueueClearInvitations(obj_id controller) throws InterruptedException
    {
        if (!isIdValid(controller))
        {
            return;
        }
        utils.removeBatchScriptVar(controller, BATTLEFIELD_INVITED_REBEL_PLAYERS);
        utils.removeBatchScriptVar(controller, BATTLEFIELD_INVITED_IMPERIAL_PLAYERS);
    }
    public void bfQueueAddPlayerToTop(obj_id controller, obj_id player, String factionScriptVar) throws InterruptedException
    {
        if (!isIdValid(controller) || !isIdValid(player))
        {
            return;
        }
        Vector recordList = new Vector();
        recordList.setSize(0);
        if (utils.hasResizeableStringBatchScriptVar(controller, factionScriptVar))
        {
            recordList = utils.getResizeableStringBatchScriptVar(controller, factionScriptVar);
        }
        Vector newRecordList = new Vector();
        newRecordList.setSize(0);
        if (recordList == null || recordList.size() < 1 || getPlayerDataFromList(player, recordList) == null)
        {
            utils.removeBatchScriptVar(controller, factionScriptVar);
            String newEntry = "" + player + "^" + getCalendarTime();
            utils.addElement(newRecordList, newEntry);
            for (int i = 0, j = recordList.size(); i < j; i++)
            {
                utils.addElement(newRecordList, ((String)recordList.get(i)));
            }
            utils.setBatchScriptVar(controller, factionScriptVar, newRecordList);
            blog(controller, "addPlayerToQueueList newEntry: " + newEntry);
        }
        else 
        {
            blog(controller, "addPlayerToQueueList player already exists in queue: " + player);
        }
    }
    public void bfQueueAddPlayer(obj_id controller, obj_id player, String factionScriptVar) throws InterruptedException
    {
        if (!isIdValid(controller) || !isIdValid(player))
        {
            return;
        }
        Vector recordList = new Vector();
        recordList.setSize(0);
        if (utils.hasResizeableStringBatchScriptVar(controller, factionScriptVar))
        {
            recordList = utils.getResizeableStringBatchScriptVar(controller, factionScriptVar);
        }
        if (recordList == null || recordList.size() < 1 || getPlayerDataFromList(player, recordList) == null)
        {
            utils.removeBatchScriptVar(controller, factionScriptVar);
            String newEntry = "" + player + "^" + getCalendarTime();
            utils.addElement(recordList, newEntry);
            utils.setBatchScriptVar(controller, factionScriptVar, recordList);
            blog(controller, "addPlayerToQueueList newEntry: " + newEntry);
        }
        else 
        {
            blog(controller, "addPlayerToQueueList player already exists in queue: " + player);
        }
        dictionary params = new dictionary();
        String battlefieldName = getStringObjVar(controller, "battlefieldName");
        params.put("battlefieldName", battlefieldName);
        messageTo(player, "receiveBattlefieldQueueAcceptance", params, 1.0f, false);
    }
    public void bfQueueRemovePlayer(obj_id controller, obj_id player, String factionScriptVar) throws InterruptedException
    {
        if (!isIdValid(controller) || !isIdValid(player))
        {
            return;
        }
        Vector recordList = new Vector();
        recordList.setSize(0);
        Vector newRecordList = new Vector();
        newRecordList.setSize(0);
        if (utils.hasResizeableStringBatchScriptVar(controller, factionScriptVar))
        {
            recordList = utils.getResizeableStringBatchScriptVar(controller, factionScriptVar);
        }
        int position = getPositionInArrayByPlayer(player, recordList);
        if (position < 0)
        {
            blog(controller, "bfQueueRemovePlayer player position: " + position + " factionScriptVar: " + factionScriptVar);
            return;
        }
        utils.removeBatchScriptVar(controller, factionScriptVar);
        for (int i = 0, j = recordList.size(); i < j; i++)
        {
            if (i != position)
            {
                utils.addElement(newRecordList, ((String)recordList.get(i)));
            }
        }
        utils.setBatchScriptVar(controller, factionScriptVar, newRecordList);
        blog(controller, "Queue removed player: " + player);
    }
    public obj_id bfQueueGetPlayerAtIndex(obj_id controller, int index, String factionScriptVar) throws InterruptedException
    {
        if (!isIdValid(controller))
        {
            return null;
        }
        Vector recordList = new Vector();
        recordList.setSize(0);
        if (utils.hasResizeableStringBatchScriptVar(controller, factionScriptVar))
        {
            recordList = utils.getResizeableStringBatchScriptVar(controller, factionScriptVar);
        }
        if (recordList == null || recordList.size() < 1 || recordList.size() <= index)
        {
            return null;
        }
        String[] parse = split(((String)recordList.get(index)), '^');
        blog(controller, "bfQueueGetPlayerAtIndex: " + index + " " + parse[0] + " factionScriptVar: " + factionScriptVar);
        return utils.stringToObjId(parse[0]);
    }
    public int bfQueueGetSize(obj_id controller, String factionScriptVar) throws InterruptedException
    {
        if (!isIdValid(controller))
        {
            return 0;
        }
        Vector recordList = new Vector();
        recordList.setSize(0);
        if (utils.hasResizeableStringBatchScriptVar(controller, factionScriptVar))
        {
            recordList = utils.getResizeableStringBatchScriptVar(controller, factionScriptVar);
            if (recordList != null && recordList.size() > 0)
            {
                return recordList.size();
            }
        }
        return 0;
    }
    public void bfQueueAddGroup(obj_id controller, obj_id group, obj_id[] members, String factionScriptVar) throws InterruptedException
    {
        if (!isIdValid(controller))
        {
            return;
        }
        Vector recordList = new Vector();
        recordList.setSize(0);
        if (utils.hasResizeableStringBatchScriptVar(controller, factionScriptVar))
        {
            recordList = utils.getResizeableStringBatchScriptVar(controller, factionScriptVar);
        }
        utils.removeBatchScriptVar(controller, factionScriptVar);
        dictionary params = new dictionary();
        String battlefieldName = getStringObjVar(controller, "battlefieldName");
        params.put("battlefieldName", battlefieldName);
        for (int i = 0, j = members.length; i < j; i++)
        {
            if (!isIdValid(members[i]))
            {
                continue;
            }
            boolean skipPlayer = false;
            if (recordList != null && recordList.size() > 0)
            {
                String playerData = getPlayerDataFromList(members[i], recordList);
                if (playerData != null)
                {
                    blog(controller, "bfQueueAddGroup oldEntry: " + playerData);
                    String[] splitData = split(playerData, '^');
                    if (splitData != null && splitData.length > 0)
                    {
                        skipPlayer = true;
                    }
                }
            }
            if (!skipPlayer)
            {
                String newEntry = "" + members[i] + "^" + getCalendarTime();
                utils.addElement(recordList, newEntry);
                blog(controller, "bfQueueAddGroup newEntry: " + newEntry);
            }
            params.put("controller", controller);
            messageTo(members[i], "receiveBattlefieldQueueAcceptance", params, 1.0f, false);
        }
        utils.setBatchScriptVar(controller, factionScriptVar, recordList);
    }
    public obj_id[] bfQueueRemoveGroup(obj_id controller, obj_id group, int faction) throws InterruptedException
    {
        if (!isIdValid(controller) || !isIdValid(group))
        {
            return null;
        }
        String factionScriptVar = "";
        if (faction == factions.FACTION_FLAG_REBEL)
        {
            factionScriptVar = BATTLEFIELD_REBEL_GROUP_QUEUE;
        }
        else if (faction == factions.FACTION_FLAG_IMPERIAL)
        {
            factionScriptVar = BATTLEFIELD_IMPERIAL_GROUP_QUEUE;
        }
        else 
        {
            blog(controller, "bfQueueRemoveGroup bad faction: " + faction);
            return null;
        }
        Vector recordList = new Vector();
        recordList.setSize(0);
        if (utils.hasResizeableStringBatchScriptVar(controller, factionScriptVar))
        {
            recordList = utils.getResizeableStringBatchScriptVar(controller, factionScriptVar);
        }
        Vector groupData = getGroupDataFromList(group, recordList);
        if (groupData == null || groupData.size() < 1)
        {
            blog(controller, "bfQueueRemoveGroup groupData == null");
            return null;
        }
        obj_id[] members = new obj_id[groupData.size()];
        for (int i = 0, j = groupData.size(); i < j; i++)
        {
            if (((String)groupData.get(i)) == null)
            {
                members[i] = null;
                continue;
            }
            String[] playerData = split(((String)groupData.get(i)), '^');
            obj_id player = utils.stringToObjId(playerData[0]);
            members[i] = player;
            bfQueueRemovePlayer(controller, player, faction);
        }
        blog(controller, "Dequeued group: " + group);
        return members;
    }
    public Vector bfQueueGetGroupNum(obj_id controller, int groupIndex, String factionScriptVar) throws InterruptedException
    {
        if (!isIdValid(controller) || groupIndex < 0)
        {
            return null;
        }
        Vector recordList = new Vector();
        recordList.setSize(0);
        if (utils.hasResizeableStringBatchScriptVar(controller, factionScriptVar))
        {
            recordList = utils.getResizeableStringBatchScriptVar(controller, factionScriptVar);
        }
        if (recordList == null || recordList.size() < 1)
        {
            return null;
        }
        String[] playerInfo = split(((String)recordList.get(0)), '^');
        obj_id group = null;
        if (playerInfo != null && playerInfo.length > 1)
        {
            group = utils.stringToObjId(playerInfo[1]);
        }
        obj_id lastGroup = group;
        obj_id currentGroup = group;
        int currentIndex = 0;
        Vector groupList = new Vector();
        groupList.setSize(0);
        for (int i = 0, j = recordList.size(); i < j; i++)
        {
            if (((String)recordList.get(i)) == null || ((String)recordList.get(i)).length() < 1)
            {
                continue;
            }
            playerInfo = split(((String)recordList.get(i)), '^');
            if (playerInfo == null || playerInfo.length < 2)
            {
                continue;
            }
            currentGroup = utils.stringToObjId(playerInfo[1]);
            if (lastGroup != currentGroup)
            {
                currentIndex++;
                if (currentIndex > groupIndex)
                {
                    return groupList;
                }
            }
            lastGroup = currentGroup;
            if (currentIndex == groupIndex)
            {
                blog(controller, "bfQueueGetGroupNum currentIndex: " + currentIndex + " groupIndex: " + groupIndex + " for " + ((String)recordList.get(i)));
                utils.addElement(groupList, ((String)recordList.get(i)));
            }
        }
        return groupList;
    }
    public void bfQueueClear(obj_id controller) throws InterruptedException
    {
        if (!isIdValid(controller))
        {
            return;
        }
        utils.removeBatchScriptVar(controller, BATTLEFIELD_REBEL_INDIVIDUAL_QUEUE);
        utils.removeBatchScriptVar(controller, BATTLEFIELD_IMPERIAL_INDIVIDUAL_QUEUE);
        utils.removeBatchScriptVar(controller, BATTLEFIELD_REBEL_GROUP_QUEUE);
        utils.removeBatchScriptVar(controller, BATTLEFIELD_IMPERIAL_GROUP_QUEUE);
        bfCleanup(controller);
    }
    public void bfCleanup(obj_id controller) throws InterruptedException
    {
        if (!isIdValid(controller))
        {
            return;
        }
        bfQueueClearPotentialEnemies(controller, true);
        bfQueueClearInvitations(controller);
        clearBattlefieldActivePlayers(controller);
        utils.removeScriptVar(controller, "battlefield.queueTime");
        utils.removeScriptVar(controller, "battlefield.inviteAnnounce");
        utils.removeScriptVar(controller, "battlefield.battleTime");
        utils.removeScriptVar(controller, "battlefield.battleTimeAnnounce");
        utils.removeScriptVar(controller, "battlefield.hibernateTime");
        pvp.bfClearRunner(controller);
    }
    public void clearBattlefieldActivePlayers(obj_id controller) throws InterruptedException
    {
        if (!isIdValid(controller))
        {
            return;
        }
        utils.removeBatchScriptVar(controller, BATTLEFIELD_ACTIVE_PLAYERS);
        utils.removeBatchScriptVar(controller, BATTLEFIELD_ACTIVE_REBEL_PLAYERS);
        utils.removeBatchScriptVar(controller, BATTLEFIELD_ACTIVE_IMPERIAL_PLAYERS);
    }
    public int addPlayerToQueue(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        int faction = params.getInt("faction");
        if (faction == factions.FACTION_FLAG_REBEL)
        {
            bfQueueAddPlayer(self, player, BATTLEFIELD_REBEL_INDIVIDUAL_QUEUE);
        }
        else if (faction == factions.FACTION_FLAG_IMPERIAL)
        {
            bfQueueAddPlayer(self, player, BATTLEFIELD_IMPERIAL_INDIVIDUAL_QUEUE);
        }
        else 
        {
            blog(self, "addPlayerToQueue bad faction: " + faction);
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, "battlefield.hibernateTime"))
        {
            battlefieldUnhibernate(self);
        }
        return SCRIPT_CONTINUE;
    }
    public void bfQueueRemovePlayer(obj_id controller, obj_id player, int faction) throws InterruptedException
    {
        if (faction == factions.FACTION_FLAG_REBEL)
        {
            bfQueueRemovePlayer(controller, player, BATTLEFIELD_REBEL_INDIVIDUAL_QUEUE);
            bfQueueRemovePlayer(controller, player, BATTLEFIELD_REBEL_GROUP_QUEUE);
        }
        else if (faction == factions.FACTION_FLAG_IMPERIAL)
        {
            bfQueueRemovePlayer(controller, player, BATTLEFIELD_IMPERIAL_INDIVIDUAL_QUEUE);
            bfQueueRemovePlayer(controller, player, BATTLEFIELD_IMPERIAL_GROUP_QUEUE);
        }
        else 
        {
            blog(controller, "bfQueueRemovePlayer bad faction: " + faction);
        }
        bfQueueRemovePotentialEnemy(controller, player, faction);
    }
    public String bfQueueGetPlayerInfo(obj_id controller, obj_id player, int faction) throws InterruptedException
    {
        if (!isIdValid(controller) || !exists(controller) || !isIdValid(player))
        {
            return null;
        }
        String playerInfo = "";
        String individualQueue = "";
        String groupQueue = "";
        Vector recordList = null;
        if (faction == factions.FACTION_FLAG_REBEL)
        {
            individualQueue = BATTLEFIELD_REBEL_INDIVIDUAL_QUEUE;
            groupQueue = BATTLEFIELD_REBEL_GROUP_QUEUE;
        }
        else if (faction == factions.FACTION_FLAG_IMPERIAL)
        {
            individualQueue = BATTLEFIELD_IMPERIAL_INDIVIDUAL_QUEUE;
            groupQueue = BATTLEFIELD_IMPERIAL_GROUP_QUEUE;
        }
        else 
        {
            return null;
        }
        if (utils.hasResizeableStringBatchScriptVar(controller, individualQueue))
        {
            recordList = utils.getResizeableStringBatchScriptVar(controller, individualQueue);
        }
        playerInfo = getPlayerDataFromList(player, recordList);
        if (playerInfo == null || playerInfo.length() < 1)
        {
            if (utils.hasResizeableStringBatchScriptVar(controller, groupQueue))
            {
                recordList = utils.getResizeableStringBatchScriptVar(controller, groupQueue);
            }
            playerInfo = getPlayerDataFromList(player, recordList);
            if (playerInfo == null || playerInfo.length() < 1)
            {
                return null;
            }
            playerInfo += "^topQueue";
        }
        else 
        {
            playerInfo += "^bottomQueue";
        }
        return playerInfo;
    }
    public int removePlayerFromQueue(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        int faction = params.getInt("faction");
        obj_id[] members = params.getObjIdArray("members");
        if (members != null && members.length > 0)
        {
            for (int i = 0, j = members.length; i < j; i++)
            {
                if (!isIdValid(members[i]))
                {
                    continue;
                }
                bfQueueRemovePlayer(self, members[i], faction);
                params.put("battlefieldController", self);
                String battlefieldName = getStringObjVar(self, "battlefieldName");
                if (battlefieldName != null || battlefieldName.length() > 0)
                {
                    params.put("battlefieldName", battlefieldName);
                }
                messageTo(members[i], "receiveBattlefieldQueueRemoval", params, 1.0f, false);
            }
            return SCRIPT_CONTINUE;
        }
        bfQueueRemovePlayer(self, player, faction);
        params.put("battlefieldController", self);
        String battlefieldName = getStringObjVar(self, "battlefieldName");
        if (battlefieldName != null || battlefieldName.length() > 0)
        {
            params.put("battlefieldName", battlefieldName);
        }
        messageTo(player, "receiveBattlefieldQueueRemoval", params, 1.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int addGroupToQueue(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] members = params.getObjIdArray("members");
        obj_id group = params.getObjId("group");
        if (!isIdValid(group) || members == null || members.length < 1)
        {
            return SCRIPT_CONTINUE;
        }
        int faction = params.getInt("faction");
        if (faction == factions.FACTION_FLAG_REBEL)
        {
            bfQueueAddGroup(self, group, members, BATTLEFIELD_REBEL_INDIVIDUAL_QUEUE);
        }
        else if (faction == factions.FACTION_FLAG_IMPERIAL)
        {
            bfQueueAddGroup(self, group, members, BATTLEFIELD_IMPERIAL_INDIVIDUAL_QUEUE);
        }
        else 
        {
            blog(self, "bfQueueRemovePlayer bad faction: " + faction);
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, "battlefield.hibernateTime"))
        {
            battlefieldUnhibernate(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int removeGroupFromQueue(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id group = params.getObjId("group");
        blog(self, "removeGroupFromQueue");
        if (!isIdValid(group))
        {
            return SCRIPT_CONTINUE;
        }
        int faction = params.getInt("faction");
        obj_id[] members = bfQueueRemoveGroup(self, group, faction);
        if (members == null || members.length < 1)
        {
            return SCRIPT_CONTINUE;
        }
        params.put("battlefieldController", self);
        String battlefieldName = getStringObjVar(self, "battlefieldName");
        if (battlefieldName != null || battlefieldName.length() > 0)
        {
            params.put("battlefieldName", battlefieldName);
        }
        for (int i = 0, j = members.length; i < j; i++)
        {
            if (!isIdValid(members[i]))
            {
                continue;
            }
            messageTo(members[i], "receiveBattlefieldQueueRemoval", params, 1.0f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int acceptBattlefieldInvitation(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        blog(self, "acceptBattlefieldInvitation player: " + player);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        int faction = params.getInt("faction");
        String name = params.getString("name");
        String[] nameInfo = split(name, '^');
        if (nameInfo.length > 1)
        {
            for (int i = 0, j = nameInfo.length; i < j; i++)
            {
                name += nameInfo[i];
            }
        }
        bfActiveAddPlayer(self, player, name, faction);
        return SCRIPT_CONTINUE;
    }
    public int refuseBattlefieldInvitation(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        blog(self, "refuseBattlefieldInvitation player: " + player);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        int faction = params.getInt("faction");
        bfQueueRemovePlayer(self, player, faction);
        return SCRIPT_CONTINUE;
    }
    public int getBattlefieldStatus(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        int faction = params.getInt("faction");
        Vector recordList = new Vector();
        recordList.setSize(0);
        int bfState = utils.getIntScriptVar(self, "battlefield.state");
        String battlefieldName = getStringObjVar(self, "battlefieldName");
        if (battlefieldName == null || battlefieldName.length() < 1)
        {
            return SCRIPT_CONTINUE;
        }
        String playerInfo = bfQueueGetPlayerInfo(self, player, faction);
        params.put("battlefieldState", bfState);
        if (bfState >= 0 && bfState < BATTLEFIELD_STATUS.length)
        {
            params.put("battlefieldStatus", BATTLEFIELD_STATUS[bfState]);
        }
        if (playerInfo != null && playerInfo.length() > 0)
        {
            params.put("playerInfo", playerInfo);
        }
        params.put("rebelPlayers", bfQueueGetSize(self, BATTLEFIELD_REBEL_INDIVIDUAL_QUEUE));
        params.put("rebelGroups", bfQueueGetSize(self, BATTLEFIELD_REBEL_GROUP_QUEUE));
        params.put("imperialPlayers", bfQueueGetSize(self, BATTLEFIELD_IMPERIAL_INDIVIDUAL_QUEUE));
        params.put("imperialGroups", bfQueueGetSize(self, BATTLEFIELD_IMPERIAL_GROUP_QUEUE));
        params.put("rebelPlayersActive", bfQueueGetSize(self, BATTLEFIELD_ACTIVE_REBEL_PLAYERS));
        params.put("imperialPlayersActive", bfQueueGetSize(self, BATTLEFIELD_ACTIVE_IMPERIAL_PLAYERS));
        params.put("battlefieldInvitationTime", utils.getIntScriptVar(self, "battlefield.queueTime"));
        params.put("battlefieldSetupTime", utils.getIntScriptVar(self, "battlefield.bfSetupTime"));
        params.put("battlefieldTime", utils.getIntScriptVar(self, "battlefield.battleTime"));
        params.put("battlefieldName", localize(new string_id("spam", battlefieldName)));
        params.put("battlefieldNameUnlocalized", battlefieldName);
        messageTo(player, "receiveBattlefieldStatus", params, 1.0f, false);
        return SCRIPT_CONTINUE;
    }
    public void bfChooseRunner(obj_id controller) throws InterruptedException
    {
        if (!isIdValid(controller) || !exists(controller))
        {
            return;
        }
        int lastFaction = utils.getIntScriptVar(controller, "battlefield.lastRunnerFaction");
        if (lastFaction != factions.FACTION_FLAG_IMPERIAL && lastFaction != factions.FACTION_FLAG_REBEL)
        {
            if (rand(0, 1) == 0)
            {
                lastFaction = factions.FACTION_FLAG_IMPERIAL;
            }
            else 
            {
                lastFaction = factions.FACTION_FLAG_REBEL;
            }
        }
        int thisFaction = factions.FACTION_FLAG_IMPERIAL;
        if (lastFaction == factions.FACTION_FLAG_IMPERIAL)
        {
            thisFaction = factions.FACTION_FLAG_REBEL;
        }
        Vector terminals = pvp.bfTerminalsGetRegistered(controller);
        if (terminals == null || terminals.size() < 1)
        {
            blog(controller, "bfChooseRunner terminals == null");
            return;
        }
        Vector playersInRegion = bfActiveGetPlayersInPvPRegion(controller, thisFaction);
        if (playersInRegion == null || playersInRegion.size() < 1)
        {
            blog(controller, "bfChooseRunner playersInRegion == null");
            return;
        }
        else 
        {
            blog(controller, "bfChooseRunner playersInRegion.length: " + playersInRegion.size() + " " + ((obj_id)playersInRegion.get(0)));
        }
        obj_id player = ((obj_id)playersInRegion.get(rand(0, playersInRegion.size() - 1)));
        if (!isIdValid(player) || !exists(player))
        {
            blog(controller, "bfChooseRunner !isIdValid(player)");
            return;
        }
        utils.setScriptVar(controller, "battlefield.lastRunnerFaction", thisFaction);
        pvp.bfSetRunner(controller, player);
        utils.setScriptVar(controller, "battlefield.runnerTime", getGameTime());
        buff.applyBuff(player, "battlefield_communication_run");
        if (thisFaction == factions.FACTION_FLAG_IMPERIAL)
        {
            pvp.bfActivePlayersAnnounce(controller, new string_id("spam", "battlefield_running_imperials"));
        }
        else 
        {
            pvp.bfActivePlayersAnnounce(controller, new string_id("spam", "battlefield_running_rebels"));
        }
        obj_id furthestTerminal = null;
        float furthestDistance = 0.0f;
        for (int i = 0, j = terminals.size(); i < j; i++)
        {
            if (!isIdValid(((obj_id)terminals.get(i))) || !exists(((obj_id)terminals.get(i))))
            {
                continue;
            }
            float terminalDistance = getDistance(player, ((obj_id)terminals.get(i)));
            if (furthestDistance < terminalDistance)
            {
                furthestTerminal = ((obj_id)terminals.get(i));
                furthestDistance = terminalDistance;
            }
        }
        utils.setScriptVar(controller, "battlefield.runnerTerminal", furthestTerminal);
        utils.setScriptVar(furthestTerminal, "battlefield.terminalRunner", player);
        dictionary dict = new dictionary();
        dict.put("terminal_location", getWorldLocation(furthestTerminal));
        dict.put("terminal", furthestTerminal);
        blog(controller, "bfChooseRunner furthestTerminal: " + furthestTerminal + " player: " + player);
        pvp.bfMessagePlayersOnBattlefield(controller, BATTLEFIELD_ACTIVE_REBEL_PLAYERS, "createBattlefieldWaypoint", dict);
        pvp.bfMessagePlayersOnBattlefield(controller, BATTLEFIELD_ACTIVE_IMPERIAL_PLAYERS, "createBattlefieldWaypoint", dict);
    }
    public int diedInPvpRegion(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        Vector recordList = new Vector();
        recordList.setSize(0);
        if (utils.hasResizeableStringBatchScriptVar(self, PVP_AREA_RECORD))
        {
            recordList = utils.getResizeableStringBatchScriptVar(self, PVP_AREA_RECORD);
        }
        utils.removeBatchScriptVar(self, PVP_AREA_RECORD);
        if (getPlayerDataFromList(player, recordList) == null)
        {
            recordList = addPlayerToList(player, recordList, 0, 1);
        }
        else 
        {
            recordList = incrementDeathsByPlayer(player, recordList);
        }
        utils.setBatchScriptVar(self, PVP_AREA_RECORD, recordList);
        return SCRIPT_CONTINUE;
    }
    public int getFactionCount(Vector recordList, String passedFaction) throws InterruptedException
    {
        if (recordList == null || recordList.size() == 0)
        {
            return 0;
        }
        int factionCount = 0;
        for (int i = 0; i < recordList.size(); i++)
        {
            String[] parse = split(((String)recordList.get(i)), '^');
            String faction = parse[1];
            if (faction.equals(passedFaction))
            {
                factionCount++;
            }
        }
        return factionCount;
    }
    public int getTotalDeathCount(Vector recordList) throws InterruptedException
    {
        if (recordList == null || recordList.size() == 0)
        {
            return 0;
        }
        int deathCount = 0;
        for (int i = 0; i < recordList.size(); i++)
        {
            String[] parse = split(((String)recordList.get(i)), '^');
            int playerDeath = utils.stringToInt(parse[3]);
            deathCount += playerDeath;
        }
        return deathCount;
    }
    public Vector addPlayerToList(obj_id player, Vector recordList, int uniqueHits, int deaths) throws InterruptedException
    {
        if (!isIdValid(player) || !exists(player) || !isPlayer(player))
        {
            doLogging("addPlayerToList", "Attempting to add player to list but he is invalid");
            return recordList;
        }
        String faction = factions.getFaction(player);
        if (faction == null || (!faction.equals("Rebel") && !faction.equals("Imperial")))
        {
            doLogging("addPlayerToList", "Player was null faction or was neither rebel/imperial");
            return recordList;
        }
        doLogging("xx", "Player is " + faction);
        String newEntry = "" + player + "^" + faction + "^" + uniqueHits + "^" + deaths;
        utils.addElement(recordList, newEntry);
        doLogging("xx", "Adding element to array: " + newEntry);
        return recordList;
    }
    public String getPlayerDataFromList(obj_id player, Vector recordList) throws InterruptedException
    {
        String listEntry = null;
        if (recordList == null || recordList.size() < 1)
        {
            return null;
        }
        for (int i = 0; i < recordList.size(); i++)
        {
            String[] parse = split(((String)recordList.get(i)), '^');
            obj_id listId = utils.stringToObjId(parse[0]);
            if (listId == player)
            {
                return (((String)recordList.get(i)) + "^" + i);
            }
        }
        return listEntry;
    }
    public Vector getGroupDataFromList(obj_id group, Vector recordList) throws InterruptedException
    {
        Vector listEntries = new Vector();
        listEntries.setSize(0);
        if (recordList == null || recordList.size() < 1)
        {
            return null;
        }
        for (int i = 0; i < recordList.size(); i++)
        {
            String[] parse = split(((String)recordList.get(i)), '^');
            obj_id listId = utils.stringToObjId(parse[1]);
            if (listId == group)
            {
                utils.addElement(listEntries, (((String)recordList.get(i)) + "^" + i));
            }
        }
        return listEntries;
    }
    public int getPositionInArrayByPlayer(obj_id player, Vector recordList) throws InterruptedException
    {
        if (recordList.size() == 0)
        {
            return -1;
        }
        int position = -1;
        for (int i = 0; i < recordList.size(); i++)
        {
            String[] parse = split(((String)recordList.get(i)), '^');
            obj_id listId = utils.stringToObjId(parse[0]);
            if (listId == player)
            {
                return i;
            }
        }
        return position;
    }
    public Vector incrementUpdateHitsByPlayer(obj_id player, Vector recordList) throws InterruptedException
    {
        int playerData = getPositionInArrayByPlayer(player, recordList);
        String[] parse = split(((String)recordList.get(playerData)), '^');
        int uniqueHits = utils.stringToInt(parse[2]);
        uniqueHits += 1;
        String updatedData = "" + parse[0] + "^" + parse[1] + "^" + uniqueHits + "^" + parse[3];
        doLogging("incrementUpdateHitsByPlayer", "Updating Data: " + ((String)recordList.get(playerData)) + " to " + updatedData);
        recordList.set(playerData, updatedData);
        return recordList;
    }
    public Vector incrementDeathsByPlayer(obj_id player, Vector recordList) throws InterruptedException
    {
        int playerData = getPositionInArrayByPlayer(player, recordList);
        String[] parse = split(((String)recordList.get(playerData)), '^');
        int deaths = utils.stringToInt(parse[3]);
        deaths += 1;
        if (deaths > gcw.MAX_DEATH_BY_PLAYER)
        {
            deaths = gcw.MAX_DEATH_BY_PLAYER;
        }
        String updatedData = "" + parse[0] + "^" + parse[1] + "^" + parse[2] + "^" + deaths;
        doLogging("incrementDeathsByPlayer", "updating Data: " + ((String)recordList.get(playerData)) + " to " + updatedData);
        recordList.set(playerData, updatedData);
        return recordList;
    }
}
