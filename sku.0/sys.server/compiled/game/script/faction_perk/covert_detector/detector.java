package script.faction_perk.covert_detector;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.prose;
import script.library.factions;
import script.library.faction_perk;
import script.library.player_structure;

public class detector extends script.base_script
{
    public detector()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        detachScript(self, "faction_perk.covert_detector.detector");
        return SCRIPT_CONTINUE;
    }
}
