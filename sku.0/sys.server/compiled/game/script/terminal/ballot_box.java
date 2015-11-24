package script.terminal;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.sui;
import script.library.utils;
import script.library.player_structure;
import script.library.xp;

public class ballot_box extends script.base_script
{
    public ballot_box()
    {
    }
    public static final int BALLOT_TEXT_MAX_LENGTH = 120;
    public static final int BALLOT_TITLE_MAX_LENGTH = 32;
    public static final int BALLOT_ITEM_MAX_LENGTH = 64;
    public static final int BALLOT_ITEM_MAX_COUNT = 16;
    public static final string_id SID_CONFIGURE_BALLOT = new string_id("sui", "ballot_box_config");
    public static final string_id SID_ADD_OPTION = new string_id("sui", "ballot_box_add_option");
    public static final string_id SID_VIEW_RESULTS = new string_id("sui", "ballot_box_view_results");
    public static final string_id SID_VOTE = new string_id("sui", "ballot_box_vote");
    public static final string_id SID_VOTE_LOGGED_MSG = new string_id("sui", "ballot_box_vote_logged");
    public static final string_id SID_EDIT_BALLOT_TITLE = new string_id("sui", "ballot_box_edit_title");
    public static final string_id SID_EDIT_BALLOT_TEXT = new string_id("sui", "ballot_box_edit_text");
    public static final string_id SID_START_VOTE = new string_id("sui", "ballot_box_start_vote");
    public static final string_id SID_STOP_VOTE = new string_id("sui", "ballot_box_stop_vote");
    public static final string_id SID_MUST_STOP_VOTE_MSG = new string_id("sui", "ballot_box_must_stop");
    public static final string_id SID_START_VOTE_MSG = new string_id("sui", "ballot_box_start_msg");
    public static final string_id SID_STOP_VOTE_MSG = new string_id("sui", "ballot_box_stop_msg");
    public static final string_id SID_VIEW_BALLOT = new string_id("sui", "ballot_box_view_ballot");
    public static final string_id SID_CLEAR_LOG = new string_id("sui", "ballot_box_clear_log");
    public static final string_id SID_TEXT_TOO_LARGE = new string_id("sui", "ballot_text_too_large");
    public static final string_id SID_TITLE_TOO_LARGE = new string_id("sui", "ballot_title_too_large");
    public static final string_id SID_ITEM_TOO_LARGE = new string_id("sui", "ballot_item_too_large");
    public static final string_id SID_ITEMS_TOO_MANY = new string_id("sui", "ballot_items_too_many");
    public static final string_id SID_SECRET = new string_id("sui", "ballot_box_secret");
    public static final string_id SID_PUBLIC = new string_id("sui", "ballot_box_public");
    public static final string_id SID_EDIT_BALLOT = new string_id("sui", "ballot_box_edit");
    public static final string_id SID_REMOVE_OPTION = new string_id("sui", "ballot_box_remove_option");
    public static final string_id SID_SECRET_CONFIRM = new string_id("sui", "ballot_box_secret_confirm");
    public static final string_id SID_PUBLIC_CONFIRM = new string_id("sui", "ballot_box_public_confirm");
    public static final string_id SID_SAMPLE_ONE = new string_id("sui", "ballot_box_sample_one");
    public static final string_id SID_SAMPLE_TWO = new string_id("sui", "ballot_box_sample_two");
    public static final string_id SID_CLEAR_MSG = new string_id("sui", "ballot_box_clear_msg");
    public static final string_id SID_CONFIG_BALLOT_TEXT = new string_id("sui", "ballot_box_config_text");
    public static final string_id SID_CONFIG_BALLOT_TITLE = new string_id("sui", "ballot_box_config_title");
    public static final string_id SID_CONFIG_ADD_ITEM = new string_id("sui", "ballot_box_config_add_item");
    public static final string_id SID_RESULTS_TITLE = new string_id("sui", "ballot_box_results_title");
    public static final string_id SID_DEFAULT_BALLOT_TITLE = new string_id("sui", "ballot_box_sample_title");
    public static final string_id SID_DEFAULT_BALLOT_TEXT = new string_id("sui", "ballot_box_sample_text");
    public static final string_id SID_REMOVE_ITEM_TEXT = new string_id("sui", "ballot_box_remove_option_text");
    public static final string_id SID_DEFAULT_BALLOT_RESULTS = new string_id("sui", "ballot_box_default_results");
    public static final string_id SID_OPEN_STATE_TEXT = new string_id("sui", "ballot_box_open_text");
    public static final string_id SID_CLOSED_STATE_TEXT = new string_id("sui", "ballot_box_closed_text");
    public static final String[] DEFAULT_BALLOT_OPTIONS = 
    {
        "Sample Option One",
        "Sample Option Two"
    };
    public static final obj_id[] DEFAULT_PARTICIPANTS = new obj_id[0];
    public static final int[] DEFAULT_VOTE_TALLY = new int[0];
    public static final string_id SID_CHAT_BAND_TEXT = new string_id("sui", "ballot_box_band");
    public static final string_id SID_CHAT_TITLE = new string_id("sui", "ballot_box_chat_title");
    public static final String[] EDIT_OPTIONS = 
    {
        "@sui:ballot_box_edit_title",
        "@sui:ballot_box_edit_text",
        "@sui:ballot_box_add_option",
        "@sui:ballot_box_remove_option",
        "@sui:ballot_box_start_vote",
        "@sui:ballot_box_secret"
    };
    public static final String VAR_BALLOT_TEXT = "var_ballot_text";
    public static final String VAR_BALLOT_TITLE = "var_ballot_title";
    public static final String VAR_BALLOT_STATE_TEXT = "var_ballot_state";
    public static final String VAR_BALLOT_RESULTS = "var_ballot_results";
    public static final String VAR_BALLOT_OPTIONS = "var_ballot_options";
    public static final String VAR_EDIT_OPTIONS = "var_edit_options";
    public static final String VAR_PARTICIPANTS = "var_ballot_participants";
    public static final String VAR_VOTE_TALLY = "var_vote_tally";
    public static final String VAR_TOTAL_VOTES = "var_total_votes";
    public static final String VAR_LOG_COUNT = "var_ballot_log_count";
    public static final String VAR_BALLOT_BOX_OWNER = "var_ballot_box_owner";
    public static final String VAR_IS_SECRET_MSG = "var_secret_msg";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setObjVar(self, "ballot_box_id", getCurrentSceneName());
        setObjVar(self, VAR_BALLOT_TEXT, getString(SID_DEFAULT_BALLOT_TEXT));
        setObjVar(self, VAR_BALLOT_TITLE, getString(SID_DEFAULT_BALLOT_TITLE));
        setObjVar(self, VAR_BALLOT_STATE_TEXT, getString(SID_CLOSED_STATE_TEXT));
        setObjVar(self, VAR_BALLOT_RESULTS, getString(SID_DEFAULT_BALLOT_RESULTS));
        setObjVar(self, VAR_TOTAL_VOTES, 0);
        setObjVar(self, VAR_BALLOT_OPTIONS, DEFAULT_BALLOT_OPTIONS);
        setObjVar(self, VAR_EDIT_OPTIONS, EDIT_OPTIONS);
        setObjVar(self, VAR_IS_SECRET_MSG, false);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        String stateStr = getStringObjVar(self, VAR_BALLOT_STATE_TEXT);
        obj_id structure = getTopMostContainer(self);
        if (structure != null && player_structure.isAdmin(structure, player))
        {
            int menu = mi.addRootMenu(menu_info_types.CREATE_BALLOT, SID_CONFIGURE_BALLOT);
            if (stateStr.equals(getString(SID_OPEN_STATE_TEXT)))
            {
                mi.addSubMenu(menu, menu_info_types.DICE_COUNT_FOUR, SID_STOP_VOTE);
            }
            else 
            {
                setObjVar(self, VAR_BALLOT_BOX_OWNER, player);
                mi.addSubMenu(menu, menu_info_types.DICE_EIGHT_FACE, SID_CLEAR_LOG);
                mi.addSubMenu(menu, menu_info_types.DICE_FIVE_FACE, SID_EDIT_BALLOT);
                mi.addSubMenu(menu, menu_info_types.DICE_FOUR_FACE, SID_VIEW_BALLOT);
                mi.addSubMenu(menu, menu_info_types.DICE_COUNT_THREE, SID_START_VOTE);
            }
        }
        obj_id[] participants = getObjIdArrayObjVar(self, VAR_PARTICIPANTS);
        if (!utils.isObjIdInArray(participants, player) && stateStr.equals(getString(SID_OPEN_STATE_TEXT)))
        {
            mi.addRootMenu(menu_info_types.VOTE, SID_VOTE);
            mi.addRootMenu(menu_info_types.DICE_THREE_FACE, SID_VIEW_BALLOT);
        }
        else if (stateStr.equals(getString(SID_OPEN_STATE_TEXT)))
        {
            mi.addRootMenu(menu_info_types.DICE_THREE_FACE, SID_VIEW_BALLOT);
        }
        else 
        {
            mi.addRootMenu(menu_info_types.DICE_TWO_FACE, SID_VIEW_RESULTS);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.DICE_THREE_FACE)
        {
            viewBallot(player, self);
        }
        else if (item == menu_info_types.DICE_COUNT_FOUR)
        {
            stopVote(player, self);
        }
        else if (item == menu_info_types.DICE_FOUR_FACE)
        {
            ownerViewBallot(player, self);
        }
        else if (item == menu_info_types.DICE_FIVE_FACE)
        {
            editBallot(player, self);
        }
        else if (item == menu_info_types.DICE_EIGHT_FACE)
        {
            removeLogResults(player, self, true);
        }
        else if (item == menu_info_types.DICE_COUNT_THREE)
        {
            startVote(player, self);
        }
        else if (item == menu_info_types.VOTE)
        {
            vote(player, self);
        }
        else if (item == menu_info_types.ITEM_USE || item == menu_info_types.DICE_TWO_FACE)
        {
            viewResults(player, self);
        }
        return SCRIPT_CONTINUE;
    }
    public void editBallot(obj_id player, obj_id terminal) throws InterruptedException
    {
        if ((getStringObjVar(terminal, VAR_BALLOT_STATE_TEXT)).equals(getString(SID_OPEN_STATE_TEXT)))
        {
            sendSystemMessage(player, SID_MUST_STOP_VOTE_MSG);
            return;
        }
        String ballot_text = getBallotViewData(terminal);
        String[] options = getStringArrayObjVar(terminal, VAR_EDIT_OPTIONS);
        sui.listbox(terminal, player, ballot_text, sui.OK_CANCEL, getString(SID_EDIT_BALLOT), options, "configureBallot", true, true);
    }
    public void makeResultsSecret(obj_id owner, obj_id terminal) throws InterruptedException
    {
        setObjVar(terminal, VAR_IS_SECRET_MSG, true);
        sendSystemMessage(owner, SID_SECRET_CONFIRM);
        String[] options = getStringArrayObjVar(terminal, VAR_EDIT_OPTIONS);
        if (options != null && options.length > 0)
        {
            options[5] = getString(SID_PUBLIC);
            setObjVar(terminal, VAR_EDIT_OPTIONS, options);
        }
        editBallot(owner, terminal);
    }
    public void makeResultsPublic(obj_id owner, obj_id terminal) throws InterruptedException
    {
        setObjVar(terminal, VAR_IS_SECRET_MSG, false);
        sendSystemMessage(owner, SID_PUBLIC_CONFIRM);
        String[] options = getStringArrayObjVar(terminal, VAR_EDIT_OPTIONS);
        if (options != null && options.length > 0)
        {
            options[5] = getString(SID_SECRET);
            setObjVar(terminal, VAR_EDIT_OPTIONS, options);
        }
        editBallot(owner, terminal);
    }
    public void removeSelectionItem(obj_id owner, obj_id terminal) throws InterruptedException
    {
        String title = getStringObjVar(terminal, VAR_BALLOT_TITLE);
        String text = getString(SID_REMOVE_ITEM_TEXT);
        String[] options = getStringArrayObjVar(terminal, VAR_BALLOT_OPTIONS);
        sui.listbox(terminal, owner, text, sui.OK_CANCEL, title, options, "removeItem");
    }
    public void addSelectionItem(obj_id owner, obj_id terminal) throws InterruptedException
    {
        if ((getStringObjVar(terminal, VAR_BALLOT_STATE_TEXT)).equals(getString(SID_OPEN_STATE_TEXT)))
        {
            sendSystemMessage(owner, SID_MUST_STOP_VOTE_MSG);
            return;
        }
        String[] items = getStringArrayObjVar(terminal, VAR_BALLOT_OPTIONS);
        if (items == null)
        {
            String[] options = new String[0];
            setObjVar(terminal, VAR_BALLOT_OPTIONS, DEFAULT_BALLOT_OPTIONS);
        }
        String ballot_text = getString(SID_CONFIG_ADD_ITEM);
        int pid = sui.inputbox(terminal, owner, ballot_text, sui.OK_CANCEL, getString(SID_ADD_OPTION), sui.INPUT_NORMAL, null, "addItem");
    }
    public void ownerViewBallot(obj_id owner, obj_id terminal) throws InterruptedException
    {
        String title = getStringObjVar(terminal, VAR_BALLOT_TITLE);
        String text = getStringObjVar(terminal, VAR_BALLOT_TEXT);
        String[] options = getStringArrayObjVar(terminal, VAR_BALLOT_OPTIONS);
        sui.listbox(terminal, owner, text, sui.OK_ONLY, title, options, "no_handler");
        return;
    }
    public void viewBallot(obj_id owner, obj_id terminal) throws InterruptedException
    {
        String ballotData = getBallotViewData(terminal);
        sui.msgbox(terminal, owner, ballotData, sui.OK_ONLY, getString(SID_VIEW_BALLOT), "noHandlerNeeded");
        return;
    }
    public String getBallotViewData(obj_id terminal) throws InterruptedException
    {
        String title = getStringObjVar(terminal, VAR_BALLOT_TITLE);
        String issue = getStringObjVar(terminal, VAR_BALLOT_TEXT);
        String[] items = getStringArrayObjVar(terminal, VAR_BALLOT_OPTIONS);
        String resultSet = new String();
        resultSet += "Title: " + getStringObjVar(terminal, VAR_BALLOT_TITLE) + "\n";
        resultSet += "Issue: " + issue + "\n\n";
        resultSet += "Options: " + "\n";
        if (items != null)
        {
            for (int i = 0; i < items.length; ++i)
            {
                int count = i + 1;
                resultSet += count + ". " + items[i] + "\n";
            }
        }
        return resultSet;
    }
    public void editBallotTitle(obj_id owner, obj_id terminal) throws InterruptedException
    {
        if ((getStringObjVar(terminal, VAR_BALLOT_STATE_TEXT)).equals(getString(SID_OPEN_STATE_TEXT)))
        {
            sendSystemMessage(owner, SID_MUST_STOP_VOTE_MSG);
            return;
        }
        String ballot_text = getString(SID_CONFIG_BALLOT_TITLE);
        int pid = sui.inputbox(terminal, owner, ballot_text, sui.OK_CANCEL, getString(SID_EDIT_BALLOT_TITLE), sui.INPUT_NORMAL, null, "editTitle");
    }
    public void editBallotText(obj_id owner, obj_id terminal) throws InterruptedException
    {
        if ((getStringObjVar(terminal, VAR_BALLOT_STATE_TEXT)).equals(getString(SID_OPEN_STATE_TEXT)))
        {
            sendSystemMessage(owner, SID_MUST_STOP_VOTE_MSG);
            return;
        }
        String ballot_text = getString(SID_CONFIG_BALLOT_TEXT);
        int pid = sui.inputbox(terminal, owner, ballot_text, sui.OK_CANCEL, getString(SID_EDIT_BALLOT_TEXT), sui.INPUT_NORMAL, null, "editText");
    }
    public void startVote(obj_id player, obj_id terminal) throws InterruptedException
    {
        setObjVar(terminal, VAR_BALLOT_STATE_TEXT, getString(SID_OPEN_STATE_TEXT));
        setObjVar(terminal, VAR_TOTAL_VOTES, 0);
        sendSystemMessage(player, SID_START_VOTE_MSG);
        String[] items = getStringArrayObjVar(terminal, VAR_BALLOT_OPTIONS);
        int[] voteCounts = new int[items.length];
        for (int i = 0; i < items.length; ++i)
        {
            voteCounts[i] = 0;
        }
        if (voteCounts.length > 0)
        {
            setObjVar(terminal, VAR_VOTE_TALLY, voteCounts);
        }
    }
    public void stopVote(obj_id player, obj_id terminal) throws InterruptedException
    {
        setObjVar(terminal, VAR_BALLOT_STATE_TEXT, getString(SID_CLOSED_STATE_TEXT));
        sendSystemMessage(player, SID_STOP_VOTE_MSG);
        logResults(player, terminal);
    }
    public void logResults(obj_id player, obj_id terminal) throws InterruptedException
    {
        removeLogResults(player, terminal, false);
        String issue = getStringObjVar(terminal, VAR_BALLOT_TEXT);
        String[] items = getStringArrayObjVar(terminal, VAR_BALLOT_OPTIONS);
        int[] votes = getIntArrayObjVar(terminal, VAR_VOTE_TALLY);
        String resultSet = getStringObjVar(terminal, VAR_BALLOT_RESULTS);
        if (resultSet.equals(getString(SID_DEFAULT_BALLOT_RESULTS)))
        {
            resultSet = new String();
        }
        resultSet += "Title: " + getStringObjVar(terminal, VAR_BALLOT_TITLE) + "\n";
        resultSet += "Issue: " + issue + "\n\n";
        resultSet += "Options: " + "\n";
        int totalVotes = getIntObjVar(terminal, VAR_TOTAL_VOTES);
        float percentValue = 0.0f;
        for (int i = 0; i < items.length; ++i)
        {
            if (totalVotes != 0)
            {
                if (votes[i] == totalVotes)
                {
                    percentValue = 100;
                }
                else 
                {
                    percentValue = (float)100 * ((float)votes[i] / (float)totalVotes);
                }
            }
            int count = i + 1;
            if (votes[i] == 1)
            {
                resultSet += count + ". " + items[i] + "\n" + votes[i] + " vote = " + percentValue + "%\n\n";
            }
            else 
            {
                resultSet += count + ". " + items[i] + "\n" + votes[i] + " votes = " + percentValue + "%\n\n";
            }
        }
        resultSet += "\n\n";
        setObjVar(terminal, VAR_BALLOT_RESULTS, resultSet);
        dictionary params = new dictionary();
        params.put("owner", player);
        messageTo(terminal, "sendPersistantResultsMessage", params, 0, true);
        setObjVar(terminal, VAR_BALLOT_TEXT, getString(SID_DEFAULT_BALLOT_TEXT));
        setObjVar(terminal, VAR_BALLOT_TITLE, getString(SID_DEFAULT_BALLOT_TITLE));
        setObjVar(terminal, VAR_BALLOT_OPTIONS, DEFAULT_BALLOT_OPTIONS);
        setObjVar(terminal, VAR_PARTICIPANTS, DEFAULT_PARTICIPANTS);
        setObjVar(terminal, VAR_IS_SECRET_MSG, false);
    }
    public void removeLogResults(obj_id owner, obj_id terminal, boolean displayMessage) throws InterruptedException
    {
        setObjVar(terminal, VAR_BALLOT_RESULTS, getString(SID_DEFAULT_BALLOT_RESULTS));
        if (displayMessage)
        {
            sendSystemMessage(owner, SID_CLEAR_MSG);
        }
    }
    public void vote(obj_id player, obj_id terminal) throws InterruptedException
    {
        xp.grantCraftingXpChance(terminal, player, 40);
        String title = getStringObjVar(terminal, VAR_BALLOT_TITLE);
        String text = getStringObjVar(terminal, VAR_BALLOT_TEXT);
        String[] options = getStringArrayObjVar(terminal, VAR_BALLOT_OPTIONS);
        sui.listbox(terminal, player, text, sui.OK_CANCEL, title, options, "voteTracker");
    }
    public void viewResults(obj_id player, obj_id terminal) throws InterruptedException
    {
        String resultData = new String("error with data");
        if (!getBooleanObjVar(terminal, VAR_IS_SECRET_MSG))
        {
            resultData = getStringObjVar(terminal, VAR_BALLOT_RESULTS);
        }
        else 
        {
            resultData = getString(SID_DEFAULT_BALLOT_RESULTS);
        }
        sui.msgbox(terminal, player, resultData, sui.OK_ONLY, getString(SID_RESULTS_TITLE), "noHandlerNeeded");
    }
    public int configureBallot(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        String button = sui.getButtonPressed(params);
        if (!button.equals(sui.OK))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        int item = sui.getListboxSelectedRow(params);
        String buttonPressed = sui.getButtonPressed(params);
        String[] selectionOptions = getStringArrayObjVar(self, VAR_EDIT_OPTIONS);
        int optionCount = selectionOptions.length;
        if (item == 0)
        {
            editBallotTitle(player, self);
        }
        else if (item == 1)
        {
            editBallotText(player, self);
        }
        else if (item == 2)
        {
            addSelectionItem(player, self);
        }
        else if (item == 3)
        {
            removeSelectionItem(player, self);
        }
        else if (item == 4)
        {
            startVote(player, self);
        }
        else if (item == 5)
        {
            makeResultsSecret(player, self);
        }
        else if (item == 6)
        {
            makeResultsPublic(player, self);
        }
        return SCRIPT_CONTINUE;
    }
    public int editText(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        String ballotText = sui.getInputBoxText(params);
        String buttonPressed = sui.getButtonPressed(params);
        if (ballotText.length() > BALLOT_TEXT_MAX_LENGTH)
        {
            obj_id owner = getObjIdObjVar(self, VAR_BALLOT_BOX_OWNER);
            sendSystemMessage(owner, SID_TEXT_TOO_LARGE);
            return SCRIPT_CONTINUE;
        }
        if (ballotText != null && ballotText.length() != 0 && buttonPressed.equals(sui.OK))
        {
            setObjVar(self, VAR_BALLOT_TEXT, ballotText);
        }
        obj_id player = sui.getPlayerId(params);
        editBallot(player, self);
        return SCRIPT_CONTINUE;
    }
    public int editTitle(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        String ballotTitle = sui.getInputBoxText(params);
        String buttonPressed = sui.getButtonPressed(params);
        if (ballotTitle.length() > BALLOT_TITLE_MAX_LENGTH)
        {
            obj_id owner = getObjIdObjVar(self, VAR_BALLOT_BOX_OWNER);
            sendSystemMessage(owner, SID_TITLE_TOO_LARGE);
            return SCRIPT_CONTINUE;
        }
        if (ballotTitle != null && ballotTitle.length() != 0 && buttonPressed.equals(sui.OK))
        {
            setObjVar(self, VAR_BALLOT_TITLE, ballotTitle);
        }
        obj_id player = sui.getPlayerId(params);
        editBallot(player, self);
        return SCRIPT_CONTINUE;
    }
    public int voteTracker(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        int listItem = sui.getListboxSelectedRow(params);
        if (listItem == -1)
        {
            return SCRIPT_CONTINUE;
        }
        String button = sui.getButtonPressed(params);
        if (!button.equals(sui.OK))
        {
            return SCRIPT_CONTINUE;
        }
        int[] voteCounts = getIntArrayObjVar(self, VAR_VOTE_TALLY);
        int value = voteCounts[listItem];
        voteCounts[listItem] = ++value;
        if (voteCounts.length > 0)
        {
            setObjVar(self, VAR_VOTE_TALLY, voteCounts);
        }
        Vector participants = getResizeableObjIdArrayObjVar(self, VAR_PARTICIPANTS);
        participants = utils.addElement(participants, sui.getPlayerId(params));
        if (participants.size() > 0)
        {
            setObjVar(self, VAR_PARTICIPANTS, participants);
        }
        int voteCount = getIntObjVar(self, VAR_TOTAL_VOTES);
        setObjVar(self, VAR_TOTAL_VOTES, ++voteCount);
        obj_id player = sui.getPlayerId(params);
        sendSystemMessage(player, SID_VOTE_LOGGED_MSG);
        return SCRIPT_CONTINUE;
    }
    public int addItem(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        String itemToAdd = sui.getInputBoxText(params);
        String buttonPressed = sui.getButtonPressed(params);
        if (itemToAdd.length() > BALLOT_ITEM_MAX_LENGTH)
        {
            obj_id owner = getObjIdObjVar(self, VAR_BALLOT_BOX_OWNER);
            sendSystemMessage(owner, SID_ITEM_TOO_LARGE);
            return SCRIPT_CONTINUE;
        }
        if (itemToAdd != null && itemToAdd.length() != 0 && buttonPressed.equals(sui.OK))
        {
            Vector options = getResizeableStringArrayObjVar(self, VAR_BALLOT_OPTIONS);
            if (((String)options.get(0)).equals(getString(SID_SAMPLE_ONE)))
            {
                options.clear();
            }
            if (options.size() > BALLOT_ITEM_MAX_COUNT)
            {
                obj_id owner = getObjIdObjVar(self, VAR_BALLOT_BOX_OWNER);
                sendSystemMessage(owner, SID_ITEMS_TOO_MANY);
                obj_id player = sui.getPlayerId(params);
                editBallot(player, self);
                return SCRIPT_CONTINUE;
            }
            options = utils.addElement(options, itemToAdd);
            if (options.size() > 0)
            {
                setObjVar(self, VAR_BALLOT_OPTIONS, options);
            }
            else 
            {
                setObjVar(self, VAR_BALLOT_OPTIONS, DEFAULT_BALLOT_OPTIONS);
            }
        }
        obj_id player = sui.getPlayerId(params);
        editBallot(player, self);
        return SCRIPT_CONTINUE;
    }
    public int sendPersistantResultsMessage(obj_id self, dictionary params) throws InterruptedException
    {
        String resultSet = getStringObjVar(self, VAR_BALLOT_RESULTS);
        if (!getBooleanObjVar(self, VAR_IS_SECRET_MSG))
        {
            obj_id[] participants = getObjIdArrayObjVar(self, VAR_PARTICIPANTS);
            for (int i = 0; i < participants.length; ++i)
            {
                chatSendPersistentMessage(participants[i], "@" + SID_CHAT_TITLE.toString(), resultSet, null);
            }
        }
        else 
        {
            obj_id owner = params.getObjId("owner");
            chatSendPersistentMessage(owner, "@" + SID_CHAT_TITLE.toString(), resultSet, null);
        }
        return SCRIPT_CONTINUE;
    }
    public int removeItem(obj_id self, dictionary params) throws InterruptedException
    {
        int listItem = sui.getListboxSelectedRow(params);
        obj_id player = sui.getPlayerId(params);
        if (listItem == -1)
        {
            editBallot(player, self);
            return SCRIPT_CONTINUE;
        }
        Vector items = getResizeableStringArrayObjVar(self, VAR_BALLOT_OPTIONS);
        items = utils.removeElementAt(items, listItem);
        if (items.size() == 0)
        {
            setObjVar(self, VAR_BALLOT_OPTIONS, DEFAULT_BALLOT_OPTIONS);
        }
        else 
        {
            setObjVar(self, VAR_BALLOT_OPTIONS, items);
        }
        editBallot(player, self);
        return SCRIPT_CONTINUE;
    }
}
