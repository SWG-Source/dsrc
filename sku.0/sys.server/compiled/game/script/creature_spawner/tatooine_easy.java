package script.creature_spawner;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class tatooine_easy extends script.creature_spawner.base_newbie_creature_spawner
{
    public tatooine_easy()
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
        String creature = "minor_worrt";
        int choice = rand(1, 14);
        switch (choice)
        {
            case 1:
            case 2:
            case 3:
            creature = "minor_worrt";
            break;
            case 4:
            case 5:
            case 6:
            creature = "kreetle";
            break;
            case 7:
            case 8:
            creature = "rill";
            break;
            case 9:
            case 10:
            creature = "lesser_desert_womprat";
            break;
            case 11:
            creature = "worrt";
            break;
            case 12:
            creature = "rockmite";
            break;
            case 13:
            creature = "mound_mite";
            break;
            case 14:
            creature = "womprat";
            break;
            default:
            creature = "minor_worrt";
            break;
        }
        return creature;
    }
    public int getMaxPop() throws InterruptedException
    {
        int maxPop = 8;
        return maxPop;
    }
}
