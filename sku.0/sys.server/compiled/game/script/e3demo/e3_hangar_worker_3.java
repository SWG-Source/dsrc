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

public class e3_hangar_worker_3 extends script.base_script
{
    public e3_hangar_worker_3()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "moveToLocationOne", null, 5, false);
        setObjVar(self, "intDestination", 1);
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
        destLoc.x = -42.5f;
        destLoc.y = 206.5f;
        destLoc.z = 306.9f;
        setObjVar(self, "intIndex", 1);
        pathTo(self, destLoc);
    }
    public void pathToLocationTwo(obj_id self) throws InterruptedException
    {
        location destLoc = new location(getLocation(self));
        destLoc.x = -15.4f;
        destLoc.y = 206.5f;
        destLoc.z = 297.3f;
        setObjVar(self, "intIndex", 2);
        pathTo(self, destLoc);
    }
    public void pathToLocationThree(obj_id self) throws InterruptedException
    {
        location destLoc = new location(getLocation(self));
        destLoc.x = -15f;
        destLoc.y = 206.5f;
        destLoc.z = 288.1f;
        setObjVar(self, "intIndex", 3);
        pathTo(self, destLoc);
    }
    public void pathToLocationFour(obj_id self) throws InterruptedException
    {
        location destLoc = new location(getLocation(self));
        destLoc.x = -9.8f;
        destLoc.y = 206.5f;
        destLoc.z = 294.5f;
        setObjVar(self, "intIndex", 4);
        pathTo(self, destLoc);
    }
    public int OnMovePathComplete(obj_id self) throws InterruptedException
    {
        int intIndex = getIntObjVar(self, "intIndex");
        int intDestination = getIntObjVar(self, "intDestination");
        if (intDestination == 4)
        {
            if (intIndex == 4)
            {
                setObjVar(self, "intDestination", 1);
                dictionary dctParams = new dictionary();
                String strObjVar = "officer1bLookAt";
                dctParams.put("strObjVar", strObjVar);
                messageTo(self, "doFaceTo", dctParams, 1.5f, false);
            }
            else 
            {
                if (intIndex == 1)
                {
                    pathToLocationTwo(self);
                }
                if (intIndex == 2)
                {
                    pathToLocationThree(self);
                }
                if (intIndex == 3)
                {
                    pathToLocationFour(self);
                }
            }
        }
        else 
        {
            if (intIndex == 1)
            {
                setObjVar(self, "intDestination", 4);
                dictionary dctParams = new dictionary();
                String strObjVar = "officer1LookAt";
                dctParams.put("strObjVar", strObjVar);
                messageTo(self, "doFaceTo", dctParams, 1.5f, false);
            }
            else 
            {
                if (intIndex == 2)
                {
                    pathToLocationOne(self);
                }
                if (intIndex == 3)
                {
                    pathToLocationTwo(self);
                }
                if (intIndex == 4)
                {
                    pathToLocationThree(self);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int doFaceTo(obj_id self, dictionary params) throws InterruptedException
    {
        String strObjVar = params.getString("strObjVar");
        obj_id[] objTestObjects = getAllObjectsWithObjVar(getLocation(self), 2000, strObjVar);
        faceTo(self, getLocation(objTestObjects[0]));
        messageTo(self, "doTwiddle", null, 1.5f, false);
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
    public int doLocationFour(obj_id self, dictionary params) throws InterruptedException
    {
        pathToLocationFour(self);
        return SCRIPT_CONTINUE;
    }
    public int doTwiddle(obj_id self, dictionary params) throws InterruptedException
    {
        float fltWait = rand(2, 5);
        doAnimationAction(self, "manipulate_medium");
        int intDestination = getIntObjVar(self, "intDestination");
        if (intDestination == 1)
        {
            messageTo(self, "doLocationThree", null, fltWait, false);
        }
        else 
        {
            messageTo(self, "doLocationTwo", null, fltWait, false);
        }
        return SCRIPT_CONTINUE;
    }
}
