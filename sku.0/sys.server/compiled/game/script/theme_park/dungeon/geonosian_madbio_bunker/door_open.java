package script.theme_park.dungeon.geonosian_madbio_bunker;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.permissions;

public class door_open extends script.base_script
{
    public door_open()
    {
    }
    public static final String MSGS = "dungeon/geonosian_madbio";
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
        boolean playersKeyCard = hasKeyCard(player);
        obj_id self = getSelf();
        int pid = createSUIPage("Script.Keypad", self, player, "KeypadCallback");
        subscribeToSUIProperty(pid, "result.numberBox", "localtext");
        subscribeToSUIProperty(pid, "buttonEnter", "ButtonPressed");
        if (playersKeyCard == false)
        {
            setSUIProperty(pid, "buttonKeyCard", "enabled", "false");
        }
        showSUIPage(pid);
        return;
    }
    public int KeypadCallback(obj_id self, dictionary params) throws InterruptedException
    {
        String result = params.getString("result.numberBox" + "." + "localtext");
        String button = params.getString("buttonEnter.ButtonPressed");
        obj_id player = params.getObjId("player");
        String passcard = "object/tangible/loot/dungeon/geonosian_mad_bunker/passkey.iff";
        boolean playersKeyCard = hasKeyCard(player);
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
        String objvarToSet = getStringObjVar(self, "player_lock");
        if (passkey == null || passkey.equals(""))
        {
            passkey = "nothing";
        }
        if (button.equals("enter"))
        {
            if (passkey.equals(result))
            {
                string_id goodCode = new string_id(MSGS, "right_code");
                String room = getStringObjVar(self, "room");
                obj_id mom = getTopMostContainer(self);
                obj_id openRoom = getCellId(mom, room);
                dictionary websters = new dictionary();
                websters.put(player, "player");
                websters.put(openRoom, "room");
                messageTo(openRoom, "addToList", websters, 1, false);
                sendSystemMessage(player, goodCode);
            }
        }
        if (button.equals("keycard"))
        {
            obj_id keyCard = keyCardObjId(player, passcard);
            if (keyCard == null)
            {
            }
            else 
            {
                String room = getStringObjVar(self, "room");
                obj_id mom = getTopMostContainer(self);
                obj_id openRoom = getCellId(mom, room);
                dictionary websters = new dictionary();
                websters.put(player, "player");
                websters.put(openRoom, "room");
                messageTo(openRoom, "addToList", websters, 1, false);
                destroyObject(keyCard);
                string_id keycard = new string_id(MSGS, "keycard_success");
                sendSystemMessage(player, keycard);
            }
        }
        if (button.equals("slice"))
        {
        }
        return SCRIPT_CONTINUE;
    }
    public boolean hasKeyCard(obj_id player) throws InterruptedException
    {
        obj_id playerInv = utils.getInventoryContainer(player);
        String passcard = "object/tangible/loot/dungeon/geonosian_mad_bunker/passkey.iff";
        boolean hadIt = false;
        hadIt = utils.playerHasItemByTemplate(player, passcard);
        return hadIt;
    }
    public obj_id keyCardObjId(obj_id objPlayer, String strTemplate) throws InterruptedException
    {
        obj_id objInventory = utils.getInventoryContainer(objPlayer);
        if (isIdValid(objInventory))
        {
            obj_id[] objContents = getContents(objInventory);
            if (objContents != null)
            {
                for (int intI = 0; intI < objContents.length; intI++)
                {
                    String strItemTemplate = getTemplateName(objContents[intI]);
                    if (strItemTemplate.equals(strTemplate))
                    {
                        obj_id keycard = objContents[intI];
                        return keycard;
                    }
                }
            }
        }
        return null;
    }
}
