package script.creature_spawner;

import script.dictionary;
import script.obj_id;

public class rori_npc_medium extends base_newbie_npc_spawner
{
    public int maxPop = 3;
    public boolean newbie = true;

    public rori_npc_medium()
    {
    }
    public String pickCreature() throws InterruptedException
    {
        switch (rand(1,4))
        {
            case 1:
                return "gundark_rogue";
            case 2:
                return "cobral_hooligan";
            case 3:
                return "garyn_thief";
            case 4:
                return "gundark_desperado";
        }
        return "gundark_rogue";
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
