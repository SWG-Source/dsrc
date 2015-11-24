package script.space.crafting;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.space_crafting;
import script.library.sui;
import script.library.utils;

public class component_name_handler extends script.base_script
{
    public component_name_handler()
    {
    }
    public int handleComponentNameSui(obj_id self, dictionary params) throws InterruptedException
    {
        int intButton = sui.getIntButtonPressed(params);
        if (intButton == sui.BP_CANCEL)
        {
            detachScript(self, space_crafting.SCRIPT_COMPONENT_NAME_HANDLER);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            obj_id player = sui.getPlayerId(params);
            showComponentNameSui(self, player);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleSetComponentName(obj_id self, dictionary params) throws InterruptedException
    {
        int intButton = sui.getIntButtonPressed(params);
        if (intButton == sui.BP_CANCEL)
        {
            detachScript(self, space_crafting.SCRIPT_COMPONENT_NAME_HANDLER);
            return SCRIPT_CONTINUE;
        }
        String newComponentName = sui.getInputBoxText(params);
        obj_id player = sui.getPlayerId(params);
        if (isNameReserved(newComponentName) != true)
        {
            setName(self, newComponentName);
            detachScript(self, space_crafting.SCRIPT_COMPONENT_NAME_HANDLER);
        }
        else 
        {
            string_id msg2 = new string_id(space_crafting.STF_COMPONENT_TOOL, "reserved");
            sendSystemMessage(player, msg2);
            showComponentNameSui(self, player);
        }
        return SCRIPT_CONTINUE;
    }
    public void showComponentNameSui(obj_id owner, obj_id target) throws InterruptedException
    {
        String prompt2 = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "prompt2"));
        String title2 = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "title2"));
        sui.inputbox(owner, target, prompt2, sui.OK_ONLY, title2, sui.INPUT_NORMAL, null, "handleSetComponentName", null);
    }
}
