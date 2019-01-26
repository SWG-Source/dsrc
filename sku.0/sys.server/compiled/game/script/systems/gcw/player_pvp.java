package script.systems.gcw;

import script.*;
import script.library.*;

import java.util.Objects;
import java.util.Vector;

public class player_pvp extends script.base_script
{
    public player_pvp()
    {
    }
    public static final String VAR_PVP_LAST_UPDATE = "pvp_tracker.lastupdate";
    public static final String VAR_PVP_LAST_KILLS = "pvp_tracker.lastkills";
    public static final string_id SID_PVP_BATTLEFIELD_KICK_OUT_INVALID = new string_id("spam", "pvp_battlefield_kick_out_invalid");
    public static final string_id SID_PVP_BATTLEFIELD_KICK_OUT_GODMODE = new string_id("spam", "pvp_battlefield_kick_out_godmode");
    public static final int BATTLEFIELD_QUEUE = 1;
    public static final int BATTLEFIELD_DEQUEUE = 2;
    public static final int BATTLEFIELD_STATUS = 3;
    public static final int BATTLEFIELD_QUEUE_GROUP = 4;
    public static final int BATTLEFIELD_DEQUEUE_GROUP = 5;
    public static final int BATTLEFIELD_NUM = 4;
    public static final string_id SID_BATTLEFIELD_QUEUE = new string_id("spam", "battlefield_queue_individual");
    public static final string_id SID_BATTLEFIELD_QUEUE_GROUP = new string_id("spam", "battlefield_queue_group");
    public static final string_id SID_BATTLEFIELD_DEQUEUE = new string_id("spam", "battlefield_dequeue_individual");
    public static final string_id SID_BATTLEFIELD_DEQUEUE_GROUP = new string_id("spam", "battlefield_dequeue_group");
    public static final string_id SID_BATTLEFIELD_STATUS = new string_id("spam", "battlefield_status");
    public static final string_id SID_BATTLEFIELD_QUEUE_PROMPT = new string_id("spam", "battlefield_queue_prompt");
    public static final string_id SID_BATTLEFIELD_STATUS_PROMPT = new string_id("spam", "battlefield_status_prompt");
    public static final string_id SID_BATTLEFIELD_FACTION_UNDECLARED = new string_id("spam", "battlefield_faction_undeclared");
    public static final string_id SID_BATTLEFIELD_LEVEL_TOO_LOW = new string_id("spam", "battlefield_level_too_low");
    public static final string_id SID_BATTLEFIELD_OPPOSITE_FACTION_GROUP = new string_id("spam", "battlefield_opposite_faction_group");
    public static final string_id SID_BATTLEFIELD_JOIN_NOT_IN_A_GROUP = new string_id("spam", "battlefield_join_not_in_a_group");
    public static final string_id SID_BATTLEFIELD_ADDING_GROUP_TO_QUEUE = new string_id("spam", "battlefield_adding_group_to_queue");
    public static final string_id SID_BATTLEFIELD_NONE_FOUND = new string_id("spam", "battlefield_none_found");
    public static final string_id SID_BATTLEFIELD_CANNOT_ENTER_BAD_FACTION = new string_id("spam", "battlefield_cannot_enter_bad_faction");
    public static final string_id SID_BATTLEFIELD_INVITATION_EXPIRED = new string_id("spam", "battlefield_invitation_expired");
    public static final string_id SID_BATTLEFIELD_REMOVE_FROM_QUEUE = new string_id("spam", "battlefield_remove_from_queue");
    public static final string_id SID_BATTLEFIELD_REMOVE_GROUP_FROM_QUEUE = new string_id("spam", "battlefield_remove_group_from_queue");
    public static final string_id SID_BATTLEFIELD_REWARD_RECEIVED_TOKENS = new string_id("spam", "battlefield_reward_received_tokens");
    public static final string_id SID_BATTLEFIELD_QUEUE_ACCEPTANCE = new string_id("spam", "battlefield_queue_acceptance");
    public static final string_id SID_BATTLEFIELD_OVER_REMOVAL = new string_id("spam", "battlefield_over_removal");
    public static final string_id SID_BATTLEFIELD_QUEUE_REFUSAL = new string_id("spam", "battlefield_queue_refusal");
    public static final string_id SID_BATTLEFIELD_QUEUE_ACCEPTED = new string_id("spam", "battlefield_queue_accepted");
    public static final string_id SID_BATTLEFIELD_QUEUE_REMOVAL = new string_id("spam", "battlefield_queue_removal");
    public static final string_id SID_BATTLEFIELD_REFUSED = new string_id("spam", "battlefield_refused");
    public static final string_id SID_BATTLEFIELD_REQUEST_IN_PROGRESS = new string_id("spam", "battlefield_request_in_progress");
    public static final string_id SID_BATTLEFIELD_QUEUE_NOT_LEADER = new string_id("spam", "battlefield_queue_not_leader");
    public static final string_id SID_BATTLEFIELD_COMBAT_PROFESSION_ONLY = new string_id("spam", "battlefield_combat_profession_only");
    public static final string_id SID_BATTLEFIELD_QUEUE_ATTEMPTED_RECENTLY = new string_id("spam", "battlefield_queue_attempted_recently");
    public static final string_id SID_BATTLEFIELD_QUEUE_ATTEMPT = new string_id("spam", "battlefield_queue_attempt");
    public static final string_id SID_BATTLEFIELD_QUEUE_GROUP_REMOVAL = new string_id("spam", "battlefield_queue_group_removal");
    public static final string_id SID_BATTLEFIELD_QUEUE_REJECTED = new string_id("spam", "battlefield_queue_rejected");
    public static final String[] BATTLEFIELD_NAMES = 
    {
        "massassi_isle",
        "battlefield2",
        "battlefield3",
        "battlefield4",
        "battlefield5"
    };
    public static final String COLOR_REBELS = "\\" + colors_hex.COLOR_REBELS;
    public static final String COLOR_IMPERIALS = "\\" + colors_hex.COLOR_IMPERIALS;
    public void blog(obj_id controller, String text) throws InterruptedException
    {
        String battlefieldName = "none";
        if (isIdValid(controller) && exists(controller) && !isPlayer(controller))
        {
            battlefieldName = getStringObjVar(controller, "battlefieldName");
        }
        if (isPlayer(controller))
        {
            if (utils.hasScriptVar(controller, "battlefield.queue"))
            {
                battlefieldName = utils.getStringScriptVar(controller, "battlefield.queue");
            }
            if (utils.hasScriptVar(controller, "battlefield.active"))
            {
                controller = utils.getObjIdScriptVar(controller, "battlefield.active");
                if (isIdValid(controller) && exists(controller) && !isPlayer(controller))
                {
                    battlefieldName = getStringObjVar(controller, "battlefieldName");
                }
            }
        }
        CustomerServiceLog("battlefield_" + battlefieldName, text);
    }
    public obj_id getBattlefieldController(obj_id self) throws InterruptedException
    {
        obj_id controller = utils.getObjIdScriptVar(self, "battlefield.active");
        if (!isIdValid(controller) && isGod(self))
        {
            controller = gcw.getPvpRegionControllerIdByPlayer(self);
        }
        if (!isIdValid(controller))
        {
            controller = utils.getObjIdScriptVar(self, "battlefield.invitation.controller");
        }
        if (!isIdValid(controller))
        {
            controller = utils.getObjIdScriptVar(self, "battlefield.invited");
        }
        if (!isIdValid(controller))
        {
            String battlefieldName = utils.getStringScriptVar(self, "battlefield.queue");
            controller = utils.getObjIdScriptVar(self, "battlefield." + battlefieldName);
        }
        if (!isIdValid(controller))
        {
            return null;
        }
        return controller;
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        if (!isGod(self))
        {
            return SCRIPT_CONTINUE;
        }
        String[] words = split(text, ' ');
        obj_id controller = null;
        int bfRequest = 0;
        if (words[0].equals("bfreset"))
        {
            utils.removeScriptVarTree(self, "battlefield");
        }
        if (words[0].equals("bfwarp"))
        {
            controller = getBattlefieldController(self);
            if (!isIdValid(controller) || !exists(controller))
            {
                return SCRIPT_CONTINUE;
            }
            int bfState = utils.getIntScriptVar(controller, "battlefield.state");
            sendSystemMessageTestingOnly(self, "bfState: " + bfState);
            if (bfState == pvp.PVP_STATE_INVITE_QUEUE)
            {
                sendSystemMessageTestingOnly(self, "Attempting to end invitations...");
                utils.setScriptVar(controller, "battlefield.state", pvp.PVP_STATE_INVITE_OVER);
            }
        }
        if (words[0].equals("bfengage"))
        {
            controller = getBattlefieldController(self);
            if (!isIdValid(controller) || !exists(controller))
            {
                return SCRIPT_CONTINUE;
            }
            int bfState = utils.getIntScriptVar(controller, "battlefield.state");
            if (bfState == pvp.PVP_STATE_BATTLE_SETUP)
            {
                sendSystemMessageTestingOnly(self, "Attempting to end setup...");
                utils.setScriptVar(controller, "battlefield.state", pvp.PVP_STATE_BATTLE_START);
            }
        }
        if (words[0].equals("bfend"))
        {
            controller = getBattlefieldController(self);
            if (!isIdValid(controller) || !exists(controller))
            {
                return SCRIPT_CONTINUE;
            }
            utils.removeScriptVar(controller, "battlefield.battleTime");
            sendSystemMessageTestingOnly(self, "Attempting to end battlefield...");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnLogin(obj_id self) throws InterruptedException
    {
        cleanupOldSystemObjVar(self);
        gcw.clearCreditForKills(self);
        return SCRIPT_CONTINUE;
    }
    public int OnEnteredCombat(obj_id self) throws InterruptedException
    {
        float ratio = getHealth(self) / getMaxHealth(self);
        if (ratio > 0.9)
        {
            gcw.clearCreditForKills(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnAddedToGroup(obj_id self, obj_id groupId) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, "battlefield.active"))
        {
            return SCRIPT_CONTINUE;
        }
        if (group.isMixedFactionGroup(groupId))
        {
            messageTo(self, "receiveLeaveGroup", null, 1.0f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int receiveLeaveGroup(obj_id self, dictionary params) throws InterruptedException
    {
        group.leaveGroup(self);
        sendSystemMessage(self, SID_BATTLEFIELD_OPPOSITE_FACTION_GROUP);
        return SCRIPT_CONTINUE;
    }
    public int cmdBattlefield(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        battlefieldCommandSui(self);
        return SCRIPT_CONTINUE;
    }
    public int displayBattlefieldSui(obj_id self, dictionary params) throws InterruptedException
    {
        battlefieldCommandSui(self);
        return SCRIPT_CONTINUE;
    }
    public void battlefieldCommandSui(obj_id self) throws InterruptedException
    {
        if (!isIdValid(self))
        {
            return;
        }
        if (!factions.isRebel(self) && !factions.isImperial(self))
        {
            sendSystemMessage(self, SID_BATTLEFIELD_FACTION_UNDECLARED);
            return;
        }
        if (getLevel(self) < 75)
        {
            sendSystemMessage(self, SID_BATTLEFIELD_LEVEL_TOO_LOW);
            return;
        }
        if (utils.hasScriptVar(self, "battlefield.requestInProgress"))
        {
            int lastTime = utils.getIntScriptVar(self, "battlefield.requestInProgress");
            if (getGameTime() - lastTime < 60)
            {
                sendSystemMessage(self, SID_BATTLEFIELD_REQUEST_IN_PROGRESS);
                return;
            }
        }
        utils.removeScriptVarTree(self, "battlefield.status");
        if (!utils.hasScriptVar(self, "battlefield." + BATTLEFIELD_NAMES[0]) || !utils.hasScriptVar(self, "battlefield." + BATTLEFIELD_NAMES[1]) || !utils.hasScriptVar(self, "battlefield." + BATTLEFIELD_NAMES[2]) || !utils.hasScriptVar(self, "battlefield." + BATTLEFIELD_NAMES[3]))
        {
            getClusterWideData("pvp", "*", true, self);
            blog(null, "battlefieldCommandSui player: " + getName(self) + "(" + self + ") getClusterWideData requested.");
        }
        else 
        {
            requestBattlefieldStatus(self);
        }
    }
    public void requestBattlefieldStatus(obj_id self) throws InterruptedException
    {
        if (!isIdValid(self) || !exists(self))
        {
            return;
        }
        if (utils.hasScriptVar(self, "battlefield.active"))
        {
            obj_id controller = utils.getObjIdScriptVar(self, "battlefield.active");
            if (isIdValid(controller) && exists(controller))
            {
                showBattlefieldScoreboard(self);
                return;
            }
        }
        if (!utils.hasScriptVar(self, "battlefield." + BATTLEFIELD_NAMES[0]) || !utils.hasScriptVar(self, "battlefield." + BATTLEFIELD_NAMES[1]) || !utils.hasScriptVar(self, "battlefield." + BATTLEFIELD_NAMES[2]) || !utils.hasScriptVar(self, "battlefield." + BATTLEFIELD_NAMES[3]))
        {
            getClusterWideData("pvp", "*", true, self);
            blog(null, "requestBattlefieldStatus player: " + getName(self) + "(" + self + ") getClusterWideData requested.");
            return;
        }
        blog(null, "requestBattlefieldStatus player: " + getName(self) + "(" + self + ") all battlefields accounted for.");
        obj_id bf1 = utils.getObjIdScriptVar(self, "battlefield." + BATTLEFIELD_NAMES[0]);
        obj_id bf2 = utils.getObjIdScriptVar(self, "battlefield." + BATTLEFIELD_NAMES[1]);
        obj_id bf3 = utils.getObjIdScriptVar(self, "battlefield." + BATTLEFIELD_NAMES[2]);
        obj_id bf4 = utils.getObjIdScriptVar(self, "battlefield." + BATTLEFIELD_NAMES[3]);
        dictionary params = new dictionary();
        params.put("player", self);
        params.put("faction", factions.getFactionFlag(self));
        utils.setScriptVar(self, "battlefield.requestInProgress", true);
        messageTo(bf1, "getBattlefieldStatus", params, 1.0f, false);
        messageTo(bf2, "getBattlefieldStatus", params, 1.0f, false);
        messageTo(bf3, "getBattlefieldStatus", params, 1.0f, false);
        messageTo(bf4, "getBattlefieldStatus", params, 1.0f, false);
        sendSystemMessage(self, SID_BATTLEFIELD_REQUEST_IN_PROGRESS);
    }
    public int receiveBattlefieldStatus(obj_id self, dictionary params) throws InterruptedException
    {
        String status = params.getString("battlefieldStatus");
        int battlefieldState = params.getInt("battlefieldState");
        int battlefieldTime = params.getInt("battlefieldTime");
        int battlefieldInvitationTime = params.getInt("battlefieldInvitationTime");
        int battlefieldSetupTime = params.getInt("battlefieldSetupTime");
        String battlefieldName = params.getString("battlefieldName");
        String battlefieldNameUnlocalized = params.getString("battlefieldNameUnlocalized");
        int rebelPlayers = params.getInt("rebelPlayers");
        int rebelGroups = params.getInt("rebelGroups");
        int imperialPlayers = params.getInt("imperialPlayers");
        int imperialGroups = params.getInt("imperialGroups");
        int rebelPlayersActive = params.getInt("rebelPlayersActive");
        int imperialPlayersActive = params.getInt("imperialPlayersActive");
        String playerInfo = params.getString("playerInfo");
        int index = 0;
        for (int i = 1, j = BATTLEFIELD_NAMES.length; i < j; i++)
        {
            if (battlefieldNameUnlocalized.equals(BATTLEFIELD_NAMES[i]))
            {
                index = i;
                break;
            }
        }
        String bfStats = status + "*" + battlefieldState + "*" + battlefieldTime + "*" + battlefieldInvitationTime + "*" + battlefieldSetupTime + "*" + battlefieldName + "*" + battlefieldNameUnlocalized + "*" + rebelPlayers + "*" + rebelGroups + "*" + imperialPlayers + "*" + imperialGroups + "*" + rebelPlayersActive + "*" + imperialPlayersActive + "*" + playerInfo;
        utils.setScriptVar(self, "battlefield.status." + BATTLEFIELD_NAMES[index], bfStats);
        if (!utils.hasScriptVar(self, "battlefield.status." + BATTLEFIELD_NAMES[0]) || !utils.hasScriptVar(self, "battlefield.status." + BATTLEFIELD_NAMES[1]) || !utils.hasScriptVar(self, "battlefield.status." + BATTLEFIELD_NAMES[2]) || !utils.hasScriptVar(self, "battlefield.status." + BATTLEFIELD_NAMES[3]))
        {
            blog(null, "receiveBattlefieldStatus player: " + getName(self) + "(" + self + ") battlefield stats accumilating.  " + BATTLEFIELD_NAMES[0] + ": " + utils.hasScriptVar(self, "battlefield.status." + BATTLEFIELD_NAMES[0]) + " " + BATTLEFIELD_NAMES[1] + ": " + utils.hasScriptVar(self, "battlefield.status." + BATTLEFIELD_NAMES[1]) + " " + BATTLEFIELD_NAMES[2] + ": " + utils.hasScriptVar(self, "battlefield.status." + BATTLEFIELD_NAMES[2]) + " " + BATTLEFIELD_NAMES[3] + ": " + utils.hasScriptVar(self, "battlefield.status." + BATTLEFIELD_NAMES[3]) + " ");
            return SCRIPT_CONTINUE;
        }
        blog(null, "receiveBattlefieldStatus player: " + getName(self) + "(" + self + ") all battlefield stats accounted for.  " + BATTLEFIELD_NAMES[0] + ": " + utils.hasScriptVar(self, "battlefield.status." + BATTLEFIELD_NAMES[0]) + " " + BATTLEFIELD_NAMES[1] + ": " + utils.hasScriptVar(self, "battlefield.status." + BATTLEFIELD_NAMES[1]) + " " + BATTLEFIELD_NAMES[2] + ": " + utils.hasScriptVar(self, "battlefield.status." + BATTLEFIELD_NAMES[2]) + " " + BATTLEFIELD_NAMES[3] + ": " + utils.hasScriptVar(self, "battlefield.status." + BATTLEFIELD_NAMES[3]) + " ");
        utils.removeScriptVar(self, "battlefield.requestInProgress");
        String bfPlayerInfo = "";
        String[][] statusData = new String[4][7];
        int bfQueuedFor = -1;
        String bfNameQueuedFor = "";
        for (int i = 0; i < 4; i++)
        {
            bfStats = utils.getStringScriptVar(self, "battlefield.status." + BATTLEFIELD_NAMES[i]);
            if (bfStats == null || bfStats.length() < 1)
            {
                return SCRIPT_CONTINUE;
            }
            String[] bfStatArray = split(bfStats, '*');
            if (bfStatArray == null || bfStatArray.length != 14)
            {
                return SCRIPT_CONTINUE;
            }
            statusData[i][0] = bfStatArray[5];
            statusData[i][1] = bfStatArray[0];
            battlefieldState = utils.stringToInt(bfStatArray[1]);
            battlefieldTime = utils.stringToInt(bfStatArray[2]);
            battlefieldInvitationTime = utils.stringToInt(bfStatArray[3]);
            battlefieldSetupTime = utils.stringToInt(bfStatArray[4]);
            battlefieldNameUnlocalized = bfStatArray[6];
            rebelPlayers = utils.stringToInt(bfStatArray[7]);
            rebelGroups = utils.stringToInt(bfStatArray[8]);
            imperialPlayers = utils.stringToInt(bfStatArray[9]);
            imperialGroups = utils.stringToInt(bfStatArray[10]);
            statusData[i][3] = "" + (rebelPlayers + rebelGroups);
            statusData[i][4] = "" + (imperialPlayers + imperialGroups);
            statusData[i][5] = bfStatArray[11];
            statusData[i][6] = bfStatArray[12];
            playerInfo = bfStatArray[13];
            if (playerInfo != null && !playerInfo.equals("null"))
            {
                bfPlayerInfo = playerInfo;
                bfQueuedFor = i;
                bfNameQueuedFor = statusData[i][0];
            }
            if (battlefieldState == pvp.PVP_STATE_BATTLE_ENGAGED)
            {
                int time = ((int)(pvp.BATTLEFIELD_DURATION + battlefieldTime) - getGameTime());
                if (time < 0)
                {
                    time = 0;
                }
                statusData[i][2] = "" + time + " seconds";
            }
            else if (battlefieldState == pvp.PVP_STATE_BATTLE_SETUP)
            {
                int time = ((int)(pvp.BATTLEFIELD_SETUP_WAIT_TIME + battlefieldSetupTime) - getGameTime());
                if (time < 0)
                {
                    time = 0;
                }
                statusData[i][2] = "" + time + " seconds";
            }
            else if (battlefieldState == pvp.PVP_STATE_INVITE_QUEUE)
            {
                int time = ((int)(pvp.BATTLEFIELD_INVITATION_WAIT_TIME + battlefieldInvitationTime) - getGameTime());
                if (time < 0)
                {
                    time = 0;
                }
                statusData[i][2] = "" + time + " seconds";
            }
            else 
            {
                statusData[i][2] = "";
            }
        }
        int faction = factions.getFactionFlag(self);
        String[] playerData = new String[0];
        if (bfPlayerInfo != null && bfPlayerInfo.length() > 0)
        {
            playerData = split(bfPlayerInfo, '^');
        }
        int queuePlace = -1;
        int waitTime = 0;
        if (playerData != null && playerData.length > 1)
        {
            if (!playerData[playerData.length - 1].equals("topQueue"))
            {
                if (faction == factions.FACTION_FLAG_REBEL)
                {
                    queuePlace = rebelGroups;
                }
                else if (faction == factions.FACTION_FLAG_IMPERIAL)
                {
                    queuePlace = imperialGroups;
                }
                queuePlace += utils.stringToInt(playerData[playerData.length - 2]);
            }
            else 
            {
                queuePlace = utils.stringToInt(playerData[playerData.length - 2]);
            }
            if (playerData.length > 4)
            {
                int bfTime = utils.stringToInt(playerData[2]);
                if (bfTime <= 0)
                {
                    waitTime = 0;
                }
                else 
                {
                    waitTime = getCalendarTime() - bfTime;
                }
            }
            else 
            {
                int bfTime = utils.stringToInt(playerData[1]);
                if (bfTime <= 0)
                {
                    waitTime = 0;
                }
                else 
                {
                    waitTime = getCalendarTime() - bfTime;
                }
            }
        }
        String queuePosition = "";
        String prompt = "";
        if (queuePlace > -1)
        {
            if (bfQueuedFor > -1)
            {
                prompt = "Queued for: " + bfNameQueuedFor + "\n";
            }
            prompt += "Your Queue Position: " + (queuePlace + 1) + "\n";
        }
        if (waitTime > 0)
        {
            prompt += "Time Waited: " + utils.padTimeDHMS(waitTime);
        }
        if (utils.hasScriptVar(self, "battlefield.pid"))
        {
            int pid = utils.getIntScriptVar(self, "battlefield.pid");
            utils.removeScriptVar(self, "battlefield.pid");
            forceCloseSUIPage(pid);
        }
        String[] table_titles = 
        {
            "@guild:table_title_name",
            "@spam:table_title_status",
            "@spam:table_title_time",
            "@spam:table_title_rebels_queued",
            "@spam:table_title_imperials_queued",
            "@spam:table_title_rebels_accepted",
            "@spam:table_title_imperials_accepted"
        };
        String[] table_types = 
        {
            "text",
            "text",
            "text",
            "integer",
            "integer",
            "integer",
            "integer"
        };
        int pid = sui.tableRowMajor(self, self, sui.OK_CANCEL, "Battlefield Status", "onBattlefieldCommandResponse", prompt, table_titles, table_types, statusData, false);
        utils.setScriptVar(self, "battlefield.pid", pid);
        utils.setScriptVar(self, "battlefield.queuedFor", bfQueuedFor);
        return SCRIPT_CONTINUE;
    }
    public int onBattlefieldCommandResponse(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, "battlefield.pid"))
        {
            int pid = utils.getIntScriptVar(self, "battlefield.pid");
            utils.removeScriptVar(self, "battlefield.pid");
            forceCloseSUIPage(pid);
        }
        int btn = sui.getIntButtonPressed(params);
        int index = sui.getTableLogicalIndex(params);
        int bfQueuedFor = utils.getIntScriptVar(self, "battlefield.queuedFor");
        utils.removeScriptVar(self, "battlefield.queuedFor");
        if (btn == sui.BP_OK)
        {
            if (index < 0 || index > 3)
            {
                return SCRIPT_CONTINUE;
            }
            obj_id controller = utils.getObjIdScriptVar(self, "battlefield." + BATTLEFIELD_NAMES[index]);
            if (!isIdValid(controller))
            {
                return SCRIPT_CONTINUE;
            }
            params = new dictionary();
            params.put("player", self);
            if (factions.isRebel(self))
            {
                params.put("faction", factions.FACTION_FLAG_REBEL);
            }
            else if (factions.isImperial(self))
            {
                params.put("faction", factions.FACTION_FLAG_IMPERIAL);
            }
            obj_id group = getGroupObject(self);
            obj_id[] members = getGroupMemberIds(group);
            boolean grouped = false;
            boolean groupLeader = false;
            if (isIdValid(group) && members != null && members.length > 1)
            {
                grouped = true;
                groupLeader = (getGroupLeaderId(group) == self);
            }
            if (utils.hasScriptVar(self, "battlefield.invited"))
            {
                return SCRIPT_CONTINUE;
            }
            if (utils.hasScriptVar(self, "battlefield.active"))
            {
                return SCRIPT_CONTINUE;
            }
            if (utils.hasScriptVar(self, "battlefield.queue"))
            {
                String battlefieldName = utils.getStringScriptVar(self, "battlefield.queue");
                if (battlefieldName != null || battlefieldName.length() > 0)
                {
                    controller = utils.getObjIdScriptVar(self, "battlefield." + battlefieldName);
                }
                if (bfQueuedFor == index)
                {
                    if (!groupLeader)
                    {
                        sendSystemMessage(self, SID_BATTLEFIELD_REMOVE_FROM_QUEUE);
                    }
                    else 
                    {
                        sendSystemMessage(self, SID_BATTLEFIELD_QUEUE_GROUP_REMOVAL);
                        params.put("members", members);
                    }
                    messageTo(controller, "removePlayerFromQueue", params, 1.0f, false);
                    return SCRIPT_CONTINUE;
                }
                return SCRIPT_CONTINUE;
            }
            int queuing = utils.getIntScriptVar(self, "battlefield.queueing");
            if (queuing + 60 < getGameTime())
            {
                if (!grouped)
                {
                    sendSystemMessage(self, SID_BATTLEFIELD_QUEUE_ATTEMPT);
                    messageTo(controller, "addPlayerToQueue", params, 1.0f, false);
                    utils.setScriptVar(self, "battlefield.queuing", getGameTime());
                }
                else 
                {
                    if (!groupLeader)
                    {
                        sendSystemMessage(self, SID_BATTLEFIELD_QUEUE_NOT_LEADER);
                        return SCRIPT_CONTINUE;
                    }
                    else 
                    {
                        params.put("group", group);
                        params.put("members", members);
                        sendSystemMessage(self, SID_BATTLEFIELD_ADDING_GROUP_TO_QUEUE);
                        messageTo(controller, "addGroupToQueue", params, 1.0f, false);
                        utils.setScriptVar(self, "battlefield.queuing", getGameTime());
                    }
                }
            }
            else 
            {
                sendSystemMessage(self, SID_BATTLEFIELD_QUEUE_ATTEMPTED_RECENTLY);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public String formatBattlefieldCountdown(int seconds) throws InterruptedException
    {
        if (seconds < 0)
        {
            return "0 seconds";
        }
        int minutes = seconds / 60;
        if (minutes < 0)
        {
            minutes = 0;
        }
        String minutesInfo = "" + minutes;
        seconds = seconds % 60;
        if (seconds < 0)
        {
            seconds = 0;
        }
        String secondsInfo = "" + seconds;
        if (minutes == 1)
        {
            minutesInfo += " minute ";
        }
        else if (minutes == 0)
        {
            minutesInfo = "";
        }
        else 
        {
            minutesInfo += " minutes ";
        }
        if (seconds == 1)
        {
            secondsInfo += " second";
        }
        else 
        {
            secondsInfo += " seconds";
        }
        return minutesInfo + secondsInfo;
    }
    public void showBattlefieldScoreboard(obj_id self) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "battlefield.pid"))
        {
            int pid = utils.getIntScriptVar(self, "battlefield.pid");
            utils.removeScriptVar(self, "battlefield.pid");
            forceCloseSUIPage(pid);
        }
        if (!utils.hasScriptVar(self, "battlefield.active"))
        {
            return;
        }
        obj_id controller = utils.getObjIdScriptVar(self, "battlefield.active");
        if (!isIdValid(controller) || !exists(controller))
        {
            return;
        }
        Vector battlefieldPlayers = pvp.bfActiveGetStatistics(controller);
        if (battlefieldPlayers == null || battlefieldPlayers.size() < 1)
        {
            return;
        }
        String[][] scoreData = pvp.bfStatisticsToArray(battlefieldPlayers, true);
        int rebelCount = ((dictionary)battlefieldPlayers.get(0)).getInt(pvp.BATTLEFIELD_ACTIVE_REBEL_PLAYERS);
        int imperialCount = ((dictionary)battlefieldPlayers.get(0)).getInt(pvp.BATTLEFIELD_ACTIVE_IMPERIAL_PLAYERS);
        String[] table_titles = 
        {
            "@guild:table_title_name",
            "@spam:table_title_faction",
            "@spam:table_title_kills",
            "@spam:table_title_assists",
            "@spam:table_title_deaths",
            "@spam:table_title_damage",
            "@spam:table_title_healing",
            "@spam:table_title_captures"
        };
        String[] table_types = 
        {
            "text",
            "text",
            "integer",
            "integer",
            "integer",
            "integer",
            "integer",
            "integer"
        };
        String statisticsTitle = "Battlefield Statistics";
        int battlefieldInvitationTime = utils.getIntScriptVar(controller, "battlefield.queueTime");
        int battlefieldTime = utils.getIntScriptVar(controller, "battlefield.battleTime");
        int bfState = utils.getIntScriptVar(controller, "battlefield.state");
        int imperialScore = utils.getIntScriptVar(controller, "battlefield.imperialScore");
        int rebelScore = utils.getIntScriptVar(controller, "battlefield.rebelScore");
        String battlefieldStatus = pvp.BATTLEFIELD_STATUS[bfState];
        if (battlefieldTime < 0)
        {
            battlefieldTime = 0;
        }
        String statisticsInfoStr = COLOR_REBELS + "Rebels: " + rebelCount + "\\#DFDFDF vs. " + COLOR_IMPERIALS + "Imperials: " + imperialCount + "\n" + COLOR_REBELS + "Rebel Score: " + rebelScore + " " + COLOR_IMPERIALS + "Imperial Score: " + imperialScore + "\n\\#DFDFDFBattlefield Status: " + battlefieldStatus;
        if (bfState == pvp.PVP_STATE_BATTLE_ENGAGED)
        {
            statisticsInfoStr += "\nBattle Time Left: " + formatBattlefieldCountdown((pvp.BATTLEFIELD_DURATION + battlefieldTime) - getGameTime());
        }
        else if (bfState == pvp.PVP_STATE_INVITE_QUEUE)
        {
            statisticsInfoStr += "\nInvitation Time Left: " + formatBattlefieldCountdown((int)((pvp.BATTLEFIELD_INVITATION_WAIT_TIME + battlefieldInvitationTime) - getGameTime()));
        }
        int pid = sui.tableRowMajor(self, self, sui.OK_CANCEL, statisticsTitle, "onStatisticsMembersResponse", statisticsInfoStr, table_titles, table_types, scoreData, true);
        utils.setScriptVar(self, "battlefield.pid", pid);
    }
    public boolean canReceiveReward(obj_id self) throws InterruptedException
    {
        if (!isPlayerActive(self))
        {
            return false;
        }
        int damage = utils.getIntScriptVar(self, "battlefield.damage");
        int healing = utils.getIntScriptVar(self, "battlefield.healing");
        int captures = utils.getIntScriptVar(self, "battlefield.captures");
        if (damage == 0 && healing == 0 && captures == 0)
        {
            return false;
        }
        return true;
    }
    public void showBattlefieldFinalScoreboard(obj_id self, String battlefieldName) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "battlefield.pid"))
        {
            int pid = utils.getIntScriptVar(self, "battlefield.pid");
            utils.removeScriptVar(self, "battlefield.pid");
            forceCloseSUIPage(pid);
        }
        if (!utils.hasScriptVar(self, "battlefield.active"))
        {
            return;
        }
        obj_id controller = utils.getObjIdScriptVar(self, "battlefield.active");
        if (!isIdValid(controller) || !exists(controller))
        {
            return;
        }
        Vector battlefieldPlayers = pvp.bfActiveGetStatistics(controller);
        if (battlefieldPlayers == null || battlefieldPlayers.size() < 1)
        {
            return;
        }
        String[][] scoreData = pvp.bfStatisticsToArray(battlefieldPlayers, true);
        int rebelCount = ((dictionary)battlefieldPlayers.get(0)).getInt(pvp.BATTLEFIELD_ACTIVE_REBEL_PLAYERS);
        int imperialCount = ((dictionary)battlefieldPlayers.get(0)).getInt(pvp.BATTLEFIELD_ACTIVE_IMPERIAL_PLAYERS);
        String[] table_titles = 
        {
            "@guild:table_title_name",
            "@spam:table_title_faction",
            "@spam:table_title_kills",
            "@spam:table_title_assists",
            "@spam:table_title_deaths",
            "@spam:table_title_damage",
            "@spam:table_title_healing",
            "@spam:table_title_captures"
        };
        String[] table_types = 
        {
            "text",
            "text",
            "integer",
            "integer",
            "integer",
            "integer",
            "integer",
            "integer"
        };
        String statisticsTitle = "Battlefield Over";
        int imperialScore = utils.getIntScriptVar(controller, "battlefield.imperialScore");
        int rebelScore = utils.getIntScriptVar(controller, "battlefield.rebelScore");
        String battlefieldStatus = "Unknown";
        int gcwCredits = 0;
        int tokens = 2;
        int faction = factions.getFactionFlag(self);
        if (imperialScore == rebelScore)
        {
            battlefieldStatus = "Battle Undecided!";
            gcwCredits = gcw.BATTLEFIELD_TIE_POINT_VALUE;
            tokens = 3;
        }
        else if (rebelScore > imperialScore)
        {
            if (faction == factions.FACTION_FLAG_REBEL)
            {
                battlefieldStatus = COLOR_REBELS + "Rebel Victory!";
                gcwCredits = gcw.BATTLEFIELD_VICTORY_POINT_VALUE;
                tokens = 5;
            }
            else 
            {
                battlefieldStatus = COLOR_IMPERIALS + "Imperials Defeated!";
                gcwCredits = gcw.BATTLEFIELD_DEFEAT_POINT_VALUE;
                tokens = 2;
            }
        }
        else 
        {
            if (faction == factions.FACTION_FLAG_REBEL)
            {
                battlefieldStatus = COLOR_REBELS + "Rebels Defeated!";
                gcwCredits = gcw.BATTLEFIELD_DEFEAT_POINT_VALUE;
                tokens = 2;
            }
            else 
            {
                battlefieldStatus = COLOR_IMPERIALS + "Imperial Victory!";
                gcwCredits = gcw.BATTLEFIELD_VICTORY_POINT_VALUE;
                tokens = 5;
            }
        }
        float multiplier = utils.stringToFloat(getConfigSetting("GameServer", "gcwTokenBonus"));
        if (multiplier > 1)
        {
            tokens *= multiplier;
        }
        if (canReceiveReward(self))
        {
            gcw._grantGcwPoints(null, self, gcwCredits, false, gcw.GCW_POINT_TYPE_GROUND_PVP_REGION, "");
            blog(self, "Player: " + self + " Name: " + getName(self) + " GCW Reward: " + gcwCredits + " Token reward: " + tokens);
            obj_id container = utils.getInventoryContainer(self);
            if (isIdValid(container) && exists(container))
            {
                for (int i = 0; i < tokens; i++)
                {
                    if (faction == factions.FACTION_FLAG_REBEL)
                    {
                        static_item.createNewItemFunction("item_battlefield_rebel_token_" + battlefieldName, container);
                    }
                    else 
                    {
                        static_item.createNewItemFunction("item_battlefield_imperial_token_" + battlefieldName, container);
                    }
                }
            }
            if (tokens > 0)
            {
                prose_package pp = new prose_package();
                pp = prose.setStringId(pp, SID_BATTLEFIELD_REWARD_RECEIVED_TOKENS);
                pp = prose.setDI(pp, tokens);
                sendSystemMessageProse(self, pp);
            }
        }
        else 
        {
            blog(self, "Player: " + self + " Name: " + getName(self) + " did not participate and is not getting rewards.");
        }
        String statisticsInfoStr = battlefieldStatus + "\n" + COLOR_REBELS + "Rebel Score: " + rebelScore + " " + COLOR_IMPERIALS + "Imperial Score: " + imperialScore;
        int pid = sui.tableRowMajor(self, self, sui.OK_CANCEL, statisticsTitle, "onStatisticsMembersResponse", statisticsInfoStr, table_titles, table_types, scoreData, true);
        utils.setScriptVar(self, "battlefield.pid", pid);
    }
    public int receiveBattlefieldFinale(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        String battlefieldName = params.getString("battlefieldName");
        showBattlefieldFinalScoreboard(self, battlefieldName);
        return SCRIPT_CONTINUE;
    }
    public int OnClusterWideDataResponse(obj_id self, String manage_name, String name, int request_id, String[] element_name_list, dictionary[] data, int lock_key) throws InterruptedException
    {
        if (!manage_name.equals("pvp"))
        {
            return SCRIPT_CONTINUE;
        }
        LOG("gcw_", "OnClusterWideDataResponse begin manage_name: " + manage_name + " name: " + name);
        if (element_name_list != null && element_name_list.length > 0 && data != null && data.length > 0)
        {
            LOG("gcw_", "OnClusterWideDataResponse manage_name: " + manage_name + " name: " + name + " element_name_list.length: " + element_name_list.length + " data.length: " + data.length);
            for (int i = 0, j = element_name_list.length; i < j; i++)
            {
                for (int k = 0; k < BATTLEFIELD_NUM; k++)
                {
                    if (element_name_list[i].equals(BATTLEFIELD_NAMES[k]))
                    {
                        obj_id controller = data[i].getObjId("battlefield");
                        if (isIdValid(controller))
                        {
                            utils.setScriptVar(self, "battlefield." + BATTLEFIELD_NAMES[k], controller);
                        }
                    }
                }
            }
        }
        else 
        {
            sendSystemMessage(self, SID_BATTLEFIELD_NONE_FOUND);
            return SCRIPT_CONTINUE;
        }
        requestBattlefieldStatus(self);
        return SCRIPT_CONTINUE;
    }
    public int receiveBattlefieldTerminalCapture(obj_id self, dictionary params) throws InterruptedException
    {
        int gcwCredits = params.getInt("gcwCredits");
        if (gcwCredits <= 0)
        {
            return SCRIPT_CONTINUE;
        }
        if (canReceiveReward(self))
        {
            gcw._grantGcwPoints(null, self, gcwCredits, false, gcw.GCW_POINT_TYPE_GROUND_PVP, "battlefield terminal");
        }
        return SCRIPT_CONTINUE;
    }
    public int receiveBattlefieldQueueAcceptance(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        if (!utils.hasScriptVar(self, "battlefield." + BATTLEFIELD_NAMES[0]) || !utils.hasScriptVar(self, "battlefield." + BATTLEFIELD_NAMES[1]) || !utils.hasScriptVar(self, "battlefield." + BATTLEFIELD_NAMES[2]) || !utils.hasScriptVar(self, "battlefield." + BATTLEFIELD_NAMES[3]))
        {
            getClusterWideData("pvp", "*", true, self);
        }
        String battlefieldName = params.getString("battlefieldName");
        if (battlefieldName == null || battlefieldName.length() < 1)
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, "battlefield.queue") || utils.hasScriptVar(self, "battlefield.invited") || utils.hasScriptVar(self, "battlefield.active"))
        {
            obj_id controllerWithMessage = params.getObjId("controller");
            obj_id controllerQueuedFor = getBattlefieldController(self);
            if (isIdValid(controllerWithMessage))
            {
                params.put("player", self);
                if (factions.isRebel(self))
                {
                    params.put("faction", factions.FACTION_FLAG_REBEL);
                }
                else if (factions.isImperial(self))
                {
                    params.put("faction", factions.FACTION_FLAG_IMPERIAL);
                }
                prose_package pp = new prose_package();
                pp = prose.setStringId(pp, SID_BATTLEFIELD_QUEUE_REJECTED);
                pp = prose.setTO(pp, localize(new string_id("spam", battlefieldName)));
                sendSystemMessageProse(self, pp);
                if (controllerWithMessage != controllerQueuedFor)
                {
                    debugSpeakMsg(self, "refuseBattlefieldInvitation controller: " + controllerWithMessage);
                    messageTo(controllerWithMessage, "refuseBattlefieldInvitation", params, 0.0f, false);
                }
                return SCRIPT_CONTINUE;
            }
        }
        utils.setScriptVar(self, "battlefield.queue", battlefieldName);
        prose_package pp = new prose_package();
        pp = prose.setStringId(pp, SID_BATTLEFIELD_QUEUE_ACCEPTANCE);
        pp = prose.setTO(pp, localize(new string_id("spam", battlefieldName)));
        sendSystemMessageProse(self, pp);
        return SCRIPT_CONTINUE;
    }
    public int receiveBattlefieldInvitation(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id controller = params.getObjId("battlefieldController");
        location loc = params.getLocation("warpInLocation");
        String battlefieldName = params.getString("battlefieldName");
        int factionToMatch = params.getInt("factionToMatch");
        if (loc == null)
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, "battlefield.invitation.pid"))
        {
            int pid = utils.getIntScriptVar(self, "battlefield.invitation.pid");
            utils.removeScriptVar(self, "battlefield.invitation.pid");
            forceCloseSUIPage(pid);
        }
        params.put("player", self);
        if (utils.hasScriptVar(self, "battlefield.invited") || utils.hasScriptVar(self, "battlefield.active"))
        {
            obj_id controllerQueuedFor = getBattlefieldController(self);
            if (factions.isRebel(self))
            {
                params.put("faction", factions.FACTION_FLAG_REBEL);
            }
            else if (factions.isImperial(self))
            {
                params.put("faction", factions.FACTION_FLAG_IMPERIAL);
            }
            if (controller != controllerQueuedFor)
            {
                messageTo(controller, "refuseBattlefieldInvitation", params, 0.0f, false);
            }
            return SCRIPT_CONTINUE;
        }
        if (((factions.isRebel(self) && factionToMatch == factions.FACTION_FLAG_REBEL) || (factions.isImperial(self) && factionToMatch == factions.FACTION_FLAG_IMPERIAL)))
        {
            utils.setScriptVar(self, "battlefield.invitation.controller", controller);
            utils.setScriptVar(self, "battlefield.invitation.warpInLocation", loc);
            utils.setScriptVar(self, "battlefield.invitation.battlefieldName", battlefieldName);
            play2dNonLoopingSound(self, "sound/ui_incoming_im.snd");
            int pid = sui.msgbox(self, self, "Your battlefield is ready!  Do you wish to go to " + battlefieldName + "?", sui.YES_NO, "@spam:battlefield_invitation_accept_t", sui.MSG_QUESTION, "onBattlefieldInvitationAccept");
            utils.setScriptVar(self, "battlefield.invitation.pid", pid);
            messageTo(self, "onBattlefieldInvitationExpire", params, 30.0f, false);
            return SCRIPT_CONTINUE;
        }
        if (factions.isRebel(self))
        {
            params.put("faction", factions.FACTION_FLAG_IMPERIAL);
        }
        else if (factions.isImperial(self))
        {
            params.put("faction", factions.FACTION_FLAG_REBEL);
        }
        sendSystemMessage(self, SID_BATTLEFIELD_CANNOT_ENTER_BAD_FACTION);
        utils.removeScriptVarTree(self, "battlefield");
        messageTo(controller, "refuseBattlefieldInvitation", params, 0.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int receiveBattlefieldOver(obj_id self, dictionary params) throws InterruptedException
    {
        buff.removeBuff(self, "battlefield_communication_run");
        buff.removeBuff(self, "battlefield_radar_invisibility");
        utils.removeScriptVarTree(self, "battlefield");
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        String battlefieldName = params.getString("battlefieldName");
        prose_package pp = new prose_package();
        pp = prose.setStringId(pp, SID_BATTLEFIELD_OVER_REMOVAL);
        pp = prose.setTO(pp, localize(new string_id("spam", battlefieldName)));
        sendSystemMessageProse(self, pp);
        location loc = params.getLocation("warpLocation");
        if (loc == null)
        {
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, "battlefield.kicked_out", true);
        pvp.battlefieldWarp(self, loc);
        return SCRIPT_CONTINUE;
    }
    public int onBattlefieldInvitationAccept(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id controller = utils.getObjIdScriptVar(self, "battlefield.invitation.controller");
        String battlefieldName = utils.getStringScriptVar(self, "battlefield.invitation.battlefieldName");
        int btn = sui.getIntButtonPressed(params);
        params.put("player", self);
        if (factions.isRebel(self))
        {
            params.put("faction", factions.FACTION_FLAG_REBEL);
        }
        else if (factions.isImperial(self))
        {
            params.put("faction", factions.FACTION_FLAG_IMPERIAL);
        }
        params.put("name", getName(self));
        if (isIdValid(controller))
        {
            if (btn == sui.BP_CANCEL)
            {
                prose_package pp = new prose_package();
                pp = prose.setStringId(pp, SID_BATTLEFIELD_QUEUE_REFUSAL);
                pp = prose.setTO(pp, battlefieldName);
                sendSystemMessageProse(self, pp);
                utils.removeScriptVarTree(self, "battlefield");
                messageTo(controller, "refuseBattlefieldInvitation", params, 1.0f, false);
            }
            else 
            {
                prose_package pp = new prose_package();
                pp = prose.setStringId(pp, SID_BATTLEFIELD_QUEUE_ACCEPTED);
                pp = prose.setTO(pp, battlefieldName);
                sendSystemMessageProse(self, pp);
                messageTo(controller, "acceptBattlefieldInvitation", params, 1.0f, false);
                utils.setScriptVar(self, "battlefield.invited", controller);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int receiveBattlefieldSetupWarp(obj_id self, dictionary params) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, "battlefield.invited"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id controller = utils.getObjIdScriptVar(self, "battlefield.invited");
        if (!isIdValid(controller))
        {
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, "battlefield.active", controller);
        group.leaveGroup(self);
        return SCRIPT_CONTINUE;
    }
    public int onBattlefieldInvitationExpire(obj_id self, dictionary params) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "battlefield.invited") || !utils.hasScriptVar(self, "battlefield.queue"))
        {
            blog(self, "onBattlefieldInvitationExpire for self: " + self + " battlefield.invited: " + utils.getObjIdScriptVar(self, "battlefield.invited") + " battlefield.queue: " + utils.getStringScriptVar(self, "battlefield.queue"));
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, "battlefield.invitation.pid"))
        {
            int pid = utils.getIntScriptVar(self, "battlefield.invitation.pid");
            forceCloseSUIPage(pid);
        }
        obj_id controller = params.getObjId("battlefieldController");
        sendSystemMessage(self, SID_BATTLEFIELD_INVITATION_EXPIRED);
        utils.removeScriptVarTree(self, "battlefield");
        params.put("player", self);
        if (factions.isRebel(self))
        {
            params.put("faction", factions.FACTION_FLAG_REBEL);
        }
        else if (factions.isImperial(self))
        {
            params.put("faction", factions.FACTION_FLAG_IMPERIAL);
        }
        messageTo(controller, "refuseBattlefieldInvitation", params, 0.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int receiveBattlefieldQueueRemoval(obj_id self, dictionary params) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, "battlefield.queue"))
        {
            return SCRIPT_CONTINUE;
        }
        if (!utils.hasScriptVar(self, "battlefield." + BATTLEFIELD_NAMES[0]) || !utils.hasScriptVar(self, "battlefield." + BATTLEFIELD_NAMES[1]) || !utils.hasScriptVar(self, "battlefield." + BATTLEFIELD_NAMES[2]) || !utils.hasScriptVar(self, "battlefield." + BATTLEFIELD_NAMES[3]))
        {
            getClusterWideData("pvp", "*", true, self);
        }
        obj_id controllerWithMessage = params.getObjId("battlefieldController");
        obj_id controllerQueuedFor = getBattlefieldController(self);
        if (controllerWithMessage != controllerQueuedFor)
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, "battlefield.invitation.pid"))
        {
            int pid = utils.getIntScriptVar(self, "battlefield.invitation.pid");
            forceCloseSUIPage(pid);
        }
        if (utils.hasScriptVar(self, "battlefield.pid"))
        {
            int pid = utils.getIntScriptVar(self, "battlefield.pid");
            utils.removeScriptVar(self, "battlefield.pid");
            forceCloseSUIPage(pid);
        }
        String battlefieldName = params.getString("battlefieldName");
        if (battlefieldName != null && battlefieldName.length() > 0)
        {
            prose_package pp = new prose_package();
            pp = prose.setStringId(pp, SID_BATTLEFIELD_QUEUE_REMOVAL);
            pp = prose.setTO(pp, localize(new string_id("spam", battlefieldName)));
            sendSystemMessageProse(self, pp);
        }
        utils.removeScriptVarTree(self, "battlefield");
        return SCRIPT_CONTINUE;
    }
    public int receiveBattlefieldRefusePlayers(obj_id self, dictionary params) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "battlefield.invitation.pid"))
        {
            int pid = utils.getIntScriptVar(self, "battlefield.invitation.pid");
            forceCloseSUIPage(pid);
        }
        if (utils.hasScriptVar(self, "battlefield.pid"))
        {
            int pid = utils.getIntScriptVar(self, "battlefield.pid");
            utils.removeScriptVar(self, "battlefield.pid");
            forceCloseSUIPage(pid);
        }
        String battlefieldName = params.getString("battlefieldName");
        prose_package pp = new prose_package();
        pp = prose.setStringId(pp, SID_BATTLEFIELD_REFUSED);
        pp = prose.setTO(pp, localize(new string_id("spam", battlefieldName)));
        sendSystemMessageProse(self, pp);
        utils.setScriptVar(self, "battlefield.queue", battlefieldName);
        utils.removeScriptVar(self, "battlefield.invited");
        utils.removeScriptVar(self, "battlefield.active");
        return SCRIPT_CONTINUE;
    }
    public int receiveBattlefieldPlayParticlePickup(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id containedBy = getTopMostContainer(self);
        if (isIdValid(containedBy) && containedBy != self)
        {
            return SCRIPT_CONTINUE;
        }
        location loc = getLocation(self);
        obj_id newObject = createObject("object/tangible/theme_park/invisible_object.iff", loc);
        setObjVar(newObject, restuss_event.EFFECT_NAME, "republic_gunship_itv_touch_and_go.prt");
        setObjVar(newObject, restuss_event.EFFECT_VISABILITY, "visibility-100");
        setObjVar(newObject, restuss_event.EFFECT_DELTA, "0");
        obj_id[] playerList = 
        {
            self
        };
        utils.setScriptVar(newObject, instance.PLAYER_ID_LIST, playerList);
        attachScript(newObject, "theme_park.restuss_event.restuss_clientfx_controller");
        return SCRIPT_CONTINUE;
    }
    public int receiveBattlefieldWarpLocation(obj_id self, dictionary params) throws InterruptedException
    {
        utils.removeBatchObjVar(self, gcw.LIST_DAILY_KILL_VALUES);
        messageTo(self, "battlefieldWarp", params, 1.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int battlefieldWarp(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            blog(self, "Null Params passed in to battlefieldWarp.");
            return SCRIPT_CONTINUE;
        }
        int bfState = params.getInt("bfState");
        if (group.isGrouped(self) && bfState != pvp.PVP_STATE_BATTLE_START)
        {
            obj_id groupObj = getGroupObject(self);
            boolean isLeader = false;
            if (isIdValid(groupObj))
            {
                isLeader = group.isLeader(groupObj);
            }
            blog(self, "Player still grouped.  Leader: " + isLeader);
            group.leaveGroup(self);
        }
        location loc = params.getLocation("warpLocation");
        obj_id controller = params.getObjId("controller");
        if (loc == null)
        {
            blog(self, "Bad warp location.");
            return SCRIPT_CONTINUE;
        }
        if (isIdValid(controller) && !utils.hasScriptVar(self, "battlefield.active"))
        {
            utils.setScriptVar(self, "battlefield.active", controller);
            blog(controller, "receiveBattlefieldWarpLocation player " + getPlayerName(self) + "(" + self + ") did not have the scriptvar 'battlefield.active' when they were warping to the battlefield. It has now been set.");
        }
        pvp.battlefieldWarp(self, loc);
        return SCRIPT_CONTINUE;
    }
    public int receiveBattlefieldAnnouncement(obj_id self, dictionary params) throws InterruptedException
    {
        string_id message = params.getStringId("announceId");
        sendSystemMessage(self, message);
        return SCRIPT_CONTINUE;
    }
    public int OnCreatureDamaged(obj_id self, obj_id attacker, obj_id weapon, int[] damage) throws InterruptedException
    {
        if (beast_lib.isBeast(attacker) || pet_lib.isPet(attacker))
        {
            attacker = getMaster(attacker);
        }
        if (!isIdValid(attacker) || !exists(attacker) || !isPlayer(attacker))
        {
            doLogging("OnCreatureDamaged", "Attacker was invalid");
            return SCRIPT_CONTINUE;
        }
        String selfFac = factions.getFaction(self);
        String attFac = factions.getFaction(attacker);
        if (selfFac == null || attFac == null)
        {
            doLogging("OnCreatureDamage", "Self or attacker faction was null");
            return SCRIPT_CONTINUE;
        }
        if (selfFac.equals("Rebel") && !attFac.equals("Imperial"))
        {
            doLogging("OnCreatureDamage", "I am a rebel and my attacker is not imperial");
            return SCRIPT_CONTINUE;
        }
        if (selfFac.equals("Imperial") && !attFac.equals("Rebel"))
        {
            doLogging("OnCreatureDamaged", "I am an Imperial and my attcker is not Rebel");
            return SCRIPT_CONTINUE;
        }
        int combinedDamage = 0;
        for (int i1 : damage) {
            combinedDamage += i1;
        }
        Vector attackList = new Vector();
        attackList.setSize(0);
        if (utils.hasResizeableStringBatchScriptVar(self, gcw.LIST_CREDIT_FOR_KILLS))
        {
            attackList = utils.getResizeableStringBatchScriptVar(self, gcw.LIST_CREDIT_FOR_KILLS);
            gcw.clearCreditForKills(self);
        }
        int arrayPosition = gcw.findAttackerInArray(attackList, attacker);
        doLogging("OnCreatureDamaged", "Attackers position in the array is " + arrayPosition);
        String damageUpdate = null;
        if (arrayPosition == -1)
        {
            damageUpdate = gcw.packAttackerDamage(attacker, combinedDamage);
            doLogging("OnCreatureDamaged", "New entry string: " + damageUpdate);
            utils.addElement(attackList, damageUpdate);
        }
        else 
        {
            int newDamage = combinedDamage + gcw.getDamageFromAttacker(attackList, attacker);
            damageUpdate = gcw.packAttackerDamage(attacker, newDamage);
            doLogging("OnCreatureDamgaed", "Updated damage string: " + damageUpdate);
            attackList.set(arrayPosition, damageUpdate);
        }
        utils.setBatchScriptVar(self, gcw.LIST_CREDIT_FOR_KILLS, attackList);
        pvp.bfCreditForDamage(attacker, combinedDamage);
        return SCRIPT_CONTINUE;
    }
    public void cleanupOldSystemObjVar(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, VAR_PVP_LAST_UPDATE))
        {
            removeObjVar(self, VAR_PVP_LAST_UPDATE);
        }
        if (hasObjVar(self, VAR_PVP_LAST_KILLS))
        {
            removeObjVar(self, VAR_PVP_LAST_KILLS);
        }
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
    }
    public int invalidBattlefieldPlayer(obj_id self, dictionary params) throws InterruptedException
    {
        location loc = params.getLocation("kickOutLocation");
        location goodLoc = locations.getGoodLocationAroundLocation(loc, 1.0f, 1.0f, 1.5f, 1.5f);
        if (isGod(self))
        {
            sendSystemMessage(self, SID_PVP_BATTLEFIELD_KICK_OUT_GODMODE);
            return SCRIPT_CONTINUE;
        }
        sendSystemMessage(self, SID_PVP_BATTLEFIELD_KICK_OUT_INVALID);
        utils.warpPlayer(self, Objects.requireNonNullElse(goodLoc, loc));
        return SCRIPT_CONTINUE;
    }
    public int createBattlefieldWaypoint(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        location loc = params.getLocation("terminal_location");
        obj_id terminal = params.getObjId("terminal");
        if (loc == null)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id waypoint = createWaypointInDatapad(self, loc);
        setWaypointName(waypoint, "Terminal Being Captured");
        setWaypointActive(waypoint, true);
        setWaypointColor(waypoint, "purple");
        utils.setScriptVar(self, "battlefield.waypoint." + terminal, waypoint);
        int gameTime = getGameTime();
        utils.setScriptVar(self, "battlefield.waypointTime." + terminal, gameTime);
        params.put("gameTime", gameTime);
        params.put("timeout", 1);
        messageTo(self, "destroyBattlefieldWaypoint", params, 90.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int destroyBattlefieldWaypoint(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id terminal = params.getObjId("terminal");
        obj_id waypoint = obj_id.NULL_ID;
        int waypointTime = utils.getIntScriptVar(self, "battlefield.waypointTime." + terminal);
        int messageTime = params.getInt("gameTime");
        int timeout = params.getInt("timeout");
        if (timeout == 1 && waypointTime != messageTime)
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, "battlefield.waypoint." + terminal))
        {
            waypoint = utils.getObjIdScriptVar(self, "battlefield.waypoint." + terminal);
        }
        if (isIdValid(waypoint))
        {
            destroyWaypointInDatapad(waypoint, self);
        }
        utils.removeScriptVar(self, "battlefield.waypoint." + terminal);
        utils.removeScriptVar(self, "battlefield.waypointTime." + terminal);
        return SCRIPT_CONTINUE;
    }
}
