package script.player.skill;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class player_healing extends script.base_script
{
    public player_healing()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        detachScript(self, "player.skill.player_healing");
        return SCRIPT_CONTINUE;
    }
}
