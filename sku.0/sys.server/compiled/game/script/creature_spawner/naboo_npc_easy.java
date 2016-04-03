package script.creature_spawner;

import script.dictionary;
import script.obj_id;

public class naboo_npc_easy extends base_newbie_npc_spawner
{
    public int maxPop = 4;
    public boolean newbie = true;

    public naboo_npc_easy()
    {
    }
    public String pickCreature() throws InterruptedException
    {
        switch (rand(1, 4))
        {
            case 1:
                return "skaak_tipper_prowler";
            case 2:
                return "skaak_tipper_mugger";
            case 3:
                return "mummer_thug";
            case 4:
                return "mummer_bully";
        }
        return "mummer_bully";
    }
    public int creatureDied(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            LOG("sissynoid", "Spawner " + self + " on Naboo. Naboo_npc_easy script had Invalid Params from the deadGuy.");
            CustomerServiceLog("SPAWNER_OVERLOAD", "Spawner " + self + " on Naboo. Naboo_npc_easy script had Invalid Params from the deadGuy.");
            return SCRIPT_CONTINUE;
        }
        return super.creatureDied(self, params);
    }
}
