package script.systems.skills.performance;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.performance;

public class post_performance extends script.base_script
{
    public post_performance()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        detachScript(self, performance.POST_PERFORMANCE);
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, performance.VAR_PERFORM_OUTRO))
        {
            removeObjVar(self, performance.VAR_PERFORM_OUTRO);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnClearOutro(obj_id self, dictionary params) throws InterruptedException
    {
        performance.stopPlaying(self);
        performance.performanceMessageToSelf(self, null, performance.SID_MUSIC_STOP_SELF);
        performance.performanceMessageToBand(self, null, performance.SID_MUSIC_STOP_OTHER);
        performance.performanceMessageToBandListeners(self, null, performance.SID_MUSIC_STOP_OTHER);
        detachScript(self, performance.POST_PERFORMANCE);
        return SCRIPT_CONTINUE;
    }
    public int OnClearBandOutro(obj_id self, dictionary params) throws InterruptedException
    {
        int leader = params.getInt("leader");
        performance.stopPlaying(self);
        if (leader == 1)
        {
            performance.performanceMessageToSelf(self, null, performance.SID_MUSIC_STOP_BAND_SELF);
            performance.performanceMessageToBand(self, null, performance.SID_MUSIC_STOP_BAND_MEMBERS);
            performance.performanceMessageToBandListeners(self, null, performance.SID_MUSIC_STOP_BAND_OTHER);
        }
        detachScript(self, performance.POST_PERFORMANCE);
        return SCRIPT_CONTINUE;
    }
}
