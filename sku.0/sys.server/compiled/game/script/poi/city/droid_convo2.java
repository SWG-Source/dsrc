package script.poi.city;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.create;

public class droid_convo2 extends script.base_script
{
    public droid_convo2()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        spawnDroid(self);
        spawnGuy(self);
        messageTo(self, "handleChatting", null, 10, false);
        messageTo(self, "checkForScripts", null, 10, true);
        return SCRIPT_CONTINUE;
    }
    public int handleChatting(obj_id self, dictionary params) throws InterruptedException
    {
        messageTo(self, "handleChatting", null, 600, false);
        obj_id droid = getObjIdObjVar(self, "droidSpawn");
        obj_id guy1 = getObjIdObjVar(self, "guy1");
        if (!exists(droid) || !exists(guy1))
        {
            return SCRIPT_CONTINUE;
        }
        faceTo(guy1, droid);
        faceTo(droid, guy1);
        setAnimationMood(guy1, "conversation");
        return SCRIPT_CONTINUE;
    }
    public void spawnGuy(obj_id baseObject) throws InterruptedException
    {
        obj_id guy1 = create.themeParkObject(getRandomGuy(), 1f, 0f, "handleDeadGuy", 0f);
        if (isIdValid(guy1))
        {
            setObjVar(baseObject, "guy1", guy1);
        }
    }
    public int handleDeadGuy(obj_id self, dictionary params) throws InterruptedException
    {
        spawnGuy(self);
        return SCRIPT_CONTINUE;
    }
    public void spawnDroid(obj_id baseObject) throws InterruptedException
    {
        String[] droids = new String[4];
        droids[0] = "r2";
        droids[1] = "r3";
        droids[2] = "r4";
        droids[3] = "r5";
        obj_id droid = create.themeParkObject(droids[rand(0, 3)], 2f, 1f, "handleDeadDroid", 0f);
        if (isIdValid(droid))
        {
            setObjVar(baseObject, "droidSpawn", droid);
        }
    }
    public int handleDeadDroid(obj_id self, dictionary params) throws InterruptedException
    {
        spawnDroid(self);
        return SCRIPT_CONTINUE;
    }
    public String getRandomGuy() throws InterruptedException
    {
        int whichGuy = rand(1, 5);
        String guy;
        if (whichGuy == 1)
        {
            guy = "commoner";
        }
        else 
        {
            guy = "noble";
        }
        return guy;
    }
    public int checkForScripts(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasScript(self, "theme_park.poi.launch"))
        {
            detachScript(self, "theme_park.poi.launch");
        }
        return SCRIPT_CONTINUE;
    }
}
