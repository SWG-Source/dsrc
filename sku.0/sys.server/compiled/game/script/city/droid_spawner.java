package script.city;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.utils;
import script.library.locations;

public class droid_spawner extends script.base_script
{
    public droid_spawner()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "checkForStart", null, 30, true);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "current"))
        {
            killAll(self);
            messageTo(self, "checkForStart", null, 30, true);
        }
        else 
        {
            messageTo(self, "checkForStart", null, 30, true);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnHearSpeech(obj_id self, obj_id speaker, String text) throws InterruptedException
    {
        if (!hasObjVar(speaker, "gm"))
        {
            return SCRIPT_OVERRIDE;
        }
        int stringCheck = text.indexOf("max");
        String population = text.substring(text.indexOf(" ") + 1, text.length());
        int maxPop = utils.stringToInt(population);
        if (stringCheck > -1)
        {
            setObjVar(self, "pop", maxPop);
        }
        if (text.equals("killall"))
        {
            killAll(self);
        }
        if (text.equals("restart"))
        {
            messageTo(self, "startSpawning", null, 1, true);
        }
        return SCRIPT_CONTINUE;
    }
    public int checkForStart(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "pop"))
        {
            messageTo(self, "startSpawning", null, 10, true);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            setObjVar(self, "pop", 3);
            messageTo(self, "checkForStart", null, 30, true);
            return SCRIPT_CONTINUE;
        }
    }
    public int startSpawning(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id spawner = getSelf();
        int maxPop = getIntObjVar(self, "pop");
        if (!hasObjVar(self, "current"))
        {
            setObjVar(self, "current", 1);
            doSpawn(self);
        }
        else 
        {
            int currentPop = getIntObjVar(self, "current");
            if (currentPop <= maxPop)
            {
                doSpawn(self);
            }
            else 
            {
                return SCRIPT_OVERRIDE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void doSpawn(obj_id self) throws InterruptedException
    {
        int currentPop = getIntObjVar(self, "current");
        int maxPop = getIntObjVar(self, "pop");
        location me = getLocation(self);
        String spawn = npcToSpawn();
        obj_id npc = create.object(spawn, me);
        attachScript(npc, "city.droid_wander");
        setInvulnerable(npc, true);
        setObjVar(self, "spawn." + currentPop, npc);
        setObjVar(npc, "spawnNum", "spawn." + currentPop);
        currentPop = currentPop + 1;
        setObjVar(self, "current", currentPop);
        messageTo(self, "startSpawning", null, 10, true);
        return;
    }
    public void killAll(obj_id self) throws InterruptedException
    {
        int increment = 1;
        int totalBodies = getIntObjVar(self, "pop");
        while (increment <= totalBodies)
        {
            obj_id npcToKill = getObjIdObjVar(self, "spawn." + increment);
            if (npcToKill != null)
            {
                destroyObject(npcToKill);
                removeObjVar(self, "spawn." + increment);
            }
            increment = increment + 1;
        }
        removeObjVar(self, "current");
        removeObjVar(self, "spawn");
        return;
    }
    public String npcToSpawn() throws InterruptedException
    {
        int choice = rand(1, 6);
        String spawn = "commoner";
        switch (choice)
        {
            case 1:
            spawn = "cll8_binary_load_lifter";
            break;
            case 2:
            spawn = "eg6_power_droid";
            break;
            case 3:
            spawn = "r2";
            break;
            case 4:
            spawn = "r3";
            break;
            case 5:
            spawn = "r4";
            break;
            case 6:
            spawn = "r5";
            break;
        }
        return spawn;
    }
}
