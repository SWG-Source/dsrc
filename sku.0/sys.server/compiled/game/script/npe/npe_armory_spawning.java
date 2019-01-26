package script.npe;

import script.library.create;
import script.library.utils;
import script.location;
import script.obj_id;

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
            -44.0f,
                18.0f,
            -47.0f,
                21.0f,
            -49.0f,
                25.0f,
            -50.0f,
                30.0f,
            -48.0f,
                34.0f,
            -45.0f,
                38.0f,
            -42.0f,
                39.0f,
            -37.0f,
                39.0f,
            -33.0f,
                36.0f
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
