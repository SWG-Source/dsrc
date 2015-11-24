package script.structure.municipal;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.healing;
import script.library.structure;
import script.library.player_structure;
import script.library.utils;

public class hospital extends script.base_script
{
    public hospital()
    {
    }
    public static final int HEALING_PULSE_MIN = 200;
    public static final int HEALING_PULSE_MAX = 400;
    public static final int WOUND_HEAL = 10;
    public static final int SHOCK_HEAL = 3;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (player_structure.isCivic(self))
        {
            if (!utils.hasScriptVar(self, "terminalsCreated"))
            {
                structure.createStructureTerminals(self, "datatables/structure/municipal/registration_terminal.iff");
                utils.setScriptVar(self, "terminalsCreated", true);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, "terminalsCreated"))
        {
            structure.createStructureTerminals(self, "datatables/structure/municipal/registration_terminal.iff");
            utils.setScriptVar(self, "terminalsCreated", true);
        }
        return SCRIPT_CONTINUE;
    }
}
