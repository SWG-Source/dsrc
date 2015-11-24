package script.creature_spawner;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class corellia_hard extends script.creature_spawner.base_newbie_creature_spawner
{
    public corellia_hard()
    {
    }
    public static final boolean SPAWNER_DISABLED = true;
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (!SPAWNER_DISABLED)
        {
            spawnCreatures(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int creatureDied(obj_id self, dictionary params) throws InterruptedException
    {
        doDeathRespawn(self);
        return SCRIPT_CONTINUE;
    }
    public String pickCreature() throws InterruptedException
    {
        String creature = "crazed_durni";
        int choice = rand(1, 3);
        switch (choice)
        {
            case 1:
            creature = "crazed_durni";
            break;
            case 2:
            creature = "gurrcat";
            break;
            case 3:
            creature = "sand_panther_cub";
            break;
            default:
            creature = "sand_panther_cub";
            break;
        }
        return creature;
    }
    public int getMaxPop() throws InterruptedException
    {
        int maxPop = 3;
        return maxPop;
    }
}
