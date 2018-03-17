package script.creature_spawner;

import script.dictionary;
import script.obj_id;

public class corellia_npc_easy extends base_newbie_npc_spawner
{
    public int maxSpawn = 4;
    public boolean newbie = true;

    public corellia_npc_easy()
    {
    }
    public String pickCreature() throws InterruptedException
    {
        switch (rand(1, 5))
        {
            case 1:
                return "swooper_gangmember";
            case 2:
                return "ragtag_kook";
            case 3:
                return "ragtag_loon";
            case 4:
                return "meatlump_fool";
            case 5:
                return "meatlump_buffoon";
        }
        return "meatlump_buffoon";
    }
    public int creatureDied(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            LOG("sissynoid", "Spawner " + self + " on Corellia. Corellia_npc_easy script had Invalid Params from the deadGuy.");
            CustomerServiceLog("SPAWNER_OVERLOAD", "Spawner " + self + " on Corellia. Corellia_npc_easy script had Invalid Params from the deadGuy.");
            return SCRIPT_CONTINUE;
        }
        return super.creatureDied(self, params);
    }
}
