package script.theme_park.dungeon.avatar_platform;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;

public class terminal_technical_readout extends script.base_script
{
    public terminal_technical_readout()
    {
    }
    public static final String STF = "dungeon/avatar_platform";
    public static final string_id STATION_READOUT = new string_id(STF, "technical_readout");
    public static final string_id READOUT_GRANTED = new string_id(STF, "readout_granted");
    public static final string_id ALREADY_READ = new string_id(STF, "already_read");
    public static final string_id NOT_USEFUL = new string_id(STF, "not_useful");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int menu = mi.addRootMenu(menu_info_types.ITEM_USE, STATION_READOUT);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            if (groundquests.isTaskActive(player, "ep3_trando_hssissk_zssik_10", "technicalReadout"))
            {
                if (!groundquests.isQuestActive(player, "ep3_avatar_self_destruct"))
                {
                    groundquests.grantQuest(player, "ep3_avatar_self_destruct");
                    sendSystemMessage(player, READOUT_GRANTED);
                }
                else 
                {
                    sendSystemMessage(player, ALREADY_READ);
                }
            }
            else 
            {
                sendSystemMessage(player, NOT_USEFUL);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
