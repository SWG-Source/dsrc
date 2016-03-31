package script.city;

import script.dictionary;
import script.library.create;
import script.library.utils;
import script.obj_id;

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
        if (text.contains("max"))
        {
            setObjVar(self, "pop", utils.stringToInt(text.substring(text.indexOf(" ") + 1, text.length())));
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
        if (!hasObjVar(self, "current"))
        {
            setObjVar(self, "current", 1);
            doSpawn(self);
        }
        else 
        {
            if (getIntObjVar(self, "current") <= getIntObjVar(self, "pop"))
            {
                doSpawn(self);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void doSpawn(obj_id self) throws InterruptedException
    {
        int currentPop = getIntObjVar(self, "current");
        String spawn = npcToSpawn();
        obj_id npc = create.object(spawn, getLocation(self));

        attachScript(npc, "city.droid_wander");
        setInvulnerable(npc, true);

        setObjVar(self, "spawn." + currentPop, npc);
        setObjVar(npc, "spawnNum", "spawn." + currentPop);
        setObjVar(self, "current", ++currentPop);

        messageTo(self, "startSpawning", null, 10, true);
    }
    public void killAll(obj_id self) throws InterruptedException
    {
        int increment = 1;
        while (increment <= getIntObjVar(self, "pop"))
        {
            obj_id npcToKill = getObjIdObjVar(self, "spawn." + increment);
            if (npcToKill != null)
            {
                destroyObject(npcToKill);
                removeObjVar(self, "spawn." + increment);
            }
            increment++;
        }
        removeObjVar(self, "current");
        removeObjVar(self, "spawn");
    }
    public String npcToSpawn() throws InterruptedException
    {
        String spawn = "commoner";
        switch (rand(1, 6))
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
