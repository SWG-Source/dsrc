package script.theme_park.dungeon.corvette;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class death_target extends script.base_script
{
    public death_target()
    {
    }
    public int OnDeath(obj_id self, obj_id killer, obj_id corpse) throws InterruptedException
    {
        obj_id top = getTopMostContainer(self);
        String type = getStringObjVar(top, "space_dungeon.quest_type");
        if (type.equals("neutral_assassin") || type.equals("imperial_assassin") || type.equals("rebel_assassin"))
        {
            if (hasObjVar(top, "current_players"))
            {
                Vector players = utils.getResizeableObjIdBatchObjVar(top, "current_players");
                if (players == null)
                {
                    return SCRIPT_CONTINUE;
                }
                int numPlayers = players.size();
                if (numPlayers == 0)
                {
                    return SCRIPT_CONTINUE;
                }
                boolean prizeAwarded = false;
                for (int i = 0; i < numPlayers; i++)
                {
                    obj_id player = (obj_id) players.get(i);
                    messageTo(player, "youWin", null, 1, false);
                    if(!prizeAwarded) {
                        // the ITV should be a 12.5% chance to be awarded.
                        int prize = rand(0, 7);
                        if (prize == 4) {
                            obj_id inv = utils.getInventoryContainer(player);
                            obj_id itv = createObject("object/tangible/veteran_reward/instant_travel_terminal.iff", inv, "");
                            if (isIdValid(itv)) {
                                CustomerServiceLog("Loot", "User: (" + player + ") " + getName(player) + " has won the factional Instant Travel Vehicle.");
                                sendSystemMessage(player, "Congratulations! You've looted the factional Instant Travel Vehicle!", null);
                                playMusic(player, "sound/music_mission_complete.snd");
                                prizeAwarded = true;
                            }
                        }
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public boolean groupHasObjVar(obj_id player, String objvarName) throws InterruptedException
    {
        if (hasObjVar(player, objvarName))
        {
            return true;
        }
        obj_id[] members = getGroupMemberIds(player);
        if (members == null)
        {
            return false;
        }
        int numInGroup = members.length;
        if (numInGroup == 0)
        {
            return false;
        }
        for (int i = 0; i < numInGroup; i++)
        {
            obj_id thisMember = members[i];
            if (hasObjVar(thisMember, objvarName))
            {
                return true;
            }
        }
        return false;
    }
}
