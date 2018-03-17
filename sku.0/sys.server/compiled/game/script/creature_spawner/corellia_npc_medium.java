package script.creature_spawner;

import script.dictionary;
import script.obj_id;

public class corellia_npc_medium extends base_newbie_npc_spawner
{
    public int maxSpawn = 3;
    public boolean newbie = true;

    public corellia_npc_medium()
    {
    }
    public String pickCreature() throws InterruptedException
    {
        switch (rand(1, 5))
        {
            case 1:
                return "ragtag_lunatic";
            case 2:
                return "meatlump_stooge";
            case 3:
                return "meatlump_clod";
            case 4:
                return "corsec_rogue";
            case 5:
                return "swooper_leader";
        }
        return "swooper_leader";
    }
    public int creatureDied(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            LOG("sissynoid", "Spawner " + self + " on Corellia. Corellia_npc_medium script had Invalid Params from the deadGuy.");
            CustomerServiceLog("SPAWNER_OVERLOAD", "Spawner " + self + " on Corellia. Corellia_npc_medium script had Invalid Params from the deadGuy.");
            return SCRIPT_CONTINUE;
        }
        return super.creatureDied(self, params);
    }
}
