package script.working;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.factions;
import script.library.regions;

public class ubertest extends script.base_script
{
    public ubertest()
    {
    }
    public int OnHearSpeech(obj_id self, obj_id objSpeaker, String strText) throws InterruptedException
    {
        if (strText.equals("uber"))
        {
            obj_id objLair = createObject("object/tangible/lair/uber_lair.iff", getLocation(self));
            setObjVar(objLair, "spawning.lairType", "imperial_uber_lair_01");
            messageTo(objLair, "handle_Spawn_Setup_Complete", null, 0, false);
        }
        if (strText.equals("cleanup"))
        {
            obj_id[] objStuff = getObjectsInRange(getLocation(self), 200);
            for (int intI = 0; intI < objStuff.length; intI++)
            {
                if (objStuff[intI] != self)
                {
                    destroyObject(objStuff[intI]);
                }
            }
        }
        if (strText.equals("imperial"))
        {
            factions.awardFactionStanding(self, "Imperial", 500);
            debugSpeakMsg(self, "granted");
        }
        return SCRIPT_CONTINUE;
    }
    public int testMessage(obj_id self, dictionary params) throws InterruptedException
    {
        debugSpeakMsg(self, "Got message");
        return SCRIPT_CONTINUE;
    }
    public int OnAssignMission(obj_id self, dictionary params) throws InterruptedException
    {
        debugSpeakMsg(self, "Asssigning missions");
        return SCRIPT_CONTINUE;
    }
}
