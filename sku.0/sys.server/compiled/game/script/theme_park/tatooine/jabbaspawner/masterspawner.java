package script.theme_park.tatooine.jabbaspawner;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.ai.ai;
import script.library.ai_lib;
import script.library.create;

public class masterspawner extends script.base_script
{
    public masterspawner()
    {
    }
    public static final String VERSION = "v1.00.00";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        debugServerConsoleMsg(self, "Attached Jabba's Palace Spawn Script");
        spawnEveryone(self);
        messageTo(self, "doGuards", null, 10, true);
        messageTo(self, "doMonks", null, 30, true);
        messageTo(self, "doGating", null, 20, true);
        return SCRIPT_CONTINUE;
    }
    public int OnHearSpeech(obj_id self, obj_id speaker, String text) throws InterruptedException
    {
        if (!hasObjVar(speaker, "gm"))
        {
            return SCRIPT_CONTINUE;
        }
        if (text.equals("throne"))
        {
            spawnThrone(self);
        }
        if (text.equals("celeb"))
        {
            spawnCelebs(self);
        }
        if (text.equals("guard"))
        {
            spawnGuards(self);
        }
        if (text.equals("monk"))
        {
            spawnMonks(self);
        }
        if (text.equals("random"))
        {
            spawnRandom(self);
        }
        if (text.equals("shorties"))
        {
            spawnJawas(self);
        }
        if (text.equals("kill guards"))
        {
            destroyGuards(self);
        }
        if (text.equals("kill celebs"))
        {
            destroyCelebs(self);
        }
        if (text.equals("kill random"))
        {
            destroyRandom(self);
        }
        if (text.equals("kill spiders"))
        {
            destroyMonks(self);
        }
        if (text.equals("kill jawas"))
        {
            destroyJawas(self);
        }
        if (text.equals("droid"))
        {
            spawnDroids(self);
        }
        if (text.equals("kill droids"))
        {
            destroyDroids(self);
        }
        return SCRIPT_CONTINUE;
    }
    public void spawnEveryone(obj_id self) throws InterruptedException
    {
        spawnCelebs(self);
        messageTo(self, "throneRoom", null, 10, true);
        return;
    }
    public int throneRoom(obj_id self, dictionary params) throws InterruptedException
    {
        spawnThrone(self);
        messageTo(self, "guardSpawn", null, 10, true);
        return SCRIPT_CONTINUE;
    }
    public int guardSpawn(obj_id self, dictionary params) throws InterruptedException
    {
        spawnGuards(self);
        messageTo(self, "monkSpawn", null, 10, true);
        return SCRIPT_CONTINUE;
    }
    public int monkSpawn(obj_id self, dictionary params) throws InterruptedException
    {
        spawnMonks(self);
        messageTo(self, "jawaSpawn", null, 10, true);
        return SCRIPT_CONTINUE;
    }
    public int jawaSpawn(obj_id self, dictionary params) throws InterruptedException
    {
        spawnJawas(self);
        messageTo(self, "randomSpawn", null, 10, true);
        return SCRIPT_CONTINUE;
    }
    public int randomSpawn(obj_id self, dictionary params) throws InterruptedException
    {
        spawnRandom(self);
        messageTo(self, "droidSpawn", null, 10, true);
        return SCRIPT_CONTINUE;
    }
    public int droidSpawn(obj_id self, dictionary params) throws InterruptedException
    {
        spawnDroids(self);
        messageTo(self, "prisonerSpawn", null, 10, true);
        return SCRIPT_CONTINUE;
    }
    public int prisonerSpawn(obj_id self, dictionary params) throws InterruptedException
    {
        spawnPrisoners(self);
        return SCRIPT_CONTINUE;
    }
    public void spawnCelebs(obj_id self) throws InterruptedException
    {
        spawnBarada(self);
        spawnKlaatu(self);
        spawnPorcellus(self);
        obj_id malakili = spawnMalakili(self);
        spawnRancor(self, malakili);
        spawnOrgan(self);
        obj_id reelo = spawnReelo(self);
        spawnReeYees(self, reelo);
        spawnJawl(self);
    }
    public void spawnThrone(obj_id self) throws InterruptedException
    {
        obj_id oola = spawnOola(self);
        spawnMax(self, oola);
        obj_id sy = spawnSy(self, oola);
        spawnDroopy(self, oola);
        spawnJabba(self, oola);
        spawnPDroid(self, oola);
        spawnBoba(self, oola);
        spawnCrumb(self, oola);
        spawnBib(self, oola);
    }
    public void spawnGuards(obj_id self) throws InterruptedException
    {
        obj_id ephant = spawnEphant(self);
        obj_id gamGuard1 = spawnGamGuard1(self, ephant);
        spawnGamGuard2(self, gamGuard1);
        spawnGamGuard3(self, gamGuard1);
        spawnGamGuard4(self, ephant);
        obj_id gamGuard6 = spawnGamGuard6(self);
        spawnGamGuard7(self, gamGuard6);
        spawnGamGuard8(self, gamGuard6);
        spawnGamGuard9(self);
        spawnGamGuard10(self);
        obj_id gamGuard11 = spawnGamGuard11(self);
        spawnGamGuard12(self);
        spawnGamGuard13(self);
    }
    public void spawnMonks(obj_id self) throws InterruptedException
    {
        spawnBomarrMonk1(self);
        spawnBomarrMonk2(self);
        spawnBomarrMonk3(self);
        spawnBomarrMonk4(self);
        spawnBomarrMonk5(self);
        obj_id bomarrMonk6 = spawnBomarrMonk6(self);
    }
    public void spawnJawas(obj_id self) throws InterruptedException
    {
        obj_id jawa1 = spawnJawa1(self);
        spawnJawa2(self);
        obj_id jawa3 = spawnJawa3(self);
        obj_id jawa4 = spawnJawa4(self);
        spawnJawa5(self);
        spawnJawa6(self, jawa1);
        obj_id jawa7 = spawnJawa7(self);
        spawnJawa8(self, jawa7);
        obj_id jawa9 = spawnJawa9(self);
        spawnJawa10(self, jawa9);
        spawnRandom13(self, jawa3);
        spawnRandom14(self, jawa4);
    }
    public void spawnRandom(obj_id self) throws InterruptedException
    {
        obj_id gamGuard5 = spawnGamGuard5(self);
        spawnRandom1(self, gamGuard5);
        spawnRandom2(self);
        obj_id random3 = spawnRandom3(self);
        spawnRandom4(self, random3);
        obj_id random5 = spawnRandom5(self);
        obj_id random6 = spawnRandom6(self);
        obj_id random7 = spawnRandom7(self);
        spawnRandom8(self, random7);
        obj_id random9 = spawnRandom9(self);
        spawnRandom10(self, random9);
        obj_id random11 = spawnRandom11(self);
        spawnRandom12(self, random11);
        spawnRandom15(self, random5);
        spawnRandom16(self, random6);
    }
    public void spawnDroids(obj_id self) throws InterruptedException
    {
        spawnEv9d9(self);
        spawnDroid1(self);
        spawnDroid2(self);
        spawnDroid3(self);
        spawnDroid4(self);
        spawnDroid5(self);
        spawnDroid6(self);
    }
    public void spawnPrisoners(obj_id self) throws InterruptedException
    {
        spawnPrisoner1(self);
        spawnPrisoner2(self);
        spawnPrisoner3(self);
        spawnPrisoner4(self);
        spawnPrisoner5(self);
        spawnPrisoner6(self);
        spawnPrisoner7(self);
        spawnPrisoner8(self);
    }
    public obj_id spawnBib(obj_id self, obj_id oola) throws InterruptedException
    {
        obj_id throne = getCellId(self, "throneroom");
        location bibLocation = new location(-12.09f, 2f, 49.41f, "tatooine", throne);
        obj_id bib = create.staticObject("bib_fortuna", bibLocation);
        faceTo(bib, oola);
        attachScript(bib, "theme_park.tatooine.jabbaspawner.bib");
        setObjVar(self, "PalaceInhabitants.bib", bib);
        setObjVar(bib, "palace", self);
        return bib;
    }
    public obj_id spawnReelo(obj_id self) throws InterruptedException
    {
        obj_id foyer = getCellId(self, "foyer");
        location reeloLocation = new location(-2, 2, 113, "tatooine", foyer);
        obj_id reelo = create.staticObject("reelo_baruk", reeloLocation);
        attachScript(reelo, "theme_park.tatooine.jabbaspawner.reelo");
        setObjVar(self, "PalaceInhabitants.reelo", reelo);
        setObjVar(reelo, "palace", self);
        return reelo;
    }
    public void spawnReeYees(obj_id self, obj_id reelo) throws InterruptedException
    {
        obj_id foyer = getCellId(self, "foyer");
        location ReeYeesLocation = new location(5.97f, .3f, 116.1f, "tatooine", foyer);
        obj_id ReeYees = create.staticObject("ree_yees", ReeYeesLocation);
        faceTo(ReeYees, reelo);
        attachScript(ReeYees, "theme_park.tatooine.jabbaspawner.reeyees");
        setObjVar(self, "PalaceInhabitants.reeyees", ReeYees);
        setObjVar(ReeYees, "palace", self);
        return;
    }
    public obj_id spawnEphant(obj_id self) throws InterruptedException
    {
        obj_id office = getCellId(self, "office");
        location ephantLocation = new location(-6, 8, 84, "tatooine", office);
        obj_id ephant = create.staticObject("ephant_mon", ephantLocation);
        attachScript(ephant, "theme_park.tatooine.jabbaspawner.ephant");
        setObjVar(self, "PalaceInhabitants.ephant", ephant);
        setObjVar(ephant, "palace", self);
        return ephant;
    }
    public obj_id spawnOola(obj_id self) throws InterruptedException
    {
        obj_id throneroom = getCellId(self, "throneroom");
        location oolaLocation = new location(-10, 4, 43, "tatooine", throneroom);
        obj_id oola = create.staticObject("oola", oolaLocation);
        attachScript(oola, "theme_park.tatooine.jabbaspawner.oola");
        setObjVar(self, "PalaceInhabitants.oola", oola);
        setObjVar(oola, "palace", self);
        return oola;
    }
    public void spawnMax(obj_id self, obj_id oola) throws InterruptedException
    {
        obj_id throneroom = getCellId(self, "throneroom");
        location maxLocation = new location(-11, 2, 36, "tatooine", throneroom);
        obj_id max = create.staticObject("max_rebo", maxLocation);
        faceTo(max, oola);
        attachScript(max, "theme_park.tatooine.jabbaspawner.max");
        setObjVar(self, "PalaceInhabitants.max", max);
        setObjVar(max, "palace", self);
        setObjVar(max, "oola", oola);
        setObjVar(oola, "max", max);
        return;
    }
    public void spawnOrgan(obj_id self) throws InterruptedException
    {
        obj_id throneroom = getCellId(self, "throneroom");
        location organLoc = new location(-11, 2, 36, "tatooine", throneroom);
        obj_id organ = createObject("object/tangible/instrument/organ_max_rebo.iff", organLoc);
        return;
    }
    public obj_id spawnSy(obj_id self, obj_id oola) throws InterruptedException
    {
        obj_id throneroom = getCellId(self, "throneroom");
        location syLocation = new location(-9, 4, 37, "tatooine", throneroom);
        obj_id sy = create.staticObject("sy_snootles", syLocation);
        faceTo(sy, oola);
        attachScript(sy, "theme_park.tatooine.jabbaspawner.sy");
        setObjVar(self, "PalaceInhabitants.sy", sy);
        setObjVar(sy, "palace", self);
        setObjVar(oola, "sy", sy);
        syLocation.x = syLocation.x + 1;
        return sy;
    }
    public void spawnDroopy(obj_id self, obj_id oola) throws InterruptedException
    {
        obj_id throneroom = getCellId(self, "throneroom");
        location droopyLocation = new location(-13, 4, 37, "tatooine", throneroom);
        obj_id droopy = create.staticObject("droopy_mccool", droopyLocation);
        faceTo(droopy, oola);
        obj_id horn = createObject("object/tangible/instrument/kloo_horn.iff", droopy, "");
        equip(horn, droopy);
        attachScript(droopy, "theme_park.tatooine.jabbaspawner.droopy");
        setObjVar(self, "PalaceInhabitants.droopy", droopy);
        setObjVar(droopy, "palace", self);
        setObjVar(oola, "droopy", droopy);
        return;
    }
    public void spawnJabba(obj_id self, obj_id oola) throws InterruptedException
    {
        obj_id throneroom = getCellId(self, "throneroom");
        location jabbaLocation = new location(-16.65f, 7, 45, "tatooine", throneroom);
        obj_id jabba = create.staticObject("jabba_the_hutt", jabbaLocation);
        faceTo(jabba, oola);
        attachScript(jabba, "theme_park.tatooine.jabbaspawner.jabba");
        attachScript(jabba, "conversation.ep3_clone_relics_clone_trooper_jabba");
        setObjVar(self, "PalaceInhabitants.jabba", jabba);
        setObjVar(jabba, "palace", self);
        return;
    }
    public void spawnCrumb(obj_id self, obj_id oola) throws InterruptedException
    {
        obj_id throneroom = getCellId(self, "throneroom");
        location crumbLocation = new location(-16.29f, 7.0f, 42.54f, "tatooine", throneroom);
        obj_id crumb = create.staticObject("salacious_crumb", crumbLocation);
        faceTo(crumb, oola);
        setObjVar(self, "PalaceInhabitants.crumb", crumb);
        attachScript(crumb, "theme_park.tatooine.jabbaspawner.crumb");
        setObjVar(crumb, "palace", self);
        setObjVar(crumb, "ai.defaultCalmBehavior", 1);
        return;
    }
    public void spawnPDroid(obj_id self, obj_id oola) throws InterruptedException
    {
        obj_id throneroom = getCellId(self, "throneroom");
        location pDroidLocation = new location(-15.07f, 2, 45.9f, "tatooine", throneroom);
        obj_id pDroid = create.staticObject("g_5po", pDroidLocation);
        faceTo(pDroid, oola);
        attachScript(pDroid, "theme_park.tatooine.jabbaspawner.pdroid");
        setObjVar(self, "PalaceInhabitants.pDroid", pDroid);
        setObjVar(pDroid, "palace", self);
        return;
    }
    public void spawnBarada(obj_id self) throws InterruptedException
    {
        obj_id garage1 = getCellId(self, "garage1");
        location baradaLocation = new location(30.57f, .2f, -1.05f, "tatooine", garage1);
        location toolBox = new location(28, .2f, -1.05f, "tatooine", garage1);
        obj_id barada = create.staticObject("barada", baradaLocation);
        attachScript(barada, "theme_park.tatooine.jabbaspawner.barada");
        setObjVar(self, "PalaceInhabitants.barada", barada);
        setObjVar(barada, "palace", self);
        faceTo(barada, toolBox);
        return;
    }
    public void spawnKlaatu(obj_id self) throws InterruptedException
    {
        location klaatuLocation = new location(-6178.97f, 90.0f, -6381.74f, "tatooine", null);
        obj_id klaatu = create.staticObject("klaatu", klaatuLocation);
        attachScript(klaatu, "theme_park.tatooine.jabbaspawner.klaatu");
        attachScript(klaatu, "conversation.corvette_neutral_pilot");
        setObjVar(klaatu, "space_dungeon.ticket.point", "corvette_neutral_pilot");
        setObjVar(klaatu, "space_dungeon.ticket.dungeon", "corvette_neutral");
        attachScript(klaatu, "item.travel_ticket.travel_space_dungeon");
        setObjVar(self, "PalaceInhabitants.klaatu", klaatu);
        setObjVar(klaatu, "palace", self);
        return;
    }
    public void spawnPorcellus(obj_id self) throws InterruptedException
    {
        obj_id kitchen = getCellId(self, "kitchen");
        location porcellusLocation = new location(-43, 5, 64, "tatooine", kitchen);
        obj_id porcellus = create.staticObject("porcellus", porcellusLocation);
        attachScript(porcellus, "theme_park.tatooine.jabbaspawner.porcellus");
        setObjVar(self, "PalaceInhabitants.porcellus", porcellus);
        setObjVar(porcellus, "palace", self);
        return;
    }
    public obj_id spawnMalakili(obj_id self) throws InterruptedException
    {
        obj_id rancortrainer = getCellId(self, "rancortrainer");
        location malakiliLocation = new location(19, -9, 42, "tatooine", rancortrainer);
        obj_id malakili = create.staticObject("malakili", malakiliLocation);
        attachScript(malakili, "theme_park.tatooine.jabbaspawner.malakili");
        setObjVar(self, "PalaceInhabitants.malakili", malakili);
        setObjVar(malakili, "palace", self);
        return malakili;
    }
    public void spawnRancor(obj_id self, obj_id malakili) throws InterruptedException
    {
        obj_id rancorpit = getCellId(self, "rancorpit");
        location rancorLocation = new location(4, -10, 45, "tatooine", rancorpit);
        obj_id rancor = create.staticObject("jabbas_palace_rancor", rancorLocation);
        faceTo(rancor, malakili);
        attachScript(rancor, "theme_park.tatooine.jabbaspawner.rancor");
        setObjVar(self, "PalaceInhabitants.rancor", rancor);
        setObjVar(rancor, "palace", self);
        setObjVar(rancor, "gm", 1);
        return;
    }
    public void spawnBoba(obj_id self, obj_id oola) throws InterruptedException
    {
        obj_id throneroom = getCellId(self, "throneroom");
        location bobaLocation = new location(-1, 5, 31, "tatooine", throneroom);
        obj_id boba = create.staticObject("boba_fett", bobaLocation);
        faceTo(boba, oola);
        attachScript(boba, "theme_park.tatooine.jabbaspawner.boba");
        attachScript(boba, "conversation.boba_fett");
        setObjVar(self, "PalaceInhabitants.boba", boba);
        setObjVar(boba, "palace", self);
        return;
    }
    public obj_id spawnGamGuard1(obj_id self, obj_id bib) throws InterruptedException
    {
        obj_id office = getCellId(self, "office");
        location gamGuard1Location = new location(1, 7, 88, "tatooine", office);
        obj_id gamGuard1 = create.object("gamorrean_guard", gamGuard1Location);
        faceTo(gamGuard1, bib);
        attachScript(gamGuard1, "theme_park.tatooine.jabbaspawner.gamguard1");
        attachScript(gamGuard1, "theme_park.tatooine.jabbaspawner.palace_path");
        setObjVar(self, "PalaceInhabitants.gamGuard1", gamGuard1);
        setObjVar(gamGuard1, "palace", self);
        setName(gamGuard1, "Ortugg");
        return gamGuard1;
    }
    public void spawnGamGuard2(obj_id self, obj_id gamGuard1) throws InterruptedException
    {
        obj_id office = getCellId(self, "office");
        location gamGuard2Location = new location(1, 7, 80, "tatooine", office);
        obj_id gamGuard2 = create.object("gamorrean_guard", gamGuard2Location);
        faceTo(gamGuard2, gamGuard1);
        attachScript(gamGuard2, "theme_park.tatooine.jabbaspawner.gamguard2");
        attachScript(gamGuard2, "theme_park.tatooine.jabbaspawner.palace_path");
        setObjVar(self, "PalaceInhabitants.gamGuard2", gamGuard2);
        setObjVar(gamGuard2, "palace", self);
        setName(gamGuard2, "Rogua");
        return;
    }
    public void spawnGamGuard3(obj_id self, obj_id gamGuard1) throws InterruptedException
    {
        obj_id office = getCellId(self, "office");
        location gamGuard3Location = new location(-11, 7, 88, "tatooine", office);
        obj_id gamGuard3 = create.object("gamorrean_guard", gamGuard3Location);
        faceTo(gamGuard3, gamGuard1);
        attachScript(gamGuard3, "theme_park.tatooine.jabbaspawner.gamguard3");
        attachScript(gamGuard3, "theme_park.tatooine.jabbaspawner.palace_path");
        setObjVar(self, "PalaceInhabitants.gamGuard3", gamGuard3);
        setObjVar(gamGuard3, "palace", self);
        setName(gamGuard3, "Gartogg");
        return;
    }
    public void spawnGamGuard4(obj_id self, obj_id bib) throws InterruptedException
    {
        obj_id office = getCellId(self, "office");
        location gamGuard4Location = new location(-1, 7, 98, "tatooine", office);
        obj_id gamGuard4 = create.object("gamorrean_guard", gamGuard4Location);
        faceTo(gamGuard4, bib);
        attachScript(gamGuard4, "theme_park.tatooine.jabbaspawner.gamguard4");
        attachScript(gamGuard4, "theme_park.tatooine.jabbaspawner.palace_path");
        setObjVar(self, "PalaceInhabitants.gamGuard4", gamGuard4);
        setObjVar(gamGuard4, "palace", self);
        setName(gamGuard4, "Torrug");
        return;
    }
    public obj_id spawnGamGuard5(obj_id self) throws InterruptedException
    {
        obj_id hall2 = getCellId(self, "hall2");
        location gamGuard5Location = new location(10, 7, 71, "tatooine", hall2);
        obj_id gamGuard5 = create.object("gamorrean_guard", gamGuard5Location);
        attachScript(gamGuard5, "theme_park.tatooine.jabbaspawner.gamguard5");
        attachScript(gamGuard5, "theme_park.tatooine.jabbaspawner.palace_path");
        setObjVar(self, "PalaceInhabitants.gamGuard5", gamGuard5);
        setObjVar(gamGuard5, "palace", self);
        setName(gamGuard5, "Grogur");
        return gamGuard5;
    }
    public obj_id spawnGamGuard6(obj_id self) throws InterruptedException
    {
        obj_id hall5 = getCellId(self, "hall5");
        location gamGuard6Location = new location(-30.78f, .2f, 82.64f, "tatooine", hall5);
        obj_id gamGuard6 = create.object("gamorrean_guard", gamGuard6Location);
        attachScript(gamGuard6, "theme_park.tatooine.jabbaspawner.gamguard6");
        setObjVar(self, "PalaceInhabitants.gamGuard6", gamGuard6);
        attachScript(gamGuard6, "theme_park.tatooine.jabbaspawner.palace_path");
        setObjVar(gamGuard6, "palace", self);
        setName(gamGuard6, "Urgott");
        return gamGuard6;
    }
    public void spawnGamGuard7(obj_id self, obj_id gamGuard6) throws InterruptedException
    {
        obj_id hall7 = getCellId(self, "hall7");
        location gamGuard7Location = new location(-2.04f, .2f, 82.61f, "tatooine", hall7);
        obj_id gamGuard7 = create.object("gamorrean_guard", gamGuard7Location);
        faceTo(gamGuard7, gamGuard6);
        attachScript(gamGuard7, "theme_park.tatooine.jabbaspawner.gamguard7");
        attachScript(gamGuard7, "theme_park.tatooine.jabbaspawner.palace_path");
        setObjVar(self, "PalaceInhabitants.gamGuard7", gamGuard7);
        setObjVar(gamGuard7, "palace", self);
        setName(gamGuard7, "Artogg");
        return;
    }
    public void spawnGamGuard8(obj_id self, obj_id gamGuard6) throws InterruptedException
    {
        obj_id hall4 = getCellId(self, "hall4");
        location gamGuard8Location = new location(-13, 5, 59, "tatooine", hall4);
        obj_id gamGuard8 = create.object("gamorrean_guard", gamGuard8Location);
        faceTo(gamGuard8, gamGuard6);
        attachScript(gamGuard8, "theme_park.tatooine.jabbaspawner.gamguard8");
        attachScript(gamGuard8, "theme_park.tatooine.jabbaspawner.palace_path");
        setObjVar(self, "PalaceInhabitants.gamGuard8", gamGuard8);
        setObjVar(gamGuard8, "palace", self);
        setName(gamGuard8, "Ogtur");
        return;
    }
    public void spawnGamGuard9(obj_id self) throws InterruptedException
    {
        obj_id jabbaPrivate = getCellId(self, "jabbaprivate");
        location gamGuard9Location = new location(-37, 5, 46, "tatooine", jabbaPrivate);
        obj_id gamGuard9 = create.object("gamorrean_guard", gamGuard9Location);
        attachScript(gamGuard9, "theme_park.tatooine.jabbaspawner.gamguard9");
        attachScript(gamGuard9, "theme_park.tatooine.jabbaspawner.palace_path");
        setObjVar(self, "PalaceInhabitants.gamGuard9", gamGuard9);
        setObjVar(gamGuard9, "palace", self);
        setName(gamGuard9, "Rautog");
        return;
    }
    public void spawnGamGuard10(obj_id self) throws InterruptedException
    {
        obj_id throneRoom = getCellId(self, "throneroom");
        location gamGuard10Location = new location(-25, 5, 34, "tatooine", throneRoom);
        obj_id gamGuard10 = create.object("gamorrean_guard", gamGuard10Location);
        attachScript(gamGuard10, "theme_park.tatooine.jabbaspawner.gamguard10");
        attachScript(gamGuard10, "theme_park.tatooine.jabbaspawner.palace_path");
        setObjVar(self, "PalaceInhabitants.gamGuard10", gamGuard10);
        setObjVar(gamGuard10, "palace", self);
        setName(gamGuard10, "Agtor");
        return;
    }
    public obj_id spawnGamGuard11(obj_id self) throws InterruptedException
    {
        obj_id throneRoom = getCellId(self, "throneroom");
        location gamGuard11Location = new location(6, 4, 26, "tatooine", throneRoom);
        obj_id gamGuard11 = create.object("gamorrean_guard", gamGuard11Location);
        attachScript(gamGuard11, "theme_park.tatooine.jabbaspawner.gamguard11");
        attachScript(gamGuard11, "theme_park.tatooine.jabbaspawner.palace_path");
        setObjVar(self, "PalaceInhabitants.gamGuard11", gamGuard11);
        setObjVar(gamGuard11, "palace", self);
        setName(gamGuard11, "Torgau");
        return gamGuard11;
    }
    public void spawnGamGuard12(obj_id self) throws InterruptedException
    {
        obj_id rancorTrainer = getCellId(self, "rancortrainer");
        location gamGuard12Location = new location(22, -9, 48, "tatooine", rancorTrainer);
        obj_id gamGuard12 = create.object("gamorrean_guard", gamGuard12Location);
        attachScript(gamGuard12, "theme_park.tatooine.jabbaspawner.gamguard12");
        attachScript(gamGuard12, "theme_park.tatooine.jabbaspawner.palace_path");
        setObjVar(self, "PalaceInhabitants.gamGuard12", gamGuard12);
        setObjVar(gamGuard12, "palace", self);
        setName(gamGuard12, "Ugtaur");
        return;
    }
    public void spawnGamGuard13(obj_id self) throws InterruptedException
    {
        obj_id throneRoom = getCellId(self, "throneroom");
        location gamGuard13Location = new location(.14f, 2f, 53.1f, "tatooine", throneRoom);
        obj_id gamGuard13 = create.object("gamorrean_guard", gamGuard13Location);
        attachScript(gamGuard13, "theme_park.tatooine.jabbaspawner.gamguard13");
        attachScript(gamGuard13, "theme_park.tatooine.jabbaspawner.palace_path");
        setObjVar(self, "PalaceInhabitants.gamGuard13", gamGuard13);
        setName(gamGuard13, "Gurgot");
        setObjVar(gamGuard13, "palace", self);
        return;
    }
    public void spawnBomarrMonk1(obj_id self) throws InterruptedException
    {
        obj_id bibspawn = getCellId(self, "bibspawn");
        location bomarrMonk1Location = new location(-24, 10, 86, "tatooine", bibspawn);
        obj_id bomarrMonk1 = create.object("bomarr_monk", bomarrMonk1Location);
        setObjVar(bomarrMonk1, "ai.defaultCalmBehavior", 1);
        attachScript(bomarrMonk1, "theme_park.tatooine.jabbaspawner.bomarrmonk1");
        attachScript(bomarrMonk1, "theme_park.tatooine.jabbaspawner.palace_path");
        setObjVar(self, "PalaceInhabitants.bomarrMonk1", bomarrMonk1);
        setObjVar(bomarrMonk1, "palace", self);
        setInvulnerable(bomarrMonk1, true);
        return;
    }
    public void spawnBomarrMonk2(obj_id self) throws InterruptedException
    {
        obj_id hall1 = getCellId(self, "hall1");
        location bomarrMonk2Location = new location(-2, 4, 104, "tatooine", hall1);
        obj_id bomarrMonk2 = create.object("bomarr_monk", bomarrMonk2Location);
        setObjVar(bomarrMonk2, "ai.defaultCalmBehavior", 1);
        attachScript(bomarrMonk2, "theme_park.tatooine.jabbaspawner.bomarrmonk2");
        attachScript(bomarrMonk2, "theme_park.tatooine.jabbaspawner.palace_path");
        setObjVar(self, "PalaceInhabitants.bomarrMonk2", bomarrMonk2);
        setObjVar(bomarrMonk2, "palace", self);
        setInvulnerable(bomarrMonk2, true);
        return;
    }
    public void spawnBomarrMonk3(obj_id self) throws InterruptedException
    {
        obj_id spawn1 = getCellId(self, "spawn1");
        location bomarrMonk3Location = new location(-18, 9, 73, "tatooine", spawn1);
        obj_id bomarrMonk3 = create.object("bomarr_monk", bomarrMonk3Location);
        setObjVar(bomarrMonk3, "ai.defaultCalmBehavior", 1);
        attachScript(bomarrMonk3, "theme_park.tatooine.jabbaspawner.bomarrmonk3");
        attachScript(bomarrMonk3, "theme_park.tatooine.jabbaspawner.palace_path");
        setObjVar(self, "PalaceInhabitants.bomarrMonk3", bomarrMonk3);
        setObjVar(bomarrMonk3, "palace", self);
        setInvulnerable(bomarrMonk3, true);
        return;
    }
    public void spawnBomarrMonk4(obj_id self) throws InterruptedException
    {
        obj_id throneroom = getCellId(self, "throneroom");
        location bomarrMonk4Location = new location(7, 6, 50, "tatooine", throneroom);
        obj_id bomarrMonk4 = create.object("bomarr_monk", bomarrMonk4Location);
        setObjVar(bomarrMonk4, "ai.defaultCalmBehavior", 1);
        attachScript(bomarrMonk4, "theme_park.tatooine.jabbaspawner.bomarrmonk4");
        attachScript(bomarrMonk4, "theme_park.tatooine.jabbaspawner.palace_path");
        setObjVar(self, "PalaceInhabitants.bomarrMonk4", bomarrMonk4);
        setObjVar(bomarrMonk4, "palace", self);
        setInvulnerable(bomarrMonk4, true);
        return;
    }
    public void spawnBomarrMonk5(obj_id self) throws InterruptedException
    {
        obj_id garage2 = getCellId(self, "garage2");
        location bomarrMonk5Location = new location(24, 4, -9, "tatooine", garage2);
        obj_id bomarrMonk5 = create.object("bomarr_monk", bomarrMonk5Location);
        setObjVar(bomarrMonk5, "ai.defaultCalmBehavior", 1);
        attachScript(bomarrMonk5, "theme_park.tatooine.jabbaspawner.bomarrmonk5");
        attachScript(bomarrMonk5, "theme_park.tatooine.jabbaspawner.palace_path");
        setObjVar(self, "PalaceInhabitants.bomarrMonk5", bomarrMonk5);
        setObjVar(bomarrMonk5, "palace", self);
        setInvulnerable(bomarrMonk5, true);
        return;
    }
    public obj_id spawnBomarrMonk6(obj_id self) throws InterruptedException
    {
        obj_id display = getCellId(self, "display");
        location bomarrMonk6Location = new location(-18, 7, 9, "tatooine", display);
        obj_id bomarrMonk6 = create.object("bomarr_monk", bomarrMonk6Location);
        setObjVar(bomarrMonk6, "ai.defaultCalmBehavior", 1);
        attachScript(bomarrMonk6, "theme_park.tatooine.jabbaspawner.bomarrmonk6");
        setObjVar(self, "PalaceInhabitants.bomarrMonk6", bomarrMonk6);
        setObjVar(bomarrMonk6, "palace", self);
        setInvulnerable(bomarrMonk6, true);
        return bomarrMonk6;
    }
    public obj_id spawnJawa1(obj_id self) throws InterruptedException
    {
        obj_id hall4 = getCellId(self, "hall4");
        location jawa1Location = new location(-23, 5, 59, "tatooine", hall4);
        obj_id jawa1 = create.staticObject("jawa", jawa1Location);
        setObjVar(jawa1, "ai.defaultCalmBehavior", 1);
        attachScript(jawa1, "theme_park.tatooine.jabbaspawner.jawa1");
        setObjVar(self, "PalaceInhabitants.jawa1", jawa1);
        setObjVar(jawa1, "palace", self);
        setInvulnerable(jawa1, true);
        return jawa1;
    }
    public void spawnJawa2(obj_id self) throws InterruptedException
    {
        obj_id hall8 = getCellId(self, "hall8");
        location jawa2Location = new location(-38, 5, 53, "tatooine", hall8);
        obj_id jawa2 = create.staticObject("jawa", jawa2Location);
        setObjVar(jawa2, "ai.defaultCalmBehavior", 1);
        attachScript(jawa2, "theme_park.tatooine.jabbaspawner.jawa2");
        setObjVar(self, "PalaceInhabitants.jawa2", jawa2);
        setObjVar(jawa2, "palace", self);
        setInvulnerable(jawa2, true);
        return;
    }
    public obj_id spawnJawa3(obj_id self) throws InterruptedException
    {
        obj_id throneroom = getCellId(self, "throneroom");
        location jawa3Location = new location(-1, 5, 25, "tatooine", throneroom);
        obj_id jawa3 = create.staticObject("jawa", jawa3Location);
        setObjVar(jawa3, "ai.defaultCalmBehavior", 1);
        attachScript(jawa3, "theme_park.tatooine.jabbaspawner.jawa3");
        setObjVar(self, "PalaceInhabitants.jawa3", jawa3);
        setObjVar(jawa3, "palace", self);
        setInvulnerable(jawa3, true);
        return jawa3;
    }
    public obj_id spawnJawa4(obj_id self) throws InterruptedException
    {
        obj_id display = getCellId(self, "display");
        location jawa4Location = new location(-8, 5, 20, "tatooine", display);
        obj_id jawa4 = create.staticObject("jawa", jawa4Location);
        setObjVar(jawa4, "ai.defaultCalmBehavior", 1);
        attachScript(jawa4, "theme_park.tatooine.jabbaspawner.jawa4");
        setObjVar(self, "PalaceInhabitants.jawa4", jawa4);
        setObjVar(jawa4, "palace", self);
        setInvulnerable(jawa4, true);
        return jawa4;
    }
    public void spawnJawa5(obj_id self) throws InterruptedException
    {
        obj_id display = getCellId(self, "display");
        location jawa5Location = new location(-18, 5, 13, "tatooine", display);
        obj_id jawa5 = create.staticObject("jawa", jawa5Location);
        setObjVar(jawa5, "ai.defaultCalmBehavior", 1);
        attachScript(jawa5, "theme_park.tatooine.jabbaspawner.jawa5");
        setObjVar(self, "PalaceInhabitants.jawa5", jawa5);
        setObjVar(jawa5, "palace", self);
        setInvulnerable(jawa5, true);
        return;
    }
    public void spawnJawa6(obj_id self, obj_id jawa1) throws InterruptedException
    {
        obj_id hall4 = getCellId(self, "hall4");
        location jawa6Location = new location(-23, 5, 61, "tatooine", hall4);
        obj_id jawa6 = create.staticObject("jawa", jawa6Location);
        setObjVar(jawa6, "ai.defaultCalmBehavior", 1);
        faceTo(jawa6, jawa1);
        attachScript(jawa6, "theme_park.tatooine.jabbaspawner.jawa6");
        setObjVar(self, "PalaceInhabitants.jawa6", jawa6);
        setObjVar(jawa6, "palace", self);
        setInvulnerable(jawa6, true);
        return;
    }
    public obj_id spawnJawa7(obj_id self) throws InterruptedException
    {
        obj_id throne = getCellId(self, "throneroom");
        location jawa7Location = new location(8.73f, 2f, 23.58f, "tatooine", throne);
        obj_id jawa7 = create.staticObject("jawa", jawa7Location);
        setObjVar(jawa7, "ai.defaultCalmBehavior", 1);
        attachScript(jawa7, "theme_park.tatooine.jabbaspawner.jawa7");
        setObjVar(self, "PalaceInhabitants.jawa7", jawa7);
        setObjVar(jawa7, "palace", self);
        setInvulnerable(jawa7, true);
        return jawa7;
    }
    public void spawnJawa8(obj_id self, obj_id jawa7) throws InterruptedException
    {
        obj_id throne = getCellId(self, "throneroom");
        location jawa8Location = new location(9.22f, 2f, 25.22f, "tatooine", throne);
        obj_id jawa8 = create.staticObject("jawa", jawa8Location);
        setObjVar(jawa8, "ai.defaultCalmBehavior", 1);
        setObjVar(jawa8, "facer", jawa7);
        setObjVar(jawa7, "facer", jawa8);
        messageTo(jawa7, "doFacing", null, 10, true);
        messageTo(jawa8, "doFacing", null, 10, true);
        attachScript(jawa8, "theme_park.tatooine.jabbaspawner.jawa8");
        setObjVar(self, "PalaceInhabitants.jawa8", jawa8);
        setObjVar(jawa8, "palace", self);
        setInvulnerable(jawa8, true);
        return;
    }
    public obj_id spawnJawa9(obj_id self) throws InterruptedException
    {
        obj_id hall3 = getCellId(self, "hall3");
        location jawa9Location = new location(29.42f, .2f, 46.58f, "tatooine", hall3);
        obj_id jawa9 = create.staticObject("jawa", jawa9Location);
        setObjVar(jawa9, "ai.defaultCalmBehavior", 1);
        attachScript(jawa9, "theme_park.tatooine.jabbaspawner.jawa9");
        setObjVar(self, "PalaceInhabitants.jawa9", jawa9);
        setObjVar(jawa9, "palace", self);
        setInvulnerable(jawa9, true);
        return jawa9;
    }
    public void spawnJawa10(obj_id self, obj_id jawa9) throws InterruptedException
    {
        obj_id hall3 = getCellId(self, "hall3");
        location jawa10Location = new location(29.44f, .2f, 45.39f, "tatooine", hall3);
        obj_id jawa10 = create.staticObject("jawa", jawa10Location);
        setObjVar(jawa10, "ai.defaultCalmBehavior", 1);
        setObjVar(jawa9, "facer", jawa10);
        setObjVar(jawa10, "facer", jawa9);
        messageTo(jawa9, "doFacing", null, 10, true);
        messageTo(jawa10, "doFacing", null, 10, true);
        attachScript(jawa10, "theme_park.tatooine.jabbaspawner.jawa10");
        setObjVar(self, "PalaceInhabitants.jawa10", jawa10);
        setObjVar(jawa10, "palace", self);
        setInvulnerable(jawa10, true);
        return;
    }
    public void spawnRandom1(obj_id self, obj_id gamGuard5) throws InterruptedException
    {
        obj_id hall2 = getCellId(self, "hall2");
        location random1Location = new location(9, 7, 73, "tatooine", hall2);
        obj_id random1 = create.staticObject("outlaw", random1Location);
        setObjVar(random1, "ai.defaultCalmBehavior", 1);
        attachScript(random1, "theme_park.tatooine.jabbaspawner.random1");
        setObjVar(random1, "facer", gamGuard5);
        setObjVar(gamGuard5, "facer", random1);
        messageTo(random1, "doFacing", null, 10, true);
        messageTo(gamGuard5, "doFacing", null, 10, true);
        setObjVar(self, "PalaceInhabitants.random1", random1);
        setObjVar(random1, "palace", self);
        ai_lib.setCustomIdleAnimation(random1, "conversation");
        attachScript(random1, "theme_park.tatooine.jabbaspawner.behavior.conversing");
        setInvulnerable(random1, true);
        return;
    }
    public void spawnRandom2(obj_id self) throws InterruptedException
    {
        obj_id hall4 = getCellId(self, "hall4");
        location random2Location = new location(-25, 5, 61, "tatooine", hall4);
        obj_id random2 = create.object("roughneck", random2Location);
        setObjVar(random2, "ai.defaultCalmBehavior", 1);
        attachScript(random2, "theme_park.tatooine.jabbaspawner.random2");
        attachScript(random2, "theme_park.tatooine.jabbaspawner.palace_path");
        setObjVar(self, "PalaceInhabitants.random2", random2);
        setObjVar(random2, "palace", self);
        ai_lib.setCustomIdleAnimation(random2, "conversation");
        setInvulnerable(random2, true);
        return;
    }
    public obj_id spawnRandom3(obj_id self) throws InterruptedException
    {
        obj_id throneroom = getCellId(self, "throneroom");
        location random3Location = new location(5, 4, 44, "tatooine", throneroom);
        obj_id random3 = create.staticObject("scoundrel", random3Location);
        setObjVar(random3, "ai.defaultCalmBehavior", 1);
        attachScript(random3, "theme_park.tatooine.jabbaspawner.random3");
        setObjVar(self, "PalaceInhabitants.random3", random3);
        setObjVar(random3, "palace", self);
        ai_lib.setCustomIdleAnimation(random3, "conversation");
        attachScript(random3, "theme_park.tatooine.jabbaspawner.behavior.conversing");
        setInvulnerable(random3, true);
        return random3;
    }
    public void spawnRandom4(obj_id self, obj_id random3) throws InterruptedException
    {
        obj_id throneroom = getCellId(self, "throneroom");
        location random4Location = new location(5, 4, 45.5f, "tatooine", throneroom);
        obj_id random4 = create.staticObject("wastrel", random4Location);
        setObjVar(random4, "ai.defaultCalmBehavior", 1);
        setObjVar(random4, "facer", random3);
        setObjVar(random3, "facer", random4);
        messageTo(random4, "doFacing", null, 10, true);
        messageTo(random3, "doFacing", null, 10, true);
        attachScript(random4, "theme_park.tatooine.jabbaspawner.random4");
        setObjVar(self, "PalaceInhabitants.random4", random4);
        setObjVar(random4, "palace", self);
        ai_lib.setCustomIdleAnimation(random4, "conversation");
        attachScript(random4, "theme_park.tatooine.jabbaspawner.behavior.conversing");
        setInvulnerable(random4, true);
        return;
    }
    public obj_id spawnRandom5(obj_id self) throws InterruptedException
    {
        obj_id throneroom = getCellId(self, "throneroom");
        location random5Location = new location(-9, 5, 25, "tatooine", throneroom);
        obj_id random5 = create.object("spacer", random5Location);
        setObjVar(random5, "ai.defaultCalmBehavior", 1);
        attachScript(random5, "theme_park.tatooine.jabbaspawner.random5");
        setObjVar(self, "PalaceInhabitants.random5", random5);
        setObjVar(random5, "palace", self);
        attachScript(random5, "theme_park.tatooine.jabbaspawner.behavior.conversing");
        ai_lib.setCustomIdleAnimation(random5, "conversation");
        setInvulnerable(random5, true);
        return random5;
    }
    public obj_id spawnRandom6(obj_id self) throws InterruptedException
    {
        obj_id throneroom = getCellId(self, "throneroom");
        location random6Location = new location(1.08f, 2f, 36.7f, "tatooine", throneroom);
        obj_id random6 = create.staticObject("thug", random6Location);
        setObjVar(random6, "ai.defaultCalmBehavior", 1);
        attachScript(random6, "theme_park.tatooine.jabbaspawner.random6");
        setObjVar(self, "PalaceInhabitants.random6", random6);
        setObjVar(random6, "palace", self);
        attachScript(random6, "theme_park.tatooine.jabbaspawner.behavior.conversing");
        ai_lib.setCustomIdleAnimation(random6, "conversation");
        setInvulnerable(random6, true);
        return random6;
    }
    public obj_id spawnRandom7(obj_id self) throws InterruptedException
    {
        obj_id throneroom = getCellId(self, "throneroom");
        location random7Location = new location(-9, 4, 51, "tatooine", throneroom);
        obj_id random7 = create.staticObject("slicer", random7Location);
        setObjVar(random7, "ai.defaultCalmBehavior", 1);
        attachScript(random7, "theme_park.tatooine.jabbaspawner.random7");
        setObjVar(self, "PalaceInhabitants.random7", random7);
        setObjVar(random7, "palace", self);
        attachScript(random7, "theme_park.tatooine.jabbaspawner.behavior.conversing");
        ai_lib.setCustomIdleAnimation(random7, "conversation");
        setInvulnerable(random7, true);
        return random7;
    }
    public void spawnRandom8(obj_id self, obj_id random7) throws InterruptedException
    {
        obj_id throneroom = getCellId(self, "throneroom");
        location random8Location = new location(-8.69f, 4f, 49.57f, "tatooine", throneroom);
        obj_id random8 = create.staticObject("fringer", random8Location);
        setObjVar(random8, "ai.defaultCalmBehavior", 1);
        attachScript(random8, "theme_park.tatooine.jabbaspawner.random8");
        setObjVar(self, "PalaceInhabitants.random8", random8);
        setObjVar(random8, "palace", self);
        setObjVar(random7, "facer", random8);
        setObjVar(random8, "facer", random7);
        messageTo(random8, "doFacing", null, 10, true);
        messageTo(random7, "doFacing", null, 10, true);
        attachScript(random8, "theme_park.tatooine.jabbaspawner.behavior.conversing");
        ai_lib.setCustomIdleAnimation(random8, "conversation");
        setInvulnerable(random8, true);
        return;
    }
    public obj_id spawnRandom9(obj_id self) throws InterruptedException
    {
        obj_id throne = getCellId(self, "throneroom");
        location random9Location = new location(-5.88f, 2.0f, 43.07f, "tatooine", throne);
        obj_id random9 = create.staticObject("roughneck", random9Location);
        setObjVar(random9, "ai.defaultCalmBehavior", 1);
        attachScript(random9, "theme_park.tatooine.jabbaspawner.random9");
        setObjVar(self, "PalaceInhabitants.random9", random9);
        setObjVar(random9, "palace", self);
        ai_lib.setCustomIdleAnimation(random9, "conversation");
        setInvulnerable(random9, true);
        return random9;
    }
    public void spawnRandom10(obj_id self, obj_id random9) throws InterruptedException
    {
        obj_id throne = getCellId(self, "throneroom");
        location random10Location = new location(-5.72f, 2.0f, 44.11f, "tatooine", throne);
        obj_id random10 = create.staticObject("roughneck", random10Location);
        setObjVar(random10, "ai.defaultCalmBehavior", 1);
        attachScript(random10, "theme_park.tatooine.jabbaspawner.random10");
        setObjVar(random9, "facer", random10);
        setObjVar(random10, "facer", random9);
        messageTo(random9, "doFacing", null, 10, true);
        messageTo(random10, "doFacing", null, 10, true);
        setObjVar(self, "PalaceInhabitants.random10", random10);
        setObjVar(random10, "palace", self);
        ai_lib.setMood(random10, "conversation");
        ai_lib.setCustomIdleAnimation(random10, "conversation");
        setInvulnerable(random10, true);
        return;
    }
    public obj_id spawnRandom11(obj_id self) throws InterruptedException
    {
        obj_id throne = getCellId(self, "throneroom");
        location random11Location = new location(-14.32f, 2f, 52.08f, "tatooine", throne);
        obj_id random11 = create.staticObject("scoundrel", random11Location);
        setObjVar(random11, "ai.defaultCalmBehavior", 1);
        attachScript(random11, "theme_park.tatooine.jabbaspawner.random11");
        setObjVar(self, "PalaceInhabitants.random11", random11);
        setObjVar(random11, "palace", self);
        ai_lib.setCustomIdleAnimation(random11, "conversation");
        setInvulnerable(random11, true);
        return random11;
    }
    public void spawnRandom12(obj_id self, obj_id random11) throws InterruptedException
    {
        obj_id throne = getCellId(self, "throneroom");
        location random12Location = new location(-15.83f, 2f, 51.94f, "tatooine", throne);
        obj_id random12 = create.staticObject("thug", random12Location);
        setObjVar(random12, "ai.defaultCalmBehavior", 1);
        attachScript(random12, "theme_park.tatooine.jabbaspawner.random12");
        setObjVar(self, "PalaceInhabitants.random12", random12);
        setObjVar(random12, "palace", self);
        setObjVar(random11, "facer", random12);
        setObjVar(random12, "facer", random11);
        messageTo(random12, "doFacing", null, 10, true);
        messageTo(random11, "doFacing", null, 10, true);
        ai_lib.setCustomIdleAnimation(random12, "conversation");
        setInvulnerable(random12, true);
        return;
    }
    public void spawnRandom13(obj_id self, obj_id jawa3) throws InterruptedException
    {
        obj_id throne = getCellId(self, "throneroom");
        location random13Location = new location(-1.64f, 3f, 26.17f, "tatooine", throne);
        obj_id random13 = create.staticObject("thug", random13Location);
        setObjVar(random13, "ai.defaultCalmBehavior", 1);
        attachScript(random13, "theme_park.tatooine.jabbaspawner.random13");
        setObjVar(self, "PalaceInhabitants.random13", random13);
        setObjVar(random13, "palace", self);
        setObjVar(jawa3, "facer", random13);
        setObjVar(random13, "facer", jawa3);
        messageTo(random13, "doFacing", null, 10, true);
        messageTo(jawa3, "doFacing", null, 10, true);
        ai_lib.setCustomIdleAnimation(random13, "conversation");
        setInvulnerable(random13, true);
        return;
    }
    public void spawnRandom14(obj_id self, obj_id jawa4) throws InterruptedException
    {
        obj_id throne = getCellId(self, "throneroom");
        location random14Location = new location(-8.44f, 3f, 21.23f, "tatooine", throne);
        obj_id random14 = create.staticObject("thug", random14Location);
        setObjVar(random14, "ai.defaultCalmBehavior", 1);
        attachScript(random14, "theme_park.tatooine.jabbaspawner.random14");
        setObjVar(self, "PalaceInhabitants.random14", random14);
        setObjVar(random14, "palace", self);
        setObjVar(jawa4, "facer", random14);
        setObjVar(random14, "facer", jawa4);
        messageTo(random14, "doFacing", null, 10, true);
        messageTo(jawa4, "doFacing", null, 10, true);
        ai_lib.setCustomIdleAnimation(random14, "conversation");
        setInvulnerable(random14, true);
        return;
    }
    public void spawnRandom15(obj_id self, obj_id random5) throws InterruptedException
    {
        obj_id throne = getCellId(self, "throneroom");
        location random15Location = new location(-9.9f, 3f, 25.6f, "tatooine", throne);
        obj_id random15 = create.staticObject("thug", random15Location);
        setObjVar(random15, "ai.defaultCalmBehavior", 1);
        attachScript(random15, "theme_park.tatooine.jabbaspawner.random15");
        setObjVar(self, "PalaceInhabitants.random15", random15);
        setObjVar(random15, "palace", self);
        setObjVar(random5, "facer", random15);
        setObjVar(random15, "facer", random5);
        messageTo(random15, "doFacing", null, 10, true);
        messageTo(random5, "doFacing", null, 10, true);
        ai_lib.setCustomIdleAnimation(random15, "conversation");
        setInvulnerable(random15, true);
        return;
    }
    public void spawnRandom16(obj_id self, obj_id random6) throws InterruptedException
    {
        obj_id throne = getCellId(self, "throneroom");
        location random16Location = new location(1.08f, 2f, 35.07f, "tatooine", throne);
        obj_id random16 = create.staticObject("thug", random16Location);
        setObjVar(random16, "ai.defaultCalmBehavior", 1);
        attachScript(random16, "theme_park.tatooine.jabbaspawner.random16");
        setObjVar(self, "PalaceInhabitants.random16", random16);
        setObjVar(random16, "palace", self);
        setObjVar(random6, "facer", random16);
        setObjVar(random16, "facer", random6);
        messageTo(random16, "doFacing", null, 10, true);
        messageTo(random6, "doFacing", null, 10, true);
        ai_lib.setCustomIdleAnimation(random16, "conversation");
        setInvulnerable(random16, true);
        return;
    }
    public void spawnEv9d9(obj_id self) throws InterruptedException
    {
        obj_id droidtorture = getCellId(self, "droidtorture");
        location ev9d9Loc = new location(24.47f, 0.2f, 87.86f, "tatooine", droidtorture);
        obj_id ev9d9 = create.staticObject("ev_9d9", ev9d9Loc);
        setObjVar(ev9d9, "ai.defaultCalmBehavior", 1);
        attachScript(ev9d9, "theme_park.tatooine.jabbaspawner.ev9d9");
        setObjVar(self, "PalaceInhabitants.ev9d9", ev9d9);
        setObjVar(ev9d9, "palace", self);
        return;
    }
    public void spawnDroid1(obj_id self) throws InterruptedException
    {
        obj_id droidtorture = getCellId(self, "droidtorture");
        location droid1Location = new location(19.73f, 0.2f, 90.51f, "tatooine", droidtorture);
        obj_id droid1 = create.object("r2", droid1Location);
        setObjVar(droid1, "ai.defaultCalmBehavior", 1);
        attachScript(droid1, "theme_park.tatooine.jabbaspawner.droid1");
        attachScript(droid1, "theme_park.tatooine.jabbaspawner.palace_path");
        setObjVar(self, "PalaceInhabitants.droid1", droid1);
        setObjVar(droid1, "palace", self);
        setInvulnerable(droid1, true);
        return;
    }
    public void spawnDroid2(obj_id self) throws InterruptedException
    {
        obj_id droidtorture = getCellId(self, "droidtorture");
        location droid2Location = new location(13.33f, 0.2f, 88.72f, "tatooine", droidtorture);
        obj_id droid2 = create.object("ra7_bug_droid", droid2Location);
        setObjVar(droid2, "ai.defaultCalmBehavior", 1);
        attachScript(droid2, "theme_park.tatooine.jabbaspawner.droid2");
        setObjVar(self, "PalaceInhabitants.droid2", droid2);
        setObjVar(droid2, "palace", self);
        setInvulnerable(droid2, true);
        return;
    }
    public void spawnDroid3(obj_id self) throws InterruptedException
    {
        obj_id droidtorture = getCellId(self, "droidtorture");
        location droid3Location = new location(10.71f, 0.2f, 84.27f, "tatooine", droidtorture);
        obj_id droid3 = create.object("protocol_droid_3po_red", droid3Location);
        setObjVar(droid3, "ai.defaultCalmBehavior", 1);
        attachScript(droid3, "theme_park.tatooine.jabbaspawner.droid3");
        setObjVar(self, "PalaceInhabitants.droid3", droid3);
        setObjVar(droid3, "palace", self);
        setInvulnerable(droid3, true);
        return;
    }
    public void spawnDroid4(obj_id self) throws InterruptedException
    {
        obj_id droidtorture = getCellId(self, "droidtorture");
        location droid4Location = new location(17.42f, 0.2f, 81.22f, "tatooine", droidtorture);
        obj_id droid4 = create.object("eg6_power_droid", droid4Location);
        setObjVar(droid4, "ai.defaultCalmBehavior", 1);
        attachScript(droid4, "theme_park.tatooine.jabbaspawner.droid4");
        attachScript(droid4, "theme_park.tatooine.jabbaspawner.palace_path");
        setObjVar(self, "PalaceInhabitants.droid4", droid4);
        setObjVar(droid4, "palace", self);
        setInvulnerable(droid4, true);
        return;
    }
    public void spawnDroid5(obj_id self) throws InterruptedException
    {
        obj_id droidtorture = getCellId(self, "droidtorture");
        location droid5Location = new location(23.67f, 0.2f, 76.43f, "tatooine", droidtorture);
        obj_id droid5 = create.object("le_repair_droid", droid5Location);
        setObjVar(droid5, "ai.defaultCalmBehavior", 1);
        attachScript(droid5, "theme_park.tatooine.jabbaspawner.droid5");
        setObjVar(self, "PalaceInhabitants.droid5", droid5);
        setObjVar(droid5, "palace", self);
        setInvulnerable(droid5, true);
        return;
    }
    public void spawnDroid6(obj_id self) throws InterruptedException
    {
        obj_id garage = getCellId(self, "garage1");
        location droid6Location = new location(43.29f, .2f, 7.14f, "tatooine", garage);
        obj_id droid6 = create.object("eg6_power_droid", droid6Location);
        setObjVar(droid6, "ai.defaultCalmBehavior", 1);
        attachScript(droid6, "theme_park.tatooine.jabbaspawner.droid6");
        attachScript(droid6, "theme_park.tatooine.jabbaspawner.palace_path");
        setObjVar(self, "PalaceInhabitants.droid6", droid6);
        setObjVar(droid6, "palace", self);
        setInvulnerable(droid6, true);
        return;
    }
    public void spawnPrisoner1(obj_id self) throws InterruptedException
    {
        obj_id jail1 = getCellId(self, "jail1");
        location jailCell1 = new location(-33.83f, .2f, 76.58f, "tatooine", jail1);
        obj_id prisoner1 = create.staticObject("commoner", jailCell1);
        setObjVar(prisoner1, "ai.defaultCalmBehavior", 1);
        attachScript(prisoner1, "theme_park.tatooine.jabbaspawner.prisoner1");
        attachScript(prisoner1, "theme_park.tatooine.jabbaspawner.palace_path");
        setObjVar(self, "PalaceInhabitants.prisoner1", prisoner1);
        setObjVar(prisoner1, "palace", self);
        setInvulnerable(prisoner1, true);
        return;
    }
    public void spawnPrisoner2(obj_id self) throws InterruptedException
    {
        obj_id jail2 = getCellId(self, "jail2");
        location jailCell2 = new location(-37.43f, .2f, 76.85f, "tatooine", jail2);
        obj_id prisoner2 = create.staticObject("commoner", jailCell2);
        setObjVar(prisoner2, "ai.defaultCalmBehavior", 1);
        attachScript(prisoner2, "theme_park.tatooine.jabbaspawner.prisoner2");
        attachScript(prisoner2, "theme_park.tatooine.jabbaspawner.palace_path");
        setObjVar(self, "PalaceInhabitants.prisoner2", prisoner2);
        setObjVar(prisoner2, "palace", self);
        setInvulnerable(prisoner2, true);
        return;
    }
    public void spawnPrisoner3(obj_id self) throws InterruptedException
    {
        obj_id jail3 = getCellId(self, "jail3");
        location jailCell3 = new location(-34.48f, .2f, 88.22f, "tatooine", jail3);
        obj_id prisoner3 = create.staticObject("commoner", jailCell3);
        setObjVar(prisoner3, "ai.defaultCalmBehavior", 1);
        attachScript(prisoner3, "theme_park.tatooine.jabbaspawner.prisoner3");
        attachScript(prisoner3, "theme_park.tatooine.jabbaspawner.palace_path");
        setObjVar(self, "PalaceInhabitants.prisoner3", prisoner3);
        setObjVar(prisoner3, "palace", self);
        setInvulnerable(prisoner3, true);
        return;
    }
    public void spawnPrisoner4(obj_id self) throws InterruptedException
    {
        obj_id jail4 = getCellId(self, "jail4");
        location jailCell4 = new location(-38.7f, .2f, 88.24f, "tatooine", jail4);
        obj_id prisoner4 = create.staticObject("commoner", jailCell4);
        setObjVar(prisoner4, "ai.defaultCalmBehavior", 1);
        attachScript(prisoner4, "theme_park.tatooine.jabbaspawner.prisoner4");
        attachScript(prisoner4, "theme_park.tatooine.jabbaspawner.palace_path");
        setObjVar(self, "PalaceInhabitants.prisoner4", prisoner4);
        setObjVar(prisoner4, "palace", self);
        setInvulnerable(prisoner4, true);
        return;
    }
    public void spawnPrisoner5(obj_id self) throws InterruptedException
    {
        obj_id jail5 = getCellId(self, "jail5");
        location jailCell5 = new location(-10.07f, .2f, 77.54f, "tatooine", jail5);
        obj_id prisoner5 = create.staticObject("commoner", jailCell5);
        setObjVar(prisoner5, "ai.defaultCalmBehavior", 1);
        attachScript(prisoner5, "theme_park.tatooine.jabbaspawner.prisoner5");
        attachScript(prisoner5, "theme_park.tatooine.jabbaspawner.palace_path");
        setObjVar(self, "PalaceInhabitants.prisoner5", prisoner5);
        setObjVar(prisoner5, "palace", self);
        setInvulnerable(prisoner5, true);
        return;
    }
    public void spawnPrisoner6(obj_id self) throws InterruptedException
    {
        obj_id jail6 = getCellId(self, "jail6");
        location jailCell6 = new location(-13.62f, .2f, 77.52f, "tatooine", jail6);
        obj_id prisoner6 = create.staticObject("commoner", jailCell6);
        setObjVar(prisoner6, "ai.defaultCalmBehavior", 1);
        attachScript(prisoner6, "theme_park.tatooine.jabbaspawner.prisoner6");
        attachScript(prisoner6, "theme_park.tatooine.jabbaspawner.palace_path");
        setObjVar(self, "PalaceInhabitants.prisoner6", prisoner6);
        setObjVar(prisoner6, "palace", self);
        setInvulnerable(prisoner6, true);
        return;
    }
    public void spawnPrisoner7(obj_id self) throws InterruptedException
    {
        obj_id jail7 = getCellId(self, "jail7");
        location jailCell7 = new location(-10.88f, .2f, 87.49f, "tatooine", jail7);
        obj_id prisoner7 = create.staticObject("commoner", jailCell7);
        setObjVar(prisoner7, "ai.defaultCalmBehavior", 1);
        attachScript(prisoner7, "theme_park.tatooine.jabbaspawner.prisoner7");
        attachScript(prisoner7, "theme_park.tatooine.jabbaspawner.palace_path");
        setObjVar(self, "PalaceInhabitants.prisoner7", prisoner7);
        setObjVar(prisoner7, "palace", self);
        setInvulnerable(prisoner7, true);
        return;
    }
    public void spawnPrisoner8(obj_id self) throws InterruptedException
    {
        obj_id jail8 = getCellId(self, "jail8");
        location jailCell8 = new location(-15.43f, .2f, 87.68f, "tatooine", jail8);
        obj_id prisoner8 = create.staticObject("commoner", jailCell8);
        setObjVar(prisoner8, "ai.defaultCalmBehavior", 1);
        attachScript(prisoner8, "theme_park.tatooine.jabbaspawner.prisoner8");
        attachScript(prisoner8, "theme_park.tatooine.jabbaspawner.palace_path");
        setObjVar(self, "PalaceInhabitants.prisoner8", prisoner8);
        setObjVar(prisoner8, "palace", self);
        setInvulnerable(prisoner8, true);
        return;
    }
    public void spawnJawl(obj_id self) throws InterruptedException
    {
        obj_id foyer = getCellId(self, "foyer");
        location jawlsloc = new location(-5.5f, 0.2f, 114.2f, "tatooine", foyer);
        int yaw = -150;
        obj_id jawl = create.object("clone_relics_jawl", jawlsloc);
        setYaw(jawl, yaw);
        ai_lib.setDefaultCalmMood(jawl, "confused");
        setObjVar(self, "PalaceInhabitants.jawl", jawl);
        setObjVar(jawl, "palace", self);
        return;
    }
    public int bibDied(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id oola = getObjIdObjVar(self, "PalaceInhabitants.oola");
        spawnBib(self, oola);
        return SCRIPT_CONTINUE;
    }
    public int reeloDied(obj_id self, dictionary params) throws InterruptedException
    {
        spawnReelo(self);
        return SCRIPT_CONTINUE;
    }
    public int ephantDied(obj_id self, dictionary params) throws InterruptedException
    {
        spawnEphant(self);
        return SCRIPT_CONTINUE;
    }
    public int reeyeesDied(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id oola = getObjIdObjVar(self, "PalaceInhabitants.oola");
        spawnReeYees(self, oola);
        return SCRIPT_CONTINUE;
    }
    public int jawlDied(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id jawl = getObjIdObjVar(self, "PalaceInhabitants.jawl");
        spawnJawl(self);
        return SCRIPT_CONTINUE;
    }
    public int oolaDied(obj_id self, dictionary params) throws InterruptedException
    {
        spawnOola(self);
        return SCRIPT_CONTINUE;
    }
    public int maxDied(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id oola = getObjIdObjVar(self, "PalaceInhabitants.oola");
        spawnMax(self, oola);
        return SCRIPT_CONTINUE;
    }
    public int syDied(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id oola = getObjIdObjVar(self, "PalaceInhabitants.oola");
        spawnSy(self, oola);
        return SCRIPT_CONTINUE;
    }
    public int droopyDied(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id oola = getObjIdObjVar(self, "PalaceInhabitants.oola");
        spawnDroopy(self, oola);
        return SCRIPT_CONTINUE;
    }
    public int jabbaDied(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id oola = getObjIdObjVar(self, "PalaceInhabitants.oola");
        spawnJabba(self, oola);
        return SCRIPT_CONTINUE;
    }
    public int crumbDied(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id oola = getObjIdObjVar(self, "PalaceInhabitants.crumb");
        spawnCrumb(self, oola);
        return SCRIPT_CONTINUE;
    }
    public int pDroidDied(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id oola = getObjIdObjVar(self, "PalaceInhabitants.oola");
        spawnPDroid(self, oola);
        return SCRIPT_CONTINUE;
    }
    public int baradaDied(obj_id self, dictionary params) throws InterruptedException
    {
        spawnBarada(self);
        return SCRIPT_CONTINUE;
    }
    public int klaatuDied(obj_id self, dictionary params) throws InterruptedException
    {
        spawnKlaatu(self);
        return SCRIPT_CONTINUE;
    }
    public int porcellusDied(obj_id self, dictionary params) throws InterruptedException
    {
        spawnPorcellus(self);
        return SCRIPT_CONTINUE;
    }
    public int malakiliDied(obj_id self, dictionary params) throws InterruptedException
    {
        spawnMalakili(self);
        return SCRIPT_CONTINUE;
    }
    public int rancorDied(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id malakili = getObjIdObjVar(self, "PalaceInhabitants.malakili");
        spawnRancor(self, malakili);
        return SCRIPT_CONTINUE;
    }
    public int bobaDied(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id oola = getObjIdObjVar(self, "PalaceInhabitants.oola");
        spawnBoba(self, oola);
        return SCRIPT_CONTINUE;
    }
    public int gamGuard1Died(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id ephant = getObjIdObjVar(self, "PalaceInhabitants.ephant");
        spawnGamGuard1(self, ephant);
        return SCRIPT_CONTINUE;
    }
    public int gamGuard2Died(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id gamGuard1 = getObjIdObjVar(self, "PalaceInhabitants.gamGuard1");
        spawnGamGuard2(self, gamGuard1);
        return SCRIPT_CONTINUE;
    }
    public int gamGuard3Died(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id gamGuard1 = getObjIdObjVar(self, "PalaceInhabitants.gamGuard1");
        spawnGamGuard3(self, gamGuard1);
        return SCRIPT_CONTINUE;
    }
    public int gamGuard4Died(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id ephant = getObjIdObjVar(self, "PalaceInhabitants.ephant");
        spawnGamGuard4(self, ephant);
        return SCRIPT_CONTINUE;
    }
    public int gamGuard5Died(obj_id self, dictionary params) throws InterruptedException
    {
        spawnGamGuard5(self);
        return SCRIPT_CONTINUE;
    }
    public int gamGuard6Died(obj_id self, dictionary params) throws InterruptedException
    {
        spawnGamGuard6(self);
        return SCRIPT_CONTINUE;
    }
    public int gamGuard7Died(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id gamGuard6 = getObjIdObjVar(self, "PalaceInhabitants.gamGuard6");
        spawnGamGuard7(self, gamGuard6);
        return SCRIPT_CONTINUE;
    }
    public int gamGuard8Died(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id gamGuard6 = getObjIdObjVar(self, "PalaceInhabitants.gamGuard6");
        spawnGamGuard8(self, gamGuard6);
        return SCRIPT_CONTINUE;
    }
    public int gamGuard9Died(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id jabba2 = getObjIdObjVar(self, "PalaceInhabitants.jabba2");
        spawnGamGuard9(self);
        return SCRIPT_CONTINUE;
    }
    public int gamGuard10Died(obj_id self, dictionary params) throws InterruptedException
    {
        spawnGamGuard10(self);
        return SCRIPT_CONTINUE;
    }
    public int gamGuard11Died(obj_id self, dictionary params) throws InterruptedException
    {
        spawnGamGuard11(self);
        return SCRIPT_CONTINUE;
    }
    public int gamGuard12Died(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id malakili = getObjIdObjVar(self, "PalaceInhabitants.malakili");
        spawnGamGuard12(self);
        return SCRIPT_CONTINUE;
    }
    public int gamGuard13Died(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id oola = getObjIdObjVar(self, "PalaceInhabitants.oola");
        spawnGamGuard13(self);
        return SCRIPT_CONTINUE;
    }
    public int bomarrMonk1Died(obj_id self, dictionary params) throws InterruptedException
    {
        spawnBomarrMonk1(self);
        return SCRIPT_CONTINUE;
    }
    public int bomarrMonk2Died(obj_id self, dictionary params) throws InterruptedException
    {
        spawnBomarrMonk2(self);
        return SCRIPT_CONTINUE;
    }
    public int bomarrMonk3Died(obj_id self, dictionary params) throws InterruptedException
    {
        spawnBomarrMonk3(self);
        return SCRIPT_CONTINUE;
    }
    public int bomarrMonk4Died(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id gamGuard11 = getObjIdObjVar(self, "PalaceInhabitants.gamGuard11");
        spawnBomarrMonk4(self);
        return SCRIPT_CONTINUE;
    }
    public int bomarrMonk5Died(obj_id self, dictionary params) throws InterruptedException
    {
        spawnBomarrMonk5(self);
        return SCRIPT_CONTINUE;
    }
    public int bomarrMonk6Died(obj_id self, dictionary params) throws InterruptedException
    {
        spawnBomarrMonk6(self);
        return SCRIPT_CONTINUE;
    }
    public int jawa1Died(obj_id self, dictionary params) throws InterruptedException
    {
        spawnJawa1(self);
        return SCRIPT_CONTINUE;
    }
    public int jawa2Died(obj_id self, dictionary params) throws InterruptedException
    {
        spawnJawa2(self);
        return SCRIPT_CONTINUE;
    }
    public int jawa3Died(obj_id self, dictionary params) throws InterruptedException
    {
        spawnJawa3(self);
        return SCRIPT_CONTINUE;
    }
    public int jawa4Died(obj_id self, dictionary params) throws InterruptedException
    {
        spawnJawa4(self);
        return SCRIPT_CONTINUE;
    }
    public int jawa5Died(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id bomarrMonk6 = getObjIdObjVar(self, "PalaceInhabitants.bomarrMonk6");
        spawnJawa5(self);
        return SCRIPT_CONTINUE;
    }
    public int jawa6Died(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id jawa1 = getObjIdObjVar(self, "PalaceInhabitants.jawa1");
        spawnJawa6(self, jawa1);
        return SCRIPT_CONTINUE;
    }
    public int jawa7Died(obj_id self, dictionary params) throws InterruptedException
    {
        spawnJawa7(self);
        return SCRIPT_CONTINUE;
    }
    public int jawa8Died(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id jawa7 = getObjIdObjVar(self, "PalaceInhabitants.jawa7");
        spawnJawa8(self, jawa7);
        return SCRIPT_CONTINUE;
    }
    public int jawa9Died(obj_id self, dictionary params) throws InterruptedException
    {
        spawnJawa9(self);
        return SCRIPT_CONTINUE;
    }
    public int jawa10Died(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id jawa9 = getObjIdObjVar(self, "PalaceInhabitants.jawa9");
        spawnJawa10(self, jawa9);
        return SCRIPT_CONTINUE;
    }
    public int random1Died(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id gamGuard5 = getObjIdObjVar(self, "PalaceInhabitants.gamGuard5");
        spawnRandom1(self, gamGuard5);
        return SCRIPT_CONTINUE;
    }
    public int random2Died(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id jawa1 = getObjIdObjVar(self, "PalaceInhabitants.jawa1");
        spawnRandom2(self);
        return SCRIPT_CONTINUE;
    }
    public int random3Died(obj_id self, dictionary params) throws InterruptedException
    {
        spawnRandom3(self);
        return SCRIPT_CONTINUE;
    }
    public int random4Died(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id random3 = getObjIdObjVar(self, "PalaceInhabitants.random3");
        spawnRandom4(self, random3);
        return SCRIPT_CONTINUE;
    }
    public int random5Died(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id random3 = getObjIdObjVar(self, "PalaceInhabitants.random3");
        spawnRandom5(self);
        return SCRIPT_CONTINUE;
    }
    public int random6Died(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id sy = getObjIdObjVar(self, "PalaceInhabitants.sy");
        spawnRandom6(self);
        return SCRIPT_CONTINUE;
    }
    public int random7Died(obj_id self, dictionary params) throws InterruptedException
    {
        spawnRandom7(self);
        return SCRIPT_CONTINUE;
    }
    public int random8Died(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id random7 = getObjIdObjVar(self, "PalaceInhabitants.random7");
        spawnRandom8(self, random7);
        return SCRIPT_CONTINUE;
    }
    public int random9Died(obj_id self, dictionary params) throws InterruptedException
    {
        spawnRandom9(self);
        return SCRIPT_CONTINUE;
    }
    public int random10Died(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id random9 = getObjIdObjVar(self, "PalaceInhabitants.random9");
        spawnRandom10(self, random9);
        return SCRIPT_CONTINUE;
    }
    public int random11Died(obj_id self, dictionary params) throws InterruptedException
    {
        spawnRandom11(self);
        return SCRIPT_CONTINUE;
    }
    public int random12Died(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id random11 = getObjIdObjVar(self, "PalaceInhabitants.random11");
        spawnRandom12(self, random11);
        return SCRIPT_CONTINUE;
    }
    public int random13Died(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id jawa3 = getObjIdObjVar(self, "PalaceInhabitants.jawa3");
        spawnRandom13(self, jawa3);
        return SCRIPT_CONTINUE;
    }
    public int random14Died(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id jawa4 = getObjIdObjVar(self, "PalaceInhabitants.jawa4");
        spawnRandom14(self, jawa4);
        return SCRIPT_CONTINUE;
    }
    public int random15Died(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id random5 = getObjIdObjVar(self, "PalaceInhabitants.random5");
        spawnRandom15(self, random5);
        return SCRIPT_CONTINUE;
    }
    public int random16Died(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id random6 = getObjIdObjVar(self, "PalaceInhabitants.random6");
        spawnRandom16(self, random6);
        return SCRIPT_CONTINUE;
    }
    public int ev9d9Died(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id existing = getObjIdObjVar(self, "PalaceInhabitants.ev9d9");
        if (!exists(existing))
        {
            spawnEv9d9(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int droid1Died(obj_id self, dictionary params) throws InterruptedException
    {
        spawnDroid1(self);
        return SCRIPT_CONTINUE;
    }
    public int droid2Died(obj_id self, dictionary params) throws InterruptedException
    {
        spawnDroid2(self);
        return SCRIPT_CONTINUE;
    }
    public int droid3Died(obj_id self, dictionary params) throws InterruptedException
    {
        spawnDroid3(self);
        return SCRIPT_CONTINUE;
    }
    public int droid4Died(obj_id self, dictionary params) throws InterruptedException
    {
        spawnDroid4(self);
        return SCRIPT_CONTINUE;
    }
    public int droid5Died(obj_id self, dictionary params) throws InterruptedException
    {
        spawnDroid5(self);
        return SCRIPT_CONTINUE;
    }
    public int droid6Died(obj_id self, dictionary params) throws InterruptedException
    {
        spawnDroid6(self);
        return SCRIPT_CONTINUE;
    }
    public int prisoner1Died(obj_id self, dictionary params) throws InterruptedException
    {
        spawnPrisoner1(self);
        return SCRIPT_CONTINUE;
    }
    public int prisoner2Died(obj_id self, dictionary params) throws InterruptedException
    {
        spawnPrisoner2(self);
        return SCRIPT_CONTINUE;
    }
    public int prisoner3Died(obj_id self, dictionary params) throws InterruptedException
    {
        spawnPrisoner3(self);
        return SCRIPT_CONTINUE;
    }
    public int prisoner4Died(obj_id self, dictionary params) throws InterruptedException
    {
        spawnPrisoner4(self);
        return SCRIPT_CONTINUE;
    }
    public int prisoner5Died(obj_id self, dictionary params) throws InterruptedException
    {
        spawnPrisoner5(self);
        return SCRIPT_CONTINUE;
    }
    public int prisoner6Died(obj_id self, dictionary params) throws InterruptedException
    {
        spawnPrisoner6(self);
        return SCRIPT_CONTINUE;
    }
    public int prisoner7Died(obj_id self, dictionary params) throws InterruptedException
    {
        spawnPrisoner7(self);
        return SCRIPT_CONTINUE;
    }
    public int prisoner8Died(obj_id self, dictionary params) throws InterruptedException
    {
        spawnPrisoner8(self);
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        debugServerConsoleMsg(self, "Detaching Jabba's Palace Spawn Script");
        despawnEveryone(self);
        return SCRIPT_CONTINUE;
    }
    public int doGuards(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id guard1 = getObjIdObjVar(self, "PalaceInhabitants.gamGuard1");
        attachScript(guard1, "theme_park.tatooine.jabbaspawner.palace_path");
        obj_id guard2 = getObjIdObjVar(self, "PalaceInhabitants.gamGuard2");
        attachScript(guard2, "theme_park.tatooine.jabbaspawner.palace_path");
        obj_id guard3 = getObjIdObjVar(self, "PalaceInhabitants.gamGuard3");
        attachScript(guard3, "theme_park.tatooine.jabbaspawner.palace_path");
        obj_id guard4 = getObjIdObjVar(self, "PalaceInhabitants.gamGuard4");
        attachScript(guard4, "theme_park.tatooine.jabbaspawner.palace_path");
        obj_id guard6 = getObjIdObjVar(self, "PalaceInhabitants.gamGuard6");
        attachScript(guard6, "theme_park.tatooine.jabbaspawner.palace_path");
        obj_id guard7 = getObjIdObjVar(self, "PalaceInhabitants.gamGuard7");
        attachScript(guard7, "theme_park.tatooine.jabbaspawner.palace_path");
        obj_id guard8 = getObjIdObjVar(self, "PalaceInhabitants.gamGuard8");
        attachScript(guard8, "theme_park.tatooine.jabbaspawner.palace_path");
        obj_id guard9 = getObjIdObjVar(self, "PalaceInhabitants.gamGuard9");
        attachScript(guard9, "theme_park.tatooine.jabbaspawner.palace_path");
        obj_id guard10 = getObjIdObjVar(self, "PalaceInhabitants.gamGuard10");
        attachScript(guard10, "theme_park.tatooine.jabbaspawner.palace_path");
        obj_id guard11 = getObjIdObjVar(self, "PalaceInhabitants.gamGuard11");
        attachScript(guard11, "theme_park.tatooine.jabbaspawner.palace_path");
        obj_id guard12 = getObjIdObjVar(self, "PalaceInhabitants.gamGuard12");
        attachScript(guard12, "theme_park.tatooine.jabbaspawner.palace_path");
        return SCRIPT_CONTINUE;
    }
    public int doMonks(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id monk1 = getObjIdObjVar(self, "PalaceInhabitants.bomarrMonk1");
        attachScript(monk1, "theme_park.tatooine.jabbaspawner.palace_path");
        obj_id monk2 = getObjIdObjVar(self, "PalaceInhabitants.bomarrMonk2");
        attachScript(monk2, "theme_park.tatooine.jabbaspawner.palace_path");
        obj_id monk3 = getObjIdObjVar(self, "PalaceInhabitants.bomarrMonk3");
        attachScript(monk3, "theme_park.tatooine.jabbaspawner.palace_path");
        obj_id monk4 = getObjIdObjVar(self, "PalaceInhabitants.bomarrMonk4");
        attachScript(monk4, "theme_park.tatooine.jabbaspawner.palace_path");
        obj_id monk5 = getObjIdObjVar(self, "PalaceInhabitants.bomarrMonk5");
        attachScript(monk5, "theme_park.tatooine.jabbaspawner.palace_path");
        return SCRIPT_CONTINUE;
    }
    public void destroyCelebs(obj_id self) throws InterruptedException
    {
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.bib"));
        removeObjVar(self, "PalaceInhabitants.bib");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.oola"));
        removeObjVar(self, "PalaceInhabitants.oola");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.max"));
        removeObjVar(self, "PalaceInhabitants.max");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.sy"));
        removeObjVar(self, "PalaceInhabitants.sy");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.droopy"));
        removeObjVar(self, "PalaceInhabitants.droopy");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.jabba"));
        removeObjVar(self, "PalaceInhabitants.jabba");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.pDroid"));
        removeObjVar(self, "PalaceInhabitants.pDroid");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.barada"));
        removeObjVar(self, "PalaceInhabitants.barada");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.klaatu"));
        removeObjVar(self, "PalaceInhabitants.klaatu");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.porcellus"));
        removeObjVar(self, "PalaceInhabitants.porcellus");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.malakili"));
        removeObjVar(self, "PalaceInhabitants.malakili");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.rancor"));
        removeObjVar(self, "PalaceInhabitants.rancor");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.jabba2"));
        removeObjVar(self, "PalaceInhabitants.jabba2");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.boba"));
        removeObjVar(self, "PalaceInhabitants.boba");
    }
    public void destroyGuards(obj_id self) throws InterruptedException
    {
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.ephant"));
        removeObjVar(self, "PalaceInhabitants.ephant");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.gamGuard1"));
        removeObjVar(self, "PalaceInhabitants.gamGuard1");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.gamGuard2"));
        removeObjVar(self, "PalaceInhabitants.gamGuard2");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.gamGuard3"));
        removeObjVar(self, "PalaceInhabitants.gamGuard3");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.gamGuard4"));
        removeObjVar(self, "PalaceInhabitants.gamGuard4");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.gamGuard5"));
        removeObjVar(self, "PalaceInhabitants.gamGuard5");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.gamGuard6"));
        removeObjVar(self, "PalaceInhabitants.gamGuard6");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.gamGuard7"));
        removeObjVar(self, "PalaceInhabitants.gamGuard7");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.gamGuard8"));
        removeObjVar(self, "PalaceInhabitants.gamGuard8");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.gamGuard9"));
        removeObjVar(self, "PalaceInhabitants.gamGuard9");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.gamGuard10"));
        removeObjVar(self, "PalaceInhabitants.gamGuard10");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.gamGuard11"));
        removeObjVar(self, "PalaceInhabitants.gamGuard11");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.gamGuard12"));
        removeObjVar(self, "PalaceInhabitants.gamGuard12");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.gamGuard13"));
        removeObjVar(self, "PalaceInhabitants.gamGuard13");
    }
    public void destroyMonks(obj_id self) throws InterruptedException
    {
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.bomarrMonk1"));
        removeObjVar(self, "PalaceInhabitants.bomarrMonk1");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.bomarrMonk2"));
        removeObjVar(self, "PalaceInhabitants.bomarrMonk2");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.bomarrMonk3"));
        removeObjVar(self, "PalaceInhabitants.bomarrMonk3");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.bomarrMonk4"));
        removeObjVar(self, "PalaceInhabitants.bomarrMonk4");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.bomarrMonk5"));
        removeObjVar(self, "PalaceInhabitants.bomarrMonk5");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.bomarrMonk6"));
        removeObjVar(self, "PalaceInhabitants.bomarrMonk6");
    }
    public void destroyJawas(obj_id self) throws InterruptedException
    {
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.jawa1"));
        removeObjVar(self, "PalaceInhabitants.jawa1");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.jawa2"));
        removeObjVar(self, "PalaceInhabitants.jawa2");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.jawa3"));
        removeObjVar(self, "PalaceInhabitants.jawa3");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.jawa4"));
        removeObjVar(self, "PalaceInhabitants.jawa4");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.jawa5"));
        removeObjVar(self, "PalaceInhabitants.jawa5");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.jawa6"));
        removeObjVar(self, "PalaceInhabitants.jawa6");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.jawa7"));
        removeObjVar(self, "PalaceInhabitants.jawa7");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.jawa8"));
        removeObjVar(self, "PalaceInhabitants.jawa8");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.jawa9"));
        removeObjVar(self, "PalaceInhabitants.jawa9");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.jawa10"));
        removeObjVar(self, "PalaceInhabitants.jawa10");
    }
    public void destroyRandom(obj_id self) throws InterruptedException
    {
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.random1"));
        removeObjVar(self, "PalaceInhabitants.random1");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.random2"));
        removeObjVar(self, "PalaceInhabitants.random2");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.random3"));
        removeObjVar(self, "PalaceInhabitants.random3");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.random4"));
        removeObjVar(self, "PalaceInhabitants.random4");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.random5"));
        removeObjVar(self, "PalaceInhabitants.random5");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.random6"));
        removeObjVar(self, "PalaceInhabitants.random6");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.random7"));
        removeObjVar(self, "PalaceInhabitants.random7");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.random8"));
        removeObjVar(self, "PalaceInhabitants.random8");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.random9"));
        removeObjVar(self, "PalaceInhabitants.random9");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.random10"));
        removeObjVar(self, "PalaceInhabitants.random10");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.random11"));
        removeObjVar(self, "PalaceInhabitants.random11");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.random12"));
        removeObjVar(self, "PalaceInhabitants.random12");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.random13"));
        removeObjVar(self, "PalaceInhabitants.random13");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.random14"));
        removeObjVar(self, "PalaceInhabitants.random14");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.random15"));
        removeObjVar(self, "PalaceInhabitants.random15");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.random16"));
        removeObjVar(self, "PalaceInhabitants.random16");
    }
    public void destroyDroids(obj_id self) throws InterruptedException
    {
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.ev9d9"));
        removeObjVar(self, "PalaceInhabitants.ev9d9");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.droid1"));
        removeObjVar(self, "PalaceInhabitants.droid1");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.droid2"));
        removeObjVar(self, "PalaceInhabitants.droid2");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.droid3"));
        removeObjVar(self, "PalaceInhabitants.droid3");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.droid4"));
        removeObjVar(self, "PalaceInhabitants.droid4");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.droid5"));
        removeObjVar(self, "PalaceInhabitants.droid5");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.droid6"));
        removeObjVar(self, "PalaceInhabitants.droid6");
    }
    public int doGating(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id hall1 = getCellId(self, "hall1");
        obj_id display = getCellId(self, "display");
        obj_id throne = getCellId(self, "throneroom");
        obj_id garage = getCellId(self, "garage1");
        obj_id garage2 = getCellId(self, "garage2");
        obj_id hall8 = getCellId(self, "hall8");
        attachScript(hall1, "theme_park.gating.jabba.stairs_block");
        attachScript(garage2, "theme_park.gating.jabba.garage_block");
        attachScript(garage, "theme_park.gating.jabba.garage_block");
        attachScript(throne, "theme_park.gating.jabba.throne_block");
        attachScript(display, "theme_park.gating.jabba.throne_block");
        attachScript(hall8, "theme_park.gating.jabba.master_block");
        return SCRIPT_CONTINUE;
    }
    public void despawnEveryone(obj_id self) throws InterruptedException
    {
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.bib"));
        removeObjVar(self, "PalaceInhabitants.bib");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.ephant"));
        removeObjVar(self, "PalaceInhabitants.ephant");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.reelo"));
        removeObjVar(self, "PalaceInhabitants.reelo");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.crumb"));
        removeObjVar(self, "PalaceInhabitants.crumb");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.oola"));
        removeObjVar(self, "PalaceInhabitants.oola");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.max"));
        removeObjVar(self, "PalaceInhabitants.max");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.sy"));
        removeObjVar(self, "PalaceInhabitants.sy");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.droopy"));
        removeObjVar(self, "PalaceInhabitants.droopy");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.jabba"));
        removeObjVar(self, "PalaceInhabitants.jabba");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.pDroid"));
        removeObjVar(self, "PalaceInhabitants.pDroid");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.barada"));
        removeObjVar(self, "PalaceInhabitants.barada");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.klaatu"));
        removeObjVar(self, "PalaceInhabitants.klaatu");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.porcellus"));
        removeObjVar(self, "PalaceInhabitants.porcellus");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.malakili"));
        removeObjVar(self, "PalaceInhabitants.malakili");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.rancor"));
        removeObjVar(self, "PalaceInhabitants.rancor");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.jabba2"));
        removeObjVar(self, "PalaceInhabitants.jabba2");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.boba"));
        removeObjVar(self, "PalaceInhabitants.boba");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.gamGuard1"));
        removeObjVar(self, "PalaceInhabitants.gamGuard1");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.gamGuard2"));
        removeObjVar(self, "PalaceInhabitants.gamGuard2");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.gamGuard3"));
        removeObjVar(self, "PalaceInhabitants.gamGuard3");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.gamGuard4"));
        removeObjVar(self, "PalaceInhabitants.gamGuard4");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.gamGuard5"));
        removeObjVar(self, "PalaceInhabitants.gamGuard5");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.gamGuard6"));
        removeObjVar(self, "PalaceInhabitants.gamGuard6");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.gamGuard7"));
        removeObjVar(self, "PalaceInhabitants.gamGuard7");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.gamGuard8"));
        removeObjVar(self, "PalaceInhabitants.gamGuard8");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.gamGuard9"));
        removeObjVar(self, "PalaceInhabitants.gamGuard9");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.gamGuard10"));
        removeObjVar(self, "PalaceInhabitants.gamGuard10");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.gamGuard11"));
        removeObjVar(self, "PalaceInhabitants.gamGuard11");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.gamGuard12"));
        removeObjVar(self, "PalaceInhabitants.gamGuard12");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.gamGuard13"));
        removeObjVar(self, "PalaceInhabitants.gamGuard13");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.bomarrMonk1"));
        removeObjVar(self, "PalaceInhabitants.bomarrMonk1");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.bomarrMonk2"));
        removeObjVar(self, "PalaceInhabitants.bomarrMonk2");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.bomarrMonk3"));
        removeObjVar(self, "PalaceInhabitants.bomarrMonk3");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.bomarrMonk4"));
        removeObjVar(self, "PalaceInhabitants.bomarrMonk4");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.bomarrMonk5"));
        removeObjVar(self, "PalaceInhabitants.bomarrMonk5");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.bomarrMonk6"));
        removeObjVar(self, "PalaceInhabitants.bomarrMonk6");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.jawa1"));
        removeObjVar(self, "PalaceInhabitants.jawa1");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.jawa2"));
        removeObjVar(self, "PalaceInhabitants.jawa2");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.jawa3"));
        removeObjVar(self, "PalaceInhabitants.jawa3");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.jawa4"));
        removeObjVar(self, "PalaceInhabitants.jawa4");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.jawa5"));
        removeObjVar(self, "PalaceInhabitants.jawa5");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.jawa6"));
        removeObjVar(self, "PalaceInhabitants.jawa6");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.random1"));
        removeObjVar(self, "PalaceInhabitants.random1");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.random2"));
        removeObjVar(self, "PalaceInhabitants.random2");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.random3"));
        removeObjVar(self, "PalaceInhabitants.random3");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.random4"));
        removeObjVar(self, "PalaceInhabitants.random4");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.random5"));
        removeObjVar(self, "PalaceInhabitants.random5");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.random6"));
        removeObjVar(self, "PalaceInhabitants.random6");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.random7"));
        removeObjVar(self, "PalaceInhabitants.random7");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.random8"));
        removeObjVar(self, "PalaceInhabitants.random8");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.random9"));
        removeObjVar(self, "PalaceInhabitants.random9");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.random10"));
        removeObjVar(self, "PalaceInhabitants.random10");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.random11"));
        removeObjVar(self, "PalaceInhabitants.random11");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.random12"));
        removeObjVar(self, "PalaceInhabitants.random12");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.random13"));
        removeObjVar(self, "PalaceInhabitants.random13");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.random14"));
        removeObjVar(self, "PalaceInhabitants.random14");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.random15"));
        removeObjVar(self, "PalaceInhabitants.random15");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.random16"));
        removeObjVar(self, "PalaceInhabitants.random16");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.reeyees"));
        removeObjVar(self, "PalaceInhabitants.reeyees");
        destroyObject(getObjIdObjVar(self, "PalaceInhabitants.jawl"));
        removeObjVar(self, "PalaceInhabitants.jawl");
        return;
    }
}
