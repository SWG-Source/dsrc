package script.theme_park.corellia;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.ai.ai;
import script.library.advanced_turret;
import script.library.ai_lib;
import script.library.create;
import script.library.factions;
import script.library.utils;

public class hideoutspawner extends script.base_script
{
    public hideoutspawner()
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
        messageTo(self, "doGating", null, 20, false);
        spawnCelebs(self);
        setObjVar(self, "people", 1);
        return;
    }
    public void spawnCelebs(obj_id self) throws InterruptedException
    {
        spawnPatient1(self);
        spawnPatient2(self);
        spawnCommtech(self);
        spawnWedgetech(self);
        spawnThreepio(self);
        spawnArtoo(self);
        spawnMedicalSupplySource(self);
        spawnQualdo(self);
        spawnLtLance(self);
        spawnWedge(self);
        spawnLeia(self);
        spawnLeiatech(self);
        spawnLeiaguard1(self);
        spawnLeiaguard2(self);
        spawnTrooper1(self);
        spawnTrooper2(self);
        spawnTrooper3(self);
        spawnTrooper4(self);
        spawnNewtech1(self);
        spawnNewtech2(self);
        spawnMousedroid(self);
        spawnProtocoldroid(self);
        spawnSittingGuy1(self);
        spawnSittingGuy2(self);
        spawnSittingGuy3(self);
        spawnSittingGuy4(self);
        spawnSittingGuy5(self);
        spawnSittingGuy6(self);
        spawnSittingGuy7(self);
        spawnSittingGuy8(self);
        spawnSittingGuy9(self);
        spawnTurret01(self);
        spawnTurret2(self);
        spawnTurret3(self);
        spawnTurret4(self);
        spawnTurret5(self);
        spawnTurret6(self);
        spawnTurret7(self);
        spawnTurret8(self);
        spawnTurret9(self);
        spawnTurret10(self);
        spawnTurret11(self);
        spawnTurret12(self);
        spawnTurret13(self);
    }
    public void spawnPatient1(obj_id self) throws InterruptedException
    {
        obj_id meeting3 = getCellId(self, "meeting3");
        location patient1Location = new location(12.73f, 1.01f, 1.77f, "corellia", meeting3);
        obj_id patient1 = create.staticObject("rebel_first_lieutenant", patient1Location);
        int patient1_yaw = -97;
        setYaw(patient1, patient1_yaw);
        setObjVar(self, "HideoutInhabitants.patient1", patient1);
        setObjVar(patient1, "hideout", self);
        setAnimationMood(patient1, "npc_sitting_chair");
        return;
    }
    public void spawnPatient2(obj_id self) throws InterruptedException
    {
        obj_id meeting3 = getCellId(self, "meeting3");
        location patient2Location = new location(13.18f, 1.01f, -6.59f, "corellia", meeting3);
        obj_id patient2 = create.staticObject("rebel_scout", patient2Location);
        int patient2_yaw = 88;
        setYaw(patient2, patient2_yaw);
        setObjVar(self, "HideoutInhabitants.patient2", patient2);
        setObjVar(patient2, "hideout", self);
        setAnimationMood(patient2, "npc_sitting_chair");
        return;
    }
    public void spawnCommtech(obj_id self) throws InterruptedException
    {
        obj_id mainhall = getCellId(self, "mainhall");
        location commtechLocation = new location(0.93f, 2.01f, 5.74f, "corellia", mainhall);
        obj_id commtech = create.object("rebel_corporal", commtechLocation);
        int tech_yaw = 3;
        setYaw(commtech, tech_yaw);
        setObjVar(self, "HideoutInhabitants.commtech", commtech);
        setObjVar(commtech, "hideout", self);
        ai_lib.setDefaultCalmMood(commtech, "ui");
        return;
    }
    public void spawnThreepio(obj_id self) throws InterruptedException
    {
        obj_id mainhall = getCellId(self, "mainhall");
        location c3poLocation = new location(-2.31f, 2.01f, 3.86f, "corellia", mainhall);
        obj_id c3po = create.staticObject("c_3po", c3poLocation);
        int c3po_yaw = -164;
        setYaw(c3po, c3po_yaw);
        setObjVar(self, "HideoutInhabitants.c3po", c3po);
        setObjVar(c3po, "hideout", self);
        return;
    }
    public void spawnMedicalSupplySource(obj_id self) throws InterruptedException
    {
        String medSupplySource = "object/tangible/quest/rebel/rtp_c3po_medical_supply_source.iff";
        obj_id meeting3 = getCellId(self, "meeting3");
        location supplyLocation = new location(18.9f, 1.0f, -0.7f, "corellia", meeting3);
        obj_id medSupplies = createObject(medSupplySource, supplyLocation);
        int tech_yaw = -92;
        setYaw(medSupplies, tech_yaw);
        setObjVar(self, "HideoutInhabitants.medsupplies", medSupplies);
        setObjVar(medSupplies, "hideout", self);
        return;
    }
    public void spawnArtoo(obj_id self) throws InterruptedException
    {
        obj_id mainhall = getCellId(self, "mainhall");
        location artooLocation = new location(-3.98f, 1.01f, -0.67f, "corellia", mainhall);
        obj_id artoo = create.staticObject("r2d2", artooLocation);
        int r2d2_yaw = 36;
        setYaw(artoo, r2d2_yaw);
        setObjVar(self, "HideoutInhabitants.r2d2", artoo);
        setObjVar(artoo, "hideout", self);
        return;
    }
    public void spawnQualdo(obj_id self) throws InterruptedException
    {
        obj_id meeting3 = getCellId(self, "meeting3");
        location qualdoLocation = new location(14.91f, 1.01f, -6.68f, "corellia", meeting3);
        obj_id qualdo = create.staticObject("qualdo_herm", qualdoLocation);
        int qualdo_yaw = -49;
        setYaw(qualdo, qualdo_yaw);
        setObjVar(self, "HideoutInhabitants.qualdo", qualdo);
        setObjVar(qualdo, "hideout", self);
        ai_lib.setDefaultCalmMood(qualdo, "npc_consoling");
        return;
    }
    public void spawnLtLance(obj_id self) throws InterruptedException
    {
        location lanceLocation = new location(-6519.03f, 398.0f, 6044.26f, "corellia", null);
        obj_id lance = create.staticObject("lt_lance", lanceLocation);
        int lance_yaw = 180;
        setYaw(lance, lance_yaw);
        attachScript(lance, "conversation.corvette_rebel_pilot");
        setObjVar(lance, "space_dungeon.ticket.point", "corvette_rebel_pilot");
        setObjVar(lance, "space_dungeon.ticket.dungeon", "corvette_rebel");
        attachScript(lance, "item.travel_ticket.travel_space_dungeon");
        setObjVar(self, "HideoutInhabitants.lt_lance", lance);
        setObjVar(lance, "hideout", self);
        return;
    }
    public void spawnWedge(obj_id self) throws InterruptedException
    {
        obj_id storage1 = getCellId(self, "storage1");
        location wedgeLocation = new location(0.01f, 1.01f, -22.01f, "corellia", storage1);
        obj_id wedge = create.object("wedge_antilles", wedgeLocation);
        setObjVar(self, "HideoutInhabitants.wedge", wedge);
        setObjVar(wedge, "hideout", self);
        int wedge_yaw = 0;
        setYaw(wedge, wedge_yaw);
        ai_lib.setDefaultCalmMood(wedge, "npc_sitting_table");
        return;
    }
    public void spawnWedgetech(obj_id self) throws InterruptedException
    {
        obj_id storage1 = getCellId(self, "storage1");
        location techLocation = new location(-4.00f, 1.01f, -20.94f, "corellia", storage1);
        obj_id tech = create.object("rebel_corporal", techLocation);
        int tech_yaw = -114;
        setYaw(tech, tech_yaw);
        setObjVar(self, "HideoutInhabitants.wedgetech", tech);
        setObjVar(tech, "hideout", self);
        ai_lib.setDefaultCalmMood(tech, "ui");
        return;
    }
    public void spawnLeia(obj_id self) throws InterruptedException
    {
        obj_id storage2 = getCellId(self, "storage2");
        location leiaLocation = new location(-11.13f, 7.01f, 11.31f, "corellia", storage2);
        obj_id leia = create.staticObject("leia_organa", leiaLocation);
        int leia_yaw = 134;
        setYaw(leia, leia_yaw);
        setObjVar(self, "HideoutInhabitants.leia", leia);
        setObjVar(leia, "hideout", self);
        return;
    }
    public void spawnLeiatech(obj_id self) throws InterruptedException
    {
        obj_id storage2 = getCellId(self, "storage2");
        location techLocation = new location(-12.94f, 7.01f, 9.54f, "corellia", storage2);
        obj_id tech = create.object("rebel_army_captain", techLocation);
        int tech_yaw = 89;
        setYaw(tech, tech_yaw);
        setObjVar(self, "HideoutInhabitants.leiatech", tech);
        setObjVar(tech, "hideout", self);
        ai_lib.setDefaultCalmMood(tech, "ui");
        return;
    }
    public void spawnLeiaguard1(obj_id self) throws InterruptedException
    {
        obj_id storage2 = getCellId(self, "storage2");
        location guard1Location = new location(-6.45f, 7.01f, 6.54f, "corellia", storage2);
        obj_id guard1 = create.object("rebel_trooper", guard1Location);
        int guard1_yaw = 124;
        setYaw(guard1, guard1_yaw);
        setObjVar(self, "HideoutInhabitants.leiaguard1", guard1);
        setObjVar(guard1, "hideout", self);
        ai_lib.setDefaultCalmMood(guard1, "ui");
        return;
    }
    public void spawnLeiaguard2(obj_id self) throws InterruptedException
    {
        obj_id storage2 = getCellId(self, "storage2");
        location guard2Location = new location(-2.37f, 7.01f, 6.54f, "corellia", storage2);
        obj_id guard2 = create.object("rebel_trooper", guard2Location);
        int guard2_yaw = -111;
        setYaw(guard2, guard2_yaw);
        setObjVar(self, "HideoutInhabitants.leiaguard2", guard2);
        setObjVar(guard2, "hideout", self);
        ai_lib.setDefaultCalmMood(guard2, "ui");
        return;
    }
    public void spawnTrooper1(obj_id self) throws InterruptedException
    {
        obj_id hall2 = getCellId(self, "hall2");
        location trooper1Location = new location(5.59f, 7.01f, -0.30f, "corellia", hall2);
        obj_id trooper1 = create.object("rebel_trooper", trooper1Location);
        setObjVar(self, "HideoutInhabitants.trooper1", trooper1);
        setObjVar(trooper1, "hideout", self);
        ai_lib.setDefaultCalmMood(trooper1, "ui");
        attachScript(trooper1, "theme_park.corellia.patrol.trooper1");
        attachScript(trooper1, "theme_park.corellia.rebel_hideout_path");
        int T1_yaw = 3;
        setYaw(trooper1, T1_yaw);
        return;
    }
    public void spawnTrooper2(obj_id self) throws InterruptedException
    {
        obj_id hall2 = getCellId(self, "hall2");
        location trooper2Location = new location(12.29f, 7.01f, -9.11f, "corellia", hall2);
        obj_id trooper2 = create.object("rebel_trooper", trooper2Location);
        setObjVar(self, "HideoutInhabitants.trooper2", trooper2);
        setObjVar(trooper2, "hideout", self);
        ai_lib.setDefaultCalmMood(trooper2, "ui");
        attachScript(trooper2, "theme_park.corellia.patrol.trooper2");
        attachScript(trooper2, "theme_park.corellia.rebel_hideout_path");
        int T2_yaw = 88;
        setYaw(trooper2, T2_yaw);
        return;
    }
    public void spawnTrooper3(obj_id self) throws InterruptedException
    {
        obj_id mainhall = getCellId(self, "mainhall");
        location trooper3Location = new location(7.01f, 1.01f, 11.42f, "corellia", mainhall);
        obj_id trooper3 = create.object("rebel_trooper", trooper3Location);
        setObjVar(self, "HideoutInhabitants.trooper3", trooper3);
        setObjVar(trooper3, "hideout", self);
        attachScript(trooper3, "theme_park.corellia.patrol.trooper3");
        attachScript(trooper3, "theme_park.corellia.rebel_hideout_path");
        int T3_yaw = -53;
        setYaw(trooper3, T3_yaw);
        return;
    }
    public void spawnTrooper4(obj_id self) throws InterruptedException
    {
        obj_id jailcell2 = getCellId(self, "jailcell2");
        location trooper4Location = new location(-13f, 1.01f, -19f, "corellia", jailcell2);
        obj_id trooper4 = create.object("rebel_trooper", trooper4Location);
        setObjVar(self, "HideoutInhabitants.trooper4", trooper4);
        setObjVar(trooper4, "hideout", self);
        attachScript(trooper4, "theme_park.corellia.patrol.trooper4");
        attachScript(trooper4, "theme_park.corellia.rebel_hideout_path");
        int T4_yaw = 111;
        setYaw(trooper4, T4_yaw);
        return;
    }
    public void spawnNewtech1(obj_id self) throws InterruptedException
    {
        obj_id mainhall = getCellId(self, "mainhall");
        location newtech1Location = new location(-0.78f, 2.01f, 5.39f, "corellia", mainhall);
        obj_id newtech1 = create.object("rebel_second_lieutenant", newtech1Location);
        setObjVar(self, "HideoutInhabitants.newtech1", newtech1);
        setObjVar(newtech1, "hideout", self);
        int newtech1_yaw = -6;
        setYaw(newtech1, newtech1_yaw);
        attachScript(newtech1, "theme_park.corellia.patrol.newtech1");
        attachScript(newtech1, "theme_park.corellia.rebel_hideout_path");
        return;
    }
    public void spawnNewtech2(obj_id self) throws InterruptedException
    {
        obj_id storage1 = getCellId(self, "storage1");
        location newtech2Location = new location(3.78f, 1.01f, -21.35f, "corellia", storage1);
        obj_id newtech2 = create.object("rebel_staff_corporal", newtech2Location);
        setObjVar(self, "HideoutInhabitants.newtech2", newtech2);
        setObjVar(newtech2, "hideout", self);
        int newtech2_yaw = 114;
        setYaw(newtech2, newtech2_yaw);
        attachScript(newtech2, "theme_park.corellia.patrol.newtech2");
        attachScript(newtech2, "theme_park.corellia.rebel_hideout_path");
        return;
    }
    public void spawnMousedroid(obj_id self) throws InterruptedException
    {
        obj_id droid6 = getCellId(self, "mainhall");
        location mdroidLocation = new location(-2.72f, 1.01f, -0.51f, "corellia", droid6);
        obj_id mdroid = create.object("mouse_droid", mdroidLocation);
        setObjVar(self, "HideoutInhabitants.mdroid", mdroid);
        setObjVar(mdroid, "hideout", self);
        int mdroid_yaw = -20;
        setYaw(mdroid, mdroid_yaw);
        attachScript(mdroid, "theme_park.corellia.patrol.mdroid");
        attachScript(mdroid, "theme_park.corellia.rebel_hideout_path");
        factions.setFaction(mdroid, "Rebel");
        return;
    }
    public void spawnProtocoldroid(obj_id self) throws InterruptedException
    {
        obj_id droid2 = getCellId(self, "meeting3");
        location pdroidLocation = new location(13.01f, 1.01f, -3.65f, "corellia", droid2);
        obj_id pdroid = create.object("protocol_droid_3po_red", pdroidLocation);
        setObjVar(self, "HideoutInhabitants.pdroid", pdroid);
        setObjVar(pdroid, "hideout", self);
        int pdroid_yaw = 148;
        setYaw(pdroid, pdroid_yaw);
        attachScript(pdroid, "theme_park.corellia.patrol.pdroid");
        attachScript(pdroid, "theme_park.corellia.rebel_hideout_path");
        factions.setFaction(pdroid, "Rebel");
        return;
    }
    public void spawnSittingGuy1(obj_id self) throws InterruptedException
    {
        obj_id meeting2 = getCellId(self, "meeting2");
        location sitter1Location = new location(19.51f, 1.01f, 17.45f, "corellia", meeting2);
        obj_id sitter1 = create.staticObject("rebel_corporal", sitter1Location);
        int sitter1_yaw = -84;
        setYaw(sitter1, sitter1_yaw);
        setObjVar(self, "HideoutInhabitants.sitter1", sitter1);
        setObjVar(sitter1, "hideout", self);
        setAnimationMood(sitter1, "npc_sitting_chair");
        return;
    }
    public void spawnSittingGuy2(obj_id self) throws InterruptedException
    {
        obj_id meeting2 = getCellId(self, "meeting2");
        location sitter2Location = new location(19.49f, 1.01f, 19.96f, "corellia", meeting2);
        obj_id sitter2 = create.staticObject("rebel_corporal", sitter2Location);
        int sitter2_yaw = -121;
        setYaw(sitter2, sitter2_yaw);
        setObjVar(self, "HideoutInhabitants.sitter2", sitter2);
        setObjVar(sitter2, "hideout", self);
        setAnimationMood(sitter2, "npc_sitting_chair");
        return;
    }
    public void spawnSittingGuy3(obj_id self) throws InterruptedException
    {
        obj_id meeting2 = getCellId(self, "meeting2");
        location sitter3Location = new location(16.9f, 1.01f, 18.54f, "corellia", meeting2);
        obj_id sitter3 = create.staticObject("rebel_first_lieutenant", sitter3Location);
        int sitter3_yaw = 81;
        setYaw(sitter3, sitter3_yaw);
        setObjVar(self, "HideoutInhabitants.sitter3", sitter3);
        setObjVar(sitter3, "hideout", self);
        setAnimationMood(sitter3, "npc_sitting_table");
        return;
    }
    public void spawnSittingGuy4(obj_id self) throws InterruptedException
    {
        obj_id meeting1 = getCellId(self, "meeting1");
        location sitter4Location = new location(-20.12f, 1.01f, 19.27f, "corellia", meeting1);
        obj_id sitter4 = create.staticObject("rebel_corporal", sitter4Location);
        int sitter4_yaw = -97;
        setYaw(sitter4, sitter4_yaw);
        setObjVar(self, "HideoutInhabitants.sitter4", sitter4);
        setObjVar(sitter4, "hideout", self);
        setAnimationMood(sitter4, "npc_sitting_table");
        return;
    }
    public void spawnSittingGuy5(obj_id self) throws InterruptedException
    {
        obj_id meeting1 = getCellId(self, "meeting1");
        location sitter5Location = new location(-15.73f, 1.01f, 14.19f, "corellia", meeting1);
        obj_id sitter5 = create.staticObject("rebel_staff_corporal", sitter5Location);
        int sitter5_yaw = 83;
        setYaw(sitter5, sitter5_yaw);
        setObjVar(self, "HideoutInhabitants.sitter5", sitter5);
        setObjVar(sitter5, "hideout", self);
        setAnimationMood(sitter5, "npc_sitting_table");
        return;
    }
    public void spawnSittingGuy6(obj_id self) throws InterruptedException
    {
        obj_id meeting1 = getCellId(self, "meeting1");
        location sitter6Location = new location(-15.8f, 1.01f, 18.62f, "corellia", meeting1);
        obj_id sitter6 = create.staticObject("rebel_first_lieutenant", sitter6Location);
        int sitter6_yaw = 106;
        setYaw(sitter6, sitter6_yaw);
        setObjVar(self, "HideoutInhabitants.sitter6", sitter6);
        setObjVar(sitter6, "hideout", self);
        setAnimationMood(sitter6, "npc_sitting_chair");
        return;
    }
    public void spawnSittingGuy7(obj_id self) throws InterruptedException
    {
        obj_id storage2 = getCellId(self, "storage2");
        location sitter7Location = new location(-3.45f, 7.01f, 12.62f, "corellia", storage2);
        obj_id sitter7 = create.staticObject("rebel_corporal", sitter7Location);
        int sitter7_yaw = 94;
        setYaw(sitter7, sitter7_yaw);
        setObjVar(self, "HideoutInhabitants.sitter7", sitter7);
        setObjVar(sitter7, "hideout", self);
        setAnimationMood(sitter7, "npc_sitting_chair");
        return;
    }
    public void spawnSittingGuy8(obj_id self) throws InterruptedException
    {
        obj_id storage2 = getCellId(self, "storage2");
        location sitter8Location = new location(0.02f, 7.01f, 12.81f, "corellia", storage2);
        obj_id sitter8 = create.staticObject("rebel_corporal", sitter8Location);
        int sitter8_yaw = -109;
        setYaw(sitter8, sitter8_yaw);
        setObjVar(self, "HideoutInhabitants.sitter8", sitter8);
        setObjVar(sitter8, "hideout", self);
        setAnimationMood(sitter8, "npc_sitting_chair");
        return;
    }
    public void spawnSittingGuy9(obj_id self) throws InterruptedException
    {
        obj_id foyer2 = getCellId(self, "foyer2");
        location sitter9Location = new location(5.9f, 7.01f, 9.92f, "corellia", foyer2);
        obj_id sitter9 = create.staticObject("rebel_colonel", sitter9Location);
        int sitter9_yaw = 86;
        setYaw(sitter9, sitter9_yaw);
        setObjVar(self, "HideoutInhabitants.sitter9", sitter9);
        setObjVar(sitter9, "hideout", self);
        setAnimationMood(sitter9, "npc_sitting_table");
        return;
    }
    public void spawnTurret01(obj_id self) throws InterruptedException
    {
        location loc = new location(-6545.16f, 405f, 5916.49f, "corellia", null);
        int T_yaw = -121;
        obj_id turret01 = advanced_turret.createTurret(loc, T_yaw, advanced_turret.TYPE_DISH, advanced_turret.SIZE_SMALL, advanced_turret.DAMAGE_ELEMENTAL_HEAT, 1000, 3000, 10000, 30.0f, 2.0f, "Rebel");
        attachScript(turret01, "theme_park.corellia.patrol.turret");
        setObjVar(self, "HideoutInhabitants.turret01", turret01);
        setObjVar(turret01, "hideout", self);
        return;
    }
    public void spawnTurret2(obj_id self) throws InterruptedException
    {
        location loc = new location(-6530.26f, 405f, 5913.65f, "corellia", null);
        int T_yaw = -161;
        obj_id turret2 = advanced_turret.createTurret(loc, T_yaw, advanced_turret.TYPE_TOWER, advanced_turret.SIZE_SMALL, advanced_turret.DAMAGE_ELEMENTAL_HEAT, 1000, 3000, 10000, 30.0f, 2.0f, "Rebel");
        attachScript(turret2, "theme_park.corellia.patrol.turret");
        setObjVar(self, "HideoutInhabitants.turret2", turret2);
        setObjVar(turret2, "hideout", self);
        return;
    }
    public void spawnTurret3(obj_id self) throws InterruptedException
    {
        location loc = new location(-6515.82f, 405f, 5904.84f, "corellia", null);
        int T_yaw = 167;
        obj_id turret3 = advanced_turret.createTurret(loc, T_yaw, advanced_turret.TYPE_DISH, advanced_turret.SIZE_SMALL, advanced_turret.DAMAGE_ELEMENTAL_HEAT, 1000, 3000, 10000, 30.0f, 2.0f, "Rebel");
        attachScript(turret3, "theme_park.corellia.patrol.turret");
        setObjVar(self, "HideoutInhabitants.turret3", turret3);
        setObjVar(turret3, "hideout", self);
        return;
    }
    public void spawnTurret4(obj_id self) throws InterruptedException
    {
        location loc = new location(-6572f, 405f, 5939.71f, "corellia", null);
        int T_yaw = -45;
        obj_id turret4 = advanced_turret.createTurret(loc, T_yaw, advanced_turret.TYPE_TOWER, advanced_turret.SIZE_SMALL, advanced_turret.DAMAGE_ELEMENTAL_HEAT, 1000, 3000, 10000, 30.0f, 2.0f, "Rebel");
        attachScript(turret4, "theme_park.corellia.patrol.turret");
        setObjVar(self, "HideoutInhabitants.turret4", turret4);
        setObjVar(turret4, "hideout", self);
        return;
    }
    public void spawnTurret5(obj_id self) throws InterruptedException
    {
        location loc = new location(-6481.84f, 405f, 5912.74f, "corellia", null);
        int T_yaw = 79;
        obj_id turret5 = advanced_turret.createTurret(loc, T_yaw, advanced_turret.TYPE_TOWER, advanced_turret.SIZE_SMALL, advanced_turret.DAMAGE_ELEMENTAL_HEAT, 1000, 3000, 10000, 30.0f, 2.0f, "Rebel");
        attachScript(turret5, "theme_park.corellia.patrol.turret");
        setObjVar(self, "HideoutInhabitants.turret5", turret5);
        setObjVar(turret5, "hideout", self);
        return;
    }
    public void spawnTurret6(obj_id self) throws InterruptedException
    {
        location loc = new location(-6560.04f, 404f, 5965.22f, "corellia", null);
        int T_yaw = -117;
        obj_id turret6 = advanced_turret.createTurret(loc, T_yaw, advanced_turret.TYPE_TOWER, advanced_turret.SIZE_LARGE, advanced_turret.DAMAGE_ELEMENTAL_HEAT, 3000, 5000, 30000, 50.0f, 2.0f, "Rebel");
        attachScript(turret6, "theme_park.corellia.patrol.turret");
        setObjVar(self, "HideoutInhabitants.turret6", turret6);
        setObjVar(turret6, "hideout", self);
        return;
    }
    public void spawnTurret7(obj_id self) throws InterruptedException
    {
        location loc = new location(-6536.1f, 404f, 5941.59f, "corellia", null);
        int T_yaw = -148;
        obj_id turret7 = advanced_turret.createTurret(loc, T_yaw, advanced_turret.TYPE_TOWER, advanced_turret.SIZE_LARGE, advanced_turret.DAMAGE_ELEMENTAL_HEAT, 3000, 5000, 30000, 50.0f, 2.0f, "Rebel");
        attachScript(turret7, "theme_park.corellia.patrol.turret");
        setObjVar(self, "HideoutInhabitants.turret7", turret7);
        setObjVar(turret7, "hideout", self);
        return;
    }
    public void spawnTurret8(obj_id self) throws InterruptedException
    {
        location loc = new location(-6510.06f, 404f, 5931.47f, "corellia", null);
        int T_yaw = -170;
        obj_id turret8 = advanced_turret.createTurret(loc, T_yaw, advanced_turret.TYPE_TOWER, advanced_turret.SIZE_LARGE, advanced_turret.DAMAGE_ELEMENTAL_HEAT, 3000, 5000, 30000, 50.0f, 2.0f, "Rebel");
        attachScript(turret8, "theme_park.corellia.patrol.turret");
        setObjVar(self, "HideoutInhabitants.turret8", turret8);
        setObjVar(turret8, "hideout", self);
        return;
    }
    public void spawnTurret9(obj_id self) throws InterruptedException
    {
        location loc = new location(-6474.96f, 404f, 5937.97f, "corellia", null);
        int T_yaw = 156;
        obj_id turret9 = advanced_turret.createTurret(loc, T_yaw, advanced_turret.TYPE_TOWER, advanced_turret.SIZE_LARGE, advanced_turret.DAMAGE_ELEMENTAL_HEAT, 3000, 5000, 30000, 50.0f, 2.0f, "Rebel");
        attachScript(turret9, "theme_park.corellia.patrol.turret");
        setObjVar(self, "HideoutInhabitants.turret9", turret9);
        setObjVar(turret9, "hideout", self);
        return;
    }
    public void spawnTurret10(obj_id self) throws InterruptedException
    {
        location loc = new location(-6443.04f, 404f, 6000.2f, "corellia", null);
        int T_yaw = 79;
        obj_id turret10 = advanced_turret.createTurret(loc, T_yaw, advanced_turret.TYPE_TOWER, advanced_turret.SIZE_LARGE, advanced_turret.DAMAGE_ELEMENTAL_HEAT, 3000, 5000, 30000, 50.0f, 2.0f, "Rebel");
        attachScript(turret10, "theme_park.corellia.patrol.turret");
        setObjVar(self, "HideoutInhabitants.turret10", turret10);
        setObjVar(turret10, "hideout", self);
        return;
    }
    public void spawnTurret11(obj_id self) throws InterruptedException
    {
        location loc = new location(-6457f, 404f, 6032.01f, "corellia", null);
        int T_yaw = 53;
        obj_id turret11 = advanced_turret.createTurret(loc, T_yaw, advanced_turret.TYPE_TOWER, advanced_turret.SIZE_LARGE, advanced_turret.DAMAGE_ELEMENTAL_HEAT, 3000, 5000, 30000, 50.0f, 2.0f, "Rebel");
        attachScript(turret11, "theme_park.corellia.patrol.turret");
        setObjVar(self, "HideoutInhabitants.turret11", turret11);
        setObjVar(turret11, "hideout", self);
        return;
    }
    public void spawnTurret12(obj_id self) throws InterruptedException
    {
        location loc = new location(-6434.14f, 405f, 6032.2f, "corellia", null);
        int T_yaw = -23;
        obj_id turret12 = advanced_turret.createTurret(loc, T_yaw, advanced_turret.TYPE_TOWER, advanced_turret.SIZE_SMALL, advanced_turret.DAMAGE_ELEMENTAL_HEAT, 1000, 3000, 10000, 30.0f, 2.0f, "Rebel");
        attachScript(turret12, "theme_park.corellia.patrol.turret");
        setObjVar(self, "HideoutInhabitants.turret12", turret12);
        setObjVar(turret12, "hideout", self);
        return;
    }
    public void spawnTurret13(obj_id self) throws InterruptedException
    {
        location loc = new location(-6424.52f, 405f, 6019.65f, "corellia", null);
        int T_yaw = 157;
        obj_id turret13 = advanced_turret.createTurret(loc, T_yaw, advanced_turret.TYPE_TOWER, advanced_turret.SIZE_SMALL, advanced_turret.DAMAGE_ELEMENTAL_HEAT, 1000, 3000, 10000, 30.0f, 2.0f, "Rebel");
        attachScript(turret13, "theme_park.corellia.patrol.turret");
        setObjVar(self, "HideoutInhabitants.turret13", turret13);
        setObjVar(turret13, "hideout", self);
        return;
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
        if (text.equals("kill celebs"))
        {
            killCelebs(self);
        }
        if (text.equals("kill droids"))
        {
            killDroids(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int doGating(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id foyer1 = getCellId(self, "foyer1");
        obj_id hall2 = getCellId(self, "hall2");
        attachScript(foyer1, "theme_park.gating.rebel.foyer1_gate");
        attachScript(hall2, "theme_park.gating.rebel.foyer1_gate");
        return SCRIPT_CONTINUE;
    }
    public int turretDied(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id existing = getObjIdObjVar(self, "HideoutInhabitants.turret01");
        if (!existing.isLoaded())
        {
            int timeOut = rand(20, 40);
            spawnTurret01(self);
        }
        obj_id existing2 = getObjIdObjVar(self, "HideoutInhabitants.turret2");
        if (!existing2.isLoaded())
        {
            int timeOut = rand(20, 40);
            spawnTurret2(self);
        }
        obj_id existing3 = getObjIdObjVar(self, "HideoutInhabitants.turret3");
        if (!existing3.isLoaded())
        {
            int timeOut = rand(20, 40);
            spawnTurret3(self);
        }
        obj_id existing4 = getObjIdObjVar(self, "HideoutInhabitants.turret4");
        if (!existing4.isLoaded())
        {
            int timeOut = rand(20, 40);
            spawnTurret4(self);
        }
        obj_id existing5 = getObjIdObjVar(self, "HideoutInhabitants.turret5");
        if (!existing5.isLoaded())
        {
            int timeOut = rand(20, 40);
            spawnTurret5(self);
        }
        obj_id existing6 = getObjIdObjVar(self, "HideoutInhabitants.turret6");
        if (!existing6.isLoaded())
        {
            int timeOut = rand(20, 40);
            spawnTurret6(self);
        }
        obj_id existing7 = getObjIdObjVar(self, "HideoutInhabitants.turret7");
        if (!existing7.isLoaded())
        {
            int timeOut = rand(20, 40);
            spawnTurret7(self);
        }
        obj_id existing8 = getObjIdObjVar(self, "HideoutInhabitants.turret8");
        if (!existing8.isLoaded())
        {
            int timeOut = rand(20, 40);
            spawnTurret8(self);
        }
        obj_id existing9 = getObjIdObjVar(self, "HideoutInhabitants.turret9");
        if (!existing9.isLoaded())
        {
            int timeOut = rand(20, 40);
            spawnTurret9(self);
        }
        obj_id existing10 = getObjIdObjVar(self, "HideoutInhabitants.turret10");
        if (!existing10.isLoaded())
        {
            int timeOut = rand(20, 40);
            spawnTurret10(self);
        }
        obj_id existing11 = getObjIdObjVar(self, "HideoutInhabitants.turret11");
        if (!existing11.isLoaded())
        {
            int timeOut = rand(20, 40);
            spawnTurret11(self);
        }
        obj_id existing12 = getObjIdObjVar(self, "HideoutInhabitants.turret12");
        if (!existing12.isLoaded())
        {
            int timeOut = rand(20, 40);
            spawnTurret12(self);
        }
        obj_id existing13 = getObjIdObjVar(self, "HideoutInhabitants.turret13");
        if (!existing13.isLoaded())
        {
            int timeOut = rand(20, 40);
            spawnTurret13(self);
        }
        return SCRIPT_CONTINUE;
    }
    public void killCelebs(obj_id self) throws InterruptedException
    {
        obj_id goldenrod = getObjIdObjVar(self, "HideoutInhabitants.c3po");
        destroyObject(goldenrod);
        removeObjVar(self, "HideoutInhabitants.c3po");
        destroyObject(getObjIdObjVar(self, "HideoutInhabitants.r2d2"));
        removeObjVar(self, "HideoutInhabitants.r2d2");
        destroyObject(getObjIdObjVar(self, "HideoutInhabitants.qualdo"));
        removeObjVar(self, "HideoutInhabitants.qualdo");
        destroyObject(getObjIdObjVar(self, "HideoutInhabitants.wedge"));
        removeObjVar(self, "HideoutInhabitants.wedge");
        destroyObject(getObjIdObjVar(self, "HideoutInhabitants.leia"));
        removeObjVar(self, "HideoutInhabitants.leia");
        destroyObject(getObjIdObjVar(self, "HideoutInhabitants.commtech"));
        removeObjVar(self, "HideoutInhabitants.commtech");
        destroyObject(getObjIdObjVar(self, "HideoutInhabitants.patient1"));
        removeObjVar(self, "HideoutInhabitants.patient1");
        destroyObject(getObjIdObjVar(self, "HideoutInhabitants.patient2"));
        removeObjVar(self, "HideoutInhabitants.patient2");
        destroyObject(getObjIdObjVar(self, "HideoutInhabitants.wedgetech"));
        removeObjVar(self, "HideoutInhabitants.wedgetech");
        destroyObject(getObjIdObjVar(self, "HideoutInhabitants.leiatech"));
        removeObjVar(self, "HideoutInhabitants.leiatech");
        destroyObject(getObjIdObjVar(self, "HideoutInhabitants.leiaguard1"));
        removeObjVar(self, "HideoutInhabitants.leiaguard1");
        destroyObject(getObjIdObjVar(self, "HideoutInhabitants.leiaguard2"));
        removeObjVar(self, "HideoutInhabitants.leiaguard2");
        destroyObject(getObjIdObjVar(self, "HideoutInhabitants.trooper1"));
        removeObjVar(self, "HideoutInhabitants.trooper1");
        destroyObject(getObjIdObjVar(self, "HideoutInhabitants.trooper2"));
        removeObjVar(self, "HideoutInhabitants.trooper2");
        destroyObject(getObjIdObjVar(self, "HideoutInhabitants.trooper3"));
        removeObjVar(self, "HideoutInhabitants.trooper3");
        destroyObject(getObjIdObjVar(self, "HideoutInhabitants.trooper4"));
        removeObjVar(self, "HideoutInhabitants.trooper4");
        destroyObject(getObjIdObjVar(self, "HideoutInhabitants.newtech1"));
        removeObjVar(self, "HideoutInhabitants.newtech1");
        destroyObject(getObjIdObjVar(self, "HideoutInhabitants.newtech2"));
        removeObjVar(self, "HideoutInhabitants.newtech2");
        destroyObject(getObjIdObjVar(self, "HideoutInhabitants.mdroid"));
        removeObjVar(self, "HideoutInhabitants.mdroid");
        destroyObject(getObjIdObjVar(self, "HideoutInhabitants.pdroid"));
        removeObjVar(self, "HideoutInhabitants.pdroid");
        destroyObject(getObjIdObjVar(self, "HideoutInhabitants.sitter1"));
        removeObjVar(self, "HideoutInhabitants.sitter1");
        destroyObject(getObjIdObjVar(self, "HideoutInhabitants.sitter2"));
        removeObjVar(self, "HideoutInhabitants.sitter2");
        destroyObject(getObjIdObjVar(self, "HideoutInhabitants.sitter3"));
        removeObjVar(self, "HideoutInhabitants.sitter3");
        destroyObject(getObjIdObjVar(self, "HideoutInhabitants.sitter4"));
        removeObjVar(self, "HideoutInhabitants.sitter4");
        destroyObject(getObjIdObjVar(self, "HideoutInhabitants.sitter5"));
        removeObjVar(self, "HideoutInhabitants.sitter5");
        destroyObject(getObjIdObjVar(self, "HideoutInhabitants.sitter6"));
        removeObjVar(self, "HideoutInhabitants.sitter6");
        destroyObject(getObjIdObjVar(self, "HideoutInhabitants.sitter7"));
        removeObjVar(self, "HideoutInhabitants.sitter7");
        destroyObject(getObjIdObjVar(self, "HideoutInhabitants.sitter8"));
        removeObjVar(self, "HideoutInhabitants.sitter8");
        destroyObject(getObjIdObjVar(self, "HideoutInhabitants.sitter9"));
        removeObjVar(self, "HideoutInhabitants.sitter9");
        destroyObject(getObjIdObjVar(self, "HideoutInhabitants.turret01"));
        removeObjVar(self, "HideoutInhabitants.turret01");
        destroyObject(getObjIdObjVar(self, "HideoutInhabitants.turret2"));
        removeObjVar(self, "HideoutInhabitants.turret2");
        destroyObject(getObjIdObjVar(self, "HideoutInhabitants.turret3"));
        removeObjVar(self, "HideoutInhabitants.turret3");
        destroyObject(getObjIdObjVar(self, "HideoutInhabitants.turret4"));
        removeObjVar(self, "HideoutInhabitants.turret4");
        destroyObject(getObjIdObjVar(self, "HideoutInhabitants.turret5"));
        removeObjVar(self, "HideoutInhabitants.turret5");
        destroyObject(getObjIdObjVar(self, "HideoutInhabitants.turret6"));
        removeObjVar(self, "HideoutInhabitants.turret6");
        destroyObject(getObjIdObjVar(self, "HideoutInhabitants.turret7"));
        removeObjVar(self, "HideoutInhabitants.turret7");
        destroyObject(getObjIdObjVar(self, "HideoutInhabitants.turret8"));
        removeObjVar(self, "HideoutInhabitants.turret8");
        destroyObject(getObjIdObjVar(self, "HideoutInhabitants.turret9"));
        removeObjVar(self, "HideoutInhabitants.turret9");
        destroyObject(getObjIdObjVar(self, "HideoutInhabitants.turret10"));
        removeObjVar(self, "HideoutInhabitants.turret10");
        destroyObject(getObjIdObjVar(self, "HideoutInhabitants.turret11"));
        removeObjVar(self, "HideoutInhabitants.turret11");
        destroyObject(getObjIdObjVar(self, "HideoutInhabitants.turret12"));
        removeObjVar(self, "HideoutInhabitants.turret12");
        destroyObject(getObjIdObjVar(self, "HideoutInhabitants.turret13"));
        removeObjVar(self, "HideoutInhabitants.turret13");
        return;
    }
    public void killDroids(obj_id self) throws InterruptedException
    {
        destroyObject(getObjIdObjVar(self, "HideoutInhabitants.mouse1"));
        removeObjVar(self, "HideoutInhabitants.mouse1");
        destroyObject(getObjIdObjVar(self, "HideoutInhabitants.mouse2"));
        removeObjVar(self, "HideoutInhabitants.mouse2");
        destroyObject(getObjIdObjVar(self, "HideoutInhabitants.mouse3"));
        removeObjVar(self, "HideoutInhabitants.mouse3");
        destroyObject(getObjIdObjVar(self, "HideoutInhabitants.ra1"));
        removeObjVar(self, "HideoutInhabitants.ra1");
        return;
    }
}
