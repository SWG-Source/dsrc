package script.event.aprilfools;

import script.dictionary;
import script.library.ai_lib;
import script.library.chat;
import script.library.utils;
import script.location;
import script.obj_id;
import script.string_id;

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
        String myMood = params.getString("myMood");
        if (myMood.equals("") || myMood.equals("null"))
        {
            myMood = "none";
        }
        chat.chat(self, chat.CHAT_SAY, myMood, new string_id("aprilfools", params.getString("myLine")));
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
        int randomEntry = rand(0, dataTableGetNumRows(RAND_LOCATION) - 1);
        String cellDestinationStr = dataTableGetString(RAND_LOCATION, randomEntry, "CELL");
        obj_id cellDestination = getCellId(getTopMostContainer(self), cellDestinationStr);
        if (cellDestinationStr.equals("") || cellDestinationStr.equals("null") || !isIdValid(cellDestination))
        {
            return SCRIPT_CONTINUE;
        }
        location there = getLocation(self);
        there.x = dataTableGetFloat(RAND_LOCATION, randomEntry, "X") + rand(-2.0f, 2.0f);
        there.z = dataTableGetFloat(RAND_LOCATION, randomEntry, "Z") + rand(-2.0f, 2.0f);
        there.cell = cellDestination;
        ai_lib.aiPathTo(self, there);
        setMovementRun(self);
        return SCRIPT_CONTINUE;
    }
    public int moveToMainActorPosition(obj_id self, dictionary params) throws InterruptedException
    {
        String actorRole = getStringObjVar(self, "aprilfools.actor_role");
        int position = params.getInt("position");
        if (position < 0 || position > 5)
        {
            // says position can only be between 0 and 5 (see else condition).
            return SCRIPT_CONTINUE;
        }
        else if (actorRole.equals("ewok2") || actorRole.equals("ewok3") || actorRole.equals("ewok4"))
        {
            // This is odd, but says we only want to deal with ewok1 or foil1.
            return SCRIPT_CONTINUE;
        }
        String myColumnForX = "";
        String myColumnForZ = "";
        switch (actorRole) {
            case "ewok1":
                myColumnForX = "EWOK_1X";
                myColumnForZ = "EWOK_1Z";
                break;
            case "foil1":
                myColumnForX = "FOIL_1X";
                myColumnForZ = "FOIL_1Z";
                break;
            // Bug?: I don't think we can even reach the following cases based on the conditional above.
            case "ewok2":
                myColumnForX = "EWOK_2X";
                myColumnForZ = "EWOK_2Z";
                break;
            case "ewok3":
                myColumnForX = "EWOK_3X";
                myColumnForZ = "EWOK_3Z";
                break;
            case "ewok4":
                myColumnForX = "EWOK_4X";
                myColumnForZ = "EWOK_4Z";
                break;
        }
        if (myColumnForX.equals("") || myColumnForZ.equals(""))
        {
            return SCRIPT_CONTINUE;
        }
        location there = getLocation(self);
        there.x = dataTableGetFloat(POSITIONS, position, myColumnForX);
        there.z = dataTableGetFloat(POSITIONS, position, myColumnForZ);
        obj_id cantinaCell = getCellId(getTopMostContainer(self), "cantina");
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
        obj_id destinationCell = getCellId(getTopMostContainer(self), "foyer1");
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
        if (destContainer == getCellId(getTopMostContainer(self), "foyer1") && getIntObjVar(self, "aprilfools.run_away") == 1)
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
            int tableLength = dataTableGetNumRows(DATATABLE);
            if (tableLength < 1)
            {
                return SCRIPT_CONTINUE;
            }
            for (int i = 0; i < tableLength; i++)
            {
                if (dataTableGetInt(DATATABLE, i, "MIN_ROLL") > rand(1, 100))
                {
                    createObject(dataTableGetString(DATATABLE, i, "LOOT"), utils.getInventoryContainer(self), "");
                    return SCRIPT_CONTINUE;
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
