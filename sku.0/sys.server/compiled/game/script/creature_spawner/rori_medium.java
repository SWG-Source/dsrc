package script.creature_spawner;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class rori_medium extends script.creature_spawner.base_newbie_creature_spawner
{
    public rori_medium()
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
        String creature = "vrobalet";
        int choice = rand(1, 5);
        switch (choice)
        {
            case 1:
            creature = "vrobalet";
            break;
            case 2:
            creature = "timid_vir_vur";
            break;
            case 3:
            creature = "flewt";
            break;
            case 4:
            creature = "vrobalet";
            break;
            case 5:
            creature = "timid_vir_vur";
            break;
            default:
            creature = "timid_vir_vur";
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
