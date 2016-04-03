package script.creature_spawner;

import script.dictionary;
import script.obj_id;

public class tatooine_npc_easy extends base_newbie_npc_spawner
{
    public int maxPop = 4;
    public boolean newbie = true;

    public tatooine_npc_easy()
    {
    }
    public String pickCreature() throws InterruptedException
    {
        switch (rand(1,4))
        {
            case 1:
                return "desert_swooper";
            case 2:
                return "desert_swooper_leader";
            case 3:
                return "jabbas_swooper";
            case 4:
                return "jabba_scout";
        }
        return "desert_swooper";
    }
    public int creatureDied(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            LOG("sissynoid", "Spawner " + self + " on Tatooine. Tatooine_npc_easy script had Invalid Params from the deadGuy.");
            CustomerServiceLog("SPAWNER_OVERLOAD", "Spawner " + self + " on Tatooine. Tatooine_npc_easy script had Invalid Params from the deadGuy.");
            return SCRIPT_CONTINUE;
        }
        return super.creatureDied(self, params);
    }
}
