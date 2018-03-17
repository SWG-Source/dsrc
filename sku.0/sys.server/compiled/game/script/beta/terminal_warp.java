package script.beta;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class terminal_warp extends script.base_script
{
    public terminal_warp()
    {
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (isGod(player) || hasObjVar(player, "beta.terminal_ok"))
        {
            if (!hasScript(player, "beta.player_warp"))
            {
                attachScript(player, "beta.player_warp");
            }
            sendSystemMessageTestingOnly(player, "Warp script attached.");
            return SCRIPT_CONTINUE;
        }
        else 
        {
            sendSystemMessageTestingOnly(player, "Only authorized users may access this terminal.");
            return SCRIPT_CONTINUE;
        }
    }
}
