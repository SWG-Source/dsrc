package script.npe;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.utils;
import script.library.npe;
import script.library.sui;

public class station_exit_terminal extends script.base_script
{
    public station_exit_terminal()
    {
    }
    public static final string_id EXIT_STATION = new string_id("npe", "exit_station");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        mi.addRootMenu(menu_info_types.ITEM_USE, EXIT_STATION);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            if (checkGod(player))
            {
                return SCRIPT_CONTINUE;
            }
            string_id stfPrompt = new string_id("npe", "exit_station_prompt");
            string_id stfTitle = new string_id("npe", "exit_station");
            String prompt = utils.packStringId(stfPrompt);
            String title = utils.packStringId(stfTitle);
            int pid = sui.msgbox(self, player, prompt, sui.OK_CANCEL, title, 0, "handTransfer");
        }
        return SCRIPT_CONTINUE;
    }
    public boolean checkGod(obj_id self) throws InterruptedException
    {
        if (isGod(self))
        {
            sendSystemMessageTestingOnly(self, "Please turn off god mode when moving between npe locations. God mode and instances do not get along");
            return true;
        }
        return false;
    }
    public int handTransfer(obj_id self, dictionary params) throws InterruptedException
    {
        int btn = sui.getIntButtonPressed(params);
        obj_id player = sui.getPlayerId(params);
        if (btn == sui.BP_OK)
        {
            sendSystemMessageTestingOnly(player, "Going to staging area...");
            npe.movePlayerFromSharedStationToFinishLocation(player);
        }
        return SCRIPT_CONTINUE;
    }
}
