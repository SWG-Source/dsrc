package script.theme_park.gating.nym;

import script.library.utils;
import script.obj_id;
import script.string_id;

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
        for (obj_id content : contents) {
            String itemInInventory = getTemplateName(content);
            if (itemInInventory.equals(giveMe)) {
                hadIt = true;
            }
        }
        return hadIt;
    }
}
