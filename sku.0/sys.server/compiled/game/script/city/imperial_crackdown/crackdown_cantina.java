package script.city.imperial_crackdown;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.chat;
import script.library.ai_lib;
import script.library.create;
import script.library.factions;
import script.library.gcw;
import script.library.locations;

public class crackdown_cantina extends script.base_script
{
    public crackdown_cantina()
    {
    }
    public static String CONVO = "npc_reaction/imperial_crackdown_cantina";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        obj_id foyer = getCellId(self, "foyer1");
        attachScript(foyer, "city.imperial_crackdown.door_check");
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "resetCrackDown", null, 1, false);
        if (!hasScript(self, "systems.gcw.gcw_data_updater"))
        {
            attachScript(self, "systems.gcw.gcw_data_updater");
        }
        return SCRIPT_CONTINUE;
    }
    public int resetCrackDown(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "crackdownHappened"))
        {
            removeObjVar(self, "crackdownHappened");
        }
        return SCRIPT_CONTINUE;
    }
    public int doTroubleCheck(obj_id self, dictionary params) throws InterruptedException
    {
        removeObjVar(self, "crackdownHappened");
        obj_id player = params.getObjId("player");
        if (hasObjVar(self, "crackdownHappened"))
        {
            if (hasObjVar(self, "checkingForTrouble"))
            {
                removeObjVar(self, "checkingForTrouble");
            }
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "crackdownTest"))
        {
            messageTo(self, "createTrouble", null, 15, false);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            String trouble = getConfigSetting("Imperial Crackdown", "troubleChance");
            int chanceOfTrouble = utils.stringToInt(trouble);
            if (chanceOfTrouble == 0 || chanceOfTrouble == -1)
            {
                chanceOfTrouble = 20;
            }
            int rollForTrouble = rand(1, 100);
            if (rollForTrouble < chanceOfTrouble)
            {
                messageTo(self, "createTrouble", null, 15, false);
                return SCRIPT_CONTINUE;
            }
            else 
            {
                if (hasObjVar(self, "checkingForTrouble"))
                {
                    removeObjVar(self, "checkingForTrouble");
                }
                return SCRIPT_CONTINUE;
            }
        }
    }
    public int createTrouble(obj_id self, dictionary params) throws InterruptedException
    {
        setObjVar(self, "crackdownHappened", 1);
        if (hasObjVar(self, "checkingForTrouble"))
        {
            removeObjVar(self, "checkingForTrouble");
        }
        obj_id foyer = getCellId(self, "foyer1");
        location here = getLocation(self);
        String planet = here.area;
        String city = locations.getCityName(here);
        float imp_r = gcw.getImperialRatio(self);
        float reb_r = gcw.getRebelRatio(self);
        if (city != null)
        {
            if ((city.equals("bestine")) || (city.equals("bela_vistal")) || (city.equals("deeja_peak")))
            {
                imp_r = 1.f;
                reb_r = 0.f;
            }
            else if ((city.equals("anchorhead")) || (city.equals("vreni_island")) || (city.equals("moenia")))
            {
                imp_r = 0.f;
                reb_r = 1.f;
            }
        }
        String troublemaker = null;
        String faction = null;
        if (imp_r >= reb_r)
        {
            troublemaker = "crackdown_stormtrooper";
            faction = "imperial";
        }
        else 
        {
            troublemaker = "crackdown_rebel_command_security_guard";
            faction = "rebel";
        }
        location impLoc = new location(48.13f, .1f, 2.47f, planet, foyer);
        obj_id trooper = create.object(troublemaker, impLoc);
        setObjVar(trooper, "string_faction", faction);
        string_id call = new string_id(CONVO, "call_in_" + faction);
        chat.chat(trooper, call);
        setObjVar(trooper, "crackdown.cantina", self);
        attachScript(trooper, "city.imperial_crackdown.imperial_trouble");
        messageTo(trooper, "startMoving", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int createFight(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id foyer = getCellId(self, "foyer1");
        obj_id cantina = getCellId(self, "cantina");
        location impLoc = new location(48.13f, .1f, 2.47f, "naboo", foyer);
        location fight = new location(22.37f, -0.89f, 0.98f, "naboo", cantina);
        obj_id trooper = create.object("crackdown_stormtrooper", impLoc);
        string_id call = new string_id(CONVO, "call_in");
        chat.chat(trooper, call);
        pathTo(trooper, fight);
        return SCRIPT_CONTINUE;
    }
    public int sendBackup(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id foyer = getCellId(self, "foyer1");
        obj_id mainroom = getCellId(self, "cantina");
        obj_id foyer2 = getCellId(self, "back_hallway");
        obj_id attacker = params.getObjId("attacker");
        location where = params.getLocation("locale");
        if (attacker == null || where == null)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id victim = params.getObjId("me");
        String faction = getStringObjVar(victim, "string_faction");
        String troublemaker = null;
        if (faction.equals("rebel"))
        {
            troublemaker = "crackdown_rebel_command_security_guard";
        }
        else 
        {
            troublemaker = "crackdown_stormtrooper";
        }
        String planet = where.area;
        location impLoc = new location(48.13f, .1f, 2.47f, planet, foyer);
        location impLoc2 = new location(-14.38f, 0, 14.29f, planet, mainroom);
        location impLoc3 = new location(-17.8f, 0, -11.53f, planet, foyer2);
        obj_id backupOne = create.object(troublemaker, impLoc);
        attachScript(backupOne, "city.imperial_crackdown.imperial_backup");
        setObjVar(backupOne, "whereToFight", where);
        setObjVar(backupOne, "whoToFight", attacker);
        setObjVar(backupOne, "string_faction", faction);
        obj_id backupTwo = create.object(troublemaker, impLoc2);
        attachScript(backupTwo, "city.imperial_crackdown.imperial_backup");
        setObjVar(backupTwo, "whereToFight", where);
        setObjVar(backupTwo, "whoToFight", attacker);
        setObjVar(backupTwo, "string_faction", faction);
        obj_id backupThree = create.object(troublemaker, impLoc3);
        attachScript(backupThree, "city.imperial_crackdown.imperial_backup");
        setObjVar(backupThree, "whereToFight", where);
        setObjVar(backupThree, "whoToFight", attacker);
        setObjVar(backupThree, "string_faction", faction);
        return SCRIPT_CONTINUE;
    }
}
