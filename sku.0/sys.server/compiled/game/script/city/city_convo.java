package script.city;

import script.dictionary;
import script.library.create;
import script.obj_id;

public class city_convo extends script.base_script
{
    private final float[][] offsets = new float[][]{{1.1f, 0},{1.1f, 1.1f},{0f,1.1f},{0f,0f}};

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
    public int handleChatting(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id guy1 = getObjIdObjVar(self, "guy1");
        obj_id guy2 = getObjIdObjVar(self, "guy2");
        if (!exists(guy1) || !exists(guy2))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id guy3 = getObjIdObjVar(self, "guy3");
        obj_id guy4 = getObjIdObjVar(self, "guy4");
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
    public int handleGuyKilled(obj_id self, dictionary params) throws InterruptedException
    {
        spawnGuy(self, getStringObjVar(self, "name"));
        return SCRIPT_CONTINUE;
    }
    public void spawnGuy(obj_id baseObject, String name) throws InterruptedException
    {
        obj_id guy = null;
        switch(name){
            case "guy1":
                guy = create.themeParkObject(getRandomGuy(), offsets[0][0], offsets[0][1], "handleGuyKilled", 0);
                break;
            case "guy2":
                guy = create.themeParkObject(getRandomGuy(), offsets[1][0], offsets[1][1], "handleGuyKilled", 0);
                break;
            case "guy3":
                guy = create.themeParkObject(getRandomGuy(), offsets[2][0], offsets[2][1], "handleGuyKilled", 0);
                break;
            case "guy4":
                guy = create.themeParkObject(getRandomGuy(), offsets[3][0], offsets[3][1], "handleGuyKilled", 0);
                break;
        }
        if(guy != null) {
            setObjVar(baseObject, name, guy);
            setCreatureStatic(guy, true);
        }
    }
    public String getRandomGuy() throws InterruptedException
    {
        String[] npcList = dataTableGetStringColumnNoDefaults(npcTable, getLocation(getSelf()).area);
        return npcList[rand(0, npcList.length - 1)];
    }
    public void beginSpawning(obj_id self) throws InterruptedException
    {
        if(!getBooleanObjVar(self, "spawned") || !getBooleanObjVar(self, "spawnInProgress")) {
            setObjVar(self, "spawnInProgress", true);
            int num = rand(1, 10);
            spawnGuy(self, "guy1");
            spawnGuy(self, "guy2");
            if (num >= 5) {
                spawnGuy(self, "guy3");
            }
            if (num >= 8) {
                spawnGuy(self, "guy4");
            }
            setObjVar(self, "spawnInProgress", false);
            setObjVar(self, "spawned", true);
        }
    }
    public int checkForScripts(obj_id self, dictionary params) throws InterruptedException
    {
        detachScript(self, "theme_park.poi.launch");
        return SCRIPT_CONTINUE;
    }
}
