package script.terminal.base;

import script.library.structure;
import script.menu_info;
import script.menu_info_data;
import script.menu_info_types;
import script.obj_id;

public class base_terminal extends script.base_script
{
    public base_terminal()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, structure.VAR_TERMINAL_HEADING))
        {
            setYaw(self, getFloatObjVar(self, structure.VAR_TERMINAL_HEADING));
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (isMob(self))
        {
            return SCRIPT_CONTINUE;
        }
        menu_info_data mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
        if (mid != null)
        {
            mid.setServerNotify(true);
        }
        return SCRIPT_CONTINUE;
    }
    public boolean playerMounted(obj_id player) throws InterruptedException
    {
        debugServerConsoleMsg(null, "+++ terminal.base.base_terminal playerMounted +++ entered playerMounted function");
        if (isIdValid(getMountId(player)))
        {
            debugServerConsoleMsg(null, "+++ terminal.base.base_terminal playerMounted +++ player IS mounted.");
            return true;
        }
        return false;
    }
}
