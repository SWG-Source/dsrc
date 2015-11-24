package script.event.aprilfools;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.chat;
import script.library.ai_lib;

public class cantina_actor extends script.base_script
{
    public cantina_actor()
    {
    }
    public static final String RAND_LOCATION = "datatables/event/aprilfools/random_location.iff";
    public static final String POSITIONS = "datatables/event/aprilfools/cantina_positions.iff";
    public static final String DATATABLE = "datatables/event/invasion/ewok_bonus_loot.iff";
    public int faceNorth(obj_id self, dictionary params) throws InterruptedException
    {
        setYaw(self, 90);
        return SCRIPT_CONTINUE;
    }
    public int faceSouth(obj_id self, dictionary params) throws InterruptedException
    {
        setYaw(self, -89);
        return SCRIPT_CONTINUE;
    }
    public int faceEast(obj_id self, dictionary params) throws InterruptedException
    {
        setYaw(self, 179);
        return SCRIPT_CONTINUE;
    }
    public int faceWest(obj_id self, dictionary params) throws InterruptedException
    {
        setYaw(self, 0);
        return SCRIPT_CONTINUE;
    }
    public int sayYourLine(obj_id self, dictionary params) throws InterruptedException
    {
        String myLineStr = params.getString("myLine");
        String myMood = params.getString("myMood");
        string_id myLine = new string_id("aprilfools", myLineStr);
        if (myMood.equals("") || myMood.equals("null"))
        {
            myMood = "none";
        }
        chat.chat(self, chat.CHAT_SAY, myMood, myLine);
        return SCRIPT_CONTINUE;
    }
    public int becomeVulnerable(obj_id self, dictionary params) throws InterruptedException
    {
        setInvulnerable(self, false);
        return SCRIPT_CONTINUE;
    }
    public int becomeInvulnerable(obj_id self, dictionary params) throws InterruptedException
    {
        setInvulnerable(self, true);
        return SCRIPT_CONTINUE;
    }
    public int moveToRandomLocation(obj_id self, dictionary params) throws InterruptedException
    {
        int numEntries = dataTableGetNumRows(RAND_LOCATION);
        int randomEntry = rand(0, numEntries - 1);
        location there = getLocation(self);
        float xDestination = dataTableGetFloat(RAND_LOCATION, randomEntry, "X");
        float zDestination = dataTableGetFloat(RAND_LOCATION, randomEntry, "Z");
        String cellDestinationStr = dataTableGetString(RAND_LOCATION, randomEntry, "CELL");
        float variableX = rand(-2.0f, 2.0f);
        float variableZ = rand(-2.0f, 2.0f);
        there.x = xDestination + variableX;
        there.z = zDestination + variableZ;
        obj_id building = getTopMostContainer(self);
        obj_id cellDestination = getCellId(building, cellDestinationStr);
        if (cellDestinationStr.equals("") || cellDestinationStr.equals("null") || !isIdValid(cellDestination))
        {
            return SCRIPT_CONTINUE;
        }
        there.cell = cellDestination;
        ai_lib.aiPathTo(self, there);
        setMovementRun(self);
        return SCRIPT_CONTINUE;
    }
    public int moveToMainActorPosition(obj_id self, dictionary params) throws InterruptedException
    {
        int position = params.getInt("position");
        String actorRole = getStringObjVar(self, "aprilfools.actor_role");
        String myColumnForX = "";
        String myColumnForZ = "";
        if (actorRole.equals("ewok1"))
        {
            myColumnForX = "EWOK_1X";
            myColumnForZ = "EWOK_1Z";
        }
        if (actorRole.equals("foil1"))
        {
            myColumnForX = "FOIL_1X";
            myColumnForZ = "FOIL_1Z";
        }
        if (actorRole.equals("ewok2"))
        {
            myColumnForX = "EWOK_2X";
            myColumnForZ = "EWOK_2Z";
        }
        if (actorRole.equals("ewok3"))
        {
            myColumnForX = "EWOK_3X";
            myColumnForZ = "EWOK_3Z";
        }
        if (actorRole.equals("ewok4"))
        {
            myColumnForX = "EWOK_4X";
            myColumnForZ = "EWOK_4Z";
        }
        if (myColumnForX.equals("") || myColumnForX.equals("null") || myColumnForZ.equals("") || myColumnForZ.equals("null"))
        {
            return SCRIPT_CONTINUE;
        }
        if (position < 0 || position > 5)
        {
            return SCRIPT_CONTINUE;
        }
        if (position > 0 && (actorRole.equals("ewok2") || actorRole.equals("ewok3") || actorRole.equals("ewok4")))
        {
            return SCRIPT_CONTINUE;
        }
        location there = getLocation(self);
        there.x = dataTableGetFloat(POSITIONS, position, myColumnForX);
        there.z = dataTableGetFloat(POSITIONS, position, myColumnForZ);
        obj_id building = getTopMostContainer(self);
        obj_id cantinaCell = getCellId(building, "cantina");
        if (!isIdValid(cantinaCell))
        {
            return SCRIPT_CONTINUE;
        }
        there.cell = cantinaCell;
        ai_lib.aiPathTo(self, there);
        setMovementRun(self);
        return SCRIPT_CONTINUE;
    }
    public int goDie(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int runAwayAndPoof(obj_id self, dictionary params) throws InterruptedException
    {
        setInvulnerable(self, false);
        setObjVar(self, "aprilfools.run_away", 1);
        location there = getLocation(self);
        obj_id building = getTopMostContainer(self);
        obj_id destinationCell = getCellId(building, "foyer1");
        if (!isIdValid(destinationCell))
        {
            return SCRIPT_CONTINUE;
        }
        there.x = 43.0f;
        there.z = 0.0f;
        there.cell = destinationCell;
        ai_lib.aiPathTo(self, there);
        setMovementRun(self);
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToBeTransferred(obj_id self, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        if (!hasObjVar(self, "aprilfools.run_away"))
        {
            return SCRIPT_CONTINUE;
        }
        int runAway = getIntObjVar(self, "aprilfools.run_away");
        obj_id building = getTopMostContainer(self);
        obj_id destinationCell = getCellId(building, "foyer1");
        if (destContainer == destinationCell && runAway == 1)
        {
            destroyObject(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        obj_id cantina = getObjIdObjVar(self, "aprilfools.cantina");
        setObjVar(cantina, "aprilfools.robbery_cycle", 999);
        removeObjVar(cantina, "aprilfools.ewok1");
        return SCRIPT_CONTINUE;
    }
    public int aiCorpsePrepared(obj_id self, dictionary params) throws InterruptedException
    {
        int chance = rand(1, 200);
        if (hasObjVar(self, "aprilfools.ewok1"))
        {
            chance = 200;
        }
        if (chance > 150)
        {
            int roll = rand(1, 100);
            int tableLength = dataTableGetNumRows(DATATABLE);
            if (tableLength < 1)
            {
                return SCRIPT_CONTINUE;
            }
            for (int i = 0; i < tableLength; i++)
            {
                int minRoll = dataTableGetInt(DATATABLE, i, "MIN_ROLL");
                if (minRoll > roll)
                {
                    String loot = dataTableGetString(DATATABLE, i, "LOOT");
                    obj_id inventory = utils.getInventoryContainer(self);
                    obj_id reward = createObject(loot, inventory, "");
                    return SCRIPT_CONTINUE;
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
