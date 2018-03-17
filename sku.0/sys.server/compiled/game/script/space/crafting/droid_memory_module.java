package script.space.crafting;

import script.*;
import script.library.space_combat;
import script.library.space_pilot_command;
import script.library.sui;
import script.library.utils;

public class droid_memory_module extends script.base_script
{
    public droid_memory_module()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        string_id strSpam = getNameStringId(self);
        string_id strTest = new string_id("space/space_item", "blank_memory_chip_n");
        if (strSpam != null)
        {
            if (!(strSpam.equals(strTest)))
            {
                setName(self, "");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id objPlayer, menu_info menuInfo) throws InterruptedException
    {
        if (!hasObjVar(self, "strDroidCommand"))
        {
            menuInfo.addRootMenu(menu_info_types.MEMORY_CHIP_PROGRAM, new string_id("space/space_interaction", "memory_chip_program"));
        }
        else 
        {
            menuInfo.addRootMenu(menu_info_types.MEMORY_CHIP_ANALYZE, new string_id("space/space_interaction", "memory_chip_analyze"));
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id objPlayer, int item) throws InterruptedException
    {
        obj_id objContainingPlayer = utils.getContainingPlayer(self);
        if (objContainingPlayer != objPlayer)
        {
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.MEMORY_CHIP_PROGRAM)
        {
            String[] strDroidCommands = space_combat.getDroidCommands(objPlayer);
            if (strDroidCommands == null)
            {
                string_id strSpam = new string_id("space/space_interaction", "no_droid_commands");
                sendSystemMessage(objPlayer, strSpam);
                return SCRIPT_CONTINUE;
            }
            String[] strCommandIds = new String[strDroidCommands.length];
            for (int intI = 0; intI < strDroidCommands.length; intI++)
            {
                strCommandIds[intI] = utils.packStringId(new string_id("space/droid_commands", strDroidCommands[intI] + "_commandname"));
            }
            utils.setScriptVar(objPlayer, "strDroidProgramCommands", strDroidCommands);
            string_id strPromptId = new string_id("space/droid_commands", "droid_program_prompt");
            String strPrompt = utils.packStringId(strPromptId);
            sui.listbox(self, objPlayer, strPrompt, strCommandIds, "programMemoryChip");
            return SCRIPT_CONTINUE;
        }
        else if (item == menu_info_types.MEMORY_CHIP_ANALYZE)
        {
            String strDroidCommand = getStringObjVar(self, "strDroidCommand");
            if (strDroidCommand == null)
            {
                return SCRIPT_CONTINUE;
            }
            string_id strSpam = new string_id("space/droid_commands", strDroidCommand + "_description");
            sui.msgbox(objPlayer, strSpam);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        if(!isIdValid(self) || !exists(self) || self == null || self == obj_id.NULL_ID){
            return SCRIPT_CONTINUE;
        }
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "programSize"))
        {
            names[idx] = "droid_command_program_size";
            int programSize = getIntObjVar(self, "programSize");
            attribs[idx] = " " + programSize;
            idx++;
        }
        if (hasObjVar(self, "strDroidCommand"))
        {
            String strDroidCommand = getStringObjVar(self, "strDroidCommand");
            names[idx] = "droid_command_name";
            attribs[idx] = utils.packStringId(new string_id("space/droid_commands", strDroidCommand));
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int programMemoryChip(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id objPlayer = sui.getPlayerId(params);
        if (!isIdValid(objPlayer))
        {
            return SCRIPT_CONTINUE;
        }
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        int intIndex = sui.getListboxSelectedRow(params);
        if (intIndex == -1)
        {
            return SCRIPT_CONTINUE;
        }
        String[] strDroidProgramCommands = utils.getStringArrayScriptVar(objPlayer, "strDroidProgramCommands");
        if (intIndex > strDroidProgramCommands.length - 1)
        {
            return SCRIPT_CONTINUE;
        }
        String strCommand = strDroidProgramCommands[intIndex];
        setObjVar(self, "strDroidCommand", strCommand);
        int programSize = space_pilot_command.getDroidProgramSize(strCommand, self);
        setObjVar(self, "programSize", programSize);
        setName(self, "");
        if (!setName(self, new string_id("space/droid_commands", strCommand + "_chipname")))
        {
            debugServerConsoleMsg(null, "DROID_MEMORY_MODULE.programMemoryChip *** FAILED TO SETNAME.");
        }
        string_id strSpam = new string_id("space/space_interaction", "memory_chip_burnt");
        sendSystemMessage(objPlayer, strSpam);
        sendDirtyObjectMenuNotification(self);
        return SCRIPT_CONTINUE;
    }
}
