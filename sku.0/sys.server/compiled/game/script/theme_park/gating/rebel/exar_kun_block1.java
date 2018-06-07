package script.theme_park.gating.rebel;

import script.library.factions;
import script.obj_id;
import script.string_id;

public class exar_kun_block1 extends script.base_script
{
    public exar_kun_block1()
    {
    }
    public int OnAboutToReceiveItem(obj_id self, obj_id destinationCell, obj_id transferrer, obj_id item) throws InterruptedException
    {
        if (!isIdValid(item) || !isPlayer(item))
        {
            return SCRIPT_CONTINUE;
        }
        if (!factions.isRebel(item))
        {
            String fromCell = getCellName(getLocation(item).cell);
            if (fromCell == null || !fromCell.equals("r4"))
            {
                string_id warning = new string_id("theme_park_rebel/warning", "foyer1");
                sendSystemMessage(item, warning);
                return SCRIPT_OVERRIDE;
            }
        }
        return SCRIPT_CONTINUE;
    }
}
