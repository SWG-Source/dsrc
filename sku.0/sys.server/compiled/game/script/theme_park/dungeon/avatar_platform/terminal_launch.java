package script.theme_park.dungeon.avatar_platform;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.space_dungeon;
import script.library.space_dungeon_data;
import script.library.utils;
import script.library.sui;
import script.library.prose;

public class terminal_launch extends script.base_script
{
    public terminal_launch()
    {
    }
    public static final string_id SID_LAUNCH = new string_id("ep3/sidequests", "avatar_launch");
    public static final string_id SID_EJECT = new string_id("ep3/sidequests", "avatar_eject");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        mi.addRootMenu(menu_info_types.ITEM_USE, SID_LAUNCH);
        mi.addRootMenu(menu_info_types.SERVER_MENU1, SID_EJECT);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        LOG("space_dungeon", "theme_park.dungeon.avatar_platform.terminal_launch.OnObjectMenuSelect()");
        if (item == menu_info_types.ITEM_USE)
        {
            confirmLaunch(player, self);
        }
        else if (item == menu_info_types.SERVER_MENU1)
        {
            confirmEject(player, self);
        }
        return SCRIPT_CONTINUE;
    }
    public void confirmEject(obj_id player, obj_id terminal) throws InterruptedException
    {
        if (hasScript(player, "theme_park.dungeon.avatar_platform.player"))
        {
            LOG("space_dungeon", "theme_park.dungeon.avatar_platform.terminal_launch.confirmEject()");
            String prompt = "Really eject?";
            int pid = sui.msgbox(player, player, prompt, sui.YES_NO, "msgDungeonEjectConfirmed");
        }
        else 
        {
            warpPlayer(player, "kashyyyk_main", -670, 18, -137, null, -670, 18, -137);
        }
    }
    public void confirmLaunch(obj_id player, obj_id terminal) throws InterruptedException
    {
        if (hasScript(player, "theme_park.dungeon.avatar_platform.player"))
        {
            LOG("space_dungeon", "theme_park.dungeon.avatar_platform.terminal_launch.confirmLaunch()");
            String prompt = "Really launch?";
            int pid = sui.msgbox(player, player, prompt, sui.YES_NO, "msgDungeonLaunchConfirmed");
        }
        else 
        {
            warpPlayer(player, "kashyyyk_main", -670, 18, -137, null, -670, 18, -137);
        }
    }
}
