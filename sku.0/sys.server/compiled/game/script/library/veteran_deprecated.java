package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.features;

public class veteran_deprecated extends script.base_script
{
    public veteran_deprecated()
    {
    }
    public static final int UNENTITLED_TIME_LIMIT = 14;
    public static final int MONTHS_PER_MILESTONE = 3;
    public static final int DAYS_PER_MONTH = 30;
    public static final int DAYS_PER_MILESTONE = MONTHS_PER_MILESTONE * DAYS_PER_MONTH;
    public static final int REWARD_FLAGS_SIZE = ((12 / MONTHS_PER_MILESTONE) * 10) / 32 + 1;
    public static final int MAX_MILESTONE = REWARD_FLAGS_SIZE * 32;
    public static final String VETERAN_STRING_TABLE = "veteran";
    public static final String REWARDS_DATATABLE = "datatables/veteran/rewards.iff";
    public static final String REWARDS_COLUMN_TEMPLATE = "template";
    public static final String REWARDS_COLUMN_SCRIPT = "script";
    public static final String REWARDS_COLUMN_MILESTONE = "milestone";
    public static final String REWARDS_COLUMN_ONETIME = "oneTime";
    public static final String REWARDS_COLUMN_NOTRADE = "noTrade";
    public static final String REWARDS_COLUMN_NODELETE = "noDelete";
    public static final String REWARDS_COLUMN_NOMOVE = "noMove";
    public static final String REWARDS_COLUMN_REQUIREDGAMEFEATUREBITANY = "requiredGameFeatureBitAny";
    public static final String REWARDS_COLUMN_REQUIREDGAMEFEATUREBITALL = "requiredGameFeatureBitAll";
    public static final String REWARDS_COLUMN_REQUIREDSUBSCRIPTIONFEATUREBITANY = "requiredSubscriptionFeatureBitAny";
    public static final String REWARDS_COLUMN_REQUIREDSUBSCRIPTIONFEATUREBITALL = "requiredSubscriptionFeatureBitAll";
    public static final String EMOTES_DATATABLE = "datatables/veteran/emotes.iff";
    public static final String EMOTES_COLUMN_EMOTE = "emote";
    public static final String EMOTES_COLUMN_GROUP = "group";
    public static final String HANDLER_SELECT_VETERAN_REWARD = "handleVeteranRewardSelected";
    public static final String HANDLER_SELECT_VETERAN_MILESTONE = "handleVeteranMilestoneSelected";
    public static final String HANDLER_SELECT_VETERAN_REWARD_CONFIRM = "handleVeteranRewardConfirmed";
    public static final String OBJVAR_TIME_ACTIVE = "veteran.timeActive";
    public static final String OBJVAR_REWARDS_RECEIVED = "veteran.rewardsReceived";
    public static final String OBJVAR_MILESTONES_NOTIFIED = "veteran.milestonesNotified";
    public static final String OBJVAR_ONETIME_REWARDS_RECEIVED = "veteran.onetime";
    public static final String OBJVAR_VETERAN_EMOTES = "veteran.emotes";
    public static final String OBJVAR_ONE_YEAR_ANNIVERSARY = "anniversary.oneYear";
    public static final String OBJVAR_ONE_YEAR_ANNIVERSARY_OVERRIDE = "anniversary.override";
    public static final String TEMPLATE_ONE_YEAR_ANNIVERSARY = "object/tangible/veteran_reward/one_year_anniversary/painting_";
    public static final string_id SID_ONE_YEAR_ANNIVERSARY = new string_id(VETERAN_STRING_TABLE, "one_year_anniversary");
    public static final string_id SID_ONE_YEAR_ANNIVERSARY_SUBJECT = new string_id(VETERAN_STRING_TABLE, "one_year_anniversary_subject");
    public static final string_id SID_ONE_YEAR_ANNIVERSARY_FROM = new string_id(VETERAN_STRING_TABLE, "one_year_anniversary_from");
    public static final string_id SID_ONE_YEAR_ANNIVERSARY_INVENTORY_FULL = new string_id(VETERAN_STRING_TABLE, "one_year_inventory_full");
    public static final string_id SID_ONE_YEAR_ANNIVERSARY_INVENTORY_FULL_SUBJECT = new string_id(VETERAN_STRING_TABLE, "one_year_inventory_full_subject");
    public static final int ONE_YEAR_ANNIVERSARY_REWARD_COUNT = 9;
    public static final int MIN_ONE_YEAR_ANNIVERSARY_TIME = 30;
    public static final int FLASH_SPEEDER_COST = 20000;
    public static final int CAN_GET_REWARD_SUCCESS = 0;
    public static final int CAN_GET_REWARD_FAIL_INADEQUATE_MILESTONE = 1;
    public static final int CAN_GET_REWARD_FAIL_ALREADY_CLAIMED = 2;
    public static final int CAN_GET_REWARD_FAIL_INADEQUATE_SUBSCRIPTION = 3;
    public static final int GIVE_PLAYER_REWARD_SUCCESS = 0;
    public static final int GIVE_PLAYER_REWARD_FAILED = 1;
    public static final int GIVE_PLAYER_REWARD_MORE_PROCESSING_REQUIRED = 2;
    public static final String OBJVAR_FAKE_VETERAN = "_testVeteran";
    public static final String OBJVAR_FAKE_VETERAN_TOTAL_TIME = OBJVAR_FAKE_VETERAN + ".total_time";
    public static final String OBJVAR_FAKE_VETERAN_ENTITLED_TIME = OBJVAR_FAKE_VETERAN + ".entitled_time";
    public static final String OBJVAR_FAKE_VETERAN_LOGIN_TIME = OBJVAR_FAKE_VETERAN + ".login_time";
    public static final String OBJVAR_FAKE_VETERAN_ENTITLED_LOGIN_TIME = OBJVAR_FAKE_VETERAN + ".entitled_login_time";
    public static final String SCRIPTVAR_VETERAN_LOGGED_IN = "veteran.loggedIn";
    public static final String SCRIPTVAR_SELECTED_REWARD = "veteran.selectedReward";
    public static final String SCRIPTVAR_SELECTED_MILESTONE = "veteran.selectedMilestone";
    public static final String SCRIPTVAR_AVAILABLE_MILESTONES = "veteran.milestones";
    public static final string_id SID_VETERAN_NEW_REWARD = new string_id(VETERAN_STRING_TABLE, "new_reward");
    public static final string_id SID_VETERAN_NEW_REWARD_SUBJECT = new string_id(VETERAN_STRING_TABLE, "new_reward_subject");
    public static final string_id SID_VETERAN_NEW_REWARD_FROM = new string_id(VETERAN_STRING_TABLE, "new_reward_from");
    public static final string_id SID_VETERAN_NEW_REWARD_MSGBOX = new string_id(VETERAN_STRING_TABLE, "new_reward_msgbox");
    public static final string_id SID_VETERAN_SELECT_MILESTONE_PROMPT = new string_id(VETERAN_STRING_TABLE, "select_milestone");
    public static final string_id SID_VETERAN_SELECT_REWARD_PROMPT = new string_id(VETERAN_STRING_TABLE, "select_reward");
    public static final string_id SID_VETERAN_SELECT_REWARD_CONFIRM = new string_id(VETERAN_STRING_TABLE, "select_reward_confirm");
    public static final string_id SID_VETERAN_TIME_ACTIVE = new string_id(VETERAN_STRING_TABLE, "time_active");
    public static final string_id SID_VETERAN_SELF_TIME_ACTIVE = new string_id(VETERAN_STRING_TABLE, "self_time_active");
    public static final string_id SID_NOT_ELIGIBLE = new string_id(VETERAN_STRING_TABLE, "not_eligible");
    public static final string_id SID_REWARD_GIVEN = new string_id(VETERAN_STRING_TABLE, "reward_given");
    public static final string_id SID_REWARD_ERROR = new string_id(VETERAN_STRING_TABLE, "reward_error");
    public static final string_id SID_SYSTEM_INACTIVE = new string_id(VETERAN_STRING_TABLE, "system_inactive");
    public static final string_id SID_INVALID_TARGET = new string_id(VETERAN_STRING_TABLE, "invalid_target");
    public static final string_id SID_MILESTONE_OUT_OF_RANGE = new string_id(VETERAN_STRING_TABLE, "milestone_out_of_range");
    public static final string_id SID_HAS_MILESTONE = new string_id(VETERAN_STRING_TABLE, "has_milestone");
    public static final string_id SID_HAS_NOT_MILESTONE = new string_id(VETERAN_STRING_TABLE, "has_not_milestone");
    public static final string_id SID_OK = new string_id(VETERAN_STRING_TABLE, "ok");
    public static final string_id SID_ACTIVE_MONTHS_CLEARED = new string_id(VETERAN_STRING_TABLE, "active_months_cleared");
    public static final string_id SID_BAD_MILESTONE = new string_id(VETERAN_STRING_TABLE, "bad_milestone");
    public static final string_id SID_BAD_REWARD = new string_id(VETERAN_STRING_TABLE, "bad_reward");
    public static final string_id SID_DAYS = new string_id(VETERAN_STRING_TABLE, "days");
    public static final string_id SID_RANK = new string_id(VETERAN_STRING_TABLE, "rank");
    public static final string_id SID_UNAVAILABLE = new string_id(VETERAN_STRING_TABLE, "unavailable");
    public static final string_id SID_UNAVAILABLE_CLAIMED = new string_id(VETERAN_STRING_TABLE, "unavailable_claimed");
    public static final string_id SID_UNAVAILABLE_NEEDS_EXPANSION = new string_id(VETERAN_STRING_TABLE, "unavailable_needs_expansion");
    public static final string_id SID_UNAVAILABLE_NOT_ENOUGH_MILESTONE = new string_id(VETERAN_STRING_TABLE, "unavailable_not_enough_milestone");
    public static final string_id SID_UNKNOWN = new string_id(VETERAN_STRING_TABLE, "unknown");
    public static void updateVeteranTime(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return;
        }
        if (!("true").equals(getConfigSetting("GameServer", "enableVeteranRewards")))
        {
            return;
        }
        if ((player.getScriptVars()).hasKey(SCRIPTVAR_VETERAN_LOGGED_IN))
        {
            return;
        }
        (player.getScriptVars()).put(SCRIPTVAR_VETERAN_LOGGED_IN, true);
        dictionary timeData = getAccountTimeData(player);
        if (timeData == null)
        {
            return;
        }
        int totalEntitledTime = timeData.getInt("total_entitled_time");
        if (!hasObjVar(player, OBJVAR_TIME_ACTIVE))
        {
            setObjVar(player, OBJVAR_TIME_ACTIVE, totalEntitledTime);
            setObjVar(player, OBJVAR_REWARDS_RECEIVED, new int[REWARD_FLAGS_SIZE]);
            setObjVar(player, OBJVAR_MILESTONES_NOTIFIED, new int[REWARD_FLAGS_SIZE]);
        }
        else 
        {
            setObjVar(player, OBJVAR_TIME_ACTIVE, totalEntitledTime);
        }
    }
    public static boolean canGetReward(obj_id player) throws InterruptedException
    {
        int[] milestones = getVeteranRewardMilestones(player);
        return ((milestones != null) && (milestones.length >= 1));
    }
    public static boolean requestVeteranRewards(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return false;
        }
        int[] milestones = getVeteranRewardMilestones(player);
        if ((milestones == null) || (milestones.length < 1))
        {
            return false;
        }
        int veteranTime = getIntObjVar(player, OBJVAR_TIME_ACTIVE);
        if (veteranTime <= 0)
        {
            CustomerServiceLog("veteran", "requestVeteranRewards called for player %TU with no active time", player);
            return false;
        }
        int firstMilestone = veteranTime / DAYS_PER_MILESTONE;
        if (firstMilestone < 1)
        {
            return false;
        }
        if (firstMilestone > MAX_MILESTONE)
        {
            CustomerServiceLog("veteran", "requestVeteranRewards called for player %TU who has a milestone " + firstMilestone + " which is greater than the maximum supported milestone " + MAX_MILESTONE, player);
            firstMilestone = MAX_MILESTONE;
        }
        String daysText = getString(SID_DAYS);
        String alreadyClaimedText = getString(SID_UNAVAILABLE_CLAIMED);
        Vector milestonesText = new Vector();
        for (int i = 0; i < firstMilestone; ++i)
        {
            if (i == 1)
            {
                if (checkVeteranMilestone(player, (i + 1)))
                {
                    milestonesText.add(((i + 1) * DAYS_PER_MILESTONE) + daysText + " (" + alreadyClaimedText + ")");
                }
                else 
                {
                    milestonesText.add(((i + 1) * DAYS_PER_MILESTONE) + daysText);
                }
            }
        }
        int pid = sui.listbox(player, player, "@" + SID_VETERAN_SELECT_MILESTONE_PROMPT, sui.OK_CANCEL, sui.DEFAULT_TITLE, milestonesText, HANDLER_SELECT_VETERAN_MILESTONE, true);
        if (pid < 0)
        {
            CustomerServiceLog("veteran", "Could not create sui reward list for player %TU, error = " + pid, player);
            return false;
        }
        (player.getScriptVars()).put(SCRIPTVAR_AVAILABLE_MILESTONES, milestones);
        return true;
    }
    public static boolean requestVeteranRewards(obj_id player, int milestone) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return false;
        }
        String daysText = getString(SID_DAYS);
        String rankText = getString(SID_RANK);
        String unavailableText = getString(SID_UNAVAILABLE);
        String alreadyClaimedText = getString(SID_UNAVAILABLE_CLAIMED);
        String needsExpansionText = getString(SID_UNAVAILABLE_NEEDS_EXPANSION);
        String notEnoughMilestoneText = getString(SID_UNAVAILABLE_NOT_ENOUGH_MILESTONE);
        String unknownText = getString(SID_UNKNOWN);
        Vector availableRewards = new Vector();
        int numRows = dataTableGetNumRows(REWARDS_DATATABLE);
        for (int i = 0; i < numRows; ++i)
        {
            String name = getRewardNameAtIndex(i);
            if (name != null)
            {
                boolean oneShot = dataTableGetInt(REWARDS_DATATABLE, i, REWARDS_COLUMN_ONETIME) != 0;
                if (oneShot)
                {
                    name += " *";
                }
                boolean noTrade = dataTableGetInt(REWARDS_DATATABLE, i, REWARDS_COLUMN_NOTRADE) != 0;
                if (noTrade)
                {
                    if (oneShot)
                    {
                        name += "#";
                    }
                    else 
                    {
                        name += " #";
                    }
                }
                int rewardMilestone = dataTableGetInt(REWARDS_DATATABLE, i, REWARDS_COLUMN_MILESTONE);
                name += " (";
                name += Integer.toString(rewardMilestone * DAYS_PER_MILESTONE);
                name += daysText;
                name += ", ";
                name += rankText;
                name += Integer.toString(rewardMilestone);
                name += ")";
                int canGetRewardStatus = canGetRewardAtIndex(player, i, milestone);
                if (canGetRewardStatus != CAN_GET_REWARD_SUCCESS)
                {
                    if (canGetRewardStatus == CAN_GET_REWARD_FAIL_INADEQUATE_MILESTONE)
                    {
                        name = name + " (" + notEnoughMilestoneText + ")";
                    }
                    else if (canGetRewardStatus == CAN_GET_REWARD_FAIL_ALREADY_CLAIMED)
                    {
                        name = name + " (" + alreadyClaimedText + ")";
                    }
                    else if (canGetRewardStatus == CAN_GET_REWARD_FAIL_INADEQUATE_SUBSCRIPTION)
                    {
                        name = name + " (" + needsExpansionText + ")";
                    }
                    else 
                    {
                        name = name + " (" + unavailableText + ")";
                    }
                }
            }
            else 
            {
                availableRewards.add(unknownText);
            }
            availableRewards.add(name);
        }
        int pid = sui.listbox(player, player, "@" + SID_VETERAN_SELECT_REWARD_PROMPT, sui.OK_CANCEL, sui.DEFAULT_TITLE, availableRewards, HANDLER_SELECT_VETERAN_REWARD, true);
        if (pid < 0)
        {
            CustomerServiceLog("veteran", "Could not create sui reward list for player %TU, error = " + pid, player);
            return false;
        }
        (player.getScriptVars()).put(SCRIPTVAR_SELECTED_MILESTONE, milestone);
        return true;
    }
    public static int[] getVeteranRewardMilestones(obj_id player) throws InterruptedException
    {
        int veteranTime = getIntObjVar(player, OBJVAR_TIME_ACTIVE);
        if (veteranTime <= 0)
        {
            CustomerServiceLog("veteran", "getVeteranRewardMilestones called for player %TU with no active time", player);
            return null;
        }
        int firstMilestone = veteranTime / DAYS_PER_MILESTONE;
        if (firstMilestone < 1)
        {
            return null;
        }
        if (firstMilestone > MAX_MILESTONE)
        {
            CustomerServiceLog("veteran", "getVeteranRewardMilestones called for player %TU who has a milestone " + firstMilestone + " which is greater than the maximum supported milestone " + MAX_MILESTONE, player);
            firstMilestone = MAX_MILESTONE;
        }
        int[] veteranRewards = getIntArrayObjVar(player, OBJVAR_REWARDS_RECEIVED);
        if (veteranRewards == null)
        {
            CustomerServiceLog("veteran", "getVeteranRewardMilestones called for player %TU with no rewards flags", player);
            veteranRewards = new int[REWARD_FLAGS_SIZE];
            setObjVar(player, OBJVAR_REWARDS_RECEIVED, veteranRewards);
        }
        Vector milestoneVector = new Vector();
        for (int i = 0; i < firstMilestone; ++i)
        {
            int index = i / 32;
            int mask = 0x00000001 << (i - index * 32);
            if ((veteranRewards[index] & mask) == 0)
            {
                milestoneVector.add(new Integer(i + 1));
            }
        }
        if (milestoneVector.size() < 1)
        {
            return null;
        }
        int[] milestoneArray = new int[milestoneVector.size()];
        for (int i = 0; i < milestoneArray.length; ++i)
        {
            milestoneArray[i] = (((Integer)(milestoneVector.elementAt(i)))).intValue();
        }
        return milestoneArray;
    }
    public static int givePlayerReward(obj_id player, int rewardIndex, boolean checkMilestoneMismatch) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return GIVE_PLAYER_REWARD_FAILED;
        }
        if (!(player.getScriptVars()).hasKey(SCRIPTVAR_SELECTED_MILESTONE))
        {
            CustomerServiceLog("veteran", "givePlayerReward could not find SCRIPTVAR_SELECTED_MILESTONE for player %TU with reward index " + rewardIndex, player);
            return GIVE_PLAYER_REWARD_FAILED;
        }
        int milestone = (player.getScriptVars()).getInt(SCRIPTVAR_SELECTED_MILESTONE);
        if (milestone < 1)
        {
            return GIVE_PLAYER_REWARD_FAILED;
        }
        if (rewardIndex >= dataTableGetNumRows(REWARDS_DATATABLE))
        {
            CustomerServiceLog("veteran", "givePlayerReward could not find reward for player %TU with reward index " + rewardIndex + " for milestone " + milestone, player);
            return GIVE_PLAYER_REWARD_FAILED;
        }
        int veteranTime = getIntObjVar(player, OBJVAR_TIME_ACTIVE);
        if (veteranTime <= 0)
        {
            CustomerServiceLog("veteran", "givePlayerReward called for player %TU with no active time", player);
            return GIVE_PLAYER_REWARD_FAILED;
        }
        int firstMilestone = veteranTime / DAYS_PER_MILESTONE;
        if (firstMilestone > MAX_MILESTONE)
        {
            CustomerServiceLog("veteran", "givePlayerReward called for player %TU who has a milestone " + firstMilestone + " which is greater than the maximum supported milestone " + MAX_MILESTONE, player);
            firstMilestone = MAX_MILESTONE;
        }
        if (milestone > firstMilestone)
        {
            CustomerServiceLog("veteran", "givePlayerReward called for player %TU with requested milestone " + milestone + " which is greater than the actual milestone " + firstMilestone, player);
            return GIVE_PLAYER_REWARD_FAILED;
        }
        if (checkVeteranMilestone(player, milestone))
        {
            CustomerServiceLog("veteran", "givePlayerReward called for player %TU who has already claimed the reward for milestone " + milestone, player);
            return GIVE_PLAYER_REWARD_FAILED;
        }
        int canGetRewardStatus = canGetRewardAtIndex(player, rewardIndex, milestone);
        if (canGetRewardStatus != CAN_GET_REWARD_SUCCESS)
        {
            CustomerServiceLog("veteran", "givePlayerReward could not grant reward for player %TU with reward index " + rewardIndex + " for milestone " + milestone + ", error code " + canGetRewardStatus, player);
            return GIVE_PLAYER_REWARD_FAILED;
        }
        if (checkMilestoneMismatch)
        {
            int rewardMilestone = dataTableGetInt(REWARDS_DATATABLE, rewardIndex, REWARDS_COLUMN_MILESTONE);
            if (rewardMilestone != milestone)
            {
                int pid = sui.msgbox(player, player, "@" + SID_VETERAN_SELECT_REWARD_CONFIRM, sui.YES_NO, HANDLER_SELECT_VETERAN_REWARD_CONFIRM);
                if (pid < 0)
                {
                    CustomerServiceLog("veteran", "givePlayerReward could not create sui reward confirmation message box for player %TU, error = " + pid, player);
                    return GIVE_PLAYER_REWARD_FAILED;
                }
                (player.getScriptVars()).put(SCRIPTVAR_SELECTED_REWARD, rewardIndex);
                return GIVE_PLAYER_REWARD_MORE_PROCESSING_REQUIRED;
            }
        }
        int templateCrc = dataTableGetInt(REWARDS_DATATABLE, rewardIndex, REWARDS_COLUMN_TEMPLATE);
        obj_id reward = createObjectInInventoryAllowOverload(templateCrc, player);
        if (!isIdValid(reward))
        {
            CustomerServiceLog("veteran", "givePlayerReward could not create reward for reward index " + rewardIndex + ", template crc " + templateCrc + " for player %TU", player);
            return GIVE_PLAYER_REWARD_FAILED;
        }
        CustomerServiceLog("veteran", "givePlayerReward has given player %TU item " + getName(reward) + " (" + reward + ") for milestone " + milestone, player);
        boolean noTrade = dataTableGetInt(REWARDS_DATATABLE, rewardIndex, REWARDS_COLUMN_NOTRADE) != 0;
        if (noTrade)
        {
            setObjVar(reward, "noTrade", true);
        }
        boolean noDelete = dataTableGetInt(REWARDS_DATATABLE, rewardIndex, REWARDS_COLUMN_NODELETE) != 0;
        if (noDelete)
        {
            attachScript(reward, "item.special.nodestroy");
        }
        boolean noMove = dataTableGetInt(REWARDS_DATATABLE, rewardIndex, REWARDS_COLUMN_NOMOVE) != 0;
        if (noMove)
        {
            attachScript(reward, "item.special.nomove");
        }
        if (!hasObjVar(player, OBJVAR_REWARDS_RECEIVED))
        {
            CustomerServiceLog("veteran", "givePlayerReward called for player %TU with no rewards flags", player);
        }
        setVeteranMilestone(player, milestone);
        boolean oneShot = dataTableGetInt(REWARDS_DATATABLE, rewardIndex, REWARDS_COLUMN_ONETIME) != 0;
        if (oneShot)
        {
            int[] onetimes = getIntArrayObjVar(player, OBJVAR_ONETIME_REWARDS_RECEIVED);
            int[] onetimesNew;
            if (onetimes != null)
            {
                onetimesNew = new int[onetimes.length + 1];
                for (int i = 0; i < onetimes.length; ++i)
                {
                    onetimesNew[i] = onetimes[i];
                }
                onetimesNew[onetimes.length] = templateCrc;
            }
            else 
            {
                onetimesNew = new int[1];
                onetimesNew[0] = templateCrc;
            }
            setObjVar(player, OBJVAR_ONETIME_REWARDS_RECEIVED, onetimesNew);
        }
        String script = dataTableGetString(REWARDS_DATATABLE, rewardIndex, REWARDS_COLUMN_SCRIPT);
        if (script != null && script.length() > 0)
        {
            attachScript(reward, script);
        }
        cleanupPlayerData(player);
        return GIVE_PLAYER_REWARD_SUCCESS;
    }
    public static void cleanupPlayerData(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return;
        }
        (player.getScriptVars()).remove(SCRIPTVAR_SELECTED_REWARD);
        (player.getScriptVars()).remove(SCRIPTVAR_SELECTED_MILESTONE);
        (player.getScriptVars()).remove(SCRIPTVAR_AVAILABLE_MILESTONES);
    }
    public static String getRewardNameAtIndex(int index) throws InterruptedException
    {
        String name = null;
        int templateCrc = dataTableGetInt(REWARDS_DATATABLE, index, REWARDS_COLUMN_TEMPLATE);
        string_id nameId = getNameFromTemplate(templateCrc);
        if (nameId != null)
        {
            name = "@" + nameId;
        }
        else 
        {
            CustomerServiceLog("veteran", "Could not find reward at index " + index);
        }
        return name;
    }
    public static int canGetRewardAtIndex(obj_id player, int index, int milestone) throws InterruptedException
    {
        int templateCrc = dataTableGetInt(REWARDS_DATATABLE, index, REWARDS_COLUMN_TEMPLATE);
        boolean oneShot = dataTableGetInt(REWARDS_DATATABLE, index, REWARDS_COLUMN_ONETIME) != 0;
        if (oneShot)
        {
            int[] onetimes = getIntArrayObjVar(player, OBJVAR_ONETIME_REWARDS_RECEIVED);
            if (onetimes != null)
            {
                for (int i = 0; i < onetimes.length; ++i)
                {
                    if (onetimes[i] == templateCrc)
                    {
                        return CAN_GET_REWARD_FAIL_ALREADY_CLAIMED;
                    }
                }
            }
        }
        int requiredBit = dataTableGetInt(REWARDS_DATATABLE, index, REWARDS_COLUMN_REQUIREDGAMEFEATUREBITANY);
        if (requiredBit > 0)
        {
            int game_bits = getGameFeatureBits(player);
            if ((game_bits & requiredBit) == 0)
            {
                return CAN_GET_REWARD_FAIL_INADEQUATE_SUBSCRIPTION;
            }
        }
        requiredBit = dataTableGetInt(REWARDS_DATATABLE, index, REWARDS_COLUMN_REQUIREDGAMEFEATUREBITALL);
        if (requiredBit > 0)
        {
            int game_bits = getGameFeatureBits(player);
            if ((game_bits & requiredBit) != requiredBit)
            {
                return CAN_GET_REWARD_FAIL_INADEQUATE_SUBSCRIPTION;
            }
        }
        requiredBit = dataTableGetInt(REWARDS_DATATABLE, index, REWARDS_COLUMN_REQUIREDSUBSCRIPTIONFEATUREBITANY);
        if (requiredBit > 0)
        {
            int sub_bits = getSubscriptionFeatureBits(player);
            if ((sub_bits & requiredBit) == 0)
            {
                return CAN_GET_REWARD_FAIL_INADEQUATE_SUBSCRIPTION;
            }
        }
        requiredBit = dataTableGetInt(REWARDS_DATATABLE, index, REWARDS_COLUMN_REQUIREDSUBSCRIPTIONFEATUREBITALL);
        if (requiredBit > 0)
        {
            int sub_bits = getSubscriptionFeatureBits(player);
            if ((sub_bits & requiredBit) != requiredBit)
            {
                return CAN_GET_REWARD_FAIL_INADEQUATE_SUBSCRIPTION;
            }
        }
        int rewardMilestone = dataTableGetInt(REWARDS_DATATABLE, index, REWARDS_COLUMN_MILESTONE);
        if (rewardMilestone > milestone)
        {
            return CAN_GET_REWARD_FAIL_INADEQUATE_MILESTONE;
        }
        return CAN_GET_REWARD_SUCCESS;
    }
    public static boolean checkVeteranMilestone(obj_id player, int milestone) throws InterruptedException
    {
        if (milestone < 1 || milestone > MAX_MILESTONE)
        {
            return false;
        }
        int[] rewardsReceived = getIntArrayObjVar(player, OBJVAR_REWARDS_RECEIVED);
        if (rewardsReceived == null)
        {
            return false;
        }
        --milestone;
        int index = milestone / 32;
        int mask = 0x00000001 << (milestone - index * 32);
        return (rewardsReceived[index] & mask) != 0;
    }
    public static boolean checkMilestoneNotified(obj_id player, int milestone) throws InterruptedException
    {
        if (milestone < 1 || milestone > MAX_MILESTONE)
        {
            return false;
        }
        int[] milestonesNotified = getIntArrayObjVar(player, OBJVAR_MILESTONES_NOTIFIED);
        if (milestonesNotified == null)
        {
            return false;
        }
        --milestone;
        int index = milestone / 32;
        int mask = 0x00000001 << (milestone - index * 32);
        return (milestonesNotified[index] & mask) != 0;
    }
    public static void setVeteranMilestone(obj_id player, int milestone) throws InterruptedException
    {
        if (milestone < 1 || milestone > MAX_MILESTONE)
        {
            return;
        }
        int[] rewardsReceived = getIntArrayObjVar(player, OBJVAR_REWARDS_RECEIVED);
        if (rewardsReceived == null)
        {
            rewardsReceived = new int[REWARD_FLAGS_SIZE];
        }
        --milestone;
        int index = milestone / 32;
        int mask = 0x00000001 << (milestone - index * 32);
        rewardsReceived[index] |= mask;
        setObjVar(player, OBJVAR_REWARDS_RECEIVED, rewardsReceived);
    }
    public static void setMilestoneNotified(obj_id player, int milestone) throws InterruptedException
    {
        if (milestone < 1 || milestone > MAX_MILESTONE)
        {
            return;
        }
        int[] milestonesNotified = getIntArrayObjVar(player, OBJVAR_MILESTONES_NOTIFIED);
        if (milestonesNotified == null)
        {
            milestonesNotified = new int[REWARD_FLAGS_SIZE];
        }
        --milestone;
        int index = milestone / 32;
        int mask = 0x00000001 << (milestone - index * 32);
        milestonesNotified[index] |= mask;
        setObjVar(player, OBJVAR_MILESTONES_NOTIFIED, milestonesNotified);
    }
    public static void clearVeteranMilestone(obj_id player, int milestone) throws InterruptedException
    {
        if (milestone < 1 || milestone > MAX_MILESTONE)
        {
            return;
        }
        int[] rewardsReceived = getIntArrayObjVar(player, OBJVAR_REWARDS_RECEIVED);
        if (rewardsReceived == null)
        {
            rewardsReceived = new int[REWARD_FLAGS_SIZE];
        }
        clearMilestoneNotified(player, milestone);
        --milestone;
        int index = milestone / 32;
        int mask = 0x00000001 << (milestone - index * 32);
        rewardsReceived[index] &= ~mask;
        setObjVar(player, OBJVAR_REWARDS_RECEIVED, rewardsReceived);
    }
    public static void clearMilestoneNotified(obj_id player, int milestone) throws InterruptedException
    {
        if (milestone < 1 || milestone > MAX_MILESTONE)
        {
            return;
        }
        int[] milestonesNotified = getIntArrayObjVar(player, OBJVAR_MILESTONES_NOTIFIED);
        if (milestonesNotified == null)
        {
            milestonesNotified = new int[REWARD_FLAGS_SIZE];
        }
        --milestone;
        int index = milestone / 32;
        int mask = 0x00000001 << (milestone - index * 32);
        milestonesNotified[index] &= ~mask;
        setObjVar(player, OBJVAR_MILESTONES_NOTIFIED, milestonesNotified);
    }
    public static boolean checkVeteranTarget(obj_id target) throws InterruptedException
    {
        if (isIdValid(target) && isPlayer(target))
        {
            if (hasObjVar(target, veteran_deprecated.OBJVAR_TIME_ACTIVE))
            {
                return true;
            }
            else 
            {
                sendSystemMessage(getSelf(), veteran_deprecated.SID_SYSTEM_INACTIVE);
            }
        }
        else 
        {
            sendSystemMessage(getSelf(), veteran_deprecated.SID_INVALID_TARGET);
        }
        return false;
    }
    public static void giveOneYearAnniversaryReward(obj_id player) throws InterruptedException
    {
        if (!("true").equals(getConfigSetting("GameServer", "enableOneYearAnniversary")))
        {
            return;
        }
        if (!isPlayer(player))
        {
            return;
        }
        if (hasObjVar(player, OBJVAR_ONE_YEAR_ANNIVERSARY))
        {
            return;
        }
        int playerBorn = getPlayerBirthDate(player);
        int existTime = getCurrentBirthDate() - playerBorn;
        if (hasObjVar(player, OBJVAR_ONE_YEAR_ANNIVERSARY_OVERRIDE))
        {
            existTime = getIntObjVar(player, OBJVAR_ONE_YEAR_ANNIVERSARY_OVERRIDE);
            CustomerServiceLog("one_year_anniversary", "Using override time of " + existTime + " for player %TU", player);
        }
        if (playerBorn < 0 || existTime < MIN_ONE_YEAR_ANNIVERSARY_TIME)
        {
            return;
        }
        obj_id inventory = utils.getInventoryContainer(player);
        if (!isIdValid(inventory))
        {
            return;
        }
        int free = getVolumeFree(inventory);
        if (free == 0)
        {
            prose_package pp = new prose_package();
            pp.stringId = SID_ONE_YEAR_ANNIVERSARY_INVENTORY_FULL;
            utils.sendMail(SID_ONE_YEAR_ANNIVERSARY_INVENTORY_FULL_SUBJECT, pp, player, "@" + SID_ONE_YEAR_ANNIVERSARY_FROM);
            return;
        }
        int rewardNum = rand(1, ONE_YEAR_ANNIVERSARY_REWARD_COUNT);
        String rewardName = TEMPLATE_ONE_YEAR_ANNIVERSARY;
        if (rewardNum < 10)
        {
            rewardName += "0";
        }
        rewardName += rewardNum;
        obj_id reward = createObject(rewardName + ".iff", inventory, "");
        if (!isIdValid(reward))
        {
            CustomerServiceLog("one_year_anniversary", "WARNING: Error creating one year anniversary reward " + rewardName + ".iff for player %TU", player);
            return;
        }
        setObjVar(player, OBJVAR_ONE_YEAR_ANNIVERSARY, reward);
        CustomerServiceLog("one_year_anniversary", "Gave player %TU one year anniversary reward " + reward, player);
        prose_package pp = new prose_package();
        pp.stringId = SID_ONE_YEAR_ANNIVERSARY;
        utils.sendMail(SID_ONE_YEAR_ANNIVERSARY_SUBJECT, pp, player, "@" + SID_ONE_YEAR_ANNIVERSARY_FROM);
    }
    public static boolean checkFlashSpeederReward(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            LOG("flash_speeder", "veteran_deprecated.checkFlashSpeeder: Player is invalid.");
            return false;
        }
        if (!isPlayer(player))
        {
            LOG("flash_speeder", "veteran_deprecated.checkFlashSpeeder: " + player + " is not a player.");
            return false;
        }
        if (isUsingAdminLogin(player))
        {
            return false;
        }
        String config = getConfigSetting("GameServer", "flashSpeederReward");
        if (config != null && config.equals("true"))
        {
            int sub_bits = getGameFeatureBits(player);
            if (hasObjVar(player, "flash_speeder.eligible"))
            {
                sub_bits = getIntObjVar(player, "flash_speeder.eligible");
            }
            if ((features.isSpaceEdition(player) && utils.checkBit(sub_bits, 3)) || features.isJPCollectorEdition(player))
            {
                if (!hasObjVar(player, "flash_speeder.granted"))
                {
                    createObjectInInventoryAllowOverload("object/tangible/deed/vehicle_deed/speederbike_flash_deed.iff", player);
                    CustomerServiceLog("flash_speeder", "%TU has received a JtL pre-order Flash Speeder.", player);
                    string_id sub = new string_id(VETERAN_STRING_TABLE, "flash_speeder_granted_sub");
                    string_id body = new string_id(VETERAN_STRING_TABLE, "flash_speeder_granted_body");
                    utils.sendMail(sub, body, player, "System");
                    setObjVar(player, "flash_speeder.granted", 1);
                    return true;
                }
            }
        }
        return false;
    }
}
