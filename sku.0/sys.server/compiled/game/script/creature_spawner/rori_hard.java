package script.creature_spawner;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class rori_hard extends script.creature_spawner.base_newbie_creature_spawner
{
    public rori_hard()
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
        String creature = "wormed_vrobal";
        int choice = rand(1, 5);
        switch (choice)
        {
            case 1:
            creature = "wormed_vrobal";
            break;
            case 2:
            creature = "bark_mite";
            break;
            case 3:
            creature = "vir_vur";
            break;
            case 4:
            creature = "squall_female";
            break;
            case 5:
            creature = "squall_male";
            break;
            default:
            creature = "bark_mite";
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
