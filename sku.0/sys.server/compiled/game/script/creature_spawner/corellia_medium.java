package script.creature_spawner;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class corellia_medium extends script.creature_spawner.base_newbie_creature_spawner
{
    public corellia_medium()
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
        String creature = "corellian_butterfly";
        int choice = rand(1, 4);
        switch (choice)
        {
            case 1:
            creature = "corellian_butterfly";
            break;
            case 2:
            creature = "corellian_butterfly_worker";
            break;
            case 3:
            creature = "vynock";
            break;
            case 4:
            creature = "scavenger_rat";
            break;
            default:
            creature = "vynock";
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
