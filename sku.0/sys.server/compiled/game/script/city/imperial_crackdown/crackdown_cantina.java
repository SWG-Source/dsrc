package script.city.imperial_crackdown;

import script.dictionary;
import script.library.*;
import script.location;
import script.obj_id;
import script.string_id;

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
        String troublemaker;
        String faction;
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
        obj_id trooper = create.object(troublemaker, new location(48.13f, .1f, 2.47f, planet, foyer));
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
        obj_id attacker = params.getObjId("attacker");
        location where = params.getLocation("locale");
        if (attacker == null || where == null)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id victim = params.getObjId("me");
        String faction = getStringObjVar(victim, "string_faction");
        String troublemaker;
        if (faction.equals("rebel"))
        {
            troublemaker = "crackdown_rebel_command_security_guard";
        }
        else 
        {
            troublemaker = "crackdown_stormtrooper";
        }
        String planet = where.area;
        location[] impLocs = new location[3];
        impLocs[0] = new location(48.13f, .1f, 2.47f, planet, getCellId(self, "foyer1"));
        impLocs[1] = new location(-14.38f, 0, 14.29f, planet, getCellId(self, "cantina"));
        impLocs[2] = new location(-17.8f, 0, -11.53f, planet, getCellId(self, "back_hallway"));

        obj_id backup;

        for (location impLoc : impLocs) {
            backup = create.object(troublemaker, impLoc);
            attachScript(backup, "city.imperial_crackdown.imperial_backup");
            setObjVar(backup, "whereToFight", where);
            setObjVar(backup, "whoToFight", attacker);
            setObjVar(backup, "string_faction", faction);
        }
        return SCRIPT_CONTINUE;
    }
}
