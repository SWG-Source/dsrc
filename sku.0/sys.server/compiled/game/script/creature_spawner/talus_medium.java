package script.creature_spawner;

import script.dictionary;
import script.obj_id;

public class talus_medium extends script.creature_spawner.base_newbie_creature_spawner
{
    public talus_medium()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int creatureDied(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public String pickCreature() throws InterruptedException
    {
        return null;
    }
    public int getMaxPop() throws InterruptedException
    {
        int maxPop = 0;
        return maxPop;
    }
}
