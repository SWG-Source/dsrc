package script.systems.beast;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.beast_lib;
import script.library.player_structure;
import script.library.prose;
import script.library.utils;

public class enzyme_crafting_combiner extends script.systems.beast.enzyme_crafting_base
{
    public enzyme_crafting_combiner()
    {
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!isInValidOperatingLocation())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id structure = getTopMostContainer(self);
        if (!player_structure.isAdmin(structure, player))
        {
            return SCRIPT_CONTINUE;
        }
        if (!beast_lib.isBeastMaster(player))
        {
            sendSystemMessage(player, NOT_BEASTMASTER);
            return SCRIPT_CONTINUE;
        }
        completeAnyOperations();
        if (isInOperation())
        {
            int options = mi.addRootMenu(menu_info_types.ITEM_USE, new string_id("beast", "get_time_to_finish"));
            mi.addSubMenu(options, menu_info_types.ITEM_DEACTIVATE, new string_id("beast", "stop_combiner"));
        }
        if (isReadyToOperate())
        {
            int options = mi.addRootMenu(menu_info_types.ITEM_USE, new string_id("beast", "start_combiner"));
            if (isGod(player))
            {
                mi.addSubMenu(options, menu_info_types.ITEM_DEACTIVATE, new string_id("beast", "god_complete_in_10"));
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        sendDirtyObjectMenuNotification(self);
        if (item == menu_info_types.ITEM_USE)
        {
            prose_package pp = new prose_package();
            pp = prose.setStringId(pp, new string_id("beast", "combiner_runtime_info"));
            if (isInOperation())
            {
                pp = prose.setTO(pp, getTimeToFinishString());
                sendSystemMessageProse(player, pp);
            }
            else if (isReadyToOperate())
            {
                if (startProcess(player) == -1)
                {
                    sendSystemMessage(player, ERROR_ON_START);
                }
                else 
                {
                    pp = prose.setTO(pp, utils.formatTimeVerbose(COMBINER_RUNTIME));
                    sendSystemMessageProse(player, pp);
                }
            }
        }
        else if (item == menu_info_types.ITEM_DEACTIVATE)
        {
            if (isInOperation())
            {
                terminateProcess();
            }
            else if (isGod(player))
            {
                startProcess(player, 10);
                prose_package pp = new prose_package();
                pp = prose.setStringId(pp, new string_id("beast", "combiner_runtime_info"));
                pp = prose.setTO(pp, utils.formatTimeVerbose(10));
                sendSystemMessageProse(player, pp);
            }
        }
        sendDirtyObjectMenuNotification(self);
        return SCRIPT_CONTINUE;
    }
}
