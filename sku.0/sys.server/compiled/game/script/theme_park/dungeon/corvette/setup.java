package script.theme_park.dungeon.corvette;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.player_structure;
import script.library.utils;

public class setup extends script.base_script
{
    public setup()
    {
    }
    public int OnReceivedItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        obj_id top = getTopMostContainer(self);
        if (hasObjVar(top, "space_dungeon.quest_type"))
        {
            String quest_type = getStringObjVar(top, "space_dungeon.quest_type");
            String forPlayer = "corl_corvette." + quest_type + "_mission";
            if (!hasObjVar(item, forPlayer))
            {
                setObjVar(item, forPlayer, 1);
            }
        }
        if (isPlayer(item))
        {
            dictionary webster = new dictionary();
            webster.put("player", item);
            messageTo(self, "addToList", webster, 1, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int addToList(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        obj_id top = getTopMostContainer(self);
        if (player == null)
        {
            return SCRIPT_CONTINUE;
        }
        Vector permList = utils.getResizeableObjIdBatchObjVar(top, "current_players");
        if (permList == null)
        {
            permList = new Vector();
        }
        if (!utils.isElementInArray(permList, player))
        {
            permList = utils.addElement(permList, player);
            utils.setResizeableBatchObjVar(top, "current_players", permList);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        obj_id[] players = player_structure.getPlayersInBuilding(self);
        if (players == null)
        {
            return SCRIPT_CONTINUE;
        }
        int numPlayers = players.length;
        if (numPlayers > 0)
        {
            for (int i = 0; i < numPlayers; i++)
            {
                if (!hasScript(players[i], "theme_park.dungeon.corvette.timer"))
                {
                    attachScript(players[i], "theme_park.dungeon.corvette.timer");
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
