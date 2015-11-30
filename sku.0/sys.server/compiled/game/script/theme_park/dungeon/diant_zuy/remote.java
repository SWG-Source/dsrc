package script.theme_park.dungeon.diant_zuy;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.locations;

public class remote extends script.base_script
{
    public remote()
    {
    }
    public static final String DIANT_BUNKER = "dungeon/diant_bunker";
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        string_id remoteUse = new string_id(DIANT_BUNKER, "remote_use");
        menu_info_data mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
        if (mid != null)
        {
            mid.setServerNotify(true);
        }
        else 
        {
            mi.addRootMenu(menu_info_types.ITEM_USE, remoteUse);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        obj_id top = getTopMostContainer(self);
        int genState = getIntObjVar(top, "diant.generator");
        int remoteState = getIntObjVar(top, "diant.remote");
        if (item == menu_info_types.ITEM_USE)
        {
            if (genState == 1)
            {
                string_id computerDesktopDenied = new string_id(DIANT_BUNKER, "computer_desktop_denied");
                sendSystemMessage(player, computerDesktopDenied);
                location loc = getLocation(self);
                if (loc != null)
                {
                    playClientEffectLoc(player, "clienteffect/trap_electric_01.cef", loc, 0.f);
                }
            }
            if (genState == 0)
            {
                if (remoteState == 0)
                {
                    setObjVar(top, "diant.remote", 1);
                    string_id computerDesktopActivate = new string_id(DIANT_BUNKER, "computer_desktop_activate");
                    sendSystemMessage(player, computerDesktopActivate);
                }
                if (remoteState == 1)
                {
                    setObjVar(top, "diant.remote", 0);
                    string_id computerDesktopDeactivate = new string_id(DIANT_BUNKER, "computer_desktop_deactivate");
                    sendSystemMessage(player, computerDesktopDeactivate);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
