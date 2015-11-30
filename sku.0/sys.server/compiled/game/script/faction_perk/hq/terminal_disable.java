package script.faction_perk.hq;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class terminal_disable extends script.base_script
{
    public terminal_disable()
    {
    }
    public static final string_id SID_TERMINAL_DISABLED = new string_id("hq", "terminal_disabled");
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        sendSystemMessage(player, SID_TERMINAL_DISABLED);
        return SCRIPT_OVERRIDE;
    }
}
