package script.quest.ep3;

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

public class kachirho_sealed_container extends script.base_script
{
    public kachirho_sealed_container()
    {
    }
    public static final String STF = "ep3/sidequests";
    public static final string_id ENTER_CODE = new string_id(STF, "enter_code");
    public static final string_id UNLOCKED = new string_id(STF, "unlocked");
    public static final string_id UNKNOWN_CODE = new string_id(STF, "unknown_code");
    public static final string_id INCORRECT_CODE = new string_id(STF, "incorrect_code");
    public static final string_id UNLOCK = new string_id(STF, "unlock");
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
            if (groundquests.isTaskActive(player, "ep3_kachirho_destroyed_camp", "unlockContainer"))
            {
                if (!utils.hasScriptVar(player, "lockbox.pid"))
                {
                    keypad(player);
                }
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
        utils.setScriptVar(player, "lockbox.pid", pid);
        subscribeToSUIProperty(pid, "result.numberBox", "localtext");
        subscribeToSUIProperty(pid, "buttonEnter", "ButtonPressed");
        setSUIProperty(pid, "buttonKeyCard", "enabled", "false");
        setSUIProperty(pid, "buttonSlice", "enabled", "false");
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
            utils.removeScriptVarTree(player, "lockbox");
            return SCRIPT_OVERRIDE;
        }
        if (button == null)
        {
            utils.removeScriptVarTree(player, "lockbox");
            button = "none";
        }
        if (result == null)
        {
            utils.removeScriptVarTree(player, "lockbox");
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
                utils.removeScriptVarTree(player, "lockbox");
                return SCRIPT_CONTINUE;
            }
            else 
            {
                utils.removeScriptVarTree(player, "lockbox");
                sendSystemMessage(player, INCORRECT_CODE);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void grantReward(obj_id player) throws InterruptedException
    {
        obj_id playerInv = utils.getInventoryContainer(player);
        groundquests.sendSignal(player, "codeEntered");
        money.bankTo(money.ACCT_NEW_PLAYER_QUESTS, player, 10000);
        weapons.createWeapon("object/weapon/ranged/pistol/pistol_jawa.iff", playerInv, 1, 1, 1, 1);
    }
}
