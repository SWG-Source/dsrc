package script.poi.factoryliberation;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.factions;
import script.library.poi;
import script.library.scenario;

public class prisoner extends script.base_script
{
    public prisoner()
    {
    }
    public static final String frustrationEmotes[] = 
    {
        "scratch",
        "yawn",
        "tantrum",
        "cough",
        "curse",
        "fear",
        "steam"
    };
    public static final String joyEmotes[] = 
    {
        "thank",
        "cheer",
        "applaud",
        "laugh",
        "softclap",
        "yes",
        "glow"
    };
    public int OnAttach(obj_id self) throws InterruptedException
    {
        ai_lib.setIgnoreCombat(self, true);
        setObjVar(self, "oldFaction", factions.getFaction(self));
        factions.setFaction(self, "Unattackable");
        return SCRIPT_CONTINUE;
    }
    public int OnLoiterWaiting(obj_id self, modifiable_float time) throws InterruptedException
    {
        stop(self);
        if (scenario.isComplete(poi.getBaseObject(self)))
        {
            messageTo(self, "emoteJoy", null, 4, false);
        }
        else 
        {
            messageTo(self, "emoteFrustration", null, 4, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int emoteFrustration(obj_id self, dictionary params) throws InterruptedException
    {
        int dosocial = rand(1, 4);
        if (dosocial == 1)
        {
            int whichsocial = rand(0, 6);
            queueCommand(self, (1780871594), null, frustrationEmotes[whichsocial], COMMAND_PRIORITY_DEFAULT);
        }
        messageTo(self, "resumeDefaultCalmBehavior", null, 3, false);
        return SCRIPT_CONTINUE;
    }
    public int emoteJoy(obj_id self, dictionary params) throws InterruptedException
    {
        int dosocial = rand(1, 4);
        if (dosocial == 1)
        {
            int whichsocial = rand(0, 6);
            queueCommand(self, (1780871594), null, joyEmotes[whichsocial], COMMAND_PRIORITY_DEFAULT);
        }
        messageTo(self, "resumeDefaultCalmBehavior", null, 3, false);
        return SCRIPT_CONTINUE;
    }
    public int celebrateFreedom(obj_id self, dictionary params) throws InterruptedException
    {
        stop(self);
        int whichsocial = rand(0, 6);
        queueCommand(self, (1780871594), null, joyEmotes[whichsocial], COMMAND_PRIORITY_DEFAULT);
        messageTo(self, "resumeDefaultCalmBehavior", null, 3, false);
        return SCRIPT_CONTINUE;
    }
}
