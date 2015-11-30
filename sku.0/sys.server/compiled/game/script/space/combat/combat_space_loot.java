package script.space.combat;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.space_combat;
import script.library.space_utils;
import script.library.space_content;
import script.library.space_transition;
import script.library.utils;

public class combat_space_loot extends script.base_script
{
    public combat_space_loot()
    {
    }
    public static final string_id SID_NO_PERMISSION_OPEN = new string_id("space/space_loot", "no_permission_open");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setOwner(self, null);
        setObjVar(self, "intNoDump", 1);
        obj_id[] objContents = getContents(self);
        if (objContents == null)
        {
            destroyObject(self);
        }
        if (objContents.length == 0)
        {
            destroyObject(self);
        }
        messageTo(self, "destroySelf", null, 300, false);
        return SCRIPT_CONTINUE;
    }
    public int destroySelf(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToLoseItem(obj_id self, obj_id objDestination, obj_id objPlayer, obj_id objItem) throws InterruptedException
    {
        if (!isIdValid(objPlayer))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] objLooters = getObjIdArrayObjVar(self, "objLooters");
        int intIndex = utils.getElementPositionInArray(objLooters, space_transition.getContainingShip(objPlayer));
        LOG("space", "element is " + intIndex);
        if (intIndex < 0)
        {
            queueCommand(objPlayer, (822776054), null, "", COMMAND_PRIORITY_IMMEDIATE);
            sendSystemMessage(self, SID_NO_PERMISSION_OPEN);
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnLostItem(obj_id self, obj_id objDestination, obj_id objPlayer, obj_id objItem) throws InterruptedException
    {
        LOG("space", "I lost an item1");
        if (!isIdValid(objPlayer))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] objContents = getContents(self);
        if (objContents == null)
        {
            queueCommand(objPlayer, (822776054), null, "", COMMAND_PRIORITY_IMMEDIATE);
            destroyObject(self);
            return SCRIPT_CONTINUE;
        }
        LOG("space", "Lost item, contents length is " + objContents.length);
        if (objContents.length == 0)
        {
            queueCommand(objPlayer, (822776054), null, "", COMMAND_PRIORITY_IMMEDIATE);
            destroyObject(self);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int recheckContents(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] objContents = getContents(self);
        if (objContents == null)
        {
            destroyObject(self);
            return SCRIPT_CONTINUE;
        }
        LOG("space", "Lost item, contents 2 length is " + objContents.length);
        if (objContents.length == 0)
        {
            destroyObject(self);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
}
