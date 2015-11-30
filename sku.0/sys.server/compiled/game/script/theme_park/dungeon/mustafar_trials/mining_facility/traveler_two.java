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
import script.library.ai_lib;
import script.library.chat;

public class traveler_two extends script.base_script
{
    public traveler_two()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setInvulnerable(self, true);
        chat.setChatMood(self, "angry");
        sequencer.registerSequenceObject(self, "female_traveler");
        return SCRIPT_CONTINUE;
    }
}
