package script.city.imperial_crackdown;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.ai.ai_combat;
import script.library.skill;
import script.library.factions;
import script.library.chat;
import script.library.utils;
import script.library.ai_lib;
import script.library.jedi;

public class imperial_trouble extends script.base_script
{
    public imperial_trouble()
    {
    }
    public static String CONVO = "npc_reaction/imperial_crackdown_cantina";
    public int startMoving(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id bldg = getTopMostContainer(self);
        obj_id cantina = getCellId(bldg, "cantina");
        location here = getLocation(self);
        String planet = here.area;
        location fight = new location(22.37f, -0.89f, 0.98f, planet, cantina);
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
            string_id scum = new string_id(CONVO, "rebel_scum_" + getFactionName(self));
            chat.chat(self, scum);
            messageTo(self, "startTrouble", null, 7, false);
        }
        if (name.equals("trouble"))
        {
            string_id move = new string_id(CONVO, "sudden_moves_" + getFactionName(self));
            chat.chat(self, move);
            obj_id troublemaker = getObjIdObjVar(self, "harrassing");
            faceTo(self, troublemaker);
            messageTo(self, "continueTrouble", null, 7, false);
        }
        if (name.equals("done"))
        {
            string_id back = new string_id(CONVO, "back_home");
            chat.chat(self, back);
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
                string_id help = new string_id(CONVO, "back_up");
                chat.chat(self, help);
                obj_id bldg = getTopMostContainer(self);
                dictionary fighters = new dictionary();
                location locale = getLocation(self);
                fighters.put("attacker", attackers[0]);
                fighters.put("locale", locale);
                fighters.put("me", self);
                messageTo(bldg, "sendBackup", fighters, 2, false);
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
            string_id none = new string_id(CONVO, "no_one_here_" + getFactionName(self));
            chat.chat(self, none);
            messageTo(self, "finishTrouble", null, 5, false);
            return SCRIPT_CONTINUE;
        }
        else if (numPlayers == 1)
        {
            obj_id harassable = players[0];
            String name = getFirstName(harassable);
            string_id only = new string_id(CONVO, "only_one_here_" + getFactionName(self));
            chat.chat(self, only);
            setObjVar(self, "harassing", harassable);
            messageTo(self, "harassPlayer", null, 12, false);
        }
        else 
        {
            for (int i = 0; i < numPlayers; i++)
            {
                obj_id thisPlayer = players[i];
                if (factions.isCovert(thisPlayer))
                {
                    String playerFaction = factions.getFaction(thisPlayer);
                    String harassFaction;
                    if (getFactionName(self) == "imperial")
                    {
                        harassFaction = "Rebel";
                    }
                    else 
                    {
                        harassFaction = "Imperial";
                    }
                    if (playerFaction.equals(harassFaction))
                    {
                        string_id diss = new string_id(CONVO, "harass_guy_" + getFactionName(self));
                        chat.chat(self, diss);
                        setObjVar(self, "harassing", thisPlayer);
                        messageTo(self, "harassPlayer", null, 10, false);
                    }
                }
            }
        }
        if (!hasObjVar(self, "harassing"))
        {
            string_id diss = new string_id(CONVO, "harass_guy_" + getFactionName(self));
            chat.chat(self, diss);
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
        string_id look = new string_id(CONVO, "looking_at_" + getFactionName(self));
        chat.chat(self, look);
        location trouble = getLocation(playerToHarass);
        setObjVar(self, "trouble", trouble);
        messageTo(self, "moveOver", null, 3, false);
        return SCRIPT_CONTINUE;
    }
    public int moveOver(obj_id self, dictionary params) throws InterruptedException
    {
        string_id plan = new string_id(CONVO, "checking_out_" + getFactionName(self));
        chat.chat(self, plan);
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
        obj_id troublemaker = getObjIdObjVar(self, "harassing");
        string_id something = getSomethingToSay(self);
        faceTo(self, troublemaker);
        chat.chat(self, something);
        messageTo(self, "continueTrouble", null, 18, false);
        utils.setScriptVar(self, "talked", talked + 1);
        return SCRIPT_CONTINUE;
    }
    public string_id getSomethingToSay(obj_id self) throws InterruptedException
    {
        int lastSaid = utils.getIntScriptVar(self, "lastSaid");
        int which = rand(1, 15);
        if (which == lastSaid)
        {
            if (which == 15)
            {
                which = 14;
            }
            else 
            {
                which = which + 1;
            }
        }
        string_id something = new string_id(CONVO, "harass_" + which + "_" + getFactionName(self));
        utils.setScriptVar(self, "lastSaid", which);
        return something;
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
        string_id hide = new string_id(CONVO, "rebel_cowards_" + getFactionName(self));
        chat.chat(self, hide);
        obj_id top = getTopMostContainer(self);
        obj_id foyer = getCellId(top, "foyer1");
        location here = getLocation(self);
        String planet = here.area;
        location impLoc = new location(47.02f, .1f, -2.93f, planet, foyer);
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
        obj_id cantina = getObjIdObjVar(self, "crackdown.cantina");
        messageTo(cantina, "resetCrackDown", null, 2, false);
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
        for (int i = 0; i < numPlayers; i++)
        {
            if (isIdValid(players[i]))
            {
                obj_id thisPlayer = players[i];
                if (factions.isCovert(thisPlayer))
                {
                    String playerFaction = factions.getFaction(thisPlayer);
                    String harassFaction;
                    if (getFactionName(self) == "imperial")
                    {
                        harassFaction = "Rebel";
                    }
                    else 
                    {
                        harassFaction = "Imperial";
                    }
                    if (playerFaction.equals(harassFaction))
                    {
                        if (checkForPercentage(thisPlayer) == false)
                        {
                            int playerLevel = getLevel(thisPlayer);
                            if (playerLevel > 10)
                            {
                                string_id diss = new string_id(CONVO, "attacking_" + getFactionName(self));
                                chat.chat(self, diss);
                                if (!isJedi(thisPlayer) && factions.isOnLeave(thisPlayer))
                                {
                                    pvpMakeCovert(thisPlayer);
                                }
                                else if (isJedi(thisPlayer))
                                {
                                    jedi.doJediTEF(thisPlayer);
                                }
                                startCombat(self, thisPlayer);
                                string_id help = new string_id(CONVO, "back_up");
                                chat.chat(self, help);
                                obj_id bldg = getTopMostContainer(self);
                                dictionary fighters = new dictionary();
                                location locale = getLocation(self);
                                fighters.put("attacker", thisPlayer);
                                fighters.put("locale", locale);
                                fighters.put("me", self);
                                messageTo(bldg, "sendBackup", fighters, 2, false);
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
            boolean smuggler = ai_lib.checkForSmuggler(player);
            return smuggler;
        }
        int numInGroup = members.length;
        if (numInGroup == 0)
        {
            return false;
        }
        for (int i = 0; i < numInGroup; i++)
        {
            obj_id thisMember = members[i];
            if (ai_lib.checkForSmuggler(thisMember) == false)
            {
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
