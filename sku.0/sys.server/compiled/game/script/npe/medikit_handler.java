package script.npe;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.utils;

public class medikit_handler extends script.base_script
{
    public medikit_handler()
    {
    }
    public static final string_id GET_STIMPACK = new string_id("npe_hangar_1", "get_stimpack");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if ((getStringObjVar(player, "canpickupmedikit")).equals("yes"))
        {
            mi.addRootMenu(menu_info_types.ITEM_USE, GET_STIMPACK);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            setObjVar(player, "canpickupmedikit", "no");
            obj_id playerInv = utils.getInventoryContainer(player);
            obj_id stimpack = create.object("object/tangible/medicine/instant_stimpack/stimpack_a.iff", playerInv, false, false);
            setObjVar(stimpack, "healing.power", 250);
            setCount(stimpack, 5);
            messageTo(getTopMostContainer(self), "notifySequencerMedikit", null, 0, true);
        }
        return SCRIPT_CONTINUE;
    }
}
