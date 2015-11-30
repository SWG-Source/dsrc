package script.npe;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.ai_lib;

public class npe_band_sequence extends script.base_script
{
    public npe_band_sequence()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setInvulnerable(self, true);
        grantSkill(self, "social_entertainer_novice");
        messageTo(self, "npeSetName", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int OnPreloadComplete(obj_id self) throws InterruptedException
    {
        boolean setting = utils.checkConfigFlag("ScriptFlags", "npeSequencersActive");
        return SCRIPT_CONTINUE;
    }
    public int npeSetName(obj_id self, dictionary params) throws InterruptedException
    {
        String myName = utils.getStringObjVar(self, "strSequenceIdentifier");
        if (myName != null || !myName.equals(""))
        {
            if (myName.equals("band1"))
            {
                ai_lib.setMood(self, "themepark_music_2");
                setName(self, "Mako");
            }
            if (myName.equals("band2"))
            {
                obj_id instrument = createObject("object/tangible/instrument/bandfill.iff", self, "");
                equip(instrument, self);
                ai_lib.setMood(self, "themepark_music_1");
                setName(self, "Alisia");
            }
            if (myName.equals("band3"))
            {
                obj_id instrument = createObject("object/tangible/instrument/kloo_horn.iff", self, "");
                equip(instrument, self);
                ai_lib.setMood(self, "themepark_music_3");
                setName(self, "Alitra");
            }
            if (myName.equals("dancer"))
            {
                setAnimationMood(self, "npc_dance_basic");
                setName(self, "Mestral");
            }
        }
        return SCRIPT_CONTINUE;
    }
}
