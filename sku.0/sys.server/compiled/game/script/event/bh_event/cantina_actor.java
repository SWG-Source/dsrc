package script.event.bh_event;

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
    public static final String STF_FILE = "event/bhcelebs";
    public int moveToMainActorPosition(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id building = getTopMostContainer(self);
        location cantinaLoc = getLocation(building);
        obj_id cantinaCell = getCellId(building, "cantina");
        String planet = cantinaLoc.area;
        location mainPosition = new location(18.0f, -0.89f, 0.0f, planet, cantinaCell);
        ai_lib.aiPathTo(self, mainPosition);
        if (params.containsKey("locationTargetName"))
        {
            String locationTargetName = params.getString("locationTargetName");
            if (locationTargetName != null && locationTargetName.length() > 0)
            {
                addLocationTarget(locationTargetName, mainPosition, 1);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void debugMessage(obj_id self, String dataStuff, location here) throws InterruptedException
    {
        obj_id[] objPlayers = getPlayerCreaturesInRange(self, 64.0f);
        if (objPlayers != null && objPlayers.length > 0)
        {
            for (int i = 0; i < objPlayers.length; i++)
            {
                sendSystemMessage(objPlayers[i], "Data: " + dataStuff + " and " + here, "");
            }
        }
    }
    public int OnArrivedAtLocation(obj_id self, String name) throws InterruptedException
    {
        if (name.equals("startBosskLine1"))
        {
            if (!hasScript(self, "conversation.event_bossk_bossk"))
            {
                string_id myLine = new string_id(STF_FILE, "bossk1_1");
                chat.chat(self, chat.CHAT_SAY, chat.MOOD_CONDESCENDING, myLine);
                attachScript(self, "conversation.event_bossk_bossk");
                setObjVar(self, "bhcelebs.run_away", 0);
                ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
                stop(self);
                setHomeLocation(self, getLocation(self));
                removeLocationTarget("startBosskLine1");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int goDie(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int runAwayAndPoof(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "bhcelebs.forcedCleanup"))
        {
            destroyObject(self);
            return SCRIPT_CONTINUE;
        }
        setObjVar(self, "bhcelebs.run_away", 1);
        clearCondition(self, CONDITION_CONVERSABLE);
        obj_id building = getTopMostContainer(self);
        location cantinaLoc = getLocation(building);
        String planet = cantinaLoc.area;
        obj_id destinationCell = getCellId(building, "foyer1");
        if (!isIdValid(destinationCell))
        {
            destroyObject(self);
            return SCRIPT_CONTINUE;
        }
        location there = new location(43.8f, .1f, 0.5f, planet, destinationCell);
        ai_lib.aiPathTo(self, there);
        messageTo(self, "goDie", null, 15, false);
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToBeTransferred(obj_id self, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        if (!hasObjVar(self, "bhcelebs.run_away"))
        {
            return SCRIPT_CONTINUE;
        }
        int runAway = getIntObjVar(self, "bhcelebs.run_away");
        obj_id building = getTopMostContainer(self);
        obj_id destinationCell = getCellId(building, "foyer1");
        if (runAway == 0)
        {
            messageTo(self, "moveToMainActorPosition", null, 1, false);
            return SCRIPT_CONTINUE;
        }
        if (destContainer == destinationCell && runAway == 1)
        {
            destroyObject(self);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id building = getTopMostContainer(self);
        utils.setScriptVar(building, "bhcelebs.beingDestroyed", true);
        return SCRIPT_CONTINUE;
    }
}
