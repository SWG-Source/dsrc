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

public class npe_armory_spawning extends script.base_script
{
    public npe_armory_spawning()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        location myLoc = new location();
        myLoc.cell = self;
        float[] locations = 
        {
            -44f,
            18f,
            -47f,
            21f,
            -49f,
            25f,
            -50f,
            30f,
            -48f,
            34f,
            -45f,
            38f,
            -42f,
            39f,
            -37f,
            39f,
            -33f,
            36f
        };
        boolean setting = utils.checkConfigFlag("ScriptFlags", "npeSequencersActive");
        if (setting == true)
        {
            for (int i = 0; i <= 17; i = i + 2)
            {
                myLoc.x = locations[i];
                myLoc.z = locations[i + 1];
                create.object("npe_training_droid", myLoc, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
