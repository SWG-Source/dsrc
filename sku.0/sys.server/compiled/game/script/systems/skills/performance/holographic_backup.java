package script.systems.skills.performance;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.performance;

public class holographic_backup extends script.base_script
{
    public holographic_backup()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        obj_id master = getMaster(self);
        if (isIdValid(master))
        {
            listenToMessage(master, "handleHologramPerformanceCommand");
        }
        createTriggerVolume("performanceRange", 64.0f, true);
        messageTo(self, "handleCleanup", null, 10800, false);
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeExited(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        if (!isMob(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (volumeName.equals("performanceRange") && breacher == getMaster(self))
        {
            messageTo(breacher, "handlePerformanceBreach", null, 1, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleCleanup(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int handleGroupInvitation(obj_id self, dictionary params) throws InterruptedException
    {
        queueCommand(self, (-1449236473), null, "", COMMAND_PRIORITY_DEFAULT);
        return SCRIPT_CONTINUE;
    }
    public int handleHologramPerformanceCommand(obj_id self, dictionary params) throws InterruptedException
    {
        int command = params.getInt("commandType");
        String songName = "";
        String danceName = "";
        switch (command)
        {
            case performance.HOLOGRAM_COMMAND_START_SONG:
            songName = params.getString("songName");
            performance.startMusic(self, songName);
            break;
            case performance.HOLOGRAM_COMMAND_CHANGE_SONG:
            songName = params.getString("songName");
            performance.changeMusic(self, songName);
            break;
            case performance.HOLOGRAM_COMMAND_STOP_SONG:
            performance.stopMusicNow(self);
            break;
            case performance.HOLOGRAM_COMMAND_START_DANCE:
            danceName = params.getString("danceName");
            performance.startDance(self, danceName);
            break;
            case performance.HOLOGRAM_COMMAND_CHANGE_DANCE:
            danceName = params.getString("danceName");
            performance.changeDance(self, danceName);
            break;
            case performance.HOLOGRAM_COMMAND_STOP_DANCE:
            performance.stopDance(self);
            break;
        }
        return SCRIPT_CONTINUE;
    }
}
