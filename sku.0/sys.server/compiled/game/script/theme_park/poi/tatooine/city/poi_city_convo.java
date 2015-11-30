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

public class poi_city_convo extends script.base_script
{
    public poi_city_convo()
    {
    }
    public static final String npcTable = "datatables/poi/city/convo_npc.iff";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        spawnGuyOne(self);
        spawnGuyTwo(self);
        messageTo(self, "handleChatting", null, 10, false);
        messageTo(self, "checkForScripts", null, 10, true);
        return SCRIPT_CONTINUE;
    }
    public int handleChatting(obj_id self, dictionary params) throws InterruptedException
    {
        messageTo(self, "handleChatting", null, 600, false);
        obj_id guy1 = getObjIdObjVar(self, "guy1");
        obj_id guy2 = getObjIdObjVar(self, "guy2");
        if (!exists(guy1) || !exists(guy2))
        {
            return SCRIPT_CONTINUE;
        }
        faceTo(guy1, guy2);
        faceTo(guy2, guy1);
        setAnimationMood(guy1, "conversation");
        setAnimationMood(guy2, "conversation");
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
    public void spawnGuyOne(obj_id baseObject) throws InterruptedException
    {
        obj_id guy1 = create.themeParkObject(getRandomGuy(), 1, 0, "handleGuyOneKilled", 0);
        setObjVar(baseObject, "guy1", guy1);
    }
    public void spawnGuyTwo(obj_id baseObject) throws InterruptedException
    {
        obj_id guy2 = create.themeParkObject(getRandomGuy(), 1, 1, "handleGuyTwoKilled", 0);
        setObjVar(baseObject, "guy2", guy2);
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
    public int checkForScripts(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasScript(self, "theme_park.poi.launch"))
        {
            detachScript(self, "theme_park.poi.launch");
        }
        return SCRIPT_CONTINUE;
    }
}
