package script.npc.skillteacher;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;

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
        return;
    }
    public void spawnMarksman(obj_id self) throws InterruptedException
    {
        obj_id room = getCellId(self, "meetingd");
        location marksLoc = new location(0.0f, 1.13f, -14.48f, "tatooine", room);
        obj_id marksman = create.object("trainer_marksman", marksLoc);
        create.addDestroyMessage(marksman, "marksmanDied", 10f, self);
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
        create.addDestroyMessage(scout, "scoutDied", 10f, self);
        setCreatureStatic(scout, true);
        setInvulnerable(scout, true);
        setYaw(scout, 179);
        return;
    }
    public void spawnJunkdealer(obj_id self) throws InterruptedException
    {
        debugServerConsoleMsg(self, "Spawning Junkdealer");
        obj_id room = getCellId(self, "meetingb");
        location dealerloc = new location(-14.56f, 1.13f, 2.85f, "tatooine", room);
        obj_id dealer = create.object("junk_dealer", dealerloc);
        create.addDestroyMessage(dealer, "dealerDied", 10f, self);
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
        create.addDestroyMessage(brawler, "brawlerDied", 10f, self);
        setCreatureStatic(brawler, true);
        setInvulnerable(brawler, true);
        setYaw(brawler, 0);
        return;
    }
    public void spawnDeliverMission(obj_id self) throws InterruptedException
    {
        obj_id room = getCellId(self, "meetingd");
        location deliverloc = new location(3.32f, 1.13f, -8.49f, "tatooine", room);
        obj_id deliverer = create.object("businessman", deliverloc);
        create.addDestroyMessage(deliverer, "delivererDied", 10f, self);
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
        create.addDestroyMessage(destroyer, "destroyerDied", 10f, self);
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
