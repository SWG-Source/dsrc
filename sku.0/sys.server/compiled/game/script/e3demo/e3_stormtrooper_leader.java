package script.e3demo;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.anims;
import script.library.space_utils;
import script.library.factions;

public class e3_stormtrooper_leader extends script.base_script
{
    public e3_stormtrooper_leader()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "moveToLocationOne", null, 5, false);
        setAnimationMood(self, "npc_imperial");
        factions.setFaction(self, "Imperial");
        return SCRIPT_CONTINUE;
    }
    public int moveToLocationOne(obj_id self, dictionary params) throws InterruptedException
    {
        pathToLocationOne(self);
        return SCRIPT_CONTINUE;
    }
    public void pathToLocationOne(obj_id self) throws InterruptedException
    {
        location destLoc = new location(getLocation(self));
        destLoc.x = 6.5f;
        destLoc.y = 206;
        destLoc.z = 324;
        setObjVar(self, "intIndex", 1);
        pathTo(self, destLoc);
    }
    public void pathToLocationTwo(obj_id self) throws InterruptedException
    {
        location destLoc = new location(getLocation(self));
        destLoc.x = 6.5f;
        destLoc.y = 206.5f;
        destLoc.z = 330.6f;
        pathTo(self, destLoc);
        setObjVar(self, "intIndex", 2);
    }
    public void pathToLocationThree(obj_id self) throws InterruptedException
    {
        location destLoc = new location(getLocation(self));
        destLoc.x = 6.5f;
        destLoc.y = 206.5f;
        destLoc.z = 335.6f;
        pathTo(self, destLoc);
        setObjVar(self, "intIndex", 3);
    }
    public int OnMovePathComplete(obj_id self) throws InterruptedException
    {
        messageTo(self, "doFaceTo", null, 1.5f, false);
        return SCRIPT_CONTINUE;
    }
    public int doFaceTo(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] objTestObjects = getAllObjectsWithObjVar(getLocation(self), 2000, "leaderFaceTo");
        faceTo(self, getLocation(objTestObjects[0]));
        messageTo(self, "doNextMove", null, 2.5f, false);
        return SCRIPT_CONTINUE;
    }
    public int doNextMove(obj_id self, dictionary params) throws InterruptedException
    {
        float fltWait = rand(1, 3);
        int intIndex = getIntObjVar(self, "intIndex");
        if (intIndex == 2)
        {
            messageTo(self, "doSalute", null, 1.5f, false);
            return SCRIPT_CONTINUE;
        }
        int intRoll = 1;
        if (intIndex == 1)
        {
            intRoll = 2;
        }
        if (intIndex == 3)
        {
            intRoll = 2;
        }
        if (intIndex == 2)
        {
            intRoll = rand(1, 2);
            if (intRoll == 2)
            {
                intRoll = 3;
            }
        }
        if (intRoll == 1)
        {
            messageTo(self, "doLocationOne", null, fltWait, false);
        }
        else if (intRoll == 2)
        {
            messageTo(self, "doLocationTwo", null, fltWait, false);
        }
        else 
        {
            messageTo(self, "doLocationThree", null, fltWait, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int doLocationOne(obj_id self, dictionary params) throws InterruptedException
    {
        pathToLocationOne(self);
        return SCRIPT_CONTINUE;
    }
    public int doLocationTwo(obj_id self, dictionary params) throws InterruptedException
    {
        pathToLocationTwo(self);
        return SCRIPT_CONTINUE;
    }
    public int doLocationThree(obj_id self, dictionary params) throws InterruptedException
    {
        pathToLocationThree(self);
        return SCRIPT_CONTINUE;
    }
    public int doSalute(obj_id self, dictionary params) throws InterruptedException
    {
        doAnimationAction(self, anims.PLAYER_SALUTE2);
        dictionary dctParams = new dictionary();
        dctParams.put("strEmote", anims.PLAYER_SALUTE2);
        obj_id[] objObjects = getAllObjectsWithScript(getLocation(self), 2000, "e3demo.e3_stormtrooper");
        for (int intI = 0; intI < objObjects.length; intI++)
        {
            space_utils.notifyObject(objObjects[intI], "doEmote", dctParams);
        }
        float fltWait = rand(1, 5);
        int intRoll = rand(1, 2);
        if (intRoll == 1)
        {
            messageTo(self, "doLocationOne", null, fltWait, false);
        }
        else 
        {
            messageTo(self, "doLocationThree", null, fltWait, false);
        }
        return SCRIPT_CONTINUE;
    }
}
