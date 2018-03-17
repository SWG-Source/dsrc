package script.city.bestine;

import script.library.utils;
import script.menu_info;
import script.menu_info_types;
import script.obj_id;
import script.string_id;

public class search_item extends script.base_script
{
    public search_item()
    {
    }
    public static final string_id SEARCH_ITEM = new string_id("bestine", "search_item");
    public static final string_id ALREADY_SEARCHED_MSG = new string_id("bestine", "already_searched");
    public static final string_id DEFAULT_RECEIVE_MSG = new string_id("bestine", "default_receive_msg");
    public static final string_id INVENTORY_FULL_MSG = new string_id("bestine", "inventory_full");
    public static final String GATING_OBJVAR = "gatingObjVar";
    public static final String ALREADY_SEARCHED_OBJVAR = "bestine.already_searched";
    public static final String OBJECT_TEMPLATE_OBJVAR = "template";
    public static final String RECEIVE_OBJECT_MSG_OBJVAR = "receiveMsg";
    public static final String VAR_ITEM_REQUEST_BASE = "item_request";
    public static final String VAR_ITEM_REQUEST_CATEGORY = VAR_ITEM_REQUEST_BASE + ".category";
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (canSearch(self, player))
        {
            int menuOption = mi.addRootMenu(menu_info_types.SERVER_ITEM_OPTIONS, SEARCH_ITEM);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.SERVER_ITEM_OPTIONS)
        {
            if (canSearch(self, player))
            {
                if (hasObjVar(player, ALREADY_SEARCHED_OBJVAR))
                {
                    sendSystemMessage(player, ALREADY_SEARCHED_MSG);
                    return SCRIPT_CONTINUE;
                }
                String template = getStringObjVar(self, OBJECT_TEMPLATE_OBJVAR);
                if ((template != null) && (!template.equals("")))
                {
                    obj_id playerInv = utils.getInventoryContainer(player);
                    if (isIdValid(playerInv))
                    {
                        int free_space = getVolumeFree(playerInv);
                        if (free_space > 0)
                        {
                            obj_id objectReceived = createObject(template, playerInv, "");
                            if (isIdValid(objectReceived))
                            {
                                string_id receiveObjectMsg = DEFAULT_RECEIVE_MSG;
                                if (hasObjVar(self, RECEIVE_OBJECT_MSG_OBJVAR))
                                {
                                    String receiveMsgObjvar = getStringObjVar(self, RECEIVE_OBJECT_MSG_OBJVAR);
                                    if (receiveMsgObjvar.startsWith("@"))
                                    {
                                        receiveObjectMsg = utils.unpackString(receiveMsgObjvar);
                                    }
                                    else 
                                    {
                                        sendSystemMessage(player, receiveMsgObjvar, null);
                                        setObjVar(player, ALREADY_SEARCHED_OBJVAR, 1);
                                        return SCRIPT_CONTINUE;
                                    }
                                }
                                sendSystemMessage(player, receiveObjectMsg);
                                setObjVar(player, ALREADY_SEARCHED_OBJVAR, 1);
                            }
                        }
                        else 
                        {
                            sendSystemMessage(player, INVENTORY_FULL_MSG);
                        }
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public boolean canSearch(obj_id self, obj_id player) throws InterruptedException
    {
        boolean result = false;
        if (hasObjVar(self, GATING_OBJVAR) && hasObjVar(self, OBJECT_TEMPLATE_OBJVAR))
        {
            String gatingObjVar = getStringObjVar(self, GATING_OBJVAR);
            if (hasObjVar(player, gatingObjVar))
            {
                result = true;
            }
        }
        return result;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, RECEIVE_OBJECT_MSG_OBJVAR))
        {
            String receiveMsg = getStringObjVar(self, RECEIVE_OBJECT_MSG_OBJVAR);
            if (receiveMsg.startsWith("="))
            {
                String[] subStrings = split(receiveMsg, ':');
                String receiveMsgId = subStrings[1];
                if (receiveMsgId.endsWith("\""))
                {
                    receiveMsgId = receiveMsgId.substring(0, receiveMsgId.length() - 1);
                }
                String receiveMsgPacked = "@city/bestine/terminal_items:" + receiveMsgId;
                setObjVar(self, RECEIVE_OBJECT_MSG_OBJVAR, receiveMsgPacked);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
