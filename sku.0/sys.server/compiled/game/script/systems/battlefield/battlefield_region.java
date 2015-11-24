package script.systems.battlefield;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.battlefield;
import script.library.factions;
import script.library.player_structure;
import script.library.sui;
import script.library.utils;

public class battlefield_region extends script.base_script
{
    public battlefield_region()
    {
    }
    public static final string_id SID_BATTLEFIELD_STATUS = new string_id("battlefield", "status");
    public static final string_id SID_GAME_STATUS = new string_id("battlefield", "game_status");
    public static final String VAR_TIME_WARNING_LEVEL = "battlefield.game.time_warning_level";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "battlefield_region::OnAttach");
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "battlefield_region::OnInitialize -- " + self);
        messageTo(self, "msgInitializeRegion", null, 0.0f, true);
        if (hasObjVar(self, battlefield.VAR_BATTLEFIELD))
        {
            String config = getConfigSetting("GameServer", "disableBattlefields");
            if (config != null)
            {
                if (config.equals("true"))
                {
                    battlefield.endBattlefield(self, false);
                    return SCRIPT_CONTINUE;
                }
            }
            int version = battlefield.getBattlefieldVersion(self);
            LOG("LOG_CHANNEL", "version ->" + version + "  current_version ->" + battlefield.CURRENT_VERSION);
            if (version == -1)
            {
                battlefield.endBattlefield(self);
                if (battlefield.updateBattlefieldData(self, null))
                {
                    setObjVar(self, battlefield.VAR_VERSION, battlefield.CURRENT_VERSION);
                }
                else 
                {
                    LOG("LOG_CHANNEL", "battlefield.battlefield_region::OnInitialize -- Unable to update " + self);
                }
            }
            else if (version < battlefield.CURRENT_VERSION)
            {
                battlefield.endBattlefield(self);
                if (battlefield.updateBattlefieldData(self, null))
                {
                    setObjVar(self, battlefield.VAR_VERSION, battlefield.CURRENT_VERSION);
                }
                else 
                {
                    LOG("LOG_CHANNEL", "battlefield.battlefield_region::OnInitialize -- Unable to update " + self);
                }
            }
            battlefield.endBattlefield(self);
            battlefield.updateBattlefieldData(self, null);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String triggerVolumeName, obj_id breacher) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "battlefield_region::OnTriggerVolumeEntered -- " + triggerVolumeName + "(" + breacher + ")");
        if (!isPlayer(breacher))
        {
            return SCRIPT_CONTINUE;
        }
        if (triggerVolumeName.equals(battlefield.STRING_TRIG_OUTER_PERIMETER))
        {
            if (!hasScript(breacher, battlefield.SCRIPT_PLAYER_BATTLEFIELD))
            {
                attachScript(breacher, battlefield.SCRIPT_PLAYER_BATTLEFIELD);
            }
            if (battlefield.isBattlefieldActive(self))
            {
                LOG("LOG_CHANNEL", "battlefield_region::OnTriggerVolumeEntered -- battlefield active");
                if (battlefield.canJoinBattlefield(self, breacher, true))
                {
                    LOG("LOG_CHANNEL", "battlefield_region::OnTriggerVolumeEntered -- can join");
                    if (!hasObjVar(breacher, battlefield.VAR_BATTLEFIELD_TO_ENTER))
                    {
                        int faction_id = pvpGetAlignedFaction(breacher);
                        String faction = factions.getFactionNameByHashCode(faction_id);
                        LOG("LOG_CHANNEL", "faction_id ->" + faction_id + " faction ->" + faction);
                        if (faction == null)
                        {
                            String[] factions_allowed = battlefield.getFactionsAllowed(self);
                            if (!battlefield.hasSelectFactionSui(breacher))
                            {
                                LOG("LOG_CHANNEL", "!!!!!!!battlefield_region::OnTriggerVolumeEntered -- sui active for " + breacher);
                                dictionary d = new dictionary();
                                d.put("master_object", self);
                                messageTo(breacher, "msgSelectFactionPrompt", d, 0.0f, false);
                            }
                        }
                        else 
                        {
                            if (battlefield.isFactionAllowed(self, faction))
                            {
                                if (battlefield.isFactionRemaining(self, faction))
                                {
                                    if (!battlefield.hasSelectFactionSui(breacher))
                                    {
                                        dictionary d = new dictionary();
                                        d.put("master_object", self);
                                        d.put("faction", faction);
                                        messageTo(breacher, "msgVerifyFactionPrompt", d, 0.0f, false);
                                    }
                                }
                                else 
                                {
                                    LOG("LOG_CHANNEL", breacher + " ->Your faction has been eliminated from the battle. You must wait until the next battle to join.");
                                    sendSystemMessageTestingOnly(breacher, "Your faction has been eliminated from the battle. You must wait until the next battle to join.");
                                    return SCRIPT_CONTINUE;
                                }
                            }
                            else 
                            {
                                LOG("LOG_CHANNEL", breacher + " ->Your faction is not permitted within this battlefield.");
                                sendSystemMessageTestingOnly(breacher, "Your faction is not permitted within this battlefield.");
                                return SCRIPT_CONTINUE;
                            }
                        }
                        setObjVar(breacher, battlefield.VAR_BATTLEFIELD_TO_ENTER, self);
                    }
                }
                else 
                {
                    if (battlefield.canExitBattlefield(self, breacher, false))
                    {
                        expelFromTriggerVolume(self, battlefield.STRING_TRIG_OUTER_PERIMETER, breacher);
                    }
                }
            }
        }
        if (triggerVolumeName.equals(battlefield.STRING_TRIG_BOUNDARY))
        {
            battlefield.addPlayerToPlayerList(breacher, self);
            if (battlefield.isBattlefieldActive(self))
            {
                if (!battlefield.canEnterBattlefield(self, breacher))
                {
                    setObjVar(breacher, battlefield.VAR_BATTLEFIELD_TO_ENTER, self);
                    int faction_id = pvpGetAlignedFaction(breacher);
                    String faction = factions.getFactionNameByHashCode(faction_id);
                    if (faction == null)
                    {
                        LOG("LOG_CHANNEL", breacher + " ->A battle is in progress here. You must ally yourself with a faction before you may enter.");
                        sendSystemMessageTestingOnly(breacher, "A battle is in progress here. You must ally yourself with a faction before you may enter.");
                        if (!battlefield.hasSelectFactionSui(breacher))
                        {
                            dictionary d = new dictionary();
                            d.put("master_object", self);
                            messageTo(breacher, "msgSelectFactionPrompt", d, 0.0f, false);
                        }
                    }
                    else 
                    {
                        if (battlefield.isFactionAllowed(self, faction))
                        {
                            if (battlefield.isFactionRemaining(self, faction))
                            {
                                if (!battlefield.hasSelectFactionSui(breacher))
                                {
                                    dictionary d = new dictionary();
                                    d.put("master_object", self);
                                    d.put("faction", faction);
                                    messageTo(breacher, "msgVerifyFactionPrompt", d, 0.0f, false);
                                }
                            }
                            else 
                            {
                                LOG("LOG_CHANNEL", breacher + " ->Your faction has been eliminated from the battle. You must wait until the next battle to join.");
                                sendSystemMessageTestingOnly(breacher, "Your faction has been eliminated from the battle. You must wait until the next battle to join.");
                                return SCRIPT_CONTINUE;
                            }
                        }
                        else 
                        {
                            LOG("LOG_CHANNEL", breacher + " ->Your faction is not permitted within this battlefield.");
                            sendSystemMessageTestingOnly(breacher, "Your faction is not permitted within this battlefield.");
                            return SCRIPT_CONTINUE;
                        }
                    }
                    expelFromTriggerVolume(self, battlefield.STRING_TRIG_BOUNDARY, breacher);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeExited(obj_id self, String triggerVolumeName, obj_id breacher) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "battlefield_region::OnTriggerVolumeExited -- " + triggerVolumeName + "(" + breacher + ")");
        if (!isMob(breacher))
        {
            return SCRIPT_CONTINUE;
        }
        boolean expelled = false;
        if (hasObjVar(breacher, battlefield.VAR_EXPELLED))
        {
            expelled = true;
            removeObjVar(breacher, battlefield.VAR_EXPELLED);
        }
        if (triggerVolumeName.equals(battlefield.STRING_TRIG_BOUNDARY))
        {
            if (!isPlayer(breacher))
            {
                if (battlefield.isBattlefieldConstructed(breacher))
                {
                    battlefield.removeBattlefieldObject(self, breacher);
                }
                return SCRIPT_CONTINUE;
            }
            battlefield.removePlayerFromPlayerList(breacher, self);
            if (battlefield.isBattlefieldActive(self) && !battlefield.canJoinBattlefield(self, breacher) && !expelled)
            {
                if (battlefield.canExitBattlefield(self, breacher, true))
                {
                    LOG("LOG_CHANNEL", breacher + " ->You are about to leave the battlefield. If you do so, you will be unable to return until the current battle is over.");
                    sendSystemMessageTestingOnly(breacher, "You are about to leave the battlefield. If you do so, you will be unable to return until the current battle is over.");
                }
                else 
                {
                    confineToTriggerVolume(self, battlefield.STRING_TRIG_BOUNDARY, breacher);
                }
            }
        }
        if (triggerVolumeName.equals(battlefield.STRING_TRIG_OUTER_PERIMETER))
        {
            if (!isPlayer(breacher))
            {
                return SCRIPT_CONTINUE;
            }
            battlefield.removeFactionItems(breacher);
            if (hasObjVar(self, battlefield.VAR_BATTLEFIELD_TO_ENTER))
            {
                removeObjVar(self, battlefield.VAR_BATTLEFIELD_TO_ENTER);
            }
            obj_id bf_entered = battlefield.getBattlefieldEntered(breacher);
            if (bf_entered == null)
            {
                if (hasScript(breacher, battlefield.SCRIPT_PLAYER_BATTLEFIELD))
                {
                    dictionary params = new dictionary();
                    params.put("master_object", self);
                    messageTo(breacher, "msgRemoveBattlefieldScript", params, 0.0f, true);
                }
            }
            if (battlefield.isBattlefieldActive(self) && !battlefield.canJoinBattlefield(self, breacher))
            {
                setObjVar(breacher, battlefield.VAR_TIME_EXITED, getGameTime());
                if (!expelled)
                {
                    LOG("LOG_CHANNEL", breacher + " ->You have left the battlefield.");
                    sendSystemMessageTestingOnly(breacher, "You have left the battlefield.");
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int battlefield_root = mi.addRootMenu(menu_info_types.SERVER_TRAVEL_OPTIONS, SID_BATTLEFIELD_STATUS);
        mi.addSubMenu(battlefield_root, menu_info_types.SERVER_BAZAAR_OPTIONS, SID_GAME_STATUS);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.SERVER_TRAVEL_OPTIONS)
        {
            LOG("LOG_CHANNEL", "battlefield_region::OnBattlefieldStatus");
            queueCommand(player, (-567736575), null, "", COMMAND_PRIORITY_DEFAULT);
        }
        if (item == menu_info_types.SERVER_BAZAAR_OPTIONS)
        {
            queueCommand(player, (-567736575), null, "game", COMMAND_PRIORITY_DEFAULT);
        }
        return SCRIPT_CONTINUE;
    }
    public int msgInitializeRegion(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "battlefield_region::msgInitializeRegion -- " + params);
        if (!hasObjVar(self, battlefield.VAR_BATTLEFIELD))
        {
            LOG("LOG_CHANNEL", "battlefield::msgInitializeRegion -- Initializing new battlefield " + self);
            if (params.containsKey("region_name"))
            {
                String regionPlanet = params.getString("region_planet");
                String regionName = params.getString("region_name");
                region bf = getRegion(regionPlanet, regionName);
                setObjVar(self, battlefield.VAR_NAME, regionName);
                LOG("LOG_CHANNEL", "bf ->" + bf);
                battlefield.updateBattlefieldData(self, bf);
                int restart_min = battlefield.getGameRestartMinimum(self);
                int restart_max = battlefield.getGameRestartMaximum(self);
                int start_time = rand(restart_min, restart_max);
                setObjVar(self, battlefield.VAR_NEXT_GAME, getGameTime() + start_time);
                messageTo(self, "msgStartGame", null, (float)start_time, false);
            }
            else 
            {
                LOG("LOG_CHANNEL", "battlefield::msgInitializeRegion -- Unable to find the region for " + self);
                return SCRIPT_CONTINUE;
            }
        }
        if (!hasTriggerVolume(self, battlefield.STRING_TRIG_BOUNDARY))
        {
            float radius = battlefield.getBattlefieldExtent(self);
            createTriggerVolume(battlefield.STRING_TRIG_BOUNDARY, radius, true);
            createTriggerVolume(battlefield.STRING_TRIG_OUTER_PERIMETER, radius + battlefield.OUTER_PERIMETER_WIDTH, true);
            LOG("LOG_CHANNEL", "Boundary trigger created -- " + radius);
        }
        String chat_room_all = battlefield.getChatRoomNameAllFactions(self);
        String name = battlefield.getBattlefieldName(self);
        LOG("LOG_CHANNEL", "battlefield_region::msgInitializeRegion -- creating chatroom " + chat_room_all);
        chatCreateRoom(true, chat_room_all, name);
        String[] allowed_factions = battlefield.getFactionsAllowed(self);
        if (allowed_factions != null)
        {
            if (allowed_factions.length > 1)
            {
                for (int i = 0; i < allowed_factions.length; i++)
                {
                    chatCreateRoom(true, battlefield.getChatRoomNameFaction(self, allowed_factions[i]), name + "-" + allowed_factions[i]);
                }
            }
        }
        else 
        {
            LOG("LOG_CHANNEL", "battlefield_region::msgInitializeRegion -- factions_allowed is null." + chat_room_all);
        }
        messageTo(self, "msgCreateBattlefieldChat", null, 10.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int msgStartGame(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "battlefield_region::msgStartGame");
        battlefield.startBattlefield(self);
        return SCRIPT_CONTINUE;
    }
    public int msgGameTimePulse(obj_id self, dictionary params) throws InterruptedException
    {
        int timer_set = battlefield.setGameTimer(self, false);
        if (timer_set > 0)
        {
            String[] factions_allowed = battlefield.getFactionsAllowed(self);
            if (factions_allowed != null)
            {
                for (int i = 0; i < factions_allowed.length; i++)
                {
                    battlefield.incrementFactionBuildPoints(self, factions_allowed[i], 2);
                }
            }
            int warning_level = 0;
            int[] warning_times = 
            {
                3600,
                1800,
                900,
                600,
                300,
                60,
                30,
                10
            };
            if (hasObjVar(self, VAR_TIME_WARNING_LEVEL))
            {
                warning_level = getIntObjVar(self, VAR_TIME_WARNING_LEVEL);
            }
            if (warning_level < warning_times.length)
            {
                int warning_time = warning_times[warning_level];
                if (timer_set < warning_time)
                {
                    if (battlefield.getGameDuration(self) > warning_time)
                    {
                        if (warning_time > 0)
                        {
                            int[] time_conv = player_structure.convertSecondsTime(warning_time);
                            String time_str = player_structure.assembleTimeRemaining(time_conv);
                            battlefield.sendBattlefieldMessage(self, "Time Remaining: " + time_str);
                        }
                    }
                    warning_level++;
                    setObjVar(self, VAR_TIME_WARNING_LEVEL, warning_level);
                }
            }
            messageTo(self, "msgGameTimePulse", null, (int)battlefield.GAME_TIME_PULSE, false);
            dictionary new_params = new dictionary();
            new_params.put("time", timer_set);
            messageTo(self, "msgGameScriptPulse", new_params, 0.0f, false);
        }
        else 
        {
            messageTo(self, "msgTimeExpired", null, 0.0f, true);
        }
        return SCRIPT_CONTINUE;
    }
    public int msgAddPlayerToBattlefield(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        if (isIdValid(player))
        {
            battlefield.setBattlefieldEntered(player, self);
        }
        return SCRIPT_CONTINUE;
    }
    public int msgBattlefieldKill(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int msgCreateBattlefieldChat(obj_id self, dictionary params) throws InterruptedException
    {
        String chat_room_all = battlefield.getChatRoomNameAllFactions(self);
        String name = battlefield.getBattlefieldName(self);
        LOG("LOG_CHANNEL", "battlefield_region::msgInitializeRegion -- creating chatroom " + chat_room_all);
        chatCreateRoom(true, chat_room_all, name);
        String[] allowed_factions = battlefield.getFactionsAllowed(self);
        if (allowed_factions != null)
        {
            if (allowed_factions.length > 1)
            {
                for (int i = 0; i < allowed_factions.length; i++)
                {
                    chatCreateRoom(true, battlefield.getChatRoomNameFaction(self, allowed_factions[i]), name + "-" + allowed_factions[i]);
                }
            }
        }
        else 
        {
            LOG("LOG_CHANNEL", "battlefield_region::msgInitializeRegion -- factions_allowed is null." + chat_room_all);
        }
        return SCRIPT_CONTINUE;
    }
    public int msgGameStatHistory(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        if (player == null || !player.isLoaded())
        {
            return SCRIPT_CONTINUE;
        }
        Vector dsrc = new Vector();
        dsrc.setSize(0);
        if (!hasObjVar(self, battlefield.VAR_STAT_ROOT))
        {
            dsrc = utils.addElement(dsrc, "No previous battle data available.");
        }
        else 
        {
            String type = getStringObjVar(self, battlefield.VAR_STAT_TYPE);
            if (type == null)
            {
                dsrc = utils.addElement(dsrc, "No previous battle data available.");
            }
            else if (type.equals("Destroy"))
            {
                int total_time = getIntObjVar(self, battlefield.VAR_STAT_TIME);
                int[] convert_time = player_structure.convertSecondsTime(total_time);
                String time_str = player_structure.assembleTimeRemaining(convert_time);
                dsrc = utils.addElement(dsrc, "Total Time: " + time_str);
                String winner = getStringObjVar(self, battlefield.VAR_STAT_WINNER);
                if (winner != null && !winner.equals("NONE"))
                {
                    dsrc = utils.addElement(dsrc, "Winning Faction: " + winner);
                }
                else 
                {
                    dsrc = utils.addElement(dsrc, "Winning Faction: None");
                }
                obj_id[] participants = battlefield.getParticipantsOnBattlefield(self);
                dsrc = utils.addElement(dsrc, "Number of Participants: " + participants.length);
                dsrc = utils.addElement(dsrc, "Kills/Deaths");
                String[] factions_allowed = battlefield.getAllFactionsAllowed(self);
                if (factions_allowed != null)
                {
                    for (int i = 0; i < factions_allowed.length; i++)
                    {
                        dsrc = utils.addElement(dsrc, "   " + factions_allowed[i]);
                        int kills = getIntObjVar(self, battlefield.VAR_STAT_KILLS + factions_allowed[i]);
                        int deaths = getIntObjVar(self, battlefield.VAR_STAT_DEATHS + factions_allowed[i]);
                        dsrc = utils.addElement(dsrc, "      " + "Kills: " + kills);
                        dsrc = utils.addElement(dsrc, "      " + "Deaths: " + deaths);
                    }
                }
            }
            else if (type.equals("Assault"))
            {
                int total_time = getIntObjVar(self, battlefield.VAR_STAT_TIME);
                int[] convert_time = player_structure.convertSecondsTime(total_time);
                String time_str = player_structure.assembleTimeRemaining(convert_time);
                dsrc = utils.addElement(dsrc, "Total Time: " + time_str);
                String winner = getStringObjVar(self, battlefield.VAR_STAT_WINNER);
                String[] player_faction = battlefield.getFactionsAllowed(self);
                if (winner != null && !winner.equals("AI"))
                {
                    dsrc = utils.addElement(dsrc, "Result: " + player_faction[0] + " win");
                }
                else 
                {
                    dsrc = utils.addElement(dsrc, "Result: " + player_faction[0] + " loss");
                }
                obj_id[] participants = battlefield.getParticipantsOnBattlefield(self);
                dsrc = utils.addElement(dsrc, "Number of participants: " + participants.length);
                dsrc = utils.addElement(dsrc, "Kills/Deaths");
                String[] factions_allowed = battlefield.getAllFactionsAllowed(self);
                if (factions_allowed != null)
                {
                    for (int i = 0; i < factions_allowed.length; i++)
                    {
                        dsrc = utils.addElement(dsrc, "   " + factions_allowed[i]);
                        int kills = getIntObjVar(self, battlefield.VAR_STAT_KILLS + factions_allowed[i]);
                        int deaths = getIntObjVar(self, battlefield.VAR_STAT_DEATHS + factions_allowed[i]);
                        dsrc = utils.addElement(dsrc, "      " + "Kills: " + kills);
                        dsrc = utils.addElement(dsrc, "      " + "Deaths: " + deaths);
                    }
                }
            }
            else 
            {
                dsrc = utils.addElement(dsrc, "No previous battle data available.");
            }
        }
        sui.listbox(player, "Previous Battle Statistics", "Objective: Destroy", dsrc);
        return SCRIPT_CONTINUE;
    }
}
