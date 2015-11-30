package script.faction_perk.base;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.faction_perk;
import script.library.factions;
import script.library.utils;

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
