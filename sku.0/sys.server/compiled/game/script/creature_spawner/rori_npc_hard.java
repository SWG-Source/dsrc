package script.creature_spawner;

import script.dictionary;
import script.obj_id;

public class rori_npc_hard extends base_newbie_npc_spawner
{
    public int maxPop = 3;
    public boolean newbie = false;

    public rori_npc_hard()
    {
    }
    public String pickCreature() throws InterruptedException
    {
        switch (rand(1, 5))
        {
            case 1:
                return "gundark_raider";
            case 2:
                return "gundark_ruffian";
            case 3:
                return "garyn_mugger";
            case 4:
                return "cobral_thug";
            case 5:
                return "garyn_pirate";
        }
        return "garyn_pirate";
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
