package script.space.crafting;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.sui;
import java.lang.Math;
import script.library.space_crafting;
import script.library.space_utils;
import script.library.utils;
import script.library.prose;
import script.library.space_pilot_command;

public class droid_datapad_handler extends script.base_script
{
    public droid_datapad_handler()
    {
    }
    public int OnAboutToReceiveItem(obj_id self, obj_id objStartContainer, obj_id objPlayer, obj_id objItem) throws InterruptedException
    {
        if (!isIdValid(objItem) || !isIdValid(objPlayer) || !isIdValid(objStartContainer))
        {
            return SCRIPT_CONTINUE;
        }
        String droidProgram = "";
        int currentUsedProgramMemory = 0;
        String objectTemplateName = getTemplateName(objItem);
        int index = objectTemplateName.indexOf("object/intangible/data_item/droid_command");
        if (index < 0)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id controlDevice = getContainedBy(self);
        if (!isIdValid(controlDevice))
        {
            return SCRIPT_OVERRIDE;
        }
        if (hasObjVar(objItem, "strDroidCommand"))
        {
            droidProgram = getStringObjVar(objItem, "strDroidCommand");
        }
        else 
        {
            return SCRIPT_OVERRIDE;
        }
        obj_id[] objCommands = space_crafting.getDatapadDroidCommands(controlDevice);
        if (objCommands != null)
        {
            if (space_pilot_command.hasDuplicateProgram(objCommands, droidProgram))
            {
                string_id strSpam = new string_id("space/droid_commands", "droid_command_programmed_already");
                sendSystemMessage(objPlayer, strSpam);
                return SCRIPT_OVERRIDE;
            }
            currentUsedProgramMemory = space_pilot_command.getCurrentlyUsedProgramMemory(objCommands);
        }
        else 
        {
            debugServerConsoleMsg(null, "DROID_DATAPAD_HANDLER.OnAboutToReceiveItem  ***   getDatapadDroidCommands returned NULL");
        }
        int droidProgramSize = space_pilot_command.getDroidProgramSize(droidProgram, objItem);
        string_id strSpam = new string_id("space/droid_commands", "droid_command_programmed");
        if (hasObjVar(controlDevice, "item.objectName"))
        {
            strSpam = new string_id("space/droid_commands", "droid_command_programmed_flightcomputer");
        }
        sendSystemMessage(objPlayer, strSpam);
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToLoseItem(obj_id self, obj_id objDestination, obj_id objPlayer, obj_id objItem) throws InterruptedException
    {
        if (hasObjVar(objItem, "strDroidCommand"))
        {
            string_id strSpam = new string_id("space/droid_commands", "droid_command_delete");
            sendSystemMessage(objPlayer, strSpam);
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
}
