package script.terminal;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.guild;
import script.library.player_structure;
import script.library.sui;
import script.library.utils;
import script.library.prose;

public class terminal_guild extends script.terminal.base.base_terminal
{
    public terminal_guild()
    {
    }
    public static final string_id SID_GUILDS_LIST = new string_id("guild", "menu_list_of_guilds");
    public static final string_id SID_GUILD_WAR_LIST = new string_id("guild", "menu_list_of_guild_wars");
    public static final string_id SID_GUILD_WAR_LIST_ACTIVE = new string_id("guild", "menu_list_of_guild_wars_active");
    public static final string_id SID_GUILD_WAR_LIST_INACTIVE = new string_id("guild", "menu_list_of_guild_wars_inactive");
    public static final string_id SID_GUILD_GUILD_MANAGEMENT = new string_id("guild", "menu_guild_management");
    public static final string_id SID_GUILD_MEMBER_MANAGEMENT = new string_id("guild", "menu_member_management");
    public static final string_id SID_GUILD_CREATE = new string_id("guild", "menu_create");
    public static final string_id SID_GUILD_INFO = new string_id("guild", "menu_info");
    public static final string_id SID_GUILD_MEMBERS = new string_id("guild", "menu_members");
    public static final string_id SID_GUILD_SPONSORED = new string_id("guild", "menu_sponsored");
    public static final string_id SID_GUILD_ENEMIES = new string_id("guild", "menu_enemies");
    public static final string_id SID_GUILD_RANK_LIST = new string_id("guild", "menu_rank_list");
    public static final string_id SID_GUILD_RANK_SUMMARY = new string_id("guild", "menu_rank_summary");
    public static final string_id SID_GUILD_PERMISSION_LIST = new string_id("guild", "menu_permission_list");
    public static final string_id SID_GUILD_SPONSOR = new string_id("guild", "menu_sponsor");
    public static final string_id SID_GUILD_DISBAND = new string_id("guild", "menu_disband");
    public static final string_id SID_GUILD_NAMECHANGE = new string_id("guild", "menu_namechange");
    public static final string_id SID_GUILD_SPONSOR_ALREADY_IN_GUILD = new string_id("guild", "sponsor_already_in_guild");
    public static final String STR_GUILD_ALLEGIENCE = "@guild:allegiance";
    public static final String STR_GUILD_CREATE_NAME_PROMPT = "@guild:create_name_prompt";
    public static final String STR_GUILD_CREATE_NAME_TITLE = "@guild:create_name_title";
    public static final String STR_GUILD_CREATE_ABBREV_PROMPT = "@guild:create_abbrev_prompt";
    public static final String STR_GUILD_CREATE_ABBREV_TITLE = "@guild:create_abbrev_title";
    public static final string_id SID_GUILD_LEADER_CHANGE = new string_id("guild", "menu_leader_change");
    public static final string_id SID_GUILD_ENABLE_ELECTIONS = new string_id("guild", "menu_enable_elections");
    public static final string_id SID_GUILD_DISABLE_ELECTIONS = new string_id("guild", "menu_disable_elections");
    public static final string_id SID_LEADER_RACE = new string_id("guild", "menu_leader_race");
    public static final string_id SID_LEADER_STANDINGS = new string_id("guild", "menu_leader_standings");
    public static final string_id SID_LEADER_VOTE = new string_id("guild", "menu_leader_vote");
    public static final string_id SID_LEADER_REGISTER = new string_id("guild", "menu_leader_register");
    public static final string_id SID_LEADER_UNREGISTER = new string_id("guild", "menu_leader_unregister");
    public static final string_id SID_QA_RESET_VOTING = new string_id("guild", "menu_leader_qa_reset_vote");
    public static final string_id SID_QA_START_ELECTION = new string_id("guild", "menu_leader_qa_start_election");
    public static final string_id SID_QA_END_ELECTION = new string_id("guild", "menu_leader_qa_end_election");
    public static final string_id SID_QA_EXPIRE_VOTING = new string_id("guild", "menu_leader_qa_expire_voting");
    public static final string_id SID_QA_EXPIRE_GRACE_PERIOD = new string_id("guild", "menu_leader_qa_expire_grace");
    public static final string_id SID_VOTE_PLACED = new string_id("guild", "vote_placed");
    public static final string_id SID_VOTE_ABSTAIN = new string_id("guild", "vote_abstain");
    public static final string_id SID_REGISTER_DUPE = new string_id("guild", "vote_register_dupe");
    public static final string_id SID_NOT_REGISTERED = new string_id("guild", "vote_not_registered");
    public static final string_id SID_UNREGISTERED = new string_id("guild", "vote_unregistered");
    public static final string_id SID_REGISTER_CONGRATS = new string_id("guild", "vote_register_congrats");
    public static final string_id SID_ELECTIONS_OPEN = new string_id("guild", "vote_elections_open");
    public static final string_id SID_ELECTIONS_CLOSED = new string_id("guild", "vote_elections_closed");
    public static final string_id SID_ALREADY_LEADER = new string_id("guild", "already_leader");
    public static final string_id OPEN_ELECTIONS_EMAIL_BODY = new string_id("guild", "open_elections_email_body");
    public static final string_id OPEN_ELECTIONS_EMAIL_SUBJECT = new string_id("guild", "open_elections_email_subject");
    public static final string_id CLOSED_ELECTIONS_EMAIL_BODY = new string_id("guild", "closed_elections_email_body");
    public static final string_id CLOSED_ELECTIONS_EMAIL_SUBJECT = new string_id("guild", "closed_elections_email_subject");
    public static final string_id OPEN_ELECTIONS_ABSENT_EMAIL_BODY = new string_id("guild", "open_elections_absent_email_body");
    public static final string_id OPEN_ELECTIONS_ABSENT_EMAIL_SUBJECT = new string_id("guild", "open_elections_absent_email_subject");
    public static final string_id SID_NEED_ACCEPT_HALL = new string_id("guild", "need_accept_hall");
    public static final string_id SID_ACCEPT_HALL = new string_id("guild", "accept_hall");
    public static final string_id SID_TERMINAL_LOCKED = new string_id("guild", "terminal_locked");
    public static final string_id SID_NO_LOTS = new string_id("guild", "no_lots");
    public static final string_id SID_ML_NOT_LOADED = new string_id("guild", "ml_not_loaded");
    public static final string_id SID_ML_NO_LOTS_FREE = new string_id("guild", "ml_no_lots_free");
    public static final string_id SID_ML_TRIAL = new string_id("guild", "ml_trial");
    public static final string_id SID_ML_SUCCESS = new string_id("guild", "ml_success");
    public static final string_id SID_ML_REJECTED = new string_id("guild", "ml_rejected");
    public static final string_id SID_ML_YOU_ARE_LEADER = new string_id("guild", "ml_you_are_leader");
    public static final string_id SID_ML_FAIL = new string_id("guild", "ml_fail");
    public static final string_id SID_PA_OWNER_NOW = new string_id("guild", "pa_owner_now");
    public static final int DISBAND_CHECK_TIME = 60 * 60 * 2;
    public static final float TERMINAL_USE_DISTANCE = 8.0f;
    public static final int LEADER_ABSENT_TIME = 60 * 60 * 24 * 90;
    public static final int ELECTIONS_COMPLETE = 60 * 60 * 24 * 14;
    public static final String SID_VOTING_ENABLED = "@guild:voting_enabled";
    public static final String SID_VOTING_DISABLED = "@guild:voting_disabled";
    public static final string_id SID_ALIGN_IMPERIAL = new string_id("guild", "align_imperial");
    public static final string_id SID_ALIGN_REBEL = new string_id("guild", "align_rebel");
    public static final string_id SID_ALIGN_NEUTRAL = new string_id("guild", "align_neutral");
    public static final string_id SID_BEGIN_GCW_REGION_DEFENDER = new string_id("guild", "begin_gcw_region_defender");
    public static final string_id SID_END_GCW_REGION_DEFENDER = new string_id("guild", "end_gcw_region_defender");
    public int getStructureGuildId(obj_id self) throws InterruptedException
    {
        int guildId = 0;
        obj_id structure = player_structure.getStructure(self);
        if (isIdValid(structure) && hasObjVar(structure, "player_structure.owner") && hasObjVar(structure, "guildId"))
        {
            guildId = getIntObjVar(structure, "guildId");
            if (guildId != 0 && !guildExists(guildId))
            {
                removeObjVar(structure, "guildId");
                guildId = 0;
            }
        }
        if (guildId == 0 && hasObjVar(self, "guildId"))
        {
            guildId = getIntObjVar(self, "guildId");
            if (guildId != 0 && !guildExists(guildId))
            {
                removeObjVar(self, "guildId");
                guildId = 0;
            }
        }
        return guildId;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        String myTemplate = getTemplateName(self);
        obj_id datapad = getContainedBy(self);
        if (!isIdValid(datapad))
        {
            return SCRIPT_CONTINUE;
        }
        if (player_structure.isBuilding(datapad))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = getContainedBy(datapad);
        if (myTemplate.equals(guild.STR_GUILD_REMOTE_DEVICE))
        {
            if (!isIdNull(player) && isPlayer(player))
            {
                obj_id playerDatapad = utils.getPlayerDatapad(player);
                if (isIdValid(playerDatapad) && (datapad == playerDatapad) && (utils.playerHasHowManyItemByTemplateInDataPad(player, guild.STR_GUILD_REMOTE_DEVICE, false) > 1))
                {
                    return SCRIPT_CONTINUE;
                }
            }
            sendSystemMessage(player, guild.SID_GUILD_NO_DESTROY_REMOTE);
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        obj_id datapad = getContainedBy(self);
        if (!isIdValid(datapad) || !exists(datapad))
        {
            return super.OnInitialize(self);
        }
        obj_id player = getContainedBy(datapad);
        if (!isIdValid(player) || !exists(player))
        {
            return super.OnInitialize(self);
        }
        if (guild.hasElectionEnded(player))
        {
            guild.stopElection(player);
        }
        return super.OnInitialize(self);
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (!exists(self))
        {
            return SCRIPT_CONTINUE;
        }
        int guildId = getGuildId(player);
        if (guildId <= 0)
        {
            return SCRIPT_CONTINUE;
        }
        String guildName = guildGetName(guildId);
        if (!guildName.equals(""))
        {
            names[idx] = "guild_name";
            attribs[idx] = guildName;
            idx++;
        }
        String guildAbbrev = guildGetAbbrev(guildId);
        if (!guildName.equals(""))
        {
            names[idx] = "guild_abbrev";
            attribs[idx] = guildAbbrev;
            idx++;
        }
        obj_id guildLeader = guildGetLeader(guildId);
        if (isValidId(guildLeader))
        {
            names[idx] = "guild_leader";
            attribs[idx] = guildGetMemberName(guildId, guildLeader);
            idx++;
        }
        if (isGod(player))
        {
            names[idx] = "god_mode";
            attribs[idx] = "Guild ID: " + guildId;
            idx++;
            names[idx] = "god_mode";
            attribs[idx] = "Voting time: " + guild.getVotingTime(guildId);
            idx++;
            names[idx] = "god_mode";
            attribs[idx] = "Grace period: " + guild.getGracePeriodTime(guildId);
            idx++;
        }
        if (guild.isVotingEnabled(guildId))
        {
            names[idx] = "elections";
            attribs[idx] = utils.localizeSIDString(SID_VOTING_ENABLED) + " until " + getCalendarTimeStringLocal(guild.getVotingTime(guildId));
            idx++;
        }
        else 
        {
            if (guild.isVotingGracePeriod(guildId))
            {
                names[idx] = "elections";
                attribs[idx] = utils.localizeSIDString(SID_VOTING_DISABLED) + " until " + getCalendarTimeStringLocal(guild.getGracePeriodTime(guildId));
                idx++;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int guildsListMenu = mi.addRootMenu(menu_info_types.SERVER_MENU3, SID_GUILDS_LIST);
        int guildWarListMenu = mi.addRootMenu(menu_info_types.SERVER_MENU8, SID_GUILD_WAR_LIST);
        mi.addSubMenu(guildWarListMenu, menu_info_types.SERVER_MENU12, SID_GUILD_WAR_LIST_ACTIVE);
        mi.addSubMenu(guildWarListMenu, menu_info_types.SERVER_MENU13, SID_GUILD_WAR_LIST_INACTIVE);
        final int guildId = getGuildId(player);
        if (guildId == 0)
        {
            return super.OnObjectMenuRequest(self, player, mi);
        }
        final obj_id guildLeader = guildGetLeader(guildId);
        if (guildId != 0 && (getGuildId(player) == guildId || isGod(player)))
        {
            int guildManagementMenu = mi.addRootMenu(menu_info_types.SERVER_GUILD_GUILD_MANAGEMENT, SID_GUILD_GUILD_MANAGEMENT);
            mi.addSubMenu(guildManagementMenu, menu_info_types.SERVER_GUILD_INFO, SID_GUILD_INFO);
            mi.addSubMenu(guildManagementMenu, menu_info_types.SERVER_GUILD_ENEMIES, SID_GUILD_ENEMIES);
            mi.addSubMenu(guildManagementMenu, menu_info_types.SERVER_MENU16, SID_GUILD_RANK_LIST);
            mi.addSubMenu(guildManagementMenu, menu_info_types.SERVER_MENU17, SID_GUILD_RANK_SUMMARY);
            mi.addSubMenu(guildManagementMenu, menu_info_types.SERVER_MENU18, SID_GUILD_PERMISSION_LIST);
            if (guild.hasGuildPermission(guildId, player, guild.GUILD_PERMISSION_DISBAND))
            {
                mi.addSubMenu(guildManagementMenu, menu_info_types.SERVER_GUILD_DISBAND, SID_GUILD_DISBAND);
            }
            if (guild.hasGuildPermission(guildId, player, guild.GUILD_PERMISSION_NAMECHANGE))
            {
                mi.addSubMenu(guildManagementMenu, menu_info_types.SERVER_GUILD_NAMECHANGE, SID_GUILD_NAMECHANGE);
            }
            if (!hasObjVar(self, guild.GUILD_SCREEN_ID) && guildLeader == player)
            {
                mi.addSubMenu(guildManagementMenu, menu_info_types.SERVER_MENU2, guild.SID_MAKE_NEW_SCREEN);
            }
            if (player == guildLeader || isGod(player))
            {
                final int factionId = guildGetCurrentFaction(guildId);
                if (((-615855020) == factionId) || ((370444368) == factionId))
                {
                    mi.addSubMenu(guildManagementMenu, menu_info_types.SERVER_MENU19, SID_ALIGN_NEUTRAL);
                }
                else 
                {
                    mi.addSubMenu(guildManagementMenu, menu_info_types.SERVER_MENU20, SID_ALIGN_IMPERIAL);
                    mi.addSubMenu(guildManagementMenu, menu_info_types.SERVER_MENU21, SID_ALIGN_REBEL);
                }
                final String gcwDefenderRegion = guildGetCurrentGcwDefenderRegion(guildId);
                if ((gcwDefenderRegion != null) && (gcwDefenderRegion.length() > 0))
                {
                    mi.addSubMenu(guildManagementMenu, menu_info_types.SERVER_MENU22, SID_END_GCW_REGION_DEFENDER);
                }
                else 
                {
                    mi.addSubMenu(guildManagementMenu, menu_info_types.SERVER_MENU23, SID_BEGIN_GCW_REGION_DEFENDER);
                }
            }
            int memberManagementMenu = mi.addRootMenu(menu_info_types.SERVER_GUILD_MEMBER_MANAGEMENT, SID_GUILD_MEMBER_MANAGEMENT);
            mi.addSubMenu(memberManagementMenu, menu_info_types.SERVER_GUILD_MEMBERS, SID_GUILD_MEMBERS);
            if (guild.hasSponsoredMembers(guildId))
            {
                mi.addSubMenu(memberManagementMenu, menu_info_types.SERVER_GUILD_SPONSORED, SID_GUILD_SPONSORED);
            }
            if (guild.hasGuildPermission(guildId, player, guild.GUILD_PERMISSION_SPONSOR))
            {
                mi.addSubMenu(memberManagementMenu, menu_info_types.SERVER_GUILD_SPONSOR, SID_GUILD_SPONSOR);
            }
            if (player == guildLeader || isGod(player))
            {
                mi.addSubMenu(memberManagementMenu, menu_info_types.SERVER_MENU1, SID_GUILD_LEADER_CHANGE);
            }
            int menu = -1;
            if (!guild.hasElectionEnded(player))
            {
                if (guild.isVotingEnabled(guildId))
                {
                    menu = mi.addRootMenu(menu_info_types.SERVER_MENU4, SID_LEADER_RACE);
                    mi.addSubMenu(menu, menu_info_types.SERVER_MENU5, SID_LEADER_STANDINGS);
                    if (!guild.isCandidate(guildId, player))
                    {
                        mi.addSubMenu(menu, menu_info_types.SERVER_MENU6, SID_LEADER_REGISTER);
                    }
                    else 
                    {
                        if (player != guildLeader)
                        {
                            mi.addSubMenu(menu, menu_info_types.SERVER_MENU7, SID_LEADER_UNREGISTER);
                        }
                    }
                }
                else 
                {
                    if (!guild.isVotingGracePeriod(guildId))
                    {
                        menu = mi.addRootMenu(menu_info_types.SERVER_MENU4, SID_LEADER_RACE);
                        mi.addSubMenu(menu, menu_info_types.SERVER_MENU6, SID_LEADER_REGISTER);
                    }
                }
            }
            else 
            {
                guild.stopElection(player);
            }
            if (isGod(player))
            {
                if (menu == -1)
                {
                    menu = mi.addRootMenu(menu_info_types.SERVER_MENU4, SID_LEADER_RACE);
                }
                mi.addSubMenu(menu, menu_info_types.SERVER_MENU9, SID_QA_RESET_VOTING);
                if (guild.isVotingEnabled(guildId))
                {
                    mi.addSubMenu(menu, menu_info_types.SERVER_MENU11, SID_QA_END_ELECTION);
                    mi.addSubMenu(menu, menu_info_types.SERVER_MENU14, SID_QA_EXPIRE_VOTING);
                }
                else 
                {
                    mi.addSubMenu(menu, menu_info_types.SERVER_MENU10, SID_QA_START_ELECTION);
                    if (guild.isVotingGracePeriod(guildId))
                    {
                        mi.addSubMenu(menu, menu_info_types.SERVER_MENU15, SID_QA_EXPIRE_GRACE_PERIOD);
                    }
                }
            }
        }
        return super.OnObjectMenuRequest(self, player, mi);
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        sendDirtyObjectMenuNotification(self);
        if (item == menu_info_types.SERVER_MENU3)
        {
            dictionary guildsListParams = new dictionary();
            guildsListParams.put("player", player);
            messageTo(self, "showGuildsList", guildsListParams, 3.0f, false);
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.SERVER_MENU12)
        {
            guild.showMasterGuildWarTableDictionary(player);
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.SERVER_MENU13)
        {
            guild.showInactiveGuildWarTableDictionary(player);
            return SCRIPT_CONTINUE;
        }
        final int guildId = getGuildId(player);
        if (guildId == 0)
        {
            return SCRIPT_CONTINUE;
        }
        final obj_id guildLeader = guildGetLeader(guildId);
        if (item == menu_info_types.ITEM_USE || item == menu_info_types.SERVER_GUILD_INFO)
        {
            guild.showGuildInfo(player);
        }
        else if (item == menu_info_types.SERVER_GUILD_ENEMIES)
        {
            guild.showGuildEnemies(player);
        }
        else if (item == menu_info_types.SERVER_GUILD_SPONSORED)
        {
            guild.showGuildSponsored(player);
        }
        else if (item == menu_info_types.SERVER_GUILD_MEMBERS)
        {
            guild.showGuildMembers(player, player, 0, 0, "", "");
        }
        else if (item == menu_info_types.SERVER_GUILD_SPONSOR && (guild.hasGuildPermission(guildId, player, guild.GUILD_PERMISSION_SPONSOR) || isGod(player)))
        {
            if (guild.hasWindowPid(player))
            {
                int pid = guild.getWindowPid(player);
                forceCloseSUIPage(pid);
                guild.removeWindowPid(player);
            }
            int pid = sui.inputbox(player, player, guild.STR_GUILD_SPONSOR_PROMPT, sui.OK_CANCEL, guild.STR_GUILD_SPONSOR_TITLE, sui.INPUT_NORMAL, null, "onGuildSponsorResponse");
            guild.setWindowPid(player, pid);
        }
        else if (item == menu_info_types.SERVER_GUILD_DISBAND && (guild.hasGuildPermission(guildId, player, guild.GUILD_PERMISSION_DISBAND) || isGod(player)))
        {
            if (guild.hasWindowPid(player))
            {
                int pid = guild.getWindowPid(player);
                forceCloseSUIPage(pid);
                guild.removeWindowPid(player);
            }
            int pid = sui.msgbox(player, player, guild.STR_GUILD_DISBAND_PROMPT, sui.YES_NO, guild.STR_GUILD_DISBAND_TITLE, sui.MSG_NORMAL, "onGuildDisbandResponse");
            guild.setWindowPid(player, pid);
        }
        else if (item == menu_info_types.SERVER_GUILD_NAMECHANGE && (guild.hasGuildPermission(guildId, player, guild.GUILD_PERMISSION_NAMECHANGE) || isGod(player)))
        {
            if (guild.hasWindowPid(player))
            {
                int pid = guild.getWindowPid(player);
                forceCloseSUIPage(pid);
                guild.removeWindowPid(player);
            }
            int pid = sui.inputbox(player, player, guild.STR_GUILD_NAMECHANGE_NAME_PROMPT, sui.OK_CANCEL, guild.STR_GUILD_NAMECHANGE_NAME_TITLE, sui.INPUT_NORMAL, null, "onGuildNamechangeNameResponse");
            guild.setWindowPid(player, pid);
        }
        else if (item == menu_info_types.SERVER_MENU1 && (player == guildLeader || isGod(player)))
        {
            if (guild.hasWindowPid(player))
            {
                int pid = guild.getWindowPid(player);
                forceCloseSUIPage(pid);
                guild.removeWindowPid(player);
            }
            int pid = sui.inputbox(self, player, "@guild:make_leader_d", sui.OK_CANCEL, "@guild:make_leader_t", sui.INPUT_NORMAL, null, "handleMakeLeader", null);
            guild.setWindowPid(player, pid);
        }
        else if (item == menu_info_types.SERVER_MENU2 && !hasObjVar(self, guild.GUILD_SCREEN_ID) && guildLeader == player)
        {
            obj_id pInv = utils.getInventoryContainer(player);
            String screenTemplate = "";
            if (hasObjVar(self, guild.GUILD_SCREEN_TYPE))
            {
                screenTemplate = getStringObjVar(self, guild.GUILD_SCREEN_TYPE);
            }
            else 
            {
                screenTemplate = guild.GUILD_SCREEN_TEMPLATE;
            }
            obj_id guildScreen = createObjectOverloaded(screenTemplate, pInv);
            if (isIdValid(guildScreen))
            {
                setOwner(guildScreen, player);
                obj_id guildLeaderPDA = guild.getGuildRemoteDevice(player);
                if (isIdValid(guildLeaderPDA) && exists(guildLeaderPDA))
                {
                    setObjVar(guildLeaderPDA, guild.GUILD_SCREEN_ID, guildScreen);
                    setObjVar(guildScreen, guild.GUILD_SCREEN_ID, guildLeaderPDA);
                    setObjVar(guildScreen, guild.REGISTERED_GUILD, guildId);
                }
            }
        }
        else if (item == menu_info_types.SERVER_MENU4 || item == menu_info_types.SERVER_MENU5)
        {
            guild.showStandings(self, player);
        }
        else if (item == menu_info_types.SERVER_MENU9 && isGod(player))
        {
            resetElection(self, player);
        }
        else if (item == menu_info_types.SERVER_MENU10 && isGod(player))
        {
            startElection(self, player);
        }
        else if (item == menu_info_types.SERVER_MENU11 && isGod(player))
        {
            guild.stopElection(player);
        }
        else if (item == menu_info_types.SERVER_MENU14 && isGod(player))
        {
            guildSetElectionEndTime(guildId, 0, getCalendarTime());
        }
        else if (item == menu_info_types.SERVER_MENU15 && isGod(player))
        {
            guildSetElectionEndTime(guildId, getCalendarTime(), 0);
        }
        else if (item == menu_info_types.SERVER_MENU6)
        {
            registerToRun(self, player);
        }
        else if (item == menu_info_types.SERVER_MENU7)
        {
            unregisterFromRace(self, player);
        }
        else if (item == menu_info_types.SERVER_MENU16)
        {
            guild.showRankList(self, player);
        }
        else if (item == menu_info_types.SERVER_MENU17)
        {
            guild.showRankSummary(self, player);
        }
        else if (item == menu_info_types.SERVER_MENU18)
        {
            guild.showPermissionSummary(self, player);
        }
        else if (item == menu_info_types.SERVER_MENU19)
        {
            if (player == guildLeader || isGod(player))
            {
                final int factionId = guildGetCurrentFaction(guildId);
                if (((-615855020) == factionId) || ((370444368) == factionId))
                {
                    final String gcwDefenderRegion = guildGetCurrentGcwDefenderRegion(guildId);
                    if ((gcwDefenderRegion != null) && (gcwDefenderRegion.length() > 0))
                    {
                        sendSystemMessage(player, "You cannot change the guild's factional alignment to Neutral while it is a GCW region defender.", "");
                    }
                    else 
                    {
                        sendSystemMessage(player, "Setting the guild's factional alignment to Neutral. This may take a few seconds. You will receive mail confirmation once the change has been completed.", "");
                        guildSetFaction(guildId, 0);
                    }
                }
            }
        }
        else if (item == menu_info_types.SERVER_MENU20)
        {
            if (player == guildLeader || isGod(player))
            {
                final int currentFactionId = guildGetCurrentFaction(guildId);
                if (currentFactionId == 0)
                {
                    boolean allow = true;
                    final int previousFactionId = guildGetPreviousFaction(guildId);
                    final int timeLeftPreviousFaction = guildGetTimeLeftPreviousFaction(guildId);
                    if (((370444368) == previousFactionId) && (timeLeftPreviousFaction > 0))
                    {
                        int cooldown = (timeLeftPreviousFaction + (isGod(player) ? 10 : 86400)) - getCalendarTime();
                        if (cooldown > 0)
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
                            sendSystemMessage(player, "You must wait " + cooldownStr + " before you can change the guild's factional alignment to Imperial.", "");
                            allow = false;
                        }
                    }
                    if (allow)
                    {
                        sendSystemMessage(player, "Setting the guild's factional alignment to Imperial. This may take a few seconds. You will receive mail confirmation once the change has been completed.", "");
                        guildSetFaction(guildId, (-615855020));
                    }
                }
            }
        }
        else if (item == menu_info_types.SERVER_MENU21)
        {
            if (player == guildLeader || isGod(player))
            {
                final int currentFactionId = guildGetCurrentFaction(guildId);
                if (currentFactionId == 0)
                {
                    boolean allow = true;
                    final int previousFactionId = guildGetPreviousFaction(guildId);
                    final int timeLeftPreviousFaction = guildGetTimeLeftPreviousFaction(guildId);
                    if (((-615855020) == previousFactionId) && (timeLeftPreviousFaction > 0))
                    {
                        int cooldown = (timeLeftPreviousFaction + (isGod(player) ? 10 : 86400)) - getCalendarTime();
                        if (cooldown > 0)
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
                            sendSystemMessage(player, "You must wait " + cooldownStr + " before you can change the guild's factional alignment to Rebel.", "");
                            allow = false;
                        }
                    }
                    if (allow)
                    {
                        sendSystemMessage(player, "Setting the guild's factional alignment to Rebel. This may take a few seconds. You will receive mail confirmation once the change has been completed.", "");
                        guildSetFaction(guildId, (370444368));
                    }
                }
            }
        }
        else if (item == menu_info_types.SERVER_MENU22)
        {
            if (player == guildLeader || isGod(player))
            {
                final String gcwDefenderRegion = guildGetCurrentGcwDefenderRegion(guildId);
                if ((gcwDefenderRegion != null) && (gcwDefenderRegion.length() > 0))
                {
                    sendSystemMessage(player, "Setting the guild's GCW defender region to (None). This may take a few seconds. You will receive mail confirmation once the change has been completed.", "");
                    guildSetGcwDefenderRegion(guildId, "");
                }
            }
        }
        else if (item == menu_info_types.SERVER_MENU23)
        {
            if (player == guildLeader || isGod(player))
            {
                final String gcwCurrentDefenderRegion = guildGetCurrentGcwDefenderRegion(guildId);
                if ((gcwCurrentDefenderRegion == null) || (gcwCurrentDefenderRegion.length() <= 0))
                {
                    final int factionId = guildGetCurrentFaction(guildId);
                    if (((-615855020) == factionId) || ((370444368) == factionId))
                    {
                        final int requiredMemberCountNumber = utils.stringToInt(getConfigSetting("GameServer", "gcwGuildMinMembersForGcwRegionDefender"));
                        if (guildGetCountMembersOnly(guildId) < requiredMemberCountNumber)
                        {
                            sendSystemMessage(player, "The guild cannot become a GCW region defender until it has at least " + requiredMemberCountNumber + " members.", "");
                        }
                        else 
                        {
                            String announcement = "Select a GCW region for your guild to defend.";
                            final String gcwPreviousDefenderRegion = guildGetPreviousGcwDefenderRegion(guildId);
                            final int timeLeftPreviousGcwDefenderRegion = guildGetTimeLeftPreviousGcwDefenderRegion(guildId);
                            if ((gcwPreviousDefenderRegion != null) && (gcwPreviousDefenderRegion.length() > 0) && (timeLeftPreviousGcwDefenderRegion > 0))
                            {
                                final int cooldown = timeLeftPreviousGcwDefenderRegion + (isGod(player) ? 10 : 86400) - getCalendarTime();
                                if (cooldown > 0)
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
                                    announcement += "\n";
                                    announcement += "You can immediately defend the GCW region you most recently defended (" + localize(new string_id("gcw_regions", gcwPreviousDefenderRegion)) + ").\n";
                                    announcement += "You must wait " + cooldownStr + " before you can defend a different GCW region.";
                                }
                            }
                            String[] gcwDefenderRegions = getGcwDefenderRegions();
                            if ((gcwDefenderRegions != null) && (gcwDefenderRegions.length > 0))
                            {
                                final String[] columnHeader = 
                                {
                                    "GCW Region"
                                };
                                final String[] columnHeaderType = 
                                {
                                    "text"
                                };
                                final String[][] columnData = new String[1][0];
                                columnData[0] = gcwDefenderRegions;
                                sui.tableColumnMajor(player, player, sui.OK_CANCEL, "@gcw:gcw_region_defender_war_terminal_menu", "handleGuildGcwRegionDefenderChoice", announcement, columnHeader, columnHeaderType, columnData, false);
                            }
                        }
                    }
                    else 
                    {
                        sendSystemMessage(player, "The guild cannot become a GCW region defender until it is aligned with a faction.", "");
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public obj_id getMenuContextObjId(obj_id self, obj_id player, String varName) throws InterruptedException
    {
        deltadictionary dd = self.getScriptVars();
        return dd.getObjId("guildMenu." + player + "." + varName);
    }
    public void setMenuContextObjId(obj_id self, obj_id player, String varName, obj_id value) throws InterruptedException
    {
        deltadictionary dd = self.getScriptVars();
        dd.put("guildMenu." + player + "." + varName, value);
    }
    public int handleMakeLeader(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        if (!guild.hasWindowPid(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (sui.getIntButtonPressed(params) != sui.BP_OK)
        {
            guild.removeWindowPid(player);
            return SCRIPT_CONTINUE;
        }
        String input_name = sui.getInputBoxText(params);
        int guildId = getGuildId(player);
        obj_id[] members = guild.getMemberIds(guildId, false, true);
        for (int i = 0; i < members.length; i++)
        {
            String name = guildGetMemberName(guildId, members[i]);
            if (name.equals(input_name))
            {
                if (members[i] == player && !isGod(player))
                {
                    sendSystemMessage(player, SID_ALREADY_LEADER);
                    return SCRIPT_CONTINUE;
                }
                location oldLeader = getLocation(player);
                location newLeader = getLocation(members[i]);
                if (!oldLeader.area.equals(newLeader.area))
                {
                    sendSystemMessage(player, SID_ML_NOT_LOADED);
                    return SCRIPT_CONTINUE;
                }
                float dist = getDistance(player, members[i]);
                if (dist < 0 || dist > 20)
                {
                    sendSystemMessage(player, SID_ML_NOT_LOADED);
                    return SCRIPT_CONTINUE;
                }
                if (utils.isFreeTrial(player, members[i]))
                {
                    sendSystemMessage(player, SID_ML_TRIAL);
                    return SCRIPT_CONTINUE;
                }
                utils.setScriptVar(self, "temp_new_leader", members[i]);
                int pid = sui.msgbox(self, members[i], "@guild:make_leader_p", sui.YES_NO, "@guild:make_leader_t", sui.MSG_NORMAL, "handleAcceptLeadership");
                guild.setWindowPid(self, pid);
                return SCRIPT_CONTINUE;
            }
        }
        sendSystemMessage(player, SID_ML_FAIL);
        return SCRIPT_CONTINUE;
    }
    public int handleAcceptLeadership(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        if (!guild.hasWindowPid(self))
        {
            return SCRIPT_CONTINUE;
        }
        int guildId = getGuildId(player);
        obj_id oldLeader = guildGetLeader(guildId);
        obj_id newLeader = utils.getObjIdScriptVar(self, "temp_new_leader");
        if (sui.getIntButtonPressed(params) == sui.BP_OK)
        {
            obj_id[] members = guild.getMemberIds(guildId, false, true);
            boolean done = false;
            for (int i = 0; i < members.length; i++)
            {
                if (members[i] == newLeader)
                {
                    guild.changeLeader(guildId, newLeader);
                    sendSystemMessage(oldLeader, SID_ML_SUCCESS);
                    sendSystemMessage(newLeader, SID_ML_YOU_ARE_LEADER);
                    done = true;
                    break;
                }
            }
            if (!done)
            {
                sendSystemMessage(oldLeader, SID_ML_FAIL);
            }
        }
        else 
        {
            sendSystemMessage(oldLeader, SID_ML_REJECTED);
        }
        guild.removeWindowPid(self);
        utils.removeScriptVar(self, "temp_new_leader");
        return SCRIPT_CONTINUE;
    }
    public void resetElection(obj_id self, obj_id player) throws InterruptedException
    {
        int guildId = getGuildId(player);
        if (guildId == 0)
        {
            return;
        }
        guildSetElectionEndTime(guildId, 0, 0);
        guild.resetCandidates(guildId, null);
    }
    public void startElection(obj_id self, obj_id player) throws InterruptedException
    {
        int guildId = getGuildId(player);
        if (guildId == 0)
        {
            return;
        }
        guildSetElectionEndTime(guildId, 0, getCalendarTime() + 604800);
        obj_id guildLeader = guildGetLeader(guildId);
        guild.makeCandidate(guildId, guildLeader);
        obj_id[] members = guild.getMemberIds(guildId, false, true);
        int lastLoginTime = getPlayerLastLoginTime(guildLeader);
        int timeDifference = getCalendarTime() - lastLoginTime;
        for (int i = 0; i < members.length; i++)
        {
            if (isIdValid(members[i]) && members[i] != guildLeader && members[i] != player && timeDifference < LEADER_ABSENT_TIME)
            {
                guildSetMemberAllegiance(guildId, members[i], guildLeader);
            }
            else 
            {
                if (isIdValid(members[i]) && members[i] != guildLeader && members[i] != player)
                {
                    guildSetMemberAllegiance(guildId, members[i], null);
                }
            }
            String cname = guildGetMemberName(guildId, members[i]);
            prose_package bodypp = prose.getPackage(OPEN_ELECTIONS_EMAIL_BODY, cname);
            utils.sendMail(OPEN_ELECTIONS_EMAIL_SUBJECT, bodypp, cname, "Guild Management");
        }
    }
    public void registerToRun(obj_id self, obj_id player) throws InterruptedException
    {
        int guildId = getGuildId(player);
        obj_id leader = guildGetLeader(guildId);
        if (!guild.hasGuildPermission(guildId, player, guild.GUILD_PERMISSION_MEMBER))
        {
            return;
        }
        if (utils.isFreeTrial(player))
        {
            return;
        }
        if (guild.isCandidate(guildId, player))
        {
            sendSystemMessage(player, SID_REGISTER_DUPE);
            return;
        }
        if (!guild.isVotingEnabled(guildId) && !guild.isVotingGracePeriod(guildId))
        {
            startElection(self, player);
        }
        guild.makeCandidate(guildId, player);
        sendSystemMessage(player, SID_REGISTER_CONGRATS);
    }
    public void unregisterFromRace(obj_id self, obj_id player) throws InterruptedException
    {
        int guildId = getGuildId(player);
        obj_id leader = guildGetLeader(guildId);
        if (!guild.isCandidate(guildId, player))
        {
            sendSystemMessage(player, SID_NOT_REGISTERED);
            return;
        }
        if (player == leader)
        {
            return;
        }
        guild.removeCandidate(guildId, player);
        sendSystemMessage(player, SID_UNREGISTERED);
    }
    public void acceptPAHall(obj_id self, obj_id player) throws InterruptedException
    {
        sui.msgbox(self, player, "@guild:accept_pa_hall_p", sui.YES_NO, "@guild:accept_pa_hall_t", sui.MSG_NORMAL, "handleAcceptPAHall");
    }
    public int handleAcceptPAHall(obj_id self, dictionary params) throws InterruptedException
    {
        if (sui.getIntButtonPressed(params) == sui.BP_OK)
        {
            obj_id pa_hall = getTopMostContainer(self);
            int guildId = getStructureGuildId(self);
            obj_id player = guildGetLeader(guildId);
            obj_id structure = player_structure.getStructure(self);
            if (!player_structure.canOwnStructure(structure, player))
            {
                sendSystemMessage(player, SID_NO_LOTS);
                return SCRIPT_CONTINUE;
            }
            obj_id hall_owner = getOwner(structure);
            player_structure.removeStructure(structure, hall_owner);
            player_structure.addStructure(structure, player);
            sendSystemMessage(player, SID_PA_OWNER_NOW);
        }
        return SCRIPT_CONTINUE;
    }
    public int handlerGuildScreenDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id screenIdSent = params.getObjId("screenId");
        obj_id guildScreen = getObjIdObjVar(self, guild.GUILD_SCREEN_ID);
        if (isIdValid(screenIdSent) && isIdValid(guildScreen))
        {
            if (screenIdSent == guildScreen)
            {
                removeObjVar(self, guild.GUILD_SCREEN_ID);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int showGuildsList(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId("player");
        if (!isIdValid(player) || !exists(player))
        {
            return SCRIPT_CONTINUE;
        }
        String[] table_titles = 
        {
            "@guild:table_title_name",
            "@guild:table_guildlist_abbreviation",
            "@guild:table_guildlist_members"
        };
        String[] table_types = 
        {
            "text",
            "text",
            "integer"
        };
        int[] guildIds = getAllGuildIds();
        if (guildIds == null || guildIds.length < 1)
        {
            return SCRIPT_CONTINUE;
        }
        Vector validatedIds = new Vector();
        validatedIds.setSize(0);
        for (int i = 0, j = guildIds.length; i < j; i++)
        {
            if (guildGetCountMembersOnly(guildIds[i]) > 4)
            {
                utils.addElement(validatedIds, guildIds[i]);
            }
        }
        if (validatedIds == null || validatedIds.size() < 1)
        {
            return SCRIPT_CONTINUE;
        }
        String[][] guildsData = new String[validatedIds.size()][3];
        for (int i = 0, j = validatedIds.size(); i < j; i++)
        {
            guildsData[i][0] = guildGetName(((Integer)validatedIds.get(i)).intValue());
            guildsData[i][1] = guildGetAbbrev(((Integer)validatedIds.get(i)).intValue());
            guildsData[i][2] = "" + guildGetCountMembersOnly(((Integer)validatedIds.get(i)).intValue());
        }
        int pid = sui.tableRowMajor(self, player, sui.OK_CANCEL, "List of Guilds", "onGuildsListResponse", null, table_titles, table_types, guildsData);
        guild.setWindowPid(self, pid);
        return SCRIPT_CONTINUE;
    }
}
