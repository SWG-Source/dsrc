package script.item.container;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.slicing;
import script.library.xp;
import script.library.utils;

public class locked_slicable extends script.base_script
{
    public locked_slicable()
    {
    }
    public static final string_id SID_SLICE = new string_id("slicing/slicing", "slice");
    public static final string_id SID_LOCKED = new string_id("slicing/slicing", "locked");
    public static final string_id SID_BROKEN = new string_id("slicing/slicing", "broken");
    public static final string_id SID_SUCCESS = new string_id("slicing/slicing", "container_success");
    public static final string_id SID_FAIL = new string_id("slicing/slicing", "container_fail");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "slicing.locked"))
        {
            setObjVar(self, "slicing.locked", 1);
        }
        if (!hasObjVar(self, "slicing.slicable"))
        {
            setObjVar(self, "slicing.slicable", 1);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (hasSkill(player, "class_smuggler_phase1_novice") && hasObjVar(self, "slicing.locked"))
        {
            if (!hasObjVar(self, "loot_crate"))
            {
                obj_id pInv = utils.getInventoryContainer(player);
                if (!isIdValid(pInv))
                {
                    return SCRIPT_CONTINUE;
                }
                if (!contains(pInv, self))
                {
                    return SCRIPT_CONTINUE;
                }
            }
            mi.addRootMenu(menu_info_types.SERVER_MENU1, SID_SLICE);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.SERVER_MENU1)
        {
            if (!hasObjVar(self, "loot_crate"))
            {
                obj_id pInv = utils.getInventoryContainer(player);
                if (!isIdValid(pInv))
                {
                    return SCRIPT_CONTINUE;
                }
                if (!contains(pInv, self))
                {
                    return SCRIPT_CONTINUE;
                }
            }
            if (!hasSkill(player, "class_smuggler_phase1_novice") || !hasObjVar(self, "slicing.locked"))
            {
                return SCRIPT_CONTINUE;
            }
            if (!hasObjVar(self, "slicing.slicable"))
            {
                sendSystemMessage(player, SID_BROKEN);
                return SCRIPT_CONTINUE;
            }
            slicing.startSlicing(player, self, "finishSlicing", "container");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToOpenContainer(obj_id self, obj_id opener) throws InterruptedException
    {
        if (hasObjVar(self, "slicing.locked"))
        {
            sendSystemMessage(opener, SID_LOCKED);
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int finishSlicing(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        int success = params.getInt("success");
        obj_id player = params.getObjId("player");
        if (success == 1)
        {
            removeObjVar(self, "slicing.locked");
            sendSystemMessage(player, SID_SUCCESS);
            messageTo(self, "handleSlicingSuccess", null, 0.f, true);
        }
        else 
        {
            removeObjVar(self, "slicing.slicable");
            sendSystemMessage(player, SID_FAIL);
        }
        return SCRIPT_CONTINUE;
    }
}
