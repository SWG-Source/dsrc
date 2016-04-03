package script.creature_spawner;

import script.dictionary;
import script.obj_id;

public class talus_npc_hard extends base_newbie_npc_spawner
{
    public int maxPop = 3;
    public boolean newbie = false;

    public talus_npc_hard()
    {
    }
    public String pickCreature() throws InterruptedException
    {
        switch (rand(1,4))
        {
            case 1:
                return "sleemo_vandal";
            case 2:
                return "chunker_bully";
            case 3:
                return "aakuan_follower";
            case 4:
                return "binayre_hooligan";
        }
        return "aakuan_follower";
    }
    public int creatureDied(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            LOG("sissynoid", "Spawner " + self + " on Rori. Rori_npc_hard script had Invalid Params from the deadGuy.");
            CustomerServiceLog("SPAWNER_OVERLOAD", "Spawner " + self + " on Rori. Rori_npc_hard script had Invalid Params from the deadGuy.");
            return SCRIPT_CONTINUE;
        }
        return super.creatureDied(self, params);
    }
}
