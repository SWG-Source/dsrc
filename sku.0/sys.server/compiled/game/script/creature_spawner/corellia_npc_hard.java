package script.creature_spawner;

import script.dictionary;
import script.obj_id;

public class corellia_npc_hard extends base_newbie_npc_spawner
{
    public int maxSpawn = 3;
    public boolean newbie = true;

    public corellia_npc_hard()
    {
    }
    public String pickCreature() throws InterruptedException
    {
        switch (rand(1, 5))
        {
            case 1:
                return "meatlump_oaf";
            case 2:
                return "meatlump_cretin";
            case 3:
                return "flail_enforcer";
            case 4:
                return "flail_cutthroat";
            case 5:
                return "ragtag_maniac";
        }
        return "flail_cutthroat";
    }
    public int creatureDied(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            LOG("sissynoid", "Spawner " + self + " on Corellia. Corellia_npc_hard script had Invalid Params from the deadGuy.");
            CustomerServiceLog("SPAWNER_OVERLOAD", "Spawner " + self + " on Corellia. Corellia_npc_hard script had Invalid Params from the deadGuy.");
            return SCRIPT_CONTINUE;
        }
        return super.creatureDied(self, params);
    }
}
