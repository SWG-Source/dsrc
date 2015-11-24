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

public class droid_convo extends script.base_script
{
    public droid_convo()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        obj_id commoner = spawnGuyOne(self);
        obj_id commoner2 = spawnGuyTwo(self);
        spawnDroid();
        faceTo(commoner, commoner2);
        messageTo(self, "handleChatting", null, 10.0f, false);
        messageTo(self, "checkForScripts", null, 10, true);
        return SCRIPT_CONTINUE;
    }
    public obj_id spawnGuyOne(obj_id baseObject) throws InterruptedException
    {
        location here = getLocation(baseObject);
        here.x = here.x + 1;
        obj_id commoner1 = create.object("commoner", here);
        setObjVar(baseObject, "commoner1", commoner1);
        ai_lib.setDefaultCalmBehavior(commoner1, ai_lib.BEHAVIOR_SENTINEL);
        return commoner1;
    }
    public obj_id spawnGuyTwo(obj_id baseObject) throws InterruptedException
    {
        location here = getLocation(baseObject);
        here.x = here.x - 1;
        obj_id commoner2 = create.object("commoner", here);
        setObjVar(baseObject, "commoner2", commoner2);
        ai_lib.setDefaultCalmBehavior(commoner2, ai_lib.BEHAVIOR_SENTINEL);
        return commoner2;
    }
    public void spawnDroid() throws InterruptedException
    {
        location here = getLocation(getSelf());
        here.z = here.z + 1;
        String droidToSpawn = getRandomDroid();
        obj_id droid = create.object(droidToSpawn, here);
        ai_lib.setDefaultCalmBehavior(droid, ai_lib.BEHAVIOR_SENTINEL);
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
        spawnDroid();
        return SCRIPT_CONTINUE;
    }
    public int handleChatting(obj_id self, dictionary params) throws InterruptedException
    {
        messageTo(self, "handleChatting", null, 600, false);
        obj_id commoner1 = getObjIdObjVar(self, "commoner1");
        obj_id commoner2 = getObjIdObjVar(self, "commoner2");
        if (!exists(commoner1) || !exists(commoner2))
        {
            return SCRIPT_CONTINUE;
        }
        faceTo(commoner1, commoner2);
        faceTo(commoner2, commoner1);
        setAnimationMood(commoner1, "conversation");
        setAnimationMood(commoner2, "conversation");
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
