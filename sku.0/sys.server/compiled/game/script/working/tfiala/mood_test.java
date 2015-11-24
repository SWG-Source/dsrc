package script.working.tfiala;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.create;

public class mood_test extends script.base_script
{
    public mood_test()
    {
    }
    public int OnHearSpeech(obj_id self, obj_id objSpeaker, String strText) throws InterruptedException
    {
        if (strText.equals("mood_test"))
        {
            location speakerLocation = getLocation(objSpeaker);
            obj_id goodGuy = create.object("commoner", speakerLocation);
            ai_lib.setDefaultCalmMood(goodGuy, "npc_sad");
            obj_id badGuy = create.object("patron_human_female_01", speakerLocation);
            ai_lib.setDefaultCalmMood(badGuy, "npc_sad");
        }
        return SCRIPT_CONTINUE;
    }
}
