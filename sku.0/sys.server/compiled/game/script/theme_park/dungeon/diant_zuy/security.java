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

public class security extends script.base_script
{
    public security()
    {
    }
    public static final String DIANT_BUNKER = "dungeon/diant_bunker";
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        string_id securityUse = new string_id(DIANT_BUNKER, "security_use");
        menu_info_data mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
        if (mid != null)
        {
            mid.setServerNotify(true);
        }
        else 
        {
            mi.addRootMenu(menu_info_types.ITEM_USE, securityUse);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        obj_id top = getTopMostContainer(self);
        int genState = getIntObjVar(top, "diant.generator");
        int remoteState = getIntObjVar(top, "diant.remote");
        int securityState = getIntObjVar(top, "diant.security");
        if (item == menu_info_types.ITEM_USE)
        {
            if (genState == 0)
            {
                string_id securityNoPower = new string_id(DIANT_BUNKER, "security_no_power");
                sendSystemMessage(player, securityNoPower);
            }
            if (genState == 1)
            {
                if (remoteState == 0)
                {
                    string_id securityAccessDenied = new string_id(DIANT_BUNKER, "security_access_denied");
                    sendSystemMessage(player, securityAccessDenied);
                }
                if (remoteState == 1)
                {
                    if (securityState == 0)
                    {
                        setObjVar(top, "diant.security", 1);
                        string_id securityOverride = new string_id(DIANT_BUNKER, "security_override");
                        sendSystemMessage(player, securityOverride);
                    }
                    if (securityState == 1)
                    {
                        setObjVar(top, "diant.security", 0);
                        string_id securityOverrideReset = new string_id(DIANT_BUNKER, "security_override_reset");
                        sendSystemMessage(player, securityOverrideReset);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
