package script.player;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.guild;
import script.library.sui;
import script.library.prose;
import script.library.player_structure;
import java.util.StringTokenizer;

public class player_guild extends script.base_script
{
    public player_guild()
    {
    }
    public static final string_id SID_GUILDREMOVE_NOT_IN_GUILD = new string_id("base_player", "guildremove_not_in_guild");
    public static final string_id SID_GUILDSTATUS_NOT_PLAYER = new string_id("base_player", "guildstatus_not_player");
    public static final string_id SID_GUILDSTATUS_NOT_IN_GUILD = new string_id("base_player", "guildstatus_not_in_guild");
    public static final string_id SID_GUILDSTATUS_PLAYER_NOT_FOUND = new string_id("base_player", "guildstatus_player_not_found");
    public static final string_id SID_GUILDSTATUS_LEADER = new string_id("base_player", "guildstatus_leader");
    public static final string_id SID_GUILDSTATUS_LEADER_TITLE = new string_id("base_player", "guildstatus_leader_title");
    public static final string_id SID_GUILDSTATUS_MEMBER = new string_id("base_player", "guildstatus_member");
    public static final string_id SID_GUILDSTATUS_MEMBER_TITLE = new string_id("base_player", "guildstatus_member_title");
    public static final string_id SID_GUILD_NO_PERMISSION = new string_id("guild", "guild_no_permission_operation");
    public static final string_id SID_GUILD_MEMBER_LOGIN = new string_id("spam", "guild_member_login");
    public static final string_id SID_GUILD_MEMBER_LOGOFF = new string_id("spam", "guild_member_logoff");
    public static final string_id SID_GUILD_NOTIFICATION_ON = new string_id("spam", "guild_notification_on");
    public static final string_id SID_GUILD_NOTIFICATION_OFF = new string_id("spam", "guild_notification_off");
    public static final string_id SID_GUILD_NOTIFY_TOO_SOON = new string_id("spam", "guild_notify_too_soon");
    public int OnLogin(obj_id self) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "guild.loginNotified"))
        {
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, "guild.loginNotified", true);
        dictionary params = new dictionary();
        params.put("login", true);
        messageTo(self, "handleStatusUpdate", params, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int OnLogout(obj_id self) throws InterruptedException
    {
        utils.removeScriptVar(self, "guild.loginNotified");
        dictionary params = new dictionary();
        params.put("login", false);
        messageTo(self, "handleStatusUpdate", params, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int cmdGuildNotify(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        int guildId = getGuildId(self);
        if (guildId <= 0)
        {
            return SCRIPT_CONTINUE;
        }
        int lastGuildNotify = utils.getIntScriptVar(self, "guild.lastGuildNotify");
        if (getGameTime() - lastGuildNotify < 10)
        {
            sendSystemMessage(self, SID_GUILD_NOTIFY_TOO_SOON);
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, "guild.lastGuildNotify", getGameTime());
        dictionary dict = new dictionary();
        dict.put("onlineStatus", guild.hasGuildPermission(guildId, self, guild.GUILD_PERMISSION_ONLINE_STATUS));
        dict.put("guildId", guildId);
        guild.togglePersonalPermission(guildId, self, getName(self), guild.GUILD_PERMISSION_ONLINE_STATUS);
        messageTo(self, "toggleGuildNotifyMessage", dict, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int toggleGuildNotifyMessage(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        boolean onlineStatus = params.getBoolean("onlineStatus");
        int guildId = params.getInt("guildId");
        if (onlineStatus == guild.hasGuildPermission(guildId, self, guild.GUILD_PERMISSION_ONLINE_STATUS))
        {
            messageTo(self, "toggleGuildNotifyMessage", params, 1, false);
            return SCRIPT_CONTINUE;
        }
        if (guild.hasGuildPermission(guildId, self, guild.GUILD_PERMISSION_ONLINE_STATUS))
        {
            sendSystemMessage(self, SID_GUILD_NOTIFICATION_ON);
        }
        else 
        {
            sendSystemMessage(self, SID_GUILD_NOTIFICATION_OFF);
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdGuildremove(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        int guildId = getGuildId(self);
        if (guild.hasElectionEnded(self))
        {
            guild.stopElection(self);
            return SCRIPT_CONTINUE;
        }
        StringTokenizer st = new java.util.StringTokenizer(params);
        int tokens = st.countTokens();
        String name = "";
        if (st.hasMoreTokens())
        {
            name = st.nextToken();
        }
        if (guildId != 0)
        {
            if (name != null && name.length() > 0)
            {
                target = guild.findMemberIdByName(guildId, name, false, true);
            }
            if (!isIdValid(target))
            {
                sendSystemMessage(self, SID_GUILDSTATUS_PLAYER_NOT_FOUND);
                return SCRIPT_CONTINUE;
            }
            if (target == self)
            {
                guild.leave(self);
            }
            else 
            {
                guild.kick(guildId, self, name);
            }
        }
        else 
        {
            sendSystemMessage(self, SID_GUILDREMOVE_NOT_IN_GUILD);
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdGuildstatus(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        obj_id intendedTarget = getIntendedTarget(self);
        obj_id lookAtTarget = getLookAtTarget(self);
        if (isIdValid(lookAtTarget))
        {
            target = lookAtTarget;
        }
        else 
        {
            target = intendedTarget;
        }
        if (!isIdValid(target))
        {
            return SCRIPT_CONTINUE;
        }
        else 
        {
            if (!isPlayer(target))
            {
                sendSystemMessage(self, SID_GUILDSTATUS_NOT_PLAYER);
            }
            else 
            {
                prose_package pp = new prose_package();
                pp.actor.set(getName(target));
                int guildId = getGuildId(target);
                if (guildId == 0)
                {
                    pp.stringId = SID_GUILDSTATUS_NOT_IN_GUILD;
                }
                else 
                {
                    String title = guildGetMemberTitle(guildId, target);
                    pp.target.set(guildGetName(guildId));
                    pp.other.set(title);
                    if (guildGetLeader(guildId) == target)
                    {
                        if (title.length() != 0)
                        {
                            pp.stringId = SID_GUILDSTATUS_LEADER_TITLE;
                        }
                        else 
                        {
                            pp.stringId = SID_GUILDSTATUS_LEADER;
                        }
                    }
                    else 
                    {
                        if (title.length() != 0)
                        {
                            pp.stringId = SID_GUILDSTATUS_MEMBER_TITLE;
                        }
                        else 
                        {
                            pp.stringId = SID_GUILDSTATUS_MEMBER;
                        }
                    }
                }
                sendSystemMessageProse(self, pp);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdGuildShow(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        int guildId = getGuildId(self);
        if (guild.hasWindowPid(self))
        {
            int pid = guild.getWindowPid(self);
            forceCloseSUIPage(pid);
            guild.removeWindowPid(self);
        }
        if (guildId != 0)
        {
            if (guild.hasElectionEnded(self))
            {
                guild.stopElection(self);
            }
            guild.showGuildMembers(self, self, 0, -1, "", "");
        }
        else 
        {
            sendSystemMessage(self, SID_GUILDREMOVE_NOT_IN_GUILD);
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdGuildShowAlpha(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (guild.hasWindowPid(self))
        {
            int pid = guild.getWindowPid(self);
            forceCloseSUIPage(pid);
            guild.removeWindowPid(self);
        }
        int guildId = getGuildId(self);
        if (guildId != 0)
        {
            if (guild.hasElectionEnded(self))
            {
                guild.stopElection(self);
            }
            guild.showGuildMembers(self, self, 0, -1, "", "");
        }
        else 
        {
            sendSystemMessage(self, SID_GUILDREMOVE_NOT_IN_GUILD);
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdGuildShowTitle(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (guild.hasWindowPid(self))
        {
            int pid = guild.getWindowPid(self);
            forceCloseSUIPage(pid);
            guild.removeWindowPid(self);
        }
        int guildId = getGuildId(self);
        if (guildId != 0)
        {
            if (guild.hasElectionEnded(self))
            {
                guild.stopElection(self);
                return SCRIPT_CONTINUE;
            }
            guild.showGuildMembers(self, self, 0, -1, "", "");
        }
        else 
        {
            sendSystemMessage(self, SID_GUILDREMOVE_NOT_IN_GUILD);
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdGuildShowPermission(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (guild.hasWindowPid(self))
        {
            int pid = guild.getWindowPid(self);
            forceCloseSUIPage(pid);
            guild.removeWindowPid(self);
        }
        int guildId = getGuildId(self);
        if (guildId != 0)
        {
            if (guild.hasElectionEnded(self))
            {
                guild.stopElection(self);
                return SCRIPT_CONTINUE;
            }
            guild.showPermissionList(self, guildId);
        }
        else 
        {
            sendSystemMessage(self, SID_GUILDREMOVE_NOT_IN_GUILD);
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdGuildShowName(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (guild.hasWindowPid(self))
        {
            int pid = guild.getWindowPid(self);
            forceCloseSUIPage(pid);
            guild.removeWindowPid(self);
        }
        int guildId = getGuildId(self);
        if (guildId != 0)
        {
            if (guild.hasElectionEnded(self))
            {
                guild.stopElection(self);
                return SCRIPT_CONTINUE;
            }
            guild.showGuildMembers(self, self, 0, -1, "", "");
        }
        else 
        {
            sendSystemMessage(self, SID_GUILDREMOVE_NOT_IN_GUILD);
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdGuildInfo(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        int guildId = getGuildId(self);
        if (guildId != 0)
        {
            if (guild.hasElectionEnded(self))
            {
                guild.stopElection(self);
                return SCRIPT_CONTINUE;
            }
            guild.showGuildInfo(self);
        }
        else 
        {
            sendSystemMessage(self, SID_GUILDREMOVE_NOT_IN_GUILD);
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdGuildSponsor(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (guild.hasWindowPid(self))
        {
            int pid = guild.getWindowPid(self);
            forceCloseSUIPage(pid);
            guild.removeWindowPid(self);
        }
        int guildId = getGuildId(self);
        if (guildId == 0)
        {
            sendSystemMessage(self, SID_GUILDREMOVE_NOT_IN_GUILD);
            return SCRIPT_CONTINUE;
        }
        if (guild.hasElectionEnded(self))
        {
            guild.stopElection(self);
        }
        if (guild.hasGuildPermission(guildId, self, guild.GUILD_PERMISSION_SPONSOR))
        {
            int pid = sui.inputbox(self, self, guild.STR_GUILD_SPONSOR_PROMPT, sui.OK_CANCEL, guild.STR_GUILD_SPONSOR_TITLE, sui.INPUT_NORMAL, null, "onGuildSponsorResponse");
            guild.setWindowPid(self, pid);
        }
        else 
        {
            sendSystemMessage(self, SID_GUILD_NO_PERMISSION);
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdGuildChangeName(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (guild.hasWindowPid(self))
        {
            int pid = guild.getWindowPid(self);
            forceCloseSUIPage(pid);
            guild.removeWindowPid(self);
        }
        int guildId = getGuildId(self);
        if (guildId == 0)
        {
            sendSystemMessage(self, SID_GUILDREMOVE_NOT_IN_GUILD);
            return SCRIPT_CONTINUE;
        }
        if (guild.hasElectionEnded(self))
        {
            guild.stopElection(self);
            sendSystemMessage(self, SID_GUILD_NO_PERMISSION);
            return SCRIPT_CONTINUE;
        }
        if (guild.hasGuildPermission(guildId, self, guild.GUILD_PERMISSION_NAMECHANGE))
        {
            int pid = sui.inputbox(self, self, guild.STR_GUILD_NAMECHANGE_NAME_PROMPT, sui.OK_CANCEL, guild.STR_GUILD_NAMECHANGE_NAME_TITLE, sui.INPUT_NORMAL, null, "onGuildNamechangeNameResponse");
            guild.setWindowPid(self, pid);
        }
        else 
        {
            sendSystemMessage(self, SID_GUILD_NO_PERMISSION);
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdGuildDisband(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (guild.hasWindowPid(self))
        {
            int pid = guild.getWindowPid(self);
            forceCloseSUIPage(pid);
            guild.removeWindowPid(self);
        }
        int guildId = getGuildId(self);
        if (guildId == 0)
        {
            sendSystemMessage(self, SID_GUILDREMOVE_NOT_IN_GUILD);
            return SCRIPT_CONTINUE;
        }
        if (guild.hasElectionEnded(self))
        {
            guild.stopElection(self);
            sendSystemMessage(self, SID_GUILD_NO_PERMISSION);
            return SCRIPT_CONTINUE;
        }
        if (guild.hasGuildPermission(guildId, self, guild.GUILD_PERMISSION_DISBAND))
        {
            int pid = sui.msgbox(self, self, guild.STR_GUILD_DISBAND_PROMPT, sui.YES_NO, guild.STR_GUILD_DISBAND_TITLE, sui.MSG_NORMAL, "onGuildDisbandResponse");
            guild.setWindowPid(self, pid);
        }
        else 
        {
            sendSystemMessage(self, SID_GUILD_NO_PERMISSION);
        }
        return SCRIPT_CONTINUE;
    }
    public int startGuildSponsorSui(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        if (guild.hasWindowPid(self))
        {
            int pid = guild.getWindowPid(self);
            forceCloseSUIPage(pid);
            guild.removeWindowPid(self);
        }
        obj_id sender = params.getObjId("sender");
        if (getGuildId(self) != 0)
        {
            dictionary dict = new dictionary();
            dict.put("proseSID", guild.SID_GUILD_SPONSOR_ALREADY_IN_GUILD);
            String name = getFirstName(self);
            dict.put("player", name);
            messageTo(sender, "onGuildSponsorVerifyResponseProse", dict, 0, false);
            return SCRIPT_CONTINUE;
        }
        obj_id targetPlayerObj = getPlayerObject(self);
        if (isIdValid(sender) && !isIgnoring(targetPlayerObj, getPlayerName(sender)))
        {
            utils.setScriptVar(self, "guildSponsor.sponsor", sender);
            utils.setScriptVar(self, "guildSponsor.guildId", params.getInt("guildId"));
            int pid = sui.msgbox(self, self, params.getString("prompt"), sui.YES_NO, params.getString("title"), sui.MSG_NORMAL, "guildSponsorSuiDone");
            guild.setWindowPid(self, pid);
        }
        return SCRIPT_CONTINUE;
    }
    public int guildSponsorSuiDone(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id target = self;
        obj_id player = utils.getObjIdScriptVar(self, "guildSponsor.sponsor");
        if (!guild.hasWindowPid(self))
        {
            return SCRIPT_CONTINUE;
        }
        int guildId = utils.getIntScriptVar(self, "guildSponsor.guildId");
        int bp = sui.getIntButtonPressed(params);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        String name = getFirstName(target);
        dictionary dict = new dictionary();
        dict.put("player", name);
        dict.put("bpOk", (bp == sui.BP_OK));
        if (bp == sui.BP_OK)
        {
            messageTo(player, "onGuildSponsorVerifyResponseProse", dict, 0, false);
            guild.sponsor(guildId, player, name);
        }
        else 
        {
            messageTo(player, "onGuildSponsorVerifyResponseProse", dict, 0, false);
        }
        utils.removeScriptVarTree(self, "guildSponsor");
        guild.removeWindowPid(self);
        return SCRIPT_CONTINUE;
    }
    public int onGuildSponsorVerifyResponseProse(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        String target = params.getString("player");
        String title = params.getString("title");
        if (target == null || target.equals(""))
        {
            return SCRIPT_CONTINUE;
        }
        boolean bpOk = params.getBoolean("bpOk");
        string_id proseSID = params.getStringId("proseSID");
        int guildId = params.getInt("guildId");
        prose_package pp = new prose_package();
        if (proseSID != null)
        {
            pp.stringId = proseSID;
        }
        pp.actor.set(target);
        if (guildId != 0)
        {
            pp.target.set(guildGetName(guildId));
        }
        if (title != null && !title.equals(""))
        {
            pp.target.set(title);
        }
        if (bpOk)
        {
            pp.stringId = guild.SID_GUILD_SPONSOR_ACCEPT;
        }
        else 
        {
            if (proseSID == null)
            {
                pp.stringId = guild.SID_GUILD_SPONSOR_DECLINE;
            }
        }
        sendSystemMessageProse(self, pp);
        return SCRIPT_CONTINUE;
    }
    public int onGuildSponsorVerifyResponse(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id target = params.getObjId("player");
        obj_id player = params.getObjId("sponsor");
        string_id stringMessage = params.getStringId("stringMessage");
        sendSystemMessage(player, stringMessage);
        return SCRIPT_CONTINUE;
    }
    public int onGuildPermissionsResponse(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId("player");
        if (!guild.hasWindowPid(player))
        {
            return SCRIPT_CONTINUE;
        }
        String target = "";
        int guildId = 0;
        String[] permissions = utils.getStringArrayScriptVar(self, "guild.permissionsParams");
        int lastInterface = utils.getIntScriptVar(player, "guild.lastInterface");
        if (utils.hasScriptVar(player, "name"))
        {
            target = utils.getStringScriptVar(self, "name");
            utils.removeScriptVar(self, "name");
        }
        if (utils.hasScriptVar(player, "guildId"))
        {
            guildId = utils.getIntScriptVar(self, "guildId");
            utils.removeScriptVar(self, "guildId");
        }
        if (permissions != null && permissions.length > 0)
        {
            utils.removeScriptVar(self, "guild.permissionsParams");
        }
        obj_id memberId = guild.findMemberIdByName(guildId, target, false, true);
        int bp = sui.getIntButtonPressed(params);
        int row = sui.getListboxSelectedRow(params);
        if (bp == sui.BP_OK)
        {
            if (row >= 0 && row <= 10 && guildGetLeader(guildId) == player)
            {
                if (row == 0)
                {
                    guild.togglePermission(guildId, player, target, guild.GUILD_PERMISSION_MAIL);
                }
                else if (row == 1)
                {
                    guild.togglePermission(guildId, player, target, guild.GUILD_PERMISSION_SPONSOR);
                }
                else if (row == 2)
                {
                    guild.togglePermission(guildId, player, target, guild.GUILD_PERMISSION_TITLE);
                }
                else if (row == 3)
                {
                    guild.togglePermission(guildId, player, target, guild.GUILD_PERMISSION_ACCEPT);
                }
                else if (row == 4)
                {
                    guild.togglePermission(guildId, player, target, guild.GUILD_PERMISSION_KICK);
                }
                else if (row == 5)
                {
                    guild.togglePermission(guildId, player, target, guild.GUILD_PERMISSION_WAR);
                }
                else if (row == 6)
                {
                    guild.togglePermission(guildId, player, target, guild.GUILD_PERMISSION_NAMECHANGE);
                }
                else if (row == 7)
                {
                    guild.togglePermission(guildId, player, target, guild.GUILD_PERMISSION_DISBAND);
                }
                else if (row == 8)
                {
                    guild.togglePermission(guildId, player, target, guild.GUILD_PERMISSION_RANK);
                }
                else if (row == 9)
                {
                    guild.togglePermission(guildId, player, target, guild.GUILD_PERMISSION_WAR_EXCLUSION);
                }
                else if (row == 10)
                {
                    guild.togglePermission(guildId, player, target, guild.GUILD_PERMISSION_WAR_INCLUSION);
                }
                guild.selectPermissions(self, player, target, guildId);
                return SCRIPT_CONTINUE;
            }
            else 
            {
                if (row == 0)
                {
                    guild.togglePersonalPermission(guildId, player, target, guild.GUILD_PERMISSION_WAR_EXCLUSION);
                }
            }
        }
        else if (bp == sui.BP_REVERT)
        {
            if (lastInterface == guild.INTERFACE_GUILD_PERMISSION_LIST)
            {
                guild.showPermissionSummary(self, player);
            }
            else 
            {
                guild.showGuildMembers(self, self, 0, -1, "", "");
            }
        }
        else 
        {
            guild.removeWindowPid(player);
        }
        return SCRIPT_CONTINUE;
    }
    public int selectPermissionsMessage(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        String name = params.getString("name");
        int guildId = params.getInt("guildId");
        obj_id target = guild.findMemberIdByName(guildId, name, false, true);
        obj_id leader = guildGetLeader(guildId);
        String[] perms = null;
        if (leader == player || isGod(player))
        {
            perms = new String[11];
            if (guild.hasGuildPermission(guildId, target, guild.GUILD_PERMISSION_MAIL))
            {
                perms[0] = "+ " + localize(guild.SID_GUILD_PERMISSION_MAIL);
            }
            else 
            {
                perms[0] = "- " + localize(guild.SID_GUILD_PERMISSION_MAIL);
            }
            if (guild.hasGuildPermission(guildId, target, guild.GUILD_PERMISSION_SPONSOR))
            {
                perms[1] = "+ " + localize(guild.SID_GUILD_PERMISSION_SPONSOR);
            }
            else 
            {
                perms[1] = "- " + localize(guild.SID_GUILD_PERMISSION_SPONSOR);
            }
            if (guild.hasGuildPermission(guildId, target, guild.GUILD_PERMISSION_TITLE))
            {
                perms[2] = "+ " + localize(guild.SID_GUILD_PERMISSION_TITLE);
            }
            else 
            {
                perms[2] = "- " + localize(guild.SID_GUILD_PERMISSION_TITLE);
            }
            if (guild.hasGuildPermission(guildId, target, guild.GUILD_PERMISSION_ACCEPT))
            {
                perms[3] = "+ " + localize(guild.SID_GUILD_PERMISSION_ACCEPT);
            }
            else 
            {
                perms[3] = "- " + localize(guild.SID_GUILD_PERMISSION_ACCEPT);
            }
            if (guild.hasGuildPermission(guildId, target, guild.GUILD_PERMISSION_KICK))
            {
                perms[4] = "+ " + localize(guild.SID_GUILD_PERMISSION_KICK);
            }
            else 
            {
                perms[4] = "- " + localize(guild.SID_GUILD_PERMISSION_KICK);
            }
            if (guild.hasGuildPermission(guildId, target, guild.GUILD_PERMISSION_WAR))
            {
                perms[5] = "+ " + localize(guild.SID_GUILD_PERMISSION_WAR);
            }
            else 
            {
                perms[5] = "- " + localize(guild.SID_GUILD_PERMISSION_WAR);
            }
            if (guild.hasGuildPermission(guildId, target, guild.GUILD_PERMISSION_NAMECHANGE))
            {
                perms[6] = "+ " + localize(guild.SID_GUILD_PERMISSION_NAMECHANGE);
            }
            else 
            {
                perms[6] = "- " + localize(guild.SID_GUILD_PERMISSION_NAMECHANGE);
            }
            if (guild.hasGuildPermission(guildId, target, guild.GUILD_PERMISSION_DISBAND))
            {
                perms[7] = "+ " + localize(guild.SID_GUILD_PERMISSION_DISBAND);
            }
            else 
            {
                perms[7] = "- " + localize(guild.SID_GUILD_PERMISSION_DISBAND);
            }
            if (guild.hasGuildPermission(guildId, target, guild.GUILD_PERMISSION_RANK))
            {
                perms[8] = "+ " + localize(guild.SID_GUILD_PERMISSION_RANK);
            }
            else 
            {
                perms[8] = "- " + localize(guild.SID_GUILD_PERMISSION_RANK);
            }
            if (guild.hasGuildPermission(guildId, target, guild.GUILD_PERMISSION_WAR_EXCLUSION))
            {
                perms[9] = "+ " + localize(guild.SID_GUILD_PERMISSION_WAR_EXCLUDE);
            }
            else 
            {
                perms[9] = "- " + localize(guild.SID_GUILD_PERMISSION_WAR_EXCLUDE);
            }
            if (guild.hasGuildPermission(guildId, target, guild.GUILD_PERMISSION_WAR_INCLUSION))
            {
                perms[10] = "+ " + localize(guild.SID_GUILD_PERMISSION_WAR_INCLUDE);
            }
            else 
            {
                perms[10] = "- " + localize(guild.SID_GUILD_PERMISSION_WAR_INCLUDE);
            }
        }
        else 
        {
            if (player == target)
            {
                perms = new String[1];
                if (guild.hasGuildPermission(guildId, target, guild.GUILD_PERMISSION_WAR_EXCLUSION))
                {
                    perms[0] = "+ " + localize(guild.SID_GUILD_PERMISSION_WAR_EXCLUDE);
                }
                else 
                {
                    perms[0] = "- " + localize(guild.SID_GUILD_PERMISSION_WAR_EXCLUDE);
                }
            }
            else 
            {
                guild.showGuildMembers(self, self, 0, -1, "", "");
            }
        }
        dictionary dict = new dictionary();
        dict.put("player", player);
        dict.put("perms", perms);
        dict.put("name", name);
        dict.put("guildId", guildId);
        messageTo(self, "permissionsChangeSuiHandler", dict, 0, false);
        return SCRIPT_CONTINUE;
    }
    public int onGuildMembersResponse(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        if (!guild.hasWindowPid(player))
        {
            return SCRIPT_CONTINUE;
        }
        int guildId = getGuildId(player);
        obj_id[] memberIds = utils.getObjIdArrayScriptVar(self, "guild.memberIds");
        if (memberIds == null || memberIds.length < 1)
        {
            return SCRIPT_CONTINUE;
        }
        int index = sui.getTableLogicalIndex(params);
        if (index < 0 || index > memberIds.length)
        {
            return SCRIPT_CONTINUE;
        }
        utils.removeScriptVarTree(self, "guildShow");
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_OK)
        {
            String name = guildGetMemberName(guildId, memberIds[index]);
            utils.setScriptVar(self, "guildMemberName", name);
            utils.setScriptVar(self, "guildId", guildId);
            String[] entries = guild.getAvailableMemberOptions(self, player);
            obj_id target = guild.findMemberIdByName(guildId, name, false, true);
            if ((entries == null || entries.length <= 0) && player != target)
            {
                guild.removeWindowPid(player);
                utils.removeScriptVar(player, "guildMemberName");
                utils.removeScriptVar(player, "guildId");
                sendSystemMessage(player, SID_GUILD_NO_PERMISSION);
                return SCRIPT_CONTINUE;
            }
            else 
            {
                if ((entries == null || entries.length <= 0) && player == target)
                {
                    guild.selectPermissions(self, player, name, guildId);
                    return SCRIPT_CONTINUE;
                }
            }
            int pid = sui.listbox(self, player, guild.buildFakeLocalizedProse(guild.STR_GUILD_MEMBER_OPTIONS_PROMPT, name, ""), sui.OK_CANCEL_REFRESH, guild.STR_GUILD_MEMBER_OPTIONS_TITLE, entries, "onGuildMemberOptionsResponse", false, true);
            sui.listboxUseOtherButton(pid, guild.STR_SUI_BACK_BUTTON);
            utils.setScriptVar(player, "guild.lastInterface", guild.INTERFACE_GUILD_ROSTER);
            sui.showSUIPage(pid);
            guild.setWindowPid(player, pid);
        }
        else 
        {
            utils.removeScriptVarTree(self, "guildShow");
            guild.removeWindowPid(player);
        }
        return SCRIPT_CONTINUE;
    }
    public int onGuildMemberOptionsResponse(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        int guildId = utils.getIntScriptVar(self, "guildId");
        if (!guild.hasWindowPid(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (sui.getIntButtonPressed(params) == sui.BP_OK)
        {
            int row = sui.getListboxSelectedRow(params);
            String name = utils.getStringScriptVar(self, "guildMemberName");
            String[] entries = guild.getAvailableMemberOptions(self, player);
            if (entries == null || entries.length <= 0)
            {
                guild.removeWindowPid(player);
                sendSystemMessage(player, SID_GUILD_NO_PERMISSION);
                return SCRIPT_CONTINUE;
            }
            if (row >= 0 && row < entries.length)
            {
                if (entries[row].equals(guild.STR_GUILD_TITLE))
                {
                    guild.chooseTitle(self, player, name);
                    return SCRIPT_CONTINUE;
                }
                else if (entries[row].equals(guild.STR_GUILD_KICK))
                {
                    guild.confirmKick(self, player, name);
                    return SCRIPT_CONTINUE;
                }
                else if (entries[row].equals(guild.STR_GUILD_PERMISSIONS))
                {
                    guild.selectPermissions(self, player, name, guildId);
                    return SCRIPT_CONTINUE;
                }
                else if (entries[row].equals(guild.STR_GUILD_RANK))
                {
                    guild.selectRank(self, player, name, guildId);
                    return SCRIPT_CONTINUE;
                }
                else if (entries[row].equals(guild.STR_GUILD_WAR_EXCLUDE_TOGGLE))
                {
                    obj_id target = guild.findMemberIdByName(guildId, name, false, true);
                    if (!isIdValid(target))
                    {
                        return SCRIPT_CONTINUE;
                    }
                    params.put("warExclusion", guild.hasGuildPermission(guildId, target, guild.GUILD_PERMISSION_WAR_EXCLUSION));
                    params.put("player", player);
                    params.put("guildMemberName", name);
                    params.put("guildId", guildId);
                    params.put("counter", 0);
                    messageTo(self, "delayWarExcludeConsistencyCheck", params, 1, false);
                    guild.toggleWarExclusion(player, guildId, name);
                }
                else if (entries[row].equals(guild.STR_GUILD_WAR_INCLUDE_TOGGLE))
                {
                    obj_id target = guild.findMemberIdByName(guildId, name, false, true);
                    if (!isIdValid(target))
                    {
                        return SCRIPT_CONTINUE;
                    }
                    params.put("warInclusion", guild.hasGuildPermission(guildId, target, guild.GUILD_PERMISSION_WAR_INCLUSION));
                    params.put("player", player);
                    params.put("guildMemberName", name);
                    params.put("guildId", guildId);
                    params.put("counter", 0);
                    messageTo(self, "delayWarIncludeConsistencyCheck", params, 1, false);
                    guild.toggleWarInclusion(player, guildId, name);
                }
            }
        }
        else if (sui.getIntButtonPressed(params) == sui.BP_REVERT)
        {
            guild.showGuildMembers(self, self, 0, -1, "", "");
        }
        else 
        {
            utils.removeScriptVar(self, "guildMemberName");
            utils.removeScriptVar(self, "guildId");
            guild.removeWindowPid(player);
        }
        return SCRIPT_CONTINUE;
    }
    public int delayWarExcludeConsistencyCheck(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        String name = params.getString("guildMemberName");
        int guildId = params.getInt("guildId");
        int counter = params.getInt("counter");
        boolean warExclusion = params.getBoolean("warExclusion");
        if (!isIdValid(player) || name == null || name.length() < 1 || guildId == 0)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id memberId = guild.findMemberIdByName(guildId, name, false, true);
        if (!isIdValid(memberId))
        {
            return SCRIPT_CONTINUE;
        }
        if (guild.hasGuildPermission(guildId, memberId, guild.GUILD_PERMISSION_WAR_EXCLUSION) == warExclusion && counter < 10)
        {
            counter++;
            params.put("counter", counter);
            messageTo(self, "delayWarExcludeConsistencyCheck", params, 1, false);
            return SCRIPT_CONTINUE;
        }
        guild.showGuildMembers(self, self, 0, -1, "", "");
        return SCRIPT_CONTINUE;
    }
    public int delayWarIncludeConsistencyCheck(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        String name = params.getString("guildMemberName");
        int guildId = params.getInt("guildId");
        int counter = params.getInt("counter");
        boolean warInclusion = params.getBoolean("warInclusion");
        if (!isIdValid(player) || name == null || name.length() < 1 || guildId == 0)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id memberId = guild.findMemberIdByName(guildId, name, false, true);
        if (!isIdValid(memberId))
        {
            return SCRIPT_CONTINUE;
        }
        if (guild.hasGuildPermission(guildId, memberId, guild.GUILD_PERMISSION_WAR_INCLUSION) == warInclusion && counter < 10)
        {
            counter++;
            params.put("counter", counter);
            messageTo(self, "delayWarIncludeConsistencyCheck", params, 1, false);
            return SCRIPT_CONTINUE;
        }
        guild.showGuildMembers(self, self, 0, -1, "", "");
        return SCRIPT_CONTINUE;
    }
    public int onGuildRankSummaryResponse(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        int guildId = utils.getIntScriptVar(self, "guildId");
        if (!guild.hasWindowPid(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (sui.getIntButtonPressed(params) == sui.BP_OK)
        {
            int row = sui.getTableLogicalIndex(params);
            String[] entries = utils.getStringArrayScriptVar(self, "guild.allRanks");
            if (entries == null || entries.length <= 0)
            {
                guild.removeWindowPid(player);
                sendSystemMessage(player, SID_GUILD_NO_PERMISSION);
                return SCRIPT_CONTINUE;
            }
            int rankSelected = 0;
            boolean rankRemoved = true;
            int[] ranksPreferred = getIntArrayObjVar(player, "guild.ranksPreferred");
            Vector newRanksPreferred = new Vector();
            newRanksPreferred.setSize(0);
            int foundIndex = -1;
            if (row >= 0 && row < entries.length)
            {
                if (ranksPreferred != null && ranksPreferred.length > 0)
                {
                    boolean found = false;
                    for (int i = 0, j = ranksPreferred.length; i < j; i++)
                    {
                        if (ranksPreferred[i] == row)
                        {
                            found = true;
                            continue;
                        }
                        utils.addElement(newRanksPreferred, ranksPreferred[i]);
                    }
                    if (!found)
                    {
                        utils.addElement(newRanksPreferred, row);
                    }
                }
                else 
                {
                    utils.addElement(newRanksPreferred, row);
                }
                if (newRanksPreferred == null || newRanksPreferred.size() < 1)
                {
                    removeObjVar(player, "guild.ranksPreferred");
                }
                else 
                {
                    java.util.Collections.sort(newRanksPreferred);
                    setObjVar(player, "guild.ranksPreferred", newRanksPreferred);
                }
                guild.showRankSummary(self, player);
            }
            else 
            {
                guild.showRankList(self, player);
            }
        }
        else if (sui.getIntButtonPressed(params) == sui.BP_REVERT)
        {
            guild.showGuildMembers(self, self, 0, -1, "", "");
        }
        else 
        {
            utils.removeScriptVar(self, "guildMemberName");
            utils.removeScriptVar(self, "guildId");
            utils.removeScriptVar(self, "guild.allRanks");
            guild.removeWindowPid(player);
        }
        return SCRIPT_CONTINUE;
    }
    public int onGuildRankListResponse(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        int guildId = utils.getIntScriptVar(self, "guildId");
        obj_id[] members = utils.getObjIdArrayScriptVar(player, "guild.memberIds");
        if (!guild.hasWindowPid(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (sui.getIntButtonPressed(params) == sui.BP_OK)
        {
            int row = sui.getTableLogicalIndex(params);
            if (members == null || members.length <= 0)
            {
                guild.removeWindowPid(player);
                sendSystemMessage(player, SID_GUILD_NO_PERMISSION);
                return SCRIPT_CONTINUE;
            }
            if (row >= 0 && row < members.length)
            {
                String name = guildGetMemberName(guildId, members[row]);
                utils.setScriptVar(player, "guild.lastInterface", guild.INTERFACE_GUILD_RANK_LIST);
                if (guild.hasGuildPermission(guildId, player, guild.GUILD_PERMISSION_RANK))
                {
                    guild.selectRank(self, player, name, guildId);
                }
                else 
                {
                    guild.showRankList(self, player);
                }
            }
            else 
            {
                utils.removeScriptVar(self, "guildMemberName");
                utils.removeScriptVar(self, "guildId");
                utils.removeScriptVar(self, "guild.memberIds");
                guild.removeWindowPid(player);
            }
        }
        else if (sui.getIntButtonPressed(params) == sui.BP_REVERT)
        {
            guild.showGuildMembers(self, self, 0, -1, "", "");
        }
        else 
        {
            utils.removeScriptVar(self, "guildMemberName");
            utils.removeScriptVar(self, "guildId");
            utils.removeScriptVar(self, "guild.memberIds");
            guild.removeWindowPid(player);
        }
        return SCRIPT_CONTINUE;
    }
    public int onRankOptionsResponse(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        String name = utils.getStringScriptVar(self, "guildMemberName");
        int guildId = utils.getIntScriptVar(self, "guildId");
        if (!guild.hasWindowPid(self))
        {
            return SCRIPT_CONTINUE;
        }
        int lastInterface = utils.getIntScriptVar(player, "guild.lastInterface");
        if (sui.getIntButtonPressed(params) == sui.BP_OK)
        {
            int row = sui.getListboxSelectedRow(params);
            String[] entries = utils.getStringArrayScriptVar(self, "guild.allRanks");
            if (entries == null || entries.length <= 0)
            {
                guild.removeWindowPid(player);
                sendSystemMessage(player, SID_GUILD_NO_PERMISSION);
                return SCRIPT_CONTINUE;
            }
            obj_id memberId = guild.findMemberIdByName(guildId, name, false, true);
            int rankSelected = 0;
            boolean rankRemoved = true;
            if (row >= 0 && row < entries.length && guild.hasGuildPermission(guildId, player, guild.GUILD_PERMISSION_RANK))
            {
                if (guildHasMemberRank(guildId, memberId, entries[row]))
                {
                    guildRemoveMemberRank(guildId, memberId, entries[row]);
                    rankSelected = row;
                }
                else 
                {
                    guildAddMemberRank(guildId, memberId, entries[row]);
                    rankSelected = row;
                    rankRemoved = false;
                }
                params.put("player", player);
                params.put("guildMemberName", name);
                params.put("guildId", guildId);
                params.put("rankList", entries);
                params.put("rankSelected", rankSelected);
                params.put("rankRemoved", rankRemoved);
                params.put("counter", 0);
                messageTo(self, "delayRankConsistencyCheck", params, 1, false);
            }
            else 
            {
                if (lastInterface == guild.INTERFACE_GUILD_RANK_LIST)
                {
                    guild.showRankList(self, player);
                }
                else 
                {
                    guild.showGuildMembers(self, self, 0, -1, "", "");
                }
            }
        }
        else if (sui.getIntButtonPressed(params) == sui.BP_REVERT)
        {
            if (lastInterface == guild.INTERFACE_GUILD_RANK_LIST)
            {
                guild.showRankList(self, player);
            }
            else 
            {
                guild.showGuildMembers(self, self, 0, -1, "", "");
            }
        }
        else 
        {
            utils.removeScriptVar(self, "guildMemberName");
            utils.removeScriptVar(self, "guildId");
            utils.removeScriptVar(self, "guild.allRanks");
            guild.removeWindowPid(player);
        }
        return SCRIPT_CONTINUE;
    }
    public int delayRankConsistencyCheck(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        String name = params.getString("guildMemberName");
        int guildId = params.getInt("guildId");
        int rankSelected = params.getInt("rankSelected");
        boolean rankRemoved = params.getBoolean("rankRemoved");
        String[] entries = params.getStringArray("rankList");
        int counter = params.getInt("counter");
        if (!isIdValid(player) || name == null || name.length() < 1 || guildId == 0)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id memberId = guild.findMemberIdByName(guildId, name, false, true);
        if (!isIdValid(memberId))
        {
            return SCRIPT_CONTINUE;
        }
        if ((rankRemoved && guildHasMemberRank(guildId, memberId, entries[rankSelected])) || (!rankRemoved && !guildHasMemberRank(guildId, memberId, entries[rankSelected])) && counter < 10)
        {
            counter++;
            params.put("counter", counter);
            messageTo(self, "delayRankConsistencyCheck", params, 1, false);
            return SCRIPT_CONTINUE;
        }
        guild.selectRank(self, player, name, guildId);
        return SCRIPT_CONTINUE;
    }
    public int selectRankMessage(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        String name = params.getString("guildMemberName");
        int guildId = params.getInt("guildId");
        String[] rankDisplay = params.getStringArray("rankDisplay");
        if (guild.hasWindowPid(player))
        {
            int pid = guild.getWindowPid(player);
            forceCloseSUIPage(pid);
            guild.removeWindowPid(player);
        }
        utils.setScriptVar(self, "guildMemberName", name);
        utils.setScriptVar(self, "guildId", guildId);
        int pid = sui.listbox(self, player, guild.buildFakeLocalizedProse(guild.STR_GUILD_MEMBER_RANK_PROMPT, name, ""), sui.OK_CANCEL_REFRESH, guild.STR_GUILD_MEMBER_RANK_TITLE, rankDisplay, "onRankOptionsResponse", false, false);
        sui.listboxUseOtherButton(pid, guild.STR_SUI_BACK_BUTTON);
        sui.showSUIPage(pid);
        guild.setWindowPid(player, pid);
        return SCRIPT_CONTINUE;
    }
    public int onGuildPermissionListResponse(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        int guildId = utils.getIntScriptVar(self, "guildId");
        obj_id[] members = utils.getObjIdArrayScriptVar(player, "guild.memberIds");
        if (!guild.hasWindowPid(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (sui.getIntButtonPressed(params) == sui.BP_OK)
        {
            int row = sui.getTableLogicalIndex(params);
            if (members == null || members.length <= 0)
            {
                guild.removeWindowPid(player);
                sendSystemMessage(player, SID_GUILD_NO_PERMISSION);
                return SCRIPT_CONTINUE;
            }
            if (row >= 0 && row < members.length)
            {
                String name = guildGetMemberName(guildId, members[row]);
                obj_id memberId = guild.findMemberIdByName(guildId, name, false, true);
                obj_id leader = guildGetLeader(guildId);
                utils.setScriptVar(player, "guild.lastInterface", guild.INTERFACE_GUILD_PERMISSION_LIST);
                if (player == leader || memberId == player)
                {
                    guild.selectPermissions(self, player, name, guildId);
                }
                else 
                {
                    guild.showPermissionSummary(self, player);
                }
            }
            else 
            {
                utils.removeScriptVar(self, "guildMemberName");
                utils.removeScriptVar(self, "guildId");
                utils.removeScriptVar(self, "guild.memberIds");
                guild.removeWindowPid(player);
            }
        }
        else if (sui.getIntButtonPressed(params) == sui.BP_REVERT)
        {
            guild.showPermissionSummary(self, player);
        }
        else 
        {
            utils.removeScriptVar(self, "guildMemberName");
            utils.removeScriptVar(self, "guildId");
            utils.removeScriptVar(self, "guild.memberIds");
            guild.removeWindowPid(player);
        }
        return SCRIPT_CONTINUE;
    }
    public int onGuildVotingResponse(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        int guildId = utils.getIntScriptVar(self, "guildId");
        if (!guild.hasWindowPid(self) || guildId == 0)
        {
            return SCRIPT_CONTINUE;
        }
        if (sui.getIntButtonPressed(params) == sui.BP_OK)
        {
            int row = sui.getTableLogicalIndex(params);
            obj_id[] candidates = utils.getObjIdArrayScriptVar(self, "guild.candidates");
            if (candidates == null || candidates.length <= 0)
            {
                guild.removeWindowPid(player);
                sendSystemMessage(player, SID_GUILD_NO_PERMISSION);
                return SCRIPT_CONTINUE;
            }
            if (row >= 0 && row < candidates.length)
            {
                String memberName = guildGetMemberName(guildId, candidates[row]);
                if (memberName != null)
                {
                    guild.allegiance(self, memberName);
                    params.put("player", player);
                    params.put("guildId", guildId);
                    params.put("memberVotedFor", memberName);
                    params.put("counter", 0);
                    messageTo(self, "votingConsistencyCheck", params, 1, false);
                }
            }
        }
        else 
        {
            utils.removeScriptVar(self, "guildMemberName");
            utils.removeScriptVar(self, "guildId");
            guild.removeWindowPid(player);
        }
        return SCRIPT_CONTINUE;
    }
    public int votingConsistencyCheck(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        int guildId = params.getInt("guildId");
        String memberVotedFor = params.getString("memberVotedFor");
        int counter = params.getInt("counter");
        if (!isIdValid(player) || memberVotedFor == null || memberVotedFor.length() < 1 || guildId == 0)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id memberId = guild.findMemberIdByName(guildId, memberVotedFor, false, true);
        if (memberId != guildGetMemberAllegiance(guildId, player) && counter < 10)
        {
            counter++;
            params.put("counter", counter);
            messageTo(self, "votingConsistencyCheck", params, 1, false);
            return SCRIPT_CONTINUE;
        }
        guild.showStandings(player, player);
        return SCRIPT_CONTINUE;
    }
    public int permissionsChangeSuiHandler(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId("player");
        String[] perms = params.getStringArray("perms");
        String name = params.getString("name");
        int guildId = params.getInt("guildId");
        utils.setScriptVar(self, "name", name);
        utils.setScriptVar(self, "guildId", guildId);
        utils.setScriptVar(self, "guild.permissionsParams", perms);
        int pid = sui.listbox(self, player, guild.buildFakeLocalizedProse(guild.STR_GUILD_PERMISSIONS_PROMPT, "\\#00FF00" + name + "\\#DFDFDF", ""), sui.OK_CANCEL_REFRESH, "\\#00FF00" + name + "\\#DFDFDF " + localize(guild.SID_GUILD_PERMISSIONS_TITLE), perms, "onGuildPermissionsResponse", false, true);
        sui.listboxUseOtherButton(pid, guild.STR_SUI_BACK_BUTTON);
        sui.showSUIPage(pid);
        guild.setWindowPid(player, pid);
        return SCRIPT_CONTINUE;
    }
    public int onGuildKickResponse(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        String name = "";
        if (!guild.hasWindowPid(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(player, "guildKickName"))
        {
            name = utils.getStringScriptVar(self, "guildKickName");
            utils.removeScriptVar(self, "guildKickName");
        }
        int guildId = getGuildId(player);
        if (sui.getIntButtonPressed(params) == sui.BP_OK)
        {
            guild.kick(guildId, player, name);
        }
        guild.removeWindowPid(player);
        return SCRIPT_CONTINUE;
    }
    public int onGuildTitleResponse(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        String name = guild.getMenuContextString(self, player, "guildTitlePlayerName");
        guild.removeMenuContextVar(self, player, "guildTitlePlayerName");
        if (!guild.hasWindowPid(player))
        {
            return SCRIPT_CONTINUE;
        }
        int guildId = getGuildId(player);
        if (sui.getIntButtonPressed(params) == sui.BP_OK)
        {
            String newTitle = sui.getInputBoxText(params);
            guild.title(guildId, player, name, newTitle);
        }
        else 
        {
            guild.removeWindowPid(player);
        }
        int pid = sui.listbox(self, player, guild.buildFakeLocalizedProse(guild.STR_GUILD_MEMBER_OPTIONS_PROMPT, name, ""), sui.OK_CANCEL_REFRESH, guild.STR_GUILD_MEMBER_OPTIONS_TITLE, guild.getAvailableMemberOptions(self, player), "onGuildMemberOptionsResponse", false, true);
        sui.listboxUseOtherButton(pid, guild.STR_SUI_BACK_BUTTON);
        sui.showSUIPage(pid);
        guild.setWindowPid(player, pid);
        return SCRIPT_CONTINUE;
    }
    public int onGuildMembersFilter(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId("player");
        if (!guild.hasWindowPid(player))
        {
            return SCRIPT_CONTINUE;
        }
        int btn = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        if (idx < 0)
        {
            guild.removeWindowPid(self);
            return SCRIPT_CONTINUE;
        }
        int guildId = getGuildId(player);
        if (btn == sui.BP_OK)
        {
            switch (idx)
            {
                case 0:
                guild.showGuildMembers(player, player, 0, -1, "", "");
                break;
                case 1:
                guild.showPermissionList(player, guildId);
                break;
                case 2:
                guild.showTitleList(player, guildId);
                break;
                case 3:
                int pid = sui.inputbox(player, player, guild.STR_GUILD_MEMBERS_NAME_PROMPT, sui.OK_CANCEL, guild.STR_GUILD_MEMBERS_TITLE, sui.INPUT_NORMAL, null, "onGuildMemberNameResponse");
                guild.setWindowPid(player, pid);
                break;
            }
        }
        else 
        {
            guild.removeWindowPid(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int onGuildMembersPermissionsResponse(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId("player");
        int btn = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        if (!guild.hasWindowPid(player))
        {
            return SCRIPT_CONTINUE;
        }
        int guildId = utils.getIntScriptVar(player, "guildShow.guildId");
        if (btn == sui.BP_OK)
        {
            if (idx < 0)
            {
                guild.removeWindowPid(player);
                return SCRIPT_CONTINUE;
            }
            switch (idx)
            {
                case 0:
                guild.showGuildMembers(player, player, 0, guild.GUILD_PERMISSION_MAIL, "", "");
                break;
                case 1:
                guild.showGuildMembers(player, player, 0, guild.GUILD_PERMISSION_SPONSOR, "", "");
                break;
                case 2:
                guild.showGuildMembers(player, player, 0, guild.GUILD_PERMISSION_TITLE, "", "");
                break;
                case 3:
                guild.showGuildMembers(player, player, 0, guild.GUILD_PERMISSION_ACCEPT, "", "");
                break;
                case 4:
                guild.showGuildMembers(player, player, 0, guild.GUILD_PERMISSION_KICK, "", "");
                break;
                case 5:
                guild.showGuildMembers(player, player, 0, guild.GUILD_PERMISSION_WAR, "", "");
                break;
                case 6:
                guild.showGuildMembers(player, player, 0, guild.GUILD_PERMISSION_NAMECHANGE, "", "");
                break;
                case 7:
                guild.showGuildMembers(player, player, 0, guild.GUILD_PERMISSION_DISBAND, "", "");
                break;
                case 8:
                guild.showGuildMembers(player, player, 0, 0, "", "");
            }
        }
        else if (btn == sui.BP_REVERT)
        {
            int pid = sui.listbox(self, self, guild.STR_GUILD_MEMBERS_FILTER_PROMPT, sui.OK_CANCEL, guild.STR_GUILD_MEMBERS_TITLE, guild.MEMBER_FILTER_LIST, "onGuildMembersFilter", true, true);
            guild.setWindowPid(player, pid);
        }
        else 
        {
            guild.removeWindowPid(player);
        }
        return SCRIPT_CONTINUE;
    }
    public int onGuildMembersTitlesResponse(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId("player");
        if (!guild.hasWindowPid(player))
        {
            return SCRIPT_CONTINUE;
        }
        int btn = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        int guildId = utils.getIntScriptVar(player, "guildShow.guildId");
        String[] myList = guild.getGuildTitles(guildId);
        if (myList == null)
        {
            return SCRIPT_CONTINUE;
        }
        if (btn == sui.BP_OK)
        {
            if (idx < 0)
            {
                guild.removeWindowPid(player);
                return SCRIPT_CONTINUE;
            }
            guild.showGuildMembers(player, player, 0, -1, myList[idx], "");
        }
        else if (btn == sui.BP_REVERT)
        {
            int pid = sui.listbox(self, self, guild.STR_GUILD_MEMBERS_FILTER_PROMPT, sui.OK_CANCEL, guild.STR_GUILD_MEMBERS_TITLE, guild.MEMBER_FILTER_LIST, "onGuildMembersFilter", true, true);
            guild.setWindowPid(player, pid);
        }
        return SCRIPT_CONTINUE;
    }
    public int onGuildMemberNameResponse(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId("player");
        if (!guild.hasWindowPid(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (sui.getIntButtonPressed(params) == sui.BP_OK)
        {
            String name = sui.getInputBoxText(params);
            guild.showGuildMembers(player, player, 0, -1, "", name);
        }
        else 
        {
            guild.removeWindowPid(player);
        }
        return SCRIPT_CONTINUE;
    }
    public int onGuildEnemiesResponse(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        if (!guild.hasWindowPid(player))
        {
            return SCRIPT_CONTINUE;
        }
        int[] enemyIds = utils.getIntArrayScriptVar(player, "guildEnemyIds");
        utils.removeScriptVar(player, "guildEnemyIds");
        if (sui.getIntButtonPressed(params) == sui.BP_OK)
        {
            int guildId = getGuildId(player);
            if (guild.hasGuildPermission(guildId, player, guild.GUILD_PERMISSION_WAR))
            {
                int row = sui.getListboxSelectedRow(params);
                if (row == 0)
                {
                    int pid = sui.inputbox(self, player, guild.STR_GUILD_WAR_ENEMY_NAME_PROMPT, sui.OK_CANCEL, guild.STR_GUILD_WAR_ENEMY_NAME_TITLE, sui.INPUT_NORMAL, null, "onGuildWarEnemyNameResponse");
                    guild.setWindowPid(player, pid);
                }
                else 
                {
                    if (row > 0 && row <= enemyIds.length)
                    {
                        int enemyId = enemyIds[row - 1];
                        if (guild.hasDeclaredWarAgainst(guildId, enemyId))
                        {
                            guild.peace(guildId, player, enemyId);
                        }
                        else 
                        {
                            guild.war(guildId, player, enemyId);
                        }
                        guild.showGuildEnemies(player);
                    }
                }
            }
        }
        else 
        {
            guild.removeWindowPid(player);
        }
        return SCRIPT_CONTINUE;
    }
    public int onGuildWarEnemyNameResponse(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        if (!guild.hasWindowPid(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (sui.getIntButtonPressed(params) == sui.BP_OK)
        {
            int guildId = getGuildId(player);
            String enemyName = sui.getInputBoxText(params);
            guild.war(guildId, player, enemyName);
        }
        guild.removeWindowPid(player);
        guild.showGuildEnemies(player);
        return SCRIPT_CONTINUE;
    }
    public int showGuildEnemiesMessage(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId("player");
        if (guild.hasWindowPid(player))
        {
            int pid = guild.getWindowPid(player);
            forceCloseSUIPage(pid);
            guild.removeWindowPid(player);
        }
        int guildId = getGuildId(player);
        int[] enemyIds = guild.getEnemyIds(guildId);
        utils.setScriptVar(player, "guildEnemyIds", enemyIds);
        String[] enemies = guild.getEnemyNamesAndAbbrevs(guildId);
        int numEnemies = 0;
        if (enemies != null)
        {
            numEnemies = enemies.length;
        }
        boolean warPerms = guild.hasGuildPermission(guildId, player, guild.GUILD_PERMISSION_WAR);
        int numOptions = numEnemies;
        if (warPerms)
        {
            ++numOptions;
        }
        String[] options = new String[numOptions];
        int index = 0;
        if (warPerms)
        {
            options[index++] = guild.STR_GUILD_ADD_ENEMY;
        }
        for (int i = 0; i < numEnemies; ++i)
        {
            options[index++] = enemies[i];
        }
        String prompt = "Your guild currently has " + guildGetCountMembersGuildWarPvPEnabled(guildId) + " members who are enabled for guild war PvP.\n\n";
        prompt += localize(guild.GUILD_ENEMIES_PROMPT);
        int pid = sui.listbox(player, player, prompt, sui.OK_CANCEL, guild.STR_GUILD_ENEMIES_TITLE, options, "onGuildEnemiesResponse", true, false);
        guild.setWindowPid(player, pid);
        return SCRIPT_CONTINUE;
    }
    public int showGuildSponsoredMessage(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        if (guild.hasWindowPid(player))
        {
            int pid = guild.getWindowPid(player);
            forceCloseSUIPage(pid);
            guild.removeWindowPid(player);
        }
        int guildId = getGuildId(player);
        String[] sponsoredNames = guild.getMemberNames(guildId, true, false);
        int pid = sui.listbox(player, player, guild.STR_GUILD_SPONSORED_PROMPT, sui.OK_CANCEL, guild.STR_GUILD_SPONSORED_TITLE, sponsoredNames, "onGuildSponsoredResponse", true, false);
        utils.setScriptVar(player, "guild.sponsoredNames", sponsoredNames);
        guild.setWindowPid(player, pid);
        return SCRIPT_CONTINUE;
    }
    public int onGuildSponsoredResponse(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        if (!guild.hasWindowPid(player))
        {
            utils.removeScriptVar(player, "guild.sponsoredNames");
            return SCRIPT_CONTINUE;
        }
        int guildId = getGuildId(player);
        String[] sponsoredNames = utils.getStringArrayScriptVar(player, "guild.sponsoredNames");
        utils.removeScriptVar(player, "guild.sponsoredNames");
        if (sponsoredNames == null || sponsoredNames.length < 1)
        {
            return SCRIPT_CONTINUE;
        }
        if (sui.getIntButtonPressed(params) == sui.BP_OK)
        {
            int row = sui.getListboxSelectedRow(params);
            if (row >= 0 && row < sponsoredNames.length)
            {
                String[] entries = new String[2];
                entries[0] = guild.STR_GUILD_ACCEPT;
                entries[1] = guild.STR_GUILD_DECLINE;
                String name = sponsoredNames[row];
                guild.setMenuContextString(self, player, "guildSponsoredName", name);
                int pid = sui.listbox(player, player, guild.buildFakeLocalizedProse(guild.STR_GUILD_SPONSORED_OPTIONS_PROMPT, name, ""), sui.OK_CANCEL, guild.STR_GUILD_SPONSORED_OPTIONS_TITLE, entries, "onGuildSponsoredOptionsResponse");
                guild.setWindowPid(player, pid);
            }
            else 
            {
                guild.removeWindowPid(player);
            }
        }
        else 
        {
            guild.removeWindowPid(player);
        }
        return SCRIPT_CONTINUE;
    }
    public int onGuildSponsoredOptionsResponse(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        if (!guild.hasWindowPid(player))
        {
            return SCRIPT_CONTINUE;
        }
        String name = guild.getMenuContextString(self, player, "guildSponsoredName");
        guild.removeMenuContextVar(self, player, "guildSponsoredName");
        if (name == null || !isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (sui.getIntButtonPressed(params) == sui.BP_OK)
        {
            int row = sui.getListboxSelectedRow(params);
            if (row >= 0 && row <= 1)
            {
                int guildId = getGuildId(player);
                if (row == 0)
                {
                    guild.accept(guildId, player, name);
                }
                else if (row == 1)
                {
                    guild.kick(guildId, player, name);
                }
                guild.removeWindowPid(player);
                guild.showGuildSponsored(player);
            }
            else 
            {
                guild.removeWindowPid(player);
            }
        }
        else 
        {
            guild.removeWindowPid(player);
        }
        return SCRIPT_CONTINUE;
    }
    public int onGuildNamechangeNameResponse(obj_id self, dictionary params) throws InterruptedException
    {
        if (!guild.hasWindowPid(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId("player");
        if (sui.getIntButtonPressed(params) == sui.BP_OK)
        {
            String newGuildName = sui.getInputBoxText(params);
            guild.setMenuContextString(player, player, "guildNamechangeName", newGuildName);
            int pid = sui.inputbox(player, player, guild.STR_GUILD_NAMECHANGE_ABBREV_PROMPT, sui.OK_CANCEL, guild.STR_GUILD_NAMECHANGE_ABBREV_TITLE, sui.INPUT_NORMAL, null, "onGuildNamechangeAbbrevResponse");
            guild.setWindowPid(self, pid);
        }
        else 
        {
            guild.removeWindowPid(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int onGuildNamechangeAbbrevResponse(obj_id self, dictionary params) throws InterruptedException
    {
        if (!guild.hasWindowPid(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId("player");
        String newGuildName = guild.getMenuContextString(self, player, "guildNamechangeName");
        guild.removeMenuContextVar(player, player, "guildNamechangeName");
        if (sui.getIntButtonPressed(params) == sui.BP_OK)
        {
            int guildId = getGuildId(player);
            String newGuildAbbrev = sui.getInputBoxText(params);
            guild.nameChange(guildId, player, newGuildName, newGuildAbbrev);
        }
        guild.removeWindowPid(self);
        return SCRIPT_CONTINUE;
    }
    public int onGuildSponsorResponse(obj_id self, dictionary params) throws InterruptedException
    {
        if (!guild.hasWindowPid(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId("player");
        if (sui.getIntButtonPressed(params) == sui.BP_OK)
        {
            String sponsorName = sui.getInputBoxText(params);
            obj_id target = utils.getNearbyPlayerByName(player, sponsorName);
            if (!isIdValid(target))
            {
                target = getPlayerIdFromFirstName(sponsorName);
            }
            if (isIdValid(target))
            {
                int guildId = getGuildId(player);
                dictionary d = new dictionary();
                d.put("sender", player);
                d.put("guildId", guildId);
                d.put("prompt", guild.buildFakeLocalizedProse(guild.STR_GUILD_SPONSOR_VERIFY_PROMPT, getName(player), guildGetName(guildId)));
                d.put("title", guild.STR_GUILD_SPONSOR_VERIFY_TITLE);
                messageTo(target, "startGuildSponsorSui", d, 0, false);
            }
            else 
            {
                prose_package pp = new prose_package();
                pp.actor.set(sponsorName);
                pp.stringId = guild.SID_GUILD_SPONSOR_NOT_FOUND;
                sendSystemMessageProse(player, pp);
            }
        }
        guild.removeWindowPid(self);
        return SCRIPT_CONTINUE;
    }
    public int onGuildDisbandResponse(obj_id self, dictionary params) throws InterruptedException
    {
        if (!guild.hasWindowPid(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId("player");
        int guildId = getGuildId(player);
        if (sui.getIntButtonPressed(params) == sui.BP_OK)
        {
            guild.disband(guildId, player);
        }
        guild.removeWindowPid(self);
        return SCRIPT_CONTINUE;
    }
    public int onMasterGuildWarTableDictionaryResponse(obj_id self, dictionary params) throws InterruptedException
    {
        guild.closedMasterGuildWarTableDictionary(self);
        return SCRIPT_CONTINUE;
    }
    public int onInactiveGuildWarTableDictionaryResponse(obj_id self, dictionary params) throws InterruptedException
    {
        guild.closedInactiveGuildWarTableDictionary(self);
        return SCRIPT_CONTINUE;
    }
    public int handleStatusUpdate(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        boolean login = params.getBoolean("login");
        int guildId = getGuildId(self);
        if (guildId > 0)
        {
            guild.statusNotification(guildId, self, login);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleStatusNotification(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        int guildId = params.getInt("guildId");
        obj_id player = params.getObjId("player");
        boolean login = params.getBoolean("login");
        if (guildId <= 0 || !isIdValid(self) || !isIdValid(player) || player == self)
        {
            return SCRIPT_CONTINUE;
        }
        if (!guild.hasGuildPermission(guildId, self, guild.GUILD_PERMISSION_ONLINE_STATUS))
        {
            return SCRIPT_CONTINUE;
        }
        String name = guildGetMemberName(guildId, player);
        prose_package pp = new prose_package();
        pp.target.set(guildGetMemberName(guildId, player));
        if (login)
        {
            pp.stringId = SID_GUILD_MEMBER_LOGIN;
            obj_id[] clients = 
            {
                self
            };
            playClientEffectObj(clients, "sound/wep_landmine_on.snd", self, "");
        }
        else 
        {
            pp.stringId = SID_GUILD_MEMBER_LOGOFF;
            obj_id[] clients = 
            {
                self
            };
            playClientEffectObj(clients, "sound/wep_landmine_off.snd", self, "");
        }
        sendSystemMessageProse(self, pp);
        return SCRIPT_CONTINUE;
    }
    public int handleGuildGcwRegionDefenderChoice(obj_id self, dictionary params) throws InterruptedException
    {
        final int bp = sui.getIntButtonPressed(params);
        if (bp != sui.BP_OK)
        {
            return SCRIPT_CONTINUE;
        }
        String[] gcwDefenderRegions = getGcwDefenderRegions();
        if ((gcwDefenderRegions == null) || (gcwDefenderRegions.length <= 0))
        {
            return SCRIPT_CONTINUE;
        }
        int rowSelected = sui.getTableSelectedRow(params);
        if ((rowSelected < 0) || (rowSelected >= gcwDefenderRegions.length))
        {
            return SCRIPT_CONTINUE;
        }
        rowSelected = sui.getTableLogicalIndex(params);
        if ((rowSelected < 0) || (rowSelected >= gcwDefenderRegions.length))
        {
            return SCRIPT_CONTINUE;
        }
        final int guildId = getGuildId(self);
        if (guildId <= 0)
        {
            return SCRIPT_CONTINUE;
        }
        final obj_id guildLeader = guildGetLeader(guildId);
        if (!isIdValid(guildLeader))
        {
            return SCRIPT_CONTINUE;
        }
        if ((guildLeader != self) && !isGod(self))
        {
            return SCRIPT_CONTINUE;
        }
        final int factionId = guildGetCurrentFaction(guildId);
        if (factionId == 0)
        {
            return SCRIPT_CONTINUE;
        }
        if (guildGetCountMembersOnly(guildId) < utils.stringToInt(getConfigSetting("GameServer", "gcwGuildMinMembersForGcwRegionDefender")))
        {
            return SCRIPT_CONTINUE;
        }
        String selectedGcwDefenderRegion = gcwDefenderRegions[rowSelected];
        final int indexSeparator = selectedGcwDefenderRegion.indexOf(":");
        if ((indexSeparator < 0) || ((indexSeparator + 1) >= selectedGcwDefenderRegion.length()))
        {
            return SCRIPT_CONTINUE;
        }
        selectedGcwDefenderRegion = selectedGcwDefenderRegion.substring(indexSeparator + 1);
        final String gcwCurrentDefenderRegion = guildGetCurrentGcwDefenderRegion(guildId);
        if ((gcwCurrentDefenderRegion != null) && (gcwCurrentDefenderRegion.length() > 0))
        {
            return SCRIPT_CONTINUE;
        }
        final String gcwPreviousDefenderRegion = guildGetPreviousGcwDefenderRegion(guildId);
        final int timeLeftPreviousGcwDefenderRegion = guildGetTimeLeftPreviousGcwDefenderRegion(guildId);
        if ((gcwPreviousDefenderRegion != null) && (gcwPreviousDefenderRegion.length() > 0) && (timeLeftPreviousGcwDefenderRegion > 0))
        {
            final int cooldown = timeLeftPreviousGcwDefenderRegion + (isGod(self) ? 10 : 86400) - getCalendarTime();
            if ((cooldown > 0) && !selectedGcwDefenderRegion.equals(gcwPreviousDefenderRegion))
            {
                String cooldownStr = "" + cooldown + "s";
                int[] convertedTime = player_structure.convertSecondsTime(cooldown);
                if ((convertedTime != null) && (convertedTime.length == 4))
                {
                    if (convertedTime[0] > 0)
                    {
                        cooldownStr = "" + convertedTime[0] + "d:" + convertedTime[1] + "h:" + convertedTime[2] + "m:" + convertedTime[3] + "s";
                    }
                    else if (convertedTime[1] > 0)
                    {
                        cooldownStr = "" + convertedTime[1] + "h:" + convertedTime[2] + "m:" + convertedTime[3] + "s";
                    }
                    else if (convertedTime[2] > 0)
                    {
                        cooldownStr = "" + convertedTime[2] + "m:" + convertedTime[3] + "s";
                    }
                    else if (convertedTime[3] > 0)
                    {
                        cooldownStr = "" + convertedTime[3] + "s";
                    }
                    else 
                    {
                        cooldownStr = "" + cooldown + "s";
                    }
                }
                sendSystemMessage(self, "You must wait " + cooldownStr + " before you can defend a different GCW region. You can immediately defend the GCW region you most recently defended (" + localize(new string_id("gcw_regions", gcwPreviousDefenderRegion)) + ").", "");
                return SCRIPT_CONTINUE;
            }
        }
        sendSystemMessage(self, "Setting the guild's GCW defender region to " + localize(new string_id("gcw_regions", selectedGcwDefenderRegion)) + ". This may take a few seconds. You will receive mail confirmation once the change has been completed.", "");
        guildSetGcwDefenderRegion(guildId, selectedGcwDefenderRegion);
        return SCRIPT_CONTINUE;
    }
}
