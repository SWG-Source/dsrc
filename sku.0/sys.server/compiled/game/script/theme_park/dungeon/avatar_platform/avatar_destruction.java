package script.theme_park.dungeon.avatar_platform;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.player_structure;
import script.library.utils;

public class avatar_destruction extends script.base_script
{
    public avatar_destruction()
    {
    }
    public int handleDestruct(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        dictionary who = new dictionary();
        who.put("player", player);
        messageTo(self, "handleEffectScript", who, 1f, false);
        return SCRIPT_CONTINUE;
    }
    public int handleEffectScript(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        obj_id structure = getTopMostContainer(player);
        obj_id[] players = player_structure.getPlayersInBuilding(structure);
        if (players == null)
        {
            return SCRIPT_CONTINUE;
        }
        int numPlayers = players.length;
        if (numPlayers > 0)
        {
            for (int i = 0; i < numPlayers; i++)
            {
                attachScript(players[i], "theme_park.dungeon.avatar_platform.avatar_destruction_player");
            }
        }
        return SCRIPT_CONTINUE;
    }
}
