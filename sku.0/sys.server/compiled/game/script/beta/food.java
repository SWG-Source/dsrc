package script.beta;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.consumable;
import script.library.player_stomach;

public class food extends script.base_script
{
    public food()
    {
    }
    public static final String SCRIPT_BETA_FOOD = "beta.food";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
}
