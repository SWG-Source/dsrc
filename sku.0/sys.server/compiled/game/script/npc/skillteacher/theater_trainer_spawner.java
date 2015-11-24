package script.npc.skillteacher;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.utils;

public class theater_trainer_spawner extends script.base_script
{
    public theater_trainer_spawner()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        dictionary params = new dictionary();
        params.put("stuff", self);
        messageTo(self, "spawnThings", params, 20, true);
        return SCRIPT_CONTINUE;
    }
    public void spawnEveryone(obj_id self) throws InterruptedException
    {
        spawnMusicMission(self);
        spawnDanceMission(self);
        spawnStorytellerVendor(self);
        if (utils.checkConfigFlag("Quest", "CrowdPleaser"))
        {
            spawnTheaterManager(self);
        }
        setObjVar(self, "trainer", 1);
    }
    public void spawnStorytellerVendor(obj_id self) throws InterruptedException
    {
        obj_id entrance = getCellId(self, "entrance");
        location storytellerVendorLoc = new location(6.6f, 0.6f, -3.5f, "tatooine", entrance);
        obj_id storytellerVendor = create.object("storyteller_vendor", storytellerVendorLoc);
        create.addDestroyMessage(storytellerVendor, "storytellerVendorDied", 10f, self);
        String name = getName(storytellerVendor);
        setCreatureStatic(storytellerVendor, true);
        setInvulnerable(storytellerVendor, true);
        setYaw(storytellerVendor, -179);
        return;
    }
    public void spawnDanceMission(obj_id self) throws InterruptedException
    {
        obj_id sideStage = getCellId(self, "backstage");
        location danceMissionLoc = new location(19.26f, 2.13f, 56.13f, "tatooine", sideStage);
        int danceYaw = -94;
        obj_id danceMission = create.object("noble", danceMissionLoc);
        String name = getName(danceMission);
        create.addDestroyMessage(danceMission, "danceMissionDied", 10f, self);
        String[] strTest = new String[1];
        strTest[0] = "mission.objDancerMission";
        setObjVar(danceMission, "mission.strMissionTypes", strTest);
        setObjVar(danceMission, "mission.intMinDifficulty", 6);
        setObjVar(danceMission, "mission.intMaxDifficulty", 20);
        attachScript(danceMission, "npc.converse.npc_convo");
        setCreatureStatic(danceMission, true);
        setInvulnerable(danceMission, true);
        setYaw(danceMission, danceYaw);
        return;
    }
    public void spawnMusicMission(obj_id self) throws InterruptedException
    {
        obj_id sideStage = getCellId(self, "side_backstage");
        location musicMissionLoc = new location(28.93f, 2.13f, 58.19f, "tatooine", sideStage);
        int musicYaw = -138;
        obj_id musicMission = create.object("noble", musicMissionLoc);
        String name = getName(musicMission);
        create.addDestroyMessage(musicMission, "musicMissionDied", 10f, self);
        String[] strTest = new String[1];
        strTest[0] = "mission.objMusicianMission";
        setObjVar(musicMission, "mission.strMissionTypes", strTest);
        setObjVar(musicMission, "mission.intMinDifficulty", 6);
        setObjVar(musicMission, "mission.intMaxDifficulty", 20);
        attachScript(musicMission, "npc.converse.npc_convo");
        setCreatureStatic(musicMission, true);
        setInvulnerable(musicMission, true);
        setYaw(musicMission, musicYaw);
        return;
    }
    public void spawnEntertainer(obj_id self) throws InterruptedException
    {
        obj_id greenroom = getCellId(self, "backstage");
        location entertainerloc = new location(26.99f, 2.13f, 75.21f, "tatooine", greenroom);
        obj_id entertainer = create.object("trainer_entertainer", entertainerloc);
        String name = getName(entertainer);
        create.addDestroyMessage(entertainer, "entertainerDied", 10f, self);
        setCreatureStatic(entertainer, true);
        setInvulnerable(entertainer, true);
        return;
    }
    public void spawnDancer(obj_id self) throws InterruptedException
    {
        if (utils.checkConfigFlag("ScriptFlags", "noEliteTrainers"))
        {
            return;
        }
        obj_id greenroom = getCellId(self, "backstage");
        location dancerloc = new location(18.78f, 2.13f, 54.98f, "tatooine", greenroom);
        obj_id dancer = create.object("trainer_dancer", dancerloc);
        create.addDestroyMessage(dancer, "dancerDied", 10f, self);
        String name = getName(dancer);
        setCreatureStatic(dancer, true);
        setInvulnerable(dancer, true);
        return;
    }
    public void spawnMusician(obj_id self) throws InterruptedException
    {
        if (utils.checkConfigFlag("ScriptFlags", "noEliteTrainers"))
        {
            return;
        }
        obj_id greenroom = getCellId(self, "side_backstage");
        location musicianloc = new location(28.89f, 2.13f, 55.83f, "tatooine", greenroom);
        obj_id musician = create.object("trainer_musician", musicianloc);
        create.addDestroyMessage(musician, "musicianDied", 10f, self);
        String name = getName(musician);
        setCreatureStatic(musician, true);
        setInvulnerable(musician, true);
        return;
    }
    public void spawnImageDesigner(obj_id self) throws InterruptedException
    {
        if (utils.checkConfigFlag("ScriptFlags", "noEliteTrainers"))
        {
            return;
        }
        obj_id greenroom = getCellId(self, "greenroom");
        location imagedesignerloc = new location(-22, 2, 74, "tatooine", greenroom);
        obj_id imagedesigner = create.object("trainer_imagedesigner", imagedesignerloc);
        create.addDestroyMessage(imagedesigner, "imagedesignerDied", 10f, self);
        String name = getName(imagedesigner);
        setCreatureStatic(imagedesigner, true);
        setInvulnerable(imagedesigner, true);
        return;
    }
    public void spawnTheaterManager(obj_id self) throws InterruptedException
    {
        obj_id greenroom = getCellId(self, "backstage");
        location managerloc = new location(21.99f, 2.13f, 64.05f, "tatooine", greenroom);
        obj_id manager = create.object("quest_crowd_pleaser_theater_manager", managerloc);
        create.addDestroyMessage(manager, "managerDied", 10f, self);
        String name = getName(manager);
        setCreatureStatic(manager, true);
        setInvulnerable(manager, true);
        setYaw(manager, 4);
        return;
    }
    public void spawnChoreographer(obj_id self) throws InterruptedException
    {
        if (utils.checkConfigFlag("ScriptFlags", "noEliteTrainers"))
        {
            return;
        }
        obj_id greenroom = getCellId(self, "backstage");
        location managerloc = new location(28.65f, 2.13f, 74.16f, "tatooine", greenroom);
        obj_id manager = create.object("trainer_dancer", managerloc);
        create.addDestroyMessage(manager, "choreographerDied", 10f, self);
        detachScript(manager, "npc.skillteacher.skillteacher");
        attachScript(manager, "npc.skillteacher.dance_advancement_trainer");
        String name = getName(manager);
        setCreatureStatic(manager, true);
        setInvulnerable(manager, true);
        setYaw(manager, -145);
        return;
    }
    public int spawnThings(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id stuffs = params.getObjId("stuff");
        spawnEveryone(stuffs);
        return SCRIPT_CONTINUE;
    }
    public int entertainerDied(obj_id self, dictionary params) throws InterruptedException
    {
        spawnEntertainer(self);
        return SCRIPT_CONTINUE;
    }
    public int danceDied(obj_id self, dictionary params) throws InterruptedException
    {
        spawnDancer(self);
        return SCRIPT_CONTINUE;
    }
    public int musicianDied(obj_id self, dictionary params) throws InterruptedException
    {
        spawnMusician(self);
        return SCRIPT_CONTINUE;
    }
    public int imagedesignerDied(obj_id self, dictionary params) throws InterruptedException
    {
        spawnImageDesigner(self);
        return SCRIPT_CONTINUE;
    }
    public int managerDied(obj_id self, dictionary params) throws InterruptedException
    {
        spawnTheaterManager(self);
        return SCRIPT_CONTINUE;
    }
    public int choreographerDied(obj_id self, dictionary params) throws InterruptedException
    {
        spawnChoreographer(self);
        return SCRIPT_CONTINUE;
    }
    public int storytellerVendorDied(obj_id self, dictionary params) throws InterruptedException
    {
        spawnStorytellerVendor(self);
        return SCRIPT_CONTINUE;
    }
}
