package script.working.cthurow;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.features;
import script.library.sui;
import script.library.utils;

public class veterantest extends script.base_script
{
    public veterantest()
    {
    }
    public static final String ANNOUNCEMENT_BOX_TITLE = "Announcement Box Title";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        debugSpeakMsg(self, "veteran test script attached.  Say \"lewt\" to get stuff.");
        return SCRIPT_CONTINUE;
    }
    public int OnHearSpeech(obj_id self, obj_id speaker, String text) throws InterruptedException
    {
        if (text.equals("lewt"))
        {
            checkForVeteranRewards(self);
        }
        return SCRIPT_CONTINUE;
    }
    public void checkForVeteranRewards(obj_id self) throws InterruptedException
    {
    }
    public int handleRewardAnnouncement(obj_id self, dictionary params) throws InterruptedException
    {
        String event = utils.getStringScriptVar(self, "veteran_rewards.event");
        int rowSelected = sui.getListboxSelectedRow(params);
        debugSpeakMsg(self, "In handleRewardAnnouncement.  Row selected is " + rowSelected);
        if (rowSelected == 0)
        {
            chooseRewardForEvent(self, event);
        }
        else 
        {
            if (rowSelected == 1)
            {
                launchClientWebBrowser(self, veteranGetEventUrl(event));
                checkForVeteranRewards(self);
            }
            if (rowSelected == 2)
            {
                return SCRIPT_CONTINUE;
            }
            int waitForNextPrompt = 0;
            if (rowSelected == 3)
            {
                waitForNextPrompt = 24 * 60 * 60;
            }
            if (rowSelected == 4)
            {
                waitForNextPrompt = 7 * 24 * 60 * 60;
            }
            int gameTime = getGameTime();
            int nextPromptTime = gameTime + waitForNextPrompt;
            setObjVar(self, "veteran_rewards.nextPromptTime", nextPromptTime);
        }
        return SCRIPT_CONTINUE;
    }
    public void chooseRewardForEvent(obj_id self, String event) throws InterruptedException
    {
        String[] rewardChoices = veteranGetRewardChoicesDescriptions(self, event);
        String[] rewardChoicesTags = veteranGetRewardChoicesTags(self, event);
        utils.setScriptVar(self, "veteran_rewards.event", event);
        utils.setScriptVar(self, "veteran_rewards.rewardTags", rewardChoicesTags);
        if (rewardChoices.length == 0)
        {
        }
        else if (rewardChoices.length == 1)
        {
            veteranClaimReward(self, event, rewardChoicesTags[0]);
        }
        else 
        {
            String[] choicesWithLaunchWeb = new String[rewardChoices.length + 1];
            choicesWithLaunchWeb[0] = "@veteran:launch_web_browser_for_event";
            for (int i = 0; i < rewardChoices.length; ++i)
            {
                choicesWithLaunchWeb[i + 1] = rewardChoices[i];
            }
            int pid = sui.listbox(self, self, veteranGetEventDescription(event), sui.OK_CANCEL, sui.DEFAULT_TITLE, choicesWithLaunchWeb, "rewardSelectHandler", true, true);
            if (pid < 0)
            {
                debugSpeakMsg(self, "Could not create sui reward list for player, error = " + pid);
            }
        }
    }
    public int rewardSelectHandler(obj_id self, dictionary params) throws InterruptedException
    {
        debugSpeakMsg(self, "In rewardSelectHandler message handler");
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        int rowSelected = sui.getListboxSelectedRow(params);
        if (bp == sui.BP_CANCEL || rowSelected < 0)
        {
            debugSpeakMsg(self, "Cancel was pressed or no row was picked.");
            return SCRIPT_CONTINUE;
        }
        String event = utils.getStringScriptVar(self, "veteran_rewards.event");
        if (rowSelected == 0)
        {
            launchClientWebBrowser(self, veteranGetEventUrl(event));
            checkForVeteranRewards(self);
        }
        else 
        {
            String[] rewardChoicesTags = utils.getStringArrayScriptVar(self, "veteran_rewards.rewardTags");
            debugSpeakMsg(self, "Choice is " + event + ":" + rewardChoicesTags[rowSelected - 1]);
            veteranClaimReward(self, event, rewardChoicesTags[rowSelected - 1]);
        }
        return SCRIPT_CONTINUE;
    }
    public int veteranItemGrantSucceeded(obj_id self, dictionary params) throws InterruptedException
    {
        debugSpeakMsg(self, "In veteranItemGrantSucceeded message handler");
        sendSystemMessage(self, new string_id("veteran", "item_grant_succeeded"));
        checkForVeteranRewards(self);
        return SCRIPT_CONTINUE;
    }
    public int veteranItemGrantFailed(obj_id self, dictionary params) throws InterruptedException
    {
        debugSpeakMsg(self, "In veteranItemGrantFailed message handler");
        sendSystemMessage(self, new string_id("veteran", "item_grant_failed"));
        return SCRIPT_CONTINUE;
    }
}
