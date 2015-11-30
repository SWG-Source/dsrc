package script.theme_park.gating.nym;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class research_lab_entry extends script.base_script
{
    public research_lab_entry()
    {
    }
    public int OnAboutToReceiveItem(obj_id self, obj_id destinationCell, obj_id transferrer, obj_id item) throws InterruptedException
    {
        if (!isPlayer(item))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id playerInv = utils.getInventoryContainer(item);
        if (!checkForPasskey(playerInv, item) == true)
        {
            string_id warning = new string_id("theme_park_nym/messages", "need_passkey");
            sendSystemMessage(item, warning);
            return SCRIPT_OVERRIDE;
        }
        else 
        {
            return SCRIPT_CONTINUE;
        }
    }
    public boolean checkForPasskey(obj_id inv, obj_id player) throws InterruptedException
    {
        String giveMe = "";
        boolean hadIt = false;
        obj_id[] contents = getContents(inv);
        for (int i = 0; i < contents.length; i++)
        {
            String itemInInventory = getTemplateName(contents[i]);
            if (itemInInventory.equals(giveMe))
            {
                hadIt = true;
            }
        }
        return hadIt;
    }
}
