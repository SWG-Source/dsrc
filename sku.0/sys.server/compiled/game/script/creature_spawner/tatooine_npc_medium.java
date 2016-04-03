package script.creature_spawner;

import script.dictionary;
import script.obj_id;

public class tatooine_npc_medium extends base_newbie_npc_spawner
{
    public static final boolean SPAWNER_DISABLED = false;
    public int maxPop = 3;
    public boolean newbie = true;

    public tatooine_npc_medium()
    {
    }
    public String pickCreature() throws InterruptedException
    {
        switch (rand(1,9))
        {
            case 1:
                return "cannibal";
            case 2:
                return "desert_demon";
            case 3:
                return "spice_fiend";
            case 4:
                return "valarian_thug";
            case 5:
                return "valarian_swooper_leader";
            case 6:
                return "jabba_thief";
            case 7:
                return "evil_nomad";
            case 8:
                return "jabba_thug";
            case 9:
                return "jabbas_swooper_leader";
        }
        return "valarian_swooper_leader";
    }
    public int creatureDied(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            LOG("sissynoid", "Spawner " + self + " on Tatooine. Tatooine_npc_medium script had Invalid Params from the deadGuy.");
            CustomerServiceLog("SPAWNER_OVERLOAD", "Spawner " + self + " on Tatooine. Tatooine_npc_medium script had Invalid Params from the deadGuy.");
            return SCRIPT_CONTINUE;
        }
        return super.creatureDied(self, params);
    }
}
