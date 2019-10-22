package script.item.gcw_buff_banner;

import script.dictionary;
import script.library.buff;
import script.library.factions;
import script.library.trial;
import script.library.utils;
import script.obj_id;

import java.util.Vector;

public class banner_buff_manager extends script.base_script
{
    public banner_buff_manager()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        trial.cleanupObject(self);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "buffPlayers", null, 1.0f, false);
        messageTo(self, "handleDeleteSelf", null, 180.0f, false);
        return SCRIPT_CONTINUE;
    }
    public String getBannerBuff(obj_id self) throws InterruptedException
    {
        obj_id player = trial.getParent(self);
        if (!isIdValid(player))
        {
            return null;
        }
        switch (utils.getPlayerProfession(player))
        {
            case utils.COMMANDO:
            return "banner_buff_commando";
            case utils.SMUGGLER:
            return "banner_buff_smuggler";
            case utils.MEDIC:
            return "banner_buff_medic";
            case utils.OFFICER:
            return "banner_buff_officer";
            case utils.SPY:
            return "banner_buff_spy";
            case utils.BOUNTY_HUNTER:
            return "banner_buff_bounty_hunter";
            case utils.FORCE_SENSITIVE:
            return "banner_buff_force_sensitive";
            case utils.TRADER:
            return "banner_buff_trader";
            case utils.ENTERTAINER:
            return "banner_buff_entertainer";
        }
        return null;
    }
    public int buffPlayers(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] players = trial.getValidPlayersInRadius(self, 30.0f);
        Vector filteredPlayers = new Vector();
        filteredPlayers.setSize(0);
        boolean applyBuff = true;
        int faction = -1;
        if (!hasObjVar(self, "parent.faction"))
        {
            applyBuff = false;
        }
        else 
        {
            faction = getIntObjVar(self, "parent.faction");
        }
        if (players == null || players.length == 0)
        {
            applyBuff = false;
        }
        else 
        {
            for (obj_id player : players) {
                switch (faction) {
                    case 0:
                        if (factions.isRebel(player)) {
                            utils.addElement(filteredPlayers, player);
                        }
                        break;
                    case 1:
                        if (factions.isImperial(player)) {
                            utils.addElement(filteredPlayers, player);
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
        String buffToApply = getBannerBuff(self);
        if (buffToApply == null)
        {
            applyBuff = false;
        }
        if (applyBuff)
        {
            buff.applyBuff(players, buffToApply);
        }
        messageTo(self, "buffPlayers", null, 5.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int handleDeleteSelf(obj_id self, dictionary params) throws InterruptedException
    {
        trial.cleanupObject(self);
        return SCRIPT_CONTINUE;
    }
}
