package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.money;
import script.library.prose;
import script.library.utils;
import script.library.skill;
import script.library.space_utils;
import script.library.space_transition;
import script.library.group;
import script.library.features;

public class space_quest extends script.base_script
{
    public space_quest()
    {
    }
    public static final String QUEST_OBJECT_TEMPLATE = "object/mission/base_mission_object.iff";
    public static final String QUEST_MANAGER = "questManager";
    public static final String QUEST_TYPE = "questType";
    public static final String QUEST_NAME = "questName";
    public static final String QUEST_OWNER = "questOwner";
    public static final String QUEST_ZONE = "questZone";
    public static final String QUEST_DUTY = "duty";
    public static final String QUEST_DELAY = "delay";
    public static final String QUEST_TRIGGER_EVENT = "trigger.triggerEvent";
    public static final String QUEST_TRIGGER_SC = "trigger.triggerSC";
    public static final String QUEST_TRIGGER_ARG = "trigger.triggerArg";
    public static final String QUEST_TRIGGER_ARG2 = "trigger.triggerArg2";
    public static final String QUEST_TRIGGER_ON = "trigger.triggerOn";
    public static final String QUEST_TRIGGER_ENDS = "trigger.triggerEnds";
    public static final String QUEST_TRIGGER_ENDS_NEW = "trigger.triggerEndsMission";
    public static final String QUEST_TRIGGER_DELAY = "trigger.triggerDelay";
    public static final String QUEST_AUTO_REWARD = "autoReward";
    public static final String QUEST_AUTO_REWARD_FROM = "autoRewardFrom";
    public static final String QUEST_AUTO_REWARD_SUBJECT = "autoRewardSubject";
    public static final String QUEST_AUTO_REWARD_BODY = "autoRewardBody";
    public static final String QUEST_STATUS = "space_quest";
    public static final String QUEST_STATUS_SERIES = "space_quest.series";
    public static final int QUEST_FAILED = 1;
    public static final int QUEST_WON = 2;
    public static final int QUEST_ABORTED = 4;
    public static final int QUEST_RECEIVED_REWARD = 8;
    public static final int QUEST_GROUP_QUEST_IN_PROGRESS = 16;
    public static final int QUEST_SERIES_COMPLETED = 1;
    public static final int PATROL_NAV_RADIUS = 150;
    public static final int ESCORT_ROAM_TOLERANCE = 1000;
    public static final string_id SID_REQUIRES_JTL = new string_id("space/quest", "jtl_required");
    public static final string_id SID_QUEST_RECEIVED = new string_id("space/quest", "quest_received");
    public static final string_id SID_QUEST_IN_PROGRESS = new string_id("space/quest", "quest_in_progress");
    public static final string_id SID_QUEST_FAILED = new string_id("space/quest", "quest_failed");
    public static final string_id SID_QUEST_WON = new string_id("space/quest", "quest_won");
    public static final string_id SID_QUEST_ENDED = new string_id("space/quest", "quest_ended");
    public static final string_id SID_QUEST_ABORTED = new string_id("space/quest", "quest_aborted");
    public static final string_id SID_QUEST_REWARDED = new string_id("space/quest", "quest_rewarded");
    public static final string_id SID_QUEST_UPDATE_S = new string_id("space/quest", "quest_update_s");
    public static final string_id SID_QUEST_UPDATE_IS = new string_id("space/quest", "quest_update_is");
    public static final string_id SID_QUEST_ALERT_S = new string_id("space/quest", "quest_alert_s");
    public static final string_id SID_ESCORT_UPDATE = new string_id("space/quest", "escort_update");
    public static final string_id SID_DUTY_RECEIVED = new string_id("space/quest", "duty_received");
    public static final string_id SID_DUTY_ABORTED = new string_id("space/quest", "duty_aborted");
    public static final string_id SID_DUTY_UPDATE_S = new string_id("space/quest", "duty_update_s");
    public static final string_id SID_DUTY_UPDATE_IS = new string_id("space/quest", "duty_update_is");
    public static final string_id SID_DUTY_ALREADY = new string_id("space/quest", "duty_already");
    public static final string_id SID_GROUP_QUEST_RECEIVED = new string_id("space/quest", "quest_received_group");
    public static final string_id SID_GROUP_QUEST_WON = new string_id("space/quest", "quest_won_group");
    public static final string_id SID_GROUP_QUEST_ENDED = new string_id("space/quest", "quest_ended_group");
    public static final string_id SID_GROUP_QUEST_FAILED = new string_id("space/quest", "quest_failed_group");
    public static final string_id SID_GROUP_QUEST_ABORTED = new string_id("space/quest", "quest_aborted_group");
    public static final string_id SID_GROUP_QUEST_IN_PROGRESS = new string_id("space/quest", "quest_in_progress_group");
    public static final string_id SID_QUEST_REWARD = new string_id("space/quest", "quest_reward");
    public static final string_id SID_QUEST_REWARD_OBJECT = new string_id("space/quest", "quest_reward_object");
    public static final string_id GROUP_QUEST_RECEIVED = new string_id("space/quest", "group_quest_received");
    public static final string_id GROUP_QUEST_FAILED = new string_id("space/quest", "group_quest_failed");
    public static final string_id GROUP_QUEST_WON = new string_id("space/quest", "group_quest_won");
    public static final string_id GROUP_QUEST_ABORTED = new string_id("space/quest", "group_quest_aborted");
    public static final String MUSIC_QUEST_RECEIVED = "sound/music_themequest_acc_criminal.snd";
    public static final String MUSIC_QUEST_WON = "sound/music_themequest_victory_rebel.snd";
    public static final String MUSIC_QUEST_FAILED = "sound/music_themequest_fail_criminal.snd";
    public static final String MUSIC_QUEST_ABORTED = "sound/music_themequest_fail_criminal.snd";
    public static final String MUSIC_QUEST_EVENT = "sound/music_event_danger.snd";
    public static final String MUSIC_QUEST_ESCORT_ARRIVAL = "sound/mus_quest_escort_arrival.snd";
    public static final String MUSIC_QUEST_BOSS_COMING = "sound/mus_quest_boss_coming.snd";
    public static final String MUSIC_QUEST_CAPTURE_OP = "sound/mus_quest_theme_docking.snd";
    public static final String MUSIC_QUEST_MISSION_START = "sound/mus_quest_theme_opening.snd";
    public static final String ALERT_DROID_WARNING = "clienteffect/space_droid_warning.iff";
    public static final String ALERT_DROID_DANGER = "clienteffect/space_droid_danger.iff";
    public static boolean hasQuest(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return false;
        }
        obj_id datapad = utils.getPlayerDatapad(player);
        if (isIdValid(datapad))
        {
            obj_id[] dpobjs = getContents(datapad);
            for (int i = 0; i < dpobjs.length; i++)
            {
                if (hasObjVar(dpobjs[i], QUEST_NAME))
                {
                    return true;
                }
            }
        }
        return false;
    }
    public static boolean hasQuest(obj_id player, String questType) throws InterruptedException
    {
        return (_getQuest(player, questType) != null);
    }
    public static obj_id _getQuest(obj_id player, String questType) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return null;
        }
        if (questType == null)
        {
            return null;
        }
        obj_id datapad = utils.getPlayerDatapad(player);
        if (isIdValid(datapad))
        {
            obj_id[] dpobjs = getContents(datapad);
            for (int i = 0; i < dpobjs.length; i++)
            {
                if (hasObjVar(dpobjs[i], QUEST_TYPE))
                {
                    String tname = getStringObjVar(dpobjs[i], QUEST_TYPE);
                    if (questType.equals(tname))
                    {
                        return dpobjs[i];
                    }
                }
            }
        }
        return null;
    }
    public static boolean hasQuest(obj_id player, String questType, String questName) throws InterruptedException
    {
        return (_getQuest(player, questType, questName) != null);
    }
    public static obj_id _getQuest(obj_id player, String questType, String questName) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return null;
        }
        if ((questName == null) || (questType == null))
        {
            return null;
        }
        obj_id datapad = utils.getPlayerDatapad(player);
        if (isIdValid(datapad))
        {
            obj_id[] dpobjs = getContents(datapad);
            for (int i = 0; i < dpobjs.length; i++)
            {
                if (hasObjVar(dpobjs[i], QUEST_NAME))
                {
                    String qname = getStringObjVar(dpobjs[i], QUEST_NAME);
                    String tname = getStringObjVar(dpobjs[i], QUEST_TYPE);
                    if (questName.equals(qname) && questType.equals(tname))
                    {
                        return dpobjs[i];
                    }
                }
            }
        }
        return null;
    }
    public static boolean grantQuest(obj_id player, String questType, String questName) throws InterruptedException
    {
        return grantQuest(player, questType, questName, false, false);
    }
    public static boolean grantQuest(obj_id player, String questType, String questName, boolean splitQuest) throws InterruptedException
    {
        return grantQuest(player, questType, questName, splitQuest, false);
    }
    public static boolean grantQuest(obj_id player, String questType, String questName, boolean splitQuest, boolean ignoreDelay) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return false;
        }
        if (!features.isSpaceEdition(player))
        {
            sendSystemMessage(player, SID_REQUIRES_JTL);
            return false;
        }
        if (hasQuest(player, questType, questName))
        {
            return false;
        }
        String qTable = "datatables/spacequest/" + questType + "/" + questName + ".iff";
        boolean duty = false;
        if (questType.indexOf("_duty") > 0)
        {
            duty = true;
        }
        dictionary questInfo = dataTableGetRow(qTable, 0);
        if (questInfo == null)
        {
            sendSystemMessageTestingOnly(player, "grantQuest: Unable to find quest table " + qTable);
            return false;
        }
        if (!duty)
        {
            int delay = dataTableGetInt(qTable, 0, "questDelay");
            if (!ignoreDelay && (delay > 0))
            {
                dictionary outparams = new dictionary();
                outparams.put(QUEST_TYPE, questType);
                outparams.put(QUEST_NAME, questName);
                space_utils.notifyObject(player, "registerDelayedQuest", outparams);
                return true;
            }
        }
        if (_setupQuest(player, questType, questName, qTable, duty))
        {
            string_id title = new string_id("spacequest/" + questType + "/" + questName, "title");
            prose_package pp;
            if (duty)
            {
                pp = prose.getPackage(SID_DUTY_RECEIVED, title);
            }
            else 
            {
                pp = prose.getPackage(SID_QUEST_RECEIVED, title);
            }
            sendQuestSystemMessage(player, pp);
            _groupNotify(player, SID_GROUP_QUEST_RECEIVED, title);
            if (!splitQuest)
            {
                play2dNonLoopingMusic(player, MUSIC_QUEST_RECEIVED);
            }
            String groupString = "GROUP:0";
            if (group.isGrouped(player))
            {
                obj_id groupId = getGroupObject(player);
                int numMembers = getGroupSize(groupId);
                if (numMembers > 0)
                {
                    groupString = "GROUP:" + numMembers;
                }
            }
            CustomerServiceLog("space_quest", "QUEST_STARTED|V1|" + groupString + "|TIME:" + getGameTime() + "|PLAYER:" + player + "|TYPE:" + questType + "|NAME:" + questName + "|ZONE:" + getCurrentSceneName());
            return true;
        }
        else 
        {
            sendSystemMessageTestingOnly(player, "Failed to grantQuest '" + questType + ":" + questName + "'.");
            return false;
        }
    }
    public static boolean grantGroupQuest(obj_id player, String questType, String questName) throws InterruptedException
    {
        boolean result = grantQuest(player, questType, questName);
        flagGroupQuest(player, questType, questName);
        return result;
    }
    public static void flagGroupQuest(obj_id player, String questType, String questName) throws InterruptedException
    {
        if (group.isGrouped(player))
        {
            obj_id gid = getGroupObject(player);
            obj_id[] members = space_utils.getSpaceGroupMemberIds(gid);
            if (members != null && members.length >= 0)
            {
                for (int i = 0; i < members.length; i++)
                {
                    if (members[i] != player)
                    {
                        sendSystemMessage(members[i], GROUP_QUEST_RECEIVED);
                    }
                    utils.setScriptVar(members[i], "group_space_quest." + questType + "." + questName, 1);
                }
            }
        }
    }
    public static boolean isOnGroupQuest(obj_id player, String questType, String questName) throws InterruptedException
    {
        return utils.hasScriptVar(player, "group_space_quest." + questType + "." + questName);
    }
    public static boolean isOnThisGroupQuest(obj_id quest, obj_id player) throws InterruptedException
    {
        String questName = getStringObjVar(quest, QUEST_NAME);
        String questType = getStringObjVar(quest, QUEST_TYPE);
        obj_id owner = getObjIdObjVar(quest, QUEST_OWNER);
        if (owner == player)
        {
            return true;
        }
        if (group.isGrouped(owner))
        {
            obj_id gid = getGroupObject(owner);
            obj_id[] members = space_utils.getSpaceGroupMemberIds(gid);
            if (members != null && members.length >= 0)
            {
                for (int i = 0; i < members.length; i++)
                {
                    if (members[i] == player)
                    {
                        return isOnGroupQuest(player, questType, questName);
                    }
                }
            }
        }
        return false;
    }
    public static boolean flagQuestGroup(obj_id missionObj, int flag) throws InterruptedException
    {
        String questName = getStringObjVar(missionObj, QUEST_NAME);
        String questType = getStringObjVar(missionObj, QUEST_TYPE);
        obj_id owner = getObjIdObjVar(missionObj, QUEST_OWNER);
        if (group.isGrouped(owner))
        {
            obj_id gid = getGroupObject(owner);
            obj_id[] members = space_utils.getSpaceGroupMemberIds(gid);
            if (members != null && members.length >= 0)
            {
                for (int i = 0; i < members.length; i++)
                {
                    if (isOnGroupQuest(members[i], questType, questName))
                    {
                        if (members[i] != owner)
                        {
                            if (flag == QUEST_FAILED)
                            {
                                sendSystemMessage(members[i], GROUP_QUEST_FAILED);
                            }
                            else if (flag == QUEST_WON)
                            {
                                sendSystemMessage(members[i], GROUP_QUEST_WON);
                            }
                            else if (flag == QUEST_ABORTED)
                            {
                                sendSystemMessage(members[i], GROUP_QUEST_ABORTED);
                            }
                        }
                        _setQuestFlag(members[i], questType, questName, flag);
                        utils.removeScriptVar(members[i], "group_space_quest." + questType + "." + questName);
                    }
                }
            }
        }
        return true;
    }
    public static boolean canTakeGroupQuest(obj_id player, int minGroupSize) throws InterruptedException
    {
        if (group.isGrouped(player))
        {
            obj_id gid = getGroupObject(player);
            obj_id[] members = space_utils.getSpaceGroupMemberIds(gid);
            if (members != null && members.length >= minGroupSize)
            {
                return true;
            }
        }
        return false;
    }
    public static boolean hasCompletedQuest(obj_id player, String questType, String questName) throws InterruptedException
    {
        return hasObjVar(player, QUEST_STATUS + "." + questType + "." + questName);
    }
    public static boolean hasCompletedQuestRecursive(obj_id player, String questType, String questName) throws InterruptedException
    {
        int recNum = utils.getIntScriptVar(player, "recursiveCheck");
        recNum++;
        utils.setScriptVar(player, "recursiveCheck", recNum);
        if (recNum > 20)
        {
            debugSpeakMsg(player, "This quest " + questType + ":" + questName + " is BAD DATA REF'ING ITSELF!");
            utils.removeScriptVar(player, "recursiveCheck");
            return false;
        }
        if (!hasObjVar(player, QUEST_STATUS + "." + questType + "." + questName))
        {
            utils.removeScriptVar(player, "recursiveCheck");
            return false;
        }
        String qTable = "datatables/spacequest/" + questType + "/" + questName + ".iff";
        dictionary questInfo = dataTableGetRow(qTable, 0);
        if (questInfo == null)
        {
            utils.removeScriptVar(player, "recursiveCheck");
            return false;
        }
        int splittype = questInfo.getInt("triggerSplitCondition");
        String splitmission = questInfo.getString("triggerArg");
        if ((splitmission == null) || splitmission.equals("") || (splittype == 2))
        {
            utils.removeScriptVar(player, "recursiveCheck");
            return true;
        }
        else 
        {
            java.util.StringTokenizer st = new java.util.StringTokenizer(splitmission, ":");
            String rtype = st.nextToken();
            String rname = st.nextToken();
            return hasCompletedQuestRecursive(player, rtype, rname);
        }
    }
    public static boolean hasFailedQuest(obj_id player, String questType, String questName) throws InterruptedException
    {
        return _testQuestFlag(player, questType, questName, QUEST_FAILED);
    }
    public static boolean hasFailedQuestRecursive(obj_id player, String questType, String questName) throws InterruptedException
    {
        return _recursiveFlagTest(player, questType, questName, QUEST_FAILED);
    }
    public static boolean hasWonQuest(obj_id player, String questType, String questName) throws InterruptedException
    {
        return _testQuestFlag(player, questType, questName, QUEST_WON);
    }
    public static boolean hasWonQuestRecursive(obj_id player, String questType, String questName) throws InterruptedException
    {
        return _recursiveFlagTestLast(player, questType, questName, QUEST_WON);
    }
    public static boolean hasAbortedQuest(obj_id player, String questType, String questName) throws InterruptedException
    {
        return _testQuestFlag(player, questType, questName, QUEST_ABORTED);
    }
    public static boolean hasAbortedQuestRecursive(obj_id player, String questType, String questName) throws InterruptedException
    {
        return _recursiveFlagTest(player, questType, questName, QUEST_ABORTED);
    }
    public static boolean hasReceivedReward(obj_id player, String questType, String questName) throws InterruptedException
    {
        return _testQuestFlag(player, questType, questName, QUEST_RECEIVED_REWARD);
    }
    public static boolean _recursiveFlagTest(obj_id player, String questType, String questName, int flag) throws InterruptedException
    {
        int recNum = utils.getIntScriptVar(player, "recursiveCheck");
        recNum++;
        utils.setScriptVar(player, "recursiveCheck", recNum);
        if (recNum > 20)
        {
            debugSpeakMsg(player, "This quest " + questType + ":" + questName + " is BAD DATA REF'ING ITSELF!");
            utils.removeScriptVar(player, "recursiveCheck");
            return false;
        }
        boolean test = _testQuestFlag(player, questType, questName, flag);
        if (test)
        {
            utils.removeScriptVar(player, "recursiveCheck");
            return true;
        }
        String qTable = "datatables/spacequest/" + questType + "/" + questName + ".iff";
        dictionary questInfo = dataTableGetRow(qTable, 0);
        if (questInfo == null)
        {
            utils.removeScriptVar(player, "recursiveCheck");
            return false;
        }
        int splittype = questInfo.getInt("triggerSplitCondition");
        String splitmission = questInfo.getString("triggerArg");
        if ((splitmission == null) || splitmission.equals("") || (splittype == 2))
        {
            utils.removeScriptVar(player, "recursiveCheck");
            return _testQuestFlag(player, questType, questName, flag);
        }
        else 
        {
            java.util.StringTokenizer st = new java.util.StringTokenizer(splitmission, ":");
            String rtype = st.nextToken();
            String rname = st.nextToken();
            return _recursiveFlagTest(player, rtype, rname, flag);
        }
    }
    public static boolean _recursiveFlagTestLast(obj_id player, String questType, String questName, int flag) throws InterruptedException
    {
        String qTable = "datatables/spacequest/" + questType + "/" + questName + ".iff";
        dictionary questInfo = dataTableGetRow(qTable, 0);
        if (questInfo == null)
        {
            return false;
        }
        int splittype = questInfo.getInt("triggerSplitCondition");
        String splitmission = questInfo.getString("triggerArg");
        if ((splitmission == null) || splitmission.equals("") || (splittype == 2))
        {
            return _testQuestFlag(player, questType, questName, flag);
        }
        else 
        {
            java.util.StringTokenizer st = new java.util.StringTokenizer(splitmission, ":");
            String rtype = st.nextToken();
            String rname = st.nextToken();
            return _recursiveFlagTest(player, rtype, rname, flag);
        }
    }
    public static boolean setQuestFailed(obj_id player, obj_id missionObj) throws InterruptedException
    {
        return setQuestFailed(player, missionObj, true);
    }
    public static boolean setQuestFailed(obj_id player, obj_id missionObj, boolean canSplit) throws InterruptedException
    {
        String questName = getStringObjVar(missionObj, QUEST_NAME);
        String questType = getStringObjVar(missionObj, QUEST_TYPE);
        dictionary webster = new dictionary();
        webster.put("questName", questName);
        string_id title = new string_id("spacequest/" + questType + "/" + questName, "title");
        prose_package pp = prose.getPackage(SID_QUEST_FAILED, title);
        sendQuestSystemMessage(player, pp);
        _groupNotify(player, SID_GROUP_QUEST_FAILED, title);
        play2dNonLoopingMusic(player, MUSIC_QUEST_FAILED);
        _setQuestFlag(player, questType, questName, QUEST_FAILED);
        flagQuestGroup(missionObj, QUEST_FAILED);
        _removeQuestJournalEntry(player, questType + "/" + questName);
        String groupString = "GROUP:0";
        if (group.isGrouped(player))
        {
            obj_id groupId = getGroupObject(player);
            int numMembers = getGroupSize(groupId);
            if (numMembers > 0)
            {
                groupString = "GROUP:" + numMembers;
            }
        }
        CustomerServiceLog("space_quest", "QUEST_FAILED|V1|" + groupString + "|TIME:" + getGameTime() + "|PLAYER:" + player + "|TYPE:" + questType + "|NAME:" + questName + "|ZONE:" + getCurrentSceneName());
        if ((getIntObjVar(missionObj, QUEST_TRIGGER_EVENT) == 1) && canSplit)
        {
            int triggerD = getIntObjVar(missionObj, QUEST_TRIGGER_DELAY);
            int triggerSC = getIntObjVar(missionObj, QUEST_TRIGGER_SC);
            if ((triggerSC == 2) || (triggerSC == 3))
            {
                dictionary params = new dictionary();
                params.put("quest", missionObj);
                params.put("failure", true);
                params.put("ends", true);
                setObjVar(missionObj, "noAbort", 1);
                messageTo(player, "doSpecialEvent", params, triggerD + 5, false);
            }
            else 
            {
                _removeQuest(player, questType, questName);
                messageTo(player, "groundSpaceFailed", webster, 1, false);
            }
        }
        else 
        {
            _removeQuest(player, questType, questName);
            messageTo(player, "groundSpaceFailed", webster, 1, false);
        }
        return true;
    }
    public static boolean setQuestWon(obj_id player, obj_id missionObj) throws InterruptedException
    {
        return setQuestWon(player, missionObj, true);
    }
    public static boolean setQuestWon(obj_id player, obj_id missionObj, boolean wsplit) throws InterruptedException
    {
        String questName = getStringObjVar(missionObj, QUEST_NAME);
        String questType = getStringObjVar(missionObj, QUEST_TYPE);
        dictionary webster = new dictionary();
        webster.put("questName", questName);
        string_id title = new string_id("spacequest/" + questType + "/" + questName, "title");
        prose_package pp;
        if (hasObjVar(missionObj, QUEST_DUTY))
        {
            pp = prose.getPackage(SID_QUEST_ENDED, title);
            sendQuestSystemMessage(player, pp);
            _groupNotify(player, SID_GROUP_QUEST_ENDED, title);
        }
        else 
        {
            pp = prose.getPackage(SID_QUEST_WON, title);
            sendQuestSystemMessage(player, pp);
            _groupNotify(player, SID_GROUP_QUEST_WON, title);
        }
        _setQuestFlag(player, questType, questName, QUEST_WON);
        flagQuestGroup(missionObj, QUEST_WON);
        String groupString = "GROUP:0";
        if (group.isGrouped(player))
        {
            obj_id groupId = getGroupObject(player);
            int numMembers = getGroupSize(groupId);
            if (numMembers > 0)
            {
                groupString = "GROUP:" + numMembers;
            }
        }
        CustomerServiceLog("space_quest", "QUEST_WON|V1|" + groupString + "|TIME:" + getGameTime() + "|PLAYER:" + player + "|TYPE:" + questType + "|NAME:" + questName + "|ZONE:" + getCurrentSceneName());
        int questid = questGetQuestId("spacequest/" + questType + "/" + questName);
        if ((questid != 0) && questIsQuestActive(questid, player))
        {
            questCompleteQuest(questid, player);
        }
        if (hasObjVar(missionObj, QUEST_AUTO_REWARD))
        {
            int autoReward = getIntObjVar(missionObj, QUEST_AUTO_REWARD);
            string_id autoRewardEmailFrom = new string_id("spacequest/" + questType + "/" + questName, "autorewardfrom");
            string_id autoRewardEmailSubject = new string_id("spacequest/" + questType + "/" + questName, "autorewardsubject");
            string_id autoRewardEmailBody = new string_id("spacequest/" + questType + "/" + questName, "autorewardbody");
            money.bankTo(money.ACCT_SPACE_QUEST_REWARD, player, autoReward);
            pp = prose.getPackage(SID_QUEST_REWARD, autoReward);
            sendQuestSystemMessage(player, pp);
            String player_name = getName(player);
            String body_oob = chatMakePersistentMessageOutOfBandBody(null, autoRewardEmailBody);
            chatSendPersistentMessage("@" + autoRewardEmailFrom.toString(), player_name, "@" + autoRewardEmailSubject.toString(), null, body_oob);
            _setQuestFlag(player, questType, questName, QUEST_RECEIVED_REWARD);
        }
        play2dNonLoopingMusic(player, MUSIC_QUEST_WON);
        if (wsplit && (getIntObjVar(missionObj, QUEST_TRIGGER_EVENT) == 1))
        {
            int triggerOn = getIntObjVar(missionObj, QUEST_TRIGGER_ON);
            int triggerSC = getIntObjVar(missionObj, QUEST_TRIGGER_SC);
            int triggerD = getIntObjVar(missionObj, QUEST_TRIGGER_DELAY);
            boolean split = false;
            if (triggerSC == 0)
            {
                split = (triggerOn == -1);
            }
            else if ((triggerSC == 1) || (triggerSC == 3))
            {
                split = true;
            }
            if (split)
            {
                dictionary params = new dictionary();
                params.put("quest", missionObj);
                webster.put("quest", missionObj);
                params.put("ends", true);
                setObjVar(missionObj, "noAbort", 1);
                messageTo(player, "doSpecialEvent", params, triggerD + 5, false);
            }
            else 
            {
                _removeQuest(player, questType, questName);
                messageTo(player, "groundSpaceWinner", webster, 1, false);
            }
        }
        else 
        {
            _removeQuest(player, questType, questName);
            messageTo(player, "groundSpaceWinner", webster, 1, false);
        }
        return true;
    }
    public static boolean setQuestAborted(obj_id player, obj_id missionObj) throws InterruptedException
    {
        String questName = getStringObjVar(missionObj, QUEST_NAME);
        String questType = getStringObjVar(missionObj, QUEST_TYPE);
        dictionary webster = new dictionary();
        webster.put("questName", questName);
        string_id title = new string_id("spacequest/" + questType + "/" + questName, "title");
        prose_package pp;
        if (hasObjVar(missionObj, QUEST_DUTY))
        {
            pp = prose.getPackage(SID_DUTY_ABORTED, title);
        }
        else 
        {
            pp = prose.getPackage(SID_QUEST_ABORTED, title);
        }
        sendQuestSystemMessage(player, pp);
        _groupNotify(player, SID_GROUP_QUEST_ABORTED, title);
        _setQuestFlag(player, questType, questName, QUEST_ABORTED);
        flagQuestGroup(missionObj, QUEST_ABORTED);
        _removeQuest(player, questType, questName);
        messageTo(player, "groundSpaceFailed", webster, 1, false);
        play2dNonLoopingMusic(player, MUSIC_QUEST_ABORTED);
        _removeQuestJournalEntry(player, questType + "/" + questName);
        String groupString = "GROUP:0";
        if (group.isGrouped(player))
        {
            obj_id groupId = getGroupObject(player);
            int numMembers = getGroupSize(groupId);
            if (numMembers > 0)
            {
                groupString = "GROUP:" + numMembers;
            }
        }
        CustomerServiceLog("space_quest", "QUEST_ABORTED|V1|" + groupString + "|TIME:" + getGameTime() + "|PLAYER:" + player + "|TYPE:" + questType + "|NAME:" + questName + "|ZONE:" + getCurrentSceneName());
        return true;
    }
    public static boolean setSilentQuestAborted(obj_id player, obj_id missionObj) throws InterruptedException
    {
        String questName = getStringObjVar(missionObj, QUEST_NAME);
        String questType = getStringObjVar(missionObj, QUEST_TYPE);
        dictionary webster = new dictionary();
        webster.put("questName", questName);
        _setQuestFlag(player, questType, questName, QUEST_ABORTED);
        flagQuestGroup(missionObj, QUEST_ABORTED);
        _removeQuest(player, questType, questName);
        messageTo(player, "groundSpaceFailed", webster, 1, false);
        _removeQuestJournalEntry(player, questType + "/" + questName);
        String groupString = "GROUP:0";
        if (group.isGrouped(player))
        {
            obj_id groupId = getGroupObject(player);
            int numMembers = getGroupSize(groupId);
            if (numMembers > 0)
            {
                groupString = "GROUP:" + numMembers;
            }
        }
        CustomerServiceLog("space_quest", "QUEST_ABORTED|V1|" + groupString + "|TIME:" + getGameTime() + "|PLAYER:" + player + "|TYPE:" + questType + "|NAME:" + questName + "|ZONE:" + getCurrentSceneName());
        return true;
    }
    public static boolean setQuestRewarded(obj_id player, String questType, String questName) throws InterruptedException
    {
        string_id title = new string_id("spacequest/" + questType + "/" + questName, "title");
        prose_package pp = prose.getPackage(SID_QUEST_REWARDED, title);
        sendQuestSystemMessage(player, pp);
        _setQuestFlag(player, questType, questName, QUEST_RECEIVED_REWARD);
        play2dNonLoopingMusic(player, MUSIC_QUEST_WON);
        return true;
    }
    public static boolean _setQuestInProgress(obj_id quest) throws InterruptedException
    {
        return _setQuestInProgress(quest, false);
    }
    public static boolean _setQuestInProgress(obj_id quest, boolean music) throws InterruptedException
    {
        if (hasObjVar(quest, "in_progress"))
        {
            return false;
        }
        setObjVar(quest, "in_progress", 1);
        obj_id player = getObjIdObjVar(quest, QUEST_OWNER);
        String questName = getStringObjVar(quest, QUEST_NAME);
        String questType = getStringObjVar(quest, QUEST_TYPE);
        string_id title = new string_id("spacequest/" + questType + "/" + questName, "title");
        prose_package pp = prose.getPackage(SID_QUEST_IN_PROGRESS, title);
        sendQuestSystemMessage(player, pp);
        if (music)
        {
            play2dNonLoopingMusic(player, space_quest.MUSIC_QUEST_MISSION_START);
        }
        _groupNotify(player, SID_GROUP_QUEST_IN_PROGRESS, title);
        return true;
    }
    public static boolean isQuestInProgress(obj_id quest) throws InterruptedException
    {
        if (hasObjVar(quest, "in_progress"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public static void showQuestUpdate(obj_id quest, string_id update_id) throws InterruptedException
    {
        obj_id player = getObjIdObjVar(quest, QUEST_OWNER);
        String questName = getStringObjVar(quest, QUEST_NAME);
        prose_package pp;
        if (hasObjVar(quest, QUEST_DUTY))
        {
            pp = prose.getPackage(SID_DUTY_UPDATE_S, update_id);
        }
        else 
        {
            pp = prose.getPackage(SID_QUEST_UPDATE_S, update_id);
        }
        sendQuestSystemMessage(player, pp);
        _groupNotify(player, pp);
    }
    public static void showQuestUpdate(obj_id quest, string_id update_id, int count) throws InterruptedException
    {
        obj_id player = getObjIdObjVar(quest, QUEST_OWNER);
        String questName = getStringObjVar(quest, QUEST_NAME);
        prose_package pp;
        if (hasObjVar(quest, QUEST_DUTY))
        {
            pp = prose.getPackage(SID_DUTY_UPDATE_IS, update_id, count);
        }
        else 
        {
            pp = prose.getPackage(SID_QUEST_UPDATE_IS, update_id, count);
        }
        sendQuestSystemMessage(player, pp);
        _groupNotify(player, pp);
    }
    public static void showQuestAlert(obj_id player, string_id update_id) throws InterruptedException
    {
        prose_package pp = prose.getPackage(SID_QUEST_ALERT_S, update_id);
        sendQuestSystemMessage(player, pp);
        _groupNotify(player, pp);
    }
    public static void showEscortUpdate(obj_id quest, string_id update_id) throws InterruptedException
    {
        obj_id player = getObjIdObjVar(quest, QUEST_OWNER);
        String questName = getStringObjVar(quest, QUEST_NAME);
        prose_package pp = prose.getPackage(SID_ESCORT_UPDATE, update_id);
        sendQuestSystemMessage(player, pp);
        _groupNotify(player, pp);
    }
    public static void giveReward(obj_id player, String questType, String questName, int credits) throws InterruptedException
    {
        giveReward(player, questType, questName, credits, null);
    }
    public static void giveReward(obj_id player, String questType, String questName, int credits, String obj) throws InterruptedException
    {
        setQuestRewarded(player, questType, questName);
        if (credits > 0)
        {
            money.bankTo(money.ACCT_SPACE_QUEST_REWARD, player, credits);
            prose_package pp = prose.getPackage(SID_QUEST_REWARD, credits);
            sendQuestSystemMessage(player, pp);
        }
        if (obj != null)
        {
            obj_id newItem = createObjectInInventoryAllowOverload(obj, player);
            String objectName = utils.packStringId(getNameStringId(newItem));
            prose_package pp = prose.getPackage(SID_QUEST_REWARD_OBJECT, objectName);
            sendQuestSystemMessage(player, pp);
        }
    }
    public static void clearQuestFlags(obj_id player, String questType, String questName) throws InterruptedException
    {
        removeObjVar(player, QUEST_STATUS + "." + questType + "." + questName);
        int questNum = questGetQuestId("spacequest/" + questType + "/" + questName);
        debugSpeakMsg(player, "Quest ID is" + questNum);
        questClearQuest(questNum, player);
        obj_id quest = _getQuest(player, questType, questName);
        if (isIdValid(quest) && exists(quest))
        {
            setQuestAborted(player, quest);
        }
    }
    public static int _getQuestId(String questName) throws InterruptedException
    {
        return dataTableSearchColumnForString(questName, 0, "datatables/player/quests.iff");
    }
    public static boolean _setupQuest(obj_id player, String questType, String questName, String qTable, boolean duty) throws InterruptedException
    {
        if (duty && _hasDutyQuest(player))
        {
            sendQuestSystemMessage(player, SID_DUTY_ALREADY);
            return false;
        }
        obj_id datapad = utils.getPlayerDatapad(player);
        if (!isIdValid(datapad))
        {
            sendSystemMessageTestingOnly(player, "_setupQuest: Unable to get datapad for player " + player);
            return false;
        }
        obj_id missionObj = createObject(QUEST_OBJECT_TEMPLATE, datapad, "");
        if (!isIdValid(missionObj))
        {
            sendSystemMessageTestingOnly(player, "_setupQuest: Unable to create a mission object for quest.");
            return false;
        }
        dictionary questInfo = dataTableGetRow(qTable, 0);
        setObjVar(missionObj, QUEST_OWNER, player);
        setObjVar(missionObj, QUEST_NAME, questName);
        setObjVar(missionObj, QUEST_TYPE, questType);
        setObjVar(missionObj, QUEST_ZONE, questInfo.getString("questZone"));
        setObjVar(missionObj, "self", missionObj);
        if (duty)
        {
            setObjVar(missionObj, QUEST_DUTY, duty);
        }
        setMissionDescription(missionObj, new string_id("spacequest/" + questType + "/" + questName, "title_d"));
        setMissionTitle(missionObj, new string_id("space/quest", questType));
        if (questType.equals("space_battle") || questType.equals("space_mining_destroy"))
        {
            setMissionType(missionObj, questType);
        }
        else if (questType.equals("assassinate"))
        {
            setMissionType(missionObj, "space_assassination");
        }
        else if (questType.equals("inspect"))
        {
            setMissionType(missionObj, "space_inspection");
        }
        else if (questType.equals("destroy_surpriseattack"))
        {
            setMissionType(missionObj, "space_surprise_attack");
        }
        else if (questType.equals("delivery_no_pickup"))
        {
            setMissionType(missionObj, "space_delivery");
        }
        else 
        {
            setMissionType(missionObj, "space_" + questType);
        }
        int autoReward = questInfo.getInt("autoReward");
        if (autoReward > 0)
        {
            setObjVar(missionObj, QUEST_AUTO_REWARD, autoReward);
        }
        int triggerEvent = questInfo.getInt("triggerEvent");
        if (triggerEvent > 0)
        {
            setObjVar(missionObj, QUEST_TRIGGER_EVENT, triggerEvent);
            setObjVar(missionObj, QUEST_TRIGGER_SC, questInfo.getInt("triggerSplitCondition"));
            setObjVar(missionObj, QUEST_TRIGGER_ARG, questInfo.getString("triggerArg"));
            setObjVar(missionObj, QUEST_TRIGGER_ARG2, questInfo.getString("triggerArg2"));
            setObjVar(missionObj, QUEST_TRIGGER_ON, questInfo.getInt("triggerOn"));
            if (questInfo.getInt("triggerEnds") == 1)
            {
                setObjVar(missionObj, QUEST_TRIGGER_ENDS, 1);
            }
            if (questInfo.getInt("triggerEndsMission") == 1)
            {
                setObjVar(missionObj, QUEST_TRIGGER_ENDS, 1);
            }
            setObjVar(missionObj, QUEST_TRIGGER_DELAY, questInfo.getInt("triggerDelay"));
        }
        int questid = questGetQuestId("spacequest/" + questType + "/" + questName);
        if ((questid != 0) && (questCanActivateQuest(questid, player) == 0))
        {
            questActivateQuest(questid, player, null);
        }
        attachScript(missionObj, "space.quest_logic." + questType);
        if (!hasScript(player, "space.quest_logic.player_spacequest"))
        {
            attachScript(player, "space.quest_logic.player_spacequest");
        }
        _clearQuestFlag(player, questType, questName, QUEST_ABORTED);
        _clearQuestFlag(player, questType, questName, QUEST_FAILED);
        _clearQuestFlag(player, questType, questName, QUEST_WON);
        return true;
    }
    public static boolean _removeQuest(obj_id player, String questType, String questName) throws InterruptedException
    {
        obj_id missionObj = _getQuest(player, questType, questName);
        _removeQuest(player, missionObj);
        return true;
    }
    public static boolean _removeQuest(obj_id player, obj_id missionObj) throws InterruptedException
    {
        _removeMissionCriticalShips(player, missionObj);
        destroyObject(missionObj);
        return true;
    }
    public static boolean _hasDutyQuest(obj_id player) throws InterruptedException
    {
        obj_id datapad = utils.getPlayerDatapad(player);
        if (isIdValid(datapad))
        {
            obj_id[] dpobjs = getContents(datapad);
            for (int i = 0; i < dpobjs.length; i++)
            {
                if (hasObjVar(dpobjs[i], QUEST_DUTY))
                {
                    return true;
                }
            }
        }
        return false;
    }
    public static int _getQuestStatus(obj_id player, String questType, String questName) throws InterruptedException
    {
        return getIntObjVar(player, QUEST_STATUS + "." + questType + "." + questName);
    }
    public static int _setQuestStatus(obj_id player, String questType, String questName, int flags) throws InterruptedException
    {
        setObjVar(player, QUEST_STATUS + "." + questType + "." + questName, flags);
        return flags;
    }
    public static boolean _testQuestFlag(obj_id player, String questType, String questName, int flag) throws InterruptedException
    {
        if (hasCompletedQuest(player, questType, questName))
        {
            int questStatus = _getQuestStatus(player, questType, questName);
            return ((questStatus & flag) != 0);
        }
        return false;
    }
    public static void _setQuestFlag(obj_id player, String questType, String questName, int flag) throws InterruptedException
    {
        int questStatus = _getQuestStatus(player, questType, questName);
        questStatus |= flag;
        _setQuestStatus(player, questType, questName, questStatus);
    }
    public static void _clearQuestFlag(obj_id player, String questType, String questName, int flag) throws InterruptedException
    {
        int questStatus = _getQuestStatus(player, questType, questName);
        questStatus = questStatus & ~flag;
        _setQuestStatus(player, questType, questName, questStatus);
    }
    public static boolean hasCompletedQuestSeries(obj_id player, String questSeries) throws InterruptedException
    {
        int flags = getIntObjVar(player, QUEST_STATUS_SERIES + "." + questSeries);
        return ((flags & QUEST_SERIES_COMPLETED) != 0);
    }
    public static void setQuestSeriesFlag(obj_id player, String questSeries, int flag) throws InterruptedException
    {
        int flags = getIntObjVar(player, QUEST_STATUS_SERIES + "." + questSeries);
        flags |= flag;
        setObjVar(player, QUEST_STATUS_SERIES + "." + questSeries, flags);
    }
    public static void clearQuestSeriesFlags(obj_id player, String questSeries) throws InterruptedException
    {
        removeObjVar(player, QUEST_STATUS_SERIES + "." + questSeries);
    }
    public static final String FAMILIARITY_LIST = "familiarity.";
    public static void setFamiliarity(obj_id player, String label, int value) throws InterruptedException
    {
        if (!isPlayer(player))
        {
            return;
        }
        setObjVar(player, FAMILIARITY_LIST + label, value);
    }
    public static int getFamiliarity(obj_id player, String label) throws InterruptedException
    {
        return (getIntObjVar(player, FAMILIARITY_LIST + label));
    }
    public static int adjustFamiliarity(obj_id player, String label, int value) throws InterruptedException
    {
        int currentFamiliarity = getFamiliarity(player, label);
        currentFamiliarity += value;
        setFamiliarity(player, label, currentFamiliarity);
        return currentFamiliarity;
    }
    public static void clearFamiliarity(obj_id player, String label) throws InterruptedException
    {
        removeObjVar(player, FAMILIARITY_LIST + label);
    }
    public static boolean isPlayerQualifiedForSkill(obj_id objPlayer, String strSkill) throws InterruptedException
    {
        boolean boolHasSkills = skill.hasRequiredSkillsForSkillPurchase(objPlayer, strSkill);
        boolean boolHasXp = skill.hasRequiredXpForSkillPurchase(objPlayer, strSkill);
        if ((boolHasSkills) && (boolHasXp))
        {
            return true;
        }
        return false;
    }
    public static transform getRandomPositionInSphere(transform trStartPosition, float fltMinRadius, float fltMaxRadius) throws InterruptedException
    {
        float fltDistance = rand(fltMinRadius, fltMaxRadius);
        vector vctOffset = (vector.randomUnit()).multiply(fltDistance);
        transform transform_w = trStartPosition;
        transform_w = transform_w.move_p(vctOffset);
        return transform_w;
    }
    public static void cleanArray(obj_id quest, String objVar, String[] array) throws InterruptedException
    {
        if (array == null)
        {
            return;
        }
        int k = 0;
        for (int i = 0; i < array.length; i++)
        {
            if (!array[i].equals(""))
            {
                k++;
            }
        }
        if (k == 0)
        {
            return;
        }
        String[] newarray = new String[k];
        for (int i = 0; i < k; i++)
        {
            newarray[i] = array[i];
        }
        setObjVar(quest, objVar, newarray);
    }
    public static void cleanArray(obj_id quest, String objVar, int[] array) throws InterruptedException
    {
        if (array == null)
        {
            return;
        }
        int k = 0;
        for (int i = 0; i < array.length; i++)
        {
            if (array[i] != -1)
            {
                k++;
            }
        }
        if (k == 0)
        {
            return;
        }
        int[] newarray = new int[k];
        for (int i = 0; i < k; i++)
        {
            newarray[i] = array[i];
        }
        setObjVar(quest, objVar, newarray);
    }
    public static boolean hasShip(obj_id player) throws InterruptedException
    {
        obj_id[] shipControlDevices = space_transition.findShipControlDevicesForPlayer(player);
        if (shipControlDevices != null && shipControlDevices.length > 0)
        {
            return true;
        }
        return false;
    }
    public static boolean hasCertifiedShip(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player) || !isPlayer(player))
        {
            return false;
        }
        obj_id[] shipControlDevices = space_transition.findShipControlDevicesForPlayer(player);
        if (shipControlDevices == null || shipControlDevices.length == 0)
        {
            return false;
        }
        for (int i = 0; i < shipControlDevices.length; i++)
        {
            obj_id ship = space_transition.getShipFromShipControlDevice(shipControlDevices[i]);
            if (!isIdValid(ship))
            {
                return false;
            }
            if (hasCertificationsForItem(player, ship))
            {
                String template = getTemplateName(ship);
                if (template.endsWith("player_sorosuub_space_yacht.iff"))
                {
                    continue;
                }
                else 
                {
                    return true;
                }
            }
        }
        return false;
    }
    public static boolean hasCertifiedNonNewbieShip(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player) || !isPlayer(player))
        {
            return false;
        }
        obj_id[] shipControlDevices = space_transition.findShipControlDevicesForPlayer(player);
        if (shipControlDevices == null || shipControlDevices.length == 0)
        {
            return false;
        }
        for (int i = 0; i < shipControlDevices.length; i++)
        {
            obj_id ship = space_transition.getShipFromShipControlDevice(shipControlDevices[i]);
            if (!isIdValid(ship))
            {
                return false;
            }
            if (hasCertificationsForItem(player, ship))
            {
                String template = getTemplateName(ship);
                if (template.endsWith("player_basic_tiefighter.iff"))
                {
                    continue;
                }
                else if (template.endsWith("player_basic_z95.iff"))
                {
                    continue;
                }
                else if (template.endsWith("player_basic_hutt_light.iff"))
                {
                    continue;
                }
                else if (template.endsWith("player_sorosuub_space_yacht.iff"))
                {
                    continue;
                }
                else 
                {
                    return true;
                }
            }
        }
        return false;
    }
    public static boolean canGrantNewbieShip(obj_id player) throws InterruptedException
    {
        if (hasCertifiedShip(player))
        {
            return false;
        }
        if (!space_transition.isPlayerBelowShipLimit(player))
        {
            return false;
        }
        return true;
    }
    public static void grantNewbieShip(obj_id player, String faction) throws InterruptedException
    {
        obj_id datapad = utils.getDatapad(player);
        if (!isIdValid(datapad))
        {
            return;
        }
        String pcd = null;
        String ship = null;
        if (faction.equals("imperial"))
        {
            pcd = "object/intangible/ship/tiefighter_pcd.iff";
            ship = "object/ship/player/player_prototype_tiefighter.iff";
        }
        else if (faction.equals("rebel"))
        {
            pcd = "object/intangible/ship/z95_pcd.iff";
            ship = "object/ship/player/player_prototype_z95.iff";
        }
        else if (faction.equals("neutral"))
        {
            pcd = "object/intangible/ship/hutt_light_s01_pcd.iff";
            ship = "object/ship/player/player_prototype_hutt_light.iff";
        }
        obj_id opcd = createObject(pcd, datapad, "");
        if (!isIdValid(opcd))
        {
            sendSystemMessageTestingOnly(player, "Critical failure: Could not create a pcd for the player!");
        }
        else 
        {
            obj_id oship = createObject(ship, opcd, "");
            if (isIdValid(oship))
            {
                setOwner(oship, player);
                if (faction.equals("imperial"))
                {
                    setShipComponentMass(oship, ship_chassis_slot_type.SCST_reactor, 1500);
                    setShipReactorEnergyGenerationRate(oship, 8000);
                    setShipComponentMass(oship, ship_chassis_slot_type.SCST_engine, 1500);
                    setShipComponentEnergyMaintenanceRequirement(oship, ship_chassis_slot_type.SCST_engine, 2000);
                    setShipComponentMass(oship, ship_chassis_slot_type.SCST_shield_0, 1500);
                    setShipComponentEnergyMaintenanceRequirement(oship, ship_chassis_slot_type.SCST_shield_0, 2000);
                    setShipComponentMass(oship, ship_chassis_slot_type.SCST_armor_0, 1500);
                    setShipComponentMass(oship, ship_chassis_slot_type.SCST_armor_1, 1500);
                    setShipComponentMass(oship, ship_chassis_slot_type.SCST_capacitor, 1500);
                    setShipComponentEnergyMaintenanceRequirement(oship, ship_chassis_slot_type.SCST_capacitor, 2000);
                    setShipComponentMass(oship, ship_chassis_slot_type.SCST_weapon_0, 1500);
                    setShipComponentEnergyMaintenanceRequirement(oship, ship_chassis_slot_type.SCST_weapon_0, 2000);
                }
                else if (faction.equals("rebel") || faction.equals("neutral"))
                {
                    setShipComponentMass(oship, ship_chassis_slot_type.SCST_reactor, 1500);
                    setShipReactorEnergyGenerationRate(oship, 8000);
                    setShipComponentMass(oship, ship_chassis_slot_type.SCST_engine, 1500);
                    setShipComponentEnergyMaintenanceRequirement(oship, ship_chassis_slot_type.SCST_engine, 2000);
                    setShipComponentMass(oship, ship_chassis_slot_type.SCST_shield_0, 1500);
                    setShipComponentEnergyMaintenanceRequirement(oship, ship_chassis_slot_type.SCST_shield_0, 2000);
                    setShipComponentMass(oship, ship_chassis_slot_type.SCST_armor_0, 1500);
                    setShipComponentMass(oship, ship_chassis_slot_type.SCST_armor_1, 1500);
                    setShipComponentMass(oship, ship_chassis_slot_type.SCST_capacitor, 1500);
                    setShipComponentEnergyMaintenanceRequirement(oship, ship_chassis_slot_type.SCST_capacitor, 2000);
                    setShipComponentMass(oship, ship_chassis_slot_type.SCST_weapon_0, 1500);
                    setShipComponentEnergyMaintenanceRequirement(oship, ship_chassis_slot_type.SCST_weapon_0, 2000);
                }
            }
            else 
            {
                sendSystemMessageTestingOnly(player, "Critical failure: Could not create a ship for the player's pcd!");
            }
        }
    }
    public static void _groupNotify(obj_id player, string_id mid, string_id s1) throws InterruptedException
    {
        if (group.isGrouped(player))
        {
            obj_id gid = getGroupObject(player);
            obj_id[] members = space_utils.getSpaceGroupMemberIds(gid);
            if (members != null)
            {
                for (int i = 0; i < members.length; i++)
                {
                    if (members[i] == player)
                    {
                        continue;
                    }
                    prose_package pp = prose.getPackage(mid, s1, getName(player));
                    sendQuestSystemMessage(members[i], pp);
                }
            }
        }
    }
    public static void _groupNotify(obj_id player, prose_package pp) throws InterruptedException
    {
        if (group.isGrouped(player))
        {
            obj_id gid = getGroupObject(player);
            obj_id[] members = space_utils.getSpaceGroupMemberIds(gid);
            if (members != null)
            {
                for (int i = 0; i < members.length; i++)
                {
                    if (members[i] == player)
                    {
                        continue;
                    }
                    sendQuestSystemMessage(members[i], pp);
                }
            }
        }
    }
    public static void _groupNotify(obj_id player, string_id mid) throws InterruptedException
    {
        if (group.isGrouped(player))
        {
            obj_id gid = getGroupObject(player);
            obj_id[] members = space_utils.getSpaceGroupMemberIds(gid);
            if (members != null)
            {
                for (int i = 0; i < members.length; i++)
                {
                    if (members[i] == player)
                    {
                        continue;
                    }
                    sendQuestSystemMessage(members[i], mid);
                }
            }
        }
    }
    public static void sendQuestMessage(obj_id player, prose_package pp) throws InterruptedException
    {
        sendQuestSystemMessage(player, pp);
        _groupNotify(player, pp);
    }
    public static void sendQuestMessage(obj_id player, string_id mid) throws InterruptedException
    {
        sendQuestSystemMessage(player, mid);
        _groupNotify(player, mid);
    }
    public static void groupTaunt(obj_id ship, obj_id player, prose_package pp) throws InterruptedException
    {
        if (!isIdValid(player) || !exists(player))
        {
            return;
        }
        if (group.isGrouped(player))
        {
            obj_id gid = getGroupObject(player);
            obj_id[] members = space_utils.getSpaceGroupMemberIds(gid);
            if (members != null)
            {
                space_utils.tauntArray(ship, new Vector(Arrays.asList(members)), pp);
            }
        }
        else 
        {
            _tauntShip(ship, space_transition.getContainingShip(player), pp);
        }
    }
    public static void _removeQuestJournalEntry(obj_id player, String quest) throws InterruptedException
    {
        quest = "spacequest/" + quest;
        int questid = questGetQuestId(quest);
        if (questid != 0)
        {
            questClearQuest(questid, player);
        }
    }
    public static void _addMissionCriticalShip(obj_id player, obj_id quest, obj_id ship) throws InterruptedException
    {
        addMissionCriticalObject(player, ship);
    }
    public static void _removeMissionCriticalShip(obj_id player, obj_id quest, obj_id ship) throws InterruptedException
    {
        removeMissionCriticalObject(player, ship);
    }
    public static boolean _isMissionCriticalShip(obj_id player, obj_id quest, obj_id ship) throws InterruptedException
    {
        return isMissionCriticalObject(player, ship);
    }
    public static void _removeMissionCriticalShips(obj_id player, obj_id quest) throws InterruptedException
    {
        clearMissionCriticalObjects(player);
    }
    public static void _tauntShip(obj_id ship, obj_id target, prose_package pp) throws InterruptedException
    {
        if (isIdValid(target) && isIdValid(ship))
        {
            space_utils.tauntShip(target, ship, pp, true, true, false, false);
        }
    }
    public static void _tauntShips(obj_id ship, obj_id[] targets, prose_package pp) throws InterruptedException
    {
        for (int i = 0; i < targets.length; i++)
        {
            if (isIdValid(targets[i]) && isIdValid(ship))
            {
                space_utils.tauntShip(targets[i], ship, pp, true, true, false, false);
            }
        }
    }
    public static void notifyMissions(obj_id player, String msg, dictionary params) throws InterruptedException
    {
        obj_id datapad = utils.getPlayerDatapad(player);
        if (isIdValid(datapad))
        {
            obj_id[] dpobjs = getContents(datapad);
            for (int i = 0; i < dpobjs.length; i++)
            {
                if (hasObjVar(dpobjs[i], space_quest.QUEST_NAME))
                {
                    space_utils.notifyObject(dpobjs[i], msg, params);
                }
            }
        }
    }
    public static void cleanupOnUnload(obj_id player) throws InterruptedException
    {
        obj_id datapad = utils.getPlayerDatapad(player);
        if (isIdValid(datapad))
        {
            obj_id[] dpobjs = getContents(datapad);
            for (int i = 0; i < dpobjs.length; i++)
            {
                if (hasObjVar(dpobjs[i], QUEST_NAME))
                {
                    String type = getStringObjVar(dpobjs[i], QUEST_TYPE);
                    if (type.equals("assassinate") || type.equals("recovery"))
                    {
                        if (isQuestInProgress(dpobjs[i]))
                        {
                            recoveryCleanUpShips(dpobjs[i]);
                        }
                    }
                }
            }
        }
    }
    public static void recoveryCleanUpShips(obj_id quest) throws InterruptedException
    {
        obj_id ship = getObjIdObjVar(quest, "target");
        if (isIdValid(ship) && exists(ship))
        {
            if (!ship.isBeingDestroyed())
            {
                destroyObjectHyperspace(ship);
            }
        }
        obj_id[] escorts = getObjIdArrayObjVar(quest, "escorts");
        if (escorts != null)
        {
            for (int i = 0; i < escorts.length; i++)
            {
                if (isIdValid(escorts[i]) && exists(escorts[i]))
                {
                    destroyObjectHyperspace(escorts[i]);
                }
            }
        }
    }
    public static obj_id findQuestLocation(obj_id quest, obj_id player, String navName, String type) throws InterruptedException
    {
        obj_id questManager = getNamedObject(QUEST_MANAGER);
        if (isIdValid(questManager))
        {
            obj_id[] navs = utils.getObjIdArrayScriptVar(questManager, type + "_list");
            if (navs == null)
            {
                debugServerConsoleMsg(questManager, "Quest Manager: Warning! Zone " + getCurrentSceneName() + " has not been initialized properly, but has been queried for a quest!");
                return null;
            }
            for (int i = 0; i < navs.length; ++i)
            {
                String curNavName = getStringObjVar(navs[i], "nav_name");
                if (curNavName != null && curNavName.equals(navName))
                {
                    return navs[i];
                }
            }
        }
        debugServerConsoleMsg(questManager, "Error: Failed to find nav point '" + navName + "'.  The nav point object may not exist or your server may need to specify 'createZoneObjects=1'");
        return null;
    }
}
