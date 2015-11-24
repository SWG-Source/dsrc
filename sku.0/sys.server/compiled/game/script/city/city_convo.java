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

public class city_convo extends script.base_script
{
    public city_convo()
    {
    }
    public static final String npcTable = "datatables/poi/city/convo_npc.iff";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        beginSpawning(self);
        messageTo(self, "handleChatting", null, 10, false);
        messageTo(self, "checkForScripts", null, 10, false);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        beginSpawning(self);
        messageTo(self, "handleChatting", null, 10, false);
        messageTo(self, "checkForScripts", null, 10, false);
        return SCRIPT_CONTINUE;
    }
    public int handleChatting(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id guy1 = getObjIdObjVar(self, "guy1");
        obj_id guy2 = getObjIdObjVar(self, "guy2");
        obj_id guy3 = getObjIdObjVar(self, "guy3");
        obj_id guy4 = getObjIdObjVar(self, "guy4");
        if (!exists(guy1) || !exists(guy2))
        {
            return SCRIPT_CONTINUE;
        }
        faceTo(guy1, guy2);
        faceTo(guy2, guy1);
        faceTo(guy3, guy1);
        faceTo(guy4, guy2);
        setAnimationMood(guy1, "conversation");
        setAnimationMood(guy2, "conversation");
        setAnimationMood(guy3, "conversation");
        setAnimationMood(guy4, "conversation");
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
    public int handleGuyFourKilled(obj_id self, dictionary params) throws InterruptedException
    {
        spawnGuyFour(self);
        return SCRIPT_CONTINUE;
    }
    public void spawnGuyOne(obj_id baseObject) throws InterruptedException
    {
        obj_id guy1 = create.themeParkObject(getRandomGuy(), 1.3f, 0, "handleGuyOneKilled", 0);
        setObjVar(baseObject, "guy1", guy1);
        setCreatureStatic(guy1, true);
    }
    public void spawnGuyTwo(obj_id baseObject) throws InterruptedException
    {
        obj_id guy2 = create.themeParkObject(getRandomGuy(), 1.3f, 1.3f, "handleGuyTwoKilled", 0);
        setObjVar(baseObject, "guy2", guy2);
        setCreatureStatic(guy2, true);
    }
    public void spawnGuyThree(obj_id baseObject) throws InterruptedException
    {
        obj_id guy3 = create.themeParkObject(getRandomGuy(), 0, 1.3f, "handleGuyThreeKilled", 0);
        setObjVar(baseObject, "guy3", guy3);
        setCreatureStatic(guy3, true);
    }
    public void spawnGuyFour(obj_id baseObject) throws InterruptedException
    {
        obj_id guy4 = create.themeParkObject(getRandomGuy(), 0, 0, "handleGuyFourKilled", 0);
        setObjVar(baseObject, "guy4", guy4);
        setCreatureStatic(guy4, true);
    }
    public String getRandomGuy() throws InterruptedException
    {
        location here = getLocation(getSelf());
        String planet = here.area;
        String[] npcList = dataTableGetStringColumnNoDefaults(npcTable, planet);
        int npcNum = rand(0, npcList.length - 1);
        String npc = npcList[npcNum];
        return npc;
    }
    public void beginSpawning(obj_id self) throws InterruptedException
    {
        int num = rand(1, 10);
        if (num < 5)
        {
            spawnGuyOne(self);
            spawnGuyTwo(self);
            return;
        }
        if (num < 8)
        {
            spawnGuyOne(self);
            spawnGuyTwo(self);
            spawnGuyThree(self);
            return;
        }
        else 
        {
            spawnGuyOne(self);
            spawnGuyTwo(self);
            spawnGuyThree(self);
            spawnGuyFour(self);
            return;
        }
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
