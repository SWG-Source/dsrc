package script.theme_park.poi.tatooine.behavior;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.ai.ai;

public class poi_victim extends script.base_script
{
    public poi_victim()
    {
    }
    public int OnEnteredCombat(obj_id self) throws InterruptedException
    {
        detachScript(self, "theme_park.tatooine.behavior.poi_victim");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        stop(self);
        setObjVar(self, "ai.defaultCalmBehavior", 1);
        messageTo(self, "playAnimation", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int playAnimation(obj_id self, dictionary params) throws InterruptedException
    {
        if (!ai_lib.isInCombat(self))
        {
            int anims = getIntObjVar(self, "animsLeft");
            String action = "wave_hail";
            int move = rand(1, 4);
            switch (move)
            {
                case 1:
                action = "shrug_shoulders";
                break;
                case 2:
                action = "shrug_hands";
                break;
                case 3:
                action = "slump_head";
                break;
                case 4:
                action = "implore";
                break;
            }
            doAnimationAction(self, action);
            if (anims < 10)
            {
                int speed = rand(5, 6);
                messageTo(self, "playAnimation", null, speed, false);
                anims = anims + 1;
                setObjVar(self, "animsLeft", anims);
            }
            else 
            {
                removeObjVar(self, "animsLeft");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int doFacing(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id face = params.getObjId("target");
        faceTo(self, face);
        faceTo(face, self);
        return SCRIPT_CONTINUE;
    }
}
