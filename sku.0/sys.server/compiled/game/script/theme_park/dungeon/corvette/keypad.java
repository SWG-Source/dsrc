package script.theme_park.dungeon.corvette;

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
    public static final String MSGS = "dungeon/corvette";
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
        obj_id top = getTopMostContainer(self);
        if (item == menu_info_types.ITEM_USE)
        {
            if (hasObjVar(self, "vette.door_quest"))
            {
                String room = getStringObjVar(self, "room");
                if (hasObjVar(player, "corl_corvette." + room))
                {
                    obj_id theRoom = getCellId(top, room);
                    messageTo(theRoom, "unlock", null, 1, false);
                    string_id open = new string_id(MSGS, "open");
                    sendSystemMessage(player, open);
                }
                else 
                {
                    String whichRoom = getStringObjVar(self, "room");
                    if (whichRoom.equals("elevator57"))
                    {
                        string_id elevator_locked = new string_id(MSGS, "elevator_locked");
                        sendSystemMessage(player, elevator_locked);
                    }
                    if (whichRoom.equals("meetingroom38"))
                    {
                        string_id meeting_locked = new string_id(MSGS, "meeting_room_locked");
                        sendSystemMessage(player, meeting_locked);
                    }
                }
            }
            else 
            {
                keypad(player);
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
        setSUIProperty(pid, "buttonSlice", "enabled", "false");
        setSUIProperty(pid, "buttonSlice", "visible", "false");
        setSUIProperty(pid, "buttonKeycard", "enabled", "false");
        setSUIProperty(pid, "buttonKeycard", "visible", "false");
        showSUIPage(pid);
        return;
    }
    public int KeypadCallback(obj_id self, dictionary params) throws InterruptedException
    {
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
        String objVarToCheck = getGroupObjVars(player, room);
        int passkey = getIntObjVar(player, objVarToCheck);
        if (passkey == 0)
        {
            passkey = -1;
        }
        if (button.equals("enter"))
        {
            string_id goodCode = new string_id(MSGS, "right_code");
            obj_id mom = getTopMostContainer(self);
            obj_id openRoom = getCellId(mom, room);
            String pass = "";
            pass = pass + passkey;
            if (pass.equals(result))
            {
                messageTo(openRoom, "unlock", null, 1, false);
                string_id open = new string_id(MSGS, "open");
                sendSystemMessage(player, open);
            }
            else 
            {
                string_id lock = new string_id(MSGS, "lock");
                sendSystemMessage(player, lock);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public String getGroupObjVars(obj_id player, String roomName) throws InterruptedException
    {
        String totalName = "corl_corvette." + roomName;
        String code = "";
        if (hasObjVar(player, totalName))
        {
            return totalName;
        }
        obj_id[] members = getGroupMemberIds(player);
        if (members == null)
        {
            return code;
        }
        int numInGroup = members.length;
        if (numInGroup == 0)
        {
            return code;
        }
        for (int i = 0; i < numInGroup; i++)
        {
            obj_id thisMember = members[i];
            if (hasObjVar(thisMember, totalName))
            {
                return totalName;
            }
        }
        return code;
    }
}
