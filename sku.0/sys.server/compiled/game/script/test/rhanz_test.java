package script.test;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.create;
import script.library.groundquests;
import script.library.pclib;
import script.library.skill;
import script.library.space_quest;
import script.library.sui;
import script.library.utils;
import script.library.weapons;

public class rhanz_test extends script.base.remote_object_requester
{
    public rhanz_test()
    {
    }
    public static final String STARTING_EQUIPMENT_FILE = "datatables/equipment/newbie_equipment.iff";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!isGod(self) || getGodLevel(self) < 50 || !isPlayer(self)) {
            detachScript(self, "test.rhanz_test");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        if (isGod(self))
        {
            java.util.StringTokenizer tok = new java.util.StringTokenizer(text);
            if (tok.hasMoreTokens())
            {
                String command = tok.nextToken();
                debugConsoleMsg(self, "command is: " + command);
                if (command.equalsIgnoreCase("rh_help"))
                {
                    debugConsoleMsg(self, "Usage\nrh_setPet (sets pet to current target)\n");
                    debugConsoleMsg(self, "rh_commands1 (loads up the first set of commands)\n");
                    debugConsoleMsg(self, "rh_commands2 (loads up the second set of commands)\n");
                    debugConsoleMsg(self, "rh_toggle1 (turns on \"bm_bite_1\")\n");
                    debugConsoleMsg(self, "rh_toggle2 (turns on \"toggleBeastDefensive\")\n");
                    debugConsoleMsg(self, "rh_cleartoggles (clears all toggles)\n");
                }
                else if (command.equalsIgnoreCase("rh_setPet"))
                {
                    setBeastmasterPet(self, getIntendedTarget(self));
                }
                else if (command.equalsIgnoreCase("rh_commands1"))
                {
                    debugSpeakMsg(self, "commands1");
                    String commands[] = 
                    {
                        "bm_follow_1",
                        "bm_stay_1",
                        "bm_pet_attack",
                        "invalid",
                        "empty",
                        ""
                    };
                    setBeastmasterPetCommands(self, commands);
                }
                else if (command.equalsIgnoreCase("rh_commands2"))
                {
                    debugSpeakMsg(self, "commands2");
                    String commands[] = 
                    {
                        "",
                        "invalid",
                        "empty",
                        "bm_pet_attack",
                        "bm_stay_1",
                        "bm_follow_1"
                    };
                    setBeastmasterPetCommands(self, commands);
                }
                else if (command.equalsIgnoreCase("rh_clearcommands"))
                {
                    debugSpeakMsg(self, "clear commands");
                    String commands[] = 
                    {
                    };
                    setBeastmasterPetCommands(self, commands);
                }
                else if (command.equalsIgnoreCase("rh_toggle1"))
                {
                    String commands[] = 
                    {
                        "bm_bite_1"
                    };
                    setBeastmasterToggledPetCommands(self, commands);
                }
                else if (command.equalsIgnoreCase("rh_toggle2"))
                {
                    String commands[] = 
                    {
                        "toggleBeastDefensive"
                    };
                    setBeastmasterToggledPetCommands(self, commands);
                }
                else if (command.equalsIgnoreCase("rh_cleartoggles"))
                {
                    String commands[] = 
                    {
                    };
                    setBeastmasterToggledPetCommands(self, commands);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
