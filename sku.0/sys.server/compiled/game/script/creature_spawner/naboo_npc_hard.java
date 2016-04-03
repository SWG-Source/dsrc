package script.creature_spawner;

import script.dictionary;
import script.obj_id;

public class naboo_npc_hard extends base_newbie_npc_spawner
{
    public int maxPop = 3;

    public naboo_npc_hard()
    {
    }
    public String pickCreature() throws InterruptedException
    {
        switch (rand(1, 4))
        {
            case 1:
                return "mauler_acolyte";
            case 2:
                return "plasma_thief_leader";
            case 3:
                return "skaak_tipper_swindler";
            case 4:
                return "skaak_tipper_crook";
        }
        return "skaak_tipper_crook";
    }
    public int creatureDied(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            LOG("sissynoid", "Spawner " + self + " on Naboo. Naboo_npc_hard script had Invalid Params from the deadGuy.");
            CustomerServiceLog("SPAWNER_OVERLOAD", "Spawner " + self + " on Naboo. Naboo_npc_hard script had Invalid Params from the deadGuy.");
            return SCRIPT_CONTINUE;
        }
        return super.creatureDied(self, params);
    }
}
