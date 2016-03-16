package script.e3demo;

import script.dictionary;
import script.library.factions;
import script.library.utils;
import script.location;
import script.obj_id;

public class e3_bridge_vader extends script.base_script
{
    public e3_bridge_vader()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setAnimationMood(self, "npc_imperial");
        factions.setFaction(self, "Imperial");
        attachScript(self, "e3demo.e3_faceto");
        setObjVar(self, "intDestination", 1);
        messageTo(self, "moveToLocationTwo", null, 0, false);
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        if (volumeName.equals("playerEnter"))
        {
            if (isPlayer(breacher))
            {
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int moveToLocationTwo(obj_id self, dictionary params) throws InterruptedException
    {
        pathToLocationTwo(self);
        return SCRIPT_CONTINUE;
    }
    public int moveToLocationOne(obj_id self, dictionary params) throws InterruptedException
    {
        pathToLocationOne(self);
        return SCRIPT_CONTINUE;
    }
    public int OnHearSpeech(obj_id self, obj_id objSpeaker, String strText) throws InterruptedException
    {
        if (strText.equals("reset"))
        {
            pathToLocationOne(self);
        }
        if (strText.equals("playerEnter"))
        {
            pathToLocationTwo(self);
        }
        return SCRIPT_CONTINUE;
    }
    public void pathToLocationOne(obj_id self) throws InterruptedException
    {
        location destLoc = new location(getLocation(self));
        destLoc.x = 8.6f;
        destLoc.y = 455.2f;
        destLoc.z = 330.7f;
        setObjVar(self, "intIndex", 1);
        pathTo(self, destLoc);
    }
    public void pathToLocationTwo(obj_id self) throws InterruptedException
    {
        location destLoc = new location(getLocation(self));
        destLoc.x = -.5f;
        destLoc.y = 455.2f;
        destLoc.z = 321.8f;
        setObjVar(self, "intIndex", 2);
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
            obj_id[] objObjects = getAllObjectsWithTemplate(getLocation(self), 2000, "object/mobile/boba_fett.iff");
            faceTo(self, getLocation(objObjects[0]));
        }
        else 
        {
            faceTo(self, utils.getObjIdScriptVar(self, "objPlayer"));
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
}
