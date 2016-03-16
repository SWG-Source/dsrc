package script.theme_park.tatooine.jabbaspawner.behavior;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.ai.ai_combat;
import script.ai.ai;

public class conversing extends script.base_script
{
    public conversing()
    {
    }
    public int OnEnteredCombat(obj_id self) throws InterruptedException
    {
        detachScript(self, "theme_park.tatooine.jabbaspawner.behavior.conversing");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        stop(self);
        setObjVar(self, "ai.defaultCalmBehavior", 1);
        setObjVar(self, "animsLeft", 1);
        messageTo(self, "playAnimation", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int playAnimation(obj_id self, dictionary params) throws InterruptedException
    {
        if (!ai_lib.isInCombat(self))
        {
            String action = "wave_hail";
            switch (rand(1, 10))
            {
                case 1:
                action = "check_wrist_device";
                break;
                case 2:
                action = "angry";
                break;
                case 3:
                action = "cover_mouth";
                break;
                case 4:
                action = "dismiss";
                break;
                case 5:
                action = "duck";
                break;
                case 6:
                action = "embarrassed";
                break;
                case 7:
                action = "explain";
                break;
                case 8:
                action = "get_hit_light";
                break;
                case 9:
                action = "laugh_titter";
                break;
                case 10:
                action = "nod_head_once";
                break;
            }
            doAnimationAction(self, action);
            messageTo(self, "playAnimation", null, rand(15, 25), false);
        }
        return SCRIPT_CONTINUE;
    }
}
