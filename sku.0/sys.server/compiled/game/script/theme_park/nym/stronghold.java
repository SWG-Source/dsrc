package script.theme_park.nym;

import script.dictionary;
import script.library.ai_lib;
import script.library.create;
import script.location;
import script.obj_id;

public class stronghold extends script.base_script
{
    public stronghold()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        spawnEveryone(self);
        return SCRIPT_CONTINUE;
    }
    public void spawnEveryone(obj_id self) throws InterruptedException
    {
        spawnCelebs(self);
        messageTo(self, "spawnGuards", null, 5, true);
        messageTo(self, "spawnPirates", null, 10, true);
        messageTo(self, "spawnParty", null, 15, true);
        messageTo(self, "spawnElevatorHandler", null, 5, true);
        return;
    }
    public void spawnCelebs(obj_id self) throws InterruptedException
    {
        obj_id nym = spawnNym(self);
        spawnKole(self, nym);
        spawnJinkins(self, nym);
        spawnDog1(self);
        spawnDog2(self);
        spawnVanaSage(self, nym);
        spawnMakoGhast(self, nym);
        spawnGrenzZittoun(self, nym);
        spawnStuvanyInglen(self, nym);
        spawnRek(self, nym);
        spawnSharphorn(self, nym);
    }
    public obj_id spawnNym(obj_id self) throws InterruptedException
    {
        obj_id lobby = getCellId(self, "lobby");
        location nymLocation = new location(-0.06f, 3.28f, -22.90f, "lok", lobby);
        obj_id nym = create.staticObject("nym", nymLocation);
        setObjVar(self, "StrongholdInhabitants.nym", nym);
        setAnimationMood(nym, "npc_sitting_chair");
        setYaw(nym, -4.0f);
        setObjVar(nym, "Stronghold", self);
        return nym;
    }
    public void spawnKole(obj_id self, obj_id nym) throws InterruptedException
    {
        obj_id lobby = getCellId(self, "lobby");
        location koleLocation = new location(-1.83f, 3.34f, -19.11f, "lok", lobby);
        obj_id kole = create.staticObject("kole", koleLocation);
        setObjVar(self, "StrongholdInhabitants.kole", kole);
        setObjVar(kole, "Stronghold", self);
        faceTo(kole, nym);
        return;
    }
    public void spawnJinkins(obj_id self, obj_id nym) throws InterruptedException
    {
        obj_id lobby = getCellId(self, "lobby");
        location jinkinsLocation = new location(3.07f, 3.29f, -21.31f, "lok", lobby);
        obj_id jinkins = create.staticObject("jinkins", jinkinsLocation);
        setObjVar(self, "StrongholdInhabitants.jinkins", jinkins);
        setObjVar(jinkins, "Stronghold", self);
        faceTo(jinkins, nym);
        return;
    }
    public void spawnVanaSage(obj_id self, obj_id nym) throws InterruptedException
    {
        obj_id lobby = getCellId(self, "lobby");
        location vanaLocation = new location(0.177338f, 3.31568f, -15.9878f, "lok", lobby);
        obj_id vana = create.staticObject("nym_themepark_vana_sage", vanaLocation);
        setObjVar(self, "StrongholdInhabitants.vana", vana);
        setObjVar(vana, "Stronghold", self);
        faceTo(vana, nym);
        return;
    }
    public void spawnMakoGhast(obj_id self, obj_id nym) throws InterruptedException
    {
        obj_id lobby = getCellId(self, "lobby");
        location ghastLocation = new location(5.65913f, 4.07455f, -13.7886f, "lok", lobby);
        obj_id ghast = create.staticObject("nym_themepark_mako_ghast", ghastLocation);
        setObjVar(self, "StrongholdInhabitants.ghast", ghast);
        setObjVar(ghast, "Stronghold", self);
        faceTo(ghast, nym);
        return;
    }
    public void spawnGrenzZittoun(obj_id self, obj_id nym) throws InterruptedException
    {
        obj_id lobby = getCellId(self, "lobby");
        location grenzLocation = new location(1.84116f, 4.0782f, -10.7134f, "lok", lobby);
        obj_id grenz = create.staticObject("nym_themepark_grenz_zittoun", grenzLocation);
        setObjVar(self, "StrongholdInhabitants.grenz", grenz);
        setObjVar(grenz, "Stronghold", self);
        faceTo(grenz, nym);
        return;
    }
    public void spawnStuvanyInglen(obj_id self, obj_id nym) throws InterruptedException
    {
        obj_id lobby = getCellId(self, "lobby");
        location inglenLocation = new location(5.22391f, 3.38231f, -20.5942f, "lok", lobby);
        obj_id inglen = create.staticObject("nym_themepark_stuvany_inglen", inglenLocation);
        setObjVar(self, "StrongholdInhabitants.inglen", inglen);
        setObjVar(inglen, "Stronghold", self);
        faceTo(inglen, nym);
        return;
    }
    public void spawnRek(obj_id self, obj_id nym) throws InterruptedException
    {
        obj_id lobby = getCellId(self, "lobby");
        location rekLocation = new location(-5.31335f, 3.42358f, -20.4917f, "lok", lobby);
        obj_id rek = create.staticObject("nym_themepark_rek_thelcar", rekLocation);
        setObjVar(self, "StrongholdInhabitants.rek", rek);
        setObjVar(rek, "Stronghold", self);
        faceTo(rek, nym);
        return;
    }
    public void spawnSharphorn(obj_id self, obj_id nym) throws InterruptedException
    {
        obj_id lobby = getCellId(self, "lobby");
        location sharphornLocation = new location(-5.56208f, 1.00421f, 8.15896f, "lok", lobby);
        obj_id sharphorn = create.staticObject("slicer_kelson_sharphorn", sharphornLocation);
        setObjVar(self, "StrongholdInhabitants.sharphorn", sharphorn);
        setObjVar(sharphorn, "Stronghold", self);
        faceTo(sharphorn, nym);
        return;
    }
    public void spawnElite1(obj_id self) throws InterruptedException
    {
        obj_id lobby = getCellId(self, "lobby");
        location e1Loc = new location(3.12f, 4.08f, -9.55f, "lok", lobby);
        obj_id guard1 = create.staticObject("nym_guard_elite", e1Loc);
        setObjVar(self, "StrongholdInhabitants.guard1", guard1);
        setObjVar(guard1, "Stronghold", self);
        aiEquipPrimaryWeapon(guard1);
        setYaw(guard1, -1);
        return;
    }
    public void spawnElite2(obj_id self) throws InterruptedException
    {
        obj_id lobby = getCellId(self, "lobby");
        location e2Loc = new location(-3.12f, 4.08f, -9.55f, "lok", lobby);
        obj_id guard2 = create.staticObject("nym_guard_elite", e2Loc);
        setObjVar(self, "StrongholdInhabitants.guard2", guard2);
        setObjVar(guard2, "Stronghold", self);
        aiEquipPrimaryWeapon(guard2);
        setYaw(guard2, -1);
        return;
    }
    public void spawnElite3(obj_id self) throws InterruptedException
    {
        obj_id lobby = getCellId(self, "lobby");
        location e1Loc = new location(1.98f, 8.9f, -30.50f, "lok", lobby);
        obj_id guard3 = create.staticObject("nym_guard_elite", e1Loc);
        setObjVar(self, "StrongholdInhabitants.guard3", guard3);
        setObjVar(guard3, "Stronghold", self);
        aiEquipPrimaryWeapon(guard3);
        setYaw(guard3, 0.0f);
        return;
    }
    public void spawnElite4(obj_id self) throws InterruptedException
    {
        obj_id lobby = getCellId(self, "lobby");
        location e2Loc = new location(-1.91f, 8.9f, -30.50f, "lok", lobby);
        obj_id guard4 = create.staticObject("nym_guard_elite", e2Loc);
        setObjVar(self, "StrongholdInhabitants.guard4", guard4);
        setObjVar(guard4, "Stronghold", self);
        aiEquipPrimaryWeapon(guard4);
        setYaw(guard4, 0.0f);
        return;
    }
    public void spawnElite5(obj_id self) throws InterruptedException
    {
        obj_id lobby = getCellId(self, "lobby");
        location e1Loc = new location(-18.91f, 2.3f, 9.74f, "lok", lobby);
        obj_id guard5 = create.staticObject("nym_guard_strong", e1Loc);
        setObjVar(self, "StrongholdInhabitants.guard5", guard5);
        setObjVar(guard5, "Stronghold", self);
        aiEquipPrimaryWeapon(guard5);
        setYaw(guard5, 178.0f);
        return;
    }
    public void spawnElite6(obj_id self) throws InterruptedException
    {
        obj_id lobby = getCellId(self, "lobby");
        location e2Loc = new location(18.98f, 2.3f, 10.51f, "lok", lobby);
        obj_id guard6 = create.staticObject("nym_guard_weak", e2Loc);
        setObjVar(self, "StrongholdInhabitants.guard6", guard6);
        setObjVar(guard6, "Stronghold", self);
        aiEquipPrimaryWeapon(guard6);
        setYaw(guard6, -179.0f);
        return;
    }
    public void spawnElite7(obj_id self) throws InterruptedException
    {
        obj_id lobby = getCellId(self, "lobby");
        location e1Loc = new location(-20.26f, 2.3f, -18.62f, "lok", lobby);
        obj_id guard7 = create.staticObject("nym_guard_weak", e1Loc);
        setObjVar(self, "StrongholdInhabitants.guard7", guard7);
        setObjVar(guard7, "Stronghold", self);
        aiEquipPrimaryWeapon(guard7);
        setYaw(guard7, 3.0f);
        return;
    }
    public void spawnElite8(obj_id self) throws InterruptedException
    {
        obj_id meeting2 = getCellId(self, "meeting2");
        location e1Loc = new location(-28.92f, 2.3f, 0.73f, "lok", meeting2);
        obj_id guard8 = create.staticObject("nym_bodyguard", e1Loc);
        setObjVar(self, "StrongholdInhabitants.guard8", guard8);
        setObjVar(guard8, "Stronghold", self);
        aiEquipPrimaryWeapon(guard8);
        attachScript(guard8, "theme_park.nym.guard_path");
        return;
    }
    public void spawnPirate1(obj_id self) throws InterruptedException
    {
        obj_id meeting5 = getCellId(self, "meeting5");
        location p1Loc = new location(37.49f, 2.29f, -1.17f, "lok", meeting5);
        obj_id pirate1 = create.staticObject("nym_pirate_elite", p1Loc);
        setObjVar(self, "StrongholdInhabitants.pirate1", pirate1);
        setObjVar(pirate1, "Stronghold", self);
        ai_lib.setDefaultCalmMood(pirate1, "npc_accusing");
        setYaw(pirate1, 88);
        return;
    }
    public void spawnPirate2(obj_id self) throws InterruptedException
    {
        obj_id meeting5 = getCellId(self, "meeting5");
        location p2Loc = new location(36.71f, 2.29f, -2.92f, "lok", meeting5);
        obj_id pirate2 = create.staticObject("nym_pirate_elite", p2Loc);
        setObjVar(self, "StrongholdInhabitants.pirate2", pirate2);
        setObjVar(pirate2, "Stronghold", self);
        ai_lib.setDefaultCalmMood(pirate2, "npc_angry");
        setYaw(pirate2, 88);
        return;
    }
    public void spawnBoard(obj_id self) throws InterruptedException
    {
        obj_id meeting5 = getCellId(self, "meeting5");
        location boardLoc = new location(39.46f, 2.29f, -2.39f, "lok", meeting5);
        obj_id board = create.staticObject("object/tangible/furniture/all/frn_all_desk_radar_topology_screen.iff", boardLoc);
        setObjVar(self, "StrongholdInhabitants.board", board);
        setObjVar(board, "Stronghold", self);
        setYaw(board, 88);
        return;
    }
    public obj_id spawnPartygoer1(obj_id self) throws InterruptedException
    {
        obj_id lobby = getCellId(self, "lobby");
        location partygoer1Location = new location(-8.11f, 1.3f, -1.06f, "lok", lobby);
        obj_id partygoer1 = create.staticObject("nym_brawler", partygoer1Location);
        setObjVar(self, "StrongholdInhabitants.partygoer1", partygoer1);
        ai_lib.setDefaultCalmMood(partygoer1, "conversation");
        setYaw(partygoer1, 177.0f);
        setObjVar(partygoer1, "Stronghold", self);
        return partygoer1;
    }
    public obj_id spawnPartygoer2(obj_id self) throws InterruptedException
    {
        obj_id lobby = getCellId(self, "lobby");
        location partygoer2Location = new location(-8.05f, 1.3f, -2.3f, "lok", lobby);
        obj_id partygoer2 = create.staticObject("nym_surveyer", partygoer2Location);
        setObjVar(self, "StrongholdInhabitants.partygoer2", partygoer2);
        ai_lib.setDefaultCalmMood(partygoer2, "conversation");
        setYaw(partygoer2, 7.0f);
        setObjVar(partygoer2, "Stronghold", self);
        return partygoer2;
    }
    public obj_id spawnPartygoer3(obj_id self) throws InterruptedException
    {
        obj_id lobby = getCellId(self, "lobby");
        location partygoer3Location = new location(4.17f, 1.3f, -5.62f, "lok", lobby);
        obj_id partygoer3 = create.staticObject("nym_brawler", partygoer3Location);
        setObjVar(self, "StrongholdInhabitants.partygoer3", partygoer3);
        ai_lib.setDefaultCalmMood(partygoer3, "conversation");
        setYaw(partygoer3, 33.0f);
        setObjVar(partygoer3, "Stronghold", self);
        return partygoer3;
    }
    public obj_id spawnPartygoer4(obj_id self) throws InterruptedException
    {
        obj_id lobby = getCellId(self, "lobby");
        location partygoer4Location = new location(5.86f, 1.3f, -5.04f, "lok", lobby);
        obj_id partygoer4 = create.staticObject("nym_bodyguard", partygoer4Location);
        setObjVar(self, "StrongholdInhabitants.partygoer4", partygoer4);
        ai_lib.setDefaultCalmMood(partygoer4, "conversation");
        setYaw(partygoer4, -77.0f);
        setObjVar(partygoer4, "Stronghold", self);
        return partygoer4;
    }
    public obj_id spawnPartygoer5(obj_id self) throws InterruptedException
    {
        obj_id lobby = getCellId(self, "lobby");
        location partygoer5Location = new location(4.39f, 1.3f, -3.87f, "lok", lobby);
        obj_id partygoer5 = create.staticObject("nym_surveyer", partygoer5Location);
        setObjVar(self, "StrongholdInhabitants.partygoer5", partygoer5);
        ai_lib.setDefaultCalmMood(partygoer5, "conversation");
        setYaw(partygoer5, 149.0f);
        setObjVar(partygoer5, "Stronghold", self);
        return partygoer5;
    }
    public obj_id spawnPartygoer6(obj_id self) throws InterruptedException
    {
        obj_id lobby = getCellId(self, "lobby");
        location partygoer6Location = new location(-3.58f, 1.3f, 4.01f, "lok", lobby);
        obj_id partygoer6 = create.staticObject("nym_bodyguard", partygoer6Location);
        setObjVar(self, "StrongholdInhabitants.partygoer6", partygoer6);
        ai_lib.setDefaultCalmMood(partygoer6, "conversation");
        setYaw(partygoer6, 89.0f);
        setObjVar(partygoer6, "Stronghold", self);
        return partygoer6;
    }
    public obj_id spawnPartygoer7(obj_id self) throws InterruptedException
    {
        obj_id lobby = getCellId(self, "lobby");
        location partygoer7Location = new location(-1.79f, 1.3f, 3.94f, "lok", lobby);
        obj_id partygoer7 = create.staticObject("nym_destroyer", partygoer7Location);
        setObjVar(self, "StrongholdInhabitants.partygoer7", partygoer7);
        ai_lib.setDefaultCalmMood(partygoer7, "conversation");
        setYaw(partygoer7, -93.0f);
        setObjVar(partygoer7, "Stronghold", self);
        return partygoer7;
    }
    public obj_id spawnDog1(obj_id self) throws InterruptedException
    {
        obj_id lobby = getCellId(self, "lobby");
        location dog1Location = new location(-3.62f, 3.28f, -25.44f, "lok", lobby);
        obj_id dog1 = create.object("nym_kusak_guardian", dog1Location);
        setObjVar(self, "StrongholdInhabitants.dog1", dog1);
        setYaw(dog1, 39.0f);
        ai_lib.setDefaultCalmBehavior(dog1, ai_lib.BEHAVIOR_SENTINEL);
        setName(dog1, "Scourge");
        dictionary dogs = new dictionary();
        dogs.put("dog", dog1);
        messageTo(self, "makeDogSit", dogs, 5, true);
        setObjVar(dog1, "Stronghold", self);
        setInvulnerable(dog1, true);
        return dog1;
    }
    public obj_id spawnDog2(obj_id self) throws InterruptedException
    {
        obj_id lobby = getCellId(self, "lobby");
        location dog2Location = new location(3.62f, 3.28f, -25.44f, "lok", lobby);
        obj_id dog2 = create.object("nym_kusak_guardian", dog2Location);
        setObjVar(self, "StrongholdInhabitants.dog2", dog2);
        setYaw(dog2, -35.0f);
        ai_lib.setDefaultCalmMood(dog2, "bored");
        ai_lib.setDefaultCalmBehavior(dog2, ai_lib.BEHAVIOR_SENTINEL);
        setName(dog2, "Razor");
        dictionary dogs = new dictionary();
        dogs.put("dog", dog2);
        messageTo(self, "makeDogSit", dogs, 5, true);
        setObjVar(dog2, "Stronghold", self);
        setInvulnerable(dog2, true);
        return dog2;
    }
    public boolean spawnElevator(obj_id self) throws InterruptedException
    {
        obj_id lobby = getCellId(self, "lobby");
        if (!isValidId(lobby))
        {
            return false;
        }
        location elevatorOneLoc = new location(0.0f, 8.9f, -30.5f, "lok", lobby);
        location elevatorTwoLoc = new location(0.0f, 13.1f, -30.5f, "lok", lobby);
        location hatchBaseLoc = new location(-0.05f, 18.5f, -31.5f, "lok", lobby);
        obj_id elevatorOne = createObject("object/tangible/theme_park/nym/ladder_to_roof.iff", elevatorOneLoc);
        if (!isValidId(elevatorOne))
        {
            return false;
        }
        obj_id elevatorTwo = createObject("object/tangible/theme_park/nym/ladder_to_roof.iff", elevatorTwoLoc);
        if (!isValidId(elevatorTwo))
        {
            return false;
        }
        obj_id hatchBase = createObject("object/static/item/item_medic_bacta_tank_large.iff", hatchBaseLoc);
        if (!isValidId(elevatorOne))
        {
            return false;
        }
        modifyPitch(hatchBase, -180.0f);
        setYaw(elevatorOne, 180.0f);
        setYaw(elevatorTwo, 180.0f);
        return true;
    }
    public int OnHearSpeech(obj_id self, obj_id speaker, String text) throws InterruptedException
    {
        if (!hasObjVar(speaker, "gm"))
        {
            return SCRIPT_CONTINUE;
        }
        if (text.equals("celebs"))
        {
            spawnCelebs(self);
        }
        if (text.equals("guards"))
        {
            messageTo(self, "spawnGuards", null, 0, true);
        }
        if (text.equals("party"))
        {
            messageTo(self, "spawnParty", null, 0, true);
        }
        if (text.equals("kill celebs"))
        {
            killCelebs(self);
        }
        if (text.equals("kill guards"))
        {
            killGuards(self);
        }
        if (text.equals("pirates"))
        {
            messageTo(self, "spawnPirates", null, 0, true);
        }
        if (text.equals("kill pirates"))
        {
            killPirates(self);
        }
        if (text.equals("kill party"))
        {
            killParty(self);
        }
        if (text.equals("kill all"))
        {
            killAll(self);
        }
        return SCRIPT_CONTINUE;
    }
    public void killAll(obj_id self) throws InterruptedException
    {
        killCelebs(self);
        killGuards(self);
        killPirates(self);
        return;
    }
    public void killCelebs(obj_id self) throws InterruptedException
    {
        destroyObject(getObjIdObjVar(self, "StrongholdInhabitants.nym"));
        removeObjVar(self, "StrongholdInhabitants.nym");
        destroyObject(getObjIdObjVar(self, "StrongholdInhabitants.kole"));
        removeObjVar(self, "StrongholdInhabitants.kole");
        destroyObject(getObjIdObjVar(self, "StrongholdInhabitants.jinkins"));
        removeObjVar(self, "StrongholdInhabitants.jinkins");
        destroyObject(getObjIdObjVar(self, "StrongholdInhabitants.dog1"));
        removeObjVar(self, "StrongholdInhabitants.dog1");
        destroyObject(getObjIdObjVar(self, "StrongholdInhabitants.dog2"));
        removeObjVar(self, "StrongholdInhabitants.dog2");
        return;
    }
    public void killGuards(obj_id self) throws InterruptedException
    {
        destroyObject(getObjIdObjVar(self, "StrongholdInhabitants.guard1"));
        removeObjVar(self, "StrongholdInhabitants.guard1");
        destroyObject(getObjIdObjVar(self, "StrongholdInhabitants.guard2"));
        removeObjVar(self, "StrongholdInhabitants.guard2");
        destroyObject(getObjIdObjVar(self, "StrongholdInhabitants.guard3"));
        removeObjVar(self, "StrongholdInhabitants.guard3");
        destroyObject(getObjIdObjVar(self, "StrongholdInhabitants.guard4"));
        removeObjVar(self, "StrongholdInhabitants.guard4");
        destroyObject(getObjIdObjVar(self, "StrongholdInhabitants.guard5"));
        removeObjVar(self, "StrongholdInhabitants.guard5");
        destroyObject(getObjIdObjVar(self, "StrongholdInhabitants.guard6"));
        removeObjVar(self, "StrongholdInhabitants.guard6");
        destroyObject(getObjIdObjVar(self, "StrongholdInhabitants.guard7"));
        removeObjVar(self, "StrongholdInhabitants.guard7");
        destroyObject(getObjIdObjVar(self, "StrongholdInhabitants.guard8"));
        removeObjVar(self, "StrongholdInhabitants.guard8");
        return;
    }
    public void killPirates(obj_id self) throws InterruptedException
    {
        destroyObject(getObjIdObjVar(self, "StrongholdInhabitants.pirate1"));
        removeObjVar(self, "StrongholdInhabitants.pirate1");
        destroyObject(getObjIdObjVar(self, "StrongholdInhabitants.pirate2"));
        removeObjVar(self, "StrongholdInhabitants.pirate2");
        destroyObject(getObjIdObjVar(self, "StrongholdInhabitants.pirate3"));
        removeObjVar(self, "StrongholdInhabitants.pirate3");
        destroyObject(getObjIdObjVar(self, "StrongholdInhabitants.pirate4"));
        removeObjVar(self, "StrongholdInhabitants.pirate4");
        destroyObject(getObjIdObjVar(self, "StrongholdInhabitants.pirate5"));
        removeObjVar(self, "StrongholdInhabitants.pirate5");
        destroyObject(getObjIdObjVar(self, "StrongholdInhabitants.board"));
        removeObjVar(self, "StrongholdInhabitants.board");
        destroyObject(getObjIdObjVar(self, "StrongholdInhabitants.futon"));
        removeObjVar(self, "StrongholdInhabitants.futon");
    }
    public void killParty(obj_id self) throws InterruptedException
    {
        destroyObject(getObjIdObjVar(self, "StrongholdInhabitants.partygoer1"));
        removeObjVar(self, "StrongholdInhabitants.partygoer1");
        destroyObject(getObjIdObjVar(self, "StrongholdInhabitants.partygoer2"));
        removeObjVar(self, "StrongholdInhabitants.partygoer2");
        destroyObject(getObjIdObjVar(self, "StrongholdInhabitants.partygoer3"));
        removeObjVar(self, "StrongholdInhabitants.partygoer3");
        destroyObject(getObjIdObjVar(self, "StrongholdInhabitants.partygoer4"));
        removeObjVar(self, "StrongholdInhabitants.partygoer4");
        destroyObject(getObjIdObjVar(self, "StrongholdInhabitants.partygoer5"));
        removeObjVar(self, "StrongholdInhabitants.partygoer5");
        destroyObject(getObjIdObjVar(self, "StrongholdInhabitants.partygoer6"));
        removeObjVar(self, "StrongholdInhabitants.partygoer6");
        destroyObject(getObjIdObjVar(self, "StrongholdInhabitants.partygoer7"));
        removeObjVar(self, "StrongholdInhabitants.partygoer7");
    }
    public int spawnGuards(obj_id self, dictionary params) throws InterruptedException
    {
        spawnElite1(self);
        spawnElite2(self);
        spawnElite3(self);
        spawnElite4(self);
        spawnElite5(self);
        spawnElite6(self);
        spawnElite7(self);
        spawnElite8(self);
        return SCRIPT_CONTINUE;
    }
    public int spawnPirates(obj_id self, dictionary params) throws InterruptedException
    {
        spawnPirate1(self);
        spawnPirate2(self);
        spawnBoard(self);
        return SCRIPT_CONTINUE;
    }
    public int spawnParty(obj_id self, dictionary params) throws InterruptedException
    {
        spawnPartygoer1(self);
        spawnPartygoer2(self);
        spawnPartygoer3(self);
        spawnPartygoer4(self);
        spawnPartygoer5(self);
        spawnPartygoer6(self);
        spawnPartygoer7(self);
        return SCRIPT_CONTINUE;
    }
    public int makeDogSit(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id dog1 = params.getObjId("dog");
        ai_lib.aiSetPosture(dog1, POSTURE_SITTING);
        return SCRIPT_CONTINUE;
    }
    public int spawnElevatorHandler(obj_id self, dictionary params) throws InterruptedException
    {
        spawnElevator(self);
        return SCRIPT_CONTINUE;
    }
}
