package script.creature_spawner;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class tatooine_medium extends script.creature_spawner.base_newbie_creature_spawner
{
    public tatooine_medium()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        spawnCreatures(self);
        return SCRIPT_CONTINUE;
    }
    public int creatureDied(obj_id self, dictionary params) throws InterruptedException
    {
        doDeathRespawn(self);
        return SCRIPT_CONTINUE;
    }
    public String pickCreature() throws InterruptedException
    {
        String creature = "rockmite";
        int choice = rand(1, 3);
        switch (choice)
        {
            case 1:
            creature = "rockmite";
            break;
            case 2:
            creature = "mound_mite";
            break;
            case 3:
            creature = "worrt";
            break;
            default:
            creature = "worrt";
            break;
        }
        return creature;
    }
    public int getMaxPop() throws InterruptedException
    {
        int maxPop = 4;
        return maxPop;
    }
}
