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

public class e3_bridge_worker_2 extends script.base_script
{
    public e3_bridge_worker_2()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "moveToLocationOne", null, 5, false);
        setObjVar(self, "intDestination", 1);
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
        destLoc.x = -5.9f;
        destLoc.y = 453f;
        destLoc.z = 326.4f;
        setObjVar(self, "intIndex", 1);
        pathTo(self, destLoc);
    }
    public void pathToLocationTwo(obj_id self) throws InterruptedException
    {
        location destLoc = new location(getLocation(self));
        destLoc.x = -6.8f;
        destLoc.y = 453f;
        destLoc.z = 331.3f;
        setObjVar(self, "intIndex", 2);
        pathTo(self, destLoc);
    }
    public void pathToLocationThree(obj_id self) throws InterruptedException
    {
        location destLoc = new location(getLocation(self));
        destLoc.x = -4.6f;
        destLoc.y = 206.5f;
        destLoc.z = 307.4f;
        setObjVar(self, "intIndex", 3);
        pathTo(self, destLoc);
    }
    public void pathToLocationFour(obj_id self) throws InterruptedException
    {
        location destLoc = new location(getLocation(self));
        destLoc.x = -0.4f;
        destLoc.y = 206.5f;
        destLoc.z = 305.8f;
        setObjVar(self, "intIndex", 4);
        pathTo(self, destLoc);
    }
    public int OnMovePathComplete(obj_id self) throws InterruptedException
    {
        messageTo(self, "doFaceTo", null, 2.5f, false);
        return SCRIPT_CONTINUE;
    }
    public int doFaceTo(obj_id self, dictionary params) throws InterruptedException
    {
        int intIndex = getIntObjVar(self, "intIndex");
        location locTest = getLocation(self);
        if (intIndex == 1)
        {
            locTest.x = -6.9f;
            locTest.y = 453f;
            locTest.z = 325.5f;
        }
        else 
        {
            locTest.x = -7.7f;
            locTest.y = 453f;
            locTest.z = 331f;
        }
        faceTo(self, locTest);
        messageTo(self, "doTwiddle", null, 2.5f, false);
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
        float fltWait = rand(3, 8);
        doAnimationAction(self, "manipulate_medium");
        int intIndex = getIntObjVar(self, "intIndex");
        if (intIndex == 1)
        {
            messageTo(self, "doLocationTwo", null, fltWait, false);
        }
        else 
        {
            messageTo(self, "doLocationOne", null, fltWait, false);
        }
        return SCRIPT_CONTINUE;
    }
}
