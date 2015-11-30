package script.npc.celebrity;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class reelo_baruk extends script.base_script
{
    public reelo_baruk()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        attachScript(self, "conversation.reelo_baruk");
        if (hasScript(self, "theme_park.tatooine.recruitment.hutt_recruit"))
        {
            detachScript(self, "theme_park.tatooine.recruitment.hutt_recruit");
        }
        return SCRIPT_CONTINUE;
    }
}
