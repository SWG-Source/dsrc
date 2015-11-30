package script.theme_park.dungeon.avatar_platform;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;
import script.library.money;
import script.library.utils;
import script.library.weapons;

public class avatar_lockbox_03 extends script.base_script
{
    public avatar_lockbox_03()
    {
    }
    public static final String STF = "dungeon/avatar_platform";
    public static final string_id ENTER_CODE = new string_id(STF, "lockbox_code");
    public static final string_id UNLOCKED = new string_id(STF, "lockbox_unlocked");
    public static final string_id UNKNOWN_CODE = new string_id(STF, "lockbox_unknown");
    public static final string_id INCORRECT_CODE = new string_id(STF, "lockbox_incorrect");
    public static final string_id UNLOCK = new string_id(STF, "lockbox_unlock");
    public static final string_id NOT_NEEDED = new string_id(STF, "not_needed");
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
            if (groundquests.isQuestActive(player, "ep3_avatar_wke_prisoner_03"))
            {
                if (!utils.hasScriptVar(player, "lockbox.pid"))
                {
                    keypad(player);
                }
                return SCRIPT_CONTINUE;
            }
            else if (groundquests.hasCompletedQuest(player, "ep3_avatar_wke_prisoner_03"))
            {
                sendSystemMessage(player, NOT_NEEDED);
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
        money.bankTo(money.ACCT_NEW_PLAYER_QUESTS, player, 7500);
        groundquests.sendSignal(player, "lockBox03");
        weapons.createWeapon("object/weapon/ranged/pistol/ep3/pistol_wookiee_bowcaster.iff", playerInv, 1, 1, 1, 1);
    }
}
