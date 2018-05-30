package script.working;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.pclib;
import script.library.factions;
import script.library.player_structure;
import script.library.skill;

public class royscript extends script.base_script
{
    public royscript()
    {
    }
    public int OnSpeaking(obj_id self, String strText) throws InterruptedException
    {
        String[] strCommands = split(strText, ' ');
        if (strCommands[0].equals("setupImperialFaction"))
        {
            factions.addFactionStanding(self, factions.FACTION_IMPERIAL, 3000);
            sendSystemMessageTestingOnly(self, "Done");
        }
        if (strCommands[0].equals("makeInteresting"))
        {
            setCondition(getLookAtTarget(self), CONDITION_INTERESTING);
            sendSystemMessageTestingOnly(self, "Set condition");
        }
        if (strCommands[0].equals("setYaw"))
        {
            setYaw(self, 90);
            sendSystemMessageTestingOnly(self, "Set condition");
        }
        return SCRIPT_CONTINUE;
    }
}
