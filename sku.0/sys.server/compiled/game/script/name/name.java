package script.name;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.player_structure;
import script.library.static_item;
import script.library.sui;
import script.library.utils;

public class name extends script.base_script
{
    public name()
    {
    }
    public static final int MAX_NAME_LENGTH = 32;
    public static final string_id SID_SET_NAME_TITLE = new string_id("sui", "set_name_title");
    public static final string_id SID_SET_NAME_PROMPT = new string_id("sui", "set_name_prompt");
    public static final String HANDLER_NAME_OBJECT = "handleNameObject";
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (static_item.isStaticItem(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.isNestedWithin(self, player) || isInPlayersHouse(self, player))
        {
            menu_info_data data = mi.getMenuItemByType(menu_info_types.SET_NAME);
            if (data != null)
            {
                data.setServerNotify(true);
            }
            else 
            {
                mi.addRootMenu(menu_info_types.SET_NAME, null);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.SET_NAME)
        {
            if (utils.isNestedWithin(self, player) || isInPlayersHouse(self, player))
            {
                showNameInputUI(player, self);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void showNameInputUI(obj_id player, obj_id self) throws InterruptedException
    {
        String title = utils.packStringId(SID_SET_NAME_TITLE);
        String prompt = utils.packStringId(SID_SET_NAME_PROMPT);
        sui.inputbox(self, player, prompt, title, HANDLER_NAME_OBJECT, MAX_NAME_LENGTH, true, getEncodedName(self));
    }
    public int handleNameObject(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        int btn = sui.getIntButtonPressed(params);
        switch (btn)
        {
            case sui.BP_CANCEL:
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.isNestedWithin(self, player) || isInPlayersHouse(self, player))
        {
            String text = sui.getInputBoxText(params);
            int i = 0;
            int textlen = text.length();
            while (textlen > i && '@' == text.charAt(i))
            {
                ++i;
            }
            text = text.substring(i, textlen);
            if (!text.equals(""))
            {
                setName(self, text);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public boolean isInPlayersHouse(obj_id self, obj_id player) throws InterruptedException
    {
        if (utils.isInHouseCellSpace(self))
        {
            obj_id structure = getTopMostContainer(self);
            if (player_structure.isAdmin(structure, player))
            {
                return true;
            }
        }
        return false;
    }
}
