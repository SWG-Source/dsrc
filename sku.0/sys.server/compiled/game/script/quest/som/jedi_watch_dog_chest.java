package script.quest.som;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;
import script.library.money;
import script.library.weapons;
import script.library.utils;

public class jedi_watch_dog_chest extends script.base_script
{
    public jedi_watch_dog_chest()
    {
    }
    public static final String STF = "som/som_quest";
    public static final string_id ENTER_CODE = new string_id(STF, "enter_code");
    public static final string_id UNLOCKED = new string_id(STF, "unlocked");
    public static final string_id UNKNOWN_CODE = new string_id(STF, "unknown_code");
    public static final string_id INCORRECT_CODE = new string_id(STF, "incorrect_code");
    public static final string_id UNLOCK = new string_id(STF, "unlock");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "som_jedi_dog", "mustafar_jedi_dog_three"))
        {
            int menu = mi.addRootMenu(menu_info_types.ITEM_USE, ENTER_CODE);
            menu_info_data mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
            if (mid == null)
            {
                return SCRIPT_CONTINUE;
            }
            mid.setServerNotify(true);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            if (groundquests.isTaskActive(player, "som_jedi_dog", "mustafar_jedi_dog_three"))
            {
                keypad(player);
                return SCRIPT_CONTINUE;
            }
            else 
            {
                sendSystemMessage(player, UNKNOWN_CODE);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void keypad(obj_id player) throws InterruptedException
    {
        obj_id self = getSelf();
        int pid = createSUIPage("Script.Keypad", self, player, "KeypadCallback");
        subscribeToSUIProperty(pid, "result.numberBox", "localtext");
        subscribeToSUIProperty(pid, "buttonEnter", "ButtonPressed");
        showSUIPage(pid);
        return;
    }
    public int KeypadCallback(obj_id self, dictionary params) throws InterruptedException
    {
        String result = params.getString("result.numberBox" + "." + "localtext");
        String button = params.getString("buttonEnter.ButtonPressed");
        obj_id player = params.getObjId("player");
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
        String passkey = getStringObjVar(self, "passcode");
        if (passkey == null || passkey.equals(""))
        {
            passkey = "nothing";
        }
        if (button.equals("enter"))
        {
            if (passkey.equals(result))
            {
                sendSystemMessage(player, UNLOCKED);
                grantReward(player);
                return SCRIPT_CONTINUE;
            }
            else 
            {
                sendSystemMessage(player, INCORRECT_CODE);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void grantReward(obj_id player) throws InterruptedException
    {
        obj_id playerInv = utils.getInventoryContainer(player);
        groundquests.sendSignal(player, "mustafar_jedi_dog_reward");
    }
}
