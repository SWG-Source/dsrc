package script.faction_perk.base;

import script.library.faction_perk;
import script.menu_info_types;
import script.obj_id;

public class factional_deed extends script.item.structure_deed.player_structure_deed
{
    public factional_deed()
    {
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            if (!faction_perk.canDeployFactionalDeed(player, self))
            {
                LOG("LOG_CHANNEL", "factional_deed::OnObjectMenuSelect -> unable to deploy deed - OVERRIDING!");
                return SCRIPT_OVERRIDE;
            }
            else 
            {
                LOG("LOG_CHANNEL", "factional_deed::OnObjectMenuSelect -> can deploy - continuing...");
            }
        }
        return super.OnObjectMenuSelect(self, player, item);
    }
}
