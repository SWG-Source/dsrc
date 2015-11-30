package script.quest.som;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;
import script.library.utils;

public class droid_factory_entrance_keypad extends script.base_script
{
    public droid_factory_entrance_keypad()
    {
    }
    public static final String STF = "som/som_quest";
    public static final string_id ENTER_CODE = new string_id(STF, "df_keypad_code");
    public static final string_id UNLOCKED = new string_id(STF, "df_keypad_unlocked");
    public static final string_id UNKNOWN_CODE = new string_id(STF, "df_keypad_unknown");
    public static final string_id INCORRECT_CODE = new string_id(STF, "df_keypad_incorrect");
    public static final string_id ALREADY_UNLOCKED = new string_id(STF, "df_keypad_already");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "passcode"))
        {
            setObjVar(self, "passcode", "37323");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "passcode"))
        {
            setObjVar(self, "passcode", "37323");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int menu = mi.addRootMenu(menu_info_types.ITEM_USE, ENTER_CODE);
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
            if (groundquests.isTaskActive(player, "som_story_arc_chapter_three_01", "mustafar_droidfactory_enter"))
            {
                keypad(player);
                return SCRIPT_CONTINUE;
            }
            if (groundquests.hasCompletedTask(player, "som_story_arc_chapter_three_01", "mustafar_droidfactory_enter"))
            {
                sendSystemMessage(player, ALREADY_UNLOCKED);
                return SCRIPT_CONTINUE;
            }
            if (groundquests.hasCompletedQuest(player, "som_story_arc_chapter_three_01"))
            {
                sendSystemMessage(player, ALREADY_UNLOCKED);
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
                groundquests.sendSignal(player, "mustafar_droidfactory_final");
                return SCRIPT_CONTINUE;
            }
            else 
            {
                sendSystemMessage(player, INCORRECT_CODE);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
