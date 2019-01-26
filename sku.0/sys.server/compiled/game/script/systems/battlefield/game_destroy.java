package script.systems.battlefield;

import script.dictionary;
import script.library.battlefield;
import script.library.factions;
import script.library.sui;
import script.library.utils;
import script.obj_id;
import script.region;

import java.util.Vector;

public class game_destroy extends script.base_script
{
    public game_destroy()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "battlefield.game_destroy::OnAttach -- " + self);
        obj_id[] crit_objs = battlefield.getGameCriticalObjects(self);
        if (crit_objs == null || crit_objs.length < 1)
        {
            LOG("LOG_CHANNEL", "battlefield.game_destroy::OnAttach -- (" + self + ") can't create a game with no allowed faction.");
            return SCRIPT_CONTINUE;
        }
        region bf = battlefield.getBattlefield(self);
        String[] allowed_factions = battlefield.getFactionsAllowed(self);
        String[] ai_factions = battlefield.getAIFactionsAllowed(self);
        String[] all_factions = battlefield.getAllFactionsAllowed(self);
        LOG("LOG_CHANNEL", "all_factions ->" + all_factions);
        if (all_factions != null)
        {
            for (int i = 0; i < all_factions.length; i++)
            {
                LOG("LOG_CHANNEL", "...[" + i + "] ->" + all_factions[i]);
            }
        }
        if (allowed_factions == null || all_factions == null)
        {
            LOG("LOG_CHANNEL", "battlefield.game_destroy::OnAttach -- (" + self + ") allowed_factions/all_factions is null.");
            return SCRIPT_CONTINUE;
        }
        if (battlefield.isBattlefieldPvP(bf))
        {
            if (allowed_factions.length < 2)
            {
                LOG("LOG_CHANNEL", "battlefield.game_destroy::OnAttach -- (" + self + ") can't create a pvp destroy game with only one allowed faction.");
                return SCRIPT_CONTINUE;
            }
        }
        else if (battlefield.isBattlefieldPvE(bf))
        {
            if (allowed_factions.length != 1)
            {
                LOG("LOG_CHANNEL", "battlefield.game_destroy::OnAttach -- (" + self + ") can't create a pve destroy game with more than one faction.");
                return SCRIPT_CONTINUE;
            }
            else if (ai_factions.length < 1)
            {
                LOG("LOG_CHANNEL", "battlefield.game_destroy::OnAttach -- (" + self + ") can't create a pve destroy game without any ai factions.");
                return SCRIPT_CONTINUE;
            }
        }
        for (String all_faction : all_factions) {
            int all_faction_id = battlefield.getFactionId(all_faction);
            Vector faction_list = new Vector();
            faction_list.setSize(0);
            for (obj_id crit_obj : crit_objs) {
                int obj_faction_id = pvpBattlefieldGetFaction(crit_obj, bf);
                if (obj_faction_id == all_faction_id) {
                    faction_list = utils.addElement(faction_list, crit_obj);
                    String obj_name = all_faction + " Power Generator #" + faction_list.size();
                    setName(crit_obj, obj_name);
                }
            }
            String objVar_name = battlefield.VAR_GAME + "." + all_faction + "_destroy";
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
            float standing = 0.0f;
            if (isPlayer(victim))
            {
                standing = 5.0f;
            }
            else 
            {
                standing = 1.0f;
            }
            region bf = battlefield.getBattlefield(self);
            int killer_faction_id = pvpBattlefieldGetFaction(killer, bf);
            String killer_faction = factions.getFactionNameByHashCode(killer_faction_id);
            dictionary new_params = new dictionary();
            new_params.put("faction", killer_faction);
            new_params.put("battlefield", self);
            new_params.put("standing", standing);
            messageTo(killer, "msgGrantFactionStanding", new_params, 0.0f, true);
        }
        return SCRIPT_CONTINUE;
    }
    public int msgAddPlayerToBattlefield(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "battlefield.game_destroy::msgAddPlayerToBattlefield -- " + params);
        obj_id player = params.getObjId("player");
        String faction = params.getString("faction");
        if (!player.isLoaded())
        {
            return SCRIPT_CONTINUE;
        }
        String[] allowed_factions = battlefield.getAllFactionsAllowed(self);
        for (String allowed_faction : allowed_factions) {
            String objVar_name = battlefield.VAR_GAME + "." + allowed_faction + "_destroy";
            if (hasObjVar(self, objVar_name)) {
                obj_id[] destroy_objs = getObjIdArrayObjVar(self, objVar_name);
                for (obj_id destroy_obj : destroy_objs) {
                    if (destroy_obj.isLoaded()) {
                        battlefield.addBattlefieldWaypoint(player, destroy_obj);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int msgEliminateFaction(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "battlefield.game_destroy::msgEliminateFaction");
        String faction = params.getString("faction");
        battlefield.eliminateFaction(self, faction);
        String[] factions_left = battlefield.getAllFactionsRemaining(self);
        for (String s : factions_left) {
            LOG("LOG_CHANNEL", "factions_left ->" + s);
        }
        LOG("LOG_CHANNEL", "factions_left ->" + factions_left.length);
        if (factions_left == null || factions_left.length == 0)
        {
            declareWinner(self, "NONE");
        }
        else if (factions_left.length == 1)
        {
            declareWinner(self, factions_left[0]);
        }
        return SCRIPT_CONTINUE;
    }
    public int msgTimeExpired(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "battlefield.game_destroy::msgGameEnd");
        String[] factions = battlefield.getAllFactionsRemaining(self);
        if (factions.length == 1)
        {
            declareWinner(self, factions[0]);
        }
        else 
        {
            declareWinner(self, "NONE");
        }
        return SCRIPT_CONTINUE;
    }
    public int msgGameStats(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "battlefield.game_destroy::msgGameStats");
        obj_id player = params.getObjId("player");
        if (player == null || !player.isLoaded())
        {
            return SCRIPT_CONTINUE;
        }
        Vector dsrc = new Vector();
        dsrc.setSize(0);
        String[] factions_remaining = battlefield.getAllFactionsRemaining(self);
        if (factions_remaining != null)
        {
            dsrc = utils.addElement(dsrc, "Objectives Remaining");
            for (String s : factions_remaining) {
                String objVar_name = battlefield.VAR_GAME + "." + s + "_destroy";
                obj_id[] destroy_objs = getObjIdArrayObjVar(self, objVar_name);
                if (destroy_objs != null) {
                    dsrc = utils.addElement(dsrc, "   " + s);
                    for (obj_id destroy_obj : destroy_objs) {
                        float hitpoints = getHitpoints(destroy_obj);
                        float maxHitpoints = getMaxHitpoints(destroy_obj);
                        int percent = (int) ((hitpoints / maxHitpoints) * 100.0f);
                        String name = getName(destroy_obj);
                        dsrc = utils.addElement(dsrc, "      " + name + " at " + percent + "%");
                    }
                }
            }
        }
        dsrc = utils.addElement(dsrc, "Kills/Deaths");
        String[] factions_allowed = battlefield.getAllFactionsAllowed(self);
        if (factions_allowed != null)
        {
            for (String s : factions_allowed) {
                dsrc = utils.addElement(dsrc, "   " + s);
                int kills = battlefield.getFactionKills(self, s);
                int deaths = battlefield.getFactionDeaths(self, s);
                dsrc = utils.addElement(dsrc, "      " + "Kills: " + kills);
                dsrc = utils.addElement(dsrc, "      " + "Deaths: " + deaths);
            }
        }
        sui.listbox(player, "Battlefield Statistics", "Objective: Destroy", dsrc);
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
            for (String s : factions_allowed) {
                int kills = battlefield.getFactionKills(master_object, s);
                int deaths = battlefield.getFactionDeaths(master_object, s);
                setObjVar(master_object, battlefield.VAR_STAT_KILLS + s, kills);
                setObjVar(master_object, battlefield.VAR_STAT_DEATHS + s, deaths);
            }
        }
        if (faction.equals("NONE"))
        {
            battlefield.sendBattlefieldMessage(master_object, "The battle is over.  It has ended in a stalemate!");
        }
        else 
        {
            battlefield.sendBattlefieldMessage(master_object, "The battle is over. The " + faction + " faction is victorious!");
            if (battlefield.isPlayerFaction(master_object, faction))
            {
                if (total_time < 900)
                {
                    battlefield.sendBattlefieldMessage(master_object, "There are no rewards for such an easy victory.");
                    battlefield.endBattlefield(master_object);
                    return;
                }
                if (participants != null)
                {
                    for (obj_id participant : participants) {
                        int time_spent = battlefield.getTimeSpentInBattlefield(participant);
                        float percent_time = (float) time_spent / game_duration;
                        LOG("LOG_CHANNEL", "battlefield.game_destroy::declareWinner -- " + participant + " at " + percent_time + "%");
                        if (percent_time > 0.1) {
                            dictionary params = new dictionary();
                            params.put("faction", faction);
                            params.put("battlefield", master_object);
                            params.put("standing", 25.0f);
                            messageTo(participant, "msgGrantFactionStanding", params, 0.0f, true);
                        }
                    }
                }
            }
        }
        battlefield.endBattlefield(master_object);
        return;
    }
}
