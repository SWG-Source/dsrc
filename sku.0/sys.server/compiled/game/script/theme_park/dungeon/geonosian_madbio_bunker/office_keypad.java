package script.theme_park.dungeon.geonosian_madbio_bunker;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.slicing;
import script.library.utils;
import script.library.permissions;

public class office_keypad extends script.base_script
{
    public office_keypad()
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
        boolean playerHasSlicingSkill = hasSlicingSkill(player);
        obj_id self = getSelf();
        int pid = createSUIPage("Script.Keypad", self, player, "KeypadCallback");
        subscribeToSUIProperty(pid, "result.numberBox", "localtext");
        subscribeToSUIProperty(pid, "buttonEnter", "ButtonPressed");
        if (playersKeyCard == false)
        {
            setSUIProperty(pid, "buttonKeyCard", "enabled", "false");
        }
        if (playerHasSlicingSkill == false)
        {
            setSUIProperty(pid, "buttonSlice", "enabled", "false");
        }
        showSUIPage(pid);
        return;
    }
    public int KeypadCallback(obj_id self, dictionary params) throws InterruptedException
    {
        String result = params.getString("result.numberBox" + "." + "localtext");
        String button = params.getString("buttonEnter.ButtonPressed");
        obj_id player = params.getObjId("player");
        String passcard = "object/tangible/loot/dungeon/geonosian_mad_bunker/engineering_key.iff";
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
                websters.put("player", player);
                websters.put("room", openRoom);
                messageTo(openRoom, "addToList", websters, 1, false);
                sendSystemMessage(player, goodCode);
                setObjVar(player, objvarToSet, 1);
            }
            else 
            {
                string_id badcode = new string_id(MSGS, "bad_code");
                sendSystemMessage(player, badcode);
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
                websters.put("player", player);
                websters.put("room", openRoom);
                messageTo(openRoom, "addToList", websters, 1, false);
                destroyObject(keyCard);
                string_id keycard = new string_id(MSGS, "keycard_success");
                sendSystemMessage(player, keycard);
            }
        }
        if (button.equals("slice"))
        {
            slicing.startSlicing(player, self, "finishedSlicingKeypad", "keypad");
        }
        return SCRIPT_CONTINUE;
    }
    public boolean hasKeyCard(obj_id player) throws InterruptedException
    {
        obj_id playerInv = utils.getInventoryContainer(player);
        String passcard = "object/tangible/loot/dungeon/geonosian_mad_bunker/engineering_key.iff";
        boolean hadIt = false;
        hadIt = utils.playerHasItemByTemplate(player, passcard);
        return hadIt;
    }
    public boolean hasSlicingSkill(obj_id player) throws InterruptedException
    {
        boolean hadIt = false;
        if (hasSkill(player, "class_smuggler_phase1_novice"))
        {
            hadIt = true;
        }
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
    public int finishedSlicingKeypad(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        int success = params.getInt("success");
        obj_id player = params.getObjId("player");
        if (success == 1)
        {
            string_id hack = new string_id(MSGS, "hack_success");
            sendSystemMessage(player, hack);
            String room = getStringObjVar(self, "room");
            obj_id mom = getTopMostContainer(self);
            obj_id openRoom = getCellId(mom, room);
            dictionary websters = new dictionary();
            websters.put("player", player);
            websters.put("room", openRoom);
            messageTo(openRoom, "addToList", websters, 1, false);
        }
        else 
        {
            string_id fail = new string_id(MSGS, "hack_fail");
            sendSystemMessage(player, fail);
            setObjVar(self, "slicing.hacked", 1);
        }
        return SCRIPT_CONTINUE;
    }
}
