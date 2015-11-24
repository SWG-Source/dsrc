package script.theme_park.poi.tatooine.city;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.create;

public class poi_city_jawa_convo extends script.base_script
{
    public poi_city_jawa_convo()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        obj_id jawa = spawnGuyOne(self);
        obj_id jawa2 = spawnGuyTwo(self);
        obj_id jawa3 = spawnGuy3(self);
        messageTo(self, "handleChatting", null, 10.0f, false);
        messageTo(self, "checkForScripts", null, 10, true);
        return SCRIPT_CONTINUE;
    }
    public obj_id spawnGuyOne(obj_id baseObject) throws InterruptedException
    {
        location here = getLocation(baseObject);
        here.x = here.x + 1;
        obj_id jawa1 = create.staticObject("jawa", here);
        setObjVar(baseObject, "jawa1", jawa1);
        return jawa1;
    }
    public obj_id spawnGuyTwo(obj_id baseObject) throws InterruptedException
    {
        location here = getLocation(baseObject);
        here.x = here.x - 1;
        obj_id jawa2 = create.staticObject("jawa", here);
        setObjVar(baseObject, "jawa2", jawa2);
        return jawa2;
    }
    public obj_id spawnGuy3(obj_id baseObject) throws InterruptedException
    {
        location here = getLocation(baseObject);
        here.z = here.z - 1;
        obj_id jawa3 = create.staticObject("jawa", here);
        setObjVar(baseObject, "jawa3", jawa3);
        return jawa3;
    }
    public int handleDeadGuyOne(obj_id self, dictionary params) throws InterruptedException
    {
        spawnGuyOne(self);
        return SCRIPT_CONTINUE;
    }
    public int handleDeadGuyTwo(obj_id self, dictionary params) throws InterruptedException
    {
        spawnGuyTwo(self);
        return SCRIPT_CONTINUE;
    }
    public int handleDeadDroid(obj_id self, dictionary params) throws InterruptedException
    {
        spawnGuy3(self);
        return SCRIPT_CONTINUE;
    }
    public int handleChatting(obj_id self, dictionary params) throws InterruptedException
    {
        messageTo(self, "handleChatting", null, 600, false);
        obj_id jawa1 = getObjIdObjVar(self, "jawa1");
        obj_id jawa2 = getObjIdObjVar(self, "jawa2");
        obj_id jawa3 = getObjIdObjVar(self, "jawa3");
        if (!exists(jawa1) || !exists(jawa2))
        {
            return SCRIPT_CONTINUE;
        }
        faceTo(jawa1, jawa2);
        faceTo(jawa2, jawa1);
        faceTo(jawa3, self);
        setAnimationMood(jawa1, "conversation");
        setAnimationMood(jawa2, "conversation");
        setAnimationMood(jawa3, "conversation");
        return SCRIPT_CONTINUE;
    }
    public String getRandomDroid() throws InterruptedException
    {
        int dnum = rand(2, 5);
        String droid = "r" + dnum;
        return droid;
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
