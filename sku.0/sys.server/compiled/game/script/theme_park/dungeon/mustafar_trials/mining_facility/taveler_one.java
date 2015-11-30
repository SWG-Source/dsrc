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

public class taveler_one extends script.base_script
{
    public taveler_one()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        attachScript(self, "content_tools.sequencer_master_object");
        sequencer.registerSequenceObject(self, "male_traveler");
        setObjVar(self, "strSequenceTable", "must_mining_traveler");
        chat.setChatMood(self, "sorry");
        setInvulnerable(self, true);
        messageTo(self, "doEvents", null, 10, false);
        return SCRIPT_CONTINUE;
    }
}
