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
import script.library.locations;
import script.library.factions;

public class interior_convo_base extends script.base_script
{
    public interior_convo_base()
    {
    }
    public static final String NPC_CONVO_TABLE = "datatables/poi/city/convo_npc.iff";
    public void spawnGuyOne(obj_id baseObject) throws InterruptedException
    {
        spawnConvoNpc(baseObject, 1.1f, 0, "handleGuyOneKilled", "guy1");
    }
    public void spawnGuyTwo(obj_id baseObject) throws InterruptedException
    {
        spawnConvoNpc(baseObject, 1.1f, 1.1f, "handleGuyTwoKilled", "guy2");
    }
    public void spawnGuyThree(obj_id baseObject) throws InterruptedException
    {
        spawnConvoNpc(baseObject, 0, 1.1f, "handleGuyThreeKilled", "guy3");
    }
    public void spawnGuyFour(obj_id baseObject) throws InterruptedException
    {
        spawnConvoNpc(baseObject, 0, 0, "handleGuyFourKilled", "guy4");
    }
    public void spawnConvoNpc(obj_id baseObject, float xOffset, float zOffset, String deathMsg, String objvarName) throws InterruptedException
    {
        obj_id guy = create.themeParkObject(getRandomGuy(), xOffset, zOffset, deathMsg, 0);
        setObjVar(baseObject, objvarName, guy);
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
        String area = getInteriorConvoArea();
        String[] npcList = dataTableGetStringColumnNoDefaults(NPC_CONVO_TABLE, area);
        int npcNum = rand(0, npcList.length - 1);
        String npc = npcList[npcNum];
        return npc;
    }
    public String getInteriorConvoArea() throws InterruptedException
    {
        String interiorConvoAreaName = "tatooine";
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
}
