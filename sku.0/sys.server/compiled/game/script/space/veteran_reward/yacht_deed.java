package script.space.veteran_reward;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.space_crafting;
import script.library.utils;
import script.library.create;
import script.library.space_transition;
import script.library.space_utils;

public class yacht_deed extends script.base_script
{
    public yacht_deed()
    {
    }
    public static final string_id MNU_CREATE_VEHICLE = new string_id("sui", "create_vehicle");
    public static final String STF = "space/space_item";
    public static final string_id SID_ALREADY_HAVE_VEHICLE = new string_id("veteran", "have_yacht");
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!utils.isNestedWithin(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        int mnuColor = mi.addRootMenu(menu_info_types.SERVER_MENU1, MNU_CREATE_VEHICLE);
        if (mnuColor > -1 && ((getContainedBy(self) != getOwner(self)) || isGod(player)))
        {
            String template = utils.getTemplateFilenameNoPath(self);
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
            obj_id datapad = utils.getDatapad(player);
            if (isIdValid(datapad))
            {
                obj_id[] existingShip = utils.getAllItemsInContainerByTemplate(datapad, "object/intangible/ship/sorosuub_space_yacht_pcd.iff", false);
                if (existingShip == null || existingShip.length < 1)
                {
                    obj_id newShip = space_crafting.createChassisFromDeed(player, self, 3000, 3000, 100000, "sorosuub_space_yacht");
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    sendSystemMessage(player, SID_ALREADY_HAVE_VEHICLE);
                }
            }
            space_transition.handlePotentialSceneChange(player);
        }
        return SCRIPT_CONTINUE;
    }
}
