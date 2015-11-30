package script.systems.battlefield;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.battlefield;
import script.library.factions;
import script.library.player_structure;
import script.library.sui;
import script.library.utils;

public class game_assault extends script.base_script
{
    public game_assault()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "battlefield.game_assault::OnAttach");
        obj_id[] crit_objs = battlefield.getGameCriticalObjects(self);
        if (crit_objs == null || crit_objs.length < 1)
        {
            LOG("LOG_CHANNEL", "battlefield.game_assault::OnAttach -- (" + self + ") can't create a game with no destroy objectives.");
            return SCRIPT_CONTINUE;
        }
        region bf = battlefield.getBattlefield(self);
        String[] allowed_factions = battlefield.getFactionsAllowed(self);
        String[] ai_factions = battlefield.getAIFactionsAllowed(self);
        String[] all_factions = battlefield.getAllFactionsAllowed(self);
        if (all_factions != null)
        {
            for (int i = 0; i < all_factions.length; i++)
            {
                LOG("LOG_CHANNEL", "...[" + i + "] ->" + all_factions[i]);
            }
        }
        if (allowed_factions == null || all_factions == null)
        {
            LOG("LOG_CHANNEL", "battlefield.game_assault::OnAttach -- (" + self + ") allowed_factions/all_factions is null.");
            return SCRIPT_CONTINUE;
        }
        if (battlefield.isBattlefieldPvP(bf))
        {
            LOG("LOG_CHANNEL", "battlefield.game_assault::OnAttach -- (" + self + ") Assault games cannot be played as PvP.");
            return SCRIPT_CONTINUE;
        }
        else if (battlefield.isBattlefieldPvE(bf))
        {
            if (allowed_factions.length != 1)
            {
                LOG("LOG_CHANNEL", "battlefield.game_assault::OnAttach -- (" + self + ") can't create a pve destroy game with more than one player faction.");
                return SCRIPT_CONTINUE;
            }
            else if (ai_factions.length < 1)
            {
                LOG("LOG_CHANNEL", "battlefield.game_assault::OnAttach -- (" + self + ") can't create a pve destroy game without any ai factions.");
                return SCRIPT_CONTINUE;
            }
        }
        for (int i = 0; i < ai_factions.length; i++)
        {
            int ai_faction_id = battlefield.getFactionId(ai_factions[i]);
            Vector faction_list = new Vector();
            faction_list.setSize(0);
            for (int j = 0; j < crit_objs.length; j++)
            {
                int obj_faction_id = pvpBattlefieldGetFaction(crit_objs[j], bf);
                if (obj_faction_id == ai_faction_id)
                {
                    faction_list = utils.addElement(faction_list, crit_objs[j]);
                    String obj_name = ai_factions[i] + " Power Generator #" + faction_list.size();
                    setName(crit_objs[j], obj_name);
                }
            }
            String objVar_name = battlefield.VAR_GAME + "." + ai_factions[i] + "_destroy";
            setObjVar(self, objVar_name, faction_list);
        }
        return SCRIPT_CONTINUE;
    }
    public int msgGameScriptPulse(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int msgBattlefieldKill(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id killer = params.getObjId("killer");
        obj_id victim = params.getObjId("victim");
        if (isPlayer(killer))
        {
            region bf = battlefield.getBattlefield(self);
            int killer_faction_id = pvpBattlefieldGetFaction(killer, bf);
            String killer_faction = factions.getFactionNameByHashCode(killer_faction_id);
            dictionary new_params = new dictionary();
            new_params.put("faction", killer_faction);
            new_params.put("battlefield", self);
            new_params.put("standing", 1.0f);
            messageTo(killer, "msgGrantFactionStanding", new_params, 0.0f, true);
        }
        return SCRIPT_CONTINUE;
    }
    public int msgAddPlayerToBattlefield(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "battlefield.game_assault::msgAddPlayerToBattlefield -- " + params);
        obj_id player = params.getObjId("player");
        String faction = params.getString("faction");
        if (!player.isLoaded())
        {
            return SCRIPT_CONTINUE;
        }
        String[] allowed_factions = battlefield.getAIFactionsAllowed(self);
        if (allowed_factions == null)
        {
            LOG("LOG_CHANNEL", "game_assault::msgAddPlayerToBattlefield -- AI factions is null." + self + "(" + battlefield.getBattlefieldName(self) + ")");
            LOG("LOG_CHANNEL", "game_assault::msgAddPlayerToBattlefield -- player: " + player + " faction: " + faction);
            return SCRIPT_CONTINUE;
        }
        for (int i = 0; i < allowed_factions.length; i++)
        {
            String objVar_name = battlefield.VAR_GAME + "." + allowed_factions[i] + "_destroy";
            if (hasObjVar(self, objVar_name))
            {
                obj_id[] destroy_objs = getObjIdArrayObjVar(self, objVar_name);
                for (int j = 0; j < destroy_objs.length; j++)
                {
                    if (destroy_objs[j].isLoaded())
                    {
                        battlefield.addBattlefieldWaypoint(player, destroy_objs[j]);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int msgEliminateFaction(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "battlefield.game_assault::msgEliminateFaction");
        String faction = params.getString("faction");
        battlefield.eliminateFaction(self, faction);
        String[] factions_left = battlefield.getAllFactionsRemaining(self);
        if (factions_left == null || factions_left.length == 0)
        {
            LOG("LOG_CHANNEL", "game_assault::msgEliminateFaction -- factions_left is empty for " + self);
            declareWinner(self, "AI");
        }
        else if (factions_left.length == 1)
        {
            declareWinner(self, factions_left[0]);
        }
        return SCRIPT_CONTINUE;
    }
    public int msgTimeExpired(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "battlefield.game_assault::msgTime");
        String[] factions = battlefield.getAllFactionsRemaining(self);
        if (factions.length == 1)
        {
            declareWinner(self, factions[0]);
        }
        else 
        {
            declareWinner(self, "AI");
        }
        return SCRIPT_CONTINUE;
    }
    public int msgGameStats(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "battlefield.game_assault::msgGameStats");
        obj_id player = params.getObjId("player");
        if (player == null || !player.isLoaded())
        {
            return SCRIPT_CONTINUE;
        }
        Vector dsrc = new Vector();
        dsrc.setSize(0);
        String[] factions_remaining = battlefield.getAIFactionsRemaining(self);
        if (factions_remaining != null)
        {
            dsrc = utils.addElement(dsrc, "Objectives Remaining");
            for (int i = 0; i < factions_remaining.length; i++)
            {
                String objVar_name = battlefield.VAR_GAME + "." + factions_remaining[i] + "_destroy";
                obj_id[] destroy_objs = getObjIdArrayObjVar(self, objVar_name);
                if (destroy_objs != null)
                {
                    dsrc = utils.addElement(dsrc, "   " + factions_remaining[i]);
                    for (int j = 0; j < destroy_objs.length; j++)
                    {
                        float hitpoints = (float)getHitpoints(destroy_objs[j]);
                        float maxHitpoints = (float)getMaxHitpoints(destroy_objs[j]);
                        int percent = (int)((hitpoints / maxHitpoints) * 100.0f);
                        String name = getName(destroy_objs[j]);
                        dsrc = utils.addElement(dsrc, "      " + name + " at " + percent + "%");
                    }
                }
            }
        }
        dsrc = utils.addElement(dsrc, "Kills/Deaths");
        String[] factions_allowed = battlefield.getAllFactionsAllowed(self);
        for (int i = 0; i < factions_allowed.length; i++)
        {
            dsrc = utils.addElement(dsrc, "   " + factions_allowed[i]);
            int kills = battlefield.getFactionKills(self, factions_allowed[i]);
            int deaths = battlefield.getFactionDeaths(self, factions_allowed[i]);
            dsrc = utils.addElement(dsrc, "      " + "Kills: " + kills);
            dsrc = utils.addElement(dsrc, "      " + "Deaths: " + deaths);
        }
        sui.listbox(player, "Battlefield Statistics", "Objective: Assault", dsrc);
        return SCRIPT_CONTINUE;
    }
    public void declareWinner(obj_id master_object, String faction) throws InterruptedException
    {
        if (hasObjVar(master_object, battlefield.VAR_STAT_ROOT))
        {
            removeObjVar(master_object, battlefield.VAR_STAT_ROOT);
        }
        setObjVar(master_object, battlefield.VAR_STAT_TYPE, battlefield.getBattlefieldGameType(master_object));
        int time_remaining = battlefield.getGameTimeRemaining(master_object);
        int game_duration = battlefield.getGameDuration(master_object);
        int total_time = game_duration - time_remaining;
        setObjVar(master_object, battlefield.VAR_STAT_TIME, total_time);
        setObjVar(master_object, battlefield.VAR_STAT_WINNER, faction);
        obj_id[] participants = battlefield.getParticipantsOnBattlefield(master_object);
        if (participants != null)
        {
            setObjVar(master_object, battlefield.VAR_STAT_PLAYERS, participants.length);
        }
        else 
        {
            setObjVar(master_object, battlefield.VAR_STAT_PLAYERS, 0);
        }
        String[] factions_allowed = battlefield.getAllFactionsAllowed(master_object);
        if (factions_allowed != null)
        {
            for (int i = 0; i < factions_allowed.length; i++)
            {
                int kills = battlefield.getFactionKills(master_object, factions_allowed[i]);
                int deaths = battlefield.getFactionDeaths(master_object, factions_allowed[i]);
                setObjVar(master_object, battlefield.VAR_STAT_KILLS + factions_allowed[i], kills);
                setObjVar(master_object, battlefield.VAR_STAT_DEATHS + factions_allowed[i], deaths);
            }
        }
        if (faction.equals("AI"))
        {
            String[] player_faction = battlefield.getFactionsAllowed(master_object);
            if (player_faction == null)
            {
                LOG("LOG_CHANNEL", "game_assault::declareWinner -- player_faction is null for " + master_object);
            }
            else 
            {
                battlefield.sendBattlefieldMessage(master_object, "The battle is over. The " + player_faction[0] + " faction's assault has failed!");
            }
        }
        else 
        {
            battlefield.sendBattlefieldMessage(master_object, "The battle is over. The " + faction + " faction's assault is successful!");
            if (total_time < 900)
            {
                battlefield.sendBattlefieldMessage(master_object, "There are no rewards for such an easy victory.");
                battlefield.endBattlefield(master_object);
                return;
            }
            if (participants != null)
            {
                for (int i = 0; i < participants.length; i++)
                {
                    int time_spent = battlefield.getTimeSpentInBattlefield(participants[i]);
                    float percent_time = (float)time_spent / (float)game_duration;
                    LOG("LOG_CHANNEL", "battlefield.game_destroy::declareWinner -- " + participants[i] + " at " + percent_time + "%");
                    if (percent_time > .1)
                    {
                        dictionary params = new dictionary();
                        params.put("faction", faction);
                        params.put("battlefield", master_object);
                        params.put("standing", 25.0f);
                        messageTo(participants[i], "msgGrantFactionStanding", params, 0.0f, true);
                    }
                }
            }
        }
        battlefield.endBattlefield(master_object);
        return;
    }
}
