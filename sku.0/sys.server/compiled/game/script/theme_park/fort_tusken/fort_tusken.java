package script.theme_park.fort_tusken;

import script.dictionary;
import script.library.create;
import script.location;
import script.obj_id;

public class fort_tusken extends script.base_script
{
    public fort_tusken()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        spawnEveryone(self);
        return SCRIPT_CONTINUE;
    }
    public void spawnEveryone(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "maxPop"))
        {
            setObjVar(self, "maxPop", 40);
        }
        int max = getIntObjVar(self, "maxPop");
        int intX = 1;
        while (intX < 40)
        {
            String room = pickRandomRoom();
            String tusken = pickRandomTusken();
            location loc = getGoodLocation(self, room);
            obj_id sandperson = create.object(tusken, loc);
            setObjVar(sandperson, "fort", self);
            create.addDestroyMessage(sandperson, "tuskenDead", 300.0f, self);
            intX = intX + 1;
        }
        return;
    }
    public String pickRandomRoom() throws InterruptedException
    {
        int randRoom = rand(1, 23);
        String roomToSpawnIn = "r" + randRoom;
        return roomToSpawnIn;
    }
    public String pickRandomTusken() throws InterruptedException
    {
        int randRaider = rand(1, 12);
        String tuskenToSpawn = "tusken_commoner";
        switch (randRaider)
        {
            case 1:
            tuskenToSpawn = "tusken_commoner";
            break;
            case 2:
            tuskenToSpawn = "tusken_raider";
            break;
            case 3:
            tuskenToSpawn = "tusken_captain";
            break;
            case 4:
            tuskenToSpawn = "tusken_commoner";
            break;
            case 5:
            tuskenToSpawn = "tusken_raider";
            break;
            case 6:
            tuskenToSpawn = "tusken_chief";
            break;
            case 7:
            tuskenToSpawn = "tusken_commoner";
            break;
            case 8:
            tuskenToSpawn = "tusken_raider";
            break;
            case 9:
            tuskenToSpawn = "tusken_sniper";
            break;
            case 10:
            tuskenToSpawn = "tusken_commoner";
            break;
            case 11:
            tuskenToSpawn = "tusken_raider";
            break;
            case 12:
            tuskenToSpawn = "tusken_warlord";
            break;
        }
        return tuskenToSpawn;
    }
    public int tuskenDead(obj_id self, dictionary params) throws InterruptedException
    {
        debugSpeakMsg(self, "Creating a replacement");
        String room = pickRandomRoom();
        String tusken = pickRandomTusken();
        location loc = getGoodLocation(self, room);
        obj_id sandperson = create.object(tusken, loc);
        setObjVar(sandperson, "fort", self);
        create.addDestroyMessage(sandperson, "tuskenDead", 300.0f, self);
        return SCRIPT_CONTINUE;
    }
}
