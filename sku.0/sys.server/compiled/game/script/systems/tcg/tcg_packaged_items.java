package script.systems.tcg;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.tcg;

public class tcg_packaged_items extends script.base_script
{
    public tcg_packaged_items()
    {
    }
    public static final String STF_FILE = "npe";
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int mnu2 = mi.addRootMenu(menu_info_types.ITEM_USE, new string_id(STF_FILE, "crate_use"));
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item != menu_info_types.ITEM_USE)
        {
            return SCRIPT_CONTINUE;
        }
        sendSystemMessage(player, new string_id(STF_FILE, "opened_crate"));
        if (!tcg.grantPackageContents(self, player))
        {
            CustomerServiceLog("tcg", "Packed item: " + self + " " + getName(self) + " failed to unpack its contents for player: " + player + " " + getName(player) + ".");
            return SCRIPT_CONTINUE;
        }
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
