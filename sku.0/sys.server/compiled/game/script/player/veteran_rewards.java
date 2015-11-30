package script.player;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.colors_hex;
import script.library.cts;
import script.library.features;
import script.library.player_structure;
import script.library.static_item;
import script.library.sui;
import script.library.utils;

public class veteran_rewards extends script.base_script
{
    public veteran_rewards()
    {
    }
    public static final String OBJVAR_NEXT_PROMPT_TIME = "veteran_rewards.next_prompt_time";
    public static final String OBJVAR_SOROSUUB_CLAIMED_ITEMS = "veteran_rewards.items_claimed.sorosuub";
    public static final String OBJVAR_6MOJTL_CONSUMED_EVENTS = "veteran_rewards.events_consumed.6moJTL";
    public static final String OBJVAR_DEPRECATED_ONETIME_ITEMS = "veteran.onetime";
    public static final String SCRIPTVAR_ALREADY_LOGGED_IN = "veteran_rewards.logged_in";
    public static final String SCRIPTVAR_ALREADY_CHECKED_AUTOPROMPT = "veteran_rewards.auto_prompted";
    public static final String SCRIPTVAR_EVENT = "veteran_rewards.event";
    public static final String SCRIPTVAR_ALL_EVENTS = "veteran_rewards.all_events";
    public static final String SCRIPTVAR_ITEM = "veteran_rewards.item";
    public static final String SCRIPTVAR_REWARD_TAGS = "veteran_rewards.rewardTags";
    public static final String SCRIPTVAR_CLAIM_IN_PROGRESS = "veteran_rewards.claim_in_progress";
    public static final int CLAIM_IN_PROGRESS_TIMEOUT = 60;
    public static final String SCRIPTVAR_ACCOUNT_FEATURE_ID_REQUEST_IN_PROGRESS = "veteran_rewards.account_feature_id_request_in_progress";
    public static final int ACCOUNT_FEATURE_ID_REQUEST_IN_PROGRESS_TIMEOUT = 60;
    public static final String SCRIPTVAR_REWARD_PID = "veteran_rewards_pid.pid";
    public static final String ANNOUNCEMENT_BOX_TITLE = "@veteran_new:announcement_box_title";
    public static final String CHOICE_CLAIM_NOW = "@veteran_new:claim_now";
    public static final String CHOICE_LAUNCH_BROWSER = "@veteran_new:launch_browser";
    public static final String CHOICE_ASK_NEXT_LOGIN = "@veteran_new:ask_next_login";
    public static final String CHOICE_ASK_ONE_DAY = "@veteran_new:ask_one_day";
    public static final String CHOICE_ASK_ONE_WEEK = "@veteran_new:ask_one_week";
    public static final String ITEM_GRANT_SUCCEEDED = "@veteran_new:item_grant_succeeded";
    public static final String ITEM_GRANT_SUCCEEDED_CAN_TRADE_IN = "@veteran_new:item_grant_succeeded_can_trade_in";
    public static final String ITEM_GRANT_FAILED = "@veteran_new:item_grant_failed";
    public static final String ITEM_GRANT_BOX_TITLE = "@veteran_new:item_grant_box_title";
    public static final String NO_OPTIONS = "@veteran_new:no_options";
    public static final String NO_OPTIONS_BOX_TITLE = "@veteran_new:no_options_box_title";
    public static final String ITEM_AND_EVENT_UNIQUE_ARE_YOU_SURE = "@veteran_new:item_and_event_unique_are_you_sure";
    public static final String ITEM_UNIQUE_ARE_YOU_SURE = "@veteran_new:item_unique_are_you_sure";
    public static final String ITEM_UNIQUE_FEATURE_ID_ARE_YOU_SURE = "@veteran_new:item_unique_feature_id_are_you_sure";
    public static final String EVENT_UNIQUE_ARE_YOU_SURE = "@veteran_new:event_unique_are_you_sure";
    public static final String UNIQUE_ARE_YOU_SURE_BOX_TITLE = "@veteran_new:unique_are_you_sure_box_title";
    public static final String REMIND_NEXT_LOGIN_DESCRIPTION = "@veteran_new:remind_next_login_description";
    public static final String REMIND_ONE_DAY_DESCRIPTION = "@veteran_new:remind_one_day_description";
    public static final String REMIND_ONE_WEEK_DESCRIPTION = "@veteran_new:remind_one_week_description";
    public static final String REMIND_DESCRIPTION_TITLE = "@veteran_new:remind_description_title";
    public static final String MULTIPLE_EVENTS_HELP_TEXT = "@veteran_new:multiple_events_help_text";
    public static final String NO_REWARDS_MESSAGE = "@veteran_new:no_rewards";
    public static final String SCRIPTVAR_REWARD_TRADE_IN_SUI_ID = "rewardTradeInSuiId";
    public static final String SCRIPTVAR_REWARD_TRADE_IN_ITEM_ID = "rewardTradeInItemId";
    public static final string_id SID_SORRY_NO_INFO = new string_id("veteran_new", "sorry_no_info");
    public static final String OBJVAR_FREE_CTS_NEXT_PROMPT_TIME = "freeCts.next_prompt_time";
    public static final String SCRIPTVAR_FREE_CTS_SUI_ID = "freeCtsSuiId";
    public static final String SCRIPTVAR_FREE_CTS_REQUEST_TIMEOUT = "freeCtsRequestTimeout";
    public static final String SCRIPTVAR_CTS_SUI_ID = "ctsSuiId";
    public static final String SCRIPTVAR_CTS_REQUEST_TIMEOUT = "ctsRequestTimeout";
    public int OnLogin(obj_id self) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, SCRIPTVAR_ALREADY_LOGGED_IN))
        {
            utils.removeScriptVar(self, SCRIPTVAR_EVENT);
            utils.removeScriptVar(self, SCRIPTVAR_ALL_EVENTS);
            utils.removeScriptVar(self, SCRIPTVAR_ITEM);
            utils.removeScriptVar(self, SCRIPTVAR_REWARD_TAGS);
            int[] onetimes = getIntArrayObjVar(self, OBJVAR_DEPRECATED_ONETIME_ITEMS);
            if (onetimes != null)
            {
                boolean hasSoro = false;
                for (int i = 0; i < onetimes.length; ++i)
                {
                    if (onetimes[i] == (884375002))
                    {
                        hasSoro = true;
                    }
                }
                if (hasSoro)
                {
                    setObjVar(self, OBJVAR_SOROSUUB_CLAIMED_ITEMS, (int)1);
                    setObjVar(self, OBJVAR_6MOJTL_CONSUMED_EVENTS, (int)1);
                }
                removeObjVar(self, OBJVAR_DEPRECATED_ONETIME_ITEMS);
            }
            veteranWriteAccountDataToObjvars(self);
            if (isFreeCtsPromptTime(self) && (getSecondsUntilCanMakeAnotherFreeCtsRequest(self) <= 0))
            {
                checkForFreeCts(self);
            }
            utils.setScriptVar(self, SCRIPTVAR_ALREADY_LOGGED_IN, 1);
        }
        if (shouldGetVeteranRewardsMessage(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (!utils.hasScriptVar(self, SCRIPTVAR_ALREADY_CHECKED_AUTOPROMPT))
        {
            if (isVeteranRewardsPromptTime(self))
            {
                checkForVeteranRewards(self);
            }
            utils.setScriptVar(self, SCRIPTVAR_ALREADY_CHECKED_AUTOPROMPT, 1);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnLogout(obj_id self) throws InterruptedException
    {
        utils.removeScriptVar(self, SCRIPTVAR_EVENT);
        utils.removeScriptVar(self, SCRIPTVAR_ALL_EVENTS);
        utils.removeScriptVar(self, SCRIPTVAR_ITEM);
        utils.removeScriptVar(self, SCRIPTVAR_REWARD_TAGS);
        utils.removeScriptVar(self, SCRIPTVAR_ALREADY_LOGGED_IN);
        utils.removeScriptVar(self, SCRIPTVAR_ALREADY_CHECKED_AUTOPROMPT);
        return SCRIPT_CONTINUE;
    }
    public int DoCreateVeteranReward(obj_id self, String[] rewardItemName) throws InterruptedException
    {
        utils.removeScriptVar(self, SCRIPTVAR_EVENT);
        utils.removeScriptVar(self, SCRIPTVAR_ALL_EVENTS);
        utils.removeScriptVar(self, SCRIPTVAR_ITEM);
        utils.removeScriptVar(self, SCRIPTVAR_REWARD_TAGS);
        if ((rewardItemName != null) && (rewardItemName.length == 1) && (rewardItemName[0].length() > 0))
        {
            obj_id createdItem = null;
            obj_id playerInventory = utils.getInventoryContainer(self);
            if (isIdValid(playerInventory))
            {
                if (static_item.isStaticItem(rewardItemName[0]))
                {
                    createdItem = static_item.createNewItemFunction(rewardItemName[0], playerInventory);
                }
                else 
                {
                    createdItem = createObjectOverloaded(rewardItemName[0], playerInventory);
                }
            }
            if (isIdValid(createdItem))
            {
                rewardItemName[0] = "" + createdItem;
                String rewardInfo[] = new String[4];
                rewardInfo[0] = "" + getCalendarTime();
                rewardInfo[1] = getClusterName();
                rewardInfo[2] = "" + self;
                rewardInfo[3] = "" + getPlayerStationId(self);
                setObjVar(createdItem, "rewardGrantInfo", rewardInfo);
            }
            else 
            {
                rewardItemName[0] = "0";
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdClaimVeteranReward(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        utils.removeScriptVar(self, SCRIPTVAR_EVENT);
        utils.removeScriptVar(self, SCRIPTVAR_ALL_EVENTS);
        utils.removeScriptVar(self, SCRIPTVAR_ITEM);
        utils.removeScriptVar(self, SCRIPTVAR_REWARD_TAGS);
        if (!hasAccountFeatureIdRequestInProgress(self) && !hasClaimInProgress(self))
        {
            if (veteranAccountFeatureIdRequest(self))
            {
                setAccountFeatureIdRequestInProgress(self);
            }
            else 
            {
                if (!checkForVeteranRewards(self))
                {
                    sui.msgbox(self, NO_REWARDS_MESSAGE);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int veteranAccountFeatureIdResponse(obj_id self, dictionary params) throws InterruptedException
    {
        clearAccountFeatureIdRequestInProgress(self);
        if (!checkForVeteranRewards(self))
        {
            sui.msgbox(self, NO_REWARDS_MESSAGE);
        }
        return SCRIPT_CONTINUE;
    }
    public boolean checkForVeteranRewards(obj_id self) throws InterruptedException
    {
        utils.removeScriptVar(self, SCRIPTVAR_EVENT);
        utils.removeScriptVar(self, SCRIPTVAR_ALL_EVENTS);
        utils.removeScriptVar(self, SCRIPTVAR_ITEM);
        utils.removeScriptVar(self, SCRIPTVAR_REWARD_TAGS);
        if (!hasClaimInProgress(self))
        {
            String[] events = veteranGetTriggeredEvents(self);
            if ((events != null) && (events.length > 0))
            {
                Vector choices = new Vector();
                choices.addElement(CHOICE_ASK_NEXT_LOGIN);
                choices.addElement(CHOICE_ASK_ONE_DAY);
                choices.addElement(CHOICE_ASK_ONE_WEEK);
                choices.addElement("");
                boolean hasMore = false;
                for (int i = 0; i < events.length; ++i)
                {
                    if (events[i].startsWith("*"))
                    {
                        hasMore = true;
                        events[i] = events[i].substring(1);
                        choices.addElement("@veteran_new:color_lime_green " + veteranGetEventAnnouncement(events[i]));
                    }
                    else 
                    {
                        choices.addElement("@veteran_new:empty_string " + veteranGetEventAnnouncement(events[i]));
                    }
                }
                utils.setScriptVar(self, SCRIPTVAR_ALL_EVENTS, events);
                String announcement = "@veteran_new:announcement";
                if (hasMore)
                {
                    announcement += "\n \n" + MULTIPLE_EVENTS_HELP_TEXT;
                }
                if (utils.hasScriptVar(self, SCRIPTVAR_REWARD_PID))
                {
                    int oldpid = utils.getIntScriptVar(self, SCRIPTVAR_REWARD_PID);
                    if (oldpid > 0)
                    {
                        forceCloseSUIPage(oldpid);
                        utils.removeScriptVarTree(self, SCRIPTVAR_REWARD_PID);
                    }
                }
                int pid = sui.listbox(self, self, announcement, sui.OK_ONLY, ANNOUNCEMENT_BOX_TITLE, choices, "handleRewardAnnouncement", false, false);
                sui.listboxUseOtherButton(pid, CHOICE_LAUNCH_BROWSER);
                showSUIPage(pid);
                utils.setScriptVar(self, SCRIPTVAR_REWARD_PID, pid);
                return true;
            }
        }
        return false;
    }
    public boolean isVeteranRewardsPromptTime(obj_id self) throws InterruptedException
    {
        int nextPromptTime = getIntObjVar(self, OBJVAR_NEXT_PROMPT_TIME);
        if (nextPromptTime == 0 || nextPromptTime < getGameTime())
        {
            removeObjVar(self, OBJVAR_NEXT_PROMPT_TIME);
            return true;
        }
        return false;
    }
    public int handleRewardAnnouncement(obj_id self, dictionary params) throws InterruptedException
    {
        String[] events = utils.getStringArrayScriptVar(self, SCRIPTVAR_ALL_EVENTS);
        utils.removeScriptVar(self, SCRIPTVAR_EVENT);
        utils.removeScriptVar(self, SCRIPTVAR_ALL_EVENTS);
        utils.removeScriptVar(self, SCRIPTVAR_ITEM);
        utils.removeScriptVar(self, SCRIPTVAR_REWARD_TAGS);
        if ((events == null) || (events.length == 0))
        {
            return SCRIPT_CONTINUE;
        }
        int rowSelected = sui.getListboxSelectedRow(params);
        int bp = sui.getIntButtonPressed(params);
        if (((bp != sui.BP_OK) && (bp != sui.BP_REVERT)) || rowSelected < 0)
        {
            if (bp == sui.BP_REVERT)
            {
                return SCRIPT_CONTINUE;
            }
            int nextPromptTime = getIntObjVar(self, OBJVAR_NEXT_PROMPT_TIME);
            if (nextPromptTime == 0 || nextPromptTime < getGameTime())
            {
                removeObjVar(self, OBJVAR_NEXT_PROMPT_TIME);
                sui.msgbox(self, self, REMIND_NEXT_LOGIN_DESCRIPTION, sui.OK_ONLY, REMIND_DESCRIPTION_TITLE, "noHandler");
            }
            return SCRIPT_CONTINUE;
        }
        if (rowSelected == 0)
        {
            if (bp == sui.BP_OK)
            {
                removeObjVar(self, OBJVAR_NEXT_PROMPT_TIME);
                sui.msgbox(self, self, REMIND_NEXT_LOGIN_DESCRIPTION, sui.OK_ONLY, REMIND_DESCRIPTION_TITLE, "noHandler");
            }
            return SCRIPT_CONTINUE;
        }
        if ((rowSelected == 1) || (rowSelected == 2))
        {
            if (bp == sui.BP_OK)
            {
                int waitForNextPrompt = 0;
                if (rowSelected == 1)
                {
                    sui.msgbox(self, self, REMIND_ONE_DAY_DESCRIPTION, sui.OK_ONLY, REMIND_DESCRIPTION_TITLE, "noHandler");
                    waitForNextPrompt = 24 * 60 * 60;
                }
                else 
                {
                    sui.msgbox(self, self, REMIND_ONE_WEEK_DESCRIPTION, sui.OK_ONLY, REMIND_DESCRIPTION_TITLE, "noHandler");
                    waitForNextPrompt = 7 * 24 * 60 * 60;
                }
                int gameTime = getGameTime();
                int nextPromptTime = gameTime + waitForNextPrompt;
                setObjVar(self, OBJVAR_NEXT_PROMPT_TIME, nextPromptTime);
            }
            return SCRIPT_CONTINUE;
        }
        if (rowSelected == 3)
        {
            return SCRIPT_CONTINUE;
        }
        rowSelected = rowSelected - 4;
        if (rowSelected >= events.length)
        {
            return SCRIPT_CONTINUE;
        }
        if (bp == sui.BP_OK)
        {
            chooseRewardForEvent(self, events[rowSelected]);
        }
        else 
        {
            String webSite = veteranGetEventUrl(events[rowSelected]);
            if (webSite != null && !webSite.equals(""))
            {
                launchClientWebBrowser(self, webSite);
            }
            else 
            {
                sendSystemMessage(self, SID_SORRY_NO_INFO);
            }
            checkForVeteranRewards(self);
        }
        return SCRIPT_CONTINUE;
    }
    public void chooseRewardForEvent(obj_id self, String event) throws InterruptedException
    {
        utils.removeScriptVar(self, SCRIPTVAR_EVENT);
        utils.removeScriptVar(self, SCRIPTVAR_ALL_EVENTS);
        utils.removeScriptVar(self, SCRIPTVAR_ITEM);
        utils.removeScriptVar(self, SCRIPTVAR_REWARD_TAGS);
        String[] rewardChoices = veteranGetRewardChoicesDescriptions(self, event);
        String[] rewardChoicesTags = veteranGetRewardChoicesTags(self, event);
        if (rewardChoices.length == 0)
        {
            sui.msgbox(self, self, NO_OPTIONS, sui.OK_ONLY, NO_OPTIONS_BOX_TITLE, "noHandler");
        }
        else if (rewardChoices.length == 1)
        {
            checkForUniquenessAndClaim(self, event, rewardChoicesTags[0]);
        }
        else 
        {
            utils.setScriptVar(self, SCRIPTVAR_EVENT, event);
            utils.setScriptVar(self, SCRIPTVAR_REWARD_TAGS, rewardChoicesTags);
            String[] choicesWithLaunchWeb = new String[rewardChoices.length + 1];
            choicesWithLaunchWeb[0] = CHOICE_LAUNCH_BROWSER;
            for (int i = 0; i < rewardChoices.length; ++i)
            {
                choicesWithLaunchWeb[i + 1] = rewardChoices[i];
            }
            sui.listbox(self, self, veteranGetEventDescription(event), sui.OK_CANCEL, sui.DEFAULT_TITLE, choicesWithLaunchWeb, "rewardSelectHandler", true, false);
        }
    }
    public int rewardSelectHandler(obj_id self, dictionary params) throws InterruptedException
    {
        String event = utils.getStringScriptVar(self, SCRIPTVAR_EVENT);
        String[] rewardChoicesTags = utils.getStringArrayScriptVar(self, SCRIPTVAR_REWARD_TAGS);
        utils.removeScriptVar(self, SCRIPTVAR_EVENT);
        utils.removeScriptVar(self, SCRIPTVAR_ALL_EVENTS);
        utils.removeScriptVar(self, SCRIPTVAR_ITEM);
        utils.removeScriptVar(self, SCRIPTVAR_REWARD_TAGS);
        if ((rewardChoicesTags == null) || (rewardChoicesTags.length == 0))
        {
            return SCRIPT_CONTINUE;
        }
        if (event == null)
        {
            return SCRIPT_CONTINUE;
        }
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        int rowSelected = sui.getListboxSelectedRow(params);
        if (bp == sui.BP_CANCEL || rowSelected < 0)
        {
            checkForVeteranRewards(self);
            return SCRIPT_CONTINUE;
        }
        if (rowSelected == 0)
        {
            String webSite = veteranGetEventUrl(event);
            if (webSite != null && !webSite.equals(""))
            {
                launchClientWebBrowser(self, webSite);
            }
            else 
            {
                sendSystemMessage(self, SID_SORRY_NO_INFO);
            }
            checkForVeteranRewards(self);
        }
        else 
        {
            if ((rowSelected > 0) && ((rowSelected - 1) < rewardChoicesTags.length))
            {
                checkForUniquenessAndClaim(self, event, rewardChoicesTags[rowSelected - 1]);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void checkForUniquenessAndClaim(obj_id self, String event, String item) throws InterruptedException
    {
        utils.removeScriptVar(self, SCRIPTVAR_EVENT);
        utils.removeScriptVar(self, SCRIPTVAR_ALL_EVENTS);
        utils.removeScriptVar(self, SCRIPTVAR_ITEM);
        utils.removeScriptVar(self, SCRIPTVAR_REWARD_TAGS);
        boolean itemAccountUnique = veteranIsItemAccountUnique(item);
        boolean eventAccountUnique = veteranIsEventAccountUnique(event);
        if (itemAccountUnique && eventAccountUnique)
        {
            utils.setScriptVar(self, SCRIPTVAR_EVENT, event);
            utils.setScriptVar(self, SCRIPTVAR_ITEM, item);
            sui.msgbox(self, self, ITEM_AND_EVENT_UNIQUE_ARE_YOU_SURE, sui.YES_NO, UNIQUE_ARE_YOU_SURE_BOX_TITLE, "handleUniqueConfirmation");
        }
        else if (itemAccountUnique)
        {
            utils.setScriptVar(self, SCRIPTVAR_EVENT, event);
            utils.setScriptVar(self, SCRIPTVAR_ITEM, item);
            sui.msgbox(self, self, ITEM_UNIQUE_ARE_YOU_SURE, sui.YES_NO, UNIQUE_ARE_YOU_SURE_BOX_TITLE, "handleUniqueConfirmation");
        }
        else if (eventAccountUnique)
        {
            utils.setScriptVar(self, SCRIPTVAR_EVENT, event);
            utils.setScriptVar(self, SCRIPTVAR_ITEM, item);
            sui.msgbox(self, self, EVENT_UNIQUE_ARE_YOU_SURE, sui.YES_NO, UNIQUE_ARE_YOU_SURE_BOX_TITLE, "handleUniqueConfirmation");
        }
        else if (veteranIsItemAccountUniqueFeatureId(item))
        {
            utils.setScriptVar(self, SCRIPTVAR_EVENT, event);
            utils.setScriptVar(self, SCRIPTVAR_ITEM, item);
            sui.msgbox(self, self, ITEM_UNIQUE_FEATURE_ID_ARE_YOU_SURE, sui.YES_NO, UNIQUE_ARE_YOU_SURE_BOX_TITLE, "handleUniqueConfirmation");
        }
        else 
        {
            lockAndClaim(self, event, item);
        }
    }
    public int handleUniqueConfirmation(obj_id self, dictionary params) throws InterruptedException
    {
        String event = utils.getStringScriptVar(self, SCRIPTVAR_EVENT);
        String item = utils.getStringScriptVar(self, SCRIPTVAR_ITEM);
        utils.removeScriptVar(self, SCRIPTVAR_EVENT);
        utils.removeScriptVar(self, SCRIPTVAR_ALL_EVENTS);
        utils.removeScriptVar(self, SCRIPTVAR_ITEM);
        utils.removeScriptVar(self, SCRIPTVAR_REWARD_TAGS);
        if ((event == null) || (item == null))
        {
            return SCRIPT_CONTINUE;
        }
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_OK)
        {
            lockAndClaim(self, event, item);
        }
        else 
        {
            checkForVeteranRewards(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int veteranItemGrantSucceeded(obj_id self, dictionary params) throws InterruptedException
    {
        utils.removeScriptVar(self, SCRIPTVAR_EVENT);
        utils.removeScriptVar(self, SCRIPTVAR_ALL_EVENTS);
        utils.removeScriptVar(self, SCRIPTVAR_ITEM);
        utils.removeScriptVar(self, SCRIPTVAR_REWARD_TAGS);
        boolean canTradeIn = false;
        if ((params != null) && params.containsKey("canTradeIn"))
        {
            canTradeIn = params.getBoolean("canTradeIn");
        }
        if (!canTradeIn)
        {
            sui.msgbox(self, self, ITEM_GRANT_SUCCEEDED, sui.OK_ONLY, ITEM_GRANT_BOX_TITLE, "handleGrantAcknowledged");
        }
        else 
        {
            sui.msgbox(self, self, ITEM_GRANT_SUCCEEDED_CAN_TRADE_IN, sui.OK_ONLY, ITEM_GRANT_BOX_TITLE, "handleGrantAcknowledged");
        }
        clearClaimInProgress(self);
        return SCRIPT_CONTINUE;
    }
    public int veteranItemGrantFailed(obj_id self, dictionary params) throws InterruptedException
    {
        utils.removeScriptVar(self, SCRIPTVAR_EVENT);
        utils.removeScriptVar(self, SCRIPTVAR_ALL_EVENTS);
        utils.removeScriptVar(self, SCRIPTVAR_ITEM);
        utils.removeScriptVar(self, SCRIPTVAR_REWARD_TAGS);
        sui.msgbox(self, self, ITEM_GRANT_FAILED, sui.OK_ONLY, ITEM_GRANT_BOX_TITLE, "handleGrantAcknowledged");
        clearClaimInProgress(self);
        return SCRIPT_CONTINUE;
    }
    public int handleGrantAcknowledged(obj_id self, dictionary params) throws InterruptedException
    {
        utils.removeScriptVar(self, SCRIPTVAR_EVENT);
        utils.removeScriptVar(self, SCRIPTVAR_ALL_EVENTS);
        utils.removeScriptVar(self, SCRIPTVAR_ITEM);
        utils.removeScriptVar(self, SCRIPTVAR_REWARD_TAGS);
        checkForVeteranRewards(self);
        return SCRIPT_CONTINUE;
    }
    public void lockAndClaim(obj_id self, String event, String item) throws InterruptedException
    {
        utils.removeScriptVar(self, SCRIPTVAR_EVENT);
        utils.removeScriptVar(self, SCRIPTVAR_ALL_EVENTS);
        utils.removeScriptVar(self, SCRIPTVAR_ITEM);
        utils.removeScriptVar(self, SCRIPTVAR_REWARD_TAGS);
        if (!hasClaimInProgress(self))
        {
            setClaimInProgress(self);
            veteranClaimReward(self, event, item);
        }
        else 
        {
            sui.msgbox(self, NO_REWARDS_MESSAGE);
        }
    }
    public boolean shouldGetVeteranRewardsMessage(obj_id player) throws InterruptedException
    {
        String area = getCurrentSceneName();
        if (area != null && !area.equals(""))
        {
            return (isInTutorialArea(player) || area.startsWith("space_"));
        }
        return true;
    }
    public void setClaimInProgress(obj_id player) throws InterruptedException
    {
        if (isIdValid(player) && exists(player))
        {
            int timeOut = getGameTime() + CLAIM_IN_PROGRESS_TIMEOUT;
            utils.setScriptVar(player, SCRIPTVAR_CLAIM_IN_PROGRESS, timeOut);
        }
    }
    public boolean hasClaimInProgress(obj_id player) throws InterruptedException
    {
        if (isIdValid(player) && exists(player) && utils.hasScriptVar(player, SCRIPTVAR_CLAIM_IN_PROGRESS))
        {
            int timeOut = utils.getIntScriptVar(player, SCRIPTVAR_CLAIM_IN_PROGRESS);
            int timeNow = getGameTime();
            if (timeOut >= timeNow)
            {
                return true;
            }
        }
        return false;
    }
    public void clearClaimInProgress(obj_id player) throws InterruptedException
    {
        if (isIdValid(player) && exists(player))
        {
            utils.removeScriptVar(player, SCRIPTVAR_CLAIM_IN_PROGRESS);
        }
    }
    public void setAccountFeatureIdRequestInProgress(obj_id player) throws InterruptedException
    {
        if (isIdValid(player) && exists(player))
        {
            int timeOut = getGameTime() + ACCOUNT_FEATURE_ID_REQUEST_IN_PROGRESS_TIMEOUT;
            utils.setScriptVar(player, SCRIPTVAR_ACCOUNT_FEATURE_ID_REQUEST_IN_PROGRESS, timeOut);
        }
    }
    public boolean hasAccountFeatureIdRequestInProgress(obj_id player) throws InterruptedException
    {
        if (isIdValid(player) && exists(player) && utils.hasScriptVar(player, SCRIPTVAR_ACCOUNT_FEATURE_ID_REQUEST_IN_PROGRESS))
        {
            int timeOut = utils.getIntScriptVar(player, SCRIPTVAR_ACCOUNT_FEATURE_ID_REQUEST_IN_PROGRESS);
            int timeNow = getGameTime();
            if (timeOut >= timeNow)
            {
                return true;
            }
        }
        return false;
    }
    public void clearAccountFeatureIdRequestInProgress(obj_id player) throws InterruptedException
    {
        if (isIdValid(player) && exists(player))
        {
            utils.removeScriptVar(player, SCRIPTVAR_ACCOUNT_FEATURE_ID_REQUEST_IN_PROGRESS);
        }
    }
    public boolean isFreeCtsPromptTime(obj_id self) throws InterruptedException
    {
        int nextPromptTime = getIntObjVar(self, OBJVAR_FREE_CTS_NEXT_PROMPT_TIME);
        if (nextPromptTime == 0 || nextPromptTime < getGameTime())
        {
            removeObjVar(self, OBJVAR_FREE_CTS_NEXT_PROMPT_TIME);
            return true;
        }
        return false;
    }
    public boolean checkForFreeCts(obj_id self) throws InterruptedException
    {
        String[] freeCtsClusters = qualifyForFreeCts(self);
        if ((freeCtsClusters != null) && (freeCtsClusters.length > 0))
        {
            Vector choices = new Vector();
            choices.addElement("I am not ready to transfer yet.  Remind me at my next login.");
            choices.addElement("I am not ready to transfer yet.  Remind me tomorrow.");
            choices.addElement("I am not ready to transfer yet.  Remind me in a week.");
            choices.addElement("I am not ready to transfer yet.  Remind me in a month.");
            choices.addElement("");
            choices.addElement("I am ready to transfer now.");
            String announcement = "This character is eligible for a Free Character Transfer to one of the following designated galaxy servers:";
            announcement += "\n\n";
            for (int i = 0; i < freeCtsClusters.length; ++i)
            {
                announcement += "\t";
                announcement += freeCtsClusters[i];
                announcement += "\n";
            }
            announcement += "\n";
            announcement += "Please select one of the following options.";
            int pid = sui.listbox(self, self, announcement, sui.OK_ONLY, "Star Wars Galaxies Free Character Transfer Service", choices, "handleFreeCtsAnnouncement", true, false);
            setFreeCtsSuiId(self, pid);
            return true;
        }
        return false;
    }
    public int cmdFreeCts(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        int secondsUntilCanMakeAnotherFreeCtsRequest = getSecondsUntilCanMakeAnotherFreeCtsRequest(self);
        if (secondsUntilCanMakeAnotherFreeCtsRequest > 0)
        {
            int[] convertedTime = player_structure.convertSecondsTime(secondsUntilCanMakeAnotherFreeCtsRequest);
            sendSystemMessage(self, "This character currently has a pending Free Character Transfer request.  Please wait " + convertedTime[2] + "m:" + convertedTime[3] + "s and try again.", "");
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, SCRIPTVAR_FREE_CTS_SUI_ID))
        {
            int savedPageId = utils.getIntScriptVar(self, SCRIPTVAR_FREE_CTS_SUI_ID);
            utils.removeScriptVar(self, SCRIPTVAR_FREE_CTS_SUI_ID);
            forceCloseSUIPage(savedPageId);
        }
        if (!checkForFreeCts(self))
        {
            sendSystemMessage(self, "This character is not eligible for a Free Character Transfer.", "");
        }
        return SCRIPT_CONTINUE;
    }
    public int handleFreeCtsAnnouncement(obj_id self, dictionary params) throws InterruptedException
    {
        if (!verifyFreeCtsSuiId(self, params.getInt("pageId")))
        {
            return SCRIPT_CONTINUE;
        }
        String[] freeCtsClusters = qualifyForFreeCts(self);
        if ((freeCtsClusters == null) || (freeCtsClusters.length <= 0))
        {
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_OK)
        {
            int rowSelected = sui.getListboxSelectedRow(params);
            if (rowSelected == 0)
            {
                removeObjVar(self, OBJVAR_FREE_CTS_NEXT_PROMPT_TIME);
                sui.msgbox(self, self, "You will be reminded the next time you log in.  You may also type \"/freeCts\" to initiate your character transfer at any time.", sui.OK_ONLY, "Star Wars Galaxies Free Character Transfer Service", "noHandler");
            }
            else if ((rowSelected == 1) || (rowSelected == 2) || (rowSelected == 3))
            {
                int waitForNextPrompt = 0;
                if (rowSelected == 1)
                {
                    sui.msgbox(self, self, "You will be reminded the next time you log in after 24 hours have passed.  You may also type \"/freeCts\" to initiate your character transfer at any time.", sui.OK_ONLY, "Star Wars Galaxies Free Character Transfer Service", "noHandler");
                    waitForNextPrompt = 24 * 60 * 60;
                }
                else if (rowSelected == 2)
                {
                    sui.msgbox(self, self, "You will be reminded the next time you log in after a week has passed.  You may also type \"/freeCts\" to initiate your character transfer at any time.", sui.OK_ONLY, "Star Wars Galaxies Free Character Transfer Service", "noHandler");
                    waitForNextPrompt = 7 * 24 * 60 * 60;
                }
                else 
                {
                    sui.msgbox(self, self, "You will be reminded the next time you log in after 30 days have passed.  You may also type \"/freeCts\" to initiate your character transfer at any time.", sui.OK_ONLY, "Star Wars Galaxies Free Character Transfer Service", "noHandler");
                    waitForNextPrompt = 30 * 24 * 60 * 60;
                }
                int gameTime = getGameTime();
                int nextPromptTime = gameTime + waitForNextPrompt;
                setObjVar(self, OBJVAR_FREE_CTS_NEXT_PROMPT_TIME, nextPromptTime);
            }
            else if (rowSelected == 5)
            {
                removeObjVar(self, OBJVAR_FREE_CTS_NEXT_PROMPT_TIME);
                if (isValidLocationForCts(self, true))
                {
                    int pid = sui.msgbox(self, self, "@veteran_new:free_cts_legal", sui.OK_CANCEL, "Guidelines and Restrictions", "handleFreeCtsLegal");
                    sui.setSUIProperty(pid, sui.MSGBOX_BTN_CANCEL, sui.PROP_TEXT, "Exit");
                    sui.setSUIProperty(pid, sui.MSGBOX_BTN_OK, sui.PROP_TEXT, "Next");
                    showSUIPage(pid);
                    setFreeCtsSuiId(self, pid);
                }
            }
            else 
            {
                removeObjVar(self, OBJVAR_FREE_CTS_NEXT_PROMPT_TIME);
                sui.msgbox(self, self, "You will be reminded the next time you log in.  You may also type \"/freeCts\" to initiate your character transfer at any time.", sui.OK_ONLY, "Star Wars Galaxies Free Character Transfer Service", "noHandler");
            }
        }
        else 
        {
            removeObjVar(self, OBJVAR_FREE_CTS_NEXT_PROMPT_TIME);
            sui.msgbox(self, self, "You will be reminded the next time you log in.  You may also type \"/freeCts\" to initiate your character transfer at any time.", sui.OK_ONLY, "Star Wars Galaxies Free Character Transfer Service", "noHandler");
        }
        return SCRIPT_CONTINUE;
    }
    public boolean isValidLocationForCts(obj_id self, boolean freeCts) throws InterruptedException
    {
        if (!isIdValid(self) || !exists(self))
        {
            return false;
        }
        location loc = getLocation(self);
        if (loc == null)
        {
            return false;
        }
        if (loc.area.equals("tatooine") || loc.area.equals("naboo") || loc.area.equals("corellia") || loc.area.equals("rori") || loc.area.equals("talus") || loc.area.equals("yavin4") || loc.area.equals("endor") || loc.area.equals("lok") || loc.area.equals("dantooine") || loc.area.equals("dathomir"))
        {
            return true;
        }
        if (freeCts)
        {
            sui.msgbox(self, self, "You must be located on one of the original 10 ground planets (Corellia, Dantooine, Dathomir, Endor, Lok, Naboo, Rori, Talus, Tatooine, and Yavin 4) before you can transfer.  Type \"/freeCts\" when you are ready to attempt your character transfer again.", sui.OK_ONLY, "Star Wars Galaxies Free Character Transfer Service", "noHandler");
        }
        else 
        {
            sui.msgbox(self, self, "You must be located on one of the original 10 ground planets (Corellia, Dantooine, Dathomir, Endor, Lok, Naboo, Rori, Talus, Tatooine, and Yavin 4) before you can transfer.", sui.OK_ONLY, "Star Wars Galaxies Character Transfer Service", "noHandler");
        }
        return false;
    }
    public int handleFreeCtsLegal(obj_id self, dictionary params) throws InterruptedException
    {
        if (!verifyFreeCtsSuiId(self, params.getInt("pageId")))
        {
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_OK)
        {
            freeCtsDisplayDestGalaxyChoice(self, "");
        }
        return SCRIPT_CONTINUE;
    }
    public void freeCtsDisplayDestGalaxyChoice(obj_id self, String messageHeader) throws InterruptedException
    {
        if (!isIdValid(self) || !exists(self))
        {
            return;
        }
        String[] freeCtsClusters = qualifyForFreeCts(self);
        if ((freeCtsClusters == null) || (freeCtsClusters.length <= 0))
        {
            return;
        }
        Vector choices = new Vector();
        for (int i = 0; i < freeCtsClusters.length; ++i)
        {
            choices.addElement(freeCtsClusters[i]);
        }
        String announcement = "";
        if ((messageHeader != null) && (messageHeader.length() > 0))
        {
            announcement = messageHeader;
            announcement += "\n\n";
        }
        announcement += "Please select which destination galaxy server you would like to transfer this character to for free and click \"Next\" to proceed.";
        int pid = sui.listbox(self, self, announcement, sui.OK_CANCEL, "Galaxy Selection", choices, "handleFreeCtsDestinationGalaxyChoice", false, false);
        sui.setSUIProperty(pid, sui.LISTBOX_BTN_CANCEL, sui.PROP_TEXT, "Exit");
        sui.setSUIProperty(pid, sui.LISTBOX_BTN_OK, sui.PROP_TEXT, "Next");
        showSUIPage(pid);
        setFreeCtsSuiId(self, pid);
    }
    public int handleFreeCtsDestinationGalaxyChoice(obj_id self, dictionary params) throws InterruptedException
    {
        if (!verifyFreeCtsSuiId(self, params.getInt("pageId")))
        {
            return SCRIPT_CONTINUE;
        }
        String[] freeCtsClusters = qualifyForFreeCts(self);
        if ((freeCtsClusters == null) || (freeCtsClusters.length <= 0))
        {
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_OK)
        {
            int rowSelected = sui.getListboxSelectedRow(params);
            if (rowSelected < freeCtsClusters.length)
            {
                validateFreeCts(self, freeCtsClusters[rowSelected], getName(self));
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleFreeCtsValidateFailCannotCreateCharacter(obj_id self, dictionary params) throws InterruptedException
    {
        String destinationGalaxy = params.getString("destinationGalaxy");
        if ((destinationGalaxy != null) && (destinationGalaxy.length() > 0))
        {
            freeCtsDisplayDestGalaxyChoice(self, "\\" + colors_hex.RED + "You cannot create any more characters on the selected destination galaxy " + destinationGalaxy + "." + "\\" + colors_hex.WHITE);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleFreeCtsValidateFailDestGalaxyUnavailable(obj_id self, dictionary params) throws InterruptedException
    {
        String destinationGalaxy = params.getString("destinationGalaxy");
        if ((destinationGalaxy != null) && (destinationGalaxy.length() > 0))
        {
            freeCtsDisplayDestGalaxyChoice(self, "\\" + colors_hex.RED + "The selected destination galaxy " + destinationGalaxy + " is currently unavailable." + "\\" + colors_hex.WHITE);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleFreeCtsValidateFailNameValidation(obj_id self, dictionary params) throws InterruptedException
    {
        String destinationGalaxy = params.getString("destinationGalaxy");
        String destinationCharacterName = params.getString("destinationCharacterName");
        if ((destinationGalaxy != null) && (destinationGalaxy.length() > 0) && (destinationCharacterName != null) && (destinationCharacterName.length() > 0))
        {
            freeCtsDisplayDestCharacterNameInput(self, destinationGalaxy, destinationCharacterName, params.getString("reason"));
        }
        return SCRIPT_CONTINUE;
    }
    public void freeCtsDisplayDestCharacterNameInput(obj_id self, String destinationGalaxy, String destinationCharacterName, String reason) throws InterruptedException
    {
        if (!isIdValid(self) || !exists(self))
        {
            return;
        }
        String[] freeCtsClusters = qualifyForFreeCts(self);
        if ((freeCtsClusters == null) || (freeCtsClusters.length <= 0))
        {
            return;
        }
        String announcement = "The name of your character, \"";
        announcement += destinationCharacterName;
        announcement += "\", is not available on ";
        announcement += destinationGalaxy;
        announcement += ".  The name may already be in use or there may be invalid characters used in the name.  Please enter a different name for your character.";
        if ((reason != null) && (reason.length() > 0))
        {
            announcement = reason;
        }
        int pid = sui.inputbox(self, self, announcement, "Please Choose a New Name", "handleFreeCtsDestinationCharacerNameInput", 125, false, "");
        sui.setSUIProperty(pid, sui.INPUTBOX_BTN_CANCEL, sui.PROP_TEXT, "Exit");
        sui.setSUIProperty(pid, sui.INPUTBOX_BTN_OK, sui.PROP_TEXT, "Next");
        sui.setSUIProperty(pid, sui.INPUTBOX_INPUT, sui.PROP_TEXT, destinationCharacterName);
        setSUIProperty(pid, sui.INPUTBOX_INPUT, "MaxLength", String.valueOf(125));
        showSUIPage(pid);
        setFreeCtsSuiId(self, pid);
        utils.setScriptVar(self, "freeCtsDestinationGalaxy", destinationGalaxy);
    }
    public int handleFreeCtsDestinationCharacerNameInput(obj_id self, dictionary params) throws InterruptedException
    {
        if (!verifyFreeCtsSuiId(self, params.getInt("pageId")))
        {
            return SCRIPT_CONTINUE;
        }
        String destinationGalaxy = utils.getStringScriptVar(self, "freeCtsDestinationGalaxy");
        utils.removeScriptVar(self, "freeCtsDestinationGalaxy");
        if ((destinationGalaxy == null) || (destinationGalaxy.length() <= 0))
        {
            return SCRIPT_CONTINUE;
        }
        String[] freeCtsClusters = qualifyForFreeCts(self);
        if ((freeCtsClusters == null) || (freeCtsClusters.length <= 0))
        {
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_OK)
        {
            String destinationCharacterName = sui.getInputBoxText(params);
            if ((destinationCharacterName != null) && (destinationCharacterName.length() > 0))
            {
                validateFreeCts(self, destinationGalaxy, destinationCharacterName);
            }
            else 
            {
                freeCtsDisplayDestCharacterNameInput(self, destinationGalaxy, "", "Please enter a name.");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleFreeCtsValidateSuccess(obj_id self, dictionary params) throws InterruptedException
    {
        String destinationGalaxy = params.getString("destinationGalaxy");
        String destinationCharacterName = params.getString("destinationCharacterName");
        if ((destinationGalaxy != null) && (destinationGalaxy.length() > 0) && (destinationCharacterName != null) && (destinationCharacterName.length() > 0))
        {
            int pid = sui.msgbox(self, self, "@veteran_new:free_cts_are_you_sure", sui.OK_CANCEL, "Are you sure?", "handleFreeCtsAreYouSure");
            sui.setSUIProperty(pid, sui.MSGBOX_BTN_CANCEL, sui.PROP_TEXT, "Exit");
            sui.setSUIProperty(pid, sui.MSGBOX_BTN_OK, sui.PROP_TEXT, "Next");
            showSUIPage(pid);
            setFreeCtsSuiId(self, pid);
            utils.setScriptVar(self, "freeCtsDestinationGalaxy", destinationGalaxy);
            utils.setScriptVar(self, "freeCtsDestinationCharacterName", destinationCharacterName);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleFreeCtsAreYouSure(obj_id self, dictionary params) throws InterruptedException
    {
        if (!verifyFreeCtsSuiId(self, params.getInt("pageId")))
        {
            return SCRIPT_CONTINUE;
        }
        String destinationGalaxy = utils.getStringScriptVar(self, "freeCtsDestinationGalaxy");
        if ((destinationGalaxy == null) || (destinationGalaxy.length() <= 0))
        {
            return SCRIPT_CONTINUE;
        }
        String destinationCharacterName = utils.getStringScriptVar(self, "freeCtsDestinationCharacterName");
        if ((destinationCharacterName == null) || (destinationCharacterName.length() <= 0))
        {
            return SCRIPT_CONTINUE;
        }
        String[] freeCtsClusters = qualifyForFreeCts(self);
        if ((freeCtsClusters == null) || (freeCtsClusters.length <= 0))
        {
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_OK)
        {
            String sourceCharacterName = getName(self);
            String announcement = "Character:\t\t\t" + sourceCharacterName + "\n";
            announcement += "Transferring From:\t" + getClusterName() + "\n";
            announcement += "\n";
            announcement += "Transferring To:\t" + destinationGalaxy;
            if (!destinationCharacterName.equals(sourceCharacterName))
            {
                announcement += ", " + destinationCharacterName;
            }
            announcement += "\n\n";
            announcement += "Click \"Transfer\" to begin the free transfer of this character.  Once selected, this action cannot be undone.  You will be disconnected when you click the \"Transfer\" button.  Please wait at least two (2) hours before logging back in.  When you do log back in, your character will be in their new galaxy!";
            int pid = sui.msgbox(self, self, announcement, sui.OK_CANCEL, "Free Character Transfer Confirmation", "handleFreeCtsConfirmation");
            sui.setSUIProperty(pid, sui.MSGBOX_BTN_CANCEL, sui.PROP_TEXT, "Exit");
            sui.setSUIProperty(pid, sui.MSGBOX_BTN_OK, sui.PROP_TEXT, "Transfer");
            showSUIPage(pid);
            setFreeCtsSuiId(self, pid);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleFreeCtsConfirmation(obj_id self, dictionary params) throws InterruptedException
    {
        if (!verifyFreeCtsSuiId(self, params.getInt("pageId")))
        {
            return SCRIPT_CONTINUE;
        }
        String destinationGalaxy = utils.getStringScriptVar(self, "freeCtsDestinationGalaxy");
        utils.removeScriptVar(self, "freeCtsDestinationGalaxy");
        if ((destinationGalaxy == null) || (destinationGalaxy.length() <= 0))
        {
            return SCRIPT_CONTINUE;
        }
        String destinationCharacterName = utils.getStringScriptVar(self, "freeCtsDestinationCharacterName");
        utils.removeScriptVar(self, "freeCtsDestinationCharacterName");
        if ((destinationCharacterName == null) || (destinationCharacterName.length() <= 0))
        {
            return SCRIPT_CONTINUE;
        }
        String[] freeCtsClusters = qualifyForFreeCts(self);
        if ((freeCtsClusters == null) || (freeCtsClusters.length <= 0))
        {
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        if ((bp == sui.BP_OK) && (isValidLocationForCts(self, true)))
        {
            performFreeCts(self, destinationGalaxy, destinationCharacterName);
            utils.setScriptVar(self, SCRIPTVAR_FREE_CTS_REQUEST_TIMEOUT, getGameTime() + 300);
        }
        return SCRIPT_CONTINUE;
    }
    public boolean verifyFreeCtsSuiId(obj_id self, int pageId) throws InterruptedException
    {
        if (!isIdValid(self) || !exists(self))
        {
            return false;
        }
        if (!utils.hasScriptVar(self, SCRIPTVAR_FREE_CTS_SUI_ID))
        {
            return false;
        }
        int savedPageId = utils.getIntScriptVar(self, SCRIPTVAR_FREE_CTS_SUI_ID);
        utils.removeScriptVar(self, SCRIPTVAR_FREE_CTS_SUI_ID);
        if (savedPageId == pageId)
        {
            return true;
        }
        return false;
    }
    public void setFreeCtsSuiId(obj_id self, int pageId) throws InterruptedException
    {
        if (!isIdValid(self) || !exists(self))
        {
            return;
        }
        utils.setScriptVar(self, SCRIPTVAR_FREE_CTS_SUI_ID, pageId);
        utils.removeScriptVar(self, SCRIPTVAR_FREE_CTS_REQUEST_TIMEOUT);
    }
    public int getSecondsUntilCanMakeAnotherFreeCtsRequest(obj_id self) throws InterruptedException
    {
        if (!isIdValid(self) || !exists(self))
        {
            return 0;
        }
        if (!utils.hasScriptVar(self, SCRIPTVAR_FREE_CTS_REQUEST_TIMEOUT))
        {
            return 0;
        }
        int gameTimePreviousRequestTimeout = utils.getIntScriptVar(self, SCRIPTVAR_FREE_CTS_REQUEST_TIMEOUT);
        int gameTimeNow = getGameTime();
        if (gameTimeNow >= gameTimePreviousRequestTimeout)
        {
            utils.removeScriptVar(self, SCRIPTVAR_FREE_CTS_REQUEST_TIMEOUT);
            return 0;
        }
        return (gameTimePreviousRequestTimeout - gameTimeNow);
    }
    public int handleRewardTradeInConfirmation(obj_id self, dictionary params) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, SCRIPTVAR_REWARD_TRADE_IN_SUI_ID))
        {
            return SCRIPT_CONTINUE;
        }
        if (!utils.hasScriptVar(self, SCRIPTVAR_REWARD_TRADE_IN_ITEM_ID))
        {
            return SCRIPT_CONTINUE;
        }
        if (params.getInt("pageId") != utils.getIntScriptVar(self, SCRIPTVAR_REWARD_TRADE_IN_SUI_ID))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id item = utils.getObjIdScriptVar(self, SCRIPTVAR_REWARD_TRADE_IN_ITEM_ID);
        if (!isIdValid(item) || !exists(item))
        {
            return SCRIPT_CONTINUE;
        }
        utils.removeScriptVar(self, SCRIPTVAR_REWARD_TRADE_IN_SUI_ID);
        utils.removeScriptVar(self, SCRIPTVAR_REWARD_TRADE_IN_ITEM_ID);
        if (sui.getIntButtonPressed(params) == sui.BP_OK)
        {
            veteranTradeInReward(self, item);
        }
        return SCRIPT_CONTINUE;
    }
    public int initiateCtsFromItem(obj_id self, dictionary params) throws InterruptedException
    {
        final int secondsUntilCanMakeAnotherCtsRequest = getSecondsUntilCanMakeAnotherCtsRequest(self);
        if (secondsUntilCanMakeAnotherCtsRequest > 0)
        {
            sendSystemMessage(self, "This character currently has a pending Character Transfer request.  Please wait " + (secondsUntilCanMakeAnotherCtsRequest / 60) + "m:" + (secondsUntilCanMakeAnotherCtsRequest % 60) + "s and try again.", "");
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, SCRIPTVAR_CTS_SUI_ID))
        {
            final int savedPageId = utils.getIntScriptVar(self, SCRIPTVAR_CTS_SUI_ID);
            utils.removeScriptVar(self, SCRIPTVAR_CTS_SUI_ID);
            forceCloseSUIPage(savedPageId);
        }
        final obj_id item = params.getObjId("item");
        if (!verifyItemForCts(self, item))
        {
            return SCRIPT_CONTINUE;
        }
        final String[] ctsDestinationClusters = getCtsDestinationClusters();
        if ((ctsDestinationClusters == null) || (ctsDestinationClusters.length <= 0))
        {
            sendSystemMessage(self, "Character Transfer is currently not allowed from this galaxy server.", "");
            return SCRIPT_CONTINUE;
        }
        if (isValidLocationForCts(self, false))
        {
            final int pid = sui.msgbox(self, self, "@veteran_new:cts_legal", sui.OK_CANCEL, "Guidelines and Restrictions", "handleCtsLegal");
            sui.setSUIProperty(pid, sui.MSGBOX_BTN_CANCEL, sui.PROP_TEXT, "Exit");
            sui.setSUIProperty(pid, sui.MSGBOX_BTN_OK, sui.PROP_TEXT, "Next");
            showSUIPage(pid);
            setCtsSuiId(self, item, pid);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleCtsLegal(obj_id self, dictionary params) throws InterruptedException
    {
        final obj_id item = verifyCtsSuiId(self, params.getInt("pageId"));
        if (!isValidId(item))
        {
            return SCRIPT_CONTINUE;
        }
        final int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_OK)
        {
            ctsDisplayDestGalaxyChoice(self, item, "");
        }
        return SCRIPT_CONTINUE;
    }
    public void ctsDisplayDestGalaxyChoice(obj_id self, obj_id item, String messageHeader) throws InterruptedException
    {
        if (!isIdValid(self) || !exists(self) || !isIdValid(item) || !exists(item))
        {
            return;
        }
        final String[] ctsDestinationClusters = getCtsDestinationClusters();
        if ((ctsDestinationClusters == null) || (ctsDestinationClusters.length <= 0))
        {
            return;
        }
        Vector choices = new Vector();
        for (int i = 0; i < ctsDestinationClusters.length; ++i)
        {
            choices.addElement(ctsDestinationClusters[i]);
        }
        String announcement = "";
        if ((messageHeader != null) && (messageHeader.length() > 0))
        {
            announcement = messageHeader;
            announcement += "\n\n";
        }
        announcement += "Please select which destination galaxy server you would like to transfer this character to and click \"Next\" to proceed.";
        final int pid = sui.listbox(self, self, announcement, sui.OK_CANCEL, "Galaxy Selection", choices, "handleCtsDestinationGalaxyChoice", false, false);
        sui.setSUIProperty(pid, sui.LISTBOX_BTN_CANCEL, sui.PROP_TEXT, "Exit");
        sui.setSUIProperty(pid, sui.LISTBOX_BTN_OK, sui.PROP_TEXT, "Next");
        showSUIPage(pid);
        setCtsSuiId(self, item, pid);
    }
    public int handleCtsDestinationGalaxyChoice(obj_id self, dictionary params) throws InterruptedException
    {
        final obj_id item = verifyCtsSuiId(self, params.getInt("pageId"));
        if (!isValidId(item))
        {
            return SCRIPT_CONTINUE;
        }
        final String[] ctsDestinationClusters = getCtsDestinationClusters();
        if ((ctsDestinationClusters == null) || (ctsDestinationClusters.length <= 0))
        {
            return SCRIPT_CONTINUE;
        }
        final int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_OK)
        {
            final int rowSelected = sui.getListboxSelectedRow(params);
            if (rowSelected < ctsDestinationClusters.length)
            {
                utils.setScriptVar(self, cts.SCRIPTVAR_CTS_ITEM_ID, item);
                validateCts(self, ctsDestinationClusters[rowSelected], getName(self));
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleCtsValidateFailCannotCreateCharacter(obj_id self, dictionary params) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, cts.SCRIPTVAR_CTS_ITEM_ID))
        {
            return SCRIPT_CONTINUE;
        }
        final obj_id item = utils.getObjIdScriptVar(self, cts.SCRIPTVAR_CTS_ITEM_ID);
        utils.removeScriptVar(self, cts.SCRIPTVAR_CTS_ITEM_ID);
        final String destinationGalaxy = params.getString("destinationGalaxy");
        if ((destinationGalaxy != null) && (destinationGalaxy.length() > 0))
        {
            ctsDisplayDestGalaxyChoice(self, item, "\\" + colors_hex.RED + "You cannot create any more characters on the selected destination galaxy " + destinationGalaxy + "." + "\\" + colors_hex.WHITE);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleCtsValidateFailDestGalaxyUnavailable(obj_id self, dictionary params) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, cts.SCRIPTVAR_CTS_ITEM_ID))
        {
            return SCRIPT_CONTINUE;
        }
        final obj_id item = utils.getObjIdScriptVar(self, cts.SCRIPTVAR_CTS_ITEM_ID);
        utils.removeScriptVar(self, cts.SCRIPTVAR_CTS_ITEM_ID);
        final String destinationGalaxy = params.getString("destinationGalaxy");
        if ((destinationGalaxy != null) && (destinationGalaxy.length() > 0))
        {
            ctsDisplayDestGalaxyChoice(self, item, "\\" + colors_hex.RED + "The selected destination galaxy " + destinationGalaxy + " is currently unavailable." + "\\" + colors_hex.WHITE);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleCtsValidateFailNameValidation(obj_id self, dictionary params) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, cts.SCRIPTVAR_CTS_ITEM_ID))
        {
            return SCRIPT_CONTINUE;
        }
        final obj_id item = utils.getObjIdScriptVar(self, cts.SCRIPTVAR_CTS_ITEM_ID);
        utils.removeScriptVar(self, cts.SCRIPTVAR_CTS_ITEM_ID);
        final String destinationGalaxy = params.getString("destinationGalaxy");
        final String destinationCharacterName = params.getString("destinationCharacterName");
        if ((destinationGalaxy != null) && (destinationGalaxy.length() > 0) && (destinationCharacterName != null) && (destinationCharacterName.length() > 0))
        {
            ctsDisplayDestCharacterNameInput(self, item, destinationGalaxy, destinationCharacterName, params.getString("reason"));
        }
        return SCRIPT_CONTINUE;
    }
    public void ctsDisplayDestCharacterNameInput(obj_id self, obj_id item, String destinationGalaxy, String destinationCharacterName, String reason) throws InterruptedException
    {
        if (!isIdValid(self) || !exists(self) || !isIdValid(item) || !exists(item))
        {
            return;
        }
        String announcement = "The name of your character, \"";
        announcement += destinationCharacterName;
        announcement += "\", is not available on ";
        announcement += destinationGalaxy;
        announcement += ".  The name may already be in use or there may be invalid characters used in the name.  Please enter a different name for your character.";
        if ((reason != null) && (reason.length() > 0))
        {
            announcement = reason;
        }
        final int pid = sui.inputbox(self, self, announcement, "Please Choose a New Name", "handleCtsDestinationCharacerNameInput", 125, false, "");
        sui.setSUIProperty(pid, sui.INPUTBOX_BTN_CANCEL, sui.PROP_TEXT, "Exit");
        sui.setSUIProperty(pid, sui.INPUTBOX_BTN_OK, sui.PROP_TEXT, "Next");
        sui.setSUIProperty(pid, sui.INPUTBOX_INPUT, sui.PROP_TEXT, destinationCharacterName);
        setSUIProperty(pid, sui.INPUTBOX_INPUT, "MaxLength", String.valueOf(125));
        showSUIPage(pid);
        setCtsSuiId(self, item, pid);
        utils.setScriptVar(self, "ctsDestinationGalaxy", destinationGalaxy);
    }
    public int handleCtsDestinationCharacerNameInput(obj_id self, dictionary params) throws InterruptedException
    {
        final obj_id item = verifyCtsSuiId(self, params.getInt("pageId"));
        if (!isValidId(item))
        {
            return SCRIPT_CONTINUE;
        }
        final String destinationGalaxy = utils.getStringScriptVar(self, "ctsDestinationGalaxy");
        utils.removeScriptVar(self, "ctsDestinationGalaxy");
        if ((destinationGalaxy == null) || (destinationGalaxy.length() <= 0))
        {
            return SCRIPT_CONTINUE;
        }
        final int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_OK)
        {
            final String destinationCharacterName = sui.getInputBoxText(params);
            if ((destinationCharacterName != null) && (destinationCharacterName.length() > 0))
            {
                utils.setScriptVar(self, cts.SCRIPTVAR_CTS_ITEM_ID, item);
                validateCts(self, destinationGalaxy, destinationCharacterName);
            }
            else 
            {
                ctsDisplayDestCharacterNameInput(self, item, destinationGalaxy, "", "Please enter a name.");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleCtsValidateSuccess(obj_id self, dictionary params) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, cts.SCRIPTVAR_CTS_ITEM_ID))
        {
            return SCRIPT_CONTINUE;
        }
        final obj_id item = utils.getObjIdScriptVar(self, cts.SCRIPTVAR_CTS_ITEM_ID);
        utils.removeScriptVar(self, cts.SCRIPTVAR_CTS_ITEM_ID);
        final String destinationGalaxy = params.getString("destinationGalaxy");
        final String destinationCharacterName = params.getString("destinationCharacterName");
        if ((destinationGalaxy != null) && (destinationGalaxy.length() > 0) && (destinationCharacterName != null) && (destinationCharacterName.length() > 0))
        {
            final int pid = sui.msgbox(self, self, "@veteran_new:free_cts_are_you_sure", sui.OK_CANCEL, "Are you sure?", "handleCtsAreYouSure");
            sui.setSUIProperty(pid, sui.MSGBOX_BTN_CANCEL, sui.PROP_TEXT, "Exit");
            sui.setSUIProperty(pid, sui.MSGBOX_BTN_OK, sui.PROP_TEXT, "Next");
            showSUIPage(pid);
            setCtsSuiId(self, item, pid);
            utils.setScriptVar(self, "ctsDestinationGalaxy", destinationGalaxy);
            utils.setScriptVar(self, "ctsDestinationCharacterName", destinationCharacterName);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleCtsAreYouSure(obj_id self, dictionary params) throws InterruptedException
    {
        final obj_id item = verifyCtsSuiId(self, params.getInt("pageId"));
        if (!isValidId(item))
        {
            return SCRIPT_CONTINUE;
        }
        final String destinationGalaxy = utils.getStringScriptVar(self, "ctsDestinationGalaxy");
        if ((destinationGalaxy == null) || (destinationGalaxy.length() <= 0))
        {
            return SCRIPT_CONTINUE;
        }
        final String destinationCharacterName = utils.getStringScriptVar(self, "ctsDestinationCharacterName");
        if ((destinationCharacterName == null) || (destinationCharacterName.length() <= 0))
        {
            return SCRIPT_CONTINUE;
        }
        final int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_OK)
        {
            final String sourceCharacterName = getName(self);
            String announcement = "Character:\t\t\t" + sourceCharacterName + "\n";
            announcement += "Transferring From:\t" + getClusterName() + "\n";
            announcement += "\n";
            announcement += "Transferring To:\t" + destinationGalaxy;
            if (!destinationCharacterName.equals(sourceCharacterName))
            {
                announcement += ", " + destinationCharacterName;
            }
            announcement += "\n\n";
            announcement += "Click \"Transfer\" to begin the transfer of this character.  Once selected, this action cannot be undone.  You will be disconnected when you click the \"Transfer\" button.  Please wait at least two (2) hours before logging back in.  When you do log back in, your character will be in their new galaxy!";
            final int pid = sui.msgbox(self, self, announcement, sui.OK_CANCEL, "Character Transfer Confirmation", "handleCtsConfirmation");
            sui.setSUIProperty(pid, sui.MSGBOX_BTN_CANCEL, sui.PROP_TEXT, "Exit");
            sui.setSUIProperty(pid, sui.MSGBOX_BTN_OK, sui.PROP_TEXT, "Transfer");
            showSUIPage(pid);
            setCtsSuiId(self, item, pid);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleCtsConfirmation(obj_id self, dictionary params) throws InterruptedException
    {
        final obj_id item = verifyCtsSuiId(self, params.getInt("pageId"));
        if (!isValidId(item))
        {
            return SCRIPT_CONTINUE;
        }
        final String destinationGalaxy = utils.getStringScriptVar(self, "ctsDestinationGalaxy");
        utils.removeScriptVar(self, "ctsDestinationGalaxy");
        if ((destinationGalaxy == null) || (destinationGalaxy.length() <= 0))
        {
            return SCRIPT_CONTINUE;
        }
        final String destinationCharacterName = utils.getStringScriptVar(self, "ctsDestinationCharacterName");
        utils.removeScriptVar(self, "ctsDestinationCharacterName");
        if ((destinationCharacterName == null) || (destinationCharacterName.length() <= 0))
        {
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        if ((bp == sui.BP_OK) && isValidLocationForCts(self, false) && verifyItemForCts(self, item))
        {
            CustomerServiceLog("CharacterTransfer", "requesting ingame CTS for character " + self + " using item " + item);
            performCts(self, destinationGalaxy, destinationCharacterName);
            utils.setScriptVar(self, cts.SCRIPTVAR_CTS_ITEM_ID, item);
            utils.setScriptVar(self, SCRIPTVAR_CTS_REQUEST_TIMEOUT, getGameTime() + 300);
            setObjVar(item, "ctsItemUsage.cluster", getClusterName());
            setObjVar(item, "ctsItemUsage.user", self);
            setObjVar(item, "ctsItemUsage.time", getCalendarTime());
        }
        return SCRIPT_CONTINUE;
    }
    public boolean verifyItemForCts(obj_id player, obj_id item) throws InterruptedException
    {
        if (!isValidId(player))
        {
            return false;
        }
        if (!isValidId(item))
        {
            sui.msgbox(player, player, "The item to be used for Character Transfer must be in your top level inventory.", sui.OK_ONLY, "Star Wars Galaxies Character Transfer Service", "noHandler");
            return false;
        }
        final obj_id itemContainer = getContainedBy(item);
        if (!isValidId(itemContainer))
        {
            sui.msgbox(player, player, "The item to be used for Character Transfer must be in your top level inventory.", sui.OK_ONLY, "Star Wars Galaxies Character Transfer Service", "noHandler");
            return false;
        }
        final obj_id playerInventory = getObjectInSlot(player, "inventory");
        if (!isValidId(playerInventory))
        {
            sui.msgbox(player, player, "The item to be used for Character Transfer must be in your top level inventory.", sui.OK_ONLY, "Star Wars Galaxies Character Transfer Service", "noHandler");
            return false;
        }
        if (itemContainer != playerInventory)
        {
            sui.msgbox(player, player, "The item to be used for Character Transfer must be in your top level inventory.", sui.OK_ONLY, "Star Wars Galaxies Character Transfer Service", "noHandler");
            return false;
        }
        if (hasObjVar(item, "ctsItemUsage.user"))
        {
            obj_id user = getObjIdObjVar(item, "ctsItemUsage.user");
            if (isValidId(user))
            {
                if (user == player)
                {
                    removeObjVar(item, "ctsItemUsage.cluster");
                    removeObjVar(item, "ctsItemUsage.user");
                    removeObjVar(item, "ctsItemUsage.time");
                }
                else 
                {
                    sui.msgbox(player, player, "The item has already been used by someone else to perform a Character Transfer.", sui.OK_ONLY, "Star Wars Galaxies Character Transfer Service", "noHandler");
                    return false;
                }
            }
        }
        return true;
    }
    public void setCtsSuiId(obj_id self, obj_id item, int pageId) throws InterruptedException
    {
        if (!isIdValid(self) || !exists(self) || !isIdValid(item) || !exists(item))
        {
            return;
        }
        utils.setScriptVar(self, SCRIPTVAR_CTS_SUI_ID, pageId);
        utils.setScriptVar(self, cts.SCRIPTVAR_CTS_ITEM_ID, item);
        utils.removeScriptVar(self, SCRIPTVAR_CTS_REQUEST_TIMEOUT);
    }
    public obj_id verifyCtsSuiId(obj_id self, int pageId) throws InterruptedException
    {
        if (!isIdValid(self) || !exists(self))
        {
            return null;
        }
        if (!utils.hasScriptVar(self, SCRIPTVAR_CTS_SUI_ID))
        {
            return null;
        }
        final int savedPageId = utils.getIntScriptVar(self, SCRIPTVAR_CTS_SUI_ID);
        utils.removeScriptVar(self, SCRIPTVAR_CTS_SUI_ID);
        if (savedPageId == pageId)
        {
            if (!utils.hasScriptVar(self, cts.SCRIPTVAR_CTS_ITEM_ID))
            {
                return null;
            }
            final obj_id item = utils.getObjIdScriptVar(self, cts.SCRIPTVAR_CTS_ITEM_ID);
            utils.removeScriptVar(self, cts.SCRIPTVAR_CTS_ITEM_ID);
            return item;
        }
        return null;
    }
    public int getSecondsUntilCanMakeAnotherCtsRequest(obj_id self) throws InterruptedException
    {
        if (!isIdValid(self) || !exists(self))
        {
            return 0;
        }
        if (!utils.hasScriptVar(self, SCRIPTVAR_CTS_REQUEST_TIMEOUT))
        {
            return 0;
        }
        final int gameTimePreviousRequestTimeout = utils.getIntScriptVar(self, SCRIPTVAR_CTS_REQUEST_TIMEOUT);
        final int gameTimeNow = getGameTime();
        if (gameTimeNow >= gameTimePreviousRequestTimeout)
        {
            utils.removeScriptVar(self, SCRIPTVAR_CTS_REQUEST_TIMEOUT);
            return 0;
        }
        return (gameTimePreviousRequestTimeout - gameTimeNow);
    }
}
