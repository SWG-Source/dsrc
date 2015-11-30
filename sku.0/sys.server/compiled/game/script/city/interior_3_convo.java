package script.city;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.create;

public class interior_3_convo extends script.city.interior_convo_base
{
    public interior_3_convo()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        spawnGuyOne(self);
        spawnGuyTwo(self);
        spawnGuyThree(self);
        messageTo(self, "checkForScripts", null, 5, false);
        messageTo(self, "handleChatting", null, 10, false);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        spawnGuyOne(self);
        spawnGuyTwo(self);
        spawnGuyThree(self);
        messageTo(self, "checkForScripts", null, 5, false);
        messageTo(self, "handleChatting", null, 10, false);
        return SCRIPT_CONTINUE;
    }
    public int handleChatting(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id guy1 = getObjIdObjVar(self, "guy1");
        obj_id guy2 = getObjIdObjVar(self, "guy2");
        obj_id guy3 = getObjIdObjVar(self, "guy3");
        if (!exists(guy1) || !exists(guy2) || !exists(guy3))
        {
            return SCRIPT_CONTINUE;
        }
        faceTo(guy1, guy2);
        faceTo(guy2, guy1);
        faceTo(guy3, guy1);
        setAnimationMood(guy1, "conversation");
        setAnimationMood(guy2, "conversation");
        setAnimationMood(guy3, "conversation");
        return SCRIPT_CONTINUE;
    }
    public int handleGuyOneKilled(obj_id self, dictionary params) throws InterruptedException
    {
        spawnGuyOne(self);
        return SCRIPT_CONTINUE;
    }
    public int handleGuyTwoKilled(obj_id self, dictionary params) throws InterruptedException
    {
        spawnGuyTwo(self);
        return SCRIPT_CONTINUE;
    }
    public int handleGuyThreeKilled(obj_id self, dictionary params) throws InterruptedException
    {
        spawnGuyThree(self);
        return SCRIPT_CONTINUE;
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
