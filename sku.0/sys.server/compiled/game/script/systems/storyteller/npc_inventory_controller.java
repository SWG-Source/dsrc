package script.systems.storyteller;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.storyteller;
import script.library.utils;

public class npc_inventory_controller extends script.base_script
{
    public npc_inventory_controller()
    {
    }
    public int OnReceivedItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (isIdValid(transferer) && isPlayer(transferer))
        {
            if (isIdValid(item))
            {
                utils.setScriptVar(item, "storytellerLoot", true);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToLoseItem(obj_id self, obj_id destContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (isIdValid(transferer) && isGod(transferer) && hasObjVar(transferer, "exploitTesting"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = getContainedBy(self);
        if (isIdValid(transferer) && isPlayer(transferer))
        {
            if (!utils.hasScriptVar(item, "storytellerLoot"))
            {
                sendSystemMessage(transferer, new string_id("storyteller", "open_npc_cannot_take_item"));
                return SCRIPT_OVERRIDE;
            }
        }
        else if (utils.isNestedWithinAPlayer(destContainer) && isPlayer(getContainedBy(destContainer)))
        {
            if (!utils.hasScriptVar(item, "storytellerLoot"))
            {
                sendSystemMessage(transferer, new string_id("storyteller", "open_npc_cannot_take_item"));
                return SCRIPT_OVERRIDE;
            }
        }
        else if (destContainer != npc && !utils.isNestedWithin(destContainer, npc))
        {
            sendSystemMessage(transferer, new string_id("storyteller", "open_npc_cannot_take_item"));
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
}
