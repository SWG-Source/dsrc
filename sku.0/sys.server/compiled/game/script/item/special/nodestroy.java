package script.item.special;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class nodestroy extends script.base_script
{
    public nodestroy()
    {
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id owner = getOwner(self);
        if (isIdValid(owner) && isPlayer(owner) && isGod(owner))
        {
            return SCRIPT_CONTINUE;
        }
        if (isIdValid(owner) && isPlayer(owner))
        {
            prose_package pp = new prose_package();
            pp.stringId = new string_id("error_message", "unable_to_destroy");
            pp.target.id = self;
            sendSystemMessageProse(owner, pp);
        }
        return SCRIPT_OVERRIDE;
    }
}
