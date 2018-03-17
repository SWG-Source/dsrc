package script.creature_spawner;

import script.dictionary;
import script.obj_id;

public class tatooine_npc_hard extends base_newbie_npc_spawner
{
    public static final boolean SPAWNER_DISABLED = false;
    public int maxPop = 3;
    public boolean newbie = true;
    
    public tatooine_npc_hard()
    {
    }
    public String pickCreature() throws InterruptedException
    {
        switch (rand(1,11))
        {
            case 1:
                return "jabba_thief";
            case 2:
                return "trandoshan_slaver";
            case 3:
                return "tusken_commoner";
            case 4:
                return "valarian_thief";
            case 5:
                return "valarian_henchman";
            case 6:
                return "jabba_enforcer";
            case 7:
                return "desert_demon_brawler";
            case 8:
                return "desert_demon_marksman";
            case 9:
                return "gunrunner";
            case 10:
                return "dune_stalker_marksman";
            case 11:
                return "dune_stalker_brawler ";
        }
        return "tusken_commoner";
    }
    public int creatureDied(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            LOG("sissynoid", "Spawner " + self + " on Tatooine. Tatooine_npc_hard script had Invalid Params from the deadGuy.");
            CustomerServiceLog("SPAWNER_OVERLOAD", "Spawner " + self + " on Tatooine. Tatooine_npc_hard script had Invalid Params from the deadGuy.");
            return SCRIPT_CONTINUE;
        }
        return super.creatureDied(self, params);
    }
}
