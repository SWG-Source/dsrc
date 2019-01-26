package script.theme_park.lok;

import script.dictionary;
import script.library.ai_lib;
import script.library.create;
import script.location;
import script.obj_id;

public class imperial_base extends script.base_script
{
    public imperial_base()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        spawnEveryone(self);
        return SCRIPT_CONTINUE;
    }
    public void spawnEveryone(obj_id self) throws InterruptedException
    {
        debugSpeakMsg(self, "Spawning Everyone");
        spawnCelebs(self);
        messageTo(self, "spawnOfficers", null, 5, true);
        return;
    }
    public void spawnCelebs(obj_id self) throws InterruptedException
    {
        spawnOtto(self);
        spawnTech1(self);
    }
    public obj_id spawnOtto(obj_id self) throws InterruptedException
    {
        obj_id office = getCellId(self, "meeting2");
        location ottoLocation = new location(19.18f, 1.01f, 21.65f, "lok", office);
        obj_id ottoSpawner = createSpawnerObject("rtp_hansolo_general_otto", ottoLocation, ai_lib.BEHAVIOR_SENTINEL, 210, 309);
        setYaw(ottoSpawner, 174.0f);
        setObjVar(self, "StrongholdInhabitants.otto", ottoSpawner);
        setObjVar(ottoSpawner, "Stronghold", self);
        return ottoSpawner;
    }
    public void spawnTech1(obj_id self) throws InterruptedException
    {
        obj_id hall = getCellId(self, "mainhall");
        location tech1Location = new location(-2.47f, 2.01f, 3.93f, "lok", hall);
        obj_id tech1 = createSpawnerObject("rtp_imperial_lieutenant", tech1Location, ai_lib.BEHAVIOR_SENTINEL, 210, 309);
        setYaw(tech1, -42.0f);
        setObjVar(self, "StrongholdInhabitants.tech1", tech1);
        setObjVar(tech1, "Stronghold", self);
        return;
    }
    public void spawnJinkins(obj_id self, obj_id otto) throws InterruptedException
    {
        obj_id mainhall = getCellId(self, "mainhall");
        location jinkinsLocation = new location(3.07f, 3.29f, -21.31f, "lok", mainhall);
        obj_id jinkins = create.staticObject("jinkins", jinkinsLocation);
        faceTo(self, otto);
        setObjVar(self, "StrongholdInhabitants.jinkins", jinkins);
        setObjVar(jinkins, "Stronghold", self);
        return;
    }
    public void spawnOfficer1(obj_id self) throws InterruptedException
    {
        obj_id mainhall = getCellId(self, "mainhall");
        location here = new location(4.13f, 1.01f, -6.79f, "lok", mainhall);
        obj_id officer1 = createSpawnerObject("rtp_imperial_medic", here, ai_lib.BEHAVIOR_LOITER, 210, 309);
        setYaw(officer1, -1);
        setObjVar(self, "StrongholdInhabitants.officer1", officer1);
        setObjVar(officer1, "Stronghold", self);
        return;
    }
    public void spawnOfficer2(obj_id self) throws InterruptedException
    {
        obj_id mainhall = getCellId(self, "mainhall");
        location here = new location(2.01f, 1.01f, -0.39f, "lok", mainhall);
        obj_id officer2 = createSpawnerObject("rtp_imperial_first_lieutenant", here, ai_lib.BEHAVIOR_LOITER, 210, 309);
        setYaw(officer2, -1);
        setObjVar(self, "StrongholdInhabitants.officer2", officer2);
        setObjVar(officer2, "Stronghold", self);
        return;
    }
    public void spawnOfficer3(obj_id self) throws InterruptedException
    {
        obj_id mainhall = getCellId(self, "mainhall");
        location here = new location(-6.31f, 1.01f, -1.10f, "lok", mainhall);
        obj_id officer3 = createSpawnerObject("rtp_imperial_lieutenant", here, ai_lib.BEHAVIOR_SENTINEL, 210, 309);
        setYaw(officer3, 178);
        setObjVar(self, "StrongholdInhabitants.officer3", officer3);
        setObjVar(officer3, "Stronghold", self);
        return;
    }
    public void spawnOfficer4(obj_id self) throws InterruptedException
    {
        obj_id mainhall = getCellId(self, "mainhall");
        location here = new location(-6.37f, 1.01f, -3.93f, "lok", mainhall);
        obj_id officer4 = createSpawnerObject("rtp_imperial_master_sergeant", here, ai_lib.BEHAVIOR_SENTINEL, 210, 309);
        setYaw(officer4, -4);
        setObjVar(self, "StrongholdInhabitants.officer4", officer4);
        setObjVar(officer4, "Stronghold", self);
        return;
    }
    public void spawnOfficer5(obj_id self) throws InterruptedException
    {
        obj_id mainhall = getCellId(self, "mainhall");
        location here = new location(-0.24f, 2.01f, 3.89f, "lok", mainhall);
        obj_id officer5 = createSpawnerObject("rtp_imperial_lieutenant", here, ai_lib.BEHAVIOR_SENTINEL, 210, 309);
        setYaw(officer5, -5);
        setObjVar(self, "StrongholdInhabitants.officer5", officer5);
        setObjVar(officer5, "Stronghold", self);
        return;
    }
    public void spawnOfficer6(obj_id self) throws InterruptedException
    {
        obj_id mainhall = getCellId(self, "mainhall");
        location here = new location(1.51f, 1.01f, -14.82f, "lok", mainhall);
        obj_id officer6 = createSpawnerObject("rtp_imperial_lieutenant", here, ai_lib.BEHAVIOR_LOITER, 210, 309);
        setYaw(officer6, -153);
        attachScript(officer6, "systems.spawning.spawned_tracker");
        setObjVar(self, "StrongholdInhabitants.officer6", officer6);
        setObjVar(officer6, "Stronghold", self);
        return;
    }
    public void spawnOfficer7(obj_id self) throws InterruptedException
    {
        obj_id mainhall = getCellId(self, "mainhall");
        location here = new location(-10.84f, 1.01f, -10.62f, "lok", mainhall);
        obj_id officer7 = createSpawnerObject("rtp_imperial_first_lieutenant", here, ai_lib.BEHAVIOR_LOITER, 210, 309);
        setYaw(officer7, 100);
        setObjVar(self, "StrongholdInhabitants.officer7", officer7);
        setObjVar(officer7, "Stronghold", self);
        return;
    }
    public void spawnOfficer8(obj_id self) throws InterruptedException
    {
        obj_id meeting1 = getCellId(self, "meeting1");
        location here = new location(-18.0f, 1.0f, 18.7f, "lok", meeting1);
        obj_id officer8 = createSpawnerObject("rtp_imperial_first_lieutenant", here, ai_lib.BEHAVIOR_SENTINEL, 210, 309);
        setYaw(officer8, 175);
        setObjVar(self, "StrongholdInhabitants.officer8", officer8);
        setObjVar(officer8, "Stronghold", self);
        return;
    }
    public void spawnTrooper1(obj_id self) throws InterruptedException
    {
        obj_id foyer2 = getCellId(self, "foyer2");
        location here = new location(13.7f, 7.0f, 10.0f, "lok", foyer2);
        obj_id trooper1 = createSpawnerObject("rtp_stormtrooper_elite_87", here, ai_lib.BEHAVIOR_SENTINEL, 210, 309);
        setYaw(trooper1, -38);
        setObjVar(self, "StrongholdInhabitants.trooper1", trooper1);
        setObjVar(trooper1, "Stronghold", self);
        return;
    }
    public void spawnTrooper2(obj_id self) throws InterruptedException
    {
        obj_id foyer2 = getCellId(self, "foyer2");
        location here = new location(6.3f, 7.0f, 9.6f, "lok", foyer2);
        obj_id trooper2 = createSpawnerObject("rtp_stormtrooper_elite_88", here, ai_lib.BEHAVIOR_SENTINEL, 210, 309);
        setYaw(trooper2, 26);
        setObjVar(self, "StrongholdInhabitants.trooper2", trooper2);
        setObjVar(trooper2, "Stronghold", self);
        return;
    }
    public void spawnTrooper3(obj_id self) throws InterruptedException
    {
        obj_id foyer1 = getCellId(self, "foyer1");
        location here = new location(-7.5f, 1.0f, 21.0f, "lok", foyer1);
        obj_id trooper3 = createSpawnerObject("rtp_stormtrooper_elite_88", here, ai_lib.BEHAVIOR_SENTINEL, 210, 309);
        setYaw(trooper3, 157);
        setObjVar(self, "StrongholdInhabitants.trooper3", trooper3);
        setObjVar(trooper3, "Stronghold", self);
        return;
    }
    public void spawnTrooper4(obj_id self) throws InterruptedException
    {
        obj_id foyer1 = getCellId(self, "foyer1");
        location here = new location(9.7f, 1.0f, 21.7f, "lok", foyer1);
        obj_id trooper4 = createSpawnerObject("rtp_stormtrooper_elite_88", here, ai_lib.BEHAVIOR_SENTINEL, 210, 309);
        setYaw(trooper4, -135);
        setObjVar(self, "StrongholdInhabitants.trooper4", trooper4);
        setObjVar(trooper4, "Stronghold", self);
        return;
    }
    public void spawnTrooper5(obj_id self) throws InterruptedException
    {
        obj_id hall2 = getCellId(self, "hall2");
        location here = new location(-5.4f, 7.0f, -13.7f, "hall2", hall2);
        obj_id trooper5 = createSpawnerObject("rtp_stormtrooper_elite_88", here, ai_lib.BEHAVIOR_LOITER, 210, 309);
        setYaw(trooper5, 43);
        setObjVar(self, "StrongholdInhabitants.trooper5", trooper5);
        setObjVar(trooper5, "Stronghold", self);
        return;
    }
    public void spawnTrooper6(obj_id self) throws InterruptedException
    {
        obj_id hall2 = getCellId(self, "hall2");
        location here = new location(10.0f, 7.0f, -13.3f, "hall2", hall2);
        obj_id trooper6 = createSpawnerObject("rtp_stormtrooper_elite_88", here, ai_lib.BEHAVIOR_LOITER, 210, 309);
        setYaw(trooper6, -23);
        setObjVar(self, "StrongholdInhabitants.trooper6", trooper6);
        setObjVar(trooper6, "Stronghold", self);
        return;
    }
    public void spawnTrooper7(obj_id self) throws InterruptedException
    {
        obj_id hall2 = getCellId(self, "hall2");
        location here = new location(-0.5f, 7.0f, -2.1f, "hall2", hall2);
        obj_id trooper7 = createSpawnerObject("rtp_stormtrooper_elite_88", here, ai_lib.BEHAVIOR_LOITER, 210, 309);
        setYaw(trooper7, 98);
        setObjVar(self, "StrongholdInhabitants.trooper7", trooper7);
        setObjVar(trooper7, "Stronghold", self);
        return;
    }
    public obj_id createSpawnerObject(String whatToSpawn, location where, int intDefaultBehavior, float maxSpawnTime, float minSpawnTime) throws InterruptedException
    {
        obj_id objSpawner = createObject("object/tangible/ground_spawning/area_spawner.iff", where);
        setObjVar(objSpawner, "strSpawnerType", "area");
        setObjVar(objSpawner, "intSpawnSystem", 1);
        String spawnerObjName = "spawning: " + whatToSpawn;
        setObjVar(objSpawner, "strName", spawnerObjName);
        setName(objSpawner, spawnerObjName);
        setObjVar(objSpawner, "intSpawnCount", 1);
        setObjVar(objSpawner, "fltMaxSpawnTime", maxSpawnTime);
        setObjVar(objSpawner, "fltMinSpawnTime", minSpawnTime);
        setObjVar(objSpawner, "strSpawns", whatToSpawn);
        setObjVar(objSpawner, "fltRadius", 0);
        setObjVar(objSpawner, "intDefaultBehavior", intDefaultBehavior);
        attachScript(objSpawner, "systems.spawning.spawner_area");
        return objSpawner;
    }
    public int OnHearSpeech(obj_id self, obj_id speaker, String text) throws InterruptedException
    {
        if (!hasObjVar(speaker, "gmAllowed"))
        {
            return SCRIPT_CONTINUE;
        }
        if (text.equals("spawn_all"))
        {
            spawnEveryone(self);
        }
        if (text.equals("spawn_celebs"))
        {
            spawnCelebs(self);
        }
        if (text.equals("spawn_officers"))
        {
            messageTo(self, "spawnOfficers", null, 0, true);
        }
        if (text.equals("kill_celebs"))
        {
            killCelebs(self);
        }
        if (text.equals("kill_officers"))
        {
            killGuards(self);
        }
        if (text.equals("kill_all"))
        {
            killAll(self);
        }
        return SCRIPT_CONTINUE;
    }
    public void killAll(obj_id self) throws InterruptedException
    {
        killCelebs(self);
        killGuards(self);
        return;
    }
    public void killCelebs(obj_id self) throws InterruptedException
    {
        destroyObject(getObjIdObjVar(self, "StrongholdInhabitants.otto"));
        removeObjVar(self, "StrongholdInhabitants.otto");
        destroyObject(getObjIdObjVar(self, "StrongholdInhabitants.tech1"));
        removeObjVar(self, "StrongholdInhabitants.tech1");
        destroyObject(getObjIdObjVar(self, "StrongholdInhabitants.jinkins"));
        removeObjVar(self, "StrongholdInhabitants.jinkins");
        return;
    }
    public void killGuards(obj_id self) throws InterruptedException
    {
        destroyObject(getObjIdObjVar(self, "StrongholdInhabitants.officer1"));
        removeObjVar(self, "StrongholdInhabitants.officer1");
        destroyObject(getObjIdObjVar(self, "StrongholdInhabitants.officer2"));
        removeObjVar(self, "StrongholdInhabitants.officer2");
        destroyObject(getObjIdObjVar(self, "StrongholdInhabitants.officer3"));
        removeObjVar(self, "StrongholdInhabitants.officer3");
        destroyObject(getObjIdObjVar(self, "StrongholdInhabitants.officer4"));
        removeObjVar(self, "StrongholdInhabitants.officer4");
        destroyObject(getObjIdObjVar(self, "StrongholdInhabitants.officer5"));
        removeObjVar(self, "StrongholdInhabitants.officer5");
        destroyObject(getObjIdObjVar(self, "StrongholdInhabitants.officer6"));
        removeObjVar(self, "StrongholdInhabitants.officer6");
        destroyObject(getObjIdObjVar(self, "StrongholdInhabitants.officer7"));
        removeObjVar(self, "StrongholdInhabitants.officer7");
        destroyObject(getObjIdObjVar(self, "StrongholdInhabitants.officer8"));
        removeObjVar(self, "StrongholdInhabitants.officer8");
        destroyObject(getObjIdObjVar(self, "StrongholdInhabitants.trooper1"));
        removeObjVar(self, "StrongholdInhabitants.trooper1");
        destroyObject(getObjIdObjVar(self, "StrongholdInhabitants.trooper2"));
        removeObjVar(self, "StrongholdInhabitants.trooper2");
        return;
    }
    public int spawnOfficers(obj_id self, dictionary params) throws InterruptedException
    {
        debugSpeakMsg(self, "SPAWNING OFFICERS");
        spawnOfficer1(self);
        spawnOfficer2(self);
        spawnOfficer3(self);
        spawnOfficer4(self);
        spawnOfficer5(self);
        spawnOfficer6(self);
        spawnOfficer7(self);
        spawnOfficer8(self);
        spawnTrooper1(self);
        spawnTrooper2(self);
        spawnTrooper3(self);
        spawnTrooper4(self);
        spawnTrooper5(self);
        spawnTrooper6(self);
        spawnTrooper7(self);
        return SCRIPT_CONTINUE;
    }
}
