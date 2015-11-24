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

public class tusken_stationary_poi extends script.base_script
{
    public tusken_stationary_poi()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        stop(self);
        setObjVar(self, "ai.defaultCalmBehavior", 1);
        messageTo(self, "faceCenter", null, 5, true);
        return SCRIPT_CONTINUE;
    }
    public int faceCenter(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id master = getObjIdObjVar(self, "poi.baseObject");
        obj_id center = getObjIdObjVar(master, "poi.stringList.center");
        if (center == null)
        {
            faceTo(self, master);
        }
        else 
        {
            faceTo(self, center);
        }
        messageTo(self, "playAnimation", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int playAnimation(obj_id self, dictionary params) throws InterruptedException
    {
        if (!ai_lib.isInCombat(self))
        {
            String action = "wave_hail";
            int move = rand(1, 12);
            switch (move)
            {
                case 1:
                action = "hands_above_head";
                break;
                case 2:
                action = "nod_head_multiple";
                break;
                case 3:
                action = "nod_head_once";
                break;
                case 4:
                action = "wave_hail";
                break;
                case 5:
                action = "point_left";
                break;
                case 6:
                action = "point_right";
                break;
                case 7:
                action = "point_forward";
                break;
                case 8:
                action = "point_up";
                break;
                case 9:
                action = "pound_fist_chest";
                break;
                case 10:
                action = "search";
                break;
                case 11:
                action = "slit_throat";
                break;
                case 12:
                action = "stamp_feet";
                break;
            }
            doAnimationAction(self, action);
            int speed = rand(4, 8);
            messageTo(self, "playAnimation", null, speed, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnEnteredCombat(obj_id self) throws InterruptedException
    {
        detachScript(self, "theme_park.poi.tatooine.behavior.tusken_stationary_poi");
        return SCRIPT_CONTINUE;
    }
}
