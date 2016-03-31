package script.city;

import script.dictionary;
import script.library.create;
import script.library.factions;
import script.library.locations;
import script.location;
import script.obj_id;

public class interior_convo_base extends script.base_script
{
    private final float[][] offsets = new float[][]{{1.1f, 0},{1.1f, 1.1f},{0f,1.1f},{0f,0f}};

    public interior_convo_base()
    {
    }
    public static final String NPC_CONVO_TABLE = "datatables/poi/city/convo_npc.iff";
    public void spawnGuy(obj_id baseObject, String name) throws InterruptedException
    {
        switch(name){
            case "guy1":
                spawnConvoNpc(baseObject, offsets[0][0], offsets[0][1], "handleGuyKilled", name);
                break;
            case "guy2":
                spawnConvoNpc(baseObject, offsets[1][0], offsets[1][1], "handleGuyKilled", name);
                break;
            case "guy3":
                spawnConvoNpc(baseObject, offsets[2][0], offsets[2][1], "handleGuyKilled", name);
                break;
            case "guy4":
                spawnConvoNpc(baseObject, offsets[3][0], offsets[3][1], "handleGuyKilled", name);
                break;
        }
    }
    public void spawnConvoNpc(obj_id baseObject, float xOffset, float zOffset, String deathMsg, String objvarName) throws InterruptedException
    {
        obj_id guy = create.themeParkObject(getRandomGuy(), xOffset, zOffset, deathMsg, 0);
        setObjVar(baseObject, objvarName, guy);
        setObjVar(baseObject, "name", objvarName);
        String myFaction = factions.getFaction(guy);
        if ((myFaction == null) || (myFaction.equals("")))
        {
            setCreatureStatic(guy, true);
        }
        else 
        {
            if ((!myFaction.equals("Imperial")) && (!myFaction.equals("Rebel")))
            {
                setCreatureStatic(guy, true);
            }
            else 
            {
                setInvulnerable(guy, false);
            }
        }
    }
    public String getRandomGuy() throws InterruptedException
    {
        String[] npcList = dataTableGetStringColumnNoDefaults(NPC_CONVO_TABLE, getInteriorConvoArea());
        return npcList[rand(0, npcList.length - 1)];
    }
    public String getInteriorConvoArea() throws InterruptedException
    {
        String interiorConvoAreaName;
        location here = getLocation(getSelf());
        String planet = here.area;
        String city = locations.getCityName(here);
        if (city == null)
        {
            city = locations.getGuardSpawnerRegionName(here);
        }
        if (city == null)
        {
            interiorConvoAreaName = planet;
        }
        else 
        {
            if (dataTableHasColumn(NPC_CONVO_TABLE, city))
            {
                interiorConvoAreaName = city;
            }
            else 
            {
                interiorConvoAreaName = planet;
            }
        }
        setObjVar(getSelf(), "interiorConvoArea", interiorConvoAreaName);
        return interiorConvoAreaName;
    }
    public int handleGuyKilled(obj_id self, dictionary params) throws InterruptedException
    {
        spawnGuy(self, getStringObjVar(self, "name"));
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
