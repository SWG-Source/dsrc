package script.item.tool;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.space_crafting;
import script.library.utils;
import script.library.sui;
import script.library.ai_lib;
import script.library.pet_lib;
import script.library.space_combat;

public class navicomputer_control_device extends script.base_script
{
    public navicomputer_control_device()
    {
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (ai_lib.aiIsDead(player))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id myContainer = getContainedBy(self);
        obj_id yourPad = utils.getPlayerDatapad(player);
        if (myContainer != yourPad)
        {
            if (hasScript(myContainer, "item.tool.navicomputer_control_device"))
            {
                putIn(self, yourPad);
            }
            return SCRIPT_CONTINUE;
        }
        if (!isSpaceScene())
        {
            mi.addRootMenu(menu_info_types.OPEN_NAVICOMP_DPAD, new string_id(pet_lib.MENU_FILE, "open_navicomp_dpad"));
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (ai_lib.aiIsDead(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (ai_lib.isInCombat(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id myContainer = getContainedBy(self);
        obj_id yourPad = utils.getPlayerDatapad(player);
        if (myContainer != yourPad)
        {
            if (hasScript(myContainer, "item.tool.navicomputer_control_device"))
            {
                putIn(self, yourPad);
            }
            return SCRIPT_CONTINUE;
        }
        if (!isSpaceScene())
        {
            if (item == menu_info_types.OPEN_NAVICOMP_DPAD)
            {
                space_combat.openFlightComputerDatapad(self, player);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToReceiveItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (utils.hasLocalVar(self, "ctsBeingUnpacked"))
        {
            return SCRIPT_CONTINUE;
        }
        debugServerConsoleMsg(null, "navicomputer_control_device.OnAboutToReceiveItem ***************** Entered ");
        if (hasObjVar(item, "item.current") || hasScript(item, "ai.pet_control_device"))
        {
            return SCRIPT_OVERRIDE;
        }
        debugServerConsoleMsg(null, "navicomputer_control_device.OnAboutToReceiveItem ***************** Not trying to pass in a pcd. Good! Continuing:");
        String itemTemplate = getTemplateName(item);
        int index = itemTemplate.indexOf("object/intangible/data_item/droid_command");
        if (index < 0)
        {
            obj_id dpad = utils.getDatapad(self);
            putIn(item, dpad);
            return SCRIPT_OVERRIDE;
        }
        if (!isIdValid(transferer))
        {
            return SCRIPT_OVERRIDE;
        }
        debugServerConsoleMsg(null, "navicomputer_control_device.OnAboutToReceiveItem ***************** Everything looked good. Sending a script-continue");
        return SCRIPT_CONTINUE;
    }
    public int OnGiveItem(obj_id self, obj_id item, obj_id player) throws InterruptedException
    {
        debugServerConsoleMsg(null, "**********  OnGiveItem trigger on the Navicomputer Control Device has fired.");
        obj_id owner = getOwner(self);
        if (!isIdValid(owner))
        {
            return SCRIPT_OVERRIDE;
        }
        if (player != owner)
        {
            return SCRIPT_OVERRIDE;
        }
        obj_id navicompDpad = getObjectInSlot(self, "datapad");
        if (!isIdValid(navicompDpad))
        {
            return SCRIPT_OVERRIDE;
        }
        putIn(item, navicompDpad);
        debugServerConsoleMsg(null, "navicomputer_control_device.OnGiveItem ***************** Everything looked good. Sending a script-continue");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        obj_id datapad = getContainedBy(self);
        if (!isIdValid(datapad))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id master = getContainedBy(datapad);
        if (!isIdValid(master))
        {
            return SCRIPT_CONTINUE;
        }
        sendSystemMessage(master, new string_id("pet/pet_menu", "device_added"));
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "dataModuleRating"))
        {
            int datastorage = getIntObjVar(self, "dataModuleRating");
        }
        obj_id[] loadedDroidCommands = space_crafting.getDatapadDroidCommands(self);
        if ((loadedDroidCommands != null) && (loadedDroidCommands.length > 0))
        {
            names[idx] = "droid_program_loaded";
            attribs[idx] = " ";
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
            for (int i = 0; i < loadedDroidCommands.length; i++)
            {
                if (hasObjVar(loadedDroidCommands[i], "strDroidCommand"))
                {
                    String programName = getStringObjVar(loadedDroidCommands[i], "strDroidCommand");
                    names[idx] = "droid_program";
                    attribs[idx] = utils.packStringId(new string_id("space/droid_commands", programName));
                    idx++;
                    if (idx >= names.length)
                    {
                        return SCRIPT_CONTINUE;
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int destroySelf(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToBeTransferred(obj_id self, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        if (!isIdValid(destContainer))
        {
            return SCRIPT_OVERRIDE;
        }
        if (!isIdValid(transferer))
        {
            return SCRIPT_OVERRIDE;
        }
        string_id strSpam = new string_id("space/space_interaction", "cant_transfer_active_computer");
        sendSystemMessage(transferer, strSpam);
        return SCRIPT_OVERRIDE;
    }
}
