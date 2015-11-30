package script.player.gcw;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.factions;
import script.library.create;
import script.library.trial;
import script.library.static_item;
import script.library.utils;
import script.library.buff;
import script.library.stealth;

public class pvp_aura_buff_controller extends script.base_script
{
    public pvp_aura_buff_controller()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (factions.isRebel(self))
        {
            setObjVar(self, "pvp_aura_buff.faction", 0);
            messageTo(self, "removeScript", null, 180.0f, false);
            messageTo(self, "buffAlly", null, 3.0f, false);
            return SCRIPT_CONTINUE;
        }
        if (factions.isImperial(self))
        {
            setObjVar(self, "pvp_aura_buff.faction", 1);
            messageTo(self, "removeScript", null, 180.0f, false);
            messageTo(self, "buffAlly", null, 3.0f, false);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            messageTo(self, "removeScript", null, 2.0f, false);
            buff.removeBuff(self, "pvp_aura_buff_self");
            return SCRIPT_CONTINUE;
        }
    }
    public int buffAlly(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] players = null;
        if (isMob(self) && !isPlayer(self))
        {
            players = getCreaturesInRange(self, 20.0f);
        }
        else 
        {
            players = trial.getValidPlayersInRadius(self, 20.0f);
        }
        Vector filteredPlayers = new Vector();
        filteredPlayers.setSize(0);
        boolean applyBuff = true;
        int faction = -1;
        if (!hasObjVar(self, "pvp_aura_buff.faction"))
        {
            applyBuff = false;
        }
        else 
        {
            faction = getIntObjVar(self, "pvp_aura_buff.faction");
        }
        if (players == null || players.length == 0)
        {
            applyBuff = false;
        }
        else 
        {
            for (int i = 0; i < players.length; i++)
            {
                if (!isPlayer(players[i]) && !isMob(players[i]) || isDead(players[i]))
                {
                    continue;
                }
                switch (faction)
                {
                    case 0:
                    if (factions.isRebel(players[i]))
                    {
                        if (!stealth.hasInvisibleBuff(players[i]))
                        {
                            utils.addElement(filteredPlayers, players[i]);
                        }
                        break;
                    }
                    break;
                    case 1:
                    if (factions.isImperial(players[i]))
                    {
                        if (!stealth.hasInvisibleBuff(players[i]))
                        {
                            utils.addElement(filteredPlayers, players[i]);
                        }
                    }
                    break;
                    default:
                    break;
                }
            }
        }
        if (filteredPlayers == null || filteredPlayers.size() == 0)
        {
            applyBuff = false;
        }
        if (filteredPlayers != null)
        {
            players = new obj_id[filteredPlayers.size()];
            filteredPlayers.toArray(players);

        }
        if (applyBuff)
        {
            if (faction == 0)
            {
                buff.applyBuff(players, "pvp_aura_buff_rebel_target");
            }
        }
        if (faction == 1)
        {
            buff.applyBuff(players, "pvp_aura_buff_target");
        }
        messageTo(self, "buffAlly", null, 4.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int removeFactionObjVar(obj_id self, dictionary params) throws InterruptedException
    {
        removeObjVar(self, "pvp_aura_buff.faction");
        return SCRIPT_CONTINUE;
    }
}
