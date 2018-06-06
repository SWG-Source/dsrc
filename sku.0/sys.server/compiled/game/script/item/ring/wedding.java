package script.item.ring;

import script.library.marriage;
import script.library.utils;
import script.menu_info;
import script.menu_info_types;
import script.obj_id;
import script.string_id;

public class wedding extends script.base_script
{
    public wedding()
    {
    }
    public static final string_id SID_CANNOT_REMOVE_RING = new string_id("unity", "cannot_remove_ring");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        detachScript(self, marriage.SCRIPT_RING_BASE);
        if (hasScript(self, "item.special.nomove"))
        {
            setObjVar(self, "wasNoMove", 1);
        }
        else 
        {
            attachScript(self, "item.special.nomove");
        }
        setAutoInsured(self);
        setHitpoints(self, getMaxHitpoints(self));
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        attachScript(self, marriage.SCRIPT_RING_BASE);
        if (!hasObjVar(self, "wasNoMove"))
        {
            detachScript(self, "item.special.nomove");
        }
        setUninsurable(self, false);
        setInsured(self, true);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setAutoInsured(self);
        setHitpoints(self, getMaxHitpoints(self));
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id player = utils.getContainingPlayer(self);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (marriage.isMarried(player))
        {
            marriage.divorce(player);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (utils.isNestedWithin(self, player))
        {
            if (marriage.isMarried(player))
            {
                mi.addRootMenu(menu_info_types.SERVER_MENU2, marriage.MNU_DIVORCE);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.SERVER_MENU2)
        {
            if (marriage.isMarried(player))
            {
                marriage.divorce(player);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
