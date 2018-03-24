package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class guild extends script.base_script
{
    public guild()
    {
    }
    public static final int GUILD_PERMISSIONS_NONE = 0;
    public static final int GUILD_PERMISSION_MEMBER = (1 << 0);
    public static final int GUILD_PERMISSION_SPONSOR = (1 << 1);
    public static final int GUILD_PERMISSION_DISBAND = (1 << 2);
    public static final int GUILD_PERMISSION_ACCEPT = (1 << 3);
    public static final int GUILD_PERMISSION_KICK = (1 << 4);
    public static final int GUILD_PERMISSION_MAIL = (1 << 5);
    public static final int GUILD_PERMISSION_TITLE = (1 << 6);
    public static final int GUILD_PERMISSION_NAMECHANGE = (1 << 7);
    public static final int GUILD_PERMISSION_WAR = (1 << 8);
    public static final int GUILD_PERMISSION_RANK = (1 << 9);
    public static final int GUILD_PERMISSION_ADMINISTRATIVE_RESERVED1 = (1 << 10);
    public static final int GUILD_PERMISSION_ADMINISTRATIVE_RESERVED2 = (1 << 11);
    public static final int GUILD_PERMISSION_ADMINISTRATIVE_RESERVED3 = (1 << 12);
    public static final int GUILD_PERMISSION_ADMINISTRATIVE_RESERVED4 = (1 << 13);
    public static final int GUILD_PERMISSION_ADMINISTRATIVE_RESERVED5 = (1 << 14);
    public static final int GUILD_PERMISSION_ADMINISTRATIVE_RESERVED6 = (1 << 15);
    public static final int GUILD_PERMISSION_ADMINISTRATIVE_RESERVED7 = (1 << 16);
    public static final int GUILD_PERMISSION_ADMINISTRATIVE_RESERVED8 = (1 << 17);
    public static final int GUILD_PERMISSION_ADMINISTRATIVE_RESERVED9 = (1 << 18);
    public static final int GUILD_PERMISSION_ADMINISTRATIVE_RESERVED10 = (1 << 19);
    public static final int GUILD_PERMISSIONS_ALL_ADMINISTRATIVE = (GUILD_PERMISSION_MEMBER | GUILD_PERMISSION_SPONSOR | GUILD_PERMISSION_DISBAND | GUILD_PERMISSION_ACCEPT | GUILD_PERMISSION_KICK | GUILD_PERMISSION_MAIL | GUILD_PERMISSION_TITLE | GUILD_PERMISSION_NAMECHANGE | GUILD_PERMISSION_WAR | GUILD_PERMISSION_RANK | GUILD_PERMISSION_ADMINISTRATIVE_RESERVED1 | GUILD_PERMISSION_ADMINISTRATIVE_RESERVED2 | GUILD_PERMISSION_ADMINISTRATIVE_RESERVED3 | GUILD_PERMISSION_ADMINISTRATIVE_RESERVED4 | GUILD_PERMISSION_ADMINISTRATIVE_RESERVED5 | GUILD_PERMISSION_ADMINISTRATIVE_RESERVED6 | GUILD_PERMISSION_ADMINISTRATIVE_RESERVED7 | GUILD_PERMISSION_ADMINISTRATIVE_RESERVED8 | GUILD_PERMISSION_ADMINISTRATIVE_RESERVED9 | GUILD_PERMISSION_ADMINISTRATIVE_RESERVED10);
    public static final int GUILD_PERMISSION_ELECTION_CANDIDATE = (1 << 20);
    public static final int GUILD_PERMISSION_WAR_INCLUSION = (1 << 21);
    public static final int GUILD_PERMISSION_WAR_EXCLUSION = (1 << 22);
    public static final int GUILD_PERMISSION_ONLINE_STATUS = (1 << 23);
    public static final int GUILD_PERMISSIONS_ALL_PERSONAL = (GUILD_PERMISSION_WAR_EXCLUSION | GUILD_PERMISSION_ONLINE_STATUS);
    public static final int GUILD_MEMBERS_PER_PAGE = 25;
    public static final String GUILD_MAIL_DISBAND_SUBJECT = "@guildmail:disband_subject";
    public static final string_id GUILD_MAIL_DISBAND_TEXT = new string_id("guildmail", "disband_text");
    public static final string_id GUILD_MAIL_DISBAND_NOT_ENOUGH_MEMBERS_TEXT = new string_id("guildmail", "disband_not_enough_members_text");
    public static final String GUILD_MAIL_SPONSOR_SUBJECT = "@guildmail:sponsor_subject";
    public static final string_id GUILD_MAIL_SPONSOR_TEXT = new string_id("guildmail", "sponsor_text");
    public static final String GUILD_MAIL_ACCEPT_SUBJECT = "@guildmail:accept_subject";
    public static final string_id GUILD_MAIL_ACCEPT_TEXT = new string_id("guildmail", "accept_text");
    public static final String GUILD_MAIL_ACCEPT_TARGET_SUBJECT = "@guildmail:accept_target_subject";
    public static final string_id GUILD_MAIL_ACCEPT_TARGET_TEXT = new string_id("guildmail", "accept_target_text");
    public static final String GUILD_MAIL_KICK_SUBJECT = "@guildmail:kick_subject";
    public static final string_id GUILD_MAIL_KICK_TEXT = new string_id("guildmail", "kick_text");
    public static final String GUILD_MAIL_DECLINE_SUBJECT = "@guildmail:decline_subject";
    public static final string_id GUILD_MAIL_DECLINE_TEXT = new string_id("guildmail", "decline_text");
    public static final String GUILD_MAIL_DECLINE_TARGET_SUBJECT = "@guildmail:decline_target_subject";
    public static final string_id GUILD_MAIL_DECLINE_TARGET_TEXT = new string_id("guildmail", "decline_target_text");
    public static final String GUILD_MAIL_NAMECHANGE_SUBJECT = "@guildmail:namechange_subject";
    public static final string_id GUILD_MAIL_NAMECHANGE_TEXT = new string_id("guildmail", "namechange_text");
    public static final String GUILD_MAIL_LEADERCHANGE_SUBJECT = "@guildmail:leaderchange_subject";
    public static final string_id GUILD_MAIL_LEADERCHANGE_TEXT = new string_id("guildmail", "leaderchange_text");
    public static final String GUILD_MAIL_LEAVE_SUBJECT = "@guildmail:leave_subject";
    public static final string_id GUILD_MAIL_LEAVE_TEXT = new string_id("guildmail", "leave_text");
    public static final string_id SID_GUILD_SPONSOR_NOT_FOUND = new string_id("guild", "sponsor_not_found");
    public static final string_id SID_GUILD_MEMBER_NOT_FOUND = new string_id("guild", "could_not_find_member");
    public static final String STR_GUILD_PERMISSION_MAIL_YES = "@guild:permission_mail_yes";
    public static final String STR_GUILD_PERMISSION_MAIL_NO = "@guild:permission_mail_no";
    public static final String STR_GUILD_PERMISSION_SPONSOR_YES = "@guild:permission_sponsor_yes";
    public static final String STR_GUILD_PERMISSION_SPONSOR_NO = "@guild:permission_sponsor_no";
    public static final String STR_GUILD_PERMISSION_TITLE_YES = "@guild:permission_title_yes";
    public static final String STR_GUILD_PERMISSION_TITLE_NO = "@guild:permission_title_no";
    public static final String STR_GUILD_PERMISSION_ACCEPT_YES = "@guild:permission_accept_yes";
    public static final String STR_GUILD_PERMISSION_ACCEPT_NO = "@guild:permission_accept_no";
    public static final String STR_GUILD_PERMISSION_KICK_YES = "@guild:permission_kick_yes";
    public static final String STR_GUILD_PERMISSION_KICK_NO = "@guild:permission_kick_no";
    public static final String STR_GUILD_PERMISSION_WAR_YES = "@guild:permission_war_yes";
    public static final String STR_GUILD_PERMISSION_WAR_NO = "@guild:permission_war_no";
    public static final String STR_GUILD_PERMISSION_NAMECHANGE_YES = "@guild:permission_namechange_yes";
    public static final String STR_GUILD_PERMISSION_NAMECHANGE_NO = "@guild:permission_namechange_no";
    public static final String STR_GUILD_PERMISSION_DISBAND_YES = "@guild:permission_disband_yes";
    public static final String STR_GUILD_PERMISSION_DISBAND_NO = "@guild:permission_disband_no";
    public static final String STR_GUILD_PERMISSION_RANK_YES = "@guild:permission_rank_yes";
    public static final String STR_GUILD_PERMISSION_RANK_NO = "@guild:permission_rank_no";
    public static final String STR_GUILD_PERMISSION_MAIL = "@guild:permission_mail";
    public static final String STR_GUILD_PERMISSION_SPONSOR = "@guild:permission_sponsor";
    public static final String STR_GUILD_PERMISSION_TITLE = "@guild:permission_title";
    public static final String STR_GUILD_PERMISSION_ACCEPT = "@guild:permission_accept";
    public static final String STR_GUILD_PERMISSION_KICK = "@guild:permission_kick";
    public static final String STR_GUILD_PERMISSION_WAR = "@guild:permission_war";
    public static final String STR_GUILD_PERMISSION_NAMECHANGE = "@guild:permission_namechange";
    public static final String STR_GUILD_PERMISSION_DISBAND = "@guild:permission_disband";
    public static final String STR_GUILD_PERMISSIONS_PROMPT = "@guild:permissions_prompt";
    public static final string_id SID_GUILD_PERMISSIONS_TITLE = new string_id("guild", "permissions_title");
    public static final String STR_GUILD_REMOTE_DEVICE = "object/intangible/data_item/guild_stone.iff";
    public static final String STR_GUILD_MEMBERS_PROMPT = "@guild:members_prompt";
    public static final String STR_GUILD_MEMBERS_FILTER_PROMPT = "@guild:members_filter_prompt";
    public static final String STR_GUILD_MEMBERS_TITLE = "@guild:members_title";
    public static final String STR_GUILD_MEMBERS_NAME_PROMPT = "@guild:members_name_prompt";
    public static final String STR_GUILD_INFO_TITLE = "@guild:info_title";
    public static final String STR_GUILD_ADD_ENEMY = "@guild:add_enemy";
    public static final string_id GUILD_ENEMIES_PROMPT = new string_id("guild", "enemies_prompt");
    public static final String STR_GUILD_ENEMIES_TITLE = "@guild:enemies_title";
    public static final String STR_GUILD_WAR_ENEMY_NAME_PROMPT = "@guild:war_enemy_name_prompt";
    public static final String STR_GUILD_WAR_ENEMY_NAME_TITLE = "@guild:war_enemy_name_title";
    public static final String STR_GUILD_ACCEPT = "@guild:accept";
    public static final String STR_GUILD_DECLINE = "@guild:decline";
    public static final String STR_GUILD_SPONSORED_OPTIONS_PROMPT = "@guild:sponsored_options_prompt";
    public static final String STR_GUILD_SPONSORED_OPTIONS_TITLE = "@guild:sponsored_options_title";
    public static final String STR_GUILD_SPONSORED_PROMPT = "@guild:sponsored_prompt";
    public static final String STR_GUILD_SPONSORED_TITLE = "@guild:sponsored_title";
    public static final String STR_GUILD_NAMECHANGE_ABBREV_PROMPT = "@guild:namechange_abbrev_prompt";
    public static final String STR_GUILD_NAMECHANGE_ABBREV_TITLE = "@guild:namechange_abbrev_title";
    public static final String STR_GUILD_SPONSOR_VERIFY_PROMPT = "@guild:sponsor_verify_prompt";
    public static final String STR_GUILD_SPONSOR_VERIFY_TITLE = "@guild:sponsor_verify_title";
    public static final String STR_GUILD_SPONSOR_PROMPT = "@guild:sponsor_prompt";
    public static final String STR_GUILD_SPONSOR_TITLE = "@guild:sponsor_title";
    public static final String STR_GUILD_DISBAND_TITLE = "@guild:disband_title";
    public static final String STR_GUILD_DISBAND_PROMPT = "@guild:disband_prompt";
    public static final String STR_GUILD_NAMECHANGE_NAME_PROMPT = "@guild:namechange_name_prompt";
    public static final String STR_GUILD_NAMECHANGE_NAME_TITLE = "@guild:namechange_name_title";
    public static final String STR_GUILD_RANK_SUMMARY_PROMPT = "@guild:guild_rank_summary_prompt";
    public static final String REGISTERED_GUILD = "guild.guildId";
    public static final String GUILD_SCREEN_ID = "guild.guildScreenId";
    public static final String GUILD_SCREEN_TYPE = "guild.guildScreen.type";
    public static final String GUILD_SCREEN_TEMPLATE = "object/tangible/furniture/technical/guild_registry.iff";
    public static final String STR_GUILD_MEMBER_OPTIONS_PROMPT = "@guild:member_options_prompt";
    public static final String STR_GUILD_MEMBER_OPTIONS_TITLE = "@guild:member_options_title";
    public static final String STR_GUILD_MEMBER_RANK_PROMPT = "@guild:member_rank_prompt";
    public static final String STR_GUILD_MEMBER_RANK_TITLE = "@guild:member_rank_title";
    public static final String STR_GUILD_TITLE = "@guild:title";
    public static final String STR_GUILD_KICK = "@guild:kick";
    public static final String STR_GUILD_RANK = "@guild:rank";
    public static final String STR_GUILD_PERMISSIONS = "@guild:permissions";
    public static final String STR_GUILD_WAR_EXCLUDE_TOGGLE = "@guild:war_exclude_toggle";
    public static final String STR_GUILD_WAR_INCLUDE_TOGGLE = "@guild:war_include_toggle";
    public static final String STR_GUILD_TITLE_PROMPT = "@guild:title_prompt";
    public static final String STR_GUILD_TITLE_TITLE = "@guild:title_title";
    public static final String STR_GUILD_KICK_TITLE = "@guild:kick_title";
    public static final String STR_GUILD_KICK_PROMPT = "@guild:kick_prompt";
    public static final string_id SID_GUILD_PERMISSION_MAIL = new string_id("guild", "permission_mail");
    public static final string_id SID_GUILD_PERMISSION_SPONSOR = new string_id("guild", "permission_sponsor");
    public static final string_id SID_GUILD_PERMISSION_TITLE = new string_id("guild", "permission_title");
    public static final string_id SID_GUILD_PERMISSION_ACCEPT = new string_id("guild", "permission_accept");
    public static final string_id SID_GUILD_PERMISSION_KICK = new string_id("guild", "permission_kick");
    public static final string_id SID_GUILD_PERMISSION_WAR = new string_id("guild", "permission_war");
    public static final string_id SID_GUILD_PERMISSION_NAMECHANGE = new string_id("guild", "permission_namechange");
    public static final string_id SID_GUILD_PERMISSION_DISBAND = new string_id("guild", "permission_disband");
    public static final string_id SID_GUILD_PERMISSION_RANK = new string_id("guild", "rank");
    public static final string_id SID_GUILD_PERMISSION_WAR_EXCLUDE = new string_id("guild", "war_exclude");
    public static final string_id SID_GUILD_PERMISSION_WAR_INCLUDE = new string_id("guild", "war_include");
    public static final String STR_SUI_BACK_BUTTON = "@guild:back_button";
    public static final String[] MEMBER_FILTER_LIST = 
    {
        "@guild:member_filter_alpha",
        "@guild:member_filter_permission",
        "@guild:member_filter_title",
        "@guild:member_filter_name"
    };
    public static final String[] PERMISSION_LIST = 
    {
        STR_GUILD_PERMISSION_MAIL,
        STR_GUILD_PERMISSION_SPONSOR,
        STR_GUILD_PERMISSION_TITLE,
        STR_GUILD_PERMISSION_ACCEPT,
        STR_GUILD_PERMISSION_KICK,
        STR_GUILD_PERMISSION_WAR,
        STR_GUILD_PERMISSION_NAMECHANGE,
        STR_GUILD_PERMISSION_DISBAND
    };
    public static final string_id SID_GUILD_CREATE_FAIL_IN_GUILD = new string_id("guild", "create_fail_in_guild");
    public static final string_id SID_GUILD_CREATE_FAIL_NAME_BAD_LENGTH = new string_id("guild", "create_fail_name_bad_length");
    public static final string_id SID_GUILD_CREATE_FAIL_ABBREV_BAD_LENGTH = new string_id("guild", "create_fail_abbrev_bad_length");
    public static final string_id SID_GUILD_CREATE_FAIL_NAME_IN_USE = new string_id("guild", "create_fail_name_in_use");
    public static final string_id SID_GUILD_CREATE_FAIL_ABBREV_IN_USE = new string_id("guild", "create_fail_abbrev_in_use");
    public static final string_id SID_GUILD_CREATE_FAIL_NAME_NOT_ALLOWED = new string_id("guild", "create_fail_name_not_allowed");
    public static final string_id SID_GUILD_CREATE_FAIL_ABBREV_NOT_ALLOWED = new string_id("guild", "create_fail_abbrev_not_allowed");
    public static final string_id SID_GUILD_SPONSOR_FAIL_FULL = new string_id("guild", "sponsor_fail_full");
    public static final string_id SID_GUILD_SPONSOR_ALREADY_IN_GUILD = new string_id("guild", "sponsor_already_in_guild");
    public static final string_id SID_GUILD_TITLE_FAIL_BAD_LENGTH = new string_id("guild", "title_fail_bad_length");
    public static final string_id SID_GUILD_TITLE_FAIL_NOT_ALLOWED = new string_id("guild", "title_fail_not_allowed");
    public static final string_id SID_GUILD_GENERIC_FAIL_NO_PERMISSION = new string_id("guild", "generic_fail_no_permission");
    public static final string_id SID_GUILD_LEAVE_FAIL_LEADER_TRIED_TO_LEAVE = new string_id("guild", "leave_fail_leader_tried_to_leave");
    public static final string_id SID_GUILD_WAR_FAIL_NO_SUCH_GUILD = new string_id("guild", "war_fail_no_such_guild");
    public static final string_id SID_GUILD_KICK_SELF = new string_id("guild", "kick_self");
    public static final string_id SID_GUILD_KICK_TARGET = new string_id("guild", "kick_target");
    public static final string_id SID_GUILD_DECLINE_SELF = new string_id("guild", "decline_self");
    public static final string_id SID_GUILD_DECLINE_TARGET = new string_id("guild", "decline_target");
    public static final string_id SID_GUILD_SPONSOR_SELF = new string_id("guild", "sponsor_self");
    public static final string_id SID_GUILD_SPONSOR_TARGET = new string_id("guild", "sponsor_target");
    public static final string_id SID_GUILD_ACCEPT_SELF = new string_id("guild", "accept_self");
    public static final string_id SID_GUILD_ACCEPT_TARGET = new string_id("guild", "accept_target");
    public static final string_id SID_GUILD_LEAVE_SELF = new string_id("guild", "leave_self");
    public static final string_id SID_GUILD_TITLE_SELF = new string_id("guild", "title_self");
    public static final string_id SID_GUILD_TITLE_TARGET = new string_id("guild", "title_target");
    public static final string_id SID_GUILD_NAMECHANGE_FAIL_ABBREV_BAD_LENGTH = new string_id("guild", "namechange_fail_abbrev_bad_length");
    public static final string_id SID_GUILD_NAMECHANGE_FAIL_NAME_BAD_LENGTH = new string_id("guild", "namechange_fail_name_bad_length");
    public static final string_id SID_GUILD_NAMECHANGE_FAIL_ABBREV_NOT_ALLOWED = new string_id("guild", "namechange_fail_abbrev_not_allowed");
    public static final string_id SID_GUILD_NAMECHANGE_FAIL_NAME_NOT_ALLOWED = new string_id("guild", "namechange_fail_name_not_allowed");
    public static final string_id SID_GUILD_NAMECHANGE_SELF = new string_id("guild", "namechange_self");
    public static final string_id SID_GUILD_ALLEGIANCE_SELF = new string_id("guild", "allegiance_self");
    public static final string_id SID_GUILD_ALLEGIANCE_UNCHANGED_SELF = new string_id("guild", "allegiance_unchanged_self");
    public static final string_id SID_NO_FREE_TRIAL = new string_id("guild", "no_free_trial");
    public static final string_id SID_GUILD_SPONSOR_ACCEPT = new string_id("guild", "sponsor_accept");
    public static final string_id SID_GUILD_SPONSOR_DECLINE = new string_id("guild", "sponsor_decline");
    public static final string_id SID_GUILD_NO_DESTROY_REMOTE = new string_id("guild", "no_destroy_remote");
    public static final string_id SID_SUI_CANNOT_BACK_FURTHER = new string_id("guild", "no_back_further");
    public static final string_id SID_MAKE_NEW_SCREEN = new string_id("guild", "make_new_screen");
    public static final string_id SID_NO_TITLES_IN_GUILD = new string_id("guild", "no_titles_in_guild");
    public static final string_id SID_NO_CANDIDATES = new string_id("guild", "vote_no_candidates");
    public static final string_id SID_GUILD_WAR_EXCLUSION_TOGGLED = new string_id("guild", "sid_guild_war_exclusion_toggled");
    public static final string_id SID_GUILD_WAR_INCLUSION_TOGGLED = new string_id("guild", "sid_guild_war_inclusion_toggled");
    public static final string_id SID_COMPLETED_ELECTIONS_EMAIL_BODY_LEADER_SAME = new string_id("guild", "sid_completed_elections_email_body_leader_same");
    public static final string_id SID_COMPLETED_ELECTIONS_EMAIL_BODY_LEADER_CHANGED = new string_id("guild", "sid_completed_elections_email_body_leader_changed");
    public static final int MAX_MEMBERS_PER_GUILD = 500;
    public static final int[] PERMISSION_INTERFACE_FLAGS = 
    {
        0,
        GUILD_PERMISSION_SPONSOR,
        GUILD_PERMISSION_ACCEPT,
        GUILD_PERMISSION_KICK,
        GUILD_PERMISSION_MAIL,
        GUILD_PERMISSION_TITLE,
        GUILD_PERMISSION_DISBAND,
        GUILD_PERMISSION_NAMECHANGE,
        GUILD_PERMISSION_RANK,
        GUILD_PERMISSION_WAR,
        GUILD_PERMISSION_WAR_EXCLUSION,
        GUILD_PERMISSION_WAR_INCLUSION
    };
    public static final int INTERFACE_GUILD_UNKNOWN = 0;
    public static final int INTERFACE_GUILD_ROSTER = 1;
    public static final int INTERFACE_GUILD_RANK_LIST = 2;
    public static final int INTERFACE_GUILD_RANK_SUMMARY = 3;
    public static final int INTERFACE_GUILD_PERMISSION_LIST = 4;
    public static final int INTERFACE_GUILD_ELECTION = 5;
    public static final int INTERFACE_GUILD_WAR_PREFERENCES = 6;
    public static String resolveGuildName(int guildId) throws InterruptedException
    {
        if (guildId != 0)
        {
            String guildName = guildGetName(guildId);
            if (guildName != null && !guildName.equals(""))
            {
                return guildName;
            }
            return "unknown";
        }
        return "none";
    }
    public static String resolveMemberName(int guildId, obj_id who) throws InterruptedException
    {
        if (isIdValid(who))
        {
            
            {
                String s = getName(who);
                if (s != null && !s.equals(""))
                {
                    return s;
                }
            }
            
            {
                String s = guildGetMemberName(guildId, who);
                if (s != null && !s.equals(""))
                {
                    return s;
                }
            }
            return "unknown";
        }
        return "none";
    }
    public static void GuildLog(int guildId, String command, obj_id actor, obj_id target, String description) throws InterruptedException
    {
        String guildDesc = resolveGuildName(guildId);
        String actorDesc = resolveMemberName(guildId, actor);
        CustomerServiceLog("Guild", "[command=" + command + "] [guild=" + guildId + "(" + guildDesc + ")] [actor=" + actor + "(" + resolveMemberName(guildId, actor) + ")] [target=" + target + "(" + resolveMemberName(guildId, target) + ")] - " + description);
    }
    public static String buildFakeLocalizedProse(String rawText, String substitute1, String substitute2) throws InterruptedException
    {
        if (rawText.startsWith("@"))
        {
            String cookedText = new String(rawText);
            string_id id = new string_id(rawText.substring(1, rawText.indexOf(":")), rawText.substring(rawText.indexOf(":") + 1, rawText.length()));
            cookedText = localize(id);
            int pos1 = cookedText.indexOf("%TU");
            if (pos1 != -1)
            {
                cookedText = cookedText.substring(0, pos1) + substitute1 + cookedText.substring(pos1 + 3, cookedText.length());
            }
            int pos2 = cookedText.indexOf("%TT");
            if (pos2 != -1)
            {
                cookedText = cookedText.substring(0, pos2) + substitute2 + cookedText.substring(pos2 + 3, cookedText.length());
            }
            return cookedText;
        }
        else 
        {
            return rawText;
        }
    }
    public static boolean hasGuildPermission(int guildId, obj_id who, int permission) throws InterruptedException
    {
        if (guildId == 0)
        {
            return false;
        }
        if (!isIdValid(who))
        {
            return false;
        }
        if (isGod(who) && (permission & (~GUILD_PERMISSIONS_ALL_ADMINISTRATIVE)) == 0)
        {
            return true;
        }
        if ((guildGetMemberPermissions(guildId, who) & permission) == permission)
        {
            return true;
        }
        return false;
    }
    public static boolean hasDeclaredWarAgainst(int guildId, int enemyId) throws InterruptedException
    {
        int[] enemies = guildGetEnemies(guildId);
        if (enemies != null)
        {
            for (int i = 0; i < enemies.length; ++i)
            {
                if (enemies[i] == enemyId)
                {
                    return true;
                }
            }
        }
        return false;
    }
    public static int create(obj_id actor, String guildName, String guildAbbrev) throws InterruptedException
    {
        if (isFreeTrialAccount(actor))
        {
            sendSystemMessage(actor, SID_NO_FREE_TRIAL);
            return 0;
        }
        if (getGuildId(actor) != 0)
        {
            sendSystemMessage(actor, SID_GUILD_CREATE_FAIL_IN_GUILD);
        }
        else if (guildAbbrev.length() < 1 || guildAbbrev.length() > 5)
        {
            sendSystemMessage(actor, SID_GUILD_CREATE_FAIL_ABBREV_BAD_LENGTH);
        }
        else if (guildName.length() < 1 || guildName.length() > 25)
        {
            sendSystemMessage(actor, SID_GUILD_CREATE_FAIL_NAME_BAD_LENGTH);
        }
        else if (findGuild(guildAbbrev) != 0)
        {
            sendSystemMessage(actor, SID_GUILD_CREATE_FAIL_ABBREV_IN_USE);
        }
        else if (findGuild(guildName) != 0)
        {
            sendSystemMessage(actor, SID_GUILD_CREATE_FAIL_NAME_IN_USE);
        }
        else if (isNameReserved(guildAbbrev))
        {
            sendSystemMessage(actor, SID_GUILD_CREATE_FAIL_ABBREV_NOT_ALLOWED);
        }
        else if (isNameReserved(guildName))
        {
            sendSystemMessage(actor, SID_GUILD_CREATE_FAIL_NAME_NOT_ALLOWED);
        }
        else 
        {
            int guildId = createGuild(guildName, guildAbbrev);
            if (guildId != 0)
            {
                GuildLog(guildId, "create", actor, null, "guildName='" + guildName + "' guildAbbrev='" + guildAbbrev + "'");
                guildAddCreatorMember(guildId, actor);
                guildSetLeader(guildId, actor);
                return guildId;
            }
            return 0;
        }
        return 0;
    }
    public static void changeLeader(int guildId, obj_id newLeader) throws InterruptedException
    {
        obj_id oldLeader = guildGetLeader(guildId);
        if (oldLeader != newLeader && isIdValid(newLeader))
        {
            GuildLog(guildId, "changeLeader", null, newLeader, "");
            mailToGuild(guildId, GUILD_MAIL_LEADERCHANGE_SUBJECT, GUILD_MAIL_LEADERCHANGE_TEXT, guildGetMemberName(guildId, newLeader));
            obj_id[] memberIds = getMemberIds(guildId, false, true);
            if (findObjIdTableOffset(memberIds, oldLeader) != -1)
            {
                guildSetMemberPermissionAndAllegiance(guildId, oldLeader, ((guildGetMemberPermissions(guildId, oldLeader) & (~GUILD_PERMISSIONS_ALL_ADMINISTRATIVE)) | GUILD_PERMISSION_MEMBER) & (~GUILD_PERMISSION_ELECTION_CANDIDATE), null);
            }
            guildSetLeader(guildId, newLeader);
            if (findObjIdTableOffset(memberIds, newLeader) != -1)
            {
                guildSetMemberPermissionAndAllegiance(guildId, newLeader, (guildGetMemberPermissions(guildId, newLeader) | GUILD_PERMISSIONS_ALL_ADMINISTRATIVE) & (~GUILD_PERMISSION_ELECTION_CANDIDATE), null);
            }
            obj_id oldLeaderPDA = getGuildRemoteDevice(oldLeader);
            obj_id newLeaderPDA = getGuildRemoteDevice(newLeader);
            if (isIdValid(oldLeaderPDA) && isIdValid(newLeaderPDA))
            {
                if (hasObjVar(oldLeaderPDA, GUILD_SCREEN_ID))
                {
                    obj_id guildScreen = getObjIdObjVar(oldLeaderPDA, GUILD_SCREEN_ID);
                    if (isIdValid(guildScreen))
                    {
                        messageTo(guildScreen, "handlerGuildNewLeader", null, 1, true);
                        if (hasObjVar(newLeaderPDA, GUILD_SCREEN_ID))
                        {
                            removeObjVar(newLeaderPDA, GUILD_SCREEN_ID);
                        }
                    }
                }
            }
        }
    }
    public static void disbandForNotEnoughMembers(int guildId) throws InterruptedException
    {
        GuildLog(guildId, "disbandForNotEnoughMembers", null, null, "");
        mailToGuild(guildId, GUILD_MAIL_DISBAND_SUBJECT, GUILD_MAIL_DISBAND_NOT_ENOUGH_MEMBERS_TEXT);
        disbandGuild(guildId);
    }
    public static void disband(int guildId, obj_id actor) throws InterruptedException
    {
        if (!hasGuildPermission(guildId, actor, GUILD_PERMISSION_DISBAND))
        {
            sendSystemMessage(actor, SID_GUILD_GENERIC_FAIL_NO_PERMISSION);
        }
        else 
        {
            GuildLog(guildId, "disband", actor, null, "");
            mailToGuild(guildId, GUILD_MAIL_DISBAND_SUBJECT, GUILD_MAIL_DISBAND_TEXT, getName(actor));
            disbandGuild(guildId);
        }
    }
    public static void sponsor(int guildId, obj_id actor, String who) throws InterruptedException
    {
        obj_id target = utils.getNearbyPlayerByName(actor, who);
        if (!isIdValid(target))
        {
            target = getPlayerIdFromFirstName(who);
        }
        if (isIdValid(target) && isIdValid(actor))
        {
            if (getGuildId(target) != 0)
            {
                dictionary dict = new dictionary();
                dict.put("proseSID", SID_GUILD_SPONSOR_ALREADY_IN_GUILD);
                dict.put("player", who);
                messageTo(actor, "onGuildSponsorVerifyResponseProse", dict, 0, false);
            }
            else 
            {
                obj_id[] members = guildGetMemberIds(guildId);
                if (members.length >= MAX_MEMBERS_PER_GUILD)
                {
                    dictionary dict = new dictionary();
                    dict.put("stringMessage", SID_GUILD_SPONSOR_FAIL_FULL);
                    dict.put("player", who);
                    messageTo(actor, "onGuildSponsorVerifyResponse", dict, 0, false);
                }
                else 
                {
                    GuildLog(guildId, "sponsor", actor, target, "");
                    dictionary dict = new dictionary();
                    dict.put("proseSID", SID_GUILD_SPONSOR_SELF);
                    dict.put("player", who);
                    dict.put("guildId", guildId);
                    messageTo(actor, "onGuildSponsorVerifyResponseProse", dict, 0, false);
                    dictionary dict2 = new dictionary();
                    dict2.put("proseSID", SID_GUILD_SPONSOR_TARGET);
                    dict2.put("player", guildGetMemberName(guildId, actor));
                    dict2.put("guildId", guildId);
                    messageTo(target, "onGuildSponsorVerifyResponseProse", dict2, 0, false);
                    obj_id leader = guildGetLeader(guildId);
                    if (isIdValid(leader))
                    {
                        mailToGuildMember(guildId, leader, GUILD_MAIL_SPONSOR_SUBJECT, GUILD_MAIL_SPONSOR_TEXT, getName(actor), who);
                    }
                    guildAddSponsorMember(guildId, target);
                }
            }
        }
    }
    public static void accept(int guildId, obj_id actor, String who) throws InterruptedException
    {
        if (!isIdValid(actor))
        {
            return;
        }
        if (!hasGuildPermission(guildId, actor, GUILD_PERMISSION_ACCEPT))
        {
            sendSystemMessage(actor, SID_GUILD_GENERIC_FAIL_NO_PERMISSION);
        }
        else 
        {
            obj_id memberId = findMemberIdByName(guildId, who, true, false);
            if (isIdValid(memberId))
            {
                GuildLog(guildId, "accept", actor, memberId, "");
                String memberName = guildGetMemberName(guildId, memberId);
                dictionary dict = new dictionary();
                dict.put("proseSID", SID_GUILD_ACCEPT_SELF);
                dict.put("player", who);
                dict.put("guildId", guildId);
                messageTo(actor, "onGuildSponsorVerifyResponseProse", dict, 0, false);
                dictionary dict2 = new dictionary();
                dict2.put("proseSID", SID_GUILD_ACCEPT_TARGET);
                String name = guildGetMemberName(guildId, actor);
                dict2.put("player", name);
                dict2.put("guildId", guildId);
                messageTo(memberId, "onGuildSponsorVerifyResponseProse", dict2, 0, false);
                mailToGuild(guildId, GUILD_MAIL_ACCEPT_SUBJECT, GUILD_MAIL_ACCEPT_TEXT, getName(actor), memberName);
                guildSetMemberPermissionAndAllegiance(guildId, memberId, GUILD_PERMISSION_MEMBER, guildGetLeader(guildId));
                mailToPerson(guildId, memberName, GUILD_MAIL_ACCEPT_TARGET_SUBJECT, GUILD_MAIL_ACCEPT_TARGET_TEXT, getName(actor), guildGetName(guildId));
                messageTo(memberId, "onGuildCreateTerminalDataObject", null, 0, false);
            }
        }
    }
    public static void leave(obj_id actor) throws InterruptedException
    {
        int guildId = getGuildId(actor);
        if (guildId != 0)
        {
            if (actor == guildGetLeader(guildId))
            {
                sendSystemMessage(actor, SID_GUILD_LEAVE_FAIL_LEADER_TRIED_TO_LEAVE);
            }
            else 
            {
                GuildLog(guildId, "leave", actor, null, "");
                prose_package pp = new prose_package();
                pp.actor.set(guildGetName(guildId));
                pp.stringId = SID_GUILD_LEAVE_SELF;
                sendSystemMessageProse(getChatName(actor), pp);
                mailToGuild(guildId, GUILD_MAIL_LEAVE_SUBJECT, GUILD_MAIL_LEAVE_TEXT, getName(actor));
                guildRemoveMember(guildId, actor);
            }
        }
    }
    public static void kick(int guildId, obj_id actor, String who) throws InterruptedException
    {
        if (guildId == 0)
        {
            return;
        }
        obj_id memberId = findMemberIdByName(guildId, who, false, true);
        if (isIdValid(memberId))
        {
            if (memberId == actor)
            {
                leave(actor);
            }
            else if (!hasGuildPermission(guildId, actor, GUILD_PERMISSION_KICK) || (!isGod(actor) && memberId == guildGetLeader(guildId)))
            {
                sendSystemMessage(actor, SID_GUILD_GENERIC_FAIL_NO_PERMISSION);
            }
            else 
            {
                GuildLog(guildId, "kick", actor, memberId, "removing member");
                String memberName = guildGetMemberName(guildId, memberId);
                dictionary dict = new dictionary();
                dict.put("proseSID", SID_GUILD_KICK_SELF);
                dict.put("player", memberName);
                dict.put("guildId", guildId);
                messageTo(actor, "onGuildSponsorVerifyResponseProse", dict, 0, false);
                dictionary dict2 = new dictionary();
                dict2.put("proseSID", SID_GUILD_KICK_TARGET);
                dict2.put("player", guildGetMemberName(guildId, actor));
                dict2.put("guildId", guildId);
                messageTo(memberId, "onGuildSponsorVerifyResponseProse", dict2, 0, false);
                mailToGuild(guildId, GUILD_MAIL_KICK_SUBJECT, GUILD_MAIL_KICK_TEXT, getName(actor), memberName);
                guildRemoveMember(guildId, memberId);
            }
        }
        else 
        {
            obj_id sponsoredId = findMemberIdByName(guildId, who, true, false);
            if (isIdValid(sponsoredId))
            {
                if (!hasGuildPermission(guildId, actor, GUILD_PERMISSION_ACCEPT))
                {
                    sendSystemMessage(actor, SID_GUILD_GENERIC_FAIL_NO_PERMISSION);
                }
                else 
                {
                    GuildLog(guildId, "kick", actor, memberId, "removing sponsored");
                    String memberName = guildGetMemberName(guildId, sponsoredId);
                    dictionary dict = new dictionary();
                    dict.put("proseSID", SID_GUILD_DECLINE_SELF);
                    dict.put("player", memberName);
                    dict.put("guildId", guildId);
                    messageTo(actor, "onGuildSponsorVerifyResponseProse", dict, 0, false);
                    dictionary dict2 = new dictionary();
                    dict2.put("proseSID", SID_GUILD_DECLINE_TARGET);
                    dict2.put("player", guildGetMemberName(guildId, actor));
                    dict2.put("guildId", guildId);
                    messageTo(sponsoredId, "onGuildSponsorVerifyResponseProse", dict2, 0, false);
                    mailToGuild(guildId, GUILD_MAIL_DECLINE_SUBJECT, GUILD_MAIL_DECLINE_TEXT, getName(actor), memberName);
                    guildRemoveMember(guildId, sponsoredId);
                    mailToPerson(guildId, memberName, GUILD_MAIL_DECLINE_TARGET_SUBJECT, GUILD_MAIL_DECLINE_TARGET_TEXT, guildGetMemberName(guildId, actor), guildGetName(guildId));
                }
            }
        }
    }
    public static void title(int guildId, obj_id actor, String who, String title) throws InterruptedException
    {
        if (!hasGuildPermission(guildId, actor, GUILD_PERMISSION_TITLE))
        {
            sendSystemMessage(actor, SID_GUILD_GENERIC_FAIL_NO_PERMISSION);
        }
        else 
        {
            obj_id memberId = findMemberIdByName(guildId, who, false, true);
            if (isIdValid(memberId))
            {
                if (isNameReserved(title))
                {
                    sendSystemMessage(actor, SID_GUILD_TITLE_FAIL_NOT_ALLOWED);
                }
                else if (title.length() > 25)
                {
                    sendSystemMessage(actor, SID_GUILD_TITLE_FAIL_BAD_LENGTH);
                }
                else 
                {
                    GuildLog(guildId, "title", actor, memberId, "setting title to '" + title + "'");
                    String memberName = guildGetMemberName(guildId, memberId);
                    dictionary dict = new dictionary();
                    dict.put("proseSID", SID_GUILD_TITLE_SELF);
                    dict.put("title", title);
                    dict.put("player", memberName);
                    messageTo(actor, "onGuildSponsorVerifyResponseProse", dict, 0, false);
                    dictionary dict2 = new dictionary();
                    dict2.put("proseSID", SID_GUILD_TITLE_TARGET);
                    dict2.put("player", guildGetMemberName(guildId, actor));
                    dict2.put("title", title);
                    messageTo(memberId, "onGuildSponsorVerifyResponseProse", dict2, 0, false);
                    guildSetMemberTitle(guildId, memberId, title);
                }
            }
        }
    }
    public static void handleGuildNameChange(int guildId, String newName, String newAbbrev, obj_id changerId) throws InterruptedException
    {
        if (isIdValid(changerId) && !hasGuildPermission(guildId, changerId, GUILD_PERMISSION_NAMECHANGE))
        {
            return;
        }
        if (findGuild(newName) != 0 || findGuild(newAbbrev) != 0)
        {
            return;
        }
        GuildLog(guildId, "handleGuildNameChange", changerId, null, "name='" + newName + "' abbrev='" + newAbbrev + "'");
        guildSetName(guildId, newName);
        guildSetAbbrev(guildId, newAbbrev);
        String memberName = null;
        if (isIdValid(changerId))
        {
            memberName = guildGetMemberName(guildId, changerId);
        }
        if (memberName != null)
        {
            mailToGuild(guildId, GUILD_MAIL_NAMECHANGE_SUBJECT, GUILD_MAIL_NAMECHANGE_TEXT, newName, newAbbrev, memberName);
        }
        else 
        {
            mailToGuild(guildId, GUILD_MAIL_NAMECHANGE_SUBJECT, GUILD_MAIL_NAMECHANGE_TEXT, newName, newAbbrev, "System");
        }
    }
    public static void nameChange(int guildId, obj_id actor, String newName, String newAbbrev) throws InterruptedException
    {
        if (!hasGuildPermission(guildId, actor, GUILD_PERMISSION_NAMECHANGE))
        {
            sendSystemMessage(actor, SID_GUILD_GENERIC_FAIL_NO_PERMISSION);
        }
        else 
        {
            if (newAbbrev.length() < 1 || newAbbrev.length() > 5)
            {
                sendSystemMessage(actor, SID_GUILD_NAMECHANGE_FAIL_ABBREV_BAD_LENGTH);
            }
            else if (newName.length() < 1 || newName.length() > 25)
            {
                sendSystemMessage(actor, SID_GUILD_NAMECHANGE_FAIL_NAME_BAD_LENGTH);
            }
            else if (isNameReserved(newAbbrev))
            {
                sendSystemMessage(actor, SID_GUILD_NAMECHANGE_FAIL_ABBREV_NOT_ALLOWED);
            }
            else if (isNameReserved(newName))
            {
                sendSystemMessage(actor, SID_GUILD_NAMECHANGE_FAIL_NAME_NOT_ALLOWED);
            }
            else 
            {
                GuildLog(guildId, "nameChange", actor, null, "queueing change to name='" + newName + "' abbrev='" + newAbbrev + "'");
                prose_package pp = new prose_package();
                pp.actor.set(newName);
                pp.target.set(newAbbrev);
                pp.stringId = SID_GUILD_NAMECHANGE_SELF;
                sendSystemMessageProse(getChatName(actor), pp);
                obj_id masterGuildObj = getMasterGuildObject();
                dictionary params = new dictionary();
                params.put("guildId", guildId);
                params.put("newName", newName);
                params.put("newAbbrev", newAbbrev);
                if (getGuildId(actor) == guildId)
                {
                    params.put("changerId", actor);
                }
                else 
                {
                    params.put("changerId", obj_id.NULL_ID);
                }
                messageTo(masterGuildObj, "initiateGuildNameChange", params, 0, false);
            }
        }
    }
    public static void war(int guildId, obj_id actor, String who) throws InterruptedException
    {
        int enemyId = findGuild(who);
        if (enemyId == 0)
        {
            sendSystemMessage(actor, SID_GUILD_WAR_FAIL_NO_SUCH_GUILD);
        }
        else 
        {
            war(guildId, actor, enemyId);
        }
    }
    public static void war(int guildId, obj_id actor, int enemyId) throws InterruptedException
    {
        if (enemyId != 0 && enemyId != guildId)
        {
            if (!hasGuildPermission(guildId, actor, GUILD_PERMISSION_WAR))
            {
                sendSystemMessage(actor, SID_GUILD_GENERIC_FAIL_NO_PERMISSION);
            }
            else 
            {
                GuildLog(guildId, "war", actor, null, "Declaring war with " + enemyId + "(" + guildGetName(enemyId) + ")");
                guildSetEnemy(guildId, enemyId);
            }
        }
    }
    public static void peace(int guildId, obj_id actor, String who) throws InterruptedException
    {
        peace(guildId, actor, findGuild(who));
    }
    public static void peace(int guildId, obj_id actor, int enemyId) throws InterruptedException
    {
        if (enemyId != 0 && enemyId != guildId)
        {
            if (!hasGuildPermission(guildId, actor, GUILD_PERMISSION_WAR))
            {
                sendSystemMessage(actor, SID_GUILD_GENERIC_FAIL_NO_PERMISSION);
            }
            else 
            {
                GuildLog(guildId, "war", actor, null, "Declaring peace with " + enemyId + "(" + guildGetName(enemyId) + ")");
                guildRemoveEnemy(guildId, enemyId);
            }
        }
    }
    public static void togglePersonalPermission(int guildId, obj_id actor, String who, int permBit) throws InterruptedException
    {
        obj_id memberId = findMemberIdByName(guildId, who, false, true);
        if ((isIdValid(memberId) && memberId == actor && (permBit & GUILD_PERMISSIONS_ALL_PERSONAL) > 0) || isGod(actor) || guildGetLeader(guildId) == actor)
        {
            GuildLog(guildId, "togglePermission", actor, memberId, "bit " + permBit);
            guildSetMemberPermission(guildId, memberId, guildGetMemberPermissions(guildId, memberId) ^ permBit);
        }
    }
    public static void togglePermission(int guildId, obj_id actor, String who, int permBit) throws InterruptedException
    {
        if (guildGetLeader(guildId) == actor || isGod(actor))
        {
            obj_id memberId = findMemberIdByName(guildId, who, false, true);
            if (isIdValid(memberId))
            {
                GuildLog(guildId, "togglePermission", actor, memberId, "bit " + permBit);
                guildSetMemberPermission(guildId, memberId, guildGetMemberPermissions(guildId, memberId) ^ permBit);
            }
        }
    }
    public static void dumpInfo(int guildId, obj_id actor) throws InterruptedException
    {
        debugConsoleMsg(actor, "Guild Id: " + guildId);
        if (guildId == 0)
        {
            return;
        }
        debugConsoleMsg(actor, "Guild Name: " + guildGetName(guildId));
        debugConsoleMsg(actor, "Guild Abbrev: " + guildGetAbbrev(guildId));
        debugConsoleMsg(actor, "Guild Leader: " + guildGetLeader(guildId));
        obj_id[] members = guildGetMemberIds(guildId);
        int memberCount = 0;
        if (members != null)
        {
            memberCount = members.length;
        }
        debugConsoleMsg(actor, "Guild members (" + memberCount + "):");
        for (int i = 0; i < memberCount; ++i)
        {
            debugConsoleMsg(actor, (i + 1) + ")" + " id = " + members[i] + " name = " + guildGetMemberName(guildId, members[i]) + " perms = " + guildGetMemberPermissions(guildId, members[i]) + " title = " + guildGetMemberTitle(guildId, members[i]) + " allegiance = " + guildGetMemberAllegiance(guildId, members[i]));
        }
        int[] enemies = guildGetEnemies(guildId);
        int enemyCount = 0;
        if (enemies != null)
        {
            enemyCount = enemies.length;
        }
        debugConsoleMsg(actor, "Guild enemies (" + enemyCount + "):");
        for (int i = 0; i < enemyCount; ++i)
        {
            debugConsoleMsg(actor, (i + 1) + ") " + enemies[i]);
        }
    }
    public static obj_id findMemberIdByName(int guildId, String name, boolean fromSponsored, boolean fromMembers) throws InterruptedException
    {
        obj_id[] members = getMemberIds(guildId, fromSponsored, fromMembers);
        if (members != null && members.length > 0)
        {
            java.util.StringTokenizer st = new java.util.StringTokenizer(name);
            if(st != null && st.hasMoreTokens()) {
                String compareName = toLower(st.nextToken());
                for (int i = 0; i < members.length; ++i) {
                    if (isIdValid(members[i])) {
                        java.util.StringTokenizer st2 = new java.util.StringTokenizer(guildGetMemberName(guildId, members[i]));
                        String memberName = toLower(st2.nextToken());
                        if (compareName.equals(memberName)) {
                            return members[i];
                        }
                    }
                }
            }
        }
        return obj_id.NULL_ID;
    }
    public static boolean hasSponsoredMembers(int guildId) throws InterruptedException
    {
        obj_id[] members = guildGetMemberIds(guildId);
        if (members != null)
        {
            for (int i = 0; i < members.length; ++i)
            {
                if (!isIdValid(members[i]))
                {
                    return false;
                }
                if (guildGetMemberPermissions(guildId, members[i]) == GUILD_PERMISSIONS_NONE)
                {
                    return true;
                }
            }
        }
        return false;
    }
    public static obj_id[] getMemberIds(int guildId, boolean fromSponsored, boolean fromMembers) throws InterruptedException
    {
        if (guildId == 0)
        {
            return null;
        }
        obj_id[] members = guildGetMemberIds(guildId);
        int count = 0;
        if (members != null && members.length > 0)
        {
            if (fromSponsored && fromMembers)
            {
                count = members.length;
            }
            else 
            {
                for (int i = 0; i < members.length; ++i)
                {
                    if (!isIdValid(members[i]))
                    {
                        LOG("GUILD", "getMemberIds::1::members[" + i + "] was null");
                        LOG("GUILD", "getMemberIds::1::guildId " + guildId);
                        continue;
                    }
                    if (guildGetMemberPermissions(guildId, members[i]) == GUILD_PERMISSIONS_NONE)
                    {
                        if (fromSponsored)
                        {
                            ++count;
                        }
                    }
                    else if (fromMembers)
                    {
                        ++count;
                    }
                }
            }
        }
        obj_id[] ret = new obj_id[count];
        int index = 0;
        members = guildGetMemberIds(guildId);
        if (members != null && members.length > 0)
        {
            for (int i = 0; i < members.length; ++i)
            {
                if (!isIdValid(members[i]))
                {
                    LOG("GUILD", "getMemberIds::2::members[" + i + "] was null");
                    LOG("GUILD", "getMemberIds::2::guildId " + guildId);
                    continue;
                }
                if (guildGetMemberPermissions(guildId, members[i]) == GUILD_PERMISSIONS_NONE)
                {
                    if (fromSponsored)
                    {
                        ret[index++] = members[i];
                    }
                }
                else if (fromMembers)
                {
                    ret[index++] = members[i];
                }
            }
        }
        return ret;
    }
    public static String[] getMemberNames(int guildId, boolean fromSponsored, boolean fromMembers) throws InterruptedException
    {
        obj_id[] members = getMemberIds(guildId, fromSponsored, fromMembers);
        int count = 0;
        if (members != null)
        {
            count = members.length;
        }
        String[] ret = new String[count];
        for (int i = 0; i < count; ++i)
        {
            if (isIdValid(members[i]))
            {
                String name = guildGetMemberName(guildId, members[i]);
                if (name != null && !name.equals(""))
                {
                    ret[i] = guildGetMemberName(guildId, members[i]);
                }
            }
        }
        if (ret != null && ret.length > 0)
        {
            Arrays.sort(ret);
        }
        return ret;
    }
    public static String[] getMemberNamesAndTitles(int guildId) throws InterruptedException
    {
        obj_id[] members = getMemberIds(guildId, false, true);
        int count = 0;
        if (members != null)
        {
            count = members.length;
        }
        String[] ret = new String[count];
        for (int i = 0; i < count; ++i)
        {
            String name = guildGetMemberName(guildId, members[i]);
            String title = guildGetMemberTitle(guildId, members[i]);
            if (title.equals(""))
            {
                ret[i] = name;
            }
            else 
            {
                ret[i] = name + " [" + title + "]";
            }
        }
        Arrays.sort(ret);
        return ret;
    }
    public static String[] getEnemyNamesAndAbbrevs(int guildId) throws InterruptedException
    {
        int[] enemies_A_to_B = guildGetEnemies(guildId);
        int[] enemies_B_to_A = getGuildsAtWarWith(guildId);
        int count = 0;
        if (enemies_A_to_B != null)
        {
            count = enemies_A_to_B.length;
        }
        if (enemies_B_to_A != null)
        {
            for (int i = 0; i < enemies_B_to_A.length; ++i)
            {
                if (findIntTableOffset(enemies_A_to_B, enemies_B_to_A[i]) == -1)
                {
                    ++count;
                }
            }
        }
        String[] ret = new String[count];
        int pos = 0;
        if (enemies_A_to_B != null)
        {
            for (int i = 0; i < enemies_A_to_B.length; ++i)
            {
                boolean atWarWith = false;
                StringBuffer sb = new StringBuffer();
                if (findIntTableOffset(enemies_B_to_A, enemies_A_to_B[i]) == -1)
                {
                    sb.append("> ");
                    atWarWith = false;
                }
                else 
                {
                    sb.append("= ");
                    atWarWith = true;
                }
                sb.append(guildGetName(enemies_A_to_B[i]));
                sb.append(" <");
                sb.append(guildGetAbbrev(enemies_A_to_B[i]));
                sb.append(">");
                if (atWarWith)
                {
                    sb.append(" (");
                    sb.append(guildGetCountMembersGuildWarPvPEnabled(enemies_A_to_B[i]));
                    sb.append(")");
                    int killCountAtoB = guildGetGuildWarKillCount(guildId, enemies_A_to_B[i]);
                    int killCountAtoBupdateTime = guildGetGuildWarKillCountUpdateTime(guildId, enemies_A_to_B[i]);
                    int killCountBtoA = guildGetGuildWarKillCount(enemies_A_to_B[i], guildId);
                    int killCountBtoAupdateTime = guildGetGuildWarKillCountUpdateTime(enemies_A_to_B[i], guildId);
                    sb.append(" (");
                    sb.append(killCountAtoB);
                    sb.append(" kills vs ");
                    sb.append(killCountBtoA);
                    sb.append(" kills");
                    if ((killCountAtoBupdateTime > 0) || (killCountBtoAupdateTime > 0))
                    {
                        sb.append(" - last updated ");
                        sb.append(getCalendarTimeStringLocal(Math.max(killCountAtoBupdateTime, killCountBtoAupdateTime)));
                    }
                    sb.append(")");
                }
                ret[pos] = sb.toString();
                ++pos;
            }
        }
        if (enemies_B_to_A != null)
        {
            for (int i = 0; i < enemies_B_to_A.length; ++i)
            {
                if (findIntTableOffset(enemies_A_to_B, enemies_B_to_A[i]) == -1)
                {
                    StringBuffer sb = new StringBuffer();
                    sb.append("< ");
                    sb.append(guildGetName(enemies_B_to_A[i]));
                    sb.append(" <");
                    sb.append(guildGetAbbrev(enemies_B_to_A[i]));
                    sb.append(">");
                    sb.append(" (");
                    sb.append(guildGetCountMembersGuildWarPvPEnabled(enemies_B_to_A[i]));
                    sb.append(")");
                    ret[pos] = sb.toString();
                    ++pos;
                }
            }
        }
        return ret;
    }
    public static int[] getEnemyIds(int guildId) throws InterruptedException
    {
        int[] enemies_A_to_B = guildGetEnemies(guildId);
        int[] enemies_B_to_A = getGuildsAtWarWith(guildId);
        int count = 0;
        if (enemies_A_to_B != null)
        {
            count = enemies_A_to_B.length;
        }
        if (enemies_B_to_A != null)
        {
            for (int i = 0; i < enemies_B_to_A.length; ++i)
            {
                if (findIntTableOffset(enemies_A_to_B, enemies_B_to_A[i]) == -1)
                {
                    ++count;
                }
            }
        }
        int[] ret = new int[count];
        int pos = 0;
        if (enemies_A_to_B != null)
        {
            for (int i = 0; i < enemies_A_to_B.length; ++i)
            {
                ret[pos++] = enemies_A_to_B[i];
            }
        }
        if (enemies_B_to_A != null)
        {
            for (int i = 0; i < enemies_B_to_A.length; ++i)
            {
                if (findIntTableOffset(enemies_A_to_B, enemies_B_to_A[i]) == -1)
                {
                    ret[pos++] = enemies_B_to_A[i];
                }
            }
        }
        return ret;
    }
    public static void mailToGuild(int guildId, String subject, string_id textId, String substitute1, String substitute2, String substitute3) throws InterruptedException
    {
        obj_id[] members = getMemberIds(guildId, false, true);
        if (members != null)
        {
            for (int i = 0; i < members.length; ++i)
            {
                mailToGuildMember(guildId, members[i], subject, textId, substitute1, substitute2, substitute3);
            }
        }
    }
    public static void mailToGuild(int guildId, String subject, string_id textId, String substitute1, String substitute2) throws InterruptedException
    {
        mailToGuild(guildId, subject, textId, substitute1, substitute2, "");
    }
    public static void mailToGuild(int guildId, String subject, string_id textId, String substitute1) throws InterruptedException
    {
        mailToGuild(guildId, subject, textId, substitute1, "", "");
    }
    public static void mailToGuild(int guildId, String subject, string_id textId) throws InterruptedException
    {
        mailToGuild(guildId, subject, textId, "", "", "");
    }
    public static void mailToPerson(int guildId, String toName, String subject, string_id textId, String substitute1, String substitute2, String substitute3) throws InterruptedException
    {
        prose_package pp = new prose_package();
        pp.stringId = textId;
        pp.actor.set(substitute1);
        pp.target.set(substitute2);
        pp.other.set(substitute3);
        chatSendPersistentMessage(guildGetName(guildId), toName, subject, null, chatMakePersistentMessageOutOfBandBody(null, pp));
    }
    public static void mailToPerson(int guildId, String toName, String subject, string_id textId, String substitute1, String substitute2) throws InterruptedException
    {
        mailToPerson(guildId, toName, subject, textId, substitute1, substitute2, "");
    }
    public static void mailToPerson(int guildId, String toName, String subject, string_id textId, String substitute1) throws InterruptedException
    {
        mailToPerson(guildId, toName, subject, textId, substitute1, "", "");
    }
    public static void mailToPerson(int guildId, String toName, String subject, string_id textId) throws InterruptedException
    {
        mailToPerson(guildId, toName, subject, textId, "", "", "");
    }
    public static void mailToGuildMember(int guildId, obj_id memberId, String subject, string_id textId, String substitute1, String substitute2, String substitute3) throws InterruptedException
    {
        mailToPerson(guildId, guildGetMemberName(guildId, memberId), subject, textId, substitute1, substitute2, substitute3);
    }
    public static void mailToGuildMember(int guildId, obj_id memberId, String subject, string_id textId, String substitute1, String substitute2) throws InterruptedException
    {
        mailToGuildMember(guildId, memberId, subject, textId, substitute1, substitute2, "");
    }
    public static void mailToGuildMember(int guildId, obj_id memberId, String subject, string_id textId, String substitute1) throws InterruptedException
    {
        mailToGuildMember(guildId, memberId, subject, textId, substitute1, "", "");
    }
    public static void mailToGuildMember(int guildId, obj_id memberId, String subject, string_id textId) throws InterruptedException
    {
        mailToGuildMember(guildId, memberId, subject, textId, "", "", "");
    }
    public static int findIntTableOffset(int[] from, int find) throws InterruptedException
    {
        if (from != null)
        {
            for (int i = 0; i < from.length; ++i)
            {
                if (from[i] == find)
                {
                    return i;
                }
            }
        }
        return -1;
    }
    public static int findObjIdTableOffset(obj_id[] from, obj_id find) throws InterruptedException
    {
        if (from != null)
        {
            for (int i = 0; i < from.length; ++i)
            {
                if (from[i] == find)
                {
                    return i;
                }
            }
        }
        return -1;
    }
    public static void selectPermissions(obj_id self, obj_id player, String name, int guildId) throws InterruptedException
    {
        obj_id memberId = findMemberIdByName(guildId, name, true, true);
        dictionary params = new dictionary();
        params.put("player", player);
        params.put("name", name);
        params.put("terminalId", self);
        params.put("guildId", guildId);
        messageTo(self, "selectPermissionsMessage", params, 1, false);
    }
    public static void selectRank(obj_id self, obj_id player, String name, int guildId) throws InterruptedException
    {
        String[] allRanks = guildGetAllRanks();
        if (allRanks == null || allRanks.length < 1)
        {
            return;
        }
        String[] rankDisplay = new String[allRanks.length];
        obj_id memberId = guild.findMemberIdByName(guildId, name, false, true);
        if (!isIdValid(memberId))
        {
            return;
        }
        for (int i = 0, j = allRanks.length; i < j; i++)
        {
            if (guildHasMemberRank(guildId, memberId, allRanks[i]))
            {
                rankDisplay[i] = "+ " + localize(new string_id("guild_rank_title", allRanks[i]));
            }
            else 
            {
                rankDisplay[i] = "- " + localize(new string_id("guild_rank_title", allRanks[i]));
            }
        }
        utils.setScriptVar(self, "guild.allRanks", allRanks);
        dictionary params = new dictionary();
        params.put("player", player);
        params.put("guildMemberName", name);
        params.put("guildId", guildId);
        params.put("rankDisplay", rankDisplay);
        messageTo(self, "selectRankMessage", params, 1, false);
    }
    public static void showGuildMembers(obj_id terminal, obj_id player, int firstMember, int permission, String title, String name) throws InterruptedException
    {
        if (guild.hasWindowPid(player))
        {
            int pid = guild.getWindowPid(player);
            forceCloseSUIPage(pid);
            guild.removeWindowPid(player);
        }
        int guildId = getGuildId(player);
        if (guildId <= 0)
        {
            return;
        }
        obj_id[] memberIds = getMemberIds(guildId, false, true);
        if (memberIds == null || memberIds.length < 1)
        {
            return;
        }
        obj_id leader = guildGetLeader(guildId);
        utils.setScriptVar(player, "guild.memberIds", memberIds);
        String[][] memberData = new String[memberIds.length][6];
        String rank = "";
        int exclusionCount = 0;
        int inclusionCount = 0;
        for (int i = 0, j = memberIds.length; i < j; i++)
        {
            if (!isIdValid(memberIds[i]))
            {
                memberData[i][0] = "Unknown Player";
                continue;
            }
            if (isIdValid(leader) && memberIds[i] == leader)
            {
                memberData[i][0] = "\\#00FF00" + guildGetMemberName(guildId, memberIds[i]) + "\\#DFDFDF";
            }
            else 
            {
                memberData[i][0] = guildGetMemberName(guildId, memberIds[i]);
            }
            int level = guildGetMemberLevel(guildId, memberIds[i]);
            if (level <= 0)
            {
                memberData[i][1] = "";
            }
            else 
            {
                memberData[i][1] = "" + level;
            }
            String profession = guildGetMemberProfession(guildId, memberIds[i]);
            if (profession != null && profession.length() > 0)
            {
                memberData[i][2] = "@ui_roadmap:" + profession;
            }
            else 
            {
                memberData[i][2] = "Unavailable";
            }
            memberData[i][3] = guildGetMemberTitle(guildId, memberIds[i]);
            String[] ranks = guildGetMemberRank(guildId, memberIds[i]);
            rank = "";
            if (ranks == null || ranks.length < 1)
            {
                rank = "None";
            }
            else 
            {
                for (int k = 0, l = ranks.length; k < l; k++)
                {
                    String rankText = new String(ranks[k]);
                    string_id id = new string_id("guild_rank_title", rankText);
                    String finalText = localize(id);
                    rank += finalText;
                    if (k + 1 < l)
                    {
                        rank += ", ";
                    }
                }
            }
            memberData[i][4] = rank;
            if (!isPlayer(memberIds[i]))
            {
                memberData[i][5] = "Deleted";
            }
            else if (!isPlayerConnected(memberIds[i]))
            {
                int lastLoginTime = getPlayerLastLoginTime(memberIds[i]);
                if (lastLoginTime > 0)
                {
                    int timeDifference = getCalendarTime() - lastLoginTime;
                    if (timeDifference > 0)
                    {
                        memberData[i][5] = "Offline " + utils.padTimeDHMS(timeDifference);
                    }
                    else 
                    {
                        memberData[i][5] = "Offline ????d:??h:??m:??s";
                    }
                }
                else 
                {
                    memberData[i][5] = "Unknown";
                }
            }
            else 
            {
                String locText = "Unknown";
                dictionary playerLoc = getConnectedPlayerLocation(memberIds[i]);
                String planet = playerLoc.getString("planet");
                if (planet != null && planet.length() > 0)
                {
                    locText = localize(new string_id("planet_n", planet));
                }
                String region = playerLoc.getString("region");
                if (region != null && region.length() > 0)
                {
                    locText += ":" + localize(new string_id(region.substring(1, region.indexOf(":")), region.substring(region.indexOf(":") + 1, region.length())));
                }
                String city = playerLoc.getString("playerCity");
                if (city != null && city.length() > 0)
                {
                    locText += ", " + city;
                }
                memberData[i][5] = "Online " + locText;
            }
        }
        String[] table_titles = 
        {
            "@guild:table_title_name",
            "@guild:table_title_level",
            "@guild:table_title_profession",
            "@guild:table_title_title",
            "@guild:table_title_rank",
            "@guild:table_title_status"
        };
        String[] table_types = 
        {
            "text",
            "integer",
            "text",
            "text",
            "text",
            "text"
        };
        String guildName = guildGetName(guildId);
        String guildAbbreviation = guildGetAbbrev(guildId);
        String guildTitle = "Guild Roster";
        if (guildName != null && guildName.length() > 0 && guildAbbreviation != null && guildAbbreviation.length() > 0)
        {
            String memberCount = "";
            if (memberData.length > 1)
            {
                memberCount = " - " + memberData.length + " members";
            }
            guildTitle = guildName + " [" + guildAbbreviation + "] Roster" + memberCount;
        }
        String guildInfoStr = "Guild Leader: " + guildGetMemberName(guildId, leader) + "\n" + "Members: " + memberIds.length;
        int pid = sui.tableRowMajor(player, player, sui.OK_CANCEL, guildTitle, "onGuildMembersResponse", guildInfoStr, table_titles, table_types, memberData, true);
        setWindowPid(player, pid);
    }
    public static void setWindowPid(obj_id player, int pid) throws InterruptedException
    {
        if (pid > -1)
        {
            utils.setScriptVar(player, "guild.pid", pid);
        }
    }
    public static int getWindowPid(obj_id player) throws InterruptedException
    {
        return utils.getIntScriptVar(player, "guild.pid");
    }
    public static boolean hasWindowPid(obj_id player) throws InterruptedException
    {
        return utils.hasScriptVar(player, "guild.pid");
    }
    public static void removeWindowPid(obj_id player) throws InterruptedException
    {
        utils.removeScriptVar(player, "guild.pid");
    }
    public static obj_id getGuildRemoteDevice(obj_id player) throws InterruptedException
    {
        if (utils.playerHasItemByTemplateInDataPad(player, STR_GUILD_REMOTE_DEVICE))
        {
            obj_id remote = utils.getItemPlayerHasByTemplateInDatapad(player, STR_GUILD_REMOTE_DEVICE);
            return remote;
        }
        return null;
    }
    public static String[] getAvailableMemberOptions(obj_id self, obj_id player) throws InterruptedException
    {
        int guildId = getGuildId(player);
        obj_id guildMaster = guildGetLeader(guildId);
        int count = 0;
        if (hasGuildPermission(guildId, player, GUILD_PERMISSION_TITLE))
        {
            ++count;
        }
        if (hasGuildPermission(guildId, player, GUILD_PERMISSION_KICK))
        {
            ++count;
        }
        if (hasGuildPermission(guildId, player, GUILD_PERMISSION_RANK) || player == guildMaster || isGod(player))
        {
            ++count;
        }
        if (player == guildMaster || isGod(player))
        {
            ++count;
        }
        String[] ret = new String[count];
        count = 0;
        if (hasGuildPermission(guildId, player, GUILD_PERMISSION_TITLE))
        {
            ret[count++] = STR_GUILD_TITLE;
        }
        if (hasGuildPermission(guildId, player, GUILD_PERMISSION_KICK))
        {
            ret[count++] = STR_GUILD_KICK;
        }
        if (hasGuildPermission(guildId, player, GUILD_PERMISSION_RANK) || player == guildMaster || isGod(player))
        {
            ret[count++] = STR_GUILD_RANK;
        }
        if (player == guildMaster || isGod(player))
        {
            ret[count++] = STR_GUILD_PERMISSIONS;
        }
        return ret;
    }
    public static void toggleWarExclusion(obj_id player, int guildId, String name) throws InterruptedException
    {
        if (!isIdValid(player) || !exists(player) || guildId <= 0 || name == null || name.length() < 1)
        {
            return;
        }
        obj_id target = guild.findMemberIdByName(guildId, name, false, true);
        if (!isIdValid(target))
        {
            return;
        }
        GuildLog(guildId, "togglePermission", player, target, "bit " + GUILD_PERMISSION_WAR_EXCLUSION);
        guildSetMemberPermission(guildId, target, guildGetMemberPermissions(guildId, target) ^ GUILD_PERMISSION_WAR_EXCLUSION);
        prose_package pp = new prose_package();
        pp.target.set(name);
        pp.stringId = SID_GUILD_WAR_EXCLUSION_TOGGLED;
        sendSystemMessageProse(player, pp);
    }
    public static void toggleWarInclusion(obj_id player, int guildId, String name) throws InterruptedException
    {
        if (!isIdValid(player) || !exists(player) || guildId <= 0 || name == null || name.length() < 1)
        {
            return;
        }
        obj_id target = guild.findMemberIdByName(guildId, name, false, true);
        if (!isIdValid(target))
        {
            return;
        }
        GuildLog(guildId, "togglePermission", player, target, "bit " + GUILD_PERMISSION_WAR_INCLUSION);
        guildSetMemberPermission(guildId, target, guildGetMemberPermissions(guildId, target) ^ GUILD_PERMISSION_WAR_INCLUSION);
        prose_package pp = new prose_package();
        pp.target.set(name);
        pp.stringId = SID_GUILD_WAR_INCLUSION_TOGGLED;
        sendSystemMessageProse(player, pp);
    }
    public static void chooseTitle(obj_id self, obj_id player, String name) throws InterruptedException
    {
        setMenuContextString(self, player, "guildTitlePlayerName", name);
        int pid = sui.inputbox(self, player, buildFakeLocalizedProse(STR_GUILD_TITLE_PROMPT, name, ""), sui.OK_CANCEL, STR_GUILD_TITLE_TITLE, sui.INPUT_NORMAL, null, "onGuildTitleResponse");
        setWindowPid(player, pid);
    }
    public static void setMenuContextString(obj_id self, obj_id player, String varName, String value) throws InterruptedException
    {
        deltadictionary dd = self.getScriptVars();
        dd.put("guildMenu." + player + "." + varName, value);
    }
    public static void confirmKick(obj_id self, obj_id player, String name) throws InterruptedException
    {
        utils.setScriptVar(self, "guildKickName", name);
        int pid = sui.msgbox(self, player, guild.buildFakeLocalizedProse(STR_GUILD_KICK_PROMPT, name, ""), sui.YES_NO, STR_GUILD_KICK_TITLE, sui.MSG_NORMAL, "onGuildKickResponse");
        setWindowPid(player, pid);
    }
    public static void setMenuContextIntArray(obj_id self, obj_id player, String varName, int[] value) throws InterruptedException
    {
        deltadictionary dd = self.getScriptVars();
        dd.put("guildMenu." + player + "." + varName, value);
    }
    public static String getMenuContextString(obj_id self, obj_id player, String varName) throws InterruptedException
    {
        deltadictionary dd = self.getScriptVars();
        return dd.getString("guildMenu." + player + "." + varName);
    }
    public static void removeMenuContextVar(obj_id self, obj_id player, String varName) throws InterruptedException
    {
        deltadictionary dd = self.getScriptVars();
        dd.remove("guildMenu." + player + "." + varName);
    }
    public static void showPermissionList(obj_id player, int guildId) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return;
        }
        if (guildId == 0)
        {
            return;
        }
        utils.setScriptVar(player, "guildShow.guildId", guildId);
        int pid = sui.listbox(player, player, STR_GUILD_MEMBERS_PROMPT, sui.OK_CANCEL_REFRESH, STR_GUILD_MEMBERS_TITLE, PERMISSION_LIST, "onGuildMembersPermissionsResponse", false, true);
        sui.listboxUseOtherButton(pid, STR_SUI_BACK_BUTTON);
        sui.showSUIPage(pid);
        setWindowPid(player, pid);
    }
    public static void showTitleList(obj_id player, int guildId) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return;
        }
        if (guildId == 0)
        {
            return;
        }
        String[] finalList = getGuildTitles(guildId);
        if (finalList != null && finalList.length > 0)
        {
            utils.setScriptVar(player, "guildShow.guildId", guildId);
            int pid = sui.listbox(player, player, STR_GUILD_MEMBERS_PROMPT, sui.OK_CANCEL_REFRESH, STR_GUILD_MEMBERS_TITLE, finalList, "onGuildMembersTitlesResponse", false, true);
            sui.listboxUseOtherButton(pid, STR_SUI_BACK_BUTTON);
            sui.showSUIPage(pid);
            setWindowPid(player, pid);
        }
        else 
        {
            if (hasWindowPid(player))
            {
                sendSystemMessage(player, SID_NO_TITLES_IN_GUILD);
                removeWindowPid(player);
            }
        }
    }
    public static String[] getGuildTitles(int guildId) throws InterruptedException
    {
        if (guildId == 0)
        {
            return null;
        }
        obj_id[] members = getMemberIds(guildId, false, true);
        int count = 0;
        if (members != null)
        {
            count = members.length;
        }
        Vector titleList = new Vector();
        titleList.setSize(0);
        for (int i = 0; i < count; ++i)
        {
            String title = guildGetMemberTitle(guildId, members[i]);
            if (!title.equals(""))
            {
                boolean alreadyHave = false;
                for (int j = 0; j < titleList.size(); ++j)
                {
                    if (title.equals(((String)titleList.get(j))))
                    {
                        alreadyHave = true;
                        break;
                    }
                }
                if (!alreadyHave)
                {
                    titleList.add(title);
                }
            }
        }
        String[] finalList = new String[titleList.size()];
        titleList.toArray(finalList);
        Arrays.sort(finalList);
        return finalList;
    }
    public static boolean memberHasTitle(int guildId, obj_id memberId, String title) throws InterruptedException
    {
        String memberTitle = guildGetMemberTitle(guildId, memberId);
        return (toLower(memberTitle)).equals(toLower(title));
    }
    public static String[] getMemberNamesByPermission(int guildId, int permission) throws InterruptedException
    {
        String[] rawNamesAndTitles = getMemberNamesAndTitles(guildId);
        String[] rawMemberNames = getMemberNames(guildId, true, true);
        Vector filteredNamesAndTitles = new Vector();
        filteredNamesAndTitles.setSize(0);
        for (int i = 0; i < rawNamesAndTitles.length; ++i)
        {
            obj_id memberId = findMemberIdByName(guildId, rawMemberNames[i], true, true);
            if (hasGuildPermission(guildId, memberId, permission))
            {
                filteredNamesAndTitles = utils.addElement(filteredNamesAndTitles, rawNamesAndTitles[i]);
            }
        }
        String[] finalList = new String[filteredNamesAndTitles.size()];
        filteredNamesAndTitles.toArray(finalList);
        Arrays.sort(finalList);
        return finalList;
    }
    public static String[] getMemberNamesByTitle(int guildId, String title) throws InterruptedException
    {
        obj_id[] members = getMemberIds(guildId, false, true);
        Vector filteredNamesAndTitles = new Vector();
        filteredNamesAndTitles.setSize(0);
        for (int i = 0; i < members.length; ++i)
        {
            if (memberHasTitle(guildId, members[i], title))
            {
                filteredNamesAndTitles = utils.addElement(filteredNamesAndTitles, guildGetMemberName(guildId, members[i]));
            }
        }
        String[] finalList = new String[filteredNamesAndTitles.size()];
        filteredNamesAndTitles.toArray(finalList);
        Arrays.sort(finalList);
        return finalList;
    }
    public static String[] getMemberNamesByName(int guildId, String name) throws InterruptedException
    {
        String[] memberNames = getMemberNames(guildId, false, true);
        Vector filteredNamesAndTitles = new Vector();
        filteredNamesAndTitles.setSize(0);
        for (int i = 0; i < memberNames.length; ++i)
        {
            if ((toLower(memberNames[i])).indexOf(toLower(name)) > -1)
            {
                filteredNamesAndTitles = utils.addElement(filteredNamesAndTitles, memberNames[i]);
            }
        }
        String[] finalList = new String[filteredNamesAndTitles.size()];
        filteredNamesAndTitles.toArray(finalList);
        Arrays.sort(finalList);
        return finalList;
    }
    public static void showGuildInfo(obj_id player) throws InterruptedException
    {
        final int guildId = getGuildId(player);
        final obj_id[] memberIds = guild.getMemberIds(guildId, false, true);
        String guildInfoStr = "Guild Name: " + guildGetName(guildId);
        final int factionId = guildGetCurrentFaction(guildId);
        if ((-615855020) == factionId)
        {
            guildInfoStr += " (Imperial aligned)";
        }
        else if ((370444368) == factionId)
        {
            guildInfoStr += " (Rebel aligned)";
        }
        guildInfoStr += "\n";
        guildInfoStr += ("Guild Abbreviation: " + guildGetAbbrev(guildId) + "\n");
        guildInfoStr += ("Guild Leader: " + guildGetMemberName(guildId, guildGetLeader(guildId)) + "\n");
        final String gcwDefenderRegion = guildGetCurrentGcwDefenderRegion(guildId);
        if ((gcwDefenderRegion != null) && (gcwDefenderRegion.length() > 0))
        {
            guildInfoStr += ("GCW Region Defender: " + localize(new string_id("gcw_regions", gcwDefenderRegion)));
            final int timeJoinedGcwDefenderRegion = guildGetTimeJoinedCurrentGcwDefenderRegion(guildId);
            if (timeJoinedGcwDefenderRegion > 0)
            {
                guildInfoStr += (" (started defending on " + getCalendarTimeStringLocal(timeJoinedGcwDefenderRegion) + ")\n");
                final int gcwDaysRequiredForGcwRegionDefenderBonus = utils.stringToInt(getConfigSetting("GameServer", "gcwDaysRequiredForGcwRegionDefenderBonus"));
                final int age = getCalendarTime() - timeJoinedGcwDefenderRegion;
                if (age > (gcwDaysRequiredForGcwRegionDefenderBonus * 86400))
                {
                    if ((-615855020) == factionId)
                    {
                        guildInfoStr += ("GCW Region Defender Bonus: " + getGcwDefenderRegionImperialBonus(gcwDefenderRegion) + "%\n");
                    }
                    else if ((370444368) == factionId)
                    {
                        guildInfoStr += ("GCW Region Defender Bonus: " + getGcwDefenderRegionRebelBonus(gcwDefenderRegion) + "%\n");
                    }
                    else 
                    {
                        guildInfoStr += "GCW Region Defender Bonus: (None - cannot determine factional alignment)\n";
                    }
                }
                else 
                {
                    guildInfoStr += ("GCW Region Defender Bonus: (None - hasn't defended for " + gcwDaysRequiredForGcwRegionDefenderBonus + " days)\n");
                }
            }
            else 
            {
                guildInfoStr += "\n";
                guildInfoStr += "GCW Region Defender Bonus: (None - cannot determine time started defending)\n";
            }
        }
        else 
        {
            guildInfoStr += "GCW Region Defender: (None)\n";
            guildInfoStr += "GCW Region Defender Bonus: (None)\n";
        }
        guildInfoStr += ("Members: " + memberIds.length);
        sui.msgbox(player, player, guildInfoStr, sui.OK_ONLY, STR_GUILD_INFO_TITLE, sui.MSG_NORMAL, "onGuildInfoResponse");
    }
    public static void showGuildEnemies(obj_id player) throws InterruptedException
    {
        dictionary params = new dictionary();
        params.put("player", player);
        messageTo(player, "showGuildEnemiesMessage", params, 2, false);
    }
    public static int[] getMenuContextIntArray(obj_id self, obj_id player, String varName) throws InterruptedException
    {
        deltadictionary dd = self.getScriptVars();
        return dd.getIntArray("guildMenu." + player + "." + varName);
    }
    public static void showGuildSponsored(obj_id player) throws InterruptedException
    {
        dictionary params = new dictionary();
        params.put("player", player);
        messageTo(player, "showGuildSponsoredMessage", params, 3, false);
    }
    public static boolean loadCartridge(obj_id player, obj_id cartridge) throws InterruptedException
    {
        if (!isIdValid(player) || !isIdValid(cartridge))
        {
            return false;
        }
        obj_id remoteDevice = getGuildRemoteDevice(player);
        if (!isIdValid(remoteDevice))
        {
            return false;
        }
        if (hasObjVar(remoteDevice, GUILD_SCREEN_ID))
        {
            obj_id guildScreen = getObjIdObjVar(remoteDevice, GUILD_SCREEN_ID);
            if (isIdValid(guildScreen))
            {
                String cartridgeTemplate = getStringObjVar(cartridge, GUILD_SCREEN_TYPE);
                if (cartridgeTemplate != null && !cartridgeTemplate.equals(""))
                {
                    setObjVar(remoteDevice, GUILD_SCREEN_TYPE, cartridgeTemplate);
                    messageTo(guildScreen, "handlerGuildNewLeader", null, 1, true);
                    if (hasObjVar(remoteDevice, GUILD_SCREEN_ID))
                    {
                        removeObjVar(remoteDevice, GUILD_SCREEN_ID);
                    }
                    return true;
                }
            }
        }
        else 
        {
            String cartridgeTemplate = getStringObjVar(cartridge, GUILD_SCREEN_TYPE);
            if (cartridgeTemplate != null && !cartridgeTemplate.equals(""))
            {
                setObjVar(remoteDevice, GUILD_SCREEN_TYPE, cartridgeTemplate);
                return true;
            }
        }
        return false;
    }
    public static int getVotingTime(int guildId) throws InterruptedException
    {
        return guildGetElectionNextEndTime(guildId);
    }
    public static int getGracePeriodTime(int guildId) throws InterruptedException
    {
        return guildGetElectionPreviousEndTime(guildId);
    }
    public static boolean isVotingEnabled(int guildId) throws InterruptedException
    {
        if (getCalendarTime() < getVotingTime(guildId) && getVotingTime(guildId) > getGracePeriodTime(guildId))
        {
            return true;
        }
        return false;
    }
    public static boolean isVotingGracePeriod(int guildId) throws InterruptedException
    {
        if (getCalendarTime() < getGracePeriodTime(guildId) && getVotingTime(guildId) < getGracePeriodTime(guildId))
        {
            return true;
        }
        return false;
    }
    public static boolean hasElectionEnded(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return false;
        }
        int guildId = getGuildId(player);
        if (guildId <= 0)
        {
            return false;
        }
        int votingTime = getVotingTime(guildId);
        int gracePeriodTime = getGracePeriodTime(guildId);
        if (gracePeriodTime == 0 && votingTime > 0 && votingTime < getCalendarTime())
        {
            return true;
        }
        return false;
    }
    public static obj_id[] getCandidates(int guildId) throws InterruptedException
    {
        if (guildId <= 0)
        {
            return null;
        }
        obj_id[] memberIds = getMemberIds(guildId, false, true);
        if (memberIds == null || memberIds.length < 1)
        {
            return null;
        }
        Vector candidates = new Vector();
        candidates.setSize(0);
        for (int i = 0, j = memberIds.length; i < j; i++)
        {
            if (isCandidate(guildId, memberIds[i]))
            {
                utils.addElement(candidates, memberIds[i]);
            }
        }
        obj_id[] _candidates = new obj_id[0];
        if (candidates != null)
        {
            _candidates = new obj_id[candidates.size()];
            candidates.toArray(_candidates);
        }
        return _candidates;
    }
    public static String[] getGuildMemberNamesByArray(int guildId, obj_id[] memberIds) throws InterruptedException
    {
        if (guildId <= 0 || memberIds == null || memberIds.length < 1)
        {
            return null;
        }
        String[] names = new String[memberIds.length];
        for (int i = 0, j = memberIds.length; i < j; i++)
        {
            names[i] = guildGetMemberName(guildId, memberIds[i]);
        }
        return names;
    }
    public static boolean isCandidate(int guildId, obj_id memberId) throws InterruptedException
    {
        if (guildId <= 0 || !isIdValid(memberId))
        {
            return false;
        }
        return hasGuildPermission(guildId, memberId, GUILD_PERMISSION_ELECTION_CANDIDATE);
    }
    public static void makeCandidate(int guildId, obj_id memberId) throws InterruptedException
    {
        if (guildId <= 0 || !isIdValid(memberId))
        {
            return;
        }
        guildSetMemberPermissionAndAllegiance(guildId, memberId, guildGetMemberPermissions(guildId, memberId) | GUILD_PERMISSION_ELECTION_CANDIDATE, memberId);
    }
    public static void removeCandidate(int guildId, obj_id memberId) throws InterruptedException
    {
        if (guildId <= 0 || !isIdValid(memberId))
        {
            return;
        }
        guildSetMemberPermissionAndAllegiance(guildId, memberId, guildGetMemberPermissions(guildId, memberId) & (~GUILD_PERMISSION_ELECTION_CANDIDATE), null);
    }
    public static void resetCandidates(int guildId, obj_id[] excluded) throws InterruptedException
    {
        if (guildId <= 0)
        {
            return;
        }
        obj_id[] memberIds = getMemberIds(guildId, false, true);
        if (memberIds == null || memberIds.length < 1)
        {
            return;
        }
        obj_id[] candidates = getCandidates(guildId);
        int l = 0;
        if (candidates != null)
        {
            l = candidates.length;
        }
        for (int i = 0, j = memberIds.length; i < j; i++)
        {
            obj_id votedFor = guildGetMemberAllegiance(guildId, memberIds[i]);
            if (isIdValid(votedFor))
            {
                if (!isIdValid(memberIds[i]))
                {
                    continue;
                }
                if (excluded != null && excluded.length > 0 && utils.isObjIdInArray(excluded, memberIds[i]))
                {
                    continue;
                }
                if (candidates == null || candidates.length < 1)
                {
                    guildSetMemberAllegiance(guildId, memberIds[i], null);
                }
                else 
                {
                    boolean candidateFound = false;
                    for (int k = 0; k < l; k++)
                    {
                        if (memberIds[i] == candidates[k])
                        {
                            candidateFound = true;
                            removeCandidate(guildId, memberIds[i]);
                        }
                    }
                    if (!candidateFound)
                    {
                        guildSetMemberAllegiance(guildId, memberIds[i], null);
                    }
                }
            }
        }
    }
    public static obj_id getGuildMemberVote(int guildId, obj_id memberId) throws InterruptedException
    {
        if (guildId <= 0 || !isIdValid(memberId))
        {
            return null;
        }
        obj_id votedForWho = guildGetMemberAllegiance(guildId, memberId);
        if (isCandidate(guildId, votedForWho))
        {
            return votedForWho;
        }
        return null;
    }
    public static obj_id[] getGuildMemberVotes(int guildId) throws InterruptedException
    {
        if (guildId <= 0)
        {
            return null;
        }
        obj_id[] memberIds = getMemberIds(guildId, false, true);
        if (memberIds == null || memberIds.length < 1)
        {
            return null;
        }
        obj_id[] memberVotedFor = new obj_id[memberIds.length];
        for (int i = 0, j = memberIds.length; i < j; i++)
        {
            memberVotedFor[i] = getGuildMemberVote(guildId, memberIds[i]);
        }
        return memberVotedFor;
    }
    public static int[] getTalliedVotes(int guildId, obj_id[] candidates) throws InterruptedException
    {
        if (guildId <= 0)
        {
            return null;
        }
        if (candidates == null || candidates.length < 1)
        {
            return null;
        }
        obj_id[] voteList = getGuildMemberVotes(guildId);
        if (voteList == null || voteList.length < 1)
        {
            return null;
        }
        int[] voteTotals = new int[candidates.length];
        obj_id self = getTopMostContainer(getSelf());
        for (int i = 0, j = candidates.length; i < j; i++)
        {
            for (int k = 0, l = voteList.length; k < l; k++)
            {
                if (candidates[i] == voteList[k])
                {
                    voteTotals[i]++;
                }
            }
        }
        return voteTotals;
    }
    public static void allegiance(obj_id actor, String who) throws InterruptedException
    {
        int guildId = getGuildId(actor);
        if (!isIdValid(actor))
        {
            return;
        }
        if (guildId != 0)
        {
            obj_id allegianceId = findMemberIdByName(guildId, who, false, true);
            if (isIdValid(allegianceId))
            {
                GuildLog(guildId, "allegiance", actor, allegianceId, "");
                prose_package pp = new prose_package();
                pp.actor.set(guildGetMemberName(guildId, allegianceId));
                pp.target.set(guildGetName(guildId));
                pp.stringId = SID_GUILD_ALLEGIANCE_SELF;
                sendSystemMessageProse(actor, pp);
                guildSetMemberAllegiance(guildId, actor, allegianceId);
            }
            else 
            {
                sendSystemMessage(actor, SID_GUILD_ALLEGIANCE_UNCHANGED_SELF);
            }
        }
    }
    public static void stopElection(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return;
        }
        int guildId = getGuildId(player);
        if (guildId == 0)
        {
            return;
        }
        guildSetElectionEndTime(guildId, getCalendarTime() + 1209600, 0);
        obj_id newLeader = determineLeaderByAllegiance(guildId);
        obj_id oldLeader = guildGetLeader(guildId);
        string_id leaderChanged = SID_COMPLETED_ELECTIONS_EMAIL_BODY_LEADER_SAME;
        if (isIdValid(newLeader) && newLeader != oldLeader)
        {
            guild.changeLeader(guildId, newLeader);
            leaderChanged = SID_COMPLETED_ELECTIONS_EMAIL_BODY_LEADER_CHANGED;
        }
        obj_id[] members = guild.getMemberIds(guildId, false, true);
        for (int i = 0; i < members.length; i++)
        {
            if (!isIdValid(members[i]))
            {
                continue;
            }
            String cname = guildGetMemberName(guildId, members[i]);
            prose_package bodypp = prose.getPackage(leaderChanged, cname);
            utils.sendMail(new string_id("guild", "open_elections_email_subject"), bodypp, cname, "Guild Management");
        }
        obj_id[] exluded = 
        {
            oldLeader,
            newLeader
        };
        resetCandidates(guildId, exluded);
    }
    public static obj_id determineLeaderByAllegiance(int guildId) throws InterruptedException
    {
        if (guildId == 0)
        {
            return null;
        }
        obj_id[] candidates = getCandidates(guildId);
        if (candidates == null || candidates.length < 1)
        {
            return null;
        }
        int[] talliedVotes = getTalliedVotes(guildId, candidates);
        if (talliedVotes == null || talliedVotes.length != candidates.length)
        {
            return null;
        }
        obj_id guildMaster = guildGetLeader(guildId);
        int guildMasterVotes = 0;
        obj_id newLeader = null;
        int leaderVotes = 0;
        for (int i = 0, j = candidates.length; i < j; i++)
        {
            if (guildMaster == candidates[i])
            {
                guildMasterVotes = talliedVotes[i];
            }
            if (leaderVotes < talliedVotes[i])
            {
                newLeader = candidates[i];
                leaderVotes = talliedVotes[i];
            }
        }
        if (guildMasterVotes == leaderVotes)
        {
            newLeader = null;
        }
        return newLeader;
    }
    public static void showStandings(obj_id self, obj_id player) throws InterruptedException
    {
        if (!isIdValid(player) || !exists(player))
        {
            return;
        }
        if (guild.hasWindowPid(player))
        {
            int pid = guild.getWindowPid(player);
            forceCloseSUIPage(pid);
            guild.removeWindowPid(player);
        }
        int guildId = getGuildId(player);
        if (guildId == 0)
        {
            return;
        }
        obj_id leader = guildGetLeader(guildId);
        obj_id[] members = getMemberIds(guildId, false, true);
        String name = guildGetMemberName(guildId, player);
        if (!isIdValid(leader) || members == null)
        {
            return;
        }
        obj_id[] candidates = getCandidates(guildId);
        if (candidates == null)
        {
            sendSystemMessage(player, SID_NO_CANDIDATES);
            return;
        }
        int[] vote_counts = getTalliedVotes(guildId, candidates);
        if (vote_counts == null)
        {
            return;
        }
        utils.setScriptVar(player, "guild.candidates", candidates);
        obj_id votedFor = guildGetMemberAllegiance(guildId, player);
        String[][] votingData = new String[candidates.length][3];
        for (int i = 0; i < candidates.length; i++)
        {
            if (!isIdValid(candidates[i]))
            {
                votingData[i][0] = "Unknown Player";
                continue;
            }
            if (candidates[i] == leader)
            {
                votingData[i][0] = "GM: " + guildGetMemberName(guildId, leader);
            }
            else 
            {
                votingData[i][0] = guildGetMemberName(guildId, candidates[i]);
            }
            if (votedFor == candidates[i])
            {
                votingData[i][1] = "X";
            }
            else 
            {
                votingData[i][1] = "";
            }
            votingData[i][2] = "" + vote_counts[i];
        }
        String[] table_titles = 
        {
            "Name",
            "Voted For",
            "Votes"
        };
        String[] table_types = 
        {
            "text",
            "text",
            "integer"
        };
        String guildName = guildGetName(guildId);
        String guildAbbreviation = guildGetAbbrev(guildId);
        String guildTitle = "Guild Candidates Running For Office";
        if (guildName != null && guildName.length() > 0 && guildAbbreviation != null && guildAbbreviation.length() > 0)
        {
            guildTitle = guildName + " [" + guildAbbreviation + "] Voting";
        }
        utils.setScriptVar(player, "guildId", guildId);
        int pid = sui.tableRowMajor(player, player, sui.OK_CANCEL, guildTitle, "onGuildVotingResponse", null, table_titles, table_types, votingData);
        guild.setWindowPid(player, pid);
    }
    public static void showMasterGuildWarTableDictionary(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player) || !exists(player))
        {
            return;
        }
        if (utils.hasScriptVar(player, "guild.masterGuildWarTablePid"))
        {
            int existingPid = utils.getIntScriptVar(player, "guild.masterGuildWarTablePid");
            utils.removeScriptVar(player, "guild.masterGuildWarTablePid");
            forceCloseSUIPage(existingPid);
        }
        int newPid = -1;
        dictionary dict = getMasterGuildWarTableDictionary();
        if (dict != null)
        {
            String[] columnHeader = dict.getStringArray("column");
            if ((columnHeader != null) && (columnHeader.length > 0))
            {
                String[] columnHeaderType = dict.getStringArray("columnType");
                if ((columnHeaderType != null) && (columnHeaderType.length > 0) && (columnHeaderType.length == columnHeader.length))
                {
                    boolean validColumnData = true;
                    String[][] columnData = new String[columnHeader.length][0];
                    for (int i = 0; i < columnHeader.length; ++i)
                    {
                        columnData[i] = dict.getStringArray("column" + i);
                        if ((columnData[i] == null) || (columnData[i].length <= 0))
                        {
                            validColumnData = false;
                            break;
                        }
                    }
                    if (validColumnData)
                    {
                        newPid = sui.tableColumnMajor(player, player, sui.OK_ONLY, "@guild:menu_list_of_guild_wars_active", "onMasterGuildWarTableDictionaryResponse", null, columnHeader, columnHeaderType, columnData);
                    }
                }
            }
        }
        if (newPid > 0)
        {
            utils.setScriptVar(player, "guild.masterGuildWarTablePid", newPid);
        }
        else 
        {
            sendSystemMessage(player, "No data to display.", "");
        }
    }
    public static void closedMasterGuildWarTableDictionary(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player) || !exists(player))
        {
            return;
        }
        if (utils.hasScriptVar(player, "guild.masterGuildWarTablePid"))
        {
            utils.removeScriptVar(player, "guild.masterGuildWarTablePid");
        }
    }
    public static void showInactiveGuildWarTableDictionary(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player) || !exists(player))
        {
            return;
        }
        if (utils.hasScriptVar(player, "guild.inactiveGuildWarTablePid"))
        {
            int existingPid = utils.getIntScriptVar(player, "guild.inactiveGuildWarTablePid");
            utils.removeScriptVar(player, "guild.inactiveGuildWarTablePid");
            forceCloseSUIPage(existingPid);
        }
        int newPid = -1;
        dictionary dict = getInactiveGuildWarTableDictionary();
        if (dict != null)
        {
            String[] columnHeader = dict.getStringArray("column");
            if ((columnHeader != null) && (columnHeader.length > 0))
            {
                String[] columnHeaderType = dict.getStringArray("columnType");
                if ((columnHeaderType != null) && (columnHeaderType.length > 0) && (columnHeaderType.length == columnHeader.length))
                {
                    boolean validColumnData = true;
                    String[][] columnData = new String[columnHeader.length][0];
                    for (int i = 0; i < columnHeader.length; ++i)
                    {
                        columnData[i] = dict.getStringArray("column" + i);
                        if ((columnData[i] == null) || (columnData[i].length <= 0))
                        {
                            validColumnData = false;
                            break;
                        }
                    }
                    if (validColumnData)
                    {
                        newPid = sui.tableColumnMajor(player, player, sui.OK_ONLY, "@guild:menu_list_of_guild_wars_inactive", "onInactiveGuildWarTableDictionaryResponse", null, columnHeader, columnHeaderType, columnData);
                    }
                }
            }
        }
        if (newPid > 0)
        {
            utils.setScriptVar(player, "guild.inactiveGuildWarTablePid", newPid);
        }
        else 
        {
            sendSystemMessage(player, "No data to display.", "");
        }
    }
    public static void closedInactiveGuildWarTableDictionary(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player) || !exists(player))
        {
            return;
        }
        if (utils.hasScriptVar(player, "guild.inactiveGuildWarTablePid"))
        {
            utils.removeScriptVar(player, "guild.inactiveGuildWarTablePid");
        }
    }
    public static void showRankSummary(obj_id self, obj_id player) throws InterruptedException
    {
        if (!isIdValid(player) || !exists(player))
        {
            return;
        }
        if (guild.hasWindowPid(player))
        {
            int pid = guild.getWindowPid(player);
            forceCloseSUIPage(pid);
            guild.removeWindowPid(player);
        }
        int guildId = getGuildId(player);
        if (guildId == 0)
        {
            return;
        }
        obj_id[] members = getMemberIds(guildId, false, true);
        String name = guildGetMemberName(guildId, player);
        if (members == null)
        {
            return;
        }
        String[] allRanks = guildGetAllRanks();
        if (allRanks == null || allRanks.length < 1)
        {
            return;
        }
        utils.setScriptVar(player, "guild.allRanks", allRanks);
        String[][] rankData = new String[allRanks.length][3];
        String[] table_titles = 
        {
            "Rank",
            "Count",
            "Show"
        };
        String[] table_types = 
        {
            "text",
            "integer",
            "text"
        };
        int[] rankTotals = new int[allRanks.length];
        int[] ranksPreferred = getIntArrayObjVar(player, "guild.ranksPreferred");
        for (int i = 0, j = members.length; i < j; i++)
        {
            if (!isIdValid(members[i]))
            {
                continue;
            }
            for (int k = 0, l = allRanks.length; k < l; k++)
            {
                if (i == 0)
                {
                    rankData[k][0] = localize(new string_id("guild_rank_title", allRanks[k]));
                }
                if (guildHasMemberRank(guildId, members[i], allRanks[k]))
                {
                    rankTotals[k]++;
                }
                if (i == j - 1)
                {
                    rankData[k][1] = "" + rankTotals[k];
                }
            }
        }
        if (ranksPreferred != null && ranksPreferred.length > 0)
        {
            for (int i = 0, j = ranksPreferred.length; i < j; i++)
            {
                rankData[ranksPreferred[i]][2] = "X";
            }
        }
        String guildName = guildGetName(guildId);
        String guildAbbreviation = guildGetAbbrev(guildId);
        String guildTitle = "Guild Rank Summary";
        if (guildName != null && guildName.length() > 0 && guildAbbreviation != null && guildAbbreviation.length() > 0)
        {
            guildTitle = guildName + " [" + guildAbbreviation + "] Rank Summary";
        }
        utils.setScriptVar(player, "guildId", guildId);
        int pid = sui.tableRowMajor(player, player, sui.OK_CANCEL, guildTitle, "onGuildRankSummaryResponse", STR_GUILD_RANK_SUMMARY_PROMPT, table_titles, table_types, rankData);
        guild.setWindowPid(player, pid);
    }
    public static void showRankList(obj_id self, obj_id player) throws InterruptedException
    {
        if (!isIdValid(player) || !exists(player))
        {
            return;
        }
        if (guild.hasWindowPid(player))
        {
            int pid = guild.getWindowPid(player);
            forceCloseSUIPage(pid);
            guild.removeWindowPid(player);
        }
        int guildId = getGuildId(player);
        if (guildId == 0)
        {
            return;
        }
        obj_id[] members = getMemberIds(guildId, false, true);
        String name = guildGetMemberName(guildId, player);
        if (members == null)
        {
            return;
        }
        String[] allRanks = guildGetAllRanks();
        if (allRanks == null || allRanks.length < 1)
        {
            return;
        }
        int[] ranksPreferred = getIntArrayObjVar(player, "guild.ranksPreferred");
        int tableWidth = 0;
        if (ranksPreferred != null && ranksPreferred.length > 0)
        {
            tableWidth = ranksPreferred.length + 1;
        }
        else 
        {
            tableWidth = 21;
        }
        if (tableWidth > 21)
        {
            tableWidth = 21;
        }
        String[][] rankData = new String[members.length][tableWidth];
        String[] table_titles = new String[tableWidth];
        String[] table_types = new String[tableWidth];
        for (int i = 0, j = members.length; i < j; i++)
        {
            if (!isIdValid(members[i]))
            {
                rankData[i][0] = "Unknown Player";
                continue;
            }
            rankData[i][0] = guildGetMemberName(guildId, members[i]);
            if (i == 0)
            {
                table_titles[0] = "Name";
                table_types[0] = "text";
            }
            for (int k = 0, l = tableWidth - 1; k < l; k++)
            {
                int rankIndex = -1;
                if (ranksPreferred != null && ranksPreferred.length > 0)
                {
                    rankIndex = ranksPreferred[k];
                }
                else 
                {
                    rankIndex = k;
                }
                if (i == 0)
                {
                    String rank = localize(new string_id("guild_rank_title", allRanks[rankIndex]));
                    table_titles[k + 1] = rank.substring(6, rank.length());
                    table_types[k + 1] = "text";
                }
                if (guildHasMemberRank(guildId, members[i], allRanks[rankIndex]))
                {
                    rankData[i][k + 1] = "X";
                }
                else 
                {
                    rankData[i][k + 1] = "";
                }
            }
        }
        String guildName = guildGetName(guildId);
        String guildAbbreviation = guildGetAbbrev(guildId);
        String guildTitle = "Guild Ranks";
        if (guildName != null && guildName.length() > 0 && guildAbbreviation != null && guildAbbreviation.length() > 0)
        {
            guildTitle = guildName + " [" + guildAbbreviation + "] Ranks";
        }
        utils.setScriptVar(player, "guildId", guildId);
        utils.setScriptVar(player, "guild.memberIds", members);
        int pid = sui.tableRowMajor(player, player, sui.OK_CANCEL, guildTitle, "onGuildRankListResponse", null, table_titles, table_types, rankData);
        guild.setWindowPid(player, pid);
    }
    public static void showPermissionSummary(obj_id self, obj_id player) throws InterruptedException
    {
        if (!isIdValid(player) || !exists(player))
        {
            return;
        }
        if (guild.hasWindowPid(player))
        {
            int pid = guild.getWindowPid(player);
            forceCloseSUIPage(pid);
            guild.removeWindowPid(player);
        }
        int guildId = getGuildId(player);
        if (guildId == 0)
        {
            return;
        }
        obj_id[] members = getMemberIds(guildId, false, true);
        String name = guildGetMemberName(guildId, player);
        if (members == null)
        {
            return;
        }
        String[][] permissionData = new String[members.length][12];
        String[] table_titles = 
        {
            "Name",
            "Sponsor",
            "Sponsor Accept",
            "Kick",
            "Mail",
            "Title",
            "Disband",
            "Guild Name Change",
            "Rank",
            "War",
            "War Excluded",
            "War Exclusive"
        };
        String[] table_types = 
        {
            "text",
            "text",
            "text",
            "text",
            "text",
            "text",
            "text",
            "text",
            "text",
            "text",
            "text",
            "text"
        };
        int[] permissionFlags = PERMISSION_INTERFACE_FLAGS;
        int[] permissionCounts = new int[11];
        for (int i = 0, j = members.length; i < j; i++)
        {
            if (!isIdValid(members[i]))
            {
                permissionData[i][0] = "Unknown Player";
                continue;
            }
            permissionData[i][0] = guildGetMemberName(guildId, members[i]);
            for (int k = 0, l = 11; k < l; k++)
            {
                if (hasGuildPermission(guildId, members[i], permissionFlags[k + 1]))
                {
                    permissionData[i][k + 1] = "X";
                    permissionCounts[k]++;
                }
                else 
                {
                    permissionData[i][k + 1] = "";
                }
                if (i == j - 1)
                {
                    table_titles[k + 1] = table_titles[k + 1] + " [" + permissionCounts[k] + "]";
                }
            }
        }
        String guildName = guildGetName(guildId);
        String guildAbbreviation = guildGetAbbrev(guildId);
        String guildTitle = "Guild Permissions";
        if (guildName != null && guildName.length() > 0 && guildAbbreviation != null && guildAbbreviation.length() > 0)
        {
            guildTitle = guildName + " [" + guildAbbreviation + "] Permissions";
        }
        utils.setScriptVar(player, "guildId", guildId);
        utils.setScriptVar(player, "guild.memberIds", members);
        int pid = sui.tableRowMajor(player, player, sui.OK_CANCEL, guildTitle, "onGuildPermissionListResponse", null, table_titles, table_types, permissionData);
        guild.setWindowPid(player, pid);
    }
    public static void statusNotification(int guildId, obj_id who, boolean login) throws InterruptedException
    {
        if (guildId <= 0)
        {
            return;
        }
        obj_id[] members = getMemberIds(guildId, false, true);
        if (members == null || members.length < 1)
        {
            return;
        }
        for (int i = 0, j = members.length; i < j; i++)
        {
            if (!isIdValid(members[i]))
            {
                continue;
            }
            if (isPlayerConnected(members[i]) && guild.hasGuildPermission(guildId, members[i], guild.GUILD_PERMISSION_ONLINE_STATUS))
            {
                dictionary params = new dictionary();
                params.put("guildId", guildId);
                params.put("player", who);
                params.put("login", login);
                messageTo(members[i], "handleStatusNotification", params, 1, false);
            }
        }
    }
    public static boolean inSameGuild(obj_id target1, obj_id target2) throws InterruptedException
    {
        int guildId1 = getGuildId(target1);
        int guildId2 = getGuildId(target2);
        if (guildId1 <= 0 || guildId2 <= 0)
        {
            return false;
        }
        else if (guildId1 == guildId2)
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
}
