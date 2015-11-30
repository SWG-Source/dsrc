package script.beta;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.resource;
import script.library.sui;

public class uber_survey_tool extends script.base_script
{
    public uber_survey_tool()
    {
    }
    public static final String HANDLER_SET_TOOL_CLASS = "handleSetToolClass";
    public static final string_id SID_TOOL_OPTIONS = new string_id("sui", "tool_options");
    public static final string_id SID_TOOL_CLASS = new string_id("sui", "survey_class");
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setName(self, "Uber Survey Tool");
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int mnuOptions = -1;
        menu_info_data mid = mi.getMenuItemByType(menu_info_types.SERVER_ITEM_OPTIONS);
        if (mid == null)
        {
            mnuOptions = mi.addRootMenu(menu_info_types.SERVER_ITEM_OPTIONS, SID_TOOL_OPTIONS);
        }
        else 
        {
            mnuOptions = mid.getId();
        }
        if (mnuOptions > 0)
        {
            int subClass = mi.addSubMenu(mnuOptions, menu_info_types.SERVER_SURVEY_TOOL_CLASS, SID_TOOL_CLASS);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.SERVER_SURVEY_TOOL_CLASS)
        {
            sui.listbox(self, player, "Select a class to set your survey tool to...", resource.CLASS_NAME, HANDLER_SET_TOOL_CLASS);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnRequestCoreSample(obj_id self, obj_id player, String resource_type) throws InterruptedException
    {
        removeObjVar(player, resource.VAR_SAMPLE_STAMP);
        return SCRIPT_CONTINUE;
    }
    public int handleSetToolClass(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        int selRow = sui.getListboxSelectedRow(params);
        switch (selRow)
        {
            case -1:
            return SCRIPT_CONTINUE;
            default:
            if ((player != null) && (player != obj_id.NULL_ID))
            {
                if (!resource.setToolClass(self, selRow))
                {
                    sendSystemMessageTestingOnly(player, "Uber Survey Tool: class set FAILED");
                }
                else 
                {
                    sendSystemMessageTestingOnly(player, "Uber Survey Tool: class set -> " + resource.CLASS_NAME[selRow]);
                }
            }
            break;
        }
        return SCRIPT_CONTINUE;
    }
}
