package script.working.tfiala;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class mount_test extends script.base_script
{
    public mount_test()
    {
    }
    public int OnHearSpeech(obj_id self, obj_id objSpeaker, String strText) throws InterruptedException
    {
        if (strText.equals("mounttest"))
        {
            boolean mpmResult = makePetMountable(self);
            updateMountWearableVisuals(self);
            boolean mcResult = mountCreature(objSpeaker, self);
            dismountCreature(objSpeaker);
            obj_id mountId = getMountId(objSpeaker);
            int canMakePetMountable = couldPetBeMadeMountable(self);
        }
        return SCRIPT_CONTINUE;
    }
}
