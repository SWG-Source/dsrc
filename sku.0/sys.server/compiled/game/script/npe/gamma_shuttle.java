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
import script.library.groundquests;
import script.library.space_quest;
import script.library.space_utils;
import script.library.space_crafting;
import script.library.sui;

public class gamma_shuttle extends script.base_script
{
    public gamma_shuttle()
    {
    }
    public static final string_id LAUNCH = new string_id("npe", "launch");
    public static final String NPE_STATION = "object/building/general/npe_space_station.iff";
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        mi.addRootMenu(menu_info_types.ITEM_USE, LAUNCH);
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
            if (utils.hasScriptVar(player, "npe.gamma_travel.pid"))
            {
                return SCRIPT_CONTINUE;
            }
            obj_id container = getTopMostContainer(self);
            String containerTemplate = getTemplateName(container);
            string_id stfPrompt = new string_id("npe", "gamma_shuttle_prompt");
            string_id stfTitle = new string_id("npe", "gamma_shuttle_title");
            String prompt = utils.packStringId(stfPrompt);
            String title = utils.packStringId(stfTitle);
            location terminalLoc = getLocation(self);
            utils.setScriptVar(player, "npe.gamma_travel.term_loc", terminalLoc);
            utils.setScriptVar(player, "npe.gamma_travel.pid", true);
            if (containerTemplate.equals(NPE_STATION))
            {
                int pid = sui.msgbox(player, player, prompt, sui.OK_CANCEL, title, 0, "npeGammaTransfer");
            }
            else 
            {
                int pid = sui.msgbox(player, player, prompt, sui.OK_CANCEL, title, 0, "npeStationTransfer");
            }
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
}
