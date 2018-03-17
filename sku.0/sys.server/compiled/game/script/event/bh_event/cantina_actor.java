package script.event.bh_event;

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
    public static final String STF_FILE = "event/bhcelebs";
    public int moveToMainActorPosition(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id building = getTopMostContainer(self);
        String planet = getLocation(building).area;
        location mainPosition = new location(18.0f, -0.89f, 0.0f, planet, getCellId(building, "cantina"));
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
            for (obj_id objPlayer : objPlayers) {
                sendSystemMessage(objPlayer, "Data: " + dataStuff + " and " + here, "");
            }
        }
    }
    public int OnArrivedAtLocation(obj_id self, String name) throws InterruptedException
    {
        if (name.equals("startBosskLine1"))
        {
            if (!hasScript(self, "conversation.event_bossk_bossk"))
            {
                chat.chat(self, chat.CHAT_SAY, chat.MOOD_CONDESCENDING, new string_id(STF_FILE, "bossk1_1"));
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
        obj_id destinationCell = getCellId(building, "foyer1");
        if (!isIdValid(destinationCell))
        {
            destroyObject(self);
            return SCRIPT_CONTINUE;
        }
        ai_lib.aiPathTo(self, new location(43.8f, .1f, 0.5f, getLocation(building).area, destinationCell));
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
        if (runAway == 0)
        {
            messageTo(self, "moveToMainActorPosition", null, 1, false);
            return SCRIPT_CONTINUE;
        }
        if (destContainer == getCellId(getTopMostContainer(self), "foyer1") && runAway == 1)
        {
            destroyObject(self);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        utils.setScriptVar(getTopMostContainer(self), "bhcelebs.beingDestroyed", true);
        return SCRIPT_CONTINUE;
    }
}
