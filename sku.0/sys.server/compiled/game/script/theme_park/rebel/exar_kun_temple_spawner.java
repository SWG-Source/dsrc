package script.theme_park.rebel;

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
import script.ai.ai_combat;

public class exar_kun_temple_spawner extends script.base_script
{
    public exar_kun_temple_spawner()
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
        messageTo(self, "doGating", null, 20, true);
        return;
    }
    public void spawnCelebs(obj_id self) throws InterruptedException
    {
        spawnLuke(self);
        spawnDodonna(self);
        spawnCrystal(self);
        spawnSpirit1(self);
        spawnSpirit2(self);
        spawnSpirit3(self);
        spawnSpirit4(self);
        spawnSpirit5(self);
        spawnSpirit6(self);
        spawnSpirit7(self);
    }
    public void spawnLuke(obj_id self) throws InterruptedException
    {
        location lukeLocation = new location(5079.3f, 73.7f, 5550.0f, "yavin4", null);
        obj_id luke = create.staticObject("luke_skywalker", lukeLocation);
        setObjVar(self, "HideoutInhabitants.luke", luke);
        setObjVar(luke, "Hideout", self);
        setYaw(luke, 83);
        return;
    }
    public void spawnDodonna(obj_id self) throws InterruptedException
    {
        location dodonnaLocation = new location(5072.5f, 73.5f, 5502.7f, "yavin4", null);
        obj_id dodonna = create.staticObject("jan_dodonna", dodonnaLocation);
        setObjVar(self, "HideoutInhabitants.dodonna", dodonna);
        setObjVar(dodonna, "Hideout", self);
        setYaw(dodonna, 147);
        return;
    }
    public void spawnCrystal(obj_id self) throws InterruptedException
    {
        String crystalTemplate = "object/tangible/quest/rebel/rtp_luke_wave_force_crystal.iff";
        obj_id room = getCellId(self, "r14");
        location here = new location(-6.5f, -8.4f, -4.6f, "yavin4", room);
        obj_id crystal = createObject(crystalTemplate, here);
        setYaw(crystal, 174);
        setObjVar(self, "HideoutInhabitants.spirit6", crystal);
        setObjVar(crystal, "Hideout", self);
        return;
    }
    public void spawnSpirit1(obj_id self) throws InterruptedException
    {
        obj_id room = getCellId(self, "r4");
        location here = new location(0.1f, 0.0f, -4.1f, "yavin4", room);
        obj_id spirit = createSpawnerObject("rtp_luke_force_ghost", here, ai_lib.BEHAVIOR_LOITER, 210, 309);
        setYaw(spirit, -1);
        setObjVar(self, "HideoutInhabitants.spirit1", spirit);
        setObjVar(spirit, "Hideout", self);
        return;
    }
    public void spawnSpirit2(obj_id self) throws InterruptedException
    {
        obj_id room = getCellId(self, "r5");
        location here = new location(9.9f, 0.0f, -11.6f, "yavin4", room);
        obj_id spirit = createSpawnerObject("rtp_luke_force_ghost", here, ai_lib.BEHAVIOR_SENTINEL, 210, 309);
        setYaw(spirit, -83);
        setObjVar(self, "HideoutInhabitants.spirit2", spirit);
        setObjVar(spirit, "Hideout", self);
        return;
    }
    public void spawnSpirit3(obj_id self) throws InterruptedException
    {
        obj_id room = getCellId(self, "r5");
        location here = new location(-10.9f, 0.0f, -11.6f, "yavin4", room);
        obj_id spirit = createSpawnerObject("rtp_luke_force_ghost", here, ai_lib.BEHAVIOR_SENTINEL, 210, 309);
        setYaw(spirit, -92);
        setObjVar(self, "HideoutInhabitants.spirit3", spirit);
        setObjVar(spirit, "Hideout", self);
        return;
    }
    public void spawnSpirit4(obj_id self) throws InterruptedException
    {
        obj_id room = getCellId(self, "r7");
        location here = new location(-19.5f, 0.0f, -12.5f, "yavin4", room);
        obj_id spirit = createSpawnerObject("rtp_luke_force_ghost", here, ai_lib.BEHAVIOR_SENTINEL, 210, 309);
        setYaw(spirit, 4);
        setObjVar(self, "HideoutInhabitants.spirit4", spirit);
        setObjVar(spirit, "Hideout", self);
        return;
    }
    public void spawnSpirit5(obj_id self) throws InterruptedException
    {
        obj_id room = getCellId(self, "r9");
        location here = new location(-1.3f, 0.0f, -42.8f, "yavin4", room);
        obj_id spirit = createSpawnerObject("rtp_luke_force_ghost", here, ai_lib.BEHAVIOR_LOITER, 210, 309);
        setYaw(spirit, 13);
        setObjVar(self, "HideoutInhabitants.spirit5", spirit);
        setObjVar(spirit, "Hideout", self);
        return;
    }
    public void spawnSpirit6(obj_id self) throws InterruptedException
    {
        obj_id room = getCellId(self, "r9");
        location here = new location(0.9f, 0.0f, -23.9f, "yavin4", room);
        obj_id spirit = createSpawnerObject("rtp_luke_force_ghost", here, ai_lib.BEHAVIOR_SENTINEL, 210, 309);
        setYaw(spirit, -90);
        setObjVar(self, "HideoutInhabitants.spirit6", spirit);
        setObjVar(spirit, "Hideout", self);
        return;
    }
    public void spawnSpirit7(obj_id self) throws InterruptedException
    {
        obj_id room = getCellId(self, "r11");
        location here = new location(0.7f, -6.0f, -29.9f, "yavin4", room);
        obj_id spirit = createSpawnerObject("rtp_luke_force_ghost", here, ai_lib.BEHAVIOR_LOITER, 210, 309);
        setYaw(spirit, 172);
        setObjVar(self, "HideoutInhabitants.spirit6", spirit);
        setObjVar(spirit, "Hideout", self);
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
        if (text.equals("spawn_celebs"))
        {
            spawnCelebs(self);
        }
        if (text.equals("kill_celebs"))
        {
            killCelebs(self);
        }
        if (text.equals("spawn_everyone"))
        {
            spawnEveryone(self);
        }
        return SCRIPT_CONTINUE;
    }
    public void killCelebs(obj_id self) throws InterruptedException
    {
        destroyObject(getObjIdObjVar(self, "HideoutInhabitants.luke"));
        removeObjVar(self, "HideoutInhabitants.luke");
        destroyObject(getObjIdObjVar(self, "HideoutInhabitants.dodonna"));
        removeObjVar(self, "HideoutInhabitants.dodonna");
        destroyObject(getObjIdObjVar(self, "HideoutInhabitants.trooper1"));
        removeObjVar(self, "HideoutInhabitants.trooper1");
        destroyObject(getObjIdObjVar(self, "HideoutInhabitants.trooper2"));
        removeObjVar(self, "HideoutInhabitants.trooper2");
        destroyObject(getObjIdObjVar(self, "HideoutInhabitants.trooper3"));
        removeObjVar(self, "HideoutInhabitants.trooper3");
        destroyObject(getObjIdObjVar(self, "HideoutInhabitants.trooper4"));
        removeObjVar(self, "HideoutInhabitants.trooper4");
        destroyObject(getObjIdObjVar(self, "HideoutInhabitants.trooper5"));
        removeObjVar(self, "HideoutInhabitants.trooper5");
        return;
    }
    public int doGating(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id room3 = getCellId(self, "r3");
        attachScript(room3, "theme_park.gating.rebel.exar_kun_block1");
        return SCRIPT_CONTINUE;
    }
}
