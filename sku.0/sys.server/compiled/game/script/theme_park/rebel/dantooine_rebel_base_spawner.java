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

public class dantooine_rebel_base_spawner extends script.base_script
{
    public dantooine_rebel_base_spawner()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        spawnEveryone(self);
        messageTo(self, "doGuards", null, 10, true);
        messageTo(self, "doDroids", null, 30, true);
        messageTo(self, "doGating", null, 20, true);
        return SCRIPT_CONTINUE;
    }
    public void spawnEveryone(obj_id self) throws InterruptedException
    {
        debugSpeakMsg(self, "Spawning Everyone");
        spawnCelebs(self);
        return;
    }
    public void spawnCelebs(obj_id self) throws InterruptedException
    {
        spawnLieutenant(self);
        spawnAckbar(self);
        spawnMothma(self);
        spawnGeneral(self);
        spawnCaptain(self);
        spawnLeia(self);
    }
    public void spawnLieutenant(obj_id self) throws InterruptedException
    {
        obj_id room = getCellId(self, "meeting3");
        location lieutenantLocation = new location(16.15f, 1.01f, -1.65f, "dantooine", room);
        obj_id lieutenant = create.staticObject("rebel_first_lieutenant", lieutenantLocation);
        setObjVar(self, "HideoutInhabitants.lieutenant", lieutenant);
        setObjVar(lieutenant, "Hideout", self);
        setYaw(lieutenant, -82);
        return;
    }
    public void spawnAckbar(obj_id self) throws InterruptedException
    {
        obj_id room = getCellId(self, "jailcell2");
        location ackbarLocation = new location(-14.35f, 1.01f, -21.61f, "dantooine", room);
        obj_id ackbar = create.staticObject("ackbar", ackbarLocation);
        setObjVar(self, "HideoutInhabitants.ackbar", ackbar);
        setObjVar(ackbar, "Hideout", self);
        setYaw(ackbar, -4);
        return;
    }
    public void spawnLeia(obj_id self) throws InterruptedException
    {
        obj_id room = getCellId(self, "jailcell2");
        location leiaLocation = new location(-14.4f, 1.01f, -19.2f, "dantooine", room);
        int yaw = 179;
        obj_id leia = create.object("clone_relics_leia", leiaLocation);
        setYaw(leia, yaw);
        ai_lib.setDefaultCalmMood(leia, "conversation");
        setObjVar(self, "HideoutInhabitants.leia", leia);
        setObjVar(leia, "Hideout", self);
        return;
    }
    public void spawnMothma(obj_id self) throws InterruptedException
    {
        obj_id room = getCellId(self, "hall2");
        location mothmaLocation = new location(-2.51f, 7.01f, -11.8f, "dantooine", room);
        obj_id mothma = create.staticObject("mon_mothma", mothmaLocation);
        setObjVar(self, "HideoutInhabitants.mothma", mothma);
        setObjVar(mothma, "Hideout", self);
        setYaw(mothma, -27);
        return;
    }
    public void spawnGeneral(obj_id self) throws InterruptedException
    {
        obj_id room = getCellId(self, "hall2");
        location generalLocation = new location(-4.96f, 7.01f, -10.23f, "dantooine", room);
        obj_id general = create.staticObject("rebel_general", generalLocation);
        setObjVar(self, "HideoutInhabitants.general", general);
        setObjVar(general, "Hideout", self);
        setYaw(general, 119);
        return;
    }
    public void spawnCaptain(obj_id self) throws InterruptedException
    {
        obj_id room = getCellId(self, "hall2");
        location captainLocation = new location(-2.23f, 7.01f, -8.86f, "dantooine", room);
        obj_id captain = create.staticObject("rebel_army_captain", captainLocation);
        setObjVar(self, "HideoutInhabitants.captain", captain);
        setObjVar(captain, "Hideout", self);
        setYaw(captain, -169);
        return;
    }
    public int doGating(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id foyer1 = getCellId(self, "foyer1");
        obj_id foyer2 = getCellId(self, "foyer2");
        attachScript(foyer1, "theme_park.gating.rebel.foyer1_gate");
        attachScript(foyer2, "theme_park.gating.rebel.foyer1_gate");
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
        if (text.equals("kill droids"))
        {
            killDroids(self);
        }
        return SCRIPT_CONTINUE;
    }
    public void killCelebs(obj_id self) throws InterruptedException
    {
        destroyObject(getObjIdObjVar(self, "HideoutInhabitants.lance"));
        removeObjVar(self, "HideoutInhabitants.lance");
        destroyObject(getObjIdObjVar(self, "HideoutInhabitants.ackbar"));
        removeObjVar(self, "HideoutInhabitants.ackbar");
        destroyObject(getObjIdObjVar(self, "HideoutInhabitants.leia"));
        removeObjVar(self, "HideoutInhabitants.leia");
        destroyObject(getObjIdObjVar(self, "HideoutInhabitants.mothma"));
        removeObjVar(self, "HideoutInhabitants.mothma");
        destroyObject(getObjIdObjVar(self, "HideoutInhabitants.general"));
        removeObjVar(self, "HideoutInhabitants.general");
        destroyObject(getObjIdObjVar(self, "HideoutInhabitants.captain"));
        removeObjVar(self, "HideoutInhabitants.captain");
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
