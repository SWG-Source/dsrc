package script.npc.skillteacher;

import script.dictionary;
import script.library.create;
import script.location;
import script.obj_id;

public class combat_trainer_spawner extends script.base_script
{
    public combat_trainer_spawner()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        debugServerConsoleMsg(self, "Attached Combat Guild Spawner in OnInitialize");
        dictionary params = new dictionary();
        messageTo(self, "spawnThings", null, 20, true);
        return SCRIPT_CONTINUE;
    }
    public void spawnEveryone(obj_id self) throws InterruptedException
    {
        spawnJunkdealer(self);
        spawnDeliverMission(self);
        spawnDestroyMission(self);
        spawnMarksman(self);
        spawnScout(self);
        spawnBrawler(self);
        spawnBountyHunter(self);
        spawnRifleman(self);
        spawnCarbineer(self);
        spawnPistoleer(self);
        spawnFencer(self);
        spawnSwordsman(self);
        spawnPikeman(self);
        spawnTerasKasi(self);
        spawnBioEngineer(self);
        spawnCreatureHandler(self);
        spawnRanger(self);
        spawnSmuggler(self);
        spawnSquadLeader(self);
        return;
    }
    public void spawnMarksman(obj_id self) throws InterruptedException
    {
        obj_id room = getCellId(self, "meetingd");
        location marksLoc = new location(0.0f, 1.13f, -14.48f, "tatooine", room);
        obj_id marksman = create.object("trainer_marksman", marksLoc);
        create.addDestroyMessage(marksman, "marksmanDied", 10.0f, self);
        setCreatureStatic(marksman, true);
        setInvulnerable(marksman, true);
        setYaw(marksman, 0);
        return;
    }
    public void spawnScout(obj_id self) throws InterruptedException
    {
        debugServerConsoleMsg(self, "Spawning Scout");
        obj_id room = getCellId(self, "meetingb");
        location scoutloc = new location(-11.98f, 1.13f, 5.00f, "tatooine", room);
        obj_id scout = create.object("trainer_scout", scoutloc);
        create.addDestroyMessage(scout, "scoutDied", 10.0f, self);
        setCreatureStatic(scout, true);
        setInvulnerable(scout, true);
        setYaw(scout, 179);
        return;
    }
    public void spawnBioEngineer(obj_id self) throws InterruptedException
    {
        obj_id room = getCellId(self, "meetingb");
        location loc = new location(-11.0f, 1.13f, 5.00f, "tatooine", room);
        obj_id trainer = create.object("trainer_bioengineer", loc);
        create.addDestroyMessage(trainer, "bioengineerDied", 10.0f, self);
        setCreatureStatic(trainer, true);
        setInvulnerable(trainer, true);
        setYaw(trainer, 179);
        return;
    }
    public void spawnCreatureHandler(obj_id self) throws InterruptedException
    {
        obj_id room = getCellId(self, "meetingb");
        location loc = new location(-8.5f, 1.13f, 5.0f, "tatooine", room);
        obj_id trainer = create.object("trainer_creaturehandler", loc);
        create.addDestroyMessage(trainer, "creaturehandlerDied", 10.0f, self);
        setCreatureStatic(trainer, true);
        setInvulnerable(trainer, true);
        setYaw(trainer, 179);
        return;
    }
    public void spawnRanger(obj_id self) throws InterruptedException
    {
        obj_id room = getCellId(self, "meetingd");
        location loc = new location(1.5f, 1.13f, -11.5f, "tatooine", room);
        obj_id trainer = create.object("trainer_ranger", loc);
        create.addDestroyMessage(trainer, "rangerDied", 10.0f, self);
        setCreatureStatic(trainer, true);
        setInvulnerable(trainer, true);
        setYaw(trainer, 270);
        return;
    }
    public void spawnSmuggler(obj_id self) throws InterruptedException
    {
        obj_id room = getCellId(self, "meetinge");
        location loc = new location(-12.5f, 1.13f, -11.5f, "tatooine", room);
        obj_id trainer = create.object("trainer_smuggler", loc);
        create.addDestroyMessage(trainer, "smugglerDied", 10.0f, self);
        setCreatureStatic(trainer, true);
        setInvulnerable(trainer, true);
        setYaw(trainer, 0);
        return;
    }
    public void spawnSquadLeader(obj_id self) throws InterruptedException
    {
        obj_id room = getCellId(self, "meetingd");
        location loc = new location(-1.5f, 1.13f, -11.5f, "tatooine", room);
        obj_id trainer = create.object("trainer_squadleader", loc);
        create.addDestroyMessage(trainer, "squadleaderDied", 10.0f, self);
        setCreatureStatic(trainer, true);
        setInvulnerable(trainer, true);
        setYaw(trainer, 90);
        return;
    }
    public void spawnJunkdealer(obj_id self) throws InterruptedException
    {
        debugServerConsoleMsg(self, "Spawning Junkdealer");
        obj_id room = getCellId(self, "meetingb");
        location dealerloc = new location(-14.56f, 1.13f, 2.85f, "tatooine", room);
        obj_id dealer = create.object("junk_dealer", dealerloc);
        create.addDestroyMessage(dealer, "dealerDied", 10.0f, self);
        setCreatureStatic(dealer, true);
        setInvulnerable(dealer, true);
        setYaw(dealer, 92);
        return;
    }
    public void spawnBrawler(obj_id self) throws InterruptedException
    {
        obj_id room = getCellId(self, "meetinge");
        location brawlerloc = new location(-11.13f, 1.13f, -13.98f, "tatooine", room);
        obj_id brawler = create.object("trainer_brawler", brawlerloc);
        create.addDestroyMessage(brawler, "brawlerDied", 10.0f, self);
        setCreatureStatic(brawler, true);
        setInvulnerable(brawler, true);
        setYaw(brawler, 0);
        return;
    }
    public void spawnBountyHunter(obj_id self) throws InterruptedException
    {
        obj_id room = getCellId(self, "meetinga");
        location loc = new location(13.0f, 1.13f, 0.0f, "tatooine", room);
        obj_id trainer = create.object("trainer_bountyhunter", loc);
        create.addDestroyMessage(trainer, "bountyhunterDied", 10.0f, self);
        setCreatureStatic(trainer, true);
        setInvulnerable(trainer, true);
        setYaw(trainer, 0);
        return;
    }
    public void spawnRifleman(obj_id self) throws InterruptedException
    {
        obj_id room = getCellId(self, "meetingd");
        location loc = new location(-4.0f, 1.13f, -13.0f, "tatooine", room);
        obj_id trainer = create.object("trainer_rifleman", loc);
        create.addDestroyMessage(trainer, "riflemanDied", 10.0f, self);
        setCreatureStatic(trainer, true);
        setInvulnerable(trainer, true);
        setYaw(trainer, 0);
        return;
    }
    public void spawnCarbineer(obj_id self) throws InterruptedException
    {
        obj_id room = getCellId(self, "meetingd");
        location loc = new location(-2.0f, 1.13f, -14.48f, "tatooine", room);
        obj_id trainer = create.object("trainer_carbine", loc);
        create.addDestroyMessage(trainer, "carbineerDied", 10.0f, self);
        setCreatureStatic(trainer, true);
        setInvulnerable(trainer, true);
        setYaw(trainer, 0);
        return;
    }
    public void spawnPistoleer(obj_id self) throws InterruptedException
    {
        obj_id room = getCellId(self, "meetingd");
        location loc = new location(2.0f, 1.13f, -14.48f, "tatooine", room);
        obj_id trainer = create.object("trainer_pistol", loc);
        create.addDestroyMessage(trainer, "pistoleerDied", 10.0f, self);
        setCreatureStatic(trainer, true);
        setInvulnerable(trainer, true);
        setYaw(trainer, 0);
        return;
    }
    public void spawnFencer(obj_id self) throws InterruptedException
    {
        obj_id room = getCellId(self, "meetingc");
        location loc = new location(9.0f, 1.13f, -8.0f, "tatooine", room);
        obj_id trainer = create.object("trainer_1hsword", loc);
        create.addDestroyMessage(trainer, "fencerDied", 10.0f, self);
        setCreatureStatic(trainer, true);
        setInvulnerable(trainer, true);
        setYaw(trainer, 180);
        return;
    }
    public void spawnSwordsman(obj_id self) throws InterruptedException
    {
        obj_id room = getCellId(self, "meetingc");
        location loc = new location(14.0f, 1.13f, -8.0f, "tatooine", room);
        obj_id trainer = create.object("trainer_2hsword", loc);
        create.addDestroyMessage(trainer, "swordsmanDied", 10.0f, self);
        setCreatureStatic(trainer, true);
        setInvulnerable(trainer, true);
        setYaw(trainer, 180);
        return;
    }
    public void spawnPikeman(obj_id self) throws InterruptedException
    {
        obj_id room = getCellId(self, "meetingc");
        location loc = new location(9.0f, 1.13f, -13.0f, "tatooine", room);
        obj_id trainer = create.object("trainer_polearm", loc);
        create.addDestroyMessage(trainer, "pikemanDied", 10.0f, self);
        setCreatureStatic(trainer, true);
        setInvulnerable(trainer, true);
        setYaw(trainer, 270);
        return;
    }
    public void spawnTerasKasi(obj_id self) throws InterruptedException
    {
        obj_id room = getCellId(self, "meetingc");
        location loc = new location(13.0f, 1.13f, -13.0f, "tatooine", room);
        obj_id trainer = create.object("trainer_unarmed", loc);
        create.addDestroyMessage(trainer, "terasKasiDied", 10.0f, self);
        setCreatureStatic(trainer, true);
        setInvulnerable(trainer, true);
        setYaw(trainer, 270);
        return;
    }
    public void spawnDeliverMission(obj_id self) throws InterruptedException
    {
        obj_id room = getCellId(self, "meetingd");
        location deliverloc = new location(3.32f, 1.13f, -8.49f, "tatooine", room);
        obj_id deliverer = create.object("businessman", deliverloc);
        create.addDestroyMessage(deliverer, "delivererDied", 10.0f, self);
        String[] strTest = new String[1];
        strTest[0] = "mission.objDeliverMission";
        setObjVar(deliverer, "mission.strMissionTypes", strTest);
        setObjVar(deliverer, "mission.intMinDifficulty", 6);
        setObjVar(deliverer, "mission.intMaxDifficulty", 20);
        setCreatureStatic(deliverer, true);
        setInvulnerable(deliverer, true);
        setYaw(deliverer, -132);
        attachScript(deliverer, "npc.converse.npc_convo");
        return;
    }
    public void spawnDestroyMission(obj_id self) throws InterruptedException
    {
        obj_id room = getCellId(self, "meetinge");
        location destroyerloc = new location(-14.01f, 1.13f, -8.53f, "tatooine", room);
        obj_id destroyer = create.object("hunter", destroyerloc);
        create.addDestroyMessage(destroyer, "destroyerDied", 10.0f, self);
        String[] strTest = new String[1];
        strTest[0] = "mission.objDestroyMission";
        setObjVar(destroyer, "mission.strMissionTypes", strTest);
        setObjVar(destroyer, "mission.intMinDifficulty", 6);
        setObjVar(destroyer, "mission.intMaxDifficulty", 20);
        setCreatureStatic(destroyer, true);
        setInvulnerable(destroyer, true);
        setYaw(destroyer, 120);
        attachScript(destroyer, "npc.converse.npc_convo");
        return;
    }
    public int spawnThings(obj_id self, dictionary params) throws InterruptedException
    {
        spawnEveryone(self);
        return SCRIPT_CONTINUE;
    }
    public int scoutDied(obj_id self, dictionary params) throws InterruptedException
    {
        spawnScout(self);
        return SCRIPT_CONTINUE;
    }
    public int bioengineerDied(obj_id self, dictionary params) throws InterruptedException
    {
        spawnBioEngineer(self);
        return SCRIPT_CONTINUE;
    }
    public int creaturehandlerDied(obj_id self, dictionary params) throws InterruptedException
    {
        spawnCreatureHandler(self);
        return SCRIPT_CONTINUE;
    }
    public int rangerDied(obj_id self, dictionary params) throws InterruptedException
    {
        spawnRanger(self);
        return SCRIPT_CONTINUE;
    }
    public int smugglerDied(obj_id self, dictionary params) throws InterruptedException
    {
        spawnSmuggler(self);
        return SCRIPT_CONTINUE;
    }
    public int squadleaderDied(obj_id self, dictionary params) throws InterruptedException
    {
        spawnSquadLeader(self);
        return SCRIPT_CONTINUE;
    }
    public int dealerDied(obj_id self, dictionary params) throws InterruptedException
    {
        spawnJunkdealer(self);
        return SCRIPT_CONTINUE;
    }
    public int marksmanDied(obj_id self, dictionary params) throws InterruptedException
    {
        spawnMarksman(self);
        return SCRIPT_CONTINUE;
    }
    public int brawlerDied(obj_id self, dictionary params) throws InterruptedException
    {
        spawnBrawler(self);
        return SCRIPT_CONTINUE;
    }
    public int bountyhunterDied(obj_id self, dictionary params) throws InterruptedException
    {
        spawnBountyHunter(self);
        return SCRIPT_CONTINUE;
    }
    public int riflemanDied(obj_id self, dictionary params) throws InterruptedException
    {
        spawnRifleman(self);
        return SCRIPT_CONTINUE;
    }
    public int carbineerDied(obj_id self, dictionary params) throws InterruptedException
    {
        spawnCarbineer(self);
        return SCRIPT_CONTINUE;
    }
    public int pistoleerDied(obj_id self, dictionary params) throws InterruptedException
    {
        spawnPistoleer(self);
        return SCRIPT_CONTINUE;
    }
    public int fencerDied(obj_id self, dictionary params) throws InterruptedException
    {
        spawnFencer(self);
        return SCRIPT_CONTINUE;
    }
    public int swordsmanDied(obj_id self, dictionary params) throws InterruptedException
    {
        spawnSwordsman(self);
        return SCRIPT_CONTINUE;
    }
    public int pikemanDied(obj_id self, dictionary params) throws InterruptedException
    {
        spawnPikeman(self);
        return SCRIPT_CONTINUE;
    }
    public int terasKasiDied(obj_id self, dictionary params) throws InterruptedException
    {
        spawnTerasKasi(self);
        return SCRIPT_CONTINUE;
    }
    public int delivererDied(obj_id self, dictionary params) throws InterruptedException
    {
        spawnDeliverMission(self);
        return SCRIPT_CONTINUE;
    }
    public int destroyerDied(obj_id self, dictionary params) throws InterruptedException
    {
        spawnDestroyMission(self);
        return SCRIPT_CONTINUE;
    }
}
