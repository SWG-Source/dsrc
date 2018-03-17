package script.item.goggles;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.hue;
import script.library.sui;
import script.library.utils;
import script.library.features;

public class menu extends script.base_script
{
    public menu()
    {
    }
    public static final string_id MNU_COLOR = new string_id("sui", "set_color");
    public static final string_id MNU_COLOR_LENS = new string_id("sui", "color_lens");
    public static final string_id MNU_COLOR_FRAME = new string_id("sui", "color_frame");
    public static final String SCRIPTVAR_FRAME_PID = "goggles.frame.pid";
    public static final String SCRIPTVAR_LENS_PID = "goggles.lens.pid";
    public static final String TBL = "datatables/item/goggles/indices.iff";
    public static final String[] COLLECTOR_EDITION_ITEMS = 
    {
        "object/tangible/wearables/goggles/goggles_s01.iff",
        "object/tangible/wearables/goggles/goggles_s02.iff",
        "object/tangible/wearables/goggles/goggles_s03.iff",
        "object/tangible/wearables/goggles/goggles_s04.iff",
        "object/tangible/wearables/goggles/goggles_s05.iff",
        "object/tangible/wearables/goggles/goggles_s06.iff"
    };
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "validateGoggles", null, 5, false);
        setHitpoints(self, getMaxHitpoints(self));
        setAutoInsured(self);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!utils.isNestedWithin(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        String template = utils.getTemplateFilenameNoPath(self);
        String frameVar = dataTableGetString(TBL, template, "FRAME");
        String lensVar = dataTableGetString(TBL, template, "LENS");
        if ((frameVar != null && !frameVar.equals("") && !frameVar.equals("none")) || (lensVar != null && !lensVar.equals("") && !lensVar.equals("none")))
        {
            int mnuColor = mi.addRootMenu(menu_info_types.SERVER_MENU1, MNU_COLOR);
            if (mnuColor > -1 && ((getContainedBy(self) != getOwner(self)) || isGod(player)))
            {
                if (frameVar != null && !frameVar.equals("") && !frameVar.equals("none"))
                {
                    int mnuColorFrame = mi.addSubMenu(mnuColor, menu_info_types.SERVER_MENU2, MNU_COLOR_FRAME);
                }
                if (lensVar != null && !lensVar.equals("") && !lensVar.equals("none"))
                {
                    int mnuColorLens = mi.addSubMenu(mnuColor, menu_info_types.SERVER_MENU3, MNU_COLOR_LENS);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (!utils.isNestedWithin(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        String template = utils.getTemplateFilenameNoPath(self);
        if (item == menu_info_types.SERVER_MENU1)
        {
            if (getContainedBy(self) == getOwner(self))
            {
                sendSystemMessage(player, new string_id("error_message", "equipped_goggles"));
            }
            else 
            {
                sendSystemMessage(player, new string_id("error_message", "goggle_submenu"));
            }
        }
        else if (item == menu_info_types.SERVER_MENU2)
        {
            if (utils.hasScriptVar(self, SCRIPTVAR_FRAME_PID))
            {
                int oldpid = utils.getIntScriptVar(self, SCRIPTVAR_FRAME_PID);
                sui.closeSUI(player, oldpid);
                utils.removeScriptVar(self, SCRIPTVAR_FRAME_PID);
            }
            String frameVar = dataTableGetString(TBL, template, "FRAME");
            if (frameVar != null && !frameVar.equals("") && !frameVar.equals("none"))
            {
                int pid_frame = sui.colorize(self, player, self, frameVar, "handleColorizeFrame");
                if (pid_frame > -1)
                {
                    utils.setScriptVar(self, SCRIPTVAR_FRAME_PID, pid_frame);
                }
            }
        }
        else if (item == menu_info_types.SERVER_MENU3)
        {
            if (utils.hasScriptVar(self, SCRIPTVAR_LENS_PID))
            {
                int oldpid = utils.getIntScriptVar(self, SCRIPTVAR_LENS_PID);
                sui.closeSUI(player, oldpid);
                utils.removeScriptVar(self, SCRIPTVAR_LENS_PID);
            }
            String lensVar = dataTableGetString(TBL, template, "LENS");
            if (lensVar != null && !lensVar.equals("") && !lensVar.equals("none"))
            {
                int pid_lens = sui.colorize(self, player, self, lensVar, "handleColorizeLens");
                if (pid_lens > -1)
                {
                    utils.setScriptVar(self, SCRIPTVAR_LENS_PID, pid_lens);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleColorizeFrame(obj_id self, dictionary params) throws InterruptedException
    {
        utils.removeScriptVar(self, SCRIPTVAR_FRAME_PID);
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        String template = utils.getTemplateFilenameNoPath(self);
        String frameVar = dataTableGetString(TBL, template, "FRAME");
        if (frameVar != null && !frameVar.equals("") && !frameVar.equals("none"))
        {
            int idx = sui.getColorPickerIndex(params);
            if (idx > -1)
            {
                hue.setColor(self, frameVar, idx);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleColorizeLens(obj_id self, dictionary params) throws InterruptedException
    {
        utils.removeScriptVar(self, SCRIPTVAR_LENS_PID);
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        String template = utils.getTemplateFilenameNoPath(self);
        String lensVar = dataTableGetString(TBL, template, "LENS");
        if (lensVar != null && !lensVar.equals("") && !lensVar.equals("none"))
        {
            int idx = sui.getColorPickerIndex(params);
            if (idx > -1)
            {
                hue.setColor(self, lensVar, idx);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int validateGoggles(obj_id self, dictionary params) throws InterruptedException
    {
        boolean validGoggles = true;
        if (!isSEGoggle(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id owner = getOwner(self);
        if (!isIdValid(owner))
        {
            validGoggles = false;
        }
        else 
        {
            obj_id inv = utils.getInventoryContainer(owner);
            obj_id bank = utils.getPlayerBank(owner);
            obj_id objOnFace = getObjectInSlot(owner, "eyes");
            if (!isIdValid(inv) || !isIdValid(bank))
            {
                validGoggles = false;
            }
            if (!features.isCollectorEdition(owner))
            {
                validGoggles = false;
            }
            else 
            {
                obj_id container = getContainedBy(self);
                if (!(container == inv || container == bank || objOnFace == self))
                {
                    validGoggles = false;
                }
            }
        }
        if (!validGoggles)
        {
            if (isIdValid(owner))
            {
                sendSystemMessage(owner, new string_id("error_message", "destroy_goggle"));
            }
            destroyObject(self);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public boolean isSEGoggle(obj_id item) throws InterruptedException
    {
        for (int i = 0; i < COLLECTOR_EDITION_ITEMS.length; i++)
        {
            if (getTemplateName(item) == COLLECTOR_EDITION_ITEMS[i])
            {
                return true;
            }
        }
        return false;
    }
}
