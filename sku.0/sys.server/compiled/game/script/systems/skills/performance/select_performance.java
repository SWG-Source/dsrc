package script.systems.skills.performance;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.performance;
import script.library.sui;

public class select_performance extends script.base_script
{
    public select_performance()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        detachScript(self, performance.PERFORMANCE_SELECT);
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, performance.VAR_AVAILABLE_PERFORMANCES))
        {
            removeObjVar(self, performance.VAR_AVAILABLE_PERFORMANCES);
        }
        return SCRIPT_CONTINUE;
    }
    public int msgSongSelected(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "performCommands::msgSongSelected");
        String[] available_music;
        if (hasObjVar(self, performance.VAR_AVAILABLE_PERFORMANCES))
        {
            available_music = getStringArrayObjVar(self, performance.VAR_AVAILABLE_PERFORMANCES);
            removeObjVar(self, performance.VAR_AVAILABLE_PERFORMANCES);
        }
        else 
        {
            performance.performanceMessageToSelf(self, null, performance.SID_MUSIC_LACK_SKILL_INSTRUMENT);
            detachScript(self, performance.PERFORMANCE_SELECT);
            return SCRIPT_CONTINUE;
        }
        detachScript(self, performance.PERFORMANCE_SELECT);
        String button = params.getString("buttonPressed");
        if (button.equals("Cancel") || button.equals("No"))
        {
            return SCRIPT_CONTINUE;
        }
        int row_selected = sui.getListboxSelectedRow(params);
        if (row_selected == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (row_selected >= available_music.length)
        {
            performance.performanceMessageToSelf(self, null, performance.SID_MUSIC_INVALID_SONG);
            return SCRIPT_CONTINUE;
        }
        String selected_music = available_music[row_selected];
        boolean band = getBooleanObjVar(self, performance.VAR_SELECT_MUSIC_BAND);
        boolean changeMusic = getBooleanObjVar(self, performance.VAR_SELECT_MUSIC_CHANGE);
        removeObjVar(self, performance.VAR_SELECT_MUSIC_BAND);
        removeObjVar(self, performance.VAR_SELECT_MUSIC_CHANGE);
        if (changeMusic)
        {
            if (band)
            {
                queueCommand(self, (2039359915), null, selected_music, COMMAND_PRIORITY_DEFAULT);
            }
            else 
            {
                queueCommand(self, (-1256059356), null, selected_music, COMMAND_PRIORITY_DEFAULT);
            }
        }
        else 
        {
            if (band)
            {
                queueCommand(self, (-12231596), null, selected_music, COMMAND_PRIORITY_DEFAULT);
            }
            else 
            {
                queueCommand(self, (-573445903), null, selected_music, COMMAND_PRIORITY_DEFAULT);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int msgDanceSelected(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "performCommands::msgDanceSelected");
        String[] available_dances;
        if (hasObjVar(self, performance.VAR_AVAILABLE_PERFORMANCES))
        {
            available_dances = getStringArrayObjVar(self, performance.VAR_AVAILABLE_PERFORMANCES);
            removeObjVar(self, performance.VAR_AVAILABLE_PERFORMANCES);
        }
        else 
        {
            performance.performanceMessageToSelf(self, null, performance.SID_DANCE_UNKNOWN_SELF);
            detachScript(self, performance.PERFORMANCE_SELECT);
            return SCRIPT_CONTINUE;
        }
        detachScript(self, performance.PERFORMANCE_SELECT);
        String button = params.getString("buttonPressed");
        if (button.equals("Cancel") || button.equals("No"))
        {
            return SCRIPT_CONTINUE;
        }
        int row_selected = sui.getListboxSelectedRow(params);
        if (row_selected == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (row_selected >= available_dances.length)
        {
            performance.performanceMessageToSelf(self, null, performance.SID_DANCE_UNKNOWN_SELF);
            return SCRIPT_CONTINUE;
        }
        String selected_dance = available_dances[row_selected];
        if (hasObjVar(self, performance.VAR_SELECT_DANCE_CHANGE))
        {
            removeObjVar(self, performance.VAR_SELECT_DANCE_CHANGE);
            queueCommand(self, (334376245), null, selected_dance, COMMAND_PRIORITY_DEFAULT);
        }
        else 
        {
            queueCommand(self, (2065550304), null, selected_dance, COMMAND_PRIORITY_DEFAULT);
        }
        return SCRIPT_CONTINUE;
    }
    public int msgJuggleSelected(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "performCommands::msgJuggleSelected");
        String[] available_juggles;
        if (hasObjVar(self, performance.VAR_AVAILABLE_PERFORMANCES))
        {
            available_juggles = getStringArrayObjVar(self, performance.VAR_AVAILABLE_PERFORMANCES);
            removeObjVar(self, performance.VAR_AVAILABLE_PERFORMANCES);
        }
        else 
        {
            performance.performanceMessageToSelf(self, null, performance.SID_JUGGLE_UNKNOWN_SELF);
            detachScript(self, performance.PERFORMANCE_SELECT);
            return SCRIPT_CONTINUE;
        }
        detachScript(self, performance.PERFORMANCE_SELECT);
        if (available_juggles == null || available_juggles.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        int row_selected = sui.getListboxSelectedRow(params);
        if (row_selected == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (row_selected >= available_juggles.length)
        {
            performance.performanceMessageToSelf(self, null, performance.SID_JUGGLE_UNKNOWN_SELF);
            return SCRIPT_CONTINUE;
        }
        String selected_juggle = available_juggles[row_selected];
        if (hasObjVar(self, performance.VAR_SELECT_JUGGLE_CHANGE))
        {
            removeObjVar(self, performance.VAR_SELECT_JUGGLE_CHANGE);
            queueCommand(self, (-3482002), null, selected_juggle, COMMAND_PRIORITY_DEFAULT);
        }
        else 
        {
            queueCommand(self, (1621157837), null, selected_juggle, COMMAND_PRIORITY_DEFAULT);
        }
        return SCRIPT_CONTINUE;
    }
}
