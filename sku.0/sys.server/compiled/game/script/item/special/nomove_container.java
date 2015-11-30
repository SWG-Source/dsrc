package script.item.special;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.player_structure;
import script.library.structure;

public class nomove_container extends script.item.special.nomove_base
{
    public nomove_container()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setObjVar(self, "noTrade", true);
        propogateNoTrade(self, true);
        return SCRIPT_CONTINUE;
    }
    public int OnLostItem(obj_id self, obj_id destContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (containsNoTradeItem(self))
        {
            return SCRIPT_CONTINUE;
        }
        removeObjVar(self, "noTrade");
        propogateNoTrade(self, false);
        detachScript(self, "item.special.nomove_container");
        return SCRIPT_CONTINUE;
    }
    public boolean containsNoTradeItem(obj_id container) throws InterruptedException
    {
        obj_id[] contents = getContents(container);
        for (int i = 0; i < contents.length; i++)
        {
            if (hasObjVar(contents[i], "noTrade"))
            {
                return true;
            }
        }
        return false;
    }
    public void propogateNoTrade(obj_id container, boolean isNoTrade) throws InterruptedException
    {
        obj_id player = utils.getContainingPlayer(container);
        obj_id inventory = getObjectInSlot(player, utils.SLOT_INVENTORY);
        obj_id parent = getContainedBy(container);
        while (isIdValid(parent) && !(getTemplateName(parent)).equals(structure.TEMPLATE_CELL) && parent != inventory && !player_structure.isBuilding(parent))
        {
            if (isNoTrade)
            {
                if (!hasObjVar(parent, "noTrade"))
                {
                    attachScript(parent, "item.special.nomove_container");
                    return;
                }
            }
            else 
            {
                if (containsNoTradeItem(parent))
                {
                    return;
                }
                if (hasScript(parent, "item.special.nomove_container"))
                {
                    removeObjVar(parent, "noTrade");
                    detachScript(parent, "item.special.nomove_container");
                }
                else 
                {
                    return;
                }
            }
            parent = getContainedBy(parent);
        }
    }
}
