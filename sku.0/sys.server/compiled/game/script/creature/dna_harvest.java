package script.creature;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.bio_engineer;

public class dna_harvest extends script.base_script
{
    public dna_harvest()
    {
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (getSkillStatMod(player, "dna_harvesting") > 0)
        {
            int subHarvestDNA = mi.addRootMenu(menu_info_types.SERVER_HARVEST_CORPSE, bio_engineer.SID_HARVEST_DNA);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.SERVER_HARVEST_CORPSE)
        {
            if (getSkillStatMod(player, "dna_harvesting") > 0)
            {
                queueCommand(player, (278730949), self, "", COMMAND_PRIORITY_DEFAULT);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
}
