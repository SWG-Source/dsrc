package script.item.camokit;

import script.library.scout;
import script.library.utils;
import script.menu_info;
import script.menu_info_types;
import script.obj_id;
import script.string_id;

public class camokit extends script.base_script
{
    public camokit()
    {
    }
    public static final string_id SID_APPLY_CAMO = new string_id("skl_use", "skl_apply_camo");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int count = getCount(self);
        if (count < 0)
        {
            destroyObject(self);
            return SCRIPT_CONTINUE;
        }
        obj_id pInv = utils.getInventoryContainer(player);
        obj_id[] contents = utils.getContents(pInv, true);
        int found = 0;
        for (obj_id content : contents) {
            if (content == self) {
                found = 1;
                break;
            }
        }
        if (found == 0)
        {
            return SCRIPT_CONTINUE;
        }
        mi.addRootMenu(menu_info_types.ITEM_USE, SID_APPLY_CAMO);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            scout.conceal(player, null);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        int count = getCount(self);
        names[idx] = "quantity";
        attribs[idx] = Integer.toString(count);
        idx++;
        if (idx >= names.length)
        {
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
}
