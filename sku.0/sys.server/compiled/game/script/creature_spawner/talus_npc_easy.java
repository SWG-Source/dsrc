package script.creature_spawner;

import script.dictionary;
import script.obj_id;

public class talus_npc_easy extends base_newbie_npc_spawner
{
    public int maxPop = 4;
    public boolean newbie = false;

    public talus_npc_easy()
    {
    }
    public String pickCreature() throws InterruptedException
    {
        switch (rand(1,4))
        {
            case 1:
                return "chunker_nitwit";
            case 2:
                return "chunker_punk";
            case 3:
                return "sleemo_hoodlum";
            case 4:
                return "sleemo_punk";
        }
        return "sleemo_hoodlum";
    }
    public int creatureDied(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            LOG("sissynoid", "Spawner " + self + " on Talus. Talus_npc_easy script had Invalid Params from the deadGuy.");
            CustomerServiceLog("SPAWNER_OVERLOAD", "Spawner " + self + " on Talus. Talus_npc_easy script had Invalid Params from the deadGuy.");
            return SCRIPT_CONTINUE;
        }
        return super.creatureDied(self, params);
    }
}
