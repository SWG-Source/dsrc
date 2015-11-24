package script.systems.encoded_disk.base;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.sui;
import script.library.locations;
import script.library.utils;
import script.library.scenario;
import script.library.create;
import script.library.ai_lib;

public class encoded_disk extends script.base_script
{
    public encoded_disk()
    {
    }
    public static final string_id SID_DECODE = new string_id("treasure_map/treasure_map", "decode");
    public static final string_id SID_SYS_DECODE_SUCCESS = new string_id("treasure_map/treasure_map", "sys_decode_success");
    public static final string_id SID_SYS_INVENTROY_FULL = new string_id("treasure_map/treasure_map", "sys_inventory_full");
    public static final int DISK_TREASURE_MAP = 1;
    public static final int DISK_MESSAGE_FRAGMENT = 2;
    public static final int DISK_UNCRACKABLE = 3;
    public static final String TREASURE_MAP = "object/tangible/treasure_map/treasure_map_base.iff";
    public static final String MESSAGE_FRAGMENT = "object/tangible/encoded_disk/message_fragment_base.iff";
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        mi.addRootMenu(menu_info_types.SERVER_MENU1, SID_DECODE);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.SERVER_MENU1)
        {
            decodeDisk(self, player);
        }
        return SCRIPT_CONTINUE;
    }
    public void decodeDisk(obj_id self, obj_id player) throws InterruptedException
    {
        obj_id container = null;
        int type = rand(1, 2);
        switch (type)
        {
            case DISK_TREASURE_MAP:
            container = getContainedBy(self);
            if (container == null)
            {
            }
            else 
            {
                obj_id tmp = createObject(TREASURE_MAP, container, "");
                if (isIdValid(tmp))
                {
                    sendSystemMessage(player, SID_SYS_DECODE_SUCCESS);
                    destroyObject(self);
                }
                else 
                {
                    sendSystemMessage(player, SID_SYS_INVENTROY_FULL);
                }
            }
            break;
            case DISK_MESSAGE_FRAGMENT:
            container = getContainedBy(self);
            if (container == null)
            {
            }
            else 
            {
                obj_id tmp = createObject(MESSAGE_FRAGMENT, container, "");
                if (isIdValid(tmp))
                {
                    sendSystemMessage(player, SID_SYS_DECODE_SUCCESS);
                    destroyObject(self);
                }
                else 
                {
                    sendSystemMessage(player, SID_SYS_INVENTROY_FULL);
                }
            }
            break;
        }
    }
}
