package script.theme_park.dungeon.death_watch_bunker;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.structure;
import script.library.player_structure;

public class water_pressure_master extends script.base_script
{
    public water_pressure_master()
    {
    }
    public static final String TBL_TERMINAL_PATH = "datatables/dungeon/death_watch/water_pressure.iff";
    public static final String TBL_LIGHTS_PATH = "datatables/dungeon/death_watch/water_pressure_light.iff";
    public static final String VAR_PRESSURE_TERMINALS = "water_pressure.terminals";
    public static final String VAR_PRESSURE_LIGHTS = "water_pressure.lights";
    public static final string_id RESTORED_PRESSURE = new string_id("dungeon/death_watch", "restored_pressure");
    public static final string_id WATER_PRESSURE_FAILED = new string_id("dungeon/death_watch", "water_pressure_failed");
    public static final string_id VALVE_OFF = new string_id("dungeon/death_watch", "valve_off");
    public static final string_id VALVE_ON = new string_id("dungeon/death_watch", "valve_on");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        loadWaterTerminals(self);
        loadWaterLights(self);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        loadWaterTerminals(self);
        loadWaterLights(self);
        if (hasObjVar(self, "death_watch.water_pressure_mission"))
        {
            removeObjVar(self, "death_watch.water_pressure_mission");
        }
        return SCRIPT_CONTINUE;
    }
    public void loadWaterTerminals(obj_id self) throws InterruptedException
    {
        obj_id[] terminals = structure.createStructureTerminals(self, TBL_TERMINAL_PATH);
        if (terminals != null && terminals.length > 0)
        {
            utils.setBatchObjVar(self, VAR_PRESSURE_TERMINALS, terminals);
        }
        return;
    }
    public void loadWaterLights(obj_id self) throws InterruptedException
    {
        obj_id[] lights = structure.createStructureTerminals(self, TBL_LIGHTS_PATH);
        if (lights != null && lights.length > 0)
        {
            utils.setBatchObjVar(self, VAR_PRESSURE_LIGHTS, lights);
        }
        return;
    }
    public void flipSwitchOne(obj_id terminal, obj_id player) throws InterruptedException
    {
        obj_id structure = player_structure.getStructure(terminal);
        if (!isIdValid(structure))
        {
            return;
        }
        obj_id lightOne = getObjIdObjVar(structure, "light1");
        obj_id lightTwo = getObjIdObjVar(structure, "light2");
        obj_id oldParticle1 = getObjIdObjVar(structure, "particle0");
        obj_id oldParticle2 = getObjIdObjVar(structure, "particle1");
        location oneLoc = getLocation(lightOne);
        location twoLoc = getLocation(lightTwo);
        if (!isIdValid(lightOne) || !isIdValid(lightTwo))
        {
            return;
        }
        if (hasObjVar(lightOne, "valveOn"))
        {
            removeObjVar(lightOne, "valveOn");
            setObjVar(lightOne, "valveOff", 1);
            destroyObject(oldParticle1);
            obj_id particleOne = createObject("object/static/particle/pt_light_streetlamp_red.iff", oneLoc);
            setObjVar(structure, "particle0", particleOne);
            sendSystemMessage(player, VALVE_OFF);
        }
        else if (hasObjVar(lightOne, "valveOff"))
        {
            removeObjVar(lightOne, "valveOff");
            setObjVar(lightOne, "valveOn", 1);
            destroyObject(oldParticle1);
            obj_id particleOne = createObject("object/static/particle/pt_light_streetlamp_green.iff", oneLoc);
            setObjVar(structure, "particle0", particleOne);
            sendSystemMessage(player, VALVE_ON);
        }
        if (hasObjVar(lightTwo, "valveOn"))
        {
            removeObjVar(lightTwo, "valveOn");
            setObjVar(lightTwo, "valveOff", 1);
            destroyObject(oldParticle2);
            obj_id particleTwo = createObject("object/static/particle/pt_light_streetlamp_red.iff", twoLoc);
            setObjVar(structure, "particle1", particleTwo);
        }
        else if (hasObjVar(lightTwo, "valveOff"))
        {
            removeObjVar(lightTwo, "valveOff");
            setObjVar(lightTwo, "valveOn", 1);
            destroyObject(oldParticle2);
            obj_id particleTwo = createObject("object/static/particle/pt_light_streetlamp_green.iff", twoLoc);
            setObjVar(structure, "particle1", particleTwo);
        }
        checkForSuccess(structure, player);
        return;
    }
    public void flipSwitchTwo(obj_id terminal, obj_id player) throws InterruptedException
    {
        obj_id structure = player_structure.getStructure(terminal);
        if (!isIdValid(structure))
        {
            return;
        }
        obj_id lightOne = getObjIdObjVar(structure, "light1");
        obj_id lightTwo = getObjIdObjVar(structure, "light2");
        obj_id lightThree = getObjIdObjVar(structure, "light3");
        obj_id oldParticle1 = getObjIdObjVar(structure, "particle0");
        obj_id oldParticle2 = getObjIdObjVar(structure, "particle1");
        obj_id oldParticle3 = getObjIdObjVar(structure, "particle2");
        location oneLoc = getLocation(lightOne);
        location twoLoc = getLocation(lightTwo);
        location threeLoc = getLocation(lightThree);
        if (!isIdValid(lightOne) || !isIdValid(lightTwo) || !isIdValid(lightThree))
        {
            return;
        }
        if (hasObjVar(lightOne, "valveOn"))
        {
            removeObjVar(lightOne, "valveOn");
            setObjVar(lightOne, "valveOff", 1);
            destroyObject(oldParticle1);
            obj_id particleOne = createObject("object/static/particle/pt_light_streetlamp_red.iff", oneLoc);
            setObjVar(structure, "particle0", particleOne);
        }
        else if (hasObjVar(lightOne, "valveOff"))
        {
            removeObjVar(lightOne, "valveOff");
            setObjVar(lightOne, "valveOn", 1);
            destroyObject(oldParticle1);
            obj_id particleOne = createObject("object/static/particle/pt_light_streetlamp_green.iff", oneLoc);
            setObjVar(structure, "particle0", particleOne);
        }
        if (hasObjVar(lightTwo, "valveOn"))
        {
            removeObjVar(lightTwo, "valveOn");
            setObjVar(lightTwo, "valveOff", 1);
            destroyObject(oldParticle2);
            obj_id particleTwo = createObject("object/static/particle/pt_light_streetlamp_red.iff", twoLoc);
            setObjVar(structure, "particle1", particleTwo);
            sendSystemMessage(player, VALVE_OFF);
        }
        else if (hasObjVar(lightTwo, "valveOff"))
        {
            removeObjVar(lightTwo, "valveOff");
            setObjVar(lightTwo, "valveOn", 1);
            destroyObject(oldParticle2);
            obj_id particleTwo = createObject("object/static/particle/pt_light_streetlamp_green.iff", twoLoc);
            setObjVar(structure, "particle1", particleTwo);
            sendSystemMessage(player, VALVE_ON);
        }
        if (hasObjVar(lightThree, "valveOn"))
        {
            removeObjVar(lightThree, "valveOn");
            setObjVar(lightThree, "valveOff", 1);
            destroyObject(oldParticle3);
            obj_id particleThree = createObject("object/static/particle/pt_light_streetlamp_red.iff", threeLoc);
            setObjVar(structure, "particle2", particleThree);
        }
        else if (hasObjVar(lightThree, "valveOff"))
        {
            removeObjVar(lightThree, "valveOff");
            setObjVar(lightThree, "valveOn", 1);
            destroyObject(oldParticle3);
            obj_id particleThree = createObject("object/static/particle/pt_light_streetlamp_green.iff", threeLoc);
            setObjVar(structure, "particle2", particleThree);
        }
        checkForSuccess(structure, player);
        return;
    }
    public void flipSwitchThree(obj_id terminal, obj_id player) throws InterruptedException
    {
        obj_id structure = player_structure.getStructure(terminal);
        if (!isIdValid(structure))
        {
            return;
        }
        obj_id lightTwo = getObjIdObjVar(structure, "light2");
        obj_id lightThree = getObjIdObjVar(structure, "light3");
        obj_id lightFour = getObjIdObjVar(structure, "light4");
        obj_id oldParticle2 = getObjIdObjVar(structure, "particle1");
        obj_id oldParticle3 = getObjIdObjVar(structure, "particle2");
        obj_id oldParticle4 = getObjIdObjVar(structure, "particle3");
        location twoLoc = getLocation(lightTwo);
        location threeLoc = getLocation(lightThree);
        location fourLoc = getLocation(lightFour);
        if (!isIdValid(lightTwo) || !isIdValid(lightThree) || !isIdValid(lightFour))
        {
            return;
        }
        if (hasObjVar(lightTwo, "valveOn"))
        {
            removeObjVar(lightTwo, "valveOn");
            setObjVar(lightTwo, "valveOff", 1);
            destroyObject(oldParticle2);
            obj_id particleTwo = createObject("object/static/particle/pt_light_streetlamp_red.iff", twoLoc);
            setObjVar(structure, "particle1", particleTwo);
        }
        else if (hasObjVar(lightTwo, "valveOff"))
        {
            removeObjVar(lightTwo, "valveOff");
            setObjVar(lightTwo, "valveOn", 1);
            destroyObject(oldParticle2);
            obj_id particleTwo = createObject("object/static/particle/pt_light_streetlamp_green.iff", twoLoc);
            setObjVar(structure, "particle1", particleTwo);
        }
        if (hasObjVar(lightThree, "valveOn"))
        {
            removeObjVar(lightThree, "valveOn");
            setObjVar(lightThree, "valveOff", 1);
            destroyObject(oldParticle3);
            obj_id particleThree = createObject("object/static/particle/pt_light_streetlamp_red.iff", threeLoc);
            setObjVar(structure, "particle2", particleThree);
            sendSystemMessage(player, VALVE_OFF);
        }
        else if (hasObjVar(lightThree, "valveOff"))
        {
            removeObjVar(lightThree, "valveOff");
            setObjVar(lightThree, "valveOn", 1);
            destroyObject(oldParticle3);
            obj_id particleThree = createObject("object/static/particle/pt_light_streetlamp_green.iff", threeLoc);
            setObjVar(structure, "particle2", particleThree);
            sendSystemMessage(player, VALVE_ON);
        }
        if (hasObjVar(lightFour, "valveOn"))
        {
            removeObjVar(lightFour, "valveOn");
            setObjVar(lightFour, "valveOff", 1);
            destroyObject(oldParticle4);
            obj_id particleFour = createObject("object/static/particle/pt_light_streetlamp_red.iff", fourLoc);
            setObjVar(structure, "particle3", particleFour);
        }
        else if (hasObjVar(lightFour, "valveOff"))
        {
            removeObjVar(lightFour, "valveOff");
            setObjVar(lightFour, "valveOn", 1);
            destroyObject(oldParticle4);
            obj_id particleFour = createObject("object/static/particle/pt_light_streetlamp_green.iff", fourLoc);
            setObjVar(structure, "particle3", particleFour);
        }
        checkForSuccess(structure, player);
        return;
    }
    public void flipSwitchFour(obj_id terminal, obj_id player) throws InterruptedException
    {
        obj_id structure = player_structure.getStructure(terminal);
        if (!isIdValid(structure))
        {
            return;
        }
        obj_id lightThree = getObjIdObjVar(structure, "light3");
        obj_id lightFour = getObjIdObjVar(structure, "light4");
        obj_id oldParticle3 = getObjIdObjVar(structure, "particle2");
        obj_id oldParticle4 = getObjIdObjVar(structure, "particle3");
        location threeLoc = getLocation(lightThree);
        location fourLoc = getLocation(lightFour);
        if (!isIdValid(lightThree) || !isIdValid(lightFour))
        {
            return;
        }
        if (hasObjVar(lightThree, "valveOn"))
        {
            removeObjVar(lightThree, "valveOn");
            setObjVar(lightThree, "valveOff", 1);
            destroyObject(oldParticle3);
            obj_id particleThree = createObject("object/static/particle/pt_light_streetlamp_red.iff", threeLoc);
            setObjVar(structure, "particle2", particleThree);
        }
        else if (hasObjVar(lightThree, "valveOff"))
        {
            removeObjVar(lightThree, "valveOff");
            setObjVar(lightThree, "valveOn", 1);
            destroyObject(oldParticle3);
            obj_id particleThree = createObject("object/static/particle/pt_light_streetlamp_green.iff", threeLoc);
            setObjVar(structure, "particle2", particleThree);
        }
        if (hasObjVar(lightFour, "valveOn"))
        {
            removeObjVar(lightFour, "valveOn");
            setObjVar(lightFour, "valveOff", 1);
            destroyObject(oldParticle4);
            obj_id particleFour = createObject("object/static/particle/pt_light_streetlamp_red.iff", fourLoc);
            setObjVar(structure, "particle3", particleFour);
            sendSystemMessage(player, VALVE_OFF);
        }
        else if (hasObjVar(lightFour, "valveOff"))
        {
            removeObjVar(lightFour, "valveOff");
            setObjVar(lightFour, "valveOn", 1);
            destroyObject(oldParticle4);
            obj_id particleFour = createObject("object/static/particle/pt_light_streetlamp_green.iff", fourLoc);
            setObjVar(structure, "particle3", particleFour);
            sendSystemMessage(player, VALVE_ON);
        }
        checkForSuccess(structure, player);
        return;
    }
    public void checkForSuccess(obj_id structure, obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return;
        }
        obj_id[] lights = utils.getObjIdBatchObjVar(structure, VAR_PRESSURE_LIGHTS);
        if (lights != null && lights.length > 0)
        {
            int numInGroup = lights.length;
            if (numInGroup < 1)
            {
                return;
            }
            for (int i = 0; i < numInGroup; i++)
            {
                obj_id thisLight = lights[i];
                if (hasObjVar(thisLight, "valveOff"))
                {
                    return;
                }
            }
            playClientEffectLoc(player, "clienteffect/dth_watch_water_pressure.cef", getLocation(player), 0f);
            removeObjVar(player, "death_watch.water_pressure");
            setObjVar(player, "death_watch.water_pressure_success", 1);
            removeObjVar(structure, "death_watch.water_pressure_mission");
            sendSystemMessage(player, RESTORED_PRESSURE);
            CustomerServiceLog("DUNGEON_DeathWatchBunker", "*Death Watch Air Vent: %TU has restored the water pressure.", player);
        }
        return;
    }
    public int handleTerminalSwitch(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        obj_id terminal = params.getObjId("terminal");
        if (!isIdValid(player) || !isIdValid(terminal))
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(terminal, "valve1"))
        {
            flipSwitchOne(terminal, player);
        }
        else if (hasObjVar(terminal, "valve2"))
        {
            flipSwitchTwo(terminal, player);
        }
        else if (hasObjVar(terminal, "valve3"))
        {
            flipSwitchThree(terminal, player);
        }
        else if (hasObjVar(terminal, "valve4"))
        {
            flipSwitchFour(terminal, player);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleWaterPumpReset(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id particle1 = getObjIdObjVar(self, "particle0");
        obj_id particle2 = getObjIdObjVar(self, "particle1");
        obj_id particle3 = getObjIdObjVar(self, "particle2");
        obj_id particle4 = getObjIdObjVar(self, "particle3");
        destroyObject(particle1);
        destroyObject(particle2);
        destroyObject(particle3);
        destroyObject(particle4);
        obj_id[] lights = utils.getObjIdBatchObjVar(self, VAR_PRESSURE_LIGHTS);
        if (lights != null && lights.length > 0)
        {
            int numInGroup = lights.length;
            if (numInGroup < 1)
            {
                return SCRIPT_CONTINUE;
            }
            int pos = rand(0, 3);
            for (int i = 0; i < numInGroup; i++)
            {
                obj_id thisLight = lights[i];
                location light = getLocation(thisLight);
                if (pos == i)
                {
                    removeObjVar(thisLight, "valveOn");
                    setObjVar(thisLight, "valveOff", 1);
                    obj_id particle = createObject("object/static/particle/pt_light_streetlamp_red.iff", light);
                    setObjVar(self, "particle" + i, particle);
                }
                else 
                {
                    removeObjVar(thisLight, "valveOff");
                    setObjVar(thisLight, "valveOn", 1);
                    obj_id particle = createObject("object/static/particle/pt_light_streetlamp_green.iff", light);
                    setObjVar(self, "particle" + i, particle);
                }
            }
            obj_id player = getObjIdObjVar(self, "death_watch.water_pressure_mission");
            dictionary d1 = new dictionary();
            params.put("player", player);
            messageTo(self, "handleWaterPumpTimeOut", params, 1800f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleWaterPumpTimeOut(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, "death_watch.water_pressure_mission"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id oldPlayer = params.getObjId("player");
        obj_id newPlayer = getObjIdObjVar(self, "death_watch.water_pressure_mission");
        if (oldPlayer != newPlayer)
        {
            return SCRIPT_CONTINUE;
        }
        else 
        {
            sendSystemMessage(oldPlayer, WATER_PRESSURE_FAILED);
            removeObjVar(oldPlayer, "death_watch.water_pressure");
            removeObjVar(self, "death_watch.water_pressure_mission");
            setObjVar(oldPlayer, "death_watch.water_pressure_failed", 1);
        }
        return SCRIPT_CONTINUE;
    }
}
