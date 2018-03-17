package script.npe;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class npe_drunk_sequence extends script.base_script
{
    public npe_drunk_sequence()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setInvulnerable(self, true);
        messageTo(self, "npeSetName", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int OnPreloadComplete(obj_id self) throws InterruptedException
    {
        boolean setting = utils.checkConfigFlag("ScriptFlags", "npeSequencersActive");
        if (setting == true)
        {
            String myName = utils.getStringObjVar(self, "strSequenceIdentifier");
            if (myName.equals("drunk"))
            {
                messageTo(self, "doEvents", null, 60, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int npeSetName(obj_id self, dictionary params) throws InterruptedException
    {
        String myName = utils.getStringObjVar(self, "strSequenceIdentifier");
        if (myName.equals("drunk"))
        {
            setName(self, "Pacus");
        }
        else 
        {
            setName(self, "Irving");
            obj_id cup = createObject("object/tangible/item/con_drinking_glass_01.iff", self, "hold_l");
            setAnimationMood(self, "npc_standing_drinking");
        }
        return SCRIPT_CONTINUE;
    }
    public int getglass(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id inv = getObjectInSlot(self, "inventory");
        obj_id cup = createObject("object/tangible/item/con_drinking_glass_01.iff", inv, "");
        equip(cup, self);
        return SCRIPT_CONTINUE;
    }
    public int removeglass(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id myInv = utils.getInventoryContainer(self);
        obj_id glass = getObjectInSlot(self, "hold_l");
        destroyObject(glass);
        return SCRIPT_CONTINUE;
    }
    public int npeaddconvo(obj_id self, dictionary params) throws InterruptedException
    {
        attachScript(self, "conversation.npe_drunk");
        return SCRIPT_CONTINUE;
    }
    public int nperemoveconvo(obj_id self, dictionary params) throws InterruptedException
    {
        detachScript(self, "conversation.npe_drunk");
        clearCondition(self, CONDITION_CONVERSABLE);
        return SCRIPT_CONTINUE;
    }
}
