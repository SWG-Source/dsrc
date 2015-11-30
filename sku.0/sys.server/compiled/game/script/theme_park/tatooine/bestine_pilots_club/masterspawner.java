package script.theme_park.tatooine.bestine_pilots_club;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;

public class masterspawner extends script.base_script
{
    public masterspawner()
    {
    }
    public static final String VERSION = "v1.00.00";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        debugServerConsoleMsg(self, "Attached Bestine Pilot's Club Spawn Script");
        if (!hasObjVar(self, "pilots"))
        {
            spawnEveryone(self);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            return SCRIPT_CONTINUE;
        }
    }
    public void spawnEveryone(obj_id self) throws InterruptedException
    {
        obj_id pilot1 = spawnPilot1(self);
        obj_id woman1 = spawnWoman1(self, pilot1);
        obj_id atPilot1 = spawnAtPilot1(self, woman1);
        obj_id trooperLeader1 = spawnTrooperLeader1(self);
        spawnTrooper1(self, trooperLeader1);
        spawnTrooper2(self, trooperLeader1);
        obj_id pilot2 = spawnPilot2(self);
        obj_id pilot3 = spawnPilot3(self, pilot2);
        spawnPilot4(self, pilot2);
        spawnPilot5(self, pilot3);
        spawnPilot6(self, pilot3);
        setObjVar(self, "pilots", 1);
        return;
    }
    public void despawnEveryone(obj_id self) throws InterruptedException
    {
        destroyObject(getObjIdObjVar(self, "ClubInhabitants.pilot1"));
        destroyObject(getObjIdObjVar(self, "ClubInhabitants.woman1"));
        destroyObject(getObjIdObjVar(self, "ClubInhabitants.atpilot1"));
        destroyObject(getObjIdObjVar(self, "ClubInhabitants.trooperleader1"));
        destroyObject(getObjIdObjVar(self, "ClubInhabitants.trooper1"));
        destroyObject(getObjIdObjVar(self, "ClubInhabitants.trooper2"));
        destroyObject(getObjIdObjVar(self, "ClubInhabitants.pilot2"));
        destroyObject(getObjIdObjVar(self, "ClubInhabitants.pilot3"));
        destroyObject(getObjIdObjVar(self, "ClubInhabitants.pilot4"));
        destroyObject(getObjIdObjVar(self, "ClubInhabitants.pilot5"));
        destroyObject(getObjIdObjVar(self, "ClubInhabitants.pilot6"));
        return;
    }
    public obj_id spawnPilot1(obj_id self) throws InterruptedException
    {
        obj_id mainhall = getCellId(self, "mainhall");
        location pilot1Location = new location(3, 3, -1, "tatooine", mainhall);
        obj_id pilot1 = create.object("imperial_pilot", pilot1Location);
        setObjVar(pilot1, "ai.defaultCalmBehavior", 1);
        setAnimationMood(pilot1, "conversation");
        attachScript(pilot1, "theme_park.tatooine.bestine_pilots_club.pilot1");
        setObjVar(self, "ClubInhabitants.pilot1", pilot1);
        setObjVar(pilot1, "club", self);
        return pilot1;
    }
    public obj_id spawnWoman1(obj_id self, obj_id pilot1) throws InterruptedException
    {
        obj_id mainhall = getCellId(self, "mainhall");
        location woman1Location = new location(5, 3, -2, "tatooine", mainhall);
        obj_id woman1 = create.object("entertainer", woman1Location);
        setObjVar(woman1, "ai.defaultCalmBehavior", 1);
        faceTo(pilot1, woman1);
        faceTo(woman1, pilot1);
        attachScript(woman1, "theme_park.tatooine.bestine_pilots_club.woman1");
        setObjVar(self, "ClubInhabitants.woman1", woman1);
        setObjVar(woman1, "palace", self);
        return woman1;
    }
    public obj_id spawnAtPilot1(obj_id self, obj_id woman1) throws InterruptedException
    {
        obj_id mainhall = getCellId(self, "mainhall");
        location atPilot1Location = new location(-2, 4, 5, "tatooine", mainhall);
        obj_id atPilot1 = create.object("imperial_pilot", atPilot1Location);
        setObjVar(atPilot1, "ai.defaultCalmBehavior", 1);
        faceTo(atPilot1, woman1);
        attachScript(atPilot1, "theme_park.tatooine.bestine_pilots_club.atpilot1");
        setObjVar(self, "ClubInhabitants.atpilot1", atPilot1);
        setObjVar(atPilot1, "palace", self);
        return atPilot1;
    }
    public obj_id spawnTrooperLeader1(obj_id self) throws InterruptedException
    {
        obj_id mainhall = getCellId(self, "mainhall");
        location trooperLeader1Location = new location(6, 4, -12, "tatooine", mainhall);
        obj_id trooperLeader1 = create.object("stormtrooper_squad_leader", trooperLeader1Location);
        setObjVar(trooperLeader1, "ai.defaultCalmBehavior", 1);
        attachScript(trooperLeader1, "theme_park.tatooine.bestine_pilots_club.trooperleader1");
        setObjVar(self, "ClubInhabitants.trooperleader1", trooperLeader1);
        setObjVar(trooperLeader1, "palace", self);
        return trooperLeader1;
    }
    public void spawnTrooper1(obj_id self, obj_id trooperLeader1) throws InterruptedException
    {
        obj_id mainhall = getCellId(self, "mainhall");
        location trooper1Location = new location(11, 4, 2, "tatooine", mainhall);
        obj_id trooper1 = create.object("stormtrooper", trooper1Location);
        setObjVar(trooper1, "ai.defaultCalmBehavior", 1);
        faceTo(trooper1, trooperLeader1);
        attachScript(trooper1, "theme_park.tatooine.bestine_pilots_club.trooper1");
        setObjVar(self, "ClubInhabitants.trooper1", trooper1);
        setObjVar(trooper1, "palace", self);
        return;
    }
    public void spawnTrooper2(obj_id self, obj_id trooperLeader1) throws InterruptedException
    {
        obj_id mainhall = getCellId(self, "mainhall");
        location trooper2Location = new location(-11, 4, 2, "tatooine", mainhall);
        obj_id trooper2 = create.object("stormtrooper", trooper2Location);
        setObjVar(trooper2, "ai.defaultCalmBehavior", 1);
        faceTo(trooper2, trooperLeader1);
        attachScript(trooper2, "theme_park.tatooine.bestine_pilots_club.trooper2");
        setObjVar(self, "ClubInhabitants.trooper2", trooper2);
        setObjVar(trooper2, "palace", self);
        return;
    }
    public obj_id spawnPilot2(obj_id self) throws InterruptedException
    {
        obj_id mainhall = getCellId(self, "mainhall");
        location pilot2Location = new location(-4, 3, -8, "tatooine", mainhall);
        obj_id pilot2 = create.object("imperial_pilot", pilot2Location);
        setObjVar(pilot2, "ai.defaultCalmBehavior", 1);
        setAnimationMood(pilot2, "conversation");
        attachScript(pilot2, "theme_park.tatooine.bestine_pilots_club.pilot2");
        setObjVar(self, "ClubInhabitants.pilot2", pilot2);
        setObjVar(pilot2, "club", self);
        return pilot2;
    }
    public obj_id spawnPilot3(obj_id self, obj_id pilot2) throws InterruptedException
    {
        obj_id mainhall = getCellId(self, "mainhall");
        location pilot3Location = new location(-2, 3, -8, "tatooine", mainhall);
        obj_id pilot3 = create.object("imperial_pilot", pilot3Location);
        setObjVar(pilot3, "ai.defaultCalmBehavior", 1);
        faceTo(pilot2, pilot3);
        faceTo(pilot3, pilot2);
        attachScript(pilot3, "theme_park.tatooine.bestine_pilots_club.pilot3");
        setObjVar(self, "ClubInhabitants.pilot3", pilot3);
        setObjVar(pilot3, "palace", self);
        return pilot3;
    }
    public void spawnPilot4(obj_id self, obj_id pilot2) throws InterruptedException
    {
        obj_id mainhall = getCellId(self, "mainhall");
        location pilot4Location = new location(-3, 3, -9, "tatooine", mainhall);
        obj_id pilot4 = create.object("imperial_pilot", pilot4Location);
        setObjVar(pilot4, "ai.defaultCalmBehavior", 1);
        setAnimationMood(pilot4, "conversation");
        faceTo(pilot4, pilot2);
        attachScript(pilot4, "theme_park.tatooine.bestine_pilots_club.pilot4");
        setObjVar(self, "ClubInhabitants.pilot4", pilot4);
        setObjVar(pilot4, "palace", self);
        return;
    }
    public void spawnPilot5(obj_id self, obj_id pilot3) throws InterruptedException
    {
        obj_id mainhall = getCellId(self, "mainhall");
        location pilot5Location = new location(-2, 4, -13, "tatooine", mainhall);
        obj_id pilot5 = create.object("imperial_pilot", pilot5Location);
        setObjVar(pilot5, "ai.defaultCalmBehavior", 1);
        setAnimationMood(pilot5, "conversation");
        faceTo(pilot5, pilot3);
        attachScript(pilot5, "theme_park.tatooine.bestine_pilots_club.pilot5");
        setObjVar(self, "ClubInhabitants.pilot5", pilot5);
        setObjVar(pilot5, "palace", self);
        return;
    }
    public void spawnPilot6(obj_id self, obj_id pilot3) throws InterruptedException
    {
        obj_id mainhall = getCellId(self, "mainhall");
        location pilot6Location = new location(-5, 4, -13, "tatooine", mainhall);
        obj_id pilot6 = create.object("imperial_pilot", pilot6Location);
        setObjVar(pilot6, "ai.defaultCalmBehavior", 1);
        faceTo(pilot6, pilot3);
        attachScript(pilot6, "theme_park.tatooine.bestine_pilots_club.pilot6");
        setObjVar(self, "ClubInhabitants.pilot6", pilot6);
        setObjVar(pilot6, "palace", self);
        return;
    }
    public int pilot1Died(obj_id self, dictionary params) throws InterruptedException
    {
        spawnPilot1(self);
        return SCRIPT_CONTINUE;
    }
    public int woman1Died(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id pilot1 = getObjIdObjVar(self, "ClubInhabitants.pilot1");
        spawnWoman1(self, pilot1);
        return SCRIPT_CONTINUE;
    }
    public int atPilot1Died(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id woman1 = getObjIdObjVar(self, "ClubInhabitants.woman1");
        spawnAtPilot1(self, woman1);
        return SCRIPT_CONTINUE;
    }
    public int trooperLeader1Died(obj_id self, dictionary params) throws InterruptedException
    {
        spawnTrooperLeader1(self);
        return SCRIPT_CONTINUE;
    }
    public int trooper1Died(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id trooperLeader1 = getObjIdObjVar(self, "ClubInhabitants.trooperleader1");
        spawnTrooper1(self, trooperLeader1);
        return SCRIPT_CONTINUE;
    }
    public int trooper2Died(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id trooperLeader1 = getObjIdObjVar(self, "ClubInhabitants.trooperleader1");
        spawnTrooper2(self, trooperLeader1);
        return SCRIPT_CONTINUE;
    }
    public int pilot2Died(obj_id self, dictionary params) throws InterruptedException
    {
        spawnPilot2(self);
        return SCRIPT_CONTINUE;
    }
    public int pilot3Died(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id pilot2 = getObjIdObjVar(self, "ClubInhabitants.pilot2");
        spawnPilot3(self, pilot2);
        return SCRIPT_CONTINUE;
    }
    public int pilot4Died(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id pilot2 = getObjIdObjVar(self, "ClubInhabitants.pilot2");
        spawnPilot4(self, pilot2);
        return SCRIPT_CONTINUE;
    }
    public int pilot5Died(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id pilot3 = getObjIdObjVar(self, "ClubInhabitants.pilot3");
        spawnPilot5(self, pilot3);
        return SCRIPT_CONTINUE;
    }
    public int pilot6Died(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id pilot3 = getObjIdObjVar(self, "ClubInhabitants.pilot3");
        spawnPilot6(self, pilot3);
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        debugServerConsoleMsg(self, "Detaching Bestine Pilot's Club Spawn Script");
        despawnEveryone(self);
        return SCRIPT_CONTINUE;
    }
}
