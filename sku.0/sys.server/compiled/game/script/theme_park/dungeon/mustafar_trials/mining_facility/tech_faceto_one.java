package script.theme_park.dungeon.mustafar_trials.mining_facility;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.sequencer;

public class tech_faceto_one extends script.base_script
{
    public tech_faceto_one()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        sequencer.registerSequenceObject(self, "tech_face1");
        return SCRIPT_CONTINUE;
    }
}
