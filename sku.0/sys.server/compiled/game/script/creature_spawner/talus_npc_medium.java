package script.creature_spawner;

import script.dictionary;
import script.obj_id;

public class talus_npc_medium extends base_newbie_npc_spawner
{
    public int maxPop = 3;
    public boolean newbie = false;

    public talus_npc_medium()
    {
    }
    public String pickCreature() throws InterruptedException
    {
        switch (rand(1, 4))
        {
            case 1:
                return "chunker_mooch";
            case 2:
                return "chunker_swindler";
            case 3:
                return "sleemo_delinquent";
            case 4:
                return "sleemo_scamp";
        }
        return "sleemo_scamp";
    }
    public int creatureDied(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            LOG("sissynoid", "Spawner " + self + " on Rori. Rori_npc_medium script had Invalid Params from the deadGuy.");
            CustomerServiceLog("SPAWNER_OVERLOAD", "Spawner " + self + " on Rori. Rori_npc_medium script had Invalid Params from the deadGuy.");
            return SCRIPT_CONTINUE;
        }
        return super.creatureDied(self, params);
    }
}
