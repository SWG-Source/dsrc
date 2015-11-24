package script.theme_park.dungeon.diant_zuy;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.permissions;

public class keypad extends script.base_script
{
    public keypad()
    {
    }
    public static final String MSGS = "dungeon/diant_bunker";
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        menu_info_data mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
        if (mid == null)
        {
            return SCRIPT_CONTINUE;
        }
        mid.setServerNotify(true);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            keypad(player);
        }
        return SCRIPT_CONTINUE;
    }
    public void keypad(obj_id player) throws InterruptedException
    {
        obj_id self = getSelf();
        int pid = createSUIPage("Script.Keypad", self, player, "KeypadCallback");
        subscribeToSUIProperty(pid, "result.numberBox", "localtext");
        subscribeToSUIProperty(pid, "buttonEnter", "ButtonPressed");
        setSUIProperty(pid, "buttonSlice", "enabled", "false");
        setSUIProperty(pid, "buttonSlice", "visible", "false");
        setSUIProperty(pid, "buttonKeycard", "enabled", "false");
        setSUIProperty(pid, "buttonKeycard", "visible", "false");
        showSUIPage(pid);
        return;
    }
    public int KeypadCallback(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id top = getTopMostContainer(self);
        String result = params.getString("result.numberBox" + "." + "localtext");
        String button = params.getString("buttonEnter.ButtonPressed");
        obj_id player = params.getObjId("player");
        String passcard = "object/tangible/loot/dungeon/corvette/passkey.iff";
        String room = getStringObjVar(self, "room");
        if (!isIdValid(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (button == null)
        {
            button = "none";
        }
        if (result == null)
        {
            result = "0";
        }
        int passkey = getIntObjVar(top, "diant.sekritCode");
        if (passkey == 0)
        {
            passkey = -1;
        }
        if (button.equals("enter"))
        {
            String pass = "";
            pass = pass + passkey;
            if (pass.equals(result))
            {
                string_id open = new string_id(MSGS, "keypad_open");
                sendSystemMessage(player, open);
                String playerName = getFirstName(player);
                obj_id pantry = getCellId(top, "pantry");
                permissionsAddAllowed(pantry, playerName);
            }
            else 
            {
                string_id lock = new string_id(MSGS, "keypad_lock");
                sendSystemMessage(player, lock);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
