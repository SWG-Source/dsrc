package script.systems.crafting.droid.modules;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.callable;
import script.library.sui;
import script.library.performance;
import script.library.utils;
import script.library.pet_lib;
import script.library.ai_lib;

public class playback extends script.base_script
{
    public playback()
    {
    }
    public static final String MODULE_OBJVAR = "module_data.playback";
    public static final String SCRIPT_NAME = "systems.crafting.droid.modules.playback";
    public static final String STF = "pet/droid_modules";
    public static final String PERFORMANCE_DATATABLE = "datatables/performance/performance.iff";
    public static final String INSTRUMENT_DATATABLE = "datatables/tangible/instrument_datatable.iff";
    public static final String LIST_TITLE = "@" + STF + ":playback_list_title";
    public static final String LIST_PROMPT = "@" + STF + ":playback_list_prompt";
    public static final String LIST_TRACK = "@" + STF + ":playback_track";
    public static final String LIST_BLANK_TRACK = "@" + STF + ":playback_blank_track";
    public static final String DELETE_TITLE = "@" + STF + ":playback_delete_title";
    public static final String DELETE_PROMPT = "@" + STF + ":playback_delete_prompt";
    public static final String BTN_PLAY_RECORD = "@" + STF + ":playback_btn_play_record";
    public static final String BTN_DELETE = "@" + STF + ":playback_btn_delete";
    public static final string_id SID_MENU_PLAYBACK = new string_id(STF, "playback_menu_playback");
    public static final string_id SID_MENU_STOP_PLAYBACK = new string_id(STF, "playback_menu_stop_playback");
    public static final string_id SID_MSG_REC_START = new string_id(STF, "playback_msg_rec_start");
    public static final string_id SID_MSG_REC_TIMEOUT = new string_id(STF, "playback_msg_rec_timeout");
    public static final string_id SID_MSG_REC_BEGUN = new string_id(STF, "playback_msg_rec_begun");
    public static final string_id SID_MSG_REC_FAIL_TRACK_FULL = new string_id(STF, "playback_msg_rec_fail_track_full");
    public static final string_id SID_MSG_REC_FAIL_ALREADY_PLAYING = new string_id(STF, "playback_msg_rec_fail_already_playing");
    public static final string_id SID_MSG_REC_FAIL_NOT_FINISHED = new string_id(STF, "playback_msg_rec_fail_not_finished");
    public static final string_id SID_MSG_REC_FAIL_CORRUPTED = new string_id(STF, "playback_msg_rec_fail_corrupted");
    public static final string_id SID_MSG_REC_COMPLETE = new string_id(STF, "playback_msg_rec_complete");
    public static final string_id SID_MSG_DEL_TRACK_ALREADY_EMPTY = new string_id(STF, "playback_msg_del_track_already_empty");
    public static final string_id SID_MSG_DEL_TRACK_DELETED = new string_id(STF, "playback_msg_del_track_deleted");
    public static final string_id SID_MSG_PLAY_OUT_OF_POWER = new string_id(STF, "playback_msg_play_out_of_power");
    public static final int MENU_TYPE = menu_info_types.SERVER_MENU10;
    public static final int RECORD_TIMEOUT = 30;
    public static final int RECORD_TIME = 30;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        int modules = getIntObjVar(self, MODULE_OBJVAR + ".modules");
        if (modules <= 0)
        {
            detachScript(self, SCRIPT_NAME);
            return SCRIPT_CONTINUE;
        }
        obj_id pcd = callable.getCallableCD(self);
        if (isIdValid(pcd) && hasObjVar(pcd, MODULE_OBJVAR + ".tracks"))
        {
            int[] tracks = getIntArrayObjVar(pcd, MODULE_OBJVAR + ".tracks");
            if (tracks != null && tracks.length > 0)
            {
                setObjVar(self, MODULE_OBJVAR + ".tracks", tracks);
            }
        }
        else 
        {
            int[] tracks = new int[modules];
            for (int i = 0; i < modules; i++)
            {
                tracks[i] = 0;
            }
            if (tracks.length > 0)
            {
                setObjVar(self, MODULE_OBJVAR + ".tracks", tracks);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (isDead(self) || ai_lib.aiIsDead(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (!isMob(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (isDead(player) || isIncapacitated(player))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id master = getMaster(self);
        if (player != master)
        {
            return SCRIPT_CONTINUE;
        }
        if (hasSkill(player, "class_entertainer_phase1_novice"))
        {
            int modules = getIntObjVar(self, MODULE_OBJVAR + ".modules");
            if (modules < 1)
            {
                return SCRIPT_CONTINUE;
            }
            if (getPerformanceType(self) == 0)
            {
                mi.addRootMenu(MENU_TYPE, SID_MENU_PLAYBACK);
            }
            else 
            {
                mi.addRootMenu(MENU_TYPE, SID_MENU_STOP_PLAYBACK);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (isDead(self) || ai_lib.aiIsDead(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (!isMob(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (isDead(player) || isIncapacitated(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (item == MENU_TYPE)
        {
            if (getPerformanceType(self) == 0)
            {
                accessPlaybackModule(self, player);
            }
            else 
            {
                performance.stopMusicNow(self);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, MODULE_OBJVAR + ".modules"))
        {
            names[idx] = "playback_modules";
            int numModules = getIntObjVar(self, MODULE_OBJVAR + ".modules");
            attribs[idx] = " " + numModules;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleModuleAccess(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        int idx = sui.getListboxSelectedRow(params);
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        else if (bp == sui.BP_REVERT)
        {
            if (idx < 0)
            {
                return SCRIPT_CONTINUE;
            }
            confirmDeleteTrack(self, player, idx);
        }
        else 
        {
            if (idx < 0)
            {
                return SCRIPT_CONTINUE;
            }
            if (pet_lib.isLowOnPower(self))
            {
                sendSystemMessage(player, SID_MSG_PLAY_OUT_OF_POWER);
                return SCRIPT_CONTINUE;
            }
            int[] tracks = getIntArrayObjVar(self, MODULE_OBJVAR + ".tracks");
            if (tracks == null || tracks.length == 0)
            {
                return SCRIPT_CONTINUE;
            }
            if (idx >= tracks.length)
            {
                return SCRIPT_CONTINUE;
            }
            if (tracks[idx] == 0)
            {
                startRecording(self, player, idx);
            }
            else 
            {
                playTrack(self, player, tracks[idx]);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleRecordTimeout(obj_id self, dictionary params) throws InterruptedException
    {
        int recStatus = -1;
        if (hasObjVar(self, MODULE_OBJVAR + ".recordStatus"))
        {
            recStatus = getIntObjVar(self, MODULE_OBJVAR + ".recordStatus");
        }
        int session_id = params.getInt("session");
        int current_session = utils.getIntScriptVar(self, MODULE_OBJVAR + ".sessionID");
        if (current_session == 0)
        {
            return SCRIPT_CONTINUE;
        }
        if (session_id != current_session)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId("player");
        if (recStatus == 0)
        {
            sendSystemMessage(player, SID_MSG_REC_TIMEOUT);
            stopRecording(self);
            stopListeningToMessage(player, "handlePerformerStartPerforming");
        }
        return SCRIPT_CONTINUE;
    }
    public int handlePerformerStartPerforming(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id master = getMaster(self);
        obj_id player = params.getObjId("performer");
        if (master != player)
        {
            return SCRIPT_CONTINUE;
        }
        int type = getPerformanceType(player);
        if (type == 0)
        {
            return SCRIPT_CONTINUE;
        }
        sendSystemMessage(player, SID_MSG_REC_BEGUN);
        setObjVar(self, MODULE_OBJVAR + ".recordStatus", type);
        stopListeningToMessage(player, "handlePerformerStartPerforming");
        listenToMessage(player, "handlePerformerStopPerforming");
        dictionary newParams = new dictionary();
        newParams.put("player", player);
        messageTo(self, "handleRecordFinished", newParams, RECORD_TIME, false);
        return SCRIPT_CONTINUE;
    }
    public int handlePerformerStopPerforming(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id master = getMaster(self);
        obj_id player = params.getObjId("performer");
        if (master != player)
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, MODULE_OBJVAR + ".recording"))
        {
            stopListeningToMessage(player, "handlePerformerStopPerforming");
            return SCRIPT_CONTINUE;
        }
        sendSystemMessage(player, SID_MSG_REC_FAIL_NOT_FINISHED);
        stopRecording(self);
        stopListeningToMessage(player, "handlePerformerStopPerforming");
        return SCRIPT_CONTINUE;
    }
    public int handleRecordFinished(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        if (!hasObjVar(self, MODULE_OBJVAR + ".recording"))
        {
            stopListeningToMessage(player, "handlePerformerStopPerforming");
            return SCRIPT_CONTINUE;
        }
        int idx = getIntObjVar(self, MODULE_OBJVAR + ".recording");
        int recType = getIntObjVar(self, MODULE_OBJVAR + ".recordStatus");
        int[] tracks = getIntArrayObjVar(self, MODULE_OBJVAR + ".tracks");
        int playType = getPerformanceType(player);
        if (recType < 1)
        {
            sendSystemMessage(player, SID_MSG_REC_FAIL_CORRUPTED);
        }
        else if (recType != playType)
        {
            sendSystemMessage(player, SID_MSG_REC_FAIL_CORRUPTED);
        }
        else if (idx >= tracks.length)
        {
            sendSystemMessage(player, SID_MSG_REC_FAIL_CORRUPTED);
        }
        else 
        {
            tracks[idx] = recType;
            obj_id pcd = callable.getCallableCD(self);
            if (tracks.length > 0)
            {
                setObjVar(pcd, MODULE_OBJVAR + ".tracks", tracks);
                setObjVar(self, MODULE_OBJVAR + ".tracks", tracks);
            }
            sendSystemMessage(player, SID_MSG_REC_COMPLETE);
        }
        stopRecording(self);
        stopListeningToMessage(player, "handlePerformerStopPerforming");
        return SCRIPT_CONTINUE;
    }
    public int handleDeleteConfirm(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        int idx = utils.getIntScriptVar(self, MODULE_OBJVAR + ".delete_track");
        int[] tracks = getIntArrayObjVar(self, MODULE_OBJVAR + ".tracks");
        if (tracks == null || tracks.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        tracks[idx] = 0;
        sendSystemMessage(player, SID_MSG_DEL_TRACK_DELETED);
        obj_id pcd = callable.getCallableCD(self);
        if (tracks.length > 0)
        {
            setObjVar(pcd, MODULE_OBJVAR + ".tracks", tracks);
            setObjVar(self, MODULE_OBJVAR + ".tracks", tracks);
        }
        utils.removeScriptVar(self, MODULE_OBJVAR + ".delete_track");
        return SCRIPT_CONTINUE;
    }
    public void accessPlaybackModule(obj_id self, obj_id player) throws InterruptedException
    {
        int modules = getIntObjVar(self, MODULE_OBJVAR + ".modules");
        int[] tracks = getIntArrayObjVar(self, MODULE_OBJVAR + ".tracks");
        if (tracks == null || tracks.length == 0)
        {
            return;
        }
        String[] trackNames = new String[tracks.length];
        for (int i = 0; i < tracks.length; i++)
        {
            if (tracks[i] == 0)
            {
                trackNames[i] = LIST_TRACK;
                trackNames[i] += " " + (i + 1) + ": ";
                trackNames[i] += LIST_BLANK_TRACK;
            }
            else 
            {
                trackNames[i] = LIST_TRACK;
                trackNames[i] += " " + (i + 1) + ": ";
                trackNames[i] += parseTrackName(tracks[i]);
            }
        }
        int pid = sui.listbox(self, player, LIST_PROMPT, sui.OK_CANCEL_ALL, LIST_TITLE, trackNames, "handleModuleAccess", false, false);
        if (pid > -1)
        {
            setSUIProperty(pid, sui.LISTBOX_BTN_OK, sui.PROP_TEXT, BTN_PLAY_RECORD);
            setSUIProperty(pid, sui.LISTBOX_BTN_OTHER, sui.PROP_TEXT, BTN_DELETE);
            showSUIPage(pid);
        }
    }
    public void startRecording(obj_id self, obj_id player, int idx) throws InterruptedException
    {
        int[] tracks = getIntArrayObjVar(self, MODULE_OBJVAR + ".tracks");
        if (tracks[idx] != 0)
        {
            sendSystemMessage(player, SID_MSG_REC_FAIL_TRACK_FULL);
            return;
        }
        if (getPerformanceType(player) != 0)
        {
            sendSystemMessage(player, SID_MSG_REC_FAIL_ALREADY_PLAYING);
            return;
        }
        int session_id = rand(10000, 99999);
        setObjVar(self, MODULE_OBJVAR + ".recording", idx);
        setObjVar(self, MODULE_OBJVAR + ".recordStatus", 0);
        utils.setScriptVar(self, MODULE_OBJVAR + ".sessionID", session_id);
        sendSystemMessage(player, SID_MSG_REC_START);
        dictionary params = new dictionary();
        params.put("player", player);
        params.put("session", session_id);
        messageTo(self, "handleRecordTimeout", params, RECORD_TIMEOUT, false);
        listenToMessage(player, "handlePerformerStartPerforming");
    }
    public void stopRecording(obj_id self) throws InterruptedException
    {
        removeObjVar(self, MODULE_OBJVAR + ".recording");
        removeObjVar(self, MODULE_OBJVAR + ".recordStatus");
        utils.removeScriptVar(self, MODULE_OBJVAR + ".sessionID");
    }
    public void confirmDeleteTrack(obj_id self, obj_id player, int idx) throws InterruptedException
    {
        int[] tracks = getIntArrayObjVar(self, MODULE_OBJVAR + ".tracks");
        if (tracks[idx] == 0)
        {
            sendSystemMessage(player, SID_MSG_DEL_TRACK_ALREADY_EMPTY);
            return;
        }
        String prompt = DELETE_PROMPT;
        prompt += "\n\n";
        prompt += parseTrackName(tracks[idx]);
        int pid = sui.msgbox(self, player, prompt, sui.YES_NO, DELETE_TITLE, "handleDeleteConfirm");
        if (pid > -1)
        {
            utils.setScriptVar(self, MODULE_OBJVAR + ".delete_track", idx);
        }
    }
    public void playTrack(obj_id self, obj_id player, int idx) throws InterruptedException
    {
        if (idx == 0)
        {
            return;
        }
        obj_id master = getMaster(self);
        if (master != player)
        {
            return;
        }
        dictionary data = getPerformanceData(idx);
        String song = data.getString("song");
        int instrument = data.getInt("instrument");
        performance.startMusic(self, song, instrument);
        setPerformanceListenTarget(player, self);
    }
    public dictionary getPerformanceData(int index) throws InterruptedException
    {
        index--;
        if (index > dataTableGetNumRows(PERFORMANCE_DATATABLE))
        {
            return null;
        }
        dictionary row = dataTableGetRow(PERFORMANCE_DATATABLE, index);
        String songName = row.getString("performanceName");
        int instrument = row.getInt("instrumentAudioId");
        if (songName == null || songName.equals("") || instrument == 0)
        {
            return null;
        }
        dictionary ret = new dictionary();
        ret.put("song", songName);
        ret.put("instrument", instrument);
        return ret;
    }
    public String parseTrackName(int index) throws InterruptedException
    {
        dictionary data = getPerformanceData(index);
        String song = data.getString("song");
        int instrument = data.getInt("instrument");
        int numRows = dataTableGetNumRows(INSTRUMENT_DATATABLE);
        String instrumentName = "";
        for (int i = 0; i < numRows; i++)
        {
            int audioId = dataTableGetInt(INSTRUMENT_DATATABLE, i, "instrumentAudioId");
            if (audioId == instrument)
            {
                instrumentName = dataTableGetString(INSTRUMENT_DATATABLE, i, "serverTemplateName");
                break;
            }
        }
        if (instrumentName.equals(""))
        {
            return null;
        }
        instrumentName = localize(getNameFromTemplate(instrumentName));
        java.util.StringTokenizer st = new java.util.StringTokenizer(instrumentName);
        if (st.countTokens() > 1)
        {
            String indefiniteArticle = st.nextToken();
            int articleLength = indefiniteArticle.length();
            if (articleLength < 3)
            {
                instrumentName = "";
            }
            else 
            {
                instrumentName = indefiniteArticle + " ";
            }
            while (st.hasMoreTokens())
            {
                instrumentName += st.nextToken() + " ";
            }
        }
        else 
        {
            instrumentName += " ";
        }
        if (Character.isDigit(song.charAt(song.length() - 1)))
        {
            song = song.substring(0, (song.length() - 1)) + " " + song.substring(song.length() - 1);
        }
        song = (song.substring(0, 1)).toUpperCase() + (song.substring(1)).toLowerCase();
        instrumentName = (instrumentName.substring(0, 1)).toUpperCase() + (instrumentName.substring(1)).toLowerCase();
        String ret = instrumentName + "- " + song;
        return ret;
    }
}
