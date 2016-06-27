package script.theme_park.imperial;

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

public class retreatspawner extends script.base_script
{
    public retreatspawner()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        spawnEveryone(self);
        messageTo(self, "doGating", null, 20, true);
        return SCRIPT_CONTINUE;
    }
    public void spawnEveryone(obj_id self) throws InterruptedException
    {
        spawnCelebs(self);
        spawnDroids(self);
        spawnExtras(self);
        messageTo(self, "spawnTheGuards", null, 10, true);
        return;
    }
    public void spawnCelebs(obj_id self) throws InterruptedException
    {
        spawnKaja(self);
        spawnRequisitionTerminal(self);
        spawnSpysTerminal(self);
        spawnLoam(self);
        spawnHethrir(self);
        spawnEmperor(self);
        spawnDarth(self);
        return;
    }
    public void spawnGuards(obj_id self) throws InterruptedException
    {
        spawnCrimson1(self);
        spawnCrimson2(self);
        spawnCrimson3(self);
        spawnCrimson4(self);
        obj_id st1 = spawnStormtrooper1(self);
        spawnStormtrooper2(self, st1);
        return;
    }
    public void spawnDroids(obj_id self) throws InterruptedException
    {
        spawnMouse1(self);
        spawnMouse2(self);
        spawnMouse3(self);
        spawnRa7(self);
        return;
    }
    public void spawnExtras(obj_id self) throws InterruptedException
    {
        spawnThrawn(self);
        spawnVeers(self);
        spawnShuttlePilot(self);
        spawnShuttlePilot2(self);
        spawnVelso(self);
        spawnRecordKeeper(self);
        return;
    }
    public void spawnKaja(obj_id self) throws InterruptedException
    {
        obj_id foyer = getCellId(self, "foyer");
        location kajaLocation = new location(2.06f, .2f, -13.74f, "naboo", foyer);
        obj_id kaja = create.staticObject("kaja_orzee", kajaLocation);
        int kaja_yaw = -18;
        setYaw(kaja, kaja_yaw);
        setObjVar(self, "RetreatInhabitants.kaja", kaja);
        setObjVar(kaja, "Retreat", self);
        return;
    }
    public void spawnRecordKeeper(obj_id self) throws InterruptedException
    {
        obj_id hall1 = getCellId(self, "hall1");
        location rkLocation = new location(33.3f, .2f, -36.9f, "naboo", hall1);
        obj_id recordKeeper = create.staticObject("record_keeper_imperial", rkLocation);
        int rk_yaw = -7;
        setYaw(recordKeeper, rk_yaw);
        setObjVar(self, "RetreatInhabitants.rk", recordKeeper);
        setObjVar(recordKeeper, "Retreat", self);
        attachScript(recordKeeper, "conversation.theme_park_record_keeper_imperial");
        setName(recordKeeper, "Wurson Harro");
        return;
    }
    public void spawnRequisitionTerminal(obj_id self) throws InterruptedException
    {
        String requisitionTerminalTemplate = "object/tangible/quest/imperial/itp_kaja_requisition_terminal.iff";
        obj_id inquisitor = getCellId(self, "inquisitor");
        location requisitionTerminalLoc = new location(-19.4f, 0.2f, -15.5f, "naboo", inquisitor);
        obj_id requisitionTerminal = createObject(requisitionTerminalTemplate, requisitionTerminalLoc);
        int myYaw = 166;
        setYaw(requisitionTerminal, myYaw);
        setObjVar(self, "RetreatInhabitants.requisitionTerminal", requisitionTerminal);
        setObjVar(requisitionTerminal, "Retreat", self);
        return;
    }
    public void spawnSpysTerminal(obj_id self) throws InterruptedException
    {
        String spyTerminalTemplate = "object/tangible/quest/imperial/itp_kaja_spy_data_terminal.iff";
        obj_id crafting1 = getCellId(self, "crafting1");
        location spyTerminalTemplateLoc = new location(-30.47f, -8.9f, -22.33f, "naboo", crafting1);
        obj_id spyTerminal = createObject(spyTerminalTemplate, spyTerminalTemplateLoc);
        int myYaw = 144;
        setYaw(spyTerminal, myYaw);
        setObjVar(self, "RetreatInhabitants.spyTerminal", spyTerminal);
        setObjVar(spyTerminal, "Retreat", self);
        return;
    }
    public void spawnLoam(obj_id self) throws InterruptedException
    {
        obj_id meetingroom = getCellId(self, "meetingroom");
        location loamLocation = new location(19.29f, .2f, -41.96f, "naboo", meetingroom);
        obj_id loam = create.staticObject("loam_redge", loamLocation);
        int loam_yaw = 120;
        setYaw(loam, loam_yaw);
        setObjVar(self, "RetreatInhabitants.loam", loam);
        setObjVar(loam, "Retreat", self);
        return;
    }
    public void spawnHethrir(obj_id self) throws InterruptedException
    {
        obj_id library = getCellId(self, "library");
        location hethrirLocation = new location(4.98f, .2f, -41.82f, "naboo", library);
        obj_id hethrir = create.staticObject("lord_hethrir", hethrirLocation);
        int hethrir_yaw = 0;
        setYaw(hethrir, hethrir_yaw);
        setObjVar(self, "RetreatInhabitants.hethrir", hethrir);
        setObjVar(hethrir, "Retreat", self);
        return;
    }
    public void spawnDarth(obj_id self) throws InterruptedException
    {
        obj_id vader = getCellId(self, "vader");
        location darthLocation = new location(-57.25f, .2f, -24.06f, "naboo", vader);
        obj_id darth = create.staticObject("darth_vader", darthLocation);
        int darth_yaw = 90;
        setYaw(darth, darth_yaw);
        setObjVar(self, "RetreatInhabitants.darth", darth);
        setObjVar(darth, "palace", self);
        return;
    }
    public void spawnEmperor(obj_id self) throws InterruptedException
    {
        obj_id emperor = getCellId(self, "emperor");
        location emperorLocation = new location(13.05f, 21f, -26.07f, "naboo", emperor);
        obj_id palpatine = create.staticObject("palpatine", emperorLocation);
        int palpatine_yaw = 180;
        setYaw(palpatine, palpatine_yaw);
        setObjVar(self, "RetreatInhabitants.emperor", palpatine);
        setObjVar(palpatine, "palace", self);
        return;
    }
    public void spawnCrimson1(obj_id self) throws InterruptedException
    {
        obj_id mainhall = getCellId(self, "mainhall");
        location c1Location = new location(17.0f, .2f, -31.0f, "naboo", mainhall);
        obj_id c1 = create.staticObject("emperor_royal_guard", c1Location);
        int c1_yaw = 0;
        setYaw(c1, c1_yaw);
        setObjVar(self, "RetreatInhabitants.c1", c1);
        setObjVar(c1, "Retreat", self);
        return;
    }
    public void spawnCrimson2(obj_id self) throws InterruptedException
    {
        obj_id mainhall = getCellId(self, "mainhall");
        location c2Location = new location(9.0f, .2f, -31.0f, "naboo", mainhall);
        obj_id c2 = create.staticObject("emperor_royal_guard", c2Location);
        int c2_yaw = 0;
        setYaw(c2, c2_yaw);
        setObjVar(self, "RetreatInhabitants.c2", c2);
        setObjVar(c2, "Retreat", self);
        return;
    }
    public void spawnCrimson3(obj_id self) throws InterruptedException
    {
        obj_id emperor = getCellId(self, "emperor");
        location c3Location = new location(15.35f, 20.0f, -28.0f, "naboo", emperor);
        obj_id c3 = create.staticObject("emperor_royal_guard", c3Location);
        int c3_yaw = 180;
        setYaw(c3, c3_yaw);
        setObjVar(self, "RetreatInhabitants.c3", c3);
        setObjVar(c3, "Retreat", self);
        return;
    }
    public void spawnCrimson4(obj_id self) throws InterruptedException
    {
        obj_id emperor = getCellId(self, "emperor");
        location c4Location = new location(10.55f, 20.0f, -28.0f, "naboo", emperor);
        obj_id c4 = create.staticObject("emperor_royal_guard", c4Location);
        int c4_yaw = 160;
        setYaw(c4, c4_yaw);
        setObjVar(self, "RetreatInhabitants.c4", c4);
        setObjVar(c4, "Retreat", self);
        return;
    }
    public obj_id spawnStormtrooper1(obj_id self) throws InterruptedException
    {
        obj_id mainhall = getCellId(self, "mainhall");
        location st1Location = new location(1.58f, .2f, -23.35f, "naboo", mainhall);
        obj_id st1 = create.staticObject("stormtrooper", st1Location);
        setObjVar(self, "RetreatInhabitants.st1", st1);
        setObjVar(st1, "Retreat", self);
        return st1;
    }
    public void spawnStormtrooper2(obj_id self, obj_id st1) throws InterruptedException
    {
        obj_id mainhall = getCellId(self, "mainhall");
        location st2Location = new location(3.58f, .2f, -23.35f, "naboo", mainhall);
        obj_id st2 = create.staticObject("stormtrooper", st2Location);
        setObjVar(self, "RetreatInhabitants.st2", st2);
        setObjVar(st2, "Retreat", self);
        dictionary troops1 = new dictionary();
        String myName = "st2";
        troops1.put("whichWay", myName);
        troops1.put("whoami", st2);
        troops1.put("faceWho", st1);
        messageTo(self, "doFacing", troops1, 5, true);
        return;
    }
    public void spawnVelso(obj_id self) throws InterruptedException
    {
        obj_id mainhall = getCellId(self, "mainhall");
        location ltvLocation = new location(23.4f, .2f, -19.4f, "naboo", mainhall);
        obj_id ltv = create.staticObject("lt_velso", ltvLocation);
        int ltv_yaw = -150;
        setYaw(ltv, ltv_yaw);
        setObjVar(self, "RetreatInhabitants.ltv", ltv);
        setObjVar(ltv, "Retreat", self);
        attachScript(ltv, "conversation.corvette_velso_imperial_destroy");
        return;
    }
    public void spawnStormtrooper3(obj_id self) throws InterruptedException
    {
        obj_id vader = getCellId(self, "vader");
        location st3Location = new location(-47.3f, 0.2f, -20.78f, "naboo", vader);
        obj_id st3 = create.staticObject("stormtrooper_rifleman", st3Location);
        int st3_yaw = 90;
        setYaw(st3, st3_yaw);
        setObjVar(self, "RetreatInhabitants.st3", st3);
        setObjVar(st3, "Retreat", self);
        return;
    }
    public void spawnStormtrooper4(obj_id self) throws InterruptedException
    {
        obj_id vader = getCellId(self, "vader");
        location st4Location = new location(-47.3f, 0.2f, -27.71f, "naboo", vader);
        obj_id st4 = create.staticObject("stormtrooper_rifleman", st4Location);
        int st4_yaw = 90;
        setYaw(st4, st4_yaw);
        setObjVar(self, "RetreatInhabitants.st4", st4);
        setObjVar(st4, "Retreat", self);
        return;
    }
    public void spawnMouse1(obj_id self) throws InterruptedException
    {
        obj_id mainhall = getCellId(self, "mainhall");
        location mouse1Location = new location(12.25f, .2f, -24.38f, "naboo", mainhall);
        obj_id mouse1 = create.staticObject("mouse_droid", mouse1Location);
        setObjVar(self, "RetreatInhabitants.mouse1", mouse1);
        setObjVar(mouse1, "Retreat", self);
        setObjVar(mouse1, "mouse1", 1);
        aiSetHomeLocation(mouse1, getLocation(mouse1));
        attachScript(mouse1, "theme_park.imperial.retreat_path");
        return;
    }
    public void spawnMouse2(obj_id self) throws InterruptedException
    {
        obj_id hall3 = getCellId(self, "hall3");
        location mouse2Location = new location(-8.91f, .2f, -12.67f, "naboo", hall3);
        obj_id mouse2 = create.staticObject("mouse_droid", mouse2Location);
        setObjVar(self, "RetreatInhabitants.mouse2", mouse2);
        setObjVar(mouse2, "Retreat", self);
        setObjVar(mouse2, "mouse2", 1);
        aiSetHomeLocation(mouse2, getLocation(mouse2));
        attachScript(mouse2, "theme_park.imperial.retreat_path");
        return;
    }
    public void spawnMouse3(obj_id self) throws InterruptedException
    {
        obj_id library = getCellId(self, "library");
        location mouse3Location = new location(11.63f, .2f, -51.12f, "naboo", library);
        obj_id mouse3 = create.staticObject("mouse_droid", mouse3Location);
        setObjVar(self, "RetreatInhabitants.mouse3", mouse3);
        setObjVar(mouse3, "Retreat", self);
        setObjVar(mouse3, "mouse3", 1);
        aiSetHomeLocation(mouse3, getLocation(mouse3));
        attachScript(mouse3, "theme_park.imperial.retreat_path");
        return;
    }
    public void spawnRa7(obj_id self) throws InterruptedException
    {
        obj_id library = getCellId(self, "library");
        location ra7Location = new location(4.0f, .2f, -45.3f, "naboo", library);
        obj_id ra7 = create.staticObject("ra7_bug_droid", ra7Location);
        int ra7_yaw = -6;
        setYaw(ra7, ra7_yaw);
        setObjVar(self, "RetreatInhabitants.ra7", ra7);
        setObjVar(ra7, "Retreat", self);
        return;
    }
    public void spawnThrawn(obj_id self) throws InterruptedException
    {
        location thrawnLocation = new location(2378.0f, 291.91f, -3922.0f, "naboo", obj_id.NULL_ID);
        obj_id thrawn = create.staticObject("thrawn", thrawnLocation);
        setFloatingTime(thrawn, 300);
        setObjVar(self, "RetreatInhabitants.thrawn", thrawn);
        setObjVar(thrawn, "Retreat", self);
        setObjVar(thrawn, "thrawn", 1);
        attachScript(thrawn, "theme_park.imperial.thrawn_veers_path");
        return;
    }
    public void spawnVeers(obj_id self) throws InterruptedException
    {
        location veersLocation = new location(2378.0f, 291.91f, -3920.0f, "naboo", obj_id.NULL_ID);
        obj_id veers = create.staticObject("veers", veersLocation);
        setFloatingTime(veers, 300);
        setObjVar(self, "RetreatInhabitants.veers", veers);
        setObjVar(veers, "Retreat", self);
        setObjVar(veers, "veers", 1);
        attachScript(veers, "theme_park.imperial.thrawn_veers_path");
        return;
    }
    public void spawnShuttlePilot(obj_id self) throws InterruptedException
    {
        location pilotLocation = new location(2447.5f, 0.0f, -3898.0f, "naboo", obj_id.NULL_ID);
        obj_id pilot = create.staticObject("imperial_pilot", pilotLocation);
        int pilot_yaw = -136;
        setYaw(pilot, pilot_yaw);
        setInvulnerable(pilot, true);
        setCreatureStatic(pilot, true);
        setAnimationMood(pilot, "npc_sitting_chair");
        attachScript(pilot, "conversation.corvette_imperial_pilot");
        setObjVar(pilot, "space_dungeon.ticket.point", "corvette_imperial_pilot");
        setObjVar(pilot, "space_dungeon.ticket.dungeon", "corvette_imperial");
        attachScript(pilot, "item.travel_ticket.travel_space_dungeon");
        setObjVar(self, "RetreatInhabitants.pilot", pilot);
        setObjVar(pilot, "Retreat", self);
        return;
    }
    public void spawnShuttlePilot2(obj_id self) throws InterruptedException
    {
        location pilot2Location = new location(2431.98f, 0.0f, -3887.32f, "naboo", obj_id.NULL_ID);
        obj_id pilot2 = create.staticObject("imperial_pilot", pilot2Location);
        int pilot2_yaw = -163;
        setYaw(pilot2, pilot2_yaw);
        setInvulnerable(pilot2, true);
        setCreatureStatic(pilot2, true);
        setAnimationMood(pilot2, "npc_use_terminal_high");
        setObjVar(self, "RetreatInhabitants.pilot2", pilot2);
        setObjVar(pilot2, "Retreat", self);
        return;
    }
    public int spawnTheGuards(obj_id self, dictionary params) throws InterruptedException
    {
        spawnGuards(self);
        setObjVar(self, "people", 1);
        return SCRIPT_CONTINUE;
    }
    public int doFacing(obj_id self, dictionary params) throws InterruptedException
    {
        String name = params.getString("whichWay");
        if (name.equals("st2"))
        {
            obj_id face = params.getObjId("faceWho");
            obj_id npc = params.getObjId("whoami");
            faceTo(npc, face);
            faceTo(face, npc);
        }
        return SCRIPT_CONTINUE;
    }
    public int doGating(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id mainhall = getCellId(self, "foyer");
        obj_id hall2 = getCellId(self, "hall2");
        obj_id elevator = getCellId(self, "empelevator");
        if (!hasScript(mainhall, "theme_park.gating.imperial.mainhall_block"))
        {
            attachScript(mainhall, "theme_park.gating.imperial.mainhall_block");
        }
        if (!hasScript(hall2, "theme_park.gating.imperial.hall2_block"))
        {
            attachScript(hall2, "theme_park.gating.imperial.hall2_block");
        }
        if (!hasScript(elevator, "theme_park.gating.imperial.elevator_block"))
        {
            attachScript(elevator, "theme_park.gating.imperial.elevator_block");
        }
        return SCRIPT_CONTINUE;
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
        if (text.equals("guards"))
        {
            spawnGuards(self);
        }
        if (text.equals("kill guards"))
        {
            killGuards(self);
        }
        if (text.equals("droids"))
        {
            spawnDroids(self);
        }
        if (text.equals("kill droids"))
        {
            killDroids(self);
        }
        if (text.equals("extras"))
        {
            spawnExtras(self);
        }
        if (text.equals("kill extras"))
        {
            killExtras(self);
        }
        return SCRIPT_CONTINUE;
    }
    public void killCelebs(obj_id self) throws InterruptedException
    {
        destroyObject(getObjIdObjVar(self, "RetreatInhabitants.kaja"));
        removeObjVar(self, "RetreatInhabitants.kaja");
        destroyObject(getObjIdObjVar(self, "RetreatInhabitants.loam"));
        removeObjVar(self, "RetreatInhabitants.loam");
        destroyObject(getObjIdObjVar(self, "RetreatInhabitants.hethrir"));
        removeObjVar(self, "RetreatInhabitants.hethrir");
        destroyObject(getObjIdObjVar(self, "RetreatInhabitants.darth"));
        removeObjVar(self, "RetreatInhabitants.darth");
        destroyObject(getObjIdObjVar(self, "RetreatInhabitants.emperor"));
        removeObjVar(self, "RetreatInhabitants.emperor");
        return;
    }
    public void killGuards(obj_id self) throws InterruptedException
    {
        destroyObject(getObjIdObjVar(self, "RetreatInhabitants.c1"));
        removeObjVar(self, "RetreatInhabitants.c1");
        destroyObject(getObjIdObjVar(self, "RetreatInhabitants.c2"));
        removeObjVar(self, "RetreatInhabitants.c2");
        destroyObject(getObjIdObjVar(self, "RetreatInhabitants.c3"));
        removeObjVar(self, "RetreatInhabitants.c3");
        destroyObject(getObjIdObjVar(self, "RetreatInhabitants.c4"));
        removeObjVar(self, "RetreatInhabitants.c4");
        destroyObject(getObjIdObjVar(self, "RetreatInhabitants.st1"));
        removeObjVar(self, "RetreatInhabitants.st1");
        destroyObject(getObjIdObjVar(self, "RetreatInhabitants.st2"));
        removeObjVar(self, "RetreatInhabitants.st2");
        destroyObject(getObjIdObjVar(self, "RetreatInhabitants.st3"));
        removeObjVar(self, "RetreatInhabitants.st3");
        destroyObject(getObjIdObjVar(self, "RetreatInhabitants.st4"));
        removeObjVar(self, "RetreatInhabitants.st4");
        return;
    }
    public void killDroids(obj_id self) throws InterruptedException
    {
        destroyObject(getObjIdObjVar(self, "RetreatInhabitants.mouse1"));
        removeObjVar(self, "RetreatInhabitants.mouse1");
        destroyObject(getObjIdObjVar(self, "RetreatInhabitants.mouse2"));
        removeObjVar(self, "RetreatInhabitants.mouse2");
        destroyObject(getObjIdObjVar(self, "RetreatInhabitants.mouse3"));
        removeObjVar(self, "RetreatInhabitants.mouse3");
        destroyObject(getObjIdObjVar(self, "RetreatInhabitants.ra7"));
        removeObjVar(self, "RetreatInhabitants.ra7");
        return;
    }
    public void killExtras(obj_id self) throws InterruptedException
    {
        destroyObject(getObjIdObjVar(self, "RetreatInhabitants.thrawn"));
        removeObjVar(self, "RetreatInhabitants.thrawn");
        destroyObject(getObjIdObjVar(self, "RetreatInhabitants.veers"));
        removeObjVar(self, "RetreatInhabitants.veers");
        destroyObject(getObjIdObjVar(self, "RetreatInhabitants.pilot"));
        removeObjVar(self, "RetreatInhabitants.pilot");
        destroyObject(getObjIdObjVar(self, "RetreatInhabitants.pilot2"));
        removeObjVar(self, "RetreatInhabitants.pilot2");
        destroyObject(getObjIdObjVar(self, "RetreatInhabitants.ltv"));
        removeObjVar(self, "RetreatInhabitants.ltv");
        destroyObject(getObjIdObjVar(self, "RetreatInhabitants.rk"));
        removeObjVar(self, "RetreatInhabitants.rk");
        destroyObject(getObjIdObjVar(self, "RetreatInhabitants.allard"));
        removeObjVar(self, "RetreatInhabitants.allard");
        return;
    }
}
