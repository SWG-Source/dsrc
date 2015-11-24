package script.structure;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.player_structure;

public class hologram_control extends script.base_script
{
    public hologram_control()
    {
    }
    public int OnReceivedItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        obj_id[] players = player_structure.getPlayersInBuilding(self);
        if (players == null || players.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        if (players.length > 1)
        {
            return SCRIPT_CONTINUE;
        }
        broadcastMessage("handleActivateHolograms", new dictionary());
        return SCRIPT_CONTINUE;
    }
}
