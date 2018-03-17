package script.faction_perk.hq;

import script.obj_id;
import script.string_id;

public class terminal_disable extends script.base_script
{
    public terminal_disable()
    {
    }
    private static final string_id SID_TERMINAL_DISABLED = new string_id("hq", "terminal_disabled");
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        sendSystemMessage(player, SID_TERMINAL_DISABLED);
        return SCRIPT_OVERRIDE;
    }
}
