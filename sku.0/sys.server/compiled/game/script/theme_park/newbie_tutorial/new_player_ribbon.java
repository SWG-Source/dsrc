package script.theme_park.newbie_tutorial;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;
import script.library.utils;

public class new_player_ribbon extends script.base_script
{
    public new_player_ribbon()
    {
    }
    public static final String questNewbieStart = "quest/tatooine_eisley_legacy";
    public static final String questCrafterEntertainer = "quest/tatooine_eisley_noncombat";
    public int OnLogin(obj_id self) throws InterruptedException
    {
        detachScript(self, "theme_park.newbie_tutorial.new_player_ribbon");
        return SCRIPT_CONTINUE;
    }
}
