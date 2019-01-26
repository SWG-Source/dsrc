package script.theme_park.dungeon.avatar_platform;

import script.dictionary;
import script.library.player_structure;
import script.obj_id;

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
        messageTo(self, "handleEffectScript", who, 1.0f, false);
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
            for (obj_id player1 : players) {
                attachScript(player1, "theme_park.dungeon.avatar_platform.avatar_destruction_player");
            }
        }
        return SCRIPT_CONTINUE;
    }
}
