package script.npe;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.utils;

public class npe_ranged_spawning extends script.base_script
{
    public npe_ranged_spawning()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        location myLoc = new location();
        myLoc.cell = self;
        float[] locations = 
        {
            -31f,
            -35f,
            -33f,
            -37f,
            -36f,
            -38f,
            -39f,
            -39f,
            -41f,
            -39f,
            -44f,
            -38f,
            -47f,
            -36f,
            -50f,
            -31f,
            -50f,
            -26f,
            -47f,
            -22f,
            -43f,
            -19f
        };
        boolean setting = utils.checkConfigFlag("ScriptFlags", "npeSequencersActive");
        if (setting == true)
        {
            for (int i = 0; i <= 21; i = i + 2)
            {
                myLoc.x = locations[i];
                myLoc.z = locations[i + 1];
                create.object("npe_training_droid", myLoc, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
