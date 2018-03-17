package script.creature_spawner;

import script.dictionary;
import script.obj_id;

public class naboo_npc_medium extends base_newbie_npc_spawner
{
    public int maxPop = 3;

    public naboo_npc_medium()
    {
    }
    public String pickCreature() throws InterruptedException
    {
        switch (rand(1, 5))
        {
            case 1:
                return "plasma_bandit";
            case 2:
                return "skaak_tipper_bandit";
            case 3:
                return "mummer_punk";
            case 4:
                return "mummer_ruffian";
            case 5:
                return "plasma_thief";
        }
        return "mummer_ruffian";
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
