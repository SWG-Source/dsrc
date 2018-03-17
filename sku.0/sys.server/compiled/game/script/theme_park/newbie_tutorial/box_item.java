package script.theme_park.newbie_tutorial;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class box_item extends script.theme_park.newbie_tutorial.tutorial_base
{
    public box_item()
    {
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        setOwner(self, player);
        if (!hasObjVar(player, "newbie"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id playerInv = getObjectInSlot(player, "inventory");
        if (playerInv != getContainedBy(self))
        {
            if (hasObjVar(player, "newbie.gotItem"))
            {
                return SCRIPT_CONTINUE;
            }
            setObjVar(player, "newbie.gotItem", true);
        }
        else 
        {
            if (hasObjVar(player, "newbie.usedItem"))
            {
                return SCRIPT_CONTINUE;
            }
            setObjVar(player, "newbie.usedItem", true);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTransferred(obj_id self, obj_id sourceContainer, obj_id destContainer, obj_id player) throws InterruptedException
    {
        if (!hasObjVar(player, "newbie"))
        {
            return SCRIPT_CONTINUE;
        }
        player = getPlayer(self);
        obj_id playerInv = getObjectInSlot(player, "inventory");
        if (destContainer == playerInv)
        {
            if (hasObjVar(player, "newbie.grabbedItem"))
            {
                return SCRIPT_CONTINUE;
            }
            setObjVar(player, "newbie.grabbedItem", true);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToBeTransferred(obj_id self, obj_id destContainer, obj_id player) throws InterruptedException
    {
        if (!hasObjVar(player, "newbie"))
        {
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id player = getPlayer(self);
        if (player == null)
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(player, "newbie.usedItem"))
        {
            setObjVar(player, "newbie.usedItem", true);
            messageTo(player, "handleToolbarPrompt", null, SHORT_DELAY, false);
        }
        return SCRIPT_CONTINUE;
    }
}
