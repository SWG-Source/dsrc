package script.theme_park.dungeon.avatar_platform;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class speaker_setup extends script.base_script
{
    public speaker_setup()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setInvulnerable(self, true);
        setName(self, "an intercom");
        detachScript(self, "systems.combat.credit_for_kills");
        obj_id structure = getTopMostContainer(self);
        setObjVar(structure, "speaker", self);
        return SCRIPT_CONTINUE;
    }
}
