package script.theme_park.dungeon.mustafar_trials.volcano_battlefield;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;

public class yt_controller extends script.base_script
{
    public yt_controller()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int performLanding(obj_id self, dictionary params) throws InterruptedException
    {
        queueCommand(self, (-1465754503), self, "", COMMAND_PRIORITY_FRONT);
        setPosture(self, POSTURE_UPRIGHT);
        return SCRIPT_CONTINUE;
    }
    public int performTakeoff(obj_id self, dictionary params) throws InterruptedException
    {
        queueCommand(self, (-1114832209), self, "", COMMAND_PRIORITY_FRONT);
        setPosture(self, POSTURE_PRONE);
        return SCRIPT_CONTINUE;
    }
    public int selfDestruct(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
