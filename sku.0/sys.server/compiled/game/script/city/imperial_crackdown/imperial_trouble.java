package script.city.imperial_crackdown;

import script.dictionary;
import script.library.*;
import script.location;
import script.obj_id;
import script.string_id;

public class imperial_trouble extends script.base_script
{
    public imperial_trouble()
    {
    }
    public static String CONVO = "npc_reaction/imperial_crackdown_cantina";
    public int startMoving(obj_id self, dictionary params) throws InterruptedException
    {
        location fight = new location(22.37f, -0.89f, 0.98f, getLocation(self).area, getCellId(getTopMostContainer(self), "cantina"));
        ai_lib.aiPathTo(self, fight);
        addLocationTarget("harass", fight, 1);
        return SCRIPT_CONTINUE;
    }
    public String getFactionName(obj_id self) throws InterruptedException
    {
        return getStringObjVar(self, "string_faction");
    }
    public int OnArrivedAtLocation(obj_id self, String name) throws InterruptedException
    {
        if (name.equals("harass"))
        {
            chat.chat(self, new string_id(CONVO, "rebel_scum_" + getFactionName(self)));
            messageTo(self, "startTrouble", null, 7, false);
        }
        if (name.equals("trouble"))
        {
            chat.chat(self, new string_id(CONVO, "sudden_moves_" + getFactionName(self)));
            faceTo(self, getObjIdObjVar(self, "harrassing"));
            messageTo(self, "continueTrouble", null, 7, false);
        }
        if (name.equals("done"))
        {
            chat.chat(self, new string_id(CONVO, "back_home"));
            messageTo(self, "leaveCantina", null, 7, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnSawAttack(obj_id self, obj_id defender, obj_id[] attackers) throws InterruptedException
    {
        if (defender == self)
        {
            if (!hasObjVar(self, "calledforbackup"))
            {
                chat.chat(self, new string_id(CONVO, "back_up"));
                dictionary fighters = new dictionary();
                fighters.put("attacker", attackers[0]);
                fighters.put("locale", getLocation(self));
                fighters.put("me", self);
                messageTo(getTopMostContainer(self), "sendBackup", fighters, 2, false);
                setObjVar(self, "calledforbackup", 1);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int startTrouble(obj_id self, dictionary params) throws InterruptedException
    {
        location here = getLocation(self);
        obj_id[] players = getAllPlayers(here, 20);
        int numPlayers = players.length;
        if (numPlayers < 1)
        {
            chat.chat(self, new string_id(CONVO, "no_one_here_" + getFactionName(self)));
            messageTo(self, "finishTrouble", null, 5, false);
            return SCRIPT_CONTINUE;
        }
        else if (numPlayers == 1)
        {
            chat.chat(self, new string_id(CONVO, "only_one_here_" + getFactionName(self)));
            setObjVar(self, "harassing", players[0]);
            messageTo(self, "harassPlayer", null, 12, false);
        }
        else 
        {
            String harassFaction;
            for (obj_id thisPlayer : players) {
                if (factions.isCovert(thisPlayer)) {
                    if (getFactionName(self).equals("imperial")) {
                        harassFaction = "Rebel";
                    } else {
                        harassFaction = "Imperial";
                    }
                    if (factions.getFaction(thisPlayer).equals(harassFaction)) {
                        chat.chat(self, new string_id(CONVO, "harass_guy_" + getFactionName(self)));
                        setObjVar(self, "harassing", thisPlayer);
                        messageTo(self, "harassPlayer", null, 10, false);
                    }
                }
            }
        }
        if (!hasObjVar(self, "harassing"))
        {
            chat.chat(self, new string_id(CONVO, "harass_guy_" + getFactionName(self)));
            setObjVar(self, "harassing", players[0]);
            messageTo(self, "harassPlayer", null, 10, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int harassPlayer(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "trouble"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id playerToHarass = getObjIdObjVar(self, "harassing");
        faceTo(self, playerToHarass);
        chat.chat(self, new string_id(CONVO, "looking_at_" + getFactionName(self)));
        setObjVar(self, "trouble", getLocation(playerToHarass));
        messageTo(self, "moveOver", null, 3, false);
        return SCRIPT_CONTINUE;
    }
    public int moveOver(obj_id self, dictionary params) throws InterruptedException
    {
        chat.chat(self, new string_id(CONVO, "checking_out_" + getFactionName(self)));
        location trouble = getLocationObjVar(self, "trouble");
        trouble.x = trouble.x + 1;
        ai_lib.aiPathTo(self, trouble);
        addLocationTarget("trouble", trouble, 1);
        return SCRIPT_CONTINUE;
    }
    public int continueTrouble(obj_id self, dictionary params) throws InterruptedException
    {
        if (ai_lib.isInCombat(self))
        {
            return SCRIPT_CONTINUE;
        }
        int talked = utils.getIntScriptVar(self, "talked");
        if (talked > 6)
        {
            messageTo(self, "finishTrouble", null, 15, false);
            return SCRIPT_CONTINUE;
        }
        faceTo(self, getObjIdObjVar(self, "harassing"));
        chat.chat(self, getSomethingToSay(self));
        messageTo(self, "continueTrouble", null, 18, false);
        utils.setScriptVar(self, "talked", talked + 1);
        return SCRIPT_CONTINUE;
    }
    public string_id getSomethingToSay(obj_id self) throws InterruptedException
    {
        int which = rand(1, 15);
        utils.setScriptVar(self, "lastSaid", which);
        return new string_id(CONVO, "harass_" + which + "_" + getFactionName(self));
    }
    public int finishTrouble(obj_id self, dictionary params) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, "Final"))
        {
            int chance = rand(1, 4);
            if (chance == 1)
            {
                if (checkForFinalFight(self))
                {
                    messageTo(self, "finishTrouble", null, 300, false);
                    utils.setScriptVar(self, "Final", 1);
                    return SCRIPT_CONTINUE;
                }
            }
        }
        chat.chat(self, new string_id(CONVO, "rebel_cowards_" + getFactionName(self)));
        obj_id foyer = getCellId(getTopMostContainer(self), "foyer1");
        location impLoc = new location(47.02f, .1f, -2.93f, getLocation(self).area, foyer);
        ai_lib.aiPathTo(self, impLoc);
        addLocationTarget("done", impLoc, 1);
        messageTo(self, "handleBadLeaving", null, 60, false);
        return SCRIPT_CONTINUE;
    }
    public int leaveCantina(obj_id self, dictionary params) throws InterruptedException
    {
        if (ai_lib.isInCombat(self))
        {
            messageTo(self, "leaveCantina", null, 60, false);
            return SCRIPT_CONTINUE;
        }
        messageTo(getObjIdObjVar(self, "crackdown.cantina"), "resetCrackDown", null, 2, false);
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int handleBadLeaving(obj_id self, dictionary params) throws InterruptedException
    {
        string_id back = new string_id(CONVO, "back_home");
        chat.chat(self, back);
        messageTo(self, "leaveCantina", null, 7, false);
        return SCRIPT_CONTINUE;
    }
    public int postCombatPathHome(obj_id self, dictionary params) throws InterruptedException
    {
        messageTo(self, "leaveCantina", null, 60, false);
        return SCRIPT_CONTINUE;
    }
    public boolean checkForFinalFight(obj_id self) throws InterruptedException
    {
        location here = getLocation(self);
        obj_id[] players = getAllPlayers(here, 20);
        if (players == null)
        {
            return false;
        }
        int numPlayers = players.length;
        if (numPlayers < 1)
        {
            return false;
        }
        String harassFaction;
        for (obj_id player : players) {
            if (isIdValid(player)) {
                if (factions.isCovert(player)) {
                    if (getFactionName(self).equals("imperial")) {
                        harassFaction = "Rebel";
                    } else {
                        harassFaction = "Imperial";
                    }
                    if (factions.getFaction(player).equals(harassFaction)) {
                        if (!checkForPercentage(player)) {
                            if (getLevel(player) > 10) {
                                chat.chat(self, new string_id(CONVO, "attacking_" + getFactionName(self)));
                                if (!isJedi(player) && factions.isOnLeave(player)) {
                                    pvpMakeCovert(player);
                                } else if (isJedi(player)) {
                                    jedi.doJediTEF(player);
                                }
                                startCombat(self, player);
                                chat.chat(self, new string_id(CONVO, "back_up"));
                                dictionary fighters = new dictionary();
                                fighters.put("attacker", player);
                                fighters.put("locale", getLocation(self));
                                fighters.put("me", self);
                                messageTo(getTopMostContainer(self), "sendBackup", fighters, 2, false);
                                setObjVar(self, "calledforbackup", 1);
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    public boolean checkForPercentage(obj_id player) throws InterruptedException
    {
        boolean yesNo = true;
        obj_id[] members = getGroupMemberIds(player);
        if (members == null)
        {
            return ai_lib.checkForSmuggler(player);
        }
        int numInGroup = members.length;
        if (numInGroup == 0)
        {
            return false;
        }
        for (obj_id thisMember : members) {
            if (!ai_lib.checkForSmuggler(thisMember)) {
                yesNo = false;
            }
        }
        return yesNo;
    }
    public int getSmugglerPercentage(obj_id thisMember) throws InterruptedException
    {
        int chance = 90;
        if (hasSkill(thisMember, "class_smuggler_phase1_novice"))
        {
            chance = 80;
        }
        if (hasSkill(thisMember, "class_smuggler_phase2_novice"))
        {
            chance = 60;
        }
        if (hasSkill(thisMember, "class_smuggler_phase3_novice"))
        {
            chance = 40;
        }
        if (hasSkill(thisMember, "class_smuggler_phase4_novice"))
        {
            chance = 20;
        }
        return chance;
    }
}
