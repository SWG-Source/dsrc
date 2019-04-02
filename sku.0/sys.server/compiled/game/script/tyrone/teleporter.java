package script.tyrone;

import script.*;
import script.library.sui;
import script.library.utils;

public class teleporter extends script.base_script
{
    public teleporter()
    {
    }
    public static final String STF = "city/city";
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
        {
            menu_info_data mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
            int menu = mi.addRootMenu(menu_info_types.ITEM_USE, new string_id("gm_teleport", "teleport"));
            int menu2 = mi.addRootMenu(menu_info_types.SERVER_MENU8, new string_id("gm_teleport","read_about"));
            if(isGod(player))
            {
                int menu1 = mi.addRootMenu(menu_info_types.SERVER_MENU1, new string_id("gm_teleport", "setup"));
                mi.addSubMenu(menu1, menu_info_types.SERVER_MENU2, new string_id("gm_teleport", "scene"));
                mi.addSubMenu(menu1, menu_info_types.SERVER_MENU3, new string_id("gm_teleport", "x"));
                mi.addSubMenu(menu1, menu_info_types.SERVER_MENU5, new string_id("gm_teleport", "z"));
                mi.addSubMenu(menu1, menu_info_types.SERVER_MENU4, new string_id("gm_teleport", "y"));
                mi.addSubMenu(menu1, menu_info_types.SERVER_MENU7, new string_id("gm_teleport", "set_about"));
            }
            return SCRIPT_CONTINUE;
        }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            String planet = getStringObjVar(self, "teleport.scene");
            String xcoord = getStringObjVar(self, "teleport.x");
            String ycoord = getStringObjVar(self, "teleport.y");
            String zcoord = getStringObjVar(self, "teleport.z");
            float x = utils.stringToFloat(xcoord);
            float y = utils.stringToFloat(ycoord);
            float z = utils.stringToFloat(zcoord);
            warpPlayer(player, planet, x, z, y, null, 0.0f, 0.0f, 0.0f);
            
        }
        if (item == menu_info_types.SERVER_MENU1)
        {
            String planet = getStringObjVar(self, "teleport.scene");
            String xcoord = getStringObjVar(self, "teleport.x");
            String ycoord = getStringObjVar(self, "teleport.y");
            String zcoord = getStringObjVar(self, "teleport.z");
            sendSystemMessageTestingOnly(player, "How to: Use radial submenus to set Scene and XZY Coordinates to teleport.");
            setName(self, "[" + planet + "] X: " + xcoord + " | Z: "  + zcoord + " | Y: " + ycoord);
            sendSystemMessageTestingOnly(player, "Debug name set. Use /setname to customize it.");
        }
        if (item == menu_info_types.SERVER_MENU2)
        {
            String message1 = "";
            String buffer = "Enter a Planet name for this teleporter to teleport to:";
            String title = "Event Teleporter";
            sui.filteredInputbox(self, player, buffer, title, "handleSceneRequest", message1);
        }
        if (item == menu_info_types.SERVER_MENU3)
        {
            String message3 = "";
            String buffer = "Enter the X coordinate";
            String title = "Event Teleporter";
            sui.filteredInputbox(self, player, buffer, title, "handleXRequest", message3);
        }
        if (item == menu_info_types.SERVER_MENU5)
        {
            String message4 = "";
            String buffer = "Enter the Z coordinate";
            String title = "Event Teleporter";
            sui.filteredInputbox(self, player, buffer, title, "handleZRequest", message4);
        }
        if (item == menu_info_types.SERVER_MENU4)
        {
            String message5 = "";
            String buffer = "Enter the Y coordinate";
            String title = "Event Teleporter";
            sui.filteredInputbox(self, player, buffer, title, "handleYRequest", message5);
        }
        if (item == menu_info_types.SERVER_MENU7)
        {
            String message6 = "";
            String buffer = "Enter an about message that players can see.";
            String title = "Event Teleporter";
            sui.filteredInputbox(self, player, buffer, title, "handleMSGRequest", message6);
        }
        if (item == menu_info_types.SERVER_MENU8)
        {
            String title = "Event Information";
            String buffer = getStringObjVar(self, "teleport.msg");
            sui.msgbox(self, player, buffer, title);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleSceneRequest(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        String message1 = sui.getInputBoxText(params);
        if (message1 == null)
        {
            sendSystemMessage(self, new string_id(STF, "not_valid_message"));
            return SCRIPT_CONTINUE;
        }
        if (message1.equals("tatooine"))
        {
            sendSystemMessageTestingOnly(player, "Note: using Tatooine will most likely lag the server with many players in a concentrated area.");
            setObjVar(self, "teleport.scene", message1);
            return SCRIPT_CONTINUE;
        }
        if (message1.equals("kashyyyk_main") || message1.contains("mustafar"))
        {
            sendSystemMessageTestingOnly(player, "Note: Kashyyyk and Mustafar may have offset coordinates.");
            setObjVar(self, "teleport.scene", message1);
            return SCRIPT_CONTINUE;
        }
        setObjVar(self, "teleport.scene", message1);
        return SCRIPT_CONTINUE;
    }
    public int handleMSGRequest(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        String message6 = sui.getInputBoxText(params);
        if (message6 == null)
        {
            sendSystemMessage(self, new string_id(STF, "not_valid_message"));
            return SCRIPT_CONTINUE;
        }
        setObjVar(self, "teleport.msg", message6);
        return SCRIPT_CONTINUE;
    }
    public int handleXRequest(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        String message3 = sui.getInputBoxText(params);
        if (message3 == null)
        {
            sendSystemMessage(self, new string_id(STF, "not_valid_message"));
            return SCRIPT_CONTINUE;
        }
        setObjVar(self, "teleport.x", message3);
        return SCRIPT_CONTINUE;
    }
    public int handleYRequest(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        String message4 = sui.getInputBoxText(params);
        if (message4 == null)
        {
            sendSystemMessage(self, new string_id(STF, "not_valid_message"));
            return SCRIPT_CONTINUE;
        }
        setObjVar(self, "teleport.y", message4);
        return SCRIPT_CONTINUE;
    }
    public int handleZRequest(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        String message5 = sui.getInputBoxText(params);
        if (message5 == null)
        {
            sendSystemMessage(self, new string_id(STF, "not_valid_message"));
            return SCRIPT_CONTINUE;
        }
        setObjVar(self, "teleport.z", message5);
        return SCRIPT_CONTINUE;
    }
}