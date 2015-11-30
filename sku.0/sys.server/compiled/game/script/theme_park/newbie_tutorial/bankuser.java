package script.theme_park.newbie_tutorial;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class bankuser extends script.theme_park.newbie_tutorial.tutorial_base
{
    public bankuser()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleUseBank", null, rand(15, 30), false);
        return SCRIPT_CONTINUE;
    }
    public int handleUseBank(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, "newbie.trooperTalking"))
        {
            doAnimationAction(self, "manipulate_medium");
        }
        messageTo(self, "handleUseBank", null, rand(15, 30), false);
        return SCRIPT_CONTINUE;
    }
    public int handleStormtrooper(obj_id self, dictionary params) throws InterruptedException
    {
        setObjVar(self, "newbie.trooperTalking", true);
        obj_id trooper = params.getObjId("stormtrooper");
        faceTo(self, trooper);
        messageTo(self, "handleDenial", null, 2, false);
        return SCRIPT_CONTINUE;
    }
    public int handleDenial(obj_id self, dictionary params) throws InterruptedException
    {
        doAnimationAction(self, "standing_placate");
        return SCRIPT_CONTINUE;
    }
}
