package script.creature_spawner;

import script.dictionary;
import script.obj_id;

public class rori_npc_easy extends base_newbie_npc_spawner
{
    public int maxPop = 4;
    public boolean newbie = true;

    public rori_npc_easy()
    {
    }
    public String pickCreature() throws InterruptedException
    {
        switch (rand(1, 4))
        {
            case 1:
                return "garyn_prowler";
            case 2:
                return "gundark_crook";
            case 3:
                return "cobral_mugger";
            case 4:
                return "gundark_hooligan";
        }
        return "cobral_mugger";
    }
    public int creatureDied(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            LOG("sissynoid", "Spawner " + self + " on Rori. Rori_npc_easy script had Invalid Params from the deadGuy.");
            CustomerServiceLog("SPAWNER_OVERLOAD", "Spawner " + self + " on Rori. Rori_npc_easy script had Invalid Params from the deadGuy.");
            return SCRIPT_CONTINUE;
        }
        return super.creatureDied(self, params);
    }
}
