package script.creature_spawner;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class rori_easy extends script.creature_spawner.base_newbie_creature_spawner
{
    public rori_easy()
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
        String creature = "frail_squall";
        int choice = rand(1, 4);
        switch (choice)
        {
            case 1:
            creature = "flesh_eating_chuba";
            break;
            case 2:
            creature = "nightspider";
            break;
            case 3:
            creature = "frail_squall";
            break;
            case 4:
            creature = "frail_squall";
            break;
            default:
            creature = "flesh_eating_chuba";
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
