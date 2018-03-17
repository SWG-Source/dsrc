package script.player;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.player_structure;
import script.library.sui;
import script.library.utils;

public class player_sui extends script.base_script
{
    public player_sui()
    {
    }
    public static final String TERMINAL_LOGGING = "special_sign";
    public static final boolean LOGGING_ON = true;
    public int handleCloseSui(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        int pid = params.getInt("pid");
        if (pid > -1)
        {
            forceCloseSUIPage(pid);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleSetSuiAssociate(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        int pid = params.getInt("pid");
        if (pid < 0)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id associate = params.getObjId("associate");
        if (!isIdValid(associate))
        {
            return SCRIPT_CONTINUE;
        }
        float range = params.getFloat("range");
        if (range < 0f)
        {
            return SCRIPT_CONTINUE;
        }
        setSUIAssociatedObject(pid, associate);
        setSUIMaxRangeToObject(pid, range);
        return SCRIPT_CONTINUE;
    }
    public int handleDecorTypeSelect(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        blog("handleDecorTypeSelect: init");
        int idx = sui.getListboxSelectedRow(params);
        if (idx < 0)
        {
            idx = 0;
        }
        obj_id player = sui.getPlayerId(params);
        if (!isValidId(player) || !exists(player))
        {
            removeDecorVars(player);
            return SCRIPT_CONTINUE;
        }
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            removeDecorVars(player);
            return SCRIPT_CONTINUE;
        }
        blog("handleDecorTypeSelect: initial validation done");
        String[] decorMenu = utils.getStringArrayScriptVar(player, ".decorMenu");
        if (decorMenu == null || decorMenu.length <= 0)
        {
            removeDecorVars(player);
            return SCRIPT_CONTINUE;
        }
        blog("handleDecorTypeSelect: decorMenu received");
        String[] slotName = utils.getStringArrayScriptVar(player, ".slotName");
        if (slotName == null || slotName.length <= 0)
        {
            removeDecorVars(player);
            return SCRIPT_CONTINUE;
        }
        blog("handleDecorTypeSelect: slotName received");
        String menuSelection = decorMenu[idx];
        if (menuSelection == null || menuSelection.equals(""))
        {
            removeDecorVars(player);
            return SCRIPT_CONTINUE;
        }
        blog("handleDecorTypeSelect: menuSelection: " + menuSelection);
        String slotSelection = slotName[idx];
        if (slotSelection == null || slotSelection.equals(""))
        {
            removeDecorVars(player);
            return SCRIPT_CONTINUE;
        }
        blog("handleDecorTypeSelect: slotSelection: " + slotSelection);
        String template = utils.getStringScriptVar(player, ".structureTemplate");
        if (template == null || template.equals(""))
        {
            removeDecorVars(player);
            return SCRIPT_CONTINUE;
        }
        blog("handleDecorTypeSelect: template: " + template);
        obj_id structure = utils.getObjIdScriptVar(player, ".structure");
        if (!isValidId(structure) || !exists(structure))
        {
            removeDecorVars(player);
            return SCRIPT_CONTINUE;
        }
        blog("handleDecorTypeSelect: structure: " + structure);
        blog("handleDecorTypeSelect: finished, sendign data");
        removeDecorVars(player);
        return SCRIPT_CONTINUE;
    }
    public int handleDecorSlotRemovalSelect(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        blog("handleDecorSlotRemovalSelect: init");
        int idx = sui.getListboxSelectedRow(params);
        if (idx < 0)
        {
            idx = 0;
        }
        obj_id player = sui.getPlayerId(params);
        if (!isValidId(player) || !exists(player))
        {
            removeDecorVars(player);
            return SCRIPT_CONTINUE;
        }
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            removeDecorVars(player);
            return SCRIPT_CONTINUE;
        }
        blog("handleDecorSlotRemovalSelect: initial validation done");
        String[] occupiedSlots = utils.getStringArrayScriptVar(player, ".occupiedSlots");
        if (occupiedSlots == null || occupiedSlots.length <= 0)
        {
            removeDecorVars(player);
            return SCRIPT_CONTINUE;
        }
        String menuSelection = occupiedSlots[idx];
        if (menuSelection == null || menuSelection.equals(""))
        {
            removeDecorVars(player);
            return SCRIPT_CONTINUE;
        }
        obj_id structure = utils.getObjIdScriptVar(player, ".structure");
        if (!isValidId(structure) || !exists(structure))
        {
            removeDecorVars(player);
            return SCRIPT_CONTINUE;
        }
        blog("handleDecorSlotRemovalSelect: menuSelection: " + menuSelection);
        return SCRIPT_CONTINUE;
    }
    public boolean removeDecorVars(obj_id player) throws InterruptedException
    {
        if (!isValidId(player) || !exists(player))
        {
            return false;
        }
        return true;
    }
    public boolean blog(String msg) throws InterruptedException
    {
        if (msg == null || msg.equals(""))
        {
            return false;
        }
        if (LOGGING_ON)
        {
            LOG(TERMINAL_LOGGING, msg);
        }
        return true;
    }
}
