package script.creature_spawner;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class corellia_easy extends script.creature_spawner.base_newbie_creature_spawner
{
    public corellia_easy()
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
        String creature = "corellian_butterfly_drone";
        int choice = rand(1, 4);
        switch (choice)
        {
            case 1:
            creature = "corellian_butterfly_drone";
            break;
            case 2:
            creature = "corellian_butterfly";
            break;
            case 3:
            creature = "durni";
            break;
            case 4:
            creature = "gubbur";
            break;
            default:
            creature = "gubbur";
            break;
        }
        return creature;
    }
    public int getMaxPop() throws InterruptedException
    {
        int maxPop = 6;
        return maxPop;
    }
}
